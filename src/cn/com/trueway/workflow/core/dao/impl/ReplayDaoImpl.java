package cn.com.trueway.workflow.core.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.ReplayDao;
import cn.com.trueway.workflow.core.pojo.Replay;


public class ReplayDaoImpl  extends BaseDao implements ReplayDao{

	public List<Replay> getReplayList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize) {
		StringBuffer querySql = new StringBuffer();
		querySql.append("select t.id, t.processid, t.title, to_char(t.replay_time,'yyyy-MM-dd hh:ss:mm'),")
				.append(" t.replay_userid,t.replay_processid,t.status, t2.employee_name, t3.wf_instance_uid")
				.append(" from T_WF_CORE_REPLAY t, zwkj_employee t2, t_wf_process t3")
				.append(" where t.status = '0' and t2.employee_guid = t.replay_userid and t3.wf_process_uid = t.replay_processid")
				.append(" and t3.fromuserid = '"+userId+"' ")
				.append(" order by t.replay_time desc");
		Query query = super.getEm().createNativeQuery(querySql.toString());
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList(); 
		List<Replay> wfpList = new ArrayList<Replay>();
		Replay  replay = null;
		for(Object[] data : list){
			replay = new Replay();
			replay.setId(data[0]!=null?data[0].toString():"");
			replay.setProcessId(data[1]!=null?data[1].toString():"");
			replay.setTitle(data[2]!=null?data[2].toString():"");
			replay.setReplayTime(convertStringToDate(data[3]));
			replay.setReplayUserid(data[4]!=null?data[4].toString():"");
			replay.setReplayProcessid(data[5]!=null?data[5].toString():"");
			replay.setStatus(data[6]!=null?data[6].toString():"");
			replay.setReplayUserName(data[7]!=null?data[7].toString():"");
			replay.setInstanceId(data[8]!=null?data[8].toString():"");
			wfpList.add(replay);
		}
		return wfpList;
	}
	
	
	public Date convertStringToDate(Object obj){
		if(obj==null){
			return null;
		}
		String time = obj.toString();
		if(time==null || time.equals("")){
			return null;
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public int getCountOfReplay(String conditionSql, String userId, String type) {
		StringBuffer querySql = new StringBuffer();
		querySql.append("select count(*) ")
				.append(" from t_wf_core_replay t, zwkj_employee t2, t_wf_process t3")
				.append(" where t.status = '0' and t2.employee_guid = t.replay_userid and t3.wf_process_uid = t.replay_processid")
				.append(" and t3.fromuserid = '"+userId+"' ")
				.append(" order by t.replay_time desc");
		return Integer.parseInt(getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
		
	}

	@Override
	public void saveReplay(Replay replay) {
		getEm().persist(replay);
		
	}


	@Override
	public void updateReplay(Replay replay) throws Exception {
		getEm().merge(replay);
	}


	@Override
	public Replay getReplayById(String id) {
		return getEm().find(Replay.class, id);
	}
}
