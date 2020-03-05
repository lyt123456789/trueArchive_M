package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.WfTemplate;

public interface TemplateDao {

	Integer getTemplateCountForPage(String column, String value, WfTemplate template);
	
	public List<WfTemplate> getTemplateListForPage(String column, String value,
			WfTemplate template, Integer pageindex, Integer pagesize);
	
	List<WfTemplate> getAllTemplateListNotLc(String lcid,String isRedTape);
	
	WfTemplate addTemplate(WfTemplate wfTemplate);
	
	public void delTemplate(WfTemplate wfTemplate);
	
	public WfTemplate getTemplateById(String id);
	
	public List<WfTemplate> getTemplateByLcid(String lcid);
	
	public List<Object> getSqlQuery(String sql);
	
	public List getHqlQuery(String Hql);
	
	List<WfTemplate> findWfTemplateList(String ids, String tempType);
	
	public List<WfTemplate> getTemplateBySql(String conditionSql);

	void updateDoFile(DoFile doFile);
}
