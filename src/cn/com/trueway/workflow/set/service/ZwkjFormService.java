package cn.com.trueway.workflow.set.service;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;

public interface ZwkjFormService {
	public ZwkjForm addForm(ZwkjForm zwkjForm)throws DataAccessException;
	public void updateForm(ZwkjForm zwkjForm)throws DataAccessException;
	public void deleteForm(ZwkjForm zwkjForm)throws DataAccessException;
	public List<ZwkjForm> getAllFormList()throws DataAccessException;
	public List<ZwkjForm> getFormListByHql(String hql,Integer pageindex,Integer pagesize)throws DataAccessException;
	
	public List<ZwkjForm> getFormListByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public List<String> getFormListCountByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	
	public ZwkjForm getOneFormById(String id)throws DataAccessException;
	
	public void addFormTagMapColumn(FormTagMapColumn formTagMapColumn)throws DataAccessException;
	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String formid)throws DataAccessException;
	public List<String> getTableNameByFormIdOfGroup(String formid)throws DataAccessException;
	public void deleteFormTagMapColumnByFormId(String formid)throws DataAccessException;
	public void saveForm(String tabName,String columnName,String tagName);
	public void saveFormByOther(String tabName,String columnName,String tagName,WfProcess wfProcess);
	public List<FormTagMapColumn> getFormTagMapColumnByFormId(String oldformId,String formId,String tableName,int todo,String isFirstChildWf);
	public void deleteForm(String tableName,String formId,String instanceId);
	public List<Map> findTableByFormId(String tabName, String formId,String instanceId);
	
	public Map<String, Object> excuteProcedure(Object[][] param,String prodecureName)throws Exception;
	public List<WfFieldInfo> findWfFileldInfoByTableName(String tablename);
	public List<FormTagMapColumn> getTableNameByFormId(String formId);
	
	public void addProcedure(Procedure procedure)throws DataAccessException;
	public void updateProcedure(Procedure procedure)throws DataAccessException;
	public void deleteProcedure(Procedure procedure)throws DataAccessException;
	public List<String> getAllProcedureList()throws DataAccessException;
	public List<Procedure> getProcedureListByHql(String hql,Integer pageindex,Integer pagesize)throws DataAccessException;
	public List<Procedure> getProcedureListByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Integer getProcedureListCountByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Procedure getOneProcedureById(String id)throws DataAccessException;
	public List<Procedure> getProcedureByParam(Procedure procedure)throws DataAccessException;
	
	public boolean excuteSql(String sql)throws DataAccessException;
	public String getColumnCnameByFormId(String formId, String cloumnname);
	// 根据 获取方式 获取 字段
	public List<FormTagMapColumn> getFormTagMapColumnByFormIdAndWay(String formId,String get_way);
	
	// 更新唯一字段
	public boolean updateOnlyField(String onlyFields,String selectSql,String tableName,String pInstanceId);
	
	// 更新合并字段
	public boolean updateMergeField(String mergeFields,String selectSql,String tableName,String pInstanceId);
	public List<Map> getSwByInstanceId(String wfInstanceUid);
	public FormTagMapColumn getFormTagMapColumnByFormId(String formId,String name);
	List<FormTagMapColumn> findFormTagMapColumnList(String formId, String field);
	/**
	 * 
	 * 描述：保存子流程步骤
	 * @param fInstanceId
	 * @param instanceId
	 * @param nodeId void
	 * 作者:蔡亚军
	 * 创建时间:2016-9-13 上午10:01:49
	 */
	public void saveChildFormValue(String fInstanceId,String fromNodeId,String oldFormId, String instanceId, String formId);

	public void saveForm(String tabName, String value);

	public List<FormStyle> getFormStyle(Map<String, String> map);
	
	public void addFormStyle(FormStyle formStyle);
	
	public FormTagMapColumn getFormTagMapColumnById(String id);
	void updateFormTagMapColumn(FormTagMapColumn ftmc);

	void updateForm(String id,Blob data)throws DataAccessException;
	
	ZwkjForm getFrom(String workflowId,String formId,String instanceId);
}
