package cn.com.trueway.extra.meeting.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_OFFICE_APPLYMEET")
public class MeetingphoApply implements Serializable{
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
	@Column(name = "ROOMID")
	private String roomid;
	@Column(name = "ROOMNAME")
	private String roomname;
	@Column(name = "USERID")
	private String userid;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "DEPARTMENTID")
	private String departmentid;
	@Column(name = "DEPARTMENTNAME")
	private String departmentname;
	@Column(name = "MEETING_RSCOUNT")
	private String meeting_rscount;
	@Column(name = "MEETING_BEGIN_TIME")
	private String meeting_begin_time;
	@Column(name = "MEETING_END_TIME")
	private String meeting_end_time;
	@Column(name = "MEETING_BCYRYID")
	private String meeting_bcyryid;
	@Column(name = "MEETING_BCYRY")
	private String meeting_bcyry;
	@Column(name = "MEETING_WCYRY")
	private String meeting_wcyry;
	@Column(name = "MEETING_ZCR")
	private String meeting_zcr;
	@Column(name = "MEETING_XQ")
	private String meeting_xq;
	@Column(name = "MEETING_SUBJECT")
	private String meeting_subject;
	@Column(name = "MEETING_BZ")
	private String meeting_bz;
	@Column(name = "STATE")
	private String state;
	@Transient
	private String item_id;
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
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getMeeting_rscount() {
		return meeting_rscount;
	}
	public void setMeeting_rscount(String meeting_rscount) {
		this.meeting_rscount = meeting_rscount;
	}
	public String getMeeting_begin_time() {
		return meeting_begin_time;
	}
	public void setMeeting_begin_time(String meeting_begin_time) {
		this.meeting_begin_time = meeting_begin_time;
	}
	public String getMeeting_end_time() {
		return meeting_end_time;
	}
	public void setMeeting_end_time(String meeting_end_time) {
		this.meeting_end_time = meeting_end_time;
	}
	public String getMeeting_bcyryid() {
		return meeting_bcyryid;
	}
	public void setMeeting_bcyryid(String meeting_bcyryid) {
		this.meeting_bcyryid = meeting_bcyryid;
	}
	public String getMeeting_bcyry() {
		return meeting_bcyry;
	}
	public void setMeeting_bcyry(String meeting_bcyry) {
		this.meeting_bcyry = meeting_bcyry;
	}
	public String getMeeting_wcyry() {
		return meeting_wcyry;
	}
	public void setMeeting_wcyry(String meeting_wcyry) {
		this.meeting_wcyry = meeting_wcyry;
	}
	public String getMeeting_zcr() {
		return meeting_zcr;
	}
	public void setMeeting_zcr(String meeting_zcr) {
		this.meeting_zcr = meeting_zcr;
	}
	public String getMeeting_xq() {
		return meeting_xq;
	}
	public void setMeeting_xq(String meeting_xq) {
		this.meeting_xq = meeting_xq;
	}
	public String getMeeting_subject() {
		return meeting_subject;
	}
	public void setMeeting_subject(String meeting_subject) {
		this.meeting_subject = meeting_subject;
	}
	public String getMeeting_bz() {
		return meeting_bz;
	}
	public void setMeeting_bz(String meeting_bz) {
		this.meeting_bz = meeting_bz;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_id() {
		return item_id;
	}
}
