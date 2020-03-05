package cn.com.trueway.document.component.taglib.attachment.model.po;

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
 * 描述：<对此类的描述，可以引用系统设计中的描述><br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 2, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "OA_DOC_ATTACHMENTSEXT")
public class SendAttachmentsext  implements java.io.Serializable{

	// Fields
	@Transient
	private static final long serialVersionUID = -4241544316593438081L;
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
	 * 上传附件标题
	 */
	private String title;
	/**
	 * 上传附件类别
	 */
	private String type;
	
	// Constructors

	/** default constructor */
	public SendAttachmentsext() {
	}

	/** minimal constructor */
	public SendAttachmentsext(String docguid, String filename, String filetype,
			Long filesize, String localation, Date filetime) {
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.filetime = filetime;
	}

	/** full constructor */
	public SendAttachmentsext(String id, String docguid, String filename,
			String filetype, Long filesize, String localation, Long fileindex,
			Date filetime, String title, String type) {
		super();
		this.id = id;
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.fileindex = fileindex;
		this.filetime = filetime;
		this.title = title;
		this.type = type;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
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
	
	@Column(name = "ATTTITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "ATTTYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
