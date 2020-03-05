package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述： 对流程自定义状态表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-04-01 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_CUSTOM_STATUS")
public class WfCustomStatus {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/*
	 * 键
	 */
	@Column(name="VC_KEY")
	private String vc_key;
	
	/*
	 * 值
	 */
	@Column(name="VC_VALUE")
	private String vc_value;
	
	/*
	 * 流程id
	 */
	@Column(name="LCID")
	private String lcid;
	
	/*
	 * 排序
	 */
	@Column(name="N_ORDER")
	private String order;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_key() {
		return vc_key;
	}

	public void setVc_key(String vcKey) {
		vc_key = vcKey;
	}

	public String getVc_value() {
		return vc_value;
	}

	public void setVc_value(String vcValue) {
		vc_value = vcValue;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	
}
