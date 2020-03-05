package cn.com.trueway.workflow.core.dao;

import java.util.List;


import cn.com.trueway.workflow.core.pojo.Replay;

public interface ReplayDao {
	
	public List<Replay> getReplayList(String conditionSql,String userId, Integer pageIndex, Integer pageSize);
	
	public int getCountOfReplay(String conditionSql,String userId,String type);
	
	void saveReplay(Replay replay);
	
	void updateReplay(Replay replay) throws Exception;
	
	Replay getReplayById(String id);

}
