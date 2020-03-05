package cn.com.trueway.archives.templates.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.service.BusinessManageService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import net.sf.json.JSONObject;

public class BusinessManageAction extends BaseAction {
	
	private static final long serialVersionUID = 7185751496177780902L;
	
	private BusinessManageService businessManageService;

	public BusinessManageService getBusinessManageService() {
		return businessManageService;
	}

	public void setBusinessManageService(BusinessManageService businessManageService) {
		this.businessManageService = businessManageService;
	}
	
	/**
	 * 跳转到业务管理页面
	 * @return
	 */
	public String toBusinessManageJsp() {
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = this.businessManageService.getBusinessManageCount();
		Paging.setPagingParams(getRequest(), pageSize, count);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<EssBusiness> list = this.businessManageService.getBusinessManageList(map);
		getRequest().setAttribute("list", list);
		return "toBusinessManageJsp";
	}
	
	/**
	 * 跳转到业务新增页面
	 * @return
	 */
	public String toBusinessManageAddJsp() {
		return "businessAddJsp";
	}
	
	/**
	 * 跳转到业务修改页面
	 * @return
	 */
	public String toBusinessManageEditJsp() {
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		List<EssBusiness> list = this.businessManageService.getBusinessManageList(map);
		if(list != null && !list.isEmpty()) {
			getRequest().setAttribute("EssBus", list.get(0));
		}
		return "businessEditJsp";
	} 
	
	/**
	 * 检查标识符是否存在重复
	 */
	public void checkIdentifierById() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String identifier = getRequest().getParameter("identifier");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("identifier", identifier);
		boolean flag = this.businessManageService.checkRecordByMap(map);
		if(flag) {
			obj.put("flag", "have");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存业务修改内容
	 */
	public void saveBusinessManage() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String esTitle = getRequest().getParameter("title");
		String esIdentifier = getRequest().getParameter("identifier");
		String esType = getRequest().getParameter("type");
		String esDescription = getRequest().getParameter("describe");
		String esUrl = "/"+esIdentifier;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("esTitle", esTitle);
		map.put("esIdentifier", esIdentifier);
		map.put("esType", esType);
		map.put("esDescription", esDescription);
		map.put("esUrl", esUrl);
		if(id == null || "".equals(id)) {
			map.put("id", "newAdd");
		} else {
			map.put("id", id);
		}
		boolean flag = this.businessManageService.saveBusinessRecord(map);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除业务信息
	 */
	public void deleteBusiness() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		boolean flag = this.businessManageService.deleteBusinessRecord(ids);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
}
