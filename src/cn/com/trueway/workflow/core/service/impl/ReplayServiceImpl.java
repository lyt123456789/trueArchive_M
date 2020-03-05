package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.core.dao.ReplayDao;
import cn.com.trueway.workflow.core.pojo.Replay;
import cn.com.trueway.workflow.core.service.ReplayService;


public class ReplayServiceImpl implements ReplayService{

	private ReplayDao replayDao;
	
	public ReplayDao getReplayDao() {
		return replayDao;
	}

	public void setReplayDao(ReplayDao replayDao) {
		this.replayDao = replayDao;
	}

	@Override
	public List<Replay> getReplayList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize) {
		List<Replay> pendList = replayDao.getReplayList(conditionSql,userId, pageIndex, pageSize);
		return pendList;
	}

	@Override
	public int getCountOfReplay(String conditionSql, String userId, String type) {
		return replayDao.getCountOfReplay(conditionSql,userId,type);
	}

	@Override
	public void saveReplay(Replay replay) {
		replayDao.saveReplay(replay);
		
	}

	@Override
	public JSONObject updateReplay(Replay replay) {
		JSONObject result = new JSONObject();
		try{
			replayDao.updateReplay(replay);
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public Replay getReplayById(String id) {
		return replayDao.getReplayById(id);
	}

}
