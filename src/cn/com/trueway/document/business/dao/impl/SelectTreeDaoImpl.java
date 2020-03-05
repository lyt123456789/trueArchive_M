package cn.com.trueway.document.business.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.dao.SelectTreeDao;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.GwDepart;
import cn.com.trueway.document.business.docxg.client.vo.GwDepartext;
import cn.com.trueway.document.business.docxg.client.vo.VGwDepart;
import cn.com.trueway.document.business.model.Depgroup;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.EmpGroup;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;

/**
 * 描述：SelectTreeDaoImpl<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-8 上午10:50:03<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@SuppressWarnings("unchecked")
public class SelectTreeDaoImpl extends BaseDao implements SelectTreeDao {

	/**
	 * 根据parentId得到其下所有的GwDepartext
	 * 
	 * @param parentId
	 * @return
	 */
	public List<GwDepartext> queryGwDepartextByParent(String parentId) {
		String hql = "from GwDepartext s where s.parentid='" + parentId + "'";
		Query query = super.getEm().createQuery(hql);
		List<GwDepartext> list = query.getResultList();
		return list;
	}
	
	/**
	 * 得到所有的GwDepart
	 * 
	 * @return
	 */
	public List<GwDepart> queryGwDeparts() {
		String hql = "from GwDepart s";
		Query query = super.getEm().createQuery(hql);
		List list = query.getResultList();
		return list;
	}
	
	/**
	 * 根据Department的departmentId找到对应的Department对象
	 * 
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public Department queryDepartmentById(String departmentId) {
		String hql = "from Department d where d.departmentGuid=? order by d.tabindex";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, departmentId);
		List<Department> list = query.getResultList();
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	public List<Department> findAllDepartments() {
		String hql = "from Department d order by d.tabindex";
		Query query = super.getEm().createQuery(hql);
		List<Department> l = (List<Department>) query.getResultList();
		return l;
	}
	
	public Department findRootDepartment() {
		String hql = "from Department d where d.departmentGuid = d.superiorGuid or d.departmentGuid = null ";
		Query query = super.getEm().createQuery(hql);
		Department d = (Department) query.getSingleResult();
		return d;
	}
	
	
	/**
	 * 根据userId找到对应的Employee对象
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Employee findEmployeeById(String userId) {
		String hql = "from Employee e where e.employeeGuid = ?";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, userId);
		List list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return (Employee) list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据depIds找到所有对应的employeeId的集合
	 * 
	 * @param depIds
	 * @return
	 */
	public Set<String> findEmployees(List<String> depIds) {
		StringBuilder in = new StringBuilder();
		for (String depId : depIds) {
			in.append("'");
			in.append(depId);
			in.append("'");
			in.append(",");
		}
		String inString = in.toString();
		if (inString.trim().length() > 0) {
			inString = in.substring(0, in.length() - 1);
		}
		String hql = "from Employee e where e.departmentGuid in (" + inString
				+ ")";
		Query query = super.getEm().createQuery(hql);
		List<Employee> list = query.getResultList();
		Set<String> userIds = new HashSet<String>();
		for (Employee employee : list) {
			userIds.add(employee.getEmployeeGuid());
		}
		return userIds;
	}
	
	private String findAlldep(String str){
		StringBuffer in = new StringBuffer(str); 
		StringBuffer sb = new StringBuffer(str); 
		for(;;){
			List<Department> deps =null;
			try {
				deps = (List<Department>)getEm().createQuery("from Department e WHERE e.superiorGuid IN (" + in+ ")").getResultList();
			} catch (Exception e) {
			}
			if(deps==null || deps.isEmpty()){
				break;
			}else{
				in = new StringBuffer();
				for(Department d : deps){
					in.append("'");
					in.append(d.getDepartmentGuid());
					in.append("'");
					in.append(",");
				}
				if(in.length()!=0)
				in = new StringBuffer(in.toString().substring(0, in.length() - 1));
				sb.append(","+in.toString());
			}
		}
		return sb.toString();
	}
	
	public DTPageBean findEmployeesByDepsId(List<String> depIds, String userName, int currentPage,int numPerPage) {
		StringBuilder in = new StringBuilder();
		for (String depId : depIds) {
			in.append("'");
			in.append(depId);
			in.append("'");
			in.append(",");
		}
		String inString = in.toString();
		if (inString.trim().length() > 0) {
			inString = in.substring(0, in.length() - 1);
		}
		//找到所有的部门ID
		inString = findAlldep(inString);
		//inString = inString.substring(0, inString.length() - 1);
		String querySql = "SELECT * FROM risenet_employee e WHERE e.DEPARTMENT_GUID IN (" + inString+ ")";
		String totalRowsSql = "SELECT COUNT(EMPLOYEE_GUID) FROM risenet_employee e WHERE e.DEPARTMENT_GUID IN (" + inString+ ")";
		//按名称索引
		if(userName!=null&&userName.trim().length()!=0){
			querySql += " and e.EMPLOYEE_NAME like '%"+userName+"%'";
			totalRowsSql += " and e.EMPLOYEE_NAME like '%"+userName+"%'";
		}
		return pagingQuery(totalRowsSql, querySql+" order by e.tabindex", currentPage, numPerPage, Employee.class);
	}

	public List<Department> findDepartments(String depId) {
		String hql = "from Department d where d.superiorGuid=? order by d.tabindex";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, depId);
		List<Department> l = (List<Department>) query.getResultList();
		return l;
	}

	public List<Employee> findEmployeesByDepIds(List<String> depIds) {
		if (depIds != null) {
			StringBuilder inString = new StringBuilder();
			for (String depId : depIds) {
				inString.append("'");
				inString.append(depId);
				inString.append("'");
				inString.append(",");
			}
			String hql = "from Employee e where e.departmentGuid in ("
					+ inString.substring(0, inString.length() - 1)
					+ ") order by e.tabindex";
			Query query = super.getEm().createQuery(hql);
			return query.getResultList();
		} else {
			return null;
		}
	}

	public List<Depgroup> getDepGroupByUserId(String userId) {
		return (List<Depgroup>)getEm().createQuery("from Depgroup p where p.superior_guid = :userid").setParameter("userid", userId).getResultList();
	}

	public int deleteGroupById(String id) {
		return getEm().createNativeQuery("delete from depgroup where id = :id").setParameter("id", id).executeUpdate();
	}

	public void saveGroup(Depgroup depgroup) {
		getEm().persist(depgroup);
	}

	public Employee queryEmployeeByEmployeeGuid(String employeeGuid) {
		return getEm().find(Employee.class, employeeGuid);
	}
	
	
	/**
	 * 描述：新增人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void insertEmpGroup(EmpGroup empGroup){
		getEm().persist(empGroup);
	}
	
	/**
	 * 描述：根据userId 查询人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public List<EmpGroup> queryEmpGroupsByUserId(String userId){
		return getEm().createQuery("from EmpGroup t where t.userId = :userId").setParameter("userId", userId).getResultList();
	}
	
	/**
	 * 描述：根据userId  删除人员组
	 * @param employeeGuid
	 * @return Employee
	 * 作者:吕建<br>
	 * 创建时间:2012-8-22 上午11:16:07
	 */
	public void removeEmpGroupByUserId(String id){
		getEm().createNativeQuery("delete from EMPGROUP  where id = :id").setParameter("id", id).executeUpdate();
	}

	/**
	 * @param pid
	 * @param id
	 * @param name
	 * @param isSub
	 * @param orderNum
	 * @param hasSub
	 */
	@Override
	public void insertDept(String pid, String id, String name, String isSub,
			String orderNum, String hasSub) {
		if("".equals(orderNum)){
			orderNum = "0";
		}
		String sql = "insert into DOCEXCHANGE_DEPARTMENT values('"+id+"','"+name+"','"+pid+"','"+isSub+"',"+orderNum+",'"+hasSub+"')";
		this.getEm().createNativeQuery(sql).executeUpdate();
		
	}

	/**
	 * @param deptId
	 * @return
	 */
	@Override
	public List<DocXgDepartment> getDocXgDept(String pdeptId) {
		if("".equals(pdeptId)){
			pdeptId = "1";
		}
		String hql = "from DocXgDepartment t where t.pid='"+pdeptId+"' order by t.orderNum asc";
		return this.getEm().createQuery(hql).getResultList();
	}
	
	@Override
	public List<DocXgDepartment> getDocXgDeptById(String id){
		String hql = "from DocXgDepartment t where t.deptGuid='"+id+"' order by t.orderNum asc";
		List<DocXgDepartment> list = this.getEm().createQuery(hql).getResultList();
		return list;
	}

	@Override
	public DocXgDepartment getDocXgDeptById4Set(String id){
		String hql = "from DocXgDepartment t where t.id='"+id+"' order by t.orderNum asc";
		List<DocXgDepartment> list = this.getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Object[]>  getExchanegDepById(String depid){
		String hql = "select t.department_guid,t.department_name,d.department_shortdn  from docexchange_department t ,zwkj_department d " +
				"where t.department_guid = d.department_guid and t.department_guid = '"+depid+"'";
		return this.getEm().createNativeQuery(hql).getResultList();
	}

	@Override
	public void addDepRelationShip(DepRelationShip relationShip) {
		getEm().persist(relationShip);
	}

	@Override
	public void deleteDepRelationShip(DepRelationShip relationShip) {
		if(relationShip!=null){
			String id = relationShip.getId();
			String sql = "delete from DOCEXCHANGE_RELATIONSHIP t where t.id='"+id+"'";
			getEm().createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public int getDepRelationShipCount() {
		String sql = "select count(*) from DOCEXCHANGE_RELATIONSHIP t";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<DepRelationShip> getDepRelationShipList() {
		String sql = "select t.id, t.gtj_depId, t.docxg_depId ,t.docxg_depname, d.DEPARTMENT_NAME  " +
				" from DOCEXCHANGE_RELATIONSHIP t, docexchange_department d" +
				" where  d.id = t.gtj_depId ";
		List list = getEm().createNativeQuery(sql).getResultList();
		List<DepRelationShip> shipList = new ArrayList<DepRelationShip>();
		DepRelationShip entity = null;
		for(int i=0; list!=null && i<list.size(); i++){
			entity = new DepRelationShip();
			Object[] data = (Object[])list.get(i);
			entity.setId(data[0].toString());
			entity.setGtj_depId(data[1].toString());
			entity.setDocxg_depId(data[2].toString());
			entity.setDocxg_depName(data[3].toString());
			entity.setGtj_depName(data[4].toString());
			shipList.add(entity);
		}
		return shipList;
	}

	@Override
	public String getDocXgName(String id) {
		String sql = "select t.name from docexchange_depart t where t.id='"+id+"'";
		List list  = getEm().createNativeQuery(sql).getResultList();
		if(list==null || list.size()==0){
			sql = "select t.name from  docexchange_departext t where t.guid='"+id+"'";
			list =  getEm().createNativeQuery(sql).getResultList();
		}
		String name = "";
		if(list!=null && list.size()>0){
			name = list.get(0).toString();
		}
		return name;
	}

	@Override
	public void addVGwDepart(List<VGwDepart> list) {
		VGwDepart vGwDepart = null;
		for(int i=0; i<list.size(); i++){
			vGwDepart = list.get(i);
			GwDepart depart = vGwDepart.getDepart();
			List<GwDepartext> textlist = vGwDepart.getSubDepartextList();
			if(depart!=null){
				getEm().merge(depart);
			}
			for(GwDepartext txt: textlist){
				txt.setParentid(depart.getId());
				getEm().merge(txt);
			}
		}
	}

	@Override
	public List<DocXgDepartment> getAllDocXgDepts(String mc) {
		String sql = "select t.* from DOCEXCHANGE_DEPARTMENT t where t.DEPARTMENT_NAME like '%"+mc+"%' "
				+ "start with t.superior_guid = '1' "
				+ "connect by prior t.department_guid = t.superior_guid";
		return this.getEm().createNativeQuery(sql, DocXgDepartment.class).getResultList();
	} 
}
