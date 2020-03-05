/**
 * 文件名称:EWorkFlowServiceImpl.java
 * 作者:zhuxc<br>
 * 创建时间:2014-1-16 下午01:44:28
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.functions.workflow.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.util.ChineseToPinyin;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.functions.workflow.dao.EWorkFlowDao;
import cn.com.trueway.functions.workflow.service.EWorkFlowService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.dao.ReplayDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.Replay;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;
import cn.com.trueway.workflow.set.dao.DepartmentDao;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.util.PdfToPngImageUtil;

/**
 * 描述： 对EWorkFlowServiceImpl进行描述
 * 作者：zhuxc
 * 创建时间：2014-1-16 下午01:44:28
 */
public class EWorkFlowServiceImpl implements EWorkFlowService {
	
	private EWorkFlowDao eworkflowDao;
	
	private DepartmentDao departmentDao;
	
	private TableInfoDao tableInfoDao;
	
	private PendingDao pendingDao;
	
	private ReplayDao replayDao;
	
	
	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	private EmployeeDao employeeDao;
	
	private ItemDao itemDao;

	public EWorkFlowDao getEworkflowDao() {
		return eworkflowDao;
	}


	public void setEworkflowDao(EWorkFlowDao eworkflowDao) {
		this.eworkflowDao = eworkflowDao;
	}


	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}


	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}


	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}


	public ItemDao getItemDao() {
		return itemDao;
	}


	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public PendingDao getPendingDao() {
		return pendingDao;
	}

	public void setPendingDao(PendingDao pendingDao) {
		this.pendingDao = pendingDao;
	}
	
	public ReplayDao getReplayDao() {
		return replayDao;
	}

	public void setReplayDao(ReplayDao replayDao) {
		this.replayDao = replayDao;
	}

	/**
	 * @param userId
	 * @param column
	 * @param pagesize
	 * @param url
	 * @param callBack
	 * @param itemIds
	 * @return
	 */
	@Override
		/**
		 * web端得到待办列表(新) 2014-1-13
		 */
	public String getTodoForPortalNew(String userId, String column, String pagesize, String url, String callback,String itemIds) {
		if(column==null||column.trim().length()==0) column = "1";
		if(pagesize==null||pagesize.trim().length()==0) pagesize = "5";
		List<Pending> pendList = eworkflowDao.getPendListOfMobile(userId,0,Integer.parseInt(column),Integer.parseInt(pagesize),"",itemIds);
//			StringBuilder sb =new StringBuilder();
		 String outString =";"+callback+"({";
	    	outString+="data:[],";
			outString+="css:'',";
			outString+="js:";
			outString+="''";
			outString+=",template:";
			//获取总数
			int size = eworkflowDao.getPendListCount(userId,0,"",itemIds);
			if(pendList!=null && pendList.size()>0){
			outString+="'<div style=\"background:url(widgets/images/daiban.png) no-repeat 0 0;padding:5px;overflow:hidden;*zoom:1;\">";
			/*if(pendList.size()>99){
				outString+="<div ><span style=\"width:19px;height:19px;line-height:19px;background:url(widgets/images/quan.png) no-repeat;color:#ffffff;display:block;text-align:center;float:right;font-size:11px;\">99+</span></div>";
			}else{*/
			   outString+="<div ><span style=\"width:19px;height:19px;line-height:19px;background:url(widgets/images/quan.png) no-repeat;color:#ffffff;display:block;text-align:center;float:right;font-size:11px;\">"+size+"</span></div>";
			//}
			//outString+="'<div >"+remindList.size()+"</div>";
			outString+="<div style=\"padding-top:20px\"><ul class=\"ul1\">";
			if(pendList!=null && pendList.size()>0){
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				for(int i = 0;i<pendList.size();i++){
					if(i>=(Integer.valueOf(pagesize)-1)){
	        			break;
	        		}
					Pending pend = pendList.get(i);
					String link = url+"/table_openPendingNoCloseForm.do?processId="+pend.getWf_process_uid()+"&isDb=true&itemId="+pend.getItem_id()+"&formId="+pend.getForm_id()+"&isPortal=1&isCanPush=1";
					String time = new SimpleDateFormat("yyyy-MM-dd").format(pend.getApply_time());
					String processTitle = pend.getProcess_title()==null?"":pend.getProcess_title().indexOf(",")==-1?pend.getProcess_title():pend.getProcess_title().split(",")[0];
					outString+="<li><span style=\"float:right;\" class=\"timer\">["+time+"]</span><a style=\"display:block;float:left;width:70%;overflow:hidden;text-overflow:ellipsis;\" href=\""+link+"\">"+(processTitle)+"</a></li>";
				}
			}
			outString+="</ul></div></div>'";
			}else{
				
				outString+="'<div style=\"padding-top:20px;text-align:center\"><img src=\"widgets/images/zwsj.png\"></div>'";	
			}
			outString+="})";
		return outString;
	}
	/**
	 * @param userId
	 * @param column
	 * @param pagesize
	 * @param url
	 * @param callBack
	 * @param itemIds
	 * @return
	 */
	@Override
		/**
		 * web端得到待办列表(新) 2014-1-13
		 */
	public String getTodo4WebNew(String userId, String column, String pagesize, String url, String callback,String itemIds) {
		if(column==null||column.trim().length()==0) column = "1";
		if(pagesize==null||pagesize.trim().length()==0) pagesize = "5";
		List<Pending> pendList = eworkflowDao.getTodo4WebNew(userId,0,Integer.parseInt(column),Integer.parseInt(pagesize),"",itemIds);
		
		String outString =";"+callback+"({";
		outString+="data:[";
		if(pendList!=null && pendList.size()>0){
			for(int i = 0;i<pendList.size();i++){
        		if(i>=(Integer.valueOf(pagesize)-1)){
        			break;
        		}
	        	Pending pend = pendList.get(i);
	
	    		//http://localhost:8080/functions/day_toDay.do?year=2013&month=8&day=14&state1=0&state2=0&is_update=0
	    		String link = url+"/table_openPendingNoCloseForm.do?processId="+pend.getWf_process_uid()+"&isDb=true&itemId="+pend.getItem_id()+"&formId="+pend.getForm_id()+"&isPortal=1&isCanPush=1";
	    		String time = new SimpleDateFormat("yyyy-MM-dd").format(pend.getApply_time());
	    		String content ="";
	    		if(pend!=null&&pend.getProcess_title()!=null){
	    			String processTitle =pend.getProcess_title();
		    		 //content = (processTitle.indexOf(",")==-1?processTitle.replace("'", "’").replace("\"", "“").replace("\r\n", ""):processTitle.split(",")[0].replace("'", "’").replace("\"", "“").replace("\r\n", ""));
	    			content = replaceQuotes(processTitle);
	    		}
	    		 
	    		outString+="{\"content\":\""+content+"\",";
			    outString+="\"time\":\""+time+"\",";
			    outString+="\"url\":\""+link+"\"},";
			}
			outString = outString.substring(0, outString.length()-1);
		}
		outString+="]})";
		
		return outString;
	}
	
	public List getTodo4Breeze(String userId, String column, String pagesize, String url, String callback, String itemIds)
	  {
	    if ((column == null) || (column.trim().length() == 0)) column = "1";
	    if ((pagesize == null) || (pagesize.trim().length() == 0)) pagesize = "5";
	    List pendList = this.eworkflowDao.getPendListOfMobile(userId, 0, Integer.valueOf(Integer.parseInt(column)), Integer.valueOf(Integer.parseInt(pagesize)), "", itemIds);

	    return pendList;
	  }
	public List getHaveDone4Breeze(String userId, String column, String pagesize, String url, String callback, String itemIds,String timetype){
		    if ((column == null) || (column.trim().length() == 0)) column = "1";
		    if ((pagesize == null) || (pagesize.trim().length() == 0)) pagesize = "5";
		    List pendList = this.eworkflowDao.getHaveDone4Breeze(userId, 0, Integer.valueOf(Integer.parseInt(column)), Integer.valueOf(Integer.parseInt(pagesize)), "", itemIds ,timetype);
		    return pendList;
	}
	
	
	//将英文的单或者双引号换算成中文的
	public String replaceQuotes(String str){
		String returnStr = str.replaceAll("\"(.*?)\"", "“$1”").replaceAll("'(.*?)'", "‘$1’");
		returnStr = (returnStr.indexOf(",")==-1?returnStr.replace("'", "’").replace("\"", "“").replace("\r\n", ""):returnStr.split(",")[0].replace("'", "’").replace("\"", "“").replace("\r\n", ""));
		return returnStr;
	}
	
	@Override
	public int searchCountByStatus(String conditionSql, String status,
			List<FieldSelectCondition> selects) {
		return eworkflowDao.searchCountByStatus(conditionSql,status,selects);
	}

	@Override
	public String searchDataByStatus(String conditionSql, int column,
			Integer pagesize, String status, List<FieldSelectCondition> selects,String serverUrl,String callback) {
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		int count = 0;
		List<Object[]> list = new ArrayList<Object[]>();
		List<Map<String, String>> outList = new ArrayList<Map<String, String>>();
		if(status.equals("6")){
			count = eworkflowDao.getCountOfOutOfDate(conditionSql,"");
			List<Object[]> instanceIds = eworkflowDao.getOutOfDateList(conditionSql, column, pagesize);
			for (Object[] o : instanceIds) {
				String instanceId = null != o[0] ? o[0].toString() : "";
				Map<String, String> map = new HashMap<String, String>();
				map.put("jdqxdate", null != o[2] ? o[2].toString() : "");
				List<Object[]> tmlist = eworkflowDao.getOutOfDateListByInstanceId(conditionSql, instanceId);
				if(null != tmlist && tmlist.size()>0){
					Object[] object = tmlist.get(0);
					map.put("processUid", null != object[0] ? object[0].toString() : "");
					map.put("title", null != object[1] ? object[1].toString() : "");
					map.put("instanceId", instanceId);
					map.put("itemName", null != object[5] ? object[5].toString() : "");
					map.put("applyTime", null != object[8] ? object[8].toString() : "");
					map.put("status", null != object[9] ? object[9].toString() : "");
					map.put("shipInstanceId", null != object[10] ? object[10].toString() : "");
					String username = "",userId = "",fromUserName="",depName="";
					for (Object[] obj : tmlist) {
						userId += (null != obj[3] ? obj[3].toString() : " ") +",";//办件持有人id
						username += (null != obj[4] ? obj[4].toString() : " ") +",";//办件持有人姓名
						String uname = null != obj[6] ? obj[6].toString() : "";
						if(StringUtils.isNotBlank(uname) && fromUserName.indexOf(uname) == -1){
							fromUserName += uname + ",";
						}
						String udep = null != obj[7] ? obj[7].toString() : "";
						if(StringUtils.isNotBlank(udep) && depName.indexOf(udep) == -1){
							depName += udep + ",";
						}
					}
					if(StringUtils.isNotBlank(userId)){
						userId = userId.substring(0, userId.length()-1);
					}
					map.put("userId", userId);
					
					if(StringUtils.isNotBlank(username)){
						username = username.substring(0, username.length()-1);
					}
					map.put("username", username);
					
					if(StringUtils.isNotBlank(fromUserName)){
						fromUserName = fromUserName.substring(0, fromUserName.length()-1);
					}
					map.put("fromUserName", fromUserName);
					
					if(StringUtils.isNotBlank(depName)){
						depName = depName.substring(0, depName.length()-1);
					}
					map.put("depName", depName);
					outList.add(map);
				}
			}
		}else{
			count = eworkflowDao.searchCountByStatus(conditionSql,status,selects);
			list =  eworkflowDao.serachDataByStatus(conditionSql,column, pagesize,status,selects);
		}
		JSONObject obj = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("listclass", "");
		data.put("num", count);
		data.put("type", "works");
		JSONArray array = new JSONArray();
		JSONObject pend = null;
		if(status.equals("6")){
			for (Map<String, String> map : outList) {
				String state = map.get("status");
				String shipInstanceId = map.get("shipInstanceId");
				String imgUrl = "";
				String html = "";
				String dbImgUrl = "";
				String dbHtml = "";
				if(StringUtils.isNotBlank(state)){
					if(state.equals("3")){
						imgUrl=serverUrl+"/images/redLight.png";
					}else if(state.equals("4")){
						imgUrl=serverUrl+"/images/yellowLight.png";
					}else{
						imgUrl=serverUrl+"/images/greenLight.png";
					}
					html="<img src=\""+imgUrl+"\" />";
				}
				
				if(StringUtils.isNotBlank(shipInstanceId)){
					dbImgUrl = serverUrl+"/images/ycb.jpg";
				}else{
					dbImgUrl = serverUrl+"/images/wcb.jpg";
				}
				dbHtml="<img src=\""+dbImgUrl+"\" />";
				pend = new JSONObject();
				pend.put("img", html);
				pend.put("dbImg", dbHtml);
				String title = map.get("title");
				if (CommonUtil.stringNotNULL(title)) {
					if (title.contains("*") && title.indexOf("*") == 36) {
						title = title.substring(37);
					}
				}
				pend.put("title",title);		//标题
				String link=serverUrl+"/table_openOverForm.do?processId="+map.get("processUid")+"&instanceId="+map.get("instanceId")+"&t="+new Date()+"&status="+status;
				pend.put("link", link);		//访问地址
				pend.put("cate", map.get("itemName"));
				pend.put("date", map.get("jdqxdate"));
				pend.put("isnew", "");	//1、表示是
				pend.put("username", map.get("username"));
				pend.put("deptname", map.get("depName"));
				
				array.add(pend);
			}
		}else{
			for(Object[] objs: list){
				pend = new JSONObject();
				String title = objs[3] != null ? objs[3].toString().replace(
						"\r\n", "") : "";
				if (CommonUtil.stringNotNULL(title)) {
					if (title.contains("*") && title.indexOf("*") == 36) {
						title = title.substring(37);
					}
				}
				pend.put("title", title);	//标题
				String link = "";
				String imgPath = "";
				if(status.equals("0") || status.equals("3") ){
					String isChild = objs[12]==null?"":objs[12].toString();
					pend.put("urgency", objs[17]==null?"":objs[17].toString());
					if(isChild.equals("1")){
						link =  serverUrl+"/table_openPendingForm.do?processId="+objs[0]+"&isDb=true&itemId="+objs[9]+"&formId="+objs[10]+"&isCy="+objs[11]+"&imgPath="+imgPath+"&t="+new Date();
					}else{
						link = serverUrl+"/table_openPendingForm.do?processId="+objs[0]+"&isDb=true&itemId="+objs[9]+"&formId="+objs[10]+"&isCanPush="+objs[11]+"&imgPath="+imgPath+"&t="+new Date();
					}
				}else if(status.equals("6")){
					link=serverUrl+"/table_openOverForm.do?processId="+objs[0]+"&instanceId="+objs[1]+"&t="+new Date()+"&status="+status;
				}else{
					link=serverUrl+"/table_openOverForm.do?favourite="+objs[14]+"&processId="+objs[0]+"&instanceId="+objs[1]+"&itemId="+objs[9]+"&formId="+objs[10]+"&t="+new Date()+"&status="+status;
				}
				pend.put("link", link);		//访问地址
				pend.put("cate", objs[7]);
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				try {
					pend.put("date", sdf.format(sdf1.parse(objs[6].toString())));
				} catch (ParseException e) {
					pend.put("date", objs[6]);
				}
				pend.put("isnew", objs[15]== null ?"1":"0");	//1、表示是
				pend.put("username", objs[5]);
				pend.put("deptname", objs[4]);
				pend.put("imgPath", imgPath);
				pend.put("processId", objs[0]);
				if(null != objs[7]){
					pend.put("className", ChineseToPinyin.getPYIndexStr(objs[7].toString(), true));
				}else{
					pend.put("className", "default");
				}
				if(status.equals("1") || status.equals("4")){
					String state = objs[8] != null ? objs[8].toString():"";
					String userId = objs[21] != null ? objs[21].toString():"";
					Map<String, String> map = new HashMap<String, String>();
					map.put("nodeId", objs[18] != null ? objs[18].toString():"");
					map.put("processId", objs[0]!=null?objs[0].toString():"");
					map.put("workflowId", objs[2]!=null?objs[2].toString():"");
					map.put("instanceId", objs[1]!=null?objs[1].toString():"");
					map.put("stepIndex", objs[11]!=null?objs[11].toString():"");
					map.put("isManyInstance", objs[19] != null ? objs[19].toString():"");
					map.put("isback", objs[17] != null ? objs[17].toString():"");
					String isback = pendingDao.setBackStatus(map, userId);
					if(StringUtils.isNotBlank(state) && StringUtils.isNotBlank(isback) && state.equals("0") &&(isback.equals("3")||isback.equals("5"))){
						pend.put("isRecycle", "1");
					}else{
						pend.put("isRecycle", "0");
					}
				}else{
					pend.put("isRecycle", "0");
				}
				if(status.equals("1") ||status.equals("2") || status.equals("4") || status.equals("5")){
					pend.put("urgency", objs[20]==null?"":objs[20].toString());
				}
				pend.put("stepIndex", objs[11]);
				pend.put("instanceId", objs[1]);
				array.add(pend);
			}
		}
		data.put("content", array.toString());
		obj.put("data", data.toString());
		String content = callback+"("+obj.toString()+")";
		return content;
	}

	@Override
	public String getMySearchJson(String conditionSql, int column, Integer pagesize,
			String status, List<FieldSelectCondition> selects,
			String serverUrl, String callback) {
		return "";
	
	}


	@Override
	public String getMySearchFormJson(String userId,String status,String action) {
		return "";
	}

	@Override
	public String getJsonByMaxUrl(String conditionSql, int column,
			Integer pagesize, String status,
			List<FieldSelectCondition> selects, String serverUrl,
			String callback) {
		return "";
	}


	@Override
	public JSONArray getMySearchObj(String conditionSql, int column,
			Integer pagesize, String status,
			List<FieldSelectCondition> selects, String serverUrl) {
		JSONArray ja = new JSONArray();
		List<Object[]> list =  eworkflowDao.serachDataByStatus(conditionSql,column, pagesize,status,selects);
		if(list != null && list.size() >0){
			for(int i = 0 , size = list.size(); i < size ; i ++){
				JSONObject jo = new JSONObject();
				Object[] objs = list.get(i);
				jo.put("processId", objs[0]);
				jo.put("instanceId", objs[1]);
				jo.put("workflowId", objs[2]);
				jo.put("title", objs[3].toString().replace("\r\n", ""));
				jo.put("commitDept", objs[4]);
				jo.put("commitUser", objs[5]);
				jo.put("commitTime", objs[6]);
				jo.put("itemType", objs[7]);
				String isRead = objs[14]== null ?"0":"1";;
				jo.put("isRead", isRead);
				jo.put("emergency", "");
				if(status.equals("0") || status.equals("3") ){
					String isChild = objs[12]==null?"":objs[12].toString();
					if(isChild.equals("1")){
						jo.put("link", serverUrl+"/table_openPendingForm.do?processId="+objs[0]+"&isDb=true&itemId="+objs[9]+"&formId="+objs[10]+"&isCy="+objs[11]+"&t="+new Date());
					}else{
						jo.put("link", serverUrl+"/table_openPendingForm.do?processId="+objs[0]+"&isDb=true&itemId="+objs[9]+"&formId="+objs[10]+"&isCanPush="+objs[11]+"&t="+new Date());
					}
				}else{
					jo.put("link", serverUrl+"/table_openOverForm.do?favourite="+objs[14]+"&processId="+objs[0]+"&itemId="+objs[9]+"&formId="+objs[10]+"&t="+new Date());
				}
				String stat =  objs[8] == null ?"0": objs[8].toString();
				jo.put("status", stat);
				ja.add(jo);
			}
		}else{
		}
		return ja;
	}

	public String getJsonReceiveDataByStatus(String callback,String userId,Integer pageIndex, Integer pageSize,String status, Map<String,String> map,String serverUrl,boolean jrdb,List<WfItem> itemlist) {/*
		String template = SystemParamConfigUtil.getParamValueByParam("template");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = eworkflowDao.getReceiveListByStatusCount(userId, status, map);
		int count=tableInfoDao.getDoFileReceiveCount(userId, Integer.valueOf(status), map);
		String outString =callback+"({\"num\":\""+count+"\",\"data\":\"";
		List<DoFileReceive> list =  eworkflowDao.getReceiveListByStatus(userId, pageIndex, pageSize, status, map);
		List<DoFileReceive> list = tableInfoDao.getDoFileReceiveList(userId, pageIndex, pageSize, Integer.valueOf(status), map);
		if(list != null && list.size() >0){
			outString+="<div class='operable_header'><a href='##' class='operate_box'></a></div><ul class='event-list'>";
			for(int i = 0 , size = list.size(); i < size ; i ++){
				DoFileReceive dofile=list.get(i);
				if("0".equals(status)){
					//未接收
					String receiveType=dofile.getReceiveType();
					if("2".equals(receiveType)){
						String link=serverUrl+"/table_showRebackerForm.do?receiveId="+dofile.getId()+"&jrdb="+jrdb+"&status=0&t="+new Date();
						outString+=template.replace("p_type", "["+dofile.getLwdw()+"]").replace("p_title", dofile.getTitle()).replace("##", link).replace("p_time",format.format(dofile.getFwsj()));
					}else{
						String link=serverUrl+"/table_openReceiveForm.do?receiveId="+dofile.getId()+"&jrdb="+jrdb+"&status=0&t="+new Date();
						outString+=template.replace("p_type", "["+dofile.getLwdw()+"]").replace("p_title", dofile.getTitle()).replace("##", link).replace("p_time",format.format(dofile.getFwsj()));
					}
				}else if("1".equals(status)){
					//已收公文
					if("2".equals(dofile.getReceiveType()) && 1==dofile.getIsReback()){
						String link=serverUrl+"/table_showRebackerForm.do?receiveId="+dofile.getId()+"&status=1&t="+new Date();
						outString+=template.replace("p_type", "["+dofile.getItemName()+"]").replace("p_title", dofile.getTitle()).replace("##", link).replace("p_time",format.format(dofile.getRecDate()));
					}else{
						String link=serverUrl+"/table_openReceiveForm.do?path="+dofile.getPdfpath()+"&receiveId="+dofile.getId()+"&status=1&t="+new Date();
						outString+=template.replace("p_type", "["+dofile.getItemName()+"]").replace("p_title", dofile.getTitle()).replace("##", link).replace("p_time",format.format(dofile.getRecDate()));
					}
				}
			}
			outString+="</ul>";
		}else{
			outString+="";	
		}
		outString+="\"})";
		return outString;
	*/
		return "";
	}

	public String getJsonToBeSealed(String callback, String deptids,
			Integer pageIndex, Integer pageSize,String conditionSql,String serverUrl) {
		return "";
	}


	@Override
	public String getJsonToDealList(String callback, String userId,
			Integer pageIndex, Integer pageSize, String conditionSql,
			String serverUrl) {
		return "";
	}
	
}
