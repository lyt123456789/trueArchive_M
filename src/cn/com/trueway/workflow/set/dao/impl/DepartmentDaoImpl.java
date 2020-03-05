package cn.com.trueway.workflow.set.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.asprise.util.tiff.q;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.sys.pojo.CommonGroup;
import cn.com.trueway.sys.pojo.CommonGroupUsers;
import cn.com.trueway.sys.pojo.SiteSource;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.DepartmentDao;


/**
 * @author 李伟
 * @version 创建时间：2009-11-27 下午08:21:33 类说明
 */
@SuppressWarnings("unchecked")
public class DepartmentDaoImpl extends BaseDao implements
		DepartmentDao {

	private String workflowOrcl = SystemParamConfigUtil.getParamValueByParam("workflowOrcl");
	public void deleteDepartment(Department department)
			throws DataAccessException {
		getEm().remove(department);
	}

	public void insertDepartment(Department department)
			throws DataAccessException {
		getEm().persist(department);
	}

	public Department queryDepartmentById(String departmentId)
			throws DataAccessException {
		List<Department> rr = (List<Department>) getEm().createQuery(
				"from Department d where d.departmentGuid='" + departmentId
						+ "' order by d.tabindex").getResultList();
		if (rr != null && rr.size() > 0)
			return rr.get(0);
		else
			return null;
	}

	public List<Department> queryDepartments(String superiorId)
			throws DataAccessException {
		return getEm().createQuery(
				"from Department d where d.superiorGuid='" + superiorId
						+ "' order by d.tabindex").getResultList();
	}

	public Collection<Department> queryDepartments() throws DataAccessException {
		return getEm().createQuery("from Department d order by d.tabindex").getResultList();
	}

	public void updateDepartment(Department department)
			throws DataAccessException {
		getEm().merge(department);
	}

	public Department queryDepartmentByName(String departmentName)
			throws DataAccessException {
		List<Department> rr = (List<Department>) getEm().createQuery(
				"from Department d where d.departmentName='" + departmentName
						+ "'  order by d.tabindex").getResultList();
		if (rr != null && rr.size() > 0)
			return rr.get(0);
		else
			return null;
	}

	
	public List<Department> queryDepartmentsLikeName(String name,
			int pageIndex, int pageSize) throws DataAccessException {

		List<Department> list = getEm().createQuery(
				"from Department d where d.departmentName like '%" + name
						+ "%'  order by d.tabindex").getResultList();
		if (null != list && list.size() > 0) { // 如果有值
			if (pageIndex >= (list.size() / pageSize)) { // 如果是最后一页
				list.subList(pageIndex * pageSize + 1, list.size());
			} else {
				list.subList(pageIndex * pageSize, (pageIndex + 1) * pageSize);
			}
		} else {
			list = null;
		}
		return list;
	}

	public Integer getDepartmentsLikeNameSize(String name)
			throws DataAccessException {
		List<Department> list = getEm().createQuery(
				"from Department d where d.departmentName like '%" + name
						+ "%'  order by d.tabindex").getResultList();
		return list.size();
	}
	/**
	 * 根据登录者的loginName得出相应的部门信息
	 * 
	 * @param loginName
	 * @return Department
	 * @throws DataAccessException
	 */
	public Department queryDepartmentAfterLogin(String loginName)
			throws DataAccessException {
		List<Department> vds = new ArrayList<Department>();

		String hql = "select new Department(d1.departmentGuid,d1.departmentName,d1.departmentShortdn,d1.superiorGuid,d2.departmentName) "
				+ "from Employee e,Department d1,Department d2 "
				+ "where e.departmentGuid=d1.departmentGuid "
				+ "and d2.departmentGuid=d1.superiorGuid "
				+ "and e.employeeLoginname='" + loginName + "'";

		List list = getEm().createQuery(hql).getResultList();

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Department d = (Department) it.next();
			vds.add(d);
		}

		if (null != vds && vds.size() > 0) {
			Department d = vds.get(0);

			String shortdn = d.getDepartmentShortdn();

			// if (!(null != shortdn || shortdn.length() < 1)) {

			/**
			 * 如果得出部门为根部门，即登录者为管理员的情况下，重新得出部门信息
			 */
			if (null != shortdn && shortdn.split(",").length == 1) {
				List<Department> vds2 = new ArrayList<Department>();

				String hql2 = "select new Department(d1.departmentGuid,d1.departmentName,d1.departmentShortdn,d1.superiorGuid) "
						+ "from Department d1 "
						+ "where d1.departmentGuid = '"
						+ d.getSuperiorGuid() + "'";

				List list2 = getEm().createQuery(hql2).getResultList();

				Iterator it2 = list2.iterator();

				while (it2.hasNext()) {
					Department d2 = (Department) it2.next();
					vds2.add(d2);
				}

				/**
				 * 重新封装一个部门信息的对象
				 */
				if (null != vds2 && vds2.size() > 0) {
					Department d2 = vds2.get(0);
					d.setDepartmentGuid(d2.getDepartmentGuid());
					d.setDepartmentName(d2.getDepartmentName());
					d.setSuperiorGuid(d2.getSuperiorGuid());
				} else {
					return null;
				}
			}
			return d;
		} else {
			return null;
		}

	}
	/**
	 * 
	 * @Title: queryDepartmentListByDN
	 * @Description: 根据dn获取所有相关部门
	 * @param @param dn
	 * @param @return    设定文件
	 * @return List<Department>    返回类型
	 * @throws
	 */
	public List<Department> queryDepartmentListByDN(String dn)
	{
		List<Department> list = getEm().createQuery("from Department d where d.departmentHierarchy like '%" + dn+ "'  order by d.tabindex").getResultList();
		return list;
	}

	public List<Department> queryDepartmentsBydepIds(String depIds) {
		List<Department> list = getEm().createQuery("from Department d where d.departmentGuid in ("+depIds+")  order by d.tabindex").getResultList();
		return list;
	}

	public List<Department> queryDepartmentsBySuperdepIds(String depIds) {
		List<Department> list = getEm().createQuery("from Department d where d.superiorGuid in ("+depIds+")  order by d.tabindex").getResultList();
		return list;
	}
	
	public List<Employee> findEmployeeListByDepId(Department dep) {
		List<Employee> list = getEm().createQuery("from Employee d where d.departmentGuid ='"+dep.getDepartmentGuid()+"' order by d.tabindex").getResultList();
		return list;
	}

	@Override
	public List<Department> queryDepartmentsByShortdnAndDept(
			String departmentShortdn, String deptsid) {
		String[] dept =deptsid.split(",");
		String querySql = "";
		for(int i=0;i<dept.length;i=i+900){
			if(Utils.isNotNullOrEmpty(querySql)){
				querySql +=" or ";
			}
			querySql +="  t1.department_guid in ('";
			for(int j=0;j<900&&(i+j)<dept.length;j++){
				querySql +=dept[i+j]+"','";
			}
			querySql = querySql.substring(0, querySql.length()-3);
			querySql+="')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from zwkj_department t where t.department_shortdn like '")
			.append(departmentShortdn==null?"":(departmentShortdn+",")).append("%' and t.department_shortdn not like '")
			.append(departmentShortdn==null?"":(departmentShortdn+",")).append("%,%' and (select count(1) from zwkj_department t1 ")
			.append(" where t1.department_shortdn like t.department_shortdn||'%' and (")
			.append(querySql)
			.append("))>0  order by t.tabindex");
		Query query=super.getEm().createNativeQuery(sql.toString(),Department.class);
		return query.getResultList();
	}
	
	public List<Object[]> getDeptInfo() {
		//	String sql = "select t.department_name as departname,t.tabindex as departindex ,f.department_name as unitname ,f.tabindex as unitindex ,t.department_guid as deptId from zwkj_department t , zwkj_department f where t.superior_guid = f.department_guid order by f.tabindex";
			String sql = "select t.department_name as departname,t.tabindex as departindex ,t.department_guid, t.superior_guid from zwkj_department t  order by t.tabindex";
			Query query=getEm().createNativeQuery(sql);
			return query.getResultList();
		}

	@Override
	public List<Department> queryDepartmentListByShortDN(String dn) {
		String sql = " from Department t where t.departmentShortdn like '%"+dn+"%'";
		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public List<Department> queryThirdDepartments(String superiorId) throws DataAccessException {
		String hql = "from Department t where t.departmentGuid in (select d.departmentGuid from Department d where d.superiorGuid=:superiorGuid)";
		Query query = getEm().createQuery(hql);
		query.setParameter("superiorGuid", superiorId);
		return query.getResultList();
	}

	@Override
	public Department findSiteDept(String ids) {
		String hql = "from Department t where t.departmentGuid in ("+ids+") and t.isSite = 1 order by t.tabindex desc";
		Query query = getEm().createQuery(hql);
		List<Department> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Department> queryAllSite() {
		String hql = "from Department t where t.isSite = 1 order by t.tabindex desc";
		Query query = getEm().createQuery(hql);
		List<Department> list = query.getResultList();
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<Object[]> findDepartmentList(Map<String, String> inmap) {
		String sql=" select t.department_guid, '', superior_guid, t.department_name, '' from zwkj_department t ";
		return getEm().createNativeQuery(sql).getResultList();
	}

	/* (non-Javadoc)
	 * @see cn.com.trueway.sys.dao.DepartmentDao#getSuperiorByDepartmentId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Department getSuperiorByDepartmentId(String id, String rootId,
			String rootId2) {
		StringBuffer sql = new StringBuffer(" select * from zwkj_department where 1=1 ");
		sql.append(" and department_guid = ( ");
		sql.append(" select superior_guid from zwkj_department where department_guid='"+id+"' ");
		sql.append(" and superior_guid<>'"+rootId+"' and superior_guid<>'"+rootId2+"' ) ");
		List<Department> list = super.getEm().createNativeQuery(sql.toString(), Department.class).getResultList();
		Department department = null;
		if(list!=null && list.size()>0){
			department = list.get(0);
		}
		return department;
	}

	/* (non-Javadoc)
	 * @see cn.com.trueway.sys.dao.DepartmentDao#getDepartId(java.lang.String)
	 */
	@Override
	public String getDepartId(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from zwkj_employee t where t.employee_guid='").append(id).append("'");
		List<Employee> list = getEm().createNativeQuery(sql.toString(),Employee.class).getResultList();
		if(null!=list&&list.size()>0){
			return list.get(0).getDepartmentGuid().toString();
		}else{
			return "";
		}
	}
	@Override
	public List<Object[]> findAllChildDeptObjects(String departmentid)
			throws DataAccessException {
		String sql=
				"	select t.department_guid, t.department_name, t.department_shortdn\n" + 
				"          from zwkj_department t\n" + 
				"         start with t.department_guid =:id\n" + 
				"        connect by prior t.department_guid = t.superior_guid";
			Query query=getEm().createNativeQuery(sql);
			query.setParameter("id", departmentid);
			return query.getResultList();
	}
	
	@Override
	public CommonGroup saveCommonGroup(CommonGroup cg) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		String createtime = bartDateFormat.format(cg.getCreateTime());
		String sql = " insert into "+workflowOrcl+".T_COMMON_GROUP  (id,name,belong_to,create_time) values ('"+cg.getId()+"','"+cg.getName()+"','"+cg.getBelongTo()+"',to_date('"+createtime+"', 'yyyy-MM-dd HH24:mi:SS'))";
		Query query = this.getEm().createNativeQuery(sql);
		query.executeUpdate();
		
		String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.id = '"+cg.getId()+"'";
		List<Object[]> datas = getEm().createNativeQuery(hql).getResultList();
		CommonGroup commonGroup = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			 commonGroup = new  CommonGroup(); 
			commonGroup.setId(data[0]==null?"":data[0].toString());
			commonGroup.setName(data[1]==null?"":data[1].toString());
			commonGroup.setBelongTo(data[2]==null?"":data[2].toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				commonGroup.setCreateTime(sdf.parse(data[3].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return commonGroup;
	}

	@Override
	public void saveCommonGroupUsers(CommonGroupUsers cgu) {
		String sql = " insert into "+workflowOrcl+".T_COMMON_GROUP_USERS  (EMPID,GID,EMPNAME,ID,ORDERINDEX,DEPTNAME) values (:empid,:gid,:empname,:id,'','')";
		Query query = this.getEm().createNativeQuery(sql);
		query.setParameter("empid", cgu.getEmpId());
		query.setParameter("gid", cgu.getGid());
		query.setParameter("empname", cgu.getEmpName());
		query.setParameter("id", cgu.getId());
		query.executeUpdate();
	}

	@Override
	public void deleteCommonGroup(CommonGroup cg) {
//		this.getEm().remove(this.getEm().merge(cg));
		String hql = "delete from "+workflowOrcl+".T_COMMON_GROUP t where t.id = '"+cg.getId()+"'";
		Query query = this.getEm().createNativeQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void deleteCommonGroupUsers(CommonGroupUsers cgu) {
		this.getEm().remove(this.getEm().merge(cgu));
	}

	@Override
	public void updateCommonGroup(CommonGroup cg) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		String createtime = bartDateFormat.format(cg.getCreateTime());
		String sql = " update "+workflowOrcl+".T_COMMON_GROUP t set t.name='"+cg.getName()+"' ,t.belong_to = '"+cg.getBelongTo()+"',t.create_time = to_date('"+createtime+"', 'yyyy-MM-dd HH24:mi:SS') where t.id='"+cg.getId()+"'";
		Query query = this.getEm().createNativeQuery(sql);
		query.executeUpdate();
	}

	@Override
	public void updateCommonGroupUsers(CommonGroupUsers cgu) {
		this.getEm().merge(cgu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid) {
		//String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from workflow_njxxzx.T_COMMON_GROUP t where t.BELONG_TO = '"+uid+"' order by t.CREATE_TIME";
		String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.BELONG_TO = '"+uid+"'  order by t.CREATE_TIME";	
		List<Object[]> datas = getEm().createNativeQuery(hql).getResultList();
		List<CommonGroup> list = new ArrayList<CommonGroup>();
		CommonGroup commonGroup = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			 commonGroup = new  CommonGroup(); 
			commonGroup.setId(data[0]==null?"":data[0].toString());
			commonGroup.setName(data[1]==null?"":data[1].toString());
			commonGroup.setBelongTo(data[2]==null?"":data[2].toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				commonGroup.setCreateTime(sdf.parse(data[3].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list.add(commonGroup);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonGroup> findAllCommonGroupByUid(String uid,String siteId,String role) {
		//String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from workflow_njxxzx.T_COMMON_GROUP t where t.BELONG_TO = '"+uid+"' order by t.CREATE_TIME";
		//String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.BELONG_TO = '"+uid+"' or (t.BELONG_TO is null and t.siteid = '"+siteId+"') order by t.CREATE_TIME";
		String hql ="";
		if("1".equals(role)){
			hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.BELONG_TO is null and t.siteid = '"+siteId+"' order by t.CREATE_TIME";	
		}else{
			hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.BELONG_TO = '"+uid+"' and t.siteid = '"+siteId+"' order by t.CREATE_TIME";
		}
		 	
		List<Object[]> datas = getEm().createNativeQuery(hql).getResultList();
		List<CommonGroup> list = new ArrayList<CommonGroup>();
		CommonGroup commonGroup = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			 commonGroup = new  CommonGroup(); 
			commonGroup.setId(data[0]==null?"":data[0].toString());
			commonGroup.setName(data[1]==null?"":data[1].toString());
			commonGroup.setBelongTo(data[2]==null?"":data[2].toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				commonGroup.setCreateTime(sdf.parse(data[3].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list.add(commonGroup);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid) {
		String hql = "select * from "+workflowOrcl+".T_COMMON_GROUP_USERS t where t.gid = :gid";
		Query query = getEm().createNativeQuery(hql,CommonGroupUsers.class);
		query.setParameter("gid", gid);
		List<CommonGroupUsers> list=query.getResultList();
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonGroupUsers> findAllCommonGroupUsersByGid(String gid,String mc) {
		String hql = "select * from "+workflowOrcl+".T_COMMON_GROUP_USERS t where t.gid = :gid and t.empName like :mc";
		Query query = this.getEm().createNativeQuery(hql,CommonGroupUsers.class);
		query.setParameter("gid", gid);
		query.setParameter("mc", "%"+mc+"%");
		return query.getResultList();
	}

	@Override
	public CommonGroup findCommonGroupById(String id) {
		String hql = " select t.id,t.name,t.BELONG_TO,t.CREATE_TIME from "+workflowOrcl+".T_COMMON_GROUP t where t.id = '"+id+"'";
		List<Object[]> datas = getEm().createNativeQuery(hql).getResultList();
		CommonGroup commonGroup = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			 commonGroup = new  CommonGroup(); 
			commonGroup.setId(data[0]==null?"":data[0].toString());
			commonGroup.setName(data[1]==null?"":data[1].toString());
			commonGroup.setBelongTo(data[2]==null?"":data[2].toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				commonGroup.setCreateTime(sdf.parse(data[3].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return commonGroup;
	}
	

	@Override
	public void deleteCommonGroupUsersByGid(String gid) {
		String hql = "delete from "+workflowOrcl+".T_COMMON_GROUP_USERS t where t.gid = ?";
		Query query = this.getEm().createNativeQuery(hql);
		query.setParameter(1, gid);
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
	public List<Department> getAllChildDeptBydeptId(String deptId) {
		String hql = "select t.* from ZWKJ_DEPARTMENT t start with t.department_guid = '"+deptId+"' connect by prior  t.department_guid = t.superior_guid order by t.tabindex";
		Query query=super.getEm().createNativeQuery(hql.toString(),Department.class);
		return query.getResultList();
	}
	@Override
	public List<Department> getAllChildDeptBydepts(String deptId) {
		String hql = "select t.* from ZWKJ_DEPARTMENT t where t.superior_guid='"+deptId+"' order by t.tabindex";
		Query query=super.getEm().createNativeQuery(hql.toString(),Department.class);
		return query.getResultList();
	}
	
	@Override
	public List<String> queryDepartmentIDsBySuperdepIds(String supDeptId) {
		List<String> list = getEm().createNativeQuery("select d.DEPARTMENT_GUID from  ZWKJ_DEPARTMENT d where d.SUPERIOR_GUID in ("+supDeptId+")  order by d.TABINDEX").getResultList();
		return list;
	}

	@Override
	public void mergeSiteSource(SiteSource ss) {
		this.getEm().merge(ss);
	}

	public List<Object[]> getAllSiteName(){
		List<Object[]> list = getEm().createNativeQuery("select t.* from nj_site t").getResultList();
		return list;
	}
	
	public String getIdByDeptId(String deptId){
		String idsOrcl = SystemParamConfigUtil2.getParamValueByParam("idsOrcl");
		String sql = " select id from "+idsOrcl+". ids_dept t  where issite = '1' start  with id = '"+deptId+"'  connect by prior t.pid = t.id";
		Query query = this.getEm().createNativeQuery(sql);
		String id = query.getSingleResult().toString();
		return id;
	}
	
	public String getNameByDeptId(String deptId){
		String idsOrcl = SystemParamConfigUtil2.getParamValueByParam("idsOrcl");
		String sql = " select name from "+idsOrcl+". ids_dept t  where issite = '1' start  with id = '"+deptId+"'  connect by prior t.pid = t.id";
		Query query = this.getEm().createNativeQuery(sql);
		String id = query.getSingleResult().toString();
		return id;
	}
	
	public Map<String,String> getPtJob(String userId){
		String sql = " SELECT JOBCODE FROM TRUEOA_PTJOB WHERE USER_ID = '"+userId+"' ";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && list.size()>0){
			Object[] o = (Object[])list.toArray();
			String jobcode = (String)o[0];
			map.put("jobcode", jobcode);
			return map;
		}else{
			return null;
		}
	}
}
