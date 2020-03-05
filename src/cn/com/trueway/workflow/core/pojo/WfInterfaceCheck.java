package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接口检测实体类
 * @author jszw
 *
 */
@Entity
@Table(name="T_WF_INTERFACECHECK")
public class WfInterfaceCheck implements Serializable{

	private static final long serialVersionUID = 7741528261299862304L;
	@Id
	@Column(name = "ID")
	private String id;//主键id
	
	@Column(name = "INTERFACENAME")
	private String interfaceName;//接口名称
	
	@Column(name = "URL")
	private String url;//接口url
	
	@Column(name = "ICONPATH")
	private String iconPath;//图标路径

	@Column(name = "SORTINDEX")
	private Integer sortIndex;//排序号

	@Column(name = "RESULT")
	private String result;//检测结果（0：未检测，1：通过，2：未通过）

	@Column(name = "ISABSURL")
	private String isAbsUrl;//是否绝对url（0：否，1：是）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIsAbsUrl() {
		return isAbsUrl;
	}

	public void setIsAbsUrl(String isAbsUrl) {
		this.isAbsUrl = isAbsUrl;
	}
}
