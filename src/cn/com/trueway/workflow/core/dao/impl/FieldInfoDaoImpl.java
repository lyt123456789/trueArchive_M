package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.FieldInfoDao;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;

public class FieldInfoDaoImpl extends BaseDao  implements FieldInfoDao {

	public Integer getFieldCountForPage(String column, String value,
			WfFieldInfo wfField) {
		String hql = "select count(*) from WfFieldInfo t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(wfField.getI_tableid())){
			hql += " and (i_tableid is null or i_tableid = '" + wfField.getI_tableid()+ "')";
		}else{
			hql += " and i_tableid is null";
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> getFieldListForPage(String column, String value,
			WfFieldInfo wfField, Integer pageindex, Integer pagesize) {
		String hql = "from WfFieldInfo t where i_tableid is null ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if(CommonUtil.stringNotNULL(wfField.getI_tableid())){
			hql += " and (i_tableid is null or i_tableid = '" + wfField.getI_tableid()+ "')";
		}else{
			hql += " and i_tableid is null";
		}
		hql += " order by i_orderid";
		Query query = getEm().createQuery(hql);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> getAllFieldByTableId(String tableid) {
		String querySql = "from WfFieldInfo e where i_tableid is null";
		if(tableid != null && !"".equals(tableid)){
			querySql += " or i_tableid='" + tableid + "'";
		}
		return getEm().createQuery(querySql + " order by i_orderid").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> getAllFieldByXbTableId(String tableid) {
		String querySql = "from WfFieldInfo e where 1=1";
		if(tableid != null && !"".equals(tableid)){
			querySql += " and i_tableid='" + tableid + "'";
		}
		return getEm().createQuery(querySql + " order by i_orderid").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> getPublicField() {
		String querySql = "from WfFieldInfo e where i_tableid is null order by i_orderid";
		return getEm().createQuery(querySql).getResultList();
	}
	
	/**
	 * 增
	 */
	public void addField(WfFieldInfo wfField){
		if(wfField.getId() != null && !"".equals(wfField.getId())){
			getEm().merge(wfField);
		}else{
			if("".equals(wfField.getId())){
				wfField.setId(null);
			}
			getEm().persist(wfField);
		}
	}
	
	/**
	 * 删
	 */
	public void delField(WfFieldInfo wfField){
		getEm().remove(getEm().merge(wfField));
	}
	
	/**
	 * 改
	 */
	public void updateField(WfFieldInfo wfField){
		if(wfField != null && wfField.getId() != null){
			getEm().merge(wfField);
		}
	}
	
	public WfFieldInfo getFieldById(String id){
		String querySql = "from WfFieldInfo e where id ='" + id + "'";
		return (WfFieldInfo)getEm().createQuery(querySql).getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> getFieldByFieldName(String vc_fieldname) {
		String querySql = "from WfFieldInfo where 1=1";
		if(vc_fieldname != null && !"".equals(vc_fieldname)){
			querySql += " and vc_fieldname = '" + vc_fieldname + "'";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}

	public List<WfFieldInfo> getFieldByParam(String table, String column) {
		String querySql = "from WfFieldInfo w where w.vc_fieldname='"+column+"' and w.i_tableid in (select t.id from  WfTableInfo t where t.vc_tablename='"+table+"')";
		return this.getEm().createQuery(querySql).getResultList();
	}
	
}
