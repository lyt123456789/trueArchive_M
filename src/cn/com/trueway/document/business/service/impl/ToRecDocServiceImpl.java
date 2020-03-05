package cn.com.trueway.document.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.BLOB;
import cn.com.trueway.document.business.dao.ReceiveDocDao;
import cn.com.trueway.document.business.dao.ToRecDocDao;
import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ToRecDoc;
import cn.com.trueway.document.business.model.ToRecDocAttachmentSext;
import cn.com.trueway.document.business.model.ToRecDocAttachments;
import cn.com.trueway.document.business.model.ToSendDoc;
import cn.com.trueway.document.business.model.ToSendDocAttachments;
import cn.com.trueway.document.business.service.ToRecDocService;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.Todos;

public class ToRecDocServiceImpl implements ToRecDocService{
	
	private ToRecDocDao toRecDocDao;
	
	private ReceiveDocDao receiveDocDao;
	
	private TableInfoDao tableInfoDao;
	
	private ItemDao itemDao;

	public ToRecDocDao getToRecDocDao() {
		return toRecDocDao;
	}

	public void setToRecDocDao(ToRecDocDao toRecDocDao) {
		this.toRecDocDao = toRecDocDao;
	}
	
	public ReceiveDocDao getReceiveDocDao() {
		return receiveDocDao;
	}

	public void setReceiveDocDao(ReceiveDocDao receiveDocDao) {
		this.receiveDocDao = receiveDocDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	@Override
	public int findToRecDocCount(String conditionSql) {
		Integer i = 0;
		try {
			i = toRecDocDao.findToRecDocCount(conditionSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<ToRecDoc> findToRecDocList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		List<ToRecDoc> list = new ArrayList<ToRecDoc>();
		try {
			list = toRecDocDao.findToRecDocList(conditionSql, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public JSONObject updateToRecDocStatus(String id, String userId) {
		JSONObject result = new JSONObject();
		try {
			if(id!=null && !id.equals("")){
				String[] ids = id.split(",");
				for(int i=0; i<ids.length; i++){
					toRecDocDao.updateToRecDocStatus(ids[i], userId);
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}

	@Override
	public int findToRecedDocCount(String userId, String conditionSql) {
		Integer i = 0;
		try {
			i = toRecDocDao.findToRecedDocCount(userId, conditionSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<ToRecDoc> findToRecedDocList(String userId,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		List<ToRecDoc> list = new ArrayList<ToRecDoc>();
		try {
			list = toRecDocDao.findToRecedDocList(userId, conditionSql, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ToRecDoc findToRecDocById(String id) {
		ToRecDoc toRecDoc = new ToRecDoc();
		try {
			toRecDoc =  toRecDocDao.findToRecDocById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toRecDoc;
	}

	@Override
	public List<ToRecDocAttachments> findToRecDocAttachmentsByDocguid(
			String docguid) {
		List<ToRecDocAttachments> list = new ArrayList<ToRecDocAttachments>();
		try {
			list = toRecDocDao.findToRecDocAttachmentsByDocguid(docguid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ToRecDocAttachmentSext> findToRecDocAttachmentSextByDocguid(
			String docguid) {
		List<ToRecDocAttachmentSext> list = new ArrayList<ToRecDocAttachmentSext>();
		try {
			list = toRecDocDao.findToRecDocAttachmentSextByDocguid(docguid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ToRecDoc findToRecDocByDocguid(String Docguid) {
		ToRecDoc toRecDoc = new ToRecDoc();
		try {
			toRecDoc = toRecDocDao.findToRecDocByDocguid(Docguid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toRecDoc;
	}

	@Override
	public Map<String, ReceiveProcessShip> findReceiveWfpShipList(
			List<ToRecDoc> list) {
		Map<String, ReceiveProcessShip> map  = new HashMap<String, ReceiveProcessShip>();
		for(int i=0; list!=null && i<list.size(); i++){
			String recId = list.get(i).getDocguid();
			ReceiveProcessShip receiveProcessShip = receiveDocDao.findReceiveProcessShipByRecId(recId);
			if(receiveProcessShip!=null){
				String processId = receiveProcessShip.getProcessId();
				if(processId!=null && !processId.equals("")){
					String instanceId = receiveProcessShip.getInstanceId();
					//是否作废
					List<WfProcess> wflist = tableInfoDao.getProcessList(instanceId);
					String itemId = "";
					if(wflist!=null && wflist.size()==0){
						receiveProcessShip.setStatus(3);
						itemId = "";
					}else if(wflist!=null && wflist.size()>1){
						receiveProcessShip.setStatus(2);
						itemId = wflist.get(0).getItemId();
					}else if(wflist!=null && wflist.size()>0){
						receiveProcessShip.setStatus(1);
						itemId = wflist.get(0).getItemId();
					}
					if(itemId!=null && !itemId.equals("")){
						WfItem wfItem = itemDao.getItemById(itemId);
						receiveProcessShip.setItemName(wfItem.getVc_sxmc());
					}
				}else{
					receiveProcessShip.setStatus(0);	//表示未收(展示收入待办)
				}
				map.put(recId, receiveProcessShip);
			}
		}
		return map;
	}

	@Override
	public JSONObject saveToSendDoc(ToSendDoc toSendDoc) {
		JSONObject result = new JSONObject();
		try {
			toRecDocDao.saveToSendDoc(toSendDoc);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ToSendDoc> findToSendDocList(String userId,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		return toRecDocDao.findToSendDocList(userId, conditionSql, pageIndex, pageSize);
	}

	@Override
	public int findToSendDocCount(String userId, String conditionSql) {
		return toRecDocDao.findToSendDocCount(userId, conditionSql);
	}

	@Override
	public List<Object[]> findRecDepList(String instanceId) {
		return toRecDocDao.findRecDepList(instanceId);
	}

	@Override
	public void updateToRecDocAttachments(ToRecDocAttachments entity) {
		try {
			toRecDocDao.updateToRecDocAttachments(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateToRecDocAttachmentSext(ToRecDocAttachmentSext entity) {
		try {
			toRecDocDao.updateToRecDocAttachmentSext(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveToSendDocAttachments(
			ToSendDocAttachments toSendDocAttachments) {
		ToSendDocAttachments att = toRecDocDao.saveToSendDocAttachments(toSendDocAttachments);
	}

	@Override
	public void updateToSendDocAttachments(
			ToSendDocAttachments toSendDocAttachments) {
		toRecDocDao.updateToSendDocAttachments(toSendDocAttachments);
	}

	@Override
	public BLOB getBlob(String tablename, String fieldname, String key,
			String value) {
		BLOB blob = null;
		try {
			blob = toRecDocDao.getBlob(tablename,fieldname,key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blob;
	}
	
	
	@Override
	public String findToRecedDocListOfMobile(String userId, List<ToRecDoc> recList,List<WfItem> itemList,
			Map<String, ReceiveProcessShip> map,int column,Integer pagesize, String type,
			String itemIds,String serverUrl) {
		List<Todos> todoList = new ArrayList<Todos>();
		if(recList.size()!=0 && recList!=null && !("").equals(recList)){
			for (ToRecDoc rec : recList) {
				Todos todo = new Todos();
				ReceiveProcessShip rps = map.get(rec.getDocguid());
				if(null != rps){
					todo.setStatus(rps.getStatus()==null?"0":rps.getStatus().toString());
				}
				todo.setTitle(rec.getBt());
				todo.setDate( rec.getSendTime()==null?"":rec.getSendTime().toString());
				todo.setUserId(userId);
				todo.setProcessId(rec.getDocguid());//唯一id
				todo.setU_Id(rec.getId());
				todo.setType(rec.getType());
				todo.setItemList(itemList);
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public String findToRecDocListOfMobile(String userId, List<ToRecDoc> recList, int column,
			Integer pagesize, String type, String itemIds,String serverUrl) {
		List<Todos> todoList = new ArrayList<Todos>();
		if(recList.size()!=0 && recList!=null && !("").equals(recList)){
			for (ToRecDoc rec : recList) {
				Todos todo = new Todos();
					todo.setTitle(rec.getBt());
					todo.setDate( rec.getSendTime()==null?"":rec.getSendTime().toString());
					todo.setUserId(userId);
					todo.setProcessId(rec.getDocguid());//唯一id
					todo.setU_Id(rec.getId());
					todo.setType(rec.getType());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public RecShip getRecShipByRecAttId(String recAttId){
		return toRecDocDao.getRecShipByRecAttId(recAttId);
	}
	
	@Override
	public void addRecShip(RecShip recShip){
		toRecDocDao.addRecShip(recShip);
	}
	
	@Override
	public List<RecShip> getRecShipByRecId(String recId){
		return toRecDocDao.getRecShipByRecId(recId);
	}
	
	
}
