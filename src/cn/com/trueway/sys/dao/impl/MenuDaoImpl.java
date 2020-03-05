package cn.com.trueway.sys.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.sys.dao.MenuDao;
import cn.com.trueway.sys.pojo.Menu;

@SuppressWarnings("unchecked")
public class MenuDaoImpl extends BaseDao implements MenuDao{

	private Object sb;

	@Override
	public Menu findMenuById(String id) {
		return getEm().find(Menu.class, id);
	}

	@Override
	public List<Menu> findChildMenu(String menuFatherId, String rank, String siteId) {
		String sql = "select t.* from t_sys_menu t  where t.menu_rank like '"+rank+"' ";
		if (CommonUtil.stringNotNULL(menuFatherId)) {
			sql += " and t.menu_fater_id= '"+menuFatherId+"' ";
		} else {
			sql += " and (t.menu_fater_id is null or t.menu_fater_id='') ";
			sql += " and (t.site_id is null or t.site_id = '' or t.site_id = '"+ siteId +"')";
			
		}
		sql += " order by t.menu_sort  asc";
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}
	
	public List<Menu> findChildMenuNew(String menuFatherId, String rank, String siteId) {
		String sql = "select t.* from t_sys_menu t  where t.menu_rank like '"+rank+"' ";
		if (CommonUtil.stringNotNULL(menuFatherId)) {
			sql += " and t.menu_fater_id= '"+menuFatherId+"' ";
		} else {
			sql += " and (t.menu_fater_id is null or t.menu_fater_id='') ";
			if(siteId == null || "".equals(siteId)){
				sql += " and (t.site_id is null or t.site_id = '') ";
			}else{
				sql += " and t.site_id = '"+ siteId +"' ";
			}
			
		}
		sql += " order by t.menu_sort  asc";
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	@Transactional
	public void saveMenu(Menu menu) throws Exception {
		getEm().persist(menu);
	}

	@Override
	@Transactional
	public void updateMenu(Menu menu) throws Exception {
		getEm().merge(menu);
	}

	@Override
	public List<Menu> getMenuListByUserId(String menuId, String userId,
			String siteId) {
		String siteid = SystemParamConfigUtil2.getParamValueByParam("menuSiteid");
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append(" select distinct t.*, t.rowid from T_SYS_MENU t, t_sys_role t2, t_sys_role_menu t3 , t_sys_role_user t4 ")
			  .append(" where t.menu_id = t3.menu_id and t2.role_id = t3.role_id and t4.role_id = t2.role_id and t.MENU_STATUS = 1 and t4.user_id = :id ")
			/*.append(" and t2.site_id='"+siteId+"'");*/
		.append(" ");
		/*if(siteid.contains(siteId)){
			buffer.append(" and (t.site_id in ("+siteid+") or t.site_id is null)");
		}else{
			buffer.append("  and (t.site_id = '"+siteId+"' or  t.site_id is null)" );
		}*/
		buffer.append(" order by t.menu_sort asc , t.menu_fater_id  Nulls First");
		Query query = getEm().createNativeQuery(buffer.toString(), Menu.class);
		query.setParameter("id", userId);
		return query.getResultList();
	}
	
	@Override
	public List<Menu> getAllMenuList() {
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append(" select distinct t.*, t.rowid from T_SYS_MENU t order by t.menu_sort asc");
		Query query = getEm().createNativeQuery(buffer.toString(), Menu.class);
		return query.getResultList();
	}

	@Override
	public List<Menu> findMenuList(String rank) throws Exception {
		//String sql = "select t.* from t_sys_menu t where t.menu_rank='"+0+"'";
		//于2017-11-30修改如下
		String sql = "select t.* from t_sys_menu t where t.menu_rank='"+rank+"'";
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	public List<Menu> selectMenuList(String menuName) throws Exception {
		String sql = "select t.* from t_sys_menu t where t.MENU_NAME like'%"+menuName+"%'";
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	public void delete(String menuId) throws DataAccessException {
		String sql = "delete from t_sys_menu t where t.MENU_ID = :id";
		getEm().createNativeQuery(sql).setParameter("id", menuId).executeUpdate();
		
	}
	
	@Override
	public int getCountBySql(Map<String, String> map){
		String countSql = map.get("countSql");
		String userId = map.get("userId");
		Query query = getEm().createNativeQuery(countSql);
		if(countSql.contains(":userId")){
			query.setParameter("userId", userId);
			
		}
		return Integer.parseInt(query.getSingleResult().toString());
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
	public List<Menu> findMenuListNew(String rank, String siteId) {
		String sql = "select t.* from t_sys_menu t where t.menu_rank='"+rank+"' ";
		if(siteId == null || "".equals(siteId)){
			sql += " and (t.SITE_ID is null or t.SITE_ID = '')";
		}else{
			sql += " and t.SITE_ID = '"+siteId+"'";
		}
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	public List<Menu> selectMenuListNew(String menuName, String siteId) {
		String sql = "select t.* from t_sys_menu t where t.MENU_NAME like'%"+menuName+"%' ";
		if(siteId == null || "".equals(siteId)){
			sql += " and (t.site_id is null or t.site_id = '') ";
		}else{
			sql += " and t.site_id = '"+ siteId +"' ";
		}
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	public List<Menu> findFamilyMenuByFid(String menuId) {
		String sql = "select * from t_sys_menu t where t.MENU_ID = '"+menuId+"' or t.MENU_FATER_ID = '"+menuId+"'";
		return getEm().createNativeQuery(sql, Menu.class).getResultList();
	}

	@Override
	public void deleteNew(String menuId) {
		String sql = "delete from t_sys_menu t where t.MENU_ID = :id or t.MENU_FATER_ID = :fid";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("id", menuId);
		query.setParameter("fid", menuId);
		query.executeUpdate();
	}

	@Override
	public void deleteMenuRole(String menuId) {
		String sql = "delete from T_SYS_ROLE_MENU t where t.MENU_ID in (select s.MENU_ID from T_SYS_MENU s where s.MENU_ID = :id or s.MENU_FATER_ID = :fid)";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("id", menuId);
		query.setParameter("fid", menuId);
		query.executeUpdate();
	}

}
