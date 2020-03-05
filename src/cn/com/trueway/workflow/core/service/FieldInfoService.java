package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfFieldInfo;

public interface FieldInfoService {

	Integer getFieldCountForPage(String column, String value, WfFieldInfo wfField);
	
	List<WfFieldInfo> getPublicField();
	
	boolean addField(List<WfFieldInfo> list);
	
	boolean delField(WfFieldInfo wfField);
	
	boolean updateField(WfFieldInfo wfField);
	
	WfFieldInfo getFieldById(String id);
	
	int isExistField(String vc_fieldname);
	
	List<WfFieldInfo> getAllFieldByTableId(String tableid);
	
	public List<WfFieldInfo> getFieldByParam(String table,String column);
	
	List<WfFieldInfo> getAllFieldByXbTableId(String tableid);
	
	List<WfFieldInfo> getFieldListForPage(String column, String value,
			WfFieldInfo wfField, Integer pageindex, Integer pagesize);
}
