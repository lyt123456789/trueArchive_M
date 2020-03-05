package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workflow.core.pojo.BasicFlow;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.pojo.WfXml;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;



public interface WorkflowBasicFlowDao {

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
	 * 保存流程相关表
	 * @param wfMain
	 */
	public void saveWfMain(WfMain wfMain);
	
	
	/**
	 * 根据Id删除流程
	 */
	public void deleteWfMain(String id);
	/**
	 * 获取开始节点
	 * @return
	 */
	public WfNode getStartNode(String id);
	/**
	 * 获得子节点
	 * @param id
	 * @param node_id
	 * @return
	 */
	public List<WfNode> getNode(String id,String node_id);
	
	/**
	 * 获得子流程
	 * @param id
	 * @param node_id
	 * @return
	 */
	public List<WfChild> showChildOfWf(String id,String node_id);
	
	/**
	 * 生成工作流
	 */
	public void saveWorkFlow(WfMain main);
	
	/**
	 * 根据id打开工作流
	 */
	public WfMain getWfMainById(String id);
	/**
	 * 根据id删除工作流
	 */
	public void deleteWorkFlowById(String id);
	/**
	 * 根据id获取wfnode
	 */
	public WfNode getWfNode(String id);
	/**
	 * 修改流程名相关
	 */
	public void updateWfMain(WfMain wfMain);
	/**
	 * 获取XML中对象
	 */
	public WfXml getWfXml(String id);
	/**
	 * 保存流程图xml
	 */
	public void saveWfXml(WfXml wfXml);
	/**
	 * 更新流程图xml
	 */
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
	 * 获取流程表的记录条数
	 */
	public int getCountFromWfMain(String roleIds, String conditionSql);
	/**
	 * 获得模糊查询记录条数
	 */
	public int getCountFromWfMainByRetrieval(String wfname,String province,String begin_rq,String end_rq);

	public WfNode findFormIdByNodeId(String nextNodeId);
	
	public List<WfNode> getSortNode(String workflowId);
	/**
	 * 获得所有流程对象
	 */
	public List<WfMain> getWfMainList();
	
	public List<WfNode> getNodesByPid(String pid);

	public WfLine findWfLineByNodeId(String workFlowId,String nextNodeId,String nodeId);

	public int getWfProcessCountByWfUId(String workFlowId);

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

	public List<WfNode> getNextNodeByChildWf(String id, String child_module);
	
	public void updateNode(String wfn_pid, String wfn_id);
	
	public void updateLine(String wfm_pid, String wfn_id);

	public List<WfNode> getNodesByids(String nodeid);

	public String findFormPdf(String formId);
	
	public WfNode findNodeById(String nodeId);
	
	public WfChild getWfChildByPidAndCid(String wfc_cid, String wfc_pid);
	
	public void addNodeToHistroy(String wfm_id);
	
	public void addLineToHistroy(String wfm_id);
	
	public WfNode getWfNodeByModuleId(String moduleId, String workFlowId);
	
	public WfMain addWorkFlow(WfMain main);
	
	WfNode saveWfNode(WfNode wfNode) throws Exception;
	
	WfLine saveWfLine(WfLine wfLine) throws Exception;

	public WfLine getLineById(String wfl_id);

	public String geneBackNodeSql(WfBackNode back, String newBackId,String newWfm_id, Map<String, String> nodeIds);
	
	/**
	 * 描述：生成流程图基本信息
	 * @param id
	 * @param newWfm_id
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-26 上午10:02:00
	 */
	public String geneMainSql(String id, String newWfm_id,Map<String, String> tableIds);
	
	/**
	 * 描述：获取老的 line id
	 * @param id
	 * @param newWfm_id
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-25 下午5:00:10
	 */
	public List<String> getOldLineIds(String id);
	
	/**
	 * 描述：生成line sql
	 * @param string
	 * @param newLineId
	 * @param newWfm_id
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-25 下午6:28:59
	 */
	public String geneLineSql(String string, String newLineId,String newWfm_id);
	
	/**
	 * 描述：获取老的 node id
	 * @param id
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-25 下午6:34:54
	 */
	public List<String> getOldNodeIds(String id);
	
	/**
	 * 描述：生成node sql
	 * @param id
	 * @param newNodeId
	 * @param newWfm_id
	 * @param innerUserIds
	 * @param templateIds
	 * @param formIds
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-25 下午6:37:57
	 */
	public String geneNodeSql(String id, String newNodeId,
			String newWfm_id, Map<String, String> innerUserIds,
			Map<String, String> templateIds, Map<String, String> formIds);
	
	/**
	 * 描述：生成流程图详细信息
	 * @param id
	 * @param newWfm_id
	 * @param innerUserIds
	 * @param templateIds
	 * @param formIds
	 * @param tableIds
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-26 下午1:56:05
	 */
	public String geneXmlSql(String id, String newWfm_id,Map<String, String> innerUserIds, Map<String, String> templateIds,Map<String, String> formIds, Map<String, String> tableIds,Map<String, String> nodeIds,Map<String, String> lineIds);

	/**
	 * 描述：获取流程表单
	 * @param id
	 * @return List<ZwkjForm>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午6:06:10
	 */
	public List<ZwkjForm> getForms(String id);
	
	/**
	 * 描述：生成coreForm 表 sql
	 * @param id
	 * @param newFormId
	 * @param htm_36
	 * @param jsp_36
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午6:21:57
	 */
	public String geneCoreForm(ZwkjForm zwkjForm, String newFormId, String htm_36,String jsp_36,String workflowId);
	
	/**
	 * 描述：生成mapColumn 表sql
	 * @param id
	 * @param newFormId
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午6:22:32
	 */
	public List<String> geneMapColumns(String id, String newFormId);
	
	/**
	 * 描述：获取tab relation 
	 * @param id
	 * @param newFormId
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-12 下午7:13:48
	 */
	/*public List<String> geneTabRelationColumns(String id, String newFormId,String htmlPath,Map<String, String> formIds);*/
	
	/**
	 * 描述：根据流程id获取用户组id
	 * @param id
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午2:53:00
	 */
	public List<String> getInnerUserIdsByLcid(String id);
	

	/**
	 * 描述：生成innerUser 表sql
	 * @param string
	 * @param newInnerUserId
	 * @param newWfm_id
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午3:10:52
	 */
	public String geneInnerUsers(String string, String newInnerUserId,String newWfm_id);
	
	/**
	 * 描述：根据流程id，获取业务表名
	 * @param id
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午2:28:52
	 */
	public List<String> getOfficeTableByLcid(String id);
	
	/**
	 * 描述：生成T_WF_CORE_TABLE 数据
	 * @param string
	 * @param newTableId
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午2:55:13
	 */
	public String geneCoreTableSql(String oldTableId, String newTableId,String newWfm_id);
	

	/**
	 * 描述：生成t_wf_core_fieldinfo数据
	 * @param string
	 * @param newTableId
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午2:55:48
	 */
	public List<String> geneCoreFieldsSql(String oldTableId, String newTableId,String newWfm_id);
	
	/**
	 * 描述：创建表语句
	 * @param string
	 * @param newTableId
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午2:56:12
	 */
	public String geneCoreOfficeSql(String oldTableId, String tableName);

	/**
	 * 描述：生成表字段备注语句
	 * @param oldTableId
	 * @param oldTableName
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午4:24:39
	 */
	public List<String> genefieldComment(String oldTableId, String oldTableName);

	/**
	 * 描述：根据formid 获取表单权限
	 * @param key
	 * @return List<WfFormPermit>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-31 下午5:44:39
	 */
	public List<WfFormPermit> getPermitsByFormId(String key);
	
	/**
	 * 描述：获取权限值
	 * @param id
	 * @param newWfm_id
	 * @param tableIds
	 * @param innerUserIds
	 * @param formIds
	 * @param nodeIds
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-26 下午3:33:17
	 */
	public String genePermitSql(WfFormPermit permit, String newPermitId,String newWfm_id,
			Map<String, String> tableIds, Map<String, String> innerUserIds,
			Map<String, String> formIds, Map<String, String> nodeIds);
	
	/**
	 * 描述：生成template 表
	 * @param template
	 * @param newTemplateId
	 * @param doc_36
	 * @param newWfm_id
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午4:05:16
	 */
	public String geneTemplateSql(WfTemplate template, String newTemplateId,String doc_36, String newWfm_id);
	
	/**
	 * 描述：生成lable 表数据
	 * @param id
	 * @param newTemplateId
	 * @param tableIds
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-23 下午4:21:01
	 */
	public List<String> geneLabels(String id, String newTemplateId,Map<String, String> tableIds);
	
	/**
	 * 
	 * 描述：根据id获取流程名称
	 * @param id
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-9-13 下午5:33:09
	 */
	public String getWfMainNameById(String id);
	
	/**
	 * 描述：导入sql文件
	 * @param path
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 上午10:26:19
	 */
	public String importSql(String path);
	

	/**
	 * 描述：判断表是否存在
	 * @param string
	 * @return boolean
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-1 下午3:54:07
	 */
	public boolean existTable(String tableName);
	
	public void execSql(String sql);
	
	List<WfChild> findWfChildListByWfPid(String wfPid);
	
	List<WfChild> findWfChildListByWfcid(String wfcid);
	
	/**
	 * 描述：查询办件经历的单人节点
	 * WorkflowBasicFlowDao
	 * List<WfNode>
	 * 作者:蒋烽
	 * 创建时间:2017 上午9:26:11
	 */
	List<WfNode> findWfNodeByInstanceId(String instanceId,String processId);
	
	WfNode getEndNode(String workflowId);
	
	public List<WfNode> getSortNodeId(String workflowId);

	public List<ZwkjForm> findAllFormByLcId(String workflowId);

	public String findRoleName(String wfn_staff);

	public int getCountFromWfItem(String roleIds, String conditionSql);

	public List<Object[]> getItemList(String roleIds, String conditionSql,
			Integer pageIndex, Integer pageSize);

	public String findRoleUserIds(String wfn_staff);
}
