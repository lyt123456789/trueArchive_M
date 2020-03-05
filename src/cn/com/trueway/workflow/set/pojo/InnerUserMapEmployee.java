package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_INNERUSER_MAP_USER")
public class InnerUserMapEmployee {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;//主键
	
	@Column(name = "INNERUSER_ID", nullable = true)
	private String inneruser_id;//用户组id
	
	@Column(name = "EMPLOYEE_ID", nullable = true)
	private String employee_id;//人员id
	
	@Column(name = "EMPLOYEE_NAME", nullable = true)
	private String employee_name;//人员姓名
	
	@Column(name = "EMPLOYEE_SHORTDN", nullable = true)
	private String employee_shortdn;//人员部门结构描述
	
	@Column(name = "DEPARTMENT_ID", nullable = true)
	private String department_id;	//属于的部门Id

	@Column(name = "ZINDEX", nullable = true)
	private Integer zindex;//排序号
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInneruser_id() {
		return inneruser_id;
	}

	public void setInneruser_id(String inneruser_id) {
		this.inneruser_id = inneruser_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_shortdn() {
		return employee_shortdn;
	}

	public void setEmployee_shortdn(String employee_shortdn) {
		this.employee_shortdn = employee_shortdn;
	}

	public Integer getZindex() {
		return zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
}
