package cn.com.trueway.workflow.set.pojo.vo;

import java.util.List;
import java.util.Set;

public class UserNodesPermission {

	private String name;
	
	private List<String> itemSet;
	
	private List<Set<String>> nodePermission;
	
	private int itemSize;
	
	public UserNodesPermission(){
	}
	
	public UserNodesPermission(String name,List<String> itemSet,List<Set<String>> nodePermission){
		this.name = name;
		this.itemSet = itemSet;
		this.nodePermission = nodePermission;
		this.itemSize  = itemSet.size();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getItemSet() {
		return itemSet;
	}

	public void setItemSet(List<String> itemSet) {
		this.itemSet = itemSet;
	}

	public List<Set<String>> getNodePermission() {
		return nodePermission;
	}

	public void setNodePermission(List<Set<String>> nodePermission) {
		this.nodePermission = nodePermission;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	@Override
	public String toString() {
		return "UserNodesPermission [name=" + name + ", itemSet=" + itemSet.toString()
				+ ", nodePermission=" + nodePermission.toString() + "]";
	}
	
}

