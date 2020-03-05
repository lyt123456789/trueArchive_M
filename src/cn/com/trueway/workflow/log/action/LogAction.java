package cn.com.trueway.workflow.log.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.log.pojo.Log;
import cn.com.trueway.workflow.log.service.LogService;


/**
 * 
 * 描述：工作流中日志信息记录
 * 作者：蔡亚军
 * 创建时间：2016-8-15 上午11:59:49
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class LogAction  extends BaseAction{

	private static final long serialVersionUID = -4738257506951719320L;
	
	private LogService logService;

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	/**
	 * 
	 * 描述：loglist列表查询展示界面
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午2:33:23
	 */
	public String queryLogList(){
		Map<String,String> map = new HashMap<String, String>();
		String createtimeFrom = getRequest().getParameter("createtimeFrom");
		String createtimeTo = getRequest().getParameter("createtimeTo");
		String loglevel = getRequest().getParameter("loglevel");
		String msg = getRequest().getParameter("msg");
		String username = getRequest().getParameter("username");
		String prcoess_title = getRequest().getParameter("prcoess_title");
		String attchmentname = getRequest().getParameter("attchmentname");
		
		getRequest().setAttribute("createtimeFrom", createtimeFrom);
		getRequest().setAttribute("createtimeTo", createtimeTo);
		getRequest().setAttribute("loglevel", loglevel);
		getRequest().setAttribute("msg", msg);
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("prcoess_title", prcoess_title);
		getRequest().setAttribute("attchmentname", attchmentname);
		
		//添加查询参数
		map.put("createtimeFrom", createtimeFrom);
		map.put("createtimeTo", createtimeTo);
		map.put("loglevel", loglevel);
		map.put("msg", msg);
		map.put("username", username);
		map.put("prcoess_title", prcoess_title);
		map.put("attchmentname", attchmentname);
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = logService.countLog(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Log> list = logService.getLogList(map, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		
		return "logList";
	}
	
	/**
	 * 
	 * 描述：log查看详细
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 下午4:33:04
	 */
	public String view(){
		String logid = getRequest().getParameter("logid");
		Log log = logService.findLogById(logid);
		String instanceid = log.getInstanceid();
		if(CommonUtil.stringNotNULL(instanceid)){
			String title = logService.getTitle(instanceid);
			log.setPrcoess_title(title);
		}
		getRequest().setAttribute("log", log);
		
		return "view";
	}
}
