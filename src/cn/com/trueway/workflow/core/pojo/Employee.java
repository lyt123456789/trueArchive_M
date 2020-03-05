package cn.com.trueway.workflow.core.pojo;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 描述：人员表
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 2:43:01 PM
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "ZWKJ_EMPLOYEE")
public class Employee implements java.io.Serializable {

	// FieldsORDERINDEX

	private static final long serialVersionUID = 3400274636541562604L;
	private String employeeGuid;
	private String departmentGuid;
	private String employeeLoginname;
	private String employeePassword;
	private String employeeName;
	private String employeeEmail;
	private String employeeJobtitles;
	private String employeeProfessionaltitle;
	private String employeeStatus;
	private String employeeSex;
	private Date employeeBirthday;
	private String employeeCountry;
	private String employeeProvince;
	private String employeeCity;
	private String employeeOfficeaddress;
	private String employeeOfficephone;
	private String employeeOfficezipcode;
	private String employeeOfficefax;
	private String employeeHomephone;
	private String employeeHomeaddress;
	private String employeeMobile;
	private String employeePoliticstatus;
	private String employeeAcademictitle;
	private String employeeEducationrecord;
	private String employeePhototype;
	private Long tabindex;
	private String employeeNative;
	private String employeeNationality;
	private String employeeProfession;
	private String employeeDescription;
	private String employeeHomezipcode;
	private String employeeIsdepartmentmanager;
	private Long employeeIsdeleted;
	private String employeeIsdeleteddesc;
	private String employeePhoto;
	private String employeeDn;
	// +++ add by  yuxl 2015-02-13
	private String isShowAllProcess;
	// +++ end
	// --- add by yuxl 2013-12-05
	private String isAdmin;
	// --- end
	private Department department;
	
	
	public String departmentName;
	
	public String departmentDn;
	
	private String jobcode;
	
	private String isLeave;//是否离职 0：否 1：是
	
	private String status;//状态 0：禁用 1：启用
	
	public String siteId;
	
	private String position;//职位：1 主要领导，2 分管领导， 3 处长  null为普通职员
	
	private String staffids;//下属人员id（以“，”号隔开）
	
	private String citizencard;//市民卡
	
	private String ssnum;
	
	private String sfznum;
	
	private String xxdh;//信息短号码
	
	private String isCheck;//是否选中
	// Constructors

	/** default constructor */
	public Employee() {
	}

	/** minimal constructor */
	public Employee(String departmentGuid, String employeeName, Long tabindex,
			Long ismanager) {
		this.departmentGuid = departmentGuid;
		this.employeeName = employeeName;
		this.tabindex = tabindex;
	}

	/** full constructor */
	public Employee(String departmentGuid, String employeeLoginname,
			String employeePassword, String employeeName, String employeeEmail,
			String employeeJobtitles, String employeeProfessionaltitle,
			String employeeStatus, String employeeSex, Date employeeBirthday,
			String employeeCountry, String employeeProvince,
			String employeeCity, String employeeOfficeaddress,
			String employeeOfficephone, String employeeOfficezipcode,
			String employeeOfficefax, String employeeHomephone,
			String employeeHomeaddress, String employeeMobile,
			String employeePoliticstatus, String employeeAcademictitle,
			String employeeEducationrecord, String employeePhototype,
			Long tabindex, String employeeNative, String employeeNationality,
			String employeeProfession, String employeeDescription,
			String employeeHomezipcode, String employeeIsdepartmentmanager,
			Long employeeIsdeleted, String employeeIsdeleteddesc,
			String employeePhoto, Long ismanager, String cardid,String isAdmin) {
		this.departmentGuid = departmentGuid;
		this.employeeLoginname = employeeLoginname;
		this.employeePassword = employeePassword;
		this.employeeName = employeeName;
		this.employeeEmail = employeeEmail;
		this.employeeJobtitles = employeeJobtitles;
		this.employeeProfessionaltitle = employeeProfessionaltitle;
		this.employeeStatus = employeeStatus;
		this.employeeSex = employeeSex;
		this.employeeBirthday = employeeBirthday;
		this.employeeCountry = employeeCountry;
		this.employeeProvince = employeeProvince;
		this.employeeCity = employeeCity;
		this.employeeOfficeaddress = employeeOfficeaddress;
		this.employeeOfficephone = employeeOfficephone;
		this.employeeOfficezipcode = employeeOfficezipcode;
		this.employeeOfficefax = employeeOfficefax;
		this.employeeHomephone = employeeHomephone;
		this.employeeHomeaddress = employeeHomeaddress;
		this.employeeMobile = employeeMobile;
		this.employeePoliticstatus = employeePoliticstatus;
		this.employeeAcademictitle = employeeAcademictitle;
		this.employeeEducationrecord = employeeEducationrecord;
		this.employeePhototype = employeePhototype;
		this.tabindex = tabindex;
		this.employeeNative = employeeNative;
		this.employeeNationality = employeeNationality;
		this.employeeProfession = employeeProfession;
		this.employeeDescription = employeeDescription;
		this.employeeHomezipcode = employeeHomezipcode;
		this.employeeIsdepartmentmanager = employeeIsdepartmentmanager;
		this.employeeIsdeleted = employeeIsdeleted;
		this.employeeIsdeleteddesc = employeeIsdeleteddesc;
		this.employeePhoto = employeePhoto;
		this.isAdmin  = isAdmin;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "EMPLOYEE_GUID", unique = true, nullable = false, length = 38)
	public String getEmployeeGuid() {
		return this.employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	@Column(name = "DEPARTMENT_GUID", nullable = false, length = 38)
	public String getDepartmentGuid() {
		return this.departmentGuid;
	}

	public void setDepartmentGuid(String departmentGuid) {
		this.departmentGuid = departmentGuid;
	}

	@Column(name = "EMPLOYEE_LOGINNAME", length = 20)
	public String getEmployeeLoginname() {
		return this.employeeLoginname;
	}

	public void setEmployeeLoginname(String employeeLoginname) {
		this.employeeLoginname = employeeLoginname;
	}

	@Column(name = "EMPLOYEE_PASSWORD", length = 20)
	public String getEmployeePassword() {
		return this.employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	@Column(name = "EMPLOYEE_NAME", nullable = false, length = 50)
	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Column(name = "EMPLOYEE_EMAIL", length = 60)
	public String getEmployeeEmail() {
		return this.employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Column(name = "EMPLOYEE_JOBTITLES", length = 50)
	public String getEmployeeJobtitles() {
		return this.employeeJobtitles;
	}

	public void setEmployeeJobtitles(String employeeJobtitles) {
		this.employeeJobtitles = employeeJobtitles;
	}

	@Column(name = "EMPLOYEE_PROFESSIONALTITLE", length = 60)
	public String getEmployeeProfessionaltitle() {
		return this.employeeProfessionaltitle;
	}

	public void setEmployeeProfessionaltitle(String employeeProfessionaltitle) {
		this.employeeProfessionaltitle = employeeProfessionaltitle;
	}

	@Column(name = "EMPLOYEE_STATUS", length = 20)
	public String getEmployeeStatus() {
		return this.employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	@Column(name = "EMPLOYEE_SEX", length = 2)
	public String getEmployeeSex() {
		return this.employeeSex;
	}

	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EMPLOYEE_BIRTHDAY", length = 7)
	public Date getEmployeeBirthday() {
		return this.employeeBirthday;
	}

	public void setEmployeeBirthday(Date employeeBirthday) {
		this.employeeBirthday = employeeBirthday;
	}

	@Column(name = "EMPLOYEE_COUNTRY", length = 20)
	public String getEmployeeCountry() {
		return this.employeeCountry;
	}

	public void setEmployeeCountry(String employeeCountry) {
		this.employeeCountry = employeeCountry;
	}

	@Column(name = "EMPLOYEE_PROVINCE", length = 20)
	public String getEmployeeProvince() {
		return this.employeeProvince;
	}

	public void setEmployeeProvince(String employeeProvince) {
		this.employeeProvince = employeeProvince;
	}

	@Column(name = "EMPLOYEE_CITY", length = 20)
	public String getEmployeeCity() {
		return this.employeeCity;
	}

	public void setEmployeeCity(String employeeCity) {
		this.employeeCity = employeeCity;
	}

	@Column(name = "EMPLOYEE_OFFICEADDRESS", length = 100)
	public String getEmployeeOfficeaddress() {
		return this.employeeOfficeaddress;
	}

	public void setEmployeeOfficeaddress(String employeeOfficeaddress) {
		this.employeeOfficeaddress = employeeOfficeaddress;
	}

	@Column(name = "EMPLOYEE_OFFICEPHONE", length = 20)
	public String getEmployeeOfficephone() {
		return this.employeeOfficephone;
	}

	public void setEmployeeOfficephone(String employeeOfficephone) {
		this.employeeOfficephone = employeeOfficephone;
	}

	@Column(name = "EMPLOYEE_OFFICEZIPCODE", length = 10)
	public String getEmployeeOfficezipcode() {
		return this.employeeOfficezipcode;
	}

	public void setEmployeeOfficezipcode(String employeeOfficezipcode) {
		this.employeeOfficezipcode = employeeOfficezipcode;
	}

	@Column(name = "EMPLOYEE_OFFICEFAX", length = 20)
	public String getEmployeeOfficefax() {
		return this.employeeOfficefax;
	}

	public void setEmployeeOfficefax(String employeeOfficefax) {
		this.employeeOfficefax = employeeOfficefax;
	}

	@Column(name = "EMPLOYEE_HOMEPHONE", length = 20)
	public String getEmployeeHomephone() {
		return this.employeeHomephone;
	}

	public void setEmployeeHomephone(String employeeHomephone) {
		this.employeeHomephone = employeeHomephone;
	}

	@Column(name = "EMPLOYEE_HOMEADDRESS", length = 100)
	public String getEmployeeHomeaddress() {
		return this.employeeHomeaddress;
	}

	public void setEmployeeHomeaddress(String employeeHomeaddress) {
		this.employeeHomeaddress = employeeHomeaddress;
	}

	@Column(name = "EMPLOYEE_MOBILE", length = 20)
	public String getEmployeeMobile() {
		return this.employeeMobile;
	}

	public void setEmployeeMobile(String employeeMobile) {
		this.employeeMobile = employeeMobile;
	}

	@Column(name = "EMPLOYEE_POLITICSTATUS", length = 50)
	public String getEmployeePoliticstatus() {
		return this.employeePoliticstatus;
	}

	public void setEmployeePoliticstatus(String employeePoliticstatus) {
		this.employeePoliticstatus = employeePoliticstatus;
	}

	@Column(name = "EMPLOYEE_ACADEMICTITLE", length = 30)
	public String getEmployeeAcademictitle() {
		return this.employeeAcademictitle;
	}

	public void setEmployeeAcademictitle(String employeeAcademictitle) {
		this.employeeAcademictitle = employeeAcademictitle;
	}

	@Column(name = "EMPLOYEE_EDUCATIONRECORD", length = 10)
	public String getEmployeeEducationrecord() {
		return this.employeeEducationrecord;
	}

	public void setEmployeeEducationrecord(String employeeEducationrecord) {
		this.employeeEducationrecord = employeeEducationrecord;
	}

	@Column(name = "EMPLOYEE_PHOTOTYPE", length = 10)
	public String getEmployeePhototype() {
		return this.employeePhototype;
	}

	public void setEmployeePhototype(String employeePhototype) {
		this.employeePhototype = employeePhototype;
	}

	@Column(name = "TABINDEX", nullable = false, precision = 8, scale = 0)
	public Long getTabindex() {
		return this.tabindex;
	}

	public void setTabindex(Long tabindex) {
		this.tabindex = tabindex;
	}

	@Column(name = "EMPLOYEE_NATIVE", length = 50)
	public String getEmployeeNative() {
		return this.employeeNative;
	}

	public void setEmployeeNative(String employeeNative) {
		this.employeeNative = employeeNative;
	}

	@Column(name = "EMPLOYEE_NATIONALITY", length = 50)
	public String getEmployeeNationality() {
		return this.employeeNationality;
	}

	public void setEmployeeNationality(String employeeNationality) {
		this.employeeNationality = employeeNationality;
	}

	@Column(name = "EMPLOYEE_PROFESSION", length = 50)
	public String getEmployeeProfession() {
		return this.employeeProfession;
	}

	public void setEmployeeProfession(String employeeProfession) {
		this.employeeProfession = employeeProfession;
	}

	@Column(name = "EMPLOYEE_DESCRIPTION", length = 200)
	public String getEmployeeDescription() {
		return this.employeeDescription;
	}

	public void setEmployeeDescription(String employeeDescription) {
		this.employeeDescription = employeeDescription;
	}

	@Column(name = "EMPLOYEE_HOMEZIPCODE", length = 10)
	public String getEmployeeHomezipcode() {
		return this.employeeHomezipcode;
	}

	public void setEmployeeHomezipcode(String employeeHomezipcode) {
		this.employeeHomezipcode = employeeHomezipcode;
	}

	@Column(name = "EMPLOYEE_ISDEPARTMENTMANAGER", length = 10)
	public String getEmployeeIsdepartmentmanager() {
		return this.employeeIsdepartmentmanager;
	}

	public void setEmployeeIsdepartmentmanager(
			String employeeIsdepartmentmanager) {
		this.employeeIsdepartmentmanager = employeeIsdepartmentmanager;
	}

	@Column(name = "EMPLOYEE_ISDELETED", precision = 1, scale = 0)
	public Long getEmployeeIsdeleted() {
		return this.employeeIsdeleted;
	}

	public void setEmployeeIsdeleted(Long employeeIsdeleted) {
		this.employeeIsdeleted = employeeIsdeleted;
	}

	@Column(name = "EMPLOYEE_ISDELETEDDESC", length = 2000)
	public String getEmployeeIsdeleteddesc() {
		return this.employeeIsdeleteddesc;
	}

	public void setEmployeeIsdeleteddesc(String employeeIsdeleteddesc) {
		this.employeeIsdeleteddesc = employeeIsdeleteddesc;
	}

	@Column(name = "EMPLOYEE_PHOTO")
	public String getEmployeePhoto() {
		return this.employeePhoto;
	}

	public void setEmployeePhoto(String employeePhoto) {
		this.employeePhoto = employeePhoto;
	}
	
	@Column(name = "EMPLOYEE_DN")
	public String getEmployeeDn() {
		return employeeDn;
	}

	public void setEmployeeDn(String employeeDn) {
		this.employeeDn = employeeDn;
	}
	
	@Transient
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	@Column(name = "IS_ADMIN")
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Column(name = "IS_SHOWALLPROCESS")
	public String getIsShowAllProcess() {
		return isShowAllProcess;
	}

	public void setIsShowAllProcess(String isShowAllProcess) {
		this.isShowAllProcess = isShowAllProcess;
	}

	@Transient
	public String getDepartmentName() {
		return departmentName;
	}

	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Transient
	public String getDepartmentDn() {
		return departmentDn;
	}

	public void setDepartmentDn(String departmentDn) {
		this.departmentDn = departmentDn;
	}
	
	@Column(name = "JOBCODE")
	public String getJobcode() {
		return jobcode;
	}

	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}

	@Column(name = "ISLEAVE")
	public String getIsLeave() {
	    return isLeave;
	}

	public void setIsLeave(String isLeave) {
	    this.isLeave = isLeave;
	}

	@Column(name = "STATUS")
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
	
	@Column(name = "SITEID")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Column(name = "POSITION")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "STAFFIDS")
	public String getStaffids() {
		return staffids;
	}

	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	@Column(name = "CITIZENCARD")
	public String getCitizencard() {
		return citizencard;
	}

	public void setCitizencard(String citizencard) {
		this.citizencard = citizencard;
	}

	@Column(name = "SFZNUM")
	public String getSfznum() {
		return sfznum;
	}

	public void setSfznum(String sfznum) {
		this.sfznum = sfznum;
	}
	@Column(name = "SSNUM")
	public String getSsnum() {
		return ssnum;
	}

	public void setSsnum(String ssnum) {
		this.ssnum = ssnum;
	}
	@Column(name = "XXDH")
	public String getXxdh() {
		return xxdh;
	}

	public void setXxdh(String xxdh) {
		this.xxdh = xxdh;
	}
	
	@Transient
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
}