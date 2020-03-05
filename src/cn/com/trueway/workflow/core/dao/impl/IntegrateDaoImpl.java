package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.axiom.om.util.CommonUtils;
import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.IntegrateDao;
import cn.com.trueway.workflow.core.pojo.ReadedEndPending;
import cn.com.trueway.workflow.core.pojo.TodoMessage;

public class IntegrateDaoImpl extends BaseDao implements IntegrateDao{

	@Override
	public int findTodoMessageCount(String conditionSql, String userId) {
		String containsEnds = SystemParamConfigUtil.getParamValueByParam("containsEnds");
		//1、工作流的待办总数
		StringBuffer workflow = new StringBuffer(512);
		workflow.append(" select count(*) from t_wf_process p, t_wf_core_item i," +
				" zwkj_department d  ,wf_node n ")
				.append(" where p.user_uid = '"+userId+"' and p.isexchanging = 0")
				.append(" and i.id = p.wf_item_uid" +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')")
				.append(" and (p.action_status is null or p.action_status != 2)")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') ")
				.append(conditionSql)
				.append(" and (p.is_duplicate!='true' or p.is_duplicate is null) and p.userdeptid = d.department_guid");
		Integer workflowCount = Integer.parseInt(getEm().createNativeQuery(workflow.toString()).getSingleResult().toString());

		//内部邮件暂不支持
//		//2、内部邮件个条数(未读)
//		StringBuffer  mail = new StringBuffer(258);
//		mail.append(" select count(*) from "+database+".t_sys_mail t")
//			.append(" where t.rec_userid = '"+userId+"'")
//			.append(" and t.maincc_flag != '3'")
//			.append(" and nvl(t.back_flag, '0') != '1' and nvl(t.delete_flag, '0') != '1' and nvl(t.sign_flag, '0')!= '1' ");
//		Integer mailCount = Integer.parseInt(getEm().createNativeQuery(mail.toString()).getSingleResult().toString());
		
		StringBuffer endSql = new StringBuffer();
		endSql.append("  select count(*) from t_wf_process p join zwkj_department d on p.userdeptid = d.department_guid, t_wf_core_item i, wf_node t")
		  .append(" where p.user_uid = '"+userId+"'  and i.id = p.wf_item_uid and t.wfn_id = p.wf_node_uid and p.is_over = 'OVER' and p.is_show = 1")
		  .append(" and to_char(apply_time, 'yyyy-MM-dd')>='2016-08-01'" +
		  		"  and t.wfn_isendremind = '1' ")
		  .append("  and p.finsh_time in(select max(p2.finsh_time) from t_wf_process p2,wf_node t2 where p2.process_title is not null and (p2.is_back != '2' or p2.is_back is null)" +
		  		" and t2.wfn_id = p2.wf_node_uid  and t2.wfn_isendremind='1' ")
		  .append(" and p2.user_uid = '"+userId+"' and p2.is_over = 'OVER' and p2.is_show = 1 and p2.finsh_time is not null group by p2.wf_instance_uid)")
		  .append(" and (p.is_duplicate != 'true' or p.is_duplicate is null)")
			.append(conditionSql);
		endSql.append(" and decode((select count(1)from t_wf_core_end_instanceid t  where p.wf_instance_uid = t.instanceId), 0, 0,  1) = 1")
		  .append(" and not exists (select t2.status from T_WF_CORE_READEDENDPENDING t2 where t2.instanceid = p.wf_instance_uid and t2.userid = p.user_uid)");
		int enCount = 0;
		if(containsEnds!=null && containsEnds.equals("1")){
			enCount = Integer.parseInt(getEm().createNativeQuery(endSql.toString()).getSingleResult().toString());
		}
//		return workflowCount+mailCount+enCount;
		return workflowCount+enCount;
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TodoMessage> findTodoMessage(String conditionSql,String userId,Integer pageIndex,Integer pagesize,String itemIds) {
//		String database = SystemParamConfigUtil.getParamValueByParam("database");
		String database = "";
		String containsEnds = SystemParamConfigUtil.getParamValueByParam("containsEnds");
		StringBuffer buffer = new StringBuffer(1024);
		String sqlCol = getMessageSqlCol();
		buffer.append(sqlCol)
			  .append(" from (");
			//查询全部事项
		if(itemIds==null){
			itemIds = "";
		}
		if(CommonUtil.stringIsNULL(itemIds)||(CommonUtil.stringNotNULL(itemIds) && itemIds.split(",").length>1 && itemIds.indexOf("id4mail")>-1)){
			if(containsEnds!=null && containsEnds.equals("1")){
				buffer.append("select * from  ( select * from ( "+getSqlConentent(conditionSql,userId, itemIds, "0", database) +" union all "+ getSqlConentent(conditionSql,userId, itemIds, "2", database)+" ) order by apply_time desc)");
			}else{
				buffer.append("select * from  ( "+getSqlConentent(conditionSql,userId, itemIds, "0", database) +" order by apply_time desc)");
			}
			//pc端暂时不开发邮件
//			buffer.append(" union all");
//			buffer.append(" select * from  ("+getSqlConentent(userId, itemIds, "1", database)+")");
		}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")>-1&&itemIds.split(",").length==1){
			buffer.append(" select * from  ("+getSqlConentent(conditionSql,userId, itemIds, "1", database)+")");
		}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")==-1){
			buffer.append("select * from  ( select * from ( "+getSqlConentent(conditionSql,userId, itemIds, "0", database) +" union all "+ getSqlConentent(conditionSql,userId, itemIds, "2", database)+" ) order by apply_time desc)");
		}
		buffer.append(" ) k ");
//		if(!CommonUtils.isEmpty(title)){
//			buffer.append(" where  k.process_title like '%"+title.trim()+"%'");
//		}
		Query query = super.getEm().createNativeQuery(buffer.toString(),"TodoMessageResults");
		int i = pageIndex;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pagesize);// 每页显示的最大记录数
		return query.getResultList();
	}
	
	
	@Override
	public int findHavedoMessageCount(String conditionSql, String userId) {
		return 0;
	}
	
	/**
	 * 获取需要查询的字段
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-3-10 上午10:10:30
	 */
	public String getMessageSqlCol(){
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append(" select " +
				"k.process_title, " +
				"k.apply_time, " +
				"k.wf_process_uid, " +
				"k.wf_instance_uid, " +
				"k.user_uid," +
				"k.wf_form_id, " +
				"k.wf_node_uid,")
			  .append(" k.wf_uid, " +
			  		   "k.wf_item_uid, " +
			  		   "k.vc_sxmc, " +
			  		   "k.owner, " +
			  		   "k.vc_sxlx, " +
			  		   "k.commentjson, " +
			  		   "k.message_type, " +
			  		   "k.message_id, " +
			  		   "k.pdfPath, " +
			  		   "k.is_master, " +
			  		   "k.IsEnd, " +
			  		   "k.isfavourite, " +
			  		   "k.stayuserId, " +
			  		   "k.userDeptId, " +
			  		   "k.employee_name, " +
			  		   "k.jdqxDate, " +
			  		   "k.item_name, " +
			  		   "k.status, " +
			  		   "k.item_id, " +
			  		   "k.form_id, " +
			  		   "k.isChildWf, " +
			  		   "k.stepIndex, " +
			  		   "k.item_type, " +
			  		   "k.dofileId,"+
			  		   "k.sendtime");
		return buffer.toString();
	}
	
	/**
	 * 
	 * 描述：
	 * @param userId
	 * @param itemIds
	 * @param type	type=2，已办结的信息
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-8-1 下午2:59:09
	 */
	public String getSqlConentent(String conditionSql,String userId, String itemIds, String type, String database){
		StringBuffer buffer = new StringBuffer(1024);
		String sqlCol_same = " p.process_title," +
							 "p.wf_process_uid," +
							 "p.wf_instance_uid, " +
							 "p.user_uid, " +
							 "p.wf_form_id, " +
							 "p.wf_node_uid, " +
							 "p.wf_uid," +
							 "p.wf_item_uid," +
							 "i.vc_sxmc, " +
							 "(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,"+
							 "i.vc_sxlx," +
							 "p.commentjson," +
							 "'' as message_id, " +
							 "p.pdfpath, " +
							 "decode(t.wfn_route_type,'3','0', '4','0', '5','0', p.is_master) as is_master,"+
							 " '' as IsEnd, " +
							 "'' as isfavourite, " +
							 "'' as stayuserId," +
							 "d.department_name as userDeptId," +
							 "(select e.employee_name " +
							 " from zwkj_employee e " +
							 " where e.employee_guid = p.fromuserid) as employee_name," +
							 "p.jdqxDate as jdqxDate," +
							 "i.vc_sxmc as item_name," +
							 "p.wf_item_uid as item_id," +
							 "p.wf_form_id as form_id,"+
							 "p.isChildWf as isChildWf,"+
							 "p.step_index as stepIndex,"+
							 "i.vc_sxlx as item_type,"+
							 "do.id as dofileId,"+
							 "'' as sendtime";
		if(type!=null && type.equals("2")){
			buffer.append("  select " +
						  " (select  t3.finsh_time from t_wf_process t3 where t3.is_end =1 and t3.wf_instance_uid = p.wf_instance_uid and rownum=1) as  apply_time," +
						  " decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, '0', '1') as status," +
						  "'2' as message_type,  " +
						  sqlCol_same )
				  .append("  from t_wf_process p join zwkj_department d on p.userdeptid = d.department_guid, t_wf_core_item i,t_wf_core_dofile do, wf_node t")
				  .append(" where p.user_uid = '"+userId+"'  " +
						  " and p.wf_instance_uid = do.instanceid"+
						  conditionSql+
				  		" and i.id = p.wf_item_uid and t.wfn_id = p.wf_node_uid and p.is_over = 'OVER' and p.is_show = 1")
				  .append(" and to_char(apply_time, 'yyyy-MM-dd')>='2016-08-01'" +
				  		" and t.wfn_isendremind='1' ")
				  .append("  and p.finsh_time in(select max(p2.finsh_time) from t_wf_process p2,wf_node t2 where p2.process_title is not null and (p2.is_back != '2' or p2.is_back is null)" +
				  		" and t2.wfn_id = p2.wf_node_uid  and t2.wfn_isendremind='1' ")
				  .append(" and p2.user_uid = '"+userId+"' and p2.is_over = 'OVER' and p2.is_show = 1 and p2.finsh_time is not null group by p2.wf_instance_uid)")
				  .append(" and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null)");
				  if(CommonUtil.stringNotNULL(itemIds)){
						buffer.append("  and i.id in ("+ itemIds +")");
				}
			buffer.append(" and decode((select count(1)from t_wf_core_end_instanceid t  where p.wf_instance_uid = t.instanceId), 0, 0,  1) = 1")
				  .append(" and not exists (select t2.status from T_WF_CORE_READEDENDPENDING t2 where t2.instanceid = p.wf_instance_uid and t2.userid = p.user_uid)");
				  //.append("  order by p.finsh_time desc");
		}
		else if(type!=null && type.equals("1")){	//关于邮件的内容
//			buffer.append("select t.message_title as process_title,t.message_time as apply_time,t.message_id as wf_process_uid,'' as wf_instance_uid,'' as user_uid,'' as wf_form_id,'' as wf_node_uid,'' as wf_uid,'' as wf_item_uid,")
//			.append(" '内部邮件' as vc_sxmc,(select e.employee_name from zwkj_employee e where e.employee_guid = t.user_id) as owner,'' as vc_sxlx,t.message_content as commentjson,'1' as message_type, t.message_id,'' as pdfpath,'' as is_master,'' as IsEnd,'' as isfavourite,'' as stayuserId, to_char(t.message_time, 'yyyy-MM-dd hh24:mi:ss') as sendtime ")
//			.append(" from "+database+".t_sys_mail t")
//			.append(" where t.rec_userid = '"+userId+"'")
//			.append(" and t.maincc_flag != '3'")
//			.append(" and nvl(t.back_flag, '0') != '1'")
//			.append(" and nvl(t.delete_flag, '0') != '1' and nvl(t.sign_flag, '0')!= '1' order by t.message_time desc");
		}
		else if(type!=null && type.equals("0")){	//待办信息
			buffer.append("select "+
								 "p.apply_time ," +
								 "(case when p.JDQXDATE <= sysdate " +
								 "	then '3' " +
								 "	else p.status end) as status," +
								 "'0' as message_type,  " +
								 sqlCol_same)
			.append(" from t_wf_core_item i," +
					" t_wf_process p  join zwkj_department d " +
					" on p.userdeptid = d.department_guid," +
					"t_wf_core_dofile do, wf_node t")
			.append(" where p.user_uid = '"+userId+"' ")
			.append(" and p.wf_instance_uid = do.instanceid")
			.append(" and p.isexchanging = 0")
			.append(" and i.id = p.wf_item_uid")
			.append(" and t.wfn_id = p.wf_node_uid")
			.append(" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')")
			.append(" and p.is_over = 'NOT_OVER'" +
					 conditionSql)
			.append(" and p.is_show = 1 and (p.is_back is null or p.is_back != '2')");
			if(CommonUtil.stringNotNULL(itemIds)){
				buffer.append("  and i.id in ("+ itemIds +")");
			}
			buffer.append("  and (p.is_duplicate != 'true' or p.is_duplicate is null) and (p.action_status is null or p.action_status != 2)");
		}
		return buffer.toString();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TodoMessage> findTodoMessageMobile(String userId, int count,
			Integer column, Integer pagesize, String type, String itemIds,
			String title) {
//		String database = SystemParamConfigUtil.getParamValueByParam("database");
		String database = "";
		String containsEnds = SystemParamConfigUtil.getParamValueByParam("containsEnds");
		StringBuffer buffer = new StringBuffer(1024);
		String sqlCol = getMessageSqlCol();
		buffer.append(sqlCol)
			  .append(" from (");
			//查询全部事项
		if(itemIds==null){
			itemIds = "";
		}
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(title)){
			conditionSql = " and p.process_title like '%" + title.trim()+ "%' escape '\\'";
		}
		if(CommonUtil.stringIsNULL(itemIds)||(CommonUtil.stringNotNULL(itemIds) && itemIds.split(",").length>1 && itemIds.indexOf("id4mail")>-1)){
			if(containsEnds!=null && containsEnds.equals("1")){
				buffer.append("select * from  ( select * from ( "+getSqlConentent("",userId, itemIds, "0", database) +" union all "+ getSqlConentent(conditionSql,userId, itemIds, "2", database)+" ) order by apply_time desc)");
			}else{
				buffer.append("select * from  ( "+getSqlConentent(conditionSql,userId, itemIds, "0", database) +" order by apply_time desc)");
			}
			//邮箱暂时不支持
//			buffer.append(" union all");
//			buffer.append(" select * from  ("+getSqlConentent(userId, itemIds, "1", database)+")");
		}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")>-1&&itemIds.split(",").length==1){
			buffer.append(" select * from  ("+getSqlConentent(conditionSql,userId, itemIds, "1", database)+")");
		}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")==-1){
			buffer.append("select * from  ( select * from ( "+getSqlConentent(conditionSql,userId, itemIds, "0", database) +" union all "+ getSqlConentent(conditionSql,userId, itemIds, "2", database)+" ) order by apply_time desc)");
		}
		buffer.append(" ) k ");
		if(!StringUtils.isBlank(title)){
			buffer.append(" where  k.process_title like '%"+title.trim()+"%'");
		}
		Query query = super.getEm().createNativeQuery(buffer.toString(),"TodoMessageResults");
		int i = (column - 1) * pagesize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pagesize);// 每页显示的最大记录数
		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TodoMessage> findHavedoMessageOfMobile(String userId,
			Integer column, Integer pagesize, String conditionSql, String itemIds,
			String title) {
		String database = SystemParamConfigUtil.getParamValueByParam("database");
		if(StringUtils.isNotBlank(database)){
			StringBuffer buffer = new StringBuffer(1024);
			buffer.append(" select k.process_title, k.apply_time, k.wf_process_uid, k.wf_instance_uid, k.user_uid,k.wf_form_id, k.wf_node_uid,")
				  .append(" k.wf_uid, k.wf_item_uid, k.vc_sxmc, k.owner, k.vc_sxlx, k.commentjson, k.message_type, k.message_id, k.pdfPath, k.is_master, k.IsEnd, k.isfavourite, k.stayuserId, k.sendtime")
				  .append(" from (");
			if(CommonUtil.stringIsNULL(itemIds)||(CommonUtil.stringNotNULL(itemIds)&&itemIds.split(",").length>1&&itemIds.indexOf("id4mail")>-1)){
				//查询全部事项 "  "
				buffer.append("select p.process_title,p.apply_time ,p.wf_process_uid, p.wf_instance_uid , p.user_uid, p.wf_form_id ,")
					.append(" p.wf_node_uid ,  p.wf_uid,  p.wf_item_uid , i.vc_sxmc ,(select emp.employee_name from  zwkj_employee emp where emp.employee_guid = p.owner) as owner  ,i.vc_sxlx ,p.commentjson , '0' as message_type, '' as message_id," +
					" p.pdfpath,  decode(t.wfn_route_type, '3', '0', '4','0','5','0',p.is_master) as is_master, to_char(p.is_end) as IsEnd, " +
					" to_char((select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = d.instanceid  or p.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid='"+userId+"')) as isfavourite," +
					" (select wm_concat( distinct(emp.employee_name)) from t_wf_process t2, zwkj_employee emp  where emp.employee_guid = t2.user_uid and t2.is_over = 'NOT_OVER' and t2.wf_instance_uid = p.wf_instance_uid) as stayuserId, '' as  sendtime ")
					.append(" from t_wf_core_item i, t_wf_process p  join zwkj_department d  on p.userdeptid = d.department_guid, wf_node t")
					.append(" where p.user_uid = '"+userId+"'")
					.append(" and i.id = p.wf_item_uid and t.wfn_id = p.wf_node_uid and p.is_over = 'OVER' and p.is_show = 1 ")
					.append(" and p.finsh_time in (select max(p2.finsh_time)  from t_wf_process p2  where p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and (p2.is_back != '2' or p2.is_back is null)and p2.user_uid = '"+userId+"' and p2.is_over = 'OVER' and p2.is_show = 1 and p2.finsh_time is not null group by p2.wf_instance_uid)");
				if(CommonUtil.stringNotNULL(itemIds)){
					buffer.append("  and i.id in ("+ itemIds +")");
				}
				buffer.append(" union all")
					.append(" select t.message_title as process_title,t.message_time as apply_time,t.message_id as wf_process_uid,'' as wf_instance_uid,'' as user_uid,'' as wf_form_id,'' as wf_node_uid,'' as wf_uid,'' as wf_item_uid,")
					.append(" '内部邮件' as vc_sxmc,(select e.employee_name from zwkj_employee e where e.employee_guid = t.user_id) as owner,'' as vc_sxlx,t.message_content as commentjson,'1' as message_type, t.message_id,'' as pdfpath,'' as is_master,'' as IsEnd,'' as isfavourite,'' as stayuserId, to_char(t.message_time, 'yyyy-MM-dd hh24:mi:ss') as sendtime ")
					.append(" from "+database+".t_sys_mail t")
					.append(" where t.rec_userid = '"+userId+"'")
					.append(" and t.maincc_flag != '3'")
					.append(" and nvl(t.back_flag, '0') != '1'")			
					.append(" and nvl(t.delete_flag, '0') != '1' and nvl(t.sign_flag, '0')= '1' ");  //收件箱已读
					buffer.append(" union all")
					.append(" select f.message_title,f.message_time as apply_time, f.message_id as wf_process_uid ,'' ,'' ,'' ,'','' ,'' ,'内部邮件' , (select e.employee_name from zwkj_employee e where e.employee_guid = f.user_id) as owner,'' ,f.message_content,'1' ,f.message_id,'','','' as IsEnd ,'' as isfavourite, '' as stayuserId, '' as  sendtime ")
					.append(" from "+database+".t_sys_mail f")
					.append(" where  f.user_id = '"+userId+"' and f.maincc_flag = '3' and nvl(f.delete_Flag,'0') != '1' and nvl(f.draft_flag,'0') !='1'");		//发件箱
			}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")>-1&&itemIds.split(",").length==1){
				//只查询邮件
				buffer.append(" select t.message_title as process_title,t.message_time as apply_time,t.message_id as wf_process_uid,'' as wf_instance_uid,'' as user_uid,'' as wf_form_id,'' as wf_node_uid,'' as wf_uid,'' as wf_item_uid,")
				.append(" '内部邮件' as vc_sxmc,(select e.employee_name from zwkj_employee e where e.employee_guid = t.user_id) as owner,'' as vc_sxlx,t.message_content as commentjson,'1' as message_type, t.message_id,'' as pdfpath,'' as is_master,'' as IsEnd,'' as isfavourite,'' as stayuserId, '' as  sendtime")
				.append(" from "+database+".t_sys_mail t")
				.append(" where t.rec_userid = '"+userId+"'")
				.append(" and t.maincc_flag != '3'")
				.append(" and nvl(t.back_flag, '0') != '1'")			
				.append(" and nvl(t.delete_flag, '0') != '1' and nvl(t.sign_flag, '0')= '1' ");  //收件箱已读
				buffer.append(" union all")
				.append(" select f.message_title,f.message_time as apply_time, f.message_id as wf_process_uid ,'' ,'' ,'' ,'','' ,'' ,'内部邮件' , (select e.employee_name from zwkj_employee e where e.employee_guid = f.user_id) as owner,'' ,f.message_content,'1' ,f.message_id,'','','' as IsEnd ,'' as isfavourite, '' as stayuserId, '' as  sendtime ")
				.append(" from "+database+".t_sys_mail f")
				.append(" where  f.user_id = '"+userId+"' and f.maincc_flag = '3' and nvl(f.delete_Flag,'0') != '1' and nvl(f.draft_flag,'0') !='1'");		//发件箱
			}else if(CommonUtil.stringNotNULL(itemIds)&&itemIds.indexOf("id4mail")==-1){
				//根据上传的id查询事项
				buffer.append("select p.process_title,p.apply_time ,p.wf_process_uid, p.wf_instance_uid , p.user_uid, p.wf_form_id ,")
				.append(" p.wf_node_uid ,  p.wf_uid,  p.wf_item_uid , i.vc_sxmc , (select emp.employee_name from  zwkj_employee emp where emp.employee_guid = p.owner) as owner,i.vc_sxlx ,p.commentjson , '0' as message_type, '' as message_id," +
				" p.pdfpath,  decode(t.wfn_route_type, '3', '0', '4','0','5','0',p.is_master) as is_master, to_char(p.is_end) as IsEnd, " +
				" to_char((select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = d.instanceid  or p.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid='"+userId+"')) as isfavourite," +
				" (select wm_concat( distinct(emp.employee_name)) from t_wf_process t2, zwkj_employee emp  where emp.employee_guid = t2.user_uid and t2.is_over = 'NOT_OVER' and t2.wf_instance_uid = p.wf_instance_uid) as stayuserId, '' as  sendtime ")
				.append(" from t_wf_core_item i, t_wf_process p  join zwkj_department d  on p.userdeptid = d.department_guid, wf_node t")
				.append(" where p.user_uid = '"+userId+"'")
				.append(" and i.id = p.wf_item_uid and t.wfn_id = p.wf_node_uid and p.is_over = 'OVER' and p.is_show = 1 ")
				.append(" and p.finsh_time in (select max(p2.finsh_time)  from t_wf_process p2  where p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and (p2.is_back != '2' or p2.is_back is null)and p2.user_uid = '"+userId+"' and p2.is_over = 'OVER' and p2.is_show = 1 and p2.finsh_time is not null group by p2.wf_instance_uid)");
				if(!StringUtils.isBlank(itemIds)){
					buffer.append("  and i.id in ("+ itemIds +")");
				}
			}	  
			buffer.append(" ) k ");
			if(!StringUtils.isBlank(title)){
				buffer.append(" where  k.process_title like '%"+title.trim()+"%'");
			}
			buffer.append(" order by k.apply_time desc");
			Query query = super.getEm().createNativeQuery(buffer.toString(),"TodoMessageResults");
			int i = (column - 1) * pagesize;
			query.setFirstResult(i);// 从哪条记录开始
			query.setMaxResults(pagesize);// 每页显示的最大记录数
			return query.getResultList();
		}
		return null;
	}


	@Override
	public int findMailCount(String conditionSql, String userId) {
		String database = SystemParamConfigUtil.getParamValueByParam("database");
		if(StringUtils.isNotBlank(database)){
			StringBuffer  mail = new StringBuffer(258);
			mail.append(" select count(*) from "+database+".t_sys_mail t")
				.append(" where t.rec_userid = '"+userId+"'")
				.append(" and t.maincc_flag != '3'")
				.append(" and nvl(t.back_flag, '0') != '1' and nvl(t.delete_flag, '0') != '1' and nvl(t.sign_flag, '0')!= '1' ");
			Integer mailCount = Integer.parseInt(getEm().createNativeQuery(mail.toString()).getSingleResult().toString());
			return mailCount;
		}
		return 0;
	}


	@Override
	public void saveReadedEndPending(ReadedEndPending enity) {
		getEm().persist(enity);
	}
}
