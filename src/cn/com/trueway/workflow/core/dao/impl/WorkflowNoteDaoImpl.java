package cn.com.trueway.workflow.core.dao.impl;


import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.WorkflowNoteDao;
import cn.com.trueway.workflow.core.pojo.Node;
import cn.com.trueway.workflow.core.pojo.SoftRoute;


public class WorkflowNoteDaoImpl extends BaseDao implements WorkflowNoteDao {

	public void delete(String nodeid) {
		// TODO Auto-generated method stub
		String hql = "from Node r where r.uuid=:uuid";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("uuid", nodeid);
		SoftRoute route = (SoftRoute) query.getSingleResult();
		if (route != null) {
			super.getEm().remove(route);
		}
	}

	public void deleteFromWFID(String wfid) {
		// TODO Auto-generated method stub
		getEm().createNativeQuery("delete from WF_SoftRoute where defineId='"+wfid+"'").executeUpdate();
	}

	public Node get(String nodeid) {
		// TODO Auto-generated method stub
		return super.getEm().find(Node.class, nodeid);
	}

	@SuppressWarnings("unchecked")
	public List<Node> getNodeList(String wfid) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from Node r "); 
		if(wfid!=null){
			hql.append("where r.defineId=:wfid");
		}
		Query query = getEm().createQuery(hql.toString());
		query.setParameter("wfid", wfid);
		return query.getResultList();
	}

	public void save(Node node) {
		// TODO Auto-generated method stub
		super.getEm().persist(node);
	}

	public void update(Node node) {
		// TODO Auto-generated method stub
		getEm().merge(node);
	}
	
}
