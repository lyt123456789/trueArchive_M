package cn.com.trueway.archives.manage.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 临时用户表
 * @author lixr
 *
 */
@Entity
@Table(name = "T_CASUAL_USER")
public class CasualUser {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "CASUALID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="CASUALNAME")
	private String casualName;//临时用户用户名
	
	@Column(name="CASUALPASSWORD")
	private String casualPassword;//临时用户密码
	
	@Column(name="CASUALSTARTTIME")
	private String casualStartTime;//临时用户权限生效时间
	
	@Column(name="CASUALENDTIME")
	private String casualEndTime;//临时用户权限结束时间
	
	@Column(name="DESCRIBE")
	private String describe;//临时用户描述
	
	@Column(name="CASUALINDEX")
	private String index;//序号
	
	@Column(name="MENUOFRIGHT")
	private String menuRight;//临时用户权限范围

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCasualName() {
		return casualName;
	}

	public void setCasualName(String casualName) {
		this.casualName = casualName;
	}

	public String getCasualPassword() {
		return casualPassword;
	}

	public void setCasualPassword(String casualPassword) {
		this.casualPassword = casualPassword;
	}

	public String getCasualStartTime() {
		return casualStartTime;
	}

	public void setCasualStartTime(String casualStartTime) {
		this.casualStartTime = casualStartTime;
	}

	public String getCasualEndTime() {
		return casualEndTime;
	}

	public void setCasualEndTime(String casualEndTime) {
		this.casualEndTime = casualEndTime;
	}

	public String getMenuRight() {
		return menuRight;
	}

	public void setMenuRight(String menuRight) {
		this.menuRight = menuRight;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
}
