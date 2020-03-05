package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.document.business.model.ItemSelect;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;


public interface DoArticlePostService {
	
	// 根据事项id 查找自定义查询条件
	public ItemSelect getSelectByItemID(String itemId,String type);
	
	int getCountOfFreeDofile(String conditionSql, String conditionTable,
			String employeeGuid, String string,
			List<FieldSelectCondition> fsList,String bjlx);
	
	List<Object> getFreeDofileList(String isEnd, String conditionSql, String conditionTable,
			String resultSql, String employeeGuid, int pageIndex, int pageSize,
			List<FieldSelectCondition> fsList,String bjlx);

}
