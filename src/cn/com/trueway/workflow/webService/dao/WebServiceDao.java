package cn.com.trueway.workflow.webService.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.webService.pojo.WebServiceInfo;;

public interface WebServiceDao {
	void saveWebServiceInfo(WebServiceInfo webServiceInfo);
	
	List<WebServiceInfo> findWebServiceInfoList(String conditionSql, Integer pageindex,Integer pagesize);
	
	int findWebServiceInfoCount(String conditionSql);
	
	void executeSql(String sql) throws Exception;
	
	public List<Object[]> getItemList(Map<String, String> map);
	
	public int getItemListCount(Map<String, String> map);
	
	public List<Object[]> getSyncInfoListByItemId(Map<String, String> map);
	
	public int getSyncInfoCountByItemId(Map<String, String> map);
}
