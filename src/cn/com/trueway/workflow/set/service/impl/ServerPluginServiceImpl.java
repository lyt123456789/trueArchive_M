package cn.com.trueway.workflow.set.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.set.dao.ServerPluginDao;
import cn.com.trueway.workflow.set.pojo.ServerPlugin;
import cn.com.trueway.workflow.set.service.ServerPluginService;

public class ServerPluginServiceImpl implements ServerPluginService{
	private ServerPluginDao serverPluginDao;

	public ServerPluginDao getServerPluginDao() {
		return serverPluginDao;
	}

	public void setServerPluginDao(ServerPluginDao serverPluginDao) {
		this.serverPluginDao = serverPluginDao;
	}

	public void addServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		serverPluginDao.addServerPlugin(serverPlugin);		
	}

	public void deleteServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		serverPluginDao.deleteServerPlugin(serverPlugin);		
	}

	public List<ServerPlugin> getAllServerPluginList() throws DataAccessException {
		return serverPluginDao.getAllServerPluginList();
	}

	public List<ServerPlugin> getServerPluginListByHql(String hql, Integer pageindex,
			Integer pagesize) throws DataAccessException {
		return serverPluginDao.getServerPluginListByHql(hql, pageindex, pagesize);
	}

	public List<ServerPlugin> getServerPluginListByParamForPage(ServerPlugin serverPlugin,String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return serverPluginDao.getServerPluginListByParamForPage(serverPlugin,column, value, pageindex, pagesize);
	}

	public Integer getServerPluginListCountByParamForPage(ServerPlugin serverPlugin,String column, String value,
			Integer pageindex, Integer pagesize) throws DataAccessException {
		return serverPluginDao.getServerPluginListCountByParamForPage(serverPlugin,column, value, pageindex, pagesize);
	}

	public ServerPlugin getOneServerPluginById(String id) throws DataAccessException {
		return serverPluginDao.getOneServerPluginById(id);
	}

	public void updateServerPlugin(ServerPlugin serverPlugin) throws DataAccessException {
		serverPluginDao.updateServerPlugin(serverPlugin);		
	}

	public List<ServerPlugin> getAllServerPluginListByParam(ServerPlugin serverPlugin,Integer pageindex,Integer pagesize,String lastStr)
			throws DataAccessException {
		return serverPluginDao.getAllServerPluginListByParam(serverPlugin,pageindex,pagesize,lastStr);
	}

	public List<ServerPlugin> getServerPluginsList(ServerPlugin serverPlugin) throws DataAccessException {
		return serverPluginDao.getServerPluginsList(serverPlugin);
	}

}
