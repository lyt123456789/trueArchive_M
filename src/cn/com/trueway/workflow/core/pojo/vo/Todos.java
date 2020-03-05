package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfItem;

public class Todos
{	
	private String title; 		//待办标题
	private String date;  		//接收时间
	private String userId;		//人员id
	private String processId;	//步骤id
	private String instanceId;	//实例id
	private String formId;		//表单id
	private String nodeId;		//节点id
	private String workFlowId;	//流程id
	private String itemId;		//事项id
	private String itemType;	//事项类型
	private String isMaster;	//是否是主送--0:抄送 1:主送
	private String pdfPath;		//生成的pdf地址
	private String commentJson;	//意见的json数据
	private String isfavourite; //是否已被收藏
	private String itemName; 	// 事项名称
	private String owner;   	// 发起人
	private String stayuserId;	//停留的人员id
	private String messageId;	//信息Id
	private String message_type;//消息类型
	private String sendtime;	//邮件发送时间
	private String isOver;		//是否完成
	private String allInstanceId;//全局实例ID
	
	private String type;//公文类型
	
	private List<WfItem> itemList;//转办事项选择
	private String status;//是否转办的状态
	private String u_Id;//前置机办件id
	
	private String urgency;
	
	private String nowProcessId;//当前步骤的步骤id
	
	private String isBack;
	
	private String stepIndex;
	
	private String isRead;
	
	public Todos(String title, String date, String userId, String processId,
			String instanceId, String formId, String nodeId, String workFlowId,
			String itemId, String itemType, String isMaster, String pdfPath,
			String commentJson,String isfavourite) {
		super();
		this.title = title;
		this.date = date;
		this.userId = userId;
		this.processId = processId;
		this.instanceId = instanceId;
		this.formId = formId;
		this.nodeId = nodeId;
		this.workFlowId = workFlowId;
		this.itemId = itemId;
		this.itemType = itemType;
		this.isMaster = isMaster;
		this.pdfPath = pdfPath;
		this.commentJson = commentJson;
		this.isfavourite = isfavourite;
	}

	public Todos(){
		
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getCommentJson() {
		return commentJson;
	}
	public void setCommentJson(String commentJson) {
		this.commentJson = commentJson;
	}

	public String getIsMaster() {
		return isMaster;
	}
	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}
	
	public String getIsfavourite() {
		return isfavourite;
	}

	public void setIsfavourite(String isfavourite) {
		this.isfavourite = isfavourite;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStayuserId() {
		return stayuserId;
	}

	public void setStayuserId(String stayuserId) {
		this.stayuserId = stayuserId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public String getAllInstanceId() {
		return allInstanceId;
	}

	public void setAllInstanceId(String allInstanceId) {
		this.allInstanceId = allInstanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<WfItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<WfItem> itemList) {
		this.itemList = itemList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getU_Id() {
		return u_Id;
	}

	public void setU_Id(String u_Id) {
		this.u_Id = u_Id;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getNowProcessId() {
		return nowProcessId;
	}

	public void setNowProcessId(String nowProcessId) {
		this.nowProcessId = nowProcessId;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(String stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}