package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工作流xml表
 * 
 * @author Administrator(Feng)
 * 
 */
@Entity
@Table(name = "WF_XML")
public class WfXml {
	@Id
	@Column(name = "wfx_id", length = 36)
	private String wfx_id;
	@Column(name = "wfx_xml", columnDefinition = "clob")
	private String wfx_xml;

	public String getWfx_id() {
		return wfx_id;
	}

	public void setWfx_id(String wfx_id) {
		this.wfx_id = wfx_id;
	}

	public String getWfx_xml() {
		return wfx_xml;
	}

	public void setWfx_xml(String wfx_xml) {
		this.wfx_xml = wfx_xml;
	}

}
