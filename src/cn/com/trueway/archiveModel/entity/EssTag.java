package cn.com.trueway.archiveModel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 字段信息标识
 * @author alike
 *
 */
@Entity
@Table(name="ESS_TAG")
public class EssTag {
	@Id
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
	private Integer idMetadata;//元数据表
	@Column(name="ID_STRUCTURE")
	private Integer idStructure;//结构对应id
	@Column(name="ESISNULL")
	private Integer esIsnull;//是否为空
	@Column(name="ESLENGTH")
	private Integer esLength;//长度
	@Column(name="ESDOTLENGTH")
	private Integer esDotlength;//小数位数
	@Column(name="ESISSYSTEM")
	private Integer esIssystem;//是否为系统字段
	
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
	public void setEsOrder(Integer esOrder) {
		this.esOrder = esOrder;
	}
	public Integer getIdMetadata() {
		return idMetadata;
	}
	public void setIdMetadata(Integer idMetadata) {
		this.idMetadata = idMetadata;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	public Integer getEsIsnull() {
		return esIsnull;
	}
	public void setEsIsnull(Integer esIsnull) {
		this.esIsnull = esIsnull;
	}
	public Integer getEsLength() {
		return esLength;
	}
	public void setEsLength(Integer esLength) {
		this.esLength = esLength;
	}
	public Integer getEsDotlength() {
		return esDotlength;
	}
	public void setEsDotlength(Integer esDotlength) {
		this.esDotlength = esDotlength;
	}
	public Integer getEsIssystem() {
		return esIssystem;
	}
	public void setEsIssystem(Integer esIssystem) {
		this.esIssystem = esIssystem;
	}
	public EssTag() {
		super();
	}
	public EssTag(Integer id, String esRelation, String esIdentifier, Integer idParent, String esType,
			String esDescription, Integer esOrder, Integer idMetadata, Integer idStructure, Integer esIsnull,
			Integer esLength, Integer esDotlength, Integer esIssystem) {
		super();
		this.id = id;
		this.esRelation = esRelation;
		this.esIdentifier = esIdentifier;
		this.idParent = idParent;
		this.esType = esType;
		this.esDescription = esDescription;
		this.esOrder = esOrder;
		this.idMetadata = idMetadata;
		this.idStructure = idStructure;
		this.esIsnull = esIsnull;
		this.esLength = esLength;
		this.esDotlength = esDotlength;
		this.esIssystem = esIssystem;
	}
	@Override
	public String toString() {
		return "EssTag [id=" + id + ", esRelation=" + esRelation + ", esIndentifier=" + esIdentifier + ", idParent="
				+ idParent + ", esType=" + esType + ", esDescription=" + esDescription + ", esOrder=" + esOrder
				+ ", idMetadata=" + idMetadata + ", idStructure=" + idStructure + ", esIsnull=" + esIsnull
				+ ", esLength=" + esLength + ", esDotlength=" + esDotlength + ", esIssystem=" + esIssystem + "]";
	}
	
}
