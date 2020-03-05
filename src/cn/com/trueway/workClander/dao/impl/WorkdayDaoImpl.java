package cn.com.trueway.workClander.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workClander.dao.WorkdayDao;
import cn.com.trueway.workClander.pojo.Workday;

public class WorkdayDaoImpl extends BaseDao implements WorkdayDao{

	public void delete(Workday workday) throws DataAccessException {
		String sql="delete from workday t where t.id = '"+workday.getId()+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	public List<Workday> getAllWorkday() throws DataAccessException {
		String hql = "from Workday t order by t.year desc,t.time desc";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}

	public Workday getOneWorkdayById(String id) throws DataAccessException {
		String hql = "from Workday t where t.id=:id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", id);
		List<Workday> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public Integer getWorkdayCountForPage(String column, String value,
			Workday workday) {
		String hql = "select count (*) from (select t.year from T_WF_CORE_WORKDAY t group by t.year)";
		Query query = super.getEm().createNativeQuery(hql);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	public Integer getAllCount(){
		String hql = "select count(*) from Workday t";
		Query query = super.getEm().createQuery(hql);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	public List<Workday> getWorkdayListForPage(String column, String value,
			Workday workday, Integer pageindex, Integer pagesize) {
		String hql = "select new Workday(t.year) from Workday t group by t.year";
		Query query = super.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public void save(Workday workday) throws DataAccessException {
		getEm().persist(workday);
	}

	public void update(Workday workday) throws DataAccessException {
		getEm().merge(workday);
	}

	public void deleteByYear(String year) throws DataAccessException {
		String sql="delete from T_WF_CORE_WORKDAY t where t.time like '"+year+"%'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	public List<Workday> getListByYear(String year) throws DataAccessException {
		String hql="from Workday t where t.time like '%"+year+"%'";
		return getEm().createQuery(hql).getResultList();
	}

}
