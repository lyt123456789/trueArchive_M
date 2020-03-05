/**
 * 文件名称:WorkflowCalendarAction.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午07:53:55
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WorkCalendar;
import cn.com.trueway.workflow.core.pojo.WorkCalendarDetail;
import cn.com.trueway.workflow.core.service.WorkflowCalendarService;

/**
 * 描述：工作日历 作者：WangXF<br>
 * 创建时间：2011-12-30 下午07:53:55<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class WorkflowCalendarAction extends BaseAction {
	private static final long serialVersionUID = -1108771982823434566L;
	private WorkflowCalendarService workflowCalendarService;

	private WorkCalendar calendar;

	private WorkCalendarDetail calendarDetail;

	public WorkflowCalendarService getWorkflowCalendarService() {
		return workflowCalendarService;
	}

	public void setWorkflowCalendarService(
			WorkflowCalendarService workflowCalendarService) {
		this.workflowCalendarService = workflowCalendarService;
	}

	public WorkCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(WorkCalendar calendar) {
		this.calendar = calendar;
	}

	public WorkCalendarDetail getCalendarDetail() {
		return calendarDetail;
	}

	public void setCalendarDetail(WorkCalendarDetail calendarDetail) {
		this.calendarDetail = calendarDetail;
	}

	public String test() {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			DateFormat df = new SimpleDateFormat(format);
			Date date = df.parse("2011-12-12 20:00:22");
			System.out.println(df.format(workflowCalendarService
					.calculateDueTime("B3AFD7B3-1FB2-4DB0-6C3C-D15192E7F0E8",
							date, 2)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toEditCalender() {
		String id = getRequest().getParameter("id");
		calendar = workflowCalendarService.getworkCalendar(id);
		getRequest().setAttribute("calendar", calendar);
		return "toEdit";
	}

	/**
	 * 
	 * 描述：得到日历详细<br>
	 * 
	 * @throws IOException
	 *             void
	 * 
	 *             作者:张亚杰<br>
	 *             创建时间:2012-1-12 下午05:43:59
	 */
	public void getCalenderDetil2Json() throws IOException {
		String id = getRequest().getParameter("id");
		PrintWriter pw = getResponse().getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
				.createJsonGenerator(pw);
		Map<String, Map<String, Map<String, String>>> map = workflowCalendarService
				.getworkByCalendarId(id);
		jsonGenerator.writeObject(map);
		pw.println();
		pw.close();
	}

	public String editCalender() throws ParseException {
		String date = getRequest().getParameter("date");
		String createDate = getRequest().getParameter("createDate");
		String modifyDate = getRequest().getParameter("modifyDate");
		if (createDate != null)
			calendar.setCreateDate(new Timestamp(DateFormat.getDateInstance()
					.parse(createDate).getTime()));
		// 如果是修改更改修改人id和修改时间
		if (calendar.getId() != null && calendar.getId().trim().length() != 0) {
			calendar.setModifyDate(new Timestamp(new Date().getTime()));
			//calendar.setModifyId(getSession().getAttribute(Constant.USER_ID).toString());
			//TODO--userId需修改
			calendar.setModifyId("{BFA811EA-0000-0000-4555-C67D000005F7}");
		} else {
			if (workflowCalendarService.workCalendarIsSame(calendar)) {
				getRequest().setAttribute("calendar", calendar);
				getRequest().setAttribute("msg", "中文或英文名重名");
				return "toEdit";
			}
			calendar.setCreateDate(new Timestamp(new Date().getTime()));
			// TODO---userId需修改
			calendar.setCreatorId("{BFA811EA-0000-0000-4555-C67D000005F7}");
			// calendar.setWebId(getSession().getAttribute(Constant.WEB_ID).toString());
		}
		try {
			workflowCalendarService.editCalendar(calendar, date, createDate,
					modifyDate, "");
		} catch (Exception e) {
			getRequest().setAttribute("msg", "操作错误");
			return "toEdit";
		}
		calendar = workflowCalendarService.getworkCalendar(calendar.getId());
		getRequest().setAttribute("msg", "修改或新增成功");
		return "toEdit";
	}

	public String editCalenderDetil() {
		workflowCalendarService.editCalendarDetail(calendarDetail);
		calendarDetail = null;
		return "";
	}

	public String getCalenderDetil() {
		String calendarDetailId = getRequest().getParameter("id");
		workflowCalendarService.getCalendarDetail(calendarDetailId);
		return "detil";
	}

	/**
	 * 
	 * 描述：删除日历<br>
	 * 
	 * @return String
	 * 
	 *         作者:张亚杰<br>
	 *         创建时间:2012-1-4 下午09:32:42
	 */
	public String deleteCalender() {
		String id = getRequest().getParameter("id");
		workflowCalendarService.delCalendar(id);
		return getCalender();
	}

	public String getCalender() {
		// String webId = getSession().getAttribute(Constant.WEB_ID).toString();

		// 分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil
				.getParamValueByParam("pagesize"));
		int count = workflowCalendarService.getCountCalendars();
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WorkCalendar> calendars = workflowCalendarService.findCalendars(
				"", Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("calendars", calendars);
		// getRequest().setAttribute("pageSize", calendars.size());
		return "calendar";
	}

	/**
	 * 
	 * 描述：督查列表<br>
	 * 
	 * @return String
	 * 
	 *         作者:张亚杰<br>
	 *         创建时间:2012-1-6 上午09:56:12
	 */
	public String superintend() {
		String color = getRequest().getParameter("light");
		if (color != null && !color.equals("")) {

		}
		// 保存页码的参数的名称
		String pageIndexParamName = new ParamEncoder("element")
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		getRequest().setAttribute("pageIndexParamName", pageIndexParamName);
		// 获取页码，为分页查询做准备
//		String pageNum = getRequest().getParameter(pageIndexParamName);
		int pageSize = Integer.valueOf(SystemParamConfigUtil
				.getParamValueByParam("pageSize"));
//		int currentPage = GenericValidator.isBlankOrNull(pageNum) ? 1 : Integer
//				.parseInt(pageNum);

//		List<String> depid = (List<String>) getSession().getAttribute(
//				Constant.DEPARMENT_IDS);
		String title = getRequest().getParameter("title");
		String isover = getRequest().getParameter("isover");
		String importent = getRequest().getParameter("importent");
		// 是否超期
//		String past = getRequest().getParameter("past");
		// 剩余天数
//		String residue = getRequest().getParameter("residue");

//		DTPageBean dt = workflowCalendarService.getSuperintend(currentPage,
//				pageSize, depid, title, importent, isover, past, residue);
//		getRequest().setAttribute("process", dt.getDataList());
//		getRequest().setAttribute("size", dt.getTotalRows());// 总长度
//		getRequest().setAttribute("pages", dt.getTotalPages());// 页数
		getRequest().setAttribute("pageSize", pageSize);
		getRequest().setAttribute("isover", isover);
		getRequest().setAttribute("importent", importent);
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("RED_LIGHT", Constant.RED_LIGHT);
		getRequest().setAttribute("YELLOW_LIGHT", Constant.YELLOW_LIGHT);
		getRequest().setAttribute("BLUE_LIGHT", Constant.BLUE_LIGHT);

		return "superintend";
	}
}
