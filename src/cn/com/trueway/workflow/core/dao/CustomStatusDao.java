package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfCustomStatus;

public interface CustomStatusDao {

	public void addCustomStatus(WfCustomStatus customStatus) ;

	public void delCustomStatus(WfCustomStatus customStatus);

	public Integer getCustomStatusCountForPage(String column, String value,
			WfCustomStatus customStatus) ;

	public List<WfCustomStatus> getCustomStatusListForPage(String column, String value,
			WfCustomStatus customStatus, Integer pageindex, Integer pagesize) ;
	
	public WfCustomStatus getCustomStatusById(String id) ;
	
	public List<WfCustomStatus> getPublicCustomStatus() ;
	
	public List<WfCustomStatus> getCustomStatusByLcid(String lcid) ;
	
}
