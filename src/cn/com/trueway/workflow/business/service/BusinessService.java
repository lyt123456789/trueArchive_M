package cn.com.trueway.workflow.business.service;

import java.util.HashMap;
import java.util.List;

import cn.com.trueway.workflow.business.pojo.HandRoundShip;
import cn.com.trueway.workflow.business.pojo.Library;
import cn.com.trueway.workflow.business.pojo.PressMsg;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;

public interface BusinessService {
	
	int findSendLibraryCount(String contionsql, String userId);
	
	List<Library> findSendLibraryList(String contionsql, String userId, Integer pageIndex, Integer pageSize);
	
	int findReceiveLibraryCount(String contionsql, String userId);
	
	List<Library> findReceiveLibraryList(String contionsql, String userId, Integer pageIndex, Integer pageSize);

	/**
	 * 获取所有设置过期限的办件
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param map
	 * @return List<Pending>
	 * 作者:季振华
	 * 创建时间:2016-9-23 下午3:05:36
	 */
	public List<Pending> getStatisticalList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);
	
	/**
	 * 获取所有设置过期限的办件个数
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param type
	 * @param map
	 * @return int
	 * 作者:季振华
	 * 创建时间:2016-9-23 下午3:05:51
	 */
	public int getCountOfStatistical(String conditionSql,String userId,String type,HashMap<String,String> map);
	/**
	 * 获取催办列表
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param map
	 * @return List<Pending>
	 * 作者:季振华
	 * 创建时间:2016-8-25 下午4:44:00
	 */
	public List<Pending> getPressList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);

	/**
	 * 获取催办信息个数
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param type
	 * @param map
	 * @return int
	 * 作者:季振华
	 * 创建时间:2016-8-25 下午4:46:01
	 */
	public int getCountOfPress(String conditionSql,String userId,String type,HashMap<String,String> map);
	/**
	 * 根据办件id获取催办信息
	 * 描述：TODO 对此方法进行描述
	 * @param processId
	 * @return List<PressMsg>
	 * 作者:季振华
	 * 创建时间:2016-8-24 下午7:35:33
	 */
	List<PressMsg> getMsgListByProcessId(String processId);
	/**
	 * 新增催办信息
	 * 描述：TODO 对此方法进行描述
	 * @param pressMsg void
	 * 作者:季振华
	 * 创建时间:2016-8-25 上午9:27:28
	 */
	public void addPressMsg(PressMsg pressMsg);
	
	/**
	 * 获取催办信息列表
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @param map
	 * @return List<Pending>
	 * 作者:季振华
	 * 创建时间:2016-8-25 下午4:44:00
	 */
	public List<Pending> getPressMsgList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);

	/**
	 * 获取催办信息个数
	 * 描述：TODO 对此方法进行描述
	 * @param conditionSql
	 * @param userId
	 * @param type
	 * @param map
	 * @return int
	 * 作者:季振华
	 * 创建时间:2016-8-25 下午4:46:01
	 */
	public int getCountOfPressMsg(String conditionSql,String userId,String type,HashMap<String,String> map);
	
	/**
	 * 描述：添加传阅关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午3:43:28
	 */
	void addHandRoundShip(String userIds, String instanceId, Employee emp);
	
	/**
	 * 描述：更新传阅关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午3:43:30
	 */
	void updateHandRoundShip(HandRoundShip entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2017-9-7 下午5:37:07
	 */
	Integer countHandRoundShips(String instanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @param pageIndex
	 * @param pageSize
	 * @return List<HandRoundShip>
	 * 作者:蒋烽
	 * 创建时间:2017-9-7 下午5:28:20
	 */
	List<HandRoundShip> selectHandRoundShips(String instanceId, Integer pageIndex, Integer pageSize);
}
