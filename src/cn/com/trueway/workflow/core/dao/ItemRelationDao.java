package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;

public interface ItemRelationDao {
	
	public WfItemRelation getItemRelationByItemId(String itemId);
	
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
