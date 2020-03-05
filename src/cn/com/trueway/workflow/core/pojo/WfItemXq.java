package cn.com.trueway.workflow.core.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对行权事项表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-04-08 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_ITEM_XQ")
public class WfItemXq {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;

	@Column(name = "VC_SXBM")
	private String vc_sxbm;

	@Column(name = "VC_SXMC")
	private String vc_sxmc;

	@Column(name = "I_YXJ")
	private int i_yxj;

	@Column(name = "I_SPSX")
	private int i_spsx;

	@Column(name = "VC_SPSXSM")
	private String vc_spsxsm;

	@Column(name = "VC_SPZT")
	private String vc_spzt;

	@Column(name = "VC_SXXZ")
	private String vc_sxxz;

	@Column(name = "I_XH")
	private String i_xh;

	@Column(name = "VC_STATUS")
	private String vc_status;

	@Column(name = "VC_SXFL")
	private String vc_sxfl;

	@Column(name = "VC_SXFW")
	private String vc_sxfw;

	@Column(name = "B_FBWW")
	private String b_fbww;

	@Column(name = "VC_LCID")
	private String vc_lcid;

	@Column(name = "B_DEBUG")
	private String b_debug;

	@Column(name = "VC_BLBM")
	private String vc_blbm;

	@Column(name = "VC_CKRYXM")
	private String vc_ckryxm;

	@Column(name = "VC_CKRYID")
	private String vc_ckryid;

	@Column(name = "B_YDZF")
	private String b_ydzf;

	@Column(name = "VC_BJLX")
	private String vc_bjlx;

	@Column(name = "VC_SBTJ")
	private String vc_sbtj;

	@Column(name = "VC_SBSM")
	private String vc_sbsm;

	@Column(name = "VC_SFBZ")
	private String vc_sfbz;

	@Column(name = "VC_LBBM")
	private String vc_lbbm;

	@Column(name = "B_RISK")
	private int b_risk;

	@Column(name = "VC_RISKTYPE")
	private String vc_risktype;

	@Column(name = "VC_RISKDESC")
	private String vc_riskdesc;

	@Column(name = "VC_RISKRESULT")
	private String vc_riskresult;

	@Column(name = "VC_DSXBM")
	private String vc_dsxbm;

	@Column(name = "VC_DSXMC")
	private String vc_dsxmc;

	@Column(name = "C_CREATEDATE")
	private Timestamp c_createdate;

	@Column(name = "VC_ZTXZ")
	private String vc_ztxz;

	@Column(name = "VC_ZDSJ")
	private Timestamp vc_zdsj;

	@Column(name = "VC_WBJ")
	private String vc_wbj;

	@Column(name = "VC_SBZT")
	private String vc_sbzt;

	@Column(name = "VC_FDSX")
	private String vc_fdsx;

	@Column(name = "B_WSSL")
	private String b_wssl;

	@Column(name = "VC_TEL")
	private String vc_tel;

	@Column(name = "VC_BLDD")
	private String vc_bldd;

	@Column(name = "VC_ZFZTYJ")
	private String vc_zfztyj;

	@Column(name = "VC_ZFXWYJ")
	private String vc_zfxwyj;

	@Column(name = "VC_JDDH")
	private String vc_jddh;

	@Column(name = "VC_FWZN")
	private String vc_fwzn;

	@Column(name = "VC_BLLJ")
	private String vc_bllj;

	@Column(name = "VC_CJWT")
	private String vc_cjwt;

	@Column(name = "VC_BZ")
	private String vc_bz;

	@Column(name = "B_SPZX")
	private String b_spzx;

	@Column(name = "B_SKBS")
	private String b_skbs;

	@Column(name = "B_SF")
	private String b_sf;

	@Column(name = "VC_SFLX")
	private String vc_sflx;

	@Column(name = "VC_SFX")
	private String vc_sfx;

	@Column(name = "VC_SKR")
	private String vc_skr;

	@Column(name = "VC_KHH")
	private String vc_khh;

	@Column(name = "VC_KHZH")
	private String vc_khzh;

	@Column(name = "VC_KHBLDD")
	private String vc_khbldd;

	@Column(name = "VC_ZSDW")
	private String vc_zsdw;

	@Column(name = "VC_ZSDWBM")
	private String vc_zsdwbm;

	@Column(name = "VC_QT")
	private String vc_qt;

	@Column(name = "VC_GRFWFL")
	private String vc_grfwfl;

	@Column(name = "VC_QYFWFL")
	private String vc_qyfwfl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_sxbm() {
		return vc_sxbm;
	}

	public void setVc_sxbm(String vcSxbm) {
		vc_sxbm = vcSxbm;
	}

	public String getVc_sxmc() {
		return vc_sxmc;
	}

	public void setVc_sxmc(String vcSxmc) {
		vc_sxmc = vcSxmc;
	}

	public int getI_yxj() {
		return i_yxj;
	}

	public void setI_yxj(int iYxj) {
		i_yxj = iYxj;
	}

	public int getI_spsx() {
		return i_spsx;
	}

	public void setI_spsx(int iSpsx) {
		i_spsx = iSpsx;
	}

	public String getVc_spsxsm() {
		return vc_spsxsm;
	}

	public void setVc_spsxsm(String vcSpsxsm) {
		vc_spsxsm = vcSpsxsm;
	}

	public String getVc_spzt() {
		return vc_spzt;
	}

	public void setVc_spzt(String vcSpzt) {
		vc_spzt = vcSpzt;
	}

	public String getVc_sxxz() {
		return vc_sxxz;
	}

	public void setVc_sxxz(String vcSxxz) {
		vc_sxxz = vcSxxz;
	}

	public String getI_xh() {
		return i_xh;
	}

	public void setI_xh(String iXh) {
		i_xh = iXh;
	}

	public String getVc_status() {
		return vc_status;
	}

	public void setVc_status(String vcStatus) {
		vc_status = vcStatus;
	}

	public String getVc_sxfl() {
		return vc_sxfl;
	}

	public void setVc_sxfl(String vcSxfl) {
		vc_sxfl = vcSxfl;
	}

	public String getVc_sxfw() {
		return vc_sxfw;
	}

	public void setVc_sxfw(String vcSxfw) {
		vc_sxfw = vcSxfw;
	}

	public String getB_fbww() {
		return b_fbww;
	}

	public void setB_fbww(String bFbww) {
		b_fbww = bFbww;
	}

	public String getVc_lcid() {
		return vc_lcid;
	}

	public void setVc_lcid(String vcLcid) {
		vc_lcid = vcLcid;
	}

	public String getB_debug() {
		return b_debug;
	}

	public void setB_debug(String bDebug) {
		b_debug = bDebug;
	}

	public String getVc_blbm() {
		return vc_blbm;
	}

	public void setVc_blbm(String vcBlbm) {
		vc_blbm = vcBlbm;
	}

	public String getVc_ckryxm() {
		return vc_ckryxm;
	}

	public void setVc_ckryxm(String vcCkryxm) {
		vc_ckryxm = vcCkryxm;
	}

	public String getVc_ckryid() {
		return vc_ckryid;
	}

	public void setVc_ckryid(String vcCkryid) {
		vc_ckryid = vcCkryid;
	}

	public String getB_ydzf() {
		return b_ydzf;
	}

	public void setB_ydzf(String bYdzf) {
		b_ydzf = bYdzf;
	}

	public String getVc_bjlx() {
		return vc_bjlx;
	}

	public void setVc_bjlx(String vcBjlx) {
		vc_bjlx = vcBjlx;
	}

	public String getVc_sbtj() {
		return vc_sbtj;
	}

	public void setVc_sbtj(String vcSbtj) {
		vc_sbtj = vcSbtj;
	}

	public String getVc_sbsm() {
		return vc_sbsm;
	}

	public void setVc_sbsm(String vcSbsm) {
		vc_sbsm = vcSbsm;
	}

	public String getVc_sfbz() {
		return vc_sfbz;
	}

	public void setVc_sfbz(String vcSfbz) {
		vc_sfbz = vcSfbz;
	}

	public String getVc_lbbm() {
		return vc_lbbm;
	}

	public void setVc_lbbm(String vcLbbm) {
		vc_lbbm = vcLbbm;
	}

	public int getB_risk() {
		return b_risk;
	}

	public void setB_risk(int bRisk) {
		b_risk = bRisk;
	}

	public String getVc_risktype() {
		return vc_risktype;
	}

	public void setVc_risktype(String vcRisktype) {
		vc_risktype = vcRisktype;
	}

	public String getVc_riskdesc() {
		return vc_riskdesc;
	}

	public void setVc_riskdesc(String vcRiskdesc) {
		vc_riskdesc = vcRiskdesc;
	}

	public String getVc_riskresult() {
		return vc_riskresult;
	}

	public void setVc_riskresult(String vcRiskresult) {
		vc_riskresult = vcRiskresult;
	}

	public String getVc_dsxbm() {
		return vc_dsxbm;
	}

	public void setVc_dsxbm(String vcDsxbm) {
		vc_dsxbm = vcDsxbm;
	}

	public String getVc_dsxmc() {
		return vc_dsxmc;
	}

	public void setVc_dsxmc(String vcDsxmc) {
		vc_dsxmc = vcDsxmc;
	}

	public Timestamp getC_createdate() {
		return c_createdate;
	}

	public void setC_createdate(Timestamp cCreatedate) {
		c_createdate = cCreatedate;
	}

	public String getVc_ztxz() {
		return vc_ztxz;
	}

	public void setVc_ztxz(String vcZtxz) {
		vc_ztxz = vcZtxz;
	}

	public Timestamp getVc_zdsj() {
		return vc_zdsj;
	}

	public void setVc_zdsj(Timestamp vcZdsj) {
		vc_zdsj = vcZdsj;
	}

	public String getVc_wbj() {
		return vc_wbj;
	}

	public void setVc_wbj(String vcWbj) {
		vc_wbj = vcWbj;
	}

	public String getVc_sbzt() {
		return vc_sbzt;
	}

	public void setVc_sbzt(String vcSbzt) {
		vc_sbzt = vcSbzt;
	}

	public String getVc_fdsx() {
		return vc_fdsx;
	}

	public void setVc_fdsx(String vcFdsx) {
		vc_fdsx = vcFdsx;
	}

	public String getB_wssl() {
		return b_wssl;
	}

	public void setB_wssl(String bWssl) {
		b_wssl = bWssl;
	}

	public String getVc_tel() {
		return vc_tel;
	}

	public void setVc_tel(String vcTel) {
		vc_tel = vcTel;
	}

	public String getVc_bldd() {
		return vc_bldd;
	}

	public void setVc_bldd(String vcBldd) {
		vc_bldd = vcBldd;
	}

	public String getVc_zfztyj() {
		return vc_zfztyj;
	}

	public void setVc_zfztyj(String vcZfztyj) {
		vc_zfztyj = vcZfztyj;
	}

	public String getVc_zfxwyj() {
		return vc_zfxwyj;
	}

	public void setVc_zfxwyj(String vcZfxwyj) {
		vc_zfxwyj = vcZfxwyj;
	}

	public String getVc_jddh() {
		return vc_jddh;
	}

	public void setVc_jddh(String vcJddh) {
		vc_jddh = vcJddh;
	}

	public String getVc_fwzn() {
		return vc_fwzn;
	}

	public void setVc_fwzn(String vcFwzn) {
		vc_fwzn = vcFwzn;
	}

	public String getVc_bllj() {
		return vc_bllj;
	}

	public void setVc_bllj(String vcBllj) {
		vc_bllj = vcBllj;
	}

	public String getVc_cjwt() {
		return vc_cjwt;
	}

	public void setVc_cjwt(String vcCjwt) {
		vc_cjwt = vcCjwt;
	}

	public String getVc_bz() {
		return vc_bz;
	}

	public void setVc_bz(String vcBz) {
		vc_bz = vcBz;
	}

	public String getB_spzx() {
		return b_spzx;
	}

	public void setB_spzx(String bSpzx) {
		b_spzx = bSpzx;
	}

	public String getB_skbs() {
		return b_skbs;
	}

	public void setB_skbs(String bSkbs) {
		b_skbs = bSkbs;
	}

	public String getB_sf() {
		return b_sf;
	}

	public void setB_sf(String bSf) {
		b_sf = bSf;
	}

	public String getVc_sflx() {
		return vc_sflx;
	}

	public void setVc_sflx(String vcSflx) {
		vc_sflx = vcSflx;
	}

	public String getVc_sfx() {
		return vc_sfx;
	}

	public void setVc_sfx(String vcSfx) {
		vc_sfx = vcSfx;
	}

	public String getVc_skr() {
		return vc_skr;
	}

	public void setVc_skr(String vcSkr) {
		vc_skr = vcSkr;
	}

	public String getVc_khh() {
		return vc_khh;
	}

	public void setVc_khh(String vcKhh) {
		vc_khh = vcKhh;
	}

	public String getVc_khzh() {
		return vc_khzh;
	}

	public void setVc_khzh(String vcKhzh) {
		vc_khzh = vcKhzh;
	}

	public String getVc_khbldd() {
		return vc_khbldd;
	}

	public void setVc_khbldd(String vcKhbldd) {
		vc_khbldd = vcKhbldd;
	}

	public String getVc_zsdw() {
		return vc_zsdw;
	}

	public void setVc_zsdw(String vcZsdw) {
		vc_zsdw = vcZsdw;
	}

	public String getVc_zsdwbm() {
		return vc_zsdwbm;
	}

	public void setVc_zsdwbm(String vcZsdwbm) {
		vc_zsdwbm = vcZsdwbm;
	}

	public String getVc_qt() {
		return vc_qt;
	}

	public void setVc_qt(String vcQt) {
		vc_qt = vcQt;
	}

	public String getVc_grfwfl() {
		return vc_grfwfl;
	}

	public void setVc_grfwfl(String vcGrfwfl) {
		vc_grfwfl = vcGrfwfl;
	}

	public String getVc_qyfwfl() {
		return vc_qyfwfl;
	}

	public void setVc_qyfwfl(String vcQyfwfl) {
		vc_qyfwfl = vcQyfwfl;
	}
	
}
