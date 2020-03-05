package cn.com.trueway.workflow.set.dao;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.EmployeeSpell;

public interface EmployeeSpellDao {

	EmployeeSpell saveEmployeeSpell(EmployeeSpell employeeSpell);

	List<EmployeeSpell> findEmployeeSpellByEmpGuid(String employeeGuid);
	
	public void updateEmployeeSpell(EmployeeSpell employeeSpell);

	void deleteByEmpId(String employeeGuid);
}
