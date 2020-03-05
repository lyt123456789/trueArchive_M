package cn.com.trueway.sys.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.sys.pojo.MenuRoleShip;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.service.MenuRoleService;
import cn.com.trueway.sys.service.MenuService;
import cn.com.trueway.sys.service.RoleService;
import cn.com.trueway.workflow.core.pojo.Employee;
/**
 * 
 * 描述：菜单授权
 * 作者：蔡亚军
 * 创建时间：2016-3-15 上午11:50:31
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class MenuRoleAction extends BaseAction{

	private static final long serialVersionUID = 7070853694133588313L;
	
	private MenuService  menuService;
	
	private RoleService roleService;
	
	private MenuRoleService menuRoleService;
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public MenuRoleService getMenuRoleService() {
		return menuRoleService;
	}

	public void setMenuRoleService(MenuRoleService menuRoleService) {
		this.menuRoleService = menuRoleService;
	}

	/**
	 * 
	 * 描述：获取角色列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 上午11:54:16
	 */
	public String getRoleList(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		
		//2017-09-27,超管账户角色管理页面 
		Boolean t = false;
		/*if(siteId == null || "".equals(siteId)){
			t = true;
			List<String[]> sites = roleService.getSites();
			if(sites != null && sites.size()>0){
				siteId = sites.get(0)[0];
			}
			this.getRequest().setAttribute("sites", sites);
			this.getRequest().setAttribute("siteId", siteId);
		}*/
		
		String roleName = getRequest().getParameter("roleName");
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("roleName", roleName);
		map.put("siteId", siteId);
		List<Role> list = roleService.findRoleList(map, null, null);
		String roleId = "";
		if (null != list && list.size()>0) {
			roleId = list.get(0).getRoleId();
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("roleName", roleName);
		getRequest().setAttribute("firstRoleId", roleId);
		
		if(t){
			return "rolelist4Super";
		}
		
		return "roleList";
	}
	
	/**
	 * 
	 * 描述：获取菜单列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午01:45:14
	 */
	public String getMenuList(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		String menuId = getRequest().getParameter("menuId");
		String menuName = getRequest().getParameter("menuName");
		try {
			List<Menu> list = null;
			
			if (StringUtils.isNotBlank(menuName)) {
				//list = menuService.findMenuByName(menuName);
				list = menuService.findMenuByNameNew(menuName,siteId);
			}else{
				// 获取全部的menu
				list = menuService.findMenuListNew(menuId, siteId);
			}
			// 获取该id的全部权限
			String roleId = getRequest().getParameter("roleId");
			List<MenuRoleShip> roleMenuList = menuRoleService.findRoleMenuShipList(roleId);
			
			List<Map<String,Object>> outList = new ArrayList<Map<String,Object>>();
			for (Menu menu : list) {
				Map<String,Object> map = new HashMap<String, Object>();
				String isGrant = "0";//1:已授权,0:未授权
				for (MenuRoleShip menuRoleShip : roleMenuList) {
					if (menu.getMenuId().equals(menuRoleShip.getMenuId())) {
						isGrant = "1";
						break;
					}
				}
				map.put("isGrant", isGrant);
				map.put("menuId", menu.getMenuId());
				map.put("menuFaterId", menu.getMenuFaterId());
				map.put("menuName", menu.getMenuName());
				map.put("menuUrl", menu.getMenuUrl());
				map.put("menuType", menu.getMenuType());
				map.put("menuSort", menu.getMenuSort());
				map.put("menuStatus", menu.getMenuStatus());
				map.put("menuExtLinks", menu.getMenuExtLinks());
				map.put("menuSerial", menu.getMenuSerial());
				map.put("menuRank", menu.getMenuRank());
				map.put("menuSimpleName", menu.getMenuSimpleName());
				map.put("havaChild", menu.getHavaChild());
				map.put("menuPath", menu.getMenuPath());
				outList.add(map);
			}
			getRequest().setAttribute("list", outList);
			getRequest().setAttribute("menuName", menuName);
			getRequest().setAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "menuList";
	}
	
	/**
	 * 
	 * 描述：菜单角色授权
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午04:11:15
	 */
	public void saveMenuRole(){
		String roleId = getRequest().getParameter("roleId");
		String menuId = getRequest().getParameter("menuId");
		MenuRoleShip menuRoleShip = new MenuRoleShip();
		menuRoleShip.setRoleId(roleId);
		menuRoleShip.setMenuId(menuId);
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		JSONObject error = null;
		error = menuRoleService.saveMenuRole(menuRoleShip, emp);
		toPage(error.toString());
	}
	
	/**
	 * 
	 * 描述：一键授权
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午04:21:48
	 */
	public void saveAllMenuRole(){
		String roleId = getRequest().getParameter("roleId");
		JSONObject error = null;
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		error = menuRoleService.saveAllMenuRole(roleId, emp);
		toPage(error.toString());
	}
	
	/** 
	 * removeMenuRole:(取消角色菜单授权). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void removeMenuRole(){
		String roleId = getRequest().getParameter("roleId");
		String menuId = getRequest().getParameter("menuId");
		JSONObject error = null;
		error = menuRoleService.deleteMenuRole(roleId, menuId);
		toPage(error.toString());
	}
	
	/** 
	 * removeAllMenuRole:(一键取消菜单授权). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void removeAllMenuRole(){
		String roleId = getRequest().getParameter("roleId");
		JSONObject error = null;
		//error = menuRoleService.deleteAllMenuRole(roleId);
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		error = menuRoleService.deleteAllMenuRoleNew(roleId,emp);
		toPage(error.toString());
	}
	
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
				sb.append("<tr onclick=\"impowerMenu('"+r.getRoleId()+"');\" id=\""+r.getRoleId()+"\">");
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
				sb.append("</tr>");
				
			}
			result.put("tbody", sb.toString());
			String roleId = list.get(0).getRoleId();
			result.put("firstRoleId", roleId);
			
		}
		result.put("siteId", siteId);
		toPage(result.toString());
	}
}
