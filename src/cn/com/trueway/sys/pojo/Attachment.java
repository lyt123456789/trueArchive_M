package cn.com.trueway.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 张正猛
 * @version 创建时间：2012-1-12 上午10:38:04 类说明
 */
@Entity
@Table(name = "T_NOTICE_ATTACHMENT")
public class Attachment implements Serializable {

	private static final long serialVersionUID = -3109212517197515946L;

	private String id;
	private String name; // 上传文件名
	private String path; // 上传文件路径
	private String noticeId;
	private String uploadFileLength; // 上传文件大小
    private Date attTime=new Date(); //附件上传时间
    private byte[] content; //附件流
    private byte[] pdfContent; //pdf附件流
    private String operateLogId;

	

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@Column(name = "noticeId")
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	@Column(name = "uploadFileLength")
	public String getUploadFileLength() {
		return uploadFileLength;
	}

	public void setUploadFileLength(String uploadFileLength) {
		this.uploadFileLength = uploadFileLength;
	}

	@Column(name = "attime")
	public Date getAttTime() {
		return attTime;
	}

	public void setAttTime(Date attTime) {
		this.attTime = attTime;
	}
	
	/**
	 * @return the content
	 */
	@Column(name = "content")
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Column(name = "pdfContent")
	public byte[] getPdfContent() {
		return pdfContent;
	}

	public void setPdfContent(byte[] pdfContent) {
		this.pdfContent = pdfContent;
	}
	@Column(name = "OPERATE_LOG_ID")
	public String getOperateLogId() {
		return operateLogId;
	}

	public void setOperateLogId(String operateLogId) {
		this.operateLogId = operateLogId;
	}


}
