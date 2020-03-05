package cn.com.trueway.workflow.business.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 催办信息
 * 描述：TODO 对PressMsg进行描述
 * 作者：季振华
 * 创建时间：2016-8-24 下午7:24:08
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_PRESSMSG")
public class PressMsg implements Serializable {

	private static final long serialVersionUID = -3323727347111322133L;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;						//主键			
	
	@Column(name = "PROCESSID", unique = true, nullable = false)
	private String processId;					//办件id
	
	@Column(name = "PRESSCONTENT", unique = true, nullable = false)
	private String pressContent;				//催办信息
	
	@Column(name = "ISSEND")
	private String isSend;				//是否发送短信
	
	@Column(name = "APPLYTIME")
	private Date applyTime;				//提交时间
	
	@Column(name = "USERID")
	private String userId;//发送催办信息的人
	
	@Column(name = "USERNAME")
	private String userName;//发送催办信息的人名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getPressContent() {
		return pressContent;
	}

	public void setPressContent(String pressContent) {
		this.pressContent = pressContent;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}

