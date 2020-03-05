package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.EmployeeRoleDao;
import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.WfMainRole;

/**
 * 人员管理的Dao层实现类
 * 
 * @author liwei
 * 
 */
@SuppressWarnings("unchecked")
public class EmployeeRoleDaoImpl extends BaseDao implements EmployeeRoleDao {

	@Override
	public List<EmployeeRole> getEmployeeRoleList(
			Map<String, String> searchMap, Integer pageIndex, Integer pageSize) {
		String sql = " from EmployeeRole ";
		Query query = super.getEm().createQuery(sql);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public int getEmployeeRoleCount(Map<String, String> searchMap) {
		String sql = "select count(*) from ZWKJ_EMPLOYEEROLE t ";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public void saveEmployeeRole(EmployeeRole employeeRole) {
		getEm().persist(employeeRole);
	}

	@Override
	public void updateEmployeeRole(EmployeeRole employeeRole) {
		getEm().merge(employeeRole);
	}

	@Override
	public void deleteEmployeeRole(EmployeeRole employeeRole) {
		String hql =" delete from EmployeeRole t where t.id='"+employeeRole.getId()+"'";
		getEm().createQuery(hql).executeUpdate();
	}

	@Override
	public EmployeeRole getEmployeeRoleById(String id) {
		String hql =" from EmployeeRole t where t.id='"+id+"'";
		return (EmployeeRole) getEm().createQuery(hql).getSingleResult();
	}

	@Override
	public int getEmployeeRoleByMc(String mc, String userid) {
		String sql = " select count(1) from ZWKJ_EMPLOYEEROLE t " +
				"where userids like '%"+userid+"%'";
		if(mc!=null && !mc.equals("")){
			sql += " and t.roleName = '"+mc+"'";
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<WfMainRole> findWfMainRoleByRoleId(String roleId) throws Exception{
		String hql = "FROM WfMainRole t WHERE t.roleId = '"+roleId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void deleteWfMainRole(String roleId) throws Exception {
		String sql = "delete from t_wf_core_wfmain_role t where t.roleId='"+roleId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}

	@Override
	public void saveWfMainRole(WfMainRole wfMainRole) throws Exception {
		if(wfMainRole!=null){
			getEm().persist(wfMainRole);
			
		}
	}

	@Override
	public List<EmployeeRole> findEmployeeRoleById(String employeeId)
			throws Exception {
		String hql = " from EmployeeRole  where userids like '%"+employeeId+"%'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public int findAllWfMainRoleCount() throws Exception {
		String sql = "select count(1) from t_wf_core_wfmain_role";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

}
