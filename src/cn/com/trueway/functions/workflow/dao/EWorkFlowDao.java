/**
 * 文件名称:EWorkFlowDao.java
 * 作者:zhuxc<br>
 * 创建时间:2014-1-16 下午01:46:29
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.functions.workflow.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;

/**
 * 描述： 对EWorkFlowDao进行描述
 * 作者：zhuxc
 * 创建时间：2014-1-16 下午01:46:29
 */
public interface EWorkFlowDao {

	/**
	 * @param userId
	 * @param i
	 * @param parseInt
	 * @param parseInt2
	 * @param string
	 * @param itemIds
	 * @return List<Pending>
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2014-1-16 下午03:53:55
	 */
	List<Pending> getPendListOfMobile(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds);

	List<Object[]> getHaveDone4Breeze(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds,String timetype);
	int getPendListCount(String userId, int count, String type, String itemIds);
	/**
	 * @param userId
	 * @param i
	 * @param parseInt
	 * @param parseInt2
	 * @param string
	 * @param itemIds
	 * @return List<Pending>
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2014-3-16 下午03:53:55
	 */
	List<Pending> getTodo4WebNew(String userId, int count, Integer pageIndex, Integer pageSize,String type, String itemIds);
	
	List<Object[]> serachDataByStatus(String conditionSql, int count,
			Integer pagesize, String status, List<FieldSelectCondition> selects);

	int searchCountByStatus(String conditionSql, String status,
			List<FieldSelectCondition> selects);
	
	/**查询待收数量
	 * @param userId
	 * @param status
	 * @param map
	 * @return
	 */
	int getReceiveListByStatusCount(String userId, String status, Map<String,String> map);
	
	List getReceiveListByStatus(String userId,Integer pageIndex, Integer pageSize,String status, Map<String,String> map);
	
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
}
