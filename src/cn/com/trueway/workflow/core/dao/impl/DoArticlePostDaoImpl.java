package cn.com.trueway.workflow.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.business.model.ItemSelect;
import cn.com.trueway.workflow.core.dao.DoArticlePostDao;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;

public class DoArticlePostDaoImpl extends BaseDao  implements DoArticlePostDao {

	public ItemSelect getSelectByItemID(String itemId, String type) {
		// 根据事项id 查找 自定义查询条件
		String hql = "from ItemSelect t where t.item_id = '"+itemId+"' and t.type ='"+type+"'";
		List<ItemSelect>  select = getEm().createQuery(hql).getResultList();
		if(select != null && select.size()>0){
			return select.get(0);
		}
		return null;
	}
	
	@Override
	public int getCountOfFreeDofile(String conditionSql,
			String conditionTable, String employeeGuid, String type,
			List<FieldSelectCondition> fsList,String bjlx) {

		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from (select distinct d.instanceid,d.dotime, t.filedtime ");
		  if("0".equals(bjlx)){
			  sb.append(" from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_core_dofile d left join filed_bw_inf t on d.instanceid = t.instanceid where i.vc_sxlx = '1'");
		  }else if("1".equals(bjlx)){
			  sb.append(" from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_core_dofile d left join filed_fw_inf t on d.instanceid = t.instanceid where i.vc_sxlx = '0'");
		  }else{
			  sb.append(" from T_WF_CORE_ITEM i,t_wf_core_dofile d where 1=1 "); 
		  }
		if("0".equals(bjlx)||"1".equals(bjlx)){
			sb.append(" and (d.isdelete!=1 or d.isdelete is  null) and d.item_id=i.id and p.wf_instance_uid = d.instanceid ")
			.append(" and ( select sum(p1.is_end) from t_wf_process p1 where  p1.wf_instance_uid = d.instanceid) >0 and p.is_end = '1' ")
			.append(" and p.apply_time = (select max(p2.apply_time) from t_wf_process p2 where  p2.wf_instance_uid = d.instanceid)");
			
		}else{
			sb.append(" and  (d.isdelete!=1 or d.isdelete is  null) and  d.item_id=i.id ");
		}
		if ( CommonUtil.stringNotNULL(conditionSql)){
			sb.append(conditionSql);
		}
		sb.append(" order by d.dotime desc) ");
		
		
		Query query=super.getEm().createNativeQuery(sb.toString());
		// setparameter
		if(fsList != null &&fsList.size()>0){
			for(int i = 0 ,size =fsList.size() ; i < size; i++){
				FieldSelectCondition fs = fsList.get(i);
				if(fs.getType() == 1){
					query.setParameter(fs.getName(), "%"+fs.getValue()+"%");
				}else{
					query.setParameter(fs.getName(), fs.getValue());
				}
			}
		}
		return Integer.parseInt(query.getSingleResult().toString());
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getFreeDofileList(String isEnd, String conditionSql,
			String conditionTable, String resultSql, String userId,
			int pageIndex, int pageSize, List<FieldSelectCondition> fsList,String bjlx) {

		StringBuffer querySql = (new StringBuffer("select  "));
		
		if("0".equals(bjlx)){
			  querySql.append(resultSql+"  d.dofile_title, t.wh, t.lwdw, p.apply_time,d.dotime,t.filedtime,d.instanceId, '' as processId,'' as itemId,'' as formid,p.ischildwf,'' as isHaveChild ") // 预留四个 参数 processid, itemId, formid
			          .append(" from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_core_dofile d left join filed_bw_inf t on d.instanceid = t.instanceid where i.vc_sxlx = '1' " );
		  }else if("1".equals(bjlx)){
			  querySql.append(resultSql+" d.dofile_title,t.wh,to_char(t.zbbm),to_char(t.csbm),p.apply_time,d.dotime,t.filedtime,d.instanceId, '' as processId,'' as itemId,'' as formid,p.ischildwf,'' as isHaveChild ") // 预留四个 参数 processid, itemId, formid
			          .append(" from t_wf_process p ,T_WF_CORE_ITEM i, t_wf_core_dofile d left join filed_fw_inf t on d.instanceid = t.instanceid where i.vc_sxlx = '0' ");
		  }else{
			  querySql.append(resultSql+", d.instanceId, '' as processId,'' as itemId,'' as formid ") // 预留四个 参数 processid, itemId, formid
				.append(" from T_WF_CORE_ITEM i,t_wf_core_dofile d  where 1=1 ");
		  }
		//isEnd==1, 流程结束，关联t_wf_core_end_instanceid查询
				
		if("0".equals(bjlx)||"1".equals(bjlx)){
			querySql.append(" and (d.isdelete!=1 or d.isdelete is  null) and d.item_id=i.id and p.wf_instance_uid = d.instanceid ")
			.append(" and ( select sum(p1.is_end) from t_wf_process p1 where  p1.wf_instance_uid = d.instanceid) >0 and p.is_end = '1' ")
			.append(" and p.apply_time = (select max(p2.apply_time) from t_wf_process p2 where  p2.wf_instance_uid = d.instanceid)");
			
		}else{
			querySql.append("  and (d.isdelete!=1 or d.isdelete is  null) and  d.item_id=i.id ");
		}
		
		querySql.append(conditionSql)
			    .append(" order by d.dotime desc");
 		Query query=super.getEm().createNativeQuery(querySql.toString());
		// setparameter
		if(fsList != null &&fsList.size()>0){
			for(int i = 0 ,size =fsList.size() ; i < size; i++){
				FieldSelectCondition fs = fsList.get(i);
				if(fs.getType() == 1){
					query.setParameter(fs.getName(), "%"+fs.getValue()+"%");
				}else{
					query.setParameter(fs.getName(), fs.getValue());
				}
			}
		}
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		List<Object> list = query.getResultList();
		return list;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<WfProcess> getProcessList(String instanceId) {
		String sql = "select t.* from t_wf_process t where t.wf_instance_uid='"+instanceId+"'  and (t.IS_DUPLICATE!='true' or t.IS_DUPLICATE is null) " +
				"  order by t.finsh_time desc";
		return getEm().createNativeQuery(sql,WfProcess.class).getResultList();
	}
}
