package cn.com.trueway.document.business.dao;

import java.util.List;
import java.util.Set;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.GwDepart;
import cn.com.trueway.document.business.docxg.client.vo.GwDepartext;
import cn.com.trueway.document.business.docxg.client.vo.VGwDepart;
import cn.com.trueway.document.business.model.Depgroup;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.EmpGroup;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface SelectTreeDao {

	/**
	 * 根据parentId得到其下所有的GwDepartext
	 * 
	 * @param parentId
	 * @return
	 */
	public List<GwDepartext> queryGwDepartextByParent(String parentId);

	/**
	 * 得到所有的GwDepart
	 * 
	 * @return
	 */
	public List<GwDepart> queryGwDeparts();
	/**
	 * 根据Department的departmentId找到对应的Department对象
	 * 
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public Department queryDepartmentById(String departmentId);

	public Employee findEmployeeById(String userId);

	public List<Department> findAllDepartments();

	public Department findRootDepartment();
	
	/**
	 * 根据depIds找到所有对应的employeeId的集合
	 * 
	 * @param depIds
	 * @return
	 */
	public Set<String> findEmployees(List<String> depIds);

	public List<Department> findDepartments(String depId);

	public List<Employee> findEmployeesByDepIds(List<String> depIds);
	/**
	 * 
	 * 描述：根据部门集合分页查询人员信息<br>
	 *
	 * @param depIds
	 * @param currentPage
	 * @param numPerPage
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-21 上午10:47:26
	 */
	public DTPageBean findEmployeesByDepsId(List<String> depIds, String userName, int currentPage,int numPerPage);
	/**
	 * 
	 * 描述：根据用户得到其常用单位分组<br>
	 *
	 * @param userId
	 * @return List<Depgroup>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-28 下午03:19:55
	 */
	public List<Depgroup> getDepGroupByUserId(String userId);
	/**
	 * 
	 * 描述：删除常用组<br>
	 *
	 * @param id void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-28 下午03:45:50
	 */
	public int deleteGroupById(String id);
	/**
	 * 
	 * 描述：保存常用组<br>
	 *
	 * @param depgroup void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-28 下午05:32:21
	 */
	public void saveGroup(Depgroup depgroup);
	
	/**
	 * 描述：根据人员ID查找人员实体<br>
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-1-18 上午11:16:07
	 */
	public Employee queryEmployeeByEmployeeGuid(String employeeGuid);
	
	/**
	 * 描述：新增人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void insertEmpGroup(EmpGroup empGroup);
	
	/**
	 * 描述：根据userId 查询人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public List<EmpGroup> queryEmpGroupsByUserId(String userId);
	
	/**
	 * 描述：根据userId  删除人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void removeEmpGroupByUserId(String userId);

	public void insertDept(String pid, String id, String name, String isSub,
			String orderNum, String hasSub);

	public List<DocXgDepartment> getDocXgDept(String deptId);
	
	public List<DocXgDepartment> getDocXgDeptById(String id);
	
	public DocXgDepartment getDocXgDeptById4Set(String id);
	
	public List<Object[]>   getExchanegDepById(String depid);
	
	public void addDepRelationShip(DepRelationShip relationShip);
	
	public void deleteDepRelationShip(DepRelationShip relationShip);
	
	public int getDepRelationShipCount();
	
	public List<DepRelationShip> getDepRelationShipList();
	
	public String getDocXgName(String id);
	
	public void addVGwDepart(List<VGwDepart> list);

	public List<DocXgDepartment> getAllDocXgDepts(String mc);

}
