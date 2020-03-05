package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.BasicFlow;
import cn.com.trueway.workflow.core.pojo.Node;
import cn.com.trueway.workflow.core.pojo.SoftRoute;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfXml;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;


public interface WorkflowBasicFlowService {


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
	public Node getNode(String nodeid);
	
	/**
	 * 通过id删除节点
	 * 
	 * @param nodeid
	 */
	public void deleteNode(String nodeid);
	
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
	public void deleteFromWFIDForNode(String wfid);

	/**
	 * 保存Route对象
	 * 
	 * @param route
	 */
	public void saveSoftRoute(SoftRoute route);
	
	/**
	 * 通过id获取路由参数
	 * 
	 * @param routeid
	 */
	public SoftRoute getSoftRoute(String routeid);
	
	/**
	 * 通过id删除路由参数
	 * 
	 * @param routeid
	 */
	public void deleteSoftRoute(String routeid);
	
	/**
	 * 更新路由
	 * 
	 * @param route
	 */
	public void updateSoftRoute(SoftRoute route);
	
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
	public void deleteFromWFIDForSoftRoute(String wfid);

	/**
	 * 保存basicFlow对象
	 * 
	 * @param node
	 */
	public void save(BasicFlow basicFlow);
	
	/**
	 * 通过id获取流程
	 * 
	 * @param routeid
	 */
	public BasicFlow get(String id);
	
	/**
	 * 通过id删除流程
	 * 
	 * @param nodeid
	 */
	public void delete(String id);
	
	/**
	 * 更新流程
	 * 
	 * @param Node
	 */
	public void update(BasicFlow basicFlow);
	
	/**
	 * 根据webid获取该站点下所有的流程
	 * 
	 * @param route
	 */
	public List<Object[]> getBasicFlowList(String roleIds, String conditionSql, Integer pageindex, Integer pagesize);
	
	/**
	 * 删除该web站点下所有的流程
	 * @param wfid
	 */
	public void deleteFromWEBID(String webId);
	
	/**
	 * 保存流程相关数据
	 */
	public void saveWfMain(WfMain wfMain);
	
	/**
	 * 根据id删除某一流程
	 */
	public void deleteWfMain(String id);
	/**
	 * 获取该ID流程的开始节点
	 */
	public WfNode getStartNode(String id);
	/**
	 * 根据id,nodeid获取该 id下面的 所有子节点
	 */
	public List<WfNode> getNode(String id,String node_id);
	/**
	 * 新建流程并且插入用户名和生成流程Id
	 */
	public void saveWorkFlow(WfMain main);
	/**
	 * 根据Id获得一个工作流
	 */
	public WfMain getWfMainById(String id);
	/**
	 * 获得节点方法
	 */
	public List<WfNode> showNode(String id,String node_id, String instanceId);
	/**
	 * 获得子流程方法
	 */
	public List<WfChild> showChildOfWf(String id,String node_id);
	/**
	 * 根据Id删除流程
	 */
	public void deleteWorkFlow(String id);
	
	/**
	 * 根据id获取wfnode
	 */
	public WfNode getWfNode(String id);
	/**
	 * 修改流程名
	 */
	public void updateWfMain(WfMain main);
	/**
	 * 根据表单去xml表中查询记录
	 */
	public WfXml getWfXml(String id);
	/**
	 * 保存流程xml
	 */
	public void saveWfXml(WfXml wfXml);
	
	public void updateWfXml(WfXml wfXml);
	/**
	 * 根据id删除xml
	 */
	public void deleteWfXml(String id);

	public WfNode findFirstNodeId(String workFlowId);

	public String findFormLocaltion(String formId);
	/**
	 * 根据条件模糊查询
	 */
	public List<WfMain> getWfMainListByRetrieval(String wfname,String province,String begin_rq,String end_rq,Integer pageindex, Integer pagesize);
	/**
	 * 获得流程表记录数
	 */
	public int getCountFromWfMain(String roleIds, String conditionSql);
	/**
	 * 获得模糊查询记录数
	 */
	public int getCountFromWfMainByRetrieval(String wfname,String province,String begin_rq,String end_rq);

	public WfNode findFormIdByNodeId(String nextNodeId);
	
	public List<WfNode> getSortNode(String workflowId);
	
	/**
	 * 获取所有流程对象
	 */
	public List<WfMain> getWfMainList();
	
	/**
	 * 判断工作流是否正在使用
	 */
	public int workFlowIsUserOrNot(String workFlowId);
	
	public List<WfNode> getNodesByPid(String pid);

	public WfLine findWfLineByNodeId(String workFlowId,String nextNodeId,String nodeId);

	public int getCountDoFiles();

	public String findValue(String tableName, String columnName, String formId,String workFlowId,String instanceId);

	public List<WfNode> getWfNodeList(String workFlowId);
	
	public List<String> getAllProcedures()throws DataAccessException;

	public void save(WfBackNode wfBackNode);

	public List<WfBackNode> getBackNodeListByWfId(String workflowId,String nextNodeId);

	public void delete(String workflowId, String wfBackNodeId);

	public List<WfBackNode> getBackNodeListByWfId(String workflowId);

	public WfBackNode getBackNodeByBackNodeId(String workflowId, String backNodeId);

	public void update(WfBackNode wfbackNode);

	public WfChild getWfChildByCid(String cid);

	public List<Object[]> getListWf(String id);

	public WfChild getWfChildById(String wfc_id);

	public void deleteWfChild(String wfc_id);

	public void saveWfChild(WfChild wfChild);

	public List<WfLine> findNextWfLineByNodeId(String nextNodeId, String workFlowId);

	public WfChild getWfChildByModuleId(String moduleId, String workFlowId);

	public List<WfNode> getNextNodeByChildWf(String f_workflowId, String child_module);
	
	public void updateNode(String wfn_pid, String wfn_id);
	
	public void updateLine(String wfm_pid, String wfn_id);

	public String getNodeNameByNodeIds(String nodeid);
	
	public String findFormPdf(String formId);
	
	public WfNode findNodeById(String nodeId);
	
	public WfChild getWfChildByPidAndCid(String wfc_cid, String wfc_pid);
	/**
	 * 将wfm_id流程中新增的节点添加到节点历史表中
	 * @param wfm_id
	 */
	public void addNodeToHistroy(String wfm_id);

	public void addLineToHistroy(String wfm_id);
	
	WfNode getWfNodeByModuleId(String moduleId, String workFlowId);

	/**
	 * 新增工作流
	 * @param main
	 * @return
	 */
	public WfMain addWorkFlow(WfMain main);
	
	/**
	 * 复制旧的工作流
	 * @param wfMain
	 * @param id
	 * @return
	 */
	JSONObject copyWorkFlow(WfMain wfMain, String id);
	
	public Object[] showNextNode(String id,String node_id);
	
	public List<WfNode> showNextNodeList(String id, String node_id);

	public WfLine getLineById(String wfl_id);
	
	JSONObject saveWfMainInfo(String wfm_id, WfMain wfMain, WfXml wfXml);
	
	/**
	 * 
	 * 描述：根据id获取流程名称
	 * @param id
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-9-13 下午5:31:54
	 */
	public String getWfMainNameById(String id);
	
	/**
	 * 描述：根据流程id生成业务表信息
	 * @param id
	 * @return Map<String,String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午1:57:33
	 */
	public Map<String ,String> geneOfficeInfo(String id,String newWfm_id,String filePath);
	
	/**
	 * 描述：根据流程id，生成form表单信息，备份form表单
	 * @param id
	 * @param newWfm_id
	 * @param rootFolder
	 * @return Map<String,String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午5:47:58
	 */
	public Map<String, String> geneFormInfo(String id, String newWfm_id,
			String rootFolder);
	
	/**
	 * 描述：根据流程id,获取用户组定义
	 * @param id
	 * @param newWfm_id
	 * @return Map<String,String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午2:45:36
	 */
	public Map<String, String> geneInnerUserInfo(String id, String newWfm_id,String rootFolder);
	
	/**
	 * 描述：获取正文模板内容
	 * @param id
	 * @param newWfm_id
	 * @param rootFolder
	 * @return Map<String,String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午3:38:48
	 */
	public Map<String, String> geneTemplateInfo(String id, String newWfm_id,String rootFolder,Map<String ,String> tableIds);
	
	/**
	 * 描述：生成WF_Main,wf_xml,wf_node,Wf_line数据
	 * @param id
	 * @param newWfm_id
	 * @param rootFolder
	 * @param tableIds
	 * @param innerUserIds
	 * @param formIds
	 * @param templateIds void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-25 下午4:43:10
	 */
	public Map<String, String> geneFlowInfo(String id, String newWfm_id, String rootFolder,
			Map<String, String> tableIds, Map<String, String> innerUserIds,
			Map<String, String> formIds, Map<String, String> templateIds);
	
	/**
	 * 描述：生成表单权限值
	 * @param id
	 * @param newWfm_id
	 * @param rootFolder
	 * @param tableIds
	 * @param innerUserIds
	 * @param formIds
	 * @param nodeIds
	 * @return Map<String,String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-26 下午3:15:45
	 */
	public Map<String, String> genePermitInfo(String id,String newWfm_id,
			String rootFolder, Map<String, String> tableIds,
			Map<String, String> innerUserIds, Map<String, String> formIds,
			Map<String, String> nodeIds);
	
	/**
	 * 描述：生成返回指定节点的数据
	 * @param id
	 * @param newWfm_id
	 * @param rootFolder
	 * @param nodeIds void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-30 下午5:49:09
	 */
	public void geneBackNodeInfo(String id, String newWfm_id,String rootFolder, Map<String, String> nodeIds);
	
	/**
	 * 描述：附件材料 绑定流程
	 * @param id
	 * @param newWfm_id void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-13 上午9:45:45
	 */
	public void geneAttRelationInfo(String id, String newWfm_id,String rootFolder);
	
	/**
	 * 描述：流程数据导入
	 * @param flowPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:07:01
	 */
	public void importFlowInfo(String flowPath);
	
	/**
	 * 描述：业务表数据更新或者新增
	 * @param officePath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:07:20
	 */
	public void importOfficeInfo(String officePath);

	/**
	 * 描述：表单数据插入 jsp form 导入
	 * @param formPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:07:57
	 */
	public void importFormInfo(String formPath);

	/**
	 * 描述：用户组描述插入
	 * @param innerUserPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:09:15
	 */
	public void importInnerUserInfo(String innerUserPath);

	/**
	 * 描述：表单许可数据插入
	 * @param permitPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:09:38
	 */
	public void importPermitInfo(String permitPath);

	/**
	 * 描述：表单模板数据
	 * @param templatePath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:10:00
	 */
	public void importTemplateInfo(String templatePath);

	/**
	 * 描述：流程设置处理
	 * @param setPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:11:28
	 */
	public void importSetInfo(String setPath);
	
	/**
	 * 描述：导入附件绑定材料信息
	 * @param attPath void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-13 上午10:24:14
	 */
	public void importAttInfo(String attPath);
	
	List<WfChild> findWfChildListByWfPid(String wfPid);
	
	List<WfChild> findWfChildListByWfcid(String wfcid);
	
	/**
	 * 描述：查询办件经历的单人节点
	 * WorkflowBasicFlowDao
	 * List<WfNode>
	 * 作者:蒋烽
	 * 创建时间:2017 上午9:26:11
	 */
	List<WfNode> findWfNodeByInstanceId(String instanceId, String processId);
	
	/** 
	 * getEndNode:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param workflowId
	 * @return 
	 * @since JDK 1.6 
	 */
	WfNode getEndNode(String workflowId);
	
	List<WfNode> getSortNodeId(String workflowId);

	/**
	 * 方法描述: [根据流程id查到所有的表单]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2019-4-23-下午4:31:48<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param workflowId
	 * @return
	 * List<ZwkjForm>
	 */
	public List<ZwkjForm> findAllFormByLcId(String workflowId);

	public String findRoleName(String wfn_staff);

	public int getCountFromWfItem(String roleIds, String conditionSql);

	public List<Object[]> getItemList(String roleIds, String conditionSql,
			int pageIndex, int pageSize);

	public String findRoleUserIds(String wfn_staff);
}
