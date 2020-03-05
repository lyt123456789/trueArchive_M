package cn.com.trueway.workflow.set.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.DepSortUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.UserGroup;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.GroupService;
import net.sf.json.JSONObject;

import com.google.gson.Gson;

/**
 * @author 
 * @version  类说明 生成部门人员组织树
 */
public class DeptTreeAction extends BaseAction {


	private static final long serialVersionUID = -8393249580234964707L;
	private DepartmentService departmentService;
	private EmployeeService employeeService;
	private WorkflowBasicFlowService workflowBasicFlowService;
	private GroupService groupService;
	private PendingService pendingService;
	private RoleManageService roleManageService;
	private ArchiveUsingService archiveUsingService;
	
	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}
	
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

	private String root;

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	


	public ArchiveUsingService getArchiveUsingService() {
		return archiveUsingService;
	}

	public void setArchiveUsingService(ArchiveUsingService archiveUsingService) {
		this.archiveUsingService = archiveUsingService;
	}

	/**
	 * 得到常用联系人Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getContentCylxr() throws Exception {
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		List<Employee> list =null;
		if(mc!=null&&!"".equals(mc)){
			list = employeeService.getEmployeeByUsername(mc);
		}
		String userids="";
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(i!=0){
					userids +=",";
				}
				userids +=list.get(i).getEmployeeGuid();
			}
		}
		getResponse().setCharacterEncoding("utf-8");
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String userid=employee==null?null:employee.getEmployeeGuid();
		UserGroup userGroup=new UserGroup();
		userGroup.setUserid(userid);
		PrintWriter out = getResponse().getWriter();
		//分页相关，代码执行顺序不变
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			out.println("[");
			//out.print(" {\"text\":\"");// 不显示链接的下划线
			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");
			List<UserGroup> userGroups = groupService.getUserGroupList(userGroup,userids);
			if(userGroups!=null){
				UserGroup newUserGroup=null;
				// 生成JSON字符串
				for (int i = 0; i < userGroups.size(); i++) {
					newUserGroup = (UserGroup) userGroups.get(i);
					out.print(" {\"text\":\"<a  id='"+newUserGroup.getId()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
					out.print(newUserGroup.getName());// 节点的名字
					out.print("</a>\",");
					out.print("\"id\":\"");
					out.print(newUserGroup.getId());// 节点的id
					out.print("\",\"hasChildren\":");
					String id =newUserGroup.getRelation_userids();
					if (id != null && !"".equals(id)) {
						out.print(true);// 有无子节点 true/false
					} else {
						out.print(false); // 页面上显示部门节点都是有子节点的样子
					}
					out.print(",\"classes\":\"");
					out.print("folder");// 节点的样式
					out.print("\"");
					out.println("}");// 第一个节点结束
					if (i != userGroups.size() - 1) {
						out.println(",");
					}
				}
			}
			out.print("]");
			out.println("}");
			out.print("]");
		}else{
			String groupid = root.toString();
			UserGroup newUserGroup = groupService.getOneUserGroupById(groupid,userids);
			out.println("[");
			List<Employee> es = (List<Employee>) employeeService.getEmployeesByIds(newUserGroup.getRelation_userids(),mc);
			// 得到的人員Set不为空时，才生成字符串
			if (es != null && es.size() > 0) {
				createJSON2(es, out);
			}
			out.print("]");
		}
		// 关闭流
		out.close();
	
	
	}
	
	/**
	 * 得到Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getContent() throws Exception {
			getResponse().setCharacterEncoding("utf-8");
			String type = getRequest().getParameter("type");
			String mc=getRequest().getParameter("mc");
			if(mc!=null&&!"".equals(mc)){
				mc =URLDecoder.decode(mc,"utf-8");
			}
			StringBuffer userids = null;
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
				String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
				//如果采用大部门，则根节点为大部门id
				//add by panh in 20130618
				//获得大部门id
				String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
				String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
				if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
					department_rootId=bigDepId;
				}
				//不包含人员
				String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
				
				// 打开流
				PrintWriter out = getResponse().getWriter();
				// 第一次加载树时，root="source",生成“大部门”的节点
				if (root.equals("source")) {
					Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
					String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
					// 生成JSON字符串
					out.println("[");
					//out.print(" {\"text\":\"");// 不显示链接的下划线
					out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
					out.print(rootString);// 节点的名字
					out.print("</a>\",");
					out.print("\"id\":\"");
					out.print(0);// 节点的id
					out.print("\",\"expanded\":");
					out.print(true);// 默认是否是展开方式
					out.print(",\"classes\":\"");
					out.print("folder");// 节点的样式
					out.print("\"");
					out.println(",");
					out.print("\"children\":");
					out.println("[");

					List<Department> departments = new ArrayList<Department>();
					
					departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
					//过滤本节点
					if (departments!=null) {
						for (int i = 0; i < departments.size(); i++) {
							if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
								departments.remove(i);
							}
						}
					}
//					if(isExistRoot.equals("true")){
						 //得到的 部门 List不为空时，才生成字符串
//						for (int i = 0; i < ds.size(); i++) {
//							Department department = departmentService.findDepartmentById(department_rootId);
//							if (null != department) {
//								departments.add(department);
//							}
//						}
//					}else{
//						departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
//					}
					
					
					if (departments != null && departments.size() > 0) {
						departments = DepSortUtil.sortDepartment(departments);
						createJSON(departments, out,deptsid,userids.toString(),mc,type);
					}
					
					if(userids!=null){
						//如果包含人员才加载人员
						if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
							
						}else {
							if(type != null && type.equals("dept")){
								
							}else{
								List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids.toString());
								// 得到的人員Set不为空时，才生成字符串
								if (es != null && es.size() > 0) {
									if (departments != null && departments.size() > 0) {
										out.println(",");
									}
									createJSON2(es, out);
								}
							}
							
						}
					}

					out.print("]");
					out.println("}");
					out.print("]");
				} else {
					List<Department> departments=null;
					String department_id = root.toString();
					Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
					departments = departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
					out.println("[");
					// 得到的部门List不为空时，才生成字符串
					if (departments != null && departments.size() > 0) {
						createJSON(departments, out,deptsid,userids.toString(),mc,type);
					}
					
					//如果包含人员才加载人员
					if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
						
					}else {
						
						if(type != null && type.equals("dept")){
							
						}else{
							List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids.toString());
							// 得到的人員Set不为空时，才生成字符串
							if (es != null && es.size() > 0) {
								if (departments != null && departments.size() > 0) {
									out.println(",");
								}
								createJSON2(es, out);
							}
						}
						
					}
					
					
					out.print("]");
				}
				// 关闭流
				out.close();
			}else{
//				WebInfo webInfo = (WebInfo) getSession().getAttribute("webInfo");
//				String web_id = webInfo.getId();
//				ArrayList<WebInfoURL> ds = (ArrayList<WebInfoURL>) webInfoAddService.findWebInfoURLs(web_id);
				//String spdepid= SystemParamConfigUtil.getParamValueByParam("sp_dep_id");
				String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
				
				//如果采用大部门，则根节点为大部门id
				//add by panh in 20130618
				//获得大部门id
				String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
				String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
				if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
					department_rootId=bigDepId;
				}
				//不包含人员
				String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
				
				
				
				// 打开流
				PrintWriter out = getResponse().getWriter();
				// 第一次加载树时，root="source",生成“大部门”的节点
				if (root.equals("source")) {
					Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
					String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
					// 生成JSON字符串
					out.println("[");
					//out.print(" {\"text\":\"");// 不显示链接的下划线
					out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
					out.print(rootString);// 节点的名字
					out.print("</a>\",");
					out.print("\"id\":\"");
					out.print(0);// 节点的id
					out.print("\",\"expanded\":");
					out.print(true);// 默认是否是展开方式
					out.print(",\"classes\":\"");
					out.print("folder");// 节点的样式
					out.print("\"");
					out.println(",");
					out.print("\"children\":");
					out.println("[");

					List<Department> departments = new ArrayList();
					
					departments=departmentService.queryDepartmentsBySuperdepIds("'"+department_rootId+"'");
					//过滤本节点
					if (departments!=null) {
						for (int i = 0; i < departments.size(); i++) {
							if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
								departments.remove(i);
							}
						}
					}
//					if(isExistRoot.equals("true")){
						 //得到的 部门 List不为空时，才生成字符串
//						for (int i = 0; i < ds.size(); i++) {
//							Department department = departmentService.findDepartmentById(department_rootId);
//							if (null != department) {
//								departments.add(department);
//							}
//						}
//					}else{
//						departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
//					}
					
					
					
					
					//如果包含人员才加载人员
					if (CommonUtil.stringNotNULL(notEmployee) && notEmployee.equals("1")) {
						
					}else {
						if(type != null && type.equals("dept")){
							
						}else{
							List<Employee> es = (List<Employee>) employeeService.findEmployees(department_rootId);
							// 得到的人員Set不为空时，才生成字符串
							if (es != null && es.size() > 0) {
								createJSON2(es, out);
								if (departments != null && departments.size() > 0) {
									out.println(",");
								}
							}
						}
					}
					
					if (departments != null && departments.size() > 0) {
						departments = DepSortUtil.sortDepartment(departments);
						createJSON5(departments, out);
					}
					out.print("]");
					out.println("}");
					out.print("]");
				} else {
					List<Department> departments=null;
					String department_id = root.toString();
					departments = (List) departmentService.findDepartments(department_id);
					out.println("[");
					
					
					//如果包含人员才加载人员
					if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
						
					}else {
						if(type != null && type.equals("dept")){
							
						}else{
							List<Employee> es = (List<Employee>) employeeService.findEmployees(department_id);
							// 得到的人員Set不为空时，才生成字符串
							if (es != null && es.size() > 0) {
								createJSON2(es, out);
								if (departments != null && departments.size() > 0) {
									out.println(",");
								}
							}
						}
						
					}
					// 得到的部门List不为空时，才生成字符串
					if (departments != null && departments.size() > 0) {
						createJSON5(departments, out);
					}
					
					out.print("]");
				}
				// 关闭流
				out.close();
			}
		
	}
	
	/**
	 * 得到主办协办Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getTsByProcessId() throws Exception {
		String isZBPush = getRequest().getParameter("isZBPush");
		String processId=getRequest().getParameter("processId");
		String mc=getRequest().getParameter("mc");
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfProcess wfProcess = pendingService.getProcessByID(processId);
		String userids = null;
		if(wfProcess!=null){
			if(isZBPush!=null&&!"1".equals(isZBPush)){
				//根据父实例id获取所有主办协办的人员id
				userids=pendingService.getAllUserIdByFInstanceId(wfProcess.getfInstancdUid(),mc);
			}else{
				//根据父实例id获取主流程的发信人id
				userids=pendingService.getZUserIdByFInstanceId(wfProcess.getfInstancdUid(),mc);
			}
		}
		getResponse().setCharacterEncoding("utf-8");
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		String deptsid ="";
		if(userids==null){
			return;
		}
		if(userids!=null&&!"".equals(userids)){
			deptsid =employeeService.getDeptByEmployeeIds(userids);
		}
		//根据userids获取部门还是人员
		String[] users = userids.split(",");
		for(int i=0;i<users.length;i++){
			Department department = departmentService.findDepartmentById(users[i]);
			if(department!=null){
				if(deptsid==null){
					deptsid = department.getDepartmentGuid();
				}else{
					deptsid +=","+ department.getDepartmentGuid();
				}
			}
		}
		if(deptsid==null){
			return;
		}
		//如果采用大部门，则根节点为大部门id
		//add by panh in 20130618
		//获得大部门id
		String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
		String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
		if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
			department_rootId=bigDepId;
		}
		//不包含人员
		String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
		
		

		// 打开流
		PrintWriter out = getResponse().getWriter();
		// 第一次加载树时，root="source",生成“大部门”的节点
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			out.println("[");
			//out.print(" {\"text\":\"");// 不显示链接的下划线
			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");

			List<Department> departments = new ArrayList<Department>();
			
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
//			if(isExistRoot.equals("true")){
				 //得到的 部门 List不为空时，才生成字符串
//				for (int i = 0; i < ds.size(); i++) {
//					Department department = departmentService.findDepartmentById(department_rootId);
//					if (null != department) {
//						departments.add(department);
//					}
//				}
//			}else{
//				departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
//			}
			
			
			if (departments != null && departments.size() > 0) {
				createJSON(departments, out,deptsid,userids,mc,null);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids);
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON2(es, out);
				}
			}
			

			out.print("]");
			out.println("}");
			out.print("]");

		} else {
			List<Department> departments=null;
			String department_id = root.toString();
			
			Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
//			 for(int i=departments.size()-1;i>=0;i--){
//					if(departments.get(i).getDepartmentGuid().equals(department_rootId)){
//						departments.remove(i);
//					}
//			}
			out.println("[");
			// 得到的部门List不为空时，才生成字符串
			if (departments != null && departments.size() > 0) {
				createJSON(departments, out,deptsid,userids,mc,null);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids);
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON2(es, out);
				}
			}
			
			
			out.print("]");
		}
		// 关闭流
		out.close();
	}
	
	
	/**
	 * 得到Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getContentByUserid() throws Exception {
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
		List<Employee> bxcylist=new ArrayList<Employee>();
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
				String needBmWfUId =  "";
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
				String bxcyuserids= "";
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
		if (innerUserList != null) {
			for (int i = 0; i < innerUserList.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUserList
						.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1){
					if (userids != null) {
						userids += ("," + innerUserMapEmployee.getEmployee_id());
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
		//如果采用大部门，则根节点为大部门id
		//add by panh in 20130618
		//获得大部门id
		String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
		String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
		if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
			department_rootId=bigDepId;
		}
		//不包含人员
		String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门

		// 打开流
		PrintWriter out = getResponse().getWriter();
		// 第一次加载树时，root="source",生成“大部门”的节点
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			out.println("[");
			//out.print(" {\"text\":\"");// 不显示链接的下划线
			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");

			List<Department> departments = new ArrayList<Department>();
			
			if(!"".equals(deptsid)){
				departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
			}else{
				departments = departmentService.queryDepartmentsBySuperdepIds("'"+d_root.getDepartmentGuid()+"'");
			}
//			if(isExistRoot.equals("true")){
				 //得到的 部门 List不为空时，才生成字符串
//				for (int i = 0; i < ds.size(); i++) {
//					Department department = departmentService.findDepartmentById(department_rootId);
//					if (null != department) {
//						departments.add(department);
//					}
//				}
//			}else{
//				departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
//			}
			
			
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				if(type != null && type.equals("dept")){
					
				}else{
					List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids);
					// 得到的人員Set不为空时，才生成字符串
					if (es != null && es.size() > 0) {
						
						createJSON2(es, out);
						
						if (departments != null && departments.size() > 0) {
							out.println(",");
						}
						
					}
				}
			}
			
			if (departments != null && departments.size() > 0) {
				createJSON(departments, out,deptsid,userids,mc,type);
			}
			
			out.print("]");
			out.println("}");
			out.print("]");

		} else {
			List<Department> departments=null;
			String department_id = root.toString();
			
			Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
			
			if(!"".equals(deptsid)){
				departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
			}else{
				departments = departmentService.queryDepartmentsBySuperdepIds("'"+d_root.getDepartmentGuid()+"'");
			}
			out.println("[");
		
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				if(type != null && type.equals("dept")){
					
				}else{
					List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids);
					// 得到的人員Set不为空时，才生成字符串
					if (es != null && es.size() > 0) {
						createJSON2(es, out);
						if (departments != null && departments.size() > 0) {
							out.println(",");
						}
					}
				}
			}
			// 得到的部门List不为空时，才生成字符串
			if (departments != null && departments.size() > 0) {
				createJSON(departments, out,deptsid,userids,mc,type);
			}
			out.print("]");
		}
		// 关闭流
		out.close();
	}
	
//	/**
//	 * 得到Tree字符串
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public void getContent1() throws Exception {
//		long l = System.currentTimeMillis();
//		String nodeId=getRequest().getParameter("nodeId");
//		String mc=getRequest().getParameter("mc");
//		if(mc!=null){
//			mc =URLDecoder.decode(mc,"utf-8");
//		}else{
//			mc="";
//		}
//		WfNode wfNode =  workflowBasicFlowService.findFormIdByNodeId(nodeId);
//		String groupId = "";
//		List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
//		if (!("").equals(wfNode) && wfNode != null) {
//			// 获取人员组的id
//			groupId = wfNode.getWfn_staff();
//			// 获取人员组的人员信息
//			innerUserList = groupService.getListByInnerUserId(groupId);
//		}
//		getResponse().setCharacterEncoding("utf-8");
//		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
//		String deptsid ="";
//		String userids = null;
//		if (innerUserList != null) {
//			for (int i = 0; i < innerUserList.size(); i++) {
//				InnerUserMapEmployee innerUserMapEmployee = innerUserList
//						.get(i);
//				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1){
//					if (userids != null) {
//						userids += ("," + innerUserMapEmployee.getEmployee_id());
//					} else {
//						userids = innerUserMapEmployee.getEmployee_id();
//					}
//				}
//			}
//		}
//		if(userids==null){
//			return;
//		}
//		if(userids!=null&&!"".equals(userids)){
//			deptsid =employeeService.getDeptByEmployeeIds(userids);
//		}
//		//如果采用大部门，则根节点为大部门id
//		//add by panh in 20130618
//		//获得大部门id
//		String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
//		String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
//		if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
//			department_rootId=bigDepId;
//		}
//		//不包含人员
//		String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
//		
//		
//
//		System.err.println(2222);
//		// 打开流
//		PrintWriter out = getResponse().getWriter();
//		// 第一次加载树时，root="source",生成“大部门”的节点
//		if (root.equals("source")) {
//			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
//			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
//			// 生成JSON字符串
//			out.println("[");
//			//out.print(" {\"text\":\"");// 不显示链接的下划线
//			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
//			out.print(rootString);// 节点的名字
//			out.print("</a>\",");
//			out.print("\"id\":\"");
//			out.print(0);// 节点的id
//			out.print("\",\"expanded\":");
//			out.print(true);// 默认是否是展开方式
//			out.print(",\"classes\":\"");
//			out.print("folder");// 节点的样式
//			out.print("\"");
//			out.println(",");
//			out.print("\"children\":");
//			out.println("[");
//
//			List<Department> departments = new ArrayList<Department>();
//			
//			departments=departmentService.queryDepartmentsBydepIds(department_rootId,deptsid);
//			//过滤本节点
//			if (departments!=null) {
//				for (int i = 0; i < departments.size(); i++) {
//					if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
//						departments.remove(i);
//					}
//				}
//			}
////			if(isExistRoot.equals("true")){
//				 //得到的 部门 List不为空时，才生成字符串
////				for (int i = 0; i < ds.size(); i++) {
////					Department department = departmentService.findDepartmentById(department_rootId);
////					if (null != department) {
////						departments.add(department);
////					}
////				}
////			}else{
////				departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
////			}
//			
//			
//			if (departments != null && departments.size() > 0) {
//				departments = DepSortUtil.sortDepartment(departments);
//				createJSON(departments, out,deptsid,userids,mc);
//			}
//			
//			//如果包含人员才加载人员
//			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
//				
//			}else {
//				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids);
//				// 得到的人員Set不为空时，才生成字符串
//				if (es != null && es.size() > 0) {
//					if (departments != null && departments.size() > 0) {
//						out.println(",");
//					}
//					createJSON2(es, out);
//				}
//			}
//			
//
//			out.print("]");
//			out.println("}");
//			out.print("]");
//
//		} else {
//			List<Department> departments=null;
//			String department_id = root.toString();
//			departments = departmentService.queryDepartmentsBydepIds(department_id,deptsid);
////			 for(int i=departments.size()-1;i>=0;i--){
////					if(departments.get(i).getDepartmentGuid().equals(department_rootId)){
////						departments.remove(i);
////					}
////			}
//			out.println("[");
//			// 得到的部门List不为空时，才生成字符串
//			if (departments != null && departments.size() > 0) {
//				createJSON(departments, out,deptsid,userids,mc);
//			}
//			
//			//如果包含人员才加载人员
//			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
//				
//			}else {
//				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids);
//				// 得到的人員Set不为空时，才生成字符串
//				if (es != null && es.size() > 0) {
//					if (departments != null && departments.size() > 0) {
//						out.println(",");
//					}
//					createJSON2(es, out);
//				}
//			}
//			
//			
//			out.print("]");
//		}
//		// 关闭流
//		out.close();
//		System.err.println((l-System.currentTimeMillis()));
//	}
	
	
	/**
	 * 得到Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getContent2() throws Exception {
		
		
		getResponse().setCharacterEncoding("utf-8");

//		WebInfo webInfo = (WebInfo) getSession().getAttribute("webInfo");
//		String web_id = webInfo.getId();
//		ArrayList<WebInfoURL> ds = (ArrayList<WebInfoURL>) webInfoAddService.findWebInfoURLs(web_id);
		//String spdepid= SystemParamConfigUtil.getParamValueByParam("sp_dep_id");
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		
		//如果采用大部门，则根节点为大部门id
		//add by panh in 20130618
		//获得大部门id
		String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
		String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
		if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
			department_rootId=bigDepId;
		}
		//不包含人员
		String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
		
		
		
		// 打开流
		PrintWriter out = getResponse().getWriter();
		// 第一次加载树时，root="source",生成“大部门”的节点
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			out.println("[");
			//out.print(" {\"text\":\"");// 不显示链接的下划线
			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");

			List<Department> departments = new ArrayList();
			
			departments=departmentService.queryDepartmentsBySuperdepIds("'"+department_rootId+"'");
			//过滤本节点
			if (departments!=null) {
				for (int i = 0; i < departments.size(); i++) {
					if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
						departments.remove(i);
					}
				}
			}
//			if(isExistRoot.equals("true")){
				 //得到的 部门 List不为空时，才生成字符串
//				for (int i = 0; i < ds.size(); i++) {
//					Department department = departmentService.findDepartmentById(department_rootId);
//					if (null != department) {
//						departments.add(department);
//					}
//				}
//			}else{
//				departments = (List) departmentService.queryDepartmentsBydepIds(dep_ids);
//			}
			
			
			if (departments != null && departments.size() > 0) {
				departments = DepSortUtil.sortDepartment(departments);
				createJSON(departments, out);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployees(department_rootId);
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON2(es, out);
				}
			}
			

			out.print("]");
			out.println("}");
			out.print("]");

		} else {
			List<Department> departments=null;
			String department_id = root.toString();
			departments = (List) departmentService.findDepartments(department_id);
//			 for(int i=departments.size()-1;i>=0;i--){
//					if(departments.get(i).getDepartmentGuid().equals(department_rootId)){
//						departments.remove(i);
//					}
//			}
			out.println("[");
			// 得到的部门List不为空时，才生成字符串
			if (departments != null && departments.size() > 0) {
				createJSON(departments, out);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployees(department_id);
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON2(es, out);
				}
			}
			
			
			out.print("]");
		}
		// 关闭流
		out.close();
	}
	
	/**
	 * 得到Tree字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getContent3() throws Exception {
		String nodeId=getRequest().getParameter("nodeId");
		String mc=getRequest().getParameter("mc");
		String userId = getRequest().getParameter("userId");
		if(mc!=null){
			mc =URLDecoder.decode(mc,"utf-8");
		}else{
			mc="";
		}
		WfNode wfNode =  workflowBasicFlowService.findFormIdByNodeId(nodeId);
		String groupId = "";
		String wfUId ="";
		String routeType = "";
		Employee emp = employeeService.findEmployeeById(userId);
		String depId = emp.getDepartmentGuid();
		Department depart = departmentService.findDepartmentById(depId);
		String dep_shortDn = depart.getDepartmentShortdn();
		List<Department> secDepList = null;
		if(dep_shortDn!=null && !dep_shortDn.equals("")){
			String[] depIds = dep_shortDn.split(",");
			if(depIds!=null && depIds.length>=2){
				String begin_dep = depIds[0]+","+depIds[1];
				secDepList = departmentService.queryDepartmentListByShortDN(begin_dep);
			}
		}
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
			WfMain wfMain = wfNode.getWfMain();
			wfUId = wfMain.getWfm_id();
			routeType=wfNode.getWfn_route_type();
			boolean needBm = false;
			if(wfNode != null && wfNode.getNode_startJb() != null && !"".equals(wfNode.getNode_startJb())){
				// 这个节点 取  <= 组下面的人
				Employee employee = employeeService.findEmployeeById(userId);
				innerUserList = groupService.getListByInnerUserIdAndGrade(groupId,null,employee.getEmployeeGuid());
			}else{
				String needBmWfUId =  "";
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
			if(routeType!=null && "4".equals(routeType) && needBm){
				String bxcyuserids= "";
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
		String deptsid =null;
		StringBuffer userids = null;
		if (innerUserList != null) {
			for (int i = 0; i < innerUserList.size(); i++) {
				InnerUserMapEmployee innerUserMapEmployee = innerUserList
						.get(i);
				if(innerUserMapEmployee.getEmployee_name().indexOf(mc)!=-1){
					if (userids != null) {
						userids.append(",").append(innerUserMapEmployee.getEmployee_id());
					} else {
						userids=new StringBuffer();
						userids.append(innerUserMapEmployee.getEmployee_id());
					}
				}
			}
		}
		if(userids==null){
			return;
		}
		if(userids!=null&&!"".equals(userids)){
			deptsid=employeeService.getDeptByEmployeeIds(userids.toString());
		}
		if(deptsid==null){
			return;
		}
		//如果采用大部门，则根节点为大部门id
		//add by panh in 20130618
		//获得大部门id
		String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
		String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
		if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
			department_rootId=bigDepId;
		}
		//不包含人员
		String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
		// 打开流
		PrintWriter out = getResponse().getWriter();
		// 第一次加载树时，root="source",生成“大部门”的节点
		if (root.equals("source")) {
			Department d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
			String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
			// 生成JSON字符串
			out.println("[");
			//out.print(" {\"text\":\"");// 不显示链接的下划线
			out.print(" {\"text\":\"<a  id='"+d_root.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;font-size:25px;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");

			List<Department> departments = new ArrayList<Department>();
			
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
			
			
			if (departments != null && departments.size() > 0) {
				createJSON1(departments, out,deptsid,userids.toString(),mc);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids.toString());
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON3(es, out);
				}
			}
			

			out.print("]");
			out.println("}");
			out.print("]");

		} else {
			List<Department> departments=null;
			String department_id = root.toString();
			Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
			departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
			out.println("[");
			// 得到的部门List不为空时，才生成字符串
			if (departments != null && departments.size() > 0) {
				createJSON1(departments, out,deptsid,userids.toString(),mc);
			}
			
			//如果包含人员才加载人员
			if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				
			}else {
				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids.toString());
				// 得到的人員Set不为空时，才生成字符串
				if (es != null && es.size() > 0) {
					if (departments != null && departments.size() > 0) {
						out.println(",");
					}
					createJSON3(es, out);
				}
			}
			
			
			out.print("]");
		}
		// 关闭流
		out.close();
	
	}
	
	/**
	 * 遍历List，生成JSON字符串
	 * @param userids 
	 * @param deptsid 
	 * 
	 * @param rights
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void createJSON(List departments, PrintWriter out, String deptsid, String userids, String mc,String type )
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			
			department = (Department) departments.get(i);
			
			

//			out.print(" {\"text\":\"");// 不显示链接的下划线
//			out.print(department.getDepartmentName());// 节点的名字
			
			out.print(" {\"text\":\"<a  id='"+department.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线

			out.print(department.getDepartmentName());// 节点的名字

			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			if(mc==null||"".equals(mc)){
				out.print("\",\"hasChildren\":");

//				// 目前要遍历查询部门表与人员表才能判断有无子节点
//				List<Department> ds = (List<Department>) departmentService
//						.findDepartments(department.getDepartmentGuid());
//				List<Employee> es = (List<Employee>) employeeService
//						.findEmployees(department.getDepartmentGuid());
//				if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
					out.print(true);// 有无子节点 true/false
//				} else {
//					out.print(false); // 页面上显示部门节点都是有子节点的样子
//				}

				out.print(",\"classes\":\"");
			}else{
				// 目前要遍历查询部门表与人员表才能判断有无子节点
				List<Department> ds = (List<Department>) departmentService
						.queryDepartmentsBydepIds1(department,deptsid);
				List<Employee> es = (List<Employee>) employeeService
						.findEmployeesByIds(department.getDepartmentGuid(),userids);
				if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
					out.print("\",\"expanded\":");
					out.print(true);// 默认是否是展开方式
					out.println(",");
					out.print("\"children\":");
					out.println("[");
					if(ds != null && ds.size() > 0){
						createJSON(ds,out,deptsid,userids,mc,type);
					}
					if(type != null  && type.equals("dept")){
						
					}else{
						if(es != null && es.size() > 0){
							if(ds != null && ds.size() > 0){
								out.println(",");
							}
							createJSON2(es,out);
						}
					}
					
					out.println("]");
					out.print(",\"classes\":\"");
				} else {
					out.print("\",\"classes\":\"");
				}
			}
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 遍历List，生成JSON字符串
	 * @param userids 
	 * @param deptsid 
	 * 
	 * @param rights
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void createJSON1(List departments, PrintWriter out, String deptsid, String userids, String mc)
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			
			department = (Department) departments.get(i);
			
			

//			out.print(" {\"text\":\"");// 不显示链接的下划线
//			out.print(department.getDepartmentName());// 节点的名字
			
			out.print(" {\"text\":\"<a  id='"+department.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;font-size:25px;' >");// 不显示链接的下划线

			out.print(department.getDepartmentName());// 节点的名字

			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			if(mc==null||"".equals(mc)){
				out.print("\",\"hasChildren\":");

//				// 目前要遍历查询部门表与人员表才能判断有无子节点
//				List<Department> ds = (List<Department>) departmentService
//						.findDepartments(department.getDepartmentGuid());
//				List<Employee> es = (List<Employee>) employeeService
//						.findEmployees(department.getDepartmentGuid());
//				if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
					out.print(true);// 有无子节点 true/false
//				} else {
//					out.print(false); // 页面上显示部门节点都是有子节点的样子
//				}

				out.print(",\"classes\":\"");
			}else{
				// 目前要遍历查询部门表与人员表才能判断有无子节点
				List<Department> ds = (List<Department>) departmentService
						.queryDepartmentsBydepIds1(department, deptsid);
				List<Employee> es = (List<Employee>) employeeService
						.findEmployeesByIds(department.getDepartmentGuid(),userids);
				if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
					out.print("\",\"expanded\":");
					out.print(true);// 默认是否是展开方式
					out.println(",");
					out.print("\"children\":");
					out.println("[");
					if(ds != null && ds.size() > 0){
						createJSON1(ds,out,deptsid,userids,mc);
					}
					if(es != null && es.size() > 0){
						if(ds != null && ds.size() > 0){
							out.println(",");
						}
						createJSON3(es,out);
					}
					out.println("]");
					out.print(",\"classes\":\"");
				} else {
					out.print("\",\"classes\":\"");
				}
			}
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 遍历List，生成JSON字符串
	 * 
	 * @param rights
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void createJSON(List departments, PrintWriter out)
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			
			department = (Department) departments.get(i);
			
			

//			out.print(" {\"text\":\"");// 不显示链接的下划线
//			out.print(department.getDepartmentName());// 节点的名字
			
			out.print(" {\"text\":\"<a  id='"+department.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线

			out.print(department.getDepartmentName());// 节点的名字

			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			// 目前要遍历查询部门表与人员表才能判断有无子节点
			List<Department> ds = (List<Department>) departmentService
					.findDepartments(department.getDepartmentGuid());
			List<Employee> es = (List<Employee>) employeeService
					.findEmployees(department.getDepartmentGuid());
			if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
				out.print("\",\"expanded\":");
				out.print(true);// 默认是否是展开方式
				out.println(",");
				out.print("\"children\":");
				out.println("[");
				if(ds != null && ds.size() > 0){
					createJSON(ds,out);
				}
				if(es != null && es.size() > 0){
					if(ds != null && ds.size() > 0){
						out.println(",");
					}
					createJSON2(es,out);
				}
				out.println("]");
				out.print(",\"classes\":\"");
			} else {
				out.print("\",\"classes\":\"");
			}

			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 遍历List，生成JSON字符串
	 * 
	 * @param rights
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void createJSON5(List departments, PrintWriter out)
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			
			department = (Department) departments.get(i);
			
			

//			out.print(" {\"text\":\"");// 不显示链接的下划线
//			out.print(department.getDepartmentName());// 节点的名字
			
			out.print(" {\"text\":\"<a  id='"+department.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线

			out.print(department.getDepartmentName());// 节点的名字

			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			out.print("\",\"hasChildren\":");

			// 目前要遍历查询部门表与人员表才能判断有无子节点
			List<Department> ds = (List<Department>) departmentService
					.findDepartments(department.getDepartmentGuid());
			List<Employee> es = (List<Employee>) employeeService
					.findEmployees(department.getDepartmentGuid());
			if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
				out.print(true);// 有无子节点 true/false
			} else {
				out.print(false); // 页面上显示部门节点都是有子节点的样子
			}

			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
//	/**
//	 * 遍历List，生成JSON字符串
//	 * @param deptsid 
//	 * @param userids 
//	 * 
//	 * @param rights
//	 * @throws IOException
//	 */
//	@SuppressWarnings("unchecked")
//	public void createJSON1(List departments, PrintWriter out, String deptsid, String userids)
//			throws IOException {
//		Department department;
//		// 生成JSON字符串
//		for (int i = 0; i < departments.size(); i++) {
//			
//			department = (Department) departments.get(i);
//			
//			
//
////			out.print(" {\"text\":\"");// 不显示链接的下划线
////			out.print(department.getDepartmentName());// 节点的名字
//			
//			out.print(" {\"text\":\"<a  id='"+department.getDepartmentGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;font-size:25px;' >");// 不显示链接的下划线
//
//			out.print(department.getDepartmentName());// 节点的名字
//
//			out.print("</a>\",");
//			out.print("\"id\":\"");
//			out.print(department.getDepartmentGuid());// 节点的id
//			// 目前要遍历查询部门表与人员表才能判断有无子节点
//			List<Department> ds = (List<Department>) departmentService
//					.queryDepartmentsBydepIds(department.getDepartmentGuid(),deptsid);
//			List<Employee> es = (List<Employee>) employeeService
//					.findEmployeesByIds(department.getDepartmentGuid(),userids);
//			if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
//				out.print("\",\"expanded\":");
//				out.print(true);// 默认是否是展开方式
//				out.println(",");
//				out.print("\"children\":");
//				out.println("[");
//				if(ds != null && ds.size() > 0){
//					createJSON1(ds,out,deptsid,userids);
//				}
//				if(es != null && es.size() > 0){
//					if(ds != null && ds.size() > 0){
//						out.println(",");
//					}
//					createJSON3(es,out);
//				}
//				out.println("]");
//				out.print(",\"classes\":\"");
//			} else {
//				out.print("\",\"classes\":\"");
//			}
//
//			out.print("folder");// 节点的样式
//			out.print("\"");
//			out.println("}");// 第一个节点结束
//			if (i != departments.size() - 1) {
//				out.println(",");
//			}
//		}
//	}

	/**
	 * 遍历List，生成JSON字符串
	 * 
	 * @param rights
	 * @throws IOException
	 */
	public void createJSON2(List<Employee> es, PrintWriter out)
			throws IOException {
		Employee e;
		// 生成人员的JSON字符串
		for (int i = 0; i < es.size(); i++) {
			e = (Employee) es.get(i);
			
//			out.print(" {\"text\":\"<a href='");
//			out.print("RightTree_toRightTree.do?employeeid="
//					+ e.getEmployeeGuid());// 每个节点上的链接
//
//			out.print("' onclick='check(this)' style='text-decoration:none;' >");// 不显示链接的下划线
			
			out.print(" {\"text\":\"<a id='"+e.getEmployeeGuid()+"' href='javascript:;' onclick='check(this,1)' style='text-decoration:none;' >");// 不显示链接的下划线

			out.print(e.getEmployeeName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");

			out.print(e.getEmployeeGuid());// 节点的id
			out.print("\",\"hasChildren\":");
			out.print(false);// 有无子节点 true/false
			out.print(",\"classes\":\"");
			out.print("file");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != es.size() - 1) {
				out.println(",");// 若不是最后一个节点则添加逗号
			}
		}
	}

	/**
	 * 遍历List，生成JSON字符串
	 * 
	 * @param rights
	 * @throws IOException
	 */
	public void createJSON3(List<Employee> es, PrintWriter out)
			throws IOException {
		Employee e;
		// 生成人员的JSON字符串
		for (int i = 0; i < es.size(); i++) {
			e = (Employee) es.get(i);
			
//			out.print(" {\"text\":\"<a href='");
//			out.print("RightTree_toRightTree.do?employeeid="
//					+ e.getEmployeeGuid());// 每个节点上的链接
//
//			out.print("' onclick='check(this)' style='text-decoration:none;' >");// 不显示链接的下划线
			
			out.print(" {\"text\":\"<input type='checkbox' onclick='djcheckbox(\\\""+e.getEmployeeGuid()+"\\\")' name='box' id='"+e.getEmployeeGuid().replace("{", "").replace("}", "")+"' value='"+e.getEmployeeGuid()+"'/><a id='"+e.getEmployeeGuid()+"' href='javascript:;' onclick='check(this,1)' style='text-decoration:none;font-size:25px;' >");// 不显示链接的下划线

			out.print(e.getEmployeeName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");

			out.print(e.getEmployeeGuid());// 节点的id
			out.print("\",\"hasChildren\":");
			out.print(false);// 有无子节点 true/false
//			out.print(",\"classes\":\"");
//			out.print("file");// 节点的样式
//			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != es.size() - 1) {
				out.println(",");// 若不是最后一个节点则添加逗号
			}
		}
	}

	
	/**
	 * 指向showRoleTree.jsp页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showDepartmentTree() {
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		getRequest().setAttribute("department_rootId", department_rootId);
//		return "showDepartmentTree";
		return "showDepartmentTreeView";
	}
	
	public String showDepartmentTree_allow() {
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		getRequest().setAttribute("department_rootId", department_rootId);
//		return "showDepartmentTree";
		return "showDepartmentTreeView_allow";
	}
	

	/**
	 * 指向角色树的frame页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toDepartmentTree() {
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		getRequest().setAttribute("department_rootId", department_rootId);
		return "toDepartmentTree";
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	
	public void syncGetAllEmployees(){
//		getResponse().setContentType("text/xml; charset=gb2312");
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String type=getRequest().getParameter("type");//0为部门 1为人员
				String id=getRequest().getParameter("id");
				String nodeId = getRequest().getParameter("nodeId");
				List<String> users = null;
				if(Utils.isNotNullOrEmpty(nodeId)){
					users = new ArrayList<String>();
					WfNode wfNode =  workflowBasicFlowService.findFormIdByNodeId(nodeId);
					String groupId = "";
					List<InnerUserMapEmployee> innerUserList = new ArrayList<InnerUserMapEmployee>();
					if (!("").equals(wfNode) && wfNode != null) {
						// 获取人员组的id
						groupId = wfNode.getWfn_staff();
						// 获取人员组的人员信息
						innerUserList = groupService.getListByInnerUserId(groupId,null,"");
					}
					if (innerUserList != null) {
						for (int i = 0; i < innerUserList.size(); i++) {
							InnerUserMapEmployee innerUserMapEmployee = innerUserList.get(i);
							users.add(innerUserMapEmployee.getEmployee_id());
						}
					}
				}
				
				List<Object[]> list=null;
				if (type!=null) {
					if (type.equals("0")) {//递归获取所有该部门下人员关联部门信息
						list=employeeService.getAllEmployeeInfoBySuperDepartmentId(id);
					}else if (type.equals("1")) {//人员关联部门信息
						list=employeeService.getEmployeeInfoByEmployeeId(id);
					}
				}
				List<Object[]> newList=new ArrayList<Object[]>();
				if(users!=null){
					for(int i=0;i<list.size();i++){
						String userid = (String) list.get(i)[0];
						boolean flag=false;
						for(int j=0;j<users.size();j++){
							if(userid.equals(users.get(j))){
								flag=true;
								break;
							}
						}
						if(flag){
							newList.add(list.get(i));
						}
					}
				}else{
					for(int i=0;i<list.size();i++){
						newList.add(list.get(i));
						
				}
				}
				Gson gson=new Gson();
				out.write(gson.toJson(newList));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
		
//		PrintWriter out = getResponse().getWriter();
//		String type=getRequest().getParameter("type");//0为部门 1为人员
//		String id=getRequest().getParameter("id");
//		List<Object[]> list=null;
//		if (type!=null) {
//			if (type.equals("0")) {//递归获取所有该部门下人员关联部门信息
//				list=employeeService.getAllEmployeeInfoBySuperDepartmentId(id);
//			}else if (type.equals("1")) {//人员关联部门信息
//				list=employeeService.getEmployeeInfoByEmployeeId(id);
//			}
//		}
//		Gson gson=new Gson();
//		out.write(gson.toJson(list));
//		out.close();
	}
	
	
	public void syncGetAllEmployees2(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String type=getRequest().getParameter("type");//0为部门 1为人员
				String id=getRequest().getParameter("id");
				String deptId= getRequest().getParameter("deptId");
				List<Object[]> list=null;
				if (type!=null) {
					if (type.equals("0")) {//获取该部门的信息
						Department department=departmentService.findDepartmentById(id);
						list = new ArrayList<Object[]>();
						Object[] obj = new Object[5];
						obj[0]=department.getDepartmentGuid();
						obj[1]=department.getDepartmentName();
						obj[2]="";
						obj[3]="";
						obj[4]=department.getDepartmentShortdn();
						list.add(obj);
					}else if (type.equals("1")) {//人员关联部门信息
						list=employeeService.getEmployeeInfoByEmployeeId(id);
						if(deptId!=null){
							Department dept = departmentService.findDepartmentById(deptId);
							if(list!= null && list.size()>0 &&dept!=null){
								if(!list.get(0)[2].equals(dept.getDepartmentGuid())){
									for(int t= 0; t<list.size();t++){
										list.get(t)[2] = dept.getDepartmentGuid();
												list.get(t)[3] = dept.getDepartmentName();
												list.get(t)[4] = dept.getDepartmentShortdn();
									}
								}
							}
						}
					}
				}
				Gson gson=new Gson();
				out.write(gson.toJson(list));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
		
	}
	
	public void syncGetAllEmployees1(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String userids=getRequest().getParameter("userList");
				String type=getRequest().getParameter("type");//0为部门 1为人员
				String id=getRequest().getParameter("id");
				String iscylxr=getRequest().getParameter("iscylxr");
				List<Object[]> list=null;
				if (type!=null) {
					if (type.equals("0")) {//递归获取所有该部门下人员关联部门信息
						//当iscylxr为1的时候获取常用联系人
						if(iscylxr!=null&&"1".equals(iscylxr)){
							list=employeeService.getEmployeeInfoByCylxrId(id);
						}else{
							list=employeeService.getAllEmployeeInfoBySuperDepartmentId(id,userids);
						}
					}else if (type.equals("1")) {//人员关联部门信息
						list=employeeService.getEmployeeInfoByEmployeeId(id);
					}
				}
				Gson gson=new Gson();
				out.write(gson.toJson(list));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
		
//		PrintWriter out = getResponse().getWriter();
//		String type=getRequest().getParameter("type");//0为部门 1为人员
//		String id=getRequest().getParameter("id");
//		List<Object[]> list=null;
//		if (type!=null) {
//			if (type.equals("0")) {//递归获取所有该部门下人员关联部门信息
//				list=employeeService.getAllEmployeeInfoBySuperDepartmentId(id);
//			}else if (type.equals("1")) {//人员关联部门信息
//				list=employeeService.getEmployeeInfoByEmployeeId(id);
//			}
//		}
//		Gson gson=new Gson();
//		out.write(gson.toJson(list));
//		out.close();
	}
	/**
	 * 
	 * @Title: validateLogin
	 * @Description: 登录验证 void    返回类型
	 */
	public void validateLogin(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String username=getRequest().getParameter("username");//登录名
				String password=getRequest().getParameter("password");//密码
				List<Employee> list=employeeService.getEmployeeByUsernameAndPassword(username, password);
				if (list==null||list.size()==0) {
					out.write("0");//登录失败
					out.close();
				}else{
					out.write("1");//登录成功
					out.close();
				}
				
				
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
	}
	/**
	 * 从根目录转至登陆页
	 * @return
	 */
	public String toLogin(){
		String adminDeptId = SystemParamConfigUtil.getParamValueByParam("superAdminDeptId");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(null != emp && StringUtils.isNotBlank(adminDeptId) && !emp.getDepartmentGuid().equals(adminDeptId)){
			return "fail2";
		}
		return "fail";
	}
	/**
	 * 单点登录直接跳过普通登录
	 * @return
	 */
	public String ssoLogin(){
		String adminDeptId = SystemParamConfigUtil.getParamValueByParam("superAdminDeptId");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(null != emp && StringUtils.isNotBlank(adminDeptId) && !emp.getDepartmentGuid().equals(adminDeptId)){
			return "fail2";
		}
		return "login1";
	}
	
	public String login4M(){
//		String username=getRequest().getParameter("username");//登录名
//		String password=getRequest().getParameter("password");//密码
//		List<Employee> list=employeeService.getEmployeeByUsernameAndPassword(username, password);
//		getSession().setAttribute(MyConstants.loginEmployee, list.get(0));
		if(!("1").equals(Constant.LICENSE_CHECK_PASSED)){
			try {
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			String username=getRequest().getParameter("username");//登录名
			String password=getRequest().getParameter("password");//密码
			List<Employee> list=employeeService.getEmployeeByUsernameAndPassword(username, password);
			Department department=null;
			if (list==null||list.size()==0) {
				getRequest().setAttribute("mes", "1");
				return "fail";
			}else{//登录成功
				//兼容单点登录写法，单点登录有数据就采用
				if (getSession().getAttribute(MyConstants.loginEmployee)==null) {
					String adminDeptId = SystemParamConfigUtil.getParamValueByParam("superAdminDeptId");
					if(StringUtils.isNotBlank(adminDeptId) && !list.get(0).getDepartmentGuid().equals(adminDeptId)){
						return "fail2";
					}
					//根据userid获取用户所属大部门信息
					department = departmentService.findDepartmentById(list.get(0).getDepartmentGuid());
					
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
					getSession().setAttribute(MyConstants.DEPARMENT_ID, depids);
					
					getSession().setAttribute(MyConstants.loginEmployee, list.get(0));
					getRequest().setAttribute("loginUsername", list.get(0).getEmployeeLoginname());
					//初始化人员大部门信息
					String department_shortdn = department.getDepartmentShortdn();
					String superior_guid = department.getSuperiorGuid();
					//查找最大的部门
					Department departmentBig = departmentService.findDepartmentById(superior_guid);
					if("1".equals(superior_guid) || departmentBig == null ){
						List<String> depIds = new ArrayList<String>();
						depIds.add(department.getDepartmentGuid());
						getSession().setAttribute(MyConstants.DEPARMENT_IDS, depIds);
						getSession().setAttribute(MyConstants.DEPARMENT_NAME, department.getDepartmentName());
					}else{
						Department dept = departmentService.findDepartmentById(superior_guid);
						String deptName = dept.getDepartmentName();
						if (!(department_shortdn == null || department_shortdn.length() < 1)) {
							if (department_shortdn.split(",").length == 1) {
								superior_guid = department.getDepartmentGuid();
								Department deptbak = departmentService.findDepartmentById(superior_guid);
								deptName = deptbak.getDepartmentName();
							}	
						}
						List<String> depIds = new ArrayList<String>();
						
						Collection<Department> depts =	departmentService.findDepartments(department.getDepartmentGuid());
						if(depts  == null||depts.size() ==0){
							depIds.add(superior_guid);
						}else{
							depIds.add(department.getDepartmentGuid());
						}
						
						getSession().setAttribute(MyConstants.DEPARMENT_IDS, depIds);
						getSession().setAttribute(MyConstants.DEPARMENT_NAME, deptName);
					}
					getSession().setAttribute(MyConstants.bigDepartmentId, superior_guid);//存储大部门信息
				}else{
					String adminDeptId = SystemParamConfigUtil.getParamValueByParam("superAdminDeptId");
					Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
					getRequest().setAttribute("loginUsername", emp.getEmployeeLoginname());
					if(null != emp && StringUtils.isNotBlank(adminDeptId) && !emp.getDepartmentGuid().equals(adminDeptId)){
						return "fail2";
					}
				}
			}
		}
		
		return "login";	
	}
	
	public void getNameById(){
		String id = getRequest().getParameter("id");
		String type = getRequest().getParameter("type");
		String name = "";
		if("1".equals(type)){
			List list = employeeService.getEmployeeInfoByEmployeeId(id);
			name = ((Object[])list.get(0))[1].toString();
		}else{
			name = departmentService.findDepartmentById(id).getDepartmentName();
		}
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
			out.write(name);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 系统欢迎页
	 **/
	public String toWelcomeJsp() {
		return "welcome";
	}
}
