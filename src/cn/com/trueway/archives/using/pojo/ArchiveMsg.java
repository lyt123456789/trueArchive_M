package cn.com.trueway.archives.using.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_archive_message")
public class ArchiveMsg {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name="GLID")
	private String glid;//关联的id
	
	@Column(name="SENDER")
	private String sender;//发送者
	
	@Column(name="RECEVIER")
	private String recevier;//接受者
	
	@Column(name="SENDTIME")
	private String sendTime;//发送时间
	
	@Column(name="STATUS")
	private String status;//状态 0:未读 1：已读
	
	@Column(name="CONTENT_MESSAGE")
	private String content_message;//内容
	
	@Column(name="MSGTYPE")
	private String msgType;//消息类型  1：借阅利用申请 2:调卷申请 3：系统消息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGlid() {
		return glid;
	}

	public void setGlid(String glid) {
		this.glid = glid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecevier() {
		return recevier;
	}

	public void setRecevier(String recevier) {
		this.recevier = recevier;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent_message() {
		return content_message;
	}

	public void setContent_message(String content_message) {
		this.content_message = content_message;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	
	
}
