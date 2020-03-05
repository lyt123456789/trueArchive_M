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
@Table(name="T_ARCHIVE_METADATA")
public class EssMetaData {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	/**
	 * 元数据标识
	 */
	@Column(name="ESIDENTIFIER")
	private String esIdentifier;
	
	/**
	 * 元数据类型
	 */
	@Column(name="ESTYPE")
	private String esType;
	
	/**
	 * 元数据说明
	 */
	@Column(name="ESDESCRIPTION")
	private String esDescription;
	
	/**
	 * 元数据标题
	 */
	@Column(name="ESTITLE")
	private String esTitle;
	
	/**
	 * 命名空间ID
	 */
	@Column(name="ID_NAMESPACE")
	private String idNameSpace;
	
	/**
	 * 元数据关系
	 */
	@Column(name="ESRELATION")
	private Integer esRelation;
	
	/**
	 * 元数据父ID
	 */
	@Column(name="ID_PARENT")
	private String idParent;
	
	/**
	 * 是否参与检索（0 是 1 否）
	 */
	@Column(name="ESISMETADATASEARCH")
	private String esIsMetaDataSearch;
	
	/**
	 * 是否参与全文检索（0 是 1 否）
	 */
	@Column(name="ESISFULLTEXTSEARCH")
	private String esIsFullTextSearch;
	
	/**
	 * 默认值
	 */
	@Column(name="METADEFAULTVALUE")
	private String metaDefaultValue;
	
	/**
	 * 是否是固定值（0 是 1 否）
	 */
	@Column(name="ISFIXEDVALUE")
	private String isFixedValue;

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

	public String getIdNameSpace() {
		return idNameSpace;
	}

	public void setIdNameSpace(String idNameSpace) {
		this.idNameSpace = idNameSpace;
	}

	public Integer getEsRelation() {
		return esRelation;
	}

	public void setEsRelation(Integer esRelation) {
		this.esRelation = esRelation;
	}

	public String getIdParent() {
		return idParent;
	}

	public void setIdParent(String idParent) {
		this.idParent = idParent;
	}

	public String getEsIsMetaDataSearch() {
		return esIsMetaDataSearch;
	}

	public void setEsIsMetaDataSearch(String esIsMetaDataSearch) {
		this.esIsMetaDataSearch = esIsMetaDataSearch;
	}

	public String getEsIsFullTextSearch() {
		return esIsFullTextSearch;
	}

	public void setEsIsFullTextSearch(String esIsFullTextSearch) {
		this.esIsFullTextSearch = esIsFullTextSearch;
	}

	public String getMetaDefaultValue() {
		return metaDefaultValue;
	}

	public void setMetaDefaultValue(String metaDefaultValue) {
		this.metaDefaultValue = metaDefaultValue;
	}

	public String getIsFixedValue() {
		return isFixedValue;
	}

	public void setIsFixedValue(String isFixedValue) {
		this.isFixedValue = isFixedValue;
	}

	public EssMetaData() {
		super();
	}

	public EssMetaData(String id, String esIdentifier, String esType, String esDescription, String esTitle,
			String idNameSpace, Integer esRelation, String idParent, String esIsMetaDataSearch,
			String esIsFullTextSearch, String metaDefaultValue, String isFixedValue) {
		super();
		this.id = id;
		this.esIdentifier = esIdentifier;
		this.esType = esType;
		this.esDescription = esDescription;
		this.esTitle = esTitle;
		this.idNameSpace = idNameSpace;
		this.esRelation = esRelation;
		this.idParent = idParent;
		this.esIsMetaDataSearch = esIsMetaDataSearch;
		this.esIsFullTextSearch = esIsFullTextSearch;
		this.metaDefaultValue = metaDefaultValue;
		this.isFixedValue = isFixedValue;
	}
}
