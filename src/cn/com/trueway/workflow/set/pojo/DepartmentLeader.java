package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：部门的领导人
 * 作者：蔡亚军
 * 创建时间：2015-1-13 下午3:17:56
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_DEPARTMENT_LEADER")
public class DepartmentLeader {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	@Column(name = "DEPID")
	private String depId;		//机构id
	
	@Column(name = "DEPNAME")
	private String depName;		//机构名称
	
	@Column(name = "LEADERID")
	private String leaderId;		//领导id
	
	@Column(name = "LEADERNAME")
	private String leaderName;		//领导名称
	
	@Column(name = "EMPTYPE")
	private String empType;		//人员类型
	
	@Column(name = "SORTID")
	private Integer sortId;		//排序号
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}
}
