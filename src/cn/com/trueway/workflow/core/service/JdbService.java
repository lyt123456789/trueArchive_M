package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Jbd;

public interface JdbService {

	Jbd getJbdByinstanceId(String instanceId);

	void addJdb(Jbd jbd);

	void delete(String id);

	int getCountJdb(Map<String, String> map);

	List<Jbd> getJbds(Map<String, String> map, int pageIndex, int pageSize);

	Jbd getJdbById(String id);

	List<Map<String, String>> getJdbList(Map<String, String> map,
			int pageIndex, int pageSize);

	/**
	 * 根据实例id获取12345数据
	 * 
	 * @param instanceId
	 * @return
	 */
	Map<String, Object> getJBDMapByinstanceId(String instanceId);

	void updateInstanceId(String id, String instanceId, int type);

	List<Map<String, String>> getJdbList(Map<String, String> map);
	
}
