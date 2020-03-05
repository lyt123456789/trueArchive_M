package cn.com.trueway.workflow.socket.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.socket.dao.SocketDao;
import cn.com.trueway.workflow.socket.pojo.SocketLog;

/** 
 * ClassName: SocketDaoImpl <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年4月12日 上午11:10:34 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public class SocketDaoImpl extends BaseDao implements SocketDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProcessByParams(String instanceId, String userId){
		String hql = "select isOver,isOpen from WfProcess where wfInstanceUid=:wfInstanceUid and userUid=:userUid order by step_index desc";
		Query query=super.getEm().createQuery(hql);
		query.setParameter("wfInstanceUid", instanceId);
		query.setParameter("userUid", userId);
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	@Override
	public void addSocketLog(SocketLog socketLog){
		getEm().persist(socketLog);
	}
}
