package cn.com.trueway.workflow.core.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.dao.ItemRelationDao;
import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.ItemRelationService;

public class ItemRelationServiceImpl implements ItemRelationService{
	
	private ItemRelationDao itemRelationDao ;

	public ItemRelationDao getItemRelationDao() {
		return itemRelationDao;
	}

	public void setItemRelationDao(ItemRelationDao itemRelationDao) {
		this.itemRelationDao = itemRelationDao;
	}

	@Override
	public void completeItemList(List<WfItem> list) {
		WfItemRelation wfItemRelation = null ;
		for(WfItem wfItem : list){
			String itemId = wfItem.getId();
			wfItemRelation = itemRelationDao.getItemRelationByItemId(itemId);
			if(wfItemRelation!=null){
				wfItem.setDelayItemId(wfItemRelation.getDelay_item_id());
				wfItem.setDelayItemName(wfItemRelation.getDelay_item_name());
			}
		}
		
	}

	@Override
	public List<WfTableInfo> getTableByItemId(String itemid) {
		return itemRelationDao.getTableByItemId(itemid);
	}

	@Override
	public void addItemRelation(WfItemRelation wfItemRelation) {
		itemRelationDao.addItemRelation(wfItemRelation);
		
	}

	@Override
	public WfItemRelation getWfItemRelationByItemId(String item_id) {
		return itemRelationDao.getWfItemRelationByItemId(item_id);
	}

	@Override
	public void updateItemRelation(WfItemRelation wfItemRelation) {
		itemRelationDao.updateItemRelation(wfItemRelation);
		
	}

	@Override
	public void addDelayResult(DelayResult delayResult) {
		itemRelationDao.addDelayResult(delayResult);
	}

	@Override
	public DelayResult getDelayResult(String delayInstance) {
		return itemRelationDao.getDelayResult(delayInstance);
	}

	@Override
	public int getDelayResultStatus(Map<String,String> map) {
		return itemRelationDao.getDelayResultStatus(map);
	}

	@Override
	public void updateDelayResult(DelayResult delayResult) {
		itemRelationDao.updateDelayResult(delayResult);
	}

	@Override
	public List<DelayResult> getDelayResultByInstanceId(String instanceId) {
		return itemRelationDao.getDelayResultByInstanceId(instanceId);
	}
}
