package cn.com.trueway.sys.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.google.gson.Gson;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.CommonGroupUsers;
import cn.com.trueway.sys.util.DepSortUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.util.UuidGenerator;

public class TreeAction extends BaseAction{

	private static final long serialVersionUID = -5542446980616786930L;
	
	
	private EmployeeService employeeService;
	
	private DepartmentService departmentService;
	
	private String root;
	

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * 
	 * 描述：展示部门人员tree
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-24 上午9:39:47
	 * @throws UnsupportedEncodingException 
	 */
	public String showDepartmentTree() throws UnsupportedEncodingException{
		String nameType = this.getRequest().getParameter("nameType");
		String idType = this.getRequest().getParameter("idType");
		this.getRequest().setAttribute("idType", idType);
		this.getRequest().setAttribute("nameType", nameType);
		this.getRequest().setAttribute("isfgw", 0);
		String isowner = getRequest().getParameter("isowner");
		getRequest().setAttribute("isowner", isowner);
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		this.getRequest().setAttribute("department_rootId", department_rootId);
		return "departmentTree";
	}
	
	
	public void getContent() throws Exception {
		getResponse().setCharacterEncoding("utf-8");
		String type = getRequest().getParameter("type");
		String mc=getRequest().getParameter("mc");
		String userId=getRequest().getParameter("userId");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		String isowner = getRequest().getParameter("isowner");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		StringBuffer userids = null;
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}	
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
			String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
			String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
			if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
				department_rootId=bigDepId;
			}
			//不包含人员
			String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
			// 打开流
			PrintWriter out = getResponse().getWriter();
			// 第一次加载树时，root="source",生成“大部门”的节点
			if (root.equals("source")) {
				Department d_root = null;
				if(isowner!=null && isowner.equals("1")){
					d_root = departmentService.findDepartmentById(emp.getDepartmentGuid());
				}else{
					d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
				}
				
				String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
				// 生成JSON字符串
				out.println("[");
				//out.print(" {\"text\":\"");// 不显示链接的下划线
				out.print(" {\"text\":\"<b class='checkbox'></b><a data-id='"+d_root.getDepartmentGuid()+"' href='javascript:;' >");// 不显示链接的下划线
				out.print(rootString);// 节点的名字
				out.print("</a>\",");
				out.print("\"id\":\"");
				out.print(d_root.getDepartmentGuid());// 节点的id
				out.print("\",\"expanded\":");
				out.print(true);// 默认是否是展开方式
				out.print(",\"hasChildren\":");
				out.print(true);// 默认是否是展开方式
				out.print(",\"classes\":\"");
				out.print("folder");// 节点的样式
				out.print("\"");
				out.println(",");
				out.print("\"children\":");
				out.println("[");
				List<Department> departments = new ArrayList<Department>();
				
				departments=departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
				//过滤本节点
				if (departments!=null) {
					for (int i = 0; i < departments.size(); i++) {
						if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
							departments.remove(i);
						}
					}
				}
				
				if (departments != null && departments.size() > 0) {
					departments = DepSortUtil.sortDepartment(departments);
					createDepartmentJSON(departments, out,deptsid,userids.toString(),mc,type);
				}
				
				if(userids!=null){
					//如果包含人员才加载人员
					if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
						
					}else {
						if(type != null && type.equals("dept")){
							
						}else{
							List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_rootId,userids.toString());
							// 得到的人員Set不为空时，才生成字符串
							if (es != null && es.size() > 0) {
								if (departments != null && departments.size() > 0) {
									out.println(",");
								}
								createEmployeeJson(es, out);
							}
						}
						
					}
				}

				out.print("]");
				out.println("}");
				out.print("]");
			} else {
				List<Department> departments=null;
				String department_id = root.toString();
				Department d_root = departmentService.findDepartmentById(department_id);// 查出根节点
				departments = departmentService.queryDepartmentsBydepIds1(d_root,deptsid);
				out.println("[");
				// 得到的部门List不为空时，才生成字符串
				if (departments != null && departments.size() > 0) {
					createDepartmentJSON(departments, out,deptsid,userids.toString(),mc,type);
				}
				
				//如果包含人员才加载人员
				if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
					
				}else {
					
					if(type != null && type.equals("dept")){
						
					}else{
						List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department_id,userids.toString());
						// 得到的人員Set不为空时，才生成字符串
						if (es != null && es.size() > 0) {
							if (departments != null && departments.size() > 0) {
								out.println(",");
							}
							createEmployeeJson(es, out);
						}
					}
					
				}
				out.print("]");
			}
			// 关闭流
			out.close();
		}else{
			String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
			//获得大部门id
			String bigDepId=getSession().getAttribute(MyConstants.bigDepartmentId)!=null?getSession().getAttribute(MyConstants.bigDepartmentId).toString():null;
			String isBigDep=getRequest().getParameter("isBigDep");//是否采用大部门
			if (CommonUtil.stringNotNULL(isBigDep)&&isBigDep.equals("1")) {
				department_rootId=bigDepId;
			}
			String notEmployee=getRequest().getParameter("notEmployee");//是否采用大部门
			// 打开流
			PrintWriter out = getResponse().getWriter();
			// 第一次加载树时，root="source",生成“大部门”的节点
			if (root.equals("source")) {
				Department d_root = null;
				if(isowner!=null && isowner.equals("1")){
					d_root = departmentService.findDepartmentById(emp.getDepartmentGuid());
					department_rootId = emp.getDepartmentGuid();
				}else{
					d_root = departmentService.findDepartmentById(department_rootId);// 查出根节点
				}
				String rootString = d_root!=null?d_root.getDepartmentName():"根节点";
				// 生成JSON字符串
				out.println("[");
				//out.print(" {\"text\":\"");// 不显示链接的下划线
				out.print(" {\"text\":\"<b class='checkbox'></b><a  data-id='"+d_root.getDepartmentGuid()+"' href='javascript:;'>");// 不显示链接的下划线
				out.print(rootString);// 节点的名字
				out.print("</a>\",");
				out.print("\"id\":\"");
				out.print(d_root.getDepartmentGuid());// 节点的id
				out.print("\",\"expanded\":");
				out.print(true);// 默认是否是展开方式
				out.print(",\"hasChildren\":");
				out.print(true);// 默认是否是展开方式
				out.print(",\"classes\":\"");
				out.print("folder");// 节点的样式
				out.print("\"");
				out.println(",");
				out.print("\"children\":");
				out.println("[");
				List<Department> departments = new ArrayList<Department>();
				departments=departmentService.queryDepartmentsBySuperdepIds("'"+department_rootId+"'");
				//过滤本节点
				if (departments!=null) {
					for (int i = 0; i < departments.size(); i++) {
						if (departments.get(i).getDepartmentGuid().equals(department_rootId)) {
							departments.remove(i);
						}
					}
				}
				if (departments != null && departments.size() > 0) {
					departments = DepSortUtil.sortDepartment(departments);
					createJSON5(departments, out);
				}
				//如果包含人员才加载人员
				if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
					
				}else {
					if(type != null && type.equals("dept")){
						
					}else{
						List<Employee> es = (List<Employee>) employeeService.findEmployees(department_rootId);
						// 得到的人員Set不为空时，才生成字符串
						if (es != null && es.size() > 0) {
							if (departments != null && departments.size() > 0) {
								out.println(",");
							}
							createEmployeeJson(es, out);
						}
					}
				}
				out.print("]");
				out.println("}");
				out.print("]");

			} else {
				List<Department> departments=null;
				String department_id = root.toString();
				departments = (List<Department>) departmentService.findDepartments(department_id);
				out.println("[");
				// 得到的部门List不为空时，才生成字符串
				if (departments != null && departments.size() > 0) {
					createJSON5(departments, out);
				}
				if (CommonUtil.stringNotNULL(notEmployee)&&notEmployee.equals("1")) {
				}else {
					if(type != null && type.equals("dept")){
						
					}else{
						List<Employee> es = (List<Employee>) employeeService.findEmployees(department_id);
						// 得到的人員Set不为空时，才生成字符串
						if (es != null && es.size() > 0) {
							if (departments != null && departments.size() > 0) {
								out.println(",");
							}
							createEmployeeJson(es, out);
						}
					}
				}
				out.print("]");
			}
			out.close();
		}
	}
	
	
	public void createJSON5(List<Department> departments, PrintWriter out)
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			department = (Department) departments.get(i);
			out.print(" {\"text\":\"<b class='checkbox'></b><a  data-id='"+department.getDepartmentGuid()+"' href='javascript:;'  >");// 不显示链接的下划线
			out.print(department.getDepartmentName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			out.print("\",\"hasChildren\":");
			// 目前要遍历查询部门表与人员表才能判断有无子节点
			List<Department> ds = (List<Department>) departmentService
					.findDepartments(department.getDepartmentGuid());
			List<Employee> es = (List<Employee>) employeeService
					.findEmployees(department.getDepartmentGuid());
			if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
				out.print(true);// 有无子节点 true/false
			} else {
				out.print(false); // 页面上显示部门节点都是有子节点的样子
			}
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 遍历List，生成JSON字符串
	 * @param userids 
	 * @param deptsid 
	 * 
	 * @param rights
	 * @throws IOException
	 */
	public void createDepartmentJSON(List<Department> departments, PrintWriter out, String deptsid, String userids, String mc,String type )
			throws IOException {
		Department department;
		// 生成JSON字符串
		for (int i = 0; i < departments.size(); i++) {
			department = (Department) departments.get(i);
			out.print(" {\"text\":\"<b class='checkbox'></b><a  data-id='"+department.getDepartmentGuid()+"' href='javascript:;' >");// 不显示链接的下划线
			out.print(department.getDepartmentName());// 节点的名字

			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getDepartmentGuid());// 节点的id
			if(mc==null||"".equals(mc)){
				out.print("\",\"hasChildren\":");
				out.print(",\"classes\":\"");
			}else{
				// 目前要遍历查询部门表与人员表才能判断有无子节点
				List<Department> ds = (List<Department>) departmentService.queryDepartmentsBydepIds1(department,deptsid);
				List<Employee> es = (List<Employee>) employeeService.findEmployeesByIds(department.getDepartmentGuid(),userids);
				if ((ds != null && ds.size() > 0) || (es != null && es.size() > 0)) {
					out.print("\",\"expanded\":");
					out.print(true);// 默认是否是展开方式
					out.println(",");
					out.print("\"children\":");
					out.println("[");
					if(ds != null && ds.size() > 0){
						createDepartmentJSON(ds,out,deptsid,userids,mc,type);
					}
					if(type != null  && type.equals("dept")){
						
					}else{
						if(es != null && es.size() > 0){
							if(ds != null && ds.size() > 0){
								out.println(",");
							}
							createEmployeeJson(es,out);
						}
					}
					
					out.println("]");
					out.print(",\"classes\":\"");
				} else {
					out.print("\",\"classes\":\"");
				}
			}
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	
	public void createEmployeeJson(List<Employee> es, PrintWriter out)
			throws IOException {
		Employee e;
		for (int i = 0; i < es.size(); i++) {
			e = (Employee) es.get(i);
			out.print(" {\"text\":\"<b class='checkbox'></b><a data-id='"+e.getEmployeeGuid()+"' href='javascript:;' >");// 不显示链接的下划线
			out.print(e.getEmployeeName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(e.getEmployeeGuid());// 节点的id
			out.print("\",\"hasChildren\":");
			out.print(false);// 有无子节点 true/false
			out.print(",\"classes\":\"");
			out.print("file");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != es.size() - 1) {
				out.println(",");// 若不是最后一个节点则添加逗号
			}
		}
	}
	
	public String showDepts4M(){
		String userId = this.getRequest().getParameter("userId");
		String userIds = this.getRequest().getParameter("userIds");
		String siteId = this.getRequest().getParameter("siteId");
		this.getRequest().setAttribute("userId", userId);
		this.getRequest().setAttribute("userIds", userIds);
		this.getRequest().setAttribute("siteId", siteId);
		this.getRequest().setAttribute("isMail", 1);
		
		return "showDepts4M";
	}
	
	public String showDepts4MOfTrueOA(){
		String userId = this.getRequest().getParameter("userId");
		this.getRequest().setAttribute("userId", userId);
		return "showDepts4MOfTrueOA";
	}
	

	public void syncGetAllEmployees(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String id=getRequest().getParameter("id");
				List<Object[]> list = employeeService.getAllEmployeeInfoBySuperDepartmentId(id);
				List<Object[]> newList=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					newList.add(list.get(i));
				}
				Gson gson=new Gson();
				out.write(gson.toJson(newList));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
		
	}
	
	public void syncGetAllChildDepList(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try {
				String id=getRequest().getParameter("id");
				List<Object[]> list = departmentService.findAllChildDeptObjects(id);
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
	 * 跳转到常用人员组设置界面
	 * @return
	 */
	public String toSetUserGroup(){
		String role="0";
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		List<CommonGroup> cgs = departmentService.findAllCommonGroupByUid(emp.getEmployeeGuid(),emp.getSiteId(),role);
		this.getRequest().setAttribute("cgs", cgs);
		return "setUserGroup";
	}
	/**
	 * 删除常用人员组
	 */
	public void deleteUserGroup(){
		String gid = this.getRequest().getParameter("gid");
		departmentService.deleteCommonGroup(departmentService.findCommonGroupById(gid));
	}
	/**
	 * 新增常用人员组
	 */
	public void addUserGroup(){
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String name = this.getRequest().getParameter("name");
		String ret="";
		if(CommonUtil.stringNotNULL(name)){
			CommonGroup cg = new CommonGroup();
			cg.setId(UuidGenerator.generate32UUID());
			cg.setName(name);
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			cg.setCreateTime(new Date());
			cg.setBelongTo(emp.getEmployeeGuid());
			cg =  departmentService.saveCommonGroup(cg);
			ret = cg.getId();
		}else{
			ret = "nameNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 修改常用人员组名称
	 */
	public void modifyUserGroup(){
		String name = this.getRequest().getParameter("name");
		String groupId = this.getRequest().getParameter("groupId");
		String ret="";
		if(CommonUtil.stringNotNULL(name)){
			CommonGroup cg = departmentService.findCommonGroupById(groupId);
			cg.setName(name);
			cg.setCreateTime(new Date());
			departmentService.updateCommonGroup(cg);
			ret = "ok";
		}else{
			ret = "nameNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	/**
	 * 为常用人员组添加人员，常用人员组
	 */
	public void addGroupUsers(){
		String groupId = this.getRequest().getParameter("groupId");
		String persons = this.getRequest().getParameter("persons");
		JSONArray ja = JSONArray.fromObject(persons);
		String ret="";
		departmentService.deleteCommonGroupUsersByGid(groupId);
		if(!ja.isEmpty()){
			for(int i=0;i<ja.size();i++){
				JSONObject jo = (JSONObject) ja.get(i);
				CommonGroupUsers cgu = new CommonGroupUsers();
				cgu.setEmpId((String) jo.get("id"));
				cgu.setEmpName((String) jo.get("name"));
				cgu.setGid(groupId);
				cgu.setId(UuidGenerator.generate32UUID());
				departmentService.saveCommonGroupUsers(cgu);
			}
			ret = "ok";
		}else{
			ret = "personsNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取常用人员组下的人员
	 */
	public void getGroupUsers(){
		String groupId = this.getRequest().getParameter("groupId");
		String ret="";
		List<CommonGroupUsers> list = null;
		if(CommonUtil.stringNotNULL(groupId)){
			list = departmentService.findAllCommonGroupUsersByGid(groupId);
		}else{
			ret = "personsNone";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			if(list.isEmpty()){
				out.print(ret);
			}else{
				out.print(JSONArray.fromObject(list).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	//获取厂用组（废弃）
	public void getUserGroups(){
		getResponse().setCharacterEncoding("utf-8");
		String userId=getRequest().getParameter("userId");
		String mc=getRequest().getParameter("mc");
		String siteId = getRequest().getParameter("siteId");
		String role = getRequest().getParameter("role");//role=1为角色，role=0为常用组
		Employee emp=null;
		if(userId==null||"".equals(userId)){
 			emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
 		}else{
 			emp = employeeService.findEmployeeById(userId);
 		}
		JSONArray jsonTree = new JSONArray();
		// 生成JSON字符串
		List<CommonGroup> groupList = departmentService.findAllCommonGroupByUid(emp.getEmployeeGuid(),emp.getSiteId(),role);
		if(groupList != null && groupList.size()>0){
			for(int j = 0; j < groupList.size(); j++){
				CommonGroup group = groupList.get(j);
				List<CommonGroupUsers> userList=null;
				if(mc==null||"".equals(mc)){
					 userList = departmentService.findAllCommonGroupUsersByGid(group.getId());
				}else{
					 userList = departmentService.findAllCommonGroupUsersByGid(group.getId(),mc);
				}
				JSONObject jo = new JSONObject();
				jo.put("id", group.getId());
				jo.put("name", group.getName());
				JSONArray jsonTree2 = new JSONArray();
				if(userList!=null&&!userList.isEmpty()){
					for (int i = 0; i < userList.size(); i++) {
						JSONObject jo2 = new JSONObject();
						jo2.put("id", userList.get(i).getEmpId());
						jo2.put("name", userList.get(i).getEmpName());
						jsonTree2.add(jo2);
					}
					jo.put("empArr", jsonTree2);
				}
				jsonTree.add(jo);
			}
		}
		// 打开流
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	//常用组方法
	public void getUserGroups2(){
		getResponse().setCharacterEncoding("utf-8");
		String userId=getRequest().getParameter("userId");
		String role = getRequest().getParameter("role");//role=1为角色，role=0为常用组
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		JSONArray jsonTree = new JSONArray();
		// 生成JSON字符串
		List<CommonGroup> groupList = departmentService.findAllCommonGroupByUid(emp.getEmployeeGuid(),emp.getSiteId(),role);
		if(groupList != null && groupList.size()>0){
			for(int j = 0; j < groupList.size(); j++){
				CommonGroup group = groupList.get(j);
				List<CommonGroupUsers> userList = departmentService.findAllCommonGroupUsersByGid(group.getId());
				JSONObject jo = new JSONObject();
				jo.put("id", group.getId());
				jo.put("pId", null);
				jo.put("name", group.getName());
				jo.put("open", false);
				jo.put("type", "folder");
				if(userList!=null&&userList.isEmpty()){
					jo.put("chkDisabled", true);
				}else{
					jo.put("chkDisabled", false);
				}
				
				jsonTree.add(jo);
				if(userList!=null&&!userList.isEmpty()){
					for (int i = 0; i < userList.size(); i++) {
						JSONObject empJo = new JSONObject();
						empJo.put("id", userList.get(i).getEmpId());
						empJo.put("pId", group.getId());
						empJo.put("name", userList.get(i).getEmpName());
						empJo.put("type", "file");
						jsonTree.add(empJo);
					}
				}
			}
		}
		// 打开流
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
 			out.print(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	

	
	/**移动端，（发改委去掉树的跟部）
	 * 通过递归查询的方式，一条sql语句查询出全部的部门和人员
	 * 提高人员树查询性能
	 * @throws UnsupportedEncodingException 
	 */
	public void getFullJsonTree3() throws UnsupportedEncodingException{
		
		String isowner = this.getRequest().getParameter("isowner");
 		String userId = this.getRequest().getParameter("userId");
 		String siteId = this.getRequest().getParameter("siteId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		//siteId不一致使用兼职部门id
		if(!"".equals(emp.getSiteId())&&!"".equals(siteId)&&!(emp.getSiteId()).equals(siteId)){
			//获取兼职部门信息
			Map<String,String> map = departmentService.getPtJob(userId);
			//emp重新赋值
			String jobcode = map.get("jobcode");
			emp.setDepartmentGuid(jobcode);
		}
		
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = new StringBuffer("");
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}
			
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			
			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());
			
			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());
			
			List<Department> allDepts = null;
			if(deptsid != null){
				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);
				if(!allDepts.isEmpty()){
					for(Department dept:allDepts){
						if(dept!=null&& !("b8791369-9b41-47db-b701-33c57e75beda".equals(dept.getDepartmentGuid()))
								&&  !("07312d85-4700-4a1b-97ea-7906745932fb".equals(dept.getDepartmentGuid()))){
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
			}
			if(!allEmps.isEmpty()){
				for(Employee e:allEmps){
					if(e!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jo.put("tabIndex", e.getTabindex());
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			List<Object[]> allEmr = null;
			if(isowner!=null && isowner.equals("1")){
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
					//for 发改委
					if("00d45884-5293-46b9-a447-26a418b1de16".equals(department_rootId)){
						department_rootId=departmentService.findDepartmentById(department_rootId).getSuperiorGuid();//b8791369-9b41-47db-b701-33c57e75beda
					}//end
				} catch (Exception e) {
//					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
					department_rootId=emp.getSiteId();
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findDeptEmr(department_rootId);
			}else{
				//department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
					//for 发改委，市政协
					if("00d45884-5293-46b9-a447-26a418b1de16".equals(department_rootId)
							||"edbb155e-a070-482f-b480-9efb55fa5c09".equals(department_rootId)//判断发改委下属单位
							||"ae27e936-6b06-499e-b0d6-ea02ef994eb1".equals(department_rootId)//判断各区发改局
							||"9d13383d-dbd9-4cd6-a2ad-d57748a92f4d".equals(department_rootId)//判断发改委领导
							||"e74fa495-5a62-41f5-8335-c154d211f5bf".equals(department_rootId)//判断市政协下属单位
							||"297f27ba-9d64-445d-b79d-acc50795e4c5".equals(department_rootId)//判断市政协下属单位
							||"37df987b-9c0b-4305-aaa5-2495b6af2365".equals(department_rootId)//判断市政协下属单位
							||"193c7081-4cd4-4e96-a578-38d8739dd19d".equals(department_rootId)//判断市政协下属单位
							||"464d5345-d914-429e-9f8f-e90963167f02".equals(department_rootId)//判断残联残联单位
							||("13881694-265b-46bc-ae34-6a43f0b3ed98".equals(department_rootId)&&emp.getEmployeeGuid()!="5f54cc5d-f7b3-4171-9f97-856a3ddd1da9")//应急管理局、执法监督处 （监察支队）
							){
						department_rootId=departmentService.findDepartmentById(department_rootId).getSuperiorGuid();//b8791369-9b41-47db-b701-33c57e75beda\07312d85-4700-4a1b-97ea-7906745932fb
					}else if(emp.siteId.equals("b8791369-9b41-47db-b701-33c57e75beda")){
						department_rootId = "b8791369-9b41-47db-b701-33c57e75beda";
					}
				} catch (Exception e) {
//					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
					department_rootId=emp.getSiteId();
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
//				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findAllEmr();
			}
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					if(dept!=null&& !("b8791369-9b41-47db-b701-33c57e75beda".equals(dept.getDepartmentGuid()))
							&&  !("07312d85-4700-4a1b-97ea-7906745932fb".equals(dept.getDepartmentGuid()))){//去发改委市政协根节点
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("pId", dept.getSuperiorGuid());
						jo.put("name", dept.getDepartmentName());
						jo.put("open", false);
						jo.put("type", "folder");
						jo.put("isParent", true);
						jo.put("chkDisabled", false);
						jsonTree.add(jo);
						
						
						List<Employee> userList = employeeService.getAllChildEmpsByDeptId(dept.getDepartmentGuid());
						if(userList!=null&&!userList.isEmpty()){
							for (int i = 0; i < userList.size(); i++) {
								JSONObject empJo = new JSONObject();
								empJo.put("id", userList.get(i).getEmployeeGuid());
								empJo.put("pId", dept.getDepartmentGuid());
								empJo.put("name", userList.get(i).getEmployeeName());
								empJo.put("type", "file");
								empJo.put("tabIndex", userList.get(i).getTabindex());
								jsonTree.add(empJo);
							}
						}
					}
				}
			}
//			if(!allEmps.isEmpty()){
//				for(Employee e:allEmps){
//					if(e!=null){
//						JSONObject jo = new JSONObject();
//						jo.put("id", e.getEmployeeGuid());
//						jo.put("pId", e.getDepartmentGuid());
//						jo.put("name", e.getEmployeeName());
//						jo.put("type", "file");
//						jsonTree.add(jo);
//						if(allEmr.size()>0){
//							for(Object[] o:allEmr){
//								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
//									JSONObject jo2 = new JSONObject();
//									jo2.put("id", o[1]);
//									jo2.put("pId", o[2]);
//									jo2.put("name", e.getEmployeeName());
//									jo2.put("type", "file");
//									jo2.put("isJz", true);
//									jsonTree.add(jo2);
//								}
//							}
//						}
//					}
//				}
//			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**pc端，2018年5月28		同站点部门兼职
	 * 通过递归查询的方式，一条sql语句查询出全部的部门和人员
	 * 提高人员树查询性能
	 * @throws UnsupportedEncodingException 
	 */
	public void getFullJsonTree2() throws UnsupportedEncodingException{
		
		String isowner = this.getRequest().getParameter("isowner");
		String idsAdmin = this.getRequest().getParameter("idsAdmin");
 		String userId = this.getRequest().getParameter("userId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = new StringBuffer("");
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}
			
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			
			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());
			
			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());
			
			List<Department> allDepts = null;
			if(deptsid != null){
				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);
				if(!allDepts.isEmpty()){
					for(Department dept:allDepts){
						if(dept!=null&& !("d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d".equals(dept.getDepartmentGuid())) 
								&& !("b8791369-9b41-47db-b701-33c57e75beda".equals(dept.getDepartmentGuid())) 
								&&  !("07312d85-4700-4a1b-97ea-7906745932fb".equals(dept.getDepartmentGuid()))){//去掉南京市的根节点，去掉发改委根节点
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
			}
			if(!allEmps.isEmpty()){
				for(Employee e:allEmps){
					if(e!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jo.put("tabIndex", e.getTabindex());
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			List<Object[]> allEmr = null;
			if(isowner!=null && isowner.equals("1")){
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
					//for 发改委，市政协
					if("00d45884-5293-46b9-a447-26a418b1de16".equals(department_rootId)
							||"edbb155e-a070-482f-b480-9efb55fa5c09".equals(department_rootId)//判断发改委下属单位
							||"ae27e936-6b06-499e-b0d6-ea02ef994eb1".equals(department_rootId)//判断各区发改局
							||"9d13383d-dbd9-4cd6-a2ad-d57748a92f4d".equals(department_rootId)//判断发改委领导
							||"e74fa495-5a62-41f5-8335-c154d211f5bf".equals(department_rootId)//判断市政协下属单位
							||"297f27ba-9d64-445d-b79d-acc50795e4c5".equals(department_rootId)//判断市政协下属单位
							||"37df987b-9c0b-4305-aaa5-2495b6af2365".equals(department_rootId)//判断市政协下属单位
							||"193c7081-4cd4-4e96-a578-38d8739dd19d".equals(department_rootId)//判断市政协下属单位
							||"464d5345-d914-429e-9f8f-e90963167f02".equals(department_rootId)//判断残联残联单位
							||("13881694-265b-46bc-ae34-6a43f0b3ed98".equals(department_rootId)&&!"5f54cc5d-f7b3-4171-9f97-856a3ddd1da9".equals(emp.getEmployeeGuid()))//应急管理局、执法监督处 （监察支队）
					){
						department_rootId=departmentService.findDepartmentById(department_rootId).getSuperiorGuid();//b8791369-9b41-47db-b701-33c57e75beda
					}//end
				} catch (Exception e) {
//					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
					department_rootId=emp.getSiteId();
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
//				if("1".equals(idsAdmin)){
//					allEmps = employeeService.getAllChildEmpsByDeptIdForAdmin(department_rootId);
//				}else{//如果不是idsadmin,过滤测试组用户
//					allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
//				}
				allEmr = employeeService.findDeptEmr(department_rootId);
			}else{
				//department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
					//department_rootId=emp.getSiteId();
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
//				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findAllEmr();
			}
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					Boolean flag=true;
					if(!("1".equals(idsAdmin))){//如果不是idsadmin,过滤测试组
						flag=(dept.getDepartmentName().indexOf("测试组")==-1);
					}
					if(dept!=null&& !("d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d".equals(dept.getDepartmentGuid()))&&flag
							&&  !("b8791369-9b41-47db-b701-33c57e75beda".equals(dept.getDepartmentGuid()))&&  !("07312d85-4700-4a1b-97ea-7906745932fb".equals(dept.getDepartmentGuid()))){//去掉南京市的根节点  去掉发改委、市政协根节点
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("pId", dept.getSuperiorGuid());
						jo.put("name", dept.getDepartmentName());
						jo.put("open", false);
						jo.put("type", "folder");
						jo.put("isParent", true);
						jo.put("chkDisabled", false);
						jsonTree.add(jo);
						
						List<Employee> userList = null;
						if("1".equals(idsAdmin)){
							userList = employeeService.getAllChildEmpsByDeptIdForAdmin(dept.getDepartmentGuid());
						}else{//如果不是idsadmin,过滤测试组用户
							userList = employeeService.getAllChildEmpsByDeptId(dept.getDepartmentGuid());
						}
						if(userList!=null&&!userList.isEmpty()){
							for (int i = 0; i < userList.size(); i++) {
								JSONObject empJo = new JSONObject();
								empJo.put("id", userList.get(i).getEmployeeGuid());
								empJo.put("pId", dept.getDepartmentGuid());
								empJo.put("name", userList.get(i).getEmployeeName());
								empJo.put("type", "file");
								empJo.put("tabIndex", userList.get(i).getTabindex());
								jsonTree.add(empJo);
							}
						}
					}
				}
			}
//			if(!allEmps.isEmpty()){
//				for(Employee e:allEmps){
////					Boolean flag=true;
////					flag=(e.getEmployeeName().indexOf("管理员")==-1);
//					if(e!=null){//&&flag
//						JSONObject jo = new JSONObject();
//						jo.put("id", e.getEmployeeGuid());
//						jo.put("pId", e.getDepartmentGuid());
//						jo.put("name", e.getEmployeeName());
//						jo.put("type", "file");
//						jsonTree.add(jo);
//						if(allEmr.size()>0){
//							for(Object[] o:allEmr){
//								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
//									JSONObject jo2 = new JSONObject();
//									jo2.put("id", o[1]);
//									jo2.put("pId", o[2]);
//									jo2.put("name", e.getEmployeeName());
//									jo2.put("type", "file");
//									jo2.put("isJz", true);
//									jsonTree.add(jo2);
//								}
//							}
//						}
//					}
//				}
//			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	//TODO
	//改写前的备份
//public void getFullJsonTree2() throws UnsupportedEncodingException{
//		
//		String isowner = this.getRequest().getParameter("isowner");
//		String idsAdmin = this.getRequest().getParameter("idsAdmin");
// 		String userId = this.getRequest().getParameter("userId");
//		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
//		if(emp==null){
//			emp = employeeService.findEmployeeById(userId);
//		}
//		String mc=getRequest().getParameter("mc");
//		if(mc!=null&&!"".equals(mc)){
//			mc =URLDecoder.decode(mc,"utf-8");
//		}
//		StringBuffer userids = new StringBuffer("");
//		// 第一次加载树时，root="source",生成“大部门”的节点
//		JSONArray jsonTree = new JSONArray();
//		
//		if(mc!=null&&!"".equals(mc)){
//			List<Employee> list = employeeService.findEmployeesByMc(mc);
//			if(list!=null&&list.size()>0){
//				for(int i=0;i<list.size();i++){
//					Employee employee = list.get(i);
//					if(userids==null){
//						userids = new StringBuffer();
//						userids.append(employee.getEmployeeGuid());
//					}else{
//						userids.append(",").append(employee.getEmployeeGuid());
//					}
//				}
//			}
//			
//			String deptsid ="";
//			if(userids!=null){
//				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
//			}
//			
//			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());
//			
//			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());
//			
//			List<Department> allDepts = null;
//			if(deptsid != null){
//				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);
//				if(!allDepts.isEmpty()){
//					for(Department dept:allDepts){
//						if(dept!=null&& !("d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d".equals(dept.getDepartmentGuid()))){//去掉南京市的根节点
//							JSONObject jo = new JSONObject();
//							jo.put("id", dept.getDepartmentGuid());
//							jo.put("pId", dept.getSuperiorGuid());
//							jo.put("name", dept.getDepartmentName());
//							jo.put("open", false);
//							jo.put("type", "folder");
//							jo.put("isParent", true);
//							jo.put("chkDisabled", false);
//							jsonTree.add(jo);
//						}
//					}
//				}
//			}
//			if(!allEmps.isEmpty()){
//				for(Employee e:allEmps){
//					if(e!=null){
//						JSONObject jo = new JSONObject();
//						jo.put("id", e.getEmployeeGuid());
//						jo.put("pId", e.getDepartmentGuid());
//						jo.put("name", e.getEmployeeName());
//						jo.put("type", "file");
//						jsonTree.add(jo);
//						if(allEmr.size()>0){
//							for(Object[] o:allEmr){
//								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
//									JSONObject jo2 = new JSONObject();
//									jo2.put("id", o[1]);
//									jo2.put("pId", o[2]);
//									jo2.put("name", e.getEmployeeName());
//									jo2.put("type", "file");
//									jo2.put("isJz", true);
//									jsonTree.add(jo2);
//								}
//							}
//						}
//					}
//				}
//			}
//		}else{
//			String department_rootId = null;
//			List<Department> allDepts = null;
//			List<Employee> allEmps = null;
//			List<Object[]> allEmr = null;
//			if(isowner!=null && isowner.equals("1")){
//				try {
//					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
//					//for 发改委
//					if("00d45884-5293-46b9-a447-26a418b1de16".equals(department_rootId)){
//						department_rootId=departmentService.findDepartmentById(department_rootId).getSuperiorGuid();//b8791369-9b41-47db-b701-33c57e75beda
//					}//end
//				} catch (Exception e) {
//					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
//				}
//				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
//				if("1".equals(idsAdmin)){
//					allEmps = employeeService.getAllChildEmpsByDeptIdForAdmin(department_rootId);
//				}else{//如果不是idsadmin,过滤测试组用户
//					allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
//				}
//				allEmr = employeeService.findDeptEmr(department_rootId);
//			}else{
//				//department_rootId = emp.getSiteId();
//				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
//				try {
//					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
//					//for 发改委
//					if("00d45884-5293-46b9-a447-26a418b1de16".equals(department_rootId)){
//						department_rootId=departmentService.findDepartmentById(department_rootId).getSuperiorGuid();//b8791369-9b41-47db-b701-33c57e75beda
//					}//end
//				} catch (Exception e) {
//					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
//				}
//				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
//				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
//				allEmr = employeeService.findAllEmr();
//			}
//			if(!allDepts.isEmpty()){
//				for(Department dept:allDepts){
//					Boolean flag=true;
//					if(!("1".equals(idsAdmin))){//如果不是idsadmin,过滤测试组
//						flag=(dept.getDepartmentName().indexOf("测试组")==-1);
//					}
//					if(dept!=null&& !("d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d".equals(dept.getDepartmentGuid()))&&flag){//去掉南京市的根节点
//						JSONObject jo = new JSONObject();
//						jo.put("id", dept.getDepartmentGuid());
//						jo.put("pId", dept.getSuperiorGuid());
//						jo.put("name", dept.getDepartmentName());
//						jo.put("open", false);
//						jo.put("type", "folder");
//						jo.put("isParent", true);
//						jo.put("chkDisabled", false);
//						jsonTree.add(jo);
//					}
//				}
//			}
//			if(!allEmps.isEmpty()){
//				for(Employee e:allEmps){
////					Boolean flag=true;
////					flag=(e.getEmployeeName().indexOf("管理员")==-1);
//					if(e!=null){//&&flag
//						JSONObject jo = new JSONObject();
//						jo.put("id", e.getEmployeeGuid());
//						jo.put("pId", e.getDepartmentGuid());
//						jo.put("name", e.getEmployeeName());
//						jo.put("type", "file");
//						jsonTree.add(jo);
//						if(allEmr.size()>0){
//							for(Object[] o:allEmr){
//								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
//									JSONObject jo2 = new JSONObject();
//									jo2.put("id", o[1]);
//									jo2.put("pId", o[2]);
//									jo2.put("name", e.getEmployeeName());
//									jo2.put("type", "file");
//									jo2.put("isJz", true);
//									jsonTree.add(jo2);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		PrintWriter pw = null;
//		try {
//			pw = this.getResponse().getWriter();
//			pw.println(jsonTree.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			pw.flush();
//			pw.close();
//		}
//	}
	//新的树的数据方法
public void getFullJsonTree() throws UnsupportedEncodingException{
		
		String isowner = this.getRequest().getParameter("isowner");//是否是本部门，isowner=1为本部门
 		String userId = this.getRequest().getParameter("userId");//?
 		Employee emp = null;
 		if(userId==null||"".equals(userId)){
 			emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
 		}else{
 			emp = employeeService.findEmployeeById(userId);
 		}
		String siteId=emp.getSiteId();
		String departId=emp.getDepartmentGuid();
		String mc=getRequest().getParameter("mc");//获取搜索条件
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = new StringBuffer("");//用户ids
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list=null;
			//判断isowner，本部门 ，添加条件department
			if(isowner!=null && isowner.equals("1")){
				list=employeeService.findEmployeesByMc(mc,siteId,departId);//根据mingcheng条件查询用户
			}else{
				list = employeeService.findEmployeesByMc(mc,siteId);//根据mingcheng条件查询用户
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}
			
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());//根据用户查部门ids
			}
			
			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());//没用……
			
			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());//根据id查用户list……查了用户拿id，反过来又查用户…鸡肋
			
			
			//for 发改委
			String department_rootId = null;
			List<Department> allDepts = null;
			//for 发改委
			List<Department> secDepts = null;
			List<Department> thrDepts = new ArrayList<Department>();//end
			if(isowner!=null && isowner.equals("1")){
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
			}else{
				//department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
				}
				
			}
			
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					if(dept!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("name", dept.getDepartmentName());
						JSONArray jsonTree2 = new JSONArray();
						if(!allEmps.isEmpty()){
							for(Employee e:allEmps){
								if(e!=null&& e.getDepartmentGuid().equals(dept.getDepartmentGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", e.getEmployeeGuid());
									jo2.put("name", e.getEmployeeName());
									jsonTree2.add(jo2);
								}
							}
						}
						jo.put("empArr",jsonTree2 );
						jsonTree.add(jo);
					}
				}
			}
//			List<Department> allDepts = null;
//			if(deptsid != null){
//				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);//所有的部门list
//				if(!allDepts.isEmpty()){
//					for(Department dept:allDepts){
//						if(dept!=null){
//							JSONObject jo = new JSONObject();
//							jo.put("id", dept.getDepartmentGuid());
//							jo.put("name", dept.getDepartmentName());
//							JSONArray jsonTree2 = new JSONArray();
//							if(!allEmps.isEmpty()){
//								for(Employee e:allEmps){
//									if(e!=null&& e.getDepartmentGuid().equals(dept.getDepartmentGuid())){
//										JSONObject jo2 = new JSONObject();
//										jo2.put("id", e.getEmployeeGuid());
//										jo2.put("name", e.getEmployeeName());
//										jsonTree2.add(jo2);
//									}
//								}
//							}
//							jo.put("empArr",jsonTree2 );
//							jsonTree.add(jo);
//						}
//					}
//				}
//			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			List<Object[]> allEmr = null;
			//for 发改委
			List<Department> secDepts = null;
			List<Department> thrDepts = new ArrayList<Department>();
			//end
			if(isowner!=null && isowner.equals("1")){
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findDeptEmr(department_rootId);
			}else{
				//department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				
				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findAllEmr();
			}
			if(!allDepts.isEmpty()){
				for(Department dept:allDepts){
					if(dept!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", dept.getDepartmentGuid());
						jo.put("name", dept.getDepartmentName());
						JSONArray jsonTree2 = new JSONArray();
						if(!allEmps.isEmpty()){
							for(Employee e:allEmps){
								if(e!=null&& e.getDepartmentGuid().equals(dept.getDepartmentGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", e.getEmployeeGuid());
									jo2.put("name", e.getEmployeeName());
									jsonTree2.add(jo2);
								}
							}
						}
						jo.put("empArr",jsonTree2 );
						jsonTree.add(jo);
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 描述：获取所有的部门和人员
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2016-12-12 下午3:48:25
	 */
	public String getAllDepAndUser(){
		String accreditId = getRequest().getParameter("accreditId");
		getRequest().setAttribute("accreditId", accreditId);
		//String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		String department_rootId = "b47b209c-77ce-402e-b378-996ab8822262";
		/*List<Map<String, String>> list = departmentService.getAllDepAndUser(department_rootId, accreditId);
		getRequest().setAttribute("list", list);*/
		return "getAllDepAndUser";
	}
	
	
	/**
	 * 描述：获取被读日记人员树
	 * @throws UnsupportedEncodingException void
	 * 作者:蒋烽
	 * 创建时间:2017-1-3 下午4:57:06
	 */
	public void getJsonTree4Diary() throws UnsupportedEncodingException{
		String userId = this.getRequest().getParameter("userId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");                    	
		Department dep = departmentService.findDepartmentById(department_rootId);                                 	
		List<Employee> emps = employeeService.findShipByLeaderId(emp.getEmployeeGuid());                                    	
		if(null != dep && StringUtils.isNotBlank(dep.getDepartmentGuid())){                                                                                     	
			JSONObject jo = new JSONObject();                                                                	
			jo.put("id", dep.getDepartmentGuid());                                                          	
			jo.put("pId", dep.getSuperiorGuid());                                                           	
			jo.put("name", dep.getDepartmentName());                                                        	
			jo.put("open", false);                                                                           	
			jo.put("type", "folder");                                                                        	
			jo.put("isParent", true);                                                                        	
			jo.put("chkDisabled", false);                                                                    	
			jsonTree.add(jo);                                                                                	
		}                                                                                                            	
		if(null != emps && emps.size()>0){                                                                                      	
			for(Employee e:	emps){                                                                                 	
				if(e!=null){                                                                                         	
					JSONObject jo = new JSONObject();                                                            	
					jo.put("id", e.getEmployeeGuid());                                                           	
					jo.put("pId", department_rootId);                                                        	
					jo.put("name", e.getEmployeeName());                                                         	
					jo.put("type", "file");                                                                      	
					jo.put("isJz", true);                                                                        	
					jsonTree.add(jo);                                                                            	
				}                                                                                                    	
			}                                                                                                        	
		}                                                                                                            	
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	public String showDepartmentTree4Super() throws UnsupportedEncodingException{
		String nameType = this.getRequest().getParameter("nameType");
		String idType = this.getRequest().getParameter("idType");
		this.getRequest().setAttribute("idType", idType);
		this.getRequest().setAttribute("nameType", nameType);
		this.getRequest().setAttribute("idsAdmin", 1);
		String isowner = getRequest().getParameter("isowner");
		getRequest().setAttribute("isowner", isowner);
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		this.getRequest().setAttribute("department_rootId", department_rootId);
		return "departmentTree4Super";
	}
	
	/**
	 * 发改委人员树
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String showDepartmentTreeForFgw() throws UnsupportedEncodingException{
		String nameType = this.getRequest().getParameter("nameType");
		String idType = this.getRequest().getParameter("idType");
		String isXxzx = this.getRequest().getParameter("isXxzx");
		this.getRequest().setAttribute("idType", idType);
		this.getRequest().setAttribute("nameType", nameType);
		this.getRequest().setAttribute("isfgw", 1);
		this.getRequest().setAttribute("isXxzx", isXxzx);
		String isowner = getRequest().getParameter("isowner");
		getRequest().setAttribute("isowner", isowner);
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		this.getRequest().setAttribute("department_rootId", department_rootId);
		return "departmentTreeForFgw";
	}
	
	/**
	 * 发改委人员树
	 */
	public void getFullJsonTreeForFgw() throws UnsupportedEncodingException{
		
		String isowner = this.getRequest().getParameter("isowner");
 		String userId = this.getRequest().getParameter("userId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = new StringBuffer("");
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}
			
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			
			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());
			
			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());
			
			List<Department> allDepts = null;
			if(deptsid != null){
				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);
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
			}
			if(!allEmps.isEmpty()){
				for(Employee e:allEmps){
					if(e!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			List<Object[]> allEmr = null;
			if(isowner!=null && isowner.equals("1")){
//				try {
//					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
//				} catch (Exception e) {
					department_rootId = "b8791369-9b41-47db-b701-33c57e75beda";
//				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findDeptEmr(department_rootId);
			}else{
				//department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
//				try {
//					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getSuperiorGuid();
//				} catch (Exception e) {
					department_rootId = "b8791369-9b41-47db-b701-33c57e75beda";
//				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findAllEmr();
			}
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
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	
	/**
	 * 编办工作安排人员树
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String showDepartmentTreeForBb() throws UnsupportedEncodingException{
		String nameType = this.getRequest().getParameter("nameType");
		String idType = this.getRequest().getParameter("idType");
		this.getRequest().setAttribute("idType", idType);
		this.getRequest().setAttribute("nameType", nameType);
		String isowner = getRequest().getParameter("isowner");
		getRequest().setAttribute("isowner", isowner);
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		this.getRequest().setAttribute("department_rootId", department_rootId);
		return "departmentTreeForBb";
	}
	/**
	 * 编办工作安排人员树
	 */
	public void getFullJsonTreeForBb() throws UnsupportedEncodingException{
		
		String isowner = this.getRequest().getParameter("isowner");
		String userId = this.getRequest().getParameter("userId");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String depId_bbadmin = SystemParamConfigUtil2.getParamValueByParam("depId_bbadmin");
		String depId_bbLeader = SystemParamConfigUtil2.getParamValueByParam("depId_bbLeader");
		if(emp==null){
			emp = employeeService.findEmployeeById(userId);
		}
		String mc=getRequest().getParameter("mc");
		if(mc!=null&&!"".equals(mc)){
			mc =URLDecoder.decode(mc,"utf-8");
		}
		StringBuffer userids = new StringBuffer("");
		// 第一次加载树时，root="source",生成“大部门”的节点
		JSONArray jsonTree = new JSONArray();
		
		if(mc!=null&&!"".equals(mc)){
			List<Employee> list = employeeService.findEmployeesByMc(mc);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Employee employee = list.get(i);
					if(userids==null){
						userids = new StringBuffer();
						userids.append(employee.getEmployeeGuid());
					}else{
						userids.append(",").append(employee.getEmployeeGuid());
					}
				}
			}
			
			String deptsid ="";
			if(userids!=null){
				deptsid =employeeService.getDeptByEmployeeIds(userids.toString());
			}
			
			List<Object[]> allEmr = employeeService.findEmrById(userids.toString());
			
			List<Employee> allEmps = employeeService.getAllEmpsByUserIds(userids.toString());
			
			List<Department> allDepts = null;
			if(deptsid != null){
				allDepts = departmentService.getAllParentDeptBydeptId(deptsid);
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
			}
			if(!allEmps.isEmpty()){
				for(Employee e:allEmps){
					if(e!=null){
						JSONObject jo = new JSONObject();
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}else{
			String department_rootId = null;
			List<Department> allDepts = null;
			List<Employee> allEmps = null;
			List<Object[]> allEmr = null;
			if(isowner!=null && isowner.equals("1")){
				try {
					department_rootId = departmentService.findDepartmentById(emp.getDepartmentGuid()).getDepartmentGuid();
				} catch (Exception e) {
					department_rootId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
				}
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				if(allDepts !=null&&allDepts.size()>0){
					for(Department d : allDepts){
						//根据部门id获取所需的人员
						if(!depId_bbadmin.equals(d.getDepartmentGuid())){
							if(depId_bbLeader.equals(d.getDepartmentGuid())){
								//获取办领导全部人员
								List<Employee> emplist = employeeService.queryAllempByDepId(d.getDepartmentGuid());
								if(emplist != null&& emplist.size()>0){
									if(allEmps != null){
										for(Employee e : emplist){
												allEmps.add(e);
										}
									}else{
										allEmps = emplist;
									}
								}
							}else{
								//获取非办领导部门的第一位
								Employee employee = employeeService.queryEmpByDepId(d.getDepartmentGuid());
								allEmps.add(employee);
							}
						}
					}
				}
				//allEmps = employeeService.getAllChildEmpsByDeptId(department_rootId);
				allEmr = employeeService.findDeptEmr(department_rootId);
			}else{
				department_rootId = emp.getSiteId();
				//department_rootId = this.getRequest().getSession().getAttribute("siteId").toString();//SystemParamConfigUtil.getParamValueByParam("root_department_id");
				allDepts = departmentService.getAllChildDeptBydeptId(department_rootId);
				if(allDepts !=null&&allDepts.size()>0){
					for(Department d : allDepts){
						//根据部门id获取所需的人员
						if(!depId_bbadmin.equals(d.getDepartmentGuid())){
							if(depId_bbLeader.equals(d.getDepartmentGuid())){
								//获取办领导全部人员
								List<Employee> emplist = employeeService.queryAllempByDepId(d.getDepartmentGuid());
								if(emplist != null&& emplist.size()>0){
									if(allEmps != null){
										for(Employee e : emplist){
												allEmps.add(e);
										}
									}else{
										allEmps = emplist;
									}
								}
							}else{
								//获取非办领导部门的第一位
								Employee employee = employeeService.queryEmpByDepId(d.getDepartmentGuid());
								allEmps.add(employee);
							}
						}
					}
				}
				allEmr = employeeService.findAllEmr();
			}
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
						jo.put("id", e.getEmployeeGuid());
						jo.put("pId", e.getDepartmentGuid());
						jo.put("name", e.getEmployeeName());
						jo.put("type", "file");
						jsonTree.add(jo);
						if(allEmr.size()>0){
							for(Object[] o:allEmr){
								if(e.getEmployeeGuid()!=null&&(o[1]+"").equals(e.getEmployeeGuid())){
									JSONObject jo2 = new JSONObject();
									jo2.put("id", o[1]);
									jo2.put("pId", o[2]);
									jo2.put("name", e.getEmployeeName());
									jo2.put("type", "file");
									jo2.put("isJz", true);
									jsonTree.add(jo2);
								}
							}
						}
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
}
