package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfFieldInfo;

public interface FieldInfoDao {

	Integer getFieldCountForPage(String column, String value, WfFieldInfo wfField);
	
	List<WfFieldInfo> getFieldListForPage(String column, String value, WfFieldInfo wfField, Integer pageindex, Integer pagesize);
	
	List<WfFieldInfo> getPublicField();
	
	void addField(WfFieldInfo wfField);
	
	void delField(WfFieldInfo wfField);
	
	void updateField(WfFieldInfo wfField);
	
	WfFieldInfo getFieldById(String id);
	
	List<WfFieldInfo> getFieldByFieldName(String vc_fieldname);
	
	public List<WfFieldInfo> getAllFieldByTableId(String tableid);
	
	public List<WfFieldInfo> getFieldByParam(String table,String column);
	
	List<WfFieldInfo> getAllFieldByXbTableId(String tableid);
}
