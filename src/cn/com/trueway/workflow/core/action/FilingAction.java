package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFiling;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.Filing;
import cn.com.trueway.workflow.core.service.FilingService;
import cn.com.trueway.workflow.core.service.PendingService;

/**
 * 办件归档Action
 * @author 赵坚
 * 2015年8月18日9:25:00
 */
@SuppressWarnings("serial")
public class FilingAction extends BaseAction {
	
	private FilingService filingService;
	
	private PendingService pendingService;
	
	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}
	
	public FilingService getFilingService() {
		return filingService;
	}

	public void setFilingService(FilingService filingService) {
		this.filingService = filingService;
	}

	public void filedDofile(){
		String processId = this.getRequest().getParameter("processId");
		String itemId = this.getRequest().getParameter("itemId");
		String formId = this.getRequest().getParameter("formId");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		WfProcess process = pendingService.getProcessByID(processId);
		boolean ret = false;
		if(CommonUtil.stringNotNULL(processId)&&CommonUtil.stringNotNULL(itemId)
				&&CommonUtil.stringNotNULL(formId)){
			WfFiling wfFiling = new WfFiling();
			wfFiling.setInstanceId(process.getWfInstanceUid());
			wfFiling.setItemId(itemId);
			wfFiling.setFormId(formId);
			wfFiling.setProcessId(processId);
			wfFiling.setFiledTime(new Date());
			wfFiling.setOperatorId(emp.getEmployeeGuid());
			ret = filingService.insertFiling(wfFiling);
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	public String getFilingList(){
		String wh = getRequest().getParameter("wh");
		String title = getRequest().getParameter("title");
		String dept = getRequest().getParameter("dept");
		String timeFrom = getRequest().getParameter("timeFrom");
		String timeTo = getRequest().getParameter("timeTo");
		
		StringBuilder conditionSql = new StringBuilder("where 1=1");
		if(CommonUtil.stringNotNULL(wh)){
			conditionSql.append(" and k.wh like '%"+wh.trim()+"%'");
		}
		if(CommonUtil.stringNotNULL(title)){
			conditionSql.append(" and k.title like '%"+title.trim()+"%'");
		}
		if(CommonUtil.stringNotNULL(timeFrom)){
			conditionSql.append(" and k.filedtime > to_date('"+timeFrom.trim()+" 00:00:00"+"','yyyy-mm-dd hh24:mi:ss')");
		}
		if(CommonUtil.stringNotNULL(timeTo)){
			conditionSql.append(" and k.filedtime < to_date('"+timeTo.trim()+" 23:59:59"+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String deptSql1 = "";
		String deptSql2 = "";
		if(CommonUtil.stringNotNULL(dept)){
			deptSql1 += " where dbms_lob.instr(t.zsdw,'"+dept.trim()+"',1,1)>0 or "
					+ "dbms_lob.instr(t.csdw,'"+dept.trim()+"',1,1)>0 ";
			deptSql2 += " where p.lwdw like '%"+dept.trim()+"%' ";
		}
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = filingService.getCountOfFilings(conditionSql.toString(), deptSql1, deptSql2);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Filing> filings;
		filings = filingService.getFilingList(conditionSql.toString(), deptSql1, deptSql2, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("wh", wh);
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("dept", dept);
		getRequest().setAttribute("timeFrom", timeFrom);
		getRequest().setAttribute("timeTo", timeTo);
		getRequest().setAttribute("filings", filings);
		
		return "getFilingList";
	}
	
	public void checkFiling(){
		String processStr = getRequest().getParameter("processStr");
		String processIds[] = processStr.split(",");
		List<String> processList = Arrays.asList(processIds);
		Iterator<String> ite = processList.iterator();
		List<String> retList = new ArrayList<String>();
		while (ite.hasNext()) {
			String processId = ite.next();
			if(filingService.checkFiling(processId)){
				retList.add(processId);				
			}
		}
		String ret = "";
		if(!retList.isEmpty()){
			for(String ob:retList){
				ret += (ob+",");
			}
			ret = ret.substring(0,ret.length()-1);
		}
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			pw.write(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			pw.flush();
			pw.close();
		}
	}
}
