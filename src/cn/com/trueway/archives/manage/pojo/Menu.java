package cn.com.trueway.archives.manage.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统菜单
 * @author lixr
 *
 */
@Entity
@Table(name = "T_TRUEARCHIVE_MENU")
public class Menu {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name="MENUNAME")
	private String menuName;
	
	@Column(name="MENUURL")
	private String menuUrl;
	
	@Column(name="MENUINDEX")
	private String menuIndex;
	
	@Column(name="DESCRIBE")
	private String describe;
	
	@Column(name="CREATETIME")
	private String createTime;
	
	@Column(name="MENUPIC")
	private String menuPic;
	
	@Column(name="MENUACTPIC")
	private String menuActPic;
	
	@Transient
	private String isCheck;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getMenuPic() {
		return menuPic;
	}

	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}

	public String getMenuActPic() {
		return menuActPic;
	}

	public void setMenuActPic(String menuActPic) {
		this.menuActPic = menuActPic;
	}

	public String getMenuIndex() {
		return menuIndex;
	}

	public void setMenuIndex(String menuIndex) {
		this.menuIndex = menuIndex;
	}
}
