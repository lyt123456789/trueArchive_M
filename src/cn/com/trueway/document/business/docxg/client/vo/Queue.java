package cn.com.trueway.document.business.docxg.client.vo;
// default package

import java.util.Date;


//@Entity
//@Table(name = "DOCEXCHANGE_QUEUE")
public class Queue implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6491452362753689954L;
	private String id;
	private String docguid;
	private String xto;
	private Long statuscode;
	private String status;
	private Date gottm;


	/** default constructor */
	public Queue() {
	}

	/** minimal constructor */
	public Queue(String docguid, String xto, Long statuscode, String status) {
		this.docguid = docguid;
		this.xto = xto;
		this.statuscode = statuscode;
		this.status = status;
	}

	/** full constructor */
	public Queue(String docguid, String xto, Long statuscode, String status,
			Date gottm) {
		this.docguid = docguid;
		this.xto = xto;
		this.statuscode = statuscode;
		this.status = status;
		this.gottm = gottm;
	}

	// Property accessors
	//@Id
	////@GeneratedValue(generator = "system-uuid")
	////@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	//@Column(name = "ID",length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//@Column(name = "DOCGUID", nullable = false, length = 36)
	public String getDocguid() {
		return this.docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	//@Column(name = "XTO", nullable = false, length = 500)
	public String getXto() {
		return this.xto;
	}

	public void setXto(String xto) {
		this.xto = xto;
	}

	//@Column(name = "STATUSCODE", nullable = true, precision = 22, scale = 0)
	public Long getStatuscode() {
		return this.statuscode;
	}

	public void setStatuscode(Long statuscode) {
		this.statuscode = statuscode;
	}

	//@Column(name = "STATUS", nullable = true, length = 500)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "GOTTM", length = 7,nullable=true)
	public Date getGottm() {
		return this.gottm;
	}

	public void setGottm(Date gottm) {
		this.gottm = gottm;
	}

}