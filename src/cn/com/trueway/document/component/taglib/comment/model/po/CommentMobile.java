package cn.com.trueway.document.component.taglib.comment.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * 描述：手机端手写意见保存<br>                                     
 */
@Entity
@Table(name="WF_COMMENT_MOBILE")
public class CommentMobile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String instanceId;
	private String imgFilePath;
	private String imgName;
	private Date uploadTime;
	
	public CommentMobile() {
	
	}
	
	public CommentMobile(String id, String instanceId, String imgFilePath,
			String imgName, Date uploadTime) {
		this.id = id;
		this.instanceId = instanceId;
		this.imgFilePath = imgFilePath;
		this.imgName = imgName;
		this.uploadTime = uploadTime;
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name="ID")
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
	
	@Column(name="IMG_FILEPATH")
	public String getImgFilePath() {
		return imgFilePath;
	}
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}
	
	@Column(name="IMG_NAME")
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Column(name="UPLOAD_TIME")
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}