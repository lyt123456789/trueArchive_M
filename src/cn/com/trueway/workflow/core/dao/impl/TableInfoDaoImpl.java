package cn.com.trueway.workflow.core.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.SerializableClob;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.dao.ItemRelationDao;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.DofileFavourite;
import cn.com.trueway.workflow.core.pojo.DzJcdb;
import cn.com.trueway.workflow.core.pojo.DzJcdbShip;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.EmployeeLeave;
import cn.com.trueway.workflow.core.pojo.EndInstanceId;
import cn.com.trueway.workflow.core.pojo.GetProcess;
import cn.com.trueway.workflow.core.pojo.OfficeInfoView;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.PersonMessage;
import cn.com.trueway.workflow.core.pojo.PushMessage;
import cn.com.trueway.workflow.core.pojo.Reply;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfConsult;
import cn.com.trueway.workflow.core.pojo.WfCyName;
import cn.com.trueway.workflow.core.pojo.WfDuBanLog;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfHistoryNode;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.WfRecallLog;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.vo.TrueOperateLog;
import cn.com.trueway.workflow.set.util.ClobToString;

@SuppressWarnings("unchecked")
public class TableInfoDaoImpl extends BaseDao implements TableInfoDao {
	
	private PendingDao pendingDao ;
	
	private ItemRelationDao itemRelationDao;
	
	public PendingDao getPendingDao() {
		return pendingDao;
	}

	public void setPendingDao(PendingDao pendingDao) {
		this.pendingDao = pendingDao;
	}
	
	public ItemRelationDao getItemRelationDao() {
		return itemRelationDao;
	}

	public void setItemRelationDao(ItemRelationDao itemRelationDao) {
		this.itemRelationDao = itemRelationDao;
	}

	public List<String> getTableCountForPage(String column, String value,
			WfTableInfo wfTable) {
		String hql = "select id from WfTableInfo t where 1=1 ";
		if (CommonUtil.stringNotNULL(column) && CommonUtil.stringNotNULL(value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if( CommonUtil.stringNotNULL(wfTable.getVc_parent())){
			hql += " and vc_parent = '" + wfTable.getVc_parent() + "'";
		}
		if( CommonUtil.stringNotNULL(wfTable.getLcid())){
			hql += " and (lcid = '" + wfTable.getLcid() + "' or reflc like '%,"+wfTable.getLcid()+",%')";
		}
		return getEm().createQuery(hql).getResultList();
	}

	public List<WfTableInfo> getTableListForPage(String column, String value,
			WfTableInfo wfTable, Integer pageindex, Integer pagesize) {
		String hql = "from WfTableInfo t where 1=1 ";
		if (CommonUtil.stringNotNULL( column) && CommonUtil.stringNotNULL( value)) {
			hql += " and t." + column + " like '%" + value + "%'";
		}
		if( CommonUtil.stringNotNULL(wfTable.getVc_parent())){
			hql += " and vc_parent = '" + wfTable.getVc_parent() + "'";
		}
		if( CommonUtil.stringNotNULL(wfTable.getLcid())){
			hql += " and (lcid = '" + wfTable.getLcid() + "' or reflc like '%,"+wfTable.getLcid()+",%')";
		}
		Query query = getEm().createQuery(hql);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	public List<WfTableInfo> getTableByTableName(String vc_tablename) {
		String querySql = "from WfTableInfo where 1=1";
		if(vc_tablename != null && !"".equals(vc_tablename)){
			querySql += " and vc_tablename = '" + vc_tablename + "'";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}
	
	public List<WfTableInfo> getTableByMap(HashMap<String,String> map) {
		String tableName = map.get("tableName");
		String tableNameNow = map.get("tableNameNow");
		String querySql = "from WfTableInfo where 1=1";
		if(tableName != null && !"".equals(tableName)){
			querySql += " and vc_tablename not in  (" + tableName + ")";
		}
		if(tableNameNow != null && !"".equals(tableNameNow)){
			querySql += " and vc_tablename =  '" + tableNameNow + "'";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}
	
	public WfTableInfo getTableById(String id) {
		return getEm().find(WfTableInfo.class, id);
	}
	
	public WfTableInfo addTable(WfTableInfo wfTable){
		if(wfTable.getId() != null && !"".equals(wfTable.getId())){
			getEm().merge(wfTable);
		}else{
			if("".equals(wfTable.getId())){
				wfTable.setId(null);
			}
			getEm().persist(wfTable);
		}
		return wfTable;
	}
	
	public List<WfTableInfo> getTableByLcid(String lcid){
		String querySql = "from WfTableInfo where 1=1";
		if(lcid != null && !"".equals(lcid)){
			querySql += " and (lcid = '" + lcid + "' or reflc like '%," + lcid + ",%')";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}
	
	public List<WfTableInfo> getAllTableNotLc(String lcid){
		String querySql = "from WfTableInfo where 1=1 ";
		if(CommonUtil.stringNotNULL(lcid)){
			querySql += " and (lcid != '" + lcid + "' or lcid is null) and (reflc not like '%," + lcid + ",%' or reflc is null)";
		}
		return this.getEm().createQuery(querySql).getResultList();
	}

	
	public Process findProcessById(String processId) {
		String hql="from Process w where wfProcessUid='"+processId+"'";     
		Query query=super.getEm().createQuery(hql);
		List<Process> processList = query.getResultList();
		if(processList != null && !("").equals(processList) && processList.size()!=0){
			return (Process) query.getResultList().get(0);
		}else{
			return null;
		}
	}

	public void save(WfProcess process) {
		if(process==null){
			return;
		}
//		if(process.getWfProcessUid()!=null){
//			process.setWfProcessUid(null);
//		}
		getEm().persist(process);
	}
	
	public void saveDoFile(DoFile doFile) {
		if(doFile==null){
			return;
		}
		getEm().persist(doFile);
	}

	public void update(WfProcess process) {
		if(process!=null){
			getEm().merge(process);
		}
	}
	
	public List<Object[]> getNextUserList(String workFlowId,String instanceId,String nodeId){
		String sql = " select t.user_uid, t.process_title, t.fromuserid,t.wf_process_uid from t_wf_process t" +
				" where t.wf_instance_uid='"+instanceId+"' and t.wf_node_uid ='"+nodeId+"'" +
				" and (is_duplicate!='true' or is_duplicate is null) and t.is_show=0  and t.is_over='NOT_OVER' and (t.is_back is null or t.is_back != '2')";
		return getEm().createNativeQuery(sql).getResultList();
	}

	
	public void updateProcessShow(String workFlowId,String instanceId,String nodeId){
		StringBuffer hql = new StringBuffer();
		hql.append("update WfProcess set isShow=1 ");
		hql.append(" where wfUid='"+workFlowId+"' and wfInstanceUid='"+instanceId+"' and nodeUid='"+nodeId+"' and (isDuplicate!='true' or isDuplicate is null)");
		this.getEm().createQuery(hql.toString()).executeUpdate();
	}
	
	public List<WfProcess> getNodeProcess(String workFlowId,String instanceId,String nodeId,String fInstanceId){
		StringBuffer hql = new StringBuffer();
		hql.append("from WfProcess  ");  
		hql.append(" where wfUid='"+workFlowId+"' and wfInstanceUid='"+instanceId+"' and nodeUid='"+nodeId+"' and (isDuplicate!='true' or isDuplicate is null)");
		//2014.1.23guojie
		if(null!=fInstanceId&&!("").equals(fInstanceId)){
			hql.append(" and fInstancdUid='"+fInstanceId+"' ");
		}
		return this.getEm().createQuery(hql.toString()).getResultList();
	}
	
	public List<String> getUpdateOverUserList(String workFlowId,String instanceId,String nodeId ){
		StringBuffer sql= new StringBuffer();
			sql.append("select  t.user_uid from t_wf_process t where t.is_over='"+Constant.NOT_OVER+"'");
			sql.append(" and t.wf_uid='"+workFlowId+"' and t.wf_instance_uid='"+instanceId+"' and t.wf_node_uid='"+nodeId
					+"' and (t.is_duplicate!='true' or t.is_duplicate is null)");
		return getEm().createNativeQuery(sql.toString()).getResultList();
		
	}
	
	public void updateOver(String workFlowId,String instanceId,String nodeId ){
		Date date = new Date((new Date()).getTime()-1000);
		StringBuffer hql = new StringBuffer();
		hql.append(" update WfProcess t set t.isOver = :isOver, t.finshTime = :finshTime ");
		hql.append(" where t.wfUid = :workFlowId and t.wfInstanceUid = :instanceId and t.nodeUid = :nodeId ")
		.append(" and (t.isDuplicate!='true' or t.isDuplicate is null) ");
		Query query = getEm().createQuery(hql.toString());
		query.setParameter("isOver", Constant.OVER);
		query.setParameter("finshTime", date);
		query.setParameter("workFlowId", workFlowId);
		query.setParameter("instanceId", instanceId);
		query.setParameter("nodeId", nodeId);
		query.executeUpdate();
	}
	
	public void updateInstanceOver(String workflowId,String instanceId){
		StringBuffer hql = new StringBuffer();
		hql.append("update WfProcess set isOver='"+Constant.OVER+"' ");
		hql.append(" where wfUid='"+workflowId+"' and wfInstanceUid='"+instanceId
				+"' and  isOver= '"+Constant.NOT_OVER+"' and (isDuplicate!='true' or isDuplicate is null)");
		this.getEm().createQuery(hql.toString()).executeUpdate();
	}
	
	public List<Pending> getOverList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,String status) {
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
				.append(" '' as entrust_name,")
				.append("p.is_over as is_over,")
				.append("p.is_end as isEnd,")
				.append("'' as status,")
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
				.append(" p.user_uid as userName, ")
				.append("(select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = d.instanceid  or p.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid='"+userId+"') as favourite, ")
				.append("p.isManyInstance as isManyInstance, ")
				.append("'' as commentJson,d.department_name as userDeptId ")
				.append(" ,do.id as dofileId ")
				.append(" ,replace(replace(replace(replace(v.jjcd, '特急', '3'), '急', '2'),'一般',''),' ','') as urgency")
				.append(" ,v.wh as siteName")//nj需求：在待办，已办，已办结，公文流转页面新增文号，在此用siteName先存文号内容避免大改动
				.append(" from t_wf_core_dofile do,t_wf_process p " +
						" join zwkj_department d  on p.userdeptid = d.department_guid" +
						" left join wh_view v on p.wf_instance_uid=v.instanceid ,T_WF_CORE_ITEM i, WF_NODE t")
				.append(" where p.allinstanceid = do.instanceid and p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid and t.wfn_id=p.wf_node_uid")
			    .append(" and p.is_over='OVER' and p.is_show=1 ")
			    .append(conditionSql)
			    .append(" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
			    .append(" where p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and  (p2.is_back != '2' or p2.is_back is null) and p2.user_uid= '").append(userId).append("' ")
			    .append(" and (p.isrepeated != 1 or p.isrepeated is null) and p2.is_end!='1' and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  group by p2.wf_instance_uid) and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
//			    if(status.equals("2")){
//					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1"); // 已办结
//				}else if(status.equals("4")){
//					querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
//				}
			    if(status.equals("2")){
					querySql.append(" and (select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId)>0 and p.is_end!='1' "); // 已办结
				}else if(status.equals("4")){
					querySql.append(" and (select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId)= 0"); // 已办未办结
				}
				querySql.append(" order by p.finsh_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	/**
	 * 查询已办列表的待办条数
	 */
	public int getCountOfOver(String conditionSql,String userId,String status) {
		try {
			StringBuffer querySql =  new StringBuffer(2048);
			querySql.append(" select count(distinct(p.wf_instance_uid))")
			.append(" from t_wf_core_dofile do,t_wf_process p " +
					" join zwkj_department d  on p.userdeptid = d.department_guid" +
					" left join wh_view v on p.wf_instance_uid=v.instanceid ,T_WF_CORE_ITEM i, WF_NODE t")
					.append(" where  p.allinstanceid = do.instanceid and p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid and t.wfn_id=p.wf_node_uid")
					.append(" and p.is_over='OVER' and p.is_show=1 ")
					.append(conditionSql)
					.append(" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
					.append(" where  p2.wf_instance_uid = p.wf_instance_uid and p2.process_title is not null and  (p2.is_back != '2' or p2.is_back is null) and p2.user_uid= '").append(userId).append("' ")
					.append(" and (p.isrepeated != 1 or p.isrepeated is null) and p2.is_end!='1' and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  group by p2.wf_instance_uid) and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
			if(status.equals("2")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 1 and p.is_end!='1' "); // 已办结
			}else if(status.equals("4")){
				querySql.append(" and decode((select count(1) from t_wf_core_end_instanceid t where p.wf_instance_uid = t.instanceId), 0, 0, 1) = 0"); // 已办未办结
			}
			return Integer.parseInt(super.getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getCountOfOver(String conditionSql,String userId, String itemid,
			String statustype) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p ,T_WF_CORE_ITEM i")
		.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid ");
		query.append(" and p.is_over='OVER' and p.is_show=1")
		.append(conditionSql)
	    .append(" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
	    .append(" where  p2.is_back != '2' and p2.user_uid= '").append(userId).append("' ");
	    if(itemids != null && !"".equals(itemids)){
	    	query.append( " and p.wf_item_uid in (")
				.append(itemids)
				.append(") ");
		}
		if(statustype != null && !"".equals(statustype)){
			query.append(" and p.status='")
			.append(statustype)
			.append("' ");
		}
		query.append(" and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  group by p2.wf_instance_uid)");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}

	public String getCommentTagIds(String wfInstanceUid) {
		StringBuffer query = new StringBuffer()
		.append("select tag_id from WF_COMMENT c ")
		.append(" where c.wf_instance_id='"+wfInstanceUid+"'");
		List<String> list = super.getEm().createNativeQuery(query.toString()).getResultList();
		String tagIds = "";
		for(String o : list){
			tagIds += o + ",";
		}
		if(tagIds.length()>0) tagIds = tagIds.substring(0,tagIds.length()-1);
		
		return tagIds;
	}
	

	public List<WfFieldInfo> getFieldByTableid(String tableid) {
		String hql = "from WfFieldInfo t where ";
			hql += " i_tableid = '" + tableid+ "'";
		Query query = getEm().createQuery(hql);
		return query.getResultList();
	}

	public List<String[]> getTableAndColumnName(String fieldId) {
		StringBuffer buffer = new  StringBuffer();
		buffer.append("select c.vc_fieldname,t.vc_tablename from T_WF_CORE_FIELDINFO c, T_WF_CORE_TABLE t ")
			  .append(" where t.id = c.i_tableid and c.id='"+fieldId+"'");
		List<Object[]> list = super.getEm().createNativeQuery(buffer.toString()).getResultList();
		List<String[]> returnList = new ArrayList<String[]>();
		for(int i=0; list!=null && i<list.size();i++){
			String[] value = new String[3];
			Object[] data =  (Object[]) list.get(0);
			String fieldname = data[0].toString();
			String tablename = data[1].toString();
			String sql = "select t.formtagname from T_WF_CORE_FORM_MAP_COLUMN t where t.columnname='"+fieldname.toUpperCase()+"'" +
					" and t.tablename='"+tablename+"'";
			List<String> tagList = getEm().createNativeQuery(sql).getResultList();
			String tag = fieldname;
			if(tagList!=null && tagList.size()>0){
				tag = tagList.get(0).toString();
			}
			value[0]= fieldname;
			value[1] = tablename;
			value[2] = tag;
			returnList.add(value);
		}
		return returnList;
	}
	
	public List<GetProcess> findProcessList(String instanceId) {
		StringBuffer querySql = (new StringBuffer("select "))
					 .append("twp.wf_process_uid as wfProcessUid,")
					 .append("twp.wf_instance_uid as wfInstanceUid,")
					 .append("twp.wf_uid as wf_uid,")
					 .append("twp.step_index as stepIndex,")
					 .append("twp.is_end as isEnd,")
					 .append("twp.wf_form_id as formId,")
					 .append("(select t.wfn_name from wf_node t where t.wfn_id = twp.wf_node_uid) as nodeName,")
					 .append("twp.wf_node_uid as nodeId,")
					 .append("twp.user_uid as userId,")
					 .append("(select t.name from user_dep_view t where t.uuid = twp.fromuserid) as fromUserName,")
					 .append("emp.employee_name  as userName,")
					 .append("twp.apply_time as applyTime,")
					 .append("twp.finsh_time as finshTime, ")
					 .append("twp.IS_BACK as is_back, ")
					 .append(" (select decode(count(*), 0, 0, 1)  from t_wf_process p where p.wf_f_instance_uid = twp.wf_instance_uid and twp.dotype=3 )  as isHaveChild, ")
					 .append("twp.fromnodeid as fromNodeId, ")
					 .append("twp.tonodeid as toNodeId, ")
					 .append("twp.push_childprocess as push_childprocess, ")
					 .append("twp.is_merge as is_merge, ")
					 .append("twp.dotype as doType, ")
					 .append("twp.wf_f_instance_uid as f_instanceId, ")
					 .append("twp.jssj as jssj, ")
					 .append("twp.isRedirect as isRedirect, ")
					 .append("twp.pdfPath as pdfPath, ")
					 .append("twp.wf_uid as workflowId, ")
					 .append("twp.is_over as isOver, ")
					 .append("twp.fjbProcessId as fjbProcessId, ")
					 .append("twp.isreturnstep as isreturnstep ")
					 .append(" from t_wf_process twp, zwkj_employee emp left join zwkj_department dep on dep.department_guid = emp.department_guid where emp.employee_guid = twp.user_uid" +
					 		" and twp.wf_instance_uid='")
					 .append(instanceId).append("'  and (twp.IS_DUPLICATE!='true' or twp.IS_DUPLICATE is null)")
					 .append(" order by twp.step_index,dep.tabindex, emp.tabindex  asc ");
		return getEm().createNativeQuery(querySql.toString(), "GetProcessResults").getResultList();
	}

	
	public List<WfProcess> findProcessList(String nodeId, String instanceId,String userId) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.wf_node_uid='"+nodeId+"'  and t.step_index !=1  and (t.IS_DUPLICATE!='true' or t.IS_DUPLICATE is null)";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}

	public String findUserNameByUserId(String userUid) {
		String sql = "select t.employee_name from zwkj_employee t where t.employee_guid='"+userUid+"'";
		return getEm().createNativeQuery(sql).getSingleResult().toString();
	}

	
	public List<DoFile> getDoFileList(String bigDepId,String conditionSql,Integer pageindex, Integer pagesize) {
		StringBuffer sb = new StringBuffer();
//		sb.append( "select d.* from t_wf_core_dofile d where d.instanceid in ( select distinct t.instanceid from t_wf_core_dofile t" +
//				" left join wh_view v on t.instanceid = v.instanceid" +
//				" left join wh_qrcode_info qr on t.instanceid = qr.instanceid" +
//				" left join t_wf_process p on t.instanceid = p.wf_instance_uid," +
//				" T_WF_CORE_ITEM i ,zwkj_employee emp,zwkj_department dep " +
//				" where   (t.isdelete!=1 or t.isdelete is  null) and t.item_id=i.id " +
//				" and dep.department_guid=emp.department_guid " +
//				" and emp.employee_guid=p.owner");
//		if ( CommonUtil.stringNotNULL(bigDepId)){
//			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
//		}
		sb.append("select t3.* from (select distinct t.instanceid  from t_wf_core_dofile t left join wh_view v on t.instanceid = v.instanceid" +
				" left join wh_qrcode_info qr on t.instanceid = qr.instanceid ,t_wf_process p , T_WF_CORE_ITEM i, zwkj_employee emp, " +
				"zwkj_department dep where (t.isdelete != 1 or t.isdelete is null) and t.instanceid = p.wf_instance_uid and t.item_id = i.id " +
				"and dep.department_guid = emp.department_guid and emp.employee_guid = p.owner");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' "+conditionSql+")t1, ");
		}
		sb.append(" t_wf_process t2,t_wf_core_dofile t3  where t1.instanceid= t2.wf_instance_uid and t2.step_index = '1' and t1.instanceid= t3.instanceid");
		sb.append(" order by t2.apply_time desc");
//		sb.append(conditionSql).append(" ) order by d.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}
	
	
	public List<DoFile> getDoFileList1(String bigDepId,String conditionSql,Integer pageindex, Integer pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select t.* from t_wf_core_dofile t,T_WF_CORE_ITEM i where   (t.isdelete!=1 or t.isdelete is  null) and t.item_id=i.id ");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		sb.append(conditionSql).append(" order by t.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}

	public int getCountDoFiles(String bigDepId,String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(distinct t.instanceid) from t_wf_core_dofile t" +
				" left join wh_view v on t.instanceid = v.instanceid" +
				" left join wh_qrcode_info qr on t.instanceid = qr.instanceid" +
				" left join t_wf_process p on t.instanceid = p.wf_instance_uid," +
				" T_WF_CORE_ITEM i ,zwkj_employee emp,zwkj_department dep " +
				" where  (t.isdelete!=1 or t.isdelete is  null) and  t.item_id=i.id " +
				" and dep.department_guid=emp.department_guid " +
				" and emp.employee_guid=p.owner");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "'");
		}
		if ( CommonUtil.stringNotNULL(conditionSql)){
			sb.append(conditionSql);
		}
		return Integer.parseInt(getEm().createNativeQuery(sb.toString()).getSingleResult().toString());
	}
	
	public int getCountDoFilesByPsbj(int type, Map<String,String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) ");
		sb.append("  from t_wf_process t2 ")
		.append("  join zwkj_employee e on e.employee_guid=t2.USER_UID  where  (select count(*) from t_wf_process zt where zt.wf_f_instance_uid=t2.wf_instance_uid)>0 and t2.wf_instance_uid ");
		if(type==1){
			sb.append(" not ");
		}
		sb.append(" in   (select t1.wf_instance_uid from t_wf_process t1 where t1.is_end = 1) ")
		.append(" and t2.is_over = 'OVER' ").append(" and t2.wf_node_uid in ").append(" (select t.wfn_id from wf_node t where t.wfn_name = '"+map.get("wfn_name")+"')  ");
		if(map.get("psr")!=null&&!"".equals(map.get("psr"))){
			sb.append(" and e.employee_guid in ( '"+map.get("psr").replace(",", "','")+"')");
		}
		if(map.get("dofile_title")!=null&&!"".equals(map.get("dofile_title"))){
			sb.append(" and t2.process_title like '%"+map.get("dofile_title")+"%'");
		}
		if(map.get("endTime")!=null&&!"".equals(map.get("endTime"))){
			sb.append(" and t2.apply_time <=to_date('"+map.get("endTime")+"','yyyy-mm-dd')");
		}

		if(map.get("startTime")!=null&&!"".equals(map.get("startTime"))){
			sb.append(" and t2.apply_time >=to_date('"+map.get("startTime")+"','yyyy-mm-dd')");
		}
		if(type==3){
			sb.append(" and t2.isyy=1");
		}
		if(type==2){
			sb.append(" and (t2.isyy!=1 or t2.isyy is null)");
		}
		return ((BigDecimal)super.getEm().createNativeQuery(sb.toString()).getResultList().get(0)).intValue();
	}
	
	
	public List<WfProcess> getProcessList(String instanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"'  and (t.IS_DUPLICATE!='true' or t.IS_DUPLICATE is null) " +
				"  order by t.finsh_time desc";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}
	
	public List<Object[]> getFastProcessList(String instanceId) {
		String sql = "select t.WF_PROCESS_UID as WF_PROCESS_UID ,t.IS_OVER as  IS_OVER from t_wf_process t where t.wf_instance_uid='"+instanceId+"'  and (t.IS_DUPLICATE!='true' or t.IS_DUPLICATE is null) " +
				"  order by t.finsh_time desc";
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	public List<String> getFastItemList(String itemId) {
		String sql = "select t.VC_SXLX  from T_WF_CORE_ITEM t where t.id='"+itemId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	public String findItemNameById(String itemId) {
		String sql = "select t.vc_sxmc from t_wf_core_item t where t.id='"+itemId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void saveConsult(WfConsult consult) {
		if(consult==null){
			return;
		}
		getEm().persist(consult);
	}

	public List<WfConsult> getConsultList(String userId,String condition,Integer pageindex, Integer pagesize) {
		String hql = "from WfConsult where toUserId='"+userId+"' and isShow='true' ";
		if(!CommonUtil.stringIsNULL(condition)){
			hql += condition;
		}
		hql += " order by isReplied,isRead,sendTime desc ";
		Query query=super.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		List<WfConsult> list = query.getResultList();
		return list;
		
		
	}

	public int getCountConsults(String userId,String condition ) {
		String hql = "select count(*) from WfConsult where toUserId='"+userId+"' and isShow='true' ";
		if(!CommonUtil.stringIsNULL(condition)){
			hql += condition;
		}
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
		
	}

	public void updateConsultRead(String id) {
		String hql = "update WfConsult set isRead='true' where id='"+id+"'";
		this.getEm().createQuery(hql).executeUpdate();
	}

	public void updateConsultReplied(String id) {
		String hql = "update WfConsult set isReplied='true' where id='"+id+"'";
		this.getEm().createQuery(hql).executeUpdate();
	}

	public WfConsult getConsultById(String id) {
		String hql = "from WfConsult where id='"+id+"'";
		Query query=super.getEm().createQuery(hql);
		List<WfConsult> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public WfProcess getProcessById(String processId) {
		String hql = "from WfProcess where wfProcessUid='"+processId+"'";
		Query query=super.getEm().createQuery(hql);
		List<WfProcess> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public int getCountOfDuplicate(String conditionSql, String employeeGuid) {
		StringBuffer querySql = (new StringBuffer("select *"))
		.append("select count(*) from t_wf_process p ,T_WF_CORE_ITEM i")
		.append(" where p.user_uid= '").append(employeeGuid).append("' and i.id=p.wf_item_uid  ")
	    .append(" and p.is_show=1 and p.IS_DUPLICATE='true' ")
	    .append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
	}

	public List<Pending> getDuplicateList(String conditionSql,
			String employeeGuid, Integer pageIndex, Integer pageSize) {
		StringBuffer querySql = (new StringBuffer("select   p.jssj as jssj,"))
		.append("p.wf_process_uid as wf_process_uid,")
		.append("p.wf_node_uid as wf_node_uid,")
		.append("p.wf_instance_uid as wf_instance_uid,")
		.append("p.wf_uid as wf_workflow_uid,")
		.append("p.process_title as process_title,")
		.append("p.wf_item_uid as item_id,")
		.append("p.wf_form_id as form_id,")
		.append("p.wf_oldform_id as oldForm_id,")
		.append("i.vc_sxmc as item_name,")
		.append("i.vc_sxlx as item_type,")
		.append("p.step_index as stepIndex,")
		.append("p.apply_time as apply_time,")
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
		//p.is_end as isEnd, 办结这一步取pdf 
		.append("p.is_end as isEnd,")
		.append("p.is_master as isMaster,")
		.append("p.pdfPath as pdfPath,")
		.append("p.is_back as is_back,")
		.append(" '' as pressStatus, ")
		.append(" '' as presscontent, ")
		.append(" '' as userName, ")
		.append("'' as favourite, ")
		.append("p.commentJson as commentJson,d.department_name as userDeptId ")
		.append(" ,'' as dofileId ")
		.append(" ,'' as urgency")
		.append(" ,'' as siteName")
		.append(" from  t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid  ,T_WF_CORE_ITEM i ")
		.append(" where p.user_uid= '").append(employeeGuid).append("' and i.id=p.wf_item_uid ")
	    .append(" and p.is_show=1 and p.IS_DUPLICATE='true' ")
	    .append(conditionSql)
		.append(" order by p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
		
	}

	public void updateDuplicateNotShow(String ids) {
		String sql = "update T_WF_PROCESS set IS_SHOW=0 where WF_PROCESS_UID in ("+ids+")";
		this.getEm().createNativeQuery(sql).executeUpdate();
		
	}

	public void updateOverForEntrust(String workFlowId, String instanceId,
			String nodeId, String entrustUserId,String processId) {
		StringBuffer hql = new StringBuffer();
		hql.append("update t_wf_process t set t.is_over='"+Constant.OVER+"'");
		hql.append(",t.finsh_time=sysdate");
		hql.append(" where t.wf_uid='"+workFlowId+"' and t.wf_instance_uid='"+instanceId+"' and t.wf_node_uid='"+nodeId+"' and (t.is_duplicate!='true' or t.is_duplicate is null)");
		hql.append(" and (t.entrustuserid='"+entrustUserId+"' or t.user_uid='"+entrustUserId+"') and t.wf_process_uid!='"+processId+"'");
		this.getEm().createNativeQuery(hql.toString(),WfProcess.class).executeUpdate();
		
	}

	public boolean isDuplicated(WfProcess dupProcess) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from WfProcess where wfInstanceUid='"+dupProcess.getWfInstanceUid()+"'")
			.append(" and fromUserId='"+dupProcess.getFromUserId()+"'")
			.append(" and userUid='"+dupProcess.getUserUid()+"'")
			.append(" and nodeUid='"+dupProcess.getNodeUid()+"'")
			.append(" and isOver='NOT_OVER' and isShow=1 ")
			.append(" and formId='"+dupProcess.getFormId()+"'")
//			.append(" and entrustUserId='"+dupProcess.getEntrustUserId()+"'");
			.append(" and isDuplicate='"+dupProcess.getIsDuplicate()+"'");
		List<WfProcess> list = this.getEm().createQuery(hql.toString()).getResultList();
		if(list.size()>0){
			return true;
		}
		return false;
	}

	public boolean hasEntrust(WfProcess dupProcess) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from WfProcess where wfInstanceUid='"+dupProcess.getWfInstanceUid()+"'")
			.append(" and userUid='"+dupProcess.getUserUid()+"'")
			.append(" and nodeUid='"+dupProcess.getNodeUid()+"'")
			.append(" and isOver='NOT_OVER' and isShow=1 ")
			.append(" and formId='"+dupProcess.getFormId()+"'")
			.append(" and entrustUserId='"+dupProcess.getEntrustUserId()+"'");
//			.append(" and isDuplicate='"+dupProcess.getIsDuplicate()+"'");
		List<WfProcess> list = this.getEm().createQuery(hql.toString()).getResultList();
		if(list.size()>0){
			return true;
		}
		return false;
	}

	
	public String findDeptNameByEmpId(Employee emp) {
		String sql = "select t.department_shortdn from zwkj_department t where " +
				"t.department_guid=(select t.department_guid from zwkj_employee t where t.employee_guid='"+emp.getEmployeeGuid()+"')";
		List<Object> list = getEm().createNativeQuery(sql.toString()).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0)!=null?list.get(0).toString():"";
		}else{
			return null;
		}
	}

	
	public List<WfItem> findItemList() {
		String hql = "from WfItem t where 1=1 ";
		return getEm().createQuery(hql).getResultList();
	}

	public void savePerMes(PersonMessage perMes) {
		if(perMes != null){
			super.getEm().persist(perMes);
		}
	}

	public PersonMessage findXccNamesByItemId(String itemId,String userId) {
		String hql = "from PersonMessage t where t.itemId='"+itemId+"' and t.employeeId='"+userId+"'";
		List<PersonMessage> list = getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public void updatePerMes(PersonMessage pm) {
		if(pm!=null){
			getEm().merge(pm);
		}		
	}
	
	public void deletePerMes(String id){
		String sql = "delete from t_wf_core_person_message t where t.id='"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}


	public void updateConsultNotShow(String ids) {
		String sql = "update WF_CONSULT set IS_SHOW='false' where ID in ("+ids+")";
		this.getEm().createNativeQuery(sql).executeUpdate();
	}

	
	public WfItem findItemByWorkFlowId(String workFlowId) {
		String sql = "select * from t_wf_core_item t where t.lcid='"+workFlowId+"'";
		List<WfItem> list = getEm().createNativeQuery(sql,WfItem.class).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	@Override
	public WfItem findItemByWorkFlowId(String workFlowId,String webId) {
		String sql = "select * from t_wf_core_item t where t.lcid='"+workFlowId+"' and t.vc_ssbmid='"+webId+"'";
		List<WfItem> list = getEm().createNativeQuery(sql,WfItem.class).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public String findNameByEmpId(String userId) {
		String sql = "select t.employee_name from zwkj_employee t where t.employee_guid='"+userId+"'";
		return getEm().createNativeQuery(sql).getSingleResult().toString();
	}

	
	public List<WfCyName> findWfCyPersonNameByInstanceId(String instanceId) {
		String sql = "select t.* from T_WF_CORE_CYLIST t where t.instance_id='"+instanceId+"' and t.PERSON_NAME is not null order by t.sort";
		return getEm().createNativeQuery(sql,WfCyName.class).getResultList();
	}
	
	
	public List<WfCyName> findWfCyOfficeNameByInstanceId(String instanceId) {
		String sql = "select t.* from T_WF_CORE_CYLIST t where t.instance_id='"+instanceId+"' and t.OFFICE_NAME is not null order by t.sort";
		return getEm().createNativeQuery(sql,WfCyName.class).getResultList();
	}
	
	public void saveWfCyName(WfCyName wfCyName) {
		if (wfCyName != null) {
			getEm().persist(wfCyName);
		}
	}

	public void updateWfCyName(WfCyName wcn) {
		getEm().merge(wcn);
	}

	public void delelteWfCyPersonName(WfCyName wfCyName, String instanceId) {
		String sql = "delete from T_WF_CORE_CYLIST t where t.INSTANCE_ID='"+instanceId+"' and t.PERSON_NAME is not null";
		getEm().createNativeQuery(sql).executeUpdate();
	}
	
	public void delelteWfCyOfficeName(WfCyName wfCyName,String instanceId) {
		String sql = "delete from T_WF_CORE_CYLIST t where t.INSTANCE_ID='"+instanceId+"' and t.OFFICE_NAME is not null";
	    getEm().createNativeQuery(sql).executeUpdate();
	}

	
	public String findDeptNameByUserId(String userId) {
		String sql = "select t.department_name from zwkj_department t where t.department_guid " +
				"	=(select t.department_guid from zwkj_employee t where t.employee_guid='"+userId+"')";
		List<Object> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0)!=null?list.get(0).toString():"";
		}else{
			return null;
		}
	}

	
	@Override
	public WfFieldInfo getFieldById(String id) {
		String hql = "from WfFieldInfo t where ";
		hql += " id = '" + id+ "'";
		Query query = getEm().createQuery(hql);
		List<WfFieldInfo> list=query.getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public List<Object[]> getListBySql(String sql) {
		return getEm().createNativeQuery(sql).getResultList();
	}

	
	public List<WfProcess> findProcesses(String workFlowId, String instanceId, String nextNodeId) {
		String  sql = "select t.* from T_WF_PROCESS t where t.wf_instance_uid='"+instanceId+"' and t.wf_node_uid='"+nextNodeId+"' and t.wf_uid='"+workFlowId+"' and t.is_back !='2'";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	public void updateWfProcess(WfProcess wfProcess) {
		getEm().merge(wfProcess);
	}

	
	public List<Process> findProcessListByElements(String workFlowId, String instanceId) {
		String sql = "select t.* from T_WF_PROCESS t where t.wf_instance_uid='"+instanceId+"' and t.wf_uid='"+workFlowId+"'";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	public void deleteWfProcess(String workFlowId, String instanceId) {
		String sql = "delete from t_wf_process t where t.wf_instance_uid='"+instanceId+"'" ;
		if(workFlowId!=null && !workFlowId.equals("")){
			sql +=	" and t.wf_uid='"+workFlowId+"'";
		}
		super.getEm().createNativeQuery(sql).executeUpdate();
	}
	
	public void deleteWfProcessByAllInstanceId(String allInstanceId) {
		String sql = "delete from T_WF_PROCESS t where t.allinstanceid = '"+allInstanceId+"'";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}
	
	public List<WfProcess> getWfProcessByAllInstanceId(String allInstanceId){
		String hql = " from WfProcess t where t.allInstanceid='"+allInstanceId+"'";
		return getEm().createQuery(hql).getResultList();
		
	}
	
	public DoFile getDoFileByElements(String workFlowId, String instanceId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.* from T_WF_CORE_DOFILE t where  (t.isdelete!=1 or t.isdelete is  null)")
			  .append(" and t.instanceid=:instanceId ");
		if(StringUtils.isNotBlank(workFlowId)){
			buffer.append(" and t.workflowid=:workFlowId ");
		}
		Query query = getEm().createNativeQuery(buffer.toString(),DoFile.class);
		query.setParameter("instanceId", instanceId);
		if(StringUtils.isNotBlank(workFlowId)){
			query.setParameter("workFlowId", workFlowId);
		}
		List<DoFile> list = query.getResultList();
		if(list!=null){
			if(list.size()>1){//如果办件数大于一条，将多余的删除以免出现重复数据
				for (int i = 1; i < list.size(); i++) {
					DoFile doFile = list.get(i);
					deleteDoFile(doFile);
				}
				return list.get(0);
			}else if(list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}
	
	public void updateDoFile(DoFile doFile) {
		getEm().merge(doFile);
	}

	public void deleteFwByElements(String workFlowId, String instanceId) {
		String sql = "delete from T_WF_CORE_WH_FW t where t.instanceid='"+instanceId+"' and t.workflowid='"+workFlowId+"'";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	public void deleteBwByElements(String workFlowId, String instanceId) {
		String sql = "delete from T_WF_CORE_WH_BW t where t.instanceid='"+instanceId+"' and t.workflowid='"+workFlowId+"'";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	public Employee findEmpByUserId(String userId) {
		return getEm().find(Employee.class, userId);
	}
	
	public List<Employee> findEmpsByUserIds(String userIds){
		List<String> userId = new ArrayList<String>();
		if(StringUtils.isNotBlank(userIds)){
			for (String id : userIds.split(",")) {
				userId.add(id);
			}
			String hql = "from Employee t where t.employeeGuid in (:userIds)";
			Query query = getEm().createQuery(hql);
			query.setParameter("userIds", userId);
			return query.getResultList();
		}
		return null;
		
	}
	
	public List<WfProcess> findWfProcessByInstanceId(String wf_instance_uid) {
		String sql = "select t.* from T_WF_PROCESS t where t.wf_instance_uid='"+wf_instance_uid+"' order by t.step_index desc";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	@Override
	public Object[] getToDbInfoIds(String nodeId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select f.id,f.WORKFLOWID from WF_NODE n ,T_WF_CORE_FORM f where n.WFN_ID='"+nodeId+"' and n.WFN_DEFAULTFORM=f.id");
		List<Object[]> list = this.getEm().createNativeQuery(sql.toString()).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	//分为办理和已办结 加上 流程名
	@Override
	public List<Pending> getOverList2(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		    StringBuffer querySql = (new StringBuffer("select   p.jssj as jssj,"))
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
				.append("i.vc_sxmc as item_type,")
				.append("p.step_index as stepIndex,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append(
						"(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append(
						"(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.is_back as is_back,")
				.append("p.jdqxDate as jdqxDate,")
				.append("p.zhqxDate as zhqxDate,")
				.append(" '' as delay_itemid,")
				.append(" '' as delay_lcid,")
				.append(" '' as isDelaying, ")
				.append("p.allinstanceid as allInstanceid, ")
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.commentJson as commentJson ,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from  t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ,T_WF_CORE_ITEM i ")
				.append(" where p.user_uid= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid ")
				.append(" and p.is_over='OVER' and p.is_show=1 ")
				//p.is_end as isEnd, 办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append(conditionSql)
				.append(
						" and p.finsh_time in (select max(p2.finsh_time) from t_wf_process p2 ")
				.append(" where  p2.is_back != '2' and  p2.user_uid= '")
				.append(userId)
				.append("' ")
				.append(
						" and p2.is_over='OVER' and p2.is_show=1 and p2.finsh_time is not null  group by p2.wf_instance_uid) and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
				if(itemids != null && !"".equals(itemids)){
					querySql.append( "and p.wf_item_uid in (")
						.append(itemids)
						.append(") ");
				}
				if(statustype != null && !"".equals(statustype)){
					querySql.append(" and p.status='")
					.append(statustype)
					.append("' ");
				}
						
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),
				"PendingResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	
	public List<WfProcess> findWfProcessByInstanceIdAndStepIndex( String instanceId, Integer stepIndex) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.step_index in ("+stepIndex+","+(stepIndex+1)+")";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	/*获得我的请假个数*/
	public int getCountOfMyLeave(String conditionSql,String userId, String itemid,
			String statustype) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p ,T_WF_CORE_ITEM i")
		.append(" where p.owner= '").append(userId).append("' and i.id=p.wf_item_uid ");
		query.append("  and p.is_show=1")
		.append(conditionSql);
		query.append(" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
	    .append(" where p3.owner= '").append(userId).append("' ");
	    if(itemids != null && !"".equals(itemids)){
	    	query.append( " and p.wf_item_uid in (")
				.append(itemids)
				.append(") ");
		}
		if(statustype != null && !"".equals(statustype)){
			query.append(" and p.status='")
			.append(statustype)
			.append("' ");
		}
		query.append(" and p3.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)  and p3.wf_instance_uid=p.wf_instance_uid )");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	//获得我的请假 条 加上 流程名
	@Override
	public List<Pending> getMyLeaveList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		    StringBuffer querySql = (new StringBuffer("select   p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("p.wf_instance_uid as wf_instance_uid,")
				.append("p.wf_uid as wf_workflow_uid,")
				.append("p.process_title as process_title,")
				.append("p.wf_item_uid as item_id,")
				.append("p.wf_form_id as form_id,")
				.append("p.wf_oldform_id as oldForm_id,")
				.append("i.vc_sxmc as item_name,")
				.append("i.vc_sxmc as item_type,")
				.append("p.step_index as stepIndex,")
				.append("p.apply_time as apply_time,")
				.append("p.finsh_time as finish_time,")
				.append(" '未知' as remainTime,")
				// 剩余时间，先不赋真实值，根据期限算出
				.append(" '0' as warnType,")
				// 预警类型，先不赋真实值，根据期限算出
				.append(
						"(select nvl(n.wfn_deadline,0) from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadline,")
				.append(
						"(select n.wfn_deadlineunit from WF_NODE n where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.fromuserid) as employee_name,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.owner) as owner,")
				.append(
						"(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				//p.is_end as isEnd, 办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.status as status,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("p.is_back as is_back,")
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.commentJson as commentJson,d.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from  t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid  ,T_WF_CORE_ITEM i ")
				.append(" where p.owner= '")
				.append(userId)
				.append("' and i.id=p.wf_item_uid ")
				.append(" and  p.is_show=1 ")
				.append(conditionSql)
				/*
				.append(
						" and p.apply_time in (select max(p2.apply_time) from t_wf_process p2 ")
				.append(" where p2.owner= '")
				.append(userId)
				.append("' ")
				.append( " and  p2.is_show=1   group by p2.wf_instance_uid) " )
				*/
				.append(
						" and p.step_index in (select max(p3.step_index) from t_wf_process p3 ")
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
				if(statustype != null && !"".equals(statustype)){
					querySql.append(" and p.status='")
					.append(statustype)
					.append("' ");
				}
						
				querySql.append(" order by p.apply_time desc ");
		
		    
		    Query query = super.getEm().createNativeQuery(querySql.toString(),
				"PendingResults");

		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	public boolean otherMultiChildProcessIsEnd(String fInstanceId, String instanceId) {
		
		// 找到最多index
		String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid in ( select t.wf_instance_uid from t_wf_process t where t.wf_f_instance_uid ='"+fInstanceId+"') and " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid ) and t.wf_f_instance_uid != '"+instanceId+"'";
		
		/*String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+fInstanceId+"' and " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid ) and t.wf_instance_uid != '"+instanceId+"'";
		*/List<WfProcess> list = getEm().createNativeQuery(hql,WfProcess.class).getResultList();
		if(list != null && list.size() > 0){
			for (WfProcess wfProcess : list) {
				if(wfProcess.getStatus().equals("0")){
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
		
	}

	public boolean otherSingleChildProcessIsEnd(String fInstanceId, String instanceId) {
		
		// 找到最多index
		/*String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid in ( select t.wf_instance_uid from t_wf_process t where t.wf_f_instance_uid ='"+fInstanceId+"') and " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid ) and t.wf_f_instance_uid != '"+instanceId+"'";
		*/
		String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+fInstanceId+"' and " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid ) and t.wf_instance_uid != '"+instanceId+"'";
		List<WfProcess> list = getEm().createNativeQuery(hql,WfProcess.class).getResultList();
		if(list != null && list.size() > 0){
			for (WfProcess wfProcess : list) {
				if(wfProcess.getStatus().equals("0")){
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
		
	}
	
	public boolean isChildProcess(String instanceId) {
		String hql = "from WfProcess p where p.wfInstanceUid = '"+instanceId+"' and p.fInstancdUid is not null";
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		if(list != null && list.size() >0){
			return true;
		}else{
			return false;
		}
		
	}

	public List<WfProcess> findMultiChildProcessList(String fInstanceId) {
		String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid in ( select t.wf_instance_uid from t_wf_process t where t.wf_f_instance_uid ='"+fInstanceId+"') and " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid )";
		List<WfProcess> list = getEm().createNativeQuery(hql,WfProcess.class).getResultList();
		return list;
	}

	public List<WfProcess> findSingleChildProcessList(String fInstanceId) {
		String hql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+fInstanceId+"' and  " +
				"t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid )";
		List<WfProcess> list = getEm().createNativeQuery(hql,WfProcess.class).getResultList();
		return list;
	}
	
	@Override
	public Date getDoFileApplyDate(String instanceId, String workFlowId) {
/*		String sql  = "select p.apply_time from t_wf_process p " +
				" where p.step_index =1 and  p.WF_INSTANCE_UID = '"+instanceId+"'" +
				" and p.wf_uid = '"+workFlowId+"'";*/
		return null;
	}

	@Override
	public WfNode getWfnNodeByInstanceId(String instanceId) {
		String sql = "select t.* from wf_node t , t_wf_process p where p.wf_node_uid = t.wfn_id" +
				" and  p.wf_instance_uid = '"+instanceId+"' and p.is_over = 'NOT_OVER' and p.is_back!='2' ";
		return (WfNode)getEm().createNativeQuery(sql,WfNode.class).getSingleResult();
	}

	@Override
	public WfProcess getWfProcessByInstanceid(String instanceId) {
		String sql = "select distinct(t.wf_item_uid) from t_wf_process t where t.wf_instance_uid = '"+instanceId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		String itemId = "";
		if(list!=null && list.size()>0){
			itemId = list.get(0) ==null?"":list.get(0).toString();
		}
		WfProcess wfProcess = new WfProcess();
		wfProcess.setItemId(itemId);
		return wfProcess;
	}

	/**
	 * 更新t_wf_process表中未办结的节点期限以及最后期限
	 */
	@Override
	public void updateProcessDate() {
		String sql ="select t.wf_instance_uid, t.wf_node_uid, t.wf_item_uid, i.vc_wcsx," +
				" n.wfn_deadline, n.wfn_deadlineunit, to_char(t.apply_time, 'yyyy-MM-dd hh24:mi:ss') as appTime," +
				" t.wf_uid,t.step_index " +
				" from t_wf_process t , t_wf_core_item i, wf_node n " +
				" where t.is_over = 'NOT_OVER' and t.wf_item_uid = i.id and t.wf_uid = i.lcid " +
				" and t.wf_node_uid = n.wfn_id" +
				" group by t.wf_instance_uid, t.wf_node_uid, t.wf_item_uid, i.vc_wcsx," +
				" n.wfn_deadline, n.wfn_deadlineunit,t.wf_uid, to_char(t.apply_time, 'yyyy-MM-dd hh24:mi:ss'),t.step_index";
		List<Object[]> list =  getEm().createNativeQuery(sql).getResultList();
		for(Object[] data : list){
			String instanceId = data[0]==null?"":data[0].toString();
			String nodeId = data[1]==null?"":data[1].toString();
			String itemId = data[2]==null?"":data[2].toString();
			String wcsx =  data[3]==null?"":data[3].toString();
			String deadline = data[4]==null?"":data[4].toString();
			String deadlineunit = data[5]==null?"":data[5].toString();
			String apply_time = data[6]==null?"":data[6].toString();
			String lcid = data[7]==null?"":data[7].toString();
			int step_index = data[8]==null?0:Integer.parseInt(data[8].toString());
			
			//获取该instanceid的父finstanceid
			String pInstanceId = pendingDao.getPinstanceId(instanceId);
			WfProcess  wfProcess = null;
			if(pInstanceId!=null && !"".equals(pInstanceId)){
				wfProcess = pendingDao.getFirstStepWfProcess(pInstanceId, lcid);
			}
			if(wfProcess==null){
				wfProcess = pendingDao.getFirstStepWfProcess(instanceId, lcid);
			}
			if(wfProcess==null){
				continue;
			}
			Date apply_date = wfProcess.getApplyTime();
			//获取延期结果
			List<DelayResult> resultList = itemRelationDao.getDelayResultByInstanceId(instanceId);
			int nodeDalay = 0 ;
			int pendingDalay = 0;
			WfItemRelation wfItemRelation = itemRelationDao.getItemRelationByItemId(itemId);
			int delayDays = 0 ;
			if(wfItemRelation!=null){
				delayDays = wfItemRelation.getDelay_date();
			}
			for(DelayResult delayResult:resultList){
				int stepindex = delayResult.getStepindex();
				Integer is_allowed = delayResult.getIs_allowed();
				if(is_allowed!=null && stepindex==step_index && is_allowed==1){
					nodeDalay += delayDays;
				}
				if(is_allowed!=null && is_allowed==1){
					pendingDalay += delayDays;
				}
				
			}
			Date zhqx_date = pendingDao.getEndDate(Integer.parseInt(wcsx)+1+pendingDalay, apply_date);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
			Date nodeAppDate = convertStringToDate(apply_time); ;
			String  zhqx_time = format.format(zhqx_date);  // 2007-01-18   
			String updateSql = " update t_wf_process p set p.zhqxdate = to_date('"+zhqx_time+"', 'yyyy-MM-dd HH24:mi:ss')";
			if(deadlineunit!=null && deadlineunit.equals("0")){	//按照工作日计算节点期限
				if(deadline==null || deadline.equals("")){
					deadline = "0";
				}
				Date jdqxDate = pendingDao.getEndDate(Integer.parseInt(deadline)+1+nodeDalay, nodeAppDate);
				String jdqx_time = format.format(jdqxDate);
				updateSql +=", p.jdqxdate = to_date('"+jdqx_time+"', 'yyyy-MM-dd HH24:mi:ss')";
			}
			updateSql +=" where p.wf_instance_uid = '"+instanceId+"' and p.wf_node_uid = '"+nodeId+"'"
					  +" and p.wf_item_uid ='"+itemId+"'";
			getEm().createNativeQuery(updateSql).executeUpdate();
		}
	}

	
	@Override
	public List<WfProcess> getWfProcessByInstanceidAndStatus(String wfChildInstanceId) {
		String sql = "select * from t_wf_process t where t.wf_instance_uid = '"+wfChildInstanceId+"' and t.action_status=2";
		List<WfProcess> list = getEm().createNativeQuery(sql,WfProcess.class).getResultList();
		return list;
	}

	
	@Override
	public List<String> getWfProcessByWfChildInstanceId(String wfChildInstanceId) {
		String sql = "select t.wf_instance_uid from t_wf_process t where t.wf_f_instance_uid = '"+wfChildInstanceId + "' and t.is_back !='2' group by t.wf_instance_uid";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		return list;
	}

	@Override
	public void updateProcessDate(String instanceId, String nodeid,
			Integer stepindex, Integer days) {
		//获取node信息
		String sql = "select distinct to_char(p.jdqxdate,'yyyy-MM-dd HH:mm:ss'), " +
				"to_char(p.zhqxdate,'yyyy-MM-dd HH:mm:ss') from t_wf_process p where p.allinstanceid='"+instanceId+"' and " +
			//" p.step_index ="+stepindex+" and "+
			"p.wf_node_uid= '"+nodeid+"'";
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		int dalay = days==null?0:(int)days;	//延期申请的天数
		for(Object[] data: list){
			String jdqxdate = data[0].toString();
			String zhqxdate = data[1].toString();
			//推迟节点时间跟最后时间
			Date jdqx_date = pendingDao.getEndDate(dalay+1, convertStringToDate(jdqxdate));
			Date zhqx_date = pendingDao.getEndDate(dalay+1, convertStringToDate(zhqxdate));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String zhqxtime =  format.format(zhqx_date);
			String jdqxtime =  format.format(jdqx_date);
			String updateSql = "update t_wf_process p set p.jdqxdate = to_date('"+jdqxtime+"', 'yyyy-MM-dd HH24:mi:ss') " +
					", p.zhqxdate = to_date('"+zhqxtime+"', 'yyyy-MM-dd HH24:mi:ss') " +
					" where p.allinstanceid='"+instanceId+"' and p.wf_node_uid= '"+nodeid+"'";
			getEm().createNativeQuery(updateSql).executeUpdate();
		}
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
	
	public int convertStringToInt(Object obj){
		if(obj==null){
			return 0;
		}
		String num = obj.toString();
		if(num!=null && !num.equals("")){
			return Integer.parseInt(num);
		}else{
			return 0;
		}
		
	}

	@Override
	public void updateWfProcessStatus(String instanceId) {
		String sql = "update T_WF_PROCESS p set p.action_status = 2 where " +
				" p.is_over='NOT_OVER' and p.wf_instance_uid = '"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}

	@Override
	public List<WfProcess> findBoobyChildProcessList(String sql) {
		String hql = "select t.* from t_wf_process t where t.wf_instance_uid in ("+sql+") and " +
				"t.step_index =  (select max(p2.step_index)-1 as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid )";
		List<WfProcess> list = getEm().createNativeQuery(hql,WfProcess.class).getResultList();
		return list;
	}

	@Override
	public WfProcess getParentProcessByInstanceid(String instanceId) {
		String hql = "from WfProcess where wfInstanceUid='"+instanceId+"' and (isBack!='2' or isBack is null) order by stepIndex desc";
		Query query=super.getEm().createQuery(hql);
		List<WfProcess> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer getMaxStepIndex(String instanceId) {
		String sql = "select max(p.step_index) from T_WF_PROCESS p where p.WF_INSTANCE_UID='"+instanceId+"'" ;
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		int stepIndex = 0;
		if(list!=null && list.size()>0){
			stepIndex = Integer.parseInt(list.get(0)==null?"0":list.get(0).toString());
		}
		return stepIndex;
	}

	@Override
	public List<WfProcess> getWfProcessList(String instanceId){
		String sql = "select t.* from T_WF_PROCESS t where t.step_index is not null and t.WF_INSTANCE_UID " +
				"in (select p.WF_INSTANCE_UID from  T_WF_PROCESS p where p.WF_F_INSTANCE_UID ='"+instanceId+"')";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}
	
	
	@Override
	public List<WfProcess> getWfProcessList(HashMap<String, String> map){
		String instanceId = map.get("instanceId");
		String fInstanceId = map.get("fInstanceId");
		String sql = "";
		if (CommonUtil.stringNotNULL(fInstanceId)){
			sql = "select t.process_title,(select e.employee_name from zwkj_employee e where e.employee_guid = t.user_uid),t.wf_process_uid,t.wf_instance_uid,t.wf_item_uid,t.wf_form_id" +
					",t.ischildwf,(select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (t.wf_instance_uid = d.instanceid  or t.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid=t.user_uid) as favourite " +
					" from t_wf_process t  where (t.wf_instance_uid = '" + fInstanceId + "' or t.wf_f_instance_uid = '"
					+ fInstanceId + "') and t.wf_instance_uid != '" + instanceId + "'  and t.is_show = 1 and (t.is_back is null or t.is_back != '2') and (t.IS_DUPLICATE != 'true' or t.IS_DUPLICATE is null) " +
//					"and (t.finsh_time in (select max(t1.finsh_time) from t_wf_process t1 where t1.wf_instance_uid = t.wf_instance_uid and t1.is_show = 1 and (t1.is_back is null or t1.is_back != '2')) or t.finsh_time is null)"+
					"  and t.step_index in (select max(t2.step_index) from t_wf_process t2 where t2.wf_instance_uid = t.wf_instance_uid and t2.is_show = 1 and (t2.is_back is null or t2.is_back != '2'))";
		}else{
			sql = "select t.process_title,(select e.employee_name from zwkj_employee e where e.employee_guid = t.user_uid),t.wf_process_uid,t.wf_instance_uid,t.wf_item_uid,t.wf_form_id" +
					",t.ischildwf,(select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (t.wf_instance_uid = d.instanceid  or t.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid=t.user_uid) as favourite " +
					" from t_wf_process t  where (t.wf_instance_uid = '" + instanceId + "' or t.wf_f_instance_uid = '"
					+ instanceId + "') and t.wf_instance_uid != '" + instanceId + "'  and t.is_show = 1 and (t.is_back is null or t.is_back != '2') and (t.IS_DUPLICATE != 'true' or t.IS_DUPLICATE is null) " +
//					"and (t.finsh_time in  (select max(t1.finsh_time) from t_wf_process t1 where t1.wf_instance_uid = t.wf_instance_uid and t1.is_show = 1 and (t1.is_back is null or t1.is_back != '2')) or t.finsh_time is null) "+
					" and t.step_index in (select max(t2.step_index) from t_wf_process t2 where t2.wf_instance_uid = t.wf_instance_uid and t2.is_show = 1 and (t2.is_back is null or t2.is_back != '2'))";
		}
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		List<WfProcess> wfList = new ArrayList<WfProcess>();
		for (Object[] o:list){
			WfProcess wf = new WfProcess();
			wf.setProcessTitle(o[0]==null?"":o[0].toString());
			wf.setUserName(o[1]==null?"":o[1].toString());
			wf.setWfProcessUid(o[2]==null?"":o[2].toString());
			wf.setWfInstanceUid(o[3]==null?"":o[3].toString());
			wf.setItemId(o[4]==null?"":o[4].toString());
			wf.setFormId(o[5]==null?"":o[5].toString());
			wf.setIsChildWf(o[6]==null?"":o[6].toString());
			wf.setFavourite(o[7]==null?"":o[7].toString());
			wfList.add(wf);
		}
		return wfList;
	}

	@Override
	public GetProcess findGetProcessByPInstanceID(String pinstanceId, String stepIndex) {
		String sql2 ="select p.WF_INSTANCE_UID from T_WF_PROCESS p " +
				"where  p.WF_F_INSTANCE_UID='"+pinstanceId+"' and p.pstepindex ="+stepIndex
				+" group by p.WF_INSTANCE_UID";
		List<String> list = getEm().createNativeQuery(sql2).getResultList();
		GetProcess getProcess = null;
		if(list!=null && list.size()>0){
			String instanceid_ = list.get(0).toString();
			List<GetProcess> processlist = findProcessList(instanceid_);
			if(list!=null && list.size()>0){
				getProcess = processlist.get(0);
			}
		}
		return getProcess;
	}
	
	@Override
	public List<GetProcess>  findProcess(String pinstanceId) {
		//获取子流程的实例id
		String sql = "select p.WF_INSTANCE_UID from T_WF_PROCESS p " +
				"where p.WF_F_INSTANCE_UID ='"+pinstanceId+"' group by p.WF_INSTANCE_UID";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		List<GetProcess> returnList = new ArrayList<GetProcess>();
		for(int i=0; list!=null && i<list.size(); i++){
			String instanceid_ = list.get(i).toString();
			List<GetProcess> processlist = findProcessList(instanceid_);
			returnList.addAll(processlist);
		}
		return returnList;
	}

	
	public List<String> findInstanceIdsByFinstanceId(String finstanceId) {
		String sql = "select distinct t.wf_instance_uid from t_wf_process t where t.wf_f_instance_uid = '"+finstanceId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	
	@Override
	public WfNode getWfNodeById(String id) {
		WfNode node = getEm().find(WfNode.class, id);
		if(node==null){
			//到histroy中查询
			String sql = " from WfHistoryNode t where t.wfn_id='"+id+"'";
			List<WfHistoryNode> list = getEm().createQuery(sql).getResultList();
			WfHistoryNode historyNode = null;
			if(list!=null && list.size()>0){
				historyNode  = (WfHistoryNode)list.get(0);
			}
			if(historyNode!=null){
				node= new WfNode();
				node.setWfn_id(historyNode.getWfn_id());
				node.setWfn_name(historyNode.getWfn_name());
				node.setWfn_createtime(historyNode.getWfn_createtime());
				node.setWfn_modifytime(historyNode.getWfn_modifytime());
				node.setWfn_deadline(historyNode.getWfn_deadline());
				node.setWfn_deadlineunit(historyNode.getWfn_deadlineunit());
				node.setWfn_inittask(historyNode.getWfn_inittask());
				node.setWfn_defaultcalendar(historyNode.getWfn_defaultcalendar());
				node.setWfn_defaultform(historyNode.getWfn_defaultform());
				node.setWfn_defaulttemplate(historyNode.getWfn_defaulttemplate());
				node.setWfn_global_process_custom(historyNode.getWfn_global_process_custom());
				node.setWfn_current_process_custom(historyNode.getWfn_current_process_custom());
				node.setWfn_staff(historyNode.getWfn_staff());
				node.setWfn_allow_cc(historyNode.getWfn_allow_cc());
				node.setWfn_allow_consultation(historyNode.getWfn_allow_consultation());
				node.setWfn_allow_delegation(historyNode.getWfn_allow_delegation());
				//node.setWfn
				node.setWfn_type(historyNode.getWfn_type());
				node.setWfn_moduleid(historyNode.getWfn_moduleid());
				node.setWfn_deadline_isworkday(historyNode.getWfn_deadline_isworkday());
				node.setWfn_procedure_name(historyNode.getWfn_procedure_name());
				node.setWfn_bd_user(historyNode.getWfn_bd_user());
				node.setWfn_route_type(historyNode.getWfn_route_type());
				node.setAction_status(historyNode.getAction_status());
				node.setIsExchange(historyNode.getIsExchange());
				node.setWfl_child_merge(historyNode.getWfl_child_merge());
				node.setWfn_self_loop(historyNode.getWfn_self_loop());
				node.setWfn_form_continue(historyNode.getWfn_form_continue());
				node.setWfn_iszf(historyNode.getWfn_iszf());
				node.setWfn_iswcsx(historyNode.getWfn_iswcsx());
				node.setWfn_txnr(historyNode.getWfn_txnr());
				node.setWfn_txnrid(historyNode.getWfn_txnrid());
				node.setWfn_tqtxsjline(historyNode.getWfn_tqtxsjline());
			}else{
				return null;
			}
		}
		return node;
	}

	@Override
	public WfProcess getWfProcessByColoum(String finStanceId, String userid) {
		String sql = "select t.* from T_WF_PROCESS t where t.USER_UID ='"+userid+"'" +
				" and t.WF_F_INSTANCE_UID='"+finStanceId+"' and t.IS_OVER='NOT_OVER'";
		List<WfProcess> list = getEm().createNativeQuery(sql,WfProcess.class).getResultList();
		WfProcess wfProcess = null;
		if(list!=null && list.size()>0){
			wfProcess = list.get(0);
		}
		return wfProcess;
	}

	@Override
	public void addDoFileReceive(DoFileReceive doFileReceive) {
		getEm().persist(doFileReceive);
	}

	@Override
	public List<DoFileReceive> getDoFileReceiveList(String userId,
			Integer pageIndex, Integer pageSize, Integer status,Map<String,String> map){
		String wfTitle = map.get("wfTitle");
		String itemName = map.get("itemName");
		String todepartId = map.get("departId");
		String lwh = map.get("lwh");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String lwdw = map.get("lwdw");
		String state = map.get("state");
		//1,表示已收
		String sql = "";
		List<DoFileReceive> returnList = new ArrayList<DoFileReceive>();
		if(todepartId.indexOf("','") == -1){
			if(status == 1){
				sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
						" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
						",to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate, t.dyfs, t.ydyfs " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+"))" +
						" and t.status != 0 and  p.is_back != '2' " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null  ";
			}else if(status == 3){
				sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
						" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
						",to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate, t.dyfs, t.ydyfs " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+"))" +
						" and  p.is_back != '2' " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2)) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null  ";
			}else{
				sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME, " +
						"to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
						" i.vc_sxmc,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback " +
						",to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate, t.dyfs, t.ydyfs " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") )" +
						" and t.status ="+status +" and  p.is_back != '2' " +
						" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null ";
			}
			if(wfTitle!=null && !wfTitle.equals("")){
				sql +=" and p.process_title like '%"+wfTitle+"%'";
			}
			
			if(itemName!=null && !itemName.equals("")){
				sql +=" and i.vc_sxmc like '%"+itemName+"%'";
			}
			if(lwh!=null && !lwh.trim().equals("")){
				sql +=" and s.lwh like '%"+lwh.trim()+"%'";
			}
			if (startTime!=null && !"".equals(startTime)) {
				sql += " and s.fwsj>= to_date('" + startTime+ "','yyyy-mm-dd')";
			}
			
			if(endTime!=null && !endTime.equals("")){
				sql += " and s.fwsj<= to_date('" + endTime+ "','yyyy-mm-dd')";
			}
			
			if (lwdw != null && !"".equals(lwdw)) {
				sql += " and s.lwdw like '%" + lwdw+ "%'";
			}
			if(StringUtils.isNotBlank(state)){
				if(state.equals("1")){
					sql += " and t.status='1' and t.jrdb is null ";
				}else if(state.equals("1")){
					sql += " and t.status='1' and t.jrdb = 1 ";
				}else if(state.equals("2")){
					
				}else if(state.equals("3")){
					sql += " and t.status='4' ";
				}
			}
			
			if(status!=null && status==1){
				sql +=" order by t.recdate desc";
			}else{
				sql +=" order by t.status, t.applydate desc";
			}
				
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List<Object[]> list =  query.getResultList();
			DoFileReceive doFileReceive = null;
			for(int i=0; list!=null && i<list.size(); i++){
				Object[] data =(Object[]) list.get(i);
				doFileReceive = new DoFileReceive();
				String employeeName = data[0].toString();
				doFileReceive.setEmployeeName(employeeName);
				doFileReceive.setApplyDate(convertStringToDate(data[1]));
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
				doFileReceive.setFwsj(convertStringToDate(data[13]));
				doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
				doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
				doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
				doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
				doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
				doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
				doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
				doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
				doFileReceive.setRecDate(convertStringToDate(data[22]));
				doFileReceive.setDyfs(convertStringToInt(data[23]));
				doFileReceive.setYdyfs(convertStringToInt(data[24]));
				returnList.add(doFileReceive);
			}
		}else{
			// 多个部门
			if(status == 1){
				sql = "select distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'),t.pinstanceid , t.isreback,  t.recdate,t.id , t.status from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID ) OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null ";
				
			}else if(status == 3){
				sql = "select distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'),t.pinstanceid , t.applydate,  t.todepartid,t.id, t.status from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID ) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null ";
				
			}else{
				sql = "select distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'),t.pinstanceid, t.applydate ,t.todepartid,t.id, t.status from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null  ";
				
			}
			if(wfTitle!=null && !wfTitle.equals("")){
				sql +=" and p.process_title like '%"+wfTitle+"%'";
			}
			if(itemName!=null && !itemName.equals("")){
				sql +=" and i.VC_SXMC like '%"+itemName+"%'";
			}
			if(lwh!=null && !lwh.equals("")){
				sql +=" and s.lwh like '%"+lwh.trim()+"%'";
			}
			if (startTime!=null && !"".equals(startTime)) {
				sql += " and s.fwsj>= to_date('" + startTime+ "','yyyy-mm-dd')";
			}
			
			if(endTime!=null && !endTime.equals("")){
				sql += " and s.fwsj<= to_date('" + endTime+ "','yyyy-mm-dd')";
			}
			
			if (lwdw != null && !"".equals(lwdw)) {
				sql += " and s.lwdw like '%" + lwdw+ "%'";
			}
			if(StringUtils.isNotBlank(state)){
				if(state.equals("1")){
					sql += " and t.status='1' and t.jrdb is null ";
				}else if(state.equals("2")){
					sql += " and t.status='1' and t.jrdb = 1 ";
				}else if(state.equals("3")){
					sql += " and t.status='4' ";
				}
			}
			if(status!=null && status==1){
				sql +=" order by t.recdate desc";
			}else{
				sql +=" order by t.status,t.applydate desc";
			}
			
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List<Object[]> list =  query.getResultList();
			// 循环list 查询数据
			for(int i=0; list!=null && i<list.size(); i++){
				if(list!=null  && list.size() > 0){
					Object[] data =(Object[]) list.get(i);
					if(status == 1){
						sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
								" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback" +
								",to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate, t.dyfs, t.ydyfs " +
								" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
								" where t.id = '"+data[4].toString()+"' and t.pinstanceid = '"+data[1].toString()+"' and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"' and t.status != 0 and ( p.is_back != '2' or p.is_back is null)" +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null ";
					}else if(status == 3){
						sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
								" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback" +
								",to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate, t.dyfs, t.ydyfs ,t.status" +
								" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
								" where t.id = '"+data[4].toString()+"' and t.pinstanceid = '"+data[1].toString()+"' and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"' and ( p.is_back != '2' or p.is_back is null)" +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2)) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null ";
					}else{
						sql =  "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME,to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss'), p.process_title," +
								" i.VC_SXMC,t.PROCESSID,t.id,p.wf_item_uid, p.wf_form_id,t.receive_type,s.lwbt,s.lwdw,s.yfdw,s.lwh,to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss'),s.gwlx, t.pdfpath,t.pinstanceid, t.isinvalid, t.jrdb, t.instanceid , t.status ,t.isreback," +
								" '' as recdate, t.dyfs, t.ydyfs " +
								" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
								" where t.id = '"+data[4].toString()+"' and t.pinstanceid = '"+data[1].toString()+"' and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"' and t.status ="+status +" and (p.is_back != '2' or p.is_back is null) and  t.todepartid = '" + data[3].toString() +"'"+
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2  ) and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null  ";
					}
					if(wfTitle!=null && !wfTitle.equals("")){
						sql +=" and p.process_title like '%"+wfTitle+"%'";
					}
					if(itemName!=null && !itemName.equals("")){
						sql +=" and i.VC_SXMC like '%"+itemName+"%'";
					}
					if(lwh!=null && !lwh.equals("")){
						sql +=" and s.lwh like '%"+lwh.trim()+"%'";
					}
					if (startTime!=null && !"".equals(startTime)) {
						sql += " and s.fwsj>= to_date('" + startTime+ "','yyyy-mm-dd')";
					}
					
					if(endTime!=null && !endTime.equals("")){
						sql += " and s.fwsj<= to_date('" + endTime+ "','yyyy-mm-dd')";
					}
					
					if (lwdw != null && !"".equals(lwdw)) {
						sql += " and s.lwdw like '%" + lwdw+ "%'";
					}
					
					if(StringUtils.isNotBlank(state)){
						if(state.equals("1")){
							sql += " and t.status='1' and t.jrdb is null ";
						}else if(state.equals("2")){
							sql += " and t.status='1' and t.jrdb = 1 ";
						}else if(state.equals("3")){
							sql += " and t.status='4' ";
						}
					}
					
					if(status!=null && status==1){
						sql +=" order by t.recdate desc";
					}else{
						sql +=" order by t.status,t.applydate desc";
					}
					query = getEm().createNativeQuery(sql);
					List<Object[]> list2 =  query.getResultList();
					DoFileReceive doFileReceive = null;
					if(list2 != null&& list2.size()>0){
						data =(Object[]) list2.get(0);
						doFileReceive = new DoFileReceive();
						String employeeName = data[0].toString();
						doFileReceive.setEmployeeName(employeeName);
						doFileReceive.setApplyDate(convertStringToDate(data[1]));
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
						doFileReceive.setFwsj(convertStringToDate(data[13]));
						doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
						doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
						doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
						doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
						doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
						doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
						doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
						doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
						doFileReceive.setRecDate(convertStringToDate(data[22]));
						doFileReceive.setDyfs(convertStringToInt(data[23]));
						doFileReceive.setYdyfs(convertStringToInt(data[24]));
						returnList.add(doFileReceive);
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public int getDoFileReceiveCount(String userId, Integer status, Map<String,String> map) {
		String wfTitle = map.get("wfTitle");
		String itemName = map.get("itemName");
		String todepartId = map.get("departId");
		String lwh = map.get("lwh");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String lwdw = map.get("lwdw");
		String state = map.get("state");
		String sql = "";
		// 一个部门
		if(todepartId.indexOf("','") == -1){
			if(status == 1){
				sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")) and t.status != 0 " +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and p.step_index != 1 ";
				
			}else if(status == 3){
				sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")) " +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid ";
				
			}else{
				sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null and t.takeback is null ";
				
			}
		}else{
			// 多个部门
			if(status == 1){
				sql = "select count(*) from (select distinct t.applydate,t.pinstanceid , t.isreback,t.recdate from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type = 2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid ";
				
			}else if(status == 3){
				sql = "select count(*) from (select distinct t.applydate,t.pinstanceid , t.isreback,t.recdate from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") ) " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type = 2)) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid ";
				
			}else{
				sql = "select count(*) from (select distinct t.applydate,t.pinstanceid ,t.isreback,t.recdate  from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")) and t.status = "+status
						+" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null and t.takeback is null ";
				
			}
		}
		
		if(wfTitle!=null && !wfTitle.equals("")){
			sql +=" and p.process_title like '%"+wfTitle+"%'";
		}
		if(itemName!=null && !itemName.equals("")){
			sql +=" and i.VC_SXMC like '%"+itemName+"%'";
		}
		if(lwh!=null && !lwh.equals("")){
			sql +=" and s.lwh like '%"+lwh.trim()+"%'";
		}
		if (startTime!=null && !"".equals(startTime)) {
			sql += " and s.fwsj>= to_date('" + startTime+ "','yyyy-mm-dd')";
		}
		
		if(endTime!=null && !endTime.equals("")){
			sql += " and s.fwsj<= to_date('" + endTime+ "','yyyy-mm-dd')";
		}
		
		if(StringUtils.isNotBlank(state)){
			if(state.equals("1")){
				sql += " and t.status='1' and t.jrdb is null ";
			}else if(state.equals("2")){
				sql += " and t.status='1' and t.jrdb = 1 ";
			}else if(state.equals("3")){
				sql += " and t.status='4' ";
			}
		}
		if (lwdw != null && !"".equals(lwdw)) {
			sql += " and s.lwdw like '%" + lwdw+ "%'";
		}
		if(todepartId.indexOf("','") > -1){
			sql += ")";
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public DoFileReceive getDoFileReceiveById(String id) {
		return getEm().find(DoFileReceive.class, id);
	}

	@Override
	public void updateDoFileReceive(DoFileReceive doFileReceive) {
		getEm().merge(doFileReceive);
	}

	@Override
	public void updateProcess(WfProcess wfProcess) {
		getEm().merge(wfProcess);
	}

	@Override
	public List<WfProcess> findWfProcessByInstanceIdAndIsChild(String wf_instance_uid) {
		String sql = "select t.* from T_WF_PROCESS t where t.wf_instance_uid='"+wf_instance_uid+"' and t.ischildwf is not null and t.ismanyinstance is not null order by t.step_index desc";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	@Override
	public Map<String,Integer> findWfProcessByInstanceIdAndIsChilds(String wf_instance_uids) {
		String sql = "select count(wf_instance_uid),wf_instance_uid from T_WF_PROCESS t where t.wf_instance_uid in ("+wf_instance_uids+") and t.ischildwf is not null and t.ismanyinstance is not null group by t.wf_instance_uid";
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		Map<String,Integer> inMap=new HashMap<String,Integer>();
		for(int i=0,size=list.size();i<size-1;i++){
			Object[] object=(Object[]) list.get(i);
			inMap.put(object[1]==null?"":object[1].toString(),Integer.valueOf(object[0].toString()));
		}
		return inMap;
	}

	@Override
	public void updateIsyyById(String id) {
		String sql = "update t_wf_process set isyy=1  where WF_PROCESS_UID='"+id+"'";
		this.getEm().createNativeQuery(sql).executeUpdate();
	}

	
	@Override
	public List<GetProcess> getChildWfProcessList(String instanceId,
			String beginTime, String endTime) {
		StringBuffer querySql = (new StringBuffer("select np.*,row_number() over (partition by np.wfInstanceUid order by np.stepIndex)  from (select "))
		 .append("twp.wf_process_uid as wfProcessUid,")
		 .append("twp.wf_instance_uid as wfInstanceUid,")
		 .append("twp.wf_uid as wf_uid,")
		 .append("twp.step_index as stepIndex,")
		 .append("twp.is_end as isEnd,")
		 .append("twp.wf_form_id as formId,")
		 .append("(select t.wfn_name from wf_node t where t.wfn_id = twp.wf_node_uid) as nodeName,")
		 .append("twp.wf_node_uid as nodeId,")
		 .append("twp.user_uid as userId,")
		 .append("(select t.employee_name from zwkj_employee t where t.employee_guid = twp.fromuserid) as fromUserName,")
		 .append("(select t.employee_name from zwkj_employee t where t.employee_guid = twp.user_uid) as userName,")
		 .append("twp.apply_time as applyTime,")
		 .append("twp.finsh_time as finshTime, ")
		 .append("twp.IS_BACK as is_back, ")
		 .append("(select decode(count(*),0,0,1) from T_WF_PROCESS p where p.wf_f_instance_uid=twp.wf_instance_uid and p.fromnodeid=twp.tonodeid and p.ismanyinstance='1') as isHaveChild, ")
		 .append("twp.fromnodeid as fromNodeId, ")
		 .append("twp.tonodeid as toNodeId, ")
		 .append("twp.push_childprocess as push_childprocess, ")
		 .append("twp.is_merge as is_merge, ")
		 .append("twp.dotype as doType, ")
		 .append("twp.wf_f_instance_uid as f_instanceId, ")
		 .append("twp.jssj as jssj, ")
		 .append("twp.isRedirect as isRedirect, ")
		 .append("twp.pdfPath as pdfPath, ")
		 .append("twp.wf_uid as workflowId, ")
		 .append("twp.is_over as isOver,")
		 .append("twp.fjbProcessId as fjbProcessId, ")
		 .append("twp.isreturnstep as isreturnstep ")
		 .append(" from t_wf_process twp where twp.wf_f_instance_uid='")
		 .append(instanceId).append("'  and (twp.IS_DUPLICATE!='true' or twp.IS_DUPLICATE is null) ")
		 .append(" and twp.apply_time >=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')");
		if(endTime!=null&&!"".equals(endTime)){
			querySql.append(" and twp.apply_time <=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')");
		}
		querySql.append(" ) np ");
       
		return getEm().createNativeQuery(querySql.toString(), "GetProcessResults").getResultList();
	}

	@Override
	public void savePushMessage(PushMessage pm) {
		getEm().persist(pm);
	}

	
	@Override
	public List<PushMessage> getPushMessageList(String employeeGuid,String userId,String instanceId) {
		String sql = "select t.* from t_wf_core_pushmessage t where t.pushed_empid='"+employeeGuid+"' and t.push_empid = '"+userId+"'and t.instanceid='"+instanceId+"'";
		return getEm().createNativeQuery(sql,PushMessage.class).getResultList();
	}

	@Override
	public String getworkFlowIdByInstanceId(String instanceId) {
		
		String sql = "select distinct t.WF_UID from t_wf_process t" +
				" where t.WF_INSTANCE_UID ='"+instanceId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		String workFlowId = "";
		if(list!=null && list.size()>0){
			workFlowId = list.get(0).toString();
		}
		return workFlowId;
	}
	
	
	@Override
	public String getFinstanceIdByInstanceId(String instanceId) {
		
		String sql = "select distinct t.wf_f_instance_uid from t_wf_process t" +
				" where t.WF_INSTANCE_UID ='"+instanceId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		String fInstanceId = "";
		if(list!=null && list.size()>0 && null != list.get(0)){
			fInstanceId = list.get(0).toString();
		}
		return fInstanceId;
	}


	@Override
	public String getPinstanceId(String instanceId) {
		String sql = "select distinct WF_F_INSTANCE_UID from t_wf_process t where t.WF_INSTANCE_UID ='"+instanceId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		String pInstanceId = "";
		if(list!=null && list.size()>0){
			pInstanceId = list.get(0).toString();
		}
		return pInstanceId;
	}

	
	@Override
	public DoFileReceive getDoFileReceive(String instanceId) {
		String sql = "select t.* from t_wf_core_receive t where t.instanceid ='"+instanceId+"'";
		List<DoFileReceive> dfrList =  getEm().createNativeQuery(sql,DoFileReceive.class).getResultList();
		if(dfrList.size() > 0){
			return dfrList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void addSw(Sw sw) {
		getEm().persist(sw);
	}

	@Override
	public void addDoFile(DoFile doFile) {
		getEm().persist(doFile);
	}

	@Override
	public void addWSPBD(Map<String, String> map) {
		String sql = "insert into T_WF_OFFICE_WSPBD(INSTANCEID,FORMID,WORKFLOWID,PROCESSID";
		if(map.get("SWH")!=null&&!"".equals(map.get("SWH"))){
			sql +=",SWH";
		}
		if(map.get("LWDW")!=null&&!"".equals(map.get("LWDW"))){
			sql +=",LWDW";
		}
		if(map.get("SWSJ")!=null&&!"".equals(map.get("SWSJ"))){
			sql +=",SWSJ";
		}
		if(map.get("WH")!=null&&!"".equals(map.get("WH"))){
			sql +=",WH";
		}
		if(map.get("CWSJ")!=null&&!"".equals(map.get("CWSJ"))){
			sql +=",CWSJ";
		}
		if(map.get("TITLE")!=null&&!"".equals(map.get("TITLE"))){
			sql +=",TITLE";
		}
		if(map.get("JJCD")!=null&&!"".equals(map.get("JJCD"))){
			sql +=",JJCD";
		}
		sql +=") values ('"+map.get("INSTANCEID")+"','"+map.get("FORMID")+"','"+map.get("WORKFLOWID")+"','"+map.get("PROCESSID")+"'";
		if(map.get("SWH")!=null&&!"".equals(map.get("SWH"))){
			sql +=",'"+map.get("SWH")+"'";
		}
		if(map.get("LWDW")!=null&&!"".equals(map.get("LWDW"))){
			sql +=",'"+map.get("LWDW")+"'";
		}
		if(map.get("SWSJ")!=null&&!"".equals(map.get("SWSJ"))){
			sql +=",'"+map.get("SWSJ")+"'";
		}
		if(map.get("WH")!=null&&!"".equals(map.get("WH"))){
			sql +=",'"+map.get("WH")+"'";
		}
		if(map.get("CWSJ")!=null&&!"".equals(map.get("CWSJ"))){
			sql +=",'"+map.get("CWSJ")+"'";
		}
		if(map.get("TITLE")!=null&&!"".equals(map.get("TITLE"))){
			sql +=",'"+map.get("TITLE")+"'";
		}
		if(map.get("JJCD")!=null&&!"".equals(map.get("JJCD"))){
			sql +=",'"+map.get("JJCD")+"'";
		}
		sql +=")";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public List<Sw> getSwdrlbList(int pageIndex, int pageSize) {
		String sql = "select t.* from t_wf_core_sw t order by t.fwsj desc nulls last ";
		Query query=super.getEm().createNativeQuery(sql,Sw.class);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public int getCountSwdrlbList() {
		String hql = "select count(id) from Sw   ";
		return Integer.parseInt(getEm().createQuery(hql).getSingleResult().toString());
	}
	


	@Override
	public List<WfTableInfo> getOfficeTableList(Map<String, String> map) {
		String vc_name = map.get("vc_name");
		String vc_tablename = map.get("vc_tablename");
		String hql = "from WfTableInfo t where 1=1 ";
		if(CommonUtil.stringNotNULL(vc_name)){
			hql += " and t.vc_name like '%"+vc_name+"%'";
		}
		if(CommonUtil.stringNotNULL(vc_tablename)){
			hql += " and t.vc_tablename like '%"+vc_tablename+"%'";
		}
		return getEm().createQuery(hql).getResultList();
	}
	
	@Override
	public String getNodeNameById(String nodeId) {
		String sql = "select t.wfn_name from wf_node t where t.wfn_id='"+nodeId+"'";
		Query query=super.getEm().createNativeQuery(sql);
		List<Object> list =  query.getResultList();
		if(list!=null&&list.size()!=0){
			return (String)list.get(0);
		}
		return null;
	}

	@Override
	public void clearTableData(String[] tableNames) {
		for(String tableName: tableNames){
			String sql = "truncate table "+tableName;
			getEm().createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public void removeOfficeTable(String[] tableNames){
		for(String tableName: tableNames){
			//删除t_wf_core_table表中相应数据
			String sql = "delete from t_wf_core_table t where t.vc_tablename = '"+tableName+"'";
			getEm().createNativeQuery(sql).executeUpdate();
			String sql2 = "drop table "+tableName;
			getEm().createNativeQuery(sql2).executeUpdate();
		}
	}

	@Override
	public List<Object[]> getAllTableList() {
		String sql = "select   table_name, comments   from   user_tab_comments order by table_name asc ";
		return  getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public void updateDoFileIsDelete(String id,String isdelete) {
		String sql = "update t_wf_core_dofile t set t.isdelete="+isdelete+" where t.id='"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public int getCountHszDoFiles(String bigDepId, String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_wf_core_dofile t,T_WF_CORE_ITEM i where  t.isdelete=1 and  t.item_id=i.id ");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "'");
		}
		if ( CommonUtil.stringNotNULL(conditionSql)){
			sb.append(conditionSql);
		}
		sb.append("order by t.dotime desc");
		return Integer.parseInt(getEm().createNativeQuery(sb.toString()).getSingleResult().toString());
	}

	@Override
	public List<DoFile> getHszDoFileList(String bigDepId, String conditionSql,
			Integer pageindex, Integer pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select t.* from t_wf_core_dofile t,T_WF_CORE_ITEM i where   t.isdelete=1 and t.item_id=i.id ");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		sb.append(conditionSql).append(" order by t.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}

	@Override
	public DoFile getDoFileById(String id) {
		String querySql = "from DoFile where doFile_id='"+id+"'";
		List<DoFile> list = this.getEm().createQuery(querySql).getResultList();
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<WfProcess> getProcessListByPinstanceId(String pInstanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_f_instance_uid='"+pInstanceId+"'";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	@Override
	public List<WfProcess> getProcessListByInstanceId(String instanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"'";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}

	@Override
	public void deleteWfProcess(WfProcess wfProcess) {
		String hql = "delete from WfProcess t where t.wfProcessUid = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", wfProcess.getWfProcessUid());
		query.executeUpdate();
	
	}

	@Override
	public void deleteWfProcess(String doFile_id) {
		String hql = "delete from DoFile t where t.doFile_id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id",doFile_id);
		query.executeUpdate();
	}

	@Override
	public List<PushMessage> getPushMessageList(String employeeGuid,
			String insertid) {
		String sql = "select t.* from t_wf_core_pushmessage t where t.pushed_empid='"+employeeGuid+"' and t.instanceid='"+insertid+"'";
		return getEm().createNativeQuery(sql,PushMessage.class).getResultList();
	}

	
	
	@Override
	public List<Object[]> getTableColoumValue(String tableName, String coloum,
			String InstanceId) {
		String sql = "select "+coloum+ " from "+tableName+ " where instanceid = '"+InstanceId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<WfProcess> getWfProcessCountList(String insertid, int i) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid = '"+insertid+"' " +
				"	and t.dotype='"+i+"'";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	
	public List<String> getAttachmentTagByForm(String formId) {
		String hql = "select distinct t.formtagname from t_wf_core_form_map_column t where formid = '"+formId+"' and  t.formtagtype='attachment'";
		List<String> list = getEm().createNativeQuery(hql).getResultList();
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public WfProcess getMasterProcess(WfProcess oldProcess) {
		if(oldProcess==null){
			return null;
		}
		String hql = " from WfProcess t where (t.isRedirect is null or isRedirect = 0) and t.isMaster=1 and (t.isBack!='2' or t.isBack is null) and " +
				"t.wfInstanceUid = '"+oldProcess.getWfInstanceUid()+"'" +
				" and t.nodeUid='"+oldProcess.getNodeUid()+"' " +
				" and t.stepIndex = "+oldProcess.getStepIndex();
		return (WfProcess)getEm().createQuery(hql).getSingleResult();
	}
	
	@Override
	public List<WfProcess> getNextProcess(WfProcess oldProcess) {
		int stepindex = oldProcess.getStepIndex() +1;
		String hql = " from WfProcess t where  (t.isBack!='2' or t.isBack is null) and " +
				"t.wfInstanceUid = '"+oldProcess.getWfInstanceUid()+"' and t.stepIndex = " +stepindex ;
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public String getMaxProcessIdByAllInstanceId(String allInstanceId) {
		String sql = " from WfProcess t where t.fInstancdUid='"+allInstanceId+"' and t.stepIndex !=1 ";
		List<WfProcess> list = getEm().createQuery(sql).getResultList();
		String pdfPath = "";
		if(list!=null && list.size()>0){
			pdfPath = list.get(0).getPdfPath();
		}
		//删除stepIndex不等于1的
		String deletesql = "delete from t_wf_process t where t.WF_F_INSTANCE_UID='"+allInstanceId+"' and t.step_index !=1";
		getEm().createNativeQuery(deletesql).executeUpdate();
		String hql = " from WfProcess t where t.fInstancdUid='"+allInstanceId+"' and t.stepIndex =1 order by t.applyTime desc";
		String processId = "";
		List<WfProcess> pList = getEm().createQuery(hql).getResultList();
		WfProcess wfProcess =  null;
		if(pList!=null && pList.size()>0){
			wfProcess = pList.get(0);
		}
		if(wfProcess!=null){
			processId = wfProcess.getWfProcessUid();
		}
		
		String sql2 = " update t_wf_process t set t.IS_OVER= 'NOT_OVER' , t.IS_SHOW ='1' , t.DOTYPE = '1'" ;
		if(pdfPath!=null && !pdfPath.equals("")){
			sql2 += ", pdfpath = '"+pdfPath+"' ";
		}
			sql2+=" where t.WF_PROCESS_UID='"+processId+"'";
		getEm().createNativeQuery(sql2).executeUpdate();
		
		return processId;
	}

	
	@Override
	public Sw getSwByInstanceId(String instanceId) {
		String sql = "select t.* from t_wf_core_sw t where t.instanceid ='"+instanceId+"' ";
		List<Sw> swList =  getEm().createNativeQuery(sql,Sw.class).getResultList();
		Sw sw = null;
		if(swList.size() > 0){
			sw = swList.get(0);
		}
		return sw;
	}

	@Override
	public void updateSw(Sw sw) {
		getEm().merge(sw);
	}

	@Override
	public List<WfProcess> getCSWfProcessByInstanceid(String wfInstanceId, int stepIndex) {
		String hql = " from WfProcess t where  t.isOver='NOT_OVER' and " +
				"t.wfInstanceUid = '"+wfInstanceId+"' and t.stepIndex = " +stepIndex ;
		return getEm().createQuery(hql).getResultList();
	}

	
	@Override
	public WfChild getwfChildByCId(String workflowId) {
		String sql = "select t.* from wf_child t where t.wfc_cid='"+workflowId+"'";
		List<WfChild> wcList =  getEm().createNativeQuery(sql,WfChild.class).getResultList();
		WfChild wfChild = null;
		if(wcList.size() > 0){
			wfChild = wcList.get(0);
		}
		return wfChild;
	}

	@Override
	public int getStepIndexByInstanceId(String instanceId) {
		String sql ="select count(*) from T_WF_PROCESS t where t.WF_INSTANCE_UID='"+instanceId+"'";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	

	@Override
	public List<Object[]> getDofileReceiveList(Map<String, String> map,
			Integer pageindex, Integer pagesize) {
		String userId = map.get("userId");
		String wfTitle = map.get("wfTitle");
		String deptId = map.get("deptId");
		StringBuffer buffer = new StringBuffer();
		buffer.append("select distinct p.process_title, i.vc_sxmc , to_char(t.applydate, 'yyyy-MM-dd hh24:mi'), t.fprocessid, t4.status, i.vc_sxlx")
			  .append(" from   t_wf_core_item i, t_wf_process p, t_wf_core_receive t left join t_wf_core_noticeinfo  t4 on  t.fprocessid=t4.fprocessid")
			  .append(" where ((t.fprocessid = p.wf_process_uid) or (p.WF_PROCESS_UID = t.PROCESSID  and t.fprocessid is null)) and i.id = p.wf_item_uid");
		if(deptId != null &&!"".equals(deptId)){
			buffer.append(" and t.fromuserid in (select e.employee_guid from zwkj_employee e where e.department_guid in (select d.department_guid from zwkj_department d where d.superior_guid = '"+deptId+"' or d.department_guid = '"+deptId+"'))");

		}else{
			buffer.append(" and t.fromuserid = '"+userId+"'");

		}
	    if(wfTitle!=null && wfTitle.length()>0){
	    	buffer.append(" and p.process_title like '%"+wfTitle+"%'");
	    }
	    
	    buffer.append(" order by  to_char(t.applydate, 'yyyy-MM-dd hh24:mi') desc");
		Query query=super.getEm().createNativeQuery(buffer.toString());
		if (pageindex!=null && pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	@Override
	public int getDofileReceiveCount(Map<String, String> map) {
		String userId = map.get("userId");
		String wfTitle = map.get("wfTitle");
		String deptId = map.get("deptId");
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from (select distinct p.process_title,i.vc_sxmc,to_char(t.applydate, 'yyyy-MM-dd hh24:mi'),t.fprocessid")
			  .append(" from t_wf_core_receive t, t_wf_process p , t_wf_core_item i ")
			  .append(" where ((t.fprocessid = p.wf_process_uid) or (p.WF_PROCESS_UID = t.PROCESSID  and t.fprocessid is null)) and i.id = p.wf_item_uid");
			  if(deptId != null &&!"".equals(deptId)){
					buffer.append(" and t.fromuserid in (select e.employee_guid from zwkj_employee e where e.department_guid in (select d.department_guid from zwkj_department d where d.superior_guid = '"+deptId+"' or d.department_guid = '"+deptId+"'))");
				}else{
					buffer.append(" and t.fromuserid = '"+userId+"'");

				}
			  if(wfTitle!=null && wfTitle.length()>0){
			    	buffer.append(" and p.process_title like '%"+wfTitle+"%'");
			    }
			  buffer.append(" order by t.applydate desc)");
		return Integer.parseInt(getEm().createNativeQuery(buffer.toString()).getSingleResult().toString());
	}

	@Override
	public List<Object[]> getReceiveInfo(String fprocessid,String applydate) {
		String hql = "select distinct t.status, d.department_name, to_char(t.recdate, 'yyyy-MM-dd HH24:mi:ss')," +
				"to_char(t.applydate, 'yyyy-MM-dd HH24:mi:ss'),t.status," +
				"to_char(t.replydate, 'yyyy-MM-dd HH24:mi:ss')," +
				"t.receive_type,t.isreback, dep.tabindex,t.jrdb,t.todepartid,t.id,t.cbcs from t_wf_core_receive t, " +
				" docexchange_department d, zwkj_department dep" +
				" where t.todepartid = d.department_guid " +
				"  and (d.department_guid = dep.department_guid or d.superior_guid =dep.department_guid) and t.takeback is null " ;
		if("".equals(fprocessid)){
			hql += "  and t.fprocessid is null   ";
		}else{
			hql += "  and t.fprocessid = '"+fprocessid+"'  ";
		}
		
		if (CommonUtil.stringNotNULL(applydate)) {
			hql += " and to_char(t.applydate, 'yyyy-MM-dd hh24:mi') = '"+applydate+"'"
					+" order by t.status desc, dep.tabindex ASC";
		}
		return getEm().createNativeQuery(hql).getResultList();
	}

	@Override
	public WfProcess getMaxWfProcessIdByInstanceId(String instanceid) {
		String hql="from WfProcess t where t.wfInstanceUid='"
				+instanceid+"' order by t.applyTime desc";
		List<WfProcess>  select = getEm().createQuery(hql).getResultList();
		if(select != null && select.size()>0){
			return select.get(0);
		}
		return null;
	}

	@Override
	public int getCountMessage(WfProcess wfProcess, String employeeGuid) {
		String sql ="select count(id) from t_wf_core_pushmessage  where (INSTANCEID ='"
					+wfProcess.getfInstancdUid()+"' or INSTANCEID ='"
					+wfProcess.getWfInstanceUid()+"') and PUSHED_EMPID='"
					+employeeGuid+"' and (zt !='1' or zt is null)";
		Query query=super.getEm().createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		if(list!=null&&list.size()!=0){
			return ((BigDecimal)list.get(0)).intValue();
		}
		return 0;
	}

	@Override
	public void updatePushMessageZt(String employeeGuid, WfProcess wfProcess) {
		String sql = "update t_wf_core_pushmessage set zt='1' where (INSTANCEID ='"
					+wfProcess.getWfInstanceUid()+"' or INSTANCEID ='"
					+wfProcess.getfInstancdUid()+"') and PUSHED_EMPID='"
					+employeeGuid+"'";
		this.getEm().createNativeQuery(sql.toString()).executeUpdate();
	}

	
	public void deleteDoFile(DoFile doFile) {

		if (CommonUtil.stringNotNULL(doFile.getDoFile_id())) {
			// 找到 表名
			String sql = "select t.vc_tablename from T_WF_CORE_TABLE t , t_wf_core_dofile f where t.lcid = f.workflowid and f.id = '"+doFile.getDoFile_id()+"'";
			
			List<String> objList =getEm().createNativeQuery(sql).getResultList(); 
			List<String> tables = new ArrayList<String>();
			for(int i=0; i<objList.size();i++){
				String data = objList.get(i); 
				tables.add(data);
			}
			for(int j = 0; j< tables.size() ; j++){
				String sqll = "delete from "+tables.get(j)+" t where t.INSTANCEID = (select f.INSTANCEID from T_WF_CORE_DOFILE f where f.id= '"+doFile.getDoFile_id()+"')";
				getEm().createNativeQuery(sqll).executeUpdate();
			}
			
			String instanceId = doFile.getInstanceId();
			//wf_comment: 删除意见标签数据
			String commentSql = "delete from wf_comment t where t.WF_INSTANCE_ID = '"+instanceId+"'";
			getEm().createNativeQuery(commentSql).executeUpdate();
			
			//t_wf_process 
			String sql_process = "delete from T_WF_PROCESS_Temp p where (p.WF_INSTANCE_UID = '"+instanceId+"' or ALLINSTANCEID = '"+instanceId+"')";
			super.getEm().createNativeQuery(sql_process).executeUpdate();
			
			String sql_doFile = "delete from T_WF_CORE_DOFILE t where t.id='"+doFile.getDoFile_id()+"'";
			super.getEm().createNativeQuery(sql_doFile).executeUpdate();
			delEndInstanceId(instanceId);
		}
	}
	
	@Override
	public List<Object[]> getIsBt(String workFlowId, String nodeid,
			String formId,String isbt,String name) {
		String sql ="select t.vc_tagname, ta.columncname, t.isbt ,ta.columnname from t_wf_core_formpermit t " +
				" left join t_wf_core_form_map_column ta on ta.formid = t.vc_formid " +
				" and t.vc_tagname = ta.FORMTAGNAME  where t.lcid = '"+workFlowId+"' and t.vc_formid = '"
				+formId+"' and t.VC_MISSIONID = '"+nodeid+"'";
		if(isbt!=null&&!"".equals(isbt)){
			sql +=" and t.VC_LIMIT=2 and t.isbt= '"+isbt+"'";
		}
		if(name!=null&&!"".equals(name)){
			sql +=" and t.VC_TAGNAME = '"+name+"'";
		}
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<Object[]> getIsBt(String workFlowId, String nodeid,
			String formId,String isbt,String name, String userId) {
		String sql ="select t.vc_tagname, ta.columncname, t.isbt ,ta.columnname from t_wf_core_formpermit t " +
				" left join t_wf_core_form_map_column ta on ta.formid = t.vc_formid and t.vc_tagname = ta.FORMTAGNAME  ";
				
				if(StringUtils.isNotBlank(userId)){
					sql += " left join t_wf_core_inneruser_map_user i on i.inneruser_id= t.vc_roleid ";
				}
				sql += " where t.lcid = '"+workFlowId+"' and t.vc_formid = '"
				+formId+"' and t.VC_MISSIONID = '"+nodeid+"'";
		if(isbt!=null&&!"".equals(isbt)){
			sql +=" and t.VC_LIMIT=2 and t.isbt= '"+isbt+"'";
		}
		if(name!=null&&!"".equals(name)){
			sql +=" and t.VC_TAGNAME = '"+name+"'";
		}
		if(StringUtils.isNotBlank(userId)){
			sql += " and i.employee_id='"+userId+"'";
		}
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<Object[]> getIsPy(String workFlowId, String nodeid,
			String formId,String ispy,String name) {
		String sql ="select t.vc_tagname, ta.columncname, t.ispy from t_wf_core_formpermit t " +
				" left join t_wf_core_form_map_column ta on ta.formid = t.vc_formid " +
				" and t.vc_tagname = ta.FORMTAGNAME  where t.lcid = '"+workFlowId+"' and t.vc_formid = '"
				+formId+"' and t.VC_MISSIONID = '"+nodeid+"'";
		if(ispy!=null&&!"".equals(ispy)){
			sql +=" and t.VC_LIMIT=2 and t.ISPY= '"+ispy+"'";
		}
		if(name!=null&&!"".equals(name)){
			sql +=" and t.VC_TAGNAME = '"+name+"'";
		}
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public WfProcess getProcessByInstanceIdAndDate(String instanceId,
			Date applyTime) {
		String sql = "select * from t_wf_process where WF_INSTANCE_UID='"+instanceId
				+"' and apply_time>to_date('"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(applyTime)+"','yyyy-MM-dd hh24:mi:ss') order by apply_time ";
		Query query=super.getEm().createNativeQuery(sql, WfProcess.class);
		List<WfProcess> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Department findDeptByDeptId(String deptid) {
		String sql = "select t.* from zwkj_department t where t.department_guid='"+deptid+"'";
		List<Department> list=getEm().createNativeQuery(sql, Department.class).getResultList();
		if(list.size() > 0){
			return (Department) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updatePushMessage(String employeeGuid, WfProcess wfProcess) {
		String sql = "update t_wf_core_pushmessage set PUSHED_EMPID='"+employeeGuid+"'  where  INSTANCEID ='"
				+wfProcess.getfInstancdUid()+"' and PUSHED_EMPID='"
				+wfProcess.getUserUid()+"'";
		this.getEm().createNativeQuery(sql.toString()).executeUpdate();
	}

	@Override
	public void updateWfProcessByTs(String employeeGuid, WfProcess wfProcess) {
		String sql = "update T_WF_PROCESS t set t.user_uid='"+employeeGuid
				+"' where t.WF_F_INSTANCE_UID='"+wfProcess.getfInstancdUid()+"' and t.is_merge='2' and t.user_uid='"+wfProcess.getUserUid()+"'";
		this.getEm().createNativeQuery(sql.toString()).executeUpdate();
	}

	@Override
	public List<Object[]> getNewestWfProcess(String instanceId) {
		StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct t.wf_instance_uid, n.wfn_name,t.process_title, i.vc_sxmc,t.owner, ee.employee_name," +
					"to_char(t.jdqxdate, 'yyyy-MM-dd HH24:mi:ss'), to_char(t.zhqxdate, 'yyyy-MM-dd HH24:mi:ss'),t.step_index,t.action_status, t.DOTYPE ")
				  .append(" from t_wf_process t, wf_node n, zwkj_employee ee ,t_wf_core_item i")
				  .append(" where t.wf_node_uid = n.wfn_id and ee.employee_guid = t.owner and t.is_over = 'NOT_OVER' and i.id = t.wf_item_uid")
				  .append(" and t.allinstanceid = '"+instanceId+"' and t.is_show=1");
		return getEm().createNativeQuery(buffer.toString()).getResultList();
	}

	@Override
	public List<WfProcess> getWfProcessList(WfProcess wfProcess) {
		StringBuffer hql = new StringBuffer();
			hql.append("from WfProcess t where t.wfInstanceUid='"+wfProcess.getWfInstanceUid()+"' and (t.isRedirect is null or t.isRedirect = 0 )")
				.append(" and t.isShow =1");
		if(wfProcess.getStepIndex()!=null){
			hql.append(" and t.stepIndex="+wfProcess.getStepIndex());
		}
		if(wfProcess.getIsOver()!=null){
			hql.append(" and t.isOver='"+wfProcess.getIsOver()+"'");
		}
		hql.append(" order by t.stepIndex  desc");
		/*String hql = " from WfProcess t where t.wfInstanceUid='"+wfProcess.getWfInstanceUid()+"'" +
				" and t.stepIndex="+wfProcess.getStepIndex() +" and (t.isRedirect is null or t.isRedirect = 0 )" +
						" and t.isShow =1 " ;*/
		return getEm().createQuery(hql.toString()).getResultList();
	}

	@Override
	public void addProcess(WfProcess wfProcess) {
		getEm().persist(wfProcess);
		
	}

	@Override
	public List<Object[]> getKcqWfProcess() {
		StringBuffer querySql = new StringBuffer();
	  querySql.append("select  p.wf_process_uid,p.tonodeid,p.user_uid,i.vc_xxlx,t.wfn_txnr,t.txnr_txnrids,p.wf_instance_uid from t_wf_process p ")
				.append(" left join T_WF_CORE_ITEM i on i.id = p.wf_item_uid ")
				.append(" left join WF_NODE t on t.wfn_id=p.wf_node_uid ")
			    .append(" where  p.isexchanging=0 " +
			    		" and t.wfn_tqtxsjline is not null" +
			    		" and  (p.fromnodeid != p.tonodeid or p.step_index = '1' or t.wfn_self_loop = '1')" +
			    		" and p.is_over='NOT_OVER' ")
				.append(" and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null) ")
			    .append(" and (p.action_status is null or p.action_status!=2) and (p.iscqfs!=1 or p.iscqfs is null) ")
			    .append(" and p.jdqxdate is not null and (p.JDQXDATE-to_date('")
			    .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())))
			    .append("','yyyy-mm-dd hh24:mi:ss'))*24>0")
				.append(" and (p.JDQXDATE-to_date('")
			    .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())))
			    .append("','yyyy-mm-dd hh24:mi:ss'))*24<t.wfn_tqtxsjline");
	  Query query=super.getEm().createNativeQuery(querySql.toString());
		return query.getResultList();
	}

	@Override
	public List<Object[]> getYwbList(String instanceid, String tableName, String culoum) {
		String sql = "select "+culoum+" from "+tableName+" where instanceid='"+instanceid+"'";
		Query query=super.getEm().createNativeQuery(sql.toString());
		return query.getResultList();
	}

	@Override
	public List<PushMessage> getPushMessage(String employeeGuid,
			String wfInstanceUid) {
		String sql = "select t.* from t_wf_core_pushmessage t where t.push_empid='"+employeeGuid+"' and  t.finstanceid='"+wfInstanceUid+"'"; 
		return getEm().createNativeQuery(sql,PushMessage.class).getResultList();
	}

	@Override
	public List<Object> getIntanceIdByZxIntanceId(String instanceId,
			String doType) {
		String sql = "select t.wf_instance_uid from t_wf_process t where t.dotype="+doType
				+" and t.wf_f_instance_uid in (select t1.wf_f_instance_uid " +
				" from t_wf_process t1 where t1.wf_instance_uid='"+instanceId+"')";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<WfProcess> getWfProcessByEntity(WfProcess wfProcess) {
		if(wfProcess==null ){
			return null;
		}
		String sql = " from WfProcess t where 1=1";
		if(wfProcess.getStepIndex()!=null){
			sql+= " and t.stepIndex = "+wfProcess.getStepIndex();
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getNodeUid())){
			sql+= " and t.nodeUid = '"+wfProcess.getNodeUid()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getIsOver())){
			sql+= " and t.isOver = '"+wfProcess.getIsOver()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getWfInstanceUid())){
			sql+= " and t.wfInstanceUid = '"+wfProcess.getWfInstanceUid()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getfInstancdUid())){
			sql+= " and t.fInstancdUid = '"+wfProcess.getfInstancdUid()+"'";
		}
		sql+= " order by t.stepIndex desc";
		return getEm().createQuery(sql).getResultList();
	}
	
	@Override
	public int getCountOfDoFileFavourites(String bigDepId, String conditionSql,
			String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select count(distinct t.instanceid) from t_wf_core_dofile t left join t_wf_process p on p.wf_instance_uid = t.instanceid,T_WF_CORE_ITEM i, t_wf_core_dofile_favourite fav" +
				" where (t.isdelete!=1 or t.isdelete is  null) " +
				" and t.item_id=i.id " +
				  conditionSql+
				" and t.id = fav.dofileid and fav.userid='"+userId+"'");
		if(CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		return Integer.parseInt(getEm().createNativeQuery(sb.toString()).getSingleResult().toString());
	}

	@Override
	public List<DoFile> getDoFileFavouriteList(String bigDepId,
			String conditionSql, String userId, Integer pageindex,
			Integer pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append( " select * from t_wf_core_dofile where instanceid in (select distinct t.instanceid from t_wf_core_dofile t left join t_wf_process p on p.wf_instance_uid = t.instanceid left join wh_view v on t.instanceid = v.instanceid,T_WF_CORE_ITEM i, t_wf_core_dofile_favourite fav" +
				" where (t.isdelete!=1 or t.isdelete is  null) " +
				" and t.item_id=i.id " +
				conditionSql+
				" and t.id = fav.dofileid and fav.userid='"+userId+"'");
		if(CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		
		sb.append(conditionSql).append(" ) order by dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList(); 
	}
	
	@Override
	public void saveDofileFavourite(DofileFavourite dofileFavourite) {
		getEm().persist(dofileFavourite);
	}

	@Override
	public void removeDofileFavourite(DofileFavourite dofileFavourite) {
		if(dofileFavourite!=null){
			String id = dofileFavourite.getId();
			String sql = "delete from t_wf_core_dofile_favourite t where t.id='"+id+"'";
			getEm().createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public DofileFavourite getDofileFavouriteById(String dofileId, String userId) {
		String hql = " from DofileFavourite t where t.dofileId='"+dofileId+"' and t.userId='"+userId+"'";
		List<DofileFavourite> list  = getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public int getDoFileReceiveCountOfMobile(String userId, Integer status,String	condition) {
		String sql = "select count(*) from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
				" where  t.status = "+status
				+" and p.WF_PROCESS_UID = t.PROCESSID and i.id = p.wf_item_uid and (p.is_back != '2' or p.is_back is null) and t.instanceid=s.instanceid and s.takeback is null and t.takeback is null ";
		if(condition!=null && !condition.equals("")){
			sql += condition;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	public List<DoFileReceive> getDofileReceiveListOfMobile(String userId,Integer pageIndex,Integer pageSize,Integer status, String condition) {
		String sql = "select  p.process_title," +
				" i.VC_SXMC,t.receive_type,p.WF_PROCESS_UID, t.id,p.wf_item_uid" +
				" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
				" where t.status ="+status +" and   (p.is_back != '2' or p.is_back is null)" +
				" and p.WF_PROCESS_UID = t.PROCESSID and i.id = p.wf_item_uid and t.instanceid=s.instanceid and s.takeback is null and t.takeback is null ";
		sql += condition;
			sql +=" order by t.applydate desc";
		Query query = getEm().createNativeQuery(sql);
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex*pageSize);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list =  query.getResultList();
		List<DoFileReceive> returnList = new ArrayList<DoFileReceive>();
		DoFileReceive doFileReceive = null;
		for(int i=0; list!=null && i<list.size(); i++){
			Object[] data =(Object[]) list.get(i);
			doFileReceive = new DoFileReceive();
			doFileReceive.setTitle(data[0]==null?"":data[0].toString());
			doFileReceive.setItemName(data[1]==null?"":data[1].toString());
			doFileReceive.setReceiveType(data[2]==null?"":data[2].toString());
			doFileReceive.setProcessId(data[3]==null?"":data[3].toString());
			doFileReceive.setId(data[4].toString());
			doFileReceive.setItemId(data[5]==null?"":data[5].toString());
			returnList.add(doFileReceive);
		}
		return returnList;
	}

	@Override
	public List<DoFileReceive> getDofileFavouriteByFprocessId(
			String fProcessid) {
		String hql = " FROM DoFileReceive t WHERE t.fProcessId='"+fProcessid+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public String getSwdjh(String type) {
		String sql = "SELECT max(to_number(t.swdjh)) FROM swdjlxhview t WHERE t.swdjlx = '"+type.trim()+"'";
		List<String> list  = getEm().createNativeQuery(sql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0)==null?"0":list.get(0).toString();
		}
		return "0";
	}
	

	@Override
	public List<Object[]> findSw(Integer pageIndex, Integer pageSize,
			String deptName,String swdj, String conditionSql, String conditionSql2) {
		String sql = "select  swdjh, lwbt, swsj,lwdw,lwh,fwsj,jjcd, fs,swdjlx,instanceid,type from(";
		sql+= "select v.swdjh,t.lwbt,t.swsj,t.lwdw,t.lwh,t.fwsj,t.jjcd,t.fs,v.swdjlx,t.instanceid,'1' as type " +
				" FROM T_WF_CORE_SW t, swdjlxhview v" +
				" where v.instanceid=t.instanceid and v.swdjlx='"
				+swdj+"'";
		String depN = "";
		if(CommonUtil.stringNotNULL(deptName)){
			String[] depNs = deptName.split(",");
			sql+=" and (";
			for(int i=0; i<depNs.length; i++){
				depN = depNs[i];
				if(i!=0){
					sql+= " or t.zsdw like '%"+depN+"%' or t.csdw like '%"+depN+"%' ";
				}else{
					sql+= " t.zsdw like '%"+depN+"%' or t.csdw like '%"+depN+"%' ";
				}
				
			}
			sql+=")";
		}
		sql += "union all "; 
		sql += "select v.swdjh, v.wjbt ,v.swsj,v.lwdw, v.lwh, v.LWRQ,v.jjcd,v.fs, v.swdjlx,v.instanceid,'2' as type " +
				" from swdjlxhview v" +
				" where v.instanceId not in (select distinct (k.INSTANCEID) from T_WF_CORE_SW k)" +
				"	and v.swdjlx='"+swdj+"'";
		if(conditionSql2!=null && !conditionSql2.equals("")){
			sql += conditionSql2;
		}
		sql+=") k ";
		sql += "order by cast(k.swdjh as int) desc";
		Query query=super.getEm().createNativeQuery(sql);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public int findCountSw(String deptName,	String swdj, String conditionSql,String conditionSql2) {
		String sql = "select count(*)" +
				" FROM T_WF_CORE_SW t, swdjlxhview v" +
				" where v.instanceid=t.instanceid and v.swdjlx='"
				+swdj+"'";
		String depN = "";
		if(CommonUtil.stringNotNULL(deptName)){
			String[] depNs = deptName.split(",");
			sql+=" and (";
			for(int i=0; i<depNs.length; i++){
				depN = depNs[i];
				if(i!=0){
					sql+= " or t.zsdw like '%"+depN+"%' or t.csdw like '%"+depN+"%' ";
				}else{
					sql+= " t.zsdw like '%"+depN+"%' or t.csdw like '%"+depN+"%' ";
				}
				
			}
			sql+=")";
		}
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += conditionSql;
		}
		int count = Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
		
		String sql2 = "select count(*)" +
				" from swdjlxhview v" +
				" where v.instanceId not in (select distinct (k.INSTANCEID) from T_WF_CORE_SW k)" +
				"	and v.swdjlx='"+swdj+"'";
		if(conditionSql2!=null && !conditionSql2.equals("")){
			sql2 += conditionSql2;
		}
		int count2 = Integer.parseInt(getEm().createNativeQuery(sql2).getSingleResult().toString());
		return count+count2;
	}

	@Override
	public void saveMsgSend(MsgSend msgSend) {
		if(msgSend==null){
			return;
		}
		getEm().persist(msgSend); 
	}

	@Override
	public String findPendDealIngUser(String processId, String instanceId,
			Integer stepIndex, String apply_time) {
		String sql = "select emp.employee_name from t_wf_process t, zwkj_employee emp"
				+" where t.is_over = 'NOT_OVER' and t.user_uid = emp.employee_guid"+
				" and t.wf_instance_uid = '"+instanceId+"'"+
				" and t.apply_time  = to_date('"+apply_time+"', 'yyyy-MM-dd hh24:mi:ss')" +
				" and t.step_index = "+stepIndex +
				" order by emp.tabindex";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		String userName = "";
		for(int i=0; list!=null && i<list.size(); i++){
			userName = list.get(0).toString();
		}
		return userName;
	}
	@Override
	public List<DoFileReceive> getDoFileReceiveByPIdAndDeptIds(
			String pInstanceId, String linkDeptIds) {
		String hql = "from DoFileReceive r where r.pInstanceId = '"+pInstanceId+"' and r.toDepartId in ("+linkDeptIds+")";
		
		return  getEm().createQuery(hql).getResultList();
	}
	@Override
	public int findAllDofileListCount(String conditionSql) {
		String sql = "select count(1) from t_wf_core_dofile t, T_WF_CORE_ITEM i where (t.isdelete!=1 or t.isdelete is  null) and t.item_id=i.id" ;
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += conditionSql;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<DoFile> findAllDofileList(String conditionSql, Integer pageIndex,
			Integer pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select t.* from t_wf_core_dofile t,t_wf_core_item i where (t.isdelete!=1 or t.isdelete is  null) and t.item_id=i.id ");
		if(conditionSql!=null && !conditionSql.equals("")){
			sb.append(conditionSql);
		}
		sb.append(" order by t.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();  
	}

	@Override
	public void addReply(Reply reply) {
		getEm().persist(reply);
		
	}
	
	@Override
	public List<DocXgDepartment> getDocXgDepartmentListByDepId(String depid) {
		String hql = " from DocXgDepartment t where t.deptGuid = '"+depid+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public WfProcess findBxjhWfProcess(WfProcess wfProcess) {
		if(wfProcess==null){
			return null;
		}
		String hql = " from WfProcess t where t.wfInstanceUid ='"+wfProcess.getWfInstanceUid()+"'" +
				" and t.toNodeid = '"+wfProcess.getToNodeid()+"' " +
				" and t.stepIndex = "+wfProcess.getStepIndex() +
				" and (t.isBack!='2' or t.isBack is null) and t.isMaster = 2";
		List<WfProcess> list=  getEm().createQuery(hql).getResultList();
		
		return	(list!=null&&list.size()>0)?list.get(0):null;
	}

	@Override
	public void falseDeleteDoFile(String dofileId,String instanceId,Employee emp) {
		String dofile = "update t_wf_core_dofile set ISDELETE = 1 ,DELUSERNAME = :DELUSERNAME ,DELUSERID = :DELUSERID  where INSTANCEID = :instanceId and id = :id";
		Query query1 =	getEm().createNativeQuery(dofile);
		query1.setParameter("DELUSERNAME", emp.getEmployeeName());
		query1.setParameter("DELUSERID", emp.getEmployeeGuid());
		query1.setParameter("instanceId", instanceId);
		query1.setParameter("id", dofileId);
		query1.executeUpdate();
	}

	@Override
	public void falseDeleteProcess(String instanceId) {
		// 插入数据
		String processInsert = "insert into  t_wf_process_temp  value " +
				"select * from t_wf_process  where t_wf_process.wf_instance_uid = :instanceId or t_wf_process.allinstanceid =:instanceId"; 
		Query query1 =	getEm().createNativeQuery(processInsert);
		query1.setParameter("instanceId", instanceId);
		query1.executeUpdate();
		// 删除数据
		String processDelete = "delete t_wf_process where  wf_instance_uid = :instanceId or ALLINSTANCEID =:instanceId";
		Query query2 =	getEm().createNativeQuery(processDelete);
		query2.setParameter("instanceId", instanceId);
		query2.executeUpdate();	
		
	}

	@Override
	public void recoverDoFile(String dofileId,String instanceId) {
		String dofile = "update t_wf_core_dofile set ISDELETE = 0 where INSTANCEID = :instanceId and id = :id";
		Query query1 =	getEm().createNativeQuery(dofile);
		query1.setParameter("instanceId", instanceId);
		query1.setParameter("id", dofileId);
		query1.executeUpdate();
	}

	@Override
	public void recoverProcess(String instanceId) {
		// 插入数据
		String processInsert = "insert into  T_WF_PROCESS  value " +
						"select * from T_WF_PROCESS_Temp  where T_WF_PROCESS_Temp.WF_INSTANCE_UID = :instanceId or T_WF_PROCESS_Temp.ALLINSTANCEID =:instanceId"; 
		Query query1 =	getEm().createNativeQuery(processInsert);
		query1.setParameter("instanceId", instanceId);
		query1.executeUpdate();
		// 删除数据
		String processDelete = "delete T_WF_PROCESS_Temp where  WF_INSTANCE_UID = :instanceId or ALLINSTANCEID =:instanceId";
		Query query2 =	getEm().createNativeQuery(processDelete);
		query2.setParameter("instanceId", instanceId);
		query2.executeUpdate();	
	}
	
	
	@Override
	public DzJcdb findDzJcdbById(String no) {
		String sql = "select t.* from dzjcdb@riseapprove t where t.no =:no";
		Query query = getEm().createNativeQuery(sql, DzJcdb.class);
		query.setParameter("no", no);
		List<DzJcdb> list = query.getResultList();
		return (list!=null&& list.size()>0)?list.get(0):null;
	}

	@Override
	public void updateDzJcdb(DzJcdb dzJcdb) {
		getEm().merge(dzJcdb);
	}

	
	@Override
	public DzJcdbShip findDzJcdbShipById(String instanceId) {
		String sql = "select t.* from t_wf_core_dzjcdbship t where t.instanceId = '"+instanceId+"'";
		List<DzJcdbShip> list = getEm().createNativeQuery(sql, DzJcdbShip.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public void saveDzJcdbShip(DzJcdbShip dzJcdbShip) {
		getEm().persist(dzJcdbShip);
	}

	@Override
	public List<WfProcess> isAllJBEnd(String fjbProcessId) {
		String hql = "select t.* from t_wf_process t where t.wf_instance_uid in (select distinct t.wf_instance_uid  from t_wf_process t where t.fjbprocessid = '"+fjbProcessId+"')"+
					 "and t.step_index =  (select max(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid = t.wf_instance_uid )";
		Query query = getEm().createNativeQuery(hql, WfProcess.class);
		List<WfProcess> list = query.getResultList();
		return (list!=null&& list.size()>0)?list:null;
	}

	@Override
	public List<GetProcess> getJBProcessList(String processId) {
		StringBuffer querySql = (new StringBuffer("select "))
				 .append("twp.wf_process_uid as wfProcessUid,")
				 .append("twp.wf_instance_uid as wfInstanceUid,")
				 .append("twp.wf_uid as wf_uid,")
				 .append("twp.step_index as stepIndex,")
				 .append("twp.is_end as isEnd,")
				 .append("twp.wf_form_id as formId,")
				 .append("(select t.wfn_name from wf_node t where t.wfn_id = twp.wf_node_uid) as nodeName,")
				 .append("twp.wf_node_uid as nodeId,")
				 .append("twp.user_uid as userId,")
				 .append("(select t.employee_name from zwkj_employee t where t.employee_guid = twp.fromuserid) as fromUserName,")
				 .append("emp.employee_name  as userName,")
				 .append("twp.apply_time as applyTime,")
				 .append("twp.finsh_time as finshTime, ")
				 .append("twp.IS_BACK as is_back, ")
				 .append(" (select decode(count(*), 0, 0, 1) from T_WF_PROCESS p  where p.wf_f_instance_uid = twp.wf_instance_uid and (((p.fromnodeid = twp.tonodeid or twp.dotype = '4') and p.ismanyinstance = '1') or (p.ismanyinstance = '0' and twp.step_index = p.pstepindex))) as isHaveChild, ")
				 .append("twp.fromnodeid as fromNodeId, ")
				 .append("twp.tonodeid as toNodeId, ")
				 .append("twp.push_childprocess as push_childprocess, ")
				 .append("twp.is_merge as is_merge, ")
				 .append("twp.dotype as doType, ")
				 .append("twp.wf_f_instance_uid as f_instanceId, ")
				 .append("twp.jssj as jssj, ")
				 .append("twp.isRedirect as isRedirect, ")
				 .append("twp.pdfPath as pdfPath, ")
				 .append("twp.wf_uid as workflowId, ")
				 .append("twp.is_over as isOver, ")
				 .append("twp.fjbProcessId as fjbProcessId, ")
				 .append("twp.isreturnstep as isreturnstep ")
				 .append(" from t_wf_process twp, zwkj_employee emp where emp.employee_guid = twp.user_uid and twp.FJBPROCESSID='"+processId+"'")
				 .append(" and (twp.IS_DUPLICATE!='true' or twp.IS_DUPLICATE is null)")
				 .append(" order by twp.apply_time,twp.step_index, emp.tabindex asc ");
		return getEm().createNativeQuery(querySql.toString(), "GetProcessResults").getResultList();
	}
	
	@Override
	public void saveEndInstanceId(EndInstanceId endInstanceId) {
		getEm().persist(endInstanceId);
	}

	@Override
	public boolean delEndInstanceId(String instanceId) {
		String sql = "delete from t_wf_core_end_instanceid t where t.instanceid='"+instanceId+"'";
		super.getEm().createNativeQuery(sql).executeUpdate();
		return true;
	}

	@Override
	public void deleteErrorProcess() {
		/*String sql = "delete from t_wf_process t where t.process_title is null";
		super.getEm().createNativeQuery(sql).executeUpdate();*/
	}

	@Override
	public List<WfProcess> findWfProcessList(WfProcess wfp) {
		if(wfp==null){
			return null;
		}
		String instanceId = wfp.getWfInstanceUid();
		Integer stepIndex = wfp.getStepIndex();
		String nodeId = wfp.getNodeUid();
		String sql = "select t.* from t_wf_process t where (t.is_back!='2' or t.is_back is null)  and t.wf_node_uid = '"+nodeId
				+"' and  t.step_Index ="+stepIndex+" and t.wf_instance_uid='"+instanceId+"'";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}

	@Override
	public List<WfProcess> findWfProcessList(String instanceId,
			Integer stepIndex, String userId) {
		String sql = "select t.* from t_wf_process t where (t.is_back!='2' or t.is_back is null) " +
				" and t.step_Index ="+stepIndex +
				" and t.wf_instance_uid='"+instanceId+"'";
		if(StringUtils.isNotBlank(userId)){
			sql += " and t.user_uid = '"+userId+"'";
		}
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}

	@Override
	public int getNewCountDoFiles(String conditionSql) {
		
		StringBuffer querySql =  new StringBuffer(2048);
		querySql.append("select count(*) from t_wf_core_dofile t join  t_wf_process p on  t.instanceid = p.wf_instance_uid ")
				.append(" and p.apply_time in (select max(p2.apply_time) from t_wf_process p2 ")
				.append("  where (p2.is_back != '2' or p2.is_back is null) ")
				.append(" and p.wf_instance_uid = p2.wf_instance_uid ")
				.append(" and p2.is_show=1 group by p2.wf_instance_uid ) and (p.is_master =1 or p.is_end = 1)  and( p.is_over = 'OVER' or (p.is_over = 'NOT_OVER' and p.step_index != 2)) ")
				.append(conditionSql);
		return Integer.parseInt(super.getEm().createNativeQuery(querySql.toString()).getSingleResult().toString());
	}

	@Override
	public List<Object> getNewDoFileList(String conditionSql,Integer pageIndex, Integer pageSize) {

		StringBuffer querySql =  new StringBuffer(2048);
		querySql.append("select t.id,t.workflowid,t.item_id,t.item_name,t.instanceid,t.formid, to_char(p.apply_time,'YYYY-MM-DD HH24:MI') as apply_time,t.dofile_title,decode((select count(1) from t_wf_core_end_instanceid e where p.wf_instance_uid = e.instanceId), 0, 0, 1) as status, n.wfn_name,n.wfn_id ,p.wf_process_uid from t_wf_core_dofile t join  t_wf_process p on  t.instanceid = p.wf_instance_uid ")
				.append(" and p.apply_time in (select max(p2.apply_time) from t_wf_process p2  ")
				.append(" where (p2.is_back != '2' or p2.is_back is null)  ")
				.append(" and p.wf_instance_uid = p2.wf_instance_uid ")
				.append(" and p2.is_show=1 group by p2.wf_instance_uid ) and (p.is_master =1 or p.is_end = 1)  and( p.is_over = 'OVER' or (p.is_over = 'NOT_OVER' and p.step_index != 2)) ")
				.append("  ,wf_node n where p.wf_node_uid =n.wfn_id")
				.append(conditionSql);
		querySql.append(" order by p.apply_time desc ");
		
		Query query=super.getEm().createNativeQuery(querySql.toString());
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}


	@Override
	public void modifyReceiveData(String fprocessid) {
		String sql = "update t_wf_core_receive t set t.takeback = 1 where t.fprocessid ='"+fprocessid+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void modifySwData(String fprocessid) {
		String sql = "update t_wf_core_sw s set s.takeback = 1" + 
						" where s.instanceid in " + 
						" (select t.instanceid " + 
						"          from t_wf_core_receive t " + 
						"         where t.fprocessid = '"+fprocessid+"') ";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public List<DoFileReceive> getDeptIdByFprocessId(String fprocessid) {
		String sql = "select t.*, t.rowid from t_wf_core_receive t where t.fprocessid='"+fprocessid+"'  and t.takeback is null";
		return getEm().createNativeQuery(sql, DoFileReceive.class).getResultList();
	}

	@Override
	public List<Object> findWfProcessByInstanceIddesc(String wf_instance_uid) {
		String sql = "select t.is_over,t.wf_process_uid,t.status,t.is_master,t.is_end,t.pdfpath,t.wf_form_id , t.action_status,t.wf_node_uid from t_wf_process t where wf_instance_uid = '"+wf_instance_uid+"' and (t.is_back is null or t.is_back ='0' ) order by t.step_index desc";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public void deleteDoFileReceiveList(String fProcessId) throws Exception {
		String sql = "delete from t_wf_core_receive t where t.fProcessId='"+fProcessId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}
	
	@Override
	public void deleteDoFileSwList(String instanceId) throws Exception {
		String sql =  "delete from t_wf_core_sw t where t.instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}

	@Override
	public WfProcess findSendWfProcess(String instanceId) {
		String sql = "select t.pstepindex, t.wf_f_instance_uid  from t_wf_process t where t.wf_instance_uid = '"+instanceId+"' and t.step_index = 1";
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null){
			Object[] data = list.get(0);
			Integer stepIndex = (data[0]!=null &&  !data[0].equals(""))?Integer.parseInt(data[0].toString()):0;
			String f_instanceId = data[1]!=null?data[1].toString():"";
			String sql2 = "select t.* from t_wf_process t where t.wf_instance_uid='"+f_instanceId+"' and t.step_index="+stepIndex;
			List<WfProcess> wfpList = getEm().createNativeQuery(sql2, WfProcess.class).getResultList();
			return  (wfpList!=null&& wfpList.size()>0)?wfpList.get(0):null;
		}else{
			return null;
		}
	}

	@Override
	public WfProcess findFakeWfProcess(String instanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.dotype='3'";
		List<WfProcess> list = getEm().createNativeQuery(sql, WfProcess.class).getResultList();
		return  (list!=null && list.size()>0)?list.get(0):null;
	}
	
	@Override
	public List<WfProcess> getProcessListByParams(Map<String, String> map){
		String sql = " select t.* from t_wf_process t where 1=1 ";
		String continueInstanceId = map.get("continueInstanceId");
		if(CommonUtil.stringNotNULL(continueInstanceId)){
			sql += " and t.continueInstanceId='" + continueInstanceId + "'";
		}
		List<WfProcess> wfpList = getEm().createNativeQuery(sql, WfProcess.class).getResultList();
		return wfpList;
	}
	
	
	public List<WfProcess> findWfProcessList(String workFlowId,String instanceId,String nodeId, Integer stepIndex){
		StringBuffer hql = new StringBuffer();
		hql.append(" from WfProcess ");
		hql.append(" where wfUid='"+workFlowId+"' and wfInstanceUid='"+instanceId+"' and nodeUid='"+nodeId+"' and (isDuplicate!='true' or isDuplicate is null)");
		if(null != stepIndex && !stepIndex.equals(0)){
			hql.append(" and stepIndex="+stepIndex);
		}
		hql.append(" order by sort desc");
		return getEm().createQuery(hql.toString()).getResultList();
	}
	
	@Override
	public List<LowDoc> getLowDocList(String userId,
			Integer pageIndex, Integer pageSize, Integer status,Map<String,String> map){
		String todepartId = map.get("departId");
		String isDept = map.get("isDept");
		//1,表示已收
		String sql = "";
		String sql_same = "";
		List<LowDoc> returnList = new ArrayList<LowDoc>();
		//去除重复的（子部门）
		if(todepartId.indexOf("','") == -1){
			sql_same += "select t.id," +
					          " s.lwbt," +
					          " s.lwh," +
					          " s.gwlx," +
					          " s.zsdw," +
					          " s.csdw ," +
					          " (select e.EMPLOYEE_NAME from zwkj_employee e where e.EMPLOYEE_GUID = t.fromuserid) as EMPLOYEE_NAME," +
					          " to_char(s.fwsj, 'yyyy-MM-dd hh24:mi:ss')," +
					          " s.lwdw," +
					          " t.instanceid," +
					          " p.wf_process_uid ," +
					          " t.id," +
					          " t.toExchangePath," +
					          " v.wh," +
					          " i.VC_SXMC," +
					          " t.fromuserid, " +
							  " t.dyfs " +
							  " from T_WF_CORE_RECEIVE t left join wh_view v on t.pinstanceid = v.instanceid,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
							  " where 1=1 " +
							  " and (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") " ;
			if (CommonUtil.stringIsNULL(isDept)  || !"1".equals(isDept)){
				sql_same += "or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid = "+todepartId+")" ;
			}
			sql_same += " )" +
						" and (p.is_back != '2' or p.is_back is null)" +
					    " and i.id = p.wf_item_uid " +
					    " and t.instanceid=s.instanceid ";
		
			if(status == 1){
				sql += sql_same +
								" and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) ";
			}else{
				sql += sql_same +
								" and t.status ="+status +"" +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2) ";
			}
			
			if(status!=null && status==1){
//				sql +=" order by t.isreback desc, t.recdate desc";
				sql +=" order by to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') desc";
			}else{
//				sql +=" order by t.applydate desc";
				sql +=" order by  to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') desc, t.applydate desc";
			}
				
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null
					&& pageIndex != -1 && pageSize != -1) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List<Object[]> list =  query.getResultList();
			LowDoc lowDoc = null;
			for(int i=0; list!=null && i<list.size(); i++){
				Object[] data =(Object[]) list.get(i);
				lowDoc = new LowDoc();
				String instanceId = data[9]==null?"":data[9].toString();
				lowDoc.setDocguid(data[0]==null?"":data[0].toString());
				lowDoc.setCebid("");
				lowDoc.setTitle(data[1]==null?"":data[1].toString());
				lowDoc.setJgdz(data[2]==null?"":data[2].toString());
				lowDoc.setFwnh(data[2]==null?"":data[2].toString());
				lowDoc.setFwxh(data[2]==null?"":data[2].toString());
				lowDoc.setKeyword("");
				lowDoc.setDoctype(data[3]==null?"":data[3].toString());
				lowDoc.setPriority((long) 1);
				
				String xto_all = data[4]==null?"":ClobToString.clob2String((SerializableClob) data[4]);
				if(CommonUtil.stringNotNULL(xto_all)){
					String[] xtos_all = xto_all.split("\\*");
					String[] xtoIds = xtos_all[0].split(",");
					String xto = "";
					for(String xtoId:xtoIds){
						xto += xtoId.substring(0,xtoId.lastIndexOf("}")+1)+",";
					}
					if(CommonUtil.stringNotNULL(xto)){
						xto = xto.substring(0,xto.length()-1);
					}
					lowDoc.setXto(xto);
					lowDoc.setXtoName(xtos_all[1]);
				}else{
					lowDoc.setXto("");
					lowDoc.setXtoName("");
				}
				String xcc_all = data[5]==null?"":ClobToString.clob2String((SerializableClob) data[5]);
				if(CommonUtil.stringNotNULL(xcc_all)){
					String[] xccs_all = xcc_all.split("*");
					String[] xccIds = xccs_all[0].split(",");
					String xcc = "";
					for(String xccId:xccIds){
						xcc += xccId.substring(0,xccId.lastIndexOf("}")+1)+",";
					}
					if(CommonUtil.stringNotNULL(xcc)){
						xcc = xcc.substring(0,xcc.length()-1);
					}
					lowDoc.setXcc(xcc);
					lowDoc.setXccName(xccs_all[1]);
				}else{
					lowDoc.setXcc("");
					lowDoc.setXccName("");
				}
				
				lowDoc.setSenderId("");
				lowDoc.setSenderName(data[6]==null?"":data[6].toString());
				lowDoc.setSenderTime(data[7]==null?"":data[7].toString());
				lowDoc.setFwjg(data[8]==null?"":data[8].toString());
//				lowDoc.setAtts(atts);
//				lowDoc.setAttExts(attExts);
				lowDoc.setInstanceId(instanceId);
				lowDoc.setProcessId(data[10]==null?"":data[10].toString());
				lowDoc.setReceiveId(data[11]==null?"":data[11].toString());
				lowDoc.setToExchangePath(data[12]==null?"":data[12].toString());
				lowDoc.setWh(data[13]==null?"":data[13].toString());
				lowDoc.setSenderId(data[15]==null?"":data[15].toString());
				lowDoc.setDyfs(data[16]==null?"":data[16].toString());
				
				returnList.add(lowDoc);
			}
		}else{
			sql = "";
			sql_same = "";
			
			sql_same += "select " +
						" distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss')," +
						" t.pinstanceid ," +
						" t.applydate," +
						" t.todepartid" +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where 1=1 "+
						" and (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid in ( "+todepartId+"))) " +
						" and i.id = p.wf_item_uid " +
						" and (p.is_back != '2' or p.is_back is null)" +
						" and t.instanceid=s.instanceid  ";
						 
			// 多个部门
			if(status == 1){
				sql = sql_same +
								" and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2))";
				
			}else{
				sql = sql_same +
								" and t.status = "+status +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 )";
			}
			
			
			if(status!=null && status==1){
//				sql +=" order by t.isreback desc, t.recdate desc";
				sql +=" order by to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') desc";
			}else{
//				sql +=" order by t.applydate desc";
				sql +=" order by  to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss')  desc, t.applydate desc";
			}
			
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null
					&& pageIndex != -1 && pageSize != -1) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List<Object[]> list =  query.getResultList();
			// 循环list 查询数据
			
			for(int i=0; list!=null && i<list.size(); i++){
				if(list!=null  && list.size() > 0){
					Object[] data =(Object[]) list.get(i);
					sql = "";
					sql_same = "select " +
							" t.id," +
							" s.lwbt," +
							" s.lwh," +
							" s.gwlx," +
							" s.zsdw," +
							" s.csdw ," +
							" (select e.EMPLOYEE_NAME from zwkj_employee e where e.EMPLOYEE_GUID = t.fromuserid) as EMPLOYEE_NAME," +
							" to_char(s.fwsj, 'yyyy-MM-dd hh24:mi:ss')," +
							" s.lwdw," +
							" t.instanceid," +
							" p.wf_process_uid," +
							" t.id," +
							" t.toExchangePath," +
							" v.wh, " +
							" i.VC_SXMC," +
							" t.fromuserid, " +
							" t.dyfs " +
							" from T_WF_CORE_RECEIVE t left join wh_view v on t.pinstanceid = v.instanceid,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
							" where 1=1 " +
							" and ( p.is_back != '2' or p.is_back is null)" +
							" and i.id = p.wf_item_uid" +
							" and t.instanceid=s.instanceid" +
							"";
					
					if(status == 1){
						sql = sql_same+  
								" and t.status != 0 " +
								" and (p.WF_PROCESS_UID = t.PROCESSID or p.WF_PROCESS_UID=t.fprocessid ) " +
								" and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"'" +
								" and t.pinstanceid = '"+data[1].toString()+"' " ;
						
					}else{
						sql = sql_same+  
								" and t.status ="+status +" " +
								" and (p.WF_PROCESS_UID = t.PROCESSID OR T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2  ) " +
								" and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"'" +
								" and t.pinstanceid = '"+data[1].toString()+"' " +
								" and  t.todepartid = '" + data[3].toString() +"'";
					}
					
					if(status!=null && status==1){
//						sql +=" order by t.isreback desc, t.recdate desc";
						sql +=" order by to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') desc";
					}else{
//						sql +=" order by t.applydate desc";
						sql +=" order by  to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss')  desc, t.applydate desc";
					}
					query = getEm().createNativeQuery(sql);
					List<Object[]> list2 =  query.getResultList();
					if(list2!=null && list2.size()>0){
						LowDoc lowDoc = null;
						data =(Object[]) list2.get(0);
						lowDoc = new LowDoc();
						
						String instanceId = data[9]==null?"":data[9].toString();
						lowDoc.setDocguid(data[0]==null?"":data[0].toString());
						lowDoc.setCebid("");
						lowDoc.setTitle(data[1]==null?"":data[1].toString());
						lowDoc.setJgdz(data[2]==null?"":data[2].toString());
						lowDoc.setFwnh(data[2]==null?"":data[2].toString());
						lowDoc.setFwxh(data[2]==null?"":data[2].toString());
						lowDoc.setKeyword("");
						lowDoc.setDoctype(data[3]==null?"":data[3].toString());
						lowDoc.setPriority((long) 1);
						String xto_all = data[4]==null?"":ClobToString.clob2String((SerializableClob) data[4]);
						if(CommonUtil.stringNotNULL(xto_all)){
							String[] xtos_all = xto_all.split("\\*");
							String[] xtoIds = xtos_all[0].split(",");
							String xto = "";
							for(String xtoId:xtoIds){
								xto += xtoId.substring(0,xtoId.lastIndexOf("}")+1)+",";
							}
							if(CommonUtil.stringNotNULL(xto)){
								xto = xto.substring(0,xto.length()-1);
							}
							lowDoc.setXto(xto);
							lowDoc.setXtoName(xtos_all[1]);
						}else{
							lowDoc.setXto("");
							lowDoc.setXtoName("");
						}
						String xcc_all = data[5]==null?"":ClobToString.clob2String((SerializableClob) data[5]);
						if(CommonUtil.stringNotNULL(xcc_all)){
							String[] xccs_all = xcc_all.split("*");
							String[] xccIds = xccs_all[0].split(",");
							String xcc = "";
							for(String xccId:xccIds){
								xcc += xccId.substring(0,xccId.lastIndexOf("}")+1)+",";
							}
							if(CommonUtil.stringNotNULL(xcc)){
								xcc = xcc.substring(0,xcc.length()-1);
							}
							lowDoc.setXcc(xcc);
							lowDoc.setXccName(xccs_all[1]);
						}else{
							lowDoc.setXcc("");
							lowDoc.setXccName("");
						}
						lowDoc.setSenderName(data[6]==null?"":data[6].toString());
						lowDoc.setSenderTime(data[7]==null?"":data[7].toString());
						lowDoc.setFwjg(data[8]==null?"":data[8].toString());
						lowDoc.setInstanceId(instanceId);
						lowDoc.setProcessId(data[10]==null?"":data[10].toString());
						lowDoc.setReceiveId(data[11]==null?"":data[11].toString());
						lowDoc.setToExchangePath(data[12]==null?"":data[12].toString());
						lowDoc.setWh(data[13]==null?"":data[13].toString());
						lowDoc.setSenderId(data[15]==null?"":data[15].toString());
						lowDoc.setDyfs(data[16]==null?"":data[16].toString());
						
						returnList.add(lowDoc);
					}
				}
			}
		}
		return returnList;
	}
	
	@Override
	public List<DoFileReceive> getReceiveAllList(String userId,
			Integer pageIndex, Integer pageSize, Integer status,Map<String,String> map){
		String wfTitle = map.get("wfTitle");
		String itemName = map.get("itemName");
		String todepartId = map.get("departId");
		String lwh = map.get("lwh");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String lwdw = map.get("lwdw");
		//1,表示已收
		String sql = "";
		List<DoFileReceive> returnList = new ArrayList<DoFileReceive>();
		//去除重复的（子部门）
		List<DoFileReceive> newDofileList = new ArrayList<DoFileReceive>();
		if(todepartId.indexOf("','") == -1){
			String sql_same = "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME," +
					"to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') as applydate," +
					" p.process_title," +
					" i.VC_SXMC," +
					" t.PROCESSID," +
					" t.id," +
					" p.wf_item_uid," +
					" p.wf_form_id," +
					" t.receive_type," +
					" s.lwbt," +
					" s.lwdw," +
					" s.yfdw," +
					" s.lwh," +
					" to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss')," +
					" s.gwlx," +
					" t.pdfpath," +
					" t.pinstanceid," +
					" t.isinvalid," +
					" t.jrdb," +
					" t.instanceid ," +
					" t.status ," +
					" t.isreback as isreback," +
					" to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate," +
					" t.dyfs," +
					" t.ydyfs, " +
					" t.updatetype as updatetype ";
			sql +=  " select * from ("+sql_same +
					" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s "+
					" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid = "+todepartId+"))" +
					" and (p.is_back is null or p.is_back != '2') " +
					" and i.id = p.wf_item_uid" +
					" and t.instanceid=s.instanceid " +
					" and (t.updatetype != 2 or t.updatetype is null) ";
			if(status == 1){
				sql +=  " and t.status != 0 " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) " ;
			}else{
				sql +=  " and t.status ="+status +"" +
						" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2))";
			}
			
			//sql相同条件
			String sql_con = "";
			String rep_title = "";
			if(CommonUtil.stringNotNULL(wfTitle)){
				rep_title = wfTitle.replaceAll("%", "\\\\%");
				rep_title = rep_title.replace("_", "\\_");
				sql_con += " and s.lwbt like :title ";
				if(rep_title!=null && !rep_title.equals(wfTitle)){
					sql_con += " escape '\\'";
				}
			}
			
			String rep_itemName = "";
			if(CommonUtil.stringNotNULL(itemName)){
				rep_itemName = itemName.replaceAll("%", "\\\\%");
				rep_itemName = rep_itemName.replace("_", "\\_");
				sql_con += " and i.VC_SXMC like :itemName ";
				if(rep_itemName!=null && !rep_itemName.equals(itemName)){
					sql_con += " escape '\\'";
				}
			}
			
			String rep_lwh = "";
			if(CommonUtil.stringNotNULL(lwh)){
				rep_lwh = lwh.replaceAll("%", "\\\\%");
				rep_lwh = rep_lwh.replace("_", "\\_");
				sql_con += " and s.lwh like :lwh ";
				if(rep_lwh!=null && !rep_lwh.equals(lwh)){
					sql_con += " escape '\\'";
				}
			}
			
			String rep_lwdw = "";
			if(CommonUtil.stringNotNULL(lwdw)){
				rep_lwdw = lwdw.replaceAll("%", "\\\\%");
				rep_lwdw = rep_lwdw.replace("_", "\\_");
				sql_con += " and s.lwdw like :lwdw ";
				if(rep_lwdw!=null && !rep_lwdw.equals(lwdw)){
					sql_con += " escape '\\'";
				}
			}
			
			if (startTime!=null && !"".equals(startTime)) {
				sql_con += " and s.fwsj>= to_date('" + startTime+" 00:00:00"+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			
			if(endTime!=null && !endTime.equals("")){
				sql_con += " and s.fwsj<= to_date('" + endTime+" 23:59:59"+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			
			sql += sql_con + "";
			
			sql += " union all " +
					sql_same +
					" from T_WF_CORE_RECEIVE t left join " +
					" t_wf_process p on t.instanceid=p.wf_instance_uid" +
					" left join t_wf_core_item i on i.id=p.wf_item_uid, t_wf_core_sw   s"+
					" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid = "+todepartId+"))" +
					" and t.instanceid=s.instanceid " +
					" and t.updatetype='2' ";	
			if(status == 1){
				sql  += " and t.status != 0 ";
			}else{
				sql +=   " and t.status ="+status;
			}
			sql += sql_con ;
			
			sql +=  ")";
				
			if(status!=null && status==1){
				sql +=" order by recdate desc";
			}else{
				sql +=" order by to_date(applydate,'yyyy-MM-dd hh24:mi:ss') desc";
			}
				
			Query query = getEm().createNativeQuery(sql);
			
			if(CommonUtil.stringNotNULL(wfTitle) ){
				query.setParameter("title", "%"+rep_title+"%");
			}
			
			if(CommonUtil.stringNotNULL(itemName) ){
				query.setParameter("itemName", "%"+rep_itemName+"%");
			}
			if(CommonUtil.stringNotNULL(lwh) ){
				query.setParameter("lwh", "%"+rep_lwh+"%");
			}
			if(CommonUtil.stringNotNULL(lwdw) ){
				query.setParameter("lwdw", "%"+rep_lwdw+"%");
			}
			
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			List<Object[]> list =  query.getResultList();
			DoFileReceive doFileReceive = null;
			for(int i=0; list!=null && i<list.size(); i++){
				Object[] data =(Object[]) list.get(i);
				doFileReceive = new DoFileReceive();
				String employeeName = data[0].toString();
				doFileReceive.setEmployeeName(employeeName);
				doFileReceive.setApplyDate(convertStringToDate(data[1]));
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
				doFileReceive.setFwsj(convertStringToDate(data[13]));
				doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
				doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
				doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
				doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
				doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
				doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
				doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
				doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
				doFileReceive.setRecDate(convertStringToDate(data[22]));
				doFileReceive.setDyfs(convertStringToInt(data[23]));
				doFileReceive.setYdyfs(convertStringToInt(data[24]));
				doFileReceive.setUpdateType(data[25]==null?0:Integer.parseInt(data[25].toString()));
				returnList.add(doFileReceive);
			}
			newDofileList = returnList;
		}else{
			String sql_same = "select " +
					" distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') as applydate," +
					" t.pinstanceid ,";
					
			sql = "";		
			sql +=  " select * from ("+sql_same ;
			// 多个部门
			if(status == 1){
				sql += 
						" t.isreback," +
						" t.recdate " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")" +
						" or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid in ("+todepartId+")))" +
						" and t.status != 0 " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2))" +
						" and i.id = p.wf_item_uid" +
						" and (p.is_back != '2' or p.is_back is null)" +
						" and t.instanceid=s.instanceid "+
						" and (t.updatetype != 2 or t.updatetype is null) ";
				
			}else{
				sql += 
						" '' ," +
						" t.todepartid" +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")" +
						" or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid in ( "+todepartId+")))" +
						" and t.status = "+status
						+" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2) )" +
						" and i.id = p.wf_item_uid" +
						" and (p.is_back != '2' or p.is_back is null)" +
						" and t.instanceid=s.instanceid  "+
						" and (t.updatetype != 2 or t.updatetype is null) ";
				
			}
			
			//sql相同条件
			String sql_con = "";
			String rep_title = "";
			if(CommonUtil.stringNotNULL(wfTitle)){
				rep_title = wfTitle.replaceAll("%", "\\\\%");
				rep_title = rep_title.replace("_", "\\_");
				sql_con += " and s.lwbt like :title ";
				if(rep_title!=null && !rep_title.equals(wfTitle)){
					sql_con += " escape '\\'";
				}
			}
			

	
	
			String rep_itemName = "";
			if(CommonUtil.stringNotNULL(itemName)){
				rep_itemName = itemName.replaceAll("%", "\\\\%");
				rep_itemName = rep_itemName.replace("_", "\\_");
				sql_con += " and i.VC_SXMC like :itemName ";
				if(rep_itemName!=null && !rep_itemName.equals(itemName)){
					sql_con += " escape '\\'";
				}
			}
			
			String rep_lwh = "";
			if(CommonUtil.stringNotNULL(lwh)){
				rep_lwh = lwh.replaceAll("%", "\\\\%");
				rep_lwh = rep_lwh.replace("_", "\\_");
				sql_con += " and s.lwh like :lwh ";
				if(rep_lwh!=null && !rep_lwh.equals(lwh)){
					sql_con += " escape '\\'";
				}
			}
			
			String rep_lwdw = "";
			if(CommonUtil.stringNotNULL(lwdw)){
				rep_lwdw = lwdw.replaceAll("%", "\\\\%");
				rep_lwdw = rep_lwdw.replace("_", "\\_");
				sql_con += " and s.lwdw like :lwdw ";
				if(rep_lwdw!=null && !rep_lwdw.equals(lwdw)){
					sql_con += " escape '\\'";
				}
			}
			
			if (startTime!=null && !"".equals(startTime)) {
				sql_con += " and s.fwsj>= to_date('" + startTime+" 00:00:00"+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			
			if(endTime!=null && !endTime.equals("")){
				sql_con += " and s.fwsj<= to_date('" + endTime+" 23:59:59"+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			
			sql += sql_con + "";
			
			sql += " union all " +
					sql_same ;
			if(status == 1){
				sql += 
						" t.isreback," +
						" t.recdate " +
						" from T_WF_CORE_RECEIVE t " +
						" left join t_wf_process p on t.instanceid = p.wf_instance_uid" +
						" left join t_wf_core_item i on i.id=p.wf_item_uid , t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") " +
								"or  t.todepartid in " +
								"(  select d.superior_guid from zwkj_department d " +
								" where d.department_guid in ("+todepartId+"))) " +
								" and t.status != 0 " +
								" and t.instanceid=s.instanceid "+
								" and t.updatetype='2' ";	
				
				
			}else{
				sql += 
						" '' ," +
						" t.todepartid" +
						" from T_WF_CORE_RECEIVE t" +
						" left join t_wf_process p on t.instanceid = p.wf_instance_uid" +
						" left join t_wf_core_item i on i.id=p.wf_item_uid,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")" +
								" or  t.todepartid in" +
								" (  select d.superior_guid from zwkj_department d" +
								" where d.department_guid in ( "+todepartId+")))" +
								" and t.status = "+status+
								" and t.instanceid=s.instanceid  "+
								" and t.updatetype='2' ";
			}
			
			sql += sql_con ;
			
			sql += ")";
			
			if(status!=null && status==1){
				sql +=" order by recdate desc";
			}else{
				sql +=" order by to_date(applydate,'yyyy-MM-dd hh24:mi:ss') desc";
			}
			
			Query query = getEm().createNativeQuery(sql);
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			
			if(CommonUtil.stringNotNULL(wfTitle) ){
				query.setParameter("title", "%"+rep_title+"%");
			}
			
			if(CommonUtil.stringNotNULL(itemName) ){
				query.setParameter("itemName", "%"+rep_itemName+"%");
			}
			if(CommonUtil.stringNotNULL(lwh) ){
				query.setParameter("lwh", "%"+rep_lwh+"%");
			}
			if(CommonUtil.stringNotNULL(lwdw) ){
				query.setParameter("lwdw", "%"+rep_lwdw+"%");
			}
			List<Object[]> list =  query.getResultList();
			// 循环list 查询数据
			
			for(int i=0; list!=null && i<list.size(); i++){
				if(list!=null  && list.size() > 0){
					sql = "";
					Object[] data =(Object[]) list.get(i);
					String sql_same_detail = "select (select e.EMPLOYEE_NAME from zwkj_employee e where  e.EMPLOYEE_GUID = t.fromuserid  ) as EMPLOYEE_NAME," +
							"to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') as applydate," +
							" p.process_title," +
							" i.VC_SXMC," +
							" t.PROCESSID," +
							" t.id," +
							" p.wf_item_uid," +
							" p.wf_form_id," +
							" t.receive_type," +
							" s.lwbt," +
							" s.lwdw," +
							" s.yfdw," +
							" s.lwh," +
							" to_char(s.fwsj,'yyyy-MM-dd hh24:mi:ss')," +
							" s.gwlx," +
							" t.pdfpath," +
							" t.pinstanceid," +
							" t.isinvalid," +
							" t.jrdb," +
							" t.instanceid ," +
							" t.status ," +
							" t.isreback as isreback," +
							" to_char(t.recdate,'yyyy-MM-dd hh24:mi:ss') as recdate," +
							" t.dyfs," +
							" t.ydyfs, " +
							" t.updatetype as updatetype ";
					sql +=  " select * from ("+sql_same_detail +
							" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s " +
							" where t.pinstanceid = '"+data[1].toString()+"'" +
							" and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"'" +
							" and ( p.is_back != '2' or p.is_back is null)"+
							" and i.id = p.wf_item_uid" +
							" and t.instanceid=s.instanceid "+
							" and (t.updatetype != 2 or t.updatetype is null) ";
					if(status == 1){
						sql +=	" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2))" +
								" and t.status != 0" ;
					}else{
						sql +=  " and  t.todepartid = '" + data[3].toString() +"'"+
								" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2 ) )" +
								" and t.status ="+status ;
					}
					
					sql += sql_con + "";
					
					sql += " union all " +
							sql_same_detail +
							" from T_WF_CORE_RECEIVE t left join " +
							" t_wf_process p on t.instanceid=p.wf_instance_uid" +
							" left join t_wf_core_item i on i.id=p.wf_item_uid, t_wf_core_sw   s"+
							" where t.pinstanceid = '"+data[1].toString()+"'" +
							" and to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') = '"+data[0].toString()+"'" +
							" and t.instanceid=s.instanceid "+
							" and t.updatetype='2' ";	
					if(status == 1){
						sql  += " and t.status != 0 ";
					}else{
						sql +=  " and  t.todepartid = '" + data[3].toString() +"'"+
								" and t.status ="+status;
					}
					sql += sql_con ;
					
					sql += ")";
					
					if(status!=null && status==1){
						sql +=" order by recdate desc";
					}else{
						sql +=" order by to_date(applydate,'yyyy-MM-dd hh24:mi:ss') desc";
					}
					query = getEm().createNativeQuery(sql);
					
					if(CommonUtil.stringNotNULL(wfTitle) ){
						query.setParameter("title", "%"+rep_title+"%");
					}
					
					if(CommonUtil.stringNotNULL(itemName) ){
						query.setParameter("itemName", "%"+rep_itemName+"%");
					}
					if(CommonUtil.stringNotNULL(lwh) ){
						query.setParameter("lwh", "%"+rep_lwh+"%");
					}
					if(CommonUtil.stringNotNULL(lwdw) ){
						query.setParameter("lwdw", "%"+rep_lwdw+"%");
					}
					
					List<Object[]> list2 =  query.getResultList();
					DoFileReceive doFileReceive = null;
					if(null!=list2&&list2.size()>0){
						data =(Object[]) list2.get(0);
						doFileReceive = new DoFileReceive();
						String employeeName = data[0].toString();
						doFileReceive.setEmployeeName(employeeName);
						doFileReceive.setApplyDate(convertStringToDate(data[1]));
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
						doFileReceive.setFwsj(convertStringToDate(data[13]));
						doFileReceive.setGwlx(data[14]==null?"":data[14].toString());
						doFileReceive.setPdfpath(data[15]==null?"":data[15].toString());
						doFileReceive.setpInstanceId(data[16]==null?"":data[16].toString());
						doFileReceive.setIsInvalid(data[17]==null?0:Integer.parseInt(data[17].toString()));
						doFileReceive.setJrdb(data[18]==null?0:Integer.parseInt(data[18].toString()));
						doFileReceive.setInstanceId(data[19]==null?"":data[19].toString());
						doFileReceive.setStatus(data[20]==null?0:Integer.parseInt(data[20].toString()));
						doFileReceive.setIsReback(data[21]==null?0:Integer.parseInt(data[21].toString()));
						doFileReceive.setRecDate(convertStringToDate(data[22]));
						doFileReceive.setDyfs(convertStringToInt(data[23]));
						doFileReceive.setYdyfs(convertStringToInt(data[24]));
						doFileReceive.setUpdateType(data[25]==null?0:Integer.parseInt(data[25].toString()));
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

	public List<String> getDelPendingList(String conditionSql,String userId, Integer pageIndex,Integer pageSize) {
		StringBuffer querySql = (new StringBuffer("select "))
				.append("p.wf_instance_uid as wf_instance_uid ")
				.append(" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ")
				.append(" where p.user_uid= '").append(userId).append("' and p.isexchanging=0  and i.id = p.wf_item_uid ")
				.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
				.append(" and p.step_index = '1' and (p.fromnodeid = 'first' or p.is_back = 1) and p.wf_f_instance_uid is null ")
			    .append(conditionSql)
				.append(" order by i.i_index ASC , p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString());
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public int getCountOfDelPending(String conditionSql,String userId,String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p ,T_WF_CORE_ITEM i ,zwkj_department d ")
		.append(" where p.user_uid= '").append(userId)
		.append("' and  p.isexchanging=0 and i.id=p.wf_item_uid and (p.action_status is null or p.action_status != 2)")
		.append(condition).append(conditionSql);
		query.append(" and p.is_over='NOT_OVER' and p.is_show=1 and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			.append(" and p.userdeptid = d.department_guid ");
		query.append(" and p.step_index = '1' and (p.fromnodeid = 'first' or p.is_back = 1) and p.wf_f_instance_uid is null ");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	
	@Override
	public int getReceiveAllCount(String userId, Integer status, Map<String,String> map) {
		String wfTitle = map.get("wfTitle");
		String itemName = map.get("itemName");
		String todepartId = map.get("departId");
		String lwh = map.get("lwh");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String lwdw = map.get("lwdw");
		String sql = "";
		
		String sql_con = "";
		String rep_title = "";
		if(CommonUtil.stringNotNULL(wfTitle)){
			rep_title = wfTitle.replaceAll("%", "\\\\%");
			rep_title = rep_title.replace("_", "\\_");
			sql_con += " and s.lwbt like :title ";
			if(rep_title!=null && !rep_title.equals(wfTitle)){
				sql_con += " escape '\\'";
			}
		}
		
		String rep_itemName = "";
		if(CommonUtil.stringNotNULL(itemName)){
			rep_itemName = itemName.replaceAll("%", "\\\\%");
			rep_itemName = rep_itemName.replace("_", "\\_");
			sql_con += " and i.VC_SXMC like :itemName ";
			if(rep_itemName!=null && !rep_itemName.equals(itemName)){
				sql_con += " escape '\\'";
			}
		}
		
		String rep_lwh = "";
		if(CommonUtil.stringNotNULL(lwh)){
			rep_lwh = lwh.replaceAll("%", "\\\\%");
			rep_lwh = rep_lwh.replace("_", "\\_");
			sql_con += " and s.lwh like :lwh ";
			if(rep_lwh!=null && !rep_lwh.equals(lwh)){
				sql_con += " escape '\\'";
			}
		}
		
		String rep_lwdw = "";
		if(CommonUtil.stringNotNULL(lwdw)){
			rep_lwdw = lwdw.replaceAll("%", "\\\\%");
			rep_lwdw = rep_lwdw.replace("_", "\\_");
			sql_con += " and s.lwdw like :lwdw ";
			if(rep_lwdw!=null && !rep_lwdw.equals(lwdw)){
				sql_con += " escape '\\'";
			}
		}
		
		if (startTime!=null && !"".equals(startTime)) {
			sql_con += " and s.fwsj>= to_date('" + startTime+" 00:00:00"+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		
		if(endTime!=null && !endTime.equals("")){
			sql_con += " and s.fwsj<= to_date('" + endTime+" 23:59:59"+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		
		// 一个部门
		if(todepartId.indexOf("','") == -1){
			String sql_same = " select " +
					" distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') as applydate," +
					" t.instanceid  ";
			sql +=  " select count(1) from ("+sql_same +
					" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i,t_wf_core_sw s "+
					" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid = "+todepartId+"))" +
					" and (p.is_back is null or p.is_back != '2') " +
					" and i.id = p.wf_item_uid" +
					" and t.instanceid=s.instanceid " +
					" and (t.updatetype != 2 or t.updatetype is null) ";
			if(status == 1){
				sql +=  " and t.status != 0 " +
						" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2)) " ;
			}else{
				sql +=  " and t.status ="+status +"" +
						" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2))";
			}
			
			
			sql += sql_con + "";
			
			sql += " union all " +
					sql_same +
					" from T_WF_CORE_RECEIVE t left join " +
					" t_wf_process p on t.instanceid=p.wf_instance_uid" +
					" left join t_wf_core_item i on i.id=p.wf_item_uid, t_wf_core_sw   s"+
					" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid = "+todepartId+"))" +
					" and t.instanceid=s.instanceid " +
					" and t.updatetype='2' ";	
			if(status == 1){
				sql  += " and t.status != 0 ";
			}else{
				sql +=   " and t.status ="+status;
			}
			sql += sql_con ;
			
			sql +=  ")";
					
		}else{
			
			//sql相同条件
			sql = "";		
			String sql_same = "select " +
					" distinct to_char(t.applydate,'yyyy-MM-dd hh24:mi:ss') as applydate," +
					" t.pinstanceid ,";
			
			sql +=  " select count(1) from ("+sql_same ;
			// 多个部门
			if(status == 1){
				sql += 
						" t.isreback," +
						" t.recdate " +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") " +
								"or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid in ("+todepartId+")))" +
								" and t.status != 0 " +
								" and ((p.WF_PROCESS_UID = t.PROCESSID )OR (T.FPROCESSID = p.WF_PROCESS_UID and p.step_index != 1 and t.receive_type =2))" +
								" and i.id = p.wf_item_uid" +
								" and (p.is_back != '2' or p.is_back is null)" +
								" and t.instanceid=s.instanceid " +
								" and (t.updatetype != 2 or t.updatetype is null) ";
				
			}else{
				sql += 
						" '' ," +
						" t.todepartid" +
						" from T_WF_CORE_RECEIVE t,t_wf_process p, t_wf_core_item i ,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")" +
						" or  t.todepartid in (  select d.superior_guid from zwkj_department d where d.department_guid in ( "+todepartId+")))" +
						" and t.status = "+status
						+" and ((p.WF_PROCESS_UID = t.PROCESSID) OR (T.FPROCESSID = p.WF_PROCESS_UID and t.receive_type =2) )" +
						" and i.id = p.wf_item_uid" +
						" and (p.is_back != '2' or p.is_back is null)" +
						" and t.instanceid=s.instanceid  "+
						" and (t.updatetype != 2 or t.updatetype is null) ";
				
			}
			
			sql += sql_con + "";
			
			sql += " union all " +
					sql_same ;
			if(status == 1){
				sql += 
						" t.isreback," +
						" t.recdate " +
						" from T_WF_CORE_RECEIVE t " +
						" left join t_wf_process p on t.instanceid = p.wf_instance_uid" +
						" left join t_wf_core_item i on i.id=p.wf_item_uid , t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+") " +
								"or  t.todepartid in " +
								"(  select d.superior_guid from zwkj_department d " +
								" where d.department_guid in ("+todepartId+"))) " +
								" and t.status != 0 " +
								" and t.instanceid=s.instanceid "+
								" and t.updatetype='2' ";	
				
				
			}else{
				sql += 
						" '' ," +
						" t.todepartid" +
						" from T_WF_CORE_RECEIVE t" +
						" left join t_wf_process p on t.instanceid = p.wf_instance_uid" +
						" left join t_wf_core_item i on i.id=p.wf_item_uid,t_wf_core_sw s  " +
						" where (t.touserid ='"+userId+"' or t.todepartid in ("+todepartId+")" +
								" or  t.todepartid in" +
								" (  select d.superior_guid from zwkj_department d" +
								" where d.department_guid in ( "+todepartId+")))" +
								" and t.status = "+status+
								" and t.instanceid=s.instanceid  "+
								" and t.updatetype='2' ";
			}
			
			sql += sql_con ;
			
			sql += ")";
			
		}

		Query query = getEm().createNativeQuery(sql);
		
		if(CommonUtil.stringNotNULL(wfTitle) ){
			query.setParameter("title", "%"+rep_title+"%");
		}
		
		if(CommonUtil.stringNotNULL(itemName) ){
			query.setParameter("itemName", "%"+rep_itemName+"%");
		}
		if(CommonUtil.stringNotNULL(lwh) ){
			query.setParameter("lwh", "%"+rep_lwh+"%");
		}
		if(CommonUtil.stringNotNULL(lwdw) ){
			query.setParameter("lwdw", "%"+rep_lwdw+"%");
		}
		
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<Integer> getStepIndex(String instanceId) {
		String sql = "select t.stepIndex from WfProcess t where t.wfInstanceUid=:instanceId and (t.isBack != '2' or t.isBack is null) " +
				"group by t.stepIndex,t.stepIndex order by t.stepIndex";
		Query query = getEm().createQuery(sql);
		query.setParameter("instanceId", instanceId);
		List<Integer> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> getProcessByStepIndex(String instanceId, Integer stepIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("select n.wfn_name,to_char(t.apply_time,'yyyy-mm-dd hh24:mi'),t.step_index,t.dotype,emp.employee_name,to_char(t.jssj,'yyyy-mm-dd hh24:mi'),")
		   .append(" to_char(t.finsh_time,'yyyy-mm-dd hh24:mi'),t.is_over,t.is_back,t.is_end,emp.employee_mobile,n.wfn_moduleid,n.wfn_id ")
		   .append(" from t_wf_process t,zwkj_employee emp,wf_node n ")
		   .append(" where t.user_uid = emp.employee_guid and t.wf_node_uid=n.wfn_id ")
		   .append(" and t.wf_instance_uid=:instanceId and t.step_index=:stepIndex ")
		   .append(" order by emp.tabindex ");
		Query query = getEm().createNativeQuery(sql.toString());
		query.setParameter("instanceId", instanceId);
		query.setParameter("stepIndex", stepIndex);
		return query.getResultList();
	}

	@Override
	public WfProcess getOverWfpByInstanceId(String instanceId){
		String sql = "select wf.* from t_wf_process wf where wf.step_index = (select distinct t.step_index from t_wf_process t where t.wf_instance_uid = '"+instanceId+"' and t.is_end='1') and wf.wf_instance_uid = '"+instanceId+"'";
		List<WfProcess> list = getEm().createNativeQuery(sql, WfProcess.class).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;	
		
	}

	@Override
	public int getCountCyDoFiles(String conditionSql, String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_wf_core_dofile t,t_wf_core_item i,t_wf_core_handround_ship h where h.instanceid=t.instanceid ");
		sb.append(" and (t.isdelete!=1 or t.isdelete is null) and t.item_id=i.id ");
		sb.append(" and h.userid=:userId ");
		if ( CommonUtil.stringNotNULL(conditionSql)){
			sb.append(conditionSql);
		}
		Query query = getEm().createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@Override
	public List<DoFile> getCyDoFileList(String conditionSql, String userId,Integer pageindex, Integer pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.* from t_wf_core_dofile t,T_WF_CORE_ITEM i,t_wf_core_handround_ship h where h.instanceid=t.instanceid ");
		sb.append(" and (t.isdelete!=1 or t.isdelete is  null) and t.item_id=i.id ");
		sb.append(" and h.userid=:userId ");
		if ( CommonUtil.stringNotNULL(conditionSql)){
			sb.append(conditionSql);
		}
		sb.append(" order by t.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		query.setParameter("userId", userId);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}

	@Override
	public int getFavFileCount(String userId, String instanceId) {
		String sql = "select decode(count(*), 0, 0, 1)"+
				   " from t_wf_core_dofile d, t_wf_core_dofile_favourite fav"+
				   " where d.instanceid = :instanceId"+
				   " and d.id = fav.dofileid"+
				   " and fav.userid = :userId";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("instanceId", instanceId);
		query.setParameter("userId", userId);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@Override
	public List<Object[]> findeWfps(String fromNodeId, String nodeId,
			String instanceId) {
		String sql = "select " +
				" (select emp.employee_name from zwkj_employee emp where emp.employee_guid = t.fromuserid) as fromusername " +
				",(select emp.employee_name from zwkj_employee emp where emp.employee_guid = t.user_uid) as username " +
				",t.apply_time,t.finsh_time" +
				" from t_wf_process t where t.wf_instance_uid=:instanceId and t.fromnodeid = :fromNodeId and t.wf_node_uid = :nodeId";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("instanceId", instanceId);
		query.setParameter("fromNodeId", fromNodeId);
		query.setParameter("nodeId", nodeId);
		return query.getResultList();
	}

	@Override
	public String findEntrustName(String instanceId) {
		String sql = "select max(a.empnames) from (select wm_concat(emp.employee_name) over(order by emp.tabindex asc) empNames from t_wf_process p, zwkj_employee emp where emp.employee_guid = p.user_uid and p.is_over = 'NOT_OVER' and p.wf_instance_uid = :instanceId) a";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("instanceId", instanceId);
		
		if(query.getResultList().size()==0){
			return "";
		}else{
			Object obj = query.getSingleResult();
			if(null != obj){
				return obj.toString();
			}else{
				return "";
			}
		}
	}
	
	@Override
	public List<Employee> findToSortEmployeeList(String allInstanceId,
			String nodeIds) {
		List<Employee> empList = new ArrayList<Employee>();
		try {
			String sql1 = " and emp.employee_guid in (select ims.employee_id "+
					" from t_wf_core_inneruser_map_user ims"+
					" where ims.inneruser_id in"+
					" (select inn.id"+
					" from wf_node node, t_wf_core_inneruser inn"+
					" where node.wfn_id in ("+nodeIds+")"+
					" and inn.is_sort = '1'"+
					" and  node.wfn_staff like '%'||inn.id||'%'))";
			
			String sql = "select distinct emp.employee_guid, emp.employee_name, emp.tabindex,dep.tabindex from zwkj_employee emp, zwkj_department dep "+ 
					" where emp.department_guid=dep.department_guid " +
					sql1+
					" order by dep.tabindex,emp.tabindex";
			List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
			
			Employee emp = null;
			Object[] data = null;
			for(int i=0; i<list.size(); i++){
				data = list.get(i);
				emp = new Employee();
				emp.setEmployeeGuid(data[0]!=null ?data[0].toString():"");
				emp.setEmployeeName(data[1]!=null ?data[1].toString():"");
				emp.setTabindex((long)i);
				empList.add(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@Override
	public WfProcess findLastProcess(String instanceId, String nodeId, String userId) {
		String hql = "from WfProcess where wfInstanceUid=:wfInstanceUid and nodeUid=:nodeUid";
		if(StringUtils.isNotBlank(userId)){
			hql += " and userUid=:userUid";
		}
		hql += " and isOver = 'OVER' and processTitle is not null and isShow = 1 ";
		hql += " and finshTime is not null and (isBack != '2' or isBack is null) ";
		hql += " order by finshTime desc";
		Query query = getEm().createQuery(hql);
		query.setParameter("wfInstanceUid", instanceId);
		query.setParameter("nodeUid", nodeId);
		if(StringUtils.isNotBlank(userId)){
			query.setParameter("userUid", userId);
		}
		List<WfProcess> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer countMyProcess(String instanceId, String userId) {
		String sql = "select count(*) from t_wf_process t where t.wf_instance_uid = :wf_instance_uid and t.user_uid = :user_uid and (t.is_back != '2' or t.is_back is null)";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("wf_instance_uid", instanceId);
		query.setParameter("user_uid", userId);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<DoFile> getDoFile(List<String> list) {
		String hql = "from DoFile d where d.doFile_id in (:doFile_id)";
		Query query = getEm().createQuery(hql);
		query.setParameter("doFile_id", list);
		return query.getResultList();
	}
	
	@Override
	public Integer getNoOwnerDofileCount(Map<String, String> map){
		String title = map.get("title");
		String departmentGuid = map.get("departmentGuid");
		
		String sql = "select count(*) " +
				" from t_wf_core_dofile t, t_wf_process p ,t_wf_core_item i" +
				" where t.instanceid = p.wf_instance_uid and p.is_over = 'OVER' and  p.is_end != '1'  and i.id = t.item_id " +
				" and ((select count(*) from  t_wf_process p2 where p2.is_over='NOT_OVER' and p2.wf_instance_uid=t.instanceid) = 0) " +
				" and p.step_index in (select max(p2.step_index) from t_wf_process p2 where p2.wf_instance_uid = t.instanceid) "+ 
				" and ((select count(*) from t_wf_process p2 where p2.is_end = '1' and p2.wf_instance_uid = t.instanceid) = 0) " +
				 " and p.apply_time in (select max(p2.apply_time) from t_wf_process p2 where p2.wf_instance_uid = t.instanceid) ";
		if(CommonUtil.stringNotNULL(title)){
			sql = sql + " and p.process_title like '%"+title+"%' ";
		}
		if(CommonUtil.stringNotNULL(departmentGuid)){
			sql = sql + " and i.vc_ssbmid = '"+ departmentGuid+"'";
		}
		Query query = getEm().createNativeQuery(sql);
		return Integer.valueOf(query.getSingleResult().toString());
	}
	
	@Override
	public List<Object[]> getNoOwnerDofileList(Map<String, String> map,  Integer pageindex, Integer pagesize){
		String title = map.get("title");
		String departmentGuid = map.get("departmentGuid");
		String sql = "select p.process_title, p.wf_process_uid, p.wf_instance_uid,p.is_over, p.is_end, p.step_index, p.finsh_time,  " +
				"(select e.employee_name from zwkj_employee e where e.employee_guid = p.user_uid) as userName, " +
				"  (select dept.department_name from zwkj_department dept where dept.department_guid = i.vc_ssbmid  ) as siteName" +
				" from t_wf_core_dofile t, t_wf_process p,t_wf_core_item i " +
				" where t.instanceid = p.wf_instance_uid and p.is_over = 'OVER' and  p.is_end != '1' and i.id = t.item_id " +
				" and ((select count(*) from  t_wf_process p2 where p2.is_over='NOT_OVER' and p2.wf_instance_uid=t.instanceid) = 0) " +
				" and p.step_index in (select max(p2.step_index) from t_wf_process p2 where p2.wf_instance_uid = t.instanceid) " + 
				" and ((select count(*) from t_wf_process p2 where p2.is_end = '1' and p2.wf_instance_uid = t.instanceid) = 0) " +
				" and p.apply_time in (select max(p2.apply_time) from t_wf_process p2 where p2.wf_instance_uid = t.instanceid) ";
		if(CommonUtil.stringNotNULL(title)){
			sql = sql + " and p.process_title like '%"+title+"%' ";
		}
		if(CommonUtil.stringNotNULL(departmentGuid)){
			sql = sql + " and i.vc_ssbmid = '"+ departmentGuid+"'";
		}
		Query query=super.getEm().createNativeQuery(sql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}
	
	public void saveAutoSend(AutoSend autosend) {
		if(autosend == null){
			return;
		}
		getEm().persist(autosend);
	}
	
	public String findWh(String instanceId) {
		String sql = "select w.WH from wh_view w where w.INSTANCEID='"+instanceId+"'  order by w.WH nulls last";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addRecallLog(WfRecallLog recallLog) {
		getEm().persist(recallLog);
	}
	
	public void saveDuBanLog(WfDuBanLog duBanLog){
		if(duBanLog==null){
			return;
		}
		getEm().persist(duBanLog);
	}

	@Override
	public  List<WfDuBanLog> getDuBanMsg() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.*" + 
				"  FROM t_wf_core_duban_log t" + 
				"  left join t_wf_process p" + 
				"    on t.INSTANCEID = p.wf_instance_uid" + 
				"   and t.processid = p.wf_process_uid" + 
				"  left join zwkj_employee e " + 
				"    on e.EMPLOYEE_GUID = t.employee_guid" +
				" where IS_OVER = 'NOT_OVER'" +
				" and not exists (select * " + 
				"          from t_wf_leader l " + 
				"         where l.employee_guid=t.employee_guid and l.siteid = e.siteid)");
		Query query=super.getEm().createNativeQuery(sb.toString(),WfDuBanLog.class);
		return query.getResultList();
	}

	@Override
	public List<String> selectOverProcessId(String instanceId) {
		String sql = "select t.wf_process_uid from t_wf_process t where  t.wf_instance_uid=:wf_instance_uid order by t.finsh_time,t.jssj nulls last";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("wf_instance_uid", instanceId);
		return query.getResultList();
	}

	@Override
	public Object[] getNewsProcessByInstanceid(String instanceId) {
		String hql = "select wfProcessUid,wfUid,nodeUid,wfInstanceUid,pdfPath,isEnd,itemId,allInstanceid from WfProcess where wfInstanceUid='"+instanceId+"' and (isBack!='2' or isBack is null) order by stepIndex desc";
		Query query=super.getEm().createQuery(hql);
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<WfProcess> findWfProcessListByIsOver(WfProcess wfp) {
		if(wfp==null){
			return null;
		}
		String instanceId = wfp.getWfInstanceUid();
		Integer stepIndex = wfp.getStepIndex();
		String nodeId = wfp.getNodeUid();
		String sql = "select t.wf_process_uid,t.is_over,t.user_uid from t_wf_process t where (t.is_back!='2' or t.is_back is null)  and t.wf_node_uid = '"+nodeId
				+"' and  t.step_Index ="+stepIndex+" and t.wf_instance_uid='"+instanceId+"'";
		List<Object[]> listTemp = getEm().createNativeQuery(sql).getResultList();
		List<WfProcess> list = new ArrayList<WfProcess>();
		if(listTemp!=null&&listTemp.size()>0){
			for(Object[] o:listTemp){
				WfProcess p = new WfProcess();
				p.setWfProcessUid(o[0]+"");
				p.setIsOver(o[1]+"");
				p.setUserUid(o[2]+"");
				list.add(p);
			}			
		}
		return list;
	}
	
	@Override
	public List<WfProcess> findWfProcessIdByInsIdAndStp(String instanceId,
			Integer stepIndex, String userId) {
		String sql = "select t.wf_process_uid,t.is_over from t_wf_process t where (t.is_back!='2' or t.is_back is null) " +
				" and t.step_Index ="+stepIndex +
				" and t.wf_instance_uid='"+instanceId+"'";
		if(StringUtils.isNotBlank(userId)){
			sql += " and t.user_uid = '"+userId+"'";
		}
		List<Object[]> listTemp = getEm().createNativeQuery(sql).getResultList();
		List<WfProcess> list = new ArrayList<WfProcess>();
		if(listTemp!=null&&listTemp.size()>0){
			for(Object[] o:listTemp){
				WfProcess p = new WfProcess();
				p.setWfProcessUid(o[0]+"");
				p.setIsOver(o[1]+"");
				list.add(p);
			}			
		}
		return list;
	}
	
	@Override
	public List<WfProcess> getStepByEntity(WfProcess wfProcess) {
		if(wfProcess==null ){
			return null;
		}
		String sql = "select t.stepIndex from WfProcess t where 1=1";
		if(wfProcess.getStepIndex()!=null){
			sql+= " and t.stepIndex = "+wfProcess.getStepIndex();
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getNodeUid())){
			sql+= " and t.nodeUid = '"+wfProcess.getNodeUid()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getIsOver())){
			sql+= " and t.isOver = '"+wfProcess.getIsOver()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getWfInstanceUid())){
			sql+= " and t.wfInstanceUid = '"+wfProcess.getWfInstanceUid()+"'";
		}
		
		if(CommonUtil.stringNotNULL(wfProcess.getfInstancdUid())){
			sql+= " and t.fInstancdUid = '"+wfProcess.getfInstancdUid()+"'";
		}
		sql+= " order by t.stepIndex desc";
		List<Integer> listTemp = getEm().createQuery(sql).getResultList();
		List<WfProcess> list = new ArrayList<WfProcess>();
		if(listTemp!=null&&listTemp.size()>0){
			for(Integer o : listTemp){
				WfProcess p = new WfProcess();
				p.setStepIndex(o);
				list.add(p);
			}
		}
		return list;
	}

	@Override
	public WfProcess findEndWfProcessByInstanceId(String instanceId) {
		String sql = " from WfProcess t where t.wfInstanceUid = :instanceId and t.isEnd = 1 ";
		Query query = getEm().createQuery(sql);
		query.setParameter("instanceId", instanceId);
		List<WfProcess> list = query.getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<WfProcess> findWfProcessAnyInfo(String workFlowId,String instanceId,String nodeId, Integer stepIndex){
		StringBuffer hql = new StringBuffer();
		hql.append(" select t.wfProcessUid,t.wfInstanceUid,t.wfUid,t.nodeUid,t.userUid,t.processTitle,t.stepIndex from WfProcess t ");
		hql.append(" where 1=1 ");
		if(StringUtils.isNotBlank(workFlowId)){
			hql.append(" and wfUid='"+workFlowId+"' ");
		}
		if(StringUtils.isNotBlank(instanceId)){
			hql.append(" and wfInstanceUid='"+instanceId+"' ");
		}
		if(StringUtils.isNotBlank(nodeId)){
			hql.append(" and nodeUid='"+nodeId+"' ");
		}
		if(null != stepIndex && !stepIndex.equals(0)){
			hql.append(" and stepIndex="+stepIndex);
		}
		hql.append(" and (isDuplicate!='true' or isDuplicate is null) ");
		hql.append(" order by sort desc");
		List<Object[]> listTemp = getEm().createQuery(hql.toString()).getResultList();
		List<WfProcess> list = null;
		if(listTemp!=null&&listTemp.size()>0){
			list = new ArrayList<WfProcess>();
			for(Object[] o:listTemp){
				WfProcess p = new WfProcess();
				p.setWfProcessUid(o[0]+"");
				p.setWfInstanceUid(o[1]+"");
				p.setWfUid(o[2]+"");
				p.setNodeUid(o[3]+"");
				p.setUserUid(o[4]+"");
				p.setProcessTitle(o[5]+"");
				p.setStepIndex(Integer.parseInt(o[6]+""));
				list.add(p);
			}
		}
		return list;
	}

	@Override
	public void updateIsShowByProcessId(String processId) {
		String sql = "update t_wf_process t set t.is_show=1 where t.wf_process_uid='"+processId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public String findNodeIdByProcessId(String processId) {
		String sql = " select t.wf_node_uid from t_wf_process t where t.wf_process_uid='"+processId+"'";
		return (String) getEm().createNativeQuery(sql).getSingleResult();
	}

	@Override
	public OfficeInfoView getOfficeInfoByInstanceId(String instanceId) {
		String sql = " select t.instanceid as instanceId," +
				"t.dwmc as dwmc," +
				"t.wjmc as wjmc," +
				"t.ngr as ngr," +
				"t.yfsj as yfsj," +
				"t.wh as wh "+
				" from wh_qrcode_info t where t.instanceId ='"+instanceId+"'";
		List<OfficeInfoView> list =  getEm().createNativeQuery(sql,"OfficeInfoView").getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Object[] findDuBanListByInsId(String instanceId) {
		String sql = " select t.instanceid,t.dblx,t.dbsx from wh_qrcode_info t where t.instanceId ='"+instanceId+"' ";
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<DoFile> findDoFilesByIds(String ids) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select d.* from t_wf_core_dofile d where d.id in ("+ids+") order by d.dotime desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),DoFile.class);
		return query.getResultList();  
	}

	@Override
	public int getCountOfOperateLog(String instanceId) {
		String sql = " select count(distinct processid) from t_wf_core_truejsonlog t where t.instanceid ='"+instanceId+"' and t.readorwrite ='1'";
		return ((BigDecimal)getEm().createNativeQuery(sql).getSingleResult()).intValue();
	}

	@Override
	public List<TrueOperateLog> findOperateLogList(String instanceId,Integer pageindex, Integer pagesize) {
		String sql = " select processid, readorwritedate, emp.employee_name  from " +
				"(select distinct t.processid,to_char(max(t.readorwritedate),'yyyy/MM/dd hh24:mi:ss') as readorwritedate,t.userid from t_wf_core_truejsonlog t" +
				" where t.instanceid = '"+instanceId+"' and t.readorwrite = '1' group by t.processid, t.userid) tem" +
				" left join zwkj_employee emp on tem.userid = emp.employee_guid order by readorwritedate";
		Query query = getEm().createNativeQuery(sql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize); 
		}
		List<Object[]> listObj = query.getResultList();
		if (listObj != null && listObj.size() > 0) {
			List<TrueOperateLog> list = new ArrayList<TrueOperateLog>();
			TrueOperateLog log = null;
			int i=0;
			for (Object[] obj : listObj) {
				log = new TrueOperateLog();
				String openTime = getOpenTimeLogByPid((String) obj[0],(String) obj[1]);
				if(StringUtils.isBlank(openTime)){
					openTime = "使用一键签批";
				}
				if(i==0){
					openTime = (String) obj[1];
				}
				i++;
				log.setOpenTime(openTime);
				log.setOperateTime((String) obj[1]);
				log.setUserName((String) obj[2]);
				list.add(log);
			}
			return list;
		}
		return null;
	}

	private String getOpenTimeLogByPid(String processId,String operateTime) {
		String sql = " select to_char(max(t.readorwritedate),'yyyy/MM/dd hh24:mi:ss') from t_wf_core_truejsonlog t" +
				" where t.readorwrite='0' and t.processid='"+processId+"' " +
						" and t.readorwritedate<to_date('"+operateTime+"','yyyy/MM/dd hh24:mi:ss')";
		return (String) getEm().createNativeQuery(sql).getSingleResult();
	}
	
	@Override
	public List<WfProcess> findWfProcessByInsAndIndex(String instanceId, Integer stepIndex) {
		String sql = "select t.wf_process_uid,t.wf_node_uid from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.step_index in ("+stepIndex+","+(stepIndex+1)+")";
		List<Object[]> listTemp = getEm().createNativeQuery(sql).getResultList();
		if(listTemp!=null && listTemp.size()>0){
			List<WfProcess> list = new ArrayList<WfProcess>();
			WfProcess wfp = null;
			for(Object[] obj : listTemp){
				wfp = new WfProcess();
				wfp.setWfProcessUid((String) obj[0]);
				wfp.setNodeUid((String) obj[1]);
				list.add(wfp);
			}
			return list;
		}
		return null;
	}
	
	public void updateOverByNodeId(String workFlowId,String instanceId,String nodeId ){
		Date date = new Date((new Date()).getTime()+1000);
		StringBuffer hql = new StringBuffer();
		hql.append(" update WfProcess t set t.isOver = :isOver, t.finshTime = :finshTime ");
		hql.append(" where t.wfUid = :workFlowId and t.wfInstanceUid = :instanceId and t.nodeUid = :nodeId ")
		.append(" and (t.isDuplicate!='true' or t.isDuplicate is null) ");
		Query query = getEm().createQuery(hql.toString());
		query.setParameter("isOver", Constant.OVER);
		query.setParameter("finshTime", date);
		query.setParameter("workFlowId", workFlowId);
		query.setParameter("instanceId", instanceId);
		query.setParameter("nodeId", nodeId);
		query.executeUpdate();
	}

	@Override
	public BigDecimal findMaxStepIndexByNodeId(String instanceId,
			String nextNodeId) {
		String sql = " select max(t.step_index) from t_wf_process t where t.wf_instance_uid ='"+instanceId+"' and t.wf_node_uid = '"+nextNodeId+"'";
		return (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
	}

	@Override
	public String getApplyTimeByInsId(String instanceId) {
		/*String sql = " select to_char(t.apply_time,'yyyy-MM-dd hh24:mi:ss') from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.step_index='1'";
		return (String) getEm().createNativeQuery(sql).getSingleResult();*/
		String sql = " select to_char(t.apply_time,'yyyy-MM-dd hh24:mi:ss') from t_wf_process t where  t.wf_instance_uid  in (select t2.allinstanceid from t_wf_process t2 where t2.wf_instance_uid = '"+instanceId+"') and t.step_index='1' order by t.apply_time asc ";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return "";
	}

	@Override
	public List<String> queryMultDeptByEmpId(String userId) {
		try{
		String sql = " select t.deptid from t_wf_emp_multdept_relation t where t.empid='"+userId+"'";
		return getEm().createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String querySiteIdByDeptId(String deptId) {
		String sql = " select t.* from zwkj_department t where t.issite='1' start with t.department_guid ='"+deptId+"' connect by prior t.superior_guid = t.department_guid  ";
		Query query = getEm().createNativeQuery(sql,Department.class);
		List<Department> list = query.getResultList();
		if(list!=null && list.size()>0){
			return list.get(0).getDepartmentGuid();
		}
		return null;
	}

	@Override
	public List<WfProcess> findPendingByInsIdAndUserId(String instanceId,
			String userId) {
		String sql = " select * from t_wf_process t where t.wf_instance_uid='"+instanceId+"' and t.user_uid='"+userId+"' and t.is_over = 'NOT_OVER' ";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<Employee> findAllLeaveEmps() {
		String sql = " select * from zwkj_employee t where t.isleave='1' ";
		return getEm().createNativeQuery(sql,Employee.class).getResultList();
	}

	@Override
	public EmployeeLeave findEmpLeave(Employee emp) {
		String hql = " from EmployeeLeave where userId=:userId";
		List<EmployeeLeave> list =getEm().createQuery(hql).setParameter("userId", emp.getEmployeeGuid()).getResultList(); 
		return (list!=null && list.size()>0 )?list.get(0):null;
	}

	@Override
	public void saveEmloyeeLeave(EmployeeLeave employeeLeave) {
		if(employeeLeave==null){
			return;
		}
		getEm().persist(employeeLeave);
	}

	@Override
	public List<WfProcess> findPendingOfUserId(String userId, int fileType) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.* from t_wf_process t where t.user_uid = :userId and t.is_over = 'NOT_OVER' and t.wf_node_uid in" +
				" (select n.wfn_id from wf_node n where n.wfn_route_type = '4') and t.wf_item_uid in(select i.id from t_wf_core_item i where i.vc_sxlx=:fileType) ");
		Query query = getEm().createNativeQuery(sb.toString(),WfProcess.class);
		query.setParameter("userId", userId);
		query.setParameter("fileType", fileType);
		return query.getResultList();
	}

	@Override
	public void deleteWhOfFw(String instanceId) {
		String sql = "delete from t_wf_core_wh_fw where instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void deleteWhOfBw(String instanceId) {
		String sql = "delete from t_wf_core_wh_bw where instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public String findJjcd(String instanceId) {
		String sql = "select w.jjcd from wh_view w where w.INSTANCEID='"+instanceId+"' order by w.jjcd nulls last ";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer getCountEndInstance(String instanceId) {
		String sql = " select count(1) from t_wf_core_end_instanceid t where t.instanceid='"+instanceId+"' ";
		BigDecimal count = (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
		return count!=null?count.intValue():0;
	}

	@Override
	public String getViewBhByInstanceId(String instanceId) {
		String sql = "select w.bh from wh_view w where w.INSTANCEID='"+instanceId+"' order by w.bh nulls last";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String findLwdw(String instanceId) {
		String sql = "select w.lwdw from wh_view w where w.INSTANCEID='"+instanceId+"'  order by w.lwdw nulls last ";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Object[] getViewInfoByInstanceId(String instanceId) {
		String sql = " select w.swlx,w.sfzddb,w.dblx,w.dbsx  from wh_fgw_view w where w.instanceid='"+instanceId+"'";
		List<Object[]> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
