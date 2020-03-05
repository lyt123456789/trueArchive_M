package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 节点实体
 */
@Entity
@Table(name = "WF_Node")
public class Node implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7014627793517702644L;
	/**
	 * 唯一标识
	 */
	@Id
	@Column(name = "uuid", length = 36)
	private String uuid;
	
	/**
	 * 对应流程的defineId
	 */
	@Column(name = "defineId")
	private String defineId;
	
	/**
	 * 节点名称
	 */
	@Column(name = "nodeName")
	private String nodeName;
	/**
	 * 节点类型（0表示顺序，1表示并行，2表示竞争）
	 */
	@Column(name = "nodeType")
	private Long nodeType;
	
	/**
	 * 对应的表单id
	 */
	@Column(name = "formId")
	private String formId;
	
	/**
	 * 对应表单的中文名
	 */
	@Column(name = "formName")
	private String formName;

	/**
	 * 本步骤对应的表单
	 */
	@Column(name = "formUrl")
	private String formUrl;

	/**
	 * 设置状态 0开始 1运行 2等待 3 结束
	 */
	@Column(name = "status")
	private int status;
	
	/**
	 * 审批动作id  关联审批表多个用，隔开
	 */
	@Column(name = "approvalId")
	private String approvalId;
	
	/**
	 * 是否是风险节点
	 */
	@Column(name = "isRiskNode")
	private int isRiskNode;
	
	/**
	 * 风险节点类别
	 */
	@Column(name = "riskNodeTyoe")
	private String riskNodeTyoe;
	
	/**
	 * 风险节点描述
	 */
	@Column(name = "riskNodeDescribe")
	private String riskNodeDescribe;
	
	/**
	 * 风险节点内控手段和结果
	 */
	@Column(name = "riskNodeControlOfResult")
	private String riskNodeControlOfResult;
	
	/**
	 * 打印该节点的模版id
	 */
	@Column(name = "masterplateId")
	private String masterplateId;
	
	/**
	 * 模版中文名
	 */
	@Column(name = "masterplateName")
	private String masterplateName;
	
	/**
	 * 查看模版权限 0可读写 1只读可查看修改痕迹 2只读 不可查看修改痕迹
	 */
	@Column(name = "masterplatePower")
	private int masterplatePower;
	
	/**
	 * 显示方式0打开表单 1打开正文
	 */
	@Column(name = "displayType")
	private String displayType;
	
	/**
	 * 附加条件表 id 里面包含一些条件
	 */
	@Column(name = "additionalConditionsId")
	private String additionalConditionsId;
	
	/**
	 * 该节点触发子流程id
	 */
	@Column(name = "subFlowId")
	private String subFlowId;
	
	/**
	 * 指定被触发子流程的任务
	 */
	@Column(name = "subFlowOfTask")
	private String subFlowOfTask;
	
	/**
	 * 挂起父流程 0不挂起 1挂起 
	 */
	@Column(name = "suspendedParentFlow")
	private int suspendedParentFlow;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getNodeType() {
		return nodeType;
	}

	public void setNodeType(Long nodeType) {
		this.nodeType = nodeType;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public int getIsRiskNode() {
		return isRiskNode;
	}

	public void setIsRiskNode(int isRiskNode) {
		this.isRiskNode = isRiskNode;
	}

	public String getRiskNodeTyoe() {
		return riskNodeTyoe;
	}

	public void setRiskNodeTyoe(String riskNodeTyoe) {
		this.riskNodeTyoe = riskNodeTyoe;
	}

	public String getRiskNodeDescribe() {
		return riskNodeDescribe;
	}

	public void setRiskNodeDescribe(String riskNodeDescribe) {
		this.riskNodeDescribe = riskNodeDescribe;
	}

	public String getRiskNodeControlOfResult() {
		return riskNodeControlOfResult;
	}

	public void setRiskNodeControlOfResult(String riskNodeControlOfResult) {
		this.riskNodeControlOfResult = riskNodeControlOfResult;
	}

	public String getMasterplateId() {
		return masterplateId;
	}

	public void setMasterplateId(String masterplateId) {
		this.masterplateId = masterplateId;
	}

	public String getMasterplateName() {
		return masterplateName;
	}

	public void setMasterplateName(String masterplateName) {
		this.masterplateName = masterplateName;
	}

	public int getMasterplatePower() {
		return masterplatePower;
	}

	public void setMasterplatePower(int masterplatePower) {
		this.masterplatePower = masterplatePower;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getAdditionalConditionsId() {
		return additionalConditionsId;
	}

	public void setAdditionalConditionsId(String additionalConditionsId) {
		this.additionalConditionsId = additionalConditionsId;
	}

	public String getSubFlowId() {
		return subFlowId;
	}

	public void setSubFlowId(String subFlowId) {
		this.subFlowId = subFlowId;
	}

	public String getSubFlowOfTask() {
		return subFlowOfTask;
	}

	public void setSubFlowOfTask(String subFlowOfTask) {
		this.subFlowOfTask = subFlowOfTask;
	}

	public int getSuspendedParentFlow() {
		return suspendedParentFlow;
	}

	public void setSuspendedParentFlow(int suspendedParentFlow) {
		this.suspendedParentFlow = suspendedParentFlow;
	}

	public Node() {
		super();
	}
	
	//=======================以上是主要添加的属性=======================//

	
	
	
}
