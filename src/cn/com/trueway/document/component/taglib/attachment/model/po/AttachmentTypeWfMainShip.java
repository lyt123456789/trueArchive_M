package cn.com.trueway.document.component.taglib.attachment.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：附件材料与流程关系绑定
 * 作者：蔡亚军
 * 创建时间：2014-10-28 下午6:28:42
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_ATTACHWFMAIN_SHIP")
public class AttachmentTypeWfMainShip {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;				//主键
	
	@Column(name = "WFUID")
	private String wfUid;				//流程
	
	@Column(name = "ATTACTHTYPEID")
	private String attacthTypeId;		//附件材料id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWfUid() {
		return wfUid;
	}

	public void setWfUid(String wfUid) {
		this.wfUid = wfUid;
	}

	public String getAttacthTypeId() {
		return attacthTypeId;
	}

	public void setAttacthTypeId(String attacthTypeId) {
		this.attacthTypeId = attacthTypeId;
	}

}
