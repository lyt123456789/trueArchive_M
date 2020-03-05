package cn.com.trueway.workflow.core.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 其他库的字段与本地库的字段匹配关系
 * 描述：TODO 对ColumnMapColumn进行描述
 * 作者：季振华
 * 创建时间：2016-1-29 下午3:27:36
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_COLUMN_MAP_COLUMN")
public class ColumnMapColumn implements java.io.Serializable {

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
	 * 数据字典Id
	 */
	@Column(name = "DATADICID",length=100)
	private String dataDicId;
	
	/**
	 * 调用表表名
	 */
	@Column(name = "TABLENAME")
	private String tableName;
	
	/**
	 * 调用表字段名
	 */
	@Column(name = "COLUMNNAME")
	private String columnName;
	
	/**
	 * 调用表字段类型
	 */
	@Column(name = "COLUMNTYPE")
	private String columnType;
	
	/**
	 * 本地表表名
	 */
	@Column(name = "TABLENAME_LOCAL")
	private String tableName_local;
	
	/**
	 * 本地表id
	 */
	@Column(name = "TABLEID_LOCAL")
	private String tableId_local;
	
	/**
	 * 本地表字段名
	 */
	@Column(name = "COLUMNNAME_LOCAL")
	private String columnName_local;
	
	/**
	 * 本地表字段中文名
	 */
	@Column(name = "NAME_LOCAL")
	private String name_local;

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
	 * 是否显示状态位0：显示1：不显示
	 */
	@Column(name = "STATUS")
	private String status;
	
	/**
	 * 排序号
	 */
	@Column(name = "PX")
	private BigDecimal px;
	
	/**
	 * 存取查询条件
	 */
	@Transient
	private String searchValue;
	
	@Transient
	private String searchValue_begin;
	
	@Transient
	private String searchValue_end;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getTableName_local() {
		return tableName_local;
	}

	public void setTableName_local(String tableName_local) {
		this.tableName_local = tableName_local;
	}

	public String getTableId_local() {
		return tableId_local;
	}

	public void setTableId_local(String tableId_local) {
		this.tableId_local = tableId_local;
	}

	public String getColumnName_local() {
		return columnName_local;
	}

	public void setColumnName_local(String columnName_local) {
		this.columnName_local = columnName_local;
	}

	public String getName_local() {
		return name_local;
	}

	public void setName_local(String name_local) {
		this.name_local = name_local;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public String getSearchValue_begin() {
		return searchValue_begin;
	}

	public void setSearchValue_begin(String searchValue_begin) {
		this.searchValue_begin = searchValue_begin;
	}

	public String getSearchValue_end() {
		return searchValue_end;
	}

	public void setSearchValue_end(String searchValue_end) {
		this.searchValue_end = searchValue_end;
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
		ColumnMapColumn other = (ColumnMapColumn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
}