package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.CustomStatusDao;
import cn.com.trueway.workflow.core.pojo.WfCustomStatus;

public class CustomStatusDaoImpl extends BaseDao implements CustomStatusDao {

	public void addCustomStatus(WfCustomStatus customStatus) {
		if(customStatus.getId() != null && !"".equals(customStatus.getId())){
			getEm().merge(customStatus);
		}else{
			if("".equals(customStatus.getId())){
				customStatus.setId(null);
			}
			getEm().persist(customStatus);
		}
	}

	public void delCustomStatus(WfCustomStatus customStatus) {
		getEm().remove(getEm().merge(customStatus));
	}

	public Integer getCustomStatusCountForPage(String column, String value,
			WfCustomStatus customStatus) {
		String hql = "select count(*) from WfCustomStatus t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(customStatus.getLcid())){
			hql += " and lcid = '" + customStatus.getLcid()+ "'";
		}else{
			hql += " and lcid is null";
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<WfCustomStatus> getCustomStatusListForPage(String column, String value,
			WfCustomStatus customStatus, Integer pageindex, Integer pagesize) {
		String hql = "from WfCustomStatus t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(customStatus.getLcid())){
			hql += " and lcid = '" + customStatus.getLcid()+ "'";
		}else{
			hql += " and lcid is null";
		}
		hql += " order by vc_key";
		Query query = getEm().createQuery(hql);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	public WfCustomStatus getCustomStatusById(String id) {
		String sql = " from WfCustomStatus where id ='"+id+"'";
		return (WfCustomStatus)getEm().createQuery(sql).getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<WfCustomStatus> getPublicCustomStatus() {
		String sql = " from WfCustomStatus where lcid is null order by vc_key";
		return getEm().createQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfCustomStatus> getCustomStatusByLcid(String lcid) {
		String sql = " from WfCustomStatus where ";
		if(lcid != null && !"".equals(lcid)){
			sql += " lcid='"+lcid+"'";
		}else{
			sql += " lcid is null";
		}
		sql += " order by vc_key";
		return getEm().createQuery(sql).getResultList();
	}
	
}
