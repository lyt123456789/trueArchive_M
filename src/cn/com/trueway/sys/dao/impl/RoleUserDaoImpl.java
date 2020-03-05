package cn.com.trueway.sys.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.sys.dao.RoleUserDao;
import cn.com.trueway.sys.pojo.RoleUser;

/**
 * ClassName: RoleUserDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年5月5日 下午2:49:38 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
@SuppressWarnings("unchecked")
public class RoleUserDaoImpl extends BaseDao implements RoleUserDao {

	@Override
	public void insertRoleUser(RoleUser roleUser) throws Exception {
		getEm().persist(roleUser);
	}

	@Override
	public List<RoleUser> selectRoleUserById(String roleId, String name) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from T_SYS_ROLE_USER t where t.ROLE_ID = :id");
			if(StringUtils.isNotBlank(name)){
				sql.append(" and (t.USER_NAME like '%").append(name).append("%' or t.USER_ID in (select distinct (l.EMPLOYEE_GUID) from T_SYS_EMPLOYEESPELL l where l.spell like '%").append(name).append("%' or l.spellhead like '%").append(name).append("%'))");
			}
			sql.append(" order by t.dept_name desc");
			Query query = getEm().createNativeQuery(sql.toString(), RoleUser.class);
			query.setParameter("id", roleId);
			List<RoleUser> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RoleUser selectRoleUser(String roleId, String userId)
			throws Exception {
		try {
			String hql = "from RoleUser t where t.roleId = :id and t.userId = :userId";
			Query query = getEm().createQuery(hql);
			query.setParameter("id", roleId);
			query.setParameter("userId", userId);
			List<RoleUser> list = query.getResultList();
			if (null != list && list.size()>0) {
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteRoleUser(String id) throws Exception {
		String sql = "delete from T_SYS_ROLE_USER t where t.GUID = :id";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
		
	}

}
