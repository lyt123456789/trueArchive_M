package cn.com.trueway.sys.dao.impl;

import java.util.List;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.sys.dao.MenuRoleDao;
import cn.com.trueway.sys.pojo.MenuRoleShip;

public class MenuRoleDaoImpl extends BaseDao implements MenuRoleDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuRoleShip> findRoleMenuShipList(String roleId) {
		String sql = "select t.* from t_sys_role_menu t where t.role_id='"+roleId+"'";
		return getEm().createNativeQuery(sql, MenuRoleShip.class).getResultList();
	}

	@Override
	public void saveMenuRole(MenuRoleShip menuRoleShip) throws Exception{
		getEm().persist(menuRoleShip);
	}

	@Override
	public void deleteMenuRoleList(String roleId, String rank) throws Exception {
		String sql = "delete from t_sys_role_menu t where t.role_id='"+roleId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void deleteMenuRole(String roleId, String menuId) throws Exception {
		String sql = "delete from t_sys_role_menu t where t.role_id='"+roleId+"' and t.menu_id='"+menuId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}
}