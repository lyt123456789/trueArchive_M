package cn.com.trueway.archives.manage.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.TreeDataOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface RoleManageService {
	
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
	public boolean addRole(TrueArchiveRole acRole);
	
	/**
	 * 修改角色信息
	 * @param acRole
	 * @return
	 */
	public boolean updateRole(TrueArchiveRole acRole);
	
	/**
	 * 获取指定角色信息
	 * @param id
	 * @return
	 */
	public TrueArchiveRole getOneAcRole(String id);
	
	/**
	 * 删除角色信息
	 * @param id
	 * @return
	 */
	public boolean delRole(String id);

	List<EssTree> getModelTreeByMap(Map<String, String> map);

	void updateTreeNodeOfRole(TreeNodeOfRole sr);
	
	void updateBtnOfRole(BtnOfRole tnr);

	List<TreeNodeOfRole> getTreeNodesOfRole(Map<String, String> map);
	
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
	public boolean updateCasualUser(CasualUser cUser,String menuFlag);
	
	/**
	 * 删除一条临时用户信息
	 */
	public boolean deleteCasualUser(String id);
	
	/**
	 * 查找临时用户的信息
	 * @param id
	 * @return
	 */
	public List<CasualUser> getTreeNodesOfCasualUser(String id);
	
	/**
	 * 获取系统菜单及菜单对应角色选中信息
	 * @param roleId
	 * @return
	 */
	public List<Menu> getRoleMenuData(String roleId);
	
	/**
	 * 获取选中的menu信息
	 * @param roleId
	 * @return
	 */
	public List<String> getRoleMenuId(String roleId);
	
	/**
	 * 设置角色对应的菜单配置
	 * @param roleId
	 * @param menuIdStr
	 * @return
	 */
	public boolean setRoleMenuList(String roleId, String menuIdStr);
	
	/**
	 * 获取角色可以对应的员工名单
	 * @param roleId
	 * @return
	 */
	public List<Employee> getRoleEmployeeList(String roleId,String deGuids);
	
	/**
	 * 获取角色对应的员工id
	 * @param roleId
	 * @return
	 */
	public List<String> getCheckRoleEmployeeList(String roleId);
	
	/**
	 * 设置角色对应的员工信息
	 * @param roleId
	 * @param eyIdStr
	 * @return
	 */
	public boolean setRoleEmployeeList(String roleId,String eyIdStr);
	
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
	public List<Department> getEmployeeDepartmentList(String roleId);
	
	/**
	 * 检查部门下是否存在员工匹配角色
	 * @param deGuid
	 * @return
	 */
	public boolean checkDepartmentIsCheck(String deGuid,String roleId);
	
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
	 * 获取临时用户文档对应文档内容是否已经分配权限
	 * @param map
	 * @return
	 */
	public boolean checkCasualUserAllot(Map<String,String> map);
	
	/**
	 * 保存临时用户数据权限
	 * @param cur
	 * @return
	 */
	public boolean saveCasualUserDataRange(CasualUserDataRange cur);
	
	/**
	 * 获取临时用户数据全限
	 * @param map
	 * @return
	 */
	public CasualUserDataRange getCasualUserDataRange(Map<String,String> map);
	
	/**
	 * 删除临时用户数据权限
	 * @param id
	 * @return
	 */
	public boolean deleteCasualuserDataRange(String id);
	
	/**
	 * 检查临时用户数据权限设定是否成立
	 * @param sqlCondition
	 * @return
	 */
	public boolean checkSqlRight(Map<String,String> map);

	public TreeDataOfRole getRoleDataRange(Map<String, String> map);

	public boolean checkRoleAllot(Map<String, String> map);

	public boolean deleteRoleDataRange(String id);

	public boolean saveRoleDataRange(TreeDataOfRole cur);

	public List<BtnOfRole> getBtnOfRole(Map<String, String> map);
}
