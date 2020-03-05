package cn.com.trueway.workflow.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.DofileFavourite;
import cn.com.trueway.workflow.core.pojo.DzJcdb;
import cn.com.trueway.workflow.core.pojo.DzJcdbShip;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.EmployeeLeave;
import cn.com.trueway.workflow.core.pojo.FollowShip;
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
import cn.com.trueway.workflow.core.pojo.vo.StepIndexVO;
import cn.com.trueway.workflow.core.pojo.vo.TrueOperateLog;

public interface TableInfoService {

	List<String> getTableCountForPage(String column, String value, WfTableInfo wfTable);
	
	List<WfTableInfo> getTableListForPage(String column, String value,
			WfTableInfo wfTable, Integer pageindex, Integer pagesize);
	
	int isExistTable(String vc_tablename);
	
	WfTableInfo addTable(WfTableInfo wfTable);
	
	boolean createTable(String sql);
	
	String getCreateSql(List<WfFieldInfo> list, String vc_tablename);
	
	WfTableInfo getTableById(String id);
	
	List<String> getForeKeySql(List<WfFieldInfo> list, String tablename);
	
	List<String> getCommentSql(List<WfFieldInfo> list, String tablename);
	
	List<String> getFieldSql(List<WfFieldInfo> list, String tablename);
	
	List<WfTableInfo> getTableByLcid(String lcid);
	
	List<WfTableInfo> getTableByTableName(String vc_tablename);
	
	List<WfTableInfo> getTableByMap(HashMap<String,String> map);
	
	String getTableCommentSql(String vc_name, String tablename);

	List<WfProcess> sendProcess(String title,String m_userId,String c_userIds,String userId, 
			String workFlowId,String nodeId,String f_proceId,String oldProcessId, String nextNodeId, 
			String instanceId,String itemId,String formId,String oldformId,String pdfPath,String trueJson, 
			String isChildWf,String cType,String relation,String finstanceId,String newInstanceIdForChildWf, 
			String specialProcess,String middlePdf, String wcqx,String firstOverPdf, String PDFPath, String formPage, Integer urgency,String self_loop) throws Exception;
	
	WfProcess sendNewProcess(String vc_title, String userIds, String employeeGuid, String newInstanceId, 
			String workFlowId, String nodeId, String fromNodeId, String toNodeId, WfProcess oldProcess,WfNode wfNode,
			String isMerge ,String commentJson,String pdfPath,String doType,String wfc_ctype,Sw sw, String wcqx, Integer pStepIndex);
	
//	String sendProcess(String title,String m_userId,String c_userIds,String userId,String workFlowId,String nodeId, String oldProcessId, String nextNodeId,String instanceId,String itemId,String formId,String pdfPath,String trueJson,String isChildWf,String cType,String relation,String finstanceId,String specialProcess) throws Exception;
	
	List<WfTableInfo> getAllTableNotLc(String lcid);
	
	void updateProcess(String processId,String operate,String instanceId,String nodeId,String userId,String title,String workFlowId,String itemId,String pdfPath,String trueJson);
	
	void updateProcessShow(String workFlowId,String instanceId,String nodeId);
	
	boolean isAllOver(String workFlowId,String instanceId,String nodeId,String fInstanceId);
	
	void updateOver(String workFlowId,String instanceId,String nodeId );
	
	String updateInstanceOver(String workflowId,String instanceId,String beforeProcessId,String processId ,String nodeId,String userId,String vc_title,String formId,String trueJson, String pdfPath, String isWriteNewValue,String newProcessId);
	
	String updateInstanceOverAuto(String workflowId,String instanceId,String beforeProcessId,String processId ,String nodeId,String userId,String vc_title,String formId,String trueJson, String pdfPath, String isWriteNewValue,String newProcessId);
	
	List<WfProcess> findWfProcessList(String workFlowId,String instanceId,String nodeId, Integer stepIndex);
	
	List<Pending> getOverList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,String status);
	
	int getCountOfOver(String conditionSql,String userId,String status);

	String getCommentTagIds(String wfInstanceUid);

	List<WfFieldInfo> getFieldByTableid(String tableid);
	
	WfFieldInfo getFieldById(String id);
	
	String getTableAndColumnName(String fieldId);

	List<GetProcess> findProcessList(String instanceId);

	List<WfProcess> findProcessList(String nodeId, String instanceId,String userId);

	List<DoFile> getDoFileList(String bigDepId,String conditionSql, Integer pageindex, Integer pagesize);

	void saveConsult(WfConsult consult);

	List<WfConsult> getConsultList(String userId, String condition, Integer pageIndex, Integer pageSize);

	int getCountConsults( String userId,String condition);

	void updateConsultRead(String id);
	
	void updateConsultReplied(String id);

	WfConsult getConsultById(String id);

	WfProcess getProcessById(String processId);

	void saveProcess(WfProcess dupProcess);

	int getCountOfDuplicate(String conditionSql, String employeeGuid);

	List<Pending> getDuplicateList(String conditionSql, String employeeGuid,
			Integer pageIndex, Integer pageSize);

	void deleteDuplicate(String ids);
	
	String getClob(String tablename, String instanceid, String fieldname, String formId);

	boolean isDuplicated(WfProcess dupProcess);

	boolean hasEntrust(WfProcess dupProcess);

	String findDeptNameByEmpId(Employee emp);

	List<WfItem> findItemList();

	void savePerMes(PersonMessage perMes);

	PersonMessage findXccNamesByItemId(String id,String userId);

	void updatePerMes(PersonMessage pm);
	
	void deletePerMes(String id);

	void deleteConsult(String ids);

	int getCountDoFiles(String bigDepId,String conditionSql);

	void addXcc(WfProcess process);

	void save(WfProcess newProcess);

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

	String getClobOfWfProcess(String tableName, String wfInstanceUid, String fieldName);

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
	
	Object[] getToDbInfoIds(String nodeId);

	List<Pending> getOverList2(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype);

	WfItem findItemByWorkFlowId(String workFlowId, String webId);

	int getCountOfOver(String conditionSql, String employeeGuid, String itemid,
			String statustype);
	/*获得我的 请假列表*/
	int getCountOfMyLeave(String conditionSql,String userId, String itemid,
			String statustype);
	List<Pending> getMyLeaveList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype);
	void updateProcess(String processId, String string, String instanceId,
			String nodeId, String userId, String vc_title, String workFlowId,
			String itemId, String pdfPath, String string2, String formId,Integer urgency);
	// 判断其他子流程是否结束  多例
	boolean otherMultiChildProcessIsEnd(String fInstanceId,String instanceId);
	List<WfProcess> findMultiChildProcessList(String fInstanceId);
	// 倒数第二步
	List<WfProcess> findBoobyChildProcessList(String sql);
	// 判断其他子流程是否结束    单例
	boolean otherSingleChildProcessIsEnd(String fInstanceId,String instanceId);
	List<WfProcess> findSingleChildProcessList(String fInstanceId);
	// 判断是否是子流程
	boolean isChildProcess(String instanceId);
	
	WfNode getWfnNodeByInstanceId(String instanceId);
	
	WfProcess getWfProcessByInstanceid(String instanceId);
	
	void updateProcessDate();

	WfNode updateMainStatus(WfProcess wfProcess);
	
	//检查子流程是否已经办结完成
	boolean checkIsChildEnd(WfProcess wfProcess);
	
	List<String> getWfProcessByWfChildInstanceId(String wfChildFinstanceId);
	
	void updateProcessDate(String instanceId, String nodeid, Integer stepindex, Integer days);
	
	void updateWfProcessStatus(String instanceId);
	
	WfProcess getParentProcessByInstanceid(String instanceId);
	
	List<WfProcess> getWfProcessList(String instanceId);
	
	List<WfProcess> getWfProcessList(HashMap<String, String>  map);
	
	GetProcess findGetProcessByPInstanceID(String pinstanceId, String stepIndex);
	
	List<GetProcess> findProcess(String pinstanceId);

	List<String> findInstanceIdsByFinstanceId(String finstanceId);

	void saveNewWfProcess(WfProcess wfProcess, String employeeGuid ,String userId);
	
	WfNode getWfNodeById(String id);
	
	WfProcess getWfProcessByColoum(String finStanceId, String userid);
	
	//以下为待收已收操作
	List<DoFileReceive> getDoFileReceiveList(String userId,
			Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map);

	int getDoFileReceiveCount(String userId,Integer status, Map<String,String> map);
	
	void addDoFileReceive(DoFileReceive doFileReceive);
	
	DoFileReceive getDoFileReceiveById(String id);
	
	void updateDoFileReceive(DoFileReceive doFileReceive);

	void updateProcess(WfProcess wfProcess);

	List<WfProcess> getProcessList(String instanceId);
	//1未办结批示,2已办结未阅,3已办结已阅

	List<Object[]> getDoFileByPsbjList(int i, Map<String, String> map,
			int pageIndex, int pageSize);

	void updateIsyyById(String id);

	List<GetProcess> getChildWfProcessList(String instanceId,String processId);
	// 获取交办历程
	List<GetProcess> getJBProcessList(String instanceId,String processId);

	void savePushMessage(PushMessage pm);

	List<PushMessage> getPushMessageList(String employeeGuid,String userId,String instanceId);

	List<DoFile> getDoFileList1(String bigDepId, String conditionSql,
			Integer pageIndex, Integer pageSize);

	List<WfProcess> insertAferEndProcess(WfNode wfNode, String pdfNewPath, WfProcess process,Employee emp, String value, String mergePdf);
	
	String getworkFlowIdByInstanceId(String instanceId);
	
	String getFinstanceIdByInstanceId(String instanceId);
	
	String getPinstanceId(String instanceId);

	void addNewProcessOfFake(WfProcess oldProcess, String fromNodeId);

	DoFileReceive getDoFileReceive(String instanceId);

	void addSw(Sw sw);

	void addDoFile(DoFile doFile);

	Date getEndDate(Date nowTime, String deadline, String deadlineunit);

	void addWSPBD(Map<String, String> map);

	List<Sw> getSwdrlbList(int pageIndex, int pageSize);

	int getCountSwdrlbList();

	List<WfTableInfo> getOfficeTableList(Map<String,String> map);
	
	void clearTableData(String[] tableNames);
	
	void removeOfficeTable(String[] tableNames);
	
	List<Object[]> getAllTableList();

	void updateDoFileIsDelete(String id,String isdelete);

	int getCountHszDoFiles(String bigDepId, String conditionSql);

	List<DoFile> getHszDoFileList(String bigDepId, String conditionSql,
			Integer pageIndex, Integer pageSize);

	DoFile getDoFileById(String id);

	List<WfProcess> getAllProcessList(String instanceId);

	void deleteWfProcesss(List<WfProcess> list);

	void deleteDoFile(DoFile doFile);

	List<PushMessage> getPushMessageList(String employeeGuid, String insertid);
	
	List<Object[]> getTableColoumValue(String tableName, String coloum, String InstanceId);

	List<WfProcess> getWfProcessByInstanceidAndStatus(String finstanceId);
	
	String getWaitingProcessId(WfProcess wfProcess);
	
	void update(WfProcess oldProcess);

	void addNewProcessOfSend(WfProcess wfProcess,String userId,Sw sw, String fjpath,
			Employee emp,String newInstanceId, String dyfs,Date nowTime, String type, String formUserId);
	
	void saveReissueDofileOfSend(String fProcessId, String toDepId, Employee emp, String dyfs, DoFileReceive receive);

	WfProcess isGetTs(String insertid);
	
	List<String> getAttachmentTagByForm(String formId);
	WfProcess getMasterProcess(WfProcess oldProcess);
	
	List<WfProcess> getNextProcess(WfProcess oldProcess);

	Sw getSwByInstanceId(String instanceId);

	void updateSw(Sw sw);
	
	String getMaxProcessIdByAllInstanceId(String allInstanceId);

	WfChild getwfChildByCId(String workflowId);
	
	int getStepIndexByInstanceId(String instanceId);
	
	List<Object[]> getDofileReceiveList(Map<String,String> map, Integer pageindex, Integer pagesize);
	
	int getDofileReceiveCount(Map<String,String> map);
	
	 List<Object[]>  getReceiveInfo(String fprocessid,String applydate);
	//查看当前实例ID的最后一个单子
	WfProcess getMaxWfProcessIdByInstanceId(String instanceid);

	//查看推送有没有新消息
	int getCountMessage(WfProcess wfProcess, String employeeGuid);

	void updatePushMessageZt(String employeeGuid, WfProcess wfProcess);
	
	List<WfProcess> getNodeProcess(String workFlowId,String instanceId,String nodeId,String fInstanceId);
	
	//根据流程id，节点id，表单ID获取改节点有几个字段必填,第一个字段为编码，第二个字段为中文名称
	List<Object[]> getIsBt(String workFlowId, String nodeid, String formId,String isbt);

	Object[] getIsBtName(String workflowid, String nodeId, String newFormId,
				String formtagname);
	
	Object[] getIsBtName(String workflowid, String nodeId, String newFormId,
			String formtagname, String userId);
	
	// 是否批阅
	Object[] getIsPy(String workflowid, String nodeId, String newFormId,
			String formtagname);

	String updateInstanceFirstStepOver(String workFlowId, String instanceId,
			String nodeId, String userId, String vc_title, String formId,
			String trueJson, String pdfPath, String currentItemId,String processId);

	void updatePushMessage(String employeeGuid, WfProcess wfProcess);

	void updateWfProcessByTs(String employeeGuid, WfProcess wfProcess);
	
	List<Object[]> getNewestWfProcess(String instanceId);
	
	List<WfProcess> getWfProcessList(WfProcess wfProcess);
	
	void addProcess(WfProcess wfProcess);

	List<Object[]> getKcqWfProcess();

	Object[] getYwbList(String instanceid, String tableName, String culoum);

	List<PushMessage> getPushMessage(String employeeGuid, String wfInstanceUid);

	/**
	 * 根据主协办实例id获取协主办实例id
	 * @param instanceId 主协办实例id
	 * @param doType 主办还是协办
	 * @return
	 */
	String[] getIntanceIdByZxIntanceId(String instanceId, String doType);
	
	List<WfProcess> getWfProcessByEntity(WfProcess wfProcess);
	
	/**
	 * 获取办件收藏夹中的数量
	 * @param bigDepId
	 * @param conditionSql
	 * @param userId
	 * @return
	 */
	int getCountOfDoFileFavourites(String bigDepId,String conditionSql, String userId);
	
	/**
	 * 分页获取收藏夹中的办件
	 * @param bigDepId
	 * @param conditionSql
	 * @param userId
	 * @param pageindex
	 * @param pagesize
	 * @return
	 */
	List<DoFile>  getDoFileFavouriteList(String bigDepId,String conditionSql,String userId, Integer pageindex, Integer pagesize);
	
	/**
	 * 新增关联关系到收藏夹
	 * @param dofileFavourite
	 */
	void saveDofileFavourite(DofileFavourite dofileFavourite);
	/**
	 * 删除收藏关系
	 * @param dofileFavourite
	 */
	void removeDofileFavourite(DofileFavourite dofileFavourite);
	
	/**
	 * 根据id获取收藏关系
	 * @param dofileId
	 * @return
	 */
	DofileFavourite getDofileFavouriteById(String dofileId, String userId);

	//获取收藏夹列表
	String getFavDoFileListOfMobile(String conditionSql,String userid, int count, int i,
			Integer pagesize,String serverUrl);

	String getDofileReceiveListOfMobile(String departId,String userId,Integer pageIndex,Integer pageSize,Integer status, String condition);

	int getDoFileReceiveCountOfMobile(String userId, Integer status,
			String condition);
	
	void saveDoFile(DoFile doFile);
	
	List<DoFileReceive> getDofileFavouriteByFprocessId(String fProcessid);
	
	String getSwdjh(String type);

	/**
	 * 获取收文信息
	 * @param pageSize 
	 * @param pageIndex 
	 * @param swdj 
	 * @return
	 */
	List<Object[]> findSw(Integer pageIndex, Integer pageSize,String deptName,
			String swdj, String conditionSql, String conditionSql2);

	int findCountSw(String deptName, String swdj, String conditionSql, String conditionSql2);
	
	void saveMsgSend(MsgSend msgSend);
	
	DoFileReceive addSend(String userId,Sw sw, String fjpath,
			Employee emp,String newInstanceId,WfProcess wfProcess,boolean isReback,Date nowTime);
	
	DoFileReceive addSend(String deptId, String userId,Sw sw, String fjpath,
			Employee emp,String newInstanceId,WfProcess wfProcess,boolean isReback,Date nowTime);
	/**
	 * 
	 * 描述：查询该办件应该由谁来处理
	 * @param processId
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2014-8-11 上午9:25:43
	 */
	String findPendDealIngUser(String processId);
	
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
	List<DoFile> findAllDofileList(String wfTitle, Integer pageIndex, Integer pageSize );

	// 根据父instanceid, 关联部门id 查询 收文表
	List<DoFileReceive> getDoFileReceiveByPIdAndDeptIds(String getpInstanceId,
			String linkDeptIds);

	void addRely(Reply reply) ;

	List<DocXgDepartment> getDocXgDepartmentListByDepId(String depid);
	
	
	void sendNextProcess(String vc_title, String userId,
			String employeeGuid, String commentJson, String pdfPath, WfProcess oldProcess
			, String nextNodeId);
	
	/**
	 * 
	 * 描述：并行结合: 该步骤最后的处理人员[承担 发送下一步]; [意见填写放置在该步骤]
	 * @param wfProcess
	 * @return WfProcess
	 * 作者:蔡亚军
	 * 创建时间:2014-8-25 上午8:59:44
	 */
	WfProcess findBxjhWfProcess(WfProcess wfProcess);


	/**
	 * 描述：假删除办件
	 * @param instanceId void
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-9-18 下午3:21:08
	 */
	void falseDeleteDoFile(String dofileId, String instanceId,Employee emp);

	/**
	 * 描述：恢复办件
	 * @param instanceId void
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-9-18 下午5:10:33
	 */
	void recoverDoFile(String dofileId ,String instanceId);
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

	/**
	 * 
	 * 描述：发起第一步
	 * @param userIds
	 * @param nextNodeId void
	 *
	 * 作者:Yuxl
	 * 创建时间:2015-1-14 下午4:31:29
	 */
	List<String> sendFirstProcess(String userIds, String nextNodeId,WfProcess oldProcess,String commonJSON);

	/**
	 * 描述： 判断其他交办流程是否结束，是 更新action_Status
	 * @param fjbProcessId void
	 *
	 * 作者:Yuxl
	 * 创建时间:2015-1-15 下午2:51:02
	 */
	boolean isAllJBEnd(String fjbProcessId);
	
	
	/**
	 * 
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蔡亚军
	 * 创建时间:2014-11-14 上午9:12:37
	 */
	void saveEndInstanceId(String instanceId);
	
	boolean delEndInstanceId(String instanceId);

	void deleteErrorProcess();

	void insertFirstTempProcess(String processId, String operate,
			String instanceId, String nodeId, String userId, String title,
			String workFlowId, String itemId, String pdfPath, String trueJson,
			String formId);
	
	List<WfProcess> findWfProcessList(WfProcess wfp);
	
	List<WfProcess> findWfProcessList(String instanceId, Integer stepIndex);

	int getNewCountDoFiles(String conditionSql);

	List<DoFile> getNewDoFileList(String serverUrl,String conditionSql, Integer pageIndex,Integer pageSize);

	void saveAndModifyData(String fprocessid);

	List<DoFileReceive> getDeptIdByFprocessId(String fprocessid);
	
	JSONObject deleteSendInfo(String fProcessId);
	
	WfItem findWfItemById(String id);
	/**
	 * 获取流程发送的步骤
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @return WfProcess
	 * 作者:蔡亚军
	 * 创建时间:2015-12-7 下午3:53:57
	 */
	WfProcess findSendWfProcess(String instanceId);
	
	WfProcess findFakeWfProcess(String instanceId);
	
	String editCommentWriteRole(String commentJson, String processId, String instanceId, String nodeId, String userId);

	List<WfProcess> getProcessListByParams(Map<String, String> map);
	
	
	WfProcess saveWfProcessToNewItem(WfProcess wfProcess,String userId,Sw sw, Employee emp,String newInstanceId, 
			String formUserId, String itemId, String newWfrocessId);
	
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-2-6 下午5:59:04
	 */
	List<String> getDelPendingList(String conditionSql,String userId, Integer pageIndex, Integer pageSize);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param type
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-2-6 下午6:02:39
	 */
	int getCountOfDelPending(String conditionSql,String userId,String type);
	

	/**
	 * 获取下级待收接口
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param status
	 * @param map
	 * @return List<LowDoc>
	 * 作者:季振华
	 * 创建时间:2016-6-15 下午5:32:16
	 */
	List<LowDoc> getLowDocList(String userId,
				Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map);
	
	
	//以下为待收已收操作
	List<DoFileReceive> getReceiveAllList(String userId,
			Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map);

	int getReceiveAllCount(String userId,Integer status, Map<String,String> map);

	/**
	 * 描述：获取办理步骤列表
	 * @param instanceId
	 * @return List<StepIndexVO>
	 * 作者:蒋烽
	 * 创建时间:2017-4-5 下午5:22:02
	 */
	List<StepIndexVO> getStepList(String instanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @param employeeGuid
	 * @return List<FollowShip>
	 * 作者:蒋烽
	 * 创建时间:2017-4-11 下午3:09:22
	 */
	List<FollowShip> getFollowShips(String instanceId, String employeeGuid, String oldInstanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-4-11 下午3:23:13
	 */
	void editFollowShip(FollowShip entity);
	
	/**
	 * 描述：
	 * @param instanceId
	 * @param stepindex
	 * @return List<WfProcess>
	 * 作者:蒋烽
	 * 创建时间:2017-5-18 下午6:09:04
	 */
	List<WfProcess> findWfProcessByInstanceIdAndStepIndex(String instanceId, Integer stepindex);
	
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
	 * @param instanceId
	 * @param stepIndex
	 * @param userString
	 * @return List<WfProcess>
	 * 作者:蒋烽
	 * 创建时间:2017-7-17 上午8:38:41
	 */
	List<WfProcess> findStepWfPListByUserId(String instanceId, Integer stepIndex, String userString);
	
	public String getDofileReceiveListOfMobile(String userId, Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map, List<WfItem> list);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午10:23:08
	 */
	int getCountCyDoFiles(String conditionSql, String userId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageindex
	 * @param pagesize
	 * @return List<DoFile>
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午10:23:40
	 */
	List<DoFile> getCyDoFileList(String conditionSql,String userId, Integer pageindex, Integer pagesize);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageindex
	 * @param pagesize
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 上午11:47:35
	 */
	String getCyDofileListOfMobile(String conditionSql,String userId,Integer pageindex, Integer pagesize, String serverUrl);
	
	List<Object[]> findeWfps(String fromNodeId,String nodeId, String instanceId);
	
	
	/**
	 * 描述：获取全部排序的人员信息
	 * TableInfoService
	 * List<Employee>
	 * 作者:蒋烽
	 * 创建时间:2017 下午1:49:12
	 */
	List<Employee> findToSortEmployeeList(String allInstanceId, String nodeIds);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * TableInfoService
	 * WfProcess
	 * 作者:蒋烽
	 * 创建时间:2017 上午11:08:13
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
	 * TableInfoService
	 * String
	 * 作者:蒋烽
	 * 创建时间:2018 下午4:46:19
	 */
	String addTrueJsonNode(String trueJson, Map<String, String> map);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param list
	 * @return List<DoFile>
	 * 作者:蒋烽
	 * 创建时间:2018-3-16 下午3:49:58
	 */
	List<DoFile> getDoFile(String ids);
	
	public Integer getNoOwnerDofileCount(Map<String, String> map);
	
	public List<Object[]> getNoOwnerDofileList(Map<String, String> map,  Integer pageindex, Integer pagesize);
	
	String findWh(String instanceId);
	
	void addRecallLog(WfRecallLog recallLog);
	
	List<WfDuBanLog> getDuBanMsg();
	
	List<String> getOverProcessId(String instanceId);

	Object[] getNewsProcessByInstanceid(String instanceId);

	List<WfProcess> findWfProcessListByIsOver(WfProcess wfProcess);

	List<WfProcess> findWfProcessIdByInsIdAndStp(String instanceId, Integer stepIndex);

	/**
	 * 方法描述: [获取步骤信息]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-10-30-下午2:45:50<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param wfProcess
	 * @return
	 * List<WfProcess>
	 */
	List<WfProcess> getStepByEntity(WfProcess wfProcess);

	WfProcess findEndWfProcessByInstanceId(String instanceId);

	/**
	 * 方法描述: [查询过程信息（优化专用）]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-11-5-下午5:22:23<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param workFlowId
	 * @param instanceId
	 * @param nodeId
	 * @param stepIndex
	 * @return
	 * List<WfProcess>
	 */
	List<WfProcess> findWfProcessAnyInfo(String workFlowId, String instanceId,
			String nodeId, Integer stepIndex);

	/**
	 * 方法描述: [根据过程id修改是否展示（串行传阅）]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-11-5-下午5:23:00<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param processId
	 * void
	 */
	void updateIsShowByProcessId(String processId);

	OfficeInfoView getOfficeInfoByInstanceId(String instanceId);

	String findDuBanListByInsId(String instanceId);

	/**
	 * 方法描述: [根据所有id查到对应的DoFile对象]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-11-24-上午9:28:35<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param instanceIds
	 * @return
	 * List<DoFile>
	 */
	List<DoFile> findDoFilesByIds(String ids);

	String findDuBanTimeByInsId(String instanceId);
	
	public void addEndProcess(String instanceId,String title,String nodeId,String formId,WfProcess oldProcess,String pdfPath,String newProcessId);

	/**
	 * 方法描述: [获取该流程的办理记录的数量]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-12-21-下午5:53:37<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param instanceId
	 * @return
	 * int
	 */
	int getCountOfOperateLog(String instanceId);

	/**
	 * 方法描述: [获取该流程的办理记录列表]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-12-21-下午6:01:56<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param instanceId
	 * @param pageindex
	 * @param pagesize
	 * @return
	 * List<TrueOperateLog>
	 */
	List<TrueOperateLog> findOperateLogList(String instanceId,Integer pageindex, Integer pagesize);

	/**
	 * 方法描述: [仅查询需要的字段，想要哪个自己手动加]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-12-27-下午3:22:32<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param instanceId
	 * @param stepindex
	 * @return
	 * List<WfProcess>
	 */
	List<WfProcess> findWfProcessByInsAndIndex(String instanceId, Integer stepindex);

	void updateOverByNodeId(String workFlowId, String instanceId,
			String nextNodeId);

	void updateEndProcessStep(String instanceId, String nextNodeId);

	List<DoFile> setDoFileApplyTime(List<DoFile> doFileList);

	List<String> queryMultDeptByEmpId(String userId);

	String querySiteIdByDeptId(String deptId);

	boolean isHavePendingByInsIdAndEmpId(String instanceId,
			String userId);

	List<Employee> findAllLeaveEmps();

	EmployeeLeave findEmpLeave(Employee emp);

	void saveEmployeeLeave(EmployeeLeave employeeLeave);

	List<WfProcess> findPendingOfUserId(Employee emp, int fileType);

	void deleteWhOfFw(String instanceId);

	void deleteWhOfBw(String instanceId);

	Integer findJjcd(String instanceId);

	String getViewBhByInstanceId(String instanceId);

	String findLwdw(String instanceId);

	String findJjcdToString(String instanceId);

	String checkIsMultDept(Employee emp, String workflowId);

	String getDefaultMultDeptName(Employee emp, String nowSiteId, String cval);

	Object[] getViewInfoByInstanceId(String instanceId);

}
