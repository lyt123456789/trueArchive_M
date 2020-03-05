package cn.com.trueway.workflow.set.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.set.pojo.Allow;

public interface AllowDao {
	public void addAllow(Allow allow)throws DataAccessException;
	public void updateAllow(Allow allow)throws DataAccessException;
	public void deleteAllow(Allow allow)throws DataAccessException;
	public List<Allow> getAllAllowList()throws DataAccessException;
	public List<Allow> getAllowListByHql(String hql,Integer pageindex,Integer pagesize)throws DataAccessException;
	public List<Allow> getAllowListByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Integer getAllowListCountByParamForPage(String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Allow getOneAllowById(String id)throws DataAccessException;
	public List<Allow> getAllAllowListByParam(Allow allow,Integer pageindex,Integer pagesize,String lastStr)throws DataAccessException;
	public List<Allow> getAllowsList(Allow allow)throws DataAccessException;
}
