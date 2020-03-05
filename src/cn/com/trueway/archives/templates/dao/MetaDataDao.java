package cn.com.trueway.archives.templates.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssProp;
import cn.com.trueway.archives.templates.pojo.EssPropValue;

public interface MetaDataDao {
	/**
	 * 
	 * @param map
	 * @return
	 */
	public int getNameSpaceListCount(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getNameSpaceList(Map<String, Object> map);
	
	/**
	 * 删除一条命名空间数据
	 * @param id
	 * @return
	 */
	public void deleteOneNameSpaceById(String id);
	
	/**
	 * 保存命名空间信息
	 * @param map
	 * @return
	 */
	public void saveNameSpaceRecord(Map<String,Object> map); 
	
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
	public void deleteMetaDataById(String id);
	
	/**
	 * 检查相同命名空间中是否存在相同唯一标识符
	 * @param map
	 * @return
	 */
	public List<EssMetaData> checkMetaDataRecord(Map<String,Object> map);
	
	/**
	 * 保存元数据信息
	 * @param emda
	 */
	public void saveMetaDataRecord(EssMetaData emda);
	
	/**
	 * 获取元数据属性的连接信息
	 * @param map
	 * @return
	 */
	public EssProp getMetaDataPorp(Map<String,Object> map);
	
	/**
	 * 获取元数据属性信息
	 * @param map
	 * @return
	 */
	public List<EssPropValue> getMetaDataPropValue(Map<String,Object> map);
	
	/**
	 * 删除元数据
	 * @param id
	 * @return
	 */
	public void deleteMeDaPropertyById(String id);
	
	/**
	 * 保存元数据属性
	 * @param epve
	 */
	public void saveMeDaPropertyRecord(EssPropValue epve);
	
	/**
	 * 新增元数据属性连接数据
	 * @return
	 */
	public String saveMeDaPropRela(EssProp ep);
}
