package cn.com.trueway.archives.manage.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.manage.service.FullTextManageService;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.manage.util.CreateIndex;
import cn.com.trueway.archives.manage.util.FullTextSearchUtil;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.util.TaskEntity;
import cn.com.trueway.workflow.set.util.TaskPoolManager;

/**
 * 全文检索管理
 * @author lixr
 *
 */
public class FullTextManageAction extends BaseAction {

	private static final long serialVersionUID = -4786716771613274776L;
	private RoleManageService roleManageService;
	private FullTextManageService fullTextManageService;
	private EssModelService essModelService;
	private ArchiveUsingService archiveUsingService; 
	
	public FullTextManageService getFullTextManageService() {
		return fullTextManageService;
	}

	public void setFullTextManageService(FullTextManageService fullTextManageService) {
		this.fullTextManageService = fullTextManageService;
	}

	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	public EssModelService getEssModelService() {
		return essModelService;
	}

	public void setEssModelService(EssModelService essModelService) {
		this.essModelService = essModelService;
	}

	public ArchiveUsingService getArchiveUsingService() {
		return archiveUsingService;
	}

	public void setArchiveUsingService(ArchiveUsingService archiveUsingService) {
		this.archiveUsingService = archiveUsingService;
	}

	/**
	   * 管理页面
	  **/
	public String toFullTextManageJsp() {
		Map<String,String> map = new HashMap<String, String>();
		List<EssTree> data = roleManageService.getModelTreeByMap(map);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
				json.append("{");
				json.append("\"id\":\"").append(e.getId()).append("\",");
				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				json.append("\"basicData\":").append(e.getId()).append("");
				json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("treeJson", json.toString());
		
		 return "toFullTextManageJsp";
	} 
	
	/**
	   * 数据树节点所关联的索引页面
	  **/
	public String showFUllTextIndexList() {
		String treeId = getRequest().getParameter("treeId");
		//获取子节点
		Map<String,String> map = new HashMap<String, String>();
		map.put("type", "son");//father 往上级递归   son 往下级递归
		map.put("treeId", treeId);
		List<EssTree> sontreelist = fullTextManageService.getTreeNodesList(map);
		String sonString1 = "";//所有子节点的集合1
		String sonString2 = "";//所有子节点的集合2
		for(int i=0;sontreelist!=null&&sontreelist.size()>0&&i<sontreelist.size();i++){
			if(sontreelist.get(i).getIdStructure()!=0&&sontreelist.get(i).getIdStructure()!=-1){
				if(i<1000){//超过1000 in 语句失效
					sonString1+="'"+sontreelist.get(i).getId()+"',";
				}else{
					sonString2+="'"+sontreelist.get(i).getId()+"',";
				}
			}	
		}
		if(!"".equals(sonString1)){
			sonString1 = sonString1.substring(0, sonString1.length()-1);
		}
		if(!"".equals(sonString2)){
			sonString2 = sonString2.substring(0, sonString2.length()-1);
		}
		getRequest().setAttribute("treeNodes1", sonString1);
		getRequest().setAttribute("treeNodes2", sonString2);
		
		 return "toFUllTextIndexMainJsp";
	} 
	
	/**
	   * 已经建立索引的页面
	  **/
	public String showFUllTextIndexYesList() {
		String pagesize = getRequest().getParameter("pageSize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String, String>();
		String treeNodes1 = getRequest().getParameter("treeNodes1");//所有子节点的集合1
		String treeNodes2 = getRequest().getParameter("treeNodes2");//所有子节点的集合2
		//查询已经建立索引的节点集合
		 map.clear();	
		 map.put("treeNodes1", treeNodes1);
		 map.put("treeNodes2", treeNodes2);
		 map.put("flag", "yes");//已经建立
		 int count = fullTextManageService.getFullTextIndexCount(map);
		 Paging.setPagingParams(getRequest(), pageSize, count);
		 List<Object[]> indexYesList = fullTextManageService.getFullTextIndexList(map,Paging.pageIndex,pageSize);
		 getRequest().setAttribute("indexYesList", indexYesList);
			
		getRequest().setAttribute("treeNodes1", treeNodes1);
		getRequest().setAttribute("treeNodes2", treeNodes2);
		
		
		 return "toFUllTextIndexYesList";
	} 
	
	/**
	   * 未建立索引的页面
	  **/
	public String showFUllTextIndexNoList() {
		String pagesize = getRequest().getParameter("pageSize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String, String>();
		String treeNodes1 = getRequest().getParameter("treeNodes1");//所有子节点的集合1
		String treeNodes2 = getRequest().getParameter("treeNodes2");//所有子节点的集合2
		//查询未建立索引的节点集合
		 map.clear();
		 map.put("treeNodes1", treeNodes1);
		 map.put("treeNodes2", treeNodes2);
		 map.put("flag", "no");//已经建立
		 int count = fullTextManageService.getFullTextIndexCount(map);
		 Paging.setPagingParams(getRequest(), pageSize, count);
		 List<Object[]> indexNoList = fullTextManageService.getFullTextIndexList(map,Paging.pageIndex,pageSize);
		 getRequest().setAttribute("indexNoList", indexNoList);
		
		getRequest().setAttribute("treeNodes1", treeNodes1);
		getRequest().setAttribute("treeNodes2", treeNodes2);
		
		 return "toFUllTextIndexNoList";
	} 
	
	public void createIndex(){	
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String treeIds = getRequest().getParameter("treeIds");
		if(treeIds!=null){
			String[] treeIdArr = treeIds.split(",");
			for(int i=0;i<treeIdArr.length;i++){
				String treeId = treeIdArr[i];
				Map<String,String> map = new HashMap<String, String>();
				map.put("treeNodes1", "'"+treeId+"'");
				List<Object[]> indexList = fullTextManageService.getFullTextIndexList(map,null,null);
				String id ="";
				String indexName ="";
				if(indexList!=null&&indexList.size()>0){
					id=indexList.get(0)[0].toString();
					indexName=indexList.get(0)[2].toString();
				}
				map.clear();
				map.put("conditionSql", " and t.id='"+treeId+"'");
				List<EssTree> data = roleManageService.getModelTreeByMap(map);
				
				String structId = "";
				if(data!=null&&data.size()>0){
					structId = data.get(0).getIdStructure().toString();
				}

				TaskEntity te = null;
		        Map<String, Object> params = null;
		        params = new HashMap<String, Object>();
		        params.put("treeId", treeId);
		        params.put("indexName", indexName);
		        params.put("id", id);
		        params.put("structId", structId);
		        params.put("emp", emp);
		        params.put("essModelService", essModelService);
		        params.put("archiveUsingService", archiveUsingService);
		        te = new TaskEntity(CreateIndex.class,"CreateIndex",params);
				TaskPoolManager.newInstance().addTask(te);
			}
		}
	}
	
	public void deleteIndex(){	
		String ids = getRequest().getParameter("ids");
		Map<String,String> map = new HashMap<String, String>();
		map.put("ids", ids);
		List<Object[]> indexlist = fullTextManageService.getFullTextIndexList(map,null,null);
		for(int i=0;indexlist!=null&&indexlist.size()>0&&i<indexlist.size();i++){
			String id= indexlist.get(i)[0].toString();
			String indexpath = indexlist.get(i)[3].toString();
			//删除数据库
			String deleteSql = " update  t_archive_fulltextindex set index_path='' where id='"+id+"'";
			archiveUsingService.updateStore(deleteSql);
			//删除索引
			try {
				FullTextSearchUtil.deleteAll(indexpath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		outWirter("success",getResponse());
	}
	
	
	public void outWirter(String data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin","*");  
		response.setHeader("Access-Control-Allow-Credentials","true");  
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
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