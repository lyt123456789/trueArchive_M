package cn.com.trueway.extra.meeting.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "T_WF_OFFICE_APPLYMEETOUT")
public class MeetingApplyOut implements Serializable{
	private static final long serialVersionUID = 1L;
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "DECLARESN")
	private String declaresn;
	@Column(name = "APPLICANT")
	private String applicant;
	@Column(name = "APPROVEITEM_ID")
	private String approveitem_id;
	@Column(name = "INSTANCEID")
	private String instanceid;
	@Column(name = "FORMID")
	private String formid;
	@Column(name = "WORKFLOWID")
	private String workflowid;
	@Column(name = "PROCESSID")
	private String processid;
	@Column(name = "MEETINGNAME")
	private String meetingname;
	@Column(name = "NEWTITLE")
	private String newtitle;
	@Column(name = "NEWTT")
	private String newtt;
	@Column(name = "NEWRL")
	private String newrl;
	@Column(name = "NEWLK")
	private String newlk;
	@Column(name = "NEWTIME")
	private String newtime;
	@Column(name = "ARR")
	private String arr;
	@Column(name = "JSY")
	private String jsy;
	@Column(name = "DX")
	private String dx;
	@Column(name = "SBMD")
	private String sbmd;
	@Column(name = "PERSONID")
	private String personid;
	@Column(name = "PERSONNAME")
	private String personname;
	@Column(name = "DXRYID")
	private String dxryid;
	@Column(name = "DXRYNAME")
	private String dxryname;
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApproveitem_id() {
		return approveitem_id;
	}
	public void setApproveitem_id(String approveitem_id) {
		this.approveitem_id = approveitem_id;
	}
	public String getInstanceid() {
		return instanceid;
	}
	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	public String getWorkflowid() {
		return workflowid;
	}
	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getMeetingname() {
		return meetingname;
	}
	public void setMeetingname(String meetingname) {
		this.meetingname = meetingname;
	}
	public String getNewtitle() {
		return newtitle;
	}
	public void setNewtitle(String newtitle) {
		this.newtitle = newtitle;
	}
	public String getNewtt() {
		return newtt;
	}
	public void setNewtt(String newtt) {
		this.newtt = newtt;
	}
	public String getNewrl() {
		return newrl;
	}
	public void setNewrl(String newrl) {
		this.newrl = newrl;
	}
	public String getNewlk() {
		return newlk;
	}
	public void setNewlk(String newlk) {
		this.newlk = newlk;
	}
	public String getNewtime() {
		return newtime;
	}
	public void setNewtime(String newtime) {
		this.newtime = newtime;
	}
	public String getArr() {
		return arr;
	}
	public void setArr(String arr) {
		this.arr = arr;
	}
	public String getJsy() {
		return jsy;
	}
	public void setJsy(String jsy) {
		this.jsy = jsy;
	}
	public String getDx() {
		return dx;
	}
	public void setDx(String dx) {
		this.dx = dx;
	}
	public String getSbmd() {
		return sbmd;
	}
	public void setSbmd(String sbmd) {
		this.sbmd = sbmd;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	public String getDxryid() {
		return dxryid;
	}
	public void setDxryid(String dxryid) {
		this.dxryid = dxryid;
	}
	public String getDxryname() {
		return dxryname;
	}
	public void setDxryname(String dxryname) {
		this.dxryname = dxryname;
	}
}
