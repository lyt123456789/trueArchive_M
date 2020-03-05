package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Jbd;

public interface JdbDao {

	Jbd getJbdByinstanceId(String instanceId);

	void addJdb(Jbd jbd);

	void delete(String id);

	int getCountJdb(Map<String, String> map);

	List<Jbd> getJbds(Map<String, String> map, int pageIndex, int pageSize);

	Jbd getJdbById(String id);

	List<Map<String, Object>> getJBDMapByinstanceId(String instanceId);

	void updateInstanceId(String id, String instanceId, int type);

	List<Jbd> getJbds(Map<String, String> map);

}
