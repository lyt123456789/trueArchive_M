package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 审批动作表实体
 * @author xuxh
 *
 */
//@Entity
//@Table(name = "WF_Approval")
public class Approval implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -960068532982327058L;
	// Fields
	/**
	 * 唯一标识
	 */
	private String uuid;
	/**
	 * 审批动作名
	 */
	private String name;
	/**
	 * 审批类型 3办结 2 打开表单 1填写意见 0路由
	 */
	private String type;
	
	/**
	 * 兼容字段</br>
	 * 如果审批类型是路由</br>
	 * 则</br>
	 * 是否连续办理 0否 1是</br>
	 * 如果审批类型是填写意见</br>
	 * 则</br>
	 * 意见类别id 用于关联意见表</br>
	 * 如果是打开文档</br>
	 * 则</br>
	 * 关联文档id  数据库有存储关于文档信息的表</br>
	 * 如果是办结 则 什么都不需要
	 */
	private String compatibleField;
	
	/**
	 * 办理条件类型1规则函数 2java脚本 3子类接口
	 */
	private int conditionType;
	
	/**
	 * 办理条件内容
	 */
	private String conditionContent;
	
	/**
	 * 路由关联主键id
	 */
	private String routeId;

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompatibleField() {
		return compatibleField;
	}

	public void setCompatibleField(String compatibleField) {
		this.compatibleField = compatibleField;
	}

	public int getConditionType() {
		return conditionType;
	}

	public void setConditionType(int conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionContent() {
		return conditionContent;
	}

	public void setConditionContent(String conditionContent) {
		this.conditionContent = conditionContent;
	}

	public Approval() {
		super();
	}

	public Approval( String name, String type,
			String compatibleField, int conditionType, String conditionContent) {
		super();
		this.name = name;
		this.type = type;
		this.compatibleField = compatibleField;
		this.conditionType = conditionType;
		this.conditionContent = conditionContent;
	}
	
	//===================以上是新增加的属性==============================//
	
}
