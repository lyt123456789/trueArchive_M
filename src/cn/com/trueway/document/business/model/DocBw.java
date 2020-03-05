package cn.com.trueway.document.business.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述：办文实例<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-8 上午10:02:48<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "OA_DOC_BW")
public class DocBw implements Comparable<DocBw>{

	@Transient
	private static final long serialVersionUID = 4835972077527954074L;
	/*
	 * 办文ID、主键
	 * */
	private String bwGuid;
	
	/**
	 * 文号
	 */
	private String wh;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 来文单位
	 */
	private String sender;
	/**
	 * 文件原号
	 */
	private String wjyh;
	/**
	 * 收文日期
	 */
	private String receiveTime;
	/**
	 * 处理结果
	 */
	private Integer result;	
	/**
	 * 主办文单编号
	 */
	private String parentId;
	/**
	 * 流程实例id
	 */
	private String wfInstanceUid;

	/**
	 * 公文ID
	 */
	private String docGuid;
	
	private String formUid;
	
	private String formInstanceUid;

	/*
	 * 办文年号
	 * */
	private String bwnh;
	
	/*
	 * 办文类型
	 * */
	private String bwlx;
	
	/*
	 * 办文序号
	 * */
	private String bwxh;
	
	/*
	 * 办文附号
	 * */
	private String bwfh;
	
	/*
	 * 来文单位类型
	 * */
	private String lwdwlx;
	
	/*
	 * 备注
	 * */
	private String description;
	
	/*
	 * 份数
	 * */
	private String copies;
	
	/*
	 * 传阅人员
	 * */
	private String cyry;
	
	/*
	 * 传阅科室
	 * */
	private String cyks;
	/*
	 * 开始时间
	 * */
	private String startTime;
	
	/*
	 * 结束时间
	 * */
	private String endTime;
	
	/**
	 * lvj
	 */
	private String agentId;
	private String agentName;
	private String transUserId;
	
	
	/**
	 * 
	 * 描述：办文类型<br>
	 *
	 * @return String
	 */
	private String bwtype;
	/**
	 * 
	 * 描述：判断是否是暂存状态：用于显示办文单列表中的是否显示”编辑“<br>
	 * 0：不现实；1:显示
	 * @return int
	 */
	private int zancunType;
	
	private String webid;
	
	private String requirLimit;
	
	private Timestamp requirDate;
	
	private Integer isremind=1;
	
	private Timestamp reqfinishdate;
	
	
	/**
	 * 密级
	 */
	private String security;
	
	/**
	 * 拟稿人
	 */
	private String ngr;
	/**
	 * 拟稿科室
	 */
	private String ngrbm;
	
	@Column(name = "BWTYPE")
	public String getBwtype() {
		return bwtype;
	}

	public void setBwtype(String bwtype) {
		this.bwtype = bwtype;
	}
	
	@Column(name="wf_form_uid")
	public String getFormUid() {
		return formUid;
	}

	public void setFormUid(String formUid) {
		this.formUid = formUid;
	}
	
	@Column(name = "WF_FORM_INSTANCE_UID")
	public String getFormInstanceUid() {
		return formInstanceUid;
	}

	public void setFormInstanceUid(String formInstanceUid) {
		this.formInstanceUid = formInstanceUid;
	}

	@Transient
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Transient
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Transient
	public int getZancunType() {
		return zancunType;
	}

	public void setZancunType(int zancunType) {
		this.zancunType = zancunType;
	}
	
	/**
	 * 记录生成时间
	 */
	private Timestamp recDate;

	public DocBw() {
	}

	@Id
	@Column(name = "BWGUID")
	public String getBwGuid() {
		return bwGuid;
	}

	public void setBwGuid(String bwGuid) {
		this.bwGuid = bwGuid;
	}
	
	@Column(name = "DOCGUID")
	public String getDocGuid() {
		return docGuid;
	}

	public void setDocGuid(String docGuid) {
		this.docGuid = docGuid;
	}
	@Column(name = "WH")
	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}
	
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "SENDER")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	@Column(name = "WJYH")
	public String getWjyh() {
		return wjyh;
	}

	public void setWjyh(String wjyh) {
		this.wjyh = wjyh;
	}
	@Column(name = "RECEIVE_TIME")
	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	@Column(name = "RESULT")
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name = "WF_INSTANCE_UID")
	public String getWfInstanceUid() {
		return wfInstanceUid;
	}

	public void setWfInstanceUid(String wfInstanceUid) {
		this.wfInstanceUid = wfInstanceUid;
	}
	
	@Column(name = "RECDATE" )
	public Timestamp getRecDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(recDate==null){
			recDate = Timestamp.valueOf(sdf.format(new Date()));
		}
		return recDate;
	}

	public void setRecDate(Timestamp recDate) {
		this.recDate = recDate;
	}
	@Column(name = "BWNH")
	public String getBwnh() {
		return bwnh;
	}

	public void setBwnh(String bwnh) {
		this.bwnh = bwnh;
	}
	@Column(name = "BWLX")
	public String getBwlx() {
		return bwlx;
	}

	public void setBwlx(String bwlx) {
		this.bwlx = bwlx;
	}
	@Column(name = "BWXH")
	public String getBwxh() {
		return bwxh;
	}
	
	public void setBwxh(String bwxh) {
		this.bwxh = bwxh;
	}
	@Column(name = "BWFH")
	public String getBwfh() {
		return bwfh;
	}

	public void setBwfh(String bwfh) {
		this.bwfh = bwfh;
	}
	@Column(name = "LWDWLX")
	public String getLwdwlx() {
		return lwdwlx;
	}

	public void setLwdwlx(String lwdwlx) {
		this.lwdwlx = lwdwlx;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "COPIES")
	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	@Column(name = "CYRY")
	public String getCyry() {
		return cyry;
	}

	public void setCyry(String cyry) {
		this.cyry = cyry;
	}

	@Column(name = "CYKS")
	public String getCyks() {
		return cyks;
	}

	public void setCyks(String cyks) {
		this.cyks = cyks;
	}

	@Column(name = "WEBID")
	public String getWebid() {
		return webid;
	}

	public void setWebid(String webid) {
		this.webid = webid;
	}

	@Column(name = "REQUIRLIMIT")
	public String getRequirLimit() {
		return requirLimit;
	}

	public void setRequirLimit(String requirLimit) {
		this.requirLimit = requirLimit;
	}

	@Column(name = "REQUIRDATE")
	public Timestamp getRequirDate() {
		return requirDate;
	}

	public void setRequirDate(Timestamp requirDate) {
		this.requirDate = requirDate;
	}

	@Column(name = "ISREMIND")
	public Integer getIsremind() {
		return isremind;
	}
	
	public void setIsremind(Integer isremind) {
		this.isremind = isremind;
	}
	
	@Column(name = "REQFINISHDATE")
	public Timestamp getReqfinishdate() {
		return reqfinishdate;
	}
	
	public void setReqfinishdate(Timestamp reqfinishdate) {
		this.reqfinishdate = reqfinishdate;
	}

   @Column(name="SECURITY")
	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	@Column(name="NGR")
	public String getNgr() {
		return ngr;
	}

	public void setNgr(String ngr) {
		this.ngr = ngr;
	}
	
	
   @Column(name="NGRBM")
	public String getNgrbm() {
		return ngrbm;
	}

	public void setNgrbm(String ngrbm) {
		this.ngrbm = ngrbm;
	}

	public int compareTo(DocBw o) {
		String thisJgdz = this.bwlx == null ? "" : this.bwlx;
		String thisFwnh = this.bwnh == null ? "" : this.bwnh;
		String thisFwxh = this.bwxh == null || this.bwxh.equals("") ? "0" : this.bwxh;
		
		String oJgdz = o.getBwlx() == null ? "" : o.getBwlx();
		String oFwnh = o.getBwnh() == null ? "" : o.getBwnh();
		String oFwxh = o.getBwxh() == null || o.getBwxh().equals("") ? "0" : o.getBwxh();
		
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
	
	
	@Transient
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	
	@Transient
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	
	@Transient
	public String getTransUserId() {
		return transUserId;
	}
	public void setTransUserId(String transUserId) {
		this.transUserId = transUserId;
	}

	
	
	
	
	

}
