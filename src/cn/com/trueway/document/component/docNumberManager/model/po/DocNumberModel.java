package cn.com.trueway.document.component.docNumberManager.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DOCNUMBER_MODEL")
public class DocNumberModel implements Serializable{
	
	private static final long serialVersionUID = -1633741031094263147L;
	private String modelid;
	private Integer modelindex;
	private String userid;
	private Date time;
	private String webid;
	private String docids;
	private String content;
	private String workflowId;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "MODELID",length = 36)
	public String getModelid() {
		return modelid;
	}
	@Column(name = "MODELINDEX")
	public Integer getModelindex() {
		return modelindex;
	}
	public void setModelindex(Integer modelindex) {
		this.modelindex = modelindex;
	}
	@Column(name = "USERID")
	public String getUserid() {
		return userid;
	}
	@Column(name = "TIME")
	public Date getTime() {
		return time;
	}
	@Column(name = "WEBID")
	public String getWebid() {
		return webid;
	}
	@Column(name = "DOCIDS")
	public String getDocids() {
		return docids;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setWebid(String webid) {
		this.webid = webid;
	}
	public void setDocids(String docids) {
		this.docids = docids;
	}
	
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "WORKFLOWID")
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
}
