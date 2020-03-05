package cn.com.trueway.workflow.log.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.log.pojo.Log;

public interface LogDao {

	/**
	 * 
	 * 描述：查询log个数
	 * @param map
	 * @return int
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午2:41:13
	 */
	int countLog(Map<String, String> map);

	/**
	 * 
	 * 描述：查询log列表
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Log>
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午3:13:27
	 */
	List<Log> getLogList(Map<String, String> map, Integer pageIndex,
			Integer pageSize);

	/**
	 * 
	 * 描述：查找log
	 * @param logid
	 * @return Log
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午4:36:11
	 */
	Log findLogById(String logid);

	/**
	 * 
	 * 描述：获取log对应的标题
	 * @param instanceid
	 * @return List<String[]>
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午4:43:22
	 */
	String getTitle(String instanceid);

}
