package cn.com.trueway.document.business.dao;

import java.util.List;

import oracle.sql.BLOB;

import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.business.model.ToRecDoc;
import cn.com.trueway.document.business.model.ToRecDocAttachmentSext;
import cn.com.trueway.document.business.model.ToRecDocAttachments;
import cn.com.trueway.document.business.model.ToSendDoc;
import cn.com.trueway.document.business.model.ToSendDocAttachments;

public interface ToRecDocDao {
	
	int findToRecDocCount(String conditionSql) throws Exception;
	
	List<ToRecDoc> findToRecDocList(String conditionSql, Integer pageIndex, Integer pageSize ) throws Exception;

    void updateToRecDocStatus(String id, String userId) throws Exception;
	
	int findToRecedDocCount(String userId, String conditionSql) throws Exception;
	
	List<ToRecDoc> findToRecedDocList(String userId, String conditionSql, Integer pageIndex, Integer pageSize) throws Exception;

	ToRecDoc findToRecDocById(String id) throws Exception;
	
	ToRecDoc findToRecDocByDocguid(String Docguid) throws Exception;
	 
	List<ToRecDocAttachments> findToRecDocAttachmentsByDocguid(String docguid) throws Exception;
	
	List<ToRecDocAttachmentSext> findToRecDocAttachmentSextByDocguid(String docguid) throws Exception;
	
	void saveToSendDoc(ToSendDoc toSendDoc) throws Exception;
	
	List<ToSendDoc> findToSendDocList(String userId, String conditionSql, Integer pageIndex, Integer pageSize);
	
	int findToSendDocCount(String userId, String conditionSql);
	
	List<Object[]>  findRecDepList(String instanceId);
	
	void updateToRecDocAttachments(ToRecDocAttachments entity) throws Exception;
	
	void updateToRecDocAttachmentSext(ToRecDocAttachmentSext entity) throws Exception;
	
	ToSendDocAttachments saveToSendDocAttachments(ToSendDocAttachments toSendDocAttachments);
	
	void updateToSendDocAttachments(ToSendDocAttachments toSendDocAttachments);

	BLOB getBlob(String tablename, String fieldname, String key, String value)
			throws Exception;
	
	RecShip getRecShipByRecAttId(String recAttId);
	
	void addRecShip(RecShip recShip);
	
	List<RecShip> getRecShipByRecId(String recId);

}
