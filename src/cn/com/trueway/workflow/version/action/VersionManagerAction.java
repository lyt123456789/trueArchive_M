package cn.com.trueway.workflow.version.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.version.pojo.VersionManager;
import cn.com.trueway.workflow.version.service.VersionManagerService;

/**
 * 
 * 描述：工作流版本管理及介绍
 * 作者：Zhaoj☭
 * 创建时间：2014-7-28 下午02:59:23
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class VersionManagerAction extends BaseAction{
	
	private static final long serialVersionUID = 4044234260888036768L;

	private VersionManagerService vmService;
	
	public VersionManagerService getVmService() {
		return vmService;
	}
	public void setVmService(VersionManagerService vmService) {
		this.vmService = vmService;
	}

	/**
	 * 
	 * 描述：跳转添加页面
	 * @return String
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午04:01:41
	 */
	public String toAddVm(){
		String instanceId = UuidGenerator.generate36UUID();
		getRequest().setAttribute("instanceId", instanceId);
		return "toAddVm";
	}
	
	/**
	 * 
	 * 描述：添加
	 * @return String
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午04:01:41
	 */
	public String addVm(){
		String instanceId = getRequest().getParameter("instanceId");
		String num = getRequest().getParameter("num");
		String content = getRequest().getParameter("content");
		String applyProject = getRequest().getParameter("applyProject");
		VersionManager vm = new VersionManager();
		vm.setNum(num);
		vm.setContent(content);
		vm.setApplyProject(applyProject);
		vm.setUpdateDate(new Date());
		vm.setInstanceId(instanceId);
		vmService.addvm(vm);	
		
		return getAllVMList();
	}
	
	/**
	 * 
	 * 描述：跳转修改页面
	 * @return String
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午04:01:41
	 */
	public String toEditVm(){
		String id = getRequest().getParameter("id");
		VersionManager vm = vmService.getVmById(id);
		//管理员
		String isAdmin=getRequest().getParameter("isAdmin");
		
		getRequest().setAttribute("vm", vm);
		getRequest().setAttribute("id", id);
		getRequest().setAttribute("isAdmin", isAdmin);
		getRequest().setAttribute("instanceId", vm.getInstanceId());
		
		return "toEditVm";
	}
	
	/**
	 * 
	 * 描述：添加
	 * @return String
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午04:01:41
	 */
	public String editVm(){
		String instanceId = getRequest().getParameter("instanceId");
		String id = getRequest().getParameter("id");
		//管理员
		String isAdmin=getRequest().getParameter("isAdmin");
		
		VersionManager vm = vmService.getVmById(id);
		String num = getRequest().getParameter("num");
		String content = getRequest().getParameter("content");
		String applyProject = getRequest().getParameter("applyProject");
		vm.setNum(num);
		vm.setContent(content);
		vm.setApplyProject(applyProject);
		vm.setUpdateDate(new Date());
		vmService.updateVm(vm);	
		
		getRequest().setAttribute("isAdmin", isAdmin);
		getRequest().setAttribute("instanceId", instanceId);
		
		return getAllVMList();
	}
	
	/**
	 * 
	 * 描述：删除
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午04:22:00
	 * @throws IOException 
	 */
	public void delVm() throws IOException{
		String id = getRequest().getParameter("ids");
		String[] ids = id.split(",");
		for(int i=0; ids!=null && i<ids.length ;i++){
			String strId = ids[i] ;
			vmService.delVm(strId);
		}
		try {
			getResponse().getWriter().print("success");
		} catch (IOException e) {
			getResponse().getWriter().print("fail");
		}finally{
			getResponse().getWriter().close();
		}
	}
	
	
	/**
	 * 
	 * 描述：获取所有版本信息
	 * @return String
	 *
	 * 作者:Zhaoj☭
	 * 创建时间:2014-7-28 下午03:32:20
	 */
	public String getAllVMList(){
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = vmService.getVmCountForPage();
		
		Paging.setPagingParams(getRequest(), pageSize, count);
		
		String content=getRequest().getParameter("content");
		//拼接查询条件
		String condition="";
		if(!("").equals(content) && content !=null){
			condition = "and t.content like '%"+content.trim()+"%'";
		}
		//管理员进行修改
		String isAdmin=getRequest().getParameter("isAdmin");
		
		List<VersionManager> list = vmService.getVmListForPage(condition, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("isAdmin", isAdmin);
		getRequest().setAttribute("content", content);
		
		return "allVmOfList";
	}
}
