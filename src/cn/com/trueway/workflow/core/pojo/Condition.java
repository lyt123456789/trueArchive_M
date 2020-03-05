package cn.com.trueway.workflow.core.pojo;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 条件表实体
 * @author xuxh
 *
 */
//@Entity
//@Table(name = "WF_Condition")
public class Condition implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7014627793517702644L;
	/**
	 * 唯一标识
	 */
	private String uuid;
	
	/**
	 * 自由流程 0否 1是
	 */
	private int isFreeFlow;
	
	/**
	 * 委托 0否 1是
	 */
	private int isEntrust;
	/**
	 * 拒绝 0否 1是
	 */
	private int isRefuse;
	
	/**
	 * 协商 0否 1是
	 */
	private int isConsult;
	
	/**
	 * 抄送 0否 1是
	 */
	private int isCarbonCopy;

	/**
	 * 退回 0否 1是
	 */
	private int isReturn;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getIsFreeFlow() {
		return isFreeFlow;
	}

	public void setIsFreeFlow(int isFreeFlow) {
		this.isFreeFlow = isFreeFlow;
	}

	public int getIsEntrust() {
		return isEntrust;
	}

	public void setIsEntrust(int isEntrust) {
		this.isEntrust = isEntrust;
	}

	public int getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(int isRefuse) {
		this.isRefuse = isRefuse;
	}

	public int getIsConsult() {
		return isConsult;
	}

	public void setIsConsult(int isConsult) {
		this.isConsult = isConsult;
	}

	public int getIsCarbonCopy() {
		return isCarbonCopy;
	}

	public void setIsCarbonCopy(int isCarbonCopy) {
		this.isCarbonCopy = isCarbonCopy;
	}

	public int getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(int isReturn) {
		this.isReturn = isReturn;
	}

	public Condition() {
		super();
	}

	
}
