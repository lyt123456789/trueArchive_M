package cn.com.trueway.document.component.docNumberManager.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_WH_FW")
public class DocNumberWhFw implements Comparable<DocNumberWhFw>{
	@Transient
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String jgdz;
	private String fwnh;
	private String fwxh;
	private String workflowId;
	private String instanceId;
	private String formId;
	private String siteId;
	
	public DocNumberWhFw() {

	}
	
	public DocNumberWhFw(String id, String jgdz, String fwnh, String fwxh,
			String workflowId, String instanceId, String formId) {
		super();
		this.id = id;
		this.jgdz = jgdz;
		this.fwnh = fwnh;
		this.fwxh = fwxh;
		this.workflowId = workflowId;
		this.instanceId = instanceId;
		this.formId = formId;
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
	
	@Column(name = "JGDZ")
	public String getJgdz() {
		return jgdz;
	}
	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

	@Column(name = "FWNH")
	public String getFwnh() {
		return fwnh;
	}
	public void setFwnh(String fwnh) {
		this.fwnh = fwnh;
	}

	@Column(name = "FWXH")
	public String getFwxh() {
		return fwxh;
	}
	public void setFwxh(String fwxh) {
		this.fwxh = fwxh;
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
	
	@Column(name = "SITEID")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int compareTo(DocNumberWhFw o){
		String thisJgdz = this.jgdz == null ? "" : this.jgdz;
		String thisFwnh = this.fwnh == null ? "" : this.fwnh;
		String thisFwxh = this.fwxh == null || this.fwxh.equals("") ? "0" : this.fwxh;
		
		String oJgdz = o.getJgdz() == null ? "" : o.getJgdz();
		String oFwnh = o.getFwnh() == null ? "" : o.getFwnh();
		String oFwxh = o.getFwxh() == null || o.getFwxh().equals("") ? "0" : o.getFwxh();
		
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
