package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对依申请公开表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-04-08 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_YSQGK")
public class Ysqgk {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name="LX")
	private Integer lx;
	
	@Column(name="XM")
	private String xm;
	
	@Column(name="GZDW")
	private String gzdw;
	
	@Column(name="ZJMC")
	private Integer zjmc;
	
	@Column(name="ZJHM")
	private String zjhm;
	
	@Column(name="TXDZ")
	private String txdz;
	
	@Column(name="YZBM")
	private String yzbm;
	
	@Column(name="LXDH")
	private String lxdh;
	
	@Column(name="CZ")
	private String cz;
	
	@Column(name="DZYX")
	private String dzyx;

	@Temporal(TemporalType.DATE)
	@Column(name="DQSJ")
	private Date dqsj;
	
	@Column(name="SQNRGY")
	private String sqnrgy;
	
	@Column(name="FR_MC")
	private String fr_mc;
	
	@Column(name="FR_ZZJGDM")
	private String fr_zzjgdm;
	
	@Column(name="FR_YYZZ")
	private String fr_yyzz;
	
	@Column(name="FR_FRDB")
	private String fr_frdb;
	
	@Column(name="FR_LXRXM")
	private String fr_lxrxm;
	
	@Column(name="FR_LXRDH")
	private String fr_lxrdh;
	
	@Column(name="FR_CZ")
	private String fr_cz;
	
	@Column(name="FR_LXRDZYX")
	private String fr_lxrdzyx;

	@Temporal(TemporalType.DATE)
	@Column(name="FR_SQSJ")
	private Date fr_sqsj;
	
	@Column(name="FR_SQNRGY")
	private String fr_sqnrgy;
	
	@Column(name="INSTANCEID",nullable = false)
	private String instanceid;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DJSJ")
	private Date djsj;
	
	@Column(name="DJRXM")
	private String djrxm;
	
	@Column(name="DJRID")
	private String djrid;
	
	@Transient
	private String zt;
	
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getDjrxm() {
		return djrxm;
	}

	public void setDjrxm(String djrxm) {
		this.djrxm = djrxm;
	}

	public String getDjrid() {
		return djrid;
	}

	public void setDjrid(String djrid) {
		this.djrid = djrid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getGzdw() {
		return gzdw;
	}

	public void setGzdw(String gzdw) {
		this.gzdw = gzdw;
	}

	public Integer getZjmc() {
		return zjmc;
	}

	public void setZjmc(Integer zjmc) {
		this.zjmc = zjmc;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getTxdz() {
		return txdz;
	}

	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public Date getDqsj() {
		return dqsj;
	}

	public void setDqsj(Date dqsj) {
		this.dqsj = dqsj;
	}

	public String getSqnrgy() {
		return sqnrgy;
	}

	public void setSqnrgy(String sqnrgy) {
		this.sqnrgy = sqnrgy;
	}

	public String getFr_mc() {
		return fr_mc;
	}

	public void setFr_mc(String fr_mc) {
		this.fr_mc = fr_mc;
	}

	public String getFr_zzjgdm() {
		return fr_zzjgdm;
	}

	public void setFr_zzjgdm(String fr_zzjgdm) {
		this.fr_zzjgdm = fr_zzjgdm;
	}

	public String getFr_yyzz() {
		return fr_yyzz;
	}

	public void setFr_yyzz(String fr_yyzz) {
		this.fr_yyzz = fr_yyzz;
	}

	public String getFr_frdb() {
		return fr_frdb;
	}

	public void setFr_frdb(String fr_frdb) {
		this.fr_frdb = fr_frdb;
	}

	public String getFr_lxrxm() {
		return fr_lxrxm;
	}

	public void setFr_lxrxm(String fr_lxrxm) {
		this.fr_lxrxm = fr_lxrxm;
	}

	public String getFr_lxrdh() {
		return fr_lxrdh;
	}

	public void setFr_lxrdh(String fr_lxrdh) {
		this.fr_lxrdh = fr_lxrdh;
	}

	public String getFr_cz() {
		return fr_cz;
	}

	public void setFr_cz(String fr_cz) {
		this.fr_cz = fr_cz;
	}

	public String getFr_lxrdzyx() {
		return fr_lxrdzyx;
	}

	public void setFr_lxrdzyx(String fr_lxrdzyx) {
		this.fr_lxrdzyx = fr_lxrdzyx;
	}

	public Date getFr_sqsj() {
		return fr_sqsj;
	}

	public void setFr_sqsj(Date fr_sqsj) {
		this.fr_sqsj = fr_sqsj;
	}

	public String getFr_sqnrgy() {
		return fr_sqnrgy;
	}

	public void setFr_sqnrgy(String fr_sqnrgy) {
		this.fr_sqnrgy = fr_sqnrgy;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
	
	
}
