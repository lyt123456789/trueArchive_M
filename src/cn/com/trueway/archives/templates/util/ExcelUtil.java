package cn.com.trueway.archives.templates.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;

public class ExcelUtil {

    public static void export(HttpServletResponse response,String excelName,List<Map<String, Object>> sheetsList) {
        //����
    	Workbook workbook = new HSSFWorkbook();
    	
    	for(int i = 0; i < sheetsList.size();i++) {
    		ExcelExportService service = new ExcelExportService();
    		service.createSheetForMap(workbook, (ExportParams)sheetsList.get(i).get("title"), (List<ExcelExportEntity>)sheetsList.get(i).get("entity"), (List<Map<String, Object>>)sheetsList.get(i).get("data"));
    	}
    	setResponseHeader(response, excelName);
        write(workbook, response);
    }

	private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	private static void write(Workbook book, HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            book.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
