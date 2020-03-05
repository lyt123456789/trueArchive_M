package cn.com.trueway.workflow.set.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.WfMainRole;

/**
 * 人员管理的Dao层接口
 * 
 * @author liwei
 * 
 */
public interface EmployeeRoleDao {
	
	public List<EmployeeRole> getEmployeeRoleList(Map<String,String> searchMap,Integer pageIndex,Integer pageSize);
	
	public int getEmployeeRoleCount(Map<String,String> searchMap);
	
	public void saveEmployeeRole(EmployeeRole employeeRole);
	
	public void updateEmployeeRole(EmployeeRole employeeRole);
	
	public void deleteEmployeeRole(EmployeeRole employeeRole);
	
	public EmployeeRole getEmployeeRoleById(String id);
	
	public int getEmployeeRoleByMc(String mc, String userid) ;
	
	/**
	 * 
	 * 描述：根据角色id查询 工作流与流程角色关系
	 * @param roleId
	 * @return List<WfMainRole>
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午5:37:16
	 */
	public List<WfMainRole> findWfMainRoleByRoleId(String roleId) throws Exception;
	
	/**
	 * 
	 * 描述：删除某角色的全部工作流权限
	 * @param roleId void
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午6:05:42
	 */
	void deleteWfMainRole(String roleId) throws Exception;
	
	/**
	 * 新增工作流权限
	 * 描述：TODO 对此方法进行描述
	 * @param wfMainRole void
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午6:06:02
	 */
	void saveWfMainRole(WfMainRole wfMainRole) throws Exception;
	
	
	public List<EmployeeRole> findEmployeeRoleById(String employeeId) throws Exception;
	
	
	/**
	 * 
	 * 描述：获取全部的角色工作流表
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2014-8-7 上午8:40:37
	 */
	public int findAllWfMainRoleCount() throws Exception;

	

}
