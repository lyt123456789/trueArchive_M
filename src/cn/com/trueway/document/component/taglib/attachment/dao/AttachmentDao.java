package cn.com.trueway.document.component.taglib.attachment.dao;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import cn.com.trueway.document.component.taglib.attachment.model.po.Attachmentsext_type;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttHistoryLog;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsHistory;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.workflow.core.pojo.WfOnlineEditStatus;


public interface AttachmentDao {

	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（发文...）
	 *
	 * @param id
	 * @return OA_DOC_Attachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:44:45
	 */
	public SendAttachments findSendAtts(String id);
	
	/**
	 * 
	 * 描述：根据ID查出单个【附加附件】（发文...）
	 *
	 * @param id
	 * @return OA_DOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:23
	 */
	public SendAttachmentsext findSendAttsext(String id);
	
	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:38
	 */
	public ReceiveAttachments findReceiveAtts(String id);

	/**
	 * 
	 * 描述：根据ID查出单个【附加附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:52
	 */
	public ReceiveAttachmentsext findReceiveAttsext(String id);
	
	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（发文...）
	 *
	 * @param docguid
	 * @return List<OA_DOC_Attachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:35:01
	 */
	public List<SendAttachments> findAllSendAtts(String docguid,String isHb);

	
	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（发文...）
	 *
	 * @param docguid
	 * @return List<OA_DOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:37:21
	 */
	public List<SendAttachmentsext> findAllSendAttsext(String docguid);

	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:01
	 */
	public List<ReceiveAttachments> findAllReceiveAtts(String docguid);

	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:30
	 */
	public List<ReceiveAttachmentsext> findAllReceiveAttsext(String docguid);
	
	/**
	 * 
	 * 描述：增加【正文附件】（发文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:39:37
	 */
	public SendAttachments addSendAtts(SendAttachments atts);
	
	/**
	 * 
	 * 描述：增加【附加附件】（发文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:18
	 */
	public void addSendAttsext(SendAttachmentsext attsext);
	
	/**
	 * 
	 * 描述：增加【正文附件】（收文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:36
	 */
	public void addReceiveAtts(ReceiveAttachments atts);
	
	/**
	 * 
	 * 描述：增加【附加附件】（收文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:41:11
	 */
	public void addReceiveAttsext(ReceiveAttachmentsext attsext);

	/**
	 * 
	 * 描述：删除【正文附件】（发文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteSendAtts(String attsId);


	/**
	 * 
	 * 描述：删除【附加附件】（发文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteSendAttsext(String attsextId);


	/**
	 * 
	 * 描述：删除【正文附件】（收文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteReceiveAtts(String attsId);
	
	/**
	 * 
	 * 描述：删除【附加附件】（收文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteReceiveAttsext(String attsextId);
	
	/**
	 * 
	 * 描述：正文在线编辑历史 保存
	 *
	 * @param sendAttachmentsHistory void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:13
	 */
	public void addSendAttHistory(SendAttachmentsHistory sendAttachmentsHistory);
	
	/**
	 * 
	 * 描述：查询 正文在线编辑历史
	 *
	 * @param docguid
	 * @param id 
	 * @return List<SendAttachmentsHistory>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:17
	 */
	public List<SendAttachmentsHistory> findAllSendAttHistory(String docguid, String id);

	public void updateSendAtt(SendAttachments att);

	public List<Attachmentsext_type> findAllAttType();

	public SendAttachments modifyAtts(String attsId);

	public void modifyOfAtts(String attsId, String title, String type);

	public List<SendAttachments> findSendAttsByDocguid(String instanceId);
	
	public List<SendAttachments> findSendAttachmentList(String instanceId, List<String> typeList);

	public List<SendAttachmentsHistory> findIsEditOfSendAttHistory(String name);

	public void updateSendAttHistory(SendAttachmentsHistory sh);

	public List<SendAttachments> findSendAttsByIdAndUserName(String instanceId, String employeeGuid);
	
	public void deleteAtts(SendAttachments att);

	public SendAttachments findAllSendAtt(String name);
	
	List<SendAttachments> findSendAttachmentListByInstanceId(String instanceId);
	
	public List<SendAttachments> getAttachListByNode(Map<String, String> map);
	
	public List<SendAttachments> findAllSendAtts(String docguid);
	
	void saveCutPages(CutPages entity);
	
	void deleteCutPages(CutPages entity);
	
	List<CutPages> findCutPagesListByDocId(String docId);
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 下午1:40:00
	 */
	Integer countNoCuteAtt(Integer fileSize);
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 下午1:37:15
	 */
	List<String> getNoCuteAtt(Integer fileSize, Integer pageIndex, Integer pageSize);
	
	void update(Blob data, Blob pdfData, String fileId);
	
	SendAttachmentsHistory findSendAttHistory(String id);

	public WfOnlineEditStatus findOESByInfo(String attId, String userId);

	public void addWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus);

	public void updateWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus);
	
	CutPages findCutPageById(String id);

	public void deleteAttsByIds(String ids);

	public void addSendAttHistoryLog(SendAttHistoryLog sendAttHistoryLog);
}
