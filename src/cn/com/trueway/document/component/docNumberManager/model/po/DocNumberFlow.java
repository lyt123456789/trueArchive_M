package cn.com.trueway.document.component.docNumberManager.model.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "DOCNUMBER_FLOW")
public class DocNumberFlow implements Serializable{
	
	private static final long serialVersionUID = -7382535421687417795L;
	private String id;
	private Integer flowindex;
	private String typeid;
	private String modelid;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}
	@Column(name = "FLOWINDEX")
	public Integer getFlowindex() {
		return flowindex;
	}
	@Column(name = "TYPEID")
	public String getTypeid() {
		return typeid;
	}
	@Column(name = "MODELID")
	public String getModelid() {
		return modelid;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFlowindex(Integer flowindex) {
		this.flowindex = flowindex;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	
	
	
	
	
}
