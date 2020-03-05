package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 著录项表
 * @author user
 *
 */
@Entity
@Table(name="T_ARCHIVE_MODELTAGS")
public class EssModelTags {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="MODELID")
	private String modelId;//结构模板ID
	
	@Column(name="ESTYPE")
	private String esType;//结构类型（对应分层标识）
	
	@Column(name="TAGTYPE")
	private String tagType;//字段格式
	
	@Column(name="TAGNAME")
	private String tagName;//字段名称
	
	@Column(name="TAGDESC")
	private String tagDesc;//字段描述
	
	@Column(name="METADATAFULLNAME")
	private String metaDataFullName;//对应元数据
	
	@Column(name="ID_METADATA")
	private String id_MetaData;//对应元数据id
	
	@Column(name="ESLENGTH")
	private String esLength;//字段长度
	
	@Column(name="ESDOLENGTH")
	private String esDoLength;//字段小数位数
	
	@Column(name="ESISEDIT")
	private String esIsEdit;//是否可编辑（0 不可以 1 可以）
	
	@Column(name="ESISNOTNULL")
	private String esIsNotNull;//是否必填（0 不可以 1 可以）
	
	@Column(name="VIEWORDER")
	private Integer viewOrder;//序号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getEsType() {
		return esType;
	}

	public void setEsType(String esType) {
		this.esType = esType;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}

	public String getMetaDataFullName() {
		return metaDataFullName;
	}

	public void setMetaDataFullName(String metaDataFullName) {
		this.metaDataFullName = metaDataFullName;
	}

	public String getEsLength() {
		return esLength;
	}

	public void setEsLength(String esLength) {
		this.esLength = esLength;
	}

	public String getEsDoLength() {
		return esDoLength;
	}

	public void setEsDoLength(String esDoLength) {
		this.esDoLength = esDoLength;
	}

	public String getEsIsEdit() {
		return esIsEdit;
	}

	public void setEsIsEdit(String esIsEdit) {
		this.esIsEdit = esIsEdit;
	}

	public Integer getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(Integer viewOrder) {
		this.viewOrder = viewOrder;
	}

	public String getId_MetaData() {
		return id_MetaData;
	}

	public void setId_MetaData(String id_MetaData) {
		this.id_MetaData = id_MetaData;
	}

	public String getEsIsNotNull() {
		return esIsNotNull;
	}

	public void setEsIsNotNull(String esIsNotNull) {
		this.esIsNotNull = esIsNotNull;
	}
	
}
