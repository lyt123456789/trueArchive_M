package cn.com.trueway.workflow.set.pojo;

import java.io.Serializable;

/**
 * 丢失意见办件实体类
 * @author jszw
 *
 */
public class LostCmtDf implements Serializable{
	private static final long serialVersionUID = -7769524894507930791L;
	
	private String id;
	
	private String instanceId;
	
	private String title;
	
	private String processIds;
	
	private String nodeId;
	
	private String userIds;
	
	private String userNames;
	
	private String nodeName;
	
	private String siteName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcessIds() {
		return processIds;
	}

	public void setProcessIds(String processIds) {
		this.processIds = processIds;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
