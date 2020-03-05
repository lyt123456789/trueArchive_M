package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.WorkflowSoftRouteDao;
import cn.com.trueway.workflow.core.pojo.SoftRoute;

public class WorkflowSoftRouteDaoImpl extends BaseDao implements WorkflowSoftRouteDao {
	

	public void delete(String routeid) {
		// TODO Auto-generated method stub
		String hql = "from SoftRoute r where r.uuid=:uuid";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("uuid", routeid);
		SoftRoute route = (SoftRoute) query.getSingleResult();
		if (route != null) {
			super.getEm().remove(route);
		}
	}

	public void deleteFromWFID(String wfid) {
		// TODO Auto-generated method stub
		getEm().createNativeQuery("delete from WF_SoftRoute where defineId='"+wfid+"'").executeUpdate();
	}

	public SoftRoute get(String routeid) {
		// TODO Auto-generated method stub
		return super.getEm().find(SoftRoute.class, routeid);
	}

	@SuppressWarnings("unchecked")
	public List<SoftRoute> getRouteList(String wfid) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from SoftRoute r "); 
		if(wfid!=null){
			hql.append("where r.defineId=:wfid");
		}
		Query query = getEm().createQuery(hql.toString());
		query.setParameter("wfid", wfid);
		return query.getResultList();
	}

	public void save(SoftRoute route) {
		// TODO Auto-generated method stub
		super.getEm().persist(route);
	}

	public void update(SoftRoute route) {
		// TODO Auto-generated method stub
		getEm().merge(route);
	}

}
