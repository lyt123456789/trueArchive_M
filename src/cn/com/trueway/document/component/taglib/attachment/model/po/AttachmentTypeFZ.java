package cn.com.trueway.document.component.taglib.attachment.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：附件材料实体类
 * 作者：蔡亚军
 * 创建时间：2014-9-30 上午10:36:53
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "WF_ATTACHMENTSEXT_TYPE")
public class AttachmentTypeFZ {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;				//主键
	
	@Column(name = "ATT_TYPE")
	private String att_type;		//附件类型; 附件名称
	
	@Column(name = "ATT_SID")
	private String att_sid; 		//附件简称;
	
	@Column(name = "ATT_REQUIRED")
	private String att_required; 	//是否必填
	
	@Column(name = "ATT_LACK")
	private String att_lack; 	  //是否容缺
	
	@Column(name = "ATT_SEQ")
	private Integer att_seq;        //序号 最大最靠前。 降序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAtt_type() {
		return att_type;
	}

	public void setAtt_type(String attType) {
		att_type = attType;
	}

	public String getAtt_sid() {
		return att_sid;
	}

	public void setAtt_sid(String att_sid) {
		this.att_sid = att_sid;
	}

	public Integer getAtt_seq() {
		return att_seq;
	}

	public void setAtt_seq(Integer att_seq) {
		this.att_seq = att_seq;
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
	
}
