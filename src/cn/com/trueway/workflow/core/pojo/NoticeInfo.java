package cn.com.trueway.workflow.core.pojo;

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
 * 描述：通知公告
 * 作者：蔡亚军
 * 创建时间：2015-7-21 下午7:10:39
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_NOTICEINFO")
public class NoticeInfo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;				//主键id
	
	@Column(name = "FPROCESSID")
	private String fprocessId;		//分发的步骤
	
	@Column(name = "USERID")
	private String userId;		//同步分发的人员id
	
	@Column(name = "SENDTIME")
	private Date sendTime;		//同步到通知告示的时间
	
	@Column(name = "STATUS")
	private String status;		//状态位置; 0正常显示, 1,隐藏
	
	@Transient
	private String title;		//标题 
	
	@Transient
	private String userName;   //用户姓名
	
	@Transient
	private String pdfPath;		//文件对应的路径
	
	@Transient
	private String commentJson;		//意见
	
	@Transient
	private String str_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFprocessId() {
		return fprocessId;
	}

	public void setFprocessId(String fprocessId) {
		this.fprocessId = fprocessId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getCommentJson() {
		return commentJson;
	}

	public void setCommentJson(String commentJson) {
		this.commentJson = commentJson;
	}

	public String getStr_time() {
		return str_time;
	}

	public void setStr_time(String str_time) {
		this.str_time = str_time;
	}
	
}
