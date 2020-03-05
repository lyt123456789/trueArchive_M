package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.document.business.model.ItemSelect;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;

public interface DoArticlePostDao {

	ItemSelect getSelectByItemID(String itemId, String type);
	
	int getCountOfFreeDofile(String conditionSql, String conditionTable,
			String employeeGuid, String type, List<FieldSelectCondition> fsList,String bjlx);
	
	List<Object> getFreeDofileList(String isEnd, String conditionSql, String conditionTable,
			String resultSql, String userId, int pageIndex, int pageSize,
			List<FieldSelectCondition> fsList,String bjlx);
	
	List<WfProcess> getProcessList(String instanceId);
}
