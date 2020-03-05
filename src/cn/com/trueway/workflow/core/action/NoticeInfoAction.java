package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.NoticeInfo;
import cn.com.trueway.workflow.core.service.NoticeInfoService;

public class NoticeInfoAction extends BaseAction{
	
	private static final long serialVersionUID = -5303783394079071805L;
	
	private NoticeInfoService noticeInfoService;

	public NoticeInfoService getNoticeInfoService() {
		return noticeInfoService;
	}

	public void setNoticeInfoService(NoticeInfoService noticeInfoService) {
		this.noticeInfoService = noticeInfoService;
	}
	
	/**
	 * 
	 * 描述：获取通知告示列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-7-21 下午7:24:55
	 */
	public String getNoticeInfoList(){
		String wfTitle = getRequest().getParameter("wfTitle");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String conditionSql = "";
		if ( CommonUtil.stringNotNULL(wfTitle)) {
			wfTitle = wfTitle.trim();
			conditionSql += " and t3.process_title like '%" + wfTitle + "%'";
			getRequest().setAttribute("wfTitle", wfTitle);
		}
		List<NoticeInfo> list;
		int count = noticeInfoService.findNoticeInfoCount(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		list = noticeInfoService.findNoticeInfoList(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "noticeInfoList";
	}
	
	/**
	 * 
	 * 描述：隐藏通知告示信息
	 * 作者:蔡亚军
	 * 创建时间:2015-7-21 下午7:24:30
	 */
	public void updateNoticeInfoStatus(){
		String fprocessId = getRequest().getParameter("fprocessId");
		String status = getRequest().getParameter("status");
		NoticeInfo noticeInfo =noticeInfoService.findNoticeInfoByfprocessId(fprocessId);
		noticeInfo.setStatus(status);
		JSONObject error = noticeInfoService.updateNoticeInfo(noticeInfo);
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
	
	/**
	 * 
	 * 描述：保存推送消息
	 * 作者:蔡亚军
	 * 创建时间:2015-7-23 下午1:11:37
	 */
	public void saveNoticeInfo(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String fProcessId = getRequest().getParameter("fProcessId");
		NoticeInfo noticeInfo = new NoticeInfo();
		noticeInfo.setFprocessId(fProcessId);
		noticeInfo.setUserId(emp.getEmployeeGuid());
		noticeInfo.setSendTime(new Date());
		noticeInfo.setStatus("0");
		JSONObject error = noticeInfoService.saveNoticeInfo(noticeInfo);
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
	
	/**
	 * 
	 * 描述：获取项目合同列表
	 * @return String
	 * 作者:ln
	 * 创建时间:2018.09.10
	 */
	public String getXMList(){
		String xmbh = getRequest().getParameter("xmbh");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String conditionSql = "";
		if ( CommonUtil.stringNotNULL(xmbh)) {
			xmbh = xmbh.trim();
			conditionSql += " and t.XMMC like '%" + xmbh + "%'";
			getRequest().setAttribute("xmbh", xmbh);
		}
		
		int count = noticeInfoService.findXMCount(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Object[]> list = noticeInfoService.findXMList(conditionSql, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		return "XMList";
	}

}
