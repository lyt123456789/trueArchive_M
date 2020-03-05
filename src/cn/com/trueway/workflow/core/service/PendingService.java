package cn.com.trueway.workflow.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.Pending1;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import net.sf.json.JSONObject;

public interface PendingService {

	public List<Pending> getPendingList(String conditionSql,String userId,Integer pageIndex,Integer pageSize);
	public int getCountOfPending(String conditionSql,String userId,String type);
	public int getCountOfPending(String conditionSql);
	public WfProcess getProcessByID(String processId);
	public void initRemainTime(List<Pending> list);
	public void initRemainTime1(List<Pending1> list);
	public void initIsOvertime(List<Pending> list);
	public List<String> getAllYear();
	public String getPendListOfMobile(String userId, int count, Integer column, Integer pagesize,String type);
	public StringBuilder getTodoForPortal(String userId, String column, String pagesize, String url, String callBack, String itemIds);
	public List<String> getTypeListOfPending(String userId);
	public String getPendListOfMobile(String userId, int count, int i,
			Integer pagesize, String type, String itemIds,String title,String serverUrl, String isReadFile);
	public String getPendListOfMobileForDB(String userId, int count, int i,
			Integer pagesize, String type, String itemIds,String title,String serverUrl, String isReadFile);
	public String getAllPendListOfMobile(String userId, int count, int i,
			Integer pagesize, String type, String itemIds1, String itemIds2,
			String itemIds3);
	public String getOverListOfMobile(String userId, List<Pending> pendList, int column,
			Integer pagesize, String type, String itemIds,String serverUrl);
	public void initDelayItem(List<Pending> list);
	// 根据用户身份,更新状态
	public void setBackStatus(List<Pending> list, String isAdmin);
	public boolean stepIsOver(String instanceId,String stepIndex);
	public void recycleTask(String instanceId, String stepIndex);
	public WfProcess getProcessByInstanceIds(String instanceIds,String userId);
	public Date getEndDate(int deadline, Date date, String wfm_calendar);
	public void initDelayItem1(List<Pending1> list);
	public List<WfProcess> findProcessListByFId(String f_instanceId);
	public List<WfProcess> getProcessByInstanceId(String instaceId);
	public List<WfProcess> findProcessListByFIdAndDoType(String getfInstancdUid,String doFile);
	
	public WfProcess checkInstanceIsOver(WfProcess wfProcess);
	public  List<WfProcess> findProcessListById(String insertid);
	public String getTodoForPortalNew(String userId, String column,
			String pagesize, String url, String callBack, String itemIds);
	// 自定义查询 待办
	public String getAllUserIdByFInstanceId(String getfInstancdUid, String mc);
	public String getZUserIdByFInstanceId(String fInstancdUid, String mc);
	public List<WfProcess> findProcessListByFInstanceid(String wfInstanceUid);
	public String getfinstancdidByInstancdid(String instancdid);
	public String getUserIdByInstancdid(String instancdid, String deptid);
	public DoFileReceive getDoFileReceiveByInstanceId(String instanceId);
	public String getDeparNameByDepartId(String toDepartId);
	// 根据 node id 加 stepIndex  获取办结 process
	public WfProcess getEndProcess(int step, String nodeUid,String instanceId);
	public String getPendListOfMobile(String superiorGuid, String employeeGuid,
			int count, String string, String serverUrl);
	public String getOverListOfMobile(String superiorGuid, String employeeGuid,
			List<Pending> list, String string, String type, String serverUrl);
	/**
	 * 
	 * 描述：获取带盖章的待办数
	 * @param conditionSql
	 * @param depIds
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2015-2-28 下午4:45:22
	 */
	int findLhfwCount(String conditionSql, String depIds); 
	/**
	 * 
	 * 描述：获取待盖章的办件总数
	 * @param conditionSql
	 * @param depIds
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Pending>
	 * 作者:蔡亚军
	 * 创建时间:2015-2-28 下午4:45:45
	 */
	List<Pending> findLhfwpendingList(String conditionSql,
			String depIds, Integer pageIndex, Integer pageSize);
	
	/**
	 * 
	 * 描述：获取各个客户端调用的接口
	 * @param wfProcess
	 * @param serverUrl
	 * @param isOver
	 * @param client
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-12-5 下午6:39:03
	 */
	String getChatInfoJson(WfProcess wfProcess, String serverUrl, String isOver, String client);
	
	/**
	 * 描述：获取移动端关注列表
	 * @param userId
	 * @param pendList
	 * @param serverUrl
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-5-26 下午6:37:23
	 */
	String getFollowListOfMobile(String userId, List<Pending> pendList,  String serverUrl);
	
	/**
	 * 
	 */
	JSONObject addFormPage(String instanceId);
	
	WfProcess getRecentProcess(String instanceId);
	
	void setBackStatus(List<Pending> list, String isAdmin, String userId);
	
	public int getCountOfExceedPending(String conditionSql,String userId,String type);
	
	public int getCountOfExceedPending2(String conditionSql);
	
	public int getCountOfExceedUR(String conditionSql);
	
	public List<Pending> getExceedPendingList(String conditionSql,String userId,Integer pageIndex,Integer pageSize);

	public List<Object[]> getExceedPendingList2(String conditionSql,Integer pageIndex,Integer pageSize);
	
	public List<Object[]> getExceedURList(String conditionSql,Integer pageIndex,Integer pageSize);
	
	public List<Department> getDeptListForpage();
	
	public List<Object[]> getSendUser(String instanceId);
	
	public void recallDoFileForBXCY(String instanceId, String stepIndex,String userId);

}
