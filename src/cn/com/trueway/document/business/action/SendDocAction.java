package cn.com.trueway.document.business.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.apache.commons.validator.GenericValidator;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.docxg.client.service.DocExchangeClient;
import cn.com.trueway.document.business.docxg.client.support.GenUserKey;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.service.FieldMatchingService;
import cn.com.trueway.document.business.service.ReceiveDocService;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.document.business.service.SendDocService;
import cn.com.trueway.document.business.util.AnalyzeWSXml;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.docNumberManager.service.DocNumberManagerService;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.document.component.taglib.comment.service.CommentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;

/**
 * 描述：发文业务<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-5 下午04:10:39<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
/**@COMPANY:西安睿通信息技术有限责任公司
 * @CLASS:SendDocAction
 * @DESCRIPTION:	
 * @AUTHOR:baifan
 * @VERSION:v1.0
 * @DATE:2012-8-24 上午10:23:13*/              
public class SendDocAction extends BaseAction {
	private static final long serialVersionUID = -2016873789410810008L;
	private Doc doc;
	private SelectTreeService selectTreeService;
	private SendDocService sendDocService;
	private DocExchangeClient docExchangeClient;
	private AttachmentService attachmentService;
	private ReceiveDocService receiveDocService;
	private DocNumberManagerService docNumberManagerService;
	private File docFile;
	private CommentService commentService;
	private FieldMatchingService fieldMatchingService;
	private ZwkjFormService zwkjFormService;
	private TableInfoService tableInfoService;
	private FlowService flowService ; 
	
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}
	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	public Doc getDoc() {
		return doc;
	}
	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	
	public SelectTreeService getSelectTreeService() {
		return selectTreeService;
	}
	public void setSelectTreeService(SelectTreeService selectTreeService) {
		this.selectTreeService = selectTreeService;
	}
	
	public SendDocService getSendDocService() {
		return sendDocService;
	}
	public void setSendDocService(SendDocService sendDocService) {
		this.sendDocService = sendDocService;
	}
	
	public DocExchangeClient getDocExchangeClient() {
		return docExchangeClient;
	}
	public void setDocExchangeClient(DocExchangeClient docExchangeClient) {
		this.docExchangeClient = docExchangeClient;
	}
	
	public File getDocFile() {
		return docFile;
	}
	public void setDocFile(File docFile) {
		this.docFile = docFile;
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
	
	public DocNumberManagerService getDocNumberManagerService() {
		return docNumberManagerService;
	}
	public void setDocNumberManagerService(
			DocNumberManagerService docNumberManagerService) {
		this.docNumberManagerService = docNumberManagerService;
	}
	
	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public FieldMatchingService getFieldMatchingService() {
		return fieldMatchingService;
	}
	public void setFieldMatchingService(FieldMatchingService fieldMatchingService) {
		this.fieldMatchingService = fieldMatchingService;
	}
	
	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}
	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}
	/**
	 * 描述：直接发文<br>
	 * void
	 * @throws Exception 
	 */
	public void directSendDoc() throws Exception{
		String instanceId = getRequest().getParameter("instanceId");
		//根据instanceId查出att对象和attext对象及doc对象
		Doc oldDoc=sendDocService.findFullDocByInstanceId(instanceId);
		oldDoc.setDocguid(instanceId);
		oldDoc.setInstanceId(instanceId);
		oldDoc = this.fullData(oldDoc);
		//解析文号
		//oldDoc = parseDN(oldDoc);
		if("no".equals(getRequest().getParameter("type"))){
			oldDoc = sendDocService.paserDocNum(oldDoc, oldDoc.getFwxh());
			oldDoc.setDocguid(UuidGenerator.generate36UUID());
			try {
				sendDocService.saveDoc(oldDoc);
				getResponse().sendRedirect("docNumberManager_dj.do?msg=success");
			} catch (Exception e) {
				getResponse().sendRedirect("docNumberManager_dj.do?msg=error");
				e.printStackTrace();
			}
		}else{
			oldDoc = sendDocService.paserDocNum(oldDoc, oldDoc.getFwxh());
			// 保存Doc对象,掉用接口向公文交换平台发送
			String result=sendDocService.sendDoc(oldDoc);
			getResponse().setCharacterEncoding("UTF-8");
			if(result.toUpperCase().indexOf(Constant.SUCCESS)>-1){
				getResponse().sendRedirect("send_directSendDocJsp.do?msg=success");
			}else{
				getResponse().sendRedirect("send_directSendDocJsp.do?msg=error");
			}
		}
	}
	
	/**
	 * 描述：发文时解析文号<br>
	 * void
	 */
	public Doc parseDN(Doc oldDoc){
		String docNum=doc.getFwxh();
		if(docNum!=null &&!"".equals(docNum)){
			String[] arr=docNum.split("\\[");
			oldDoc.setJgdz(arr[0]);
			String[] arr2=arr[1].split("\\]");
			oldDoc.setFwnh(arr2[0]);
			oldDoc.setFwxh(arr2[1].indexOf("号")>-1?arr2[1].substring(0,arr2[1].indexOf("号")):arr2[1]);
		}
		return  oldDoc;
	}
	/**
	 * 填充Doc的相关属性
	 * 
	 * @param oldDoc
	 */
	private Doc fullData(Doc oldDoc) {
		// 填充数据
		//doc = oldDoc;
		if (doc != null) {
			// 填充Doc的属性
			String xtoFull = doc.getXtoFull();
			String xccFull = doc.getXccFull();
			String xto = splitString(xtoFull, 1);
			String xcc = splitString(xccFull, 1);
			String xtoName = doc.getXtoName();
			if(xtoFull != null && !("").equals(xtoFull)){
				xtoName = splitString(xtoFull, 0);
			}
			String xccName = doc.getXccName();
			if(xccFull != null && !("").equals(xccFull)){
				xccName = splitString(xccFull, 0);
			}
			String bodyxml = "<root><docguid>" + doc.getDocguid()
					+ "</docguid><xto>" + xtoName + "</xto><xcc>" + xccName
					+ "</xcc><title>" + doc.getTitle()
					+ "</title><keyword>" + doc.getKeyword()
					+ "</keyword><type>" + doc.getDoctype()
					+ "</type><priority>" + doc.getPriority()
					+ "</priority></root>";
			oldDoc.setXtoName(xtoName);
			oldDoc.setXccName(xccName);
			oldDoc.setXto(xto);
			oldDoc.setXcc(xcc);
			oldDoc.setXtoFull(xtoFull);
			oldDoc.setXccFull(xccFull);
			oldDoc.setTitle(doc.getTitle());
			oldDoc.setKeyword(doc.getKeyword());
			oldDoc.setJgdz(doc.getJgdz());
			oldDoc.setFwnh(doc.getFwnh());
			oldDoc.setFwxh(doc.getFwxh());
			oldDoc.setFwjg(doc.getFwjg());
			oldDoc.setYfdw(doc.getYfdw());
			/*
			 * 判断QFR里面是否除了“,”没有其他数据
			 * 如果只有“,”设置为空，不是还设置原值
			 * */
			if(doc.getQfr()!=null){
				String qfrStr = doc.getQfr().replace(",","").trim();
				if(qfrStr!=null&&qfrStr.equals("")){
					oldDoc.setQfr("");
				}else{
					oldDoc.setQfr(doc.getQfr());
				}
			}else{
				oldDoc.setQfr(doc.getQfr());
			}
			oldDoc.setYfrq(doc.getYfrq());
			oldDoc.setYffs(doc.getYffs());
			oldDoc.setDoctype(doc.getDoctype());
			oldDoc.setCebid(doc.getCebid());
			oldDoc.setPriority(doc.getPriority());
			oldDoc.setSender(doc.getSender());
			oldDoc.setBodyxml(bodyxml);			
			oldDoc.setGkfw(doc.getGkfw());
			oldDoc.setHgr(doc.getHgr());
			oldDoc.setFzbhgr(doc.getFzbhgr());
			oldDoc.setRecord(doc.getRecord());
			oldDoc.setGzdbt(doc.getGzdbt());
			oldDoc.setGkfs(doc.getGkfs());
		   oldDoc.setSenderId(doc.getSenderId());//
			Employee employee=null;
			try {
				if(oldDoc.getNgr()==null || oldDoc.getNgr().equals("") || oldDoc.getNgrbm()==null || oldDoc.getNgrbm().equals("")){					
					//employee =(Employee)selectTreeService.findEmployeeById((String)getSession().getAttribute("userId"));
					employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
					oldDoc.setNgr(employee.getEmployeeName());
					Department dept=selectTreeService.findDepartmentById(employee.getDepartmentGuid());
					oldDoc.setNgrbm(dept.getDepartmentName());
				}			 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return oldDoc;
	}
	/**
	 * xtoFull、xccFull得到xto、xcc
	 * 
	 * @param oriStr
	 * @param index
	 *            0为单位名称，1为单位Id，2为发文份数，3为单位公章名称
	 * @return
	 */
	public String splitString(String oriStr, int index) {
		if (oriStr != null && oriStr.trim().length() > 0) {
			StringBuilder sb = new StringBuilder();
			String[] str = oriStr.split(";");
			for (int i = 0, l = str.length; i < l; i++) {
				sb.append(str[i].split("\\|")[index]);
				sb.append(";");
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 描述：已发列表<br>
	 *
	 * @return String
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public String sentDocList() throws DocumentException{  
		String departmentId = getRequest().getParameter("departmentIds");
		getRequest().setAttribute("departmentIds", departmentId);
		String colName = getRequest().getParameter("colName");
		String colValue = getRequest().getParameter("colValue");
		String type=getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		String listType=getRequest().getParameter("listType");
		List<String> deps = new ArrayList<String>();
		String userId="";
		if(departmentId!=null && !"".equals(departmentId)){
			deps.add(departmentId);
		}else if(type!=null && ("webid").equals(type)){
			deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		}else{
			String depId = (String) getSession().getAttribute(Constant.DEPARTMENT_ID);
			deps.add(depId);
		}
		if(listType!=null&&"userId".equals(listType)){
			userId=(String)getSession().getAttribute(Constant.USER_ID);
		}
		if(colName==null) colName="title";
		if(colValue==null) colValue="";
		//保存页码的参数的名称
		String pageIndexParamName = new ParamEncoder("element").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		getRequest().setAttribute("pageIndexParamName", pageIndexParamName);
		//获取页码，为分页查询做准备
		String pageNum = getRequest().getParameter(pageIndexParamName);
		int currentPage = GenericValidator.isBlankOrNull(pageNum) ? 1 : Integer.parseInt(pageNum);
		int pagesize=Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		//文号-标识
		String wh_bs=getRequest().getParameter("wh_bs");
		//文号-年号
		String wh_nh=getRequest().getParameter("wh_nh");
		//文号-序号
		String wh_xh=getRequest().getParameter("wh_xh");
		//发文号（标识+年号+序号） 如：%通政发%|%2012%|%001%
		String wh="%"+(wh_bs==null?"":wh_bs)+"%|"+"%"+(wh_nh==null?"":wh_nh)+"%|"+"%"+(wh_xh==null?"":wh_xh)+"%";
		DTPageBean dtPageBean=sendDocService.getSentDocList(currentPage, pagesize, deps, colName, colValue,wh, Constant.DOC_BOX_YIFA,userId);
		
		List<Doc> docs = (List<Doc>) dtPageBean.getDataList();
		List<String> docGuidList = new ArrayList<String>(); 
		if(docs!=null&& docs.size()!=0){
			for (Doc doc : docs) {
				Doc oldDoc = sendDocService.findFullDocByDocguid(doc.getDocguid());
				getRequest().setAttribute("doc", oldDoc);
				String docxg_docguid=oldDoc.getResultFlag();
				if(docxg_docguid==null){
					docxg_docguid = "";
				}
				docGuidList.add(docxg_docguid);
			}
		}
		String userKey=null;
		if(departmentId!=null && !"".equals(departmentId)){
			userKey = GenUserKey.genUserKey(departmentId);
		}else{
			userKey = GenUserKey.genUserKey(deps.get(0));
		}
//		//读取所发部门的收取状态
//		String status = docExchangeClient.getDocStatusByDocguidList(userKey, docGuidList); //格式：id,status;id2.,status2;...
//		List<Doc> docList = new ArrayList<Doc>();
//		if(docs!=null&&docs.size()!=0&&status!=null&&!("").equals(status)&&(status.indexOf("验证没有通过")<0)){
//			Doc doces = new Doc();
//			for (int i = 0; i < docs.size(); i++) {
//				doces = docs.get(i);	//取出每篇文
//				String st = status.split(";")[i].split(",")[1];   //取出每篇文的状态
//				if(!("null").equals(st)&&(st!=null)){
//					doces.setSendStatus(status.split(";")[i].split(",")[1]); //给每篇文的状态赋值
//				}
//				docList.add(doces);
//			}
//		}
//		if(docList!=null&&docList.size()!=0){
//			getRequest().setAttribute("sendedList", docList);
//		}else{
//			getRequest().setAttribute("sendedList", dtPageBean.getDataList());
//		}
		//读取所发部门的收取状态
		String status = "1";
		List<Doc> docList = new ArrayList<Doc>();
		if(docs!=null&&docs.size()!=0&&status!=null&&!("").equals(status)&&(status.indexOf("验证没有通过")<0)){
			Doc doces = new Doc();
			for (int i = 0; i < docs.size(); i++) {
				doces = docs.get(i);	//取出每篇文
				String st = "1";   //取出每篇文的状态
				if(!("null").equals(st)&&(st!=null)){
					doces.setSendStatus("1"); //给每篇文的状态赋值
				}
				docList.add(doces);
			}
		}
		if(docList!=null&&docList.size()!=0){
			getRequest().setAttribute("sendedList", docList);
		}else{
			getRequest().setAttribute("sendedList", dtPageBean.getDataList());
		}
		getRequest().setAttribute("size", dtPageBean.getTotalRows());
		getRequest().setAttribute("pages",dtPageBean.getTotalPages());
		getRequest().setAttribute("colName",colName);
		getRequest().setAttribute("colValue",colValue);
		getRequest().setAttribute("wh_bs",wh_bs);
		getRequest().setAttribute("wh_nh",wh_nh);
		getRequest().setAttribute("wh_xh",wh_xh);
		
		//部门列表
		List<Department> departments = new ArrayList<Department>();
		deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		for(String id : deps){
			departments.add(selectTreeService.findDepartmentById(id));
		}
		getRequest().setAttribute("deps", departments);
		if(departmentId!=null && !"".equals(departmentId)){
			getRequest().setAttribute("deptName", selectTreeService.findDepartmentById(departmentId).getDepartmentName());
		}else{
			getRequest().setAttribute("deptName", "全部");
		}
//		List<Define> defs = workflowDefineService.findAllDefines(deps);
//		String sendDefine = null;
//		for(Define d : defs){
//			if(Constant.DEFINE_TYPE_SEND.equals(d.getDefineType())){
//				sendDefine = d.getWfUid();
//			}
//		}
		deps = null;
		//发文文号模型
//		if(sendDefine != null){
//			List<DocNumberModel> dnModelList=docNumberManagerService.findDocNumberModel(sendDefine);
//			for (DocNumberModel m:dnModelList) {
//				try {
//					m.setContent((m.getContent()==null?"":m.getContent().split(",")[0]));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			getRequest().setAttribute("dnModelList", dnModelList);
//		}
		if(listType!=null&&"userId".equals(listType)){
			return "sentDocList4Myself";
		}
		return "sentDocListJsp";
	}
	
	/**
	 * 描述：已发列表详细页面<br>
	 *
	 * @return
	 * @throws DocumentException String
	 */
	@SuppressWarnings("unchecked")
	public String sentDocDetail() throws DocumentException{
		String docguid = getRequest().getParameter("docguid");
		Doc oldDoc=sendDocService.findFullDocByDocguid(docguid);
		getRequest().setAttribute("doc", oldDoc);
		String docxg_docguid=oldDoc.getResultFlag();
		String departmentId = getRequest().getParameter("departmentId");
		String userKey=null;
		if(departmentId!=null && !"".equals(departmentId)){
			userKey = GenUserKey.genUserKey(departmentId);
		}
		String xml = docExchangeClient.getDocStatusByDocguid(userKey, docxg_docguid);
		List<Doc> docStatusList=new ArrayList<Doc>();
		//FAILEDINFO
		if (xml.indexOf("FAILEDINFO") >-1) {
			System.out.println("接收发文状态失败，docxg_docguid:"+docxg_docguid+".");
		} else {
			org.dom4j.Document doc = DocumentHelper.parseText(xml);  
			Element root = doc.getRootElement();
			List<Element> elems=root.elements("DOCSTATUSINFO");
			if(elems.size()>0){
				for(Element e:elems){
					Doc docStatus=new Doc();
					docStatus.setXto(e.elementText("XTO"));
					docStatus.setXtoName(e.elementText("XTONAME"));
					docStatus.setStatus(e.elementText("STATUS"));
					docStatus.setYfrq(e.elementText("OPERATETIME"));
					docStatusList.add(docStatus);
				}
			}
			System.out.println("接收发文状态成功，docxg_docguid:"+docxg_docguid+".");
		}
		getRequest().setAttribute("docStatusList", docStatusList);
		if(docStatusList.size()>0){
			getRequest().setAttribute("pageSize", docStatusList.size());
		}else{
			getRequest().setAttribute("pageSize", 10);
		}
		getRequest().setAttribute("recordSize", docStatusList.size());
		getRequest().setAttribute("size", docStatusList.size());
		getRequest().setAttribute("docxg_docguid", docxg_docguid);
		getRequest().setAttribute("departmentId", departmentId);
		getRequest().setAttribute("pages", 1);
		return "sentDocDetailJsp";
	}
	
	/**
	 * 
	 * 描述：批量上传
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午08:26:18
	 */
	public void batchUpload(){
		String attextIds = getRequest().getParameter("attextIds");
		String commentIds = getRequest().getParameter("commentIds");
		String docguid = getRequest().getParameter("docguid");
		sendDocService.addAttsextForBatchUpload(docguid,attextIds,commentIds);
	}
	
	
	/**
	 * 
	 * 描述：上传办文单doc文件
	 *
	 * @throws IOException void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-11 下午03:07:09
	 */
	public void uploadBwdDocFile() throws IOException {
		if(docFile!=null&&docFile.length()>0){
			String filename = "a.doc";
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String dstPath = FileUploadUtils.getRealFilePath(filename,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
			File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
			FileUploadUtils.copy(docFile, dstFile);
			PrintWriter out = getResponse().getWriter();
			out.print(dstPath);
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述：编办办文中启动发文流程 保存办文单到发文的附件中
	 * @throws IOException void
	 * 作者:WangXF<br>
	 * 创建时间:2012-5-24 下午04:21:43
	 */
	public void saveBwdForSend() throws IOException {
		String docguid = getRequest().getParameter("docguid");
		String fileName = getRequest().getParameter("fileName");
		if(docFile!=null&&docFile.length()>0&&fileName!=null&&fileName.length()!=0&&docguid!=null&&docguid.length()!=0){
			String newDocguid = UuidGenerator.generate36UUID();
			String filename = fileName+".doc";
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String dstPath = FileUploadUtils.getRealFilePath(filename,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
			File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
			FileUploadUtils.copy(docFile, dstFile);
			
			List<SendAttachmentsext> attsextList = new ArrayList<SendAttachmentsext>();
			//办文单附件
			SendAttachmentsext bwdExt = new SendAttachmentsext();
			bwdExt.setDocguid(newDocguid);
			bwdExt.setFileindex(0l);
			bwdExt.setFilename(filename);
			bwdExt.setFilesize(docFile.length());
			bwdExt.setFiletime(new Date());
			bwdExt.setFiletype("doc");
			bwdExt.setLocalation(dstPath);
			attsextList.add(bwdExt);
			List<ReceiveAttachments> recAttList = attachmentService.findAllReceiveAtts(docguid);
			//该文的正文附件
			for (ReceiveAttachments receiveAttachments : recAttList) {
				dstPath = FileUploadUtils.getRealFilePath(filename,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
				FileUploadUtils.copy(new File(basePath + receiveAttachments.getLocalation()), new File(basePath + dstPath));
				SendAttachmentsext attext = new SendAttachmentsext();
				attext.setDocguid(newDocguid);
				attext.setFileindex(receiveAttachments.getFileindex());
				attext.setFilename(receiveAttachments.getFilename());
				attext.setFilesize(receiveAttachments.getFilesize());
				attext.setFiletime(new Date());
				attext.setFiletype(receiveAttachments.getFiletype());
				attext.setLocalation(dstPath);
				attsextList.add(attext);
			}
			//该文的附加附件
			List<ReceiveAttachmentsext>  recAttextList = attachmentService.findAllReceiveAttsext(docguid);
			for (ReceiveAttachmentsext receiveAttachmentsext : recAttextList) {
				dstPath = FileUploadUtils.getRealFilePath(filename,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
				FileUploadUtils.copy(new File(basePath + receiveAttachmentsext.getLocalation()), new File(basePath + dstPath));
				SendAttachmentsext attext = new SendAttachmentsext();
				attext.setDocguid(newDocguid);
				attext.setFileindex(receiveAttachmentsext.getFileindex());
				attext.setFilename(receiveAttachmentsext.getFilename());
				attext.setFilesize(receiveAttachmentsext.getFilesize());
				attext.setFiletime(new Date());
				attext.setFiletype(receiveAttachmentsext.getFiletype());
				attext.setLocalation(dstPath);
				attsextList.add(attext);
			}
			attachmentService.addSendAttsext(attsextList);
			PrintWriter out = getResponse().getWriter();
			out.print(newDocguid);
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述：上传办文单ceb文件并删除原doc文件
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-11 下午03:08:43
	 */
	public void uploadBwdCebFile(){
		try {
			String docLocation = getRequest().getParameter("docLocation");
			String sendDocguid = getRequest().getParameter("sendDocguid");
			String name = getRequest().getParameter("name");
			String saveToWhere = getRequest().getParameter("saveToWhere");
			String uploadfilename ="";
			if(name!= null){
				uploadfilename=name + ".ceb";
			}
			String dstPath = "";
			if(docLocation!=null){
				dstPath = docLocation.substring(0, docLocation.lastIndexOf(".")) + ".ceb";
			}
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
			String filepath = basePath + dstPath;
			//CEB文件上传操作
			ServletInputStream inStream = getRequest().getInputStream(); // 取HTTP请求流
			File file = new File(filepath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
			int rc = 0;
			while ((rc = inStream.read(buff, 0, 100)) > 0){
				fileOutputStream.write(buff, 0, rc);
			}
			fileOutputStream.close();
			inStream.close();
			getResponse().setContentType("text/xml");
			getResponse().setCharacterEncoding("GBK");
			//入库操作
			if(saveToWhere!=null&&saveToWhere.equals("toAtt")){
				SendAttachments att=  new SendAttachments();
				att.setId(null);
				att.setDocguid(sendDocguid);
				att.setFiletype("ceb");
				att.setFilename(uploadfilename);
				att.setFilesize(file.length());
				att.setLocalation(dstPath);
				att.setFiletime(new Timestamp(new Date().getTime()));
				attachmentService.addSendAtts(att);

			}else if(saveToWhere!=null&&saveToWhere.equals("toAttext")){
				SendAttachmentsext attsext=  new SendAttachmentsext();
				attsext.setId(null);
				attsext.setDocguid(sendDocguid);
				attsext.setFiletype("ceb");
				attsext.setFilename(uploadfilename);
				attsext.setFilesize(file.length());
				attsext.setLocalation(dstPath);
				attsext.setFiletime(new Timestamp(new Date().getTime()));
				attachmentService.addSendAttsext(attsext);
			}
			PrintWriter out = getResponse().getWriter();
			out.write(uploadfilename);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<SendAttachmentsext> fullSendAttachmentsext(List<SendAttachmentsext> allAttsext,RecDoc recDoc){
		List<ReceiveAttachments> attsList =recDoc.getAtts();  
		for (ReceiveAttachments att:attsList) {
			allAttsext.add(this.fullSendAttachmentsext(att));
		}
		List<ReceiveAttachmentsext> attsextList = recDoc.getAttsext();
		for (ReceiveAttachmentsext attext:attsextList) {
			allAttsext.add(this.fullSendAttachmentsext(attext));
		}
		return allAttsext;
	}
	
	/**
	 *  
	 * 描述：得到附加附件对象 
	 *
	 * @param attrs
	 * @return SendAttachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午04:47:51
	 */
	private SendAttachmentsext fullSendAttachmentsext(Object attObj){
		String id="";
		String douguid="";
		String name="";
		String type="";
		Long size=0L;
		String location="";
		if(attObj instanceof ReceiveAttachments){
			ReceiveAttachments att = (ReceiveAttachments)attObj;
			id=att.getId();
			douguid=att.getDocguid();
			name=att.getFilename();
			type=att.getFiletype();
			size=att.getFilesize();
			location=att.getLocalation();
		}
		if(attObj instanceof ReceiveAttachmentsext){
			ReceiveAttachmentsext att = (ReceiveAttachmentsext)attObj;
			id=att.getId();
			douguid=att.getDocguid();
			name=att.getFilename();
			type=att.getFiletype();
			size=att.getFilesize();
			location=att.getLocalation();
		}
		SendAttachmentsext attsext = new SendAttachmentsext();
		attsext.setId(id);
		attsext.setDocguid(douguid);
		attsext.setFilename(name);
		attsext.setFiletype(type);
		attsext.setFilesize(size);
		attsext.setLocalation(location);
		attsext.setFiletime(new Date());
		attsext.setFileindex(0L);
		return attsext;
	}
	
	/**
	 * 
	 * 描述：发文单列表<br>
	 *
	 * @return String
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-5-31 下午12:12:43
	 */
	@SuppressWarnings("unchecked")
	public String sendwd(){
		String wh = getRequest().getParameter("wh");
		String title = getRequest().getParameter("title");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		
		List<String> deptIdList = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		if(deptIdList==null || deptIdList.isEmpty() ){
			String depId = (String) getSession().getAttribute(Constant.DEPARTMENT_ID);
			deptIdList.add(depId);
		}
//		List<Define> defines=workflowDefineService.findAllDefines(deptIdList);
		
		//保存页码的参数的名称
		String pageIndexParamName = new ParamEncoder("element").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		getRequest().setAttribute("pageIndexParamName", pageIndexParamName);
		//获取页码，为分页查询做准备
		String pageNum = getRequest().getParameter(pageIndexParamName);
		int currentPage = GenericValidator.isBlankOrNull(pageNum) ? 1 : Integer.parseInt(pageNum);
		
		int pageSize=Integer.valueOf(SystemParamConfigUtil.getParamValueByParam("pagesize"));//每页显示多少行
		
//		DTPageBean sendProcess = sendDocService.getSendwd(defines, title, wh, startTime, endTime, currentPage, pageSize);
		
//		List<DocBw> sendList =  (List<DocBw>)sendProcess.getDataList();
//		int totalRows = sendProcess.getTotalRows();//一共多少行数据
//		int totalPages = sendProcess.getTotalPages();//一共多少页
//		getRequest().setAttribute("processList",sendList);
//		getRequest().setAttribute("totalRows",totalRows);
//		getRequest().setAttribute("totalPages",totalPages);
		getRequest().setAttribute("pageSize",pageSize);
		getRequest().setAttribute("wh",wh);
		getRequest().setAttribute("title",title);
		getRequest().setAttribute("startTime",startTime);
		getRequest().setAttribute("endTime",endTime);
		String listType = getRequest().getParameter("listType");
		if("manage".equals(listType)){
			getRequest().setAttribute("listType",listType);
		}
		return "sendwd";
	}
	
	public void checkbw() throws IOException{
		String wh = getRequest().getParameter("wh");
		String title = getRequest().getParameter("title");
		String webId=getRequest().getParameter("webId");
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			if(webId==null||"".equals(webId.trim())){
				webId=(String)getSession().getAttribute("webId");
			}
			String str = sendDocService.checkbw(wh,title,webId);
			out.print(str);
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if(out != null) out.close();
		}
	}
	
	
	
	/**
	 * 发送
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void checkSendDoc() throws IOException{
		String workFlowId = getRequest().getParameter("workFlowId");
		String instanceId = getRequest().getParameter("instanceId");
		String formId = getRequest().getParameter("formId");
		//创建一个临时类
		Doc doc = new Doc();//为了和交换平台交互信息
		doc.setSender(((List<String>)getSession().getAttribute(MyConstants.DEPARMENT_IDS)).get(0).toString());
		doc.setInstanceId(instanceId);
		doc.setFormid(formId);
		
		
		//获取字段的匹配关系
		String itemId = getRequest().getParameter("itemId");
		List<DocxgFieldMap>  maplist  = fieldMatchingService.getDocxgFieldMapList(itemId);
		
		//获取匹配的value值
		DocxgFieldMap map = null;
		String tableName = "";
		String fieldName = "";
		String tagName = "";
		List<String> tagList = new ArrayList<String>();
		tagList.add("xtoname");
		tagList.add("xccname");
		tagList.add("xto");
		tagList.add("xcc");
		tagList.add("title");
		tagList.add("keyword");
		tagList.add("doctype");
		tagList.add("priority");
		tagList.add("yfdw");
		tagList.add("qfr");
		tagList.add("priority");
		tagList.add("yfrq");
		Map<String, String> fmap = new HashMap<String, String>();
		List<String> tableNameList = new ArrayList<String>();
		for(int i=0; i<maplist.size() ; i++){
			map = maplist.get(i);
			fieldName = map.getFieldName();
			tableName = map.getTableName();
			tagName = map.getTagName();
			for(int j=0; j<tagList.size(); j++){
				String gwjhfield = tagList.get(j);
				if(tagName.equals(gwjhfield)){	//需要到数据库中查询数据
					if(!tableNameList.contains(tableName)){
						tableNameList.add(tableName);
					}
					fmap.put(fieldName+";"+tagName, tableName);
					break;
				}
			}
		}
		
		Map<String,String> valueMap = new HashMap<String,String> ();
		
		if(tableNameList!=null && tableNameList.size()>0){
			for(int i=0; i<tableNameList.size(); i++){
				tableName = tableNameList.get(i);
				List<Map> mapList = zwkjFormService.findTableByFormId(tableName, formId, instanceId);
				for(String key: fmap.keySet()){
					String field = key.split(";")[0];
					String tag = key.split(";")[1];
					String tab = fmap.get(key);
					if(tab.equals(tableName)){
						//遍历map
						for(int j=0; j<mapList.size(); j++){
							Map relaship = mapList.get(j);
							//遍历map
							String value = "";
							try{
								 value = (String) relaship.get(field);	//可以为数据库字段
							}catch (Exception e) {
								//clob字段
								value = tableInfoService.getClob(tableName, instanceId, field ,formId);
							}
							//relaship
							valueMap.put(tag, value);
						}
					}
				}
			}
		}
		
		String title = valueMap.get("title");
		String xtoName = valueMap.get("xtoName");
		String xccName = valueMap.get("xccname");
		String xtoFull = valueMap.get("xto");
		String xccFull = valueMap.get("xco");
		String keyword = valueMap.get("keyword");
		String doctype = valueMap.get("doctype");
		String priority = valueMap.get("priority");
		if(xtoFull != null && !("").equals(xtoFull)){
			xtoName = splitDepIdAndName(xtoFull, 1);
		}
		if(xccFull != null && !("").equals(xccFull)){
			xccName = splitDepIdAndName(xccFull, 1);
		}
		doc.setXtoName(xtoName);
		doc.setXccName(xccName);
		doc.setXto(splitDepIdAndName(xtoFull, 0));
		doc.setXcc(splitDepIdAndName(xccFull, 0));
		doc.setTitle(title);
		doc.setKeyword(keyword);
		doc.setDoctype(doctype);
		//doc.setPriority(Long.parseLong(CommonUtil.stringIsNULL(priority)?"0":priority));
		//取文号--start--
		DocNumberWhFw docNumberWhFw = docNumberManagerService.findDocNumWhFw(workFlowId,instanceId);
		doc.setJgdz(docNumberWhFw.getJgdz());
		doc.setFwnh(docNumberWhFw.getFwnh());
		doc.setFwxh(docNumberWhFw.getFwxh());
		//取文号---end---
		doc.setYfdw(getRequest().getParameter("yfdw")); 
		doc.setQfr(getRequest().getParameter("qfr"));
		String yffs = getRequest().getParameter("priority");
		doc.setYffs(Long.parseLong(CommonUtil.stringIsNULL(yffs)?"0":yffs));
		doc.setYfrq(getRequest().getParameter("yfrq"));
		String docguid = UuidGenerator.generate36UUID();
		doc.setDocguid(docguid);
			String bodyxml = "<root><docguid>" + docguid
				+ "</docguid><xto>" + xtoName + "</xto><xcc>" + xccName
				+ "</xcc><title>" + getRequest().getParameter("title")
				+ "</title><keyword>" + getRequest().getParameter("keyword")
				+ "</keyword><type>" + getRequest().getParameter("doctype")
				+ "</type><priority>" + getRequest().getParameter("priority")
				+ "</priority></root>";
		doc.setBodyxml(bodyxml);
		List<SendAttachments> sendAttachmentList = attachmentService.findSendAttsByDocguid(instanceId+"attzw");
		sendAttachmentList.addAll(attachmentService.findSendAttsByDocguid(instanceId+"fj"));
		doc.setAtts(sendAttachmentList);
		// 发文
		getResponse().setCharacterEncoding("UTF-8");
		//String result = sendDocService.sendDoc(doc);
		String result = "";
		sendDocService.saveDoc(doc);
		/*if (result.contains(Constant.FAIL)){
			getResponse().getWriter().print("no");
		} else{
			getResponse().getWriter().print("yes");
		}*/
		try {
			SendDocToGwjhpt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String splitDepIdAndName(String oriStr, int index) {
		if (oriStr != null && oriStr.trim().length() > 0) {
			StringBuilder sb = new StringBuilder();
			String[] str = oriStr.split("[*]");
			sb.append(str[index]);
			sb.append(";");
			return sb.toString();
		}
		return "";
	}
	
	
	/**
	 * 发送数据到公文交换平台
	 */
	public void SendDocToGwjhpt() throws Exception{
		String instanceId = getRequest().getParameter("instanceId");
		//根据instanceId查出att对象和attext对象及doc对象
		Doc oldDoc=sendDocService.findFullDocByInstanceId(instanceId);
		
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		
		//签发人员id与姓名
		Map<String,String> mapbak = new HashMap<String,String>();
		mapbak.put("userId", employee.getEmployeeGuid());
		mapbak.put("userName", employee.getEmployeeName());
		
		//发送机构
		oldDoc.setDocguid(instanceId);
		oldDoc.setInstanceId(instanceId);
		oldDoc = this.fullData(oldDoc);
		
		oldDoc = sendDocService.paserDocNum(oldDoc, oldDoc.getFwxh());
		// 保存Doc对象,掉用接口向公文交换平台发送
		String docInfoXml = AnalyzeWSXml.getSendToGwjhptXml(oldDoc,mapbak);
		String userKey = "UWtaQk9ERXhSVUV0TURBd01DMHdNREF3TFRRMU5UY3RPVGhFTmpBd01EQXdPRGd4ZlE9PSU9VSQl";
		String result = docExchangeClient.sendDoc(userKey,docInfoXml);
		if(result.toUpperCase().indexOf(Constant.SUCCESS)>-1){
			//接收成功
			
			
		}else{
			//接收失败
			
			
		}
	}
}
