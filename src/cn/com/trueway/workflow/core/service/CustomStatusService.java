package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfCustomStatus;

public interface CustomStatusService {

	public boolean addCustomStatus(WfCustomStatus customStatus) ;

	public boolean delCustomStatus(WfCustomStatus customStatus) ;

	public WfCustomStatus getCustomStatusById(String id) ;
	

	public Integer getCustomStatusCountForPage(String column, String value,
			WfCustomStatus customStatus);
	
	public List<WfCustomStatus> getCustomStatusListForPage(String column, String value,
			WfCustomStatus customStatus, Integer pageindex, Integer pagesize);

	public List<WfCustomStatus> getPublicCustomStatus() ;

	public List<WfCustomStatus> getCustomStatusByLcid(String lcid) ;
}
