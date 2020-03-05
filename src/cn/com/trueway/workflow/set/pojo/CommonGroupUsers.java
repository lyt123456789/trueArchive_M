package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COMMON_GROUP_USERS")
public class CommonGroupUsers {
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	private String id;	//id
	
	@Column(name = "EMPID", length = 38)
	private String empId;//人员id
	
	@Column(name = "GID", length = 38)
	private String gid;//人员组id
	
	@Column(name = "EMPNAME", length = 200)
	private String empName;//人员姓名
	
	@Column(name = "ORDERINDEX")
	private Integer orderIndex;//排序
	
	@Column(name = "DEPTNAME")
	private String deptName;//部门名称
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "CommonGroupUsers [id=" + id + ", empId=" + empId + ", gid="
				+ gid + ", empName=" + empName + "]";
	}

}
