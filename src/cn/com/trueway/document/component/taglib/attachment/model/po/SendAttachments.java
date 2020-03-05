/** 
 * 文件名称：Attachments.java<br>
 * 作者：吴新星<br>
 * 创建时间：Sep 4, 2009 5:09:15 PM<br>
 * CopyRright (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.document.component.taglib.attachment.model.po;

import java.sql.Blob;
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
 * 描述：公文正文附件<br>
 * 作者：吴新星<br>
 * 创建时间：Sep 4, 2009 5:09:15 PM <br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "OA_DOC_ATTACHMENTS")
public class SendAttachments  implements java.io.Serializable{

	// Fields
	@Transient
	private static final long serialVersionUID = -187176942157478465L;
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 公文id
	 */
	private String docguid;
	/**
	 * 文件名称
	 */
	private String filename;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 文件大小
	 */
	private Long filesize;
	/**
	 * 文件路径
	 */
	private String localation;
	/**
	 * 文件排序
	 */
	private Long fileindex;
	/**
	 * 上传时间
	 */
	private Date filetime;
	
	/**
	 * 编辑人
	 */
	private String editer;

	/**
	 * 上传附件标题
	 */
	private String title;
	/**
	 * 上传附件类别
	 */
	private String type;
	
	/**
	 * 转换后的pdf路径
	 */
	private String topdfpath;
	
	/**
	 * pdf附件来源; 1:表示来源于子流程返回值
	 */
	private Integer filefrom;
	
	private String nodeId;
	
	/**
	 * 文件上传顺序
	 */
	private Integer	uploadIndex;
	
	/**
	 * 操作日志id(用途1、区分异库同步操作或是本库操作 2、确认是否同步成功，异库回写判断)
	 */
	private String operateLogId;
	
	/**
	 * pdf页数
	 */
	private Integer pagecount;
	
	/**
	 * 该文件是否被盖过章（1,是;0,否）
	 */
	private String isSeal;
	
	private String tmPdfPath;//脱密pdf路径
	
	private Blob	data;//文件流
	
	private Blob	pdfData;//pdf文件流
	
	private String fileSize;
	
	private String temId;//正文模板id
	
	/** default constructor */
	public SendAttachments() {
	}

	/** minimal constructor */
	public SendAttachments(String docguid, String filename, String filetype,
			Long filesize, String localation, Date filetime) {
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.filetime = filetime;
	}

	/** full constructor */
	public SendAttachments(String id, String docguid, String filename,
			String filetype, Long filesize, String localation, Long fileindex,
			Date filetime, String editer, String title, String type) {
		super();
		this.id = id;
		this.docguid = docguid;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.localation = localation;
		this.fileindex = fileindex;
		this.filetime = filetime;
		this.editer = editer;
		this.title = title;
		this.type = type;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DOCGUID", nullable = false)
	public String getDocguid() {
		return this.docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	@Column(name = "FILENAME", nullable = false)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "FILETYPE", nullable = false, length = 20)
	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "FILESIZE", nullable = false, precision = 22, scale = 0)
	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	@Column(name = "LOCALATION", nullable = false, length = 500)
	public String getLocalation() {
		return this.localation;
	}

	public void setLocalation(String localation) {
		this.localation = localation;
	}

	@Column(name = "FILEINDEX", precision = 22, scale = 0)
	public Long getFileindex() {
		return this.fileindex;
	}

	public void setFileindex(Long fileindex) {
		this.fileindex = fileindex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FILETIME", nullable = false, length = 7)
	public Date getFiletime() {
		return this.filetime;
	}

	public void setFiletime(Date filetime) {
		this.filetime = filetime;
	}

	@Column(name = "EDITER", length = 50)
	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}
	
	@Column(name = "ATTTITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "ATTTYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TOPDFPATH")
	public String getTopdfpath() {
		return topdfpath;
	}

	public void setTopdfpath(String topdfpath) {
		this.topdfpath = topdfpath;
	}

	@Column(name = "FILEFROM")
	public Integer getFilefrom() {
		return filefrom;
	}

	public void setFilefrom(Integer filefrom) {
		this.filefrom = filefrom;
	}

	@Column(name = "nodeId")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "UPLOADINDEX")
	public Integer getUploadIndex() {
		return uploadIndex;
	}

	public void setUploadIndex(Integer uploadIndex) {
		this.uploadIndex = uploadIndex;
	}

	@Column(name = "OPERATE_LOG_ID")
	public String getOperateLogId() {
		return operateLogId;
	}

	public void setOperateLogId(String operateLogId) {
		this.operateLogId = operateLogId;
	}

	@Column(name = "PAGECOUNT")
	public Integer getPagecount() {
		return pagecount;
	}

	public void setPagecount(Integer pagecount) {
		this.pagecount = pagecount;
	}

	@Column(name = "IS_SEAL")
	public String getIsSeal() {
		return isSeal;
	}

	public void setIsSeal(String isSeal) {
		this.isSeal = isSeal;
	}

	@Column(name = "TMPDFPATH")
	public String getTmPdfPath() {
		return tmPdfPath;
	}

	public void setTmPdfPath(String tmPdfPath) {
		this.tmPdfPath = tmPdfPath;
	}

	@Column(name = "DATA")
	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	@Column(name = "PDFDATA")
	public Blob getPdfData() {
		return pdfData;
	}

	public void setPdfData(Blob pdfData) {
		this.pdfData = pdfData;
	}

	@Transient
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	@Column(name = "TEMID", length = 38)
	public String getTemId() {
		return temId;
	}

	public void setTemId(String temId) {
		this.temId = temId;
	}
}
