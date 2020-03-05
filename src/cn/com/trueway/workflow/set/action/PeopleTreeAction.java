package cn.com.trueway.workflow.set.action;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.DepSortUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.CommonGroupUsers;
import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeSpell;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.Leader;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeLeaderService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.EmployeeSpellService;
import cn.com.trueway.workflow.set.service.GroupService;
import cn.com.trueway.workflow.set.service.ZtreeService;
import cn.com.trueway.workflow.set.util.PinYin4jUtil;

/**
 * @author 赵坚
 * @version 创建时间：赵坚
 */
public class PeopleTreeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private EmployeeSpellService 		employeeSpellService;
	
	private EmployeeLeaderService 		employeeLeaderService;
	
	private EmployeeService 			employeeService;
	
	private DepartmentService 			departmentService;
	
	private ZtreeService 				ztreeService;
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private GroupService groupService;
	
	private TableInfoService			tableInfoService;
	
	public EmployeeSpellService getEmployeeSpellService() {
		return employeeSpellService;
	}

	public void setEmployeeSpellService(EmployeeSpellService employeeSpellService) {
		this.employeeSpellService = employeeSpellService;
	}

	public EmployeeLeaderService getEmployeeLeaderService() {
		return employeeLeaderService;
	}

	public void setEmployeeLeaderService(EmployeeLeaderService employeeLeaderService) {
		this.employeeLeaderService = employeeLeaderService;
	}

	private String root;
	
	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public ZtreeService getZtreeService() {
		return ztreeService;
	}

	public void setZtreeService(ZtreeService ztreeService) {
		this.ztreeService = ztreeService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public String showDepartmentTree(){
		String siteId = getRequest().getParameter("siteId");
		String status = getRequest().getParameter("status");
		String mac = getRequest().getParameter("mac");
		String nodeId = getRequest().getParameter("nodeId");
		String exchange = getRequest().getParameter("exchange");
		//String isTreeAll =  getRequest().getParameter("isTreeAll");
		String send = getRequest().getParameter("send");
		String routType = getRequest().getParameter("routType");
		String defUserId = getRequest().getParameter("defUserId");
		String instanceId = getRequest().getParameter("instanceId");
		String pleaseOrWatch = getRequest().getParameter("pleaseOrWatch");
		String nopeople = getRequest().getParameter("nopeople");
		WfNode wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
		String bdDefUserIds = "";
		if(wfNode!=null && nopeople==null){
			bdDefUserIds = wfNode.getWfn_bd_user();//默认人员
		}
		String jsonData = "";
		String dbUserIds = "";
		String dpUserIds = "";
		String yueshiText = "";
		String banliText = "";
		JSONObject jsonObject = getJSONObject();
		if(jsonObject!=null){
			jsonData = jsonObject.get("jsondata")!=null?(String)jsonObject.get("jsondata"):"";
			dbUserIds = jsonObject.get("dbUserIds")!=null?((String)jsonObject.get("dbUserIds")).split("\\$\\$")[0]:"";
			if(jsonObject.get("dbUserIds")!=null){
				if(((String)jsonObject.get("dbUserIds")).split("\\$\\$").length>1){
					dpUserIds = ((String)jsonObject.get("dbUserIds")).split("\\$\\$")[1];
				}
				if(((String)jsonObject.get("dbUserIds")).split("\\$\\$").length>2){
					yueshiText = ((String)jsonObject.get("dbUserIds")).split("\\$\\$")[2];
				}
				if(((String)jsonObject.get("dbUserIds")).split("\\$\\$").length>3){
					banliText = ((String)jsonObject.get("dbUserIds")).split("\\$\\$")[3];
				}
			}
		}
		if(!dbUserIds.equals("")){
			getRequest().setAttribute("isHasValue", "1");
		}
		if(StringUtils.isNotBlank(bdDefUserIds)&&dpUserIds==""){
			if(pleaseOrWatch!=null&&pleaseOrWatch.equals("1")){
				
			}else{
				dbUserIds = bdDefUserIds;
				String[] arrays = bdDefUserIds.split(",");
				for(String str : arrays){
					Employee e = tableInfoService.findEmpByUserId(str);
					if(e!=null){
						dpUserIds += e.getDepartmentGuid()+"_"+e.getEmployeeGuid()+"_0,";
					}
				}
				if(dpUserIds.length()>1){
					dpUserIds = dpUserIds.substring(0, dpUserIds.length()-1);
				}
			}
		}
		getRequest().setAttribute("jsondata", jsonData);
		getRequest().setAttribute("dbUserIds", dbUserIds);
		getRequest().setAttribute("dpUserIds", dpUserIds);
		getRequest().setAttribute("yueshiText", yueshiText);
		getRequest().setAttribute("banliText", banliText);
		
		String isDuBan = getRequest().getParameter("isDuBan");
		if(CommonUtil.stringNotNULL(pleaseOrWatch)&&"1".equals(pleaseOrWatch)){
			getRequest().setAttribute("pleaseOrWatch", true);
		}
		if(CommonUtil.stringNotNULL(isDuBan)&&isDuBan.equals("yes")){
			
		}else{
			isDuBan = "";
		}
		String userId = getRequest().getParameter("userid");
		getRequest().setAttribute("userId", userId);
		getRequest().setAttribute("isDuBan",isDuBan);
		
		getRequest().setAttribute("defUserId", defUserId);
		if (routType == null || ("").equals(routType)
				|| ("null").equals(routType)) {
			routType = "0";
		}
		String isZf = getRequest().getParameter("isZf");
		getRequest().setAttribute("isZf", isZf);
		getRequest().setAttribute("exchange", exchange);
		getRequest().setAttribute("send", send);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("routType", routType);
		getRequest().setAttribute("exchange", exchange);
		getRequest().setAttribute("mac", mac);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("instanceId", instanceId);
		if(nodeId == null || "".equals(nodeId)){
			getRequest().setAttribute("type", "dept");
		}else{
			getRequest().setAttribute("type", "");
		}
		String showCheckbox = getRequest().getParameter("showCheckbox");
		getRequest().setAttribute("showCheckbox", showCheckbox);
		getRequest().setAttribute("siteId", siteId);
		
		String fgwSiteId = SystemParamConfigUtil.getParamValueByParam("nb_zr_siteId");
		if(StringUtils.isNotBlank(fgwSiteId) && StringUtils.isNotBlank(siteId) && fgwSiteId.equals(siteId)){
			getRequest().setAttribute("isShowZr", "1");
		}else{
			getRequest().setAttribute("isShowZr", "2");
		}
		String notWriteFormSiteId = SystemParamConfigUtil.getParamValueByParam("notWriteFormSiteId");
		if(CommonUtil.stringNotNULL(notWriteFormSiteId)&&notWriteFormSiteId.contains(siteId)){
			getRequest().setAttribute("notWriteForm", true);
		}else{
			getRequest().setAttribute("notWriteForm", false);
		}
		
		if(isDuBan.equals("yes")){
			return "departmentTreeDb";
		}
		if(CommonUtil.stringNotNULL(pleaseOrWatch)&&"1".equals(pleaseOrWatch)){
			return "departmentTreeQy";
		}
		return "departmentTree";
	}
	
	public String showDepartmentTreeNew(){
		String siteId = getRequest().getParameter("siteId");
		String status = getRequest().getParameter("status");
		String mac = getRequest().getParameter("mac");
		String nodeId = getRequest().getParameter("nodeId");
		String exchange = getRequest().getParameter("exchange");
		String send = getRequest().getParameter("send");
		String routType = getRequest().getParameter("routType");
		String defUserId = getRequest().getParameter("defUserId");
		getRequest().setAttribute("defUserId", defUserId);
		if (routType == null || ("").equals(routType)
				|| ("null").equals(routType)) {
			routType = "0";
		}
		String isZf = getRequest().getParameter("isZf");
		getRequest().setAttribute("isZf", isZf);
		getRequest().setAttribute("exchange", exchange);
		getRequest().setAttribute("send", send);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("routType", routType);
		getRequest().setAttribute("exchange", exchange);
		getRequest().setAttribute("mac", mac);
		getRequest().setAttribute("status", status);
		if(nodeId == null || "".equals(nodeId)){
			getRequest().setAttribute("type", "dept");
		}else{
			getRequest().setAttribute("type", "");
		}
		String showCheckbox = getRequest().getParameter("showCheckbox");
		getRequest().setAttribute("showCheckbox", showCheckbox);
		getRequest().setAttribute("siteId", siteId);
		return "departmentTreeNew";
	}
	
	public void getArrData() throws UnsupportedEncodingException{
		String nodeId=getRequest().getParameter("nodeId");
		String defUserId = getRequest().getParameter("defUserId");
		String mc=getRequest().getParameter("mc");
		String type = getRequest().getParameter("type");
		String userId = getRequest().getParameter("userId");
		//获取当前登录人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfNode wfNode =  null;
		String wfUId = "";
		String routeType="";
		String bdUser = "";
		Integer isSkipUser = 0;
		if(type != null && type.equals("dept")){
		}else{
			wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
			WfMain wfMain = wfNode.getWfMain();
			wfUId = wfMain.getWfm_id();
			routeType=wfNode.getWfn_route_type();
			bdUser = wfNode.getWfn_bd_user();
			isSkipUser = wfNode.getWfn_skipUser();
			if(isSkipUser==null){
				isSkipUser=0;
			}
			if(StringUtils.isBlank(bdUser)){
				String isdefdep = wfNode.getWfn_isdefdep(); 		
				if(isdefdep!=null && isdefdep.equals("1")){		//默认部门
					//获取人员的id
					DepartmentLeader departmentLeader =employeeLeaderService.findDepartmentLeaderByEmpId(emp.getEmployeeGuid());
					if(departmentLeader!=null){
						bdUser = departmentLeader.getLeaderId();		//领导人Id
					}
				}
			}
		}
		
		if(CommonUtil.stringIsNULL(bdUser) && CommonUtil.stringNotNULL(defUserId) && defUserId.equals("nullUserId")){
			bdUser = emp.getEmployeeGuid();
		}
		
		String depId = emp.getDepartmentGuid();
		Department depart = departmentService.findDepartmentById(depId);
		String dep_shortDn = depart.getDepartmentShortdn();
		List<Department> secDepList = null;
		if(dep_shortDn!=null && !dep_shortDn.equals("")){
			String[] depIds = dep_shortDn.split(",");
			if(depIds!=null && depIds.length>=4){
				String begin_dep = depIds[0]+","+depIds[1]+","+depIds[2]+","+depIds[3];
				secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
			}
		}
		 
		String groupId = "";
		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
		List<InnerUserMapEmployee> innerUsers = new ArrayList<InnerUserMapEmployee>();
		if (!("").equals(wfNode) && wfNode != null) {
			// 获取人员组的id
			groupId = wfNode.getWfn_staff();
			String ids = "";
			if(secDepList!=null){
				if(secDepList!=null && secDepList.size()>0){
					for(int i=0; i<secDepList.size(); i++){
						ids += "'"+secDepList.get(i).getDepartmentGuid()+"',";
					}
					if(ids!=null&& ids.length()>0){
						ids = ids.substring(0, ids.length()-1);
					}
				}
			}
			boolean needBm = false;
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,emp.getEmployeeGuid());
			}else{
				String needBmWfUId =  SystemParamConfigUtil.getParamValueByParam("needBmWfUId");
				if(wfUId!=null && !wfUId.equals("")){
					if(needBmWfUId!=null && needBmWfUId.contains(wfUId)){
						needBm = true;
					}else{
						ids = "";
					}
				}else{
					ids = "";
				}
				// 获取人员组的人员信息
				innerUserList = groupService.getListByInnerUserId(groupId,null,ids);
			}
			
			//过虑非本部门人员
			if(isSkipUser.equals(1)){
				for (InnerUserMapEmployee iume : innerUserList) {
					if(iume.getDepartment_id().equals(depId)){
						innerUsers.add(iume);
					}
				}
			}else{
				innerUsers = innerUserList;
			}
			
			//判断是否为并行传阅式  且是需要限制部门的流程
			if(routeType!=null && "4".equals(routeType) && needBm){
				String bxcyuserids=SystemParamConfigUtil.getParamValueByParam("bxcyuserids");
				String superDepId = depart.getSuperiorGuid();
				String toAddUserIds = "";
				if(bxcyuserids!=null && !bxcyuserids.equals("")){
					String[] dep_userIds = bxcyuserids.split(";");
					for(int i=0; i<dep_userIds.length; i++){
						String dep_userId = dep_userIds[i];
						if(dep_userId!=null && !dep_userId.equals("")){
							String[] deps = dep_userId.split(":");
							if((superDepId!=null && superDepId.equals(deps[0])) || depId.equals(deps[0])){		//父部门或者本部门
								toAddUserIds = deps[1];
							}
						}
					}
				}
				if(toAddUserIds!=null && !toAddUserIds.equals("")){
					toAddUserIds="'"+toAddUserIds.replace(",", "','")+"'";
					List<InnerUserMapEmployee>  list = groupService.getInnerUserListByIds(toAddUserIds);
					if(innerUsers!=null && innerUsers.size()>0){
						for(int i=0; i<list.size(); i++){
							String emp_userId = list.get(i).getEmployee_id();
								int count = 0;
								for(int j=0; j<innerUsers.size(); j++){
									if(innerUsers.get(j).getEmployee_id().equals(emp_userId)){
										break;
									}else{
										count ++;
									}
								}
								if(count==innerUsers.size()){
									innerUsers.add(list.get(i));
								}
						}
					}else{
						innerUsers = list;
					}
				}
			}
		}
		List<Employee> empsByMc = employeeService.findEmployeesByMc(mc);
		String str4EmpsByMc = "";
		for(Employee e:empsByMc){
			str4EmpsByMc += e.getEmployeeGuid();
		}
		getResponse().setCharacterEncoding("utf-8");
		String deptsid ="";
		String userids = "";
		if (innerUsers != null) {
			for (int i = 0; i < innerUsers.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUsers.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1||str4EmpsByMc.indexOf(innerUserMapEmployee.getEmployee_id())!=-1){
					if (userids != null) {
						userids += ("," + innerUserMapEmployee.getEmployee_id());
					} else {
						userids = innerUserMapEmployee.getEmployee_id();
					}
					
					if(innerUserMapEmployee.getDepartment_id()==null || innerUserMapEmployee.getDepartment_id().equals("")){
						deptsid += innerUserMapEmployee.getEmployee_id()+",";
					}else{
						deptsid += innerUserMapEmployee.getDepartment_id()+",";
					}
				}
			}
		}
		if(userids==null&&"".equals(type)){
			return;
		}
		if(deptsid!=null && !deptsid.equals("")){
			deptsid = deptsid.substring(0, deptsid.length()-1);
		}
		// 第一次加载树时，root="source",生成“大部门”的节点
		List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(deptsid);
		List<Employee> allEmpList = new ArrayList<Employee>();
		List<Employee> allEmps = new ArrayList<Employee>();
		if(mc!=null && !mc.equals("")){
			allEmps = ztreeService.getEmployeeListByNodeInfo(nodeId, mc);
		}else{
			allEmps = ztreeService.getEmployeeListByNodeId(nodeId);
		}
		
		//过虑非本部门人员
		if(isSkipUser.equals(1)){
			for (Employee e : allEmps) {
				if(e.getDepartmentGuid().equals(depId)){
					allEmpList.add(e);
				}
			}
		}else{
			allEmpList = allEmps;
		}
		
		
		JSONArray arr = new JSONArray();
		if(!allDepts.isEmpty()){
			for(Department dept:allDepts){
				if(dept.getIsSite() != null && dept.getIsSite().equals(1)){
					continue;
				}
				String deptId =  dept.getDepartmentGuid();
				JSONArray empArr = new JSONArray();
				if(!allEmpList.isEmpty()){
					for(Employee e:allEmpList){
						if(deptId.equals(e.getDepartmentGuid())){
							JSONObject empObj = new JSONObject();
							empObj.put("id", e.getEmployeeGuid());
							empObj.put("name", e.getEmployeeName());
							if(StringUtils.isNotBlank(bdUser) && bdUser.indexOf(e.getEmployeeGuid()) != -1){
								empObj.put("checked", "true");
							}else{
								empObj.put("checked", "false");
							}
							empArr.add(empObj);
						}
					}
				}
				if(empArr.size()>0){
					JSONObject obj = new JSONObject();
					
					obj.put("id", deptId);
					obj.put("name", dept.getDepartmentName());
					obj.put("empArr", empArr);
					arr.add(obj);
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(arr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 新人员树json接口
	 * @throws UnsupportedEncodingException 
	 */
	public void getTreeOfJson() throws Exception {
		String nodeId=getRequest().getParameter("nodeId");
		String mc=getRequest().getParameter("mc");
		String type = getRequest().getParameter("type");
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfNode wfNode =  null;
		String wfUId = "";
		String routeType="";
		if(type != null && type.equals("dept")){
		}else{
			/*if(CommonUtil.stringIsNULL(nodeId)){
				return;
			}*/
			wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
			WfMain wfMain = wfNode.getWfMain();
			wfUId = wfMain.getWfm_id();
			routeType=wfNode.getWfn_route_type();
		}

		//获取当前登录人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String depId = emp.getDepartmentGuid();
		Department depart = departmentService.findDepartmentById(depId);
		String dep_shortDn = depart.getDepartmentShortdn();
		List<Department> secDepList = null;
		if(dep_shortDn!=null && !dep_shortDn.equals("")){
			String[] depIds = dep_shortDn.split(",");
			if(depIds!=null && depIds.length>=4){
				String begin_dep = depIds[0]+","+depIds[1]+","+depIds[2]+","+depIds[3];
				secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
			}
		}
		 
		String groupId = "";
		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
		if (!("").equals(wfNode) && wfNode != null) {
			// 获取人员组的id
			groupId = wfNode.getWfn_staff();
			String ids = "";
			if(secDepList!=null){
				if(secDepList!=null && secDepList.size()>0){
					for(int i=0; i<secDepList.size(); i++){
						ids += "'"+secDepList.get(i).getDepartmentGuid()+"',";
					}
					if(ids!=null&& ids.length()>0){
						ids = ids.substring(0, ids.length()-1);
					}
				}
			}
			Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			boolean needBm = false;
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,employee.getEmployeeGuid());
			}else{
				String needBmWfUId =  SystemParamConfigUtil.getParamValueByParam("needBmWfUId");
				if(wfUId!=null && !wfUId.equals("")){
					if(needBmWfUId.contains(wfUId)){
						needBm = true;
					}else{
						ids = "";
					}
				}else{
					ids = "";
				}
				// 获取人员组的人员信息
				innerUserList = groupService.getListByInnerUserId(groupId,null,ids);
			}
			//判断是否为并行传阅式  且是需要限制部门的流程
			if(routeType!=null && "4".equals(routeType) && needBm){
				String bxcyuserids=SystemParamConfigUtil.getParamValueByParam("bxcyuserids");
				String superDepId = depart.getSuperiorGuid();
				String toAddUserIds = "";
				if(bxcyuserids!=null && !bxcyuserids.equals("")){
					String[] dep_userIds = bxcyuserids.split(";");
					for(int i=0; i<dep_userIds.length; i++){
						String dep_userId = dep_userIds[i];
						if(dep_userId!=null && !dep_userId.equals("")){
							String[] deps = dep_userId.split(":");
							if((superDepId!=null && superDepId.equals(deps[0])) || depId.equals(deps[0])){		//父部门或者本部门
								toAddUserIds = deps[1];
							}
						}
					}
				}
				if(toAddUserIds!=null && !toAddUserIds.equals("")){
					toAddUserIds="'"+toAddUserIds.replace(",", "','")+"'";
					List<InnerUserMapEmployee>  list = groupService.getInnerUserListByIds(toAddUserIds);
					if(innerUserList!=null && innerUserList.size()>0){
						for(int i=0; i<list.size(); i++){
							String emp_userId = list.get(i).getEmployee_id();
								int count = 0;
								for(int j=0; j<innerUserList.size(); j++){
									if(innerUserList.get(j).getEmployee_id().equals(emp_userId)){
										break;
									}else{
										count ++;
									}
								}
								if(count==innerUserList.size()){
									innerUserList.add(list.get(i));
								}
						}
					}else{
						innerUserList = list;
					}
				}
			}
		}
		getResponse().setCharacterEncoding("utf-8");
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		String deptsid ="";
		String userids = null;
		int empSize = 0;
		if (innerUserList != null) {
			for (int i = 0; i < innerUserList.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUserList
						.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1){
					if (userids != null) {
						userids += ("," + innerUserMapEmployee.getEmployee_id());
						empSize++;
					} else {
						userids = innerUserMapEmployee.getEmployee_id();
					}
				}
			}
		}
		if(userids==null&&"".equals(type)){
			return;
		}
		if(userids!=null&&!"".equals(userids)){
			deptsid =employeeService.getDeptByEmployeeIds(userids);
		}
		if(deptsid==null&&"".equals(type)){
			return;
		}

		boolean isAsync = false;//是否异步加载树
		if(empSize>10){//人员超过10个就异步加载人员树
			isAsync = true;
		}
		
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			//顶级部门
			JSONObject jo = new JSONObject();
			jo.put("id", d_root.getDepartmentGuid());
			jo.put("pId", null);
			jo.put("name", rootString);
			jo.put("open", true);
			jo.put("type", "folder");
			jo.put("isParent", true);
			jo.put("chkDisabled", false);
			jsonTree.add(jo);
			// 生成JSON字符串
			List<Department> departments = new ArrayList<Department>();
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);//树包含人员的上级部门
			if (departments != null && departments.size() > 0) {
				departments = DepSortUtil.sortDepartment(departments);
				createDeptJsonTree(departments, d_root.getDepartmentGuid(), jsonTree, deptsid, userids,isAsync);
			}
			List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids);
			// 得到的人員Set不为空时，才生成字符串
			if (es != null && es.size() > 0) {
				createEmpJsonTree(es, d_root.getDepartmentGuid(), jsonTree);
			}
		} else {
			String department_id = root.toString();
			Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
			List<Department> departments = null;
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);//树包含人员的上级部门
			if (departments != null && departments.size() > 0) {
				departments = DepSortUtil.sortDepartment(departments);
				createDeptJsonTree(departments, d_root.getDepartmentGuid(), jsonTree, deptsid, userids,isAsync);
			}
			List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(d_root.getDepartmentGuid(),userids);
			// 得到的人員Set不为空时，才生成字符串
			if (es != null && es.size() > 0) {
				createEmpJsonTree(es, d_root.getDepartmentGuid(), jsonTree);
			}
		}
		
		
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
		
	}
	
	/**
	 * 通过递归查询的方式，一条sql语句查询出全部的部门和人员
	 * 提高人员树查询性能
	 * @throws UnsupportedEncodingException 
	 */
	public void getEmpsOfNodeJsonTree() throws UnsupportedEncodingException{
		String nodeId=getRequest().getParameter("nodeId");
		String defUserId = getRequest().getParameter("defUserId");
		String mc=getRequest().getParameter("mc");
		String type = getRequest().getParameter("type");
		String userId = getRequest().getParameter("userId");
		String instanceId = getRequest().getParameter("instanceId");
		
		String checkedUserIds = getRequest().getParameter("checkedUserIds");
		
		String isDuBan = getRequest().getParameter("isDuBan");
		
		String pleaseOrWatch = getRequest().getParameter("pleaseOrWatch");
		
		//获取当前登录人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfNode wfNode =  null;
		String wfUId = "";
		String routeType="";
		String bdUser = "";
		Integer isSkipUser = 0;
		if(type != null && type.equals("dept")){
		}else{
			/*if(CommonUtil.stringIsNULL(nodeId)){
				return;
			}*/
			wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
			WfMain wfMain = wfNode.getWfMain();
			wfUId = wfMain.getWfm_id();
			routeType=wfNode.getWfn_route_type();
			bdUser = wfNode.getWfn_bd_user();
			isSkipUser = wfNode.getWfn_skipUser();
			if(isSkipUser==null){
				isSkipUser=0;
			}
			
			if(StringUtils.isBlank(bdUser)){
				// 自动追溯
				List<WfBackNode> wfbList = workflowBasicFlowService.getBackNodeListByWfId(wfUId, nodeId);
				if (wfbList != null && wfbList.size() != 0) {
					if ((nodeId).equals(wfbList.get(0).getFromNodeId())) {
						List<WfProcess> desPersons = tableInfoService.findProcesses(wfUId, instanceId,wfbList.get(0).getToNodeId());
						if (desPersons.size() != 0 && desPersons != null && !("").equals(desPersons)) {
							for (WfProcess wfProcess : desPersons) {
								if (wfProcess.getIsMaster() == 1) {
									String conditionSql = "";
									String isdefdep = wfNode.getWfn_isdefdep();
									String empType = wfNode.getWfn_empType(); 		
									if(isdefdep!=null&&"1".equals(isdefdep)&&empType!=null && empType.equals("处长")){	
										conditionSql += " and t.emptype ='处长' ";
										//获取人员的id
										DepartmentLeader departmentLeader =employeeLeaderService.findChuzhangByEmpId(wfProcess.getUserUid(),conditionSql);
										if(departmentLeader!=null){
											bdUser = departmentLeader.getLeaderId();		//领导人Id
										}
									}
								}
							}
						}
					}
				}
				if(CommonUtil.stringIsNULL(bdUser)){
					String isdefdep = wfNode.getWfn_isdefdep(); 		
					if(isdefdep!=null && isdefdep.equals("1")){		//默认部门
						//获取人员的id
						DepartmentLeader departmentLeader =employeeLeaderService.findDepartmentLeaderByEmpId(emp.getEmployeeGuid());
						if(departmentLeader!=null){
							bdUser = departmentLeader.getLeaderId();		//领导人Id
						}
					}
				}
				
			}
		}
		
		if(CommonUtil.stringIsNULL(bdUser) && CommonUtil.stringNotNULL(defUserId) && defUserId.equals("nullUserId")){
			bdUser = emp.getEmployeeGuid();
		}
		
		String depId = emp.getDepartmentGuid();
		Department depart = departmentService.findDepartmentById(depId);
		String dep_shortDn = depart.getDepartmentShortdn();
		List<Department> secDepList = null;
		if(dep_shortDn!=null && !dep_shortDn.equals("")){
			String[] depIds = dep_shortDn.split(",");
			if(depIds!=null && depIds.length>=4){
				String begin_dep = depIds[0]+","+depIds[1]+","+depIds[2]+","+depIds[3];
				secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
			}
		}
		 
		String groupId = "";
		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
		List<InnerUserMapEmployee> innerUsers = new ArrayList<InnerUserMapEmployee>();
		if (!("").equals(wfNode) && wfNode != null) {
			// 获取人员组的id
			groupId = wfNode.getWfn_staff();
			String ids = "";
			if(secDepList!=null){
				if(secDepList!=null && secDepList.size()>0){
					for(int i=0; i<secDepList.size(); i++){
						ids += "'"+secDepList.get(i).getDepartmentGuid()+"',";
					}
					if(ids!=null&& ids.length()>0){
						ids = ids.substring(0, ids.length()-1);
					}
				}
			}
			boolean needBm = false;
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,emp.getEmployeeGuid());
			}else{
				String needBmWfUId =  SystemParamConfigUtil.getParamValueByParam("needBmWfUId");
				if(wfUId!=null && !wfUId.equals("")){
					if(needBmWfUId!=null && needBmWfUId.contains(wfUId)){
						needBm = true;
					}else{
						ids = "";
					}
				}else{
					ids = "";
				}
				// 获取人员组的人员信息
				innerUserList = groupService.getListByInnerUserId(groupId,null,ids);
			}
			
			//过虑非本部门人员
			if(isSkipUser.equals(1)){
				for (InnerUserMapEmployee iume : innerUserList) {
					if(iume.getDepartment_id().equals(depId)){
						innerUsers.add(iume);
					}
				}
			}else{
				innerUsers = innerUserList;
			}
			
			//判断是否为并行传阅式  且是需要限制部门的流程
			if(routeType!=null && "4".equals(routeType) && needBm){
				String bxcyuserids=SystemParamConfigUtil.getParamValueByParam("bxcyuserids");
				String superDepId = depart.getSuperiorGuid();
				String toAddUserIds = "";
				if(bxcyuserids!=null && !bxcyuserids.equals("")){
					String[] dep_userIds = bxcyuserids.split(";");
					for(int i=0; i<dep_userIds.length; i++){
						String dep_userId = dep_userIds[i];
						if(dep_userId!=null && !dep_userId.equals("")){
							String[] deps = dep_userId.split(":");
							if((superDepId!=null && superDepId.equals(deps[0])) || depId.equals(deps[0])){		//父部门或者本部门
								toAddUserIds = deps[1];
							}
						}
					}
				}
				if(toAddUserIds!=null && !toAddUserIds.equals("")){
					toAddUserIds="'"+toAddUserIds.replace(",", "','")+"'";
					List<InnerUserMapEmployee>  list = groupService.getInnerUserListByIds(toAddUserIds);
					if(innerUsers!=null && innerUsers.size()>0){
						for(int i=0; i<list.size(); i++){
							String emp_userId = list.get(i).getEmployee_id();
								int count = 0;
								for(int j=0; j<innerUsers.size(); j++){
									if(innerUsers.get(j).getEmployee_id().equals(emp_userId)){
										break;
									}else{
										count ++;
									}
								}
								if(count==innerUsers.size()){
									innerUsers.add(list.get(i));
								}
						}
					}else{
						innerUsers = list;
					}
				}
			}
		}
		List<Employee> empsByMc = employeeService.findEmployeesByMc(mc);
		String str4EmpsByMc = "";
		for(Employee e:empsByMc){
			str4EmpsByMc += e.getEmployeeGuid();
		}
		getResponse().setCharacterEncoding("utf-8");
		String deptsid ="";
		String userids = "";
		if (innerUsers != null) {
			for (int i = 0; i < innerUsers.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUsers.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1||str4EmpsByMc.indexOf(innerUserMapEmployee.getEmployee_id())!=-1){
					if (userids != null) {
						userids += ("," + innerUserMapEmployee.getEmployee_id());
					} else {
						userids = innerUserMapEmployee.getEmployee_id();
					}
					
					if(innerUserMapEmployee.getDepartment_id()==null || innerUserMapEmployee.getDepartment_id().equals("")){
						deptsid += innerUserMapEmployee.getEmployee_id()+",";
					}else{
						deptsid += innerUserMapEmployee.getDepartment_id()+",";
					}
				}
			}
		}
		if(userids==null&&"".equals(type)){
			return;
		}
		/*if(userids!=null&&!"".equals(userids)){
			deptsid =employeeService.getDeptByEmployeeIds(userids);
		}
		if(deptsid==null&&"".equals(type)){
			return;
		}*/
		
		if(deptsid!=null && !deptsid.equals("")){
			deptsid = deptsid.substring(0, deptsid.length()-1);
		}
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(deptsid);
		List<Employee> allEmpList = new ArrayList<Employee>();
		List<Employee> allEmps = new ArrayList<Employee>();
		if(mc!=null && !mc.equals("")){
			allEmps = ztreeService.getEmployeeListByNodeInfo(nodeId, mc);
		}else{
			allEmps = ztreeService.getEmployeeListByNodeId(nodeId);
		}
		
		//过虑非本部门人员
		if(isSkipUser.equals(1)){
			for (Employee e : allEmps) {
				if(e.getDepartmentGuid().equals(depId)){
					allEmpList.add(e);
				}
			}
		}else{
			allEmpList = allEmps;
		}
		
		String depRootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		String allNextDepid = SystemParamConfigUtil.getParamValueByParam("all_next_dep_id");
		JSONArray tempArr = new JSONArray();
		// 生成JSON字符串
		String ids = "";
		if(!allDepts.isEmpty()){
			for(Department dept:allDepts){
				if(dept!=null && !dept.getDepartmentGuid().equals(depRootId)){
					if(allNextDepid.indexOf(dept.getDepartmentGuid()) == -1){
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("pId", dept.getSuperiorGuid());
						jo.put("name", dept.getDepartmentName());
						jo.put("open", false);
						jo.put("type", "folder");
						jo.put("isParent", true);
						jo.put("chkDisabled", false);
						jo.put("deptsort", dept.getTabindex());
						ids += dept.getDepartmentGuid()+",";
						tempArr.add(jo);
					}
				}
			}
			if(StringUtils.isNotBlank(ids)){
				ids = ids.substring(0, ids.length()-1);
			}
		}
		
		for (Object tempObj : tempArr) {
			JSONObject empObj = JSONObject.fromObject(tempObj);
			String pId = empObj.get("pId") != null ? empObj.getString("pId"):"";
			if(StringUtils.isNotBlank(pId)){
				if(ids.indexOf(pId) == -1){
					empObj.put("pId", "0");
				}
			}
			jsonTree.add(empObj);
		}
		
		long moreSort = 1;
		String leaderIds = SystemParamConfigUtil.getParamValueByParam("pw_leader_id");
		String directorIds = SystemParamConfigUtil.getParamValueByParam("directorIds");
		if(!allEmpList.isEmpty()){
			for(Employee e:allEmpList){
				if(e!=null){
					JSONObject jo = new JSONObject();
					Object obj = RemoteLogin.map.get(e.getEmployeeGuid());
					if(obj!=null){//在线
						jo.put("online", true);
					}else{//不在线
						jo.put("online", false);
					}
					jo.put("id", e.getEmployeeGuid());
					jo.put("pId", e.getDepartmentGuid());
					jo.put("name", e.getEmployeeName());
					jo.put("type", "file");
					jo.put("empsort",String.valueOf(moreSort++));
					if(leaderIds.contains(e.getEmployeeGuid())){
						jo.put("isLeader", "true");
					}else{
						jo.put("isLeader", "false");
					}
					if(directorIds!=null && directorIds.contains(e.getEmployeeGuid())){
						jo.put("isPrincipal", "true");
					}else{
						jo.put("isPrincipal", "false");
					}
					if(StringUtils.isNotBlank(checkedUserIds) && checkedUserIds.indexOf(e.getEmployeeGuid()) != -1){
						jo.put("checked", "true");
					}else{
						if(StringUtils.isNotBlank(bdUser) && bdUser.indexOf(e.getEmployeeGuid()) != -1){
							if((isDuBan!=null && isDuBan.equals("yes")) || (pleaseOrWatch!=null&&pleaseOrWatch.equals("true"))){
								jo.put("checked", "false");
							}else{
								jo.put("checked", "true");
							}
						}else{
							jo.put("checked", "false");
						}
					}
					jsonTree.add(jo);
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 通过递归查询的方式，一条sql语句查询出全部的部门和人员
	 * 提高人员树查询性能
	 * @throws UnsupportedEncodingException 
	 */
	public void getFullJsonTree() throws UnsupportedEncodingException{
		String allNextDepid = SystemParamConfigUtil.getParamValueByParam("all_next_dep_id");
		String isZf = this.getRequest().getParameter("isZf");
		String isowner = this.getRequest().getParameter("isowner");
		String userId = this.getRequest().getParameter("userId");
		String allEmp = this.getRequest().getParameter("allEmp");
		String isDeptTree = this.getRequest().getParameter("isDeptTree");
		String searchSiteName = this.getRequest().getParameter("searchSiteName");
		String siteId = this.getRequest().getParameter("siteId");
		
		String checkedUserIds = getRequest().getParameter("checkedUserIds");
		
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		String employeDn = emp.getEmployeeDn();
		String [] employeDns = employeDn.split(",");
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = null;
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		String depRootId = "";
		//if(CommonUtil.stringIsNULL(siteId)){
			depRootId=SystemParamConfigUtil.getParamValueByParam("root_department_id");
	//	}else{
		//	depRootId=siteId;
	//	}
		//查询部门树有条件时
		if(mc!=null&&!"".equals(mc)){
			if("1".equals(isDeptTree)){
				List<Department> allDepts = ztreeService.getAllParentDeptByMC(mc);
				JSONArray tempArr = new JSONArray();
				String ids = "";
				if(!allDepts.isEmpty()){
					boolean isParent = true;
					if("1".equals(isDeptTree)){
						isParent = false;
					}
					for(Department dept:allDepts){
						if(dept!=null && !dept.getDepartmentGuid().equals(depRootId) ){
							if(allNextDepid.indexOf(dept.getDepartmentGuid()) == -1){
								JSONObject jo = new JSONObject();
								jo.put("id", dept.getDepartmentGuid());
								jo.put("pId", dept.getSuperiorGuid());
								jo.put("name", dept.getDepartmentName());
								jo.put("open", false);
								jo.put("type", "folder");
								jo.put("isParent", isParent);
								jo.put("chkDisabled", false);
								ids += dept.getDepartmentGuid()+",";
								tempArr.add(jo);
							}
						}
					}
					if(StringUtils.isNotBlank(ids)){
						ids = ids.substring(0, ids.length()-1);
					}
				}
				
				for (Object tempObj : tempArr) {
					JSONObject empObj = JSONObject.fromObject(tempObj);
					String pId = empObj.get("pId") != null ? empObj.getString("pId"):"";
					if(StringUtils.isNotBlank(pId)){
						if(ids.indexOf(pId) == -1){
							empObj.put("pId", "0");
						}
					}
					jsonTree.add(empObj);
				}
			}else{
				List<Employee> list = employeeService.findEmployeesByMc(mc);
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						Employee employee = list.get(i);
						if(userids==null){
							userids = new StringBuffer();
							userids.append(employee.getEmployeeGuid());
						}else{
							userids.append(",").append(employee.getEmployeeGuid());
						}
					}
				}	
				String deptsid ="";
				if(userids!=null){
					deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
				}
				List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(deptsid);
				JSONArray tempArr = new JSONArray();
				String ids = "";
				if(userids==null){
					return;
				}
				List<Employee> allEmps = ztreeService.getAllEmpsByUserIds(userids.toString());
				if(!allDepts.isEmpty()){
					boolean isParent = true;
					if("1".equals(isDeptTree)){
						isParent = false;
					}
					for(Department dept:allDepts){
						if(dept!=null && !dept.getDepartmentGuid().equals(depRootId) ){
							if(allNextDepid.indexOf(dept.getDepartmentGuid()) == -1){
								JSONObject jo = new JSONObject();
								jo.put("id", dept.getDepartmentGuid());
								jo.put("pId", dept.getSuperiorGuid());
								jo.put("name", dept.getDepartmentName());
								jo.put("open", false);
								jo.put("type", "folder");
								jo.put("isParent", isParent);
								jo.put("chkDisabled", false);
								ids += dept.getDepartmentGuid()+",";
								tempArr.add(jo);
							}
						}
					}
					if(StringUtils.isNotBlank(ids)){
						ids = ids.substring(0, ids.length()-1);
					}
				}
				
				for (Object tempObj : tempArr) {
					JSONObject empObj = JSONObject.fromObject(tempObj);
					String pId = empObj.get("pId") != null ? empObj.getString("pId"):"";
					if(StringUtils.isNotBlank(pId)){
						if(ids.indexOf(pId) == -1){
							empObj.put("pId", "0");
						}
					}
					jsonTree.add(empObj);
				}
				if(!"1".equals(isDeptTree)){
					if(!allEmps.isEmpty()){
						for(Employee e:allEmps){
							if(e!=null){
								if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && e.getEmployeeGuid().equals(emp.getEmployeeGuid())){
									continue;
								}
								JSONObject jo = new JSONObject();
								jo.put("id", e.getEmployeeGuid());
								jo.put("pId", e.getDepartmentGuid());
								jo.put("name", e.getEmployeeName());
								jo.put("type", "file");
								jsonTree.add(jo);
							}
						}
					}
				}
			}
			
			
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			if(isowner!=null && isowner.equals("1")){
				department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
				allDepts = ztreeService.getAllChildDeptBydeptId(department_rootId);
				allEmps = ztreeService.getAllChildEmpsByDeptId(department_rootId);
			}else{
				Collection<Department> list = departmentService.findThirdDepartments(depRootId);
				if("1".equals(allEmp)){
					//department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
					if(StringUtils.isNotBlank(searchSiteName)){
						department_rootId = searchSiteName;
					}else{
						String deptIds = (String) getSession().getAttribute(MyConstants.DEPARMENT_ID);
						if(StringUtils.isNotBlank(deptIds)){
							Department dept = departmentService.findSiteDept(deptIds);
							if(null != dept){
								department_rootId = dept.getDepartmentGuid();
							}
						}
					}
					
				}else{
					for (Department department : list) {
						String depHierarchy =department.getDepartmentHierarchy();
						String [] depHierarchys = depHierarchy.split(",");
						if(depHierarchys.length>2 && employeDns[employeDns.length-3].equals(depHierarchys[depHierarchys.length-3])){
							department_rootId = department.getDepartmentGuid();
							break;
						}else{
							if(StringUtils.isNotBlank(siteId)){
								department_rootId = siteId;
							}else{
								department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
							}
							//department_rootId = depRootId;
						}
					}
				}
				allDepts = ztreeService.getAllChildDeptBydeptId(department_rootId);
				allEmps = ztreeService.getAllChildEmpsByDeptId(department_rootId);
			}
			JSONArray tempArr = new JSONArray();
			String ids = "";
			
			if(!allDepts.isEmpty()){
				
				if("1".equals(isDeptTree)){
					for(Department dept:allDepts){
						if(dept!=null && !dept.getDepartmentGuid().equals(depRootId)){
							if(allNextDepid.indexOf(dept.getDepartmentGuid()) == -1){
								String shortDn = dept.getDepartmentShortdn();
								boolean isParent = true;
								String treeNodeType = "folder";
								if(CommonUtil.stringNotNULL(shortDn)){
									String[] shortDns = shortDn.split(",");
									if(shortDns != null && shortDns.length > 1){
										String lastDn = shortDns[shortDns.length - 1];
										if(CommonUtil.stringNotNULL(lastDn) &&lastDn.equals(dept.getDepartmentName())){
											isParent = false;
											treeNodeType = "file";
										}
									}
									
								}
								JSONObject jo = new JSONObject();
								jo.put("id", dept.getDepartmentGuid());
								jo.put("pId", dept.getSuperiorGuid());
								jo.put("name", dept.getDepartmentName());
								jo.put("open", true);
								jo.put("type", treeNodeType);
								jo.put("isParent", isParent);
								jo.put("chkDisabled", false);
								ids += dept.getDepartmentGuid()+",";
								tempArr.add(jo);
							}
						}
					}
				}else{
					for(Department dept:allDepts){
						if(dept!=null && !dept.getDepartmentGuid().equals(depRootId)){
							if(allNextDepid.indexOf(dept.getDepartmentGuid()) == -1){
								JSONObject jo = new JSONObject();
								jo.put("id", dept.getDepartmentGuid());
								jo.put("pId", dept.getSuperiorGuid());
								jo.put("name", dept.getDepartmentName());
								jo.put("open", true);
								jo.put("type", "folder");
								jo.put("isParent", true);
								jo.put("chkDisabled", false);
								ids += dept.getDepartmentGuid()+",";
								tempArr.add(jo);
							}
						}
					}
				}
				if(StringUtils.isNotBlank(ids)){
					ids = ids.substring(0, ids.length()-1);
				}
			}
			
			for (Object tempObj : tempArr) {
				JSONObject empObj = JSONObject.fromObject(tempObj);
				String pId = empObj.get("pId") != null ? empObj.getString("pId"):"";
				if(StringUtils.isNotBlank(pId)){
					if(ids.indexOf(pId) == -1){
						empObj.put("pId", "0");
					}
				}
				jsonTree.add(empObj);
			}
			if(!"1".equals(isDeptTree)){
				if(!allEmps.isEmpty()){
					for(Employee e:allEmps){
						if(e!=null){
							if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && e.getEmployeeGuid().equals(emp.getEmployeeGuid())){
								continue;
							}
							JSONObject jo = new JSONObject();
							jo.put("id", e.getEmployeeGuid());
							jo.put("pId", e.getDepartmentGuid());
							jo.put("name", e.getEmployeeName());
							jo.put("type", "file");
							if(StringUtils.isNotBlank(checkedUserIds) && checkedUserIds.indexOf(e.getEmployeeGuid()) != -1){
								jo.put("checked", "true");
							}else{
								jo.put("checked", "false");
							}
							jsonTree.add(jo);
						}
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	private void createDeptJsonTree(List<Department> departments, String pId, JSONArray jsonTree, String deptsid,String userids,boolean isAsync){
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			department = departments.get(i);
			JSONObject deptJo = new JSONObject();
			deptJo.put("id", department.getDepartmentGuid());
			deptJo.put("pId", pId);
			deptJo.put("name", department.getDepartmentName());
			if(!isAsync){
				deptJo.put("open", true);
			}else{
				deptJo.put("open", false);
			}
			
			deptJo.put("type", "folder");
			List<Department> ds = (List<Department>) departmentService.queryDepartmentsBydepIds1(department,deptsid);
			List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department.getDepartmentGuid(),userids);
			if(es.isEmpty()&&ds.isEmpty()){
				deptJo.put("isParent", false);
				deptJo.put("chkDisabled", true);
			}else{
				deptJo.put("isParent", true);
				deptJo.put("chkDisabled", false);
			}
			jsonTree.add(deptJo);
			if(!isAsync){
				if(!ds.isEmpty()){
					createDeptJsonTree(ds, department.getDepartmentGuid(), jsonTree, deptsid,userids,isAsync);
				}
				if(!es.isEmpty()){
					createEmpJsonTree(es,department.getDepartmentGuid(),jsonTree);
				}
			}
		}
	}
	
	private void createEmpJsonTree(List<Employee> es, String pId, JSONArray jsonTree){
		Employee e;
		for (int i = 0; i < es.size(); i++) {
			e = es.get(i);
			JSONObject empJo = new JSONObject();
			empJo.put("id", e.getEmployeeGuid());
			empJo.put("pId", pId);
			empJo.put("name", e.getEmployeeName());
			empJo.put("type", "file");
			jsonTree.add(empJo);
		}
	}
	
	public void getAllEmpsByDeptId(){
		List<Object[]> list=employeeService.getAllEmployeeInfoBySuperDepartmentId(root.toString());
		JSONArray jsonTree = new JSONArray();
		//createEmpJsonTree(es, department_id, jsonTree);
		for (int i = 0; i < list.size(); i++) {
			Object[] o = list.get(i);
			JSONObject empJo = new JSONObject();
			empJo.put("id", o[0]);
			empJo.put("pId", o[2]);
			empJo.put("name", o[1]);
			empJo.put("type", "file");
			jsonTree.add(empJo);
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	
	/**
	 * 跳转到常用人员组设置界面
	 * @return
	 */
	public String toSetUserGroup(){
		String siteId = getRequest().getParameter("siteId");
		String isRole = getRequest().getParameter("isRole");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		List<CommonGroup> cgs = null;
		if(StringUtils.isNotBlank(isRole) && isRole.equals("1")){
			cgs = ztreeService.findAllCommonGroupByUid("", siteId);
		}else{
			cgs = ztreeService.findAllCommonGroupByUid(emp.getEmployeeGuid(), siteId);
		}
		if(null != cgs && cgs.size()>0){
			getRequest().setAttribute("groupId", cgs.get(0).getId());
		}
		getRequest().setAttribute("cgs", cgs);
		getRequest().setAttribute("nodeId", getRequest().getParameter("nodeId"));
		getRequest().setAttribute("type", getRequest().getParameter("type"));
		getRequest().setAttribute("siteId", siteId);
		getRequest().setAttribute("isRole", isRole);
		return "setUserGroup";
	}
	
	/**
	 * 跳转到领导设置界面
	 * @return
	 */
	public String toSetLeader(){
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String siteId = emp.getSiteId();
		/*List<CommonGroup> cgs = null;
		if(StringUtils.isNotBlank(isRole) && isRole.equals("1")){
			cgs = ztreeService.findAllCommonGroupByUid("", siteId);
		}else{
			cgs = ztreeService.findAllCommonGroupByUid(emp.getEmployeeGuid(), siteId);
		}*/
		/*if(null != cgs && cgs.size()>0){
			getRequest().setAttribute("groupId", cgs.get(0).getId());
		}*/
		getRequest().setAttribute("siteId", siteId);
		return "setLeader";
	}
	
	/**
	 * 删除常用人员组
	 */
	public void deleteUserGroup(){
		String gid = this.getRequest().getParameter("gid");
		String [] gids = gid.split(",");
		for (String string : gids) {
			ztreeService.deleteCommonGroup(ztreeService.findCommonGroupById(string));
		}
	}
	/**
	 * 新增常用人员组
	 */
	public void addUserGroup(){
		String userId = this.getRequest().getParameter("userId");
		if(CommonUtil.stringIsNULL(userId)){
			Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
			userId = emp.getEmployeeGuid();
		}
		String name = getRequest().getParameter("name");
		String siteId = getRequest().getParameter("siteId");
		String deptFlag = getRequest().getParameter("deptFlag");
		String isCommonUse = getRequest().getParameter("isCommonUse");
		String ret="";
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(siteId)){
			CommonGroup cg = new CommonGroup();
			cg.setName(name);
			cg.setCreateTime(new Date());
			if(StringUtils.isBlank(isCommonUse)){
				cg.setBelongTo(userId);
			}
			cg.setDeptFlag(deptFlag);
			cg.setSiteId(siteId);
			cg =  ztreeService.saveCommonGroup(cg);
			ret = cg.getId();
		}else{
			ret = "nameNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 修改常用人员组名称
	 */
	public void modifyUserGroup(){
		String name = this.getRequest().getParameter("name");
		String groupId = this.getRequest().getParameter("groupId");
		String deptFlag = this.getRequest().getParameter("deptFlag");
		String ret="";
		if(CommonUtil.stringNotNULL(name)){
			CommonGroup cg = ztreeService.findCommonGroupById(groupId);
			cg.setName(name);
			cg.setCreateTime(new Date());
			cg.setDeptFlag(deptFlag);
			ztreeService.updateCommonGroup(cg);
			ret = "ok";
		}else{
			ret = "nameNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 为常用人员组添加人员，常用人员组
	 */
	public void addGroupUsers(){
		String groupId = this.getRequest().getParameter("groupId");
		String persons = this.getRequest().getParameter("persons");
		JSONArray ja = JSONArray.fromObject(persons);
		String ret="";
		if(!ja.isEmpty()){
			ztreeService.deleteCommonGroupUsersByGid(groupId);
			for(int i=0;i<ja.size();i++){
				JSONObject jo = (JSONObject) ja.get(i);
				CommonGroupUsers cgu = new CommonGroupUsers();
				cgu.setEmpId((String) jo.get("id"));
				cgu.setEmpName((String) jo.get("name"));
				cgu.setGid(groupId);
				ztreeService.saveCommonGroupUsers(cgu);
			}
			ret = "ok";
		}else{
			ret = "personsNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 添加站点领导
	 */
	public void addLeaders(){
		String siteId = this.getRequest().getParameter("siteId");
		String persons = this.getRequest().getParameter("persons");
		JSONArray ja = JSONArray.fromObject(persons);
		String ret="";
		/*if(!ja.isEmpty()){*/
			ztreeService.deleteLeaderBySiteId(siteId);
			for(int i=0;i<ja.size();i++){
				JSONObject jo = (JSONObject) ja.get(i);
				Leader leader = new Leader();
				leader.setEmployeeGuid((String) jo.get("id"));
				leader.setEmployeeName((String) jo.get("name"));
				leader.setSiteId(siteId);
				leader.setCreateTime(new Date());
				ztreeService.saveLeaders(leader);
			}
			ret = "ok";
		/*}else{
			ret = "ok";
		}*/
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取常用人员组下的人员
	 */
	public void getGroupUsers(){
		String groupId = this.getRequest().getParameter("groupId");
		String ret="";
		List<CommonGroupUsers> list = new ArrayList<CommonGroupUsers>();
		if(CommonUtil.stringNotNULL(groupId)){
			list = ztreeService.findAllCommonGroupUsersByGid(groupId);
		}else{
			ret = "personsNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			if(list.isEmpty()){
				out.print(ret);
			}else{
				out.print(JSONArray.fromObject(list).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取常用人员组下的人员
	 */
	public void getLeaders(){
		String siteId = this.getRequest().getParameter("siteId");
		String ret="";
		List<Leader> list = new ArrayList<Leader>();
		if(CommonUtil.stringNotNULL(siteId)){
			list = ztreeService.findAllLeadersBySiteId(siteId);
		}else{
			ret = "personsNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			if(list.isEmpty()){
				out.print(ret);
			}else{
				out.print(JSONArray.fromObject(list).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void getUserGroups(){
		
		String nodeId=getRequest().getParameter("nodeId");
		String type = getRequest().getParameter("type");
		String userId = getRequest().getParameter("userId");
		String isZf = getRequest().getParameter("isZf");
		String isRole = getRequest().getParameter("isRole");
		String siteId = getRequest().getParameter("siteId");
		WfNode wfNode =  null;
		String wfUId = "";
		if(type != null && type.equals("dept")){
		}else{
			/*if(CommonUtil.stringIsNULL(nodeId)){
				return;
			}*/
			if(StringUtils.isNotBlank(nodeId)){
				wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
				WfMain wfMain = wfNode.getWfMain();
				wfUId = wfMain.getWfm_id();
			}
		}

		//获取当前登录人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		List<Department> secDepList = null;
		if(emp != null){
			String depId = emp.getDepartmentGuid();
			Department depart = departmentService.findDepartmentById(depId);
			String dep_shortDn = depart.getDepartmentShortdn();
			if(dep_shortDn!=null && !dep_shortDn.equals("")){
				String[] depIds = dep_shortDn.split(",");
				if(depIds!=null && depIds.length>=4){
					String begin_dep = depIds[0]+","+depIds[1]+","+depIds[2]+","+depIds[3];
					secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
				}
			}
		}
		 
		String groupId = "";
		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
		if (!("").equals(wfNode) && wfNode != null) {
			// 获取人员组的id
			groupId = wfNode.getWfn_staff();
			String ids = "";
			if(secDepList!=null){
				if(secDepList!=null && secDepList.size()>0){
					for(int i=0; i<secDepList.size(); i++){
						ids += "'"+secDepList.get(i).getDepartmentGuid()+"',";
					}
					if(ids!=null&& ids.length()>0){
						ids = ids.substring(0, ids.length()-1);
					}
				}
			}
			Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,employee.getEmployeeGuid());
			}else{
				String needBmWfUId =  SystemParamConfigUtil.getParamValueByParam("needBmWfUId");
				if(wfUId!=null && !wfUId.equals("")){
					if(needBmWfUId!=null && needBmWfUId.contains(wfUId)){
					}else{
						ids = "";
					}
				}else{
					ids = "";
				}
				// 获取人员组的人员信息
				innerUserList = groupService.getListByInnerUserId(groupId,null,ids);
			}
		}
		String str4Emps = "";
		for(InnerUserMapEmployee e:innerUserList){
			str4Emps += e.getEmployee_id();
		}
		
		
		getResponse().setCharacterEncoding("utf-8");
		JSONArray jsonTree = new JSONArray();
		
		List<CommonGroup> groupList = new ArrayList<CommonGroup>();
		if(StringUtils.isNotBlank(isRole) && isRole.equals("1")){
			//公共常用组
			Map<String, String> map = new HashMap<String, String>();
			map.put("isCommonUse", "yes");
			map.put("bigDepId", siteId);
			groupList = ztreeService.getCommonUseGroupList(map);
		}else{
			//个人常用组
			groupList = ztreeService.findAllCommonGroupByUid(emp.getEmployeeGuid(),siteId);
		}
		// 生成JSON字符串
		//List<CommonGroup> groupList = ztreeService.findAllCommonGroupByUid(emp.getEmployeeGuid());
		for(int j = 0; j < groupList.size(); j++){
			CommonGroup group = groupList.get(j);
			List<CommonGroupUsers> userList = ztreeService.findAllCommonGroupUsersByGid(group.getId());
			if(StringUtils.isNotBlank(nodeId)){
				Iterator<CommonGroupUsers> ite = userList.iterator();
				while(ite.hasNext()){ 
					if(str4Emps.indexOf(ite.next().getEmpId())<0){
						ite.remove();
					}
				}
			}
			JSONObject jo = new JSONObject();
			jo.put("id", group.getId());
			jo.put("pId", "0");
			jo.put("name", group.getName());
			jo.put("open", false);
			jo.put("type", "folder");
			if(userList.isEmpty()){
				jo.put("chkDisabled", true);
			}else{
				jo.put("chkDisabled", false);
			}
			
			jsonTree.add(jo);
			if(!userList.isEmpty()){
				for (int i = 0; i < userList.size(); i++) {
					if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && userList.get(i).getEmpId().equals(emp.getEmployeeGuid())){
						continue;
					}
					JSONObject empJo = new JSONObject();
					Object obj = RemoteLogin.map.get(userList.get(i).getEmpId());
					if(obj!=null){//在线
						empJo.put("online", true);
					}else{//不在线
						empJo.put("online", false);
					}
					empJo.put("id", userList.get(i).getEmpId());
					empJo.put("pId", group.getId());
					empJo.put("name", userList.get(i).getEmpName());
					empJo.put("type", "file");
					jsonTree.add(empJo);
				}
			}
		}
		// 打开流
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void initSpellNew(){
		List<Employee>  list = null;
		String depId = getRequest().getParameter("depId");
		getRequest().setAttribute("depId", depId);
		if (StringUtils.isNotBlank(depId)) {
			Department dep = departmentService.findDepartmentById(depId);
			List<Department> depLists = departmentService.queryDepartmentListByDN(dep.getDepartmentHierarchy());
			String depIds = "(";
			for(int i=0;i<depLists.size();i++){
				if(i==0){
					depIds += "'"+depLists.get(i).getDepartmentGuid()+"'";
				}else{
					depIds += ",'"+depLists.get(i).getDepartmentGuid()+"'";
				}
			}
			depIds += ")";
			
			String name = getRequest().getParameter("name");
			getRequest().setAttribute("name", name);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("depIds", depIds);
			list = employeeService.findEmpList(map);
		}
		for(int i=0;i<list.size();i++){
			Employee emp = list.get(i);
			String empName = emp.getEmployeeName();
			
			List<String> pingyins = PinYin4jUtil.getPinYin(empName);
			
			List<EmployeeSpell> empSpellList = employeeSpellService
					.findEmployeeSpellByEmpGuid(emp.getEmployeeGuid());
			if (empSpellList.size() == 0) {
//				EmployeeSpell empSpell = new EmployeeSpell();
//				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
//				empSpell.setEmployeeName(emp.getEmployeeName());
//				empSpell.setSpell(spell);
//				empSpell.setSpellhead(spellhead);
//				employeeSpellService.saveEmployeeSpell(empSpell);
			}else{
//				EmployeeSpell empSpell = empSpellList.get(0);
//				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
//				empSpell.setEmployeeName(emp.getEmployeeName());
//				empSpell.setSpell(spell);
//				empSpell.setSpellhead(spellhead);
//				employeeSpellService.updateEmployeeSpell(empSpell);
				employeeSpellService.deleteByEmpId(emp.getEmployeeGuid());
			}
			
			for(int j=0;j<pingyins.size();j++){
				String pingyin = pingyins.get(j);
				String[] strs = pingyin.split(",");
				String spell = "";
				String spellHead = "";
				for(int a=0;a<strs.length;a++){
					spell += strs[a];
					spellHead += strs[a].charAt(0);
				}
				EmployeeSpell empSpell = new EmployeeSpell();
				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
				empSpell.setEmployeeName(emp.getEmployeeName());
				empSpell.setSpell(spell);
				empSpell.setSpellhead(spellHead);
				employeeSpellService.saveEmployeeSpell(empSpell);
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			getResponse().setCharacterEncoding("utf-8");
			pw.write("true");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取公共常用用户组列表
	 * @return
	 * xiep
	 * 2016-12-06 10:31
	 */
	public String getCommonUseGroupList(){
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("isCommonUse", "yes");
		int count=ztreeService.getCommonUseGroupCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex + "");
		map.put("pageSize", Paging.pageSize + "");
		List<CommonGroup> commonGrpList = ztreeService.getCommonUseGroupList(map);
		getRequest().setAttribute("commonGrpList", commonGrpList);
		return "getCommonUseGroupList";
	}
	
	/**
	 * 跳转到添加公共常用组页面
	 * @return
	 *  xiep
	 * 2016-12-06 10:31
	 */
	public String toAddCommonUseGroup(){
		return "toAddCommonUseGroup";
	}
	
	/**
	 * 添加公共常用组
	 * @return
	 *  xiep
	 * 2016-12-06 10:31
	 * 
	 */
	public void addCommonGroupUser(){
		String groupName = this.getRequest().getParameter("groupName");
		String orderIndex = this.getRequest().getParameter("orderIndex");
		String persons = this.getRequest().getParameter("persons");
		String groupId = this.getRequest().getParameter("groupId");
		String isJyGroup = this.getRequest().getParameter("isJyGroup");
		String ret="";
		if(CommonUtil.stringNotNULL(groupName)){
			Map<String, String> map = new HashMap<String, String>();
			map.put("groupName", groupName);
			if(CommonUtil.stringIsNULL(groupId)){
				//添加常用组
				List<CommonGroup> commonGrpList = ztreeService.getCommonUseGroupList(map);
				//判断是否有重名的常用组
				if(commonGrpList != null && commonGrpList.size() > 0){
					ret = "existedGroupName";
				}else{
					map.put("orderIndex", orderIndex);
					map.put("isJyGroup", isJyGroup);
					map.put("persons", persons);
					map.put("groupId", groupId);
					ret = ztreeService.addCommonGroupUser(map);
				}
			}else{
				//更新常用组
				map.put("orderIndex", orderIndex);
				map.put("isJyGroup", isJyGroup);
				map.put("persons", persons);
				map.put("groupId", groupId);
				ret = ztreeService.addCommonGroupUser(map);
			}
			
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 跳转到修改公共常用组页面
	 * @return
	 *  xiep
	 * 2016-12-06 16:31
	 */
	public String toUpdateCommonUseGroup(){
		String groupId = this.getRequest().getParameter("groupId");
		CommonGroup commonGroup = new CommonGroup();
		commonGroup = ztreeService.findCommonGroupById(groupId);
		String groupName = commonGroup.getName();
		String isJyGroup = commonGroup.getIsJyGroup();
		String orderIndex = commonGroup.getOrderIndex() == null ? "" : commonGroup.getOrderIndex().toString();
		getRequest().setAttribute("groupId", groupId);
		getRequest().setAttribute("groupName", groupName);
		getRequest().setAttribute("orderIndex", orderIndex);
		getRequest().setAttribute("isJyGroup", isJyGroup);
		return "toUpdateCommonUseGroup";
	}
	
	/**
	 * 删除公共常用组
	 * @return
	 * xiep
	 * 2016-12-06 18:31
	 */
	public String deleteCommonUseGroup(){
		String ids = this.getRequest().getParameter("ids");
		Map<String, String> map = new HashMap<String, String>();
		map.put("ids", ids);
		ztreeService.deleteCommonUseGroup(map);
		return getCommonUseGroupList();
	}
	
	public void ThirdChat(){
		String userId = getRequest().getParameter("userId");	//需要聊天的用户id
		String macAddr = getRequest().getParameter("macaddr");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String ownUserId = emp.getEmployeeGuid();			//当前人员的userId
		//检测当前用户是否在线,pc在线
		String result = "";
		Object obj = RemoteLogin.map.get(ownUserId);
		if(obj!=null){
			String  mac = (String)obj;		//获取mac地址;
			if(mac==null || mac.equals("")){
				result = "online_other";	
			}else{
				//比对前台的mac地址
				if(macAddr.contains(mac)){
					result = "online";
					RemoteLogin remote = new RemoteLogin(RemoteLogin.loginName);
					remote.toThirdChat(ownUserId, userId);
				}else{
					result = "online_other";	
				}
			}
		}else{
			result = "unOnline";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 
	 * @Description:移动端判断人员是否属于机要组 
	 * @author: xiep
	 * @time: 2017-8-19 下午2:48:21
	 */
	public void getUserFromCommGrpForMobile(){
		String userId = "";// 用户id
		String retVal = "exist";
		JSONObject jsonObject = getJSONObject();
		if(jsonObject != null){
			userId = (String) jsonObject.get("userId");
		}else{
			userId =  getRequest().getParameter("userId");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId",userId);
		List<CommonGroupUsers> comGrpUsersList = ztreeService.getComGrpUsersByParams(paramMap);
		if(comGrpUsersList != null && comGrpUsersList.size() > 0){
			retVal = "exist";
		}else{
			retVal = "notExist";
		}
		try {
			getResponse().getWriter().print("{\"status\":\""+retVal+"\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * 
	 * @Description: 
	 * @author: xiep
	 * @time: 2017-9-6 下午6:49:15
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getSecRootDeptId(Employee emp){
	    String trueDeptId = "";
	    if(emp != null){
			String deptId = emp.getDepartmentGuid();
			String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
			String superDeptId = "";
			if(CommonUtil.stringNotNULL(deptId) && CommonUtil.stringNotNULL(department_rootId)){
			    while(!department_rootId.equals(superDeptId)){
					Department dept = departmentService.findDepartmentById(deptId);
					if(dept != null){
					    superDeptId = dept.getSuperiorGuid();
					    deptId = superDeptId;
					    trueDeptId = trueDeptId + dept.getDepartmentGuid() + ",";
					}
			    }
			}
	    }
	    return trueDeptId; 
	}
	
	/**
	 * 璺宠浆鍒板父鐢ㄤ汉鍛樼粍鍒楄〃
	 * @return
	 */
	public String toUserGroupList(){
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		
		//鍒嗛〉鐩稿叧锛屼唬鐮佹墽琛岄『搴忎笉鍙�
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = ztreeService.countOfCommonGroup(emp.getEmployeeGuid());
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<CommonGroup> cgs = ztreeService.getCommonGroups(emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		this.getRequest().setAttribute("cgs", cgs);
		this.getRequest().setAttribute("nodeId", this.getRequest().getParameter("nodeId"));
		this.getRequest().setAttribute("type", this.getRequest().getParameter("type"));
		return "toUserGroupList";
	}
	
	public void getGroupsArrData() throws UnsupportedEncodingException{
		String isRole = getRequest().getParameter("isRole");
		String bigDepId= getRequest().getParameter("siteId");
		String deptIds = (String) getSession().getAttribute(MyConstants.DEPARMENT_ID);
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(StringUtils.isNotBlank(deptIds) && StringUtils.isBlank(bigDepId)){
			Department dept = departmentService.findSiteDept(deptIds);
			if(null != dept){
				bigDepId = dept.getDepartmentGuid();
			}
		}
		
		String nodeId=getRequest().getParameter("nodeId");
		String type = getRequest().getParameter("type");
		String userId = getRequest().getParameter("userId");
		String isZf = getRequest().getParameter("isZf");
		String mc = getRequest().getParameter("mc");
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfNode wfNode =  null;
		String wfUId = "";
		if(type != null && type.equals("dept")){
		}else{
			if(StringUtils.isNotBlank(nodeId)){
				wfNode = workflowBasicFlowService.findFormIdByNodeId(nodeId);
				WfMain wfMain = wfNode.getWfMain();
				wfUId = wfMain.getWfm_id();
			}
		}

		//获取当前登录人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		List<Department> secDepList = null;
		if(emp != null){
			String depId = emp.getDepartmentGuid();
			Department depart = departmentService.findDepartmentById(depId);
			String dep_shortDn = depart.getDepartmentShortdn();
			if(dep_shortDn!=null && !dep_shortDn.equals("")){
				String[] depIds = dep_shortDn.split(",");
				if(depIds!=null && depIds.length>=4){
					String begin_dep = depIds[0]+","+depIds[1]+","+depIds[2]+","+depIds[3];
					secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
				}
			}
		}
		 
		String groupId = "";
		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
		if (!("").equals(wfNode) && wfNode != null) {
			// 获取人员组的id
			groupId = wfNode.getWfn_staff();
			String ids = "";
			if(secDepList!=null){
				if(secDepList!=null && secDepList.size()>0){
					for(int i=0; i<secDepList.size(); i++){
						ids += "'"+secDepList.get(i).getDepartmentGuid()+"',";
					}
					if(ids!=null&& ids.length()>0){
						ids = ids.substring(0, ids.length()-1);
					}
				}
			}
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,employee.getEmployeeGuid());
			}else{
				String needBmWfUId =  SystemParamConfigUtil.getParamValueByParam("needBmWfUId");
				if(wfUId!=null && !wfUId.equals("")){
					if(needBmWfUId!=null && needBmWfUId.contains(wfUId)){
					}else{
						ids = "";
					}
				}else{
					ids = "";
				}
				// 获取人员组的人员信息
				innerUserList = groupService.getListByInnerUserId(groupId,null,ids);
			}
		}
		
		
		List<Employee> empsByMc = employeeService.findEmployeesByMc(mc);
		String str4EmpsByMc = "";
		for(Employee e:empsByMc){
			str4EmpsByMc += e.getEmployeeGuid();
		}
		List<InnerUserMapEmployee> innerUsers = new ArrayList<InnerUserMapEmployee>();
		if (innerUserList != null) {
			for (int i = 0; i < innerUserList.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUserList.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1||str4EmpsByMc.indexOf(innerUserMapEmployee.getEmployee_id())!=-1){
					innerUsers.add(innerUserMapEmployee);
				}
			}
		}		
		
		String str4Emps = "";
		for(InnerUserMapEmployee e:innerUsers){
			str4Emps += e.getEmployee_id();
		}
		getResponse().setCharacterEncoding("utf-8");
		
		List<CommonGroup> groupList = new ArrayList<CommonGroup>();
		if(StringUtils.isNotBlank(isRole) && isRole.equals("1")){
			//公共常用组
			Map<String, String> map = new HashMap<String, String>();
			map.put("isCommonUse", "yes");
			map.put("bigDepId", bigDepId);
			List<CommonGroup> commonGrpList = ztreeService.getCommonUseGroupList(map);
			groupList = commonGrpList;
		}else{
			//个人常用组
			List<CommonGroup> personalGroupList = ztreeService.findAllCommonGroupByUid(emp.getEmployeeGuid(),bigDepId);
			groupList = personalGroupList;
		}
		JSONArray arr = new JSONArray();
		if(!groupList.isEmpty()){
			for(int j = 0; j < groupList.size(); j++){
				CommonGroup group = groupList.get(j);
				List<CommonGroupUsers> userList = ztreeService.findAllCommonGroupUsersByGid(group.getId());
				if(StringUtils.isNotBlank(nodeId)){
					Iterator<CommonGroupUsers> ite = userList.iterator();
					while(ite.hasNext()){ 
						if(str4Emps.indexOf(ite.next().getEmpId())<0){
							ite.remove();
						}
					}
				}
				JSONArray empArr = new JSONArray();
				if(!userList.isEmpty()){
					for (int i = 0; i < userList.size(); i++) {
						if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && userList.get(i).getEmpId().equals(emp.getEmployeeGuid())){
							continue;
						}
						if(group.getId().equals(userList.get(i).getGid())){
							JSONObject empJo = new JSONObject();
							empJo.put("id", userList.get(i).getEmpId());
							empJo.put("name", userList.get(i).getEmpName());
							empArr.add(empJo);
						}
					}
					
					JSONObject groupJo = new JSONObject();
					groupJo.put("id", group.getId());
					groupJo.put("name", group.getName());
					groupJo.put("empArr", empArr);
					arr.add(groupJo);
				}
			}
		}
		
		// 打开流
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(arr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void getFullArrData() throws UnsupportedEncodingException{
		String isZf = getRequest().getParameter("isZf");
		String isowner = getRequest().getParameter("isowner");
		String userId = getRequest().getParameter("userId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		String employeDn = emp.getEmployeeDn();
		String [] employeDns = employeDn.split(",");
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = null;
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		String depRootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}	
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(deptsid);
			
			List<Employee> allEmps = ztreeService.getAllEmpsByUserIds(userids.toString());
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					if(dept!=null && !dept.getDepartmentGuid().equals(depRootId) ){
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("pId", dept.getSuperiorGuid());
						jo.put("name", dept.getDepartmentName());
						jo.put("open", false);
						jo.put("type", "folder");
						jo.put("isParent", true);
						jo.put("chkDisabled", false);
						jsonTree.add(jo);
					}
				}
			}
			if(!allEmps.isEmpty()){
				for(Employee e:allEmps){
					if(e!=null){
						if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && e.getEmployeeGuid().equals(emp.getEmployeeGuid())){
							continue;
						}
						JSONObject jo = new JSONObject();
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jsonTree.add(jo);
					}
				}
			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			if(isowner!=null && isowner.equals("1")){
				department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
				allDepts = ztreeService.getAllChildDeptBydeptId(department_rootId);
				allEmps = ztreeService.getAllChildEmpsByDeptId(department_rootId);
			}else{
				Collection<Department> list = departmentService.findThirdDepartments(depRootId);
				for (Department department : list) {
					String depHierarchy =department.getDepartmentHierarchy();
					String [] depHierarchys = depHierarchy.split(",");
					if(depHierarchys.length>2 && employeDns[employeDns.length-3].equals(depHierarchys[depHierarchys.length-3])){
						department_rootId = department.getDepartmentGuid();
						break;
					}else{
						department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
					}
				}
				allDepts = ztreeService.getAllChildDeptBydeptId(department_rootId);
				allEmps = ztreeService.getAllChildEmpsByDeptId(department_rootId);
			}
			
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					if(dept!=null && !dept.getDepartmentGuid().equals(depRootId)){
						if(!allEmps.isEmpty()){
							for(Employee e:allEmps){
								if(e!=null){
									if(StringUtils.isNotBlank(isZf) && isZf.equals("1") && e.getEmployeeGuid().equals(emp.getEmployeeGuid())){
										continue;
									}
									
									if(dept.getDepartmentGuid().equals(e.getEmployeeGuid())){
										JSONObject jo = new JSONObject();
										jo.put("id", e.getEmployeeGuid());
										jo.put("pId", e.getDepartmentGuid());
										jo.put("name", e.getEmployeeName());
										jo.put("type", "file");
										jsonTree.add(jo);
									}
								}
							}
							
							JSONObject jo = new JSONObject();
							jo.put("id", dept.getDepartmentGuid());
							jo.put("pId", dept.getSuperiorGuid());
							jo.put("name", dept.getDepartmentName());
							jo.put("open", false);
							jo.put("type", "folder");
							jo.put("isParent", true);
							jo.put("chkDisabled", false);
							jsonTree.add(jo);
						}
						
						
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 
	 * @Description: 显示所有部门人员
	 * @author: xiep
	 * @time: 2018-5-23 上午10:59:52
	 * @return
	 */
	public String showAllDeptTree(){
		String mac = getRequest().getParameter("mac");
		String allEmp = getRequest().getParameter("allEmp");
		String isDeptTree = getRequest().getParameter("isDeptTree");
		String selectedUserIds = getRequest().getParameter("userIds");
		String isTra = getRequest().getParameter("isTra");
		String searchSiteName = getRequest().getParameter("searchSiteName");
		getRequest().setAttribute("mac", mac);
		getRequest().setAttribute("allEmp", allEmp);
		getRequest().setAttribute("isDeptTree", isDeptTree);
		getRequest().setAttribute("routType", 4);
		getRequest().setAttribute("selectedUserIds", selectedUserIds);
		getRequest().setAttribute("isTra", isTra);
		getRequest().setAttribute("searchSiteName", searchSiteName);
		return "showAllDeptTree";
	}
	
	public void userGroupIsIn(){

		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String groupName = getRequest().getParameter("groupName");
		String siteId = getRequest().getParameter("siteId");
		getRequest().setAttribute("siteId", siteId);
		CommonGroup cg=groupService.userGroupBySiteId(groupName,siteId,emp.getEmployeeGuid());
		if(cg!=null){
			toPage("yes");
		}
		toPage("no");
	}
	
	/**
	 * 方法描述: [字符串乱码处理]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-10-24-上午11:18:39<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param msg
	 * @return
	 * String
	 */
	public static String toChinese(String msg){
		if(CommonUtil.stringIsNULL(msg)){
			return "";
		}
		if(isMessyCode(msg)){
			try {
				return new String(msg.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
	
	// 判断字符串是否为乱码
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (char element : ch) {
			char c = element;
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
}
