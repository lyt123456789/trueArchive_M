package cn.com.trueway.workflow.core.action;

import cn.com.trueway.base.actionSupport.BaseAction;

/**
 * 事项管理ACTION
 * @author zhuy
 * @date 2013-04-09
 *
 */
public class ItemXqAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	private ItemService itemService;
	
	private WfItemX item;
	
	private File vc_file;

	public File getVc_file() {
		return vc_file;
	}

	public void setVc_file(File vcFile) {
		vc_file = vcFile;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public WfItem getItem() {
		return item;
	}

	public void setItem(WfItem item) {
		this.item = item;
	}

	public String getItemList(){
		
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		
		String lcid = (String)getRequest().getSession().getAttribute("workflow_difined_id");
		
		if(item == null){
			item = new WfItem();
		}
		item.setVc_lcid(lcid);
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = itemService.getItemCountForPage(column, value, item);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfItem> list = itemService.getItemListForPage(column, value, item, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "itemList";
	}
	
	public String toAddJsp(){
		return "toAddJsp";
	}
	
	public String add(){
		item.setC_createdate(new Timestamp(System.currentTimeMillis()));
		itemService.addItem(item);
		return getItemList();
	}
	
	public void del(){
		String id = getRequest().getParameter("id");
		if( CommonUtil.stringNotNULL(id)){
			itemService.delItem(itemService.getItemById(id));
		}
	}
	 */
}
