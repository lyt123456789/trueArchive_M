package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfDictionary;

public interface DictionaryDao {

	Integer getDictionaryCountForPage(String column, String value,
			WfDictionary dictionary);
	
	List<WfDictionary> getDictionaryListForPage(String column, String value,
			WfDictionary dictionary, Integer pageindex, Integer pagesize) ;
	
	List<WfDictionary> getPublicDictionary();
	
	void addDictionary(WfDictionary dictionary);
	
	void delDictionary(WfDictionary dictionary);
	
	WfDictionary getDictionaryById(String id);
	
	List<WfDictionary> getDictionaryByLcid(String lcid);
	
	List<WfDictionary> getDictionaryByName(String vc_name);
	
	String getDictionaryNameByField(String itemId, String tableName,
			String fieldName);
	
}
