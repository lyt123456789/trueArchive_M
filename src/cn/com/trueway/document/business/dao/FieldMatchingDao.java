package cn.com.trueway.document.business.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;

public interface FieldMatchingDao {
	
	void addDocxgFieldMap(DocxgFieldMap docxgField);
	
	List<DocxgFieldMap> getDocxgFieldMapList(String itemId);

	public void deleteDocxgFieldMapByItemId(String itemId);
	
	public void saveForm(String  tableName, Map<String,Object>  map) throws Exception;
	
	public List<DepRelationShip> getDepRelationShipListByDepId(String depId);
	
	public void deleteDepShipById(String id);
	
	public void deleteTableInfo(String instanceId, String tableName);

}
