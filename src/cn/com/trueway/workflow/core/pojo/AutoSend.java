package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_AUTOSEND")
public class AutoSend {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "PROCESSID")
	private String processid;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "AUTO_DATE")
	private Date autoDate;
	
	@Column(name = "SAME_PROCESS")
	private String sameProcess;
	
	/**
	 * 是否是主送人  1 主送人 ，0 抄送人
	 */
	@Column(name = "IS_MASTER")
	private Integer isMaster;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAutoDate() {
		return autoDate;
	}

	public void setAutoDate(Date autoDate) {
		this.autoDate = autoDate;
	}

	public String getSameProcess() {
		return sameProcess;
	}

	public void setSameProcess(String sameProcess) {
		this.sameProcess = sameProcess;
	}

	public Integer getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Integer isMaster) {
		this.isMaster = isMaster;
	}
}
