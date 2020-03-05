package cn.com.trueway.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.trueway.archives.manage.pojo.BtnDictionary;
import cn.com.trueway.sys.dao.RoleDao;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.vo.RoleView;
import cn.com.trueway.sys.service.RoleService;
import cn.com.trueway.workflow.core.pojo.Employee;

public class RoleServiceImpl implements RoleService{
	
	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public Role findRoleById(String id) {
		return roleDao.findRoleById(id);
	}

	@Override
	public int findRoleCount(Map<String, String> map) {
		return roleDao.findRoleCount(map);
	}

	@Override
	public List<Role> findRoleList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		return roleDao.findRoleList(map, pageIndex, pageSize);
	}

	@Override
	public JSONObject saveRole(Role role, Employee emp) {
		JSONObject result = new JSONObject();
		try {
			roleDao.saveRole(role);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public JSONObject updateRole(Role role, Employee emp) {
		JSONObject result = new JSONObject();
		try {
			roleDao.updateRole(role);
			result.put("success", true);
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
			result.put("success", false);
		}
		return result;
	}

	@Override
	public String deleteRole(String id, Employee emp) {
		String result = "";
		try {
			roleDao.deleteRole(id);
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
	public List<String[]> getSites() {
		List<Object[]> os = roleDao.getSites();
		if(!os.isEmpty()){
			List<String[]> rs = new ArrayList<String[]>();
			for(Object[] o :os){
				String[] s = {(String) o[0],(String) o[1]};
				rs.add(s);
			}
			return rs;
		}else{
			return null;
		}
	}

	@Override
	public String deleteRoleNew(String roleId, Employee emp) {
		String result = "";
		try {
			roleDao.deleteRole(roleId);
			roleDao.deleteRoleUser(roleId);
			roleDao.deleteRoleMenu(roleId);
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
	public List<RoleView> findRoleViewList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		return roleDao.findRoleViewList(map, pageIndex, pageSize);
	}

	@Override
	public List<BtnDictionary> findBtnList(Map<String, String> map, Integer pageIndex, Integer pageSize) {
		return roleDao.findBtnList(map, pageIndex, pageSize);
	}
	
}
