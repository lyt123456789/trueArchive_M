package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.ItemRelationService;
import cn.com.trueway.workflow.core.service.ItemService;

public class ItemRelationAction extends BaseAction{
	
	private static final long serialVersionUID = -2400935242873918598L;
	
	private ItemRelationService itemRelationService;
	
	private ItemService itemService;
	
	private WfItemRelation wfItemRelation;
	
	private PrintWriter out = null;// 返回相应的值
	
	private FieldInfoService fieldInfoService;
	
	public ItemRelationService getItemRelationService() {
		return itemRelationService;
	}

	public void setItemRelationService(ItemRelationService itemRelationService) {
		this.itemRelationService = itemRelationService;
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public WfItemRelation getWfItemRelation() {
		return wfItemRelation;
	}

	public void setWfItemRelation(WfItemRelation wfItemRelation) {
		this.wfItemRelation = wfItemRelation;
	}

	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}

	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}

	public String toadd(){
		String itemid = getRequest().getParameter("itemid");
		getRequest().setAttribute("itemid", itemid);
		List<WfItem> list = itemService.getAllWfItem();
		getRequest().setAttribute("list", list);
		return "toadd";
	}
	
	public void addItemRelation(){
		itemRelationService.addItemRelation(wfItemRelation);
	}
	
	
	public String toupdate(){
		String itemid = getRequest().getParameter("itemid");
		WfItemRelation wfItemRelation  = itemRelationService.getWfItemRelationByItemId(itemid);
		getRequest().setAttribute("wfItemRelation", wfItemRelation);
		//事项列表
		List<WfItem> list = itemService.getAllWfItem();
		getRequest().setAttribute("list", list);
		//事项涉及的表
		List<WfTableInfo> tablelist = itemRelationService.getTableByItemId(wfItemRelation.getDelay_item_id());
		getRequest().setAttribute("tablelist", tablelist);
		
		List<WfFieldInfo> fieldlist = fieldInfoService.getAllFieldByTableId(wfItemRelation.getTable_id());
		getRequest().setAttribute("fieldlist", fieldlist);
		return "toupdate";
	}
	
	public void editItemRelation(){
		itemRelationService.updateItemRelation(wfItemRelation);
	}
	
	/**
	 * 根据事项ID获取流程中涉及的表单
	 * @return
	 */
	public void getTableByItemId(){
		String itemid = getRequest().getParameter("itemid");
		List<WfTableInfo> list = itemRelationService.getTableByItemId(itemid);
		JSONArray jsonArray =  JSONArray.fromObject(list);
		String returnValue = jsonArray.toString();
		try {
			out = getResponse().getWriter();
			out.write(returnValue);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getFieldByTableId(){
		String tableid = getRequest().getParameter("tableid");
		List<WfFieldInfo> list = fieldInfoService.getAllFieldByTableId(tableid);
		JSONArray jsonArray =  JSONArray.fromObject(list);
		String returnValue = jsonArray.toString();
		try {
			out = getResponse().getWriter();
			out.write(returnValue);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 描述：检查待办是否处于被暂停状态
	 * 作者:蔡亚军
	 * 创建时间:2015-1-5 上午10:25:16
	 */
	public void getIsDelaying(){
		String instanceId = getRequest().getParameter("instanceId");
		List<DelayResult> list = itemRelationService.getDelayResultByInstanceId(instanceId);
		int isDelaying = 0 ;
		for(DelayResult delayResult:list){
			if(delayResult.getStatus()!=null && 
						delayResult.getStatus()==0){
				isDelaying = 1;
				break;
			}
		}
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().setContentType("text/html;charset=utf-8"); 
			out = getResponse().getWriter();
			out.write(isDelaying+"");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 申请延期的待办的实例id,以及工作流id
	 */
	public void getBjYqInfo(){
		String instanceId = getRequest().getParameter("instanceId");
		List<DelayResult> list = itemRelationService.getDelayResultByInstanceId(instanceId);
		String delayinstanceid = "";
		String workFlowId = "";
		for(DelayResult delayResult :list){
			Integer status = delayResult.getStatus();
			if(status!=null && status == 0){	//申请未结束
				delayinstanceid = delayResult.getDelay_instanceid();		//申请延期的待办实例id
				String delayItemId = delayResult.getDelay_item_id();
				WfItem wfItem = itemService.getItemById(delayItemId);
				if(wfItem!=null){
					workFlowId = wfItem.getLcid();
				}
				break;
			}
		}
		String returnValue = delayinstanceid+";"+workFlowId;
		try{
			out = getResponse().getWriter();
			out.write(returnValue);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
/**
 * 申请延期的待办的实例id,以及工作流id
 */
	public void getBjYqOverInfo() {
		String instanceId = getRequest().getParameter("instanceId");
		List<DelayResult> list = itemRelationService
				.getDelayResultByInstanceId(instanceId);
		String delayinstanceid = "";
		String workFlowId = "";
		if(list!=null && list.size()>0){
			DelayResult delayResult = list.get(0);
			delayinstanceid = delayResult.getDelay_instanceid();		//申请延期的待办实例id
			String delayItemId = delayResult.getDelay_item_id();
			WfItem wfItem = itemService.getItemById(delayItemId);
			if(wfItem!=null){
				workFlowId = wfItem.getLcid();
			}
		}
		String returnValue = delayinstanceid + ";" + workFlowId;
		try {
			out = getResponse().getWriter();
			out.write(returnValue);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}





