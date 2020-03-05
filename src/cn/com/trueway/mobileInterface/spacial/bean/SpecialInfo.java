package cn.com.trueway.mobileInterface.spacial.bean;

public class SpecialInfo {
	String zxbdw = "";	//专项办单位 0：环整办 1：渣土办 2：废整办 3：城管办 4：广告办 5：控违办 6：弄环办
	String jbdw = "";	//交办单位
	String ly = "";		//来源
	String dd = "";		//地点
	String flfg = "";	//法律法规
	String jbsxms = ""; //交办事项概述
	String zgyq	= "";	//整改要求
	String zgsj = "";	//整改时间
	String lxr = "";	//联系人
	String lxdh = "";	//联系电话
	String sj = "";		//时间
	
	public SpecialInfo(String zxbdw, String jbdw, String ly, String dd,
			String flfg, String jbsxms, String zgyq, String zgsj, String lxr,
			String lxdh, String sj) {
		super();
		this.zxbdw = zxbdw;
		this.jbdw = jbdw;
		this.ly = ly;
		this.dd = dd;
		this.flfg = flfg;
		this.jbsxms = jbsxms;
		this.zgyq = zgyq;
		this.zgsj = zgsj;
		this.lxr = lxr;
		this.lxdh = lxdh;
		this.sj = sj;
	}

	public String getZxbdw() {
		return zxbdw;
	}
	public void setZxbdw(String zxbdw) {
		this.zxbdw = zxbdw;
	}
	public String getJbdw() {
		return jbdw;
	}
	public void setJbdw(String jbdw) {
		this.jbdw = jbdw;
	}
	public String getLy() {
		return ly;
	}
	public void setLy(String ly) {
		this.ly = ly;
	}
	public String getDd() {
		return dd;
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getFlfg() {
		return flfg;
	}
	public void setFlfg(String flfg) {
		this.flfg = flfg;
	}
	public String getJbsxms() {
		return jbsxms;
	}
	public void setJbsxms(String jbsxms) {
		this.jbsxms = jbsxms;
	}
	public String getZgyq() {
		return zgyq;
	}
	public void setZgyq(String zgyq) {
		this.zgyq = zgyq;
	}
	public String getZgsj() {
		return zgsj;
	}
	public void setZgsj(String zgsj) {
		this.zgsj = zgsj;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	
}
