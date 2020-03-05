package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.ItemRelationDao;
import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;

public class ItemRelationDaoImpl extends BaseDao implements ItemRelationDao{

	@SuppressWarnings("unchecked")
	@Override
	public WfItemRelation getItemRelationByItemId(String itemId) {
		String sql = "select t.* from t_wf_core_itemrelation t  where  t.item_id = '"+itemId+"'";
		List<WfItemRelation> list = getEm().createNativeQuery(sql,WfItemRelation.class).getResultList();
		if(list!=null && list.size()>0){
			WfItemRelation wfItemRelation = list.get(0);
			if(wfItemRelation!=null){
				String sql2 = "select vc_sxmc  from  t_wf_core_item where id= '"+wfItemRelation.getDelay_item_id()+"'";
				wfItemRelation.setDelay_item_name(getEm().createNativeQuery(sql2).getSingleResult().toString());
			}
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfTableInfo> getTableByItemId(String itemid) {
		//获取事项绑定的流程
		String sql = "select t.* from t_wf_core_item t where t.id='"+itemid+"'";
		WfItem wfItem = (WfItem) getEm().createNativeQuery(sql,WfItem.class).getSingleResult();
		String lcid = "";
		if(wfItem!=null){
			lcid = wfItem.getLcid();
		}
		String hql = "from WfTableInfo t where lcid = '" + lcid + "' or reflc like '%,"+lcid+",%'";
		return  getEm().createQuery(hql).getResultList();
	}

	@Override
	public void addItemRelation(WfItemRelation wfItemRelation) {
		getEm().persist(wfItemRelation);
	}

	@Override
	public WfItemRelation getWfItemRelationByItemId(String item_id) {
		String hql = " from WfItemRelation w where w.item_id = '"+item_id+"'";
		return (WfItemRelation)getEm().createQuery(hql).getSingleResult();
	}

	@Override
	public void updateItemRelation(WfItemRelation wfItemRelation) {
		getEm().merge(wfItemRelation);
	}

	@Override
	public void addDelayResult(DelayResult delayResult) {
		getEm().persist(delayResult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DelayResult getDelayResult(String delayInstance) {
		String hql = " from DelayResult t where t.delay_instanceid = '"+delayInstance+"'";
		List<DelayResult> list = getEm().createQuery(hql).getResultList();
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDelayResultStatus(Map<String, String> map) {
		String tableid = map.get("tableid");		//表
		String coloum = map.get("coloum");			//字段名
		String instanceId = map.get("instanceId");	//实例id
		String reserve_value  = map.get("reserve_value");
		if(!CommonUtil.stringNotNULL(tableid) && !CommonUtil.stringNotNULL(coloum)
				&& !CommonUtil.stringNotNULL(instanceId)){
			return 0;
		}
		reserve_value = reserve_value==null?"":reserve_value;
		
		String tablesql = "select t.vc_tablename from t_wf_core_table t where t.id = '"+tableid+"'";
		String tablename = getEm().createNativeQuery(tablesql).getSingleResult().toString();
		
		String sql = "select "+coloum+ " from "+ tablename +
				" where instanceid ='"+instanceId+"'";
		int status = 0 ;
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		for(int i=0 ; list!=null && i<list.size(); i++){
			Object obj = list.get(i);
			String value = obj==null?"":obj.toString();
			if(value.equals(reserve_value)){
				status = 1;
				break;
			}
		}
		return status;
	}

	@Override
	public void updateDelayResult(DelayResult delayResult) {
		getEm().merge(delayResult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelayResult> getDelayResultByInstanceId(String instanceId) {
		String hql = "from DelayResult t where t.instanceid = '"+instanceId+"' order by t.begin_date desc ";
		return getEm().createQuery(hql).getResultList();
	}
}
