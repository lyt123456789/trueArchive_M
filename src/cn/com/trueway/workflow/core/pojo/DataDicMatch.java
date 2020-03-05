package cn.com.trueway.workflow.core.pojo;

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
@Table(name = "T_WF_CORE_TABLE_MAP_DATADIC")
public class DataDicMatch implements java.io.Serializable {

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
	 * 数据字典id
	 */
	@Column(name = "DATADICID",length=100)
	private String dataDicId;
	
	/**
	 * 本地表id
	 */
	@Column(name = "TABLEID")
	private String tableId;
	
	/**
	 * 表对应表单名称
	 */
	@Column(name = "FORMNAME")
	private String formName;
	
	/**
	 * 本地表编码
	 */
	@Column(name = "TABLECODE")
	private String tableCode;
	
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
	 * 状态位
	 */
	@Column(name = "STATUS")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataDicId() {
		return dataDicId;
	}

	public void setDataDicId(String dataDicId) {
		this.dataDicId = dataDicId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		DataDicMatch other = (DataDicMatch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}