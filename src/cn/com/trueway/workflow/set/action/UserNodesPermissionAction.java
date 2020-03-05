package cn.com.trueway.workflow.set.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.vo.UserNodesPermission;
import cn.com.trueway.workflow.set.service.UserNodesPermissionService;

/**
 * 人员节点权限action
 * @author trueway
 *
 */
public class UserNodesPermissionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private UserNodesPermissionService userNodesPermissionService;
	
	public UserNodesPermissionService getUserNodesPermissionService() {
		return userNodesPermissionService;
	}

	public void setUserNodesPermissionService(
			UserNodesPermissionService userNodesPermissionService) {
		this.userNodesPermissionService = userNodesPermissionService;
	}

	/**
	 * 跳转到人员权限查看界面
	 * @return
	 */
	public String showPermissionToEverBody(){
		String name = CommonUtil.stringIsNULL(this.getRequest().getParameter("name"))?"":this.getRequest().getParameter("name");
		String sxmc = CommonUtil.stringIsNULL(this.getRequest().getParameter("sxmc"))?"":this.getRequest().getParameter("sxmc");
		String nodeName = CommonUtil.stringIsNULL(this.getRequest().getParameter("nodeName"))?"":this.getRequest().getParameter("nodeName");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = userNodesPermissionService.countUserNodesPermission(name,sxmc,nodeName);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Object[]> list = userNodesPermissionService.getUserNodesPermission(name, sxmc,nodeName,Paging.pageIndex, Paging.pageSize);
		List<UserNodesPermission> ulist = new ArrayList<UserNodesPermission>();
		for(Object[] o:list){
			if(o[1]!=null){
				UserNodesPermission unp = new UserNodesPermission();//人员事项节点权限
				List<String> itemSet = new ArrayList<String>();//事项名称集合
				List<Set<String>> nodePermissionSet = new ArrayList<Set<String>>();//节点权限集合
				String[] items = ((String) o[1]).split("\\#\\#");
				Set<String> itemAndNodesSet = new TreeSet<String>(Arrays.asList(items));//使用set集合是因为set集合会自动排序
				for(String item: itemAndNodesSet){
					String[] i = item.split("\\:\\:");
					if(i[1]!=null){
						String[] nodes = i[1].split("\\^\\^");
						Set<String> nodeSet = new TreeSet<String>(Arrays.asList(nodes));//使用set集合是因为set集合会自动排序
						itemSet.add(i[0]);
						nodePermissionSet.add(nodeSet);
					}
					unp.setName((String) o[0]);
					unp.setItemSet(itemSet);
					unp.setNodePermission(nodePermissionSet);
					unp.setItemSize(itemSet.size());
				}
				ulist.add(unp);
				//System.out.println(unp);
			}
		}
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("sxmc", sxmc);
		getRequest().setAttribute("nodeName", nodeName);
		getRequest().setAttribute("ulist", ulist);
		getRequest().setAttribute("pageIndex", Paging.pageIndex);
		return "showPermissionToEverBody";
	}
	
}
