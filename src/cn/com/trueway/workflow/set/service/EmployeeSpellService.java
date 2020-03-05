package cn.com.trueway.workflow.set.service;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.EmployeeSpell;

public interface EmployeeSpellService {

	EmployeeSpell saveEmployeeSpell(EmployeeSpell employeeSpell);

	List<EmployeeSpell> findEmployeeSpellByEmpGuid(String employeeGuid);
	
	public void updateEmployeeSpell(EmployeeSpell employeeSpell);

	void deleteByEmpId(String employeeGuid);
}
