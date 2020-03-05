/**
 * 文件名称:CommentAtts.java
 * 作者:WangXF<br>
 * 创建时间:2012-1-4 下午09:01:01
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.component.taglib.comment.model.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：意见附件类
 * 作者：WangXF<br>
 * 创建时间：2012-1-4 下午09:01:01<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "WF_COMMENT_ATTACHMENTS")
public class CommentAtt implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4273170317983212042L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 意见id
	 */
	private String commentId;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件类型
	 */
	private String fileType;
	
	/**
	 * 文件大小
	 */
	private long fileLength;
	
	/**
	 * 文件路径
	 */
	private String fileLocation;
	
	/**
	 * 文件排序
	 */
	private long fileIndex;
	
	/**
	 * 上传时间
	 */
	private Timestamp uploadTime;

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

	@Column(name="COMMENT_ID")
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	@Column(name="FILE_NAME")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="FILE_TYPE")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="FILE_LENGTH")
	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	@Column(name="FILE_LOCATION")
	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Column(name="FILE_INDEX")
	public Long getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(long fileIndex) {
		this.fileIndex = fileIndex;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	@Column(name="UPLOAD_TIME")
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public CommentAtt() {
		super();
	}
}
