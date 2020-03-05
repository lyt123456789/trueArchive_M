package cn.com.trueway.workflow.set.dao.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.lob.SerializableClob;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.util.ClobToString;


public class ZwkjFormDaoImpl extends BaseDao implements ZwkjFormDao{
	
	public ZwkjForm addForm(ZwkjForm zwkjForm) throws DataAccessException {
		super.getEm().persist(zwkjForm);
		return zwkjForm;
	}

	public void deleteForm(ZwkjForm zwkjForm) throws DataAccessException {
		String hql = "delete from ZwkjForm t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", zwkjForm.getId());
		query.executeUpdate();
	}

	public List<ZwkjForm> getAllFormList() throws DataAccessException {
		return null;
	}

	public List<ZwkjForm> getFormListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return getEm().createQuery(hql).getResultList();
	}

	public List<ZwkjForm> getFormListByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		String hql="from ZwkjForm t where 1=1";
		if (column!=null&&!column.equals("")&&value!=null&&!value.equals("")) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		hql+=" order by t.intime desc";
		Query query=this.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public List<String> getFormListCountByParamForPage(String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		String sql = "select ID from T_WF_CORE_FORM t where t." + column + "='" + value + "'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	public void updateForm(ZwkjForm zwkjForm) throws DataAccessException {
		getEm().merge(zwkjForm);
	}

	public ZwkjForm getOneFormById(String id) throws DataAccessException {
		String hql="from ZwkjForm t where t.id='"+id+"'";
		List<ZwkjForm> list=this.getEm().createQuery(hql).getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public void addFormTagMapColumn(FormTagMapColumn formTagMapColumn)
			throws DataAccessException {
		super.getEm().persist(formTagMapColumn);
	}

	public void deleteFormTagMapColumnByFormId(String formid)
			throws DataAccessException {
		String sql="delete from T_WF_CORE_FORM_MAP_COLUMN where FORMID=:FORMID";
		if (formid!=null&&!formid.equals("")) {
			Query query=getEm().createNativeQuery(sql);
			query.setParameter("FORMID", formid);
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getTableNameByFormIdOfGroup(String formid) throws DataAccessException {
		String hql="from FormTagMapColumn t where t.formid=:formid group by t.tablename,t.listId";
		Query query=getEm().createQuery(hql);
		query.setParameter("formid", formid);
		return query.getResultList();
	}
	
	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String formid)
			throws DataAccessException {
		String hql="from FormTagMapColumn t where t.formid=:formid";
		Query query=getEm().createQuery(hql);
		query.setParameter("formid", formid);
		return query.getResultList();
	}
	
	
	/**
	 * 插入业务表数据: 每个value外包含''(单引号),query.setParameter 避免oracle字段过长引起报错
	 */
	public void saveForm(String tabName,String columnName,String tagName) {
		if(!columnName.equals("")&&!tagName.equals("")){
			String[] columns = columnName.split(",");
			String[] tags =  tagName.substring(0, tagName.length()-1).split("',");
			String  perch = "";
			for(String column: columns){
				perch +="?,";
			}
			if(perch.length()>0){
				perch = perch.substring(0, perch.length()-1);
			}
			String sql = "insert into "+ tabName +"("+columnName+")"
					+" values("+perch+")"; 
			Query query = super.getEm().createNativeQuery(sql);
			for(int i =1; columns!=null && i<columns.length+1; i++){
				String value = tags[i-1].substring(1, tags[i-1].length());
				query.setParameter(i, value);	//占位符
			}
			query.executeUpdate();
		}
	}

	/**
	 * 插入业务表数据: 每个value外包含''(单引号),query.setParameter 避免oracle字段过长引起报错
	 */
	public void saveFormByOther(String tabName,String columnName,String tagName,WfProcess wfProcess) {
		if(!columnName.equals("")&&!tagName.equals("")){
			String[] columns = columnName.split(",");
			String[] tags =  tagName.substring(0, tagName.length()-1).split("',");
			String  perch = "";
			for(int t = 0 ; t< columns.length ; t++){
				if(columns[t] != null && columns[t].equalsIgnoreCase("INSTANCEID")){
					tags[t] = "'"+wfProcess.getWfInstanceUid();
				}
				if(columns[t] != null && columns[t].equalsIgnoreCase("FORMID")){
					tags[t] = "'"+wfProcess.getFormId();
				}
				if(columns[t] != null && columns[t].equalsIgnoreCase("WORKFLOWID")){
					tags[t] = "'"+wfProcess.getWfUid();
				}
				if(columns[t] != null && columns[t].equalsIgnoreCase("PROCESSID")){
					tags[t] = "'"+wfProcess.getWfProcessUid();
				}
				perch +="?,";
			}
			if(perch.length()>0){
				perch = perch.substring(0, perch.length()-1);
			}
			String sql = "insert into "+ tabName +"("+columnName+")"
					+" values("+perch+")"; 
			Query query = super.getEm().createNativeQuery(sql);
			for(int i =1; columns!=null && i<columns.length+1; i++){
				String value = tags[i-1].substring(1, tags[i-1].length());
				query.setParameter(i, value);	//占位符
			}
			query.executeUpdate();
		}
	}
	@SuppressWarnings("unchecked")
	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String oldformId,String formId, String tableName, int todo, String isFirstChildWf) {
		String sql = "";
		//oldformId为空，子流程第一步发起等情况
		if(("").equals(oldformId) || formId.equals(oldformId)){
			if(todo == 1){
				sql = "select t.* from t_wf_core_form_map_column t where t.formid='"+formId+"' and t.assigntablename='"+tableName+"'";
			}else if(todo == 2){
//				if(!("true").equals(isFirstChildWf)){
					sql = "select t.* from t_wf_core_form_map_column t where t.formid='"+formId+"' and t.assigntablename='"+tableName+"'";
//				}else{
//					sql = "select t.* from t_wf_core_form_map_column t where t.formid='"+formId+"' and t.tablename='"+tableName+"'";
//				}
			}
		}else{
			if(todo == 2){
				sql = "select t.* from t_wf_core_form_map_column t where t.formid='"+formId+"' and t.tablename='"+tableName+"'";
			}else if(todo == 1){
				sql = "select t.* from t_wf_core_form_map_column t where t.formid='"+formId+"' and t.assigntablename='"+tableName+"'";
			}
		}
		Query query=getEm().createNativeQuery(sql, FormTagMapColumn.class);
		return query.getResultList();  
	}

	public void deleteForm(String tableName,String formId,String instanceId) {
		String sql = "delete from "+tableName+" t where t.formid='"+formId+"' and t.instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Map> findTableByFormId(String tabName, String formId,String instanceId) {
		Session session = (Session) getEm().getDelegate();
		String sql =  "";
		
		if(formId != null && !"".equals(formId)){
			sql = "select t.* from "+tabName+" t where t.formid='"+formId+"' and t.instanceid='"+instanceId+"'";
		}else{
			sql = "select t.* from "+tabName+" t where  t.instanceid='"+instanceId+"'";
		}
		List<Map>  list = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		if(list == null || list.size() == 0){
			sql = "select t.* from "+tabName+" t where  t.instanceid='"+instanceId+"'";
			list = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		}
	    return list;	
	    
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getSwByInstanceId(String instanceId) {
		Session session = (Session) getEm().getDelegate();
		String sql = "select t.* from t_wf_core_sw t where t.instanceid='"+instanceId+"'";
		return session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
	}

	public Map<String, Object> excuteProcedure(Object[][] param,String prodecureName)
			throws Exception {
		Map<String, Object> m=null;
		//调用存储过程获得数据
			//拼接存储过程执行sql
			String procedureSql="{Call "+prodecureName+"(";
			if (param!=null) {
				for (int i = 0; i < param.length; i++) {
					procedureSql+=i==param.length-1?"?":"?,";
				}
			}
			procedureSql+=")}";
			//设置存储过程参数
			Session session = (Session) getEm().getDelegate();
			CallableStatement cs=session.connection().prepareCall(procedureSql);
			for (int i = 0; i < param.length; i++) {
				Object[] o=param[i];
				if (o.length>=3) {
					if (o[0].toString().toLowerCase().equals("in")) {
						if (o[1].toString().toUpperCase().equals("INTEGER")) {
							cs.setInt(i+1, Integer.parseInt(o[2].toString()));
						}else if (o[1].toString().toUpperCase().equals("VARCHAR")){
							cs.setString(i+1, o[2].toString());
						}else if (o[1].toString().toUpperCase().equals("DATE")){
							cs.setTimestamp(i+1, (Timestamp)o[2]);
						}
					}else if(o[0].toString().toLowerCase().equals("out")){
						if (o[1].toString().toUpperCase().equals("INTEGER")) {
							cs.registerOutParameter(i+1, Types.INTEGER);
						}else if (o[1].toString().toUpperCase().equals("VARCHAR")){
							cs.registerOutParameter(i+1, Types.VARCHAR);
						}else if (o[1].toString().toUpperCase().equals("DATE")){
							cs.registerOutParameter(i+1, Types.TIMESTAMP);
						}
					}
				}
			}
			cs.execute();
			
			m=new HashMap<String, Object>();
			for (int i = 0; i < param.length; i++) {
				Object[] o=param[i];
				if (o.length>=3) {
					if (o[0].toString().toLowerCase().equals("out")) {
						if (o[1].toString().toUpperCase().equals("INTEGER")) {
							m.put(o[2].toString(), cs.getInt(i+1));
						}else if (o[1].toString().toUpperCase().equals("VARCHAR")){
							m.put(o[2].toString(), cs.getString(i+1));
						}else if (o[1].toString().toUpperCase().equals("DATE")){
							m.put(o[2].toString(), cs.getTimestamp(i+1));
						}
					}
				}
			}
			
		return m;
	}

	@SuppressWarnings("unchecked")
	public List<WfFieldInfo> findWfFileldInfoByTableName(String tablename) {
		String sql = "select t.* from t_wf_core_fieldinfo t where t.i_tableid=(select t.id from t_wf_core_table t where t.vc_tablename='"+tablename+"')  or t.i_tableid is null";
		Query query = getEm().createNativeQuery(sql,WfFieldInfo.class);
		return  query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<FormTagMapColumn> getTableNameByFormId(String formId) {
		String sql=" select t.* from T_WF_CORE_FORM_MAP_COLUMN t where t.formid=:formid";
		Query query=getEm().createNativeQuery(sql,FormTagMapColumn.class);
		query.setParameter("formid", formId);
		return query.getResultList();
	}
	
	
	public void addProcedure(Procedure procedure) throws DataAccessException {
		super.getEm().persist(procedure);
	}

	public void deleteProcedure(Procedure procedure) throws DataAccessException {
		String hql = "delete from Procedure t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", procedure.getId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllProcedureList() throws DataAccessException {
		String sql = "select t.pname,t.id from t_wf_core_procedure t order by t.intime desc";
		List<Object[]> list=getEm().createNativeQuery(sql).getResultList();
		List<String> returnList=null;
		if (list!=null&&list.size()>0) {
			returnList=new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				returnList.add(list.get(i)[0].toString());
			}
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public Procedure getOneProcedureById(String id) throws DataAccessException {
		String hql="from Procedure t where t.id='"+id+"'";
		List<Procedure> list=this.getEm().createQuery(hql).getResultList();
		
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public List<Procedure> getProcedureListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Procedure> getProcedureListByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		String hql="from Procedure t where 1=1";
		if (column!=null&&!column.equals("")&&value!=null&&!value.equals("")) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		hql+=" order by t.intime desc";
		Query query=this.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public Integer getProcedureListCountByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		String hql="select count(*) from Procedure t where 1=1";
		if (column!=null&&!column.equals("")&&value!=null&&!value.equals("")) {
			hql+=" and t."+column+" like '%"+value+"%'";
		}
		hql+=" order by t.intime desc";
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}

	public void updateProcedure(Procedure procedure) throws DataAccessException {
		getEm().merge(procedure);
	}

	public boolean excuteSql(String sql) throws DataAccessException {
		try {
			getEm().createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Procedure> getProcedureByParam(Procedure procedure)
			throws DataAccessException {
		String hql="from Procedure t where 1=1 ";
		if (procedure!=null) {
			if (CommonUtil.stringNotNULL(procedure.getPname())) {
				hql+=" and t.pname='"+procedure.getPname()+"'";
			}
		}
		return getEm().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public String getColumnCnameByFormId(String formId, String cloumnname) {
		String sql="select t.columncname from T_WF_CORE_FORM_MAP_COLUMN t where t.formid='"+formId+"' and t.formtagname='"+cloumnname+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public List<FormTagMapColumn> getFormTagMapColumnByFormIdAndWay(
			String formId, String get_way) {
		String hql="from FormTagMapColumn t where t.formid=:formid and t.get_way=:get_way";
		Query query=getEm().createQuery(hql);
		query.setParameter("formid", formId);
		query.setParameter("get_way", get_way);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public boolean updateOnlyField(String onlyFields,String selectSql, String tableName,
			String pInstanceId) {
		List<Object[]> list=getEm().createNativeQuery(selectSql).getResultList();
		String[] fields = onlyFields.split(",");
		Object[] objs = null;
		String updateSql = "update "+tableName +" set ";
		if (list!=null&&list.size()>0) {
			// 取第一条
			objs = list.get(0);
			for (int i = 0; i < objs.length-1; i++) {
				Object object = objs[i] == null ?"":objs[i];
				if(i == 0){
					updateSql += fields[i] +"= '"+object+"'";
				}else{
					updateSql += ","+fields[i] +"= '"+object+"'";
				}
				
			}
			updateSql += " where INSTANCEID = '"+pInstanceId+"'";
			int ret = getEm().createNativeQuery(updateSql).executeUpdate();
			if(ret == 1){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateMergeField(String mergeFields,String selectSql, String tableName,
			String pInstanceId) {
		//查询出各个子流程的数据集合
		List<Object[]> datalist = getEm().createNativeQuery(selectSql).getResultList();
		List<Object[]> list = new ArrayList<Object[]>();
		String[] fields = mergeFields.split(",");
		String[] objs = new String[fields.length];
		String updateSql = "update "+tableName +" set ";
		//获取父流程的数据集合
		String sql = "select " + mergeFields +",'1' from " +tableName +" where INSTANCEID = '"+pInstanceId+"'";
		List<Object[]> dataList = getEm().createNativeQuery(sql).getResultList();
		//将子流程父流程的数据合并起来
		if(dataList!=null && dataList.size()>0){
			Object[] data = (Object[])dataList.get(0);
			datalist.add(data);
		}
		
		//去除重复的数据
		for (int i = 0; i < datalist.size(); i++) { // 列表型
			if (!list.contains(datalist.get(i))) {
				list.add(datalist.get(i));
			}
		}
		
		// 遍历字段
		if (list!=null && list.size()>0){
			for(int j = 0 ; j < list.size() ; j++){
				Object[] temp = list.get(j);
				for (int i = 0; i < temp.length-1; i++) {
					Object object = temp[i];
					String tempValue = "";
					try{
						tempValue = ClobToString.clob2String((SerializableClob) object);
					}catch (Exception e) {
						 tempValue = object == null ? "":object.toString();
					}
					if(tempValue != null){
					}
					if(j == 0){
						objs[i] = tempValue == null ? "":tempValue;
					}else{
						objs[i] += (tempValue == null ? ";": ";"+tempValue);
					}
				}
			}
		}
		for(int i=0; i<fields.length; i++){
			if(i == 0){
				updateSql += fields[i] +"= ?";
			}else{
				updateSql += ","+fields[i] +" = ?";
			}
		}
		
		updateSql += " where INSTANCEID = '"+pInstanceId+"'";
		
		Query query = getEm().createNativeQuery(updateSql);
		
		for (int i = 0; i < objs.length; i++) {
			String object = objs[i];
			List<String> noSameList = new ArrayList<String>();
			if(object!=null && !object.equals("") && object.indexOf("*")>-1){	//存在引号
				String[] idAndNames = object.split(";");
				String ids = "";
				String names = "";
				for(int j=0; idAndNames!=null && j<idAndNames.length;j++){
					String id = idAndNames[j].split("[*]")[1];
					String name = idAndNames[j].split("[*]")[0];
					ids += id+",";
					names += name +",";
				}
				if(ids.length()>0){
					ids = ids.substring(0, ids.length()-1);
				}
				if(names.length()>0){
					names = names.substring(0, names.length()-1);
				}
				
				String returnId = "";
				String returnName = "";
				
				if(ids!=null && ids.length()>0){
					String[] id = ids.split(",");
					String[] name = names.split(",");
					for(int j=0; j<id.length; j++){
						if (!noSameList.contains(id[j])) {
							noSameList.add(id[j]);
							returnId+= id[j]+",";
							returnName += name[j]+",";
						}
					}
				}
				if(returnId.length()>0){
					returnId = returnId.substring(0, returnId.length()-1);
				}
				if(returnName.length()>0){
					returnName = returnName.substring(0, returnName.length()-1);
				}
				object = returnName+"*"+returnId;
			}else{
				object ="";
			}
			query.setParameter(i+1, object);	//占位符
		}
		int ret = query.executeUpdate();
		if(ret == 1){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormTagMapColumn getFormTagMapColumnByFormId(String formId,
			String name) {
		String hql="from FormTagMapColumn t where t.formid=:formid and t.formtagname =:formtagname";
		Query query=getEm().createQuery(hql);
		query.setParameter("formid", formId);
		query.setParameter("formtagname", name);
		List<FormTagMapColumn> list =  query.getResultList();
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findOfficeTableVal(String fieldName, String tableName,
			String instanceId) {
		String sql = "select t."+fieldName +" from "+ tableName +" t where t.instanceid = '"+instanceId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormTagMapColumn> findFormTagMapColumnList(String formId,
			String field) {
		String sql = "select t.* from t_wf_core_form_map_column t where t.formId ='"+formId+"'" +
				" and t.assigncolumnnam = '"+field.toUpperCase()+"'";
		return getEm().createNativeQuery(sql, FormTagMapColumn.class).getResultList();
	}
	
	public void saveForm(String tabName, String value){
		if(!value.equals("")&&!value.equals("")){
			String[] cols = value.split(";");
			String[] columns = new String[cols.length];
			String[] tags = new String[cols.length];
			for(int i=0; i<cols.length; i++){
				String val =  cols[i];
				if(val!=null && !val.equals("")){
					String[] vals = val.split(":");
					columns[i] = vals[0];
					if(vals.length==2){
						tags[i] = vals[1];
					}else if(vals.length==1){
						tags[i] = "";
					}else if(vals.length>1){
						tags[i] = val.substring(val.indexOf(":")+1, val.length());
					}
				}
			}
			String  perch = "";
			for(String column: columns){
				perch +="?,";
			}
			if(perch.length()>0){
				perch = perch.substring(0, perch.length()-1);
			}
			String columnName = "";
			for(int i=0; i<columns.length; i++){
				columnName += columns[i]+",";
			}
			if(columnName.length()>0){
				columnName = columnName.substring(0, columnName.length()-1);
			}
			String sql = "insert into "+ tabName +"("+columnName+")"
					+" values("+perch+")"; 
			Query query = super.getEm().createNativeQuery(sql);
			for(int i =1; tags!=null && i<tags.length+1; i++){
				query.setParameter(i, tags[i-1]);	//占位符
			}
			query.executeUpdate();
		}
	}
	
	@Override
	public List<FormStyle> getFormStyle(Map<String, String> map){
		String sql = " select t.* from t_wf_core_formStyle t where 1=1 ";
		String id = map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			sql = sql + " and t.id='" + id + "' ";
		}
		List<FormStyle> list = getEm().createNativeQuery(sql, FormStyle.class).getResultList();
		return list;
	}
	
	@Override
	public void addFormStyle(FormStyle formStyle){
		if(formStyle != null){
			String id = formStyle.getId();
			if(CommonUtil.stringIsNULL(id)){
				formStyle.setId( UuidGenerator.generate36UUID());
				super.getEm().persist(formStyle);
			}else{
				super.getEm().merge(formStyle);
			}
		}
	}
	
	@Override
	public FormTagMapColumn getFormTagMapColumnById(String id) {
		String hql = "from FormTagMapColumn t where t.id= :id";
		List<FormTagMapColumn> q = this.getEm().createQuery(hql).setParameter("id", id).getResultList();
		if(q.size()>0){
			return q.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateFormTagMapColumn(FormTagMapColumn ftmc) {
		this.getEm().merge(this.getEm().merge(ftmc));
	}

	@Override
	public void updateForm(String id, Blob data) throws DataAccessException {
		String sql = "update t_wf_core_form t set t.data=:data where t.id=:id";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("data", blobToBytes(data));
		query.setParameter("id", id);
		query.executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ZwkjForm> selectByWfId(String id){
		String hql = "from ZwkjForm where workflowId = :workflowId";
		Query query = getEm().createQuery(hql);
		query.setParameter("workflowId", id);
		return query.getResultList();
	}
	
	/**
	* 把Blob类型转换为byte数组类型
	* @param blob
	* @return
	*/
	private byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}
}
