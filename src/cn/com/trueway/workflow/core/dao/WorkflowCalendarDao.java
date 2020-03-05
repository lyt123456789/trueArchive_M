/**
 * 文件名称:WorkflowCalendarDao.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午04:27:51
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.workflow.core.pojo.WorkCalendar;
import cn.com.trueway.workflow.core.pojo.WorkCalendarDetail;

/**
 * 描述：工作日历相关库操作
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午04:27:51<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface WorkflowCalendarDao {
	
	/**
	 * 
	 * 描述：增加工作日历
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:22
	 */
	public void addCalendar(WorkCalendar calendar);
	
	/**
	 * 
	 * 描述：根据ID删除工作日历
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:28
	 */
	public void deleteCalendar(String calendarId);
	
	/**
	 * 
	 * 描述：更新工作日历
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:31
	 */
	public void updateCalendar(WorkCalendar calendar);

	/**
	 * 
	 * 描述：根据ID查找工作日历
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:34
	 */
	public WorkCalendar findCalendar(String calendarId);
	
	/**
	 * 
	 * 描述：增加CalendarDetail
	 *
	 * @param calendarDetail void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:38
	 */
	public void addCalendarDetail(WorkCalendarDetail calendarDetail);
	
	/**
	 * 
	 * 描述：根据calendarDetailId删除CalendarDetail
	 *
	 * @param calendarDetailId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:41
	 */
	public void deleteCalendarDetail(String calendarDetailId);
	
	/**
	 * 
	 * 描述：根据calendarId删除CalendarDetail
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午05:01:24
	 */
	public void deleteAllCalendarDetail(String calendarId);
	
	
	/**
	 * 
	 * 描述：更新CalendarDetail
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:45
	 */
	public void updateCalendarDetail(WorkCalendarDetail calendar);

	/**
	 * 
	 * 描述：查找CalendarDetail
	 *
	 * @param calendarDetailId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:49
	 */
	public WorkCalendarDetail findCalendarDetail(String calendarDetailId);

	/**
	 * 
	 * 描述：根据calendarDetailId和yearMonth查找CalendarDetail
	 *
	 * @param calendarDetailId
	 * @param yearMonth
	 * @return CalendarDetail
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午06:02:01
	 */
	public WorkCalendarDetail findCalendarDetail(String calendarDetailId,String yearMonth);
	
	
	
	/**
	 * 
	 * 描述：查找所有CalendarDetail
	 *
	 * @param calendarId
	 * @return List<CalendarDetail>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:52
	 */
	public List<WorkCalendarDetail> findAllCalendarDetail(String calendarId, String yearMonth);
	
	/**
	 * 
	 * 描述：找到本部门的日历<br>
	 *
	 * @param webId
	 * @return List<Calendar>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午10:11:02
	 */
	public List<WorkCalendar> findCalendars(String webId,Integer pageindex, Integer pagesize);
	/**
	 * 
	 * 描述：得到该工作日历<br>
	 *
	 * @param id
	 * @return WorkCalendar
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 下午05:18:36
	 */
	public WorkCalendar getWorkCalendar(String id);
	
	public int workCalendarIsSame(WorkCalendar calendar);

	public List<String> getCalendarYear(String calendarId);
	public DTPageBean getSuperintend(int pageIndex,int pageSize,List<String> depid,String title,Integer importent, String isover);
	public int getCountCalendars();

	public List<WorkCalendar> getCalendars(String webId);
}
