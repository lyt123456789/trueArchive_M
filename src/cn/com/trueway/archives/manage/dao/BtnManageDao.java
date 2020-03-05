package cn.com.trueway.archives.manage.dao;

import java.util.List;
import java.util.Map;

public interface BtnManageDao {

	int getBtnManageListCount(Map<String, Object> map);

	List<Map<String, String>> getBtnManageList(Map<String, Object> map);

	void saveBtnManageRecord(Map<String, Object> map);

	void deleteOneBtnManageById(String id);

}
