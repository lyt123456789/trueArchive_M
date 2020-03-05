package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;

public interface TrueJsonDao {
	
	void saveTrueJson(TrueJson entity)  throws Exception;
	
	List<TrueJson> findTrueJsonListByInstanceId(String instanceId);
	
	TrueJson findNewestTrueJson(String instanceId);
	
	TrueJson findNewestTrueJsonByProcessId(String processId);
	
	void saveTrueJsonLog(TrueJsonLog log) throws Exception;
	
	int findTrueJsonLogCount(String contionSql);
	
	List<TrueJsonLog> findTrueJsonLogList(String contionSql, Integer pageindex, Integer pagesize);
	
	public List<TrueJsonLog> getTrueJsonByParams(Map<String, String> map);
	
	public TrueJson findTrueJsonForDofile(String instanceId);

	int findDelFileLogCount(String contionSql);
	
	List<TrueJsonLog> findDelFileLogList(String contionSql, Integer pageindex, Integer pagesize);

	TrueJson findNewsetTrueJsonByInstanceId(String instanceId);
	
}
