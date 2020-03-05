package cn.com.trueway.workflow.set.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.set.pojo.Allow;

public interface AllowService {
	public void addAllow(Allow zwkjAllow)throws DataAccessException;
	public void updateAllow(Allow zwkjAllow)throws DataAccessException;
	public void deleteAllow(Allow zwkjAllow)throws DataAccessException;
	public List<Allow> getAllAllowList()throws DataAccessException;
	public List<Allow> getAllowListByHql(String hql,Integer pageindex,Integer pagesize)throws DataAccessException;
	public List<Allow> getAllowListByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Integer getAllowListCountByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Allow getOneAllowById(String id)throws DataAccessException;
	public List<Allow> getAllAllowListByParam(Allow allow,Integer pageindex,Integer pagesize,String lastStr)throws DataAccessException;
	public List<Allow> getAllowsList(Allow allow)throws DataAccessException;
}
