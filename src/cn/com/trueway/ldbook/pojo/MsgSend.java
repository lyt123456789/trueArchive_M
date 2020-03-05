package cn.com.trueway.ldbook.pojo;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：tcpServcer短信发送接口
 * 作者：蔡亚军
 * 创建时间：2014-7-15 上午11:19:45
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_MSG_SEND")
public class MsgSend {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "SENDUSERID")
	private String sendUserId;	//VARCHAR2(38)	Y			发送人员ID
	
	@Column(name = "SENDUSERNAME")
	private String sendUserName;
	
	@Column(name = "RECUSERID")
	private String recUserId;	//VARCHAR2(38)	Y			接收人员id
	
	@Column(name = "RECUSERNAME")
	private String recUserName;	//
	
	@Column(name = "CONTENT")
	private String content;		//VARCHAR2(4000)	Y			消息内容
	
	@Column(name = "STATUS")
	private Integer status;		//NUMBER	Y			状态位: 1为发送成功
	
	@Column(name = "SENDDATE")
	private Date sendDate;	//	DATE	Y			发送时间
	
	@Column(name = "PROCESSID")
	private String processId;	
	
	@Transient
	private String title;
	
	@Transient
	private String type;
	
	@Transient
	private String itemType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(String recUserId) {
		this.recUserId = recUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getRecUserName() {
		return recUserName;
	}

	public void setRecUserName(String recUserName) {
		this.recUserName = recUserName;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
