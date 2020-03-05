package cn.com.trueway.workflow.set.dao;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeLeaderShip;

public interface EmployeeLeaderDao {
	
	/**
	 * 
	 * 描述：获取部门领导机构数
	 * @param leaderName
	 * @param employeeId
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2015-1-13 下午3:23:58
	 */
	int findDepartmentLeaderCount(String leaderName, String employeeName);
	
	/**
	 * 
	 * 描述：获取部门机构list
	 * @param leaderName
	 * @param employeeId
	 * @param pageIndex
	 * @param pageSize
	 * @return List<DepartmentLeader>
	 * 作者:蔡亚军
	 * 创建时间:2015-1-13 下午3:24:17
	 */
	List<DepartmentLeader> findDepartmentLeaderList(String leaderName, String employeeName,
				Integer pageIndex, Integer pageSize);
	
	DepartmentLeader saveDepartmentLeader(DepartmentLeader leader)  throws Exception;
	
	void  saveEmployeeLeaderShip(EmployeeLeaderShip employeeLeaderShip) throws Exception;
	
	void deleteDepartmentLeader(String id) throws Exception;
	
	void deleteEmployeeLeaderShip(String depLeaderId) throws Exception; 
	
	
	DepartmentLeader findDepartmentLeaderById(String id);
	
	public List<EmployeeLeaderShip> findEmployeeLeaderShipList(String depLeaderId);
	
	void updateDepartmentLeader(DepartmentLeader leader)  throws Exception;
	
	DepartmentLeader findDepartmentLeaderByEmpId(String employeeId);
	
	/**
	 * 
	 * 描述：根据机构查询各个领导人
	 * @param depId
	 * @return List<DepartmentLeader>
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午11:49:34
	 */
	List<DepartmentLeader> findDepartmentLeaderByDepId(String depId);

	DepartmentLeader findChuzhangByEmpId(String employeeId, String conditionSql);

}
