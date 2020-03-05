package cn.com.trueway.document.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：公文交换系统已收列表与待办过程关联关系表
 * 作者：蔡亚军
 * 创建时间：2014-8-19 下午2:59:29
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_RECEIVE_PROCESS_SHIP")
public class ReceiveProcessShip {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	
	
	@Column(name = "RECID")
	private String recId;	//已收事项id
	
	
	@Column(name = "INSTANCEID")
	private String instanceId;	//过程表中的信息id
	
	@Column(name = "PROCESSID")
	private String processId;	//待办被收入的待办信息processId
	
	@Column(name = "XML")
	private String xml;
	
	@Column(name = "idIndex")
	private String idIndex;
	
	@Transient
	private Integer status;
	
	@Transient
	private String itemName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
