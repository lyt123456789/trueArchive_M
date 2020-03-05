package cn.com.trueway.workflow.business.dao;

import java.util.HashMap;
import java.util.List;

import cn.com.trueway.workflow.business.pojo.HandRoundShip;
import cn.com.trueway.workflow.business.pojo.Library;
import cn.com.trueway.workflow.business.pojo.PressMsg;
import cn.com.trueway.workflow.core.pojo.Pending;

public interface BusinessDao {
	
	int findSendLibraryCount(String contionsql, String userId);
	
	List<Library> findSendLibraryList(String contionsql, String userId, Integer pageIndex, Integer pageSize);
	
	int findReceiveLibraryCount(String contionsql, String userId);
	
	List<Library> findReceiveLibraryList(String contionsql, String userId, Integer pageIndex, Integer pageSize);

	public List<Pending> getStatisticalList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);

	public int getCountOfStatistical(String conditionSql,String userId,String type,HashMap<String,String> map);
	
	public List<Pending> getPressList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);

	public int getCountOfPress(String conditionSql,String userId,String type,HashMap<String,String> map);

	List<PressMsg> getMsgListByProcessId(String processId);
	
	public void addPressMsg(PressMsg pressMsg);
	
	public List<Pending> getPressMsgList(String conditionSql,String userId,Integer pageIndex,Integer pageSize,HashMap<String,String> map);

	public int getCountOfPressMsg(String conditionSql,String userId,String type,HashMap<String,String> map);
	
	
	/**
	 * 描述：添加传阅关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午3:43:28
	 */
	void addHandRoundShip(HandRoundShip entity);
	
	/**
	 * 描述：更新传阅关联关系
	 * @param entity void
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午3:43:30
	 */
	void updateHandRoundShip(HandRoundShip entity);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @param instanceId
	 * @return HandRoundShip
	 * 作者:蒋烽
	 * 创建时间:2017-9-6 下午4:22:43
	 */
	HandRoundShip selectHandRoundShip(String userId,String instanceId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param instanceId
	 * @param pageIndex
	 * @param pageSize
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2017-9-7 下午5:35:23
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
