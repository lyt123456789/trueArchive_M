package cn.com.trueway.workflow.version.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.version.dao.VersionManagerDao;
import cn.com.trueway.workflow.version.pojo.VersionManager;

public class VersionManagerDaoImpl extends BaseDao implements VersionManagerDao{

	public int getVmCountForPage() {
		String sql = "select count(t.num) from t_wf_core_version t";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<VersionManager> getVmListForPage(String condition, Integer pageIndex, Integer pageSize) {
		StringBuffer sb = new StringBuffer("select t.* from t_wf_core_version t where 1=1");
		if(!("").equals(condition) && condition != null){
			sb.append(condition);
		}
		sb.append("order by t.updatedate desc");
		Query query = getEm().createNativeQuery(sb.toString(),VersionManager.class);
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	public void addvm(VersionManager vm) {
		getEm().persist(vm);
	}

	public VersionManager getVmById(String id) {
		return getEm().find(VersionManager.class, id);
	}

	public void updateVm(VersionManager vm) {
		getEm().merge(vm);
	}

	public void delVm(String vmId) {
		String sql = "delete from t_wf_core_version t where t.id='"+vmId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}
}
