package cn.com.trueway.functions.meeting.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "HYGL_ATTACHMENT")
public class Attachment implements java.io.Serializable{
	private static final long serialVersionUID = -1099021766949063743L;
	private String id;
	private String docid;
	private String file_name;
	private String file_type;
	private Long file_size;
	private String localation;
	private Date filetime;
	private String fileindex;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "DOCID")
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	@Column(name = "FILE_NAME")
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	@Column(name = "FILE_TYPE")
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	@Column(name = "FILE_SIZE")
	public Long getFile_size() {
		return file_size;
	}
	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}
	@Column(name = "FILETIME")
	public Date getFiletime() {
		return filetime;
	}
	public void setFiletime(Date filetime) {
		this.filetime = filetime;
	}
	@Column(name = "FILEINDEX")
	public String getFileindex() {
		return fileindex;
	}
	public void setFileindex(String fileindex) {
		this.fileindex = fileindex;
	}
	@Column(name = "LOCALATION")
	public String getLocalation() {
		return localation;
	}
	public void setLocalation(String localation) {
		this.localation = localation;
	}
}
