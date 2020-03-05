package cn.com.trueway.workflow.set.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.ZtreeDao;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.CommonGroupUsers;
import cn.com.trueway.workflow.set.pojo.Leader;


/**
 * @author 赵坚
 * @version 创建时间：2016年7月21日15:52:26
 */
@SuppressWarnings("unchecked")
public class ZtreeDaoImpl extends BaseDao implements ZtreeDao {

	@Override
	public CommonGroup saveCommonGroup(CommonGroup cg) {
		this.getEm().persist(cg);
		return this.getEm().merge(cg);
	}

	@Override
	public void saveCommonGroupUsers(CommonGroupUsers cgu) {
		this.getEm().persist(cgu);
	}
	
	@Override
	public void saveLeaders(Leader leader) {
		this.getEm().persist(leader);
	}

	@Override
	public void deleteCommonGroup(CommonGroup cg) {
		this.getEm().remove(this.getEm().merge(cg));
	}
	
	@Override
	public void deleteCommGrpById(String id){
	    String hql = "delete from CommonGroup t where t.id = ?";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, id);
		query.executeUpdate();
	}

	@Override
	public void deleteCommonGroupUsers(CommonGroupUsers cgu) {
		this.getEm().remove(this.getEm().merge(cgu));
	}

	@Override
	public void updateCommonGroup(CommonGroup cg) {
		this.getEm().merge(cg);
	}

	@Override
	public void updateCommonGroupUsers(CommonGroupUsers cgu) {
		this.getEm().merge(cgu);
	}

	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid,String siteId) {
		String hql = "from CommonGroup t where 1=1 ";
		if(StringUtils.isNotBlank(uid)){
			hql += " and t.belongTo = :belongTo";
		}else{
			hql += " and t.belongTo is null";
		}
		if(StringUtils.isNotBlank(siteId)){
			hql += " and t.siteId = :siteId";
		}
		hql += " order by t.createTime";
		Query query = this.getEm().createQuery(hql);
		if(StringUtils.isNotBlank(uid)){
			query.setParameter("belongTo", uid);
		}
		if(StringUtils.isNotBlank(siteId)){
			query.setParameter("siteId", siteId);
		}
		return query.getResultList();
	}

	@Override
	public List<CommonGroup> findAllDeptGroupByUid(String uid) {
		String hql = "from CommonGroup t where t.belongTo = ? and t.deptFlag = '1' order by t.createTime";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, uid);
		return query.getResultList();
	}

	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid) {
		String sql = "select t.* from t_common_group_users t,zwkj_employee e ,zwkj_department d where d.department_guid=e.department_guid and t.empid = e.employee_guid and e.employee_name is not null and t.gid = ? order by d.tabindex,e.tabindex asc";
		Query query = this.getEm().createNativeQuery(sql,CommonGroupUsers.class);
		query.setParameter(1, gid);
		return query.getResultList();
	}
	
	@Override
	public List<Leader> findAllLeadersBySiteId(String siteId) {
		String sql = "select t.* from T_WF_LEADER t,zwkj_employee e where t.EMPLOYEE_GUID = e.employee_guid and e.employee_name is not null and t.SITEID = ? order by e.tabindex asc";
		Query query = this.getEm().createNativeQuery(sql,Leader.class);
		query.setParameter(1, siteId);
		return query.getResultList();
	}

	@Override
	public CommonGroup findCommonGroupById(String id) {
		return this.getEm().find(CommonGroup.class, id);
	}

	@Override
	public void deleteCommonGroupUsersByGid(String gid) {
		String hql = "delete from CommonGroupUsers t where t.gid = ?";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, gid);
		query.executeUpdate();
	}
	
	@Override
	public void deleteLeaderBySiteId(String siteId) {
		String hql = "delete from Leader t where t.siteId = ?";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, siteId);
		query.executeUpdate();
	}

	@Override
	public List<Department> getAllParentDeptBydeptId(String deptsid) {
		String[] dept =deptsid.split(",");
		String querySql = "";
		for(int i=0;i<dept.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  t.department_guid in ('";
			for(int j=0;j<900&&(i+j)<dept.length;j++){
				querySql +=dept[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		String hql = " select k.* from ZWKJ_DEPARTMENT k right join (select distinct (t.department_guid)"
				+ " from ZWKJ_DEPARTMENT t start with "+querySql
				+ " connect by prior t.superior_guid = t.department_guid) m on k.department_guid = m.department_guid order by k.tabindex";
		Query query=super.getEm().createNativeQuery(hql.toString(),Department.class);
		return query.getResultList();
	}
	
	@Override
	public List<Department> getAllParentDeptByMC(String mc) {
	
		String hql = " select k.* from ZWKJ_DEPARTMENT k right join (select distinct (t.department_guid)"
				+ " from ZWKJ_DEPARTMENT t start with t.DEPARTMENT_NAME like '%"+mc+"%'"
				+ " connect by prior t.superior_guid = t.department_guid) m on k.department_guid = m.department_guid order by k.tabindex";
		Query query=super.getEm().createNativeQuery(hql.toString(),Department.class);
		return query.getResultList();
	}

	@Override
	public List<Department> getAllChildDeptBydeptId(String deptId) {
		String hql = "select t.* from ZWKJ_DEPARTMENT t start with t.department_guid = '"+deptId+"' connect by prior  t.department_guid = t.superior_guid order by t.tabindex";
		Query query=super.getEm().createNativeQuery(hql.toString(),Department.class);
		return query.getResultList();
	}
	
	@Override
	public List<Employee> getAllChildEmpsByDeptId(String deptId) {
		String sql = "select * from (select k1.* from ZWKJ_EMPLOYEE k1 right join (select t.* from ZWKJ_DEPARTMENT t start with t.department_guid ='"+deptId+"'"
				+ " connect by prior t.department_guid = t.superior_guid) m1 on k1.department_guid = m1.department_guid where k1.isleave = '0' "
				+ "union all " + 
				"  select j.DEPTID as DEPARTMENT_GUID, k2.employee_guid,k2.employee_loginname,k2.employee_password,k2.employee_name,k2.employee_email, " + 
				"         k2.employee_jobtitles,k2.employee_professionaltitle,k2.employee_status, k2.employee_sex, k2.employee_birthday, k2.employee_country, " + 
				"         k2.employee_province, k2.employee_city, k2.employee_officeaddress, k2.employee_officephone, k2.employee_officezipcode, k2.employee_officefax, " + 
				"         k2.employee_homephone, k2.employee_homeaddress, k2.employee_mobile, k2.employee_politicstatus, k2.employee_academictitle, k2.employee_educationrecord, " + 
				"         k2.employee_phototype, j.sortindex as tabindex, k2.employee_native,k2.employee_nationality, k2.employee_profession, k2.employee_description, " + 
				"         k2.employee_homezipcode, k2.employee_isdepartmentmanager, k2.employee_isdeleted, k2.employee_isdeleteddesc, k2.employee_photo, k2.employee_dn, " + 
				"         k2.employee_mailstatus, k2.isaliasuser, k2.is_checked, k2.is_kq, k2.is_showallprocess, k2.operate_log_id, k2.is_admin, k2.mployee_mobile_short, " + 
				"         k2.jobcode, k2.isleave, k2.status,k2.SITEID, k2.SSNUM,k2.POSITION, k2.STAFFIDS, k2.CITIZENCARD, k2.XXDH, k2.SFZNUM " + 
				"  from ZWKJ_EMPLOYEE k2" + 
				" inner join t_wf_emp_multdept_relation j" + 
				"    on j.empid = k2.employee_guid" + 
				" inner join (select t.*" + 
				"               from ZWKJ_DEPARTMENT t" + 
				"              start with t.department_guid ='"+deptId+"'" +
				"             connect by prior t.department_guid = t.superior_guid) m" + 
				"    on j.deptid = m.department_guid  where k2.isleave = '0') t  order by t.tabindex asc ";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		List<Employee> list1 = new ArrayList<Employee>();
		for(Object[] o:list){
			Employee e = new Employee();
			e.setDepartmentGuid(o[0]==null?"":o[0].toString());
			e.setEmployeeGuid(o[1]==null?"":o[1].toString());
			e.setEmployeeName(o[4]==null?"":o[4].toString());
			list1.add(e);
		}
		return list1;
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
		String hql = "select e.* from ZWKJ_EMPLOYEE e where 1=1 and e.ISLEAVE!='1' and "+querySql;
		Query query=super.getEm().createNativeQuery(hql.toString(),Employee.class);
		return query.getResultList();
	}
	
	

	@Override
	public List<Employee> getEmployeeListByNodeId(String nodeId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select e.employee_guid, e.employee_name, k.department_id,(case when k.department_id != e.department_guid " )
			  .append(" then (select mr.sortindex from t_wf_emp_multdept_relation mr where mr.empid = e.employee_guid and mr.deptid = k.department_id)")
			  .append(" else e.tabindex end) as tabindex from zwkj_employee e,")
			  .append(" (select t3.employee_id, t3.department_id  from wf_node n, t_wf_core_inneruser_map_user t3")
			  .append(" where n.wfn_staff like '%'||t3.inneruser_id||'%' and n.wfn_id = '"+nodeId+"') k")
			  .append("  where e.ISLEAVE!='1' and e.employee_guid = k.employee_id order by tabindex asc ");
		List<Object[]> list = getEm().createNativeQuery(buffer.toString()).getResultList();
		List<Employee> empList = new ArrayList<Employee>();
		Employee emp = null;
		Object[] data = null;
		for(int i=0; i<list.size(); i++){
			emp = new Employee();
			data = list.get(i);
			emp.setEmployeeGuid(data[0]!=null?data[0].toString():"");
			emp.setEmployeeName(data[1]!=null?data[1].toString():"");
			emp.setDepartmentGuid(data[2]!=null?data[2].toString():"");
			empList.add(emp);
		}
		return empList;
	}
	
	@Override
	public List<Employee> getEmployeeListByNodeInfo(String nodeId, String mc) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select e.employee_guid, e.employee_name, k.department_id,(case when k.department_id != e.department_guid " )
		  	  .append(" then (select mr.sortindex from t_wf_emp_multdept_relation mr where mr.empid = e.employee_guid and mr.deptid = k.department_id)")
		  	  .append(" else e.tabindex end) as tabindex from zwkj_employee e,")
			  .append(" (select t3.employee_id, t3.department_id  from wf_node n, t_wf_core_inneruser_map_user t3")
			  .append(" where instr(n.wfn_staff,t3.inneruser_id) > 0 and n.wfn_id = '"+nodeId+"') k")
			  .append("  where e.ISLEAVE!='1' and e.employee_guid = k.employee_id  and e.employee_name like '%"+mc+"%' order by tabindex asc");
		List<Object[]> list = getEm().createNativeQuery(buffer.toString()).getResultList();
		List<Employee> empList = new ArrayList<Employee>();
		Employee emp = null;
		Object[] data = null;
		for(int i=0; i<list.size(); i++){
			emp = new Employee();
			data = list.get(i);
			emp.setEmployeeGuid(data[0]!=null?data[0].toString():"");
			emp.setEmployeeName(data[1]!=null?data[1].toString():"");
			emp.setDepartmentGuid(data[2]!=null?data[2].toString():"");
			empList.add(emp);
		}
		return empList;
	}

	@Override
	public List<CommonGroup> getCommonUseGroupList(Map<String, String> map){
		String sql = "select t.* from t_common_group t where 1=1";
		String isCommonUse = map.get("isCommonUse");
		String groupName = map.get("groupName");
		String pageIndex = map.get("pageIndex");
		String pageSize = map.get("pageSize"); 
		String bigDepId = map.get("bigDepId");
		if(CommonUtil.stringNotNULL(isCommonUse) && "yes".equals(isCommonUse)){
			sql = sql + " and t.belong_to is null ";
		}
		if(CommonUtil.stringNotNULL(groupName)){
			sql = sql + " and t.name ='" + groupName + "'";
		}
		if(StringUtils.isNotBlank(bigDepId)){
			sql = sql + " and t.siteid ='" + bigDepId + "'";
		}
		sql = sql + " order by t.orderindex asc, t.create_time desc";
		Query query = getEm().createNativeQuery(sql,CommonGroup.class);
		if(CommonUtil.stringNotNULL(pageIndex) && CommonUtil.stringNotNULL(pageSize)){
			query.setFirstResult(Integer.parseInt(pageIndex));
			query.setMaxResults(Integer.parseInt(pageSize));
		}
		return query.getResultList();
	}

	@Override
	public int getCommonUseGroupCount(Map<String, String> map){
		String sql = "select count(*) from T_COMMON_GROUP t where 1=1";
		String isCommonUse = map.get("isCommonUse");
		if(CommonUtil.stringNotNULL(isCommonUse)){
			sql = sql + " and t.belong_to is null ";
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<Department> getDepartmentListByEmpId(String empId){
		String sql = "select dept.* from zwkj_department dept, zwkj_employee emp where emp.department_guid = dept.department_guid " +
				" and emp.employee_guid = '" + empId + "'";
		Query query=super.getEm().createNativeQuery(sql,Department.class);
		return query.getResultList();
	}

	@Override
	public List<Employee> getEmployeeList(Map<String, String> map){
		String empId = map.get("empId");
		String sql = "select emp.* from zwkj_employee emp where 1=1 " +
				" and emp.employee_guid = '" + empId + "'";
		Query query=super.getEm().createNativeQuery(sql,Employee.class);
		return query.getResultList();
	}
	
	@Override
	public List<CommonGroupUsers> getComGrpUsersByParams(Map<String, String> map){
		String userId = map.get("userId");
		String hql = "select * from T_COMMON_GROUP_USERS t, T_COMMON_GROUP grp where 1=1 and t.gid = grp.id and grp.BELONG_TO is null ";
		hql = hql + " and t.EMPID = ? ";
		hql = hql + " order by t.ORDERINDEX asc ";
		Query query = this.getEm().createNativeQuery(hql, CommonGroupUsers.class);
		query.setParameter(1, userId);
		return query.getResultList();
	}
	
	@Override
	public int countOfCommonGroup(String uid) {
		String hql = "select count(*) from CommonGroup t where t.belongTo = ? order by t.createTime";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, uid);
		return Integer.valueOf(query.getSingleResult()+"");
	}

	@Override
	public List<CommonGroup> getCommonGroups(String uid, Integer pageIndex, Integer pageSize) {
		String hql = "from CommonGroup t where t.belongTo = ? order by t.createTime";
		Query query = this.getEm().createQuery(hql);
		query.setParameter(1, uid);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
}
