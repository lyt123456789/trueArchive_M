package cn.com.trueway.workflow.version.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：工作流版本管理及介绍实体类
 * 作者：Zhaoj☭
 * 创建时间：2014-7-28 下午03:31:18
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "T_WF_CORE_VERSION")
public class VersionManager implements Serializable{
	
	private static final long serialVersionUID = -8684715443311101535L;
	
	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	public String id;
	
	@Column(name = "NUM", length = 36)
	public String num;
	
	@Column(name = "CONTENT", length = 36)
	public String content;

	@Column(name = "UPDATEDATE", length = 36)
	public Date updateDate;

	@Column(name = "INSTANCEID", length = 36)
	public String instanceId;

	@Column(name = "APPLYPROJECT", length = 36)
	public String applyProject;

	public VersionManager() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getApplyProject() {
		return applyProject;
	}
	public void setApplyProject(String applyProject) {
		this.applyProject = applyProject;
	}
}
