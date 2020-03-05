package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.AccessLog;
import cn.com.trueway.workflow.core.pojo.AutoFile;
import cn.com.trueway.workflow.core.pojo.FileDownloadLog;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.set.pojo.LostAttsDf;
import cn.com.trueway.workflow.set.pojo.LostCmtDf;

/**
 * 描述：核心操作类dao层扩展
 * 作者：蒋烽
 * 创建时间：2017-4-10 上午9:07:28
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public interface TableInfoExtendDao {
	
	/**
	 * 描述：查询 人员与关注办件的关联关系
	 * @param instanceId
	 * @param employeeGuid
	 * @return List<FollowShip>
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:10:02
	 */
	List<FollowShip> selectFollowShip(String instanceId, String employeeGuid, String oldInstanceId);
	
	/**
	 * 描述：插入 人员与关注办件的关联关系
	 * @param entity
	 * @return FollowShip
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:17:47
	 */
	void insertFollowShip(FollowShip entity);
	
	/**
	 * 描述：删除 人员与关注办件的关联关系
	 * @param entity
	 * @return FollowShip
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:17:49
	 */
	void deleteFollowShip(FollowShip entity);
	
	/**
	 * 描述：更新 人员与关注办件的关联关系
	 * @param entity
	 * @return FollowShip
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午9:17:53
	 */
	void updateFollowShip(FollowShip entity);
	
	/**
	 * 描述：查询关注列表
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param status
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午11:47:41
	 */
	List<Pending> getFollowList(String conditionSql, String userId, Integer pageIndex, Integer pageSize, String status);
	
	/**
	 * 描述：统计关注列表的totalCount
	 * @param conditionSql
	 * @param userId
	 * @param status
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-4-10 上午11:48:32
	 */
	Integer getCountOfFollow(String conditionSql, String userId, String status);
	
	/**
	 * 描述：所有人超期办件列表
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-4-12 下午6:45:53
	 */
	List<Object[]> getOutOfDateList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	/**
	 * 描述：所有人超期办件的总数
	 * @param conditionSql
	 * @param type
	 * @return int
	 * 作者:蒋烽
	 * 创建时间:2017-4-12 下午6:46:56
	 */
	int getCountOfOutOfDate(String conditionSql, String type);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @return List<Pending>
	 * 作者:蒋烽
	 * 创建时间:2017-4-13 上午10:19:04
	 */
	List<Object[]> getOutOfDateListByInstanceId(String conditionSql,String instanceId);
	
	void deleteFollowShip(String instaceId, String userId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-2-9 上午9:35:48
	 */
	void insertAutoFile(AutoFile entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-2-9 上午9:36:11
	 */
	void updateAutoFile(AutoFile entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午9:47:51
	 */
	void addAccessLog(AccessLog entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午9:47:54
	 */
	Integer countAccessLog(Map<String,String> map);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @param pageIndex
	 * @param pageSize
	 * @return List<AccessLog>
	 * 作者:蒋烽
	 * 创建时间:2018-3-8 上午9:47:57
	 */
	List<AccessLog> selectAccLog(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	List<LostCmtDf> getLostCmtDfList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	int getLostCmtDfCount(Map<String,String> map);
	
	List<LostAttsDf> getLostAttDfList(Map<String,String> map, Integer pageIndex, Integer pageSize);
	
	int getLostAttDfCount(Map<String,String> map);
	
	WfNode getWfNodeById(String id);
	
	List<String> getInstanceId(String nodeId, String fromNodeId);
	
	List<WfProcess> getAutoSendStep(String nodeId, String fromNodeId, String instanceId);
	
	List<String> getColumnNames(String tableName);
	
	List<Object[]> selectOutSideTab(String tableName,String columns, String instanceId);
	
	void insert(FileDownloadLog entity);
	
	Integer countFileDownloadLogs(Map<String,String> map);
	
	List<FileDownloadLog> selectFileDownloadLogs(Map<String,String> map, Integer pageIndex, Integer pageSize);
}
