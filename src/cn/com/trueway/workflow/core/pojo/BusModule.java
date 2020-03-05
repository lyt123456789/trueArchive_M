package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 数据中心业务模块实体类
 * 描述：TODO 对BusModule进行描述
 * 作者：季振华
 * 创建时间：2016-1-21 下午3:06:36
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_BUSMODULE")
public class BusModule implements java.io.Serializable {

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
	 * 模块部门
	 */
	@Column(name = "MODCODE",length=100)
	private String modCode;
	
	/**
	 * 模块名称
	 */
	@Column(name = "MODNAME")
	private String modName;
	
	/**
	 * 数据库名称
	 */
	@Column(name = "DATANAME")
	private String dataName;
	
	/**
	 * 数据库地址
	 */
	@Column(name = "DATAADDR")
	private String dataAddr;
	
	/**
	 * 数据库用户名
	 */
	@Column(name = "DATAACCOUNT",length=100)
	private String dataAccount;
	
	/**
	 * 数据库密码
	 */
	@Column(name = "DATAPASSWORD")
	private String dataPassword;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModCode() {
		return modCode;
	}

	public void setModCode(String modCode) {
		this.modCode = modCode;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataAddr() {
		return dataAddr;
	}

	public void setDataAddr(String dataAddr) {
		this.dataAddr = dataAddr;
	}

	public String getDataAccount() {
		return dataAccount;
	}

	public void setDataAccount(String dataAccount) {
		this.dataAccount = dataAccount;
	}

	public String getDataPassword() {
		return dataPassword;
	}

	public void setDataPassword(String dataPassword) {
		this.dataPassword = dataPassword;
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
		BusModule other = (BusModule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}