package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 工作流与角色之间的关系
 * 描述：TODO 对WfMainRole进行描述
 * 作者：蔡亚军
 * 创建时间：2014-8-6 下午5:34:43
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_WFMAIN_ROLE")
public class WfMainRole {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	@Column(name = "ROLEID")
	private String roleId;	//角色id
	
	@Column(name = "WFMAINID")
	private String wfmainId;	//工作流id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getWfmainId() {
		return wfmainId;
	}

	public void setWfmainId(String wfmainId) {
		this.wfmainId = wfmainId;
	}

}
