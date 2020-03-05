package cn.com.trueway.archives.templates.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asprise.util.tiff.i;
import com.lowagie.text.DocumentException;

import cn.com.trueway.archives.templates.pojo.EssPropValue;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.service.DataManageService;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.archives.templates.service.MetaDataService;
import cn.com.trueway.archives.templates.service.StructureService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.archives.using.util.FtpUtil;
import net.sf.json.JSONObject;

public class DataManageAction extends BaseAction {
	
	private static final long serialVersionUID = -9043175135581417337L;
	private DataTempService dataTempService;
	private StructureService structureService;
	private DataManageService dataManageService;
	private MetaDataService metaDataService;
	private File file; 
	private String fileFileName; 
	private String fileContentType;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

	
	public DataManageService getDataManageService() {
		return dataManageService;
	}


	public void setDataManageService(DataManageService dataManageService) {
		this.dataManageService = dataManageService;
	}


	public MetaDataService getMetaDataService() {
		return metaDataService;
	}


	public void setMetaDataService(MetaDataService metaDataService) {
		this.metaDataService = metaDataService;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	/**
	 * 数据管理主页面
	 * */
	public String toDataManageMainPage(){
		
		return "toDataManagePortalPage";
	}
	
	/**
	 * 数据管理--节点树页面
	 * */
	public String toDataTreePage(){
		String businessId = getRequest().getParameter("businessId");//业务类型
		String currId = getRequest().getParameter("currId");//当前选中节点id
		//获得目录树
		Map<String,String> map = new HashMap<String, String>();
		map.put("business", businessId);
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
		return "toDataTreePage";
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
	 * 数据管理  数据编辑主界面
	 * */
	public String toEditDataMainPage(){
		String structureId = getRequest().getParameter("structureId");
		String treeId = getRequest().getParameter("treeId");
		Map<String,String> map = new HashMap<String, String>();
		//String treeNodeid = treeId.split("-")[0];
		//获取节点信息
		map.put("id", treeId);
		List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
		EssTree et = data.get(0);
		
		//获取子结构信息
		map.clear();
		map.put("structureId", et.getIdStructure()+"");
		map.put("treeId", et.getId()+"");//哪个树下面的结构
		map.put("type", "dg");//递归查询
		List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
		
		String projectStructureId="";
		String fileStructureId="";
		String innerFileStructureId="";
		String layout ="";//3:展示三层结构的数据  2：展示两层结构   1：展示一层结构
		for(EssStructure es:stList) {
			if("project".equals(es.getEsType())){
				projectStructureId=es.getId()+"";
				if(structureId.equals(es.getId()+"")){
					layout="3";
				}
			}else if("file".equals(es.getEsType())){
				fileStructureId=es.getId()+"";
				if(structureId.equals(es.getId()+"")){
					layout="2";
				}
			}else if("innerFile".equals(es.getEsType())){
				innerFileStructureId=es.getId()+"";
				if(structureId.equals(es.getId()+"")){
					layout="1";
				}
			}
		}
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("projectStructureId", projectStructureId);
		getRequest().setAttribute("fileStructureId", fileStructureId);
		getRequest().setAttribute("innerFileStructureId", innerFileStructureId);
		getRequest().setAttribute("layout", layout);
		return "toDataManageMainPage";
	}
	
	/**
	 * 各个结构的 数据（列表）页面
	 * */
	public String toStructureDataPage(){
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");//当前结构id
		String projectStructureId = getRequest().getParameter("projectStructureId");
		String fileStructureId = getRequest().getParameter("fileStructureId");
		String innerFileStructureId = getRequest().getParameter("innerFileStructureId");
		String parentId = getRequest().getParameter("parentId");//父级结构数据的id
		String flag = getRequest().getParameter("flag");//直接查全部，不需要更具父级id来查数据
		String keyWord = getRequest().getParameter("keyWord");//检索关键词
		String zhcxCondition = getRequest().getParameter("zhcxCondition");//综合查询
		String zhcxConditionSql = getRequest().getParameter("zhcxConditionSql");//综合查询条件
		String row = getRequest().getParameter("row");//综合查询页面的行数
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		if(structureId.equals(projectStructureId)){
			getRequest().setAttribute("type", "2");//用来控制页面数据点击后 触发下级数据的标记；2：项目级数据点击后 查询案卷级  1：案卷级数据单击后查询卷内级
			getRequest().setAttribute("refreshFlag", "3");//用来控制页面数据新增数据后 刷新父级页面用的
		}else if(structureId.equals(fileStructureId)){
			getRequest().setAttribute("type", "1");
			getRequest().setAttribute("refreshFlag", "2");
		}else if(structureId.equals(innerFileStructureId)){
			getRequest().setAttribute("type", "0");
			getRequest().setAttribute("refreshFlag", "1");
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
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
		/*for(int i = 0; i < etList1.size();i++){
			if(etList1.get(i).getEsOrder() < 101 || etList1.get(i).getEsOrder() > 111){
				int j = 0;
				if(gridList != null){
					for(; j < gridList.size();j++){
						int id = etList1.get(i).getId();
						int tagId = gridList.get(j).getIdtag();
						if(id == tagId){
							break;
						}
					}
					if(j < gridList.size()){
						etList.add(etList1.get(i));
					}
				}
			}else{
				etList.add(etList1.get(i));
			}
		}*/
		Map<String,Object> mapp = new HashMap<String, Object>();
		String conSql="";
		if(zhcxConditionSql!=null&&!"".equals(zhcxConditionSql)){
			conSql+=" and "+zhcxConditionSql;
		}
		if("all".equals(flag)){//主结构查询数据，子结构不查询
			conSql+=" and tree_nodeid= '"+treeId+"'";
			mapp.put("showField", etList);
			mapp.put("orderSql", "");
			mapp.put("conSql", conSql);
			mapp.put("keyWord", keyWord);
			mapp.put("tableName", "ESP_"+structureId);
			
			int count = dataManageService.getStructureDataCount(mapp);
			Paging.setPagingParams(getRequest(), pageSize, count);
			List<Map<String,Object>> list = dataManageService.getStructureDataList(mapp,Paging.pageSize, Paging.pageIndex);
			getRequest().setAttribute("list", list);
		}else{
			if(parentId!=null&&!"".equals(parentId)){//勾选了父结构，查询子结构
				conSql+=" and id_parent_package="+parentId+" and tree_nodeid="+treeId;
				mapp.put("showField", etList);
				mapp.put("orderSql", "");
				mapp.put("conSql", conSql);
				mapp.put("keyWord", keyWord);
				mapp.put("tableName", "ESP_"+structureId);
				
				int count = dataManageService.getStructureDataCount(mapp);
				Paging.setPagingParams(getRequest(), pageSize, count);
				List<Map<String,Object>> list = dataManageService.getStructureDataList(mapp,Paging.pageSize, Paging.pageIndex);
				getRequest().setAttribute("list", list);
			}else{
				Paging.setPagingParams(getRequest(), pageSize, 0);
			}
		}
		
		
		getRequest().setAttribute("flag", flag);
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("etList", etList);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("projectStructureId", projectStructureId);
		getRequest().setAttribute("fileStructureId", fileStructureId);
		getRequest().setAttribute("innerFileStructureId", innerFileStructureId);
		getRequest().setAttribute("keyWord", keyWord);
		getRequest().setAttribute("zhcxCondition", zhcxCondition);
		getRequest().setAttribute("zhcxConditionSql", zhcxConditionSql);
		getRequest().setAttribute("row", row);
		return "toStructureDataPage";
	}
	
	/**
	 * 各个结构的 数据 （新增）页面
	 * */
	public String toAddStructureDataPage(){
		String treeId = getRequest().getParameter("treeId");
		String parentId = getRequest().getParameter("parentId");//父级结构数据的id
		String structureId = getRequest().getParameter("structureId");//当前结构id
		String projectStructureId = getRequest().getParameter("projectStructureId");
		String fileStructureId = getRequest().getParameter("fileStructureId");
		String innerFileStructureId = getRequest().getParameter("innerFileStructureId");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
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
		/*for(int i = 0; i < etList1.size();i++){
			if(etList1.get(i).getEsOrder() < 101 || etList1.get(i).getEsOrder() > 111){
				int j = 0;
				if(gridList != null){
					for(; j < gridList.size();j++){
						int id = etList1.get(i).getId();
						int tagId = gridList.get(j).getIdtag();
						if(id == tagId){
							break;
						}
					}
					if(j < gridList.size()){
						etList.add(etList1.get(i));
					}
				}
			}else{
				etList.add(etList1.get(i));
			}
		}*/
		//获取字段代码值规则
		map.put("tableFlag","fieldCode");
		List<EssZDXZCommon> abstructList = dataTempService.getZDXZDataList(map);
		
		Map<String,List<Map<String,String>>> map_DMZ = new HashMap<String, List<Map<String,String>>>();
		Map<String,List<EssPropValue>> map_YSJ = new HashMap<String, List<EssPropValue>>();
		for(int i=0;i<etList.size();i++){
			String tagId = etList.get(i).getId()+"";
			String idMetadata = etList.get(i).getIdMetadata();
			//1.代码值
			for(int j=0;abstructList!=null&&j<abstructList.size();j++){
				if(tagId.equals(abstructList.get(j).getIdtag()+"")){
					map.clear();
					map.put("structureId", structureId);
					map.put("idTag", tagId);
					List<Map<String,String>> list = this.dataTempService.getFieldCodeProp(map);
					map_DMZ.put(tagId+"_DMZ", list);
				}
			}
			//2.元数据带值
			if(idMetadata!=null&&!"".equals(idMetadata)){
				Map<String,Object> map_ysj = new HashMap<String,Object>();
				map_ysj.put("metaDataId", idMetadata);
				List<EssPropValue> list = metaDataService.getMetaDataPropValue(map_ysj);
				map_YSJ.put(tagId+"_YSJ", list);
				
			}
		}
		
		
		getRequest().setAttribute("map_DMZ", map_DMZ);
		getRequest().setAttribute("map_YSJ", map_YSJ);
		
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("etList", etList);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("projectStructureId", projectStructureId);
		getRequest().setAttribute("fileStructureId", fileStructureId);
		getRequest().setAttribute("innerFileStructureId", innerFileStructureId);
		return "toAddStructureDataPage";
	}
	
	/**
	 * 各个结构的 数据 （编辑）页面
	 * */
	public String toEditStructureDataPage(){
		String id = getRequest().getParameter("id");
		String treeId = getRequest().getParameter("treeId");
		String parentId = getRequest().getParameter("parentId");//父级结构数据的id
		String structureId = getRequest().getParameter("structureId");//当前结构id
		String projectStructureId = getRequest().getParameter("projectStructureId");
		String fileStructureId = getRequest().getParameter("fileStructureId");
		String innerFileStructureId = getRequest().getParameter("innerFileStructureId");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("tableFlag","grid");
		//List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
		List<EssTag> etList1 = dataTempService.getEssTagList(map,null,null);
		List<EssTag> etList = new ArrayList<EssTag>();
		List<EssZDXZCommon> gridList = this.dataTempService.getZDXZDataList(map);
		for(int i = 0; i < gridList.size()&&gridList!=null;i++){
			int tagId = gridList.get(i).getIdtag();
			for(int j = 0; j < etList1.size()&&etList1!=null;j++){
				int id1 = etList1.get(j).getId();
				if(id1 == tagId){
					etList.add(etList1.get(j));
					//break;
				}
			}
		}
		/*for(int i = 0; i < etList1.size();i++){
			if(etList1.get(i).getEsOrder() < 101 || etList1.get(i).getEsOrder() > 111){
				int j = 0;
				if(gridList != null){
					for(; j < gridList.size();j++){
						int eid = etList1.get(i).getId();
						int tagId = gridList.get(j).getIdtag();
						if(eid == tagId){
							break;
						}
					}
					if(j < gridList.size()){
						etList.add(etList1.get(i));
					}
				}
			}else{
				etList.add(etList1.get(i));
			}
		}*/
		
		Map<String,Object> mapp = new HashMap<String, Object>();
		mapp.put("id", id);
		mapp.put("tableName", "ESP_"+structureId);
		mapp.put("showField", etList);
		List<Map<String,Object>> dataMap = dataManageService.getStructureData(mapp);
		getRequest().setAttribute("dataMap", dataMap.get(0));
		
		getRequest().setAttribute("id", id);
		getRequest().setAttribute("treeId", treeId);
		getRequest().setAttribute("parentId", parentId);
		getRequest().setAttribute("etList", etList);
		getRequest().setAttribute("structureId", structureId);
		getRequest().setAttribute("projectStructureId", projectStructureId);
		getRequest().setAttribute("fileStructureId", fileStructureId);
		getRequest().setAttribute("innerFileStructureId", innerFileStructureId);
		return "toEditStructureDataPage";
	}
	
	/**
	 * 保存 对应结构表数据
	 * */
	public void saveStructureData() throws IOException{
		String id = getRequest().getParameter("id");
		String treeId = getRequest().getParameter("treeId");
		String parentId = getRequest().getParameter("parentId");//父级结构数据的id
		String structureId = getRequest().getParameter("structureId");//当前结构id
		Map<String,String> map = new HashMap<String, String>();
		try {
			if(id!=null&&!"".equals(id)){//编辑保存
				map.put("structureId", structureId);
				List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
				
				String sql=" update  ESP_"+structureId+" set ";
				String conditionsql = "";
				for(int i=0;i<etList.size();i++){
					if(i==etList.size()-1){
						conditionsql+=" C"+etList.get(i).getId()+"='"+getRequest().getParameter("C"+etList.get(i).getId())+"'";
					}else{
						conditionsql+=" C"+etList.get(i).getId()+"='"+getRequest().getParameter("C"+etList.get(i).getId())+"',";
					}
				}
				sql=sql+conditionsql+" where id="+id;
				dataTempService.updateNativeSql(sql);
			}else{//新增保存
				//获取节点信息
				map.put("id", treeId);
				List<EssTree> data = dataTempService.getTreeByMap(map,null,null);
				EssTree et = data.get(0);
				//获取结构层次信息(递归获取父级结构)
				map.clear();
				map.put("structureId", structureId);
				map.put("type", "-dg");//反递归查询     查询下级   dg   -dg反递归 查询上级
				List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
				String espath="";
				String id_parent_structure = "";
				String id_parent_package = "";
				
				if(stList!=null&&stList.size()==1){//本身就是最顶层结构
					id_parent_structure = "1";
					id_parent_package = treeId;
					espath = et.getEsPath();
				}else{
					id_parent_structure = stList.get(stList.size()-2).getId()+"";
					if("".equals(parentId)){
						id_parent_package = "-1";
						String ss="";
						for(int i=0;i<stList.size()-1;i++){//减1是为了排除自己
							ss+="/@"+stList.get(i).getId();
						}	
						espath = et.getEsPath()+ss;
					}else{
						id_parent_package = parentId;
						String sql = "select espath,1 from ESP_"+id_parent_structure+" where id="+parentId;
						List<Object[]> list = dataTempService.excuteNativeSql(sql);
						espath = list.get(0)[0]+"/"+parentId+"@"+id_parent_structure;
					}	
				}
				
				map.clear();
				map.put("structureId", structureId);
				List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
				String sql=" insert into ESP_"+structureId;
				String column1="id,espath,id_parent_structure,id_parent_package,tree_nodeid";
				String value1="SEQ_ESP_"+structureId+".nextval,'"+espath+"',"+id_parent_structure+","+id_parent_package+","+treeId;
				String column2="";
				String value2="";
				for(int i=0;i<etList.size();i++){
					column2+=",C"+etList.get(i).getId();
					value2+=",'"+getRequest().getParameter("C"+etList.get(i).getId())+"'";
				}
				sql=sql+" ("+column1+column2+") values "+" ( "+value1+value2+" ) ";
				dataTempService.updateNativeSql(sql);
			}
			
			this.outWirter("success");
		} catch (Exception e) {
			this.outWirter("false");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除 对应结构表的数据
	 * */
	public void deleteStructureData() throws IOException{
		String treeId = getRequest().getParameter("treeId");
		String structureId = getRequest().getParameter("structureId");//当前结构id
		String ids = getRequest().getParameter("ids");
		String flag = getRequest().getParameter("flag");//1：只删除所挂接的电子文件条目
		Map<String,String> map = new HashMap<String, String>();
		try {	
			//获取结构层次信息(递归获取子级结构)
			map.clear();
			map.put("structureId", structureId);
			map.put("type", "dg");//反递归查询        查询下级   dg   -dg反递归 查询上级
			List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
			String type = stList.get(0).getEsType();
			if("1".equals(flag)){
				if("project".equals(type)){
					//查询案卷级数据
					String sql1= " select id,1 from ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					List<Object[]> fileList = dataTempService.excuteNativeSql(sql1);
					String ids_file = "";
					for(int a=0;a<fileList.size();a++){
						if(a==fileList.size()-1){
							ids_file+=fileList.get(a)[0];
						}else{
							ids_file+=fileList.get(a)[0]+",";
						}	
					}
					if(!"".equals(ids_file)){
						//查询卷内级数据
						String sql2= " select id,1 from ESP_"+stList.get(2).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_file+")";
						List<Object[]> innerFileList = dataTempService.excuteNativeSql(sql2);
						String ids_innerFile = "";
						for(int a=0;a<innerFileList.size();a++){
							if(a==innerFileList.size()-1){
								ids_innerFile+=innerFileList.get(a)[0];
							}else{
								ids_innerFile+=innerFileList.get(a)[0]+",";
							}	
						}
						if(!"".equals(ids_innerFile)){
							//删除文件级数据
							String sql3= " delete  ESP_"+stList.get(3).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_innerFile+")";
							dataTempService.updateNativeSql(sql3);
						}
					}			
				}else if("file".equals(type)){
					//查询卷内级数据
					String sql1= " select id,1 from ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					List<Object[]> innerFileList = dataTempService.excuteNativeSql(sql1);
					String ids_innerFile = "";
					for(int a=0;a<innerFileList.size();a++){
						if(a==innerFileList.size()-1){
							ids_innerFile+=innerFileList.get(a)[0];
						}else{
							ids_innerFile+=innerFileList.get(a)[0]+",";
						}	
					}
					if(!"".equals(ids_innerFile)){
						//删除文件级数据
						String sql2= " delete  ESP_"+stList.get(2).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_innerFile+")";
						dataTempService.updateNativeSql(sql2);
					}
				}else if("innerFile".equals(type)){
					//删除文件级数据
					String sql1= " delete  ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					dataTempService.updateNativeSql(sql1);
				}
			}else{
				if("project".equals(type)){
					//查询案卷级数据
					String sql1= " select id,1 from ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					List<Object[]> fileList = dataTempService.excuteNativeSql(sql1);
					String ids_file = "";
					for(int a=0;a<fileList.size();a++){
						if(a==fileList.size()-1){
							ids_file+=fileList.get(a)[0];
						}else{
							ids_file+=fileList.get(a)[0]+",";
						}	
					}
					if(!"".equals(ids_file)){
						//查询卷内级数据
						String sql2= " select id,1 from ESP_"+stList.get(2).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_file+")";
						List<Object[]> innerFileList = dataTempService.excuteNativeSql(sql2);
						String ids_innerFile = "";
						for(int a=0;a<innerFileList.size();a++){
							if(a==innerFileList.size()-1){
								ids_innerFile+=innerFileList.get(a)[0];
							}else{
								ids_innerFile+=innerFileList.get(a)[0]+",";
							}	
						}
						if(!"".equals(ids_innerFile)){
							//删除文件级数据
							String sql3= " delete  ESP_"+stList.get(3).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_innerFile+")";
							dataTempService.updateNativeSql(sql3);
							//删除卷内级数据
							sql2= " delete  ESP_"+stList.get(2).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_file+")";
							dataTempService.updateNativeSql(sql2);
							//删除案卷级数据
							sql1= " delete  ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
							dataTempService.updateNativeSql(sql1);
							//删除项目级数据
							String sql0="  delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
							dataTempService.updateNativeSql(sql0);
						}else{
							//删除案卷级数据
							sql1= " delete  ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
							dataTempService.updateNativeSql(sql1);
							//删除项目级数据
							String sql0="  delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
							dataTempService.updateNativeSql(sql0);
						}
					}else{
						//删除项目级数据
						String sql0="  delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
						dataTempService.updateNativeSql(sql0);
					}	
				}else if("file".equals(type)){
					//查询卷内级数据
					String sql1= " select id,1 from ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					List<Object[]> innerFileList = dataTempService.excuteNativeSql(sql1);
					String ids_innerFile = "";
					for(int a=0;a<innerFileList.size();a++){
						if(a==innerFileList.size()-1){
							ids_innerFile+=innerFileList.get(a)[0];
						}else{
							ids_innerFile+=innerFileList.get(a)[0]+",";
						}	
					}
					if(!"".equals(ids_innerFile)){
						//删除文件级数据
						String sql2= " delete  ESP_"+stList.get(2).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids_innerFile+")";
						dataTempService.updateNativeSql(sql2);
						//删除卷内级数据
						sql1= " delete  ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
						dataTempService.updateNativeSql(sql1);
						//删除案卷级数据
						String sql0= " delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
						dataTempService.updateNativeSql(sql0);
					}else{
						//删除案卷级数据
						String sql0= " delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
						dataTempService.updateNativeSql(sql0);
					}
					
				}else if("innerFile".equals(type)){
					//删除文件级数据
					String sql1= " delete  ESP_"+stList.get(1).getId()+" where tree_nodeid="+treeId+" and id_parent_package in ("+ids+")";
					dataTempService.updateNativeSql(sql1);
					//删除卷内级数据
					String sql0= " delete  ESP_"+stList.get(0).getId()+" where tree_nodeid="+treeId+" and id in ("+ids+")";
					dataTempService.updateNativeSql(sql0);
				}
			}
			this.outWirter("success");
		} catch (Exception e) {
			this.outWirter("false");
			e.printStackTrace();
		}
	}

	/**
	 * 转向综合查询 的动态页面
	 * */
	public String toShowZhcxPage(){
		String structureId = getRequest().getParameter("structureId");//当前结构id
		String zhcxCondition = getRequest().getParameter("zhcxCondition");//带入之前操作的条件
		String row = getRequest().getParameter("row");//带入之前操作的条件
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
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
		/*for(int i = 0; i < etList1.size();i++){
			if(etList1.get(i).getEsOrder() < 101 || etList1.get(i).getEsOrder() > 111){
				int j = 0;
				if(gridList != null){
					for(; j < gridList.size();j++){
						int id = etList1.get(i).getId();
						int tagId = gridList.get(j).getIdtag();
						if(id == tagId){
							break;
						}
					}
					if(j < gridList.size()){
						etList.add(etList1.get(i));
					}
				}
			}else{
				etList.add(etList1.get(i));
			}
		}*/
		
		
		String tagstr="";
		for(int j=0;j<etList.size();j++){
			tagstr+=etList.get(j).getId()+","+etList.get(j).getEsIdentifier()+";";
		}
		getRequest().setAttribute("tagstr", tagstr);
		
		Map<String,String> columnMap = new HashMap<String, String>();
		if(zhcxCondition!=null&&!"".equals(zhcxCondition)){
			String[] arr = zhcxCondition.split(";");
			for(int i=0;i<arr.length;i++){
				if(arr[i].split(":").length==1){
					columnMap.put(arr[i].split(":")[0], "");
				}else{
					columnMap.put(arr[i].split(":")[0], arr[i].split(":")[1]);
				}
			}
		}
		if(row!=null&&!"".equals(row)){
			getRequest().setAttribute("row", row);
		}
		getRequest().setAttribute("columnMap", columnMap);
		getRequest().setAttribute("tags", etList);
		getRequest().setAttribute("structureId", structureId);
		
		return "toShowZhcxPage";
	}
	

	/**
	 * 检验综合查询的正确性以及数据是否存在
	 * */
	public void checkZhcxCondition(){
		String row = getRequest().getParameter("row");
		String structureId = getRequest().getParameter("structureId");
		String zhconditionsql = "";
		JSONObject obj = new JSONObject();
		StringBuffer str = new StringBuffer();
		Map<String,String> columnMap = new HashMap<String, String>();
		if(row!=null&&!"".equals(row)){
			int count = Integer.valueOf(row);
			getRequest().setAttribute("count", count);
			List<String> countlist = new ArrayList<String>();
			
			for(int i=1;i<count;i++){			
				String keyword = getRequest().getParameter("keyWord"+i);
				String lkh = getRequest().getParameter("lkh"+i);
				String column = getRequest().getParameter("column"+i);
				String com = getRequest().getParameter("com"+i);
				String rkh = getRequest().getParameter("rkh"+i);
				String rela = getRequest().getParameter("rela"+i);
			/*	columnMap.put("keyWord"+i, keyword);
				columnMap.put("lkh"+i, lkh);
				columnMap.put("column"+i, column);
				columnMap.put("com"+i, com);
				columnMap.put("rkh"+i, rkh);
				columnMap.put("rela"+i, rela);*/
				str.append("keyWord"+i+":"+keyword+";");
				str.append("lkh"+i+":"+lkh+";");
				str.append("column"+i+":"+column+";");
				str.append("com"+i+":"+com+";");
				str.append("rkh"+i+":"+rkh+";");
				str.append("rela"+i+":"+rela+";");
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
		}
		if(!"".equals(zhconditionsql)){
			try {
				List<Object[]> list = dataTempService.excuteNativeSql(" select count(*),1 from ESP_"+structureId+" where "+zhconditionsql);
				if("0".equals(list.get(0)[0].toString())){
					obj.put("flag", "false");
					obj.put("msg", "未查到数据！");	
				}else{
					obj.put("flag", "success");
					obj.put("msg", str.toString());	
					obj.put("sql", zhconditionsql);	
				}
			} catch (Exception e) {
				obj.put("flag", "false");
				obj.put("msg", "条件格式错误，请检查条件设置！");	
				e.printStackTrace();
			}
			this.outWirter(obj.toString());
		}else{
			obj.put("flag", "false");
			obj.put("msg", "请设置查询条件！");	
			this.outWirter(obj.toString());
		}	
	}
	
	public String openDocumentDialog(){
		String pid = getRequest().getParameter("pid");//父级数据的id
		String structureId = getRequest().getParameter("structureId");
		getRequest().setAttribute("pid", pid);
		getRequest().setAttribute("structureId", structureId);
		return "uploadDocumentPage";
	}
	
	/**
	 * 
	 * 描述：手动上传电子文件
	 *	
	 */
	public String uploadAtts() throws IOException, DocumentException{
		String fileSequence = getRequest().getParameter("fileSequence");
		File attsFile = this.getFile(); // 要上传的文件
		String uploadfilename = this.getFileFileName(); // 上传文件的真实文件名
		String pid = getRequest().getParameter("pid");//父级数据的id
		String structureId = getRequest().getParameter("structureId");//父级结构id
		String nameRule = getRequest().getParameter("nameRule");//是否按照规则命名
		String type = getRequest().getParameter("type");
	
		
	
		
		//获取文件上传ftp服务器地址
		
		
		
		String realfilename="";
		if (null != attsFile && attsFile.exists()&&uploadfilename!=null){
			//FtpUtil.uploadFile(jyd_ftp_ip, Integer.valueOf(jyd_ftp_port), jyd_ftp_name, jyd_ftp_password, jyd_ftp_dirpath, fileName, input);
			
			//数据入库
			Map<String,String> map = new HashMap<String, String>();
			map.clear();
			map.put("structureId", structureId);
			map.put("type", "-dg");//反递归查询     查询下级   dg   -dg反递归 查询上级
			List<EssStructure> stList = dataTempService.getStructureList(map,null,null);
			int documentStructureId = stList.get(stList.size()-1).getId();//电子文件级结构
			
			
			//先获取父级数据 ，根据父级数据 来组织电子文件级 数据
			List<Object[]> list = dataTempService.excuteNativeSql("select espath,tree_nodeid from ESP_"+structureId+" where id="+pid);
			String espath = list.get(0)[0]+"/"+pid+"@"+structureId;
			String treeId = list.get(0)[1].toString();
			
			map.clear();
			map.put("structureId", documentStructureId+"");
			List<EssTag> etList = dataTempService.getEssTagList(map,null,null);
			String sql=" insert into ESP_"+structureId;
			String column1="id,espath,id_parent_structure,id_parent_package,tree_nodeid";
			String value1="SEQ_ESP_"+structureId+".nextval,'"+espath+"',"+structureId+","+pid+","+treeId;
			String column2="";
			String value2="";
			for(int i=0;i<etList.size();i++){
				column2+=",C"+etList.get(i).getId();
				if("文件类别".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+type+"'";
				}
				if("创建时间".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+sdf.format(new Date()) +"'";
				}
				if("原文路径".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+""+"'";
				}
				if("文件校验".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+""+"'";
				}
				if("文件大小".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+""+"'";
				}
				if("所属部门".equals(etList.get(i).getEsIdentifier())){
					value2+=",'"+""+"'";
				}
			}
			sql=sql+" ("+column1+column2+") values "+" ( "+value1+value2+" ) ";
			dataTempService.updateNativeSql(sql);
			
			realfilename="ok";
			getResponse().setContentType("text/xml");
			getResponse().setCharacterEncoding("GBK");
			PrintWriter out = getResponse().getWriter();
			out.write(realfilename);
		}
		/*//为实现套打图片 ，需要在工程目录先上传附件图片至工程中
		if (realfilename!=null&&!realfilename.equals("")) {
			realfilename=realfilename.split("/")[4];
		}
		String jarEnvAllUrl=PathUtil.getWebRoot()+"tempfile/"+realfilename;//需要复制到运行环境中的 位置
		MyUtils.copy(attsFile, new File(jarEnvAllUrl));// 完成上传文件，就是将本地文件复制到服务器上
*/		return null;
	}
}