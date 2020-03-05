package cn.com.trueway.document.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 前置机来文附件关系表
 * 描述：TODO 对RecShip进行描述
 * 作者：季振华
 * 创建时间：2017-5-17 上午10:06:23
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_TOREC_SHIP")
public class RecShip {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;		//主键id
	
	@Column(name = "RECATTID")//对应附件id
	private String recAttId;
	
	@Column(name = "CREATETIME")
	private Date createTime;		//入库时间
	
	@Column(name = "PDFPATH")
	private String pdfPath;		//路径
	
	@Column(name = "RECID")
	private String recId;// 前置机办件id
	
	@Column(name = "FILENAME")
	private String fileName;//附件名
	
	/**
	 * pdf页数
	 */
	@Column(name = "PAGECOUNT")
	private Integer pageCount;
	
	/**
	 * 该文件是否被盖过章（1,是;0,否）
	 */
	@Column(name = "ISSEAL")
	private String isSeal;
	
	/**
	 * 该文件是否是正文（0,是;1,否）
	 */
	@Column(name = "ISZW")
	private String isZw;
	
	/**
	 * 对应附件的时间
	 */
	@Column(name = "FILETIME")
	private Date fileTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecAttId() {
		return recAttId;
	}

	public void setRecAttId(String recAttId) {
		this.recAttId = recAttId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getIsSeal() {
		return isSeal;
	}

	public void setIsSeal(String isSeal) {
		this.isSeal = isSeal;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIsZw() {
		return isZw;
	}

	public void setIsZw(String isZw) {
		this.isZw = isZw;
	}

	public Date getFileTime() {
		return fileTime;
	}

	public void setFileTime(Date fileTime) {
		this.fileTime = fileTime;
	}
	

}
