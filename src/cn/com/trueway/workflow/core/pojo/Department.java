package cn.com.trueway.workflow.core.pojo;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 描述：部门表
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 11:50:22 AM
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "ZWKJ_DEPARTMENT")
public class Department implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -7985621231307380288L;
	private String departmentGuid;
	private String superiorGuid;
	private String departmentName;
	private String departmentDescription;
	private String departmentAlias;
	private String departmentShortdn;
	private String departmentGivenname;
	private String departmentHierarchy;
	private String departmentCountry;
	private String departmentState;
	private String departmentCity;
	private String departmentZipcode;
	private String departmentAddress;
	private String departmentOffice;
	private String departmentManagerguid;
	private String departmentPhone;
	private String departmentFax;
	private Long tabindex;
	private Long departmentIsdeleted;
	private String departmentIsdeleteddesc;
	private Long isbureau;
	private String dispatcherGuids;
	private String departmentManager;
	private String departmentSupermanager;
	private String stampDeptName;//盖章部门名称
	
	private Integer isSite;		//是否是站点部门（0,否;1,是）
	/**
	 * 是否经过公文交换平台    0:否; 1:是
	 */
	private Integer isExchange;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** minimal constructor */
	public Department(String superiorGuid, String departmentName, Long tabindex) {
		this.superiorGuid = superiorGuid;
		this.departmentName = departmentName;
		this.tabindex = tabindex;
	}

	/** full constructor */
	public Department(String superiorGuid, String departmentName,
			String departmentDescription, String departmentAlias,
			String departmentShortdn, String departmentGivenname,
			String departmentHierarchy, String departmentCountry,
			String departmentState, String departmentCity,
			String departmentZipcode, String departmentAddress,
			String departmentOffice, String departmentManagerguid,
			String departmentPhone, String departmentFax, Long tabindex,
			Long departmentIsdeleted, String departmentIsdeleteddesc,
			Long isbureau, String dispatcherGuids, String departmentManager,
			String departmentSupermanager, Integer isExchange, String stampDeptName) {
		this.superiorGuid = superiorGuid;
		this.departmentName = departmentName;
		this.departmentDescription = departmentDescription;
		this.departmentAlias = departmentAlias;
		this.departmentShortdn = departmentShortdn;
		this.departmentGivenname = departmentGivenname;
		this.departmentHierarchy = departmentHierarchy;
		this.departmentCountry = departmentCountry;
		this.departmentState = departmentState;
		this.departmentCity = departmentCity;
		this.departmentZipcode = departmentZipcode;
		this.departmentAddress = departmentAddress;
		this.departmentOffice = departmentOffice;
		this.departmentManagerguid = departmentManagerguid;
		this.departmentPhone = departmentPhone;
		this.departmentFax = departmentFax;
		this.tabindex = tabindex;
		this.departmentIsdeleted = departmentIsdeleted;
		this.departmentIsdeleteddesc = departmentIsdeleteddesc;
		this.isbureau = isbureau;
		this.dispatcherGuids = dispatcherGuids;
		this.departmentManager = departmentManager;
		this.departmentSupermanager = departmentSupermanager;
		this.isExchange = isExchange;
		this.stampDeptName = stampDeptName;
	}

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "DEPARTMENT_GUID", unique = true, nullable = false, length = 38)
	public String getDepartmentGuid() {
		return this.departmentGuid;
	}

	public void setDepartmentGuid(String departmentGuid) {
		this.departmentGuid = departmentGuid;
	}

	@Column(name = "SUPERIOR_GUID", nullable = false, length = 38)
	public String getSuperiorGuid() {
		return this.superiorGuid;
	}

	public void setSuperiorGuid(String superiorGuid) {
		this.superiorGuid = superiorGuid;
	}

	@Column(name = "DEPARTMENT_NAME", nullable = false, length = 200)
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "DEPARTMENT_DESCRIPTION", length = 4000)
	public String getDepartmentDescription() {
		return this.departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	@Column(name = "DEPARTMENT_ALIAS", length = 200)
	public String getDepartmentAlias() {
		return this.departmentAlias;
	}

	public void setDepartmentAlias(String departmentAlias) {
		this.departmentAlias = departmentAlias;
	}

	@Column(name = "DEPARTMENT_SHORTDN", length = 200)
	public String getDepartmentShortdn() {
		return this.departmentShortdn;
	}

	public void setDepartmentShortdn(String departmentShortdn) {
		this.departmentShortdn = departmentShortdn;
	}

	@Column(name = "DEPARTMENT_GIVENNAME", length = 200)
	public String getDepartmentGivenname() {
		return this.departmentGivenname;
	}

	public void setDepartmentGivenname(String departmentGivenname) {
		this.departmentGivenname = departmentGivenname;
	}

	@Column(name = "DEPARTMENT_HIERARCHY", length = 2000)
	public String getDepartmentHierarchy() {
		return this.departmentHierarchy;
	}

	public void setDepartmentHierarchy(String departmentHierarchy) {
		this.departmentHierarchy = departmentHierarchy;
	}

	@Column(name = "DEPARTMENT_COUNTRY", length = 200)
	public String getDepartmentCountry() {
		return this.departmentCountry;
	}

	public void setDepartmentCountry(String departmentCountry) {
		this.departmentCountry = departmentCountry;
	}

	@Column(name = "DEPARTMENT_STATE", length = 200)
	public String getDepartmentState() {
		return this.departmentState;
	}

	public void setDepartmentState(String departmentState) {
		this.departmentState = departmentState;
	}

	@Column(name = "DEPARTMENT_CITY", length = 200)
	public String getDepartmentCity() {
		return this.departmentCity;
	}

	public void setDepartmentCity(String departmentCity) {
		this.departmentCity = departmentCity;
	}

	@Column(name = "DEPARTMENT_ZIPCODE", length = 12)
	public String getDepartmentZipcode() {
		return this.departmentZipcode;
	}

	public void setDepartmentZipcode(String departmentZipcode) {
		this.departmentZipcode = departmentZipcode;
	}

	@Column(name = "DEPARTMENT_ADDRESS")
	public String getDepartmentAddress() {
		return this.departmentAddress;
	}

	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}

	@Column(name = "DEPARTMENT_OFFICE")
	public String getDepartmentOffice() {
		return this.departmentOffice;
	}

	public void setDepartmentOffice(String departmentOffice) {
		this.departmentOffice = departmentOffice;
	}

	@Column(name = "DEPARTMENT_MANAGERGUID", length = 38)
	public String getDepartmentManagerguid() {
		return this.departmentManagerguid;
	}

	public void setDepartmentManagerguid(String departmentManagerguid) {
		this.departmentManagerguid = departmentManagerguid;
	}

	@Column(name = "DEPARTMENT_PHONE", length = 200)
	public String getDepartmentPhone() {
		return this.departmentPhone;
	}

	public void setDepartmentPhone(String departmentPhone) {
		this.departmentPhone = departmentPhone;
	}

	@Column(name = "DEPARTMENT_FAX", length = 200)
	public String getDepartmentFax() {
		return this.departmentFax;
	}

	public void setDepartmentFax(String departmentFax) {
		this.departmentFax = departmentFax;
	}

	@Column(name = "TABINDEX", nullable = false, precision = 8, scale = 0)
	public Long getTabindex() {
		return this.tabindex;
	}

	public void setTabindex(Long tabindex) {
		this.tabindex = tabindex;
	}

	@Column(name = "DEPARTMENT_ISDELETED", precision = 1, scale = 0)
	public Long getDepartmentIsdeleted() {
		return this.departmentIsdeleted;
	}

	public void setDepartmentIsdeleted(Long departmentIsdeleted) {
		this.departmentIsdeleted = departmentIsdeleted;
	}

	@Column(name = "DEPARTMENT_ISDELETEDDESC", length = 2000)
	public String getDepartmentIsdeleteddesc() {
		return this.departmentIsdeleteddesc;
	}

	public void setDepartmentIsdeleteddesc(String departmentIsdeleteddesc) {
		this.departmentIsdeleteddesc = departmentIsdeleteddesc;
	}

	@Column(name = "ISBUREAU", precision = 10, scale = 0)
	public Long getIsbureau() {
		return this.isbureau;
	}

	public void setIsbureau(Long isbureau) {
		this.isbureau = isbureau;
	}

	@Column(name = "DISPATCHER_GUIDS", length = 1000)
	public String getDispatcherGuids() {
		return this.dispatcherGuids;
	}

	public void setDispatcherGuids(String dispatcherGuids) {
		this.dispatcherGuids = dispatcherGuids;
	}

	@Column(name = "DEPARTMENT_MANAGER")
	public String getDepartmentManager() {
		return this.departmentManager;
	}

	public void setDepartmentManager(String departmentManager) {
		this.departmentManager = departmentManager;
	}

	@Column(name = "DEPARTMENT_SUPERMANAGER")
	public String getDepartmentSupermanager() {
		return this.departmentSupermanager;
	}

	public void setDepartmentSupermanager(String departmentSupermanager) {
		this.departmentSupermanager = departmentSupermanager;
	}

	@Column(name = "ISEXCHANGE")
	public Integer getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(Integer isExchange) {
		this.isExchange = isExchange;
	}
	
	@Column(name = "STAMPDEPTNAME")
	public String getStampDeptName() {
		return stampDeptName;
	}

	public void setStampDeptName(String stampDeptName) {
		this.stampDeptName = stampDeptName;
	}

	@Column(name = "ISSITE")
	public Integer getIsSite() {
		return isSite;
	}

	public void setIsSite(Integer isSite) {
		this.isSite = isSite;
	}
}