package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_WF_CORE_BACKNODE")
public class WfBackNode {
	/**
	 * 节点id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;	
	
	/**
	 * 起始节点id
	 */
	@Column(name="FORMNODEID",length=100)
	private String fromNodeId;
	
	/**
	 * 指定节点id
	 */
	@Column(name="TONODEID",length=100)
	private String toNodeId;
	
	/**
	 * 流程id
	 */
	@Column(name="WORKFLOWID",length=100)
	private String workflowId;
	
	/**
	 * 起始节点name
	 */
	@Column(name="FROMNODENAME",length=100)
	private String fromNodeName;
	
	/**
	 * 指定节点name
	 */
	@Column(name="TONODENAME",length=100)
	private String toNodeName;
	
	/**
	 * 入库时间
	 */
	@Column(name="DATETIME")
	private Date dateTime;
	
	public WfBackNode() {
	}

	public WfBackNode(String id, String fromNodeId, String toNodeId,
			String workflowId, String fromNodeName, String toNodeName,
			Date dateTime) {
		super();
		this.id = id;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.workflowId = workflowId;
		this.fromNodeName = fromNodeName;
		this.toNodeName = toNodeName;
		this.dateTime = dateTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	public String getToNodeId() {
		return toNodeId;
	}
	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}

	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getFromNodeName() {
		return fromNodeName;
	}
	public void setFromNodeName(String fromNodeName) {
		this.fromNodeName = fromNodeName;
	}

	public String getToNodeName() {
		return toNodeName;
	}
	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}