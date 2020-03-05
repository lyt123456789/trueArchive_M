package cn.com.trueway.workflow.business.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.ExcelExport;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.MyUtils;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.business.pojo.HandRoundShip;
import cn.com.trueway.workflow.business.pojo.Library;
import cn.com.trueway.workflow.business.pojo.PressMsg;
import cn.com.trueway.workflow.business.service.BusinessService;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptVo;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;


/**
 * 
 * 描述：关于业务数据的查询操作类
 * 作者：蔡亚军
 * 创建时间：2016-11-29 下午2:36:31
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class BusinessAction extends BaseAction{
	
	private static final long serialVersionUID = -5054837033085713864L;
	
	private BusinessService businessService;
	
	private ItemService itemService;

	private TableInfoService tableInfoService;
	
	private PendingService pendingService;
	
	private DepartmentService departmentService;
	
	private EmployeeService employeeService;


	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
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

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**
	 * 
	 * 描述：发文库
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-11-29 下午2:40:55
	 */
	public String getSendLibrary(){
		String title = getRequest().getParameter("title");
		String fwh = getRequest().getParameter("fwh");
		String dw = getRequest().getParameter("dw");
		String beginTime = getRequest().getParameter("beginTime");
		String endTime = getRequest().getParameter("endTime");
		String itemid = getRequest().getParameter("itemid");
		String contionsql = "";
		if(CommonUtil.stringNotNULL(itemid)){
			contionsql += " and i.id = '"+itemid+"'";
		}
		if(CommonUtil.stringNotNULL(title)){
			contionsql += " and d.dofile_title like '%"+title.trim()+"%'";
		}
		if(CommonUtil.stringNotNULL(fwh)){
			contionsql += " and t.wh like '%"+fwh.trim()+"%'";
		}
		if(CommonUtil.stringNotNULL(dw)){
			contionsql += " and (t.zbbm like '%"+dw.trim()+"%' or t.csbm like '%"+dw+"%')";
		}
		if (CommonUtil.stringNotNULL(beginTime)) {
			beginTime = beginTime.trim().replaceAll("'","\\'\\'");
			contionsql +=" and (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 )" +
					"  >= to_date('"+beginTime + " 00:00:00"+"','yyyy-mm-dd hh24:mi:ss') ";	
		}
		if (CommonUtil.stringNotNULL(endTime)) {
			endTime = endTime.trim().replaceAll("'","\\'\\'");
			contionsql +=" and (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) " +
					"  <= to_date('"+endTime + " 23:59:59"+"','yyyy-mm-dd hh24:mi:ss') ";	
		}
		contionsql += " and ( select count(p.is_end) from t_wf_process p where  p.wf_instance_uid = d.instanceid and p.is_end = 1) >0  ";
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		int count =  businessService.findSendLibraryCount(contionsql, userId);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Library>  list = businessService.findSendLibraryList(contionsql, userId, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("title", title);
		
		getRequest().setAttribute("fwh", fwh);
		getRequest().setAttribute("dw", dw);
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("itemid", itemid);
		
		if(getSession().getAttribute("myPendItems") == null){
			List<WfItem> items = new ArrayList<WfItem>();
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
				items = itemService.getItemListByDeptIds(depids,"");
			getRequest().setAttribute("myPendItems", items);
		}
		return "sendLibrary";
	}
	
	/**
	 * 
	 * 描述：收文库
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-11-29 下午2:41:12
	 */
	public String getReceiveLibrary(){
		String title = getRequest().getParameter("title");
		String wh = getRequest().getParameter("wh");
		String dw = getRequest().getParameter("dw");
		String beginTime = getRequest().getParameter("beginTime");
		String endTime = getRequest().getParameter("endTime");
		String itemid = getRequest().getParameter("itemid");
		String contionsql = "";
		if(CommonUtil.stringNotNULL(itemid)){
			contionsql += " and i.id = '"+itemid+"'";
		}
		if(CommonUtil.stringNotNULL(title)){
			contionsql += " and d.dofile_title like '%"+title.trim()+"%'";
		}
		if(CommonUtil.stringNotNULL(wh)){
			contionsql += " and t.wh like '%"+wh.trim()+"%'";
		}
		if(CommonUtil.stringNotNULL(dw)){
			contionsql += " and (t.lwdw like '%"+dw.trim()+"%')";
		}
		if (CommonUtil.stringNotNULL(beginTime)) {
			beginTime = beginTime.trim().replaceAll("'","\\'\\'");
			contionsql +=" and (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 )" +
					"  >= to_date('"+beginTime + " 00:00:00"+"','yyyy-mm-dd hh24:mi:ss') ";	
		}
		if (CommonUtil.stringNotNULL(endTime)) {
			endTime = endTime.trim().replaceAll("'","\\'\\'");
			contionsql +=" and (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) " +
					"  <= to_date('"+endTime + " 23:59:59"+"','yyyy-mm-dd hh24:mi:ss') ";	
		}
		contionsql += " and ( select count(p.is_end) from t_wf_process p where  p.wf_instance_uid = d.instanceid and p.is_end = 1) >0  ";
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		int count =  businessService.findReceiveLibraryCount(contionsql, userId);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Library>  list = businessService.findReceiveLibraryList(contionsql, userId, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("title", title);
		
		getRequest().setAttribute("wh", wh);
		getRequest().setAttribute("dw", dw);
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("itemid", itemid);
		
		if(getSession().getAttribute("myPendItems") == null){
			List<WfItem> items = new ArrayList<WfItem>();
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
				items = itemService.getItemListByDeptIds(depids,"");
			getRequest().setAttribute("myPendItems", items);
		}
		return "receiveLibrary";
	}
	
	
	/**
	 * 获取所有超期的办件
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-9-23 下午2:42:42
	 */
	public String getStatisticalList() {
		String isOver = getRequest().getParameter("isOver");//是否回复
		String isAdmin = getRequest().getParameter("isAdmin");//管理员查看所有办件
		isAdmin = "1";
		getRequest().setAttribute("isAdmin", isAdmin);
		String title = getRequest().getParameter("title");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " ";
		
		String wfUserName = getRequest().getParameter("wfUserName");
		wfUserName = CommonUtil.stringNotNULL(wfUserName) ? wfUserName.replaceAll("'",
				"\\'\\'") : "";
		if (CommonUtil.stringNotNULL(wfUserName)) {
			wfUserName = wfUserName.trim();
			List<Employee> list_emp = employeeService.findEmployeesByMc(wfUserName);
			String userIds = "";
			if(null != list_emp && list_emp.size()>0){
				for(Employee emp:list_emp){
					userIds += emp.getEmployeeGuid() + ",";
				}
				userIds = userIds.substring(0,userIds.length()-1);
				userIds = userIds.replace(",", "','");
			}
			conditionSql += " and p.user_uid in ('" + userIds.trim()+ "')";
			getRequest().setAttribute("wfUserName", wfUserName);
		}
		
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("title", title);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.apply_time >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		String status = getRequest().getParameter("status");
		if(status != null && status.equals("3")){
	    	conditionSql +=" and p.jdqxdate <= sysdate ";
	    	getRequest().setAttribute("status", status);
		}
		
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		
		
		String itemId = getRequest().getParameter("itemId");
		if(CommonUtil.stringNotNULL(itemId)){	//根据itemid查询
			conditionSql +=" and p.wf_item_uid ='"+itemId+"'";
		}
		getRequest().setAttribute("itemId", itemId);
		
		String wh = getRequest().getParameter("wh");		//文号
		if(CommonUtil.stringNotNULL(wh)){	
			conditionSql +=" and wh.wh like'%"+wh.trim()+"%'";
		}
		getRequest().setAttribute("wh", wh);
		
		String sernum = getRequest().getParameter("sernum");		//编号
		if(CommonUtil.stringNotNULL(sernum)){	
			String sernum_sub = sernum.substring(sernum.indexOf("】")+1,sernum.length());
			conditionSql += " and ((wh.sernum like '%"+sernum.trim()+"%') or (wh.sernum like '%"+sernum_sub.trim()+"%' and i.vc_sxlx='2'))";
		}
		
		getRequest().setAttribute("sernum", sernum);
		
		if(CommonUtil.stringNotNULL(isOver)&&"1".equals(isOver)){
			conditionSql += " and p.is_over = 'NOT_OVER'";
		}else if(CommonUtil.stringNotNULL(isOver)&&"0".equals(isOver)){
			conditionSql += " and p.is_over = 'OVER'";
		}
		getRequest().setAttribute("isOver", isOver);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("isAdmin", isAdmin);
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));

		List<Pending> list;
		// 区分工作流的待办列表获取
		int count = businessService.getCountOfStatistical(conditionSql,emp.getEmployeeGuid(), "",map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = businessService.getStatisticalList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,map);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemid", itemid);
		
		List<WfItem> itemList = itemService.getAllWfItem();
		getRequest().setAttribute("itemList", itemList);
		
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
		if(getSession().getAttribute("myPendDepts") == null){
			List<SimpleDeptVo> depts = departmentService.getDeptInfo();
			getRequest().setAttribute("myPendDepts", depts);
			
		}
		return "statisticalList";
	}

	/**
	 * 获取催办列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-8-26 下午4:32:37
	 */
	public String getPressList() {
		String isOver = getRequest().getParameter("isOver");//是否回复
		String isAdmin = getRequest().getParameter("isAdmin");//管理员查看所有办件
		isAdmin = "1";
		getRequest().setAttribute("isAdmin", isAdmin);
		String title = getRequest().getParameter("title");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " ";
		
		String wfUserName = getRequest().getParameter("wfUserName");
		wfUserName = CommonUtil.stringNotNULL(wfUserName) ? wfUserName.replaceAll("'",
				"\\'\\'") : "";
		if (CommonUtil.stringNotNULL(wfUserName)) {
			wfUserName = wfUserName.trim();
			List<Employee> list_emp = employeeService.findEmployeesByMc(wfUserName);
			String userIds = "";
			if(null != list_emp && list_emp.size()>0){
				for(Employee emp:list_emp){
					userIds += emp.getEmployeeGuid() + ",";
				}
				userIds = userIds.substring(0,userIds.length()-1);
				userIds = userIds.replace(",", "','");
			}
			conditionSql += " and p.user_uid in ('" + userIds.trim()+ "')";
			getRequest().setAttribute("wfUserName", wfUserName);
		}
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("title", title);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.apply_time >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		String status = getRequest().getParameter("status");
		if(status != null && status.equals("3")){
	    	conditionSql +=" and p.jdqxdate <= sysdate ";
	    	getRequest().setAttribute("status", status);
		}
		
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		
		
		String itemId = getRequest().getParameter("itemId");
		if(CommonUtil.stringNotNULL(itemId)){	//根据itemid查询
			conditionSql +=" and p.wf_item_uid ='"+itemId+"'";
		}
		getRequest().setAttribute("itemId", itemId);
		
		String wh = getRequest().getParameter("wh");		//文号
		if(CommonUtil.stringNotNULL(wh)){	
			conditionSql +=" and wh.wh like'%"+wh.trim()+"%'";
		}
		getRequest().setAttribute("wh", wh);
		
		String sernum = getRequest().getParameter("sernum");		//编号
		if(CommonUtil.stringNotNULL(sernum)){	
			String sernum_sub = sernum.substring(sernum.indexOf("】")+1,sernum.length());
			conditionSql += " and ((wh.sernum like '%"+sernum.trim()+"%') or (wh.sernum like '%"+sernum_sub.trim()+"%' and i.vc_sxlx='2'))";
		}
		
		getRequest().setAttribute("sernum", sernum);
		
		if(CommonUtil.stringNotNULL(isOver)&&"1".equals(isOver)){
			conditionSql += " and p.is_over = 'NOT_OVER'";
		}else if(CommonUtil.stringNotNULL(isOver)&&"0".equals(isOver)){
			conditionSql += " and p.is_over = 'OVER'";
		}
		getRequest().setAttribute("isOver", isOver);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("isAdmin", isAdmin);
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));

		List<Pending> list;
		// 区分工作流的待办列表获取
		int count = businessService.getCountOfPress(conditionSql,emp.getEmployeeGuid(), "",map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = businessService.getPressList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,map);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemid", itemid);
		
		List<WfItem> itemList = itemService.getAllWfItem();
		getRequest().setAttribute("itemList", itemList);
		
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
		if(getSession().getAttribute("myPendDepts") == null){
			List<SimpleDeptVo> depts = departmentService.getDeptInfo();
			getRequest().setAttribute("myPendDepts", depts);
			
		}
		return "pressList";
	}
	
	/**
	 * 催办信息
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-8-24 下午7:22:09
	 */
	public String getPressMsg(){
		PressMsg pressMsg = new PressMsg();
		String processId = getRequest().getParameter("processId");
		pressMsg = getPorcessPressMsg(processId);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		WfProcess wfp = tableInfoService.getProcessById(processId);
		String userId_wfp = "";
		String title = "";
		Date apply_time = new Date();
		Date end_time = new Date();
		String limit_days="";
		if(null != wfp){
			userId_wfp= wfp.getUserUid();
			title = wfp.getProcessTitle();
			apply_time = wfp.getApplyTime();
			end_time = wfp.getJdqxDate();
			long end = end_time.getTime();
			long now = new Date().getTime();
			String isBeyond = "";
			if(now<=end){
				isBeyond = "未超期";
			}else{
				isBeyond = "超期";
			}
			getRequest().setAttribute("isBeyond",isBeyond );
		}
		
		getRequest().setAttribute("pressTitle",title );
		getRequest().setAttribute("pressApplyTime",sdf.format(apply_time) );
		getRequest().setAttribute("pressEndTime",sdf.format(end_time) );
		getRequest().setAttribute("limit_days",limit_days );
		
		//获取当前办件的办理人
		Employee emp_wfp = employeeService.findEmployeeById(userId_wfp);
		String userName_wfp = "";
		if(null != emp_wfp){
			userName_wfp = emp_wfp.getEmployeeName();
			Department dep = departmentService.findDepartmentById(emp_wfp.getDepartmentGuid());
			if(null!=dep){
				String pressDepName = dep.getDepartmentName();
				getRequest().setAttribute("pressDepName",pressDepName );
			}
			getRequest().setAttribute("userName_wfp",userName_wfp );
		}
		
		if(pressMsg != null){
			getRequest().setAttribute("processId", processId);
			getRequest().setAttribute("pressContent", pressMsg.getPressContent());
			getRequest().setAttribute("isSend", pressMsg.getIsSend());
		}
		
		return "getPressMsg";
	}
	
	/**
	 * 根据办件id获取催办信息
	 * 描述：TODO 对此方法进行描述
	 * @param processId
	 * @return PressMsg
	 * 作者:季振华
	 * 创建时间:2016-8-24 下午7:38:28
	 */
	private PressMsg getPorcessPressMsg(String processId){
		
		List<PressMsg> msgList = new ArrayList<PressMsg>();
		PressMsg pressMsg = new PressMsg();
		msgList = businessService.getMsgListByProcessId(processId);
		if(msgList != null && msgList.size() > 0){
			pressMsg = msgList.get(0);
		}
		
		return pressMsg;
	}
	
	/**
	 * 新增催办信息
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-8-24 下午7:39:56
	 */
	public void addPressMsg(){
		PressMsg pressMsg = new PressMsg();
		String pressContent = getRequest().getParameter("pressContent");
		
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		
		String processId = getRequest().getParameter("processId");
		
		
		String msgId = UuidGenerator.generate36UUID();
		
		pressMsg.setId(msgId);
		pressMsg.setProcessId(processId);
		pressMsg.setPressContent(pressContent);
		pressMsg.setApplyTime(new Date());
		pressMsg.setUserId(emp.getEmployeeGuid());
		pressMsg.setUserName(emp.getEmployeeName());
		
		businessService.addPressMsg(pressMsg);
			
	}
	
	/**
	 * 获取催办信息列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-12-14 下午6:56:11
	 */
	public String getPressMsgList(){
		String isAdmin = "1";
		
		String title = getRequest().getParameter("title");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " ";
		
		String wfUserName = getRequest().getParameter("wfUserName");
		wfUserName = CommonUtil.stringNotNULL(wfUserName) ? wfUserName.replaceAll("'",
				"\\'\\'") : "";
		if (CommonUtil.stringNotNULL(wfUserName)) {
			wfUserName = wfUserName.trim();
			List<Employee> list_emp = employeeService.findEmployeesByMc(wfUserName);
			String userIds = "";
			if(null != list_emp && list_emp.size()>0){
				for(Employee emp:list_emp){
					userIds += emp.getEmployeeGuid() + ",";
				}
				userIds = userIds.substring(0,userIds.length()-1);
				userIds = userIds.replace(",", "','");
			}
			conditionSql += " and p.user_uid in ('" + userIds.trim()+ "')";
			getRequest().setAttribute("wfUserName", wfUserName);
		}
		
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("title", title);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.apply_time >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		getRequest().setAttribute("itemid", itemid);
		
		String itemId = getRequest().getParameter("itemId");
		if(CommonUtil.stringNotNULL(itemId)){	//根据itemid查询
			conditionSql +=" and p.wf_item_uid ='"+itemId+"'";
		}
		getRequest().setAttribute("itemId", itemId);
		
		
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("isAdmin", isAdmin);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		List<Pending> list;
		// 区分工作流的待办列表获取
		int count = businessService.getCountOfPressMsg(conditionSql,emp.getEmployeeGuid(), "",map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = businessService.getPressMsgList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,map);
		
		getRequest().setAttribute("list", list);
		
		List<WfItem> itemList = itemService.getAllWfItem();
		getRequest().setAttribute("itemList", itemList);
		
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
		if(getSession().getAttribute("myPendDepts") == null){
			List<SimpleDeptVo> depts = departmentService.getDeptInfo();
			getRequest().setAttribute("myPendDepts", depts);
			
		}
		
		return "pressMsgList";
	}
	
	/**
	 * 导出催办列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-8-26 上午9:28:49
	 */
	public String exportPressList(){
		
		String isAdmin = getRequest().getParameter("isAdmin");//管理员查看所有办件
		isAdmin = "1";
		getRequest().setAttribute("isAdmin", isAdmin);
		String isWarn = getRequest().getParameter("isWarn");//控制剩余期限
		getRequest().setAttribute("isWarn", isWarn);
		String title = getRequest().getParameter("title");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitUser = getRequest().getParameter("commitUser");
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = "  ";

		String wfUserName = getRequest().getParameter("wfUserName");
		wfUserName = CommonUtil.stringNotNULL(wfUserName) ? wfUserName.replaceAll("'",
				"\\'\\'") : "";
		if (CommonUtil.stringNotNULL(wfUserName)) {
			wfUserName = wfUserName.trim();
			List<Employee> list_emp = employeeService.findEmployeesByMc(wfUserName);
			String userIds = "";
			if(null != list_emp && list_emp.size()>0){
				for(Employee emp:list_emp){
					userIds += emp.getEmployeeGuid() + ",";
				}
				userIds = userIds.substring(0,userIds.length()-1);
				userIds = userIds.replace(",", "','");
			}
			conditionSql += " and p.user_uid in ('" + userIds.trim()+ "')";
			getRequest().setAttribute("wfUserName", wfUserName);
		}
		
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("title", title);
		}
		if (CommonUtil.stringNotNULL(commitUser)) {
			commitUser = commitUser.trim();
			conditionSql += " and (select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid ) like  '%"+commitUser+"%' escape '\\' ";
			getRequest().setAttribute("commitUser", commitUser);
		}
		
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.apply_time >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		String status = getRequest().getParameter("status");
		if(status != null && status.equals("3")){
	    	conditionSql +=" and p.jdqxdate <= sysdate ";
	    	getRequest().setAttribute("status", status);
		}
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		
		String itemId = getRequest().getParameter("itemId");
		if(CommonUtil.stringNotNULL(itemId)){	//根据itemid查询
			conditionSql +=" and p.wf_item_uid ='"+itemId+"'";
		}
		getRequest().setAttribute("itemId", itemId);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("isAdmin", isAdmin);
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);

		List<Pending> list;
		// 区分工作流的待办列表获取
		//int count = pendingService.getCountOfPending(conditionSql,emp.getEmployeeGuid(), "",map);
		//Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = businessService.getPressList(conditionSql,emp.getEmployeeGuid(), null,null,map);
				
		MyUtils myUtils = new MyUtils();
		
		String[] titles = new String[]{"序号","标题","办理部门","办理人员","提交时间","步骤期限","事项类别","是否超期"}; 
		
		List<String[]> dataSource=new ArrayList<String[]>();
		for(int j = 0 ;j<list.size() ;j++){
			Pending pending = list.get(j);
		    String title_e = pending.getProcess_title()==null?"":pending.getProcess_title();//标题
			String userDept_e = pending.getUserDeptId()==null?"":pending.getUserDeptId();//办理部门
			String userName_e = pending.getUserName()==null?"":pending.getUserName();//办理人员
			
			String apply_time_e = "";
			if(null != pending.getApply_time()){
				Date apply_time  = pending.getApply_time()==null?new Date():pending.getApply_time() ; //提交时间
				apply_time_e = myUtils.convertDateToString(apply_time);
			}
			String jdqxDate_e = "";
			if(null != pending.getJdqxDate()){
				Date jdqxDate  = pending.getJdqxDate()==null?new Date():pending.getJdqxDate() ; //步骤期限
				jdqxDate_e = myUtils.convertDateToString(jdqxDate);
			}
			
			String item_name_e = pending.getItem_name()==null?"":pending.getItem_name();//办文类型
			String isbeyond = pending.getStatus() == null?"":pending.getStatus();//是否超期
			String isbeyond_e = "";
			if(CommonUtil.stringNotNULL(isbeyond) && "3".equals(isbeyond)){
				isbeyond_e = "超期";
			}else{
				isbeyond_e = "未超期";
			}
			
			//"序号","编号","文号","标题","办理部门","交办时间","办文类型","超期天数"
			String[] s= new String[]{(j+1)+"",title_e,userDept_e,userName_e,apply_time_e,jdqxDate_e,
					item_name_e,isbeyond_e};
			dataSource.add(s);
		}
		String biaoti ="催办列表"+myUtils.convertDayToString(new Date())+"";
		ExcelExport e = ExcelExport.getExcelExportInstance(biaoti,getResponse());
		e.setBiaoti(biaoti);
		e.setAllCelNum(7);
		e.setTitles(titles);
		e.setDataSource(dataSource);
		e.setColWidth(1, 15);
		e.setColWidth(2, 60);
		e.setColWidth(3, 30);
		e.setColWidth(4, 18);
		e.setColWidth(5, 25);
		e.setColWidth(6, 25);
		e.setColWidth(7, 25);
		e.setColWidth(8, 20);
		e.export();
			
		return null;
	}
	
	/**
	 * 导出超期汇总列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-12-15 上午10:50:28
	 */
	public String exportStatisticalList(){
		
		String isOver = getRequest().getParameter("isOver");//是否回复
		String isAdmin = getRequest().getParameter("isAdmin");//管理员查看所有办件
		isAdmin = "1";
		getRequest().setAttribute("isAdmin", isAdmin);
		String title = getRequest().getParameter("title");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");
		String commitTimeTo = getRequest().getParameter("commitTimeTo");
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = " ";
		
		String wfUserName = getRequest().getParameter("wfUserName");
		wfUserName = CommonUtil.stringNotNULL(wfUserName) ? wfUserName.replaceAll("'",
				"\\'\\'") : "";
		if (CommonUtil.stringNotNULL(wfUserName)) {
			wfUserName = wfUserName.trim();
			List<Employee> list_emp = employeeService.findEmployeesByMc(wfUserName);
			String userIds = "";
			if(null != list_emp && list_emp.size()>0){
				for(Employee emp:list_emp){
					userIds += emp.getEmployeeGuid() + ",";
				}
				userIds = userIds.substring(0,userIds.length()-1);
				userIds = userIds.replace(",", "','");
			}
			conditionSql += " and p.user_uid in ('" + userIds.trim()+ "')";
			getRequest().setAttribute("wfUserName", wfUserName);
		}
		
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("title", title);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.apply_time >= to_date('"+commitTimeFrom+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			conditionSql +=" and p.APPLY_TIME <= to_date('"+commitTimeTo+"','yyyy-mm-dd') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		String status = getRequest().getParameter("status");
		if(status != null && status.equals("3")){
	    	conditionSql +=" and p.jdqxdate <= sysdate ";
	    	getRequest().setAttribute("status", status);
		}
		
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}
		
		
		String itemId = getRequest().getParameter("itemId");
		if(CommonUtil.stringNotNULL(itemId)){	//根据itemid查询
			conditionSql +=" and p.wf_item_uid ='"+itemId+"'";
		}
		getRequest().setAttribute("itemId", itemId);
		
		String wh = getRequest().getParameter("wh");		//文号
		if(CommonUtil.stringNotNULL(wh)){	
			conditionSql +=" and wh.wh like'%"+wh.trim()+"%'";
		}
		getRequest().setAttribute("wh", wh);
		
		String sernum = getRequest().getParameter("sernum");		//编号
		if(CommonUtil.stringNotNULL(sernum)){	
			String sernum_sub = sernum.substring(sernum.indexOf("】")+1,sernum.length());
			conditionSql += " and ((wh.sernum like '%"+sernum.trim()+"%') or (wh.sernum like '%"+sernum_sub.trim()+"%' and i.vc_sxlx='2'))";
		}
		
		getRequest().setAttribute("sernum", sernum);
		
		if(CommonUtil.stringNotNULL(isOver)&&"1".equals(isOver)){
			conditionSql += " and p.is_over = 'NOT_OVER'";
		}else if(CommonUtil.stringNotNULL(isOver)&&"0".equals(isOver)){
			conditionSql += " and p.is_over = 'OVER'";
		}
		getRequest().setAttribute("isOver", isOver);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("isAdmin", isAdmin);
		// 删除 process title 为空的数据
		tableInfoService.deleteErrorProcess();
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));

		List<Pending> list;
		// 区分工作流的待办列表获取
		int count = businessService.getCountOfStatistical(conditionSql,emp.getEmployeeGuid(), "",map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = businessService.getStatisticalList(conditionSql,emp.getEmployeeGuid(), null, null,map);
				
		MyUtils myUtils = new MyUtils();
		
		String[] titles = new String[]{"序号","标题","办理部门","办理人员","提交时间","步骤期限","办理时间","事项类别","是否办理"}; 
		
		List<String[]> dataSource=new ArrayList<String[]>();
		for(int j = 0 ;j<list.size() ;j++){
			Pending pending = list.get(j);
		    String title_e = pending.getProcess_title()==null?"":pending.getProcess_title();//标题
			String userDept_e = pending.getUserDeptId()==null?"":pending.getUserDeptId();//办理部门
			String userName_e = pending.getUserName()==null?"":pending.getUserName();//办理人员
			
			String apply_time_e = "";
			if(null != pending.getApply_time()){
				Date apply_time  = pending.getApply_time()==null?new Date():pending.getApply_time() ; //提交时间
				apply_time_e = myUtils.convertDateToString(apply_time);
			}
			String jdqxDate_e = "";
			if(null != pending.getJdqxDate()){
				Date jdqxDate  = pending.getJdqxDate()==null?new Date():pending.getJdqxDate() ; //步骤期限
				jdqxDate_e = myUtils.convertDateToString(jdqxDate);
			}
			String finish_time_e = "";
			if(null != pending.getFinish_time()){
				Date finish_time  = pending.getFinish_time()==null?new Date():pending.getFinish_time() ; //提交时间
				finish_time_e = myUtils.convertDateToString(finish_time);
			}
			
			String item_name_e = pending.getItem_name()==null?"":pending.getItem_name();//办文类型
			String is_Over = pending.getIsover() == null?"":pending.getIsover();//是否已办
			String is_Over_e = "";
			if(CommonUtil.stringNotNULL(is_Over) && "NOT_OVER".equals(is_Over)){
				is_Over_e = "未办";
			}else{
				is_Over_e = "已办";
			}
			
			//"序号","编号","文号","标题","办理部门","交办时间","办文类型","超期天数"
			String[] s= new String[]{(j+1)+"",title_e,userDept_e,userName_e,apply_time_e,jdqxDate_e,finish_time_e,
					item_name_e,is_Over_e};
			dataSource.add(s);
		}
		String biaoti ="超期汇总列表"+myUtils.convertDayToString(new Date())+"";
		ExcelExport e = ExcelExport.getExcelExportInstance(biaoti,getResponse());
		e.setBiaoti(biaoti);
		e.setAllCelNum(8);
		e.setTitles(titles);
		e.setDataSource(dataSource);
		e.setColWidth(1, 15);
		e.setColWidth(2, 35);
		e.setColWidth(3, 30);
		e.setColWidth(4, 18);
		e.setColWidth(5, 25);
		e.setColWidth(6, 25);
		e.setColWidth(7, 25);
		e.setColWidth(8, 20);
		e.setColWidth(9, 15);
		e.export();
			
		return null;
	}
	
	/**
	 * 描述：获取传阅列表
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午11:08:58
	 */
	public String getCyDoFileList() {
		String title = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid");
		String status = getRequest().getParameter("status");
		
		if(itemid!=null&&!itemid.equals("")){
			WfItem wfItem = itemService.getItemById(itemid);
			if(wfItem!=null){
				itemName = wfItem.getVc_sxmc();
			}
		}
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'", "\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		String conditionSql = "";// 查询条件
		if (CommonUtil.stringNotNULL(title)) {
			conditionSql = " and t.dofile_title like '%" + title.trim() + "%' ";
		}
		if (CommonUtil.stringNotNULL(itemName)) {
			conditionSql += " and i.vc_sxmc = '" + itemName + "' ";
		}
		if(StringUtils.isNotBlank(status)){
			if(status.equals("0")){
				conditionSql += " and h.isread = '0' ";
			}else if(status.equals("1")){
				conditionSql += " and h.isread = '1' ";
			}
		}
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int count = tableInfoService.getCountCyDoFiles(conditionSql, employee.getEmployeeGuid());
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<DoFile> doFileList = tableInfoService.getCyDoFileList(conditionSql, employee.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		
		if(doFileList!=null){
			for(DoFile doFile : doFileList){
				if(doFile.getProcessId()!=null&&!"".equals(doFile.getProcessId())){
					WfProcess process = tableInfoService.getProcessById(doFile.getProcessId());
					if(process.getNodeUid()!=null&&!"".equals(process.getNodeUid())){
						 WfNode node = tableInfoService.getWfNodeById(process.getNodeUid());
						 doFile.setNodeName(node.getWfn_name());
					}
				}
			}
		}
		getRequest().setAttribute("doFileList", doFileList);
		getRequest().setAttribute("wfTitle", title);
		getRequest().setAttribute("itemName", itemName);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("status", status);
		if(StringUtils.isNotBlank(status)){
			if(status.equals("0")){
				return "getCyDoFileList";
			}else if(status.equals("1")){
				return "getYyDoFileList";
			}
		}
		return "getCyDoFileList";
		
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午4:04:51
	 */
	public void addHandRoundShip(){
		JSONObject jsonObj = getJSONObject();
		String userIds = "";
		String instanceId = "";
		String userId = "";
		if(null != jsonObj){
			userIds = (String)jsonObj.get("userIds");
			instanceId = (String)jsonObj.get("instanceId");
			userId = (String)jsonObj.get("userId");
		}else{
			userIds = getRequest().getParameter("userIds");
			instanceId = getRequest().getParameter("instanceId");
		}
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(null == emp){
			emp = employeeService.findEmployeeById(userId);
		}
		JSONObject outObj = new JSONObject();
		if(StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(instanceId)){ 
			businessService.addHandRoundShip(userIds, instanceId, emp);
			outObj.put("result", "success");
		}else{
			outObj.put("result", "fail");
			outObj.put("message", "userIds or instanceId can not be null");
		}
		toPage(outObj.toString());
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午4:19:09
	 */
	public void updateHandRoundShip(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String instanceId = getRequest().getParameter("instanceId");
		JSONObject outObj = new JSONObject();
		if(null!= emp && StringUtils.isNotBlank(instanceId)){
			HandRoundShip entity = new HandRoundShip();
			entity.setInstanceId(instanceId);
			entity.setUserId(emp.getEmployeeGuid());
			entity.setIsRead(1);
			businessService.updateHandRoundShip(entity);
			outObj.put("result", "success");
		}else{
			outObj.put("result", "fail");
			outObj.put("message", "10001");
		}
		toPage(outObj.toString());
	}
	
	public String getHandRoundShips(){
		String instanceId = getRequest().getParameter("instanceId");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = businessService.countHandRoundShips(instanceId);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<HandRoundShip> list = businessService.selectHandRoundShips(instanceId, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("instanceId", instanceId);
		return "handRoundShipList";
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-9-8 上午11:06:44
	 */
	public void getHandRoundShips4Mobile(){
		JSONObject jsonObj = getJSONObject();
		String instanceId = "";
		Integer column = null;
		Integer pagesize = null;
		if(null != jsonObj){
			instanceId = (String)jsonObj.get("instanceId");
			// 页数ag
			column = (String) jsonObj.get("column") == null ? 0 : Integer.parseInt((String) jsonObj.get("column"));
			// 页面显示的条数
			pagesize = (String) jsonObj.get("pagesize") == null ? 10 : Integer.parseInt((String) jsonObj.get("pagesize"));
		}else{
			instanceId = getRequest().getParameter("instanceId");
			column =  getRequest().getParameter("column") == null ? 0 : Integer.parseInt((String) getRequest().getParameter("column"));
			// 页面显示的条数
			pagesize = (String)  getRequest().getParameter("pagesize") == null ? 10 : Integer.parseInt((String)  getRequest().getParameter("pagesize"));
		}
		
		List<HandRoundShip> list = businessService.selectHandRoundShips(instanceId, column*pagesize, pagesize);
		toPage(JSONArray.fromObject(list).toString());
	}
	
	
	//-------------------------------------------
	//--------------以下是私有方法--------------------
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
}
