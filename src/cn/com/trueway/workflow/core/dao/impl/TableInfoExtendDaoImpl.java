package cn.com.trueway.workflow.core.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.TableInfoExtendDao;
import cn.com.trueway.workflow.core.pojo.AccessLog;
import cn.com.trueway.workflow.core.pojo.AutoFile;
import cn.com.trueway.workflow.core.pojo.FileDownloadLog;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.pojo.LostAttsDf;
import cn.com.trueway.workflow.set.pojo.LostCmtDf;

/**
 * 描述：核心操作类dao层扩展 实现
 * 作者：蒋烽
 * 创建时间：2017-4-10 上午9:19:18
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@SuppressWarnings("unchecked")
public class TableInfoExtendDaoImpl extends BaseDao implements TableInfoExtendDao {

	@Override
	public List<FollowShip> selectFollowShip(String instanceId,
			String employeeGuid, String oldInstanceId) {
		StringBuffer hql = new StringBuffer(" from FollowShip t where 1=1 ");
		if(StringUtils.isNotBlank(instanceId)){
			hql.append(" and t.instanceId = :instanceId ");
		}
		if(StringUtils.isNotBlank(employeeGuid)){
			hql.append(" and t.employeeGuid = :employeeGuid ");
		}
		if(StringUtils.isNotBlank(oldInstanceId)){
			hql.append(" and t.oldInstanceId = :oldInstanceId ");
		}
		Query query = getEm().createQuery(hql.toString());
		if(StringUtils.isNotBlank(instanceId)){
			query.setParameter("instanceId", instanceId);
		}
		if(StringUtils.isNotBlank(employeeGuid)){
			query.setParameter("employeeGuid", employeeGuid);
		}
		if(StringUtils.isNotBlank(oldInstanceId)){
			query.setParameter("oldInstanceId", oldInstanceId);
		}
		return query.getResultList();
	}

	@Override
	public void insertFollowShip(FollowShip entity) {
		if(entity==null){
			return;
		}
		getEm().persist(entity);
	}

	@Override
	public void deleteFollowShip(FollowShip entity) {
		if(entity==null){
			return;
		}
		getEm().remove(entity);
	}

	@Override
	public void updateFollowShip(FollowShip entity) {
		if(entity==null){
			return;
		}
		getEm().merge(entity);
	}

	@Override
	public List<Pending> getFollowList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,String status) {
		StringBuffer querySql = (new StringBuffer("select  p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("t.wfn_name as nodeName,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("replace(p.process_title, chr(10), '') as process_title,")
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
				.append(" (select wm_concat( distinct(emp.employee_name)) from t_wf_process t2, zwkj_employee emp  where emp.employee_guid = t2.user_uid and t2.is_over = 'NOT_OVER' and t2.wf_instance_uid = p.wf_instance_uid) as entrust_name,")
				.append("p.is_over as is_over,")
				.append("p.is_end as isEnd,")
				.append("decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.is_back as is_back,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append(" '' as isDelaying, ")
				.append("p.isChildWf as isChildWf, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("(select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = d.instanceid  or p.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid='"+userId+"') as favourite, ")
				.append("p.isManyInstance as isManyInstance, ")
				.append("p.commentJson as commentJson,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from t_wf_core_follow_ship sh,t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ,T_WF_CORE_ITEM i, WF_NODE t")
				.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid and t.wfn_id=p.wf_node_uid")
				.append(" and sh.employee_guid='").append(userId).append("' and sh.wf_instance_uid = p.wf_instance_uid and sh.old_instanceid is null ")
			    .append(" and p.is_over='OVER' and p.is_show=1 ")
			    .append(conditionSql)
			    .append(" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
			    .append(" where p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and  (p2.is_back != '2' or p2.is_back is null) and p2.user_uid= '").append(userId).append("' ")
			    .append(" and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  group by p2.wf_instance_uid) and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
			    if(status.equals("2")){
					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1"); // 已办结
				}else if(status.equals("4")){
					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
				}

				querySql.append(" order by sh.is_read, p.finsh_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public Integer getCountOfFollow(String conditionSql,String userId,String status) {
		StringBuffer querySql =  new StringBuffer(2048);
		querySql.append(" select count(distinct(p.wf_instance_uid))")
				.append(" from t_wf_core_follow_ship sh,t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ,T_WF_CORE_ITEM i, WF_NODE t")
				.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid and t.wfn_id=p.wf_node_uid")
				.append(" and sh.wf_instance_uid = p.wf_instance_uid and sh.old_instanceid is null ")
			    .append(" and p.is_over='OVER' and p.is_show=1 ")
			    .append(conditionSql)
			    .append(" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
			    .append(" where  p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and  (p2.is_back != '2' or p2.is_back is null) and p2.user_uid= '").append(userId).append("' ")
			    .append(" and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  group by p2.wf_instance_uid) and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
			    if(status.equals("2")){
					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1"); // 已办结
				}else if(status.equals("4")){
					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
				}
		return Integer.parseInt(super.getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
	}
	
	@Override
	public List<Object[]> getOutOfDateList(String conditionSql, Integer pageIndex, Integer pageSize) {
		StringBuffer querySql = (new StringBuffer());
		querySql.append(" select distinct tm.* from (select p.wf_instance_uid as wf_instance_uid, p.wf_item_uid as item_id, p.jdqxdate ")
				.append(" from t_wf_core_dofile d, T_WF_CORE_ITEM i ," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid ,wf_node n ")
				.append(" where p.wf_instance_uid = d.instanceid ")
				.append(" and p.isexchanging=0  and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(conditionSql)
				.append(" ) tm order by tm.jdqxdate desc ");
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
		.append("select count(distinct p.wf_instance_uid) from" +
				" t_wf_process p ," +
				" T_WF_CORE_ITEM i,wf_node n  ")
		.append(" where p.isexchanging=0" +
				" and i.id=p.wf_item_uid" +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')" +
				" and (p.action_status is null or p.action_status != 2)")
		.append(condition).append(conditionSql)
		.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
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
			.append("p.apply_time as apply_time,")
			.append("p.wf_form_id as form_id,")
			.append("p.wf_uid as wf_workflow_uid,")
			.append("p.wf_item_uid as item_id,")
			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
			.append("i.vc_sxlx as item_type,")
			.append("p.pdfPath as pdfPath,")
			.append("p.is_master as isMaster,")
			.append("p.is_end as isEnd,")
			.append(" (select wm_concat( distinct(emp.employee_name)) from t_wf_process t2, zwkj_employee emp  where emp.employee_guid = t2.user_uid and t2.is_over = 'NOT_OVER' and t2.wf_instance_uid = p.wf_instance_uid) as entrust_name,")
			.append("( case when p.jdqxdate <= sysdate then '3' when p.jdqxdate > sysdate and sysdate > p.jdqxdate-3 then '4' else p.status end) as status,")
			.append("(select ship.wf_instance_uid from t_wf_core_follow_ship ship where ship.old_instanceid=p.wf_instance_uid and ship.employee_guid is null ) as shipinstanceId")
//			.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
//			.append("p.wf_oldform_id as oldForm_id,")
//			.append("i.RELATEDITEMID as RELATEDITEMID,")
//			.append("p.CONTINUEINSTANCEID as continueInstanceId,")
//			.append("p.step_index as stepIndex,")
//			.append("p.finsh_time as finish_time,")
//			.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
//			.append("p.commentJson as commentJson,")
//			.append("p.jdqxDate as jdqxDate,")
//			.append("p.zhqxDate as zhqxDate,")
//			.append("p.is_back as is_back,")
//			.append("p.isChildWf as isChildWf, ")
//			.append("p.allinstanceid as allInstanceid, ")
//			.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
//			.append(" ,do.id as dofileId ")
			.append(" from T_WF_CORE_ITEM i ," +
					" t_wf_process p join zwkj_department d " +
					" on p.userdeptid = d.department_guid,wf_node n  ")
			.append(" where p.wf_instance_uid = '").append(instanceId).append("' and p.isexchanging=0 " +
					" and i.id = p.wf_item_uid " +
					" and p.wf_node_uid=n.wfn_id " +
					" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
			.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
		    .append(conditionSql)
			.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString());
		return query.getResultList();
	}
	
	@Override
	public void deleteFollowShip(String instanceId, String userId) {
		String sql = "delete from t_wf_core_follow_ship t " +
				" where t.wf_instance_uid='"+instanceId+"' and t.employee_guid='"+userId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void insertAutoFile(AutoFile entity) {
		if(null == entity){
			return;
		}
		getEm().persist(entity);
	}

	@Override
	public void updateAutoFile(AutoFile entity) {
		if(null == entity){
			return;
		}
		getEm().merge(entity);
	}

	@Override
	public void addAccessLog(AccessLog entity) {
		if(null == entity){
			return;
		}
		getEm().persist(entity);
		
	}

	@Override
	public Integer countAccessLog(Map<String, String> map) {
		String methodName = map.get("methodName");
		String userName = map.get("userName");
		String sql = "select count(*) from AccessLog t, Employee e where e.employeeGuid=t.userid";
		if(StringUtils.isNotBlank(methodName)){
			sql += " and t.methodName like :methodName";
		}
		if(StringUtils.isNotBlank(userName)){
			sql += " and e.employeeName like :employeeName";
		}
		Query query = getEm().createQuery(sql);
		if(StringUtils.isNotBlank(methodName)){
			query.setParameter("methodName", "%"+methodName+"%");
		}
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("employeeName", "%"+userName+"%");
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<AccessLog> selectAccLog(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		String methodName = map.get("methodName");
		String userName = map.get("userName");
		String sql = "select t.* from t_wf_core_accesslog t, zwkj_employee e where e.employee_guid=t.userid";
		if(StringUtils.isNotBlank(methodName)){
			sql += " and t.method_name like :method_name";
		}
		if(StringUtils.isNotBlank(userName)){
			sql += " and e.employee_name like :employee_name";
		}
		Query query = getEm().createNativeQuery(sql, AccessLog.class);
		if(StringUtils.isNotBlank(methodName)){
			query.setParameter("method_name", "%"+methodName+"%");
		}
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("employee_name", "%"+userName+"%");
		}
		if(null != pageIndex && null != pageSize){
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	@Override
	public List<LostCmtDf> getLostCmtDfList(Map<String,String> map, Integer pageIndex, Integer pageSize){
		String sql = "select d.id,d.instanceid,d.dofile_title, " +
				" XMLAGG(XMLELEMENT(E, p.wf_process_uid || ',')).EXTRACT('//text()').getclobval() wf_process_uid,p.tonodeid,XMLAGG(XMLELEMENT(E, p.user_uid || ',')).EXTRACT('//text()').getclobval() user_uid,  " +
				"  XMLAGG(XMLELEMENT(E,(select distinct em.employee_name from zwkj_employee em where em.employee_guid=p.user_uid) || ',')).EXTRACT('//text()').getclobval() userName, " +
				" (select nd.wfn_name from wf_node nd where nd.wfn_id=p.tonodeid) as nodeName, " +
				"  i.vc_ssbmid, (select dept.department_name from zwkj_department dept where dept.department_guid=i.vc_ssbmid) as siteName " +
				" from t_wf_core_dofile d, t_wf_core_item i, t_wf_process p, t_wf_core_formpermit fp left join t_wf_core_form_map_column ta on ta.formid = fp.vc_formid and fp.vc_tagname = ta.FORMTAGNAME ,wf_node t" +
				" where  t.wfn_id = p.wf_node_uid and d.instanceid = p.wf_instance_uid and d.item_id=i.id and fp.lcid = p.wf_uid and p.is_back='0'" +
				" and fp.vc_formid = p.wf_form_id and fp.VC_MISSIONID = p.tonodeid and fp.VC_LIMIT = 2 and fp.isbt = '1'  and p.is_over='OVER' and p.isrepeated != '1' " +
				" and not exists (select tr.processid from t_wf_core_truejson tr where tr.instanceid = d.instanceid and p.wf_process_uid = tr.processid) " +
				" and (p.wf_instance_uid not in (select dcv.instanceid from document_circulation_view dcv) or t.wfn_onekeyhandle != 1)";
				
		if(CommonUtil.stringNotNULL(map.get("title"))){
			sql +=  " and d.dofile_title like '%" + map.get("title") +"%'";
		}
				
		sql +=	" group by d.id, p.tonodeid, d.dofile_title,d.instanceid, i.vc_ssbmid";
		
		Query query = getEm().createNativeQuery(sql);
		if(null != pageIndex && null != pageSize){
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		List<LostCmtDf> lostCmtDfList = new ArrayList<LostCmtDf>();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Object[] objs = list.get(i);
				if(objs != null){
					LostCmtDf lostCmtDf = new LostCmtDf();
					lostCmtDf.setId(objs[0] == null ? "" : objs[0].toString());
					lostCmtDf.setInstanceId(objs[1] == null ? "" : objs[1].toString());
					lostCmtDf.setTitle(objs[2] == null ? "" : objs[2].toString().replace("\r\n", "").replace("\r", ""));
					lostCmtDf.setProcessIds(objs[3] == null ? "" : objs[3].toString());
					lostCmtDf.setNodeId(objs[4] == null ? "" : objs[4].toString());
					lostCmtDf.setUserIds(objs[5] == null ? "" : objs[5].toString());
					lostCmtDf.setUserNames(objs[6] == null ? "" : objs[6].toString());
					lostCmtDf.setNodeName(objs[7] == null ? "" : objs[7].toString());
					lostCmtDf.setSiteName(objs[8] == null ? "" : objs[8].toString());
					lostCmtDfList.add(lostCmtDf);
				}
			}
		}
		return lostCmtDfList;
	}
	
	@Override
	public int getLostCmtDfCount(Map<String,String> map){
		String sql = "select count(*) from (select d.id,d.instanceid,d.dofile_title, " +
			" XMLAGG(XMLELEMENT(E, p.wf_process_uid || ',')).EXTRACT('//text()').getclobval() wf_process_uid,p.tonodeid,XMLAGG(XMLELEMENT(E, p.user_uid || ',')).EXTRACT('//text()').getclobval() user_uid,  " +
			" XMLAGG(XMLELEMENT(E,(select distinct em.employee_name from zwkj_employee em where em.employee_guid=p.user_uid) || ',')).EXTRACT('//text()').getclobval() userName, " +
			" (select nd.wfn_name from wf_node nd where nd.wfn_id=p.tonodeid) as nodeName " +
			" from t_wf_core_dofile d, t_wf_process p, t_wf_core_formpermit fp left join t_wf_core_form_map_column ta on ta.formid = fp.vc_formid and fp.vc_tagname = ta.FORMTAGNAME ,wf_node t" +
			" where t.wfn_id = p.wf_node_uid and d.instanceid = p.wf_instance_uid and fp.lcid = p.wf_uid  and p.is_back='0' " +
			" and fp.vc_formid = p.wf_form_id and fp.VC_MISSIONID = p.tonodeid and fp.VC_LIMIT = 2 and fp.isbt = '1'  and p.is_over='OVER' and p.isrepeated != '1' " +
			" and not exists (select tr.processid from t_wf_core_truejson tr where tr.instanceid = d.instanceid and p.wf_process_uid = tr.processid) " 	+
		    " and (p.wf_instance_uid not in (select dcv.instanceid from document_circulation_view dcv) or t.wfn_onekeyhandle != 1)";
		if(CommonUtil.stringNotNULL(map.get("title"))){
			sql +=  " and d.dofile_title like '%" + map.get("title") +"%'";
		}
				
		sql +=	" group by d.id, p.tonodeid, d.dofile_title,d.instanceid)";
		
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public List<LostAttsDf> getLostAttDfList(Map<String,String> map, Integer pageIndex, Integer pageSize){
		String sql = "select distinct d.id as dofileId, d.instanceid, d.dofile_title,i.id as itemId,i.vc_sxmc,i.vc_sxlx," +
			" (select e.employee_name from zwkj_employee e where e.employee_guid=p.owner) as userName, " +
			" att.docguid, " +
			" (select wm_concat(att.id) from oa_doc_attachments atts where atts.docguid = att.docguid) as attIds, " +
			" (select wm_concat(att.pagecount) from oa_doc_attachments atts where atts.docguid = att.docguid) as pages, " +
			" (select dept.department_name from zwkj_department dept where dept.department_guid=i.vc_ssbmid) as siteName, " +
            " p.wf_process_uid " +
			" from t_wf_core_dofile d " +
			" left join oa_doc_attachments att " +
			" on d.instanceid = substr(att.docguid, 0, 36), t_wf_core_item i, t_wf_process p, wf_node nd" +
	        " where d.item_id = i.id " +
	        " and (i.vc_sxlx='0' or i.vc_sxlx='1') " +
	        " and p.wf_instance_uid=d.instanceid " +
	        " and p.step_index=1 and p.wf_node_uid = nd.wfn_id and nd.WFN_ISUPLOADATTACH = '1' and docguid  is null";
			if(CommonUtil.stringNotNULL(map.get("title"))){
				sql +=  " and d.dofile_title like '%" + map.get("title") +"%'";
			}
	       
		Query query = getEm().createNativeQuery(sql);
		if(null != pageIndex && null != pageSize){
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		List<LostAttsDf> lostAttsDfList = new ArrayList<LostAttsDf>();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Object[] objs = list.get(i);
				LostAttsDf lostAttsDf = new LostAttsDf();
				lostAttsDf.setDofileId(objs[0] == null ? "" : objs[0].toString());
				lostAttsDf.setInstanceId(objs[1] == null ? "" : objs[1].toString());
				lostAttsDf.setTitle(objs[2] == null ? "" : objs[2].toString().replace("\r\n", "").replace("\r", ""));
				lostAttsDf.setItemId(objs[3] == null ? "" : objs[3].toString());
				lostAttsDf.setItemName(objs[4] == null ? "" : objs[4].toString());
				lostAttsDf.setItemType(objs[5] == null ? "" : objs[5].toString());
				lostAttsDf.setUserName(objs[6] == null ? "" : objs[6].toString());
				lostAttsDf.setDocguid(objs[7] == null ? "" : objs[7].toString());
				lostAttsDf.setAttIds(objs[8] == null ? "" : objs[8].toString());
				lostAttsDf.setPages(objs[9] == null ? "" : objs[9].toString());
				lostAttsDf.setSiteName(objs[10] == null ? "" : objs[10].toString());
				lostAttsDf.setProcessId(objs[11] == null ? "" : objs[11].toString());
				lostAttsDfList.add(lostAttsDf);
			}
		}
		return lostAttsDfList;
	}
	
	@Override
	public int getLostAttDfCount(Map<String,String> map){
		String sql = "select count(*) from (select distinct d.id as dofileId, d.instanceid, d.dofile_title,i.id as itemId,i.vc_sxmc,i.vc_sxlx," +
				" (select e.employee_name from zwkj_employee e where e.employee_guid=p.owner) as userName, " +
				" att.docguid, " +
				" (select wm_concat(att.id) from oa_doc_attachments atts where atts.docguid = att.docguid) as attIds, " +
				" (select wm_concat(att.pagecount) from oa_doc_attachments atts where atts.docguid = att.docguid) as pages " +
				" from t_wf_core_dofile d " +
				" left join oa_doc_attachments att " +
				" on d.instanceid = substr(att.docguid, 0, 36), t_wf_core_item i, t_wf_process p,wf_node nd " +
		        " where d.item_id = i.id " +
		        " and (i.vc_sxlx='0' or i.vc_sxlx='1') " +
		        " and p.wf_instance_uid=d.instanceid " +
		        " and p.step_index=1 and p.wf_node_uid = nd.wfn_id and nd.WFN_ISUPLOADATTACH = '1' and docguid  is null";
		if(CommonUtil.stringNotNULL(map.get("title"))){
			sql +=  " and d.dofile_title like '%" + map.get("title") +"%'";
		}
		sql += ")";
			return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public WfNode getWfNodeById(String id) {
		return getEm().find(WfNode.class, id);
	}

	@Override
	public List<String> getInstanceId(String nodeId, String fromNodeId) {
		String sql = "select distinct t.wf_instance_uid from t_wf_process t, wf_node n where n.wfn_id = t.wf_node_uid " +
				" and n.wfn_id = :nodeId and t.fromnodeid = :fromNodeId and t.is_over = 'NOT_OVER'" +
				" and t.wf_process_uid not in (select a.processid from t_wf_core_autosend a)";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("nodeId", nodeId);
		query.setParameter("fromNodeId", fromNodeId);
		return query.getResultList();
	}

	@Override
	public List<WfProcess> getAutoSendStep(String nodeId, String fromNodeId,
			String instanceId) {
		String sql = "select t.* from t_wf_process t, wf_node n where n.wfn_id = t.wf_node_uid and n.wfn_id = :nodeId and t.fromnodeid = :fromNodeId " +
				" and t.is_over = 'NOT_OVER' and t.wf_instance_uid=:instanceId and t.wf_process_uid not in (select a.processid from t_wf_core_autosend a)";
		Query query = getEm().createNativeQuery(sql,WfProcess.class);
		query.setParameter("nodeId", nodeId);
		query.setParameter("fromNodeId", fromNodeId);
		query.setParameter("instanceId", instanceId);
		return query.getResultList();
	}

	@Override
	public List<String> getColumnNames(String tableName) {
		if(StringUtils.isNotBlank(tableName)){
			tableName = tableName.toUpperCase();
			String sql = "select column_name from user_tab_columns where table_name='"+tableName+"'";
			return getEm().createNativeQuery(sql).getResultList();
		}
		return null;
	}

	@Override
	public List<Object[]> selectOutSideTab(String tableName, String columns,
			String instanceId) {
		String sql = "select " + columns + " from " + tableName +" where instanceid = '"+instanceId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public void insert(FileDownloadLog entity) {
		if(null == entity){
			return;
		}
		getEm().persist(entity);
	}

	@Override
	public Integer countFileDownloadLogs(Map<String,String> map) {
		String sql = "select count(*) from t_wf_core_file_download_log t where 1=1";
		String beginTime = map.get("beginTime");
		String endTime = map.get("endTime");
		String name = map.get("name");
		String title = map.get("title");
		if(StringUtils.isNotBlank(beginTime)){
			beginTime += " 00:00:00";
			sql += " and t.download_time >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime += " 23:59:59";
			sql += " and t.download_time <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and t.employee_name like '%" + name + "%'";
		}
		if(StringUtils.isNotBlank(title)){
			sql += " and t.file_title like '%" + title + "%'";
		}
		
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<FileDownloadLog> selectFileDownloadLogs(Map<String,String> map,
			Integer pageIndex, Integer pageSize) {
		String sql = "select * from t_wf_core_file_download_log t where 1=1";
		String beginTime = map.get("beginTime");
		String endTime = map.get("endTime");
		String name = map.get("name");
		String title = map.get("title");
		if(StringUtils.isNotBlank(beginTime)){
			beginTime += " 00:00:00";
			sql += " and t.download_time >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime += " 23:59:59";
			sql += " and t.download_time <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and t.employee_name like '%" + name + "%'";
		}
		if(StringUtils.isNotBlank(title)){
			sql += " and t.file_title like '%" + title + "%'";
		}
		sql += " order by t.download_time desc";
		Query query = getEm().createNativeQuery(sql, FileDownloadLog.class);
		if(null != pageIndex && null != pageSize){
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
}
