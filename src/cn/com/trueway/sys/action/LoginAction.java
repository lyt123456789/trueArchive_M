package cn.com.trueway.sys.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sun.misc.BASE64Encoder;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.SpringContextUtil;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.service.MenuService;
import cn.com.trueway.sys.util.AESPlus;
import cn.com.trueway.sys.util.ImageUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
/**
 * 
 * 描述：系统登录操作action
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class LoginAction extends BaseAction{
	
	private static final long serialVersionUID = -5592521460728146916L;

	private DepartmentService departmentService;
	
	private EmployeeService employeeService;
	
	private MenuService menuService;

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	/**  
	 * <p>Description: 生成验证码</p>  
	 */  
	public void createImg() throws Exception{
		Object[] objs = ImageUtil.createImage();
		HttpServletRequest req = getRequest();
		HttpServletResponse res = getResponse();
		HttpSession session = req.getSession();
		session.setAttribute("imgcode", objs[0]);
		res.setContentType("image/png");
		OutputStream os = res.getOutputStream();
		ImageIO.write(
			(BufferedImage)objs[1], "png", os);
		os.close();
	}
	
	/**  
	 * <p>Description:验证  验证码 </p>  
	 */  
	public void valideCode(){
		String code = getRequest().getParameter("code");
		String imgcode = (String) getRequest().getSession().getAttribute("imgcode");
		Map<String, Object> map =new HashMap<String, Object>();
		if(code == null|| code.equals("")) {
				map.put("success", false);
				map.put("msg", "验证码为空!");
		}else if(!code.equalsIgnoreCase(imgcode)){
			map.put("success", false);
			map.put("msg", "验证码不正确!");
		}else{
			map.put("success", true);
		}		
		JSONObject json = JSONObject.fromObject(map);
		String callbackparam = getRequest().getParameter("callbackparam");
		String result = json.toString();
		result = callbackparam + "(" + result + ")";
		PrintWriter out = null;
		try {
			HttpServletResponse response = getResponse();
			response.setContentType("text/html; charset=utf-8");	         
			out = response.getWriter();		
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null)
				out.close();
		}
	}

	/**
	 * 从根目录转至登陆页
	 * @return
	 */
	public String toLogin(){
		return "fail";
	}

	
	//检测当前登陆人的密码是不是本单位的初始密码
	public String checkIfNeedToChangePsw(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String loginName=emp.getEmployeeLoginname();
		Employee tempemp=employeeService.getEmployeeByLoginName(loginName);
		String userId = tempemp.getEmployeeGuid();
		//获取ids中人员密码
		String userPswInOrcl=employeeService.getPassWord(userId);
		String result="0";
		String siteId=emp.getSiteId()==null?"":emp.getSiteId();
		if(siteId != null && !"".equals(siteId)){
			List<Object[]> allSiteName=departmentService.getAllSiteName();
			for(int i=0 ;allSiteName!=null && i<allSiteName.size(); i++){
				Object[] temp = allSiteName.get(i);
				String tempSiteId=temp[2].toString();//siteid
				String sitepasswordpwd=temp[4].toString();//MD5加密的密码
				if(siteId.equals(tempSiteId) && userPswInOrcl.equals(sitepasswordpwd)){
					result="1";
				}
			}
		}
		return result;
	}
	
	

	
	
	
	/**
	 * 
	 * 描述：系统推出
	 */
	public String loginout(){
		if (getSession()!=null) {
			getSession().invalidate();
		}
		return "loginout";
	}
	
	/**
	 * 描述：第三方的退出接口
	 */
	public void thirdLoginOut(){
		if (getSession()!=null) {
			try{
				getSession().invalidate();
			}catch (Exception e) {
			}
		}
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
	 * 描述：定时获取首页菜单角标数字
	 */
	public void getCountNum(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = "";
		JSONObject obj = new JSONObject();
		if(null != emp){
			userId = emp.getEmployeeGuid();
		}
		String id = getRequest().getParameter("id");
		Menu menu = menuService.findMenuById(id);
		String sql = menu.getCountSql();
		if(StringUtils.isNotBlank(sql)){
			Map<String, String> map = new HashMap<String, String>();
			map.put("countSql", sql);
			map.put("userId", userId);
			int count = 0;
			try {
				count = menuService.getCountBySql(map);//获取角标数目
			} catch (Exception e) {
			}
			if(count>0){
				obj.put("result", "success");
				obj.put("count", count);
			}
		}else{
			obj.put("result", "fail");
		}
		toPage(obj.toString());
	}
	
	/**
	 * base64加密
	 * @param str
	 * @return
	 */
	public String getBase64(String str){  
        byte[] b=null;  
        String s=null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if(b!=null){  
            s=new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
	
	
	/**
	 * 登录（在用）
	 * @return
	 */
	public String LoginOn(){
		if(!("1").equals(Constant.LICENSE_CHECK_PASSED)){
			try {
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			String username=getRequest().getParameter("username");//登录名
			String password=getRequest().getParameter("password");//密码

			Employee emp = new Employee();
			List<Employee> emplist = employeeService.getEmployeeByUsernameAndPassword(username, password);
			if(emplist==null||emplist.size()==0){
				getRequest().setAttribute("mes", "1");
				return "fail";
			}else{
				emp = emplist.get(0);
			}
			getSession().setAttribute(MyConstants.loginEmployee, emp);
			
			getRequest().setAttribute("loginname", emp.getEmployeeLoginname());
			getRequest().setAttribute("password", "");
			if(!"ids_admin".equals(emp.getEmployeeLoginname())){
				String userId = "";
				if (null != emp) {
					userId = emp.getEmployeeGuid();
				}
				Department dept = departmentService.findDepartmentById(emp.getDepartmentGuid());
				/*if(dept != null){
					if(emp.getEmployeeLoginname().contains("_admin")){
						getSession().setAttribute("deparmentName", dept.getDepartmentName());
					}else{
						getSession().setAttribute("deparmentName", getDepName(dept));
					}
				}*/
				//base64加密
				String base64UserId = getBase64(userId);
				String fgwSiteId = SystemParamConfigUtil2.getParamValueByParam("fgwSiteId");
//				if(siteId.equals(fgwSiteId)){
//					//获取阳光发改角标，并入库
//					getCount(emp.getEmployeeLoginname());
//				}
				
				getSession().setAttribute("siteId", emp.getSiteId());
				List<Menu> list = menuService.getMenuListByUserId("", userId, "");
				@SuppressWarnings("unchecked")
				//List<Menu> flist = (List<Menu>) getRequest().getAttribute("flist");	
				List<Menu> flist = new ArrayList<Menu>();
				List<Menu> clist = new ArrayList<Menu>();
				List<Menu> cclist = new ArrayList<Menu>();
				Iterator<Menu> ite = list.iterator();
				while(ite.hasNext()){
					Menu m = ite.next();
					if(m != null){
						//判断是否需要加数目角标
						String countSql = m.getCountSql();
						if(CommonUtil.stringNotNULL(countSql)){
							Map<String, String> map = new HashMap<String, String>();
							map.put("countSql", countSql);
							map.put("userId", userId);
							int count = 0;
							try {
								count = menuService.getCountBySql(map);//获取角标数目
							} catch (Exception e) {
							}
							if(count > 0){
								m.setCountStr(count+"");
							}else{
								m.setCountStr("");
							}
						}
					}
					if(Integer.parseInt(m.getMenuExtLinks()+"")==1){
						/*String url = ApplicationAction.getUrlById(m.getForeignAppAddress());*/
						String url = m.getForeignAppAddress();
						m.setForeignAppAddress(url);
					}
					if(CommonUtil.stringIsNULL(m.getMenuFaterId())){
						flist.add(m);
						ite.remove();
						continue;
					}
				}
				for(Menu m:flist){
					for(Menu n:list){
						if(m.getMenuId().equals(n.getMenuFaterId())){
							if(n != null){
								//判断是否需要加数目角标
								String countSql = n.getCountSql();
								if(CommonUtil.stringNotNULL(countSql)){
									Map<String, String> map = new HashMap<String, String>();
									map.put("countSql", countSql);
									map.put("userId", userId);
									int count = 0;
									try {
										count = menuService.getCountBySql(map);//获取角标数目
									} catch (Exception e) {
									}
									if(count > 0){
										n.setCountStr(count+"");
									}else{
										n.setCountStr("");
									}
								}
							}
							String url =n.getMenuUrl();
							n.setMenuUrl(url);
							clist.add(n);
							continue;
						}
					}
				}
				for(Menu m:clist){
					for(Menu n:list){
						if(m.getMenuId().equals(n.getMenuFaterId())){
							if(n != null){
								//判断是否需要加数目角标
								String countSql = n.getCountSql();
								if(CommonUtil.stringNotNULL(countSql)){
									Map<String, String> map = new HashMap<String, String>();
									map.put("countSql", countSql);
									map.put("userId", userId);
									int count = 0;
									try {
										count = menuService.getCountBySql(map);//获取角标数目
									} catch (Exception e) {
									}
									if(count > 0){
										n.setCountStr(count+"");
									}else{
										n.setCountStr("");
									}
								}
							}
							cclist.add(n);
							continue;
						}
					}
				}
				//存角色id
				RoleUser role = new RoleUser();
				List<RoleUser> roleUserList = employeeService.getRoleUserById(userId);
				if(roleUserList==null||roleUserList.size()==0){
					getRequest().setAttribute("mes", "1");
					return "fail";
				}else{
					role = roleUserList.get(0);
				}
				getSession().setAttribute(MyConstants.roleUser, roleUserList);
				
				getRequest().setAttribute("flist", flist);
				getRequest().setAttribute("clist", clist);
				getRequest().setAttribute("cclist", cclist);
				getRequest().setAttribute("base64UserId", base64UserId);
			}else{
				getSession().setAttribute("siteId", emp.getSiteId());
				List<Menu> mlist= menuService.getAllMenuList();	
				@SuppressWarnings("unchecked")
				//List<Menu> flist = (List<Menu>) getRequest().getAttribute("flist");	
				List<Menu> flist = new ArrayList<Menu>();
				List<Menu> clist= new ArrayList<Menu>();	
				List<Menu> cclist= new ArrayList<Menu>();
				Iterator<Menu> ite = mlist.iterator();
				while(ite.hasNext()){
					Menu m = ite.next();
					if(Integer.parseInt(m.getMenuExtLinks()+"")==1){
						/*String url = ApplicationAction.getUrlById(m.getForeignAppAddress());*/
						String url = m.getForeignAppAddress();
						m.setForeignAppAddress(url);
					}
					if(CommonUtil.stringIsNULL(m.getMenuFaterId())){
						flist.add(m);
						ite.remove();
						continue;
					}
				}
				for(Menu m:flist){
					for(Menu n:mlist){
						if(m.getMenuId().equals(n.getMenuFaterId())){
							String url =n.getMenuUrl();
							n.setMenuUrl(url);
							clist.add(n);
							continue;
						}
					}
				}
				for(Menu m:clist){
					for(Menu n:mlist){
						if(m.getMenuId().equals(n.getMenuFaterId())){
							cclist.add(n);
							continue;
						}
					}
				}
				getRequest().setAttribute("flist", flist);
				getRequest().setAttribute("clist", clist);
				getRequest().setAttribute("cclist", cclist);
			}
			/*TimedResource tr = imageService.getNowTimedResource(emp.getSiteId());
			if(tr!=null){
				getRequest().setAttribute("resourcesDirName",tr.getPath());
			}*/
			//判断是不是各个部门的初始密码
			//getRequest().setAttribute("needToChangePwd", checkIfNeedToChangePsw());
			
			//生成token
			/*String loginname = emp.getEmployeeLoginname();
			String password0 = emp.getEmployeePassword();
			try {
				String token = AESPlus.encrypt(loginname + "," + password0, "Truewaytalkkkkkk", "Truewaytalkkkkkk");
				getRequest().setAttribute("token", token);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/	
			/*operateLogUtil.addLog("登录", "登录人("+jyxx.getXM()+")","登录");*/
			return "login";
		}
		return null;
		
	}
	

}
