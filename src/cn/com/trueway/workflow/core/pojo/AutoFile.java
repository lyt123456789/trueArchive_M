package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：需要自动发送到某个节点的办件信息实体
 * 作者：蒋烽
 * 创建时间：2018-2-9 上午9:25:49
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_AUTOFILE")
public class AutoFile {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String 	id;
	
	/**
	 * 办件的实例id
	 */
	@Column(name = "INSTANCEID")
	private String  instanceid;
	
	/**
	 * (0,未自动办理;1,已自动办理)
	 */
	@Column(name = "ISDO")
	private Integer isdo;
	
	/**
	 * 自动办理的时间
	 */
	@Column(name = "DOTIME")
	private Date 	dotime;
	
	/**
	 * 需要自动办理的节点id
	 */
	@Column(name = "NODEID")
	private String 	nodeid;
	
	/**
	 * 在过程表中的步骤数
	 */
	@Column(name = "STEP_INDEX")
	private Integer stepIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public Integer getIsdo() {
		return isdo;
	}

	public void setIsdo(Integer isdo) {
		this.isdo = isdo;
	}

	public Date getDotime() {
		return dotime;
	}

	public void setDotime(Date dotime) {
		this.dotime = dotime;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}
}
