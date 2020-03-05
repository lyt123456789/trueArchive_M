package cn.com.trueway.workflow.set.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.CommonGroupUsers;
import cn.com.trueway.sys.pojo.SiteSource;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.DepartmentDao;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptVo;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.util.TxtUtil;

/**
 * @author 李伟
 * @version 创建时间：2009-11-27 下午09:00:28 类说明
 */
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDAO;

	public DepartmentDao getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDao departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public Department findDepartmentById(String departmentId) {
		return departmentDAO.queryDepartmentById(departmentId);
	}

	public Collection<Department> findDepartments(String superiorId) {
		return departmentDAO.queryDepartments(superiorId);
	}

	public Collection<Department> findDepartments() {
		return departmentDAO.queryDepartments();
	}

	public Department findDepartmentByName(String departmentName) {
		return departmentDAO.queryDepartmentByName(departmentName);
	}

	public Department findDepartmentAfterLogin(String loginName) {
		return departmentDAO.queryDepartmentAfterLogin(loginName);
	}

	public Collection<Department> queryDepartmentsLikeName(String name,
			int pageIndex, int pageSize) {
		return departmentDAO
				.queryDepartmentsLikeName(name, pageIndex, pageSize);
	}

	public Integer getDepartmentsLikeNameSize(String name) {
		return departmentDAO.getDepartmentsLikeNameSize(name);
	}

	/**
	 * 
	 * @Title: queryDepartmentListByDN
	 * @Description: 根据dn获取所有相关部门
	 * @param @param dn
	 * @param @return 设定文件
	 * @return List<Department> 返回类型
	 * @throws
	 */
	public List<Department> queryDepartmentListByDN(String dn) {
		List<Department> list = departmentDAO.queryDepartmentListByDN(dn);
		return list;
	}

	public List<Department> queryDepartmentsBySuperdepIds(String depIds) {
		return departmentDAO.queryDepartmentsBySuperdepIds(depIds);
	}

	public List<Employee> findEmployeeListByDepId(Department dep) {
		return departmentDAO.findEmployeeListByDepId(dep);
	}

	@Override
	public List<Department> queryDepartmentsBydepIds1(Department root,
			String deptsid) {
		if(null == root){
			return (new ArrayList<Department>());
		}
		List<Department> departments = (List<Department>) departmentDAO.queryDepartmentsByShortdnAndDept(root.getDepartmentShortdn(),deptsid);
		return departments;
	}

	@Override
	public Department queryDepartmentById(String departmentId)
			throws DataAccessException {
		return departmentDAO.queryDepartmentById(departmentId);
	}
	public List<SimpleDeptVo> getDeptInfo() {
		List<Object[]> depts = departmentDAO.getDeptInfo();
		List<SimpleDeptVo> vos = new ArrayList<SimpleDeptVo>();
		StringBuffer buffer = new StringBuffer(200*depts.size());
		String filePath = "";
		buffer.append("部门总数："+depts.size()+"\r\n");
		if(depts != null && depts.size()>0){
			for(int i = 0; i < depts.size(); i++){
				SimpleDeptVo vo = new SimpleDeptVo();
				vo.setDepartname(depts.get(i)[0].toString());
				vo.setDepartindex(depts.get(i)[1] == null ?0:Integer.valueOf(depts.get(i)[1].toString()));
				vo.setDeptid(depts.get(i)[2].toString());
				vo.setFdeptid(depts.get(i)[3] == null ?"":depts.get(i)[3].toString());
				vos.add(vo);
				buffer.append("序号："+(i+1)+",部门名称："+vo.getDepartname()+",部门排序号："+vo.getDepartindex()
						+",部门id="+vo.getDeptid()+",人员父id："+vo.getFdeptid()+"\r\n");
			}
		}
		TxtUtil txtUtil = new TxtUtil();
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhssmm");
		String fileName = sdf.format(new Date())+"_dep.txt";
		filePath = basePath+"/log/"+fileName;
		FileUploadUtils.mkDirectory(basePath+"/log/"); // 根据路径创建一系列的目录
		txtUtil.writeFile(filePath, buffer.toString());
		return vos;
	}

	@Override
	public List<Department> queryDepartmentListByShortDN(String dn) {
		return departmentDAO.queryDepartmentListByShortDN(dn);
	}

	@Override
	public Collection<Department> findAllChildDepList(String depId) {
		Department department = departmentDAO.queryDepartmentById(depId);
		Collection<Department> list = new ArrayList<Department>();
		list.add(department);
		if(department!=null){
			depId = department.getSuperiorGuid();
			Department dep = departmentDAO.queryDepartmentById(depId);
			addDepartmentToColl(dep, list);
		}
		return list;
	}

	public void addDepartmentToColl(Department dep, Collection<Department>  list){
		if(dep!=null){
			list.add(dep);
			Department p_dep = departmentDAO.queryDepartmentById(dep.getSuperiorGuid());
			if(p_dep!=null){
				addDepartmentToColl(p_dep , list);
			}
		}
	}

	
	@Override
	public String findDepIds(String depid) {
		boolean flag = true ;
		Department depart=null;
		String depids = "";
		while(flag){
			depart = departmentDAO.queryDepartmentById(depid);
			if(depart!=null){
				depids+= "'"+depid +"',";
				depid = depart.getSuperiorGuid();
				if(depid!=null && depid.equals("1")){
					flag = false;
				}
			}
		}
		if(depids!=null && depids.length()>0){
			depids = depids.substring(0,depids.length()-1);
		}
		return depids;
	}

	@Override
	public String findSencodDep(String depid) {
		boolean flag = true ;
		String depids = "";
		Department depart=null;
		while(flag){
			depart = departmentDAO.queryDepartmentById(depid);
			if(depart!=null){
				if(StringUtils.isNotBlank(depart.getSuperiorGuid()) && depid.equals(depart.getSuperiorGuid())){
					flag = false;
				}else{
					depids+= "'"+depid +"',";
					depid = depart.getSuperiorGuid();
					if(depid!=null && depid.equals("1")){
						flag = false;
					}
				}
			}
		}
		if(depids!=null && depids.length()>0){
			depids = depids.substring(0,depids.length()-1);
		}
		
		return depids;
	}

	@Override
	public Collection<Department> findThirdDepartments(String superiorId) {
		return departmentDAO.queryThirdDepartments(superiorId);
	}

	@Override
	public Department findSiteDept(String ids) {
		return departmentDAO.findSiteDept(ids);
	}

	@Override
	public List<Department> queryAllSite() {
		return departmentDAO.queryAllSite();
	}
	
	@Override
	public List<Department> getAllChildDeptBydeptId(String deptId) {
		return departmentDAO.getAllChildDeptBydeptId(deptId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	

	@Override
	public List<Object[]> findDepartmentList(Map<String, String> inmap) {
		return departmentDAO.findDepartmentList(inmap);
	}

	/* (non-Javadoc)
	 * @see cn.com.trueway.sys.service.DepartmentService#getDepartId(java.lang.String)
	 */
	@Override
	public String getDepartId(String id) {
		return departmentDAO.getDepartId(id);
	}
	
	@Override
	public List<Object[]> findAllChildDeptObjects(String departmentid)
			throws DataAccessException {
		return departmentDAO.findAllChildDeptObjects(departmentid);
	}
	
	@Override
	public CommonGroup saveCommonGroup(CommonGroup cg) {
		return departmentDAO.saveCommonGroup(cg);
	}

	@Override
	public void saveCommonGroupUsers(CommonGroupUsers cgu) {
		departmentDAO.saveCommonGroupUsers(cgu);
	}

	@Override
	public void deleteCommonGroup(CommonGroup cg) {
		departmentDAO.deleteCommonGroup(cg);
	}

	@Override
	public void deleteCommonGroupUsers(CommonGroupUsers cgu) {
		departmentDAO.deleteCommonGroupUsers(cgu);
	}

	@Override
	public void updateCommonGroup(CommonGroup cg) {
		departmentDAO.updateCommonGroup(cg);
	}

	@Override
	public void updateCommonGroupUsers(CommonGroupUsers cgu) {
		departmentDAO.updateCommonGroupUsers(cgu);
	}

	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid) {
		return departmentDAO.findAllCommonGroupByUid(uid);
	}
	
	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid,String siteId,String role) {
		return departmentDAO.findAllCommonGroupByUid(uid,siteId,role);
	}

	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid) {
		return departmentDAO.findAllCommonGroupUsersByGid(gid);
	}
	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid,String mc) {
		return departmentDAO.findAllCommonGroupUsersByGid(gid,mc);
	}

	@Override
	public CommonGroup findCommonGroupById(String id) {
		return departmentDAO.findCommonGroupById(id);
	}

	@Override
	public void deleteCommonGroupUsersByGid(String gid) {
		departmentDAO.deleteCommonGroupUsersByGid(gid);
	}
	
	@Override
	public List<Department> getAllParentDeptBydeptId(String deptsid) {
		return departmentDAO.getAllParentDeptBydeptId(deptsid);
	}


	@Override
	public List<Department> getAllChildDeptBydepts(String deptId) {
		return departmentDAO.getAllChildDeptBydepts(deptId);
	}

	/*@Override
	public List<Map<String, String>> getAllDepAndUser(String rootDepId, String accreditId){
		List<Map<String, String>> outList = new ArrayList<Map<String, String>>();
		List<Department> list = new ArrayList<Department>();
		Department dep = departmentDAO.queryDepartmentById(rootDepId);
		List<Department> oldList = new ArrayList<Department>();
		oldList.add(dep);
		getAllDep(list, oldList);
		for (Department department : list) {
			Map<String, String> depMap = new HashMap<String, String>();
			depMap.put("isForbidenGrant", "0");
			depMap.put("id", department.getDepartmentGuid());
			depMap.put("faterId", department.getSuperiorGuid());
			depMap.put("name", department.getDepartmentName());
			depMap.put("sort", null != department.getTabindex()?department.getTabindex().toString():"");
			depMap.put("havaChild", "true");
			outList.add(depMap);
			List<Employee> empList = employeeDao.findEmployeeListByDepId(department.getDepartmentGuid());
			for (Employee employee : empList) {
				List<DiaryAccredit> shipList = employeeDao.findShipById(accreditId, employee.getEmployeeGuid());
				String isGrant = "0";
				if(null != shipList && shipList.size()>0){
					isGrant = "1";
				}
				Map<String, String> empMap = new HashMap<String, String>();
				empMap.put("isForbidenGrant", "0");
				empMap.put("id", employee.getEmployeeGuid());
				empMap.put("faterId", employee.getDepartmentGuid());
				empMap.put("name", employee.getEmployeeName());
				empMap.put("sort", null != employee.getTabindex()?employee.getTabindex().toString():"");
				empMap.put("havaChild", "false");
				Department d = departmentDAO.queryDepartmentById(accreditId);
				if(null != d && StringUtils.isNotBlank(d.getDepartmentGuid())){
				}else{
					empMap.put("isGrant", isGrant);
				}
				outList.add(empMap);
			}
		}
		return outList;
	}*/
	
	private void getAllDep(List<Department> newList, List<Department> oldList){
		for (Department department : oldList) {
			newList.add(department);
			List<Department> depList = (List<Department>) departmentDAO.queryDepartments(department.getDepartmentGuid());
			if (depList != null && depList.size() > 0) {
				getAllDep(newList, depList);
			}
		}
	}

	@Override
	public void setSiteSource(SiteSource ss) {
		departmentDAO.mergeSiteSource(ss);
	} 
	
	public List<Object[]> getAllSiteName(){
		return departmentDAO.getAllSiteName();
	}
	
	public String getIdByDeptId(String deptId){
		return departmentDAO.getIdByDeptId(deptId);
	}
	
	public String getNameByDeptId(String deptId){
		return departmentDAO.getNameByDeptId(deptId);
	}
	
	public Map<String,String> getPtJob(String userId){
		return departmentDAO.getPtJob(userId);
	}

	/*@Override
	public List<Map<String, String>> getAllDepAndUser(String rootDepId, String accreditId){
		List<Map<String, String>> outList = new ArrayList<Map<String, String>>();
		List<Department> list = new ArrayList<Department>();
		Department dep = departmentDAO.queryDepartmentById(rootDepId);
		List<Department> oldList = new ArrayList<Department>();
		oldList.add(dep);
		getAllDep(list, oldList);
		for (Department department : list) {
			Map<String, String> depMap = new HashMap<String, String>();
			depMap.put("isForbidenGrant", "0");
			depMap.put("id", department.getDepartmentGuid());
			depMap.put("faterId", department.getSuperiorGuid());
			depMap.put("name", department.getDepartmentName());
			depMap.put("sort", null != department.getTabindex()?department.getTabindex().toString():"");
			depMap.put("havaChild", "true");
			outList.add(depMap);
			List<Employee> empList = employeeDao.findEmployeeListByDepId(department.getDepartmentGuid());
			for (Employee employee : empList) {
				List<DiaryAccredit> shipList = employeeDao.findShipById(accreditId, employee.getEmployeeGuid());
				String isGrant = "0";
				if(null != shipList && shipList.size()>0){
					isGrant = "1";
				}
				Map<String, String> empMap = new HashMap<String, String>();
				empMap.put("isForbidenGrant", "0");
				empMap.put("id", employee.getEmployeeGuid());
				empMap.put("faterId", employee.getDepartmentGuid());
				empMap.put("name", employee.getEmployeeName());
				empMap.put("sort", null != employee.getTabindex()?employee.getTabindex().toString():"");
				empMap.put("havaChild", "false");
				Department d = departmentDAO.queryDepartmentById(accreditId);
				if(null != d && StringUtils.isNotBlank(d.getDepartmentGuid())){
				}else{
					empMap.put("isGrant", isGrant);
				}
				outList.add(empMap);
			}
		}
		return outList;
	}*/
}
