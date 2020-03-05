package cn.com.trueway.sys.action;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.sys.service.MenuService;
import cn.com.trueway.workflow.core.pojo.Employee;
/**
 * 
 * 描述：菜单管理
 * 作者：蔡亚军
 * 创建时间：2016-3-14 下午03:36:10
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class MenuAction extends BaseAction{

	private static final long serialVersionUID = 3115467302536800790L;
	
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	private File upImg;
	
	private String upImgFileName;
	
	private String upImgContentType;
	
	public String getUpImgFileName() {
		return upImgFileName;
	}

	public void setUpImgFileName(String upImgFileName) {
		this.upImgFileName = upImgFileName;
	}

	public String getUpImgContentType() {
		return upImgContentType;
	}

	public void setUpImgContentType(String upImgContentType) {
		this.upImgContentType = upImgContentType;
	}

	public File getUpImg() {
		return upImg;
	}

	public void setUpImg(File upImg) {
		this.upImg = upImg;
	}

	/**
	 * 
	 * 描述：获取菜单列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-14 下午03:40:44
	 */
	public String getMenuList(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		Employee employee = (Employee)getRequest().getSession().getAttribute(MyConstants.loginEmployee);
		try {
			String name = getRequest().getParameter("menuName");
			List<Menu> list = null;
			
			if (StringUtils.isNotBlank(name)) {
				list = menuService.findMenuByName(name);
			}else{
				list = menuService.findMenuList("",siteId);
			}
			List<Map<String, Object>> outList = new ArrayList<Map<String,Object>>();
			String isAdmin = employee.getIsAdmin();
			for (Menu menu : list) {
				Map<String, Object> map = new HashMap<String, Object>(14);
				map.put("isForbidenGrant", "0");
				map.put("menuId", menu.getMenuId());
				map.put("menuFaterId", menu.getMenuFaterId());
				map.put("menuName", menu.getMenuName());
				map.put("menuUrl", menu.getMenuUrl());
				map.put("menuType", menu.getMenuType());
				map.put("menuSort", menu.getMenuSort());
				map.put("menuStatus", menu.getMenuStatus());
				map.put("menuExtLinks", menu.getMenuExtLinks());
				String foreignAppAddress;
				if(CommonUtil.stringIsNULL(menu.getForeignAppAddress())){
					foreignAppAddress = "";
				}else{
					foreignAppAddress = menu.getForeignAppAddress();
					/*if(applicationService.findApplicationById(menu.getForeignAppAddress())!=null){
						foreignAppAddress = applicationService.findApplicationById(menu.getForeignAppAddress()).getAppUrl();
					}else{
						foreignAppAddress="";
					}*/
				}
				map.put("foreignAppAddress", foreignAppAddress);
				map.put("menuSerial", menu.getMenuSerial());
				map.put("menuRank", menu.getMenuRank());
				map.put("menuSimpleName", menu.getMenuSimpleName());
				map.put("havaChild", menu.getHavaChild());
				map.put("menuPath", menu.getMenuPath());
				/*if(!"1".equals(isAdmin)&&CommonUtil.stringIsNULL(menu.getSiteId())){
					map.put("disabled", true);
				}else{
					map.put("disabled", false);
				}*/
				map.put("disabled", false);
				outList.add(map);
			}
			getRequest().setAttribute("list", outList);
			getRequest().setAttribute("menuName", name);
			return "menuList";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 
	 * 描述：新增菜单
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午02:16:29
	 */
	public String toAdd(){
		String father_menuId = getRequest().getParameter("father_menuId");
		getRequest().setAttribute("menuFaterId", father_menuId);
		return "toAdd";
	}
	
	/**
	 * 
	 * 描述：保存菜单
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午02:25:31
	 */
	public void saveMenu(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		File upImg = this.getUpImg(); // 要上传的文件
		String menuPath = getRequest().getParameter("menuPath");
		if(upImg!=null){
			//String upImgFileName = this.getUpImgFileName();
			String upImgContentType = this.getUpImgContentType();
			Calendar calendar = Calendar.getInstance();
			String imgNewName = calendar.getTimeInMillis()+"."+upImgContentType.split("/")[1];
			menuPath = "/menuImage/"+imgNewName;
			File dstFile = new File(PathUtil.getWebRoot()+"menuImage/"+imgNewName);// 创建一个服务器上的目标路径文件对象
			FileUploadUtils.copy(upImg, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
		}
		
		String menuName = getRequest().getParameter("menuName");
		String menuSimpleName = getRequest().getParameter("menuSimpleName");
		String menuUrl = getRequest().getParameter("menuUrl");
		String menuExtLinks = getRequest().getParameter("menuExtLinks");
		String foriegnAppAddress = getRequest().getParameter("foriegnAppAddress");
		String menuType = getRequest().getParameter("menuType");
		String menuSort = getRequest().getParameter("menuSort");
		String menuRank = getRequest().getParameter("menuRank");
//		String menuStatus = getRequest().getParameter("menuStatus");
		String menuFaterId = getRequest().getParameter("menuFaterId");
		String countSql = getRequest().getParameter("countSql");//数目角标sql
		if("undefined".equals(menuFaterId)){
			menuFaterId = "";
		}
		if (StringUtils.isBlank(menuType)) {
			menuType = "1";
		}
		if (StringUtils.isBlank(menuSort)) {
			menuSort = "1";
		}
		if (StringUtils.isBlank(menuRank)) {
			menuRank = "0";
		}
		Menu menu = new Menu();
		menu.setMenuName(menuName);
		menu.setMenuSimpleName(menuSimpleName);
		menu.setMenuUrl(menuUrl);
		menu.setMenuPath(menuPath);
		menu.setMenuExtLinks(new BigDecimal(menuExtLinks));
		menu.setMenuType(new BigDecimal(menuType));
		menu.setMenuSort(new BigDecimal(menuSort));
		menu.setMenuRank(new BigDecimal(menuRank));
		menu.setMenuFaterId(menuFaterId);
		menu.setMenuStatus(new BigDecimal("1"));
		menu.setForeignAppAddress(foriegnAppAddress);
		menu.setCountSql(countSql);
		menu.setSiteId(siteId);
		JSONObject error = menuService.saveMenu(menu);
		toPage(error.toString());
	}
	
	/**
	 * 
	 * 描述: 跳转到修改页面
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午02:17:37
	 */
	public String toUpdate(){
		String menuId = getRequest().getParameter("menuId");
		Menu menu = menuService.findMenuById(menuId);
		getRequest().setAttribute("sysMenu", menu);
		return "toUpdate";
	}
	
	
	/**
	 * 
	 * 描述：修改菜单
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 下午02:24:15
	 */
	public void updateMenu(){
		String siteId = this.getRequest().getSession().getAttribute("siteId")==null?"":this.getRequest().getSession().getAttribute("siteId").toString();
		File upImg = this.getUpImg(); // 要上传的文件
		String menuPath = getRequest().getParameter("menuPath");
		if(upImg!=null){
			//String upImgFileName = this.getUpImgFileName();
			String upImgContentType = this.getUpImgContentType();
			Calendar calendar = Calendar.getInstance();
			String imgNewName = calendar.getTimeInMillis()+"."+upImgContentType.split("/")[1];
			menuPath = "/menuImage/"+imgNewName;
			File dstFile = new File(PathUtil.getWebRoot()+"menuImage/"+imgNewName);// 创建一个服务器上的目标路径文件对象
			FileUploadUtils.copy(upImg, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
		}
		String menuName = getRequest().getParameter("menuName");
		String menuSimpleName = getRequest().getParameter("menuSimpleName");
		String menuUrl = getRequest().getParameter("menuUrl");
		String menuExtLinks = getRequest().getParameter("menuExtLinks");
		String foriegnAppAddress = getRequest().getParameter("foriegnAppAddress");
		String menuType = getRequest().getParameter("menuType");
		String menuSort = getRequest().getParameter("menuSort");
		String menuId = getRequest().getParameter("menuId");
		String menuFaterId = getRequest().getParameter("menuFaterId");
		String menuStatus = getRequest().getParameter("menuStatus");
		String menuSerial = getRequest().getParameter("menuSerial");
		String menuRank= getRequest().getParameter("menuRank");
		String countSql = getRequest().getParameter("countSql");//数目角标sql
		if (StringUtils.isNotBlank(menuStatus)) {
			Menu menu = menuService.findMenuById(menuId);
			menu.setMenuStatus(new BigDecimal(menuStatus));
			JSONObject error = menuService.updateMenu(menu);
			toPage(error.toString());
		} else {
			Menu menu = menuService.findMenuById(menuId);
			//---------update之前先删除老的图片
			File oldImg = new File(PathUtil.getWebRoot()+menu.getMenuPath());
			if(oldImg.exists()){
				oldImg.delete();
			}
			menu.setMenuId(menuId);
			menu.setMenuName(menuName);
			menu.setMenuSimpleName(menuSimpleName);
			menu.setMenuUrl(menuUrl);
			menu.setMenuPath(menuPath);
			menu.setMenuExtLinks(new BigDecimal(menuExtLinks));
			menu.setMenuType(new BigDecimal(menuType));
			menu.setMenuSort(new BigDecimal(menuSort));
			menu.setMenuRank(new BigDecimal(menuRank));
			menu.setMenuFaterId(menuFaterId);
			menu.setMenuStatus(new BigDecimal("1"));
			menu.setMenuSerial(menuSerial);
			menu.setForeignAppAddress(foriegnAppAddress);
			menu.setCountSql(countSql);
			menu.setSiteId(siteId);
			JSONObject error = menuService.updateMenu(menu);
			toPage(error.toString());
		}
		
	}
	
	/** 
	 * deleteMenu:(删除菜单). <br/> 
	 * 
	 * @author adolph.jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteMenu(){
		String menuId = getRequest().getParameter("menuId");
		//---------delete之前先删除链接图标
		//Menu menu = menuService.findMenuById(menuId);
		List<Menu> list = menuService.findFamilyMenuByFid(menuId);
		for (Menu menu : list) {
			File oldImg = new File(PathUtil.getWebRoot()+menu.getMenuPath());
			if(oldImg.exists()){
				oldImg.delete();
			}
		}
		JSONObject error = menuService.deleteNew(menuId);
		toPage(error.toString());
	}
	
	/**
	 * 各站点菜单列表
	 * @return
	 */
	public String getMenu4EverSite(){
		this.getRequest().setAttribute("sites", menuService.getSites());
		return "getMenu4EverSite";
	}
	
	public void getMenus(){
		String siteId = this.getRequest().getParameter("siteId");
		List<Menu> list = menuService.findMenuList("",siteId);
		JSONArray ja = new JSONArray();
		for (Menu menu : list) {
			JSONObject jo = new JSONObject();
			jo.put("isForbidenGrant", "0");
			jo.put("menuId", menu.getMenuId());
			jo.put("menuFaterId", menu.getMenuFaterId());
			jo.put("menuName", menu.getMenuName());
			jo.put("menuUrl", menu.getMenuUrl());
			jo.put("menuType", menu.getMenuType());
			jo.put("menuSort", menu.getMenuSort());
			jo.put("menuStatus", menu.getMenuStatus());
			jo.put("menuExtLinks", menu.getMenuExtLinks());
			String foreignAppAddress;
			if(CommonUtil.stringIsNULL(menu.getForeignAppAddress())){
				foreignAppAddress = "";
			}else{
				foreignAppAddress = "";
			}
			jo.put("foreignAppAddress", foreignAppAddress);
			jo.put("menuSerial", menu.getMenuSerial());
			jo.put("menuRank", menu.getMenuRank());
			jo.put("menuSimpleName", menu.getMenuSimpleName());
			jo.put("havaChild", menu.getHavaChild());
			jo.put("menuPath", menu.getMenuPath());
//			if(!"1".equals(isAdmin)&&CommonUtil.stringIsNULL(menu.getSiteId())){
//				map.put("disabled", true);
//			}else{
//				map.put("disabled", false);
//			}
			ja.add(jo);
		}
		toPage(ja.toString());
	}
}
