package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.ReadedEndPending;
import cn.com.trueway.workflow.core.pojo.TodoMessage;

public interface IntegrateDao {
	
	int findTodoMessageCount(String conditionSql, String userId);
	
	 List<TodoMessage> findTodoMessage(String conditionSql,String userId,Integer pageIndex,Integer pageSize,String itemIds);

	int findHavedoMessageCount(String conditionSql, String userId);
	
	List<TodoMessage> findTodoMessageMobile(String userId, int count,Integer column, Integer pagesize, String type, String itemIds,String title);

	List<TodoMessage> findHavedoMessageOfMobile(String userId, Integer pageIndex, Integer pagesize, String conditionSql, String itemIds,String title);

	int findMailCount(String conditionSql, String userId);
	
	void saveReadedEndPending(ReadedEndPending enity);
}
