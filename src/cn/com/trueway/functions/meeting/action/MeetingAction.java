package cn.com.trueway.functions.meeting.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.functions.meeting.entity.Chry;
import cn.com.trueway.functions.meeting.entity.Chry4Out;
import cn.com.trueway.functions.meeting.entity.Hyqj;
import cn.com.trueway.functions.meeting.entity.Hytz;
import cn.com.trueway.functions.meeting.entity.Sgtjhytz;
import cn.com.trueway.functions.meeting.service.MeetingService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;

public class MeetingAction extends BaseAction{
	private static final long serialVersionUID = -3033111073105696265L;
	private static Logger logger = Logger.getLogger(MeetingAction.class);
	
	private MeetingService meetingService;
	private DepartmentService departmentService;
	
	public MeetingService getMeetingService() {
		return meetingService;
	}
	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	/**
	 * 得到我的 会议登记 列表
	 * @return
	 */
	public String getMyRegisterList(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		
		String itemid=getRequest().getParameter("itemid");
		String workflowid=getRequest().getParameter("workflowid");
		
		String hyqjitemid=getRequest().getParameter("hyqjitemid");
		String hyqjworkflowid=getRequest().getParameter("hyqjworkflowid");
		//查询条件
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		String status = getRequest().getParameter("status")==null?"":getRequest().getParameter("status");
		
		logger.info("--itemid----"+itemid+"----workflowid----"+workflowid+"----searchDateFrom----"+searchDateFrom+"---searchDateTo---"+searchDateTo+"----status--"+status);
		String conditionSql = "";
//		if(CommonUtil.stringNotNULL(status)){
//			conditionSql = " and p.status like '%"+status+"%'";
//		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		List<Hytz> list = null;
		
		if(!itemid.equals("")&&itemid!=null){
			int count = meetingService.getCountOfMyRegister( conditionSql,emp.getEmployeeGuid(),itemid,searchDateFrom,searchDateTo);
			Paging.setPagingParams(getRequest(), pageSize, count);
			
			logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
			list= meetingService.getMyRegisterList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,itemid,searchDateFrom,searchDateTo);
				
		}
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("workflowid", workflowid);
		getRequest().setAttribute("hyqjitemid", hyqjitemid);
		getRequest().setAttribute("hyqjworkflowid", hyqjworkflowid);
		return "myRegisterList";
	}
	
	/**
	 * @return
	 */
	public String getChmdList(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
	
		String itemid=getRequest().getParameter("itemid");
		String workflowid=getRequest().getParameter("workflowid");
		//查询条件
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		String status = getRequest().getParameter("status")==null?"":getRequest().getParameter("status");
		
		logger.info("--itemid----"+itemid+"----workflowid----"+workflowid+"----searchDateFrom----"+searchDateFrom+"---searchDateTo---"+searchDateTo+"----status--"+status);
		String conditionSql = "";
	//	if(CommonUtil.stringNotNULL(status)){
	//		conditionSql = " and p.status like '%"+status+"%'";
	//	}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		List<Hytz> list = null;
		
		if(!itemid.equals("")&&itemid!=null){
			int count = meetingService.getCountOfMyChmd( conditionSql,emp.getEmployeeGuid(),itemid,searchDateFrom,searchDateTo);
			Paging.setPagingParams(getRequest(), pageSize, count);
			
			logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
			list= meetingService.getMyChmdList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,itemid,searchDateFrom,searchDateTo);
				
		}
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("workflowid", workflowid);
		return "chmdList";
	}

	/**
	 * @return
	 */
	public String getHyqjList(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		
		String itemid=getRequest().getParameter("itemid");
		String workflowid=getRequest().getParameter("workflowid");
	
		//查询条件
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		String status = getRequest().getParameter("status")==null?"":getRequest().getParameter("status");
		
		logger.info("--itemid----"+itemid+"----workflowid----"+workflowid+"----searchDateFrom----"+searchDateFrom+"---searchDateTo---"+searchDateTo+"----status--"+status);
		String conditionSql = "";
	//	if(CommonUtil.stringNotNULL(status)){
	//		conditionSql = " and p.status like '%"+status+"%'";
	//	}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		List<Hytz> list = null;
		
		if(!itemid.equals("")&&itemid!=null){
			int count = meetingService.getCountOfMyHyqj( conditionSql,emp.getEmployeeGuid(),itemid,searchDateFrom,searchDateTo);
			Paging.setPagingParams(getRequest(), pageSize, count);
			
			logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
			list= meetingService.getMyHyqjList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,itemid,searchDateFrom,searchDateTo);
				
		}
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("workflowid", workflowid);
		return "chmdList";
	}
	
	public String getChmd(){
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String instanceId = getRequest().getParameter("instanceId");
		String yxry = Utils.clobToString(meetingService.getPerson(instanceId,"PERSONNAME"));
		String yxryString = "";
		if(StringUtils.isNotBlank(yxry)){
			yxryString = yxry.split("\\*")[0];
		}
		String[] yxrylist = yxryString.split(",");
		String eploeIds =  "(";
		for(String eploe : yxrylist){
			eploeIds+="'"+eploe+"',";
		}
		eploeIds+="'')";
		
		
		
		
		List<Chry4Out> list =  null;
		int count = meetingService.getCountOfChmd(eploeIds);
		Paging.setPagingParams(getRequest(), pageSize, count);
		
		logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
		list= meetingService.getChmdList4Out(Paging.pageIndex, Paging.pageSize,eploeIds);
		
		//list 处理
		//Map<String , List<Chry4Out> >  resMap = new HashMap<String , List<Chry4Out>>();
		
		Set set = new HashSet();
		List depIdList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {  
			Chry4Out element = (Chry4Out) iter.next();  
	         if (set.add(element.getDEPARTMENT_GUID())) {
	        	 depIdList.add(element.getDEPARTMENT_GUID());  
	         } 
	      }  
		//list.get(j).setDEPARTMENT_NAME("");
		
		for(int j = 0 ;j<depIdList.size() ;j++){
			String depid =  (String) depIdList.get(j);
			Boolean ispreplace = false;
			for(int i = 0 ;i<list.size() ;i++){
				if(depid.equals(list.get(i).getDEPARTMENT_GUID())){
					if(ispreplace){
						list.get(i).setDEPARTMENT_NAME("");
						list.get(i).setSUPERDEPARTMENT_NAME("");
					}
					ispreplace = true;
				}
			}
		
			
		}
		//获得填报指定参会人员的 id
		List needTbchmd = null;
		List useridList = meetingService.getMastUserid(instanceId);
		if(useridList.size()>0){
			String queryUserids = "('";
			for(int j = 0 ;j<useridList.size() ;j++){
				queryUserids=queryUserids+useridList.get(j)+",";
			}
			queryUserids=queryUserids+"')";
			queryUserids=queryUserids.replace(",", "','");
			
			needTbchmd=meetingService.getSupDepIdByUserid(queryUserids);
		}
		
		//如果分发下去 填报参会名单  需要处理 显示未填报参会名单的 单位
		List<Chry4Out> needlist =  new ArrayList<Chry4Out>();
		if(needTbchmd!=null){
	       for(int k = 0 ;k<needTbchmd.size() ;k++){
	    	  Object[] supDep = (Object[]) needTbchmd.get(k);
	    	  String supDepId = (String) supDep[1];
	    	  boolean isInsert = false;
	    	   for(Chry4Out chry : list){
	    		   if(supDepId.equals(chry.getSUPERIOR_GUID())){
	    			   needlist.add(chry);
	    			   isInsert=true;
	    		   }
	    		  
	    	   }
	    	   //如果 单位下没有填写 参会名单 那么 就插个空值 站位
	    	   if(isInsert==false){
    			   Chry4Out nonChry = new Chry4Out();
    			   nonChry.setDEPARTMENT_GUID(supDepId);
    			   nonChry.setDEPARTMENT_NAME((String)supDep[2]);
    			   needlist.add(nonChry);
    		   }
	       }
		}
		if(needlist.size()>0){
			getRequest().setAttribute("list", needlist);
		}else{
	     	getRequest().setAttribute("list", list);
		}
		getRequest().setAttribute("instanceId",instanceId);
		
		return "chmd";
	}
	
	public String getHyqj(){
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		String instanceId = getRequest().getParameter("instanceId");
		
		List<Hyqj> list =  null;
		
		int count = meetingService.getCountOfHyqj(instanceId);
		Paging.setPagingParams(getRequest(), pageSize, count);
		
		logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
		list= meetingService.getHyqjList(Paging.pageIndex, Paging.pageSize,instanceId);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("instanceId",instanceId);
		
		return "hyqj";
	}
	
	public void getHyList() throws IOException{
//        String callback = getRequest().getParameter("callback");
//        String conditionSql = "";//查询条件
//        Map<String,String> map = new HashMap<String, String>();
//        Employee employee = (Employee) getRequest().getSession().getAttribute("publisher");
//		String userid = employee.getEmployeeGuid();
		
//        List<Sgtjhytz> allhyList = meetingService.getAllHy(conditionSql);
//		StringBuffer hystring = new StringBuffer();
//		hystring.append(";"+callback+"([");
//		if(allhyList.size()!=0){
//			for(Sgtjhytz hy : allhyList){
//				hystring.append("{'hyname':'").append(hy.getMeetingname()).append("','hyinsid':'").append(hy.getInstanceid()).append("'},");
//			}
//			hystring.delete(hystring.length()-1, hystring.length());
//		}
//		hystring.append("]);");
//    	HttpServletResponse response =getResponse();
//		response.setContentType("text/plain;UTF-8");
//		PrintWriter out=response.getWriter();
//		out.print(hystring.toString());
	}
	
	/**
	 * 会议请假工作流中的会议列表页面
	 * @return
	 */
	public String getHyListForQj(){
		JSONObject jsonObject  = getJSONObject();
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		
		if(jsonObject != null && !"".equals(jsonObject)){
			searchDateFrom = jsonObject.getString("searchDateFrom");
			searchDateTo = jsonObject.getString("searchDateTo");
		}else{
			searchDateFrom = getRequest().getParameter("searchDateFrom");
			searchDateTo = getRequest().getParameter("searchDateTo");
		}
		String conditionSql = "";//查询条件
		if(CommonUtil.stringNotNULL(searchDateFrom) ){
			conditionSql += " and to_date(t.newtime,'YYYY-MM-dd HH24:mi') >= to_date('"+searchDateFrom+"','YYYY-MM-dd HH24:mi')";
		}
		if(CommonUtil.stringNotNULL(searchDateTo) ){
			conditionSql += " and to_date(t.newtime,'YYYY-MM-dd HH24:mi') <= to_date('"+searchDateTo+"','YYYY-MM-dd HH24:mi')";
		}
		int count = meetingService.getAllHyCount(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Sgtjhytz> allhyList = meetingService.getAllHy(Paging.pageIndex, Paging.pageSize,conditionSql);
		
		getRequest().setAttribute("allhyList", allhyList);
		
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		
		return "hylistforqj";
	}
	
	/**
	 * 会议请假工作流中的会议人员页面
	 * @return
	 */
//	public String getHyry(){
//		String conditionSql = "";//查询条件
//		List<Sgtjhytz> allhyList = meetingService.getAllHy(conditionSql);
//		
//		getRequest().setAttribute("allhyList", allhyList);
//		
//		return "hylistforqj";
//	}
	
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
	
	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String getChryByInsId() throws IOException{
		JSONObject jsonObject  = getJSONObject();
		String callback = "";
		String instanceId = "";
		if(jsonObject != null && !"".equals(jsonObject)){
			instanceId = jsonObject.getString("hymc").split("\\*")[0];
			try {
				callback = jsonObject.getString("callback");
			} catch (Exception e) {
			}
		}else{
			callback = getRequest().getParameter("callback");
			instanceId = getRequest().getParameter("hymc").split("\\*")[0];
		}
		
		String yxry = Utils.clobToString(meetingService.getPerson(instanceId,"PERSONNAME"));
		String dxry = Utils.clobToString(meetingService.getPerson(instanceId,"DXRYNAME"));
		
		List<String> qjry = meetingService.getQjry(instanceId);
		
		String yxryString = "";
		String dxryString = "";
		String yxryidString = "";
		String dxryidString = "";
		if(StringUtils.isNotBlank(yxry)){
			yxryString = yxry.split("\\*")[1];
		}
		if(StringUtils.isNotBlank(dxry)){
			dxryString = dxry.split("\\*")[1];
		}
		if(StringUtils.isNotBlank(yxry)){
			yxryidString = yxry.split("\\*")[0];
		}
		if(StringUtils.isNotBlank(dxry)){
			dxryidString = dxry.split("\\*")[0];
		}
		
		String[] yxrylist = null;
		String[] dxrylist = null;
		String[] yxryidlist = null;
		String[] dxryidlist = null;
		
		if(StringUtils.isNotBlank(yxryString)){
			yxrylist = yxryString.split(",");
		}
		if(StringUtils.isNotBlank(dxryString)){
			dxrylist = dxryString.split(",");
		}
		if(StringUtils.isNotBlank(yxryidString)){
			yxryidlist = yxryidString.split(",");
		}
		if(StringUtils.isNotBlank(dxryidString)){
			dxryidlist = dxryidString.split(",");
		}
		
		Map<String,Chry> rymap = new HashMap<String, Chry>();
		if(yxrylist!=null&&yxrylist.length>0){
			for(int i=0;i<yxrylist.length;i++){
				Chry chry = new Chry();
				chry.setEMPLOYEE_GUID(yxryidlist[i]);
				chry.setEMPLOYEE_NAME(yxrylist[i]);
				rymap.put(yxrylist[i], chry);
			}
		}
		if(dxrylist!=null&&dxrylist.length>0){
//			for(String s : dxrylist){
//				rymap.put(s, s);
//			}
			for(int i=0;i<dxrylist.length;i++){
				Chry chry = new Chry();
				chry.setEMPLOYEE_GUID(dxryidlist[i]);
				chry.setEMPLOYEE_NAME(dxrylist[i]);
				rymap.put(dxrylist[i], chry);
			}
		}
		if(qjry!=null&&qjry.size()>0){
			for(String s : qjry){
				if(rymap.containsKey(s)){
					rymap.remove(s);
				}
			}
		}
		List<Chry> personlist = new ArrayList<Chry>(rymap.values());
		getRequest().setAttribute("personlist", personlist);
		return "chry";
	}
	
	public void outprintExcel(){
//		List<Chry> list =  null;
//		String instanceId = getRequest().getParameter("instanceId");
//		String yxry = Utils.clobToString(meetingService.getPerson(instanceId,"PERSONNAME"));
//		String dxry = Utils.clobToString(meetingService.getPerson(instanceId,"DXRYNAME"));
//		
//		String yxryString = "";
//		String dxryString = "";
//		if(StringUtils.isNotBlank(yxry)){
//			yxryString = yxry.split("\\*")[0];
//		}
//		if(StringUtils.isNotBlank(dxry)){
//			dxryString = dxry.split("\\*")[0];
//		}
//		
//		String[] yxrylist = yxryString.split(",");
//		String[] dxrylist = dxryString.split(",");
//		
//		String eploeIds =  "(";
//		for(String eploe : yxrylist){
//			eploeIds+="'"+eploe+"',";
//		}
//		for(String eploe : dxrylist){
//			eploeIds+="'"+eploe+"',";
//		}
//		eploeIds+="'')";
//		logger.info("meeting out print excel");
//		list= meetingService.getChmdList(null, null,eploeIds);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String instanceId = getRequest().getParameter("instanceId");
		String yxry = Utils.clobToString(meetingService.getPerson(instanceId,"PERSONNAME"));
		String yxryString = "";
		if(StringUtils.isNotBlank(yxry)){
			yxryString = yxry.split("\\*")[0];
		}
		String[] yxrylist = yxryString.split(",");
		String eploeIds =  "(";
		for(String eploe : yxrylist){
			eploeIds+="'"+eploe+"',";
		}
		eploeIds+="'')";
		
		
		
		
		List<Chry4Out> list =  null;
		int count = meetingService.getCountOfChmd(eploeIds);
		Paging.setPagingParams(getRequest(), pageSize, count);
		
		logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
		list= meetingService.getChmdList4Out(Paging.pageIndex, Paging.pageSize,eploeIds);
		
		//list 处理
		//Map<String , List<Chry4Out> >  resMap = new HashMap<String , List<Chry4Out>>();
		
		Set set = new HashSet();
		List depIdList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {  
			Chry4Out element = (Chry4Out) iter.next();  
	         if (set.add(element.getDEPARTMENT_GUID())) {
	        	 depIdList.add(element.getDEPARTMENT_GUID());  
	         } 
	      }  
		//list.get(j).setDEPARTMENT_NAME("");
		
		for(int j = 0 ;j<depIdList.size() ;j++){
			String depid =  (String) depIdList.get(j);
			Boolean ispreplace = false;
			for(int i = 0 ;i<list.size() ;i++){
				if(depid.equals(list.get(i).getDEPARTMENT_GUID())){
					if(ispreplace){
						list.get(i).setDEPARTMENT_NAME("");
						list.get(i).setSUPERDEPARTMENT_NAME("");
					}
					ispreplace = true;
				}
			}
		
			
		}
		//获得填报指定参会人员的 id
		List needTbchmd = null;
		List useridList = meetingService.getMastUserid(instanceId);
		if(useridList.size()>0){
			String queryUserids = "('";
			for(int j = 0 ;j<useridList.size() ;j++){
				queryUserids=queryUserids+useridList.get(j)+",";
			}
			queryUserids=queryUserids+"')";
			queryUserids=queryUserids.replace(",", "','");
			
			needTbchmd=meetingService.getSupDepIdByUserid(queryUserids);
		}
		
		//如果分发下去 填报参会名单  需要处理 显示未填报参会名单的 单位
		List<Chry4Out> needlist =  new ArrayList<Chry4Out>();
		if(needTbchmd!=null){
	       for(int k = 0 ;k<needTbchmd.size() ;k++){
	    	  Object[] supDep = (Object[]) needTbchmd.get(k);
	    	  String supDepId = (String) supDep[1];
	    	  boolean isInsert = false;
	    	   for(Chry4Out chry : list){
	    		   if(supDepId.equals(chry.getSUPERIOR_GUID())){
	    			   needlist.add(chry);
	    			   isInsert=true;
	    		   }
	    		  
	    	   }
	    	   //如果 单位下没有填写 参会名单 那么 就插个空值 站位
	    	   if(isInsert==false){
    			   Chry4Out nonChry = new Chry4Out();
    			   nonChry.setDEPARTMENT_GUID(supDepId);
    			   nonChry.setDEPARTMENT_NAME((String)supDep[2]);
    			   needlist.add(nonChry);
    		   }
	       }
		}
		if(needlist.size()>0){
			list=needlist;
		}
		String type = "参会名单";
		try {
				
				if (list.size() > 4000) {
					String msg = "你要导出的条目已经过大请选择适当条件分批导出！";
					getResponse().setCharacterEncoding("UTF-8");
					getResponse().setHeader("UTF-8","msg" );
					getResponse().getWriter().write(msg);
				} else {
					String filePath = "C:/";
					String fileName=type+"-excel导出表";				
							// 获得列名
						String[] str = {"序号","单位","姓名","职务","手机号码"};
		  				createxcel(filePath, fileName, str, list, getResponse());
					
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void createxcel(String filePath, String fileName, String[] cellName,
			List list, HttpServletResponse response) throws ParseException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		/******************* 样式定义开始 *******************/
		// 定义全局文本样式，也可为某行某列单独定义
		HSSFCellStyle style = workbook.createCellStyle();
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		// 字体设置
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");// 设置字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置字体加粗
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置文本居中

		/******************* 样式定义结束 *******************/

		// 设置模板sheet
		HSSFSheet sheet1 = workbook.createSheet();
		
		//设置列宽
		sheet1.setColumnWidth(0, 2000);
		sheet1.setColumnWidth(1, 5000);
		sheet1.setColumnWidth(2, 5000);
		sheet1.setColumnWidth(3, 5000);
		sheet1.setColumnWidth(4, 5000);

		// 设置模板名称，防止中文乱码，重要
		workbook.setSheetName(0, fileName);
		// 添加标题行
		HSSFRow row_title = sheet1.createRow((short) 0);
		// 添加标题行所属的列
		for (int i = 0; i < cellName.length; i++) {
			HSSFCell cell = row_title.createCell((short) i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 设置编码格式，防止中文乱码，重要
			cell.setCellValue(cellName[i]);
				
			// sheet1.setColumnWidth((short) i, (short) length);
			// 设置文本样式
			cell.setCellStyle(style);
		}

		for (int i = 0; i < list.size(); i++) {
			HSSFRow row = sheet1.createRow((short) (i + 1));// 生成一行
				
			Chry4Out chry = (Chry4Out)list.get(i);
			HSSFCell cell2 = row.createCell((short) 0);
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell2.setCellValue(i+1);
			
			int j = 1;
				
				
			HSSFCell cell = row.createCell((short) j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(null!=chry.getDEPARTMENT_NAME()){
				cell.setCellValue(chry.getDEPARTMENT_NAME());
			}else{
				cell.setCellValue(" ");
			}	
			j+=1;
			HSSFCell cell1 = row.createCell((short) j);
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(null!=chry.getEMPLOYEE_NAME()){
				cell1.setCellValue(chry.getEMPLOYEE_NAME());
			}else{
				cell1.setCellValue(" ");
			}	
			j+=1;
			HSSFCell cell21 = row.createCell((short) j);
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(null!=chry.getEMPLOYEE_JOBTITLES()){
				cell21.setCellValue(chry.getEMPLOYEE_JOBTITLES());
			}else{
				cell21.setCellValue(" ");
			}	
			j+=1;
			HSSFCell cell3 = row.createCell((short) j);
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(null!=chry.getEMPLOYEE_MOBILE()){
				cell3.setCellValue(chry.getEMPLOYEE_MOBILE());
			}else{
				cell3.setCellValue(" ");
			}	
		}
		response.reset();
		response.setContentType("application/vnd.ms-excel");	
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName.toString() + ".xls", "UTF8"));
			workbook.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 参会名单汇总
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getMeetingOutList(){
		String hyqjitemid=getRequest().getParameter("hyqjitemid");
		String hyqjworkflowid=getRequest().getParameter("hyqjworkflowid");
		//查询条件
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		String status = getRequest().getParameter("status")==null?"":getRequest().getParameter("status");
		
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String depIds=getSession().getAttribute(MyConstants.DEPARMENT_IDS)==null?null:((List)getSession().getAttribute(MyConstants.DEPARMENT_IDS)).get(0).toString();
		Department department = departmentService.findDepartmentById(employee.getDepartmentGuid());
		String[] bgsdepids = SystemParamConfigUtil.getParamValueByParam("bgs_depid").split(",");//办公室
		String[] jzsdepids = SystemParamConfigUtil.getParamValueByParam("jzs_depid").split(",");//局长室
		if (department!=null&&CommonUtil.stringNotNULL(department.getSuperiorGuid())&&department.getSuperiorGuid().equals("1")) {
			//超管查看所有事项
			depIds = null;
		}else{
			for (String bgsstr : bgsdepids) {
				if(employee.getDepartmentGuid().equals(bgsstr)){
					depIds = null;
					break;
				}else{
					for (String jzsstr : jzsdepids) {
						if(employee.getDepartmentGuid().equals(jzsstr)){
							depIds = null;
							break;
						}
					}
				}
			}
		}
		String conditionSql = "";//查询条件
		String name = getRequest().getParameter("name");
		String begtime = getRequest().getParameter("begtime");
		if(CommonUtil.stringNotNULL(name) ){
			conditionSql += " and m.meetingname like '%"+name+"%' ";
		}
		if(CommonUtil.stringNotNULL(begtime) ){
			conditionSql += " and m.newtime >= '"+begtime+"'";
		}
		if(CommonUtil.stringNotNULL(searchDateFrom) ){
			conditionSql += " and to_date(m.newtime,'YYYY-MM-dd HH24:mi') >= to_date('"+searchDateFrom+"','YYYY-MM-dd HH24:mi')";
		}
		if(CommonUtil.stringNotNULL(searchDateTo) ){
			conditionSql += " and to_date(m.newtime,'YYYY-MM-dd HH24:mi') <= to_date('"+searchDateTo+"','YYYY-MM-dd HH24:mi')";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = meetingService.getCountMeetingOuts(depIds, conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Hytz> meetingOutList = meetingService.getMeetingOutList(depIds, conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("meetingOutList", meetingOutList);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("begtime", begtime);
		
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("hyqjitemid", hyqjitemid);
		getRequest().setAttribute("hyqjworkflowid", hyqjworkflowid);
		
		return "meetingOutList";
	}
	
	
	public void checkMeetingTime(){
		String meetingBeginTime=getRequest().getParameter("meetingBeginTime");
		String meetingEndTime=getRequest().getParameter("meetingEndTime");
		String roomName=getRequest().getParameter("roomName").split("[*]")[1];
		int num=meetingService.checkMeetingTime(meetingBeginTime, meetingEndTime,roomName);
		String result=null;
		if(num>0){
			result = "false";
		}else{
			result = "true";
		}
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
