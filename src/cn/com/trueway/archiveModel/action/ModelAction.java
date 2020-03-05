package cn.com.trueway.archiveModel.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.listener.initDataListener;
import cn.com.trueway.archiveModel.service.EssEepService;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.service.FullTextManageService;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.manage.util.FullTextSearchUtil;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Transferstore;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.archives.using.util.FtpUtil;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;


public class ModelAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private EssModelService essModelService;
	@Autowired
	private EssEepService essEepService;
	private DepartmentService departmentService;
	private EmployeeService employeeService;
	private ArchiveUsingService archiveUsingService; 
	private RoleManageService roleManageService;
	private FullTextManageService fullTextManageService;
	public EssModelService getEssModelService() {
		return essModelService;
	}

	public void setEssModelService(EssModelService essModelService) {
		this.essModelService = essModelService;
	}

	public EssEepService getEssEepService() {
		return essEepService;
	}

	public void setEssEepService(EssEepService essEepService) {
		this.essEepService = essEepService;
	}

	public ArchiveUsingService getArchiveUsingService() {
		return archiveUsingService;
	}

	public void setArchiveUsingService(ArchiveUsingService archiveUsingService) {
		this.archiveUsingService = archiveUsingService;
	}

	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	
	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	public FullTextManageService getFullTextManageService() {
		return fullTextManageService;
	}

	public void setFullTextManageService(FullTextManageService fullTextManageService) {
		this.fullTextManageService = fullTextManageService;
	}

	/**
	 * 利用系统首页
	 **/
	public String toIndex() {
		//获得目录树权限
		Map<String,String> map = new HashMap<String, String>();
		List<EssTree> data = roleManageService.getModelTreeByMap(map);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
				Integer idStructure = e.getIdStructure();
				json.append("{");
				json.append("\"id\":\"").append(e.getId()).append("\",");
				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				json.append("\"basicData\":").append(idStructure).append("");
				json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().getSession().setAttribute("treeJson", json);
		return "index";
	}
	/**
	 * 管理中心
	 **/
	public String toManageJsp() {
		return "manageJsp";
	}
	/**
	 * 利用系统欢迎页
	 **/
	public String toWelcomeJsp() {
		return "welcome";
	}
	
	
	public void checkCasualUser(){
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Map<String,String> map = new HashMap<String, String>();
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		//String zzcdFlag = getRequest().getParameter("zzcdFlag");
		map.put("username", username);
		map.put("password", password);
		//首先验证临是否有临时用户
		List<CasualUser> list = this.roleManageService.getCasualUserList(map,null, null);
		if(list!=null&&list.size()>0){
			CasualUser cu = list.get(0);
			String starttime = cu.getCasualStartTime();
			String endtime = cu.getCasualEndTime();
			if(endtime==null||"".equals(endtime)){
				getRequest().getSession().setAttribute("CASUALUSERID", cu.getId());
				outWirter("success",getResponse());
			}else{
				int a = Integer.valueOf(endtime.replaceAll("-", ""));
				int b = Integer.valueOf(sd.format(new Date()));
				if(a>b||a==b){
					getRequest().getSession().setAttribute("CASUALUSERID", cu.getId());
					outWirter("success",getResponse());
				}else{
					outWirter("此账号权限已到期！",getResponse());
				}
			}	
		}else{
			outWirter("无此账号！",getResponse());
		}
	}
	
	public String zzcdLogin(){
		Map<String,String> map = new HashMap<String, String>();
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		String zzcdFlag = getRequest().getParameter("zzcdFlag");
		map.put("username", username);
		map.put("password", password);
		List<CasualUser> list = this.roleManageService.getCasualUserList(map,null, null);
		getRequest().getSession().setAttribute("CasualUser",  list.get(0));
		String treeNodes = list.get(0).getMenuRight();
		//获得目录树权限
		List<EssTree> data = roleManageService.getModelTreeByMap(map);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
			if(treeNodes!=""&&(treeNodes+",").indexOf(e.getId()+",")>-1){
				Integer idStructure = e.getIdStructure();
				json.append("{");
				json.append("\"id\":\"").append(e.getId()).append("\",");
				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				json.append("\"basicData\":").append(idStructure).append("");
				json.append("},");
			}
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		
		getRequest().getSession().setAttribute("treeJsonOfZzcd", json.toString());
		
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		getRequest().getSession().setAttribute("zzcdFlag", zzcdFlag);
		
		return "index4zzcd";
	}
	
	/**
	 * 查询功能页
	 **/
	public String toSearchJsp() {
		String jydId = getRequest().getParameter("jydId");//判断是否从借阅单过来的
		String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//判断是否是自主查档
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		return "toSearchJsp";
	}
	
    public String toModelMenu4Key() {
    	Map<String,String> map = new HashMap<String, String>();
    	String jydId = getRequest().getParameter("jydId");//判断是否从借阅单过来的
    	String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
    	String zzcdFlag = getRequest().getParameter("zzcdFlag");//判断是否是自主查档
    	StringBuffer json = new StringBuffer();
    	if("1".equals(zzcdFlag)){
    		CasualUser cu = (CasualUser)getRequest().getSession().getAttribute("CasualUser");
    		String treeNodes = cu.getMenuRight();
    		map.put("conditionSql", " and (id_parent_node=1 or id=1) ");//查询出根节点以及下一级子节点
    		List<EssTree> data = roleManageService.getModelTreeByMap(map);
    		json.append("[");
    		for (EssTree e : data) {
    			if(treeNodes!=""&&(treeNodes+",").indexOf(e.getId()+",")>-1){
    				Integer idStructure = e.getIdStructure();
    				json.append("{");
    				json.append("\"id\":\"").append(e.getId()).append("\",");
    				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
    				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
    				json.append("\"checkArr\":\"").append("0\",");
    				if(!"1".equals(e.getIsLeaf()+"")){
    					json.append("\"isLast\":").append("false,");
    				}
    				json.append("\"basicData\":").append(idStructure).append("");
    				json.append("},");
    			}
    		}
    		int index = json.lastIndexOf(",");
    		if(index>-1){
    			json.deleteCharAt(index);
    		}
    		json.append("]");
    		//json = (String)getRequest().getSession().getAttribute("treeJsonOfZzcd2");//treeJsonOfZzcd1：全部加载      treeJsonOfZzcd2：分级展示
    	}else{
    		map.put("conditionSql", " and (id_parent_node=1 or id=1) ");//查询出根节点以及下一级子节点
    		List<EssTree> data = roleManageService.getModelTreeByMap(map);
    		json.append("[");
    		for (EssTree e : data) {
    				Integer idStructure = e.getIdStructure();
    				json.append("{");
    				json.append("\"id\":\"").append(e.getId()).append("\",");
    				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
    				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
    				json.append("\"checkArr\":\"").append("0\",");
    				if(!"1".equals(e.getIsLeaf()+"")){
    					json.append("\"isLast\":").append("false,");
    				}
    				json.append("\"basicData\":").append(idStructure).append("");
    				json.append("},");
    		}
    		int index = json.lastIndexOf(",");
    		if(index>-1){
    			json.deleteCharAt(index);
    		}
    		json.append("]");
    		//json = (String)getRequest().getSession().getAttribute("treeJson2");//分级展示
    	}
    	getRequest().setAttribute("dataJson", json.toString());
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
        return "modelMenu4Key";
    }
    
    public String toModelMenu4Zh() {
    	String jydId = getRequest().getParameter("jydId");//判断是否从借阅单过来的
    	String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
    	String zzcdFlag = getRequest().getParameter("zzcdFlag");//判断是否是自主查档
    	String json ="";
    	if("1".equals(zzcdFlag)){
    		json = (String)getRequest().getSession().getAttribute("treeJsonOfZzcd");
    	}else{
    		json = (String)getRequest().getSession().getAttribute("treeJson");
    	}
    	getRequest().setAttribute("dataJson", json);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
        return "modelMenu4Zh";
    }
    
    public String toModelMenu4QW() {
    	String jydId = getRequest().getParameter("jydId");//判断是否从借阅单过来的
    	String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
    	String zzcdFlag = getRequest().getParameter("zzcdFlag");//判断是否是自主查档
    	String json ="";
    	if("1".equals(zzcdFlag)){
    		json = (String)getRequest().getSession().getAttribute("treeJsonOfZzcd");
    	}else{
    		json = (String)getRequest().getSession().getAttribute("treeJson");
    	}
    	getRequest().setAttribute("dataJson", json);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
        return "modelMenu4QW";
    }

    /**
	 * 获取档案数据  树结构
	 **/
	public void getModelTreeData() throws IOException{
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//判断是否是自主查档
		String id = getRequest().getParameter("nodeId");
		String pid = getRequest().getParameter("parentId");
		String json ="";
		//if("1".equals(zzcdFlag)){
		//	json = (String)getRequest().getSession().getAttribute("treeJsonOfZzcd");
		//}else{
			//json = initDataListener.getTreeJsonMap().get("treeJson")!=null?initDataListener.getTreeJsonMap().get("treeJson").toString():null;
		//	if(json==null||"".equals(json)){
				json = essModelService.getModelTreeById(null, id);// id  pid
		//	}
		//}
		getResponse().setContentType("text/plain;UTF-8");
		this.outWirter(json, getResponse());
	}
	
	/**
	 * 关键词查询页面
	 **/
	public String toModelTask4Key() {
		String type = getRequest().getParameter("type");//0：从数据树进入，1：从页面搜索进入
		String searchType = getRequest().getParameter("searchType");//zh：页面搜索，然后综合搜索
		String struc = getRequest().getParameter("structId");
		String fatherId = getRequest().getParameter("fatherId");
		String codeId = getRequest().getParameter("codeId");
		String pagesize = getRequest().getParameter("pageSize");
		String keyWord = getRequest().getParameter("keyWord");
		String row = getRequest().getParameter("row");
		String jydId = getRequest().getParameter("jydId");
		String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//自主查询
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		String zhconditionsql="";//综合查询的sql
		getRequest().setAttribute("codeId", codeId);
		if("0".equals(type)){//查询子结构
			fatherId=struc;
			if(struc!=null && !"0".equals(struc)) {
				List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(struc));
				List<String> jjtypelist = new ArrayList<String>();
				if(strucList!=null){
					for(Map<String, Object> map:strucList) {
						String idChild = map.get("IDCHILD").toString();
						String title = map.get("TITLE").toString();
						String parentId = map.get("PID").toString();
						//获取结构map//判断当前选中的层级是哪个子结构
						if("项目目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "1");
							}
						}else if("案卷目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "2");
							}
						}else if("卷内目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "3");
							}
						}else if("电子文件".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "4");
							}
						}
						if(!"电子文件".equals(title)){
							jjtypelist.add(idChild+","+title);
						}
					}
				}
				getRequest().setAttribute("jjtypelist", jjtypelist);
			
			}
		}else if("1".equals(type)){//根据父结构去查子结构
			if(fatherId!=null && !"0".equals(fatherId)) {
				List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(fatherId));
				List<String> jjtypelist = new ArrayList<String>();
				if(strucList!=null){
					for(Map<String, Object> map:strucList) {
						String idChild = map.get("IDCHILD").toString();
						String title = map.get("TITLE").toString();
						String parentId = map.get("PID").toString();
						//获取结构map//判断当前选中的层级是哪个子结构
						if("项目目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "1");
							}
						}else if("案卷目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "2");
							}
						}else if("卷内目录".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "3");
							}
						}else if("电子文件".equals(title)){
							if(struc.equals(idChild)){
								getRequest().setAttribute("structType", "4");
							}
						}
						if(!"电子文件".equals(title)){
							jjtypelist.add(idChild+","+title);
						}
					}
				}
				getRequest().setAttribute("jjtypelist", jjtypelist);
			}
			if(row!=null&&!"".equals(row)){
				int count = Integer.valueOf(row);
				getRequest().setAttribute("count", count);
				List<String> countlist = new ArrayList<String>();
				Map<String,String> columnMap = new HashMap<String, String>();
				for(int i=1;i<count;i++){			
					String keyword = getRequest().getParameter("keyWord"+i);
					String lkh = getRequest().getParameter("lkh"+i);
					String column = getRequest().getParameter("column"+i);
					String com = getRequest().getParameter("com"+i);
					String rkh = getRequest().getParameter("rkh"+i);
					String rela = getRequest().getParameter("rela"+i);
					columnMap.put("keyWord"+i, keyword);
					columnMap.put("lkh"+i, lkh);
					columnMap.put("column"+i, column);
					columnMap.put("com"+i, com);
					columnMap.put("rkh"+i, rkh);
					columnMap.put("rela"+i, rela);
					getRequest().setAttribute("keyWord"+i, keyword);
					getRequest().setAttribute("lkh"+i, lkh);
					getRequest().setAttribute("column"+i, column);
					getRequest().setAttribute("com"+i, com);
					getRequest().setAttribute("rkh"+i, rkh);
					getRequest().setAttribute("rela", rela);
					countlist.add(i+"");
					if(keyword!=null&&!"".equals(keyword)){										
						if(lkh!=null&&!"".equals(lkh)){
							zhconditionsql+=lkh+" ";
						}
						if(column!=null&&!"".equals(column)){
							zhconditionsql+="C"+column+" ";
						}
						if(com!=null&&!"".equals(com)){
							if("1".equals(com)){//包含
								zhconditionsql+= " like '%"+keyword+"%' ";
							}else if("2".equals(com)){//等于
								zhconditionsql+= " = '"+keyword+"' ";
							}else if("3".equals(com)){//小于
								zhconditionsql+= " < '"+keyword+"' ";
							}else if("4".equals(com)){//小于等于
								zhconditionsql+= " <= '"+keyword+"' ";
							}else if("5".equals(com)){//大于等于
								zhconditionsql+= " >= '"+keyword+"' ";
							}else if("6".equals(com)){//大于
								zhconditionsql+= " > '"+keyword+"' ";
							}else if("7".equals(com)){//不等于
								zhconditionsql+= " <> '"+keyword+"' ";
							}else if("8".equals(com)){//不包含
								zhconditionsql+= " not like '%"+keyword+"%' ";
							}
						}
						if(rkh!=null&&!"".equals(rkh)){
							zhconditionsql+=rkh+" ";
						}
						if(rela!=null&&!"".equals(rela)){
							zhconditionsql+=rela+" ";
						}
					}else{
						continue;
					}
				}
				getRequest().setAttribute("countlist", countlist);
				getRequest().setAttribute("columnMap", columnMap);
			}
		}else{
			getRequest().setAttribute("jydId", jydId);
			getRequest().setAttribute("djdId", djdId);
			Paging.setPagingParams(getRequest(), 10, 0);
			return "searchList4Key";
		}
		//获取字段名(配置的展示字段)
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", struc);
		List<EssTag> tags = essModelService.queryTagsForShow(map);
		String tagstr = "";
		for(int j=0;j<tags.size();j++){
			tagstr+=tags.get(j).getId()+","+tags.get(j).getEsIdentifier()+";";
		}
		getRequest().setAttribute("tagstr", tagstr);
		//获取检索列
		List<EssTag> tags1 = essModelService.queryTagsForSearch(map);
		//获取排序列
		String orderSql = essModelService.queryTagsForOrder(map);
		//组织查询条件
		if("1".equals(zzcdFlag)){//临时用户数据限制
			String CASUALUSERID = getRequest().getSession().getAttribute("CASUALUSERID").toString();
			Map<String,String> tempmap = new HashMap<String,String>();
			tempmap.put("casualId", CASUALUSERID);
			tempmap.put("dataIdChild", struc);
			CasualUserDataRange cudr = roleManageService.getCasualUserDataRange(tempmap);
			if(cudr!=null){
				String rangeSql = cudr.getSqlCondition();
				if("".equals(zhconditionsql)){
					zhconditionsql+=rangeSql;
				}else{
					zhconditionsql+= " and "+rangeSql;
				}
				
			}	
		}
		
		//查询数据
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("struc", struc);
		map1.put("keyWord", keyWord);
		map1.put("orderSql", orderSql);
		map1.put("zhconditionsql", zhconditionsql);
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count =essModelService.getTaskDetailsCount(map1,tags,tags1);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Map<String, Object>> list = essModelService.getTaskDetails(map1,tags,tags1, Paging.pageSize,Paging.pageIndex);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("tags", tags);
		getRequest().setAttribute("structId", struc);
		getRequest().setAttribute("fatherId", fatherId);
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("keyWord", keyWord);
		getRequest().setAttribute("searchType", searchType);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		return "searchList4Key";
	}
	
	/**
	 * 综合业务查询页面
	 **/
	public String toModelTask4Zh() {
		String zhywIds = getRequest().getParameter("zhywIds");
		String pagesize = getRequest().getParameter("pageSize");
		String keyWord = getRequest().getParameter("keyWord");
		String keyWordOld = getRequest().getParameter("keyWordOld");
		String searchType = getRequest().getParameter("searchType");//首次还二次检索
		String zhywType = getRequest().getParameter("zhywType");//案卷还是卷内
		String jydId = getRequest().getParameter("jydId");
		String djdId = getRequest().getParameter("djdId");
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//自主查询
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, 0);//县获取分页的一些参数，供下面使用
		int totalCount=0;
		int startNum = Paging.pageIndex;
		int pageNum = pageSize;
		List<String[]> list = new ArrayList<String[]>();
		//Map<String,String> metamap = new HashMap<String, String>();
		//metamap.put("sql", "select * from ESS_METADATA where esidentifier ='Title'");
		//List<String[]> metaDatalist = essModelService.getMetaDataList(metamap);	
		//List<String> titlelist = new ArrayList<String>();	
		//for(int ii=0;metaDatalist!=null&&ii<metaDatalist.size();ii++){
		//	titlelist.add(metaDatalist.get(ii)[0]);
		//}
		if(zhywIds!=null&&zhywIds!=""){
			String[] ids = zhywIds.split(",");
			for(int i=0;i<ids.length;i++){
				String structId = ids[i];
				String newStructId="";
				List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(structId));
				if(strucList!=null){
					for(Map<String, Object> map:strucList) {
						String idChild = map.get("IDCHILD").toString();
						String title = map.get("TITLE").toString();
						//获取结构map//判断当前选中的层级是哪个子结构
						if(zhywType!=null&&"2".equals(zhywType)){//案卷
							if("案卷目录".equals(title)){
								newStructId=idChild;
							}
						}else if(zhywType!=null&&"3".equals(zhywType)){//卷内
							if("卷内目录".equals(title)){
								newStructId=idChild;
							}
						}
					}
				}
				if("".equals(newStructId)){//此结构只到卷内目录，而选择的类型是案卷目录，没有表的信息，直接跳过
					continue;
				}
				//确定好表后开始查询数据
				Map<String,String> map = new HashMap<String,String>();
				map.put("idStructure", newStructId);
				//获取检索列
				List<EssTag> tags1 = essModelService.queryTagsForSearch(map);
				//获取排序列
				String orderSql = essModelService.queryTagsForOrder(map);
				//组织查询条件
				String zhconditionsql="";
				if("1".equals(zzcdFlag)){//临时用户数据限制
					String CASUALUSERID = getRequest().getSession().getAttribute("CASUALUSERID").toString();
					Map<String,String> tempmap = new HashMap<String,String>();
					tempmap.put("casualId", CASUALUSERID);
					tempmap.put("dataIdChild", newStructId);
					CasualUserDataRange cudr = roleManageService.getCasualUserDataRange(tempmap);
					if(cudr!=null){
						String rangeSql = cudr.getSqlCondition();
						if("".equals(zhconditionsql)){
							zhconditionsql+=rangeSql;
						}else{
							zhconditionsql+= " and "+rangeSql;
						}
						
					}	
				}
				//查询数据
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("struc", newStructId);
				map1.put("keyWord", keyWord);
				if(searchType!=null&&"2".equals(searchType)){
					map1.put("keyWordOld", keyWordOld);
					getRequest().setAttribute("keyWordOld", keyWordOld);
				}else{
					getRequest().setAttribute("keyWordOld", keyWord);
				}
				map1.put("orderSql", orderSql);
				map1.put("fatherId", structId);
				map1.put("zhconditionsql", zhconditionsql);
				Object cc =getRequest().getSession().getAttribute(keyWordOld+"_"+keyWord+"_"+newStructId);//直接存session
				int count =0;
				if(cc==null){
					count = essModelService.getTaskDetails4ZHYWCount(map1,null,tags1);
					getRequest().getSession().setAttribute(keyWordOld+"_"+keyWord+"_"+newStructId, count);
				}else{
					count=(Integer) cc;
					
				}
				totalCount+=count;//分页的总数据
				if(count!=0&&list.size()==pageSize){//如果集合中数据足够了,则不需要查数据了
					break;
				}
				Integer temp = pageNum+startNum;//需要的数据
				 //一张表数据满足条件。
                if(temp <= count){
    				//获取字段名(配置的展示字段)
    				List<EssTag> tags = essModelService.queryTagsForShowOfzhyw(map);//综合业务检索后展示的列
                	List<String[]> datalist = essModelService.getTaskDetails4ZHYW(map1,tags,tags1, pageNum,startNum);
    				list.addAll(datalist);
                    continue;
                }
                //一张表有部分数据满足条件。
                if(startNum < count){
    				//获取字段名(配置的展示字段)
    				List<EssTag> tags = essModelService.queryTagsForShowOfzhyw(map);//综合业务检索后展示的列
                    List<String[]> datalist = essModelService.getTaskDetails4ZHYW(map1,tags,tags1, pageNum,startNum);
                    list.addAll(datalist);
                    startNum = startNum-count<0 ? 0 : startNum-count;//4
                    pageNum = pageNum - datalist.size();
                    temp = startNum + pageNum;
                    continue;
                }
                //一张表没有数据满足条件。
                if(startNum >= count){
                    startNum = startNum-count;
                    temp = startNum + pageNum;
                    continue;
                }
               
				
			}
		}
		Paging.setPagingParams(getRequest(), pageSize, totalCount);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("zhywIds", zhywIds);
		getRequest().setAttribute("keyWord", keyWord);
		getRequest().setAttribute("searchType", searchType);
		getRequest().setAttribute("zhywType", zhywType);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		return "searchList4Zh";
	}
	
	/**
	 * 全文检索查询页面
	 **/
	public String toModelTask4QW() {
		String treeNodeIds = getRequest().getParameter("treeNodeIds");
		
		String pagesize = getRequest().getParameter("pageSize");
		String keyWord = getRequest().getParameter("keyWord");
		String keyWordOld = getRequest().getParameter("keyWordOld");
		String searchType = getRequest().getParameter("searchType");//首次还二次检索
		String jydId = getRequest().getParameter("jydId");
		String djdId = getRequest().getParameter("djdId");
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//自主查询
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, 0);//县获取分页的一些参数，供下面使用
		int totalCount=0;
		//查询出选中节点的所建的索引path
		getRequest().setAttribute("keyWord", keyWord);
		if(treeNodeIds==null||"".equals(treeNodeIds)){
			Paging.setPagingParams(getRequest(), pageSize, totalCount);
			getRequest().setAttribute("jydId", jydId);
			getRequest().setAttribute("djdId", djdId);
			return "searchList4QW";
		}
		String treeNodes1 = "";
		String treeNodes2 = "";
		String[] idsarr = treeNodeIds.split(",");
		for(int i=0;i<idsarr.length;i++){
			if(i<1000){//超过1000 in 语句失效
				treeNodes1+="'"+idsarr[i]+"',";
			}else{
				treeNodes2+="'"+idsarr[i]+"',";
			}
		}
		if(!"".equals(treeNodes1)){
			treeNodes1 = treeNodes1.substring(0, treeNodes1.length()-1);
		}
		if(!"".equals(treeNodes2)){
			treeNodes2 = treeNodes2.substring(0, treeNodes2.length()-1);
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("treeNodes1", treeNodes1);
		map.put("treeNodes2", treeNodes2);
		List<Object[]> indexlist = fullTextManageService.getFullTextIndexList(map,null,null);
		List<String> indexPathList = new ArrayList<String>();
		for(int j=0;indexlist!=null&&indexlist.size()>0&&j<indexlist.size();j++){
			if(indexlist.get(j)[3]!=null&&!"".equals(indexlist.get(j)[3].toString())){
				indexPathList.add(indexlist.get(j)[3].toString());
			}
		}
		String[] fields = { "title", "bgqx", "zrz", "nd","content"};  
		List<String[]> returnList =null;
		try {
			returnList = FullTextSearchUtil.searchIndex(indexPathList,fields,keyWord,pageSize,Paging.pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Paging.pageIndex==0&&returnList.size()<pageSize){
			totalCount=returnList.size();
		}else if(Paging.pageIndex==0&&returnList.size()==pageSize){
			totalCount=returnList.size()+pageSize;
		}else if(Paging.pageIndex>0&&returnList.size()<pageSize){
			totalCount=Paging.pageIndex+returnList.size();
		}else if(Paging.pageIndex>0&&returnList.size()==pageSize){
			totalCount=Paging.pageIndex+returnList.size()+pageSize;
		}else{
			totalCount=returnList.size();
		}
		Paging.setPagingParams(getRequest(), pageSize, totalCount);
		getRequest().setAttribute("returnList", returnList);
		getRequest().setAttribute("treeNodeIds", treeNodeIds);
		getRequest().setAttribute("searchType", searchType);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		return "searchList4QW";
	}
	
	/**
	 * 档案详情页面
	 **/
	public String showDetail() {
		String lookType = getRequest().getParameter("lookType");//1：检索页查看    2：借阅单查看
		String fatherId = getRequest().getParameter("fatherId");//数据树的最底层
		String structId = getRequest().getParameter("structId");//当前
		String id = getRequest().getParameter("id");
		if("2".equals(lookType)){
			String esPath = getRequest().getParameter("esPath");//文件路径（分解后可得到结构信息）格式：/archive/1995@1/76@4328/758@4329
			if(esPath!=null&&!"".equals(esPath)){
				String[] patharr = esPath.split("/");
				if(patharr.length==4){
					fatherId=patharr[3].split("@")[1];
					structId=patharr[3].split("@")[1];
					id=patharr[3].split("@")[0];
				}else if(patharr.length==5){
					fatherId=patharr[3].split("@")[1];
					structId=patharr[4].split("@")[1];
					id=patharr[4].split("@")[0];
				}else if(patharr.length==6){
					fatherId=patharr[3].split("@")[1];
					structId=patharr[5].split("@")[1];
					id=patharr[5].split("@")[0];
				}
			}
		}
		
		
		String returnId=getRequest().getParameter("returnId");//上一级
		String returnStructId=getRequest().getParameter("returnStructId");
		
		String structType = "";
		Map<String,String> sturctmap = new HashMap<String, String>();
		List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(fatherId));//先根据最底层查询出子结构
		List<String> jjtypelist = new ArrayList<String>();
		if(strucList!=null){
			for(Map<String, Object> map:strucList) {
				String idChild = map.get("IDCHILD").toString();
				String title = map.get("TITLE").toString();
				//获取结构map//判断当前选中的层级是哪个子结构
				if("项目目录".equals(title)){
					if(structId.equals(idChild)){
						structType="1";
					}
					sturctmap.put("1", idChild);
				}else if("案卷目录".equals(title)){
					if(structId.equals(idChild)){
						structType="2";
					}
					sturctmap.put("2", idChild);
				}else if("卷内目录".equals(title)){
					if(structId.equals(idChild)){
						structType="3";
					}
					sturctmap.put("3", idChild);
				}else if("电子文件".equals(title)){
					if(structId.equals(idChild)){
						structType="4";
					}
					sturctmap.put("4", idChild);
				}
			}
		}
		
		//查询主信息
		String tableName = "ESP_"+structId;
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", structId);
		map.put("esdotlength","0");
		//获得字段名(已排序)
		List<EssTag> tags = essModelService.queryTags(map);
		StringBuffer sql = new StringBuffer("select  ID as ID");
		String ordersql = "order by ";
		if(tags!=null&&tags.size()>0) {
			for(int i=0;i<tags.size();i++) {
				Integer zd = tags.get(i).getId();
				String des = tags.get(i).getEsIdentifier();
				sql.append(",C"+zd+" as C"+zd);
				
			}
		}
		sql.append(",espath  as  espath");
		sql.append(" from "+tableName+" where 1=1 and id='"+id+"'");
		map.clear();
		map.put("sql", sql.toString());
		List<Map<String, Object>> list = essModelService.getTaskDetailsById(map,tags);
		if(list!=null&&list.size()>0){
			getRequest().setAttribute("dataMap", list.get(0));
		}
		
		
		
		
		//查询子信息	//查询子信息	//查询子信息	//查询子信息	//查询子信息
		String sonStructId="";
		String sonStructType="";
		ordersql=" order by ";
		if("1".equals(structType)){//项目 
			sonStructId=sturctmap.get("2");
			sonStructType="2";
		}else if("2".equals(structType)){//案卷
			sonStructId=sturctmap.get("3");
			sonStructType="3";
		}else if("3".equals(structType)){//卷内，直接查文件级
			sonStructId=sturctmap.get("4");
			sonStructType="4";
		}
		String tableName2 = "ESP_"+sonStructId;
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("idStructure", sonStructId);
		map2.put("esdotlength","0");
		//获得字段名(已排序)
		List<EssTag> tags2 = essModelService.queryTags(map2);
		StringBuffer sql2 = new StringBuffer("select  ID as ID");
		if(tags2!=null&&tags2.size()>0) {
			for(int i=0;i<tags2.size();i++) {
				Integer zd = tags2.get(i).getId();
				String des = tags2.get(i).getEsIdentifier();
				sql2.append(",C"+zd+" as C"+zd);
				ordersql+=" C"+zd+",";		
			}
		}
		sql2.append(",espath  as  espath");
		sql2.append(" from "+tableName2+" where 1=1 and id_parent_package='"+id+"'");
		ordersql = ordersql.substring(0, ordersql.length()-1);
		sql2.append(ordersql);
		String newsql = "select * from (SELECT rownum as rn, t.* FROM ("+sql2+") t) WHERE 1=1 ";//rn>" + start + " AND rn<=" + end;
		
		map2.clear();
		map2.put("sql", newsql);
		List<Map<String, Object>> list2 = essModelService.getTaskDetailsById(map2,tags2);
		getRequest().setAttribute("sonDataMapList", list2);
		
		getRequest().setAttribute("tags", tags);
		getRequest().setAttribute("tags2", tags2);
		getRequest().setAttribute("id", id);
		getRequest().setAttribute("returnId", returnId);
		getRequest().setAttribute("returnStructId", returnStructId);
		getRequest().setAttribute("fatherId", fatherId);
		getRequest().setAttribute("structId", structId);
		getRequest().setAttribute("sonStructId", sonStructId);
		getRequest().setAttribute("structType", structType);//当前查的数据类型
		getRequest().setAttribute("sonStructType", sonStructType);//子机构类型
		String zzcdFlag = getRequest().getSession().getAttribute("zzcdFlag")!=null?getRequest().getSession().getAttribute("zzcdFlag").toString():"";
		getRequest().setAttribute("zzcdFlag", zzcdFlag);//自主查档有权限控制
		//Paging.setPagingParams(getRequest(), 10, 0);
		return "showDetail";
	}
	
	
	/**
	 * 元数据的查询（万变不离其宗）
	 **/
	public String metaDataList() {
		String pagesize = getRequest().getParameter("pageSize");
		String sql = " select * from ESS_NAMESPACE order by esdate ";
		Map<String,String> map = new HashMap<String, String>();
		map.put("sql", sql);
		List<String[]> nameSpacelist = essModelService.getNameSpaceList(map);		
		getRequest().setAttribute("nameSpacelist", nameSpacelist);
		
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		if(nameSpaceId==null){
			nameSpaceId="";
		}
		map.clear();
		String sql2 = " select * from ESS_METADATA where id_namespace='"+nameSpaceId+"' order by id";
		map.put("sql", sql2);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count =essModelService.getMetaDataCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		String newsql = "select * from (SELECT rownum as rn, t.* FROM ("+sql2+") t) WHERE 1=1  and rn>" + Paging.pageIndex + " AND rn<=" + (Paging.pageIndex+Paging.pageSize);
		map.clear();
		map.put("sql", newsql);
		List<String[]> metaDatalist = essModelService.getMetaDataList(map);		
		getRequest().setAttribute("metaDatalist", metaDatalist);
		
		getRequest().setAttribute("nameSpaceId", nameSpaceId);
		return "metaDataList";
	}
	
	/**
	 * 档案数据加入借阅库
	 **/
	public void addToJYK(){
		
		try {
			String jydId = getRequest().getParameter("jydId");
			String ids = getRequest().getParameter("ids");
			String structId = getRequest().getParameter("structId");	
			String type = getRequest().getParameter("type");//1：关键词检索加入:2：综合业务查询加入 3：全文检索加入
			Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			String zzcdFlag = getRequest().getParameter("zzcdFlag");
			String usingdjr = "";
			if("1".equals(zzcdFlag)){
				usingdjr = "自主查档";
			}else if(emp!=null){
				usingdjr = emp.getEmployeeName();;
			}
			String[] idarr = null;
			List<String> structidlist = new ArrayList<String>();
			if("1".equals(type)){
				idarr=ids.split(",");
			}else if("2".equals(type)){
				String[] arr = ids.split(",");
				idarr = new String[arr.length];
				for(int i=0;i<arr.length;i++){
					idarr[i]=arr[i].split("@")[0];
					structidlist.add(arr[i].split("@")[1]);
				}
			}else if("3".equals(type)){
				String[] arr = ids.split(",");
				idarr = new String[arr.length];
				for(int i=0;i<arr.length;i++){
					idarr[i]=arr[i].split("@")[0];
					structidlist.add(arr[i].split("@")[1]);
				}
			}
			
			
			String cdnr="";//查档内容
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				String id = idarr[i];
				if("2".equals(type)){
					structId=structidlist.get(i);
				}else if("3".equals(type)){
					structId=structidlist.get(i);
				}
				//查询主信息
				String tableName = "ESP_"+structId;
				Map<String,String> map = new HashMap<String,String>();
				map.put("idStructure", structId);
				//map.put("esdotlength","0");
				//获得字段名(已排序)
				List<EssTag> tags = essModelService.queryTags(map);
				StringBuffer sql = new StringBuffer("select  ID as ID");
				if(tags!=null&&tags.size()>0) {
					for(int j=0;j<tags.size();j++) {
						Integer zd = tags.get(j).getId();
						sql.append(",C"+zd+" as C"+zd);
						
					}
					//添加恒定字段
					sql.append(",espath as espath");//用来从借阅单中查看档案详情的，借阅库表中需要配置此Path字段
				}
				sql.append(" from "+tableName+" where 1=1 and id='"+id+"'");
				map.clear();
				map.put("sql", sql.toString());
				List<Map<String, Object>> list = essModelService.getTaskDetailsById(map,tags);
				Map<String, Object> datamap = null;
				if(list!=null){
					datamap=list.get(0);
				}
				
				//获取借阅单字段信息
				map.clear();
				map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
				List<ArchiveNode> JYDnodeList = archiveUsingService.queryNodeList(map);
				//获取借阅单数据
				
				
				
				//获取借阅库字段信息
				map.clear();
				map.put("conditionSql", " and vc_table = 'T_USING_STORE'");
				List<ArchiveNode> JYKnodeList = archiveUsingService.queryNodeList(map);
				
				
				//插入数据
				StringBuffer addsql = new StringBuffer();
				String columns="ID";
				String values="'"+UuidGenerator.generate36UUID()+"'";
				addsql.append(" insert into T_USING_STORE ");
				for(int k=0;k<JYKnodeList.size();k++){
					ArchiveNode an = JYKnodeList.get(k);
					String column = an.getVc_fielName();
					String name = an.getVc_name();
					String medadataid =  an.getVc_metadataid();
					String medadataname =  an.getVc_metadataname();
					String value = "";
					if("shr".equals(medadataname)){//审核人
						if(!"1".equals(zzcdFlag)){//非自助查档，即协助查档时，设置审核人
							value=usingdjr;
							columns+=","+column;
							values+=",'"+value+"'";
						}
					}else if("tjType".equals(medadataname)){//统计类型
						String tjtype="";
						
						Map<String,String> metamap = new HashMap<String,String>();
						metamap.put("sql", "select * from ESS_METADATA where esidentifier ='ArchiveType'");//查询类型
						List<String[]> metaDatalist = essModelService.getMetaDataList(metamap);
						String damlid =  metaDatalist!=null?metaDatalist.get(0)[0]:"";
						for(int p=0;p<tags.size();p++){
							if(damlid.equals(tags.get(p).getIdMetadata()+"")){
								value =datamap.get("C"+tags.get(p).getId())!=null?datamap.get("C"+tags.get(p).getId()).toString():"";
								if("资料".equals(value)){
									tjtype="资料";
									break;
								}
							}							
						}
						if("".equals(tjtype)){//如果不是资料 为空时，再去查是  卷  还是 件
							List<String[]> infolist = essModelService.queryInfo(" select a.estype from ESS_STRUCTURE a where a.id='"+structId+"'",1);
							if(infolist!=null&&infolist.size()>0){
								String estype = infolist.get(0)[0];
								if("innerFile".equals(estype)){//卷内级
									tjtype="件";
								}else if("file".equals(estype)){
									tjtype="卷";
								}
							}
						}
						columns+=","+column;
						values+=",'"+tjtype+"'";
						
					}else{
						if(medadataid!=null&&!"".equals(medadataid)){//第一步插入关联了元数据的字段数据	
							/*//先从借阅单获取数据
							for(int p=0;p<JYDnodeList.size();p++){
								if(medadataid.equals(JYDnodeList.get(p).getVc_metadataid()+"")){
									//value =jyddatamap.get(JYDnodeList.get(p).getId()).toString();
								}
							}*/
							//再从档案获取数据
							for(int p=0;p<tags.size();p++){
								if(medadataid.equals(tags.get(p).getIdMetadata()+"")){
									value =datamap.get("C"+tags.get(p).getId())!=null?datamap.get("C"+tags.get(p).getId()).toString():"";
									if("档号".equals(name)){
										cdnr+=value+",";
									}
								}							
							}				
							columns+=","+column;
							values+=",'"+value+"'";
						}
					}
						
				}
				//绑定关联id
				columns+=",formid";
				values+=",'"+jydId+"'";
				//绑定档案结构路径
				columns+=",path";
				values+=",'"+datamap.get("espath").toString()+"/"+id+"@"+structId+"'";
				addsql.append("("+columns+")").append(" values ").append("("+values+")");
				archiveUsingService.addToJYK(addsql.toString());
				
			}
			
			//更新借阅单
			if(!"".equals(cdnr)){
				cdnr=cdnr.substring(0,cdnr.length()-1);
			}
			String updateSql = "";
			Map<String,String> map = new HashMap<String,String>();
			map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
			List<ArchiveNode> JYDnodeList = archiveUsingService.queryNodeList(map);
			map.put("id", jydId);
			List<Map<String,Object>> formlist = archiveUsingService.queryFormlist(map,JYDnodeList);
			for(int j=0;j<JYDnodeList.size();j++){
				ArchiveNode node = JYDnodeList.get(j);
				String column = node.getVc_fielName();
				String name = node.getVc_name();
				String value="";
				if("查档内容".equals(name)){
					if(formlist.get(0).get(column)!=null&&!"".equals(formlist.get(0).get(column))){
						value=formlist.get(0).get(column)+","+cdnr;		
					}else{
						value=cdnr;
					}
					updateSql+=column+"='"+value+"',";
				}else if("调阅卷(件)数".equals(name)){
					if(formlist.get(0).get(column)!=null&&!"".equals(formlist.get(0).get(column).toString())){
						int avc = 0;
						try {
							avc = Integer.valueOf(formlist.get(0).get(column).toString());
						} catch (Exception e) {
							avc = 0; 
						}
						value= avc + idarr.length+"";
					}else{
						value=idarr.length+"";
					}
					updateSql+=column+"='"+value+"',";
				}
			}
			if(!"".equals(updateSql)){
				updateSql=updateSql.substring(0,updateSql.length()-1);
				String sql = "update T_USING_FORM set "+updateSql+" where id='"+jydId+"'";
				archiveUsingService.updateStore(sql);
				System.out.println(sql);
			}
			outWirter("success",getResponse());//返回前台
		} catch (NumberFormatException e) {
			e.printStackTrace();
			outWirter("false",getResponse());//返回前台
		}	
	}
	
	
	/**
	 * 档案数据加入调卷库---检查
	 **/
	public void checkDJK(){
		try {
			String djdId = getRequest().getParameter("djdId");
			String ids = getRequest().getParameter("ids");
			String structId = getRequest().getParameter("structId");	
			String type = getRequest().getParameter("type");//1：关键词检索加入:2：综合业务查询加入
			String[] idarr = null;
			List<String> structidlist = new ArrayList<String>();
			Map<String,String> map = new HashMap<String,String>();
			if("1".equals(type)){
				idarr=ids.split(",");
			}else if("2".equals(type)){
				String[] arr = ids.split(",");
				idarr = new String[arr.length];
				for(int i=0;i<arr.length;i++){
					idarr[i]=arr[i].split("@")[0];
					structidlist.add(arr[i].split("@")[1]);
				}
			}
			String titles = "";
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				String id = idarr[i];
				if("2".equals(type)){
					structId=structidlist.get(i);
				}
				map.clear();
				map.put("djdId", djdId);//调卷单id
				map.put("glid", id);//调卷库关联档案数据的id
				map.put("structId", structId);//调卷库关联档案数据的结构id
				List<Object[]> checklist = archiveUsingService.getChecDJKList(map);
				if(checklist!=null&&checklist.size()>0){
					titles+=checklist.get(0)[0].toString()+";";
				}
			}
			
			if(!"".equals(titles)){
				titles = titles.substring(0, titles.length()-1);
			}
			outWirter(titles,getResponse());//返回前台
		} catch (NumberFormatException e) {
			e.printStackTrace();
			outWirter("false",getResponse());//返回前台
		}	
	}
	
	/**
	 * 档案数据加入调卷库
	 **/
	public void addToDJK(){
		
		try {
			String djdId = getRequest().getParameter("djdId");
			String ids = getRequest().getParameter("ids");
			String structId = getRequest().getParameter("structId");	
			String type = getRequest().getParameter("type");//1：关键词检索加入:2：综合业务查询加入
			String[] idarr = null;
			List<String> structidlist = new ArrayList<String>();
			if("1".equals(type)){
				idarr=ids.split(",");
			}else if("2".equals(type)){
				String[] arr = ids.split(",");
				idarr = new String[arr.length];
				for(int i=0;i<arr.length;i++){
					idarr[i]=arr[i].split("@")[0];
					structidlist.add(arr[i].split("@")[1]);
				}
			}
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				String id = idarr[i];
				if("2".equals(type)){
					structId=structidlist.get(i);
				}
				String tableName = "ESP_"+structId;
				
				//查询主信息(固定)
				String sql2 = " select id,espath,id_parent_structure,id_parent_package,tree_nodeid from "+tableName+" where id="+id;
				List<String[]> datalist = essModelService.queryInfo(sql2,5);
				Transferstore ts = new Transferstore();
				ts.setGlid(datalist.get(0)[0]);
				//ts.setEsPath(datalist.get(0)[1]);
				ts.setId_parent_structure(datalist.get(0)[2]);
				ts.setId_parent_package(datalist.get(0)[3]);
				ts.setTree_nodeid(datalist.get(0)[4]);
				ts.setFormId(djdId);
				ts.setStructId(structId);
				
				//查询主信息(配置)
				Map<String,String> map = new HashMap<String,String>();
				map.put("idStructure", structId);
				map.put("esdotlength","0");
				//获得字段名(已排序)
				List<EssTag> tags = essModelService.queryTags(map);
				StringBuffer sql = new StringBuffer("select  ID as ID");
				if(tags!=null&&tags.size()>0) {
					for(int j=0;j<tags.size();j++) {
						Integer zd = tags.get(j).getId();
						sql.append(",C"+zd+" as C"+zd);	
					}
					//添加恒定字段
					sql.append(",espath as espath");//用来从调卷单中查看档案详情的，调卷库表中需要配置此Path字段
				}
				sql.append(" from "+tableName+" where 1=1 and id='"+id+"'");
				map.clear();
				map.put("sql", sql.toString());
				List<Map<String, Object>> list = essModelService.getTaskDetailsById(map,tags);
				Map<String, Object> datamap = null;
				if(list!=null){
					datamap=list.get(0);
				}
				
				Map<String,String> metamap = new HashMap<String,String>();
				metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('Title') and id_namespace=3");//查询
				List<String[]> metaDatalist1 = essModelService.getMetaDataList(metamap);
				metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('ManName') and id_namespace=3");//查询
				List<String[]> metaDatalist2 = essModelService.getMetaDataList(metamap);
				metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('WomanName') and id_namespace=3");//查询
				List<String[]> metaDatalist3 = essModelService.getMetaDataList(metamap);
				String metadata_title="";
				if(metaDatalist1!=null&&metaDatalist1.size()>0){
					metadata_title=metaDatalist1.get(0)[0];
				}
				String metadata_man="";
				if(metaDatalist2!=null&&metaDatalist2.size()>0){
					metadata_man=metaDatalist2.get(0)[0];
				}
				String metadata_woman="";
				if(metaDatalist3!=null&&metaDatalist3.size()>0){
					metadata_woman=metaDatalist3.get(0)[0];
				}
				String title="";
				String man="";
				String woman="";
				for(int p=0;p<tags.size();p++){
					if(metadata_title.equals(tags.get(p).getIdMetadata()+"")){
						title =datamap.get("C"+tags.get(p).getId())!=null?datamap.get("C"+tags.get(p).getId()).toString():"";
					}	
					if(metadata_man.equals(tags.get(p).getIdMetadata()+"")){
						man =datamap.get("C"+tags.get(p).getId())!=null?datamap.get("C"+tags.get(p).getId()).toString():"";
					}
					if(metadata_woman.equals(tags.get(p).getIdMetadata()+"")){
						woman =datamap.get("C"+tags.get(p).getId())!=null?datamap.get("C"+tags.get(p).getId()).toString():"";
					}
				}
				if(!"".equals(title)){
					ts.setTitle(title);
				}else{
					ts.setTitle(man+"--"+woman);
				}
				//绑定档案结构路径
				ts.setEsPath(datamap.get("espath").toString()+"/"+id+"@"+structId);
				archiveUsingService.addToDJK(ts);
			}
			
			outWirter("success",getResponse());//返回前台
		} catch (NumberFormatException e) {
			e.printStackTrace();
			outWirter("false",getResponse());//返回前台
		}	
	}
	
	//获取档案原文路径信息
	public void getFilePath(){
		String id = getRequest().getParameter("id");
		String structId = getRequest().getParameter("structId");
		
		//查询主信息
		String tableName = "ESP_"+structId;
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", structId);
		//获得字段名(已排序)
		List<EssTag> tags = essModelService.queryTags(map);
		StringBuffer sql = new StringBuffer("select  ID as ID");
		if(tags!=null&&tags.size()>0) {
			for(int j=0;j<tags.size();j++) {
				Integer zd = tags.get(j).getId();
				sql.append(",C"+zd+" as C"+zd);
				
			}
			//添加恒定字段
			sql.append(",espath as espath");//用来从借阅单中查看档案详情的，借阅库表中需要配置此Path字段
		}
		sql.append(" from "+tableName+" where 1=1 and id='"+id+"'");
		map.clear();
		map.put("sql", sql.toString());
		List<Map<String, Object>> list = essModelService.getTaskDetailsById(map,tags);
		Map<String, Object> datamap = null;
		if(list!=null){
			datamap=list.get(0);
			String espath = datamap.get("espath").toString();
			String[] value= espath.split("/");
			String parentstructid = value[value.length-1].split("@")[1];
			String ftpsql = " select filepath from ESS_RULE_SCANPOLICY where id_structure="+parentstructid;
			int returnColumn=1;//需要查询的列数
			List<String[]> ftpinfo = essModelService.queryInfo(ftpsql,returnColumn);
			if(ftpinfo!=null&&ftpinfo.size()>0){
				String ftpparam = ftpinfo.get(0)[0];//例如  FTP://ftpServer/data
				String protocol = ftpparam.split(":")[0];
				String serveralias = ftpparam.split("//")[1].split("/")[0];
				String path = ftpparam.split("//")[1].split("/")[1];
				String serverSql = " select server from  ESSFILESERVER where protocol='"+protocol+"' and serveralias='"+serveralias+"' and path='"+path+"'";
				List<String[]> ftpServerinfo = essModelService.queryInfo(serverSql,1);
				if(ftpServerinfo!=null&&ftpServerinfo.size()>0){
					String server = ftpServerinfo.get(0)[0];
					for(int j=0;j<tags.size();j++){
						if("RESOURCE".equals(tags.get(j).getEsType())){//文件路径
							String ywlj = datamap.get("C"+tags.get(j).getId()).toString();//文件级 数据的原文路径
							ywlj = ywlj.replace(ftpparam, "http://"+server+"/"+path);
							outWirter(ywlj,getResponse());//返回前台
							break;
						}
					}
				}
			}
			
			
		}
	}
	
	/**
	 * 下载原文
	 **/
	public void downloadFile(){
		String id = getRequest().getParameter("id");
		String structId = getRequest().getParameter("structId");
		
		//查询主信息
		String tableName = "ESP_"+structId;
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", structId);
		//获得字段名(已排序)
		List<EssTag> tags = essModelService.queryTags(map);
		StringBuffer sql = new StringBuffer("select  ID as ID");
		if(tags!=null&&tags.size()>0) {
			for(int j=0;j<tags.size();j++) {
				Integer zd = tags.get(j).getId();
				sql.append(",C"+zd+" as C"+zd);
				
			}
			//添加恒定字段
			sql.append(",espath as espath");//用来从借阅单中查看档案详情的，借阅库表中需要配置此Path字段
		}
		sql.append(" from "+tableName+" where 1=1 and id='"+id+"'");
		map.clear();
		map.put("sql", sql.toString());
		List<Map<String, Object>> list = essModelService.getTaskDetailsById(map,tags);
		Map<String, Object> datamap = null;
		if(list!=null){
			datamap=list.get(0);
			String espath = datamap.get("espath").toString();
			String[] value= espath.split("/");
			String parentstructid = value[value.length-1].split("@")[1];
			String ftpsql = " select filepath from ESS_RULE_SCANPOLICY where id_structure="+parentstructid;
			int returnColumn=1;//需要查询的列数
			List<String[]> ftpinfo = essModelService.queryInfo(ftpsql,returnColumn);
			if(ftpinfo!=null&&ftpinfo.size()>0){
				String ftpparam = ftpinfo.get(0)[0];//例如  FTP://ftpServer/data
				String protocol = ftpparam.split(":")[0];
				String serveralias = ftpparam.split("//")[1].split("/")[0];
				String path = ftpparam.split("//")[1].split("/")[1];
				String serverSql = " select server,fileserverport,username,psw,path from  ESSFILESERVER where protocol='"+protocol+"' and serveralias='"+serveralias+"' and path='"+path+"'";
				List<String[]> ftpServerinfo = essModelService.queryInfo(serverSql,5);
				if(ftpServerinfo!=null&&ftpServerinfo.size()>0){
					String server = ftpServerinfo.get(0)[0];
					String fileserverport = ftpServerinfo.get(0)[1];
					String username = ftpServerinfo.get(0)[2];
					String psw = ftpServerinfo.get(0)[3];
					for(int j=0;j<tags.size();j++){
						if("RESOURCE".equals(tags.get(j).getEsType())){//文件路径
							String ywlj = datamap.get("C"+tags.get(j).getId()).toString();//文件级 数据的原文路径  如：FTP://ftpServerNew/data/D017/D017-2001-001-0001_2273.pdf
							String fileName = ywlj.split("/")[ywlj.split("/").length-1];
							String str = ywlj.split(serveralias)[1];//    /data/D017/D017-2001-001-0001_2273.pdf
							String wjpath = str.split(fileName)[0];
							getResponse().setContentType("application/x-msdownload");
							getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
							FtpUtil.downFile(server, Integer.valueOf(fileserverport), username, psw, wjpath, fileName, getResponse());
							break;
						}
					}
				}
			}
		}	
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
	
	/**
	 * 跳转到申请中心
	 * @return
	 */
	public String toApplyCenter() {
		String applyFlag = getRequest().getParameter("applyFlag");
		getRequest().setAttribute("applyFlag", applyFlag);
		return "applyCenterJsp";
	}
	
	//
	public void getMsg(){
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		Map<String,String> map = new HashMap<String, String>();
		map.put("status", "0");
		map.put("recevier", emp.getEmployeeName());
		List<ArchiveMsg> msglist = archiveUsingService.getArchiveMsgList(map,null,null);
		JSONArray jsonArray = JSONArray.fromObject(msglist);
		String json = jsonArray.toString();
		outWirter(json,getResponse());
	}
	
	/**
	 * 获取申请中心待办数的角标
	 */
	public void getdbCount() {
		JSONObject obj = new JSONObject();
		Map<String,String> map = new HashMap<String,String>();
		String conditionSql = "";
		conditionSql +=" and vc_table = 'T_USING_FORM'";
		map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		map.clear();
		String sql="";
		for(int i=0;i<nodeList.size();i++){
			if("status".equals(nodeList.get(i).getVc_metadataname())) {
				sql += nodeList.get(i).getVc_fielName()+"='1'";
			}
		}
		map.put("sql", " and "+sql);
		int jycount = archiveUsingService.getValCount(null,map);//借阅待审核数
		
		map.clear();
		map.put("statusSe", "1");
		int djcount = archiveUsingService.getTransferformCount(map);
		obj.put("dbCount", jycount+djcount+"");
		toPage(obj.toString());
	}
}
