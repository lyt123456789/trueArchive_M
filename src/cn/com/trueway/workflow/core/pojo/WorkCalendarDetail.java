/**
 * 文件名称:CalendarDetail.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午04:30:49
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 对CalendarDetail进行描述<br>
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午04:30:49<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@Entity
@Table(name = "WF_CALENDAR_DETAIL")
public class WorkCalendarDetail implements Serializable{
	
	private static final long serialVersionUID = 3820240550423077780L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 工作日历ID
	 */
	private String calendarId;
	
	/**
	 * 当前年月，格式如：2012-01
	 */
	private String yearMonth;
	
	/**
	 * 本月中的所有工作日，工作日之前按从小到大排列且用“,”分开，格式如：01，02，03，26，30
	 */
	private String workday;
	
	/**
	 * 本月中的所有非工作日，非工作日之前按从小到大排列且用“,”分开，格式如：01，02，03，26，30
	 */
	private String notWorkday;

	public WorkCalendarDetail() {
		super();
	}

	public WorkCalendarDetail(String calendarId, String yearMonth) {
		super();
		this.calendarId = calendarId;
		this.yearMonth = yearMonth;
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

	@Column(name = "CALENDAR_ID")
	public String getCalendarId() {
		return calendarId;
	}


	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	@Column(name = "YEAR_MONTH")
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name = "WORKDAY")
	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	@Column(name = "NOT_WORKDAY")
	public String getNotWorkday() {
		return notWorkday;
	}

	public void setNotWorkday(String notWorkday) {
		this.notWorkday = notWorkday;
	}
	
}
