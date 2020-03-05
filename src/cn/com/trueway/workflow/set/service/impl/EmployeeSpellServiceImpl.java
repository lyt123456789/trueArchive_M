package cn.com.trueway.workflow.set.service.impl;

import java.util.List;

import cn.com.trueway.workflow.set.dao.EmployeeSpellDao;
import cn.com.trueway.workflow.set.pojo.EmployeeSpell;
import cn.com.trueway.workflow.set.service.EmployeeSpellService;


public class EmployeeSpellServiceImpl implements EmployeeSpellService {

	private EmployeeSpellDao employeeSpellDao;

	public EmployeeSpellDao getEmployeeSpellDao() {
		return employeeSpellDao;
	}

	public void setEmployeeSpellDao(EmployeeSpellDao employeeSpellDao) {
		this.employeeSpellDao = employeeSpellDao;
	}
	
	public EmployeeSpell saveEmployeeSpell(EmployeeSpell employeeSpell){
		return employeeSpellDao.saveEmployeeSpell(employeeSpell);
	}

	public List<EmployeeSpell> findEmployeeSpellByEmpGuid(String employeeGuid) {
		return employeeSpellDao.findEmployeeSpellByEmpGuid(employeeGuid);
	}

	public void updateEmployeeSpell(EmployeeSpell employeeSpell) {
		employeeSpellDao.updateEmployeeSpell(employeeSpell);
	}

	public void deleteByEmpId(String employeeGuid) {
		employeeSpellDao.deleteByEmpId(employeeGuid);
	}

}
