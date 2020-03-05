package cn.com.trueway.workflow.set.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.ZtreeDao;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.CommonGroupUsers;
import cn.com.trueway.workflow.set.pojo.Leader;
import cn.com.trueway.workflow.set.service.ZtreeService;


/**
 * @author 李伟
 * @version 创建时间：2009-11-27 下午09:00:28 类说明
 */
public class ZtreeServiceImpl implements ZtreeService {

	private ZtreeDao ztreeDao;
	
	public ZtreeDao getZtreeDao() {
		return ztreeDao;
	}

	public void setZtreeDao(ZtreeDao ztreeDao) {
		this.ztreeDao = ztreeDao;
	}

	@Override
	public CommonGroup saveCommonGroup(CommonGroup cg) {
		return ztreeDao.saveCommonGroup(cg);
	}

	@Override
	public void saveCommonGroupUsers(CommonGroupUsers cgu) {
		ztreeDao.saveCommonGroupUsers(cgu);
	}
	
	@Override
	public void saveLeaders(Leader leader) {
		ztreeDao.saveLeaders(leader);
	}

	@Override
	public void deleteCommonGroup(CommonGroup cg) {
		ztreeDao.deleteCommonGroup(cg);
	}

	@Override
	public void deleteCommonGroupUsers(CommonGroupUsers cgu) {
		ztreeDao.deleteCommonGroupUsers(cgu);
	}

	@Override
	public void updateCommonGroup(CommonGroup cg) {
		ztreeDao.updateCommonGroup(cg);
	}

	@Override
	public void updateCommonGroupUsers(CommonGroupUsers cgu) {
		ztreeDao.updateCommonGroupUsers(cgu);
	}

	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid, String siteId) {
		return ztreeDao.findAllCommonGroupByUid(uid, siteId);
	}

	@Override
	public List<CommonGroup> findAllDeptGroupByUid(String uid) {
		return ztreeDao.findAllDeptGroupByUid(uid);
	}

	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid) {
		return ztreeDao.findAllCommonGroupUsersByGid(gid);
	}
	
	@Override
	public List<Leader> findAllLeadersBySiteId(String siteId) {
		return ztreeDao.findAllLeadersBySiteId(siteId);
	}

	@Override
	public CommonGroup findCommonGroupById(String id) {
		return ztreeDao.findCommonGroupById(id);
	}

	@Override
	public void deleteCommonGroupUsersByGid(String gid) {
		ztreeDao.deleteCommonGroupUsersByGid(gid);
	}
	
	@Override
	public void deleteLeaderBySiteId(String siteId) {
		ztreeDao.deleteLeaderBySiteId(siteId);
	}

	@Override
	public List<Department> getAllParentDeptBydeptId(String deptsid) {
		return ztreeDao.getAllParentDeptBydeptId(deptsid);
	}
	
	@Override
	public List<Department> getAllParentDeptByMC(String mc) {
		return ztreeDao.getAllParentDeptByMC(mc);
	}

	@Override
	public List<Department> getAllChildDeptBydeptId(String deptId) {
		return ztreeDao.getAllChildDeptBydeptId(deptId);
	}
	
	@Override
	public List<Employee> getAllEmpsByUserIds(String userIds) {
		return ztreeDao.getAllEmpsByUserIds(userIds);
	}


	@Override
	public List<Employee> getAllChildEmpsByDeptId(String deptId) {
		return ztreeDao.getAllChildEmpsByDeptId(deptId);
	}
	
	@Override
	public List<Employee> getEmployeeListByNodeId(String nodeId) {
		return ztreeDao.getEmployeeListByNodeId(nodeId);
	}
	
	@Override
	public List<Employee> getEmployeeListByNodeInfo(String nodeId, String mc) {
		return ztreeDao.getEmployeeListByNodeInfo(nodeId, mc);
	}
	
	@Override
	public List<CommonGroup> getCommonUseGroupList(Map<String, String> map){
		return ztreeDao.getCommonUseGroupList(map);
	}
	
	@Override
	public int getCommonUseGroupCount(Map<String, String> map){
		return ztreeDao.getCommonUseGroupCount(map);
	}
	
	@Override
	public String addCommonGroupUser(Map<String, String> map){
		String retStr = "";//返回信息
		String groupName = map.get("groupName");
		String groupId = map.get("groupId");
		String orderIndex = map.get("orderIndex");
		String persons = map.get("persons");
		String isJyGroup = map.get("isJyGroup");
		//保存常用组信息
		CommonGroup commonGroup = new CommonGroup();
		commonGroup.setName(groupName);
		commonGroup.setOrderIndex(Integer.parseInt(orderIndex));
		commonGroup.setIsJyGroup(isJyGroup);
		commonGroup.setCreateTime(new Date());
		commonGroup.setBelongTo("");
		if(CommonUtil.stringNotNULL(groupId)){
			//更新
			commonGroup.setId(groupId);
			updateCommonGroup(commonGroup);
		}else{
			//新增
			commonGroup = ztreeDao.saveCommonGroup(commonGroup);
		}
		//保存常用组中人员信息
		JSONArray ja = JSONArray.fromObject(persons);
		if(!ja.isEmpty()){
			ztreeDao.deleteCommonGroupUsersByGid(commonGroup.getId());
			for(int i=0;i<ja.size();i++){
				JSONObject jo = (JSONObject) ja.get(i);
				CommonGroupUsers cgu = new CommonGroupUsers();
				cgu.setEmpId((String) jo.get("id"));
				cgu.setEmpName((String) jo.get("name"));
				cgu.setGid(commonGroup.getId());
				cgu.setOrderIndex(i + 1);
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("empId", (String) jo.get("id"));
				List<Employee> empList = ztreeDao.getEmployeeList(searchMap);
				String deptDispName = "";//显示部门名称
				if(empList != null && empList.size() > 0){
					Employee emp = empList.get(0);
					deptDispName = emp.getEmployeeDescription();
				}
				cgu.setDeptName(deptDispName);
				ztreeDao.saveCommonGroupUsers(cgu);
			}
			retStr = "ok";
		}else{
			retStr = "personsNone";
		}
		return retStr;
	}
	
	public String deleteCommonUseGroup(Map<String, String> map){
		String ids = map.get("ids");
		String retStr = "";
		if(CommonUtil.stringNotNULL(ids)){
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				String id = idsArr[i];
				//删除组
				ztreeDao.deleteCommGrpById(id);
				//删除组内成员
				ztreeDao.deleteCommonGroupUsersByGid(id);
				retStr = "success";
			}
		}
		return retStr;
	}
	
	@Override
	public List<CommonGroupUsers> getComGrpUsersByParams(Map<String, String> map){
		return ztreeDao.getComGrpUsersByParams(map);
	}
	
	@Override
	public int countOfCommonGroup(String uid) {
		return ztreeDao.countOfCommonGroup(uid);
	}

	@Override
	public List<CommonGroup> getCommonGroups(String uid, Integer pageIndex, Integer pageSize) {
		return ztreeDao.getCommonGroups(uid, pageIndex, pageSize);
	}
}
