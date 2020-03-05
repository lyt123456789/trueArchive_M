/**
 * 文件名称:TableForMobileAction.java
 * 作者:zhuxc<br>
 * 创建时间:2013-11-25 下午03:23:59
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.functions.workflow.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.functions.workflow.entity.vo.HaveDone4BreezeVo;
import cn.com.trueway.functions.workflow.entity.vo.Pend4BreezeVo;
import cn.com.trueway.functions.workflow.service.EWorkFlowService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptVo;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;

/**
 * 描述： 对TableForMobileAction进行描述
 * 作者：zhuxc
 * 创建时间：2013-11-25 下午03:23:59
 */
public class EWorkFlowAction extends BaseAction{
	
	private static final long serialVersionUID = -4188518691271558872L;

	private EWorkFlowService eworkflowService;
	
	private DictionaryService dictionaryService;

	private DepartmentService departmentService;
	
	private PendingService pendingService;
	
	private EmployeeService employeeService;
	
	private ItemService itemService;
	
	
	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public EWorkFlowService getEworkflowService() {
		return eworkflowService;
	}

	public void setEworkflowService(EWorkFlowService eworkflowService) {
		this.eworkflowService = eworkflowService;
	}

	//-工具1
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"GBK"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	//-工具1
		private JSONArray getJSONArray(){
			InputStream iStream = null;
			try {
				iStream = getRequest().getInputStream();
				byte[] data = readStream(iStream);
				return JSONArray.fromObject(new String (data,"UTF-8"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(iStream!=null){
					try {
						iStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}
	
	//-工具2
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
	
	/**
	 * 获取待办---门户
	 * 
	 * @throws IOException
	 */
	public void getTodoListOfWebNew() throws IOException {
		// 当前人员
		Employee emp = (Employee) getSession().getAttribute(
				MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String itemIds = getRequest().getParameter("itemIds");

		String column = getRequest().getParameter("column");
		String pagesize = getRequest().getParameter("pagesize");
		String url = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"
				+ getRequest().getLocalPort() + getRequest().getContextPath();
		String callBack = getRequest().getParameter("callback");
		String out = "";
		out = eworkflowService.getTodoForPortalNew(userId, column, pagesize, url,
				callBack, itemIds);
		getResponse().getWriter().write(out);
		getResponse().getWriter().close();
	}
	
	/**
	 * 获取待办---门户
	 * 
	 * @throws IOException
	 */
	public void getTodoList4WebNew() throws IOException {
		// 当前人员
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String itemIds = getRequest().getParameter("itemIds");

		String column = getRequest().getParameter("column");
		String pagesize = getRequest().getParameter("pagesize");
		String url = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
		String callBack = getRequest().getParameter("callback");
		String out = "";
		out = eworkflowService.getTodo4WebNew(userId, column, pagesize, url,callBack, itemIds);
		getResponse().getWriter().write(out);
		getResponse().getWriter().close();
	}
	
	public String getThemeWords(){
		JSONObject jsonObject  = getJSONObject();
		String value = "";
		if(jsonObject != null){
			try {
				value = jsonObject.getString("value");
			} catch (Exception e) {
			}
		}else{
			value = getRequest().getParameter("value");
		}
		value = value.replace("{{cs::::value}}", "");
		if(value!= null && !value.equals("")){
			value = ","+value+",";
		}
		// 查询字段表 找到 主题词
		StringBuffer json = new StringBuffer();
		json.append("[");
		List<WfDictionary> keys = dictionaryService.getDictionaryByName("themeWords");
		for(int index = 0 ; index <keys.size() ; index++){
			WfDictionary dic = keys.get(index);
			String[] dicKeys = dic.getVc_key().split(",");
			String[] dicValues = dic.getVc_value().split(",");
			for(int j = 0 ; j < dicKeys.length ; j++){
				json.append("{");
				json.append("key:").append("'").append(dicKeys[j]).append("',");
				json.append("value:").append("'").append(dicValues[j]).append("',");
				if(value.indexOf(","+dicValues[j]+",")>-1){
					json.append("checked:").append("true").append("");
				}else{
					json.append("checked:").append("false").append("");
				}
				
				if(j==dicKeys.length-1){
					json.append("}");
				}else{
					json.append("},");
				}
			}
		}
		json.append("]");
		getRequest().setAttribute("json", json);
		return "themeWords";
	}
	
	public void breezeInterface() throws IOException
	  {
	    Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
	    String userId = getRequest().getParameter("userId");
	    if(null!=emp){
	    	 userId = emp.getEmployeeGuid();
	    }
	   
	    String itemIds = getRequest().getParameter("itemIds");

	    String column = getRequest().getParameter("column");
	    String pagesize = getRequest().getParameter("pagesize");
	    String url = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
	    String callBack = getRequest().getParameter("callback");
	    
	    List<Pending> pendlist = this.eworkflowService.getTodo4Breeze(userId, column, pagesize, url, callBack, itemIds);
	    
	    List<Pend4BreezeVo> pend4BreezeList = new ArrayList<Pend4BreezeVo>();
	    
	    if(pendlist!=null&&pendlist.size()>0){
			for(Pending pend : pendlist){
				Pend4BreezeVo p4b=new Pend4BreezeVo();
				if (null != pend.getProcess_title()){
					p4b.setTitle(pend.getProcess_title().indexOf(",") == -1 ? pend.getProcess_title() : pend.getProcess_title().split(",")[0]);
				}
				else{
					p4b.setTitle("");
				}
				String instanceId = pend.getWf_instance_uid();
				List<WfProcess> wfps = pendingService.findProcessListByFId(instanceId);
				if(null != wfps && wfps.size()>0){
					//当前办件有子流程
					p4b.setIsHaveChild(1);
				}else{
					//当前办件没有子流程
					List<WfProcess> wfpss = pendingService.getProcessByInstanceId(instanceId);
					boolean isChild = false;
					for(WfProcess wfp:wfpss){
						if(CommonUtil.stringNotNULL(wfp.getfInstancdUid())){
							isChild = true;
						}
					}
					if(isChild){
						//当前办件是子流程
						p4b.setIsHaveChild(1);
					}else{
						//当前办件是主流程
						p4b.setIsHaveChild(0);
					}
				}
				p4b.setDate(new SimpleDateFormat("yyyy-MM-dd").format(pend.getApply_time()));
				p4b.setLink(url + "/table_openPendingForm.do?closeWay=1&processId=" + pend.getWf_process_uid() + "&isDb=true&itemId=" + pend.getItem_id() + "&formId=" + pend.getForm_id() + "&isPortal=1&isCanPush=1");
				p4b.setPendType("公文待办");
				p4b.setIsChildWf(pend.getIsChildWf());
				pend4BreezeList.add(p4b);
			}
		}
		Map outmap=new HashMap();
		   outmap.put("data", pend4BreezeList);
		   outmap.put("title", "待办中心");
		   outmap.put("more", getRequest().getScheme()+"://www.trueway.com.cn");
		getResponse().getWriter().println(";"+callBack+"("+JSONObject.fromObject(outmap).toString()+")");
		getResponse().getWriter().close();
	  }
	
	public void breezeInterface4history() throws IOException
	  {
	    Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
	    String userId = emp.getEmployeeGuid();
	    String itemIds = getRequest().getParameter("itemIds");
	    
	    String timetype = getRequest().getParameter("timetype");//0-全部  1-本周  2-本月  3-本年
	    
	    String column = getRequest().getParameter("column");
	    String pagesize = getRequest().getParameter("pagesize");
	    String url = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
	    String callBack = getRequest().getParameter("callback");
	    
	    List<Object[]> pendlist = this.eworkflowService.getHaveDone4Breeze(userId, column, pagesize, url, callBack, itemIds,timetype);
	    
	    List<HaveDone4BreezeVo> pend4BreezeList = new ArrayList<HaveDone4BreezeVo>();
	    
	    if(pendlist!=null&&pendlist.size()>0){
			for(int i = 0 ;i<pendlist.size();i++){
				Object[] obj= pendlist.get(i);
				HaveDone4BreezeVo p4b=new HaveDone4BreezeVo();
				p4b.setTitle(obj[0].toString().indexOf(",") == -1 ? obj[0].toString() : obj[0].toString().split(",")[0]);
				p4b.setDate(obj[4].toString());
				p4b.setLink(url + "/table_openPendingForm.do?closeWay=1&processId=" + "&isDb=true&itemId=" + "&formId="  + "&isPortal=1&isCanPush=1");
				
				 p4b.setPendType(obj[1].toString());
				
				pend4BreezeList.add(p4b);
			}
		}
		Map outmap=new HashMap();
		   outmap.put("data", pend4BreezeList);
		   outmap.put("title", "已办");
		   outmap.put("more", url+"/table_getOverList.do");
		getResponse().getWriter().println(";"+callBack+"("+JSONObject.fromObject(outmap).toString()+")");
		getResponse().getWriter().close();
	  }
	
	/**
	 * 描述：根据状态查询数据
	 *
	 * 作者:Yuxl
	 * 创建时间:2015-1-23 上午8:52:59
	 */
	public void searchByStatus(){
		PrintWriter out = null;
		String jsonStr = "";
		// status 0,待办;1,已办; 2,已办结;3,超期未办 ;4,已办未办结;5,已关注;6,查询所有人的待办超期;7,收藏列表
		// 已办的查询字段 事项类型 itemType, 标题 title, 提交部门 commitDept , 提交人 commitUser, 提交时间(commitTimeFrom-commitTimeTo)
		try {
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String status = getRequest().getParameter("status");
			String userId = getRequest().getParameter("userId");
			String siteId = getRequest().getParameter("siteId");
			if(userId==null || userId.equals("")){
				Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				userId = emp.getEmployeeGuid();
			}
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));

		    String itemType = getRequest().getParameter("itemType"); //事项类型
		    String title = getRequest().getParameter("title"); //标题 
		    String commitDept = getRequest().getParameter("commitDept"); //提交部门
		    String commitUser = getRequest().getParameter("commitUser"); //提交人
		    String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//提交时间 区间 from
		    String commitTimeTo = getRequest().getParameter("commitTimeTo");//提交时间 区间end
		    String conditionSql = "";
		    List<FieldSelectCondition> selects = new ArrayList<FieldSelectCondition>();
		    if(CommonUtil.stringNotNULL(userId) && StringUtils.isNotBlank(status) && !status.equals("6")){
		    	FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("userId");
		    	select.setValue(userId.trim());
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql = "and p.user_uid = :userId ";
		    }
		   
		    String itemids = "";
			if(StringUtils.isNotBlank(siteId)){
				if(StringUtils.isNotBlank(itemType)){//去取两个itemId的交集
					itemids = this.getIntersectItemId(itemType, siteId);
				}else{
					itemids = itemService.getItemIdsBydeptId(siteId);
				}
			}else{
				itemids = itemType;
			}
		    
		    if(CommonUtil.stringNotNULL(itemids)){
		    	String itemIds = "";
		    	if(itemids.split(",").length>1){
		    		int i=0;
		    		for (String itemId : itemids.split(",")) {
						if(i == 0){
							itemIds += "'"+itemId+"'";
						}else{
							itemIds += ",'"+itemId+"'";
						}
		    			i++;
					}
		    	}else{
		    		itemIds = "'"+itemids+"'";
		    	}
		    	conditionSql += "and i.id in ("+itemIds+") ";
		    }
			if(CommonUtil.stringNotNULL(title)){
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("title");
		    	String rep_value = "";
		    	title = title.trim();
				rep_value = title.replaceAll("%", "\\\\%");
				rep_value = rep_value.replace("_", "\\_");
				
		    	select.setValue(rep_value);
		    	select.setType(1);
		    	selects.add(select);
		    	conditionSql += "and p.process_title like :title ";
		    	if(rep_value!=null && !rep_value.equals(title)){
					conditionSql += " escape '\\'";
				}
			}
			if(CommonUtil.stringNotNULL(commitDept)){
				commitDept = commitDept.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitDept");
		    	select.setValue(commitDept);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and d.DEPARTMENT_NAME = :commitDept ";
			}
			if(CommonUtil.stringNotNULL(commitUser)){
				commitUser = commitUser.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitUser");
		    	select.setValue(commitUser);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and e.employee_name = :commitUser ";
			}
			if(CommonUtil.stringNotNULL(commitTimeFrom)){
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";
			}
			if(CommonUtil.stringNotNULL(commitTimeTo)){
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";
			}
			if(status != null){
				if(status.equals("3")){
					conditionSql +="and p.JDQXDATE <= sysdate ";
		    	}else if(status.equals("6")){
		    		conditionSql +=" and (p.jdqxdate <= sysdate or (p.jdqxdate > sysdate and sysdate > p.jdqxdate-3)) ";
		    	}
			}
			if((status != null && !"".equals(status))&&(userId != null && !"".equals(userId))){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				
				jsonStr = eworkflowService.searchDataByStatus(conditionSql,column*pagesize, pagesize,status,selects,serverUrl,callback);

			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(jsonStr);
		out.close();
	}
	
	public void getMySearchJson(){

		PrintWriter out = null;
		String jsonStr = "";
		// status 0 待办 ，1 已办 2，已办结，3，超期未办 ,4, 已办未办结
		// 已办的查询字段 事项类型 itemType, 标题 title, 提交部门 commitDept , 提交人 commitUser, 提交时间(commitTimeFrom-commitTimeTo)
		try {
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String status = getRequest().getParameter("status");
			String userId = getRequest().getParameter("userId");
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));

		    String itemType = getRequest().getParameter("itemType"); //事项类型
		    String title = getRequest().getParameter("title"); //标题 
		    String commitDept = getRequest().getParameter("commitDept"); //提交部门
		    String commitUser = getRequest().getParameter("commitUser"); //提交人
		    String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//提交时间 区间 from
		    String commitTimeTo = getRequest().getParameter("commitTimeTo");//提交时间 区间end
		    String conditionSql = "";
		    List<FieldSelectCondition> selects = new ArrayList<FieldSelectCondition>();
		    if(CommonUtil.stringNotNULL(userId)){
		    	FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("userId");
		    	select.setValue(userId.trim());
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql = "and p.user_uid = :userId ";
		    }
		   
		    if(CommonUtil.stringNotNULL(itemType)){
		    	FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("itemType");
		    	select.setValue(itemType.trim());
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql += "and i.VC_SXMC = :itemType ";
		    }
			if(CommonUtil.stringNotNULL(title)){
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("title");
		    	String rep_value = "";
		    	title = title.trim();
				rep_value = title.replaceAll("%", "\\\\%");
				rep_value = rep_value.replace("_", "\\_");
				
		    	select.setValue(rep_value);
		    	select.setType(1);
		    	selects.add(select);
		    	conditionSql += "and p.process_title like :title ";
		    	if(rep_value!=null && !rep_value.equals(title)){
					conditionSql += " escape '\\'";
				}
			}
			if(CommonUtil.stringNotNULL(commitDept)){
				commitDept = commitDept.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitDept");
		    	select.setValue(commitDept);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and d.DEPARTMENT_NAME = :commitDept ";
			}
			if(CommonUtil.stringNotNULL(commitUser)){
				commitUser = commitUser.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitUser");
		    	select.setValue(commitUser);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and e.employee_name = :commitUser ";
			}
			if(CommonUtil.stringNotNULL(commitTimeFrom)){
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";
			}
			if(CommonUtil.stringNotNULL(commitTimeTo)){
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";
			}
			if(status != null && status.equals("3")){
		    	conditionSql +="and p.JDQXDATE <= sysdate ";
			}
			if((status != null && !"".equals(status))&&(userId != null && !"".equals(userId))){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				String left = eworkflowService.getMySearchJson(conditionSql,column*pagesize, pagesize,status,selects,serverUrl,callback);
				serverUrl +="/eworkflow_getMySearchJson.do?userId="+userId+"&colunm="+column+"&pagesize="+pagesize+"&status="+status;
				String right = eworkflowService.getMySearchFormJson(userId, status,serverUrl);
				
				jsonStr = callback+"({\"left\":"+left+",\"right\":"+right+"})";
			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonStr);
		out.close();
	
	}
	
	public void getJsonByMaxUrl(){


		PrintWriter out = null;
		String jsonStr = "";
		// status 0 待办 ，1 已办 2，已办结，3，超期未办 ,4, 已办未办结
		// 已办的查询字段 事项类型 itemType, 标题 title, 提交部门 commitDept , 提交人 commitUser, 提交时间(commitTimeFrom-commitTimeTo)
		try {
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String status = getRequest().getParameter("status");
			String userId = getRequest().getParameter("userId");
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));

		    String itemType = getRequest().getParameter("itemType"); //事项类型
		    String title = getRequest().getParameter("title"); //标题 
		    String commitDept = getRequest().getParameter("commitDept"); //提交部门
		    String commitUser = getRequest().getParameter("commitUser"); //提交人
		    String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//提交时间 区间 from
		    String commitTimeTo = getRequest().getParameter("commitTimeTo");//提交时间 区间end
		    String conditionSql = "";
		    List<FieldSelectCondition> selects = new ArrayList<FieldSelectCondition>();
		    if(CommonUtil.stringNotNULL(userId)){
		    	FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("userId");
		    	select.setValue(userId.trim());
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql = "and p.user_uid = :userId ";
		    }
		   
		    if(CommonUtil.stringNotNULL(itemType)){
		    	FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("itemType");
		    	select.setValue(itemType.trim());
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql += "and i.VC_SXMC = :itemType ";
		    }
			if(CommonUtil.stringNotNULL(title)){
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("title");
		    	String rep_value = "";
		    	title = title.trim();
				rep_value = title.replaceAll("%", "\\\\%");
				rep_value = rep_value.replace("_", "\\_");
				
		    	select.setValue(rep_value);
		    	select.setType(1);
		    	selects.add(select);
		    	conditionSql += "and p.process_title like :title ";
		    	if(rep_value!=null && !rep_value.equals(title)){
					conditionSql += " escape '\\'";
				}
			}
			if(CommonUtil.stringNotNULL(commitDept)){
				commitDept = commitDept.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitDept");
		    	select.setValue(commitDept);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and d.DEPARTMENT_NAME = :commitDept ";
			}
			if(CommonUtil.stringNotNULL(commitUser)){
				commitUser = commitUser.trim();
				FieldSelectCondition select = new FieldSelectCondition();
		    	select.setName("commitUser");
		    	select.setValue(commitUser);
		    	select.setType(0);
		    	selects.add(select);
		    	conditionSql +="and e.employee_name = :commitUser ";
			}
			if(CommonUtil.stringNotNULL(commitTimeFrom)){
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";
			}
			if(CommonUtil.stringNotNULL(commitTimeTo)){
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
		    	conditionSql +="and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";
			}
			if(status != null && status.equals("3")){
		    	conditionSql +="and p.JDQXDATE <= sysdate ";
			}
			if((status != null && !"".equals(status))&&(userId != null && !"".equals(userId))){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				jsonStr = eworkflowService.getJsonByMaxUrl(conditionSql,column*pagesize, pagesize,status,selects,serverUrl,callback);
			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonStr);
		out.close();
	
	
	}
	
	public String mySearch(){
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		String status = getRequest().getParameter("status");
		String userId = getRequest().getParameter("userId");
		// 页数ag
	    Integer column =  getRequest().getParameter("column") == null ? 0
				: Integer.parseInt((String) getRequest().getParameter("column"));
		// 页面显示的条数
	    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 6
				: Integer.parseInt((String)  getRequest().getParameter("pagesize"));

	    String itemType = getRequest().getParameter("itemType"); //事项类型
	    String title = getRequest().getParameter("title"); //标题 
	    String commitDept = getRequest().getParameter("commitDept"); //提交部门
	    String commitUser = getRequest().getParameter("commitUser"); //提交人
	    String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//提交时间 区间 from
	    String commitTimeTo = getRequest().getParameter("commitTimeTo");//提交时间 区间end
	    String conditionSql = "";
	    List<FieldSelectCondition> selects = new ArrayList<FieldSelectCondition>();
	    if(CommonUtil.stringNotNULL(userId)){
	    	FieldSelectCondition select = new FieldSelectCondition();
	    	select.setName("userId");
	    	select.setValue(userId.trim());
	    	select.setType(0);
	    	selects.add(select);
	    	conditionSql = "and p.user_uid = :userId ";
	    	
	    }
	   
	    if(CommonUtil.stringNotNULL(itemType)){
	    	FieldSelectCondition select = new FieldSelectCondition();
	    	select.setName("itemType");
	    	select.setValue(itemType.trim());
	    	select.setType(0);
	    	selects.add(select);
	    	conditionSql += "and i.VC_SXMC = :itemType ";
	    }
		if(CommonUtil.stringNotNULL(title)){
			FieldSelectCondition select = new FieldSelectCondition();
	    	select.setName("title");
	    	String rep_value = "";
	    	title = title.trim();
			rep_value = title.replaceAll("%", "\\\\%");
			rep_value = rep_value.replace("_", "\\_");
			
	    	select.setValue(rep_value);
	    	select.setType(1);
	    	selects.add(select);
	    	conditionSql += "and p.process_title like :title ";
	    	if(rep_value!=null && !rep_value.equals(title)){
				conditionSql += " escape '\\'";
			}
		}
		if(CommonUtil.stringNotNULL(commitDept)){
			commitDept = commitDept.trim();
			FieldSelectCondition select = new FieldSelectCondition();
	    	select.setName("commitDept");
	    	select.setValue(commitDept);
	    	select.setType(0);
	    	selects.add(select);
	    	conditionSql +="and d.DEPARTMENT_NAME = :commitDept ";
		}
		if(CommonUtil.stringNotNULL(commitUser)){
			commitUser = commitUser.trim();
			FieldSelectCondition select = new FieldSelectCondition();
	    	select.setName("commitUser");
	    	select.setValue(commitUser);
	    	select.setType(0);
	    	selects.add(select);
			conditionSql += " and (select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid )= :commitUser ";
		}
		if(CommonUtil.stringNotNULL(commitTimeFrom)){
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
	    	conditionSql +="and p.APPLY_TIME >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";
		}
		if(CommonUtil.stringNotNULL(commitTimeTo)){
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
	    	conditionSql +="and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";
		}
		if(status != null && status.equals("3")){
	    	conditionSql +="and p.JDQXDATE <= sysdate ";
		}
		if((status != null && !"".equals(status))&&(userId != null && !"".equals(userId))){
			String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
			JSONArray data = eworkflowService.getMySearchObj(conditionSql,column*pagesize, pagesize,status,selects,serverUrl);
			if(data.size() != 0){
				getRequest().setAttribute("mySearchData", data);
			}
		}
		if(getSession().getAttribute("mySearchItems") == null){
			Employee emp = employeeService.findEmployeeById(userId);
			Department department=null;
			try {
				department = departmentService.queryDepartmentById(emp.getDepartmentGuid());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//根据机构逆推
			boolean flag = true ;
			String depids = "";
			String depid = department.getDepartmentGuid();
			Department depart=null;
			while(flag){
				depart = departmentService.queryDepartmentById(depid);
				if(depart!=null){
					depids+= "'"+depid +"',";
					depid = depart.getSuperiorGuid();
					if(depid!=null && depid.equals("1")){
						flag = false;
					}
				}
			}
			if(depids!=null && depids.length()>0){
				depids = depids.substring(0,depids.length()-1);
			}
			List<WfItem> items = itemService.getItemListByDeptIds(depids,"");
			getRequest().setAttribute("mySearchItems", items);
		}
		if(getSession().getAttribute("mySearchDepts") == null){
			List<SimpleDeptVo> depts = departmentService.getDeptInfo();
			getRequest().setAttribute("mySearchDepts", depts);
			
		}

		getRequest().setAttribute("commitDept", commitDept);
		getRequest().setAttribute("commitUser", commitUser);
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("itemType", itemType);
		getRequest().setAttribute("userId", userId);
		// 获取事项 和  部门
		if(status.equals("0") || status.equals("3")){ // 待办
			return "myPendList";
		}else if(status.equals("1") ||status.equals("2") ||status.equals("4")){ // 已办
			return "myOverList";
		}else{
			return "myOverList";
		}
		
	}
	/**
	 *  根据状态查询收文
	 */
	public void getReceiveDataByStatus(){
		PrintWriter out = null;
		String jsonStr = "";
		try{
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String status = getRequest().getParameter("status");
			String userId = getRequest().getParameter("userId");
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));
		    
			String zfbDepId = "";
			Map<String, String> searchmap = new HashMap<String, String>();
			Employee emp=employeeService.findEmployeeById(userId);
			Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
			getRequest().setAttribute("deptName", dep.getDepartmentName());
			String departId = dep.getSuperiorGuid();	//获取当前人员的父机构Id
			String linkDeptIds = dep.getDepartmentGuid();
			if (CommonUtil.stringNotNULL(linkDeptIds)) {  
				linkDeptIds = "'" + linkDeptIds.replaceAll(",", "','") + "'";
			}
			linkDeptIds += ",'"+departId+"'";
			/*if(super_dep!=null){
				if(super_dep.getSuperiorGuid()!=null && !super_dep.getSuperiorGuid().equals("")){
					linkDeptIds += ",'"+super_dep.getSuperiorGuid()+"'";
				}
			}*/
			searchmap.put("departId", linkDeptIds);
			int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
			boolean jrdb;
			if(zfbDepId!=null && (zfbDepId.equals(dep.getDepartmentGuid()) || zfbDepId.equals(departId))){
				jrdb=true;
			}else {
				jrdb=false;
			}
			List<WfItem> itemList = itemService.getItemList(emp.getDepartmentGuid());
			if((status != null && !"".equals(status))&&(userId != null && !"".equals(userId))){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				jsonStr = eworkflowService.getJsonReceiveDataByStatus(callback, userId, column*pagesize, pageSize, status,searchmap, serverUrl, jrdb, itemList);
			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		out.print(jsonStr);
		out.close();
	}
	
	/**
	 * 获取待盖章的数据
	 */
	public void getToBeSealedList(){
		PrintWriter out = null;
		String jsonStr = "";
		try{
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String userId = getRequest().getParameter("userId");
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));
		    String conditionSql = " and (p.action_status is null or p.action_status!=2) ";
		    conditionSql +=" and p.is_over='NOT_OVER'";
		    Employee emp = employeeService.findEmployeeById(userId);
			String depId = emp.getDepartmentGuid();
			//获取向上推的全部
			Collection<Department> depColl = departmentService.findAllChildDepList(depId);
			Iterator<Department> iterator = depColl.iterator();
			Department depar = null;
			String depIds = "";
			while(iterator.hasNext()){
				depar = iterator.next();
				depIds += "'"+depar.getDepartmentGuid()+"',";
			}
			if(depIds!=null && depIds.length()>0){
				depIds = depIds.substring(0, depIds.length()-1);
			}
			if(userId != null && !"".equals(userId)){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				jsonStr = eworkflowService.getJsonToBeSealed(callback, depIds, column*pagesize, pagesize, conditionSql, serverUrl);
			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		out.print(jsonStr);
		out.close();
	}
	
	public void getToDealList(){
		PrintWriter out = null;
		String jsonStr = "";
		try{
			String callback =getRequest().getParameter("callback")==null ?"":getRequest().getParameter("callback");
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String userId = getRequest().getParameter("userId");
			// 页数ag
		    Integer column =  getRequest().getParameter("column") == null ? 0
					: Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
		    Integer pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10
					: Integer.parseInt((String)  getRequest().getParameter("pagesize"));
		    String conditionSql = "";
		    if(userId != null && !"".equals(userId)){
				String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
				jsonStr = eworkflowService.getJsonToDealList(callback, userId, column*pagesize, pagesize, conditionSql, serverUrl);
			}else{
				jsonStr = callback+"({\"data\":\"\",\"success\":\"false\",\"message\":\"参数不合法\"})";
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		out.print(jsonStr);
		out.close();
	}
	
	
	/** 
	 * getIntersectItemId:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param itemIds
	 * @param siteId
	 * @return 
	 * @since JDK 1.6 
	 */
	private String getIntersectItemId(String itemIds,String siteId){
		String itemids = itemService.getItemIdsBydeptId(siteId);
		if(StringUtils.isNotBlank(itemids) && StringUtils.isNotBlank(itemIds)){
			String[] itemid = this.getIntersect(itemIds.split(","), itemIds.split(","));
			if(itemid.length>0){
				String itemId = "";
				for (int i = 0; i < itemid.length; i++) {
					itemId += itemid[i]+",";
				}
				itemId = itemId.substring(0,itemId.length()-1);
				return itemId;
			}else{
				return itemids;
			}
		}
		return "";
	}
	
	/** 
	 * getIntersect:(获取两个数组的交集). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param str1
	 * @param str2
	 * @return 
	 * @since JDK 1.6 
	 */
	private String[] getIntersect(String[] arr1, String[] arr2){
		Map<String,Boolean> map = new HashMap<String,Boolean>();
        List<String> list = new LinkedList<String>();
        //取出str1数组的值存放到map集合中，将值作为key，所以的value都设置为false
        for (String str1:arr1){
        	if (!map.containsKey(str1)){
        		map.put(str1,Boolean.FALSE);
        	}
        }
        //取出str2数组的值循环判断是否有重复的key，如果有就将value设置为true
        for (String str2:arr2){
        	if (map.containsKey(str2)){
        		map.put(str2,Boolean.TRUE);
        	}
        }
        //取出map中所有value为true的key值，存放到list中
        for (Map.Entry<String,Boolean> entry:map.entrySet()){
        	if (entry.getValue().equals(Boolean.TRUE)){
        		list.add(entry.getKey());
        	}
        }
        //声明String数组存储交集
        String[] result={};
        return list.toArray(result);
	}
}
