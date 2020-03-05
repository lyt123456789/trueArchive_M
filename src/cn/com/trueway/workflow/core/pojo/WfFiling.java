package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 归档表映射类
 * @author 赵坚
 *2015年8月18日16:47:34
 */
@Entity
@Table(name="T_WF_CORE_FILING")
public class WfFiling {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	private String id;//id

	@Column(name="INSTANCEID",length=36)
	private String instanceId;//实例id

	@Column(name="PROCESSID",length=36)
	private String processId;//事项id
	
	@Column(name="ITEMID",length=36)
	private String itemId;//事项id
	
	@Column(name="FORMID",length=36)
	private String formId;//事项id

	@Column(name="OPERATOR_ID",length=38)
	private String operatorId;//操作人id

	@Column(name="FILED_TIME")
	private Date filedTime;//归档时间

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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getFiledTime() {
		return filedTime;
	}

	public void setFiledTime(Date filedTime) {
		this.filedTime = filedTime;
	}
	
	
	
}
