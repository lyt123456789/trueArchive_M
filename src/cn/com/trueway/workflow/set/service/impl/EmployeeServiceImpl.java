package cn.com.trueway.workflow.set.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.sys.pojo.FeedBack;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.pojo.EmployeeSpe;
import cn.com.trueway.workflow.set.pojo.vo.SimpleEmpVo;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.util.TxtUtil;


/**
 * 人员管理的Service层实现类
 * 
 * @author liwei
 * 
 */
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}


	public Employee findEmployeeById(String userId) {
		return employeeDao.queryEmployeeById(userId);
	}

	

	
	public Collection<Employee> findEmployees(String departmentId) {
		return employeeDao.queryEmployees(departmentId);
	}
	
	public Collection<Employee> findEmployeesByIds(String departmentId,String ids) {
		return employeeDao.queryEmployeesByIds(departmentId,ids);
	}
	/**
	 * 
	 * @Title: queryEmployeeByDN
	 * @Description: 根据dn获取所有用户
	 * @param @param dn
	 * @param @return
	 * @param @throws DataAccessException    设定文件
	 * @return List<Employee>    返回类型
	 * @throws
	 */
	public List<EmployeeSpe> queryEmployeeByDN(String dn) throws DataAccessException {
		
		
		List<EmployeeSpe> list=employeeDao.queryEmployeeByDN(dn);
		return list;
		
		
	}


	public List<EmployeeSpe> queryEmployeeByDepIds(String depids)
			throws DataAccessException {
		return employeeDao.queryEmployeeByDepIds(depids);
	}


	public List<EmployeeSpe> queryEmployeeBySuperDepIds(String depids)
			throws DataAccessException {
		return employeeDao.queryEmployeeBySuperDepIds(depids);
	}


	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(
			String departmentid) throws DataAccessException {
		return employeeDao.getAllEmployeeInfoBySuperDepartmentId(departmentid);
	}

	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(
			String departmentid,String userids) throws DataAccessException {
		return employeeDao.getAllEmployeeInfoBySuperDepartmentId(departmentid,userids);
	}

	public List<Object[]> getEmployeeInfoByEmployeeId(String employeeId)
			throws DataAccessException {
		return employeeDao.getEmployeeInfoByEmployeeId(employeeId);
	}


	public List<Employee> getEmployeeByUsernameAndPassword(String username,
			String password) throws DataAccessException {
		return employeeDao.getEmployeeByUsernameAndPassword(username, password);
	}


	@Override
	public String getDeptByEmployeeIds(String userids) {

		//获取所有的人员信息
		List<Object> list = employeeDao.getEmployeeDeptIdByEmployeeIds(userids);
		//获取所有的部门信息
		List<Object> deptList = employeeDao.getDepartmentIdByDepartmentId(userids);
		String dept=null;
		if(deptList!=null){
			for(int i=0;i<deptList.size();i++){
				String deptId  = (String) deptList.get(i);
				if(dept!=null){
					dept +=",";
				}else{
					dept ="";
				}
				dept+=deptId;
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				String deptId = (String) list.get(i);
				if(dept!=null){
					dept +=",";
				}else{
					dept ="";
				}
				dept+=deptId;
			}
		}
		return dept;
	}


	@Override
	public List<Employee> findEmployeesByMc(String mc) {
		return employeeDao.findEmployeesByMc(mc);
	}


	@Override
	public List<Employee> getEmployeesByIds(String relation_userids,String mc) {
		return employeeDao.getEmployeeByEmployeeIds(relation_userids,mc);
	}


	@Override
	public List<Employee> getEmployeeByUsername(String mc) {
		return  employeeDao.getEmployeeByUsername(mc);
	}


	@Override
	public List<Employee> getEmployeeList(String ids) {
		return employeeDao.getEmployeeList(ids);
	}
	
	@Override
	public List<Object[]> getEmployeeInfoByCylxrId(String id) {
		return employeeDao.getEmployeeInfoByCylxrId(id);
	}


	@Override
	public List<Object> getUsers(String id) {
		return employeeDao.getUsers(id);
	}


	@Override
	public String getUserNameAndDept(String id) {
		if(id==null || id.equals("")){
			return "";
		}
		String[] ids = id.split(",");
		String searId = "";
		for(int i=0; i<ids.length; i++){
			searId += "'"+ids[i]+"',";
		}
		if(searId!=null && searId.length()>0){
			searId = searId.substring(0 , searId.length()-1);
		}
		
		List<Object[]> objs =  employeeDao.getUserNameAndDeptList(searId);
		String ret = "[";
		for(int i=0; i<objs.size(); i++){
			Object[] obj = objs.get(i);
			ret += "{\"employeeName\":\""+obj[0]+"\",\"departmentName\":\""+obj[1]+"\"}";
			if( i!= objs.size()-1){
				ret += ",";
			}
		}
		ret += "]";
		/*if(objs.size()> 0 ){
			Object[] obj = objs.get(0);
		   ret = "{\"employeeName\":\""+obj[0]+"\",\"departmentName\":\""+obj[1]+"\"}";
		}*/
		return ret;
	}
	
	public List<SimpleEmpVo> getUserInfo() {
		List<Object[]> emps =	employeeDao.getUserInfo();
		List<SimpleEmpVo> vos = new ArrayList<SimpleEmpVo>();
		StringBuffer buffer = new StringBuffer(400*emps.size());
		String filePath = "";
		buffer.append("人员总数："+emps.size()+"\r\n");
		if(emps != null && emps.size()>0){
			for(int i = 0; i < emps.size(); i++){
				SimpleEmpVo vo = new SimpleEmpVo();
				vo.setName(emps.get(i)[0].toString());
				vo.setDeptId(emps.get(i)[1] == null ?"":emps.get(i)[1].toString());
				vo.setJob(emps.get(i)[2] == null ?"":emps.get(i)[2].toString());
				vo.setUname(emps.get(i)[3] == null ?"":emps.get(i)[3].toString());
				vo.setOfficephone(emps.get(i)[4] == null ?"":emps.get(i)[4].toString());
				vo.setFax(emps.get(i)[5] == null ?"":emps.get(i)[5].toString());
				vo.setMobile(emps.get(i)[6] == null ?"":emps.get(i)[6].toString());
				vo.setHomephone(emps.get(i)[7] == null ?"":emps.get(i)[7].toString());
				vo.setMail(emps.get(i)[8] == null ?"":emps.get(i)[8].toString());
				vo.setUserId(emps.get(i)[9] == null ?"":emps.get(i)[9].toString());
				String tabIndex = emps.get(i)[10] == null ?"":emps.get(i)[10].toString();
				if(tabIndex!=null && !tabIndex.equals("")){
					vo.setUserindex(Integer.parseInt(tabIndex));
				}
				vos.add(vo);
				buffer.append("序号："+(i+1)+",登录名："+vo.getUname()+",姓名："+vo.getName()+",id="+vo.getUserId()+",人员排序号："+vo.getUserindex())
				  .append(",部门id："+vo.getDeptId()+",职务："+vo.getJob()+"\r\n");
			}
		}
		TxtUtil txtUtil = new TxtUtil();
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhssmm");
		String fileName = sdf.format(new Date())+"_user.txt";
		filePath = basePath+"/log/"+fileName;
		FileUploadUtils.mkDirectory(basePath+"/log/"); // 根据路径创建一系列的目录
		txtUtil.writeFile(filePath, buffer.toString());
		return vos;
	}
	
	@Override
	public Employee getEmployeeByLoginName(String loginName) {
		return employeeDao.getEmployeeByLoginName(loginName);
	}


	@Override
	public String getUserNamesByIds(String ids) {
		// TODO Auto-generated method stub
		List<Object> list = employeeDao.getUserNamesByIds(ids);
		String userNames = "";
		if( list!= null && list.size()>0){
			for(int i = 0 ; i < list.size(); i++){
				userNames +=list.get(i).toString()+",";
			}
			if(userNames.length()>0){
				userNames = userNames.substring(0,userNames.length()-1);
			}
		}
		return userNames;
	}

	@Override
	public List<Employee> findEmployeeList(String loginName, String likeType, Integer pageIndex,Integer pageSize) {
		return employeeDao.findEmployeeList(loginName, likeType, pageIndex, pageSize);
	}

	@Override
	public List<Employee> findEmployeeList1(String loginName, String likeType, Integer pageIndex,Integer pageSize) {
		return employeeDao.findEmployeeList1(loginName, likeType, pageIndex, pageSize);
	}

	@Override
	public int findEmployeeCount(String loginName,  String likeType) {
		return employeeDao.findEmployeeCount(loginName, likeType);
	}
	
	@Override
	public int findEmployeeCount1(String loginName,  String likeType) {
		return employeeDao.findEmployeeCount1(loginName, likeType);
	}


	@Override
	public List<Employee> findEmpList(Map<String, String> map) {
		return employeeDao.findEmpList(map);
	}
	
	@Override
	public Employee queryEmployee(String id) {
		return employeeDao.selectById(id);
	}
	
	@Override
	public List<Object[]> findEmps(Map<String, String> map) {
		return employeeDao.findEmps(map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String getPtjobSiteIdByUserId(String userId){
		return employeeDao.getPtjobSiteIdByUserId(userId);
	}
	
	public String getSiteNameBySiteId(String siteId){
		return employeeDao.getSiteNameBySiteId(siteId);
	}
	
	@Override
	public List<Employee> findEmployeeListByDepId(String depId){
		return employeeDao.findEmployeeListByDepId(depId);
	}

	
	@Override
	public List<Employee> findEmployeesByMc(String mc,String siteId) {
		return employeeDao.findEmployeesByMc(mc,siteId);
	}
	
	@Override
	public List<Employee> findEmployeesByMc(String mc,String siteId ,String departId) {
		return employeeDao.findEmployeesByMc(mc,siteId,departId);
	}

	@Override
	public List<Employee> queryEmpByName(String name) {
		return employeeDao.selectEmpByName(name);
	}

	@Override
	public List<Employee> getAllEmpsByUserIds(String userIds) {
		return employeeDao.getAllEmpsByUserIds(userIds);
	}


	@Override
	public List<Employee> getAllChildEmpsByDeptId(String deptId) {
		return employeeDao.getAllChildEmpsByDeptId(deptId);
	}
	@Override
	public List<Employee> getAllChildEmpsByDeptIdForAdmin(String deptId) {
		return employeeDao.getAllChildEmpsByDeptIdForAdmin(deptId);
	}
	
	@Override
	public List<Employee> getAllUnitEmployee(String depId){
		return employeeDao.getAllUnitEmployee(depId);
	}


	@Override
	public List<Employee> findShipByLeaderId(String leaderId) {
		return employeeDao.findShipByLeaderId(leaderId);
	}


	@Override
	public List<Object[]> findDeptEmr(String did) {
		return employeeDao.findDeptEmr(did);
	}
	
	@Override
	public List<Object[]> findAllEmr() {
		return employeeDao.findAllEmr();
	}

	@Override
	public List<Object[]> findEmrById(String id) {
		return employeeDao.findEmrById(id);
	}
	
	@Override
	public void addFeedBack(FeedBack feedBack) {
		employeeDao.addFeedBack(feedBack);
	}



	@Override
	public List<Employee> findEmployeeListBySiteId(String siteId) {
		return employeeDao.findEmployeeListBySiteId(siteId);
	}

	
	public List<Employee> queryAllempByDepId(String depId){
		return employeeDao.queryAllempByDepId(depId);
	}
	
	public Employee queryEmpByDepId(String depId){
		return employeeDao.queryEmpByDepId(depId);
	}
	
	public String getPassWord(String userId){
		return employeeDao.getPassWord(userId);
	}

	public String getJobCode(String userId){
		return employeeDao.getJobCode(userId);
	}
	
	public String getSiteid(String departmentGuid){
		return employeeDao.getSiteid(departmentGuid);
	}
	
	public String getJobSeq(String userId){
		return employeeDao.getJobCode(userId);
	}

//根据用户身份证号查询
	@Override
	public List<Employee> getEmployeeByCardId(String cardId)
			throws DataAccessException {
		return employeeDao.getEmployeeByCardId(cardId);
	}


	@Override
	public List<RoleUser> getRoleUserById(String userId) {
		return employeeDao.getRoleUserById(userId);
	}
}
