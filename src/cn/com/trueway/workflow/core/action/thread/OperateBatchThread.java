package cn.com.trueway.workflow.core.action.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.workflow.core.action.TableInfoAction;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;

/**
 * 文件名称： cn.com.trueway.workflow.core.action.thread.OperateBatchThread.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2018-12-6<br/>
 * 功能说明： 批量办理线程 <br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 */
public class OperateBatchThread extends Thread {

	private TableInfoService tableInfoService;

	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private TableInfoAction	 tableInfoAction;

	private String step;// 选择操作

	private String nodeId;

	private WfProcess wfp;

	private String saveformData;

	private String formJson;

	private String userId;
	
	private String serverUrl;

	public OperateBatchThread() {

	}

	public OperateBatchThread(TableInfoService tableInfoService,
			WorkflowBasicFlowService workflowBasicFlowService,TableInfoAction tableInfoAction, String step,
			String nodeId, WfProcess wfp, String saveformData, String formJson,
			String userId,String serverUrl) {
		this.tableInfoService = tableInfoService;
		this.workflowBasicFlowService = workflowBasicFlowService;
		this.step = step;
		this.nodeId = nodeId;
		this.wfp = wfp;
		this.saveformData = saveformData;
		this.formJson = formJson;
		this.userId = userId;
		this.tableInfoAction = tableInfoAction;
		this.serverUrl = serverUrl;
	}

	@Override
	public void run() {

		if (step.equals("optBatch")) {
			
			boolean isEndProcess = false;   //是否办结
			String processId = wfp.getWfProcessUid();
			String vc_title = wfp.getProcessTitle();
			String instanceId = wfp.getWfInstanceUid();
			String workFlowId = wfp.getWfUid();
			String newProcessId = "";
			
			
			List<WfNode> nodeLists = new ArrayList<WfNode>();
			WfNode wfNode = tableInfoService.getWfNodeById(wfp.getNodeUid());
			if (wfNode != null) {
				String wfn_route_type = wfNode.getWfn_route_type();
				if (wfn_route_type != null && wfn_route_type.equals("6")) {// 串行传阅式
					List<WfProcess> list = tableInfoService.findWfProcessAnyInfo(wfp.getWfUid(),wfp.getWfInstanceUid(), wfp.getNodeUid(),wfp.getStepIndex());
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getWfProcessUid().equals(processId)) {
							if (i == list.size() - 1) {
								System.out.println("当前节点为并行传阅模式, 且传阅结束");
							} else {
								WfProcess wfpr = list.get(i + 1);
								tableInfoService.updateIsShowByProcessId(wfpr
										.getWfProcessUid());
								break;
							}
						}
					}
				}
				nodeLists = workflowBasicFlowService.showNode(wfp.getWfUid(), wfp.getNodeUid(), wfp.getWfInstanceUid());
				for (WfNode wfn : nodeLists) {
					if (("end").equals(wfn.getWfn_type())||(wfn.getNode_isReply() != null && wfn.getNode_isReply()==1)) {
						isEndProcess = true;
						break;
					}
				}

				if (wfn_route_type != null && (wfn_route_type.equals("4") || wfn_route_type.equals("6"))) { // 并行传阅式样
					// 检查该步骤信息是否已经被处理
					// List<WfProcess> sameStepWfList =
					// tableInfoService.findWfProcessList(wfp);
					List<WfProcess> sameStepWfList = tableInfoService.findWfProcessListByIsOver(wfp);
					int count = 0;
					for (int i = 0; i < sameStepWfList.size(); i++) {
						if (sameStepWfList.get(i).getIsOver()
								.equals("NOT_OVER")) {
							count++;
							break;
						}
					}
					if (count == 0) { // count=0时表示该节点已经办结完成
						List<WfNode> nodeList = workflowBasicFlowService.showNode(wfp.getWfUid(), wfp.getNodeUid(), wfp.getWfInstanceUid());
						WfNode nextNode = null; // 下一节点
						if (nodeList != null && nodeList.size() > 0) {
							for (WfNode node : nodeList) {
								if (("end").equals(node.getWfn_type())||(node.getNode_isReply() != null && node.getNode_isReply()==1)) {
									isEndProcess = true;
								}
								String xtoUserId = node.getWfn_bd_user();
								if (StringUtils.isNotBlank(xtoUserId)) {
									nextNode = node;
									break;
								}
							}
							if (null == nextNode) {
								nextNode = nodeList.get(0);
							}
						}
						if(isEndProcess){
							newProcessId =UuidGenerator.generate36UUID(); 
							try {
								tableInfoAction.endAutoOperate(instanceId, workFlowId, wfp.getFormId(), processId, wfp.getItemId(), wfp.getNodeUid(), formJson, "true", userId, saveformData, serverUrl);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							sendNextProcess(nextNode, wfp, saveformData, vc_title,
									formJson, "", false, userId);
						}
					}
					
				}
			}
		}
		
	}

	/**
	 * 方法描述: [插入下一节点数据]<br/>
	 * 初始作者: lihanqi<br/>
	 * 创建日期: 2018-12-6-下午4:46:17<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * 
	 * @param nextNode
	 * @param wfprocess
	 * @param value
	 * @param vc_title
	 * @param trueJson
	 * @param formPage
	 * @param isCyWF
	 * @param userId
	 *            void
	 */
	public void sendNextProcess(WfNode nextNode, WfProcess wfprocess,
			String value, String vc_title, String trueJson, String formPage,
			boolean isCyWF, String userId) {
		String xtoUserId = ""; // 发送的人员
		if (nextNode != null) {
			xtoUserId = nextNode.getWfn_bd_user();
		}
		String workFlowId = wfprocess.getWfUid();
		String instanceId = wfprocess.getWfInstanceUid();
		String nextNodeId = nextNode.getWfn_id();
		String formId = nextNode.getWfn_defaultform();
		String title = wfprocess.getProcessTitle();
		if (xtoUserId == null || xtoUserId.equals("")) {
			// 检查下是否存在自动追溯
			List<WfBackNode> wfbList = workflowBasicFlowService
					.getBackNodeListByWfId(workFlowId, nextNodeId);
			if (wfbList != null && wfbList.size() != 0) {
				if ((nextNodeId).equals(wfbList.get(0).getFromNodeId())) {
					List<WfProcess> desPersons = tableInfoService
							.findProcesses(workFlowId, instanceId,
									wfbList.get(0).getToNodeId());
					if (desPersons.size() != 0 && desPersons != null
							&& !("").equals(desPersons)) {
						String xtoname = "";// 主送
						String xccname = "";// 抄送
						for (WfProcess wfProcess : desPersons) {
							if (wfProcess.getIsMaster() == 1) {
								xtoname = wfProcess.getUserUid();
							} else {
								xccname += wfProcess.getUserUid() + ",";
							}
						}
						if (!("").equals(xccname) && xccname.length() > 1) {
							xccname = xccname
									.substring(0, xccname.length() - 1);
						}
						xtoUserId = xtoname;
					}
				}
			}
		}
		try {

			WfNode node = workflowBasicFlowService.getWfNode(nextNodeId);
			String form_continue = node.getWfn_form_continue();
			String truePath = "";
			if (form_continue == null || form_continue.equals("0")) { // 延用
				truePath = wfprocess.getPdfPath();
				tableInfoService.sendNextProcess(title, xtoUserId, userId,
						trueJson, truePath, wfprocess, nextNodeId);
			}else{
				truePath = wfprocess.getPdfPath();
				tableInfoService.sendNextProcess(title, xtoUserId, userId,
						trueJson, truePath, wfprocess, nextNodeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
