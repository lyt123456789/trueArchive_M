package cn.com.trueway.workflow.set.service.impl;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ZwkjFormService;

public class ZwkjFormServiceImpl implements ZwkjFormService{
	
	private ZwkjFormDao 	zwkjFormDao;

	private TableInfoDao	tableInfoDao;
	
	public ZwkjFormDao getZwkjFormDao() {
		return zwkjFormDao;
	}

	public void setZwkjFormDao(ZwkjFormDao zwkjFormDao) {
		this.zwkjFormDao = zwkjFormDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public ZwkjForm addForm(ZwkjForm zwkjForm) throws DataAccessException {
		return zwkjFormDao.addForm(zwkjForm);
	}

	public void deleteForm(ZwkjForm zwkjForm) throws DataAccessException {
		zwkjFormDao.deleteForm(zwkjForm);
	}

	public List<ZwkjForm> getAllFormList() throws DataAccessException {
		return zwkjFormDao.getAllFormList();
	}

	public List<ZwkjForm> getFormListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return zwkjFormDao.getFormListByHql(hql, pageindex, pagesize);
	}

	public List<ZwkjForm> getFormListByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		return zwkjFormDao.getFormListByParamForPage(column, value, pageindex, pagesize);
	}

	public List<String> getFormListCountByParamForPage(String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return zwkjFormDao.getFormListCountByParamForPage(column, value, pageindex, pagesize);
	}

	public void updateForm(ZwkjForm zwkjForm) throws DataAccessException {
		zwkjFormDao.updateForm(zwkjForm);
	}

	public ZwkjForm getOneFormById(String id) throws DataAccessException {
		return zwkjFormDao.getOneFormById(id);
	}

	public void addFormTagMapColumn(FormTagMapColumn formTagMapColumn)
			throws DataAccessException {
		zwkjFormDao.addFormTagMapColumn(formTagMapColumn);
	}

	public void deleteFormTagMapColumnByFormId(String formid)
			throws DataAccessException {
		zwkjFormDao.deleteFormTagMapColumnByFormId(formid);
	}

	
	public List<String> getTableNameByFormIdOfGroup(String formid)
			throws DataAccessException {
		return zwkjFormDao.getTableNameByFormIdOfGroup(formid);
	}

	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String formid)
			throws DataAccessException {
		return zwkjFormDao.getFormTagMapColumnByFormId(formid);
	}
	
	public void saveForm(String tabName,String columnName,String tagName) {
		zwkjFormDao.saveForm(tabName,columnName,tagName);
	}
	public void saveFormByOther(String tabName,String columnName,String tagName,WfProcess wfProcess) {
		zwkjFormDao.saveFormByOther(tabName,columnName,tagName,wfProcess);
	}

	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String oldformId,String formId, String tableName, int todo,String isFirstChildWf) {
		return zwkjFormDao.getFormTagMapColumnByFormId(oldformId,formId,tableName,todo,isFirstChildWf);
	}

	public void deleteForm(String tableName,String formId,String instanceId) {
		zwkjFormDao.deleteForm(tableName,formId, instanceId);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> findTableByFormId(String tabName, String formId,String instanceId) {
		return zwkjFormDao.findTableByFormId(tabName,formId,instanceId);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> getSwByInstanceId(String instanceId) {
		return zwkjFormDao.getSwByInstanceId(instanceId);
	}

	public Map<String, Object> excuteProcedure(Object[][] param,String prodecureName)
			throws Exception {
		return zwkjFormDao.excuteProcedure(param,prodecureName);
	}

	public List<WfFieldInfo> findWfFileldInfoByTableName(String tablename) {
		List<WfFieldInfo>  fieldList = zwkjFormDao.findWfFileldInfoByTableName(tablename);
		return fieldList;
	}

	public List<FormTagMapColumn> getTableNameByFormId(String formId) {
		return zwkjFormDao.getTableNameByFormId(formId);
	}
	
	public void addProcedure(Procedure procedure) throws DataAccessException {
		zwkjFormDao.addProcedure(procedure);
	}

	public void deleteProcedure(Procedure procedure) throws DataAccessException {
		zwkjFormDao.deleteProcedure(procedure);
	}

	public List<String> getAllProcedureList() throws DataAccessException {
		return zwkjFormDao.getAllProcedureList();
	}

	public Procedure getOneProcedureById(String id) throws DataAccessException {
		return zwkjFormDao.getOneProcedureById(id);
	}

	public List<Procedure> getProcedureListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return zwkjFormDao.getProcedureListByHql(hql, pageindex, pagesize);
	}

	public List<Procedure> getProcedureListByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		return zwkjFormDao.getProcedureListByParamForPage(column, value, pageindex, pagesize);
	}

	public Integer getProcedureListCountByParamForPage(String column,
			String value, Integer pageindex, Integer pagesize)
			throws DataAccessException {
		return zwkjFormDao.getProcedureListCountByParamForPage(column, value, pageindex, pagesize);
	}

	public void updateProcedure(Procedure procedure) throws DataAccessException {
		zwkjFormDao.updateProcedure(procedure);
	}

	public boolean excuteSql(String sql) throws DataAccessException {
		return zwkjFormDao.excuteSql(sql);
	}

	public List<Procedure> getProcedureByParam(Procedure procedure)
			throws DataAccessException {
		return zwkjFormDao.getProcedureByParam(procedure);
	}

	public String getColumnCnameByFormId(String formId, String cloumnname) {
		return zwkjFormDao.getColumnCnameByFormId(formId,cloumnname);
	}

	public List<FormTagMapColumn>  getFormTagMapColumnByFormIdAndWay(String formId, String get_way) {
		return zwkjFormDao.getFormTagMapColumnByFormIdAndWay(formId,get_way);
		
	}

	@Override
	public boolean updateOnlyField(String onlyFields,String selectSql, String tableName,
			String pInstanceId) {
		return zwkjFormDao.updateOnlyField(onlyFields,selectSql,tableName,pInstanceId);
	}

	@Override
	public boolean updateMergeField(String mergeFields,String selectSql, String tableName,
			String pInstanceId) {
		return zwkjFormDao.updateMergeField(mergeFields,selectSql,tableName,pInstanceId);
	}

	@Override
	public FormTagMapColumn getFormTagMapColumnByFormId(String formId,
			String name) {
		return zwkjFormDao.getFormTagMapColumnByFormId(formId,name);
	}
	
	@Override
	public List<FormTagMapColumn> findFormTagMapColumnList(String formId,
			String field) {
		return zwkjFormDao.findFormTagMapColumnList(formId, field);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void saveChildFormValue(String fInstanceId, String fromNodeId, String oldFormId, String instanceId,
			String formId) {
		ZwkjForm form = zwkjFormDao.getOneFormById(oldFormId);
		String tableName = form.getInsert_table();
		List<Map> mapList = zwkjFormDao.findTableByFormId(tableName, oldFormId, fInstanceId);
		Map map = null;
		String colunm = "";
		String val = "";
		for(int i=0; i<mapList.size(); i++){
			map = mapList.get(i);
			for(Object key: map.keySet()){
				if(map.get(key)!=null && !key.toString().equalsIgnoreCase("instanceId") && !key.toString().equalsIgnoreCase("formId")){
					colunm  += key+",";
					val += "'"+map.get(key)+"',";
				}
			}
			colunm += "instanceId,formId";
			val += "'"+instanceId+"','"+formId+"'";
		}
		zwkjFormDao.saveForm(tableName, colunm, val);
	}
	
	public void saveForm(String tabName, String value){
		zwkjFormDao.saveForm(tabName, value);
	}

	@Override
	public List<FormStyle> getFormStyle(Map<String, String> map){
		return zwkjFormDao.getFormStyle(map);
	}
	
	@Override
	public void addFormStyle(FormStyle formStyle){
		zwkjFormDao.addFormStyle(formStyle);
	}
	
	@Override
	public FormTagMapColumn getFormTagMapColumnById(String id) {
		return zwkjFormDao.getFormTagMapColumnById(id);
	}

	@Override
	public void updateFormTagMapColumn(FormTagMapColumn ftmc) {
		zwkjFormDao.updateFormTagMapColumn(ftmc);
	}

	@Override
	public void updateForm(String id, Blob data) throws DataAccessException {
		zwkjFormDao.updateForm(id, data);
	}

	@Override
	public ZwkjForm getFrom(String workflowId, String formId, String instanceId) {
		ZwkjForm zwkjForm = null;
		List<ZwkjForm> list = zwkjFormDao.selectByWfId(workflowId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(list != null && list.size()>0){
			if(list.size()>1){
				List<WfProcess> wfps = tableInfoDao.findWfProcessList(instanceId, 1, null);
				if(wfps != null && wfps.size()>0){
					Date date = wfps.get(0).getApplyTime();
					try {
						date = sdf.parse(sdf.format(date));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					for (ZwkjForm z : list) {
						Date beginDate = z.getBeginDate();
						Date endDate = z.getEndDate();
						if(beginDate == null && endDate == null){//
							continue;
						}
						if(beginDate == null && endDate != null && endDate.compareTo(date)>=0){
							zwkjForm = z;
							break;
						}
						if(beginDate != null && endDate == null && date.compareTo(beginDate)>=0){
							zwkjForm = z;
							break;
						}
						if(beginDate != null && endDate != null && date.compareTo(beginDate)>=0 && endDate.compareTo(date)>=0){
							zwkjForm = z;
							break;
						}
					}
					if(zwkjForm == null){
						zwkjForm = zwkjFormDao.getOneFormById(formId);
					}
				}else{
					zwkjForm = zwkjFormDao.getOneFormById(formId);
				}
			}else{
				ZwkjForm zwkjForm1 = list.get(0);
				if(zwkjForm1.getId().equals(formId)){
					zwkjForm = zwkjForm1; 
				}else{
					zwkjForm = zwkjFormDao.getOneFormById(formId);
				}
			}
		}else{
			zwkjForm = zwkjFormDao.getOneFormById(formId);
		}
		return zwkjForm;
	}

}
