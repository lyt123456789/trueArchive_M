package cn.com.trueway.sys.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.service.RoleService;
import cn.com.trueway.sys.service.RoleUserService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;

/**
 * ClassName: RoleUserAction <br/>
 * Function: TODO . <br/>
 * date: 2016年5月5日 下午2:55:22 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
public class RoleUserAction extends BaseAction{
	
	private static final long serialVersionUID = 5719306665126784445L;
	
	private RoleService roleService;
	
	private RoleUserService roleUserService;
	
	private EmployeeService employeeService;
	
	private DepartmentService departmentService;
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RoleUserService getRoleUserService() {
		return roleUserService;
	}
	
	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**
	 * 
	 * 描述：获取角色下绑定的人员信息
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-14 下午06:36:57
	 */
	public String getUserList(){
		try {
			String roleId = getRequest().getParameter("roleId");
			String name = getRequest().getParameter("itemName");
			List<RoleUser> list = roleUserService.queryRoleUserById(roleId, name);
			getRequest().setAttribute("roleId", roleId);
			getRequest().setAttribute("roleUserList", list);
			getRequest().setAttribute("itemName", name);
			
			return "userList";
		} catch (Exception e) {
			e.printStackTrace();
			return "userList";
		}
	}
	
	/** 
	 * toAddUserToRole:(给角色添加人员数据初始化). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public String toAddUserToRole() throws UnsupportedEncodingException{
		String nameType = this.getRequest().getParameter("nameType");
		String idType = this.getRequest().getParameter("idType");
		String value = this.getRequest().getParameter("value");
		String roleId = this.getRequest().getParameter("roleId");
		if(value!=null&&!"".equals(value)){
			value =URLDecoder.decode(value,"utf-8");
		}
		this.getRequest().setAttribute("value", value);
		this.getRequest().setAttribute("idType", idType);
		this.getRequest().setAttribute("nameType", nameType);
		this.getRequest().setAttribute("roleId", roleId);
		String isowner = getRequest().getParameter("isowner");
		getRequest().setAttribute("isowner", isowner);
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		this.getRequest().setAttribute("department_rootId", department_rootId);
		return "addUserToRole";
	}
	
	/** 
	 * addUserToRole:(添加人员角色关联关系). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void addUserToRole(){
		try {
			String roleId = this.getRequest().getParameter("roleId");
			Role role = roleService.findRoleById(roleId);
			String roleName = "";
			if (null != role) {
				roleName = role.getRoleName();
			}
			String userId = this.getRequest().getParameter("userId");
			if (StringUtils.isNotBlank(userId)) {
				String[] userIds = userId.split(";");
				for (String str : userIds) {
					RoleUser roleUser = roleUserService.queryRoleUser(roleId, str);
					if (null != roleUser) {
						continue;
					}else{
						String deptName = "";
						String deptId = "";
						String empName = "";
						Employee employee = employeeService.queryEmployee(str);
						if (null != employee) {
							deptId = employee.getDepartmentGuid();
							empName = employee.getEmployeeName();
							Department department = departmentService.findDepartmentById(employee.getDepartmentGuid());
							if (null != department) {
								deptName = department.getDepartmentName();
							}
						}
						RoleUser roleUser2 = new RoleUser();
						roleUser2.setDeptId(deptId);
						roleUser2.setDeptName(deptName);
						roleUser2.setRoleId(roleId);
						roleUser2.setRoleName(roleName);
						roleUser2.setUserId(str);
						roleUser2.setUserName(empName);
						String result = roleUserService.saveRoleUser(roleUser2);
						toPage(result);
					}
				}
			}else{
				toPage("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * deleteRoleUser:(删除角色用户关联关系). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteRoleUser(){
		String id = this.getRequest().getParameter("id");
		try {
			if (StringUtils.isNotBlank(id)) {
				String ids [] = id.split(",");
				for (String str : ids) {
					this.roleUserService.deleteRoleUser(str);
				}
				toPage("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			toPage("fail");
		}
	}
	
	public String getUserList4Super(){
		try {
			String roleId = getRequest().getParameter("roleId");
			String name = getRequest().getParameter("itemName");
			List<RoleUser> list = roleUserService.queryRoleUserById(roleId, name);
			getRequest().setAttribute("roleId", roleId);
			getRequest().setAttribute("roleUserList", list);
			getRequest().setAttribute("itemName", name);
			
			return "userList4Super";
		} catch (Exception e) {
			e.printStackTrace();
			return "userList4Super";
		}
	}
}
