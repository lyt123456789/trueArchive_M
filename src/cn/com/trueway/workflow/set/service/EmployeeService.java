package cn.com.trueway.workflow.set.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.FeedBack;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.EmployeeSpe;
import cn.com.trueway.workflow.set.pojo.vo.SimpleEmpVo;


/**
 * 人员管理的Service层接口
 * 
 * @author liwei
 * 
 */
public interface EmployeeService {
/**
 * 查询特定人员信息  根据人员id
 * @param userId
 * @return
 */
	Employee findEmployeeById(String userId);

	
/**
 * 查得 直属部门下的全部人员信息
 * @param departmentId
 * @return
 */
	Collection<Employee> findEmployees(String departmentId);
	

/**
 * 查得 直属部门下的有该id的人员信息
 * @param departmentId
 * @return
 */
	Collection<Employee> findEmployeesByIds(String departmentId,String ids);
	
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
	public List<EmployeeSpe> queryEmployeeByDN(String dn) throws DataAccessException ;
	
	public List<EmployeeSpe> queryEmployeeByDepIds(String depids) throws DataAccessException ;
	
	public List<EmployeeSpe> queryEmployeeBySuperDepIds(String depids) throws DataAccessException ;
	
	public List<Object[]> getEmployeeInfoByEmployeeId(String employeeId)throws DataAccessException; 
	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(String departmentid)throws DataAccessException;
	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(String departmentid,String userids)throws DataAccessException;
	
	public List<Employee> getEmployeeByUsernameAndPassword(String username,String password)throws DataAccessException;

	String getDeptByEmployeeIds(String userids);

	List<Employee> findEmployeesByMc(String mc);

	List<Employee> getEmployeesByIds(String relation_userids,String mc);

	List<Employee> getEmployeeByUsername(String mc);
	
	public List<Employee> getEmployeeList(String ids);

	List<Object[]> getEmployeeInfoByCylxrId(String id);


	List<Object> getUsers(String paramValueByParam);
	
	String getUserNameAndDept(String id);
	
	List<SimpleEmpVo> getUserInfo();
	
	Employee getEmployeeByLoginName(String loginName);
	
	String getUserNamesByIds(String ids);

	int findEmployeeCount(String loginName,  String likeType);
	
	int findEmployeeCount1(String loginName,  String likeType);
	
	List<Employee> findEmployeeList(String loginName,  String likeType, Integer pageIndex,Integer pageSize);
	
	List<Employee> findEmployeeList1(String loginName,  String likeType, Integer pageIndex,Integer pageSize);
	
	List<Employee> findEmpList(Map<String, String> map);
	
	public Employee queryEmployee(String id);
	
	List<Object[]> findEmps(Map<String, String> map);
	
	
	
	
	

	
	String getPtjobSiteIdByUserId(String userId);
	
	public String getSiteNameBySiteId(String siteId);
	
	List<Employee> findEmployeeListByDepId(String depId);

	List<Employee> findEmployeesByMc(String mc,String siteId);
	List<Employee> findEmployeesByMc(String mc,String siteId,String departId);
	
	public List<Employee> queryEmpByName(String name);

	List<Employee> getAllChildEmpsByDeptId(String deptId);
	List<Employee> getAllChildEmpsByDeptIdForAdmin(String deptId);
	
	List<Employee> getAllEmpsByUserIds(String userIds);
	
	public List<Employee> getAllUnitEmployee(String depId);
	
	public List<Employee> findShipByLeaderId(String leaderId);

	public List<Object[]> findEmrById(String id);

	public List<Object[]> findDeptEmr(String did);

	public List<Object[]> findAllEmr();
	
	void addFeedBack(FeedBack feedBack);

	 public List<Employee> findEmployeeListBySiteId(String siteId);

	
	//按照排序获取全部办领导
	List<Employee> queryAllempByDepId(String depId);
	//按照排序获取部门中第一位
	Employee queryEmpByDepId(String depId);
	
	//获取人员密码
	String getPassWord(String userId);
	
	String getJobCode(String userId);
	
	String getSiteid(String departmentGuid);
	
	String getJobSeq(String userId);
	//获取角色id
	List<RoleUser> getRoleUserById(String userId);
	
	
	/**
	 * 描述：获取人员信息<br>
	 * @param userId
	 * @return 
	 */
//	Employee findSysUserByUserId(String userId);
	public List<Employee> getEmployeeByCardId(String cardId)throws DataAccessException;
}
