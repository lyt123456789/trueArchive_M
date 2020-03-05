package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * ClassName: FileDownloadLog <br/> 
 * Function: 文件下载日志实体. <br/> 
 * date: 2018年8月1日 下午2:24:16 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
@Entity
@Table(name = "T_WF_CORE_FILE_DOWNLOAD_LOG")
public class FileDownloadLog {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String 	id;
	
	/**
	 * 下载时间
	 */
	@Column(name = "DOWNLOAD_TIME")
	private Date 	downloadTime;
	
	/**
	 * 文件名称
	 */
	@Column(name = "FILE_TITLE")
	private String 	fileTitle;
	
	/**
	 * 文件实例id
	 */
	@Column(name = "INSTANCEID")
	private String 	instanceId;
	
	/**
	 * 人员id
	 */
	@Column(name = "EMPLOYEE_GUID")
	private String 	employeeGuid;
	
	/**
	 * 人员姓名
	 */
	@Column(name = "EMPLOYEE_NAME")
	private String 	employeeName;
	
	/**
	 * 日志类型（1，公文导出；2，附件下载）
	 */
	@Column(name = "TYPE")
	private String	type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

