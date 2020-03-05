package cn.com.trueway.workflow.set.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COMMON_GROUP")
public class CommonGroup {
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	private String id;//id
	
	@Column(name = "NAME", nullable = false, length = 200)
	private String name;//常用人员组名
	
	@Column(name = "BELONG_TO", length = 38)
	private String belongTo;//所属人员id
	
	@Column(name = "CREATE_TIME")
	private Date createTime;//所属人员id
	
	@Column(name = "ORDERINDEX")
	private Integer orderIndex;//排序号
	
	@Column(name = "ISJYGROUP")
	private String isJyGroup;

	@Column(name = "DEPT_FLAG")
	private String deptFlag;
	
	@Column(name = "SITEID")
	private String siteId;	//站点id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getIsJyGroup() {
		return isJyGroup;
	}

	public void setIsJyGroup(String isJyGroup) {
		this.isJyGroup = isJyGroup;
	}

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Override
	public String toString() {
		return "CommonGroup [id=" + id + ", name=" + name + ", belongTo="
				+ belongTo + "]";
	}

	
}
