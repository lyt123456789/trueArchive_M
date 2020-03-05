package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 文件名称： cn.com.trueway.workflow.core.pojo.WfOnlineEditStatus.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2019-1-26<br/>
 * 功能说明： 正文编辑状态实体类 <br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 */
@Entity
@Table(name = "WF_ONLINE_EDIT_HISTORY")
public class WfOnlineEditStatus implements Serializable{

	private static final long serialVersionUID = 48616627558004477L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 附件id
	 */
	private String attid;
	
	/**
	 * 文件状态
	 */
	private String fileStatus;
	
	/**
	 * 编辑人id
	 */
	private String userId;
	
	/**
	 * 编辑时间
	 */
	private Date editTime;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ATTID", length = 38)
	public String getAttid() {
		return attid;
	}

	public void setAttid(String attid) {
		this.attid = attid;
	}

	@Column(name = "FILESTATUS", length = 2)
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Column(name = "USERID", length = 38)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "EDITTIME")
	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public WfOnlineEditStatus() {

	}

}
