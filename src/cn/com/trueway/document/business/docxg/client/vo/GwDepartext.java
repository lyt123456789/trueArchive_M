package cn.com.trueway.document.business.docxg.client.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

// default package


/**
 * GwDepartext entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCEXCHANGE_DEPARTEXT")
public class GwDepartext implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5171050277004598304L;
	private String guid;
	private String name;
	private String parentid;
	private Long ind;
	private String moblie;
	/**
	 * 单位公章打印标识名称
	 */
	private String GZName;

	// Constructors

	/** default constructor */
	public GwDepartext() {
	}

	/** full constructor */
	public GwDepartext(String name, String parentid, Long ind, String moblie) {
		this.name = name;
		this.parentid = parentid;
		this.ind = ind;
		this.moblie = moblie;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "c-assigned")
    @GenericGenerator(name = "c-assigned", strategy = "assigned")
	@Column(name = "GUID", unique = true, nullable = false, length = 38)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PARENTID", length = 38)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "IND", precision = 22, scale = 0)
	public Long getInd() {
		return this.ind;
	}

	public void setInd(Long ind) {
		this.ind = ind;
	}

	@Column(name = "MOBLIE", length = 2000)
	public String getMoblie() {
		return this.moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	@Column(name = "GZNAME", length = 50)
	public String getGZName() {
		return GZName;
	}

	public void setGZName(String name) {
		GZName = name;
	}

}