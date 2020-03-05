package cn.com.trueway.workflow.core.action.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.HtmlToPdf;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.SendNextProcess;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.GenePdfUtil;
import cn.com.trueway.workflow.set.util.HtmlHander;
import cn.com.trueway.workflow.set.util.MsgToObj;
import cn.com.trueway.workflow.set.util.SplitJSONPage;
import cn.com.trueway.workflow.set.util.ToPdfUtil;
import cn.com.trueway.workflow.set.util.WebSocketUtil;
/**
 * 生成合并转换pdf
 * @author caiyj
 *
 */
public class ToPdfThread extends Thread{
	
	public String newHtmlPath;	//表单转换的pdf地址
	
	private AttachmentService attachmentService;	
	
	private TableInfoService  tableInfoService;
	
	private String instanceId;	//实例id
	
	private String oldPdfPath;	//需要的合并pdf
	
	private String trueJson; 	//需要合并的相关意见
	
	private String step;		//步骤   “end”结束步骤, "middle" 中间步骤
	
	private String[] files ;
	
	private String firstpdfpath;
	
	private String pdfNewPath;
	
	private String mergePath;
	
	private String  pdfOldPath;
	
	private SendNextProcess entity;
	
	private Integer isExchange;
	
	private String  pdfType;	//pdfType为0,或者1的时候不需要转换pdf
	
	private Map<String, Object> map;
	
	private String route_type;
	
	private EmployeeService employeeService;
	
	private DepartmentService departmentService;
	
	private Employee emp ;					//系统当前登录人员
	
	private WfProcess wfProcess; 
	
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private ZwkjFormService zwkjFormService;
	
	private String sessionWorkflowid;
	
	private String nextNodePdfPath;
	
	private List<String> list;
	
	private String receiveId;
	
	private FlowService flowService ; 
	
	private String firstOverPdf;
	
	private List<DoFileReceive> dfrs ;
	
	private String nbUserId;
	
	private String pdfpath;
	
	private String dyfs;
	
	private String self_loop;
	
	private Sw sw;
	
	private ItemService itemService;
	
	private String processId;
	
	private String newProcessId;
	
	private String employeeGuid;
	
	private String title;
	
	private String nextNodeId;				
	
	private int yjHeight;
	
	private String xtoName;
	
	private String withForm;	
	
	private String formPageJson;				//第一页表单内容的完整Json数据
	
	private Integer urgency;
	
	private String formAttPath;	//表单转成pdf作为附件
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public ToPdfThread(String newHtmlPath, String oldPdfPath,
			String instanceId, AttachmentService attachmentService , String step,
			WfProcess wfProcess,TableInfoService tableInfoService,String json,FlowService flowService){
		this.newHtmlPath = newHtmlPath;
		this.oldPdfPath = oldPdfPath;
		this.instanceId = instanceId; 
		this.step = step;
		this.attachmentService = attachmentService;
		this.tableInfoService = tableInfoService;
		this.wfProcess = wfProcess;
		this.trueJson=json;
		this.flowService = flowService;
		
	}
	
	//sendNext下发数据
	public ToPdfThread(String newHtmlPath,AttachmentService attachmentService,
			String instanceId,String oldPdfPath, String trueJson, String step, 
			SendNextProcess sendNextProcess,Integer isExchange, String pdfType,
			TableInfoService tableInfoService, String route_type,
			EmployeeService employeeService, DepartmentService departmentService,
			Employee emp, WfProcess wfProcess, WorkflowBasicFlowService workflowBasicFlowService,
			ZwkjFormService zwkjFormService, String sessionWorkflowid,FlowService flowService,
			ItemService itemService,String firstOverPdf, String  formPageJson, Integer urgency,String self_loop){
		this.newHtmlPath = newHtmlPath;
		this.attachmentService = attachmentService;
		this.instanceId = instanceId;
		this.oldPdfPath = oldPdfPath;
		this.trueJson = trueJson;
		this.step = step;
		this.entity = sendNextProcess;
		this.isExchange = isExchange;
		this.pdfType = pdfType;
		this.tableInfoService = tableInfoService;
		this.route_type = route_type;
		this.employeeService = employeeService;
		this.departmentService = departmentService;
		this.emp = emp;
		this.wfProcess = wfProcess;
		this.workflowBasicFlowService = workflowBasicFlowService;
		this.zwkjFormService = zwkjFormService;
		this.sessionWorkflowid = sessionWorkflowid;
		this.flowService = flowService;
		this.firstOverPdf = firstOverPdf;
		this.itemService =itemService;
		this.formPageJson = formPageJson;
		this.urgency = urgency;
		this.self_loop = self_loop;
	}
	
	
	//end操作数据
	public ToPdfThread(String newHtmlPath,AttachmentService attachmentService,
			String instanceId,String oldPdfPath, String trueJson, String step,
			WfProcess wfProcess,String newPdfPath,TableInfoService tableInfoService,
			FlowService flowService,Integer urgency){
		this.newHtmlPath = newHtmlPath;
		this.attachmentService = attachmentService;
		this.instanceId = instanceId;
		this.oldPdfPath = oldPdfPath;
		this.trueJson = trueJson;
		this.step = step;
		this.wfProcess = wfProcess;
		this.pdfNewPath = newPdfPath;
		this.tableInfoService = tableInfoService;
		this.flowService = flowService;
		this.urgency = urgency;
	}
	
	//暂存操作数据
	public ToPdfThread(String newHtmlPath,AttachmentService attachmentService,
				String instanceId,String oldPdfPath, String trueJson, String step,
				WfProcess wfProcess,String newPdfPath,FlowService flowService,
				String processId,String newProcessId, TableInfoService tableInfoService){
			this.newHtmlPath = newHtmlPath;
			this.attachmentService = attachmentService;
			this.instanceId = instanceId;
			this.oldPdfPath = oldPdfPath;
			this.trueJson = trueJson;
			this.step = step;
			this.wfProcess = wfProcess;
			this.pdfNewPath = newPdfPath;
			this.flowService = flowService;
			this.processId = processId;
			this.newProcessId = newProcessId;
			this.tableInfoService = tableInfoService;
	}
	
	//子流程发文end操作数据
	public ToPdfThread(String newHtmlPath,AttachmentService attachmentService,
				String instanceId,String oldPdfPath, String trueJson, String step,
				WfProcess wfProcess,String mergePath,String newPdfPath,FlowService flowService,String processId,String newProcessId){
			this.newHtmlPath = newHtmlPath;
			this.attachmentService = attachmentService;
			this.instanceId = instanceId;
			this.oldPdfPath = oldPdfPath;
			this.trueJson = trueJson;
			this.step = step;
			this.wfProcess = wfProcess;
			this.mergePath = mergePath;
			this.pdfNewPath = newPdfPath;
			this.flowService = flowService;
			this.processId = processId;
			this.newProcessId = newProcessId;
	}
	
	/**
	 * 用户合并pdf
	 * @param files			需要合并的文件
	 * @param firstpdfpath		//合并后的地址
	 */
	public ToPdfThread(String[] files, String firstpdfpath,String step, Map<String, Object> map, 
			TableInfoService tableInfoService,FlowService flowService){
		this.files = files;
		this.firstpdfpath = firstpdfpath;
		this.step = step;
		this.map = map;
		this.tableInfoService = tableInfoService;
		this.flowService = flowService;
	}
	
	/**
	 * 针对于将意见合并到老的pdf中,
	 * @param pdfNewPath	合并后的pdf
	 * @param pdfOldPath	合并前的pdf
	 * @param trueJson	意见
	 * @param step	步骤：ff
	 */
	public ToPdfThread(String pdfNewPath, String pdfOldPath, String trueJson, String step,FlowService flowService){
		this.pdfNewPath = pdfNewPath;
		this.pdfOldPath = pdfOldPath;
		this.trueJson = trueJson;
		this.step = step;
		this.flowService = flowService;
	}
	
	
	public ToPdfThread(String newHtmlPath, String oldPdfPath, String step,
			SendNextProcess sendNextProcess, TableInfoService tableInfoService,FlowService flowService){
		this.newHtmlPath = newHtmlPath;
		this.oldPdfPath = oldPdfPath;
		this.step = step;
		this.entity = sendNextProcess;
		this.tableInfoService = tableInfoService;
		this.flowService = flowService;
	}
	
	public ToPdfThread(String pdfOldPath, String pdfNewPath, 
			String commentJson, String oldPdfPath, String nextNodePdfPath, String step,FlowService flowService){
		this.pdfOldPath = pdfOldPath;
		this.pdfNewPath = pdfNewPath;
		this.trueJson = commentJson;
		this.oldPdfPath = oldPdfPath;
		this.nextNodePdfPath = nextNodePdfPath;
		this.step = step;
		this.flowService = flowService;
	}
	
	public ToPdfThread(String instanceId, String oldPdfPath, TableInfoService tableInfoService,String step ){
		this.instanceId = instanceId; 
		this.oldPdfPath = oldPdfPath;
		this.tableInfoService = tableInfoService;
		this.step = step;
	}
	
	/**
	 * 
	 * @param instanceId
	 * @param oldPdfPath
	 * @param attachmentService
	 * @param step
	 * @param wfProcess
	 * @param tableInfoService
	 * @param list
	 * @param emp
	 * @param flowService
	 * @param nbUserId
	 * @param withForm :  重点表示: withform=1时,表示需要携带员表单
	 */
	public ToPdfThread(String instanceId, String oldPdfPath, AttachmentService attachmentService, 
			String step,WfProcess wfProcess,TableInfoService tableInfoService,List<String> list,
			Employee emp ,FlowService flowService, String nbUserId, String withForm){
		this.instanceId = instanceId;
		this.oldPdfPath = oldPdfPath;
		this.step = step;
		this.attachmentService = attachmentService;
		this.wfProcess = wfProcess;
		this.tableInfoService = tableInfoService;
		this.emp = emp;
		this.list = list;
		this.flowService = flowService;
		this.nbUserId = nbUserId;
		this.withForm = withForm;
	}
	
	
	public ToPdfThread(String instanceId, String oldPdfPath, AttachmentService attachmentService, 
			String step,WfProcess wfProcess,TableInfoService tableInfoService,List<String> list,
			Employee emp ,FlowService flowService, String nbUserId, String withForm, String commentJson, String lastPdf, String formAttPath){
		this.instanceId = instanceId;
		this.oldPdfPath = oldPdfPath;
		this.step = step;
		this.attachmentService = attachmentService;
		this.wfProcess = wfProcess;
		this.tableInfoService = tableInfoService;
		this.emp = emp;
		this.list = list;
		this.flowService = flowService;
		this.nbUserId = nbUserId;
		this.withForm = withForm;
		this.trueJson = commentJson;
		this.pdfNewPath = lastPdf;
		this.formAttPath = formAttPath;
	}
	
	/**
	 * 初始化：将html转换成pdf, 合并true文件,  更新receive表中的pdfPath, 意见trueJson
	 * @param htmlPath
	 * @param receiveId
	 * @param truePath
	 * @param tableInfoService
	 * @param step
	 */
	public ToPdfThread(String htmlPath, String receiveId, String truePath, 
			TableInfoService tableInfoService ,String step,FlowService flowService){
		this.newHtmlPath = htmlPath;
		this.receiveId = receiveId;
		this.pdfOldPath = truePath;
		this.tableInfoService = tableInfoService;
		this.step = step;
		this.flowService = flowService;
	}
	
	public ToPdfThread(String htmlpath, TableInfoService tableInfoService,String step,String trueJson,List<DoFileReceive> dfrs,FlowService flowService) {
		this.newHtmlPath = htmlpath;
		this.tableInfoService = tableInfoService;
		this.step = step;
		this.trueJson = trueJson;
		this.dfrs = dfrs;
		this.flowService = flowService;
	}
	
	/**
	 * 发送时,部门过多,导致操作时间过长,添加到线程中处理
	 * @param wfProcess	
	 * @param nbUserId	部门ids
	 * @param emp	当前用户
	 * @param pdfpath  pdf的附件地址
	 * @param dyfs	打印份数
	 * @param step	"sendDoc"
	 */	
	public ToPdfThread(WfProcess wfProcess, String nbUserId, Employee emp, 
			String pdfpath, String dyfs,TableInfoService tableInfoService,
			FlowService flowService, Sw sw, List<String> list, String step){
		this.wfProcess = wfProcess;
		this.nbUserId = nbUserId;
		this.emp = emp;
		this.pdfpath = pdfpath;
		this.dyfs = dyfs;
		this.tableInfoService = tableInfoService;
		this.flowService = flowService;
		this.sw = sw;
		this.list = list;
		this.step = step;
	}
	
	
	/**
	 * 并行结合式(发送下一步)
	 * @param vc_title
	 * @param userId
	 * @param employeeGuid
	 * @param commentJson
	 * @param pdfPath
	 * @param oldProcess
	 * @param nextNodeId
	 * @param tableInfoService
	 * @param step
	 */
	public ToPdfThread(String title, String userId,String employeeGuid, 
			String commentJson, String htmlPath, WfProcess oldProcess
			, String nextNodeId, TableInfoService tableInfoService,	
			AttachmentService attachmentService, String step){
		this.title = title;
		this.nbUserId = userId;
		this.employeeGuid = employeeGuid;
		this.trueJson = commentJson;
		this.newHtmlPath = htmlPath;
		this.wfProcess = oldProcess;
		this.nextNodeId = nextNodeId;
		this.tableInfoService = tableInfoService;
		this.attachmentService = attachmentService;
		this.step = step;
	}
	

	public ToPdfThread(String step, WfProcess fOldProcess, TableInfoService tableInfoService, FlowService flowService, AttachmentService attachmentService,int yjHeight,ZwkjFormService zwkjFormService) {
		this.tableInfoService = tableInfoService;
		this.attachmentService = attachmentService;
		this.step = step;
		this.wfProcess = fOldProcess;
		this.flowService = flowService;
		this.yjHeight = yjHeight;
		this.zwkjFormService = zwkjFormService;
	}
	
	
	/**
	 * 联合发文
	 * @param step
	 * @param wfprocess
	 * @param attachmentService
	 * @param tableInfoService
	 * @param emp
	 * @param flowService
	 * @param xtoName
	 */
	public ToPdfThread(String step, WfProcess wfprocess, AttachmentService attachmentService
			,TableInfoService tableInfoService, Employee emp ,FlowService flowService,
			String xtoName, String pdfpath){
		this.step = step;
		this.wfProcess = wfprocess;
		this.attachmentService = attachmentService;
		this.tableInfoService = tableInfoService;
		this.emp = emp;
		this.flowService = flowService;
		this.xtoName = xtoName;
		this.oldPdfPath = pdfpath;
	}
	
	
	public ToPdfThread(String step, String oldPdfPath, String pdfNewPath, String trueJson){
		this.step = step;
		this.oldPdfPath = oldPdfPath;
		this.trueJson = trueJson;
		this.pdfNewPath = pdfNewPath;
	}
	
	@Override
	public void run() {
		if(step.equals("middle")){	//middle中间步骤(sendNext时执行)
			String PDFPath = "";
			String js = "";
			String itemId = "";
			if(entity!=null){
				itemId = entity.getItemId();				//检查事项Id
				WfProcess endProcess = tableInfoService.findEndWfProcessByInstanceId(entity.getInstanceId());
				if(endProcess!=null){
					endProcess.setStepIndex(endProcess.getStepIndex()+1);
					tableInfoService.update(endProcess);
				}
			}
			WfItem item = tableInfoService.findWfItemById(itemId);
			boolean isFlexibleForm = false;
			if(item!=null){
				isFlexibleForm = (item.getIsFlexibleForm()!=null && item.getIsFlexibleForm().equals("1"))?true: false;
			}
			/*if(isFlexibleForm){
				FileOperateUtils util = new FileOperateUtils();
				try {
					PDFPath = util.combineSendAtts(instanceId, attachmentService);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			if(pdfType==null || pdfType.equals("2")){
				if(wfProcess!=null && Utils.isNotNullOrEmpty(wfProcess.getCommentJson())){
					js = wfProcess.getCommentJson();
				}
				if(entity!=null&&Utils.isNotNullOrEmpty(entity.getTrueJson())){
					js = entity.getTrueJson();
				}
				if(Utils.isNotNullOrEmpty(trueJson)){
					js = trueJson;
				}
				//合并附件操作
				if(!isFlexibleForm){
					String[] htmlPath =  newHtmlPath.split(",");
					for(int i=0; htmlPath!=null && i<htmlPath.length; i++){
						String tohtmlpath = htmlPath[i];
						String pdfPath = "";
						if(oldPdfPath!=null && oldPdfPath.length()>0
								&& pdfPath!=null && pdfPath.length()>0){
							mergerPdf(pdfPath,oldPdfPath);
						}
					}
				}
			}
			if(pdfType==null){
				if(wfProcess!=null && tableInfoService!=null){
					String com_json = wfProcess.getCommentJson();
					if(js!=null && (com_json==null || js.length()>com_json.length())){
						wfProcess.setCommentJson(js);
					}
					tableInfoService.update(wfProcess);
				}
				return;
			}
			//插入办件
			try {
				String xtoUserId = entity.getM_userId();
				String xccUserId = entity.getC_userIds();
				String nextNodeId = entity.getNextNodeId();
				String workFlowId = entity.getWorkFlowId();
				String cType = entity.getcType();
				String nodeId = entity.getNodeId();
				String processId = entity.getOldProcessId();
				String title = entity.getTitle();
				String  wfPinstanceId = "";
				WfProcess pre_process = tableInfoService.getProcessById(processId);
				sleep(3000);
				tableInfoService.sendProcess(title,
						entity.getM_userId(), entity.getC_userIds(),
						entity.getUserId(),workFlowId,
						nodeId,entity.getF_proceId(),
						processId,nextNodeId,
						entity.getInstanceId(),entity.getItemId(),
						entity.getFormId(),entity.getOldformId(),
						entity.getPdfPath(),
						entity.getTrueJson(),entity.getIsChildWf(),
						cType,entity.getRelation(),
						entity.getFinstanceId(),entity.getNewInstanceIdForChildWf(),
						entity.getSpecialProcess(),entity.getMiddlePdf(),
						entity.getWcqx(),firstOverPdf, PDFPath,
						formPageJson, urgency,self_loop);
				WfProcess oldProcess = tableInfoService.getProcessById(processId);
				//通知中威通讯录该待办已经被办结了
				RemoteLogin remote = new RemoteLogin();
				//websocket消息推送
				WebSocketUtil webSocket = new WebSocketUtil();
				if(pre_process!=null){
					webSocket.delBadge(oldProcess.getUserUid(), "", "");		//webSocket消息推送 人员-1
				}
				boolean checkUser = RemoteLogin.checkPassed;
				if(checkUser && oldProcess!=null){
					remote.deleteThirdPend(oldProcess.getUserUid(), processId);
				}
				if (isExchange != null && isExchange == 1) {
					if (xtoUserId != null && xtoUserId.length() > 0) {
						saveDoFileReceive(emp,xtoUserId.split(","), 1, wfPinstanceId);
					}
					if (xccUserId != null && xccUserId.length() > 0) {
						saveDoFileReceive(emp,xccUserId.split(","), 0, wfPinstanceId);
					}
				}
				
				// 竞争，更新其他人步骤为OVER
				if ("1".equals(route_type)) {
					tableInfoService.updateOver(workFlowId, instanceId, nodeId);
				}
				
				//根据节点判断,是否将上一步,状态修改为over
				String lastNodeId = oldProcess.getFromNodeid();		
				WfNode node = null;
				if(CommonUtil.stringNotNULL(lastNodeId)){
					node = tableInfoService.getWfNodeById(lastNodeId);
				}
				if(node!=null){
					String  wfn_route_type  = node.getWfn_route_type();
					if(wfn_route_type!=null && wfn_route_type.equals("5")){
						String instanceId = oldProcess.getWfInstanceUid();
						Integer stepIndex = oldProcess.getStepIndex()-1;
						WfProcess lastwf = new WfProcess();
						lastwf.setWfInstanceUid(instanceId);
						lastwf.setStepIndex(stepIndex);
						lastwf.setNodeUid(lastNodeId);
						List<WfProcess> list = tableInfoService.findWfProcessList(lastwf);
						WfProcess wf = null;
						for(int i=0; i<list.size(); i++){
							wf = list.get(i);
							if(wf.getIsOver()!=null && wf.getIsOver().equals("NOT_OVER")){
								wf.setFinshTime(new Date());
								wf.setIsOver("OVER");
								if(wf.getJssj()==null){
									wf.setJssj(new Date());
								}
								tableInfoService.update(wf);
							}
						}
					}
				}
				// 父流程id
				String fInstanceId = "";
				if (wfProcess != null) {
					fInstanceId = wfProcess.getfInstancdUid();
				}
				// 是否当前步骤的其他人都办完
				boolean isOver = tableInfoService.isAllOver(workFlowId, instanceId,nodeId, fInstanceId);
				
				if(CommonUtil.stringNotNULL(self_loop) 
						&& "1".equals(self_loop)){// 自循环
					// 自循环让本步待办显示
					updateNewProcess(instanceId, workFlowId, "", nodeId);
				}
				
				if (isOver) {
					// 让下一步发送的待办显示
					updateNewProcess(instanceId, workFlowId, "", nextNodeId);
				}
				//执行存储过程
				excuteProcedure(entity.getOldformId(), 1, instanceId, entity.getFormId(), nodeId);
				
				sycnPendToChat(oldProcess, nextNodeId, entity);
				
				String autoEndNodeId = SystemParamConfigUtil.getParamValueByParam("autoEndNodeId");
				if(StringUtils.isBlank(autoEndNodeId) || (StringUtils.isNotBlank(autoEndNodeId) && StringUtils.isNotBlank(nextNodeId) && autoEndNodeId.indexOf(nextNodeId) != -1)){
					String[] endNodeIds = autoEndNodeId.split(",");
					if(endNodeIds.length>1){
//						if(endNodeIds[1]!=null&& endNodeIds[1].equals(nextNodeId)){
//							tableInfoService.updateOverByNodeId(workFlowId, instanceId, nextNodeId);
//							tableInfoService.updateEndProcessStep(instanceId,nextNodeId);
//						}
						for(int i=1;i<endNodeIds.length;i++){
							int count = 0;
							if(i==2){
								List<WfProcess> sameStepWfList = tableInfoService.findWfProcessListByIsOver(wfProcess);
								for(int j=0; j<sameStepWfList.size(); j++){
									if(sameStepWfList.get(j).getIsOver().equals("NOT_OVER")){
										count ++;
									}
								}
							}
							System.out.println("----------线程自动办结节点id-----"+endNodeIds[i]+"------,未办理的流程数量-----"+count+"------");
							if(endNodeIds[i]!=null&& (endNodeIds[i].equals(nodeId)||endNodeIds[i].equals(nextNodeId))){
								System.out.println("-----开始执行线程自动办结逻辑------nodeId:"+endNodeIds[i]+"---------");
								tableInfoService.updateOverByNodeId(workFlowId, instanceId, endNodeIds[i]);
								tableInfoService.updateEndProcessStep(instanceId,endNodeIds[i]);
							}
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(step.equals("ljsend")){	//只去异步处理pdf
			String[] htmlPath =  newHtmlPath.split(",");
			for(int i=0; htmlPath!=null && i<htmlPath.length; i++){
				String tohtmlpath = htmlPath[i];
				String pdfPath = "";
				String[] str = MergerPdf(tohtmlpath,trueJson);	//将html转换为pdf,且附件合并pdf
				if(str!=null){
					pdfPath = str[0];
				}
				if(oldPdfPath!=null && oldPdfPath.length()>0
						&& pdfPath!=null && pdfPath.length()>0){
					mergerPdf(pdfPath,oldPdfPath);
				}else{
				}
			}
		}else if(step.equals("end")){
			//表单转换成pdf且与附件合并
			String pdfpath ="";
			String[] str = MergerPdf(newHtmlPath,trueJson,pdfNewPath);
			MergerPdf(newHtmlPath,trueJson,mergePath);
			if(str!=null){
				pdfpath = str[0];
			}
			//合并旧表单
			boolean b = fileExist(oldPdfPath);
			if(b){
				b = fileExist(pdfNewPath);
				String saveMergePath = pdfNewPath.replace(".true", ".pdf");
				if(saveMergePath!=null){
					GenePdfUtil util = GenePdfUtil.getInstance();
					String parPdf = saveMergePath.substring(0, saveMergePath.length()-4)+"toParfj.pdf";
					util.genePdf(saveMergePath, trueJson, parPdf);
					System.out.println("-----end---1---"+parPdf);
				}
				if(b){
					if(oldPdfPath!=null && oldPdfPath.length()>0){
						try {
							sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mergerPdf(pdfNewPath,oldPdfPath);
						mergePath = pdfNewPath.substring(0, pdfNewPath.length() - 5) + "mergeNew" + ".true";
					}
				}
			}
		}else if(step.equals("first")){
			//检查文件是否存在
			boolean b = fileExist();
			if(b){
				MergePdf mp = new MergePdf();
				String pdfs = firstpdfpath.split(",")[0];
				mp.mergePdfFiles(files, pdfs);
			}
			//遍历map
			WfNode wfNode = (WfNode)map.get("wfNode");
			String path = (String)map.get("path");
			WfProcess process = (WfProcess)map.get("process");
			Employee emp = (Employee)map.get("emp");
			String value = (String)map.get("value");
			String mergePath = (String)map.get("mergePath");
			tableInfoService.insertAferEndProcess(wfNode ,path,process,emp,value,mergePath);
			
		}else if(step.equals("ff")){
			boolean b = fileExist(pdfOldPath);
			if(b){
				new PDFToTrue().pdfToTrue(pdfOldPath, trueJson);
			}
		}else if(step.equals("ffnext")){
			//html转pdf
			String pdfNewPath = htmltoPdf();		//转换后的空pdf
			if(oldPdfPath!=null && oldPdfPath.length()>0){
			boolean b = fileExist(oldPdfPath);
			if(b){
				mergerPdf(pdfNewPath,oldPdfPath);
			}
			}else{
			}
			tableInfoService.sendNewProcess(entity.getTitle(),entity.getM_userId(),entity.getUserId(),entity.getInstanceId(),
					entity.getChildWorkflowId(),entity.getNodeId(),entity.getFromNodeId(),entity.getNextNodeId(),entity.getOldProcess(),
					entity.getChildWfFirstNode(),entity.getIsMerge(),entity.getTrueJson(),entity.getPdfPath(),
					entity.getcType(),"",entity.getSw(),entity.getWcqx(),entity.getpStepIndex());
		}else if(step.equals("createPdf")){	//生成pdf
			GenePdfUtil pdfUtil = GenePdfUtil.getInstance();
			//表单合并意见
			pdfNewPath =  pdfOldPath.substring(0,pdfOldPath.length()-4)+"meryj.pdf";
			try {
				pdfUtil.genePdf(pdfOldPath,trueJson, pdfNewPath);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] files2 = {oldPdfPath,pdfNewPath};
			MergePdf mp = new MergePdf();
			mp.mergePdfFiles(files2, nextNodePdfPath);
		}else if(step.equals("htmlToPdf") || step.equals("htmlToPdf2")){
			String[] htmlPath =  newHtmlPath.split(",");
			for(int i=0; htmlPath!=null && i<htmlPath.length; i++){
				String pdfPath = "";
				if(htmlPath.length == 1){
					String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
					String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
				    String 	pdfNewPath1	= pdfRoot+dstPath+ (htmlPath[0].substring
								(htmlPath[0].lastIndexOf("/")+1,htmlPath[0].length()-5)+"merge")+".true";
					String[] str = MergerPdf(htmlPath[0],trueJson,pdfNewPath1);
					if(str!=null){
						pdfPath = str[0];
						if(wfProcess!=null){	
							String  json = "";
							if(str[1].indexOf("\"resources\"")>-1 &&str[1].startsWith("[")){
								json = str[1].substring(1, str[1].length()-1);
							}else{
								json = str[1];
							}
							wfProcess.setCommentJson(json);
						}
					}
					if(oldPdfPath!=null && oldPdfPath.length()>0
							&& pdfPath!=null && pdfPath.length()>0){
						mergerPdf(pdfPath,oldPdfPath);
					}
				}else{
					String tohtmlpath = htmlPath[i];
					String[] str = MergerPdf(tohtmlpath,trueJson);	//将html转换为pdf,且附件合并pdf
					if(str!=null){
						pdfPath = str[0];
						if(wfProcess!=null){	
							String  json = "";
							if((str[1].indexOf("\"resources\"")>-1 || str[1].indexOf("\"resource\"")>-1) &&str[1].startsWith("[")){
								json = str[1].substring(1, str[1].length()-1);
							}else{
								json = str[1];
							}
							if(step.equals("htmlToPdf")){
								wfProcess.setCommentJson(json);
							}
						}
					}
					if(oldPdfPath!=null && oldPdfPath.length()>0
							&& pdfPath!=null && pdfPath.length()>0){
						mergerPdf(pdfPath,oldPdfPath);
					}
				}
			}
			tableInfoService.update(wfProcess);
			WebSocketUtil webSocket = new WebSocketUtil();
			try {
				webSocket.apnsPush(wfProcess.getProcessTitle(), wfProcess.getFromUserId(), "", "", "", wfProcess.getUserUid());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}else if(step.equals("toAndCombToPdf")){
			String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");// 正文附件的后缀
			String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");// 附加附件的后缀
			List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + attSuffixName,null);
			String isHb = SystemParamConfigUtil.getParamValueByParam("isHbFj");
			List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(instanceId + attFjSuffixName,isHb);
			ToPdfUtil toPdfUtil = new ToPdfUtil();
			if(withForm!=null && withForm.equals("1")){//携带表单
			    /*try{
				if(endWfp!=null){
				    String json = endWfp.getCommentJson();
				    String itemId = endWfp.getItemId();
				    String isFlexForm = "";
				    if(CommonUtil.stringNotNULL(itemId)){
					WfItem item = itemService.getItemById(itemId);
					isFlexForm = item.getIsFlexibleForm();
				}
				    //toPdfUtil.toAndCombToPdf(sattList, sattExtList ,oldPdfPath,wfProcess,attachmentService,list,flowService,emp);
				    toPdfUtil.saveAttList(oldPdfPath, trueJson, attachmentService, list, flowService, emp, wfProcess, pdfNewPath);
				    List<DoFileReceive> rec_list = tableInfoService.getDofileFavouriteByFprocessId(wfProcess.getWfProcessUid());
				    if(nbUserId!=null && nbUserId.length()>0){
					String[] ids = nbUserId.split(",");
					int length = ids.length;
					//个数相同时,执行下一步的相应更新操作
					while(rec_list==null || rec_list.size()<length){
					    sleep(1000);
					    logger.debug("待收人员暂未收到,待收信息,稍后！");
					    rec_list = tableInfoService.getDofileFavouriteByFprocessId(wfProcess.getWfProcessUid());	//再次获取
					}
				    }
				    for(DoFileReceive ent: rec_list){
					ent.setTrueJson(trueJson);
					ent.setPdfpath(receivePdf);
					tableInfoService.updateDoFileReceive(ent);
				    }
				}
			    }catch (Exception e){
				e.getMessage();
			    }*/
			    //上传表单pdf
			    toPdfUtil.uploadFormPdf(formAttPath, list, wfProcess.getProcessTitle(), attachmentService, emp);
			    //上传附件
			    toPdfUtil.uploadSealedPdf(sattList, sattExtList, attachmentService, list, emp);
			}else{
			    try {
				//将附件带到收文
				toPdfUtil.uploadSealedPdf(sattList, sattExtList, attachmentService, list, emp);
				List<DoFileReceive> list = tableInfoService.getDofileFavouriteByFprocessId(wfProcess.getWfProcessUid());
				if(nbUserId!=null && nbUserId.length()>0){
				    String[] ids = nbUserId.split(",");
				    int length = ids.length;
				    //个数相同时,执行下一步的相应更新操作
				    while(list==null || list.size()<length){
					sleep(1000);	
					logger.debug("待收人员暂未收到,待收信息,稍后！");
					list = tableInfoService.getDofileFavouriteByFprocessId(wfProcess.getWfProcessUid());
				    }
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			}
		}else if(step.equals("tolhfw")){		//联合发文
			String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");// 正文附件的后缀
			String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");// 附加附件的后缀
			if(wfProcess!=null){
				instanceId = wfProcess.getWfInstanceUid();
			}
			List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + attSuffixName,null);
			String isHb = SystemParamConfigUtil.getParamValueByParam("isHbFj");
			List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(instanceId + attFjSuffixName,isHb);
			ToPdfUtil toPdfUtil = new ToPdfUtil();
			try {
				//获取所有json
				if(wfProcess!=null){
					String pdf = wfProcess.getPdfPath().split(",")[1];
					boolean b = fileExist(pdf);
				}
				String json = toPdfUtil.toAndCombToPdf(sattList, sattExtList ,oldPdfPath,wfProcess,attachmentService,list,flowService,emp);
				if(wfProcess!=null){
					Integer stepIndex = wfProcess.getStepIndex()+1;
					String userId = wfProcess.getUserUid();
					if(xtoName!=null && !xtoName.equals("")){	
						String[] users = xtoName.split(",");
						WfProcess wfp = null;
						for(int i=0; i<users.length;i++){
							wfp = wfProcess;
							wfp.setWfProcessUid(UuidGenerator.generate36UUID());
							wfp.setIsOver("NOT_OVER");
							wfp.setFinshTime(null);
							wfp.setApplyTime(new Date());
							wfp.setUserUid(users[i]);
							wfp.setFromUserId(userId);
							wfp.setCommentJson(json);
							wfp.setStepIndex(stepIndex);
							wfp.setJssj(null);
							wfp.setPdfPath(oldPdfPath+","+oldPdfPath);
							tableInfoService.save(wfp);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(step.equals("createRecTrue")){	//将html转换成pdf与true文件合并, 生成新的true文件以及json保存到receive表中
			//html转换成pdf, pdf与true文件合并
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			String wfPath = "";
			String pdfPath = pdfRoot+dstPath+ (newHtmlPath.substring
					(newHtmlPath.lastIndexOf("/"),newHtmlPath.length()-5)+"merge")+".pdf";
			HtmlToPdf htp = new HtmlToPdf();
			htp.htmlToPdf(newHtmlPath, pdfPath);
			
			MergePdf mergePdf = new MergePdf(); 
			String[] files = null;
			String trueJson = "";
			String truePath = "";
			if(pdfOldPath!=null && !pdfOldPath.equals("")){
				//truePath = pdfPath.replace(".pdf",".true");
				Calendar calendar = Calendar.getInstance();
				String pdf = pdfRoot+dstPath+ String.valueOf(calendar.getTimeInMillis()) + ".pdf";
				files = new String[]{pdfPath, pdfOldPath};
				trueJson = mergePdf.mergePdfFiles(files, pdf);
				truePath = new PDFToTrue().pdfToTrue(pdf, trueJson);
			}else{
				truePath = new PDFToTrue().pdfToTrue(pdfPath, trueJson);
			}
			DoFileReceive doFileReceive = tableInfoService.getDoFileReceiveById(receiveId);
			doFileReceive.setPdfpath(truePath);
			doFileReceive.setTrueJson(trueJson);
			tableInfoService.updateDoFileReceive(doFileReceive);
			
		}else if(step.equals("createTrue")){	//将html转换成pdf与true文件合并, 生成新的true文件以及json保存到receive表中
			//html转换成pdf, pdf与true文件合并
			String pdfPath = newHtmlPath;
			String truePath = "";
			truePath = new PDFToTrue().pdfToTrue(pdfPath, trueJson);
			//插入 附件流
			HashMap<String, String> keyValueSet = new HashMap<String, String>();
			keyValueSet.put("PDFFLOW",truePath );
			if(dfrs != null && dfrs.size() > 0){
				for(int i = 0 ; i < dfrs.size(); i++ ){
					DoFileReceive doFileReceive = dfrs.get(i);
					doFileReceive.setPdfpath(truePath);
					doFileReceive.setTrueJson(trueJson);
					tableInfoService.updateDoFileReceive(doFileReceive);
				}
			}
		}else if(step.equals("sendDoc")){
			String[] empId = nbUserId.split(",");
			String[] fs = dyfs.split(",");
			if(empId != null && empId.length > 0){
				Date nowTime = new Date();
				for(int i=0; i<empId.length; i++){
					String uId = empId[i];
					String dfs = fs[i];
					String newInstanceId = list.get(i);
					//点击发送按钮发送,插待办并入待收表
					tableInfoService.addNewProcessOfSend(wfProcess,uId,sw, pdfpath,emp,newInstanceId, dfs,nowTime, "send","");
					}
			}
		}else if(step.equals("nextSend")){		//发送到下一步
			String[] htmlPath =  newHtmlPath.split(",");	//html路径
			String truePath = "";
			instanceId = wfProcess.getWfInstanceUid();
			for(int i=0; htmlPath!=null && i<htmlPath.length; i++){
				String tohtmlpath = htmlPath[i];
				String pdfPath = "";
				String[] str = MergerPdf(tohtmlpath,trueJson);	//将html转换为pdf,且附件合并pdf
				if(str!=null && str.length >0){
					pdfPath = str[0];
				}
				truePath += pdfPath;
				if(i!=htmlPath.length-1){
					truePath += ",";
				}
			}
			tableInfoService.sendNextProcess(title, nbUserId, employeeGuid,trueJson, truePath, wfProcess, nextNodeId);
		}else if(step.equals("start")){
			//html转pdf
			String pdfNewPath = htmltoPdf();		//转换后的空pdf
			if(oldPdfPath!=null && oldPdfPath.length()>0){
			boolean b = fileExist(oldPdfPath);
			if(b){
				mergerPdf(pdfNewPath,oldPdfPath);
			}
			}else{
			}
			WfProcess newProcess =	tableInfoService.sendNewProcess(entity.getTitle(),entity.getM_userId(),entity.getUserId(),entity.getInstanceId(),
					entity.getChildWorkflowId(),entity.getNodeId(),entity.getFromNodeId(),entity.getNextNodeId(),entity.getOldProcess(),
					entity.getChildWfFirstNode(),entity.getIsMerge(),entity.getTrueJson(),entity.getPdfPath(),
					entity.getcType(),"",entity.getSw(),entity.getWcqx(),entity.getpStepIndex());
			
		}else if(step.equals("jbYJ")){
			// 先获取位置
			String formId = wfProcess.getFormId();
			ZwkjForm form = zwkjFormService.getOneFormById(formId);
			String locations = form.getElementLocationJson();
			JSONArray jsonArray = JSONArray.fromObject(locations);
			String yjId= SystemParamConfigUtil.getParamValueByParam("yjclkId");
			String newPdfPath = "";
			// 遍历获取高度
			int oldHeight = 0;
			JSONObject jObject = null;
			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject jo = jsonArray.getJSONObject(j);
				if(jo.isNullObject()){
					continue;
				}
				String name = jo.get("name").toString();
				if(yjId.equals(name)){
					oldHeight = jo.getInt("height");
					jObject = jo;
					break;
				}
			}
			// 暂时有问题
			System.out.println(wfProcess.getCommentJson());
			JSONObject newJO = regeneJSON(wfProcess.getCommentJson(),jObject);
			if(newJO != null && newJO.containsKey("Data")){
				Object object = newJO.containsKey("Error")?newJO.get("Error"):"";
				if(object== JSONNull.getInstance() || object =="null" || object ==null ){
					String ss = newJO.getJSONObject("Data").getString("JSON");
					wfProcess.setCommentJson(ss);
					yjHeight = newJO.getJSONObject("Data").getInt("MaxY")-jObject.getInt("startY")+20;
					if(newJO.getJSONObject("Data").getInt("MaxY")>1362){
						// 处理json
						String json = SplitJSONPage.splitPage(ss,1362);
						wfProcess.setCommentJson(json);
					}
				}
			}
			
			// 如果意见框的高度小于 实际意见的高度 ,重新生成true文件
			if(oldHeight < yjHeight){
				HtmlHander htmlHander = new HtmlHander();
				String[] pdfPath = wfProcess.getPdfPath().split(",");
				String pendHtml = "";
				if(pdfPath[0].startsWith(PathUtil.getWebRoot())){
					// 处理form 的 html
					pendHtml = PathUtil.getWebRoot()+"form/html/"+form.getForm_htmlfilename();
					pendHtml = htmlHander.getPdfPath(pendHtml);
				}else{
					String oldpdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_process")+"/pdf/";	
					if(pdfPath[0].startsWith(oldpdfRoot)){
						pendHtml = SystemParamConfigUtil.getParamValueByParam("workflow_process")+"/html/"+pdfPath[0].substring(pdfPath[0].lastIndexOf("/")+1).substring(0,13)+".html";
					}else{
						String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
						String subMu= pdfPath[0].substring((pdfRoot+"pdf/").length(),pdfPath[0].lastIndexOf("/")+1);
						pendHtml = pdfRoot+"html/"+subMu+pdfPath[0].substring(pdfPath[0].lastIndexOf("/")+1).substring(0,13)+".html";
					}
										// replace 高度
				}
				instanceId = wfProcess.getWfInstanceUid();
				String newPendHtml = htmlHander.replaceHtml(pendHtml, yjId, yjHeight+100);
				String[] str = MergerPdf(newPendHtml,wfProcess.getCommentJson());
				newPdfPath = str[0];
				if(!pdfPath[0].equals(pdfPath[1])){
					String overHtml ="";
					if(pdfPath[1].startsWith(PathUtil.getWebRoot())){
						// 处理form 的 html
						overHtml = PathUtil.getWebRoot()+"form/html/"+form.getForm_htmlfilename();
					}else{
						String oldpdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_process")+"/pdf/";	
						String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
						if(pdfPath[1].startsWith(oldpdfRoot)){
							overHtml = SystemParamConfigUtil.getParamValueByParam("workflow_process")+"/html/"+pdfPath[1].substring(pdfPath[1].lastIndexOf("/")+1).substring(0,13)+".html";
						}else{
							String subMu= pdfPath[1].substring((pdfRoot+"pdf/").length(),pdfPath[1].lastIndexOf("/")+1);
							overHtml = pdfRoot+"html/"+subMu+pdfPath[1].substring(pdfPath[1].lastIndexOf("/")+1).substring(0,13)+".html";
						}
					}
					// replace 高度
					String newOverHtml = htmlHander.replaceHtml(overHtml, yjId, yjHeight);
					str = MergerPdf(newOverHtml,wfProcess.getCommentJson());
					newPdfPath = newPdfPath+","+str[0];
				}else{
					newPdfPath +=","+str[0]; 
				}
				wfProcess.setTempHtmlPath(newPendHtml);
				wfProcess.setPdfPath(newPdfPath);
				
			}
			tableInfoService.update(wfProcess);
		}else if(step.equals("htmlToTrue")){
			String[] htmlPath =  newHtmlPath.split(",");
			for(int i=0; htmlPath!=null && i<htmlPath.length; i++){
				String pdfPath = "";
				if(htmlPath.length == 1){
					String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
					String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
				    String 	pdfNewPath1	= pdfRoot+dstPath+ (htmlPath[0].substring
								(htmlPath[0].lastIndexOf("/")+1,htmlPath[0].length()-5)+"merge")+".true";
					String[] str = MergerPdf(htmlPath[0],trueJson,pdfNewPath1);
					if(str!=null){
						pdfPath = str[0];
						if(wfProcess!=null){	
							String  json = "";
							if(str[1].indexOf("\"resources\"")>-1 &&str[1].startsWith("[")){
								json = str[1].substring(1, str[1].length()-1);
							}else{
								json = str[1];
							}
							wfProcess.setCommentJson(json);
						}
					}
					if(oldPdfPath!=null && oldPdfPath.length()>0
							&& pdfPath!=null && pdfPath.length()>0){
						mergerPdf(pdfPath,oldPdfPath);
					}
				}
			
			}
		}else if(step.equals("automatic")){
			WfProcess newProcess =	tableInfoService.sendNewProcess(entity.getTitle(),entity.getM_userId(),entity.getUserId(),entity.getInstanceId(),
					entity.getChildWorkflowId(),entity.getNodeId(),entity.getFromNodeId(),entity.getNextNodeId(),entity.getOldProcess(),
					entity.getChildWfFirstNode(),entity.getIsMerge(),entity.getTrueJson(),entity.getPdfPath(),
					entity.getcType(),entity.getWfc_ctype(),entity.getSw(),entity.getWcqx(),entity.getpStepIndex());
		}else if(step.equals("createFile")){
			GenePdfUtil util = GenePdfUtil.getInstance();
			boolean  b = fileExist(oldPdfPath);
			//true转换为pdf
			String[] args = new TrueToPdf().trueToPdf(oldPdfPath);
			String pdf_path = args[0];
			File file = new File(pdf_path);
			System.out.println("file.exists()="+file.exists());
			if(file.exists()){
				
			}else{
				String path = oldPdfPath.replace("/true/", "/pdf/");
				FileUploadUtils.copy(new File(oldPdfPath), new File(path));		//文件迁移
				args = new TrueToPdf().trueTwoPdf(path);
				pdf_path = args[0];
			}
			System.out.println("pdf_path=="+pdf_path);
			if(trueJson==null){
				trueJson = args[1];
			}
			boolean  b2 = fileExist(pdf_path);
			if(b2){
				util.genePdf(pdf_path, trueJson, pdfNewPath);
			}
		}else if(step.equals("toSend")){
			 boolean b = fileExist(oldPdfPath);
			 if(b){
				 try {
					sleep(5000);		//睡5S
					List<WfProcess>  list = tableInfoService.getProcessList(instanceId);
					if(list!=null && list.size()>0){
						WfProcess wfp = list.get(0);
						String path = wfp.getPdfPath().split(",")[0];
						String json = mergerPdf(path,oldPdfPath);
						path = path.substring(0 , path.length()-5)+"mergeNew.true";
						wfp.setPdfPath(path+","+path);
						tableInfoService.update(wfp);
						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
		}
	}
	
	
	
	private String[] MergerPdf(String newHtmlPath, String trueJson,
			String pdfNewPath) {
		String[] str = new String[2];
		try{
			String pdfFormPath = pdfNewPath.replace(".true", ".pdf");
			HtmlToPdf htp = new HtmlToPdf();
			htp.htmlToPdf(newHtmlPath, pdfFormPath);
			pdfFormPath = new PDFToTrue().pdfToTrue(pdfFormPath, trueJson);
			str[0] = pdfFormPath;
			str[1] = trueJson;
//			str = attachmentService.mergerAttToPdf(pdfFormPath, trueJson, instanceId, pdfNewPath, 0);
			return str;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String  htmltoPdf(){
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String pdfFormPath = pdfRoot+ (newHtmlPath.substring(newHtmlPath.lastIndexOf("/"),
				newHtmlPath.length()-5)+"merge")+".pdf";
		HtmlToPdf htp = new HtmlToPdf();
		htp.htmlToPdf(newHtmlPath, pdfFormPath);
		return pdfFormPath;
	}
	
	/**
	 * 截取掉最后一个字符串
	 * @return
	 */
	public String removeLastComma(String str){
		if(str==null || str.equals("")){
			return "";
		}else{
			return str.substring(0, str.length() - 1);
		}
	}
	
	/**
	 * 将html转换成pdf, 且将附件attzw,fj中doc与ceb文件转换成pdf且合并
	 * @param tohtmlpath
	 * @return
	 */
	public String[] MergerPdf(String tohtmlpath,String jsons){
		String[] str = new String[2];
		try{
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			//pdf地址
			String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			String pdfFormPath =pdfRoot+dstPath+(tohtmlpath.substring(tohtmlpath.lastIndexOf("/"),tohtmlpath.length()-5))+".pdf";
			// html转成pdf
			HtmlToPdf htp = new HtmlToPdf();
			htp.htmlToPdf(tohtmlpath, pdfFormPath);
			pdfFormPath = new PDFToTrue().pdfToTrue(pdfFormPath, jsons);
			// 获取正文附件地址 doc,ceb
			String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");// 正文附件的后缀
			String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");// 附加附件的后缀
			List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + attSuffixName,null);
			String isHb = SystemParamConfigUtil.getParamValueByParam("isHbFj");
			String docguid = instanceId;
			if(null != wfProcess){
				docguid = wfProcess.getAllInstanceid();
			}
			List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(docguid + attFjSuffixName,isHb);
			// 合并后的pdf地址
			String saveMergePath = pdfFormPath;
			//合并的文件集合
			String fileStrs = pdfFormPath + ","; 
			String lastMergeFilePath = "";//续办流程将原办件表单合成到最后
			String fileTyle = ""; //文件类型
			ToPdfUtil pdfUtil = new ToPdfUtil();
			if (sattList.size() != 0 && !("").equals(sattList)) {
				for (SendAttachments sat : sattList) {
					FileUtils.byteArrayToFile(sat,attachmentService);
					//判断当前附件是否有重名
					boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
					
					fileTyle = sat.getFiletype().toLowerCase();	//小写
					if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
							String fp = sat.getTopdfpath();
							geneFj(sat.getTopdfpath(),sat.getLocalation(),fileTyle, instanceId, sat.getId());
							//fileStrs += fp + ",";
						}else{	//word转换为pdf,并且update数据
							String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, instanceId, sat.getId()); 
							//fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentService.updateSendAtt(sat);
						}
					} else if (("ceb").equals(sat.getFiletype()) && isSatt_ceb) {
						CebToPdf cp = new CebToPdf();
						// 文件路径
						cp.cebToPdf(pdfRoot + sat.getLocalation());
						//fileStrs += pdfRoot+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
					}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							//fileStrs += filePathOfSys+sat.getLocalation() + ",";
						}else{
							//fileStrs += pdfRoot+sat.getLocalation() + ",";
						}
					}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
							geneFj(sat.getTopdfpath(),sat.getLocalation(),fileTyle, instanceId, sat.getId());
							//fileStrs += sat.getTopdfpath()+ ",";
						}else{
							String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle, instanceId, sat.getId());
							//fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentService.updateSendAtt(sat);
						}
					}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
							fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
							|| fileTyle.equalsIgnoreCase("tif")){
						//fileStrs+= pdfUtil.imgToPdf(sat.getLocalation(), fileTyle)+ ",";
					}else if(("true").equals(fileTyle)){
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							lastMergeFilePath +=filePathOfSys+sat.getLocalation() + ",";
						}else{
							lastMergeFilePath += pdfRoot+sat.getLocalation() + ",";
						}
					}
				}
			}
			
			if (sattExtList.size() != 0 && !("").equals(sattExtList)) {
				for (SendAttachments satExt : sattExtList) {
					//附件中存在同名ceb则不合入ceb
					boolean isSattExt_ceb = pdfUtil.listIsHaveSameDocName(sattExtList,satExt);
					
					//filefrom=1: 附件是由子流程返回
					fileTyle = satExt.getFiletype().toLowerCase();
					if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
						if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){
							String fp = satExt.getTopdfpath();
							geneFj(satExt.getTopdfpath(),satExt.getLocalation(),fileTyle, instanceId, satExt.getId());
							//直接获取
							//fileStrs += fp + ",";
						}else{	//word转换为pdf,并且update数据
							String path = pdfUtil.docToPdf(satExt.getLocalation(),fileTyle, instanceId, satExt.getId()); 
							//fileStrs += path+ ",";
							satExt.setTopdfpath(path);
							attachmentService.updateSendAtt(satExt);
						}
					}else if (("ceb").equals(satExt.getFiletype()) && isSattExt_ceb) {
						CebToPdf cp = new CebToPdf();
						// 文件路径
						cp.cebToPdf(pdfRoot + satExt.getLocalation());
						//fileStrs += pdfRoot+ satExt.getLocalation().substring(0,satExt.getLocalation().length() - 3) + "pdf,";
					}else if (("pdf").equalsIgnoreCase(satExt.getFiletype())){
						if(new File(filePathOfSys + satExt.getLocalation()).exists()){
							//fileStrs += filePathOfSys+satExt.getLocalation() + ",";
						}else{
							//fileStrs += pdfRoot+satExt.getLocalation() + ",";
						}
					}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
						if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){	
							geneFj(satExt.getTopdfpath(),satExt.getTopdfpath(),fileTyle, instanceId, satExt.getId());
							//fileStrs += satExt.getTopdfpath()+ ",";
						}else{
							String path = pdfUtil.xlsToPdf(satExt.getLocalation(), fileTyle,instanceId, satExt.getId());
							//fileStrs += path+ ",";
							satExt.setTopdfpath(path);
							attachmentService.updateSendAtt(satExt);
						}
					}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
							fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
							|| fileTyle.equalsIgnoreCase("tif")){
						//fileStrs+= pdfUtil.imgToPdf(satExt.getLocalation(), fileTyle)+ ",";
					}else if (("true").equals(satExt.getFiletype())){
						if(new File(filePathOfSys + satExt.getLocalation()).exists()){
							lastMergeFilePath += filePathOfSys+satExt.getLocalation() + ",";
						}else{
							lastMergeFilePath += pdfRoot+satExt.getLocalation() + ",";
						}
					}
				}
			}
			
			// 合并正文附件的pdf和表单的pdf
			MergePdf mp = new MergePdf();
			//fileStrs = fileStrs + lastMergeFilePath;
			//fileStrs = removeLastComma(fileStrs);
			//String fileStrs = pdfFormPath + "," + wordPath + "," + cebPath+","+pdfPath+","+xlsPath;
			String[] files = null;
			if (!("").equals(fileStrs) && fileStrs.length() > 0) {
				files = new String[fileStrs.split(",").length];
				for (int i = 0; i < fileStrs.split(",").length; i++) {
					files[i] = fileStrs.split(",")[i];
				}
				saveMergePath = pdfRoot+dstPath+(tohtmlpath.substring(tohtmlpath.lastIndexOf("/"),tohtmlpath.length()-5)+"merge")+".pdf";
				String json=mp.mergePdfFiles(files, saveMergePath);
				str[1] =json;
			}
			saveMergePath = new PDFToTrue().pdfToTrue(saveMergePath, str[1]);
			str[0]=saveMergePath;
			if(new File(pdfFormPath).exists()){
				new File(pdfFormPath).delete();
			}
			return str;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 将html转换成pdf, 且将附件attzw,fj中doc与ceb文件转换成pdf且合并
	 * @param tohtmlpath
	 * @return
	 */
	public String[] MergerPdf(String tohtmlpath){
		String jsons="";
		String[] str = new String[2];
		try{
			// pdf地址--得到的地址会在前面加个"/",不懂,有待检查
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录

			//pdf地址
			String pdfFormPath = pdfRoot+dstPath+(tohtmlpath.substring(tohtmlpath.lastIndexOf("/"),tohtmlpath.length()-5))+".pdf";

			// html转成pdf
			HtmlToPdf htp = new HtmlToPdf();
			htp.htmlToPdf(tohtmlpath, pdfFormPath);
			pdfFormPath = new PDFToTrue().pdfToTrue(pdfFormPath, jsons);
			// 获取正文附件地址 doc,ceb
			String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");// 正文附件的后缀
			String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");// 附加附件的后缀
			List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + attSuffixName,null);
			String isHb = SystemParamConfigUtil.getParamValueByParam("isHbFj");// 正文附件的后缀;
			List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(instanceId + attFjSuffixName,isHb);
			
			// 合并后的pdf地址
			String saveMergePath = pdfFormPath;
			//合并的文件集合
			String fileStrs = pdfFormPath + ","; 
			String fileTyle = ""; //文件类型
			ToPdfUtil pdfUtil = new ToPdfUtil();
			if (sattList.size() != 0 && !("").equals(sattList)) {
				for (SendAttachments sat : sattList) {
					FileUtils.byteArrayToFile(sat,attachmentService);
					//正文中存在同名ceb则不合入ceb
					boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
					
					fileTyle = sat.getFiletype().toLowerCase();	//小写
					if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
							String fp = sat.getTopdfpath();
							geneFj(sat.getTopdfpath(),sat.getTopdfpath(),fileTyle, instanceId, sat.getId());
							//直接获取
							fileStrs += fp + ",";
						}else{	//word转换为pdf,并且update数据
							String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, instanceId, sat.getId()); 
							fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentService.updateSendAtt(sat);
						}
					}else if (("ceb").equals(sat.getFiletype()) && isSatt_ceb) {
						CebToPdf cp = new CebToPdf();
						// 文件路径
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							fileStrs += filePathOfSys+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
						}else{
							cp.cebToPdf(pdfRoot+ sat.getLocalation());
							fileStrs += pdfRoot+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
						}
					}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							fileStrs +=  filePathOfSys+sat.getLocalation() + ",";
						}else{
							fileStrs += pdfRoot+sat.getLocalation() + ",";
						}
					}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
							geneFj(sat.getTopdfpath(),sat.getLocalation(),fileTyle, instanceId, sat.getId());
							fileStrs += sat.getTopdfpath()+ ",";
						}else{
							String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle, instanceId, sat.getId());
							fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentService.updateSendAtt(sat);
						}
					}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
							fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
							|| fileTyle.equalsIgnoreCase("tif")){
						fileStrs+= pdfUtil.imgToPdf(sat.getLocalation(), fileTyle)+ ",";
					}else if(("true").equals(fileTyle)){
						String prePath = "";
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							prePath = filePathOfSys;
							fileStrs +=filePathOfSys+sat.getLocalation() + ",";
						}else{
							prePath = pdfRoot;
							fileStrs += pdfRoot+sat.getLocalation() + ",";
						}
						String[] st = new TrueToPdf().trueToPdf(prePath+sat.getLocalation());
						if(st!=null){
							String js = st[1];
							//将json改变成脱密的
							if(Utils.isNotNullOrEmpty(js)){
								JSONArray jsonArray = JSONArray.fromObject(js);
								for(int j=0;j<jsonArray.size();j++){
									JSONObject jsonObject =(JSONObject) jsonArray.get(j);
									Object obj= jsonObject.get("stamps");
									JSONArray jsArray =null;
									if(obj!=null){
										jsArray=(JSONArray) obj;
									}
									for(int z=0;z<jsArray.size();z++){
										JSONObject jsObject =jsArray.getJSONObject(z);
										jsObject.put("stamp_color", 0);
									}
									
								}
								js=jsonArray.toString();
							}
							new PDFToTrue().pdfToTrue(st[0], js,prePath+sat.getLocalation());
						}
					}
				}
			}
			
			if (sattExtList.size() != 0 && !("").equals(sattExtList)) {
				for (SendAttachments satExt : sattExtList) {
					//附件中存在同名ceb则不合入ceb
					boolean isSattExt_ceb = pdfUtil.listIsHaveSameDocName(sattExtList,satExt);
					
					//filefrom=1: 附件是由子流程返回
					Integer filefrom = satExt.getFilefrom();
					if(filefrom!=null && filefrom ==1){
						continue;
					}
					fileTyle = satExt.getFiletype().toLowerCase();
					if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
						if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){
							String fp = satExt.getTopdfpath();
							geneFj(satExt.getTopdfpath(),satExt.getLocalation(),fileTyle, instanceId, satExt.getId());
							//直接获取
							fileStrs += fp + ",";
						}else{	//word转换为pdf,并且update数据
							String path = pdfUtil.docToPdf(satExt.getLocalation(),fileTyle, instanceId, satExt.getId()); 
							fileStrs += path+ ",";
							satExt.setTopdfpath(path);
							attachmentService.updateSendAtt(satExt);
						}
					}else if (("ceb").equals(satExt.getFiletype()) && isSattExt_ceb) {
						CebToPdf cp = new CebToPdf();
						// 文件路径
						if(new File(filePathOfSys + satExt.getLocalation()).exists()){
							cp.cebToPdf(filePathOfSys + satExt.getLocalation());
							fileStrs += filePathOfSys+ satExt.getLocalation().substring(0,satExt.getLocalation().length() - 3) + "pdf,";
						}else{
							cp.cebToPdf(pdfRoot+ satExt.getLocalation());
							fileStrs += pdfRoot+ satExt.getLocalation().substring(0,satExt.getLocalation().length() - 3) + "pdf,";
						}
					}else if (("pdf").equalsIgnoreCase(satExt.getFiletype())){
						if(new File(filePathOfSys + satExt.getLocalation()).exists()){
							fileStrs += filePathOfSys+satExt.getLocalation() + ",";
						}else{
							fileStrs += pdfRoot+satExt.getLocalation() + ",";
						}
					}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
						if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){	
							geneFj(satExt.getTopdfpath(),satExt.getLocalation(),fileTyle,instanceId, satExt.getId());
							fileStrs += satExt.getTopdfpath()+ ",";
						}else{
							String path = pdfUtil.xlsToPdf(satExt.getLocalation(), fileTyle, instanceId, satExt.getId());
							fileStrs += path+ ",";
							satExt.setTopdfpath(path);
							attachmentService.updateSendAtt(satExt);
						}
					}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
							fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
							|| fileTyle.equalsIgnoreCase("tif")){
						fileStrs+= pdfUtil.imgToPdf(satExt.getLocalation(), fileTyle)+ ",";
					}else if (("true").equals(satExt.getFiletype())){
						if(new File(filePathOfSys + satExt.getLocalation()).exists()){
							fileStrs +=filePathOfSys+satExt.getLocalation() + ",";
						}else{
							fileStrs += pdfRoot+satExt.getLocalation() + ",";
						}
					}
				}
			}
			
			// 合并正文附件的pdf和表单的pdf
			MergePdf mp = new MergePdf();
			fileStrs = removeLastComma(fileStrs);
			//String fileStrs = pdfFormPath + "," + wordPath + "," + cebPath+","+pdfPath+","+xlsPath;
			String[] files = null;
			if (!("").equals(fileStrs) && fileStrs.length() > 0) {
				files = new String[fileStrs.split(",").length];
				for (int i = 0; i < fileStrs.split(",").length; i++) {
					files[i] = fileStrs.split(",")[i];
				}
				saveMergePath = pdfRoot+dstPath+ (tohtmlpath.substring(tohtmlpath.lastIndexOf("/"),
						tohtmlpath.length()-5)+"merge")+".pdf";
				String json=mp.mergePdfFiles(files, saveMergePath);
				str[1] =json;
			}
			saveMergePath = new PDFToTrue().pdfToTrue(saveMergePath, str[1]);
			str[0]=saveMergePath;
			if(new File(pdfFormPath).exists()){
				new File(pdfFormPath).delete();
			}
			return str;
		}catch (Exception e) {
			return null;
		}
	}
	
	
	
	
	/**
	 * 将几个pdf进行合并
	 * @param pdfPath
	 * @param oldpdfPath
	 */
	private String mergerPdf(String pdfPath, String oldpdfPath){
		MergePdf mp = new MergePdf();
		String mergePath = "";
		String fileStrs = pdfPath + "," + oldpdfPath;
		String[] files = null;
		String json = null;
		if(oldpdfPath!=null && !oldpdfPath.equals("")){
			if (fileStrs!=null && fileStrs.length() > 0) {
				files = new String[fileStrs.split(",").length];
				for (int i = 0; i < fileStrs.split(",").length; i++) {
					files[i] = fileStrs.split(",")[i];
				}
				if(pdfPath.split("\\.")[1].length()== 3 ){
					mergePath = pdfPath.substring(0, pdfPath.length() - 4) + "mergeNew" + ".pdf";
				}else{
					mergePath = pdfPath.substring(0, pdfPath.length() - 5) + "mergeNew" + ".pdf";
				}
				
				json =mp.mergePdfFiles(files, mergePath);
				new PDFToTrue().pdfToTrue(mergePath, json);
			}
		}
		return json;
	}
	
	
	/**
	 * pdf转换成image图片
	 * @param pdfPath
	 */
	public void PDFToPNGImage(String pdfPath){/*
		System.out.println("pdf转换生成png");
		if(pdfPath != null && !pdfPath.equals("")){
			//String pdfDir = pdfPath.substring(0,pdfPath.lastIndexOf("/")+1);
			String pdfDir = pdfPath.substring(0,pdfPath.lastIndexOf("."))+"/";
			// 创建目录
			try
			{
				if(!(new File(pdfDir).isDirectory()))
				{
					new File(pdfDir).mkdir();
				}else{
					// 清空 文件夹
					String[] files =	new File(pdfDir).list();
					if(files != null){
						for(int i = 0; i <files.length ; i++){
							new File(pdfDir+files[i]).delete();
						}
					}
				}
			}
			catch(SecurityException e)
			{
			        e.printStackTrace();
			}
			// 设置 pdf 页数
			//int imageCount  =1;
			ExecDll exceDll = new ExecDll();
			try {
				exceDll.PDFToPNGImage(pdfPath, pdfDir);
			} catch (NativeException e) {
					e.printStackTrace();
			} catch (IllegalAccessException e) {
					e.printStackTrace();
			}
		}*/
	}
	
	/**
	 * 预先校验需要合并的pdfs是否都存在
	 * @return
	 */
	public boolean fileExist(){
		for(int i=0; files!=null && i<files.length; i++){
			boolean flag = false;
			File file = new File(files[i]);
			while(!flag){
				flag = file.exists();	//true为存在
				if(!flag){
					try {
						Thread.sleep(3000);
						//System.out.println("合并的pdf不存在,稍后");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	/**
	 * 预先校验pdf是否存在
	 * @return
	 */
	public boolean fileExist(String pdfpath){
		boolean flag = false;
		File file = new File(pdfpath);
		while(!flag){
			flag = (file.exists() && file.length()>0);	//true为存在
			if(!flag){
				try {
					Thread.sleep(3000);
					//System.out.println("合并的pdf不存在,稍后");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	// ========================以下为操作数据库的数据(类似于tableAction中的方法)=============================//
	/**
	 * 保存待回收列表
	 * 
	 * @param ids
	 *            id的一维数组
	 * @param type
	 *            类型,主送1, 0,抄送
	 * @param process
	 *            过程信息
	 */
	public void saveDoFileReceive(Employee emp , String[] ids, Integer type,
			String fInstancdUid) {
		DoFileReceive doFileReceive = null; // 待收办件
		for (int i = 0; ids != null && i < ids.length; i++) {
			Employee employee = employeeService.findEmployeeById(ids[i]);
			Department department = departmentService.findDepartmentById(employee.getDepartmentGuid());
			Integer exchange = 0;
			if (department != null) {
				exchange = department.getIsExchange();
			}
			if (exchange != null && exchange == 1) { // 需要通过公文处理平台同步数据:根据机构判断
				doFileReceive = new DoFileReceive();
				doFileReceive.setType(type); // 主送1; 抄送
				doFileReceive.setStatus(0); // 待接收
				doFileReceive.setApplyDate(new Date()); // 申请时间
				doFileReceive.setToUserId(employee.getEmployeeGuid()); // 接收人员
				doFileReceive.setFormUserId(emp.getEmployeeGuid()); // 发送人员
				// 获取processid, instanceid, pinstanceid
				if (fInstancdUid != null && !fInstancdUid.equals("")) {
					// 仅针对子流程
					WfProcess pwfProcess = tableInfoService.getWfProcessByColoum(fInstancdUid,
									employee.getEmployeeGuid()); // 获取该接收人员的过程对应信息
					/*List<WfProcess>  list = tableInfoService.getProcessList(fInstancdUid);
					String fprocessid = "";
					if(list!=null && list.size()>0){
						fprocessid = list.get(0).getWfProcessUid();
					}*/
					if (pwfProcess != null) {
						doFileReceive.setProcessId(pwfProcess.getWfProcessUid()); // 流程id
						doFileReceive.setpInstanceId(pwfProcess.getfInstancdUid()); // 父实例id
						doFileReceive.setInstanceId(pwfProcess.getWfInstanceUid()); // 父实例id
						//doFileReceive.setpInstanceId(fprocessid);
						// 保存待收数据
						tableInfoService.addDoFileReceive(doFileReceive);
						// 更新该process信息
						pwfProcess.setIsExchanging(1);
						tableInfoService.updateWfProcess(pwfProcess);
					}

				}
			}
		}
	}
	
	/**
	 * 根据当前节点，更新下一节点所有暂存的步骤记录状态
	 * 
	 * @param nodeId
	 * @return
	 */
	public void updateNewProcess(String instanceId, String workFlowId,
			String nodeId, String nextNodeId) {
		if (nodeId != null && !("").equals(nodeId)) {
			// 根据当前节点Id查找下一节点Id
			List<WfNode> nodes = workflowBasicFlowService.showNode(workFlowId,
					nodeId,instanceId);
			for (WfNode wfNode : nodes) {
				tableInfoService.updateProcessShow(workFlowId, instanceId,
						wfNode.getWfn_id());
			}
		} else if (nextNodeId != null && !("").equals(nextNodeId)) {
			WfNode nextNode = workflowBasicFlowService.getWfNode(nextNodeId);
			if(nextNode!=null){
				String wfn_route_type = nextNode.getWfn_route_type();
				if(wfn_route_type!=null && wfn_route_type.equals("6")){
					WebSocketUtil webSocket = new WebSocketUtil();
					List<WfProcess> wfpList = tableInfoService.findWfProcessList(workFlowId, instanceId, nextNodeId, null);
					if(null != wfpList && wfpList.size()>0){
						for(int i=0; i<wfpList.size(); i++){
							String isOver = wfpList.get(i).getIsOver();
							if(isOver!=null && isOver.equals("NOT_OVER")){
								WfProcess wfp =  wfpList.get(i);
								WfItem item = tableInfoService.findWfItemById(wfp.getItemId());
								String push_type = "";
								if(item!=null){
									String vc_sxlx = item.getVc_sxlx();
									push_type = (vc_sxlx!=null && vc_sxlx.equals("1"))?"收文提醒: ":"发文提醒: ";
								}
								wfp.setIsShow(1);
								tableInfoService.update(wfp);
								String userId = wfp.getUserUid();
								String processTitle = wfp.getProcessTitle();
								try {
									webSocket.apnsPush(push_type+processTitle, wfp.getFromUserId(), "", "", "", userId);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								break;
							}
						}
					}
				}else{
					tableInfoService.updateProcessShow(workFlowId, instanceId, nextNodeId);
				}
			}
		}
	}
	
	public void excuteProcedure(String oldformId, int todo, String instanceId,
			String formId, String nodeid) {
		if (CommonUtil.stringNotNULL(nodeid)) {
			WfNode node = workflowBasicFlowService.getWfNode(nodeid);
			if (node != null) {
				String procedureName = node.getWfn_procedure_name();
				if (CommonUtil.stringNotNULL(procedureName)) {
					// 输入、输出参数 目前只支持VARCHAR、INTEGER、DATE三种
					// 输入、输出参数类型 仅有in、out两种
					// Object[][] obj={
					// {"in", "VARCHAR","wh1234"},//1输入参数 2输入参数类型 3输入参数值
					// {"in", "VARCHAR","id1234"},
					// {"in", "DATE",Timestamp.valueOf("2013-01-01 15:25:33")},
					// {"out", "INTEGER","oldCount"},//1输出参数 2输出参数类型 3输出值map的key
					// {"out", "INTEGER","newCount"},
					// {"out", "DATE","cDate"}
					// };
					// 目前固定两个输入参数
					// Object[][] obj={
					// {"in", "VARCHAR",UuidGenerator.generate36UUID()},//1输入参数
					// 2输入参数类型 3输入参数值
					// {"in", "VARCHAR",instanceId},//1输入参数 2输入参数类型 3输入参数值
					// {"in", "VARCHAR",formId},
					// {"in", "VARCHAR",nodeid}
					// };
					// Map<String, Object>
					// map=zwkjFormService.excuteProcedure(obj, procedureName);

					Procedure param = new Procedure();
					param.setPname(procedureName);
					List<Procedure> pList = zwkjFormService
							.getProcedureByParam(param);
					String workflowid = sessionWorkflowid;
					if (pList != null && pList.size() > 0) {
						Procedure p = pList.get(0);
						String[] paramname = p.getParamname() == null ? null
								: p.getParamname().split(",");
						String[] paramtype = p.getParamtype() == null ? null
								: p.getParamtype().split(",");
						String[] inoutparam = p.getInouttype() == null ? null
								: p.getInouttype().split(",");
						Object[][] obj = null;
						if (paramname != null) {
							obj = new Object[paramname.length][3];
							for (int i = 0; i < paramname.length; i++) {
								if (!paramname[i].equals("")) {
									Object[] o = new Object[3];
									o[0] = inoutparam[i];
									o[1] = paramtype[i];
									if (paramname[i].equals("uuid")) {
										o[2] = UuidGenerator.generate36UUID();
									} else if (paramname[i]
											.equals("workflow_id")) {
										o[2] = workflowid;
									} else if (paramname[i]
											.equals("workflow_instance_id")) {
										o[2] = instanceId;
									} else if (paramname[i].equals("form_id")) {
										o[2] = formId;
									} else if (paramname[i].equals("node_id")) {
										o[2] = nodeid;
									}
									obj[i] = o;
								}
							}
						}
						try {
							Map<String, Object> map = zwkjFormService.excuteProcedure(obj, procedureName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void geneFj(String allPath, String path,String fileTyle, String instanceId, String attId){
		ToPdfUtil pdfUtil = new ToPdfUtil();
		File file = new File(allPath);
		//判断附件是否转成了pdf,因有同步过来的数据,需检查
		if(!file.exists()){
			try {
				if(("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")){
					path = pdfUtil.docToPdf(path,fileTyle, instanceId, attId);
				}
				if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					path = pdfUtil.xlsToPdf(path,fileTyle,instanceId, attId);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	
	
	public String getXxlx(String xxlx){
		String lxmc = "";
		switch(Integer.valueOf(xxlx)){
		case 2:
			lxmc  = "公文管理";
			break;
		case 4:
			lxmc = "工作任务";
			break;
		case 5:
			lxmc = "工作计划";
			break;
		case 8:
			lxmc = "工作动态";
			break;
		case 11:
			lxmc = "依申请公开";
			break;
		case 17:
			lxmc = "工作日志";
			break;
		case 19:
			lxmc = "车辆管理";
			break;
		case 20:
			lxmc = "会议管理";
			break;
		case 21:
			lxmc = "资产管理";
			break;
		case 22:
			lxmc = "资产管理";
			break;
		}
		return lxmc;
	}
	
	public JSONObject regeneJSON(String commonJSON,JSONObject jo){
		 HttpClient client = new HttpClient();
		 int startY = jo.getInt("startY");
		 int startX = jo.getInt("startX");
		 PostMethod post = new PostMethod("http://localhost:9898/?ox="+startX+"&oy="+startY);
		 post.setRequestHeader("Content-Type","application/json;charset=utf-8");
		 StringBuffer sb = new StringBuffer();
		 post.setRequestBody("["+commonJSON+"]");
		try {
			int  state =   client.executeMethod(post);
			if(state == 200){
				
				BufferedReader br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), "UTF-8"));
				String data = null;
				while ((data = br.readLine()) != null) {
					sb.append(data);
				}
				br.close();
				post.abort();
				Runtime.getRuntime().gc();
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject js = 	JSONObject.fromObject(sb.toString());
		/*String json ="";
		if(sb != null &&sb.length() >0 &&!sb.toString().equals("")){
			 json  = sb.toString().replace("\\\"", "\"");
			json = json.replace("\",  \"Error\": null", "").replace("{  \"Data\": \"","");
			//json = JSONObject.fromObject(json).get("Data").toString();
		}*/
		return js;
	}
	
	
	/**
	 * 
	 * 描述：推送消息内容到通讯服务器中
	 * @param oldProcess void
	 * 作者:蔡亚军
	 * 创建时间:2016-7-12 上午9:57:52
	 */
	public void sycnPendToChat(WfProcess oldProcess, String nextNodeId, SendNextProcess entity){
		RemoteLogin remote = new RemoteLogin();
		boolean checkUser = RemoteLogin.checkPassed;
		//发送到第三方短信接口
		String serverUrl = entity.getServerUrl();
		String url = serverUrl+"/table_openPendingForm.do";
		String json = "";
		String itemId =	entity.getItemId();
		WfItem wfItem = itemService.getItemById(itemId);
		String itemName = wfItem.getVc_sxmc();
		//String msgContent = "您有一条待办,标题为'"+title+"',地址为："+url;
		String xxlx = wfItem.getVc_xxlx();
		String xmlxName = getXxlx(xxlx);
		String wfTitle = "";
		if(title==null || title.equals("") || title.equals("null")){
			if(oldProcess!=null){
				wfTitle = oldProcess.getProcessTitle();
			}
		}else{
			wfTitle = title;
		}
		json += "{\"name\":\""+ wfTitle+"\",\"itemId\":\""+itemId+"\",\"xxlx\":\""
				+xmlxName+"\",\"itemName\":\""+itemName+"\",";
		//获取processId
		List<WfProcess> list = null;
		int step = 0;
		if(oldProcess!=null){
			String wfUId = oldProcess.getWfUid();
			String instanceId = oldProcess.getWfInstanceUid();
			step = oldProcess.getStepIndex()+1;
			list = tableInfoService.findProcesses(wfUId,instanceId,nextNodeId);
		}
		MsgSend msgSend = null;
		if(checkUser){
			WfProcess wf = null;
			for(int i=0; i<list.size(); i++){
				wf = list.get(i);
				if(wf.getIsOver().equals("NOT_OVER") && step == wf.getStepIndex() && wf.getIsShow()==1){
					String commentJson = json + "\"processId\":\""+wf.getWfProcessUid()+"\",";
					String userId = wf.getUserUid();
					Employee emp_xto = employeeService.findEmployeeById(userId);
					String pendingUrl = url +"?processId="+wf.getWfProcessUid()+"&isDb=true&itemId="+wf.getItemId()+"&formId="+wf.getFormId();
					msgSend = new MsgSend();
					msgSend.setSendUserId(emp.getEmployeeGuid());
					msgSend.setSendUserName(emp.getEmployeeLoginname());
					msgSend.setRecUserId(emp_xto.getEmployeeGuid());
					msgSend.setRecUserName(emp_xto.getEmployeeLoginname());
					msgSend.setSendDate(new Date());
					msgSend.setProcessId(wf.getWfProcessUid());
					msgSend.setStatus(2);
					msgSend.setTitle(wf.getProcessTitle());
					commentJson += "\"url\":\""+pendingUrl+"\"}";
					msgSend.setContent(commentJson);
					tableInfoService.saveMsgSend(msgSend);
					JSONObject obj = new JSONObject();
					obj = MsgToObj.msgToObj(msgSend, serverUrl);
					remote.SendUsersMessage(msgSend.getSendUserId(), emp.getEmployeeName(), obj.toString(), emp_xto.getEmployeeGuid(), "");
				}
			}
		}
	}
}
