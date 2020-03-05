package cn.com.trueway.sys.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.manage.pojo.BtnDictionary;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.sys.dao.RoleDao;
import cn.com.trueway.sys.pojo.Role;
import cn.com.trueway.sys.pojo.vo.RoleView;

/**
 * ClassName: RoleDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年5月6日 下午3:45:30 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
@SuppressWarnings("unchecked")
public class RoleDaoImpl extends BaseDao implements RoleDao{

	@Override
	public Role findRoleById(String id) {
		return getEm().find(Role.class, id);
	}

	@Override
	public int findRoleCount(Map<String, String> map) {
		return 0;
	}

	@Override
	public List<Role> findRoleList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		String roleName = null;
		String siteId = null;
		if (null != map) {
			roleName = map.get("roleName");
			siteId = map.get("siteId");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from t_sys_role t where 1=1 ");
		if (StringUtils.isNotBlank(roleName)) {
			sql.append(" and t.ROLE_NAME like '%"+roleName+"%' ");
		}
		if (StringUtils.isNotBlank(siteId)) {
			sql.append(" and t.site_id = '"+siteId+"' ");
		}
		sql.append(" order by role_sort desc ");
		return getEm().createNativeQuery(sql.toString(), Role.class).getResultList();
	}

	@Override
	@Transactional
	public void saveRole(Role role) throws Exception{
		getEm().persist(role);
	}

	@Override
	@Transactional
	public void updateRole(Role role) throws Exception{
		getEm().merge(role);
	}

	@Override
	public void deleteRole(String id) {
		try {
			String sql = "delete from T_SYS_ROLE where ROLE_ID = :id";
			Query query = getEm().createNativeQuery(sql);
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Object[]> getSites() {
		String sql = "select d.department_guid, d.department_name from zwkj_department d where d.department_guid in"
				+ " (select distinct (t.siteid) from ZWKJ_EMPLOYEE t)";
		Query q = this.getEm().createNativeQuery(sql);
		List<Object[]> os = q.getResultList();
		return os;
	}

	@Override
	public void deleteRoleUser(String roleId) {
		try {
			String sql = "delete from T_SYS_ROLE_USER where ROLE_ID = :id";
			Query query = getEm().createNativeQuery(sql);
			query.setParameter("id", roleId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteRoleMenu(String roleId) {
		try {
			String sql = "delete from T_SYS_ROLE_MENU where ROLE_ID = :id";
			Query query = getEm().createNativeQuery(sql);
			query.setParameter("id", roleId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public List<RoleView> findRoleViewList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		String roleName = null;
		String siteId = null;
		if (null != map) {
			roleName = map.get("roleName");
			siteId = map.get("siteId");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from roal_view t where 1=1 ");
		if (StringUtils.isNotBlank(roleName)) {
			sql.append(" and t.ROLE_NAME like '%"+roleName+"%' ");
		}
		if (StringUtils.isNotBlank(siteId)) {
			sql.append(" and t.site_id = '"+siteId+"' ");
		}
		sql.append(" order by siteid desc,role_sort desc ");
		return getEm().createNativeQuery(sql.toString(), RoleView.class).getResultList();
	}

	@Override
	public List<BtnDictionary> findBtnList(Map<String, String> map, Integer pageIndex, Integer pageSize) {
//		String roleName = null;
//		String siteId = null;
//		if (null != map) {
//			roleName = map.get("roleName");
//			siteId = map.get("siteId");
//		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from t_btn_dic t where 1=1 ");
//		if (StringUtils.isNotBlank(roleName)) {
//			sql.append(" and t.ROLE_NAME like '%"+roleName+"%' ");
//		}
//		if (StringUtils.isNotBlank(siteId)) {
//			sql.append(" and t.site_id = '"+siteId+"' ");
//		}
		sql.append(" order by btn_sort desc ");
		return getEm().createNativeQuery(sql.toString(), BtnDictionary.class).getResultList();
	}
	
	
}
