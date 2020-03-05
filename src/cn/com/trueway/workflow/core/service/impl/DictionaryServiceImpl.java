package cn.com.trueway.workflow.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.dao.DictionaryDao;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.service.DictionaryService;

public class DictionaryServiceImpl implements DictionaryService{

	private DictionaryDao dictionaryDao;
	
	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	public boolean addDictionary(List<WfDictionary> list) {
		for(WfDictionary dictionary : list){
			dictionaryDao.addDictionary(dictionary);
		}
		return false;
	}

	public boolean delDictionary(WfDictionary dictionary) {
		dictionaryDao.delDictionary(dictionary);
		return false;
	}

	public WfDictionary getDictionaryById(String id) {
		return dictionaryDao.getDictionaryById(id);
	}
	
	public List<WfDictionary> getDictionaryByName(String vc_name) {
		return dictionaryDao.getDictionaryByName(vc_name);
	}

	public Integer getDictionaryCountForPage(String column, String value,
			WfDictionary dictionary){
		return dictionaryDao.getDictionaryCountForPage(column, value, dictionary);
	}
	
	public List<WfDictionary> getDictionaryListForPage(String column, String value,
			WfDictionary dictionary, Integer pageindex, Integer pagesize){
		return dictionaryDao.getDictionaryListForPage(column, value, dictionary, pageindex, pagesize);
	} 

	public List<WfDictionary> getPublicDictionary() {
		return dictionaryDao.getPublicDictionary();
	}

	public List<WfDictionary> getDictionaryByLcid(String lcid) {
		return dictionaryDao.getDictionaryByLcid(lcid);
	}
	
	public int isExistDictionary( String vc_name){
		List<WfDictionary> list = dictionaryDao.getDictionaryByName(vc_name);
		if(list != null && list.size() > 0){
			return 1;
		}else{
			return 0;
		}
	}
	
	@Override
	public List<String[]> getDictionaryValueByField(String itemId,
			String tableName, String fieldName) {
		//1 获取字典
		String dictionaryName = dictionaryDao.getDictionaryNameByField(itemId,tableName,fieldName);
		if(dictionaryName != null){
			List<WfDictionary> dics =	dictionaryDao.getDictionaryByName(dictionaryName);
			if(dics != null && dics.size()>0){
				String[] dicKeys = dics.get(0).getVc_key().split(",");
				String[] dicValues = dics.get(0).getVc_value().split(",");
				List<String[]> list = new ArrayList<String[]>();
				
				for(int i = 0 ; i <dicKeys.length ; i++){
					if(!"".equals(dicKeys[i].trim())){
						list.add(new String[]{dicKeys[i],dicValues[i]});
					}
				}
				return list;
			}else{
				return null;
			}
			
			
		}else{
			return null;
		}
		
	}
	
	@Override
	public List<Map<String, String>> getKeyAndValue(String vc_name){
		try {
			List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
			List<WfDictionary> list = dictionaryDao.getDictionaryByName(vc_name);
			if(null != list && list.size()>0){
				WfDictionary dictionary = list.get(0); 
				String[] key = dictionary.getVc_key().split(",");
				String[] value = dictionary.getVc_value().split(",");
				
				for (int i=0; i < key.length; i++) {
					Map<String,String> map = new HashMap<String, String>();
					map.put("key", key[i]);
					map.put("value", value[i]);
					returnList.add(map);
				}
				return returnList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
