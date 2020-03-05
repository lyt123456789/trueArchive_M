package cn.com.trueway.workflow.set.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.CommonGroupUsers;
import cn.com.trueway.sys.pojo.SiteSource;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.pojo.vo.SimpleDeptVo;

/**
 * @author 李伟
 * @version 创建时间：2009-11-27 下午08:56:39 类说明
 */
public interface DepartmentService {


	/**根据部门id  查询部门信息
	 * 
	 * @param departmentId
	 * @return
	 */
	Department findDepartmentById(String departmentId);

	/**
	 * 根据部门名称  查询部门信息
	 * @param departmentName
	 * @return
	 */
	Department findDepartmentByName(String departmentName);

	/**
	 * 根据 上级部门id  查询部门信息
	 * @param superiorId
	 * @return
	 */
	Collection<Department> findDepartments(String superiorId);

	/**
	 * 获得所有部门信息
	 * @return
	 */
	Collection<Department> findDepartments();

	/**
	 * 大部门 信息查询
	 * @param loginName
	 * @return
	 */
	Department findDepartmentAfterLogin(String loginName);

	/**
	 * 根据  部门名称 分页查询部门信息
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Collection<Department> queryDepartmentsLikeName(String name, int pageIndex,
			int pageSize);

	/**
	 * 内页的条数设定
	 * @param name
	 * @return
	 */
	Integer getDepartmentsLikeNameSize(String name);
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
	
	public List<Department> queryDepartmentsBySuperdepIds(String depIds);
	
	List<Employee> findEmployeeListByDepId(Department dep);

	List<Department> queryDepartmentsBydepIds1(Department department_rootId,
			String deptsid);
	
	Department queryDepartmentById(String departmentId)throws DataAccessException;
	
	public List<SimpleDeptVo> getDeptInfo();
	
	List<Department> queryDepartmentListByShortDN(String dn) ;
	
	Collection<Department> findAllChildDepList(String depId);

	String findDepIds(String departmentId);
	
	String findSencodDep(String deptId);
	
	/**
	 * 获得所有部门信息
	 * @return
	 */
	Collection<Department> findThirdDepartments(String superiorId);
	
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
		
		void deleteCommonGroupUsersByGid(String gid);
		
		void updateCommonGroup(CommonGroup cg);
		
		void updateCommonGroupUsers(CommonGroupUsers cgu);
		
		List<CommonGroup> findAllCommonGroupByUid(String uid);
		
		List<CommonGroup> findAllCommonGroupByUid(String uid,String siteId,String role);
		
		List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid);
		List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid,String mc);
		
		CommonGroup findCommonGroupById(String id);
		
		List<Department> getAllParentDeptBydeptId(String deptsid);
		
		List<Department> getAllChildDeptBydepts(String deptId);
		
		/**
		 * 描述：获取所有的部门和人员
		 * @param rootDepId
		 * @return List<Map<String, String>>
		 * 作者:蒋烽
		 * 创建时间:2016-12-12 下午3:49:39
		 */
		/*List<Map<String, String>> getAllDepAndUser(String rootDepId, String accreditId);*/
		
		void setSiteSource(SiteSource ss);
		
		public List<Object[]> getAllSiteName();
		
		String getIdByDeptId(String deptId);
		
		String getNameByDeptId(String deptId);
		
		Map<String,String> getPtJob(String userId);
}
