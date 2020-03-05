package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.SoftRoute;



public interface WorkflowSoftRouteDao {
	
	/**
	 * 保存Route对象
	 * 
	 * @param route
	 */
	public void save(SoftRoute route);
	
	/**
	 * 通过id获取路由参数
	 * 
	 * @param routeid
	 */
	public SoftRoute get(String routeid);
	
	/**
	 * 通过id删除路由参数
	 * 
	 * @param routeid
	 */
	public void delete(String routeid);
	
	/**
	 * 更新路由
	 * 
	 * @param route
	 */
	public void update(SoftRoute route);
	
	/**
	 * 通过流程id过去 该流程所有的路由
	 * 
	 * @param route
	 */
	public List<SoftRoute> getRouteList(String wfid);
	
	/**
	 * 删除该流程id的所有节点
	 * @param wfid
	 */
	public void deleteFromWFID(String wfid);
}
