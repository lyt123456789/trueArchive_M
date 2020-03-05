package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 数据库字段权限设置<br>
 * 作者：zhuy<br>
 * 创建时间：2013-03-19 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "WF_ATTACHMENTSEXT_TYPE")
public class AttachmentType {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name = "ATT_TYPE")
	private String att_type;

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
	
}
