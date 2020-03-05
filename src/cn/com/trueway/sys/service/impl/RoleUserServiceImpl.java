package cn.com.trueway.sys.service.impl;

import java.util.List;

import cn.com.trueway.sys.dao.RoleUserDao;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.service.RoleUserService;

public class RoleUserServiceImpl implements RoleUserService {
	
	private RoleUserDao roleUserDao;
	
	public RoleUserDao getRoleUserDao() {
		return roleUserDao;
	}

	public void setRoleUserDao(RoleUserDao roleUserDao) {
		this.roleUserDao = roleUserDao;
	}

	@Override
	public String saveRoleUser(RoleUser roleUser) {
		String result = "";
		try {
			roleUserDao.insertRoleUser(roleUser);
			result = "success";
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result = "fail";
		}
		return result;
	}

	@Override
	public List<RoleUser> queryRoleUserById(String roleId, String name) throws Exception {
		return roleUserDao.selectRoleUserById(roleId, name);
	}

	@Override
	public RoleUser queryRoleUser(String roleId, String userId)
			throws Exception {
		return roleUserDao.selectRoleUser(roleId, userId);
	}

	@Override
	public String deleteRoleUser(String id) throws Exception {
		String result = "";
		try {
			roleUserDao.deleteRoleUser(id);
			result = "success";
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result = "fail";
		}
		return result;
	}

}
