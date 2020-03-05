package cn.com.trueway.workflow.set.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "T_WF_CORE_ALLOW")
public class Allow {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id",length = 36)
	private String id;//主键
	
	@Column(name = "ALLOW_TYPE", nullable = true)
	private String allow_type;//0工作流许可 1任务许可 2审批意见许可
	
	@Column(name = "GLID", nullable = true)
	private String glid;//关联许可id(流程管理员0，流程读者1，工作内容读者2，文件管理员3，及任务id和审批意见id)
	
	@Column(name = "ROLE_TYPE", nullable = true)
	private String role_type;//角色类型(1内置用户2全局用户组3平台用户组4流程用户组5动态角色6人员7本部门8本部门递归)
	
	@Column(name = "ROLE_TYPENAME", nullable = true)
	private String role_typename;//角色类型中文名称
	
	@Column(name = "ROLE_NAME", nullable = true)
	private String role_name;//组名称或人员名称或部门名称
	
	@Column(name = "ROLE_ID", nullable = true)
	private String role_id;//组id或人员id或部门id
	
	@Column(name = "INTIME", nullable = true)
	private Timestamp intime;//入库时间
	
	@Column(name = "INPERSON", nullable = true)
	private String inperson;//入库人
	
	@Column(name = "WORKFLOWID", nullable = true)
	private String workflowid;//工作流id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAllow_type() {
		return allow_type;
	}

	public void setAllow_type(String allow_type) {
		this.allow_type = allow_type;
	}

	public String getGlid() {
		return glid;
	}

	public void setGlid(String glid) {
		this.glid = glid;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getRole_typename() {
		return role_typename;
	}

	public void setRole_typename(String role_typename) {
		this.role_typename = role_typename;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public Timestamp getIntime() {
		return intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	public String getInperson() {
		return inperson;
	}

	public void setInperson(String inperson) {
		this.inperson = inperson;
	}

	public String getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	
	
}
