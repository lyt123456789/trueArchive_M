package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.AccessLog;
import cn.com.trueway.workflow.core.pojo.AutoFile;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.FileDownloadLog;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.set.pojo.LostAttsDf;
import cn.com.trueway.workflow.set.pojo.LostCmtDf;

/**
 * 描述：核心操作类服务层扩展
 * 作者：蒋烽
 * 创建时间：2017-4-10 上午9:57:04
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public interface TableInfoExtendService {
	
	/**
	 * 描述：查询 人员与关注办件的关联关系
	 * @param instanceId
	 * @param employeeGuid
	 * @return List<FollowShip>
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:58:26
	 */
	List<FollowShip> getFollowShips(String instanceId, String employeeGuid, String oldInstanceId);

	/**
	 * 描述：插入 人员与关注办件的关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:58:29
	 */
	void addFollowShip(FollowShip entity);

	/**
	 * 描述：删除 人员与关注办件的关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:58:32
	 */
	void removeFollowShip(FollowShip entity);

	/**
	 * 描述：更新 人员与关注办件的关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:58:34
	 */
	void editFollowShip(FollowShip entity);
	
	/**
	 * 描述：查询关注列表
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param status
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 下午2:32:50
	 */
	List<Pending> getFollowList(String conditionSql,String userId, Integer pageIndex,Integer pageSize,String status);
	
	/**
	 * 描述：统计关注列表的totalCount
	 * @param conditionSql
	 * @param userId
	 * @param status
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 下午2:33:15
	 */
	Integer getCountOfFollow(String conditionSql, String userId, String status);
	
	/**
	 * 描述：督办列表
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-4-13 上午8:44:07
	 */
	List<Map<String, String>> getOutOfDateList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	/**
	 * 描述：所有人超期办件的总数
	 * @param conditionSql
	 * @param type
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-4-13 上午8:44:15
	 */
	int getCountOfOutOfDate(String conditionSql, String type);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param instanceId
	 * @return List<Object[]>
	 * 作者:蒋烽
	 * 创建时间:2017-4-18 下午1:43:04
	 */
	List<Object[]> getOutOfDateListByInstanceId(String conditionSql, String instanceId);
	
	/**
	 * 描述：督办列表(移动端)
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-4-20 上午9:18:33
	 */
	String getOutOfDateList4Mobile(String conditionSql, String serverUrl, Integer pageIndex, Integer pageSize);
	
	void deleteFollowShip(String instaceId, String userId);
	
	/**
	 * 描述：提取意见
	 * TableInfoExtendService
	 * String
	 * 作者:蒋烽
	 * 创建时间:2017 下午9:43:43
	 */
	String getTextValue(String commentJson, String processId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-2-9 上午9:38:09
	 */
	void addAutoFile(AutoFile entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-2-9 上午9:38:11
	 */
	void modifyAutoFile(AutoFile entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午10:03:55
	 */
	void addAccessLog(AccessLog entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午10:03:49
	 */
	Integer countAccessLog(Map<String,String> map);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return List<AccessLog>
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午10:03:43
	 */
	List<AccessLog> getAccLog(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	List<LostCmtDf> getLostCmtDfList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	public int getLostCmtDfCount(Map<String,String> map);
	
	public List<LostAttsDf> getLostAttDfList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	public int getLostAttDfCount(Map<String,String> map);
	
	/** 
	 * skipNextNodes:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param nodes	下一步节点列表
	 * @param userId 当前登录人id
	 * @return 
	 * @since JDK 1.6 
	 */
	List<WfNode> skipNextNodes(List<WfNode> nodes,String userId);
	
	void sendAutoData(String nodeId, String fromNodeId);
	
	List<String> getColumnNames(String tableName);
	
	List<Object[]> getOutSideTab(String tableName, String columns, String instanceId);
	
	void addFileDownloadLog(DoFile file, Employee emp, String type);
	
	Integer countFileDownloadLog(Map<String, String> map);
	
	List<FileDownloadLog> getFileDownloadLog(Map<String, String> map, Integer pageIndex, Integer pageSize);
}
