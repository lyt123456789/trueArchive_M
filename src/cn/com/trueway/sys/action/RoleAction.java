package cn.com.trueway.sys.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.vo.RoleView;
import cn.com.trueway.sys.service.RoleService;
import cn.com.trueway.workflow.core.pojo.Employee;

/**
 * 
 * 描述：角色模块管理
 * 作者：蔡亚军
 * 创建时间：2016-3-14 下午04:55:58
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class RoleAction extends BaseAction{

	private static final long serialVersionUID = -7935780779799590303L;
	
	private RoleService  roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
		
	public String getRoleList(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		
		//2017-09-27,超管账户角色管理页面 
		/*Boolean t = false;
		if(siteId == null || "".equals(siteId)){
			t = true;
			List<String[]> sites = roleService.getSites();
			if(sites != null && sites.size()>0){
				siteId = sites.get(0)[0];
			}
			this.getRequest().setAttribute("sites", sites);
			this.getRequest().setAttribute("siteId", siteId);
		}*/
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("siteId", siteId);
		List<Role> list = roleService.findRoleList(map, null, null);
		String roleId = "";
		if (null != list && list.size()>0) {
			roleId = list.get(0).getRoleId();
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("firstRoleId", roleId);
		
		/*if(t){
			return "rolelist4Super";
		}*/
		
		return "rolelist";
	}
	
	
	public String getRoleList4Select(){
		List<RoleView> list = roleService.findRoleViewList(null, null, null);  //将Role换为了RoleView，其中包含了nj_site的字段
		String roleId = "";
		if (null != list && list.size()>0) {
			roleId = list.get(0).getRoleId();
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("firstRoleId", roleId);
		return "roleList4Select";
	}
	
	public String toAdd(){
		//2017-09-27,超级管理员传入siteId---------------------------
		String siteId = getRequest().getParameter("siteId");
		getRequest().setAttribute("siteId", siteId);
		//-----------------------------------------------------
		
		return "addrole";
	}
	
	/**
	 * 
	 * 描述：新增保存角色信息
	 * 作者:蔡亚军
	 * 创建时间:2016-3-14 下午06:02:03
	 */
	public void saveRole(){
		//2017-09-27,超级管理员传入siteId---------------------------
		String siteId = getRequest().getParameter("siteId");
		if(siteId == null || "".equals(siteId)){
			//非超管无siteId传入，直接获取
			siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		}
		//String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		String roleName = getRequest().getParameter("roleName");		//角色名称
		String roleSort = getRequest().getParameter("roleSort");		//角色排序号
		if (StringUtils.isBlank(roleSort)) {
			roleSort = "1";
		}
		JSONObject error = null;
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRoleSort(new BigDecimal(roleSort));
		role.setRoleStatus(new BigDecimal("1"));
		role.setSiteId(siteId);
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		error = roleService.saveRole(role, emp);
		toPage(error.toString());
	}
	
	/** 
	 * deleteRole:(删除角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteRole(){
		String roleId = getRequest().getParameter("roleId");		//角色名称
		//String error = roleService.deleteRole(roleId, null);
		String error = roleService.deleteRoleNew(roleId, null);
		toPage(error);
	}
	
	/** 
	 * toUpdateRole:(更新角色初始化). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public String toUpdateRole(){
		String roleId = getRequest().getParameter("roleId");
		Role role = roleService.findRoleById(roleId);
		getRequest().setAttribute("role", role);
		
		//2017-09-28,超级管理员传入siteId---------------------------
		String siteId = getRequest().getParameter("siteId");
		getRequest().setAttribute("siteId", siteId);
		//----------------------------------------------------
		
		return "updaterole";
	}
	
	/** 
	 * updateRole:(更新角色). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void updateRole(){
		//2017-09-28,超级管理员传入siteId---------------------------
		String siteId = getRequest().getParameter("siteId");
		if(siteId == null || "".equals(siteId)){
			//非超管无siteId传入，直接获取
			siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		}
		
		//String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		String roleId = getRequest().getParameter("roleId");
		String roleName = getRequest().getParameter("roleName");		//角色名称
		String roleSort = getRequest().getParameter("roleSort");		//角色排序号
		String roleStatus = getRequest().getParameter("roleStatus");
		if (StringUtils.isBlank(roleSort)) {
			roleSort = "1";
		}
		Role role = new Role();
		JSONObject error = null;
		if (StringUtils.isNotBlank(roleStatus)) {
			role = roleService.findRoleById(roleId);
			role.setRoleStatus(new BigDecimal(roleStatus));
		}else{
			role.setRoleId(roleId);
			role.setRoleName(roleName);
			role.setRoleSort(new BigDecimal(roleSort));
			role.setRoleStatus(new BigDecimal("1"));
			role.setSiteId(siteId);
		}
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		error = roleService.updateRole(role, emp);
		toPage(error.toString());
	}
	
	public String getRole4EverSite(){
		this.getRequest().setAttribute("sites", roleService.getSites());
		return "getRole4EverSite";
	}
	
	public void getRoles(){
		String siteId = this.getRequest().getParameter("siteId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("siteId", siteId);
		List<Role> list = roleService.findRoleList(map, null, null);
		JSONArray ja = new JSONArray();
		if(!list.isEmpty()){
			for(Role r: list){
				JSONObject jo = new JSONObject();
				jo.put("roleId", r.getRoleId());
				jo.put("roleStatus", r.getRoleStatus());
				jo.put("roleName", r.getRoleName());
				jo.put("roleSort", r.getRoleSort());
				ja.add(jo);
			}
		}
		toPage(ja.toString());
	}
	
	/**
	 * 超级管理员，切换站点查看角色
	 * 
	 *@author  wangl
	 *@date    2017年9月27日 下午7:29:25 
	 *@version 1.0      void
	 */
	public void changeRoleList(){
		String siteId = this.getRequest().getParameter("siteId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("siteId", siteId);
		List<Role> list = roleService.findRoleList(map, null, null);
		JSONObject result = new JSONObject();
		StringBuffer sb = new StringBuffer();
		if(!list.isEmpty()){
			int i = 0;
			for(Role r: list){
				i++;
				sb.append("<tr onclick=\"changeBusMod('"+r.getRoleId()+"','"+r.getRoleStatus()+"');\" id=\""+r.getRoleId()+"\">");
				sb.append("<td align=\"center\" id=\""+r.getRoleId()+"\" class=\"item\" >"+i+"</td>");
				sb.append("<td >"+r.getRoleName()+"</td>");
				BigDecimal roleStatus = r.getRoleStatus();
				if(roleStatus != null){
					if(roleStatus.intValue() == 1){
						sb.append("<td align=\"center\"><font style=\"color: green;\">启用</font></td>");
					}
					if(roleStatus.intValue() == 0){
						sb.append("<td align=\"center\"><font style=\"color: red;\">禁用</font></td>");
					}
				}else{
					sb.append("<td align=\"center\">暂无数据</td>");
				}
				sb.append("<td align=\"center\">"+r.getRoleSort()+"</td>");
				sb.append("</tr>");
				
			}
			result.put("tbody", sb.toString());
			String roleId = list.get(0).getRoleId();
			result.put("firstRoleId", roleId);
			
		}
		result.put("siteId", siteId);
		toPage(result.toString());
	}
	
	//账号密码修改页面
	public String toEditPassWord(){
		//取用户session中的信息
				String yhm = "",jh="",dwbm="",jyxm="",dwmc="",zwdm="",dutytype="",tipstr="";
				Employee publisher = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				getRequest().setAttribute("jyxm", jyxm);
				String password="";
				if(getRequest().getParameter("password")!=null&& !"".equals(getRequest().getParameter("password"))){
					password=getRequest().getParameter("password");
				}	
		
		return "toEditPassWord";
	}
	
	//修改账户密码保存
	public void updateUserPassword(){
		//取用户session中的信息
		String yhm = "",jh="",dwbm="",jyxm="",dwmc="",zwdm="",dutytype="",tipstr="";
		Employee publisher = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
	
		String password="";
		if(getRequest().getParameter("password")!=null&& !"".equals(getRequest().getParameter("password"))){
			password=getRequest().getParameter("password");
		}
		
		Connection cn=null;
		Statement stmt=null;

		ResultSet rs=null;
		String sql_str="";
		try {
			stmt = cn.createStatement(); 
		    sql_str="UPDATE syhpt.T_YW_JY_JYXX SET YHMM='"+password+"' WHERE YHM='"+yhm+"'";
		    stmt.executeUpdate(sql_str);
		    sql_str="UPDATE ZWKJ_EMPLOYEE SET employee_password='"+password+"' WHERE employee_guid='"+yhm+"'";
		    stmt.executeUpdate(sql_str);
		}catch (Exception e) {
			toPage("error");
			e.printStackTrace();
			}
			finally{
				try{
					if(rs!=null)
						rs.close();
					if(stmt!=null)
						stmt.close();
					if(cn != null)
						cn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		
		toPage("success");
	}
}
