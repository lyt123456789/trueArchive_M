package cn.com.trueway.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.trueway.sys.dao.MenuDao;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.sys.service.MenuService;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class MenuServiceImpl implements MenuService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public Menu findMenuById(String id) {
		return menuDao.findMenuById(id);
	}

	@Override
	public List<Menu> findMenuList(String menuId, String siteId) {
		List<Menu> list = new ArrayList<Menu>();
		try {
			// 获取子节点有该查询部门根节点
			List<Menu> childSysMenus = menuDao.findChildMenu(menuId, "%%", siteId);
			// 根据主节点一层一层获取子节点
			this.getMenu(list, childSysMenus, siteId);
		} catch (Exception e) {
			logger.error("分页查询菜单出错;异常信息：" + e.getMessage());
		}
		return list;
	}
	
	public List<Menu> findMenuListNew(String menuId, String siteId) {
		List<Menu> list = new ArrayList<Menu>();
		try {
			// 获取子节点有该查询部门根节点
			List<Menu> childSysMenus = menuDao.findChildMenuNew(menuId, "%%", siteId);
			// 根据主节点一层一层获取子节点
			this.getMenu(list, childSysMenus, siteId);
		} catch (Exception e) {
			logger.error("分页查询菜单出错;异常信息：" + e.getMessage());
		}
		return list;
	}
	
	private void getMenu(List<Menu> newList, List<Menu> oldList, String siteId) throws Exception {
		for (int i = 0; i < oldList.size(); i++) {
			Menu sysMenu = oldList.get(i);
			newList.add(sysMenu);
			List<Menu> childSysMenus = menuDao.findChildMenu(sysMenu.getMenuId(), "%%", siteId);
			if (childSysMenus != null && childSysMenus.size() > 0) {
				sysMenu.setHavaChild(true);
				getMenu(newList, childSysMenus , siteId);
			} else {
				sysMenu.setHavaChild(false);
			}
		}
	}

	@Override
	public JSONObject saveMenu(Menu menu) {
		JSONObject result = new JSONObject();
		try {
			menuDao.saveMenu(menu);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("error", "修改菜单失败");
		}
		return result;
	}

	@Override
	public JSONObject updateMenu(Menu menu) {
		JSONObject result = new JSONObject();
		try {
			menuDao.updateMenu(menu);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("error", "修改菜单失败");
		}
		return result;
	}

	
	@Override
	public List<Menu> getMenuListByUserId(String menuId, String userId,
			String siteId) {
		return menuDao.getMenuListByUserId(menuId, userId, siteId);
	}
	
	@Override
	public List<Menu> getAllMenuList(){
		return menuDao.getAllMenuList();
	}

	@Override
	public List<Menu> findMenuByName(String menuName) throws Exception {
		return menuDao.selectMenuList(menuName);
	}

	@Override
	public JSONObject delete(String menuId) {
		JSONObject result = new JSONObject();
		try {
			menuDao.delete(menuId);
			result.put("success", true);
		} catch (DataAccessException e) {
			result.put("success", false);
			result.put("error", "修改菜单失败");
		}
		return result;
	}

	@Override
	public int getCountBySql(Map<String, String> map){
		return menuDao.getCountBySql(map);
	}

	@Override
	public List<String[]> getSites() {
		List<Object[]> os = menuDao.getSites();
		if(!os.isEmpty()){
			List<String[]> rs = new ArrayList<String[]>();
			for(Object[] o :os){
				String[] s = {(String) o[0],(String) o[1]};
				rs.add(s);
			}
			return rs;
		}else{
			return null;
		}
	}

	@Override
	public List<Menu> findMenuByNameNew(String menuName, String siteId) {
		return menuDao.selectMenuListNew(menuName,siteId);
	}

	@Override
	public List<Menu> findFamilyMenuByFid(String menuId) {
		return menuDao.findFamilyMenuByFid(menuId);
	}

	@Override
	public JSONObject deleteNew(String menuId) {
		JSONObject result = new JSONObject();
		try {
			menuDao.deleteNew(menuId);
			menuDao.deleteMenuRole(menuId);
			result.put("success", true);
		} catch (DataAccessException e) {
			result.put("success", false);
			result.put("error", "修改菜单失败");
		}
		return result; 
	}
}
