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
 * 结构模板信息表
 * @author lixr
 *
 */
@Entity
@Table(name="T_ARCHIVE_STRUCTUREMODEL")
public class EssStructureModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ARCHIVE_STRUCTUREMODEL")
	@SequenceGenerator(name="SEQ_ARCHIVE_STRUCTUREMODEL",sequenceName="SEQ_ARCHIVE_STRUCTUREMODEL",allocationSize=1)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="BUSINESS")
	private String business;//业务标识符
	
	@Column(name="ESIDENTIFIER")
	private String esIdentifier;//模板名称
	
	@Column(name="ESDESCRIPTION")
	private String esDescription;//模板描述
	
	@Column(name="ESTYPE")
	private String esType;//模板类型（指三级或两级目录形式）
	
	@Transient
	private String esBusinessName;//业务名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
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

	public String getEsType() {
		return esType;
	}

	public void setEsType(String esType) {
		this.esType = esType;
	}

	public String getEsBusinessName() {
		return esBusinessName;
	}

	public void setEsBusinessName(String esBusinessName) {
		this.esBusinessName = esBusinessName;
	}

	public EssStructureModel(Integer id, String business, String esIdentifier, String esDescription, String esType) {
		super();
		this.id = id;
		this.business = business;
		this.esIdentifier = esIdentifier;
		this.esDescription = esDescription;
		this.esType = esType;
	}

	public EssStructureModel() {
		super();
	}
	
}
