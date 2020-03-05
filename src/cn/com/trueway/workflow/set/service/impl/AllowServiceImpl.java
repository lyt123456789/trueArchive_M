package cn.com.trueway.workflow.set.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.set.dao.AllowDao;
import cn.com.trueway.workflow.set.pojo.Allow;
import cn.com.trueway.workflow.set.service.AllowService;

public class AllowServiceImpl implements AllowService{
	private AllowDao allowDao;
	
	
	public AllowDao getAllowDao() {
		return allowDao;
	}

	public void setAllowDao(AllowDao allowDao) {
		this.allowDao = allowDao;
	}

	public void addAllow(Allow allow) throws DataAccessException {
		allowDao.addAllow(allow);		
	}

	public void deleteAllow(Allow allow) throws DataAccessException {
		allowDao.deleteAllow(allow);		
	}

	public List<Allow> getAllAllowList() throws DataAccessException {
		return allowDao.getAllAllowList();
	}

	public List<Allow> getAllowListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return allowDao.getAllowListByHql(hql, pageindex, pagesize);
	}

	public List<Allow> getAllowListByParamForPage(String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return allowDao.getAllowListByParamForPage(column, value, pageindex, pagesize);
	}

	public Integer getAllowListCountByParamForPage(String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return allowDao.getAllowListCountByParamForPage(column, value, pageindex, pagesize);
	}

	public Allow getOneAllowById(String id) throws DataAccessException {
		return allowDao.getOneAllowById(id);
	}

	public void updateAllow(Allow allow) throws DataAccessException {
		allowDao.updateAllow(allow);		
	}

	public List<Allow> getAllAllowListByParam(Allow allow,Integer pageindex,Integer pagesize,String lastStr)
			throws DataAccessException {
		return allowDao.getAllAllowListByParam(allow,pageindex,pagesize,lastStr);
	}

	public List<Allow> getAllowsList(Allow allow) throws DataAccessException {
		return allowDao.getAllowsList(allow);
	}
	
}
