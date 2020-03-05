package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.EmployeeLeaderDao;
import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeLeaderShip;

public class EmployeeLeaderDaoImpl extends BaseDao implements EmployeeLeaderDao{

	@Override
	public int findDepartmentLeaderCount(String leaderName, String employeeName) {
		String sql =  "";
		if( employeeName!=null && !employeeName.equals("")){	//有查询条件
			sql = "select count(distinct(t.id)) from t_wf_core_department_leader t , " +
					" t_wf_core_employee_leadership t2 where t.id = t2.depLeaderId";
			
			if(leaderName!=null && !leaderName.equals("")){
				sql += " and t.leaderName like '%"+leaderName+"%'";
			}
			if(employeeName!=null && !employeeName.equals("")){
				sql += " and t2.employeename like '%"+employeeName+"%'";
			}
		}else{
			sql = "select count(distinct(t.id)) from t_wf_core_department_leader t left join " +
					" t_wf_core_employee_leadership t2 on t.id = t2.depLeaderId";
			if(leaderName!=null && !leaderName.equals("")){
				sql += " where t.leaderName like '%"+leaderName+"%'";
			}
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<DepartmentLeader> findDepartmentLeaderList(String leaderName,
			String employeeName, Integer pageIndex, Integer pageSize) {
		String sql = "";
		if( employeeName!=null && !employeeName.equals("")){	//有查询条件
			sql = "select distinct t.* from t_wf_core_department_leader t , " +
					" t_wf_core_employee_leadership t2 where t.id = t2.depLeaderId";
			if(leaderName!=null && !leaderName.equals("")){
				sql += " and t.leaderName like '%"+leaderName+"%'";
			}
			if(employeeName!=null && !employeeName.equals("")){
				sql += " and t2.employeename like '%"+employeeName+"%'";
			}
		}else{
			sql = "select distinct t.* from t_wf_core_department_leader t left join " +
					" t_wf_core_employee_leadership t2 on t.id = t2.depLeaderId";
			if(leaderName!=null && !leaderName.equals("")){
				sql += " where t.leaderName like '%"+leaderName+"%'";
			}
		}
		sql += " order by t.sortId asc ";
		Query query = getEm().createNativeQuery(sql, DepartmentLeader.class);
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult( pageIndex);
			query.setMaxResults( pageSize);
		}
		return query.getResultList();
	}

	@Override
	public DepartmentLeader saveDepartmentLeader(DepartmentLeader leader) throws Exception{
		getEm().persist(leader);
		return leader;
	}

	@Override
	public void saveEmployeeLeaderShip(EmployeeLeaderShip employeeLeaderShip) throws Exception{
		getEm().persist(employeeLeaderShip);
	}

	@Override
	public void deleteDepartmentLeader(String id) throws Exception {
		String sql = "delete from t_wf_core_department_leader t where t.id='"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void deleteEmployeeLeaderShip(String id) throws Exception {
		String sql = "delete from t_wf_core_employee_leadership t where t.depLeaderId='"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public DepartmentLeader findDepartmentLeaderById(String id) {
		return getEm().find(DepartmentLeader.class, id);
	}

	@Override
	public List<EmployeeLeaderShip> findEmployeeLeaderShipList(
			String depLeaderId) {
		String sql = " from EmployeeLeaderShip t where t.depLeaderId = '"+depLeaderId+"'";
		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public void updateDepartmentLeader(DepartmentLeader leader)
			throws Exception {
		getEm().merge(leader);
		
	}

	@Override
	public DepartmentLeader findDepartmentLeaderByEmpId(String employeeId) {
		String sql = "select t.* from t_wf_core_department_leader t," +
				" t_wf_core_employee_leadership t2 where t.id = t2.depLeaderId" +
				" and t2.employeeId = '"+employeeId+"'";
		List<DepartmentLeader> list= getEm().createNativeQuery(sql, DepartmentLeader.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public List<DepartmentLeader> findDepartmentLeaderByDepId(String depId) {
		String sql = "from DepartmentLeader t where t.depId = '"+depId+"'";
		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public DepartmentLeader findChuzhangByEmpId(String employeeId,String conditionSql) {
		String sql = "select t.* from t_wf_core_department_leader t," +
				" t_wf_core_employee_leadership t2 where t.id = t2.depLeaderId" +
				" and t2.employeeId = '"+employeeId+"'"+conditionSql;
		List<DepartmentLeader> list= getEm().createNativeQuery(sql, DepartmentLeader.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
