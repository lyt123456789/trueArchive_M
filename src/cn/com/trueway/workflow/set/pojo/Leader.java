package cn.com.trueway.workflow.set.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_LEADER")
public class Leader {
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	private String id;	//id
	
	@Column(name = "CREATE_TIME")
	private Date createTime;//创建时间
	
	@Column(name = "EMPLOYEE_GUID", length = 38)
	private String employeeGuid;//人员组id
	
	@Column(name = "EMPLOYEE_NAME", length = 38)
	private String employeeName;//人员姓名

	
	@Column(name = "SITEID")
	private String siteId;//站点id
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}


	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
