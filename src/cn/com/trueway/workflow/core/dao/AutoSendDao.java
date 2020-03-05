package cn.com.trueway.workflow.core.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;

public interface AutoSendDao {
	public WfProcess getProcessByID(String processId);

	public WfProcess getNotOverProcessByID(String processId);

	public void updateProcess(WfProcess oldProcess);

	public WfNode getStartNode(String id);

	public List<WfNode> getNode(String id, String node_id);

	public WfLine findWfLineByNodeId(String workFlowId, String nextNodeId,
			String nodeId);

	public List<Object> findOfficeTableVal(String fieldName, String tableName,
			String instanceId);

	public Employee queryEmployeeById(String userId) throws DataAccessException;

	public void saveProcess(WfProcess process);

	public List<AutoSend> getAutoSend();
	
	public void updateMasterProcess(String instanceid);
	
	public void saveTrueJson(TrueJson entity)  throws Exception;
	
	public void saveTrueJsonLog(TrueJsonLog log) throws Exception;
}
