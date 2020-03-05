package cn.com.trueway.archives.reports.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.trueway.client.util.CommonUtils;

import cn.com.trueway.archives.reports.pojo.WorkStatistics;
import cn.com.trueway.archives.reports.service.ReportsService;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.excelCommon.CellModel;
import cn.com.trueway.base.excelCommon.ExcelUtils;
import net.sf.json.JSONObject;

public class ReportsAction extends BaseAction {
	
	private static final long serialVersionUID = -9043175135581417337L;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private ReportsService reportsService;
	
	private ArchiveUsingService archiveUsingService; 

	public ArchiveUsingService getArchiveUsingService() {
		return archiveUsingService;
	}

	public void setArchiveUsingService(ArchiveUsingService archiveUsingService) {
		this.archiveUsingService = archiveUsingService;
	}

	public ReportsService getReportsService() {
		return reportsService;
	}

	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}
	
	/**
	 * 获取全部接待人信息
	 */
	public void getAllReceivePeople() {
		JSONObject obj = new JSONObject();
		List<WorkStatistics> list = this.reportsService.getAllReceivePeople("");
		obj.put("list", list);
		toPage(obj.toString());
	}
	
	/**
	 * 获取所有的查档目的
	 */
	public void getAllCDMD() {
		JSONObject obj = new JSONObject();
		String sql = "select id,dataname from t_using_basicdata where type='cdmd' order by numindex";
		List<Map<String,String>> list = this.reportsService.getSysDicResult(sql);
		obj.put("list", list);
		toPage(obj.toString());
	}
	
	/**
	 * 获取所有的查档目的
	 */
	public void getAllCYJG() {
		JSONObject obj = new JSONObject();
		String sql = "select id,dataname from t_using_basicdata where type='cymd' order by numindex";
		List<Map<String,String>> list = this.reportsService.getSysDicResult(sql);
		obj.put("list", list);
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到工作统计页面
	 * @return
	 */
	public String toWorkReportsPage() {
		List<WorkStatistics> listPeople = this.reportsService.getAllReceivePeople("");
		String sqlCDMD = "select id,dataname from t_using_basicdata where type='cdmd' order by numindex";
		List<Map<String,String>> listCDMD = this.reportsService.getSysDicResult(sqlCDMD);
		String sqlCYMD = "select id,dataname from t_using_basicdata where type='cymd' order by numindex";
		List<Map<String,String>> listCYMD = this.reportsService.getSysDicResult(sqlCYMD);
		getRequest().setAttribute("listPeople", listPeople);
		getRequest().setAttribute("listCDMD", listCDMD);
		getRequest().setAttribute("listCYMD", listCYMD);
		return "workStatisticsReportsJsp";
	}
	
	/**
	 * 获取档案局利用工作统计报表
	 */
	public void getWorkStatisticsReports() {
		JSONObject obj = new JSONObject();
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String rePeople = getRequest().getParameter("receivePeople");
		if(rePeople == null) {
			rePeople = "";
		}
		String cdmd = getRequest().getParameter("cdmd");
		String cyjg = getRequest().getParameter("cyjg");
		List<WorkStatistics> list = getWorkReportsData(startTime, endTime, rePeople, cdmd, cyjg);
		if(list != null && !list.isEmpty()) {
			obj.put("flag", "success");  
			obj.put("msg", "");
			obj.put("data", list);
		} else {
			obj.put("flag", "lack");  
			obj.put("msg", "请联系管理员，检查接待人信息"); 
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取档案局利用工作统计报表数据
	 * @param startTime
	 * @param endTime
	 * @param rePeople
	 * @param cdmd
	 * @param cyjg
	 * @return
	 */
	public List<WorkStatistics> getWorkReportsData(String startTime,String endTime,String rePeople,String cdmd,String cyjg) {
		if(rePeople == null) {
			rePeople = "";
		}
		//查询条件
		String tsql = " from t_using_form tf where 1=1";
		String tsql2 = "";
		//总利用人数查询内容
		String zUPeople = "";
		//总利用天数查询内容
		String zUDays = "";
		String rgPeopel = "";
		Map<String,String> map = new HashMap<String,String>();
		map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		for(int i = 0; i < nodeList.size(); i++) {
			ArchiveNode acNode = nodeList.get(i);
			if("登记人".equals(acNode.getVc_name())) {
				rgPeopel = acNode.getVc_fielName();
			} else if("登记日期".equals(acNode.getVc_name())) {
				if(CommonUtils.isNotBlank(startTime)) {
					tsql2 += " and '"+startTime +"'<=tf." + acNode.getVc_fielName();
				}
				if(CommonUtils.isNotBlank(endTime)) {
					tsql2 += " and tf." + acNode.getVc_fielName() + "<='" + endTime + "'";
				}
			} else if("查档目的".equals(acNode.getVc_name()) && CommonUtils.isNotBlank(cdmd)) {
				tsql2 += " and tf." + acNode.getVc_fielName() + "='"+ cdmd + "'";
			} else if("查阅结果".equals(acNode.getVc_name()) && CommonUtils.isNotBlank(cyjg)) {
				tsql2 += " and tf." + acNode.getVc_fielName() + "='"+ cyjg + "'";
			} else if("查档人数".equals(acNode.getVc_name())) {
				zUPeople += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("查档天数".equals(acNode.getVc_name())) {
				zUDays += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("利用单状态".equals(acNode.getVc_name())) {
				tsql2 += " and tf." + acNode.getVc_fielName() + "='3'";
			}	
		}
		
		Map<String,String> mapStore = new HashMap<String,String>();
		mapStore.put("conditionSql", " and vc_table = 'T_USING_STORE'");
		List<ArchiveNode> nodeStoreList = archiveUsingService.queryNodeList(mapStore);
		String dyName = "";
		String fyName = "";
		String zcName = "";
		String phName = "";
		String using = "";
		for(int n = 0; n < nodeStoreList.size(); n++) {
			ArchiveNode acNode = nodeStoreList.get(n);
			if("利用方式".equals(acNode.getVc_name())) {
				using = acNode.getVc_fielName();
			} else if("拍摄画图数".equals(acNode.getVc_name())) {
				phName = acNode.getVc_fielName();
			} else if("打印页数".equals(acNode.getVc_name())) {
				dyName = acNode.getVc_fielName();
			} else if("复印页数".equals(acNode.getVc_name())) {
				fyName = acNode.getVc_fielName();
			} else if("摘抄页数".equals(acNode.getVc_name())) {
				zcName = acNode.getVc_fielName();
			} 
		}
		
		//第一步，获取所有接待人信息
		List<WorkStatistics> list = this.reportsService.getAllReceivePeople(rePeople);
		if(list == null || list.isEmpty()) {
			return null;
		}
		/*if(rePeople == null || "".equals(rePeople)) {
			WorkStatistics wkAdmin = new WorkStatistics();
			wkAdmin.setReceivePeople("admin");
			list.add(wkAdmin);
		}*/
		/*WorkStatistics wkzu = new WorkStatistics();
		wkzu.setReceivePeople("自主查档");
		list.add(wkzu);*/
		int allUP = 0;
		int allUD = 0;
		int allUT = 0;
		int allUA = 0;
		int allPT = 0;
		int allPP = 0;
		int allCT = 0;
		int allCP = 0;
		int allZT = 0;
		int allZP = 0;
		int allPHT = 0;
		int allPHP = 0;
		for(int m = 0; m < list.size(); m++) {
			WorkStatistics wk = list.get(m);
			String receivePeople = wk.getReceivePeople();
			String checkPeopleSql = "select *" + tsql + tsql2 + " and tf."+ rgPeopel + "='" + receivePeople + "'";
			//查询该用户是否在选中条件下存在查阅记录
			boolean flag = this.reportsService.checkRecord(checkPeopleSql);
			if(flag) {
				wk.setUsingPeopleNum("0");
				wk.setUsingDays("0");
				wk.setUsingPeopleTimes("0");
				wk.setArchivesNum("0");
				wk.setPrintTimes("0");
				wk.setPrintPageNums("0");
				wk.setCopyTimes("0");
				wk.setCopyPageNums("0");
				wk.setExcerptTimes("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoTimes("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			String osql = tsql + tsql2 + " and tf."+ rgPeopel + "='" + receivePeople + "'";
			//利用人数
			if(!"".equals(zUPeople)) {
				String zupsql = zUPeople + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zupsql);
				wk.setUsingPeopleNum(upNums);
			} else {
				wk.setUsingPeopleNum("0");
			}
			allUP += Integer.valueOf(wk.getUsingPeopleNum());
			//利用天数
			if(!"".equals(zUDays)) {
				String zudsql = zUDays + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zudsql);
				wk.setUsingDays(upNums);
			} else {
				wk.setUsingDays("0");
			}
			allUD += Integer.valueOf(wk.getUsingDays());
			//利用人次
			Integer usingPeopleTimes = Integer.valueOf(wk.getUsingPeopleNum())*Integer.valueOf(wk.getUsingDays());
			allUT += usingPeopleTimes;
			wk.setUsingPeopleTimes(String.valueOf(usingPeopleTimes));
			
			//调卷件总数
			String countSql = "select count(ts.id) from t_using_store ts left join t_using_form tf on ts.formid=tf.id where 1=1";
			countSql += tsql2 + "and tf." + rgPeopel + "='"+ receivePeople + "'";;
			String archivesNum = this.reportsService.getUsingPeopleNum(countSql);
			allUA += Integer.valueOf(archivesNum);
			wk.setArchivesNum(archivesNum);
			
			//打印，复印，摘抄，拍摄次数与页数
			if("".equals(using)) {
				wk.setPrintTimes("0");
				wk.setPrintPageNums("0");
				wk.setCopyTimes("0");
				wk.setCopyPageNums("0");
				wk.setExcerptTimes("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoTimes("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			if(!"".equals(dyName)) {
				String storeSql = "select count(*),sum(ts."+dyName+") from t_using_store ts left join t_using_form tf on ts.formid=tf.id";
				storeSql += " where ts."+ using + "='打印'" + tsql2 + "and tf." + rgPeopel + "='"+ receivePeople + "'";
				Map<String,String> storeMap = this.reportsService.getStoreStatistics(storeSql);
				if(storeMap == null) {
					wk.setPrintTimes("0");
					wk.setPrintPageNums("0");
				} else {
					wk.setPrintTimes(storeMap.get("count"));
					wk.setPrintPageNums(storeMap.get("sum"));
				}
			} else {
				wk.setPrintTimes("0");
				wk.setPrintPageNums("0");
			}
			allPT += Integer.valueOf(wk.getPrintTimes());
			allPP += Integer.valueOf(wk.getPrintPageNums());
			if(!"".equals(fyName)) {
				String storeSql = "select count(*),sum(ts."+fyName+") from t_using_store ts left join t_using_form tf on ts.formid=tf.id";
				storeSql += " where ts."+ using + "='复印'" +tsql2 + "and tf." + rgPeopel + "='"+ receivePeople + "'";
				Map<String,String> storeMap = this.reportsService.getStoreStatistics(storeSql);
				if(storeMap == null) {
					wk.setCopyTimes("0");
					wk.setCopyPageNums("0");
				} else {
					wk.setCopyTimes(storeMap.get("count"));
					wk.setCopyPageNums(storeMap.get("sum"));
				}
			} else {
				wk.setCopyTimes("0");
				wk.setCopyPageNums("0");
			}
			allCT += Integer.valueOf(wk.getCopyTimes());
			allCP += Integer.valueOf(wk.getCopyPageNums());
			if(!"".equals(zcName)) {
				String storeSql = "select count(*),sum(ts."+zcName+") from t_using_store ts left join t_using_form tf on ts.formid=tf.id";
				storeSql += " where ts."+ using + "='摘抄'" +tsql2 + "and tf." + rgPeopel + "='"+ receivePeople + "'";
				Map<String,String> storeMap = this.reportsService.getStoreStatistics(storeSql);
				if(storeMap == null) {
					wk.setExcerptTimes("0");
					wk.setExcerptPageNums("0");
				} else {
					wk.setExcerptTimes(storeMap.get("count"));
					wk.setExcerptPageNums(storeMap.get("sum"));
				}
			} else {
				wk.setExcerptTimes("0");
				wk.setExcerptPageNums("0");
			}
			allZT += Integer.valueOf(wk.getExcerptTimes());
			allZP += Integer.valueOf(wk.getExcerptPageNums());
			if(!"".equals(phName)) {
				String storeSql = "select count(*),sum(ts."+phName+") from t_using_store ts left join t_using_form tf on ts.formid=tf.id";
				storeSql += " where ts."+ using + "='拍摄'" +tsql2 + "and tf." + rgPeopel + "='"+ receivePeople + "'";
				Map<String,String> storeMap = this.reportsService.getStoreStatistics(storeSql);
				if(storeMap == null) {
					wk.setPhotoTimes("0");
					wk.setPhotoPageNums("0");
				} else {
					wk.setPhotoTimes(storeMap.get("count"));
					wk.setPhotoPageNums(storeMap.get("sum"));
				}
			} else {
				wk.setPhotoTimes("0");
				wk.setPhotoPageNums("0");
			}
			allPHT += Integer.valueOf(wk.getPhotoTimes());
			allPHP += Integer.valueOf(wk.getPhotoPageNums());
		}
		//总计
		WorkStatistics wkAll = new WorkStatistics();
		wkAll.setReceivePeople("合计");
		wkAll.setUsingPeopleNum(String.valueOf(allUP));
		wkAll.setUsingDays(String.valueOf(allUD));
		wkAll.setUsingPeopleTimes(String.valueOf(allUT));
		wkAll.setArchivesNum(String.valueOf(allUA));
		wkAll.setPrintTimes(String.valueOf(allPT));
		wkAll.setPrintPageNums(String.valueOf(allPP));
		wkAll.setCopyTimes(String.valueOf(allCT));
		wkAll.setCopyPageNums(String.valueOf(allCP));
		wkAll.setExcerptTimes(String.valueOf(allZT));
		wkAll.setExcerptPageNums(String.valueOf(allZP));
		wkAll.setPhotoTimes(String.valueOf(allPHT));
		wkAll.setPhotoPageNums(String.valueOf(allPHP));
		list.add(wkAll);
		return list;
	}
	
	/**
	 * 导出利用工作统计excel
	 * @throws UnsupportedEncodingException
	 */
	public void exportWorkExcel() throws UnsupportedEncodingException {
		String startTime = getRequest().getParameter("startTime");
		if(CommonUtils.isNotBlank(startTime)){
			startTime = URLDecoder.decode(startTime, "utf-8");
		}
		String endTime = getRequest().getParameter("endTime");
		if(CommonUtils.isNotBlank(endTime)){
			endTime = URLDecoder.decode(endTime, "utf-8");
		}
		String receivePeople = getRequest().getParameter("receivePeople");
		if(CommonUtils.isNotBlank(receivePeople)){
			receivePeople = URLDecoder.decode(receivePeople, "utf-8");
		}
		String cdmd = getRequest().getParameter("cdmd");
		if(CommonUtils.isNotBlank(cdmd)){
			cdmd = URLDecoder.decode(cdmd, "utf-8");
		}
		String cyjg = getRequest().getParameter("cyjg");
		if(CommonUtils.isNotBlank(cyjg)){
			cyjg = URLDecoder.decode(cyjg, "utf-8");
		}
		List<WorkStatistics> list = getWorkReportsData(startTime, endTime, receivePeople, cdmd, cyjg);
		String titleName = "接待人,利用人数,利用天数,利用人次,调卷（件）总数,打印件数,打印页数,复印件数,复印页数,摘抄件数,摘抄页数,拍摄件数,拍摄画图数";
		String titleCode = "receivePeople,usingPeopleNum,usingDays,usingPeopleTimes,archivesNum,printTimes,printPageNums,copyTimes,copyPageNums,excerptTimes,excerptPageNums,photoTimes,photoPageNums";
		//向指定的Excel中写入数据
		OutputStream out = null;
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);
		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中 
		try {
			String tabName = "利用工作统计报表";
			Date date = new Date();
			String sd = sdf.format(date);
			//用于写入文件
			wb = ExcelUtils.exportTool(wb, list, titleName, titleCode);
			//设置文件名，加上格式化的时间
			String excelName = tabName + sd + ".xlsx";
			String s_attachment = "attachment; filename=" + excelName;
			//设置字符编码的格式
			s_attachment = new String(s_attachment.getBytes("gb2312"), "ISO8859-1");
			
			getResponse().setHeader("Content-disposition", s_attachment); // 设定输出文件头
			getResponse().setContentType("application/vnd.ms-excel");// 定义输出类型
			getResponse().setContentType("text/plain;charset=UTF-8");
			out = getResponse().getOutputStream();
			wb.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (wb != null) {
				wb.dispose();
			}
		}
	}
	
	/**
	 * 跳转到查档目的分类统计界面
	 * @return
	 */
	public String toAimStatisticsReports() {
		List<WorkStatistics> listPeople = this.reportsService.getAllReceivePeople("");
		getRequest().setAttribute("listPeople", listPeople);
		return "aimStatisticsReportsJsp";
	}
	
	/**
	 * 获取查档目的分类统计数据
	 */
	public void getAimStatisticsReports() {
		JSONObject obj = new JSONObject();
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String rePeople = getRequest().getParameter("receivePeople");
		List<WorkStatistics> list = getAimReportsData(startTime, endTime, rePeople);
		if(list != null && !list.isEmpty()) {
			obj.put("flag", "success");  
			obj.put("msg", "");
			obj.put("data", list);
		} else {
			obj.put("flag", "lack");  
			obj.put("msg", "请联系管理员，检查系统字典查档目的信息及对应元数据"); 
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取查档目的的分类统计数据
	 * @param startTime
	 * @param endTime
	 * @param rePeople
	 * @return
	 */
	public List<WorkStatistics> getAimReportsData(String startTime,String endTime,String rePeople) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		//查询条件
		String queryTerms = "";
		//总利用人数查询内容
		String zUPeople = "";
		//总利用天数查询内容
		String zUDays = "";
		//查档目的代表字段
		String cdmdFielName = "";
		for(int n = 0; n < nodeList.size(); n++) {
			ArchiveNode acNode = nodeList.get(n);
			if("查档目的".equals(acNode.getVc_name())) {
				cdmdFielName = acNode.getVc_fielName();
			} else if("登记日期".equals(acNode.getVc_name())) {
				if(CommonUtils.isNotBlank(startTime)) {
					queryTerms += " and '"+startTime +"'<=tf." + acNode.getVc_fielName();
				}
				if(CommonUtils.isNotBlank(endTime)) {
					queryTerms += " and tf." + acNode.getVc_fielName() + "<='" + endTime + "'";
				}
			} else if("登记人".equals(acNode.getVc_name()) && CommonUtils.isNotBlank(rePeople)) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='"+ rePeople + "'";
			} else if("查档人数".equals(acNode.getVc_name())) {
				zUPeople += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("查档天数".equals(acNode.getVc_name())) {
				zUDays += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("利用单状态".equals(acNode.getVc_name())) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='3'";
			} 
		}
		
		Map<String,String> mapStore = new HashMap<String,String>();
		mapStore.put("conditionSql", " and vc_table = 'T_USING_STORE'");
		List<ArchiveNode> nodeStoreList = archiveUsingService.queryNodeList(mapStore);
		String tjType = "";
		String dyName = "";
		String fyName = "";
		String zcName = "";
		String phName = "";
		String using = "";
		for(int n = 0; n < nodeStoreList.size(); n++) {
			ArchiveNode acNode = nodeStoreList.get(n);
			if("利用方式".equals(acNode.getVc_name())) {
				using = acNode.getVc_fielName();
			} else if("拍摄画图数".equals(acNode.getVc_name())) {
				phName = acNode.getVc_fielName();
			} else if("打印页数".equals(acNode.getVc_name())) {
				dyName = acNode.getVc_fielName();
			} else if("复印页数".equals(acNode.getVc_name())) {
				fyName = acNode.getVc_fielName();
			} else if("摘抄页数".equals(acNode.getVc_name())) {
				zcName = acNode.getVc_fielName();
			} else if("统计类型".equals(acNode.getVc_name())) {
				tjType = acNode.getVc_fielName();
			}
		}
		
		if("".equals(cdmdFielName)) {
			return null;
		}
		List<WorkStatistics> list = this.reportsService.getBorrowCdmdList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		int upt = 0;//usingPeopleTimes（利用人次）
		int bjp = 0;//borrowJuanPages（提供档案（卷次））
		int bjip = 0;//borrowJianPages（提供档案（件））
		int bdp = 0;//borrowDataPages（提供资料（册））
		int acp = 0;//查阅复制总页数
		//打印，复印，摘抄，拍照页数
		int allPP = 0;
		int allCP = 0;
		int allZP = 0;
		int allPHP = 0;
		//查询条件
		String ufSql = " from t_using_form tf where 1=1";
		String joinSql = "from t_using_store ts left join t_using_form tf on ts.formid=tf.id where 1=1";
		for(int i = 0; i < list.size(); i++) {
			WorkStatistics wk = list.get(i);
			String borrowCdmd = wk.getBorrowCdmd();
			String checkSql = "select *" + ufSql + queryTerms + " and tf." + cdmdFielName + "='" + borrowCdmd+"'";
			//查询是否在选中条件下存在查阅记录
			boolean flag = this.reportsService.checkRecord(checkSql);
			if(flag) {
				wk.setUsingPeopleTimes("0");
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			//计算利用人次
			String osql = ufSql + queryTerms + " and tf."+ cdmdFielName + "='" + borrowCdmd + "'";
			if(!"".equals(zUPeople)) {
				String zupsql = zUPeople + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zupsql);
				wk.setUsingPeopleNum(upNums);
			} else {
				wk.setUsingPeopleNum("0");
			}
			if(!"".equals(zUDays)) {
				String zudsql = zUDays + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zudsql);
				wk.setUsingDays(upNums);
			} else {
				wk.setUsingDays("0");
			}
			Integer usingPeopleTimes = Integer.valueOf(wk.getUsingPeopleNum())*Integer.valueOf(wk.getUsingDays());
			upt += usingPeopleTimes;
			wk.setUsingPeopleTimes(String.valueOf(usingPeopleTimes));
			
			if("".equals(tjType)) {
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
			} else {
				//计算总卷，件，资料数量
				String zlsSql = "select sum(case when ts."+ tjType +"='卷' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='件' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='资料' then 1 else 0 end) ";
				String zlSql = zlsSql + joinSql + queryTerms + " and tf."+ cdmdFielName + "='" + borrowCdmd + "'";
				Map<String,String> countMap = this.reportsService.getBorrowDataCount(zlSql);
				if(countMap != null && !countMap.isEmpty()) {
					wk.setBorrowJuan(countMap.get("juanCount"));
					wk.setBorrowJian(countMap.get("jianCount"));
					wk.setBorrowData(countMap.get("dataCount"));
				} else {
					wk.setBorrowJuan("0");
					wk.setBorrowJian("0");
					wk.setBorrowData("0");
				}
				bjp += Integer.valueOf(wk.getBorrowJuan());
				bjip += Integer.valueOf(wk.getBorrowJian());
				bdp += Integer.valueOf(wk.getBorrowData());
			}
			if("".equals(using)) {
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			if(!"".equals(dyName)) {
				String storeSql = "select sum(ts."+dyName+")" + joinSql;
				storeSql += " and ts."+ using + "='打印'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowCdmd + "'";
				String storeDy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPrintPageNums(storeDy);
			} else {
				wk.setPrintPageNums("0");
			}
			allPP += Integer.valueOf(wk.getPrintPageNums());
			if(!"".equals(fyName)) {
				String storeSql = "select sum(ts."+fyName+")"+ joinSql;
				storeSql += " and ts."+ using + "='复印'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowCdmd + "'";
				String storeFy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setCopyPageNums(storeFy);
			} else {
				wk.setCopyPageNums("0");
			}
			allCP += Integer.valueOf(wk.getCopyPageNums());
			if(!"".equals(zcName)) {
				String storeSql = "select sum(ts."+zcName+")"+ joinSql;
				storeSql += " and ts."+ using + "='摘抄'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowCdmd + "'";
				String storeZc = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setExcerptPageNums(storeZc);
			} else {
				wk.setExcerptPageNums("0");
			}
			allZP += Integer.valueOf(wk.getExcerptPageNums());
			if(!"".equals(phName)) {
				String storeSql = "select sum(ts."+phName+")"+ joinSql;
				storeSql += " and ts."+ using + "='拍摄'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowCdmd + "'";
				String storePh = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPhotoPageNums(storePh);
			} else {
				wk.setPhotoPageNums("0");
			}
			allPHP += Integer.valueOf(wk.getPhotoPageNums());
			int oneAcp = Integer.valueOf(wk.getPhotoPageNums())+Integer.valueOf(wk.getExcerptPageNums())+Integer.valueOf(wk.getCopyPageNums())+Integer.valueOf(wk.getPrintPageNums());
			acp += oneAcp;
			wk.setPageCopyTotal(String.valueOf(oneAcp));
		}
		//总计
		WorkStatistics wkAll = new WorkStatistics();
		wkAll.setBorrowCdmd("合计");
		wkAll.setUsingPeopleTimes(String.valueOf(upt));
		wkAll.setBorrowJuan(String.valueOf(bjp));
		wkAll.setBorrowJian(String.valueOf(bjip));
		wkAll.setBorrowData(String.valueOf(bdp));
		wkAll.setPageCopyTotal(String.valueOf(acp));
		wkAll.setPrintPageNums(String.valueOf(allPP));
		wkAll.setCopyPageNums(String.valueOf(allCP));
		wkAll.setExcerptPageNums(String.valueOf(allZP));
		wkAll.setPhotoPageNums(String.valueOf(allPHP));
		list.add(wkAll);
		return list;
	}
	
	/**
	 * 导出档案局档案、资料利用情况统计表（查档目的）
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public void exportAimStatisticsExcel() throws UnsupportedEncodingException {
		String startTime = getRequest().getParameter("startTime");
		if(CommonUtils.isNotBlank(startTime)){
			startTime = URLDecoder.decode(startTime, "utf-8");
		}
		String endTime = getRequest().getParameter("endTime");
		if(CommonUtils.isNotBlank(endTime)){
			endTime = URLDecoder.decode(endTime, "utf-8");
		}
		String receivePeople = getRequest().getParameter("receivePeople");
		if(CommonUtils.isNotBlank(receivePeople)){
			receivePeople = URLDecoder.decode(receivePeople, "utf-8");
		}
		List<WorkStatistics> list = getAimReportsData(startTime, endTime, receivePeople);
		//向指定的Excel中写入数据
		OutputStream out = null;
		//设置最大数据行数
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);
		try {
            String tabName = "档案与资料利用情况统计表（查档目的）";
            Map<String,List<CellModel>> map = new HashMap<String,List<CellModel>>();
            // 设置数据
            List<CellModel> firstRow = new ArrayList<CellModel>();
            CellModel cellModel1 = new CellModel();
            //总占用3行
            Integer cellRow = 3;
            cellModel1.setCellName("查档目的");
            cellModel1.setStartRow(0);
            cellModel1.setEndRow(2);
            cellModel1.setStartColumn(0);
            cellModel1.setEndColumn(0);

            CellModel cellModel2 = new CellModel();
            cellModel2.setCellName("利用人次");
            cellModel2.setStartRow(0);
            cellModel2.setEndRow(2);
            cellModel2.setStartColumn(1);
            cellModel2.setEndColumn(1);

            CellModel cellModel3 = new CellModel();
            cellModel3.setCellName("提供档案（卷次）");
            cellModel3.setStartRow(0);
            cellModel3.setEndRow(2);
            cellModel3.setStartColumn(2);
            cellModel3.setEndColumn(2);

            CellModel cellModel4 = new CellModel();
            cellModel4.setCellName("提供档案（件）");
            cellModel4.setStartRow(0);
            cellModel4.setEndRow(2);
            cellModel4.setStartColumn(3);
            cellModel4.setEndColumn(3);

            CellModel cellModel5 = new CellModel();
            cellModel5.setCellName("提供资料（册）");
            cellModel5.setStartRow(0);
            cellModel5.setEndRow(2);
            cellModel5.setStartColumn(4);
            cellModel5.setEndColumn(4);
            
            CellModel cellModel6 = new CellModel();
            cellModel6.setCellName("复制（页）");
            cellModel6.setStartRow(0);
            cellModel6.setEndRow(0);
            cellModel6.setStartColumn(5);
            cellModel6.setEndColumn(9);
            
            firstRow.add(cellModel1);
            firstRow.add(cellModel2);
            firstRow.add(cellModel3);
            firstRow.add(cellModel4);
            firstRow.add(cellModel5);
            firstRow.add(cellModel6);
            map.put("0", firstRow);

            List<CellModel> secondRow = new ArrayList<CellModel>();
            CellModel cellModel7 = new CellModel();
            cellModel7.setCellName("合计");
            cellModel7.setStartRow(1);
            cellModel7.setEndRow(2);
            cellModel7.setStartColumn(5);
            cellModel7.setEndColumn(5);

            CellModel cellModel8 = new CellModel();
            cellModel8.setCellName("其中");
            cellModel8.setStartRow(1);
            cellModel8.setEndRow(1);
            cellModel8.setStartColumn(6);
            cellModel8.setEndColumn(9);
            
            secondRow.add(cellModel7);
            secondRow.add(cellModel8);
            map.put("1", secondRow);
            
            List<CellModel> thirdRow = new ArrayList<CellModel>();
            CellModel cellModel9 = new CellModel();
            cellModel9.setCellName("打印");
            cellModel9.setStartRow(2);
            cellModel9.setEndRow(2);
            cellModel9.setStartColumn(6);
            cellModel9.setEndColumn(6);
            
            CellModel cellModel10 = new CellModel();
            cellModel10.setCellName("复印");
            cellModel10.setStartRow(2);
            cellModel10.setEndRow(2);
            cellModel10.setStartColumn(7);
            cellModel10.setEndColumn(7);
            
            CellModel cellModel11 = new CellModel();
            cellModel11.setCellName("摘抄");
            cellModel11.setStartRow(2);
            cellModel11.setEndRow(2);
            cellModel11.setStartColumn(8);
            cellModel11.setEndColumn(8);
            
            CellModel cellModel12 = new CellModel();
            cellModel12.setCellName("拍摄");
            cellModel12.setStartRow(2);
            cellModel12.setEndRow(2);
            cellModel12.setStartColumn(9);
            cellModel12.setEndColumn(9);
            
            thirdRow.add(cellModel9);
            thirdRow.add(cellModel10);
            thirdRow.add(cellModel11);
            thirdRow.add(cellModel12);
            map.put("2", thirdRow);

            List<LinkedHashMap> exportData = new ArrayList<LinkedHashMap>();
            for (int i = 0; i < list.size(); i++) {
                WorkStatistics wk = list.get(i);
                LinkedHashMap<String, String> rowPut = new LinkedHashMap<String, String>();
                rowPut.put("1", wk.getBorrowCdmd());
                rowPut.put("2", wk.getUsingPeopleTimes());
                rowPut.put("3", wk.getBorrowJuan());
                rowPut.put("4", wk.getBorrowJian());
                rowPut.put("5", wk.getBorrowData());
                rowPut.put("6", wk.getPageCopyTotal());
                rowPut.put("7", wk.getPrintPageNums());
                rowPut.put("8", wk.getCopyPageNums());
                rowPut.put("9", wk.getExcerptPageNums());
                rowPut.put("10", wk.getPhotoPageNums());

                exportData.add(rowPut);
            }
            // 用于写入文件
            wb = ExcelUtils.createCSVUtil("查档目的",wb, map, cellRow, exportData);
            Date date = new Date();
			String sd = sdf.format(date);
            // 设置文件名，加上格式化的时间
            String excelName =
                    tabName + sd + ".xlsx";
            String s_attachment = "attachment; filename=" + excelName;
            // 设置字符编码的格式
            s_attachment = new String(s_attachment.getBytes("gb2312"), "ISO8859-1");

            getResponse().setHeader("Content-disposition", s_attachment); // 设定输出文件头
            getResponse().setContentType("application/vnd.ms-excel");// 定义输出类型
            getResponse().setContentType("text/plain;charset=UTF-8");
            out = getResponse().getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
            if (wb != null) {
                wb.dispose();
            }
        }
	}
	
	/**
	 * 跳转到其他目的分类统计界面
	 * @return
	 */
	public String toOtherAimStatisticsReports() {
		List<WorkStatistics> listPeople = this.reportsService.getAllReceivePeople("");
		getRequest().setAttribute("listPeople", listPeople);
		return "otherAimStatisticsReportsJsp";
	}
	
	/**
	 * 获取其他目的分类统计数据
	 */
	public void getOtherAimStatisticsReports() {
		JSONObject obj = new JSONObject();
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String rePeople = getRequest().getParameter("receivePeople");
		List<WorkStatistics> list = getOtherAimStatisticsData(startTime, endTime, rePeople);
		if(list != null && !list.isEmpty()) {
			obj.put("flag", "success");  
			obj.put("msg", "");
			obj.put("data", list);
		} else {
			obj.put("flag", "lack");  
			obj.put("msg", "请联系管理员，检查系统字典其他目的信息及对应元数据"); 
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取其他目的
	 * @return
	 */
	public List<WorkStatistics> getOtherAimStatisticsData(String startTime,String endTime,String rePeople) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		//查询条件
		String queryTerms = "";
		//总利用人数查询内容
		String zUPeople = "";
		//总利用天数查询内容
		String zUDays = "";
		//查档目的代表字段
		String cdmdFielName = "";
		for(int n = 0; n < nodeList.size(); n++) {
			ArchiveNode acNode = nodeList.get(n);
			if("其他目的".equals(acNode.getVc_name())) {
				cdmdFielName = acNode.getVc_fielName();
			} else if("登记日期".equals(acNode.getVc_name())) {
				if(CommonUtils.isNotBlank(startTime)) {
					queryTerms += " and '"+startTime +"'<=tf." + acNode.getVc_fielName();
				}
				if(CommonUtils.isNotBlank(endTime)) {
					queryTerms += " and tf." + acNode.getVc_fielName() + "<='" + endTime + "'";
				}
			} else if("登记人".equals(acNode.getVc_name()) && CommonUtils.isNotBlank(rePeople)) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='"+ rePeople + "'";
			} else if("查档人数".equals(acNode.getVc_name())) {
				zUPeople += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("查档天数".equals(acNode.getVc_name())) {
				zUDays += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("利用单状态".equals(acNode.getVc_name())) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='3'";
			} else if("查档目的".equals(acNode.getVc_name())) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='其他'";
			}
		}
		if("".equals(cdmdFielName)) {
			return null;
		}
		List<WorkStatistics> list = this.reportsService.getBorrowOtherMdList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		int upt = 0;//usingPeopleTimes（利用人次）
		int bjp = 0;//borrowJuanPages（提供档案（卷次））
		int bjip = 0;//borrowJianPages（提供档案（件））
		int bdp = 0;//borrowDataPages（提供资料（册））
		int acp = 0;//查阅复制总页数
		//打印，复印，摘抄，拍照页数
		int allPP = 0;
		int allCP = 0;
		int allZP = 0;
		int allPHP = 0;
		//查询条件
		String ufSql = " from t_using_form tf where 1=1";
		String joinSql = "from t_using_store ts left join t_using_form tf on ts.formid=tf.id where 1=1";
		for(int i = 0; i < list.size(); i++) {
			WorkStatistics wk = list.get(i);
			String borrowQtmd = wk.getBorrowQtmd();
			String checkSql = "select *" + ufSql + queryTerms + " and tf." + cdmdFielName + "='" + borrowQtmd+"'";
			//查询是否在选中条件下存在查阅记录
			boolean flag = this.reportsService.checkRecord(checkSql);
			if(flag) {
				wk.setUsingPeopleTimes("0");
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			//计算利用人次
			String osql = ufSql + queryTerms + " and tf."+ cdmdFielName + "='" + borrowQtmd + "'";
			if(!"".equals(zUPeople)) {
				String zupsql = zUPeople + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zupsql);
				wk.setUsingPeopleNum(upNums);
			} else {
				wk.setUsingPeopleNum("0");
			}
			if(!"".equals(zUDays)) {
				String zudsql = zUDays + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zudsql);
				wk.setUsingDays(upNums);
			} else {
				wk.setUsingDays("0");
			}
			Integer usingPeopleTimes = Integer.valueOf(wk.getUsingPeopleNum())*Integer.valueOf(wk.getUsingDays());
			upt += usingPeopleTimes;
			wk.setUsingPeopleTimes(String.valueOf(usingPeopleTimes));
			
			Map<String,String> mapStore = new HashMap<String,String>();
			mapStore.put("conditionSql", " and vc_table = 'T_USING_STORE'");
			List<ArchiveNode> nodeStoreList = archiveUsingService.queryNodeList(mapStore);
			String tjType = "";
			String dyName = "";
			String fyName = "";
			String zcName = "";
			String phName = "";
			String using = "";
			for(int n = 0; n < nodeStoreList.size(); n++) {
				ArchiveNode acNode = nodeStoreList.get(n);
				if("利用方式".equals(acNode.getVc_name())) {
					using = acNode.getVc_fielName();
				} else if("拍摄画图数".equals(acNode.getVc_name())) {
					phName = acNode.getVc_fielName();
				} else if("打印页数".equals(acNode.getVc_name())) {
					dyName = acNode.getVc_fielName();
				} else if("复印页数".equals(acNode.getVc_name())) {
					fyName = acNode.getVc_fielName();
				} else if("摘抄页数".equals(acNode.getVc_name())) {
					zcName = acNode.getVc_fielName();
				} else if("统计类型".equals(acNode.getVc_name())) {
					tjType = acNode.getVc_fielName();
				}
			}
			if("".equals(tjType)) {
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
			} else {
				//计算总卷，件，资料数量
				String zlsSql = "select sum(case when ts."+ tjType +"='卷' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='件' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='资料' then 1 else 0 end) ";
				String zlSql = zlsSql + joinSql + queryTerms + " and tf."+ cdmdFielName + "='" + borrowQtmd + "'";
				Map<String,String> countMap = this.reportsService.getBorrowDataCount(zlSql);
				if(countMap != null && !countMap.isEmpty()) {
					wk.setBorrowJuan(countMap.get("juanCount"));
					wk.setBorrowJian(countMap.get("jianCount"));
					wk.setBorrowData(countMap.get("dataCount"));
				} else {
					wk.setBorrowJuan("0");
					wk.setBorrowJian("0");
					wk.setBorrowData("0");
				}
				bjp += Integer.valueOf(wk.getBorrowJuan());
				bjip += Integer.valueOf(wk.getBorrowJian());
				bdp += Integer.valueOf(wk.getBorrowData());
			}
			if("".equals(using)) {
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			if(!"".equals(dyName)) {
				String storeSql = "select sum(ts."+dyName+")" + joinSql;
				storeSql += " and ts."+ using + "='打印'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowQtmd + "'";
				String storeDy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPrintPageNums(storeDy);
			} else {
				wk.setPrintPageNums("0");
			}
			allPP += Integer.valueOf(wk.getPrintPageNums());
			if(!"".equals(fyName)) {
				String storeSql = "select sum(ts."+fyName+")"+ joinSql;
				storeSql += " and ts."+ using + "='复印'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowQtmd + "'";
				String storeFy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setCopyPageNums(storeFy);
			} else {
				wk.setCopyPageNums("0");
			}
			allCP += Integer.valueOf(wk.getCopyPageNums());
			if(!"".equals(zcName)) {
				String storeSql = "select sum(ts."+zcName+")"+ joinSql;
				storeSql += " and ts."+ using + "='摘抄'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowQtmd + "'";
				String storeZc = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setExcerptPageNums(storeZc);
			} else {
				wk.setExcerptPageNums("0");
			}
			allZP += Integer.valueOf(wk.getExcerptPageNums());
			if(!"".equals(phName)) {
				String storeSql = "select sum(ts."+phName+")"+ joinSql;
				storeSql += " and ts."+ using + "='拍摄'" + queryTerms + "and tf." + cdmdFielName + "='"+ borrowQtmd + "'";
				String storePh = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPhotoPageNums(storePh);
			} else {
				wk.setPhotoPageNums("0");
			}
			allPHP += Integer.valueOf(wk.getPhotoPageNums());
			int oneAcp = Integer.valueOf(wk.getPhotoPageNums())+Integer.valueOf(wk.getExcerptPageNums())+Integer.valueOf(wk.getCopyPageNums())+Integer.valueOf(wk.getPrintPageNums());
			acp += oneAcp;
			wk.setPageCopyTotal(String.valueOf(oneAcp));
		}
		//总计
		WorkStatistics wkAll = new WorkStatistics();
		wkAll.setBorrowQtmd("合计");
		wkAll.setUsingPeopleTimes(String.valueOf(upt));
		wkAll.setBorrowJuan(String.valueOf(bjp));
		wkAll.setBorrowJian(String.valueOf(bjip));
		wkAll.setBorrowData(String.valueOf(bdp));
		wkAll.setPageCopyTotal(String.valueOf(acp));
		wkAll.setPrintPageNums(String.valueOf(allPP));
		wkAll.setCopyPageNums(String.valueOf(allCP));
		wkAll.setExcerptPageNums(String.valueOf(allZP));
		wkAll.setPhotoPageNums(String.valueOf(allPHP));
		list.add(wkAll);
		return list;
	}
	
	/**
	 * 导出档案局档案、资料利用情况统计表（其他目的）
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public void exportOtherAimStatisticsExcel() throws UnsupportedEncodingException {
		String startTime = getRequest().getParameter("startTime");
		if(CommonUtils.isNotBlank(startTime)){
			startTime = URLDecoder.decode(startTime, "utf-8");
		}
		String endTime = getRequest().getParameter("endTime");
		if(CommonUtils.isNotBlank(endTime)){
			endTime = URLDecoder.decode(endTime, "utf-8");
		}
		String receivePeople = getRequest().getParameter("receivePeople");
		if(CommonUtils.isNotBlank(receivePeople)){
			receivePeople = URLDecoder.decode(receivePeople, "utf-8");
		}
		List<WorkStatistics> list = getOtherAimStatisticsData(startTime, endTime, receivePeople);
		//向指定的Excel中写入数据
		OutputStream out = null;
		//设置最大数据行数
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);
		try {
            String tabName = "档案与资料利用情况统计表（其他目的）";
            Map<String,List<CellModel>> map = new HashMap<String,List<CellModel>>();
            // 设置数据
            List<CellModel> firstRow = new ArrayList<CellModel>();
            CellModel cellModel1 = new CellModel();
            //总占用3行
            Integer cellRow = 3;
            cellModel1.setCellName("其他目的");
            cellModel1.setStartRow(0);
            cellModel1.setEndRow(2);
            cellModel1.setStartColumn(0);
            cellModel1.setEndColumn(0);

            CellModel cellModel2 = new CellModel();
            cellModel2.setCellName("利用人次");
            cellModel2.setStartRow(0);
            cellModel2.setEndRow(2);
            cellModel2.setStartColumn(1);
            cellModel2.setEndColumn(1);

            CellModel cellModel3 = new CellModel();
            cellModel3.setCellName("提供档案（卷次）");
            cellModel3.setStartRow(0);
            cellModel3.setEndRow(2);
            cellModel3.setStartColumn(2);
            cellModel3.setEndColumn(2);

            CellModel cellModel4 = new CellModel();
            cellModel4.setCellName("提供档案（件）");
            cellModel4.setStartRow(0);
            cellModel4.setEndRow(2);
            cellModel4.setStartColumn(3);
            cellModel4.setEndColumn(3);

            CellModel cellModel5 = new CellModel();
            cellModel5.setCellName("提供资料（册）");
            cellModel5.setStartRow(0);
            cellModel5.setEndRow(2);
            cellModel5.setStartColumn(4);
            cellModel5.setEndColumn(4);
            
            CellModel cellModel6 = new CellModel();
            cellModel6.setCellName("复制（页）");
            cellModel6.setStartRow(0);
            cellModel6.setEndRow(0);
            cellModel6.setStartColumn(5);
            cellModel6.setEndColumn(9);
            
            firstRow.add(cellModel1);
            firstRow.add(cellModel2);
            firstRow.add(cellModel3);
            firstRow.add(cellModel4);
            firstRow.add(cellModel5);
            firstRow.add(cellModel6);
            map.put("0", firstRow);

            List<CellModel> secondRow = new ArrayList<CellModel>();
            CellModel cellModel7 = new CellModel();
            cellModel7.setCellName("合计");
            cellModel7.setStartRow(1);
            cellModel7.setEndRow(2);
            cellModel7.setStartColumn(5);
            cellModel7.setEndColumn(5);

            CellModel cellModel8 = new CellModel();
            cellModel8.setCellName("其中");
            cellModel8.setStartRow(1);
            cellModel8.setEndRow(1);
            cellModel8.setStartColumn(6);
            cellModel8.setEndColumn(9);
            
            secondRow.add(cellModel7);
            secondRow.add(cellModel8);
            map.put("1", secondRow);
            
            List<CellModel> thirdRow = new ArrayList<CellModel>();
            CellModel cellModel9 = new CellModel();
            cellModel9.setCellName("打印");
            cellModel9.setStartRow(2);
            cellModel9.setEndRow(2);
            cellModel9.setStartColumn(6);
            cellModel9.setEndColumn(6);
            
            CellModel cellModel10 = new CellModel();
            cellModel10.setCellName("复印");
            cellModel10.setStartRow(2);
            cellModel10.setEndRow(2);
            cellModel10.setStartColumn(7);
            cellModel10.setEndColumn(7);
            
            CellModel cellModel11 = new CellModel();
            cellModel11.setCellName("摘抄");
            cellModel11.setStartRow(2);
            cellModel11.setEndRow(2);
            cellModel11.setStartColumn(8);
            cellModel11.setEndColumn(8);
            
            CellModel cellModel12 = new CellModel();
            cellModel12.setCellName("拍摄");
            cellModel12.setStartRow(2);
            cellModel12.setEndRow(2);
            cellModel12.setStartColumn(9);
            cellModel12.setEndColumn(9);
            
            thirdRow.add(cellModel9);
            thirdRow.add(cellModel10);
            thirdRow.add(cellModel11);
            thirdRow.add(cellModel12);
            map.put("2", thirdRow);

            List<LinkedHashMap> exportData = new ArrayList<LinkedHashMap>();
            for (int i = 0; i < list.size(); i++) {
                WorkStatistics wk = list.get(i);
                LinkedHashMap<String, String> rowPut = new LinkedHashMap<String, String>();
                rowPut.put("1", wk.getBorrowQtmd());
                rowPut.put("2", wk.getUsingPeopleTimes());
                rowPut.put("3", wk.getBorrowJuan());
                rowPut.put("4", wk.getBorrowJian());
                rowPut.put("5", wk.getBorrowData());
                rowPut.put("6", wk.getPageCopyTotal());
                rowPut.put("7", wk.getPrintPageNums());
                rowPut.put("8", wk.getCopyPageNums());
                rowPut.put("9", wk.getExcerptPageNums());
                rowPut.put("10", wk.getPhotoPageNums());

                exportData.add(rowPut);
            }
            // 用于写入文件
            wb = ExcelUtils.createCSVUtil("其他目的",wb, map, cellRow, exportData);
            Date date = new Date();
			String sd = sdf.format(date);
            // 设置文件名，加上格式化的时间
            String excelName =
                    tabName + sd + ".xlsx";
            String s_attachment = "attachment; filename=" + excelName;
            // 设置字符编码的格式
            s_attachment = new String(s_attachment.getBytes("gb2312"), "ISO8859-1");

            getResponse().setHeader("Content-disposition", s_attachment); // 设定输出文件头
            getResponse().setContentType("application/vnd.ms-excel");// 定义输出类型
            getResponse().setContentType("text/plain;charset=UTF-8");
            out = getResponse().getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
            if (wb != null) {
                wb.dispose();
            }
        }
	}
	
	/**
	 * 跳转统计主页面
	 * @return
	 */
	public String showMainStatisticsJsp() {
		return "mainStatisticsJsp";
	}
	
	/**
	 * 跳转到档案分类统计界面
	 * @return
	 */
	public String toArchiveStatisticsReports() {
		List<WorkStatistics> listPeople = this.reportsService.getAllReceivePeople("");
		getRequest().setAttribute("listPeople", listPeople);
		return "archiveStatisticsReportsJsp";
	}
	
	/**
	 * 获取按档案分类统计的数据
	 * @param startTime
	 * @param endTime
	 * @param rePeople
	 * @return
	 */
	public List<WorkStatistics> getArchiveStatisticsData(String startTime,String endTime,String rePeople) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		//查询条件
		String queryTerms = "";
		//总利用人数查询内容
		String zUPeople = "";
		//总利用天数查询内容
		String zUDays = "";
		//查档目的代表字段
		String archiveFielName = "";
		for(int n = 0; n < nodeList.size(); n++) {
			ArchiveNode acNode = nodeList.get(n);
			if("登记日期".equals(acNode.getVc_name())) {
				if(CommonUtils.isNotBlank(startTime)) {
					queryTerms += " and '"+startTime +"'<=tf." + acNode.getVc_fielName();
				}
				if(CommonUtils.isNotBlank(endTime)) {
					queryTerms += " and tf." + acNode.getVc_fielName() + "<='" + endTime + "'";
				}
			} else if("登记人".equals(acNode.getVc_name()) && CommonUtils.isNotBlank(rePeople)) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='"+ rePeople + "'";
			} else if("查档人数".equals(acNode.getVc_name())) {
				zUPeople += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("查档天数".equals(acNode.getVc_name())) {
				zUDays += "select sum(tf." + acNode.getVc_fielName() + ")";
			} else if("利用单状态".equals(acNode.getVc_name())) {
				queryTerms += " and tf." + acNode.getVc_fielName() + "='3'";
			}
		}
		
		Map<String,String> mapStore = new HashMap<String,String>();
		mapStore.put("conditionSql", " and vc_table = 'T_USING_STORE'");
		List<ArchiveNode> nodeStoreList = archiveUsingService.queryNodeList(mapStore);
		String tjType = "";
		String dyName = "";
		String fyName = "";
		String zcName = "";
		String phName = "";
		String using = "";
		for(int n = 0; n < nodeStoreList.size(); n++) {
			ArchiveNode acNode = nodeStoreList.get(n);
			if("利用方式".equals(acNode.getVc_name())) {
				using = acNode.getVc_fielName();
			} else if("拍摄画图数".equals(acNode.getVc_name())) {
				phName = acNode.getVc_fielName();
			} else if("打印页数".equals(acNode.getVc_name())) {
				dyName = acNode.getVc_fielName();
			} else if("复印页数".equals(acNode.getVc_name())) {
				fyName = acNode.getVc_fielName();
			} else if("摘抄页数".equals(acNode.getVc_name())) {
				zcName = acNode.getVc_fielName();
			} else if("统计类型".equals(acNode.getVc_name())) {
				tjType = acNode.getVc_fielName();
			} else if("档案门类".equals(acNode.getVc_name())) {
				archiveFielName = acNode.getVc_fielName();
			}
		}
		if("".equals(archiveFielName)) {
			return null;
		}
		List<WorkStatistics> list = this.reportsService.getBorrowArchiveList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		int upt = 0;//usingPeopleTimes（利用人次）
		int bjp = 0;//borrowJuanPages（提供档案（卷次））
		int bjip = 0;//borrowJianPages（提供档案（件））
		int bdp = 0;//borrowDataPages（提供资料（册））
		int acp = 0;//查阅复制总页数
		//打印，复印，摘抄，拍照页数
		int allPP = 0;
		int allCP = 0;
		int allZP = 0;
		int allPHP = 0;
		//查询条件
		String joinSql = "from t_using_store ts left join t_using_form tf on ts.formid=tf.id where 1=1";
		String joinCheckSql = " from t_using_form tf left join t_using_store ts on ts.formid=tf.id where 1=1" + " and ts." + archiveFielName + " is not null";
		for(int i = 0; i < list.size(); i++) {
			WorkStatistics wk = list.get(i);
			String bArchive = wk.getBorrowArchive();
			String checkSql = "select *" + joinCheckSql + queryTerms + " and ts." + archiveFielName + "='" + bArchive+"'";
			//查询是否在选中条件下存在查阅记录
			boolean flag = this.reportsService.checkRecord(checkSql);
			if(flag) {
				wk.setUsingPeopleTimes("0");
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			//计算利用人次
			String osql = joinCheckSql + queryTerms + " and ts."+ archiveFielName + "='" + bArchive + "'";
			if(!"".equals(zUPeople)) {
				String zupsql = zUPeople + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zupsql);
				wk.setUsingPeopleNum(upNums);
			} else {
				wk.setUsingPeopleNum("0");
			}
			if(!"".equals(zUDays)) {
				String zudsql = zUDays + osql;
				String upNums = this.reportsService.getUsingPeopleNum(zudsql);
				wk.setUsingDays(upNums);
			} else {
				wk.setUsingDays("0");
			}
			Integer usingPeopleTimes = Integer.valueOf(wk.getUsingPeopleNum())*Integer.valueOf(wk.getUsingDays());
			upt += usingPeopleTimes;
			wk.setUsingPeopleTimes(String.valueOf(usingPeopleTimes));
			
			if("".equals(tjType)) {
				wk.setBorrowJuan("0");
				wk.setBorrowJian("0");
				wk.setBorrowData("0");
			} else {
				//计算总卷，件，资料数量
				String zlsSql = "select sum(case when ts."+ tjType +"='卷' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='件' then 1 else 0 end),";
				zlsSql += "sum(case when ts."+ tjType +"='资料' then 1 else 0 end) ";
				String zlSql = zlsSql + joinSql + queryTerms + " and ts."+ archiveFielName + "='" + bArchive + "'";
				Map<String,String> countMap = this.reportsService.getBorrowDataCount(zlSql);
				if(countMap != null && !countMap.isEmpty()) {
					wk.setBorrowJuan(countMap.get("juanCount"));
					wk.setBorrowJian(countMap.get("jianCount"));
					wk.setBorrowData(countMap.get("dataCount"));
				} else {
					wk.setBorrowJuan("0");
					wk.setBorrowJian("0");
					wk.setBorrowData("0");
				}
				bjp += Integer.valueOf(wk.getBorrowJuan());
				bjip += Integer.valueOf(wk.getBorrowJian());
				bdp += Integer.valueOf(wk.getBorrowData());
			}
			if("".equals(using)) {
				wk.setPageCopyTotal("0");
				wk.setPrintPageNums("0");
				wk.setCopyPageNums("0");
				wk.setExcerptPageNums("0");
				wk.setPhotoPageNums("0");
				continue;
			}
			if(!"".equals(dyName)) {
				String storeSql = "select sum(ts."+dyName+")" + joinSql;
				storeSql += " and ts."+ using + "='打印'" + queryTerms + "and ts." + archiveFielName + "='"+ bArchive + "'";
				String storeDy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPrintPageNums(storeDy);
			} else {
				wk.setPrintPageNums("0");
			}
			allPP += Integer.valueOf(wk.getPrintPageNums());
			if(!"".equals(fyName)) {
				String storeSql = "select sum(ts."+fyName+")"+ joinSql;
				storeSql += " and ts."+ using + "='复印'" + queryTerms + "and ts." + archiveFielName + "='"+ bArchive + "'";
				String storeFy = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setCopyPageNums(storeFy);
			} else {
				wk.setCopyPageNums("0");
			}
			allCP += Integer.valueOf(wk.getCopyPageNums());
			if(!"".equals(zcName)) {
				String storeSql = "select sum(ts."+zcName+")"+ joinSql;
				storeSql += " and ts."+ using + "='摘抄'" + queryTerms + "and ts." + archiveFielName + "='"+ bArchive + "'";
				String storeZc = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setExcerptPageNums(storeZc);
			} else {
				wk.setExcerptPageNums("0");
			}
			allZP += Integer.valueOf(wk.getExcerptPageNums());
			if(!"".equals(phName)) {
				String storeSql = "select sum(ts."+phName+")"+ joinSql;
				storeSql += " and ts."+ using + "='拍摄'" + queryTerms + "and ts." + archiveFielName + "='"+ bArchive + "'";
				String storePh = this.reportsService.getUsingPeopleNum(storeSql);
				wk.setPhotoPageNums(storePh);
			} else {
				wk.setPhotoPageNums("0");
			}
			allPHP += Integer.valueOf(wk.getPhotoPageNums());
			int oneAcp = Integer.valueOf(wk.getPhotoPageNums())+Integer.valueOf(wk.getExcerptPageNums())+Integer.valueOf(wk.getCopyPageNums())+Integer.valueOf(wk.getPrintPageNums());
			acp += oneAcp;
			wk.setPageCopyTotal(String.valueOf(oneAcp));
		}
		//总计
		WorkStatistics wkAll = new WorkStatistics();
		wkAll.setBorrowArchive("合计");
		wkAll.setUsingPeopleTimes(String.valueOf(upt));
		wkAll.setBorrowJuan(String.valueOf(bjp));
		wkAll.setBorrowJian(String.valueOf(bjip));
		wkAll.setBorrowData(String.valueOf(bdp));
		wkAll.setPageCopyTotal(String.valueOf(acp));
		wkAll.setPrintPageNums(String.valueOf(allPP));
		wkAll.setCopyPageNums(String.valueOf(allCP));
		wkAll.setExcerptPageNums(String.valueOf(allZP));
		wkAll.setPhotoPageNums(String.valueOf(allPHP));
		list.add(wkAll);
		return list;
	}
	
	/**
	 * 获取其他目的分类统计数据
	 */
	public void getArchiveStatisticsReports() {
		JSONObject obj = new JSONObject();
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String rePeople = getRequest().getParameter("receivePeople");
		List<WorkStatistics> list = getArchiveStatisticsData(startTime, endTime, rePeople);
		if(list != null && !list.isEmpty()) {
			obj.put("flag", "success");  
			obj.put("msg", "");
			obj.put("data", list);
		} else {
			obj.put("flag", "lack");  
			obj.put("msg", "请联系管理员，检查系统字典档案门类字段信息及对应元数据"); 
		}
		toPage(obj.toString());
	}
	
	/**
	 * 导出档案局档案、资料利用情况统计表（档案门类）
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public void exportArchiveStatisticsExcel() throws UnsupportedEncodingException {
		String startTime = getRequest().getParameter("startTime");
		if(CommonUtils.isNotBlank(startTime)){
			startTime = URLDecoder.decode(startTime, "utf-8");
		}
		String endTime = getRequest().getParameter("endTime");
		if(CommonUtils.isNotBlank(endTime)){
			endTime = URLDecoder.decode(endTime, "utf-8");
		}
		String receivePeople = getRequest().getParameter("receivePeople");
		if(CommonUtils.isNotBlank(receivePeople)){
			receivePeople = URLDecoder.decode(receivePeople, "utf-8");
		}
		List<WorkStatistics> list = getArchiveStatisticsData(startTime, endTime, receivePeople);
		//向指定的Excel中写入数据
		OutputStream out = null;
		//设置最大数据行数
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);
		try {
            String tabName = "档案与资料利用情况统计表（档案门类）";
            Map<String,List<CellModel>> map = new HashMap<String,List<CellModel>>();
            // 设置数据
            List<CellModel> firstRow = new ArrayList<CellModel>();
            CellModel cellModel1 = new CellModel();
            //总占用3行
            Integer cellRow = 3;
            cellModel1.setCellName("档案门类");
            cellModel1.setStartRow(0);
            cellModel1.setEndRow(2);
            cellModel1.setStartColumn(0);
            cellModel1.setEndColumn(0);

            CellModel cellModel2 = new CellModel();
            cellModel2.setCellName("利用人次");
            cellModel2.setStartRow(0);
            cellModel2.setEndRow(2);
            cellModel2.setStartColumn(1);
            cellModel2.setEndColumn(1);

            CellModel cellModel3 = new CellModel();
            cellModel3.setCellName("提供档案（卷次）");
            cellModel3.setStartRow(0);
            cellModel3.setEndRow(2);
            cellModel3.setStartColumn(2);
            cellModel3.setEndColumn(2);

            CellModel cellModel4 = new CellModel();
            cellModel4.setCellName("提供档案（件）");
            cellModel4.setStartRow(0);
            cellModel4.setEndRow(2);
            cellModel4.setStartColumn(3);
            cellModel4.setEndColumn(3);

            CellModel cellModel5 = new CellModel();
            cellModel5.setCellName("提供资料（册）");
            cellModel5.setStartRow(0);
            cellModel5.setEndRow(2);
            cellModel5.setStartColumn(4);
            cellModel5.setEndColumn(4);
            
            CellModel cellModel6 = new CellModel();
            cellModel6.setCellName("复制（页）");
            cellModel6.setStartRow(0);
            cellModel6.setEndRow(0);
            cellModel6.setStartColumn(5);
            cellModel6.setEndColumn(9);
            
            firstRow.add(cellModel1);
            firstRow.add(cellModel2);
            firstRow.add(cellModel3);
            firstRow.add(cellModel4);
            firstRow.add(cellModel5);
            firstRow.add(cellModel6);
            map.put("0", firstRow);

            List<CellModel> secondRow = new ArrayList<CellModel>();
            CellModel cellModel7 = new CellModel();
            cellModel7.setCellName("合计");
            cellModel7.setStartRow(1);
            cellModel7.setEndRow(2);
            cellModel7.setStartColumn(5);
            cellModel7.setEndColumn(5);

            CellModel cellModel8 = new CellModel();
            cellModel8.setCellName("其中");
            cellModel8.setStartRow(1);
            cellModel8.setEndRow(1);
            cellModel8.setStartColumn(6);
            cellModel8.setEndColumn(9);
            
            secondRow.add(cellModel7);
            secondRow.add(cellModel8);
            map.put("1", secondRow);
            
            List<CellModel> thirdRow = new ArrayList<CellModel>();
            CellModel cellModel9 = new CellModel();
            cellModel9.setCellName("打印");
            cellModel9.setStartRow(2);
            cellModel9.setEndRow(2);
            cellModel9.setStartColumn(6);
            cellModel9.setEndColumn(6);
            
            CellModel cellModel10 = new CellModel();
            cellModel10.setCellName("复印");
            cellModel10.setStartRow(2);
            cellModel10.setEndRow(2);
            cellModel10.setStartColumn(7);
            cellModel10.setEndColumn(7);
            
            CellModel cellModel11 = new CellModel();
            cellModel11.setCellName("摘抄");
            cellModel11.setStartRow(2);
            cellModel11.setEndRow(2);
            cellModel11.setStartColumn(8);
            cellModel11.setEndColumn(8);
            
            CellModel cellModel12 = new CellModel();
            cellModel12.setCellName("拍摄");
            cellModel12.setStartRow(2);
            cellModel12.setEndRow(2);
            cellModel12.setStartColumn(9);
            cellModel12.setEndColumn(9);
            
            thirdRow.add(cellModel9);
            thirdRow.add(cellModel10);
            thirdRow.add(cellModel11);
            thirdRow.add(cellModel12);
            map.put("2", thirdRow);

            List<LinkedHashMap> exportData = new ArrayList<LinkedHashMap>();
            for (int i = 0; i < list.size(); i++) {
                WorkStatistics wk = list.get(i);
                LinkedHashMap<String, String> rowPut = new LinkedHashMap<String, String>();
                rowPut.put("1", wk.getBorrowArchive());
                rowPut.put("2", wk.getUsingPeopleTimes());
                rowPut.put("3", wk.getBorrowJuan());
                rowPut.put("4", wk.getBorrowJian());
                rowPut.put("5", wk.getBorrowData());
                rowPut.put("6", wk.getPageCopyTotal());
                rowPut.put("7", wk.getPrintPageNums());
                rowPut.put("8", wk.getCopyPageNums());
                rowPut.put("9", wk.getExcerptPageNums());
                rowPut.put("10", wk.getPhotoPageNums());

                exportData.add(rowPut);
            }
            // 用于写入文件
            wb = ExcelUtils.createCSVUtil("档案门类",wb, map, cellRow, exportData);
            Date date = new Date();
			String sd = sdf.format(date);
            // 设置文件名，加上格式化的时间
            String excelName =
                    tabName + sd + ".xlsx";
            String s_attachment = "attachment; filename=" + excelName;
            // 设置字符编码的格式
            s_attachment = new String(s_attachment.getBytes("gb2312"), "ISO8859-1");

            getResponse().setHeader("Content-disposition", s_attachment); // 设定输出文件头
            getResponse().setContentType("application/vnd.ms-excel");// 定义输出类型
            getResponse().setContentType("text/plain;charset=UTF-8");
            out = getResponse().getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
            if (wb != null) {
                wb.dispose();
            }
        }
	}
	
	/**
	 * 跳转到载体类型统计页面
	 * @return
	 */
	public String toformatsStatisticsReports() {
		List<WorkStatistics> listPeople = this.reportsService.getAllReceivePeople("");
		getRequest().setAttribute("listPeople", listPeople);
		return "formatsStatisticsReportsJsp";
	}
	
	/**
	 * 获取载体类型数据
	 * @param startTime
	 * @param endTime
	 * @param rePeople
	 * @return
	 */
	public List<WorkStatistics> getFormatsStatisticsData(String startTime,String endTime,String rePeople) {
		List<WorkStatistics> list = getArchiveStatisticsData(startTime, endTime, rePeople);
		if( list == null || list.isEmpty()) {
			return null;
		}
		List<WorkStatistics> newList = new ArrayList<WorkStatistics>();
		//利用人次，打印页数，复印页数，摘抄页数，拍照页数，提供档案（卷），提供档案（件），提供档案（册），合计页数，载体类型
		WorkStatistics wkp = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","照片");
		WorkStatistics wkv = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","录音");
		WorkStatistics wkd = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","录像");
		WorkStatistics wkt = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","实物");
		WorkStatistics wke = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","电子");
		WorkStatistics wka = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","纸质");
		WorkStatistics wkh = new WorkStatistics("0", "0", "0", "0","0", "0", "0", "0", "0","合计");
		for(int v = 0; v < list.size(); v++) {
			WorkStatistics wk = list.get(v);
			String borrowArchive = wk.getBorrowArchive();
			if("合计".equals(borrowArchive)) {
				continue;
			}
			if("照片档案".equals(borrowArchive)) {
				
				wkp.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkp.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkp.setBorrowJuan(String.valueOf(Integer.valueOf(wkp.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkp.setBorrowJian(String.valueOf(Integer.valueOf(wkp.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkp.setBorrowData(String.valueOf(Integer.valueOf(wkp.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkp.setPageCopyTotal(String.valueOf(Integer.valueOf(wkp.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkp.setPrintPageNums(String.valueOf(Integer.valueOf(wkp.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkp.setCopyPageNums(String.valueOf(Integer.valueOf(wkp.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkp.setExcerptPageNums(String.valueOf(Integer.valueOf(wkp.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkp.setPhotoPageNums(String.valueOf(Integer.valueOf(wkp.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			} else if("录音档案".equals(borrowArchive)) {
				
				wkv.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkv.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkv.setBorrowJuan(String.valueOf(Integer.valueOf(wkv.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkv.setBorrowJian(String.valueOf(Integer.valueOf(wkv.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkv.setBorrowData(String.valueOf(Integer.valueOf(wkv.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkv.setPageCopyTotal(String.valueOf(Integer.valueOf(wkv.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkv.setPrintPageNums(String.valueOf(Integer.valueOf(wkv.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkv.setCopyPageNums(String.valueOf(Integer.valueOf(wkv.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkv.setExcerptPageNums(String.valueOf(Integer.valueOf(wkv.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkv.setPhotoPageNums(String.valueOf(Integer.valueOf(wkv.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			} else if("录像档案".equals(borrowArchive)) {
				
				wkd.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkd.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkd.setBorrowJuan(String.valueOf(Integer.valueOf(wkd.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkd.setBorrowJian(String.valueOf(Integer.valueOf(wkd.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkd.setBorrowData(String.valueOf(Integer.valueOf(wkd.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkd.setPageCopyTotal(String.valueOf(Integer.valueOf(wkd.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkd.setPrintPageNums(String.valueOf(Integer.valueOf(wkd.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkd.setCopyPageNums(String.valueOf(Integer.valueOf(wkd.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkd.setExcerptPageNums(String.valueOf(Integer.valueOf(wkd.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkd.setPhotoPageNums(String.valueOf(Integer.valueOf(wkd.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			} else if("实物档案".equals(borrowArchive)) {
				
				wkt.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkt.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkt.setBorrowJuan(String.valueOf(Integer.valueOf(wkt.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkt.setBorrowJian(String.valueOf(Integer.valueOf(wkt.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkt.setBorrowData(String.valueOf(Integer.valueOf(wkt.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkt.setPageCopyTotal(String.valueOf(Integer.valueOf(wkt.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkt.setPrintPageNums(String.valueOf(Integer.valueOf(wkt.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkt.setCopyPageNums(String.valueOf(Integer.valueOf(wkt.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkt.setExcerptPageNums(String.valueOf(Integer.valueOf(wkt.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkt.setPhotoPageNums(String.valueOf(Integer.valueOf(wkt.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			} else if("电子档案".equals(borrowArchive) || "光盘档案".equals(borrowArchive)) {
				
				wke.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wke.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wke.setBorrowJuan(String.valueOf(Integer.valueOf(wke.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wke.setBorrowJian(String.valueOf(Integer.valueOf(wke.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wke.setBorrowData(String.valueOf(Integer.valueOf(wke.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wke.setPageCopyTotal(String.valueOf(Integer.valueOf(wke.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wke.setPrintPageNums(String.valueOf(Integer.valueOf(wke.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wke.setCopyPageNums(String.valueOf(Integer.valueOf(wke.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wke.setExcerptPageNums(String.valueOf(Integer.valueOf(wke.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wke.setPhotoPageNums(String.valueOf(Integer.valueOf(wke.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			} else {
				
				wka.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wka.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wka.setBorrowJuan(String.valueOf(Integer.valueOf(wka.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wka.setBorrowJian(String.valueOf(Integer.valueOf(wka.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wka.setBorrowData(String.valueOf(Integer.valueOf(wka.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wka.setPageCopyTotal(String.valueOf(Integer.valueOf(wka.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wka.setPrintPageNums(String.valueOf(Integer.valueOf(wka.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wka.setCopyPageNums(String.valueOf(Integer.valueOf(wka.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wka.setExcerptPageNums(String.valueOf(Integer.valueOf(wka.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wka.setPhotoPageNums(String.valueOf(Integer.valueOf(wka.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
				
				wkh.setUsingPeopleTimes(String.valueOf(Integer.valueOf(wkh.getUsingPeopleTimes()) + Integer.valueOf(wk.getUsingPeopleTimes())));
				wkh.setBorrowJuan(String.valueOf(Integer.valueOf(wkh.getBorrowJuan()) + Integer.valueOf(wk.getBorrowJuan())));
				wkh.setBorrowJian(String.valueOf(Integer.valueOf(wkh.getBorrowJian()) + Integer.valueOf(wk.getBorrowJian())));
				wkh.setBorrowData(String.valueOf(Integer.valueOf(wkh.getBorrowData()) + Integer.valueOf(wk.getBorrowData())));
				wkh.setPageCopyTotal(String.valueOf(Integer.valueOf(wkh.getPageCopyTotal()) + Integer.valueOf(wk.getPageCopyTotal())));
				wkh.setPrintPageNums(String.valueOf(Integer.valueOf(wkh.getPrintPageNums()) + Integer.valueOf(wk.getPrintPageNums())));
				wkh.setCopyPageNums(String.valueOf(Integer.valueOf(wkh.getCopyPageNums()) + Integer.valueOf(wk.getCopyPageNums())));
				wkh.setExcerptPageNums(String.valueOf(Integer.valueOf(wkh.getExcerptPageNums()) + Integer.valueOf(wk.getExcerptPageNums())));
				wkh.setPhotoPageNums(String.valueOf(Integer.valueOf(wkh.getPhotoPageNums()) + Integer.valueOf(wk.getPhotoPageNums())));
			
			}
		}
		newList.add(wkp);
		newList.add(wkv);
		newList.add(wkd);
		newList.add(wkt);
		newList.add(wke);
		newList.add(wka);
		newList.add(wkh);
		return newList;
	}
	
	/**
	 * 获取按载体分类的分类统计数据
	 */
	public void getformatsStatisticsReports() {
		JSONObject obj = new JSONObject();
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String rePeople = getRequest().getParameter("receivePeople");
		List<WorkStatistics> list = getFormatsStatisticsData(startTime, endTime, rePeople);
		if(list != null && !list.isEmpty()) {
			obj.put("flag", "success");  
			obj.put("msg", "");
			obj.put("data", list);
		} else {
			obj.put("flag", "lack");  
			obj.put("msg", "请联系管理员，检查系统字典档案门类字段信息及对应元数据"); 
		}
		toPage(obj.toString());
	}
	
	/**
	 * 导出档案局档案、资料利用情况统计表（档案门类）
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public void exportFormatsStatisticsExcel() throws UnsupportedEncodingException {
		String startTime = getRequest().getParameter("startTime");
		if(CommonUtils.isNotBlank(startTime)){
			startTime = URLDecoder.decode(startTime, "utf-8");
		}
		String endTime = getRequest().getParameter("endTime");
		if(CommonUtils.isNotBlank(endTime)){
			endTime = URLDecoder.decode(endTime, "utf-8");
		}
		String receivePeople = getRequest().getParameter("receivePeople");
		if(CommonUtils.isNotBlank(receivePeople)){
			receivePeople = URLDecoder.decode(receivePeople, "utf-8");
		}
		List<WorkStatistics> list = getFormatsStatisticsData(startTime, endTime, receivePeople);
		//向指定的Excel中写入数据
		OutputStream out = null;
		//设置最大数据行数
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);
		try {
            String tabName = "档案与资料利用情况统计表（载体类型）";
            Map<String,List<CellModel>> map = new HashMap<String,List<CellModel>>();
            // 设置数据
            List<CellModel> firstRow = new ArrayList<CellModel>();
            CellModel cellModel1 = new CellModel();
            //总占用3行
            Integer cellRow = 3;
            cellModel1.setCellName("载体类型");
            cellModel1.setStartRow(0);
            cellModel1.setEndRow(2);
            cellModel1.setStartColumn(0);
            cellModel1.setEndColumn(0);

            CellModel cellModel2 = new CellModel();
            cellModel2.setCellName("利用人次");
            cellModel2.setStartRow(0);
            cellModel2.setEndRow(2);
            cellModel2.setStartColumn(1);
            cellModel2.setEndColumn(1);

            CellModel cellModel3 = new CellModel();
            cellModel3.setCellName("提供档案（卷次）");
            cellModel3.setStartRow(0);
            cellModel3.setEndRow(2);
            cellModel3.setStartColumn(2);
            cellModel3.setEndColumn(2);

            CellModel cellModel4 = new CellModel();
            cellModel4.setCellName("提供档案（件）");
            cellModel4.setStartRow(0);
            cellModel4.setEndRow(2);
            cellModel4.setStartColumn(3);
            cellModel4.setEndColumn(3);

            CellModel cellModel5 = new CellModel();
            cellModel5.setCellName("提供资料（册）");
            cellModel5.setStartRow(0);
            cellModel5.setEndRow(2);
            cellModel5.setStartColumn(4);
            cellModel5.setEndColumn(4);
            
            CellModel cellModel6 = new CellModel();
            cellModel6.setCellName("复制（页）");
            cellModel6.setStartRow(0);
            cellModel6.setEndRow(0);
            cellModel6.setStartColumn(5);
            cellModel6.setEndColumn(9);
            
            firstRow.add(cellModel1);
            firstRow.add(cellModel2);
            firstRow.add(cellModel3);
            firstRow.add(cellModel4);
            firstRow.add(cellModel5);
            firstRow.add(cellModel6);
            map.put("0", firstRow);

            List<CellModel> secondRow = new ArrayList<CellModel>();
            CellModel cellModel7 = new CellModel();
            cellModel7.setCellName("合计");
            cellModel7.setStartRow(1);
            cellModel7.setEndRow(2);
            cellModel7.setStartColumn(5);
            cellModel7.setEndColumn(5);

            CellModel cellModel8 = new CellModel();
            cellModel8.setCellName("其中");
            cellModel8.setStartRow(1);
            cellModel8.setEndRow(1);
            cellModel8.setStartColumn(6);
            cellModel8.setEndColumn(9);
            
            secondRow.add(cellModel7);
            secondRow.add(cellModel8);
            map.put("1", secondRow);
            
            List<CellModel> thirdRow = new ArrayList<CellModel>();
            CellModel cellModel9 = new CellModel();
            cellModel9.setCellName("打印");
            cellModel9.setStartRow(2);
            cellModel9.setEndRow(2);
            cellModel9.setStartColumn(6);
            cellModel9.setEndColumn(6);
            
            CellModel cellModel10 = new CellModel();
            cellModel10.setCellName("复印");
            cellModel10.setStartRow(2);
            cellModel10.setEndRow(2);
            cellModel10.setStartColumn(7);
            cellModel10.setEndColumn(7);
            
            CellModel cellModel11 = new CellModel();
            cellModel11.setCellName("摘抄");
            cellModel11.setStartRow(2);
            cellModel11.setEndRow(2);
            cellModel11.setStartColumn(8);
            cellModel11.setEndColumn(8);
            
            CellModel cellModel12 = new CellModel();
            cellModel12.setCellName("拍摄");
            cellModel12.setStartRow(2);
            cellModel12.setEndRow(2);
            cellModel12.setStartColumn(9);
            cellModel12.setEndColumn(9);
            
            thirdRow.add(cellModel9);
            thirdRow.add(cellModel10);
            thirdRow.add(cellModel11);
            thirdRow.add(cellModel12);
            map.put("2", thirdRow);

            List<LinkedHashMap> exportData = new ArrayList<LinkedHashMap>();
            for (int i = 0; i < list.size(); i++) {
                WorkStatistics wk = list.get(i);
                LinkedHashMap<String, String> rowPut = new LinkedHashMap<String, String>();
                rowPut.put("1", wk.getFormats());
                rowPut.put("2", wk.getUsingPeopleTimes());
                rowPut.put("3", wk.getBorrowJuan());
                rowPut.put("4", wk.getBorrowJian());
                rowPut.put("5", wk.getBorrowData());
                rowPut.put("6", wk.getPageCopyTotal());
                rowPut.put("7", wk.getPrintPageNums());
                rowPut.put("8", wk.getCopyPageNums());
                rowPut.put("9", wk.getExcerptPageNums());
                rowPut.put("10", wk.getPhotoPageNums());

                exportData.add(rowPut);
            }
            // 用于写入文件
            wb = ExcelUtils.createCSVUtil("载体类型",wb, map, cellRow, exportData);
            Date date = new Date();
			String sd = sdf.format(date);
            // 设置文件名，加上格式化的时间
            String excelName =
                    tabName + sd + ".xlsx";
            String s_attachment = "attachment; filename=" + excelName;
            // 设置字符编码的格式
            s_attachment = new String(s_attachment.getBytes("gb2312"), "ISO8859-1");

            getResponse().setHeader("Content-disposition", s_attachment); // 设定输出文件头
            getResponse().setContentType("application/vnd.ms-excel");// 定义输出类型
            getResponse().setContentType("text/plain;charset=UTF-8");
            out = getResponse().getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
            if (wb != null) {
                wb.dispose();
            }
        }
	}
}
