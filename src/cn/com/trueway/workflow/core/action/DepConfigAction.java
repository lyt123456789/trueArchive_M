package cn.com.trueway.workflow.core.action;

import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.service.DepConfigService;
import cn.com.trueway.workflow.set.service.DepartmentService;

/**
 * 部门配置实现
 * 描述：TODO 对DepConfigAction进行描述
 * 作者：季振华
 * 创建时间：2017-8-16 上午10:24:52
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class DepConfigAction extends BaseAction{

	private static final long serialVersionUID = 8299796767861290672L;
	
	private DepConfigService depConfigService;
	
	private DepartmentService departmentService;
	
	private DocXgDepartment docXgDepartment;

	public DepConfigService getDepConfigService() {
		return depConfigService;
	}

	public void setDepConfigService(DepConfigService depConfigService) {
		this.depConfigService = depConfigService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public DocXgDepartment getDocXgDepartment() {
		return docXgDepartment;
	}

	public void setDocXgDepartment(DocXgDepartment docXgDepartment) {
		this.docXgDepartment = docXgDepartment;
	}

	/**
	 * 获取父子部门页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午4:08:06
	 */
	public String getDep_All(){
		return "depAllList";
	}
	
	/**
	 * 获取父部门list
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 上午10:38:30
	 */
	public String getDep_F(){
		//顶级部门id默认为1
		String superior_guid = "1";
		List<DocXgDepartment> list = depConfigService.getDepsBySuperId(superior_guid);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("superior_guid", superior_guid);
		return "depConfigList";
	}
	
	/**
	 * 根据父部门id获取子部门
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午3:12:49
	 */
	public String getDep_C(){
		String id = getRequest().getParameter("id");
		docXgDepartment = depConfigService.getDepById(id);
		String superior_guid = docXgDepartment.getDeptGuid();
		List<DocXgDepartment> list = depConfigService.getDepsBySuperId(superior_guid);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("superior_guid", superior_guid);
		return "depChildList";
	}
	
	/**
	 * 到新增父部门页面
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-8-16 上午11:15:11
	 */
	public String toAddDep_F(){
		String superior_guid = getRequest().getParameter("superior_guid");
		docXgDepartment = new DocXgDepartment();
		docXgDepartment.setPid(superior_guid);
		//父部门默认字段
		docXgDepartment.setIsSub("1");
		docXgDepartment.setHasSub("1");
		return "addDep_F";
	}
	
	/**
	 * 到新增子部门页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午3:28:15
	 */
	public String toAddDep_C(){
		String id = getRequest().getParameter("id");
		docXgDepartment = depConfigService.getDepById(id);
		String superior_guid = docXgDepartment.getDeptGuid();
		docXgDepartment = new DocXgDepartment();
		docXgDepartment.setPid(superior_guid);
		return "addDep_F";
	}
	
	/**
	 * 新增或修改父部门
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-8-16 上午11:40:44
	 */
	public void addDep_F(){
		JSONObject result = depConfigService.addDep_F(docXgDepartment);
		this.outWirter(result);
	}
	
	/**
	 * 判断当前部门id是否已被使用
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午5:00:35
	 */
	public void isSameDep(){
		String depId = docXgDepartment.getDeptGuid();
		String id = docXgDepartment.getId();
		DocXgDepartment dep_old = depConfigService.getDepById(id);
		JSONObject result = new JSONObject();
		//就得部门id与新的一致
		if(null != dep_old
				&& CommonUtil.stringNotNULL(dep_old.getDeptGuid())
				&& depId.equalsIgnoreCase(dep_old.getDeptGuid())){
			result.put("success",true);
		}else{
			DocXgDepartment dep = depConfigService.getDepByDepId(depId);
			if(null != dep){
				result.put("success",false);
			}else{
				result.put("success",true);
			}
		}
		
		this.outWirter(result);
		
	}
	
	/**
	 * 到修改父部门页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:21:25
	 */
	public String toUpdateDep_F(){
		String id = getRequest().getParameter("id");
		docXgDepartment = depConfigService.getDepById(id);
		return "updateDep_F";
	}
	
	/**
	 * 到修改父部门页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:21:25
	 */
	public String toUpdateDep_C(){
		String id = getRequest().getParameter("id");
		docXgDepartment = depConfigService.getDepById(id);
		Department dept = departmentService.findDepartmentById(docXgDepartment.getDeptGuid());
		getRequest().setAttribute("deptName", dept.getDepartmentName());
		return "updateDep_F";
	}
	
	
	/**
	 * 删除父部门
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:48:06
	 */
	public void delDep_F(){
		String ids = getRequest().getParameter("ids");
		String[] ids_arr=ids.split(",");
		JSONObject result = depConfigService.delDep_F(ids_arr);
		this.outWirter(result);
	}
	
	/**
	 * 删除子部门
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午4:50:32
	 */
	public void delDep_C(){
		String ids = getRequest().getParameter("ids");
		String[] ids_arr=ids.split(",");
		JSONObject result = depConfigService.delDep_C(ids_arr);
		this.outWirter(result);
	}
	
	/**
	 * outWirter方法公用
	 * 描述：TODO 对此方法进行描述
	 * @param data void
	 * 作者:季振华
	 * 创建时间:2016-1-27 上午11:22:53
	 */
	public void outWirter(JSONObject data){
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setHeader("Access-Control-Allow-Origin","*");  
		getResponse().setHeader("Access-Control-Allow-Credentials","true");  
		getResponse().setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}

}
