package cn.com.trueway.workflow.set.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.GroupService;
import cn.com.trueway.workflow.set.service.ZtreeService;

import com.google.gson.Gson;

public class GroupAction extends BaseAction{
	
	private static final long 			serialVersionUID = -2368952048407447269L;
	
	private InnerUser 					innerUser;
	
	private GroupService 				groupService;
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private TableInfoService 			tableInfoService;
	
	private PendingService 				pendingService;
	
	private EmployeeService 			employeeService;
	
	private ZtreeService 				ztreeService;
	
	public InnerUser getInnerUser() {
		return innerUser;
	}

	public void setInnerUser(InnerUser innerUser) {
		this.innerUser = innerUser;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public ZtreeService getZtreeService() {
	    return ztreeService;
	}

	public void setZtreeService(ZtreeService ztreeService) {
	    this.ztreeService = ztreeService;
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
		String lcid =  this.getRequest().getParameter("id");
		if(CommonUtil.stringIsNULL(lcid)){
			lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		
		WfItem item = tableInfoService.findItemByWorkFlowId(lcid);
		String siteId = "";
		if(null != item){
			siteId = item.getVc_ssbmid();
		}
		
		String isSystemGroup=getRequest().getParameter("isSystemGroup");
		if(innerUser==null){
			innerUser=new InnerUser();
		}
		if (CommonUtil.stringNotNULL(isSystemGroup) && "1".equals(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
			innerUser.setWorkflowId("");
		}else{
			innerUser.setWorkflowId(lcid);
		}
		innerUser.setType(4);
		innerUser.setSiteId(siteId);
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=groupService.getInnerUserCountForPage(column, value, innerUser);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<InnerUser> list=groupService.getInnerUserListForPage(new String[]{column}, new String[]{value}, innerUser, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("lcid", lcid);
		return "getInnerUserList";
	}
	
	/**
	 * 判断当前流程下的用户组是否存在
	 * @throws IOException 
	 */
	public void isExistOfInnerUser() throws IOException{
		String id = getRequest().getParameter("id");
		String teamName = getRequest().getParameter("teamName");
		String type = getRequest().getParameter("type");
		String update = getRequest().getParameter("update");
		String add = getRequest().getParameter("add");
		String isSystemGroup = getRequest().getParameter("isSystemGroup");
		
		String lcid = this.getRequest().getParameter("lcid");
		if(CommonUtil.stringIsNULL(lcid)){
			lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		if(CommonUtil.stringNotNULL(isSystemGroup) && "1".equals(isSystemGroup)){
			lcid = "";
		}
		List<InnerUser> list = groupService.getInnerUserList(lcid,type);
		//新增组名
		if(add != null && !("").equals(add)){
			for (InnerUser innerUser : list) {
				try {
					if(teamName.equals(innerUser.getName())){
						getResponse().getWriter().print("yes");
					}else{
						getResponse().getWriter().print("noExist");
					}
				} catch (IOException e) {
					getResponse().getWriter().print("no");
				}
			}
		}else if(update!=null && !("").equals(update)){
			//修改之前的组名
			InnerUser innerUserIsHave = groupService.findInnerUserById(id);
			if(innerUserIsHave != null){
				for (int i=0;i < list.size();i++) {
					if(list.get(i).getName().equals(innerUserIsHave.getName())){
						list.remove(list.get(i));
						break;
					}
				}
			}
			if(list!=null && list.size()!=0){
				for (InnerUser innerUser : list) {
					try {
						if(teamName.equals(innerUser.getName())){
							getResponse().getWriter().print("yes");
						}
					} catch (IOException e) {
						getResponse().getWriter().print("no");
					}
				}
			}else{
				getResponse().getWriter().print("noExist");
			}
		}
	}
	
	/**
	 * 
	 * @Title: toAddInnerUserJsp
	 * @Description: 跳转新增内置用户
	 * @return String    返回类型
	 */
	public String toAddInnerUserJsp(){
		innerUser=null;//必须清空实体

		String mc=getRequest().getParameter("mc");
		String lcid =getRequest().getParameter("id");
		getRequest().setAttribute("lcid", lcid);
		if (CommonUtil.stringNotNULL(mc)) {
			getRequest().setAttribute("mc", mc);
		}
		//String type=getRequest().getParameter("type");
		String isSystemGroup = getRequest().getParameter("isSystemGroup");
		if (CommonUtil.stringNotNULL(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
		}
		return "toAddInnerUserJsp";
	}
	/**
	 * 
	 * @Title: addInnerUser
	 * @Description: 新增内置用户
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void addInnerUser() throws IOException{
		/************************新增用户组信息******************************/
		String isSystemGroup=getRequest().getParameter("isSystemGroup");
		String lcid = this.getRequest().getParameter("lcid");
		if(CommonUtil.stringIsNULL(lcid)){
			lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		if (CommonUtil.stringNotNULL(isSystemGroup) && "1".equals(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
			innerUser.setName("平台-" + innerUser.getName());
			innerUser.setWorkflowId("");
		}else{
			innerUser.setName("流程-" + innerUser.getName());
			innerUser.setWorkflowId(lcid);
		}
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		innerUser.setIntime(CommonUtil.getNowTimeTimestamp(null));
		innerUser.setType(4);
		innerUser.setInperson(employee.getEmployeeName());//TODO
		groupService.save(innerUser);
		/************************新增用户组关联的人员信息******************************/
		String inneruserid=innerUser.getId();
		
		//先删除原先关联数据
		groupService.deleteByInnerUserId(inneruserid);
		
		//新增新关联数据
		String allEmpIds = "";
		String allDeptIds = "";
		JSONArray jsonTree = new JSONArray();
		String employeeinfo=getRequest().getParameter("employeeinfo");
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					if (strs!=null&&strs.length==3) {
						InnerUserMapEmployee m=new InnerUserMapEmployee();
						m.setInneruser_id(inneruserid);
						String info = strs[0];
						if(info!=null && !info.equals("")){
							String[] ids = info.split(",");
							m.setEmployee_id(ids[0]);
							allEmpIds = allEmpIds + ids[0] + ",";
							if(ids.length==2){
								m.setDepartment_id(ids[1]);
								allDeptIds = allDeptIds + ids[1] + ",";
							}
						}
						m.setEmployee_name(strs[1]);
						m.setEmployee_shortdn(strs[2]);
						m.setZindex(i);
						groupService.save(m);
					}
					
				}
				//形成静态树
				if(CommonUtil.stringNotNULL(allDeptIds)){
				    allDeptIds = allDeptIds.substring(0, allDeptIds.length() - 1);
				}
				if(CommonUtil.stringNotNULL(allEmpIds)){
				    allEmpIds = allEmpIds.substring(0, allEmpIds.length() - 1);
				}
				List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(allDeptIds);
				List<Employee> allEmps = ztreeService.getAllEmpsByUserIds(allEmpIds);
				
				// 生成JSON字符串
				if(!allDepts.isEmpty()){
				    for(Department dept:allDepts){
					if(dept!=null){
					    JSONObject jo = new JSONObject();
					    jo.put("id", dept.getDepartmentGuid());
					    jo.put("pId", dept.getSuperiorGuid());
					    jo.put("name", dept.getDepartmentName());
					    jo.put("open", false);
					    jo.put("type", "folder");
					    jo.put("isParent", true);
					    jo.put("chkDisabled", false);
					    jsonTree.add(jo);
					}
				    }
				}
				if(!allEmps.isEmpty()){
				    for(Employee e:allEmps){
					if(e!=null){
					    JSONObject jo = new JSONObject();
					   /* Object obj = RemoteLogin.map.get(e.getEmployeeGuid());
					    if(obj!=null){//在线
						jo.put("online", true);
					    }else{//不在线
						jo.put("online", false);
					    }*/
					    jo.put("online", false);
					    jo.put("id", e.getEmployeeGuid());
					    jo.put("pId", e.getDepartmentGuid());
					    jo.put("name", e.getEmployeeName());
					    jo.put("type", "file");
					    jsonTree.add(jo);
					}
				    }
				}
			}
			InnerUser newInnerUser = groupService.findInnerUserById(inneruserid);
			//保存静态人员树
			//innerUser.setEmpJsonTree(jsonTree.toString());
			groupService.update(newInnerUser);
		}
		getResponse().sendRedirect(getRequest().getContextPath()+"/group_getInnerUserList.do?id="+lcid);
	}
	/**
	 * 
	 * @Title: toUpdateInnerUserJsp
	 * @Description: 跳转更新内置用户
	 * @return String    返回类型
	 */
	public String toUpdateInnerUserJsp(){
		/************************查询用户组信息******************************/
		innerUser=null;//必须清空实体
		String id=getRequest().getParameter("id");
		innerUser=groupService.getOneInnerUserById(id);
		getRequest().setAttribute("innerUser",innerUser );
		getRequest().setAttribute("id", id);
		String isSystemGroup=getRequest().getParameter("isSystemGroup");
		if (CommonUtil.stringNotNULL(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
		}

		String mc=getRequest().getParameter("mc");
		if (CommonUtil.stringNotNULL(mc)) {
			getRequest().setAttribute("mc", mc);
		}
		/************************查询用户组关联的人员信息******************************/
		//查询所有关联数据
		getRequest().setAttribute("mapList", groupService.getListByInnerUserId(id,null,""));
		
		return "toUpdateInnerUserJsp";
	}
	/**
	 * 异步根据用户组id获取绑定的用户
	 */
	public void synGetUser(){
//		getResponse().setContentType("text/xml; charset=gb2312");
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String id=getRequest().getParameter("id");
				List<InnerUserMapEmployee> list=groupService.getListByInnerUserId(id,null,"");
				Gson gson=new Gson();
				out.write(gson.toJson(list));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
		
	}
	/**
	 * 
	 * @Title: updateInnerUser
	 * @Description: 更新内置用户
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void updateInnerUser() throws IOException{
		String isSystemGroup=getRequest().getParameter("isSystemGroup");
		if (CommonUtil.stringNotNULL(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
		}
		innerUser.setType(4);
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		innerUser.setUpdatetime(CommonUtil.getNowTimeTimestamp(null));
		innerUser.setUpdateperson(employee.getEmployeeName());//TODO
		
		/************************更新用户组关联的人员信息******************************/
		String inneruserid=innerUser.getId();
		
		//先删除原先关联数据
		groupService.deleteByInnerUserId(inneruserid);
		
		//新增新关联数据
		String allEmpIds = "";
		String allDeptIds = "";
		String employeeinfo=getRequest().getParameter("employeeinfo");
		JSONArray jsonTree = new JSONArray();
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					JSONObject jo = new JSONObject();
					if (strs!=null && strs.length==3) {
						InnerUserMapEmployee m=new InnerUserMapEmployee();
						m.setInneruser_id(inneruserid);
						String info = strs[0];
						if(info!=null && !info.equals("")){
							String[] ids = info.split(",");
							m.setEmployee_id(ids[0]);
							allEmpIds = allEmpIds + ids[0] + ",";
							jo.put("id", ids[0]);
							if(ids.length==2){
								m.setDepartment_id(ids[1]);
								jo.put("pId", ids[1]);
								allDeptIds = allDeptIds + ids[1] + ",";
							}
							
							 
							 jo.put("online", false);
							 
							 jo.put("name", strs[1]);
							 jo.put("type", "file");
							 jsonTree.add(jo);
						}
						m.setEmployee_name(strs[1]);
						m.setEmployee_shortdn(strs[2]);
						m.setZindex(i);
						groupService.save(m);
					}
				}
				//形成静态树
				if(CommonUtil.stringNotNULL(allDeptIds)){
				    allDeptIds = allDeptIds.substring(0, allDeptIds.length() - 1);
				}
				if(CommonUtil.stringNotNULL(allEmpIds)){
				    allEmpIds = allEmpIds.substring(0, allEmpIds.length() - 1);
				}
				List<Department> allDepts = ztreeService.getAllParentDeptBydeptId(allDeptIds);
//				List<Employee> allEmps = ztreeService.getAllEmpsByUserIds(allEmpIds);
				// 生成JSON字符串
				if(!allDepts.isEmpty()){
				    for(Department dept:allDepts){
					if(dept!=null){
					    JSONObject jo = new JSONObject();
					    jo.put("id", dept.getDepartmentGuid());
					    jo.put("pId", dept.getSuperiorGuid());
					    jo.put("name", dept.getDepartmentName());
					    jo.put("open", false);
					    jo.put("type", "folder");
					    jo.put("isParent", true);
					    jo.put("chkDisabled", false);
					    jsonTree.add(jo);
					}
				    }
				}
				/*if(!allEmps.isEmpty()){
				    for(Employee e:allEmps){
					if(e!=null){
					    JSONObject jo = new JSONObject();
					    jo.put("online", false);
					    jo.put("id", e.getEmployeeGuid());
					    jo.put("pId", e.getDepartmentGuid());
					    jo.put("name", e.getEmployeeName());
					    jo.put("type", "file");
					    jsonTree.add(jo);
					}
				    }
				}*/
				//保存静态人员树
				//innerUser.setEmpJsonTree(jsonTree.toString());
			}
		}
		groupService.update(innerUser);
		//return getInnerUserList();
		getResponse().sendRedirect(getRequest().getContextPath()+"/group_getInnerUserList.do?id="+innerUser.getWorkflowId());
	}
	/**
	 * 
	 * @Title: deleteInnerUser
	 * @Description: 删除内置用户
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void deleteInnerUser() throws IOException{
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				InnerUser user=new InnerUser();
				user.setId(idsArr[i]);
				groupService.delete(user);
			}
		}
		
		String isSystemGroup=getRequest().getParameter("isSystemGroup");
		if (CommonUtil.stringNotNULL(isSystemGroup)) {
			getRequest().setAttribute("isSystemGroup", isSystemGroup);
		}
		//return getInnerUserList();
		getResponse().sendRedirect(getRequest().getContextPath()+"/group_getInnerUserList.do?id="+groupService.findInnerUserById(ids.split(",")[0]));
	}
	
	/**
	 * 
	 * @Title: viewInnerUser
	 * @Description: 查看内置用户
	 * @return String    返回类型
	 */
	public String viewInnerUser(){
		String id=getRequest().getParameter("id");
		getRequest().setAttribute("innerUser", groupService.getOneInnerUserById(id));
		return "viewInnerUser";
	}
	
	/**
	 * 
	 * @Title: toMapEmployeeJsp
	 * @Description: 转向设置组和人员对应关系页面
	 * @return String    返回类型
	 */
	public String toMapEmployeeJsp(){
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		String id=getRequest().getParameter("id");
		innerUser=groupService.getOneInnerUserById(id);
		getRequest().setAttribute("innerUser",innerUser );
		
		//查询所有关联数据
		getRequest().setAttribute("mapList", groupService.getListByInnerUserId(id,null,""));
		
		return "toMapEmployeeJsp";
	}
	
	public String addMapEmployeeInfo(){
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		String inneruserid=getRequest().getParameter("id");
		
		//先删除原先关联数据
		groupService.deleteByInnerUserId(inneruserid);
		
		//新增新关联数据
		String employeeinfo=getRequest().getParameter("employeeinfo");
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					if (strs!=null&&strs.length==3) {
						InnerUserMapEmployee m=new InnerUserMapEmployee();
						m.setInneruser_id(inneruserid);
						m.setEmployee_id(strs[0]);
						m.setEmployee_name(strs[1]);
						m.setEmployee_shortdn(strs[2]);
						m.setZindex(i);
						groupService.save(m);
					}
					
				}
			}
		}
		
		return getInnerUserList();
	}
	
	/**
	 * @Title: validateLogin
	 * @Description: 异步获取用户组 void    返回类型
	 
	 */
	public void getGroups(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
//				String workflow_id=getSession().getAttribute(MyConstants.workflow_session_id)==null?null:getSession().getAttribute(MyConstants.workflow_session_id).toString();
				String type=getRequest().getParameter("type");//角色类型
				InnerUser innerUser=new InnerUser();
				innerUser.setType(Integer.parseInt(type));
				List<InnerUser> list=groupService.getInnerUserListForPage(null, null, innerUser, null, null);
				String str="";
				if (list!=null) {
					for (int i = 0; i < list.size(); i++) {
						str+=list.get(i).getName()+','+list.get(i).getId()+";";
					}
				}
				out.write(str);
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
	}
	
	
	/**
	 * 获取所有的用户组相关的人员  为流程节点选择字段
	 * @return
	 */
	public String getListForNodeIframe(){
		String nodegroup=getRequest().getParameter("nodegroup");//节点人员组id 
		String name=getRequest().getParameter("name");//人员名称
		if(Utils.isNotNullOrEmpty(name)){
			try {
				name = URLDecoder.decode(name,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("nodegroup", nodegroup);
		//node_route_type 0:单人; 1:并行抢占式; 2:并行完全式;
		String node_route_type = getRequest().getParameter("node_route_type");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		if(innerUser==null)innerUser=new InnerUser();
		innerUser.setType(4);
		innerUser.setWorkflowId(lcid);
		String[] column=new String[]{"id"};
		String[] value=new String[]{nodegroup};
		List<InnerUser> list=groupService.getInnerUserListForPage(column, value, innerUser, null, null);
		List<InnerUserMapEmployee> mapList=new ArrayList<InnerUserMapEmployee>();
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				mapList.addAll(groupService.getListByInnerUserId(list.get(i).getId(),name, ""));
			}
		}
		getRequest().setAttribute("mapList", mapList);
		getRequest().setAttribute("node_route_type", node_route_type);
		String ids=getRequest().getParameter("ids");
		getRequest().setAttribute("ids", ids);
		return "getListForNode1";
	}
	/**
	 * 获取所有的用户组相关的人员  为流程节点选择字段
	 * @return
	 */
	public String getListForNode(){
		String nodegroup=getRequest().getParameter("nodegroup");//节点人员组id 
		String name=getRequest().getParameter("name");//人员名称
		if(Utils.isNotNullOrEmpty(name)){
			try {
				name = URLDecoder.decode(name,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("nodegroup", nodegroup);
		//node_route_type 0:单人; 1:并行抢占式; 2:并行完全式;
		String node_route_type = getRequest().getParameter("node_route_type");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		if(innerUser==null)innerUser=new InnerUser();
		innerUser.setType(4);
		if(CommonUtil.stringIsNULL(nodegroup)){
			innerUser.setWorkflowId(lcid);
		}else{
			innerUser.setWorkflowId("");
		}
		String[] column=new String[]{"id"};
		String[] value=new String[]{nodegroup};
		List<InnerUser> list=groupService.getInnerUserListForPage(column, value, innerUser, null, null);
		List<InnerUserMapEmployee> mapList=new ArrayList<InnerUserMapEmployee>();
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				mapList.addAll(groupService.getListByInnerUserId(list.get(i).getId(),name,""));
			}
		}
		getRequest().setAttribute("mapList", mapList);
		getRequest().setAttribute("node_route_type", node_route_type);
		String ids=getRequest().getParameter("ids");
		
		if(node_route_type!=null && node_route_type.equals("2")){
			getRequest().setAttribute("routType", "2");
			getRequest().setAttribute("userList", mapList);
			List<InnerUserMapEmployee> zsuserList=new ArrayList<InnerUserMapEmployee>();
			List<InnerUserMapEmployee> csuserList=new ArrayList<InnerUserMapEmployee>();

			if(ids!=null && ids.length()>0){
				String[] id = ids.split(";");
				String zsids = id[0];
				if(zsids!=null){
					String[] zsid = zsids.split(",");
					for(String zs: zsid){
						for(InnerUserMapEmployee employee : mapList){
							if(zs.equals(employee.getEmployee_id())){
								zsuserList.add(employee);
							}
						}
					}
				}
				if(id.length==2){
					String csids = id[1];
					String[] csid = csids.split(",");
					for(String cs: csid){
						for(InnerUserMapEmployee employee : mapList){
							if(cs.equals(employee.getEmployee_id())){
								csuserList.add(employee);
							}
						}						
					}
				}
				getRequest().setAttribute("zsuserList", zsuserList);
				getRequest().setAttribute("csuserList", csuserList);

			}
			return "getListForNode2";
		}else{
			getRequest().setAttribute("ids", ids);
			return "getListForNode";
		}
	}
	
	
	public String toUserDepartmentJsp(){
		String routType = getRequest().getParameter("routType");
		String userList = getRequest().getParameter("userList");
		getRequest().setAttribute("userList", userList);
		getRequest().setAttribute("routType", routType);
		String mc = getRequest().getParameter("mc");
		getRequest().setAttribute("mc", mc);
		
		String wfl_id = getRequest().getParameter("id");
		WfLine line = workflowBasicFlowService.getLineById(wfl_id);
		String ids = "";
		if(line != null && line.getWfl_conditions()!= null){
			ids = line.getWfl_conditions();
		}
		
		String useridOrGroupids = "";
		if(ids!=null){
			String[] id = ids.split(",");
			for(int i=0 ;id!=null && i<id.length; i++){
				if(id[i]==null || !id[i].trim().equals("")){
					useridOrGroupids += "'"+id[i]+"',";
				}
			}
		}
		if(useridOrGroupids!=null && useridOrGroupids.length()>0){
			useridOrGroupids = useridOrGroupids.substring(0,useridOrGroupids.length()-1);
		}
		//选择人员list
		List<Employee>  mapList = employeeService.getEmployeeList(useridOrGroupids);
		getRequest().setAttribute("mapList", mapList);
		
		//选择机构list
		
		List<Department>  depList = groupService.getUserGroupList(useridOrGroupids);
		getRequest().setAttribute("depList", depList);
		
		return "toUserDepartmentJsp";
	}
	
	public String getUserListForLine(){
		String nodegroup=getRequest().getParameter("nodegroup");//节点人员组id 
		if(innerUser==null)innerUser=new InnerUser();
		innerUser.setType(4);
		List<InnerUser> list=groupService.getInnerUserListForPage(new String[]{"id"}, new String[]{nodegroup}, innerUser, null, null);
		List<InnerUserMapEmployee> mapList=new ArrayList<InnerUserMapEmployee>();
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				mapList.addAll(groupService.getListByInnerUserId(list.get(i).getId(),null, ""));
			}
		}
		getRequest().setAttribute("mapList", mapList);
		String ids=getRequest().getParameter("ids");
		getRequest().setAttribute("ids", ids);
		return "getListForNode";
	}
	
	
	
	
	/**
	 * 
	 * @Title: getUsers 
	 * @Description: 显示当前节点人员组内除本身以外的人员
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getInnerOtherUsers() {
		String nodeId = getRequest().getParameter("nodeId");
		String processId = getRequest().getParameter("processId");
		WfProcess wfProcess = pendingService.getProcessByID(processId);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("processId", processId);
		getRequest().setAttribute("isZBPush", getRequest().getParameter("isZBPush"));
		getRequest().setAttribute("id", UuidGenerator.generate36UUID());
		getRequest().setAttribute("allInstanceId", wfProcess.getfInstancdUid()==null?wfProcess.getAllInstanceid():wfProcess.getfInstancdUid());
		return "getInnerOtherUsers";
	}
	
	/**
	 * 
	 * @Title: getInnerUserList
	 * @Description: 内置用户列表
	 * @return String    返回类型
	 */
	public String getInnerGroupForNode(){
		String lcid = this.getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(lcid)){
			lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		String siteId = "";
		WfItem item = tableInfoService.findItemByWorkFlowId(lcid);
		if(null != item){
			siteId = item.getVc_ssbmid();
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("workflowId", lcid);
		map.put("type", 4 + "");
		map.put("siteId", siteId);
		List<InnerUser> list=groupService.getInnerUserListByParams(map);
		
		getRequest().setAttribute("list", list);
		String ids=getRequest().getParameter("ids");
		getRequest().setAttribute("ids", ids);
		return "getInnerUserListForNode";
	}
	
	public void getInnerUserCount(){
		String workflowId = getRequest().getParameter("workflowId");
		List<InnerUser> users = groupService.getInnerUserList(workflowId,"4");
		JSONArray ja = new JSONArray();
		for(InnerUser u: users){
			ja.add(u.getId());
		}
		JSONObject jo = new JSONObject();
		jo.put("count", users.size());
		jo.put("userIds", ja);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		// 打开流
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			out.print(jo.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
