package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.EmployeeSpellDao;
import cn.com.trueway.workflow.set.pojo.EmployeeSpell;

public class EmployeeSpellDaoImpl extends BaseDao implements EmployeeSpellDao {

	public EmployeeSpell saveEmployeeSpell(EmployeeSpell employeeSpell) {
		if(employeeSpell!=null){
			getEm().persist(employeeSpell);
		}
		return employeeSpell;
	}

	public List<EmployeeSpell> findEmployeeSpellByEmpGuid(String employeeGuid) {
		String hql = "from EmployeeSpell where employeeGuid =  ? ";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, employeeGuid);
		
		return query.getResultList();
	}

	public void updateEmployeeSpell(EmployeeSpell employeeSpell){
		this.getEm().merge(employeeSpell);
	}

	public void deleteByEmpId(String employeeGuid) {
		try {
			String sql = "delete from T_SYS_EmployeeSpell where employee_guid = :id";
			Query query = getEm().createNativeQuery(sql);
			query.setParameter("id", employeeGuid);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
