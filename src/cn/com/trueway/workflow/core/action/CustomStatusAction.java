package cn.com.trueway.workflow.core.action;

import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfCustomStatus;
import cn.com.trueway.workflow.core.service.CustomStatusService;

public class CustomStatusAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CustomStatusService customStatusService;
	
	private WfCustomStatus customStatus;
	

	public CustomStatusService getCustomStatusService() {
		return customStatusService;
	}

	public void setCustomStatusService(CustomStatusService customStatusService) {
		this.customStatusService = customStatusService;
	}

	public WfCustomStatus getCustomStatus() {
		return customStatus;
	}

	public void setCustomStatus(WfCustomStatus customStatus) {
		this.customStatus = customStatus;
	}

	public String getCustomStatusList(){
		String b_global = getRequest().getParameter("b_global");
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		
		if(customStatus == null){
			customStatus = new WfCustomStatus();
		}
		if("1".equals(b_global)){
			customStatus.setLcid(null);
		}else{
			customStatus.setLcid(lcid);
		}
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = customStatusService.getCustomStatusCountForPage(column, value, customStatus);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfCustomStatus> list = customStatusService.getCustomStatusListForPage(column, value, customStatus, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("b_global", b_global);
		getRequest().setAttribute("lcid", lcid);
		getRequest().setAttribute("list", list);
		return "customStatusList";
	}
	
	public String toAddJsp(){
		String b_global = getRequest().getParameter("b_global");
		getRequest().setAttribute("b_global", b_global);
		return "toAddJsp";
	}
	
	public String add(){
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String b_global = getRequest().getParameter("b_global");
		if("1".equals(b_global)){
			customStatus.setLcid(null);
		}else{
			customStatus.setLcid(lcid);
		}
		customStatusService.addCustomStatus(customStatus);
		return null;
	}
	
	public String del(){
		String id = getRequest().getParameter("ids");
		String[] ids = id.split(",");
		for(String strId: ids){
			customStatus = customStatusService.getCustomStatusById(strId);
			customStatusService.delCustomStatus(customStatus);
		}
		return getCustomStatusList();
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		customStatus = customStatusService.getCustomStatusById(id);
		String b_global = getRequest().getParameter("b_global");
		getRequest().setAttribute("b_global", b_global);
		getRequest().setAttribute("customStatus", customStatus);
		return "toEditJsp";
	}
	
	public String edit(){
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String b_global = getRequest().getParameter("b_global");
		if("1".equals(b_global)){
			customStatus.setLcid(null);
		}else{
			customStatus.setLcid(lcid);
		}
		customStatusService.addCustomStatus(customStatus);
		return getCustomStatusList();
	}
	
}
