package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfTemplate;

public interface TemplateService {

	public Integer getTemplateCountForPage(String column, String value,
			WfTemplate template);
	
	public List<WfTemplate> getTemplateListForPage(String column, String value,
			WfTemplate template, Integer pageindex, Integer pagesize);
	
	List<WfTemplate> getAllTemplateListNotLc(String lcid,String isRedTape);
	
	WfTemplate addTemplate(WfTemplate wfTemplate);
	
	public String getAllMeta(String content);
	
	boolean delTemplate(WfTemplate wfTemplate);
	
	WfTemplate getTemplateById(String id);
	
	Map<String, String> getTemplateValue(List<WfLabel> list, String infoid, String fileId);
	
	List<WfTemplate> getTemplateByLcid(String lcid);
	
	List<WfTemplate> findWfTemplateList(String ids, String tempType);

	List<WfTemplate> getTemplateBySql(String conditionSql);

	public Map<String, String> getTemplateValue(List<WfLabel> list,
			String instanceId, String fileId, String serverUrl);
	
}
