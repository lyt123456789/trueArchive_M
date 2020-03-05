package cn.com.trueway.workflow.webService.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.webService.dao.ArchiveWebServiceDao;

public class ArchiveWebServiceDaoImpl extends BaseDao implements ArchiveWebServiceDao{

	@Override
	public List<Object[]> getArchivedFwList(String instanceId) {
		String sql = " select v.instanceId, v.fwdjh, v.zbbm, v.xbbm, v.qcr, v.xgr, v.shyj, v.qfyj, v.dzr, v.jjcd, v.mj, " +
				" v.zrz, v.wh, v.tm, v.cwrq, v.fs, v.ys, v.ztc, v.wz, v.gb, v.lb, v.qwbs, v.djrq, v.fwdw, v.csdw, v.zzjgdm, " +
				" v.bz from fw_archive_view v where v.instanceid='" + instanceId + "'";
		System.out.println("--------------getArchivedFwList----------------sql="+sql);
		Query query=getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getArchivedSwList(String instanceId) {
		String sql = "select v.instanceId, v.swdjh, v.wjlyfl, v.jjcd, v.mj, v.zrz, v.wh, v.tm, v.cwrq, v.fs, v.ys, v.ztc, " +
				" v.wz, v.gb, v.lb, v.qwbs, v.swrq, v.swh, v.swz, v.nbyj, v.ldps, v.zbbm, v.xbbm, v.cljg, v.zzjgdm, v.bz " +
				" from sw_archive_view v where v.instanceId='" + instanceId + "'";
		System.out.println("--------------getArchivedSwList----------------sql="+sql);
		Query query=getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getProcessList(String instanceId) {
		StringBuffer  sql = new StringBuffer();
		sql.append(" select twp.wf_process_uid as wfProcessUid, ");
		sql.append(" twp.wf_instance_uid as wfInstanceUid, ");
		sql.append(" twp.wf_uid as wf_uid,twp.step_index as stepIndex, ");  
		sql.append("  twp.is_end as isEnd,twp.wf_form_id as formId, ");  
		sql.append("   (select t.wfn_name from wf_node t where t.wfn_id = twp.wf_node_uid) as nodeName, ");
		sql.append("   twp.wf_node_uid as nodeId, ");   
		sql.append("   twp.user_uid as userId, ");   
		sql.append("   (select t.name from user_dep_view t where t.uuid = twp.fromuserid) as fromUserName, ");      
		sql.append("   emp.employee_name as userName, ");  
		sql.append("    dep.department_guid as depId, ");  
		sql.append("    dep.department_name as depName, ");  
		sql.append("   twp.apply_time as applyTime, ");      
		sql.append("   to_char(twp.finsh_time,'yyyy-mm-dd') as finshTime, ");    
		sql.append("   twp.IS_BACK as is_back, ");      
		sql.append("   (select decode(count(*), 0, 0, 1) ");       
		sql.append("   from t_wf_process p ");      
		sql.append("  where p.wf_f_instance_uid = twp.wf_instance_uid ");       
		sql.append("  and twp.dotype = 3) as isHaveChild, ");       
		sql.append("  twp.fromnodeid as fromNodeId, ");   
		sql.append("  twp.tonodeid as toNodeId, ");          
		sql.append("  twp.push_childprocess as push_childprocess, ");      
		sql.append("  twp.is_merge as is_merge, ");   
		sql.append("  twp.dotype as doType, ");    
		sql.append("  twp.wf_f_instance_uid as f_instanceId, ");       
		sql.append("  twp.jssj as jssj, ");     
		sql.append("  twp.isRedirect as isRedirect, ");     
		sql.append("  twp.pdfPath as pdfPath, ");       
		sql.append("  twp.wf_uid as workflowId, ");    
		sql.append("   twp.is_over as isOver, ");  
		sql.append("   emp.employee_jobtitles as job, ");  
		sql.append("   twp.fjbProcessId as fjbProcessId ");     
		sql.append("    from t_wf_process twp, zwkj_employee emp,zwkj_department dep ");    
		sql.append("    where emp.employee_guid = twp.user_uid ");   
		sql.append("    and emp.department_guid = dep.department_guid ");   
		sql.append("    and twp.wf_instance_uid = '"+instanceId+"' ");      
		sql.append("    and (twp.IS_DUPLICATE != 'true' or twp.IS_DUPLICATE is null) "); 
		sql.append("    order by twp.apply_time, twp.step_index, emp.tabindex asc "); 
		System.out.println("--------------getPrcessList----------------sql="+sql);
		Query query=getEm().createNativeQuery(sql.toString());
		return query.getResultList();
	}
	
	
}
