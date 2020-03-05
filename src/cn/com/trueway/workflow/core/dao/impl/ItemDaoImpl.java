package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.pojo.ItemDepBinding;
import cn.com.trueway.workflow.core.pojo.WfItem;

@SuppressWarnings("unchecked")
public class ItemDaoImpl extends BaseDao implements ItemDao {

	public Integer getItemCountForPage(String column, String value,
			WfItem item) {
		String hql = "select count(*) from WfItem t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if ( CommonUtil.stringNotNULL(item.getLcid())){
			hql += " and t.lcid='" + item.getLcid() + "'";
		}
		if(CommonUtil.stringNotNULL(item.getVc_sxmc())){
			hql += " and t.vc_sxmc like '%"+item.getVc_sxmc()+"%'";
		}
		if ( CommonUtil.stringNotNULL(item.getVc_ssbmid())){
			hql += " and t.vc_ssbmid in (" + item.getVc_ssbmid() + ")";
		}
		if ( CommonUtil.stringNotNULL(item.getId())){
			hql += " and t.id in (" + item.getId() + ")";
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	public List<WfItem> getItemListForPage(String column, String value,
			WfItem item, Integer pageindex, Integer pagesize) {
		String hql = "from WfItem t where 1=1 ";
		if (CommonUtil.stringNotNULL( column) && CommonUtil.stringNotNULL( value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if ( CommonUtil.stringNotNULL(item.getLcid())){
			hql += " and t.lcid='" + item.getLcid() + "'";
		}
		
		if(CommonUtil.stringNotNULL(item.getVc_sxmc())){
			hql += " and t.vc_sxmc like '%"+item.getVc_sxmc()+"%'";
		}
		
		if ( CommonUtil.stringNotNULL(item.getVc_ssbmid())){
			//hql += " and t.vc_ssbmid='" + item.getVc_ssbmid() + "'";
			hql += " and t.vc_ssbmid in (" + item.getVc_ssbmid() + ")";
		}
		
		if ( CommonUtil.stringNotNULL(item.getId())){
			hql += " and t.id in (" + item.getId() + ")";
		}
		hql += " order by t.index desc";
		Query query = getEm().createQuery(hql);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}

	public WfItem addItem(WfItem item){
		if(item.getId() == null || "".equals(item.getId())){
			item.setId(null);
			this.getEm().persist(item);
		}else{
			this.getEm().merge(item);
		}
		return item;
	}
	
	/**
	 * 删
	 */
	public void delItem(WfItem item){
		getEm().remove(getEm().merge(item));
	}
	
	public WfItem getItemById(String id){
		String querySql = "from WfItem e where id ='" + id + "'";
		List<Object> list = getEm().createQuery(querySql).getResultList();
		if(list!=null&&list.size()!=0){
			return (WfItem) list.get(0);
		}
		return null;
	}

	public List<WfItem> getItemByLcid(String lcid) {
		String querySql = "from WfItem e where lcid ='" + lcid + "'";
		return getEm().createQuery(querySql).getResultList();
	}
	
	public List<WfItem> getItemByLcids(String allLcId) {
		String querySql = "from WfItem e where lcid in (" + allLcId + ")";
		return getEm().createQuery(querySql).getResultList();
	}

	@Override
	public List<WfItem> getAllWfItem() {
		String hql = " from WfItem t order by t.vc_sxmc ";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void addItemBinding(ItemDepBinding itemDepBinding) {
		//修改状态位
		String updateSql = "update T_WF_CORE_ITEMDEPBINDING  t set t.status = 0 " +
				"where t.depId ='"+itemDepBinding.getDepId()+"'";
		getEm().createNativeQuery(updateSql).executeUpdate();
		//新增数据
		this.getEm().persist(itemDepBinding);
	}

	@Override
	public ItemDepBinding getItemBinding(String depId) {
		String sql = " from  ItemDepBinding t where t.depId='"+depId+"' and t.status = 1";
		List<ItemDepBinding> list = getEm().createQuery(sql).getResultList();
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<WfItem> getItemList(String depId) {
		String sql = " from  ItemDepBinding t where t.depId='"+depId+"' and t.status = 1";
		List<ItemDepBinding> list = getEm().createQuery(sql).getResultList();
		String ids = "";
		if(list!=null && list.size()>0){
			String itemids = list.get(0).getItemids();
			if(itemids!=null && !itemids.equals("")){
				String[] itemid = itemids.split(",");
				for(int i=0; itemid!=null && i<itemid.length; i++){
					ids +="'"+itemid[i]+"',";
				}
			}
		}
		if(ids!=null && ids.length()>0){
			ids = ids.substring(0, ids.length()-1);
		}
		if(ids==null || ids.equals("")){
			return null;
		}
		String hql =" from WfItem t where t.id in ("+ids+")";
		return getEm().createQuery(hql).getResultList();
	}
	

	@Override
	public List<WfItem> getItemListBySetDofile() {
		String hql = "from WfItem t where 1=1 ";
		hql += " and t.id in (select s.item_id from ItemSelect s where s.type = 'dofile')";
		hql += " order by t.c_createdate desc";
		Query query = getEm().createQuery(hql);
		return query.getResultList();
	}
	
	@Override
	public List<WfItem> getItemListByDeptIds(String depids,String itemType) {
		String hql = "from WfItem t where 1=1  ";
		if(StringUtils.isNotBlank(depids)){
			hql += " and t.vc_ssbmid in (" + depids + ") ";
		}
		if(StringUtils.isNotBlank(itemType)){
			hql += " and t.vc_sxlx='" + itemType + "' ";
		}
		hql += " order by t.index desc";
		Query query = getEm().createQuery(hql);
		return query.getResultList();
	
	}
	
	@Override
	public List<WfItem> getItemLists(String id) {
		String hql =" from WfItem t where t.id in ("+id+")";
		return getEm().createQuery(hql).getResultList();
	}
	
	@Override
	public String selectItemIdsBydeptId(String deptId){
		if(StringUtils.isNotBlank(deptId)){
			String sql = "select wm_concat(id) from t_wf_core_item where vc_ssbmid=:vc_ssbmid";
			Query query = getEm().createNativeQuery(sql);
			query.setParameter("vc_ssbmid", deptId);
			String itemIds = query.getSingleResult() != null ? query.getSingleResult().toString() : "";
			return itemIds;
		}
		return "";
	}
}
