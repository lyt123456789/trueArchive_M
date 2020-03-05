package cn.com.trueway.sys.service;

import java.util.List;

import cn.com.trueway.sys.pojo.RoleUser;

/**
 * ClassName: RoleUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年5月5日 下午2:50:50 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
public interface RoleUserService {
	
	/** 
	 * saveRoleUser:(添加方法描述). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6
	 */ 
	public String saveRoleUser(RoleUser roleUser);
	
	/** 
	 * selectRoleUserById:(根据角色id获取人员信息列表). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<RoleUser> queryRoleUserById(String roleId, String name) throws Exception;
	
	/** 
	 * selectRoleUser:(根据人员id和角色id获取人员信息). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public RoleUser queryRoleUser(String roleId, String userId) throws Exception;
	
	/** 
	 * deleteRoleUser:(删除角色用户关联关系). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public String deleteRoleUser(String id) throws Exception;
}
