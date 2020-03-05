package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Replay;
import cn.com.trueway.workflow.core.service.ReplayService;

public class ReplayAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private ReplayService replayService;
	
	public ReplayService getReplayService() {
		return replayService;
	}

	public void setReplayService(ReplayService replayService) {
		this.replayService = replayService;
	}

	/**
	 * 得到replay列表   create by jizh 2015-7-14 下午7:24:33
	 * 
	 * @return
	 */
	public String getReplayList() {
		String title = getRequest().getParameter("wfTitle");
		String itemName = getRequest().getParameter("itemName");
		String itemid = getRequest().getParameter("itemid"); // 城管局定制 事项id

		title = CommonUtil.stringNotNULL(title) ? title.replaceAll("'",
				"\\'\\'") : "";
		itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll(
				"'", "\\'\\'") : "";
		String conditionSql = "  ";
		if ( CommonUtil.stringNotNULL(itemName)) {
			itemName = itemName.trim();
			conditionSql += " and i.vc_sxmc like '%" + itemName.trim() + "%' escape '\\'";
			getRequest().setAttribute("itemName", itemName);
		}
		
		if (CommonUtil.stringNotNULL(title)) {
			title = title.trim();
			conditionSql += " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
			getRequest().setAttribute("wfTitle", title);
		}
		
		if(CommonUtil.stringNotNULL(itemid)){	//根据itemid查询
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for(String itemId: itemIds){
				pendingItemId += "'"+itemId+"',";
			}
			if(pendingItemId!=null && pendingItemId.length()>0){
				pendingItemId = pendingItemId.substring(0, pendingItemId.length()-1);
			}
			conditionSql +=" and p.wf_item_uid in ("+pendingItemId+")";
		}

		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(
				MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil
				.getParamValueByParam("pagesize"));

		List<Replay> list;
		// 得到replay列表 数量
		int count = replayService.getCountOfReplay(conditionSql,emp.getEmployeeGuid(), "");
		Paging.setPagingParams(getRequest(), pageSize, count);
		// 包含是否显示推送按钮
		list = replayService.getReplayList(conditionSql,
				emp.getEmployeeGuid(), Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("itemid", itemid);
		
		return "replayList";
	}
	
	
	/**
	 * 
	 * 描述：更新回复意见状态位
	 * 作者:蔡亚军
	 * 创建时间:2015-7-15 上午10:15:41
	 */
	public void updateReplayStatus(){
		String replayId = getRequest().getParameter("replayId");
		Replay replay = replayService.getReplayById(replayId);
		if(replay!=null){
			replay.setStatus("1");
		}
		JSONObject error = replayService.updateReplay(replay);
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
}
