package cn.com.trueway.sys.pojo;

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

import cn.com.trueway.workflow.core.pojo.Department;


@Entity
@Table(name = "T_WF_EMP_MULTDEPT_RELATION")
public class EmpMultdeptRelation implements java.io.Serializable {

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
	
	public String mployeeMobileShort;
	
	public String jobCode;
	
	// Constructors

	/** default constructor */
	public EmpMultdeptRelation() {
	}

	/** minimal constructor */
	public EmpMultdeptRelation(String departmentGuid, String employeeName, Long tabindex,
			Long ismanager) {
		this.departmentGuid = departmentGuid;
		this.employeeName = employeeName;
		this.tabindex = tabindex;
	}

	/** full constructor */
	public EmpMultdeptRelation(String departmentGuid, String employeeLoginname,
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
			String employeePhoto, Long ismanager, String cardid,String isAdmin,String mployeeMobileShort ) {
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
	
	@Column(name = "JOBCODE")
	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
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

	@Column(name = "MPLOYEE_MOBILE_SHORT")
	public String getMployeeMobileShort() {
		return mployeeMobileShort;
	}

	public void setMployeeMobileShort(String mployeeMobileShort) {
		this.mployeeMobileShort = mployeeMobileShort;
	}
	
	
}