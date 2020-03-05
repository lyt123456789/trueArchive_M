package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：办件关注 关联关系实体
 * 作者：蒋烽
 * 创建时间：2017-4-10 上午8:45:37
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_FOLLOW_SHIP")
public class FollowShip {
	
	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 办件实例id
	 */
	private String instanceId;
	
	/**
	 * 人员id
	 */
	private String employeeGuid;
	
	/**
	 * 是否点开(1,是;0,否)
	 */
	private String isRead;
	
	/**
	 * 被督办的实例id
	 */
	private String oldInstanceId;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "WF_INSTANCE_UID")
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "EMPLOYEE_GUID")
	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	@Column(name = "IS_READ")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Column(name = "OLD_INSTANCEID")
	public String getOldInstanceId() {
		return oldInstanceId;
	}

	public void setOldInstanceId(String oldInstanceId) {
		this.oldInstanceId = oldInstanceId;
	}
}
