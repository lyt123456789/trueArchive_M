package cn.com.trueway.sys.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.trueway.sys.pojo.Menu;

public interface MenuService {
	
	List<Menu> findMenuList(String menuId, String siteId);

	Menu findMenuById(String id);
	
	JSONObject updateMenu(Menu sysMenu);

	JSONObject saveMenu(Menu sysMenu);
	
	List<Menu> getMenuListByUserId(String menuId, String userId,String siteId);
	
	List<Menu> getAllMenuList();
	
	public List<Menu> findMenuByName(String menuName) throws Exception;
	
	public JSONObject delete(String menuId);
	
	public int getCountBySql(Map<String, String> map);
	
	public List<String[]> getSites();

	List<Menu> findMenuByNameNew(String menuName, String siteId);

	List<Menu> findMenuListNew(String menuId, String siteId);

	List<Menu> findFamilyMenuByFid(String menuId);

	JSONObject deleteNew(String menuId);
	
}
