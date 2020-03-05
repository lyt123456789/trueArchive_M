package cn.com.trueway.workflow.core.action.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.action.TableInfoAction;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.service.TableInfoExtendService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TrueJsonService;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.ThirdpartyInterfaceLog;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ThirdpartyInterfaceLogService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.GenePdfUtil;

/** 
 * ClassName: PushDataToPdfThread <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年7月30日 下午2:00:05 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public class PushDataToPdfThread extends Thread{
	
	private ThirdpartyInterfaceLogService 	interfaceLogService;

	private TableInfoExtendService 			tableInfoExtendService;
	
	private TableInfoService 				tableInfoService;

	private AttachmentService				attachmentService;
	
	private TrueJsonService					trueJsonService;
	
	private ZwkjFormService					zwkjFormService;
	
	private WfItem							wfItem;
	
	private String 							instanceId;
		
	private String							fileName;
	
	private String							filePath;
	
	private Map<String, Object> 			param;
	
	private TableInfoAction					tableInfoAction;
	
	public PushDataToPdfThread(
			ThirdpartyInterfaceLogService interfaceLogService,
			TableInfoExtendService tableInfoExtendService, WfItem wfItem,
			String instanceId, String fileName, String filePath) {
		super();
		this.interfaceLogService = interfaceLogService;
		this.tableInfoExtendService = tableInfoExtendService;
		this.wfItem = wfItem;
		this.instanceId = instanceId;
		this.fileName = fileName;
		this.filePath = filePath;
	}


	public PushDataToPdfThread(
			ThirdpartyInterfaceLogService interfaceLogService,
			TableInfoExtendService tableInfoExtendService,AttachmentService attachmentService,
			TableInfoService tableInfoService,TrueJsonService trueJsonService,ZwkjFormService zwkjFormService,
			TableInfoAction tableInfoAction, WfItem wfItem, String instanceId,
			String fileName, Map<String, Object> param) {
		super();
		this.interfaceLogService = interfaceLogService;
		this.tableInfoExtendService = tableInfoExtendService;
		this.tableInfoService = tableInfoService;
		this.attachmentService = attachmentService;
		this.trueJsonService = trueJsonService;
		this.zwkjFormService = zwkjFormService;
		this.tableInfoAction = tableInfoAction;
		this.wfItem = wfItem;
		this.instanceId = instanceId;
		this.fileName = fileName;
		this.param = param;
	}


	@Override
	public void run() {
		
		String gdPdfPath = "";
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String flexPdfPath = (String)param.get("flexPdfPath");
		String newPdfPath = (String)param.get("newPdfPath");
		String oldFormId = (String)param.get("oldFormId");
		String processId = (String)param.get("processId");
		String location = (String)param.get("location");
		DoFile doFile = (DoFile) param.get("doFile");
		ZwkjForm form = zwkjFormService.getOneFormById(oldFormId);
		String pageJson = "";
		if(form!=null){
			pageJson = form.getFormPageJson();
		}
		//永远获取第一步的json数据
		TrueJson trueJson = trueJsonService.findNewestTrueJson(instanceId);
		String commentJson = "";
		commentJson = trueJson.getTrueJson();
		String jsonStr = "{\"location\":" + location + ",\"pageJson\":" + pageJson + ",\"commentJson\":"+commentJson+"}";
		//获取页面风格相关参数
		Map<String, String> map = new HashMap<String, String>();
		List<FormStyle> formStylelist = zwkjFormService.getFormStyle(map);
		String fontSize = "";
		String verSpacing = "";
		String dateFmt = "";
		String font = "";

		if(formStylelist != null && formStylelist.size() > 0){
			FormStyle formStyle = formStylelist.get(0);
			if(formStyle != null){
				fontSize = formStyle.getFontSize();
				verSpacing = formStyle.getVerticalSpacing();
				dateFmt = formStyle.getDateFormat();
				font = formStyle.getFont();
			} 
		}
				
		GenePdfUtil pdfUtil = GenePdfUtil.getInstance();		
		pdfUtil.geneFlexFormWithStamp(flexPdfPath,jsonStr ,fontSize ,verSpacing,dateFmt,font);
		
		//所有附件合成pdf
		String attMergePdfPath = "";
		commentJson = editCommentJsonByPages(commentJson);		
		List<SendAttachments> attList = attachmentService.findSendAttachmentListByInstanceId(instanceId);//所有附件
		String mergePath = processId + "Att_merge.pdf";
		mergePath = FileUploadUtils.getRealFilePath(mergePath, pdfRoot,Constant.GENE_FILE_PATH);
		mergePath = pdfRoot + mergePath;
		try {
			attMergePdfPath = tableInfoAction.toAndCombToPdf(attList, mergePath, commentJson, true);//合并文件
			// 合并正文附件的pdf和表单的pdf
			MergePdf mp = new MergePdf();
			String[] filePaths = new String[2];
			filePaths[0] = flexPdfPath;
			filePaths[1] = attMergePdfPath;
			mp.mergePdfFiles(filePaths, newPdfPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isNotBlank(gdPdfPath)){
			File file = new File(gdPdfPath);
			if(file.exists()){
				doFile.setPdfPath(gdPdfPath);
				try {
					doFile.setPdfData(Hibernate.createBlob(new FileInputStream(file)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				tableInfoService.updateDoFile(doFile);
			}
		}
		
		
		//--6,向第三方推送数据
		String filePath = "";
		if(StringUtils.isNotBlank(gdPdfPath)){
			File file = new File(gdPdfPath);
			if(file.exists()){
				String serverUrl = (String) param.get("serverUrl"); 
				if(gdPdfPath.startsWith(pdfRoot)){
					filePath = serverUrl+ "/form/html/workflow/"+gdPdfPath.substring(pdfRoot.length());
				}
			}
		}
		
		
		String url = wfItem.getInterfaceUrl();
		String tableName = wfItem.getOutSideTabName();
		if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(instanceId) 
				&& StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(filePath)){
			List<String> columnNames = tableInfoExtendService.getColumnNames(tableName);
			String selectColumn = "";
			if(null != columnNames && columnNames.size()>0){
				for (String string : columnNames) {
					selectColumn += string + ",";
				}
			
				if(StringUtils.isNotBlank(selectColumn)){
					selectColumn = selectColumn.substring(0, selectColumn.length()-1);
				}
				List<Object[]> list = tableInfoExtendService.getOutSideTab(tableName, selectColumn, instanceId);
				Object[] objs = null;
				if(list != null && list.size()>0){
					objs = list.get(0);
				}
			
			
				String params = "";
				if(objs != null){
					for (int i = 0; i < columnNames.size(); i++) {
						if(columnNames.get(i).toLowerCase().equals("fileurl") || columnNames.get(i).toLowerCase().equals("filetitle")){
							continue;
						}
						params += columnNames.get(i).toLowerCase() + ";" + objs[i] + "###";
					}
					
					if(StringUtils.isNotBlank(params)){
						params += "fileurl;" +  filePath + "###filetitle;" + fileName;
					}
					
					HttpClient client = new HttpClient();
					try {
						ThirdpartyInterfaceLog log = new ThirdpartyInterfaceLog();
						log.setInstanceId(instanceId);
						log.setInterfaceUrl(url);
						log.setParams(params);
						HttpMethod method = getPostMethod(url,params);
						log.setRequestTime(new Date());
						ThirdpartyInterfaceLog entity = interfaceLogService.add(log);
						client.executeMethod(method);
						String result = method.getResponseBodyAsString();
						entity.setResult(result);
						entity.setBackTime(new Date());
						interfaceLogService.update(entity);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (HttpException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private  HttpMethod getPostMethod(String url, String param){
		try {
			PostMethod post=new PostMethod(url);
			if(StringUtils.isNotBlank(param)){
				String [] params = param.split("###");
				for (String string : params) {
					String [] keyVa = string.split(";");
					if(keyVa.length>1){
						post.addParameter(keyVa[0], keyVa[1]);
					}
				}
			}
			return post;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String editCommentJsonByPages(String json) {
		//1.意见为空
		if(json==null || json.equals("")||json.equals("{}") ||json.equals("[]")){
			return "";
		}
		//3.找寻相同节点,相同人员调整的内容(后续拓展)
		//2.检测processIdshifo
		Map<String, String> comment = new HashMap<String, String>();
		//解析意见
		net.sf.json.JSONArray js;
		try{
			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(json);
			if(obj.containsKey("pages")){
				js	= obj.getJSONArray("pages");
				int size = js.size();
				for(int i = 0; i<size; i++){
					net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject)js.get(i);
					int page = 0;
					try{
						page = jsonObject.getInt("page");
						String old_json = jsonObject.toString();
						String new_json = old_json.replaceAll("\"page\":"+page+"", "\"page\":"+(page-1));
						comment.put(old_json, new_json);
					}catch (Exception e) {
						
					}
				}
			}
			//遍历map,将意见内容修改编辑权限修改调整
			for (String key : comment.keySet()) {
				String value = comment.get(key);
				json = json.replace(key, value);
			}
			return json;
		}catch (Exception e) {
			return json;
		}
	}
}
