package cn.com.trueway.document.business.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import oracle.sql.BLOB;
import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ToRecDoc;
import cn.com.trueway.document.business.model.ToRecDocAttachmentSext;
import cn.com.trueway.document.business.model.ToRecDocAttachments;
import cn.com.trueway.document.business.model.ToSendDoc;
import cn.com.trueway.document.business.model.ToSendDocAttachments;
import cn.com.trueway.workflow.core.pojo.WfItem;

public interface ToRecDocService {
	
	/**
	 * 
	 * 描述：获取待收办件数
	 * @param conditionSql
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午2:56:17
	 */
	int findToRecDocCount(String conditionSql);
	
	/**
	 * 
	 * 描述：获取待收办件列表
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return List<ToRecDoc>
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午2:56:37
	 */
	List<ToRecDoc> findToRecDocList(String conditionSql, Integer pageIndex, Integer pageSize );
	
	JSONObject updateToRecDocStatus(String id, String userId);
	
	int findToRecedDocCount(String userId, String conditionSql);
	
	List<ToRecDoc> findToRecedDocList(String userId, String conditionSql, Integer pageIndex, Integer pageSize);

	ToRecDoc findToRecDocById(String id);
	
	ToRecDoc findToRecDocByDocguid(String Docguid);
	
	List<ToRecDocAttachments> findToRecDocAttachmentsByDocguid(String docguid);
	
	List<ToRecDocAttachmentSext> findToRecDocAttachmentSextByDocguid(String docguid);
	
	Map<String, ReceiveProcessShip> findReceiveWfpShipList(List<ToRecDoc> list);
	
	JSONObject saveToSendDoc(ToSendDoc toSendDoc);
	
	List<ToSendDoc> findToSendDocList(String userId, String conditionSql, Integer pageIndex, Integer pageSize);
	
	int findToSendDocCount(String userId, String conditionSql);
	
	List<Object[]>  findRecDepList(String instanceId);
	
	void updateToRecDocAttachments(ToRecDocAttachments entity);
	
	void updateToRecDocAttachmentSext(ToRecDocAttachmentSext entity);
	
	void saveToSendDocAttachments(ToSendDocAttachments toSendDocAttachments);
	
	void updateToSendDocAttachments(ToSendDocAttachments toSendDocAttachments);

	BLOB getBlob(String tablename, String fieldname, String key, String value);
	
	/**
	 * 获取前置机移动端待收
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @param pendList
	 * @param column
	 * @param pagesize
	 * @param type
	 * @param itemIds
	 * @param serverUrl
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-5-16 上午11:04:16
	 */
	public String findToRecDocListOfMobile(String userId, List<ToRecDoc> pendList, int column,
			Integer pagesize, String type, String itemIds,String serverUrl);

	
	/**
	 * 获取前置机移动端已收
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @param pendList
	 * @param itemList
	 * @param map
	 * @param column
	 * @param pagesize
	 * @param type
	 * @param itemIds
	 * @param serverUrl
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-5-16 下午2:02:37
	 */
	public String findToRecedDocListOfMobile(String userId, List<ToRecDoc> pendList,List<WfItem> itemList,
			Map<String, ReceiveProcessShip> map,int column,Integer pagesize, String type,
			String itemIds,String serverUrl);
	
	/**
	 * 根据前置机附件id获取关系
	 * 描述：TODO 对此方法进行描述
	 * @param recAttId
	 * @return RecShip
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午10:47:23
	 */
	RecShip getRecShipByRecAttId(String recAttId);
	
	/**
	 * 新建前置机附件与工作流表的关系
	 * 描述：TODO 对此方法进行描述
	 * @param recShip void
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午10:55:59
	 */
	void addRecShip(RecShip recShip);
	
	/**
	 * 根据前置机办件id获取pdfpath
	 * 描述：TODO 对此方法进行描述
	 * @return List<RecShip>
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午11:57:04
	 */
	List<RecShip> getRecShipByRecId(String recId);
	
}
