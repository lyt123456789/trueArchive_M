package cn.com.trueway.archives.using.pojo;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_archive_transferform")
public class Transferform {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;//字段id
	
	@Column(name="FORMID")
	private String formId;//单子id
	
	@Column(name="APPLYNAME")
	private String applyName;//申请人
	
	@Column(name="APPLYUNIT")
	private String applyUnit;//申请单位
	
	@Column(name="APPLYTIME")
	private String applyTime;//申请时间
	
	@Column(name="REGISTRANTNAME")
	private String registrantName;//接待人
	
	@Column(name="AUDITORNAME")
	private String auditorName;//审核人
	
	@Column(name="TRANSFERTYPE")
	private String transferType;//调阅类型
	
	@Column(name="TRANSFERCONTENT")
	private String transferContent;//调阅内容
	
	@Column(name="FORMSTATUS")
	private String formStatus;//调阅单状态      1待审核  2已审核 3归卷）
	
	@Column(name="RETURNTIME")
	private String returnTime;//归还时间
	
	@Column(name="RETURNPEOPLE")
	private String returnPeople;//归还人
	
	@Column(name="GETPEOPLE")
	private String getPeople;//接受人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getApplyUnit() {
		return applyUnit;
	}
	public void setApplyUnit(String applyUnit) {
		this.applyUnit = applyUnit;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getRegistrantName() {
		return registrantName;
	}
	public void setRegistrantName(String registrantName) {
		this.registrantName = registrantName;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getTransferContent() {
		return transferContent;
	}
	public void setTransferContent(String transferContent) {
		this.transferContent = transferContent;
	}
	public String getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public String getReturnPeople() {
		return returnPeople;
	}
	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}
	public String getGetPeople() {
		return getPeople;
	}
	public void setGetPeople(String getPeople) {
		this.getPeople = getPeople;
	}
	
}
