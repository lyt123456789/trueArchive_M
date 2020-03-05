package cn.com.trueway.document.business.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;
import cn.com.trueway.document.business.service.FieldMatchingService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
/**
 * 公文交换字段 匹配流程
 * @author caiyj
 *
 */
public class FieldMatchingAction extends BaseAction{

	private static final long serialVersionUID = -591662927260414711L;
	
	private ItemService itemService;
	
	private FieldMatchingService fieldMatchingService;
	
	private ZwkjFormService zwkjFormService;
	
	private TableInfoService tableInfoService;
	
	private FieldInfoService fieldInfoService;
	
	public ItemService getItemService() {
		return itemService;
	}
	
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public FieldMatchingService getFieldMatchingService() {
		return fieldMatchingService;
	}

	public void setFieldMatchingService(FieldMatchingService fieldMatchingService) {
		this.fieldMatchingService = fieldMatchingService;
	}
	
	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}

	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}

	/**
	 * 获取item列表(匹配对应关系)
	 * @return
	 */
	public String getAllItemList(){
		String itemName = getRequest().getParameter("itemName");
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		WfItem item = new WfItem();
		item.setVc_ssbmid(depId);
		item.setVc_sxmc(itemName);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = itemService.getItemCountForPage(column, value, item);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfItem> list = itemService.getItemListForPage(column, value, item, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemName", itemName);
		return "fieldItemList";
	}
	
	public String toSetFieldMatching(){
		String itemId = getRequest().getParameter("itemId");
		WfItem item = itemService.getItemById(itemId);
		String wfUid = item.getLcid();	//流程id
		//获取formid
		List<ZwkjForm> list = zwkjFormService.getFormListByParamForPage("workflowId", wfUid, null, null);
		ZwkjForm zwkjForm = null;
		List<WfTableInfo> tableList = new ArrayList<WfTableInfo>();
		for(int i=0; list!=null && i<list.size(); i++){
			zwkjForm = list.get(i);
			String formid = zwkjForm.getId();
			List<FormTagMapColumn> mapedList = zwkjFormService.getFormTagMapColumnByFormId(formid);
			for(int j=0; j<mapedList.size(); j++){
				FormTagMapColumn mapcolumn = mapedList.get(i);
				String tableName = mapcolumn.getAssignTableName();
				List<WfTableInfo> tablesList = tableInfoService.getTableByTableName(tableName);
				WfTableInfo wfTable = null;
				if(tablesList!=null){
					wfTable = tablesList.get(0);
				}
				List<WfFieldInfo> fields = fieldInfoService.getAllFieldByTableId(wfTable.getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int k = 0; k < fields.size(); k++) {
						if(fields.get(k).getVc_fieldname()!=null){
							fields.get(k).setVc_fieldname(fields.get(k).getVc_fieldname().toUpperCase());
						}
					}
				}
				wfTable.setFields(fields);
				boolean flag = true;
				for(int m=0; tableList!=null && m<tableList.size(); m++){
					if(tableList.get(m).getVc_tablename().equals(tableName)){
						flag  = false;
						break;
					}
				}
				if(flag){
					tableList.add(wfTable);
				}
			}
			getRequest().setAttribute("mapedList", mapedList);
		}
		
		List<DocxgFieldMap>  maplist  = fieldMatchingService.getDocxgFieldMapList(itemId);
		List<DocxgFieldMap>  fieldlist  = new ArrayList<DocxgFieldMap>();
		Map<String,String> map = getDocxgField();
		for(String key: map.keySet()){
			String value = map.get(key);
			boolean flag = true;
			DocxgFieldMap entity = null;
			for(int i=0; maplist!=null && i<maplist.size(); i++){
				entity = maplist.get(i);
				String fieldName =entity.getTagName();
				if(fieldName.equals(key)){
					entity.setTagDesc(value);
					fieldlist.add(entity);
					flag = false;
					break;
				}
			}
			if(flag){
				entity = new DocxgFieldMap();
				entity.setItemId(itemId);
				entity.setTagName(key);
				entity.setTagDesc(value);
				fieldlist.add(entity);
			}
		}
		getRequest().setAttribute("tableList", tableList);
		getRequest().setAttribute("maplist", fieldlist);
		getRequest().setAttribute("itemId", itemId);
		return "fieldMatching";
	}
	
	public Map<String,String>  getDocxgField(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("docguid", "唯一标识");
		map.put("sender", "发送者编号");
		map.put("xto", "主送单位");
		map.put("xcc", "抄送单位");
		map.put("title", "公文标题");
		map.put("keyword", "公文主题词");
		map.put("doctype", "公文类型");
		map.put("origindocguid", "唯一标识");
		map.put("submittm", "发送时间");
		map.put("priority", "紧急程度");
		map.put("sendstat", "发送状态");
		map.put("jgdz", "文号(标识)");
		map.put("fwnh", "文号(年号)");
		map.put("fwjg", "发文单位");
		map.put("yfdw", "印发单位");
		map.put("qfr", "签发人");
		map.put("yfrq", "印发日期");
		map.put("yffs", "印发分数");
		map.put("fwxh", "发文号");
		//map.put("cebid", "版式文件id");
		//map.put("bodyxml", "公文xml");
		map.put("sourceFrom", "");
		map.put("xtoName", "主送单位名");
		map.put("xccName", "抄送单位名");
		map.put("wh", "文号");
		return  map;
	}
	
	/**
	 * 保存公文交换字段与业务表之间的匹配关系
	 * @return
	 */
	public String addDocxgColoumRelationship(){
		
		String itemId = getRequest().getParameter("itemId");
		
		String[] formtagnames = getRequest().getParameterValues("formtagnames");
		
		String[] tagTables=getRequest().getParameterValues("tagTables");
		
		String[] tagFields=getRequest().getParameterValues("tagFields");

		//删除原先的itemId对应的字段对应关系
		
		fieldMatchingService.deleteDocxgFieldMapByItemId(itemId);
		
		DocxgFieldMap map = null;
		if (formtagnames!=null&&formtagnames.length>0) {
			for (int i = 0; i < formtagnames.length; i++) {
				map = new DocxgFieldMap();
				map.setItemId(itemId);
				map.setTagName(formtagnames[i]);
				map.setTableName(tagTables[i]);
				map.setFieldName(tagFields[i]);
				fieldMatchingService.addDocxgFieldMap(map);
			}
		}
		return getAllItemList();
	}
	
	
	/**
	 * 删除匹配对应关系
	 */
	public void deleteDepShipById(){
		String id = getRequest().getParameter("id");
		fieldMatchingService.deleteDepShipById(id);
		try {
			getResponse().getWriter().print("success");
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
