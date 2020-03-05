package cn.com.trueway.document.business.model;

// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;


/**
 * OaDocReceive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_DOC_RECEIVE")
public class RecDoc implements java.io.Serializable {

	private static final long serialVersionUID = -8018654133484306201L;
	private String queueId;
	private String docguid;
	private String doctype;
	private String keyword;
	private String priority;
	private String queueXto;
	private String sendername;
	private Date submittm;
	private String title;
	private String wh;
	private String xcc;
	private String xto;
	private String yfdw;
	private String yfrq;
	private String status;
	private String bwType;
	private String printFlag;
	private Date recDate;
	private String sourceStatus;
	private String writeName;
	private Date writeDate;
	
	//市府办请示件办理流程
	private String bwtype;
	private String qsbWh;
	private String yffs;
	
	private String xtoName;
	private String xccName;
	
	private List<ReceiveAttachments> atts;
	private List<ReceiveAttachmentsext> attsext=new ArrayList<ReceiveAttachmentsext>();
	
	private String cebid;
	//收文查看状态
	private int recDocViewStatus;
	
	@Transient
	public int getRecDocViewStatus() {
		return recDocViewStatus;
	}

	public void setRecDocViewStatus(int recDocViewStatus) {
		this.recDocViewStatus = recDocViewStatus;
	}
	//备注
	private String beizhu;

	//所有查看该收文的用户列表字段
	private String viewStatus;

	@Column(name = "VIEWSTATUS")
	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	@Transient
	public List<ReceiveAttachments> getAtts() {
		return atts;
	}

	public void setAtts(List<ReceiveAttachments> atts) {
		this.atts = atts;
	}

	public RecDoc() {
	}

	

	public RecDoc(String queueId, String docguid, String doctype,
			String keyword, String priority, String queueXto,
			String sendername, Date submittm, String title, String wh,
			String xcc, String xto, String yfdw, String yfrq, String status,
			String bwType, String printFlag, Date recDate, String sourceStatus,
			String writeName, Date writeDate, String bwtype2, String qsbWh,
			List<ReceiveAttachments> atts, List<ReceiveAttachmentsext> attsext) {
		this.queueId = queueId;
		this.docguid = docguid;
		this.doctype = doctype;
		this.keyword = keyword;
		this.priority = priority;
		this.queueXto = queueXto;
		this.sendername = sendername;
		this.submittm = submittm;
		this.title = title;
		this.wh = wh;
		this.xcc = xcc;
		this.xto = xto;
		this.yfdw = yfdw;
		this.yfrq = yfrq;
		this.status = status;
		this.bwType = bwType;
		this.printFlag = printFlag;
		this.recDate = recDate;
		this.sourceStatus = sourceStatus;
		this.writeName = writeName;
		this.writeDate = writeDate;
		bwtype = bwtype2;
		this.qsbWh = qsbWh;
		this.atts = atts;
		this.attsext = attsext;
	}

	public RecDoc(String sourceStatus, String writeName, Date writeDate, String docguid, String doctype, String keyword, String priority, String queueXto, String sendername, Date submittm, String title, String wh, String xcc, String xto, String yfdw, String yfrq, Date recDate, List<ReceiveAttachments> atts, List<ReceiveAttachmentsext> attsext)
	{
		this.docguid = docguid;
		this.doctype = doctype;
		this.keyword = keyword;
		this.priority = priority;
		this.queueXto = queueXto;
		this.sendername = sendername;
		this.submittm = submittm;
		this.title = title;
		this.wh = wh;
		this.xcc = xcc;
		this.xto = xto;
		this.yfdw = yfdw;
		this.yfrq = yfrq;
		this.recDate = recDate;
		this.atts = atts;
		this.attsext = attsext;
		this.sourceStatus = sourceStatus;
		this.writeName = writeName;
		this.writeDate = writeDate;
	}
	
	
	public RecDoc(String docguid, String doctype, String keyword, String priority, String queueXto, String sendername, Date submittm, String title, String wh, String xcc, String xto, String yfdw, String yfrq, Date recDate, List<ReceiveAttachments> atts, List<ReceiveAttachmentsext> attsext)
	{
		this.docguid = docguid;
		this.doctype = doctype;
		this.keyword = keyword;
		this.priority = priority;
		this.queueXto = queueXto;
		this.sendername = sendername;
		this.submittm = submittm;
		this.title = title;
		this.wh = wh;
		this.xcc = xcc;
		this.xto = xto;
		this.yfdw = yfdw;
		this.yfrq = yfrq;
		this.recDate = recDate;
		this.atts = atts;
		this.attsext = attsext;
		
	}
	
	
	public RecDoc(String docguid,String status)
	{
		this.docguid = docguid;
		this.status = status;
	}

	


	public RecDoc(String docguid, String printFlag,String status) {
		this.docguid = docguid;
		this.printFlag = printFlag;
		this.status = status;
	}

	//待收公文构造
	public RecDoc(String docGuid, String queue_Id, String queue_xto, String xto, String xcc, 
			String senderName, String title, String docType, Date submittm,
			String jgdz, String fwnh, String fwxh) {
		this.docguid = docGuid;
		this.queueId = queue_Id;
		this.queueXto = queue_xto;
		this.xto = xto;
		this.xcc = xcc;
		this.sendername = senderName;
		this.title = title;
		this.doctype = docType;
		this.submittm = submittm;
		if (jgdz == null || jgdz.length() < 1) {
			this.wh = "";
		} else {
			this.wh = jgdz + "[" + fwnh + "]" + fwxh + "号";
		}

	}

	public RecDoc(String docGuid, String queue_Id, String queue_xto,
			String senderName, String title, String docType, Date submittm,
			String jgdz, String fwnh, String fwxh, String keyword, String yfdw,
			String yfrq, Long priority, String xto,String xcc) {
		if (jgdz == null || jgdz.length() < 1) {
			this.wh = "";
		} else {
			this.wh = jgdz + "[" + fwnh + "]" + fwxh + "号";
		}
		this.queueId = queue_Id;
		this.docguid = docGuid;
		this.queueXto = queue_xto;
		this.sendername = senderName;
		this.title = title;
		this.doctype = docType;
		this.submittm = submittm;
		this.keyword = keyword;
		this.yfdw = yfdw;
		this.yfrq = yfrq;
		this.xto = xto;
		this.xcc = xcc;

		if (priority == 0) {
			this.priority = "特急";
		} else if (priority == 1) {
			this.priority = "紧急";
		} else if (priority == 2) {
			this.priority = "急件";
		} else {
			this.priority = "平件";
		}
	}
	
	public RecDoc(String docguid, String doctype, String keyword, String priority, String queueXto, String sendername, Date submittm, String title, String wh, String xcc, String xto, String yfdw, String yfrq, Date recDate, List<ReceiveAttachments> atts, List<ReceiveAttachmentsext> attsext,String cebid)
	{
		this.docguid = docguid;
		this.doctype = doctype;
		this.keyword = keyword;
		this.priority = priority;
		this.queueXto = queueXto;
		this.sendername = sendername;
		this.submittm = submittm;
		this.title = title;
		this.wh = wh;
		this.xcc = xcc;
		this.xto = xto;
		this.yfdw = yfdw;
		this.yfrq = yfrq;
		this.recDate = recDate;
		this.atts = atts;
		this.attsext = attsext;
		this.cebid=cebid;
		
	}
	
	@Transient
	public String getQueueId() {
		return this.queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	@Id
	@Column(name = "DOCGUID", length = 36 ,nullable=false)
	public String getDocguid() {
		return this.docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	@Column(name = "DOCTYPE")
	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	@Column(name = "KEYWORD")
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "PRIORITY")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name = "QUEUE_XTO")
	public String getQueueXto() {
		return this.queueXto;
	}

	public void setQueueXto(String queueXto) {
		this.queueXto = queueXto;
	}

	@Column(name = "SENDERNAME")
	public String getSendername() {
		return this.sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMITTM", length = 7)
	public Date getSubmittm() {
		return this.submittm;
	}

	public void setSubmittm(Date submittm) {
		this.submittm = submittm;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "WH")
	public String getWh() {
		return this.wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	@Column(name = "XCC")
	public String getXcc() {
		return this.xcc;
	}

	public void setXcc(String xcc) {
		this.xcc = xcc;
	}

	@Column(name = "XTO")
	public String getXto() {
		return this.xto;
	}

	public void setXto(String xto) {
		this.xto = xto;
	}

	@Column(name = "YFDW")
	public String getYfdw() {
		return this.yfdw;
	}

	public void setYfdw(String yfdw) {
		this.yfdw = yfdw;
	}

	@Column(name = "YFRQ")
	public String getYfrq() {
		return this.yfrq;
	}

	public void setYfrq(String yfrq) {
		this.yfrq = yfrq;
	}

	/*@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER )
	@JoinColumn(name = "DOCGUID",nullable=true)*/ 
	@Transient
	public List<ReceiveAttachmentsext> getAttsext() {
		return attsext;
	}

	public void setAttsext(List<ReceiveAttachmentsext> attsext) {
		this.attsext = attsext;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECDATE", length = 7)
	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BW_TYPE")
	public String getBwType() {
		return bwType;
	}

	public void setBwType(String bwType) {
		this.bwType = bwType;
	}
	
	@Column(name = "PRINT_FLAG")
	public String getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}

	@Column(name = "SOURCESTATUS")
	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
    
	@Column(name = "WRITENAME")
	public String getWriteName() {
		return writeName;
	}

	public void setWriteName(String writeName) {
		this.writeName = writeName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WRITETIME", length = 7)
	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	@Column(name = "BWTYPE")
	public String getBwtype() {
		return bwtype;
	}

	public void setBwtype(String bwtype) {
		this.bwtype = bwtype;
	}
	
	@Column(name = "QSBWH")
	public String getQsbWh() {
		return qsbWh;
	}

	public void setQsbWh(String qsbWh) {
		this.qsbWh = qsbWh;
	}
	@Column(name = "YFFS")
	public String getYffs() {
		return yffs;
	}

	public void setYffs(String yffs) {
		this.yffs = yffs;
	}
	
	@Column(name = "CEBID")
	public String getCebid() {
		return cebid;
	}

	public void setCebid(String cebid) {
		this.cebid = cebid;
	}

	@Column(name = "BEIZHU")
	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	@Column(name = "XTO_NAME")
	public String getXtoName() {
		return xtoName;
	}

	public void setXtoName(String xtoName) {
		this.xtoName = xtoName;
	}
	@Column(name = "XCC_NAME")
	public String getXccName() {
		return xccName;
	}

	public void setXccName(String xccName) {
		this.xccName = xccName;
	}
	
	
}