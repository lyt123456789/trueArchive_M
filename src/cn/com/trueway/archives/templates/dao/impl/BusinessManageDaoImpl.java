package cn.com.trueway.archives.templates.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.templates.dao.BusinessManageDao;
import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.base.daoSupport.BaseDao;

@SuppressWarnings("unchecked")
public class BusinessManageDaoImpl extends BaseDao implements BusinessManageDao {

	@Override
	public int getBusinessManageCount() {
		String sql = "select count(*) from t_archive_business";
		BigDecimal count = (BigDecimal) this.getEm().createNativeQuery(sql).getSingleResult();
		int all = 0;
		if(count == null) {
			all = 0;
		} else {
			all = count.intValue();
		}
		return all;
	}

	@Override
	public List<EssBusiness> getBusinessManageList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_business where 1=1");
		String id = (String)map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id='"+id+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(),EssBusiness.class);
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}

	@Override
	public List<EssBusiness> checkRecordByMap(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_business where 1=1");
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id!='"+id+"'");
		}
		String identifier = (String) map.get("identifier");
		if(identifier != null && !"".equals(identifier)) {
			sql.append(" and esidentifier='"+identifier+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(),EssBusiness.class);
		List<EssBusiness> list = query.getResultList();
		return list;
	}
	
	@Transactional
	@Override
	public void saveBusinessRecord(Map<String, Object> map) {
		String id = (String) map.get("id");
		String esTitle = (String) map.get("esTitle");
		String esIdentifier = (String) map.get("esIdentifier");
		String esType = (String) map.get("esType");
		String esDescription = (String) map.get("esDescription");
		String esUrl = (String) map.get("esUrl");
		if("newAdd".equals(id)) {
			EssBusiness ebs = new EssBusiness();
			ebs.setEsTitle(esTitle);
			ebs.setEsIdentifier(esIdentifier);
			ebs.setEsType(esType);
			ebs.setEsDescription(esDescription);
			ebs.setEsUrl(esUrl);
			this.getEm().persist(ebs);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_archive_business t set t.esurl=?,t.esidentifier=?,t.estype=?,t.esdescription=?,t.estitle=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, esUrl);
			query.setParameter(2, esIdentifier);
			query.setParameter(3, esType);
			query.setParameter(4, esDescription);
			query.setParameter(5, esTitle);
			query.setParameter(6, id);
			query.executeUpdate();
		}
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void deleteBusinessRecord(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_business t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
}
