package cn.com.trueway.workflow.core.pojo.vo;

public class EmpData {
	private String userId;
	
	private String userName;
	
	//private String nodeId;

	public EmpData() {
	}

	public EmpData(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	/*public EmpData(String userId, String userName,String nodeId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.nodeId = nodeId;
	}*/

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

	/*public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}*/
	
}
