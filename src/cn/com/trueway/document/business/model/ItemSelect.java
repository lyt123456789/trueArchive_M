package cn.com.trueway.document.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "T_WF_CORE_ITEM_SELECT")
public class ItemSelect {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name = "WF_ITEM_UID")
	private String item_id;
	
	@Column(name = "SELECT_CONDITION")
	private String select_condition;
	
	@Column(name = "SELECT_RESULT")
	private String select_result;

	@Column(name = "CONDITION_VALUE")
	private String condition_value;
	
	@Column(name = "RESULT_VALUE")
	private String result_value;
	
	@Column(name = "TYPE")
	private String type ;
	
	@Column(name = "SELECT_ORDERBY")
	private String select_orderBy ;
	
	@Column(name = "ORDERBY_VALUE")
	private String orderBy_value ;
	
	@Column(name = "DAIBANSQL")
	private String daiBanSql ;
	
	@Column(name = "YIBANSQL")
	private String yiBanSql ;
	
	@Column(name = "YIBANJIESQL")
	private String yiBanJieSql ;
	
	@Column(name = "WIDTH")
	private String width ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
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

	public String getCondition_value() {
		return condition_value;
	}

	public void setCondition_value(String condition_value) {
		this.condition_value = condition_value;
	}

	public String getResult_value() {
		return result_value;
	}

	public void setResult_value(String result_value) {
		this.result_value = result_value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelect_orderBy() {
		return select_orderBy;
	}

	public void setSelect_orderBy(String select_orderBy) {
		this.select_orderBy = select_orderBy;
	}

	public String getOrderBy_value() {
		return orderBy_value;
	}

	public void setOrderBy_value(String orderBy_value) {
		this.orderBy_value = orderBy_value;
	}

	public String getDaiBanSql() {
		return daiBanSql;
	}

	public void setDaiBanSql(String daiBanSql) {
		this.daiBanSql = daiBanSql;
	}

	public String getYiBanSql() {
		return yiBanSql;
	}

	public void setYiBanSql(String yiBanSql) {
		this.yiBanSql = yiBanSql;
	}

	public String getYiBanJieSql() {
		return yiBanJieSql;
	}

	public void setYiBanJieSql(String yiBanJieSql) {
		this.yiBanJieSql = yiBanJieSql;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	
	
	
}
