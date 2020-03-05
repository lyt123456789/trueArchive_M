package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.ItemDepBinding;
import cn.com.trueway.workflow.core.pojo.WfItem;

public interface ItemDao {

	Integer getItemCountForPage(String column, String value, WfItem item);
	
	public List<WfItem> getItemListForPage(String column, String value,
			WfItem item, Integer pageindex, Integer pagesize);
	
	WfItem addItem(WfItem item);
	
	public void delItem(WfItem item);
	
	public WfItem getItemById(String id);
	
	public List<WfItem> getItemByLcid(String lcid);
	
	public List<WfItem> getItemByLcids(String allLcId);
	
	public List<WfItem> getAllWfItem();
	
	public void addItemBinding(ItemDepBinding itemDepBinding);
	
	public ItemDepBinding getItemBinding(String depId);
	
	public List<WfItem> getItemList(String depId);

	List<WfItem> getItemListBySetDofile();

	List<WfItem> getItemListByDeptIds(String depids,String itemType);

	List<WfItem> getItemLists(String ids);

	String selectItemIdsBydeptId(String deptId);
}
