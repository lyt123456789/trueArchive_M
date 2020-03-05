package cn.com.trueway.document.component.taglib.attachment.model.po;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：被切割的文件<br>
 * 作者：蔡亚军<br>
 * 创建时间：Sep 4, 2009 5:09:15 PM <br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "OA_DOC_CutPages")
public class CutPages {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	private String id;					//主键ID
	
	@Column(name = "DOCID")
	private String docId;				//属于的文件ID
	
	@Column(name = "FILEPATH")
	private String filepath;			//文件处于的位置
	
	@Column(name = "PAGECOUNT")
	private Integer pageCount;			//文件页数
	
	@Column(name = "FILESIZE")
	private Long  fileSize;				//文件的大小
		
	@Column(name = "SORT")
	private Integer sort;				//牌序号
	
	@Column(name = "STARTPAGE")
	private Integer startPage;				//开始页
	
	@Column(name = "ENDPAGE")
	private Integer endPage;				//结束页
	
	@Column(name = "PDFDATA")
	private Blob	pdfData;				//文件流

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getEndPage() {
		return endPage;
	}

	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}

	public Blob getPdfData() {
		return pdfData;
	}

	public void setPdfData(Blob pdfData) {
		this.pdfData = pdfData;
	}
	
}
