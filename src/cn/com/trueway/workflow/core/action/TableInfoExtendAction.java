package cn.com.trueway.workflow.core.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.ExportExcel;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.AccessLog;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.FileDownloadLog;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.FormPermitService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoExtendService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TrueJsonService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.MsgToObj;
import cn.com.trueway.workflow.set.util.SendMsgUtil;
import cn.com.trueway.workflow.set.util.WebSocketUtil;

/**
 * 描述：核心操作类控制层扩展
 * 作者：蒋烽
 * 创建时间：2017-4-10 上午10:30:40
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TableInfoExtendAction extends TableInfoAction {

	private static final long 			serialVersionUID 	= 9194289457294310927L;
	
	/**
	 * 日志信息
	 */
	private Logger 						LOGGER 				= Logger.getLogger(this.getClass());

	private TableInfoExtendService 		tableInfoExtendService;

	private DepartmentService			departmentService;
	
	private ItemService					itemService;
	
	private TableInfoService			tableInfoService;
	
	private PendingService				pendingService;
	
	private TrueJsonService				trueJsonService;
	
	private FormPermitService			formPermitService;
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private ZwkjFormService				zwkjFormService;
	
	private String						userName;
	
	public TableInfoExtendService getTableInfoExtendService() {
		return tableInfoExtendService;
	}

	public void setTableInfoExtendService(
			TableInfoExtendService tableInfoExtendService) {
		this.tableInfoExtendService = tableInfoExtendService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}

	public TrueJsonService getTrueJsonService() {
		return trueJsonService;
	}

	public void setTrueJsonService(TrueJsonService trueJsonService) {
		this.trueJsonService = trueJsonService;
	}

	public FormPermitService getFormPermitService() {
		return formPermitService;
	}

	public void setFormPermitService(FormPermitService formPermitService) {
		this.formPermitService = formPermitService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	/**
	 * 描述：添加  人员与关注办件的关联关系
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午10:34:00
	 */
	public void addFollowShip(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = getRequest().getParameter("userId");
		String instanceId = getRequest().getParameter("instanceId");
		String oldInstanceId = getRequest().getParameter("oldInstanceId");
		String employeeId = "";
		if(null != emp){
			employeeId = emp.getEmployeeGuid();
		}else{
			employeeId = userId;
		}
		List<FollowShip> list = tableInfoExtendService.getFollowShips(instanceId, employeeId, oldInstanceId);
		if(null != list && list.size()>0){
			if(StringUtils.isBlank(oldInstanceId)){
				FollowShip oldShip = list.get(0);
				oldShip.setIsRead("0");
				tableInfoExtendService.editFollowShip(oldShip);
			}
		}else{
			FollowShip followShip = new FollowShip();
			if(StringUtils.isNotBlank(oldInstanceId)){
				followShip.setOldInstanceId(oldInstanceId);
			}else{
				followShip.setEmployeeGuid(employeeId);
				followShip.setIsRead("0");
			}
			followShip.setInstanceId(instanceId);
			tableInfoExtendService.addFollowShip(followShip);
		}
		toPage("success");
	}
	
	/**
	 * 
	 * 描述：删除待办提醒
	 * 作者:蒋烽
	 * 创建时间:2017-4-25 下午5:42:47
	 */
	public void deleteFollowShip(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		//String userId = getRequest().getParameter("userId");
		String instanceId = getRequest().getParameter("instanceId");
//		String oldInstanceId = getRequest().getParameter("oldInstanceId");
		
		String employeeId = emp.getEmployeeGuid();
		
		tableInfoExtendService.deleteFollowShip(instanceId, employeeId);
		toPage("success");
	}
	
	/**
	 * 描述：查询关注列表
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 下午2:48:36
	 */
	public String getFollowList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String statustype = getRequest().getParameter("statustype");
		String itemid = getRequest().getParameter("itemid");
		String owner = getRequest().getParameter("owner");
		String title = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");
		String status = getRequest().getParameter("status") == null ? ""
				: getRequest().getParameter("status");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		String conditionSql = "";
		if (CommonUtil.stringNotNULL(title)|| CommonUtil.stringNotNULL(itemName)|| CommonUtil.stringNotNULL(status)) {
			conditionSql = "and p.process_title like '%" + title
					+ "%' and i.vc_sxmc like '%" + itemName+ "%'";
		}
		if(CommonUtil.stringNotNULL(owner)&&"1".equals(owner)){
			conditionSql += "and p.owner = '"+emp.getEmployeeGuid()+"'";
		}
		if(CommonUtil.stringNotNULL(itemid)){
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql += " and i.id  in ("+pendingItemId+")";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Pending> list;
		int count = tableInfoExtendService.getCountOfFollow(conditionSql, emp.getEmployeeGuid(), status);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = tableInfoExtendService.getFollowList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,status);
		if(getSession().getAttribute("myPendItems") == null){
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
			getRequest().setAttribute("myPendItems", items);
		}
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("wfTitle", title);
		getRequest().setAttribute("itemName", itemName);
		getRequest().setAttribute("statustype", statustype);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("status", status);
		return "followList";
	}
	
	/**
	 * 描述：督办列表
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-4-13 上午8:48:36
	 */
	public String getOutOfDateList() {
		String title = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");

		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " and (p.action_status is null or p.action_status!=2) ";
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("wfTitle", title);
		}
		
		conditionSql +=" and (p.jdqxdate <= sysdate or (p.jdqxdate > sysdate and sysdate > p.jdqxdate-3)) ";

		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		int count = tableInfoExtendService.getCountOfOutOfDate(conditionSql, "");                                                                         	
		Paging.setPagingParams(getRequest(), pageSize, count);                                                                                                        	
		// 包含是否显示推送按钮                                                                                                                                                 	
		List<Map<String, String>> list = tableInfoExtendService.getOutOfDateList(conditionSql, Paging.pageIndex, Paging.pageSize);                                                                                            	
		getRequest().setAttribute("list", list);
		                                                                                                                                                              
		if(getSession().getAttribute("myPendItems") == null){
			List<WfItem> items = itemService.getItemListByDeptIds("","");
			getRequest().setAttribute("myPendItems", items);
		}
		
		return "outOfDateList";
	}
	
	/**
	 * 描述：执行补发操作
	 * 作者:蒋烽
	 * 创建时间:2017-5-18 下午5:54:58
	 */
	public void doReissue(){
		String processId = getRequest().getParameter("processId");
		String userId = getRequest().getParameter("userId");
		WfProcess wfp = tableInfoService.getProcessById(processId);
		if(null != wfp){
			if(StringUtils.isNotBlank(userId)){
				List<WfProcess> wfps = tableInfoService.findWfProcessList(wfp.getWfInstanceUid(), wfp.getStepIndex()+1);
				if(null != wfps && wfps.size()>0){
					String[] userIds = userId.split(",");
					for (String string : userIds) {
						WfProcess wfProcess = wfps.get(0);
						wfProcess.setWfProcessUid(UuidGenerator.generate36UUID());
						wfProcess.setUserUid(string);
						wfProcess.setIsOver("NOT_OVER");
						wfProcess.setApplyTime(new Date());
						wfProcess.setJssj(null);
						wfProcess.setFinshTime(null);
						tableInfoService.addProcess(wfProcess);
					}
					toPage("success");
				}else{
					toPage("fail");
				}
			}
		}else{
			toPage("fail");
		}
	}
	
	
	public void followShip(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = getRequest().getParameter("userId");
		String instanceId = getRequest().getParameter("instanceId");
		String oldInstanceId = getRequest().getParameter("oldInstanceId");
		String type = getRequest().getParameter("type");
		String employeeId = "";
		if(null != emp){
			employeeId = emp.getEmployeeGuid();
		}else{
			employeeId = userId;
		}
		if(StringUtils.isNotBlank(type) && type.equals("1")){
			List<FollowShip> list = tableInfoExtendService.getFollowShips(instanceId, employeeId, oldInstanceId);
			if(null != list && list.size()>0){
				if(StringUtils.isBlank(oldInstanceId)){
					FollowShip oldShip = list.get(0);
					oldShip.setIsRead("0");
					tableInfoExtendService.editFollowShip(oldShip);
				}
			}else{
				FollowShip followShip = new FollowShip();
				if(StringUtils.isNotBlank(oldInstanceId)){
					followShip.setOldInstanceId(oldInstanceId);
				}else{
					followShip.setEmployeeGuid(employeeId);
					followShip.setIsRead("0");
				}
				followShip.setInstanceId(instanceId);
				tableInfoExtendService.addFollowShip(followShip);
			}
		}else if(StringUtils.isNotBlank(type) && type.equals("3")){
			tableInfoExtendService.deleteFollowShip(instanceId, employeeId);
		}
		toPage("success");
	}
	
	/**
	 * 描述：办结办件在办
	 * 作者:蒋烽
	 * 创建时间:2017-7-14 上午8:38:58
	 */
	public void changOverFile(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String processId = getRequest().getParameter("processId");
		WfProcess wfp = tableInfoService.getProcessById(processId);
		if(wfp != null){
			List<WfProcess> deletelist = new ArrayList<WfProcess>();
			if(wfp.getIsEnd().equals("1") && wfp.getUserUid().equals(emp.getEmployeeGuid())){//直接删除当前步骤，更新上一步为待办
				deletelist.add(wfp);
				tableInfoService.deleteWfProcesss(deletelist);
				String instanceId = wfp.getAllInstanceid();
				if(StringUtils.isBlank(instanceId)){
					instanceId = wfp.getWfInstanceUid();
				}
				tableInfoService.delEndInstanceId(instanceId);
				List<WfProcess> wfs = tableInfoService.findStepWfPListByUserId(wfp.getWfInstanceUid(), wfp.getStepIndex()-1, emp.getEmployeeGuid());
				for (WfProcess wfProcess : wfs) {
					wfProcess.setIsOver("NOT_OVER");
					wfProcess.setFinshTime(null);
					tableInfoService.update(wfProcess);
				}
				toPage("10000");
			}else{//拿出instanceId查出最后一步
				WfProcess endWfp = tableInfoService.getOverWfpByInstanceId(wfp.getWfInstanceUid());
				deletelist.add(endWfp);
				tableInfoService.deleteWfProcesss(deletelist);
				String instanceId = endWfp.getAllInstanceid();
				if(StringUtils.isBlank(instanceId)){
					instanceId = endWfp.getWfInstanceUid();
				}
				tableInfoService.delEndInstanceId(instanceId);
				List<WfProcess> wfs = tableInfoService.findStepWfPListByUserId(endWfp.getWfInstanceUid(), endWfp.getStepIndex()-1, emp.getEmployeeGuid());
				for (WfProcess wfProcess : wfs) {
					wfProcess.setIsOver("NOT_OVER");
					wfProcess.setFinshTime(null);
					tableInfoService.update(wfProcess);
				}
				toPage("10000");
			}
		}else{
			toPage("10001");//processId不正确，请联系管理员
		}
		
		
	}
	
	public void doSendAgain(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String processId = getRequest().getParameter("processId");
		String userIds = getRequest().getParameter("userIds");
		String userId = getRequest().getParameter("userId");
		if(StringUtils.isBlank(userId)){
			userId = emp.getEmployeeGuid();
		}
		WfProcess wfp = tableInfoService.getProcessById(processId);
		if(null != wfp){
			WfItem item = itemService.getItemById(wfp.getItemId());
			if(StringUtils.isNotBlank(userIds)){
				String[] userIdss = userIds.split(",");
				WebSocketUtil util = new WebSocketUtil();
				for (String string : userIdss) {
					if(string.length()>38){
						break;
					}
					String wfProcessId = UuidGenerator.generate36UUID();
					List<WfProcess> wfps = tableInfoService.findStepWfPListByUserId(wfp.getWfInstanceUid(), wfp.getStepIndex(),string);
					if(null != wfps && wfps.size()>0){
						for (WfProcess wfProcess : wfps) {
							if(processId.equals(wfProcess.getWfProcessUid())){
								continue;
							}
							wfProcess.setIsrepeated(1);
							wfProcess.setFinshTime(new Date());
							wfProcess.setIsOver("OVER");
							tableInfoService.update(wfProcess);
							try {
								util.delBadge(wfProcess.getUserUid(), "", "");
							} catch (org.json.JSONException e) {
								e.printStackTrace();
							}
						}
					}
					wfp.setUserUid(string);
					wfp.setIsOver("NOT_OVER");
					wfp.setApplyTime(new Date());
					wfp.setJssj(null);
					wfp.setFinshTime(null);
					wfp.setFromUserId(userId);
					wfp.setWfProcessUid(wfProcessId);
					tableInfoService.addProcess(wfp);
					try {
						util.apnsPush(wfp.getProcessTitle(), userId, "", "", "", string);
					} catch (org.json.JSONException e) {
						e.printStackTrace();
					}
					sycnPendToChat(wfp,userId);
				}
				
				toPage("success");
				
				//发短信
				String canSendMsg = getRequest().getParameter("canSendMsg");
				if(CommonUtil.stringIsNULL(canSendMsg)){
					canSendMsg = getRequest().getParameter("sms");
				}
				//需要发短信的userId
				String sendMsgUserId = getRequest().getParameter("sendMsgId");
				if((StringUtils.isNotBlank(canSendMsg) && canSendMsg.equals("1")) || StringUtils.isNotBlank(sendMsgUserId)){
					String msgTo = "";
					if(StringUtils.isNotBlank(canSendMsg) && canSendMsg.equals("1")){
						msgTo = userIds;
					}else{
						if(StringUtils.isNotBlank(sendMsgUserId)){
							msgTo = sendMsgUserId;
						}
					}
					List<Employee> empList = tableInfoService.findEmpsByUserIds(msgTo);
					String numbers = "";
					if(null != empList && empList.size()>0){
						for (Employee employee : empList) {
							numbers += employee.getEmployeeMobile()+",";
						}
					}
					if(StringUtils.isNotBlank(numbers)){
						numbers = numbers.substring(0,numbers.length()-1);
					}
					SendMsgUtil msgUtil = new SendMsgUtil();
					Map<String, String> map = new HashMap<String, String>();
					map.put("itemName", item.getVc_sxmc());
					map.put("sendUserName", emp.getEmployeeName());
					map.put("title", wfp.getProcessTitle());
					map.put("senderId", "");
					map.put("receiverName", "");
					msgUtil.sendMsg(numbers, "trueWorkflow", map);
				}
			}
		}else{
			toPage("fail");
		}
	}
	
	/**
	 * 描述：阅文待办列表
	 * TableInfoAction
	 * String
	 * 作者:蒋烽
	 * 创建时间:2018 上午11:14:26
	 */
	public String getPendingListOfReadFile() {
		String title = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitDept = getRequest().getParameter("commitDept");
		String commitUser = getRequest().getParameter("commitUser");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		String itemType = getRequest().getParameter("itemType");
		String pagesize = getRequest().getParameter("pageSize");
		
		String siteId = getRequest().getParameter("siteId");
		
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		String itemType2 = getRequest().getParameter("itemType2");

		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " and (p.action_status is null or p.action_status!=2) ";
		//查询传阅件
		conditionSql += " and (p.wf_instance_uid in (select dcv.instanceid from document_circulation_view dcv) and n.wfn_onekeyhandle = 1)"; 
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and p.process_title like '%" + title2.trim()+ "%' escape '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
		}
		if (CommonUtil.stringNotNULL(commitUser)) {
			commitUser = commitUser.trim();
			conditionSql += " and (select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid ) like  '%"+commitUser+"%' escape '\\' ";
			getRequest().setAttribute("commitUser", commitUser);
		}
		
		if (CommonUtil.stringNotNULL(commitDept)) {
			commitDept = commitDept.trim();
			conditionSql += " and d.DEPARTMENT_NAME = '"+commitDept+"' ";
			getRequest().setAttribute("commitDept", commitDept);
		}
		if(StringUtils.isNotBlank(commitTimeFrom3)){
			commitTimeFrom3 = commitTimeFrom3.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom4 = commitTimeFrom3 + " 00:00:00";
			conditionSql +=" and p.APPLY_TIME >= to_date('"+commitTimeFrom4+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeFrom2", commitTimeFrom3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeFrom)) {
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
				String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
				conditionSql +=" and p.APPLY_TIME >= to_date('"+commitTimeFrom2+"','yyyy-mm-dd hh24:mi:ss') ";	
				getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
			}
		}
		if(StringUtils.isNotBlank(commitTimeTo3)){
			commitTimeTo3 = commitTimeTo3.trim().replaceAll("'","\\'\\'");
			String commitTimeTo4 = commitTimeTo3 + " 23:59:59";
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo4+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeTo2", commitTimeTo3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeTo)) {
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
				String commitTimeTo2 = commitTimeTo + " 23:59:59";
				conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo2+"','yyyy-mm-dd hh24:mi:ss') ";	
				getRequest().setAttribute("commitTimeTo", commitTimeTo);
			}
		}
		
		if(StringUtils.isNotBlank(itemType2)){
			conditionSql +=" and i.vc_sxlx = '"+itemType2+"' ";
			getRequest().setAttribute("itemType2", itemType2);
		}else{
			if(StringUtils.isNotBlank(itemType)){
				conditionSql +=" and i.vc_sxlx = '"+itemType+"' ";
			}
		}
		
		//视图字段
		String wh = getRequest().getParameter("wh");
		String lwdw = getRequest().getParameter("lwdw");
		wh = CommonUtil.stringNotNULL(wh) ? wh.replaceAll("'",
				"\\'\\'") : "";
		lwdw = CommonUtil.stringNotNULL(lwdw) ? lwdw.replaceAll("'",
				"\\'\\'") : "";
		
		if (CommonUtil.stringNotNULL(wh)) {
			wh = wh.trim();
			conditionSql += " and v.wh like '%" + wh.trim()+ "%' escape '\\'";
		}
		getRequest().setAttribute("wh", wh);
		if (CommonUtil.stringNotNULL(lwdw)) {
			lwdw = lwdw.trim();
			conditionSql += " and v.lwdw like '%" + lwdw.trim()+ "%' escape '\\'";
			getRequest().setAttribute("lwdw", lwdw);
		}
		
		String status = getRequest().getParameter("status");
		if(status != null && status.equals("3")){
	    	conditionSql +=" and p.jdqxdate <= sysdate ";
	    	getRequest().setAttribute("status", status);
		}
		
		String itemids = "";
		if(StringUtils.isNotBlank(siteId)){
			if(StringUtils.isNotBlank(itemid)){//去取两个itemId的交集
				itemids = this.getIntersectItemId(itemid, siteId);
			}else{
				itemids = itemService.getItemIdsBydeptId(siteId);
			}
		}else{
			itemids = itemid;
		}
		
		String pendingItemId = "";
		if(CommonUtil.stringNotNULL(itemids)){	//根据itemid查询
			String[] itemIds = itemids.split(",");
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));

		List<Pending> list;
		int count = pendingService.getCountOfPending(conditionSql,emp.getEmployeeGuid(), "");
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = pendingService.getPendingList(conditionSql, emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		// 区分工作流的待办列表获取
		pendingService.initRemainTime(list);// 设定节点期限
		pendingService.initDelayItem(list);
		/*//拿出最新的意见
		//优化查询速度时去掉，改成点一键办理
		/*for (Pending pending : list) {
			TrueJson trueJson = trueJsonService.findNewestTrueJson(pending.getWf_instance_uid());
			if(trueJson!=null){
				pending.setCommentJson(trueJson.getTrueJson());
			}
		}*/
		getRequest().setAttribute("list", list);
		String superDeptId = "";
		if(emp != null){
			Department dept = departmentService.findDepartmentById(emp.getDepartmentGuid());
			if(dept != null){
				superDeptId = dept.getSuperiorGuid();
			}
			if(StringUtils.isNotBlank(siteId)){
				superDeptId = siteId;
				getRequest().setAttribute("siteId", siteId);
			}
		}
		List<WfItem> items = itemService.getItemListByDeptIds( "'" + superDeptId + "'","");
		getRequest().setAttribute("myPendItems", items);
		String state = getRequest().getParameter("state");
		getRequest().setAttribute("state", state);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("itemType", itemType);
		
		return "pendingListOfReadFile";
	}
	
	public void getLatestTrueJson(){
		String instanceId = getRequest().getParameter("instanceId");
		TrueJson trueJson = trueJsonService.findNewestTrueJson(instanceId);
		String commentJson = "";
		if(trueJson!=null){
			commentJson = trueJson.getTrueJson();
		}
		toPage(commentJson);
	}
	
	
	/**
	 * 
	 * 描述：已办事项列表;  status为状态位  2:已办结; 4:已办未结
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-8-19 下午3:35:20
	 */
	public String getOverListOfReadFile() {
		
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String statustype = getRequest().getParameter("statustype");
		String itemid = getRequest().getParameter("itemid");
		String owner = getRequest().getParameter("owner");
		String title = getRequest().getParameter("wfTitle");
		String status = getRequest().getParameter("status") == null ? "" : getRequest().getParameter("status");
		String itemType = getRequest().getParameter("itemType");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		String siteId = getRequest().getParameter("siteId");
		String pagesize = getRequest().getParameter("pageSize");
		
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		title2 = CommonUtil.stringNotNULL(title2) ? title2.replaceAll("'","\\'\\'") : "";
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		
		String conditionSql = " and (p.wf_instance_uid in (select dcv.instanceid from document_circulation_view dcv) and t.wfn_onekeyhandle = 1)";
		if(StringUtils.isNotBlank(title2)){
			conditionSql += "and p.process_title like '%" + title2 + "%'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if(StringUtils.isNotBlank(title)){
				conditionSql += "and p.process_title like '%" + title + "%'";
			}
		}
		
		if(CommonUtil.stringNotNULL(owner)&&"1".equals(owner)){
			conditionSql += "and p.owner = '"+emp.getEmployeeGuid()+"'";
		}
		
		if(StringUtils.isNotBlank(commitTimeFrom3)){
			commitTimeFrom3 = commitTimeFrom3.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom4 = commitTimeFrom3 + " 00:00:00";
			conditionSql +=" and p.FINSH_TIME >= to_date('"+commitTimeFrom4+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeFrom2", commitTimeFrom3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeFrom)) {
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
				String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
				conditionSql +=" and p.FINSH_TIME >= to_date('"+commitTimeFrom2+"','yyyy-mm-dd hh24:mi:ss') ";	
				getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
			}
		}
		if(StringUtils.isNotBlank(commitTimeTo3)){
			commitTimeTo3 = commitTimeTo3.trim().replaceAll("'","\\'\\'");
			String commitTimeTo4 = commitTimeTo3 + " 23:59:59";
			conditionSql +=" and p.FINSH_TIME <= to_date('"+commitTimeTo4+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeTo2", commitTimeTo3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeTo)) {
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
				String commitTimeTo2 = commitTimeTo + " 23:59:59";
				conditionSql +=" and p.FINSH_TIME <= to_date('"+commitTimeTo2+"','yyyy-mm-dd hh24:mi:ss') ";	
				getRequest().setAttribute("commitTimeTo", commitTimeTo);
			}
		}
		//视图字段
		String wh = getRequest().getParameter("wh");
		String lwdw = getRequest().getParameter("lwdw");
		wh = CommonUtil.stringNotNULL(wh) ? wh.replaceAll("'",
				"\\'\\'") : "";
		lwdw = CommonUtil.stringNotNULL(lwdw) ? lwdw.replaceAll("'",
				"\\'\\'") : "";
		
		if (CommonUtil.stringNotNULL(wh)) {
			wh = wh.trim();
			conditionSql += " and v.wh like '%" + wh.trim()+ "%' escape '\\'";
		}
		getRequest().setAttribute("wh", wh);
		if (CommonUtil.stringNotNULL(lwdw)) {
			lwdw = lwdw.trim();
			conditionSql += " and v.lwdw like '%" + lwdw.trim()+ "%' escape '\\'";
			getRequest().setAttribute("lwdw", lwdw);
		}
		
		String itemids = "";
		if(StringUtils.isNotBlank(siteId)){
			if(StringUtils.isNotBlank(itemid)){//去取两个itemId的交集
				itemids = this.getIntersectItemId(itemid, siteId);
			}else{
				itemids = itemService.getItemIdsBydeptId(siteId);
			}
		}else{
			itemids = itemid;
		}
		
		String pendingItemId = "";
		if(CommonUtil.stringNotNULL(itemids)){
			String[] itemIds = itemids.split(",");
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql += " and i.id  in ("+pendingItemId+")";
		}
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Pending> list;
		// 分为办理和已办结 加上 流程名
		int count = tableInfoService.getCountOfOver(conditionSql,emp.getEmployeeGuid(),status);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = tableInfoService.getOverList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,status);
		pendingService.setBackStatus(list, emp.getIsAdmin(), emp.getEmployeeGuid());
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("wfTitle", title);
		getRequest().setAttribute("statustype", statustype);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("itemType", itemType);
		getRequest().setAttribute("siteId", siteId);
		return "overListOfReadFile";
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * TableInfoExtendAction
	 * void
	 * 作者:蒋烽
	 * 创建时间:2018 下午2:52:58
	 */
	public void getAutoJson(){
		long startTime = System.currentTimeMillis();
		long endTime ;
		JSONObject jsonObject = getJSONObject();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String processId = "";
		String userId = "";
		if(null != jsonObject){
			processId = (String) jsonObject.get("processId");
			userId = (String) jsonObject.get("userId");
		}else{
			processId = getRequest().getParameter("processId");
			userId = getRequest().getParameter("userId");
		}
		if(emp == null){
			emp = tableInfoService.findEmpByUserId(userId);
		}
		WfProcess wfProcess = pendingService.getProcessByID(processId);
		endTime = System.currentTimeMillis();
		System.out.println("---------执行getAutoJson断点1时间------："+((endTime-startTime)/1000.0));
		//根据instanceid获取最新的意见
		String instanceId = getRequest().getParameter("instanceId");
		if(CommonUtil.stringIsNULL(instanceId)){
			instanceId = wfProcess.getWfInstanceUid();
		}
//		TrueJson trueJson = trueJsonService.findNewestTrueJson(instanceId);
		TrueJson trueJson = trueJsonService.findNewestTrueJsonByInstanceId(instanceId);
		endTime = System.currentTimeMillis();
		System.out.println("---------执行getAutoJson断点2时间------："+((endTime-startTime)/1000.0));
		String commentJson = "";
		if(trueJson!=null){
			commentJson = trueJson.getTrueJson();
		}
		
		JSONObject outJson = new JSONObject();
		if(null != wfProcess){
			JSONObject obj = new JSONObject();
			String allInstanceId = wfProcess.getAllInstanceid();
			if(StringUtils.isBlank(allInstanceId)){
				allInstanceId = wfProcess.getWfInstanceUid();
			}
			try {
				endTime = System.currentTimeMillis();
				System.out.println("---------执行getAutoJson断点3时间------："+((endTime-startTime)/1000.0));
				boolean isInUse = checkIsInuse(allInstanceId);
				endTime = System.currentTimeMillis();
				System.out.println("---------执行getAutoJson断点4时间------："+((endTime-startTime)/1000.0));
				if(!isInUse){
					String truefromEle = getCanWriteTrueform(emp, wfProcess.getWfUid(), wfProcess.getNodeUid());
					obj.put("userId", emp.getEmployeeGuid());
					obj.put("username",emp.getEmployeeName());
					obj.put("processId", processId);
					obj.put("nodeId", wfProcess.getNodeUid());
					WfNode wfNode = tableInfoService.getWfNodeById(wfProcess.getNodeUid());
					if(wfNode!=null && wfNode.getWfn_autoNoname()!=null && wfNode.getWfn_autoNoname()==1){
						outJson.put("autoNoname", "true");
					}else{
						outJson.put("autoNoname", "false");
					}
					ZwkjForm zwkjForm = zwkjFormService.getFrom(wfProcess.getWfUid(), wfProcess.getFormId(), instanceId);
					Integer fontSize = zwkjForm.getFontSize();
					Map<String, String> formStylemap = new HashMap<String, String>();
					List<FormStyle> formStylelist = zwkjFormService.getFormStyle(formStylemap);
					if(fontSize != null && fontSize != 0){
						outJson.put("fontSize", fontSize+"");
					}else{
						if(formStylelist != null && formStylelist.size() > 0){
							FormStyle formStyle = formStylelist.get(0);
							if(formStyle != null){
								outJson.put("fontSize", formStyle.getFontSize());
							} 
						}
					}
					outJson.put("userInfo", obj.toString());
					outJson.put("areaId", truefromEle);
					outJson.put("result", "true");
					outJson.put("trueJson", commentJson);
				}else{
					outJson.put("result", "false");
					outJson.put("userName", userName);
				}
			} catch (Exception e) {
				outJson.put("result", "false");
			}
		}else{
			outJson.put("result", "false");
		}
		toPage(outJson.toString());
		endTime = System.currentTimeMillis();
		System.out.println("---------结束getAutoJson时间------："+((endTime-startTime)/1000.0));
	}
	
	public boolean checkIsInuse(String allInstanceId){
		long startTime = System.currentTimeMillis();
		long endTime;
		boolean flag = false;
		HttpClient client = new HttpClient();
		String socketUrl=SystemParamConfigUtil.getParamValueByParam("sockIp");
		HttpMethod method = getGetMethod(socketUrl+"/checkIsInuse?instanceId="+allInstanceId);
		endTime = System.currentTimeMillis();
		System.out.println("---------执行checkIsInUse断点1------："+((endTime-startTime)/1000.0));
		try {
			client.executeMethod(method);
			String result=method.getResponseBodyAsString();
			JSONObject obj = JSONObject.fromObject(result);
			endTime = System.currentTimeMillis();
			System.out.println("---------执行checkIsInUse断点2------："+((endTime-startTime)/1000.0));
			flag = obj.getBoolean("isInUse");
			if(flag){
				userName = "";
				userName = obj.getString("userName");
			}
		} catch (JSONException e) {
			LOGGER.error("json转换异常");
		} catch (HttpException e) {
			LOGGER.error("webSocket消息数累加接口,访问失败");
		} catch (IOException e) {
			LOGGER.error("webSocket消息数累加接口,访问失败");
		}finally{
			method.releaseConnection();
		}
		return flag;
	}
	
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param url
	 * @return HttpMethod
	 * 作者:蒋烽
	 * 创建时间:2018-2-1 下午4:56:50
	 */
	private  HttpMethod getGetMethod(String url){
		GetMethod get=new GetMethod(url);
		return get;
	}
	
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * TableInfoAction
	 * String
	 * 作者:蒋烽
	 * 创建时间:2018 下午2:35:48
	 */
	public String getCanWriteTrueform(Employee emp,String workflowid,String nodeId){
		Map<String, String> perLimit = formPermitService.getEmployeeLimit(emp.getEmployeeGuid(), emp.getDepartmentGuid(), nodeId, workflowid);
		if (perLimit != null && !("").equals(perLimit) && perLimit.size() != 0) {
			for (Map.Entry<String, String> entry : perLimit.entrySet()) { // 格式---VC_SQR:0,text
				String isWrite = entry.getValue().split(",")[0];
				if(isWrite.equals("2")){
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	/**                                                                                                                                    	
	 *                                                                                                                                     	
	 * 描述：推送消息内容到通讯服务器中                                                                                                                    	
	 * @param oldProcess void                                                                                                              	
	 * 作者:蔡亚军                                                                                                                              	
	 * 创建时间:2016-7-12 上午9:57:52                                                                                                            	
	 */                                                                                                                                    	
	public void sycnPendToChat(WfProcess wfp, String userId){                                                                              	
		RemoteLogin remote = new RemoteLogin();                                                                                            	
		Employee emp = null;                                                                                                               	
		if(userId!=null){                                                                                                                  	
			emp = tableInfoService.findEmpByUserId(userId);                                                                                	
		}else{                                                                                                                             	
			emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);                                                         	
		}                                                                                                                                  	
		boolean checkUser = RemoteLogin.checkPassed;
		if(checkUser){
			//发送到第三方短信接口                                                                                                                   	
			String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"+ getRequest().getLocalPort() + getRequest().getContextPath();	
			String url = serverUrl+"/table_openPendingForm.do";                                                                            	
			String json = "";                                                                                                              	
			String itemId =	wfp.getItemId();                                                                                               	
			WfItem wfItem = itemService.getItemById(itemId);                                                                               	
			String itemName = wfItem.getVc_sxmc();
			//String msgContent = "您有一条待办,标题为'"+title+"',地址为："+url;
			String xxlx = wfItem.getVc_xxlx();
			String xmlxName = getXxlx(xxlx);
			String title = wfp.getProcessTitle();                                                                                          	
			json += "{\"name\":\""+ title+"\",\"itemId\":\""+itemId+"\",\"xxlx\":\""                                                       	
					+xmlxName+"\",\"itemName\":\""+itemName+"\",";                                                                         	
			//获取processId                                                                                                                  	
			String commentJson = json + "\"processId\":\""+wfp.getWfProcessUid()+"\",";                                                    	
			Employee emp_xto = tableInfoService.findEmpByUserId(wfp.getUserUid());                                                         	
			String pendingUrl = url +"?processId="+wfp.getWfProcessUid()+"&isDb=true&itemId="+wfp.getItemId()+"&formId="+wfp.getFormId();  	
			MsgSend	msgSend = new MsgSend();
			/*if(StringUtils.isNotBlank(superItemId) && superItemId.equals(item.getId())){
				msgSend.setType("5");
			}*/
			msgSend.setSendUserId(emp.getEmployeeGuid());                                                                                  	
			msgSend.setSendUserName(emp.getEmployeeLoginname());                                                                           	
			msgSend.setRecUserId(emp_xto.getEmployeeGuid());                                                                               	
			msgSend.setRecUserName(emp_xto.getEmployeeLoginname());                                                                        	
			msgSend.setSendDate(new Date());                                                                                               	
			msgSend.setProcessId(wfp.getWfProcessUid());                                                                                   	
			msgSend.setStatus(2);                                                                                                          	
			commentJson += "\"url\":\""+pendingUrl+"\"}";                                                                                  	
			msgSend.setContent(commentJson);
			msgSend.setTitle(wfp.getProcessTitle());
			msgSend.setItemType(wfItem.getVc_sxmc());
			tableInfoService.saveMsgSend(msgSend); 
			JSONObject obj = new JSONObject();
			obj = MsgToObj.msgToObj(msgSend, serverUrl);
			remote.SendUsersMessage(msgSend.getSendUserId(), emp.getEmployeeName(), obj.toString(), emp_xto.getEmployeeGuid(), "");
		}
	}   
	
	public void expExcelOfDofileList() throws UnsupportedEncodingException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Integer pageSize = 50;
		Calendar calendar = Calendar.getInstance();
		String year = calendar.get(Calendar.YEAR)+"";
		String [] rowsName = {"序号","公文标题","公文文号","当前步骤","当前步骤办理人","登记时间","状态"};
		//机关事务局定制字段
		String [] specialRows = {"序号","公文标题","公文文号","来文单位","公文编号","紧急程度","当前步骤","当前步骤办理人","登记时间","状态"};
		//发改委定制字段
		String [] fgwRows = {"序号","公文标题","公文文号","当前步骤","当前步骤办理人","登记时间","状态","来文单位","收文类型","是否重点督办","督办类型","督办时限"};
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		String wfTitle = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		String wh = getRequest().getParameter("wh");
		String lwdw = getRequest().getParameter("lwdw");
		String status1 = getRequest().getParameter("status");
		String departId = getRequest().getParameter("departId");
		String isShowTestDept = getRequest().getParameter("isShowTestDept");
		String isFavourite = getRequest().getParameter("isFavourite");
		
		String btfw =  getRequest().getParameter("btfw"); //发文标题
		
		String fwh = getRequest().getParameter("fwh"); //发文号
		
		String fsbm = getRequest().getParameter("fsbm"); //发送部门
		
		String fwbeginTime = getRequest().getParameter("fwbeginTime");//fw开始时间
		
		String fwendTime = getRequest().getParameter("fwendTime");//fw结束时间
		
		String isShowExp = getRequest().getParameter("isShowExp");//发改委导出定制
		String isFgw = getRequest().getParameter("isFgw");//发改委导出定制
		
		String excelSpecial = getRequest().getParameter("excelSpecial");//机关事务局定制
		
		boolean addField = false;
		
		//需要定制导出的事项名称
		String fgwExpItemName = SystemParamConfigUtil.getParamValueByParam("fgwExpItemName");
		
		if(CommonUtil.stringNotNULL(excelSpecial)&&"yes".equals(excelSpecial)){
			addField = true;
		}

		String bigDepId= null;
		String deptIds = (String) getSession().getAttribute(MyConstants.DEPARMENT_ID);
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(StringUtils.isNotBlank(deptIds)){
			Department dept = departmentService.findSiteDept(deptIds);
			if(null != dept){
				bigDepId = dept.getDepartmentGuid();
			}
		}
		if(employee != null && StringUtils.isBlank(bigDepId)){
			String deptId = employee.getDepartmentGuid();
			Department dep = departmentService.findDepartmentById(deptId);
			if(dep != null){
				String superDeptId = dep.getSuperiorGuid();
				bigDepId = superDeptId;
			}else{
				System.out.println("获取部门失败！");
			}
		}else{
			System.out.println("session中人员信息为空！");
		}
		
		String conditionSql = "";// 查询条件
		if(StringUtils.isNotBlank(wfTitle)){
			wfTitle = URLDecoder.decode(wfTitle,"UTF-8");
			conditionSql = " and t.dofile_title like '%" + wfTitle.trim() + "%' ";
		}
		
		if (CommonUtil.stringNotNULL(itemName)) {
			itemName = URLDecoder.decode(itemName,"UTF-8");
			conditionSql += " and i.vc_sxmc = '" + itemName + "' ";
		}

		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
			conditionSql +=" and t.dotime >= to_date('"+commitTimeFrom2+"','yyyy-mm-dd hh24:mi:ss') ";	
		}

		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			String commitTimeTo2 = commitTimeTo + " 23:59:59";
			conditionSql +=" and t.dotime <= to_date('"+commitTimeTo2+"','yyyy-mm-dd hh24:mi:ss') ";	
		}
		
		if (!CommonUtil.stringIsNULL(btfw)) {
			conditionSql += "and t.title like '%" + btfw + "%'";
		}
		if (!CommonUtil.stringIsNULL(fwh)) {
			conditionSql += "and  t.wh like '%" + fwh + "%'";
		}
		if (!CommonUtil.stringIsNULL(fsbm)) {
			fsbm = fsbm.trim();
			conditionSql += "and  (t.zbbm like '%" + fsbm
					+ "%' or t.csbm like '%" + fsbm + "%')";
		}
		if (!CommonUtil.stringIsNULL(fwbeginTime)) {
			conditionSql += "and p.apply_time > to_date('" + fwbeginTime + " 00:00:00"
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}
		if (!CommonUtil.stringIsNULL(fwendTime)) {
			conditionSql += "and p.apply_time < to_date('" + fwendTime + " 23:59:59"
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}
		
		//在办办结
		if(StringUtils.isNotBlank(status1)){
			if(status1.equals("4")){//在办
				conditionSql += " and t.instanceid not in (select e.instanceid from t_wf_core_end_instanceid e)";
			}else if(status1.equals("2")){//办结
				conditionSql += " and t.instanceid in (select e.instanceid from t_wf_core_end_instanceid e)";
			}
		}
		
		//更具部门id查询
		if(StringUtils.isNotBlank(departId)){
			conditionSql += " and p.user_uid in (select e.employee_guid from zwkj_employee e where e.department_guid = '" + departId + "')";
		}
		
		if (CommonUtil.stringNotNULL(wh)) {
			wh = URLDecoder.decode(wh,"UTF-8");
			wh = wh.replaceAll("'", "\\'\\'");
			wh = wh.trim();
			conditionSql += " and v.wh like '%" + wh.trim()+ "%' escape '\\'";
		}
		if (CommonUtil.stringNotNULL(lwdw)) {
			lwdw = URLDecoder.decode(lwdw,"UTF-8");
			lwdw = lwdw.replaceAll("'","\\'\\'");
			lwdw = lwdw.trim();
			conditionSql += " and v.lwdw like '%" + lwdw.trim()+ "%' escape '\\'";
		}
		int count;
		if(CommonUtil.stringNotNULL(isFavourite)){
			String userId  =  employee.getEmployeeGuid();
			count = tableInfoService.getCountOfDoFileFavourites(bigDepId, conditionSql, userId);
		}else{
			if(StringUtils.isNotBlank(isShowTestDept) && isShowTestDept.equals("1")){
			}else{
				String testDeptId = SystemParamConfigUtil.getParamValueByParam("testDeptId");
				conditionSql += " and dep.department_guid != '" + testDeptId + "' ";
			}
			count = tableInfoService.getCountDoFiles(bigDepId, conditionSql);
		}
		int k = getCount(count,50);
		for (int i = 0; i < k; i++) {
			int pageIndex = pageSize * i;
			List<DoFile> doFileList = new ArrayList<DoFile>(); 
			if(CommonUtil.stringNotNULL(isFavourite)){
				doFileList = tableInfoService.getDoFileFavouriteList(bigDepId, conditionSql, employee.getEmployeeGuid(), Paging.pageIndex, count);
			}else{
				doFileList = tableInfoService.getDoFileList(bigDepId,conditionSql, pageIndex, pageSize);
				tableInfoService.setDoFileApplyTime(doFileList);
				if(StringUtils.isNotBlank(isShowExp) && "1".equals(isShowExp) ){
				}
			}
			int j = 1;
			for (DoFile doFile : doFileList) {
				if(addField){  //机关事务局定制添加字段
					Object[] objs = new Object[10];
					objs[0] = pageIndex + j;
					j++;
					objs[1] = doFile.getDoFile_title();
					//获取文号
					String a = tableInfoService.findWh(doFile.getInstanceId());
					objs[2] = a;
					
					//获取来文单位
					String b = tableInfoService.findLwdw(doFile.getInstanceId());
					objs[3] = b;
					
					//公文编号
					String c = tableInfoService.getViewBhByInstanceId(doFile.getInstanceId());
					objs[4] = c;
					
					String d = tableInfoService.findJjcdToString(doFile.getInstanceId());
					objs[5] = d;
					
					if(doFile.getProcessId()!=null&&!"".equals(doFile.getProcessId())){
						WfProcess process = tableInfoService.getProcessById(doFile.getProcessId());
						if(process.getNodeUid()!=null&&!"".equals(process.getNodeUid())){
							 WfNode node = tableInfoService.getWfNodeById(process.getNodeUid());
							 objs[6] = node.getWfn_name();
						}
					}
					objs[7] = doFile.getEntrustName();
					try {
						objs[8] = sdf.format(doFile.getApplyTime()!=null?sdf.parse(doFile.getApplyTime()):new Date());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Integer result = doFile.getDoFile_result();
					String status = "";
					if (result.equals(0)) {
						status = "未办";
					}else if(result.equals(1)){
						status = "已办";
					}else if(result.equals(2)){
						status = "办结";
					}
					
					objs[9] = status;
					dataList.add(objs);
				}else if(StringUtils.isNotBlank(isFgw) && "1".equals(isFgw) && StringUtils.isNotBlank(fgwExpItemName)
						&& itemName!=null && fgwExpItemName.equals(itemName)){
					int length = 12;
					Department dept = null;
					if(StringUtils.isBlank(departId)){
					}else{
						fgwRows =new String[]{"序号","公文标题","公文文号","当前步骤","当前步骤办理人","登记时间","状态","来文单位","收文类型","是否重点督办","督办类型","督办时限","处室"};
						length++;
						dept = departmentService.findDepartmentById(departId);
					}
					Object[] objs = new Object[length];
					objs[0] = pageIndex + j;
					j++;
					objs[1] = doFile.getDoFile_title();
					//获取文号
					String a = tableInfoService.findWh(doFile.getInstanceId());
					objs[2] = a;
					if(doFile.getProcessId()!=null&&!"".equals(doFile.getProcessId())){
						WfProcess process = tableInfoService.getProcessById(doFile.getProcessId());
						if(process.getNodeUid()!=null&&!"".equals(process.getNodeUid())){
							 WfNode node = tableInfoService.getWfNodeById(process.getNodeUid());
							 objs[3] = node.getWfn_name();
						}
					}
					objs[4] = doFile.getEntrustName();
					try {
						objs[5] = sdf.format(doFile.getApplyTime()!=null?sdf.parse(doFile.getApplyTime()):new Date());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Integer result = doFile.getDoFile_result();
					String status = "";
					if (result.equals(0)) {
						status = "未办";
					}else if(result.equals(1)){
						status = "已办";
					}else if(result.equals(2)){
						status = "办结";
					}
					objs[6] = status;
					
					String b = tableInfoService.findLwdw(doFile.getInstanceId());
					objs[7] = b;
					
					Object[] viewInfo = tableInfoService.getViewInfoByInstanceId(doFile.getInstanceId());
					if(viewInfo!=null){
						objs[8] = viewInfo[0]!=null?viewInfo[0].toString():"";
						objs[9] = viewInfo[1]!=null?viewInfo[1].toString():"";
						objs[10] = viewInfo[2]!=null?viewInfo[2].toString():"";
						objs[11] = viewInfo[3]!=null?viewInfo[3].toString():"";
					}
					
					if(dept!=null){
						objs[12] = dept.getDepartmentName(); 
					}
					
					dataList.add(objs);
				}else{
					Object[] objs = new Object[7];
					objs[0] = pageIndex + j;
					j++;
					objs[1] = doFile.getDoFile_title();
					//获取文号
					String a = tableInfoService.findWh(doFile.getInstanceId());
					objs[2] = a;
					if(doFile.getProcessId()!=null&&!"".equals(doFile.getProcessId())){
						WfProcess process = tableInfoService.getProcessById(doFile.getProcessId());
						if(process.getNodeUid()!=null&&!"".equals(process.getNodeUid())){
							 WfNode node = tableInfoService.getWfNodeById(process.getNodeUid());
							 objs[3] = node.getWfn_name();
						}
					}
					objs[4] = doFile.getEntrustName();
					try {
						objs[5] = sdf.format(doFile.getApplyTime()!=null?sdf.parse(doFile.getApplyTime()):new Date());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Integer result = doFile.getDoFile_result();
					String status = "";
					if (result.equals(0)) {
						status = "未办";
					}else if(result.equals(1)){
						status = "已办";
					}else if(result.equals(2)){
						status = "办结";
					}
					
					objs[6] = status;
					dataList.add(objs);
				}
			}
		}
		ExportExcel ex = null ;
		if(addField){
			ex = new ExportExcel(year+"年度公文列表", specialRows, dataList);
		}else if(StringUtils.isNotBlank(isFgw) && "1".equals(isFgw) && StringUtils.isNotBlank(fgwExpItemName)
				&& itemName!=null && fgwExpItemName.equals(itemName)){
			ex = new ExportExcel(year+"年度公文列表", fgwRows, dataList);
		}else{
			ex = new ExportExcel(year+"年度公文列表", rowsName, dataList);
		}
		try {
			ex.saveExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 描述：通用下载方法
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:54:58
	 * @throws IOException 
	 */
	public void downloadForExcel() throws IOException{
		String fileNameWithPath = getRequest().getParameter("fileNameWithPath");
		
		try {
			File file = new File(fileNameWithPath);
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setContentLength((int) l);
			String year = Calendar.getInstance().get(Calendar.YEAR)+"";
			String fileName = year+"年度公文列表.xls";
			fileName = URLEncoder.encode(fileName, "UTF-8");
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			LOGGER.error("下载失败,文件路径为："+fileNameWithPath, e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getCount(0,50));
	}
	
	public static int getCount(int bigNum,int size){
		int i = bigNum/size;
		if(i*size<bigNum){
			return i+1;
		}else{
			return i;
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午10:11:34
	 */
	public String getAccessLogList(){
		String userName = getRequest().getParameter("userName");
		String mehtodName = getRequest().getParameter("methodName");
		String pagesize = getRequest().getParameter("pageSize");
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("methodName", StringUtils.isNotBlank(mehtodName) ? mehtodName : "");
		map.put("userName", StringUtils.isNotBlank(userName) ? userName : "");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Integer count = tableInfoExtendService.countAccessLog(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<AccessLog> list = tableInfoExtendService.getAccLog(map, Paging.pageIndex, Paging.pageSize);
		for (AccessLog accessLog : list) {
			if(StringUtils.isNotBlank(accessLog.getUserid())){
				Employee emp = tableInfoService.findEmpByUserId(accessLog.getUserid());
				accessLog.setUserName(emp.getEmployeeName());
			}
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userName", userName);
		getRequest().setAttribute("mehtodName", mehtodName);
		return "accesslogList";
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-4-8 下午2:28:56
	 */
	public void getServerTime(){
		Calendar calendar = Calendar.getInstance();
		JSONObject obj = new JSONObject();
		obj.put("year", calendar.get(Calendar.YEAR));
		obj.put("month", calendar.get(Calendar.MONTH)+1);
		obj.put("date", calendar.get(Calendar.DAY_OF_MONTH));
		obj.put("hours", calendar.get(Calendar.HOUR_OF_DAY));
		obj.put("minutes", calendar.get(Calendar.MINUTE));
		obj.put("seconds", calendar.get(Calendar.SECOND));
		toPage(obj.toString());
	}
	
	public String getPortalFileList(){
		String userId = getRequest().getParameter("userId");
		String status = getRequest().getParameter("status");
		if(StringUtils.isNotBlank(status)){
			if(StringUtils.isBlank(userId)){
				Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				userId = emp.getEmployeeGuid();
			}
			String serverurl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
			getRequest().setAttribute("url", serverurl+"/eworkflow_searchByStatus.do?status="+status+"&userId="+userId);
		}else{
			if(StringUtils.isNotBlank(status)){
				LOGGER.error("查询状态不能为空");
			}
		}
		return "portalFileList";
	}
	
	/** 
	 * updateIsOpen:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 *  
	 * @since JDK 1.6 
	 */
	public void updateIsOpen(){
		JSONObject obj = new JSONObject();
		String processId = getRequest().getParameter("processId");
		String isOpen = getRequest().getParameter("isOpen");
		WfProcess wfp = pendingService.getProcessByID(processId);
		if(null != wfp){
			wfp.setIsOpen(StringUtils.isNotBlank(isOpen)?Integer.parseInt(isOpen):0);
			tableInfoService.update(wfp);
			obj.put("result", "success");
		}else{
			obj.put("result", "fail");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2018-7-12 下午2:31:09
	 */
	public void dealAutoData(){
		String nodeId = getRequest().getParameter("nodeId");
		String fromNodeId = getRequest().getParameter("fromNodeId");
		tableInfoExtendService.sendAutoData(nodeId, fromNodeId);
		toPage("恭喜你，历史数据处理成功。");
	}
	
	public String getDownloadLogList(){
		String pagesize = getRequest().getParameter("pageSize");
		String beginTime = getRequest().getParameter("beginTime");
		String endTime = getRequest().getParameter("endTime");
		String name = getRequest().getParameter("name");
		String title = getRequest().getParameter("title");
		Map<String, String> map = new HashMap<String, String>(4);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("name", name);
		map.put("title", title);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Integer count = tableInfoExtendService.countFileDownloadLog(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<FileDownloadLog> list = tableInfoExtendService.getFileDownloadLog(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("title", title);
		return "downloadlogList";
	}
	
	
	//-------------------------------
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
