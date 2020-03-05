package cn.com.trueway.document.component.taglib.attachment.service;

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


/**
 * 描述：为附件标签提供接口<br>
 * 作者：WangXF<br>
 * 创建时间：2011-12-2 上午09:36:42<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface AttachmentService {
	
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

	public void addSendAttsext(List<SendAttachmentsext> attsextList);
	
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
	 * 描述：删除【正文附件】（发文或收文根据isReceive判断）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteAtts(String attsId,boolean isReceive);

	/**
	 * 
	 * 描述：删除【附加附件】（发文或收文根据isReceive判断）
	 *
	 * @param attsextId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:43:50
	 */
	public void deleteAttsext(String attsextId,boolean isReceive);
	
	/**
	 * 
	 * 描述：word转成CEB文件后和CEB文件盖章后 CEB文件的导入
	 *
	 * @param attObj
	 * @param isManAtt void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-3 上午10:36:02
	 */
	public void addCebAtt(Object attObj,boolean isManAtt,String toRemoveFileid);

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

	public List<SendAttachments> findSendAttsByDocguid(String docguid);
	
	public List<SendAttachments> findSendAttachmentList(String instanceId,  List<String> typeList);

	public List<SendAttachmentsHistory> findIsEditOfSendAttHistory(String name);

	public void updateSendAttHistory(SendAttachmentsHistory sh);

	public List<SendAttachments> findSendAttsByIdAndUserName(String instanceId, String employeeGuid);
	
	public void deleteAtts(SendAttachments att);
	
	/**
	 * 描述：根据fj名称查找附件
	 * @param name
	 * @return SendAttachments
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-8-27 上午9:31:47
	 */
	public SendAttachments findAllSendAtt(String name);
	
	List<SendAttachments> findSendAttachmentListByInstanceId(String instanceId);
	
	/**
	 * 描述：根据节点ID获取附件列表
	 * @param map
	 * @return
	 */
	public List<SendAttachments> getAttachListByNode(Map<String, String> map);
	
	/**
	 * 将某流程实例的正文附件合并形成一个新的True文件
	 * @param pdfFormPath
	 * @param trueJson
	 * @param instanceId
	 * @param pdfNewPath
	 * @return
	 */
	public String[] mergerAttToPdf(String pdfFormPath, String trueJson, String instanceId, String pdfNewPath, int copyNum);
	
	/**
	 * 根据docguid获取符合的所有附件
	 * @param docguid
	 * @return
	 */
	public List<SendAttachments> findAllSendAtts(String docguid);
	

	/**
	 * 更新附件页数
	 * 描述：TODO 对此方法进行描述
	 * @param sattList
	 * @return int
	 * 作者:季振华
	 * 创建时间:2017-7-27 上午10:41:37
	 */
	int updateAttsToPageCount(List<SendAttachments> sattList);
	
	
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
	
	/** 
	 * update:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param data
	 * @param pdfData
	 * @param fileId 
	 * @since JDK 1.6 
	 */
	void update(Blob data, Blob pdfData, String fileId);
	
	SendAttachmentsHistory findSendAttHistory(String id);

	/**
	 * 方法描述: [这里用一句话描述这个方法的作用.]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2019-1-26-上午11:17:04<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param attId
	 * @param userId
	 * @return
	 * WfOnlineEditStatus
	 */
	public WfOnlineEditStatus findOESByInfo(String attId, String userId);

	public void addWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus);

	public void updateWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus);
	
	CutPages findCutPageById(String id);

	public void deleteAttsByIds(String ids);

	public void addSendHistoryLog(SendAttHistoryLog sendAttHistoryLog);
}
