package cn.com.trueway.archives.templates.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssModelTags;
import cn.com.trueway.archives.templates.pojo.EssStructureModel;
import cn.com.trueway.archives.templates.service.BusinessManageService;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.archives.templates.service.MetaDataService;
import cn.com.trueway.archives.templates.service.StructureService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 结构模板
 * @author lixr
 *
 */
@SuppressWarnings("unchecked")
public class StructureTempAction extends BaseAction {
	
	private static final long serialVersionUID = -8695435186487473850L;
	
	private StructureService structureService;
	
	private BusinessManageService businessManageService;
	
	private DataTempService dataTempService;
	
	private MetaDataService metaDataService;
	
	public DataTempService getDataTempService() {
		return dataTempService;
	}
	
	public void setDataTempService(DataTempService dataTempService) {
		this.dataTempService = dataTempService;
	}
	
	public BusinessManageService getBusinessManageService() {
		return businessManageService;
	}

	public void setBusinessManageService(BusinessManageService businessManageService) {
		this.businessManageService = businessManageService;
	}

	public StructureService getStructureService() {
		return structureService;
	}

	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	
	public MetaDataService getMetaDataService() {
		return metaDataService;
	}

	public void setMetaDataService(MetaDataService metaDataService) {
		this.metaDataService = metaDataService;
	}

	/**
	 * 跳转到结构模板首页
	 * @return
	 */
	public String toStructureTempPage() {
		Map<String,Object> map = new HashMap<String,Object>();
		String esIdentifier = getRequest().getParameter("esIdentifier");
		map.put("esIdentifier", esIdentifier);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = this.structureService.getStructureTempCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<EssStructureModel> list = this.structureService.getStructureTempList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("esIdentifier", esIdentifier);
		return "toStrTemplateJsp";
	}
	
	/**
	 * 跳转到结构模板新增页面
	 */
	public String toStructureTempAddPage() {
		Map<String, Object> map = new HashMap<String,Object>();
		List<EssBusiness> list = this.businessManageService.getBusinessManageList(map);
		getRequest().setAttribute("list", list);
		return "structureTempAddJsp";
	}
	
	/**
	 * 跳转到结构模板修改页面
	 * @return
	 */
	public String toStructureTempEditPage() {
		String id = getRequest().getParameter("id");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", Integer.valueOf(id));
		List<EssStructureModel> sList = this.structureService.getStructureTempList(map);
		if(sList != null && !sList.isEmpty()) {
			getRequest().setAttribute("estm", sList.get(0));
		}
		return "structureTempEditJsp";
	}
	
	/**
	 * 删除结构模板信息
	 */
	public void delStructureTemp() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		Boolean flag = this.structureService.deleteStructureByIds(ids);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存结构模板信息
	 */
	public void saveStructureTemp() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String business = getRequest().getParameter("business");
		String esIdentifier = getRequest().getParameter("esIdentifier");
		String esDescription = getRequest().getParameter("esDescription");
		String esType = getRequest().getParameter("esType");
		EssStructureModel estm = null;
		if(id != null && !"".equals(id)) {
			estm = new EssStructureModel(Integer.valueOf(id), business, esIdentifier, esDescription, esType);
		} else {
			estm = new EssStructureModel(null, business, esIdentifier, esDescription, esType);
		}
		Boolean flag = this.structureService.saveStructureTemp(estm);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到设置著录项主页面
	 * @return
	 */
	public String toSetModelTagsMainPage() {
		String modelId = getRequest().getParameter("modelId");		
		String esTitle = getRequest().getParameter("esTitle");
		String esType = getRequest().getParameter("esType");
		String onlyLook = getRequest().getParameter("onlyLook");
		getRequest().setAttribute("esTitle", esTitle);
		getRequest().setAttribute("esType", esType);
		getRequest().setAttribute("modelId", modelId);
		getRequest().setAttribute("onlyLook", onlyLook);
		return "setModelTagsMainJsp";
	}
	
	/**
	 * 跳转到设置著录项    各子结构页面
	 * @return
	 */
	public String toSetModelTagsEditPage() {
		String modelId = getRequest().getParameter("modelId");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,Object> map = new HashMap<String, Object>();
		String esType = getRequest().getParameter("esType");
		String onlyLook = getRequest().getParameter("onlyLook");
		if("document".equals(esType)){
			onlyLook="1";
		}
		map.put("modelid", Integer.valueOf(modelId));
		map.put("esType",esType);
		int count = this.structureService.getEssModelTagsCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<EssModelTags> list = this.structureService.getEssModelTagsList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("esType", esType);
		getRequest().setAttribute("modelId", modelId);
		getRequest().setAttribute("onlyLook", onlyLook);
		return "setModelTagsEditJsp";
	}
	
	/**
	 * 校验业务结构模板字段是否存在信息
	 */
	public void checkIsHaveTag() {
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		String tagName = getRequest().getParameter("tagName");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("modelId", Integer.valueOf(modelId));
		map.put("esType",esType);
		map.put("tagName",tagName);
		int count = this.structureService.getEssModelTagsCount(map);
		if(count>0){
			toPage("true");
		}else{
			toPage("false");
		}
	}
	
	/**
	 * 保存结构模板字段信息
	 */
	public void addModelTags() {
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		String str = getRequest().getParameter("str");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("modelId", Integer.valueOf(modelId));
		map.put("esType",esType);
		List<EssModelTags> list = this.structureService.getEssModelTagsList(map);
		int sort=0;
		if(list!=null&&list.size()>0){
			sort=list.get(list.size()-1).getViewOrder();//取最大排序号
		}
		
		String[] esmarr = str.split(";");
		try {
			for(int i=0;i<esmarr.length;i++){
				String[] data = esmarr[i].split(",");
				EssModelTags emt = new EssModelTags();
				emt.setModelId(modelId);
				emt.setEsType(esType);
				try {
					emt.setViewOrder(Integer.valueOf(data[0]));//排序号
				} catch (NumberFormatException e) {
					emt.setViewOrder(sort+1);
					sort++;
				}
				emt.setTagName(data[1]);//字段名
				emt.setMetaDataFullName(data[2]);//元数据
				emt.setId_MetaData(data[3]);
				emt.setTagType(data[4]);//字段类型
				emt.setTagDesc(data[5]);//字段描述
				emt.setEsLength(Integer.valueOf(data[6])+"");//字段长度
				emt.setEsDoLength(Integer.valueOf(data[7])+"");//小数位数
				emt.setEsIsEdit(data[8]);//是否编辑
				emt.setEsIsNotNull(data[9]);//是否必填
				structureService.updateModelTags(emt);
			}
			//obj.put("flag", "success");
			toPage("success");
		} catch (Exception e) {
			e.printStackTrace();
			//obj.put("flag", "false");
			toPage("false");
		}
	}
	
	/**
	 * 修改结构模板字段信息
	 */
	public void updateModelTags() {
		JSONObject obj = new JSONObject();
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		String str = getRequest().getParameter("str");
		
		String[] esmarr = str.split(";");
		try {
			for(int i=0;i<esmarr.length;i++){
				String[] data = esmarr[i].split(",");
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("modelid", Integer.valueOf(modelId));
				map.put("esType",esType);
				map.put("id",data[0]);//字段的id
				List<EssModelTags> list = this.structureService.getEssModelTagsList(map);
				if(list!=null&&list.size()>0){
					EssModelTags emt = list.get(0);
					emt.setModelId(modelId);
					emt.setEsType(esType);
					//以下的字符串里面的数据的顺序其实和新增差不多，只不过都往后移了以为，首位变成id了
					try {
						emt.setViewOrder(Integer.valueOf(data[1]));//排序号
					} catch (NumberFormatException e) {
					}
					emt.setTagName(data[2]);//字段名
					emt.setMetaDataFullName(data[3]);//元数据
					emt.setId_MetaData(data[4]);
					emt.setTagType(data[5]);//字段类型
					emt.setTagDesc(data[6]);//字段描述
					emt.setEsLength(data[7]);//字段长度
					emt.setEsDoLength(data[8]);//小数位数
					emt.setEsIsEdit(data[9]);//是否编辑
					emt.setEsIsNotNull(data[10]);//是否必填
					structureService.updateModelTags(emt);
				}
			}
			toPage("success");
		} catch (Exception e) {
			toPage("false");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除结构模板字段信息
	 */
	public void deleteModelTags() {
		String ids = getRequest().getParameter("ids");
		String deleteSql = " delete from T_ARCHIVE_MODELTAGS where id in ("+ids+")";
		dataTempService.updateNativeSql(deleteSql);
		toPage("success");
		
	}
	
	/**
	 * 跳转到结构模板匹配元数据页面
	 * @return
	 */
	public String toTempMathchMetaDataPage() {
		Map<String,Object> map = new HashMap<String, Object>();
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		map.put("modelid", Integer.valueOf(modelId));
		map.put("esType",esType);
		List<EssModelTags> list = this.structureService.getEssModelTagsList(map);
		List<Map<String,String>> nameSpaceList = this.metaDataService.getNameSpaceList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("esType", esType);
		getRequest().setAttribute("modelId", modelId);
		getRequest().setAttribute("nameSpaceList", nameSpaceList);
		return "tempMatchMetaDataJsp";
	}
	
	/**
	 * 保存结构模板匹配的数据字段
	 */
	public void saveTagsMetaData() {
		JSONObject obj = new JSONObject();
		String jsonArrStr = getRequest().getParameter("jsonArr");
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Map<String,String>> list = (List<Map<String, String>>)jsonArr;
		boolean flag = this.structureService.saveTagsMetaData(list);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "failed");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 元数据的自动对应
	 */
	public void autoMetaDataOf() {
		Map<String,Object> map = new HashMap<String, Object>();
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		String spaceName = getRequest().getParameter("spaceName");
		map.put("modelid", Integer.valueOf(modelId));
		map.put("esType",esType);
		//著录项list
		List<EssModelTags> listG = this.structureService.getEssModelTagsList(map);
		map.clear();
		map.put("nameSpaceId", nameSpaceId);
		//元数据list
		List<EssMetaData> listA = this.metaDataService.getMetaDataList(map);
		List<EssModelTags> returnList = new ArrayList<EssModelTags>();
		for(int i = 0; i < listG.size(); i++) {
			EssModelTags esmt = listG.get(i);
			String tagName = esmt.getTagName();
			for(int t = 0; t < listA.size(); t++) {
				EssMetaData esta = listA.get(t);
				String esTitle = esta.getEsTitle();
				if(tagName.equals(esTitle)) {
					String metaDataFullName = spaceName + ":" + esta.getEsIdentifier();
					esmt.setMetaDataFullName(metaDataFullName);
					esmt.setId_MetaData(esta.getId());
					returnList.add(esmt);
					continue;
				}
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("retList", returnList);
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到元数据匹配一条数据页面
	 * @return
	 */
	public String toMatchMetaDataOne() {
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,String>> nameSpaceList = this.metaDataService.getNameSpaceList(map);
		getRequest().setAttribute("nameSpaceList", nameSpaceList);
		getRequest().setAttribute("id", id);
		return "matchOneMetaDataJsp";
	}
	
	/**
	 * 检查匹配元数据是否存在重复
	 */
	public void checkIsJustOne() {
		String id = getRequest().getParameter("id");
		String modelId = getRequest().getParameter("modelId");
		String esType = getRequest().getParameter("esType");
		String metaDataFullName = getRequest().getParameter("metaDataFullName");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("modelId", modelId);
		map.put("esType", esType);
		map.put("metaDataFullName", metaDataFullName);
		int count = this.structureService.checkIsJustOne(map);
		JSONObject obj = new JSONObject();
		obj.put("count", count);
		toPage(obj.toString());
	}
}
