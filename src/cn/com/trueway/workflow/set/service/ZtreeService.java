package cn.com.trueway.workflow.set.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.CommonGroupUsers;
import cn.com.trueway.workflow.set.pojo.Leader;

/**
 * @author 赵坚
 * @version 创建时间：2016年7月21日15:45:39
 */
public interface ZtreeService {

	CommonGroup saveCommonGroup(CommonGroup cg);
	
	void saveCommonGroupUsers(CommonGroupUsers cgu);
	
	void saveLeaders(Leader leader);
	
	void deleteCommonGroup(CommonGroup cg);
	
	void deleteCommonGroupUsers(CommonGroupUsers cgu);
	
	void deleteCommonGroupUsersByGid(String gid);
	
	void deleteLeaderBySiteId(String siteId);
	
	void updateCommonGroup(CommonGroup cg);
	
	void updateCommonGroupUsers(CommonGroupUsers cgu);
	
	List<CommonGroup> findAllCommonGroupByUid(String uid, String siteId);

	List<CommonGroup> findAllDeptGroupByUid(String uid);
	
	List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid);
	
	List<Leader> findAllLeadersBySiteId(String siteId);
	
	CommonGroup findCommonGroupById(String id);

	List<Department> getAllParentDeptBydeptId(String deptsid);
	
	List<Department> getAllParentDeptByMC(String deptsid);
	
	List<Department> getAllChildDeptBydeptId(String deptId);
	
	List<Employee> getAllChildEmpsByDeptId(String deptId);
	
	List<Employee> getAllEmpsByUserIds(String userIds);
	
	List<Employee> getEmployeeListByNodeId(String nodeId);

	List<Employee> getEmployeeListByNodeInfo(String nodeId, String mc);
	
	public List<CommonGroup> getCommonUseGroupList(Map<String, String> map);
	
	public int getCommonUseGroupCount(Map<String, String> map);

	public String addCommonGroupUser(Map<String, String> map);
	
	public String deleteCommonUseGroup(Map<String, String> map);
	
	public List<CommonGroupUsers> getComGrpUsersByParams(Map<String, String> map);
	
	int countOfCommonGroup(String uid);
	
	List<CommonGroup> getCommonGroups(String uid, Integer pageIndex, Integer pageSize);
}
