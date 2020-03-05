/**
 * 文件名称:Calendar.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午04:30:00
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：工作日历
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午04:30:00<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "WF_CALENDAR")
public class WorkCalendar implements Serializable{
	
	private static final long serialVersionUID = 3170994884082064192L;
	
	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 中文名称
	 */
	private String chName;
	
	/**
	 * 英文名称
	 */
	private String enName;
	
	/**
	 * 工作日历描述
	 */
	private String describe;
	
	/**
	 * 创建人ID
	 */
	private String creatorId;
	
	/**
	 * 创建日期
	 */
	private Timestamp createDate;
	
	/**
	 * 修改人ID
	 */
	private String modifyId;

	/**
	 * 修改日期
	 */
	private Timestamp modifyDate;
	
	private String creatorName;
	
	private String modifyName;
	
	public WorkCalendar() {
		super();
	}

	public WorkCalendar(String chName, String enName, String describe,
			String creatorId, Timestamp createDate) {
		super();
		this.chName = chName;
		this.enName = enName;
		this.describe = describe;
		this.creatorId = creatorId;
		this.createDate = createDate;
	}

	public WorkCalendar(String chName, String enName, String creatorId,
			Timestamp createDate) {
		super();
		this.chName = chName;
		this.enName = enName;
		this.creatorId = creatorId;
		this.createDate = createDate;
	}

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

	@Column(name = "CH_NAME")
	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	@Column(name = "EN_NAME")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "DESCRIBE")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "CREATOR_ID")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATE_DATE")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFY_ID")
	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	@Column(name = "MODIFY_DATE")
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Transient
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	@Transient
	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	
}
