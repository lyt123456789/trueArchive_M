/**
 * 文件名称:WorkFlowCalendarService.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午05:16:30
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.workflow.core.pojo.WorkCalendar;
import cn.com.trueway.workflow.core.pojo.WorkCalendarDetail;

/**
 * 描述：工作日历业务功能
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午05:16:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface WorkflowCalendarService {
	
	/**
	 * 
	 *  描述：根据所传工作日历ID、时间和期限计算到期时间
	 *
	 * @param calendarId
	 * @param date
	 * @param timeLimit
	 * @return Date
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午05:55:08
	 * @throws Exception 
	 */
	public Date calculateDueTime(String calendarId,Date date,int timeLimit) throws Exception;
	
	/**
	 * 
	 * 描述：操作工作日厉详细<br>
	 *
	 * @param calendarDetail void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午09:05:24
	 */
	public void editCalendarDetail(WorkCalendarDetail calendarDetail);
	
	/**
	 * 
	 * 描述：操作工作日历<br>
	 *
	 * @param calendar void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午09:05:28
	 */
	public void editCalendar(WorkCalendar calendar,String date,String createDate, String modifyDate,String webid) throws Exception;
	
	public Map<String,Map<String,Map<String,String>>> getworkByCalendarId(String id);
	
	/**
	 * 
	 * 描述：根据ID得到工作日历详细<br>
	 *
	 * @param calendarDetailId void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午09:22:39
	 */
	public WorkCalendarDetail getCalendarDetail(String calendarDetailId);
	
	/**
	 * 
	 * 描述：根据webId找到本部门的日历<br>
	 *
	 * @param webId
	 * @return List<cn.com.trueway.workflow.core.model.po.Calendar>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午10:13:59
	 */
	public List<WorkCalendar> findCalendars(String webId,Integer pageindex, Integer pagesize);
	
	/**
	 * 
	 * 描述：得到该日历的详细<br>(分页)
	 *
	 * @param calendarId
	 * @return List<CalendarDetail>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 上午10:26:35
	 */
	public List<WorkCalendarDetail> getCalendar(String calendarId, String yearMonth);
	
	/**
	 * 描述：得到该日历的详细<br>(非分页)
	 */
	public List<WorkCalendar> getCalendars(String webId);
	
	/**
	 * 
	 * 描述：得到工作日历<br>
	 *
	 * @param id
	 * @return Calendar
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 下午05:14:57
	 */
	public WorkCalendar getworkCalendar(String id);
	
	/**
	 * 
	 * 描述：校验名称是否重复<br>
	 *
	 * @param calendar
	 * @return boolean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 下午07:55:17
	 */
	public boolean workCalendarIsSame(WorkCalendar calendar);
	
	/**
	 * 
	 * 描述：取得该日历的年份<br>
	 *
	 * @param calendarId
	 * @return List<String>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-4 下午08:20:46
	 */
	public List<String> getCalendarYear(String calendarId);

	public void delCalendar(String id);
	
	public DTPageBean getProcess(int currentPage,int numPerPage);
	
//	public DTPageBean getSuperintend(int pageIndex,int pageSize,List<String> depid,String title,String importent, String isover,String past, String residue);
	
	public int getCountCalendars();
}
