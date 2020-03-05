package cn.com.trueway.archiveModel.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archiveModel.entity.CheckLog;
import cn.com.trueway.archiveModel.entity.EssStructure;
import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.entity.FileAttachment;
import cn.com.trueway.archiveModel.entity.RsaMes;
import cn.com.trueway.base.util.Paging;


public interface EssModelDao {

	/**
	 * 根据id查idBusiness=1档案的树结构
	 * @param id
	 * @param pid
	 * @return
	 */
	List<EssTree> getModelById(String id,String pid);
	/**
	 * 分页
	 * @param map
	 * @param paging
	 * @return
	 * @throws Exception
	 */
	List<EssTree> getModelObjListByMap(Map<String, String> map, Paging paging) throws Exception;
	
	void saveEssModel(EssTree ess);
	void updateEssModel(EssTree ess);
	void deleteEssModelById(String id);
	Integer deleteEssModelByIds(String ids);
	
	/**
	 * 根据条件查找对应tag关联字段
	 * @param map
	 * @return
	 */
	List<EssTag> queryTags(Map<String,String> map);
	/**
	 * 查Structure子结构
	 * @param structure
	 * @return
	 */
	List<Map<String,Object>> getChildStructure(Integer structure);
	/**
	 * 根据拼接的sql查要检测的值
	 * @param sql
	 * @return
	 */
	String getOneMesByTag(String sql);
	/**
	 * 存查询日志
	 * @param log
	 */
	void saveCheckLog(CheckLog log);
	/**
	 * 根据树节点id查找全宗名称
	 * @param codeId
	 * @return
	 */
	String getTopTreeOfBranch(String codeId);
	/**
	 * 查找生成的表中关联元数据的字段
	 * @param struc
	 * @return ID,NAME
	 */
	List<Map<String,Object>> getNeededFields(String struc);
	/**
	 * 存生成eep信息
	 * @param atta
	 */
	void saveFileAttachement(FileAttachment atta);
	/**
	 * 查eep文件信息
	 * @param id
	 * @param sjid
	 * @param struc
	 * @return
	 */
	FileAttachment getFileAttachmentById(String id,String sjid,String struc);
	/**
	 * 根据id查核心结构信息
	 * @param id
	 * @return
	 */
	EssStructure getStructureById(String id);
	/**
	 * 存rsa信息
	 * @param rsa
	 */
	void saveRsaMes(RsaMes rsa);
	List<Map<String, Object>> getTaskDetailsById(Map<String, String> map, List<EssTag> tags);
	List<String[]> getMetaDataList(Map<String, String> map);
	List<String[]> getNameSpaceList(Map<String, String> map);
	int getMetaDataCount(Map<String, String> map);
	List<EssTag> queryTagsForShow(Map<String, String> map);
	List<EssTag> queryTagsForSearch(Map<String, String> map);
	String queryTagsForOrder(Map<String, String> map);
	int getTaskDetailsCount(Map<String, String> map, List<EssTag> tags1,
			List<EssTag> tags2);
	List<Map<String, Object>> getTaskDetails(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize, Integer pageIndex);
	List<EssTag> queryTagsForShowOfzhyw(Map<String, String> map);
	String[] getMetaDataById(String id);
	List<String[]> getTaskDetails4ZHYW(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize,
			Integer pageIndex);
	int getTaskDetails4ZHYWCount(Map<String, String> map, List<EssTag> tags1,
			List<EssTag> tags2);
	List<String[]> queryInfo(String sql, int returnColumn);
	
}
