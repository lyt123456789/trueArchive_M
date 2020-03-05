package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * ClassName: PushEntity <br/>
 * date: 2016年4月19日 上午10:05:41 <br/>
 *
 * @author adolph_jiang
 * @version 
 * @since JDK 1.6
 */
@Entity
@Table(name = "T_PUSH")
public class PushEntity {

	/**
	 * 主鍵id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;
	
	/**
	 * 計數
	 */
	@Column(name = "BADGE_WF")
	private String badgeWf;
	
	/**
	 * 計數
	 */
	@Column(name = "BADGE_FUNC")
	private String badgeFunc;
	
	/**
	 * 机器标识
	 */
	@Column(name = "TOKEN")
	private String token;
	
	/**
	 * 登錄名
	 */
	@Column(name = "LOGIN_NAME")
	private String loginName;

	/**
	 * 用户id
	 */
	@Column(name = "USER_ID")
	private String userId;
	
	/**
	 * 手機系統標識(1:苹果系统,2:安卓系统,0:其他系统)
	 */
	@Column(name = "ISAPPLE")
	private String isApple;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBadgeWf() {
		return badgeWf;
	}

	public void setBadgeWf(String badgeWf) {
		this.badgeWf = badgeWf;
	}

	public String getBadgeFunc() {
		return badgeFunc;
	}

	public void setBadgeFunc(String badgeFunc) {
		this.badgeFunc = badgeFunc;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsApple() {
		return isApple;
	}

	public void setIsApple(String isApple) {
		this.isApple = isApple;
	}
}
