package cn.com.trueway.document.component.taglib.attachment.model.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：表单中已提交的附件 实体类
 * 作者：蔡亚军
 * 创建时间：2014-10-8 上午9:14:47
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "WF_ATTACHMENTSEXT_TYPE_SUB")
public class AttachmentTypeSub {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;					//唯一标识
	
	@Column(name = "TYPEID")
	private String typeId;				//材料类型:对应的AttachmentType的主键id
	
	@Column(name = "ITEMID")
	private String itemId;				//绑定事项id
	
	@Column(name = "INSTANCEID")
	private String instanceId;			//实例id
	
	@Column(name = "ISSUB")
	private String isSub;				//是否提交  0：未提交  1：已提交
	
	@Column(name = "ISBQBZ")
	private String isbqbz;       		//是否需要补齐补正
	
	@Column(name = "ATT_COUNT")
	private String att_count;				//页码数量
	
	@Transient
	private String typeName;			//材料名称
	
	@Transient
	private String att_sid;				//附件标签id
	
	@Transient
	private String att_required;		//是否必填
	
	@Transient
	private String att_lack;		//是否容缺
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIsSub() {
		return isSub;
	}

	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}

	public String getIsbqbz() {
		return isbqbz;
	}

	public void setIsbqbz(String isbqbz) {
		this.isbqbz = isbqbz;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAtt_sid() {
		return att_sid;
	}

	public void setAtt_sid(String att_sid) {
		this.att_sid = att_sid;
	}

	public String getAtt_required() {
		return att_required;
	}

	public void setAtt_required(String att_required) {
		this.att_required = att_required;
	}

	public String getAtt_lack() {
		return att_lack;
	}

	public void setAtt_lack(String att_lack) {
		this.att_lack = att_lack;
	}

	public String getAtt_count() {
		return att_count;
	}

	public void setAtt_count(String att_count) {
		this.att_count = att_count;
	}
	
}
