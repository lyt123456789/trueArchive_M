package cn.com.trueway.archiveModel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 核心结构表
 * @author alike
 *
 */
@Entity
@Table(name="ESS_STRUCTURE")
public class EssStructure {

	@Id
	@Column(name="ID")
	private Integer id;
	@Column(name="ESIDENTIFIER")
	private String esIdentifire;
	@Column(name="ESDATE")
	private String esDate;
	@Column(name="ESSTATUS")
	private String esStatus;
	@Column(name="ESTYPE")
	private String esType;
	@Column(name="ESPUBLISHER")
	private String esPublisher;
	@Column(name="ESDESCRIPTION")
	private String esDescription;
	@Column(name="ESCREATOR")
	private String esCreator;
	@Column(name="ESTITLE")
	private String esTitle;
	@Column(name="ESORDER")
	private String esOrder;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEsIdentifire() {
		return esIdentifire;
	}
	public void setEsIdentifire(String esIdentifire) {
		this.esIdentifire = esIdentifire;
	}
	public String getEsDate() {
		return esDate;
	}
	public void setEsDate(String esDate) {
		this.esDate = esDate;
	}
	public String getEsStatus() {
		return esStatus;
	}
	public void setEsStatus(String esStatus) {
		this.esStatus = esStatus;
	}
	public String getEsType() {
		return esType;
	}
	public void setEsType(String esType) {
		this.esType = esType;
	}
	public String getEsPublisher() {
		return esPublisher;
	}
	public void setEsPublisher(String esPublisher) {
		this.esPublisher = esPublisher;
	}
	public String getEsDescription() {
		return esDescription;
	}
	public void setEsDescription(String esDescription) {
		this.esDescription = esDescription;
	}
	public String getEsCreator() {
		return esCreator;
	}
	public void setEsCreator(String esCreator) {
		this.esCreator = esCreator;
	}
	public String getEsTitle() {
		return esTitle;
	}
	public void setEsTitle(String esTitle) {
		this.esTitle = esTitle;
	}
	public String getEsOrder() {
		return esOrder;
	}
	public void setEsOrder(String esOrder) {
		this.esOrder = esOrder;
	}
	public EssStructure() {
		super();
	}
	public EssStructure(Integer id, String esIdentifire, String esDate, String esStatus, String esType,
			String esPublisher, String esDescription, String esCreator, String esTitle, String esOrder) {
		super();
		this.id = id;
		this.esIdentifire = esIdentifire;
		this.esDate = esDate;
		this.esStatus = esStatus;
		this.esType = esType;
		this.esPublisher = esPublisher;
		this.esDescription = esDescription;
		this.esCreator = esCreator;
		this.esTitle = esTitle;
		this.esOrder = esOrder;
	}
	@Override
	public String toString() {
		return "EssStructure [id=" + id + ", esIdentifire=" + esIdentifire + ", esDate=" + esDate + ", esStatus="
				+ esStatus + ", esType=" + esType + ", esPublisher=" + esPublisher + ", esDescription=" + esDescription
				+ ", esCreator=" + esCreator + ", esTitle=" + esTitle + ", esOrder=" + esOrder + "]";
	}
	
}
