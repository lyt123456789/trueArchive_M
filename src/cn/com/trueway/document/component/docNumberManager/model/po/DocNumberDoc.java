package cn.com.trueway.document.component.docNumberManager.model.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "DOCNUMBER_DOC")
public class DocNumberDoc implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String whmodelid;
	private String doctypeid;
	
	public DocNumberDoc() {

	}
	public DocNumberDoc(String whmodelid, String doctypeid) {
		this.whmodelid = whmodelid;
		this.doctypeid = doctypeid;
	}
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "WHMODELID")
	public String getWhmodelid() {
		return whmodelid;
	}
	public void setWhmodelid(String whmodelid) {
		this.whmodelid = whmodelid;
	}
	@Column(name = "DOCTYPEID")
	public String getDoctypeid() {
		return doctypeid;
	}
	public void setDoctypeid(String doctypeid) {
		this.doctypeid = doctypeid;
	}
	
}
