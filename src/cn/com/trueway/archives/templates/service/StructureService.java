package cn.com.trueway.archives.templates.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssModelTags;
import cn.com.trueway.archives.templates.pojo.EssStructureModel;

public interface StructureService {
	
	/**
	 * 获取结构模板总数
	 * @param map
	 * @return
	 */
	public int getStructureTempCount(Map<String,Object> map);
	
	/**
	 * 获取结构模板数据集合
	 * @param map
	 * @return
	 */
	public List<EssStructureModel> getStructureTempList(Map<String,Object> map);
	
	/**
	 * 删除结构模板信息
	 * @param map
	 * @return
	 */
	public boolean deleteStructureByIds(String ids);
	
	/**
	 * 保存结构模板信息
	 * @param estm
	 * @return
	 */
	public boolean saveStructureTemp(EssStructureModel estm);
	
	/**
	 * 获取著录项总数
	 * @param map
	 * @return
	 */
	public int getEssModelTagsCount(Map<String,Object> map);
	
	/**
	 * 获取著录项集合
	 * @param map
	 * @return
	 */
	public List<EssModelTags> getEssModelTagsList(Map<String,Object> map);

	public void updateModelTags(EssModelTags emt);
	
	/**
	 * 保存结构模板与元数据匹配信息
	 * @param list
	 * @return
	 */
	public boolean saveTagsMetaData(List<Map<String,String>> list);
	
	/**
	 * 检查著录项模板是否存在重复
	 * @param map
	 * @return
	 */
	public int checkIsJustOne(Map<String,Object> map);
}
