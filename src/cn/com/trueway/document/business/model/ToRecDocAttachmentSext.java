package cn.com.trueway.document.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.BLOB;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TO_RECDOC_ATTACHMENTSEXT")
public class ToRecDocAttachmentSext {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "DOCGUID")
	private String docguid;
	
	@Column(name = "FILEINDEX")
	private Integer fileIndex;
	
	@Column(name = "FILENAME")
	private String  fileName;
	
	@Column(name = "FILESIZE")
	private Long  fileSize;
	
	@Column(name = "FILETIME")
	private Date  fileTime;
	
	@Column(name = "FILETYPE")
	private String fileType;
	
	@Column(name = "LOCALATION")
	private String localation;
	
	@Column(name = "CONTENT")
	private BLOB content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocguid() {
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public Integer getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(Integer fileIndex) {
		this.fileIndex = fileIndex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getFileTime() {
		return fileTime;
	}

	public void setFileTime(Date fileTime) {
		this.fileTime = fileTime;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getLocalation() {
		return localation;
	}

	public void setLocalation(String localation) {
		this.localation = localation;
	}

	public BLOB getContent() {
		return content;
	}

	public void setContent(BLOB content) {
		this.content = content;
	}

	
}
