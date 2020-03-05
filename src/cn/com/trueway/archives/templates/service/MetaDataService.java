package cn.com.trueway.archives.templates.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssPropValue;

public interface MetaDataService {
	
	/**
	 * 查询命名空间总数
	 * @param map
	 * @return
	 */
	public int getNameSpaceListCount(Map<String, Object> map);
	
	/**
	 * 查询命名空间内容
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getNameSpaceList(Map<String, Object> map);
	
	/**
	 * 删除一条命名空间数据
	 * @param id
	 * @return
	 */
	public boolean deleteOneNameSpaceById(String id);
	
	/**
	 * 保存命名空间信息
	 * @param map
	 * @return
	 */
	public boolean saveNameSpaceRecord(Map<String,Object> map); 
	
	/**
	 * 获取元数据总数
	 * @param map
	 * @return
	 */
	public int getMetaDataListCount(Map<String, Object> map);
	
	/**
	 * 获取元数据列表
	 * @param map
	 * @return
	 */
	public List<EssMetaData> getMetaDataList(Map<String, Object> map);
	
	/**
	 * 删除元数据
	 * @param id
	 * @return
	 */
	public boolean deleteMetaDataById(String ids);
	
	/**
	 * 检查相同命名空间中是否存在相同唯一标识符
	 * @param map
	 * @return
	 */
	public boolean checkMetaDataRecord(Map<String,Object> map);
	
	/**
	 * 保存元数据信息
	 * @param emda
	 * @return
	 */
	public boolean saveMetaDataRecord(EssMetaData emda);
	
	/**
	 * 获取元数据属性值集合
	 * @param map
	 * @return
	 */
	public List<EssPropValue> getMetaDataPropValue(Map<String,Object> map);
	
	/**
	 * 删除元数据属性
	 * @param ids
	 * @return
	 */
	public boolean deleteMeDaPropertyById(String ids);
	
	/**
	 * 保存元数据属性
	 * @param map
	 * @return
	 */
	public boolean saveMeDaPropertyRecord(Map<String,Object> map);
	
	/**
	 * 获取元数据属性信息
	 * @param map
	 * @return
	 */
	public List<EssPropValue> getMetaDataPropValueById(Map<String,Object> map);
}
