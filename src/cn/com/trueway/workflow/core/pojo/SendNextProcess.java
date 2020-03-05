package cn.com.trueway.workflow.core.pojo;


/**
 * 发送下一步需要的 必要信息
 * @author caiyj
 *
 */
public class SendNextProcess {
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 主送人员
	 */
	private String m_userId;
	
	/**
	 * 抄送人员
	 */
	private String c_userIds;
	
	/**
	 * 操作人员
	 */
	private String userId;
	
	/**
	 * 流程Id
	 */
	private String workFlowId;
	
	/**
	 * 当前节点id
	 */
	private String nodeId;
	
	/**
	 * 当前流程的父流程过程id
	 */
	private	String f_proceId;
	
	/**
	 * 当前步骤的 过程id
	 */
	private String oldProcessId;
	
	/**
	 * 发送的下一节点id
	 */
	private String nextNodeId;
	
	/**
	 * 实例id
	 */
	private String instanceId;
	
	/**
	 * 事项id
	 */
	private String itemId;
	
	/**
	 * 下一节点的表单id
	 */
	private String formId;
	
	/**
	 * 当前表单id
	 */
	private String oldformId;
	
	/**
	 * pdf地址
	 */
	private String pdfPath;
	
	/**
	 * 意见
	 */
	private String trueJson;
	
	/**
	 * 首页表单
	 */
	private String formPage;
	
	/**
	 * 是否为子流程
	 */
	private String isChildWf;
	
	/**
	 * 主送或者抄送
	 */
	private String cType;
	
	/**
	 * 如果为子流程,那么该过程与 与父流程关系--0:异步 1:同步
	 */
	private String relation;
	
	
	/**
	 * 如果为子流程, finstanceId为父流程实例id
	 */
	private String finstanceId;
	
	/**
	 * 第一次进入子流程,入库数据表需要重新生成
	 */
	private String newInstanceIdForChildWf;
	
	/**
	 * 弃用,无特别意义
	 */
	private String specialProcess;
	
	
	/**
	 * middlePdf   中间pdf,留给子流程合并表单使用
	 */
	private String middlePdf;
	
	private String wfc_ctype;
	
	private String childWorkflowId;
	
	private WfProcess oldProcess;
	
	private WfNode childWfFirstNode;
	
	private String isMerge;
	
	private Sw sw; 
	
	private String fromNodeId;
	
	private String wcqx;
	
	private String serverUrl;
	
	private Integer pStepIndex;
	
	public String getWcqx() {
		return wcqx;
	}
	public void setWcqx(String wcqx) {
		this.wcqx = wcqx;
	}
	public SendNextProcess(){
		
	}
	public SendNextProcess(String title,String m_userId,String c_userIds,String userId,
			String workFlowId,String nodeId,String f_proceId,String oldProcessId, 
			String nextNodeId,String instanceId,String itemId,String formId,
			String oldformId,String pdfPath,String trueJson,String formPage,
			String isChildWf,String cType,String relation,
			String finstanceId,String newInstanceIdForChildWf,
			String specialProcess,String middlePdf,String wcqx,String serverUrl ){
		this.wcqx= wcqx;
		this.title = title;
		this.m_userId = m_userId;
		this.c_userIds = c_userIds;
		this.userId = userId;
		this.workFlowId = workFlowId;
		this.nodeId = nodeId;
		this.f_proceId = f_proceId;
		this.oldProcessId = oldProcessId;
		this.nextNodeId = nextNodeId;
		this.instanceId  = instanceId;
		this.itemId = itemId;
		this.formId = formId;
		this.oldformId = oldformId;
		this.pdfPath = pdfPath;
		this.trueJson = trueJson;
		this.formPage = formPage;
		this.isChildWf = isChildWf;
		this.cType = cType;
		this.relation = relation;
		this.finstanceId = finstanceId;
		this.newInstanceIdForChildWf = newInstanceIdForChildWf;
		this.specialProcess = specialProcess;
		this.middlePdf= middlePdf;
		this.serverUrl = serverUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getM_userId() {
		return m_userId;
	}

	public void setM_userId(String m_userId) {
		this.m_userId = m_userId;
	}

	public String getC_userIds() {
		return c_userIds;
	}

	public void setC_userIds(String c_userIds) {
		this.c_userIds = c_userIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getF_proceId() {
		return f_proceId;
	}

	public void setF_proceId(String f_proceId) {
		this.f_proceId = f_proceId;
	}

	public String getOldProcessId() {
		return oldProcessId;
	}

	public void setOldProcessId(String oldProcessId) {
		this.oldProcessId = oldProcessId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getOldformId() {
		return oldformId;
	}

	public void setOldformId(String oldformId) {
		this.oldformId = oldformId;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getTrueJson() {
		return trueJson;
	}

	public void setTrueJson(String trueJson) {
		this.trueJson = trueJson;
	}

	public String getIsChildWf() {
		return isChildWf;
	}

	public void setIsChildWf(String isChildWf) {
		this.isChildWf = isChildWf;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getFinstanceId() {
		return finstanceId;
	}

	public void setFinstanceId(String finstanceId) {
		this.finstanceId = finstanceId;
	}

	public String getNewInstanceIdForChildWf() {
		return newInstanceIdForChildWf;
	}

	public void setNewInstanceIdForChildWf(String newInstanceIdForChildWf) {
		this.newInstanceIdForChildWf = newInstanceIdForChildWf;
	}

	public String getSpecialProcess() {
		return specialProcess;
	}

	public void setSpecialProcess(String specialProcess) {
		this.specialProcess = specialProcess;
	}

	public String getMiddlePdf() {
		return middlePdf;
	}

	public void setMiddlePdf(String middlePdf) {
		this.middlePdf = middlePdf;
	}

	public String getChildWorkflowId() {
		return childWorkflowId;
	}

	public void setChildWorkflowId(String childWorkflowId) {
		this.childWorkflowId = childWorkflowId;
	}
	public WfProcess getOldProcess() {
		return oldProcess;
	}
	public void setOldProcess(WfProcess oldProcess) {
		this.oldProcess = oldProcess;
	}
	public WfNode getChildWfFirstNode() {
		return childWfFirstNode;
	}
	public void setChildWfFirstNode(WfNode childWfFirstNode) {
		this.childWfFirstNode = childWfFirstNode;
	}
	public String getIsMerge() {
		return isMerge;
	}
	public void setIsMerge(String isMerge) {
		this.isMerge = isMerge;
	}
	public Sw getSw() {
		return sw;
	}
	public void setSw(Sw sw) {
		this.sw = sw;
	}
	public String getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public Integer getpStepIndex() {
		return pStepIndex;
	}
	public void setpStepIndex(Integer pStepIndex) {
		this.pStepIndex = pStepIndex;
	}
	public String getWfc_ctype() {
		return wfc_ctype;
	}
	public void setWfc_ctype(String wfc_ctype) {
		this.wfc_ctype = wfc_ctype;
	}
	public String getFormPage() {
		return formPage;
	}
	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}
}
