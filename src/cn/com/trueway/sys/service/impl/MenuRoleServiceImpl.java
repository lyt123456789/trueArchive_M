package cn.com.trueway.sys.service.impl;

import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.sys.dao.MenuDao;
import cn.com.trueway.sys.dao.MenuRoleDao;
import cn.com.trueway.sys.dao.RoleDao;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.sys.pojo.MenuRoleShip;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.service.MenuRoleService;
import cn.com.trueway.workflow.core.pojo.Employee;

public class MenuRoleServiceImpl implements MenuRoleService{
	
	private MenuRoleDao menuRoleDao;
	
	private MenuDao menuDao;
	
	private RoleDao roleDao;

	public MenuRoleDao getMenuRoleDao() {
		return menuRoleDao;
	}

	public void setMenuRoleDao(MenuRoleDao menuRoleDao) {
		this.menuRoleDao = menuRoleDao;
	}
	
	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<MenuRoleShip> findRoleMenuShipList(String roleId) {
		return menuRoleDao.findRoleMenuShipList(roleId);
	}

	@Override
	public JSONObject saveMenuRole(MenuRoleShip menuRoleShip, Employee emp) {
		JSONObject result = new JSONObject();
		try {
			menuRoleDao.saveMenuRole(menuRoleShip);
			result.put("success", true);
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result.put("success", false);
		}
		return result;
	}

	@Override
	public JSONObject saveAllMenuRole(String roleId, Employee emp) {
		JSONObject result = new JSONObject();
		try {
//			// 获取所有菜单信息
			List<Menu> sysMenus = menuDao.findMenuList("0");
			// 获取所有菜单信息,根据站点
			String siteId = emp.getSiteId();
			//List<Menu> sysMenus = menuDao.findMenuListNew("0",siteId);
			// 获取角色信息
			Role sysRole = roleDao.findRoleById(roleId);
			//获取已入库的角色菜单权限
			//List<MenuRoleShip> roleMenus = menuRoleDao.findRoleMenuShipList(roleId);
			
			//menuRoleDao.deleteMenuRoleList(roleId, "0");
			if (sysMenus != null && sysMenus.size() > 0) {
				for (int i = 0; i < sysMenus.size(); i++) {
					Menu sysMenu = sysMenus.get(i);
					menuRoleDao.deleteMenuRole(roleId, sysMenu.getMenuId());
					MenuRoleShip sysRoleMenu = new MenuRoleShip();
					sysRoleMenu.setRoleId(roleId);
					sysRoleMenu.setMenuId(sysMenu.getMenuId());
					sysRoleMenu.setRoleName(sysRole.getRoleName());
					menuRoleDao.saveMenuRole(sysRoleMenu);
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public JSONObject deleteMenuRole(String roleId, String menuId) {
		JSONObject result = new JSONObject();
		try {
			menuRoleDao.deleteMenuRole(roleId, menuId);
			result.put("success", true);
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result.put("success", false);
		}
		return result;
	}

	@Override
	public JSONObject deleteAllMenuRole(String roleId) {
		JSONObject result = new JSONObject();
		try {
			List<Menu> sysMenus = menuDao.findMenuList("0");
			if (sysMenus != null && sysMenus.size() > 0) {
				for (int i = 0; i < sysMenus.size(); i++) {
					Menu sysMenu = sysMenus.get(i);
					menuRoleDao.deleteMenuRole(roleId, sysMenu.getMenuId());
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result.put("success", false);
		}
		return result;
	}

	@Override
	public JSONObject deleteAllMenuRoleNew(String roleId, Employee emp) {
		JSONObject result = new JSONObject();
		try {
			String siteId = emp.getSiteId();
			//List<Menu> sysMenus = menuDao.findMenuListNew("0",siteId);
			List<Menu> sysMenus = menuDao.findMenuList("0");
			if (sysMenus != null && sysMenus.size() > 0) {
				for (int i = 0; i < sysMenus.size(); i++) {
					Menu sysMenu = sysMenus.get(i);
					menuRoleDao.deleteMenuRole(roleId, sysMenu.getMenuId());
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result.put("success", false);
		}
		return result;
	}
}
