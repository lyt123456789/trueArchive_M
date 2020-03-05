package cn.com.trueway.workflow.core.service;

import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.core.pojo.Replay;

public interface ReplayService {
	
	List<Replay> getReplayList(String conditionSql,String userId, Integer pageIndex, Integer pageSize);
		
	int getCountOfReplay(String conditionSql,String userId,String type);
	
	void saveReplay(Replay replay);
	
	JSONObject updateReplay(Replay replay);
	
	Replay getReplayById(String id);
	
}
