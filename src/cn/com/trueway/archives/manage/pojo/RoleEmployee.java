package cn.com.trueway.archives.manage.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色对应菜单表
 * @author lixr
 *
 */
@Entity
@Table(name = "T_SYS_ROLE_EMPLOYEE")
public class RoleEmployee {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="ROLE_ID")
	private String roleId;//角色id
	
	@Column(name="EMPLOYEE_ID")
	private String employeeId;//员工ID
	
	@Column(name="EMPLOYEE_NAME")
	private String employeeName;//员工名字
	
	@Column(name="ROLE_NAME")
	private String rolename;//角色名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
}
