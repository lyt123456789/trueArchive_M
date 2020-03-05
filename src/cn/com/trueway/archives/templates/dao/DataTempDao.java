package cn.com.trueway.archives.templates.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.pojo.MrzPojo;



public interface DataTempDao {

	int TreeCountByMap(Map<String, String> map);
	
	List<EssTree> getTreeByMap(Map<String, String> map, Integer pageSize,
			Integer pageIndex);

	void updateEssTree(EssTree et);

	void updateNativeSql(String sql);

	List<EssStructure> getStructureList(Map<String, String> map,
			Integer pageSize, Integer pageIndex);

	void updateStructure(EssStructure es);

	void updateTag(EssTag etag);

	List<Object[]> excuteNativeSql(String sql);

	int getEssTagCount(Map<String, String> map);

	List<EssTag> getEssTagList(Map<String, String> map,Integer pageSize, Integer pageIndex);
	
	/**
	 * 保存匹配元数据
	 * @param list
	 */
	public void saveTagsMetaData(Map<String,String> list);
	
	/**
	 * 查找相同元数据总数
	 * @param map
	 * @return
	 */
	public int checkIsJustOne(Map<String,Object> map);
	
	/**
	 * 获取字段选择中相同返回信息
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getZDXZDataList(Map<String,String> map);
	
	/**
	 * 删除字段选择数据
	 * @param tableFlag
	 * @param idStructure
	 */
	public void deleteZDXZDataOfTable(String tableFlag, String idStructure);

	/**
	 * 保存字段选择数据
	 * @param list
	 * @param tableFlag
	 * @return
	 */
	public void saveZDXZDataOfTable(Map<String,String> map, String tableFlag);
	
	/**
	 * 获取字段排序的设定信息
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getPXDataList(Map<String,String> map);
	
	/**
	 * 删除排序字段信息
	 * @param idStructure
	 */
	public void deletePxData(String idStructure);
	
	/**
	 * 保存排序字段保存数据
	 * @param map
	 * @param tableFlag
	 */
	public void savePxData(String sortString, String idstructure);
	
	/**
	 * 获取字段值选取数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getZDZDataList(Map<String,String> map);
	
	/**
	 * 插入字段值数据
	 * @param map
	 */
	public void insertAllZDZDataList(Map<String,String> map);
	
	/**
	 * 删除原有选中的字段值数据
	 * @param structureId
	 */
	public void deleteAllZDZData(String structureId);
	
	/**
	 * 删除字段代码规则表内容
	 * @param idstructure
	 * @param idTag
	 */
	public void deleteFileCodeProp(int idstructure, int idTag);
	
	/**
	 * 获取代码值数据
	 * @param structureId
	 * @param idTag
	 * @return
	 */
	public List<EssZDXZCommon> getDataOfDMZList(String structureId, String idTag);
	
	/**
	 * 获取字段代码-字段值的数据
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> getFieldCodeProp(Map<String,String> map);
	
	/**
	 * 检查字段代码-字段值属性值是否重复
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> checkDMZPropIsHave(Map<String,String> map);
	
	/**
	 * 字段代码-字段值保存数据
	 * @param map
	 * @return
	 */
	public void saveDMZPropData(Map<String,String> map);
	
	/**
	 * 删除字段代码-字段值
	 * @param idStr
	 * @return
	 */
	public void deleteDMZPropData(String id);
	
	/**
	 * 获取补零数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getBLDataList(Map<String,String> map);
	
	/**
	 * 删除补零数据
	 * @param structureId
	 */
	public void deleteDataOfBL(String structureId);
	
	/**
	 * 保存补零数据
	 */
	public void saveDataOfBL(Map<String,String> map);
	
	/**
	 * 获取组合字段数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getZHZDDataList(Map<String,String> map);
	
	/**
	 * 删除组合字段数据
	 * @param structureId
	 */
	public void deleteDataOfZHZD(String structureId);
	
	/**
	 * 保存组合字段数据
	 * @param map
	 */
	public void saveDataOfZHZD(Map<String,String> map);
	
	/**
	 * 获取组合字段的替代值
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> getZHZDPropDataList(Map<String,String> map);
	
	/**
	 * 检查组合字段中被替代值是否存在
	 * @param map
	 * @return
	 */
	public List<Object[]> checkZHZDPropIsHave(Map<String,String> map);
	
	/**
	 * 删除组合字段替代值字段
	 * @param id
	 */
	public void deleteZHZDPropData(String id);
	
	/**
	 * 保存组合字段替代值数据
	 * @param map
	 */
	public void saveZHZDPropData(Map<String,String> map);
	
	/**
	 * 获取鉴定数据
	 * @param structureId
	 * @return
	 */
	public Map<String,String> getDataOfJD(String structureId);
	
	/**
	 *获取鉴定数据中，保管期限数据信息
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> getCheckUpPropData(Map<String,String> map);
	
	/**
	 * 更新鉴定字段数据
	 * @param map
	 */
	public void updateDataOfJD(Map<String, String> map);
	
	/**
	 * 新增鉴定字段数据
	 * @param map
	 */
	public void insertDataOfJD(Map<String, String> map);

	void deleteZHZDPropData(String structureId, String PidTag, String idtag);
	
	/**
	 * 检查是否存在重复数据
	 * @param map
	 * @return
	 */
	public List<Object[]> checkDataRepeat(Map<String,String> map);
	
	/**
	 * 保存鉴定字段保管期限数据
	 * @param map
	 */
	public void insertDataOfJDKeyValue(Map<String,String> map);
	
	/**
	 * 更新鉴定保管期限数据
	 * @param map
	 * @return
	 */
	public void updateDataOfJDKeyValue(Map<String,String> map);
	
	/**
	 * 删除鉴定表格数据
	 * @param id
	 */
	public void deleteTableDataOfJD(String id);
	
	/**
	 * 获取当前选中树节点下所有子节点id
	 * @param map
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	List<EssTree> deleteTreeNodeList(Map<String, String> map,
			Integer pageSize, Integer pageIndex);

	List<MrzPojo> searchMRZ(Map<String, String> map, Integer pageSize,
			Integer pageIndex);

	boolean updateMrz(String updateSql);

	boolean deleteMrz(String deleteSql);

	int findByIdCount(String sql);

}
