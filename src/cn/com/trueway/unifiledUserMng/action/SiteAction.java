package cn.com.trueway.unifiledUserMng.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.unifiledUserMng.entity.DeptBO;
import cn.com.trueway.unifiledUserMng.entity.ReturnEmp;
import cn.com.trueway.unifiledUserMng.entity.ReturnRole;
import cn.com.trueway.unifiledUserMng.entity.ReturnSite;
import cn.com.trueway.unifiledUserMng.entity.UserBO;
import cn.com.trueway.unifiledUserMng.entity.WbApp;
import cn.com.trueway.unifiledUserMng.service.SiteService;
import cn.com.trueway.unifiledUserMng.util.HttpUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.vo.Tree;
import cn.com.trueway.workflow.set.action.PushAction;
import cn.com.trueway.workflow.set.pojo.InnerUser;

public class SiteAction extends BaseAction{
	 
	/** 
	 *      
	 */
	private static final long 	serialVersionUID = 90973810892793670L;

	private static final Logger LOGGER 					= LoggerFactory.getLogger(PushAction.class);
	
	private SiteService 		siteService;
	
	
	
	public SiteService getSiteService() {
		
		
		
		
		
		return siteService;
	}



	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	/**
	 * 站点管理菜单  后台校验权限 仅超级管理员可见
	 * @return
	 * @throws UnsupportedEncodingException 
	 */

	public String showAllSite() throws UnsupportedEncodingException{
		String siteName = getRequest().getParameter("sitename");
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
		boolean adminFlag = false;
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			adminFlag = true;
		}
		if(adminFlag){
			Map<String,String> map = new HashMap<String,String>();
			map.put("siteName", siteName);
			//List<ReturnSite> list = siteService.getReturnSite(map);
			List<ReturnSite> list = new ArrayList<ReturnSite>();
			//调用ids接口，保存
			String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
			String methedUrl = "/deptJk/getAllSites.do";
			HttpUtil util = new HttpUtil();
			Map<String, String> param = new HashMap<String, String>();
			param.put("siteName", siteName);
			String ret = util.sendPost(idsUrl + methedUrl, param);
			if(!"fail".equals(ret)){
				JSONArray retArray = JSONArray.fromObject(ret);
				if(retArray != null){
					for(int i = 0;i < retArray.size();i++){
						JSONObject retObj = retArray.getJSONObject(i);
						if(retObj != null){
							ReturnSite returnSite = new ReturnSite();
							String id = retObj.getString("id");
							String name = retObj.getString("name");
							String code = "";
							String isSite = retObj.getString("isSite");
							String pName = retObj.getString("pName");
							String pid = retObj.getString("pid");
							String seq = retObj.getString("seq");
							String siteId = retObj.getString("siteId");
							String depAdmin = retObj.getString("depAdmin");
							if("null".equals(depAdmin)){
								depAdmin = "";
							}
							String depAdminId = retObj.getString("depAdminId");
							if("null".equals(depAdminId)){
								depAdminId = "";
							}
							String siteAdmin = retObj.getString("siteAdmin");
							if("null".equals(siteAdmin)){
								siteAdmin = "";
							}
							String siteAdminId = retObj.getString("siteAdminId");
							if("null".equals(siteAdminId)){
								siteAdminId = "";
							}
							String roleAdmin = retObj.getString("roleAdmin");
							if("null".equals(roleAdmin)){
								roleAdmin = "";
							}
							String roleAdminId = retObj.getString("roleAdminId");
							if("null".equals(roleAdminId)){
								roleAdminId = "";
							}
							String userAdmin = retObj.getString("userAdmin");
							if("null".equals(userAdmin)){
								userAdmin = "";
							}
							String userAdminId = retObj.getString("userAdminId");
							if("null".equals(userAdminId)){
								userAdminId = "";
							}
							returnSite.setId(id);
							returnSite.setName(name);
							returnSite.setCode(code);
							returnSite.setIssite(isSite);
							returnSite.setPname(pName);
							returnSite.setpId(pid);
							returnSite.setSiteAdminName(siteAdmin);
							returnSite.setSiteAdminGuid(siteAdminId);
							returnSite.setDepAdminGuid(depAdminId);
							returnSite.setDepAdminName(depAdmin);
							returnSite.setEmpAdminGuid(userAdminId);
							returnSite.setEmpAdminName(userAdmin);
							returnSite.setRoleAdminGuid(roleAdminId);
							returnSite.setRoleAdminName(roleAdmin);
							returnSite.setSeq(seq);
							returnSite.setSiteId(siteId);
							list.add(returnSite);
						}
					}
				}
			}
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("sitename", siteName);
		}
		return "toShowAll";
	}
	
	/**
	 * 添加站点
	 * @return
	 */
	public String toAddSite(){
		//父级部门
		List<ReturnSite> list = siteService.getFatherDep();
		getRequest().setAttribute("list", list);
		//最大排序
		String seq = siteService.getMaxSeq();
		getRequest().setAttribute("seq", seq);
		return "toAddSite";
	}
	
	/**
	 * 
	 * @Description: 添加站点 
	 * @author: xiep
	 * @time: 2018-5-14 下午4:22:36
	 */
	public void addSite(){
		String name = getRequest().getParameter("name");//站点名称
		String pDeptId = getRequest().getParameter("pDeptId");//父部门id
//		String pDeptName = getRequest().getParameter("pDeptName");//父部门名称
		String sortIndex = getRequest().getParameter("sortIndex");//排序号
//		String siteAdaminName = getRequest().getParameter("siteAdaminName");//站点管理员姓名
		String siteAdminGuid = getRequest().getParameter("siteAdminGuid");//站点管理员id
//		String depAdminName = getRequest().getParameter("depAdminName");//部门管理员姓名
		String depAdminGuid = getRequest().getParameter("depAdminGuid");//部门管理员id
//		String empAdminName = getRequest().getParameter("empAdminName");//人员管理员姓名
		String empAdminGuid = getRequest().getParameter("empAdminGuid");//人员管理员id
//		String roleAdminName = getRequest().getParameter("roleAdminName");//角色管理员姓名
		String roleAdminGuid = getRequest().getParameter("roleAdminGuid");//角色管理员id
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("name", name);
		obj.put("pid", pDeptId);
		obj.put("seq", sortIndex);
		obj.put("siteAdminId", siteAdminGuid);
		obj.put("depAdminId", depAdminGuid);
		obj.put("userAdminId", empAdminGuid);
		obj.put("roleAdminId", roleAdminGuid);
		obj.put("isSite", "1");
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/add.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			ret = "success";
		}
		toPage(ret);
	}
	
	/**
	 * 修改站点
	 * @return
	 */
	public String toUpdateSite(){
		String id = getRequest().getParameter("id");
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/editDept.do?id=" + id;
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		String ret = util.sendPost(idsUrl + methedUrl, param);
		ReturnSite returnSite = new ReturnSite();
		if(!"fail".equals(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			if(retObj != null){
				String name = retObj.getString("name");
//				String address = retObj.getString("address");
//				String areaId = retObj.getString("areaId");
//				String areaName = retObj.getString("areaName");
				String code = retObj.getString("code");
//				String dn = retObj.getString("dn");
				String isSite = retObj.getString("isSite");
				String pName = retObj.getString("pName");
				String pid = retObj.getString("pid");
				String seq = retObj.getString("seq");
				String siteId = retObj.getString("siteId");
				String depAdmin = retObj.getString("depAdmin");
				if("null".equals(depAdmin)){
					depAdmin = "";
				}
				String depAdminId = retObj.getString("depAdminId");
				if("null".equals(depAdminId)){
					depAdminId = "";
				}
				String siteAdmin = retObj.getString("siteAdmin");
				if("null".equals(siteAdmin)){
					siteAdmin = "";
				}
				String siteAdminId = retObj.getString("siteAdminId");
				if("null".equals(siteAdminId)){
					siteAdminId = "";
				}
				String roleAdmin = retObj.getString("roleAdmin");
				if("null".equals(roleAdmin)){
					roleAdmin = "";
				}
				String roleAdminId = retObj.getString("roleAdminId");
				if("null".equals(roleAdminId)){
					roleAdminId = "";
				}
				String userAdmin = retObj.getString("userAdmin");
				if("null".equals(userAdmin)){
					userAdmin = "";
				}
				String userAdminId = retObj.getString("userAdminId");
				if("null".equals(userAdminId)){
					userAdminId = "";
				}
				returnSite.setId(id);
				returnSite.setName(name);
				returnSite.setCode(code);
				returnSite.setIssite(isSite);
				returnSite.setPname(pName);
				returnSite.setpId(pid);
				returnSite.setSiteAdminName(siteAdmin);
				returnSite.setSiteAdminGuid(siteAdminId);
				returnSite.setDepAdminGuid(depAdminId);
				returnSite.setDepAdminName(depAdmin);
				returnSite.setEmpAdminGuid(userAdminId);
				returnSite.setEmpAdminName(userAdmin);
				returnSite.setRoleAdminGuid(roleAdminId);
				returnSite.setRoleAdminName(roleAdmin);
				returnSite.setSeq(seq);
				returnSite.setSiteId(siteId);
			}
		}
		//ReturnSite returnSite = siteService.getReturnSiteById(id);
		getRequest().setAttribute("returnSite", returnSite);
		//父级部门
		List<ReturnSite> list = siteService.getFatherDep();
		getRequest().setAttribute("list", list);
		return "toUpdateSite";
	}
	
	/**
	 * 
	 * @Description: 修改站点信息
	 * @author: xiep
	 * @time: 2018-5-14 下午2:19:46
	 */
	public void updateSite(){
		String id = getRequest().getParameter("id");//站点名称
		String name = getRequest().getParameter("name");//站点名称
		String pDeptId = getRequest().getParameter("pDeptId");//父部门id
//		String pDeptName = getRequest().getParameter("pDeptName");//父部门名称
		String sortIndex = getRequest().getParameter("sortIndex");//排序号
//		String siteAdaminName = getRequest().getParameter("siteAdaminName");//站点管理员姓名
		String siteAdminGuid = getRequest().getParameter("siteAdminGuid");//站点管理员id
//		String depAdminName = getRequest().getParameter("depAdminName");//部门管理员姓名
		String depAdminGuid = getRequest().getParameter("depAdminGuid");//部门管理员id
//		String empAdminName = getRequest().getParameter("empAdminName");//人员管理员姓名
		String empAdminGuid = getRequest().getParameter("empAdminGuid");//人员管理员id
//		String roleAdminName = getRequest().getParameter("roleAdminName");//角色管理员姓名
		String roleAdminGuid = getRequest().getParameter("roleAdminGuid");//角色管理员id
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("id", id);
		obj.put("name", name);
		obj.put("pid", pDeptId);
		obj.put("seq", sortIndex);
		obj.put("isSite", "1");
		obj.put("siteAdminId", siteAdminGuid);
		obj.put("depAdminId", depAdminGuid);
		obj.put("userAdminId", empAdminGuid);
		obj.put("roleAdminId", roleAdminGuid);
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/edit.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			ret = "success";
		}
		toPage(ret);
	}
	
	
	/**
	 * 根据站点，显示站点下一级部门
	 * @return
	 */
	public String showAllDep(){
		String siteId = "";
		String isadmin = getRequest().getParameter("isadmin");
		String name = getRequest().getParameter("name");
		String seq = getRequest().getParameter("seq");
		String searchSiteName = getRequest().getParameter("searchSiteName");
		if(CommonUtil.stringNotNULL(searchSiteName)){
			searchSiteName = searchSiteName.trim();
		}
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
		String depId = employee.getDepartmentGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			if(searchSiteName ==null||"".equals(searchSiteName)){
				siteId = "BFA811EA-0000-0000-4557-2FC000000689";
			}else{
				siteId = searchSiteName;
			}
			isadmin = "1";
		}else{
			//判断是否是部门管理员，并获取部门的站点siteId
			//siteId = siteService.getSiteByUserId(userId,depId);
			ReturnSite returnSite = siteService.getReturnSite(depId);
			if(returnSite != null ){
				siteId = returnSite.getSiteId();
			}
			getRequest().setAttribute("returnList","0" );
		}
		//获取所有站点
		List<ReturnSite> siteList = siteService.getAllSite();
		getRequest().setAttribute("siteList", siteList);
		if(CommonUtil.stringNotNULL(searchSiteName)){
			siteId = searchSiteName;
		}
		getRequest().setAttribute("siteId", siteId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("seq", seq);
		map.put("siteId", siteId);
		List<ReturnSite> returnList = siteService.getDepBySiteId(map);
		getRequest().setAttribute("returnList", returnList);
		getRequest().setAttribute("isadmin", isadmin);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("seq", seq);
		return "showAllDep";
	}
	
	/**
	 * 根据父级部门获取子集部门
	 */
	public void getDepByPid(){
		PrintWriter out = null;
		String pid = getRequest().getParameter("pid");
		String json = null;
		try {
			List<ReturnSite> returnList =  siteService.getDepByPid(pid);
			if(returnList !=null && returnList.size()>0){
				JSONArray list = JSONArray.fromObject(returnList);
				json = "{\"success\":\"success\",\"list\":"+list+"}";
			}else{
				json = "{\"success\":\"false\"}";
			}
			out = getResponse().getWriter();
			out.print(json);
		} catch (Exception e) {
			json = "{\"success\":\"false\"}";
			out.print(json);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 添加部门
	 * @return
	 */
	public String toAddDep(){
		String fatherId = getRequest().getParameter("depId");
		String siteId = getRequest().getParameter("siteId");//站点id
		//查找父级部门的相关信息
		ReturnSite returnSite = siteService.getReturnSite(fatherId);
		//最大排序
		String seq = siteService.getMaxSeq();
		getRequest().setAttribute("pname", returnSite.getName());
		getRequest().setAttribute("pid", returnSite.getId());
		getRequest().setAttribute("seq", seq);
		getRequest().setAttribute("siteId", siteId);
		return "toAddDep";
	}
	/**
	 * 
	 * @Description: 添加部门
	 * @author: xiep
	 * @time: 2018-5-15 上午10:44:28
	 */
	public void addDept(){
		String deptName = getRequest().getParameter("name");//部门名称
//		String pName = getRequest().getParameter("pName");//父部门名称
		String pId = getRequest().getParameter("pId");//父部门id
		String seq = getRequest().getParameter("seq");//排序
//		String code = getRequest().getParameter("code");//编码
		String siteId = getRequest().getParameter("siteId");//站点id
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("name", deptName);
		obj.put("pid", pId);
		obj.put("seq", seq);
		obj.put("isSite", "0");
		obj.put("siteId", siteId);
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/add.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			ret = "success";
		}
		toPage(ret);
	}
	
	/**
	 * 查看部门
	 * @return
	 */
	public String toUpdateDep(){
		String depId = getRequest().getParameter("depId");
		String isadmin = getRequest().getParameter("isadmin");
		String siteId = getRequest().getParameter("siteId");
		String isCheck = getRequest().getParameter("isCheck");
		ReturnSite returnSite = siteService.getReturnSite(depId);
		//获取父级
		String pname = siteService.getdepNameById(returnSite.getFatherDep());
		returnSite.setPname(pname);
		getRequest().setAttribute("returnSite", returnSite);
		getRequest().setAttribute("isadmin", isadmin);
		getRequest().setAttribute("siteId", siteId);
		return "toUpdateDep";
	}
	
	/**
	 * 
	 * @Description: 更新部门信息
	 * @author: xiep
	 * @time: 2018-5-15 下午3:45:59
	 * @return
	 */
	public void updateDep(){
		String id = getRequest().getParameter("id");//站点id
		String name = getRequest().getParameter("name");//站点名称
		String pDeptId = getRequest().getParameter("pDeptId");//父部门id
		String sortIndex = getRequest().getParameter("seq");//排序号
		String siteId = getRequest().getParameter("siteId");//站点id
		String isSite = getRequest().getParameter("isSite");//站点id
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("id", id);
		obj.put("name", name);
		obj.put("pid", pDeptId);
		obj.put("seq", sortIndex);
		obj.put("siteId", siteId);
		obj.put("isSite", isSite);
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/edit.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			ret = "success";
		}
		toPage(ret);
	}
	
	
	/**
	 * 判断部门下是否包含人员
	 */
	public void judge(){
		PrintWriter out = null;
		String depId = getRequest().getParameter("depId");
		String json = null;
		try {
			int count =  siteService.getCount(depId);
			if(count >0){
				json = "{\"success\":\"success\"}";
			}else{
				json = "{\"success\":\"false\"}";
			}
			out = getResponse().getWriter();
			out.print(json);
		} catch (Exception e) {
			json = "{\"success\":\"false\"}";
			out.print(json);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 
	 * @Description: 获取人员列表
	 * @author: xiep
	 * @time: 2018-6-5 下午5:32:51
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String showAllEmp() throws UnsupportedEncodingException{
		String siteId ="";
		String isadmin = getRequest().getParameter("isadmin");
		String name = getRequest().getParameter("name");
		String seq = getRequest().getParameter("seq");
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
//		String depId = employee.getDepartmentGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			siteId = getRequest().getParameter("sitename");
			if(siteId ==null||"".equals(siteId)){
				siteId = "BFA811EA-0000-0000-4557-2FC000000689";
			}
			getRequest().setAttribute("admin","1");
		}else{
			//判断是否是部门管理员，并获取部门的站点siteId
			//siteId = siteService.getSiteByUserId(userId,depId);
			siteId = employee.getSiteId();
			getRequest().setAttribute("returnList","0" );
		}
		//获取所有站点
		List<ReturnSite> siteList = siteService.getAllSite();
		getRequest().setAttribute("siteList", siteList);
		getRequest().setAttribute("siteId", siteId);
		Map<String,String> map = new HashMap<String,String>();
//		map.put("name", name);
		map.put("seq", seq);
		map.put("siteId", siteId);
		List<ReturnSite> returnList = siteService.getDepBySiteId(map);
		getRequest().setAttribute("returnList", returnList);
		getRequest().setAttribute("isadmin", isadmin);
		getRequest().setAttribute("name", name);
		return "toShowAllEmp";
	}
	
	/**
	 * 
	 * @Description:人员赋权 
	 * @author: xiep
	 * @time: 2018-6-14 下午3:09:35
	 * @return
	 */
	public String showAllEmpForAuth(){
		String siteId = "";
		String isadmin = getRequest().getParameter("isadmin");
		String name = getRequest().getParameter("name");
		String beginTime = getRequest().getParameter("begintime");
		String endTime = getRequest().getParameter("endtime");
		String seq = getRequest().getParameter("seq");
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
//		String depId = employee.getDepartmentGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			siteId = getRequest().getParameter("siteId");
			if(siteId ==null||"".equals(siteId)){
				siteId = "BFA811EA-0000-0000-4557-2FC000000689";
			}
			getRequest().setAttribute("admin","1");
		}else{
			//判断是否是部门管理员，并获取部门的站点siteId
			//siteId = siteService.getSiteByUserId(userId,depId);
			siteId = employee.getSiteId();
			getRequest().setAttribute("returnList","0" );
		}
		//获取所有站点
		List<ReturnSite> siteList = siteService.getAllSite();
		getRequest().setAttribute("siteList", siteList);
		getRequest().setAttribute("siteId", siteId);
		Map<String,String> map = new HashMap<String,String>();
//		map.put("name", name);
		map.put("seq", seq);
		map.put("siteId", siteId);
		List<ReturnSite> returnList = siteService.getDepBySiteId(map);
		getRequest().setAttribute("returnList", returnList);
		getRequest().setAttribute("isadmin", isadmin);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime", endTime);
		return "showAllEmpForAuth";
	}
	
	/**
	 * 展示相关站点或部门下的人员(包括兼职人员)
	 * @throws UnsupportedEncodingException 
	 */
	public String showEmp() throws UnsupportedEncodingException{
		String depId = getRequest().getParameter("depId");
		String siteId = getRequest().getParameter("siteId");
		String name = getRequest().getParameter("name");
		//name = StringUtils.isBlank(name) ? "" : new String(name .getBytes("ISO-8859-1"),"utf-8");
		String pagesize = getRequest().getParameter("pageSize");
		int selectIndex = 0;
		getRequest().setAttribute("pageIndex", selectIndex);
		selectIndex = GenericValidator.isBlankOrNull(getRequest().getParameter("selectIndex")) ? 1 : (Integer.parseInt(getRequest().getParameter("selectIndex")));
		//判断是否为超级管理员
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			getRequest().setAttribute("admin","1");
		}
		//根据当前人获取当前站点下排序第一的部门，默认展示该部门下的所有人员
		/*if(depId == null || "".equals(depId)){
			if( !"1".equals(userId)){
				depId = siteService.getFristDepId(employee.siteId);
			}
		}else{
			int count = siteService.getCount(depId);
			if(count<2){//每个站点下有管理员
				if( !"1".equals(userId)){
					depId = siteService.getFristDepId(depId);
				}
			}
		}*/
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("depId", depId);
		map.put("name", name);
		map.put("siteId", siteId);
		int count = siteService.getAllEmpCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		DTPageBean list = siteService.getAllEmp(count,Paging.selectIndex, Paging.pageSize, map);
		List<ReturnEmp> empList = new ArrayList<ReturnEmp>();
		List<?> returnList = list.getDataList();
		for(int i=0 ;returnList!=null && i<returnList.size(); i++){
			Object[] data = (Object[]) returnList.get(i);
			ReturnEmp  returnEmp= new  ReturnEmp(); 
			returnEmp.setId(data[0]==null?"":data[0].toString());
			returnEmp.setLoginName(data[1]==null?"":data[1].toString());
			returnEmp.setName(data[2]==null?"":data[2].toString());
			returnEmp.setPassword(data[3]==null?"":data[3].toString());
			returnEmp.setSex(data[4]==null?"":data[4].toString());
			returnEmp.setAge(data[5]==null?"":data[5].toString());
			returnEmp.setDepId(data[6]==null?"":data[6].toString());
			returnEmp.setCreateDate(data[7]==null?"":data[7].toString());
			returnEmp.setStatus(data[8]==null?"":data[8].toString());
			returnEmp.setPhone(data[9]==null?"":data[9].toString());
			returnEmp.setTel(data[10]==null?"":data[10].toString());
			returnEmp.setSeq(data[11]==null?"":data[11].toString());
			returnEmp.setUserType(data[12]==null?"":data[12].toString());
			returnEmp.setJobCode(data[13]==null?"":data[13].toString());
			returnEmp.setJobTitle(data[14]==null?"":data[14].toString());
			returnEmp.setFax(data[15]==null?"":data[15].toString());
			returnEmp.setDn(data[16]==null?"":data[16].toString());
			returnEmp.setShortphonenum(data[17]==null?"":data[17].toString());
			returnEmp.setDutytitle(data[18]==null?"":data[18].toString());
			returnEmp.setJobseq(data[19]==null?"":data[19].toString());
			returnEmp.setIsleave(data[20]==null?"":data[20].toString());
			returnEmp.setGroup_id(data[21]==null?"":data[21].toString());
			returnEmp.setNickName(data[22]==null?"":data[22].toString());
			returnEmp.setSiteId(data[23]==null?"":data[23].toString());
			returnEmp.setEmail(data[24]==null?"":data[24].toString());
			returnEmp.setSsnum(data[25]==null?"":data[25].toString());
			returnEmp.setSfznum(data[26]==null?"":data[26].toString());
			returnEmp.setSmknum(data[27]==null?"":data[27].toString());
			returnEmp.setPosition(data[28]==null?"":data[28].toString());
			returnEmp.setStaffids(data[29]==null?"":data[29].toString());
			returnEmp.setJobName(data[30]==null?"":data[30].toString());
			returnEmp.setRoleName(data[31]==null?"":data[31].toString());
			
			
			/*if(jobcode != null&&!"".equals(jobcode)){
				String jobName = siteService.getdepNameById(jobcode.split(",")[0]);
				returnEmp.setJobName(jobName);
				String jobSiteName = siteService.getJonSiteName(jobcode.split(",")[0]);
				returnEmp.setJobSiteName(jobSiteName);
			}*/
			//获取角色id与角色名
			/*Map<String,String> roleMap = siteService.getRoleId(data[0]==null?"":data[0].toString(),data[6]==null?"":data[6].toString());
			String roleId = roleMap.get("roleId");
			String roleName = roleMap.get("roleName");
			if(roleId !=null&&!"".equals(roleId)){
				returnEmp.setRoleId(roleId);
			}
			if(roleName !=null&&!"".equals(roleName)){
				returnEmp.setRoleName(roleName);
			}*/
			empList.add(returnEmp);
		}
		getRequest().setAttribute("page", Paging.pageIndex);
		getRequest().setAttribute("totalRows", list.getTotalRows());
		getRequest().setAttribute("numPerPage",list.getNumPerPage());
		getRequest().setAttribute("totalPages", list.getTotalPages());
		getRequest().setAttribute("depId", depId);
		getRequest().setAttribute("siteId", siteId);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("empList", empList);
		return "toShowEmp";
	}
	
	public String showEmpForAuth() throws UnsupportedEncodingException{
		String depId = getRequest().getParameter("depId");
		String name = getRequest().getParameter("name");
		name = StringUtils.isBlank(name) ? "" : new String(name .getBytes("ISO-8859-1"),"utf-8");
		String beginTime = getRequest().getParameter("begintime");
		String endTime = getRequest().getParameter("endtime");
		String pagesize = getRequest().getParameter("pageSize");
		int selectIndex = 0;
		getRequest().setAttribute("pageIndex", selectIndex);
		selectIndex = GenericValidator.isBlankOrNull(getRequest().getParameter("selectIndex")) ? 1 : (Integer.parseInt(getRequest().getParameter("selectIndex")));
		//判断是否为超级管理员
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			getRequest().setAttribute("admin","1");
		}
		//根据当前人获取当前站点下排序第一的部门，默认展示该部门下的所有人员
		if(depId == null || "".equals(depId)){
			if( !"1".equals(userId)){
				depId = siteService.getFristDepId(employee.siteId);
			}
		}else{
			int count = siteService.getCount(depId);
			if(count<2){//每个站点下有管理员
				if( !"1".equals(userId)){
					depId = siteService.getFristDepId(depId);
				}
			}
		}
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("depId", depId);
		map.put("name", name);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		int count = siteService.getAllEmpCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		DTPageBean list = siteService.getAllEmp(count,Paging.selectIndex, Paging.pageSize, map);
		List<ReturnEmp> empList = new ArrayList<ReturnEmp>();
		List<?> returnList = list.getDataList();
		for(int i=0 ;returnList!=null && i<returnList.size(); i++){
			Object[] data = (Object[]) returnList.get(i);
			ReturnEmp  returnEmp= new  ReturnEmp(); 
			returnEmp.setId(data[0]==null?"":data[0].toString());
			returnEmp.setLoginName(data[1]==null?"":data[1].toString());
			returnEmp.setName(data[2]==null?"":data[2].toString());
			returnEmp.setPassword(data[3]==null?"":data[3].toString());
			returnEmp.setSex(data[4]==null?"":data[4].toString());
			returnEmp.setAge(data[5]==null?"":data[5].toString());
			returnEmp.setDepId(data[6]==null?"":data[6].toString());
			returnEmp.setCreateDate(data[7]==null?"":data[7].toString());
			returnEmp.setStatus(data[8]==null?"":data[8].toString());
			returnEmp.setPhone(data[9]==null?"":data[9].toString());
			returnEmp.setTel(data[10]==null?"":data[10].toString());
			returnEmp.setSeq(data[11]==null?"":data[11].toString());
			returnEmp.setUserType(data[12]==null?"":data[12].toString());
			returnEmp.setJobCode(data[13]==null?"":data[13].toString());
			returnEmp.setJobTitle(data[14]==null?"":data[14].toString());
			returnEmp.setFax(data[15]==null?"":data[15].toString());
			returnEmp.setDn(data[16]==null?"":data[16].toString());
			returnEmp.setShortphonenum(data[17]==null?"":data[17].toString());
			returnEmp.setDutytitle(data[18]==null?"":data[18].toString());
			returnEmp.setJobseq(data[19]==null?"":data[19].toString());
			returnEmp.setIsleave(data[20]==null?"":data[20].toString());
			returnEmp.setGroup_id(data[21]==null?"":data[21].toString());
			returnEmp.setNickName(data[22]==null?"":data[22].toString());
			returnEmp.setSiteId(data[23]==null?"":data[23].toString());
			returnEmp.setEmail(data[24]==null?"":data[24].toString());
			returnEmp.setSsnum(data[25]==null?"":data[25].toString());
			returnEmp.setSfznum(data[26]==null?"":data[26].toString());
			returnEmp.setSmknum(data[27]==null?"":data[27].toString());
			returnEmp.setPosition(data[28]==null?"":data[28].toString());
			returnEmp.setStaffids(data[29]==null?"":data[29].toString());
			//获取兼职部门，兼职站点
			String jobcode = data[13]==null?"":data[13].toString();
			if(jobcode != null&&!"".equals(jobcode)){
				String jobName = siteService.getdepNameById(jobcode.split(",")[0]);
				returnEmp.setJobName(jobName);
				String jobSiteName = siteService.getJonSiteName(jobcode.split(",")[0]);
				returnEmp.setJobSiteName(jobSiteName);
			}
			empList.add(returnEmp);
		}
		getRequest().setAttribute("page", Paging.pageIndex);
		getRequest().setAttribute("totalRows", list.getTotalRows());
		getRequest().setAttribute("numPerPage",list.getNumPerPage());
		getRequest().setAttribute("totalPages", list.getTotalPages());
		getRequest().setAttribute("depId", depId);
		getRequest().setAttribute("empList", empList);
		return "showEmpForAuth";
	}
	
	/**
	 * 
	 * @Description:跳到人员添加页面 
	 * @author: xiep
	 * @time: 2018-5-16 下午4:28:13
	 * @return
	 */
	public String toAddEmp(){
		String deptId = getRequest().getParameter("depId");
		ReturnSite returnSite = siteService.getReturnSite(deptId);
		String seq = siteService.getMaxEmpSeq();
		if(returnSite != null){
			String deptName = returnSite.getName();
			getRequest().setAttribute("deptName", deptName);
			getRequest().setAttribute("deptId", deptId);
		}
		getRequest().setAttribute("seq", seq);
		return "addEmp";
	}
	
	/**
	 * 
	 * @Description:跳到批量修改密码页面 
	 * @author: lun
	 * @time: 2018-5-28 下午4:28:13
	 * @return
	 */
	public String toChangePW(){
		String personIds = getRequest().getParameter("personIds");
		String passWords = getRequest().getParameter("passWords");
		getRequest().setAttribute("personIds", personIds);
		getRequest().setAttribute("passWords", passWords);
		
		return "changePW";
	}
	
	public void changePW(){
		String newPW = getRequest().getParameter("newPW");//需批量修改的密码

		String personIds = getRequest().getParameter("personIds");
		String[] idArray = personIds.split(",");
		
		String result = "success";
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		for(int i = 0 ; i < idArray.length ; i++){
			String methedUrl = "/userJk/setPassword.do?userId="+idArray[i]+"&passWord="+newPW;
			HttpUtil util = new HttpUtil();
			String ret = util.sendPost(idsUrl + methedUrl, null);
			if("fail".equals(ret)){
				result = "fail";
			}else {
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if(!"true".equals(jsonObject.getString("success"))){
					result = "fail";
				}
			}
		}
		
		toPage(result);
	}
	
	//批量删除站点
	public void deleteSite(){
		String siteIds = getRequest().getParameter("siteIds");//需批量修改的密码
		String[] siteIdArray = siteIds.split(",");
		String result = "success";
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		for(int i = 0 ; i < siteIdArray.length ; i++){
			String methedUrl = "/deptJk/delete.do?id="+siteIdArray[i];
			HttpUtil util = new HttpUtil();
			String ret = util.sendPost(idsUrl + methedUrl, null);
			if("fail".equals(ret)){
				result = "fail";
			}else {
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if(!"true".equals(jsonObject.getString("success"))){
					result = "fail";
				}
			}
		}
		toPage(result);
	}
	//批量删除部门
	public void deleteDept(){
		String deptIds = getRequest().getParameter("deptIds");//需批量修改的密码
		String[] deptIdArray = deptIds.split(",");
		String result = "success";
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		for(int i = 0 ; i < deptIdArray.length ; i++){
			String methedUrl = "/deptJk/delete.do?id="+deptIdArray[i];
			HttpUtil util = new HttpUtil();
			String ret = util.sendPost(idsUrl + methedUrl, null);
			if("fail".equals(ret)){
				result = "fail";
			}else {
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if(!"true".equals(jsonObject.getString("success"))){
					result = "fail";
				}
			}
		}
		toPage(result);
	}
	
	public void deleteEmp(){
		String siteId =  getRequest().getParameter("siteId");//人员id
		String deptId =  getRequest().getParameter("deptId");//部门id
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/userJk/delete.do?id="+siteId+"&deptId="+deptId;
		HttpUtil util = new HttpUtil();
		String result = "success";
		String ret = util.sendPost(idsUrl + methedUrl, null);
		if("fail".equals(ret)){
			result = "fail";
		}else {
			JSONObject jsonObject = JSONObject.fromObject(ret);
			if(!"true".equals(jsonObject.getString("success"))){
				result = "fail";
			}
		}
		toPage(result);
		
	}
	
	public void deleteRole(){
		String roleIds =  getRequest().getParameter("roleIds");//人员id
		String[] roleArray = roleIds.split(",");
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String result = "success";
		//逐个检查角色是否流程绑定，有流程角色就提示
		for(int i=0;i<roleArray.length;i++){
			List<WfNode> nodeList = siteService.checkProcessRole(roleArray[i]);
			if(nodeList != null&&nodeList.size()>0){
				InnerUser innerUser = siteService.getRole(roleArray[i]);
				result = "fail;选择的角色"+innerUser.getName()+"已绑定流程，不能删除，否则影响流程使用";
				toPage(result);
				return;
			}
		}
		
		for(int i=0;i<roleArray.length;i++){
			String methedUrl = "/roleJk/delete.do?roleId="+roleArray[i];
			HttpUtil httpUtil = new HttpUtil();
			String ret = httpUtil
					.sendPost(idsUrl+methedUrl,null);
			if("fail".equals(ret)){
				result = "fail;删除角色失败！";
			}else{
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if(!"true".equals(jsonObject.getString("success"))){
					result = "fail;删除角色失败！";
				}
			}
		}
		toPage(result);
	}
	
	public void addEmp(){
		String deptId = getRequest().getParameter("deptId");//部门id
		String loginName = getRequest().getParameter("loginName");//登录名
		String pwd = getRequest().getParameter("pwd");//密码
		String name = getRequest().getParameter("name");//姓名
		String sex = getRequest().getParameter("sex");//性别
		String age = getRequest().getParameter("age");//年龄
		String phone = getRequest().getParameter("phone");//手机号
		String tel = getRequest().getParameter("tel");//电话号码
		String jobTitle = getRequest().getParameter("jobTitle");//职称
		String fax = getRequest().getParameter("fax");//传真
		String shortPhoneNum = getRequest().getParameter("shortPhoneNum");//短号
		String ssnum = getRequest().getParameter("ssnum");//社保卡号
		String sfznum = getRequest().getParameter("sfznum");//身份证号码
		String smknum = getRequest().getParameter("smknum");//市民卡号
		String seq = getRequest().getParameter("seq");//排序
		
		if(StringUtils.isBlank(smknum)){//市名卡没填默认存1
			smknum = "1";
		}
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("deptId", deptId);
		obj.put("loginname", loginName);
		obj.put("password", pwd);
		obj.put("name", name);
		obj.put("sex", sex);
		obj.put("age", age);
		obj.put("phone", phone);
		obj.put("tel", tel);
		obj.put("status", "1");
		obj.put("jobTitle", jobTitle);
		obj.put("fax", fax);
		obj.put("shortPhoneNum", shortPhoneNum);
		obj.put("ssnum", ssnum);
		obj.put("sfznum", sfznum);
		obj.put("smknum", smknum);
		obj.put("isLeave", "0");
		obj.put("seq", seq);
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/userJk/add.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		String retVal = "fail";
		if(!"fail".equals(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			boolean isSuccess = retObj.getBoolean("success");
			String msg = retObj.getString("msg");
			if(isSuccess){
				retVal = "success;"+msg;
			}else{
				retVal = "fail;"+msg;
			}
		}
		toPage(retVal);
	}
	
	public void batchLeave(){
		String personIds = getRequest().getParameter("personIds");//部门id
		String[] idArray =  personIds.split(",");
		String result = "success";
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		for(int i = 0;i < idArray.length ; i++){
			//调用ids接口
			String methedUrl = "/userJk/leave.do?id="+idArray[i];
			HttpUtil util = new HttpUtil();
			Map<String, String> param = new HashMap<String, String>();
			String ret = util.sendPost(idsUrl + methedUrl, param);
			if("fail".equals(ret)){
				result = "fail";
			}
		}
	
		toPage(result);
	}
	
	public void checkDept(){
		String siteId = getRequest().getParameter("siteId");//部门id
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String result = "success";
		String methedUrl = "/deptJk/getAllDeptsBySiteId.do?siteId="+siteId;
		HttpUtil util = new HttpUtil();
		String ret = util.sendPost(idsUrl + methedUrl, null);
		JSONArray retObj = JSONArray.fromObject(ret);
		if(retObj.size()!=0){
			result = "fail";
		}
		toPage(result);
	}
	
	public void checkDept2(){
		String deptId = getRequest().getParameter("deptId");//部门id
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String result = "success";
		String methedUrl = "/deptJk/getDeptsByPid.do?id="+deptId;
		HttpUtil util = new HttpUtil();
		String ret = util.sendPost(idsUrl + methedUrl, null);
		JSONArray retObj = JSONArray.fromObject(ret);
		if(retObj.size()!=0){
			result = "fail";
		}
	    methedUrl = "/userJk/getUserList.do";
	    JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("depId", deptId);
		obj.put("page", 1);
		obj.put("rows", 10);
		obj.put("userName", "");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
	    ret = util.sendPost(idsUrl + methedUrl, param);
	    JSONObject retObj2 = JSONObject.fromObject(ret);
	    int count = retObj2.getInt("total");
	    if(count > 0){
	    	result = "fail";
	    }
	    
		toPage(result);
	}
	
	/**
	 * 
	 * @Description: 跳转到修改人员页面
	 * @author: xiep
	 * @time: 2018-5-16 上午9:49:28
	 * @return
	 */
	public String toUpdateEmp(){
		String empId = getRequest().getParameter("empId");//选中人员的id
		//调用ids接口，根据id获取人员信息
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/userJk/getUser.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(employee!=null){
			getRequest().setAttribute("siteId", employee.getSiteId());
		}
		param.put("id", empId);
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			if(retObj != null){
//				String currdeptId = retObj.getString("currdeptId");
				JSONObject userObj = retObj.getJSONObject("user");
				if(userObj != null){
					getRequest().setAttribute("depId", userObj.getString("deptId") == null ? "" : userObj.getString("deptId"));//部门id
					getRequest().setAttribute("deptName", userObj.getString("deptName") == null ? "" : userObj.getString("deptName"));//部门名称
					getRequest().setAttribute("id", userObj.getString("id") == null ? "" : userObj.getString("id"));//empId
					getRequest().setAttribute("loginName", userObj.getString("loginname") == null ? "" : userObj.getString("loginname"));//登录名
					getRequest().setAttribute("name", userObj.getString("name") == null ? "" : userObj.getString("name"));//姓名
					getRequest().setAttribute("phone", userObj.getString("phone") == null ? "" : userObj.getString("phone"));//手机
					getRequest().setAttribute("sex", userObj.getString("sex") == null ? "" : userObj.getString("sex"));//性别
					getRequest().setAttribute("age", ("null").equals(userObj.getString("age")) ? "" : userObj.getString("age"));//年龄
					getRequest().setAttribute("tel", ("null").equals(userObj.getString("tel")) ? "" : userObj.getString("tel"));//电话
					getRequest().setAttribute("jobTitle", ("null").equals(userObj.getString("jobTitle"))  ? "" : userObj.getString("jobTitle"));//职称
					getRequest().setAttribute("fax", ("null").equals(userObj.getString("fax")) ? "" : userObj.getString("fax"));//传真
					getRequest().setAttribute("shortPhoneNum", ("null").equals(userObj.getString("shortPhoneNum"))  ? "" : userObj.getString("shortPhoneNum"));//短号
					getRequest().setAttribute("ssnum", ("null").equals(userObj.getString("ssnum")) ? "" : userObj.getString("ssnum"));//社保卡号
					getRequest().setAttribute("sfznum", ("null").equals(userObj.getString("sfznum")) ? "" : userObj.getString("sfznum"));//身份证号
					getRequest().setAttribute("smknum", ("null").equals(userObj.getString("smknum")) ? "" : userObj.getString("smknum"));//市民卡号
					getRequest().setAttribute("seq", ("null").equals(userObj.getString("seq")) ? "" : userObj.getString("seq"));//排序
				}
			}
		}
		return "updateEmp";
	}
	/**
	 * 
	 * @Description: 修改人员
	 * @author: xiep
	 * @time: 2018-5-16 下午2:31:21
	 */
	public void updateEmp(){
		String empId = getRequest().getParameter("id");
		String deptId = getRequest().getParameter("deptId");//部门id
		String loginName = getRequest().getParameter("loginName");//登录名
		String pwd = getRequest().getParameter("pwd");//密码
		String name = getRequest().getParameter("name");//姓名
		String sex = getRequest().getParameter("sex");//性别
		String age = getRequest().getParameter("age");//年龄
		String phone = getRequest().getParameter("phone");//手机号
		String tel = getRequest().getParameter("tel");//电话号码
		String jobTitle = getRequest().getParameter("jobTitle");//职称
		String fax = getRequest().getParameter("fax");//传真
		String shortPhoneNum = getRequest().getParameter("shortPhoneNum");//短号
		String ssnum = getRequest().getParameter("ssnum");//社保卡号
		String sfznum = getRequest().getParameter("sfznum");//身份证号码
		String smknum = getRequest().getParameter("smknum");//市民卡号
		String seq = getRequest().getParameter("seq");//排序
		String currentDeptId = getRequest().getParameter("currentDeptId");//当前人员部门ID
		
		if(StringUtils.isBlank(smknum)){//市名卡没填默认存1
			smknum = "1";
		}
		
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		obj.put("id", empId);
		obj.put("deptId", deptId);
		obj.put("loginname", loginName);
		obj.put("password", pwd);
		obj.put("name", name);
		obj.put("sex", sex);
		obj.put("age", age);
		obj.put("phone", phone);
		obj.put("tel", tel);
		obj.put("status", "1");
		obj.put("jobTitle", jobTitle);
		obj.put("fax", fax);
		obj.put("shortPhoneNum", shortPhoneNum);
		obj.put("ssnum", ssnum);
		obj.put("sfznum", sfznum);
		obj.put("smknum", smknum);
		obj.put("isLeave", "0");
		obj.put("seq", seq);
		obj.put("currdeptId", currentDeptId);
		//调用ids接口，保存
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/userJk/edit.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		if(!"fail".equals(ret)){
			ret = "success";
		}
		toPage(ret);
	}
	
	/**
	 * 
	 * 根据userid与部门deptid定位人员信息
	 * @return
	 */
	public String getEmpById(){
//		String empId = getRequest().getParameter("empId");
//		String depId = getRequest().getParameter("depId");
		
		return "";
	}
	
	/**
	 * 
	 * @Description: 
	 * @author: xiep
	 * @time: 2018-5-18 上午10:17:07
	 * @return
	 */
	public String getRoleList(){
		String searchSiteName = getRequest().getParameter("searchSiteName");
		if(CommonUtil.stringNotNULL(searchSiteName)){
			searchSiteName = searchSiteName.trim();
		}
		//获取当前登录人siteid
		String siteId = "";
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);// 获取当前登录人
		String userId = employee.getEmployeeGuid();
		String depId = employee.getDepartmentGuid();
		String superadmin = SystemParamConfigUtil.getParamValueByParam("superadmin");
		if(superadmin.equals(userId)){//验证当前登陆人是否为超级管理员
			if(searchSiteName ==null||"".equals(searchSiteName)){
				siteId = "BFA811EA-0000-0000-4557-2FC000000689";
			}else{
				siteId = searchSiteName;
			}
			getRequest().setAttribute("isadmin", 1 );
		}else{
			//判断是否是部门管理员，并获取部门的站点siteId
			//siteId = siteService.getSiteByUserId(userId,depId);
			ReturnSite returnSite = siteService.getReturnSite(depId);
			if(returnSite != null ){
				siteId = returnSite.getSiteId();
			}
		}
		//获取所有站点
		List<ReturnSite> siteList = siteService.getAllSite();
		getRequest().setAttribute("siteList", siteList);
		if(CommonUtil.stringNotNULL(searchSiteName)){
			siteId = searchSiteName;
		}
		getRequest().setAttribute("siteId", siteId);
		
		String name = getRequest().getParameter("name");
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/roleJk/getRoleList.do";
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		JSONObject obj = new JSONObject();
		String pagesize = getRequest().getParameter("pageSize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = 0;
		int selectIndex = 0;
		selectIndex = GenericValidator.isBlankOrNull(getRequest().getParameter("selectIndex")) ? 1 : (Integer.parseInt(getRequest().getParameter("selectIndex")));
		obj.put("page", selectIndex);
		obj.put("rows", pageSize);
		obj.put("siteId", siteId);
		obj.put("roleName", name);
		obj.put("userName", "");
		obj.put("deptName", "");
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		List<ReturnRole> retRoleList = new ArrayList<ReturnRole>();//角色列表
		if(CommonUtil.stringNotNULL(ret)){
			try {
				JSONObject retObj = JSONObject.fromObject(ret);
				if(retObj != null){
					count = retObj.getInt("total");
					JSONArray roleArray = retObj.getJSONArray("roleList");
					for(int i = 0; i < roleArray.size(); i++){
						JSONObject roleObj = roleArray.getJSONObject(i);
						ReturnRole retRole = new ReturnRole();
						if(roleObj != null){
							retRole.setId(roleObj.getString("id"));
							retRole.setAppId(roleObj.getString("wbAppIds"));
							retRole.setDescription(roleObj.getString("description"));
							retRole.setName(roleObj.getString("name"));
							retRole.setSeq(roleObj.getInt("seq"));
							retRole.setUserIds(roleObj.getString("userIds"));
							retRole.setUserName(roleObj.getString("userNames"));
							retRole.setDeptIds(roleObj.getString("deptIds"));
							retRole.setDeptName(roleObj.getString("deptNames"));
							retRole.setDeptName(roleObj.getString("deptNames"));
							retRole.setIfDefault(roleObj.getInt("ifDefault"));
							//人员
							JSONArray userArray = roleObj.getJSONArray("userList");//人员列表
							List<UserBO> userList = new ArrayList<UserBO>();
							if(userArray != null){
								for(int j = 0; j < userArray.size(); j++){
									UserBO userBO = new UserBO();
									JSONObject userObj = userArray.getJSONObject(j);
									userBO.setId(userObj.getString("id"));
									userBO.setName(userObj.getString("name"));
									userList.add(userBO);
								}
							}
							retRole.setUserList(userList);
							//部门
							JSONArray deptArray = roleObj.getJSONArray("deptList");//部门列表
							List<DeptBO> deptList = new ArrayList<DeptBO>();
							if(deptArray != null){
								for(int j = 0; j < deptArray.size(); j++){
									JSONObject deptObj = deptArray.getJSONObject(j);
									DeptBO deptBO = new DeptBO();
									deptBO.setId(deptObj.getString("id"));
									deptBO.setName(deptObj.getString("name"));
									deptList.add(deptBO);
								}
							}
							retRole.setDeptList(deptList);
							//资源
							JSONArray wbAppArray = roleObj.getJSONArray("wbAppList");
							List<WbApp> wbAppList = new ArrayList<WbApp>();//资源列表
							List<WbApp> portalAppList = new ArrayList<WbApp>();//门户资源列表(resourcetype=1)
							List<WbApp> oaAppList = new ArrayList<WbApp>();//OA资源列表(resourcetype=2)
							String resIds = "";
							String portalResIds = "";
							String oaResIds = "";
							if(wbAppArray != null){
								for(int j = 0; j < wbAppArray.size(); j++){
									JSONObject wbAppObj = wbAppArray.getJSONObject(j);
									if(wbAppObj != null){
										WbApp wbApp = new WbApp();
										wbApp.setId(wbAppObj.getString("id"));
										wbApp.setName(wbAppObj.getString("name"));
										wbApp.setResourceType(wbAppObj.getInt("resourcetype") + "");
										wbAppList.add(wbApp);
										if("1".equals(wbApp.getResourceType())){//门户资源列表(resourcetype=1)
											portalAppList.add(wbApp);
											portalResIds = portalResIds + wbApp.getId() + ",";
										}else if("2".equals(wbApp.getResourceType())){//OA资源列表(resourcetype=2)
											oaAppList.add(wbApp);
											oaResIds = oaResIds + wbApp.getId() + ",";
										}
										resIds = resIds + wbApp.getId() + ",";
									}
								}
								if(CommonUtil.stringNotNULL(resIds)){
									resIds = resIds.substring(0, resIds.length() - 1);
								}
								if(CommonUtil.stringNotNULL(portalResIds)){
									portalResIds = portalResIds.substring(0, portalResIds.length() - 1);
								}
								if(CommonUtil.stringNotNULL(oaResIds)){
									oaResIds = oaResIds.substring(0, oaResIds.length() - 1);
								}
							}
							retRole.setWbAppList(wbAppList);
							retRole.setPortalAppList(portalAppList);
							retRole.setOaAppList(oaAppList);
							retRole.setResIds(resIds);
							retRole.setPortalResIds(portalResIds);
							retRole.setOaResIds(oaResIds);
						}
						retRoleList.add(retRole);
					}
				}
			} catch (Exception e) {
				LOGGER.error("TrueIDS 接口返回参数异常，接口："+methedUrl+",回参："+ret+";{}", e);
			}
		}
		Paging.setPagingParams(getRequest(), pageSize, count);
		getRequest().setAttribute("retRoleList", retRoleList);
		getRequest().setAttribute("name", name);
		return "getRoleList";
	}
	
	/**
	 * 
	 * @Description: 添加角色
	 * @author: xiep
	 * @time: 2018-5-22 上午11:22:50
	 * @return
	 */
	public String toAddRole(){
		String roleId = UuidGenerator.generate36UUID();
		getRequest().setAttribute("roleId", roleId);
		//获取站点
		List<ReturnSite> siteList = getSiteList();
		String searchSiteName =  getRequest().getParameter("searchSiteName");
		String siteId =  getRequest().getParameter("siteId");
		getRequest().setAttribute("siteId", siteId);
		getRequest().setAttribute("searchSiteName", searchSiteName);
		getRequest().setAttribute("siteList", siteList);
		return "addRole";
	}
	
	/**
	 * 
	 * @Description: 获取资源列表
	 * @author: xiep
	 * @time: 2018-5-28 下午3:33:37
	 * @return
	 */
	public String getResourceList(){
		String siteId =  getRequest().getParameter("siteId");//站点id
		String resType = getRequest().getParameter("resType");//资源类型
		String selectedIds = getRequest().getParameter("selectedIds");//已选id
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		Map<String,String> map = new HashMap<String,String>();
		map.put("siteId", siteId);
		List<WbApp> wbAppList = new ArrayList<WbApp>();
		if(("1").equals(resType)){
			wbAppList = siteService.getPortalRSList(map);
		}else if(("2").equals(resType)){
			wbAppList = siteService.getOARSList(map);
		}
		
		for(WbApp fuWbApp : wbAppList){
			List<WbApp> a = new ArrayList<WbApp>();
			for(WbApp sonWbApp : wbAppList){
				if(fuWbApp.getId().equals(sonWbApp.getpId())){
					a.add(sonWbApp);
				}
			}
			fuWbApp.setWbAppList(a);
		}
		if(("1").equals(resType)){
			getRequest().setAttribute("portalWbAppList", wbAppList);
		}else if(("2").equals(resType)){
			getRequest().setAttribute("oaWbAppList", wbAppList);
		}
		getRequest().setAttribute("resType", resType);
		getRequest().setAttribute("selectedIds", selectedIds);
		return "getResourceList";
		
	}
	
	
	/**
	 * 
	 * @Description: 添加角色
	 * @author: xiep
	 * @time: 2018-5-28 下午5:27:33
	 */
	public void addRole(){
		String retMsg = "";
		String result = "";
		boolean addRoleRes = false;//添加角色结果
		boolean addRoleDeptRes = false;//添加角色部门结果
		boolean addRoleUserRes = false;//添加角色人员结果
		boolean addRoleResRes = false;//添加角色资源结果
		String roleName = getRequest().getParameter("roleName");//角色名称
		String deptIdAndNames = getRequest().getParameter("deptIdAndNames");//部门id和名称
		String userIdAndNames = getRequest().getParameter("userIdAndNames");//人员id和名称
		String portalRes = getRequest().getParameter("portalRes");//门户资源
		String oaRes = getRequest().getParameter("oaRes");//oa资源
		String siteId = getRequest().getParameter("siteId");//站点id
		String seq = getRequest().getParameter("seq");//站点id
		String ifDefault = StringUtils.isBlank(getRequest().getParameter("ifDefault"))?"0":"1";
		//保存角色
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/roleJk/add.do";
		HttpUtil util = new HttpUtil();
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		Map<String, String> param = new HashMap<String, String>();
		obj.put("name", roleName);
		obj.put("description", "");
		obj.put("seq", seq);
		obj.put("status", "1");
		obj.put("ifDefault", ifDefault);
		obj.put("siteId", siteId);
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		String roleId = "";//角色id
		if(CommonUtil.stringNotNULL(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			if(retObj != null){
				boolean success = retObj.getBoolean("success");
				if(success){
					roleId = retObj.getString("obj");
					addRoleRes = true;
				}else{
					retMsg = retMsg + retObj.getString("msg");
					addRoleRes = false;
				}
			}
		}
		if(CommonUtil.stringNotNULL(roleId)){
			//保存绑定部门
			String deptIds = "";
			if(CommonUtil.stringNotNULL(deptIdAndNames)){
				String[] deptIdAndNameArr = deptIdAndNames.split(";");
				if(deptIdAndNameArr.length>0){
					deptIds = deptIdAndNameArr[0];
				}
			}
			String saveRoleDeptUrl = "/roleJk/saveRoleDept.do?roleId="+ roleId + "&deptIds=" + deptIds;
			String saveRoleDeptRet = util.sendPost(idsUrl + saveRoleDeptUrl, null);
			if(CommonUtil.stringNotNULL(saveRoleDeptRet) && !"fail".equals(saveRoleDeptRet)){
				JSONObject retObj = JSONObject.fromObject(saveRoleDeptRet);
				if(retObj != null && retObj.getBoolean("success")){
					retMsg = retMsg + "部门绑定成功" + ",";
					addRoleDeptRes = true;
				}else{
					retMsg = retMsg + "部门绑定失败";
				}
			}
			//保存绑定人员
			String userIds = "";
			String userDeptIds = "";
			if(CommonUtil.stringNotNULL(userIdAndNames)){
				String[] userIdAndNameArr = userIdAndNames.split(";");
				if(userIdAndNameArr.length>0){
					userIds = userIdAndNameArr[0];
					userDeptIds = userIdAndNameArr[2];
				}
			}
			//String saveRoleUserUrl = "/roleJk/saveRoleUser.do?roleId="+ roleId + "&userIds=" + userIds + "&deptIds=" + userDeptIds;
			String saveRoleUserUrl = "/roleJk/saveRoleUser.do";
			Map<String, String> param2 = new HashMap<String, String>();
			param2.put("roleId", roleId);
			param2.put("userIds", userIds);
			param2.put("deptIds", userDeptIds);
			String saveRoleUserRet = util.sendPost(idsUrl + saveRoleUserUrl, param2);
			if(CommonUtil.stringNotNULL(saveRoleUserRet) && !"fail".equals(saveRoleUserRet)){
				JSONObject retObj = JSONObject.fromObject(saveRoleUserRet);
				if(retObj != null && retObj.getBoolean("success")){
					retMsg = retMsg + "人员绑定成功" + ",";
					addRoleUserRes = true;
				}else{
					retMsg = retMsg + "人员绑定失败";
				}
			}
			//保存绑定资源
			String protalResIds = "";//门户资源id
			String protalResNames = "";//门户资源名称
			if(CommonUtil.stringNotNULL(portalRes)){
				String[] portalResArr = portalRes.split(";");
				protalResIds = portalResArr[0];
				protalResNames = portalResArr[1];
			}
			String oaResIds = "";//oa资源id
			String oaResNames = "";//oa资源名称
			if(CommonUtil.stringNotNULL(oaRes)){
				String[] oaResArr = oaRes.split(";");
				oaResIds = oaResArr[0];
				oaResNames = oaResArr[1];
			}
			//门户资源绑定
				Map<String, String> param3 = new HashMap<String, String>();
				JSONObject obj2 = new JSONObject();
				obj2.put("roleId", roleId);
				obj2.put("appIds", protalResIds);
				obj2.put("appNames", protalResNames);
				obj2.put("resourcetype", "1");
				param3.put("data", obj2.toString());
				
				String saveRoleResUrl = "/roleJk/saveRoleWbApp.do";
				String saveRoleResRet = util.sendPost(idsUrl + saveRoleResUrl, param3);
				if(CommonUtil.stringNotNULL(saveRoleResRet) && !"fail".equals(saveRoleResRet)){
					JSONObject retObj = JSONObject.fromObject(saveRoleResRet);
					if(retObj != null && retObj.getBoolean("success")){
						retMsg = retMsg + "门户资源绑定成功";
						addRoleResRes = true;
					}else{
						retMsg = retMsg + "门户资源绑定失败";
					}
				}
			
			//oa资源绑定
				Map<String, String> param4 = new HashMap<String, String>();
				JSONObject obj3 = new JSONObject();
				obj3.put("roleId", roleId);
				obj3.put("appIds", oaResIds);
				obj3.put("appNames", oaResNames);
				obj3.put("resourcetype", "2");
				param4.put("data", obj3.toString());
				
				saveRoleResUrl = "/roleJk/saveRoleWbApp.do";
				saveRoleResRet = util.sendPost(idsUrl + saveRoleResUrl, param4);
				if(CommonUtil.stringNotNULL(saveRoleResRet) && !"fail".equals(saveRoleResRet)){
					JSONObject retObj = JSONObject.fromObject(saveRoleResRet);
					if(retObj != null && retObj.getBoolean("success")){
						retMsg = retMsg + "oa资源绑定成功";
						addRoleResRes = true;
					}else{
						retMsg = retMsg + "oa资源绑定失败";
					}
				}
			
		
		}else{
			retMsg = "角色添加失败！"+retMsg;
		}
		if(addRoleRes && addRoleDeptRes && addRoleUserRes && addRoleResRes){
			result = "success";
		}else{
			result = "fail";
		}
		toPage(result + ";" + retMsg);
	}
	
	/**
	 * 
	 * @Description:跳转到编辑角色页面 
	 * @author: xiep
	 * @time: 2018-5-30 上午10:23:10
	 * @return
	 */
	public String toUpdateRole(){
		String roleId = getRequest().getParameter("roleId");
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String searchSiteName =  getRequest().getParameter("searchSiteName");
		getRequest().setAttribute("searchSiteName", searchSiteName);
		//获取站点
		List<ReturnSite> siteList = getSiteList();
		getRequest().setAttribute("siteList", siteList);
		//获取角色信息
		HttpUtil util = new HttpUtil();
		String getRoleUrl = "/roleJk/getRole.do?id=" + roleId;
		String ret = util.sendPost(idsUrl + getRoleUrl, null);
		if(CommonUtil.stringNotNULL(ret) && !"fail".equals(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			if(retObj != null){
				//String description = retObj.getString("description");
				String id = retObj.getString("id");
				String ifDefault = retObj.getString("ifDefault");
				String name = retObj.getString("name");
				String seq = retObj.getString("seq");
				String siteId = retObj.getString("siteId");
				String status = retObj.getString("status");
				
				getRequest().setAttribute("id", id);
				getRequest().setAttribute("ifDefault", ifDefault);
				getRequest().setAttribute("name", name);
				getRequest().setAttribute("seq", seq);
				getRequest().setAttribute("siteId", siteId);
				getRequest().setAttribute("status", status);
			}
		}
		//获取绑定部门信息
		String getRoleDeptUrl = "/deptJk/getDeptsByRoleId.do?roleId=" + roleId;
		String roleDeptRet = util.sendPost(idsUrl + getRoleDeptUrl, null);
		if(CommonUtil.stringNotNULL(roleDeptRet) && !"fail".equals(roleDeptRet)){
			String deptIds = "";
			String deptNames = "";
			JSONArray roleDeptRetObj = JSONArray.fromObject(roleDeptRet);
			if(roleDeptRetObj != null){
				for(int i = 0; i < roleDeptRetObj.size(); i++){
					JSONObject roleDeptObj = roleDeptRetObj.getJSONObject(i);
					String deptId = roleDeptObj.getString("id");
					String deptName = roleDeptObj.getString("name");
					deptIds = deptIds + deptId + ",";
					deptNames = deptNames + deptName + ",";
				}
			}
			if(CommonUtil.stringNotNULL(deptIds)){
				deptIds = deptIds.substring(0, deptIds.length() - 1);
			}
			if(CommonUtil.stringNotNULL(deptNames)){
				deptNames = deptNames.substring(0, deptNames.length() - 1);
			}
			if(StringUtils.isBlank(deptIds)&&StringUtils.isBlank(deptNames)){
				getRequest().setAttribute("deptIdAndNames","");
			}else {
				getRequest().setAttribute("deptIdAndNames", deptIds + ";" + deptNames);
			}
			getRequest().setAttribute("deptNames", deptNames);
		}
		//获取已绑定人员信息
		String getRoleUserUrl = "/roleJk/getUserListByRoleId.do?roleId=" + roleId;
		String roleUserRet = util.sendPost(idsUrl + getRoleUserUrl, null);
		if(CommonUtil.stringNotNULL(roleUserRet) && !"fail".equals(roleUserRet)){
			String userIds = "";
			String userNames = "";
			String userDeptIds = "";
			JSONArray roleUserRetObj = JSONArray.fromObject(roleUserRet);
			if(roleUserRetObj != null){
				for(int i = 0; i < roleUserRetObj.size(); i++){
					JSONObject roleUserObj = roleUserRetObj.getJSONObject(i);
					String userId = roleUserObj.getString("id");
					String userName = roleUserObj.getString("name");
					String deptId = roleUserObj.getString("deptId");
					userIds = userIds + userId + ",";
					userNames = userNames + userName + ",";
					userDeptIds = userDeptIds + deptId + ",";
				}
			}
			if(CommonUtil.stringNotNULL(userIds)){
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			if(CommonUtil.stringNotNULL(userNames)){
				userNames = userNames.substring(0, userNames.length() - 1);
			}
			if(StringUtils.isBlank(userIds)&&StringUtils.isBlank(userNames)&&StringUtils.isBlank(userDeptIds)){
				getRequest().setAttribute("userIdAndNames","");
			}else {
				getRequest().setAttribute("userIdAndNames", userIds + ";" + userNames + ";" + userDeptIds);
			}
			getRequest().setAttribute("userNames", userNames);
		}
		//获取已绑定资源信息
		String getWbAppUrl = "/wbAppJk/getWbAppByRoleId.do?roleId=" + roleId;
		String roleWbAppRet = util.sendPost(idsUrl + getWbAppUrl, null);
		if(CommonUtil.stringNotNULL(roleWbAppRet) && !"fail".equals(roleWbAppRet)){
			String portalResIds = "";
			String portalResNames = "";
			String oaResIds = "";
			String oaResNames = "";
			String wfResIds = "";
			String wfResNames = "";
			JSONArray roleWbAppArray = JSONArray.fromObject(roleWbAppRet);
			if(roleWbAppArray != null){
				for(int i = 0; i < roleWbAppArray.size(); i++){
					JSONObject wbAppObj = roleWbAppArray.getJSONObject(i);
					if(wbAppObj != null){
						String resType = wbAppObj.getString("resourcetype") + "";
						String id = wbAppObj.getString("id");
						String name = wbAppObj.getString("name");
						if("1".equals(resType)){
							portalResIds = portalResIds + id + ",";
							portalResNames = portalResNames + name + ",";
						}else if("2".equals(resType)){
							oaResIds = oaResIds + id + ",";
							oaResNames = oaResNames + name + ",";
						}else if("3".equals(resType)){
							wfResIds = wfResIds + id + ",";
							wfResNames = wfResNames + name + ",";
						}
					}
				}
			}
			if(CommonUtil.stringNotNULL(portalResIds)){
				portalResIds = portalResIds.substring(0, portalResIds.length() - 1);
			}
			if(CommonUtil.stringNotNULL(portalResNames)){
				portalResNames = portalResNames.substring(0, portalResNames.length() - 1);
			}
			if(CommonUtil.stringNotNULL(oaResIds)){
				oaResIds = oaResIds.substring(0, oaResIds.length() - 1);
			}
			if(CommonUtil.stringNotNULL(oaResNames)){
				oaResNames = oaResNames.substring(0, oaResNames.length() - 1);
			}
			if(CommonUtil.stringNotNULL(wfResIds)){
				wfResIds = wfResIds.substring(0, wfResIds.length() - 1);
			}
			if(CommonUtil.stringNotNULL(wfResNames)){
				wfResNames = wfResNames.substring(0, wfResNames.length() - 1);
			}
			if(StringUtils.isBlank(portalResIds)&&StringUtils.isBlank(portalResNames)){
				getRequest().setAttribute("portalRes","");
			}else {
				getRequest().setAttribute("portalRes", portalResIds + ";" + portalResNames);
			}
			if(StringUtils.isBlank(oaResIds)&&StringUtils.isBlank(oaResNames)){
				getRequest().setAttribute("oaRes","");
			}else {
				getRequest().setAttribute("oaRes", oaResIds + ";" + oaResNames);
			}
			if(StringUtils.isBlank(wfResIds)&&StringUtils.isBlank(wfResNames)){
				getRequest().setAttribute("wfRes","");
			}else {
				getRequest().setAttribute("wfRes", wfResIds + ";" + wfResNames);
			}
			getRequest().setAttribute("portalResNames", portalResNames);
			getRequest().setAttribute("oaResNames", oaResNames);
			getRequest().setAttribute("wfResNames", wfResNames);
		}
		return "updateRole";
	}
	
	/**
	 * 
	 * @Description: 修改角色
	 * @author: xiep
	 * @time: 2018-5-30 下午4:58:43
	 */
	public void updateRole(){
		String retMsg = "";
		String result = "";
		boolean addRoleRes = false;//添加角色结果
		boolean addRoleDeptRes = false;//添加角色部门结果
		boolean addRoleUserRes = false;//添加角色人员结果
		boolean addRoleResRes = false;//添加角色资源结果
		String roleName = getRequest().getParameter("roleName");//角色名称
		String deptIdAndNames = getRequest().getParameter("deptIdAndNames");//部门id和名称
		String userIdAndNames = getRequest().getParameter("userIdAndNames");//人员id和名称
		String portalRes = getRequest().getParameter("portalRes");//门户资源
		String oaRes = getRequest().getParameter("oaRes");//oa资源
		String siteId = getRequest().getParameter("siteId");//站点id
		String seq = getRequest().getParameter("seq");//站点id
		String roleId = getRequest().getParameter("roleId");//角色id
		String ifDefault = StringUtils.isBlank(getRequest().getParameter("ifDefault"))?"0":"1";//角色id
		//保存角色
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/roleJk/edit.do";
		HttpUtil util = new HttpUtil();
		JSONObject obj = new JSONObject();//{"name":"tlc","pid":"","seq":"1","isSite":"0","siteId":""}
		Map<String, String> param = new HashMap<String, String>();
		obj.put("name", roleName);
		obj.put("description", "");
		obj.put("seq", seq);
		obj.put("status", "1");
		obj.put("ifDefault",ifDefault );
		obj.put("siteId", siteId);
		obj.put("id", roleId);
		param.put("data", obj.toString());
		String ret = util.sendPost(idsUrl + methedUrl, param);
		boolean success = false;
		if(CommonUtil.stringNotNULL(ret)){
			JSONObject retObj = JSONObject.fromObject(ret);
			if(retObj != null){
				success = retObj.getBoolean("success");
				if(success){
					addRoleRes = true;
				}else{
					retMsg = retMsg + retObj.getString("msg");
					addRoleRes = false;
				}
			}
		}
		if(success){
			//保存绑定部门
			String deptIds = "";
			if(CommonUtil.stringNotNULL(deptIdAndNames)){
				String[] deptIdAndNameArr = deptIdAndNames.split(";");
				if(deptIdAndNameArr.length>0){
					deptIds = deptIdAndNameArr[0];
				}
			}
			String saveRoleDeptUrl = "/roleJk/saveRoleDept.do?roleId="+ roleId + "&deptIds=" + deptIds;
			String saveRoleDeptRet = util.sendPost(idsUrl + saveRoleDeptUrl, null);
			if(CommonUtil.stringNotNULL(saveRoleDeptRet) && !"fail".equals(saveRoleDeptRet)){
				JSONObject retObj = JSONObject.fromObject(saveRoleDeptRet);
				if(retObj != null && retObj.getBoolean("success")){
					retMsg = retMsg + "部门绑定成功" + ",";
					addRoleDeptRes = true;
				}else{
					retMsg = retMsg + "部门绑定失败";
				}
			}
			//保存绑定人员
			String userIds = "";
			String userDeptIds = "";
			if(CommonUtil.stringNotNULL(userIdAndNames)){
				String[] userIdAndNameArr = userIdAndNames.split(";");
				if(userIdAndNameArr.length>0){
					userIds = userIdAndNameArr[0];
					userDeptIds = userIdAndNameArr[2];
				}
			}
			//String saveRoleUserUrl = "/roleJk/saveRoleUser.do?roleId="+ roleId + "&userIds=" + userIds + "&deptIds=" + userDeptIds;
			String saveRoleUserUrl = "/roleJk/saveRoleUser.do";
			Map<String, String> param2 = new HashMap<String, String>();
			param2.put("roleId", roleId);
			param2.put("userIds", userIds);
			param2.put("deptIds", userDeptIds);
			String saveRoleUserRet = util.sendPost(idsUrl + saveRoleUserUrl, param2);
			if(CommonUtil.stringNotNULL(saveRoleUserRet) && !"fail".equals(saveRoleUserRet)){
				JSONObject retObj = JSONObject.fromObject(saveRoleUserRet);
				if(retObj != null && retObj.getBoolean("success")){
					retMsg = retMsg + "人员绑定成功" + ",";
					addRoleUserRes = true;
				}else{
					retMsg = retMsg + "人员绑定失败";
				}
			}
			//保存绑定资源
			String protalResIds = "";//门户资源id
			String protalResNames = "";//门户资源name
			if(CommonUtil.stringNotNULL(portalRes)){
				String[] portalResArr = portalRes.split(";");
				protalResIds = portalResArr[0];
				protalResNames = portalResArr[1];
			}
			String oaResIds = "";//oa资源id
			String oaResNames = "";//oa资源name
			if(CommonUtil.stringNotNULL(oaRes)){
				String[] oaResArr = oaRes.split(";");
				oaResIds = oaResArr[0];
				oaResNames = oaResArr[1];
			}
			   
			//门户资源绑定
				Map<String, String> param3 = new HashMap<String, String>();
				JSONObject obj2 = new JSONObject();
				obj2.put("roleId", roleId);
				obj2.put("appIds", protalResIds);
				obj2.put("appNames", protalResNames);
				obj2.put("resourcetype", "1");
				param3.put("data", obj2.toString());
				
				String saveRoleResUrl = "/roleJk/saveRoleWbApp.do";
				String saveRoleResRet = util.sendPost(idsUrl + saveRoleResUrl, param3);
				if(CommonUtil.stringNotNULL(saveRoleResRet) && !"fail".equals(saveRoleResRet)){
					JSONObject retObj = JSONObject.fromObject(saveRoleResRet);
					if(retObj != null && retObj.getBoolean("success")){
						retMsg = retMsg + "门户资源绑定成功";
						addRoleResRes = true;
					}else{
						retMsg = retMsg + "门户资源绑定失败";
					}
				}
			
			//oa资源绑定
				Map<String, String> param4 = new HashMap<String, String>();
				JSONObject obj3 = new JSONObject();
				obj3.put("roleId", roleId);
				obj3.put("appIds", oaResIds);
				obj3.put("appNames", oaResNames);
				obj3.put("resourcetype", "2");
				param4.put("data", obj3.toString());
				
				saveRoleResUrl = "/roleJk/saveRoleWbApp.do";
				saveRoleResRet = util.sendPost(idsUrl + saveRoleResUrl, param4);
				if(CommonUtil.stringNotNULL(saveRoleResRet) && !"fail".equals(saveRoleResRet)){
					JSONObject retObj = JSONObject.fromObject(saveRoleResRet);
					if(retObj != null && retObj.getBoolean("success")){
						retMsg = retMsg + "oa资源绑定成功";
						addRoleResRes = true;
					}else{
						retMsg = retMsg + "oa资源绑定失败";
					}
				}
			
		}else{
			retMsg = "角色修改失败！"+retMsg;
		}
		if(addRoleRes && addRoleDeptRes && addRoleUserRes && addRoleResRes){
			result = "success";
		}else{
			result = "fail";
		}
		toPage(result + ";" + retMsg);
	}
	
	
	
	public void roleTransfer(){
		
		String result = "";
		String sendUserId = getRequest().getParameter("userId");
		String userIdAndNames = getRequest().getParameter("userIdAndNames");
		String sendDept = getRequest().getParameter("deptId");
		
		//角色转移接受方
		String recUserId = "";
		String recDeptId = "";
		
		if(CommonUtil.stringNotNULL(userIdAndNames)){
			String[] userIdAndNameArr = userIdAndNames.split(";");
			recUserId = userIdAndNameArr[0];
			recDeptId = userIdAndNameArr[2];
		}
		
		//角色转移
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/roleJk/transfer.do?oldUserId="+sendUserId+"&oldDeptId="+sendDept+"&newUserId="+recUserId+"&newDeptId="+recDeptId;
		HttpUtil util = new HttpUtil();
		String ret = util.sendPost(idsUrl+methedUrl, null);
		JSONObject jsonO = JSONObject.fromObject(ret);
		if(jsonO.getBoolean("success")){
			result = "true";
		}else{
			result = "false";
		}
		toPage(result);
	}
	
	/**
	 * 
	 * @Description: 保存角色部门信息
	 * @author: xiep
	 * @time: 2018-5-31 上午10:35:53
	 */
	public void saveRoleDepts(){
		boolean addRoleDeptRes = false;
		String roleId = getRequest().getParameter("roleId");
		String deptIds = getRequest().getParameter("deptIds");
		
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		HttpUtil util = new HttpUtil();
		String saveRoleDeptUrl = "/roleJk/saveRoleDept.do?roleId="+ roleId + "&deptIds=" + deptIds;
		String saveRoleDeptRet = util.sendPost(idsUrl + saveRoleDeptUrl, null);
		if(CommonUtil.stringNotNULL(saveRoleDeptRet) && !"fail".equals(saveRoleDeptRet)){
			JSONObject retObj = JSONObject.fromObject(saveRoleDeptRet);
			if(retObj != null && retObj.getBoolean("success")){
				addRoleDeptRes = true;
			}
		}
		toPage(addRoleDeptRes + "");
	}
	
	/**
	 * 
	 * @Description: 保存角色部门信息
	 * @author: xiep
	 * @time: 2018-5-31 上午10:35:53
	 */
	public void saveRoleUsers(){
		boolean addRoleUserRes = false;
		String roleId = getRequest().getParameter("roleId");
		String userIdAndNames = getRequest().getParameter("userIdAndNames");
		String userIds = "";
		String userDeptIds = "";
		if(CommonUtil.stringNotNULL(userIdAndNames)){
			String[] userIdAndNameArr = userIdAndNames.split(";");
			if(userIdAndNameArr.length>0){
				userIds = userIdAndNameArr[0];
				userDeptIds = userIdAndNameArr[2];
			}
		}
		//String saveRoleUserUrl = "/roleJk/saveRoleUser.do?roleId="+ roleId + "&userIds=" + userIds + "&deptIds=" + userDeptIds;
		String saveRoleUserUrl = "/roleJk/saveRoleUser.do";
		Map<String, String> param = new HashMap<String, String>();
		param.put("roleId", roleId);
		param.put("userIds", userIds);
		param.put("deptIds", userDeptIds);
		
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		HttpUtil util = new HttpUtil();
		String saveRoleUserRet = util.sendPost(idsUrl + saveRoleUserUrl, param);
		if(CommonUtil.stringNotNULL(saveRoleUserRet) && !"fail".equals(saveRoleUserRet)){
			JSONObject retObj = JSONObject.fromObject(saveRoleUserRet);
			if(retObj != null && retObj.getBoolean("success")){
				addRoleUserRes = true;
			}
		}
		toPage(addRoleUserRes + "");
	}
	
	public void saveRoleRes(){
		boolean addRoleRes = false;
		String portalRes = getRequest().getParameter("portalRes");//门户资源
		String oaRes = getRequest().getParameter("oaRes");//oa资源
		String roleId = getRequest().getParameter("roleId");//角色id
		String resourcetype = getRequest().getParameter("resourcetype");//资源类型
		String portalResNames = getRequest().getParameter("portalResNames");//
		String oaResNames = getRequest().getParameter("oaResNames");//
		
		String protalResIds = "";//门户资源
		if(CommonUtil.stringNotNULL(portalRes)){
			String[] portalResArr = portalRes.split(";");
			protalResIds = portalResArr[0];
		}
		String oaResIds = "";//oa资源
		if(CommonUtil.stringNotNULL(oaRes)){
			String[] oaResArr = oaRes.split(";");
			oaResIds = oaResArr[0];
		}
		
		/*String resIds = "";//所有资源id
		if(CommonUtil.stringNotNULL(protalResIds)){
			resIds = resIds + protalResIds + ",";
		}
		if(CommonUtil.stringNotNULL(oaResIds)){
			resIds = resIds + oaResIds + ",";
		}   
		
		String resNames = "";//所有资源名字
		if(CommonUtil.stringNotNULL(portalResNames)){
			resNames = resNames + portalResNames + ",";
		}
		if(CommonUtil.stringNotNULL(oaResNames)){
			resNames = resNames + oaResNames + ",";
		}*/
		//根据resourcetype来保存角色资源信息
		String resIds = "";
		String resNames = "";
		if(("1").equals(resourcetype)){
			resIds = portalRes;
			resNames = portalResNames;
		}else if(("2").equals(resourcetype)){
			resIds = oaRes;
			resNames = oaResNames;
		}
		
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		HttpUtil util = new HttpUtil();
		Map<String, String> param = new HashMap<String, String>();
		JSONObject obj = new JSONObject();
		obj.put("roleId", roleId);
		obj.put("appIds", resIds);
		obj.put("appNames", resNames);
		obj.put("resourcetype", resourcetype);
		param.put("data", obj.toString());
		
		String saveRoleResUrl = "/roleJk/saveRoleWbApp.do";
		String saveRoleResRet = util.sendPost(idsUrl + saveRoleResUrl, param);
		if(CommonUtil.stringNotNULL(saveRoleResRet) && !"fail".equals(saveRoleResRet)){
			JSONObject retObj = JSONObject.fromObject(saveRoleResRet);
			if(retObj != null && retObj.getBoolean("success")){
				addRoleRes = true;
			}
		}
		toPage(addRoleRes + "");
	}
	
	/**
	 * 
	 * @Description: //获取站点
	 * @author: xiep
	 * @time: 2018-5-28 下午2:23:02
	 * @return
	 */
	private List<ReturnSite> getSiteList(){
		String idsUrl = SystemParamConfigUtil.getParamValueByParam("idsUrl");//ids地址
		String methedUrl = "/deptJk/getAllSites.do";
		HttpUtil util = new HttpUtil();
		String ret = util.sendPost(idsUrl + methedUrl, null);
		JSONArray retObj = JSONArray.fromObject(ret);
		List<ReturnSite> siteList = new ArrayList<ReturnSite>();
		if(CommonUtil.stringNotNULL(ret) && retObj != null){
			for(int i = 0; i < retObj.size(); i++){
				JSONObject siteObj = retObj.getJSONObject(i);
				if(siteObj != null){
					ReturnSite returnSite = new ReturnSite();
					returnSite.setId(siteObj.getString("id"));
					returnSite.setName(siteObj.getString("name"));
					returnSite.setIssite(siteObj.getString("isSite"));
					returnSite.setSeq(siteObj.getString("seq"));
					siteList.add(returnSite);
				}
			}
		}
		return siteList;
	}
	
	public void getDeptListBySiteId(){
		String siteId = getRequest().getParameter("siteId");
		String userId = getRequest().getParameter("id");
		String deptId = getRequest().getParameter("deptId");
		if(userId==null||deptId==null){
			return;
		}
		Tree tree = siteService.getDeptById(siteId);
		tree.setChildren(siteService.findTree(tree));
		JSONArray jsonArray = JSONArray.fromObject(tree);
		toPage(jsonArray.toString());
	}
	
}
