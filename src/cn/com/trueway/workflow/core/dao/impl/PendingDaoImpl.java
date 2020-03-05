package cn.com.trueway.workflow.core.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.SerializableClob;
import org.json.JSONException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.util.ClobToString;
import cn.com.trueway.workflow.set.util.WebSocketUtil;

@SuppressWarnings("unchecked")
public class PendingDaoImpl extends BaseDao implements PendingDao {
	
	public List<Pending> getPendingList(String conditionSql,String userId, Integer pageIndex,Integer pageSize) {
		StringBuffer querySql = (new StringBuffer("select p.jssj as jssj,"))
				.append("p.wf_process_uid as wf_process_uid,")
				.append("p.wf_node_uid as wf_node_uid,")
				.append("(select t.wfn_name from WF_NODE t where t.wfn_id=p.wf_node_uid) as nodeName,")
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
				.append("(select e.employee_name from zwkj_employee e where e.employee_guid = p.ENTRUSTUSERID) as entrust_name,")
				.append("p.is_over as is_over,")
				.append(" ( case when p.JDQXDATE <= sysdate then '3' else p.status end) as status, ")
				//p.is_end as isEnd,  办结这一步取pdf 
				.append("p.is_end as isEnd,")
				.append("p.is_master as isMaster,")
				.append("p.pdfPath as pdfPath,")
				.append("'' as commentJson,")
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
				.append(" (select decode(count(*), 0, 0, 1) from t_wf_core_dofile d, t_wf_core_dofile_favourite fav where (p.wf_instance_uid = d.instanceid  or p.wf_f_instance_uid = d.instanceid ) and d.id = fav.dofileid and fav.userid='"+userId+"') as favourite, ")
				.append("p.isManyInstance as isManyInstance ,d.department_name as userDeptId ")
				.append(" ,do.id as dofileId ")
				.append(" ,replace(replace(replace(replace(v.jjcd, '特急', '3'), '急', '2'),'一般',''),' ','') as urgency")
				.append(" ,v.wh as siteName")//nj需求：在待办，已办，已办结，公文流转页面新增文号，在此用siteName先存文号内容避免大改动
				.append(" from t_wf_core_dofile do, T_WF_CORE_ITEM i ," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid" +
						" left join wh_view v on p.wf_instance_uid=v.instanceid," +
						" wf_node n ")
				.append(" where p.allinstanceid = do.instanceid and p.user_uid = '").append(userId).append("' and p.isexchanging=0 " +
						" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append(" and (p.isrepeated != 1 or p.isrepeated is null) and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(conditionSql)
				.append(" order by urgency desc nulls last,i.itemsort nulls last,p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	public int getCountOfPending(String conditionSql,String userId,String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p " +
				" left join wh_view v on p.wf_instance_uid=v.instanceid ," +
				"T_WF_CORE_ITEM i ,zwkj_department d ,wf_node n ")
		.append(" where p.user_uid= '").append(userId)
		.append("' and  p.isexchanging=0" +
				" and i.id=p.wf_item_uid" +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')" +
				" and (p.action_status is null or p.action_status != 2)")
		.append(condition).append(conditionSql);
		query.append(" and (p.isrepeated != 1 or p.isrepeated is null) and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			.append(" and p.userdeptid = d.department_guid ");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}
	
	public int getCountOfPending(String conditionSql) {
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p  ,wf_node n  ")
		.append(" where 1=1 ")
		.append(" and  p.isexchanging=0 " +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')" +
				" and (p.action_status is null or p.action_status != 2)")
		.append(conditionSql);
		query.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
		
	}
	
	public int getCountOfPending(String conditionSql,String userId,String type,String itemid) {
		String itemids = "'"+itemid.replaceAll(",", "','")+"'";
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p ,T_WF_CORE_ITEM i ")
		.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid ")
		.append(condition).append(conditionSql);
		 if(itemids != null && !"".equals(itemids)){
		    	query.append( " and p.wf_item_uid in (")
					.append(itemids)
					.append(") ");
			}
		query.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}

	public WfProcess getProcessByID(String processId) {
		String hql = "from WfProcess p where p.wfProcessUid='"+processId+"'";
		List<WfProcess> list = super.getEm().createQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public WfMain getWfMainById(String id) {
		String hql="from WfMain w where wfm_id='"+id+"'";
		Query query=super.getEm().createQuery(hql);
		return (WfMain) query.getResultList().get(0);
	}

	public WfNode getWfNode(String nodeid) {
		return super.getEm().find(WfNode.class, nodeid);
	}
	
	public Date getEndDate(int count,Date beginDate){
		if(count<0){
			String end_date = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_date = sdf.format(beginDate); 
			StringBuffer sql = new StringBuffer();
			//获取比begin_date的全部时间按照升序排列,获取前count条
			sql.append("select time from (");
			sql.append("select time from T_WF_CORE_WORKDAY where time<='"+begin_date+"'");
			sql.append(" order by time desc) ");
			sql.append(" where  rownum<="+(count*-1));
			List<String> list = super.getEm().createNativeQuery(sql.toString()).getResultList();
			Date date = beginDate;
			try {
				if(list.size()>0){
					int size = list.size();
					end_date = list.get(size-1);
//					end_date += " 23:59:59";
					try{
						date = sdf2.parse(end_date);
					}catch (Exception e) {
						end_date += new SimpleDateFormat(" HH:mm:ss").format(new Date(System.currentTimeMillis()));
						date = sdf2.parse(end_date);
					}
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		
		}else{
			String end_date = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_date = sdf.format(beginDate); 
			StringBuffer sql = new StringBuffer();
			//获取比begin_date的全部时间按照升序排列,获取前count条
			sql.append("select time from (");
			sql.append("select time from T_WF_CORE_WORKDAY where time>='"+begin_date+"'");
			sql.append(" order by time ASC) ");
			sql.append(" where  rownum<="+count);
			List<String> list = super.getEm().createNativeQuery(sql.toString()).getResultList();
			Date date = beginDate;
			try {
				if(list.size()>0){
					int size = list.size();
					end_date = list.get(size-1).toString();
//					end_date += " 23:59:59";
					try{
						date = sdf2.parse(end_date);
					}catch (Exception e) {
						end_date += new SimpleDateFormat(" HH:mm:ss").format(new Date(System.currentTimeMillis()));
						date = sdf2.parse(end_date);
					}
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		
	}
	
	
	@Override
	public int getDateCount(Date beginDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		if(beginDate==null || endDate ==null){
			return 0;
		}
		String begin_date = sdf.format(beginDate); 
		String end_date = sdf.format(endDate); 
		sql.append("select count(t.time) from T_WF_CORE_WORKDAY t  where t.time>='"+begin_date+"' and t.time <='"+end_date+"'");
		return Integer.parseInt(getEm().createNativeQuery(sql.toString()).getSingleResult().toString());
	}
	
	public List<String> getAllYear() {
		String sql="select distinct year from t_wf_core_workday";
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	/**
	 * 移动端得到待办列表
	 */
	public List<Pending> getPendListOfMobile(String userId, int count, Integer pageIndex, Integer pageSize,String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
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
				.append("p.status as status,")
				//p.is_end as isEnd, 办结这一步取pdf 
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
				.append(" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid ")
				.append(" where p.user_uid= '").append(userId).append("' and p.isexchanging=0  and i.id = p.wf_item_uid  ")
			    .append(" and p.is_over='NOT_OVER' and p.is_show=1 and p.is_back != '2' and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(condition)
				.append(" order by p.apply_time desc ");
	   
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
        int i = (pageIndex - 1) * pageSize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
			
		return query.getResultList();
	}

	public List<String> getTypeListOfPending(String userId) {
		StringBuffer query = new StringBuffer()
		.append("select distinct i.vc_sxlx from t_wf_process p ,T_WF_CORE_ITEM i ")
		.append(" where p.user_uid= '").append(userId).append("' and i.id=p.wf_item_uid ");
		query.append(" and p.is_over='NOT_OVER' and p.is_show=1  and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)");
		return getEm().createNativeQuery(query.toString()).getResultList();
	}
	

	@Override
	public List<Pending> getPendListOfMobile(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds, String title, String isReadFile) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition += " and i.vc_sxlx = '"+ type +"'";
		}
		if(!("").equals(itemIds) && itemIds != null){
			condition += " and i.id in ("+ itemIds +")";
		}
		if (title != null  && CommonUtil.stringNotNULL(title)) {  
			condition += " and p.process_title like '%" + title.trim() + "%'";
		}
		if(StringUtils.isNotBlank(isReadFile) && isReadFile.equals("1")){//查传阅件
			condition += " and (p.wf_instance_uid in (select dcv.instanceid from document_circulation_view dcv) and n.wfn_onekeyhandle = 1)";
		}else{//否则查非传阅的待办
			condition += " and (p.wf_instance_uid not in (select dcv.instanceid from document_circulation_view dcv) or n.wfn_onekeyhandle != 1)";
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
				.append("p.status as status,")
				//p.is_end as isEnd, 办结这一步取pdf 
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
				.append("p.isManyInstance as isManyInstance , d.department_name as userDeptId  ")
				.append(" ,'' as dofileId ")
				.append(" ,replace(replace(replace(v.jjcd, '特急', '3'), '急', '2'),'一般','1') as urgency")
				.append(" ,'' as siteName")
				.append(" from t_wf_core_dofile do,T_WF_CORE_ITEM i," +
						" t_wf_process p join zwkj_department d " +
						" on p.userdeptid = d.department_guid left join wh_view v on p.wf_instance_uid=v.instanceid,wf_node n  ")
				.append(" where p.allinstanceid = do.instanceid and p.user_uid= '").append(userId).append("' and p.isexchanging=0 " +
						" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
			    .append(" and (p.isrepeated != 1 or p.isrepeated is null) and p.is_over='NOT_OVER' and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(condition)
				.append(" order by urgency desc nulls last,i.itemsort nulls last,p.apply_time desc ");
		
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
        int i = (pageIndex - 1) * pageSize;
		query.setFirstResult(i);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
		return query.getResultList();
	}

	@Override
	public WfProcess getFirstStepWfProcess(String instanceid, String workflowid) {
		String hql  = " from WfProcess p where p.stepIndex = 1  and  p.wfInstanceUid = '"+instanceid+"'" ;
			if(workflowid!=null && !workflowid.equals("")){
				hql+= " and p.wfUid ='"+workflowid+"'";
			}
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		WfProcess wfProcess = null;
		if(list!=null && list.size()>0){
			wfProcess =  list.get(0);
		}
		return wfProcess;
	}

	public void setBackStatus(List<Pending> list, String isAdmin) {
		// isAdmin 1 为 管理员 , isAdmin 为普通用户
		if(isAdmin != null && isAdmin.equals("1")){
			for (Pending pending : list) {
				// 判断是否已经回收
				String stepIndex = pending.getStepIndex();
				//pending
				/*//是否被暂停
				if(pending.getIsDelaying()!= null &&pending.getIsDelaying().equals("2")){
					pending.setIsBack("1");
					continue;
				}*/
				// 获取最小的步骤stepIndex
				if(pending.getIsManyInstance() != null && pending.getIsManyInstance().equals("1")){
					// 判断 最小的stepIndex 是否等于当前节点
					int minStepIndex = childMinStep(pending.getWf_instance_uid());
					if(stepIndex.equals(minStepIndex)){
						pending.setIsBack("0");
						continue;
					}
				}
				// 获取下一步的 process
				WfProcess nextProcess = getNextProcess(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1));
				if(nextProcess != null &&nextProcess.getAction_status()!= null &&nextProcess.getAction_status().equals("2") ){
					pending.setIsBack("0");
					continue;
				}
				if(isBacked(pending.getWf_instance_uid())){
						if(isBacked(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1))){
							pending.setIsBack("1");
						}else{
							pending.setIsBack("3");
						}
				}else{
					pending.setIsBack("3");
				}
				
			}
		}else{
			// 普通用户   判断下一步是否完成
			for (Pending pending : list) {
				String stepIndex = pending.getStepIndex();
				String currentIndex = Integer.valueOf(stepIndex)+1+"";
				
				// 获取最小的步骤stepIndex
				if(pending.getIsManyInstance() != null && pending.getIsManyInstance().equals("1")){
					// 判断 最小的stepIndex 是否等于当前节点
					int minStepIndex = childMinStep(pending.getWf_instance_uid());
					if(stepIndex.equals(minStepIndex)){
						pending.setIsBack("0");
						continue;
					}
				}
				// 获取当前的 process
				WfProcess currentProcess = getCurrentProcess(pending.getWf_instance_uid(),stepIndex);
			
				if(currentProcess != null){
					List<WfProcess> childs = null;
					if(currentProcess.getAction_status()!= null&&currentProcess.getAction_status() ==2){	//分发 市级主分发市级子 等办
						// 多例同步 ，  instanceid 为stepindex =1 的父节点
						 childs = getChildFirstProcess(currentProcess.getWfInstanceUid());
					}else{ // 主流程分发子流程(县主到县子) 分发假节点
						// 这一步的instanceid 是 子流程的 假节点的父instanceid,切 doctype 为 3
						String falseProcess = getFalseProcess(currentProcess.getWfInstanceUid());
						if(falseProcess != null &&currentProcess.getfInstancdUid() != null){
							// 查找假节点的 instanceid 是否over
							childs = getChildFirstProcess(falseProcess);
						}
					}
					pending.setIsBack("3");
					if(childs != null){
						for(int t = 0;t < childs.size() ; t++){
							// 判断 当前步骤是否是 childs 的 pstep -1;
							if(t == 0 && childs.get(t).getpStepIndex() != null){
								// 当前用户是
								WfProcess currentMax = getProcessByStep(pending.getWf_instance_uid(),childs.get(t).getpStepIndex()+"");
								if( !currentMax.getUserUid().equals(currentProcess.getUserUid())){
									pending.setIsBack("0");
									break;
								}
							}
							// 判断子流程是否 over
							if(childs.get(t).getIsOver() != null && childs.get(t).getIsOver().equals("OVER")){
								// 不显示回收按钮
								pending.setIsBack("0");
								break;
							}
						}
						if(pending.getIsBack().equals("0")){
							continue;
						}
					}
				}
				
			// 一般步骤
				if(isBacked(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1))){
					pending.setIsBack("1");
				}else{
					if(!stepIsOver(pending.getWf_instance_uid(),currentIndex)){
						// 下一步待办
						pending.setIsBack("3");
					}else{
						// 下一步已办
						pending.setIsBack("4");
					}
				}
					
			}
		}
	}

	
	public boolean stepIsOver(String instanceId, String stepIndex) {
		// 2代表上一步被回收
		// 判断该流程是不是有假节点的分发
		String falseProcess = getFalseProcess(instanceId);
		String sql = null;
		// 如果 falseProcess 不为null , 当前步骤是否为是否 > 分发步骤 
		List<WfProcess>  childs = getChildFirstProcess(falseProcess);
		if(childs!= null &&childs.size()>0){
			if(childs.get(0).getpStepIndex() != null && childs.get(0).getpStepIndex() <= Integer.valueOf(stepIndex)){ 
				// 判断是子还是父
				WfProcess currentProcess =	getCurrentProcess(instanceId, stepIndex);
				if(currentProcess != null){ //fu
					if( currentProcess.getfInstancdUid() == null){
						sql = "select distinct t.is_over from t_wf_process t where t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"'";
					}else{
						if(currentProcess.getIsChildWf()!= null &&currentProcess.getIsChildWf().equals("1")){
							sql = "select distinct t.is_over from t_wf_process t where t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"'";
						}else{
							sql = "select distinct t.is_over from t_wf_process t where t.WF_F_INSTANCE_UID = '"+falseProcess+"'";
						}
					}
				}else{ //zi
					sql = "select distinct t.is_over from t_wf_process t where t.WF_F_INSTANCE_UID = '"+falseProcess+"'";
				}
				
			}else{
				sql = "select distinct t.is_over from t_wf_process t where t.WF_F_INSTANCE_UID = '"+falseProcess+"'";
			}
				
		}else{
			if(falseProcess != null){
				sql = "select distinct t.is_over from t_wf_process t where t.WF_F_INSTANCE_UID = '"+falseProcess+"'";
			}else{
				sql ="select distinct t.is_over from t_wf_process t where t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"'";

			}
		}
		try{
			String result = getEm().createNativeQuery(sql).getSingleResult().toString();
			if(result.equals("NOT_OVER")){
				return false;
			}else{
				return true;
			}
		}catch (Exception e) {
			return false;
		}
	}

	/**
	 * 待办数据的回收(上一步);
	 * @param instanceId
	 * @param stepIndex
	 */
	public void recycleTask(String instanceId, String stepIndex) {
		WebSocketUtil webSocket = new WebSocketUtil();
		//1, 更新上一步为待办
		String sql1 = " select t.user_uid, t.process_title, t.fromuserid from t_wf_process t where t.wf_instance_uid = '"+instanceId
					+"' and t.step_index = '"+stepIndex+"' and t.is_show=1 and (t.is_back is null or t.is_back!=2)";
		List<Object[]> addList = getEm().createNativeQuery(sql1).getResultList();
		for(Object[] data: addList){
			String userId = data[0]!=null?data[0].toString():"";
			String process_title = data[1]!=null?data[1].toString():"";
			String fromuserid = data[2]!=null?data[2].toString():"";
			try {
				webSocket.apnsPush(process_title, fromuserid, "0", "", "", userId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		String currentHql = "update t_wf_process t set t.finsh_time = null,t.status = '0', t.is_over = 'NOT_OVER'," +
				"t.sendpersonid = null,t.is_back = '1' where t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"' and t.is_back != '2'";
		getEm().createNativeQuery(currentHql).executeUpdate();
		
		
		// 2, 更新当前的步骤为已办且被收回
		String sql2 =  " select t.user_uid from t_wf_process t where t.wf_instance_uid = '"+instanceId
				+ "' and t.step_index > '"+stepIndex+"' and t.is_show=1 and t.is_over = 'NOT_OVER'";
		List<String> delList = getEm().createNativeQuery(sql2).getResultList();
		for(String userId: delList){
			try {
				webSocket.delBadge(userId, "", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String nextHql = "update t_wf_process t set t.finsh_time = sysdate,t.status = '0', t.is_over = 'OVER',t.sendpersonid = null," +
				"t.is_back = '2' where t.wf_instance_uid = '"+instanceId+"' and t.step_index > '"+stepIndex+"'";
		getEm().createNativeQuery(nextHql).executeUpdate();
		
		//3. 处理业务数据
		String preProcessSql = "select wf_process_uid , wf_node_uid, commentjson from t_wf_process t where  t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"' order by t.apply_time desc ";
		if(!preProcessSql.equals("")){
			Query qu =  getEm().createNativeQuery(preProcessSql);
			List<Object[]> list = qu.getResultList();
			if(list != null && list.size()>0){
				Object[] object =  list.get(0);
				String processId = object[0].toString();
				String nodeId =  object[1].toString();
				String commentJSON = "";
				JSONObject retObj = new JSONObject();
				JSONArray jsons =new JSONArray();;
				if(object[2] != null){
					commentJSON = ClobToString.clob2String((SerializableClob) object[2]);
					if(commentJSON != null && !commentJSON.equals("")&& !commentJSON.equals("null")){
						try {
							jsons = JSONArray.fromObject(commentJSON);
						} catch (Exception e) {
							jsons = JSONObject.fromObject(commentJSON).getJSONArray("pages");
							retObj.put("ServerUrl", JSONObject.fromObject(commentJSON).get("ServerUrl"));
							retObj.put("StampType", JSONObject.fromObject(commentJSON).get("StampType"));
							retObj.put("docId", JSONObject.fromObject(commentJSON).get("docId"));
							retObj.put("resources", JSONObject.fromObject(commentJSON).getJSONArray("resources"));
						}
						
						// 解析json
						JSONObject   obj = jsons.getJSONObject(0);
						JSONArray processes = JSONArray.fromObject(obj.get("processes"));
						for(int i =0 ; i< processes.size() ; i++){
							String ss = processes.getString(i);
							// 根据node 找到对应的 意见
							if(ss.indexOf("\"nodeId\":\""+nodeId+"\"")>-1){
								// 将iswrite 改为1
								ss = ss.replace("\"isWrite\":0","\"isWrite\":1").replace("\"isSignWrite\":0","\"isSignWrite\":1");
								processes.set(i, ss);
								obj.put("processes", processes);
								jsons.set(0, obj);
								break;
							}
						}
					}
				}
				//更新数据
				String sql = "update t_wf_process t set COMMENTJSON = ? where t.WF_PROCESS_UID = ?";
				Query query = getEm().createNativeQuery(sql);
				if(retObj != null &&retObj.size()>0){
					retObj.put("pages", jsons);
					query.setParameter(1, retObj.toString());
				}else{
					query.setParameter(1, jsons.toString());
				}
				
				query.setParameter(2, processId);
				query.executeUpdate();
			}
		}
	}

	public boolean isBacked(String instanceId) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String sql = "select  t.is_back from t_wf_process t where t.wf_instance_uid = '"+instanceId+"'  and t.finsh_time is  null and t.is_back != '2'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		for(String is_back : list){
			if(!is_back.equals("0")){
				return true;
			}
		}
		return false;
	}
	
	public boolean isBacked(String instanceId,String preStep) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String sql = "select  t.is_back from t_wf_process t where t.wf_instance_uid = '"+instanceId+"'  and t.finsh_time is  null and t.is_back != '2' and t.step_index = '"+preStep+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		for(String is_back : list){
			if(!is_back.equals("0")){
				return true;
			}
		}
		return false;
	}
	
	public int childMinStep(String instanceId) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "select min(p2.step_index) as step_index from t_wf_process p2 where p2.wf_instance_uid ='"+instanceId+"'";
		return Integer.parseInt(getEm().createNativeQuery(hql).getSingleResult().toString());
	}


	@Override
	public String getPinstanceId(String instanceId) {
		String sql = "select wf_f_instance_uid from t_wf_process where wf_instance_uid = '"+instanceId+"'" +
				" group by wf_f_instance_uid";
		List<Object> list = getEm().createNativeQuery(sql).getResultList();
		String pInstanceId = "";
		if(list!=null && list.size()>0){
			pInstanceId = list.get(0)==null?"":list.get(0).toString();
		}
		return pInstanceId;
	}
	
	public WfProcess getNextProcess(String instanceId,String stepIndex) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "from WfProcess t where t.wfInstanceUid = '"+instanceId+"' and t.stepIndex = '"+stepIndex+"'";
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public WfProcess getCurrentProcess(String instanceId,String stepIndex) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "from WfProcess t where t.wfInstanceUid = '"+instanceId+"' and t.stepIndex = '"+stepIndex+"' and t.isBack != '2' and t.isBack != '1'";
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public WfProcess getProcessByStep(String instanceId,String stepIndex) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "from WfProcess t where t.wfInstanceUid = '"+instanceId+"' and t.stepIndex = '"+stepIndex+"'";
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public WfProcess getProcessByInstanceIds(String instanceIds,String userId) {
		String sql = "select t.* from t_wf_process t where t.user_uid='"+userId+"' and t.is_over ='NOT_OVER' and (t.wf_instance_uid in ('"+instanceIds+"') or t.wf_f_instance_uid in ('"+instanceIds+"')) and t.ismanyinstance is not null and t.ischildwf = '1'";
		List<WfProcess> procList = getEm().createNativeQuery(sql, WfProcess.class).getResultList();
		if(procList.size() > 0){
			return procList.get(0);
		}
		return null;
	}


	@Override
	public Date getEndDate(int count, Date beginDate, String wfm_calendar) {
		String end_date = "";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String begin_date = sdf2.format(beginDate); 
		StringBuffer sql = new StringBuffer();

		sql.append("select c.time ");
		sql.append("from (select * from T_WF_CORE_WORKDAY  where time >='"+begin_date+"' order by time ) c " );
		sql.append("where rownum <="+count +" order by time desc");
		List<String> list = super.getEm().createNativeQuery(sql.toString()).getResultList();
		Date date = beginDate;
		try {
			if(list!=null && list.size()>0){
					end_date = list.get(0).toString() + " ";
					end_date += begin_date.substring(begin_date.length()-8,begin_date.length());
					date = sdf2.parse(end_date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	
	@Override
	public List<WfProcess> findProcessListByFId(String f_instanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+f_instanceId+"'";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	
	@Override
	public Integer countProcessListByFId(String f_instanceId){
		String sql = "select count(*) from t_wf_process t where t.wf_f_instance_uid = '"+f_instanceId+"'";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public List<WfProcess> findProcessListByFIdAndDoType(String getfInstancdUid,String doFile) {
		String sql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+getfInstancdUid+"' and t.dotype='"+doFile+"'";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	@Override
	public WfProcess checkInstanceIsOver(WfProcess wfProcess) {
		if(wfProcess==null){
			return null ;
		}
		String wfInstanceUid = wfProcess.getWfInstanceUid();
		//String wfProcessUid = wfProcess.getWfProcessUid();
		Integer stepIndex = wfProcess.getStepIndex()+1;
		String sql = "select t.is_end from t_wf_process t" +
				" where t.WF_INSTANCE_UID='"+wfInstanceUid+"' and t.STEP_INDEX="+stepIndex ;
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		int is_end = 0 ;
		if(list!=null && list.size()>0){
			is_end = Integer.parseInt(list.get(0).toString());
		}
		if(is_end==0){
			return wfProcess ;
		}else{
			//获取
			String hql = "from WfProcess t where t.wfInstanceUid='"+wfInstanceUid+"' and t.isEnd = 1";
			return (WfProcess)getEm().createQuery(hql).getSingleResult();
			
		}
	}
	@Override
	public List<WfProcess> findProcessListById(String insertid) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid = '"+insertid+"'";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	@Override
	public List<SendAttachments> findSendAttsByDocguid(String instanceId) {
		String sql = "select t.* from oa_doc_attachments t where t.docguid ='"+instanceId+"'";
		return getEm().createNativeQuery(sql,SendAttachments.class).getResultList();
	}
	
	/**
	 * 
	 * 描述：增加【正文附件】（发文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:39:37
	 */
	@Override
	public void addSendAtts(SendAttachments atts){
		if(atts==null){
			return;
		}
		getEm().persist(atts);
	}
	
	public List<WfProcess> getChildFirstProcess(String instanceId) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "from WfProcess t where t.fInstancdUid = '"+instanceId+"' and t.stepIndex = '1' and t.isBack != '2'";
		List<WfProcess> list = getEm().createQuery(hql).getResultList();
		
		return list;
	}
	
	public String getFalseProcess(String finstanceId) {
		// 判断项目是否已经被回收  (被回收的 item , 一定没有办完，并且 is_back 部位0  2 代表 上一步被回收
		String hql = "select t.WF_INSTANCE_UID from t_wf_process t" +
				" where t.WF_F_INSTANCE_UID='"+finstanceId+"' and t.DOTYPE= '3' and t.IS_BACK != '2' ";
		List<String> list = getEm().createNativeQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public List<Object> getAllUserIdByFInstanceId(String getfInstancdUid, String mc) {
		String sql = "select t.user_uid from t_wf_process t left join " +
				"zwkj_employee e on e.employee_guid=t.user_uid " +
				"where  t.wf_f_instance_uid='"+getfInstancdUid+"' and t.step_index=1 and t.dotype='1' ";
		if(mc!=null&&!"".equals(mc)){
			sql +=" and e.employee_name like '%"+mc+"%'";
		}
		Query query=super.getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public List<WfProcess> getProcessByInstanceId(String instancdUid) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid ='"+instancdUid+"'";
		List<WfProcess> procList = getEm().createNativeQuery(sql, WfProcess.class).getResultList();
		return procList;
	}
	
	@Override
	public Integer countByInstanceId(String instancdUid){
		String sql = "select count(*) from t_wf_process t where t.wf_instance_uid ='"+instancdUid+"' "
				+ "and t.wf_f_instance_uid is not null";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public List<WfProcess> findProcessListByFIdAndDoFile(String wfInstanceUid,String dotype) {
		String sql = "select t.* from t_wf_process t where t.wf_f_instance_uid = '"+wfInstanceUid+"' and t.DOTYPE="+dotype;
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	@Override
	public List<WfProcess> findProcessListByIdAndDoFile(String instancdid,
			String dotype) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid = '"+instancdid+"' and t.DOTYPE="+dotype;
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	
	@Override
	public List<DoFileReceive> getDoFileReceiveByInstanceId(String instanceId) {
		String hql = "from DoFileReceive t where t.instanceId='"+instanceId+"'";
		return super.getEm().createQuery(hql).getResultList();
	}
	@Override
	public List<Object> getDeparNameByDepartId(String toDepartId) {
		String sql = "select t.department_name from docexchange_department t where t.department_guid ='"+toDepartId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<Object[]>  getEndProcess(int step, String nodeUid,String instanceId) {
		String sql = "select WF_PROCESS_UID,PDFPATH from T_WF_PROCESS where STEP_INDEX = ? and WF_NODE_UID = ? and WF_INSTANCE_UID = ?";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, Integer.valueOf(step));
		query.setParameter(2, nodeUid);
		query.setParameter(3, instanceId);
		return query.getResultList();
	}
	
	/**
	 * 移动端得到待办列表
	 */
	public List<Pending> getPendListOfMobile(String userId, int count,String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
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
				.append("p.status as status,")
				//p.is_end as isEnd, 办结这一步取pdf 
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
				.append(" from T_WF_CORE_ITEM i , t_wf_process p join zwkj_department d  on p.userdeptid = d.department_guid  ")
				.append(" where p.user_uid= '").append(userId).append("' and p.isexchanging=0  and i.id = p.wf_item_uid  ")
			    .append(" and p.is_over='NOT_OVER' and p.is_show=1 and p.is_back != '2' and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(condition)
				.append(" order by p.wf_item_uid ,p.apply_time desc ");
	   
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
		return query.getResultList();
	}
	
	@Override
	public int findLhfwCount(String conditionSql, String depIds) {
		StringBuffer query = new StringBuffer()
		.append("select count(*) from t_wf_process p ," +
				"t_wf_core_item i, zwkj_department t2 ,wf_node n ")
		.append(" where  p.user_uid = t2.department_guid and p.isexchanging=0" +
				" and i.id=p.wf_item_uid " +
				" and p.wf_node_uid=n.wfn_id " +
				" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1')" +
				" and (p.action_status is null or p.action_status != 2)")
		.append(conditionSql);
		query.append(" and p.is_show=1  and (p.is_back is null or p.is_back != '2')" +
				" and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			 .append(" and (select count(*) from t_wf_core_receive t2 where t2.instanceid = p.wf_instance_uid)=0")
			 .append(" and t2.department_guid in ("+depIds+")");
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	@Override
	public List<Pending> findLhfwpendingList(String conditionSql,
			String depIds, Integer pageIndex, Integer pageSize) {
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
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance ,t2.department_name as userDeptId ")
				.append(" ,'' as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from T_WF_CORE_ITEM i , t_wf_process p ," +
						" zwkj_department t2 ,wf_node n ")
				.append(" where  p.user_uid = t2.department_guid and p.isexchanging=0 " +
						" and i.id = p.wf_item_uid " +
						" and p.wf_node_uid=n.wfn_id " +
						" and (p.fromnodeid != p.tonodeid or p.step_index = '1' or n.wfn_self_loop = '1') ")
				.append("  and p.is_show=1 and (p.is_back is null or p.is_back != '2') and (p.IS_DUPLICATE!='true' or p.IS_DUPLICATE is null)")
			    .append(conditionSql)
			    .append(" and (select count(*) from t_wf_core_receive t2 where t2.instanceid = p.wf_instance_uid)=0")
			    .append(" and t2.department_guid in ("+depIds+")")
				.append(" order by i.i_index ASC , p.apply_time desc ");
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public Pending getPendingById(String processId) {
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
				.append(" '' as pressStatus, ")
				.append(" '' as presscontent, ")
				.append(" '' as userName, ")
				.append("'' as favourite, ")
				.append("p.isManyInstance as isManyInstance , '' as userDeptId ")
				.append(" ,''as dofileId ")
				.append(" ,'' as urgency")
				.append(" ,'' as siteName")
				.append(" from t_wf_core_item i,t_wf_process p ")
				.append(" where 1=1 ")
				.append(" and i.id = p.wf_item_uid and p.wf_process_uid = '" +processId+ "' ");
		Query query = super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		List<Pending> list = query.getResultList();
		return list.get(0);
	}

	@Override
	public WfProcess getRecentProcess(String instanceId) {
		String sql = "select a.* from (select t.* from T_WF_PROCESS t where t.wf_instance_uid = ? "
				+ " and t.finsh_time is not null order by t.finsh_time desc) a where rownum <= 1";
		Query q = this.getEm().createNativeQuery(sql, WfProcess.class);
		q.setParameter(1, instanceId);
		if(q.getResultList().size() < 1){
			sql = "select a.* from (select t.* from T_WF_PROCESS t where t.wf_instance_uid = ? "
				+ "order by t.finsh_time desc) a where rownum <= 1";
			Query q1 = this.getEm().createNativeQuery(sql, WfProcess.class);
			q1.setParameter(1, instanceId);
			return (WfProcess) q1.getSingleResult();
		}else{
			return (WfProcess) q.getSingleResult();
		}
	}

	@Override
	public Integer setDofileCopyNumber(String instanceId) {
		String sql = "update T_WF_CORE_DOFILE t set t.copy_number = (NVL(t.copy_number, 0) + 1) where t.instanceid = ?";
		this.getEm().createNativeQuery(sql).setParameter(1, instanceId).executeUpdate();
		String sql2 = "select t.copy_number from T_WF_CORE_DOFILE t where t.instanceid = ?";
		Query q = this.getEm().createNativeQuery(sql2).setParameter(1, instanceId);
		BigDecimal n = (BigDecimal) q.getSingleResult();
		return n.intValue();
	}
	
	public void setBackStatus(List<Pending> list, String isAdmin, String userId) {
		// isAdmin 1 为 管理员 , isAdmin 为普通用户
		if(isAdmin != null && isAdmin.equals("1")){
			for (Pending pending : list) {
				// 判断是否已经回收
				String stepIndex = pending.getStepIndex();
				//pending
				/*//是否被暂停
				if(pending.getIsDelaying()!= null &&pending.getIsDelaying().equals("2")){
					pending.setIsBack("1");
					continue;
				}*/
				// 获取最小的步骤stepIndex
				if(pending.getIsManyInstance() != null && pending.getIsManyInstance().equals("1")){
					// 判断 最小的stepIndex 是否等于当前节点
					int minStepIndex = childMinStep(pending.getWf_instance_uid());
					if(stepIndex.equals(minStepIndex)){
						pending.setIsBack("0");
						continue;
					}
				}
				// 获取下一步的 process
				WfProcess nextProcess = getNextProcess(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1));
				if(nextProcess != null &&nextProcess.getAction_status()!= null &&nextProcess.getAction_status().equals("2") ){
					pending.setIsBack("0");
					continue;
				}
				if(isBacked(pending.getWf_instance_uid())){
						if(isBacked(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1))){
							pending.setIsBack("1");
						}else{
							pending.setIsBack("3");
						}
				}else{
					pending.setIsBack("3");
				}
				
			}
		}else{
			// 普通用户   判断下一步是否完成
			for (Pending pending : list) {
				String nodeId = pending.getWf_node_uid();
				if(CommonUtil.stringNotNULL(nodeId)){
					WfNode node = getWfNode(nodeId);
					if(node != null){
						if("4".equals(node.getWfn_route_type())){//并行传阅节点，不能收回
							//pending.setIsBack("4");
							BigDecimal count = getFenpiCountByPid(pending.getWf_instance_uid(),pending.getStepIndex(),nodeId,pending.getUserName());
							if(node.getWfn_isSendAgain()==1&&count!=null&&count.intValue()>0){
								pending.setIsBack("0");//如果该节点是并行传阅节点且进行过分批，不允许收回
								continue;
							}else{
								BigDecimal countToOver = getNotOverCountByIds(pending.getWf_instance_uid(),pending.getStepIndex(),nodeId);
								if(countToOver!=null && countToOver.intValue()==0){
									pending.setIsBack("4");
								}else{
									pending.setIsBack("5");
								}
							}
//							count = getCountOfYyByInsId(pending.getWf_instance_uid());
//							if(count!=null && count.intValue() > 0){
//								pending.setIsBack("0");//如果是已阅，不允许收回
//								continue;
//							}
							pending.setIsBack("5");//需求：并行传阅节点的办件加撤回功能。isback设为5做标记
						}else if("6".equals(node.getWfn_route_type())){//串行传阅节点，如果同节点上下个人员尚未打开，可以收回，否则不能收回
							WfProcess curProc = getProcessByID(pending.getWf_process_uid());
							List<WfProcess> procList = findWfProcessList(pending.getWf_workflow_uid(), pending.getWf_instance_uid(), nodeId, pending.getStepIndex());
							if(procList != null && procList.size() > 0){
								WfProcess nextSortProc = procList.get(0);
								if(nextSortProc != null){
									if((nextSortProc.getJssj() != null) || !userId.equals(curProc.getUserUid())){
										pending.setIsBack("4");
									}else{
										pending.setIsBack("3");
									}
								}
							}else{
								pending.setIsBack("4");
							}
						}else{
							String stepIndex = pending.getStepIndex();
							String currentIndex = Integer.valueOf(stepIndex)+1+"";
							
							// 获取最小的步骤stepIndex
							if(pending.getIsManyInstance() != null && pending.getIsManyInstance().equals("1")){
								// 判断 最小的stepIndex 是否等于当前节点
								int minStepIndex = childMinStep(pending.getWf_instance_uid());
								if(stepIndex.equals(minStepIndex)){
									pending.setIsBack("0");
									continue;
								}
							}
							// 获取当前的 process
							WfProcess currentProcess = getCurrentProcess(pending.getWf_instance_uid(),stepIndex);
							if(currentProcess != null){
								List<WfProcess> childs = null;
								if(currentProcess.getAction_status()!= null&&currentProcess.getAction_status() ==2){	//分发 市级主分发市级子 等办
									// 多例同步 ，  instanceid 为stepindex =1 的父节点
									 childs = getChildFirstProcess(currentProcess.getWfInstanceUid());
								}else{ // 主流程分发子流程(县主到县子) 分发假节点
									// 这一步的instanceid 是 子流程的 假节点的父instanceid,切 doctype 为 3
									String falseProcess = getFalseProcess(currentProcess.getWfInstanceUid());
									if(falseProcess != null &&currentProcess.getfInstancdUid() != null){
										// 查找假节点的 instanceid 是否over
										childs = getChildFirstProcess(falseProcess);
									}
								}
								pending.setIsBack("3");
								if(childs != null){
									for(int t = 0;t < childs.size() ; t++){
										// 判断 当前步骤是否是 childs 的 pstep -1;
										if(t == 0 && childs.get(t).getpStepIndex() != null){
											// 当前用户是
											WfProcess currentMax = getProcessByStep(pending.getWf_instance_uid(),childs.get(t).getpStepIndex()+"");
											if( !currentMax.getUserUid().equals(currentProcess.getUserUid())){
												pending.setIsBack("0");
												break;
											}
										}
										// 判断子流程是否 over
										if(childs.get(t).getIsOver() != null && childs.get(t).getIsOver().equals("OVER")){
											// 不显示回收按钮
											pending.setIsBack("0");
											break;
										}
									}
									if(pending.getIsBack().equals("0")){
										continue;
									}
								}
							}
							// 一般步骤
							if(isBacked(pending.getWf_instance_uid(),String.valueOf(Integer.valueOf(stepIndex)+1))){
								pending.setIsBack("1");
							}else{
								if(!stepIsOver(pending.getWf_instance_uid(),currentIndex)){
									// 下一步待办
									pending.setIsBack("3");
									//如果下一步待办是传阅节点而且已经有人办理，不可收回
									List<WfProcess> isCyProcList = getIsOverCyProc(pending.getWf_workflow_uid(), pending.getWf_instance_uid(), currentIndex);
									if(isCyProcList != null && isCyProcList.size() > 0){
										for(WfProcess wfp : isCyProcList){
											if("OVER".equals(wfp.getIsOver())){
												pending.setIsBack("4");
												break;
											}
										}
									}
								}else{
									// 下一步已办
									pending.setIsBack("4");
								}
							}
						}
					}
				}
			}
		}
	}


	public List<WfProcess> findWfProcessList(String workFlowId,String instanceId,String nodeId, String stepIndex){
		StringBuffer hql = new StringBuffer();
		hql.append(" from WfProcess ");
		hql.append(" where isOver = 'NOT_OVER' and wfUid='" + workFlowId + "' and wfInstanceUid='" + instanceId + "' and nodeUid='" + nodeId + 
				"' and (isDuplicate!='true' or isDuplicate is null) and stepIndex='" + stepIndex + "' order by sort desc");
		return getEm().createQuery(hql.toString()).getResultList();
	}	
	
	public List<WfProcess> getIsOverCyProc(String workflowId, String instanceId, String stepIndex){
		String sql = " select t.* from t_wf_process t, wf_node n where t.wf_instance_uid='" + instanceId + 
				"' and t.wf_uid = '"+workflowId+"' and t.step_index='" + stepIndex + 
				"' and t.wf_node_uid=n.wfn_id and (n.wfn_route_type = '4' or n.wfn_route_type='6')";
		return getEm().createNativeQuery(sql, WfProcess.class).getResultList();
	}
	
	@Override
	public String setBackStatus(Map<String, String> map, String userId) {
		String nodeId = map.get("nodeId");
		String processId = map.get("processId");
		String workflowId = map.get("workflowId");
		String instanceId = map.get("instanceId");
		String stepIndex = map.get("stepIndex");
		String isManyInstance = map.get("isManyInstance");
		String isback = map.get("isback");
		if(CommonUtil.stringNotNULL(nodeId)){
			WfNode node = getWfNode(nodeId);
			if(node != null){
				if("4".equals(node.getWfn_route_type())){//并行传阅节点，不能收回
//					isback = "4";
					BigDecimal count = getFenpiCountByPid(instanceId,stepIndex,nodeId,userId);
					if(node.getWfn_isSendAgain()==1&&count!=null&&count.intValue()>0){
						isback = "4";
					}else{
						BigDecimal countToOver = getNotOverCountByIds(instanceId,stepIndex,nodeId);
						if(countToOver!=null && countToOver.intValue()==0){
							isback = "4";
						}else{
							isback = "5";//需求：并行传阅节点的办件加撤回功能。isback设为5做标记
						}
					}
				}else if("6".equals(node.getWfn_route_type())){//串行传阅节点，如果同节点上下个人员尚未打开，可以收回，否则不能收回
					WfProcess curProc = getProcessByID(processId);
					List<WfProcess> procList = findWfProcessList(workflowId, instanceId, nodeId, stepIndex);
					if(procList != null && procList.size() > 0){
						WfProcess nextSortProc = procList.get(0);
						if(nextSortProc != null){
							if((nextSortProc.getJssj() != null) || !userId.equals(curProc.getUserUid())){
								isback = "4";
							}else{
								isback = "3";
							}
						}
					}else{
						isback = "4";
					}
				}else{
					String currentIndex = Integer.valueOf(stepIndex)+1+"";
					
					// 获取最小的步骤stepIndex
					if(isManyInstance != null && isManyInstance.equals("1")){
						// 判断 最小的stepIndex 是否等于当前节点
						int minStepIndex = childMinStep(instanceId);
						if(stepIndex.equals(minStepIndex)){
							isback = "0";
							return isback;
						}
					}
					// 获取当前的 process
					WfProcess currentProcess = getCurrentProcess(instanceId,stepIndex);
				
					if(currentProcess != null){
						List<WfProcess> childs = null;
						if(currentProcess.getAction_status()!= null&&currentProcess.getAction_status() ==2){	//分发 市级主分发市级子 等办
							// 多例同步 ，  instanceid 为stepindex =1 的父节点
							 childs = getChildFirstProcess(currentProcess.getWfInstanceUid());
						}else{ // 主流程分发子流程(县主到县子) 分发假节点
							// 这一步的instanceid 是 子流程的 假节点的父instanceid,切 doctype 为 3
							String falseProcess = getFalseProcess(currentProcess.getWfInstanceUid());
							if(falseProcess != null &&currentProcess.getfInstancdUid() != null){
								// 查找假节点的 instanceid 是否over
								childs = getChildFirstProcess(falseProcess);
							}
						}
						isback = "3";
						if(childs != null){
							for(int t = 0;t < childs.size() ; t++){
								// 判断 当前步骤是否是 childs 的 pstep -1;
								if(t == 0 && childs.get(t).getpStepIndex() != null){
									// 当前用户是
									WfProcess currentMax = getProcessByStep(instanceId,childs.get(t).getpStepIndex()+"");
									if( !currentMax.getUserUid().equals(currentProcess.getUserUid())){
										isback = "0";
										break;
									}
								}
								// 判断子流程是否 over
								if(childs.get(t).getIsOver() != null && childs.get(t).getIsOver().equals("OVER")){
									// 不显示回收按钮
									isback = "0";
									break;
								}
							}
							if(isback.equals("0")){
								return isback;
							}
						}
					}
					// 一般步骤
					if(isBacked(instanceId,String.valueOf(Integer.valueOf(stepIndex)+1))){
						isback = "1";
					}else{
						if(!stepIsOver(instanceId,currentIndex)){
							// 下一步待办
							isback = "3";
							//如果下一步待办是传阅节点而且已经有人办理，不可收回
							List<WfProcess> isCyProcList = getIsOverCyProc(workflowId, instanceId, currentIndex);
							if(isCyProcList != null && isCyProcList.size() > 0){
								for(WfProcess wfp : isCyProcList){
									if("OVER".equals(wfp.getIsOver())){
										isback = "4";
										break;
									}
								}
							}
						}else{
							// 下一步已办
							isback = "4";
						}
					}
				}
			}
		}
		return isback;
	}
	

	@Override
	public int getCountOfExceedPending(String conditionSql,String userId,String type) {
		String condition = "";
		if(!("").equals(type) && type != null){
			condition = " and i.vc_sxlx = '"+ type +"'";
		}
		StringBuffer query = new StringBuffer()
		.append("select count(*) from (select distinct p.wf_instance_uid, p.is_master, p.apply_time ")
		.append("   from t_wf_core_dofile do, T_WF_CORE_ITEM i, t_wf_process p  ")
		.append("   join zwkj_department d ")
		.append("     on p.userdeptid = d.department_guid ")
		.append("   left join wh_view v ")
		.append("     on p.wf_instance_uid = v.instanceid, wf_node n ")
		.append("  where p.allinstanceid = do.instanceid ")
		.append("    and p.isexchanging = 0 ")
		.append("    and i.id = p.wf_item_uid ")
		.append("    and p.wf_node_uid = n.wfn_id ")
		.append("    and (p.fromnodeid != p.tonodeid or p.step_index = '1' or ")
		.append("        n.wfn_self_loop = '1') ")
		.append("    and (p.isrepeated != 1 or p.isrepeated is null) ")
		.append("    and p.is_over = 'NOT_OVER' ")
		.append("    and p.is_show = 1 ")
		.append("    and (p.is_back is null or p.is_back != '2') ")
		.append(condition).append(conditionSql)
		.append("    and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null) )");

		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	
	@Override
	public int getCountOfExceedPending2(String conditionSql) {
		
		StringBuffer query = new StringBuffer()
		.append("  select count(1)   ")
		.append("   from (select min(i.vc_sxlx) as item_type, ")
		.append("                 min(i.vc_sxmc) as item_name, ")
		.append("                 min(do.id) as id, ")
		.append("                 min(do.instanceid) as instanceid, ")
		.append("                 min(do.item_id) as item_id, ")
		.append("                 min(do.dofile_title) as dofile_title, ")
		.append("                 min(ps.wf_process_uid) as processId, ")
		.append("                 XMLAGG(XMLELEMENT(E,ps.user_uid || ',')).EXTRACT('//text()').getclobval() userIds, ")
		.append("                 XMLAGG(XMLELEMENT(E,ee.employee_name || ',')).EXTRACT('//text()').getclobval() userNames, ")
		.append("                 min(nd.wfn_name) as nodeName, ")
		.append("                 min(ps.apply_time) as appTime, ")
		.append("                 min(dp.department_name) as siteName, ")
		.append("                 min(vc_ssbmid) as vc_ssbmid ")
		.append("            from t_wf_core_dofile do, T_WF_CORE_ITEM i ")
		.append("            left join zwkj_department dp ")
		.append("              on dp.department_guid = i.vc_ssbmid ")
		.append("           , t_wf_process ps ")
		.append("            left join zwkj_employee ee ")
		.append("              on ee.employee_guid = ps.user_uid ")
		.append("            left join wf_node nd ")
		.append("              on ps.wf_node_uid = nd.wfn_id ")
		.append("           where (do.isdelete != 1 or do.isdelete is null) ")
		.append("             and ps.wf_instance_uid = do.instanceid ")
		.append("             and do.ITEM_ID = i.id ")
		.append("             and ps.is_over = 'NOT_OVER' ")
		.append("             and ps.is_show = '1' ")
		.append("             and not exists (select * ")
		.append("                    from t_wf_core_end_instanceid en ")
		.append("                   where en.instanceid = do.instanceid) ")
		.append("           group by ps.wf_instance_uid) a ")
		.append("   where 1 = 1 ")
		.append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	
	@Override
	public int getCountOfExceedUR(String conditionSql) {
		
		StringBuffer query = new StringBuffer()
			.append(" select count(1)   ")
		  .append(" from (select distinct i.vc_sxlx as item_type, ")
		  .append("                       i.vc_sxmc as item_name, ")
		  .append("                       do.id as id, ")
		  .append("                       do.instanceid as instanceid, ")
		  .append("                       do.item_id as item_id, ")
		  .append("                       do.dofile_title as dofile_title, ")
		  .append("                       (select pp.wf_process_uid ")
		  .append("                          from t_wf_process pp ")
		  .append("                         where pp.wf_instance_uid = do.instanceid ")
		  .append("                           and pp.is_over = 'NOT_OVER' ")
		  .append("                           and rownum = 1) as processId, ")
		  .append("                       nd.wfn_id as nodeId, ")
		  .append("                       nd.wfn_name as nodeName, ")
		  .append("                       (select pp.apply_time ")
		  .append("                          from t_wf_process pp ")
		  .append("                         where pp.wf_instance_uid = do.instanceid ")
		  .append("                           and pp.is_over = 'NOT_OVER' ")
		  .append("                           and rownum = 1) as appTime, ")
		  .append("                       dp.department_name as siteName, ")
		  .append("                       vc_ssbmid as vc_ssbmid ")
		  .append("         from t_wf_core_dofile do, T_WF_CORE_ITEM i ")
		  .append("         left join zwkj_department dp ")
		  .append("           on dp.department_guid = i.vc_ssbmid ")
		  .append("        , t_wf_process ps ")
		  .append("         left join zwkj_employee ee ")
		  .append("           on ee.employee_guid = ps.user_uid ")
		  .append("         left join wf_node nd ")
		  .append("           on ps.wf_node_uid = nd.wfn_id ")
		  .append("        where (do.isdelete != 1 or do.isdelete is null) ")
		  .append("          and ps.wf_instance_uid = do.instanceid ")
		  .append("          and do.ITEM_ID = i.id ")
		  .append("          and ps.is_over = 'NOT_OVER' ")
		  .append("          and ps.is_show = '1' ")
		  .append("          and not exists (select * ")
		  .append("                 from t_wf_core_end_instanceid en ")
		  .append("                where en.instanceid = do.instanceid)) a ")
		  .append(" where 1 = 1 ")
		  .append("  and (select count(*) ")
		  .append("         from wf_node n ")
		  .append("        where n.wfn_type = 'end' ")
		  .append("          and n.wfn_moduleid in ")
		  .append("              (select l.wfl_wbasemode ")
		  .append("                 from wf_line l, wf_node n2 ")
		  .append("                where (n2.wfn_id = nodeId) ")
		  .append("                  and n2.wfn_moduleid = l.wfl_xbasemode ")
		  .append("                  and n.wfn_pid = n2.wfn_pid ")
		  .append("                  and l.wfl_pid = n.wfn_pid)) > 0 ")
		.append(conditionSql);
		return Integer.parseInt(getEm().createNativeQuery(query.toString()).getSingleResult().toString());
	}
	
	@Override
	public List<Pending> getExceedPendingList(String conditionSql,String userId, Integer pageIndex,Integer pageSize) {
		StringBuffer querySql = 
	    new StringBuffer(" select distinct '' as jssj,  ")
       .append(" (select wm_concat(pp.wf_process_uid) from t_wf_process pp where pp.wf_instance_uid=p.wf_instance_uid and pp.is_over = 'NOT_OVER') as wf_process_uid, ")
       .append(" p.wf_node_uid as wf_node_uid, ")
       .append(" (select t.wfn_name from WF_NODE t where t.wfn_id = p.wf_node_uid) as nodeName, ")
       .append(" p.wf_instance_uid as wf_instance_uid, ")
       .append(" p.wf_uid as wf_workflow_uid, ")
       .append(" replace(p.process_title, chr(10), '') as process_title, ")
       .append(" p.wf_item_uid as item_id, ")
       .append(" p.wf_form_id as form_id, ")
       .append(" p.wf_oldform_id as oldForm_id, ")
       .append(" i.vc_sxmc as item_name, ")
       .append(" i.RELATEDITEMID as RELATEDITEMID, ")
       .append(" p.CONTINUEINSTANCEID as continueInstanceId, ")
       .append(" i.vc_sxlx as item_type, ")
       .append(" p.apply_time as apply_time, ")
       .append(" p.step_index as stepIndex, ")
       .append(" '' as finish_time, ")
       .append(" '未知' as remainTime, ")
       .append(" '0' as warnType, ")
       .append(" (select nvl(n.wfn_deadline, 0) ")
       .append("    from WF_NODE n ")
       .append("   where n.wfn_id = p.wf_node_uid) as wfn_deadline, ")
       .append(" (select n.wfn_deadlineunit ")
       .append("    from WF_NODE n ")
       .append("   where n.wfn_id = p.wf_node_uid) as wfn_deadlineunit, ")
       .append(" '' as employee_name, ")
       .append(" (select e.employee_name ")
       .append("    from zwkj_employee e ")
       .append("   where e.employee_guid = p.owner) as owner, ")
       .append(" (select e.employee_name ")
       .append("    from zwkj_employee e ")
       .append("   where e.employee_guid = p.ENTRUSTUSERID) as entrust_name, ")
       .append(" p.is_over as is_over, ")
       .append(" (case ")
       .append("   when p.JDQXDATE <= sysdate then ")
       .append("    '3' ")
       .append("   else ")
       .append("    p.status ")
       .append(" end) as status, ")
       .append(" p.is_end as isEnd, ")
       .append(" p.is_master as isMaster, ")
       .append(" '' as pdfPath, ")
       .append(" '' as commentJson, ")
       .append(" p.jdqxDate as jdqxDate, ")
       .append(" p.zhqxDate as zhqxDate, ")
       .append(" '' as delay_itemid, ")
       .append(" '' as delay_lcid, ")
       .append(" p.is_back as is_back, ")
       .append(" '' as isDelaying, ")
       .append(" p.isChildWf as isChildWf, ")
       .append(" p.allinstanceid as allInstanceid, ")
       .append(" '' as pressStatus, ")
       .append(" '' as presscontent, ")
       .append(" '' as userName, ")
       .append(" p.isManyInstance as isManyInstance, ")
       .append(" '' as userDeptId, ")
       .append(" do.id as dofileId, ")
       .append(" do.urgency as urgency, ")
       .append(" (select dept.department_name ")
       .append("    from zwkj_department dept ")
       .append("   where dept.department_guid = i.vc_ssbmid) as siteName, ")
       .append(" (select count(*) ")
       .append("    from t_wf_core_workday t ")
       .append("   where to_date(t.time, 'yyyy-mm-dd') > p.apply_time ")
       .append("     and to_date(t.time, 'yyyy-mm-dd') < sysdate) as favourite ")
	   .append(" from t_wf_core_dofile do, T_WF_CORE_ITEM i, t_wf_process p ")
	   .append(" join zwkj_department d ")
	   .append("   on p.userdeptid = d.department_guid ")
	   .append(" left join wh_view v ")
	   .append("   on p.wf_instance_uid = v.instanceid, wf_node n ")
	   .append(" where p.allinstanceid = do.instanceid ")
	   .append("  and p.isexchanging = 0 ")
	   .append("  and i.id = p.wf_item_uid ")
	   .append("  and p.wf_node_uid = n.wfn_id ")
	   .append("  and (p.fromnodeid != p.tonodeid or p.step_index = '1' or ")
	   .append("      n.wfn_self_loop = '1') ")
	   .append("  and (p.isrepeated != 1 or p.isrepeated is null) ")
	   .append("  and p.is_over = 'NOT_OVER' ")
	   .append("  and p.is_show = 1 ")
	   .append("  and (p.is_back is null or p.is_back != '2') ")
	   .append("  and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null) ")
	   .append(conditionSql)
	   .append(" order by favourite desc, p.apply_time desc");
	
		Query query=super.getEm().createNativeQuery(querySql.toString(),"PendingResults");
		
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getExceedPendingList2(String conditionSql, Integer pageIndex,Integer pageSize) {
		StringBuffer querySql = new StringBuffer()
		.append(" select a.item_type,   ")
	    .append("    a.item_name, ")
	    .append("    a.id, ")
	    .append("    a.instanceid, ")
	    .append("    a.item_id, ")
	    .append("    a.dofile_title, ")
	    .append("    a.processId, ")
	    .append("    a.userIds, ")
	    .append("    a.userNames, ")
	    .append("    a.nodeName, ")
	    .append("    a.appTime, ")
	    .append("    (select count(1) ")
	    .append("       from t_wf_core_workday t ")
	    .append("      where to_date(t.time, 'yyyy-mm-dd') > a.appTime ")
	    .append("        and to_date(t.time, 'yyyy-mm-dd') < sysdate) as days, ")
	    .append("    a.siteName ")
		  .append(" from (select min(i.vc_sxlx) as item_type, ")
		  .append("              min(i.vc_sxmc) as item_name, ")
		  .append("              min(do.id) as id, ")
		  .append("              min(do.instanceid) as instanceid, ")
		  .append("              min(do.item_id) as item_id, ")
		  .append("              min(do.dofile_title) as dofile_title, ")
		  .append("              min(ps.wf_process_uid) as processId, ")
		  .append("              XMLAGG(XMLELEMENT(E, ps.user_uid || ',')).EXTRACT('//text()').getclobval() userIds, ")
		  .append("              XMLAGG(XMLELEMENT(E, ee.employee_name || ',')).EXTRACT('//text()').getclobval() userNames, ")
		  .append("              min(nd.wfn_name) as nodeName, ")
		  .append("              min(ps.apply_time) as appTime, ")
		  .append("              min(dp.department_name) as siteName, ")
		  .append("              min(vc_ssbmid) as vc_ssbmid ")
		  .append("         from t_wf_core_dofile do, T_WF_CORE_ITEM i ")
		  .append("         left join zwkj_department dp ")
		  .append("           on dp.department_guid = i.vc_ssbmid ")
		  .append("        , t_wf_process ps ")
		  .append("         left join zwkj_employee ee ")
		  .append("           on ee.employee_guid = ps.user_uid ")
		  .append("         left join wf_node nd ")
		  .append("           on ps.wf_node_uid = nd.wfn_id ")
		  .append("        where (do.isdelete != 1 or do.isdelete is null) ")
		  .append("          and ps.wf_instance_uid = do.instanceid ")
		  .append("          and do.ITEM_ID = i.id ") 
		  .append("          and ps.is_over = 'NOT_OVER' ")
		  .append("          and ps.is_show = '1' ")
		  .append("          and not exists (select * ")
		  .append("                 from t_wf_core_end_instanceid en ")
		  .append("                where en.instanceid = do.instanceid) ")
		  .append("        group by ps.wf_instance_uid order by min(ps.apply_time) ) a ")
		  .append(" where 1 = 1 ")
		.append(conditionSql);
		
		Query query=getEm().createNativeQuery(querySql.toString());

		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getSendUser(String instanceId) {
		String querySql = "select ps.wf_process_uid as processId," + 
				"       nd.wfn_id         as nodeId," + 
				"       nd.wfn_name       as nodeName," + 
				"       ps.user_uid       as userId," + 
				"       ee.employee_name  as userName," + 
				"       do.dofile_title   as dofileTitle" + 
				"  from t_wf_core_dofile do, T_WF_CORE_ITEM i" + 
				"  left join zwkj_department dp" + 
				"    on dp.department_guid = i.vc_ssbmid, t_wf_process ps" + 
				"  left join zwkj_employee ee" + 
				"    on ee.employee_guid = ps.user_uid" + 
				"  left join wf_node nd" + 
				"    on ps.wf_node_uid = nd.wfn_id" + 
				" where (do.isdelete != 1 or do.isdelete is null)" + 
				"   and ps.wf_instance_uid = do.instanceid" + 
				"   and do.ITEM_ID = i.id" + 
				"   and ps.is_over = 'NOT_OVER'" + 
				"   and ps.is_show = '1'" + 
				"   and not exists" + 
				" (select *" + 
				"          from t_wf_core_end_instanceid en" + 
				"         where en.instanceid = do.instanceid) " ;
		if(StringUtils.isNotBlank(instanceId)){
			querySql += " and do.instanceid ='"+instanceId+"'";
		}
		Query query=getEm().createNativeQuery(querySql);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getExceedURList(String conditionSql, Integer pageIndex,Integer pageSize) {
		StringBuffer querySql = new StringBuffer()
			.append(" 	select a.item_type, ")
		    .append("    a.item_name, ")
		    .append("    a.id, ")
		    .append("    a.instanceid, ")
		    .append("    a.item_id, ")
		    .append("    a.dofile_title, ")
		    .append("    a.processId, ")
		    .append("    '' as userIds, ")
		    .append("    '' as userNames,")
		    .append("    a.nodeName, ")
		    .append("    a.appTime, ")
		    .append("    (select count(1) ")
		    .append("       from t_wf_core_workday t ")
		    .append("      where to_date(t.time, 'yyyy-mm-dd') > a.appTime ")
		    .append("        and to_date(t.time, 'yyyy-mm-dd') < sysdate) as days, ")
		    .append("    a.siteName ")
		  .append(" from (select distinct i.vc_sxlx as item_type, ")
		  .append("                       i.vc_sxmc as item_name, ")
		  .append("                       do.id as id, ")
		  .append("                       do.instanceid as instanceid, ")
		  .append("                       do.item_id as item_id, ")
		  .append("                       do.dofile_title as dofile_title, ")
		  .append("                       (select pp.wf_process_uid ")
		  .append("                          from t_wf_process pp ")
		  .append("                         where pp.wf_instance_uid = do.instanceid ")
		  .append("                           and pp.is_over = 'NOT_OVER' ")
		  .append("                           and rownum = 1) as processId, ")
		  .append("                       nd.wfn_id as nodeId, ")
		  .append("                       nd.wfn_name as nodeName, ")
		  .append("                       (select pp.apply_time ")
		  .append("                          from t_wf_process pp ")
		  .append("                         where pp.wf_instance_uid = do.instanceid ")
		  .append("                           and pp.is_over = 'NOT_OVER' ")
		  .append("                           and rownum = 1) as appTime, ")
		  .append("                       dp.department_name as siteName, ")
		  .append("                       vc_ssbmid as vc_ssbmid ")
		  .append("         from t_wf_core_dofile do, T_WF_CORE_ITEM i ")
		  .append("         left join zwkj_department dp ")
		  .append("           on dp.department_guid = i.vc_ssbmid ")
		  .append("        , t_wf_process ps ")
		  .append("         left join zwkj_employee ee ")
		  .append("           on ee.employee_guid = ps.user_uid ")
		  .append("         left join wf_node nd ")
		  .append("           on ps.wf_node_uid = nd.wfn_id ")
		  .append("        where (do.isdelete != 1 or do.isdelete is null) ")
		  .append("          and ps.wf_instance_uid = do.instanceid ")
		  .append("          and do.ITEM_ID = i.id ")
		  .append("          and ps.is_over = 'NOT_OVER' ")
		  .append("          and ps.is_show = '1' ")
		  .append("          and not exists (select * ")
		  .append("                 from t_wf_core_end_instanceid en ")
		  .append("                where en.instanceid = do.instanceid)   order by appTime    ) a ")
		  .append(" where 1 = 1 ")
		  .append("  and (select count(*) ")
		  .append("         from wf_node n ")
		  .append("        where n.wfn_type = 'end' ")
		  .append("          and n.wfn_moduleid in ")
		  .append("              (select l.wfl_wbasemode ")
		  .append("                 from wf_line l, wf_node n2 ")
		  .append("                where (n2.wfn_id = nodeId) ")
		  .append("                  and n2.wfn_moduleid = l.wfl_xbasemode ")
		  .append("                  and n.wfn_pid = n2.wfn_pid ")
		  .append("                  and l.wfl_pid = n.wfn_pid)) > 0 ")
		.append(conditionSql);
		
		Query query=getEm().createNativeQuery(querySql.toString());
		
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public List<Department> getDeptListForpage() {
		String hql = "from Department t where isSite = 1 ";
		Query query = getEm().createQuery(hql);
		return  query.getResultList();
	}

	@Override
	public void recallDoFileForBXCY(String instanceId, String stepIndex,
			String userId) {
		String currentHql = "update t_wf_process t set t.finsh_time = null, t.is_over = 'NOT_OVER' " +
				" where t.wf_instance_uid = '"+instanceId+"' and t.step_index = '"+stepIndex+"' and t.user_uid = '"+userId+"'";
		getEm().createNativeQuery(currentHql).executeUpdate();
	}

	private BigDecimal getFenpiCountByPid(String instanceId,String stepIndex,String nodeId,String userId) {
		String sql = "select count(*) from t_wf_process t where t.wf_instance_uid='"+instanceId+"' "+
				"and t.step_index="+stepIndex+" and t.fromuserid='"+userId+"'"+
				" and t.wf_node_uid ='"+nodeId+"'";
		return (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
	}
	
	private BigDecimal getCountOfYyByInsId(String instanceId) {
		String sql = "select count(*) from document_circulation_view t where t.instanceid='"+instanceId+"' ";
		return (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
	}
	
	private BigDecimal getNotOverCountByIds(String instanceId,
			String stepIndex, String nodeId) {
		String sql = "select count(*) from t_wf_process t where t.wf_instance_uid='"+instanceId+"' "+
				"and t.step_index="+stepIndex+" and t.wf_node_uid ='"+nodeId+"' and t.is_over='NOT_OVER'";
		return (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
	}
}
