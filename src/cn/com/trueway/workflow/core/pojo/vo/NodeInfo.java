package cn.com.trueway.workflow.core.pojo.vo;

public class NodeInfo {
	private String nodeId;
	
	private String nodeName;

	private String formId;
	
	private String routeType;
	
	private String isHaveGdPerson;//0:没有   1:有
	
	private String m_userIds;
	
	private String c_userIds;
	
	private String operate;//控制保存
	
	private String isSendNow;//直接发送，不用选人员
	
	public NodeInfo() {
	}

	public NodeInfo(String nodeId, String nodeName, String formId,
			String routeType, String isHaveGdPerson, String ids, String ids2,String operate) {
		super();
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.formId = formId;
		this.routeType = routeType;
		this.isHaveGdPerson = isHaveGdPerson;
		m_userIds = ids;
		c_userIds = ids2;
		this.operate = operate;
	}

	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getIsHaveGdPerson() {
		return isHaveGdPerson;
	}
	public void setIsHaveGdPerson(String isHaveGdPerson) {
		this.isHaveGdPerson = isHaveGdPerson;
	}

	public String getM_userIds() {
		return m_userIds;
	}
	public void setM_userIds(String ids) {
		m_userIds = ids;
	}

	public String getC_userIds() {
		return c_userIds;
	}
	public void setC_userIds(String ids) {
		c_userIds = ids;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getIsSendNow() {
		return isSendNow;
	}

	public void setIsSendNow(String isSendNow) {
		this.isSendNow = isSendNow;
	}

}