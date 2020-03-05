package cn.com.trueway.document.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.CollectionUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.business.dao.SelectTreeDao;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.GwDepart;
import cn.com.trueway.document.business.docxg.client.vo.GwDepartext;
import cn.com.trueway.document.business.docxg.client.vo.VGwDepart;
import cn.com.trueway.document.business.model.Depgroup;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.EmpGroup;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

public class SelectTreeServiceImpl implements SelectTreeService {
	private SelectTreeDao selectTreeDao;

	// getChildDep方法中，用于存结果的集合
	private Set<String> childDeps = new HashSet<String>();
	
	public SelectTreeDao getSelectTreeDao() {
		return selectTreeDao;
	}

	public void setSelectTreeDao(SelectTreeDao selectTreeDao) {
		this.selectTreeDao = selectTreeDao;
	}

	/**
	 * 根据parentId得到其下所有的GwDepartext
	 * 
	 * @param parentId
	 * @return
	 */
	public List<GwDepartext> getDepsByPid(String parentId) {
		return selectTreeDao.queryGwDepartextByParent(parentId);
	}
	
	/**
	 * 得到所有的GwDepart
	 * 
	 * @return
	 */
	public List<GwDepart> getAllDeps() {
		return selectTreeDao.queryGwDeparts();
	}
	
	/**
	 * 根据Department的departmentId找到对应的Department对象
	 * 
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public Department findDepartmentById(String departmentId) {
		return selectTreeDao.queryDepartmentById(departmentId);
	}

	/**
	 * 根据
	 * @param toUserId
	 * @return
	 */
	public Employee findEmployeeById(String toUserId) {
		return selectTreeDao.findEmployeeById(toUserId);
	}

	/**
	 * 处理接收人员Id
	 * @param toUserIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String parseUserIds(String toUserIds) {
		String[] ids = toUserIds.split(",");
		// 盛放结果的集合
		Set userIdSet = new HashSet();
		Set depIds = new HashSet();
		Set empIds = new HashSet();
		List temp = new ArrayList();
		// 数据分组
		for (int i = 0, l = ids.length; i < l; i++) {
			String id = ids[i];
			if (id == null || id.trim().length() < 1) {
				continue;
			} else if (id.endsWith("d")) {
				depIds.add(id.substring(0, id.length() - 1));
			} else {
				empIds.add(id);
			}
		}
		if (depIds != null && !depIds.isEmpty()) {
			// 根据depIds得其下的所有的depIds
			temp.addAll(this.getChildDep(new HashSet<String>(), depIds, this.findAllDepartments(),  this.findRootDepartment()));
			childDeps.clear();
			List<List<Object>> l = CollectionUtil.listOne2many(temp, 1000);
			for (List list : l) {
				userIdSet.addAll(selectTreeDao.findEmployees(list));
			}
		}
		// 合并并过滤掉相同的empId
		userIdSet.addAll(empIds);
		// 把结果加到StringBuilder中，并用英文逗号隔开
		StringBuilder sb = new StringBuilder();
		Iterator it = userIdSet.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			sb.append(",");
		}
		if (sb.toString().endsWith(",")) {
			return sb.substring(0, sb.length() - 1);
		} else {
			return sb.toString();
		}
	}
	
	public DTPageBean findEmployeesByDepsId(List<String> depIds, String userName, int currentPage,int numPerPage) {
		return selectTreeDao.findEmployeesByDepsId(depIds, userName, currentPage, numPerPage);
	}
	/**
	 * 找到所有的部门
	 * 
	 * @return
	 */
	public List<Department> findAllDepartments() {
		return selectTreeDao.findAllDepartments();
	}

	/**
	 * 根据depId为空或depId=superId找到根Department对象
	 * 
	 * @param departmentId
	 * @return
	 */
	public Department findRootDepartment() {
		return selectTreeDao.findRootDepartment();
	}
	
	/**
	 * 得到department下所有的节点
	 * 
	 * @param childDep
	 * @param depIds
	 * @param deps
	 * @param rootDep
	 * @return
	 */
	public Set<String> getChildDep(Set<String> result, Set<String> depIds,List<Department> deps, Department rootDep) {
		if (result != null && !result.isEmpty()){
			childDeps.addAll(result);
		}
		childDeps.addAll(depIds);
		for (String depId : depIds) {
			Set<String> oneFloorResult = new HashSet<String>();
			// 如果是根节点直接返回所有部门的Id
			if (depId.equals(rootDep.getDepartmentGuid())) {
				for (Department department : deps) {
					childDeps.add(department.getDepartmentGuid());
				}
				return childDeps;
			}
			for (int i = 0, l = deps.size(); i < l; i++) {
				Department dep = deps.get(i);
				// 过滤掉根节点
				if (rootDep.getDepartmentGuid().equals(dep.getDepartmentGuid())) {
					continue;
				}
				// 得depId部门下所有部门的Id，加到childDep中
				if (dep.getSuperiorGuid().equals(depId)) {
					oneFloorResult.add(dep.getDepartmentGuid());
				}
				// 遍历结束后的操作：跳出or跳出循环
				if (i == l - 1) {
					// oneFloorResult为空表示改depId已经是最下一层，此时跳出循环
					if (oneFloorResult == null || oneFloorResult.isEmpty()) {
						break;
					}
					// 不是最下一层，递归
					getChildDep(oneFloorResult, oneFloorResult, deps, rootDep);
				}
			}
		}
		return childDeps;
	}
	
	/**
	 * 根据depId得到List<Employee> emps,List<Department> deps,得到第一层
	 * 
	 * @param depId
	 * @return
	 */
	public Map<String, Object> getInfosOneFloor(String depId) {
		List<Department> deps = new ArrayList<Department>();
		List<String> depIds = new ArrayList<String>();
		deps.add(selectTreeDao.queryDepartmentById(depId));
		depIds.add(depId);

		List<Department> departments = selectTreeDao.findDepartments(depId);
		for (Department department : departments) {
			deps.add(department);
			depIds.add(department.getDepartmentGuid());
		}
		List<Employee> emps = selectTreeDao.findEmployeesByDepIds(depIds);
		// 盛放结果集的容器
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.DEPS, deps);
		map.put(Constant.EMPS, emps);
		return map;
	}

	public List<Depgroup> getDepGroupByUserId(String userId) {
		return selectTreeDao.getDepGroupByUserId(userId);
	}

	public boolean deleteGroupById(String id) throws Exception{
		return selectTreeDao.deleteGroupById(id)==1;
	}

	public void saveGroup(String name, String dep, String userId) {
		Depgroup depgroup = new Depgroup();
		depgroup.setName(name);
		depgroup.setDep(dep);
		depgroup.setSuperior_guid(userId);
		selectTreeDao.saveGroup(depgroup);
	}
	
	
	/**
	 * 描述：根据人员ID查找人员实体<br>
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-1-18 上午11:16:07
	 */
	public Employee queryEmployeeByEmployeeGuid(String employeeGuid) {
		return selectTreeDao.queryEmployeeByEmployeeGuid(employeeGuid);
	}
	
	
	/**
	 * 描述：新增人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void insertEmpGroup(EmpGroup empGroup){
		selectTreeDao.insertEmpGroup(empGroup);
	}
	
	/**
	 * 描述：根据userId 查询人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public List<EmpGroup> queryEmpGroupsByUserId(String userId){
		return selectTreeDao.queryEmpGroupsByUserId(userId);
	}
	
	/**
	 * 描述：根据userId  删除人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void removeEmpGroupByUserId(String userId){
		selectTreeDao.removeEmpGroupByUserId(userId);
	}

	/**
	 * @param pid
	 * @param id
	 * @param name
	 * @param isSub
	 * @param orderNum
	 * @param hasSub
	 */
	@Override
	public void insertDept(String pid, String id, String name, String isSub,
			String orderNum, String hasSub) {
		selectTreeDao.insertDept( pid,  id,  name,  isSub,
				 orderNum,  hasSub);
		
	}

	/**
	 * @param string
	 * @return
	 */
	@Override
	public List<DocXgDepartment> getDocXgDept(String deptId) {
		return selectTreeDao.getDocXgDept(deptId);
	}

	@Override
	public List<Object[]>  getExchanegDepById(String depid){
		return selectTreeDao.getExchanegDepById(depid);
	}
	
	@Override
	public List<DocXgDepartment> getDocXgDeptById(String id){
		return selectTreeDao.getDocXgDeptById(id);
	}
	
	
	@Override
	public DocXgDepartment getDocXgDeptById4Set(String id){
		return selectTreeDao.getDocXgDeptById4Set(id);
	}

	@Override
	public void addDepRelationShip(DepRelationShip relationShip) {
		selectTreeDao.addDepRelationShip(relationShip);
	}

	@Override
	public void deleteDepRelationShip(DepRelationShip relationShip) {
		selectTreeDao.deleteDepRelationShip(relationShip);
	}

	@Override
	public int getDepRelationShipCount() {
		return selectTreeDao.getDepRelationShipCount();
	}

	@Override
	public List<DepRelationShip> getDepRelationShipList() {
		return selectTreeDao.getDepRelationShipList();
	}

	@Override
	public String getDocXgName(String id) {
		return selectTreeDao.getDocXgName(id);
	}

	@Override
	public void addVGwDepart(List<VGwDepart> list) {
		selectTreeDao.addVGwDepart(list);
	}

	@Override
	public List<DocXgDepartment> getAllDocXgDepts(String mc) {
		return selectTreeDao.getAllDocXgDepts(mc);
	}
	
}
