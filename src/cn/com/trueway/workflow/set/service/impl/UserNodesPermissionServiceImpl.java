package cn.com.trueway.workflow.set.service.impl;

import java.util.List;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.set.dao.UserNodesPermissionDao;
import cn.com.trueway.workflow.set.service.UserNodesPermissionService;

public class UserNodesPermissionServiceImpl implements UserNodesPermissionService {
	private UserNodesPermissionDao userNodesPermissionDao;
	
	public UserNodesPermissionDao getUserNodesPermissionDao() {
		return userNodesPermissionDao;
	}

	public void setUserNodesPermissionDao(
			UserNodesPermissionDao userNodesPermissionDao) {
		this.userNodesPermissionDao = userNodesPermissionDao;
	}

	@Override
	public Integer countUserNodesPermission(String name,String sxmc,String nodeName) {
		return userNodesPermissionDao.countUserNodesPermission(name,sxmc,nodeName);
	}

	@Override
	public List<Object[]> getUserNodesPermission(String name, String sxmc ,String nodeName,int pageIndex,
			int pageSize) {
		List<Object[]> list = userNodesPermissionDao.getUserNodesPermission(name, sxmc,nodeName,pageIndex, pageSize);
		if(list != null && list.size() > 0){
			for(Object[] objs : list){
				if(objs != null && objs.length > 2){
					String userId = objs[2] == null ? "" : objs[2].toString();
					List<Object[]> nodeInfoList = userNodesPermissionDao.getNodeInfoById(userId);
					String nodeInfo = "";
					if(nodeInfoList != null && nodeInfoList.size() > 0){
						for(Object[] nodeObjs : nodeInfoList){
							if(nodeObjs != null){
								String nodes = nodeObjs[4] == null ? "" : nodeObjs[4].toString();
								if(CommonUtil.stringNotNULL(nodes)){
									nodeInfo = nodeInfo + nodes + "##";
								}
							}
						}
						if(CommonUtil.stringNotNULL(nodeInfo)){
							nodeInfo = nodeInfo.substring(0, nodeInfo.length() - 2);
						}
						objs[1] = nodeInfo;	
					}
				}
			}
		}
		return list;
	}
	

}
