package cn.com.trueway.workflow.set.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.UserGroup;


public interface GroupDao {
	public InnerUser save(InnerUser innerUser)throws DataAccessException;
	public void update(InnerUser innerUser)throws DataAccessException;
	public void delete(InnerUser innerUser)throws DataAccessException;
	public List<InnerUser> getAllInnerUser()throws DataAccessException;
	public InnerUser getOneInnerUserById(String id)throws DataAccessException;
	public List<InnerUser> getInnerUserListForPage(String[] column,String[] value,InnerUser innerUser,Integer pageindex,Integer pagesize);
	public Integer getInnerUserCountForPage(String column,String value,InnerUser innerUser);
	
	public void save(InnerUserMapEmployee innerUserMapEmployee)throws DataAccessException;
	public List<InnerUserMapEmployee> getListByInnerUserId(String inneruserId, String name, String ids)throws DataAccessException;
	public void  deleteByInnerUserId(String inneruserId)throws DataAccessException;
	public List<InnerUser> getInnerUserByType(String type, String workflowId, String siteId);
	
	public List<InnerUserMapEmployee> getListByEmployeeId(String vc_employeeid, String workflowid)throws DataAccessException;
	public List<InnerUser> getInnerUserList(String lcid, String type);
	public InnerUser findInnerUserById(String id);
	
	
	
	public void save(UserGroup userGroup)throws DataAccessException;
	public void update(UserGroup userGroup)throws DataAccessException;
	public void delete(UserGroup userGroup)throws DataAccessException;
	public List<UserGroup> getAllUserGroup()throws DataAccessException;
	public UserGroup getOneUserGroupById(String id)throws DataAccessException;
	public List<UserGroup> getUserGroupListForPage(String column,String value,UserGroup userGroup,Integer pageindex,Integer pagesize);
	public Integer getUserGroupCountForPage(String column,String value,UserGroup userGroup);
	public List<UserGroup> getUserGroupList(UserGroup userGroup,String mc);
	public UserGroup getOneUserGroupById(String groupid, String mc);
	
	public List<Department> getUserGroupList(String ids);
	public List<InnerUserMapEmployee> getListByInnerUserIdAndGrade(
			String groupId, String name, String employeeGuid);
	List<InnerUserMapEmployee> getInnerUserListByIds(String userIds);
	List<InnerUser> getInnerUserListByParams(Map<String, String> map);
	
	List<String> getUserIdFromInnerUserMap(String groupId,String userId);
	
	public CommonGroup getUserGroupBySiteId(String groupName, String siteId,String userId);
}
