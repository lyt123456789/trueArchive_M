package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.service.FormPermitService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.GroupService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;

public class FormPermitAction extends BaseAction{

	/**
	 * 
	 */
	private static final long 			serialVersionUID = 1L;
	
	private ZwkjFormService 			zwkjFormService;
	
	private FormPermitService 			formPermitService;
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private GroupService 				groupService;
	
	private ItemService					itemService;
	
	private InnerUser 					innerUser;
	
	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	public FormPermitService getFormPermitService() {
		return formPermitService;
	}

	public void setFormPermitService(FormPermitService formPermitService) {
		this.formPermitService = formPermitService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getFormList(){
		String type = getRequest().getParameter("type");
		String workflowid=getSession().getAttribute(
			MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		List<ZwkjForm> list=zwkjFormService.getFormListByParamForPage("workflowId", workflowid, null, null);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("type", type);
		if(!"1".equals(type)){
			return "formList";
		}else{
			return "formListForComment";
		}
	}
	
	public String getTagList(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String type = getRequest().getParameter("type");
		List<FormTagMapColumn> list=zwkjFormService.getFormTagMapColumnByFormId(vc_formid);
		List<FormTagMapColumn> resultList = new ArrayList<FormTagMapColumn>();
		if("1".equals(type)){
			for(FormTagMapColumn mapColumn : list){
				if("comment".equals(mapColumn.getFormtagtype())){
					resultList.add(mapColumn);
				}
			}
		}else{
			for(FormTagMapColumn mapColumn : list){
				if(!"comment".equals(mapColumn.getFormtagtype())){
					resultList.add(mapColumn);
				}
			}
		}
		getRequest().setAttribute("list", resultList);
		getRequest().setAttribute("vc_formid", vc_formid);
		getRequest().setAttribute("type", type);
		return "tagList";
	}
	
	public String getFormPermitList(){
		String vc_formid = getRequest().getParameter("vc_formid");
		List<WfFormPermit> list = formPermitService.getPermitByFormId(vc_formid);
		getRequest().setAttribute("vc_formid", vc_formid);
		getRequest().setAttribute("list", list);
		return "formPermitList";
	}
	
	public String getTagPermitList(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String type = getRequest().getParameter("type");
		
		List<WfFormPermit> list = formPermitService.getPermitByTagName(vc_formid, vc_tagname);
		getRequest().setAttribute("vc_formid", vc_formid);
		getRequest().setAttribute("vc_tagname", vc_tagname);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("type", type);
		return "tagPermitList";
	}
	
	public String toAddFormPermitJsp(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		List<WfNode> list = workflowBasicFlowService.getNodesByPid(lcid);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("vc_formid", vc_formid);
		return "toAddFormPermitJsp";
	}
	
	public String toAddTagPermitJsp(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		List<WfNode> list = workflowBasicFlowService.getNodesByPid(lcid);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("vc_formid", vc_formid);
		getRequest().setAttribute("vc_tagname", vc_tagname);
		return "toAddTagPermitJsp";
	}
	
	public String addFormPermit(){
		String[] nodeid = getRequest().getParameterValues("nodeid");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String[] vc_limit = getRequest().getParameterValues("vc_limit");
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_role = getRequest().getParameter("vc_rolename");
		String vc_roleid = "";
		String vc_rolename = "";
		if(vc_role != null){
			vc_roleid = vc_role.split(",")[0];
			vc_rolename = vc_role.split(",")[1];
		}
		
		WfFormPermit permit = new WfFormPermit();
		//删除数据表权限
		permit.setVc_formid(vc_formid);
		permit.setVc_rolename(vc_rolename);
		permit.setVc_roletype(vc_roletype);
		List<WfFormPermit> list = formPermitService.getPermitByParam(permit);
		for(WfFormPermit permit2 : list){
			formPermitService.delFormPermit(permit2);
		}
		
		//新增数据表权限
		List<WfFormPermit> addList = new ArrayList<WfFormPermit>();
		WfFormPermit permit3 = null;
		for(int i = 0 ; i < nodeid.length; i++){
			permit3 = new WfFormPermit();
			permit3.setVc_formid(vc_formid);
			permit3.setLcid(lcid);
			permit3.setVc_limit(vc_limit[i]);
			permit3.setVc_missionid(nodeid[i]);
			permit3.setVc_roleid(vc_roleid);
			permit3.setVc_rolename(vc_rolename);
			permit3.setVc_roletype(vc_roletype);
			addList.add(permit3);
		}
		
		formPermitService.addFormPermit(addList);
		
		//将设置权限推送到数据表所包含的字段
		List<FormTagMapColumn> tagList=zwkjFormService.getFormTagMapColumnByFormId(vc_formid);
		List<WfFormPermit> addFieldList = new ArrayList<WfFormPermit>();
		for(FormTagMapColumn tag : tagList){
			if("comment".equals(tag.getFormtagtype())){
				continue;
			}
			permit.setVc_tagname(tag.getFormtagname());
			list = formPermitService.getPermitByParam(permit);
			if(list != null && list.size() > 0){
				continue;
			}
			for(int i = 0 ; i < nodeid.length; i++){
				permit3 = new WfFormPermit();
				permit3.setVc_formid(tag.getFormid());
				permit3.setVc_tagname(tag.getFormtagname());
				permit3.setLcid(lcid);
				permit3.setVc_limit(vc_limit[i]);
				permit3.setVc_missionid(nodeid[i]);
				permit3.setVc_roleid(vc_roleid);
				permit3.setVc_rolename(vc_rolename);
				permit3.setVc_roletype(vc_roletype);
				addFieldList.add(permit3);
			}
		}
		formPermitService.addFormPermit(addFieldList);
		
		return getFormPermitList();
	}
	
	public String addTagPermit(){
		String[] nodeid = getRequest().getParameterValues("nodeid");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String[] vc_limit = getRequest().getParameterValues("vc_limit");
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_role = getRequest().getParameter("vc_rolename");
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		String vc_roleid = "";
		String vc_rolename = "";
		if(vc_role != null){
			vc_roleid = vc_role.split(",")[0];
			vc_rolename = vc_role.split(",")[1];
		}
		
		WfFormPermit permit = new WfFormPermit();
		//删除数据表权限
		permit.setVc_formid(vc_formid);
		permit.setVc_tagname(vc_tagname);
		permit.setVc_rolename(vc_rolename);
		permit.setVc_roletype(vc_roletype);
		List<WfFormPermit> list = formPermitService.getPermitByParam(permit);
		for(WfFormPermit permit2 : list){
			formPermitService.delFormPermit(permit2);
		}
		
		//新增数据表权限
		List<WfFormPermit> addList = new ArrayList<WfFormPermit>();
		WfFormPermit permit3 = null;
		for(int i = 0 ; i < nodeid.length; i++){
			permit3 = new WfFormPermit();
			permit3.setVc_formid(vc_formid);
			permit3.setVc_tagname(vc_tagname);
			permit3.setLcid(lcid);
			permit3.setVc_limit(vc_limit[i]);
			permit3.setVc_missionid(nodeid[i]);
			permit3.setVc_roleid(vc_roleid);
			permit3.setVc_rolename(vc_rolename);
			permit3.setVc_roletype(vc_roletype);
			addList.add(permit3);
		}
		
		formPermitService.addFormPermit(addList);
		
		return getTagPermitList();
	}
	
	public void getRole(){
		String type = getRequest().getParameter("type");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		
		String siteId = "";
		if(StringUtils.isNotBlank(type) && type.equals("3")){
			List<WfItem> items = itemService.getItemByLcid(lcid);
			if(null != items && items.size()>0){
				siteId = items.get(0).getVc_ssbmid();
			}
		}
		
		List<InnerUser> list = groupService.getInnerUsersByType(type, lcid, siteId);
		StringBuffer sb = new StringBuffer();
		for(InnerUser user : list){
			if("4".equals(type)){ //流程用户组，只显示该流程下的用户组
				if(CommonUtil.stringNotNULL(lcid) && lcid.equals(user.getWorkflowId())){
					sb.append("<option value='").append(user.getId()).append(",")
					.append(user.getName()).append("'>").append(user.getName())
					.append("</option>");
				}
			}else{
				sb.append("<option value='").append(user.getId()).append(",")
				.append(user.getName()).append("'>").append(user.getName())
				.append("</option>");
			}
			
		}
		PrintWriter out =null;
		try {
			out = this.getResponse().getWriter() ;
			out.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
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
	
	public String delPermit(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_rolename = getRequest().getParameter("vc_rolename");
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		WfFormPermit permit = new WfFormPermit();
		permit.setVc_formid(vc_formid);
		permit.setVc_tagname(vc_tagname);
		permit.setVc_roletype(vc_roletype);
		permit.setVc_rolename(vc_rolename);
		
		List<WfFormPermit> list = formPermitService.getPermitByParam(permit);
		
		for(WfFormPermit permit2 : list){
			formPermitService.delFormPermit(permit2);
		}
		if(CommonUtil.stringNotNULL(vc_tagname)){
			return getTagPermitList();
		}else{
			return getFormPermitList();
		}
	}
	
	public String toEditFormPermit(){
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_rolename = getRequest().getParameter("vc_rolename");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		
		WfFormPermit permit = new WfFormPermit();
		permit.setVc_formid(vc_formid);
		permit.setVc_tagname(vc_tagname);
		permit.setVc_roletype(vc_roletype);
		permit.setVc_rolename(vc_rolename);
		
		List<WfFormPermit> list = formPermitService.getPermitByParam(permit);
		List<WfNode> nodeList = workflowBasicFlowService.getNodesByPid(lcid);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("nodeList", nodeList);
		getRequest().setAttribute("vc_formid", vc_formid);
		getRequest().setAttribute("vc_tagname", vc_tagname);
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		return "toEditFormPermitJsp";
	}
	
	public String editPermit(){
		String[] nodeid = getRequest().getParameterValues("nodeid");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String[] vc_limit = getRequest().getParameterValues("vc_limit");
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_tagname = getRequest().getParameter("vc_tagname");
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_role = getRequest().getParameter("vc_rolename");
		String vc_roleid = "";
		String vc_rolename = "";
		if(vc_role != null){
			vc_roleid = vc_role.split(",")[0];
			vc_rolename = vc_role.split(",")[1];
		}
		
		WfFormPermit permit = new WfFormPermit();
		//删除数据表权限
		permit.setVc_formid(vc_formid);
		permit.setVc_rolename(vc_rolename);
		permit.setVc_roletype(vc_roletype);
		permit.setVc_tagname(vc_tagname);
		List<WfFormPermit> list = formPermitService.getPermitByParam(permit);
		for(WfFormPermit permit2 : list){
			formPermitService.delFormPermit(permit2);
		}
		
		//新增数据表权限
		List<WfFormPermit> addList = new ArrayList<WfFormPermit>();
		WfFormPermit permit3 = null;
		for(int i = 0 ; i < nodeid.length; i++){
			permit3 = new WfFormPermit();
			permit3.setVc_formid(vc_formid);
			permit3.setVc_tagname(vc_tagname);
			permit3.setLcid(lcid);
			permit3.setVc_limit(vc_limit[i]);
			permit3.setVc_missionid(nodeid[i]);
			permit3.setVc_roleid(vc_roleid);
			permit3.setVc_rolename(vc_rolename);
			permit3.setVc_roletype(vc_roletype);
			addList.add(permit3);
		}
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		formPermitService.addFormPermit(addList);
		if(CommonUtil.stringNotNULL(vc_tagname)){
			return getTagPermitList();
		}else{
			return getFormPermitList();
		}
		
	}
	
	/**
	 * 
	 * @Title: getInnerUserList
	 * @Description: 内置用户列表
	 * @return String    返回类型
	 */
	public String getInnerUserList(){
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		
		//String type=getRequest().getParameter("type");
			
		if(innerUser==null){
			innerUser=new InnerUser();
		}
		innerUser.setType(4);
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=groupService.getInnerUserCountForPage(column, value, innerUser);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<InnerUser> list=groupService.getInnerUserListForPage(new String[]{column}, new String[]{value}, innerUser, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "getInnerUserList";
	}
	
	public String toPermitJsp(){
		String vc_roletype = getRequest().getParameter("vc_roletype");
		String vc_roleid = getRequest().getParameter("vc_roleid");
		String vc_rolename = getRequest().getParameter("vc_rolename");
		String vc_formid = getRequest().getParameter("vc_formid");
		String vc_missionid = getRequest().getParameter("vc_missionid");
		
		String workflowid=getSession().getAttribute(
				MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		List<ZwkjForm> formList=zwkjFormService.getFormListByParamForPage("workflowId", workflowid, null, null);
		List<WfNode> nodeList = workflowBasicFlowService.getNodesByPid(workflowid);
		getRequest().setAttribute("vc_roletype", vc_roletype);
		getRequest().setAttribute("vc_roleid", vc_roleid);
		getRequest().setAttribute("vc_rolename", vc_rolename);
		getRequest().setAttribute("formList", formList);
		getRequest().setAttribute("nodeList", nodeList);
		if(CommonUtil.stringNotNULL(vc_formid) && CommonUtil.stringNotNULL(vc_missionid)){
			List<FormTagMapColumn> tagList = zwkjFormService.getFormTagMapColumnByFormId(vc_formid);
			WfFormPermit permit = new WfFormPermit();
			permit.setVc_roletype( vc_roletype);
			permit.setVc_roleid(vc_roleid);
			permit.setVc_rolename(vc_rolename);
			permit.setVc_formid(vc_formid);
			permit.setVc_missionid(vc_missionid);
			
			List<WfFormPermit> list = formPermitService.getPermitByParam2(permit);
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("tagList", tagList);
			getRequest().setAttribute("vc_formid", vc_roleid);
			getRequest().setAttribute("vc_missionid", vc_missionid);
		}else{
			getRequest().setAttribute("list", null);
			getRequest().setAttribute("tagList", null);
		}
		return "permitJsp";
	}
	
	public String getLimit(){
		//String nodeid = getRequest().getParameter("nodeid");
		//String userid = getRequest().getParameter("userid");
//		formPermitService.getEmployeeLimit(userid, nodeid);
		return getFormPermitList();
	}
	
}
