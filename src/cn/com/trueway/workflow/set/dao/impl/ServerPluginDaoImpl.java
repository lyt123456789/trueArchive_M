package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.set.dao.ServerPluginDao;
import cn.com.trueway.workflow.set.pojo.ServerPlugin;

public class ServerPluginDaoImpl extends BaseDao implements ServerPluginDao{
	public void addServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		super.getEm().persist(serverPlugin);
	}

	public void deleteServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		String hql = "delete from ServerPlugin t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", serverPlugin.getId());
		query.executeUpdate();
	}

	public List<ServerPlugin> getAllServerPluginList() throws DataAccessException {
		String hql="from ServerPlugin t where 1=1";
		hql+=" order by t.intime desc";
		return getEm().createQuery(hql).getResultList();
	}

	public List<ServerPlugin> getServerPluginListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return null;
	}

	public List<ServerPlugin> getServerPluginListByParamForPage(ServerPlugin serverPlugin,String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		String hql="from ServerPlugin t where 1=1";
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

	public Integer getServerPluginListCountByParamForPage(ServerPlugin serverPlugin,String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		String hql="select count(*) from ServerPlugin t where 1=1";
		if (column!=null&&!column.equals("")&&value!=null&&!value.equals("")) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		hql+=" order by t.intime desc";
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	public void updateServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		getEm().merge(serverPlugin);
	}

	public ServerPlugin getOneServerPluginById(String id) throws DataAccessException {
		String hql="from ServerPlugin t where t.id='"+id+"'";
		List<ServerPlugin> list=this.getEm().createQuery(hql).getResultList();
		
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public List<ServerPlugin> getAllServerPluginListByParam(ServerPlugin serverPlugin,Integer pageindex,Integer pagesize,String lastStr)
			throws DataAccessException {
		String hql="from ServerPlugin where 1=1 ";
		if (serverPlugin!=null) {
			if (CommonUtil.stringNotNULL(serverPlugin.getId())) {
				hql+=" and id='"+serverPlugin.getId()+"'";
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

	public List<ServerPlugin> getServerPluginsList(ServerPlugin serverPlugin) throws DataAccessException {
		return null;
	}
}
