package cn.com.trueway.workflow.core.pojo;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import cn.com.trueway.document.component.taglib.comment.model.po.Comment;

@SqlResultSetMapping(name="GetProcessResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.workflow.core.pojo.GetProcess.class,
            	fields={
	            	@FieldResult(name="wfProcessUid", column="wfProcessUid"),
					@FieldResult(name="wfInstanceUid", column="wfInstanceUid"),
					@FieldResult(name="wf_uid", column="wf_uid"),
					@FieldResult(name="nodeName", column="nodeName"),
					@FieldResult(name="nodeId", column="nodeId"),
					@FieldResult(name="fromUserName", column="fromUserName"),
					@FieldResult(name="userId", column="userId"),
					@FieldResult(name="userName", column="userName"),
					@FieldResult(name="applyTime", column="applyTime"),
					@FieldResult(name="finshTime", column="finshTime"),
					@FieldResult(name="stepIndex", column="stepIndex"),
					@FieldResult(name="isEnd", column="isEnd"),
					@FieldResult(name="formId", column="formId"),
					@FieldResult(name="isBack", column="is_back"),
					@FieldResult(name="isHaveChild", column="isHaveChild"),
					@FieldResult(name="fromNodeId", column="fromNodeId"),
					@FieldResult(name="toNodeId", column="toNodeId"),
					@FieldResult(name="push_childprocess", column="push_childprocess"),
					@FieldResult(name="is_merge", column="is_merge"),
					@FieldResult(name="doType", column="doType"),
					@FieldResult(name="f_instanceId", column="f_instanceId"),
					@FieldResult(name="jssj", column="jssj"),
					@FieldResult(name="isRedirect", column="isRedirect"),
	            	@FieldResult(name="pdfPath", column="pdfPath"),
	            	@FieldResult(name="workflowId", column="workflowId"),
	            	@FieldResult(name="isOver", column="isOver"),
	            	@FieldResult(name="fjbProcessId", column="fjbProcessId"),
	            	@FieldResult(name="isReturnStep", column="isReturnStep")
                })
        }
)
@Entity
public class GetProcess
{	
	public void setDoType(Integer doType) {
		this.doType = doType;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	@Id
	private String wfProcessUid;
	private String wfInstanceUid;
	private String wf_uid;
	private String userId;
	private String userName;
	private String nodeName;
	private String nodeId;
	private Date applyTime;
	private Date finshTime;
	private String fromUserName;
	private String stepIndex;
	private String isEnd;
	private String formId;
	private String isBack; 
	@Transient
	private List<Comment> commentList;
	private String isHaveChild; //是否有子流程
	private String fromNodeId;
	private String toNodeId;
	private String push_childprocess;
	private String is_merge;
	private Integer doType;
	private String f_instanceId;
	private Date jssj;
	private Integer isRedirect;
	//添加 pdfpath 已办的 path
	private String pdfPath;
	//workflow id
	private String workflowId;
	private String fjbProcessId;
	
	private String isOver;
	
	private Integer isReturnStep;
	
	@Transient
	private String str_applyTime;
	@Transient
	private String str_finshTime;
	@Transient
	private String str_jssj;
	@Transient
	private String depName;
	@Transient
	private String commentText;
	
	public GetProcess(){
		
	}
	public GetProcess(String wfProcessUid, String wfInstanceUid, String wf_uid,
			String userId, String userName, String nodeName, String nodeId,
			Date applyTime, Date finshTime, String fromUserName,
			String stepIndex, String isEnd, String formId, String isBack,
			List<Comment> commentList, String isHaveChild, String fromNodeId,
			String toNodeId, String push_childprocess, String is_merge,
			Integer doType, String id,Date jssj,Integer isRedirect,String pdfPath,
			String workflowId, String isOver,String fjbProcessId) {
		super();
		this.wfProcessUid = wfProcessUid;
		this.wfInstanceUid = wfInstanceUid;
		this.wf_uid = wf_uid;
		this.userId = userId;
		this.userName = userName;
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.applyTime = applyTime;
		this.finshTime = finshTime;
		this.fromUserName = fromUserName;
		this.stepIndex = stepIndex;
		this.isEnd = isEnd;
		this.formId = formId;
		this.isBack = isBack;
		this.commentList = commentList;
		this.isHaveChild = isHaveChild;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.push_childprocess = push_childprocess;
		this.is_merge = is_merge;
		this.doType = doType;
		f_instanceId = id;
		this.jssj = jssj;
		this.isRedirect = isRedirect;
		this.pdfPath = pdfPath;
		this.workflowId = workflowId;
		this.isOver = isOver;
		this.fjbProcessId = fjbProcessId;
	}

	public String getWfProcessUid() {
		return wfProcessUid;
	}

	public void setWfProcessUid(String wfProcessUid) {
		this.wfProcessUid = wfProcessUid;
	}

	public String getWfInstanceUid() {
		return wfInstanceUid;
	}
	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}
	public void setWfInstanceUid(String wfInstanceUid) {
		this.wfInstanceUid = wfInstanceUid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getFinshTime() {
		return finshTime;
	}

	public void setFinshTime(Date finshTime) {
		this.finshTime = finshTime;
	}


	public String getFjbProcessId() {
		return fjbProcessId;
	}
	public void setFjbProcessId(String fjbProcessId) {
		this.fjbProcessId = fjbProcessId;
	}
	public String getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(String stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getIsHaveChild() {
		return isHaveChild;
	}

	public void setIsHaveChild(String isHaveChild) {
		this.isHaveChild = isHaveChild;
	}

	public String getFromNodeId() {
		return fromNodeId;
	}

	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	public String getToNodeId() {
		return toNodeId;
	}

	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}

	public String getF_instanceId() {
		return f_instanceId;
	}

	public void setF_instanceId(String f_instanceId) {
		this.f_instanceId = f_instanceId;
	}

	public String getPush_childprocess() {
		return push_childprocess;
	}

	public void setPush_childprocess(String push_childprocess) {
		this.push_childprocess = push_childprocess;
	}

	public String getIs_merge() {
		return is_merge;
	}

	public void setIs_merge(String is_merge) {
		this.is_merge = is_merge;
	}

	public String getWf_uid() {
		return wf_uid;
	}

	public void setWf_uid(String wf_uid) {
		this.wf_uid = wf_uid;
	}

	public Integer getDoType() {
		return doType;
	}

	public void Integer(Integer doType) {
		this.doType = doType;
	}

	public Integer getIsRedirect() {
		return isRedirect;
	}

	public void setIsRedirect(Integer isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getIsOver() {
		return isOver;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	public String getStr_applyTime() {
		return str_applyTime;
	}
	public void setStr_applyTime(String str_applyTime) {
		this.str_applyTime = str_applyTime;
	}
	public String getStr_finshTime() {
		return str_finshTime;
	}
	public void setStr_finshTime(String str_finshTime) {
		this.str_finshTime = str_finshTime;
	}
	public String getStr_jssj() {
		return str_jssj;
	}
	public void setStr_jssj(String str_jssj) {
		this.str_jssj = str_jssj;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Integer getIsReturnStep() {
		return isReturnStep;
	}
	public void setIsReturnStep(Integer isReturnStep) {
		this.isReturnStep = isReturnStep;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}
