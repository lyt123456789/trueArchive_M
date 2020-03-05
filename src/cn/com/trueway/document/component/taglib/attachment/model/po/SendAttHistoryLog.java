package cn.com.trueway.document.component.taglib.attachment.model.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 文件名称： cn.com.trueway.document.component.taglib.attachment.model.po.SendAttHistoryLog.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2019-3-27<br/>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>  
 * 修改记录：<br/> 
 * 修改作者        日期       修改内容<br/> 
 */
@Entity
@Table(name = "OA_DOC_ATTACHMENTS_HISTORY_LOG")
public class SendAttHistoryLog {

	private String id;
	private String attId;
	private String userId;
	private String docguid;
	private String excuteMethod;
	private Date editTime;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "ATTID", length = 36)
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}
	@Column(name = "USERID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "DOCGUID")
	public String getDocguid() {
		return docguid;
	}
	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}
	@Column(name = "EDITTIME")
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	@Column(name = "EXCUTEMETHOD")
	public String getExcuteMethod() {
		return excuteMethod;
	}
	public void setExcuteMethod(String excuteMethod) {
		this.excuteMethod = excuteMethod;
	}
	public SendAttHistoryLog() {
	}
	
}
