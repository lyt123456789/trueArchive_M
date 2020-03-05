package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;

public interface ItemRelationService {
	
	public void completeItemList(List<WfItem> list);
	
	public List<WfTableInfo> getTableByItemId(String itemid);
	
	public void addItemRelation(WfItemRelation wfItemRelation);
	
	public WfItemRelation getWfItemRelationByItemId(String item_id);
	
	public void updateItemRelation(WfItemRelation wfItemRelation);
	
	public void addDelayResult(DelayResult delayResult);
	
	public DelayResult getDelayResult(String delayInstance);
	
	public int getDelayResultStatus(Map<String,String> map);
	
	public void updateDelayResult(DelayResult delayResult);
	
	public List<DelayResult> getDelayResultByInstanceId(String instanceId);

	

}
