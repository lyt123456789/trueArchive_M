package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：收回办件日志表
 * 作者：ln
 * 创建时间：2018-8-13 下午1:44:02
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_RECALL_LOG")
public class WfRecallLog {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;			//主键id
	
	@Column(name="recall_time")
	private Date recallTime;	//收回时间
	
	@Column(name="userid")
	private String userId;	//收回用户
	
	@Column(name="instanceid")
	private String instanceId;		//办件instanceId
	
	@Column(name="processid")
	private String processId;		//过程id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRecallTime() {
		return recallTime;
	}

	public void setRecallTime(Date recallTime) {
		this.recallTime = recallTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
