package cn.com.trueway.sys.dao;

import java.util.List;

import cn.com.trueway.sys.pojo.RoleUser;

/**
 * ClassName: RoleUserDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年5月5日 下午2:45:30 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
public interface RoleUserDao {
	
	/** 
	 * insertRoleUser:(插入角色人员关联关系). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void insertRoleUser(RoleUser roleUser) throws Exception;
	
	/** 
	 * selectRoleUserById:(根据角色id获取人员信息列表). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<RoleUser> selectRoleUserById(String roleId, String name) throws Exception;
	
	/** 
	 * selectRoleUser:(根据人员id和角色id获取人员信息). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public RoleUser selectRoleUser(String roleId, String userId) throws Exception;
	
	/** 
	 * deleteRoleUser:(删除角色人员关联关系). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteRoleUser(String id) throws Exception;
}
