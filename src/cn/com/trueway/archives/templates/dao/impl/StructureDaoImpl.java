package cn.com.trueway.archives.templates.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.templates.dao.StructureDao;
import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.pojo.EssModelTags;
import cn.com.trueway.archives.templates.pojo.EssStructureModel;
import cn.com.trueway.base.daoSupport.BaseDao;

@SuppressWarnings("unchecked")
public class StructureDaoImpl extends BaseDao implements StructureDao {

	@Override
	public int getStructureTempCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select count(*) from t_archive_structuremodel where 1=1");
		String esIdentifier = map.get("esIdentifier")!=null?map.get("esIdentifier").toString():"";
		String id = map.get("id")!=null?map.get("id").toString():"";
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			sql.append(" and esidentifier like ?");
		}
		if(id != null && !"".equals(id)) {
			sql.append(" and id = "+id);
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			query.setParameter(1, "%"+esIdentifier+"%");
		}
		BigDecimal count = (BigDecimal)query.getSingleResult();
		int all = 0;
		if(count == null) {
			all = 0;
		} else {
			all = count.intValue();
		}
		return all;
	}

	@Override
	public List<EssStructureModel> getStructureTempList(Map<String, Object> map) {
		List<EssStructureModel> eList = new ArrayList<EssStructureModel>();
		StringBuffer sql = new StringBuffer();
		sql.append("select s.id,s.business,s.esidentifier,s.esdescription,s.estype,b.estitle busname ");
		sql.append("from t_archive_structuremodel s left join t_archive_business b on s.business=b.esidentifier where 1=1");
		String esIdentifier = map.get("esIdentifier")!=null?map.get("esIdentifier").toString():"";
		String id = map.get("id")!=null?map.get("id").toString():"";
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			sql.append(" and s.esidentifier like ? ");
		}
		if(id != null && !"".equals(id)) {
			sql.append(" and s.id="+id);
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(esIdentifier != null && !"".equals(esIdentifier)) {
			query.setParameter(1, "%"+esIdentifier+"%");
		}
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		List<Object[]> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				EssStructureModel estm = new EssStructureModel();
				estm.setId(obj[0]==null?0:Integer.valueOf(obj[0].toString()));
				estm.setBusiness(obj[1]==null?"":obj[1].toString());
				estm.setEsIdentifier(obj[2]==null?"":obj[2].toString());
				estm.setEsDescription(obj[3]==null?"":obj[3].toString());
				estm.setEsType(obj[4]==null?"":obj[4].toString());
				estm.setEsBusinessName(obj[5]==null?"":obj[5].toString());
				eList.add(estm);
			}
		}
		return eList;
	}

	@Transactional
	@Override
	public void deleteStructureTempRecord(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_structuremodel t where 1=1");
		Integer id = (Integer)map.get("id");
		if(id != null) {
			sql.append(" and t.id=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(id != null) {
			query.setParameter(1, id);
		}
		query.executeUpdate();
		getEm().flush();
	}
	
	@Transactional
	@Override
	public void saveStructureTemp(EssStructureModel estm) {
		Integer id = estm.getId();
		if(id == null) {
			this.getEm().persist(estm);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_archive_structuremodel t set t.esidentifier=?,t.esdescription=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, estm.getEsIdentifier());
			query.setParameter(2, estm.getEsDescription());
			query.setParameter(3, estm.getId());
			query.executeUpdate();
		}
		this.getEm().flush();
	}

	@Override
	public int getModelTagsCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select count(*) from t_archive_modeltags where 1=1");
		Integer modelId = (Integer) map.get("modelId");
		if(modelId != null) {
			sql.append(" and modelid='"+modelId+"'");
		}
		String esType = (String)map.get("esType");
		if(esType != null && !"".equals(esType)) {
			sql.append(" and estype='"+esType+"'");
		}
		String tagName = (String)map.get("tagName");
		if(tagName != null && !"".equals(tagName)) {
			sql.append(" and tagname='"+tagName+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		BigDecimal count = (BigDecimal)query.getSingleResult();
		int all = 0;
		if(count == null) {
			all = 0;
		} else {
			all = count.intValue();
		}
		return all;
	}

	@Override
	public List<EssModelTags> getModelTagsList(Map<String, Object> map) {
		List<EssModelTags> list = new ArrayList<EssModelTags>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_modeltags where 1=1");
		Integer modelId = (Integer) map.get("modelId");
		if(modelId != null) {
			sql.append(" and modelid='"+modelId+"'");
		}
		String esType = (String)map.get("esType");
		if(esType != null && !"".equals(esType)) {
			sql.append(" and estype='"+esType+"'");
		}
		String id = (String)map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id='"+id+"'");
		}
		String tagName = (String)map.get("tagName");
		if(tagName != null && !"".equals(tagName)) {
			sql.append(" and tagname='"+tagName+"'");
		}
		sql.append(" order by vieworder");
		Query query = this.getEm().createNativeQuery(sql.toString(),EssModelTags.class);
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		list = query.getResultList();
		return list;
	}

	@Override
	public void updateModelTags(EssModelTags emt) {
		if(emt.getId() == null || "".equals(emt.getId())){
			emt.setId(null);
			this.getEm().persist(emt);
		}else{
			this.getEm().merge(emt);
		}
	}

	@Transactional
	@Override
	public void saveTagsMetaData(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("update T_ARCHIVE_MODELTAGS t set t.METADATAFULLNAME=?,t.ID_METADATA=? where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("metaDataFullName"));
		query.setParameter(2, map.get("id_MetaData"));
		query.setParameter(3, map.get("id"));
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public int checkIsJustOne(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_archive_modeltags where 1=1");
		String modelId = (String)map.get("modelId");
		if(modelId != null && !"".equals(modelId)) {
			sql.append(" and modelid='"+modelId+"'");
		}
		String esType = (String)map.get("esType");
		if(esType != null && !"".equals(esType)) {
			sql.append(" and estype='"+esType+"'");
		}
		String id = (String)map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id<>'"+id+"'");
		}
		String metaDataFullName = (String)map.get("metaDataFullName");
		if(metaDataFullName != null && !"".equals(metaDataFullName)) {
			sql.append(" and metadatafullname='"+metaDataFullName+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		BigDecimal count = (BigDecimal)query.getSingleResult();
		int all = 0;
		if(count == null) {
			all = 0;
		} else {
			all = count.intValue();
		}
		return all;
	}

}
