package cn.com.trueway.workflow.webService.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.webService.dao.WebServiceDao;
import cn.com.trueway.workflow.webService.pojo.WebServiceInfo;

public class WebServiceDaoImpl extends BaseDao implements WebServiceDao{

	@Override
	public void saveWebServiceInfo(WebServiceInfo webServiceInfo) {
		getEm().persist(webServiceInfo);
	}

	@Override
	public List<WebServiceInfo> findWebServiceInfoList(String conditionSql,
			Integer pageindex, Integer pagesize) {
		String sql = "select t.* from t_wf_core_webserviceinfo t where 1=1 ";
		sql+= conditionSql;
		sql+=" order by t.rec_time desc";
		Query query = getEm().createNativeQuery(sql, WebServiceInfo.class);
		if (pageindex != null && pagesize != null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	@Override
	public int findWebServiceInfoCount(String conditionSql) {
		String sql = "select count(*) from t_wf_core_webserviceinfo t where 1=1 ";
		  sql+= conditionSql;
	return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}
	
	@Override
	public void executeSql(String sql) throws Exception{
		getEm().createNativeQuery(sql).executeUpdate();
		
	}
	
	@Override
	public List<Object[]> getItemList(Map<String, String> map){
		String isAdmin = map.get("isAdmin");
		String userId = map.get("userId");//userId
		String pageSize = map.get("pageSize");
		String pageIndex = map.get("pageIndex");
		String itemName =  map.get("itemName");//事项名称
		String sql = "";
		if(CommonUtil.stringNotNULL(isAdmin) && isAdmin.equals("1")){
			//管理员查看所有事项
			sql = "select item.id, item.VC_SXMC, item.VC_SSBM, item.VC_WCSX, item.VC_SXLX, item.B_YJ, " +
					" item.LCID, to_char(item.C_CREATEDATE, 'yyyy-MM-dd hh24:mi:ss'), item.VC_SSBMID, item.VC_XXLX, item.I_INDEX, item.OPERATE_LOG_ID " +
					" from t_wf_core_item item ";
		}else{
			//登录用户有该事项所属流程首节点权限即可查看事项
			sql = " select item.id, item.VC_SXMC, item.VC_SSBM, item.VC_WCSX, item.VC_SXLX, item.B_YJ, " +
					" item.LCID, to_char(item.C_CREATEDATE, 'yyyy-MM-dd hh24:mi:ss'), item.VC_SSBMID, item.VC_XXLX, item.I_INDEX, item.OPERATE_LOG_ID " + 
			  " from t_wf_core_item item, t_wf_core_inneruser_map_user mapuser " + 
			  " where 1 = 1 " + 
			  " and mapuser.employee_id = '" + userId + "' " +  
			  " and mapuser.inneruser_id = " +
			  " (select wf.wfn_staff " +     
			  " from wf_node wf " +      
			  " where wf.wfn_moduleid in " +      
			  " (select l.wfl_wbasemode " +            
			  " from wf_line l " +
			  " where l.wfl_xbasemode = " +           
			  " (select n.wfn_moduleid " +                    
			  " from wf_node n " +                        
			  " where n.wfn_pid = item.lcid " +                     
			  " and wfn_type = 'start') " +                       
			  " and l.wfl_pid = item.lcid) " +                 
			  " and wfn_pid = item.lcid) ";
		}
		
		if(CommonUtil.stringNotNULL(itemName)){
			//根据事项名称查询
			sql = sql + " and item.vc_sxmc like '%" + itemName + "%' ";
		}
		
		Query query = getEm().createNativeQuery(sql.toString());
		if(CommonUtil.stringNotNULL(pageIndex) && CommonUtil.stringNotNULL(pageSize)){
			query.setFirstResult(Integer.parseInt(pageIndex));
			query.setMaxResults(Integer.parseInt(pageSize));
		}
		return query.getResultList();
	}
	
	@Override
	public int getItemListCount(Map<String, String> map){
		String isAdmin = map.get("isAdmin");
		String userId = map.get("userId");//userId
		String itemName =  map.get("itemName");//事项名称
		String sql = "";
		if(CommonUtil.stringNotNULL(isAdmin) && isAdmin.equals("1")){
			sql = "select count(*) from t_wf_core_item item ";
		}else{
			sql = " select count(*) " + 
			  " from t_wf_core_item item, t_wf_core_inneruser_map_user mapuser " + 
			  " where 1 = 1 " + 
			  " and mapuser.employee_id = '" + userId + "' " +  
			  " and mapuser.inneruser_id = " +
			  " (select wf.wfn_staff " +     
			  " from wf_node wf " +      
			  " where wf.wfn_moduleid in " +      
			  " (select l.wfl_wbasemode " +            
			  " from wf_line l " +
			  " where l.wfl_xbasemode = " +           
			  " (select n.wfn_moduleid " +                    
			  " from wf_node n " +                        
			  " where n.wfn_pid = item.lcid " +                     
			  " and wfn_type = 'start') " +                       
			  " and l.wfl_pid = item.lcid) " +                 
			  " and wfn_pid = item.lcid) ";
		}
		
		if(CommonUtil.stringNotNULL(itemName)){
			sql = sql + " and item.vc_sxmc like '%" + itemName + "%' ";
		}
		
		Query query = getEm().createNativeQuery(sql.toString());
		return ((BigDecimal)query.getResultList().get(0)).intValue();
	}
	
	@Override
	public List<Object[]> getSyncInfoListByItemId(Map<String, String> map){
		String itemId = map.get("itemId");
		String pageSize = map.get("pageSize");
		String pageIndex = map.get("pageIndex"); 
		String sql = " select distinct t5.ID, t5.VC_NAME, t5.VC_FIELDNAME, t5.I_TYPE, t5.I_TABLEID, t5.I_FIELDTYPE, t5.B_VALUE, " +
				" t5.VC_COMMENT, t5.VC_VALUE, t5.I_LENGTH, t5.I_PRECISION, t5.VC_FTABLE, t5.VC_FFIELD, t5.I_ORDERID, t5.OPERATE_LOG_ID " +
				" from wf_node wf, wf_main m , t_wf_core_item i, t_wf_core_form t2, t_wf_core_form_map_column t3, "
				+ " t_wf_core_table t4, t_wf_core_fieldinfo t5 "
				+ " where wf.wfn_moduleid in "
				+ " (select l.wfl_wbasemode  from wf_line l where l.wfl_xbasemode = " 
				+ " (select n.wfn_moduleid from wf_node n where n.wfn_pid = m.wfm_id and wfn_type='start') "
				+ " and l.wfl_pid =  m.wfm_id) " 
				+ " and i.lcid = m.wfm_id and m.wfm_id = wf.wfn_pid "
				+ " and t2.id = wf.wfn_defaultform "
				+ " and t3.formid = t2.id "
				+ " and t4.vc_tablename = t3.assigntablename "
				+ " and t3.assigncolumnnam is not null "
				+ " and t5.i_tableid = t4.id "
				+ " and i.id = '"+itemId+"' ";
		
		Query query = getEm().createNativeQuery(sql.toString());
		if(CommonUtil.stringNotNULL(pageIndex) && CommonUtil.stringNotNULL(pageSize)){
			query.setFirstResult(Integer.parseInt(pageIndex));
			query.setMaxResults(Integer.parseInt(pageSize));
		}
		return query.getResultList();
	}
	
	public int getSyncInfoCountByItemId(Map<String, String> map){
		String itemId = map.get("itemId");
		String sql = " select count(*) "
				+ " from t_wf_core_fieldinfo field, T_WF_CORE_TABLE tab, t_wf_core_item item "
				+ " where tab.id = field.i_tableid "
				+ " and item.lcid=tab.lcid " 
				+ " and item.id='" + itemId + "'";
		Query query = getEm().createNativeQuery(sql.toString());
		
		return ((BigDecimal)query.getResultList().get(0)).intValue();
	}
	
}
