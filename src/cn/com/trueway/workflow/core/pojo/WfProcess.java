package cn.com.trueway.workflow.core.pojo;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "T_WF_PROCESS")
public class WfProcess implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3601053263282892187L;
	/**
	 * 唯一标识
	 */
	private String wfProcessUid;
	/**
	 * 实例标识
	 */
	private String wfInstanceUid;
	/**
	 * 接收人的Id
	 */
	private String userUid;
	
	/**
	 * 节点ID
	 */
	private String nodeUid;
	/**
	 * 接收时间
	 */
	private Date applyTime;
	/**
	 * 完成时间
	 */
	private Date finshTime;
	/**
	 * 发送人的Id
	 */
	private String fromUserId;
	
	/**
	 * 这个流程的发起人
	 */
	private String owner;
	
	/**
	 * 本步是否已经处理（OVER表示已处理，NOT_OVER表示没有处理）
	 */
	private String isOver;
	
	/**
	 * 本步骤对应待办事件的名称
	 */
	private String processTitle;
	
	/**
	 * 本流程是否结束,1表示结束,0表示未结束
	 */
	private Integer isEnd; 
	
	/**
	 * 是否是主送人  1 主送人 ，0 抄送人
	 */
	private Integer isMaster;
	
	/**
	 * 流程ID
	 */
	private String wfUid;

	/**
	 * 是否显示该步骤(当并行完全式，所有办理人都办理后显示)
	 */
	private Integer isShow;
	
	/**
	 * 步骤
	 */
	private Integer stepIndex;
	
	/**
	 * 事项id
	 */
	private String itemId;
	
	/**
	 * 表单ID
	 */
	private String formId;
	
	/**
	 * 旧表单ID
	 */
	private String oldFormId;
	
	/**
	 * 是否是抄送(辅助功能),只有查看权限
	 */
	private String isDuplicate;
	
	/**
	 * 委托人id
	 */
	private String entrustUserId;
	/**
	 * 来自的节点
	 */
	private String fromNodeid;
	/**
	 * 发送的节点
	 */
	private String toNodeid;
	/**
	 * 特殊步骤的名字
	 */
	private String nodeName;
	/**
	 * 节点发送的下一个人(针对港闸的流程而言)
	 */
	private String sendPersonId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * pdf地址
	 */
	private String pdfPath;
	/**
	 * 意见的json
	 */
	private String commentJson;
	/**
	 * 父实例id
	 */
	private String fInstancdUid;
	
	/**
	 * 该节点的最后时间期限
	 */
	private Date jdqxDate;
	
	/**
	 * 该办件的最后时间
	 */
	private Date zhqxDate;
	
	/**
	 * 办件处理待办或者等办理
	 */
	private Integer action_status;
	
	/**
	 * 当前是否是子流程的步骤
	 */
	private String isChildWf;
	
	/**
	 * 子流程是否是多例
	 */
	private String isManyInstance;
	
	/**
	 * 若该流程为子流程, pstepIndex记录 执行该步骤的父流程步骤
	 */
	private Integer pStepIndex; 
	
	/**
	 * 被推送的那条待办记录
	 */
	private String push_childProcess; 
	
	/**
	 * 被推送的那条待办是否被合并
	 */
	private Integer isMerge; 
	
	/**
	 * 该过程信息是否正在待收处理中
	 */
	private Integer isExchanging;

	/**
	 * 是否已阅
	 */
	private Integer isyy;
	/**
	 * 全局instancedId，用于换表单，统一附件
	 */
	private String allInstanceid;
	
	/**
	 * 办理类型--1：主办、2：协办、3:中间流程
	 */
	private Integer doType;
	
	/**
	 * 是否回收
	 */
	private String isBack;
	/**
	 * 是否超期提醒，0：不是，1：是
	 */
	private Integer iscqfs;
	
	/**
	 * 是否重新进行签批
	 */
	private Integer sfqp;
	
	/**
	 * 合并意见之后的临时的htmlpath,以便办结是不用重新生成
	 */
	private String tempHtmlPath;
	/**
	 * 发起交办的 父流程过程id
	 */
	@Column(name = "FJBPROCESSID")
	private String fjbProcessId;
	
	/**
	 * 办件处理人员当前部门id
	 */
	@Column(name = "FJBPROCESSID")
	private String userDeptId;
	/**
	 * 接收时间
	 */
	private Date jssj;
	
	/**
	 * 接收者用户姓名
	 */
	private String userName;
	
	/**
	 * 是否收藏
	 */
	private String favourite;
	
	private Integer isRedirect;
	
	/**
	 * formPage
	 */
	private String  formPage;
	
	/**
	 * 附件地址路径
	 */
	private String 	attachPath;
	
	/**
	 * 续办的新办件ID
	 */
	@Column(name = "CONTINUEINSTANCEID")
	private String continueInstanceId;

	/**
	 * 同一步时:排列次序
	 */
	private Integer sort;
	
	/**
	 * 是否为分批步骤
	 */
	@Column(name = "ISREPEATED")
	private Integer isrepeated;
	
	/**
	 * 是否为退回步骤(1,退回;2,被退回)
	 */
	@Column(name = "ISRETURNSTEP")
	private Integer isReturnStep;
	
	/**
	 * 退回意见
	 */
	@Column(name = "RETURNMESSAGE")
	private String returnMessage;
	
	/**
	 * 待办窗口是否打开
	 */
	@Column(name = "ISOPEN")
	private Integer isOpen;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JSSJ", length = 7)
	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	/**
	 * pdf来源于的过程id
	 */
	private String mergePdf;
	
	@Column(name = "isyy", nullable = true, length = 2)
	public Integer getIsyy() {
		return isyy;
	}

	public void setIsyy(Integer isyy) {
		this.isyy = isyy;
	}

	@Id
	@Column(name = "WF_PROCESS_UID")
	public String getWfProcessUid() {
		return this.wfProcessUid;
	}  

	public void setWfProcessUid(String wfProcessUid) {
		this.wfProcessUid = wfProcessUid;
	}

	@Column(name = "WF_INSTANCE_UID", nullable = false, length = 36)
	public String getWfInstanceUid() {
		return this.wfInstanceUid;
	}

	public void setWfInstanceUid(String wfInstanceUid) {
		this.wfInstanceUid = wfInstanceUid;
	}

	@Column(name = "USER_UID", nullable = false, length = 38)
	public String getUserUid() {
		return this.userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME", length = 7)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINSH_TIME", length = 7)
	public Date getFinshTime() {
		return this.finshTime;
	}

	public void setFinshTime(Date finshTime) {
		this.finshTime = finshTime;
	}

	@Column(name = "FROMUSERID", nullable = true, length = 38)
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	@Column(name = "OWNER", nullable = true, length = 38)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "IS_OVER", nullable = true, length = 255)
	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	@Column(name = "PROCESS_TITLE", nullable = true, length = 1000)
	public String getProcessTitle() {
		return processTitle;
	}

	public void setProcessTitle(String processTitle) {
		this.processTitle = processTitle;
	}

	@Column(name = "IS_END", nullable = true, precision = 2, scale = 0)
	public Integer getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}
	
	@Column(name = "WF_NODE_UID", nullable = true, length = 38)
	public String getNodeUid() {
		return nodeUid;
	}

	public void setNodeUid(String nodeUid) {
		this.nodeUid = nodeUid;
	}
	@Column(name = "IS_MASTER", nullable = true, precision = 2, scale = 0)
	public Integer getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Integer isMaster) {
		this.isMaster = isMaster;
	}
	
	@Column(name = "WF_UID", nullable = true, length = 36)
	public String getWfUid() {
		return wfUid;
	}

	public void setWfUid(String wfUid) {
		this.wfUid = wfUid;
	}
	@Column(name = "IS_SHOW", nullable = true, precision = 2, scale = 0)
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	@Column(name = "STEP_INDEX", nullable = true)
	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}

	@Column(name = "WF_ITEM_UID", nullable = true, length = 36)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "WF_FORM_ID", nullable = true, length = 36)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name = "WF_OLDFORM_ID", nullable = true, length = 36)
	public String getOldFormId() {
		return oldFormId;
	}

	public void setOldFormId(String oldFormId) {
		this.oldFormId = oldFormId;
	}

	@Column(name = "IS_DUPLICATE", nullable = true, length = 10)
	public String getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(String isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	@Column(name = "ENTRUSTUSERID", nullable = true, length = 38)
	public String getEntrustUserId() {
		return entrustUserId;
	}

	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}

	@Column(name = "FROMNODEID", nullable = true, length = 36)
	public String getFromNodeid() {
		return fromNodeid;
	}
	public void setFromNodeid(String fromNodeid) {
		this.fromNodeid = fromNodeid;
	}

	@Column(name = "TONODEID", nullable = true, length = 36)
	public String getToNodeid() {
		return toNodeid;
	}
	public void setToNodeid(String toNodeid) {
		this.toNodeid = toNodeid;
	}

	@Column(name = "NODENAME", nullable = true)
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "SENDPERSONID", nullable = true)
	public String getSendPersonId() {
		return sendPersonId;
	}
	public void setSendPersonId(String sendPersonId) {
		this.sendPersonId = sendPersonId;
	}

	@Column(name = "STATUS", nullable = true)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PDFPATH", nullable = true)
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	@Column(name = "COMMENTJSON", nullable = true)
	public String getCommentJson() {
		return commentJson;
	}
	public void setCommentJson(String commentJson) {
		this.commentJson = commentJson;
	}

	@Column(name = "JDQXDATE", nullable = true)
	public Date getJdqxDate() {
		return jdqxDate;
	}

	public void setJdqxDate(Date jdqxDate) {
		this.jdqxDate = jdqxDate;
	}

	@Column(name = "ZHQXDATE", nullable = true)
	public Date getZhqxDate() {
		return zhqxDate;
	}

	public void setZhqxDate(Date zhqxDate) {
		this.zhqxDate = zhqxDate;
	}
	
	@Column(name = "WF_F_INSTANCE_UID", nullable = true)
	public String getfInstancdUid() {
		return fInstancdUid;
	}

	public void setfInstancdUid(String fInstancdUid) {
		this.fInstancdUid = fInstancdUid;
	}

	@Column(name = "ACTION_STATUS", nullable = true)
	public Integer getAction_status() {
		return action_status;
	}

	public void setAction_status(Integer action_status) {
		this.action_status = action_status;
	}

	@Column(name = "ISCHILDWF", nullable = true)
	public String getIsChildWf() {
		return isChildWf;
	}

	public void setIsChildWf(String isChildWf) {
		this.isChildWf = isChildWf;
	}

	@Column(name = "ISMANYINSTANCE", nullable = true)
	public String getIsManyInstance() {
		return isManyInstance;
	}

	public void setIsManyInstance(String isManyInstance) {
		this.isManyInstance = isManyInstance;
	}

	@Column(name = "PSTEPINDEX", nullable = true)
	public Integer getpStepIndex() {
		return pStepIndex;
	}

	public void setpStepIndex(Integer pStepIndex) {
		this.pStepIndex = pStepIndex;
	}

	@Column(name = "PUSH_CHILDPROCESS", nullable = true)
	public String getPush_childProcess() {
		return push_childProcess;
	}

	public void setPush_childProcess(String push_childProcess) {
		this.push_childProcess = push_childProcess;
	}

	@Column(name = "IS_MERGE", nullable = true)
	public Integer getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(Integer isMerge) {
		this.isMerge = isMerge;
	}
	

	@Column(name = "ISEXCHANGING", nullable = true)
	public Integer getIsExchanging() {
		return isExchanging;
	}

	public void setIsExchanging(Integer isExchanging) {
		this.isExchanging = isExchanging;
	}

	@Column(name = "ALLINSTANCEID", nullable = true)
	public String getAllInstanceid() {
		return allInstanceid;
	}
	public void setAllInstanceid(String allInstanceid) {
		this.allInstanceid = allInstanceid;
	}

	@Column(name = "DOTYPE", nullable = true)
	public Integer getDoType() {
		return doType;
	}
	public void setDoType(Integer doType) {
		this.doType = doType;
	}

	@Column(name = "IS_BACK", nullable = true)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	
	@Column(name = "MERGEPDF", nullable = true)
	public String getMergePdf() {
		return mergePdf;
	}

	public void setMergePdf(String mergePdf) {
		this.mergePdf = mergePdf;
	}
	
	@Column(name = "ISREDIRECT", nullable = true)
	public Integer getIsRedirect() {
		return isRedirect;
	}

	public void setIsRedirect(Integer isRedirect) {
		this.isRedirect = isRedirect;
	}

	@Transient
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@Column(name = "SFQP", nullable = true)
	public Integer getSfqp() {
		return sfqp;
	}

	public void setSfqp(Integer sfqp) {
		this.sfqp = sfqp;
	}

	public String getFjbProcessId() {
		return fjbProcessId;
	}

	public void setFjbProcessId(String fjbProcessId) {
		this.fjbProcessId = fjbProcessId;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}
	@Column(name = "TEMPHTMLPATH", nullable = true)
	public String getTempHtmlPath() {
		return tempHtmlPath;
	}

	public void setTempHtmlPath(String tempHtmlPath) {
		this.tempHtmlPath = tempHtmlPath;
	}
	
	@Column(name = "FORMPAGE", nullable = true)
	public String getFormPage() {
		return formPage;
	}

	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}
	
	@Column(name = "ATTACHPATH", nullable = true)
	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	@Transient
	public String getFavourite() {
		return favourite;
	}

	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}

	public String getContinueInstanceId() {
		return continueInstanceId;
	}

	public void setContinueInstanceId(String continueInstanceId) {
		this.continueInstanceId = continueInstanceId;
	}

	@Column(name = "SORT", nullable = true)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsrepeated() {
		return isrepeated;
	}

	public void setIsrepeated(Integer isrepeated) {
		this.isrepeated = isrepeated;
	}

	public Integer getIsReturnStep() {
		return isReturnStep;
	}

	public void setIsReturnStep(Integer isReturnStep) {
		this.isReturnStep = isReturnStep;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	
}