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
 * 数据中心数据字典子表实体类
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
@Table(name = "T_WF_CORE_DATADICTABLE")
public class DataDicTable implements java.io.Serializable {

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
	 * 表名
	 */
	@Column(name = "TABLENAME")
	private String tableName;
	
	/**
	 * 字段名称
	 */
	@Column(name = "FILEDNAME")
	private String filedName;
	
	/**
	 * 字段类型
	 */
	@Column(name = "FILEDTYPE")
	private String filedType;
	
	/**
	 * 字段长度
	 */
	@Column(name = "FILEDLENGTH")
	private String filedLength;
	
	/**
	 * 缺省值
	 */
	@Column(name = "FILEDDEAFULT")
	private String filedDeafult;
	
	/**
	 * 描述
	 */
	@Column(name = "FILEDESC")
	private String fileDesc;
	
	/**
	 * 生成规则
	 */
	@Column(name = "FILEDRULE")
	private String filedRule;
	
	/**
	 * 外部链接
	 */
	@Column(name = "FILEDPK")
	private String fileDpk;
	
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
	
	/**
	 * 表描述
	 */
	@Column(name = "TABLEDESC")
	private String tableDesc;
	
	/**
	 * 是否主键
	 */
	@Column(name = "ISPK")
	private String isPk;
	
	@Column(name = "PX")
	private BigDecimal px;

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

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	public String getFiledLength() {
		return filedLength;
	}

	public void setFiledLength(String filedLength) {
		this.filedLength = filedLength;
	}

	public String getFiledDeafult() {
		return filedDeafult;
	}

	public void setFiledDeafult(String filedDeafult) {
		this.filedDeafult = filedDeafult;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFiledRule() {
		return filedRule;
	}

	public void setFiledRule(String filedRule) {
		this.filedRule = filedRule;
	}

	public String getFileDpk() {
		return fileDpk;
	}

	public void setFileDpk(String fileDpk) {
		this.fileDpk = fileDpk;
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

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getIsPk() {
		return isPk;
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
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
		DataDicTable other = (DataDicTable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}