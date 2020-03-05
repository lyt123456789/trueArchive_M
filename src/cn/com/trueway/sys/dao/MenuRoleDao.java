package cn.com.trueway.sys.dao;

import java.util.List;

import cn.com.trueway.sys.pojo.MenuRoleShip;

public interface MenuRoleDao {
	
	List<MenuRoleShip> findRoleMenuShipList(String roleId);
	
	void saveMenuRole(MenuRoleShip menuRoleShip) throws Exception;
	
	void deleteMenuRoleList(String roleId, String rank) throws Exception;

	void deleteMenuRole(String roleId, String menuId) throws Exception;
}
