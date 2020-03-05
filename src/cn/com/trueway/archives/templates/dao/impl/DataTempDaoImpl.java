package cn.com.trueway.archives.templates.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.templates.dao.DataTempDao;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.pojo.MrzPojo;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.UuidGenerator;


@SuppressWarnings("unchecked")
public class DataTempDaoImpl extends BaseDao implements DataTempDao {

	
	@Override
	public int TreeCountByMap(Map<String, String> map) {
		String hql = " from EssTree where 1=1 ";
		String business =  map.get("business");
		if(CommonUtil.stringNotNULL(business)){
			hql += " and idBusiness = "+business;
		}
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = "+id;
		}
		String parentId =  map.get("parentId");
		if(CommonUtil.stringNotNULL(parentId)){
			hql += " and idParent = "+parentId;
		}
		hql += "   order by esOrder ";
		int  count = this.getEm().createQuery(hql).getResultList().size();
		return count;
	}
	
	@Override
	public List<EssTree> getTreeByMap(Map<String, String> map,
			Integer pageSize, Integer pageIndex) {
		String hql = " from EssTree where 1=1 ";
		String business =  map.get("business");
		if(CommonUtil.stringNotNULL(business)){
			hql += " and idBusiness = "+"'"+business+"'";
		}
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = "+id;
		}
		String parentId =  map.get("parentId");
		if(CommonUtil.stringNotNULL(parentId)){
			hql += " and idParent = "+parentId;
		}
		hql += "   order by esOrder ";
		Query query = this.getEm().createQuery(hql);
		if(pageSize!=null&&pageIndex!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Override
	public void updateEssTree(EssTree et) {
		if(et.getId() == null || "".equals(et.getId())){
			et.setId(null);
			this.getEm().persist(et);
		}else{
			this.getEm().merge(et);
		}
	}

	@Override
	public void updateNativeSql(String sql) {
		this.getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public List<EssStructure> getStructureList(
			Map<String, String> map, Integer pageSize, Integer pageIndex) {
		String type =  map.get("type");
		String structureId =  map.get("structureId");
		String treeId =  map.get("treeId");
		if("dg".equals(type)){//正递归查询，查询下级
			String sql="select * from T_ARCHIVE_STRUCTURE  where id in "
					  + " (select d.id_childstructure from T_ARCHIVE_CHILDSTRUCTURE d  where  d.esstatus='启用' "
                      + "    connect by d.id_structure = prior d.id_childstructure "
                      + "    start with d.id_childstructure = "+structureId+")" //+" and d.id_package="+treeId+""
                      + " order by id";
			return  this.getEm().createNativeQuery(sql,EssStructure.class).getResultList();
		}if("-dg".equals(type)){//反递归查询，查询上级
			String sql="select * from T_ARCHIVE_STRUCTURE  where id in "
					  + " (select d.id_childstructure from T_ARCHIVE_CHILDSTRUCTURE d  where  d.esstatus='启用' "
                    + "    connect by d.id_childstructure = prior  d.id_structure"
                    + "    start with d.id_childstructure = "+structureId +")"//+" and d.id_package="+treeId+")"
                    + " order by id";
			return  this.getEm().createNativeQuery(sql,EssStructure.class).getResultList();
		}else{
			String hql = " from EssStructure where 1=1 ";
			if(CommonUtil.stringNotNULL(structureId)){
				hql += " and id = "+structureId;
			}
			hql += "   order by esOrder ";
			Query query = this.getEm().createQuery(hql);
			if(pageSize!=null&&pageIndex!=null){
				query.setFirstResult(pageIndex);// 从哪条记录开始
				query.setMaxResults(pageSize);// 每页显示的最大记录数
			}
			return query.getResultList();
		}
	}

	@Override
	public void updateStructure(EssStructure es) {
		if(es.getId() == null || "".equals(es.getId())){
			es.setId(null);
			this.getEm().persist(es);
		}else{
			this.getEm().merge(es);
		}
	}

	@Override
	public void updateTag(EssTag etag) {
		if(etag.getId() == null || "".equals(etag.getId())){
			etag.setId(null);
			this.getEm().persist(etag);
		}else{
			this.getEm().merge(etag);
		}
	}

	@Override
	public List<Object[]> excuteNativeSql(String sql) {
		List<Object[]> list = this.getEm().createNativeQuery(sql).getResultList(); 
		return list;
	}

	@Override
	public int getEssTagCount(Map<String, String> map) {
		String hql = " from EssTag where 1=1 ";
		String structureId =  map.get("structureId");
		if(CommonUtil.stringNotNULL(structureId)){
			hql += " and idStructure = "+structureId;
		}
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = "+id;
		}
		String esIdentifier =  map.get("esIdentifier");
		if(CommonUtil.stringNotNULL(esIdentifier)){
			hql += " and esIdentifier = '"+esIdentifier+"'";
		}
		String isNotSystem =  map.get("isNotSystem");
		if("1".equals(isNotSystem)){
			hql += " and esDotlength is not null ";
		}
		hql += "   order by esOrder ";
		int  count = this.getEm().createQuery(hql).getResultList().size();
		return count;
	}

	@Override
	public List<EssTag> getEssTagList(Map<String, String> map, Integer pageSize, Integer pageIndex) {
		String hql = " from EssTag where 1=1 ";
		String structureId =  map.get("structureId");
		if(CommonUtil.stringNotNULL(structureId)){
			hql += " and idStructure = "+structureId;
		}
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = "+id;
		}
		String esIdentifier =  map.get("esIdentifier");
		if(CommonUtil.stringNotNULL(esIdentifier)){
			hql += " and esIdentifier = '"+esIdentifier+"'";
		}
		String esType = map.get("esType");
		if(CommonUtil.stringNotNULL(esType)){
			hql += " and esType in ("+ esType +")";
		}
		String isNotSystem =  map.get("isNotSystem");
		if("1".equals(isNotSystem)){
			hql += " and esDotlength is not null ";
		}
		hql += "   order by esOrder ";
		Query query = this.getEm().createQuery(hql);
		if(pageSize!=null&&pageIndex!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Transactional
	@Override
	public void saveTagsMetaData(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_archive_tag t set t.NAME_METADATA=?,t.ID_METADATA=? where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("metaDataFullName"));
		query.setParameter(2, map.get("id_MetaData"));
		query.setParameter(3, map.get("id"));
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public int checkIsJustOne(Map<String,Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_archive_tag where 1=1");
		String structureId = (String)map.get("structureId");
		if(structureId != null && !"".equals(structureId)) {
			sql.append(" and id_structure='"+structureId+"'");
		}
		String id = (String)map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id<>'"+id+"'");
		}
		String metaDataFullName = (String)map.get("metaDataFullName");
		if(metaDataFullName != null && !"".equals(metaDataFullName)) {
			sql.append(" and name_metadata='"+metaDataFullName+"'");
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
	public List<EssZDXZCommon> getZDXZDataList(Map<String, String> map) {
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		String tableFlag = map.get("tableFlag");
		if(tableFlag == null || "".equals(tableFlag)) {
			return null;
		}
		String tableName = null;
		switch(tableFlag){
		    case "grid" :
		    	tableName = " t_archive_displayfieldofgrid t";
		        break;
		    case "ofForm" :
		    	tableName = " t_archive_displayfieldofform t";
		        break;
		    case "adSearch" :
		    	tableName = " t_archive_advancesearchfield t";
		        break;
		    case "ofDdto" :
		    	tableName = " t_archive_displayfieldofddto t";
		        break;
		    case "usingForm" :
		    	tableName = " t_archive_usingformfield t";
		        break;
		    case "usingGrid" :
		    	tableName = " t_archive_usinggridfield t";
		        break;
		    case "abstract" :
		    	tableName = " t_archive_displayfieldabstract t";
		        break;
		    case "fieldCode" :
		    	tableName = " t_archive_fieldcode t";
		        break;
		    default:tableName="";
		}
		if(tableName == null || "".equals(tableName)) {
			return null;
		}
		String structureId = map.get("structureId");
		StringBuffer sql = new StringBuffer();
		sql.append("select g.esidentifier tagName,g.id idtag,t.id_structure idstructure,t.esorder from");
		sql.append(tableName);
		sql.append(" left join t_archive_tag g on t.id_tag=g.id where 1=1");
		if(structureId != null && !"".equals(structureId)) {
			sql.append(" and t.id_structure='"+structureId+"'");
		}
		sql.append(" order by t.esorder asc");
		Query query = this.getEm().createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon eszc = new EssZDXZCommon();
			eszc.setTagName(obj[0]==null?"":obj[0].toString());
			eszc.setIdtag(obj[1]==null?0:Integer.valueOf(obj[1].toString()));
			eszc.setIdstructure(obj[2]==null?0:Integer.valueOf(obj[2].toString()));
			eszc.setEsorder(obj[3]==null?0:Integer.valueOf(obj[3].toString()));
			returnList.add(eszc);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void deleteZDXZDataOfTable(String tableFlag, String idStructure) {
		String tableName = null;
		switch(tableFlag){
		    case "grid" :
		    	tableName = " t_archive_displayfieldofgrid";
		        break;
		    case "ofForm" :
		    	tableName = " t_archive_displayfieldofform";
		        break;
		    case "adSearch" :
		    	tableName = " t_archive_advancesearchfield";
		        break;
		    case "ofDdto" :
		    	tableName = " t_archive_displayfieldofddto";
		        break;
		    case "usingForm" :
		    	tableName = " t_archive_usingformfield";
		        break;
		    case "usingGrid" :
		    	tableName = " t_archive_usinggridfield";
		        break;
		    case "abstract" :
		    	tableName = " t_archive_displayfieldabstract";
		        break;
		    case "fieldCode" :
		    	tableName = " t_archive_fieldcode t";
		        break;
		    default:tableName="";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("delete from" + tableName + " where id_structure='" + idStructure + "'");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.executeUpdate();
		this.getEm().flush();
	}

	@Transactional
	@Override
	public void saveZDXZDataOfTable(Map<String, String> map, String tableFlag) {
		String tableName = null;
		switch(tableFlag){
		    case "grid" :
		    	tableName = " t_archive_displayfieldofgrid";
		        break;
		    case "ofForm" :
		    	tableName = " t_archive_displayfieldofform";
		        break;
		    case "adSearch" :
		    	tableName = " t_archive_advancesearchfield";
		        break;
		    case "ofDdto" :
		    	tableName = " t_archive_displayfieldofddto";
		        break;
		    case "usingForm" :
		    	tableName = " t_archive_usingformfield";
		        break;
		    case "usingGrid" :
		    	tableName = " t_archive_usinggridfield";
		        break;
		    case "abstract" :
		    	tableName = " t_archive_displayfieldabstract";
		        break;
		    case "fieldCode" :
		    	tableName = " t_archive_fieldcode t";
		        break;
		    default:tableName="";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("insert into" + tableName + " (id_structure,id_tag,esorder) values (?,?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("idstructure"));
		query.setParameter(2, map.get("idtag"));
		query.setParameter(3, map.get("esorder"));
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<EssZDXZCommon> getPXDataList(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id_structure idstructure,rule from t_archive_sortfield where id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon ezcn = new EssZDXZCommon();
			ezcn.setIdstructure(obj[0]==null?0:Integer.valueOf(obj[0].toString()));
			ezcn.setRule(obj[1]==null?"":obj[1].toString());
			returnList.add(ezcn);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void deletePxData(String idStructure) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_sortfield where id_structure='" + idStructure + "'");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void savePxData(String sortString, String idstructure) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_sortfield (id_structure,rule) values (?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, idstructure);
		query.setParameter(2, sortString);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<EssZDXZCommon> getZDZDataList(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,id_structure,id_tag,tag_ids,esorder from t_archive_computefields where 1=1");
		String structureId = map.get("structureId");
		if(structureId != null && !"".equals("structureId")) {
			sql.append(" and id_structure=?");
		}
		String idTag = map.get("idTag");
		if(idTag != null && !"".equals(idTag)) {
			sql.append(" and id_tag=?");
		}
		sql.append(" order by esorder");
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(structureId != null && !"".equals("structureId")) {
			query.setParameter(1, structureId);
		}
		if(idTag != null && !"".equals(idTag)) {
			query.setParameter(2, idTag);
		}
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon ezcn = new EssZDXZCommon();
			ezcn.setId(obj[0]==null?"":obj[0].toString());
			ezcn.setIdstructure(obj[1]==null?0:Integer.valueOf(obj[1].toString()));
			ezcn.setIdtag(obj[2]==null?0:Integer.valueOf(obj[2].toString()));
			ezcn.setTagIds(obj[3]==null?"":obj[3].toString());
			ezcn.setEsorder(obj[4]==null?0:Integer.valueOf(obj[4].toString()));
			returnList.add(ezcn);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void insertAllZDZDataList(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_computefields (id,id_structure,id_tag,esorder");
		String tagIds = map.get("tagIds");
		if(tagIds != null && !"".equals(tagIds)) {
			sql.append(",tag_ids");
		}
		sql.append(") values(?,?,?,?");
		if(tagIds != null && !"".equals(tagIds)) {
			sql.append(",?");
		}
		sql.append(")");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, UuidGenerator.generate36UUID());
		query.setParameter(2, map.get("idStructure"));
		query.setParameter(3, map.get("idTag"));
		query.setParameter(4, map.get("esorder"));
		if(tagIds != null && !"".equals(tagIds)) {
			query.setParameter(5, map.get("tagIds"));
		}
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void deleteAllZDZData(String structureId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_computefields where id_structure='" + structureId + "'");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void deleteFileCodeProp(int idstructure, int idTag) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_computefields where 1=1 and id_structure=? and id_tag=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, idstructure);
		query.setParameter(2, idTag);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<EssZDXZCommon> getDataOfDMZList(String structureId, String idTag) {
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_fieldcode where 1=1 and id_structure=? and id_tag=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, structureId);
		query.setParameter(2, idTag);
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon eszc = new EssZDXZCommon();
			eszc.setIdstructure(obj[0]==null?0:Integer.valueOf(obj[0].toString()));
			eszc.setIdtag(obj[1]==null?0:Integer.valueOf(obj[1].toString()));
			eszc.setEsorder(obj[2]==null?0:Integer.valueOf(obj[2].toString()));
			returnList.add(eszc);
		}
		return returnList;
	}

	@Override
	public List<Map<String, String>> getFieldCodeProp(Map<String, String> map) {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,id_structure,id_tag,tag_propvalue,tag_codevalue,tag_description from t_archive_fieldcodeprop where 1=1 ");
		String structureId = map.get("structureId");
		String idTag = map.get("idTag");
		String propValue = map.get("propValue");
		if(structureId!=null&&!"".equals(structureId)){
			sql.append(" and id_structure="+structureId);
		}
		if(idTag!=null&&!"".equals(idTag)){
			sql.append(" and id_tag="+idTag);
		}
		if(propValue!=null&&!"".equals(propValue)){
			sql.append(" and tag_propValue='"+propValue+"'");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		/*query.setParameter(1, map.get("structureId"));
		query.setParameter(2, map.get("idTag"));*/
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			Map<String,String> mapIn = new HashMap<String,String>();
			mapIn.put("id",obj[0]==null?"":obj[0].toString());
			mapIn.put("idStructure", obj[1]==null?"":obj[1].toString());
			mapIn.put("idTag", obj[2]==null?"":obj[2].toString());
			mapIn.put("tagPropValue", obj[3]==null?"":obj[3].toString());
			mapIn.put("tagCodeValue", obj[4]==null?"":obj[4].toString());
			mapIn.put("description", obj[5]==null?"":obj[5].toString());
			returnList.add(mapIn);
		}
		return returnList;
	}

	@Override
	public List<Map<String, String>> checkDMZPropIsHave(Map<String, String> map) {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,id_structure,id_tag,tag_propvalue,tag_codevalue,tag_description from t_archive_fieldcodeprop where 1=1");
		String structureId = map.get("structureId");
		if(structureId!= null && !"".equals(structureId)) {
			sql.append(" and id_structure=?");
		}
		String idTag = map.get("idTag");
		if(idTag != null && !"".equals(idTag)) {
			sql.append(" and id_tag=?");
		}
		String tagPropValue = map.get("tagPropValue");
		if(tagPropValue != null && !"".equals(tagPropValue)) {
			sql.append(" and tag_propvalue=?");
		}
		String id = map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id!=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(structureId!= null && !"".equals(structureId)) {
			query.setParameter(1, structureId);
		}
		if(idTag != null && !"".equals(idTag)) {
			query.setParameter(2, idTag);
		}
		if(tagPropValue != null && !"".equals(tagPropValue)) {
			query.setParameter(3, tagPropValue);
		}
		if(id != null && !"".equals(id)) {
			query.setParameter(4, id);
		}
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			Map<String,String> mapIn = new HashMap<String,String>();
			mapIn.put("id",obj[0]==null?"":obj[0].toString());
			mapIn.put("idStructure", obj[1]==null?"":obj[1].toString());
			mapIn.put("idTag", obj[2]==null?"":obj[2].toString());
			mapIn.put("tagPropValue", obj[3]==null?"":obj[3].toString());
			mapIn.put("tagCodeValue", obj[4]==null?"":obj[4].toString());
			mapIn.put("description", obj[5]==null?"":obj[5].toString());
			returnList.add(mapIn);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void saveDMZPropData(Map<String, String> map) {
		String id = map.get("id");
		StringBuffer sql = new StringBuffer();
		if(id == null || "".equals(id)) {
			sql.append("insert into t_archive_fieldcodeprop (id,id_structure,id_tag,tag_propvalue,tag_codevalue,tag_description) values(?,?,?,?,?,?)");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, UuidGenerator.generate36UUID());
			query.setParameter(2, map.get("structureId"));
			query.setParameter(3, map.get("idTag"));
			query.setParameter(4, map.get("tagPropValue"));
			query.setParameter(5, map.get("tagCodeValue"));
			query.setParameter(6, map.get("description"));
			query.executeUpdate();
		} else {
			sql.append("update t_archive_fieldcodeprop set id_structure=?,id_tag=?,tag_propvalue=?,tag_codevalue=?,tag_description=? where id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, map.get("structureId"));
			query.setParameter(2, map.get("idTag"));
			query.setParameter(3, map.get("tagPropValue"));
			query.setParameter(4, map.get("tagCodeValue"));
			query.setParameter(5, map.get("description"));
			query.setParameter(6, id);
			query.executeUpdate();
		}
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void deleteDMZPropData(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_fieldcodeprop where 1=1 and id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<EssZDXZCommon> getBLDataList(Map<String, String> map) {
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id_structure,id_tag,fillzeronumber,esorder from t_archive_fieldfillzero where id_structure=? order by esorder");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon ez = new EssZDXZCommon();
			ez.setIdstructure(obj[0]==null?0:Integer.valueOf(obj[0].toString()));
			ez.setIdtag(obj[1]==null?0:Integer.valueOf(obj[1].toString()));
			ez.setZeroNumber(obj[2]==null?0:Integer.valueOf(obj[2].toString()));
			ez.setEsorder(obj[3]==null?0:Integer.valueOf(obj[3].toString()));
			returnList.add(ez);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void deleteDataOfBL(String structureId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_fieldfillzero where 1=1 and id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, structureId);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Transactional
	@Override
	public void saveDataOfBL(Map<String, String> map) {
		int idstructure = Integer.valueOf(map.get("idstructure"));
		int tagId = Integer.valueOf(map.get("tagId"));
		int zeroNumber = Integer.valueOf(map.get("zeroNumber"));
		int esorder = Integer.valueOf(map.get("esorder"));
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_fieldfillzero (id_structure,id_tag,fillzeronumber,esorder) values(?,?,?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1,idstructure);
		query.setParameter(2,tagId);
		query.setParameter(3,zeroNumber);
		query.setParameter(4,esorder);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<EssZDXZCommon> getZHZDDataList(Map<String, String> map) {
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id_structure,id_tag,tag_ids,esorder from t_archive_combinfield where 1=1");
		sql.append(" and id_structure=?");
		String idTag = map.get("idTag");
		if(idTag != null && !"".equals(idTag)) {
			sql.append(" and id_tag=?");
		}
		sql.append(" order by esorder");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		if(idTag != null && !"".equals(idTag)) {
			query.setParameter(2, idTag);
		}
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			EssZDXZCommon ez = new EssZDXZCommon();
			ez.setIdstructure(obj[0]==null?0:Integer.valueOf(obj[0].toString()));
			ez.setIdtag(obj[1]==null?0:Integer.valueOf(obj[1].toString()));
			ez.setTagIds(obj[2]==null?"":obj[2].toString());
			ez.setEsorder(obj[3]==null?0:Integer.valueOf(obj[3].toString()));
			returnList.add(ez);
		}
		return returnList;
	}
	
	@Transactional
	@Override
	public void deleteDataOfZHZD(String structureId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_combinfield where 1=1 and id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, structureId);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void saveDataOfZHZD(Map<String, String> map) {
		int idStructure = Integer.valueOf(map.get("idStructure"));
		int idTag = Integer.valueOf(map.get("idTag"));
		String tagIds = map.get("tagIds");
		int esorder = Integer.valueOf(map.get("esorder"));
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_combinfield (id_structure,id_tag,tag_ids,esorder) values(?,?,?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1,idStructure);
		query.setParameter(2,idTag);
		query.setParameter(3,tagIds);
		query.setParameter(4,esorder);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<Map<String, String>> getZHZDPropDataList(Map<String, String> map) {
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		String structureId = map.get("structureId");
		String parentTagId = map.get("idTag");
		String tagId = map.get("childrenTagId");
		StringBuffer sql = new StringBuffer();
		sql.append("select id,id_structure,parenttagid,tag_id,tag_prepropvalue,tag_propvalue,oper,tag_description");
		sql.append(" from t_archive_combinfieldprop where 1=1");
		if(structureId != null && !"".equals(structureId)) {
			sql.append(" and id_structure=?");
		}
		if(parentTagId != null && !"".equals(parentTagId)) {
			sql.append(" and parenttagid=?");
		}
		if(tagId != null && !"".equals(tagId)) {
			sql.append(" and tag_id=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(structureId != null && !"".equals(structureId)) {
			query.setParameter(1, structureId);
		}
		if(parentTagId != null && !"".equals(parentTagId)) {
			query.setParameter(2, parentTagId);
		}
		if(tagId != null && !"".equals(tagId)) {
			query.setParameter(3, tagId);
		}
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			Map<String,String> returnMap = new HashMap<String,String>();
			returnMap.put("id", obj[0]==null?"":obj[0].toString());
			returnMap.put("idStructure", obj[1]==null?"":obj[1].toString());
			returnMap.put("parentTagId", obj[2]==null?"":obj[2].toString());
			returnMap.put("tagId", obj[3]==null?"":obj[3].toString());
			returnMap.put("prePropValue", obj[4]==null?"":obj[4].toString());
			returnMap.put("propValue", obj[5]==null?"":obj[5].toString());
			returnMap.put("oper", obj[6]==null?"":obj[6].toString());
			returnMap.put("description", obj[7]==null?"":obj[7].toString());
			returnList.add(returnMap);
		}
		return returnList;
	}

	@Override
	public List<Object[]> checkZHZDPropIsHave(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_combinfieldprop where 1=1");
		String idStructure = map.get("idStructure");
		if(idStructure!= null && !"".equals(idStructure)) {
			sql.append(" and id_structure=?");
		}
		String parentTagId = map.get("parentTagId");
		if(parentTagId != null && !"".equals(parentTagId)) {
			sql.append(" and parenttagid=?");
		}
		String tagId = map.get("tagId");
		if(tagId != null && !"".equals(tagId)) {
			sql.append(" and tag_id=?");
		}
		String prePropValue = map.get("prePropValue");
		if(prePropValue != null && !"".equals(prePropValue)) {
			sql.append(" and tag_prepropvalue=?");
		}
		String id = map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id!=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if(idStructure!= null && !"".equals(idStructure)) {
			query.setParameter(1, idStructure);
		}
		if(parentTagId!= null && !"".equals(parentTagId)) {
			query.setParameter(2, parentTagId);
		}
		if(tagId!= null && !"".equals(tagId)) {
			query.setParameter(3, tagId);
		}
		if(prePropValue!= null && !"".equals(prePropValue)) {
			query.setParameter(4, prePropValue);
		}
		if(id != null && !"".equals(id)) {
			query.setParameter(5, id);
		}
		List<Object[]> list = query.getResultList();
		if(list == null || list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}
	
	@Transactional
	@Override
	public void deleteZHZDPropData(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_combinfieldprop where 1=1 and id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public void saveZHZDPropData(Map<String, String> map) {
		String id = map.get("id");
		StringBuffer sql = new StringBuffer();
		if(id == null || "".equals(id)) {
			sql.append("insert into t_archive_combinfieldprop (");
			sql.append("id,id_structure,parenttagid,tag_id,tag_prepropvalue,tag_propvalue,oper,tag_description) values(?,?,?,?,?,?,?,?)");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, UuidGenerator.generate36UUID());
			query.setParameter(2, map.get("idStructure"));
			query.setParameter(3, map.get("parentTagId"));
			query.setParameter(4, map.get("tagId"));
			query.setParameter(5, map.get("prePropValue"));
			query.setParameter(6, map.get("propValue"));
			query.setParameter(7, map.get("oper"));
			query.setParameter(8, map.get("description"));
			query.executeUpdate();
		} else {
			sql.append("update t_archive_combinfieldprop set id_structure=?,parenttagid=?,tag_id=?,tag_prepropvalue=?,tag_propvalue=?,oper=?,tag_description=? where id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, map.get("idStructure"));
			query.setParameter(2, map.get("parentTagId"));
			query.setParameter(3, map.get("tagId"));
			query.setParameter(4, map.get("prePropValue"));
			query.setParameter(5, map.get("propValue"));
			query.setParameter(6, map.get("oper"));
			query.setParameter(7, map.get("description"));
			query.setParameter(8, id);
			query.executeUpdate();
		}
		this.getEm().flush();
		
	}

	@Override
	public Map<String, String> getDataOfJD(String structureId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id_structure,tagid_startenddate,tagid_preservationperiod from t_archive_checkuprule where 1=1");
		sql.append(" and id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, structureId);
		List<Object[]> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			Object[] obj = list.get(0);
			map.put("structureId", obj[0]==null?"":obj[0].toString());
			map.put("tagIdSE", obj[1]==null?"":obj[1].toString());
			map.put("tagIdSection", obj[2]==null?"":obj[2].toString());
			return map;
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, String>> getCheckUpPropData(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,id_structure,tag_key,tag_value,esorder,tagid_startenddate,tagid_preservationperiod");
		sql.append(" from t_archive_checkupkeyvalue where 1=1");
		sql.append(" and id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		List<Object[]> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			List<Map<String, String>> lstMap = new ArrayList<Map<String,String>>();
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				Map<String, String> dMap = new HashMap<String, String>();
				dMap.put("id", obj[0]==null?"":obj[0].toString());
				dMap.put("structureId", obj[1]==null?"":obj[1].toString());
				dMap.put("tagKey", obj[2]==null?"":obj[2].toString());
				dMap.put("tagValue", obj[3]==null?"":obj[3].toString());
				dMap.put("esorder", obj[4]==null?"":obj[4].toString());
				dMap.put("tagIdSE", obj[5]==null?"":obj[5].toString());
				dMap.put("tagIdSection", obj[6]==null?"":obj[6].toString());
				lstMap.add(dMap);
			}
			return lstMap;
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public void updateDataOfJD(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_archive_checkuprule set tagid_startenddate=?,tagid_preservationperiod=? where id_structure=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("tagIdSE"));
		query.setParameter(2, map.get("tagIdSection"));
		query.setParameter(3, map.get("structureId"));
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@Override
	public void insertDataOfJD(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_checkuprule (id_structure,tagid_startenddate,tagid_preservationperiod) values (?,?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		query.setParameter(2, map.get("tagIdSE"));
		query.setParameter(3, map.get("tagIdSection"));
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public void deleteZHZDPropData(String structureId, String PidTag,
			String idtag) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_combinfieldprop where 1=1");
		if(structureId!=null&&!"".equals(structureId)){
			sql.append(" and id_structure="+structureId);
		}
		if(PidTag!=null&&!"".equals(PidTag)){
			sql.append(" and parenttagid="+PidTag);
		}
		if(idtag!=null&&!"".equals(idtag)){
			sql.append(" and tag_id="+idtag);
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
	
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public List<Object[]> checkDataRepeat(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_checkupkeyvalue where id_structure=? and tag_key=?");
		String id = map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id !=?");
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		query.setParameter(2, map.get("tagKey"));
		if(id != null && !"".equals(id)) {
			query.setParameter(3, map.get("id"));
		}
		List<Object[]> lst = query.getResultList();
		if(lst != null && !lst.isEmpty()) {
			return lst;
		} else {
			return null;
		}
	}

	@Override
	public void insertDataOfJDKeyValue(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_archive_checkupkeyvalue ( id,id_structure,tag_key,tag_value,esorder,");
		sql.append("tagid_startenddate,tagid_preservationperiod) values (?,?,?,?,?,?,?)");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, UuidGenerator.generate36UUID());
		query.setParameter(2, map.get("structureId"));
		query.setParameter(3, map.get("tagKey"));
		query.setParameter(4, map.get("tagValue"));
		query.setParameter(5, map.get("esorder"));
		query.setParameter(6, map.get("tagIdSE"));
		query.setParameter(7, map.get("tagIdSection"));
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public void updateDataOfJDKeyValue(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_archive_checkupkeyvalue set id_structure=?,tag_key=?,tag_value=?,esorder=?,");
		sql.append("tagid_startenddate=?,tagid_preservationperiod=? where id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, map.get("structureId"));
		query.setParameter(2, map.get("tagKey"));
		query.setParameter(3, map.get("tagValue"));
		query.setParameter(4, map.get("esorder"));
		query.setParameter(5, map.get("tagIdSE"));
		query.setParameter(6, map.get("tagIdSection"));
		query.setParameter(7, map.get("id"));
		query.executeUpdate();
		this.getEm().flush();
		
	}

	@Override
	public void deleteTableDataOfJD(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_archive_checkupkeyvalue where 1=1 and id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		this.getEm().flush();
	}

	//获取当前选中树节点下所有子节点id
	@Override
	public List<EssTree> deleteTreeNodeList(Map<String, String> map, Integer pageSize, Integer pageIndex) {
		
		String type =  map.get("type");
	//	String structureId =  map.get("structureId");
		String treeId =  map.get("treeId");
		if("dg".equals(type)){//正递归查询，查询下级
//			String sql="select * from T_ARCHIVE_STRUCTURE  where id in "
//					  + " (select d.id_childstructure from T_ARCHIVE_CHILDSTRUCTURE d  where  d.esstatus='启用' "
//                      + "    connect by d.id_structure = prior d.id_childstructure "
//                      + "    start with d.id_childstructure = "+treeId+")" //+" and d.id_package="+treeId+""
//                      + " order by id";
			/*String sql="select * from T_ARCHIVE_TREE d  where 1=1 "
                    + "    connect by d.ID_PARENT_NODE = prior d.id "
                    + "    start with d.id = "+treeId+"" //+" and d.id_package="+treeId+""
                    + " order by d.id";*/
			String sql="select * from T_ARCHIVE_TREE  where id in "
			  + " (select d.id from T_ARCHIVE_TREE d  where  1=1 "
            + "    connect by d.ID_PARENT_NODE = prior d.id "
            + "    start with d.id = "+treeId+")" //+" and d.id_package="+treeId+""
            + " order by id";
			return  this.getEm().createNativeQuery(sql,EssTree.class).getResultList();
		}if("-dg".equals(type)){//反递归查询，查询上级
			String sql="select * from T_ARCHIVE_STRUCTURE  where id in "
					  + " (select d.id_childstructure from T_ARCHIVE_CHILDSTRUCTURE d  where  d.esstatus='启用' "
                    + "    connect by d.id_childstructure = prior  d.id_structure"
                    + "    start with d.id_childstructure = "+treeId +")"//+" and d.id_package="+treeId+")"
                    + " order by id";
			return  this.getEm().createNativeQuery(sql,EssTree.class).getResultList();
		}else{
			String hql = " from EssStructure where 1=1 ";
			if(CommonUtil.stringNotNULL(treeId)){
				hql += " and id = "+treeId;
			}
			hql += "   order by esOrder ";
			Query query = this.getEm().createQuery(hql);
			if(pageSize!=null&&pageIndex!=null){
				query.setFirstResult(pageIndex);// 从哪条记录开始
				query.setMaxResults(pageSize);// 每页显示的最大记录数
			}
			return query.getResultList();
		}
	}

	@Override
	public List<MrzPojo> searchMRZ(Map<String, String> map, Integer pageSize,
			Integer pageIndex) {
		String hql = " from MrzPojo where 1=1 ";
		String structureId =  map.get("structureId");
		if(CommonUtil.stringNotNULL(structureId)){
			hql += " and idstructure = "+structureId;
		}
		String tagId =  map.get("tagId");
		if(CommonUtil.stringNotNULL(tagId)){
			hql += " and idtag = "+tagId;
		}
		
		Query query = this.getEm().createQuery(hql);
		if(pageSize!=null&&pageIndex!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Override
	public boolean updateMrz(String updateSql) {
		Query query = this.getEm().createNativeQuery(updateSql);
		int ss = query.executeUpdate();
		if(ss==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteMrz(String deleteSql) {
		Query query = this.getEm().createNativeQuery(deleteSql);
		query.executeUpdate();
		return true;
	}

	@Override
	public int findByIdCount(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		Object obj = query.getSingleResult();
		if(obj != null) {
			return Integer.parseInt(obj.toString());
		}else {
			return 0;
		}
	}
	
}
