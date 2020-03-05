package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 字段信息标识
 * @author alike
 *
 */
@Entity
@Table(name="T_ARCHIVE_TAG")
public class EssTag {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ARCHIVE_TAG")
	@SequenceGenerator(name="SEQ_ARCHIVE_TAG",sequenceName="SEQ_ARCHIVE_TAG",allocationSize=1)
	@Column(name="ID")
    private Integer id;//主键
	@Column(name="ESRELATION")
	private String esRelation;//关联
	@Column(name="ESIDENTIFIER")
	private String esIdentifier;//标识
	@Column(name="ID_PARENT")
	private Integer idParent;//父id
	@Column(name="ESTYPE")
	private String esType;//类型
	@Column(name="ESDESCRIPTION")
	private String esDescription;//描述
	@Column(name="ESORDER")
	private Integer esOrder;//排序
	@Column(name="ID_METADATA")
	private String idMetadata;//元数据id
	@Column(name="NAME_METADATA")
	private String nameMetadata;//元数据name
	@Column(name="ID_STRUCTURE")
	private Integer idStructure;//结构对应id
	@Column(name="ESISNOTNULL")
	private String esIsNotNull;//是否为空
	@Column(name="ESLENGTH")
	private String esLength;//长度
	@Column(name="ESDOTLENGTH")
	private String esDotlength;//小数位数（可用来判断是否为系统字段）
	@Column(name="ESISEDIT")
	private String esIsEdit;//是否可编辑  
	@Transient
	private String isCustom;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEsRelation() {
		return esRelation;
	}
	public void setEsRelation(String esRelation) {
		this.esRelation = esRelation;
	}
	public String getEsIdentifier() {
		return esIdentifier;
	}
	public void setEsIdentifier(String esIdentifier) {
		this.esIdentifier = esIdentifier;
	}
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
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
	public Integer getEsOrder() {
		return esOrder;
	}
	public String getIdMetadata() {
		return idMetadata;
	}
	public void setIdMetadata(String idMetadata) {
		this.idMetadata = idMetadata;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	public String getEsIsNotNull() {
		return esIsNotNull;
	}
	public void setEsIsNotNull(String esIsNotNull) {
		this.esIsNotNull = esIsNotNull;
	}
	public String getEsLength() {
		return esLength;
	}
	public void setEsLength(String esLength) {
		this.esLength = esLength;
	}
	public String getEsDotlength() {
		return esDotlength;
	}
	public void setEsDotlength(String esDotlength) {
		this.esDotlength = esDotlength;
	}
	
	public String getEsIsEdit() {
		return esIsEdit;
	}
	@Override
	public String toString() {
		return "EssTag [id=" + id + ", esRelation=" + esRelation + ", esIdentifier=" + esIdentifier + ", idParent="
				+ idParent + ", esType=" + esType + ", esDescription=" + esDescription + ", esOrder=" + esOrder
				+ ", idMetadata=" + idMetadata + ", nameMetadata=" + nameMetadata + ", idStructure=" + idStructure
				+ ", esIsNotNull=" + esIsNotNull + ", esLength=" + esLength + ", esDotlength=" + esDotlength
				+ ", esIsEdit=" + esIsEdit + ", isCustom=" + isCustom + "]";
	}
	public void setEsIsEdit(String esIsEdit) {
		this.esIsEdit = esIsEdit;
	}
	public void setEsOrder(Integer esOrder) {
		this.esOrder = esOrder;
	}
	public String getNameMetadata() {
		return nameMetadata;
	}
	public void setNameMetadata(String nameMetadata) {
		this.nameMetadata = nameMetadata;
	}
	public String getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(String isCustom) {
		this.isCustom = isCustom;
	}
	
}
