package cn.com.trueway.document.business.docxg.client.vo;

// default package

import java.util.Date;

/**
 * 描述：OA发文公文信息
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 11:58:05 AM
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
//@Entity
//@Table(name = "DOCEXCHANGE_DOCBOX")
public class BaseDoc implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -6162095726761969752L;
	/**
	 * 唯一标识
	 */
	private String docguid;
	/**
	 * 发送者编号
	 */
	private String sender;
	/**
	 * 主送单位
	 */
	private String xto;
	/**
	 * 抄送单位
	 */
	private String xcc;
	/**
	 * 公文标题
	 */
	private String title;
	/**
	 * 公文主题词
	 */
	private String keyword;
	/**
	 * 公文类型
	 */
	private String doctype;
	/**
	 * 
	 */
	private String origindocguid;
	/**
	 * 发送时间
	 */
	private Date submittm;
	/**
	 * 紧急程度
	 */
	private Long priority;
	/**
	 * 发送状态
	 */
	private Long sendstat;
	/**
	 * 文号(标识)
	 */
	private String jgdz;
	/**
	 * 文号(年号)
	 */
	private String fwnh;
	/**
	 * 发文单位
	 */
	private String fwjg;
	/**
	 * 印发单位
	 */
	private String yfdw;
	/**
	 * 签发人
	 */
	private String qfr;
	/**
	 * 印发日期
	 */
	private String yfrq;
	/**
	 * 印发分数
	 */
	private Long yffs;
	/**
	 * 发文号
	 */
	private String fwxh;
	/**
	 * 版式文件id
	 */
	private String cebid;
	/**
	 * 公文xml
	 */
	private String bodyxml;
	
	private String sourceFrom;
	
	private String xtoName;
	
	private String xccName;
	
	private String wh;
	
	// Constructors

	/** default constructor */
	public BaseDoc() {
	}

	/** minimal constructor */
	public BaseDoc(Date submittm) {
		this.submittm = submittm;
	}

	/** full constructor */
	public BaseDoc(String sender, String xto, String xcc, String title,
			String keyword, String doctype, String origindocguid,
			Date submittm, Long priority, Long sendstat, String jgdz,
			String fwnh, String fwjg, String yfdw, String qfr, String yfrq,
			Long yffs, String fwxh, String cebid, String bodyxml,String wh) {
		this.sender = sender;
		this.xto = xto;
		this.xcc = xcc;
		this.title = title;
		this.keyword = keyword;
		this.doctype = doctype;
		this.origindocguid = origindocguid;
		this.submittm = submittm;
		this.priority = priority;
		this.sendstat = sendstat;
		this.jgdz = jgdz;
		this.fwnh = fwnh;
		this.fwjg = fwjg;
		this.yfdw = yfdw;
		this.qfr = qfr;
		this.yfrq = yfrq;
		this.yffs = yffs;
		this.fwxh = fwxh;
		this.cebid = cebid;
		this.bodyxml = bodyxml;
		this.wh = wh;
	}

	// Property accessors
//	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
//	@Column(name = "DOCGUID",length = 36)
	public String getDocguid() {
		return this.docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

//	@Column(name = "SENDER", length = 500)
	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

//	@Column(name = "XTO")
	public String getXto() {
		return this.xto;
	}

	public void setXto(String xto) {
		this.xto = xto;
	}

//	@Column(name = "XCC")
	public String getXcc() {
		return this.xcc;
	}

	public void setXcc(String xcc) {
		this.xcc = xcc;
	}

//	@Column(name = "TITLE", length = 1000)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	@Column(name = "KEYWORD", length = 1000)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

//	@Column(name = "DOCTYPE", length = 50)
	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

//	@Column(name = "ORIGINDOCGUID", length = 36)
	public String getOrigindocguid() {
		return this.origindocguid;
	}

	public void setOrigindocguid(String origindocguid) {
		this.origindocguid = origindocguid;
	}

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "SUBMITTM", nullable = true, length = 7)
	public Date getSubmittm() {
		return this.submittm;
	}

	public void setSubmittm(Date submittm) {
		this.submittm = submittm;
	}

//	@Column(name = "PRIORITY", precision = 22, scale = 0)
	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

//	@Column(name = "SENDSTAT",nullable=true ,precision = 22, scale = 0)
	public Long getSendstat() {
		return this.sendstat;
	}

	public void setSendstat(Long sendstat) {
		this.sendstat = sendstat;
	}

//	@Column(name = "JGDZ", length = 20)
	public String getJgdz() {
		return this.jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

//	@Column(name = "FWNH", length = 20)
	public String getFwnh() {
		return this.fwnh;
	}

	public void setFwnh(String fwnh) {
		this.fwnh = fwnh;
	}

//	@Column(name = "FWJG", length = 50)
	public String getFwjg() {
		return this.fwjg;
	}

	public void setFwjg(String fwjg) {
		this.fwjg = fwjg;
	}

//	@Column(name = "YFDW", length = 50)
	public String getYfdw() {
		return this.yfdw;
	}

	public void setYfdw(String yfdw) {
		this.yfdw = yfdw;
	}

//	@Column(name = "QFR", length = 20)
	public String getQfr() {
		return this.qfr;
	}

	public void setQfr(String qfr) {
		this.qfr = qfr;
	}

//	@Column(name = "YFRQ", length = 20)
	public String getYfrq() {
		return this.yfrq;
	}

	public void setYfrq(String yfrq) {
		this.yfrq = yfrq;
	}

//	@Column(name = "YFFS", precision = 22, scale = 0)
	public Long getYffs() {
		return this.yffs;
	}

	public void setYffs(Long yffs) {
		this.yffs = yffs;
	}

//	@Column(name = "FWXH", length = 20)
	public String getFwxh() {
		return this.fwxh;
	}

	public void setFwxh(String fwxh) {
		this.fwxh = fwxh;
	}

//	@Column(name = "CEBID", length = 50)
	public String getCebid() {
		return this.cebid;
	}

	public void setCebid(String cebid) {
		this.cebid = cebid;
	}

//	@Column(name = "BODYXML")
	public String getBodyxml() {
		return this.bodyxml;
	}

	public void setBodyxml(String bodyxml) {
		this.bodyxml = bodyxml;
	}
	
//	@Column(name = "SOURCEFROM")
	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getXtoName() {
		return xtoName;
	}

	public void setXtoName(String xtoName) {
		this.xtoName = xtoName;
	}

	public String getXccName() {
		return xccName;
	}

	public void setXccName(String xccName) {
		this.xccName = xccName;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}
	
}