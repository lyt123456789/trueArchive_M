package cn.com.trueway.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：角色与菜单的授权关系
 * 作者：蔡亚军
 * 创建时间：2016-3-15 下午01:48:48
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_SYS_ROLE_MENU")
public class MenuRoleShip {
	
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "GUID", unique = true, nullable = false, length = 38)
	private String guid;// VARCHAR2(38) N 表主键

	@Column(name = "ROLE_ID")
	private String roleId;// VARCHAR2(38) Y 角色id

	@Column(name = "MENU_ID")
	private String menuId;// VARCHAR2(38) Y 单菜id

	@Column(name = "ROLE_NAME")
	private String roleName;// VARCHAR2(200) Y 角色名

	@Column(name = "MEUN_NAME")
	private String menuName;// VARCHAR2(200) Y 菜单名
	
	/** 使用权限 */
	@Column(name = "WEB_RIGHT")
	private Integer webRight;
	/** 控制权限 */
	@Column(name = "MOBILE_RIGHT")
	private Integer mobileRight;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getWebRight() {
		return webRight;
	}

	public void setWebRight(Integer webRight) {
		this.webRight = webRight;
	}

	public Integer getMobileRight() {
		return mobileRight;
	}

	public void setMobileRight(Integer mobileRight) {
		this.mobileRight = mobileRight;
	}

}
