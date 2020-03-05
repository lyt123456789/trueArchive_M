package cn.com.trueway.sys.service;

import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.sys.pojo.MenuRoleShip;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface MenuRoleService {
	
	List<MenuRoleShip> findRoleMenuShipList(String roleId);
	
	JSONObject saveMenuRole(MenuRoleShip menuRoleShip, Employee emp);
	
	JSONObject saveAllMenuRole(String roleId, Employee emp);
	
	JSONObject deleteMenuRole(String roleId, String menuId);
	
	JSONObject deleteAllMenuRole(String roleId);

	JSONObject deleteAllMenuRoleNew(String roleId, Employee emp);
}
