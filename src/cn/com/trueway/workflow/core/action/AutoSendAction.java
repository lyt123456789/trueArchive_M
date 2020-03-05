package cn.com.trueway.workflow.core.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.AutoSendService;
import cn.com.trueway.workflow.core.service.TrueJsonService;
import cn.com.trueway.workflow.set.util.WebSocketUtil;

/** 
 * ClassName: AutoSendAction <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年7月17日 下午4:47:34 <br/> 
 * 
 * @author 张馨
 * @version  
 * @since JDK 1.6 
 */
public class AutoSendAction extends BaseAction {
	private static final long serialVersionUID = 235340112769831944L;
	
	private AutoSendService autoSendService;

	private TrueJsonService	trueJsonService;
	
	public AutoSendService getAutoSendService() {
		return autoSendService;
	}

	public void setAutoSendService(AutoSendService autoSendService) {
		this.autoSendService = autoSendService;
	}

	public TrueJsonService getTrueJsonService() {
		return trueJsonService;
	}

	public void setTrueJsonService(TrueJsonService trueJsonService) {
		this.trueJsonService = trueJsonService;
	}

	public void findAuto() {
		System.out.println("--------进入findAuto方法----------");
		List<AutoSend> list = autoSendService.getAutoSend();
		if(list!=null && list.size()>0){
			System.out.println("----------findAuto方法中找到了"+list.size()+"个需要自动办理的办件--------");
		}
		List<String> pIdList = new ArrayList<String>();
		List<String> csList = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (AutoSend autosend : list) {
				String processid = autosend.getProcessid();
				String instanceId = "";
				WfProcess wp = null;
				if(CommonUtil.stringNotNULL(processid)){
					wp = autoSendService.getNotOverProcessByID(processid);
					instanceId = wp.getWfInstanceUid();
				}
				
				//TrueJson oldJson = trueJsonService.findNewestTrueJson(instanceId);
				TrueJson oldJson = trueJsonService.findNewestTrueJsonByInstanceId(instanceId);
				
				//保存日志信息表
				TrueJson entity = new TrueJson();
				entity.setInstanceId(instanceId);
				entity.setProcessId(processid);
				entity.setSaveDate(new Date());
				if(null != wp){
					entity.setUserId(wp.getUserUid());
				}
				if(null != oldJson){
					entity.setTrueJson(oldJson.getTrueJson());
				}
				entity.setExcute("autoSendNext");
				autoSendService.saveTrueJson(entity);
				if (CommonUtil.stringNotNULL(processid)) {
					//更新旧数据
					autoSetOldProcess(processid);
				}
				int isMaster = autosend.getIsMaster();
				String same = autosend.getSameProcess();
				if(CommonUtil.stringNotNULL(same) && isMaster == 1){
					if(!pIdList.contains(same)){
						pIdList.add(same);
					}
				}
				if(CommonUtil.stringNotNULL(same) && isMaster == 0){//说明存在并行完全式
					if(!csList.contains(same)){
						csList.add(same);
					}
				}
			}
		}
		System.out.println("--------findAuto方法断点1----------");
		if(pIdList != null && pIdList.size() > 0){
			for(String processid:pIdList){
				if(CommonUtil.stringNotNULL(processid)){
					autoSendNext(processid);
				}
			}
		}
		System.out.println("--------findAuto方法断点2----------");
		if(csList != null && csList.size() > 0){//处理并行完全式
			for(String processid:csList){
				if(CommonUtil.stringNotNULL(processid)){
					WfProcess wp = autoSendService.getNotOverProcessByID(processid);
					if(wp != null){
						String instanceid = wp.getWfInstanceUid();
						if(CommonUtil.stringNotNULL(instanceid)){
							autoSendService.updateMasterProcess(instanceid);
						}
					}
				}
			}
		}
		System.out.println("--------findAuto方法断点3----------");
	}
	
	/**
	 * 更新旧数据
	 */
	public void autoSetOldProcess(String processId) {
		WfProcess lastProcess = autoSendService.getProcessByID(processId);
		if (lastProcess != null) {
			lastProcess.setIsOver("OVER");
			lastProcess.setFinshTime(new Date());
			autoSendService.updateProcess(lastProcess);
			WebSocketUtil uitl = new WebSocketUtil();
			try {
				uitl.delBadge(lastProcess.getUserUid(), "", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 到期自动办理
	 */
	public void autoSendNext(String processId) {
		WfProcess lastProcess = autoSendService.getNotOverProcessByID(processId);
		if (lastProcess != null) {
			// 复制本环节数据
			WfProcess newProcess = lastProcess;
			String instanceId = lastProcess.getWfInstanceUid();
			String fromNodeId = lastProcess.getNodeUid();// 本节点
			Integer maxStepIndex = lastProcess.getStepIndex();
			String fromUserId = lastProcess.getUserUid();
			String workFlowId = lastProcess.getWfUid();
			// 根据本节点Id查找下一节点Id
			List<WfNode> nodes = autoSendService.showNode(workFlowId, fromNodeId, instanceId);
			WfNode nextNode = null;
			String userid = "";
			String nextNodeId = "";
			if (nodes != null && nodes.size() > 0) {
				nextNode = nodes.get(0);
			}
			if (nextNode != null) {
				nextNodeId = nextNode.getWfn_id();
				userid = nextNode.getWfn_bd_user();
			}
			if (userid != null && !"".equals(userid)) {
				String [] userids = userid.split(",");
				WebSocketUtil util = new WebSocketUtil();
				for (String string : userids) {
					Employee emp = autoSendService.findEmployeeById(string);
					newProcess.setAction_status(0);
					newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
					newProcess.setNodeUid(nextNodeId);
					newProcess.setDoType(null);
					newProcess.setApplyTime(new Date());
					newProcess.setFinshTime(null);
					newProcess.setStepIndex(maxStepIndex + 1);
					newProcess.setFromNodeid(fromNodeId);
					newProcess.setToNodeid(nextNodeId);
					newProcess.setFromUserId(fromUserId);
					newProcess.setUserUid(string);
					newProcess.setUserDeptId(emp.getDepartmentGuid());
					newProcess.setIsOver("NOT_OVER");
					autoSendService.saveProcess(newProcess);
					try {
						util.apnsPush(newProcess.getProcessTitle(), fromUserId, "", "", "", string);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
