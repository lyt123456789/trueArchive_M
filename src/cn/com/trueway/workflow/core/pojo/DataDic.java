package cn.com.trueway.workflow.core.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 数据中心数据字典实体类
 * 描述：TODO 对DataDic进行描述
 * 作者：季振华
 * 创建时间：2016-1-22 下午12:00:38
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_DATADIC")
public class DataDic implements java.io.Serializable {

	private static final long serialVersionUID = 7457134915716417836L;
	
	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", length = 36)
	private String id;
	/**
	 * 模块Id
	 */
	@Column(name = "MODID",length=100)
	private String modId;
	
	/**
	 * 数字字典代码
	 */
	@Column(name = "DICCODE")
	private String dicCode;
	
	/**
	 * 数字字典名称
	 */
	@Column(name = "DICNAME")
	private String dicName;
	
	/**
	 * 类别
	 */
	@Column(name = "CATEGORY")
	private String category;

	/**
	 * 创建者
	 */
	@Column(name = "CREATEUSER")
	private String createUser;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATEDATE")
	private Date createDate;
	
	/**
	 * 修改者
	 */
	@Column(name = "MODIFYUSER")
	private String modifyUser;
	
	/**
	 * 修改时间
	 */
	@Column(name = "MODIFYTIME")
	private Date modifyTime;
	
	/**
	 * 该数据库状态 
	 */
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "PX")
	private BigDecimal px;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPx() {
		return px;
	}

	public void setPx(BigDecimal px) {
		this.px = px;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataDic other = (DataDic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}