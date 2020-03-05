package cn.com.trueway.sys.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_SYS_ROLE_USER")
public class RoleUser implements Serializable{
	
	private static final long serialVersionUID = 4344930951879057108L;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "GUID", unique = true, nullable = false, length = 38)
	private String guId;
	
	@Column(name = "ROLE_ID")
	private String roleId;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "DEPT_ID")
	private String deptId;
	
	@Column(name = "DEPT_NAME")
	private String deptName;

	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
