package cn.com.trueway.workflow.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;
import cn.com.trueway.workflow.core.service.TrueJsonService;

/**
 * 
 * 描述：关于意见json的操作类方法
 * 作者：蔡亚军
 * 创建时间：2016-9-3 下午2:03:44
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TrueJsonAction extends BaseAction{

	private static final long serialVersionUID = 8299796767861290672L;
	
	private TrueJsonService trueJsonService;

	public TrueJsonService getTrueJsonService() {
		return trueJsonService;
	}

	public void setTrueJsonService(TrueJsonService trueJsonService) {
		this.trueJsonService = trueJsonService;
	}
	
	/**
	 * 
	 * 描述：获取json日志信息
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午4:16:34
	 */
	public String getTrueJsonLogList(){
		String title = getRequest().getParameter("title");//标题
		String empName = getRequest().getParameter("empName");//人员姓名
		String actionStep = getRequest().getParameter("actionStep");//操作步骤
		String exeMethod = getRequest().getParameter("exeMethod");//执行方法
		String actMethod = getRequest().getParameter("actMethod");//操作方法
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//执行时间：自
		String commitTimeTo = getRequest().getParameter("commitTimeTo");//执行时间：至
		
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(title)){
			conditionSql += " and t2.process_title like '%" + title + "%' ";
			getRequest().setAttribute("title", title);
		}
		if(CommonUtil.stringNotNULL(empName)){
			conditionSql += " and t4.employee_name = '" + empName + "' ";
			getRequest().setAttribute("empName", empName);
		}
		if(CommonUtil.stringNotNULL(actionStep)){
			conditionSql += " and t3.wfn_name = '" + actionStep + "' ";
			getRequest().setAttribute("actionStep", actionStep);
		}
		if(CommonUtil.stringNotNULL(exeMethod)){
			conditionSql += " and t.excute = '" + exeMethod + "' ";
			getRequest().setAttribute("exeMethod", exeMethod);
		}
		if(CommonUtil.stringNotNULL(actMethod)){
			conditionSql += " and t.readorwrite = '" + actMethod + "' ";
			getRequest().setAttribute("actMethod", actMethod);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
			conditionSql +=" and t.readorwritedate >= to_date('" + commitTimeFrom2 + "','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			String commitTimeTo2 = commitTimeTo + " 23:59:59";
			conditionSql +=" and t.readorwritedate <= to_date('"+commitTimeTo2+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		int count = trueJsonService.findTrueJsonLogCount(conditionSql);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<TrueJsonLog> list = trueJsonService.findTrueJsonLogList(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "trueJsonLogList";
	}
	
	/**
	 * 
	 * 描述：获取json日志信息
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-9-3 下午4:16:34
	 */
	public String getDelFileLogList(){
		String title = getRequest().getParameter("title");//标题
		String empName = getRequest().getParameter("empName");//人员姓名
		String exeMethod = getRequest().getParameter("exeMethod");//执行方法
		String commitTimeFrom = getRequest().getParameter("commitTimeFrom");//执行时间：自
		String commitTimeTo = getRequest().getParameter("commitTimeTo");//执行时间：至
		
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(title)){
			conditionSql += " and t2.process_title like '%" + title + "%' ";
			getRequest().setAttribute("title", title);
		}
		if(CommonUtil.stringNotNULL(empName)){
			conditionSql += " and t4.employee_name = '" + empName + "' ";
			getRequest().setAttribute("empName", empName);
		}
		if(CommonUtil.stringNotNULL(exeMethod)){
			conditionSql += " and t.excute = '" + exeMethod + "' ";
			getRequest().setAttribute("exeMethod", exeMethod);
		}
		if (CommonUtil.stringNotNULL(commitTimeFrom)) {
			commitTimeFrom = commitTimeFrom.trim().replaceAll("'","\\'\\'");
			String commitTimeFrom2 = commitTimeFrom + " 00:00:00";
			conditionSql +=" and t.readorwritedate >= to_date('" + commitTimeFrom2 + "','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeFrom", commitTimeFrom);
		}
		if (CommonUtil.stringNotNULL(commitTimeTo)) {
			commitTimeTo = commitTimeTo.trim().replaceAll("'","\\'\\'");
			String commitTimeTo2 = commitTimeTo + " 23:59:59";
			conditionSql +=" and t.readorwritedate <= to_date('"+commitTimeTo2+"','yyyy-mm-dd hh24:mi:ss') ";	
			getRequest().setAttribute("commitTimeTo", commitTimeTo);
		}
		
		int count = trueJsonService.findDelFileLogCount(conditionSql);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<TrueJsonLog> list = trueJsonService.findDelFileLogList(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "delFileLogList";
	}
	
	public String getTrueJsionById(){
		String id = getRequest().getParameter("id");
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		List<TrueJsonLog> trueJsonList = trueJsonService.getTrueJsonByParams(map);
		if(trueJsonList != null && trueJsonList.size() > 0){
			TrueJsonLog trueJsonLog = trueJsonList.get(0);
			getRequest().setAttribute("trueJsonLog", trueJsonLog);
		}
		return "openTrueJsonPage";
	}
}
