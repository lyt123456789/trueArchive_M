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
@Table(name = "T_WF_CORE_SW")
public class Sw {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name="LWBT")
	private String lwbt;
	
	@Column(name="LWDW")
	private String lwdw;
	
	@Column(name="YFDW")
	private String yfdw;
	
	@Column(name="LWH")
	private String lwh;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FWSJ")
	private Date fwsj;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SWSJ")
	private Date swsj;
	
	@Column(name="FS")
	private String fs;
	
	@Column(name="ITEM_ID")
	private String item_id;
	
	@Column(name="GWLX")
	private String gwlx;
	
	@Column(name="INSTANCEID")
	private String instanceid;
	
	@Column(name="ZSDW")
	private String zsdw;
	
	@Column(name="CSDW")
	private String csdw;
	
	@Column(name="ZTC")
	private String ztc;
	
	@Column(name="JJCD")
	private String jjcd;
	
	@Transient
	private String wh;
	
	@Transient
	private String lwrq;
	
	@Transient
	private String wjbh;
	
	@Transient
	private String wjnr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLwbt() {
		return lwbt;
	}

	public void setLwbt(String lwbt) {
		this.lwbt = lwbt;
	}

	public String getLwdw() {
		return lwdw;
	}

	public void setLwdw(String lwdw) {
		this.lwdw = lwdw;
	}

	public String getYfdw() {
		return yfdw;
	}

	public void setYfdw(String yfdw) {
		this.yfdw = yfdw;
	}

	public String getLwh() {
		return lwh;
	}

	public void setLwh(String lwh) {
		this.lwh = lwh;
	}

	public Date getFwsj() {
		return fwsj;
	}

	public void setFwsj(Date fwsj) {
		this.fwsj = fwsj;
	}

	public Date getSwsj() {
		return swsj;
	}

	public void setSwsj(Date swsj) {
		this.swsj = swsj;
	}

	public String getFs() {
		return fs;
	}

	public void setFs(String fs) {
		this.fs = fs;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getGwlx() {
		return gwlx;
	}

	public void setGwlx(String gwlx) {
		this.gwlx = gwlx;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getZsdw() {
		return zsdw;
	}

	public void setZsdw(String zsdw) {
		this.zsdw = zsdw;
	}

	public String getCsdw() {
		return csdw;
	}

	public void setCsdw(String csdw) {
		this.csdw = csdw;
	}

	public String getZtc() {
		return ztc;
	}

	public void setZtc(String ztc) {
		this.ztc = ztc;
	}

	public String getJjcd() {
		return jjcd;
	}

	public void setJjcd(String jjcd) {
		this.jjcd = jjcd;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	public String getLwrq() {
		return lwrq;
	}

	public void setLwrq(String lwrq) {
		this.lwrq = lwrq;
	}

	public String getWjbh() {
		return wjbh;
	}

	public void setWjbh(String wjbh) {
		this.wjbh = wjbh;
	}

	public String getWjnr() {
		return wjnr;
	}

	public void setWjnr(String wjnr) {
		this.wjnr = wjnr;
	}
	
}