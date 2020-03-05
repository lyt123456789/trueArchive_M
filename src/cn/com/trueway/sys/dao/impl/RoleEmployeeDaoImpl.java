package cn.com.trueway.sys.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.sys.dao.RoleEmployeeDao;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.util.SysConstant;

/**
 * ClassName: RoleEmployeeDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年4月22日 下午5:33:35 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
@SuppressWarnings("unchecked")
public class RoleEmployeeDaoImpl extends BaseDao implements RoleEmployeeDao {
	
	@Override
	public List<RoleUser> queryRoleEmployeesByRole() throws DataAccessException {
		try {
			String sql = "select r.ROLE_ID from T_SYS_ROLE r where r.ROLE_NAME='"+SysConstant.MSG_ADMIN+"'";
			Query _query = super.getEm().createNativeQuery(sql);
			List<String> list = _query.getResultList();
			
			if (null != list && list.size() > 0) {
				/**
				 * 根据roleId到RoleEmployee查找数据
				 */
				String hql = "from RoleUser r where r.role_id in ('" + list.get(0)+ "')";
				Query query = super.getEm().createQuery(hql);
				List<RoleUser> roleEmployeeList = query.getResultList();
				return roleEmployeeList;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RoleUser> queryRoleEmployeesByUserId(String userId) throws DataAccessException {
		try {
			String sql = "from RoleUser r where r.employee_id = '"+userId+"' and r.role_name like '%"+SysConstant.ELE_ADMIN+"'";
			Query query = super.getEm().createQuery(sql);
			List<RoleUser> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
