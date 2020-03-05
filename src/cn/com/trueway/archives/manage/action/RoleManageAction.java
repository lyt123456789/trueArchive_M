package cn.com.trueway.archives.manage.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;





/*import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;*/
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.manage.pojo.BtnDictionary;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.TreeDataOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.service.RoleService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 角色管理相关 
 * @author lixr
 *
 */
public class RoleManageAction extends BaseAction {
	
	private final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final long serialVersionUID = -4786716771613274776L;
	private RoleManageService roleManageService;
	private DataTempService dataTempService;
	private RoleService  roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public RoleManageService getRoleManageService() {
		return roleManageService;
	}
	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	} 
	
	/*private EssModelService essModelService;*/
	
	/*public EssModelService getEssModelService() {
		return essModelService;
	}

	public void setEssModelService(EssModelService essModelService) {
		this.essModelService = essModelService;
	}*/
	
	
	
	public DataTempService getDataTempService() {
		return dataTempService;
	}

	public void setDataTempService(DataTempService dataTempService) {
		this.dataTempService = dataTempService;
	}

	//进入角色-数据授权页面
	public String toRoleDataAuthorizeJsp() {
		String roleName = getRequest().getParameter("roleName");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleName", roleName);
		//int count = this.roleService.findRoleCount(map);
		//Paging.setPagingParams(getRequest(), pageSize, count);
		List<Role> list = roleService.findRoleList(map,null,null);
		String roleId = "";
		if (null != list && list.size()>0) {
			roleId = list.get(0).getRoleId();
		}
		//获取按钮字典表数据
		List<BtnDictionary> btn_list = roleService.findBtnList(map,null,null);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("firstRoleId", roleId);
		getRequest().setAttribute("btn_list", btn_list);
		return "roleDataAuthorizeJsp";
	}
	
	

	/**
	 * 角色关联  节点  页面
	 * @return
	 */
	public String toCasualTreePage() {
		Map<String,String> map = new HashMap<String,String>();
		String roleId = getRequest().getParameter("roleId");
		String treeNodes="";
		map.put("roleId", roleId);
		List<TreeNodeOfRole> list = this.roleManageService.getTreeNodesOfRole(map);
		if(list!=null&&list.size()>0){
			treeNodes=list.get(0).getTreeNodes();
		}
		map.clear();
		
		
		//获得目录树
		map.put("business", "35547CE2-A572-4CDF-83D0-48B404164C59");//需要管理权限的业务数（利用模块也是档案管理模块的业务id）
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		//json.append("{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": [");
		json.append("[");
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			if(treeNodes!=""&&(treeNodes+",").indexOf(e.getId()+",")>-1){
				json.append("\"checkArr\":\"").append("1\",");
			}else{
				json.append("\"checkArr\":\"").append("0\",");
			}
			json.append("\"basicData\":").append(e.getId()).append("");
			//json.append("\"basicData\":{\"nodeType\":\"").append(e.getNodeType()).append("\"}");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("dataJson", json.toString());  
		getRequest().setAttribute("roleIds", roleId);  
		getRequest().setAttribute("treeNodes", treeNodes);  
		return "showDtreeJsp";
	}
	
	
	/**
	 * 保存角色目录树关联信息
	 */
	public void saveRoleNode() {
		String roleId = getRequest().getParameter("roleId");
		String nodeIds = getRequest().getParameter("nodeIds");
/*		String menuFlag = getRequest().getParameter("menuFlag");*/
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleId", roleId);
		List<TreeNodeOfRole> list = this.roleManageService.getTreeNodesOfRole(map);
		TreeNodeOfRole tnr = new TreeNodeOfRole();
		if(list!=null&&list.size()>0){
			tnr=list.get(0);
			tnr.setTreeNodes(nodeIds);
		}else{
			tnr.setRoleId(roleId);
			tnr.setTreeNodes(nodeIds);
		}
		PrintWriter out = null;
		String retrunStr = "";
		try {
			this.roleManageService.updateTreeNodeOfRole(tnr);
			out = getResponse().getWriter();
			retrunStr = "success";
		} catch (Exception e) { 
			retrunStr = "fail";
			e.printStackTrace();
		} finally {
			out.print(retrunStr);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 验证按钮就选择关联信息
	 */
	public void searchRoleBtn() {
		String roleId = getRequest().getParameter("roleId");
//		String nodeIds = getRequest().getParameter("nodeIds");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleId", roleId);
		List<BtnOfRole> list = this.roleManageService.getBtnOfRole(map);
		String btn_ids = "";
		if(list!=null&&list.size()>0){
			btn_ids=list.get(0).getBtn_ids();
		}
		String return_btn = ""; 
		//获取按钮字典表所有的按钮数据
		List<BtnDictionary> btn_list = roleService.findBtnList(map,null,null);
		for(int i=0;i<btn_list.size();i++){
			String btn_id = btn_list.get(i).getId();
			String btn_name = btn_list.get(i).getBtn_name();
			if(btn_ids!=null&&!"".equals(btn_ids)){
				if(btn_ids.indexOf(btn_id)!=-1){
					return_btn+="<p style='padding: 20px;'><input type='checkbox' name='item' class='check_view_state' style='display: none;' id='"+btn_id+"' value='"+btn_id+"' checked='checked'><label for='"+btn_id+"'></label>&nbsp;"+btn_name+"</p>";
				}else{
					return_btn+="<p style='padding: 20px;'><input type='checkbox' name='item' class='check_view_state' style='display: none;' id='"+btn_id+"' value='"+btn_id+"' ><label for='"+btn_id+"'></label>&nbsp;"+btn_name+"</p>";
				}
			}else{
				return_btn+="<p style='padding: 20px;'><input type='checkbox' name='item' class='check_view_state' style='display: none;' id='"+btn_id+"' value='"+btn_id+"' ><label for='"+btn_id+"'></label>&nbsp;"+btn_name+"</p>";
			}
		}
		
		PrintWriter out = null;
//		JSONObject jsonObject = JSONObject.fromObject(btn_ids);
		String retrunStr = "";
		try {
			out = getResponse().getWriter();
//			retrunStr = btn_ids;
			retrunStr = return_btn;//返回拼接好的option
		} catch (Exception e) { 
			retrunStr = "fail";
			e.printStackTrace();
		} finally {
			out.print(retrunStr);
			out.flush();
			out.close();
		}
	}
	/**
	 * 保存按钮
	 */
	public void saveRoleBtn() {
		String roleId = getRequest().getParameter("roleId");
		String btn_ids = getRequest().getParameter("btn_ids");
/*		String menuFlag = getRequest().getParameter("menuFlag");*/
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleId", roleId);
		List<BtnOfRole> list = this.roleManageService.getBtnOfRole(map);
		BtnOfRole tnr = new BtnOfRole();
		if(list!=null&&list.size()>0){
			tnr=list.get(0);
			tnr.setBtn_ids(btn_ids);
		}else{
			tnr.setRoleId(roleId);
			tnr.setBtn_ids(btn_ids);
		}
		PrintWriter out = null;
		String retrunStr = "";
		try {
			this.roleManageService.updateBtnOfRole(tnr);
			out = getResponse().getWriter();
			retrunStr = "success";
		} catch (Exception e) { 
			retrunStr = "fail";
			e.printStackTrace();
		} finally {
			out.print(retrunStr);
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 检查角色是否已经分配目录树权限
	 */
	public void checkRoleNodes() {
		JSONObject obj = new JSONObject();
		String roleId = getRequest().getParameter("roleId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleId", roleId);
		List<TreeNodeOfRole> list = this.roleManageService.getTreeNodesOfRole(map);
		if(list!=null&&list.size()>0){
			obj.put("flag", "success");
		}else{
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	
	/**
	 * 跳转到角色数据授权页面 
	 * @return
	 */
	public String toRoleDataPage() {
		Map<String,String> map = new HashMap<String,String>();
		String roleId = getRequest().getParameter("roleId");
		String treeNodes="";
		map.put("roleId", roleId);
		List<TreeNodeOfRole> list = this.roleManageService.getTreeNodesOfRole(map);
		if(list!=null&&list.size()>0){
			treeNodes=list.get(0).getTreeNodes();
		}
	
		//获得目录树
		map.put("business", "35547CE2-A572-4CDF-83D0-48B404164C59");//需要管理权限的业务数（利用模块也是档案管理模块的业务id）
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
			String tagid = String.valueOf(e.getId());
			if(treeNodes.indexOf(tagid)<0) {
				continue;
			}
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"basicData\":").append(idStructure).append("");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("dataJson", json.toString());  
		getRequest().setAttribute("roleId", roleId);
		return "roleDataJsp";
	}
	
	/**
	 * 跳转到角色案卷分类分配权限页面
	 * @return
	 */
	public String toRoleDataTabPage() {
		String roleId = getRequest().getParameter("roleId");
		String structureId = getRequest().getParameter("structureId");
		String treeId = getRequest().getParameter("treeId");
		List<Map<String,String>> list = this.roleManageService.getTreeNodesSorts(structureId,treeId);
		getRequest().setAttribute("list", list);  
		getRequest().setAttribute("roleId", roleId);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("treeId", treeId);
		return "roleDataTabJsp";
	}
	
	/**
	 * 获取角色数据权限
	 */
	public void getRoleDataRange() {
		JSONObject obj = new JSONObject();
		String roleId = getRequest().getParameter("roleId");
		String treeNode = getRequest().getParameter("treeNode");
		String dataIdChild = getRequest().getParameter("dataIdChild");
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		map.put("roleId", roleId);
		map.put("treeNode", treeNode);
		map.put("dataIdChild", dataIdChild);
		map.put("id", id);
		//CasualUserDataRange cur = this.roleManageService.getCasualUserDataRange(map);
		TreeDataOfRole rdr = this.roleManageService.getRoleDataRange(map);
		if(rdr == null) {
			obj.put("flag", "no");
		} else {
			obj.put("flag", "success"); 
			obj.put("data", rdr);
		}
		toPage(obj.toString());
	}
	
	/**
	 * 检查档案中的类别是否已经分配了权限范围
	 */
	public void checkRoleAllotData() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String idchild = getRequest().getParameter("idchild");
		String roleId = getRequest().getParameter("roleId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("treenode", structureId);
		map.put("idchild", idchild);
		map.put("roleId", roleId);
		boolean flag = this.roleManageService.checkRoleAllot(map);
		if(flag) {
			obj.put("have", "have");
		} else {
			obj.put("have", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除角色数据权限
	 */
	public void delRoleDataRange() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		boolean flag = this.roleManageService.deleteRoleDataRange(id);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到设置角色节点查询范围页面(增加修改)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String toSetRoleDataRange(){
		String addoredit = getRequest().getParameter("aeFlag");
		String structureId = getRequest().getParameter("structureId");
		String idchild = getRequest().getParameter("idchild");
		String roleId = getRequest().getParameter("roleId");
		String dataFabric = getRequest().getParameter("title");
		try {
			dataFabric = URLDecoder.decode(dataFabric,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String id = getRequest().getParameter("id");
		if("edit".equals(addoredit)) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", id);
			TreeDataOfRole cur = this.roleManageService.getRoleDataRange(map);
			String condition = cur.getCondition();
			JSONObject json =JSONObject.fromObject(condition);
			JSONArray jsonArray = JSONArray.fromObject(json.getString("cds"));
			getRequest().setAttribute("jsonArray", jsonArray);
			getRequest().setAttribute("id", id);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", idchild);
		map.put("tableFlag", "adSearch");
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		List<EssZDXZCommon> adSearchList = this.dataTempService.getZDXZDataList(map);
		List<EssTag> tags = new ArrayList<EssTag>();
		StringBuffer tagstr = new StringBuffer();
		for(int i = 0; adSearchList!=null&&i < adSearchList.size();i++){
			int tagId = adSearchList.get(i).getIdtag();
			for(int j = 0; j < etList.size()&&etList!=null;j++){
				int tagid = etList.get(j).getId();
				if(tagid == tagId){
					tags.add(etList.get(j));
					tagstr.append(etList.get(j).getId()+","+etList.get(j).getEsIdentifier()+";");
				}
			}
		}
		
		getRequest().setAttribute("id", id);
		getRequest().setAttribute("roleId", roleId);
		getRequest().setAttribute("idchild", idchild);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("dataFabric", dataFabric);
		getRequest().setAttribute("aeFlag", addoredit);
		getRequest().setAttribute("tags", tags);
		getRequest().setAttribute("tagstr", tagstr.toString());
		return "setRoleDataRangeJsp";
	}
	
	/**
	 * 新增或修改临时用户权限范围
	 */
	public void saveRoleDataRange() {
		JSONObject obj = new JSONObject();
		String roleId = getRequest().getParameter("roleId");
		String treeNode = getRequest().getParameter("treeNode");
		String dataIdChild = getRequest().getParameter("dataIdChild");
		String dataFabric = getRequest().getParameter("dataFabric");
		String condition = getRequest().getParameter("condition");
		String conditionShow = getRequest().getParameter("conditionShow");
		String sqlCondition = getRequest().getParameter("sqlCondition");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", dataIdChild);
		map.put("sqlCondition", sqlCondition);
		boolean check = this.roleManageService.checkSqlRight(map);
		if(!check) {
			obj.put("flag", "cannot");
			toPage(obj.toString());
			return;
		}
		String id = getRequest().getParameter("id");
		TreeDataOfRole cur = new TreeDataOfRole();
		cur.setRoleId(roleId);
		cur.setTreeNode(treeNode);
		cur.setDataIdChild(dataIdChild);
		cur.setDataFabric(dataFabric);
		cur.setCondition(condition);
		cur.setConditionShow(conditionShow);
		cur.setSqlCondition(sqlCondition);
		if(null == id || "".equals(id)) {
			cur.setId("newAdd");
		} else {
			cur.setId(id);
		}
		boolean flag = this.roleManageService.saveRoleDataRange(cur);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	   * 数据授权页面
	  **/
	public String toDataAuthorizeJsp() {
		 Map<String,String> map = new HashMap<String,String>();
		 int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		 int count = 0;
		 Paging.setPagingParams(getRequest(), pageSize, count);
		
		 //List<Basicdata> dataList =  roleManageService.queryDataList(map,Paging.pageIndex,Paging.pageSize);
		 List<Basicdata> roleList =new ArrayList<Basicdata>();
		 getRequest().setAttribute("roleList", roleList);  
		
		 return "toDataAuthorizeJsp";
	} 
	
	 /**
	   * 获取档案数据  树结构
	  **/
	public String showDirectories(){
		Map<String,String> map = new HashMap<String,String>();
		String roleIds = getRequest().getParameter("roleIds");
		String treeNodes="";
		if(roleIds.indexOf(",")>-1){//多个角色同时授权
			
		}else{//一个角色授权
			map.put("roleId", roleIds);
			List<TreeNodeOfRole> list = roleManageService.getTreeNodesOfRole(map);
			if(list!=null&&list.size()>0){
				treeNodes=list.get(0).getTreeNodes();
			}
		}
		
		
		map.clear();
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		//获得目录树
		map.put("business", "1");//需要管理权限的业务数（利用模块也是档案管理模块的业务id）
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		//json.append("{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": [");
		json.append("[");
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			if(treeNodes!=""&&(treeNodes+",").indexOf(e.getId()+",")>-1){
				json.append("\"checkArr\":\"").append("1\",");
			}else{
				json.append("\"checkArr\":\"").append("0\",");
			}
			json.append("\"basicData\":").append(e.getId()).append("");
			//json.append("\"basicData\":{\"nodeType\":\"").append(e.getNodeType()).append("\"}");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("dataJson", json.toString());  
		getRequest().setAttribute("roleIds", roleIds);  
		return "showDirectoriesJsp";
	}
	/**
	 * 跳转到角色管理页面
	 * @return
	 */
	public String toRoleManagePage() {
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = this.roleManageService.getManageListCount();
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<TrueArchiveRole> list = this.roleManageService.getRoleManageList(Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "roleManageJsp";
	}
	
	public void authorize(){
		PrintWriter out = null;
		Map<String,String> map = new HashMap<String,String>();
		String roleIds = getRequest().getParameter("roleIds");
		String treeNodes = getRequest().getParameter("treeNodes");
		try {
			String[] rolearr =roleIds.split(",");
			for(int i=0;rolearr.length>0&&i<rolearr.length;i++){
				map.clear();
				map.put("roleId", rolearr[i]);
				List<TreeNodeOfRole> list = roleManageService.getTreeNodesOfRole(map);
				TreeNodeOfRole sr=null;
				if(list==null||list.size()==0){
					sr = new TreeNodeOfRole();
					sr.setRoleId(rolearr[i]);
				}else{
					sr = list.get(0);
				}
				sr.setTreeNodes(treeNodes);
				roleManageService.updateTreeNodeOfRole(sr);
			}
			out = getResponse().getWriter();
			out.print("success");
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
			out.print("fail");
			out.flush();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}
	
	public void outWirter(String data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin","*");  
		response.setHeader("Access-Control-Allow-Credentials","true");  
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}
	/**
	 * 跳转到新增角色页面
	 * @return
	 */
	public String toRoleManageAddPage() {
		return "roleManageAddJsp";
	}
	
	/**
	 * 新增角色
	 */
	public void addRole() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String name = getRequest().getParameter("name");
			String describe = getRequest().getParameter("describe");
			String index = getRequest().getParameter("index");
			Date now = new Date();
			String fillTime = ymdhms.format(now);
			TrueArchiveRole acRole = new TrueArchiveRole();
			acRole.setCreateTime(fillTime);
			acRole.setName(name);
			acRole.setRoleDescibe(describe);
			acRole.setRoleIndex(index);
			boolean flag = this.roleManageService.addRole(acRole);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 跳转到角色信息更新页面
	 * @return
	 */
	public String toUpdateRole() {
		String id = getRequest().getParameter("id");
		TrueArchiveRole acRole = this.roleManageService.getOneAcRole(id);
		getRequest().setAttribute("roleid", acRole.getId());
		getRequest().setAttribute("name", acRole.getName());
		getRequest().setAttribute("roledescibe", acRole.getRoleDescibe());
		getRequest().setAttribute("roleindex", acRole.getRoleIndex());
		return "roleManageUpdateJsp";
	}
	
	/**
	 * 修改角色信息
	 */
	public void updateRole() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String id = getRequest().getParameter("id");
			String name = getRequest().getParameter("name");
			String describe = getRequest().getParameter("describe");
			String index = getRequest().getParameter("index");
			Date now = new Date();
			String fillTime = ymdhms.format(now);
			TrueArchiveRole acRole = new TrueArchiveRole();
			acRole.setId(id);
			acRole.setChangeTime(fillTime);
			acRole.setName(name);
			acRole.setRoleDescibe(describe);
			acRole.setRoleIndex(index);
			boolean flag = this.roleManageService.updateRole(acRole);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 删除角色信息
	 */
	public void delRole() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String ids = getRequest().getParameter("ids");
			boolean flag = this.roleManageService.delRole(ids);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * (@*@)跳转到设置角色菜单页面
	 */
	public String toSetRoleMenu() {
		String roleId = getRequest().getParameter("roleId");
		List<Menu> list = this.roleManageService.getRoleMenuData(roleId);
		List<String> slist = this.roleManageService.getRoleMenuId(roleId);
		String num = "";
		if(slist != null && !slist.isEmpty()) {
			num = String.valueOf(slist.size());
		} else {
			num = "0";
		}
		getRequest().setAttribute("menuList", list);
		getRequest().setAttribute("checkNum", num);
		getRequest().setAttribute("roleId", roleId);
		return "setRoleMenuJsp";
	}
	
	/**
	 * 设置角色对应的菜单
	 */
	public void setRoleMenuList() {
	    PrintWriter out = null;
		String retrunStr = "";
		try {
			String roleId = getRequest().getParameter("roleId");
			String menuIdStr = getRequest().getParameter("menuIdStr");
			boolean flag = this.roleManageService.setRoleMenuList(roleId,menuIdStr);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 跳转到角色人员配置页面
	 * @return
	 */
	public String toSetRoleEmployee() {
		String roleId = getRequest().getParameter("roleId");
		//已选中人员
		List<String> slist = this.roleManageService.getCheckRoleEmployeeList(roleId);
		String str = "";
		if(slist != null && !slist.isEmpty()) {
			for(int t = 0; t < slist.size(); t++) {
				str += slist.get(t)+",";
			}
		}
		//如东县档案局部门
		List<Department> dList = this.roleManageService.getEmployeeDepartmentList(roleId);
		StringBuilder json = new StringBuilder();
		StringBuilder eJson = new StringBuilder();
		json.append("[");
		for(Department de : dList) {
			eJson.append("'"+ de.getDepartmentGuid()+"',");
			json.append("{");
			json.append("\"id\":\"").append(de.getDepartmentGuid()).append("\",");
			json.append("\"title\":\"").append(de.getDepartmentName()).append("\",");
			json.append("\"parentId\":\"").append(de.getSuperiorGuid()).append("\",");
            boolean flag = this.roleManageService.checkDepartmentIsCheck(de.getDepartmentGuid(),roleId);
			if(flag) {
				json.append("\"checkArr\":\"").append("1\"");
			} else {
				json.append("\"checkArr\":\"").append("0\"");
			}
			json.append("},");
		}
		int eindex = eJson.lastIndexOf(",");
		if(eindex>-1){
			eJson.deleteCharAt(eindex);
		}
		String deGuids = eJson.toString();
		//已剔除其他角色对应的人员，选中人员已做yes标识
		List<Employee> list = this.roleManageService.getRoleEmployeeList(roleId,deGuids);
		for(Employee ee : list) {
			json.append("{");
			json.append("\"id\":\"").append(ee.getEmployeeGuid()).append("\",");
			json.append("\"title\":\"").append(ee.getEmployeeName()).append("\",");
			json.append("\"parentId\":\"").append(ee.getDepartmentGuid()).append("\",");
			String isCheck = ee.getIsCheck();
			if("yes".equals(isCheck)) {
				json.append("\"checkArr\":\"").append("1\",");
			} else {
				json.append("\"checkArr\":\"").append("0\",");
			}
			json.append("\"basicData\":\"").append("last\"");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("dataJson", json.toString());  
		getRequest().setAttribute("checked", str);
		getRequest().setAttribute("roleId", roleId);
		return "setRoleEmployeeJsp";
	}
	
	public void setRoleEmployeeAbout() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String roleId = getRequest().getParameter("roleId");
			String eyIdStr = getRequest().getParameter("eyIdStr");
			boolean flag = this.roleManageService.setRoleEmployeeList(roleId,eyIdStr);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 跳转到临时用户管理页面
	 * @return
	 */
	public String toCasualUserManage() {
		Map<String,String> map = new HashMap<String, String>();
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = this.roleManageService.getCasualUserCount();
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<CasualUser> list = this.roleManageService.getCasualUserList(map,Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "casualUserManageJsp";
	}
	
	/**
	 * 跳转到临时用户新增页面
	 * @return
	 */
	public String toCausualUserAdd(){
		String casualId = this.roleManageService.addCasualUserStart();
		getRequest().setAttribute("casualId", casualId);
		return "casualUserManageAddJsp";
	} 
	
	/**
	 * 删除临时用户信息
	 */
	public void delCausualUser() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String ids = getRequest().getParameter("ids");
			boolean flag = this.roleManageService.deleteCasualUser(ids);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 保存临时用户信息
	 */
	public void saveCasualUser() {
		String id = getRequest().getParameter("id");
		String casualName = getRequest().getParameter("name");
		String casualPassword = getRequest().getParameter("password");
		String casualStartTime = getRequest().getParameter("startTime");
		String casualEndTime = getRequest().getParameter("endTime");
		String describe = getRequest().getParameter("describe");
		String index = getRequest().getParameter("index");
		String menuRight = getRequest().getParameter("menuId");
		String menuFlag = getRequest().getParameter("menuFlag");
		CasualUser cUser = new CasualUser();
		cUser.setId(id);
		cUser.setCasualName(casualName);
		cUser.setCasualPassword(casualPassword);
		cUser.setCasualStartTime(casualStartTime);
		cUser.setCasualEndTime(casualEndTime);
		cUser.setDescribe(describe);
		cUser.setIndex(index);
		cUser.setMenuRight(menuRight);
		PrintWriter out = null;
		String retrunStr = "";
		try {
			boolean flag = this.roleManageService.updateCasualUser(cUser,menuFlag);
			if(flag) {
				retrunStr = "success";
			} else {
				retrunStr = "fail";
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 跳转到更新页面
	 */
	public String toCasualUpdatePage() {
		String id = getRequest().getParameter("id");
		List<CasualUser> list = this.roleManageService.getTreeNodesOfCasualUser(id);
		if(list != null && list.size()>0) {
			CasualUser cUser = list.get(0);
			getRequest().setAttribute("id",id);
			getRequest().setAttribute("name", cUser.getCasualName());
			getRequest().setAttribute("password", cUser.getCasualPassword());
			getRequest().setAttribute("describe", cUser.getDescribe());
			getRequest().setAttribute("index", cUser.getIndex());
			return "casualUserManageUpdateJsp";
		} else {
			return "casualUserManageJsp";
		}
	}
	
	/**
	 * 检查临时用户是否已经分配目录树权限
	 */
	public void checkCasualUserTree() {
		JSONObject obj = new JSONObject();
		String casualId = getRequest().getParameter("casualId");
		Set<String> checkMenu = this.roleManageService.getCasualCheckNodes(casualId);
		if(checkMenu != null && !checkMenu.isEmpty()) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到临时用户授权页面 
	 * @return
	 */
	public String toCasualUserDataPage() {
		String casualId = getRequest().getParameter("casualId");
		Set<String> checkMenu = this.roleManageService.getCasualCheckNodes(casualId);
		Map<String,String> map = new HashMap<String,String>();
		//获得目录树
		map.put("business", "1");//需要管理权限的业务数（利用模块也是档案管理模块的业务id）
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		//List<EssTree> data = roleManageService.getModelTreeByMap(map);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
			String id = String.valueOf(e.getId());
			if(!checkMenu.contains(id)) {
				continue;
			}
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"basicData\":").append(idStructure).append("");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("dataJson", json.toString());  
		getRequest().setAttribute("casualId", casualId);
		return "casualUserDataJsp";
	}
	
	/**
	 * 跳转到临时用户案卷分类分配权限页面
	 * @return
	 */
	public String toCasualUserDataTabPage() {
		String casualId = getRequest().getParameter("casualId");
		String structureId = getRequest().getParameter("structureId");
		String treeId = getRequest().getParameter("treeId");
		List<Map<String,String>> list = this.roleManageService.getTreeNodesSorts(structureId,treeId);
		getRequest().setAttribute("list", list);  
		getRequest().setAttribute("casualId", casualId);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("treeId", treeId);
		return "casualUserDataTabJsp";
	}
	
	/**
	 * 获取临时用户数据权限
	 */
	public void getCasualUserDataRange() {
		JSONObject obj = new JSONObject();
		String casualId = getRequest().getParameter("casualId");
		String treeNode = getRequest().getParameter("treeNode");
		String dataIdChild = getRequest().getParameter("dataIdChild");
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		map.put("casualId", casualId);
		map.put("treeNode", treeNode);
		map.put("dataIdChild", dataIdChild);
		map.put("id", id);
		CasualUserDataRange cur = this.roleManageService.getCasualUserDataRange(map);
		if(cur == null) {
			obj.put("flag", "no");
		} else {
			obj.put("flag", "success"); 
			obj.put("data", cur);
		}
		toPage(obj.toString());
	}
	
	/**
	 * 检查档案中的类别是否已经分配了权限范围
	 */
	public void checkCasualUserAllotData() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String idchild = getRequest().getParameter("idchild");
		String casualId = getRequest().getParameter("casualId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("treenode", structureId);
		map.put("idchild", idchild);
		map.put("casualId", casualId);
		boolean flag = this.roleManageService.checkCasualUserAllot(map);
		if(flag) {
			obj.put("have", "have");
		} else {
			obj.put("have", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除临时用户数据权限
	 */
	public void delCausualUserDataRange() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		boolean flag = this.roleManageService.deleteCasualuserDataRange(id);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到设置临时用户节点查询范围页面(增加修改)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String toSetCasualUserDataRange() throws UnsupportedEncodingException {
		String addoredit = getRequest().getParameter("aeFlag");
		String structureId = getRequest().getParameter("structureId");
		String idchild = getRequest().getParameter("idchild");
		String casualId = getRequest().getParameter("casualId");
		String dataFabric = getRequest().getParameter("title");
		dataFabric = URLDecoder.decode(dataFabric,"UTF-8");
		String id = getRequest().getParameter("id");
		if("edit".equals(addoredit)) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", id);
			CasualUserDataRange cur = this.roleManageService.getCasualUserDataRange(map);
			String condition = cur.getCondition();
			JSONObject json =JSONObject.fromObject(condition);
			JSONArray jsonArray = JSONArray.fromObject(json.getString("cds"));
			getRequest().setAttribute("jsonArray", jsonArray);
			getRequest().setAttribute("id", id);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", idchild);
		//List<EssTag> tags = essModelService.queryTagsForShow(map);
		getRequest().setAttribute("casualId", casualId);
		getRequest().setAttribute("idchild", idchild);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("dataFabric", dataFabric);
		getRequest().setAttribute("aeFlag", addoredit);
		//getRequest().setAttribute("tags", tags);
		return "setCUserDataRangeJsp";
	}
	
	/**
	 * 新增或修改临时用户权限范围
	 */
	public void saveCasualUserDataRange() {
		JSONObject obj = new JSONObject();
		String casualId = getRequest().getParameter("casualId");
		String treeNode = getRequest().getParameter("treeNode");
		String dataIdChild = getRequest().getParameter("dataIdChild");
		String dataFabric = getRequest().getParameter("dataFabric");
		String condition = getRequest().getParameter("condition");
		String conditionShow = getRequest().getParameter("conditionShow");
		String sqlCondition = getRequest().getParameter("sqlCondition");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", dataIdChild);
		map.put("sqlCondition", sqlCondition);
		boolean check = this.roleManageService.checkSqlRight(map);
		if(!check) {
			obj.put("flag", "cannot");
			toPage(obj.toString());
			return;
		}
		String id = getRequest().getParameter("id");
		CasualUserDataRange cur = new CasualUserDataRange();
		cur.setCasualId(casualId);
		cur.setTreeNode(treeNode);
		cur.setDataIdChild(dataIdChild);
		cur.setDataFabric(dataFabric);
		cur.setCondition(condition);
		cur.setConditionShow(conditionShow);
		cur.setSqlCondition(sqlCondition);
		if(null == id || "".equals(id)) {
			cur.setId("newAdd");
		} else {
			cur.setId(id);
		}
		boolean flag = this.roleManageService.saveCasualUserDataRange(cur);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
}