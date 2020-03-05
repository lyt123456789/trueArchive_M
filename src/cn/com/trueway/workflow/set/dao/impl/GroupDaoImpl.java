package cn.com.trueway.workflow.set.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.set.dao.GroupDao;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.UserGroup;

public class GroupDaoImpl extends BaseDao implements GroupDao {

	public void delete(InnerUser innerUser) throws DataAccessException {
		String hql = "delete from InnerUser t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", innerUser.getId());
		query.executeUpdate();
	}

	public List<InnerUser> getAllInnerUser() throws DataAccessException {
		String hql = "from InnerUser t order by t.zindex asc";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}

	public InnerUser getOneInnerUserById(String id) throws DataAccessException {
		String hql = "from InnerUser t where t.id=:id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", id);
		List<InnerUser> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public InnerUser save(InnerUser innerUser) throws DataAccessException {
		getEm().persist(innerUser);
		return innerUser;
	}

	public void update(InnerUser innerUser) throws DataAccessException {
		getEm().merge(innerUser);
	}

	public Integer getInnerUserCountForPage(String column, String value,
			InnerUser innerUser) {
		String hql="select count(*) from InnerUser t where 1=1 ";
		if (CommonUtil.stringNotNULL(column)&&CommonUtil.stringNotNULL(value)) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		if (innerUser!=null) {
			if (innerUser.getType()==null) 
				hql+=" and t.type='3' or (t.type='4' and t.workflowId='"+innerUser.getWorkflowId()+"')";
			if (innerUser.getType()!=null) {
				Integer type = innerUser.getType();
				String workflowId = innerUser.getWorkflowId();
				if(type != null && type == 4){
					hql += " and t.type=4";
				}
				if(CommonUtil.stringNotNULL(workflowId)){
					hql += " and t.workflowId='" + workflowId + "'";
				}else{
					hql += " and t.workflowId is null";
				}
			}
			if(StringUtils.isNotBlank(innerUser.getSiteId())){
				hql += " and (t.siteId = '" + innerUser.getSiteId() + "' or t.siteId is null) ";
			}
		}
		
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	public List<InnerUser> getInnerUserListForPage(String[] column, String[] value,
			InnerUser innerUser, Integer pageindex, Integer pagesize) {
		String hql="from InnerUser t where 1=1 ";
		if (column!=null&&value!=null) {
			for(int i=0;i<column.length;i++){
				if(Utils.isNotNullOrEmpty(column[i])){
					// 判断value是否含有 逗号分割
					if(value[i] != null &&!"".equals(value[i]) &&value[i].indexOf(",")>-1){
						String tempValue = "'" + value[i].replaceAll(",", "','") + "'";
						hql+=" and t."+column[i]+" in ("+tempValue+")";
					}else{
						hql+=" and t."+column[i]+" like '%"+value[i]+"%'";
					}
				}
			}
		}
		if (innerUser!=null) {
			/*if (innerUser.getType()==null) 
				hql+=" and t.type='3' or (t.type='4' and t.workflowId='"+innerUser.getWorkflowId()+"')";
			if (innerUser.getType()!=null) {
				hql+=" and t.type="+innerUser.getType();
				if(innerUser.getType()==4){
					hql+=" and t.workflowId='"+innerUser.getWorkflowId()+"'";
				}
			}*/
			
			Integer type = innerUser.getType();
			String workflowId = innerUser.getWorkflowId();
			if(type != null && type == 4){
				hql += " and t.type=4";
			}
			if((value == null) || (value.length == 1 && CommonUtil.stringIsNULL(value[0]))){
				if(CommonUtil.stringNotNULL(workflowId)){
					hql += " and t.workflowId='" + workflowId + "'";
				}else{
					hql += " and t.workflowId is null";
				}
			}
			if(StringUtils.isNotBlank(innerUser.getSiteId())){
				hql += " and (t.siteId = '" + innerUser.getSiteId() + "' or t.siteId is null) ";
			}
		}
		hql+=" order by t.type desc,t.zindex desc";
		Query query=getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<InnerUser> getInnerUserByType(String type, String workflowId, String siteId) {
		String hql="from InnerUser t where 1=1 ";
		if(CommonUtil.stringNotNULL(type)){
			if("4".equals(type)){
				hql += " and t.type=4";
				if(StringUtils.isNotBlank(workflowId)){
					hql += " and t.workflowId = '" + workflowId + "'";
				}else{
					hql += " and t.workflowId is not null ";
				}
			}else if("3".equals(type)){
				hql += " and t.type=4 and t.workflowId is null";
				if(StringUtils.isNotBlank(siteId)){
					hql += " and t.siteId='" + siteId + "'";
				}
			}else{
				hql+=" and t.type=" + type;
			}
		}			
		hql+=" order by t.zindex desc";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	public List<InnerUserMapEmployee> getListByInnerUserId(String inneruserId,String name, String ids)
			throws DataAccessException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println("getListByInnerUserId-------------start time="+ sdf.format(new Date()));
		String hql="select t.* from t_wf_core_inneruser_map_user t, ZWKJ_EMPLOYEE em " +
				" where em.employee_Guid = t.employee_id and em.ISLEAVE!='1' " ;
		if(ids!=null && !ids.equals("")){
			hql += " and em.department_guid in ("+ids+")";
		}
		String[] list = null;
		if(StringUtils.isNotBlank(inneruserId)){
			list = inneruserId.split(",");
			hql+=" and t.inneruser_id in (";
			for(int i = 0, size = list.length; i < size ; i++){
				if(i == 0){
					hql += " ? ";
				}else{
					hql += " ,? ";
				}
			}
			hql += ")";
		}
		if(Utils.isNotNullOrEmpty(name)){
			hql += " and t.employee_name like '%"+name+"%' ";
		}
		hql+=" order by t.zindex asc";
		Query query=getEm().createNativeQuery(hql, InnerUserMapEmployee.class);
		if(StringUtils.isNotBlank(inneruserId)){
			for(int i = 0, size = list.length; i < size ; i++){
				query.setParameter(i+1, list[i]);
			}
		}
		System.out.println("getListByInnerUserId-------------end time="+ sdf.format(new Date()));
		return query.getResultList();
	}
	
	public List<InnerUserMapEmployee> getListByEmployeeId(String vc_employeeid, String workflowid)
			throws DataAccessException {
		String hql="from InnerUserMapEmployee t where t.employee_id=:employee_id and t.inneruser_id in (select id from InnerUser where workflowid='"+workflowid+"' or workflowid is null) order by t.zindex asc";
		Query query=getEm().createQuery(hql);
		query.setParameter("employee_id", vc_employeeid);
		return query.getResultList();
	}

	public void save(InnerUserMapEmployee innerUserMapEmployee)
			throws DataAccessException {
		getEm().persist(innerUserMapEmployee);
	}

	public void deleteByInnerUserId(String inneruserId)
			throws DataAccessException {
		String hql = "delete from InnerUserMapEmployee t where t.inneruser_id = :inneruserId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("inneruserId", inneruserId);
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	public List<InnerUser> getInnerUserList(String lcid, String type) {
		String hql="from InnerUser t where t.type="+type+" and t.workflowId='"+lcid+"' order by t.zindex desc";
		hql+=" order by t.zindex desc";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	public InnerUser findInnerUserById(String id) {
		return getEm().find(InnerUser.class, id);
	}
	
	
	
	
	
	
	
	
	
	
	public void delete(UserGroup userGroup) throws DataAccessException {
		String hql = "delete from UserGroup t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", userGroup.getId());
		query.executeUpdate();
	}

	public List<UserGroup> getAllUserGroup() throws DataAccessException {
		String hql = "from UserGroup t order by t.zindex asc";
		Query query = super.getEm().createQuery(hql);
		return query.getResultList();
	}

	public UserGroup getOneUserGroupById(String id) throws DataAccessException {
		String hql = "from UserGroup t where t.id=:id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", id);
		List<UserGroup> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public void save(UserGroup userGroup) throws DataAccessException {
		getEm().persist(userGroup);
	}

	public void update(UserGroup userGroup) throws DataAccessException {
		getEm().merge(userGroup);
	}

	public Integer getUserGroupCountForPage(String column, String value,
			UserGroup userGroup) {
		String hql="select count(*) from UserGroup t where 1=1 ";
		if (CommonUtil.stringNotNULL(column)&&CommonUtil.stringNotNULL(value)) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		if (userGroup!=null) {
			if (CommonUtil.stringNotNULL(userGroup.getUserid())) {
				hql+=" and t.userid='"+userGroup.getUserid()+"'";
			}
		}
		
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	public List<UserGroup> getUserGroupListForPage(String column, String value,
			UserGroup userGroup, Integer pageindex, Integer pagesize) {
		String hql="from UserGroup t where 1=1 ";
		if (CommonUtil.stringNotNULL(column)&&CommonUtil.stringNotNULL(value)) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		if (userGroup!=null) {
			if (CommonUtil.stringNotNULL(userGroup.getUserid())) {
				hql+=" and t.userid='"+userGroup.getUserid()+"'";
			}
		}
		hql+=" order by t.zindex desc,t.intime desc";
		Query query=getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	@Override
	public List<UserGroup> getUserGroupList(UserGroup userGroup,String userids) {
		String hql="from UserGroup t where 1=1 ";
		if(userids!=null&&!"".equals(userids)){
			hql +=" and t.relation_userids like '%"+userids+"%'";
		}
		if (userGroup!=null) {
			if (CommonUtil.stringNotNULL(userGroup.getUserid())) {
				hql+=" and t.userid='"+userGroup.getUserid()+"'";
			}
		}
		hql+=" order by t.zindex desc,t.intime desc";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public UserGroup getOneUserGroupById(String groupid, String userids) {
		String hql = "from UserGroup t where t.id=:id";
		if(userids!=null&&!"".equals(userids)){
			hql+=" and t.relation_userids like '%"+userids+"%'";
		}
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", groupid);
		List<UserGroup> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Department> getUserGroupList(String ids) {
		if(ids==null || ids.equals("")){
			return null;
		}
		String sql = "select t.department_guid,t.department_name,d.department_shortdn  from docexchange_department t ,zwkj_department d " +
				"where t.department_guid = d.department_guid and t.department_guid in ("+ids+")";
		List<Object[]> datas = getEm().createNativeQuery(sql).getResultList();
		List<Department> list = new ArrayList<Department>();
		Department department = null;
		for(int i=0 ;datas!=null && i<datas.size(); i++){
			Object[] data = datas.get(i);
			department = new  Department(); 
			department.setDepartmentGuid(data[0]==null?"":data[0].toString());
			department.setDepartmentName(data[1]==null?"":data[1].toString());
			department.setDepartmentShortdn(data[2]==null?"":data[2].toString());
			list.add(department);
		}
		return list;
	}

	@Override
	public List<InnerUserMapEmployee> getListByInnerUserIdAndGrade(
			String groupId, String name, String employeeGuid) {
			groupId = "'"+groupId.replace(",", "','")+"'";
			String hql = "select t.*, t.rowid  " +
					"from t_wf_core_inneruser_map_user t "+
					  "join t_wf_core_inneruser u "+
					    "on t.inneruser_id = u.id, zwkj_employee  em"+
					" where em.ISLEAVE!='1' and em.employee_guid = t.employee_id";
				hql += " and inneruser_id in ("+groupId+")"+
					   " and u.grade >= "+
					     "  (select s.grade "+
					       "  from t_wf_core_inneruser s "+
					       "  where s.id in "+
					              " (select t.inneruser_id "+
					              "   from t_wf_core_inneruser_map_user t "+
					              "   where t.employee_id = "+
					             "'"+employeeGuid+"' "+
					          " and t.inneruser_id in ("+groupId+")))";
	
		if(Utils.isNotNullOrEmpty(name)){
			hql += " and t.employee_name like '%"+name+"%' ";
		}
		hql+=" order by t.zindex asc";
		Query query=getEm().createNativeQuery(hql, InnerUserMapEmployee.class);
		return query.getResultList();
	
	}

	@Override
	public List<InnerUserMapEmployee> getInnerUserListByIds(String userIds) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println("getInnerUserListByIds------------------start time=" + sdf.format(new Date()));
		String sql = " select t.employee_guid, t.employee_name, t2.department_shortdn from zwkj_employee t , zwkj_department t2 "
			     + " where t.department_guid = t2.department_guid and t.employee_guid in ("+userIds+")";
		List<Object[]>  list = getEm().createNativeQuery(sql).getResultList();
		List<InnerUserMapEmployee>  innerList = new ArrayList<InnerUserMapEmployee>();
		InnerUserMapEmployee  inner = null;
		for(int i=0; i<list.size(); i++){
			inner = new InnerUserMapEmployee();
			Object[] data = list.get(i);
			inner.setEmployee_id(data[0]!=null?data[0].toString():"");
			inner.setEmployee_name(data[1]!=null?data[1].toString():"");
			inner.setEmployee_shortdn(data[2]!=null?data[2].toString():"");
			innerList.add(inner);
		}
		System.out.println("getInnerUserListByIds------------------end time=" + sdf.format(new Date()));
		return innerList;
	}
	
	@Override
	public List<InnerUser> getInnerUserListByParams(Map<String, String> map){
		String workflowId = map.get("workflowId");
		String type = map.get("type");
		String siteId = map.get("siteId");
		String hql = "from InnerUser t where 1=1 ";
		if(CommonUtil.stringNotNULL(type)){
			hql += " and t.type='" + type + "'";
		}
		if(CommonUtil.stringNotNULL(workflowId)){
			hql+=" and (t.workflowId='" + workflowId + "' or workflowId is null) ";
		}
		if(StringUtils.isNotBlank(siteId)){
			hql += " and (t.siteId = '" + siteId + "' or t.siteId is null) ";			
		}
		hql+=" order by t.type desc,t.zindex desc";
		Query query=getEm().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<String> getUserIdFromInnerUserMap(String groupId, String userId) {
		String sql = "select t.id from t_wf_core_inneruser_map_user t where t.inneruser_id=:inneruser_id and t.employee_id=:employee_id";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("inneruser_id", groupId);
		query.setParameter("employee_id", userId);
		return query.getResultList();
	}

	@Override
	public CommonGroup getUserGroupBySiteId(String groupName, String siteId,String userId) {
		String hql = " from CommonGroup t where t.siteId = :siteId and t.name =:name and t.belongTo= :userId ";
		
		Query query = getEm().createQuery(hql);
		query.setParameter("siteId", siteId);
		query.setParameter("name", groupName);
		query.setParameter("userId", userId);
		List<CommonGroup> list = query.getResultList();
		if(list!=null&&list.size()==0){
			return null;
		}
		return list.get(0);
	}
}
