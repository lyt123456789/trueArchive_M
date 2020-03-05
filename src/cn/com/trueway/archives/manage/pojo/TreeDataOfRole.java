package cn.com.trueway.archives.manage.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色--数据权限表
 * @author lixr
 *
 */
@Entity
@Table(name = "T_ARCHIVE_ROLE_TREEDATA")
public class TreeDataOfRole {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="ROLEID")
	private String roleId;//角色ID
	
	@Column(name="TREENODE")
	private String treeNode;//文档节点ID
	
	@Column(name="DATAIDCHILD")
	private String dataIdChild;//文档内容分类ID
	
	@Column(name="DATAFABRIC")
	private String dataFabric;//文档内容分类
	
	@Column(name="CONDITION")
	private String condition;//文档内容分类对应权限条件
	
	@Column(name="SQLCONDITION")
	private String sqlCondition;//权限条件分类sql
	
	@Column(name="CONDITIONSHOW")
	private String conditionShow;//权限条件中文展示

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

	public String getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode;
	}

	public String getDataIdChild() {
		return dataIdChild;
	}

	public void setDataIdChild(String dataIdChild) {
		this.dataIdChild = dataIdChild;
	}

	public String getDataFabric() {
		return dataFabric;
	}

	public void setDataFabric(String dataFabric) {
		this.dataFabric = dataFabric;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public String getConditionShow() {
		return conditionShow;
	}

	public void setConditionShow(String conditionShow) {
		this.conditionShow = conditionShow;
	}
}
