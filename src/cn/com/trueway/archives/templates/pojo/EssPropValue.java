package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 元数据表
 * @author user
 *
 */
@Entity
@Table(name="T_ARCHIVE_PROPVALUE")
public class EssPropValue {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	/**
	 * 属性标识
	 */
	@Column(name="ESIDENTIFIER")
	private String esIdentifier;
	
	/**
	 * 属性说明
	 */
	@Column(name="ESDESCRIPTION")
	private String esDescription;
	
	/**
	 * 属性标题
	 */
	@Column(name="ESTITLE")
	private String esTitle;
	
	/**
	 * 属性ID
	 */
	@Column(name="ID_PROP")
	private String idProp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEsIdentifier() {
		return esIdentifier;
	}

	public void setEsIdentifier(String esIdentifier) {
		this.esIdentifier = esIdentifier;
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

	public String getIdProp() {
		return idProp;
	}

	public void setIdProp(String idProp) {
		this.idProp = idProp;
	}
	
}
