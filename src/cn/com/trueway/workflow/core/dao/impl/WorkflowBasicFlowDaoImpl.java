package cn.com.trueway.workflow.core.dao.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.hibernate.lob.SerializableClob;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.pojo.BasicFlow;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfHistoryNode;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.pojo.WfXml;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;

@SuppressWarnings("unchecked")
public class WorkflowBasicFlowDaoImpl extends BaseDao implements WorkflowBasicFlowDao {
	private static Logger logger=Logger.getLogger(WorkflowBasicFlowDaoImpl.class);
	public void delete(String id) {
		String hql = "from BasicFlow r where r.uuid=:uuid";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("uuid", id);
		BasicFlow route = (BasicFlow) query.getSingleResult();
		if (route != null) {
			super.getEm().remove(route);
		}
	}

	public void deleteFromWEBID(String webId) {
		getEm().createNativeQuery("delete from WF_BasicFlow where webId='"+webId+"'").executeUpdate();
	}

	public BasicFlow get(String id) {
		return super.getEm().find(BasicFlow.class, id);
	}

	public List<Object[]> getBasicFlowList(String roleIds, String conditionSql, Integer pageindex, Integer pagesize) {
		String hql = "";
		if(roleIds!=null && !roleIds.equals("")){
			if(roleIds.equals("all")){
				hql = "select w.wfm_id, w.wfm_name,w.wfm_modifytime," +
							"w.wfm_createtime,w.wfm_status from wf_main w where 1=1 " ;
				if(conditionSql!=null && !conditionSql.equals("")){
					hql += conditionSql;
				}
				hql +=" order by wfm_modifytime desc";
			}else{
				hql = "select distinct w.wfm_id, w.wfm_name,w.wfm_modifytime," +
							"w.wfm_createtime, w.wfm_status" +
							" from wf_main w , t_wf_core_wfmain_role t2 " +
						" where t2.wfmainid = w.wfm_id and t2.roleId in ("+roleIds+")";
				if(conditionSql!=null && !conditionSql.equals("")){
					hql += conditionSql;
				}
				hql += " order by w.wfm_modifytime desc";
			}
		}else{
			return new ArrayList<Object[]>();
		}
		Query query = getEm().createNativeQuery(hql.toString());
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public void save(BasicFlow basicFlow) {
		super.getEm().persist(basicFlow);
	}

	public void update(BasicFlow basicFlow) {
		getEm().merge(basicFlow);
	}
	public void saveWfMain(WfMain wfMain) {
		super.getEm().persist(wfMain);
	}

	public void deleteWfMain(String id) {
		super.getEm().createQuery("delete from WfMain where wfm_id='"+id+"'").executeUpdate();
	}

	public WfNode getStartNode(String id) {
		String hql="from WfNode where wfn_pid='"+id+"' and wfn_type='start'";
		Query query=super.getEm().createQuery(hql);
		return (WfNode) query.getResultList().get(0);
	}

	
	public List<WfNode> getNode(String id, String node_id) {
		//获取下一节点(单向)
		String sql="select * from wf_node wf where  wf.wfn_isdisplay!='1' and wf.wfn_moduleid in(select l.wfl_wbasemode from wf_line l where l.wfl_xbasemode=(select n.wfn_moduleid from wf_node n where n.wfn_pid='"+id+"' and n.wfn_id='"+node_id+"') and l.wfl_pid='"+id+"' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0') and wfn_pid='"+id+"'";
		//节点前面的line：wfl_arrow(0：没有，1：开始，2：结束，3：双向), xbasemode
		String sql_lineType = "select l.wfl_arrow,l.wfl_xbasemode from WF_LINE l where l.WFL_wBASEMODE in (select n.wfn_moduleid from wf_node n where n.wfn_id='"+node_id+"') and l.wfl_pid='"+id+"'  and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0'";
		List<Object[]> list = super.getEm().createNativeQuery(sql_lineType).getResultList();
		boolean flag = false;
		if(list==null || list.size()==0){
			 String historyLine = "select l.wfl_arrow,l.WFL_XBASEMODE from wf_line_histroy l " +
			 		"where l.WFL_wBASEMODE in (select n.WFN_MODULEID from WF_NODE_histroy n where n.WFN_ID='"+node_id+"')" +
			 		" and l.wfl_pid='"+id+"' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0'";
			 flag = true;
			 list = super.getEm().createNativeQuery(historyLine).getResultList();
		}
		List<WfNode> retlist = new ArrayList<WfNode>();
		for(int i=0; list!=null && i<list.size(); i++){
			Object[] data = (Object[])list.get(i);
			String type = data[0].toString();
			String wfl_xbasemode = data[1].toString();
			String lineTableName = "wf_line";
			if(flag){
				lineTableName = "wf_line_histroy";
			}
			if("3".equals(type)){
				String sql2 = "select * from wf_node wf where  wf.wfn_isdisplay!='1' and wf.wfn_moduleid " +
						"in(select l.wfl_xbasemode from "+lineTableName+" l " +
						"where l.wfl_wbasemode=(select n.wfn_moduleid from wf_node_histroy n where n.wfn_pid='"+id+"' and n.wfn_id='"+node_id+"')" +
								" and l.wfl_pid='"+id+"' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0' and l.wfl_xbasemode = '"+wfl_xbasemode+"') " +
								" and wfn_pid='"+id+"'";
				Query query2 = super.getEm().createNativeQuery(sql2,WfNode.class);
				retlist.addAll(query2.getResultList());
			}
		}
		Query query1=super.getEm().createNativeQuery(sql,WfNode.class);
		retlist.addAll(query1.getResultList());
		List<WfNode> noideList = new ArrayList<WfNode>();
		WfNode node = null;
		for(int i=0; i<retlist.size(); i++){
			node = retlist.get(i);
			if(node.getWfn_isdisplay()==null || node.getWfn_isdisplay()==0){
				noideList.add(node);
			}
		}
		sort(noideList,true);
		return noideList;
	}
	
	private void sort(List<WfNode> list,boolean isAsc){
		Collections.sort(list,new Comparator<WfNode>(){
			public int compare(WfNode o1,WfNode o2){
				Integer f1 = (o1.getWfn_sortNumber()==null?0:o1.getWfn_sortNumber());
				Integer f2 = (o2.getWfn_sortNumber()==null?0:o2.getWfn_sortNumber());
					return  f1-f2;
			}
		});
		if(!isAsc){
			Collections.reverse(list);
		}
	}
	
	public List<WfChild> showChildOfWf(String id, String node_id) {
		String sql="select * from wf_child wfc where wfc.wfc_moduleid in(select l.wfl_wbasemode from wf_line l where l.wfl_xbasemode=(select n.wfn_moduleid from wf_node n where n.wfn_pid='"+id+"' and n.wfn_id='"+node_id+"') and l.wfl_pid='"+id+"' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='1') and wfc_pid='"+id+"'";
		return super.getEm().createNativeQuery(sql,WfChild.class).getResultList();
	}
	
	public void saveWorkFlow(WfMain main){
		super.getEm().persist(main);
	}

	public WfMain getWfMainById(String id) {
		return getEm().find(WfMain.class, id);
	}

	public void deleteWorkFlowById(String id) {
		super.getEm().createQuery("delete from WfMain where wfm_id='"+id+"'").executeUpdate();
	}
	
	public WfNode getWfNode(String id){
		WfNode node =  super.getEm().find(WfNode.class, id);
		if(node==null){
			//到histroy中查询
			String sql = " from WfHistoryNode t where t.wfn_id='"+id+"'";
			WfHistoryNode historyNode  = (WfHistoryNode)getEm().createQuery
					(sql).getSingleResult();
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
		}
		return node;
	}

	public void updateWfMain(WfMain wfMain) {
		super.getEm().merge(wfMain);
	}

	public WfXml getWfXml(String id) {
		
		return super.getEm().find(WfXml.class, id);
	}

	public void saveWfXml(WfXml wfXml) {
		super.getEm().persist(wfXml);
	}

	public void updateWfXml(WfXml wfXml) {
		super.getEm().merge(wfXml);
	}

	public void deleteWfXml(String id) {
		super.getEm().createQuery("delete from WfXml where wfx_id='"+id+"'").executeUpdate();
	}

	public WfNode findFirstNodeId(String workFlowId) {
//		String sql="select wf.* from wf_node wf where wf.wfn_pid='"+workFlowId+"' and wf.wfn_type='start'";
		String sql = "select *\n" +
					"  from wf_node wf\n" + 
					" where wf.wfn_moduleid in (select l.wfl_wbasemode\n" + 
					"                             from wf_line l\n" + 
					"                            where l.wfl_xbasemode =\n" + 
					"                                  (select n.wfn_moduleid\n" + 
					"                                     from wf_node n\n" + 
					"                                    where n.wfn_pid = '"+workFlowId+"'\n" + 
					"                                     and wfn_type='start')\n" + 
					"                              and l.wfl_pid = '"+workFlowId+"' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0')\n" + 
					"   and wfn_pid = '"+workFlowId+"'";
		List<WfNode>  list = getEm().createNativeQuery(sql,WfNode.class).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<WfNode> getNodesByPid(String pid){
		String hql="select t.* from wf_node t where t.wfn_pid='"+pid+"' order by t.wfn_sortNumber asc";
		Query query=super.getEm().createNativeQuery(hql, WfNode.class);
		return query.getResultList();
	}

	public String findFormLocaltion(String formId) {
		String sql="select wf.form_jspfilename from T_WF_CORE_FORM wf where wf.id='"+formId+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		return (list!=null && list.size()>0)?list.get(0).toString():"";
	}

	
	public List<WfMain> getWfMainListByRetrieval(String wfname,String province,String begin_rq,String end_rq,Integer pageindex, Integer pagesize) {
		String hql="from WfMain w where wfm_name like '%"+wfname+"%' order by wfm_modifytime desc";
		Query query=super.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public WfNode findFormIdByNodeId(String nextNodeId) {
		if(CommonUtil.stringIsNULL(nextNodeId)){
			return null;
		}
		WfNode node = getEm().find(WfNode.class, nextNodeId);
		if(node==null){
			//到histroy中查询
			String sql2 = " from WfHistoryNode t where t.wfn_id='"+nextNodeId+"'";
			WfHistoryNode historyNode  = (WfHistoryNode)getEm().createQuery
					(sql2).getSingleResult();
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
		}
		return node;
	}
	public List<WfNode> getSortNode(String workflowId) {
		String sql =  "SELECT * FROM wf_node  where  WFN_COMMENT_SORT = '1' and  wfn_pid = '"+workflowId+"'";
		return getEm().createNativeQuery(sql,WfNode.class).getResultList();
	}

	public int getCountFromWfMain(String roleIds, String conditionSql) {
		if(roleIds!=null && !roleIds.trim().equals("")){
			String hql = "";
			if(roleIds.equals("all")){
				hql = "select count(1) from wf_main w where 1=1 ";	
			}else{
				hql="select count(distinct(w.wfm_id)) from wf_main w, t_wf_core_wfmain_role t2" +
						" where t2.wfmainid = w.wfm_id and t2.roleId in ("+roleIds+")";
			}
			if(conditionSql!=null && !conditionSql.equals("")){
				hql+= conditionSql;
			}
			return Integer.parseInt(super.getEm().createNativeQuery(hql).getSingleResult().toString());
		}else{
			return 0;
		}
	}

	public int getCountFromWfMainByRetrieval(String wfname, String province,
			String begin_rq, String end_rq) {
		String hql="from WfMain w where wfm_name like '%"+wfname+"%'";
		Query query=super.getEm().createQuery(hql);
		return query.getResultList().size();
	}

	public List<WfMain> getWfMainList() {
		String hql=" from WfMain w";
		return super.getEm().createQuery(hql).getResultList();
	}

	public WfLine findWfLineByNodeId(String workFlowId, String nextNodeId,String nodeId) {
		String sql = "select wfl.* from wf_line wfl where wfl.wfl_wbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"+nextNodeId+"') and wfl.wfl_xbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"+nodeId+"') and wfl.wfl_pid='"+workFlowId+"'";
		String sqlTwoWay = "select wfl.* from wf_line wfl where wfl.wfl_xbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"+nextNodeId+"') and wfl.wfl_wbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"+nodeId+"') and wfl.wfl_pid='"+workFlowId+"'";
		Query query = super.getEm().createNativeQuery(sql,WfLine.class);
		Query queryTwoWay = super.getEm().createNativeQuery(sqlTwoWay,WfLine.class);
		WfLine wfLine = new WfLine();
		if (query.getResultList().size() > 0) {
			wfLine = (WfLine) query.getSingleResult();
		} else if (queryTwoWay.getResultList().size() > 0) {
			wfLine = (WfLine) queryTwoWay.getSingleResult();
		}
		return wfLine;
	}

	public int getWfProcessCountByWfUId(String workFlowId) {
		String hql="select count(*) from t_wf_process p where p.wf_uid='"+workFlowId+"'";
		return Integer.parseInt(getEm().createNativeQuery(hql).getSingleResult().toString());
		
	}

	public int getCountDoFiles() {
		String hql="from DoFile where (isDelete!=1 or isDelete is  null)  ";
		return super.getEm().createQuery(hql).getResultList().size();
	}

	public String findValue(String tableName, String columnName, String formId,String workFlowId,String instanceId) {
		String sql = "select t."+columnName +" from "+tableName+" t where t.formid='"+formId+"' and t.workflowid='"+workFlowId+"' and t.instanceid='"+instanceId+"'";
		Query query = getEm().createNativeQuery(sql);
		if(query.getResultList().size() >0 ){
			if(query.getResultList().get(0)!=null){
				return query.getResultList().get(0).toString();
			}else{
				return "";
			}
		}else {
			return "";
		}
	}

	public List<WfNode> getWfNodeList(String workFlowId) {
		String sql = "select t.* from wf_node t where t.wfn_pid='"+workFlowId+"' and t.wfn_type is null order by t.wfn_moduleid";
		return getEm().createNativeQuery(sql, WfNode.class).getResultList();
	}

	public List<String> getAllProcedures() throws DataAccessException {
		String sql = "select object_name,owner from all_objects where object_type='PROCEDURE' and owner='WORKFLOW' and object_name like 'WORKFLOW_NODE_%' order by object_name";
		List<Object[]> list=getEm().createNativeQuery(sql).getResultList();
		List<String> returnList=null;
		if (list!=null&&list.size()>0) {
			returnList=new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				returnList.add(list.get(i)[0].toString());
			}
		}
		return returnList;
	}

	public void save(WfBackNode wfBackNode) {
		if(wfBackNode != null){
			super.getEm().persist(wfBackNode);
		}
	}

	public List<WfBackNode> getBackNodeListByWfId(String workflowId,String nextNodeId) {
		String sql = "select t.* from T_WF_CORE_BACKNODE t where t.workflowid='"+workflowId+"' and t.formnodeid='"+nextNodeId+"' order by t.datetime ";
		return getEm().createNativeQuery(sql, WfBackNode.class).getResultList();
	}

	public void delete(String workflowId, String wfBackNodeId) {
		String sql = "delete from T_WF_CORE_BACKNODE t where t.workflowid='"+workflowId+"' and t.id='"+wfBackNodeId+"'";
	    getEm().createNativeQuery(sql).executeUpdate();
	}

	public List<WfBackNode> getBackNodeListByWfId(String workflowId) {
		String sql = "select t.* from T_WF_CORE_BACKNODE t where t.workflowid= :workflowId order by t.datetime ";
		return getEm().createNativeQuery(sql, WfBackNode.class).setParameter("workflowId", workflowId).getResultList();
	}

	public WfBackNode getBackNodeByBackNodeId(String workflowId, String backNodeId) {
		String sql = "select t.* from T_WF_CORE_BACKNODE t where t.workflowid='"+workflowId+"' and t.id='"+backNodeId+"' order by t.datetime ";
		return (WfBackNode) getEm().createNativeQuery(sql, WfBackNode.class).getSingleResult();
	}

	public void update(WfBackNode wfbackNode) {
		super.getEm().merge(wfbackNode);
	}

	public WfChild getWfChildByCid(String cid) {
		String hql="from WfChild w where wfc_cid='"+cid+"'";
		Query query=super.getEm().createQuery(hql);
		if(query.getResultList().size()>0){
			return (WfChild) query.getResultList().get(0);
		}else{
			return null;
		} 
	}

	public List<Object[]> getListWf(String id) {
		String sql = "select w.wfm_id, w.wfm_name,w.wfm_modifytime," +
							"w.wfm_createtime,w.wfm_status from wf_main w where w.wfm_status = '2' ";
		
		if(id != null && !"".equals(id)){
			sql += " and w.wfm_id != '"+id+"'";
		}
		sql +=" order by w.wfm_createtime desc";
		Query query = getEm().createNativeQuery(sql.toString());

		return query.getResultList();
	}

	@Override
	public WfChild getWfChildById(String wfc_id) {
		return getEm().find(WfChild.class,wfc_id);
	}

	@Override
	public void deleteWfChild(String wfc_id) {
		super.getEm().createQuery("delete from WfChild where wfc_id='"+wfc_id+"'").executeUpdate();
	}

	@Override
	public void saveWfChild(WfChild wfChild) {
		super.getEm().persist(wfChild);
	}

	@Override
	public List<WfLine> findNextWfLineByNodeId(String nextNodeId, String workFlowId) {
		String sql = "select wfl.* from WF_LINE wfl where wfl.wfl_xbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"+nextNodeId+"') and wfl.wfl_pid='"+workFlowId+"'";
		return super.getEm().createNativeQuery(sql,WfLine.class).getResultList();
	}

	@Override
	public WfChild getWfChildByModuleId(String moduleId, String workFlowId) {
		String sql = "select t.* from wf_child t where t.wfc_moduleid='"+moduleId+"' and t.wfc_pid='"+workFlowId+"'";
		return (WfChild) super.getEm().createNativeQuery(sql,WfChild.class).getSingleResult();
	}

	@Override
	public List<WfNode> getNextNodeByChildWf(String id, String child_module) {
		String sql="select * from wf_node wf where wf.wfn_moduleid in (select l.wfl_wbasemode from wf_line l where l.wfl_xbasemode='"+child_module+"' and l.wfl_pid='"+id+"' and l.wfl_xbasemode_type='1' and l.wfl_wbasemode_type='0') and wfn_pid='"+id+"'";
		return super.getEm().createNativeQuery(sql,WfNode.class).getResultList();
	}
	
	@Override
	public void updateNode(String wfn_pid, String wfn_id) {
		String sql = "update wf_node  t set t.wfn_pid = '"+wfn_pid+"' where t.wfn_id = '"+wfn_id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}
	
	@Override
	public void updateLine(String wfm_pid, String wfn_id) {
		String sql = "update wf_line t set t.wfl_pid = '"+wfm_pid+"' where t.wfl_id = '"+wfn_id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public List<WfNode> getNodesByids(String nodeid) {
		String hql = "from WfNode where wfn_id in ('"+nodeid.replace(",", "','")+"')";
		return super.getEm().createQuery(hql).getResultList();
	}
	
	@Override
	public String findFormPdf(String formId) {
		String sql="select wf.form_pdffilename from T_WF_CORE_FORM wf where wf.id='"+formId+"'";
		return getEm().createNativeQuery(sql).getSingleResult().toString();
	}

	@Override
	public WfNode findNodeById(String nodeId) {
		String hql = "from WfNode r where r.wfn_id= '"+nodeId+"'";
		List<WfNode> list = getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public WfChild getWfChildByPidAndCid(String wfc_cid, String wfc_pid) {
		String hql ="select t.*  from wf_child t where t.wfc_cid='"+wfc_cid+"' and t.wfc_pid='"+wfc_pid+"'";
		List<WfChild> list = getEm().createNativeQuery(hql, WfChild.class).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addNodeToHistroy(String wfm_id) {
		String sql = "insert into  wf_node_histroy  value " +
				"select * from wf_node " +
				"where wf_node.wfn_id not in (select k.wfn_id from  wf_node_histroy k) " +
				"and wf_node.wfn_pid = '"+wfm_id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void addLineToHistroy(String wfm_id) {
		String sql = "insert into  wf_line_histroy value " +
				"select * from wf_line " +
				"where wf_line.wfl_id not in (select k.wfl_id from  wf_line_histroy k) " +
				"and wf_line.wfl_pid = '"+wfm_id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}
	
	@Override
	public WfNode getWfNodeByModuleId(String moduleId, String workFlowId) {
		String sql = "select t.* from wf_node t where t.wfn_moduleid='"+moduleId+"' and t.wfn_pid='"+workFlowId+"'";
		return (WfNode) super.getEm().createNativeQuery(sql,WfNode.class).getSingleResult();
	}
	
	@Override
	public WfMain addWorkFlow(WfMain wfMain) {
		 getEm().persist(wfMain);
		 return wfMain;
	}
	
	@Override
	public WfNode saveWfNode(WfNode wfNode) throws Exception {
		getEm().persist(wfNode);
		return wfNode;
	}
	
	@Override
	public WfLine saveWfLine(WfLine wfLine) throws Exception {
		getEm().persist(wfLine);
		return wfLine;
	}

	@Override
	public WfLine getLineById(String wfl_id) {
		String hql = "from WfLine l where l.wfl_id = '"+wfl_id+"'";
		List<WfLine> list = getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String geneBackNodeSql(WfBackNode back, String newBackId, String newWfm_id, Map<String, String> nodeIds) {
		
		String geneSql = "insert into T_WF_CORE_BACKNODE(ID,FORMNODEID,TONODEID,WORKFLOWID,FROMNODENAME," +
				"TONODENAME,DATETIME) values(";
		if(back.getFromNodeId()!= null){
			if(nodeIds.containsKey(back.getFromNodeId())){
				back.setFromNodeId(nodeIds.get(back.getFromNodeId()));
			}else{
				back.setFromNodeId("");
			}
		}else{
			back.setFromNodeId("");
		}
		
		if(back.getToNodeId()!= null){
			if(nodeIds.containsKey(back.getToNodeId())){
				back.setToNodeId(nodeIds.get(back.getToNodeId()));
			}else{
				back.setToNodeId("");
			}
		}else{
			back.setToNodeId("");
		}
		
		back.setWorkflowId(newWfm_id);
		geneSql += "'"+newBackId+"','"+back.getFromNodeId()+"','"+back.getToNodeId()+"'," +
				"'"+back.getWorkflowId()+"','"+back.getFromNodeName()+"','"+back.getToNodeName()+"',sysdate);";
		return geneSql;
	}

	@Override
	public List<String> geneCoreFieldsSql(String oldTableId, String newTableId, String newWfm_id) {
		List<String> sqls = new ArrayList<String>();
		String hql = "from WfFieldInfo where i_tableid = '"+oldTableId+"'";
		Query query = getEm().createQuery(hql);
		List<WfFieldInfo> fields = query.getResultList();
		String geneSql = "insert into T_WF_CORE_FIELDINFO (ID,VC_NAME,VC_FIELDNAME,I_TABLEID,I_FIELDTYPE,B_VALUE,VC_COMMENT,VC_VALUE,I_LENGTH,I_ORDERID) values(";
		for(int f=0 ; f< fields.size() ; f++){
			WfFieldInfo filed = fields.get(f);
			filed.setVc_comment(filed.getVc_comment()==null?"":filed.getVc_comment());
			filed.setI_length(filed.getI_length()==null?"":filed.getI_length());
			filed.setVc_value(filed.getVc_value()==null?"":filed.getVc_value());
			String values = "sys_guid(),'"+filed.getVc_name()+"','"+filed.getVc_fieldname()+"'," +
				        	"'"+newTableId+"','"+filed.getI_fieldtype()+"','"+filed.getB_value()+"'," +
				        	"'"+filed.getVc_comment()+"','"+filed.getVc_value()+"','"+filed.getI_length()+"',"+filed.getI_orderid()+")";
			sqls.add(geneSql+values+";");
		}
		return sqls;
	}

	@Override
	public String geneCoreForm(ZwkjForm zwkjForm, String newFormId, String htm_36, String jsp_36, String workflowId) {
		htm_36 = htm_36+".html";
		jsp_36 = jsp_36+".jsp";
		zwkjForm.setInsert_table(zwkjForm.getInsert_table()==null?"":zwkjForm.getInsert_table());
		String geneSql = "insert into T_WF_CORE_FORM(ID,FORM_NAME,FORM_CAPTION,FORM_HTMLFILENAME,FORM_JSPFILENAME,INTIME,WORKFLOWID,INSERT_TABLE) values";//,IS_NESTED
		geneSql += "('"+newFormId+"','"+zwkjForm.getForm_name()+"','"+zwkjForm.getForm_caption()+"'," +
				"'"+htm_36+"','"+jsp_36+"',sysdate,'"+workflowId+"','"+zwkjForm.getInsert_table()+"')";//,'"+zwkjForm.getIsNested()+"'
		return geneSql+";";
	}

	@Override
	public String geneCoreOfficeSql(String oldTableId, String vc_tablename) {
		String hql = "from WfFieldInfo where i_tableid = '"+oldTableId+"' or i_tableid is null ";
		Query query = getEm().createQuery(hql);
		List<WfFieldInfo> fields = query.getResultList();
		StringBuffer sb = new StringBuffer();
		sb.append(" create table " + vc_tablename + "(");
		WfFieldInfo wfField = null;
		for(int i = 0; fields != null && fields.size() > i; i++){
			wfField = fields.get(i);
			if(wfField.getVc_fieldname()!= null && !"".equals(wfField.getVc_fieldname())){
				sb.append(wfField.getVc_fieldname()+" ");
			}
			if("0".equals(wfField.getI_fieldtype())){
				sb.append("varchar2("+wfField.getI_length()+") ");
			}else if("1".equals(wfField.getI_fieldtype())){
				sb.append("date ");
			}else if("2".equals(wfField.getI_fieldtype())){
				sb.append("number ");
			}else if("3".equals(wfField.getI_fieldtype())) {
				sb.append("clob ");
			}
			if("0".equals(wfField.getI_fieldtype()) && wfField.getVc_value() != null 
					&& !"".equals(wfField.getVc_value())){
				sb.append(" default "+wfField.getVc_value());
			}
			if(!"1".equals(wfField.getB_value())){
				sb.append(" not null");
			}
			if(i < fields.size() - 1){
				sb.append(",");
			}
			if(i == fields.size() - 1){
				sb.append(");");
			}
		}
		return sb.toString();
	
	}

	@Override
	public String geneCoreTableSql(String oldTableId, String newTableId, String newWfm_id) {
		String geneSql = "insert into T_WF_CORE_TABLE (ID,VC_NAME,VC_TABLENAME,VC_CREATDATE,LCID,reflc) values";
		geneSql += " ('"+newTableId+"',";
		String hql = "from WfTableInfo where id = '"+oldTableId+"'";
		Query query = getEm().createQuery(hql);
		WfTableInfo table = (WfTableInfo) query.getResultList().get(0);
		geneSql += 	"'"+table.getVc_name()+"','"+table.getVc_tablename()+"',sysdate,'"+newWfm_id+"',',')";
		return geneSql+";";
	}

	@Override
	public String geneInnerUsers(String id, String newInnerUserId, String newWfm_id) {
		String hql = "from InnerUser i where i.id = '"+id+"'";
		Query query = getEm().createQuery(hql);
		InnerUser inner = (InnerUser) query.getResultList().get(0);
		String geneSql = "insert into t_wf_core_inneruser (ID,NAME,INTIME,ZINDEX,TYPE,WORKFLOWID) values(";
		       geneSql +="'"+newInnerUserId+"','"+inner.getName()+"',sysdate,"+inner.getZindex()+","+inner.getType()+",'"+newWfm_id+"');";
		       return geneSql;
	}

	@Override
	public List<String> geneLabels(String id, String newTemplateId, Map<String, String> tableIds) {
		
		List<String> sqls = new ArrayList<String>();
		String hql = "from WfLabel where vc_templateId = '"+id+"'";
		Query query = getEm().createQuery(hql);
		List<WfLabel> labels = query.getResultList();
		String geneSql = "insert into T_WF_CORE_LABELFIELD(ID,VC_LABEL,VC_TABLEID,VC_TABLENAME,VC_FIELDID,VC_FIELDNAME,VC_TEMPLATEID,VC_COMMENTID,VC_TYPE,VC_ATT,VC_LIST) values(";
		for(int i = 0; i < labels.size() ; i++ ){
			WfLabel label = labels.get(i);
			// 需要替换 tableid
			if(label.getVc_tableid()!=null){
				label.setVc_tableid(tableIds.get(label.getVc_tableid()));
			}else{
				label.setVc_tableid("");
			}
			label.setVc_commentId(label.getVc_commentId()==null?"":label.getVc_commentId());
			label.setVc_type(label.getVc_type()==null?"":label.getVc_type());
			label.setVc_att(label.getVc_att()==null?"":label.getVc_att().replace("$", "$$"));
			label.setVc_list(label.getVc_list()==null?"":label.getVc_list());
			label.setVc_tablename(label.getVc_tablename()==null?"":label.getVc_tablename());
			// fieldid 暂时没有传新的字段过来 先置空
			label.setVc_fieldid("");
			label.setVc_fieldname(label.getVc_fieldname()==null?"":label.getVc_fieldname());
			String values = "sys_guid(),'"+label.getVc_label()+"','"+label.getVc_tableid()+"','"+label.getVc_tablename()+"'," +
						    "'"+label.getVc_fieldid()+"','"+label.getVc_fieldname()+"','"+newTemplateId+"','"+label.getVc_commentId()+"'," +
							"'"+label.getVc_type()+"','"+label.getVc_att()+"','"+label.getVc_list()+"');";
			sqls.add(geneSql+values);
		}
		
		return sqls;
	}

	@Override
	public String geneLineSql(String id, String newLineId, String newWfm_id) {
		String fromSql = "select l.wfl_xbasemode,l.wfl_wbasemode,l.wfl_arrow,l.wfl_route_type,l.wfl_conditions,l.wfl_xbasemode_type,l.wfl_wbasemode_type,l.wfl_choice_condition,l.WFL_REMARK,l.wfl_choice_rule from wf_line l where l.wfl_id = '"+id+"'";
		Query query = getEm().createNativeQuery(fromSql);
		List<Object[]> list = query.getResultList();
		String geneSql = "insert into wf_line(wfl_pid,wfl_id,wfl_xbasemode,wfl_wbasemode,wfl_arrow,wfl_route_type,wfl_conditions,wfl_xbasemode_type,wfl_wbasemode_type,wfl_choice_condition,WFL_REMARK,wfl_choice_rule) values(";
		
		if(list != null && list.size()>0){
			Object[] obj = list.get(0);
			obj[0] = obj[0]==null ?"":obj[0].toString(); //wfl_xbasemode
			obj[1] = obj[1]==null ?"":obj[1].toString();//wfl_wbasemode
			obj[2] = obj[2]==null ?"":obj[2].toString();//wfl_arrow
			obj[3] = obj[3]==null ?"":obj[3].toString();//wfl_route_type
			obj[4] = obj[4]==null ?"":obj[4].toString();//wfl_conditions
			obj[5] = obj[5]==null ?"":obj[5].toString();//wfl_xbasemode_type
			obj[6] = obj[6]==null ?"":obj[6].toString();//wfl_wbasemode_type
			obj[7] = obj[7]==null ?"":obj[7].toString();//wfl_choice_condition
			obj[8] = obj[8]==null ?"":obj[8].toString();//WFL_REMARK
			obj[9] = obj[9]==null ?"":obj[9].toString();//wfl_choice_rule

			geneSql+= "'"+newWfm_id+"','"+newLineId+"','"+obj[0]+"'," +
					"'"+obj[1]+"','"+obj[2]+"','"+obj[3]+"','"+obj[4]+"'," +
					"'"+obj[5]+"','"+obj[6]+"','"+obj[7]+"','"+obj[8]+"'," +
					"'"+obj[9]+"');";
		}
		return geneSql;
	}

	@Override
	public String geneMainSql(String id, String newWfm_id, Map<String, String> tableIds) {
		String oldSql = "select t.wfm_name,t.wfm_calendar,t.wfm_defaultform,t.wfm_inittasks,t.wfm_status,t.wfm_title_table,t.wfm_title_name from wf_main t where t.WFM_ID = '"+id+"'";
		Query query = getEm().createNativeQuery(oldSql);
		List<Object[]> list = query.getResultList();
		String geneSql = "insert into wf_main(WFM_ID,WFM_CREATETIME,WFM_MODIFYTIME,wfm_name,wfm_calendar,wfm_defaultform,wfm_inittasks,wfm_status,wfm_title_table,wfm_title_name) values(";
		if(list != null && list.size()>0){
			Object[] obj = list.get(0);
			obj[0] = obj[0]==null ?"":obj[0].toString(); //wfm_name
			obj[1] = obj[1]==null ?"":obj[1].toString();//wfm_calendar
			obj[2] = obj[2]==null ?"0":obj[2].toString();//wfm_defaultform
			obj[3] = obj[3]==null ?"":obj[3].toString();//wfm_inittasks
			obj[4] = obj[4]==null ?"":obj[4].toString();//wfm_status
			//wfm_title_table 需要重新选择  不使用下面的方法是因为 这边的表可能是新增的也可能是应用的 
			obj[5] = "";
			/*if(obj[5]!= null){  
				if(tableIds.containsKey(obj[5])){
					obj[5] = tableIds.get(obj[5]);
				}else{
					obj[5] = "";
				}
			}else{
				obj[5] = "";
			}*/
			obj[6] = obj[6]==null ?"":obj[6].toString();//wfm_title_name
			geneSql+= "'"+newWfm_id+"',to_char(sysdate,'yyyy-MM-dd HH24:mi'),to_char(sysdate,'yyyy-MM-dd HH24:mi'),'"+obj[0]+"'," +
					"'"+obj[1]+"','"+obj[2]+"','"+obj[3]+"','"+obj[4]+"'," +
					"'"+obj[5]+"','"+obj[6]+"');";
		}
		return geneSql;
	}

	@Override
	public List<String> geneMapColumns(String id, String newFormId) {

		List<String> sqls = new ArrayList<String>();
		String hql = "from FormTagMapColumn where formid = '"+id+"'";
		Query query = getEm().createQuery(hql);
		List<FormTagMapColumn> columns = query.getResultList();
		String geneSql = "insert into T_WF_CORE_FORM_MAP_COLUMN(ID,FORMID,FORMTAGNAME,FORMTAGTYPE,TAGTYPE,TABLENAME,COLUMNNAME,DICDATA,OTHERDATA,SORT_INDEX," +
				"SELECTDIC,LISTID,COLUMNCNAME,DOC_COLUMN,SERVERPLUGIN_ID,ASSIGNTABLENAME,ASSIGNCOLUMNNAM) values(";
		for(int f=0 ; f< columns.size() ; f++){
			FormTagMapColumn column = columns.get(f);
			column.setColumnname(column.getColumnname()==null?"":column.getColumnname());
			column.setDicdata(column.getDicdata()==null?"":column.getDicdata());
			column.setOtherData(column.getOtherData()==null?"":column.getOtherData());
			column.setSelectDic(column.getSelectDic()==null?"":column.getSelectDic());
			column.setListId(column.getListId()==null?"":column.getListId());
			column.setColumnCname(column.getColumnCname()==null?"":column.getColumnCname());
			column.setDocColumn(column.getDocColumn()==null?"":column.getDocColumn());
			column.setServerPlugin_id(column.getServerPlugin_id()==null?"":column.getServerPlugin_id());
			//column.setAssociated(column.getAssociated()==null?"":column.getAssociated());
			column.setAssignColumnName(column.getAssignColumnName()==null?"":column.getAssignColumnName());
			column.setAssignTableName(column.getAssignTableName()==null?"":column.getAssignTableName());
			column.setColumnCname(column.getColumnCname()==null?"":column.getColumnCname());
			column.setFormtagname(column.getFormtagname()==null?"":column.getFormtagname().replace("$", "$$"));
			String values = "sys_guid(),'"+newFormId+"','"+column.getFormtagname()+"','"+column.getFormtagtype()+"'," +
							"'"+column.getTagtype()+"','"+column.getTablename()+"','"+column.getColumnname()+"'," +
							"'"+column.getDicdata()+"','"+column.getOtherData()+"',"+column.getSortIndex()+"," +
							"'"+column.getSelectDic()+"','"+column.getListId()+"','"+column.getColumnCname()+"'," +
							"'"+column.getDocColumn()+"','"+column.getServerPlugin_id()+"','"+column.getAssignTableName()+"'," +
									"'"+column.getAssignColumnName()+"')";//,'"+column.getAssociated()+"'
			sqls.add(geneSql+values+";");
		}
		return sqls;
	
	}

	@Override
	public String geneNodeSql(String id, String newNodeId, String newWfm_id, Map<String, String> innerUserIds, Map<String, String> templateIds,
			Map<String, String> formIds) {
		String fromSql = " select t.wfn_name,t.wfn_deadline,t.wfn_deadlineunit,t.wfn_inittask,t.wfn_defaulttemplate,t.wfn_defaultcalendar,t.wfn_defaultform "+
		" ,t.wfn_global_process_custom,t.wfn_current_process_custom,t.wfn_staff,t.wfn_allow_consultation,t.wfn_allow_delegation,t.wfn_allow_cc "+
		" ,t.wfn_type,t.wfn_moduleid,t.wfn_deadline_isworkday,t.wfn_procedure_name,t.wfn_bd_user,t.wfn_route_type," +//18
		" t.WFN_MODIFYTIME,t.ACTION_STATUS,t.WFN_ISEXCHANGE,t.WFL_CHILD_MERGE,t.WFN_FORM_CONTINUE,t.WFN_ISZF,t.WFN_ISWCSX,"+
		" t.WFN_TXNR,t.TXNR_TXNRIDS,t.WFN_TQTXSJLINE,t.WFN_ISSEAL,t.WFN_ALLOWFAIR," +
		" t.OPERATE_LOG_ID,t.WFN_ALLOWPRINT,t.WFN_SEND_FILE,t.WFN_ISDEFDEP,t.NODE_STARTJB,t.NODE_ISREPLY," +
		" t.WFN_ISLHFW,t.WFN_ISDISPLAY,t.WFN_ISUPLOADATTACH,t.WFN_DOUBLESCREEN,t.WFN_REDTAPETEMPLATE,t.WFN_SORTNUMBER" +//42
		" from wf_node t where t.wfn_id = '"+id+"'";
		Query query = getEm().createNativeQuery(fromSql);
		List<Object[]> list = query.getResultList();
		String geneSql = "insert into wf_node(WFN_ID,WFN_PID,WFN_CREATETIME,wfn_name,wfn_deadline,wfn_deadlineunit,wfn_inittask,wfn_defaulttemplate,wfn_defaultcalendar,wfn_defaultform "+
		" ,wfn_global_process_custom,wfn_current_process_custom,wfn_staff,wfn_allow_consultation,wfn_allow_delegation,wfn_allow_cc "+
		" ,wfn_type,wfn_moduleid,wfn_deadline_isworkday,wfn_procedure_name,wfn_bd_user,wfn_route_type,"+
		" WFN_MODIFYTIME,ACTION_STATUS,WFN_ISEXCHANGE,WFL_CHILD_MERGE,WFN_FORM_CONTINUE,WFN_ISZF,WFN_ISWCSX,"+
		" WFN_TXNR,TXNR_TXNRIDS,WFN_TQTXSJLINE,WFN_ISSEAL,WFN_ALLOWFAIR," +
		" OPERATE_LOG_ID,WFN_ALLOWPRINT,WFN_SEND_FILE,WFN_ISDEFDEP,NODE_STARTJB,NODE_ISREPLY," +
		" WFN_ISLHFW,WFN_ISDISPLAY,WFN_ISUPLOADATTACH,WFN_DOUBLESCREEN,WFN_REDTAPETEMPLATE,WFN_SORTNUMBER" +//42
		" ) values(";
if(list != null && list.size()>0){
	Object[] obj = list.get(0);
	obj[0] = obj[0]==null ?"":obj[0].toString(); //wfn_name
	obj[1] = obj[1]==null ?"":obj[1].toString();//wfn_deadline
	obj[2] = obj[2]==null ?"0":obj[2].toString();//wfn_deadlineunit
	obj[3] = obj[3]==null ?"":obj[3].toString();//wfn_inittask
	//wfn_defaulttemplate 套打模板替换值
	if(obj[4]!= null){  
		if(templateIds.containsKey(obj[4])){
			obj[4] = templateIds.get(obj[4]);
		}else{
			obj[4] = "";
		}
	}else{
		obj[4] = "";
	}
	obj[5] = obj[5]==null ?"":obj[5].toString();//wfn_defaultcalendar
	// wfn_defaultform form表单处理
	if(obj[6]!= null){  
		if(formIds.containsKey(obj[6])){
			obj[6] = formIds.get(obj[6]);
		}else{
			obj[6] = "";
		}
	}else{
		obj[6] = "";
	}
	obj[7] = obj[7]==null ?"":obj[7].toString();//wfn_global_process_custom
	obj[8] = obj[8]==null ?"":obj[8].toString();//wfn_current_process_custom
	//wfn_staff
	if(obj[9] != null){
		if(innerUserIds.containsKey(obj[9])){
			obj[9] = innerUserIds.get(obj[9]);
		}else{
			obj[9] = "";
		}
	}else{
		obj[9] = "";//wfn_staff
	}
	
	obj[10] = obj[10]==null ?"":obj[10].toString();//wfn_allow_consultation
	obj[11] = obj[11]==null ?"":obj[11].toString();//wfn_allow_delegation
	obj[12] = obj[12]==null ?"":obj[12].toString();//wfn_allow_cc
	obj[13] = obj[13]==null ?"":obj[13].toString();//wfn_type
	obj[14] = obj[14]==null ?"":obj[14].toString(); //wfn_moduleid
	obj[15] = obj[15]==null ?"":obj[15].toString();//wfn_deadline_isworkday
	obj[16] = obj[16]==null ?"":obj[16].toString();//wfn_procedure_name
	obj[17] = "";//wfn_bd_user
	obj[18] = obj[18]==null ?"":obj[18].toString();//wfn_route_type
	obj[19] = obj[19]==null ?"":obj[19].toString();
	obj[20] = obj[20]==null ?"":obj[20].toString();//
	obj[21] = obj[21]==null ?"":obj[21].toString();//
	obj[22] = obj[22]==null ?"":obj[22].toString();//
	obj[23] = obj[23]==null ?"":obj[23].toString();//
	obj[24] = obj[24]==null ?"":obj[24].toString();//
	obj[25] = obj[25]==null ?"":obj[25].toString();//
	obj[26] = obj[26]==null ?"":obj[26].toString();
	obj[27] = obj[27]==null ?"0":obj[27].toString();
	obj[28] = obj[28]==null ?"":obj[28].toString();
	obj[29] = obj[29]==null ?"":obj[29].toString();//
	obj[30] = obj[30]==null ?"":obj[30].toString();//
	obj[31] = obj[31]==null ?"":obj[31].toString();
	obj[32] = obj[32]==null ?"":obj[32].toString();//
	obj[33] = obj[33]==null ?"":obj[33].toString();//
	obj[34] = obj[34]==null ?"":obj[34].toString();//
	obj[35] = obj[35]==null ?"":obj[35].toString();
	obj[36] = obj[36]==null ?"":obj[36].toString();//
	obj[37] = obj[37]==null ?"":obj[37].toString();//
	obj[38] = obj[38]==null ?"":obj[38].toString();//
	obj[39] = obj[39]==null ?"":obj[39].toString();//
	obj[40] = obj[40]==null ?"":obj[40].toString();//
	obj[41] = obj[41]==null ?"":obj[41].toString();
	obj[42] = obj[42]==null ?"":obj[42].toString();//
	geneSql+= "'"+newNodeId+"','"+newWfm_id+"',to_char(sysdate,'yyyy-MM-dd HH24:mi'),'"+obj[0]+"'," +
			"'"+obj[1]+"','"+obj[2]+"','"+obj[3]+"','"+obj[4]+"'," +
			"'"+obj[5]+"','"+obj[6]+"','"+obj[7]+"','"+obj[8]+"'," +
			"'"+obj[9]+"','"+obj[10]+"','"+obj[11]+"'," +
			"'"+obj[12]+"','"+obj[13]+"','"+obj[14]+"'," +
			"'"+obj[15]+"','"+obj[16]+"','"+obj[17]+"'," +
			"'"+obj[18]+"','"+obj[19]+"','"+obj[20]+"','" +obj[21]+"','" +obj[22]+"','" +obj[23]+"','" +obj[24]+"','" 
			+obj[25]+"','" +obj[26]+"','" +obj[27]+"','" +obj[28]+"','" +obj[29]+"','" +obj[30]+"','" +obj[31]+"','" 
			+obj[32]+"','" +obj[33]+"','" +obj[34]+"','" +obj[35]+"','" +obj[36]+"','" +obj[37]+"','" +obj[38]+"','" 
			+obj[39]+"','" +obj[40]+"','" +obj[41]+"','" +obj[42]+"'" + ");";
}
return geneSql;
}

	@Override
	public String genePermitSql(WfFormPermit permit, String newPermitId, String newWfm_id, Map<String, String> tableIds,
			Map<String, String> innerUserIds, Map<String, String> formIds, Map<String, String> nodeIds) {
		
		String geneSql = "insert into T_WF_CORE_FORMPERMIT(ID,VC_FORMID,VC_TAGNAME,VC_ROLETYPE,VC_ROLEID," +
				"VC_LIMIT,I_ORDERID,VC_MISSIONID,VC_ROLENAME,LCID,NODETYPE) values(";
		// formid
		if(permit.getVc_formid()!= null){
			if(formIds.containsKey(permit.getVc_formid())){
				permit.setVc_formid(formIds.get(permit.getVc_formid()));
			}else{
				permit.setVc_formid("");
			}
		}else{
			permit.setVc_formid("");
		}
		// roleid
		if(permit.getVc_roleid()!= null){
			if(innerUserIds.containsKey(permit.getVc_roleid())){
				permit.setVc_roleid(innerUserIds.get(permit.getVc_roleid()));
			}else{
				permit.setVc_roleid("");
			}
		}else{
			permit.setVc_roleid("");
		}
		// nodeId
		if(permit.getVc_missionid()!= null){
			if(nodeIds.containsKey(permit.getVc_missionid())){
				permit.setVc_missionid(nodeIds.get(permit.getVc_missionid()));
			}else{
				permit.setVc_missionid("");
			}
		}else{
			permit.setVc_missionid("");
		}
		permit.setVc_tagname(permit.getVc_tagname()==null?"":permit.getVc_tagname().replace("$", "$$"));
		geneSql+= "'"+newPermitId+"','"+permit.getVc_formid()+"','"+permit.getVc_tagname()+"'," +
				  "'"+permit.getVc_roletype()+"','"+permit.getVc_roleid()+"','"+permit.getVc_limit()+"'," +
				  ""+permit.getI_orderid()+",'"+permit.getVc_missionid()+"','"+permit.getVc_rolename()+"'," +
				  "'"+newWfm_id+"','"+permit.getNodetype()+"');";
		return geneSql;
	}

/*	@Override
	public List<String> geneTabRelationColumns(String oldFormId, String newFormId, String htmlPath, Map<String, String> formIds) {
		String sql = "select t.* from t_wf_core_tabformrelation t where 1=1 ";
		if(oldFormId!=null && !oldFormId.equals("")){
			sql += " and formId = '"+oldFormId+"'";
		}
		sql += " order by t.sort asc";
		List<TabFormRelation> list = getEm().createNativeQuery(sql, TabFormRelation.class).getResultList();
		
		List<String> sqls = new ArrayList<String>();
		for(int i = 0; i < list.size() ; i++){
			TabFormRelation tabrelation = list.get(i);
			if(formIds.containsKey(tabrelation.getInnerFormId())){
				tabrelation.setInnerFormId(formIds.get(tabrelation.getInnerFormId()));
			}
			String geneSql = "insert into t_wf_core_tabformrelation(ID,FORMID,HTMLPATH,TABNAME,INNERFORMID," +
					"STATUS,SORT) values(";
			geneSql += "'"+UuidGenerator.generate36UUID()+"','"+newFormId+"','"+htmlPath+"','"+tabrelation.getTabName()+"'" +
					",'"+tabrelation.getInnerFormId()+"','"+tabrelation.getStatus()+"',"+tabrelation.getSort()+");";
			sqls.add(geneSql);
		}
		return sqls;
	}*/

	@Override
	public String geneTemplateSql(WfTemplate template, String newTemplateId, String doc_36, String newWfm_id) {
		
		template.setIsRedTape(template.getIsRedTape()==null?"":template.getIsRedTape());
		template.setReflcId(template.getReflcId()==null?"":template.getReflcId());
		template.setPosition(template.getPosition()==null?"":template.getPosition());
		template.setRelationId(template.getRelationId()==null?"":template.getRelationId());
		
		String geneSql = "insert into t_wf_core_template(ID,VC_CNAME,VC_ENAME,LX,GWZL,VC_PATH,LCID,C_CREATEDATE,ISREDTAPE,REFLCID,POSITION,relation_id) values";
		geneSql += "('"+newTemplateId+"','"+(CommonUtil.stringIsNULL(template.getVc_cname())?"":template.getVc_cname())+"','"+(CommonUtil.stringIsNULL(template.getVc_ename())?"":template.getVc_ename())+"'," +
				   "'"+(CommonUtil.stringIsNULL(template.getLx())?"":template.getLx())+"','"+(CommonUtil.stringIsNULL(template.getGwzl())?"":template.getGwzl())+"','"+doc_36+"','"+newWfm_id+"',sysdate," +
				   "'"+(CommonUtil.stringIsNULL(template.getIsRedTape())?"":template.getIsRedTape())+"','"+(CommonUtil.stringIsNULL(template.getReflcId())?"":template.getReflcId())+"','"+(CommonUtil.stringIsNULL(template.getPosition())?"":template.getPosition())+"'," +
				   		"'"+(CommonUtil.stringIsNULL(template.getRelationId())?"":template.getRelationId())+"')";
		return geneSql+";";
	}

	@Override
	public String geneXmlSql(String id, String newWfm_id, Map<String, String> innerUserIds, Map<String, String> templateIds,
			Map<String, String> formIds, Map<String, String> tableIds, Map<String, String> nodeIds, Map<String, String> lineIds) {
	    String hql ="from WfXml x where x.wfx_id = '"+id+"'";
	    Query query = getEm().createQuery(hql);
		List<WfXml> list = query.getResultList();
		String xml = "";
		
		if(list != null && list.size()>0){
			WfXml wfxml = list.get(0);
			xml = wfxml.getWfx_xml();
			// 1. 替换流程id
			xml.replace(id, newWfm_id);
			// 2. 替换 innerUserIds 用户组
			String old_innerUserId ="";
			String new_innerUserId ="";
			for(String key : innerUserIds.keySet()){
				old_innerUserId = key;
				new_innerUserId = innerUserIds.get(old_innerUserId);
				xml = xml.replace(old_innerUserId, new_innerUserId); 
			}
			//3. 替换 templateIds 正文模板
			String old_templateId ="";
			String new_templateId ="";
			for(String key : templateIds.keySet()){
				old_templateId = key;
				new_templateId = templateIds.get(old_templateId);
				xml = xml.replace(old_templateId, new_templateId); 
			}
			//4. 替换 formIds 默认表单
			String old_formId ="";
			String new_formId ="";
			for(String key : formIds.keySet()){
				old_formId = key;
				new_formId = formIds.get(old_formId);
				xml = xml.replace(old_formId, new_formId); 
			}
			//5. 替换 tables 表单信息
			String old_tableId ="";
			String new_tableId ="";
			for(String key : tableIds.keySet()){
				old_tableId = key;
				new_tableId = tableIds.get(old_tableId);
				xml = xml.replace(old_tableId, new_tableId); 
			}
			//6. 替换节点
			String old_nodeId ="";
			String new_nodeId ="";
			for(String key : nodeIds.keySet()){
				old_nodeId = key;
				new_nodeId = nodeIds.get(old_nodeId);
				xml = xml.replace(old_nodeId, new_nodeId); 
			}
			//7. 替换line
			String old_lineId ="";
			String new_lineId ="";
			for(String key : lineIds.keySet()){
				old_lineId = key;
				new_lineId = lineIds.get(old_lineId);
				xml = xml.replace(old_lineId, new_lineId); 
			}
		}
		String geneSql = xml;
		return geneSql;
	}

	@Override
	public List<String> genefieldComment(String oldTableId, String tablename) {

		String hql = "from WfFieldInfo where i_tableid = '"+oldTableId+"' and vc_comment is not null ";
		Query query = getEm().createQuery(hql);
		List<WfFieldInfo> fields = query.getResultList();
		List<String> sqlList = new ArrayList<String>();
		String sql = "";
		for(WfFieldInfo wfField : fields){
			if(wfField.getVc_comment() != null && !"".equals(wfField.getVc_comment())){
				sql = "comment on column " + tablename + "." + wfField.getVc_fieldname() 
					+ " is '" + wfField.getVc_comment() + "'";
				sqlList.add( sql+";");
			}
		}
		return sqlList;
	}

	@Override
	public List<ZwkjForm> getForms(String id) {
		String hql = "from ZwkjForm f where f.workflowId = '"+id+"'";
		Query query = getEm().createQuery(hql);
		List<ZwkjForm> fields = query.getResultList();
		return fields;
	}

	@Override
	public List<String> getInnerUserIdsByLcid(String id) {
		String hql = "select t.id from t_wf_core_inneruser t where t.workflowid  = '"+id+"'";
		List<String> list = getEm().createNativeQuery(hql).getResultList();
		return list;
	}

	@Override
	public List<String> getOfficeTableByLcid(String id) {
		String hql = "select t.id ||'|'|| t.vc_tablename from T_WF_CORE_TABLE t where t.lcid = '"+id+"' or t.reflc like '%,"+id+",%'";
		List<String> list = getEm().createNativeQuery(hql).getResultList();
		return list;
	}

	@Override
	public List<String> getOldLineIds(String id) {
		String hql = "select t.wfl_id from wf_line t where t.wfl_pid = '"+id+"'";
		Query query = getEm().createNativeQuery(hql);
		List<String> ids = query.getResultList();
		return ids;
	}

	@Override
	public List<String> getOldNodeIds(String id) {
		String hql = "select t.WFN_ID from WF_NODE t where t.WFN_PID = '"+id+"'";
		Query query = getEm().createNativeQuery(hql);
		List<String> ids = query.getResultList();
		return ids;
	}

	@Override
	public List<WfFormPermit> getPermitsByFormId(String formId) {
		String hql = "from WfFormPermit t where t.vc_formid = '"+formId+"'";
		Query query = getEm().createQuery(hql);
		List<WfFormPermit> permits = query.getResultList();
		return permits;
	}

	@Override
	public String getWfMainNameById(String id) {
		String sql = "select m.wfm_name from wf_main m where m.wfm_id = '"+id+"'";
		List<String> list = getEm().createNativeQuery(sql).getResultList();
		return (list!=null&&list.size()>0)?list.get(0):null;
	}

	@Override
	public void execSql(String sql) {
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public boolean existTable(String tableName) {
		String sql = "select count(*)  from t_wf_core_table t where t.vc_tablename = '"+tableName+"'";
		int count =  Integer.parseInt(super.getEm().createNativeQuery(sql).getSingleResult().toString());
		if(count ==0){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public String importSql(String path) {
		SQLExec sqlExec = new SQLExec();   
		//设置数据库参数   
		sqlExec.setDriver("oracle.jdbc.driver.OracleDriver");   
		sqlExec.setUrl(SystemParamConfigUtil2.getParamValueByParam("jdbc.url","hibernate.properties"));  
		sqlExec.setUserid(SystemParamConfigUtil2.getParamValueByParam("jdbc.username","hibernate.properties"));   
		sqlExec.setPassword(SystemParamConfigUtil2.getParamValueByParam("jdbc.password","hibernate.properties"));   
		//要执行的脚本   
		System.out.println(path);
		File file = new File(path);
		if(file.exists()){
			sqlExec.setSrc(new File(path));  
			//有出错的语句该如何处理   
			Calendar cal = Calendar.getInstance();
			String newPath = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));
			sqlExec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));  
			sqlExec.setPrint(true); //设置是否输出  
			//输出到文件 sql.out 中；不设置该属性，默认输出到控制台   
			File f = new File(PathUtil.getWebRoot()+"tempfile/log/"+cal.getTimeInMillis()+newPath+".out");
			sqlExec.setOutput(f);   
			sqlExec.setProject(new Project()); // 要指定这个属性，不然会出错   
			sqlExec.setEncoding("UTF-8"); 
			System.out.println(newPath);
			sqlExec.execute();  
			try {
				sqlExec.clone();
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		return null;
	}
	
	@Override
	public List<WfChild> findWfChildListByWfPid(String wfc_pid) {
		String hql ="select t.*  from Wf_Child t where t.wfc_pid='"+wfc_pid+"'";
		List<WfChild> list = getEm().createNativeQuery(hql, WfChild.class).getResultList();
		return list;
	}

	@Override
	public List<WfChild> findWfChildListByWfcid(String wfcid) {
		String hql ="select t.*  from Wf_Child t where t.wfc_cid='"+wfcid+"'";
		List<WfChild> list = getEm().createNativeQuery(hql, WfChild.class).getResultList();
		return list;
	}

	@Override
	public List<WfNode> findWfNodeByInstanceId(String instanceId, String processId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from wf_node wn where wn.wfn_id in (select distinct n.wfn_id from t_wf_process t,wf_node n ")
		.append("where t.wf_instance_uid = :wf_instance_uid ")
		.append("and t.is_over = 'OVER' and t.process_title is not null and t.is_show = 1 ")
		.append("and t.finsh_time is not null and (t.is_back != '2' or t.is_back is null) ")
		.append("and n.wfn_route_type='0' and n.wfn_id=t.wf_node_uid ")
		.append("and n.wfn_sortnumber<(select n2.wfn_sortnumber from t_wf_process t2,wf_node n2 where t2.wf_process_uid = :wf_process_uid and n2.wfn_id=t2.wf_node_uid)) ")
		.append("order by wn.wfn_sortnumber");
		Query query = getEm().createNativeQuery(sql.toString(), WfNode.class);
		query.setParameter("wf_instance_uid", instanceId);
		query.setParameter("wf_process_uid", processId);
		return query.getResultList();
	}
	
	@Override
	public WfNode getEndNode(String workflowId) {
		String hql="from WfNode where wfn_pid=:wfn_pid and wfn_type='end'";
		Query query=super.getEm().createQuery(hql);
		query.setParameter("wfn_pid", workflowId);
		List<WfNode> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<WfNode> getSortNodeId(String workflowId) {
		String sql =  "SELECT t.wfn_id FROM wf_node t  where  WFN_COMMENT_SORT = '1' and  wfn_pid = '"+workflowId+"'";
		List<Object> listTemp = getEm().createNativeQuery(sql).getResultList();
		if(listTemp!=null&&listTemp.size()>0){
			List<WfNode> list = new ArrayList<WfNode>();
			WfNode wfNode = null;
			for(Object o : listTemp){
				wfNode = new WfNode();
				wfNode.setWfn_id(o!=null?o.toString():"");
				list.add(wfNode);
			}
			return list;
		}
		return null;
	}

	@Override
	public List<ZwkjForm> findAllFormByLcId(String workflowId) {
		String hql = " from ZwkjForm t where t.workflowId ='"+workflowId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public String findRoleName(String wfn_staff) {
		String sql = " select wm_concat(t.name) from t_wf_core_inneruser t where t.id in ('"+wfn_staff+"')";
		return (String) getEm().createNativeQuery(sql).getSingleResult();
	}

	@Override
	public int getCountFromWfItem(String roleIds, String conditionSql) {
		StringBuffer sql = new StringBuffer(" select count(*) from t_wf_core_item t where 1=1 ");
		sql.append(conditionSql);
		return Integer.parseInt(super.getEm().createNativeQuery(sql.toString()).getSingleResult().toString());
	}

	@Override
	public List<Object[]> getItemList(String roleIds, String conditionSql,
			Integer pageIndex, Integer pageSize) {
		
		String hql = "select w.wfm_id, t.vc_sxmc,w.wfm_modifytime," +
				"w.wfm_createtime,w.wfm_status from t_wf_core_item t left join wf_main w on t.lcid = w.wfm_id where 1=1 " ;
		if(conditionSql!=null && !conditionSql.equals("")){
			hql += conditionSql;
		}
		hql +=" order by t.c_createdate desc";

		Query query = getEm().createNativeQuery(hql);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public String findRoleUserIds(String wfn_staff) {
		String sql = " select XMLAGG(XMLELEMENT(E, t.EMPLOYEE_ID || ',')).EXTRACT('//text()').getclobval() from t_wf_core_inneruser_map_user t where t.inneruser_id in ('"+wfn_staff+"')";
		SerializableClob clob = (SerializableClob) getEm().createNativeQuery(sql).getSingleResult();
		if(clob !=null ){
			char[] buffer = null; 
			try { 
			            //根据CLOB长度创建字符数组 
			    buffer = new char[(int)clob.length()]; 
			            //获取CLOB的字符流Reader,并将内容读入到字符数组中 
			    clob.getCharacterStream().read(buffer); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
	        //转换为字符串 
			String str = String.valueOf(buffer);
			if(str!=null && str.length()>1){
				str = str.substring(0,str.length()-1);
			}
			return str;
		}
		return  null;
	}
	
}
