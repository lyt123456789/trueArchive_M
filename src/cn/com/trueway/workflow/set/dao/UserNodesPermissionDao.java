package cn.com.trueway.workflow.set.dao;

import java.util.List;

public interface UserNodesPermissionDao {
	public Integer countUserNodesPermission(String name,String sxmc,String nodeName);
	public List<Object[]> getUserNodesPermission(String name ,String sxmc,String nodeName,int pageIndex, int pageSize);
	public List<Object[]> getNodeInfoById(String userId);
}
