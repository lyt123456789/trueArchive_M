package cn.com.trueway.sys.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 系统菜单信息类<br>
 * 作者: 蔡亚军<br>
 * 邮箱:c_jing1984@163.com<br>
 * 创建时间：2014-2-26 上午11:12:14<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_SYS_MENU")
public class Menu {

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "MENU_ID", unique = true, nullable = false, length = 38)
	private String menuId;			// VARCHAR2(38) N 菜单id

	@Column(name = "MENU_FATER_ID")
	private String menuFaterId;		// VARCHAR2(38) Y 上级菜单id

	@Column(name = "MENU_NAME")
	private String menuName;		// VARCHAR2(200) Y 菜单名

	@Column(name = "MENU_URL")
	private String menuUrl;// VARCHAR2(200) Y 菜单地址

	@Column(name = "MENU_TYPE")
	private BigDecimal menuType;// NUMBER Y 菜单类型 0-相对 1-绝对

	@Column(name = "MENU_SORT")
	private BigDecimal menuSort;// NUMBER Y 排序号

	@Column(name = "MENU_STATUS")
	private BigDecimal menuStatus; // NUMBER Y 启用或者停用 0-停用 1-启用

	@Column(name = "MENU_EXTLINKS")
	private BigDecimal menuExtLinks; // NUMBER Y 是否为外部链接
	
	@Column(name = "MENU_FOREIGN_AD")
	private String foreignAppAddress;//外部应用地址

	@Column(name = "MENU_SERIAL")
	private String menuSerial; // NUMBER Y 是否为外部链接

	@Column(name = "MENU_RANK")
	private BigDecimal menuRank; // NUMBER Y 菜单级别 0-菜单，1-按钮

	@Column(name = "MENU_SIMPLE_NAME")
	private String menuSimpleName;// VARCHAR2(200) 菜单简称

	@Transient
	private Boolean havaChild;// 是否有子节点

	@Column(name = "MENU_PATH")
	private String menuPath;// 菜单图片
	
	@Column(name = "COUNTSQL")
	private String countSql;//角标数目sql

	@Column(name = "SITE_ID")
	private String siteId;//站点id
	
	@Transient
	private String countStr;//角标数目

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuFaterId() {
		return menuFaterId;
	}

	public void setMenuFaterId(String menuFaterId) {
		this.menuFaterId = menuFaterId;
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

	public BigDecimal getMenuType() {
		return menuType;
	}

	public void setMenuType(BigDecimal menuType) {
		this.menuType = menuType;
	}

	public BigDecimal getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(BigDecimal menuSort) {
		this.menuSort = menuSort;
	}

	public BigDecimal getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(BigDecimal menuStatus) {
		this.menuStatus = menuStatus;
	}

	public BigDecimal getMenuExtLinks() {
		return menuExtLinks;
	}

	public void setMenuExtLinks(BigDecimal menuExtLinks) {
		this.menuExtLinks = menuExtLinks;
	}

	public String getMenuSerial() {
		return menuSerial;
	}

	public void setMenuSerial(String menuSerial) {
		this.menuSerial = menuSerial;
	}

	public BigDecimal getMenuRank() {
		return menuRank;
	}

	public void setMenuRank(BigDecimal menuRank) {
		this.menuRank = menuRank;
	}

	public String getMenuSimpleName() {
		return menuSimpleName;
	}

	public void setMenuSimpleName(String menuSimpleName) {
		this.menuSimpleName = menuSimpleName;
	}

	public Boolean getHavaChild() {
		return havaChild;
	}

	public void setHavaChild(Boolean havaChild) {
		this.havaChild = havaChild;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getForeignAppAddress() {
		return foreignAppAddress;
	}

	public void setForeignAppAddress(String foreignAppAddress) {
		this.foreignAppAddress = foreignAppAddress;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getCountStr() {
		return countStr;
	}

	public void setCountStr(String countStr) {
		this.countStr = countStr;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
}
