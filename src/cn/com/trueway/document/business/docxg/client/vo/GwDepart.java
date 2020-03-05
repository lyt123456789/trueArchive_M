package cn.com.trueway.document.business.docxg.client.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

// default package


/**
 * GwDepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCEXCHANGE_DEPART")
public class GwDepart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936609508094887208L;
	// Fields

	private String id;
	private String name;
	private Long ind;

	// Constructors

	/** default constructor */
	public GwDepart() {
	}

	/** minimal constructor */
	public GwDepart(String name) {
		this.name = name;
	}

	/** full constructor */
	public GwDepart(String name, Long ind) {
		this.name = name;
		this.ind = ind;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(generator = "c-assigned")
    @GenericGenerator(name = "c-assigned", strategy = "assigned")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IND", precision = 22, scale = 0)
	public Long getInd() {
		return this.ind;
	}

	public void setInd(Long ind) {
		this.ind = ind;
	}
  
}