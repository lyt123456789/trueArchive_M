package cn.com.trueway.document.component.taglib.attachment.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：上传的附件类型
 * 作者：蔡亚军
 * 创建时间：2014-10-29 上午9:42:10
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "WF_ATTACHFILE_TYPE_TYPE")
public class AttachFileType {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name = "ATT_TYPE")
	private String att_type;		//分类
	
	@Column(name = "ATT_SSLB")
	private String att_sslb;		//文件所属类别  zjclatt(证据材料);  wsclatt(文书材料);

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

	public String getAtt_sslb() {
		return att_sslb;
	}

	public void setAtt_sslb(String att_sslb) {
		this.att_sslb = att_sslb;
	}
	
}
