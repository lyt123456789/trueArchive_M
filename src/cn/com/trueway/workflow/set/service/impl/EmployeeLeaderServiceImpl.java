package cn.com.trueway.workflow.set.service.impl;

import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.workflow.set.dao.EmployeeLeaderDao;
import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeLeaderShip;
import cn.com.trueway.workflow.set.service.EmployeeLeaderService;

public class EmployeeLeaderServiceImpl implements EmployeeLeaderService{
	
	private EmployeeLeaderDao employeeLeaderDao;

	public EmployeeLeaderDao getEmployeeLeaderDao() {
		return employeeLeaderDao;
	}

	public void setEmployeeLeaderDao(EmployeeLeaderDao employeeLeaderDao) {
		this.employeeLeaderDao = employeeLeaderDao;
	}

	@Override
	public int findDepartmentLeaderCount(String leaderName, String employeeName) {
		return employeeLeaderDao.findDepartmentLeaderCount(leaderName, employeeName);
	}

	@Override
	public List<DepartmentLeader> findDepartmentLeaderList(String leaderName,
			String employeeName, Integer pageIndex, Integer pageSize) {
		return employeeLeaderDao.findDepartmentLeaderList(leaderName, employeeName, pageIndex, pageSize);
	}

	@Override
	public JSONObject saveDepartmentLeader(String depId, String depName,
			String leaderId, String leaderName, String sortId,
			String employeeinfo, String empType) {
		JSONObject result = new JSONObject();
		try{
			DepartmentLeader leader = new DepartmentLeader();
			leader.setDepId(depId);
			leader.setDepName(depName);
			leader.setLeaderId(leaderId);
			leader.setLeaderName(leaderName);
			leader.setSortId((sortId!=null && !sortId.equals(""))?Integer.parseInt(sortId):null);
			leader.setEmpType(empType);
			DepartmentLeader departmentLeader = employeeLeaderDao.saveDepartmentLeader(leader);
			if(departmentLeader!=null){
				String id = departmentLeader.getId();
				//人员id
				if(employeeinfo!=null && !employeeinfo.equals("")){
					String[] infos = employeeinfo.split("#");
					EmployeeLeaderShip entity = null;
					for(int i=0; i<infos.length; i++){
						entity = new EmployeeLeaderShip();
						entity.setDepLeaderId(id);
						String[] info = infos[i].split("[|]");
						entity.setEmployeeId(info[0]);
						entity.setEmployeeName(info[1]);
						entity.setEmployeeShortDn(info[2]);
						employeeLeaderDao.saveEmployeeLeaderShip(entity);
					}
				}
				
			}
			result.put("success", true);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		
		return result;
	}

	@Override
	public JSONObject deleteEmployeeLeader(String ids) {
		JSONObject result = new JSONObject();
		try{
			String[] id = ids.split(",");
			for(int i=0; i<id.length; i++){
				employeeLeaderDao.deleteDepartmentLeader(id[i]);
				employeeLeaderDao.deleteEmployeeLeaderShip(id[i]);
			}
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public DepartmentLeader findDepartmentLeaderById(String id) {
		return employeeLeaderDao.findDepartmentLeaderById(id);
	}

	@Override
	public List<EmployeeLeaderShip> findEmployeeLeaderShipList(
			String depLeaderId) {
		return employeeLeaderDao.findEmployeeLeaderShipList(depLeaderId);
	}

	@Override
	public JSONObject updateDepartmentLeader(String id, String depId,
			String depName, String leaderId, String leaderName, String sortId,
			String employeeinfo, String empType) {
		JSONObject result = new JSONObject();
		try{
			DepartmentLeader leader = new DepartmentLeader();
			leader.setId(id);
			leader.setDepId(depId);
			leader.setDepName(depName);
			leader.setLeaderId(leaderId);
			leader.setLeaderName(leaderName);
			leader.setSortId((sortId!=null && !sortId.equals(""))?Integer.parseInt(sortId):null);
			leader.setEmpType(empType);
			employeeLeaderDao.updateDepartmentLeader(leader);
			employeeLeaderDao.deleteEmployeeLeaderShip(id);
			//人员id
			if(employeeinfo!=null && !employeeinfo.equals("")){
				String[] infos = employeeinfo.split("#");
				EmployeeLeaderShip entity = null;
				for(int i=0; i<infos.length; i++){
					entity = new EmployeeLeaderShip();
					entity.setDepLeaderId(id);
					String[] info = infos[i].split("[|]");
					entity.setEmployeeId(info[0]);
					entity.setEmployeeName(info[1]);
					entity.setEmployeeShortDn(info[2]);
					employeeLeaderDao.saveEmployeeLeaderShip(entity);
				}
			}
			result.put("success", true);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}

	@Override
	public DepartmentLeader findDepartmentLeaderByEmpId(String employeeId) {
		return employeeLeaderDao.findDepartmentLeaderByEmpId(employeeId);
	}

	@Override
	public List<DepartmentLeader> findDepartmentLeaderByDepId(String depId) {
		return employeeLeaderDao.findDepartmentLeaderByDepId(depId);
	}

	@Override
	public DepartmentLeader findChuzhangByEmpId(String userUid, String conditionSql) {
		return employeeLeaderDao.findChuzhangByEmpId(userUid, conditionSql);
	}
}
