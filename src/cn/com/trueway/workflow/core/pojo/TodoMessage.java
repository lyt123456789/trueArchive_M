package cn.com.trueway.workflow.core.pojo;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

@Entity
@SqlResultSetMapping(name="TodoMessageResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.workflow.core.pojo.TodoMessage.class,
            	fields={
            			@FieldResult(name="process_title", column="process_title"),
    	            	@FieldResult(name="apply_time", column="apply_time"),
    	            	@FieldResult(name="wf_process_uid", column="wf_process_uid"),
    	            	@FieldResult(name="wf_instance_uid", column="wf_instance_uid"),
    	            	@FieldResult(name="user_uid", column="user_uid"),
    	            	@FieldResult(name="wf_form_id", column="wf_form_id"),
    	            	@FieldResult(name="wf_node_uid", column="wf_node_uid"),
    	            	@FieldResult(name="wf_uid", column="wf_uid"),
    	            	@FieldResult(name="wf_item_uid", column="wf_item_uid"),
    	            	@FieldResult(name="vc_sxmc", column="vc_sxmc"),
    	            	@FieldResult(name="owner", column="owner"),
    	            	@FieldResult(name="vc_sxlx", column="vc_sxlx"),
    	            	@FieldResult(name="commentjson", column="commentjson"),
    	            	@FieldResult(name="message_type", column="message_type"),
    	            	@FieldResult(name="message_id", column="message_id"),
    	            	@FieldResult(name="pdfPath", column="pdfPath"),
    	            	@FieldResult(name="is_master", column="is_master"),
    	            	@FieldResult(name="IsEnd", column="IsEnd"),
    	            	@FieldResult(name="isfavourite", column="isfavourite"),
    	            	@FieldResult(name="stayuserId", column="stayuserId"),
    	            	@FieldResult(name="userDeptId", column="userDeptId"),
    	            	@FieldResult(name="jdqxDate", column="jdqxDate"),
    	            	@FieldResult(name="employee_name", column="employee_name"),
    	            	@FieldResult(name="item_name", column="item_name"),
    	            	@FieldResult(name="status", column="status"),
    					@FieldResult(name="item_id", column="item_id"),
    					@FieldResult(name="item_type", column="item_type"),
    					@FieldResult(name="form_id", column="form_id"),
    					@FieldResult(name="stepIndex", column="stepIndex"),
    					@FieldResult(name="isChildWf", column="isChildWf"),
    					@FieldResult(name="dofileId", column="dofileId"),
    	            	@FieldResult(name="sendtime", column="sendtime")
            })
        }
)

public class TodoMessage {
	
	private String process_title;	//标题
	
	private Date apply_time;	//申请时间
	
	@Id
	private String wf_process_uid;	
	
	private String wf_instance_uid;	
	
	private String user_uid;	

	private String wf_form_id;	

	private String wf_node_uid;	

	private String wf_uid;	
	
	private String wf_item_uid;	

	private String vc_sxmc;	

	private String owner;	
	
	private String vc_sxlx;	

	private String commentjson;	
	
	private String message_type;	

	private String message_id;
	
	private String pdfPath;
	
	private String is_master;
	
	private String IsEnd;
	
	private String isfavourite;
	
	private String stayuserId;
	
	private String sendtime;
	
	
	private String dofileId;
	
	@Transient
	private Integer isDelaying;		//是否被暂停
	
	
	private String item_id;
	
	
	private String form_id;
	
	@Transient
	private String isCanPush; //是否显示推送按钮
	
	@Transient
	private String wf_workflow_uid;	//实例
	
	private String item_name;	//事项名称
	
	private String item_type;		//事项类型
	@Transient
	private String oldForm_id;
	@Transient
	private Date finish_time;		//办理时间
	@Transient
	private String isover;			//是否已办
	@Transient
	private String wfn_deadline;	//期限时间
	@Transient
	private String wfn_deadlineunit;	//期限时间单位
	@Transient
	private String remainTime;		//剩余时间
	@Transient
	private String warnType;		//预警类型
	@Transient
	private String nodeName; 		//节点名称
	
	private String employee_name;	//发送人
	@Transient
	private String entrust_name;	//受委托的人
	@Transient
	private String isEnd;           // 是否办结
	
	private String status;			//状态
	@Transient
	private String commentJson;		//意见的json数据
	@Transient
	private String isMaster;		//是否是主送--0:抄送 1:主送
	
	private String stepIndex;
	
	private Date jdqxDate;
	@Transient
	private Date zhqxDate;
	@Transient
	private String delay_itemid;	//延期事项id
	@Transient
	private String delay_lcid;      //延期的流程id
	@Transient
	private String isBack;          //是否是回收办件
	
	private Integer isChildWf;		//当前是否是子流程的步骤 0：是 1：不是
	@Transient
	private Integer isManyInstance;	//子流程是否是多例  0：是 1：不是
	@Transient
	private String allInstanceid; 
	@Transient
	private Date jssj; //接收时间
	@Transient
	private String favourite;
	
	private String userDeptId;
	@Transient
	private String relatedItemId;//关联事项ID
	@Transient
	private String continueInstanceId;//续办件的ID
	@Transient
	private String userName;//办理人员
	@Transient
	private String pressStatus;//催办状态位
	@Transient
	private String presscontent;//催办信息
	
	@Transient
	private String isHaveChild; //是否有子流程 0：没有 1：有
	
	@Transient
	private String isDel;//办件是否能被删除 0:否;1:是

	public String getProcess_title() {
		return process_title;
	}

	public void setProcess_title(String process_title) {
		this.process_title = process_title;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public String getWf_process_uid() {
		return wf_process_uid;
	}

	public void setWf_process_uid(String wf_process_uid) {
		this.wf_process_uid = wf_process_uid;
	}

	public String getWf_instance_uid() {
		return wf_instance_uid;
	}

	public void setWf_instance_uid(String wf_instance_uid) {
		this.wf_instance_uid = wf_instance_uid;
	}

	public String getUser_uid() {
		return user_uid;
	}

	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}

	public String getWf_form_id() {
		return wf_form_id;
	}

	public void setWf_form_id(String wf_form_id) {
		this.wf_form_id = wf_form_id;
	}

	public String getWf_node_uid() {
		return wf_node_uid;
	}

	public void setWf_node_uid(String wf_node_uid) {
		this.wf_node_uid = wf_node_uid;
	}

	public String getWf_uid() {
		return wf_uid;
	}

	public void setWf_uid(String wf_uid) {
		this.wf_uid = wf_uid;
	}

	public String getWf_item_uid() {
		return wf_item_uid;
	}

	public void setWf_item_uid(String wf_item_uid) {
		this.wf_item_uid = wf_item_uid;
	}

	public String getVc_sxmc() {
		return vc_sxmc;
	}

	public void setVc_sxmc(String vc_sxmc) {
		this.vc_sxmc = vc_sxmc;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getVc_sxlx() {
		return vc_sxlx;
	}

	public void setVc_sxlx(String vc_sxlx) {
		this.vc_sxlx = vc_sxlx;
	}

	public String getCommentjson() {
		return commentjson;
	}

	public void setCommentjson(String commentjson) {
		this.commentjson = commentjson;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getIs_master() {
		return is_master;
	}

	public void setIs_master(String is_master) {
		this.is_master = is_master;
	}

	public String getIsEnd() {
		return IsEnd;
	}

	public void setIsEnd(String isEnd) {
		IsEnd = isEnd;
	}

	public String getIsfavourite() {
		return isfavourite;
	}

	public void setIsfavourite(String isfavourite) {
		this.isfavourite = isfavourite;
	}

	public String getStayuserId() {
		return stayuserId;
	}

	public void setStayuserId(String stayuserId) {
		this.stayuserId = stayuserId;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getDofileId() {
		return dofileId;
	}

	public void setDofileId(String dofileId) {
		this.dofileId = dofileId;
	}

	public Integer getIsDelaying() {
		return isDelaying;
	}

	public void setIsDelaying(Integer isDelaying) {
		this.isDelaying = isDelaying;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getIsCanPush() {
		return isCanPush;
	}

	public void setIsCanPush(String isCanPush) {
		this.isCanPush = isCanPush;
	}

	public String getWf_workflow_uid() {
		return wf_workflow_uid;
	}

	public void setWf_workflow_uid(String wf_workflow_uid) {
		this.wf_workflow_uid = wf_workflow_uid;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getOldForm_id() {
		return oldForm_id;
	}

	public void setOldForm_id(String oldForm_id) {
		this.oldForm_id = oldForm_id;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public String getIsover() {
		return isover;
	}

	public void setIsover(String isover) {
		this.isover = isover;
	}

	public String getWfn_deadline() {
		return wfn_deadline;
	}

	public void setWfn_deadline(String wfn_deadline) {
		this.wfn_deadline = wfn_deadline;
	}

	public String getWfn_deadlineunit() {
		return wfn_deadlineunit;
	}

	public void setWfn_deadlineunit(String wfn_deadlineunit) {
		this.wfn_deadlineunit = wfn_deadlineunit;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEntrust_name() {
		return entrust_name;
	}

	public void setEntrust_name(String entrust_name) {
		this.entrust_name = entrust_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(String stepIndex) {
		this.stepIndex = stepIndex;
	}

	public Date getJdqxDate() {
		return jdqxDate;
	}

	public void setJdqxDate(Date jdqxDate) {
		this.jdqxDate = jdqxDate;
	}

	public Date getZhqxDate() {
		return zhqxDate;
	}

	public void setZhqxDate(Date zhqxDate) {
		this.zhqxDate = zhqxDate;
	}

	public String getDelay_itemid() {
		return delay_itemid;
	}

	public void setDelay_itemid(String delay_itemid) {
		this.delay_itemid = delay_itemid;
	}

	public String getDelay_lcid() {
		return delay_lcid;
	}

	public void setDelay_lcid(String delay_lcid) {
		this.delay_lcid = delay_lcid;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public Integer getIsChildWf() {
		return isChildWf;
	}

	public void setIsChildWf(Integer isChildWf) {
		this.isChildWf = isChildWf;
	}

	public Integer getIsManyInstance() {
		return isManyInstance;
	}

	public void setIsManyInstance(Integer isManyInstance) {
		this.isManyInstance = isManyInstance;
	}

	public String getAllInstanceid() {
		return allInstanceid;
	}

	public void setAllInstanceid(String allInstanceid) {
		this.allInstanceid = allInstanceid;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public String getFavourite() {
		return favourite;
	}

	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getRelatedItemId() {
		return relatedItemId;
	}

	public void setRelatedItemId(String relatedItemId) {
		this.relatedItemId = relatedItemId;
	}

	public String getContinueInstanceId() {
		return continueInstanceId;
	}

	public void setContinueInstanceId(String continueInstanceId) {
		this.continueInstanceId = continueInstanceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPressStatus() {
		return pressStatus;
	}

	public void setPressStatus(String pressStatus) {
		this.pressStatus = pressStatus;
	}

	public String getPresscontent() {
		return presscontent;
	}

	public void setPresscontent(String presscontent) {
		this.presscontent = presscontent;
	}

	public String getIsHaveChild() {
		return isHaveChild;
	}

	public void setIsHaveChild(String isHaveChild) {
		this.isHaveChild = isHaveChild;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}	
}
