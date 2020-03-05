package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.TemplateDao;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.WfTemplate;

public class TemplateDaoImpl extends BaseDao implements TemplateDao {

	public Integer getTemplateCountForPage(String column, String value,
			WfTemplate template) {
		String hql = "select count(*) from WfTemplate t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(template.getLcid())){
			hql += " and (lcid = '" + template.getLcid() + "' or reflcId like '%,"+template.getLcid()+",%')";
		}
		if(CommonUtil.stringNotNULL(template.getIsRedTape())){
			hql += " and t.isRedTape='" + template.getIsRedTape() + "'";
		}else{
			hql += " and (t.isRedTape != '1' or t.isRedTape is null) ";
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<WfTemplate> getTemplateListForPage(String column, String value,
			WfTemplate template, Integer pageindex, Integer pagesize) {
		String hql = "from WfTemplate t where 1=1 ";
		if (CommonUtil.stringNotNULL( column) && CommonUtil.stringNotNULL( value)) {
			hql += " and t." + column + " like :value";
		}
		if(CommonUtil.stringNotNULL(template.getLcid())){
			hql += " and (lcid = :Lcid1 or reflcId like :Lcid2)";
		}
		
		if(CommonUtil.stringNotNULL(template.getIsRedTape())){
			hql += " and t.isRedTape = :IsRedTape";
		}else{
			hql += " and (t.isRedTape != '1' or t.isRedTape is null) ";
		}
 		Query query = getEm().createQuery(hql);
		if (CommonUtil.stringNotNULL( column) && CommonUtil.stringNotNULL( value)) {
			query.setParameter("value", "%" + value + "%");
		}
		if(CommonUtil.stringNotNULL(template.getLcid())){
			query.setParameter("Lcid1", template.getLcid());
			query.setParameter("Lcid2", "%,"+template.getLcid()+",%");
		}
		if(CommonUtil.stringNotNULL(template.getIsRedTape())){
			query.setParameter("IsRedTape", template.getIsRedTape());
		}
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfTemplate> getAllTemplateListNotLc(String lcid,String isRedTape){
		String querySql = "from WfTemplate where 1=1 ";
		if(CommonUtil.stringNotNULL(lcid)){
			querySql += " and (lcid != '" + lcid + "' or lcid is null) and (reflcId not like '%," + lcid + ",%' or reflcId is null)";
		}
		if(CommonUtil.stringNotNULL(isRedTape)){
			querySql += " and isRedTape='" + isRedTape + "'";
		}else{
			querySql += " and (isRedTape != '1' or isRedTape is null) ";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}

	public WfTemplate addTemplate(WfTemplate wfTemplate){
		if(wfTemplate.getId() == null || "".equals(wfTemplate.getId())){
			wfTemplate.setId(null);
			this.getEm().persist(wfTemplate);
		}else{
			this.getEm().merge(wfTemplate);
		}
		return wfTemplate;
	}
	
	/**
	 * 删
	 */
	public void delTemplate(WfTemplate wfTemplate){
		getEm().remove(getEm().merge(wfTemplate));
	}
	
	public WfTemplate getTemplateById(String id){
		String querySql = "from WfTemplate e where id ='" + id + "'";
		List list = getEm().createQuery(querySql).getResultList();
		if(list.size()>0){
			return (WfTemplate) list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<WfTemplate> getTemplateByLcid(String lcid) {
		String querySql = "from WfTemplate e where lcid ='" + lcid + "'";
		return getEm().createQuery(querySql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object> getSqlQuery(String sql) {
		return this.getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List getHqlQuery(String Hql) {
		return this.getEm().createQuery(Hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WfTemplate> findWfTemplateList(String ids, String tempType) {
		String sql = " select  t.* from t_wf_core_template t where t.id in ("+ ids+") and t.gwzl = '" + tempType + "'";
		return getEm().createNativeQuery(sql, WfTemplate.class).getResultList();
	}
	
	public List<WfTemplate> getTemplateBySql(String conditionSql) {
		String hql = "from WfTemplate e where 1=1 " + conditionSql;
		return getEm().createQuery(hql).getResultList();
	}
	
	@Override
	public void updateDoFile(DoFile doFile) {
		getEm().merge(doFile);
	}
}
