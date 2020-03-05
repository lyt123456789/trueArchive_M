/**
 * 文件名称:EWorkFlowService.java
 * 作者:zhuxc<br>
 * 创建时间:2014-1-16 下午01:41:55
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.functions.workflow.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;

/**
 * 描述： 对EWorkFlowService进行描述
 * 作者：zhuxc
 * 创建时间：2014-1-16 下午01:41:55
 */
public interface EWorkFlowService {

	String getTodoForPortalNew(String userId, String column, String pagesize,
			String url, String callBack, String itemIds);
	
	String getTodo4WebNew(String userId, String column, String pagesize, String url, String callback,String itemIds);
	  
    public abstract List getTodo4Breeze(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
	
    public List getHaveDone4Breeze(String userId, String column, String pagesize, String url, String callback, String itemIds,String timetype);
    
    int searchCountByStatus(String conditionSql, String status,
			List<FieldSelectCondition> selects);

	String searchDataByStatus(String conditionSql, int column, Integer pagesize,
			String status, List<FieldSelectCondition> selects,String serverUrl,String callback);
	
	String getMySearchJson(String conditionSql, int column, Integer pagesize,
			String status, List<FieldSelectCondition> selects,String serverUrl,String callback);
	
	JSONArray getMySearchObj(String conditionSql, int column, Integer pagesize,
			String status, List<FieldSelectCondition> selects,String serverUrl);
	
	String getMySearchFormJson(String userId,String status,String action);

	String getJsonByMaxUrl(String conditionSql, int column, Integer pagesize,
			String status, List<FieldSelectCondition> selects,
			String serverUrl, String callback);
	
	String getJsonReceiveDataByStatus(String callback,String userId,Integer pageIndex, Integer pageSize,String status, Map<String,String> map,String serverUrl,boolean jrdb,List<WfItem> itemlist);
	
	String getJsonToBeSealed(String callback,String deptids,Integer pageIndex, Integer pageSize,String conditionSql,String serverUrl);
	
	String getJsonToDealList(String callback, String userId,Integer pageIndex, Integer pageSize,String conditionSql,String serverUrl);
}
