package cn.com.trueway.document.business.dao;

import java.util.List;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.model.CheckInRecDoc;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ReceiveXml;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
/**
 * 描述：对收文的库操作<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-2 上午11:05:31<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface ReceiveDocDao {
	
	/**
	 * 描述：插OA_DOC_RECEIVE表<br>
	 * @param recDoc 
	 */
	public void saveRecDOC(RecDoc recDoc);
	/**
	 * 描述：保存正文附件<br>
	 * @param recDoc 
	 */
	public void saveRecDocAtts(ReceiveAttachments att);

	/**
	 * 描述：根据条件查询已收列表<br>
	 *
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean queryReceivedDoc(int currentPage,int numPerPage,List<String> xto,String keyword,String content,String status,String timeType,String startTime,String endTime);
	
	/**
	 * 描述：根据DOCGUID得到RECEVIEDOC对象<br>
	 *
	 * @param docguid
	 * @return RecDoc
	 */
	public RecDoc getRecDocByDocguid(String docguid);
	//更新已收公文的状态(STATUS)
	public void updateRecDOC(RecDoc recDoc);
	
	public void saveCheckInRecDoc(CheckInRecDoc docCheckin);
	//根据docguid 修改备注及其状态位
	public void updateBeizhuByDocguid(RecDoc recDoc);
	
	/**
	 * 
	 * 描述：根据ID更新RecDoc的STATUS状态位
	 *
	 * @param docguid
	 * @param statusValue void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-29 下午01:58:49
	 */
	public void updateRecDocStatus(String docguid, String statusValue);
	
	/**
	 * 
	 * 描述：根据条件查询已收公文数目<br>
	 *
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @return int
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-2 下午03:04:21
	 */
	public long queryReceivedDocCounts(List<String>deps, String wh,String title,String sendername, String statuskey, String timeType, String startTime, String endTime,String keyword);

	public long queryReceivedDocCounts(List<String>deps, String wh,String title,String sendername, String statuskey, String timeType, String startTime, String endTime,String keyword,boolean isIN,String whs);

	/**
	 * 
	 * 描述：根据查询条件导出已收公文
	 *
	 * @param deps
	 * @param keyword
	 * @param content
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param cols
	 * @return List<Object[]>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-3 上午10:28:43
	 */
	public List<Object[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols);

	public List<Object[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols,boolean isIN,String whs);
	
	/**
	 * 
	 * 描述：根据条件查询已收列表
	 *
	 * @param currentPage
	 * @param pageSize
	 * @param deps
	 * @param wh
	 * @param title
	 * @param sendername
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return DTPageBean
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-4 下午02:17:06
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword);
	
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword,boolean isIN,String whs);
	public void setViewStatus(String userId,RecDoc recDocs);
	public String findDepIdByEmpId(String userId);
	public List<RecDoc> queryReceivedDocForPortal(String url, List<String> deps);
	
	public List<RecDoc> getReceivedDocList(String conditionSql, Integer  pageIndex, Integer pageSize);
	
	public int getReceivedDocCount(String conditionSql);
	
	/**
	 * 
	 * 描述：保存已收与待办关联关系
	 * @param receiveProcessShip void
	 * 作者:蔡亚军
	 * 创建时间:2014-8-19 下午3:05:12
	 */
	public void saveReceiveProcessShip(ReceiveProcessShip receiveProcessShip);
	
	public ReceiveProcessShip findReceiveProcessShipByRecId(String recId); 
	
	public void saveReceiveXml(ReceiveXml receiveXml);

	public List<ReceiveXml> findReceiveXmlByRecId(String recId);
	
	public void updateReceiveProcessShip(ReceiveProcessShip receiveProcessShip);

}
