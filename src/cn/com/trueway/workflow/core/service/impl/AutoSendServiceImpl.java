package cn.com.trueway.workflow.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cn.com.trueway.workflow.core.dao.AutoSendDao;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.AutoSendService;

public class AutoSendServiceImpl implements AutoSendService {

	private AutoSendDao autoSendDao;

	public AutoSendDao getAutoSendDao() {
		return autoSendDao;
	}

	public void setAutoSendDao(AutoSendDao autoSendDao) {
		this.autoSendDao = autoSendDao;
	}

	public WfProcess getProcessByID(String processId) {
		return autoSendDao.getProcessByID(processId);
	}
	
	public WfProcess getNotOverProcessByID(String processId){
		return autoSendDao.getNotOverProcessByID(processId);
	}

	public void updateProcess(WfProcess oldProcess) {
		autoSendDao.updateProcess(oldProcess);
	}

	public List<WfNode> showNode(String id, String node_id, String instanceId) {
		// 获取节点id,如果为空则说明搜寻的是开始节点
		List<WfNode> list = new ArrayList<WfNode>();
		if (null == node_id || node_id.equals("")) {
			// 查询出开始节点
			WfNode node = getStartNode(id);
			list.add(node);
		} else {
			// 找出该id节点下面的节点
			list = getNode(id, node_id);
		}
		List<WfNode> nodeList = new ArrayList<WfNode>();
		// 节点,查询出,节点之间的线条信息
		WfNode wfNode = null;
		for (int i = 0; i < list.size(); i++) {
			wfNode = list.get(i);
			String nextNodeId = list.get(i).getWfn_id();
			WfLine wfLine = autoSendDao.findWfLineByNodeId(id, nextNodeId,
					node_id); // 两个节点之间的线条信息
			if (wfLine != null) {
				String choice_condition = wfLine.getWfl_choice_condition(); // 线条上判断条件
				String choice_rule = wfLine.getWfl_choice_rule();
				if (choice_condition != null && !choice_condition.equals("")) { // 存在线上条件
					boolean fhtj = checkNodeCondition(choice_condition,
							choice_rule, id, instanceId); // 是否符合条件
					if (fhtj) {
						nodeList.add(wfNode);
					}
				} else {
					nodeList.add(wfNode);
				}
			} else {
				nodeList.add(wfNode);
			}
		}
		if (nodeList == null || nodeList.size() == 0) {
			nodeList = list;
		}
		return nodeList;
	}

	public WfNode getStartNode(String id) {
		return autoSendDao.getStartNode(id);
	}

	public List<WfNode> getNode(String id, String node_id) {
		return autoSendDao.getNode(id, node_id);
	}

	public boolean checkNodeCondition(String condition, String choice_rule,
			String wfUId, String instanceId) {
		if (instanceId != null && !instanceId.equals("")) {
			String[] rules = choice_rule.split(";");
			for (int i = 0; i < rules.length; i++) {
				String[] rule = rules[i].split("=");
				String rel_name = rule[0];
				String[] field = rule[1].split("[|]");
				String fieldName = field[0]; // 字段名称
				String tableName = field[1]; // 表名
				List<Object> list = autoSendDao.findOfficeTableVal(fieldName,
						tableName, instanceId);
				if (list != null) {
					String value = list.get(0).toString();
					boolean isNum = false;
					try {
						Double.parseDouble(value);
						isNum = true;
					} catch (Exception e) {
						e.getMessage();
					}
					if (isNum) {
						condition = condition.replace(rel_name, value);
					} else {
						condition = condition.replace(rel_name, "\"" + value
								+ "\"");
					}
				}
			}
			// 判断逻辑是否正确
			try {
				Object eval = new ScriptEngineManager().getEngineByName("js")
						.eval(condition);
				return (Boolean) eval;
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Employee findEmployeeById(String userId) {
		return autoSendDao.queryEmployeeById(userId);
	}

	public void saveProcess(WfProcess newProcess) {
		autoSendDao.saveProcess(newProcess);
	}
	
	public List<AutoSend> getAutoSend(){
		return autoSendDao.getAutoSend();
	}
	
	public void updateMasterProcess(String instanceid){
		autoSendDao.updateMasterProcess(instanceid);
	}
	
	@Override
	public void saveTrueJson(TrueJson entity) {
		try {
			autoSendDao.saveTrueJson(entity);
			saveTrueJsonLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTrueJsonLog(TrueJson log){
		TrueJsonLog entity = new TrueJsonLog();
		entity.setInstanceId(log.getInstanceId());
		entity.setProcessId(log.getProcessId());
		entity.setUserId(log.getUserId());
		entity.setTrueJson(log.getTrueJson());
		entity.setExcute(log.getExcute());
		entity.setReadOrWrite("1");
		entity.setReadOrWriteDate(new Date());
		try {
			autoSendDao.saveTrueJsonLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
