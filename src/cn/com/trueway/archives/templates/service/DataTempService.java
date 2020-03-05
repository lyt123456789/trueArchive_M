package cn.com.trueway.archives.templates.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.pojo.MrzPojo;



public interface DataTempService {

	int TreeCountByMap(Map<String, String> map);
	
	List<EssTree> getTreeByMap(Map<String, String> map,Integer pageSize, Integer pageIndex);

	void updateEssTree(EssTree et);

	void updateNativeSql(String string);

	List<EssStructure> getStructureList(Map<String, String> map,Integer pageSize, Integer pageIndex);

	void updateStructure(EssStructure es);

	void updateTag(EssTag etag);

	List<Object[]> excuteNativeSql(String sql);

	int getEssTagCount(Map<String, String> map);

	List<EssTag> getEssTagList(Map<String, String> map, Integer pageSize,
			Integer pageIndex);
	
	/**
	 * 保存匹配元数据
	 * @param list
	 * @return
	 */
	public boolean saveTagsMetaData(List<Map<String,String>> list);
	
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
	 * 保存字段选择数据
	 * @param list
	 * @param tableFlag
	 * @param idstructure
	 * @return
	 */
	public boolean saveZDXZDataOfTable(List<Map<String,String>> list, String tableFlag, String idstructure);
	
	/**
	 * 获取字段排序的设定信息
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getPXDataList(Map<String,String> map);
	
	/**
	 * 保存排序字段的信息
	 * @param sortString
	 * @param idstructure
	 * @return
	 */
	public boolean savePxData(String sortString, String idstructure);
	
	/**
	 * 获取字段值选取数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getZDZDataList(Map<String,String> map);
	
	/**
	 * 保存字段值选中数据
	 * @param list
	 * @return
	 */
	public boolean saveOfZDZData(List<Map<String,String>> list, String structureId);
	
	/**
	 * 保存代码值选中的数据
	 * @param list
	 * @param structureId
	 * @return
	 */
	public boolean saveofDMZData(List<Map<String,String>> list, String tableFlag, String structureId);
	
	/**
	 * 获取代码值的数据
	 * @param structureId
	 * @param idTag
	 * @return
	 */
	public boolean getDataOfDMZList(String structureId, String idTag);
	
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
	public boolean checkDMZPropIsHave(Map<String,String> map);
	
	/**
	 * 字段代码-字段值保存数据
	 * @param map
	 * @return
	 */
	public boolean saveDMZPropData(Map<String,String> map);
	
	/**
	 * 删除字段代码-字段值
	 * @param idStr
	 * @return
	 */
	public boolean deleteDMZPropData(String idStr);
	
	/**
	 * 获取补零数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getBLDataList(Map<String,String> map);
	
	/**
	 * 保存补零字段
	 * @param list
	 * @param structureId
	 * @return
	 */
	public boolean saveDataOfBL(List<Map<String, String>> list, String structureId);
	
	/**
	 * 获取组合字段数据
	 * @param map
	 * @return
	 */
	public List<EssZDXZCommon> getZHZDDataList(Map<String,String> map);
	
	/**
	 * 保存组合字段数据
	 * @param list
	 * @param structureId
	 * @return
	 */
	public boolean saveDataOfZHZD(List<Map<String, String>> list, String structureId);
	
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
	public boolean checkZHZDPropIsHave(Map<String,String> map);
	
	/**
	 * 删除组合字段替代值数据
	 * @param idStr
	 * @return
	 */
	public boolean deleteZHZDPropData(String idStr);
	
	/**
	 * 保存组合字段替代值数据
	 * @param map
	 * @return
	 */
	public boolean saveZHZDPropData(Map<String,String> map);
	
	/**
	 * 获取鉴定数据
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
	 * 保存鉴定数据
	 * @return
	 */
	public boolean saveDataOfJD(Map<String,String> map);
	
	/**
	 * 检查是否存在重复数据
	 * @param map
	 * @return
	 */
	public boolean checkDataRepeat(Map<String,String> map);
	
	/**
	 * 新增鉴定保管期限数据
	 * @param map
	 * @return
	 */
	public boolean insertDataOfJDKeyValue(Map<String,String> map);
	
	/**
	 * 更新鉴定保管期限数据
	 * @param map
	 * @return
	 */
	public boolean updateDataOfJDKeyValue(Map<String,String> map);
	
	/**
	 * 删除鉴定表格数据
	 * @param ids
	 * @return
	 */
	public boolean deleteTableDataOfJD(String ids);
	
	/**
	 * 获取当前选中树节点下所有子节点id
	 * @param map
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	List<EssTree> deleteTreeNodeList(Map<String, String> map,Integer pageSize, Integer pageIndex);

	List<MrzPojo> searchMRZ(Map<String, String> map,Integer pageSize, Integer pageIndex);

	boolean updateMrz(String updateSql);

	boolean deleteMrz(String deleteSql);

	int findByIdCount(String sql);
}
