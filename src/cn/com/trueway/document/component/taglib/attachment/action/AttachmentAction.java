package cn.com.trueway.document.component.taglib.attachment.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.AESPlus;
import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.ExcelToPdf;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.TiffToMPdf;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.model.po.Attachmentsext_type;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttHistoryLog;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsHistory;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.document.component.taglib.attachment.thread.AttachmentThread;
import cn.com.trueway.document.component.taglib.attachment.util.File2String;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfOnlineEditStatus;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TemplateService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.util.DocToPdf;
import cn.com.trueway.workflow.set.util.ImageToPdf;
import cn.com.trueway.workflow.set.util.PdfPage;
import cn.com.trueway.workflow.set.util.TaskEntity;
import cn.com.trueway.workflow.set.util.TaskPoolManager;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * 
 * 描述：提供关于附件操作的方法<br>
 * 作者：WangXF<br>
 * 创建时间：2011-11-29 上午10:42:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>sh
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class AttachmentAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentAction.class);

	private static final long serialVersionUID = 1L;
	
	private final String ATT_SUFFIX_NAME 	= SystemParamConfigUtil.getParamValueByParam("attSuffixName"); 
	
	private File file; 
	private String fileFileName; 
	private String fileContentType;
	private String docguid;
	private String tagid;
	private String nodeId;
	private FlowService flowService ; 
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private AttachmentService attachmentService;
	
	private TableInfoService  tableInfoService;
	
	private TemplateService templateService;

	//word图标路径
	private String WORD_ICON_PATH="/widgets/component/taglib/attachment/imgs/word-new.gif";
	//word在线编辑图标路径
	private String WORDTOEDIT_ICON_PATH="/widgets/component/taglib/attachment/imgs/onEditLine-new.png";
	//word查看历史图标路径
	private String WORDTOHIS_ICON_PATH="/widgets/component/taglib/attachment/imgs/seeHistory-new.png";
	//带红章打印图标路径
	private String PRINTOFREDCAP_ICON_PATH="/widgets/component/taglib/attachment/imgs/printofredcap.png";
	//脱密图标路径
	private String UNLOCK_ICON_PATH="/widgets/component/taglib/attachment/imgs/unlock.png";
	
	private String STAMP_ICON_PATH="/widgets/component/taglib/attachment/imgs/seal.jpg";
	//转ceb图标路径
	private String WORDTOCEB_ICON_PATH="/widgets/component/taglib/attachment/imgs/wordtoceb.png";
	//删除按钮的图片
	private String DELETE_ICON_PATH="/widgets/component/taglib/attachment/imgs/delete-new.png";
	//下载按钮的图片
	private String DOWNLOAD_ICON_PATH="/widgets/component/taglib/attachment/imgs/dow-new.png";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//下载按钮的图片
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	public String getDocguid() {
		return docguid;
	}
	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}
	
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}
	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}
	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	public String test(){
		return "test";
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public TemplateService getTemplateService() {
		return templateService;
	}
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	/**
	 * 
	 * 描述：根据标签属性显示正文附件内容<br>
	 * 
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午04:54:39
	 */
	public String showAtts() throws IOException {
		//得到标签属性参数Start
		Boolean deleteAble = Boolean.valueOf(getRequest().getParameter("deleteAble"));
		Boolean downloadAble = Boolean.valueOf(getRequest().getParameter("downloadAble"));
		Boolean previewAble = Boolean.valueOf(getRequest().getParameter("previewAble"));
		Boolean tocebAble = Boolean.valueOf(getRequest().getParameter("tocebAble"));
		Boolean toStampAble = Boolean.valueOf(getRequest().getParameter("toStampAble"));
		String printStampName = getRequest().getParameter("printStampName");
		Boolean detachStampAble = Boolean.valueOf(getRequest().getParameter("detachStampAble"));
		String openBtnClass = getRequest().getParameter("openBtnClass");
		String otherBtnsClass = getRequest().getParameter("otherBtnsClass");
		String showId = getRequest().getParameter("showId");
		Boolean isReciveAtt = Boolean.valueOf(getRequest().getParameter("isReciveAtt"));
		Boolean onlineEditAble = Boolean.valueOf(getRequest().getParameter("onlineEditAble"));
		String nodeId = getRequest().getParameter("nodeId");
		String isFirst = getRequest().getParameter("isFirst");
		WfNode wfNode = null;
		if(nodeId!=null && !nodeId.equals("")){
			wfNode = workflowBasicFlowService.getWfNode(nodeId);
		}
		boolean sfqg = false;	//是否清稿
		if(wfNode!=null){
			Integer allowfair = wfNode.getWfn_allowfair();
			if(allowfair!=null && allowfair==1){
				sfqg = true;
			}
			
			Integer isSeal = StringUtils.isNotBlank(wfNode.getWfn_isseal())?Integer.parseInt(wfNode.getWfn_isseal()):0;
			if(isSeal.equals(1)){
				toStampAble=true;
			}else{
				toStampAble=false;
			}
		}
		boolean isman= true;
		//得到标签属性参数End
		List<SendAttachments> attsList= attachmentService.findAllSendAtts(docguid,null);
		if(attsList==null){
			return null;
		}
		StringBuffer attsInfo = this.getAttInfoNew(deleteAble, downloadAble, previewAble,onlineEditAble,tocebAble, toStampAble, printStampName, detachStampAble, openBtnClass, otherBtnsClass, showId, isReciveAtt, attsList, isman, sfqg, nodeId,isFirst);
		HttpServletResponse response = getResponse();
		response.setContentType("text/xml;UTF-8");
		response.setContentType("<MEAT HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"/>");    
		PrintWriter out = response.getWriter();
		out.print(attsInfo.toString());
		return null;
	}
	
	/**
	 * 
	 * 描述：根据标签属性显示附加附件内容<br>
	 * 
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午04:54:39
	 */
	public String showAttsext() throws IOException {
		//得到标签属性参数Start
		Boolean deleteAble = Boolean.valueOf(getRequest().getParameter("deleteAble"));
		Boolean downloadAble = Boolean.valueOf(getRequest().getParameter("downloadAble"));
		Boolean previewAble = Boolean.valueOf(getRequest().getParameter("previewAble"));
		Boolean tocebAble = Boolean.valueOf(getRequest().getParameter("tocebAble"));
		Boolean toStampAble = Boolean.valueOf(getRequest().getParameter("toStampAble"));
		String printStampName = getRequest().getParameter("printStampName");
		Boolean detachStampAble = Boolean.valueOf(getRequest().getParameter("detachStampAble"));
		String openBtnClass = getRequest().getParameter("openBtnClass");
		String otherBtnsClass = getRequest().getParameter("otherBtnsClass");
		String showId = getRequest().getParameter("showId");
		Boolean isReciveAtt = Boolean.valueOf(getRequest().getParameter("isReciveAtt"));
		String nodeId = getRequest().getParameter("nodeId");
		String isFirst = getRequest().getParameter("isFirst");
		WfNode wfNode = null;
		if(nodeId!=null && !nodeId.equals("")){
			wfNode = workflowBasicFlowService.getWfNode(nodeId);
		}
		boolean sfqg = false;	//是否清稿
		if(wfNode!=null){
			Integer allowfair = wfNode.getWfn_allowfair();
			if(allowfair!=null && allowfair==1){
				sfqg = true;
			}
		}
		boolean isman= false;
		//得到标签属性参数End
		
		List attsList=null;
		//根据附件是发文还是收文，进行不同的操作
		if(isReciveAtt){
			attsList =attachmentService.findAllReceiveAttsext(docguid);
		}else{
			attsList =attachmentService.findAllSendAttsext(docguid);
		}
		if(attsList==null){
			return null;
		}
		StringBuffer attsInfo = this.getAttInfo(deleteAble, downloadAble, previewAble, false,tocebAble,
				toStampAble, printStampName, detachStampAble, openBtnClass, otherBtnsClass, 
				showId, isReciveAtt, attsList, isman, sfqg, nodeId,isFirst);
		HttpServletResponse response = getResponse();
		response.setContentType("text/xml;UTF-8");
		response.setContentType("<MEAT HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"/>");    
		PrintWriter out = response.getWriter();
		out.print(attsInfo.toString());
		return null;
	}
	
	public String openAttsDialog(){
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		getRequest().setAttribute("isReciveAtt",isReciveAtt);
		//得到所有的附件的类型
		List<Attachmentsext_type> attsextTypeList = attachmentService.findAllAttType();
		getRequest().setAttribute("attsextTypeList",attsextTypeList);
		return "uploadAtts";
	}
	
	public String openAttsextDialog(){
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		getRequest().setAttribute("isReciveAtt",isReciveAtt);
		nodeId = getRequest().getParameter("nodeId");
		getRequest().setAttribute("nodeId",nodeId);
		//得到所有的附件的类型
		List<Attachmentsext_type> attsextTypeList = attachmentService.findAllAttType();
		getRequest().setAttribute("attsextTypeList",attsextTypeList);
		return "uploadAttsext";
	}
	
	/**
	 * 打开新上传控件界面
	 * @return
	 */
	public String openWebUploader(){
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		getRequest().setAttribute("isReciveAtt",isReciveAtt);
		nodeId = getRequest().getParameter("nodeId");
		getRequest().setAttribute("nodeId",nodeId);
		String docguId = getRequest().getParameter("docguid");
		if(StringUtils.isNotBlank(docguId) && docguId.indexOf("attzw")>-1){
			getRequest().setAttribute("isZw", "true");
		}
		//得到所有的附件的类型
		List<Attachmentsext_type> attsextTypeList = attachmentService.findAllAttType();
		getRequest().setAttribute("attsextTypeList",attsextTypeList);
		return "openWebUploader";
	}
	
	/**
	 * 
	 * @return
	 */
	public String openCaptureDialog(){
		String nodeId = getRequest().getParameter("nodeId");
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		getRequest().setAttribute("isReciveAtt",isReciveAtt);
		String maxFileSize = SystemParamConfigUtil.getParamValueByParam("maxFileSize"); 
		//得到所有的附件的类型
		String docguid = getRequest().getParameter("docguid");
		String sslx = "";
		if(docguid!=null && !docguid.equals("")){
			String docg = docguid.toLowerCase();
			if(docg.contains("zjclatt")){
				sslx = "zjclatt";
			}else if(docg.contains("wsclatt")){
				sslx = "wsclatt";
			}
		}
//		List<AttachFileType> attsextTypeList = attachmentTypeService.findAttachFileTypeList(sslx);
//		getRequest().setAttribute("attsextTypeList",attsextTypeList);
		getRequest().setAttribute("maxFileSize",maxFileSize);
		getRequest().setAttribute("nodeId",nodeId);
		getRequest().setAttribute("sslx", sslx);
	//	return "fzcaptureAtt"; // 方正型号的高拍仪
		return "captureAtt"; // 其它型号
	}
	
	/**
	 * 
	 * 描述：上传正文
	 *
	 * @return
	 * @throws IOException
	 * @throws DocumentException String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:01:03
	 */
	public String uploadAtts() throws IOException, DocumentException{
		String fileSequence = getRequest().getParameter("fileSequence");
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		File attsFile = this.getFile(); // 要上传的文件
		String uploadfilename = this.getFileFileName(); // 上传文件的真实文件名
		String content = getRequest().getParameter("content");
		String dates = getRequest().getParameter("dates");
		String dateStr = "";
		Integer uploadIndex = 0;
		//上传附件的顺序
		if(CommonUtil.stringNotNULL(fileSequence)){
		    String[] fileSeqs = fileSequence.split(",");
		    int fileSeqsLen = fileSeqs.length;
		    for(int i = 0; i < fileSeqsLen; i++){
			if(CommonUtil.stringNotNULL(uploadfilename) && uploadfilename.equals(fileSeqs[i])){
			    uploadIndex = i + 1;
			    break;
			}
		    }
		}
		if(content!=null && !content.equals("")){
			String[] cont = content.split(",");
			String[] dat = dates.split(",");
			for(int i=0; i<cont.length; i++){
				if(uploadfilename.equals(cont[i])){
					dateStr = dat[i];
				}
			}
		}
		Date date = null;
		if(dateStr!=null && !dateStr.equals("")){
			long time = Long.parseLong(dateStr);
			date = new Date(time);
			//System.out.println(uploadfilename+"----"+date);
		}
		String title = uploadfilename;
		String docType = getRequest().getParameter("type");
		String[] fjlxs = SystemParamConfigUtil.getParamValueByParam("fjlx").split(",");
		boolean flag=false;
		for(int i=0;i<fjlxs.length;i++){
			if(title!=null&&title.toLowerCase().endsWith(fjlxs[i].toLowerCase())){
				flag=true;
			};
		}
		if(!flag){
			return null;
		}
		String realfilename="";
		if (null != attsFile && attsFile.exists()&&uploadfilename!=null){
			if(isReciveAtt!=null&&"true".equals(isReciveAtt)){
				//手动导入时上传正文
				realfilename=this.uploadReceiveAtts(attsFile,uploadfilename);
			}else if(isReciveAtt!=null&&"false".equals(isReciveAtt)){
				//发文时上传正文
				realfilename=this.uploadSendAtts(attsFile,uploadfilename,title,docType,date, uploadIndex,"");
			}
			getResponse().setContentType("text/xml");
			getResponse().setCharacterEncoding("GBK");
			PrintWriter out = getResponse().getWriter();
			out.write(realfilename);
		}
		/*//为实现套打图片 ，需要在工程目录先上传附件图片至工程中
		if (realfilename!=null&&!realfilename.equals("")) {
			realfilename=realfilename.split("/")[4];
		}
		String jarEnvAllUrl=PathUtil.getWebRoot()+"tempfile/"+realfilename;//需要复制到运行环境中的 位置
		MyUtils.copy(attsFile, new File(jarEnvAllUrl));// 完成上传文件，就是将本地文件复制到服务器上
*/		return null;
	}
	
	public void uploadScanNew()
	  {
	    InputStream input = null;
	    OutputStream output = null;
	    PrintWriter out = null;
	    JSONObject json = new JSONObject();
	    try {
	      out = getResponse().getWriter();
	      ServletInputStream stream = getRequest().getInputStream();
	      byte[] fileByte = IOUtils.toByteArray(stream);

	      if (fileByte != null) {
//	          byte[] fileBytes = Tools.decodeBase64ToBytes(base64Str);

	          String nodeId = getRequest().getParameter("nodeId");
	          String docguid = getRequest().getParameter("docguid");
	          String title = getRequest().getParameter("title");
	          title = URLDecoder.decode(title,"UTF-8");

	          if (!title.endsWith(".jpg"))
	          {
	            title = title + ".jpg";
	          }
	          String attType = getRequest().getParameter("type");
	          attType = attType == null ? "" : attType;
	          attType = URLDecoder.decode(attType,"UTF-8");	
	          if(cn.com.trueway.base.util.CommonUtil.stringIsNULL(attType)){
	        	  attType = "正文材料";
	          }
	          Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
	          String basePath = SystemParamConfigUtil.getParamValueByParam("filepath");
	          String dstPath = createFileFromByte(fileByte, title);
	          File attsFile = new File(basePath + dstPath);
	          Long length = null;
	          if (attsFile.exists()) {
	            length = Long.valueOf(attsFile.length());
	          }
	          SendAttachments atts = new SendAttachments();
	          String userId = emp.getEmployeeGuid();
	          String userName = emp.getEmployeeName();
	          atts.setEditer(userId + ";" + userName);
	          atts.setTitle(title);
	          atts.setNodeId(nodeId);
	          atts.setDocguid(docguid);
	          atts.setFiletime(new Timestamp(new Date().getTime()));
	          atts.setFilename(title);
	          atts.setFileindex(Long.valueOf(0L));
	          atts.setFiletype("jpg");
	          atts.setFilesize(length);
	          atts.setLocalation(dstPath);
	          atts.setType(attType);
	          this.attachmentService.addSendAtts(atts);
	      }
	      json.put("state", "0");
	      out.print(json);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      IOUtils.closeQuietly(input);
	      IOUtils.closeQuietly(output);
	    }
	  }
	
	/**
	 * 
	 * 描述：发文时的上传正文
	 *
	 * @param attsFile
	 * @param uploadfilename void
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:01:18
	 */
	private String uploadSendAtts(File attsFile, String uploadfilename,String title,String type,Date date, Integer uploadIndex,String temId){
		String fileType=FileUploadUtils.getExtension(uploadfilename);
		fileType = fileType.toLowerCase();
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
		SendAttachments atts = new SendAttachments();
		atts.setDocguid(docguid);
		List<SendAttachments> existAttList = attachmentService.findAllSendAtts(docguid);
		int existSize = 0;
		if(existAttList != null && existAttList.size() > 0){
			existSize = existAttList.size();
		}
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String userName = emp.getEmployeeName();
		atts.setEditer(userId+";"+userName);
		if(uploadIndex != null){
		    atts.setUploadIndex(uploadIndex + existSize);
		}
		ToPdfUtil pdfUtil = new ToPdfUtil();
		if(fileType.equals("tif")){
			uploadfilename = uploadfilename.substring(0,uploadfilename.lastIndexOf("."))+".pdf";
			dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			File docFile = new File(basePath+dstPath);
			try {
				docFile = TiffToMPdf.tifftopdf(attsFile, docFile);
				atts.setFilename(uploadfilename);// 设置文件名属性
				atts.setFiletype("pdf");// 设置文件类型(后缀名)的属性
				atts.setFileindex(0L);
				atts.setFilesize(docFile.length());// 设置文件大小的属性
				atts.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
				atts.setTitle(title);// 设置上传附件所属类别
				atts.setType(type);// 设置上传附件标题
				atts.setNodeId(nodeId);//设置上传附件的节点ID
				if(date != null){
					atts.setFiletime(date);// 设置上传时间属性
				}else{
					atts.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(date != null){
				atts.setFiletime(date);// 设置上传时间属性
			}else{
				atts.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
			}
			FileUploadUtils.copy(attsFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
			try {
				atts.setData(Hibernate.createBlob(new FileInputStream(dstFile)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			atts.setFilename(uploadfilename);// 设置文件名属性
			atts.setFileindex(0L);
			atts.setFiletype(fileType);// 设置文件类型(后缀名)的属性
			atts.setFilesize(attsFile.length());// 设置文件大小的属性
			atts.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
			atts.setTitle(title);// 设置上传附件所属类别
			atts.setType(type);// 设置上传附件标题
			atts.setNodeId(nodeId);//设置上传附件的节点ID
		}	
		
		if(fileType.equals("doc")|| fileType.equals("docx")){
			String sourceFilePath = basePath + dstPath;
			String destinPDFFilePath = "";
			if(fileType.equals("doc")){
				destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 4)+".pdf";
			}else if(fileType.equals("docx")){
				destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 5)+".pdf";
			}
			atts.setTopdfpath(destinPDFFilePath);
			TaskEntity msg = null;
	        Map<String, String> params = null;
	        params = new HashMap<String, String>();
	        params.put("sourceFilePath", basePath + dstPath);
	        params.put("destinPDFFilePath", destinPDFFilePath);
	        msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
			TaskPoolManager.newInstance().addTask(msg);
			File pdfFile = new File(destinPDFFilePath);
			do {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!pdfFile.exists());
			int pdfCount = PdfPage.getPdfPage(destinPDFFilePath);
			atts.setPagecount(pdfCount);
		}else if(fileType.equals("xls")|| fileType.equals("xlsx")){
			try {
				String path = pdfUtil.xlsToPdf(dstPath, fileType, "", "");
				atts.setTopdfpath(path);
				File pdfFile = new File(path);
				do {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (!pdfFile.exists());
				int pdfCount = PdfPage.getPdfPage(path);
				atts.setPagecount(pdfCount);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if(fileType.equals("ceb") || fileType.equals("cebx")){
			CebToPdf cp = new CebToPdf();
			// 文件路径
			cp.cebToPdf(basePath + dstPath);
			String cebPath = basePath + dstPath;
			String path = "";
			if(fileType.equals("ceb")){
				path = cebPath.substring(0,cebPath.length() - 3)+"pdf";
			}else if(fileType.equals("cebx")){
				path = cebPath.substring(0,cebPath.length() - 4)+"pdf";
			}
			File pdfFile = new File(path);
			int i = 0;
			do {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(i>10){
					break;
				}
				i++;
			} while (!pdfFile.exists());
			if(pdfFile.exists()){
				int pdfCount = PdfPage.getPdfPage(path);
				atts.setPagecount(pdfCount);
				atts.setTopdfpath(path);
			}
			
		}else if(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("jpeg") || fileType.equals("bmp")){
			String path = pdfUtil.imgToPdf(dstPath, fileType);
			atts.setTopdfpath(path);
			File pdfFile = new File(path);
			do {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!pdfFile.exists());
			int pdfCount = PdfPage.getPdfPage(path);
			atts.setPagecount(pdfCount);
		}else if(fileType.equals("pdf")){
			String path = basePath + dstPath;
			atts.setTopdfpath(path);
			File pdfFile = new File(path);
			if(pdfFile.exists()){
				try {
					int pdfCount = PdfPage.getPdfPage(path);
					atts.setPagecount(pdfCount);
				} catch (Exception e) {
					atts.setPagecount(0);
				}
			}
		}
		try {
			System.out.println("---1----"+new Date());
			if(StringUtils.isNotBlank(atts.getTopdfpath())){
				atts.setPdfData(Hibernate.createBlob(new FileInputStream(new File(atts.getTopdfpath()))));
			}
			System.out.println("---2----"+new Date());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//正文模板要多存temid字段，用于自动带值
		if(StringUtils.isNotBlank(temId)){
			atts.setTemId(temId);
		}
		SendAttachments sAtt =	attachmentService.addSendAtts(atts);
		
		//判断文件的大小：filesize，定义基准线长度 ;2M大小
		Long limit = (long) (2*1024*1024);
		if(atts!=null && atts.getFilesize()>limit){	 //此处文件需要切割
			System.out.println("限制大小-------"+limit);
			System.out.println("原文大小-------"+atts.getFilesize());
			saveCutFiles(sAtt);
		}
		return sAtt.getId();
	}
	
	
	public String saveCutFiles(SendAttachments sAtt){
		if(sAtt!=null){
			Integer pageCount = sAtt.getPagecount();
			Integer pages = pageCount/5;		
			Integer left = pageCount%5;
			Integer total = 0;			//合计文件个数
			if(left!=null && left!=0){
				total = pages+1;
			}else{
				total= pages;
			}
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String filepath = sAtt.getTopdfpath();
			File existFile = new File(filepath);
			String newFile = "";
			try{
				if(existFile.exists()){
					CutPages entity = null;
					for(int i=1; i<=total; i++){
						newFile = FileUploadUtils.getRealFilePath("test.pdf", basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
						Integer startPage = (i-1)*5+1;
						Integer endPage = i*5;
						if(endPage>pageCount){
							endPage = pageCount;
						}
						boolean isNeewPwd = partitionPdfFile(filepath, basePath+newFile,startPage, endPage);
						if (isNeewPwd) {
							continue;
						}
						entity = new CutPages();
						entity.setDocId(sAtt.getId());
						entity.setPageCount(endPage-startPage+1);
						entity.setStartPage(startPage);
						entity.setEndPage(endPage);
						entity.setFilepath(newFile);
						entity.setFileSize(new File(basePath+newFile).length());
						try {
							entity.setPdfData(Hibernate.createBlob(new FileInputStream(new File(basePath+newFile))));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						entity.setSort(i);
						attachmentService.saveCutPages(entity);
					}
				}else{
					return "10001";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return "10000";
		}else{
			return "10002";
		}
	}
	
	/** 
     * 截取pdfFile的第from页至第end页，组成一个新的文件名 
    * @param pdfFile 
     * @param subfileName 
     * @param from 
     * @param end 
     */  
   @SuppressWarnings("finally")
public boolean partitionPdfFile(String pdfFile,  
          String newFile, int from, int end) {  
       Document document = null;  
       PdfCopy copy = null;          
       try {  
            PdfReader reader = new PdfReader(pdfFile);            
            int n = reader.getNumberOfPages();            
            if(end==0){  
                end = n;  
            }  
            ArrayList<String> savepaths = new ArrayList<String>();  
            String staticpath = pdfFile.substring(0, pdfFile.lastIndexOf("\\")+1);  
            String savepath = staticpath+ newFile;  
            savepaths.add(savepath);  
            document = new Document(reader.getPageSize(1));  
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));  
            document.open();  
            for(int j=from; j<=end; j++) {  
                document.newPage();   
                PdfImportedPage page = copy.getImportedPage(reader, j);  
                copy.addPage(page);  
            }  
            document.close();  
       }catch (IOException e) {  
            e.printStackTrace();  
       }catch(DocumentException e) {  
           e.printStackTrace();  
       }finally{
    	   if(copy!=null){
    		   copy.close();
    	   }
    	   if(document!=null){
    		   document.close();
    		   return false;
    	   }else{
    		   return true;
    	   }
       }
    }  
	
   
	/**
	 * 描述：判断此节点是否上传附件
	 * @author xiep
	 */
	public void isAttachExistByNode(){
		String nodeId = getRequest().getParameter("nodeId");
		String instanceId = getRequest().getParameter("instanceId");
		String type = getRequest().getParameter("type");
		List<SendAttachments> attachList = new ArrayList<SendAttachments>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("nodeId", nodeId);
		map.put("instanceId", instanceId);
		map.put("type", type);
		attachList = attachmentService.getAttachListByNode(map);
		String result = "fail";
		if(attachList != null && attachList.size() > 0){
			result = "success";
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述：手动导入时上传正文
	 *
	 * @param attsFile
	 * @param uploadfilename void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:01:46
	 */
	private String uploadReceiveAtts(File attsFile, String uploadfilename){
		String fileType=FileUploadUtils.getExtension(uploadfilename);
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
		ReceiveAttachments atts = new ReceiveAttachments();
		atts.setDocguid(docguid);
		if(fileType.toUpperCase().equals("TIF")){
			uploadfilename = uploadfilename.substring(0,uploadfilename.lastIndexOf("."))+".pdf";
			/*uploadfilename = uploadfilename.substring(0,uploadfilename.lastIndexOf("."))+".doc";*/
			dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			File docFile = new File(basePath+dstPath);
			try {
				docFile = TiffToMPdf.tifftopdf(attsFile, docFile);
				atts.setFilename(uploadfilename);// 设置文件名属性
				atts.setFiletype("pdf");// 设置文件类型(后缀名)的属性
				atts.setFileindex(0L);
				atts.setFilesize(docFile.length());// 设置文件大小的属性
				atts.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
				atts.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			atts.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
			FileUploadUtils.copy(attsFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
//			atts.setData(Hibernate.createBlob(new FileInputStream(dstFile)));
			atts.setFilename(uploadfilename);// 设置文件名属性
			atts.setFileindex(0L);
			atts.setFiletype(fileType);// 设置文件类型(后缀名)的属性
			atts.setFilesize(attsFile.length());// 设置文件大小的属性
			atts.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
		}
		attachmentService.addReceiveAtts(atts);
		return atts.getId();
	}
	
	/**
	 * 
	 * 描述：在线编辑时保存正文
	 * @return
	 * @throws IOException
	 * @throws DocumentException String
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:11:32
	 */
	public String saveAttHistory() throws IOException, DocumentException{
		System.out.println("saveAttHistory>>>>begin"+new Date());
		File attsFile = this.getFile(); // 要上传的文件
		if (null != attsFile && attsFile.exists()){
			System.out.println("saveAttHistory>>>>1"+new Date());
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
			String attId = getRequest().getParameter("attId");
			SendAttachments att = attachmentService.findSendAtts(attId);
			String fileType = att.getFiletype();
			String dstPath = FileUploadUtils.getRealFilePath(att.getLocalation(), basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
			FileUploadUtils.copy(attsFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
			String sourceFilePath = basePath + dstPath;
			Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);

			if(emp==null){
				String userId = getRequest().getParameter("userId");
				if(userId!=null && !userId.equals("")){
					emp = tableInfoService.findEmpByUserId(userId);
				}
			}
			
			SendAttHistoryLog sendAttHistoryLog = new SendAttHistoryLog();
			sendAttHistoryLog.setAttId(attId);
			sendAttHistoryLog.setUserId(emp.getEmployeeGuid());
			sendAttHistoryLog.setDocguid(att.getDocguid());
			sendAttHistoryLog.setEditTime(new Date());
			attachmentService.addSendHistoryLog(sendAttHistoryLog);
			
			
			String docguid = att.getDocguid();
			Date nowTime = new Date();
			//如果为第一次编辑附件,将原始信息保存到历史表中
			saveOriginalHistroy(docguid, attId, att);
			SendAttachmentsHistory attHistory = new SendAttachmentsHistory();
			//获取当前登录用户
			String userName = "";
			if(null != emp){
				userName = emp.getEmployeeName();
			}
			attHistory.setDocguid(docguid);
			attHistory.setEditer(att.getEditer()); 
			attHistory.setFileindex(att.getFileindex());
			attHistory.setFiletype(att.getFiletype());
			attHistory.setFilename(att.getFilename());
			attHistory.setFjid(attId);
			attHistory.setEditer(userName);
			attHistory.setLocalation(dstPath);
			attHistory.setFiletime(nowTime);
			attHistory.setFilesize(attsFile.length());
			//判断是否有编辑---用于pdf领导签批意见后重新生成正文,意见需不显示，下次签而又要显示的标识位判断
			attHistory.setIsHaveEdit("1");
			attHistory.setAttflow(Hibernate.createBlob(new FileInputStream(dstFile)));
			attachmentService.addSendAttHistory(attHistory);
//			att.setFiletime(nowTime);
			att.setLocalation(dstPath);
			att.setFilesize(attsFile.length());
			attachmentService.updateSendAtt(att);
			
			WfOnlineEditStatus wfOnlineEditStatus = attachmentService.findOESByInfo(attId, "");
			if(wfOnlineEditStatus!=null){
				wfOnlineEditStatus.setFileStatus("0");
				attachmentService.updateWfOnlineEditStatus(wfOnlineEditStatus);
			}
			

			long startTime = System.currentTimeMillis();
			long endTime ;
			String destinPDFFilePath = "";
			if(fileType.equals("doc")){
				destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 4)+".pdf";
			}else if(fileType.equals("docx")){
				destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 5)+".pdf";
			}
			if(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("jpeg") || fileType.equals("bmp")){
//				String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
				ToPdfUtil pdfUtil = new ToPdfUtil();
				String pdfPath = att.getLocalation();
//				pdfPath = pdfPath.substring(0, pdfPath.length()-fileType.length())+"pdf";
				String path = pdfUtil.imgToPdf(pdfPath, fileType);
				System.out.println("-------移动端发起生成的pdf文件------："+path);
				att.setTopdfpath(path);
				File pdfFile = new File(path);
				do {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();                        
					}
				} while (!pdfFile.exists());
				int pdfCount = PdfPage.getPdfPage(path);
				att.setPagecount(pdfCount);
				attachmentService.updateSendAtt(att);
			}else{
				TaskEntity msg = null;
		        Map<String, String> params = null;
		        params = new HashMap<String, String>();
		        params.put("sourceFilePath", sourceFilePath);
		        params.put("destinPDFFilePath", destinPDFFilePath);
		        msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
				TaskPoolManager.newInstance().addTask(msg);
				endTime = System.currentTimeMillis();
				System.out.println("---转换正文需要4444----"+(endTime-startTime)/1000.0);
				File pdfFile = new File(destinPDFFilePath);
				endTime = System.currentTimeMillis();
				System.out.println("---转换正文需要3333----"+(endTime-startTime)/1000.0);
				do {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (!pdfFile.exists());
				endTime = System.currentTimeMillis();
				System.out.println("---转换正文需要11111----"+(endTime-startTime)/1000.0);
				int pdfCount = PdfPage.getPdfPage(destinPDFFilePath);
				
				endTime = System.currentTimeMillis();
				System.out.println("---转换正文需要22222----"+(endTime-startTime)/1000.0);
				att.setTopdfpath(destinPDFFilePath);
				att.setPagecount(pdfCount);
				att.setFilesize(pdfFile.length());
				
				attachmentService.updateSendAtt(att);
				
				//删掉切割文件，重新生成
				List<CutPages> cutList = attachmentService.findCutPagesListByDocId(att.getId());
				if(null != cutList && cutList.size()>0){
					for (CutPages cutPages : cutList) {
						attachmentService.deleteCutPages(cutPages);
					}
				}
				Long limit = (long) (2*1024*1024);
				if(att.getFilesize()>limit){	 //此处文件需要切割
					System.out.println("限制大小-------"+limit);
					System.out.println("原文大小-------"+att.getFilesize());
					saveCutFiles(att);
				}
			}
			endTime = System.currentTimeMillis();
			System.out.println("---转换正文需要----"+(endTime-startTime)/1000.0);
		
			toPage("success");
//			AttachmentThread thread = new AttachmentThread(attachmentService, sourceFilePath, fileType, att, attsFile);
//			thread.start();
		}else{
			System.out.println("saveAttHistory>>>>文件为null"+new Date());
			toPage("10001");//文件为null
		}
		System.out.println("saveAttHistory>>>>begin"+new Date());
		return null;
	}
	
	/**
	 * 保存原始附件数据到历史表中
	 * @param docguid	
	 * @param attId
	 * @param att
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void saveOriginalHistroy(String docguid,
			String attId, SendAttachments att) throws FileNotFoundException, IOException{
		List<SendAttachmentsHistory> attHistoryList=attachmentService.findAllSendAttHistory(docguid,"");
		if(attHistoryList==null || attHistoryList.size()==0){
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
			File dstFile = new File(basePath + att.getLocalation());
			SendAttachmentsHistory attHistory = new SendAttachmentsHistory();
			attHistory.setDocguid(docguid);
			attHistory.setEditer(att.getEditer()); 
			attHistory.setFileindex(att.getFileindex());
			attHistory.setFiletype(att.getFiletype());
			attHistory.setFilename(att.getFilename());
			attHistory.setFjid(attId);
			attHistory.setEditer(att.getEditer());
			attHistory.setLocalation(att.getLocalation());
			attHistory.setFiletime(att.getFiletime());
			attHistory.setFilesize(att.getFilesize());
			attHistory.setAttflow(Hibernate.createBlob(new FileInputStream(dstFile)));
			//判断是否有编辑---用于pdf领导签批意见后重新生成正文,意见需不显示，下次签而又要显示的标识位判断
			attHistory.setIsHaveEdit("1");
			attachmentService.addSendAttHistory(attHistory);
		}
	}
	
	/**
	 * 
	 * 描述：上传附件
	 *
	 * @return
	 * @throws IOException String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:11:53
	 */
	public String uploadAttsext() throws IOException{
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		String title = getRequest().getParameter("title");
		String docType = getRequest().getParameter("type");
		File attsextFile = this.getFile(); // 要上传的文件
		String uploadfilename = this.getFileFileName(); // 上传文件的真实文件名
		if (null != attsextFile && attsextFile.exists()&&uploadfilename!=null){
			if(isReciveAtt!=null&&"true".equals(isReciveAtt)){
				//手动导入时上传附加附件
				this.uploadReceiveAttsext(attsextFile, uploadfilename);
			}else if(isReciveAtt!=null&&"false".equals(isReciveAtt)){
				//发文时上传附加附件
				this.uploadSendAttsext(attsextFile, uploadfilename,title,docType);
			}
			getResponse().setContentType("text/xml");
        	getResponse().setCharacterEncoding("GBK");
        	PrintWriter out = getResponse().getWriter();
        	out.write(uploadfilename);
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：发文时上传附加附件
	 *
	 * @param attsextFile
	 * @param uploadfilename void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:17:39
	 */
	private void uploadSendAttsext(File attsextFile,String uploadfilename,String title,String docType){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		SendAttachmentsext attsext = new SendAttachmentsext();
		attsext.setDocguid(docguid);
		attsext.setFileindex(0L);
		attsext.setFilename(uploadfilename);// 设置文件名属性
		attsext.setFiletype(FileUploadUtils.getExtension(uploadfilename));// 设置文件类型(后缀名)的属性
		attsext.setFilesize(attsextFile.length());// 设置文件大小的属性
		attsext.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
		attsext.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
		attsext.setTitle(title);// 设置上传附件所属类别
		attsext.setType(docType);// 设置上传附件标题
		File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
		attachmentService.addSendAttsext(attsext);
		try{
			FileUploadUtils.copy(attsextFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
		}catch (Exception e) {
			e.printStackTrace();
			attachmentService.deleteAttsext(attsext.getId(), false);
		}
	}
	
	/**
	 * 
	 * 描述：手动导入时上传附加附件
	 *
	 * @param attsextFile
	 * @param uploadfilename void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:17:39
	 */
	private void uploadReceiveAttsext(File attsextFile,String uploadfilename){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		ReceiveAttachmentsext attsext = new ReceiveAttachmentsext();
		attsext.setDocguid(docguid);
		attsext.setFileindex(0L);
		attsext.setFilename(uploadfilename);// 设置文件名属性
		attsext.setFiletype(FileUploadUtils.getExtension(uploadfilename));// 设置文件类型(后缀名)的属性
		attsext.setFilesize(attsextFile.length());// 设置文件大小的属性
		attsext.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
		attsext.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
		File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
		attachmentService.addReceiveAttsext(attsext);
		try{
			FileUploadUtils.copy(attsextFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
		}catch (Exception e) {
			e.printStackTrace();
			attachmentService.deleteAttsext(attsext.getId(), true);
		}
	}
	
	/**
	 * 
	 * 描述：word转成CEB文件后和CEB文件盖章后 CEB文件的导入，只在发文的时候有
	 * void
	 *
	 * 作者:王雪峰<br>
	 * 创建时间:2011-11-8 下午03:19:13
	 */
	public void uploadCEB(){
		try{
			//原附件的ID
			String fileId = getRequest().getParameter("fileId");
			//是否是正文附件
			String isManAttStr=getRequest().getParameter("isManAtt");
			if(isManAttStr==null){
				throw new Exception("参数isManAtt不能为空！");
			}
			Boolean isManAtt = Boolean.valueOf(isManAttStr);
			
			if(isManAtt){
				SendAttachments atts=  attachmentService.findSendAtts(fileId);
				if(atts==null){
					throw new Exception("uploadCEB时没有查找到附件");
				}
				String fileName = atts.getFilename();
				String fileType = atts.getFiletype();
				String uploadfilename = fileName.substring(0, fileName.lastIndexOf(".")) + ".ceb";
				String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
				String dstPath = FileUploadUtils.getRealFilePath(new Date().getTime()+".ceb", basePath, Constant.UPLOAD_FILE_PATH);
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
				PrintWriter out = getResponse().getWriter();
				out.write(uploadfilename);
				//入库操作
				atts.setId(null);
				atts.setFiletype("ceb");
				atts.setFilename(uploadfilename);
				atts.setFilesize(file.length());
				atts.setLocalation(dstPath);
				atts.setFiletime(new Timestamp(new Date().getTime()));
				if(fileType!=null&&fileType.equals("ceb")){
					attachmentService.addCebAtt(atts, isManAtt, fileId);
				}else{
					attachmentService.addCebAtt(atts, isManAtt, null);
				}
			}else{
				SendAttachmentsext attsext=  attachmentService.findSendAttsext(fileId);
				if(attsext==null){
					LOGGER.debug("uploadCEB时没有查找到附件");
					return;
				}
				String fileName = attsext.getFilename();
				String fileType = attsext.getFiletype();
				String uploadfilename = fileName.substring(0, fileName.lastIndexOf(".")) + ".ceb";
				String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
				String dstPath = FileUploadUtils.getRealFilePath(new Date().getTime()+".ceb", basePath, "ATTACACHMENT");

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
				PrintWriter out = getResponse().getWriter();
				out.write(uploadfilename);
				//入库操作
				attsext.setId(null);
				attsext.setFiletype("ceb");
				attsext.setFilename(uploadfilename);
				attsext.setFilesize(file.length());
				attsext.setLocalation(dstPath);
				attsext.setFiletime(new Timestamp(new Date().getTime()));
				if(fileType!=null&&fileType.equals("ceb")){
					attachmentService.addCebAtt(attsext, isManAtt, fileId);
				}else{
					attachmentService.addCebAtt(attsext, isManAtt, null);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 描述：删除正文附件
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 下午01:27:44
	 */
	public String removeAtts(){
//		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		String attsId= getRequest().getParameter("attsId");
//		if(isReciveAtt !=null && "true".equals(isReciveAtt)){
			//删除收文中的正文附件
			attachmentService.deleteAtts(attsId, true);
//		}else if(isReciveAtt !=null && "false".equals(isReciveAtt)){
//			//删除发文中的正文附件
//			attachmentService.deleteAtts(attsId, false);
//		}
		return null;
	}
	
	/**
	 * 
	 * 描述：修改正文附件(跳转)
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 下午01:27:44
	 */
	public String toModifyAtts(){
		String attsId= getRequest().getParameter("attsId");
		SendAttachments sendAtt = attachmentService.modifyAtts(attsId);
		getRequest().setAttribute("sendAtt", sendAtt);
		//得到所有的附件的类型
		List<Attachmentsext_type> attsextTypeList = attachmentService.findAllAttType();
		getRequest().setAttribute("attsextTypeList",attsextTypeList);
		return "toModifyAtts";
	}
	
	/**
	 * 
	 * 描述：修改正文附件
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 下午01:27:44
	 * @throws IOException 
	 */
	public void modifyAtts() throws IOException{
		String attsId= getRequest().getParameter("attId");
		String title= getRequest().getParameter("title");
		String type= getRequest().getParameter("type");
		attachmentService.modifyOfAtts(attsId,title,type);
		getResponse().getWriter().print("修改成功！");
	}
	
	/**
	 * 
	 * 描述：删除附加附件
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 下午01:28:01
	 */
	public String removeAttsext(){
		String isReciveAtt = getRequest().getParameter("isReciveAtt");
		String attsextId= getRequest().getParameter("attsextId");
		if(isReciveAtt !=null && "true".equals(isReciveAtt)){
			//删除收文中的附加附件
			attachmentService.deleteAttsext(attsextId, true);
		}else if(isReciveAtt !=null && "false".equals(isReciveAtt)){
			//删除发文中的附加附件
			attachmentService.deleteAttsext(attsextId, false);
		}
		return null;
	}
	
	/**
	 * 
	 * 描述 ：检查文件是否存在
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-15 下午02:32:51
	 * @throws IOException 
	 */
	public void checkFileExist() throws IOException{
		String location =getRequest().getParameter("location");
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String fileNameWithPath = pdfRoot+location;
		File  downFile = new File(fileNameWithPath);
		if(downFile.exists()){
			toPage("yes");
		}else{
			String attId = getRequest().getParameter("attId");
			if(StringUtils.isNotBlank(attId)){
				SendAttachments att = attachmentService.findSendAtts(attId);
				if(null != att){
					FileUtils.byteArrayToFile(att,attachmentService);
					File file = new File(pdfRoot+att.getLocalation());
					if(file.exists()){
						toPage("yes");
					}else{
						toPage("no");
					}
				}else{
					toPage("no");
				}
			}else{
				toPage("no");
			}
		}
	}

	
	/**
	 * 
	 * 描述：通用下载方法
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:54:58
	 * @throws IOException 
	 */
	public void download() throws IOException{
		String fileName = getRequest().getParameter("name");
		if(StringUtils.isNotBlank(fileName)){
			fileName = URLDecoder.decode(fileName,"UTF-8");
		}
		LOGGER.info("fileName:{}",fileName);
		String location =getRequest().getParameter("location");
		String isabsolute = getRequest().getParameter("isabsolute");
		String fileId = getRequest().getParameter("fileId");
		String fileNameWithPath = "";
		try {
			if(StringUtils.isNotBlank(fileId)){
				SendAttachments att = attachmentService.findSendAtts(fileId);
				if(null != att){
					System.out.println("-----进入download方法断点1-------");
					fileName = att.getFilename();
					FileUtils.byteArrayToFile(att, attachmentService);
				}else{
					System.out.println("-----进入download方法断点2-------");
					WfTemplate template = templateService.getTemplateById(fileId);
					if(null != template){
						Blob blob = template.getAttflow();
						String realpath = PathUtil.getWebRoot();
						String path = template.getVc_path();
						if(!new File(realpath+"tempfile/"+path).exists()){
							FileUtils.byteArrayToFile(blob, realpath+"tempfile/"+path);
						}
					}
				}
			}
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			if(isabsolute!=null && isabsolute.equals("1")){
				fileNameWithPath = location;
			}else{
				fileNameWithPath = pdfRoot+location;
			}
			file = new File(fileNameWithPath);
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setContentLength((int) l);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			LOGGER.error("下载失败,文件路径为："+fileNameWithPath, e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * 描述：通用下载方法
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:54:58
	 * @throws IOException 
	 */
	public void downloadTure() throws IOException{
		String fileName = getRequest().getParameter("name");
		String location =getRequest().getParameter("location");
		String basePath = SystemParamConfigUtil.getParamValueByParam("filePath");
		String isabsolute = getRequest().getParameter("isabsolute");
		String fileNameWithPath =basePath+location;
		if(isabsolute!=null && isabsolute.equals("1")){
			fileNameWithPath = location;
		}
		try {
			// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
			if(fileName!=null&&fileName.trim().length()>0){
				fileName = URLEncoder.encode(fileName, "GB2312");
				fileName = URLDecoder.decode(fileName, "ISO8859-1");
			}else{
				fileName = "a."+FileUploadUtils.getExtension(location).toLowerCase();
			}
			File file = new File(fileNameWithPath);
			if(!file.exists()){
				String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
				fileNameWithPath = fileNameWithPath.replace(basePath, pdfRoot);
				file = new File(fileNameWithPath);
			}
			FileInputStream fileinputstream = new FileInputStream(file);
			byte abyte0[] = new byte[fileinputstream.available()];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
			fileinputstream.read(abyte0, 0, fileinputstream.available());
			byte[] datas = AESPlus.decrypt(abyte0);
			if(!new String(datas,0,4).equals("true")){
				getResponse().getOutputStream().write(abyte0, 0, fileinputstream.available());
			}
			int len = Utils.getIntValue(datas,4,8);
			String json =new String(datas,8,len);
			JSONArray jsonArray =JSONArray.fromObject(json);
			for(int z=0;z<jsonArray.size();z++){
				JSONObject jsonObject = jsonArray.getJSONObject(z);
				Object obj= jsonObject.get("stamps");
				JSONArray jArray =null;
				if(obj!=null){
					jArray=(JSONArray) obj;
				}
				if(jArray!=null){
					for(int i=0;i<jArray.size();i++){
						JSONObject jObject = jArray.getJSONObject(i);
						jObject.put("stamp_color", 0);
					}
				}
			}
			byte[] data = Utils.getByteValue(jsonArray.toString().getBytes().length);
			data =Utils.addByte("true".getBytes(), data);
			data = Utils.addByte(data,jsonArray.toString().getBytes());
			data =Utils.addByte(data,Utils.cutByte(datas,(8+len),(datas.length-(8+len))));
			data =AESPlus.encrypt(data);
			getResponse().setContentLength(data.length);
			getResponse().getOutputStream().write(data, 0, data.length);
			fileinputstream.close();
		} catch (IOException e) {
			LOGGER.error("下载失败,文件路径为："+fileNameWithPath, e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 描述：格式化文件大小 得到对应的（BT,KB,MB,GB,TG）<br>
	 *
	 * @param filesize
	 * @return String
	 *
	 * 作者:王雪峰<br>
	 * 创建时间:2011-10-29 上午10:24:53
	 */
	private String getFormateFileSize(Long filesize){
		//单位
		final int M = 1024;
		//保留小数位
		final int N = 2;
		double KB = filesize/M;
		if(KB<1){
			return filesize+"Byte";
		}
		double MB = KB/M;
		if(MB<1){
			return new BigDecimal(String.valueOf(KB)).setScale(N, BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
		}
		double GB = MB/M;
		if(GB<1){
			return new BigDecimal(String.valueOf(MB)).setScale(N, BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
		}
		double TB = GB/M;
		if(TB<1){
			return new BigDecimal(String.valueOf(GB)).setScale(N, BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
		}
		return new BigDecimal(String.valueOf(TB)).setScale(N, BigDecimal.ROUND_HALF_UP).toPlainString()+"TB";
	}
	
	/**
	 * 盖章打印接口
	 * @return String
	 *
	 * 创建时间:2013-9-10 下午06:17:07
	 */
	public String printStampJson(){
		String toStampAble = getRequest().getParameter("toStampAble");		//是否可以盖章
		String tocebAble = getRequest().getParameter("tocebAble");			//是否可以转ceb
		String previewAble = getRequest().getParameter("previewAble");		//是否可以预览ceb
		String printStampName = getRequest().getParameter("printStampName");//章名称
		String fileId = getRequest().getParameter("fileId");				//附件id
		String cebid = getRequest().getParameter("cedid");					//cebid
		String fileLocation = getRequest().getParameter("fileLocation");	//文件路径
		String docguid = getRequest().getParameter("docguid");				//公文id
		String isManAtt = getRequest().getParameter("isManAtt");			//是否是正文附件
		String docDownloadUrl= getRequest().getParameter("docDownloadUrl");	//公文word下载路径
		//读取配置文件中的相关需要用到的参数Start
		String fileDownloadUrl=SystemParamConfigUtil.getParamValueByParam("filedownloadurl");
		String cebUploadUrl=SystemParamConfigUtil.getParamValueByParam("cebUploadUrl");
		String StampServer_PrintNumURL_Serialctrl=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintNumURL_Serialctrl");
		String StampServer_PrintNumURL_Numproc=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintNumURL_Numproc");
		String StampServer_PrintInfoURL=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintInfoURL");
		String StampServer_SendPrint=SystemParamConfigUtil.getParamValueByParam("StampServer_SendPrint");
		//读取配置文件中的相关需要用到的参数End
		StringBuffer jsonInfo = new StringBuffer();
		String cebUploadUrlArgs="?docguid="+docguid+"&attsId="+fileId+"&fileId="+fileId+"&isManAtt="+isManAtt;
		String cebDownloadUrl=fileDownloadUrl+"?name=a.ceb&location="+fileLocation;
		//String webCebUrl = "";
		//ceb预览
		if("true".equals(previewAble)){
			//webCebUrl = serverUrl + "/form/html/workflow/" + fileLocation;
			//jsonInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(CEB_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"预览\" onclick=\"javascript:loadCEB('"+webCebUrl+"')\" />");
		}
		if("true".equals(tocebAble)){
			jsonInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(WORDTOCEB_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"转CEB\" onclick=\"javascript:wordToCEB('"+cebid+"','"+cebUploadUrl+cebUploadUrlArgs+"','"+docDownloadUrl+"')\" />");
		}
		if("true".equals(toStampAble)){
//			jsonInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(STAMP_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"盖章\" onclick=\"javascript:visualStamp('"+cebid+"','"+cebUploadUrl+cebUploadUrlArgs+"','"+cebDownloadUrl+"','"+ldaPProxySvr+"','"+stampServer_AffixRegisterURL+"','"+stampServer_Printerror+"')\" />");
		}
		if(printStampName!=null&&printStampName.trim().length()>0&&!printStampName.trim().equals("null")){
			jsonInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(PRINTOFREDCAP_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"带红章打印\" onclick=\"javascript:printStamp('"+cebDownloadUrl+"','"+printStampName+"','"+StampServer_PrintInfoURL+"','"+StampServer_PrintNumURL_Serialctrl+"','"+StampServer_PrintNumURL_Numproc+"','"+StampServer_SendPrint+"')\" />");
		}
		
		try {
			getResponse().getWriter().print(jsonInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：显示附件内容
	 *
	 * @param deleteAble
	 * @param downloadAble
	 * @param previewAble
	 * @param onlineEditAble
	 * @param tocebAble
	 * @param toStampAble
	 * @param printStampName
	 * @param detachStampAble
	 * @param openBtnClass
	 * @param otherBtnsClass
	 * @param showId
	 * @param isReciveAtt
	 * @param attList
	 * @param isman
	 * @return StringBuffer
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-22 上午11:27:14
	 */
	private StringBuffer getAttInfo(boolean deleteAble,boolean downloadAble,boolean previewAble,boolean onlineEditAble,boolean tocebAble,boolean toStampAble,String printStampName,boolean detachStampAble,String openBtnClass,String otherBtnsClass,
				String showId,boolean isReciveAtt,List attList,boolean isman, boolean sfqg, String nodeId,String isFirst){
		//处理标签属性参数（拼成参数字符串）Start
		Object args[] = new Object[]{tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId};
		String argsStr=this.getArgStr(args, "','");
		//处理标签属性参数（拼成参数字符串）End
		
		//获取当前节点
		WfNode wfNode = null;
		if(nodeId!=null && !nodeId.equals("")){
			wfNode = workflowBasicFlowService.getWfNode(nodeId);
		}
		
		boolean isEdit = false;	//是否清稿
		if(wfNode!=null){
			Integer wfn_isEdit = wfNode.getWfn_isEdit();
			if(wfn_isEdit!=null && wfn_isEdit==1){
				isEdit = true;
			}
		}
		
		
		//读取配置文件中的相关需要用到的参数Start
		String fileDownloadUrl=SystemParamConfigUtil.getParamValueByParam("filedownloadurl");
		String cebUploadUrl=SystemParamConfigUtil.getParamValueByParam("cebUploadUrl");
		String StampServer_PrintNumURL_Serialctrl=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintNumURL_Serialctrl");
		String StampServer_PrintNumURL_Numproc=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintNumURL_Numproc");
		String StampServer_PrintInfoURL=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintInfoURL");
		String StampServer_SendPrint=SystemParamConfigUtil.getParamValueByParam("StampServer_SendPrint");
		//读取配置文件中的相关需要用到的参数End
		StringBuffer attInfo = new StringBuffer();
		String fileId="";
		String fileName="";
		String fileType="";
		String attTitle="";
		String attType="";
		String docguid="";
		String uploadUser = "";//上传者
		String isSealed = "";//是否已盖章
		long fileSize=0;
		String fileLocation="";
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		for(Object obj:attList){
			if(obj instanceof SendAttachments){
				SendAttachments atts = (SendAttachments)obj;
				fileId=atts.getId();
				fileName=atts.getFilename();
				fileType=atts.getFiletype();
				fileSize=atts.getFilesize();
				fileLocation=atts.getLocalation();
				if(atts.getEditer() != null && !("").equals(atts.getEditer())){
					uploadUser = atts.getEditer();
				}
				docguid=atts.getDocguid();
				attTitle=atts.getTitle();
				attType=atts.getType();
				isSealed = atts.getIsSeal();
			}else if(obj instanceof ReceiveAttachments){
				ReceiveAttachments atts = (ReceiveAttachments)obj;
				fileId=atts.getId();
				fileName=atts.getFilename();
				fileType=atts.getFiletype();
				fileSize=atts.getFilesize();
				fileLocation=atts.getLocalation();
			}else if(obj instanceof SendAttachmentsext){
				SendAttachmentsext atts = (SendAttachmentsext)obj;
				fileId=atts.getId();
				fileName=atts.getFilename();
				fileType=atts.getFiletype();
				fileSize=atts.getFilesize();
				fileLocation=atts.getLocalation();
				attTitle=atts.getTitle();
				attType=atts.getType();

			}else if(obj instanceof ReceiveAttachmentsext){
				ReceiveAttachmentsext atts = (ReceiveAttachmentsext)obj;
				fileId=atts.getId();
				fileName=atts.getFilename();
				fileType=atts.getFiletype();
				fileSize=atts.getFilesize(); 
				fileLocation=atts.getLocalation();
			}
			if(attTitle!=null && !("").equals(attTitle)){
				String showtitle = attTitle;
				if(attTitle.length()>30){
					showtitle = attTitle.substring(0, 30)+"...";
				}
				attInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"att_tag_filename\" style=\"color: black;line-height:12px;font-size:12px\">▶&nbsp;&nbsp;</span>");
				attInfo.append("<span class=\"att_tag_filename\" style=\"color: black;line-height:12px;\">&nbsp;&nbsp;");
				attInfo.append("<span style=\"white-space:nowrap;overflow:hidden;text-overflow:ellipsis;font-size: 12px;width:200px;\" title='"+attTitle+"'>"+showtitle).append("</span></span>");
			}else{
				attInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"att_tag_filename\" style=\"color: black;line-height:12px;font-size:12px\">▶&nbsp;</span>");
				attInfo.append("<span class=\"att_tag_filename\" style=\"color: black;line-height:12px;\"><span style=\"color: blue;\">(").append(attType).append(")</span>&nbsp;&nbsp;");
				attInfo.append("<span>&nbsp;</span></span>");
			}
			if(fileSize!=0L){
				attInfo.append("<span class=\"att_tag_filesize\" style=\"color: gray;line-height:12px;font-size: 12px;\">&nbsp;[").append(this.getFormateFileSize(fileSize)).append("]</span>");
			}
//			attInfo.append("<span class=\"att_tag_filename\" style=\"color: black;line-height:15px;\">&nbsp;").append(fileName).append("</span>"); 
			//是否是正文附件
			String isManAtt="false";
			if(isman){
				isManAtt="true";
			}
			
			//Word的相关功能集成
			if(fileType!=null&&("doc".equals(fileType) || "docx".equals(fileType))){
				String docDownloadUrl=fileDownloadUrl+"?name=a.doc&location="+fileLocation;
				String cebUploadUrlArgs="?fileId="+fileId+"&isManAtt="+isManAtt;
				//word预览（包括编辑）
				if(previewAble){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(WORD_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"预览\" onclick=\"javascript:viewDoc('"+fileLocation+"')\" />");
				}
				if(tocebAble&&!isReciveAtt){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(WORDTOCEB_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"转CEB\" onclick=\"javascript:wordToCEB('"+argsStr+"','"+cebUploadUrl+cebUploadUrlArgs+"','"+docDownloadUrl+"')\" />");
				}
				if(onlineEditAble&&isEdit){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(WORDTOEDIT_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" id=\"").append(tagid).append("_toceb\" onMouseOver=\"this.style.cursor='hand'\" title=\"在线编辑\" onclick=\"javascript:onlineEdit('"+argsStr+"','"+fileId+"','"+fileLocation+"','"+sfqg+"','"+isFirst+"')\" />");
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(WORDTOHIS_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" id=\"").append(tagid).append("_toceb\" onMouseOver=\"this.style.cursor='hand'\" title=\"查看历史\" onclick=\"javascript:viewHistory('"+docguid+"','"+fileId+"')\" />");
				}
				if(toStampAble && !"1".equals(isSealed)){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(STAMP_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"盖章\" onclick=\"javascript:founderSeal('"+argsStr+"','"+fileId+"')\" />");
				}
//				if(isman&&!isReciveAtt&&onlineEditAble){
//				}
			}
			//String webCebUrl = "";
			//CEB的相关功能集成
			if(fileType!=null&&"ceb".equals(fileType)){
				String cebDownloadUrl=fileDownloadUrl+"?name=a.ceb&location="+fileLocation;
				//webCebUrl = serverUrl + "/form/html/workflow/" + fileLocation;
				//ceb预览
				if(previewAble){
					//attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(CEB_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"预览\" onclick=\"javascript:loadCEB('"+webCebUrl+"')\" />");
				}
				if(toStampAble&&!isReciveAtt){
//					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(STAMP_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"盖章\" onclick=\"javascript:visualStamp('"+argsStr+"','"+cebUploadUrl+cebUploadUrlArgs+"','"+cebDownloadUrl+"','"+ldaPProxySvr+"','"+stampServer_AffixRegisterURL+"','"+stampServer_Printerror+"')\" />");
				}
				if(printStampName!=null&&printStampName.trim().length()>0&&!printStampName.trim().equals("null")){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(PRINTOFREDCAP_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"带红章打印\" onclick=\"javascript:printStamp('"+cebDownloadUrl+"','"+printStampName+"','"+StampServer_PrintInfoURL+"','"+StampServer_PrintNumURL_Serialctrl+"','"+StampServer_PrintNumURL_Numproc+"','"+StampServer_SendPrint+"')\" />");
				}
				if(detachStampAble){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(UNLOCK_ICON_PATH).append("\" align=\"absmiddle\"   border=\"0\" onMouseOver=\"this.style.cursor='hand'\" title=\"脱密\" onclick=\"javascript:detachStamp('"+cebDownloadUrl+"')\" />");
				}
			}
			//PDF预览
			if(fileType!=null&&"pdf".equals(fileType)){
				//String pdfDownloadUrl=fileDownloadUrl+"?name=a.pdf&location="+fileLocation;
				//pdf预览
				/*if(previewAble){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(PDF_ICON_PATH).append("\" align=\"absmiddle\"  border=\"0\" onMouseOver=\"this.style.cursor='hand'\" onclick=\"javascript:viewPDF('"+fileLocation+"')\" />");
				}*/
			}
			if(downloadAble){
				attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(DOWNLOAD_ICON_PATH).append("\" title=\"下载\" style=\"cursor:pointer;\" onclick=\"javascript:download('"+fileLocation+"','"+fileDownloadUrl+"?name=").append(fileName).append("&location="+fileLocation+"')\" />");
			}
			if(deleteAble&&isman){
				if((userId).equals(uploadUser.split(";")[0])){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(DELETE_ICON_PATH).append("\" title=\"删除\" style=\"cursor:pointer;\" id=\""+docguid+"attachmentDel\" name=\""+docguid+"attachmentDel\" onclick=\"deleteAtts('"+argsStr+"','"+fileId+"','"+isFirst+"')\"/>");
				}
//				attInfo.append("&nbsp&nbsp<input type=\"button\" class=\"").append(otherBtnsClass).append("\" mice-btn=\"").append(otherBtnsClass).append("\" value=\"删除\" id=\"attachmentDel\" name=\"attachmentDel\" onclick=\"deleteAtts('"+argsStr+"','"+fileId+"')\"/>");
			}/*else if(deleteAble){
				if((userId).equals(uploadUser.split(";")[0])){
					attInfo.append("&nbsp&nbsp<img src=\"").append(getRequest().getContextPath()).append(DELETE_ICON_PATH).append("\" title=\"删除\" style=\"cursor:pointer;\" id=\""+docguid+"attachmentDel\" name=\""+docguid+"attachmentDel\" onclick=\"deleteAtts('"+argsStr+"','"+fileId+"')\"/>");
				}
//				attInfo.append("&nbsp&nbsp<input type=\"button\" class=\"").append(otherBtnsClass).append("\" mice-btn=\"").append(otherBtnsClass).append("\" value=\"删除\" onclick=\"deleteAttsext('"+argsStr+"','"+fileId+"')\"/>");
//new			attInfo.append("&nbsp&nbsp<input type=\"button\" class=\"").append(otherBtnsClass).append("\" mice-btn=\"").append(otherBtnsClass).append("\" value=\"删除\" id=\"attachmentDel\" name=\"attachmentDel\" onclick=\"deleteAtts('"+argsStr+"','"+fileId+"')\"/>");
			}*/
			//attInfo.append("&nbsp&nbsp<input type=\"button\" class=\"").append(otherBtnsClass).append("\" mice-btn=\"").append(otherBtnsClass).append("\" value=\"修改\" onclick=\"modifyAtts('"+argsStr+"','"+fileId+"')\"/>");
			attInfo.append("<BR>");
		}
		return attInfo;
	}
	
	/**
	 * 
	 * 描述：将数据中的元素以字符串的形势用str连接起来返回<br>
	 *
	 * @param obj
	 * @param str
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:03:42
	 */
	private String getArgStr(Object[]obj,String str){
		StringBuffer sb = new StringBuffer();
		for(Object o:obj){
			if(o==null){
				sb.append("").append(str);
			}else{
				sb.append(o.toString().trim()).append(str);
			}
		}
		return sb.toString().substring(0,sb.toString().lastIndexOf(str));
	}
	
	/**
	 * 
	 * 描述：WORD在线查看<br>
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-3 下午04:25:50
	 */
	public String viewDoc(){
		String location = getRequest().getParameter("filelacation");
		String histroyid = getRequest().getParameter("histroyid");
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String filePath = pdfRoot + location;
		File file = new File(filePath);
		if(!file.exists() && StringUtils.isNotBlank(histroyid)){
			SendAttachmentsHistory history = attachmentService.findSendAttHistory(histroyid);
			if(null != history){
				FileUtils.byteArrayToFile(history);
			}
		}
		getRequest().setAttribute("location",location);
		return "viewDoc";
	}
	
	//查看附件之前判断是否加密文件
	public void beforeViewDoc(){
		try {
			String location = getRequest().getParameter("fileLocation");
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String filePath = pdfRoot + location;
			
			String mergePath = UuidGenerator.generate36UUID() + "Att_merge.pdf";
			mergePath = FileUploadUtils.getRealFilePath(mergePath, pdfRoot,Constant.GENE_FILE_PATH);
			mergePath = pdfRoot + mergePath;
			String[] files = new String[1];
			files[0] = filePath;
			MergePdf mp = new MergePdf();
			mp.mergePdfFiles(files, mergePath, "");
			toPage("success");
		} catch (Exception e) {
			e.printStackTrace();
			String errorPage = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath()+"/table_downloadError.do";
			toPage(errorPage);
			return;
		}
	}
	
	
	/**
	 * 
	 * 描述：在线编辑word文件
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-12-1 下午3:11:43
	 */
	public String onlineEditDoc(){
		String location = getRequest().getParameter("attLocation");
		String attId = getRequest().getParameter("attId");
		String sfqg = getRequest().getParameter("sfqg");
		String nodeId = getRequest().getParameter("nodeId");		//节点Id
		String instanceId = getRequest().getParameter("instanceId");
		String isFirst = getRequest().getParameter("isFirst");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		
		Boolean isOpened = false;
		
		WfOnlineEditStatus wfOnlineEditStatus = attachmentService.findOESByInfo(attId,"");
		if(wfOnlineEditStatus!=null){
			if(!wfOnlineEditStatus.getUserId().equals(emp.getEmployeeGuid()) && wfOnlineEditStatus.getFileStatus().equals("1")){
				isOpened = true;
			}
			if(!isOpened){
				wfOnlineEditStatus.setEditTime(new Date());
				wfOnlineEditStatus.setFileStatus("1");
				wfOnlineEditStatus.setUserId(emp.getEmployeeGuid());
				attachmentService.updateWfOnlineEditStatus(wfOnlineEditStatus);
			}
		}else{
			wfOnlineEditStatus = new WfOnlineEditStatus();
//			wfOnlineEditStatus.setId(UuidGenerator.generate36UUID());
			wfOnlineEditStatus.setAttid(attId);
			wfOnlineEditStatus.setFileStatus("1");
			wfOnlineEditStatus.setUserId(emp.getEmployeeGuid());
			wfOnlineEditStatus.setEditTime(new Date());
			attachmentService.addWfOnlineEditStatus(wfOnlineEditStatus);
		}
		
		SendAttachments att = attachmentService.findSendAtts(attId);
		if(null != att && StringUtils.isNotBlank(att.getId())){
			location = att.getLocalation();
			List<SendAttachmentsHistory> hisList = attachmentService.findAllSendAttHistory(att.getDocguid(), attId);
			if(null != hisList && hisList.size()>0){
				String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
				Blob data = hisList.get(0).getAttflow();
				if(null != data){
					FileUtils.byteArrayToFile(data, basePath+location);
				}
			}
		}
		String fgwQrcodeNodeIds = SystemParamConfigUtil.getParamValueByParam("fgw_qrcode_nodeIds");
		if(fgwQrcodeNodeIds!=null&&fgwQrcodeNodeIds.contains(nodeId)){
			getRequest().setAttribute("writeQrcode", true);
		}
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath() + "/form/html/workflow/";
		getRequest().setAttribute("serverUrl", serverUrl);
		getRequest().setAttribute("empName", emp.getEmployeeName());
		getRequest().setAttribute("instanceId", instanceId);
		getRequest().setAttribute("location",location);
		getRequest().setAttribute("attId",attId);
		if(StringUtils.isNotBlank(att.getTemId())){
			getRequest().setAttribute("templateId",att.getTemId());
		}
		getRequest().setAttribute("sfqg",sfqg);
		getRequest().setAttribute("isFirst",isFirst);
		if(nodeId!=null && !nodeId.equals("")){
			WfNode node = workflowBasicFlowService.getWfNode(nodeId);
			if(node!=null){
		    	String templates = node.getWfn_redtapeTemplate()+","+node.getWfn_defaulttemplate();
//				String templates = node.getWfn_redtapeTemplate();
				if(templates!=null && !templates.equals("")){
					String[] ids = templates.split(",");
					String tempIds = "";
					for(int i=0; i<ids.length; i++){
						tempIds += "'"+ids[i]+"',";
					}
					if(tempIds!=null && tempIds.length()>0){
						tempIds = tempIds.substring(0, tempIds.length()-1);
					}
					List<WfTemplate> list = templateService.findWfTemplateList(tempIds, "0");//获取到的正文模板,"0",表示上传时默认模板（红头、普通）
					getRequest().setAttribute("list", list);
					String sysPath = PathUtil.getWebRoot();
					getRequest().setAttribute("sysPath", sysPath);
				}
				getRequest().setAttribute("showMarkbtn", node.getWfn_showMarkbtn());
			}
		}
		String projectUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
		String realId = SystemParamConfigUtil.getParamValueByParam("readIp");
		String[] realIds = realId.split("##");
		if(projectUrl.indexOf(realIds[1]) != -1){
			projectUrl = projectUrl.replace(realIds[1], realIds[0]);
		}
		getRequest().setAttribute("projectUrl", projectUrl);
		if(isOpened){
			return "viewDoc";
		}
		return "onlineEditDoc";
	}
	
	/**
	 * 描述:系统中双屏打开应用
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-1-19 上午11:54:55
	 */
	public String onlinePrintDoc(){
		String attId = getRequest().getParameter("attId");
		String processId = getRequest().getParameter("processId");
		String instanceId = getRequest().getParameter("instanceId");
		SendAttachments sa = attachmentService.findSendAtts(attId);
		String location = "";
		if(sa!=null){
			location = sa.getLocalation();
		}else{
			List<String> typelist = new ArrayList<String>();
			typelist.add("doc");
			typelist.add("docx");
			List<SendAttachments> list = attachmentService.findSendAttachmentList(instanceId, typelist);
			if(list!=null && list.size()>0){
				location = list.get(0).getLocalation();
			}
		}
		getRequest().setAttribute("location", location);
		getRequest().setAttribute("attId",attId);
		getRequest().setAttribute("processId",processId);
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		getRequest().setAttribute("userName",emp.getEmployeeName());
		return "onlinePrintDoc";
	}
	
	public String viewHistory(){
		String docguid = getRequest().getParameter("docguid");
		String id = getRequest().getParameter("id");
		List<SendAttachmentsHistory> attHistoryList=attachmentService.findAllSendAttHistory(docguid,id);
		getRequest().setAttribute("attHistoryList", attHistoryList);
		return "viewHistory";
	}
	
	/**
	 * 
	 * 描述：在线打开pdf
	 * @param filepath void
	 *
	 * 作者:Zhaoj☂<br>
	 * 创建时间:2012-9-5 下午03:02:01
	 * @throws IOException 
	 */
	public void viewPdf() throws IOException{
//		String type="";
//		//type 为response 返回类型,filename 为要打开的文件名称
//		 if((filename.indexOf(".xls")>0) || (filename.indexOf(".xlsx")>0)){
//			 type = "application/vnd.ms-excel";  
//		 }else if(filename.indexOf(".pdf")>0){
//			 type = "application/pdf";          
//		 }else if((filename.indexOf(".doc")>0)  || (filename.indexOf(".docx")>0)){
//			 type = "application/msword";       
//		 }else if(filename.indexOf(".txt")>0){ 
//			 type = "text/plain";                
//		 }else if(filename.indexOf(".ppt") >0){
//			 type = "application/ppt";           
//		 }
//

		String fileName = getRequest().getParameter("filename");  //C:\\Users\\zj\\AppData\\Local\\Microsoft\\Windows\\Temporary Internet Files\\Content.IE5\\1347500646246.pdf
		String location = getRequest().getParameter("location");  //attachments/2012/09/13/FDCD7438-85BB-4008-8F67-9E8C0C82D6FB.pdf
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");  //d:/data/file/oadoc/
		String fileNameWithPath =basePath+location;
		try {
			// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
			if(fileName!=null&&fileName.trim().length()>0){
				fileName = URLEncoder.encode(fileName, "GB2312");
				fileName = URLDecoder.decode(fileName, "ISO8859-1");
			}else{
				fileName = "a."+FileUploadUtils.getExtension(location).toLowerCase();
			}
			File file = new File(fileNameWithPath);
			if(!file.exists()){
				String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/";
				fileNameWithPath = fileNameWithPath.replace(basePath, pdfRoot);
				file = new File(fileNameWithPath);
			}
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength((int) l);
			getResponse().setHeader("Content-Disposition", "inline; filename="+ fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			LOGGER.error("打开失败,文件路径为："+fileNameWithPath, e);
			e.printStackTrace();
		}
	}
	
	public void getCountOfFj(){
		String docguid = getRequest().getParameter("docguid");
		List<SendAttachments> atts =attachmentService.findAllSendAtts(docguid,null);
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(atts.size());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void getAttachmentCount(){
		String instanceId = getRequest().getParameter("instanceId");
		List<SendAttachments> list = attachmentService.findSendAttachmentListByInstanceId(instanceId);
		int count = 0;
		for(SendAttachments att: list){
			String fileType = att.getFiletype();
			if(fileType!=null && (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("true")
					|| fileType.equalsIgnoreCase("doc") || fileType.equalsIgnoreCase("docx")||
					fileType.equalsIgnoreCase("xls") || fileType.equalsIgnoreCase("xlsx")||
					fileType.equalsIgnoreCase("ceb"))){
				count ++;
			}
		}
		PrintWriter out = null;
		String result = "";
		if(count>0){
			result = "success";
		}else{
			result = "false";
		}
		try {
			out = this.getResponse().getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	

	 public String createFileFromByte(byte[] fileBytes, String uploadfilename)
	  {
	    String basePath = SystemParamConfigUtil.getParamValueByParam("filepath");
	    String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath, "attachments/");
	    String filePath = "";
	    if (fileBytes != null) {
	      filePath = basePath + dstPath;
	      File file = new File(filePath);
	      if (file.exists()) {
	        file.delete();
	      }
	      FileOutputStream fos = null;
	      try {
	        fos = new FileOutputStream(file);
	        fos.write(fileBytes, 0, fileBytes.length);
	        fos.flush();
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        try
	        {
	          fos.flush();
	          fos.close();
	        } catch (IOException e2) {
	          e2.printStackTrace();
	        }
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	        try
	        {
	          fos.flush();
	          fos.close();
	        } catch (IOException e1) {
	          e1.printStackTrace();
	        }
	      }
	      finally
	      {
	        try
	        {
	          fos.flush();
	          fos.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    return dstPath;
	  }
	 
	 /**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-3-1 下午4:26:25
	 */
	public void uploadFile4Widget(){
		String isDetach = getRequest().getParameter("isDetach");//脱密文件上传
		String id = "";
		String size = "";
		String fileExt = "";
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = iStream.read()) != -1) {
				bytestream.write(ch);
			}
			byte[] filedata = bytestream.toByteArray();
			bytestream.close();
			// 获取前4个字节
			int jsonLength = Utils.getIntValue(filedata, 0, 4);
			String fileInfo = new String(filedata, 4, jsonLength);
			JSONObject json = null;
			if(fileInfo!=null && !fileInfo.equals("")){
				json = JSONObject.fromObject(fileInfo);
			}
			if(null != json){
				id = json.getString("id");
				size = json.getString("size");
				//fileExt = json.getString("fileExt");
				if(StringUtils.isBlank(fileExt)){
					fileExt = "pdf";
				}
				FileOutputStream out = null;
				SendAttachments att = attachmentService.findSendAtts(id);
				String uploadfilename = UuidGenerator.generate36UUID()+"."+fileExt;
				String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
				String dstPath = FileUploadUtils.getRealFilePath(uploadfilename, basePath,Constant.UPLOAD_FILE_PATH);
				String path = basePath + dstPath;
				out = new FileOutputStream(new File(path));
				out.write(filedata, jsonLength+4, Integer.valueOf(size.trim()));
				if("1".equals(isDetach)){
					att.setTmPdfPath(path);
				}else{
					att.setTopdfpath(path);
					att.setIsSeal("1");
				}
				attachmentService.updateSendAtt(att);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-5-31 下午2:54:02
	 */
	public void uploadFile4M(){
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath();
		int size = 0;
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = iStream.read()) != -1) {
				bytestream.write(ch);
			}
			byte[] filedata = bytestream.toByteArray();
			bytestream.close();
			// 获取前4个字节
			int idLength = Utils.getIntValue(filedata, 0, 4);
			String processId = new String(filedata, 4, idLength);
			size = (filedata.length) - idLength - 4;
			FileOutputStream out = null;
			String uploadfilename = processId+".media";
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String path = basePath + "mobile/commentAtt/"+uploadfilename;
			out = new FileOutputStream(new File(path));
			out.write(filedata, idLength+4, size);
			String outPath = serverUrl+ "/form/html/workflow/mobile/commentAtt/"+uploadfilename;
			JSONObject json = new JSONObject();
			json.put("result", "success");
			json.put("path", outPath);
			toPage(json.toString());
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 * @Description: 将附件名称设置为办件标题
	 * @author: xiep
	 * @time: 2017-8-15 下午2:13:59
	 */
	public void getDocNameAsTitle(){
		String nodeId = getRequest().getParameter("nodeId");
		String docName = "";
		String docGuid = getRequest().getParameter("docguid");
		String attZw = SystemParamConfigUtil.getParamValueByParam("attSuffixName");
		String result = "";
		if(cn.com.trueway.base.util.CommonUtil.stringNotNULL(nodeId)){
			WfNode node = workflowBasicFlowService.findNodeById(nodeId);
			if(node != null){
				Integer isAttachNameAsTitle = node.getWfn_isAttachAsTitle();
				//设置了第一个附件名作为标题
				if(isAttachNameAsTitle != null && isAttachNameAsTitle == 1){
					if(cn.com.trueway.base.util.CommonUtil.stringNotNULL(docGuid) && docGuid.endsWith(attZw)){
						//获取正文
						List<SendAttachments> attList = attachmentService.findAllSendAtts(docGuid);
						if(attList != null && attList.size() > 0){
							SendAttachments attachments = attList.get(0);
							if(attachments != null){
								docName = attachments.getFilename();
							}
						}
					}
					if(cn.com.trueway.base.util.CommonUtil.stringNotNULL(docName)&&docName.contains(".")){
						docName = docName.substring(0, docName.indexOf("."));
					}
					String fieldName = "";
					WfMain wfMain = node.getWfMain();
					String titleCol = wfMain.getWfm_title_column();
					//获取标题字段，流程标题设置单个字段就作替换
					if(cn.com.trueway.base.util.CommonUtil.stringNotNULL(titleCol) && !titleCol.contains(",")){
						WfFieldInfo fieldInfo = tableInfoService.getFieldById(titleCol);
						if(fieldInfo != null){
							fieldName = fieldInfo.getVc_fieldname();
							if(cn.com.trueway.base.util.CommonUtil.stringNotNULL(fieldName)){
								result = fieldName + ":" + docName;
								PrintWriter out = null;
								try {
									out = getResponse().getWriter();
								} catch (IOException e) {
									e.printStackTrace();
								}
								out.write(result);
								out.close();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2017-9-5 上午9:18:30
	 */
	public void file2Pdf(){
		String filePath = getRequest().getParameter("filePath");
		String pdfPath = getRequest().getParameter("pdfPath");
		JSONObject outObj = new JSONObject();
		if(StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(pdfPath)){
			File file = new File(filePath);
			if(file.exists()){
				String fileName = file.getName();
				String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
				String outPdfPath = "";
				if(fileType.toUpperCase().equals("TIF")){
					File pdfFile = new File(pdfPath);
					pdfFile = TiffToMPdf.tifftopdf(file, pdfFile);
					outPdfPath = pdfPath;
				}else if(fileType.equals("doc")|| fileType.equals("docx")){
					TaskEntity msg = null;
			        Map<String, String> params = null;
			        params = new HashMap<String, String>();
			        params.put("sourceFilePath", filePath);
			        params.put("destinPDFFilePath", pdfPath);
			        msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
					TaskPoolManager.newInstance().addTask(msg);
					File pdfFile = new File(pdfPath);
					do {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} while (!pdfFile.exists());
					outPdfPath = pdfPath;
				}else if(fileType.equals("xls")|| fileType.equals("xlsx")){
					ExcelToPdf xlsToPdf = new ExcelToPdf();
					try {
						xlsToPdf.excelToPDF(filePath, pdfPath, "", "");
						outPdfPath = pdfPath;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(fileType.equals("ceb") || fileType.equals("cebx")){
					CebToPdf cp = new CebToPdf();
					// 文件路径
					String cebPath = filePath;
					cp.cebToPdf(cebPath);
					String path = "";
					if(fileType.equals("ceb")){
						path = cebPath.substring(0,cebPath.length() - 3)+"pdf";
					}else if(fileType.equals("cebx")){
						path = cebPath.substring(0,cebPath.length() - 4)+"pdf";
					}
					File pdfFile = new File(path);
					do {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} while (!pdfFile.exists());
					outPdfPath = path;
				}else if(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("jpeg") || fileType.equals("bmp")){
					ImageToPdf imgtoPdf = new ImageToPdf();
					try {
						imgtoPdf.imgToPdf(pdfPath, filePath);
						outPdfPath = pdfPath;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(fileType.equals("pdf")){
					outPdfPath = filePath;
				}
				outObj.put("result", "success");
				outObj.put("pdfPath", outPdfPath);
			}else{
				outObj.put("result", "fail");
				outObj.put("message", "10001");//文件不存在
			}
		}else{
			outObj.put("result", "fail");
			outObj.put("message", "10002");//待转路径和目标路径不能为空
		}
		
		toPage(outObj.toString());
	}
	
	public static void main(String[] args) {
		String wh = "苏12345〔2017〕0001号";
		String zh = "";
		String nh = "";
		String xh = "";
		if(wh.indexOf("苏12345")>-1){
			zh = "苏12345";
			
			nh = wh.substring(7,11);
			xh = wh.substring(12,wh.length()-1);
			System.out.println(zh+"["+nh+"]"+xh);
		}
	}
	
	/**
	 * 发文选择添加正文模板
	 */
	public void addTemplateForSendDoc(){
		String innerDocguid = getRequest().getParameter("docguid");//实例id
		String temId = getRequest().getParameter("temId");//模板id
		String sysPath = PathUtil.getWebRoot();
		String vc_path = "";
		String temName = "";
		WfTemplate template = templateService.getTemplateById(temId);
		if(template != null){
			vc_path = template.getVc_path();
			temName = template.getVc_cname();
		}
		String temLocation = sysPath + "/tempfile/" + vc_path;//模板所在路径
		File templateFile = new File(temLocation);
		Date curDate = new Date();
		String type = "正文模板";
		docguid = innerDocguid;
		nodeId = getRequest().getParameter("nodeId");
		String attId = "";
		if(templateFile.exists()){
			//正文模板要多传temid字段
			attId = uploadSendAtts(templateFile, temName + ".doc", temName, type, curDate, 1,temId);
		}else{
			
		}
		toPage(attId);
	}
	
	/**
	 * 跳转到上传正文模板页面
	 * @return
	 */
	public String openUploadtemplate(){
		String docguid =  getRequest().getParameter("docguid");//实例id
		String nodeId = getRequest().getParameter("nodeId");//节点id
		//获取正文模板
		if(CommonUtil.stringNotNULL(nodeId)){
			WfNode wfNode = workflowBasicFlowService.getWfNode(nodeId); 
			if(wfNode != null){
				String defTemp = wfNode.getWfn_defaulttemplate();
				if(CommonUtil.stringNotNULL(defTemp)){
					String[] ids = defTemp.split(",");
					String tempIds = "";
					for(int i=0; i<ids.length; i++){
						tempIds += "'"+ids[i]+"',";
					}
					if(tempIds!=null && tempIds.length()>0){
						tempIds = tempIds.substring(0, tempIds.length()-1);
					}
					List<WfTemplate> temList = templateService.findWfTemplateList(tempIds, "1");//获取到的正文模板,"1",表示是公文模板
					getRequest().setAttribute("temList", temList);
				}
			}
		}
		getRequest().setAttribute("docguid", docguid);
		getRequest().setAttribute("nodeId", nodeId);
		return "openUploadtemplate";
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 下午1:40:00
	 */
	public void countNoCuteAtt(){
		Integer fileSize = StringUtils.isNotBlank(getRequest().getParameter("fileSize"))?Integer.parseInt(getRequest().getParameter("fileSize")):2;
		toPage(attachmentService.countNoCuteAtt(fileSize)+"");
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 下午1:37:15
	 */
	public void getNoCuteAtt(){
		Integer fileSize = StringUtils.isNotBlank(getRequest().getParameter("fileSize"))?Integer.parseInt(getRequest().getParameter("fileSize")):2;
		Integer pageSize = StringUtils.isNotBlank(getRequest().getParameter("pageSize"))?Integer.parseInt(getRequest().getParameter("pageSize")):100;
		Integer selectIndex = StringUtils.isNotBlank(getRequest().getParameter("selectIndex"))?Integer.parseInt(getRequest().getParameter("selectIndex")):1;
		List<String> list = attachmentService.getNoCuteAtt(fileSize, (selectIndex-1)*pageSize, pageSize);
		JSONArray arr = JSONArray.fromObject(list);
		toPage(arr.toString());
	}
	
	/**
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:蒋烽
	 * 创建时间:2018-3-15 下午1:37:37
	 */
	public void CuteAtt(){
		String id = getRequest().getParameter("id");
		SendAttachments att = attachmentService.findSendAtts(id);
		String code = saveCutFiles(att);
		toPage(code);
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param deleteAble
	 * @param downloadAble
	 * @param previewAble
	 * @param onlineEditAble
	 * @param tocebAble
	 * @param toStampAble
	 * @param printStampName
	 * @param detachStampAble
	 * @param openBtnClass
	 * @param otherBtnsClass
	 * @param showId
	 * @param isReciveAtt
	 * @param attList
	 * @param isman
	 * @param sfqg
	 * @param nodeId
	 * @param isFirst
	 * @return StringBuffer
	 * 作者:蒋烽
	 * 创建时间:2018-3-29 下午2:12:25
	 */
	private StringBuffer getAttInfoNew(boolean deleteAble,boolean downloadAble,boolean previewAble,boolean onlineEditAble,boolean tocebAble,boolean toStampAble,String printStampName,boolean detachStampAble,String openBtnClass,String otherBtnsClass,
			String showId,boolean isReciveAtt,List attList,boolean isman, boolean sfqg, String nodeId,String isFirst){
		//处理标签属性参数（拼成参数字符串）Start
		Object args[] = new Object[]{tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId};
		String argsStr=this.getArgStr(args, "','");
		//处理标签属性参数（拼成参数字符串）End
		
		//获取当前节点
		WfNode wfNode = null;
		if(nodeId!=null && !nodeId.equals("")){
			wfNode = workflowBasicFlowService.getWfNode(nodeId);
		}
		
		boolean isEdit = false;	//是否清稿
		if(wfNode!=null){
			Integer wfn_isEdit = wfNode.getWfn_isEdit();
			if(wfn_isEdit!=null && wfn_isEdit==1){
				isEdit = true;
			}
		}
		
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath() ;
		
		//读取配置文件中的相关需要用到的参数Start
		String fileDownloadUrl=SystemParamConfigUtil.getParamValueByParam("filedownloadurl");
		String fgw_siteId=SystemParamConfigUtil.getParamValueByParam("hideOpt_siteId");
		//读取配置文件中的相关需要用到的参数End
		StringBuffer attInfo = new StringBuffer();
		String fileId="";
		String fileName="";
		String fileType="";
		String attTitle="";
		String docguid="";
		String uploadUser = "";//上传者
		String pdfPath = "";
		long fileSize=0;
		String fileLocation="";
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		for(Object obj:attList){
			attInfo.append("<div class=\"file-item\">");
			if(obj instanceof SendAttachments){
				SendAttachments atts = (SendAttachments)obj;
				fileId=atts.getId();
				fileName=atts.getFilename();
				fileType=atts.getFiletype();
				fileSize=atts.getFilesize();
				fileLocation=atts.getLocalation();
				if(atts.getEditer() != null && !("").equals(atts.getEditer())){
					uploadUser = atts.getEditer();
				}
				if(atts.getTopdfpath()!=null&&!("").equals(atts.getTopdfpath())){
					pdfPath = atts.getTopdfpath();
				}
				docguid=atts.getDocguid();
				attTitle=atts.getTitle();
			}
			String filePrefix = "【普通附件】";
			if(StringUtils.isNotBlank(docguid) && docguid.indexOf("attzw") != -1){
				filePrefix = "【正       文】";
			}
			if(attTitle!=null && !("").equals(attTitle)){
				attInfo.append("<div class=\"progress-bar\"></div>");
				attInfo.append("<span class=\"pt\">"+filePrefix+"</span>");
				attInfo.append("<span class=\"pt-title\">"+attTitle+"</span>");
			}
			if(fileSize!=0L){
				attInfo.append("<span class=\"nc\">[").append(this.getFormateFileSize(fileSize)).append("] </span>");
			}
			
			if(downloadAble){
				boolean fgwDownload = false;
				List<String> jzDeptIds = tableInfoService.queryMultDeptByEmpId(emp.getEmployeeGuid());
				for(String s : jzDeptIds){
					String jzSiteId = tableInfoService.querySiteIdByDeptId(s);
					if(fgw_siteId.contains(jzSiteId)){
						fgwDownload = true;
						break;
					}
				}
				if(fgw_siteId.contains(emp.getSiteId())){
					fgwDownload = true;
				}
				
				//nj需求：ceb和pdf格式的附件下载按钮取消
				if(!("ceb").equals(fileType)&&!("CEB").equals(fileType)&&!("pdf").equals(fileType)&&!("PDF").equals(fileType)){
					//if(!uploadUser.contains(userId)){
					attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/xz.png\" onclick=\"javascript:download('"+fileLocation+"','"+fileId+"','"+fileName+"','"+fileDownloadUrl+"?name="+fileName+"&location="+fileLocation+"&fileId="+fileId+"',downloadCb)\"/></span>");
					//}else{
					//attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/xz.png\" onclick=\"javascript:downloadOnlyPdf('"+fileId+"','"+fileName+"','"+otherUserDownloadUrl+"'?id="+fileId+"&name="+fileName+"?pdfPath="+pdfPath+",'&fileType="+fileType+"',downloadCb)\"/></span>");
					//}
				}else if(("ceb").equals(fileType)||("CEB").equals(fileType)||("pdf").equals(fileType)||("PDF").equals(fileType)){
					if(uploadUser.contains(userId) || fgwDownload){
						attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/xz.png\" onclick=\"javascript:download('"+fileLocation+"','"+fileId+"','"+fileName+"','"+fileDownloadUrl+"?name="+fileName+"&location="+fileLocation+"&fileId="+fileId+"',downloadCb)\"/></span>");
					}
				}
				if(CommonUtil.stringNotNULL(isFirst) && isFirst.equals("true")){
					if("pdf".equals(fileType)||"PDF".equals(fileType)){
						attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/watch.png\" id=\"").append(tagid).append("_toceb\" title=\"预览\" onclick=\"javascript:viewPDF('"+fileLocation+"','"+fileName+"')\" /></span>");
					}
					if(StringUtils.isNotBlank(docguid) && docguid.indexOf("attzw") == -1 && ("doc".equals(fileType)||"docx".equals(fileType))){
						attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/watch.png\" id=\"").append(tagid).append("_toceb\" title=\"预览\" onclick=\"javascript:viewDoc('"+fileLocation+"')\" /></span>");
					}
				}
				
			}
			if(deleteAble&&isman){
				if((userId).equals(uploadUser.split(";")[0])){
					attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/sc.png\" title=\"删除\" id=\""+docguid+"attachmentDel\" name=\""+docguid+"attachmentDel\" onclick=\"deleteAtts('"+argsStr+"','"+fileId+"','"+isFirst+"')\"/></span>");
				}
			}
			String realId = SystemParamConfigUtil.getParamValueByParam("readIp");
			String[] realIds = realId.split("##");
			String realServerUrl = serverUrl.replace(realIds[1], realIds[0]);
			//Word的相关功能集成
			if(StringUtils.isNotBlank(docguid) && docguid.indexOf("attzw") != -1 && (fileType!=null&&("doc".equals(fileType) || "docx".equals(fileType)))){
				if(onlineEditAble&&isEdit){
					attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/edit.png\" id=\"").append(tagid).append("_toceb\" title=\"在线编辑\" onclick=\"javascript:onlineEdit('"+argsStr+"','"+fileId+"','"+fileLocation+"','"+sfqg+"','"+isFirst+"','"+realServerUrl+"')\" /></span>");
					attInfo.append(" <span style=\"vertical-align:-6px;\"><img src=\""+serverUrl+"/assets-common/newImage2/history.png\" id=\"").append(tagid).append("_toceb\" title=\"查看历史\" onclick=\"javascript:viewHistory('"+docguid+"','"+fileId+"')\" /></span>");
				}
			}
			attInfo.append("</div>");
		}
		return attInfo;
	}
	
	public void downloadOnlyPdf()throws IOException{
		String id = getRequest().getParameter("id");
		String fileName = getRequest().getParameter("name");
		String pdfPath = getRequest().getParameter("pdfPath");
		if(pdfPath==null||"".equals(pdfPath)){
			toPage("error");
		}else{
			if(StringUtils.isNotBlank(fileName)){
				fileName = URLDecoder.decode(fileName,"UTF-8");
				fileName = fileName.substring(0,fileName.lastIndexOf("."))+".pdf";
			}
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath();
			if(pdfPath.contains(pdfRoot)){
				pdfRoot.replace(pdfRoot, serverUrl+"/form/html/workflow/");
			}
			file = new File(pdfPath);
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setContentLength((int) l);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		}
	}
	
	public void reloadAttById(){
		String attId = getRequest().getParameter("attId");
		String instanceId = getRequest().getParameter("instanceId");
		String isFq = getRequest().getParameter("isFq"); //是否为发起步骤
		SendAttachments sendAttachments = attachmentService.findSendAtts(attId);
		if(sendAttachments==null){
			toPage("errror");
		}
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath();
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String realId = SystemParamConfigUtil.getParamValueByParam("readIp");
		String[] realIds = realId.split("##");
		
		int imageCount = 0;
		if(StringUtils.isNotBlank(isFq) && "true".equals(isFq) && StringUtils.isNotBlank(instanceId)){
			ToPdfUtil pdfUtil = new ToPdfUtil();
			JSONArray jArr = new JSONArray();
			List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + ATT_SUFFIX_NAME,null);
			for (SendAttachments sendAttachment : sattList) {
				String skipFileType = "rar,zip,cebx";
				if((skipFileType.indexOf(sendAttachment.getFiletype()) == -1) || sendAttachment.getFiletype().equals("ceb")){
					FileUtils.byteArrayToFile(sendAttachment,attachmentService);
					if(pdfUtil.isCebAndHaveSaveName(sattList,sendAttachment)){
						String path = sendAttachment.getTopdfpath();
						if(StringUtils.isBlank(path)){
							ToPdfUtil toPdfUtil = new ToPdfUtil();
							try {
								path = toPdfUtil.fileToPdf(sendAttachment,attachmentService);
							} catch (Exception e) {
								e.printStackTrace();
							}
							sendAttachment.setTopdfpath(path);
							attachmentService.updateSendAtt(sendAttachment);
						}
						if(null != sendAttachment.getPagecount() && sendAttachment.getPagecount() != 0){
							imageCount += sendAttachment.getPagecount();
						}else{
							Integer pageCount = PdfPage.getPdfPage(sendAttachment.getTopdfpath());
							sendAttachment.setPagecount(pageCount);
							attachmentService.updateSendAtt(sendAttachment);
							imageCount += pageCount;
						}
						JSONObject obj = new JSONObject();
						obj.put("id", sendAttachment.getId());
						obj.put("name", sendAttachment.getFilename());
						String attPdfPath = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
						if(attPdfPath.indexOf(realIds[1]) != -1){
							attPdfPath = attPdfPath.replace(realIds[1], realIds[0]);
						}
						obj.put("pdfUrl", attPdfPath);
						obj.put("isSeal", StringUtils.isNotBlank(sendAttachment.getIsSeal())?sendAttachment.getIsSeal():"0");
						obj.put("pageCount", sendAttachment.getPagecount());
						obj.put("filesize", sendAttachment.getFilesize());
						
						List<CutPages> cutList = attachmentService.findCutPagesListByDocId(sendAttachment.getId());
						if(cutList!=null && cutList.size()>0){
							JSONArray array = new JSONArray();
							JSONObject entity = null;
							CutPages cut = null;
							for(int i=0; i<cutList.size(); i++){
								cut = cutList.get(i);
								FileUtils.byteArrayToFile(cut);
								entity = new JSONObject();
								entity.put("id", cut.getId());
								entity.put("pageCount", cut.getPageCount());
								String pdfUrl = cut.getFilepath();
								String attUrl = serverUrl+ "/form/html/workflow/"+pdfUrl;
								if(attUrl.indexOf(realIds[1]) != -1){
									attUrl = attUrl.replace(realIds[1], realIds[0]);
								}
								entity.put("pdfUrl", attUrl);
								entity.put("sort", cut.getSort());
								entity.put("startPage", cut.getStartPage());
								entity.put("endPage", cut.getEndPage());
								entity.put("curFilesize", cut.getFileSize());
								array.add(entity);
							}
							obj.put("files", array.toString());
						}
						jArr.add(obj);
					}
				}
			}
			toPage(jArr.toString());
		}else{
			FileUtils.byteArrayToFile(sendAttachments,attachmentService);
			String path = sendAttachments.getTopdfpath();
			if(StringUtils.isBlank(path)){
				ToPdfUtil toPdfUtil = new ToPdfUtil();
				try {
					path = toPdfUtil.fileToPdf(sendAttachments,attachmentService);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sendAttachments.setTopdfpath(path);
				attachmentService.updateSendAtt(sendAttachments);
			}
			if(null != sendAttachments.getPagecount() && sendAttachments.getPagecount() != 0){
				imageCount += sendAttachments.getPagecount();
			}else{
				Integer pageCount = PdfPage.getPdfPage(sendAttachments.getTopdfpath());
				sendAttachments.setPagecount(pageCount);
				attachmentService.updateSendAtt(sendAttachments);
				imageCount += pageCount;
			}
			JSONObject obj = new JSONObject();
			obj.put("id", sendAttachments.getId());
			obj.put("name", sendAttachments.getFilename());
			String attPdfPath = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
			if(attPdfPath.indexOf(realIds[1]) != -1){
				attPdfPath = attPdfPath.replace(realIds[1], realIds[0]);
			}
			obj.put("pdfUrl", attPdfPath);
			obj.put("isSeal", StringUtils.isNotBlank(sendAttachments.getIsSeal())?sendAttachments.getIsSeal():"0");
			obj.put("pageCount", imageCount);
			obj.put("filesize", sendAttachments.getFilesize());
			toPage(obj.toString());
		}
		
	}
	
	public void updateStatus(){
		String attId = getRequest().getParameter("attId");
		String userId = getRequest().getParameter("userId");
		Employee emp = null;
		JSONObject resutObj = new JSONObject();
		if(userId!=null){
			emp = tableInfoService.findEmpByUserId(userId);;
		}else{
			emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		}
		WfOnlineEditStatus wfOnlineEditStatus = attachmentService.findOESByInfo(attId,"");
		if(wfOnlineEditStatus!=null && "1".equals(wfOnlineEditStatus.getFileStatus())){
			wfOnlineEditStatus.setFileStatus("0");
			attachmentService.updateWfOnlineEditStatus(wfOnlineEditStatus);
			resutObj.put("result", "success");
		}else{
			resutObj.put("result", "fail");
		}
		toPage(resutObj.toString());
	}
	
	public void openAttsByMobile(){
		String attId = getRequest().getParameter("attId");
		String userId = getRequest().getParameter("userId");
		Employee emp = null;
		JSONObject resutObj = new JSONObject();
		if(userId!=null){
			emp = tableInfoService.findEmpByUserId(userId);;
		}else{
			emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		}
		WfOnlineEditStatus wfOnlineEditStatus = attachmentService.findOESByInfo(attId,"");
		
		Boolean isOpened = false;
		
		if(wfOnlineEditStatus!=null){
			if(!wfOnlineEditStatus.getUserId().equals(emp.getEmployeeGuid()) && wfOnlineEditStatus.getFileStatus().equals("1")){
				isOpened = true;
			}
			if(!isOpened){
				wfOnlineEditStatus.setEditTime(new Date());
				wfOnlineEditStatus.setFileStatus("1");
				wfOnlineEditStatus.setUserId(emp.getEmployeeGuid());
				attachmentService.updateWfOnlineEditStatus(wfOnlineEditStatus);
			}
		}else{
			wfOnlineEditStatus = new WfOnlineEditStatus();
			wfOnlineEditStatus.setAttid(attId);
			wfOnlineEditStatus.setFileStatus("1");
			wfOnlineEditStatus.setUserId(emp.getEmployeeGuid());
			wfOnlineEditStatus.setEditTime(new Date());
			attachmentService.addWfOnlineEditStatus(wfOnlineEditStatus);
		}
		if(!isOpened){
			resutObj.put("result", "success");
		}else{
			resutObj.put("result", "fail");
		}
		toPage(resutObj.toString());
	}
	
	/**
	 * 
	 * 描述：通用下载方法
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:54:58
	 * @throws IOException 
	 */
	public void download4OCX() throws IOException{
		String attId = getRequest().getParameter("attId");
		String fileNameWithPath = "";
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		try {
			if(StringUtils.isNotBlank(attId)){
				SendAttachments att = attachmentService.findSendAtts(attId);
				if(null != att){
					FileUtils.byteArrayToFile(att, attachmentService);
					fileNameWithPath = att.getTopdfpath();
				}else{
					CutPages cutPages = attachmentService.findCutPageById(attId);
					if(null != cutPages){
						FileUtils.byteArrayToFile(cutPages);
						fileNameWithPath = pdfRoot + cutPages.getFilepath();
					}
				}
			}
			String str = File2String.file2String(fileNameWithPath);
			toPage(str);
		} catch (Exception e) {
			e.printStackTrace();
			toPage("fail");
		}
	}
	
	public void isExistPwdPdf(){
		//String docguid = getRequest().getParameter("docguid");
		String id = getRequest().getParameter("id");
		//List<SendAttachments> attList = attachmentService.findSendAttsByDocguid(docguid);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String filePath = "";
		StringBuffer pwdPdfAttIds = new StringBuffer();
		SendAttachments sendAttachments = attachmentService.findSendAtts(id);
		//for(SendAttachments sendAttachments : attList){
			filePath = pdfRoot+sendAttachments.getLocalation();
			if(!(sendAttachments.getFiletype().equals("pdf")||sendAttachments.getFiletype().equals("PDF"))){
				toPage("");
				return;
			}
			MergePdf mergePdf = new MergePdf();
			boolean isJm = mergePdf.pdfIsNeedPwd(filePath);
			if(isJm){
				pwdPdfAttIds.append(sendAttachments.getId()+",");
			}
			
//			File fileObj = new File(filePath);
//			boolean isExist = fileObj.exists();
//			try {
//				document = new com.itextpdf.text.Document(new com.itextpdf.text.pdf.PdfReader(filePath).getPageSize(1));
//				com.itextpdf.text.pdf.PdfCopy copy = new com.itextpdf.text.pdf.PdfCopy(document, new FileOutputStream(filePath));
//				document.open();
//				com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(filePath);
//					int n = reader.getNumberOfPages();
//					for (int j = 1; j <= n; j++) {
//						document.newPage();
//						com.itextpdf.text.pdf.PdfImportedPage page = copy.getImportedPage(reader, j);
//						copy.addPage(page);
//					}
//				}
//			catch (Exception e) {
//				e.printStackTrace();
//				pwdPdfAttIds.append(sendAttachments.getId()+",");
//			}finally{
//				if(null != document){
//					document.close();
//				}
//			}
		//}
		if(pwdPdfAttIds.length()>0){
			toPage(pwdPdfAttIds.substring(0,pwdPdfAttIds.length()-1));
		}else{
			toPage("");
		}
	}
	
	public void deleteFj(){
		String ids = getRequest().getParameter("ids");
		if(CommonUtil.stringIsNULL(ids)){
			return;
		}
		ids = ("'"+ids+"'").replaceAll(",", "','");
		attachmentService.deleteAttsByIds(ids);
	}
}
