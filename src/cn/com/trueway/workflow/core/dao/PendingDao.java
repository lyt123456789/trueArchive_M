package cn.com.trueway.workflow.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;

public interface PendingDao {

	List<Pending> getPendingList(String conditionSql,String userId,Integer pageIndex,Integer pageSize);
	
	int getCountOfPending(String conditionSql,String userId,String type);
	
	int getCountOfPending(String conditionSql);
	
	WfProcess getProcessByID(String processId);
	
	WfMain getWfMainById(String id);
	
	WfNode getWfNode(String nodeid);
	
	Date getEndDate(int count,Date beginDate);
	
	int getDateCount(Date beginDate, Date endDate);
	
	List<String> getAllYear();
	
	List<Pending> getPendListOfMobile(String userId, int count,String type);
	
	List<Pending> getPendListOfMobile(String userId, int count, Integer column, Integer pagesize,String type);
	
	List<String> getTypeListOfPending(String userId);
	
	List<Pending> getPendListOfMobile(String userId, int count,Integer column, Integer pagesize, String type, String itemIds,String title,String isReadFile);
	
	WfProcess getFirstStepWfProcess(String instanceid, String workflowid);
	
	void setBackStatus(List<Pending> list, String isAdmin);
	
	boolean stepIsOver(String instanceId,String stepIndex);
	
	void recycleTask(String instanceId, String stepIndex);
	
	boolean isBacked(String instanceId);
	
	String getPinstanceId(String instanceId);
	
	WfProcess getProcessByInstanceIds(String instanceIds,String userId);
	
	Date getEndDate(int deadline, Date date, String wfm_calendar);
	
	List<WfProcess> findProcessListByFId(String f_instanceId);
	
	Integer countProcessListByFId(String f_instanceId);
	
	List<WfProcess> findProcessListByFIdAndDoType(String getfInstancdUid,String doFile);

	WfProcess checkInstanceIsOver(WfProcess wfProcess);
	
	List<WfProcess> findProcessListById(String insertid);
	
	List<SendAttachments> findSendAttsByDocguid(String instanceId);
	
	void addSendAtts(SendAttachments newSendAttachments);
	
	List<Object> getAllUserIdByFInstanceId(String getfInstancdUid, String mc);
	
	List<WfProcess> getProcessByInstanceId(String instancdUid);
	
	Integer countByInstanceId(String instancdUid);
	
	List<WfProcess> findProcessListByFIdAndDoFile(String wfInstanceUid, String dotype);
	
	List<WfProcess> findProcessListByIdAndDoFile(String instancdid, String dotype);
	
	List<DoFileReceive> getDoFileReceiveByInstanceId(String instanceId);
	
	List<Object> getDeparNameByDepartId(String toDepartId);
	
	List<Object[]>  getEndProcess(int step, String nodeUid,String instanceId);
	
	int findLhfwCount(String conditionSql, String depIds); 
	
	List<Pending> findLhfwpendingList(String conditionSql, String depIds, Integer pageIndex, Integer pageSize);
	
	Pending getPendingById(String processId);
	
	WfProcess getRecentProcess(String instanceId);
	
	Integer setDofileCopyNumber(String instanceId);
	
	void setBackStatus(List<Pending> list, String isAdmin, String userId);
	
	String setBackStatus(Map<String, String> map, String userId);
	
	int getCountOfExceedPending(String conditionSql,String userId,String type);
	
	int getCountOfExceedPending2(String conditionSql);
	
	int getCountOfExceedUR(String conditionSql);
	
	List<Pending> getExceedPendingList(String conditionSql,String userId,Integer pageIndex,Integer pageSize);
	
	List<Department> getDeptListForpage();
	
	List<Object[]> getExceedPendingList2(String conditionSql,Integer pageIndex,Integer pageSize);
	
	List<Object[]> getExceedURList(String conditionSql,Integer pageIndex,Integer pageSize);
	
	List<Object[]> getSendUser(String instanceId);
	
	public void recallDoFileForBXCY(String instanceId, String stepIndex,String userId);

}
