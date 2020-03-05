package cn.com.trueway.workflow.set.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.WfMainRole;
/**
 * 人员管理的Service层接口
 * 
 * @author liwei
 * 
 */
public interface EmployeeRoleService {
	
	public List<EmployeeRole> getEmployeeRoleList(Map<String,String> searchMap,Integer pageIndex,Integer pageSize);
	
	public int getEmployeeRoleCount(Map<String,String> searchMap);
	
	public void saveEmployeeRole(EmployeeRole employeeRole);
	
	public void updateEmployeeRole(EmployeeRole employeeRole);
	
	public void deleteEmployeeRole(EmployeeRole employeeRole);
	
	public EmployeeRole getEmployeeRoleById(String id);
	
	public int getEmployeeRoleByMc(String mc, String userid);
	/**
	 * 
	 * 描述：根据角色id查询 工作流与流程角色关系
	 * @param roleId
	 * @return List<WfMainRole>
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午5:37:16
	 */
	public List<WfMainRole> findWfMainRoleByRoleId(String roleId);
	/**
	 * 
	 * 描述：保存工作流角色关系
	 * @param roleId
	 * @param wfMainIds
	 * @return JSONObject
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午6:03:57
	 */
	public JSONObject saveWfMainRole(String roleId, String wfMainIds);
	
	public List<EmployeeRole> findEmployeeRoleById(String employeeId);
	
	/**
	 * 
	 * 描述：获取全部的角色工作流表
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2014-8-7 上午8:40:37
	 */
	public int findAllWfMainRoleCount();

}
