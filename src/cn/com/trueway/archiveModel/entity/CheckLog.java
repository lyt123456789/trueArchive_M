package cn.com.trueway.archiveModel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FOURCHECKLOG")
public class CheckLog {
	@Id
	@Column(name="ID")
	private String id;//主键
	@Column(name="RESULT")
	private String result;//检测结果
	@Column(name="IDSTRUCTURE")
	private String idStructure;//结构id
	@Column(name="ZDID")
	private String zdid;//检测字段的id
	@Column(name="CHECKTIME")
	private Date checkTime;//检测时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(String idStructure) {
		this.idStructure = idStructure;
	}
	public String getZdid() {
		return zdid;
	}
	public void setZdid(String zdid) {
		this.zdid = zdid;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public CheckLog() {
		super();
	}
	public CheckLog(String id, String result, String idStructure, String zdid, Date checkTime) {
		super();
		this.id = id;
		this.result = result;
		this.idStructure = idStructure;
		this.zdid = zdid;
		this.checkTime = checkTime;
	}
	@Override
	public String toString() {
		return "CheckLog [id=" + id + ", result=" + result + ", idStructure=" + idStructure + ", zdid=" + zdid
				+ ", checkTime=" + checkTime + "]";
	}

}
