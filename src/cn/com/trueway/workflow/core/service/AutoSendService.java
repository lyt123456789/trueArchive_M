package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;

public interface AutoSendService {
	public WfProcess getProcessByID(String processId);
	
	public WfProcess getNotOverProcessByID(String processId);

	public void updateProcess(WfProcess oldProcess);

	public List<WfNode> showNode(String id, String node_id, String instanceId);

	public Employee findEmployeeById(String userId);

	public void saveProcess(WfProcess newProcess);

	public List<AutoSend> getAutoSend();
	
	public void updateMasterProcess(String instanceid);
	
	public void saveTrueJson(TrueJson entity);
}
