package cn.com.trueway.workflow.set.pojo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：中威True表单信息
 * 作者：蔡亚军
 * 创建时间：2016-2-26 上午8:42:34
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_FORM")
public class ZwkjForm implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id",length = 36)
	private String id;//主键
	
	@Column(name = "form_name", nullable = true)
	private String form_name;//表单英文名称
	
	@Column(name = "form_caption", nullable = true)
	private String form_caption;//表单中文名称
	
	@Column(name = "form_type", nullable = true)
	private String form_type;//表单类型
	
	@Column(name = "form_usage", nullable = true)
	private String form_usage;//表单用途
	
	@Column(name = "form_url", nullable = true)
	private String form_url;//表单地址
	
	@Column(name = "form_htmlfilename", nullable = true)
	private String form_htmlfilename;//表单html路径名称
	
	@Column(name = "form_jspfilename", nullable = true)
	private String form_jspfilename;//表单jsp路径名称
	
	@Column(name = "intime", nullable = true)
	private Timestamp intime;//入库时间
	
	@Column(name = "inperson", nullable = true)
	private String inperson;//入库人
	
	@Column(name = "updatetime", nullable = true)
	private Timestamp updatetime;//更新时间
	
	@Column(name = "updateperson", nullable = true)
	private String updateperson;//更新人
	
	@Column(name = "WORKFLOWID", nullable = true)
	private String workflowId;//工作流id
	
	@Column(name = "node_id", nullable = true)
	private String nodeId;
	
	@Column(name = "node_name", nullable = true)
	private String nodeName;
	
	@Column(name = "field_id", nullable = true)
	private String fieldId;
	
	@Column(name = "field_name", nullable = true)
	private String fieldName;
	
	@Column(name = "ELEMENT_LOCATION_JSON", nullable = true)
	private String elementLocationJson;

	@Column(name="INSERT_TABLE")
	private String insert_table;
	
	@Column(name="FORM_PDFFILENAME" , nullable = true)
	private String form_pdf;
	
	@Column(name = "FORMPAGE_JSON", nullable = true)
	private String formPageJson;			//表单json数据
	
/*	@Column(name="FORM_PDFFILEFLOW" , nullable = true)
	private String form_pdf_flow;*/
	
	@Column(name="BEGIN_DATE")
	private Date	beginDate;
	
	@Column(name="END_DATE")
	private Date	endDate;
	
	private Blob data;		//html文件流
	
	@Column(name = "FONT_SIZE")
	private Integer fontSize; //字号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getForm_name() {
		return form_name;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}
	public String getForm_caption() {
		return form_caption;
	}
	public void setForm_caption(String form_caption) {
		this.form_caption = form_caption;
	}
	public String getForm_type() {
		return form_type;
	}
	public void setForm_type(String form_type) {
		this.form_type = form_type;
	}
	public String getForm_usage() {
		return form_usage;
	}
	public void setForm_usage(String form_usage) {
		this.form_usage = form_usage;
	}
	public String getForm_url() {
		return form_url;
	}
	public void setForm_url(String form_url) {
		this.form_url = form_url;
	}
	public String getForm_htmlfilename() {
		return form_htmlfilename;
	}
	public void setForm_htmlfilename(String form_htmlfilename) {
		this.form_htmlfilename = form_htmlfilename;
	}
	public String getForm_jspfilename() {
		return form_jspfilename;
	}
	public void setForm_jspfilename(String form_jspfilename) {
		this.form_jspfilename = form_jspfilename;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public String getInperson() {
		return inperson;
	}
	public void setInperson(String inperson) {
		this.inperson = inperson;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateperson() {
		return updateperson;
	}
	public void setUpdateperson(String updateperson) {
		this.updateperson = updateperson;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getElementLocationJson() {
		return elementLocationJson;
	}
	public void setElementLocationJson(String elementLocationJson) {
		this.elementLocationJson = elementLocationJson;
	}
	public String getInsert_table() {
		return insert_table;
	}
	public void setInsert_table(String insert_table) {
		this.insert_table = insert_table;
	}
	public String getForm_pdf() {
		return form_pdf;
	}
	public void setForm_pdf(String form_pdf) {
		this.form_pdf = form_pdf;
	}
	public String getFormPageJson() {
		return formPageJson;
	}
	public void setFormPageJson(String formPageJson) {
		this.formPageJson = formPageJson;
	}
	/*public String getForm_pdf_flow() {
		return form_pdf_flow;
	}
	public void setForm_pdf_flow(String form_pdf_flow) {
		this.form_pdf_flow = form_pdf_flow;
	}*/
	@Column(name="DATA")
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	
	
}
