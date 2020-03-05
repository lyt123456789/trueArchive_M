package cn.com.trueway.workflow.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.workflow.core.dao.DepConfigDao;

public class DepConfigDaoImpl  extends BaseDao implements DepConfigDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<DocXgDepartment> getDepsBySuperId(String superior_guid){
		List<DocXgDepartment> list = new ArrayList<DocXgDepartment>();
		String hql = "from DocXgDepartment where pid = :pid order by orderNum ";
		Query query = getEm().createQuery(hql);
		query.setParameter("pid", superior_guid);
		list = query.getResultList();
		return list;
	}
	
	@Override
	public void addDep_F(DocXgDepartment docXgDepartment)  throws Exception {
		if(null != docXgDepartment 
				&& CommonUtil.stringNotNULL(docXgDepartment.getId())){
			getEm().merge(docXgDepartment);
		}else{
			getEm().persist(docXgDepartment);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public DocXgDepartment getDepById(String id){
		List<DocXgDepartment> list = new ArrayList<DocXgDepartment>();
		String hql = "from DocXgDepartment where id = :id order by orderNum ";
		Query query = getEm().createQuery(hql);
		query.setParameter("id", id);
		list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void delDep_F(String[] ids_arr) throws Exception{
		String ids = "";
		if(null != ids_arr && ids_arr.length>0){
			for(String id:ids_arr){
				ids += "'" + id + "',";
			}
			ids = ids.substring(0,ids.length()-1);
			//删除下属子部门
			String sql = " delete from docexchange_department t " +
					" where t.superior_guid in " +
					"(select t2.department_guid " +
					" from docexchange_department t2 where t2.id in ("+ids+")" +
					")";
			getEm().createNativeQuery(sql).executeUpdate();
			//删除父部门
			String sql2 = " delete from docexchange_department t " +
					" where t.id in  ("+ids+")" ;
			getEm().createNativeQuery(sql2).executeUpdate();
		}
	}
	
	@Override
	public void delDep_C(String[] ids_arr) throws Exception{
		String ids = "";
		if(null != ids_arr && ids_arr.length>0){
			for(String id:ids_arr){
				ids += "'" + id + "',";
			}
			ids = ids.substring(0,ids.length()-1);
			//删除本部门
			String sql2 = " delete from docexchange_department t " +
					" where t.id in  ("+ids+")" ;
			getEm().createNativeQuery(sql2).executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocXgDepartment getDepByDepId(String depId){
		List<DocXgDepartment> list = new ArrayList<DocXgDepartment>();
		String hql = "from DocXgDepartment where deptGuid = :deptGuid order by orderNum ";
		Query query = getEm().createQuery(hql);
		query.setParameter("deptGuid", depId);
		list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
