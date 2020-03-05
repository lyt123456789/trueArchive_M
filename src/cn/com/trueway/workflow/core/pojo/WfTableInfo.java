package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对工作流表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-03-19 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_TABLE")
public class WfTableInfo  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="VC_NAME")
	private String vc_name;
	
	@Column(name="VC_TABLENAME")
	private String vc_tablename;
	
	@Column(name="VC_PARENT")
	private String vc_parent;
	
	@Column(name="VC_COMMENT")
	private String vc_comment;
	
	@Column(name="VC_CREATDATE")
	private Timestamp vc_creatdate;
	
	@Column(name="VC_MODIFYDATE")
	private Timestamp vc_modifydate;
	
	@Column(name="VC_USER")
	private String vc_user;
	
	@Column(name="LCID")
	private String lcid;
	
	@Column(name="REFLC")
	private String reflc;
	
	@Transient
	private List<WfFieldInfo> fields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_name() {
		return vc_name;
	}

	public void setVc_name(String vcName) {
		vc_name = vcName;
	}

	public String getVc_tablename() {
		return vc_tablename;
	}

	public void setVc_tablename(String vcTablename) {
		vc_tablename = vcTablename;
	}

	public String getVc_parent() {
		return vc_parent;
	}

	public void setVc_parent(String vcParent) {
		vc_parent = vcParent;
	}

	public String getVc_comment() {
		return vc_comment;
	}

	public void setVc_comment(String vcComment) {
		vc_comment = vcComment;
	}

	public Timestamp getVc_creatdate() {
		return vc_creatdate;
	}

	public void setVc_creatdate(Timestamp vcCreatdate) {
		vc_creatdate = vcCreatdate;
	}

	public Timestamp getVc_modifydate() {
		return vc_modifydate;
	}

	public void setVc_modifydate(Timestamp vcModifydate) {
		vc_modifydate = vcModifydate;
	}

	public String getVc_user() {
		return vc_user;
	}

	public void setVc_user(String vcUser) {
		vc_user = vcUser;
	}

	public List<WfFieldInfo> getFields() {
		return fields;
	}

	public void setFields(List<WfFieldInfo> fields) {
		this.fields = fields;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getReflc() {
		return reflc;
	}

	public void setReflc(String reflc) {
		this.reflc = reflc;
	}
	
}
