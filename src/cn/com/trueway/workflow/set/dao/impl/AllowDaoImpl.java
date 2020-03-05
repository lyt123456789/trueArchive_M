package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.set.dao.AllowDao;
import cn.com.trueway.workflow.set.pojo.Allow;


public class AllowDaoImpl extends BaseDao implements AllowDao{

	public void addAllow(Allow allow) throws DataAccessException {
		super.getEm().persist(allow);
	}

	public void deleteAllow(Allow allow) throws DataAccessException {
		String hql = "delete from Allow t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", allow.getId());
		query.executeUpdate();
	}

	public List<Allow> getAllAllowList() throws DataAccessException {
		return null;
	}

	public List<Allow> getAllowListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return null;
	}

	public List<Allow> getAllowListByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		String hql="from Allow t where 1=1";
		if (column!=null&&!column.equals("")&&value!=null&&!value.equals("")) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		hql+=" order by t.intime desc";
		Query query=this.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public Integer getAllowListCountByParamForPage(String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return null;
	}

	public void updateAllow(Allow allow) throws DataAccessException {
		getEm().merge(allow);
	}

	public Allow getOneAllowById(String id) throws DataAccessException {
		String hql="from Allow t where t.id='"+id+"'";
		List<Allow> list=this.getEm().createQuery(hql).getResultList();
		
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public List<Allow> getAllAllowListByParam(Allow allow,Integer pageindex,Integer pagesize,String lastStr)
			throws DataAccessException {
		String hql="from Allow where 1=1 ";
		if (allow!=null) {
			if (CommonUtil.stringNotNULL(allow.getId())) {
				hql+=" and id='"+allow.getId()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getAllow_type())) {
				hql+=" and allow_type='"+allow.getAllow_type()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getGlid())) {
				hql+=" and glid='"+allow.getGlid()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getRole_type())) {
				hql+=" and role_type='"+allow.getRole_type()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getRole_typename())) {
				hql+=" and role_typename='"+allow.getRole_typename()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getRole_name())) {
				hql+=" and role_name='"+allow.getRole_name()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getWorkflowid())) {
				hql+=" and workflowid='"+allow.getWorkflowid()+"'";
			}
			if (CommonUtil.stringNotNULL(allow.getRole_id())) {
				hql+=" and role_id='"+allow.getRole_id()+"'";
			}
		}
		if (CommonUtil.stringNotNULL(lastStr)) {
			hql+=" "+lastStr;
		}else{
			hql+=" order by intime desc";
		}
		Query query=getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public List<Allow> getAllowsList(Allow allow) throws DataAccessException {
		return null;
	}
}
