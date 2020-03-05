package cn.com.trueway.extra.meeting.action;

import java.io.IOException;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.extra.meeting.bean.MeetingApply;
import cn.com.trueway.extra.meeting.bean.MeetingApplyOut;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.service.MeetingInfoService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.service.DepartmentService;

public class MeetingInfoAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private TableInfoService tableInfoService;
	
	private DepartmentService departmentService;
	private MeetingInfoService meetingInfoService;
	
	public MeetingInfoService getMeetingInfoService() {
		return meetingInfoService;
	}

	public void setMeetingInfoService(MeetingInfoService meetingInfoService) {
		this.meetingInfoService = meetingInfoService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}



	/**
	 * 办件列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDeptMeetingList(){
		String title = getRequest().getParameter("wfTitle");
		String isSuperior = getRequest().getParameter("isSuperior");
		String itemName = getRequest().getParameter("itemName");
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		
		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'", "\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
		String conditionSql = "";//查询条件
		if(CommonUtil.stringNotNULL(title) ){
			conditionSql = " and t.DOFILE_TITLE like '%"+title+"%' ";
		}
		if(CommonUtil.stringNotNULL(itemName)){
			conditionSql += " and i.vc_sxmc = '"+itemName+"' ";
		}
		if(CommonUtil.stringNotNULL(searchDateFrom)){
			conditionSql += " and to_date('"+searchDateFrom+"' ,'YYYY-MM-dd HH24:mi') <= to_date( m.meeting_Begin_Time ,'YYYY-MM-dd HH24:mi')";
		}
		if(CommonUtil.stringNotNULL(searchDateTo)){
			conditionSql += " and to_date('"+searchDateTo+"' ,'YYYY-MM-dd HH24:mi') >= to_date( m.meeting_End_Time ,'YYYY-MM-dd HH24:mi')";
		}
		
		//城管局定制
		String itemIds = getRequest().getParameter("itemIds");
		
		if(CommonUtil.stringNotNULL(itemIds) ){
			itemIds = "'"+itemIds.replaceAll(",", "','")+"'";
			conditionSql += " and t.ITEM_ID in ("+itemIds+") ";
//			conditionSql += " and m.departmentid='"+employee.getDepartmentGuid()+"' ";
		}
		
		//新增王静查看所有的会议申请，其他人查看自己的会议申请
		String userid=employee.getEmployeeGuid();
		String nbhysq=SystemParamConfigUtil.getParamValueByParam("nbhysq");
		if(CommonUtil.stringNotNULL(nbhysq)){
			if(!nbhysq.contains(userid)){
				conditionSql+=" and m.userid= '"+userid+"'";
			}
		}
		
		String bigDepId=getSession().getAttribute(MyConstants.DEPARMENT_IDS)==null?null:((List)getSession().getAttribute(MyConstants.DEPARMENT_IDS)).get(0).toString();
		
		
		Department department = departmentService.findDepartmentById(employee.getDepartmentGuid());
		String[] bgsdepids = SystemParamConfigUtil.getParamValueByParam("bgs_depid").split(",");//办公室
		String[] jzsdepids = SystemParamConfigUtil.getParamValueByParam("jzs_depid").split(",");//局长室
		if (department!=null&&CommonUtil.stringNotNULL(department.getSuperiorGuid())&&department.getSuperiorGuid().equals("1")) {
			//超管查看所有事项
			bigDepId = null;
		}else{
			for (String bgsstr : bgsdepids) {
				if(employee.getDepartmentGuid().equals(bgsstr)){
					bigDepId = null;
					break;
				}else{
					for (String jzsstr : jzsdepids) {
						if(employee.getDepartmentGuid().equals(jzsstr)){
							bigDepId = null;
							break;
						}
					}
				}
			}
		}
		if (department!=null&&CommonUtil.stringNotNULL(department.getSuperiorGuid())&&department.getSuperiorGuid().equals("1")) {
			//超管查看所有事项
			bigDepId = null;
		}
		if (!"yes".equals(isSuperior)&&bigDepId != null) {
			//超管查看所有事项
//			bigDepId = null;
//			conditionSql += " and m.departmentid='"+bigDepId+"' ";
		}
		String empId = employee.getEmployeeGuid();
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = meetingInfoService.getCountDeptMeetings(bigDepId,conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<MeetingApply> doFileList = meetingInfoService.getDeptMeetingList(bigDepId,conditionSql,Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("doFileList", doFileList);
		getRequest().setAttribute("wfTitle", title);
		getRequest().setAttribute("itemName", itemName);
		getRequest().setAttribute("isSuperior", isSuperior);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("itemIds", getRequest().getParameter("itemIds"));
		return "deptMeetingList";
	}
	
	public void updateMeetingState() throws IOException{
		try {
			String instanceId = getRequest().getParameter("instanceId");
			String state = getRequest().getParameter("state");
			meetingInfoService.updateState(instanceId,state);
	        
			getResponse().getWriter().print("yes");
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().getWriter().print("yes");
		}
	}
	/**
	 * 综合统计
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getMeetingList(){
		String title = getRequest().getParameter("title");
		String arr = getRequest().getParameter("arr");
		String begtime = getRequest().getParameter("begtime");
		String endtime = getRequest().getParameter("endtime");
		String person = getRequest().getParameter("person");
		String zcr = getRequest().getParameter("zcr");
		String conditionSql = "";//查询条件
		if(CommonUtil.stringNotNULL(title) ){
			conditionSql += " and m.meeting_subject like '%"+title+"%' ";
		}
		if(CommonUtil.stringNotNULL(arr) ){
			conditionSql += " and m.roomname like '%"+arr+"%' ";
		}
		if(CommonUtil.stringNotNULL(begtime) ){
			conditionSql += " and m.meeting_begin_time >= '"+begtime+"'";
		}
		if(CommonUtil.stringNotNULL(endtime) ){
			conditionSql += " and m.meeting_end_time <= '"+endtime+"'";
		}
		if(CommonUtil.stringNotNULL(person) ){
			conditionSql += " and (m.meeting_bcyry like '%"+person+"%' or m.meeting_wcyry like '%"+person+"%')";
		}
		if(CommonUtil.stringNotNULL(zcr) ){
			conditionSql += " and m.meeting_zcr like '%"+zcr+"%' ";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = meetingInfoService.getCountMeetings(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<MeetingphoApply> meetingList = meetingInfoService.getMeetingList(conditionSql,Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("meetingList", meetingList);
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("arr", arr);
		getRequest().setAttribute("begtime", begtime);
		getRequest().setAttribute("endtime", endtime);
		getRequest().setAttribute("person", person);
		getRequest().setAttribute("zcr", zcr);
		return "meetingList";
	}
	/**
	 * 参会名单汇总
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getMeetingOutList(){
		Employee employee=(Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String depIds=getSession().getAttribute(MyConstants.DEPARMENT_IDS)==null?null:((List)getSession().getAttribute(MyConstants.DEPARMENT_IDS)).get(0).toString();
		Department department = departmentService.findDepartmentById(employee.getDepartmentGuid());
		String[] bgsdepids = SystemParamConfigUtil.getParamValueByParam("bgs_depid").split(",");//办公室
		String[] jzsdepids = SystemParamConfigUtil.getParamValueByParam("jzs_depid").split(",");//局长室
		if (department!=null&&CommonUtil.stringNotNULL(department.getSuperiorGuid())&&department.getSuperiorGuid().equals("1")) {
			//超管查看所有事项
			depIds = null;
		}else{
			for (String bgsstr : bgsdepids) {
				if(employee.getDepartmentGuid().equals(bgsstr)){
					depIds = null;
					break;
				}else{
					for (String jzsstr : jzsdepids) {
						if(employee.getDepartmentGuid().equals(jzsstr)){
							depIds = null;
							break;
						}
					}
				}
			}
		}
		String conditionSql = "";//查询条件
		String name = getRequest().getParameter("name");
		String begtime = getRequest().getParameter("begtime");
		if(CommonUtil.stringNotNULL(name) ){
			conditionSql += " and m.meetingname like '%"+name+"%' ";
		}
		if(CommonUtil.stringNotNULL(begtime) ){
			conditionSql += " and m.newtime >= '"+begtime+"'";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = meetingInfoService.getCountMeetingOuts(depIds, conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<MeetingApplyOut> meetingOutList = meetingInfoService.getMeetingOutList(depIds, conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("meetingOutList", meetingOutList);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("begtime", begtime);
		return "meetingOutList";
	}
	@SuppressWarnings("unchecked")
	public String getMeetingOutName(){
		String instanceId = getRequest().getParameter("instanceId");
		String meetingname = getRequest().getParameter("meetingname");
		List<Employee> list = meetingInfoService.getPersonName(instanceId);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("meetingname", meetingname);
		return "meetingOutName";
	}
}
