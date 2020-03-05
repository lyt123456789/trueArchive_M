package cn.com.trueway.workflow.set.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "ZWKJ_EMPLOYEEROLE")
public class EmployeeRole {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	/**
	 * 角色名称
	 */
	@Column(name = "ROLENAME", nullable = false)
	private String roleName;
	
	/**
	 * 人员id
	 */
	@Column(name = "USERIDS")
	private String	userIds;
	
	/**
	 * 排序号
	 */
	@Column(name = "ZINDEX")
	private Integer zindex;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getZindex() {
		return zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

}
