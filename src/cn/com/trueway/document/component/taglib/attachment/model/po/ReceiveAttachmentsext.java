package cn.com.trueway.document.component.taglib.attachment.model.po;
// default package

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

import cn.com.trueway.document.component.taglib.attachment.model.vo.DocexchangeAttachmentsext;


/**
 * DocexchangeAttachments entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_RECDOC_ATTACHMENTSEXT")
public class ReceiveAttachmentsext  implements java.io.Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -3400678766022577412L;

	private String id;
	private String docguid;
	private String filename;
	private String filetype;
	private Long filesize;
	private String localation;
	private Long fileindex;
	private Date filetime;
	
	private String data;
	
	// Constructors
	@Transient
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/** default constructor */
	public ReceiveAttachmentsext() {
	}

	public ReceiveAttachmentsext(DocexchangeAttachmentsext docAttachments) {
		this.docguid = docAttachments.getDocguid();
		this.filename = docAttachments.getFilename();
		this.filetype = docAttachments.getFiletype();
		this.filesize = docAttachments.getFilesize();
		this.localation = docAttachments.getLocalation();
		this.filetime = docAttachments.getFiletime();
		this.fileindex=docAttachments.getFileindex();
	}


	/** full constructor */
	public ReceiveAttachmentsext(String docguid, String filename,
			String filetype, Long filesize, String localation, Long fileindex,
			Date filetime) {
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.fileindex = fileindex;
		this.filetime = filetime;
	}

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

}