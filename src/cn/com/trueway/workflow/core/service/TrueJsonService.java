package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;

public interface TrueJsonService {
	/**
	 * 
	 * 描述：保存意见内容
	 * @param entity void
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:09:23
	 */
	void saveTrueJson(TrueJson entity);
	
	/**
	 * 
	 * 描述：跟实例id获取整个办理过程的意见信息
	 * @param instanceId
	 * @return List<TrueJson>
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:09:35
	 */
	List<TrueJson> findTrueJsonListByInstanceId(String instanceId);
	
	/**
	 * 
	 * 描述：获取某流程最新的流程意见
	 * @param instanceId
	 * @return TrueJson
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:09:58
	 */
	TrueJson findNewestTrueJson(String instanceId);
	
	/**
	 * 
	 * 描述：获取步骤中签批的最新意见
	 * @param processId
	 * @return TrueJson
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:10:20
	 */
	TrueJson findNewestTrueJsonByProcessId(String processId);
	
	/**
	 * 
	 * 描述：保存相关日志的意见信息
	 * @param log void
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:11:16
	 */
	void saveTrueJsonLog(TrueJsonLog log);
	
	
	
	void saveTrueJsonLog(TrueJson log);
	
	/**
	 * 
	 * 描述：获取意见的日志数字
	 * @param contionSql
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:12:14
	 */
	int findTrueJsonLogCount(String contionSql);
	
	/**
	 * 
	 * 描述：获取日志信息列表
	 * @param contionSql
	 * @param pageindex
	 * @param pagesize
	 * @return List<TrueJsonLog>
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午2:13:19
	 */
	List<TrueJsonLog> findTrueJsonLogList(String contionSql, Integer pageindex, Integer pagesize);
	
	List<TrueJsonLog> getTrueJsonByParams(Map<String, String> map);
	
	int findDelFileLogCount(String contionSql);
	
	List<TrueJsonLog> findDelFileLogList(String contionSql, Integer pageindex, Integer pagesize);

	TrueJson findNewestTrueJsonByInstanceId(String instanceId);
	
}
