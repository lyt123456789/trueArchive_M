package cn.com.trueway.workflow.set.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.com.trueway.workflow.core.pojo.Employee;

@Entity
@Table(name = "T_WF_CORE_USER_GROUP")
public class UserGroup {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	@Column(name = "NAME", nullable = true)
	private String name;
	
	@Column(name = "USERID", nullable = true)
	private String userid;
	
	@Column(name = "RELATION_USERIDS", nullable = true)
	private String relation_userids;
	
	@Column(name = "INTIME", nullable = true)
	private Timestamp intime;
	
	@Column(name = "UPDATETIME", nullable = true)
	private Timestamp updatetime;
	
	@Column(name = "ZINDEX", nullable = true)
	private Integer zindex;
	
	@Column(name = "grade", nullable = true)
	private Integer grade ; // 组的等级
	
	@Transient
	private List<Employee> employeeList;
	
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
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getRelation_userids() {
		return relation_userids;
	}
	
	public void setRelation_userids(String relation_userids) {
		this.relation_userids = relation_userids;
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	public Timestamp getIntime() {
		return intime;
	}
	
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	
	public Integer getZindex() {
		return zindex;
	}
	
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	
	public Integer getGrade() {
		return grade;
	}
	
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
