package cn.com.trueway.document.business.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;

public interface FieldMatchingService {
	
	void addDocxgFieldMap(DocxgFieldMap docxgField);
	
	public List<DocxgFieldMap> getDocxgFieldMapList(String itemId);
	
	public void deleteDocxgFieldMapByItemId(String itemId);
	
	public void saveForm(String tableName, Map<String,Object>  map, String instanceId);
	
	public List<DepRelationShip> getDepRelationShipListByDepId(String depId);
	
	public void deleteDepShipById(String id);
	
	public void deleteTableInfo(String instanceId,String tableName);
	
}
