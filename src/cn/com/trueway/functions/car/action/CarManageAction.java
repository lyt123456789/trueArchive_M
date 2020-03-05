package cn.com.trueway.functions.car.action;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.functions.car.entity.ClOut;
import cn.com.trueway.functions.car.service.ClOutService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.service.PendingService;

public class CarManageAction  extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9074731370496931638L;
	
	private static Logger logger = Logger.getLogger(CarManageAction.class); 
	
	private ClOutService clOutService;
	
	private PendingService pendingService;
	
	/**
	 * 得到我的请假单 列表
	 * @return
	 */
	public String getMyApplyList(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		/********城管局定制 区分办结、等办；按事项id分类*******/
		String itemid=getRequest().getParameter("itemid");
		String workflowid=getRequest().getParameter("workflowid");
		//查询条件
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		String status = getRequest().getParameter("status")==null?"":getRequest().getParameter("status");
		
		logger.info("--itemid----"+itemid+"----workflowid----"+workflowid+"----searchDateFrom----"+searchDateFrom+"---searchDateTo---"+searchDateTo+"----status--"+status);
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(status)){
			conditionSql = " and p.status like '%"+status+"%'";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		
		List<ClOut> list = null;
		//分为办理和已办结 加上 流程名
		if(!itemid.equals("")&&itemid!=null){
			int count = clOutService.getCountOfMyApply( conditionSql,emp.getEmployeeGuid(),itemid,searchDateFrom,searchDateTo);
			Paging.setPagingParams(getRequest(), pageSize, count);
			
			logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
			list= clOutService.getMyApplyList(conditionSql,emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,itemid,searchDateFrom,searchDateTo);
				
		}
//		pendingService.initIsOvertime(list);	//设置是否超期提示
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("workflowid", workflowid);
		return "myApplyList";
	}
	
	/**
	 * @return
	 */
	public String getCarPendingList(){  
		String itemid=getRequest().getParameter("itemid");		
		String searchDateFrom = getRequest().getParameter("searchDateFrom");
		String searchDateTo = getRequest().getParameter("searchDateTo");
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		logger.info("--itemid----"+itemid+"----searchDateFrom----"+searchDateFrom+"---searchDateTo---"+searchDateTo);
		List<ClOut> list=null;
		//区分工作流的待办列表获取
		if(itemid!=null && itemid!=""){
			int count = clOutService.getCountOfCarPending(emp.getEmployeeGuid(),itemid,searchDateFrom,searchDateTo);
			Paging.setPagingParams(getRequest(), pageSize, count);
			
			logger.info("--Paging.pageIndex----"+Paging.pageIndex+"----Paging.pageSize----"+Paging.pageSize+"----count----"+count);
			list=clOutService.getPendingCarList(emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize,itemid,searchDateFrom,searchDateTo);	
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("searchDateFrom", searchDateFrom);
		getRequest().setAttribute("searchDateTo", searchDateTo);
		getRequest().setAttribute("itemid", itemid);
		return "pendingCarList";
	}


	public void setClOutService(ClOutService clOutService) {
		this.clOutService = clOutService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}
	
}
