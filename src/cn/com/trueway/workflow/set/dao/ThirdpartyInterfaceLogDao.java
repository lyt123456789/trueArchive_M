package cn.com.trueway.workflow.set.dao;

import cn.com.trueway.workflow.set.pojo.ThirdpartyInterfaceLog;

/** 
 * ClassName: ThirdpartyInterfaceLogDao <br/> 
 * Function: 第三方接口调用日志持久层. <br/> 
 * date: 2018年7月31日 下午3:28:00 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public interface ThirdpartyInterfaceLogDao {
	
	/** 
	 * insert:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param entity
	 * @return 
	 * @since JDK 1.6 
	 */
	ThirdpartyInterfaceLog insert(ThirdpartyInterfaceLog entity);
	
	/** 
	 * update:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param entity 
	 * @since JDK 1.6 
	 */
	void update(ThirdpartyInterfaceLog entity);
}
