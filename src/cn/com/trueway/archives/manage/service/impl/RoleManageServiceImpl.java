package cn.com.trueway.archives.manage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.trueway.archiveModel.entity.EssTree;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.manage.dao.RoleManageDao;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.RoleMenu;
import cn.com.trueway.archives.manage.pojo.StructsOfRole;
import cn.com.trueway.archives.manage.pojo.TreeDataOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.using.dao.ArchiveUsingDao;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

@SuppressWarnings("unused")
public class RoleManageServiceImpl implements  RoleManageService{
	private RoleManageDao roleManageDao;

	public RoleManageDao getRoleManageDao() {
		return roleManageDao;
	}

	public void setRoleManageDao(RoleManageDao roleManageDao) {
		this.roleManageDao = roleManageDao;
	}

	@Override
	public List<EssTree> getModelTreeByMap(Map<String, String> map) {
		return roleManageDao.getModelTreeByMap(map);
	}

	@Override
	public void updateTreeNodeOfRole(TreeNodeOfRole sr) {
		roleManageDao.updateTreeNodeOfRole(sr);
	}

	@Override
	public List<TreeNodeOfRole> getTreeNodesOfRole(Map<String, String> map) {
		return roleManageDao.getTreeNodesOfRole(map);
	}
	

	@Override
	public int getManageListCount() {
		return this.roleManageDao.getManageListCount();
	}

	@Override
	public List<TrueArchiveRole> getRoleManageList(Integer pageindex, Integer pagesize) {
		return this.roleManageDao.getRoleManageList(pageindex, pagesize);
	}

	@Override
	public boolean addRole(TrueArchiveRole acRole) {
		try {
			this.roleManageDao.addRole(acRole);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRole(TrueArchiveRole acRole) {
		try {
			this.roleManageDao.updateRole(acRole);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public TrueArchiveRole getOneAcRole(String id) {
		return this.roleManageDao.getOneAcRole(id);
	}

	@Override
	public boolean delRole(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.roleManageDao.delRole(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public int getCasualUserCount() {
		return this.roleManageDao.getCasualUserCount();
	}

	@Override
	public List<CasualUser> getCasualUserList(Map<String,String> map,Integer pageindex, Integer pagesize) {
		return this.roleManageDao.getCasualUserList(map,pageindex, pagesize);
	}

	@Override
	public String addCasualUserStart() {
		return this.roleManageDao.addCasualUserStart();
	}

	@Override
	public boolean updateCasualUser(CasualUser cUser,String menuFlag) {
		try {
			this.roleManageDao.updateCasualUser(cUser,menuFlag);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCasualUser(String id) {
		try {
			String[] idz = id.split(",");
			for(int i = 0; i < idz.length; i++) {
				String idx = idz[i];
				this.roleManageDao.deleteCasualUser(idx);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<CasualUser> getTreeNodesOfCasualUser(String id) {
		return this.roleManageDao.getTreeNodesOfCasualUser(id);
	}

	@Override
	public List<Menu> getRoleMenuData(String roleId) {
		List<Menu> mList = this.roleManageDao.getMenuData();
		List<String> sList = this.roleManageDao.getRoleMenuId(roleId);
		for(int i = 0; i < mList.size(); i++) {
			Menu menu = mList.get(i);
			String menuId = menu.getId();
			if(sList.contains(menuId)) {
				menu.setIsCheck("yes");
			} else {
				menu.setIsCheck("no");
			}
		}
		return mList;
	}

	@Override
	public List<String> getRoleMenuId(String roleId) {
		return this.roleManageDao.getRoleMenuId(roleId);
	}

	@Override
	public boolean setRoleMenuList(String roleId, String menuIdStr) {
		try {
			this.roleManageDao.deleteRoleMenu(roleId);
			if(!"".equals(menuIdStr)) {
				String[] menuIds = menuIdStr.split(",");
				for(int i = 0; i < menuIds.length; i++) {
					String menuId = menuIds[i];
					RoleMenu rm = new RoleMenu();
					rm.setMenuId(menuId);
					rm.setRoleId(roleId);
					this.roleManageDao.setRoleMenu(rm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<Employee> getRoleEmployeeList(String roleId,String deGuids) {
		//全部人员
		List<Employee> list = this.roleManageDao.getAllEmployee(deGuids);
		//其他角色对应人员
		List<String> otherCheckList = this.roleManageDao.getOtherCheckRoleEmployeeList(roleId);
		//本角色对应人员
		List<String> checkList = this.roleManageDao.getCheckRoleEmployeeList(roleId);
		Iterator<Employee> it = list.iterator();
		while(it.hasNext()) {
			Employee ey = it.next();
			String id = ey.getEmployeeGuid();
			if(otherCheckList.contains(id)) {
				it.remove();
			} else {
				if(checkList.contains(id)) {
					ey.setIsCheck("yes");
				}
			}
		}
		return list;
	}

	@Override
	public List<String> getCheckRoleEmployeeList(String roleId) {
		return this.roleManageDao.getCheckRoleEmployeeList(roleId);
	}

	@Override
	public boolean setRoleEmployeeList(String roleId, String eyIdStr) {
		try {
			this.roleManageDao.deleteRoleEmployee(roleId);
			if(!"".equals(eyIdStr)) {
				String[] eyIds = eyIdStr.split(",");
				for(int i = 0; i < eyIds.length; i++) {
					String eyId = eyIds[i];
					RoleEmployee rmy = new RoleEmployee();
					rmy.setRoleId(roleId);
					rmy.setEmployeeId(eyId);
					this.roleManageDao.setRoleEmployee(rmy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public RoleEmployee getRoleEmployeeListByEmId(String employeeId) {
		return this.roleManageDao.getRoleEmployeeListByEmId(employeeId);
	}

	@Override
	public List<Department> getEmployeeDepartmentList(String roleId) {
		 List<Department> list = this.roleManageDao.getEmployeeDepartmentList();
		 List<String> sList = this.roleManageDao.getEmployeeCheckedDepartment(roleId);
		 for(int i = 0; i < list.size(); i++) {
			 Department de = list.get(i);
			 String deId = de.getDepartmentGuid();
			 if(sList.contains(deId)) {
				 //de.setIsCheck("yes");
			 }
		 }
		 return list;
	}

	@Override
	public boolean checkDepartmentIsCheck(String deGuid,String roleId) {
		List<Employee> list = this.roleManageDao.checkDepartmentIsCheck(deGuid);
		//本角色对应人员
		List<String> checkList = this.roleManageDao.getCheckRoleEmployeeList(roleId);
		if(list == null || list.isEmpty()) {
			return false;
		} else {
			for(Employee ee : list) {
				String eguid = ee.getDepartmentGuid();
				if(checkList.contains(eguid)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public Set<String> getCasualCheckNodes(String casualId) {
		return this.roleManageDao.getCasualCheckNodes(casualId);
	}

	@Override
	public List<Map<String, String>> getTreeNodesSorts(String structureId,String treeId) {
		return this.roleManageDao.getTreeNodesSorts(structureId,treeId);
	}

	@Override
	public boolean checkCasualUserAllot(Map<String, String> map) {
		List<CasualUserDataRange> list = this.roleManageDao.checkCasualUserAllot(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean saveCasualUserDataRange(CasualUserDataRange cur) {
		try {
			this.roleManageDao.saveCasualUserDataRange(cur);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public CasualUserDataRange getCasualUserDataRange(Map<String, String> map) {
		return this.roleManageDao.getCasualUserDataRange(map);
	}

	@Override
	public boolean deleteCasualuserDataRange(String id) {
		try {
			this.roleManageDao.deleteCasualuserDataRange(id);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean checkSqlRight(Map<String, String> map) {
		return this.roleManageDao.getConditionResult(map);
	}

	@Override
	public TreeDataOfRole getRoleDataRange(Map<String, String> map) {
		return this.roleManageDao.getRoleDataRange(map);
	}

	@Override
	public boolean checkRoleAllot(Map<String, String> map) {
		List<TreeDataOfRole> list = this.roleManageDao.checkRoleAllot(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteRoleDataRange(String id) {
		try {
			this.roleManageDao.deleteRoleDataRange(id);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveRoleDataRange(TreeDataOfRole cur) {
		try {
			this.roleManageDao.saveRoleDataRange(cur);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<BtnOfRole> getBtnOfRole(Map<String, String> map) {
		return roleManageDao.getBtnOfRole(map);
	}

	@Override
	public void updateBtnOfRole(BtnOfRole tnr) {
		roleManageDao.updateBtnOfRole(tnr);
		
	}
}
