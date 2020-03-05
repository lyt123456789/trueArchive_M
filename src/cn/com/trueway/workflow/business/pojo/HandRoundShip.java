package cn.com.trueway.workflow.business.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：传阅关联关系实体
 * 作者：蒋烽
 * 创建时间：2017-9-6 上午9:58:57
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_HANDROUND_SHIP")
public class HandRoundShip {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	@Column(name = "USERID")
	private String userId;
	
	@Column(name = "INSTANCEID")
	private String instanceId;
	
	@Column(name = "ISREAD")
	private Integer isRead;
	
	@Column(name = "READDATE")
	private String readTime;
	
	@Column(name = "ADDTIME")
	private String addTime;
	
	/**
	 * 发送者id
	 */
	@Column(name = "SENDERID")
	private String senderId;
	
	/**
	 * 发送者姓名
	 */
	@Column(name = "SENDERNAME")
	private String senderName;
	
	/**
	 * 接收者姓名
	 */
	@Column(name = "USERNAME")
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddTime() {
	    return addTime;
	}

	public void setAddTime(String addTime) {
	    this.addTime = addTime;
	}
}
