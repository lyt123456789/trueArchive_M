package cn.com.trueway.workflow.set.service;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.PushEntity;

/**
 * ClassName: PushService <br/>
 * date: 2016年4月19日 上午10:36:03 <br/>
 *
 * @author adolph_jiang
 * @version 
 * @since JDK 1.6
 */
public interface PushService {
	/** 
	 * add:(新增推送信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void add(PushEntity entity);
	
	/** 
	 * delete:(刪除推送信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void delete(PushEntity entity);
	
	/** 
	 * getPushDate:(查询推送信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> getPushDate(String loginName, String token);
	
	/** 
	 * update:(更新推送信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void update(PushEntity entity);
	
	/** 
	 * getPushDate:(查询推送信息(扩展)). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> getPushDateByUserId(String userId);
	
	/** 
	 * selectNew:(获取机器信息（扩展）). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> getPushDateNew(String token);

	
	/** 
	 * deleteByToken:(根据token删除机器信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteByToken(String token);
}
