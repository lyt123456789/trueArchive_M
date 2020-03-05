package cn.com.trueway.archives.manage.service;

import java.util.List;
import java.util.Map;

public interface BtnManageService {

	/**
	 * 查询按钮总数
	 * @param map
	 * @return
	 */
	public int getBtnManageListCount(Map<String, Object> map);
	
	/**
	 * 查询按钮内容
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getBtnManageList(Map<String, Object> map);
	/**
	 * 保存新增按钮
	 * @param map
	 * @return
	 */
	public boolean saveBtnManageRecord(Map<String, Object> map);

	public boolean deleteOneBtnManageById(String id);
}
