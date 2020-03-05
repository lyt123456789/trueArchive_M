package cn.com.trueway.document.business.docxg.client.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DOCEXCHANGE_RELATIONSHIP")
public class DepRelationShip implements java.io.Serializable{
	private static final long serialVersionUID = -1062997635084762296L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "GTJ_DEPID", nullable = false)
	private String gtj_depId;
	
	@Column(name = "DOCXG_DEPID", nullable = false)
	private String docxg_depId;
	
	@Column(name = "DOCXG_DEPNAME")
	private String  docxg_depName;

	@Transient
	private String gtj_depName;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGtj_depId() {
		return gtj_depId;
	}

	public void setGtj_depId(String gtj_depId) {
		this.gtj_depId = gtj_depId;
	}

	public String getDocxg_depId() {
		return docxg_depId;
	}

	public void setDocxg_depId(String docxg_depId) {
		this.docxg_depId = docxg_depId;
	}

	public String getGtj_depName() {
		return gtj_depName;
	}

	public void setGtj_depName(String gtj_depName) {
		this.gtj_depName = gtj_depName;
	}

	public String getDocxg_depName() {
		return docxg_depName;
	}

	public void setDocxg_depName(String docxg_depName) {
		this.docxg_depName = docxg_depName;
	}
}
