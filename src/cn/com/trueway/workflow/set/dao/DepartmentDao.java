package cn.com.trueway.workflow.set.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.CommonGroupUsers;
import cn.com.trueway.sys.pojo.SiteSource;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

/**
 * @author 李伟
 * @version 创建时间：2009-11-27 下午08:16:12 类说明
 */
public interface DepartmentDao {
	void insertDepartment(Department department) throws DataAccessException;

	void updateDepartment(Department department) throws DataAccessException;

	void deleteDepartment(Department department) throws DataAccessException;

	Department queryDepartmentById(String departmentId)
			throws DataAccessException;

	Department queryDepartmentByName(String departmentName)
			throws DataAccessException;

	Collection<Department> queryDepartments(String superiorId)
			throws DataAccessException;

	Collection<Department> queryDepartments() throws DataAccessException;
	
	/**
	 * 根据用户的登录名得到所对应的部门信息
	 * @param loginName
	 * @return
	 * @throws DataAccessException
	 */
	Department queryDepartmentAfterLogin(String loginName)
	throws DataAccessException;
	Collection<Department> queryDepartmentsLikeName(String name, int pageIndex,
			int pageSize) throws DataAccessException;

	Integer getDepartmentsLikeNameSize(String name) throws DataAccessException;
	/**
	 * 
	 * @Title: queryDepartmentListByDN
	 * @Description: 根据dn获取所有相关部门
	 * @param @param dn
	 * @param @return    设定文件
	 * @return List<Department>    返回类型
	 * @throws
	 */
	public List<Department> queryDepartmentListByDN(String dn);
	/**不存在根节点， 系统指定部门id
	 * 
	 * @param depIds
	 * @return
	 */
	public List<Department> queryDepartmentsBydepIds(String depIds);
	
	/**不存在根节点， 系统指定部门id
	 * 
	 * @param depIds
	 * @return
	 */
	public List<Department> queryDepartmentsBySuperdepIds(String depIds);

	List<Employee> findEmployeeListByDepId(Department dep);

	List<Department> queryDepartmentsByShortdnAndDept(String departmentShortdn,
			String deptsid);
	
	List<Object[]> getDeptInfo();

	List<Department> queryDepartmentListByShortDN(String dn) ;
	
	Collection<Department> queryThirdDepartments(String superiorId)
			throws DataAccessException;
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param ids
	 * @return List<Department>
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 上午10:47:45
	 */
	Department findSiteDept(String ids);

	List<Department> queryAllSite();
	
	List<Department> getAllChildDeptBydeptId(String deptId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	List<Object[]> findDepartmentList(Map<String, String> inmap);
	
	public Department getSuperiorByDepartmentId(String id,String rootId,String rootId2);
	
	/** 
	 * getDepartId:(根据人员id获取部门id). <br/> 
	 * 
	 * @author 
	 * @update adolph.jiang  
	 * @return String
	 * @since JDK 1.6 
	 */ 
	public String getDepartId(String id);
	
	public List<Object[]> findAllChildDeptObjects(String departmentid)throws DataAccessException;
	
	CommonGroup saveCommonGroup(CommonGroup cg);
	
	void saveCommonGroupUsers(CommonGroupUsers cgu);
	
	void deleteCommonGroup(CommonGroup cg);
	
	void deleteCommonGroupUsers(CommonGroupUsers cgu);
	
	void updateCommonGroup(CommonGroup cg);
	
	void updateCommonGroupUsers(CommonGroupUsers cgu);
	
	List<CommonGroup> findAllCommonGroupByUid(String uid);
	
	List<CommonGroup> findAllCommonGroupByUid(String uid,String siteId,String role);
	
	List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid);
	List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid,String mc);//根据名称条件查常用组人员
	
	CommonGroup findCommonGroupById(String id);
	
	void deleteCommonGroupUsersByGid(String gid);
	
	List<Department> getAllParentDeptBydeptId(String deptsid);
	
	List<Department> getAllChildDeptBydepts(String deptId);
	
	List<String> queryDepartmentIDsBySuperdepIds(String supDeptId);
	
	void mergeSiteSource(SiteSource ss);
	
	public List<Object[]> getAllSiteName();
	
	String getIdByDeptId(String deptId);
	
	String getNameByDeptId(String deptId);
	
	Map<String,String> getPtJob(String userId);
}
