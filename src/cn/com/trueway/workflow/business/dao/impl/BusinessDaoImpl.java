package cn.com.trueway.workflow.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.business.dao.BusinessDao;
import cn.com.trueway.workflow.business.pojo.HandRoundShip;
import cn.com.trueway.workflow.business.pojo.Library;
import cn.com.trueway.workflow.business.pojo.PressMsg;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;

@SuppressWarnings("unchecked")
public class BusinessDaoImpl extends BaseDao implements BusinessDao{

	@Override
	public int findSendLibraryCount(String contionsql, String userId) {
		StringBuffer buffer = new StringBuffer(512);
		buffer.append("  select count(*) from (select distinct d.dofile_title as title,")
		  .append(" t.wh as lwh,")
		  .append(" '' as lwdw,")
		  .append("  (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) as lwsj,")
		  .append(" '1' as type,")
		  .append("  t.zbbm as zsdw,")
		  .append("  t.csbm as csdw,")
		  .append(" d.instanceId as instanceId,")
		  .append(" i.vc_sxmc as itemName," +
		  		" d.dotime as dotime ")
		  .append(" from  t_wf_core_item i, t_wf_core_dofile d left join filed_fw_inf t on d.instanceid = t.instanceid")
		  .append(" where (d.isdelete != 1 or d.isdelete is null) " +
		  		" and i.vc_sxlx = '0' " +
		  		" and d.item_id = i.id ")
		  .append( contionsql )
		  .append(" )");
		return Integer.parseInt(getEm().createNativeQuery(buffer.toString()).getSingleResult().toString());
	}

	@Override
	public List<Library> findSendLibraryList(String contionsql, String userId,
			Integer pageIndex, Integer pageSize) {
		StringBuffer buffer = new StringBuffer(512);
		buffer.append(" select distinct d.dofile_title as title,")
			  .append(" t.wh as lwh,")
			  .append(" '' as lwdw,")
			  .append("  (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) as lwsj,")
			  .append(" '1' as type,")
			  .append("  t.zbbm as zsdw,")
			  .append("  t.csbm as csdw,")
			  .append(" d.instanceId as instanceId,")
			  .append(" i.vc_sxmc as itemName," +
			  		" d.dotime as dotime ")
			  .append(" from  t_wf_core_item i, t_wf_core_dofile d left join filed_fw_inf t on d.instanceid = t.instanceid")
			  .append(" where (d.isdelete != 1 or d.isdelete is null)  " +
			  		" and i.vc_sxlx = '0' " +
			  		" and d.item_id = i.id ")
			  .append( contionsql )
			  .append(" order by dotime desc");
		Query query=super.getEm().createNativeQuery(buffer.toString(),"LibraryResults");
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public int findReceiveLibraryCount(String contionsql, String userId) {
		StringBuffer buffer = new StringBuffer(512);
		buffer.append("  select count(*) from (select distinct d.dofile_title as title,")
		  .append(" t.wh as lwh,")
		  .append(" t.lwdw as lwdw,")
		  .append("  (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) as lwsj,")
		  .append(" '1' as type,")
		  .append("  '' as zsdw,")
		  .append("  '' as csdw,")
		  .append(" d.instanceId as instanceId,")
		  .append(" i.vc_sxmc as itemName," +
		  		" d.dotime as dotime ")
		  .append(" from  t_wf_core_item i, t_wf_core_dofile d left join filed_bw_inf t on d.instanceid = t.instanceid")
		  .append(" where (d.isdelete != 1 or d.isdelete is null) " +
		  		" and i.vc_sxlx = '1' " +
		  		" and d.item_id = i.id ")
		  .append( contionsql )
		  .append(" )");
		return Integer.parseInt(getEm().createNativeQuery(buffer.toString()).getSingleResult().toString());
	
	}

	@Override
	public List<Library> findReceiveLibraryList(String contionsql,
			String userId, Integer pageIndex, Integer pageSize) {
		StringBuffer buffer = new StringBuffer(512);
		buffer.append(" select distinct d.dofile_title as title,")
			  .append(" t.wh as lwh,")
			  .append(" t.lwdw as lwdw,")
			  .append("  (select p2.finsh_time from t_wf_process p2 where p2.wf_instance_uid = d.instanceid and p2.is_end =1 ) as lwsj,")
			  .append(" '1' as type,")
			  .append("  '' as zsdw,")
			  .append("  '' as csdw,")
			  .append(" d.instanceId as instanceId,")
			  .append(" i.vc_sxmc as itemName," +
			  		" d.dotime as dotime ")
			  .append(" from  t_wf_core_item i, t_wf_core_dofile d left join filed_bw_inf t on d.instanceid = t.instanceid")
			  .append(" where (d.isdelete != 1 or d.isdelete is null)  " +
			  		" and i.vc_sxlx = '1' " +
			  		" and d.item_id = i.id ")
			  .append( contionsql )
			  .append(" order by dotime desc");
		Query query=super.getEm().createNativeQuery(buffer.toString(),"LibraryResults");
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	public List<Pending> getStatisticalList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		StringBuffer querySql = (new StringBuffer("select p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				//.append("t.wfn_name as nodeName,")
				.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("p.wf_oldform_id as oldForm_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.RELATEDITEMID as RELATEDITEMID,")
				.append("p.CONTINUEINSTANCEID as continueInstanceId,")
				.append("i.vc_sxlx as item_type,")
				.append("p.apply_time as apply_time,")
				.append("p.step_index as stepIndex,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")	//剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")		//预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				.append(" ( case  when (p.JDQXDATE <= sysdate and p.finsh_time is null)  then '3' " +
						" when p.JDQXDATE <= p.finsh_time then '3' " +
						" else p.status end) as status, ")
				//p.is_end as isEnd,  办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append("p.is_back as is_back,")
				.append("'' as isDelaying, ")
				.append("p.isChildWf as isChildWf, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" (select e.employee_name from zwkj_employee e where e.employee_guid = p.user_uid)  as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i ," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid,wf_node n  ")
				.append(" where p.isexchanging=0 ")
				.append(" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1' ) ")
				.append(" and (p.finsh_time > p.jdqxdate or (sysdate > p.jdqxdate and p.finsh_time is null))  " +
						" and p.is_show=1 " +
						" and (p.is_back is null or p.is_back != '2') " +
						" and p.jdqxDate is not null " +
						" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
				
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			querySql.append(" and p.user_uid= '").append(userId).append("' ");
		}
		querySql.append(conditionSql)
				.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Pending> list = query.getResultList();
		return list;
	}
	
	public int getCountOfStatistical(String conditionSql,String userId,String type,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		
		StringBuffer query = new StringBuffer()
		.append("select count(*) " +
				" from T_WF_CORE_ITEM i ," +
				" t_wf_process p join zwkj_department d " +
				" on p.userdeptid = d.department_guid,wf_node n ")
		.append(" where p.isexchanging=0 ")
		.append(" and i.id = p.wf_item_uid " +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
		.append(" and (p.finsh_time > p.jdqxdate or (sysdate > p.jdqxdate and p.finsh_time is null)) " +
				" and p.is_show=1 " +
				" and (p.is_back is null or p.is_back != '2')" +
				" and p.jdqxDate is not null " +
				" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			query.append(" and p.user_uid= '").append(userId).append("' ");
		}
		query.append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}

	public List<Pending> getPressList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		StringBuffer querySql = (new StringBuffer("select p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				//.append("t.wfn_name as nodeName,")
				.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("p.wf_oldform_id as oldForm_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.RELATEDITEMID as RELATEDITEMID,")
				.append("p.CONTINUEINSTANCEID as continueInstanceId,")
				.append("i.vc_sxlx as item_type,")
				.append("p.apply_time as apply_time,")
				.append("p.step_index as stepIndex,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")	//剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")		//预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				.append(" ( case when p.JDQXDATE <= sysdate then '3' else p.status end) as status, ")
				//p.is_end as isEnd,  办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append("p.is_back as is_back,")
				.append("'' as isDelaying, ")
				.append("p.isChildWf as isChildWf, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append(" ( case when (select count(1) from t_wf_core_pressmsg pre where pre.processId = p.wf_process_uid) > 0 then '1' else '0' end) as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" (select e.employee_name from zwkj_employee e where e.employee_guid = p.user_uid)  as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i , " +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid ,wf_node n")
				.append(" where p.isexchanging=0 ")
				.append(" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1 " +
						" and (p.is_back is null or p.is_back != '2') " +
						" and p.jdqxDate is not null " +
						" and round(to_date(to_char(p.jdqxdate, 'yyyy-MM-dd'), 'yyyy-MM-dd') -" +
						" to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd')) <= 2 "+
						" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
				
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			querySql.append(" and p.user_uid= '").append(userId).append("' ");
		}
		querySql.append(conditionSql)
				.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	public int getCountOfPress(String conditionSql,String userId,String type,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		
		StringBuffer query = new StringBuffer()
		.append("select count(*) " +
				" from T_WF_CORE_ITEM i ," +
				" t_wf_process p join zwkj_department d " +
				" on p.userdeptid = d.department_guid ,wf_node n  ")
		.append(" where p.isexchanging=0 ")
		.append(" and i.id = p.wf_item_uid " +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
		.append(" and p.is_over='NOT_OVER' and p.is_show=1 " +
				" and (p.is_back is null or p.is_back != '2')" +
				" and p.jdqxDate is not null " +
				" and round(to_date(to_char(p.jdqxdate, 'yyyy-MM-dd'), 'yyyy-MM-dd') -" +
				" to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd')) <= 2 "+
				" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			query.append(" and p.user_uid= '").append(userId).append("' ");
		}
		query.append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}
	
	@Override
	public List<PressMsg> getMsgListByProcessId(String processId) {
		String hql = "from PressMsg t where ";
		hql += " processId = '" + processId+ "'  order by t.applyTime desc ";
		Query query = getEm().createQuery(hql);
		List<PressMsg> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}
	
	@Override
	public void addPressMsg(PressMsg pressMsg) {
		getEm().persist(pressMsg);
		
	}
	
	@Override
	public List<Pending> getPressMsgList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		StringBuffer querySql = (new StringBuffer("select p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				//.append("t.wfn_name as nodeName,")
				.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("p.wf_oldform_id as oldForm_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.RELATEDITEMID as RELATEDITEMID,")
				.append("p.CONTINUEINSTANCEID as continueInstanceId,")
				.append("i.vc_sxlx as item_type,")
				.append("p.apply_time as apply_time,")
				.append("p.step_index as stepIndex,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")	//剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")		//预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				.append(" ( case when p.JDQXDATE <= sysdate then '3' else p.status end) as status, ")
				//p.is_end as isEnd,  办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append("p.is_back as is_back,")
				.append("'' as isDelaying, ")
				.append("p.isChildWf as isChildWf, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append(" ( case when (select count(1) from t_wf_core_pressmsg pre where pre.processId = p.wf_process_uid) > 0 then '1' else '0' end) as pressStatus, ")
				.append(" m.presscontent as presscontent, ")
				.append(" (select e.employee_name from zwkj_employee e where e.employee_guid = p.user_uid)  as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid," +
						" t_wf_core_pressmsg m ,wf_node n  ")
				.append(" where p.isexchanging=0 ")
				.append(" and i.id = p.wf_item_uid  " +
						" and p.wf_process_uid = m.processid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1 " +
						" and (p.is_back is null or p.is_back != '2') " +
						" and p.jdqxDate is not null " +
						" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
				
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			querySql.append(" and p.user_uid= '").append(userId).append("' ");
		}
		querySql.append(conditionSql)
				.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Pending> list = query.getResultList();
		return list;
	}
	
	@Override
	public int getCountOfPressMsg(String conditionSql,String userId,String type,HashMap<String,String> map) {
		String isAdmin = "";
		if(null!=map){
			isAdmin = map.get("isAdmin");
		}
		
		StringBuffer query = new StringBuffer()
		.append("select count(*) " +
				" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid," +
				" t_wf_core_pressmsg m ,wf_node n ")
		.append(" where p.isexchanging=0 ")
		.append(" and i.id = p.wf_item_uid  " +
				" and p.wf_process_uid = m.processid " +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
		.append(" and p.is_over='NOT_OVER' and p.is_show=1 " +
				" and (p.is_back is null or p.is_back != '2')" +
				" and p.jdqxDate is not null " +
				" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		if(CommonUtil.stringNotNULL(isAdmin) && "1".equals(isAdmin)){
		}else{
			query.append(" and p.user_uid= '").append(userId).append("' ");
		}
		query.append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}

	@Override
	public void addHandRoundShip(HandRoundShip entity) {
		if(null == entity){
			return;
		}
		getEm().persist(entity);
	}

	@Override
	public void updateHandRoundShip(HandRoundShip entity) {
		if(null == entity){
			return;
		}
		//String sql = "update HandRoundShip t set t.isRead='1' where t.id='"+entity.getId()+"'";
		getEm().merge(entity);
	}

	@Override
	public HandRoundShip selectHandRoundShip(String userId, String instanceId) {
		String hql = " from HandRoundShip t where t.userId=:userId and t.instanceId=:instanceId";
		Query query = getEm().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("instanceId", instanceId);
		List<HandRoundShip> list = query.getResultList();
		if(list != null){
			if(list.size() == 1){
				return list.get(0);
			}else if(list.size() > 1){
				int i = 0;
				for (HandRoundShip handRoundShip : list) {
					if(i != 0){
						getEm().remove(handRoundShip);
					}
				}
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public Integer countHandRoundShips(String instanceId){
		String hql = "select count(*) from HandRoundShip t where t.instanceId=:instanceId";
		Query query = getEm().createQuery(hql);
		query.setParameter("instanceId", instanceId);
		
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@Override
	public List<HandRoundShip> selectHandRoundShips(String instanceId, Integer pageIndex, Integer pageSize) {
		
		String sql = "select t.* from T_WF_CORE_HANDROUND_SHIP t,ZWKJ_EMPLOYEE e,ZWKJ_DEPARTMENT d where t.INSTANCEID='"+instanceId+"' and t.USERID = e.EMPLOYEE_GUID and e.DEPARTMENT_GUID = d.DEPARTMENT_GUID order by d.TABINDEX,e.TABINDEX ";
		Query query = getEm().createNativeQuery(sql,HandRoundShip.class);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
}
