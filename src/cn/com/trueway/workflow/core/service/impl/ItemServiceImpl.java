package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.ItemRelationDao;
import cn.com.trueway.workflow.core.pojo.ItemDepBinding;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.service.ItemService;

public class ItemServiceImpl implements ItemService {

	private ItemDao itemDao;
	
	private ItemRelationDao itemRelationDao;
	
	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	public ItemRelationDao getItemRelationDao() {
		return itemRelationDao;
	}

	public void setItemRelationDao(ItemRelationDao itemRelationDao) {
		this.itemRelationDao = itemRelationDao;
	}

	public Integer getItemCountForPage(String column, String value,
			WfItem item){
		return itemDao.getItemCountForPage(column, value, item);
	}
	
	public List<WfItem> getItemListForPage(String column, String value,
			WfItem item, Integer pageindex, Integer pagesize){
		return itemDao.getItemListForPage(column, value, item, pageindex, pagesize);
	}

	public WfItem addItem(WfItem item) {
		return itemDao.addItem(item);
	}
	
	public boolean delItem(WfItem item){
		itemDao.delItem(item);
		return true;
	}
	
	public WfItem getItemById(String id){
		return itemDao.getItemById(id);
	}

	public List<WfItem> getItemByLcid(String lcid) {
		return itemDao.getItemByLcid(lcid);
	}
	
	public List<WfItem> getItemByLcids(String allLcId) {
		return itemDao.getItemByLcids(allLcId);
	}

	@Override
	public List<WfItem> getAllWfItem() {
		return itemDao.getAllWfItem();
	}

	@Override
	public void addItemBinding(ItemDepBinding itemDepBinding) {
		itemDao.addItemBinding(itemDepBinding);
	}

	@Override
	public ItemDepBinding getItemBinding(String depId) {
		return itemDao.getItemBinding(depId);
	}

	@Override
	public List<WfItem> getItemList(String depId) {
		return itemDao.getItemList(depId);
	}
	@Override
	public List<WfItem> getItemListBySetDofile() {
		return itemDao.getItemListBySetDofile();
	}
	
	@Override
	public List<WfItem> getItemListByDeptIds(String depids,String itemType) {
		return itemDao.getItemListByDeptIds(depids,itemType);
	}

	@Override
	public List<WfItem> getItemLists(String id) {
		return itemDao.getItemLists( id);
	}
	
	@Override
	public String getItemIdsBydeptId(String deptId){
		return itemDao.selectItemIdsBydeptId(deptId);
	}
}
