package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Ysqgk;

public interface YsqgkService {

	Ysqgk getYsqgkByinstanceId(String instanceId);

	void addYsqgk(Ysqgk ysqgk);

	
	int getCountYsqgk(Map<String, String> map);

	
	List<Ysqgk> getYsqgks(Map<String, String> map, int pageIndex, int pageSize);

	void delete(String id);

	Ysqgk getYsqgkById(String id);

	List<Map<String, String>> getYsqgkList(Map<String, String> map, int pageIndex,
			int pageSize);

	/**
	 * 根据父节点获取数据
	 * 
	 * @param fInstanceId
	 * @return
	 */
	Map<String, Object> getYsqgkMapByinstanceId(String fInstanceId);

	void updateInstanceId(String id, String instanceId, int type);

	List<Ysqgk> getYsqgks(Map<String, String> map);

}
