package cn.com.trueway.workflow.set.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.sys.pojo.FeedBack;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.pojo.EmployeeSpe;

/**
 * 人员管理的Dao层实现类
 * 
 * @author liwei
 * 
 */
@SuppressWarnings("unchecked")
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {



	public Employee queryEmployeeById(String userId) throws DataAccessException {
		List<Employee> rr=(List<Employee>)getEm().createQuery(
				"from Employee e where e.employeeGuid='" + userId + "' order by e.tabindex").getResultList();
	
		if (rr != null && rr.size() > 0) 
			 return rr.get(0);
			 else return null;
	}

	public Collection<Employee> queryEmployees(String departmentId)
			throws DataAccessException {
		String sql = "select * from (select * from zwkj_employee t where t.ISLEAVE!='1' and t.department_guid = '" + departmentId + "'" +
				" union all " +
				" select t.department_guid,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email, " +
				" t.employee_jobtitles,t.employee_professionaltitle,t.employee_status, t.employee_sex, t.employee_birthday, t.employee_country, " +
				" t.employee_province, t.employee_city, t.employee_officeaddress, t.employee_officephone, t.employee_officezipcode, t.employee_officefax, " +
				" t.employee_homephone, t.employee_homeaddress, t.employee_mobile, t.employee_politicstatus, t.employee_academictitle, t.employee_educationrecord, " +
				" t.employee_phototype, mr.sortindex as tabindex, t.employee_native, t.employee_nationality, t.employee_profession, t.employee_description, " +
				" t.employee_homezipcode, t.employee_isdepartmentmanager, t.employee_isdeleted, t.employee_isdeleteddesc, t.employee_photo, t.employee_dn, " +
				" t.employee_mailstatus, t.isaliasuser, t.is_checked, t.is_kq, t.is_showallprocess, t.operate_log_id, t.is_admin, t.mployee_mobile_short, " +
				" t.jobcode, t.isleave, t.status,t.SITEID, t.SSNUM, t.POSITION, t.STAFFIDS, t.CITIZENCARD, t.XXDH, t.SFZNUM from t_wf_emp_multDept_relation mr, zwkj_employee t where t.ISLEAVE!='1' and t.employee_guid = mr.empid and mr.deptid = '" + departmentId + "') t " +
				" order by t.tabindex asc ";
		Query query =  getEm().createNativeQuery(sql, Employee.class);
		return query.getResultList();
		/*return getEm().createQuery(
				"from Employee e where (e.departmentGuid='" + departmentId+"' or e.jobcode like '%"+departmentId+"%')"
						+ " and (e.isAdmin is null or e.isAdmin!=1) order by e.tabindex").getResultList();*/
	}
	
	public Collection<Employee> queryEmployeesByIds(String departmentId,String ids)
			throws DataAccessException {
		String[] id =ids.split(",");
		String querySql = "";
		for(int i=0;i<id.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  t.employee_guid in ('";
			for(int j=0;j<900&&(i+j)<id.length;j++){
				querySql +=id[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		/*String hql ="from Employee e where (e.departmentGuid='" + departmentId+"'  or e.jobcode like '%"+departmentId+"%') and (e.isAdmin is null or e.isAdmin!=1) ";
		if(ids!=null&&!"".equals(ids)){
			hql +=" and ("+querySql+")";
		}
		hql +=" order by e.tabindex ";*/
		String sql = " select * from (select * from zwkj_employee t where t.ISLEAVE!='1' and t.department_guid = '" + departmentId + "' " +
				" union all " +
				" select t.department_guid,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email, " +
				" t.employee_jobtitles,t.employee_professionaltitle,t.employee_status, t.employee_sex, t.employee_birthday, t.employee_country, " +
				" t.employee_province, t.employee_city, t.employee_officeaddress, t.employee_officephone, t.employee_officezipcode, t.employee_officefax, " +
				" t.employee_homephone, t.employee_homeaddress, t.employee_mobile, t.employee_politicstatus, t.employee_academictitle, t.employee_educationrecord, " +
				" t.employee_phototype, mr.sortindex as tabindex, t.employee_native, t.employee_nationality, t.employee_profession, t.employee_description, " +
				" t.employee_homezipcode, t.employee_isdepartmentmanager, t.employee_isdeleted, t.employee_isdeleteddesc, t.employee_photo, t.employee_dn, " +
				" t.employee_mailstatus, t.isaliasuser, t.is_checked, t.is_kq, t.is_showallprocess, t.operate_log_id, t.is_admin, t.mployee_mobile_short, " +
				" t.jobcode,t.isleave, t.status,t.siteid,t.ssnum,t.position,t.staffids,t.citizencard,t.xxdh,t.sfznum from t_wf_emp_multDept_relation mr, zwkj_employee t where t.ISLEAVE!='1' and t.employee_guid = mr.empid and mr.deptid = '" + departmentId + "') t " +
				" where (t.is_admin is null or t.is_admin != 1) ";
		if(ids!=null&&!"".equals(ids)){
			sql +=" and (" + querySql + ")";
		}
		sql = sql + " order by t.tabindex asc ";
		/*return getEm().createQuery(hql).getResultList();*/
		Query query =  getEm().createNativeQuery(sql, Employee.class);
		return query.getResultList();
	}
	/**
	 * 
	 * @Title: queryEmployeeByDN
	 * @Description: 根据dn获取所有用户
	 * @param @param dn
	 * @param @return
	 * @param @throws DataAccessException    设定文件
	 * @return List<Employee>    返回类型
	 * @throws
	 */
	public List<EmployeeSpe> queryEmployeeByDN(String dn) throws DataAccessException {
		List<EmployeeSpe> rr=getEm().createQuery("from EmployeeSpe e where e.employeeDn like '%" + dn + "' order by e.tabindex").getResultList();
		return rr;
			 
	}

	public List<EmployeeSpe> queryEmployeeByDepIds(String depids)
			throws DataAccessException {
		return super.getEm().createQuery("from EmployeeSpe e where e.departmentGuid in ("+depids+") or e.departmentGuid in (select t.departmentGuid from Department t where t.superiorGuid in ("+depids+")) order by e.tabindex").getResultList();
	}

	public List<EmployeeSpe> queryEmployeeBySuperDepIds(String depids)
			throws DataAccessException {
		return super.getEm().createQuery("from EmployeeSpe e where e.departmentGuid in (select t.departmentGuid from Department t where t.superiorGuid in ("+depids+")) order by e.tabindex").getResultList();
	}

	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(
			String departmentid) throws DataAccessException {
		String sql=
				"select e.employee_guid,\n" +
						"       e.employee_name,\n" + 
						"       z.department_guid,\n" + 
						"       z.department_name,\n" + 
						"       z.department_shortdn\n" + 
						//"  from (select * from (select * from zwkj_employee t where t.department_guid = :id " +
						"  from (select * from (select * from zwkj_employee t where t.ISLEAVE!='1' " +
						" union all " +
						" select mr.deptId as department_guid,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email, " +
						" t.employee_jobtitles,t.employee_professionaltitle,t.employee_status, t.employee_sex, t.employee_birthday, t.employee_country, " +
						" t.employee_province, t.employee_city, t.employee_officeaddress, t.employee_officephone, t.employee_officezipcode, t.employee_officefax, " +
						" t.employee_homephone, t.employee_homeaddress, t.employee_mobile, t.employee_politicstatus, t.employee_academictitle, t.employee_educationrecord, " +
						" t.employee_phototype, mr.sortindex as tabindex, t.employee_native, t.employee_nationality, t.employee_profession, t.employee_description, t.employee_homezipcode, " +
						" t.employee_isdepartmentmanager, t.employee_isdeleted, t.employee_isdeleteddesc, t.employee_photo, t.employee_dn, t.employee_mailstatus, t.isaliasuser, t.is_checked, " +
						" t.is_kq, t.is_showallprocess, t.operate_log_id, t.is_admin, t.mployee_mobile_short, t.jobcode, t.isleave,t.status,t.siteid,t.ssnum,t.position,t.staffids,t.citizencard,t.xxdh,t.sfznum from t_wf_emp_multDept_relation mr, zwkj_employee t " +
						" where t.ISLEAVE!='1' and t.employee_guid = mr.empid and mr.deptid = :id) t) e,\n" + 
						"       (select t.department_guid, t.department_name, t.department_shortdn\n" + 
						"          from zwkj_department t\n" + 
						"         start with t.department_guid =:id\n" + 
						"        connect by prior t.department_guid = t.superior_guid) z\n" + 
						" where z.department_guid = e.department_guid ";
		Query query=getEm().createNativeQuery(sql);
		query.setParameter("id", departmentid);
		return query.getResultList();
	}
	
	public List<Object[]> getAllEmployeeInfoBySuperDepartmentId(
			String departmentid,String userids) throws DataAccessException {
		String sql=
			"select t.employee_guid,\n" +
			"       t.employee_name,\n" + 
			"       z.department_guid,\n" + 
			"       z.department_name,\n" + 
			"       z.department_shortdn\n" + 
			"  from zwkj_employee t,\n" + 
			"       (select t.department_guid, t.department_name, t.department_shortdn\n" + 
			"          from zwkj_department t\n" + 
			"         start with t.department_guid =:id\n" + 
			"        connect by prior t.department_guid = t.superior_guid) z\n" + 
			" where z.department_guid = t.department_guid and t.ISLEAVE!='1' and t.employee_guid in ('"+userids.replace(",", "','")+"')";
		Query query=getEm().createNativeQuery(sql);
		query.setParameter("id", departmentid);
		return query.getResultList();
	}

	public List<Object[]> getEmployeeInfoByEmployeeId(String employeeId)
			throws DataAccessException {
		String sql="select t.employee_guid,t.employee_name,z.department_guid,z.department_name,z.department_shortdn\n" +
			"  from zwkj_employee t, ZWKJ_DEPARTMENT z\n" + 
			" where t.department_guid = z.department_guid\n" + 
			" and t.employee_guid = :id order by t.tabindex";
		Query query=getEm().createNativeQuery(sql);
		query.setParameter("id", employeeId);
		return query.getResultList();
	}

	public List<Employee> getEmployeeByUsernameAndPassword(String username,
			String password) throws DataAccessException {
		String hql="from Employee t where t.employeeLoginname=:username and t.employeePassword=:password order by t.tabindex";
		Query query=getEm().createQuery(hql);
		query.setParameter("username",username);
		query.setParameter("password", password);
		return query.getResultList();
	}

	@Override
	public List<Employee> getEmployeeByEmployeeIds(String userids) {
		String[] user =userids.split(",");
		String querySql = "";
		for(int i=0;i<user.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  e.employeeGuid in ('";
			for(int j=0;j<900&&(i+j)<user.length;j++){
				querySql +=user[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql="from Employee e where   ("+querySql+") and (e.isAdmin!=1 or e.isAdmin is null)  order by e.tabindex ";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<Employee> getEmployeeByEmployeeId(String userids) {
		String hql="from Employee e where e.employeeGuid = '"+userids+"'";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}	

	@Override
	public List<Employee> findEmployeesByMc(String mc) {
		String sql =" select * from (select * from zwkj_employee t where t.ISLEAVE!='1' " +
				" union all " +
				" select mr.deptId as department_guid,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email, " +
				" t.employee_jobtitles,t.employee_professionaltitle,t.employee_status, t.employee_sex, t.employee_birthday, t.employee_country, " +
				" t.employee_province, t.employee_city, t.employee_officeaddress, t.employee_officephone, t.employee_officezipcode, t.employee_officefax, " +
				" t.employee_homephone, t.employee_homeaddress, t.employee_mobile, t.employee_politicstatus, t.employee_academictitle, t.employee_educationrecord, " +
				" t.employee_phototype, mr.sortindex as tabindex, t.employee_native, t.employee_nationality, t.employee_profession, t.employee_description, " +
				" t.employee_homezipcode, t.employee_isdepartmentmanager, t.employee_isdeleted, t.employee_isdeleteddesc, t.employee_photo, t.employee_dn, " +
				" t.employee_mailstatus, t.isaliasuser, t.is_checked, t.is_kq, t.is_showallprocess, t.operate_log_id, t.is_admin, t.mployee_mobile_short, " +
				" t.jobcode,t.isleave, t.status,t.siteid,t.ssnum,t.position,t.staffids,t.citizencard,t.xxdh,t.sfznum from t_wf_emp_multDept_relation mr, zwkj_employee t where t.employee_guid = mr.empid and t.ISLEAVE!='1') e ";
		sql = sql + " where (e.is_admin is null or e.is_admin != 1)";
		if(mc!=null&&!"".equals(mc)){
			sql +=" and (e.employee_name like '%"+mc+"%' or e.employee_guid in (select distinct(l.employee_guid) from t_sys_employeespell l where l.spell like '%"+mc+"%' or l.spellhead like '%"+mc+"%'))";
		}
		sql += " order by e.tabindex ";
		return (List<Employee>)getEm().createNativeQuery(sql,Employee.class).getResultList();
	}

	@Override
	public List<Employee> getEmployeeByEmployeeIds(String relation_userids,
			String mc) {
		String hql="from Employee e where e.employeeGuid in ('"+relation_userids.replace(",", "','")+"')";
		if(mc!=null&&!"".equals(mc)){
			hql +=" and e.employeeName like '%"+mc+"%'";
		}
		hql += " order by e.tabindex ";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<Employee> getEmployeeByUsername(String mc) {
		String hql="from Employee e  ";
		if(mc!=null&&!"".equals(mc)){
			hql +=" where   e.employeeName like '%"+mc+"%'";
		}	
		hql += " order by e.tabindex ";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getEmployeeInfoByCylxrId(String id) {
		String sql="select t.employee_guid,t.employee_name,z.department_guid,z.department_name,z.department_shortdn\n" +
				"  from zwkj_employee t, t_wf_core_user_group o, ZWKJ_DEPARTMENT z\n" + 
				" where o.relation_userids like  '%'||t.employee_guid||'%'\n" + 
				" and t.department_guid = z.department_guid\n" + 
				" and o.id = :id order by t.tabindex";
			Query query=getEm().createNativeQuery(sql);
			query.setParameter("id", id);
			return query.getResultList();
	}

	@Override
	public List<Employee> getEmployeeList(String ids) {
		if(ids==null || ids.equals("")){
			return null;
		}
		String sql="select t.employee_guid,t.employee_name,z.department_guid,z.department_name,z.department_shortdn" +
				"  from zwkj_employee t, ZWKJ_DEPARTMENT z" + 
				" where t.ISLEAVE!='1' and t.department_guid = z.department_guid\n" + 
				" and t.employee_guid in ("+ids+") order by t.tabindex";
		List<Object[]> datas = getEm().createNativeQuery(sql).getResultList();
		List<Employee> list = new ArrayList<Employee>();
		Employee employee = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			employee = new  Employee(); 
			employee.setEmployeeGuid(data[0]==null?"":data[0].toString());
			employee.setEmployeeName(data[1]==null?"":data[1].toString());
			employee.setDepartmentGuid(data[2]==null?"":data[2].toString());
			employee.setDepartmentName(data[3]==null?"":data[3].toString());
			employee.setDepartmentDn(data[4]==null?"":data[4].toString());
			list.add(employee);
		}
		return list;
	}

	@Override
	public List<Object> getUsers(String id) {
		String sql=
				"select t.userids from zwkj_employeerole t where t.id='"+id+"'";
			Query query=getEm().createNativeQuery(sql);
			return query.getResultList();
		}

	@Override
	public List<Department> getDepartmentByDepartmentId(String deptIds) {
		String[] deptId =deptIds.split(",");
		String querySql = "";
		for(int i=0;i<deptId.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  e.departmentGuid in ('";
			for(int j=0;j<900&&(i+j)<deptId.length;j++){
				querySql +=deptId[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql="from Department e where  ("+querySql+") order by e.tabindex ";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getUserNameAndDept(String id) {
		String sql=
				"select t.EMPLOYEE_NAME,d.DEPARTMENT_NAME from ZWKJ_EMPLOYEE t ,ZWKJ_DEPARTMENT d  where t.DEPARTMENT_GUID =  d.DEPARTMENT_GUID and t.employee_guid ='"+id+"'";
			Query query=getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getUserNameAndDeptList(String ids) {
		String sql=
				"select t.EMPLOYEE_NAME,d.DEPARTMENT_NAME from ZWKJ_EMPLOYEE t ,ZWKJ_DEPARTMENT d  where t.DEPARTMENT_GUID =  d.DEPARTMENT_GUID and t.employee_guid in ("+ids+")";
			Query query=getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	
	@Override
	public List<Object[]> getUserInfo() {
		String hql = "select t.employee_name, t.department_guid,t.employee_jobtitles, t.employee_loginname, " +
				"t.employee_officephone, t.employee_officefax, t.employee_mobile, t.employee_homephone, " +
				" t.employee_email,t.employee_guid,t.tabindex from zwkj_employee t where t.ISLEAVE!='1'";
		Query query=getEm().createNativeQuery(hql);
		return query.getResultList();
	}
	

	@Override
	public Employee getEmployeeByLoginName(String loginName) {
		String hql = "from Employee e where e.employeeLoginname = ?";
		Query query=getEm().createQuery(hql);
		query.setParameter(1, loginName);
		List<Employee> list = query.getResultList();
		if(list != null &&list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Object> getUserNamesByIds(String ids) {
		String sql = "select t.employee_name from zwkj_employee t where t.EMPLOYEE_GUID in ("+ids+")";
		Query query=getEm().createNativeQuery(sql);
		List<Object> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Object> getEmployeeDeptIdByEmployeeIds(String userids) {

		String[] user =userids.split(",");
		String querySql = "";
		for(int i=0;i<user.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  e.EMPLOYEE_GUID in ('";
			for(int j=0;j<900&&(i+j)<user.length;j++){
				querySql +=user[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql="select e.department_guid from ZWKJ_EMPLOYEE e where  (e.IS_ADMIN!=1 or e.IS_ADMIN is null) and ("+querySql+") order by e.tabindex ";
		Query query=getEm().createNativeQuery(hql);
		return query.getResultList();
	
	}

	@Override
	public List<Object> getDepartmentIdByDepartmentId(String deptIds) {
		String[] deptId =deptIds.split(",");
		String querySql = "";
		for(int i=0;i<deptId.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  e.DEPARTMENT_GUID in ('";
			for(int j=0;j<900&&(i+j)<deptId.length;j++){
				querySql +=deptId[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql="select e.DEPARTMENT_GUID from ZWKJ_DEPARTMENT e where  ("+querySql+") order by e.tabindex ";
		Query query=getEm().createNativeQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<Employee> findEmployeeList(String loginName,String likeType, Integer pageIndex,Integer pageSize) {
		String sql = "select t.* from zwkj_employee t where 1=1 and t.ISLEAVE!='1'";
		if(loginName!=null && !loginName.equals("")){
			if(likeType!=null && likeType.equals("1")){
				sql+= " and t.employee_loginname like '%"+loginName+"'";
			}else{
				sql+= " and t.employee_loginname like '%"+loginName+"%'";
			}
		}
		sql += " order by t.tabindex asc";
		Query query =  getEm().createNativeQuery(sql, Employee.class);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	@Override
	public List<Employee> findEmployeeList1(String loginName,String likeType, Integer pageIndex,Integer pageSize) {
		String sql = "select t.* from zwkj_employee t where 1=1 and t.ISLEAVE!='1'";
		if(loginName!=null && !loginName.equals("")){
			sql+= " and t.employee_mobile = '"+loginName+"'";
		}
		sql += " order by t.tabindex asc";
		Query query =  getEm().createNativeQuery(sql, Employee.class);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public int findEmployeeCount(String loginName,  String likeType) {
		String sql = "select count(*) from zwkj_employee t where 1=1 and t.ISLEAVE!='1'";
		if(loginName!=null && !loginName.equals("")){
			if(likeType!=null && likeType.equals("1")){
				sql+= " and t.employee_loginname like '%"+loginName+"'";
			}else{
				sql+= " and t.employee_loginname like '%"+loginName+"%'";
			}
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public int findEmployeeCount1(String loginName,  String likeType) {
		String sql = "select count(*) from zwkj_employee t where 1=1 and t.ISLEAVE!='1'";
		if(loginName!=null && !loginName.equals("")){
			sql+= " and t.employee_mobile = '"+loginName+"'";
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<Employee> findEmpList(Map<String, String> map) {
		String depIds = map.get("depIds");
		String name = map.get("name");
		try {
			String sql = "select t.*,s.spell,s.spellhead from ZWKJ_EMPLOYEE t left join t_sys_employeespell s on s.employee_guid = t.employee_guid where 1=1 " ;
			if(name!=null&&!"".equals(name)){
				sql += " and t.EMPLOYEE_NAME like '%"+name+"%'";
			}
			if(depIds!=null&&!"".equals(depIds)){
				sql += " and t.department_guid in "+depIds;
			}
			sql += "order by t.employee_dn";
			Query query = getEm().createNativeQuery(sql,Employee.class);
			List<Employee> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Employee selectById(String id) {
		try {
			String hql = "from Employee t where t.employeeGuid = :id";
			Query query = getEm().createQuery(hql);
			query.setParameter("id", id);
			List<Employee> list = query.getResultList();
			if (null != list && list.size()>0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Object[]> findEmps(Map<String, String> map) {
		String depIds = map.get("depIds");
		String name = map.get("name");
		try {
			//String sql = "select t.*,s.spell,s.spellhead from ZWKJ_EMPLOYEE t left join t_sys_employeespell s on s.employee_guid = t.employee_guid where 1=1 " ;
			//String sql = "select t.employee_name,t.employee_loginname,t.employee_sex,t.employee_mobile, s.spell, s.spellhead from ZWKJ_EMPLOYEE t left join t_sys_employeespell s on s.employee_guid = t.employee_guid where 1=1 " ;
			
			String sql = "select t.employee_name,t.employee_loginname,t.employee_sex,t.employee_mobile from ZWKJ_EMPLOYEE t  where 1=1 " ;
			if(name!=null&&!"".equals(name)){
				sql += " and (t.EMPLOYEE_NAME like '%"+name+"%' or t.employee_guid in (select distinct (l.EMPLOYEE_GUID) from T_SYS_EMPLOYEESPELL l where l.spell like '%"+name+"%' or l.spellhead like '%"+name+"%'))";
				
			}
			if(depIds!=null&&!"".equals(depIds)){
				sql += " and t.department_guid in "+depIds;
			}
			sql += "order by t.employee_dn";
//			Query query = getEm().createNativeQuery(sql,Employee.class);
			Query query = getEm().createNativeQuery(sql);
			List<Object[]> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	/*=======================================融合的代码==========================================*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//通过人员ID查询兼职部门站点ID
	public String getPtjobSiteIdByUserId(String userId) throws DataAccessException {
		List<String> rr= getEm().createNativeQuery("select (select b.siteid from TRUEOA_PTJOB a,trueids_nj.ids_dept b where a.jobcode = b.id and user_id = '"+userId+"') || '&&&' || (select distinct jobcode from trueoa_ptjob where user_id = '"+userId+"') from dual").getResultList();
		if (rr != null && rr.size()>0 ){
			return rr.get(0).toString();
		}else{
			return null;
		}
	}
	
	//根据站点ID获取站点名称
	public String getSiteNameBySiteId(String siteId){
		List<String> rr = getEm().createNativeQuery(
				"select t.sitename from nj_site t where t.siteid='" + siteId + "'").getResultList();
		if (rr != null && rr.size()>0 ){
			return rr.get(0).toString();
		}else{
			return null;
		}
	}
	
	@Override
	public List<Employee> findEmployeeListByDepId(String depId){
		String sql = "select t.* from zwkj_employee t where t.department_guid = '"+depId+"'";
		return getEm().createNativeQuery(sql, Employee.class).getResultList();
	}

	
	@Override
	public List<Employee> findEmployeesByMc(String mc,String siteId) {
		String sql ="from Employee e where (e.isAdmin is null or e.isAdmin!=1)";
		if(mc!=null&&!"".equals(mc)){
			sql +=" and (e.employeeName like '%"+mc+"%' or e.employeeGuid in (select distinct(l.employeeGuid) from EmployeeSpell l where l.spell like '%"+mc+"%' or l.spellhead like '%"+mc+"%'))";
		}
		sql += "and e.siteId='"+siteId+"' and e.employeeName not like '%管理员%' order by e.tabindex ";
		return getEm().createQuery(sql).getResultList();
	}
	
	@Override
	public List<Employee> findEmployeesByMc(String mc,String siteId,String departId) {
		String sql ="from Employee e where (e.isAdmin is null or e.isAdmin!=1)";
		if(mc!=null&&!"".equals(mc)){
			sql +=" and (e.employeeName like '%"+mc+"%' or e.employeeGuid in (select distinct(l.employeeGuid) from EmployeeSpell l where l.spell like '%"+mc+"%' or l.spellhead like '%"+mc+"%'))";
		}
		sql += "and e.siteId='"+siteId+"' and e.departmentGuid='"+departId+"' order by e.tabindex ";
		return getEm().createQuery(sql).getResultList();
	}

	

	@Override
	public List<Employee> selectEmpByName(String name) {
		try {
			String sql = "select * from ZWKJ_EMPLOYEE t where t.EMPLOYEE_NAME like '%"+name+"%'";
			Query query = getEm().createNativeQuery(sql,Employee.class);
			List<Employee> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	//过滤了测试组的人员	同站点部门兼职
		@Override
		public List<Employee> getAllChildEmpsByDeptId(String deptId) {
			/*String sql = "select * from (select b.* from ( select * from  ZWKJ_EMPLOYEE e where e.department_guid  ='"+deptId+"'"
					+ " UNION all"
					+ " select e.jobcode,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,"
					+ " t.employee_email,t.employee_jobtitles,  t.employee_professionaltitle,t.employee_status,t.employee_sex,"
					+ "t.employee_birthday,t.employee_country,t.employee_province,t.employee_city, t.employee_officeaddress,"
					+ "  t.employee_officephone,t.employee_officezipcode,t.employee_officefax,t.employee_homephone,"
					+ "t.employee_homeaddress,  t.employee_mobile,t.employee_politicstatus,t.employee_academictitle,"
					+ " t.employee_educationrecord,t.employee_phototype,e.jobseq as tabindex,t.employee_native, t.employee_nationality,"
					+ " t.employee_profession,t.employee_description,t.employee_homezipcode,t.employee_isdepartmentmanager,"
					+ "   t.employee_isdeleted, t.employee_isdeleteddesc,t.employee_photo,  t.employee_dn ,t.employee_mailstatus,"
					+ "  t.isaliasuser,t.is_checked,t.is_kq,t.is_showallprocess,t.operate_log_id, t.is_admin,t.mployee_mobile_short,"
					+ " t.jobcode, (select distinct(siteid) from zwkj_employee s where s.department_guid ='"+deptId+"') as siteid, t.ssnum,t.sfznum,t.position,t.staffids,t.citizencard,t.xxdh  "
					+ "from zwkj_employee t  inner join trueoa_ptjob e on t.employee_guid = e.user_id  "
					+ " where t.employee_guid in( select user_id from trueoa_ptjob  where jobcode ='"+deptId+"')"
					+") b  order by b.tabindex) s where s.employee_status != '0' and s.employee_name not like '%管理员%'";// 
*/			
			String sql = "select e.* from  ZWKJ_EMPLOYEE e where e.department_guid  ='"+deptId+"'"
					+" and e.employee_status != '0' and e.employee_name not like '%管理员%'";// 
			if(!"7b26433d-01b8-4d31-9575-4df5da404945".equals(deptId)){
				sql+="and e.department_guid <> '7b26433d-01b8-4d31-9575-4df5da404945'";
			}
			Query query = getEm().createNativeQuery(sql,Employee.class);
			return query.getResultList();
		}
	
//	//过滤了测试组的人员   备份
//	@Override
//	public List<Employee> getAllChildEmpsByDeptId(String deptId) {
//		String sql = "select * from (select b.* from (select k.* from ZWKJ_EMPLOYEE k right join (select t.* from ZWKJ_DEPARTMENT t start with t.department_guid ='"+deptId+"'"
//				+ " connect by prior t.department_guid = t.superior_guid) m on k.department_guid = m.department_guid"
//				+ " UNION all"
//				+ " select e.jobcode,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email,t.employee_jobtitles,"
//				+ "  t.employee_professionaltitle,t.employee_status,t.employee_sex,t.employee_birthday,t.employee_country,t.employee_province,t.employee_city,"
//				+ " t.employee_officeaddress,t.employee_officephone,t.employee_officezipcode,t.employee_officefax,t.employee_homephone,t.employee_homeaddress,"
//				+ "  t.employee_mobile,t.employee_politicstatus,t.employee_academictitle,t.employee_educationrecord,t.employee_phototype,e.jobseq,t.employee_native,"
//				+ " t.employee_nationality,t.employee_profession,t.employee_description,t.employee_homezipcode,t.employee_isdepartmentmanager,t.employee_isdeleted,"
//				+ " t.employee_isdeleteddesc,t.employee_photo,"
//				+ "  t.employee_dn ,t.employee_mailstatus,t.isaliasuser,t.is_checked,t.is_kq,t.is_showallprocess,t.operate_log_id,"
//				+ " t.is_admin,t.mployee_mobile_short,t.jobcode,"
//				+ "  j.siteid,"
//				+ " t.ssnum,t.sfznum,t.position,t.staffids,t.citizencard,t.xxdh"
//				+ "  from zwkj_employee t inner join trueoa_ptjob e on t.employee_guid = e.user_id "
//				+ " left join (select distinct siteid,department_guid from zwkj_employee where department_guid in (select t.department_guid"
//				+ " from ZWKJ_DEPARTMENT t start with t.department_guid ='"+deptId+"' connect by prior t.department_guid = t.superior_guid))"
//				+" j on e.jobcode = j.department_guid  where e.jobcode in (select t.department_guid from ZWKJ_DEPARTMENT t start with t.department_guid ='"+deptId+"'"
//				+" connect by prior t.department_guid = t.superior_guid)) b"
//				+ "  order by b.tabindex ) s where s.employee_name not like '%管理员%'";//
//		if(!"7b26433d-01b8-4d31-9575-4df5da404945".equals(deptId)){
//			sql+="and s.department_guid <> '7b26433d-01b8-4d31-9575-4df5da404945'";
//		}
//		Query query = getEm().createNativeQuery(sql,Employee.class);
//		return query.getResultList();
//	}
	
		//idsAdmin查询全部人员和组
		@Override
		public List<Employee> getAllChildEmpsByDeptIdForAdmin(String deptId) {
			String sql = "select b.* from ( select * from  ZWKJ_EMPLOYEE e where e.department_guid  ='"+deptId+"'"
					+ " UNION all"
					+ " select e.jobcode,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,"
					+ " t.employee_email,t.employee_jobtitles,  t.employee_professionaltitle,t.employee_status,t.employee_sex,"
					+ "t.employee_birthday,t.employee_country,t.employee_province,t.employee_city, t.employee_officeaddress,"
					+ "  t.employee_officephone,t.employee_officezipcode,t.employee_officefax,t.employee_homephone,"
					+ "t.employee_homeaddress,  t.employee_mobile,t.employee_politicstatus,t.employee_academictitle,"
					+ " t.employee_educationrecord,t.employee_phototype,e.jobseq as tabindex,t.employee_native, t.employee_nationality,"
					+ " t.employee_profession,t.employee_description,t.employee_homezipcode,t.employee_isdepartmentmanager,"
					+ "   t.employee_isdeleted, t.employee_isdeleteddesc,t.employee_photo,  t.employee_dn ,t.employee_mailstatus,"
					+ "  t.isaliasuser,t.is_checked,t.is_kq,t.is_showallprocess,t.operate_log_id, t.is_admin,t.mployee_mobile_short,"
					+ " t.jobcode, (select distinct(siteid) from zwkj_employee s where s.department_guid ='"+deptId+"') as siteid, t.ssnum,t.sfznum,t.position,t.staffids,t.citizencard,t.xxdh  "
					+ "from zwkj_employee t  inner join trueoa_ptjob e on t.employee_guid = e.user_id  "
					+ " where t.employee_guid in( select user_id from trueoa_ptjob  where jobcode ='"+deptId+"')"
					+") b and b.employee_status != '0'  order by b.tabindex";
			Query query = getEm().createNativeQuery(sql,Employee.class);
			return query.getResultList();
		}
	
	@Override
	public List<Employee> getAllEmpsByUserIds(String userIds) {
		String[] id =userIds.split(",");
		String querySql = "";
		for(int i=0;i<id.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  e.employee_Guid in ('";
			for(int j=0;j<900&&(i+j)<id.length;j++){
				querySql +=id[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql = "select e.* from ZWKJ_EMPLOYEE e where 1=1 and EMPLOYEE_STATUS != '0' and "+querySql;
		Query query=super.getEm().createNativeQuery(hql.toString(),Employee.class);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> findEmployeeInfoListByDepId(String depId)
			throws Exception {
		String sql=
				"select t.employee_guid,\n" +
				"       t.employee_name,\n" + 
				"       z.department_guid,\n" + 
				"       z.department_name,\n" + 
				"       z.department_shortdn\n" + 
				"  from zwkj_employee t,\n" + 
				"       (select t.department_guid, t.department_name, t.department_shortdn\n" + 
				"          from zwkj_department t\n" + 
				"         start with t.department_guid =:id\n" + 
				"        connect by prior t.department_guid = t.superior_guid) z\n" + 
				" where z.department_guid = t.department_guid";
			Query query=getEm().createNativeQuery(sql);
			query.setParameter("id", depId);
			return query.getResultList();
	}
	
	@Override
	public List<Employee> getAllUnitEmployee(String depId){
		StringBuffer hql = new StringBuffer();
		hql.append("select * from zwkj_employee emp where exists (select t.department_guid from ZWKJ_DEPARTMENT t ")
			.append(" where emp.department_guid = t.department_guid and t.department_hierarchy like ")
			.append(" '%' || (select dep.department_hierarchy from zwkj_department dep ")
			.append(" where dep.department_guid = '").append(depId).append("'))");
		return getEm().createNativeQuery(hql.toString(),Employee.class).getResultList();
	}

	/*@Override
	public List<DiaryAccredit> findShipById(String leadId, String personId) {
		StringBuffer hql = new StringBuffer(); 
		hql.append("from DiaryAccredit t where 1=1");
		if(StringUtils.isNotBlank(leadId)){
			hql.append(" and t.leaderId=:leaderId");
		}
		if(StringUtils.isNotBlank(personId)){
			hql.append(" and t.personId=:personId");
		}
		Query query = getEm().createQuery(hql.toString());
		if(StringUtils.isNotBlank(leadId)){
			query.setParameter("leaderId", leadId);
		}
		if(StringUtils.isNotBlank(personId)){
			query.setParameter("personId", personId);
		}
		
		return query.getResultList();
	}*/

	@Override
	public List<Employee> findShipByLeaderId(String leaderId) {
		String sql = "select * from zwkj_employee emp where exists " +
				"(select t.person_id from T_OA_DIARY_ACCREDIT t where t.person_id = emp.employee_guid and t.leader_id = '"+leaderId+"')";
		return getEm().createNativeQuery(sql,Employee.class).getResultList();
	}

	@Override
	public List<Object[]> findDeptEmr(String did) {
		String sql = "select * from T_WF_EMP_MULTDEPT_RELATION where deptid = '"+did+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<Object[]> findAllEmr() {
		String sql = "select * from T_WF_EMP_MULTDEPT_RELATION";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<Object[]> findEmrById(String id) {
		String[] ids = id.split(",");
		if(ids.length>0){}
		String idCondition = "";
		for(String i:ids){
			idCondition += "'"+i+"',"; 
		}
		idCondition = idCondition.substring(0, idCondition.length()-1);
		String sql = "select * from T_WF_EMP_MULTDEPT_RELATION where empid in ("+idCondition+")";
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public void addFeedBack(FeedBack feedBack) {
		getEm().persist(feedBack);
	}


	@Override
	public List<Employee> findEmployeeListBySiteId(String siteId) {
		String sql = "select t.* from zwkj_employee t where t.siteId = '"+siteId+"'";
		return getEm().createNativeQuery(sql, Employee.class).getResultList();
	}

	
	public List<Employee> queryAllempByDepId(String depId){
		//String sql = "select e.* from ZWKJ_EMPLOYEE e where department_guid = '"+depId+"' order by tabindex asc";
		String sql = " select b.department_guid,b.employee_guid,b.employee_loginname,b.employee_password,b.employee_name,b.employee_email,b.employee_jobtitles, "
				+" b.employee_professionaltitle,b.employee_status,b.employee_sex,b.employee_birthday,b.employee_country,b.employee_province,b.employee_city,"
				+" b.employee_officeaddress,b.employee_officephone,b.employee_officezipcode,b.employee_officefax,b.employee_homephone,b.employee_homeaddress,"
				+" b.employee_mobile,b.employee_politicstatus,b.employee_academictitle,b.employee_educationrecord,b.employee_phototype,b.tabindex,b.employee_native,"
				+" b.employee_nationality,b.employee_profession,b.employee_description,b.employee_homezipcode,b.employee_isdepartmentmanager,b.employee_isdeleted,"
				+" b.employee_isdeleteddesc,b.employee_photo ,b.employee_dn,b.employee_mailstatus,b.isaliasuser,b.is_checked,b.is_kq,b.is_showallprocess,b.operate_log_id,"
				+" b.is_admin,b.mployee_mobile_short,b.jobcode,b.siteid,b.ssnum,b.sfznum,b.position,b.staffids,b.citizencard,b.xxdh from ("
				+" select d.department_guid,d.employee_guid,d.employee_loginname,d.employee_password,d.employee_name,d.employee_email,d.employee_jobtitles,"
				+" d.employee_professionaltitle,d.employee_status,d.employee_sex,d.employee_birthday,d.employee_country,d.employee_province,d.employee_city,"
				+" d.employee_officeaddress,d.employee_officephone,d.employee_officezipcode,d.employee_officefax,d.employee_homephone,d.employee_homeaddress,"
				+" d.employee_mobile,d.employee_politicstatus,d.employee_academictitle,d.employee_educationrecord,d.employee_phototype,d.tabindex,d.employee_native,"
				+" d.employee_nationality,d.employee_profession,d.employee_description,d.employee_homezipcode,d.employee_isdepartmentmanager,d.employee_isdeleted,"
				+" d.employee_isdeleteddesc,d.employee_photo ,d.employee_dn,d.employee_mailstatus,d.isaliasuser,d.is_checked,d.is_kq,d.is_showallprocess,d.operate_log_id,"
				+" d.is_admin,d.mployee_mobile_short,d.jobcode,d.siteid,d.ssnum,d.sfznum,d.position,d.staffids,d.citizencard,d.xxdh"
				+" from  zwkj_employee d where d.department_Guid  = '"+depId+"'"
				+" UNION all"
				+" select e.jobcode,t.employee_guid,t.employee_loginname,t.employee_password,t.employee_name,t.employee_email,t.employee_jobtitles,"
				+" t.employee_professionaltitle,t.employee_status,t.employee_sex,t.employee_birthday,t.employee_country,t.employee_province,t.employee_city,"
				+" t.employee_officeaddress,t.employee_officephone,t.employee_officezipcode,t.employee_officefax,t.employee_homephone,t.employee_homeaddress,"
				+" t.employee_mobile,t.employee_politicstatus,t.employee_academictitle,t.employee_educationrecord,t.employee_phototype,e.jobseq,t.employee_native,"
				+" t.employee_nationality,t.employee_profession,t.employee_description,t.employee_homezipcode,t.employee_isdepartmentmanager,t.employee_isdeleted,"
				+" t.employee_isdeleteddesc,t.employee_photo,"
				+" t.employee_dn ,t.employee_mailstatus,t.isaliasuser,t.is_checked,t.is_kq,t.is_showallprocess,t.operate_log_id,"
				+" t.is_admin,t.mployee_mobile_short,t.jobcode,"
				+" j.siteid,"
				+" t.ssnum,t.sfznum,t.position,t.staffids,t.citizencard,t.xxdh"
				+" from zwkj_employee t inner join trueoa_ptjob e on t.employee_guid = e.user_id "
				+" left join (select distinct siteid,department_guid from zwkj_employee where department_guid = '"+depId+"') j "
				+" on e.jobcode = j.department_guid"
				+"  where e.jobcode = '"+depId+"') b where 1=1 and b.employee_isdeleted !='1' order by b.tabindex ";
		List<Employee> emplist = getEm().createNativeQuery(sql,Employee.class).getResultList();
		if(emplist.size()>0){
			return emplist;
		}else{
			return null;
		}
	}
	
	public Employee queryEmpByDepId(String depId){
		String sql = " select * from( select e.* from ZWKJ_EMPLOYEE e where department_guid = '"+depId+"' order by tabindex asc)  where rownum=1";
		List<Employee> emplist = getEm().createNativeQuery(sql,Employee.class).getResultList();
		if(emplist.size()>0){
			return emplist.get(0);
		}else{
			return null;
		}
	}
	
	public String getPassWord(String userId){
		String idsOrcl = SystemParamConfigUtil2.getParamValueByParam("idsOrcl");
		String sql = " SELECT PASSWORD FROM " + idsOrcl+ ".ids_user WHERE ID=:userId ";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("userId", userId);
		String password = query.getSingleResult().toString();
		return password;
	}
	
	public String getJobCode(String userId){
		String sql = " SELECT JOBCODE FROM TRUEOA_PTJOB WHERE USER_ID = '"+userId+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		String jobCode = "";
		if(list != null && list.size()>0){
			Object[] o = (Object[])list.toArray();
			jobCode= (String)o[0];
			return jobCode;
		}else{
			return null;
		}
	}
	
	public String getSiteid(String departmentGuid){
		String sql = " SELECT distinct(SITEID) FROM ZWKJ_EMPLOYEE WHERE DEPARTMENT_GUID = '"+departmentGuid+"' ";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		String siteid = "";
		if(list != null && list.size()>0){
			Object[] o = (Object[])list.toArray();
			siteid= (String)o[0];
			return siteid;
		}else{
			return null;
		}
	}
	
	public String getJobSeq(String userId){
		String sql = " SELECT JOBSEQ FROM TRUEOA_USERJZ WHERE USER_ID = '"+userId+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		String jobSeq = "";
		if(list != null && list.size()>0){
			Object[] o = (Object[])list.toArray();
			jobSeq= (String)o[0];
			return jobSeq;
		}else{
			return null;
		}
	}

	@Override
	public List<Employee> getEmployeeByCardId(String cardId) {
//		String hql="from Employee t where t.employeeLoginname=:username and t.employeePassword=:password order by t.tabindex";
		String hql="from Employee t where t.employeeGuid=:cardId order by t.tabindex";
		Query query=getEm().createQuery(hql);
		query.setParameter("cardId",cardId);
		return query.getResultList();
	}

	@Override
	public List<RoleUser> getRoleUserById(String userId) {
		String sql = " select * from t_sys_role_user WHERE USER_ID = '"+userId+"'";
		List<RoleUser> roleUserList = getEm().createNativeQuery(sql.toString(),RoleUser.class).getResultList();
		if(roleUserList.size()>0){
			return roleUserList;
		}else{
			return null;
		}
	}

	
}
