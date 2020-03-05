package cn.com.trueway.sys.pojo;

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
	
	@Column(name = "BELONG_TO", nullable = false, length = 38)
	private String belongTo;//所属人员id
	
	@Column(name = "CREATE_TIME")
	private Date createTime;//所属人员id

	@Column(name = "SITEID")
	private String siteId;
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
