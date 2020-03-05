package cn.com.trueway.document.component.docNumberManager.model.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DOCNUMBER_TYPE")
public class DocNumberType implements Serializable{

	private static final long serialVersionUID = 2806141560903979749L;
	private String typeid;
	private String parentid;  //"0"
	private String isparent;  //大类为"0"，子类为"1"
	private String name;
	private String type;
	private String webid;
	private String doctype;
	private String createLogId;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "TYPEID",length = 36)
	public String getTypeid() {
		return typeid;
	}
	@Column(name = "PARENTID")
	public String getParentid() {
		return parentid;
	}
	@Column(name = "ISPARENT")
	public String getIsparent() {
		return isparent;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}
	@Column(name = "WEBID")
	public String getWebid() {
		return webid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setWebid(String webid) {
		this.webid = webid;
	}
	@Column(name = "DOCTYPE")
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	@Column(name="OPERATE_LOG_ID")
	public String getCreateLogId() {
		return createLogId;
	}
	public void setCreateLogId(String createLogId) {
		this.createLogId = createLogId;
	}
	
}
