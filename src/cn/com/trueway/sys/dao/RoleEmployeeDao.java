package cn.com.trueway.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.RoleUser;


/**
 * ClassName: RoleEmployeeDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年4月22日 下午5:30:54 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
public interface RoleEmployeeDao {

	/** 
	 * queryRoleEmployeesByRole:(根据webId查询"信息审核管理员"的角色人员关联关系). <br/> 
	 * 
	 * @author 
	 * @update adolph.jiang  
	 * @return List<RoleEmployee>
	 * @since JDK 1.6 
	 */ 
	public List<RoleUser> queryRoleEmployeesByRole()throws DataAccessException;
	
	/** 
	 * queryRoleEmployeesByUserId:(根据人员id查询角色人员关联关系). <br/> 
	 * 
	 * @author 
	 * @update adolph.jiang  
	 * @return List<RoleEmployee>
	 * @since JDK 1.6 
	 */ 
	public List<RoleUser> queryRoleEmployeesByUserId(String userId)throws DataAccessException;

}
