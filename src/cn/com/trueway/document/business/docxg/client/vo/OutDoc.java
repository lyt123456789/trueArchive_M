package cn.com.trueway.document.business.docxg.client.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;


/**
 * 描述：公文对象<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 5:18:10 PM 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：Dec 10, 2010 修改人：fanxh, 实现Comparable接口，按问号排序<br><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
//@Entity
//@Table(name = "OA_OUT_DOC_DOCBOX")
public class OutDoc implements Comparable<OutDoc>{

	@Transient
	private static final long serialVersionUID = 4835972077527954074L;
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
	 * 完整的主送单位，格式 单位名称|单位Id|发文份数|单位公章打印标识名称
	 */
	private String xtoFull;
	/**
	 * 主送单位名称
	 */
	private String xtoName;
	/**
	 * 抄送单位
	 */
	private String xcc;
	/**
	 * 完整的抄送单位，格式 单位名称|单位Id|发文份数|单位公章打印标识名称
	 */
	private String xccFull;	
	/**
	 * 抄送单位名称
	 */
	private String xccName;
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
	@SuppressWarnings("unused")
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

	// 附加的一些属性
	/**
	 * 公文的状态：待阅、已阅、待签、已签、待发送、已发送
	 */
	private String status;

	/**
	 * 对应流程的实例id
	 */
	private String instanceId;
	
	/**
	 * 公文交换接口发送成功时返回的唯一标识
	 */
	private String resultFlag;
	
	private String dense;
	
	//联合发文单位
	private String lhdw;
	
	//会签意见
	private String hqyj;
	
	//拟使用文号
	private String virtualWH;
	/**
	 *抄送内部人员
	 */
	private String xccInnerId;
	private String xccInnerName;
	
//	@Column(name = "DENSE", length = 50)
	public String getDense() {
		return dense;
	}

	public void setDense(String dense) {
		this.dense = dense;
	}

	/**
	 * 本公文所关联的正文附件
	 */
	private List<SendAttachments> atts = new ArrayList<SendAttachments>();

	

	/**
	 * 本公文所关联的附加附件
	 */
	private List<SendAttachmentsext> attExts = new ArrayList<SendAttachmentsext>();
	/**
	 * 公开范围
	 */
	private String gkfw;
	// Constructors
	
	private int lwType;

	public OutDoc(String docguid, String sender, String xto, String xcc,
			String title, String keyword, String doctype, String origindocguid,
			Date submittm, Long priority, Long sendstat, String jgdz,
			String fwnh, String fwjg, String yfdw, String qfr, String yfrq,
			Long yffs, String fwxh, String cebid, String bodyxml,
			String instanceId,String gkfw,String xccInnerId,String xccInnerName,String fh,int lwType) {
		super();
		this.docguid = docguid;
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
		this.instanceId = instanceId;
		this.gkfw=gkfw;
		this.xccInnerId=xccInnerId;
		this.xccInnerName=xccInnerName;
		this.lwType=lwType;
	}

	public OutDoc() {
	}
	
	public int compareTo(OutDoc o){
		String thisJgdz = this.jgdz == null ? "" : this.jgdz;
		String thisFwnh = this.fwnh == null ? "" : this.fwnh;
		String thisFwxh = this.fwxh == null || this.fwxh.equals("") ? "0" : this.fwxh;
		
		String oJgdz = o.getJgdz() == null ? "" : o.getJgdz();
		String oFwnh = o.getFwnh() == null ? "" : o.getFwnh();
		String oFwxh = o.getFwxh() == null || o.getFwxh().equals("") ? "0" : o.getFwxh();
		
		if (thisJgdz.equals(oJgdz)){
			if (thisFwnh.equals(oFwnh)){
				return Integer.parseInt(thisFwxh) - Integer.parseInt(oFwxh);
			}else{
				return thisFwnh.compareTo(oFwnh);
			}
		}else{
			return thisJgdz.compareTo(oJgdz);
		}
	}

	/**
	 * 自定义构造，提供instanceId
	 * 
	 * @param instanceId
	 */
	public OutDoc(String docId,String instanceId) {
		this.docguid = docId;
		this.instanceId = instanceId;
	}

	// Property accessors
//	@Id
//	@Column(name = "DOCGUID", length = 36)
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
	
//	@Column(name = "XTO_FULL")
	public String getXtoFull() {
		return xtoFull;
	}

	public void setXtoFull(String xtoFull) {
		this.xtoFull = xtoFull;
	}
	
//	@Column(name = "XTO_NAME")
	public String getXtoName() {
		return xtoName;
	}

	public void setXtoName(String xtoName) {
		this.xtoName = xtoName;
	}

//	@Column(name = "XCC")
	public String getXcc() {
		return this.xcc;
	}

	public void setXcc(String xcc) {
		this.xcc = xcc;
	}

//	@Column(name = "XCC_FULL")
	public String getXccFull() {
		return xccFull;
	}

	public void setXccFull(String xccFull) {
		this.xccFull = xccFull;
	}

//	@Column(name = "XCC_NAME")
	public String getXccName() {
		return xccName;
	}

	public void setXccName(String xccName) {
		this.xccName = xccName;
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

//	@Column(name = "SENDSTAT", precision = 22, scale = 0)
	public Long getSendstat() {
		return 1l;
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

	// 附加的一些属性

//	@Column(name = "STATUS", length = 255)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	@Column(name = "INSTANCEID", length = 36)
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Transient
	public List<SendAttachments> getAtts() {
		return atts;
	}

	public void setAtts(List<SendAttachments> atts) {
		this.atts = atts;
	}

	@Transient
	public List<SendAttachmentsext> getAttExts() {
		return attExts;
	}

	public void setAttExts(List<SendAttachmentsext> attExts) {
		this.attExts = attExts;
	}
	
//	@Column(name = "RESULT_FLAG", length = 50)
	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	
//	@Column(name = "GKFW")
	public String getGkfw() {
		return gkfw;
	}

	public void setGkfw(String gkfw) {
		this.gkfw = gkfw;
	}
	
//	@Column(name = "LHDW")
	public String getLhdw() {
		return lhdw;
	}

	public void setLhdw(String lhdw) {
		this.lhdw = lhdw;
	}

//	@Column(name = "HQYJ")
	public String getHqyj() {
		return hqyj;
	}

	public void setHqyj(String hqyj) {
		this.hqyj = hqyj;
	}
//	@Column(name = "XCCINNERID")
	public String getXccInnerId() {
		return xccInnerId;
	}

	public void setXccInnerId(String xccInnerId) {
		this.xccInnerId = xccInnerId;
	}
//	@Column(name = "XCCINNERNAME")
	public String getXccInnerName() {
		return xccInnerName;
	}

	public void setXccInnerName(String xccInnerName) {
		this.xccInnerName = xccInnerName;
	}
//	@Column(name = "VIRTUALWH")
	public String getVirtualWH() {
		return virtualWH;
	}
	public void setVirtualWH(String virtualWH) {
		this.virtualWH = virtualWH;
	}
	
//	@Column(name = "LWTYPE", length = 50)
	public int getLwType() {
		return lwType;
	}

	public void setLwType(int lwType) {
		this.lwType = lwType;
	}
	
}
