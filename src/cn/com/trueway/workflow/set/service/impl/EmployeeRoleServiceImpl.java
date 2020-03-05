package cn.com.trueway.workflow.set.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.set.dao.EmployeeRoleDao;
import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.WfMainRole;
import cn.com.trueway.workflow.set.service.EmployeeRoleService;


/**
 * 人员管理的Service层实现类
 * 
 * @author liwei
 * 
 */
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

	private EmployeeRoleDao employeeRoleDao;

	public EmployeeRoleDao getEmployeeRoleDao() {
		return employeeRoleDao;
	}
	public void setEmployeeRoleDao(EmployeeRoleDao employeeRoleDao) {
		this.employeeRoleDao = employeeRoleDao;
	}


	@Override
	public List<EmployeeRole> getEmployeeRoleList(
			Map<String, String> searchMap, Integer pageIndex, Integer pageSize) {
		return employeeRoleDao.getEmployeeRoleList(searchMap, pageIndex, pageSize);
	}


	@Override
	public int getEmployeeRoleCount(Map<String, String> searchMap) {
		return employeeRoleDao.getEmployeeRoleCount(searchMap);
	}


	@Override
	public void saveEmployeeRole(EmployeeRole employeeRole) {
		employeeRoleDao.saveEmployeeRole(employeeRole);
	}


	@Override
	public void updateEmployeeRole(EmployeeRole employeeRole) {
		employeeRoleDao.updateEmployeeRole(employeeRole);
	}


	@Override
	public void deleteEmployeeRole(EmployeeRole employeeRole) {
		if(employeeRole!=null){
			employeeRoleDao.deleteEmployeeRole(employeeRole);
			
			String roleId = employeeRole.getId();
			try {
				employeeRoleDao.deleteWfMainRole(roleId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public EmployeeRole getEmployeeRoleById(String id) {
		return employeeRoleDao.getEmployeeRoleById(id);
	}


	@Override
	public int getEmployeeRoleByMc(String mc, String userid){
		return employeeRoleDao.getEmployeeRoleByMc(mc,userid);
	}
	
	@Override
	public List<WfMainRole> findWfMainRoleByRoleId(String roleId) {
		List<WfMainRole> list  = new ArrayList<WfMainRole>();
		try{
			list = employeeRoleDao.findWfMainRoleByRoleId(roleId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public JSONObject saveWfMainRole(String roleId, String wfMainIds) {
		JSONObject result = new JSONObject();
		try{
			//删除
			employeeRoleDao.deleteWfMainRole(roleId);
			//新增
			if(wfMainIds!=null){
				WfMainRole wfMainRole = null;
				String[] ids = wfMainIds.split(",");
				String wfMainId = "";
				for(int i=0; i<ids.length; i++){
					wfMainRole = new WfMainRole();
					wfMainId = ids[i];
					wfMainRole.setRoleId(roleId);
					wfMainRole.setWfmainId(wfMainId);
					employeeRoleDao.saveWfMainRole(wfMainRole);
				}
			}
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<EmployeeRole> findEmployeeRoleById(String employeeId) {
		List<EmployeeRole> list = new ArrayList<EmployeeRole>();
		try{
			list = employeeRoleDao.findEmployeeRoleById(employeeId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int findAllWfMainRoleCount() {
		int count = 0 ;
		try{
			count = employeeRoleDao.findAllWfMainRoleCount();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
