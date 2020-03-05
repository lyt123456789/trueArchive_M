package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.IntegrateDao;
import cn.com.trueway.workflow.core.pojo.ReadedEndPending;
import cn.com.trueway.workflow.core.pojo.TodoMessage;
import cn.com.trueway.workflow.core.pojo.vo.Todos;
import cn.com.trueway.workflow.core.service.IntegrateService;

public class IntegrateServiceImpl implements IntegrateService{
	
	private IntegrateDao integrateDao;
	
	public IntegrateDao getIntegrateDao() {
		return integrateDao;
	}

	public void setIntegrateDao(IntegrateDao integrateDao) {
		this.integrateDao = integrateDao;
	}

	@Override
	public int findTodoMessageCount(String conditionSql, String userId) {
		return integrateDao.findTodoMessageCount(conditionSql, userId);
	}
	
	public List<TodoMessage> findTodoMessage(String conditionSql,String userId, Integer pageIndex, Integer pageSize,String itemIds) {
		List<TodoMessage> pendList = integrateDao.findTodoMessage(conditionSql,userId, pageIndex, pageSize,itemIds);
		return pendList;
	}
	
	@Override
	public String findTodoMessageOfMobile(String userId, int count, int column, Integer pagesize, 
				String type, String itemIds,String title,String serverUrl){
		List<TodoMessage>  list = integrateDao.findTodoMessageMobile(userId,count,column,pagesize,type,itemIds,title);
		List<Todos> todoList = new ArrayList<Todos>();
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		if(list.size()!=0 && list!=null && !("").equals(list)){
			for (TodoMessage message : list) {
				Todos todo = new Todos();
				todo.setTitle(message.getProcess_title());
				todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(message.getApply_time()));
				todo.setProcessId(message.getWf_process_uid());
				todo.setInstanceId(message.getWf_instance_uid());
				todo.setUserId(userId);
				todo.setFormId(message.getWf_form_id());
				todo.setNodeId(message.getWf_node_uid());
				todo.setWorkFlowId(message.getWf_uid());
				todo.setItemId(message.getWf_item_uid());
				todo.setItemName(message.getVc_sxmc());
				todo.setOwner(message.getOwner());
				todo.setItemType(message.getVc_sxlx()); //0-发文，1-办文，2-传阅
				String path = message.getPdfPath();
				if(!("").equals(path) && path != null){
					path = path.split(",")[0];
					 if(path.startsWith(newPdfRoot)){
							path = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
						}else{
							path = serverUrl+ "/form/html/"+path.substring(path.lastIndexOf("/") + 1);
						}
				}
				todo.setPdfPath(path); 
				todo.setIsMaster(message.getIs_master());
				//todo.setCommentJson(message.getCommentjson() == null ?null:message.getCommentjson().trim());
				todo.setMessage_type(message.getMessage_type());
				todo.setMessageId(message.getMessage_id());
				todo.setSendtime(message.getSendtime());
				todo.setAllInstanceId(message.getAllInstanceid());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}

	@Override
	public int findHavedoMessageCount(String conditionSql, String userId) {
		return integrateDao.findHavedoMessageCount(conditionSql, userId);
	}
	
	@Override
	public String findHavedoMessageOfMobile(String userId, 
			Integer pageIndex, Integer pagesize, String conditionSql, String itemIds,String title , String serverUrl){
		List<TodoMessage>  list = integrateDao.findHavedoMessageOfMobile(userId, pageIndex, pagesize, conditionSql, itemIds, title);
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		List<Todos> todoList = new ArrayList<Todos>();
		if(list.size()!=0 && list!=null && !("").equals(list)){
			for (TodoMessage message : list) {
				Todos todo = new Todos();
				todo.setTitle(message.getProcess_title());
				todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(message.getApply_time()));
				todo.setProcessId(message.getWf_process_uid());
				todo.setInstanceId(message.getWf_instance_uid());
				todo.setUserId(userId);
				todo.setFormId(message.getWf_form_id());
				todo.setNodeId(message.getWf_node_uid());
				todo.setWorkFlowId(message.getWf_uid());
				todo.setItemId(message.getWf_item_uid());
				todo.setItemName(message.getVc_sxmc());
				todo.setOwner(message.getOwner());
				todo.setItemType(message.getVc_sxlx()); //0-发文，1-办文，2-传阅
				String path = message.getPdfPath();
				if(!("").equals(path) && path != null){
					path = path.split(",")[0];
					 if(path.startsWith(newPdfRoot)){
							path = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
						}else{
							path = serverUrl+ "/form/html/"+path.substring(path.lastIndexOf("/") + 1);
						}
				}
				todo.setPdfPath(path); 
				todo.setIsMaster(message.getIs_master());
				todo.setIsfavourite(message.getIsfavourite());
				todo.setStayuserId(message.getStayuserId());
				todo.setMessage_type(message.getMessage_type());
				todo.setMessageId(message.getMessage_id());
				todo.setSendtime(message.getSendtime());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}

	@Override
	public int findMailCount(String conditionSql, String userId) {
		return integrateDao.findMailCount(conditionSql, userId);
	}

	@Override
	public void saveReadedEndPending(ReadedEndPending enity) {
		integrateDao.saveReadedEndPending(enity);
	}


}
