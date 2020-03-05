package cn.com.trueway.workClander.action;

import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workClander.pojo.Workday;
import cn.com.trueway.workClander.service.WorkdayService;
import cn.com.trueway.workflow.core.action.thread.UpdateTimeThread;
import cn.com.trueway.workflow.core.service.TableInfoService;

public class WorkdayAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorkdayService workdayService;
	private Workday workday;
	
	private TableInfoService tableInfoService;
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public Workday getWorkday() {
		return workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}

	public WorkdayService getWorkdayService() {
		return workdayService;
	}

	public void setWorkdayService(WorkdayService workdayService) {
		this.workdayService = workdayService;
	}
	/**
	 * 获得工作日历年度列表
	 * @return
	 */
	public String getList(){
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		
		getRequest().setAttribute("column", column==null?"":column);
		getRequest().setAttribute("value", value==null?"":value);
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=workdayService.getWorkdayCountForPage(column, value, workday);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Workday> list=workdayService.getWorkdayListForPage(column, value, workday, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "getList";
	}
	
	/**转向新增日历页面
	 * @return
	 */
	public String toAddJsp(){
		getRequest().setAttribute("type", getRequest().getParameter("type"));
		return "toAddJsp";
	}
	
	/**新增日历
	 * @return
	 */
	public String add(){
		String type=getRequest().getParameter("type");
		String wordays=getRequest().getParameter("wordays");
		if (wordays!=null&&!wordays.equals("")) {
			String[] arr = wordays.split(",");
			
			if (type.equals("add")) {
				//查询年度日历,如果有则提示，如果没有则新增
				List<Workday> list=workdayService.getListByYear(arr[0].substring(0,4));
				if (list!=null&&list.size()>0) {
					getRequest().setAttribute("mes", arr[0].substring(0,4)+"年度日历已存在");
				}else {
					//新增日历
					for (int i = 0; i < arr.length; i++) {
						Workday w=new Workday();
						w.setTime(arr[i]);
						w.setYear(arr[i].substring(0,4));
						workdayService.save(w);
					}
				}
			}else if (type.equals("update")){
				//删除日历
				workdayService.deleteByYear(arr[0].substring(0,4));
				//新增日历
				for (int i = 0; i < arr.length; i++) {
					Workday w=new Workday();
					w.setTime(arr[i]);
					w.setYear(arr[i].substring(0,4));
					workdayService.save(w);
				}
			}
			
			//添加线程去执行更新t_wf_process的节点期限与最后期限
			UpdateTimeThread timeThread = new UpdateTimeThread(tableInfoService);
			timeThread.start();
			
		}
		return getList();
	}
	
	/**删除日历
	 * @return
	 */
	public String delete(){
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				workdayService.deleteByYear(idsArr[i]);
			}
		}
		
		return getList();
	}
	
	/**转向修改日历页面
	 * @return
	 */
	public String toUpdateJsp(){
		getRequest().setAttribute("type", getRequest().getParameter("type"));
		
		String year=getRequest().getParameter("year");
		List<Workday> list=workdayService.getListByYear(year);
		String str="";
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				str+=i==list.size()-1?list.get(i).getTime():list.get(i).getTime()+",";
			}
			getRequest().setAttribute("year", list.get(0).getYear());
		}
		getRequest().setAttribute("str",str);
		return "toUpdateJsp";
	}
	
	/**更新日历
	 * @return
	 */
	public String update(){
		
		return getList();
	}
}
