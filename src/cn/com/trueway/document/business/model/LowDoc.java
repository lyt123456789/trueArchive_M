package cn.com.trueway.document.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
/**
 * 下级待收实体类
 * 描述：TODO 对LowDoc进行描述
 * 作者：季振华
 * 创建时间：2016-6-15 下午5:27:32
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class LowDoc implements Comparable<LowDoc>{

	@Transient
	private static final long serialVersionUID = 4835972077527954074L;
	
	/**
	 * 唯一标识
	 */
	private String docguid;
	/**
	 * 版式文件id
	 */
	private String cebid;
	/**
	 * 公文标题
	 */
	private String title;
	/**
	 * 文号(标识)
	 */
	private String jgdz;
	/**
	 * 文号(年号)
	 */
	private String fwnh;
	/**
	 * 发文号
	 */
	private String fwxh;
	/**
	 * 公文主题词
	 */
	private String keyword;
	/**
	 * 公文类型
	 */
	private String doctype;
	/**
	 * 紧急程度
	 */
	private Long priority;
	/**
	 * 主送单位
	 */
	private String xto;
	/**
	 * 主送单位名称
	 */
	private String xtoName;
	/**
	 * 抄送单位
	 */
	private String xcc;
	/**
	 * 抄送单位名称
	 */
	private String xccName;
	/**
	 * 发送者编号
	 */
	private String senderId;
	/**
	 * 发送者名称
	 */
	private String senderName;
	/**
	 * 发送时间
	 */
	private String senderTime;
	
	/**
	 * 发文单位
	 */
	private String fwjg;
	/**
	 * 本公文所关联的正文附件
	 */
	private List<SendAttachments> atts = new ArrayList<SendAttachments>();
	/**
	 * 本公文所关联的附加附件
	 */
	private List<SendAttachments> attExts = new ArrayList<SendAttachments>();
	/**
	 * 实例id
	 */
	private String instanceId;
	/**
	 * 实例唯一id
	 */
	private String processId;
	/**
	 * 生成的pdf路径
	 */
	private String path_pdf;
	/**
	 * 生成的pdf大小
	 */
	private long pdfsize;
	
	/**
	 * 收取表中id
	 */
	private String receiveId;
	
	/**
	 * 公文交换生成的pdf路径
	 */
	private String toExchangePath;
	
	/**
	 * 文号
	 */
	private String wh;
	/**
	 * 打印份数
	 */
	private String dyfs;
	
	
	public LowDoc() {
	}

	public LowDoc(String docguid, String senderId,String senderName, String senderTime,
			String xto,String xtoName, String xcc, String xccName,
			String title, String keyword, String doctype,
			Long priority, Long sendstat, String jgdz,
			String fwnh, String fwjg,String fwxh, String cebid,
			List<SendAttachments> atts, List<SendAttachments> attExts,String instanceId,String processId,String path_pdf,long pdfsize,
			String receiveId,String toExchangePath,String wh,String dyfs
			) {
		super();
		this.docguid = docguid;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderTime = senderTime;
		this.xto = xto;
		this.xtoName = xtoName;
		this.xcc = xcc;
		this.xccName = xccName;
		this.title = title;
		this.keyword = keyword;
		this.doctype = doctype;
		this.priority = priority;
		this.jgdz = jgdz;
		this.fwnh = fwnh;
		this.fwjg = fwjg;
		this.fwxh = fwxh;
		this.cebid = cebid;
		this.atts = atts;
		this.attExts = attExts;
		this.instanceId = instanceId;
		this.processId = processId;
		this.path_pdf = path_pdf;
		this.pdfsize = pdfsize;
		this.receiveId = receiveId;
		this.toExchangePath = toExchangePath;
		this.wh = wh;
		this.dyfs = dyfs;
	}

	/**
	 * 自定义构造，提供instanceId
	 * 
	 * @param instanceId
	 */
	public LowDoc(String docId,String instanceId) {
		this.docguid = docId;
		this.instanceId = instanceId;
	}


	public String getDocguid() {
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public String getCebid() {
		return cebid;
	}

	public void setCebid(String cebid) {
		this.cebid = cebid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJgdz() {
		return jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

	public String getFwnh() {
		return fwnh;
	}

	public void setFwnh(String fwnh) {
		this.fwnh = fwnh;
	}

	public String getFwxh() {
		return fwxh;
	}

	public void setFwxh(String fwxh) {
		this.fwxh = fwxh;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getXto() {
		return xto;
	}

	public void setXto(String xto) {
		this.xto = xto;
	}

	public String getXtoName() {
		return xtoName;
	}

	public void setXtoName(String xtoName) {
		this.xtoName = xtoName;
	}

	public String getXcc() {
		return xcc;
	}

	public void setXcc(String xcc) {
		this.xcc = xcc;
	}

	public String getXccName() {
		return xccName;
	}

	public void setXccName(String xccName) {
		this.xccName = xccName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}

	public String getFwjg() {
		return fwjg;
	}

	public void setFwjg(String fwjg) {
		this.fwjg = fwjg;
	}

	public List<SendAttachments> getAtts() {
		return atts;
	}

	public void setAtts(List<SendAttachments> atts) {
		this.atts = atts;
	}

	public List<SendAttachments> getAttExts() {
		return attExts;
	}

	public void setAttExts(List<SendAttachments> attExts) {
		this.attExts = attExts;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public String getPath_pdf() {
		return path_pdf;
	}

	public void setPath_pdf(String path_pdf) {
		this.path_pdf = path_pdf;
	}

	public long getPdfsize() {
		return pdfsize;
	}

	public void setPdfsize(long pdfsize) {
		this.pdfsize = pdfsize;
	}
	
	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getToExchangePath() {
		return toExchangePath;
	}

	public void setToExchangePath(String toExchangePath) {
		this.toExchangePath = toExchangePath;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	public String getDyfs() {
		return dyfs;
	}

	public void setDyfs(String dyfs) {
		this.dyfs = dyfs;
	}

	public int compareTo(LowDoc o){
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


}
