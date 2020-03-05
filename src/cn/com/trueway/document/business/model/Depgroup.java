package cn.com.trueway.document.business.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：常用单位分组<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-28 下午03:12:18<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "DEPGROUP")
public class Depgroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7986659488505268464L;

	private String id;
	private String name;
	private String dep;
	private String superior_guid;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "dep")
	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}
	@Column(name = "superior_guid")
	public String getSuperior_guid() {
		return superior_guid;
	}

	public void setSuperior_guid(String superior_guid) {
		this.superior_guid = superior_guid;
	}
	
}
