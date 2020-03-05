package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_OFFICE_TEST")
public class WfTest {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="WORKFLOW_INSTANCE_ID")
	private String workflow_instance_id;
	
	@Column(name="FORM_ID")
	private String form_id;
		
	@Column(name="DEPT")
	private String dept;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="THINGNAME")
	private String thingName;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="WORKFLOWID")
	private String workflowId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getThingName() {
		return thingName;
	}

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWorkflow_instance_id() {
		return workflow_instance_id;
	}

	public void setWorkflow_instance_id(String workflow_instance_id) {
		this.workflow_instance_id = workflow_instance_id;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

}
