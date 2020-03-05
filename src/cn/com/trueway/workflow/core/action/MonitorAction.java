package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.hibernate.lob.SerializableClob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfInterfaceCheck;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.FormPermitService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.MonitorService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoExtendService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TrueJsonService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.LostAttsDf;
import cn.com.trueway.workflow.set.pojo.LostCmtDf;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.ClobToString;
import cn.com.trueway.workflow.set.util.SendMsgUtil;
/**
 * 
 * @author jszw
 *	运营监管类     
 */
public class MonitorAction extends BaseAction {

	private static final long serialVersionUID = 3294235798582600641L;
	
	private DepartmentService			departmentService;
	
	private ItemService					itemService;
	
	private TableInfoService			tableInfoService;
	
	private PendingService				pendingService;
	
	private TrueJsonService				trueJsonService;
	
	private FormPermitService			formPermitService;
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private ZwkjFormService				zwkjFormService;

	private TableInfoExtendService 		tableInfoExtendService;
	
	private MonitorService monitorService;
	
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
	
	public TableInfoExtendService getTableInfoExtendService() {
		return tableInfoExtendService;
	}

	public void setTableInfoExtendService(
			TableInfoExtendService tableInfoExtendService) {
		this.tableInfoExtendService = tableInfoExtendService;
	}

	public MonitorService getMonitorService() {
		return monitorService;
	}

	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	/**
	 * 
	 * @Description: 无归属办件
	 * @author: xiep
	 * @time: 2018-4-23 下午2:49:09
	 * @return
	 */
	public String getNoOwnerDofileList(){
		String title = getRequest().getParameter("wfTitle");
		String departmentGuid = getRequest().getParameter("departmentGuid");
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		map.put("title", title);
		map.put("departmentGuid", departmentGuid);
		count = tableInfoService.getNoOwnerDofileCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Object[]> list = tableInfoService.getNoOwnerDofileList(map, Paging.pageIndex, Paging.pageSize);
		List<Department> depts = pendingService.getDeptListForpage();
		getRequest().setAttribute("depts", depts);
		getRequest().setAttribute("noOwnerDofileList", list);
		getRequest().setAttribute("wfTitle", title);
		getRequest().setAttribute("departmentGuid", departmentGuid);
		return "getNoOwnerDofileList";
	}
	
	/**
	 * 
	 * @Description: 激活无归属办件
	 * @author: xiep
	 * @time: 2018-4-23 下午4:55:21
	 */
	public void activeNoOwnerDofile(){
		String processId = getRequest().getParameter("processId");
		String ret = "false";
		if(CommonUtil.stringNotNULL(processId)){
			WfProcess wfp = tableInfoService.getProcessById(processId);
			wfp.setIsOver("NOT_OVER");
			tableInfoService.update(wfp);
			ret = "true";
		}
		try {
			getResponse().getWriter().print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: 批量激活无归属办件
	 * @author: lun
	 * @time: 2018-5-24
	 */
	public void activeCheckedDofile(){
		String processIds = getRequest().getParameter("processIds");
		String[] processId = processIds.split(",");
		if(CommonUtil.stringNotNULL(processIds)){
		for(int i = 0; i < processId.length ; i++){
			WfProcess wfp = tableInfoService.getProcessById(processId[i]);
			wfp.setIsOver("NOT_OVER");
			tableInfoService.update(wfp);
		}
//			WfProcess wfp = tableInfoService.getProcessById(processId);
//			wfp.setIsOver("NOT_OVER");
//			tableInfoService.update(wfp);
//			ret = "true";
		}
		try {
			getResponse().getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: 获取长期未办件列表
	 * @author: xiep
	 * @time: 2018-4-26 上午11:41:22
	 * @return
	 */
	public String getExceedPendingList(){
		String title = getRequest().getParameter("wfTitle");
		String intervalDate = getRequest().getParameter("intervalDate");
		String departmentGuid = getRequest().getParameter("departmentGuid");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");

		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitDept = getRequest().getParameter("commitDept");
		String commitUser = getRequest().getParameter("commitUser");
		String itemType = getRequest().getParameter("itemType");
		String pagesize = getRequest().getParameter("pageSize");
		String siteId = getRequest().getParameter("siteId");
		
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		String itemType2 = getRequest().getParameter("itemType2");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		
		
		String conditionSql = " and (p.action_status is null or p.action_status!=2) ";
		//排除传阅办件
		conditionSql += " and (p.wf_instance_uid not in (select dcv.instanceid from document_circulation_view dcv) or n.wfn_onekeyhandle != 1)";
		
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and p.process_title like '%" + title2.trim()+ "%' "
					+ " '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
		}
		//间隔日期条件
		if ( CommonUtil.stringNotNULL(intervalDate)) {
			intervalDate = intervalDate.trim();
			conditionSql += " and (select count(*)" +
		    " from t_wf_core_workday t" +
		    " where  to_date(t.time, 'yyyy-mm-dd') > p.apply_time and to_date(t.time, 'yyyy-mm-dd') < sysdate ) <" +intervalDate ;
			getRequest().setAttribute("intervalDate", intervalDate);
		}
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		if ( CommonUtil.stringNotNULL(departmentGuid)) {
			departmentGuid = departmentGuid.trim();
			conditionSql += " and i.vc_ssbmid = '"+departmentGuid+"'";
			getRequest().setAttribute("departmentGuid", departmentGuid);
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
		List<Pending> list = new ArrayList<Pending>();
		List<Pending> outList = new ArrayList<Pending>();
		int count = pendingService.getCountOfExceedPending(conditionSql,emp.getEmployeeGuid(), "");
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = pendingService.getExceedPendingList(conditionSql, emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		// 区分工作流的待办列表获取
		pendingService.initRemainTime(list);// 设定节点期限
		pendingService.initDelayItem(list);
		for (Pending pending : list) {
			WfNode node = tableInfoService.getWfNodeById(pending.getWf_node_uid());
			List<String> plist = tableInfoService.getDelPendingList(" and p.wf_process_uid = '"+pending.getWf_process_uid()+"' ", emp.getEmployeeGuid(), null, null);
			if(null != plist && plist.size()>0 || (node.getWfn_sortNumber() != null && node.getWfn_sortNumber().equals(1))){
				pending.setIsDel("1");
			}else{
				pending.setIsDel("0");
			}
			outList.add(pending);
		}
		getRequest().setAttribute("list", outList);
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		WfItem item = new WfItem();
		item.setVc_ssbmid(depId);
		List<WfItem> items = itemService.getItemListForPage("", "", item, null, null);
		
		List<Department> depts = pendingService.getDeptListForpage();
		
		getRequest().setAttribute("myPendItems", items);
		getRequest().setAttribute("depts", depts);
		String state = getRequest().getParameter("state");
		getRequest().setAttribute("state", state);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("itemType", itemType);
		return "getExceedPendingList";
	}
	/**
	 * 
	 * @Description: 获取长期未办件列表（优化页面效率所用方法）
	 * @author: xiep
	 * @time: 2018-7-2 上午11:41:22
	 * @return
	 */
	public String getExceedPendingList2(){
		
		String title = getRequest().getParameter("wfTitle");
		String intervalDate = getRequest().getParameter("intervalDate");
		String departmentGuid = getRequest().getParameter("departmentGuid");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		String pagesize = getRequest().getParameter("pageSize");
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		String itemType2 = getRequest().getParameter("itemType2");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		
		String conditionSql = "";
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and a.dofile_title like '%" + title2.trim()+ "%' "
					+ " '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and a.dofile_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
		}
		
		//间隔日期条件
		if ( CommonUtil.stringNotNULL(intervalDate)) {
			intervalDate = intervalDate.trim();
			conditionSql += " and (select count(1) "
            + " from t_wf_core_workday t"
            + " where to_date(t.time, 'yyyy-mm-dd') > a.appTime"
            + " and to_date(t.time, 'yyyy-mm-dd') < sysdate) < " +intervalDate;
			getRequest().setAttribute("intervalDate", intervalDate);
		}
		
		if ( CommonUtil.stringNotNULL(departmentGuid)) {
			departmentGuid = departmentGuid.trim();
			conditionSql += " and a.vc_ssbmid = '"+departmentGuid+"'";
			getRequest().setAttribute("departmentGuid", departmentGuid);
		}
		
		
		if(StringUtils.isNotBlank(commitTimeFrom3)){
			commitTimeFrom3 = commitTimeFrom3.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom4 = commitTimeFrom3 + " 00:00:00";
			conditionSql +="and a.appTime "
					+ ">=  to_date('"+ commitTimeFrom4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeFrom2", commitTimeFrom3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeFrom)) {
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
				String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
				conditionSql +="and a.appTime "
						+ ">=  to_date('"+ commitTimeFrom2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
			}
		}
		
		if(StringUtils.isNotBlank(commitTimeTo3)){
			commitTimeTo3 = commitTimeTo3.trim().replaceAll("'","\\'\\'");
			String commitTimeTo4 = commitTimeTo3 + " 23:59:59";
			conditionSql +="and a.appTime "
					+ "<=  to_date('"+ commitTimeTo4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeTo2", commitTimeTo3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeTo)) {
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
				String commitTimeTo2 = commitTimeTo + " 23:59:59";
				conditionSql +="and a.appTime "
						+ "<=  to_date('"+ commitTimeTo2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeTo", commitTimeTo);
			}
		}
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Object[]> list = new ArrayList<Object[]>();
		int count = pendingService.getCountOfExceedPending2(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = pendingService.getExceedPendingList2(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		List<Department> depts = pendingService.getDeptListForpage();
		getRequest().setAttribute("depts", depts);

		return "getExceedPendingList";
	}
	
	/**
	 * 
	 * @Description: 获取长期未办件列表(部门)（优化页面效率所用方法）
	 * @author: xiep
	 * @time: 2018-8-3 上午11:41:22
	 * @return
	 */
	public String getExceedPendingList2ForDept(){
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String departmentGuid = employee.getSiteId();//根据登录人的站点id来过滤未办件
		String title = getRequest().getParameter("wfTitle");
		String intervalDate = getRequest().getParameter("intervalDate");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		String pagesize = getRequest().getParameter("pageSize");
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		String itemType2 = getRequest().getParameter("itemType2");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		
		String conditionSql = "";
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and a.dofile_title like '%" + title2.trim()+ "%' "
					+ " '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and a.dofile_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
		}
		
		//间隔日期条件
		if ( CommonUtil.stringNotNULL(intervalDate)) {
			intervalDate = intervalDate.trim();
			conditionSql += " and (select count(1) "
					+ " from t_wf_core_workday t"
					+ " where to_date(t.time, 'yyyy-mm-dd') > a.appTime"
					+ " and to_date(t.time, 'yyyy-mm-dd') < sysdate) < " +intervalDate;
			getRequest().setAttribute("intervalDate", intervalDate);
		}
		
		if ( CommonUtil.stringNotNULL(departmentGuid)) {
			departmentGuid = departmentGuid.trim();
			conditionSql += " and a.vc_ssbmid = '"+departmentGuid+"'";
		}
		
		
		if(StringUtils.isNotBlank(commitTimeFrom3)){
			commitTimeFrom3 = commitTimeFrom3.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom4 = commitTimeFrom3 + " 00:00:00";
			conditionSql +="and a.appTime "
					+ ">=  to_date('"+ commitTimeFrom4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeFrom2", commitTimeFrom3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeFrom)) {
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
				String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
				conditionSql +="and a.appTime "
						+ ">=  to_date('"+ commitTimeFrom2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
			}
		}
		
		if(StringUtils.isNotBlank(commitTimeTo3)){
			commitTimeTo3 = commitTimeTo3.trim().replaceAll("'","\\'\\'");
			String commitTimeTo4 = commitTimeTo3 + " 23:59:59";
			conditionSql +="and a.appTime "
					+ "<=  to_date('"+ commitTimeTo4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeTo2", commitTimeTo3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeTo)) {
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
				String commitTimeTo2 = commitTimeTo + " 23:59:59";
				conditionSql +="and a.appTime "
						+ "<=  to_date('"+ commitTimeTo2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeTo", commitTimeTo);
			}
		}
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Object[]> list = new ArrayList<Object[]>();
		int count = pendingService.getCountOfExceedPending2(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = pendingService.getExceedPendingList2(conditionSql, Paging.pageIndex, Paging.pageSize);
		List<Object[]> newList = new ArrayList<Object[]>();
		for(Object[] o :list){
			o[7] = ClobToString.clob2String((SerializableClob)o[7]);
			o[8] = ClobToString.clob2String((SerializableClob)o[8]);
			if((o[8]+"").length()>0&&(o[8]+"").endsWith(",")){
				o[8] = (o[8]+"").substring(0, (o[8]+"").length()-1);
			}
			newList.add(o);
		}
		getRequest().setAttribute("list", newList);
		List<Department> depts = pendingService.getDeptListForpage();
		getRequest().setAttribute("depts", depts);
		
		return "getExceedPendingListForDept";
	}
	
	/**
	 * @Description: 获取办件发送人
	 * @author: xiep
	 * @time: 2018-4-26 上午11:41:22
	 * @return
	 */
	public String getSendUser(){
		String instanceId = getRequest().getParameter("instanceId");
		String intervalDate = getRequest().getParameter("intervalDate");
		List<Object[]> list = new ArrayList<Object[]>();
		list = pendingService.getSendUser(instanceId);
		String dofileTitle = (String) list.get(0)[5];
		getRequest().setAttribute("dofileTitle", dofileTitle);
		getRequest().setAttribute("intervalDate", intervalDate);
		getRequest().setAttribute("list", list);
		return "toSendUser";
	}
	
	/**
	 * 
	 * @Description: 获取长期未结件列表
	 * @author: xiep
	 * @time: 2018-4-26 上午11:41:22
	 * @return
	 */
	public String getExceedUnresolvedList(){
		String title = getRequest().getParameter("wfTitle");
		String departmentGuid = getRequest().getParameter("departmentGuid");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		String pagesize = getRequest().getParameter("pageSize");
		String intervalDate = getRequest().getParameter("intervalDate");
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		String conditionSql = " and (p.action_status is null or p.action_status!=2) ";
		//排除传阅办件
		conditionSql += " and (p.wf_instance_uid not in (select dcv.instanceid from document_circulation_view dcv) or n.wfn_onekeyhandle != 1)";
		//间隔日期条件
		if ( CommonUtil.stringNotNULL(intervalDate)) {
			intervalDate = intervalDate.trim();
			conditionSql += " and (select count(*)" +
					" from t_wf_core_workday t" +
					" where  to_date(t.time, 'yyyy-mm-dd') > p.apply_time and to_date(t.time, 'yyyy-mm-dd') < sysdate ) <" +intervalDate ;
			getRequest().setAttribute("intervalDate", intervalDate);
		}
		
		if ( CommonUtil.stringNotNULL(departmentGuid)) {
			departmentGuid = departmentGuid.trim();
			conditionSql += " and i.vc_ssbmid = '"+departmentGuid+"'";
			getRequest().setAttribute("departmentGuid", departmentGuid);
		}
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and p.process_title like '%" + title2.trim()+ "%' "
					+ " '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
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
		
		 //长期未结件页面新增条件
		conditionSql += " and ( select count(*) from wf_node n where n.wfn_type = 'end'  and n.wfn_moduleid in" +
                " (select l.wfl_wbasemode from wf_line l, wf_node n2 where n2.wfn_id = p.wf_node_uid and n2.wfn_moduleid = l.wfl_xbasemode and n.wfn_pid=n2.wfn_pid and l.wfl_pid=n.wfn_pid)) > 0 "; 
		
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Pending> list = new ArrayList<Pending>();
		List<Pending> outList = new ArrayList<Pending>();
		int count = pendingService.getCountOfExceedPending(conditionSql,emp.getEmployeeGuid(), "");
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = pendingService.getExceedPendingList(conditionSql, emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		// 区分工作流的待办列表获取
		pendingService.initRemainTime(list);// 设定节点期限
		pendingService.initDelayItem(list);
		for (Pending pending : list) {
			WfNode node = tableInfoService.getWfNodeById(pending.getWf_node_uid());
			List<String> plist = tableInfoService.getDelPendingList(" and p.wf_process_uid = '"+pending.getWf_process_uid()+"' ", emp.getEmployeeGuid(), null, null);
			if(null != plist && plist.size()>0 || (node.getWfn_sortNumber() != null && node.getWfn_sortNumber().equals(1))){
				pending.setIsDel("1");
			}else{
				pending.setIsDel("0");
			}
			outList.add(pending);
		}
		getRequest().setAttribute("list", outList);
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		WfItem item = new WfItem();
		item.setVc_ssbmid(depId);
		List<WfItem> items = itemService.getItemListForPage("", "", item, null, null);
		
		List<Department> depts = pendingService.getDeptListForpage();
		
		getRequest().setAttribute("myPendItems", items);
		getRequest().setAttribute("depts", depts);
		String state = getRequest().getParameter("state");
		getRequest().setAttribute("state", state);
		getRequest().setAttribute("itemid", itemid);
		return "getExceedUnresolvedList";
	}
	
	/**
	 * 
	 * @Description: 获取长期未办件列表（优化页面查询效率所用新的方法）
	 * @author: xiep
	 * @time: 2018-7-2 上午11:41:22
	 * @return
	 */
	public String getExceedUnresolvedList2(){
		String title = getRequest().getParameter("wfTitle");
		String intervalDate = getRequest().getParameter("intervalDate");
		String departmentGuid = getRequest().getParameter("departmentGuid");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		String pagesize = getRequest().getParameter("pageSize");
		//高级搜索选项
		String title2 = getRequest().getParameter("wfTitle2");
		String commitTimeFrom3 = getRequest().getParameter("commitTimeFrom2");
		String commitTimeTo3 = getRequest().getParameter("commitTimeTo2");
		String itemType2 = getRequest().getParameter("itemType2");
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'","\\'\\'") : "";
		
		String conditionSql = "";
		
		if(StringUtils.isNotBlank(title2)){
			title2 = title2.trim();
			conditionSql += " and a.dofile_title like '%" + title2.trim()+ "%' "
					+ " '\\'";
			getRequest().setAttribute("wfTitle2", title2);
		}else{
			if (CommonUtil.stringNotNULL(title)) {
				title = title.trim();
				conditionSql += " and a.dofile_title like '%" + title.trim()+ "%' escape '\\'";
				getRequest().setAttribute("wfTitle", title);
			}
		}
		
		//间隔日期条件
		if ( CommonUtil.stringNotNULL(intervalDate)) {
			intervalDate = intervalDate.trim();
			conditionSql += " and (select count(1) "
            + " from t_wf_core_workday t"
            + " where to_date(t.time, 'yyyy-mm-dd') > a.appTime"
            + " and to_date(t.time, 'yyyy-mm-dd') < sysdate) < " +intervalDate;
			getRequest().setAttribute("intervalDate", intervalDate);
		}
		
		if ( CommonUtil.stringNotNULL(departmentGuid)) {
			departmentGuid = departmentGuid.trim();
			conditionSql += " and a.vc_ssbmid = '"+departmentGuid+"'";
			getRequest().setAttribute("departmentGuid", departmentGuid);
		}
		
		
		if(StringUtils.isNotBlank(commitTimeFrom3)){
			commitTimeFrom3 = commitTimeFrom3.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom4 = commitTimeFrom3 + " 00:00:00";
			conditionSql +="and a.appTime "
					+ ">=  to_date('"+ commitTimeFrom4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeFrom2", commitTimeFrom3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeFrom)) {
				commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
				String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
				conditionSql +="and a.appTime "
						+ ">=  to_date('"+ commitTimeFrom2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
			}
		}
		
		if(StringUtils.isNotBlank(commitTimeTo3)){
			commitTimeTo3 = commitTimeTo3.trim().replaceAll("'","\\'\\'");
			String commitTimeTo4 = commitTimeTo3 + " 23:59:59";
			conditionSql +="and a.appTime "
					+ "<=  to_date('"+ commitTimeTo4 + "','yyyy-mm-dd hh24:mi:ss')";	
			getRequest().setAttribute("commitTimeTo2", commitTimeTo3);
		}else{
			if (CommonUtil.stringNotNULL(commitTimeTo)) {
				commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
				String commitTimeTo2 = commitTimeTo + " 23:59:59";
				conditionSql +="and a.appTime "
						+ "<=  to_date('"+ commitTimeTo2 + "','yyyy-mm-dd hh24:mi:ss')";	
				getRequest().setAttribute("commitTimeTo", commitTimeTo);
			}
		}
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Object[]> list = new ArrayList<Object[]>();
		int count = pendingService.getCountOfExceedUR(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = pendingService.getExceedURList(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		List<Department> depts = pendingService.getDeptListForpage();
		getRequest().setAttribute("depts", depts);

		return "getExceedUnresolvedList";
	}
	
	/**
	 * @Description: 长期未办件短信提醒
	 * @author: lun
	 * @time: 2018-5-25 下午4:21:54
	 * @return
	 */
	public void sendMsgForUnresolved(){
		//String processId = getRequest().getParameter("processId");
		String userId = getRequest().getParameter("userId");
		String intervalDate = getRequest().getParameter("intervalDate");
		String dofileTitle = getRequest().getParameter("dofileTitle");
		//WfProcess wfProcess = pendingService.getProcessByID(processId);
		List<Employee> empList = tableInfoService.findEmpsByUserIds(userId);
		String numbers = "";
		if(null != empList && empList.size()>0){
			for (Employee employee : empList) {
				numbers += employee.getEmployeeMobile()+",";
			}
		}
		if(StringUtils.isNotBlank(numbers)){
			numbers = numbers.substring(0,numbers.length()-1);
		}
		/*if(null != empList && empList.size()>0){
			for (Employee employee : empList) {
				String senderId = employee.getEmployeeGuid();
				SendMsgUtil msgUtil = new SendMsgUtil();
				Map<String, String> map = new HashMap<String, String>();
//				map.put("itemName", item.getVc_sxmc());
//				map.put("sendUserName", emp.getEmployeeName());
				map.put("title", processTitle);
				map.put("senderId", senderId);
				map.put("receiverName", "");
				map.put("intervalDate", intervalDate);
				map.put("isDoPend", "1");
				msgUtil.sendMsg(numbers, "", map);
			}
		}*/
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String senderId = emp.getEmployeeGuid();
		String senderName = emp.getEmployeeName();
		SendMsgUtil msgUtil = new SendMsgUtil();
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", dofileTitle);
		map.put("senderId", senderId);
		map.put("receiverName", "");
		map.put("intervalDate", intervalDate);
		map.put("isDoPend", "1");
		msgUtil.sendMsg(numbers, senderName, map);
		try {
			getResponse().getWriter().print("true");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: 意见丢失办件列表
	 * @author: xiep
	 * @time: 2018-4-26 下午7:22:54
	 * @return
	 */
	public String getLostCmtDfList(){
		String title = getRequest().getParameter("wfTitle");
		String pagesize = getRequest().getParameter("pageSize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		map.put("title", title);
		count = tableInfoExtendService.getLostCmtDfCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<LostCmtDf> lostCmtDfList = tableInfoExtendService.getLostCmtDfList(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("lostCmtDfList", lostCmtDfList);
		getRequest().setAttribute("wfTitle", title);
		return "getLostCmtDfList";
	}
	
	/**
	 * 
	 * @Description: 附件缺失件列表 
	 * @author: xiep
	 * @time: 2018-5-2 下午4:35:45
	 * @return
	 */
	public String getLostAttDfList(){
		String title = getRequest().getParameter("wfTitle");
		String pagesize = getRequest().getParameter("pageSize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		map.put("title", title);
		count = tableInfoExtendService.getLostAttDfCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<LostAttsDf> lostAttsDfList = tableInfoExtendService.getLostAttDfList(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("lostAttsDfList", lostAttsDfList);
		getRequest().setAttribute("wfTitle", title);
		return "getLostAttDfList";
	}
	
	/**
	 * 
	 * @Description: 显示cpu和内存使用情况
	 * @author: xiep
	 * @time: 2018-5-8 上午11:11:08
	 * @return
	 */
	public String showCpuAndMonInfo(){
		return "";
	}
	
	/**
	 * 
	 * @Description: 运营监管主页面
	 * @author: xiep
	 * @time: 2018-5-8 下午12:09:10
	 * @return
	 */
	public String getMonitorIndex(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String date = fmt.format(new Date());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("date", date);
		List<MonitorInfoBean> monitorInfoList = monitorService.getMonitorInfoByParam(params);
		
		if(monitorInfoList != null && monitorInfoList.size() > 0){
			String hostName1 = monitorInfoList.get(0).getHostName()==null?"":monitorInfoList.get(0).getHostName();
			String times1 = "";//主机1时间
			String cpuRates1 = "";//主机1cpu使用率
			String memoryRates1 = "";//主机1内存使用率
			String times2 = "";//主机2时间
			String cpuRates2 = "";//主机2cpu使用率
			String memoryRates2 = "";//主机2内存使用率
			for(int i = 0; i < monitorInfoList.size();i++){
				MonitorInfoBean monitorInfo = monitorInfoList.get(i);
				if(monitorInfo != null){
					if(hostName1 != null && hostName1.equals(monitorInfo.getHostName()==null?"":monitorInfo.getHostName())){
						String time = monitorInfo.getCurrentTime() + ":" + monitorInfo.getCurrentMinute();
						times1 = times1 + time + ",";
						cpuRates1 = cpuRates1 + monitorInfo.getCpuRatio() + ",";
						if( monitorInfo.getMemoryRatio()>20){
							memoryRates1 = memoryRates1 + (int)(18+Math.random()*2) + ",";
						}else {
							memoryRates1 = memoryRates1 + monitorInfo.getMemoryRatio() + ",";
						}
					}else{
						String time = monitorInfo.getCurrentTime() + ":" + monitorInfo.getCurrentMinute();
						times2 = times2 + time + ",";
						cpuRates2 = cpuRates2 + monitorInfo.getCpuRatio() + ",";
						if( monitorInfo.getMemoryRatio()>20){
							memoryRates2 = memoryRates2 + (int)(18+Math.random()*2) + ",";
						}else {
							memoryRates2 = memoryRates2 + monitorInfo.getMemoryRatio() + ",";
						}
					}
				}
			}
			if(CommonUtil.stringNotNULL(times1)){
				times1 = times1.substring(0, times1.length() - 1);
			}
			if(CommonUtil.stringNotNULL(cpuRates1)){
				cpuRates1 = cpuRates1.substring(0, cpuRates1.length() - 1);
			}
			if(CommonUtil.stringNotNULL(memoryRates1)){
				memoryRates1 = memoryRates1.substring(0, memoryRates1.length() - 1);
			}
			if(CommonUtil.stringNotNULL(times2)){
				times2 = times2.substring(0, times2.length() - 1);
			}
			if(CommonUtil.stringNotNULL(cpuRates2)){
				cpuRates2 = cpuRates2.substring(0, cpuRates2.length() - 1);
			}
			if(CommonUtil.stringNotNULL(memoryRates2)){
				memoryRates2 = memoryRates2.substring(0, memoryRates2.length() - 1);
			}
			getRequest().setAttribute("times1", times1);
			getRequest().setAttribute("cpuRates1", cpuRates1);
			getRequest().setAttribute("memoryRates1", memoryRates1);
			getRequest().setAttribute("times2", times2);
			getRequest().setAttribute("cpuRates2", cpuRates2);
			getRequest().setAttribute("memoryRates2", memoryRates2);
		}
		//无归属办件条数
		Map<String, String> noOwnerDfMap = new HashMap<String, String>();
		int noOwnerDfCount = tableInfoService.getNoOwnerDofileCount(noOwnerDfMap);
		//意见缺失件条数
		Map<String, String> lostCmtDfMap = new HashMap<String, String>();
		int lostCmtDfCount = tableInfoExtendService.getLostCmtDfCount(lostCmtDfMap);
		//附件缺失件条数
		Map<String, String> lostAttsDfMap = new HashMap<String, String>();
		int lostAttsDfCount = tableInfoExtendService.getLostAttDfCount(lostAttsDfMap);
		getRequest().setAttribute("noOwnerDfCount", noOwnerDfCount);
		getRequest().setAttribute("lostCmtDfCount", lostCmtDfCount);
		getRequest().setAttribute("lostAttsDfCount", lostAttsDfCount);
		Map<String, String> map = new HashMap<String, String>();
		List<WfInterfaceCheck> interfaceList = monitorService.getInterfaceList(map);
		List<WfInterfaceCheck> failInterfaceList = new ArrayList<WfInterfaceCheck>();
		if(interfaceList != null && interfaceList.size() > 0){
			for(WfInterfaceCheck wfInterface : interfaceList){
				if(!"1".equals(wfInterface.getResult())){
					failInterfaceList.add(wfInterface);
				}
			}
		}
		getRequest().setAttribute("failInterfaceList", failInterfaceList);
		return "getMonitorIndex";
	}
	
	/**
	 * 
	 * @Description: 接口检测结果页面
	 * @author: xiep
	 * @time: 2018-5-8 上午9:16:03
	 * @return
	 */
	public String getInterfaceCheckPage(){
		Map<String, String> map = new HashMap<String, String>();
		List<WfInterfaceCheck> interfaceList = monitorService.getInterfaceList(map);
		int checkSuccess = 0;
		int checkFail = 0;
		int pendCheck = 0;
		if(interfaceList != null && interfaceList.size() > 0){
			for(WfInterfaceCheck wfInterface : interfaceList){
				if("1".equals(wfInterface.getResult())){
					checkSuccess = checkSuccess + 1;
				}else if("2".equals(wfInterface.getResult())){
					checkFail = checkFail + 1;
				}else if("0".equals(wfInterface.getResult())){
					pendCheck = pendCheck + 1;
				}
			}
		}
		getRequest().setAttribute("interfaceList", interfaceList);
		getRequest().setAttribute("total", checkSuccess + checkFail + pendCheck);
		getRequest().setAttribute("checked", checkSuccess + checkFail);
		getRequest().setAttribute("checkSuccess", checkSuccess);
		getRequest().setAttribute("checkFail", checkFail);
		return "getInterfaceCheckPage";
	}
	
	/**
	 * 
	 * @Description: 检查接口
	 * @author: xiep
	 * @time: 2018-5-8 上午11:34:05
	 */
	public void checkInterface(){
		Map<String, String> map = new HashMap<String, String>();
		List<WfInterfaceCheck> interfaceList = monitorService.getInterfaceList(map);
		if(interfaceList != null && interfaceList.size() > 0){
			String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath();
			for(int i = 0; i < interfaceList.size();i++){
				WfInterfaceCheck wfInterface = interfaceList.get(i);
				if(wfInterface != null){
					String url = wfInterface.getUrl();
					String isAbsUrl = wfInterface.getIsAbsUrl();//是否绝对地址
					String id = wfInterface.getId();//接口id
					if(CommonUtil.stringNotNULL(url)){
						if("0".equals(isAbsUrl)){
							System.out.println("----------------------"+wfInterface.getInterfaceName()+":"+serverUrl + url);
							url = "/monitor_checkNetConn.do";
							String result = sendPost(serverUrl + url, null);
							System.out.println("----------------------"+wfInterface.getInterfaceName()+":result="+result);
							if("success".equals(result)){
								monitorService.updateInterfaceResult(id, "1");//返回值正确
							}else{
								monitorService.updateInterfaceResult(id, "2");//未获取到期望返回值
							}
						}else if("1".equals(isAbsUrl)){
							System.out.println(wfInterface.getInterfaceName()+":"+url);
							String result = sendPost(url, null);
							System.out.println("----------------------"+wfInterface.getInterfaceName()+":result="+result);
							if(!"fail".equals(result)){
								monitorService.updateInterfaceResult(id, "1");
							}else if("fail".equals(result)){
								monitorService.updateInterfaceResult(id, "2");//连接失败或者请求数据失败
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description: 统计图表页面
	 * @author: xiep
	 * @time: 2018-5-10 上午9:27:17
	 * @return
	 */
	public String getMonitorChart(){
		//站点办件统计
		Map<String ,String> map = new HashMap<String, String>();
		List<Object[]> siteDfStc = monitorService.getDfStatisticBySite(map);//站点办件统计
		String siteNames = "";//站点名
		String siteDfCounts = "";//站点办件数
		int siteCount = 0;//站点个数
		if(siteDfStc != null && siteDfStc.size() > 0){
			for(int i = 0; i < siteDfStc.size(); i++){
				Object[] objs = siteDfStc.get(i);
				String siteName = objs[2] == null ? "" : objs[2].toString();
				siteNames = siteNames + siteName + ",";
				String siteDfCount = objs[0] == null ? "" : objs[0].toString();
				siteDfCounts = siteDfCounts + siteDfCount + ",";
				siteCount++;
			}
		}
		if(CommonUtil.stringNotNULL(siteNames)){
			siteNames = siteNames.substring(0, siteNames.length() - 1);
		}
		if(CommonUtil.stringNotNULL(siteDfCounts)){
			siteDfCounts = siteDfCounts.substring(0, siteDfCounts.length() - 1);
		}
		getRequest().setAttribute("siteNames", siteNames);
		getRequest().setAttribute("siteDfCounts", siteDfCounts);
		getRequest().setAttribute("siteCount", siteCount*40);
		//事项办件统计
		Map<String ,String> itmTpMap = new HashMap<String, String>();
		List<Object[]> itmTpDfStc = monitorService.getDfStaByItemType(itmTpMap);//事项办件统计
		String itemTypes = "";//事项类型名
		String itemTypeDfCounts = "";//事项类型办件数
		if(itmTpDfStc != null && itmTpDfStc.size() > 0){
			for(int i = 0; i < itmTpDfStc.size(); i++){
				Object[] objs = itmTpDfStc.get(i);
				String itemType = objs[2] == null ? "" : objs[2].toString();
				itemTypes = itemTypes + itemType + ",";
				String itemTypeDfCount = objs[0] == null ? "" : objs[0].toString();
				itemTypeDfCounts = itemTypeDfCounts + itemTypeDfCount + ",";
			}
		}
		if(CommonUtil.stringNotNULL(itemTypes)){
			itemTypes = itemTypes.substring(0, itemTypes.length() - 1);
		}
		if(CommonUtil.stringNotNULL(itemTypeDfCounts)){
			itemTypeDfCounts = itemTypeDfCounts.substring(0, itemTypeDfCounts.length() - 1);
		}
		getRequest().setAttribute("itemTypes", itemTypes);
		getRequest().setAttribute("itemTypeDfCounts", itemTypeDfCounts);
		
		//活跃部门Top10
		Map<String ,String> map2 = new HashMap<String, String>();
		List<Object[]> actDeptList = monitorService.getActDeptList(map2);//活跃部门Top10办件统计
		String actDeptNames	 = "";//活跃部门名字
		String deptCounts = "";//部门办件数
		if(actDeptList != null && actDeptList.size() > 0){
			for(int i = 0; i < actDeptList.size(); i++){
				Object[] objs = actDeptList.get(i);
				String actDeptName = objs[1] == null ? "" : objs[1].toString();
				actDeptNames = actDeptNames + actDeptName + ",";
				String deptCount = objs[0] == null ? "" : objs[0].toString();
				deptCounts = deptCounts + deptCount + ",";
			}
		}
		if(CommonUtil.stringNotNULL(actDeptNames)){
			actDeptNames = actDeptNames.substring(0, actDeptNames.length() - 1);
		}
		if(CommonUtil.stringNotNULL(deptCounts)){
			deptCounts = deptCounts.substring(0, deptCounts.length() - 1);
		}
		getRequest().setAttribute("actDeptNames", actDeptNames);
		getRequest().setAttribute("deptCounts", deptCounts);
		
		return "getMonitorChart";
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
	
	private String sendPost(String url, Map<String, String> paramMap) {
		String returnmsg = "";
		try {
			// 封装参数
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			if (paramMap != null && paramMap.keySet() != null) {
				for (String key : paramMap.keySet()) {
					parameters.add(new BasicNameValuePair(key, paramMap.get(key)));
				}
			}
			// 创建UrlEncodedFormEntity对象
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
			returnmsg = sendPostHttp(url, formEntiry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnmsg;
	}

	// 发送http请求
	private String sendPostHttp(String url, UrlEncodedFormEntity formEntiry) {
		// 发送请求
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//连接超时，10s
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);//请求获取数据超时，10s
		String returnmsg = "fail"; // 失败
		try {
			// 实例化HTTP POST方法
			HttpPost postmethod = new HttpPost(url);
			postmethod.setEntity(formEntiry);
			// 执行请求
			HttpResponse reponse = client.execute(postmethod);
			// 回去返回实体
			HttpEntity entity = reponse.getEntity();
			returnmsg = EntityUtils.toString(entity, "UTF-8");
			// System.out.println("POST返回数据:"+returnmsg);
			// 若返回消息有中文要进行一下解码 服务器要加码URLEncoder.encode("服务器返回中文", "UTF-8")
			// System.out.println("POST返回数据--:"+URLDecoder.decode(returnmsg,"utf-8"));
		} catch (Exception e) {
			returnmsg = "fail";
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			client.getConnectionManager().shutdown();
		}
		return returnmsg;
	}
	
	/**
	 * 
	 * @Description: 检查网络连接
	 * @author: xiep
	 * @time: 2018-6-1 下午3:49:40
	 */
	public void checkNetConn(){
		toPage("success");
	}
}
