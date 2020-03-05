package cn.com.trueway.functions.meeting.entity;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
@Entity
@SqlResultSetMapping(name="HyList", 
entities={ 
    @EntityResult(entityClass=cn.com.trueway.functions.meeting.entity.Sgtjhytz.class,
    	fields={
        	@FieldResult(name="declaresn", column="declaresn"),
			@FieldResult(name="applicant", column="applicant"),
			@FieldResult(name="approveitem_id", column="approveitem_id"),
			@FieldResult(name="formid", column="formid"),
			@FieldResult(name="workflowid", column="workflowid"),	
			@FieldResult(name="processid", column="processid"),
			@FieldResult(name="instanceid", column="instanceid"),
			@FieldResult(name="meetingname", column="meetingname"),
			@FieldResult(name="newtitle", column="newtitle"),
			@FieldResult(name="newtt", column="newtt"),
			@FieldResult(name="newrl", column="newrl"),
			@FieldResult(name="newlk", column="newlk"),
			@FieldResult(name="newtime", column="newtime"),
			@FieldResult(name="arr", column="arr"),
			@FieldResult(name="jsy", column="jsy"),
			@FieldResult(name="dx", column="dx"),
			@FieldResult(name="sbmd", column="sbmd"),
			@FieldResult(name="personid", column="personid"),
			@FieldResult(name="personname", column="personname"),
			@FieldResult(name="dxryid", column="dxryid"),
			@FieldResult(name="dxryname", column="dxryname"),
        })
}
)
public class Sgtjhytz{
//	@Column(name = "DECLARESN")  
//	@Column(name = "APPLICANT")  
//	@Column(name = "APPROVEITEM_ID")  
//	@Column(name = "FORMID")  
//	@Column(name = "WORKFLOWID")  
//	@Column(name = "PROCESSID")  
//	@Column(name = "NEWTITLE")
//	@Column(name = "INSTANCEID")
//	@Column(name = "NEWTT")
//	@Column(name = "MEETINGNAME")
//	@Column(name = "NEWRL")
//	@Column(name = "NEWLK")
//	@Column(name = "NEWTIME")
//	@Column(name = "ARR")
//	@Column(name = "JSY")
//	@Column(name = "DX")
//	@Column(name = "SBMD")
//	@Column(name = "PERSONNAME")
//	@Column(name="DXRYID")
//	@Column(name="DXRYNAME")
//	@Column(name = "PERSONID")
	
	private String declaresn;
	private String applicant;
	private String approveitem_id;
	private String formid;
	private String workflowid;
	private String processid;
	@Id
	private String instanceid;
	private String meetingname;
	private String newtitle;
	private String newtt;
	private String newrl;
	private String newlk;
	private String newtime;
	private String arr;
	private String jsy;
	private String dx;
	private String sbmd;
	private String personid;
	private String personname;
	private String dxryid;
	private String dxryname;
	
	
	public Sgtjhytz(){
		
	}
	
	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	
	
	public String getInstanceid() {
		return instanceid;
	}
	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
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

}
