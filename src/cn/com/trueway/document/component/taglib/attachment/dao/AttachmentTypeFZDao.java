package cn.com.trueway.document.component.taglib.attachment.dao;

import java.util.List;

import cn.com.trueway.document.component.taglib.attachment.model.po.AttachFileType;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeFZ;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeBind;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeSub;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeWfMainShip;

public interface AttachmentTypeFZDao {


	/**
	 * 
	 * 描述：获取材料种类总数
	 * @param conditionSql
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:41:24
	 */
	int findAttachmentTypeCount(String conditionSql);
	/**
	 * 
	 * 描述：获取附件材料类别列表
	 * @param AttachmentType
	 * @param pageIndex
	 * @param pageSize
	 * @return List<AttachmentType>
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:41:47
	 */
	List<AttachmentTypeFZ> findAttachmentTypeList(String AttachmentType, Integer pageIndex,  Integer pageSize);
	
	/**
	 * 
	 * 描述：保存附件材料
	 * @param attachmentType
	 * @return AttachmentType
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:42:19
	 */
	void saveAttachmentType(AttachmentTypeFZ attachmentType) throws Exception;

	/**
	 * 
	 * 描述：根据id获取附件材料
	 * @param id
	 * @return AttachmentType
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:42:38
	 */
	AttachmentTypeFZ findAttachmentTypeById(String id);
	
	/**
	 * 
	 * 描述：删除附件材料(先验证是否被绑定)
	 * @param attachmentType void
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:43:04
	 */
	void delAttachmentType(AttachmentTypeFZ attachmentType) throws Exception;
	
	/**
	 * 
	 * 描述：更新附件材料
	 * @param attachmentType
	 * @return AttachmentType
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 上午10:43:37
	 */
	void updateAttachmentType(AttachmentTypeFZ attachmentType) throws Exception;
	
	/**
	 * 
	 * 描述：保存附件材料与事项节点的关联关系
	 * @param attachmentTypeBind
	 * @return JSONObject
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 下午3:00:55
	 */
	void saveAttachmentTypeBind(AttachmentTypeBind attachmentTypeBind) throws Exception;
	
	/**
	 * 
	 * 描述：删除某个节点中的材料绑定
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 下午3:05:47
	 */
	void deleteAttachmentTypeBind(String nodeId);
	
	/**
	 * 
	 * 描述：获取某一nodeId绑定的附件材料
	 * @param nodeId
	 * @return List<AttachmentTypeBind>
	 * 作者:蔡亚军
	 * 创建时间:2014-9-30 下午3:23:54
	 */
	List<AttachmentTypeBind> getAttachmentTypeBindList(String nodeId) throws Exception;
	
	/**
	 * 
	 * 描述：获取表单中材料的数据提交
	 * @param itemId
	 * @param instanceId
	 * @param nodeId
	 * @return List<AttachmentTypeSub>
	 * 作者:蔡亚军
	 * 创建时间:2014-10-8 上午9:57:14
	 */
	List<AttachmentTypeSub> findAttachmentTypeSubList(String itemId,
						String instanceId, String nodeId)  throws Exception;
	
	/**
	 * 
	 * 描述：保存材料文件
	 * @param ids
	 * @param typeIds
	 * @param isSubs
	 * @param isbqbzs
	 * @param itemId
	 * @param instanceId
	 * 作者:蔡亚军
	 * 创建时间:2014-10-8 上午10:56:40
	 */
	void saveAttachmentTypeSub(AttachmentTypeSub attachmentTypeSub) throws Exception;
	
	/**
	 * 
	 * 描述：修改材料文件
	 * @param attachmentTypeSub
	 * @throws Exception void
	 * 作者:蔡亚军
	 * 创建时间:2014-10-8 上午10:57:52
	 */
	void updateAttachmentTypeSub(AttachmentTypeSub attachmentTypeSub) throws Exception;
	
	/**
	 * 
	 * 描述：获取全部流程中附件
	 * @param instanceId
	 * @return List<AttachmentTypeSub>
	 * 作者:蔡亚军
	 * 创建时间:2014-10-8 上午11:03:06
	 */
	List<AttachmentTypeSub> findAttachmentTypeSubById(String instanceId, String nodeId) throws Exception;
	/**
	 * 
	 * 描述：获取未提交的材料文件
	 * @param insanceId
	 * @param nodeId
	 * @return
	 * @throws Exception List<AttachmentTypeSub>
	 * 作者:蔡亚军
	 * 创建时间:2014-10-8 下午2:50:23
	 */
	List<AttachmentTypeSub> findUnSubedList(String insanceId, String nodeId);
	
	void saveAttachTypeWfMainShip(AttachmentTypeWfMainShip attachTypeWfMainShip) throws Exception;
	
	void deleteAttachTypeWfMainShip(String attachTypeId, String searchIds);
	
	List<AttachmentTypeWfMainShip>  findAttachTypeWfMainShipList(String attachTypeId);
	
	List<AttachmentTypeFZ> findAttachmentTypeList(String conditionSql);
	
	List<AttachFileType> findAttachFileTypeList(String sslx);
	
	void saveAttachFileType(AttachFileType attachFileType);
	
	AttachFileType findAttachFileTypeById(String id);
	
	void updateAttachFileType(AttachFileType attachFileType);
	
	void deleteAttachFileTypeById(String id);
	
	List<AttachmentTypeWfMainShip> findAttachmentTypeWfMainShipList(String lcId);
	
	List<Object[]> findAttachmentTypeBind(String lcId);
}
