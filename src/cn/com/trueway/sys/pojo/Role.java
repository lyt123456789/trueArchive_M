package cn.com.trueway.sys.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 系统角色信息类<br>
 * 作者：蔡亚军<br>
 * 邮箱:c_jing1984@163.com<br>
 * 创建时间：2014-2-26 上午11:14:49<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_SYS_ROLE")
public class Role implements Serializable{

	private static final long serialVersionUID = -5092766038150589116L;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 38)
	private String roleId;// VARCHAR2(38) N 角色id

	@Column(name = "ROLE_NAME")
	private String roleName;// VARCHAR2(200) Y 角色名

	@Column(name = "ROLE_SORT")
	private BigDecimal roleSort;// NUMBER Y 排序号
	
	@Column(name = "ROLE_STATUS")
	private BigDecimal roleStatus; 	//NUMBER	Y	1		角色状态，1：启用; 0：禁用
	
	@Column(name = "SITE_ID")
	private String siteId; 	//String 站点id

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public BigDecimal getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(BigDecimal roleSort) {
		this.roleSort = roleSort;
	}

	public BigDecimal getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(BigDecimal roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
