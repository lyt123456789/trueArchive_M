package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;
import java.util.List;

public class FreeSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String itemName;
	
	private String itemId;
	
	private String select_condition;
	
	private String select_result;
	
	private List<WfTableInfo> tables;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSelect_condition() {
		return select_condition;
	}

	public void setSelect_condition(String select_condition) {
		this.select_condition = select_condition;
	}

	public String getSelect_result() {
		return select_result;
	}

	public void setSelect_result(String select_result) {
		this.select_result = select_result;
	}

	public List<WfTableInfo> getTables() {
		return tables;
	}

	public void setTables(List<WfTableInfo> tables) {
		this.tables = tables;
	}
	
}
