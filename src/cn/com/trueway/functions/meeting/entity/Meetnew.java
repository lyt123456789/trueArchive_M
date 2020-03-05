package cn.com.trueway.functions.meeting.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "HYGL_MEETNEW")
public class Meetnew implements java.io.Serializable{
	private static final long serialVersionUID = -5363648145876340775L;
	private String id;
	private String meetingname;
	private String newtitle;
	private String newtt;
	private String newrl;
	private String newlk;
	private String newtime;
	private String arr;
	private String jsy;
	private String jsyid;
	private String dx;
	private String sbmd;
	private String personid;
	private String personname;
	private String docid;
	private Date creat_time;
	private String chryid;
	private String chryname;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "MEETINGNAME")
	public String getMeetingname() {
		return meetingname;
	}
	public void setMeetingname(String meetingname) {
		this.meetingname = meetingname;
	}
	@Column(name = "NEWTITLE")
	public String getNewtitle() {
		return newtitle;
	}
	public void setNewtitle(String newtitle) {
		this.newtitle = newtitle;
	}
	@Column(name = "NEWTT")
	public String getNewtt() {
		return newtt;
	}
	public void setNewtt(String newtt) {
		this.newtt = newtt;
	}
	@Column(name = "NEWRL")
	public String getNewrl() {
		return newrl;
	}
	public void setNewrl(String newrl) {
		this.newrl = newrl;
	}
	@Column(name = "NEWLK")
	public String getNewlk() {
		return newlk;
	}
	public void setNewlk(String newlk) {
		this.newlk = newlk;
	}
	@Column(name = "NEWTIME")
	public String getNewtime() {
		return newtime;
	}
	public void setNewtime(String newtime) {
		this.newtime = newtime;
	}
	@Column(name = "ARR")
	public String getArr() {
		return arr;
	}
	public void setArr(String arr) {
		this.arr = arr;
	}
	@Column(name = "JSY")
	public String getJsy() {
		return jsy;
	}
	public void setJsy(String jsy) {
		this.jsy = jsy;
	}
	@Column(name = "JSYID")
	public String getJsyid() {
		return jsyid;
	}
	public void setJsyid(String jsyid) {
		this.jsyid = jsyid;
	}
	@Column(name = "DX")
	public String getDx() {
		return dx;
	}
	public void setDx(String dx) {
		this.dx = dx;
	}
	@Column(name = "SBMD")
	public String getSbmd() {
		return sbmd;
	}
	public void setSbmd(String sbmd) {
		this.sbmd = sbmd;
	}
	@Column(name = "PERSONID")
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	@Column(name = "PERSONNAME")
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	@Column(name = "DOCID")
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	@Column(name="CREAT_TIME")
	public Date getCreat_time() {
		return creat_time;
	}
	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}
	@Column(name="CHRYID")
	public String getChryid() {
		return chryid;
	}
	public void setChryid(String chryid) {
		this.chryid = chryid;
	}
	@Column(name="CHRYNAME")
	public String getChryname() {
		return chryname;
	}
	public void setChryname(String chryname) {
		this.chryname = chryname;
	}
}
