package cn.com.trueway.workflow.core.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.AutoSendDao;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;

public class AutoSendDaoImpl extends BaseDao implements AutoSendDao {
	@SuppressWarnings("unchecked")
	public WfProcess getProcessByID(String processId) {
		String hql = "from WfProcess p where p.wfProcessUid='" + processId
				+ "' and isOver='NOT_OVER'";
		List<WfProcess> list = super.getEm().createQuery(hql).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public WfProcess getNotOverProcessByID(String processId) {
		String hql = "from WfProcess p where p.wfProcessUid='" + processId
				+ "'";
		List<WfProcess> list = super.getEm().createQuery(hql).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void updateProcess(WfProcess process) {
		if (process != null) {
			getEm().merge(process);
		}
	}

	public WfNode getStartNode(String id) {
		String hql = "from WfNode where wfn_pid='" + id
				+ "' and wfn_type='start'";
		Query query = super.getEm().createQuery(hql);
		return (WfNode) query.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<WfNode> getNode(String id, String node_id) {
		// 获取下一节点(单向)
		String sql = "select * from wf_node wf where  wf.wfn_isdisplay!='1' and wf.wfn_moduleid in(select l.wfl_wbasemode from wf_line l where l.wfl_xbasemode=(select n.wfn_moduleid from wf_node n where n.wfn_pid='"
				+ id
				+ "' and n.wfn_id='"
				+ node_id
				+ "') and l.wfl_pid='"
				+ id
				+ "' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0') and wfn_pid='"
				+ id + "'";
		// 节点前面的line：wfl_arrow(0：没有，1：开始，2：结束，3：双向), xbasemode
		String sql_lineType = "select l.wfl_arrow,l.wfl_xbasemode from WF_LINE l where l.WFL_wBASEMODE in (select n.wfn_moduleid from wf_node n where n.wfn_id='"
				+ node_id
				+ "') and l.wfl_pid='"
				+ id
				+ "'  and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0'";
		List<Object[]> list = super.getEm().createNativeQuery(sql_lineType)
				.getResultList();
		boolean flag = false;
		if (list == null || list.size() == 0) {
			String historyLine = "select l.wfl_arrow,l.WFL_XBASEMODE from wf_line_histroy l "
					+ "where l.WFL_wBASEMODE in (select n.WFN_MODULEID from WF_NODE_histroy n where n.WFN_ID='"
					+ node_id
					+ "')"
					+ " and l.wfl_pid='"
					+ id
					+ "' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0'";
			flag = true;
			list = super.getEm().createNativeQuery(historyLine).getResultList();
		}
		List<WfNode> retlist = new ArrayList<WfNode>();
		for (int i = 0; list != null && i < list.size(); i++) {
			Object[] data = (Object[]) list.get(i);
			String type = data[0].toString();
			String wfl_xbasemode = data[1].toString();
			String lineTableName = "wf_line";
			if (flag) {
				lineTableName = "wf_line_histroy";
			}
			if ("3".equals(type)) {
				String sql2 = "select * from wf_node wf where  wf.wfn_isdisplay!='1' and wf.wfn_moduleid "
						+ "in(select l.wfl_xbasemode from "
						+ lineTableName
						+ " l "
						+ "where l.wfl_wbasemode=(select n.wfn_moduleid from wf_node_histroy n where n.wfn_pid='"
						+ id
						+ "' and n.wfn_id='"
						+ node_id
						+ "')"
						+ " and l.wfl_pid='"
						+ id
						+ "' and l.wfl_xbasemode_type='0' and l.wfl_wbasemode_type='0' and l.wfl_xbasemode = '"
						+ wfl_xbasemode + "') " + " and wfn_pid='" + id + "'";
				Query query2 = super.getEm().createNativeQuery(sql2,
						WfNode.class);
				retlist.addAll(query2.getResultList());
			}
		}
		Query query1 = super.getEm().createNativeQuery(sql, WfNode.class);
		retlist.addAll(query1.getResultList());
		List<WfNode> noideList = new ArrayList<WfNode>();
		WfNode node = null;
		for (int i = 0; i < retlist.size(); i++) {
			node = retlist.get(i);
			if (node.getWfn_isdisplay() == null || node.getWfn_isdisplay() == 0) {
				noideList.add(node);
			}
		}
		sort(noideList, true);
		return noideList;
	}

	private void sort(List<WfNode> list, boolean isAsc) {
		Collections.sort(list, new Comparator<WfNode>() {
			public int compare(WfNode o1, WfNode o2) {
				Integer f1 = (o1.getWfn_sortNumber() == null ? 0 : o1
						.getWfn_sortNumber());
				Integer f2 = (o2.getWfn_sortNumber() == null ? 0 : o2
						.getWfn_sortNumber());
				return f1 - f2;
			}
		});
		if (!isAsc) {
			Collections.reverse(list);
		}
	}

	public WfLine findWfLineByNodeId(String workFlowId, String nextNodeId,
			String nodeId) {
		String sql = "select wfl.* from wf_line wfl where wfl.wfl_wbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"
				+ nextNodeId
				+ "') and wfl.wfl_xbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"
				+ nodeId + "') and wfl.wfl_pid='" + workFlowId + "'";
		String sqlTwoWay = "select wfl.* from wf_line wfl where wfl.wfl_xbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"
				+ nextNodeId
				+ "') and wfl.wfl_wbasemode=(select wn.wfn_moduleid from WF_NODE wn where wn.wfn_id='"
				+ nodeId + "') and wfl.wfl_pid='" + workFlowId + "'";
		Query query = super.getEm().createNativeQuery(sql, WfLine.class);
		Query queryTwoWay = super.getEm().createNativeQuery(sqlTwoWay,
				WfLine.class);
		WfLine wfLine = new WfLine();
		if (query.getResultList().size() > 0) {
			wfLine = (WfLine) query.getSingleResult();
		} else if (queryTwoWay.getResultList().size() > 0) {
			wfLine = (WfLine) queryTwoWay.getSingleResult();
		}
		return wfLine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findOfficeTableVal(String fieldName, String tableName,
			String instanceId) {
		String sql = "select t." + fieldName + " from " + tableName
				+ " t where t.instanceid = '" + instanceId + "'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public Employee queryEmployeeById(String userId) throws DataAccessException {
		List<Employee> rr = (List<Employee>) getEm().createQuery(
				"from Employee e where e.employeeGuid='" + userId
						+ "' order by e.tabindex").getResultList();
		if (rr != null && rr.size() > 0)
			return rr.get(0);
		else
			return null;
	}

	public void saveProcess(WfProcess process) {
		if (process == null) {
			return;
		}
		getEm().persist(process);
	}

	@SuppressWarnings("unchecked")
	public List<AutoSend> getAutoSend() {
		StringBuffer querySql = new StringBuffer(
				"select a.* from T_WF_CORE_AUTOSEND a,t_wf_process p"
						+ " where a.processid=p.wf_process_uid"
						+ " and to_char(a.auto_date,'yyyy-mm-dd')<=to_char(sysdate,'yyyy-mm-dd')"
						+ " and p.is_over='NOT_OVER'");
		Query query = super.getEm().createNativeQuery(querySql.toString(),
				AutoSend.class);
		return query.getResultList();
	}

	public void updateMasterProcess(String instanceid) {
		StringBuffer hql = new StringBuffer();
		hql.append("update WfProcess set isShow=1 where wfInstanceUid='"
				+ instanceid + "' and isOver='NOT_OVER'");
		this.getEm().createQuery(hql.toString()).executeUpdate();
	}

	@Override
	public void saveTrueJson(TrueJson entity) throws Exception {
		getEm().persist(entity);
	}

	@Override
	public void saveTrueJsonLog(TrueJsonLog log) throws Exception {
		getEm().persist(log);
	}
}
