package cn.com.trueway.document.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.CLOB;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TO_RECDOC")
public class ToRecDoc {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;		//主键id
	
	@Column(name = "DOCGUID")
	private String docguid;
	
	@Column(name = "DOCXML")
	private CLOB docXml;	
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CREATTIME")
	private String creatTime;
	
	@Column(name = "DEPARTMENTID")
	private String departmentId;
	
	@Column(name = "RECUSERID")
	private String recUserId;		//接收的人员id
	
	@Column(name = "BT")
	private String bt;				//标题
	
	@Column(name = "FWDW")
	private String fwdw;			//发文单位	
	
	@Column(name = "TYPE")
	private String type;			//公文类型, 例如通知
	
	@Column(name = "SENDTIME")
	private String sendTime;		//发文时间
	
	@Column(name = "WH")
	private String wh;		//文号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocguid() {
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public CLOB getDocXml() {
		return docXml;
	}

	public void setDocXml(CLOB docXml) {
		this.docXml = docXml;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(String recUserId) {
		this.recUserId = recUserId;
	}

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}

	public String getFwdw() {
		return fwdw;
	}

	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

}
