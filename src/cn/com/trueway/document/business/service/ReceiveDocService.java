package cn.com.trueway.document.business.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo;
import cn.com.trueway.document.business.model.CheckInRecDoc;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ReceiveXml;

/**
 * 作者：周雪贇<br>
 * 创建时间：2011-12-1 下午03:34:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface ReceiveDocService {
	/**
	 * 描述：获取待收列表的MAP<br>
	 * @param deps
	 * @return LinkedHashMap<String,DocExchangeVo>
	 */
	public LinkedHashMap<String, DocExchangeVo> getToBeReceivedList(List<ReceiveXml> xmlList); 
	/**
	 * 描述：从公文交换收取一个公文，并且入库<br>
	 * @param doc
	 * @param idString
	 * @return String
	 * @throws Exception 
	 */
	public String addReceiveOneDoc(DocExchangeVo doc,String idString) throws Exception;
	/**
	 * 描述：从公文交换收取全部<br>
	 * @param doc
	 * @param idString
	 * @return String
	 * @throws Exception 
	 */
	public String addAllReceiveDoc(Map<String, DocExchangeVo> tobeRecevicedoc,String departmentId) throws Exception;
	
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
	public DTPageBean queryReceivedDoc(int currentPage, int numPerPage,
			List<String> xto, String keyword, String content, String status,String timeType,String startTime,String endTime);
	
	/**
	 * 
	 * 描述：根据条件查询已收公文数目
	 *
	 * @param deps
	 * @param wh
	 * @param title
	 * @param sendername
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return long
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-4 下午02:38:23
	 */
	public long queryReceivedDocCounts(List<String>deps, String wh,String title,String sendername, String statuskey, String timeType, String startTime, String endTime,String keyword);
	
	public long queryReceivedDocCounts(List<String>deps, String wh,String title,String sendername, String statuskey, String timeType, String startTime, String endTime,String keyword,boolean isIN,String whs);
	
	/**
	 * 描述：根据DOCGUID得到RECEVIEDOC对象<br>
	 *
	 * @param docguid
	 * @return RecDoc
	 */
	public RecDoc getRecDocByDocguid(String docguid);
	
	/**
	 * 
	 * 描述：保存RecDoc
	 *
	 * @param recDoc void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 下午02:27:01
	 */
	public void saveRecDoc(RecDoc recDoc);
	
	public void saveCheckInRecDoc(CheckInRecDoc docCheckin);
	//根据docguid 修改备注及其状态位
	public void updateBeizhuByDocguid(RecDoc recDoc);

	/**
	 * 	/**
	 * 
	 * 描述：根据条件查询已收列表
	 * @return
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword);
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword,boolean isIN,String whs);
	
	/**
	 * 
	 * 描述：根据查询条件导出已收公文
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-3 上午10:28:30
	 */
	public List<String[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols);

	public List<String[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols,boolean isIN,String whs);
	
	public StringBuilder getToBeReceivedListForPortal(List<String> deps,
			String callBack,String url);
	/**
	 * 当前用户收文的查看状态
	 */
	public void viewStatus(String userId,RecDoc recDoc);
	public List<RecDoc> checkViewStatus(DTPageBean db, String userId);
	public String findDepIdByEmpId(String userId);
	public String queryReceivedDocForPortal(String url, List<String> deps,String callBack);
	
	/**
	 * 分页获取公文交换平台处已接收列表
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<RecDoc> getReceivedDocList(String conditionSql, Integer  pageIndex, Integer pageSize);
	
	/**
	 * 获取已接收列表中信息的数量
	 * @param conditionSql
	 * @return
	 */
	public int getReceivedDocCount(String conditionSql);
	
	/**
	 * 
	 * 描述：保存已收与待办关联关系
	 * @param receiveProcessShip void
	 * 作者:蔡亚军
	 * 创建时间:2014-8-19 下午3:05:12
	 */
	public void saveReceiveProcessShip(ReceiveProcessShip receiveProcessShip);
	
	/**
	 * 
	 * 描述：获取关联关系
	 * @return Map<String,ReceiveProcessShip>
	 * 作者:蔡亚军
	 * 创建时间:2014-8-19 下午3:21:20
	 */
	public Map<String, ReceiveProcessShip> findReceiveProcessShipList(List<RecDoc> list);
	
	public ReceiveProcessShip findReceiveProcessShipByRecId(String recId); 
	
	public List<ReceiveXml> findReceiveXmlList(List<String> deps);
	
	public void saveReceiveXml(ReceiveXml receiveXml);
	
	public List<ReceiveXml> findReceiveXmlByRecId(String recId);
	
	public void updateReceiveProcessShip(ReceiveProcessShip receiveProcessShip);
}
