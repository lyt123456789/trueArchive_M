package cn.com.trueway.workflow.set.dao;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.PushEntity;

public interface PushDao {
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
	 * update:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void update(PushEntity entity);
	
	/** 
	 * select:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> select(String token, String loginName);
	
	/** 
	 * selectNew:(获取机器信息（扩展）). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> selectNew(String token);

	/** 
	 * selectByUserId:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public List<PushEntity> selectByUserId(String userId);
	
	/** 
	 * deleteByToken:(根据token删除机器信息). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void deleteByToken(String token);
}
