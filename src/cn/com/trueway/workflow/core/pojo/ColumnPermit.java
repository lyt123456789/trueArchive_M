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
 * 控制使用表的字段的权限属性
 * 描述：TODO 对ColumnPermit进行描述
 * 作者：季振华
 * 创建时间：2016-1-29 下午3:28:18
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_COLUMNPERMIT")
public class ColumnPermit implements java.io.Serializable {

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
	 * 匹配id
	 */
	@Column(name = "MATCHID",length=100)
	private String matchId;
	
	/**
	 * 字段名
	 */
	@Column(name = "COLNAME")
	private String colName;
	
	/**
	 * 字段中文名
	 */
	@Column(name = "COLNAME_C")
	private String colName_C;
	
	/**
	 * 控制是否展示（0：显示1：不显示）
	 */
	@Column(name = "IS_SHOW")
	private String is_Show;
	
	/**
	 * 排序字段
	 */
	@Column(name = "SORT")
	private BigDecimal sort;
	
	/**
	 * 控制是否作为检索条件（0：不是1：是）
	 */
	@Column(name = "IS_SEARCH")
	private String is_Search;
	
	/**
	 * 控制是否设为回写字段（0：不是1：是）
	 */
	@Column(name = "IS_BACK")
	private String is_Back;
	
	/**
	 * 过滤条件
	 */
	@Column(name = "FILTER")
	private String filter;
	
	/**
	 * 调用表字段类型
	 */
	@Column(name = "COLUMNTYPE")
	private String columnType;
	
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
	
	@Transient
	private String filter_begin;
	
	@Transient
	private String filter_end;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColName_C() {
		return colName_C;
	}

	public void setColName_C(String colName_C) {
		this.colName_C = colName_C;
	}

	public String getIs_Show() {
		return is_Show;
	}

	public void setIs_Show(String is_Show) {
		this.is_Show = is_Show;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getIs_Search() {
		return is_Search;
	}

	public void setIs_Search(String is_Search) {
		this.is_Search = is_Search;
	}
	
	public String getIs_Back() {
		return is_Back;
	}

	public void setIs_Back(String is_Back) {
		this.is_Back = is_Back;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
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
	
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public String getFilter_begin() {
		return filter_begin;
	}

	public void setFilter_begin(String filter_begin) {
		this.filter_begin = filter_begin;
	}

	public String getFilter_end() {
		return filter_end;
	}

	public void setFilter_end(String filter_end) {
		this.filter_end = filter_end;
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
		ColumnPermit other = (ColumnPermit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}