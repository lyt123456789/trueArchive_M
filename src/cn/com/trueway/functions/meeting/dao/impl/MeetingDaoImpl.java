package cn.com.trueway.functions.meeting.dao.impl;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.functions.meeting.dao.MeetingDao;
import cn.com.trueway.functions.meeting.entity.Chry;
import cn.com.trueway.functions.meeting.entity.Chry4Out;
import cn.com.trueway.functions.meeting.entity.Hyqj;
import cn.com.trueway.functions.meeting.entity.Hytz;
import cn.com.trueway.functions.meeting.entity.Sgtjhytz;

public class MeetingDaoImpl extends BaseDao implements MeetingDao {
	
	private static Logger logger = Logger.getLogger(MeetingDaoImpl.class);


	public int getCountOfMyRegister(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select * from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_office_sgtjhytz t ")
		 
		.append(" where p.owner= '").append(userId).append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ");
		  
		query.append("  and p.is_show=1")
		.append(conditionSql);
		query.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
	    .append(" where p3.owner= '").append(userId).append("' ");
	    if(itemids != null && !"".equals(itemids)){
	    	query.append( " and p.wf_item_uid in (")
				.append(itemids)
				.append(") ");
		}
		
		query.append(" and p3.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  and p3.wf_instance_uid=p.wf_instance_uid )");
		return super.getEm().createNativeQuery(query.toString()).getResultList().size();
	}

	@Override
	public List<Hytz> getMyRegisterList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		    StringBuffer querySql = (new StringBuffer("select "))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.vc_sxmc as item_type,")
				.append("p.step_index as stepIndex,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				
				.append("(select t.MEETINGNAME from t_wf_office_sgtjhytz t where t.instanceid = p.wf_instance_uid) as MEETINGNAME,")
				
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson")
				.append(" from t_wf_process p ,T_WF_CORE_ITEM i  , t_wf_office_sgtjhytz t")

				.append(" where p.owner= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid")
				.append(" and p.wf_f_instance_uid in (select distinct p4.wf_instance_uid from t_wf_process p4 ");
				if(itemids != null && !"".equals(itemids)){
					querySql.append( " where p4.wf_item_uid in (")
						.append(itemids)
						.append(") ");
				}

				querySql.append(" ) ")			 
				.append(" and  p.is_show=1 ")
				.append(conditionSql)
				
				.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
				.append(" where p3.owner= '")
				.append(userId)
				.append("' ")
				.append( " and p3.wf_instance_uid=p.wf_instance_uid   and p3.is_show=1) " )
				
				 
				.append( " and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		       
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),"HyResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		logger.info(querySql.toString());
		return query.getResultList();
	}
	
	public int getCountOfMyChmd(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select * from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_sgtjhytz t ")
		.append(" where p.owner= '").append(userId).append("' and i.id=p.wf_item_uid  and t.instanceid = p.wf_instance_uid ");
		query.append("  and p.is_show=1")
		.append(conditionSql);
		query.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
	    .append(" where p3.owner= '").append(userId).append("' ");
	    if(itemids != null && !"".equals(itemids)){
	    	query.append( " and p.wf_item_uid in (")
				.append(itemids)
				.append(") ");
		}
//	    if(searchDateFrom != null && !"".equals(searchDateFrom)){
//	    	query.append(" and to_date(t.applystartdate,'yyyy-mm-dd')>=date'")	
//			.append(searchDateFrom)
//			.append("' ");
//		}
//		if(searchDateTo != null && !"".equals(searchDateTo)){
//			query.append(" and to_date(t.applyenddate,'yyyy-mm-dd')<=date'")	
//			.append(searchDateTo)
//			.append("' ");
//		}
		
		query.append(" and p3.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  and p3.wf_instance_uid=p.wf_instance_uid )");
		return super.getEm().createNativeQuery(query.toString()).getResultList().size();
	}

	@Override
	public List<Hytz> getMyChmdList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		    StringBuffer querySql = (new StringBuffer("select "))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.vc_sxmc as item_type,")
				.append("p.step_index as stepIndex,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				
//				.append("(select t.carnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as carnumber,")
//				.append("(select t.applystartdate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applystartdate,")
//				.append("(select t.applyenddate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyenddate,")
//				.append("(select t.applyreason from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyreason,")
//				.append("(select t.applycomment from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applycomment,")
//				.append("(select t.plancarnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as plancarnumber,")
				
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson")
				.append(" from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_sgtjhytz t ")
				.append(" where p.owner= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ")
				.append(" and  p.is_show=1 ")
				.append(conditionSql)
				
				.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
				.append(" where p3.owner= '")
				.append(userId)
				.append("' ")
				.append( " and p3.wf_instance_uid=p.wf_instance_uid   and p3.is_show=1) " )
				
				 
				.append( " and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		       
				if(itemids != null && !"".equals(itemids)){
					querySql.append( "and p.wf_item_uid in (")
						.append(itemids)
						.append(") ");
				}
					
//				if(searchDateFrom != null && !"".equals(searchDateFrom)){
//					querySql.append(" and to_date(t.applystartdate,'yyyy-mm-dd')>=date'")	
//					.append(searchDateFrom)
//					.append("' ");
//				}
//				if(searchDateTo != null && !"".equals(searchDateTo)){
//					querySql.append(" and to_date(t.applyenddate,'yyyy-mm-dd')<=date'")	
//					.append(searchDateTo)
//					.append("' ");
//				}
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),"HyResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		logger.info(querySql.toString());
		return query.getResultList();
	}

	public int getCountOfMyHyqj(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select * from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_sgtjhytz t ")
		.append(" where p.owner= '").append(userId).append("' and i.id=p.wf_item_uid  and t.instanceid = p.wf_instance_uid ");
		query.append("  and p.is_show=1")
		.append(conditionSql);
		query.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
	    .append(" where p3.owner= '").append(userId).append("' ");
	    if(itemids != null && !"".equals(itemids)){
	    	query.append( " and p.wf_item_uid in (")
				.append(itemids)
				.append(") ");
		}
//	    if(searchDateFrom != null && !"".equals(searchDateFrom)){
//	    	query.append(" and to_date(t.applystartdate,'yyyy-mm-dd')>=date'")	
//			.append(searchDateFrom)
//			.append("' ");
//		}
//		if(searchDateTo != null && !"".equals(searchDateTo)){
//			query.append(" and to_date(t.applyenddate,'yyyy-mm-dd')<=date'")	
//			.append(searchDateTo)
//			.append("' ");
//		}
		
		query.append(" and p3.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  and p3.wf_instance_uid=p.wf_instance_uid )");
		return super.getEm().createNativeQuery(query.toString()).getResultList().size();
	}

	@Override
	public List<Hytz> getMyHyqjList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		    StringBuffer querySql = (new StringBuffer("select "))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.vc_sxmc as item_type,")
				.append("p.step_index as stepIndex,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				
//				.append("(select t.carnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as carnumber,")
//				.append("(select t.applystartdate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applystartdate,")
//				.append("(select t.applyenddate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyenddate,")
//				.append("(select t.applyreason from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyreason,")
//				.append("(select t.applycomment from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applycomment,")
//				.append("(select t.plancarnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as plancarnumber,")
				
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson")
				.append(" from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_sgtjhytz t ")
				.append(" where p.owner= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ")
				.append(" and  p.is_show=1 ")
				.append(conditionSql)
				
				.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
				.append(" where p3.owner= '")
				.append(userId)
				.append("' ")
				.append( " and p3.wf_instance_uid=p.wf_instance_uid   and p3.is_show=1) " )
				
				 
				.append( " and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		       
				if(itemids != null && !"".equals(itemids)){
					querySql.append( "and p.wf_item_uid in (")
						.append(itemids)
						.append(") ");
				}
					
//				if(searchDateFrom != null && !"".equals(searchDateFrom)){
//					querySql.append(" and to_date(t.applystartdate,'yyyy-mm-dd')>=date'")	
//					.append(searchDateFrom)
//					.append("' ");
//				}
//				if(searchDateTo != null && !"".equals(searchDateTo)){
//					querySql.append(" and to_date(t.applyenddate,'yyyy-mm-dd')<=date'")	
//					.append(searchDateTo)
//					.append("' ");
//				}
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),"HyqjResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		logger.info(querySql.toString());
		return query.getResultList();
	}

	public Clob getPerson(String instanceId,String columnName){
		String sql = "select "+columnName+" from t_wf_office_sgtjhytz t where t.INSTANCEID='"+instanceId+"'";
		
//		return (Clob)super.getEm().createNativeQuery(sql).getSingleResult();
		Clob person = null;
        try{
        	person = (Clob)super.getEm().createNativeQuery(sql).getSingleResult();
        }catch(NoResultException e){
        	logger.warn("=======No entity found for query=====");
        }
        return person;
	}
	
	public int getCountOfChmd(String eploeIds){
		
		String sql = "select b.DEPARTMENT_NAME,a.EMPLOYEE_NAME,a.EMPLOYEE_JOBTITLES,a.EMPLOYEE_MOBILE from ZWKJ_EMPLOYEE a,ZWKJ_DEPARTMENT b where a.DEPARTMENT_GUID=b.DEPARTMENT_GUID and a.EMPLOYEE_GUID in "+eploeIds;
		
		return super.getEm().createNativeQuery(sql).getResultList().size();
	}
	
	public List<Chry> getChmdList(Integer pageIndex, Integer pageSize,String eploeIds){
		
		String sql = "select b.DEPARTMENT_NAME,a.EMPLOYEE_NAME,a.EMPLOYEE_JOBTITLES,a.EMPLOYEE_MOBILE,a.EMPLOYEE_GUID from ZWKJ_EMPLOYEE a,ZWKJ_DEPARTMENT b where a.DEPARTMENT_GUID=b.DEPARTMENT_GUID and a.EMPLOYEE_GUID in "+eploeIds;
		
		
		Query query = super.getEm().createNativeQuery(sql,"ChryResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	public int getCountOfHyqj(String instanceId){
		
		String sql = "select * from T_WF_OFFICE_SGTJHYQJ t where t.HYSLID='"+instanceId+"'";
		
		return super.getEm().createNativeQuery(sql).getResultList().size();
	}
	
	public List<Hyqj> getHyqjList(Integer pageIndex, Integer pageSize,String instanceId){

	    StringBuffer querySql = (new StringBuffer("select "))
			.append("p.wf_process_uid as wf_process_uid,")
			.append("p.wf_node_uid as wf_node_uid,")
			.append("p.wf_instance_uid as wf_instance_uid,")
			.append("p.wf_uid as wf_workflow_uid,")
			.append("p.process_title as process_title,")
			.append("p.wf_item_uid as item_id,")
			.append("p.wf_form_id as form_id,")
			.append("i.vc_sxmc as item_name,")
			.append("i.vc_sxmc as item_type,")
			.append("p.step_index as stepIndex,")
			.append("p.apply_time as apply_time,")
			.append("p.finsh_time as finish_time,")
			.append(" '未知' as remainTime,")
			// 剩余时间，先不赋真实值，根据期限算出
			.append(" '0' as warnType,")
			// 预警类型，先不赋真实值，根据期限算出
			.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
			.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
			
			.append("(select splitstr(t.QJR,2,'*') from T_WF_OFFICE_SGTJHYQJ t where t.instanceid = p.wf_instance_uid) as QJR,")
			.append("(select t.QJYY from T_WF_OFFICE_SGTJHYQJ t where t.instanceid = p.wf_instance_uid) as QJYY,")
			.append("(select t.SPJG from T_WF_OFFICE_SGTJHYQJ t where t.instanceid = p.wf_instance_uid) as SPJG,")
//				.append("(select t.applyreason from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyreason,")
//				.append("(select t.applycomment from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applycomment,")
//				.append("(select t.plancarnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as plancarnumber,")
			
			.append("p.is_over as is_over,")
			.append("p.status as status,")
			.append("p.is_master as isMaster,")
			.append("p.pdfPath as pdfPath,")
			.append("p.commentJson as commentJson")
			.append(" from t_wf_process p ,T_WF_CORE_ITEM i , T_WF_OFFICE_SGTJHYQJ t ")
			.append(" where i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ")
			.append(" and  p.is_show=1 ")
			.append(" and  t.HYSLID='"+instanceId+"'")
			.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
			.append(" where p3.wf_instance_uid=p.wf_instance_uid   and p3.is_show=1) " )
			
			.append( " and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
	       
			querySql.append(" order by p.apply_time desc ");
	
	    
	    Query query = super.getEm().createNativeQuery(querySql.toString(),"HyqjResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		logger.info(querySql.toString());
		return query.getResultList();
	}
	
	public int getAllHyCount(String conditionSql){
		String sql = "select t.declaresn as declaresn,t.applicant as applicant,t.approveitem_id as approveitem_id" +
				",t.formid as formid,t.workflowid as workflowid,t.processid as processid" +
				",t.instanceid as instanceid,t.meetingname as meetingname,t.newtitle as newtitle" +
				",t.newtt as newtt,t.newrl as newrl,t.newlk as newlk" +
				",t.newtime as newtime,t.arr as arr,t.jsy as jsy" +
				",t.dx as dx,t.sbmd as sbmd,t.personid as personid" +
				",t.personname as personname,t.dxryid as dxryid,t.dxryname as dxryname" +
				" from t_wf_office_sgtjhytz t where t.instanceid is not null "+
				conditionSql+" order by t.newtime desc";
		return super.getEm().createNativeQuery(sql,"HyList").getResultList().size();
	}
	
	public List<Sgtjhytz> getAllHy(Integer pageIndex, Integer pageSize,String conditionSql){
		String sql = "select t.declaresn as declaresn,t.applicant as applicant,t.approveitem_id as approveitem_id" +
				",t.formid as formid,t.workflowid as workflowid,t.processid as processid" +
				",t.instanceid as instanceid,t.meetingname as meetingname,t.newtitle as newtitle" +
				",t.newtt as newtt,t.newrl as newrl,t.newlk as newlk" +
				",t.newtime as newtime,t.arr as arr,t.jsy as jsy" +
				",t.dx as dx,t.sbmd as sbmd,t.personid as personid" +
				",t.personname as personname,t.dxryid as dxryid,t.dxryname as dxryname" +
				" from t_wf_office_sgtjhytz t where t.instanceid is not null "+
				conditionSql+" order by t.newtime desc";
		
		Query query = super.getEm().createNativeQuery(sql,"HyList");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		
		return query.getResultList();
	}
	
	public List<String> getQjry(String instanceId){
		String sql = "select distinct splitstr(a.qjr,2,'*') from t_wf_office_sgtjhyqj a where a.hyslid='"+instanceId+"'";
		
		return getEm().createNativeQuery(sql).getResultList();
	}
	

	@Override
	public int getCountMeetingOuts(String depids, String conditionSql) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("select distinct m.instanceid from t_wf_core_dofile t,t_wf_core_item i,t_wf_office_sgtjhytz m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null) ");
//		sb.append(" and t.instanceid=m.instanceid and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24:mi')");
//		if ( CommonUtil.stringNotNULL(depids)){
//			sb.append(" and i.vc_ssbmid='" + depids + "' ");
//		}
//		return super.getEm().createNativeQuery(sb.toString()).getResultList().size();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct m.meetingname,m.newtitle,m.newtime,m.arr,m.instanceid from t_wf_core_dofile t,T_WF_CORE_ITEM i,t_wf_process p,t_wf_office_sgtjhytz m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null)");
		sb.append(" and t.instanceid = p.wf_f_instance_uid and p.wf_instance_uid = m.instanceid and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24:mi')");
		if ( CommonUtil.stringNotNULL(depids)){
			sb.append(" and i.vc_ssbmid='" + depids + "' ");
		}
		sb.append(conditionSql).append(" order by m.newtime desc");
		Query query=super.getEm().createNativeQuery(sb.toString());
		return query.getResultList().size();
	}

	@Override
	public List<Hytz> getMeetingOutList(String depids,String conditionSql, Integer pageIndex, Integer pageSize) {
		List<Hytz> returnlist = new ArrayList<Hytz>();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct m.meetingname,m.newtitle,m.newtime,m.arr,m.instanceid,p.wf_process_uid,p.wf_form_id,p.wf_item_uid from t_wf_core_dofile t,T_WF_CORE_ITEM i,t_wf_process p,t_wf_office_sgtjhytz m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null)");
		sb.append(" and t.instanceid = p.wf_f_instance_uid and p.wf_instance_uid = m.instanceid and p.step_index = 1 ");
//		sb.append(" and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24:mi')");
		if ( CommonUtil.stringNotNULL(depids)){
			sb.append(" and i.vc_ssbmid='" + depids + "' ");
			sb.append(" and m.departmentid='" + depids + "' ");
		}
		sb.append(conditionSql).append(" order by m.newtime desc");
		Query query=super.getEm().createNativeQuery(sb.toString());
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		if(null !=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				Hytz meetingapplyout = new Hytz();
				meetingapplyout.setMeetingname(obj[0]==null?"":obj[0].toString());
				meetingapplyout.setNewtitle(obj[1]==null?"":obj[1].toString());
				meetingapplyout.setNewtime(obj[2]==null?"":obj[2].toString());
				meetingapplyout.setArr(obj[3]==null?"":obj[3].toString());
				meetingapplyout.setWf_instance_uid(obj[4]==null?"":obj[4].toString());
				meetingapplyout.setWf_process_uid(obj[5]==null?"":obj[5].toString());
				meetingapplyout.setForm_id(obj[6]==null?"":obj[6].toString());
				meetingapplyout.setItem_id(obj[7]==null?"":obj[7].toString());
				returnlist.add(meetingapplyout);
			}
		}
		return returnlist;
	}
	/*
	 * lul 2014-4-2
	 */
	public List<Chry4Out> getChmdList4Out(Integer pageIndex, Integer pageSize,String eploeIds){
		
		//String sql = "select b.superior_guid,b.department_guid, b.DEPARTMENT_NAME,a.EMPLOYEE_NAME,a.EMPLOYEE_JOBTITLES,a.EMPLOYEE_MOBILE,a.EMPLOYEE_GUID from ZWKJ_EMPLOYEE a,ZWKJ_DEPARTMENT b where a.DEPARTMENT_GUID=b.DEPARTMENT_GUID and a.EMPLOYEE_GUID in "+eploeIds+"  order by b.tabindex ,a.tabindex ";
		String sql = "select  t.department_name as superdepartment_name,"+
	        "bb.superior_guid,bb.department_guid, bb.DEPARTMENT_NAME,bb.EMPLOYEE_NAME,bb.EMPLOYEE_JOBTITLES,bb.EMPLOYEE_MOBILE,bb.EMPLOYEE_GUID "+
		    " from (select b.superior_guid,b.department_guid, b.DEPARTMENT_NAME, a.EMPLOYEE_NAME,a.EMPLOYEE_JOBTITLES, a.EMPLOYEE_MOBILE, a.EMPLOYEE_GUID, a.tabindex as emptabindex ,"+
		    " b.tabindex as deptabindex  from ZWKJ_EMPLOYEE a, ZWKJ_DEPARTMENT b  where a.DEPARTMENT_GUID = b.DEPARTMENT_GUID "+
	        " and a.EMPLOYEE_GUID in "+eploeIds+" ) bb " +
	    	"  left join ZWKJ_DEPARTMENT t    on t.department_guid = bb.superior_guid order by t.tabindex, bb.deptabindex, bb.emptabindex";
		
		Query query = super.getEm().createNativeQuery(sql,"ChryResults4Out");

//		if (pageIndex != null && pageSize != null) {
//			query.setFirstResult(pageIndex);
//			query.setMaxResults(pageSize);
//		}
		return query.getResultList();
	}
	
	public List getMastUserid(String instanceId){
		String sql = "select t.user_uid from t_wf_process t where t.wf_f_instance_uid='"+instanceId+"'";
		return 	getEm().createNativeQuery(sql).getResultList();
		//return query.getResultList();
	}
	public List getSupDepIdByUserid(String userid){
		String sql = "select * from zwkj_department sd where sd.department_guid in  (select d.superior_guid   from zwkj_department d where d.department_guid in   (select t.department_guid  from zwkj_employee t  where t.employee_guid in " +userid+"))order by sd.tabindex";
		return 	getEm().createNativeQuery(sql).getResultList();	
	}
	
	public int checkMeetingTime(String meetingBeginTime, String meetingEndTime, String roonName){
		String sql="select * from workflow_wxbt.t_wf_office_applymeet t1 ";
		sql += " where t1.roomname like '%"+ roonName +"%' and  t1.state = '1' and ";
		sql += " ((to_date('"+ meetingBeginTime +" ', 'yyyy-mm-dd hh24:mi') >= to_date(t1.meeting_begin_time, 'yyyy-mm-dd hh24:mi') ";
		sql += " and to_date('"+ meetingBeginTime +" ', 'yyyy-mm-dd hh24:mi') <= to_date(t1.meeting_end_time, 'yyyy-mm-dd hh24:mi'))";
		sql += " or (to_date('"+ meetingBeginTime +" ', 'yyyy-mm-dd hh24:mi') <= to_date(t1.meeting_begin_time, 'yyyy-mm-dd hh24:mi')";
		sql += " and to_date('"+ meetingEndTime +" ', 'yyyy-mm-dd hh24:mi') >= to_date(t1.meeting_end_time, 'yyyy-mm-dd hh24:mi'))";
		sql += " or (to_date('"+ meetingEndTime +" ', 'yyyy-mm-dd hh24:mi') >= to_date(t1.meeting_begin_time, 'yyyy-mm-dd hh24:mi')";
		sql += " and to_date('"+ meetingEndTime +" ', 'yyyy-mm-dd hh24:mi') <= to_date(t1.meeting_end_time, 'yyyy-mm-dd hh24:mi')))";
		List<Object[]> list = this.getEm().createNativeQuery(sql.toString()).getResultList();
		return list.size();
	}

}
