package cn.com.trueway.archives.using.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 借阅单、借阅库字段配置
 * @author hw
 *
 */
@Entity
@Table(name = "T_USING_ARCHIVENODE")
public class ArchiveNode {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name="VC_NAME")
	private String vc_name;//字段名称
	
	@Column(name="VC_CREATETIME")
	private String vc_createTime;//字段创建时间
	
	@Column(name="VC_TYPE")
	private String vc_type;//类型
	
	@Column(name="VC_FIELTYPE")
	private String vc_fielType;//数据库字段类型
	
	@Column(name="VC_LENGTH")
	private String vc_length;//字段长度
	
	@Column(name="VC_FIELNAME")
	private String vc_fielName;//数据库字段
	
	@Column(name="VC_DATAFORMAT")
	private String vc_dataFormat;//数据格式
	
	@Column(name="VC_ISEDIT")
	private String vc_isEdit;//是否可编辑
	
	@Column(name="VC_ISNULL")
	private String vc_isNull;//是否必填
	
	@Column(name="VC_ISSHOW")
	private String vc_isShow;//是否展示
	
	@Column(name="VC_NUMBER")
	private String vc_number;//排序
	
	@Column(name="VC_ISDEL")
	private String vc_isDel;//是否删除
	
	@Column(name="VC_TABLE")
	private String vc_table;//应用表
	
	@Column(name="VC_SYSTEM")
	private String vc_system;//系统配置字段
	
	@Column(name="VC_SYSNAME")
	private String vc_sysName;

	@Column(name="VC_HEIGHT")
	private String vc_height;//默认向下合并行数
	
	@Column(name="VC_WIDTH")
	private String vc_width;//默认向右合并td
	
	@Column(name="VC_INPUT")
	private String vc_input;//默认展示形式
	
	@Column(name="VC_METADATAID")
	private String vc_metadataid;//绑定元数据的id
	
	@Column(name="VC_METADATANAME")
	private String vc_metadataname;//绑定元数据的name
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_name() {
		return vc_name;
	}

	public void setVc_name(String vc_name) {
		this.vc_name = vc_name;
	}

	public String getVc_createTime() {
		return vc_createTime;
	}

	public void setVc_createTime(String vc_createTime) {
		this.vc_createTime = vc_createTime;
	}

	public String getVc_type() {
		return vc_type;
	}

	public void setVc_type(String vc_type) {
		this.vc_type = vc_type;
	}

	public String getVc_fielType() {
		return vc_fielType;
	}

	public void setVc_fielType(String vc_fielType) {
		this.vc_fielType = vc_fielType;
	}

	public String getVc_length() {
		return vc_length;
	}

	public void setVc_length(String vc_length) {
		this.vc_length = vc_length;
	}

	public String getVc_fielName() {
		return vc_fielName;
	}

	public void setVc_fielName(String vc_fielName) {
		this.vc_fielName = vc_fielName;
	}

	public String getVc_isEdit() {
		return vc_isEdit;
	}

	public void setVc_isEdit(String vc_isEdit) {
		this.vc_isEdit = vc_isEdit;
	}

	public String getVc_isNull() {
		return vc_isNull;
	}

	public void setVc_isNull(String vc_isNull) {
		this.vc_isNull = vc_isNull;
	}

	public String getVc_isShow() {
		return vc_isShow;
	}

	public void setVc_isShow(String vc_isShow) {
		this.vc_isShow = vc_isShow;
	}

	public String getVc_number() {
		return vc_number;
	}

	public void setVc_number(String vc_number) {
		this.vc_number = vc_number;
	}

	public String getVc_isDel() {
		return vc_isDel;
	}

	public void setVc_isDel(String vc_isDel) {
		this.vc_isDel = vc_isDel;
	}

	public String getVc_table() {
		return vc_table;
	}

	public void setVc_table(String vc_table) {
		this.vc_table = vc_table;
	}

	public String getVc_system() {
		return vc_system;
	}

	public void setVc_system(String vc_system) {
		this.vc_system = vc_system;
	}

	public String getVc_sysName() {
		return vc_sysName;
	}

	public void setVc_sysName(String vc_sysName) {
		this.vc_sysName = vc_sysName;
	}

	public String getVc_height() {
		return vc_height;
	}

	public void setVc_height(String vc_height) {
		this.vc_height = vc_height;
	}

	public String getVc_width() {
		return vc_width;
	}

	public void setVc_width(String vc_width) {
		this.vc_width = vc_width;
	}

	public String getVc_input() {
		return vc_input;
	}

	public void setVc_input(String vc_input) {
		this.vc_input = vc_input;
	}

	public String getVc_metadataid() {
		return vc_metadataid;
	}

	public void setVc_metadataid(String vc_metadataid) {
		this.vc_metadataid = vc_metadataid;
	}

	public String getVc_metadataname() {
		return vc_metadataname;
	}

	public void setVc_metadataname(String vc_metadataname) {
		this.vc_metadataname = vc_metadataname;
	}

	public String getVc_dataFormat() {
		return vc_dataFormat;
	}

	public void setVc_dataFormat(String vc_dataFormat) {
		this.vc_dataFormat = vc_dataFormat;
	}
	
}
