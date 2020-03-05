package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 归档树结构信息表
 * @author alike
 *
 */
@Entity
@Table(name="T_ARCHIVE_TEMPLATE_MRZ")
public class MrzPojo {
	
	@Column(name="IDSTRUCTURE")
	private Integer idstructure;
	@Id
	@Column(name="IDTAG")
	private Integer idtag;
	@Column(name="MRZ")
	private String mrz;
	@Column(name="TYPE")
	private String type;//1:固定值  2:当前值
	
	
	public Integer getIdstructure() {
		return idstructure;
	}
	public void setIdstructure(Integer idstructure) {
		this.idstructure = idstructure;
	}
	public Integer getIdtag() {
		return idtag;
	}
	public void setIdtag(Integer idtag) {
		this.idtag = idtag;
	}
	public String getMrz() {
		return mrz;
	}
	public void setMrz(String mrz) {
		this.mrz = mrz;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
}
