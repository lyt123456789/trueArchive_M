package cn.com.trueway.sys.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Bean;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.sys.pojo.vo.PYBean;
import cn.com.trueway.sys.util.PinYin4jUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.EmployeeSpell;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.EmployeeSpellService;

/**
 * 
 * 描述：组织架构操作类
 * 作者：蔡亚军
 * 创建时间：2016-3-14 下午07:14:34
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class OrganizeAction extends BaseAction{

	private static final long serialVersionUID = 3008532166667258707L;
	
	private DepartmentService  departmentService;
	
	private EmployeeService  employeeService;
	
	private EmployeeSpellService employeeSpellService;

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
	
	public EmployeeSpellService getEmployeeSpellService() {
		return employeeSpellService;
	}
	
	public void setEmployeeSpellService(
			EmployeeSpellService employeeSpellService) {
		this.employeeSpellService = employeeSpellService;
	}
	
	public String getOrganizeList(){
		return "organizeList";	
	}
	
	/**
	 * 
	 * 描述：获取系统内人员组织架构树
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 上午09:16:40
	 */
	public void getDeptTreeJson(){
		HttpServletRequest request = getRequest();
		String pid = request.getParameter("pid");//异步加载获取pid
		String chked = request.getParameter("chked");//被选数据
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String zzjgName = request.getParameter("zzjgName");
		Map<String,String> inmap = new HashMap<String, String>();
		inmap.put("code", code);
		inmap.put("name", name);
		inmap.put("zzjgName", zzjgName);
		inmap.put("status", "0");//逻辑删除
		String siteId;
		try {
			siteId = this.getRequest().getSession().getAttribute("siteId").toString();//2017-10-10，ids账号登进来没有siteId
		} catch (Exception e) {
			siteId = "d5e3c675-ea8b-4cf6-84cd-7eb4b2958c4d";
		}
		List<Department> allDepts = departmentService.getAllChildDeptBydeptId(siteId);
		//List<Object[]> list = departmentService.findDepartmentList(inmap);//数据集
		//递归树---此处由于是根据科目代码进行生存树且数据库没有父子关系，故此无法使用Oracle进行递归
		JSONArray jsonArr = new JSONArray();
		for(Department os : allDepts){
			JSONObject json = new JSONObject();
			json.accumulate("id", os.getDepartmentGuid());
			json.accumulate("pId", os.getSuperiorGuid());
			json.accumulate("name",os.getDepartmentName());
			json.accumulate("open", false);
			json.accumulate("dataid", os.getDepartmentGuid());
			json.accumulate("checked", (chked+"").contains(os.getDepartmentGuid()+""));
			jsonArr.add(json);
		}
		this.outWirter(jsonArr, getResponse());
	}
	
	
	/**
	 * 
	 * 描述：根据所属
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-15 上午09:30:31
	 */
	public String getEmployeeList(){
		List<Object[]>  list = null;
		String depId = getRequest().getParameter("depId");
		getRequest().setAttribute("depId", depId);
		if (StringUtils.isNotBlank(depId)) {
			Department dep = departmentService.findDepartmentById(depId);
			List<Department> depLists = departmentService.queryDepartmentListByDN(dep.getDepartmentHierarchy());
			String depIds = "(";
			for(int i=0;i<depLists.size();i++){
				if(i==0){
					depIds += "'"+depLists.get(i).getDepartmentGuid()+"'";
				}else{
					depIds += ",'"+depLists.get(i).getDepartmentGuid()+"'";
				}
			}
			depIds += ")";
			
			String name = getRequest().getParameter("name");
			getRequest().setAttribute("name", name);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("depIds", depIds);
			list = employeeService.findEmps(map);
		
//			list = employeeService.findEmployeeListByDepId(depId);
		}
//		String name = getRequest().getParameter("name");
//		if (StringUtils.isNotBlank(name)) {
//			list = employeeService.queryEmpByName(name);
//		}
		getRequest().setAttribute("list", list);
		return "empList";
	}
	
	
	public void initSpell(){
		List<PYBean> pyList = new ArrayList<PYBean>();
		List<Employee>  list = null;
		String depId = getRequest().getParameter("depId");
		getRequest().setAttribute("depId", depId);
		if (StringUtils.isNotBlank(depId)) {
			Department dep = departmentService.findDepartmentById(depId);
			List<Department> depLists = departmentService.queryDepartmentListByDN(dep.getDepartmentHierarchy());
			String depIds = "(";
			for(int i=0;i<depLists.size();i++){
				if(i==0){
					depIds += "'"+depLists.get(i).getDepartmentGuid()+"'";
				}else{
					depIds += ",'"+depLists.get(i).getDepartmentGuid()+"'";
				}
			}
			depIds += ")";
			String name = getRequest().getParameter("name");
			getRequest().setAttribute("name", name);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("depIds", depIds);
			list = employeeService.findEmpList(map);
		}
		for(int i=0;i<list.size();i++){
			Employee emp = list.get(i);
			
			PYBean bean = new PYBean();
			bean.setSfdy("no");
			bean.setName(emp.getEmployeeName());
			bean.setId(emp.getEmployeeGuid());
			
			
			bean = PinYin4jUtil.getPinYin(bean);
			bean = PinYin4jUtil.getPinYinHeadChar(bean);
			
			if("is".equals(bean.getSfdy())){
				pyList.add(bean);
			}else{
				List<EmployeeSpell> empSpellList = employeeSpellService
						.findEmployeeSpellByEmpGuid(emp.getEmployeeGuid());
				if (empSpellList.size() == 0) {
					EmployeeSpell empSpell = new EmployeeSpell();
					empSpell.setEmployeeGuid(emp.getEmployeeGuid());
					empSpell.setEmployeeName(emp.getEmployeeName());
					empSpell.setSpell(bean.getPinyin());
					empSpell.setSpellhead(bean.getPyHead());
					employeeSpellService.saveEmployeeSpell(empSpell);
				}else{
					EmployeeSpell empSpell = empSpellList.get(0);
					empSpell.setEmployeeGuid(emp.getEmployeeGuid());
					empSpell.setEmployeeName(emp.getEmployeeName());
					empSpell.setSpell(bean.getPinyin());
					empSpell.setSpellhead(bean.getPyHead());
					employeeSpellService.updateEmployeeSpell(empSpell);
				}
			}
		}
		getSession().setAttribute("pyList", pyList);
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			getResponse().setCharacterEncoding("utf-8");
			if(pyList.size()>0){
				pw.write("czdy");
			}else{
				pw.write("true");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		}
	}
	
	public String selectDY(){
		List<PYBean> list = (List<PYBean>) getSession().getAttribute("pyList");
		getRequest().setAttribute("list", list);
		Map<String, List<String>> mapAll = new HashMap<String, List<String>>();
		for(int i=0;i<list.size();i++){
			Map<String, List<String>> map = list.get(i).getMap();
			mapAll.putAll(map);
		}
		getRequest().setAttribute("mapAll", mapAll);
		return "selectDY";
	}
	
	public String selectDY1(){
		List<PYBean> list = (List<PYBean>) getSession().getAttribute("pyList");
		getRequest().setAttribute("list", list);
		
		return "selectDY1";
	}
	
	public void sureDY() throws BadHanyuPinyinOutputFormatCombination {
		List<PYBean> list = (List<PYBean>) getSession().getAttribute("pyList");
		getRequest().setAttribute("list", list);
		Map<String, List<String>> mapAll = new HashMap<String, List<String>>();
		Map<String,String> mapDic = new HashMap<String, String>(); 
		for (int i = 0; i < list.size(); i++) {
			Map<String, List<String>> map = list.get(i).getMap();
			mapAll.putAll(map);
		}
		Iterator<Entry<String, List<String>>> it = mapAll.entrySet().iterator();
	      while(it.hasNext()){
	        Entry<String, List<String>> entry = it.next();
	        String key = entry.getKey();
	        String pingyin = getRequest().getParameter(key);
	        mapDic.put(key, pingyin);
	      }
	     
		for (int i = 0; i < list.size(); i++) {
			PYBean bean = list.get(i);
			bean = PinYin4jUtil.getPinYinNew(bean,mapDic);
			bean = PinYin4jUtil.getPinYinHeadCharNew(bean,mapDic);

			List<EmployeeSpell> empSpellList = employeeSpellService
					.findEmployeeSpellByEmpGuid(bean.getId());
			if (empSpellList.size() == 0) {
				EmployeeSpell empSpell = new EmployeeSpell();
				empSpell.setEmployeeGuid(bean.getId());
				empSpell.setEmployeeName(bean.getName());
				empSpell.setSpell(bean.getPinyin());
				empSpell.setSpellhead(bean.getPyHead());
				employeeSpellService.saveEmployeeSpell(empSpell);
			} else {
				EmployeeSpell empSpell = empSpellList.get(0);
				empSpell.setEmployeeGuid(bean.getId());
				empSpell.setEmployeeName(bean.getName());
				empSpell.setSpell(bean.getPinyin());
				empSpell.setSpellhead(bean.getPyHead());
				employeeSpellService.updateEmployeeSpell(empSpell);
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			getResponse().setCharacterEncoding("utf-8");
			pw.write("true");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		} 
	}
	
	public void initSpellNew(){
		List<Employee>  list = null;
		String depId = getRequest().getParameter("depId");
		getRequest().setAttribute("depId", depId);
		if (StringUtils.isNotBlank(depId)) {
			Department dep = departmentService.findDepartmentById(depId);
			List<Department> depLists = departmentService.queryDepartmentListByDN(dep.getDepartmentHierarchy());
			String depIds = "(";
			for(int i=0;i<depLists.size();i++){
				if(i==0){
					depIds += "'"+depLists.get(i).getDepartmentGuid()+"'";
				}else{
					depIds += ",'"+depLists.get(i).getDepartmentGuid()+"'";
				}
			}
			depIds += ")";
			
			String name = getRequest().getParameter("name");
			getRequest().setAttribute("name", name);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("depIds", depIds);
			list = employeeService.findEmpList(map);
		}
		for(int i=0;i<list.size();i++){
			Employee emp = list.get(i);
			String empName = emp.getEmployeeName();
			
			List<String> pingyins = PinYin4jUtil.getPinYin(empName);
			
			List<EmployeeSpell> empSpellList = employeeSpellService
					.findEmployeeSpellByEmpGuid(emp.getEmployeeGuid());
			if (empSpellList.size() == 0) {
//				EmployeeSpell empSpell = new EmployeeSpell();
//				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
//				empSpell.setEmployeeName(emp.getEmployeeName());
//				empSpell.setSpell(spell);
//				empSpell.setSpellhead(spellhead);
//				employeeSpellService.saveEmployeeSpell(empSpell);
			}else{
//				EmployeeSpell empSpell = empSpellList.get(0);
//				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
//				empSpell.setEmployeeName(emp.getEmployeeName());
//				empSpell.setSpell(spell);
//				empSpell.setSpellhead(spellhead);
//				employeeSpellService.updateEmployeeSpell(empSpell);
				employeeSpellService.deleteByEmpId(emp.getEmployeeGuid());
			}
			
			for(int j=0;j<pingyins.size();j++){
				String pingyin = pingyins.get(j);
				String[] strs = pingyin.split(",");
				String spell = "";
				String spellHead = "";
				for(int a=0;a<strs.length;a++){
					spell += strs[a];
					spellHead += strs[a].charAt(0);
				}
				EmployeeSpell empSpell = new EmployeeSpell();
				empSpell.setEmployeeGuid(emp.getEmployeeGuid());
				empSpell.setEmployeeName(emp.getEmployeeName());
				empSpell.setSpell(spell);
				empSpell.setSpellhead(spellHead);
				employeeSpellService.saveEmployeeSpell(empSpell);
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			getResponse().setCharacterEncoding("utf-8");
			pw.write("true");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		}
	}
}
