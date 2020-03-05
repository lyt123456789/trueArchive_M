package cn.com.trueway.workflow.set.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.FeedBack;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.EmployeeSpe;

/**
 * 人员管理的Dao层接口
 * 
 * @author liwei
 * 
 */
public interface EmployeeDao {

	Employee queryEmployeeById(String userId) throws DataAccessException;

	Collection<Employee> queryEmployees(String departmentId) throws DataAccessException;

	Collection<Employee> queryEmployeesByIds(String departmentId,String ids) throws DataAccessException;
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

	List<Employee> getEmployeeByEmployeeIds(String userids);



	List<Employee> findEmployeesByMc(String mc);



	List<Employee> getEmployeeByEmployeeIds(String relation_userids, String mc);



	List<Employee> getEmployeeByUsername(String mc);

	public List<Employee> getEmployeeList(String ids);

	List<Object[]> getEmployeeInfoByCylxrId(String id);

	List<Employee> getEmployeeByEmployeeId(String employeeId);



	List<Object> getUsers(String id);



	List<Department> getDepartmentByDepartmentId(String deptIds);


	List<Object[]> getUserNameAndDept(String id);
	
	List<Object[]> getUserNameAndDeptList(String ids);

	List<Object[]> getUserInfo();
	
	Employee getEmployeeByLoginName(String loginName);



	List<Object> getUserNamesByIds(String ids);
	
	List<Object> getDepartmentIdByDepartmentId(String deptIds);
	
	List<Object> getEmployeeDeptIdByEmployeeIds(String userids);

	List<Employee> findEmployeeList(String loginName, String likeType,
			Integer pageIndex, Integer pageSize);
	
	List<Employee> findEmployeeList1(String loginName,String likeType, Integer pageIndex,Integer pageSize);

	int findEmployeeCount(String loginName, String likeType);
	
	int findEmployeeCount1(String loginName, String likeType);
	
	List<Employee> findEmpList(Map<String, String> map);
	/** 
	 * selectById:(根据主键查询人员信息). <br/> 
	 * 
	 */ 
	public Employee selectById(String id);
	
	List<Object[]> findEmps(Map<String, String> map);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	String getPtjobSiteIdByUserId(String userId) throws DataAccessException;
	
	public String getSiteNameBySiteId(String siteId);
	
	List<Employee> findEmployeeListByDepId(String depId);

	List<Employee> findEmployeesByMc(String mc,String siteId);
	List<Employee> findEmployeesByMc(String mc,String siteId,String DepartId);

	public List<Employee> selectEmpByName(String name);

	
	List<Employee> getAllChildEmpsByDeptId(String deptId);
	
	List<Employee> getAllEmpsByUserIds(String userIds);	
	/**
	 * 
	 * 描述：根据部门id获取数组(包含人员信息,以及部分部门信息)
	 * @param guid
	 * @return List<Object[]>
	 * 作者:蔡亚军
	 * 创建时间:2014-8-8 下午4:04:11
	 */
	public List<Object[]> findEmployeeInfoListByDepId(String depId)  throws Exception;
	
	/**
	 * 描述：获取该部门下的所有人员
	 * @param depId
	 * @return List<Employee>
	 * 作者:蒋烽
	 * 创建时间:2016-12-13 下午3:31:32
	 */
	public List<Employee> getAllUnitEmployee(String depId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param leadId
	 * @param personId
	 * @return List<DiaryAccredit>
	 * 作者:蒋烽
	 * 创建时间:2017-1-3 下午5:05:48
	 */
	/*public List<DiaryAccredit> findShipById(String leadId, String personId);*/
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param leaderId
	 * @return List<DiaryAccredit>
	 * 作者:蒋烽
	 * 创建时间:2017-1-3 下午5:05:45
	 */
	public List<Employee> findShipByLeaderId(String leaderId);
	
	public List<Object[]> findEmrById(String id);
	
	public List<Object[]> findDeptEmr(String did);
	
	public List<Object[]> findAllEmr();
	
	void addFeedBack(FeedBack feedBack);

	public List<Employee> findEmployeeListBySiteId(String siteId);

	
	List<Employee> queryAllempByDepId(String depId);
	
	Employee queryEmpByDepId(String depId);
	
	String getPassWord(String userId);

	String getJobCode(String userId);
	
	String getSiteid(String departmentGuid);
	
	String getJobSeq(String userId);

	List<Employee> getAllChildEmpsByDeptIdForAdmin(String deptId);

	List<Employee> getEmployeeByCardId(String cardId);

	List<RoleUser> getRoleUserById(String userId);
	
}
