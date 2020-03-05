/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.model.po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/** 
 * 描述：意见对象<br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 24, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
@Entity
@Table(name="WF_COMMENT")
public class Comment implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 2982350254071192799L;
	
	private String id;
	private String instanceId;
	private String stepId;
	private String userId;
	private String userName;
	private Timestamp signdate;
	private String content;
	private String tagId;
	private String cabWrite;
	private String cabWriteId;
	private String cabImgPath;//CABIMGPATH
	

	
	//是否已审核 0：未审核  1：已审核  2:是办文中的已阅意见
	private Integer approved; 
	//常量0 表示：未审核
	public static final int ISNOT_APPROVED=0;
	//常量1 表示：已审核
	public static final int IS_APPROVED=1;
	//常量2 表示：是办文中的已阅意见
	public static final int ISBW_HAVE_READABLE=2;
	//是否满意
	private Integer isSatisfied;
	
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name="COMMENT_ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="WF_INSTANCE_ID")
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	@Column(name="COMMENT_USERID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="COMMENT_USERNAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="COMMENT_SIGNDATE")
	public Timestamp getSigndate() {
		return signdate;
	}
	public void setSigndate(Timestamp signdate) {
		this.signdate = signdate;
	}
	
	@Column(name="COMMENT_CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="WF_STEP_ID")
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	
	@Column(name="TAG_ID")
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	@Column(name="CABWRITE")
	public String getCabWrite() {
		return cabWrite;
	}
	public void setCabWrite(String cabWrite) {
		this.cabWrite = cabWrite;
	}
	
	@Column(name="CABWRITE_ID")
	public String getCabWriteId() {
		return cabWriteId;
	}
	public void setCabWriteId(String cabWriteId) {
		this.cabWriteId = cabWriteId;
	}
	
	@Column(name="CABIMGPATH")
	public String getCabImgPath() {
		return cabImgPath;
	}
	public void setCabImgPath(String cabImgPath) {
		this.cabImgPath = cabImgPath;
	}
	
	@Column(name="APPROVED")
	public Integer getApproved() {
		return approved;
	}
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	
	@Column(name="ISSATISFIED")
	public Integer getIsSatisfied() {
		return isSatisfied;
	}
	public void setIsSatisfied(Integer isSatisfied) {
		this.isSatisfied = isSatisfied;
	}
}
