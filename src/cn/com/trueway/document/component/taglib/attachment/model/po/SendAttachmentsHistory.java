/**
 * 文件名称:SendAttachmentHistory.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-17 下午03:08:05
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.component.taglib.attachment.model.po;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：正文在线编辑历史记录类
 * 作者：WangXF<br>
 * 创建时间：2011-12-17 下午03:08:05<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "OA_DOC_ATTACHMENTS_HISTORY")
public class SendAttachmentsHistory implements java.io.Serializable{

	@Transient
	private static final long serialVersionUID = -3648873964365182301L;
	
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 公文id
	 */
	private String docguid;
	/**
	 * 文件名称
	 */
	private String filename;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 文件大小
	 */
	private Long filesize;
	/**
	 * 文件路径
	 */
	private String localation;
	/**
	 * 文件排序
	 */
	private Long fileindex;
	/**
	 * 上传时间
	 */
	private Date filetime;
	
	/**
	 * 编辑人
	 */
	private String editer;
	
	/**
	 * 是否再次编辑
	 */
	private String isHaveEdit;

	/**
	 * 附件id
	 */
	private String fjid;

	private Blob attflow;

	/** default constructor */
	public SendAttachmentsHistory() {
	}

	/** minimal constructor */
	public SendAttachmentsHistory(String docguid, String filename, String filetype,
			Long filesize, String localation, Date filetime) {
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.filetime = filetime;
	}

	/** full constructor */
	public SendAttachmentsHistory(String docguid, String filename, String filetype,
			Long filesize, String localation, Long fileindex, Date filetime) {
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.fileindex = fileindex;
		this.filetime = filetime;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DOCGUID", nullable = false, length = 36)
	public String getDocguid() {
		return this.docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	@Column(name = "FILENAME", nullable = false)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "FILETYPE", nullable = false, length = 20)
	public String getFiletype() {
		return this.filetype;
	}
	@Column(name = "FJID")
	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "FILESIZE", nullable = false, precision = 22, scale = 0)
	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	@Column(name = "LOCALATION", nullable = false, length = 500)
	public String getLocalation() {
		return this.localation;
	}

	public void setLocalation(String localation) {
		this.localation = localation;
	}

	@Column(name = "FILEINDEX", precision = 22, scale = 0)
	public Long getFileindex() {
		return this.fileindex;
	}

	public void setFileindex(Long fileindex) {
		this.fileindex = fileindex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FILETIME", nullable = false, length = 7)
	public Date getFiletime() {
		return this.filetime;
	}

	public void setFiletime(Date filetime) {
		this.filetime = filetime;
	}

	@Column(name = "EDITER", length = 50)
	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}

	@Column(name = "ISHAVEEDIT", length = 50)
	public String getIsHaveEdit() {
		return isHaveEdit;
	}
	public void setIsHaveEdit(String isHaveEdit) {
		this.isHaveEdit = isHaveEdit;
	}

	@Column(name = "ATTFLOW")
	public Blob getAttflow() {
		return attflow;
	}

	public void setAttflow(Blob attflow) {
		this.attflow = attflow;
	}
	
	
}
