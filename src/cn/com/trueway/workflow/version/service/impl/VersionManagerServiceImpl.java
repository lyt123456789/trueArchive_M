package cn.com.trueway.workflow.version.service.impl;

import java.util.List;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.version.dao.VersionManagerDao;
import cn.com.trueway.workflow.version.pojo.VersionManager;
import cn.com.trueway.workflow.version.service.VersionManagerService;

public class VersionManagerServiceImpl extends BaseDao implements VersionManagerService{

	private VersionManagerDao vmDao;
	
	public VersionManagerDao getVmDao() {
		return vmDao;
	}
	public void setVmDao(VersionManagerDao vmDao) {
		this.vmDao = vmDao;
	}

	public int getVmCountForPage() {
		return vmDao.getVmCountForPage();
	}
	
	public List<VersionManager> getVmListForPage(String condition, Integer pageIndex, Integer pageSize) {
		return vmDao.getVmListForPage(condition, pageIndex, pageSize);
	}

	public void addvm(VersionManager vm) {
		vmDao.addvm(vm);
	}

	public VersionManager getVmById(String id) {
		return vmDao.getVmById(id);
	}
	
	public void updateVm(VersionManager vm) {
		vmDao.updateVm(vm);
	}
	
	public void delVm(String vmId) {
		vmDao.delVm(vmId);
	}
	
}
