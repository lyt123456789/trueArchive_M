package cn.com.trueway.workflow.set.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_INNERUSER")
public class InnerUser {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;//主键
	
	@Column(name = "NAME", nullable = true)
	private String name;//内置用户名称
	
	@Column(name = "INTIME", nullable = true)
	private Timestamp intime;//入库时间
	
	@Column(name = "INPERSON", nullable = true)
	private String inperson;//入库人
	
	@Column(name = "UPDATETIME", nullable = true)
	private Timestamp updatetime;//更新时间
	
	@Column(name = "UPDATEPERSON", nullable = true)
	private String updateperson;//更新人
	
	@Column(name = "ZINDEX", nullable = true)
	private Integer zindex;//排序号
	
	@Column(name = "TYPE", nullable = true)
	private Integer type;//组别 (1内置用户组 2全局用户组 3平台用户组 4流程用户组 5动态角色)
	
	@Column(name = "WORKFLOWID", nullable = true)
	private String workflowId;//流程id

	@Column(name = "grade", nullable = true)
	private Integer grade ; // 组的等级
	
	@Column(name="EMPJSONTREE")
	private String empJsonTree;
	
	@Column(name="SITEID")
	private String siteId;
	
	@Column(name="IS_SORT")
	private String isSort;
	
	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

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

	public Timestamp getIntime() {
		return intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	public String getInperson() {
		return inperson;
	}

	public void setInperson(String inperson) {
		this.inperson = inperson;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateperson() {
		return updateperson;
	}

	public void setUpdateperson(String updateperson) {
		this.updateperson = updateperson;
	}

	public Integer getZindex() {
		return zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getEmpJsonTree() {
	    return empJsonTree;
	}

	public void setEmpJsonTree(String empJsonTree) {
	    this.empJsonTree = empJsonTree;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getIsSort() {
		return isSort;
	}

	public void setIsSort(String isSort) {
		this.isSort = isSort;
	}
	
}
