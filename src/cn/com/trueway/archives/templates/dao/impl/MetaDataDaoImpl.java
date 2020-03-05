package cn.com.trueway.archives.templates.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.templates.dao.MetaDataDao;
import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssProp;
import cn.com.trueway.archives.templates.pojo.EssPropValue;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.UuidGenerator;

@SuppressWarnings("unchecked")
public class MetaDataDaoImpl extends BaseDao implements MetaDataDao {

	@Override
	public int getNameSpaceListCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_archive_namespace where 1=1");
		String estitle = (String) map.get("esTitle");
		if(estitle != null && !"".equals(estitle)) {
			sql.append(" and estitle like ?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(estitle != null && !"".equals(estitle)) {
			query.setParameter(1, "%"+estitle+"%");
		}
		BigDecimal count = (BigDecimal) query.getSingleResult();
		if(count !=null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	@Override
	public List<Map<String, String>> getNameSpaceList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,esurl,esidentifier,esdate,esdescription,escreator,esversion,estitle from t_archive_namespace where 1=1");
		String estitle = (String) map.get("esTitle");
		if(estitle != null && !"".equals(estitle)) {
			sql.append(" and estitle like ?");
		}
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(estitle != null && !"".equals(estitle)) {
			query.setParameter(1, "%"+estitle+"%");
		}
		if(id != null && !"".equals(id)) {
			query.setParameter(1, id);
		}
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		List<Object[]> lst = query.getResultList();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i = 0; i < lst.size(); i++) {
			Object[] obj = lst.get(i);
			Map<String,String> oMap = new HashMap<String,String>();
			oMap.put("id", obj[0]==null?"":obj[0].toString());
			oMap.put("esUrl", obj[1]==null?"":obj[1].toString());
			oMap.put("esIdentifier", obj[2]==null?"":obj[2].toString());
			oMap.put("esDate", obj[3]==null?"":obj[3].toString());
			oMap.put("esDescription", obj[4]==null?"":obj[4].toString());
			oMap.put("esCreator", obj[5]==null?"":obj[5].toString());
			oMap.put("esVersion", obj[6]==null?"":obj[6].toString());
			oMap.put("esTitle", obj[7]==null?"":obj[7].toString());
			list.add(oMap);
		}
		return list;
	}
	
	@Transactional
	@Override
	public void deleteOneNameSpaceById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_namespace t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Transactional
	@Override
	public void saveNameSpaceRecord(Map<String, Object> map) {
		String id = (String) map.get("id");
		String esTitle = (String) map.get("esTitle");
		String esIdentifier = (String) map.get("esIdentifier");
		String esDescription = (String) map.get("esDescription");
		String esUrl = (String) map.get("esUrl");
		String esDate = (String) map.get("esDate");
		String esCreator = (String) map.get("esCreator");
		String esVersion = (String) map.get("esVersion");
		if("newAdd".equals(id)) {
			id = UuidGenerator.generate36UUID();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into t_archive_namespace(id,esurl,esidentifier,esdate,esdescription,escreator,esversion,estitle) values(?,?,?,?,?,?,?,?)");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, id);
			query.setParameter(2, esUrl);
			query.setParameter(3, esIdentifier);
			query.setParameter(4, esDate);
			query.setParameter(5, esDescription);
			query.setParameter(6, esCreator);
			query.setParameter(7, esVersion);
			query.setParameter(8, esTitle);
			query.executeUpdate();
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_archive_namespace t set t.esurl=?,t.esdate=?,t.esdescription=?,t.escreator=?,t.esversion=?,t.estitle=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, esUrl);
			query.setParameter(2, esDate);
			query.setParameter(3, esDescription);
			query.setParameter(4, esCreator);
			query.setParameter(5, esVersion);
			query.setParameter(6, esTitle);
			query.setParameter(7, id);
			query.executeUpdate();
		}
		this.getEm().flush();
		
	}

	@Override
	public int getMetaDataListCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_archive_metadata where 1=1");
		String nameSpaceId = (String) map.get("nameSpaceId");
		if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			sql.append(" and id_namespace=?");
		}
		String estitle = (String) map.get("esTitle");
		if(estitle != null && !"".equals(estitle)) {
			sql.append(" and estitle like ?");
		}
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id='"+id+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			query.setParameter(1, nameSpaceId);
		}
		if(estitle != null && !"".equals(estitle)) {
			query.setParameter(2, "%"+estitle+"%");
		}
		BigDecimal count = (BigDecimal) query.getSingleResult();
		if(count !=null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	@Override
	public List<EssMetaData> getMetaDataList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_metadata where 1=1");
		String nameSpaceId = (String) map.get("nameSpaceId");
		if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			sql.append(" and id_namespace='"+nameSpaceId+"'");
		}
		String estitle = (String) map.get("esTitle");
		if(estitle != null && !"".equals(estitle)) {
			sql.append(" and estitle like '%"+estitle+"%'");
		}
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id='"+id+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(),EssMetaData.class);
		/*if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			query.setParameter(1, nameSpaceId);
		}
		if(estitle != null && !"".equals(estitle)) {
			query.setParameter(2, "%"+estitle+"%");
		}*/
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		List<EssMetaData> list = query.getResultList();
		return list;
	}

	@Transactional
	@Override
	public void deleteMetaDataById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_metadata t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}

	@Override
	public List<EssMetaData> checkMetaDataRecord(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_metadata where 1=1");
		String esIdentifier = (String)map.get("esIdentifier");
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			sql.append(" and esidentifier=?");
		}
		String nameSpaceId = (String)map.get("nameSpaceId");
		if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			sql.append(" and id_namespace=?");
		}
		String id = (String)map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id!=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(),EssMetaData.class);
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			query.setParameter(1, esIdentifier);
		}
		if(nameSpaceId != null && !"".equals(nameSpaceId)) {
			query.setParameter(2, nameSpaceId);
		}
		if(id != null && !"".equals(id)) {
			query.setParameter(3, id);
		}
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void saveMetaDataRecord(EssMetaData emda) {
		String id = emda.getId();
		if("newAdd".equals(id)) {
			emda.setId(null);
			this.getEm().persist(emda);
		} else {
			this.getEm().merge(emda);
		}
		this.getEm().flush();
	}

	@Override
	public EssProp getMetaDataPorp(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_prop where 1=1");
		String metaDataId = (String) map.get("metaDataId");
		if(metaDataId != null && !"".equals(metaDataId)) {
			sql.append(" and id_metadata=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(), EssProp.class);
		if(metaDataId != null && !"".equals(metaDataId)) {
			query.setParameter(1, metaDataId);
		}
		List<EssProp> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<EssPropValue> getMetaDataPropValue(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_propvalue where 1=1");
		String idProp = (String) map.get("idProp");
		if(idProp != null && !"".equals(idProp)) {
			sql.append(" and id_prop=?");
		}
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString(), EssPropValue.class);
		if(idProp != null && !"".equals(idProp)) {
			query.setParameter(1, idProp);
		}
		if(id != null && !"".equals(id)) {
			query.setParameter(1, id);
		}
		List<EssPropValue> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public void deleteMeDaPropertyById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_propvalue t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}

	@Override
	public void saveMeDaPropertyRecord(EssPropValue epve) {
		String id = epve.getId();
		if("newAdd".equals(id)) {
			epve.setId(null);
			this.getEm().persist(epve);
		} else {
			this.getEm().merge(epve);
		}
		this.getEm().flush();
	}

	@Override
	public String saveMeDaPropRela(EssProp ep) {
		this.getEm().persist(ep);
		this.getEm().flush();
		return ep.getId();
	}
}
