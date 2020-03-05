package cn.com.trueway.workflow.set.action;

import java.util.List;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.Allow;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.service.AllowService;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.GroupService;


/**
 * @ClassName: cn.com.zwkj.allow.action.AllowAction.java
 * @Description: (这里用一句话描述这个类的作用)
 * @author 潘浩
 * @date 2013-4-18 下午03:27:28
 *
 */
public class AllowAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private AllowService allowService;
	private String allowType="";//许可类型
	private String glid="";//许可类型id
	private Allow allow;
	private DepartmentService departmentService;
	private EmployeeService employeeService;
	private GroupService groupService;
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
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

	public String getGlid() {
		return glid;
	}

	public void setGlid(String glid) {
		this.glid = glid;
	}

	public Allow getAllow() {
		return allow;
	}

	public void setAllow(Allow allow) {
		this.allow = allow;
	}

	public String getAllowType() {
		return allowType;
	}

	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	public AllowService getAllowService() {
		return allowService;
	}

	public void setAllowService(AllowService allowService) {
		this.allowService = allowService;
	}
	/**
	 * 
	 * @Title: getAllowTypeList
	 * @Description: 转向许可类型列表
	 * @return String    返回类型
	 */
	public String getAllowTypeList(){
		getRequest().setAttribute("allowType", allowType);
		getRequest().setAttribute("glid", glid);
		
		String workflow_id=getSession().getAttribute(MyConstants.workflow_session_id)==null?null:getSession().getAttribute(MyConstants.workflow_session_id).toString();
		
		if (allowType.equals("0")) {//0工作流许可  
			
			
			return "getAllowTypeList_0";
		}else if (allowType.equals("1")) {//1任务许可
			List<WfNode> nodes=workflowBasicFlowService.getNodesByPid(workflow_id);
			getRequest().setAttribute("list", nodes);
			return "getAllowTypeList_1";
		}else if (allowType.equals("2")) {//2审批意见许可
			
			
			return "getAllowTypeList_2";
		}
		return "error";
	}
	
	/**
	 * @Title: getAllowList
	 * @Description: 转向许可列表
	 * @return String    返回类型
	 
	 */
	public String getAllowList(){
		getRequest().setAttribute("allowType", allowType);
		getRequest().setAttribute("glid", glid);
		
		String workflow_id=getSession().getAttribute(MyConstants.workflow_session_id)==null?null:getSession().getAttribute(MyConstants.workflow_session_id).toString();
		Allow a=new Allow();//查询实体
		a.setWorkflowid(workflow_id);
		a.setAllow_type(allowType);
		a.setGlid(glid);
		List<Allow> list=allowService.getAllAllowListByParam(a, null, null,null);
		getRequest().setAttribute("list", list);
		
		if (allowType.equals("0")) {//0工作流许可  
			return "getAllowList_0";
		}else if (allowType.equals("1")) {//1任务许可
			return "getAllowList_0";
		}else if (allowType.equals("2")) {//2审批意见许可
			return "getAllowList_2";
		}
		return "error";
	}
	/**
	 * @Title: toAddAllowJsp
	 * @Description: 转向新增许可页面
	 * @return String    返回类型
	 
	 */
	public String toAddAllowJsp(){
		getRequest().setAttribute("allowType", allowType);
		getRequest().setAttribute("glid", glid);
		
		
		return "toAddAllowJsp";
	}
	
	/**
	 * @Title: addAllow
	 * @Description: 新增许可
	 * @return String    返回类型
	 
	 */
	public String addAllow(){
		getRequest().setAttribute("allowType", allowType);
		getRequest().setAttribute("glid", glid);
		
		String workflow_id=getSession().getAttribute(MyConstants.workflow_session_id)==null?null:getSession().getAttribute(MyConstants.workflow_session_id).toString();
		
		String roleid=getRequest().getParameter("roleid");
		String role_type=getRequest().getParameter("role_type");
		String[] role_types=role_type.split(",");
		
		String role_name=null;
		if (role_types[0].equals("6")) {//人员
			Employee e=employeeService.findEmployeeById(roleid);
			if (e!=null) {
				role_name=e.getEmployeeName();
			}
			
		}else if (role_types[0].equals("7")||role_types[0].equals("8")){//部门
			Department d=departmentService.findDepartmentById(roleid);
			if (d!=null) {
				role_name=d.getDepartmentName();
			}
		}else{//流程组
			InnerUser i=groupService.getOneInnerUserById(roleid);
			if (i!=null) {
				role_name=i.getName();
			}
		}
		Allow allow1=new Allow();//查询实体
		allow1.setWorkflowid(workflow_id);
		allow1.setGlid(glid);
		allow1.setRole_id(roleid);
		List<Allow> list=allowService.getAllAllowListByParam(allow1, null, null, null);
		
		if (list==null||list.size()==0) {//录入去重
			Allow a=new Allow();
			a.setAllow_type(allowType);
			a.setGlid(glid);
			a.setRole_type(role_types[0]);
			a.setRole_typename(role_types[1]);
			a.setRole_name(role_name);
			a.setRole_id(roleid);
			a.setIntime(CommonUtil.getNowTimeTimestamp(null));
			a.setWorkflowid(workflow_id);
//			a.setInperson();//TODO
			allowService.addAllow(a);
		}
		
		
		
		
		
		
		return getAllowList();
	}
	
	/**
	 * @Title: deleteAllow
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return String    返回类型
	 
	 */
	public String deleteAllow(){
		getRequest().setAttribute("allowType", allowType);
		getRequest().setAttribute("glid", glid);
		
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				Allow a=allowService.getOneAllowById(idsArr[i]);
				if (a!=null) {
					//删除表单信息
					allowService.deleteAllow(a);
				}
			}
		}
		return getAllowList();
	}
	
	
}
