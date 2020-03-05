/**
 * 文件名称:DocXgDepartment.java
 * 作者:Lv☂<br>
 * 创建时间:2013-10-25 下午05:31:55
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DOCEXCHANGE_DEPARTMENT")
public class DocXgDepartment {

	private String id;
	private String pid;
	private String name;
	private String isSub;
	private Integer orderNum;
	private String hasSub;
	private String deptGuid;
	
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "DEPARTMENT_GUID",length = 36)
	public String getDeptGuid() {
		return deptGuid;
	}
	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}
	
	@Column(name = "SUPERIOR_GUID")
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name = "DEPARTMENT_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "IS_SUB")
	public String getIsSub() {
		return isSub;
	}
	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}
	
	@Column(name = "ORDER_NUMBER")
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	@Column(name = "HAS_SUB")
	public String getHasSub() {
		return hasSub;
	}
	public void setHasSub(String hasSub) {
		this.hasSub = hasSub;
	}
	
	
}
