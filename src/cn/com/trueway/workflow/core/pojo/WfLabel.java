package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 标签字段关系映射表<br>
 * 作者：zhuy<br>
 * 创建时间：2013-03-19 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_LABELFIELD")
public class WfLabel {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="VC_LABEL")
	private String vc_label;
	
	@Column(name="VC_TABLEID")
	private String vc_tableid;
	
	@Column(name="VC_TABLENAME")
	private String vc_tablename;
	
	@Column(name="VC_FIELDID")
	private String vc_fieldid;
	
	@Column(name="VC_FIELDNAME")
	private String vc_fieldname;
	
	@Column(name="VC_TEMPLATEID")
	private String vc_templateId;
	
	@Column(name="VC_COMMENTID")
	private String vc_commentId;//关联步骤id(用于意见标签套打)
	
	@Column(name="VC_TYPE")
	private String vc_type;//关联类型
	
	@Column(name="VC_ATT")
	private String vc_att;//关联附件
	
	@Column(name="VC_LIST")
	private String vc_list;//关联列表字段

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_label() {
		return vc_label;
	}

	public void setVc_label(String vcLabel) {
		vc_label = vcLabel;
	}

	public String getVc_tableid() {
		return vc_tableid;
	}

	public void setVc_tableid(String vcTableid) {
		vc_tableid = vcTableid;
	}

	public String getVc_tablename() {
		return vc_tablename;
	}

	public void setVc_tablename(String vcTablename) {
		vc_tablename = vcTablename;
	}

	public String getVc_fieldid() {
		return vc_fieldid;
	}

	public void setVc_fieldid(String vcFieldid) {
		vc_fieldid = vcFieldid;
	}

	public String getVc_fieldname() {
		return vc_fieldname;
	}

	public void setVc_fieldname(String vcFieldname) {
		vc_fieldname = vcFieldname;
	}

	public String getVc_templateId() {
		return vc_templateId;
	}

	public void setVc_templateId(String vcTemplateId) {
		vc_templateId = vcTemplateId;
	}

	public String getVc_commentId() {
		return vc_commentId;
	}

	public void setVc_commentId(String vc_commentId) {
		this.vc_commentId = vc_commentId;
	}

	public String getVc_type() {
		return vc_type;
	}

	public void setVc_type(String vc_type) {
		this.vc_type = vc_type;
	}

	public String getVc_att() {
		return vc_att;
	}

	public void setVc_att(String vc_att) {
		this.vc_att = vc_att;
	}

	public String getVc_list() {
		return vc_list;
	}

	public void setVc_list(String vc_list) {
		this.vc_list = vc_list;
	}
	
}
