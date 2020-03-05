package cn.com.trueway.functions.car.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.functions.car.dao.ClOutDao;
import cn.com.trueway.functions.car.entity.ClOut;


public class ClOutDaoImpl extends BaseDao implements ClOutDao {

	private static Logger logger = Logger.getLogger(ClOutDaoImpl.class);
	

	public int getCountOfMyApply(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select * from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_gtjccsq t ")
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
	    if(searchDateFrom != null && !"".equals(searchDateFrom)){
	    	query.append(" and to_date(t.applystartdate,'yyyy-mm-dd hh24:mi')>=to_date('")	
			.append(searchDateFrom)
			.append("','yyyy-mm-dd hh24:mi') ");
		}
		if(searchDateTo != null && !"".equals(searchDateTo)){
			query.append(" and to_date(t.applyenddate,'yyyy-mm-dd hh24:mi')<=to_date('")	
			.append(searchDateTo)
			.append("','yyyy-mm-dd hh24:mi') ");
		}
		
		query.append(" and p3.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  and p3.wf_instance_uid=p.wf_instance_uid )");
		return super.getEm().createNativeQuery(query.toString()).getResultList().size();
	}
	//获得我的请假 条 加上 流程名
	@Override
	public List<ClOut> getMyApplyList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
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
				
				.append("(select t.carnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as carnumber,")
				.append("(select t.applystartdate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applystartdate,")
				.append("(select t.applyenddate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyenddate,")
				.append("(select t.applyreason from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyreason,")
				.append("(select t.applycomment from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applycomment,")
				.append("(select t.plancarnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as plancarnumber,")
				
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson")
				.append(" from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_gtjccsq t ")
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
					
				if(searchDateFrom != null && !"".equals(searchDateFrom)){
					querySql.append(" and to_date(t.applystartdate,'yyyy-mm-dd hh24:mi')>=to_date('")	
					.append(searchDateFrom)
					.append("','yyyy-mm-dd hh24:mi') ");
				}
				if(searchDateTo != null && !"".equals(searchDateTo)){
					querySql.append(" and to_date(t.applyenddate,'yyyy-mm-dd hh24:mi')<=to_date('")	
					.append(searchDateTo)
					.append("','yyyy-mm-dd hh24:mi') ");
				}
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),"CarOutResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		logger.info(querySql.toString());
		return query.getResultList();
	}
	
	public int getCountOfCarPending(String userId,String itemid,String searchDateFrom,String searchDateTo) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";

		StringBuffer query = new StringBuffer()
		.append("select *  from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_office_gtjccsq t ")
		.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ");
		 if(itemids != null && !"".equals(itemids)){
		    	query.append( " and p.wf_item_uid in (")
					.append(itemids)
					.append(") ");
			}
		 if(searchDateFrom != null && !"".equals(searchDateFrom)){
		    	query.append(" and to_date(t.applystartdate,'yyyy-mm-dd hh24:mi')>=to_date('")	
				.append(searchDateFrom)
				.append("','yyyy-mm-dd hh24:mi') ");
			}
			if(searchDateTo != null && !"".equals(searchDateTo)){
				query.append(" and to_date(t.applyenddate,'yyyy-mm-dd hh24:mi')<=to_date('")	
				.append(searchDateTo)
				.append("','yyyy-mm-dd hh24:mi') ");
			}
		query.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		return super.getEm().createNativeQuery(query.toString()).getResultList().size();
	}

	public List<ClOut> getPendingCarList(String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
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
				.append("i.vc_sxlx as item_type,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				.append("p.step_index as stepIndex,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append("(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append("(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				//添加出车申请表相关
				.append("(select t.carnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as carnumber,")
				.append("(select t.applystartdate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applystartdate,")
				.append("(select t.applyenddate from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyenddate,")
				.append("(select t.applyreason from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applyreason,")
				.append("(select t.applycomment from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as applycomment,")
				.append("(select t.plancarnumber from t_wf_office_gtjccsq t where t.instanceid = p.wf_instance_uid) as plancarnumber,")
				
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.pdfPath as pdfPath,")
				.append("p.is_master as isMaster,")
				.append("p.commentJson as commentJson")
				.append(" from t_wf_process p ,T_WF_CORE_ITEM i , t_wf_office_gtjccsq t ")
				.append(" where p.user_uid= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid and t.instanceid = p.wf_instance_uid ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		if(itemids != null && !"".equals(itemids)){
			querySql.append(" and p.wf_item_uid in (")	
			.append(itemids)
			.append(") ");
		}
		if(searchDateFrom != null && !"".equals(searchDateFrom)){
			querySql.append(" and to_date(t.applystartdate,'yyyy-mm-dd hh24:mi')>=to_date('")	
			.append(searchDateFrom)
			.append("','yyyy-mm-dd hh24:mi') ");
		}
		if(searchDateTo != null && !"".equals(searchDateTo)){
			querySql.append(" and to_date(t.applyenddate,'yyyy-mm-dd hh24:mi')<=to_date('")	
			.append(searchDateTo)
			.append("','yyyy-mm-dd hh24:mi') ");
		}
		querySql.append(" order by p.apply_time desc ");
		Query query = super.getEm().createNativeQuery(querySql.toString(),"CarOutResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
}
