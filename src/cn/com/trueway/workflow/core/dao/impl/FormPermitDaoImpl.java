package cn.com.trueway.workflow.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.FormPermitDao;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;

public class FormPermitDaoImpl extends BaseDao implements FormPermitDao {

	public WfFormPermit addFormPermit(WfFormPermit permit) {
		if(permit.getId() != null && !"".equals(permit.getId())){
			getEm().merge(permit);
		}else{
			if("".equals(permit.getId())){
				permit.setId(null);
			}
			getEm().persist(permit);
		}
		return permit;
	}

	public void delFormPermit(WfFormPermit permit) {
		getEm().remove(getEm().merge(permit));
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFormPermit> getPermitByFormId( String vc_formid){
		String hql = " select " +
				"vc_roletype,vc_rolename from t_wf_core_formpermit where vc_tagname is null";
		if(vc_formid != null && !"".equals(vc_formid)){
			hql += " and vc_formid = '" + vc_formid + "'";
		}
		hql += " group by vc_roletype,vc_rolename having count(*) >=1 order by vc_roletype";
		List list = getEm().createNativeQuery(hql).getResultList();
		return formatData(list);
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFormPermit> getPermitByTagName( String vc_formid, String vc_tagname){
		String hql = "select " +
				"vc_roletype,vc_rolename from t_wf_core_formpermit where 1=1";
		if(vc_formid != null && !"".equals(vc_formid)){
			hql += " and vc_formid = '" + vc_formid + "'";
		}
		if(vc_tagname != null && !"".equals(vc_tagname)){
			hql += " and vc_tagname = '" + vc_tagname + "'";
		}
		hql += " group by vc_roletype,vc_rolename having count(*) >=1 order by vc_roletype";
		List list = getEm().createNativeQuery(hql).getResultList();
		return formatData(list);
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFormPermit> getPermitByParam(WfFormPermit permit){
		String hql = "from WfFormPermit where 1=1";
		if(CommonUtil.stringNotNULL(permit.getVc_formid())){
			hql += " and vc_formid='"+permit.getVc_formid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_tagname())){
			hql += " and vc_tagname='"+permit.getVc_tagname()+"'";
		}else{
			hql += " and vc_tagname is null";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_rolename())){
			hql += " and vc_rolename='"+permit.getVc_rolename()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_missionid())){
			hql += " and vc_missionid='"+permit.getVc_missionid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		return getEm().createQuery(hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfFormPermit> getPermitByParam2(WfFormPermit permit){
		String hql = "from WfFormPermit where 1=1";
		if(CommonUtil.stringNotNULL(permit.getVc_formid())){
			hql += " and vc_formid='"+permit.getVc_formid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_tagname())){
			hql += " and vc_tagname='"+permit.getVc_tagname()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_rolename())){
			hql += " and vc_rolename='"+permit.getVc_rolename()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_missionid())){
			hql += " and vc_missionid='"+permit.getVc_missionid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		return getEm().createQuery(hql).getResultList();
	}

	public List<WfFormPermit> formatData(List<Object> list){
		List<WfFormPermit> resultList = new ArrayList<WfFormPermit>();
		WfFormPermit permit = null;
		for(Object obj : list){
			permit = new WfFormPermit();
			permit.setVc_roletype(((Object[])obj)[0].toString());
			permit.setVc_rolename(((Object[])obj)[1].toString());
			permit.setVc_roleid(((Object[])obj)[2].toString());
			resultList.add(permit);
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> queryBySql(String sql){
		return getEm().createNativeQuery(sql).getResultList();
	}

	public List<WfFormPermit> getPermitByFormId_new(String vc_formid,String nodeid,String type) {
		String hql = " select " +
				"vc_roletype,vc_rolename,vc_roleid from t_wf_core_formpermit where 1=1 ";
		if(vc_formid != null && !"".equals(vc_formid)){
			hql += " and vc_formid = '" + vc_formid + "'";
		}
		if(nodeid != null && !"".equals(nodeid)){
			hql += " and vc_missionid = '" + nodeid + "'";
		}
		if(type != null && !"".equals(type)){
			hql += " and nodetype = '" + type + "'";
		}
		hql += " group by vc_roletype,vc_rolename,vc_roleid having count(*) >=1 order by vc_roletype";
		List list = getEm().createNativeQuery(hql).getResultList();
		return formatData(list);
	}

	public List<WfFormPermit> getPermitByParam_new(WfFormPermit permit) {
		String hql = "from WfFormPermit where 1=1";
		if(CommonUtil.stringNotNULL(permit.getVc_formid())){
			hql += " and vc_formid='"+permit.getVc_formid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_tagname())){
			hql += " and vc_tagname='"+permit.getVc_tagname()+"'";
		}else{
			//hql += " and vc_tagname is null";
		}
		if(CommonUtil.stringNotNULL(permit.getNodetype())){
			hql += " and nodetype='"+permit.getNodetype()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_rolename())){
			hql += " and vc_rolename='"+permit.getVc_rolename()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roleid())){
			hql += " and vc_roleid='"+permit.getVc_roleid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_missionid())){
			hql += " and vc_missionid='"+permit.getVc_missionid()+"'";
		}
		if(CommonUtil.stringNotNULL(permit.getVc_roletype())){
			hql += " and vc_roletype='"+permit.getVc_roletype()+"'";
		}
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public Map<String, String> getLimitByNodeId(String nodeId, String formId,
			String workflowId) {
		String sql = "select vc_tagname, vc_limit from t_wf_core_formpermit t" +
				" where t.vc_missionid ='"+nodeId+"' and t.vc_formid='"+formId+"'" +
				" and t.lcid='"+workflowId+"'";
		Map<String,String> map = new HashMap<String,String>();
		List list = getEm().createNativeQuery(sql).getResultList();
		for(int i=0; list!=null && i<list.size(); i++){
			Object[] data = (Object[])list.get(i);
			String vc_tagName = data[0].toString();
			String vc_limit = data[1].toString();
			map.put(vc_tagName.toLowerCase(), vc_limit);
		}
		return map;
	}

	@Override
	public Map<String, String> getLimitByEndNode(String nodeId, String formId,
			String workflowId) {
		String sql = "select distinct vc_tagname from t_wf_core_formpermit t" +
				" where  t.vc_formid='"+formId+"'" +
				" and t.lcid='"+workflowId+"'";
		Map<String,String> map = new HashMap<String,String>();
		List list = getEm().createNativeQuery(sql).getResultList();
		for(int i=0; list!=null && i<list.size(); i++){
			String vc_tagName = list.get(i).toString();
			map.put(vc_tagName.toLowerCase(), "1");
		}
		return map;
	}

	@Override
	public boolean checkNodePermitByUserId(String workFlowId, String nodeId,
			String userId) {

		String sql = "select count(1) from t_wf_core_inneruser t, t_wf_core_inneruser_map_user t2," +
				" wf_node t3, wf_main t4 " +
				"where t2.employee_id = '"+userId+"' " +
				" and t4.wfm_id = t3.wfn_pid "+
				//" and t4.wfm_id = '"+workflowId+"' " +
				" and t3.wfn_id = '"+nodeId+"'" +
				" and t.id = t2.inneruser_id  and t3.WFN_STAFF = t.id";
		int count = Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
		return count>0?true:false;
	
	}

	@Override
	public WfFormPermit findFormFormPermit(String workFlowId, String nodeId,
			String formtagname) {

		String hql = "select t1.* from t_wf_core_formpermit t1, t_wf_core_form t2, wf_node t3" +
				" where t1.lcid =:lcid and t1.vc_missionid =:vc_missionid " +
				" and t1.vc_formId = t2.id  and t3.wfn_id = t1.vc_missionid and t2.id = t3.wfn_defaultform and t1.VC_TAGNAME =:tagName";
		Query query = getEm().createNativeQuery(hql, WfFormPermit.class);
		query.setParameter("lcid", workFlowId);
		query.setParameter("vc_missionid",nodeId);
		query.setParameter("tagName",formtagname);
		List<WfFormPermit> list = query.getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	
	}
	
	@Override
	public List<WfFormPermit> findFormPermitListByLcId(String lcId) {
		String hql = " from WfFormPermit t where t.lcid = '"+lcId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void saveFormPermit(WfFormPermit permit) {
		getEm().persist(permit);
	}
	
}
