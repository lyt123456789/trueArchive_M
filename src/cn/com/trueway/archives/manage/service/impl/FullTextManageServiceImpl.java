package cn.com.trueway.archives.manage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archiveModel.entity.EssTree;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.manage.dao.FullTextManageDao;
import cn.com.trueway.archives.manage.dao.RoleManageDao;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.RoleMenu;
import cn.com.trueway.archives.manage.pojo.StructsOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.archives.manage.service.FullTextManageService;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.using.dao.ArchiveUsingDao;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.workflow.core.pojo.Employee;

@SuppressWarnings("unused")
public class FullTextManageServiceImpl implements  FullTextManageService{
	private FullTextManageDao fullTextManageDao;

	public FullTextManageDao getFullTextManageDao() {
		return fullTextManageDao;
	}

	public void setFullTextManageDao(FullTextManageDao fullTextManageDao) {
		this.fullTextManageDao = fullTextManageDao;
	}

	@Override
	public List<EssTree> getTreeNodesList(Map<String, String> map) {
		return fullTextManageDao.getTreeNodesList(map);
	}

	@Override
	public int getFullTextIndexCount(Map<String, String> map) {
		return fullTextManageDao.getFullTextIndexCount(map);
	}

	@Override
	public List<Object[]> getFullTextIndexList(Map<String, String> map,Integer pageindex,Integer pagesize) {
		return fullTextManageDao.getFullTextIndexList(map,pageindex,pagesize);
	}
	

	
}
