package cn.com.trueway.workflow.set.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.set.pojo.ServerPlugin;

public interface ServerPluginService {
	public void addServerPlugin(ServerPlugin serverPlugin)throws DataAccessException;
	public void updateServerPlugin(ServerPlugin serverPlugin)throws DataAccessException;
	public void deleteServerPlugin(ServerPlugin serverPlugin)throws DataAccessException;
	public List<ServerPlugin> getAllServerPluginList()throws DataAccessException;
	public List<ServerPlugin> getServerPluginListByHql(String hql,Integer pageindex,Integer pagesize)throws DataAccessException;
	public List<ServerPlugin> getServerPluginListByParamForPage(ServerPlugin serverPlugin,String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public Integer getServerPluginListCountByParamForPage(ServerPlugin serverPlugin,String column,String value,Integer pageindex,Integer pagesize)throws DataAccessException;
	public ServerPlugin getOneServerPluginById(String id)throws DataAccessException;
	public List<ServerPlugin> getAllServerPluginListByParam(ServerPlugin ServerPlugin,Integer pageindex,Integer pagesize,String lastStr)throws DataAccessException;
	public List<ServerPlugin> getServerPluginsList(ServerPlugin ServerPlugin)throws DataAccessException;
}