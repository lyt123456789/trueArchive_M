package cn.com.trueway.archives.manage.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统角色bean
 * @author lixr
 *
 */
@Entity
@Table(name = "T_TRUEARCHIVE_ROLE")
public class TrueArchiveRole {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ROLEID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="NAME")
	private String name;//角色名称
	
	@Column(name="ROLEDESCIBE")
	private String roleDescibe;//角色描述
	
	@Column(name="ROLEINDEX")
	private String roleIndex;//角色序号
	
	@Column(name="CREATETIME")
	private String createTime;//创建时间
	
	@Column(name="CHANGETIME")
	private String changeTime;//修改时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleDescibe() {
		return roleDescibe;
	}

	public void setRoleDescibe(String roleDescibe) {
		this.roleDescibe = roleDescibe;
	}

	public String getRoleIndex() {
		return roleIndex;
	}

	public void setRoleIndex(String roleIndex) {
		this.roleIndex = roleIndex;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	
}
