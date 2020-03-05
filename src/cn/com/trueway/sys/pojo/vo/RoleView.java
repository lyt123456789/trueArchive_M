package cn.com.trueway.sys.pojo.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROAL_VIEW")
public class RoleView {
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;// VARCHAR2(38) N 角色id

	@Column(name = "ROLE_NAME")
	private String roleName;// VARCHAR2(200) Y 角色名

	@Column(name = "ROLE_SORT")
	private BigDecimal roleSort;// NUMBER Y 排序号
	
	@Column(name = "ROLE_STATUS")
	private BigDecimal roleStatus; 	//NUMBER	Y	1		角色状态，1：启用; 0：禁用
	
	@Column(name = "SITE_ID")
	private String siteId; 	//String 站点id
	
	@Column(name = "ID")
	private String id;   //String    站点表id
	
	@Column(name = "SITENAME")
	private String siteName;  //String 站点名称
	
	@Column(name = "SITEID")
	private String site_Id;  //String 站点id
	
	@Column(name = "SITEPASSWORD")
	private String sitePassword;  //String  站点密码
	
	@Column(name = "SITEPASSWORDMD5")
	private String sitePasswordMd5;  //String 站点密码md5

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public BigDecimal getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(BigDecimal roleSort) {
		this.roleSort = roleSort;
	}

	public BigDecimal getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(BigDecimal roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSite_Id() {
		return site_Id;
	}

	public void setSite_Id(String site_Id) {
		this.site_Id = site_Id;
	}

	public String getSitePassword() {
		return sitePassword;
	}

	public void setSitePassword(String sitePassword) {
		this.sitePassword = sitePassword;
	}

	public String getSitePasswordMd5() {
		return sitePasswordMd5;
	}

	public void setSitePasswordMd5(String sitePasswordMd5) {
		this.sitePasswordMd5 = sitePasswordMd5;
	}
	
	
}
