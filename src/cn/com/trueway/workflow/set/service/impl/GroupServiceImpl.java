package cn.com.trueway.workflow.set.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.set.dao.GroupDao;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.UserGroup;
import cn.com.trueway.workflow.set.service.GroupService;

public class GroupServiceImpl implements GroupService{
	private GroupDao groupDao;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void delete(InnerUser innerUser) throws DataAccessException {
		groupDao.delete(innerUser);
	}

	public List<InnerUser> getAllInnerUser() throws DataAccessException {
		return groupDao.getAllInnerUser();
	}

	public InnerUser getOneInnerUserById(String id) throws DataAccessException {
		return groupDao.getOneInnerUserById(id);
	}

	public void save(InnerUser innerUser) throws DataAccessException {
		groupDao.save(innerUser);
	}

	public void update(InnerUser innerUser) throws DataAccessException {
		groupDao.update(innerUser);
	}

	public Integer getInnerUserCountForPage(String column, String value,
			InnerUser innerUser) {
		return groupDao.getInnerUserCountForPage(column, value, innerUser);
	}

	public List<InnerUser> getInnerUserListForPage(String[] column, String[] value,
			InnerUser innerUser, Integer pageindex, Integer pagesize) {
		return groupDao.getInnerUserListForPage(column, value, innerUser, pageindex, pagesize);
	}

	public List<InnerUserMapEmployee> getListByInnerUserId(String inneruserId,String name, String ids)
			throws DataAccessException {
		return groupDao.getListByInnerUserId(inneruserId,name, ids);
	}

	public void save(InnerUserMapEmployee innerUserMapEmployee)
			throws DataAccessException {
		groupDao.save(innerUserMapEmployee);
	}

	public void deleteByInnerUserId(String inneruserId)
			throws DataAccessException {
		groupDao.deleteByInnerUserId(inneruserId);		
	}
	
	public List<InnerUser> getInnerUsersByType(String type, String workflowId, String siteId)
			throws DataAccessException {
		return groupDao.getInnerUserByType(type, workflowId, siteId);
	}

	public List<InnerUser> getInnerUserList(String lcid, String type) {
		return groupDao.getInnerUserList(lcid, type);
	}

	public InnerUser findInnerUserById(String id) {
		return groupDao.findInnerUserById(id);
	}
	
	public void delete(UserGroup userGroup) throws DataAccessException {
		groupDao.delete(userGroup);
	}

	public List<UserGroup> getAllUserGroup() throws DataAccessException {
		return groupDao.getAllUserGroup();
	}

	public UserGroup getOneUserGroupById(String id) throws DataAccessException {
		return groupDao.getOneUserGroupById(id);
	}

	public void save(UserGroup userGroup) throws DataAccessException {
		groupDao.save(userGroup);
	}

	public void update(UserGroup userGroup) throws DataAccessException {
		groupDao.update(userGroup);
	}

	public Integer getUserGroupCountForPage(String column, String value,
			UserGroup userGroup) {
		return groupDao.getUserGroupCountForPage(column, value, userGroup);
	}

	public List<UserGroup> getUserGroupListForPage(String column, String value,
			UserGroup userGroup, Integer pageindex, Integer pagesize) {
		return groupDao.getUserGroupListForPage(column, value, userGroup, pageindex, pagesize);
	}

	@Override
	public List<UserGroup> getUserGroupList(UserGroup userGroup,String userids) {
		return groupDao.getUserGroupList(userGroup,userids);
	}

	@Override
	public UserGroup getOneUserGroupById(String groupid, String userids) {
		return groupDao.getOneUserGroupById(groupid,userids);
	}

	@Override
	public List<Department> getUserGroupList(String ids) {
		return groupDao.getUserGroupList(ids);
	}

	@Override
	public List<InnerUserMapEmployee> getListByInnerUserIdAndGrade(
			String groupId, String name, String employeeGuid) {
		return groupDao.getListByInnerUserIdAndGrade(groupId,name,employeeGuid);
	}

	@Override
	public List<InnerUserMapEmployee> getInnerUserListByIds(String userIds) {
		return groupDao.getInnerUserListByIds(userIds);
	}

	@Override
	public List<InnerUser> getInnerUserListByParams(Map<String, String> map){
		return groupDao.getInnerUserListByParams(map);
	}

	@Override
	public CommonGroup userGroupBySiteId(String groupName, String siteId,String userId) {
		return groupDao.getUserGroupBySiteId(groupName,siteId,userId);
	}
}
