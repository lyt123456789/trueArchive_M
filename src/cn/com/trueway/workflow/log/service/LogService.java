package cn.com.trueway.workflow.log.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.log.pojo.Log;

public interface LogService {

	/**
	 * 
	 * 描述：查询log个数
	 * @param map
	 * @return int
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午2:40:26
	 */
	int countLog(Map<String, String> map);

	/**
	 * 
	 * 描述：获取log列表
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Log>
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午3:10:19
	 */
	List<Log> getLogList(Map<String, String> map, Integer pageIndex, Integer pageSize);

	/**
	 * 
	 * 描述：查找log
	 * @param logid
	 * @return Log
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午4:34:02
	 */
	Log findLogById(String logid);

	/**
	 * 
	 * 描述：获取log的title
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午5:38:04
	 */
	String getTitle(String instanceid);

}
