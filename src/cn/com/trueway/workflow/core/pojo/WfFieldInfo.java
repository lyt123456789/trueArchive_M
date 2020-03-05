package cn.com.trueway.workflow.core.pojo;

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
@Table(name = "T_WF_CORE_FIELDINFO")
public class WfFieldInfo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name="VC_NAME")
	private String vc_name;
	
	@Column(name="VC_FIELDNAME")
	private String vc_fieldname;
	
	@Column(name="I_TYPE")
	private String i_type;
	
	@Column(name="I_TABLEID")
	private String i_tableid;
	
	@Column(name="I_FIELDTYPE")
	private String i_fieldtype;
	
	@Column(name="B_VALUE")
	private String b_value;
	
	@Column(name="VC_COMMENT")
	private String vc_comment;
	
	@Column(name="VC_VALUE")
	private String vc_value;
	
	@Column(name="I_LENGTH")
	private String i_length;
	
	@Column(name="I_PRECISION")
	private String i_precision;
	
	@Column(name="I_ORDERID")
	private int i_orderid;
	
	@Column(name="VC_FTABLE")
	private String vc_ftable;
	
	@Column(name="VC_FFIELD")
	private String vc_ffield;
	
	@Transient
	private String vc_ftablename;
	
	@Transient
	private String vc_ffieldname;

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

	public String getVc_fieldname() {
		return vc_fieldname;
	}

	public void setVc_fieldname(String vcFieldname) {
		vc_fieldname = vcFieldname;
	}

	public String getI_type() {
		return i_type;
	}

	public void setI_type(String iType) {
		i_type = iType;
	}

	public String getI_tableid() {
		return i_tableid;
	}

	public void setI_tableid(String iTableid) {
		i_tableid = iTableid;
	}

	public String getI_fieldtype() {
		return i_fieldtype;
	}

	public void setI_fieldtype(String iFieldtype) {
		i_fieldtype = iFieldtype;
	}

	public String getB_value() {
		return b_value;
	}

	public void setB_value(String bValue) {
		b_value = bValue;
	}

	public String getVc_comment() {
		return vc_comment;
	}

	public void setVc_comment(String vcComment) {
		vc_comment = vcComment;
	}

	public String getVc_value() {
		return vc_value;
	}

	public void setVc_value(String vcValue) {
		vc_value = vcValue;
	}

	public String getI_length() {
		return i_length;
	}

	public void setI_length(String iLength) {
		i_length = iLength;
	}

	public String getI_precision() {
		return i_precision;
	}

	public void setI_precision(String iPrecision) {
		i_precision = iPrecision;
	}

	public int getI_orderid() {
		return i_orderid;
	}

	public void setI_orderid(int iOrderid) {
		i_orderid = iOrderid;
	}

	public String getVc_ftable() {
		return vc_ftable;
	}

	public void setVc_ftable(String vcFtable) {
		vc_ftable = vcFtable;
	}

	public String getVc_ffield() {
		return vc_ffield;
	}

	public void setVc_ffield(String vcFfield) {
		vc_ffield = vcFfield;
	}

	public String getVc_ftablename() {
		return vc_ftablename;
	}

	public void setVc_ftablename(String vcFtablename) {
		vc_ftablename = vcFtablename;
	}

	public String getVc_ffieldname() {
		return vc_ffieldname;
	}

	public void setVc_ffieldname(String vcFfieldname) {
		vc_ffieldname = vcFfieldname;
	}
	
}
