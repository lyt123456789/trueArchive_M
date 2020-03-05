package cn.com.trueway.document.component.docNumberManager.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_WH_BW")
public class DocNumberWhBw implements Comparable<DocNumberWhBw>{
	private static final long serialVersionUID = 1L;
	private String id;
	private String bwnh;
	private String bwlx;
	private String bwxh;
	private String lwdwlx;
	private Integer result;
	private String workflowId;
	private String instanceId;
	private String formId;
	private String webId;
	private String siteId;
	
	public DocNumberWhBw() {

	}
	
	public DocNumberWhBw(String id, String bwnh, String bwlx, String bwxh,
			String lwdwlx, Integer result, String workflowId,
			String instanceId, String formId, String webId,String siteId) {
		super();
		this.id = id;
		this.bwnh = bwnh;
		this.bwlx = bwlx;
		this.bwxh = bwxh;
		this.lwdwlx = lwdwlx;
		this.result = result;
		this.workflowId = workflowId;
		this.instanceId = instanceId;
		this.formId = formId;
		this.webId = webId;
		this.siteId = siteId;
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
	
	@Column(name = "BWNH")
	public String getBwnh() {
		return bwnh;
	}
	public void setBwnh(String bwnh) {
		this.bwnh = bwnh;
	}
	
	@Column(name = "BWLX")
	public String getBwlx() {
		return bwlx;
	}
	public void setBwlx(String bwlx) {
		this.bwlx = bwlx;
	}
	
	@Column(name = "BWXH")
	public String getBwxh() {
		return bwxh;
	}
	public void setBwxh(String bwxh) {
		this.bwxh = bwxh;
	}
	
	@Column(name = "LWDWLX")
	public String getLwdwlx() {
		return lwdwlx;
	}
	public void setLwdwlx(String lwdwlx) {
		this.lwdwlx = lwdwlx;
	}

	@Column(name = "WORKFLOWID")
	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	@Column(name = "INSTANCEID")
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "FORMID")
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Column(name = "RESULT")
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}

	@Column(name = "WEBID")
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	
	@Column(name = "SITEID")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int compareTo(DocNumberWhBw o) {
		String thisJgdz = this.bwlx == null ? "" : this.bwlx;
		String thisFwnh = this.bwnh == null ? "" : this.bwnh;
		String thisFwxh = this.bwxh == null || this.bwxh.equals("") ? "0" : this.bwxh;
		
		String oJgdz = o.getBwlx() == null ? "" : o.getBwlx();
		String oFwnh = o.getBwnh() == null ? "" : o.getBwnh();
		String oFwxh = o.getBwxh() == null || o.getBwxh().equals("") ? "0" : o.getBwxh();
		
		if (thisJgdz.equals(oJgdz)){
			if (thisFwnh.equals(oFwnh)){
				return Integer.parseInt(thisFwxh) - Integer.parseInt(oFwxh);
			}else{
				return thisFwnh.compareTo(oFwnh);
			}
		}else{
			return thisJgdz.compareTo(oJgdz);
		}
	}
}