package cn.com.trueway.document.business.model;

import java.io.Serializable;
import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfTableInfo;

/**
 * 
 * 描述：自定义查询<br>
 * 作者：张灵<br>
 * 创建时间：2016-1-27 下午05:12:18<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class FreeSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private String itemName;
	
	private String itemId;
	
	private String select_condition;
	
	private String select_result;
	
	private String select_orderBy;
	
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
	
	public String getSelect_orderBy() {
		return select_orderBy;
	}

	public void setSelect_orderBy(String select_orderBy) {
		this.select_orderBy = select_orderBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<WfTableInfo> getTables() {
		return tables;
	}

	public void setTables(List<WfTableInfo> tables) {
		this.tables = tables;
	}
	
}
