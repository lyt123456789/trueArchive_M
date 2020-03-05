package cn.com.trueway.workflow.set.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.UserGroup;


public interface GroupService {
	public void save(InnerUser innerUser)throws DataAccessException;
	public void update(InnerUser innerUser)throws DataAccessException;
	public void delete(InnerUser innerUser)throws DataAccessException;
	public List<InnerUser> getAllInnerUser()throws DataAccessException;
	public InnerUser getOneInnerUserById(String id)throws DataAccessException;
	public List<InnerUser> getInnerUserListForPage(String[] column,String[] value,InnerUser innerUser,Integer pageindex,Integer pagesize);
	public Integer getInnerUserCountForPage(String column,String value,InnerUser innerUser);
	
	public void save(InnerUserMapEmployee innerUserMapEmployee)throws DataAccessException;
	public List<InnerUserMapEmployee> getListByInnerUserId(String inneruserId,String name, String ids)throws DataAccessException;
	public void  deleteByInnerUserId(String inneruserId)throws DataAccessException;
	public List<InnerUser> getInnerUsersByType(String type, String workflowId, String siteId);
	public List<InnerUser> getInnerUserList(String lcid, String type);
	public InnerUser findInnerUserById(String id);
	
	public void save(UserGroup userGroup)throws DataAccessException;
	public void update(UserGroup userGroup)throws DataAccessException;
	public void delete(UserGroup userGroup)throws DataAccessException;
	public List<UserGroup> getAllUserGroup()throws DataAccessException;
	public UserGroup getOneUserGroupById(String id)throws DataAccessException;
	public List<UserGroup> getUserGroupListForPage(String column,String value,UserGroup userGroup,Integer pageindex,Integer pagesize);
	public Integer getUserGroupCountForPage(String column,String value,UserGroup userGroup);
	public List<UserGroup> getUserGroupList(UserGroup userGroup,String userids);
	public UserGroup getOneUserGroupById(String groupid, String userids);
	public List<Department> getUserGroupList(String ids);
	public List<InnerUserMapEmployee> getListByInnerUserIdAndGrade(
			String groupId, String name, String employeeGuid);
	List<InnerUserMapEmployee> getInnerUserListByIds(String userIds);
	public List<InnerUser> getInnerUserListByParams(Map<String, String> map);
	/**
	 * 方法描述: [根据站点id和组名查询联系组]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-8-31-下午3:42:48<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param groupName
	 * @param siteId
	 * @param userId
	 * @return
	 * CommonGroup
	 */
	public CommonGroup userGroupBySiteId(String groupName, String siteId,String userId);
}
