package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.UserNodesPermissionDao;

public class UserNodesPermissionDaoImpl extends BaseDao implements UserNodesPermissionDao {

	@Override
	public Integer countUserNodesPermission(String name,String sxmc,String nodeName) {
		String sql = "select Count(*) from (select empN from (select depI,empI,employee_guid,empN,"
				+ " sxmc||'::'|| replace(wm_concat(nodeI), ',', '^^') nodeL from (select dep.tabindex depI, emp.tabindex empI,"
				+ " emp.employee_guid, f.employee_name empN, e.i_index || ')' || e.vc_sxmc sxmc, n.wfn_sortnumber || ')' || n.wfn_name nodeI"
				+ " from T_WF_CORE_INNERUSER t, T_WF_CORE_INNERUSER_MAP_USER f, T_WF_CORE_ITEM e, zwkj_employee emp, zwkj_department dep,"
				+ " WF_NODE n where t.id = f.inneruser_id and e.lcid = t.workflowid and f.inneruser_id = t.id and emp.employee_guid = f.employee_id"
				+ " and dep.department_guid = emp.department_guid  and t.workflowid = n.wfn_pid and n.wfn_name is not null "
				+ " and f.employee_name like '%"+name+"%') where sxmc like '%"+sxmc+"%' group by empN, sxmc, depI, empI, employee_guid) where nodeL like '%"+nodeName+"%'"
				+ " group by employee_guid, empN, depI, empI order by depI, empI)";
		Query query = this.getEm().createNativeQuery(sql);
		return Integer.valueOf(query.getSingleResult()+"");
	}

	@Override
	public List<Object[]> getUserNodesPermission(String name, String sxmc,String nodeName,int pageIndex,
			int pageSize) {
		String sql = "select empN, '' as nodeLL,  employee_guid from (select depI,empI,employee_guid,empN,"
				+ " sxmc||'::'|| replace(wm_concat(nodeI), ',', '^^') nodeL from (select dep.tabindex depI, emp.tabindex empI,"
				+ " emp.employee_guid, f.employee_name empN, e.i_index || ')' || e.vc_sxmc sxmc, n.wfn_sortnumber || ')' || n.wfn_name nodeI"
				+ " from T_WF_CORE_INNERUSER t, T_WF_CORE_INNERUSER_MAP_USER f, T_WF_CORE_ITEM e, zwkj_employee emp, zwkj_department dep,"
				+ " WF_NODE n where t.id = f.inneruser_id and e.lcid = t.workflowid and f.inneruser_id = t.id and emp.employee_guid = f.employee_id"
				+ " and dep.department_guid = emp.department_guid  and t.workflowid = n.wfn_pid and n.wfn_name is not null "
				+ " and f.employee_name like '%"+name+"%') where sxmc like '%"+sxmc+"%' group by empN, sxmc, depI, empI, employee_guid) where nodeL like '%"+nodeName+"%'"
				+ " group by employee_guid, empN, depI, empI order by depI, empI";
		Query query = this.getEm().createNativeQuery(sql);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getNodeInfoById(String userId){
		String sql = "select depI,empI,employee_guid,empN, sxmc||'::'|| replace(wm_concat(nodeI), ',', '^^') nodeL " +
				" from (select dep.tabindex depI, emp.tabindex empI, emp.employee_guid, f.employee_name empN, e.i_index || ')' || e.vc_sxmc sxmc, n.wfn_sortnumber || ')' || n.wfn_name nodeI " +
				" from T_WF_CORE_INNERUSER t, T_WF_CORE_INNERUSER_MAP_USER f, T_WF_CORE_ITEM e, zwkj_employee emp, zwkj_department dep, WF_NODE n " +
				" where t.id = f.inneruser_id and e.lcid = t.workflowid and f.inneruser_id = t.id and emp.employee_guid = f.employee_id and dep.department_guid = emp.department_guid  and t.workflowid = n.wfn_pid and n.wfn_name is not null) " +
				" where employee_guid='" + userId + "' group by empN, sxmc, depI, empI, employee_guid";
		Query query = this.getEm().createNativeQuery(sql);
		return query.getResultList();
	}

}
