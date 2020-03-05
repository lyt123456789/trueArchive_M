package cn.com.trueway.archives.using.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.CasualUserDataRange;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.archives.using.service.DataUsingService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.pojo.RoleUser;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class DataUsingAction extends BaseAction{

	private static final long serialVersionUID = 7408372112834612880L;
   
	private DataUsingService dataUsingService;
	private DataTempService dataTempService;
	private RoleManageService roleManageService;

	public DataTempService getDataTempService() {
		return dataTempService;
	}

	public void setDataTempService(DataTempService dataTempService) {
		this.dataTempService = dataTempService;
	}

	public DataUsingService getDataUsingService() {
		return dataUsingService;
	}

	public void setDataUsingService(DataUsingService dataUsingService) {
		this.dataUsingService = dataUsingService;
	}

	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
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
    	String treeId =  getRequest().getParameter("treeId");
    	getRequest().setAttribute("treeId", treeId);
    	
    	String businessId = getRequest().getParameter("businessId");//业务类型
		String currId = getRequest().getParameter("currId");//当前选中节点id
		map.put("business", businessId);
    	
    	StringBuffer json = new StringBuffer();
    		//获取目录树授权节点
    	    String roleId = "";
    	    String treeNodes="";
    	    List<RoleUser> roleUserList = (List<RoleUser>) getRequest().getSession().getAttribute(MyConstants.roleUser);
    		for(int i=0;i<roleUserList.size();i++){
    			roleId = roleUserList.get(i).getRoleId();
    			map.put("roleId", roleId);
    			List<TreeNodeOfRole> list = roleManageService.getTreeNodesOfRole(map);
    		    if(list!=null&&list.size()>0){
    				treeNodes+=list.get(0).getTreeNodes()+",";
    			}
    		}
    		map.put("conditionSql", " and (id_parent_node=1 or id=1) ");//查询出根节点以及下一级子节点
    		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
    		json.append("[");
    		for (EssTree e : data) {
    			if(treeNodes!=""&&(treeNodes+",").indexOf(e.getId()+",")>-1){
    				Integer idStructure = e.getIdStructure();
    				json.append("{");
    				json.append("\"id\":\"").append(e.getId()).append("\",");
    				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
    				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
    				json.append("\"checkArr\":\"").append("0\",");
    				/*if(!"1".equals(e.getIsLeaf()+"")){
    					json.append("\"isLast\":").append("false,");
    				}*/
    				if((e.getId().toString()).equals(currId)){
    					json.append("\"spread\":").append("true,");
    				}
    				if(idStructure!=null && idStructure != 0) {	
    					json.append("\"last\":").append("false,");
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
    	getRequest().setAttribute("dataJson", json.toString());
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
        return "modelMenu4Key";
    }
	
	/**
	 * 查询子节点json串
	 * */
	public void getSonTreeJson() throws IOException{
		String id = getRequest().getParameter("nodeId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("parentId", id);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		json.append("{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": [");
		if(data!=null&&data.size()>0){//有树节点	
			for (EssTree e : data) {
				Integer idStructure = e.getIdStructure();
				json.append("{");
				json.append("\"id\":\"").append(e.getId()).append("\",");
				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				if(!"1".equals(e.getIsLeaf()+"")){
					json.append("\"last\":").append("false,");
				}else{
					if(idStructure!=null && idStructure != 0) {	
						json.append("\"last\":").append("false,");
					}
				}
				json.append("\"basicData\":").append(idStructure).append("");	
				json.append("},");
			}
		}else{//无节点时，去查询下面的结构（案卷 卷内）
			map.clear();
			map.put("id", id);
			
			EssTree et = dataTempService.getTreeByMap(map,null,null).get(0);
			Integer idStructure = et.getIdStructure();
			//查询子结构
			map.clear();
			map.put("structureId", idStructure+"");
			map.put("treeId", et.getId()+"");//哪个树下面的结构
			map.put("type", "dg");//递归查询
			List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
			if(stList!=null){
				for(EssStructure es:stList) {
					if("document".equals(es.getEsType())){//电子文件级目录过滤掉
						continue;
					}
					String title ="";
					if("project".equals(es.getEsType())){
						title="项目目录";
					}else if("file".equals(es.getEsType())){
						title="案卷目录";
					}else if("innerFile".equals(es.getEsType())){
						title="卷内目录";
					}
					json.append("{");
					json.append("\"id\":\"").append(et.getId()+"-"+es.getId()).append("\",");
					json.append("\"title\":\"").append(title).append("\",");
					json.append("\"parentId\":\"").append(et.getId()).append("\",");
					//查询自定义分类
					
					json.append("\"basicData\":").append(es.getId()).append("");
					json.append("},");
				}
			}
			
		}
		
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]}");
		getResponse().setContentType("text/plain;UTF-8");
		this.outWirter(json.toString());
	}
	
	/**
	 * 关键词查询页面
	 **/
	public String toModelTask4Key() {
		String openFlag = getRequest().getParameter("openFlag");//从左侧树打开     默认展示卷内目录    的标志
		String searchType = getRequest().getParameter("searchType");//zh：页面搜索，然后综合搜索
		String treeId = getRequest().getParameter("treeId");//节点数的id
		String struc = getRequest().getParameter("structId");//当前选中结构
		String fatherId = getRequest().getParameter("fatherId");//顶层结构
		String codeId = getRequest().getParameter("codeId");
		String pagesize = getRequest().getParameter("pageSize");
		String keyWord = getRequest().getParameter("keyWord");
		String row = getRequest().getParameter("row");
		String jydId = getRequest().getParameter("jydId");
		String djdId = getRequest().getParameter("djdId");//判断是否从调卷单过来的	
		String zzcdFlag = getRequest().getParameter("zzcdFlag");//自主查询
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		getRequest().setAttribute("treeId", treeId);
		String zhconditionsql="";//综合查询的sql
		getRequest().setAttribute("codeId", codeId);
		
		/*List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(fatherId),treeId);
		List<String> jjtypelist = new ArrayList<String>();
		if(strucList!=null){
			for(Map<String, Object> map:strucList) {
				String idChild = map.get("IDCHILD").toString();
				String title = map.get("TITLE").toString();	
				if("1".equals(openFlag)){
					if("卷内目录".equals(title)){
						struc = idChild+"";
					}
				}
				if(!"电子文件".equals(title)){
					jjtypelist.add(idChild+","+title);
				}
			}
		}
		getRequest().setAttribute("jjtypelist", jjtypelist);*/
		
		//综合查询组织
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
		
		//获取字段名(配置的展示字段)
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", struc);
		map.put("tableFlag","grid");
		List<EssTag> etList1 = dataTempService.getEssTagList(map,null,null);
		List<EssTag> etList = new ArrayList<EssTag>();
		List<EssZDXZCommon> gridList = this.dataTempService.getZDXZDataList(map);
		for(int i = 0; i < gridList.size()&&gridList!=null;i++){
			int tagId = gridList.get(i).getIdtag();
			for(int j = 0; j < etList1.size()&&etList1!=null;j++){
				int id = etList1.get(j).getId();
				if(id == tagId){
					etList.add(etList1.get(j));
					//break;
				}
			}
		}
		String tagstr = "";
		for(int j=0;j<etList.size();j++){
			tagstr+=etList.get(j).getId()+","+etList.get(j).getEsIdentifier()+";";
		}
		getRequest().setAttribute("tagstr", tagstr);
		//获取检索列
		map.put("tableFlag","usingGrid");
		List<EssTag> ugList1 = dataTempService.getEssTagList(map,null,null);
		List<EssTag> ugList = new ArrayList<EssTag>();
		List<EssZDXZCommon> usingGridList = this.dataTempService.getZDXZDataList(map);
		for(int i = 0; i < usingGridList.size()&&usingGridList!=null;i++){
			int tagId = usingGridList.get(i).getIdtag();
			for(int j = 0; j < ugList1.size()&&ugList1!=null;j++){
				int id = ugList1.get(j).getId();
				if(id == tagId){
					ugList.add(ugList1.get(j));
					//break;
				}
			}
		}
		//获取排序列
//		String orderSql = essModelService.queryTagsForOrder(map);
		List<EssZDXZCommon> abstructList = dataTempService.getPXDataList(map);
		String orderSql = abstructList.get(0).getRule();
				
		//组织查询条件(数据权限)
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
		int count =essModelService.getTaskDetailsCount(map1,etList,ugList);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Map<String, Object>> list = essModelService.getTaskDetails(map1,etList,ugList, Paging.pageSize,Paging.pageIndex);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("tags", etList);
		getRequest().setAttribute("structId", struc);
		getRequest().setAttribute("fatherId", fatherId);
		getRequest().setAttribute("keyWord", keyWord);
		getRequest().setAttribute("searchType", searchType);
		getRequest().setAttribute("jydId", jydId);
		getRequest().setAttribute("djdId", djdId);
		return "searchList4Key";
	}
	
}
