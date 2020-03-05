package cn.com.trueway.workflow.set.service;

import java.util.List;

public interface UserNodesPermissionService {
	public Integer countUserNodesPermission(String name,String sxmc,String nodeName);
	public List<Object[]> getUserNodesPermission(String name ,String sxmc,String nodeName, int pageIndex, int pageSize);
}
