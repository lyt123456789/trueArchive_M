package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.DictionaryDao;
import cn.com.trueway.workflow.core.pojo.WfDictionary;

public class DictionaryDaoImpl extends BaseDao implements DictionaryDao {

	public void addDictionary(WfDictionary dictionary) {
		if(dictionary.getId() != null && !"".equals(dictionary.getId())){
			getEm().merge(dictionary);
		}else{
			if("".equals(dictionary.getId())){
				dictionary.setId(null);
			}
			getEm().persist(dictionary);
		}
	}

	public void delDictionary(WfDictionary dictionary) {
		getEm().remove(getEm().merge(dictionary));
	}

	public Integer getDictionaryCountForPage(String column, String value,
			WfDictionary dictionary) {
		String hql = "select count(*) from WfDictionary t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(dictionary.getLcid())){
			hql += " and (lcid is null or lcid = '" + dictionary.getLcid()+ "')";
		}else{
			hql += " and lcid is null";
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<WfDictionary> getDictionaryListForPage(String column, String value,
			WfDictionary dictionary, Integer pageindex, Integer pagesize) {
		String hql = "from WfDictionary t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(dictionary.getLcid())){
			hql += " and (lcid is null or lcid = '" + dictionary.getLcid()+ "')";
		}else{
			hql += " and lcid is null";
		}
		Query query = getEm().createQuery(hql);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	public WfDictionary getDictionaryById(String id) {
		String sql = " from WfDictionary where id ='"+id+"'";
		return (WfDictionary)getEm().createQuery(sql).getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<WfDictionary> getPublicDictionary() {
		String sql = " from WfDictionary where lcid is null";
		return getEm().createQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfDictionary> getDictionaryByLcid(String lcid) {
		String sql = " from WfDictionary where ";
		if(lcid != null && !"".equals(lcid)){
			sql += " lcid='"+lcid+"'";
		}else{
			sql += " lcid is null";
		}
		return getEm().createQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfDictionary> getDictionaryByName(String vc_name){
		String sql = " from WfDictionary where 1=1";
		if(vc_name != null && !"".equals(vc_name)){
			sql += " and vc_name='"+vc_name+"'";
		}
		return getEm().createQuery(sql).getResultList();
	}
	
	@Override
	public String getDictionaryNameByField(String itemId, String tableName,
			String fieldName) {

		String sql = "select distinct t.selectdic from t_wf_core_form_map_column t where t.tablename = ? and t.columnname = ? and t.formid in (select t.id from t_wf_core_form t ,t_wf_core_item i where i.id = ? and i.lcid = t.workflowid )";
		String dicName = null;
		try{
			Query query = getEm().createNativeQuery(sql);
			query.setParameter(3, itemId);
			query.setParameter(1, tableName);
			query.setParameter(2, fieldName);
			dicName = query.getSingleResult().toString();
		}catch(Exception e){
		}
		return dicName;
	
	}

}
