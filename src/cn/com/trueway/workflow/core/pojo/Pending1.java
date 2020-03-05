package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
@Entity
@SqlResultSetMapping(name="PendingResults1", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.workflow.core.pojo.Pending1.class,
            	fields={
	            	@FieldResult(name="wf_process_uid", column="wf_process_uid"),
					@FieldResult(name="wf_node_uid", column="wf_node_uid"),
					@FieldResult(name="wf_instance_uid", column="wf_instance_uid"),
					@FieldResult(name="wf_workflow_uid", column="wf_workflow_uid"),
					@FieldResult(name="process_title", column="process_title"),	
					@FieldResult(name="item_name", column="item_name"),
					@FieldResult(name="item_id", column="item_id"),
					@FieldResult(name="item_type", column="item_type"),
					@FieldResult(name="form_id", column="form_id"),
					@FieldResult(name="apply_time", column="apply_time"),
					@FieldResult(name="finish_time", column="finish_time"),
					@FieldResult(name="isover", column="is_over"),
					@FieldResult(name="remainTime", column="remainTime"),
					@FieldResult(name="warnType", column="warnType"),
					@FieldResult(name="wfn_deadline", column="wfn_deadline"),
					@FieldResult(name="wfn_deadlineunit", column="wfn_deadlineunit"),
					@FieldResult(name="employee_name", column="employee_name"),
					@FieldResult(name="owner", column="owner"),
					@FieldResult(name="entrust_name", column="entrust_name"),
					@FieldResult(name="status", column="status"),
					@FieldResult(name="pdfPath", column="pdfPath"),
					@FieldResult(name="commentJson", column="commentJson"),
					@FieldResult(name="isMaster", column="isMaster"),
					@FieldResult(name="stepIndex", column="stepIndex"),
	            	@FieldResult(name="jdqxDate", column="jdqxDate"),
	            	@FieldResult(name="zhqxDate", column="zhqxDate"),
	            	@FieldResult(name="delay_itemid", column="delay_itemid"),
	            	@FieldResult(name="delay_lcid", column="delay_lcid"),
				    @FieldResult(name="isBack", column="is_back"),
				    @FieldResult(name="isDelaying", column="isDelaying"),
	            	@FieldResult(name="nodeName", column="nodeName"),
	            	@FieldResult(name="isChildWf", column="isChildWf"),
	            	@FieldResult(name="isManyInstance", column="isManyInstance"),
				    @FieldResult(name="sqr", column="sqr"),
				    @FieldResult(name="sqsj", column="sqsj"),
	            	@FieldResult(name="sqnrgy", column="sqnrgy"),
	            	@FieldResult(name="isyq", column="isyq")
                })
        }
)

public class Pending1 {
	@Id
	private String wf_process_uid;	//步骤id
	private String wf_node_uid;		//节点id
	private String wf_instance_uid;	//实例
	private String wf_workflow_uid;	//实例
	private String process_title;	//标题
	private String item_name;	//事项名称
	private String item_id;
	private String item_type;		//事项类型
	private String form_id;
	private Date apply_time;		//接受时间
	private Date finish_time;		//办理时间
	private String isover;			//是否已办
	private String wfn_deadline;	//期限时间
	private String wfn_deadlineunit;	//期限时间单位
	private String remainTime;		//剩余时间
	private String warnType;		//预警类型
	private String nodeName; 		//节点名称
	private String employee_name;	//发送人
	private String owner;			//流程发起人
	private String entrust_name;	//受委托的人
	private String status;			//状态
	private String pdfPath;			//生成的pdf地址
	private String commentJson;		//意见的json数据
	private String isMaster;		//是否是主送--0:抄送 1:主送
	private String stepIndex;
	private Date jdqxDate;
	private Date zhqxDate;
	private String delay_itemid;	//延期事项id
	private String delay_lcid;      //延期的流程id
	private String isBack;          //是否是回收办件
	private Integer isDelaying;		//是否被暂停
	private Integer isChildWf;		//当前是否是子流程的步骤 0：是 1：不是
	private Integer isManyInstance;	//子流程是否是多例  0：是 1：不是
	@Transient
	private String isCanPush; //是否显示推送按钮
	private String sqr;
	private String sqsj;
	private String sqnrgy;
	private String isyq;
	
	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getSqsj() {
		return sqsj;
	}

	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}

	public String getSqnrgy() {
		return sqnrgy;
	}

	public void setSqnrgy(String sqnrgy) {
		this.sqnrgy = sqnrgy;
	}

	public String getIsyq() {
		return isyq;
	}

	public void setIsyq(String isyq) {
		this.isyq = isyq;
	}

	public String getOwner() {
		return owner;
	}

	public String getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(String stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getEntrust_name() {
		return entrust_name;
	}

	public void setEntrust_name(String entrust_name) {
		this.entrust_name = entrust_name;
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

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getWf_workflow_uid() {
		return wf_workflow_uid;
	}

	public void setWf_workflow_uid(String wf_workflow_uid) {
		this.wf_workflow_uid = wf_workflow_uid;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
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

	public Pending1(){
		
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getWf_process_uid() {
		return wf_process_uid;
	}
	public void setWf_process_uid(String wf_process_uid) {
		this.wf_process_uid = wf_process_uid;
	}
	
	public String getWf_node_uid() {
		return wf_node_uid;
	}

	public void setWf_node_uid(String wf_node_uid) {
		this.wf_node_uid = wf_node_uid;
	}

	public String getWf_instance_uid() {
		return wf_instance_uid;
	}
	public void setWf_instance_uid(String wf_instance_uid) {
		this.wf_instance_uid = wf_instance_uid;
	}
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
	public String getIsover() {
		return isover;
	}
	public void setIsover(String isover) {
		this.isover = isover;
	}

	public Date getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public String getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

	public Integer getIsDelaying() {
		return isDelaying;
	}

	public void setIsDelaying(Integer isDelaying) {
		this.isDelaying = isDelaying;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
	
	public String getIsCanPush() {
		return isCanPush;
	}

	public void setIsCanPush(String isCanPush) {
		this.isCanPush = isCanPush;
	}
}
