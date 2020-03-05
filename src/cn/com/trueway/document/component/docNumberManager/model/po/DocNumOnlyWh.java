package cn.com.trueway.document.component.docNumberManager.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对DocNumOnlyWh进行描述
 * 作者：蒋烽
 * 创建时间：2017-6-29 上午9:44:55
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_WH")
public class DocNumOnlyWh {
	private String id;
	
	/**
	 * 流程ID
	 */
	private String workflowid;
	
	/**
	 * 事项ID
	 */
	private String itemid;
	
	/**
	 * 
	 */
	private Integer wh;
	
	/**
	 * 该文号是否使用
	 */
	private String isUsed;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "WORKFLOWID")
	public String getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	@Column(name = "ITEMID")
	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	@Column(name = "WH")
	public Integer getWh() {
		return wh;
	}

	public void setWh(Integer wh) {
		this.wh = wh;
	}

	@Column(name = "ISUESD")
	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
}
