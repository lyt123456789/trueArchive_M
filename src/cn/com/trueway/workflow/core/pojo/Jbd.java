package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对12345交办单表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-04-08 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_JBD")
public class Jbd {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name="JBDBH")
	private String jbdbh;
	
	@Column(name="SQR")
	private String sqr;
	
	@Column(name="XB")
	private String xb;
	
	@Column(name="LXDH")
	private String lxdh;
	
	@Column(name="SMDH")
	private String smdh;
	
	@Column(name="GDLY")
	private String gdly;
	
	@Column(name="DWDZ")
	private String dwdz;
	

	@Temporal(TemporalType.DATE)
	@Column(name="JBSJ")
	private Date jbsj;
	
	@Column(name="BLSX")
	private String blsx;
	
	@Column(name="ZYCD")
	private String zycd;
	
	@Column(name="SQFL")
	private String sqfl;
	
	@Column(name="SQGK")
	private String sqgk;
	
	@Column(name="GKGL")
	private String gkgl;
	
	@Column(name="XXDZ")
	private String xxdz;
	
	@Column(name="SQNR")
	private String sqnr;
	
	
	@Column(name="INSTANCEID",nullable = false)
	private String instanceid;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getJbdbh() {
		return jbdbh;
	}


	public void setJbdbh(String jbdbh) {
		this.jbdbh = jbdbh;
	}


	public String getSqr() {
		return sqr;
	}


	public void setSqr(String sqr) {
		this.sqr = sqr;
	}


	public String getXb() {
		return xb;
	}


	public void setXb(String xb) {
		this.xb = xb;
	}


	public String getLxdh() {
		return lxdh;
	}


	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}


	public String getSmdh() {
		return smdh;
	}


	public void setSmdh(String smdh) {
		this.smdh = smdh;
	}


	public String getGdly() {
		return gdly;
	}


	public void setGdly(String gdly) {
		this.gdly = gdly;
	}


	public String getDwdz() {
		return dwdz;
	}


	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}


	public Date getJbsj() {
		return jbsj;
	}


	public void setJbsj(Date jbsj) {
		this.jbsj = jbsj;
	}


	public String getBlsx() {
		return blsx;
	}


	public void setBlsx(String blsx) {
		this.blsx = blsx;
	}


	public String getZycd() {
		return zycd;
	}


	public void setZycd(String zycd) {
		this.zycd = zycd;
	}


	public String getSqfl() {
		return sqfl;
	}


	public void setSqfl(String sqfl) {
		this.sqfl = sqfl;
	}


	public String getSqgk() {
		return sqgk;
	}


	public void setSqgk(String sqgk) {
		this.sqgk = sqgk;
	}


	public String getGkgl() {
		return gkgl;
	}


	public void setGkgl(String gkgl) {
		this.gkgl = gkgl;
	}


	public String getXxdz() {
		return xxdz;
	}


	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}


	public String getSqnr() {
		return sqnr;
	}


	public void setSqnr(String sqnr) {
		this.sqnr = sqnr;
	}


	public String getInstanceid() {
		return instanceid;
	}


	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
	
	
}