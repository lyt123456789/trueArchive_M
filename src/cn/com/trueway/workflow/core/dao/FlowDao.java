package cn.com.trueway.workflow.core.dao;

public interface FlowDao {

	void executeSql(String sql,Object[] params,String selectSql);

	void executeSql(String sql,Object[] params);
	
}
