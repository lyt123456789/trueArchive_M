package cn.com.trueway.document.component.taglib.attachment.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：材料与事项节点绑定实体类
 * 作者：蔡亚军
 * 创建时间：2014-9-30 下午2:53:56
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_ATTACHMENT_BIND")
public class AttachmentTypeBind {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;				//主键
	
	@Column(name = "NODEID")
	private String nodeId;				//节点id
	
	@Column(name = "ATTACHMENTTYPEID")
	private String attachmentTypeId;	//材料id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getAttachmentTypeId() {
		return attachmentTypeId;
	}

	public void setAttachmentTypeId(String attachmentTypeId) {
		this.attachmentTypeId = attachmentTypeId;
	}
}
