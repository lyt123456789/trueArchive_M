package cn.com.trueway.workflow.core.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;

import cn.com.trueway.workflow.core.dao.TrueJsonDao;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;

public class TrueJsonDaoImpl  extends BaseDao implements TrueJsonDao{

	@Override
	public void saveTrueJson(TrueJson entity)  throws Exception {
		getEm().persist(entity);
	}

	@Override
	public List<TrueJson> findTrueJsonListByInstanceId(String instanceId) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TrueJson findNewestTrueJson(String instanceId) {
		String sql = "select t.* from t_wf_core_truejson t where t.instanceId='"+instanceId+"' order by t.saveDate desc";
		List<TrueJson> list = getEm().createNativeQuery(sql, TrueJson.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TrueJson findNewestTrueJsonByProcessId(String processId) {
		String sql = "select t.* from t_wf_core_truejson t where t.processId='"+processId+"' order by t.saveDate desc";
		List<TrueJson> list = getEm().createNativeQuery(sql).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public void saveTrueJsonLog(TrueJsonLog log)  throws Exception{
		getEm().persist(log);
	}

	@Override
	public int findTrueJsonLogCount(String contionSql) {
		String sql = "select  count(*) from t_wf_core_truejsonlog t, t_wf_process t2, wf_node t3, zwkj_employee t4 " +
				" where t2.wf_process_uid = t.processid " +
				" and t3.wfn_id = t2.wf_node_uid and t4.employee_guid = t.userid  " + 
				" and t.readorwrite != '2'";
		if(CommonUtil.stringNotNULL(contionSql)){
			sql += contionSql;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrueJsonLog> findTrueJsonLogList(String contionSql,
			Integer pageindex, Integer pagesize) {
		String sql = "select t.id, t2.process_title, t.excute, t3.wfn_name, t4.employee_name," +
				" to_char(t.readorwritedate,'yyyy-MM-dd hh24:mi:ss'), t.readorwrite " +
				" from t_wf_core_truejsonlog t, t_wf_process t2, wf_node t3, zwkj_employee t4 " +
				" where t2.wf_process_uid = t.processid " +
				" and t3.wfn_id = t2.wf_node_uid and t4.employee_guid = t.userid " + 
				" and t.readorwrite != '2'";
		if(CommonUtil.stringNotNULL(contionSql)){
			sql += contionSql;
		}
		sql += " order by t.readorwritedate desc";
		Query query=super.getEm().createNativeQuery(sql.toString());
		query.setFirstResult(pageindex);
		query.setMaxResults(pagesize);
		List<Object[]>  list = query.getResultList();
		List<TrueJsonLog> logList = new ArrayList<TrueJsonLog>();
		TrueJsonLog log = null;
		Object[] data = null;
		for(int i=0; i<list.size(); i++){
			log = new TrueJsonLog();
			data = list.get(i);
			log.setId(data[0]!=null?data[0].toString():"");
			log.setProcess_title(data[1]!=null?data[1].toString():"");
			log.setExcute(data[2]!=null?data[2].toString():"");
			log.setNodeName(data[3]!=null?data[3].toString():"");
			log.setUsername(data[4]!=null?data[4].toString():"");
			log.setReadOrWriteDate(convertStringToDate(data[5]!=null?data[5].toString():""));
			log.setReadOrWrite(data[6]!=null?data[6].toString():"");
			logList.add(log);
		}
		return logList;
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
	
	@SuppressWarnings("unchecked")
	public List<TrueJsonLog> getTrueJsonByParams(Map<String, String> map){
		String id = map.get("id");
		String hql = "from TrueJsonLog t where 1=1 ";
		
		if( CommonUtil.stringNotNULL(id)){
			hql += " and t.id = '" + id + "'";
		}
		Query query = getEm().createQuery(hql);
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public TrueJson findTrueJsonForDofile(String instanceId){
		String sql = "select t.* from t_wf_core_truejson t where t.instanceId in (select t2.wf_instance_uid from t_wf_process t2 where t2.allinstanceid = (" +
				" select  distinct t2.allinstanceid from t_wf_process t2 where t2.wf_instance_uid = '"+instanceId+"'" +
				")) order by t.saveDate desc";
		List<TrueJson> list = getEm().createNativeQuery(sql, TrueJson.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	
	@Override
	public int findDelFileLogCount(String contionSql) {
		String sql = "select  count(*) from t_wf_core_truejsonlog t, t_wf_core_dofile t2, zwkj_employee t4 " +
				" where t2.instanceid = t.instanceid " +
				" and t4.employee_guid = t.userid  " + 
				" and t.readorwrite = '2'";
		if(CommonUtil.stringNotNULL(contionSql)){
			sql += contionSql;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrueJsonLog> findDelFileLogList(String contionSql,
			Integer pageindex, Integer pagesize) {
		String sql = "select t.id, t2.dofile_title, t.excute, t4.employee_name," +
				" to_char(t.readorwritedate,'yyyy-MM-dd hh24:mi:ss'), t.readorwrite, t.fromexcute " +
				" from t_wf_core_truejsonlog t, t_wf_core_dofile t2, zwkj_employee t4 " +
				" where t2.instanceid = t.instanceid " +
				" and t4.employee_guid = t.userid " + 
				" and t.readorwrite = '2'";
		if(CommonUtil.stringNotNULL(contionSql)){
			sql += contionSql;
		}
		sql += " order by t.readorwritedate desc";
		Query query=super.getEm().createNativeQuery(sql.toString());
		query.setFirstResult(pageindex);
		query.setMaxResults(pagesize);
		List<Object[]>  list = query.getResultList();
		List<TrueJsonLog> logList = new ArrayList<TrueJsonLog>();
		TrueJsonLog log = null;
		Object[] data = null;
		for(int i=0; i<list.size(); i++){
			log = new TrueJsonLog();
			data = list.get(i);
			log.setId(data[0]!=null?data[0].toString():"");
			log.setProcess_title(data[1]!=null?data[1].toString():"");
			log.setExcute(data[2]!=null?data[2].toString():"");
			log.setUsername(data[3]!=null?data[3].toString():"");
			log.setReadOrWriteDate(convertStringToDate(data[4]!=null?data[4].toString():""));
			log.setReadOrWrite(data[5]!=null?data[5].toString():"");
			log.setFromExcute(data[6]!=null?data[6].toString():"");
			logList.add(log);
		}
		return logList;
	}
	
	@Override
	public TrueJson findNewsetTrueJsonByInstanceId(String instanceId){
//		String sql = "select t.* from t_wf_core_truejson t where t.savedate =(select max(t2.savedate) from t_wf_core_truejson t2  " +
//				"where t2.instanceid in (select t2.wf_instance_uid from t_wf_process t2 where t2.allinstanceid =" +
//				"(select distinct t2.allinstanceid from t_wf_process t2 where t2.wf_instance_uid = '"+instanceId+"'" +
//				"))) and t.instanceid = '"+instanceId+"' order by t.saveDate desc";
//		String sql = "select t.* from t_wf_core_truejson t where t.savedate =(select max(t2.savedate) from t_wf_core_truejson t2  " +
//				"where t2.instanceid ='"+instanceId+"') and t.instanceid = '"+instanceId+"' order by t.saveDate desc";
		String sql = "select * from (select t.*,row_number() OVER(PARTITION BY instanceid ORDER BY t.saveDate desc) as rn from t_wf_core_truejson t) t1 " +
				"where t1.rn = '1' and t1.instanceid = '"+instanceId+"'";
		List<TrueJson> list = getEm().createNativeQuery(sql, TrueJson.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
