package cn.com.trueway.workflow.version.service;

import java.util.List;

import cn.com.trueway.workflow.version.pojo.VersionManager;

public interface VersionManagerService {

	public int getVmCountForPage();

	public List<VersionManager> getVmListForPage(String condition, Integer pageIndex, Integer pageSize);

	public void addvm(VersionManager vm);

	public VersionManager getVmById(String id);

	public void updateVm(VersionManager vm);

	public void delVm(String vmId);

}
