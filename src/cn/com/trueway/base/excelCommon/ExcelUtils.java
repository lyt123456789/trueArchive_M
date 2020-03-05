package cn.com.trueway.base.excelCommon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import cn.com.trueway.base.excelCommon.ContentValidate;

public class ExcelUtils {
    /**
     * 根据路径加载解析Excel
     * 
     * @param path
     * @return
     */
    public static List<Object> parseExcelWhData(Class<?> obj, File file) {

        List<Object> list = new ArrayList<Object>();
        InputStream input = null;
        Workbook workBook = null;
        Sheet sheet = null;
        // 判断文件是否是Excel(2003、2007)
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定的文件！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("读取Excel文件发生异常！");
            e.printStackTrace();
        }
        if (input != null) {
            if (!input.markSupported()) {
                input = new PushbackInputStream(input, 8);
            }
            try {
                workBook = WorkbookFactory.create(input);
            } catch (IOException e) {
                System.out.println("创建表格工作簿对象发生IO异常！原因：" + e.getMessage());
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                System.out.println("非法的输入流：当前输入流非OLE2流或OOXML流！");
                e.printStackTrace();
            }
            try {
                if (workBook != null) {
                    int numberSheet = workBook.getNumberOfSheets();
                    if (numberSheet > 0) {
                        sheet = workBook.getSheetAt(0);// 获取第一个工作簿(Sheet)的内容【注意根据实际需要进行修改】
                        list = getExcelContentWhData(sheet, obj);
                    } else {
                        System.out.println("目标表格工作簿(Sheet)数目为0！");
                    }
                }
                input.close();
            } catch (IOException e) {
                System.out.println("关闭输入流异常！" + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 解析(读取)Excel内容
     * 
     * @param sheet
     * @return
     */
    public static List<Object> getExcelContentWhData(Sheet sheet, Class<?> obj) {
        List<Object> list = new ArrayList<Object>();
        int rowCount = sheet.getPhysicalNumberOfRows();// 总行数
        int start = 1;
        if (rowCount > start) {
            // 得到真正的行数
            int cellCount = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = start; i < rowCount; i++) {// 遍历行，略过标题行，从第三行开始
                Row row = sheet.getRow(i);
                String resultTemp = "";
                // 判断当前行的
                for (int j = 0; j < cellCount; j++) {
                    if (row.getCell(j) != null
                            && !ContentValidate.checkNull(getCellValue(row.getCell(j)).trim())) {
                        resultTemp = "false";
                    }
                }
                if (!ContentValidate.checkNull(resultTemp) && row != null) {
                    Object entity = null;
                    /*if (obj == xxx.class) {
                        entity = new xxx();
                    }
                    invokeBeanFromExcel(entity, row);
                    String validateStr = "";
                    // 必填属性是否为空
                    validateStr = ValidateHelper.validateClass(entity);
                    if (obj == xxx.class) {
                        ((xxx) entity).setErrorMsg(validateStr);
                    }*/
                    list.add(entity);
                }
            }
        }
        return list;
    }

    public static String getCellValue(Cell cell) {
        String ret;
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    ret = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    ret = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    ret = null;
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    try {
                        ret = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        ret = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    ret = cell.getNumericCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            ret = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        } else {
                            ret = "";
                        }
                    } else {
                        ret = cell.getNumericCellValue() + "";
                        ret = subZeroAndDot(ret);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    ret = cell.getRichStringCellValue().getString();
                    break;
                default:
                    ret = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }
        return ret; // 有必要自行trim
    }

    /**
     * 自动获取请求参数，注入到bean中
     * 
     * @param bean
     */
    protected static Object invokeBeanFromExcel(Object bean, Row row) {

        if (bean == null || row == null) return bean;
        try {
            Class<?> userCla = (Class<?>) bean.getClass();
            Field[] fs = userCla.getDeclaredFields();
            int cellCount = row.getLastCellNum();// 总列数
            DecimalFormat avoidScientific = new DecimalFormat("0");
            for (int i = 0; i < fs.length; i++) {
                try {
                    Field f = fs[i];
                    f.setAccessible(true); // 设置些属性是可以访问的
                    String type = f.getType().toString();// 得到此属性的类型
                    // 获取请求参数
                    String value = null;
                    // 获取excel的值
                    if (i < cellCount) {
                        Cell cell = row.getCell(i);
                        if (cell != null) {
                            value = getCellValue(cell);
                            // 防止科学技术法
                            if (!ContentValidate.checkNull(value.trim())
                                    && cell.getCellType() == Cell.CELL_TYPE_NUMERIC
                                    && getCellValue(cell).trim().indexOf("E") > 0) {
                                value = avoidScientific.format(cell.getNumericCellValue());
                            }
                            value = value == null ? null : value.trim();
                        }
                    }

                    if (value != null && !"".equalsIgnoreCase(value)
                            && !"null".equalsIgnoreCase(value)) {
                        if ("null".equalsIgnoreCase(value)) {
                            value = "";
                        }
                        if (type.endsWith("String")) {
                            f.set(bean, value);
                        } else if (type.endsWith("int") || type.endsWith("Integer")) {
                            f.set(bean, Integer.parseInt(value));
                        } else if (type.equalsIgnoreCase("float")) {
                            f.set(bean, Float.parseFloat(value));
                        } else if (type.equalsIgnoreCase("double")) {
                            f.set(bean, Double.parseDouble(value));
                        } else if (type.endsWith("BigDecimal")) {
                            if (ContentValidate.checkNull(value)) {
                                f.set(bean, new BigDecimal(0));
                            } else {
                                f.set(bean, new BigDecimal(value));
                            }
                        }
                    } else {
                        if (type.endsWith("BigDecimal")) {
                            f.set(bean, new BigDecimal(0));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bean;
        }
        return bean;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * 
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 初始化头部单元格格式：背景色默认GREY_25_PERCENT、文字水平、垂直居中、自动换行、边框有线条
     * 
     * @param workBook
     * @param fontColor
     * 
     * @return
     */
    private static XSSFCellStyle initCellHeadStyle(SXSSFWorkbook workBook, short fontColor,
            boolean bigTitle) {
        XSSFCellStyle cs = (XSSFCellStyle) workBook.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.WHITE.index); // 设置背景色
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        cs.setFont(initFontStyle(workBook, fontColor, bigTitle));// 设置文字样式
        cs.setWrapText(true);// 默认自动换行
        cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cs.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cs;
    }

    /**
     * 初始化单元格文字格式 默认加粗，大小为10，颜色默认
     * 
     * @param workBook
     * @return
     */
    private static XSSFFont initFontStyle(SXSSFWorkbook workBook, short fontColor,
            boolean bigTitle) {
        XSSFFont fontBlack = (XSSFFont) workBook.createFont();
        if (bigTitle) {
            fontBlack.setFontHeightInPoints((short) 20);
        } else {
            fontBlack.setFontHeightInPoints((short) 10);
        }
        fontBlack.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
        fontBlack.setColor(fontColor);
        return fontBlack;
    }

    /**
     * 单元格赋值，初始化
     * 
     * @param row 行对象
     * @param nameColorArr 文字和文字颜色属性
     */
    private static void initCells(SXSSFWorkbook workBook, SXSSFRow row, String[] nameColorArr) {
        for (int i = 0; i < nameColorArr.length; i++) {
            SXSSFCell cellTmp = (SXSSFCell) row.createCell(i);
            cellTmp.setCellStyle(initCellHeadStyle(workBook, HSSFColor.BLACK.index, false));
            cellTmp.setCellValue(nameColorArr[i]);// 单元格设值
        }
    }

    public static String getValuebyProperty(Object model, String valueParam) throws Exception {
        String value = "";
        valueParam = valueParam.substring(0, 1).toUpperCase() + valueParam.substring(1);
        Method m = model.getClass().getMethod("get" + valueParam);
        value = String.valueOf(m.invoke(model));
        return value;
    }

    public static SXSSFWorkbook exportTool(SXSSFWorkbook wb, List<?> objList, String titleName,
            String titleCode) {
        try {
            String[] prostrArr = titleCode.split(",");
            SXSSFSheet sh = (SXSSFSheet) wb.createSheet();
            // 循环设置单元格宽度，自定义
            for (int i = 0; i < prostrArr.length; i++) {
                sh.setColumnWidth(i, 20 * 256);
            }
            String[] Line1namesArr = titleName.split(",");
            SXSSFRow row1 = (SXSSFRow) sh.createRow(0);
            row1.setHeight((short) (25 * 25));
            initCells(wb, row1, Line1namesArr);
            // 数据填充
            // 判断是哪个对象
            if (objList != null && objList.size() > 0) {
                int locusCnt = 0;
                for (int j = 0; j < objList.size(); j++) {
                    locusCnt++;
                    SXSSFRow row = (SXSSFRow) sh.createRow(locusCnt);
                    for (int i = 0; i < prostrArr.length; i++) {
                        SXSSFCell cell = (SXSSFCell) row.createCell(i);
                        cell.setCellValue("null".equals(getValuebyProperty(objList.get(j), prostrArr[i]))
                                || "0E-7".equals(getValuebyProperty(objList.get(j), prostrArr[i]))
                                        ? ""
                                        : getValuebyProperty(objList.get(j), prostrArr[i]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return wb;
    }

    /**
     * 用于设置复杂表头（简单型的复杂表头）
     * 
     * @param sheetName sheet名称
     * @param wb 表对象
     * @param cellListMap 表头数据 {key=cellRowNum-1}
     * @param cellRowNum 表头总占用行数
     * @param exportData 行数据
     * @param rowMapper 单元格名称
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static SXSSFWorkbook createCSVUtil(String sheetName, SXSSFWorkbook wb, Map<String,List<CellModel>> cellListMap,
            Integer cellRowNum, List<LinkedHashMap> exportData)throws Exception {
        //设置表格名称
        SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetName);
        sheet.autoSizeColumn(1, true);
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        //设置单元格内容水平对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //设置单元格内容垂直对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //设置自动换行
        cellStyle.setWrapText(true);
        for(int t = 0; t < cellRowNum; t++) {
        	SXSSFRow row = (SXSSFRow) sheet.createRow(t);
        	List<CellModel> cellNameList = cellListMap.get(String.valueOf(t));
            for (CellModel cellModel : cellNameList) {
                sheet.addMergedRegion(new CellRangeAddress(cellModel.getStartRow(),
                        cellModel.getEndRow(), cellModel.getStartColumn(), cellModel.getEndColumn()));
            } 
            for (int i = 0; i < cellNameList.size(); i++) {
                CellModel cellModel = cellNameList.get(i);
                // 遍历插入表头
                SXSSFCell cell = (SXSSFCell) row.createCell(cellModel.getStartColumn());
                cell.setCellValue(cellModel.getCellName());
                cell.setCellStyle(cellStyle);
            }
        }
        for (LinkedHashMap hashMap : exportData) {
            SXSSFRow rowValue = (SXSSFRow) sheet.createRow(cellRowNum);
            Iterator<Map.Entry> iteratorRow = hashMap.entrySet().iterator();
            while (iteratorRow.hasNext()) {
                Map.Entry entryRow = iteratorRow.next();
                Integer key = Integer.valueOf(entryRow.getKey().toString());
                String value = "";
                if (entryRow.getValue() != null) {
                    value = entryRow.getValue().toString();
                } else {
                    value = "";
                }
                SXSSFCell cellValue = (SXSSFCell) rowValue.createCell(key - 1);
                cellValue.setCellValue(value);
                cellValue.setCellStyle(cellStyle);
            }
            cellRowNum++;
        }
        return wb;
    }

    private static void initMerageCells(SXSSFWorkbook workBook, SXSSFSheet sh, SXSSFRow row,
            int start, int len, String cellValue, boolean bigTitle) {
        SXSSFCell cell01 = (SXSSFCell) row.createCell(start);
        cell01.setCellStyle(initCellHeadStyle(workBook, HSSFColor.BLACK.index, bigTitle));
        cell01.setCellValue(cellValue);// 单元格设值
        CellRangeAddress cra = new CellRangeAddress(0, 0, start, start + len);
        sh.addMergedRegion(cra);
        for (int i = start + 1; i < start + len + 1; i++) {
            SXSSFCell tc = (SXSSFCell) row.createCell(i);
            tc.setCellStyle(initCellHeadStyle(workBook, HSSFColor.BLACK.index, bigTitle));
        }
    }

    private static void initMerageCells_row(SXSSFWorkbook workBook, SXSSFSheet sh, SXSSFRow row,
            int rowbegin, int rowend, int start, int len, String cellValue, boolean bigTitle) {
        SXSSFCell cell01 = (SXSSFCell) row.createCell(start);
        cell01.setCellStyle(initCellHeadStyle(workBook, HSSFColor.BLACK.index, false));
        cell01.setCellValue(cellValue);// 单元格设值
        CellRangeAddress cra = new CellRangeAddress(rowbegin, rowend, start, start + len);
        sh.addMergedRegion(cra);
        for (int i = start + 1; i < start + len + 1; i++) {
            SXSSFCell tc = (SXSSFCell) row.createCell(i);
            XSSFCellStyle cs = (XSSFCellStyle) workBook.createCellStyle();
            cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cs.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
            tc.setCellStyle(cs);
        }
    }

}
