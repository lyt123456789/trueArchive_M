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
@Table(name="T_ARCHIVE_PROP")
public class EssProp {
	
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
	 * 属性类型
	 */
	@Column(name="ESTYPE")
	private String esType;
	
	/**
	 * 属性说明
	 */
	@Column(name="ESDESCRIPTION")
	private String esDescription;
	
	/**
	 * 属性名称
	 */
	@Column(name="ESTITLE")
	private String esTitle;
	
	/**
	 * 元数据ID
	 */
	@Column(name="ID_METADATA")
	private String idMetaData;

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

	public String getIdMetaData() {
		return idMetaData;
	}

	public void setIdMetaData(String idMetaData) {
		this.idMetaData = idMetaData;
	}

	public EssProp() {
		super();
	}

	public EssProp(String id, String esIdentifier, String esType, String esDescription, String esTitle,
			String idMetaData) {
		super();
		this.id = id;
		this.esIdentifier = esIdentifier;
		this.esType = esType;
		this.esDescription = esDescription;
		this.esTitle = esTitle;
		this.idMetaData = idMetaData;
	}
}
