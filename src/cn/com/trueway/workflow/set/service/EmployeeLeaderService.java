package cn.com.trueway.workflow.set.service;

import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeLeaderShip;

public interface EmployeeLeaderService {
	
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
	
	/**
	 * 
	 * 描述：新增保存
	 * @param leader
	 * @return JSONObject
	 * 作者:蔡亚军
	 * 创建时间:2015-1-13 下午3:26:27
	 */
	JSONObject saveDepartmentLeader(String depId, String depName, 
				String leaderId, String leaderName, String sortId, String employeeinfo, String empType);
	
	/**
	 * 
	 * 描述：删除领导部门
	 * @param ids
	 * @return JSONObject
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午9:01:11
	 */
	JSONObject deleteEmployeeLeader(String ids);
	
	DepartmentLeader findDepartmentLeaderById(String id);
	
	List<EmployeeLeaderShip> findEmployeeLeaderShipList(String depLeaderId);
	
	/**
	 * 
	 * 描述：新增保存
	 * @param leader
	 * @return JSONObject
	 * 作者:蔡亚军
	 * 创建时间:2015-1-13 下午3:26:27
	 */
	JSONObject updateDepartmentLeader(String id, String depId, String depName, 
				String leaderId, String leaderName, String sortId, String employeeinfo, String empType);
	
	/**
	 * 
	 * 
	 * 描述：查看出人员处于的部门,设定的领导
	 * @param employeeId
	 * @return DepartmentLeader
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午10:55:12
	 */
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

	/**
	 * 方法描述: [根据人员id查到到人员领导]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-11-24-下午1:40:58<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param userUid
	 * @param conditionSql
	 * @return
	 * DepartmentLeader
	 */
	DepartmentLeader findChuzhangByEmpId(String userUid, String conditionSql);
}
