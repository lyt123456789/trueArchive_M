/**
 * 文件名称:EWorkFlowDaoImpl.java
 * 作者:zhuxc<br>
 * 创建时间:2014-1-16 下午01:47:22
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.functions.workflow.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.functions.workflow.dao.EWorkFlowDao;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;

/**
 * 描述： 对EWorkFlowDaoImpl进行描述
 * 作者：zhuxc
 * 创建时间：2014-1-16 下午01:47:22
 */
public class EWorkFlowDaoImpl extends BaseDao implements EWorkFlowDao {

	/**
	 * @param userId
	 * @param i
	 * @param parseInt
	 * @param parseInt2
	 * @param string
	 * @param itemIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Pending> getPendListOfMobile(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition += " and i.vc_sxlx = '"+ type +"'";
		}
		if(!("").equals(itemIds) && itemIds != null){
			itemIds = "'"+itemIds.replaceAll(",", "','")+"'";
			condition += " and i.id in ("+ itemIds +")";
		}
		condition += " and (p.action_status is null or p.action_status!=2) ";
		StringBuffer querySql = (new StringBuffer("select  p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("p.wf_oldform_id as oldForm_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.vc_sxlx as item_type,")
				.append("i.RELATEDITEMID as RELATEDITEMID,")
				.append("p.CONTINUEINSTANCEID as continueInstanceId,")
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
				.append("p.status as status,")
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
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i ," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid ,wf_node n")
				.append(" where p.user_uid= '").append(userId).append("' and p.isexchanging=0 " +
						" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
			    .append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2')  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(condition)
				.append(" order by p.apply_time desc ");
		
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
        int i = (pageIndex - 1) * pageSize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
			
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getHaveDone4Breeze(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds,String timetype){
		String sql = "	select t.process_title ,t.is_over,t.apply_time,t.finsh_time,to_char(t.jssj,'YYYY-MM-DD HH24:MI') from t_wf_process t where  " +
				"t.jssj is not null  and t.user_uid='"+userId+"' " ;
		//0-全部  1-本周  2-本月  3-本年		
		if(timetype.equals("0")){
		}else if(timetype.equals("1")){
			//本周
			sql =	sql+" and t.jssj>=trunc(next_day(sysdate-8,1)+1) and t.jssj<=trunc(next_day(sysdate-8,1)+7)+1 ";
		}else if(timetype.equals("2")){
			// 2-本月
			sql =	sql+" and t.jssj>=trunc(sysdate,'MM') and t.jssj<=last_day(sysdate) ";
		}else if(timetype.equals("3")){
			//  3-本年
			sql =	sql+" and to_char(t.jssj,'yyyy')= to_char(sysdate,'yyyy') ";
		}		
		    sql = sql+ " order by t.jssj desc";
		/*String yearSql = " select t.process_title ,t.is_over,t.apply_time,t.finsh_time,t.jssj from t_wf_process t where t.jssj is not null  and t.user_uid='"+userId+"' and to_char(t.jssj,'yyyy')= to_char(sysdate,'yyyy') order by t.jssj desc";
		String weekSql = " select t.process_title ,t.is_over,t.apply_time,t.finsh_time,t.jssj from t_wf_process t where t.jssj is not null  and t.user_uid='{09870F0B-0000-0000-424B-12F40000002A}' and t.jssj>=trunc(next_day(sysdate-8,1)+1) and t.jssj<=trunc(next_day(sysdate-8,1)+7)+1 order by t.jssj desc ";
		String monthSql = " select t.process_title ,t.is_over,t.apply_time,t.finsh_time,t.jssj from t_wf_process t where t.jssj is not null  and t.user_uid='{09870F0B-0000-0000-424B-12F40000002A}' and t.jssj>=trunc(sysdate,'MM') and t.jssj<=last_day(sysdate) order by t.jssj desc ";
		*/
		Query query = super.getEm().createNativeQuery(sql);
        int i = (pageIndex - 1) * pageSize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
			
		return query.getResultList();
	
	}
	
	
	@Override
	public int getPendListCount(String userId, int count, String type,
			String itemIds) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition += " and i.vc_sxlx = '"+ type +"'";
		}
		if(!("").equals(itemIds) && itemIds != null){
			itemIds = "'"+itemIds.replaceAll(",", "','")+"'";
			condition += " and i.id in ("+ itemIds +")";
		}
		condition += " and (p.action_status is null or p.action_status!=2) ";
		StringBuffer querySql = new StringBuffer();
		querySql.append("select count(*) ")
		.append(" from t_wf_process p ,T_WF_CORE_ITEM i ,wf_node n")
		.append(" where p.user_uid= '").append(userId).append("' and p.isexchanging=0 " +
				" and i.id = p.wf_item_uid " +
				" and p.wf_node_uid=n.wfn_id " +
				" and  (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
		.append(" and p.is_over='NOT_OVER' and p.is_show=1 and  (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
		.append(condition)
		.append(" order by p.apply_time desc ");
		// TODO Auto-generated method stub
		return Integer.parseInt(getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
	}

	/**
	 * @param userId
	 * @param i
	 * @param parseInt
	 * @param parseInt2
	 * @param string
	 * @param itemIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Pending> getTodo4WebNew(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition += " and i.vc_sxlx = '"+ type +"'";
		}
		if(!("").equals(itemIds) && itemIds != null){
			itemIds = "'"+itemIds.replaceAll(",", "','")+"'";
			condition += " and i.id in ("+ itemIds +")";
		}
		if(!("").equals(userId) && userId != null){
			condition += " and p.user_uid = '"+ userId +"'";
		}
		condition += " and (p.action_status is null or p.action_status!=2) ";
		StringBuffer querySql = (new StringBuffer("select  p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
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
				//p.is_end as isEnd,  办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.commentJson as commentJson,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append("p.is_back as is_back,")
				.append("'' as isDelaying, ")
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.isChildWf as isChildWf, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append("p.isManyInstance as isManyInstance,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ")
				.append(" where p.isexchanging=0  and i.id = p.wf_item_uid  and p.fromnodeid != p.tonodeid ")
			    .append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(condition)
				.append(" order by p.apply_time desc ");
		
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
        int i = (pageIndex - 1) * pageSize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
			
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> serachDataByStatus(String conditionSql, int count,
			Integer pagesize, String status, List<FieldSelectCondition> selects) {
		StringBuffer querySql = new StringBuffer("select p.wf_process_uid as processId,")
				.append("p.wf_instance_uid as instanceId,")
				.append("p.wf_uid as workflowId,")
				.append("replace(p.process_title, chr(10), '')  as title,")
				.append("d.department_name as commitDept,")
				.append("e.employee_name as commitUser,");
				if("1".equals(status)){
					querySql.append("to_char(p.finsh_time,'YYYY-MM-DD HH24:MI') as commitTime,");
				}else{
					querySql.append("to_char(p.apply_time,'YYYY-MM-DD HH24:MI') as commitTime,");
				}
				querySql.append("i.vc_sxmc as itemType,")
				.append("decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) as status, ")
				.append("p.wf_item_uid as item_id, ")
				.append("p.wf_form_id as form_id, ")
				.append("p.step_index as stepIndex, ")
				.append("p.isChildWf as isChildWf,  ")
				.append("i.vc_sxlx as item_type,  ")
				.append("(select decode(count(*), 0, 0, 1) from t_wf_core_dofile f, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = f.instanceid  or p.wf_f_instance_uid = f.instanceid ) and f.id = fav.dofileid and fav.userid= :userId) as favourite ,")
				.append("p.jssj as isRead, ")
				.append("p.pdfPath as pdfPath");
				if(status.equals("0")){
					querySql.append(",replace(replace(replace(replace(v.jjcd, '特急', '3'), '急', '2'),'一般',''),' ','') as urgency");
				}
				querySql.append(",p.is_back as isback");
				querySql.append(",p.wf_node_uid as nodeid");
				querySql.append(",p.ismanyinstance as ismanyinstance ");
				if(status.equals("1") ||status.equals("2") || status.equals("4") || status.equals("5")){
					querySql.append(",replace(replace(replace(replace(v.jjcd, '特急', '3'), '急', '2'),'一般',''),' ','') as urgency");
				}
				querySql.append(",p.user_uid as userId ");
				querySql.append(" from t_wf_core_dofile do, t_wf_process p left join wh_view v on p.wf_instance_uid=v.instanceid ," +
						"t_wf_core_item i, zwkj_employee e, zwkj_department d,wf_node n");
				if(status.equals("5")){
					querySql.append(" ,t_wf_core_follow_ship sh ");
				}
				if(status.equals("7")){
					querySql.append(",t_wf_core_dofile_favourite fav");
				}
				querySql.append(" where p.allinstanceid = do.instanceid and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null) ");
				if(status.equals("5")){
					querySql.append(" and sh.employee_guid=:userId and sh.wf_instance_uid = p.wf_instance_uid and sh.old_instanceid is null ");
				}
				querySql.append(" and p.is_show = 1 ")
				.append(" and (p.is_back is null or p.is_back != '2') ");
		if(status.equals("0") || status.equals("3")){ 
			querySql.append(" and (p.action_status is null or p.action_status != 2) ");
		}
		querySql.append(" and i.id = p.wf_item_uid ")
				.append(" and p.wf_node_uid=n.wfn_id and (p.isrepeated != 1 or p.isrepeated is null) ")
				.append(" and e.employee_guid = p.user_uid ")
				.append(" and e.department_guid = d.department_guid ");
		querySql.append(conditionSql);
		if(status.equals("0") || status.equals("3")){ 
			querySql.append(" and p.is_over='NOT_OVER' ")
			.append(" and p.isexchanging = 0 ")
			.append(" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') "); // 待办
		}else if(status.equals("1") ||status.equals("2") || status.equals("4") || status.equals("5") ){
			querySql.append(" and p.is_over= 'OVER' ") // 已办
		   .append(" and p.finsh_time in ")
		   .append(" (select max(p2.finsh_time) ")
		   .append(" from t_wf_process p2 ")
		   .append(" where p2.process_title is not null ")
		   .append(" and (p2.is_back != '2' or p2.is_back is null) ")
		   .append(" and p2.user_uid = :userId ")
		   .append(" and p2.is_over = 'OVER' ")
		   .append(" and p2.is_show = 1 ")
		   .append(" and p2.finsh_time is not null ")
		   .append(" group by p2.wf_instance_uid) ");
			if(status.equals("2")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1"); // 已办结
			}else if(status.equals("4")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
			}
		}else if(status.equals("7")){
			querySql.append(" and do.id = fav.dofileid and fav.userid=:userId"); 
			querySql.append(" and p.step_index =(select max(t2.step_index) from t_wf_process t2 " +
			    		"where t2.user_uid = :userId and (t2.is_back is null or t2.is_back != '2') and t2.wf_instance_uid = do.instanceid) ");
		}
		/*else if(status.equals("7")){
			//TODO 未完成，要再看一下
			
			querySql.append(" and do.id = fav.dofileid and fav.userid=:userId").append(" and p.is_over= 'OVER' "); // 已办;
		}*/
		if(status.equals("5")){
			querySql.append(" order by sh.is_read,p.apply_time desc ");
		}else if(status.equals("1")){
			querySql.append(" order by p.finsh_time desc ");
		}else if(status.equals("0")){
			querySql.append(" order by urgency desc nulls last,i.itemsort nulls last,p.apply_time desc ");
		}else{
			querySql.append(" order by p.apply_time desc ");
		}
		Query query=super.getEm().createNativeQuery(querySql.toString());
		if(selects != null &&selects.size()>0){
			for(int i = 0 ,size =selects.size() ; i < size; i++){
				FieldSelectCondition fs = selects.get(i);
				if(fs.getType() == 1){
					query.setParameter(fs.getName(), "%"+fs.getValue()+"%");
				}else{
					query.setParameter(fs.getName(), fs.getValue());
				}
			}
		}
		query.setFirstResult(count);
		query.setMaxResults(pagesize);
		return query.getResultList();
	}
	
	@Override
	public int searchCountByStatus(String conditionSql, String status,
			List<FieldSelectCondition> selects) {
		StringBuffer querySql = new StringBuffer("select count(*) ")
		.append(" from t_wf_core_dofile do, t_wf_process p ,t_wf_core_item i, zwkj_employee e, zwkj_department d,wf_node n");
		if(status.equals("5")){
			querySql.append(" ,t_wf_core_follow_ship sh ");
		}
		if(status.equals("7")){
			querySql.append(",t_wf_core_dofile_favourite fav");
		}
		querySql.append(" where p.allinstanceid = do.instanceid and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null) ");
		if(status.equals("5")){
			querySql.append("and sh.employee_guid=:userId and sh.wf_instance_uid = p.wf_instance_uid and sh.old_instanceid is null ");
		}
		querySql.append(" and p.is_show = 1 ").append(" and p.wf_node_uid=n.wfn_id and (p.isrepeated != 1 or p.isrepeated is null) ")
		.append(" and (p.is_back is null or p.is_back != '2') ");
		if(status.equals("0") || status.equals("3")){ 
			querySql.append(" and (p.action_status is null or p.action_status != 2) ");
		}
		querySql.append(" and i.id = p.wf_item_uid ")
		.append(" and p.wf_node_uid=n.wfn_id ")
		.append(" and e.employee_guid = p.user_uid ")
		.append(" and e.department_guid = d.department_guid ");
		querySql.append(conditionSql);
		if(status.equals("0") || status.equals("3")){ 
			querySql.append(" and p.is_over='NOT_OVER' ")
			.append(" and p.isexchanging = 0 ")
			.append(" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') "); // 待办
		}else if(status.equals("1") ||status.equals("2") ||status.equals("4") || status.equals("5")){
			querySql.append(" and p.is_over= 'OVER' ") // 已办
		   .append(" and p.finsh_time in ")
		   .append(" (select max(p2.finsh_time) ")
		   .append(" from t_wf_process p2 ")
		   .append(" where p2.process_title is not null ")
		   .append(" and (p2.is_back != '2' or p2.is_back is null) ")
		   .append(" and p2.user_uid = :userId ")
		   .append(" and p2.is_over = 'OVER' ")
		   .append(" and p2.is_show = 1 ")
		   .append(" and p2.finsh_time is not null ")
		   .append(" group by p2.wf_instance_uid) ");
			if(status.equals("2")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1"); // 已办结
			}else if(status.equals("4")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
			}
		}else if(status.equals("7")){
		    querySql.append(" and do.id = fav.dofileid and fav.userid=:userId"); // 已办;
		    querySql.append(" and p.step_index =(select max(t2.step_index) from t_wf_process t2 " +
		    		"where t2.user_uid = :userId and (t2.is_back is null or t2.is_back != '2') and t2.wf_instance_uid = do.instanceid)");
		}
		/*else if(status.equals("7")){
			//TODO 未完成，要再看一下
			
			querySql.append(" and do.id = fav.dofileid and fav.userid=:userId").append(" and p.is_over= 'OVER' "); // 已办;
		}*/
		querySql.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString());
		if(selects != null &&selects.size()>0){
			for(int i = 0 ,size =selects.size() ; i < size; i++){
				FieldSelectCondition fs = selects.get(i);
				if(fs.getType() == 1){
					query.setParameter(fs.getName(), "%"+fs.getValue()+"%");
				}else{
					query.setParameter(fs.getName(), fs.getValue());
				}
			}
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}
	@Override
	public int getReceiveListByStatusCount(String userId, String status,
			Map<String, String> map) {
		String wfTitle = map.get("wfTitle");
		String itemName = map.get("itemName");
		String todepartId = map.get("departId");
		String sql = "";
		// 一个部门
		if(todepartId.indexOf("','") == -1){
			if("1".equals(status)){
				sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")) and t.status != 0 " +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and p.step_index != 1 ";
				
			}else{
				sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid  ";
				
			}
		}else{
			// 多个部门
			if("1".equals(status)){
				sql = "select count(*) from (select distinct t.applydate,t.pinstanceid, t.isreback,t.recdate from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")) and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type = 2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid ";
				
			}else{
				sql = "select count(*) from (select distinct t.applydate,t.pinstanceid , t.isreback,t.recdate from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid  ";
				
			}
		}
		
		if(wfTitle!=null && !wfTitle.equals("")){
			sql +=" and p.process_title like '%"+wfTitle+"%'";
		}
		if(itemName!=null && !itemName.equals("")){
			sql +=" and i.VC_SXMC like '%"+itemName+"%'";
		}
		if(todepartId.indexOf("','") > -1){
			sql += ")";
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	@Override
	public List getReceiveListByStatus(String userId,
			Integer pageIndex, Integer pageSize,String status, Map<String,String> map) {
		String todepartId = map.get("departId");
		//1,表示已收
		String sql = "";
		List<DoFileReceive> returnList = new ArrayList<DoFileReceive>();
		//去除重复的（子部门）
		List<DoFileReceive> newDofileList = new ArrayList<DoFileReceive>();
		if(todepartId.indexOf("','") == -1){
			if("1".equals(status)){
				sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
						" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") )" +
						" and t.status != 0 and  p.is_back != '2' " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) and i.id = p.wf_item_uid and t.instanceid=s.instanceid ";
			}else{
				sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME, " +
						"to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
						" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+"))" +
						" and t.status ="+status +" and  p.is_back != '2' " +
						" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2) and i.id = p.wf_item_uid and t.instanceid=s.instanceid ";
			}
			
			if(status!=null && "1".equals(status)){
				sql +=" order by t.isreback desc, t.recdate desc";
			}else{
				sql +=" order by t.applydate desc";
			}
				
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List list =  query.getResultList();
			DoFileReceive doFileReceive = null;
			for(int i=0; list!=null && i<list.size(); i++){
				Object[] data =(Object[]) list.get(i);
				doFileReceive = new DoFileReceive();
				String employeeName = data[0].toString();
				doFileReceive.setEmployeeName(employeeName);
				doFileReceive.setApplyDate(convertStringToDate(data[1].toString()));
				doFileReceive.setTitle(data[2]==null?"":data[2].toString());
				doFileReceive.setItemName(data[3]==null?"":data[3].toString());
				doFileReceive.setProcessId(data[4]==null?"":data[4].toString());
				doFileReceive.setId(data[5].toString());
				doFileReceive.setItemId(data[6]==null?"":data[6].toString());
				doFileReceive.setFormId(data[7]==null?"":data[7].toString());
				doFileReceive.setReceiveType(data[8]==null?"":data[8].toString());
				doFileReceive.setLwbt(data[9]==null?"":data[9].toString().replace("'", "‘").replace("\"", "”").replace("\r\n", ""));
				doFileReceive.setLwdw(data[10]==null?"":data[10].toString());
				doFileReceive.setYfdw(data[11]==null?"":data[11].toString());
				doFileReceive.setLwh(data[12]==null?"":data[12].toString());
				doFileReceive.setFwsj(convertStringToDate(data[13].toString()));
				doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
				doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
				doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
				doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
				doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
				doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
				doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
				doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
				returnList.add(doFileReceive);
			}
			newDofileList = returnList;
		}else{
			// 多个部门
			if("1".equals(status)){
				sql = "select distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'),t.pinstanceid , t.isreback,  t.recdate,t.id from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid ";
				
			}else{
				sql = "select distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'),t.pinstanceid, t.applydate ,t.todepartid,t.id from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid  ";
				
			}
			if(status!=null && "1".equals(status)){
				sql +=" order by t.isreback desc, t.recdate desc";
			}else{
				sql +=" order by t.applydate desc";
			}
			
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List list =  query.getResultList();
			// 循环list 查询数据
			for(int i=0; list!=null && i<list.size(); i++){
				if(list!=null  && list.size() > 0){
					Object[] data =(Object[]) list.get(i);
					if("1".equals(status)){
						sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
								" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
								" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
								" where t.id = '"+data[4].toString()+"' and  t.pinstanceid = '"+data[1].toString()+"' and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"' and t.status != 0 and ( p.is_back != '2' or p.is_back is null)" +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID  ) and i.id = p.wf_item_uid and t.instanceid=s.instanceid ";
						
					}else{
						sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
								" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
								" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
								" where t.id = '"+data[4].toString()+"' and t.pinstanceid = '"+data[1].toString()+"' and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"' and t.status ="+status +" and (p.is_back != '2' or p.is_back is null) and  t.todepartid = '" + data[3].toString() +"'"+
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2  ) and i.id = p.wf_item_uid and t.instanceid=s.instanceid    ";
					}
					if(status!=null && "1".equals(status)){
						sql +=" order by t.isreback desc, t.recdate desc";
					}else{
						sql +=" order by t.applydate desc";
					}
					query = getEm().createNativeQuery(sql);
					List list2 =  query.getResultList();
					DoFileReceive doFileReceive = null;
					if(list2 != null&& list2.size()>0){
						data =(Object[]) list2.get(0);
						doFileReceive = new DoFileReceive();
						String employeeName = data[0].toString();
						doFileReceive.setEmployeeName(employeeName);
						doFileReceive.setApplyDate(convertStringToDate(data[1].toString()));
						doFileReceive.setTitle(data[2]==null?"":data[2].toString());
						doFileReceive.setItemName(data[3]==null?"":data[3].toString());
						doFileReceive.setProcessId(data[4]==null?"":data[4].toString());
						doFileReceive.setId(data[5].toString());
						doFileReceive.setItemId(data[6]==null?"":data[6].toString());
						doFileReceive.setFormId(data[7]==null?"":data[7].toString());
						doFileReceive.setReceiveType(data[8]==null?"":data[8].toString());
						doFileReceive.setLwbt(data[9]==null?"":data[9].toString().replace("'", "‘").replace("\"", "”"));
						doFileReceive.setLwdw(data[10]==null?"":data[10].toString());
						doFileReceive.setYfdw(data[11]==null?"":data[11].toString());
						doFileReceive.setLwh(data[12]==null?"":data[12].toString());
						doFileReceive.setFwsj(convertStringToDate(data[13].toString()));
						doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
						doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
						doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
						doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
						doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
						doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
						doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
						doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
						returnList.add(doFileReceive);
					}
				}
			}
			//去除重复的（子部门）
			String instanceId = "123";//临时赋值做比较
			if(returnList != null && returnList.size()>0){
				for (DoFileReceive dofile : returnList) {
					if(!instanceId.equals(dofile.getpInstanceId())){
						newDofileList.add(dofile);
						//替换原来的值
						instanceId = dofile.getpInstanceId();
					}
				}
			}
		}
		return newDofileList;
	}
	
	public Date convertStringToDate(String time){
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
	public List<Object[]> getOutOfDateList(String conditionSql, Integer pageIndex, Integer pageSize) {
		StringBuffer querySql = (new StringBuffer());
		querySql.append(" select distinct tm.* from (select p.wf_instance_uid as wf_instance_uid, p.wf_item_uid as item_id, p.jdqxdate ")
				.append(" from t_wf_core_dofile d, T_WF_CORE_ITEM i ," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid ,wf_node n ")
				.append(" where p.wf_instance_uid = d.instanceid ").append(" and p.isexchanging=0 " +
						" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(conditionSql)
				.append(") tm order by tm.jdqxdate desc");
		Query query=super.getEm().createNativeQuery(querySql.toString());
		
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public int getCountOfOutOfDate(String conditionSql, String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
		StringBuffer query = new StringBuffer()
		.append("select count(distinct p.wf_instance_uid) from t_wf_process p ," +
				"T_WF_CORE_ITEM i ,zwkj_department d,wf_node n  ")
		.append(" where p.isexchanging=0" +
				" and i.id=p.wf_item_uid" +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')" +
				" and (p.action_status is null or p.action_status != 2)")
		.append(condition).append(conditionSql)
		.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
		.append(" and p.userdeptid = d.department_guid ");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}

	@Override
	public List<Object[]> getOutOfDateListByInstanceId(String conditionSql,
			String instanceId) {		
		StringBuffer querySql = (new StringBuffer("select "))
			.append("p.wf_process_uid as wf_process_uid,")
			.append("replace(p.process_title, chr(10), '') as process_title,")
			.append("p.wf_node_uid as wf_node_uid,")
			.append("p.user_uid as user_uid,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.user_uid) as employee_name,")
			.append("i.vc_sxmc as item_name,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as fromUserName,")
			.append("d.department_name as userDepName,")
			.append("to_char(p.apply_time,'YYYY-MM-DD HH24:MI') as commitTime,")
			.append("( case when p.JDQXDATE <= sysdate then '3' when p.jdqxdate > sysdate and sysdate > p.jdqxdate-3 then '4' else p.status end) as status,")
			.append("(select ship.wf_instance_uid from t_wf_core_follow_ship ship where ship.old_instanceid=p.wf_instance_uid and ship.employee_guid is null ) as shipinstanceId")
//			.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
//			.append("p.wf_uid as wf_workflow_uid,")
//			.append("p.wf_item_uid as item_id,")
//			.append("p.wf_form_id as form_id,")
//			.append("p.wf_oldform_id as oldForm_id,")
//			.append("i.RELATEDITEMID as RELATEDITEMID,")
//			.append("p.CONTINUEINSTANCEID as continueInstanceId,")
//			.append("i.vc_sxlx as item_type,")
//			.append("p.apply_time as apply_time,")
//			.append("p.step_index as stepIndex,")
//			.append("p.finsh_time as finish_time,")
//			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
//			.append("p.is_master as isMaster,")
//			.append("p.pdfPath as pdfPath,")
//			.append("p.commentJson as commentJson,")
//			.append("p.jdqxDate as jdqxDate,")
//			.append("p.zhqxDate as zhqxDate,")
//			.append("p.is_back as is_back,")
//			.append("p.isChildWf as isChildWf, ")
//			.append("p.allinstanceid as allInstanceid, ")
//			.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
//			.append(" ,do.id as dofileId ")
//			.append(" ,'' as urgency")
			.append(" from T_WF_CORE_ITEM i ," +
					" t_wf_process p join zwkj_department d " +
					" on p.userdeptid = d.department_guid ,wf_node n")
			.append(" where p.wf_instance_uid = '").append(instanceId).append("' and p.isexchanging=0 " +
					" and i.id = p.wf_item_uid " +
					" and p.wf_node_uid=n.wfn_id " +
					" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
			.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
		    .append(conditionSql)
			.append(" order by p.jdqxdate desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString());
		return query.getResultList();
	}
}
