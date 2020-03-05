package cn.com.trueway.archives.manage.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.RoleMenu;
import cn.com.trueway.archives.manage.pojo.TreeDataOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface RoleManageDao {

	List<EssTree> getModelTreeByMap(Map<String, String> map);

	void updateTreeNodeOfRole(TreeNodeOfRole sr);

	List<TreeNodeOfRole> getTreeNodesOfRole(Map<String, String> map);
	/**
	 * 获取角色信息集合总数
	 * @return
	 */
	public int getManageListCount();
	
	/**
	 * 获取角色信息集合
	 * @return
	 */
	public List<TrueArchiveRole> getRoleManageList(Integer pageindex, Integer pagesize);
	
	/**
	 * 新增角色
	 * @param acRole
	 */
	public void addRole(TrueArchiveRole acRole);
	
	/**
	 * 修改角色信息
	 * @param acRole
	 * @return
	 */
	public void updateRole(TrueArchiveRole acRole);
	
	/**
	 * 获取指定角色信息
	 * @param id
	 * @return
	 */
	public TrueArchiveRole getOneAcRole(String id);
	
	/**
	 * 删除角色信息
	 * @param id
	 */
	public void delRole(String id);

	/**
	 * 获取临时用户总数
	 * @return
	 */
	public int getCasualUserCount();
	
	/**
	 * 获取临时用户信息集合
	 * @return
	 */
	public List<CasualUser> getCasualUserList(Map<String,String> map,Integer pageindex, Integer pagesize);
	
	/**
	 * 最初生成一条临时用户记录
	 * @return casualid
	 */
	public String addCasualUserStart();
	
	/**
	 * 修改与新增修改临时用户信息
	 */
	public void updateCasualUser(CasualUser cUser,String menuFlag);
	
	/**
	 * 删除一条临时用户信息
	 */
	public void deleteCasualUser(String id);
	
	/**
	 * 查找临时用户的信息
	 * @param id
	 * @return
	 */
	public List<CasualUser> getTreeNodesOfCasualUser(String id);
	
	/**
	 * 获取系统菜单
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuData();
	
	/**
	 * 获取角色对应的系统菜单id
	 * @param roleId
	 * @return
	 */
	public List<String> getRoleMenuId(String roleId);
	
	/**
	 * 删除角色对应菜单信息
	 * @param roleId
	 */
	public void deleteRoleMenu(String roleId);
	
	/**
	 * 设置角色对应菜单信息
	 * @param roleId
	 * @param menuId
	 */
	public void setRoleMenu(RoleMenu rm);
	
	/**
	 * 获取所有的角色信息
	 * @return
	 */
	public List<Employee> getAllEmployee(String deGuids);
	
	/**
	 * 获取其他角色信息对应的人员
	 * @param roleId
	 * @return
	 */
	public List<String> getOtherCheckRoleEmployeeList(String roleId);
	
	/**
	 * 获取当前角色对应的人员
	 * @param roleId
	 * @return
	 */
	public List<String> getCheckRoleEmployeeList(String roleId);
	
	/**
	 * 删除角色分配的员工信息
	 * @param roleId
	 */
	public void deleteRoleEmployee(String roleId);
	
	/**
	 * 添加角色对应的员工分配
	 * @param rey
	 */
	public void setRoleEmployee(RoleEmployee rey);
	
	/**
	 * 查询用户对应的角色分类
	 * @param employeeId
	 * @return
	 */
	public RoleEmployee getRoleEmployeeListByEmId(String employeeId);
	
	/**
	 * 获取系统存在人员的部门
	 * @return
	 */
	public List<Department> getEmployeeDepartmentList();
	
	/**
	 * 获取有人员选中的部门集合
	 * @param roleId
	 * @return
	 */
	public List<String> getEmployeeCheckedDepartment(String roleId);
	
	/**
	 * 检查部门下是否存在员工匹配角色
	 * @param deGuid
	 * @return
	 */
	public List<Employee> checkDepartmentIsCheck(String deGuid);
	
	/**
	 * 获得临时用户分配的树节点
	 * @param casualId
	 * @return
	 */
	public Set<String> getCasualCheckNodes(String casualId);
	
	/**
	 * 获取档案的分类数据
	 * @param structureId
	 * @return
	 */
	public List<Map<String,String>> getTreeNodesSorts(String structureId,String treeId);
	
	/**
	 * 获取临时用户文档对应文档内容查询权限
	 * @param map
	 * @return
	 */
	public List<CasualUserDataRange> checkCasualUserAllot(Map<String,String> map);
	
	/**
	 * 保存临时用户数据权限
	 * @param cur
	 */
	public void saveCasualUserDataRange(CasualUserDataRange cur);
	
	/**
	 * 获取临时用户数据全限
	 * @param map
	 * @return
	 */
	public CasualUserDataRange getCasualUserDataRange(Map<String,String> map);
	
	/**
	 * 删除临时用户数据权限
	 * @param id
	 */
	public void deleteCasualuserDataRange(String id);
	
	/**
	 * 验证临时用户数据全限是否能获取数据
	 * @param map
	 */
	public boolean getConditionResult(Map<String,String> map);

	TreeDataOfRole getRoleDataRange(Map<String, String> map);

	List<TreeDataOfRole> checkRoleAllot(Map<String, String> map);

	void deleteRoleDataRange(String id);

	void saveRoleDataRange(TreeDataOfRole cur);

	List<BtnOfRole> getBtnOfRole(Map<String, String> map);

	void updateBtnOfRole(BtnOfRole tnr);

}
