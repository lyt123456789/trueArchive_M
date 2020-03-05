package cn.com.trueway.archives.templates.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssPropValue;
import cn.com.trueway.archives.templates.service.MetaDataService;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import net.sf.json.JSONObject;

public class MetaDataAction extends BaseAction {
	
	private static final long serialVersionUID = -4055215071628157765L;
	
	private MetaDataService metaDataService;

	public MetaDataService getMetaDataService() {
		return metaDataService;
	}

	public void setMetaDataService(MetaDataService metaDataService) {
		this.metaDataService = metaDataService;
	}
	
	/**
	 * 跳转到命名空间
	 * @return
	 */
	public String toNameSpacePage() {
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String esTitle = getRequest().getParameter("esTitle");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("esTitle", esTitle);
		int count = this.metaDataService.getNameSpaceListCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<Map<String,String>> list = this.metaDataService.getNameSpaceList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("esTitle", esTitle);
		return "toNameSpaceJsp";
	}
	
	/**
	 * 跳转到新增命名空间
	 * @return
	 */
	public String toNameSpaceAddPage() {
		return "nameSpaceAddJsp";
	}
	
	/**
	 * 跳转到修改命名空间
	 * @return
	 */
	public String toNameSpaceEditPage() {
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<Map<String,String>> list = this.metaDataService.getNameSpaceList(map);
		if(list != null && !list.isEmpty()) {
			Map<String,String> reMap = list.get(0);
			getRequest().setAttribute("id", reMap.get("id"));
			getRequest().setAttribute("esUrl", reMap.get("esUrl"));
			getRequest().setAttribute("esIdentifier", reMap.get("esIdentifier"));
			getRequest().setAttribute("esDate", reMap.get("esDate"));
			getRequest().setAttribute("esDescription", reMap.get("esDescription"));
			getRequest().setAttribute("esCreator", reMap.get("esCreator"));
			getRequest().setAttribute("esVersion", reMap.get("esVersion"));
			getRequest().setAttribute("esTitle", reMap.get("esTitle"));
		}
		return "nameSpaceEditJsp";
	}
	
	/**
	 * 验证命名空间是否可删除
	 */
	public void checkNameSpaceDelete() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		boolean flag = true;
		if(flag) {
			obj.put("flag", "delete");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 删除命名空间
	 */
	public void deleteNameSpace() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		boolean flag = this.metaDataService.deleteOneNameSpaceById(ids);
		if(flag) {
			obj.put("flag", "yes");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存命名空间
	 */
	public void NameSpaceSave() {
		JSONObject obj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String id = getRequest().getParameter("id");
		String esUrl = getRequest().getParameter("esUrl");
		String esIdentifier = getRequest().getParameter("esIdentifier");
		String esDate = sdf.format(date);
		String esDescription = getRequest().getParameter("esDescription");
		String esCreator = getRequest().getParameter("esCreator");
		String esVersion = getRequest().getParameter("esVersion");
		String esTitle = getRequest().getParameter("esTitle");
		Map<String,Object> map = new HashMap<String,Object>();
		if(id != null && !"".equals(id)) {
			map.put("id", id);
			map.put("esUrl", esUrl);
			map.put("esDate", esDate);
			map.put("esDescription", esDescription);
			map.put("esCreator", esCreator);
			map.put("esVersion", esVersion);
			map.put("esTitle", esTitle);
		} else {
			map.put("id", "newAdd");
			map.put("esUrl", esUrl);
			map.put("esIdentifier", esIdentifier);
			map.put("esDate", esDate);
			map.put("esDescription", esDescription);
			map.put("esCreator", esCreator);
			map.put("esVersion", esVersion);
			map.put("esTitle", esTitle);
		}
		boolean flag = this.metaDataService.saveNameSpaceRecord(map);
		if(flag) {
			obj.put("flag", "yes");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到元数据管理页面
	 * @return
	 */
	public String toMetaDataPage() {
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		String tempFlag = getRequest().getParameter("tempFlag");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String esTitle = getRequest().getParameter("esTitle");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("nameSpaceId", nameSpaceId);
		map.put("esTitle", esTitle);
		int count = this.metaDataService.getMetaDataListCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		map.put("pageIndex", Paging.pageIndex);
		map.put("pageSize", Paging.pageSize);
		List<EssMetaData> list = this.metaDataService.getMetaDataList(map);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("esTitle", esTitle);
		getRequest().setAttribute("nameSpaceId", nameSpaceId);
		getRequest().setAttribute("tempFlag", tempFlag);
		return "metaDataJsp";
	}
	
	/**
	 * 跳转到元数据新增页面
	 * @return
	 */
	public String toMetaDataAddPage() {
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		getRequest().setAttribute("nameSpaceId", nameSpaceId);
		return "metaDataAddJsp";
	}
	
	/**
	 * 跳转到元数据编辑页面
	 * @return
	 */
	public String toMetaDataEditPage() {
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		List<EssMetaData> list = this.metaDataService.getMetaDataList(map);
		if(list != null && !list.isEmpty()) {
			getRequest().setAttribute("esmd", list.get(0));
		}
		return "metaDataEditJsp";
	}
	
	/**
	 * 删除元数据
	 */
	public void deleteMetaDataRecord() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		boolean flag = this.metaDataService.deleteMetaDataById(ids);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 检查元数据中是否有唯一标识重复
	 */
	public void checkMetaDataRecord() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		String esIdentifier = getRequest().getParameter("esIdentifier");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("nameSpaceId", nameSpaceId);
		map.put("esIdentifier", esIdentifier);
		boolean flag = this.metaDataService.checkMetaDataRecord(map);
		if(flag) {
			obj.put("flag", "have");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存元数据
	 */
	public void metaDataSave() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		//元数据标识
		String esIdentifier = getRequest().getParameter("esIdentifier");
		//元数据类型
		String esType = getRequest().getParameter("esType");
		//元数据说明
		String esDescription = getRequest().getParameter("esDescription");
		//元数据标题
		String esTitle = getRequest().getParameter("esTitle");
		//命名空间ID
		String nameSpaceId = getRequest().getParameter("nameSpaceId");
		//是否参与检索（0 是 1 否）
		String esIsMetaDataSearch = getRequest().getParameter("esIsMetaDataSearch");
		//是否参与全文检索（0 是 1 否）
		String esIsFullTextSearch = getRequest().getParameter("esIsFullTextSearch");
		//默认值
		String metaDefaultValue = getRequest().getParameter("metaDefaultValue");
		//是否是固定值（0 是 1 否）
		String isFixedValue = getRequest().getParameter("isFixedValue");
		//元数据关系
		String esRelationS = getRequest().getParameter("esRelation");
		//元数据父ID
		String parentId = getRequest().getParameter("parentId");
		Integer esRelation = null;
		if(esRelationS != null && !"".equals(esRelationS)) {
			esRelation = Integer.valueOf(esRelationS);
		}
		EssMetaData emda =  null;
		if(id != null && !"".equals(id)) {
			emda = new EssMetaData(id,esIdentifier,esType,esDescription,esTitle,nameSpaceId,esRelation,parentId,
					esIsMetaDataSearch,esIsFullTextSearch,metaDefaultValue,isFixedValue);
		} else {
			emda = new EssMetaData("newAdd",esIdentifier,esType,esDescription,esTitle,nameSpaceId,esRelation,parentId,
					esIsMetaDataSearch,esIsFullTextSearch,metaDefaultValue,isFixedValue);
		}
		boolean flag = this.metaDataService.saveMetaDataRecord(emda);
		if(flag) {
			obj.put("flag", "have");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 跳转到元数据属性页面
	 * @return
	 */
	public String toMeDaPropertyPage() {
		String metaDataId = getRequest().getParameter("metaDataId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("metaDataId", metaDataId);
		List<EssPropValue> list = this.metaDataService.getMetaDataPropValue(map);
		getRequest().setAttribute("metaDataId", metaDataId);
		getRequest().setAttribute("list", list);
		return "toMeDaPropertyJsp";
	}
	
	/**
	 * 跳转到元数据属性新增页面
	 * @return
	 */
	public String toMeDaPropertyAddPage() {
		String metaDataId = getRequest().getParameter("metaDataId");
		getRequest().setAttribute("metaDataId", metaDataId);
		return "toMeDaPropertyAddJsp";
	}
	
	/**
	 * 跳转到元数据属性编辑页面
	 * @return
	 */
	public String toMeDaPropertyEditPage() {
		String metaDataId = getRequest().getParameter("metaDataId");
		String id = getRequest().getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		List<EssPropValue> list = this.metaDataService.getMetaDataPropValueById(map);
		getRequest().setAttribute("metaDataId", metaDataId);
		getRequest().setAttribute("epve", list.get(0));
		return "toMeDaPropertyEditJsp";
	}
	
	public void deleteMeDaProPertyReocrd() {
		JSONObject obj = new JSONObject();
		String ids = getRequest().getParameter("ids");
		boolean flag = this.metaDataService.deleteMeDaPropertyById(ids);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
	
	/**
	 * 保存元数据属性
	 */
	public void saveMeDaProPertyRecord() {
		JSONObject obj = new JSONObject();
		String id = getRequest().getParameter("id");
		String esIdentifier = getRequest().getParameter("esIdentifier");
		String esTitle = getRequest().getParameter("esTitle");
		String esDescription = getRequest().getParameter("esDescription");
		String metaDataId = getRequest().getParameter("metaDataId");
		String idProp = getRequest().getParameter("idProp");
		EssPropValue epve = new EssPropValue();
		if(id != null && !"".equals(id)) {
			epve.setId(id);
			epve.setIdProp(idProp);
		} else {
			epve.setId("newAdd");
		}
		epve.setEsIdentifier(esIdentifier);
		epve.setEsTitle(esTitle);
		epve.setEsDescription(esDescription);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("metaDataId", metaDataId);
		map.put("essPropValue", epve);
		boolean flag = this.metaDataService.saveMeDaPropertyRecord(map);
		if(flag) {
			obj.put("flag", "success");
		} else {
			obj.put("flag", "no");
		}
		toPage(obj.toString());
	}
}
