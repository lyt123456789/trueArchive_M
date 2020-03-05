package cn.com.trueway.archives.manage.dao.impl;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.dao.RoleManageDao;
import cn.com.trueway.archives.manage.pojo.BtnOfRole;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.RoleMenu;
import cn.com.trueway.archives.manage.pojo.TreeDataOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.archives.manage.util.ClobUtil;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

@SuppressWarnings("unchecked")
public class RoleManageDaoImpl extends BaseDao implements  RoleManageDao {

	@Override
	public List<EssTree> getModelTreeByMap(Map<String, String> map) {
		String id_business = map.get("id_business");
		
		String hql = "from ESS_TREE where idBusiness='" + id_business+"' ";
		Query query = super.getEm().createQuery(hql);
		List<EssTree> list =  query.getResultList();
		
		
		/*Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<EssTree> list = new ArrayList<EssTree>();
		StringBuffer sql = new StringBuffer("select t.id,t.title,t.id_parent_node,t.id_structure,t.isleaf from ESS_TREE t where t.id_business = 1 ");
		String conditionSql = map.get("conditionSql");
		if(conditionSql!=null&&!"".equals(conditionSql)){
			sql.append(conditionSql);
		}
		sql.append(" order by t.esorder");
		try {
			String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");
			try {
				Class.forName(jdbc_driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password); 
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();    
			while (rs.next()) {
				String _id = String.valueOf(rs.getObject("id"));
				String title = rs.getString("title");
				//String espath = rs.getString("espath");
				String id_parent_node = String.valueOf(rs.getObject("id_parent_node"));
				//String id_business = String.valueOf(rs.getObject("id_business"));
				String id_structure = String.valueOf(rs.getObject("id_structure"));
				//String id_modelstructure = String.valueOf(rs.getObject("id_modelstructure"));
				//String esorder = String.valueOf(rs.getObject("esorder"));
				String isleaf = String.valueOf(rs.getObject("isleaf"));
				//String id_seq = rs.getString("id_seq");
				EssTree et = new EssTree();
				et.setId(Integer.valueOf(_id));
				et.setTitle(title);
				//et.setEspath(espath);
				et.setIdParent(Integer.valueOf(id_parent_node));
				//et.setIdBusiness(Integer.valueOf(id_business));
				et.setIdStructure(Integer.valueOf(id_structure));
				//et.setIdModelStructure(Integer.valueOf(id_modelstructure));
				//et.setEsorder(Integer.valueOf(esorder));
				et.setIsLeaf(Integer.valueOf(isleaf));
				//et.setIdSeq(id_seq);
				list.add(et);
			}
			closeCon(rs,pstmt,con);   
		} catch (SQLException e) {
			e.printStackTrace();
			closeCon(rs,pstmt,con);   
		} */
        return list;
	}
	 public void closeCon(ResultSet rs, PreparedStatement sta, Connection con) { 
	        try { 
	            if(rs != null) { 
	                rs.close(); 
	            } 
	        } catch (SQLException e) { 
	            e.printStackTrace(); 
	        } finally { 
	            try { 
	                if(sta != null) { 
	                    sta.close(); 
	                } 
	            } catch (SQLException e) { 
	                e.printStackTrace(); 
	            } finally { 
	                try { 
	                    if(con != null) { 
	                        con.close();     
	                    } 
	                } catch (SQLException e) { 
	                    e.printStackTrace(); 
	                } 
	            } 
	        } 
	    }

	@Override
	@Transactional
	public void updateTreeNodeOfRole(TreeNodeOfRole sr) {
		if(sr.getId() == null || "".equals(sr.getId())){
			sr.setId(null);
			this.getEm().persist(sr);
		}else{
			this.getEm().merge(sr);
		}
	}

	@Override
	public List<TreeNodeOfRole> getTreeNodesOfRole(Map<String, String> map) {
		String roleId = map.get("roleId");
		String hql = "from TreeNodeOfRole where roleId='" + roleId+"' ";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}
	@Override
	public int getManageListCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("select roleid from t_truearchive_role");
		Query query = this.getEm().createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(null != list && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public List<TrueArchiveRole> getRoleManageList(Integer pageindex, Integer pagesize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from t_truearchive_role t order by roleindex asc");
		Query query = this.getEm().createNativeQuery(sql.toString(),TrueArchiveRole.class);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void addRole(TrueArchiveRole acRole) {
		/*StringBuffer sql = new StringBuffer();
		sql.append("insert into t_truearchive_role (name,roledescibe,roleindex,createtime) values(");
		sql.append("'"+acRole.getName()+"','"+ acRole.getRoleDescibe() +"','"+ acRole.getRoleIndex() +"','"+ acRole.getCreateTime() +"')");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.executeUpdate();
		getEm().flush();*/
		acRole.setId(null);
		this.getEm().persist(acRole);
		this.getEm().flush();
	}
	
	@Transactional
	@Override
	public void updateRole(TrueArchiveRole acRole) {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_truearchive_role t set t.name=?,t.roledescibe=?,t.roleindex=?,t.changetime=? where t.roleid=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, acRole.getName());
		query.setParameter(2, acRole.getRoleDescibe());
		query.setParameter(3, acRole.getRoleIndex());
		query.setParameter(4, acRole.getChangeTime());
		query.setParameter(5, acRole.getId());
		query.executeUpdate();
		getEm().flush();
	}

	@Override
	public TrueArchiveRole getOneAcRole(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from TrueArchiveRole where id=?");
		Query query = this.getEm().createQuery(sql.toString());
		query.setParameter(1, id);
		List<TrueArchiveRole> list = query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public void delRole(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_truearchive_role t where t.roleid=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Override
	public int getCasualUserCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("select casualid from t_casual_user");
		Query query = this.getEm().createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(null != list && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}
	
	@Override
	public List<CasualUser> getCasualUserList(Map<String,String> map,Integer pageindex, Integer pagesize) {
		StringBuffer sql = new StringBuffer();
		String username = map.get("username");
		String password = map.get("password");
		sql.append("select t.* from t_casual_user t where 1=1 ");
		if(username!=null&&!"".equals(username)){
			sql.append(" and t.casualname='"+username+"' ");
		}
		if(password!=null&&!"".equals(password)){
			sql.append(" and t.casualpassword='"+password+"' ");
		}
		sql.append("  order by casualindex asc ");
		Query query = this.getEm().createNativeQuery(sql.toString(),CasualUser.class);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		List<CasualUser> list = query.getResultList();
		return list;
	}
	
	@Override
	public String addCasualUserStart() {
		CasualUser cUser = new CasualUser();
		cUser.setId(null);
		this.getEm().persist(cUser);
		this.getEm().flush();
		return cUser.getId();
	}
	
	@Transactional
	@Override
	public void updateCasualUser(CasualUser cUser,String menuFlag) {
		if("yes".equals(menuFlag)) {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_casual_user t set t.menuofright=? where t.casualid=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, cUser.getMenuRight());
			query.setParameter(2, cUser.getId());
			query.executeUpdate();
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_casual_user set casualname=?,casualpassword=?,casualstarttime=?,casualendtime=?,describe=?,casualindex=? where casualid=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, cUser.getCasualName());
			query.setParameter(2, cUser.getCasualPassword());
			query.setParameter(3, cUser.getCasualStartTime());
			query.setParameter(4, cUser.getCasualEndTime());
			query.setParameter(5, cUser.getDescribe());
			query.setParameter(6, cUser.getIndex());
			query.setParameter(7, cUser.getId());
			query.executeUpdate();
		}
		getEm().flush();
	}
	
	@Transactional
	@Override
	public void deleteCasualUser(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_casual_user t where t.casualid=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Override
	public List<CasualUser> getTreeNodesOfCasualUser(String id) {
		String hql = "from CasualUser where id='" + id+"' ";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}
	
	@Override
	public List<Menu> getMenuData() {
		String sql = "select * from t_truearchive_menu order by menuindex asc";
		Query query = this.getEm().createNativeQuery(sql, Menu.class);
		return query.getResultList();
	}
	
	@Override
	public List<String> getRoleMenuId(String roleId) {
		String sql = "select menuid from t_truearchive_role_menu where roleid='"+roleId+"'";
		Query query = this.getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void deleteRoleMenu(String roleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_truearchive_role_menu t where t.roleid=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, roleId);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Transactional
	@Override
	public void setRoleMenu(RoleMenu rm) {
		rm.setId(null);
		this.getEm().persist(rm);
		this.getEm().flush();
	}
	
	@Override
	public List<Employee> getAllEmployee(String deGuids) {
		String sql = "select * from zwkj_employee where department_guid in("+deGuids+")";
		Query query = this.getEm().createNativeQuery(sql, Employee.class);
		return query.getResultList();
	}
	
	@Override
	public List<String> getOtherCheckRoleEmployeeList(String roleId) {
		String sql = "select employee_id from t_sys_role_employee where role_id<>'"+roleId+"'";
		Query query = this.getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public List<String> getCheckRoleEmployeeList(String roleId) {
		String sql = "select employee_id from t_sys_role_employee where role_id='"+roleId+"'";
		Query query = this.getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void deleteRoleEmployee(String roleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_sys_role_employee t where t.role_id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, roleId);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Override
	public void setRoleEmployee(RoleEmployee rey) {
		rey.setId(null);
		this.getEm().persist(rey);
		this.getEm().flush();
	}
	
	@Override
	public RoleEmployee getRoleEmployeeListByEmId(String employeeId) {
		String sql = "select * from t_sys_role_employee where employee_id='"+employeeId+"'";
		Query query = this.getEm().createNativeQuery(sql,RoleEmployee.class);
		List<RoleEmployee> list = query.getResultList();		
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Department> getEmployeeDepartmentList() {
		String sql = "select * from zwkj_department tt  connect by  tt.superior_guid  = prior tt.department_guid start with tt.department_guid='2'";
	    Query query = this.getEm().createNativeQuery(sql,Department.class);
	    List<Department> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<String> getEmployeeCheckedDepartment(String roleId) {
		String sql = "select distinct d.department_guid from t_sys_role_employee se";
		sql += " left join zwkj_employee e on se.employee_id= e.employee_guid left join zwkj_department d on d.department_guid=e.department_guid";
		sql += " where d.department_guid is not null and d.department_name is not null and se.role_id='";
		sql += roleId+"'";
		Query query = this.getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public List<Employee> checkDepartmentIsCheck(String deGuid) {
		String sql = "select * from zwkj_employee where department_guid='"+deGuid+"'";
		Query query = this.getEm().createNativeQuery(sql, Employee.class);
		return query.getResultList();
	}
	
	@Override
	public Set<String> getCasualCheckNodes(String casualId) {
		String sql = "select casualid,menuofright from t_casual_user where casualid='"+casualId+"'";
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			Object[] obj = list.get(0);
			String nodes = obj[1]==null?"":ClobUtil.ClobTostring((Clob)obj[1]);
			if("".equals(nodes)) {
				return null;
			} else {
				String[] nodeArray = nodes.split(",");
				Set<String> set = new HashSet<String>(Arrays.asList(nodeArray));
				return set;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public List<Map<String, String>> getTreeNodesSorts(String structureId,String treeId) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT d.id_childstructure AS IDCHILD,");
		sql.append("CASE s.estype WHEN 'project' THEN '项目目录' WHEN 'file' THEN '案卷目录' WHEN 'innerFile' THEN '卷内目录'");
		sql.append(" WHEN 'document' THEN '电子文件' END AS TITLE,");
		sql.append("(SELECT c.id_package FROM t_archive_CHILDSTRUCTURE c WHERE c.id_childstructure = '"+ structureId +"' and c.id_package='"+treeId+"') AS PID");
		sql.append(" FROM t_archive_CHILDSTRUCTURE d LEFT JOIN t_archive_STRUCTURE s ON d.id_childstructure = s.id");
		sql.append(" WHERE d.esstatus='启用' CONNECT BY d.id_structure=PRIOR d.id_childstructure START WITH d.id_childstructure='"+ structureId +"'  and d.id_package='"+treeId+"'");
	/*	sql.append("SELECT d.id_childstructure AS IDCHILD,");
		sql.append("CASE s.estype WHEN 'project' THEN '项目目录' WHEN 'file' THEN '案卷目录' WHEN 'innerFile' THEN '卷内目录'");
		sql.append(" WHEN 'document' THEN '电子文件' END AS TITLE,");
		sql.append("(SELECT c.id_package FROM ESS_CHILDSTRUCTURE c WHERE c.id_childstructure = '"+ structureId +"' and c.id_package='"+treeId+"') AS PID");
		sql.append(" FROM ESS_CHILDSTRUCTURE d LEFT JOIN ESS_STRUCTURE s ON d.id_childstructure = s.id");
		sql.append(" WHERE d.esstatus='启用' CONNECT BY d.id_structure=PRIOR d.id_childstructure START WITH d.id_childstructure='"+ structureId +"'  and d.id_package='"+treeId+"'");*/
		try {
			String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password","hibernate.properties");
			/*String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");*/
			try {
				Class.forName(jdbc_driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password); 
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();    
			while (rs.next()) {
				String idchild = String.valueOf(rs.getObject("idchild"));
				String title = rs.getString("title");
				String pid = String.valueOf(rs.getObject("pid"));
				Map<String,String> map = new HashMap<String,String>();
				map.put("idchild", idchild);
				map.put("title", title);
				map.put("pid", pid);
				list.add(map);
			}
			closeCon(rs,pstmt,con);   
		} catch (SQLException e) {
			e.printStackTrace();
			closeCon(rs,pstmt,con);   
		} 
        return list;
	}
	
	@Override
	public List<CasualUserDataRange> checkCasualUserAllot(Map<String, String> map) {
		String sql = "select * from t_casualuser_data_license where 1=1";
		String casualId = map.get("casualId");
		if(casualId != null && !"".equals(casualId)) {
			sql += " and casualid='" + casualId + "'";
		}
		String structureId = map.get("structureId");
		if(structureId != null && !"".equals(structureId)) {
			sql += " and treenode='" + structureId + "'";
		}
		String idchild = map.get("idchild");
		if(idchild != null && !"".equals(idchild)) {
			sql += " and dataidchild='" + idchild + "'";
		}
		Query query = this.getEm().createNativeQuery(sql, CasualUserDataRange.class);
		List<CasualUserDataRange> list = query.getResultList();
		return list;
	}
	
	@Transactional
	@Override
	public void saveCasualUserDataRange(CasualUserDataRange cur) {
		if("newAdd".equals(cur.getId())) {
			CasualUserDataRange newCur = new CasualUserDataRange();
			newCur.setCasualId(cur.getCasualId());
			newCur.setTreeNode(cur.getTreeNode());
			newCur.setDataIdChild(cur.getDataIdChild());
			newCur.setDataFabric(cur.getDataFabric());
			newCur.setCondition(cur.getCondition());
			newCur.setConditionShow(cur.getConditionShow());
			newCur.setSqlCondition(cur.getSqlCondition());
			this.getEm().persist(newCur);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update T_CASUALUSER_DATA_LICENSE t set t.condition=?,t.conditionshow=?,t.sqlcondition=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1,cur.getCondition());
			query.setParameter(2,cur.getConditionShow()); 
			query.setParameter(3,cur.getSqlCondition());
			query.setParameter(4,cur.getId()); 
			query.executeUpdate();
		}
		this.getEm().flush();
	}
	
	@Override
	public CasualUserDataRange getCasualUserDataRange(Map<String, String> map) {
		String sql = "select * from t_casualuser_data_license where 1=1";
		String id = map.get("id");
		if(id != null && !"".equals(id)) {
			sql += " and id='"+id+"'";
		}
		String casualId = map.get("casualId");
		if(casualId != null && !"".equals(casualId)) {
			sql += " and casualid='"+casualId+"'";
		}
		String treeNode = map.get("treeNode");
		if(treeNode != null && !"".equals(treeNode)) {
			sql += " and treenode='"+treeNode+"'";
		}
		String dataIdChild = map.get("dataIdChild");
		if(dataIdChild != null && !"".equals(dataIdChild)) {
			sql += " and dataidchild='"+dataIdChild+"'";
		}
		Query query = this.getEm().createNativeQuery(sql, CasualUserDataRange.class);
		List<CasualUserDataRange> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public void deleteCasualuserDataRange(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_casualuser_data_license t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
	
	@Override
	public boolean getConditionResult(Map<String, String> map) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		String tableName = "ESP_" + map.get("tableName"); 
		String sqlCondition = map.get("sqlCondition");
		String sql = "select * from " + tableName + " where 1=1 and " + sqlCondition;
		try {
			String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password","hibernate.properties");
			/*String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");*/
			try {
				Class.forName(jdbc_driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password); 
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();    
			closeCon(rs,pstmt,con);   
		} catch (Exception e) {
			e.printStackTrace();
			closeCon(rs,pstmt,con);   
			return false;
		} 
		return true;
	}
	@Override
	public TreeDataOfRole getRoleDataRange(Map<String, String> map) {
		String sql = "select * from T_ARCHIVE_ROLE_TREEDATA where 1=1";
		String id = map.get("id");
		if(id != null && !"".equals(id)) {
			sql += " and id='"+id+"'";
		}
		String roleId = map.get("roleId");
		if(roleId != null && !"".equals(roleId)) {
			sql += " and roleId='"+roleId+"'";
		}
		String treeNode = map.get("treeNode");
		if(treeNode != null && !"".equals(treeNode)) {
			sql += " and treenode='"+treeNode+"'";
		}
		String dataIdChild = map.get("dataIdChild");
		if(dataIdChild != null && !"".equals(dataIdChild)) {
			sql += " and dataidchild='"+dataIdChild+"'";
		}
		Query query = this.getEm().createNativeQuery(sql, TreeDataOfRole.class);
		List<TreeDataOfRole> list = query.getResultList();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	@Override
	public List<TreeDataOfRole> checkRoleAllot(Map<String, String> map) {
		String sql = "select * from T_ARCHIVE_ROLE_TREEDATA where 1=1";
		String roleId = map.get("roleId");
		if(roleId != null && !"".equals(roleId)) {
			sql += " and roleid='" + roleId + "'";
		}
		String structureId = map.get("structureId");
		if(structureId != null && !"".equals(structureId)) {
			sql += " and treenode='" + structureId + "'";
		}
		String idchild = map.get("idchild");
		if(idchild != null && !"".equals(idchild)) {
			sql += " and dataidchild='" + idchild + "'";
		}
		Query query = this.getEm().createNativeQuery(sql, TreeDataOfRole.class);
		List<TreeDataOfRole> list = query.getResultList();
		return list;
	}
	@Override
	public void deleteRoleDataRange(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from T_ARCHIVE_ROLE_TREEDATA t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}
	@Override
	@Transactional
	public void saveRoleDataRange(TreeDataOfRole cur) {
		if("newAdd".equals(cur.getId())) {
			TreeDataOfRole newCur = new TreeDataOfRole();
			newCur.setRoleId(cur.getRoleId());
			newCur.setTreeNode(cur.getTreeNode());
			newCur.setDataIdChild(cur.getDataIdChild());
			newCur.setDataFabric(cur.getDataFabric());
			newCur.setCondition(cur.getCondition());
			newCur.setConditionShow(cur.getConditionShow());
			newCur.setSqlCondition(cur.getSqlCondition());
			this.getEm().persist(newCur);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update T_ARCHIVE_ROLE_TREEDATA t set t.condition=?,t.conditionshow=?,t.sqlcondition=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1,cur.getCondition());
			query.setParameter(2,cur.getConditionShow()); 
			query.setParameter(3,cur.getSqlCondition());
			query.setParameter(4,cur.getId()); 
			query.executeUpdate();
		}
		this.getEm().flush();
	}
	@Override
	public List<BtnOfRole> getBtnOfRole(Map<String, String> map) {
		String roleId = map.get("roleId");
		String hql = "from BtnOfRole where roleId='" + roleId+"' ";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}
	@Override
	public void updateBtnOfRole(BtnOfRole tnr) {
		if(tnr.getId() == null || "".equals(tnr.getId())){
			tnr.setId(null);
			this.getEm().persist(tnr);
		}else{
			this.getEm().merge(tnr);
		}
		
	}
	
	
}
