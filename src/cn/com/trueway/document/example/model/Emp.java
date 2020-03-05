/**
 * 文件名称:Emp.java
 * 作者:吴新星<br>
 * 创建时间:2011-11-29 下午10:29:26
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对Emp进行描述<br>
 * 作者：吴新星<br>
 * 创建时间：2011-11-29 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "example_emp")
public class Emp {

	private String id;
	private String name;
	private byte[] img;
	private String description;
	
	public Emp() {
	}
	
	public Emp(String id, String name, byte[] img, String description) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.description = description;
	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	@Column(name="name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the img
	 */
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="img")
	public byte[] getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}
	/**
	 * @return the description
	 */
	@Lob
	@Basic(fetch=FetchType.EAGER)
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
