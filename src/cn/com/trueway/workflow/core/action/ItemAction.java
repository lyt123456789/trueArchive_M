package cn.com.trueway.workflow.core.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.ItemDepBinding;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfMainJson;
import cn.com.trueway.workflow.core.service.ItemRelationService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.service.DepartmentService;
/**
 * 
 * 描述：事项管理操作类
 * 作者：蔡亚军
 * 创建时间：2016-2-29 下午5:28:53
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class ItemAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private ItemService itemService;
	
	private DepartmentService departmentService;
 	
	private WfItem item;
	
	private File vc_file;
	
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private ItemRelationService itemRelationService;

	public ItemRelationService getItemRelationService() {
		return itemRelationService;
	}

	public void setItemRelationService(ItemRelationService itemRelationService) {
		this.itemRelationService = itemRelationService;
	}

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

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**
	 * 
	 * 描述：获取事项列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-2-29 下午5:29:34
	 */
	public String getItemList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		String status = getRequest().getParameter("status");
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		
		String ids =getRequest().getParameter("ids");
		StringBuffer idsarray = new StringBuffer();
		if(CommonUtil.stringNotNULL(ids)){
			String[] idArray = ids.split(",") ;
			for(int j = 0 ;j<idArray.length;j++){
				idsarray.append("'"+idArray[j]+"'");
				if(j!=(idArray.length-1)){
					idsarray.append(",");
				}
			}
		}
		String itemName = getRequest().getParameter("itemName");
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		
		item = new WfItem();
		item.setId(idsarray.toString());
		item.setVc_sxmc(itemName);
		//用户关联显示本部门事项查询
		item.setVc_ssbmid(depId);
		//分页相关，代码执行顺序不变
		List<WfItem> list = null;
		if(StringUtils.isNotBlank(status) && status.equals("1")){
			list = itemService.getItemListForPage(column, value, item, null, null);
		}else{
			int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
			int count = itemService.getItemCountForPage(column, value, item);
			Paging.setPagingParams(getRequest(), pageSize, count);
			list = itemService.getItemListForPage(column, value, item, Paging.pageIndex, Paging.pageSize);
		}
		itemRelationService.completeItemList(list);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemName", itemName);
		String no = getRequest().getParameter("no");
		getRequest().setAttribute("no", no);
		getRequest().setAttribute("ids", ids);
		getRequest().setAttribute("status", status);
		if(StringUtils.isNotBlank(status) && status.equals("1")){
			String trueOAUrl = SystemParamConfigUtil.getParamValueByParam("smsUrl");
			HttpClient client=new HttpClient();
			HttpMethod method = getPostMethod(trueOAUrl + "/trueOA/portalInterface_getModuleMenuJson.do");
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			NameValuePair[] param={new NameValuePair("isadd","1"),new NameValuePair("userId",emp.getEmployeeGuid()),};
			((PostMethod)method).setRequestBody(param);
			try {
				client.executeMethod(method);
				String result=method.getResponseBodyAsString();
				JSONObject obj = null;
				if (StringUtils.isNotBlank(result)) {
					obj = new JSONObject(result);
					JSONObject data = obj.getJSONObject("data");
					JSONArray arr = data.getJSONArray("content");
					List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
					for (int i=0;i<arr.length();i++) {
						JSONObject obj2 = arr.getJSONObject(i);
						Map<String,String> map = new HashMap<String,String>();
						map.put("id", obj2.getString("id"));
						map.put("title", obj2.getString("title"));
						map.put("link", obj2.getString("link"));
						list2.add(map);
					}
					getRequest().setAttribute("itemList", list2);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				method.releaseConnection();
			}
			return "startList";
		}
		return "itemList";
	}
	
	/**
	 * 数据字典里的事项列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-28 下午3:16:28
	 */
	public String getDicItemList(){
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		String allLcId = getRequest().getParameter("allLcId");
		String dicValue=getRequest().getParameter("dicValue");
		String modId=getRequest().getParameter("modId");
		String matchId=getRequest().getParameter("matchId");
		String dicId=getRequest().getParameter("dicId");
		String allLcIdStr = "";
		if(CommonUtil.stringNotNULL(allLcId)&&allLcId.indexOf(",")>0){
			String[] allLcIds = allLcId.split(",");
			for(String s:allLcIds){
				allLcIdStr += "'" + s +"',";
			}
			allLcIdStr = allLcIdStr.substring(0,allLcIdStr.length()-1);
		}else{
			allLcIdStr ="'"+allLcId+"'";
		}
		List<WfItem> lists = itemService.getItemByLcids(allLcIdStr);
		String ids = "";
		if(null!=lists&&lists.size()>0){
			for(WfItem w:lists){
				ids += w.getId()==null?"":w.getId() + ",";
			}
			if(CommonUtil.stringNotNULL(ids)&&ids.length()>0){
				ids=ids.substring(0,ids.length()-1);
			}
		}
		
		StringBuffer idsarray = new StringBuffer();
		if(CommonUtil.stringNotNULL(ids)){
			String[] idArray = ids.split(",") ;
			for(int j = 0 ;j<idArray.length;j++){
				idsarray.append("'"+idArray[j]+"'");
				if(j!=(idArray.length-1)){
					idsarray.append(",");
				}
			}
		}
		//System.out.println("idsarray------------------------"+idsarray.toString());
		String itemName = getRequest().getParameter("itemName");
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		
		item = new WfItem();
		item.setId(idsarray.toString());
		item.setVc_sxmc(itemName);
		
		/*if (department!=null&&CommonUtil.stringNotNULL(department.getSuperiorGuid())&&department.getSuperiorGuid().equals("1")) {
			//超管查看所有事项
			bigDepId=null;
		}*/
		//用户关联显示本部门事项查询
		item.setVc_ssbmid(depId);
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = itemService.getItemCountForPage(column, value, item);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfItem> list = itemService.getItemListForPage(column, value, item, Paging.pageIndex, Paging.pageSize);
		itemRelationService.completeItemList(list);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemName", itemName);
		String no = getRequest().getParameter("no");
		getRequest().setAttribute("no", no);
		getRequest().setAttribute("ids", ids);
		getRequest().setAttribute("dicValue", dicValue);
		getRequest().setAttribute("modId", modId);
		getRequest().setAttribute("matchId", matchId);
		getRequest().setAttribute("dicId", dicId);
		return "dicItemList";
	}
	
	/**
	 * 
	 * 描述：新增事项页面
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-2-29 下午5:29:51
	 */
	public String toAddJsp(){
		List<Object[]> list = workflowBasicFlowService.getBasicFlowList("all","", 0, 1000);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			list_return.add(wfMainJson);
		}
		//获取当前所有事项
		item = new WfItem();
		List<WfItem> itemList = itemService.getItemListForPage("", "", item, null, null);
		getRequest().setAttribute("itemList", itemList);
		getRequest().setAttribute("list", list_return);
		return "toAddJsp";
	}
	
	public String add(){
		//工作流从页面上进行选择
		//String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		//item.setLcid(lcid);
		item.setC_createdate(new Timestamp(System.currentTimeMillis()));
		item.setIsFlexibleForm("1");
		itemService.addItem(item);
		return getItemList();
	}
	
	public void del(){
		String id = getRequest().getParameter("ids");
		String[] ids = id.split(",");
		for(int i=0; ids!=null && i<ids.length ;i++){
			String strId = ids[i] ;
			itemService.delItem(itemService.getItemById(strId));
		}
		try {
			getResponse().getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		item = itemService.getItemById(id);
		getRequest().setAttribute("item", item);
		List<Object[]> list = workflowBasicFlowService.getBasicFlowList("all","",0, 1000);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			list_return.add(wfMainJson);
		}
		//获取当前所有事项
		item = new WfItem();
		List<WfItem> itemList = itemService.getItemListForPage("", "", item, null, null);
		getRequest().setAttribute("itemList", itemList);
		getRequest().setAttribute("list", list_return);
		return "toEditJsp";
	}
	
	public String edit(){
		item.setC_createdate(new Timestamp(System.currentTimeMillis()));
		item.setIsFlexibleForm("1");
		itemService.addItem(item);
		return getItemList();
	}
	
	/**
	 * 部门人员树
	 * @return
	 */
	public String showDepartmentTree() {
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		getRequest().setAttribute("department_rootId", department_rootId);
		
		return "showDepartmentTreeView";
	}

	/**
	 * 得到流程状态
	 * @return
	 */
	public String getWfStatus(){
		String id = getRequest().getParameter("workflowid");
		WfMain main = workflowBasicFlowService.getWfMainById(id);
		String status = main.getWfm_status();
		try {
			getResponse().getWriter().print(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * 描述：展示事项选定树
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-8-25 下午2:34:09
	 */
	public String showTree() {
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		getRequest().setAttribute("department_rootId", department_rootId);
		
		if (getRequest().getParameter("isBigDep")!=null) {
			getRequest().setAttribute("isBigDep", getRequest().getParameter("isBigDep"));
		}
		if (getRequest().getParameter("notEmployee")!=null) {
			getRequest().setAttribute("notEmployee", getRequest().getParameter("notEmployee"));
		}
		return "showTree";
	}
	
	
	public String showItem(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		List<WfItem> itemList = itemService.getItemList(emp.getDepartmentGuid());
		getRequest().setAttribute("itemList", itemList);
		return "showItemList";
	}
	
	
	/**
	 * 
	 * 描述：设置接收事项绑定页面
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-2-29 下午3:05:48
	 */
	public String setItemBinding(){
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String depId = getSession().getAttribute(MyConstants.DEPARMENT_ID)==null?null:(getSession().getAttribute(MyConstants.DEPARMENT_ID)).toString();
		//获取departmentGuid
		String departmentGuid = emp.getDepartmentGuid();
		ItemDepBinding itemDepBinding = itemService.getItemBinding(departmentGuid);
		String itemids = "";
		if(itemDepBinding!=null){
			itemids = itemDepBinding.getItemids();
		}
		//获取全部的事项
		WfItem item = new WfItem();
		item.setVc_ssbmid(depId);
		String itemName = getRequest().getParameter("itemName");
		item.setVc_sxmc(itemName);
		List<WfItem> itemList = itemService.getItemListForPage("", "", item, null, null);
		List<WfItem> bingItemList = new ArrayList<WfItem>();
		if(itemids!=null && !itemids.equals("")){
			String[] itemid = itemids.split(",");
			WfItem wfItem2 = null;
			//遍历list
			for(int i=0; itemList!=null && i<itemList.size(); i++){
				wfItem2 = itemList.get(i);
				boolean flag = true;
				 for(int j=0; itemid!=null && j<itemid.length; j++){
					 if(wfItem2.getId().equals(itemid[j])){
						 flag = false;
						 wfItem2.setIsChecked("1");
						 bingItemList.add(wfItem2);
						 continue;
					 }
				 }
				 if(flag==true){
					 bingItemList.add(wfItem2);
				 }
			}
		}else{
			bingItemList = itemList;
		}
		getRequest().setAttribute("itemList", bingItemList);
		getRequest().setAttribute("itemName", itemName);
		return "setItemBinding";
	}
	
	public void addItemBinding(){
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		//获取departmentGuid
		String departmentGuid = emp.getDepartmentGuid();
		String itemids = getRequest().getParameter("ids");
		ItemDepBinding itemDepBinding = new  ItemDepBinding();
		itemDepBinding.setItemids(itemids);
		itemDepBinding.setDepId(departmentGuid);
		itemDepBinding.setUserId(emp.getEmployeeGuid());
		itemDepBinding.setUserName(emp.getEmployeeName());
		itemDepBinding.setCreateDate(new Date());
		itemDepBinding.setStatus("1");
		itemService.addItemBinding(itemDepBinding);
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write("success");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 根据事项Id获取流程Id
	 * 2016-10-11
	 * xiep
	 */
	public void getWfIdByItemId(){
		String ids =getRequest().getParameter("ids");
		StringBuffer idsarray = new StringBuffer();
		if(CommonUtil.stringNotNULL(ids)){
			String[] idArray = ids.split(",") ;
			for(int j = 0 ;j<idArray.length;j++){
				idsarray.append("'"+idArray[j]+"'");
				if(j!=(idArray.length-1)){
					idsarray.append(",");
				}
			}
		}
		String itemName = getRequest().getParameter("itemName");
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		
		item = new WfItem();
		item.setId(idsarray.toString());
		item.setVc_sxmc(itemName);
		List<WfItem> list = itemService.getItemListForPage("", "", item, null, null);
		if(list != null && list.size() > 0){
			WfItem continueItem = list.get(0);
			if(continueItem != null){
				String continueWfId = continueItem.getLcid(); 
				PrintWriter out = null;
				try {
					out = this.getResponse().getWriter();
					out.write(continueWfId);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					out.flush();
					out.close();
				}
			}
		}
	}
	
	/** 
	 * getItemList4Mobile:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 *  
	 * @since JDK 1.6 
	 */
	public void getItemList4Mobile(){
		net.sf.json.JSONObject jsonObj = getJSONObject();
		String siteId = "";
		if(null != jsonObj){
			siteId = (String)jsonObj.get("siteId");
		}else{
			siteId = getRequest().getParameter("siteId");
		}
		net.sf.json.JSONObject outObj = new net.sf.json.JSONObject();
		if(StringUtils.isNotBlank(siteId)){
			WfItem item = new WfItem();
			item.setVc_ssbmid("'"+siteId+"'");
			List<WfItem> itemList = itemService.getItemListForPage("","",item,null,null);
			if(null != itemList && itemList.size()>0){
				net.sf.json.JSONArray arr = new net.sf.json.JSONArray();
				for (WfItem wfItem : itemList) {
					net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
					obj.put("itemId", wfItem.getId());
					obj.put("itemName", wfItem.getVc_sxmc());
					arr.add(obj);
				}
				outObj.put("code", "10000");
				outObj.put("message", "成功");
				outObj.put("items", arr);
			}else{
				outObj.put("code", "10002");
				outObj.put("message", "站点id有误");
			}
		}else{
			outObj.put("code", "10001");
			outObj.put("message", "必传参数不能为空");
		}
		toPage(outObj.toString());
	}
	
	private  HttpMethod getPostMethod(String url){
		PostMethod post=new PostMethod(url);
		return post;
	}
	
	private net.sf.json.JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return net.sf.json.JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
}
