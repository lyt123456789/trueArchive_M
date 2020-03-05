package cn.com.trueway.archives.manage.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.manage.service.BtnManageService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import net.sf.json.JSONObject;

public class BtnManageAction extends BaseAction{
	private static final long serialVersionUID = -6154682244315925810L;
	private BtnManageService btnManageService;
	
	public BtnManageService getBtnManageService() {
		return btnManageService;
	}

	public void setBtnManageService(BtnManageService btnManageService) {
		this.btnManageService = btnManageService;
	}

	/**
	 * 跳转到按钮维护界面
	 * @return
	 */
	public String toBtnManagePage() {
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String esTitle = getRequest().getParameter("esTitle");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("esTitle", esTitle);
		int count = this.btnManageService.getBtnManageListCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<Map<String,String>> list = this.btnManageService.getBtnManageList(map);
		getRequest().setAttribute("list", list);
//		getRequest().setAttribute("esTitle", esTitle);
		return "toBtnManageJsp";
	}
	
	/**
	 * 跳转到新增按钮页面
	 * @return
	 */
	public String toBtnManageAddPage() {
		return "btnManageAddJsp";
	}
	
	/**
	 * 保存新增按钮
	 */
	public void BtnManageSave() {
		JSONObject obj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String id = getRequest().getParameter("id");
		String btn_name = getRequest().getParameter("btn_name");
		String btn_description = getRequest().getParameter("btn_description");
		String esDate = sdf.format(date);
		String btn_sort = getRequest().getParameter("btn_sort");
		Map<String,Object> map = new HashMap<String,Object>();
		if(id != null && !"".equals(id)) {
			map.put("id", id);
			map.put("btn_name", btn_name);
			map.put("esDate", esDate);
			map.put("btn_description", btn_description);
			map.put("btn_sort", btn_sort);
		} else {
			map.put("id", "newAdd");
			map.put("btn_name", btn_name);
			map.put("esDate", esDate);
			map.put("btn_description", btn_description);
			map.put("btn_sort", btn_sort);
		}
		boolean flag = this.btnManageService.saveBtnManageRecord(map);
		if(flag) {
			obj.put("flag", "yes");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到修改按钮
	 * @return
	 */
	public String toBtnManageEditPage() {
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<Map<String,String>> list = this.btnManageService.getBtnManageList(map);
		if(list != null && !list.isEmpty()) {
			Map<String,String> reMap = list.get(0);
			getRequest().setAttribute("id", reMap.get("id"));
			getRequest().setAttribute("btn_name", reMap.get("btn_name"));
			getRequest().setAttribute("btn_description", reMap.get("btn_description"));
			getRequest().setAttribute("btn_sort", reMap.get("btn_sort"));
		}
		return "btnManageEditJsp";
	}
	
	/**
	 * 验证按钮是否可删除
	 */
	public void checkBtnManageDelete() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		boolean flag = true;
		if(flag) {
			obj.put("flag", "delete");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除按钮信息
	 */
	public void deleteBtnManage() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		boolean flag = this.btnManageService.deleteOneBtnManageById(ids);
		if(flag) {
			obj.put("flag", "yes");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}

}
