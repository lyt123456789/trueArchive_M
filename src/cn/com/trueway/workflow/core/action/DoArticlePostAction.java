package cn.com.trueway.workflow.core.action;

import java.util.ArrayList;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.DoArticlePostService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.set.service.DepartmentService;

public class DoArticlePostAction extends BaseAction{

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private DoArticlePostService doArticlePostService;
	
	private DepartmentService departmentService;
	
	private ItemService itemService;
	
	private DictionaryService dictionaryService;
	
	public DoArticlePostService getDoArticlePostService() {
		return doArticlePostService;
	}


	public void setDoArticlePostService(DoArticlePostService doArticlePostService) {
		this.doArticlePostService = doArticlePostService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}


	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}


	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}


	/**
	 * 
	 * 描述：自定义办件查询
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-8-14 下午6:14:55
	 */
	public String getFreeDofileList(){
		// 获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		// 根据用户权限获取事项类别 管理员查询全部事项
		//查询部门
		Department dept = departmentService.findDepartmentById(emp.getDepartmentGuid());
		String isFiling = SystemParamConfigUtil.getParamValueByParam("isFiling");//控制权限
		
		if(isFiling==null || isFiling.equals("")){
			getRequest().setAttribute("isFiling", "0");
		}else{
			String empId = emp.getEmployeeGuid();
			String [] isFilings = isFiling.split(",");
			for (String a: isFilings){
				if (a.equals(empId)){
					getRequest().setAttribute("isFiling", "1");
				}
			}
		}
		String bt =  getRequest().getParameter("bt"); //标题
		
		String lwh =  getRequest().getParameter("lwh"); //来文号
		
		String lwdw =  getRequest().getParameter("lwdw"); //来文单位
		
		String beginTime = getRequest().getParameter("beginTime");//开始时间
		
		String endTime = getRequest().getParameter("endTime");//结束时间
		
		String btfw =  getRequest().getParameter("btfw"); //发文标题
		
		String fwh = getRequest().getParameter("fwh"); //发文号
		
		String fsbm = getRequest().getParameter("fsbm"); //发送部门
		
		String fwbeginTime = getRequest().getParameter("fwbeginTime");//fw开始时间
		
		String fwendTime = getRequest().getParameter("fwendTime");//fw结束时间
		
		String bjlx = getRequest().getParameter("bjlx"); //办件类型
		// 根据itemid 查询 
		String itemid = getRequest().getParameter("itemid"); 
		
		// 根据workflowId 查询 
		String workflowId = getRequest().getParameter("workflowId"); 
		
		String conditionSql = "";
		if (CommonUtil.stringNotNULL(itemid)) {
			String[] itemIds = itemid.split(",");
			String pendingItemId = "";
			for (String itemId : itemIds) {
				pendingItemId += "'" + itemId + "',";
			}
			if (pendingItemId != null && pendingItemId.length() > 0) {
				pendingItemId = pendingItemId.substring(0,
						pendingItemId.length() - 1);
			}
			conditionSql += " and i.id  in ( " + pendingItemId + " ) ";
		}
		
		if (CommonUtil.stringNotNULL(workflowId)) {
			String[] workflowIds = workflowId.split(",");
			String pendingWorkflowId = "";
			for (String itemId : workflowIds) {
				pendingWorkflowId += "'" + itemId + "',";
			}
			if (pendingWorkflowId != null && pendingWorkflowId.length() > 0) {
				pendingWorkflowId = pendingWorkflowId.substring(0,
						pendingWorkflowId.length() - 1);
			}
			conditionSql += " and d.workflowId  in ( " + pendingWorkflowId + " ) ";
		}
		
		String resultSql = "";
		if ("0".equals(bjlx)) {
			if (!CommonUtil.stringIsNULL(bt)) {
				conditionSql += "and t.title like '%" + bt + "%'";
			}
			if (!CommonUtil.stringIsNULL(lwh)) {
				conditionSql += "and t.wh like '%" + lwh + "%'";
			}
			if (!CommonUtil.stringIsNULL(lwdw)) {
				conditionSql += "and t.lwdw like '%" + lwdw + "%'";
			}
			if (!CommonUtil.stringIsNULL(beginTime)) {
				conditionSql += "and p.apply_time > to_date('" + beginTime + "00:00:00"
						+ "','yyyy-MM-dd hh24:mi:ss')";
			}
			if (!CommonUtil.stringIsNULL(endTime)) {
				conditionSql += "and p.apply_time < to_date('" + endTime + "23:59:59"
						+ "','yyyy-MM-dd hh24:mi:ss')";
			}
			resultSql = " distinct ";
		} else if ("1".equals(bjlx)) {
			if (!CommonUtil.stringIsNULL(btfw)) {
				conditionSql += "and t.title like '%" + btfw + "%'";
			}
			if (!CommonUtil.stringIsNULL(fwh)) {
				conditionSql += "and  t.wh like '%" + fwh + "%'";
			}
			if (!CommonUtil.stringIsNULL(fsbm)) {
				fsbm = fsbm.trim();
				conditionSql += "and  (t.zbbm like '%" + fsbm
						+ "%' or t.csbm like '%" + fsbm + "%')";
			}
			if (!CommonUtil.stringIsNULL(fwbeginTime)) {
				conditionSql += "and p.apply_time > to_date('" + fwbeginTime + " 00:00:00"
						+ "','yyyy-MM-dd hh24:mi:ss')";
			}
			if (!CommonUtil.stringIsNULL(fwendTime)) {
				conditionSql += "and p.apply_time < to_date('" + fwendTime + " 23:59:59"
						+ "','yyyy-MM-dd hh24:mi:ss')";
			}
			resultSql = " distinct ";
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil
				.getParamValueByParam("pagesize"));
		if (!resultSql.equals("") || "0".equals(bjlx) || "1".equals(bjlx)) {
			int count = doArticlePostService.getCountOfFreeDofile(conditionSql,
					"", emp.getEmployeeGuid(), "", null, bjlx);
			Paging.setPagingParams(getRequest(), pageSize, count);

			List<Object> list;
			// 包含是否显示推送按钮
			list = doArticlePostService.getFreeDofileList("", conditionSql, "",
					resultSql, emp.getEmployeeGuid(), Paging.pageIndex,
					Paging.pageSize, null, bjlx);
			getRequest().setAttribute("list", list);
		} else {
			Paging.setPagingParams(getRequest(), pageSize, 0);
		}

		if(getSession().getAttribute("myPendItems") == null){
			List<WfItem> items = new ArrayList<WfItem>();
				Department department=null;
				try {
					department = departmentService.queryDepartmentById(emp.getDepartmentGuid());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//根据机构逆推
				boolean flag = true ;
				String depids = "";
				String depid = department.getDepartmentGuid();
				Department depart=null;
				while(flag){
					depart = departmentService.queryDepartmentById(depid);
					if(depart!=null){
						depids+= "'"+depid +"',";
						depid = depart.getSuperiorGuid();
						if(depid!=null && depid.equals("1")){
							flag = false;
						}
					}
				}
				if(depids!=null && depids.length()>0){
					depids = depids.substring(0,depids.length()-1);
				}
				items = itemService.getItemListByDeptIds(depids,"");
			getRequest().setAttribute("myPendItems", items);
		}
		
		getRequest().setAttribute("itemid", itemid);
		getRequest().setAttribute("bjlx", bjlx);
		getRequest().setAttribute("bt", bt);
		getRequest().setAttribute("lwh", lwh);
		getRequest().setAttribute("lwdw", lwdw);
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("btfw", btfw);
		getRequest().setAttribute("fwh", fwh);
		getRequest().setAttribute("fsbm", fsbm);
		getRequest().setAttribute("fwbeginTime", fwbeginTime);
		getRequest().setAttribute("fwendTime", fwendTime);
		getRequest().setAttribute("workflowId", workflowId);
		if ("0".equals(bjlx)) {
			return "doArticlePostList";
		} else if ("1".equals(bjlx)) {
			return "doArticlePostFwList";
		} 
		else {
			return "doArticlePostQtList";
		}

	}

}
