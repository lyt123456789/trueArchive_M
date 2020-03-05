package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.WfDictionary;

public interface DictionaryService {

	Integer getDictionaryCountForPage(String column, String value,
			WfDictionary dictionary);
	
	List<WfDictionary> getPublicDictionary();
	
	boolean addDictionary(List<WfDictionary> list);
	
	boolean delDictionary(WfDictionary dictionary);
	
	WfDictionary getDictionaryById(String id);
	
	List<WfDictionary> getDictionaryByLcid(String lcid);
	
	int isExistDictionary( String vc_name);
	
	List<WfDictionary> getDictionaryListForPage(String column, String value,
			WfDictionary dictionary, Integer pageindex, Integer pagesize) ;
	
	List<WfDictionary> getDictionaryByName(String vc_name);

	List<String[]>  getDictionaryValueByField(String itemId, String tableName, String fieldName);

	public List<Map<String, String>> getKeyAndValue(String vc_name);
}
