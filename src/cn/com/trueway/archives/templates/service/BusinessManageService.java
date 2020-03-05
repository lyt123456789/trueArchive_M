package cn.com.trueway.archives.templates.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssBusiness;

public interface BusinessManageService {
	
	/**
	 * 获取业务总条数
	 * @return
	 */
	public int getBusinessManageCount();
	
	/**
	 * 获取业务数据集合
	 * @param map
	 * @return
	 */
	public List<EssBusiness> getBusinessManageList(Map<String,Object> map);
	
	/**
	 * 获取符合条件的业务数据
	 * @param map
	 * @return
	 */
	public boolean checkRecordByMap(Map<String,Object> map);
	
	/**
	 * 新增或修改业务数据
	 * @param map
	 * @return
	 */
	public boolean saveBusinessRecord(Map<String,Object> map);
	
	/**
	 * 删除业务数据
	 * @param ids
	 * @return
	 */
	public boolean deleteBusinessRecord(String ids);

}

