package cn.com.trueway.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.Menu;

public interface MenuDao {
	
	List<Menu> findChildMenu(String menuFatherId, String rank, String siteId);

	Menu findMenuById(String id);
	
	void updateMenu(Menu menu) throws Exception;

	void saveMenu(Menu menu) throws Exception;
	
	List<Menu> getMenuListByUserId(String menuId, String userId,String siteId);
	
	List<Menu> getAllMenuList();
	
	List<Menu> findMenuList(String rank) throws Exception;
	
	List<Menu> selectMenuList(String menuName) throws Exception;
	
	public void delete(String menuId) throws DataAccessException;
	
	public int getCountBySql(Map<String, String> map);
	
	public List<Object[]> getSites();

	List<Menu> findMenuListNew(String string, String siteId);

	List<Menu> selectMenuListNew(String menuName, String siteId);

	List<Menu> findChildMenuNew(String menuId, String string, String siteId);

	List<Menu> findFamilyMenuByFid(String menuId);

	void deleteNew(String menuId);

	void deleteMenuRole(String menuId);
}
