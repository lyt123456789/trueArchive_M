package cn.com.trueway.workflow.log.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;

import cn.com.trueway.workflow.log.dao.LogDao;
import cn.com.trueway.workflow.log.pojo.Log;

public class LogDaoImpl extends BaseDao implements LogDao{

	public int countLog(Map<String, String> map) {

		StringBuffer query = new StringBuffer().append("select count(1)  from ( select r.*,d.dofile_title as prcoess_title from reslog r, t_wf_core_dofile d where r.instanceid =  d.instanceid ) l left join oa_doc_attachments a on l.attid = a.id where 1=1");
		
		String createtimeFrom = map.get("createtimeFrom");
		String createtimeTo = map.get("createtimeTo");
		String loglevel = map.get("loglevel");
		String msg = map.get("msg");
		String username = map.get("username");
		String prcoess_title = map.get("prcoess_title");
		String attchmentname = map.get("attchmentname");
		if(CommonUtil.stringNotNULL(createtimeFrom)){
			createtimeFrom = createtimeFrom.trim().replaceAll("'","\\'\\'");
			query.append("and l.createtime >= to_date('"+createtimeFrom+" 00:00:00','yyyy-mm-dd HH24:mi:ss') ");
		}
		if(CommonUtil.stringNotNULL(createtimeTo)){
			createtimeTo = createtimeTo.trim().replaceAll("'","\\'\\'");
			query.append("and l.createtime <= to_date('"+createtimeTo+" 23:59:59','yyyy-mm-dd HH24:mi:ss') ");
		}
		if(CommonUtil.stringNotNULL(loglevel)){
			query.append(" and l.loglevel = '"+loglevel+"' ");
		}
		if(CommonUtil.stringNotNULL(msg)){
			query.append(" and l.msg like '%"+msg+"%' ");
		}
		if(CommonUtil.stringNotNULL(username)){
			query.append(" and l.username like '%"+username+"%' ");
		}
		if(CommonUtil.stringNotNULL(prcoess_title)){
			query.append(" and l.prcoess_title like '%"+prcoess_title+"%' ");
		}
		if(CommonUtil.stringNotNULL(attchmentname)){
			query.append(" and a.filename like '%"+attchmentname+"%' ");
		}
		
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<Log> getLogList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		String hql = "select l.logid,l.method,l.createtime,l.loglevel,l.msg,l.line,l.thread,l.userid,l.username,l.instanceid,l.attid,l.prcoess_title,a.filename as attchmentName from " +
				" (select r.*,d.dofile_title as prcoess_title from reslog r , t_wf_core_dofile d where r.instanceid =  d.instanceid ) l " +
				" left join oa_doc_attachments a on l.attid = a.id  where 1=1";
		String createtimeFrom = map.get("createtimeFrom");
		String createtimeTo = map.get("createtimeTo");
		String loglevel = map.get("loglevel");
		String msg = map.get("msg");
		String username = map.get("username");
		String prcoess_title = map.get("prcoess_title");
		String attchmentname = map.get("attchmentname");
		
		if(CommonUtil.stringNotNULL(createtimeFrom)){
			createtimeFrom = createtimeFrom.trim().replaceAll("'","\\'\\'");
			hql += "and l.createtime >= to_date('"+createtimeFrom+" 00:00:00','yyyy-mm-dd HH24:mi:ss') ";
		}
		if(CommonUtil.stringNotNULL(createtimeTo)){
			createtimeTo = createtimeTo.trim().replaceAll("'","\\'\\'");
			hql +=  "and l.createtime <= to_date('"+createtimeTo+" 23:59:59','yyyy-mm-dd HH24:mi:ss') ";
		}
		if(CommonUtil.stringNotNULL(loglevel)){
			hql += " and l.loglevel = '"+loglevel+"' ";
		}
		if(CommonUtil.stringNotNULL(msg)){
			hql += " and l.msg like '%"+msg+"%' ";
		}
		if(CommonUtil.stringNotNULL(username)){
			hql += " and l.username like '%"+username+"%' ";
		}
		if(CommonUtil.stringNotNULL(prcoess_title)){
			hql += " and l.prcoess_title like '%"+prcoess_title+"%' ";
		}
		if(CommonUtil.stringNotNULL(attchmentname)){
			hql += " and a.filename like '%"+attchmentname+"%' ";
		}

		hql += " order by l.createtime desc";
		Query query=super.getEm().createNativeQuery(hql,"LogResults");
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult( pageIndex);
			query.setMaxResults( pageSize);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Log findLogById(String logid) {
		String hql = "select l.logid,l.method,l.createtime,l.loglevel,l.msg,l.line,l.thread,l.userid,l.username,l.instanceid,l.attid,l.prcoess_title,a.filename as attchmentName from " +
				" (select r.*,d.dofile_title as prcoess_title from reslog r left join  T_WF_CORE_DOFILE d on r.instanceid =  d.instanceid ) l " +
				" left join oa_doc_attachments a on l.attid = a.id  where 1=1";
		
		hql += " and l.logid = '"+logid+"'";
		Query query = getEm().createNativeQuery(hql,"LogResults");
		List<Log> list = query.getResultList();
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String[]> getInstanceInfo(String instanceid) {

		String sql = "select p.process_title,d.item_name  from t_wf_process p " +
				" left join  T_WF_CORE_DOFILE d " +
				" on p.wf_instance_uid = d.instanceid " +
				" where p.wf_instance_uid = '"+instanceid+"'";
		
		return getEm().createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public String getTitle(String instanceid) {
		String sql = "select distinct(p.process_title)  from t_wf_process p " +
				" where p.wf_instance_uid = '"+instanceid+"'";
		Query query = getEm().createNativeQuery(sql);
		List<String> list = query.getResultList();
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
