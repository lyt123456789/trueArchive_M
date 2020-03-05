package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.ItemDepBinding;
import cn.com.trueway.workflow.core.pojo.WfItem;

public interface ItemService {

	public Integer getItemCountForPage(String column, String value,WfItem item);
	
	public List<WfItem> getItemListForPage(String column, String value,WfItem item, Integer pageindex, Integer pagesize);
	
	WfItem addItem(WfItem item);
	
	boolean delItem(WfItem item);
	
	WfItem getItemById(String id);
	
	List<WfItem> getItemByLcid(String lcid);
	
	List<WfItem> getItemByLcids(String allLcId);
	
	public List<WfItem> getAllWfItem();
	
	public void addItemBinding(ItemDepBinding itemDepBinding);
	
	public ItemDepBinding getItemBinding(String depId);
	
	public List<WfItem> getItemList(String depId);

	public List<WfItem> getItemListBySetDofile();

	public List<WfItem> getItemListByDeptIds(String depids,String itemType);
	
	List<WfItem> getItemLists(String id);
	
	String getItemIdsBydeptId(String deptId);
}
