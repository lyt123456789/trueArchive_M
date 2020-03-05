package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.MonitorDao;
import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.pojo.WfInterfaceCheck;

public class MonitorDaoImpl extends BaseDao implements MonitorDao {

	@Override
	public void addMonitorInfo(MonitorInfoBean info) {
		if (info != null) {
			getEm().merge(info);
		}
	}

	@Override
	public List<MonitorInfoBean> getMonitorInfoByParam(Map<String, Object> params) {
		String sql = "select * from T_WF_CORE_MONITORINFO t where 1=1 ";
		if(params.containsKey("id")){
			sql += " and id =:id ";
		}
		
		if(params.containsKey("date")){
			sql += " and CURRENTDATE =:date ";
		}
		
		if(params.containsKey("time")){
			sql += " and CURRENTTIME =:time ";
		}
		
		if(params.containsKey("minute")){
			sql += " and CURRENTMINUTE =:minute ";
		}
		sql = sql + " order by t.currentdate asc, t.currenttime asc, t.currentminute asc ";
		Query query = getEm().createNativeQuery(sql,MonitorInfoBean.class);
		
		if(params.containsKey("Id")){
			query.setParameter("id", params.get("id").toString());
		}
		
		if(params.containsKey("date")){
			query.setParameter("date", params.get("date").toString());
		}
		
		if(params.containsKey("time")){
			query.setParameter("time", params.get("time").toString());
		}
		
		if(params.containsKey("minute")){
			query.setParameter("minute", params.get("minute").toString());
		}
		List<MonitorInfoBean> list = query.getResultList();
		return list;
	}

	@Override
	public List<String> getDayCpuInfo(String date) {
		String sql = "select CPURATIO from T_WF_CORE_MONITORINFO where 1=1 and CURRENTDATE  =:date order by CURRENTTIME asc ,CURRENTMINUTE asc";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("date", date);
		return query.getResultList();
	}

	@Override
	public List<String> getDayMemoryInfo(String date) {
		String sql = "select MEMORYRATIO from T_WF_CORE_MONITORINFO where 1=1 and CURRENTDATE  =:date order by CURRENTTIME asc ,CURRENTMINUTE asc ";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("date", date);
		return query.getResultList();
	}
	
	@Override
	public List<WfInterfaceCheck> getInterfaceList(Map<String, String> map){
		String sql = "select * from T_WF_INTERFACECHECK t where 1=1 ";
		sql = sql + " order by t.sortIndex asc ";
		Query query = getEm().createNativeQuery(sql,WfInterfaceCheck.class);
		List<WfInterfaceCheck> list = query.getResultList();
		return list;
	}
	
	@Override
	public void updateInterfaceResult(String id, String result){
		String sql = "update T_WF_INTERFACECHECK t set t.result = '" + result + "' where t.id='" + id + "'";
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public List<Object[]> getDfStatisticBySite(Map<String, String> map){
		String sql = " select count(d.id),i.vc_ssbmid,i.vc_ssbm from t_wf_core_dofile d, t_wf_core_item i where d.item_id=i.id group by i.vc_ssbmid, i.vc_ssbm ";
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> getDfStaByItemType(Map<String, String> map){
		String sql = " select count(d.id), i.vc_sxlx, " +
			" (case when i.vc_sxlx = '0' then '发文' when i.vc_sxlx = '1' then '办文' when i.vc_sxlx = '2' then '传阅' when i.vc_sxlx = '3' then '客情报告' else '用车申请' end) as sxmc " +
			" from t_wf_core_dofile d, t_wf_core_item i where d.item_id = i.id group by i.vc_sxlx ";
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getActDeptList(Map<String, String> map) {
		String sql = 		"select  counts,deptName " +
		"		  from (select count(dofileId) as counts, deptName"+
		"		          from (select d.id as dofileId,"+
		"		                       p.userdeptid as userdeptId,"+
		"		                       (select VC_SSBM"+
		"		                          from T_WF_CORE_ITEM"+
		"		                         where id = p.WF_ITEM_UID)  || '-'||"+
		"		                       (select dept.department_name"+
		"		                          from zwkj_department dept"+
		"		                         where dept.department_guid = p.userdeptid) as deptName"+
		"		                  from t_wf_core_dofile d, t_wf_process p"+
		"		                 where d.instanceid = p.wf_instance_uid"+
		"		                   and p.step_index = 1)"+
		"		         group by deptName"+
		"		         order by counts desc)"+
		"		 where rownum < 11";
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}
}
