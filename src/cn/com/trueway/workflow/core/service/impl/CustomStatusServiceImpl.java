package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.CustomStatusDao;
import cn.com.trueway.workflow.core.pojo.WfCustomStatus;
import cn.com.trueway.workflow.core.service.CustomStatusService;

public class CustomStatusServiceImpl implements CustomStatusService{

	private CustomStatusDao customStatusDao;
	

	public CustomStatusDao getCustomStatusDao() {
		return customStatusDao;
	}

	public void setCustomStatusDao(CustomStatusDao customStatusDao) {
		this.customStatusDao = customStatusDao;
	}

	public boolean addCustomStatus(WfCustomStatus customStatus) {
		customStatusDao.addCustomStatus(customStatus);
		return false;
	}

	public boolean delCustomStatus(WfCustomStatus customStatus) {
		customStatusDao.delCustomStatus(customStatus);
		return false;
	}

	public WfCustomStatus getCustomStatusById(String id) {
		return customStatusDao.getCustomStatusById(id);
	}
	

	public Integer getCustomStatusCountForPage(String column, String value,
			WfCustomStatus customStatus){
		return customStatusDao.getCustomStatusCountForPage(column, value, customStatus);
	}
	
	public List<WfCustomStatus> getCustomStatusListForPage(String column, String value,
			WfCustomStatus customStatus, Integer pageindex, Integer pagesize){
		return customStatusDao.getCustomStatusListForPage(column, value, customStatus, pageindex, pagesize);
	} 

	public List<WfCustomStatus> getPublicCustomStatus() {
		return customStatusDao.getPublicCustomStatus();
	}

	public List<WfCustomStatus> getCustomStatusByLcid(String lcid) {
		return customStatusDao.getCustomStatusByLcid(lcid);
	}
	
}
