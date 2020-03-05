/**
 * 文件名称:WorkFlowCalendarServiceImpl.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午05:28:57
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.Step;
import cn.com.trueway.document.business.dao.SelectTreeDao;
import cn.com.trueway.workflow.core.dao.WorkflowCalendarDao;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WorkCalendar;
import cn.com.trueway.workflow.core.pojo.WorkCalendarDetail;
import cn.com.trueway.workflow.core.service.WorkflowCalendarService;

/**
 * 描述：工作日历业务功能实现
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午05:28:57<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class WorkflowCalendarServiceImpl implements WorkflowCalendarService {
	
	private WorkflowCalendarDao workflowCalendarDao ;
	
	private SelectTreeDao selectTreeDao;
	
	public WorkflowCalendarDao getWorkflowCalendarDao() {
		return workflowCalendarDao;
	}

	public void setWorkflowCalendarDao(WorkflowCalendarDao workflowCalendarDao) {
		this.workflowCalendarDao = workflowCalendarDao;
	}

	public SelectTreeDao getSelectTreeDao() {
		return selectTreeDao;
	}

	public void setSelectTreeDao(SelectTreeDao selectTreeDao) {
		this.selectTreeDao = selectTreeDao;
	}
	

	/**
	 * 
	 * 描述：根据所传工作日历ID、时间和期限计算到期时间
	 *
	 * @param calendarId
	 * @param date
	 * @param timeLimit
	 * @return Date
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午05:55:08
	 * @throws ParseException 
	 */
	public Date calculateDueTime(String calendarId,Date date,int timeLimit) throws Exception{
		if(date==null){
			throw new Exception("参数：date不能为null");
		}
		if(timeLimit<=0){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal =this.checkAndGetWorkday(cal, calendarId, true);
		List<Date> dateList = new ArrayList<Date>();
		String yearMonth = this.getYearMonth(cal);
    	List<String> workdayList = this.getWorkdayOfOneMonth(calendarId, yearMonth);
    	List<String> newList = new ArrayList<String>();
    	boolean flag = false;
    	for (int i = 0; i < workdayList.size(); i++) {
    		if(cal.get(Calendar.DATE)==Integer.valueOf(workdayList.get(i))){
				flag = true;
				i++;
			}
    		if(flag&&i!=workdayList.size()){
    			newList.add(workdayList.get(i));
    		}
		}
    	for (int i = 0; i < newList.size(); i++) {
    		String dateStr = yearMonth+"-"+newList.get(i);
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    		dateList.add(sf.parse(dateStr));
		}
    	while(dateList.size()<timeLimit){
    		cal.add(Calendar.MONTH,1);
    		yearMonth = this.getYearMonth(cal);
        	List<String> workdayList2 = this.getWorkdayOfOneMonth(calendarId, yearMonth);
        	for (int i = 0; i < workdayList2.size(); i++) {
        		String dateStr = yearMonth+"-"+workdayList2.get(i);
        		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        		dateList.add(sf.parse(dateStr));
			}
    	}
    	Calendar dueCal = Calendar.getInstance();
    	dueCal.setTime(dateList.get(timeLimit-1));
    	dueCal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
    	dueCal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
    	dueCal.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return dueCal.getTime();
	}
	
	
	/**
	 * 
	 * 描述：判断当前日期是否是工作日，如果不是根据类型找到下一个或前一个工作日
	 *
	 * @param date
	 * @param isNext
	 * @return Date
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-31 下午06:31:48
	 * @throws Exception 
	 */
	private Calendar checkAndGetWorkday(Calendar cal,String calendarId, boolean isNext) throws Exception{
		//先检查此日期是否是工作日，如果是工作日直接返回此日期
		//如果此日期不是工作日根据查找类型isNext判断是向后找还是向前找
		//向后找：找到此日期下一个工作日并将时间设置成上班时间，返回找到的工作日
		//向前找：找到此日期上一个工作日并将时间设置成下班时间，返回找到的工作日
		String yearMonth = this.getYearMonth(cal);
		List<String>  workdayList=this.getWorkdayOfOneMonth(calendarId, yearMonth);
		String dayStr = (cal.get(Calendar.DATE)>9?cal.get(Calendar.DATE)+"":"0"+cal.get(Calendar.DATE));
		if(workdayList.contains(dayStr)){
			return cal;
		}
		if(isNext){
			//向后找
			boolean isfind= false;
			for(int i=0;i<workdayList.size();i++){
				if(cal.get(Calendar.DATE)<Integer.valueOf(workdayList.get(i))){
					cal.set(Calendar.DATE,Integer.valueOf(workdayList.get(i)));
					isfind=true;
					break;
				}
			}
			while(isfind==false){
				cal.add(Calendar.MONTH, 1);
				yearMonth = this.getYearMonth(cal);
				List<String> list=this.getWorkdayOfOneMonth(calendarId, yearMonth);
				if(!list.isEmpty()){
					cal.set(Calendar.DATE,Integer.valueOf(list.get(0)));
					isfind=true;
				}
			}
			cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(Constant.START_WORK_TIME.split(":")[0]));
			cal.set(Calendar.MINUTE,Integer.valueOf(Constant.START_WORK_TIME.split(":")[1]));
			cal.set(Calendar.SECOND,0);
			return cal;
		}else{
			//向前找
			boolean isfind= false;
			for(int i=workdayList.size()-1;i>=0;i--){
				if(cal.get(Calendar.DATE)>Integer.valueOf(workdayList.get(i))){
					cal.set(Calendar.DATE,Integer.valueOf(workdayList.get(i)));
					isfind=true;
					break;
				}
			}
			while(isfind==false){
				cal.add(Calendar.MONTH, -1);
				yearMonth = this.getYearMonth(cal);
				List<String> list=this.getWorkdayOfOneMonth(calendarId, yearMonth);
				if(!list.isEmpty()){
					cal.set(Calendar.DATE, Integer.valueOf(list.get(list.size()-1)));
					isfind=true;
				}
			}
			cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(Constant.END_WORK_TIME.split(":")[0]));
			cal.set(Calendar.MINUTE,Integer.valueOf(Constant.END_WORK_TIME.split(":")[1]));
			cal.set(Calendar.SECOND,0);
			return cal;
		}
	}
	
	public void editCalendarDetail(WorkCalendarDetail calendarDetail){
		if(calendarDetail == null) return;
		if(calendarDetail.getCalendarId() == null)
		workflowCalendarDao.addCalendarDetail(calendarDetail);
		else
		workflowCalendarDao.updateCalendarDetail(calendarDetail);
	}
	
	
	@SuppressWarnings("unchecked")
	public void editCalendar(WorkCalendar calendar,String date,String createDate, String modifyDate,String webid) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Map<String, Object>> maps = objectMapper.readValue(date, Map.class);
//		System.out.println(maps.size());
		Set<String> key = maps.keySet();
		Iterator<String> iter = key.iterator();
		List<WorkCalendarDetail> list = new ArrayList<WorkCalendarDetail>();
		while (iter.hasNext()) {
			// 年份
			String year = iter.next();
			Set<String> key1 = maps.get(year).keySet();
			Iterator<String> iter1 = key1.iterator();
			while (iter1.hasNext()) {
				WorkCalendarDetail wcd = new WorkCalendarDetail();
				// 月份
				String month = iter1.next();
				wcd.setYearMonth(year + "-" + upNum(month));
				StringBuffer notWork = new StringBuffer();
				StringBuffer work = new StringBuffer();
				//天数
				String days = maps.get(year).get(month).toString().replaceAll("\\{", "").replaceAll("\\}", "");
				if(days.contains(",")){
					for (String str : days.split(",")) {
						if(str.contains("=")){
							if (Constant.NOT_WORK_DAY.equals(str.split("=")[1])) {
								notWork.append(upNum(str.split("=")[0].trim()) + ",");
							} else if (Constant.WORK_DAY.equals(str.split("=")[1])) {
								work.append(upNum(str.split("=")[0].trim()) + ",");
							}
						}
					}
				}else if(days.contains("=")){
					if (Constant.NOT_WORK_DAY.equals(days.split("=")[1])) {
						notWork.append(upNum(days.split("=")[0].trim()) + ",");
					} else if (Constant.WORK_DAY.equals(days.split("=")[1])) {
						work.append(upNum(days.split("=")[0].trim()) + ",");
					}
				}
				if(notWork.toString().endsWith(",") && notWork.length()>1){
					String notwork_str = notWork.toString();
					//System.out.println(notwork_str.substring(0, notWork.length()-1));
					wcd.setNotWorkday(notwork_str.substring(0, notWork.length()-1));
					//System.out.println(wcd.getNotWorkday());
				}
				if(work.toString().endsWith(",") && work.length()>1){
					String work_str = work.toString();
					//System.out.println(work_str.substring(0, work.length()-1));
					wcd.setWorkday(work_str.substring(0, work.length()-1));
					//System.out.println(wcd.getWorkday());
				}
				list.add(wcd);
			}
		}

		if (calendar == null){
			return;
		}
//		calendar.setWebId(webid);
		//更新或插入工作日历
		if (calendar.getId() == null || calendar.getId().length()==0) {
			workflowCalendarDao.addCalendar(calendar);
		}else{
			workflowCalendarDao.updateCalendar(calendar);
		}
		
		if (calendar.getId() == null) throw new Exception();
		//更新或插入详细
		for(WorkCalendarDetail w : list){
			WorkCalendarDetail workcd = workflowCalendarDao.findCalendarDetail(calendar.getId(), w.getYearMonth());
			if(workcd != null){
				workcd.setNotWorkday(daySort(w.getNotWorkday()));
				workcd.setWorkday(daySort(w.getWorkday()));
				workflowCalendarDao.updateCalendarDetail(workcd);
			} else {
				w.setCalendarId(calendar.getId());
				w.setNotWorkday(daySort(w.getNotWorkday()));
				w.setWorkday(daySort(w.getWorkday()));
				workflowCalendarDao.addCalendarDetail(w);
			}
		}
		
 		/*if (calendar.getId() == null || calendar.getId().length()==0) {
			workflowCalendarDao.addCalendar(calendar);
			if (calendar.getId() == null) throw new Exception();
			for(WorkCalendarDetail w : list){
				WorkCalendarDetail workcd = workflowCalendarDao.findCalendarDetail(calendar.getId(), w.getYearMonth());
				w.setCalendarId(calendar.getId());
				w.setNotWorkday(daySort(w.getNotWorkday()));
				w.setWorkday(daySort(w.getWorkday()));
				if(workcd != null){
					w.setId(workcd.getId());
					workflowCalendarDao.updateCalendarDetail(w);
				}else{
					workflowCalendarDao.addCalendarDetail(w);
				}
			}
		} else {
			for(WorkCalendarDetail w : list){
				WorkCalendarDetail workcd = workflowCalendarDao.findCalendarDetail(calendar.getId(), w.getYearMonth());
				if(workcd != null){
					w.setNotWorkday(daySort(w.getNotWorkday()));
					w.setWorkday(daySort(w.getWorkday()));
					if(workcd.getNotWorkday()!=null||workcd.getWorkday()!=null){
						workflowCalendarDao.updateCalendarDetail(workcd);
					}
				}else{
					w.setCalendarId(calendar.getId());
					if(w.getNotWorkday()!=null||w.getWorkday()!=null){
						workflowCalendarDao.addCalendarDetail(w);
					}
				}
			}
		}*/
	}
	/**
	 * 
	 * 描述：将"1"变为"01"<br>
	 *
	 * @param str
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-13 下午05:57:10
	 */
	private String upNum(String str){
		if(str != null){
			if(str.length()==1){
				return "0"+str;
			}
		}
		return str; 
	}
	/**
	 * 
	 * 描述：日历排序<br>
	 *
	 * @param str
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-13 下午05:36:38
	 */
	private String daySort(String str){
		if(str!=null && str.length()>2){
			String[] arr = str.split(",");
			Arrays.sort(arr);
			StringBuilder not_work = new StringBuilder();
			for(int i=0,j=arr.length; i<j; i++){
				if(i!=j-1){
					not_work.append(arr[i]).append(",");
				}else{
					not_work.append(arr[i]);
				}
			}
			return not_work.toString();
		}
		return str;
	}
	
	public WorkCalendarDetail getCalendarDetail(String calendarDetailId){
		return workflowCalendarDao.findCalendarDetail(calendarDetailId);
	}
	
	public List<WorkCalendarDetail> getCalendar(String calendarId, String yearMonth){
		return workflowCalendarDao.findAllCalendarDetail(calendarId, yearMonth);
	}
	
	/**
	 * Calendar分页
	 */
	public List<WorkCalendar> findCalendars(String webId,Integer pageindex, Integer pagesize){
		List<WorkCalendar> list = workflowCalendarDao.findCalendars(webId,pageindex,pagesize);
		if(list!=null){
			for(WorkCalendar wc : list){
				Employee e = selectTreeDao.findEmployeeById(wc.getCreatorId());
				wc.setCreatorName(e.getEmployeeName());
				e = selectTreeDao.findEmployeeById(wc.getModifyId());
				if(e!=null)
				wc.setModifyName(e.getEmployeeName());
			}
		}
		return list;
	}
	
	/**
	 * list，Calendar非分页
	 * @param webId
	 * @return
	 */
	public List<WorkCalendar> getCalendars(String webId){
		List<WorkCalendar> list = workflowCalendarDao.getCalendars(webId);
		if(list!=null){
			for(WorkCalendar wc : list){
				Employee e = selectTreeDao.findEmployeeById(wc.getCreatorId());
				wc.setCreatorName(e.getEmployeeName());
				e = selectTreeDao.findEmployeeById(wc.getModifyId());
				if(e!=null)
					wc.setModifyName(e.getEmployeeName());
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		String format="yyyy-MM-dd hh:mm";
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date date;
			date = df.parse("2011-12-30 12:00");
			Calendar startday = Calendar.getInstance();
			startday.setTime(date);
			Calendar endday = Calendar.getInstance();
			endday.setTime(new Date());
			if(startday.after(endday)){
			  Calendar cal=startday;
			  startday=endday;
			  endday=cal;
		    }    
			long sl=startday.getTimeInMillis();
			long el=endday.getTimeInMillis();
			long ei=el-sl;
			double mins = ei/(1000*60);
			int day = Double.valueOf(mins/60/24).intValue();
//			System.out.println(day);
			int hour =Double.valueOf((mins-day*24*60)/60).intValue();
//			System.out.println(hour);
			int min=Double.valueOf(mins-(day*24*60+hour*60)).intValue();
//			System.out.println(min);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 描述：得到某月的工作日
	 *
	 * @param calendarId
	 * @param yearMonth
	 * @return
	 * @throws Exception List<String>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 上午09:04:05
	 */
	private List<String> getWorkdayOfOneMonth(String calendarId,String yearMonth) throws Exception{
		WorkCalendarDetail calendarDetail= workflowCalendarDao.findCalendarDetail(calendarId,yearMonth);
		if(calendarDetail==null){
			throw new Exception("没有ID为"+calendarId+"的工作日历");
		}
		if(calendarDetail.getWorkday()==null){
			return new ArrayList<String>();
		}
		List<String> workdayList = Arrays.asList(calendarDetail.getWorkday().split(","));
		return workdayList;
	}
	
	/**
	 * 
	 * 描述：得到查询工作日所需要的年月字段（如：2012-01）
	 *
	 * @param cal
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 上午09:05:18
	 */
	private String getYearMonth(Calendar cal){
		return cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1>9?cal.get(Calendar.MONTH)+1:"0"+(cal.get(Calendar.MONTH)+1));
	}

	public WorkCalendar getworkCalendar(String id) {
		WorkCalendar wc = workflowCalendarDao.getWorkCalendar(id);
		if(wc!=null){
			wc.setCreatorName(selectTreeDao.findEmployeeById(wc.getCreatorId()).getEmployeeName());
			if(wc.getModifyId()!=null){
				Employee e = selectTreeDao.findEmployeeById(wc.getModifyId());
				wc.setModifyName((e==null?new Employee():e).getEmployeeName());
			}
			getworkByCalendarId(wc.getId());
		}
		return wc;
	}
	/**
	 * 
	 * 描述：得到日历json<br>
	 *
	 * @param id
	 * @return List<WorkCalendarDetail>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-12 下午05:24:08
	 */
	public Map<String,Map<String,Map<String,String>>> getworkByCalendarId(String id){
		List<WorkCalendarDetail> list = workflowCalendarDao.findAllCalendarDetail(id, null);
		StringBuffer date = new StringBuffer("{");
		Set<String> set = new HashSet<String>();
		for(WorkCalendarDetail w : list){
			set.add(w.getYearMonth().split("-")[0]);
		}
		//{"2012":{"1":{"1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"-1","10":"-1","11":"-1","12":"-1","13":"-1","14":"0","15":"0","16":"-1","17":"-1","18":"-1","19":"-1","20":"-1","21":"0","22":"0","23":"-1","24":"-1","25":"-1","26":"-1","27":"-1","28":"0","29":"0","30":"-1","31":"-1"}}};
		Map<String,Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
		for(String year : set){
			date.append("\""+year+"\"");
			Map<String,Map<String,String>> monthMap = new HashMap<String, Map<String,String>>();
			for(WorkCalendarDetail w : list){
				if(w.getYearMonth().contains(year)){
					Map<String,String> workOrNotMap = new HashMap<String, String>();
					if(w.getNotWorkday() != null){
						for(String day : w.getNotWorkday().split(",")){
							workOrNotMap.put(formateNum(day), Constant.NOT_WORK_DAY);
						}
					}
					if(w.getWorkday() != null){
						for(String day : w.getWorkday().split(",")){
							workOrNotMap.put(formateNum(day), Constant.WORK_DAY);
						}
					}
					monthMap.put(formateNum(w.getYearMonth().split("-")[1]), workOrNotMap);
				}
			}
			map.put(year, monthMap);
		}
		return map;
	}
	/**
	 * 
	 * 描述：将"01"变为"1"<br>
	 *
	 * @param str
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-13 下午05:57:51
	 */
	private String formateNum(String str){
		if(str.matches("0\\d{1}")){
			return str.replace("0", "");
		}
		return str;
	}
	
	public boolean workCalendarIsSame(WorkCalendar calendar) {
		return workflowCalendarDao.workCalendarIsSame(calendar)>=1;
	}

	public List<String> getCalendarYear(String calendarId) {
		return workflowCalendarDao.getCalendarYear(calendarId);
	}

	public void delCalendar(String id) {
		workflowCalendarDao.deleteCalendar(id);
		workflowCalendarDao.deleteAllCalendarDetail(id);
	}
	public DTPageBean getProcess(int currentPage,int numPerPage){
//		return workflowProcessDao.getProcessAll(currentPage, numPerPage);
		return null;
	}
	
//	@SuppressWarnings("unchecked")
//	public DTPageBean getSuperintend(int pageIndex,int pageSize,List<String> depid,String title,String importent, String isover,String past,String residue){
//		title = title == null || title.trim().length() == 0 ? null : title.toString();
//		Integer impor = null;
//		try {
//			impor = Integer.valueOf(importent);
//		} catch (NumberFormatException e) {
//			impor = null;
//		}
//		isover = isover == null || isover.trim().length() == 0 ? null : isover.toString();
//		DTPageBean dt = workflowCalendarDao.getSuperintend(pageIndex, pageSize, depid, title, impor, isover);
//		List<Object> list = new ArrayList<Object>();
		
		//返回的pageBean
		//List<Superintend> resultDt = new ArrayList<Superintend>();
		
//		for(Superintend s : (List<Superintend>)dt.getDataList()){
//			Date date = s.getFinishTime();
//			Step step = workflowStepDao.findStepById(s.getWf_st_uid());
//			if(date == null) date = new Date();
//			try {
//				TimeHandle expireDay = calculateRemainTime(step.getWorkCalendar(), date, s.getExpire_time());
//				s.setExpireDay(expireDay);
//			} catch (Exception e) {
//				s.setExpireDay(null);
//			}
//			try {
//				TimeHandle useDay = calculateRemainTime(step.getWorkCalendar(), s.getFinishTime(), date);
//				s.setUseDay(useDay);
//			} catch (Exception e) {
//				s.setUseDay(null);
//			}
//			s.setTimeLimit(step.getTimeLimit());
//			if("true".equals(past))
//				if(s.getFinishTime()==null&&s.getExpireDay().getDays()<0){
//					list.add(s);
//				}
//			if(residue!=null&&residue.trim().length()!=0){
//				if(s.getExpireDay().getDays()<=Integer.valueOf(residue)){
//					list.add(s);
//				}
//			}
//		}
		//if(!list.isEmpty())
//		dt.setDataList(list);
//		if(residue==null&&"true".equals(past)){
//			list.addAll(dt.getDataList());
//		}
//		dt.setTotalRows(list.size());
//		return dt;
//	}

	public int getCountCalendars() {
		
		return workflowCalendarDao.getCountCalendars();
	}
}
