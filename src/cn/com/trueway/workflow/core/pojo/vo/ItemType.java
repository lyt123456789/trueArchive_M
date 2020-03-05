package cn.com.trueway.workflow.core.pojo.vo;

public class ItemType {
	private String type;
	
	private String name;

	public ItemType() {
	}

	public ItemType(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
