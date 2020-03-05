package cn.com.trueway.archiveModel.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.trueway.archiveModel.dao.EssModelDao;
import cn.com.trueway.archiveModel.entity.CheckLog;
import cn.com.trueway.archiveModel.entity.EssStructure;
import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.entity.FileAttachment;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.util.Utils;

@Service("essModelService")
public class EssModelServiceImpl implements EssModelService {
	@Autowired
	private EssModelDao essModelDao;
	
	
	public EssModelDao getEssModelDao() {
		return essModelDao;
	}

	public void setEssModelDao(EssModelDao essModelDao) {
		this.essModelDao = essModelDao;
	}

	@Override
	public List<EssTree> getModelObjListByMap(Map<String, String> map, Paging paging) {
		List<EssTree> list = new ArrayList<EssTree>();
		try {
			list = essModelDao.getModelObjListByMap(map,paging);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getModelTreeById(String id, String pid) {
		
		StringBuilder json = new StringBuilder();
		List<EssTree> data = essModelDao.getModelById(id, pid);
		json.append("{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": [");
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"checkArr\":\"").append("0\",");
			if(!"1".equals(e.getIsLeaf()+"")){
				json.append("\"isLast\":").append("false,");
			}
			json.append("\"basicData\":").append(idStructure).append("");	
			json.append("},");
			
			//子结构
			/*if(idStructure!=null && idStructure != 0) {
				List<Map<String, Object>> strucList = essModelDao.getChildStructure(idStructure);
				if(strucList!=null){
					for(Map<String, Object> map:strucList) {
						String idChild = map.get("IDCHILD").toString();
						String title = map.get("TITLE").toString();
						String parentId = map.get("PID").toString();
						json.append("{");
						json.append("\"id\":\"").append(parentId+"-"+idChild).append("\",");
						json.append("\"title\":\"").append(title).append("\",");
						json.append("\"parentId\":\"").append(parentId).append("\",");
						json.append("\"basicData\":").append(idChild).append("");
						json.append("},");
					}
				}
			
			}*/
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]}");
		return json.toString();
	}
	
	@Override
	public List<Map<String, Object>> getChildStructure(Integer struc) {
		List<Map<String, Object>> strucList = essModelDao.getChildStructure(struc);
		return strucList;
	}

	@Override
	public List<EssTree> getModelById(String id, String pid) {
		return essModelDao.getModelById(id, pid);
	}

	@Override
	public List<EssTag> queryTags(Map<String, String> map) {
		return essModelDao.queryTags(map);
	}

	@Override
	public String getTagsTitle(String struc) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", struc);
		StringBuilder json = new StringBuilder();
		List<EssTag> tags = essModelDao.queryTags(map);
		for (EssTag t : tags) {
			json.append(",{field:'C"+t.getId()+"',title: '"+t.getEsIdentifier()+"',minWidth:80}");
		}
		
		return json.toString();
	}

	/**
	 * 要生成xls,准确性、完整性、可用性、安全性
	 */
	public HSSFWorkbook checkResultExcel(String struc, String [] ids) {
		// 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建第一个工作表
        HSSFSheet sheet = workbook.createSheet("准确性");
        // 创建sheet行
        HSSFRow hssfRow = null;
        // 设置格式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setFont(font);
        
        //创建行及单元格
        sheet.setColumnWidth(0, 10*256);
        if(ids!=null&&ids.length>0) {
        	for(int i=0;i<ids.length;i++) {
        		hssfRow = sheet.createRow(i);
        		List<String> list = checkSixing(struc, ids[i]);
        		HSSFCell headCell = hssfRow.createCell(0);
    			headCell.setCellValue(ids[i]);//设置单元格内容
    			headCell.setCellStyle(cellStyle);
        		for(int j=0;j<list.size();j++) {
        			headCell = hssfRow.createCell(j+1);
        			headCell.setCellValue(list.get(j));//设置单元格内容
        			headCell.setCellStyle(cellStyle);
        			
        			sheet.setColumnWidth(j+1, 24*256);
        		}
        		
        	}
        }
        // 创建第2个工作表,检测是否有对应的档案内容,案卷号、件号、文号是否连续
        HSSFSheet sheet2 = workbook.createSheet("完整性");
        sheet2.setColumnWidth(0, 10*256);
        HSSFRow row2 = null;
        if(ids!=null&&ids.length>0) {
        	for(int i=0;i<ids.length;i++) {
        		row2 = sheet2.createRow(i);
        		
        		HSSFCell cell2 = row2.createCell(0);
        		cell2.setCellValue(ids[i]);//设置单元格内容
        		cell2.setCellStyle(cellStyle);
        		cell2 = row2.createCell(1);
        		cell2.setCellValue("目录项目记录完整");//设置单元格内容
        		cell2.setCellStyle(cellStyle);
        		
        		sheet2.setColumnWidth(1, 24*256);
        		
        	}
        }
        // 创建第3个工作表
        HSSFSheet sheet3 = workbook.createSheet("可用性");
        sheet3.setColumnWidth(0, 10*256);
        HSSFRow row3 = null;
        if(ids!=null&&ids.length>0) {
        	for(int i=0;i<ids.length;i++) {
        		row3 = sheet3.createRow(i);
        		
        		HSSFCell cell3 = row3.createCell(0);
        		cell3.setCellValue(ids[i]);//设置单元格内容
        		cell3.setCellStyle(cellStyle);
        		cell3 = row3.createCell(1);
        		cell3.setCellValue("未找到相关电子档案");//设置单元格内容
        		cell3.setCellStyle(cellStyle);
        		
        		sheet3.setColumnWidth(1, 24*256);
        		
        	}
        }
        // 创建第4个工作表
        HSSFSheet sheet4 = workbook.createSheet("安全性");
        sheet4.setColumnWidth(0, 10*256);
        HSSFRow row4 = null;
        if(ids!=null&&ids.length>0) {
        	for(int i=0;i<ids.length;i++) {
        		row4 = sheet4.createRow(i);
        		
        		HSSFCell cell4 = row4.createCell(0);
        		cell4.setCellValue(ids[i]);//设置单元格内容
        		cell4.setCellStyle(cellStyle);
        		cell4 = row4.createCell(1);
        		cell4.setCellValue("目录数据安全");//设置单元格内容
        		cell4.setCellStyle(cellStyle);
        		
        		sheet4.setColumnWidth(1, 24*256);
        	}
        }
		
        return workbook;
	}
	

	@Override
	public FileAttachment getFileAttachmentById(String id,String sjid,String struc) {
		return essModelDao.getFileAttachmentById(id, sjid, struc);
	}

	@Override
	public List<Map<String, Object>> getTaskDetailsById(Map<String, String> map,List<EssTag> tags) {
		return essModelDao.getTaskDetailsById(map,tags);
	}

	@Override
	public List<String[]> getMetaDataList(Map<String, String> map) {
		return essModelDao.getMetaDataList(map);
	}

	@Override
	public void saveEssModel(EssTree ess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEssModel(EssTree ess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEssModelById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer deleteEssModelByIds(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> checkSixing(String struc, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCheckLog(CheckLog log) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFileAttachement(FileAttachment atta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String[]> getNameSpaceList(Map<String, String> map) {
		return essModelDao.getNameSpaceList(map);
	}

	@Override
	public int getMetaDataCount(Map<String, String> map) {
		return essModelDao.getMetaDataCount(map);
	}

	@Override
	public List<EssTag> queryTagsForShow(Map<String, String> map) {
		return essModelDao.queryTagsForShow(map);
	}
	@Override
	public List<EssTag> queryTagsForSearch(Map<String, String> map) {
		return essModelDao.queryTagsForSearch(map);
	}

	@Override
	public String queryTagsForOrder(Map<String, String> map) {
		return essModelDao.queryTagsForOrder(map);
	}

	@Override
	public int getTaskDetailsCount(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2) {
		return essModelDao.getTaskDetailsCount(map,tags1,tags2);
	}

	@Override
	public List<Map<String, Object>> getTaskDetails(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize, Integer pageIndex) {
		return essModelDao.getTaskDetails(map,tags1,tags2,pageSize,pageIndex);
	}

	@Override
	public List<EssTag> queryTagsForShowOfzhyw(Map<String, String> map) {
		return essModelDao.queryTagsForShowOfzhyw(map);
	}

	@Override
	public String[] getMetaDataById(String id) {
		return essModelDao.getMetaDataById(id);
	}

	@Override
	public List<String[]> getTaskDetails4ZHYW(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize,
			Integer pageIndex) {
		return essModelDao.getTaskDetails4ZHYW(map,tags1,tags2,pageSize,pageIndex);
	}

	@Override
	public int getTaskDetails4ZHYWCount(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2) {
		return essModelDao.getTaskDetails4ZHYWCount(map,tags1,tags2);
	}

	@Override
	public List<String[]> queryInfo(String sql, int returnColumn) {
		// TODO Auto-generated method stub
		return essModelDao.queryInfo(sql,returnColumn);
	}
	
}
