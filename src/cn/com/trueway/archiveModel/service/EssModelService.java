package cn.com.trueway.archiveModel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.trueway.archiveModel.entity.CheckLog;
import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.entity.FileAttachment;
import cn.com.trueway.base.util.Paging;


public interface EssModelService {

	/**
	 * 分页
	 * @param map
	 * @param paging
	 * @return
	 */
	List<EssTree> getModelObjListByMap(Map<String, String> map, Paging paging);
	/**
	 * 树型结构获取数据
	 * @param id
	 * @param pid
	 * @return
	 */
	String getModelTreeById(String id,String pid);
	/**
	 * 根据id查
	 * @param id
	 * @param pid
	 * @return
	 */
	List<EssTree> getModelById(String id,String pid);
	
	void saveEssModel(EssTree ess);
	void updateEssModel(EssTree ess);
	void deleteEssModelById(String id);
	Integer deleteEssModelByIds(String ids);
	
	/**
	 * 获取字段信息
	 * @param map
	 * @return
	 */
	List<EssTag> queryTags(Map<String, String> map);
	/**
	 * 详情页标题
	 * @param struc
	 * @return
	 */
	String getTagsTitle(String struc);
	/**
	 * 四性检测
	 */
	HSSFWorkbook checkResultExcel(String struc, String [] ids);
	List<String> checkSixing(String struc,String id);
	/**
	 * 存日志
	 * @param log
	 */
	void saveCheckLog(CheckLog log);
	/**
	 * 存eep信息
	 * @param atta
	 */
	void saveFileAttachement(FileAttachment atta);
	
	FileAttachment getFileAttachmentById(String id,String sjid,String struc);
	List<Map<String, Object>> getChildStructure(Integer struc);
	List<Map<String, Object>> getTaskDetailsById(Map<String, String> map, List<EssTag> tags);
	List<String[]> getMetaDataList(Map<String, String> map);
	List<String[]> getNameSpaceList(Map<String, String> map);
	int getMetaDataCount(Map<String, String> map);
	List<EssTag> queryTagsForShow(Map<String, String> map);
	List<EssTag> queryTagsForSearch(Map<String, String> map);
	String queryTagsForOrder(Map<String, String> map);
	int getTaskDetailsCount(Map<String, String> map1,List<EssTag> tags1,List<EssTag> tags2);
	List<Map<String, Object>> getTaskDetails(Map<String, String> map1,
			List<EssTag> tags, List<EssTag> tags1, Integer pageSize, Integer pageIndex);
	List<EssTag> queryTagsForShowOfzhyw(Map<String, String> map);
	String[] getMetaDataById(String string);
	List<String[]> getTaskDetails4ZHYW(Map<String, String> map1,
			List<EssTag> tags, List<EssTag> tags1, Integer pageSize, Integer pageIndex);
	int getTaskDetails4ZHYWCount(Map<String, String> map1, List<EssTag> tags,
			List<EssTag> tags1);
	List<String[]> queryInfo(String sql, int returnColumn);

}
