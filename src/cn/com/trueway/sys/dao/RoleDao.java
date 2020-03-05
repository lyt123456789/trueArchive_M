package cn.com.trueway.sys.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.manage.pojo.BtnDictionary;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.vo.RoleView;

public interface RoleDao {
	
	/** 
	 * findRoleCount:(统计角色数量). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	int findRoleCount( Map<String,String> map );
	
	/** 
	 * findRoleList:(获取角色列表). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	List<Role> findRoleList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	/** 
	 * findRoleById:(根据主键id查找角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	Role findRoleById(String id);

	/** 
	 * saveRole:(保存角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	void saveRole(Role role) throws Exception;
	
	/** 
	 * updateRole:(更新角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	void updateRole(Role role) throws Exception;
	
	/** 
	 * deleteRole:(删除角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteRole(String id) throws Exception;
	
	public List<Object[]> getSites();

	void deleteRoleUser(String roleId);

	void deleteRoleMenu(String roleId);

	public List<RoleView> findRoleViewList(Map<String,String> map, Integer pageIndex, Integer pageSize);

	List<BtnDictionary> findBtnList(Map<String, String> map, Integer pageIndex, Integer pageSize);
}
