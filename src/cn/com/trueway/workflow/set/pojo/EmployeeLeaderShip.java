package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：某机构领导的下属人员list
 * 作者：蔡亚军
 * 创建时间：2015-1-13 下午6:00:58
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_EMPLOYEE_LEADERSHIP")
public class EmployeeLeaderShip {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	@Column(name = "DEPLEADERID")
	private String depLeaderId;
	
	@Column(name = "EMPLOYEEID")
	private String employeeId;
	
	@Column(name = "EMPLOYEENAME")
	private String employeeName;
	
	@Column(name = "EMPLOYEESHORTDN")
	private String employeeShortDn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepLeaderId() {
		return depLeaderId;
	}

	public void setDepLeaderId(String depLeaderId) {
		this.depLeaderId = depLeaderId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeShortDn() {
		return employeeShortDn;
	}

	public void setEmployeeShortDn(String employeeShortDn) {
		this.employeeShortDn = employeeShortDn;
	}
	
}
