package cn.com.trueway.workflow.set.pojo;

import java.io.Serializable;

public class LostAttsDf implements Serializable {

	private static final long serialVersionUID = 6145514495570763707L;
	
	private String dofileId;//办件id
	
	private String instanceId;//实例id
	
	private String title;//办件标题
	
	private String itemId;//事项id
	
	private String itemName;//事项名称
	
	private String itemType;//事项类型
	
	private String userName;//发起人
	
	private String docguid;//附件关联id
	
	private String attIds;//附件ids
	
	private String pages;//附件页码s
	
	private String siteName;//站点名
	
	private String processId;//过程id

	public String getDofileId() {
		return dofileId;
	}

	public void setDofileId(String dofileId) {
		this.dofileId = dofileId;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDocguid() {
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public String getAttIds() {
		return attIds;
	}

	public void setAttIds(String attIds) {
		this.attIds = attIds;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
