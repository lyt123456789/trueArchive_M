package cn.com.trueway.unifiledUserMng.entity;

import java.io.Serializable;
import java.util.Date;

public class ReturnSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773033757186204344L;

	private String id;
	
	private String name;//站点
	
	private String siteAdminGuid;//站点管理员

	private String siteAdminName;
	
	private String depAdminGuid;//部门管理员
	
	private String depAdminName;
	
	private String empAdminGuid;//人员管理员
	
	private String empAdminName;
	
	private String roleAdminGuid;//角色管理员
	
	private String roleAdminName;
	
	private String fatherDep;
	
	private String seq;
	
	private String code;
	
	private String issite;
	
	private String pname;
	
	private String siteId;
	
	private Date createDate;
	
	private String pId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSiteAdminGuid() {
		return siteAdminGuid;
	}

	public void setSiteAdminGuid(String siteAdminGuid) {
		this.siteAdminGuid = siteAdminGuid;
	}


	public String getSiteAdminName() {
		return siteAdminName;
	}

	public void setSiteAdminName(String siteAdminName) {
		this.siteAdminName = siteAdminName;
	}

	public String getDepAdminGuid() {
		return depAdminGuid;
	}

	public void setDepAdminGuid(String depAdminGuid) {
		this.depAdminGuid = depAdminGuid;
	}

	public String getDepAdminName() {
		return depAdminName;
	}

	public void setDepAdminName(String depAdminName) {
		this.depAdminName = depAdminName;
	}

	public String getEmpAdminGuid() {
		return empAdminGuid;
	}

	public void setEmpAdminGuid(String empAdminGuid) {
		this.empAdminGuid = empAdminGuid;
	}

	public String getEmpAdminName() {
		return empAdminName;
	}

	public void setEmpAdminName(String empAdminName) {
		this.empAdminName = empAdminName;
	}

	public String getRoleAdminGuid() {
		return roleAdminGuid;
	}

	public void setRoleAdminGuid(String roleAdminGuid) {
		this.roleAdminGuid = roleAdminGuid;
	}

	public String getRoleAdminName() {
		return roleAdminName;
	}

	public void setRoleAdminName(String roleAdminName) {
		this.roleAdminName = roleAdminName;
	}

	public String getFatherDep() {
		return fatherDep;
	}

	public void setFatherDep(String fatherDep) {
		this.fatherDep = fatherDep;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIssite() {
		return issite;
	}

	public void setIssite(String issite) {
		this.issite = issite;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
}
