package cn.com.trueway.archiveModel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FILE_ATTACHEMENT")
public class FileAttachment {

	@Id
	@Column(name="ID")
	private String id;
	@Column(name="FILEPATH")
	private String filePath;
	@Column(name="ID_STRUCTURE")
	private String idStructure;
	@Column(name="SJID")
	private String sjid;
	@Column(name="CREATOR")
	private String creator;
	@Column(name="CTIME")
	private Date ctime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(String idStructure) {
		this.idStructure = idStructure;
	}
	public String getSjid() {
		return sjid;
	}
	public void setSjid(String sjid) {
		this.sjid = sjid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public FileAttachment() {
		super();
	}
	public FileAttachment(String id, String filePath, String idStructure, String sjid, String creator, Date ctime) {
		super();
		this.id = id;
		this.filePath = filePath;
		this.idStructure = idStructure;
		this.sjid = sjid;
		this.creator = creator;
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "FileAttachment [id=" + id + ", filePath=" + filePath + ", idStructure=" + idStructure + ", sjid=" + sjid
				+ ", creator=" + creator + ", ctime=" + ctime + "]";
	}
	
}
