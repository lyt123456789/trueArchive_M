package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.ReadedEndPending;
import cn.com.trueway.workflow.core.pojo.TodoMessage;


/**
 * 
 * 描述：用于将 内部邮件。通知公告等信息集成到 待办中心去
 * 作者：蔡亚军
 * 创建时间：2016-5-12 下午3:58:18
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public interface IntegrateService {
	
	/**
	 * 
	 * 描述：获取待办中心全部需要办理的办件数
	 * @param conditionSql
	 * @param userId
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2016-5-12 下午4:01:29
	 */
	int findTodoMessageCount(String conditionSql, String userId);
	
	/**
	 * 
	 * 描述：pc端获取待办中心地全部信息内容
	 * @param conditionSql
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return List<TodoMessage>
	 * 作者:季振华
	 * 创建时间:2017-3-8 下午3:10:22
	 */
	List<TodoMessage> findTodoMessage(String conditionSql,String userId,Integer pageIndex,Integer pageSize,String itemIds);
	
	
	/**
	 * 
	 * 描述：获取待办中心地全部信息内容
	 * @param userId
	 * @param count
	 * @param i
	 * @param pagesize
	 * @param type
	 * @param itemIds
	 * @param title
	 * @param serverUrl
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-5-12 下午4:22:31
	 */
	String findTodoMessageOfMobile(String userId, int count, int i, Integer pagesize, String type, String itemIds,String title,String serverUrl);
	
	/**
	 * 
	 * 描述：获取办件中心 已读或者已办的办件数
	 * @param conditionSql
	 * @param userId
	 * @return int
	 * 作者:蔡亚军
	 * 创建时间:2016-5-12 下午4:01:52
	 */
	int findHavedoMessageCount(String conditionSql, String userId);
	
	/**
	 * 
	 * 描述：获取已经办理过的办件内容
	 * @param userId
	 * @param pendList
	 * @param column
	 * @param pagesize
	 * @param type
	 * @param itemIds
	 * @param serverUrl
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-5-12 下午4:39:31
	 */
	String findHavedoMessageOfMobile(String userId, Integer column, Integer pagesize, String conditionSql, String itemIds,String title, String serverUrl);

	int findMailCount(String conditionSql, String userId);
	/**
	 * 
	 * 描述：保存移动端意见签批
	 * @param eniity void
	 * 作者:蔡亚军
	 * 创建时间:2016-8-1 下午4:24:20
	 */
	void saveReadedEndPending(ReadedEndPending enity);
}
