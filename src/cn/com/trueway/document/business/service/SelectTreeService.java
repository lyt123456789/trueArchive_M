package cn.com.trueway.document.business.service;

import java.util.List;
import java.util.Map;
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

/**
 * 作者：周雪贇<br>
 * 创建时间：2011-12-1 下午03:34:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface SelectTreeService {
	/**
	 * 根据parentId得到其下所有的GwDepartext
	 * 
	 * @param parentId
	 * @return
	 */
	public List<GwDepartext> getDepsByPid(String parentId);
	
	/**
	 * 得到所有的GwDepart
	 * 
	 * @return
	 */
	public List<GwDepart> getAllDeps();
	
	/**
	 * 根据Department的departmentId找到对应的Department对象
	 * 
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public Department findDepartmentById(String departmentId);

	public String parseUserIds(String toUserIds);

	public Employee findEmployeeById(String toUserId);
	
	/**
	 * 找到所有的部门
	 * 
	 * @return
	 */
	public List<Department> findAllDepartments();

	/**
	 * 根据depId为空或depId=superId找到根Department对象
	 * 
	 * @param departmentId
	 * @return
	 */
	public Department findRootDepartment();
	
	/**
	 * 得到department下所有的节点
	 * 
	 * @param childDep
	 * @param depIds
	 * @param deps
	 * @param rootDep
	 * @return
	 */
	public Set<String> getChildDep(Set<String> childDep,Set<String> depIds, List<Department> deps,Department rootDep);

	/**
	 * 根据depId得到List<Employee> emps,List<Department> deps,得到第一层
	 * 
	 * @param depId
	 * @return
	 */
	public Map<String, Object> getInfosOneFloor(String depId);
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
	 * 创建时间:2011-12-21 上午10:48:55
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
	 * 创建时间:2011-12-28 下午03:44:18
	 */
	public boolean deleteGroupById(String id) throws Exception;
	/**
	 * 
	 * 描述：保存常用组<br>
	 *
	 * @param name
	 * @param dep
	 * @param userId void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-28 下午05:30:44
	 */
	public void saveGroup(String name, String dep, String userId);
	
	
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

	public void insertDept(String pid,String id,String name,String isSub,String orderNum,String hasSub);

	public List<DocXgDepartment> getDocXgDept(String deptId);
	
	public List<DocXgDepartment> getDocXgDeptById(String id);

	public List<DocXgDepartment> getAllDocXgDepts(String mc);
	
	public DocXgDepartment getDocXgDeptById4Set(String id);
	
	public List<Object[]>   getExchanegDepById(String depid);
	
	public void addDepRelationShip(DepRelationShip relationShip);
	
	public void deleteDepRelationShip(DepRelationShip relationShip);
	
	public int getDepRelationShipCount();
	
	public List<DepRelationShip> getDepRelationShipList();
	
	public String getDocXgName(String id);
	
	public void addVGwDepart(List<VGwDepart> list);
	
}
