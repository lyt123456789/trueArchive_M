package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Ysqgk;

public interface YsqgkDao {

	Ysqgk getYsqgkByinstanceId(String instanceId);

	void addYsqgk(Ysqgk ysqgk);

	int getCountYsqgk(Map<String, String> map);

	List<Ysqgk> getYsqgks(Map<String, String> map, int pageIndex, int pageSize);

	void delete(String id);

	Ysqgk getYsqgkById(String id);

	int getZtByInstanceid(String instanceid);

	/**
	 * 根据父节点获取数据
	 * 
	 * @param fInstanceId
	 * @return
	 */
	List<Map<String, Object>> getYsqgkMapByinstanceId(String fInstanceId);

	void updateInstanceId(String id, String instanceId, int type);

	List<Ysqgk> getYsqgks(Map<String, String> map);

}
