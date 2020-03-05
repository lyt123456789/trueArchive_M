package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.Node;
import cn.com.trueway.workflow.core.pojo.WfChild;



public interface WorkflowNoteDao {

	/**
	 * 保存node对象
	 * 
	 * @param node
	 */
	public void save(Node node);
	
	/**
	 * 通过nodeid获取节点参数
	 * 
	 * @param routeid
	 */
	public Node get(String nodeid);
	
	/**
	 * 通过id删除节点
	 * 
	 * @param nodeid
	 */
	public void delete(String nodeid);
	
	/**
	 * 更新节点
	 * 
	 * @param Node
	 */
	public void update(Node node);
	
	/**
	 * 通过流程id过去 该流程所有的节点
	 * 
	 * @param route
	 */
	public List<Node> getNodeList(String wfid);
	
	/**
	 * 删除该流程id的所有节点
	 * @param wfid
	 */
	public void deleteFromWFID(String wfid);
	
	
	
}
