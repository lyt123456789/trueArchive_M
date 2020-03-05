package cn.com.trueway.document.business.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.BLOB;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.hibernate.lob.SerializableClob;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.docxg.client.support.DocXgXmlUtil;
import cn.com.trueway.document.business.docxg.client.vo.BaseDoc;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;
import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ToRecDoc;
import cn.com.trueway.document.business.model.ToRecDocAttachmentSext;
import cn.com.trueway.document.business.model.ToRecDocAttachments;
import cn.com.trueway.document.business.model.ToSendDoc;
import cn.com.trueway.document.business.service.FieldMatchingService;
import cn.com.trueway.document.business.service.ReceiveDocService;
import cn.com.trueway.document.business.service.ToRecDocService;
import cn.com.trueway.document.business.util.DocXgConst;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.vo.TrueJSON;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.ClobToString;
import cn.com.trueway.workflow.set.util.PdfPage;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

public class ToRecDocAction extends BaseAction{

	private static final long serialVersionUID = 7517479511858998532L;
	
	private ToRecDocService toRecDocService;
	
	private DepartmentService departmentService;
	
	private FieldMatchingService fieldMatchingService;
	
	private ItemService itemService;
	
	private TableInfoService tableInfoService;
	
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private FlowService flowService;
	
	private AttachmentService attachmentService;
	
	private ReceiveDocService receiveDocService;
	
	private ZwkjFormService zwkjFormService;
	
	private EmployeeService employeeService;
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public ReceiveDocService getReceiveDocService() {
		return receiveDocService;
	}

	public void setReceiveDocService(ReceiveDocService receiveDocService) {
		this.receiveDocService = receiveDocService;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	public ToRecDocService getToRecDocService() {
		return toRecDocService;
	}

	public void setToRecDocService(ToRecDocService toRecDocService) {
		this.toRecDocService = toRecDocService;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public FieldMatchingService getFieldMatchingService() {
		return fieldMatchingService;
	}

	public void setFieldMatchingService(FieldMatchingService fieldMatchingService) {
		this.fieldMatchingService = fieldMatchingService;
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	/**
	 * 
	 * 描述：获取待收信息的列表
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午3:09:41
	 */
	public String getToRecDocList(){
		String wfTitle = getRequest().getParameter("wfTitle");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
		/*
		 * departId  上级部门id 和 本部门id 如果还有上上级部门id 需要再查下部门表 暂时只支持3及部门
		 * */
		String departId = "'"+dep.getSuperiorGuid()+"','"+dep.getDepartmentGuid()+"'";	//获取当前人员的父机构Id
		List<String> deps = new ArrayList<String>();
		//获取机构匹配
		List<DepRelationShip> deplist = fieldMatchingService.getDepRelationShipListByDepId(departId);
		DepRelationShip ship = null;
		String docxg_depId = "";
		for(int i=0; deplist!=null && i<deplist.size(); i++){
			ship = deplist.get(i);
			if(ship!=null){
				docxg_depId = ship.getDocxg_depId();
				deps.add(docxg_depId);
			}
		}
		String conditionSql = "";
		if(deps!=null && deps.size()>0){
			String depIds = "";
			for(int i=0; i<deps.size(); i++){
				depIds +="'"+deps.get(i)+"',";
			}
			if(depIds!=null && !depIds.equals("")){
				depIds = depIds.substring(0, depIds.length()-1);
			}
			conditionSql += " and t.departmentId in ("+depIds+")";
		}
		
		if(CommonUtil.stringNotNULL(wfTitle)){
			conditionSql += " and t.bt like '%"+wfTitle+"%'";
		}
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = toRecDocService.findToRecDocCount(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<ToRecDoc> list = toRecDocService.findToRecDocList(conditionSql,
					Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("wfTitle", wfTitle);
		return "toRecDocList";
	}
	
	/**
	 * 
	 * 描述：更换状态位置,将state由0改为1
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午3:16:49
	 */
	public void updateToRecDocStatus(){
		String ids = getRequest().getParameter("ids");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		JSONObject error = null;
		error = toRecDocService.updateToRecDocStatus(ids, userId);
		if(ids!=null && !ids.equals("")){
			String[] id = ids.split(",");
			for(int i=0; i<id.length; i++){
				receiveDocToWfProcess(id[i],userId);
			}
		}
		
		//将文章收入待办中,插入一条虚拟的wfprocess信息
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 收取(移动端)
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-5-16 上午11:25:43
	 */
	public void updateToRecDocOfMobile(){
		String recId = getRequest().getParameter("recId");
		String userId =  getRequest().getParameter("userId");
		String returnmsg="fail";
		toRecDocService.updateToRecDocStatus(recId, userId);
		if(recId!=null && !recId.equals("")){
			String[] id = recId.split(",");
			for(int i=0; i<id.length; i++){
				receiveDocToWfProcess(id[i],userId);
			}
		}
		returnmsg = "success";
		//将文章收入待办中,插入一条虚拟的wfprocess信息
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.print(returnmsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 
	 * 描述：TODO 对此方法进行描述
	 * @param id void
	 * 作者:蔡亚军
	 * 创建时间:2015-1-23 上午9:53:01
	 */
	public void receiveDocToWfProcess(String id,String userId){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		if(null == emp && CommonUtil.stringNotNULL(userId)){
			emp = employeeService.findEmployeeById(userId);
		}
		//获取当前登录用户
		userId = emp.getEmployeeGuid();
		Date nowTime = new Date();
		String instanceId = UuidGenerator.generate36UUID();
		//收取后直接插入待办列表中
		WfProcess newProcess = new WfProcess();
		newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
		newProcess.setWfInstanceUid(instanceId);
		newProcess.setAllInstanceid(instanceId);
		newProcess.setNodeUid("default");
		newProcess.setFromUserId(userId);
		newProcess.setOwner(userId);
		newProcess.setApplyTime(nowTime);
//		newProcess.setFinshTime(nowTime);
		newProcess.setIsOver(Constant.NOT_OVER);
		newProcess.setIsEnd(0);
		newProcess.setIsExchanging(0);
		//主送人ID及标识值
		newProcess.setUserUid(userId);
		newProcess.setIsMaster(1);
		newProcess.setIsShow(1);
		newProcess.setStepIndex(1);
		newProcess.setUserDeptId(emp.getDepartmentGuid());
		ToRecDoc toRecDoc = toRecDocService.findToRecDocById(id);
		if(toRecDoc!=null){
			String docguid = toRecDoc.getDocguid();
			List<ToRecDocAttachments> list_att = toRecDocService.findToRecDocAttachmentsByDocguid(docguid);
			List<ToRecDocAttachmentSext>  list_attSext = toRecDocService.findToRecDocAttachmentSextByDocguid(docguid);
			//将前置机附件入工作流库
			addSendAttsByList(list_att,list_attSext,instanceId);
			
			ReceiveProcessShip receiveProcessShip = new ReceiveProcessShip();
			receiveProcessShip.setInstanceId(instanceId);
			receiveProcessShip.setRecId(docguid);
			receiveProcessShip.setIdIndex(id);
			receiveDocService.saveReceiveProcessShip(receiveProcessShip);
		}
		String title = "";	//预先设置空标题
		newProcess.setProcessTitle(title);
		tableInfoService.save(newProcess);//下一步
	}
	
	/**
	 * 新加附件入工作流附件库
	 * 描述：TODO 对此方法进行描述
	 * @param list_att
	 * @param list_attSext
	 * @param instanceId void
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午10:35:24
	 */
	public void addSendAttsByList(List<ToRecDocAttachments> list_att,
			List<ToRecDocAttachmentSext>  list_attSext,String instanceId){
		SendAttachments attachment =null;
		String toPdfPath = "";
		ToRecDocAttachments toRecDocAttachments = null;
		Date date = new Date();
		int num = 0;
		for(int i=0; i<list_att.size(); i++){
			attachment = new SendAttachments();
			toRecDocAttachments = list_att.get(i);
			BLOB  blob = toRecDocService.getBlob("TO_RECDOC_ATTACHMENTS", "content", "id", toRecDocAttachments.getId());
			String location = createAttachmentFromBlob(toRecDocAttachments, "1", blob );
			String fileType = toRecDocAttachments.getFileType();
			toPdfPath = getPdfPathByRecAttId(toRecDocAttachments.getId());
			if(CommonUtil.stringIsNULL(toPdfPath)){
				toPdfPath = attToPdf(location,toRecDocAttachments,null);
			}
			attachment.setDocguid(instanceId+"fj");
			attachment.setTitle(toRecDocAttachments.getFileName());
			attachment.setLocalation(location);
			attachment.setFilesize(toRecDocAttachments.getFileSize());
			attachment.setFilename(toRecDocAttachments.getFileName());
			attachment.setFiletime(new Date(date.getTime()+num*1000));
			attachment.setFiletype(fileType);
			attachment.setType("正文材料");
			attachment.setTopdfpath(toPdfPath);
			attachmentService.addSendAtts(attachment);
			
			RecShip recShip = toRecDocService.getRecShipByRecAttId(toRecDocAttachments.getId());
			//新建前置机附件与工作流表的关系
			addRecShip(recShip,location,toRecDocAttachments,null);
			
			//插入 附件流
			HashMap<String, String> keyValueSet = new HashMap<String, String>();
			keyValueSet.put("ATTFLOW",  SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+attachment.getLocalation());
			flowService.geneSql("OA_DOC_ATTACHMENTS", keyValueSet, "id", attachment.getId());
			
			num++;
		}
		ToRecDocAttachmentSext toRecDocAttachmentSext = null;
		for(int i=0; i<list_attSext.size(); i++){
			attachment = new SendAttachments();
			toRecDocAttachmentSext = list_attSext.get(i);
			BLOB  blob = toRecDocService.getBlob("TO_RECDOC_ATTACHMENTSEXT", "content", "id", toRecDocAttachmentSext.getId());
			String location =  createAttachmentFromBlob(toRecDocAttachmentSext, "2", blob);
			toPdfPath = getPdfPathByRecAttId(toRecDocAttachmentSext.getId());
			if(CommonUtil.stringIsNULL(toPdfPath)){
				toPdfPath = attToPdf(location,null,toRecDocAttachmentSext);
			}
			attachment.setDocguid(instanceId+"fj");
			attachment.setTitle(toRecDocAttachmentSext.getFileName());
			attachment.setLocalation(location);
			attachment.setFilesize(toRecDocAttachmentSext.getFileSize());
			attachment.setFilename(toRecDocAttachmentSext.getFileName());
			attachment.setFiletime(new Date(date.getTime()+num*1000));
			attachment.setFiletype(toRecDocAttachmentSext.getFileType());
			attachment.setType("附件材料");
			attachment.setTopdfpath(toPdfPath);
			attachmentService.addSendAtts(attachment);
			
			RecShip recShip = toRecDocService.getRecShipByRecAttId(toRecDocAttachmentSext.getId());
			//新建前置机附件与工作流表的关系
			addRecShip(recShip,location,null,toRecDocAttachmentSext);
			
			//插入 附件流
			HashMap<String, String> keyValueSet = new HashMap<String, String>();
			keyValueSet.put("ATTFLOW",  SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+attachment.getLocalation());
			flowService.geneSql("OA_DOC_ATTACHMENTS", keyValueSet, "id", attachment.getId());
			
			num++;
		}
	}
	
	
	/**
	 * 
	 * 描述：从数据库中获取数据流,且生成文件,返回附件生成的location
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-23 上午10:22:30
	 */
	public String createAttachmentFromBlob(Object attach, String type, BLOB blob){
		String fileType = "";
		String fileName = "";
		if(type!=null && type.equals("1")){
			ToRecDocAttachments attachment = (ToRecDocAttachments)attach;
			fileType = attachment.getFileType();
			fileName = attachment.getFileName();
		}else if(type.equals("2")){
			ToRecDocAttachmentSext attachment = (ToRecDocAttachmentSext)attach;
			fileType = attachment.getFileType();
			fileName = attachment.getFileName();
		}
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String dstPath = FileUploadUtils.getRealFilePath(fileName+"."+fileType, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String newHtmlPath = basePath+dstPath; 
		FileUtils.byteArrayToFile(blob, newHtmlPath);
		return dstPath;
	}
	
	/**
	 * 
	 * 描述：获取已收信息
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午3:23:06
	 */
	public String getToRecedDocList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String wfTitle = getRequest().getParameter("wfTitle");
		String itemName =  getRequest().getParameter("itemName");
		String fileNum = getRequest().getParameter("filenum");
		String fileUnit = getRequest().getParameter("fileunit");
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(wfTitle)){
			conditionSql += " and t.bt like '%"+wfTitle.trim()+"%'";
			getRequest().setAttribute("wfTitle", wfTitle);
		}
		if(CommonUtil.stringNotNULL(itemName)){
		}
		if(StringUtils.isNotBlank(fileNum)){
			conditionSql += " and t.wh like '%"+fileNum+"%'";
			getRequest().setAttribute("filenum", fileNum);
		}
		if(StringUtils.isNotBlank(fileUnit)){
			conditionSql += " and t.fwdw like '%"+fileUnit+"%'";
			getRequest().setAttribute("fileunit", fileUnit);
		}
		//分页获取
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = toRecDocService.findToRecedDocCount(userId, conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<ToRecDoc> list = toRecDocService.findToRecedDocList(userId, conditionSql,
					Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		List<WfItem> itemList = itemService.getItemList(emp.getDepartmentGuid());
		getRequest().setAttribute("itemList", itemList);
		Map<String, ReceiveProcessShip> map = toRecDocService.findReceiveWfpShipList(list);
		getRequest().setAttribute("map", map);
		getRequest().setAttribute("wfTitle", wfTitle);
		getRequest().setAttribute("itemName", itemName);
		return "toRecedDocList";
	}
	
	public String getToSendDocList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String wfTitle = getRequest().getParameter("wfTitle");
		String fwdw = getRequest().getParameter("fwdw");
		String conditionSql = "";
		if(CommonUtil.stringNotNULL(wfTitle)){
			conditionSql += " and t.bt like '%"+wfTitle+"%'";
	 	}
		if(CommonUtil.stringNotNULL(fwdw)){
			conditionSql += " and t.fwdw like '%"+fwdw+"%'";
	 	}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = toRecDocService.findToSendDocCount(userId, conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<ToSendDoc> list = toRecDocService.findToSendDocList(userId, conditionSql,
					Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("wfTitle", wfTitle);
		getRequest().setAttribute("fwdw", fwdw);
		return "toSendDocList";
	}
	
	
	public String getRecDepList(){
		String instanceId = getRequest().getParameter("instanceId");
		List<Object[]> list = toRecDocService.findRecDepList(instanceId);
		getRequest().setAttribute("list", list);
		return "recDepList";
	}
	
	/**
	 * 
	 * 描述：进入待办
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-22 下午4:57:58
	 */
	public void innerPending() {
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String workFlowId = getRequest().getParameter("workFlowId");
		String itemId = getRequest().getParameter("itemId");
		String itemName = itemService.getItemById(itemId).getVc_sxmc();
		String recId = getRequest().getParameter("recId");
		String message = "";
		if(recId!=null){
			ReceiveProcessShip receiveProcessShip = receiveDocService.findReceiveProcessShipByRecId(recId);
			if(receiveProcessShip!=null){
				WfNode wfNode = workflowBasicFlowService.findFirstNodeId(workFlowId);
				String formId = wfNode.getWfn_defaultform();
				String nodeId = wfNode.getWfn_id();
				String instanceId = receiveProcessShip.getInstanceId();
				List<WfProcess> wflist = tableInfoService.getProcessList(instanceId);
				int total = 0;
				WfProcess wfProcess = null;
				if(wflist!=null && wflist.size()>0){
					total =  wflist.size();
					if(total == 1){
						wfProcess = wflist.get(0);
					}
				}
				wfProcess.setNodeUid(wfNode.getWfn_id());	//插入node
				wfProcess.setWfUid(workFlowId);
				wfProcess.setItemId(itemId);
				wfProcess.setFormId(formId);
				wfProcess.setUserUid(userId);
				ToRecDoc toRecDoc = toRecDocService.findToRecDocByDocguid(recId);
				BaseDoc baseDoc = null;
				if(toRecDoc!=null ){
					String xml = clobToString(toRecDoc.getDocXml());
					//获取数据,组装BaseDoc
					List<BaseDoc> list = null;
					try {
						list = DocXgXmlUtil.findBaseDocListFromXml(xml);
					} catch (XmlException e) {
						e.printStackTrace();
					}
					if(list!=null && list.size()>0){
						baseDoc = list.get(0);
					}
				}
				String idIndex = receiveProcessShip.getIdIndex();
				//保存form值
				String title = saveForm(formId, workFlowId, nodeId, instanceId, itemId, idIndex, baseDoc);
				wfProcess.setProcessTitle(title);
				wfProcess.setStatus("0");//办件状态为办
				wfProcess.setFromNodeid(nodeId);
				wfProcess.setToNodeid(nodeId);
				//process保存数据库
				tableInfoService.update(wfProcess);
				//保存到dofile
				DoFile doFile = new DoFile();
				doFile.setDoFile_title(title);
				doFile.setItemId(itemId);
				doFile.setWorkflowId(workFlowId);
				doFile.setInstanceId(instanceId);
				doFile.setItemName(itemName);
				doFile.setDo_time(new Date());
				doFile.setFormId(formId);
				doFile.setNodeId(nodeId);
				
				tableInfoService.saveDoFile(doFile);
				//更新ship中的数据
				receiveProcessShip.setProcessId(wfProcess.getWfProcessUid());
				receiveDocService.updateReceiveProcessShip(receiveProcessShip);
				message =  "success"+";"+wfProcess.getFormId()+";"+wfProcess.getWfInstanceUid()+";"+wfProcess.getWfProcessUid()+";"+title;
			}
		}
		try{
			getResponse().getWriter().print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 移动端上级已收转待办
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-7-27 上午11:58:24
	 */
	public String innerPendingOfMobile(String workFlowId,String itemId,String docguid,String userId){
		//获取当前登录用户
		Employee emp = employeeService.findEmployeeById(userId);
		userId = emp.getEmployeeGuid();
		String itemName = itemService.getItemById(itemId).getVc_sxmc();

		String message = "";
		if(docguid!=null){
			ReceiveProcessShip receiveProcessShip = receiveDocService.findReceiveProcessShipByRecId(docguid);
			if(receiveProcessShip!=null){
				WfNode wfNode = workflowBasicFlowService.findFirstNodeId(workFlowId);
				String formId = wfNode.getWfn_defaultform();
				String nodeId = wfNode.getWfn_id();
				String instanceId = receiveProcessShip.getInstanceId();
				List<WfProcess> wflist = tableInfoService.getProcessList(instanceId);
				int total = 0;
				WfProcess wfProcess = null;
				if(wflist!=null && wflist.size()>0){
					total =  wflist.size();
					if(total == 1){
						wfProcess = wflist.get(0);
					}
				}
				wfProcess.setNodeUid(wfNode.getWfn_id());	//插入node
				wfProcess.setWfUid(workFlowId);
				wfProcess.setItemId(itemId);
				wfProcess.setFormId(formId);
				wfProcess.setUserUid(userId);
				ToRecDoc toRecDoc = toRecDocService.findToRecDocByDocguid(docguid);
				BaseDoc baseDoc = null;
				if(toRecDoc!=null ){
					String xml = clobToString(toRecDoc.getDocXml());
					//获取数据,组装BaseDoc
					List<BaseDoc> list = null;
					try {
						list = DocXgXmlUtil.findBaseDocListFromXml(xml);
					} catch (XmlException e) {
						e.printStackTrace();
					}
					if(list!=null && list.size()>0){
						baseDoc = list.get(0);
					}
				}
				String idIndex = receiveProcessShip.getIdIndex();
				//保存form值
				String title = saveForm(formId, workFlowId, nodeId, instanceId, itemId, idIndex, baseDoc);
				wfProcess.setProcessTitle(title);
				wfProcess.setStatus("0");//办件状态为办
				wfProcess.setFromNodeid(nodeId);
				wfProcess.setToNodeid(nodeId);
				//process保存数据库
				tableInfoService.update(wfProcess);
				//保存到dofile
				DoFile doFile = new DoFile();
				doFile.setDoFile_title(title);
				doFile.setItemId(itemId);
				doFile.setWorkflowId(workFlowId);
				doFile.setInstanceId(instanceId);
				doFile.setItemName(itemName);
				doFile.setDo_time(new Date());
				doFile.setFormId(formId);
				doFile.setNodeId(nodeId);
				
				tableInfoService.saveDoFile(doFile);
				//更新ship中的数据
				receiveProcessShip.setProcessId(wfProcess.getWfProcessUid());
				receiveDocService.updateReceiveProcessShip(receiveProcessShip);
				message =  "success"+";"+wfProcess.getFormId()+";"+wfProcess.getWfInstanceUid()+";"+wfProcess.getWfProcessUid();
			}
		}
		return message;
	}
	
	public String saveForm(String formId, String workFlowId, String nodeId,
			String instanceId, String itemId, String idIndex, BaseDoc baseDoc){
		// 读取表单所设定的所有表
		List<FormTagMapColumn> tableNameAllLists = zwkjFormService.getTableNameByFormId(formId);
		// ========================列表类型=============================//
		List<FormTagMapColumn> haveLists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameHaveList = new ArrayList<String>();
		// ========================非列表类型=============================//
		List<FormTagMapColumn> lists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameList = new ArrayList<String>();
		// 列表和非列表类型的分开存入list中
		for (FormTagMapColumn ft : tableNameAllLists) {
			if (ft.getListId() != null && !("").equals(ft.getListId())) {
				haveLists.add(ft);
			} else {
				lists.add(ft);
			}
		}
		// 读取两个list中的tableName(去重之后的)
		for (int i = 0, n = haveLists.size(); i < n; i++) { // 列表型
			if (!tableNameHaveList.contains(haveLists.get(i).getTablename())) {
				tableNameHaveList.add(haveLists.get(i).getTablename());
			}
		}
		for (int i = 0, n = lists.size(); i < n; i++) { // 非列表型
			if (!tableNameList.contains(lists.get(i).getAssignTableName())) {
					tableNameList.add(lists.get(i).getAssignTableName());
				}
		}
		String value = "";// 页面回值
		String vc_title ="";
		try {
			value = this.getNotListTypeForm(tableNameList, value, formId, 
					 instanceId, formId, nodeId, workFlowId,itemId,idIndex, baseDoc);
			vc_title = getTitle(workFlowId, formId, instanceId).split(";")[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vc_title;
	}
	
	
	/**
	 * 得到流程的标题和所对应的字段
	 */
	public String getTitle(String workFlowId, String formId, String instanceId) {
		WfMain wf_main = workflowBasicFlowService.getWfMainById(workFlowId);
		String title = getTitle(wf_main, instanceId);// {标题}test---找到手动输入的值
		String name = tableInfoService.getTableAndColumnName(wf_main.getWfm_title_column());// tableName;columnName#tableName;columnName
		// 查找列名,用于页面判断
		String columnName = "";
		// String columnValue = "";
		String[] names = name.split("#");
		for (String str : names) {
			columnName += str.split(";")[1] + ",";
		}
		if (!("").equals(columnName) && columnName.length() > 0) {
			columnName = columnName.substring(0, columnName.length() - 1);
		}
		return title + ";" + columnName;
	}
	
	
	public String getTitle(WfMain wfMain, String instanceId) {
		String str = wfMain.getWfm_title_name();
		String tablename = wfMain.getWfm_title_table();
		String names = wfMain.getWfm_title_name();
		String ids = wfMain.getWfm_title_column();

		WfTableInfo table = tableInfoService.getTableById(tablename);
		if (table != null) {
			tablename = table.getVc_tablename();
		}
		// 解析标题字符串 2013年{姓名}3月{性别}4日
		List<String> cloumnNames = new ArrayList<String>();

		String regEx = "\\{[^\\}\\{]*\\}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(names);
		String tag = "";
		String all = "";
		while (m.find()) {
			// 标签和内容
			tag = m.group();
			if (tag != null && !tag.equals("")) {
				tag = tag.substring(1, tag.length() - 1);
				cloumnNames.add(tag);
			}
		}
		String[] idsArr = ids == null ? null : ids.split(",");

		if (cloumnNames != null) {
			for (int i = 0; i < cloumnNames.size(); i++) {
				WfFieldInfo filed = tableInfoService.getFieldById(idsArr[i]);
				String sql = "select " + filed.getVc_fieldname()
						+ ",'替代' from " + tablename + " t where t.INSTANCEID='"
						+ instanceId + "'";
				List<Object[]> list = tableInfoService.getListBySql(sql);
				String value = "";
				if (list != null && list.size() > 0) {
					String fieldtype = filed.getI_fieldtype() ;
					//clob字段需要处理
					if(fieldtype!=null && fieldtype.equals("3")){		
						value = ClobToString.clob2String((SerializableClob) list.get(0)[0]);
					}else{
						value = list.get(0)[0]==null?"":list.get(0)[0].toString();
					}
				}
				str = str.replace("{" + cloumnNames.get(i) + "}", value);
			}
		}
		return str;
	}
	
	public String getNotListTypeForm(List<String> tableNameList, String value, String oldformId,
			String instanceId, String formId, String nodeId, String workFlowId,
			String itemId, String idIndex, BaseDoc baseDoc)
		throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//根据itemId到数据库中匹配关系
		List<DocxgFieldMap> list  =  fieldMatchingService.getDocxgFieldMapList(itemId);
		StringBuffer searchValue = new StringBuffer();
		Object model = baseDoc;
		Field[] fields = model.getClass().getDeclaredFields();//获取实体类的所有属性，返回filed数组
		Map<String,Object> tagValueList = new HashMap<String,Object>();
		for(int i=0; i<tableNameList.size(); i++){
			String tableName = tableNameList.get(i);
			DocxgFieldMap fieldMap = null;
			for(int j=0; j<list.size(); j++){
				fieldMap = list.get(j);
				String table = fieldMap.getTableName(); 
				if(table.equals(tableName)){
					String coloum = fieldMap.getTagName();
					String name  = fieldMap.getFieldName();
				//	System.out.println("--------------coloum="+coloum+ " name="+name);
					//反射获取每一个属性
					Object tagValue = null ;
					for (int k = 0; k < fields.length; k++) {				//遍历所有属性
						String fieldname = fields[k].getName();					//获取属性的名字
					//	System.out.println("-----------fieldname="+fieldname);
						String type = fields[k].getGenericType().toString();//获取属性的类型
						if(fieldname.equals(coloum)){
							if(type.equals("class java.lang.String")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								tagValue = (String) m.invoke(model);   //调用getter方法获取属性值
							}else if(type.equals("class java.lang.Integer")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								tagValue = (Integer) m.invoke(model);   //调用getter方法获取属性值
							}else if(type.equals("class java.util.Date")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								if( m.invoke(model)!=null){
									tagValue = (Date) m.invoke(model);   //调用getter方法获取属性值
									tagValue=sdf.format(tagValue);
								}
							}else if(type.equals("class java.lang.Long")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								if(coloum.equals("priority")){
									tagValue = (Long) m.invoke(model);   //调用getter方法获取属性值
									tagValue = DocXgConst.getPriorityNameByCode((Long) m.invoke(model));
								}else{
									tagValue = (Long) m.invoke(model);   //调用getter方法获取属性值
								}
							}
						}
						if(name!=null && !name.equals("") && !name.equals("null") && null!=tagValue){
							tagValueList.put(name, tagValue);
						}
					}
					if(name!=null && !name.equals("") && !name.equals("null")){
						searchValue.append(name + ":" + tagValue + ";");
					}
				}
			}
			if(tagValueList!=null && tagValueList.size()>0){
				tagValueList.put("workflowid", workFlowId);
				tagValueList.put("formid", formId);
				tagValueList.put("instanceid", instanceId);
				tagValueList.put("processid", UuidGenerator.generate36UUID());
			}
			fieldMatchingService.saveForm(tableName, tagValueList, instanceId);
		}
		
		if(searchValue!=null){
			value = searchValue.toString();
		}
		return value;
	}
	
	/**
	 * 
	 * 描述：预览信息
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-1-24 上午9:53:09
	 */
	public String toViewToRecDocInfo(){
		String id = getRequest().getParameter("id");
		ToRecDoc toRecDoc = toRecDocService.findToRecDocById(id);
		String docguid = toRecDoc.getDocguid();
		if(toRecDoc!=null){
			String xml = clobToString(toRecDoc.getDocXml());
			//解析xml
			if(xml==null || xml.trim().equals("")){
				return null; 
			}
			try{
				List<BaseDoc> list = null;
				BaseDoc baseDoc = null;
				try {
					list = DocXgXmlUtil.findBaseDocListFromXml(xml);
				} catch (XmlException e) {
					e.printStackTrace();
				}
				if(list!=null && list.size()>0){
					baseDoc = list.get(0);
				}
				getRequest().setAttribute("recDoc", baseDoc);
			}catch (Exception e) {
				e.printStackTrace();
			}
			List<ToRecDocAttachments> zwlist = toRecDocService.findToRecDocAttachmentsByDocguid(docguid);
			List<ToRecDocAttachmentSext> attList = toRecDocService.findToRecDocAttachmentSextByDocguid(docguid);
			//更新前置机附件
			updateToRecDocAtt(zwlist,attList);
			String fileDownloadUrl=SystemParamConfigUtil.getParamValueByParam("filedownloadurl");
			getRequest().setAttribute("attList", attList);
			getRequest().setAttribute("zwlist", zwlist);
			getRequest().setAttribute("fileDownloadUrl", fileDownloadUrl);
		}
		return "toRecDocInfo";
	}
	
	/**
	 * 更新前置机附件信息
	 * 描述：TODO 对此方法进行描述
	 * @param attList
	 * @param zwlist void
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午10:43:45
	 */
	public void updateToRecDocAtt(List<ToRecDocAttachments> zwlist,
										List<ToRecDocAttachmentSext> attList){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		ToRecDocAttachments entity = null;
		String location = "";
		for(int i=0; i<zwlist.size(); i++){
			entity = zwlist.get(i);
			location = entity.getLocalation();
			if(CommonUtil.stringNotNULL(location)&&location.startsWith("http")){
				updateToRecDocAttachments(entity,basePath);
			}else{
				File file = new File(basePath+location);
				if(file.exists()){
					RecShip recShip = toRecDocService.getRecShipByRecAttId(entity.getId());
					//新建前置机附件与工作流表的关系
					addRecShip(recShip,location,entity,null);
				}else{
					updateToRecDocAttachments(entity,basePath);
				}
			}
		}
		
		ToRecDocAttachmentSext mentSext = null;
		for(int i=0; i<attList.size(); i++){
			mentSext = attList.get(i);
			location = mentSext.getLocalation();
			if(CommonUtil.stringNotNULL(location)&&location.startsWith("http")){
				updateToRecDocAttachmentSext(mentSext,basePath);
			}else{
				File file = new File(basePath+location);
				if(file.exists()){
					RecShip recShip = toRecDocService.getRecShipByRecAttId(entity.getId());
					//新建前置机附件与工作流表的关系
					addRecShip(recShip,location,entity,null);
				}else{
					updateToRecDocAttachmentSext(mentSext,basePath);
				}
			}
		}
	}
	
	/**
	 * 更新ToRecDocAttachments
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:季振华
	 * 创建时间:2017-5-17 下午2:19:05
	 */
	public void updateToRecDocAttachments(ToRecDocAttachments entity,String basePath){
		BLOB  blob = toRecDocService.getBlob("TO_RECDOC_ATTACHMENTS", "content", "id", entity.getId());
		String fileName = entity.getFileName();
		String fileType = entity.getFileType();
		String dstPath = FileUploadUtils.getRealFilePath(fileName+"."+fileType, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String newHtmlPath = basePath+dstPath; 
		FileUtils.byteArrayToFile(blob, newHtmlPath);
		entity.setLocalation(dstPath);
		RecShip recShip = toRecDocService.getRecShipByRecAttId(entity.getId());
		//新建前置机附件与工作流表的关系
		addRecShip(recShip,dstPath,entity,null);
		toRecDocService.updateToRecDocAttachments(entity);
	}
	
	/**
	 * 更新ToRecDocAttachmentSext
	 * 描述：TODO 对此方法进行描述
	 * @param entity void
	 * 作者:季振华
	 * 创建时间:2017-5-17 下午2:19:05
	 */
	public void updateToRecDocAttachmentSext(ToRecDocAttachmentSext entity,String basePath){
		BLOB  blob = toRecDocService.getBlob("TO_RECDOC_ATTACHMENTSEXT", "content", "id", entity.getId());
		String fileName = entity.getFileName();
		String fileType = entity.getFileType();
		String dstPath = FileUploadUtils.getRealFilePath(fileName+"."+fileType, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String newHtmlPath = basePath+dstPath; 
		FileUtils.byteArrayToFile(blob, newHtmlPath);
		entity.setLocalation(dstPath);
		RecShip recShip = toRecDocService.getRecShipByRecAttId(entity.getId());
		//新建前置机附件与工作流表的关系
		addRecShip(recShip,dstPath,null,entity);
		toRecDocService.updateToRecDocAttachmentSext(entity);
	}
	
	/**
	 * 新建前置机附件与工作流表的关系
	 * 描述：TODO 对此方法进行描述
	 * @param recShip
	 * @param entity
	 * @param mentSext void
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午11:02:58
	 */
	public void addRecShip(RecShip recShip,String location,ToRecDocAttachments entity,
							ToRecDocAttachmentSext mentSext){
		Integer pageCount = 0;
		String toPdfPath = "";
		if(null!=entity){
			if(null == recShip){
				recShip = new RecShip();
				toPdfPath = attToPdf(location,entity,null);
				recShip.setRecAttId(entity.getId());
				recShip.setRecId(entity.getDocguid());
				recShip.setFileName(entity.getFileName());
				recShip.setCreateTime(new Date());
				recShip.setPdfPath(toPdfPath);
				recShip.setIsZw("0");//是正文
				recShip.setFileTime(entity.getFileTime());
				pageCount = PdfPage.getPdfPage(toPdfPath);
				recShip.setPageCount(pageCount);
				toRecDocService.addRecShip(recShip);
			}else{
				toPdfPath = recShip.getPdfPath();
				File file = null;
				if(CommonUtil.stringNotNULL(toPdfPath)){
					file = new File(toPdfPath);
				}
				if(null==file || !file.exists()){
					toPdfPath = attToPdf(location,entity,null);
					recShip.setPdfPath(toPdfPath);
					pageCount = PdfPage.getPdfPage(toPdfPath);
					recShip.setPageCount(pageCount);
					recShip.setIsZw("0");//是正文
					toRecDocService.addRecShip(recShip);
				}
			}
		}
		if(null!=mentSext){
			if(null == recShip){
				recShip = new RecShip();
				toPdfPath = attToPdf(location,null,mentSext);
				recShip.setFileName(mentSext.getFileName());
				recShip.setRecAttId(mentSext.getId());
				recShip.setRecId(mentSext.getDocguid());
				recShip.setCreateTime(new Date());
				recShip.setPdfPath(toPdfPath);
				recShip.setIsZw("1");//是附件
				recShip.setFileTime(mentSext.getFileTime());
				pageCount = PdfPage.getPdfPage(toPdfPath);
				recShip.setPageCount(pageCount);
				toRecDocService.addRecShip(recShip);
			}else{
				toPdfPath = recShip.getPdfPath();
				File file = null;
				if(CommonUtil.stringNotNULL(toPdfPath)){
					file = new File(toPdfPath);
				}
				if(null==file || !file.exists()){
					toPdfPath = attToPdf(location,null,mentSext);
					recShip.setPdfPath(toPdfPath);
					pageCount = PdfPage.getPdfPage(toPdfPath);
					recShip.setIsZw("1");//是附件
					recShip.setPageCount(pageCount);
					toRecDocService.addRecShip(recShip);
				}
			}
		}
	}
	
	/**
	 * 附件转换成pdf
	 * 描述：TODO 对此方法进行描述
	 * @param location
	 * @param toRecDocAttachments
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午9:38:22
	 */
	public String attToPdf(String location,ToRecDocAttachments toRecDocAttachments,
			ToRecDocAttachmentSext toRecDocAttachmentSext){
		String toPdfPath = "";
		if(null != toRecDocAttachments){
			String fileType = toRecDocAttachments.getFileType();
			String docguid = toRecDocAttachments.getDocguid();
			String id = toRecDocAttachments.getId();
			toPdfPath = getPdfPathByRecAttId(id);
			if(CommonUtil.stringIsNULL(toPdfPath)){
				toPdfPath = fileToPdf(location,fileType,docguid,id);
			}
			
		}else{
			String fileType = toRecDocAttachmentSext.getFileType();
			String docguid = toRecDocAttachmentSext.getDocguid();
			String id = toRecDocAttachmentSext.getId();
			toPdfPath = getPdfPathByRecAttId(id);
			if(CommonUtil.stringIsNULL(toPdfPath)){
				toPdfPath = fileToPdf(location,fileType,docguid,id);
			}
		}
			
		return toPdfPath;
	}
	
	/**
	 * 通过attid获取pdfpath
	 * 描述：TODO 对此方法进行描述
	 * @param id
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午11:15:52
	 */
	public String getPdfPathByRecAttId(String id){
		String pdfPath = "";
		RecShip recShip = toRecDocService.getRecShipByRecAttId(id);
		if(null == recShip){
			return "";
		}else{
			pdfPath = recShip.getPdfPath();
			File file = null;
			if(CommonUtil.stringNotNULL(pdfPath)){
				file = new File(pdfPath);
			}
			if(null==file || !file.exists()){
				return "";
			}else{
				return pdfPath;
			}
		}

	}
	
	/**
	 * 文件转换成pdf
	 * 描述：TODO 对此方法进行描述
	 * @param location
	 * @param fileType
	 * @param docguid
	 * @param id
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-5-17 上午10:31:53
	 */
	public String fileToPdf(String location,String fileType,String docguid,String id){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String filePath = basePath + location;
		String toPdfPath = "";
		try{
			ToPdfUtil pdfUtil = new ToPdfUtil();
			File file = new File(filePath);
			int num = 0;
			while(!file.exists() && num<3){
				Thread.sleep(1000);
				num++;
			}
			
			if (("doc").equalsIgnoreCase(fileType) || fileType.equalsIgnoreCase("docx")) {
				toPdfPath = pdfUtil.docToPdf(location,fileType, docguid, id); 
			} else if (("ceb").equals(fileType)) {
				CebToPdf cp = new CebToPdf();
				// 文件路径
				cp.cebToPdf(filePath);
				toPdfPath = basePath+ location.substring(0,location.length() - 3) + "pdf";
			}else if (("pdf").equalsIgnoreCase(fileType)){
				toPdfPath = basePath+location;
			}else if(("xlsx").equalsIgnoreCase(fileType) || fileType.equalsIgnoreCase("xls")){
				toPdfPath = pdfUtil.xlsToPdf(location, fileType, docguid, id);
			}else if(("jpg").equalsIgnoreCase(fileType) || fileType.equalsIgnoreCase("png") || 
					fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("bmp")
					|| fileType.equalsIgnoreCase("tif")){
				toPdfPath = pdfUtil.imgToPdf(location, fileType);
			}else if(("true").equals(fileType)){
				toPdfPath = filePath;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return toPdfPath;
	}
	
	/**
	 * 移动端待收已收打开接口
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2017-5-16 上午11:52:09
	 */
	public void getAllTrueJson() {
		try {
			JSONObject jsonObject = getJSONObject();
			String recId = getRequest().getParameter("recId");
			if(jsonObject != null){
				// 待收办件id
				recId =(String) jsonObject.get("recId");
			}else{
				recId = getRequest().getParameter("recId");
			}
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
			ToRecDoc toRecDoc = toRecDocService.findToRecDocById(recId);
			String docguid = toRecDoc.getDocguid();
			if(toRecDoc!=null){
				List<ToRecDocAttachments> attList = toRecDocService.findToRecDocAttachmentsByDocguid(docguid);
				List<ToRecDocAttachmentSext> zwlist = toRecDocService.findToRecDocAttachmentSextByDocguid(docguid);
				//更新前置机附件
				updateToRecDocAtt(attList,zwlist);
				String serverUrl =  SystemParamConfigUtil.getParamValueByParam("mobileUrl")+getRequest().getContextPath();
				JSONArray jArr = new JSONArray();

				ToPdfUtil pdfUtil = new ToPdfUtil();
				List<RecShip> sattList = toRecDocService.getRecShipByRecId(docguid);
				for (RecShip recShip : sattList) {
					if(pdfUtil.isCebAndHaveSaveName_Rec(sattList,recShip)){
						JSONObject obj = new JSONObject();
						obj.put("id", recShip.getRecAttId());
						obj.put("name", recShip.getFileName());
						String newPdfPath = "";
						newPdfPath = recShip.getPdfPath();
						if(CommonUtil.stringNotNULL(newPdfPath)){
							newPdfPath = newPdfPath.substring(basePath.length(),newPdfPath.length());
						}
						String attPdfPath = serverUrl+ "/form/html/workflow/" + newPdfPath;
						obj.put("pdfPath", attPdfPath);
						obj.put("pageCount", recShip.getPageCount());
						obj.put("isSeal", "0");
						jArr.add(obj);
					}
				}
				
				TrueJSON trueJson = new TrueJSON();
				trueJson.setPdfurl(jArr.toString());
				
				String sb = "";
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("utf-8");
				// 打开流
				PrintWriter out = getResponse().getWriter();
				sb = JSONObject.fromObject(trueJson).toString();
				out.print(sb);
				out.close();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String clobToString(oracle.sql.CLOB  clob){
		String reString = "";
		Reader is;
		try {
			is = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while(s!=null){
				sb.append(s);
				s=br.readLine();
			}
			reString = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reString;
	}
	
	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
	
}
