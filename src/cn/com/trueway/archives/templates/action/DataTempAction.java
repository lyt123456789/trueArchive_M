package cn.com.trueway.archives.templates.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssModelTags;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssStructureModel;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.pojo.MrzPojo;
import cn.com.trueway.archives.templates.service.BusinessManageService;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.archives.templates.service.MetaDataService;
import cn.com.trueway.archives.templates.service.StructureService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.util.UuidGenerator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class DataTempAction extends BaseAction {
	
	private static final long serialVersionUID = -9043175135581417337L;
	private DataTempService dataTempService;
	private StructureService structureService;
	private MetaDataService metaDataService;
	
	
	public StructureService getStructureService() {
		return structureService;
	}


	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}


	public DataTempService getDataTempService() {
		return dataTempService;
	}


	public void setDataTempService(DataTempService dataTempService) {
		this.dataTempService = dataTempService;
	}
	
	public MetaDataService getMetaDataService() {
		return metaDataService;
	}

	public void setMetaDataService(MetaDataService metaDataService) {
		this.metaDataService = metaDataService;
	}
	//获取业务类型数据
	private BusinessManageService businessManageService;
	public BusinessManageService getBusinessManageService() {
		return businessManageService;
	}
	public void setBusinessManageService(BusinessManageService businessManageService) {
		this.businessManageService = businessManageService;
	}

	
	/**
	 * 转向模板定义页面
	 * */
	public String toDataTempJsp(){
		String business = getRequest().getParameter("business");//业务类型
		String currId = getRequest().getParameter("currId");//当前选中节点id
		Map<String,Object> map1 = new HashMap<String,Object>();
		//获取到业务类型数据
		List<EssBusiness> businessList = businessManageService.getBusinessManageList(map1);
		getRequest().setAttribute("businessList", businessList);
		//获得目录树
		Map<String,String> map = new HashMap<String, String>();
		map.put("business", business);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
				Integer idStructure = e.getIdStructure();
				json.append("{");
				json.append("\"id\":\"").append(e.getId()).append("\",");
				json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
				json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				if((e.getId().toString()).equals(currId)){
					json.append("\"spread\":").append("true,");
				}
				json.append("\"basicData\":").append(idStructure).append("");
				json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("treeJson", json);
		getRequest().setAttribute("business", business);
		return "toDataTempJsp";
	}
	
	/**
	 * 转向节点新增编辑页面
	 * */
	public String toEditeSJSTPage(){
		String parentId = getRequest().getParameter("parentId");
		String treeId = getRequest().getParameter("treeId");
		String editFlag = getRequest().getParameter("editFlag");// add  update
		String fromFlag = getRequest().getParameter("fromFlag");//判断从哪里编辑节点   // 1:tree  2:list
		Map<String,String> map = new HashMap<String, String>();
		if("update".equals(editFlag)){
			map.put("id", treeId);
			List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
			parentId = data.get(0).getIdParent()+"";
			getRequest().setAttribute("title", data.get(0).getTitle());
			getRequest().setAttribute("order", data.get(0).getEsOrder());
		}
		getRequest().setAttribute("treeId",treeId);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("editFlag", editFlag);
		getRequest().setAttribute("fromFlag", fromFlag);
		return "toShowSJSTPage_edit";
	}
	
	/**
	 * 新增树节点
	 * */
	public void addTreeNode(){
		String parentId = getRequest().getParameter("parentId");
		String title = getRequest().getParameter("title");
		String order = getRequest().getParameter("order");
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", parentId);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		if(data!=null&&data.size()>0||parentId==null||"".equals(parentId)|"null".equals(parentId)){
			EssTree Fet = data.get(0);//获取父节点信息
			Fet.setIsLeaf(0);//新增子节点父节点置为0-文件夹
			dataTempService.updateEssTree(Fet);	
			map.clear();
			map.put("parentId", parentId);
			List<EssTree> sondata = dataTempService.getTreeByMap(map,null,null);
			int sort = 1;//获取排序号
			if(sondata.size()!=0){
				sort = sondata.get(sondata.size()-1).getEsOrder()+1;
			}
			map.clear();
			//map.put("id", Fet.getIdBusiness());
			//List<EssBusiness> list = this.businessManageService.getBusinessManageList(map);

			EssTree et = new EssTree();
			if(order!=null&&!"".equals(order)){
				try {
					et.setEsOrder(Integer.valueOf(order));
				} catch (NumberFormatException e) {
					et.setEsOrder(sort);
					e.printStackTrace();
				}
			}else{
				et.setEsOrder(sort);
			}	
			et.setEsPath("/"+Fet.getEsPath().split("/")[1]);//先存业务路径，取得id后再存id
			et.setIdBusiness(Fet.getIdBusiness());
			et.setIdModelStructure(0);
			et.setIdParent(Integer.valueOf(parentId));
			et.setIdSeq(Fet.getIdSeq()+"."+Fet.getId());
			et.setIdStructure(0);
			et.setIsLeaf(1);
			et.setTitle(title);
			dataTempService.updateEssTree(et);	
			et.setEsPath("/"+Fet.getEsPath().split("/")[1]+"/"+et.getId()+"@1");
			dataTempService.updateEssTree(et);	
			outWirter("success");
		}else{
			outWirter("false");
		}
	}
	/**
	 * 修改树节点
	 * */
	public void updateTreeNode(){
		String id = getRequest().getParameter("id");
		String title = getRequest().getParameter("title");
		String order = getRequest().getParameter("order");
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		if(data!=null&&data.size()>0||id==null||"".equals(id)|"null".equals(id)){
			EssTree et = data.get(0);
			if(order!=null&&!"".equals(order.trim())){
				try {
					et.setEsOrder(Integer.valueOf(order));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			if(title!=null&&!"".equals(title.trim())){
				et.setTitle(title);
			}
			dataTempService.updateEssTree(et);	
			outWirter("success");
		}else{
			outWirter("false");
		}
	}
	
	/**
	 * 删除树节点时，校验节点下是否是有数据
	 * */
	public void checkDeleteNode(){
		String ids = getRequest().getParameter("ids");
		String[] idsarr = ids.split(",");
		String returnstr="";
		for(int i=0;i<idsarr.length;i++){
			returnstr+="节点下已有数据，是否删除？";
		}
		outWirter(returnstr);
	}
	
	/**
	 * 删除树节点
	 * */
	public void deleteTreeNode(){
		String ids = getRequest().getParameter("ids");
		String[] idsarr = ids.split(",");
		String conditionSql = "";
		for(int i=0;i<idsarr.length;i++){
			/*if(i==idsarr.length-1){
				conditionSql+="'"+idsarr[i]+"'";
			}else{
				conditionSql+="'"+idsarr[i]+"',";
			}*/
			//1.删除子节点所有数据
			Map<String,String> map = new HashMap<String, String>();
			map.put("treeId", idsarr[i]);//哪个树下面的结构
			map.put("type", "dg");//递归查询
			List<EssTree> treeNodeList = dataTempService.deleteTreeNodeList(map,null,null);
			if(treeNodeList!=null&&treeNodeList.size()>0){
				for(int j=0;j<treeNodeList.size();j++){
					int treeNodeId = treeNodeList.get(j).getId();
					//2.删除所选节点
					dataTempService.updateNativeSql(" delete from T_ARCHIVE_TREE where ID="+treeNodeId+"");
					outWirter("success");
					int idParentNode = treeNodeList.get(j).getIdParent();
					Map<String,String> map1 = new HashMap<String, String>();
					map1.put("parentId", idParentNode+"");
					//获取到所有父节点下的集合
					List<EssTree> sondata = dataTempService.getTreeByMap(map1,null,null);
					//3.判断所选节点的父节点是否为叶子节点
					if(sondata == null||sondata.size()==0){
						map1.clear();
						map1.put("id", idParentNode+"");
						//获取到当前子节点的父节点
						List<EssTree> data = dataTempService.getTreeByMap(map1,null,null);
						EssTree Fet = data.get(0);//获取父节点信息
						Fet.setIsLeaf(1);//新增子节点父节点置为0-文件夹
						dataTempService.updateEssTree(Fet);	
						map1.clear();
					}
				}
			}
			
			
		}
		/*if(!"".equals(conditionSql)){
			//删除相关表
			//1.
			//2.
			//3.
			dataTempService.updateNativeSql(" delete from T_ARCHIVE_TREE where ID in ("+conditionSql+")");
			outWirter("success");
		}else{
			outWirter("false");
		}*/
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
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"checkArr\":\"").append("0\",");
			if(!"1".equals(e.getIsLeaf()+"")){
				json.append("\"last\":").append("false,");
			}
			json.append("\"basicData\":").append(idStructure).append("");	
			json.append("},");
			
			//子结构
			/*if(idStructure!=null && idStructure != 0) {
				List<Map<String, Object>> strucList = essModelDao.getChildStructure(idStructure);
				if(strucList!=null){
					for(Map<String, Object> map:strucList) {
						String idChild = map.get("IDCHILD").toString();
						String title = map.get("TITLE").toString();
						String parentId = map.get("PID").toString();
						json.append("{");
						json.append("\"id\":\"").append(parentId+"-"+idChild).append("\",");
						json.append("\"title\":\"").append(title).append("\",");
						json.append("\"parentId\":\"").append(parentId).append("\",");
						json.append("\"basicData\":").append(idChild).append("");
						json.append("},");
					}
				}
			
			}*/
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
	 * 查询节点关联的子结构的json串供树展示
	 * */
	public void getStructureJsonOfTree() throws IOException{	
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("treeId", treeId);//哪个树下面的结构
		map.put("type", "dg");//递归查询
		List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
		StringBuilder json = new StringBuilder();
		json.append("[");
		try {
			for (int i=0;i<stList.size();i++) {
				json.append("{");
				json.append("\"id\":\"").append(stList.get(i).getId()).append("\",");
				json.append("\"title\":\"").append(stList.get(i).getEsTitle()).append("\",");
				if(i==0){
					json.append("\"parentId\":\"").append("-1").append("\",");
				}else{
					json.append("\"parentId\":\"").append(stList.get(i-1).getId()).append("\",");
				}	
				json.append("\"basicData\":").append(treeId).append("");	
				json.append("},");
			}
			int index = json.lastIndexOf(",");
			if(index>-1){
				json.deleteCharAt(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.append("]");
		getResponse().setContentType("text/plain;UTF-8");
		this.outWirter(json.toString());
	}
	
	/**
	 * 转向模板规则、属性、节点定义页面
	 * */
	public String toTempEditMainJsp(){
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("structureId", structureId);
		return "toTempEditMainJsp";
	}
	
	/**
	 * 转向树节点的 --数据视图-- 页面
	 * */
	public String toShowSJSTPage(){
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");
		String pagesize = getRequest().getParameter("pagesize");
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String, String>();
		map.put("parentId", treeId);
		int count = dataTempService.TreeCountByMap(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<EssTree> treeList = dataTempService.getTreeByMap(map,Paging.pageSize,Paging.pageIndex);
		getRequest().setAttribute("treeList", treeList);
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("structureId", structureId);
		return "toShowSJSTPage";
	}
	

	/**
	 * 转向树节点的 --结构视图-- 主页面
	 * */
	public String toShowJGSTPage(){
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
		getRequest().setAttribute("structure", stList.get(0));
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("structureId", structureId);
		return "toShowJGSTPage";
	}
	
	/**
	 * 转向树节点的 --结构视图-- 字段编辑页面
	 * */
	public String toShowJGSTPage_field(){
		String structureId = getRequest().getParameter("structureId");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		int count = dataTempService.getEssTagCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<EssTag> etList = dataTempService.getEssTagList(map,Paging.pageSize,Paging.pageIndex);
		getRequest().setAttribute("list", etList);
		getRequest().setAttribute("structureId", structureId);
		return "toShowJGSTPage_field";
	}
	
	/**
	 * 跳转到模板定义-结构模板-引用元数据页面
	 * @return
	 */
	public String toDampMatchMetaDataPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> mapS = new HashMap<String, String>();
		mapS.put("structureId", structureId);
		List<EssTag> etList = dataTempService.getEssTagList(mapS,null,null);
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,String>> nameSpaceList = this.metaDataService.getNameSpaceList(map);
		getRequest().setAttribute("list", etList);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("nameSpaceList", nameSpaceList);
		return "dampMatchMetaDataJsp";
	}
	
	/**
	 * 保存匹配的元数据
	 */
	public void saveMetaData() {
		JSONObject obj = new JSONObject();
		String jsonArrStr = getRequest().getParameter("jsonArr");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.dataTempService.saveTagsMetaData(list);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 自动对应元数据
	 */
	public void autoMetaDataOf() {
		String structureId = getRequest().getParameter("structureId");
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		String spaceName = getRequest().getParameter("spaceName");
		Map<String,String> mapS = new HashMap<String, String>();
		mapS.put("structureId", structureId);
		List<EssTag> etList = dataTempService.getEssTagList(mapS,null,null);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("nameSpaceId", nameSpaceId);
		//元数据list
		List<EssMetaData> listA = this.metaDataService.getMetaDataList(map);
		List<EssTag> returnList = new ArrayList<EssTag>();
		for(int i = 0; i < etList.size(); i++) {
			EssTag est = etList.get(i);
			String tagName = est.getEsIdentifier();
			for(int t = 0; t < listA.size(); t++) {
				EssMetaData esta = listA.get(t);
				String esTitle = esta.getEsTitle();
				if(tagName.equals(esTitle)) {
					String metaDataFullName = spaceName + ":" + esta.getEsIdentifier();
					est.setNameMetadata(metaDataFullName);
					est.setIdMetadata(esta.getId());
					returnList.add(est);
					continue;
				}
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("retList", returnList);
		toPage(obj.toString());
	}
	
	/**
	 * 验证元数据的唯一性
	 */
	public void checkIsJustOne() {
		String id = getRequest().getParameter("id");
		String structureId = getRequest().getParameter("structureId");
		String metaDataFullName = getRequest().getParameter("metaDataFullName");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("structureId", structureId);
		map.put("metaDataFullName", metaDataFullName);
		int count = this.dataTempService.checkIsJustOne(map);
		JSONObject obj = new JSONObject();
		obj.put("count", count);
		toPage(obj.toString());
	}
	
	/**
	 * 保存  编辑结构的数据
	 * */
	public void saveStructure() throws IOException{			
		String id = getRequest().getParameter("id");
		String esTitle = getRequest().getParameter("esTitle");
		String esDescription = getRequest().getParameter("esDescription");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", id);
		List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
		if(stList!=null&&stList.size()>0){
			EssStructure es = stList.get(0);
			es.setEsDescription(esDescription);
			es.setEsTitle(esTitle);
			dataTempService.updateStructure(es);
			getResponse().setContentType("text/plain;UTF-8");
			this.outWirter("success");
		}else{
			getResponse().setContentType("text/plain;UTF-8");
			this.outWirter("false");
		}
	}
	
	/**
	 * 转向节点关联结构模板的选择页面
	 * */
	public String toChooseStructurePage(){
		String quoteFlag = getRequest().getParameter("quoteFlag");// 无：选择模板  1：引用模板
		Map<String,Object> map = new HashMap<String,Object>();
		String nodeId = getRequest().getParameter("nodeId");
		String parentId = getRequest().getParameter("parentId");
		if("1".equals(quoteFlag)){
			//获得节点信息
			Map<String,String> map2 = new HashMap<String, String>();
			map2.put("id", nodeId);
			List<EssTree> data = dataTempService.getTreeByMap(map2,null,null);
			String moudelId = data.get(0).getIdModelStructure()+"";
			map.put("id", moudelId);
		}
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = this.structureService.getStructureTempCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<EssStructureModel> list = this.structureService.getStructureTempList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("quoteFlag", quoteFlag);
		return "toChooseStructurePage";
	}
	

	/**
	 * 保存  关联的模板 并生成对应的表
	 * */
	public void addChooseStructure() throws IOException{	
		try {
			Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			String nodeId = getRequest().getParameter("nodeId");
			String modelId = getRequest().getParameter("modelId");
			//获取树的节点信息
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", nodeId);
			List<EssTree> treedata = dataTempService.getTreeByMap(map,null,null);
			EssTree et = treedata.get(0);
			et.setIdModelStructure(Integer.valueOf(modelId));
			//获取模板信息
			Map<String, Object> map2 = new HashMap<String,Object>();
			map2.put("id", Integer.valueOf(modelId));
			List<EssStructureModel> sList = this.structureService.getStructureTempList(map2);
			EssStructureModel esm = sList.get(0);
			
			if("project".equals(esm.getEsType())){
				//1.生成项目级结构以及表
				String structureId_poject = generateTable(modelId,"project",et,emp);	
				if(!"".equals(structureId_poject)){//建立层级关系
					//先将首层结构与树节点关联（子结构则不需要，到时可以递归查出）
					et.setIdStructure(Integer.valueOf(structureId_poject));
					dataTempService.updateEssTree(et);
					//再关联结构表
					dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
							+ " values("+structureId_poject+",1,'启用',1,1,"+nodeId+")");
					//2.生成案卷级结构以及表
					String structureId_file = generateTable(modelId,"file",et,emp);
					if(!"".equals(structureId_file)){//建立层级关系
						dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
								+ " values("+structureId_file+","+structureId_poject+",'启用',1,1,0)");
						
						//3.生成卷内级结构以及表
						String structureId_innerFile = generateTable(modelId,"innerFile",et,emp);
						if(!"".equals(structureId_innerFile)){//建立层级关系
							dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
									+ " values("+structureId_innerFile+","+structureId_file+",'启用',1,1,0)");
							//4.生成电子文件级结构以及表
							String structureId_document = generateTable(modelId,"document",et,emp);
							if(!"".equals(structureId_document)){//建立层级关系
								dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
										+ " values("+structureId_document+","+structureId_innerFile+",'启用',1,1,0)");
								/*//只有当全部结构生成并且生成了表之后，才将结构与树节点关联（防止关联过程中，在生成结构的步骤出现问题，导致节点关联的结构不完整）					
								 */
								et.setIdStructure(Integer.valueOf(structureId_poject));
								dataTempService.updateEssTree(et);
							}
						}	
					}
				}
			}else if("file".equals(esm.getEsType())){
				//1.生成案卷级结构以及表
				String structureId_file = generateTable(modelId,"file",et,emp);
				if(!"".equals(structureId_file)){//建立层级关系
					//先将首层结构与树节点关联（子结构则不需要，到时可以递归查出）
					et.setIdStructure(Integer.valueOf(structureId_file));
					dataTempService.updateEssTree(et);
					//再关联结构表
					dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
							+ " values("+structureId_file+",1,'启用',1,1,"+nodeId+")");
					
					//2.生成卷内级结构以及表
					String structureId_innerFile = generateTable(modelId,"innerFile",et,emp);
					if(!"".equals(structureId_innerFile)){//建立层级关系
						dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
								+ " values("+structureId_innerFile+","+structureId_file+",'启用',1,1,0)");
						//3.生成电子文件级结构以及表
						String structureId_document = generateTable(modelId,"document",et,emp);
						if(!"".equals(structureId_document)){//建立层级关系
							dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
									+ " values("+structureId_document+","+structureId_innerFile+",'启用',1,1,0)");
							/*//只有当全部结构生成并且生成了表之后，才将结构与树节点关联（防止关联过程中，在生成结构的步骤出现问题，导致节点关联的结构不完整）					
							 */
							et.setIdStructure(Integer.valueOf(structureId_file));
							dataTempService.updateEssTree(et);
						}
					}	
				}
				
			}else if("innerFile".equals(esm.getEsType())){
				//1.生成卷内级结构以及表
				String structureId_innerFile = generateTable(modelId,"innerFile",et,emp);
				if(!"".equals(structureId_innerFile)){//建立层级关系
					dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
							+ " values("+structureId_innerFile+",1,'启用',1,1,"+nodeId+")");
					//2.生成电子文件级结构以及表
					String structureId_document = generateTable(modelId,"document",et,emp);
					if(!"".equals(structureId_document)){//建立层级关系
						dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
								+ " values("+structureId_document+","+structureId_innerFile+",'启用',1,1,0)");
						/*//只有当全部结构生成并且生成了表之后，才将结构与树节点关联（防止关联过程中，在生成结构的步骤出现问题，导致节点关联的结构不完整）					
						 */
						et.setIdStructure(Integer.valueOf(structureId_innerFile));
						dataTempService.updateEssTree(et);
					}
				}	
			}
			toPage("success");
		} catch (Exception e) {
			toPage("false");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
	}
	
	/**
	 * 转向节点关联结构模板的引用模板页面
	 * */
	public String toQuoteStructurePage(){
		String nodeId = getRequest().getParameter("nodeId");//需要引用的节点
		String parentId = getRequest().getParameter("parentId");//需要引用的节点的父节点（用来刷新）
		//获得目录树
		Map<String,String> map = new HashMap<String, String>();
		//map.put("business", businessId);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"checkArr\":\"").append("0\",");
			if(idStructure!=null && idStructure != 0) {	
				json.append("\"last\":").append("false,");
			}
			json.append("\"basicData\":").append(idStructure).append("");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("treeJson", json);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("parentId", parentId);
		return "toQuoteStructurePage";
	}
	
	/**
	 * 保存  引用的模板 
	 * */
	public void addQuoteStructure() throws IOException{	
		String quoteNodeId = getRequest().getParameter("quoteNodeId");//被引用的节点
		String nodeId = getRequest().getParameter("nodeId");
		String modelId = getRequest().getParameter("modelId");
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", quoteNodeId);
			List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
			int structureId = data.get(0).getIdStructure();
			
			//更新节点信息
			map.clear();
			map.put("id", nodeId);
			EssTree et = dataTempService.getTreeByMap(map,null,null).get(0);
			et.setIdStructure(structureId);
			et.setIdModelStructure(Integer.valueOf(modelId));
			dataTempService.updateEssTree(et);
			//更新结构关联关系
			dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
					+ " values("+structureId+",1,'启用',1,1,"+nodeId+")");
			toPage("success");
		} catch (NumberFormatException e) {
			toPage("false");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 转向节点关联结构模板的复制模板页面
	 * */
	public String toCopyStructurePage(){
		String nodeId = getRequest().getParameter("nodeId");
		String parentId = getRequest().getParameter("parentId");
		//获得目录树
		Map<String,String> map = new HashMap<String, String>();
		//map.put("business", businessId);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (EssTree e : data) {
			Integer idStructure = e.getIdStructure();
			json.append("{");
			json.append("\"id\":\"").append(e.getId()).append("\",");
			json.append("\"title\":\"").append(e.getTitle().toString()).append("\",");
			json.append("\"parentId\":\"").append(e.getIdParent()).append("\",");
			json.append("\"checkArr\":\"").append("0\",");
			if(idStructure!=null && idStructure != 0) {	
				json.append("\"last\":").append("false,");
			}
			json.append("\"basicData\":").append(idStructure).append("");
			json.append("},");
		}
		int index = json.lastIndexOf(",");
		if(index>-1){
			json.deleteCharAt(index);
		}
		json.append("]");
		getRequest().setAttribute("treeJson", json);
		getRequest().setAttribute("nodeId", nodeId);
		getRequest().setAttribute("parentId", parentId);
		return "toCopyStructurePage";
	}
	
	/**
	 * 保存 复制的模板
	 * */
	public void addCopyStructure() throws IOException{	
		try {
			Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			String copyNodeId = getRequest().getParameter("copyNodeId");
			String nodeId = getRequest().getParameter("nodeId");
			//获取树的节点信息
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", nodeId);
			EssTree et = dataTempService.getTreeByMap(map,null,null).get(0);//需要复制的节点
			
			map.clear();
			map.put("id", copyNodeId);
			EssTree et_copy = dataTempService.getTreeByMap(map,null,null).get(0);//被复制的节点
			
			et.setIdModelStructure(et_copy.getIdModelStructure());//设置模板的id
			
			
			map.clear();
			map.put("structureId", et_copy.getIdStructure()+"");
			map.put("type", "dg");//递归查询
			List<EssStructure> list = dataTempService.getStructureList(map, null, null);
			List<String> stlist = new ArrayList<String>();//新的结构集合
			for(int i=0;i<list.size();i++){
				EssStructure est_old = list.get(i);
				String structureId_old = est_old.getId()+"";
				est_old.setId(null);
				est_old.setEsCreator(emp.getEmployeeLoginname());
				est_old.setEsDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				est_old.setEsIdentifire(UuidGenerator.generate36UUID());
				est_old.setEsPublisher(emp.getEmployeeLoginname());
				dataTempService.updateStructure(est_old);
				String structureId_new = est_old.getId()+"";
				stlist.add(structureId_new);//新的结构加入集合，后面做关联
				String tableName = "ESP_"+structureId_new;
				map.clear();
				map.put("structureId", structureId_old);
				List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
				String createTableSql = "";
				String baseColumn=" id number(10),espath varchar2(1024),id_parent_structure number(10),id_parent_package number(10),tree_nodeid number(10),documentflag varchar2(1)";
				for(int j=0;j<etList.size();j++){
					EssTag etag = etList.get(j);
					etag.setId(null);
					etag.setIdStructure(Integer.parseInt(structureId_new));
					dataTempService.updateTag(etag);
					if("大文本型".equals(etag.getEsType())){
						createTableSql+=",C"+etag.getId()+" clob";
					}else if("数值类型".equals(etag.getEsType())){
						createTableSql+=",C"+etag.getId()+" number("+etag.getEsLength()+")";
					}else{
						createTableSql+=",C"+etag.getId()+" varchar2("+etag.getEsLength()+")";
					}
				}
				String sql =" create table "+tableName+" ("+baseColumn+createTableSql+")";
				dataTempService.updateNativeSql(sql);
				//生成表后给对应表创建序列
				dataTempService.updateNativeSql(" create sequence SEQ_"+tableName
						+ " minvalue 1 maxvalue 99999999999999999999999 "
						+ " start with 1 "
						+ " increment by 1 "
						+ " nocache");
			}
			
			for(int k=0;k<stlist.size();k++){
				if(k==0){
					et.setIdStructure(Integer.valueOf(stlist.get(0)));
					dataTempService.updateEssTree(et);
					dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
							+ " values("+stlist.get(0)+",1,'启用',1,1,"+nodeId+")");
				}else{
					dataTempService.updateNativeSql(" insert into t_archive_childstructure(id_childstructure,id_structure,esstatus,esorder,relation,id_package)"
							+ " values("+stlist.get(k)+","+stlist.get(k-1)+",'启用',1,1,0)");
				}
			}
			toPage("success");
		} catch (Exception e) {
			toPage("false");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
	}
	
	public String generateTable(String modelId,String esType,EssTree et,Employee emp){
		String rteturn = "";
		Map<String, Object> map = new HashMap<String,Object>();
		EssStructure es1 = new EssStructure();
		es1.setEsCreator(emp.getEmployeeLoginname());
		es1.setEsDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		es1.setEsDescription("名称");
		es1.setEsIdentifire(UuidGenerator.generate36UUID());
		es1.setEsOrder("");
		es1.setEsPublisher(emp.getEmployeeLoginname());
		es1.setEsStatus("启动");
		if("project".equals(esType)){
			es1.setEsTitle(et.getTitle()+"项目级");
		}else if("file".equals(esType)){
			es1.setEsTitle(et.getTitle()+"案卷级");
		}else if("innerFile".equals(esType)){
			es1.setEsTitle(et.getTitle()+"卷内级");
		}else if("document".equals(esType)){
			es1.setEsTitle(et.getTitle()+"电子文件级");
		}
		es1.setEsType(esType);
		dataTempService.updateStructure(es1);
		String tableName = "ESP_"+es1.getId();
		//将模板的字段取出，复制到字段表中，并生成表
		map.clear();
		map.put("modelId", Integer.valueOf(modelId));
		map.put("esType",esType);
		List<EssModelTags> modelTagList = this.structureService.getEssModelTagsList(map);//取出模板字段
		String createTableSql = "";
		String baseColumn=" id number(10),espath varchar2(1024),id_parent_structure number(10),id_parent_package number(10),tree_nodeid number(10),documentflag varchar2(1)";
		if("document".equals(esType)&&modelTagList.size()==0){//电子文件  若没有关联字段，则系统自动生成
			List<Map<String,String>> zdxzRuleList = new ArrayList<Map<String,String>>();
			Map<String,String> zdxzRuleMap = new HashMap<String, String>();
			//自动生成
			EssTag etag = new EssTag();
			etag.setEsDescription("树节点的顺序号");etag.setEsDotlength("0");etag.setEsIdentifier("序号");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("4");etag.setEsOrder(1);
			etag.setEsRelation("");etag.setEsType("数值类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" number(4)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "1");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("文档的种类，如正文、处理单、附件");etag.setEsDotlength("0");etag.setEsIdentifier("文件类别");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("32");etag.setEsOrder(2);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(32)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "2");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("电子文件的时间戳");etag.setEsDotlength("0");etag.setEsIdentifier("创建时间");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("22");etag.setEsOrder(3);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(22)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "3");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("挂接扫描图像文件或其他电子文件的全路径。");etag.setEsDotlength("0");etag.setEsIdentifier("原文路径");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("512");etag.setEsOrder(4);
			etag.setEsRelation("");etag.setEsType("资源类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(512)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "4");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("电子文件的和校验值(MD5)");etag.setEsDotlength(null);etag.setEsIdentifier("文件校验");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("64");etag.setEsOrder(5);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(64)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "5");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("电子文件的大小");etag.setEsDotlength("0");etag.setEsIdentifier("文件大小");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("32");etag.setEsOrder(6);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(32)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "6");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			
			etag = new EssTag();
			etag.setEsDescription("档案资源的所属部门");etag.setEsDotlength("0");etag.setEsIdentifier("所属部门");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("50");etag.setEsOrder(7);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(50)";
			//设置默认规则
			zdxzRuleMap = new HashMap<String, String>();
			zdxzRuleMap.put("esorder", "7");
			zdxzRuleMap.put("idstructure", es1.getId()+"");
			zdxzRuleMap.put("idtag", etag.getId()+"");
			zdxzRuleList.add(zdxzRuleMap);
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"grid",es1.getId()+"");//列表字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"ofForm",es1.getId()+"");//表单字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"adSearch",es1.getId()+"");//综合查询下拉字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"ofDdto",es1.getId()+"");//追加携带字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"usingForm",es1.getId()+"");//参与检索字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"usingGrid",es1.getId()+"");//检索结果展示字段	
			
			//系统字段
			etag = new EssTag();
			etag.setEsDescription("用户的IP地址");etag.setEsDotlength(null);etag.setEsIdentifier("登录IP");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("16");etag.setEsOrder(101);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(16)";
			
			etag = new EssTag();
			etag.setEsDescription("创建人");etag.setEsDotlength(null);etag.setEsIdentifier("创建人");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("50");etag.setEsOrder(102);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(50)";
			
			etag = new EssTag();
			etag.setEsDescription("修改人");etag.setEsDotlength(null);etag.setEsIdentifier("修改人");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("50");etag.setEsOrder(103);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(50)";
			
			etag = new EssTag();
			etag.setEsDescription("当前用户所在机构");etag.setEsDotlength(null);etag.setEsIdentifier("所在机构");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("200");etag.setEsOrder(104);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(200)";
			
			etag = new EssTag();
			etag.setEsDescription("创建日期");etag.setEsDotlength(null);etag.setEsIdentifier("创建日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(105);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
			etag = new EssTag();
			etag.setEsDescription("修改日期");etag.setEsDotlength(null);etag.setEsIdentifier("修改日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(106);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
			etag = new EssTag();
			etag.setEsDescription("Rec_PkgStatus状态");etag.setEsDotlength(null);etag.setEsIdentifier("状态值");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("128");etag.setEsOrder(107);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(128)";
			
			etag = new EssTag();
			etag.setEsDescription("Src_PkgPath源Path");etag.setEsDotlength(null);etag.setEsIdentifier("源数据路径");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("4000");etag.setEsOrder(108);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(4000)";
			
			etag = new EssTag();
			etag.setEsDescription("借出份数");etag.setEsDotlength(null);etag.setEsIdentifier("借出份数");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("100");etag.setEsOrder(109);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(100)";
			
			etag = new EssTag();
			etag.setEsDescription("库房路径");etag.setEsDotlength(null);etag.setEsIdentifier("库房路径");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("256");etag.setEsOrder(110);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(256)";
			
			etag = new EssTag();
			etag.setEsDescription("划控日期");etag.setEsDotlength(null);etag.setEsIdentifier("划控日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(111);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
		}else{
			//1.先生成系统字段
			EssTag etag = new EssTag();
			etag.setEsDescription("用户的IP地址");
			etag.setEsDotlength(null);
			etag.setEsIdentifier("登录IP");
			etag.setEsIsNotNull("1");
			etag.setEsIsEdit("1");
			etag.setEsLength("16");
			etag.setEsOrder(101);
			etag.setEsRelation("");
			etag.setEsType("文本类型");
			etag.setIdMetadata(null);
			etag.setNameMetadata(null);
			etag.setIdParent(null);
			etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(16)";
			
			etag = new EssTag();
			etag.setEsDescription("创建人");etag.setEsDotlength(null);etag.setEsIdentifier("创建人");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("50");etag.setEsOrder(102);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(50)";
			
			etag = new EssTag();
			etag.setEsDescription("修改人");etag.setEsDotlength(null);etag.setEsIdentifier("修改人");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("50");etag.setEsOrder(103);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(50)";
			
			etag = new EssTag();
			etag.setEsDescription("当前用户所在机构");etag.setEsDotlength(null);etag.setEsIdentifier("所在机构");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("200");etag.setEsOrder(104);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(200)";
			
			etag = new EssTag();
			etag.setEsDescription("创建日期");etag.setEsDotlength(null);etag.setEsIdentifier("创建日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(105);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
			etag = new EssTag();
			etag.setEsDescription("修改日期");etag.setEsDotlength(null);etag.setEsIdentifier("修改日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(106);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
			etag = new EssTag();
			etag.setEsDescription("Rec_PkgStatus状态");etag.setEsDotlength(null);etag.setEsIdentifier("状态值");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("128");etag.setEsOrder(107);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(128)";
			
			etag = new EssTag();
			etag.setEsDescription("Src_PkgPath源Path");etag.setEsDotlength(null);etag.setEsIdentifier("源数据路径");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("4000");etag.setEsOrder(108);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(4000)";
			
			etag = new EssTag();
			etag.setEsDescription("借出份数");etag.setEsDotlength(null);etag.setEsIdentifier("借出份数");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("100");etag.setEsOrder(109);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(100)";
			
			etag = new EssTag();
			etag.setEsDescription("库房路径");etag.setEsDotlength(null);etag.setEsIdentifier("库房路径");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("256");etag.setEsOrder(110);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(256)";
			
			etag = new EssTag();
			etag.setEsDescription("划控日期");etag.setEsDotlength(null);etag.setEsIdentifier("划控日期");
			etag.setEsIsNotNull("1");etag.setEsIsEdit("1");etag.setEsLength("20");etag.setEsOrder(111);
			etag.setEsRelation("");etag.setEsType("文本类型");etag.setIdMetadata(null);
			etag.setNameMetadata(null);etag.setIdParent(null);etag.setIdStructure(es1.getId());
			dataTempService.updateTag(etag);
			createTableSql+=",C"+etag.getId()+" varchar2(20)";
			
			//2.再生成配置字段
			List<Map<String,String>> zdxzRuleList = new ArrayList<Map<String,String>>();
			List<Map<String,String>> titleRuleList = new ArrayList<Map<String,String>>();
			Map<String,String> zdxzRuleMap = new HashMap<String, String>();
			for(int i=0;i<modelTagList.size();i++){
				etag = new EssTag();
				String tagType = modelTagList.get(i).getTagType();
				String eslength = modelTagList.get(i).getEsLength();
				
				etag.setEsDescription(modelTagList.get(i).getTagDesc());
				etag.setEsDotlength(modelTagList.get(i).getEsDoLength());
				etag.setEsIdentifier(modelTagList.get(i).getTagName());
				etag.setEsIsNotNull(modelTagList.get(i).getEsIsNotNull());
				etag.setEsIsEdit(modelTagList.get(i).getEsIsEdit());;
				etag.setEsLength(eslength);
				etag.setEsOrder(modelTagList.get(i).getViewOrder());
				etag.setEsRelation("");
				etag.setEsType(tagType);
				etag.setIdMetadata(modelTagList.get(i).getId_MetaData());
				etag.setNameMetadata(modelTagList.get(i).getMetaDataFullName());
				etag.setIdParent(null);
				etag.setIdStructure(es1.getId());
				dataTempService.updateTag(etag);
				if(etag.getNameMetadata()!=null&&!"".equals(etag.getNameMetadata())&&etag.getNameMetadata().indexOf(":Title")>-1){//包含title元数据，自动设置为标题属性
					zdxzRuleMap = new HashMap<String, String>();
					zdxzRuleMap.put("esorder", i+1+"");
					zdxzRuleMap.put("idstructure", es1.getId()+"");
					zdxzRuleMap.put("idtag", etag.getId()+"");
					zdxzRuleList.add(zdxzRuleMap);
					titleRuleList.add(zdxzRuleMap);
					dataTempService.saveZDXZDataOfTable(titleRuleList,"abstract",es1.getId()+"");//标题字段	
				}
				
				if("大文本型".equals(tagType)){
					createTableSql+=",C"+etag.getId()+" clob";
				}else if("数值类型".equals(tagType)){
					createTableSql+=",C"+etag.getId()+" number("+eslength+")";
				}else{
					createTableSql+=",C"+etag.getId()+" varchar2("+eslength+")";
				}
				//设置默认规则
				zdxzRuleMap = new HashMap<String, String>();
				zdxzRuleMap.put("esorder", i+1+"");
				zdxzRuleMap.put("idstructure", es1.getId()+"");
				zdxzRuleMap.put("idtag", etag.getId()+"");
				zdxzRuleList.add(zdxzRuleMap);
			}
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"grid",es1.getId()+"");//列表字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"ofForm",es1.getId()+"");//表单字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"adSearch",es1.getId()+"");//综合查询下拉字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"ofDdto",es1.getId()+"");//追加携带字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"usingForm",es1.getId()+"");//参与检索字段
			dataTempService.saveZDXZDataOfTable(zdxzRuleList,"usingGrid",es1.getId()+"");//检索结果展示字段	
		}
		
		String sql =" create table "+tableName+" ("+baseColumn+createTableSql+")";
		dataTempService.updateNativeSql(sql);
		//生成表后给对应表创建序列
		dataTempService.updateNativeSql(" create sequence SEQ_"+tableName
				+ " minvalue 1 maxvalue 99999999999999999999999 "
				+ " start with 1 "
				+ " increment by 1 "
				+ " nocache");
		
		rteturn=es1.getId()+"";
		return rteturn;
	}
	
	/**
	 * 检查节点 下关联的模板 是否有数据
	 * */
	public void checkIsHaveDataOfNode() throws IOException{
		String nodeId = getRequest().getParameter("nodeId");
		//获取树的节点信息
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", nodeId);
		List<EssTree> treedata = dataTempService.getTreeByMap(map,null,null);
		EssTree et = treedata.get(0);
		
		map.clear();
		map.put("structureId", et.getIdStructure()+"");
		map.put("treeId", et.getId()+"");//哪个树下面的结构
		map.put("type", "dg");//递归查询
		List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
		String flag = "true";
		for(int i=0;i<stList.size();i++){
			String sql =" select count(*),1 from ESP_"+stList.get(i).getId();
			List<Object[]> strarr = dataTempService.excuteNativeSql(sql);
			if(!"0".equals(strarr.get(0)[0].toString())){
				flag="false";//有数据
				break;
			}
		}
		toPage(flag);
	}
	/**
	 * 取消节点 关联的模板 并删除对应的表
	 * */
	public void deleteStructureFromNode() throws IOException{
		try {
			String nodeId = getRequest().getParameter("nodeId");
			//获取树的节点信息
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", nodeId);
			List<EssTree> treedata = dataTempService.getTreeByMap(map,null,null);
			EssTree et = treedata.get(0);
			//先删除结构与节点的关联关系，防止先删表过程中异常，导致不完整
			String id_structure=et.getIdStructure()+"";
			et.setIdStructure(0);
			dataTempService.updateEssTree(et);
			
			//再查询是否多个节点共同使用的某个结构
			String sql =" select count(*),1 from t_archive_childstructure where id_childstructure="+id_structure;
			List<Object[]> strarr = dataTempService.excuteNativeSql(sql);
			map.clear();
			map.put("structureId", id_structure+"");
			map.put("treeId", et.getId()+"");//哪个树下面的结构
			map.put("type", "dg");//递归查询
			List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
			if(!"1".equals(strarr.get(0)[0].toString())){//多个的情况下，只要把节点与顶层结构的对应关系和对应结构的数据删除就行（这种情况出现在模板的引用）
				dataTempService.updateNativeSql(" delete from t_archive_childstructure where id_childstructure="+id_structure+" and id_package="+nodeId);
				for(int i=0;i<stList.size();i++){
					String id = stList.get(i).getId()+"";
					//删除数据
					dataTempService.updateNativeSql(" delete from ESP_"+id+" where tree_nodeid="+ nodeId);
					//删除规则
					deleteRule(id);
				}
			}else if("1".equals(strarr.get(0)[0].toString())){//一个的情况下，将所有的子结构对应关系都删除
				for(int i=0;i<stList.size();i++){
					String id = stList.get(i).getId()+"";
					//1.删除结构与结构之间的对应关系
					dataTempService.updateNativeSql(" delete from t_archive_childstructure where id_childstructure="+id);
					//2.删除结构表中的结构
					dataTempService.updateNativeSql(" delete from t_archive_structure where id="+id);
					//3.删除字段表中关于结构的字段
					dataTempService.updateNativeSql(" delete from t_archive_tag where id_structure="+id);
					//4.删除结构对应的业务表
					dataTempService.updateNativeSql(" drop table ESP_"+id);
					//5.删除结构对应的业务表的序列
					dataTempService.updateNativeSql(" drop sequence  SEQ_ESP_"+id);
					//6.删除规则
					deleteRule(id);
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		} finally{
			toPage("success");
		}
	}
	
	/**
	 * 删除结构时，删除对应的字段规则
	 */
	public void deleteRule(String structureId) {
		//1.字段选择，名称删除     传过去集合为空，等于删除之前保存的数据
		dataTempService.saveZDXZDataOfTable(null,"grid",structureId);
		dataTempService.saveZDXZDataOfTable(null,"ofForm",structureId);
		dataTempService.saveZDXZDataOfTable(null,"adSearch",structureId);
		dataTempService.saveZDXZDataOfTable(null,"ofDdto",structureId);
		dataTempService.saveZDXZDataOfTable(null,"usingForm",structureId);
		dataTempService.saveZDXZDataOfTable(null,"usingGrid",structureId);
		dataTempService.saveZDXZDataOfTable(null,"abstract",structureId);
		//2.排序
		dataTempService.savePxData(null,structureId);
		//3.组合字段
		dataTempService.saveDataOfZHZD(null,structureId);
		//4.字段值
		dataTempService.saveOfZDZData(null,structureId);
		//5.代码值
		dataTempService.saveofDMZData(null,"fieldCode",structureId);
		//6.补零
		dataTempService.saveDataOfBL(null,structureId);
	}
	
	/**
	 * 校验业务模板字段是否存在信息
	 */
	public void checkIsHaveTag() {
		String structureId = getRequest().getParameter("structureId");
		String esIdentifier = getRequest().getParameter("esIdentifier");
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("esIdentifier", esIdentifier);
		int count = dataTempService.getEssTagCount(map);
		if(count>0){
			toPage("true");
		}else{
			toPage("false");
		}
	}
	
	/**
	 * 校验业务表字段是否录入数据
	 */
	public void checkIsHaveData() {
		String id = getRequest().getParameter("id");
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		String sql ="";
		if("大文本型".equals(etList.get(0).getEsType())){
			sql =" select count(C"+id+"),1 from ESP_"+structureId+" where C"+id+" is not null and to_char(C"+id+") <>' '";
		}else{
			sql =" select count(C"+id+"),1 from ESP_"+structureId+" where C"+id+" is not null and C"+id+" <>' '";
		}
		List<Object[]> list = dataTempService.excuteNativeSql(sql);
		if("0".equals(list.get(0)[0].toString())){//无数据
			toPage("false");
		}else{
			toPage("true");
		}
	}
	
	/**
	 * 保存业务模板字段信息
	 */
	public void addTags() {
		String structureId = getRequest().getParameter("structureId");
		String str = getRequest().getParameter("str");
		

		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		int sort=0;
		if(etList!=null&&etList.size()>0){
			sort=etList.get(etList.size()-1).getEsOrder();//取最大排序号
		}
		
		String[] esmarr = str.split(";");
		try {
			for(int i=0;i<esmarr.length;i++){
				String[] data = esmarr[i].split(",");
				EssTag et = new EssTag();
				et.setIdStructure(Integer.valueOf(structureId));
				try {
					et.setEsOrder(Integer.valueOf(data[0]));//排序号
				} catch (NumberFormatException e) {
					et.setEsOrder(sort+1);
					sort++;
				}
				et.setEsIdentifier(data[1]);//字段名
				et.setNameMetadata(data[2]);//元数据
				et.setIdMetadata(data[3]);
				et.setEsType(data[4]);//字段类型
				et.setEsDescription(data[5]);//字段描述
				String length = Integer.valueOf(data[6])+"";
				if("大文本型".equals(data[4])){
						length="0";
				}
				et.setEsLength(length);//字段长度
				String dotLength = Integer.valueOf(data[7])+"";
				et.setEsDotlength(dotLength);//小数位数
				et.setEsIsEdit(data[8]);//是否编辑
				et.setEsIsNotNull(data[9]);//是否必填
				dataTempService.updateTag(et);
				//给表增加字段
				String fieldType="";
				if("大文本型".equals(data[4])){
					fieldType="clob";
				}else if("数值类型".equals(data[4])){
					fieldType="number("+length+")";
				}else{
					fieldType="varchar2("+length+")";
				}
				dataTempService.updateNativeSql(" alter table ESP_"+structureId+" add (C"+et.getId()+" "+fieldType+")");
			}
			toPage("success");
		} catch (Exception e) {
			e.printStackTrace();
			toPage("false");
		}
	}
	
	/**
	 * 保存业务模板字段信息
	 */
	public void updateTags() {
		String structureId = getRequest().getParameter("structureId");
		String str = getRequest().getParameter("str");
		

		Map<String,String> map = new HashMap<String, String>();
		/*map.put("structureId", structureId);
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		int sort=0;
		if(etList!=null&&etList.size()>0){
			sort=etList.get(etList.size()-1).getEsOrder();//取最大排序号
		}*/
		
		String[] esmarr = str.split(";");
		try {
			for(int i=0;i<esmarr.length;i++){
				String[] data = esmarr[i].split(",");
				map.clear();
				map.put("id", data[0]);
				map.put("structureId", structureId);
				List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
				if(etList!=null&&etList.size()>0){
					EssTag et = etList.get(0);
					try {
						et.setEsOrder(Integer.valueOf(data[1]));//排序号
					} catch (NumberFormatException e) {
					}
					et.setEsIdentifier(data[2]);//字段名
					et.setNameMetadata(data[3]);//元数据
					et.setIdMetadata(data[4]);
					String estype = data[5];
					String oldesType = et.getEsType();
					et.setEsType(data[5]);//字段类型
					et.setEsDescription(data[6]);//字段描述
					String length = Integer.valueOf(data[7])+"";
					String oldLength = et.getEsLength();
					if("数值类型".equals(estype)){
						if(Integer.valueOf(data[7])>9){
							length="9";
							oldLength="9";
						}
					}
					et.setEsLength(length);//字段长度
					String dotLength = Integer.valueOf(data[8])+"";
					et.setEsDotlength(dotLength);//小数位数
					et.setEsIsEdit(data[9]);//是否编辑
					et.setEsIsNotNull(data[10]);//是否必填
					dataTempService.updateTag(et);
					//开始修改表
					if(estype.equals(oldesType)){//类型不修改
						if(length!=oldLength){
							if("大文本型".equals(estype)){	//非大文本类型，字段长度修改，才操作数据库
							}else if("数值类型".equals(estype)){
								dataTempService.updateNativeSql(" alter table ESP_"+structureId+" modify  (C"+et.getId()+" number("+length+"))");
							}else{
								dataTempService.updateNativeSql(" alter table ESP_"+structureId+" modify  (C"+et.getId()+" varchar2("+length+"))");
							}
						}
					}else{//类型修改(先删除再加)
						dataTempService.updateNativeSql(" alter table ESP_"+structureId+" drop column C"+et.getId());
						//给表增加字段
						String fieldType="";
						if("大文本型".equals(estype)){
							fieldType="clob";
						}else if("数值类型".equals(estype)){
							fieldType="number("+length+")";
						}else{
							fieldType="varchar2("+length+")";
						}
						dataTempService.updateNativeSql(" alter table ESP_"+structureId+" add ( C"+et.getId()+" "+fieldType+")");
					}
				}
				
			}
			toPage("success");
		} catch (Exception e) {
			e.printStackTrace();
			toPage("false");
		}
	}
	
	/**
	 * 校验要删除的字段 
	 */
	public void checkDeleteTags() {
		JSONObject obj = new JSONObject();
		Map<String,String> map = new HashMap<String, String>();
		String structureId = getRequest().getParameter("structureId");
		String ids = getRequest().getParameter("ids"); 
		String[] idarr = ids.split(",");
		String ruleContent="";
		String dataContent="";
		for(int i=0;i<idarr.length;i++){
			String id = idarr[i];
			//先校验字段规则
			ruleContent+="";
			
			//再校验字段下是否有值
			map.put("id", id);
			map.put("structureId", structureId);
			List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
			String sql="";
			if("大文本型".equals(etList.get(0).getEsType())){
				sql =" select count(C"+id+"),1 from ESP_"+structureId+" where C"+id+" is not null and to_char(C"+id+") <>' '";
			}else{
				sql =" select count(C"+id+"),1 from ESP_"+structureId+" where C"+id+" is not null and C"+id+" <>' '";
			}
			try {
				List<Object[]> list = dataTempService.excuteNativeSql(sql);
				if(!"0".equals(list.get(0)[0].toString())){//有数据
					dataContent+=etList.get(0).getEsIdentifier()+",";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		if(!"".equals(ruleContent)){
			obj.put("type", "1");//规则校验不通过
			obj.put("content", ruleContent);
		}else{
			
			obj.put("type", "2");//数据校验
			obj.put("content", dataContent);
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除字段 
	 */
	public void deleteTags() {
		String structureId = getRequest().getParameter("structureId");
		String ids = getRequest().getParameter("ids"); 
		String[] idarr = ids.split(",");
		for(int i=0;i<idarr.length;i++){
			String id = idarr[i];
			try {
				dataTempService.updateNativeSql("delete from T_ARCHIVE_TAG where id="+id);
				dataTempService.updateNativeSql("alter table ESP_"+structureId+" drop column C"+id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		toPage("success");
	}
	
	/**
	 * list转为json字符串
	 * @param ts
	 * @return
	 */
	public static <T> String listToJson(List<T> ts) {
        JSONArray jsonarr = JSONArray.fromObject(ts);
        String jsons = jsonarr.toString();
        return jsons;
    }
	
	/**
	 * 跳转到字段选择页面
	 * @return
	 */
	public String toShowZDXZPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowZDXZJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//1.获取列表展示字段
		map.put("tableFlag","grid");
		//选中数据
		List<EssZDXZCommon> gridList = this.dataTempService.getZDXZDataList(map);
		
		//2.获取表单展示字段
		map.clear();
		map.put("tableFlag", "ofForm");
		map.put("structureId", structureId);
		List<EssZDXZCommon> ofFormList = this.dataTempService.getZDXZDataList(map);
		
		//3.获取综合查询字段
		map.clear();
		map.put("tableFlag", "adSearch");
		map.put("structureId", structureId);
		List<EssZDXZCommon> adSearchList = this.dataTempService.getZDXZDataList(map);
		
		//4.获取追加携带字段
		map.clear();
		map.put("tableFlag", "ofDdto");
		map.put("structureId", structureId);
		List<EssZDXZCommon> ofDdtoList = this.dataTempService.getZDXZDataList(map);
		
		//5.获取参与检索字段
		map.clear();
		map.put("tableFlag", "usingForm");
		map.put("structureId", structureId);
		List<EssZDXZCommon> usingFormList = this.dataTempService.getZDXZDataList(map);
		
		//6.获取检索结果展示字段
		map.clear();
		map.put("tableFlag", "usingGrid");
		map.put("structureId", structureId);
		List<EssZDXZCommon> usingGridList = this.dataTempService.getZDXZDataList(map);
		
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("gridList", listToJson(gridList));
		getRequest().setAttribute("ofFormList", listToJson(ofFormList));
		getRequest().setAttribute("adSearchList", listToJson(adSearchList));
		getRequest().setAttribute("ofDdtoList", listToJson(ofDdtoList));
		getRequest().setAttribute("usingFormList", listToJson(usingFormList));
		getRequest().setAttribute("usingGridList", listToJson(usingGridList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowZDXZJsp";
	}
	
	/**
	 * 保存字段选择数据
	 */
	public void saveZDXZDataOfTable() {
		JSONObject obj = new JSONObject();
		String tableFlag = getRequest().getParameter("tableFlag");
		String jsonArrStr = getRequest().getParameter("data");
		String idstructure = getRequest().getParameter("idstructure");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.dataTempService.saveZDXZDataOfTable(list,tableFlag,idstructure);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到名称页面（摘要显示控制页面）
	 * @return
	 */
	public String toShowMCPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowMCJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//1.获取列表展示字段
		map.put("tableFlag","abstract");
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZDXZDataList(map);
		if(abstructList != null && !abstructList.isEmpty() && abstructList.get(0) != null) {
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTag = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTag == id) {
						it.remove();
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(abstructList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowMCJsp";
	}
	
	/**
	 * 跳转到排序页面
	 * @return
	 */
	public String toShowPXPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowPXJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getPXDataList(map);
		List<EssZDXZCommon> returnAllList = new ArrayList<EssZDXZCommon>();
		if(abstructList == null || abstructList.isEmpty()) {
			getRequest().setAttribute("allYuanList", listToJson(etList));
			getRequest().setAttribute("abstractList", listToJson(abstructList));
			getRequest().setAttribute("structureId", structureId);
			return "toShowPXJsp";
		}
		//循环更改选中的数据的格式
		for(int i = 0; i < abstructList.size(); i++) {
			EssZDXZCommon eszn = abstructList.get(i);
			String rule = eszn.getRule();
			String[] ruleArr = rule.split(",");
			for(int t = 0; t < ruleArr.length; t++) {
				String ruleStr = ruleArr[t];
				String[] ruleStrArr = ruleStr.split(" ");
				EssZDXZCommon eszns = new EssZDXZCommon();
				eszns.setIdstructure(eszn.getIdstructure());
				eszns.setIdtag(Integer.valueOf(ruleStrArr[0]));
				eszns.setRuleOneSort(ruleStrArr[1]);
				if("DESC".equals(ruleStrArr[1]) || "ASC".equals(ruleStrArr[1])) {
					eszns.setRuleFlag("SYSTEM");
				} else {
					eszns.setRuleFlag("CUSTOM");
				}
				returnAllList.add(eszns);
			}
		}
		//清理总数据
		for(int i = 0; i < returnAllList.size(); i++) {
			EssZDXZCommon eszn = returnAllList.get(i);
			int idTag = eszn.getIdtag();
			String ruleFlag = eszn.getRuleFlag();
			Iterator<EssTag> it = etList.iterator();
			while(it.hasNext()) {
				EssTag et = it.next();
				int id = et.getId();
				if(idTag == id) {
					String tagName = et.getEsIdentifier();
					eszn.setTagName(tagName);
					if("SYSTEM".equals(ruleFlag)) {
						it.remove();
					} else if("CUSTOM".equals(ruleFlag)){
						et.setIsCustom("CUSTOM");
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(returnAllList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowPXJsp";
	} 
	
	/**
	 * 保存排序信息
	 */
	public void savePxData() {
		JSONObject obj = new JSONObject();
		String sortString = getRequest().getParameter("data");
		String idstructure = getRequest().getParameter("idstructure");
		boolean flag = this.dataTempService.savePxData(sortString,idstructure);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到字段值页面
	 * @return
	 */
	public String toShowZDZPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowZDZJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZDZDataList(map);
		if(abstructList != null && !abstructList.isEmpty()) {
			//清理总数据
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTag = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTag == id) {
						String tagName = et.getEsIdentifier();
						eszn.setTagName(tagName);
						it.remove();
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(abstructList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowZDZJsp";
	}
	
	/**
	 * 获取自动累加字段的参照字段
	 */
	public void getZDZAppendFields() {
		String structureId = getRequest().getParameter("structureId");
		String idTagStr = getRequest().getParameter("idTag");
		int id_tag = Integer.valueOf(idTagStr);
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("idTag", idTagStr);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		Iterator<EssTag> itFirst = etList.iterator();
		while(itFirst.hasNext()) {
			EssTag et = itFirst.next();
			int id = et.getId();
			if(id == id_tag) {
				itFirst.remove();
			}
		}
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZDZDataList(map);
		List<EssZDXZCommon> returnList = new ArrayList<EssZDXZCommon>();
		if(abstructList != null && !abstructList.isEmpty()) {
			EssZDXZCommon eszdzc = abstructList.get(0);
			String tagIds = eszdzc.getTagIds();
			String[] tagIdsArr = tagIds.split(",");
			if(tagIdsArr != null && tagIdsArr.length > 0 && !"".equals(tagIdsArr[0])) {
				for(int i = 0; i < tagIdsArr.length; i++) {
					int idTag = Integer.valueOf(tagIdsArr[i]);
					EssZDXZCommon eszd = new EssZDXZCommon();
					eszd.setIdtag(idTag);
					returnList.add(eszd);
				}
				//清理总数据
				for(int i = 0; i < returnList.size(); i++) {
					EssZDXZCommon eszn = returnList.get(i);
					int idTag = eszn.getIdtag();
					Iterator<EssTag> it = etList.iterator();
					while(it.hasNext()) {
						EssTag et = it.next();
						int id = et.getId();
						if(idTag == id) {
							String tagName = et.getEsIdentifier();
							eszn.setTagName(tagName);
							it.remove();
						}
					}
				}
			}
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		JSONObject obj = new JSONObject();
		returnMap.put("yuanData", etList);
		returnMap.put("targetData", returnList);
		returnMap.put("id_tag",idTagStr);
		obj.put("data", returnMap);
		toPage(obj.toString());
	}
	
	/**
	 * 保存字段值选中数据
	 */
	public void saveofZDZData() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idstructure");
		String jsonArrStr = getRequest().getParameter("jsonArrStr");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.dataTempService.saveOfZDZData(list,structureId);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到代码值页面
	 * @return
	 */
	public String toShowDMZPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowDMZJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//1.获取列表展示字段
		map.put("tableFlag","fieldCode");
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZDXZDataList(map);
		if(abstructList != null && !abstructList.isEmpty() && abstructList.get(0) != null) {
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTag = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTag == id) {
						it.remove();
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(abstructList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowDMZJsp";
	}
	
	/**
	 * 保存代码值选中的数据
	 */
	public void saveofDMZData() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idstructure");
		String jsonArrStr = getRequest().getParameter("jsonArrStr");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		String tableFlag = "fieldCode";
		boolean flag = this.dataTempService.saveofDMZData(list,tableFlag,structureId);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取字段代码和字段代码-字段值
	 */
	public void getDMZAndDMZProp() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String idTag = getRequest().getParameter("idTag");
		boolean flag = this.dataTempService.getDataOfDMZList(structureId, idTag);
		obj.put("isHave", flag);
		if(flag) {//存在字段代码-字段值数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("structureId", structureId);
			map.put("idTag", idTag);
			List<Map<String,String>> list = this.dataTempService.getFieldCodeProp(map);
			obj.put("data", list);
		}
		toPage(obj.toString());
	}
	
	/**
	 * 检查字段代码-字段值是否重复
	 */
	public void checkDMZPropIsHave() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idStructure");
		String idTag = getRequest().getParameter("idTag");
		String id = getRequest().getParameter("id");
		String tagPropValue = getRequest().getParameter("tagPropValue");
		Map<String, String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("idTag", idTag);
		map.put("id", id);
		map.put("tagPropValue", tagPropValue);
		boolean flag = this.dataTempService.checkDMZPropIsHave(map);
		if(flag) {
			obj.put("isHave", "have");
		} else {
			obj.put("isHave", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存字段代码-字段值数据
	 */
	public void saveDMZPropData() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idStructure");
		String idTag = getRequest().getParameter("idTag");
		String id = getRequest().getParameter("id");
		String tagPropValue = getRequest().getParameter("tagPropValue");
		String tagCodeValue = getRequest().getParameter("tagCodeValue");
		String description = getRequest().getParameter("description");
		Map<String, String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("idTag", idTag);
		map.put("id", id);
		map.put("tagPropValue", tagPropValue);
		map.put("tagCodeValue", tagCodeValue);
		map.put("description", description);
		boolean flag = this.dataTempService.saveDMZPropData(map);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取字段代码和字段代码-字段值
	 */
	public void justGetDMZAndDMZProp() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String idTag = getRequest().getParameter("idTag");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("idTag", idTag);
		List<Map<String,String>> list = this.dataTempService.getFieldCodeProp(map);
		obj.put("list", list);
		toPage(obj.toString());
	}
	
	/**
	 * 删除字段代码-字段值
	 */
	public void deleteDMZPropData() {
		JSONObject obj = new JSONObject();
		String idStr = getRequest().getParameter("idStr");
		boolean flag = this.dataTempService.deleteDMZPropData(idStr);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到补零页面
	 * @return
	 */
	public String toShowBLPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowBLJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getBLDataList(map);
		if(abstructList != null && !abstructList.isEmpty()) {
			//清理总数据
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTag = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTag == id) {
						String tagName = et.getEsIdentifier();
						eszn.setTagName(tagName);
						it.remove();
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(abstructList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowBLJsp";
	}
	
	/**
	 * 保存补零数据
	 */
	public void saveDataOfBL() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idstructure");
		String jsonArrStr = getRequest().getParameter("data");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.dataTempService.saveDataOfBL(list, structureId);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到组合字段页面
	 * @return
	 */
	public String toShowZHZDPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("structureId", structureId);
		String esType = "'文本类型','大文本类型'";
		map.put("esType", esType);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//无数据直接跳转返回
		if(etList == null || etList.isEmpty()) {
			getRequest().setAttribute("dataFlag", "NO");
			return "toShowZHZDJsp";
		} else {
			getRequest().setAttribute("dataFlag", "YES");
		}
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZHZDDataList(map);
		if(abstructList != null && !abstructList.isEmpty()) {
			//清理总数据
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTag = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTag == id) {
						String tagName = et.getEsIdentifier();
						eszn.setTagName(tagName);
						it.remove();
					}
				}
			}
		}
		getRequest().setAttribute("allYuanList", listToJson(etList));
		getRequest().setAttribute("abstractList", listToJson(abstructList));
		getRequest().setAttribute("structureId", structureId);
		return "toShowZHZDJsp";
	}
	
	/**
	 * 查找组合字段信息
	 */
	public void getOneZHZDData() {
		JSONObject objToPage = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String idTag = getRequest().getParameter("idTag");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		//总源数据
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//选中数据
		List<EssZDXZCommon> abstructList = this.dataTempService.getZHZDDataList(map);
		if(abstructList != null && !abstructList.isEmpty()) {
			//清理总数据 1.去除选中组合字段
			for(int i = 0; i < abstructList.size(); i++) {
				EssZDXZCommon eszn = abstructList.get(i);
				int idTagInt = eszn.getIdtag();
				Iterator<EssTag> it = etList.iterator();
				while(it.hasNext()) {
					EssTag et = it.next();
					int id = et.getId();
					if(idTagInt == id) {
						String tagName = et.getEsIdentifier();
						eszn.setTagName(tagName);
						it.remove();
					}
				}
			}
		}
		map.put("idTag", idTag);
		List<EssZDXZCommon> returnLst = new ArrayList<EssZDXZCommon>();
		//单条选中数据
		List<EssZDXZCommon> abstructListOne = this.dataTempService.getZHZDDataList(map);
		if(abstructListOne != null && !abstructListOne.isEmpty() && abstructListOne.get(0) != null) {
			EssZDXZCommon zc = abstructListOne.get(0);
			String tagIds = zc.getTagIds();
			String[] tagIdsArr = tagIds.split(",");
			for(int i = 0; i < tagIdsArr.length; i++) {
				String obj = tagIdsArr[i];
				String[] objArr = obj.split("\\|");
				String flag = objArr[1];
				EssZDXZCommon exz = new EssZDXZCommon();
				if("false".equals(flag) ) {
					exz.setTagName(objArr[0]);
					returnLst.add(exz);
					continue;
				} else {
					int idTagInt = Integer.valueOf(objArr[0]);
					exz.setIdtag(idTagInt);
					Iterator<EssTag> it = etList.iterator();
					while(it.hasNext()) {
						EssTag et = it.next();
						int id = et.getId();
						if(idTagInt == id) {
							exz.setTagName(et.getEsIdentifier());
							it.remove();
						}
					}
					returnLst.add(exz);
				}
			}
		}
		objToPage.put("etList", etList);
		objToPage.put("commonLst", returnLst);
		toPage(objToPage.toString());
	}
	
	/**
	 * 保存组合字段数据
	 */
	public void saveDataOfZHZD() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("idstructure");
		String jsonArrStr = getRequest().getParameter("jsonArrStr");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.dataTempService.saveDataOfZHZD(list, structureId);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 检查后获取替换值数据
	 */
	public void checkAndGetZHZDProp() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String childrenTagId = getRequest().getParameter("tagId");
		String tagId = getRequest().getParameter("parentTagId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("idTag", tagId);
		//单条选中数据
		List<EssZDXZCommon> lstOne = this.dataTempService.getZHZDDataList(map);
		if(lstOne != null && !lstOne.isEmpty() && lstOne.size()>0) {
			String tagIds = lstOne.get(0).getTagIds();
			String[] tagidsArr = tagIds.split(",");
			List<String> lst = new ArrayList<String>();
			for(int i = 0; i < tagidsArr.length; i++) {
				String tagid = tagidsArr[i];
				String[] tagidArr = tagid.split("\\|");
				String flag = tagidArr[1];
				if("true".equals(flag)) {
					lst.add(tagidArr[0]);
				}
			}
			if(lst.contains(childrenTagId)) {
				obj.put("isHave", "YES");
				map.put("childrenTagId", childrenTagId);
				List<Map<String,String>> list = this.dataTempService.getZHZDPropDataList(map);
				obj.put("data", list);
			} else {
				obj.put("isHave", "NO");
			}
		} else {
			obj.put("isHave", "NO");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取组合字段替代值
	 */
	public void getZHZDPropDataJust() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String childrenTagId = getRequest().getParameter("childrenId");
		String tagId = getRequest().getParameter("parentId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("idTag", tagId);
		map.put("childrenTagId", childrenTagId);
		List<Map<String,String>> list = this.dataTempService.getZHZDPropDataList(map);
		obj.put("data", list);
		toPage(obj.toString());
	}
	
	/**
	 * 验证组合字段替代值被替代值是否存在重复
	 */
	public void checkZHZDPropIsHave() {
		JSONObject obj = new JSONObject();
		String idStructure = getRequest().getParameter("idStructure");
		String parentTagId = getRequest().getParameter("parentTagId");
		String tagId = getRequest().getParameter("tagId");
		String prePropValue = getRequest().getParameter("prePropValue");
		String id = getRequest().getParameter("id");
		Map<String, String> map = new HashMap<String,String>();
		map.put("idStructure", idStructure);
		map.put("parentTagId", parentTagId);
		map.put("tagId", tagId);
		map.put("prePropValue", prePropValue);
		map.put("id", id);
		boolean flag = this.dataTempService.checkZHZDPropIsHave(map);
		if(flag) {
			obj.put("isHave", "have");
		} else {
			obj.put("isHave", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除组合字段替代值数据
	 */
	public void deleteZHZDPropData() {
		JSONObject obj = new JSONObject();
		String idStr = getRequest().getParameter("idStr");
		boolean flag = this.dataTempService.deleteZHZDPropData(idStr);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存组合字段替代值数据
	 */
	public void saveZHZDPropData() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String idStructure = getRequest().getParameter("idStructure");
		String parentTagId = getRequest().getParameter("parentTagId");
		String tagId = getRequest().getParameter("tagId");
		String prePropValue = getRequest().getParameter("prePropValue");
		String propValue = getRequest().getParameter("propValue");
		String description = getRequest().getParameter("description");
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("idStructure", idStructure);
		map.put("parentTagId", parentTagId);
		map.put("tagId", tagId);
		map.put("prePropValue", prePropValue);
		map.put("propValue", propValue);
		map.put("oper", "替换");
		map.put("description", description);
		boolean flag = this.dataTempService.saveZHZDPropData(map);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到鉴定页面
	 * @return
	 */
	public String toShowJDPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		//获取一条鉴定数据
		Map<String,String> onlyData = this.dataTempService.getDataOfJD(structureId);
		if(onlyData != null && !onlyData.isEmpty()) {
			getRequest().setAttribute("tagIdSE", onlyData.get("tagIdSE"));
			getRequest().setAttribute("tagIdSection", onlyData.get("tagIdSection"));
			List<Map<String,String>> lstMap = this.dataTempService.getCheckUpPropData(map);
			if(lstMap != null && !lstMap.isEmpty()) {
				getRequest().setAttribute("tableList", lstMap);
			}
		}
		getRequest().setAttribute("etList", etList);
		getRequest().setAttribute("structureId", structureId);
		return "toShowJDJsp";
	}
	
	/**
	 * 保存鉴定数据
	 */
	public void saveDataOfJD() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String tagIdSE = getRequest().getParameter("tagIdSE");
		String tagIdSection = getRequest().getParameter("tagIdSection");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("tagIdSE", tagIdSE);
		map.put("tagIdSection", tagIdSection);
		boolean flag = this.dataTempService.saveDataOfJD(map);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存鉴定的新增数据
	 */
	public void saveAddDataOfJD() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String tagKey = getRequest().getParameter("tagKey");
		String tagValue = getRequest().getParameter("tagValue");
		String esorder = getRequest().getParameter("esorder");
		String id = getRequest().getParameter("id");
		//获取一条鉴定数据
		Map<String,String> onlyData = this.dataTempService.getDataOfJD(structureId);
		if(onlyData != null && !onlyData.isEmpty()) {
			String tagIdSE = onlyData.get("tagIdSE");
			String tagIdSection = onlyData.get("tagIdSection");
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("structureId",structureId);
			paramMap.put("tagKey",tagKey);
			paramMap.put("id",id);
			boolean flag = this.dataTempService.checkDataRepeat(paramMap);
			if(flag) {
				obj.put("dataFlag", "haveRepeat");
			} else {
				obj.put("dataFlag", "OK");
				Map<String,String> saveMap = new HashMap<String,String>(); 
				if(id == null || "".equals(id)) {
					//insert
					saveMap.put("structureId", structureId);
					saveMap.put("tagKey", tagKey);
					saveMap.put("tagValue", tagValue);
					saveMap.put("esorder", esorder);
					saveMap.put("tagIdSE", tagIdSE);
					saveMap.put("tagIdSection", tagIdSection);
					boolean saveFlag = this.dataTempService.insertDataOfJDKeyValue(saveMap);
					if(saveFlag) {
						obj.put("saveFlag", "success");
					} else {
						obj.put("saveFlag", "failed");
					}
				} else {
					//update
					saveMap.put("id", id);
					saveMap.put("structureId", structureId);
					saveMap.put("tagKey", tagKey);
					saveMap.put("tagValue", tagValue);
					saveMap.put("esorder", esorder);
					saveMap.put("tagIdSE", tagIdSE);
					saveMap.put("tagIdSection", tagIdSection);
					boolean saveFlag = this.dataTempService.updateDataOfJDKeyValue(saveMap);
					if(saveFlag) {
						obj.put("saveFlag", "success");
					} else {
						obj.put("saveFlag", "failed");
					}
				}
			}
		} else {
			obj.put("dataFlag","noMainData");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 获取鉴定中的数据
	 */
	public void getTableDataOfJD() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		//获取一条鉴定数据
		List<Map<String,String>> lstMap = this.dataTempService.getCheckUpPropData(map);
		if(lstMap != null && !lstMap.isEmpty()) {
			obj.put("dataFlag", "have");
			obj.put("data", lstMap);
		} else {
			obj.put("dataFlag", "lack");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除鉴定数据
	 */
	public void deleteTableDataOfJD() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		//获取一条鉴定数据
		boolean flag = this.dataTempService.deleteTableDataOfJD(ids);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	

	/**
	 * 跳转到默认值页面
	 * @return
	 */
	public String toShowMRZPage() {
		String structureId = getRequest().getParameter("structureId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("isNotSystem", "1");
		List<EssTag> etList = dataTempService.getEssTagList(map,null,null);	
		//查询默认值
		List<MrzPojo> mrzList = this.dataTempService.searchMRZ(map,null,null);
		List<String[]> dataList = new ArrayList<String[]>();
		for(int i=0;i<etList.size();i++){
			int tagid = etList.get(i).getId();
			String[] aa = new String[5];
			aa[0]=structureId+"";
			aa[1]=tagid+"";
			aa[2]=etList.get(i).getEsIdentifier()+"";
			String mrz = "";
			String type = "1";
			for(int j=0;j<mrzList.size();j++){
				int idtag = mrzList.get(j).getIdtag();
				if(tagid==idtag){
					mrz= mrzList.get(j).getMrz();
					type= mrzList.get(j).getType();
				}
			}
			aa[3]= mrz;
			aa[4]= type;
			dataList.add(aa);
		}
		getRequest().setAttribute("dataList", dataList);
		getRequest().setAttribute("structureId", structureId);
		return "toShowMRZJsp";
	}
	
	/**
	 * 新增或者修改默认值
	 * @return
	 */
	public void updateMRZ() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String tagId = getRequest().getParameter("tagId");
		String mrz = getRequest().getParameter("mrz");
		String type = getRequest().getParameter("type");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);//结构id
		map.put("tagId", tagId);//字段id
		//先判断是否有记录
		List<MrzPojo> mrzList = this.dataTempService.searchMRZ(map,null,null);
		String updateSql = "";
		if(mrzList!=null&&mrzList.size()>0){
			updateSql = " update t_archive_template_mrz set mrz='"+mrz+"',type='"+type+"' where idstructure= "+structureId+" and idtag="+tagId;
		}else{
			updateSql = " insert into t_archive_template_mrz(idstructure,idtag,mrz,type) values("+structureId+","+tagId+",'"+mrz+"','"+type+"')";
		}
		//然后再更新
		boolean flag = this.dataTempService.updateMrz(updateSql);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除默认值
	 * @return
	 */
	public void deleteMRZ() {
		JSONObject obj = new JSONObject();
		String structureId = getRequest().getParameter("structureId");
		String tagId = getRequest().getParameter("tagId");
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);//结构id
		map.put("tagId", tagId);//字段id
		String deleteSql = " delete from t_archive_template_mrz where idstructure="+ structureId+" and idtag="+tagId;
		boolean flag = this.dataTempService.deleteMrz(deleteSql);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
}
