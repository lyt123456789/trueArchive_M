package cn.com.trueway.workflow.webService.dao;

import java.util.List;

public interface ArchiveWebServiceDao {
	public List<Object[]> getArchivedFwList(String instanceId);
	
	public List<Object[]> getArchivedSwList(String instanceId);
	
	public List<Object[]> getProcessList(String instanceId);
}
