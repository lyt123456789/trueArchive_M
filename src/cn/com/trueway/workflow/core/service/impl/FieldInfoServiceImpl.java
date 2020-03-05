package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.FieldInfoDao;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.service.FieldInfoService;

public class FieldInfoServiceImpl implements FieldInfoService {
	
	private FieldInfoDao fieldInfoDao;

	public FieldInfoDao getFieldInfoDao() {
		return fieldInfoDao;
	}

	public void setFieldInfoDao(FieldInfoDao fieldInfoDao) {
		this.fieldInfoDao = fieldInfoDao;
	}

	public Integer getFieldCountForPage(String column, String value, WfFieldInfo wfField){
		return fieldInfoDao.getFieldCountForPage(column, value, wfField);
	}
	
	public List<WfFieldInfo> getFieldListForPage(String column, String value,
			WfFieldInfo wfField, Integer pageindex, Integer pagesize) {
		return fieldInfoDao.getFieldListForPage(column, value, wfField, pageindex, pagesize);
	}
	
	public List<WfFieldInfo> getPublicField(){
		return fieldInfoDao.getPublicField();
	}
	
	public boolean addField(List<WfFieldInfo> list){
		for(WfFieldInfo wfField : list){
			fieldInfoDao.addField(wfField);
		}
		return true;
	}
	
	public boolean delField(WfFieldInfo wfField){
		fieldInfoDao.delField(wfField);
		return true;
	}
	
	public boolean updateField(WfFieldInfo wfField){
		fieldInfoDao.updateField(wfField);
		return true;
	}
	
	public WfFieldInfo getFieldById(String id){
		return fieldInfoDao.getFieldById(id);
	}
	
	public int isExistField(String vc_fieldname){
		int num = 0;
		List<WfFieldInfo> list = fieldInfoDao.getFieldByFieldName(vc_fieldname);
		if(list != null && list.size() > 0){
			num = 1;
		}
		return num;
	}
	
	public List<WfFieldInfo> getAllFieldByTableId(String tableid) {
		return fieldInfoDao.getAllFieldByTableId(tableid);
	}
	
	public List<WfFieldInfo> getAllFieldByXbTableId(String tableid) {
		return fieldInfoDao.getAllFieldByXbTableId(tableid);
	}

	public List<WfFieldInfo> getFieldByParam(String table, String column) {
		return fieldInfoDao.getFieldByParam(table, column);
	}

}
