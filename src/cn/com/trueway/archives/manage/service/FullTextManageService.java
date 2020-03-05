package cn.com.trueway.archives.manage.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface FullTextManageService {

	List<EssTree> getTreeNodesList(Map<String, String> map);

	int getFullTextIndexCount(Map<String, String> map);

	List<Object[]> getFullTextIndexList(Map<String, String> map,Integer pageindex,Integer pagesize);
	
	
}
