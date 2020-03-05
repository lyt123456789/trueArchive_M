package cn.com.trueway.workflow.socket.dao;

import java.util.List;

import cn.com.trueway.workflow.socket.pojo.SocketLog;

/** 
 * ClassName: SocketDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年4月12日 上午11:06:34 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public interface SocketDao {
	
	/** 
	 * getProcessByParams:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param map
	 * @return 
	 * @since JDK 1.6 
	 */
	List<Object[]> getProcessByParams(String instanceId, String userId);
	
	/** 
	 * addSocketLog:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param socketLog 
	 * @since JDK 1.6 
	 */
	void addSocketLog(SocketLog socketLog);
}
