package cn.com.trueway.workflow.set.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.service.EmployeeRoleService;

/**
 * 用户角色操作表
 * @author caiyj
 *
 */
public class EmployeeRoleAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private EmployeeRoleService employeeRoleService;
	
	private EmployeeRole employeeRole;
	
	public EmployeeRoleService getEmployeeRoleService() {
		return employeeRoleService;
	}

	public void setEmployeeRoleService(EmployeeRoleService employeeRoleService) {
		this.employeeRoleService = employeeRoleService;
	}

	public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getRoleList(){
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> searchMap = new HashMap<String,String>();
		int count = employeeRoleService.getEmployeeRoleCount(searchMap);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<EmployeeRole> list = employeeRoleService.getEmployeeRoleList(searchMap, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "roleList";
	}
	
	public String toadd(){
		return "toadd";
	}
	
	public String toupdate(){
		String id = getRequest().getParameter("id");
		EmployeeRole employeeRole = employeeRoleService.getEmployeeRoleById(id);
		getRequest().setAttribute("employeeRole", employeeRole);
		String userid = employeeRole.getUserIds();
		List<InnerUserMapEmployee>  mapList = new ArrayList<InnerUserMapEmployee>();
		InnerUserMapEmployee innerUserMapEmployee = null;
		if(userid!=null && !userid.equals("")){
			String[] users = userid.split("#");
			for(String user : users){
				String[] userInfo = user.split("[|]");
				innerUserMapEmployee = new InnerUserMapEmployee(); 
				innerUserMapEmployee.setEmployee_id(userInfo[0]);
				innerUserMapEmployee.setEmployee_name(userInfo[1]);
				innerUserMapEmployee.setEmployee_shortdn(userInfo[2]);
				mapList.add(innerUserMapEmployee);
			}
		}
		getRequest().setAttribute("mapList", mapList);
		return "toupdate";
	}
	
	public String addEmployeeRole(){
		String employeeinfo = getRequest().getParameter("employeeinfo");
		employeeRole.setUserIds(employeeinfo);
		employeeRoleService.saveEmployeeRole(employeeRole);
		return getRoleList();
	}
	public String updateEmployeeRole(){
		String employeeinfo = getRequest().getParameter("employeeinfo");
		employeeRole.setUserIds(employeeinfo);
		employeeRoleService.updateEmployeeRole(employeeRole);
		return getRoleList();
	}
	
	
	public void deleteEmployeeRole(){
		String ids = getRequest().getParameter("ids");
		if(ids!=null && !ids.equals("")){
			String[] id = ids.split(",");
			for(String roleId: id){
				employeeRole = new EmployeeRole();
				employeeRole.setId(roleId);
				employeeRoleService.deleteEmployeeRole(employeeRole);
			}
		}
		try {
			getResponse().getWriter().print("success");
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * 描述：保存角色工作流关系
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午6:15:54
	 */
	public void saveWfMainRole(){
		String roleId = getRequest().getParameter("roleId");
		String wfMainIds = getRequest().getParameter("ids");
		JSONObject error = null;
		error = employeeRoleService.saveWfMainRole(roleId, wfMainIds);
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			out = getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
}
