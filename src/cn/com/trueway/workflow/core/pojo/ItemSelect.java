package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


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
	
}
