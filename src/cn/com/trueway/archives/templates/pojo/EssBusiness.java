package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务表
 * @author user
 *
 */
@Entity
@Table(name="T_ARCHIVE_BUSINESS")
public class EssBusiness {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="ESURL")
	private String esUrl;
	
	@Column(name="ESIDENTIFIER")
	private String esIdentifier;
	
	@Column(name="ESTYPE")
	private String esType;
	
	@Column(name="ESDESCRIPTION")
	private String esDescription;
	
	@Column(name="ESTITLE")
	private String esTitle;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEsUrl() {
		return esUrl;
	}

	public void setEsUrl(String esUrl) {
		this.esUrl = esUrl;
	}

	public String getEsIdentifier() {
		return esIdentifier;
	}

	public void setEsIdentifier(String esIdentifier) {
		this.esIdentifier = esIdentifier;
	}

	public String getEsType() {
		return esType;
	}

	public void setEsType(String esType) {
		this.esType = esType;
	}

	public String getEsDescription() {
		return esDescription;
	}

	public void setEsDescription(String esDescription) {
		this.esDescription = esDescription;
	}

	public String getEsTitle() {
		return esTitle;
	}

	public void setEsTitle(String esTitle) {
		this.esTitle = esTitle;
	}
}
