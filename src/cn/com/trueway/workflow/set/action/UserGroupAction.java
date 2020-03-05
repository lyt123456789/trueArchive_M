package cn.com.trueway.workflow.set.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.UserGroup;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptAndEmpVo;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptVo;
import cn.com.trueway.workflow.set.pojo.vo.SimpleEmpVo;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.GroupService;

import com.google.gson.Gson;

public class UserGroupAction extends BaseAction{
	
	/** 
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
	 * @since JDK 1.6 
	 */
	private static final long 	serialVersionUID = 699491882869244233L;

	private GroupService 		groupService;
	
	private UserGroup 			userGroup;
	
	private EmployeeService 	employeeService;
	
	private DepartmentService 	departmentService;

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

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	/**
	 * 
	 * @Title: getInnerUserList
	 * @Description: 内置用户列表
	 * @return String    返回类型
	 */
	public String getUserGroupList(){
		Employee employee=getSession().getAttribute(MyConstants.loginEmployee)==null?null:(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String userid=employee==null?null:employee.getEmployeeGuid();
		
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
//		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String type=getRequest().getParameter("type");
		
		
		if(userGroup==null)userGroup=new UserGroup();
		userGroup.setUserid(userid);
		
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		
		
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=groupService.getUserGroupCountForPage(column, value, userGroup);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<UserGroup> list=groupService.getUserGroupListForPage(column, value, userGroup, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "getUserGroupList";
	}
	
	/**
	 * 判断当前流程下的用户组是否存在
	 * @throws IOException 
	 */
	public void isExistOfUserGroup() throws IOException{
//		String id = getRequest().getParameter("id");
//		String teamName = getRequest().getParameter("teamName");
//		String type = getRequest().getParameter("type");
//		String update = getRequest().getParameter("update");
//		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
//		//修改之前的组名
//		UserGroup userGroupIsHave = groupService.findUserGroupById(id);
//		List<UserGroup> list = groupService.getUserGroupList(lcid,type);
//		if(update!=null && !("").equals(update)){
//			if(userGroupIsHave != null){
//				for (int i=0;i < list.size();i++) {
//					if(list.get(i).getName().equals(userGroupIsHave.getName())){
//						list.remove(list.get(i));
//						break;
//					}
//				}
//			}
//		}
//		if(list!=null && list.size()!=0){
//			for (UserGroup userGroup : list) {
//				try {
//					if(teamName.equals(userGroup.getName())){
//						getResponse().getWriter().print("yes");
//					}
//				} catch (IOException e) {
//					getResponse().getWriter().print("no");
//				}
//			}
//		}else{
//			getResponse().getWriter().print("noExist");
//		}
	}
	
	/**
	 * 
	 * @Title: toAddUserGroupJsp
	 * @Description: 跳转新增内置用户
	 * @return String    返回类型
	 */
	public String toAddUserGroupJsp(){
		userGroup=null;//必须清空实体
		
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		return "toAddUserGroupJsp";
	}
	/**
	 * 
	 * @Title: addUserGroup
	 * @Description: 新增内置用户
	 * @return String    返回类型
	 */
	public String addUserGroup(){
		
		/************************新增用户组信息******************************/
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		userGroup.setIntime(CommonUtil.getNowTimeTimestamp(null));
		userGroup.setUserid(employee.getEmployeeGuid());
		String userids="";
		String employeeinfo=getRequest().getParameter("employeeinfo");
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					if (strs!=null&&strs.length==3) {
						userids+=i==employeeinfos.length-1?strs[0]:strs[0]+",";
					}
				}
			}
		}
		userGroup.setRelation_userids(userids);
		groupService.save(userGroup);
		return getUserGroupList();
	}
	/**
	 * 
	 * @Title: toUpdateUserGroupJsp
	 * @Description: 跳转更新内置用户
	 * @return String    返回类型
	 */
	public String toUpdateUserGroupJsp(){
		/************************查询用户组信息******************************/
		userGroup=null;//必须清空实体
		String id=getRequest().getParameter("id");
		userGroup=groupService.getOneUserGroupById(id);
		
		
		
		/************************查询用户组关联的人员信息******************************/
		//查询所有关联数据
		if (userGroup!=null) {
			String userids=userGroup.getRelation_userids();
			if (CommonUtil.stringNotNULL(userids)) {
				String[] arr=userids.split(",");
				List<Employee> employees=new ArrayList<Employee>();
				for (int i = 0; i < arr.length; i++) {
					Employee employee=employeeService.findEmployeeById(arr[i]);
					employee.setDepartment(departmentService.findDepartmentById(employee.getDepartmentGuid()));
					employees.add(employee);
				}
				userGroup.setEmployeeList(employees);
			}
		}
		getRequest().setAttribute("userGroup",userGroup );
		
		
		
		return "toUpdateUserGroupJsp";
	}
	/**
	 * 
	 * @Title: updateUserGroup
	 * @Description: 更新内置用户
	 * @return String    返回类型
	 */
	public String updateUserGroup(){
		
		/************************新增用户组信息******************************/
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		userGroup.setUpdatetime(CommonUtil.getNowTimeTimestamp(null));
		userGroup.setUserid(employee.getEmployeeGuid());
		String userids="";
		String employeeinfo=getRequest().getParameter("employeeinfo");
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					if (strs!=null&&strs.length==3) {
						userids+=i==employeeinfos.length-1?strs[0]:strs[0]+",";
					}
				}
			}
		}
		userGroup.setRelation_userids(userids);
		groupService.update(userGroup);
		return getUserGroupList();
	}
	/**
	 * 
	 * @Title: deleteUserGroup
	 * @Description: 删除内置用户
	 * @return String    返回类型
	 */
	public String deleteUserGroup(){
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				UserGroup user=new UserGroup();
				user.setId(idsArr[i]);
				groupService.delete(user);
			}
		}
		
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		return getUserGroupList();
	}
	
	/**
	 * 
	 * @Title: viewUserGroup
	 * @Description: 查看内置用户
	 * @return String    返回类型
	 */
	public String viewUserGroup(){
		String id=getRequest().getParameter("id");
		getRequest().setAttribute("userGroup", groupService.getOneUserGroupById(id));
		return "viewUserGroup";
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
		userGroup=groupService.getOneUserGroupById(id);
		getRequest().setAttribute("userGroup",userGroup );
		
		//查询所有关联数据
//		getRequest().setAttribute("mapList", groupService.getListByUserGroupId(id));
		
		return "toMapEmployeeJsp";
	}
	
	public String addMapEmployeeInfo(){
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
		}
		String inneruserid=getRequest().getParameter("id");
		
		//先删除原先关联数据
//		groupService.deleteByUserGroupId(inneruserid);
		
		//新增新关联数据
		String employeeinfo=getRequest().getParameter("employeeinfo");
		if (CommonUtil.stringNotNULL(employeeinfo)) {
			String[] employeeinfos=employeeinfo.split("#");
			if (employeeinfos!=null) {
				for (int i = 0; i < employeeinfos.length; i++) {
					String[] strs=employeeinfos[i].split("\\|");
					if (strs!=null&&strs.length==3) {
//						UserGroupMapEmployee m=new UserGroupMapEmployee();
//						m.setInneruser_id(inneruserid);
//						m.setEmployee_id(strs[0]);
//						m.setEmployee_name(strs[1]);
//						m.setEmployee_shortdn(strs[2]);
//						m.setZindex(i);
//						groupService.save(m);
					}
					
				}
			}
		}
		
		return getUserGroupList();
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
				UserGroup userGroup=new UserGroup();
//				userGroup.setType(Integer.parseInt(type));
				List<UserGroup> list=groupService.getUserGroupListForPage(null, null, userGroup, null, null);
				String str="";
				if (list!=null) {
					for (int i = 0; i < list.size(); i++) {
						str+=list.get(i).getName()+','+list.get(i).getId()+";";
					}
				}
				Gson g=new  Gson();
				out.write(str);
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
	}
	
	public String toUserGroupJsp(){
		/************************查询用户组信息******************************/
		userGroup=null;//必须清空实体
		String id=getRequest().getParameter("id");
		userGroup=groupService.getOneUserGroupById(id);
		
		String type=getRequest().getParameter("type");
		
		
		/************************查询用户组关联的人员信息******************************/
		//查询所有关联数据
		if (userGroup!=null) {
			String userids=userGroup.getRelation_userids();
			if (CommonUtil.stringNotNULL(userids)) {
				String[] arr=userids.split(",");
				List<Employee> employees=new ArrayList<Employee>();
				for (int i = 0; i < arr.length; i++) {
					Employee employee=employeeService.findEmployeeById(arr[i]);
					employee.setDepartment(departmentService.findDepartmentById(employee.getDepartmentGuid()));
					employees.add(employee);
				}
				userGroup.setEmployeeList(employees);
			}
		}
		getRequest().setAttribute("userGroup",userGroup );
		getRequest().setAttribute("routType",type );
		return "toUserGroupJsp";
	}
	
	/**
	 * 
	 * 描述：导入用户到中威通讯录中
	 * 作者:蔡亚军
	 * 创建时间:2015-4-7 下午3:49:16
	 */
	public void getUserInfo(){
		PrintWriter out = null;
		String jsonStr ="";
		int state =0;
		try {
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			List<SimpleEmpVo> empVos = employeeService.getUserInfo();
			List<SimpleDeptVo> deptVos = departmentService.getDeptInfo();
			SimpleDeptAndEmpVo vo = new SimpleDeptAndEmpVo();
			vo.setDepts(deptVos);
			vo.setEmps(empVos);
			String cId = SystemParamConfigUtil.getParamValueByParam("cId");
			String vId = SystemParamConfigUtil.getParamValueByParam("vId");
			vo.setCid(Integer.parseInt(cId));
			vo.setVid(Integer.parseInt(vId));
			jsonStr = JSONObject.fromObject(vo).toString();
			HttpClient client = new HttpClient();
			String postMethodUrl = SystemParamConfigUtil.getParamValueByParam("postMethodUrl");
			PostMethod post = new PostMethod(postMethodUrl);
			post.setRequestHeader("Content-Type","application/json;charset=utf-8");
			post.setRequestBody(jsonStr);
			state = client.executeMethod(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print("{\"state\":"+state+"}");
		out.close();
	}
	
	public static String read(){
		File file = new File("C:\\Users\\Trueway\\Desktop\\dept&emp.txt");
		
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String data = null;
			
			while((data = br.readLine())!= null) {
				sb.append(data);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
