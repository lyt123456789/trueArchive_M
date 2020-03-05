package cn.com.trueway.sys.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.trueway.archives.manage.pojo.BtnDictionary;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.vo.RoleView;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface RoleService {
	
	int findRoleCount( Map<String,String> map );
	
	List<Role> findRoleList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	Role findRoleById(String id);

	JSONObject saveRole(Role role, Employee emp);
	
	JSONObject updateRole(Role role, Employee emp);

	String deleteRole(String id, Employee emp);
	
	public List<String[]> getSites();

	String deleteRoleNew(String roleId, Employee emp);

	public List<RoleView> findRoleViewList(Map<String,String> map, Integer pageIndex, Integer pageSize);

	List<BtnDictionary> findBtnList(Map<String, String> map,Integer pageIndex, Integer pageSize);
}
