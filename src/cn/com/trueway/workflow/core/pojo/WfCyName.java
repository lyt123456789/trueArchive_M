package cn.com.trueway.workflow.core.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_CYLIST")
public class WfCyName implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5082455473129315711L;

	private String wfCyid;

	private String personName;

	private String officeName;
	
	private String instanceId;

	private String itemId;
	
	private String employeeId;
	
	private String employeeName;
	
	private String employeeShortDn;
	
	private String deptId;
	
	private String deptName;
	
	private Timestamp cyDate;//传阅时间
	
	private String sort;
	
	public WfCyName() {
	}

	public WfCyName(String wfCyid, String personName, String officeName,
			String instanceId, String itemId, String employeeId,
			String employeeName, String employeeShortDn, String deptId,
			String deptName, String sort) {
		super();
		this.wfCyid = wfCyid;
		this.personName = personName;
		this.officeName = officeName;
		this.instanceId = instanceId;
		this.itemId = itemId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeShortDn = employeeShortDn;
		this.deptId = deptId;
		this.deptName = deptName;
		this.sort = sort;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "CYID", unique = true, nullable = false, length = 36)
	public String getWfCyid() {
		return wfCyid;
	}
	public void setWfCyid(String wfCyid) {
		this.wfCyid = wfCyid;
	}

	@Column(name = "PERSON_NAME")
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "OFFICE_NAME")
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@Column(name = "INSTANCE_ID", nullable = false, length = 36)
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "ITEM_ID", nullable = false, length = 36)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "EMPLOYEE_ID")
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Column(name = "EMPLOYEE_SHORTDN")
	public String getEmployeeShortDn() {
		return employeeShortDn;
	}
	public void setEmployeeShortDn(String employeeShortDn) {
		this.employeeShortDn = employeeShortDn;
	}

	@Column(name = "DEPT_ID")
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public Timestamp getCyDate() {
		return cyDate;
	}

	public void setCyDate(Timestamp cyDate) {
		this.cyDate = cyDate;
	}
	
	
	@Column(name = "SORT")
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}