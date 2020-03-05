package cn.com.trueway.workflow.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.DofileFavourite;
import cn.com.trueway.workflow.core.pojo.DzJcdb;
import cn.com.trueway.workflow.core.pojo.DzJcdbShip;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.EmployeeLeave;
import cn.com.trueway.workflow.core.pojo.EndInstanceId;
import cn.com.trueway.workflow.core.pojo.GetProcess;
import cn.com.trueway.workflow.core.pojo.OfficeInfoView;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.PersonMessage;
import cn.com.trueway.workflow.core.pojo.PushMessage;
import cn.com.trueway.workflow.core.pojo.Reply;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfConsult;
import cn.com.trueway.workflow.core.pojo.WfCyName;
import cn.com.trueway.workflow.core.pojo.WfDuBanLog;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.WfRecallLog;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.vo.TrueOperateLog;

public interface TableInfoDao {

	List<String> getTableCountForPage(String column, String value, WfTableInfo wfTable);
	
	List<WfTableInfo> getTableListForPage(String column, String value,
			WfTableInfo wfTable, Integer pageindex, Integer pagesize);
	
	List<WfTableInfo> getTableByTableName(String vc_tablename);
	
	List<WfTableInfo> getTableByMap(HashMap<String,String> map);
	
	WfTableInfo addTable(WfTableInfo wfTable);
	
	WfTableInfo getTableById(String id);
	
	List<WfTableInfo> getTableByLcid(String id);

	Process findProcessById(String oldProcessId);

	void update(WfProcess oldProcess);

	void save(WfProcess process);
	
	void saveDoFile(DoFile doFile);
	
	List<WfTableInfo> getAllTableNotLc(String lcid);
	
	List<Object[]> getNextUserList(String workFlowId,String instanceId,String nodeId);
	
	void updateProcessShow(String workFlowId,String instanceId,String nodeId);
	
	List<WfProcess> getNodeProcess(String workFlowId,String instanceId,String nodeId,String fInstanceId);
	
	List<String> getUpdateOverUserList(String workFlowId,String instanceId,String nodeId );
	
	void updateOver(String workFlowId,String instanceId,String nodeId );
	
	void updateInstanceOver(String workflowId,String instanceId);
	
	List<Pending> getOverList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,String status);
	
	int getCountOfOver(String conditionSql,String userId,String status);

	String getCommentTagIds(String wfInstanceUid);

	List<WfFieldInfo> getFieldByTableid(String tableid);
	
	WfFieldInfo getFieldById(String id);

	List<String[]> getTableAndColumnName(String fieldId);

	List<GetProcess> findProcessList(String instanceId);

	List<WfProcess> findProcessList(String nodeId, String instanceId,String userId);

	String findUserNameByUserId(String userUid);

	List<DoFile> getDoFileList(String bigDepId,String conditionSql, Integer pageindex, Integer pagesize);

	List<WfProcess> getProcessList(String instanceId);
	
	List<Object[]> getFastProcessList(String instanceId);
	
	List<String> getFastItemList(String itemId);
	
	String findItemNameById(String itemId);

	void saveConsult(WfConsult consult);

	List<WfConsult> getConsultList(String userId, String condition, Integer pageIndex, Integer pageSize);

	int getCountConsults(String userId,String condition);

	void updateConsultRead(String id);

	void updateConsultReplied(String id);

	WfConsult getConsultById(String id);

	WfProcess getProcessById(String processId);

	int getCountOfDuplicate(String conditionSql, String employeeGuid);

	List<Pending> getDuplicateList(String conditionSql, String employeeGuid,
			Integer pageIndex, Integer pageSize);

	void updateDuplicateNotShow(String ids);

	void updateOverForEntrust(String workFlowId, String instanceId,
			String nodeId, String entrustUserId,String processId);

	boolean isDuplicated(WfProcess dupProcess);

	boolean hasEntrust(WfProcess dupProcess);

	String findDeptNameByEmpId(Employee emp);

	List<WfItem> findItemList();

	void savePerMes(PersonMessage perMes);

	PersonMessage findXccNamesByItemId(String id,String userId);

	void updatePerMes(PersonMessage pm);
	
	void deletePerMes(String id);

	void updateConsultNotShow(String ids);

	int getCountDoFiles(String bigDepId,String conditionSql);

	WfItem findItemByWorkFlowId(String workFlowId);

	String findNameByEmpId(String userId);

	List<WfCyName> findWfCyPersonNameByInstanceId(String instanceId);
	
	List<WfCyName> findWfCyOfficeNameByInstanceId(String instanceId);
	
	void saveWfCyName(WfCyName wfCyName);

	void updateWfCyName(WfCyName wcn);
	
	void delelteWfCyPersonName(WfCyName wfCyName, String instanceId);

	void delelteWfCyOfficeName(WfCyName wfCyName,String instanceId);

	String findDeptNameByUserId(String userId);
	
	List<Object[]> getListBySql(String sql);

	List<WfProcess> findProcesses(String workFlowId, String instanceId, String nextNodeId);

	void updateWfProcess(WfProcess wfProcess);

	List<Process> findProcessListByElements(String workFlowId, String instanceId);

	void deleteWfProcess(String workFlowId, String instanceId);
	
	void deleteWfProcessByAllInstanceId(String allInstanceId);
	
	List<WfProcess> getWfProcessByAllInstanceId(String allInstanceId);

	DoFile getDoFileByElements(String workFlowId, String instanceId);
	
	void updateDoFile(DoFile doFile);

	void deleteFwByElements(String workFlowId, String instanceId);

	void deleteBwByElements(String workFlowId, String instanceId);

	Employee findEmpByUserId(String userId);
	
	List<Employee> findEmpsByUserIds(String userIds);
	
	List<WfProcess> findWfProcessByInstanceId(String wf_instance_uid);
	
	List<Object> findWfProcessByInstanceIddesc(String wf_instance_uid);

	Object[] getToDbInfoIds(String nodeId);

	List<Pending> getOverList2(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype);

	WfItem findItemByWorkFlowId(String workFlowId, String webId);

	int getCountOfOver(String conditionSql, String userId, String itemid,
			String statustype);

	List<WfProcess> findWfProcessByInstanceIdAndStepIndex(String instanceId,
			Integer stepIndex);
	
	int getCountOfMyLeave(String conditionSql,String userId, String itemid,String statustype); 

	List<Pending> getMyLeaveList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype);

	boolean otherMultiChildProcessIsEnd(String fInstanceId, String instanceId);
	boolean otherSingleChildProcessIsEnd(String fInstanceId, String instanceId);

	boolean isChildProcess(String instanceId);

	List<WfProcess> findMultiChildProcessList(String fInstanceId);
	List<WfProcess> findSingleChildProcessList(String fInstanceId);

	Date getDoFileApplyDate(String instanceId, String workFlowId);
	
	WfNode getWfnNodeByInstanceId(String instanceId);
	
	WfProcess getWfProcessByInstanceid(String instanceId);
	
	void updateProcessDate();

	List<WfProcess> getWfProcessByInstanceidAndStatus(String wfChildInstanceId);
	
	void updateProcessDate(String instanceId, String nodeid, Integer stepindex, Integer days);

	List<String> getWfProcessByWfChildInstanceId(String wfChildInstanceId);
	
	void updateWfProcessStatus(String instanceId);

	List<WfProcess> findBoobyChildProcessList(String sql);

	WfProcess getParentProcessByInstanceid(String instanceId);
	
	Integer getMaxStepIndex(String instanceId); 
	
	List<WfProcess> getWfProcessList(String instanceId);
	
	List<WfProcess> getWfProcessList(HashMap<String, String> instanceId);
	
	GetProcess findGetProcessByPInstanceID(String pinstanceId, String stepIndex);
	
	List<GetProcess> findProcess(String pinstanceId);
	
	WfNode getWfNodeById(String id);
	
	List<String> findInstanceIdsByFinstanceId(String finstanceId);
	
	WfProcess getWfProcessByColoum(String finStanceId, String userid);
	
	List<DoFileReceive> getDoFileReceiveList(String userId,
			Integer pageIndex,Integer pageSize,Integer status,Map<String,String> map);

	int getDoFileReceiveCount(String userId,Integer status,Map<String,String> map);
	
	void addDoFileReceive(DoFileReceive doFileReceive);

	DoFileReceive getDoFileReceiveById(String id);
	
	void updateDoFileReceive(DoFileReceive doFileReceive);
	
	void updateProcess(WfProcess wfProcess);

	List<WfProcess> findWfProcessByInstanceIdAndIsChild(String wf_instance_uid);
	
	Map<String,Integer> findWfProcessByInstanceIdAndIsChilds(String wf_instance_uids);
	
	void updateIsyyById(String id);

	List<GetProcess> getChildWfProcessList(String instanceId, String beginTime, String endTime);

	void savePushMessage(PushMessage pm);

	List<PushMessage> getPushMessageList(String employeeGuid,String userId,String instanceId);

	List<DoFile> getDoFileList1(String bigDepId, String conditionSql,
			Integer pageindex, Integer pagesize);
	
	String getworkFlowIdByInstanceId(String instanceId);
	
	String getFinstanceIdByInstanceId(String instanceId);

	String getPinstanceId(String instanceId);

	DoFileReceive getDoFileReceive(String instanceId);

	void addSw(Sw sw);

	void addDoFile(DoFile doFile);

	void addWSPBD(Map<String, String> map);

	List<Sw> getSwdrlbList(int pageIndex, int pageSize);

	int getCountSwdrlbList();

	String getNodeNameById(String nodeId);
	
	List<WfTableInfo> getOfficeTableList(Map<String,String> map);

	void clearTableData(String[] tableNames);
	
	void removeOfficeTable(String[] tableNames);
	
	List<Object[]> getAllTableList();

	void updateDoFileIsDelete(String id,String isdelete);

	int getCountHszDoFiles(String bigDepId, String conditionSql); 
	List<DoFile> getHszDoFileList(String bigDepId,String conditionSql, Integer pageindex, Integer pagesize);

	DoFile getDoFileById(String id);

	List<WfProcess> getProcessListByPinstanceId(String pInstanceId);

	List<WfProcess> getProcessListByInstanceId(String instanceId);

	void deleteWfProcess(WfProcess wfProcess);

	void deleteWfProcess(String doFile_id);

	List<PushMessage> getPushMessageList(String employeeGuid, String insertid);
	
	List<Object[]> getTableColoumValue(String tableName, String coloum, String InstanceId);

	List<WfProcess> getWfProcessCountList(String insertid, int i);
	
	WfProcess getMasterProcess(WfProcess oldProcess);
	
	List<WfProcess> getNextProcess(WfProcess oldProcess);
	
	String getMaxProcessIdByAllInstanceId(String allInstanceId);

	Sw getSwByInstanceId(String instanceId);

	void updateSw(Sw sw);

	List<WfProcess> getCSWfProcessByInstanceid(String wfInstanceId, int stepIndex);

	List<String> getAttachmentTagByForm(String formId);

	WfChild getwfChildByCId(String workflowId);
	
	int getStepIndexByInstanceId(String instanceId);
	
	List<Object[]> getDofileReceiveList(Map<String,String> map, Integer pageindex, Integer pagesize);
	
	int getDofileReceiveCount(Map<String,String> map);
	
	List<Object[]>  getReceiveInfo(String fprocessid,String applydate);
	WfProcess getMaxWfProcessIdByInstanceId(String instanceid);

	int getCountMessage(WfProcess wfProcess, String employeeGuid);

	void updatePushMessageZt(String employeeGuid, WfProcess wfProcess);

	void deleteDoFile(DoFile doFile);
	
	List<Object[]> getIsBt(String workFlowId, String nodeid, String formId,String isbt,String name);
	
	List<Object[]> getIsPy(String workFlowId, String nodeid, String formId,String isbt,String name);

	List<Object[]> getIsBt(String workFlowId, String nodeid, String formId,String isbt,String name, String userId);
	
	WfProcess getProcessByInstanceIdAndDate(String instanceId, Date applyTime);

	Department findDeptByDeptId(String deptid);

	void updatePushMessage(String employeeGuid, WfProcess wfProcess);

	void updateWfProcessByTs(String employeeGuid, WfProcess wfProcess);
	
	List<Object[]> getNewestWfProcess(String instanceId);
	
	List<WfProcess> getWfProcessList(WfProcess wfProcess);
	
	void addProcess(WfProcess wfProcess);

	List<Object[]> getKcqWfProcess();

	List<Object[]> getYwbList(String instanceid, String tableName, String culoum);

	List<PushMessage> getPushMessage(String employeeGuid, String wfInstanceUid);

	List<Object> getIntanceIdByZxIntanceId(String instanceId, String doType);
	
	List<WfProcess> getWfProcessByEntity(WfProcess wfProcess);
	
	int getCountOfDoFileFavourites(String bigDepId,String conditionSql, String userId);
	
	List<DoFile>  getDoFileFavouriteList(String bigDepId,String conditionSql,String userId, Integer pageindex, Integer pagesize);
	
	void saveDofileFavourite(DofileFavourite dofileFavourite);
	
	void removeDofileFavourite(DofileFavourite dofileFavourite);
	
	DofileFavourite getDofileFavouriteById(String dofileId, String userId);

	int getDoFileReceiveCountOfMobile(String userId, Integer status,String condition);

	List<DoFileReceive> getDofileReceiveListOfMobile(String userId,Integer pageIndex,Integer pageSize,Integer status, String condition);

	List<DoFileReceive> getDofileFavouriteByFprocessId(String fProcessid);
	
	String getSwdjh(String type);

	List<Object[]> findSw(Integer pageIndex, Integer pageSize, String deptName,
			String swdj, String conditionSql, String conditionSql2);

	int findCountSw(String deptName, String swdj, String conditionSql, String conditionSql2);
	
	void saveMsgSend(MsgSend msgSend);

	List<DoFileReceive> getDoFileReceiveByPIdAndDeptIds(String pInstanceId,
			String linkDeptIds);
	
	String findPendDealIngUser(String processId,String instanceId, 
			Integer stepIndex, String apply_time);
	
	/**
	 * 
	 * 描述：获取全部的办件
	 * @param WfTitle
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2014-8-11 上午11:48:11
	 */
	int findAllDofileListCount(String wfTitle);
	
	/**
	 * 
	 * 描述：分页获取全部的办件
	 * @param WfTitle
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Dofile>
	 * 作者:蔡亚军
	 * 创建时间:2014-8-11 上午11:49:40
	 */
	List<DoFile> findAllDofileList(String conditionSql, Integer pageIndex, Integer pageSize );

	void addReply(Reply reply);

	List<DocXgDepartment> getDocXgDepartmentListByDepId(String depid);
	/**
	 * 
	 * 描述：并行结合: 该步骤最后的处理人员[承担 发送下一步]; [意见填写放置在该步骤]
	 * @param wfProcess
	 * @return WfProcess
	 * 作者:蔡亚军
	 * 创建时间:2014-8-25 上午8:59:44
	 */
	WfProcess findBxjhWfProcess(WfProcess wfProcess);

	void falseDeleteDoFile(String dofileId , String instanceId,Employee emp);

	void falseDeleteProcess(String instanceId);

	void recoverDoFile(String dofileId ,String instanceId);

	void recoverProcess(String instanceId);
	
	/**
	 * 
	 * 描述：根据id获取DzJcdb
	 * @param no
	 * @return DzJcdb
	 * 作者:蔡亚军
	 * 创建时间:2014-11-20 下午6:06:08
	 */
	DzJcdb findDzJcdbById(String no);
	
	/**
	 * 
	 * 描述：更新DzJcdb的state状态;   1已督办未反馈(流程开始)、2已督办已反馈(流程技术)
	 * @param dzJcdb void
	 * 作者:蔡亚军
	 * 创建时间:2014-11-21 上午9:35:40
	 */
	void updateDzJcdb(DzJcdb dzJcdb);
	
	/**
	 * 
	 * 描述：根据instanceId获取DzJcdbShip
	 * @param instanceId
	 * @return DzJcdbShip
	 * 作者:蔡亚军
	 * 创建时间:2014-11-21 上午9:39:12
	 */
	DzJcdbShip findDzJcdbShipById(String instanceId);
	
	/**
	 * 
	 * 描述：保存no与intsnaceId的规则
	 * @param dzJcdbShip void
	 * 作者:蔡亚军
	 * 创建时间:2014-11-21 上午9:39:52
	 */
	void saveDzJcdbShip(DzJcdbShip dzJcdbShip);

	List<WfProcess> isAllJBEnd(String fjbProcessId);

	List<GetProcess> getJBProcessList(String processId);

	void saveEndInstanceId(EndInstanceId endInstanceId);
	
	boolean delEndInstanceId(String instanceId);

	void deleteErrorProcess();
	
	List<WfProcess> findWfProcessList(WfProcess wfp);
	
	List<WfProcess> findWfProcessList(String instanceId, Integer stepIndex, String userId);

	int getNewCountDoFiles(String conditionSql);

	List<Object> getNewDoFileList(String conditionSql, Integer pageIndex,Integer pageSize);

	void modifyReceiveData(String fprocessid);

	void modifySwData(String fprocessid);

	List<DoFileReceive> getDeptIdByFprocessId(String fprocessid);
	
	void deleteDoFileReceiveList(String fProcessId) throws Exception;
	
	void deleteDoFileSwList(String instanceId) throws Exception;
	
	WfProcess findSendWfProcess(String instanceId);
	
	WfProcess findFakeWfProcess(String instanceId);
	
	List<WfProcess> getProcessListByParams(Map<String, String> map);
	
	List<WfProcess> findWfProcessList(String workFlowId,String instanceId,String nodeId, Integer stepIndex);
	
	/**
	 * 描述：可删除列表，（第一步暂存以及发送下一步收回的待办公文）
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-2-6 下午5:37:10
	 */
	List<String> getDelPendingList(String conditionSql,String userId, Integer pageIndex,Integer pageSize);
	
	int getCountOfDelPending(String conditionSql,String userId,String type);
	
	List<LowDoc> getLowDocList(String userId,
			Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map);
	
	List<DoFileReceive> getReceiveAllList(String userId,
			Integer pageIndex,Integer pageSize,Integer status,Map<String,String> map);

	int getReceiveAllCount(String userId,Integer status,Map<String,String> map);
	
	/**
	 * 描述：获取办件的步骤列表
	 * @param instanceId
	 * @return List<Integer>
	 * 作者:蒋烽
	 * 创建时间:2017-4-5 下午5:09:15
	 */
	List<Integer> getStepIndex(String instanceId);
	
	/**
	 * 描述：获取当前步骤的办理情况
	 * @param instanceId
	 * @param stepIndex
	 * @return List<WfProcess>
	 * 作者:蒋烽
	 * 创建时间:2017-4-5 下午5:29:59
	 */
	List<Object[]> getProcessByStepIndex(String instanceId, Integer stepIndex);
	
	/**
	 * 描述：更加实例id获取办结步骤
	 * @param instanceId
	 * @return WfProcess
	 * 作者:蒋烽
	 * 创建时间:2017-7-14 上午10:22:10
	 */
	WfProcess getOverWfpByInstanceId(String instanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param bigDepId
	 * @param conditionSql
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午10:13:29
	 */
	int getCountCyDoFiles(String conditionSql, String userId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param bigDepId
	 * @param conditionSql
	 * @param pageindex
	 * @param pagesize
	 * @return List<DoFile>
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午10:13:32
	 */
	List<DoFile> getCyDoFileList(String conditionSql, String userId, Integer pageindex, Integer pagesize);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @param instanceId
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-9-8 上午10:49:30
	 */
	int getFavFileCount(String userId,String instanceId);
	
	List<Object[]> findeWfps(String fromNodeId,String nodeId, String instanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * String
	 * 作者:蒋烽
	 * 创建时间:2017 下午8:05:00
	 */
	String findEntrustName(String instanceId);
	
	List<Employee> findToSortEmployeeList(String allInstanceId, String nodeIds);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * TableInfoDao
	 * WfProcess
	 * 作者:蒋烽
	 * 创建时间:2017 上午10:52:58
	 */
	WfProcess findLastProcess(String instanceId, String nodeId, String userId);
	
	/**
	 * 描述：统计我的过程信息
	 * TableInfoDao
	 * Integer
	 * 作者:蒋烽
	 * 创建时间:2017 下午10:19:21
	 */
	Integer countMyProcess(String instanceId,String userId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param list
	 * @return List<DoFile>
	 * 作者:蒋烽
	 * 创建时间:2018-3-16 下午3:49:58
	 */
	List<DoFile> getDoFile(List<String> list);
	
	public Integer getNoOwnerDofileCount(Map<String, String> map);
	
	public List<Object[]> getNoOwnerDofileList(Map<String, String> map,  Integer pageindex, Integer pagesize);
	
	public void saveAutoSend(AutoSend autosend);
	
	/**
	 * 描述：获取文号
	 * String
	 * 作者:ln
	 * 创建时间:2018-08-10
	 */
	String findWh(String instanceId);
	
	void addRecallLog(WfRecallLog recallLog);
	
	void saveDuBanLog(WfDuBanLog duBanLog);
	
	List<WfDuBanLog> getDuBanMsg();
	
	List<String> selectOverProcessId(String instanceId);

	/**
	 * 方法描述: [获取最新的process记录]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-10-24-下午5:35:18<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param instanceId
	 * @return
	 * Object[]
	 */
	Object[] getNewsProcessByInstanceid(String instanceId);
	
	/**
	 * 方法描述: [只查询过程id和IsOver]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-10-25-上午9:06:45<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param wfp
	 * @return
	 * List<WfProcess>
	 */
	List<WfProcess> findWfProcessListByIsOver(WfProcess wfp);

	List<WfProcess> findWfProcessIdByInsIdAndStp(
			String instanceId, Integer stepIndex, String userId);

	List<WfProcess> getStepByEntity(WfProcess wfProcess);

	WfProcess findEndWfProcessByInstanceId(String instanceId);

	List<WfProcess> findWfProcessAnyInfo(String workFlowId, String instanceId,
			String nodeId, Integer stepIndex);

	void updateIsShowByProcessId(String processId);

	String findNodeIdByProcessId(String processId);

	OfficeInfoView getOfficeInfoByInstanceId(String instanceId);

	Object[] findDuBanListByInsId(String instanceId);

	List<DoFile> findDoFilesByIds(String ids);

	int getCountOfOperateLog(String instanceId);

	List<TrueOperateLog> findOperateLogList(String instanceId,Integer pageindex, Integer pagesize);

	List<WfProcess> findWfProcessByInsAndIndex(String instanceId,
			Integer stepindex);

	void updateOverByNodeId(String workFlowId, String instanceId, String nodeId);

	BigDecimal findMaxStepIndexByNodeId(String instanceId, String nextNodeId);

	String getApplyTimeByInsId(String instanceId);

	List<String> queryMultDeptByEmpId(String userId);

	String querySiteIdByDeptId(String deptId);

	List<WfProcess> findPendingByInsIdAndUserId(String instanceId, String userId);

	List<Employee> findAllLeaveEmps();

	EmployeeLeave findEmpLeave(Employee emp);

	void saveEmloyeeLeave(EmployeeLeave employeeLeave);

	List<WfProcess> findPendingOfUserId(String userId, int fileType);

	void deleteWhOfFw(String instanceId);

	void deleteWhOfBw(String instanceId);

	String findJjcd(String instanceId);

	Integer getCountEndInstance(String instanceId);

	String getViewBhByInstanceId(String instanceId);

	String findLwdw(String instanceId);

	Object[] getViewInfoByInstanceId(String instanceId);
}

