package cn.com.trueway.document.business.service;

import java.util.List;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.model.Doc;

/**
 * 作者：周雪贇<br>
 * 创建时间：2011-12-1 下午03:34:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface SendDocService {
	/**
	 * 描述：根据RESULTFLAG查询出DOC对象，包含附件<br>
	 *
	 * @param ResultFlag
	 * @return Doc
	 */
	public Doc getFullDocByRF(String ResultFlag);
	/**
	 * 描述：发送公文：调用WS接口到公文交换平台并且想本地数据库插入服务<br>
	 *
	 * @param doc
	 * @return String
	 */
	/**
	 * 描述：根据docguid查询出DOC对象，包含附件<br>
	 *
	 * @param ResultFlag
	 * @return Doc
	 */
	public Doc findFullDocByDocguid(String docguid);
	
	public String sendDoc(Doc doc);
	
	/**
	 * 描述：根据instanceId查出DOC对象及其附件对象<br>
	 *
	 * @param instanceId
	 * @return Doc
	 * @throws Exception 
	 */
	public Doc findFullDocByInstanceId(String instanceId) throws Exception;
	/**
	 * 描述：根据条件查询已发列表<br>
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean getSentDocList(int currentPage, int numPerPage, List<String> deps, String keyword, String content,String wh,String status,String userId);
	
	/**
	 * 
	 * 描述：在办理发送时，根据InstanceId查询Doc
	 *
	 * @param instanceId
	 * @return Doc
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午04:10:21
	 */
	public List<Doc> findDocForSendInProgress(String instanceId);
	
	/**
	 * 
	 * 描述：根据所传的附件Ids 为docguid增加附加附件
	 *
	 * @param docguid
	 * @param attextIds void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午08:29:46
	 */
	public void addAttsextForBatchUpload(String docguid, String attextIds,String commentIds);
	
	/**
	 * 
	 * 描述：保存Doc<br>
	 *
	 * @param doc void
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-7 下午04:05:18
	 */
	public void saveDoc(Doc doc);
	
	/**
	 * 
	 * 描述：更新Doc<br>
	 *
	 * @param doc void
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-7 下午04:05:30
	 */
	public void updateDoc(Doc doc);
	
	/**
	 * 
	 * 描述：保存或更新DOC<br>
	 *
	 * @param oldDoc
	 * @param doc
	 * @param saveOrUpdate void
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-7 下午04:14:15
	 */
	public void saveOrUpdateDoc(Doc oldDoc, Doc doc, String saveOrUpdate);
	
	public Doc findDocByInstanceId(String instanceId);
	
	/**
	 * 描述：解析文号<br>
	 *
	 * @param docNum
	 * @return DocBw
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-10 下午06:02:17
	 */
	public Doc paserDocNum(Doc dn, String docNum);
	
	/**
	 * 
	 * 描述：发文单列表<br>
	 *
	 * @param defines
	 * @param title
	 * @param wh
	 * @param startTime
	 * @param endTime
	 * @param currentPage
	 * @param pageSize
	 * @return DTPageBean
	 */
	public String checkbw(String wh, String title,String webId);
}
