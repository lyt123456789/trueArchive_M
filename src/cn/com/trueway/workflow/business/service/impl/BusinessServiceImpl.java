package cn.com.trueway.workflow.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.trueway.workflow.business.dao.BusinessDao;
import cn.com.trueway.workflow.business.pojo.HandRoundShip;
import cn.com.trueway.workflow.business.pojo.Library;
import cn.com.trueway.workflow.business.pojo.PressMsg;
import cn.com.trueway.workflow.business.service.BusinessService;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.set.dao.EmployeeDao;

public class BusinessServiceImpl implements BusinessService{
	
	private BusinessDao businessDao;
	
	private EmployeeDao employeeDao;

	public BusinessDao getBusinessDao() {
		return businessDao;
	}

	public void setBusinessDao(BusinessDao businessDao) {
		this.businessDao = businessDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public int findSendLibraryCount(String contionsql, String userId) {
		return businessDao.findSendLibraryCount(contionsql, userId);
	}

	@Override
	public List<Library> findSendLibraryList(String contionsql, String userId,
			Integer pageIndex, Integer pageSize) {
		return businessDao.findSendLibraryList(contionsql, userId, pageIndex, pageSize);
	}

	@Override
	public int findReceiveLibraryCount(String contionsql, String userId) {
		return businessDao.findReceiveLibraryCount(contionsql, userId);
	}

	@Override
	public List<Library> findReceiveLibraryList(String contionsql,
			String userId, Integer pageIndex, Integer pageSize) {
		return businessDao.findReceiveLibraryList(contionsql, userId, pageIndex, pageSize);
	}
	
	@Override
	public List<Pending> getStatisticalList(String conditionSql,String userId, Integer pageIndex, Integer pageSize,HashMap<String,String> map) {
		List<Pending> pendList = businessDao.getStatisticalList(conditionSql,userId, pageIndex, pageSize,map);
		return pendList;
	}
	
	@Override
	public int getCountOfStatistical(String conditionSql,String userId,String type,HashMap<String,String> map){
		return businessDao.getCountOfStatistical(conditionSql,userId,type,map);
	}
	
	@Override
	public List<Pending> getPressList(String conditionSql,String userId, Integer pageIndex, Integer pageSize,HashMap<String,String> map) {
		List<Pending> pendList = businessDao.getPressList(conditionSql,userId, pageIndex, pageSize,map);
		return pendList;
	}
	
	@Override
	public int getCountOfPress(String conditionSql,String userId,String type,HashMap<String,String> map){
		return businessDao.getCountOfPress(conditionSql,userId,type,map);
	}
	
	@Override
	public List<PressMsg> getMsgListByProcessId(String processId) {
		return businessDao.getMsgListByProcessId(processId);
	}
	
	@Override
	public void addPressMsg(PressMsg pressMsg) {
		
		businessDao.addPressMsg(pressMsg);
	}
	
	@Override
	public List<Pending> getPressMsgList(String conditionSql,String userId, Integer pageIndex, Integer pageSize,HashMap<String,String> map) {
		List<Pending> pressMsgList = businessDao.getPressMsgList(conditionSql,userId, pageIndex, pageSize,map);
		return pressMsgList;
	}
	
	@Override
	public int getCountOfPressMsg(String conditionSql,String userId,String type,HashMap<String,String> map){
		return businessDao.getCountOfPressMsg(conditionSql,userId,type,map);
	}

	@Override
	public void addHandRoundShip(String userIds, String instanceId, Employee emp) {
		String[] userids = userIds.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String userId : userids) {
			HandRoundShip entity = businessDao.selectHandRoundShip(userId, instanceId);
			if(null == entity){
				Employee employee = employeeDao.queryEmployeeById(userId);
				entity = new HandRoundShip();
				entity.setInstanceId(instanceId);
				entity.setUserId(userId);
				if(null != employee){
					entity.setUserName(employee.getEmployeeName());
				}
				entity.setIsRead(0);
				entity.setSenderId(emp.getEmployeeGuid());
				entity.setSenderName(emp.getEmployeeName());
				entity.setAddTime(sdf.format(new Date()));
				businessDao.addHandRoundShip(entity);
			}
		}
		
	}

	@Override
	public void updateHandRoundShip(HandRoundShip entity) {
		HandRoundShip entity1 = businessDao.selectHandRoundShip(entity.getUserId(), entity.getInstanceId());
		entity1.setIsRead(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity1.setReadTime(sdf.format(new Date()));
		businessDao.updateHandRoundShip(entity1);
	}

	@Override
	public Integer countHandRoundShips(String instanceId){
		return businessDao.countHandRoundShips(instanceId);
	}
	
	@Override
	public List<HandRoundShip> selectHandRoundShips(String instanceId,
			Integer pageIndex, Integer pageSize) {
		return businessDao.selectHandRoundShips(instanceId, pageIndex, pageSize);
	}

}
