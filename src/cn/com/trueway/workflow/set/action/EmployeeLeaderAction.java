package cn.com.trueway.workflow.set.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


import sun.misc.BASE64Decoder;

import net.sf.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.set.pojo.DepartmentLeader;
import cn.com.trueway.workflow.set.pojo.EmployeeLeaderShip;
import cn.com.trueway.workflow.set.service.EmployeeLeaderService;
import cn.com.trueway.workflow.set.service.EmployeeService;

/**
 * 
 * 描述：用户 领导管理模块
 * 作者：蔡亚军
 * 创建时间：2015-1-13 下午3:07:53
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class EmployeeLeaderAction extends BaseAction{

	private static final long serialVersionUID = -6551777376837095976L;
	
	private EmployeeLeaderService employeeLeaderService;
	
	private EmployeeService employeeService;
	
	private DictionaryService		dictionaryService;
	
	public EmployeeLeaderService getEmployeeLeaderService() {
		return employeeLeaderService;
	}

	public void setEmployeeLeaderService(EmployeeLeaderService employeeLeaderService) {
		this.employeeLeaderService = employeeLeaderService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/**
	 * 
	 * 描述：查询全部的部门以及领导关联关系
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-13 下午3:13:11
	 */
	public String getDepartmentLeaderList(){
		String leaderName = getRequest().getParameter("leaderName");
		String employeeName = getRequest().getParameter("employeeName");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = employeeLeaderService.findDepartmentLeaderCount(leaderName, employeeName);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<DepartmentLeader> list = employeeLeaderService
					.findDepartmentLeaderList(leaderName, employeeName, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("leaderName", leaderName);
		getRequest().setAttribute("employeeName", employeeName);
		return "leaderList";
	}
	
	/**
	 * 
	 * 描述：跳转到新增页面
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午9:17:48
	 */
	public String toAdd(){
		List<Map<String, String>> typeList = dictionaryService.getKeyAndValue("empType");
		getRequest().setAttribute("typeList", typeList);
		return "toAdd";
	}
	
	public String getLeaderTree(){
		return "leaderTree";
	}
	
	
	public void  saveEmployeeLeader(){
		String depId = getRequest().getParameter("depId");
		String depName = getRequest().getParameter("depName");
		String leaderId = getRequest().getParameter("leaderId");
		String leaderName = getRequest().getParameter("leaderName");
		String sortId = getRequest().getParameter("sortId");
		String employeeinfo = getRequest().getParameter("employeeinfo");
		String empType = getRequest().getParameter("empType");
		JSONObject error = employeeLeaderService
						.saveDepartmentLeader(depId, depName, leaderId, leaderName, sortId, employeeinfo, empType);
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			out = getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	public String toUpdate(){
		List<Map<String, String>> typeList = dictionaryService.getKeyAndValue("empType");
		
		String id = getRequest().getParameter("id");
		DepartmentLeader departmentLeader = employeeLeaderService.findDepartmentLeaderById(id);
		List<EmployeeLeaderShip> list = employeeLeaderService.findEmployeeLeaderShipList(id);
		getRequest().setAttribute("departmentLeader", departmentLeader);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("typeList", typeList);
		getRequest().setAttribute("id", id);
		return "toUpdate";
	}
	
	/**
	 * 
	 * 描述：修改办件
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午10:02:25
	 */
	public void updateEmployeeLeader(){
		String id = getRequest().getParameter("id");
		String depId = getRequest().getParameter("depId");
		String depName = getRequest().getParameter("depName");
		String leaderId = getRequest().getParameter("leaderId");
		String leaderName = getRequest().getParameter("leaderName");
		String sortId = getRequest().getParameter("sortId");
		String employeeinfo = getRequest().getParameter("employeeinfo");
		String empType = getRequest().getParameter("empType");
		JSONObject error = employeeLeaderService
				.updateDepartmentLeader(id, depId, depName, leaderId, leaderName, sortId, employeeinfo, empType);
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			out = getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 
	 * 描述：删除部门领导关系
	 * 作者:蔡亚军
	 * 创建时间:2015-1-14 上午9:08:23
	 */
	public void deleteEmployeeLeader(){
		String ids = getRequest().getParameter("ids");
		JSONObject error = employeeLeaderService.deleteEmployeeLeader(ids);
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			out = getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述：获取
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-4-27 下午3:37:39
	 */
	public String getEmployeeList(){
		String watermark = getRequest().getParameter("watermark");
		String employeeId = "";
		String time = "";
		if(watermark!=null && !watermark.equals("")){	
			try{
				String pass = new String((new BASE64Decoder()).decodeBuffer(watermark));
				if(pass!=null){
					String[] data = pass.split(" ");
					if(data!=null){
						if(data.length==2){
							employeeId = data[0];
							time = data[1];
						}
						if(data.length==1){
							employeeId = data[0];
						}
					}
				}
			}catch (Exception e) {
			}
		}
		String likeType = "0";
		if(time!=null){
			likeType = "1";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = employeeService.findEmployeeCount(employeeId, likeType);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Employee> list = employeeService.findEmployeeList(employeeId, likeType, Paging.pageIndex, Paging.pageSize);
		//解密移动端
		if(null == list || list.size()==0){
			if(watermark!=null && !watermark.equals("")){	
				try{
					String pass = new String((new BASE64Decoder()).decodeBuffer(watermark));
					if(pass!=null){
						String[] data = pass.split("\\*");
						if(data!=null){
							if(data.length==2){
								employeeId = data[0];
								time = data[1];
							}
							if(data.length==1){
								employeeId = data[0];
							}
						}
					}
				}catch (Exception e) {
				}
			}
			likeType = "0";
			if(time!=null){
				likeType = "1";
			}
			count = employeeService.findEmployeeCount1(employeeId, likeType);
			Paging.setPagingParams(getRequest(), pageSize, count);
			list = employeeService.findEmployeeList1(employeeId, likeType, Paging.pageIndex, Paging.pageSize);
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("time", time);
		//根据用户名查询
		getRequest().setAttribute("watermark", watermark);
		return "employeeList";
	}
}
