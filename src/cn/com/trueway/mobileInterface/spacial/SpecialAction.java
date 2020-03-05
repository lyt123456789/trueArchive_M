package cn.com.trueway.mobileInterface.spacial;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.DocumentHandlerNew;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.HtmlToPdf;
import cn.com.trueway.base.util.JacobWordUtil;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.UrlCatcher;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.base.util.WordToPdfOfPrinter;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.document.component.taglib.comment.model.po.Comment;
import cn.com.trueway.document.component.taglib.docNumber.service.DocNumberService;
import cn.com.trueway.mobileInterface.spacial.bean.SpecialFj;
import cn.com.trueway.mobileInterface.spacial.bean.SpecialInfo;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfCyName;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.LabelService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TemplateService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.TagBean;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

/**
 * 专项办手机接口
 * @author Administrator
 *
 */
public class SpecialAction extends BaseAction{
	private SpecialService specialService;
	private TableInfoService tableInfoService;
	private WorkflowBasicFlowService workflowBasicFlowService;
	private AttachmentService attachmentService;
	private ZwkjFormService zwkjFormService;
	private DocNumberService docNumberService;
	private TemplateService templateService;
	private LabelService labelService;
	private DictionaryService dictionaryService;
	private FlowService flowService ; 
	
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public LabelService getLabelService() {
		return labelService;
	}

	public void setLabelService(LabelService labelService) {
		this.labelService = labelService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	public DocNumberService getDocNumberService() {
		return docNumberService;
	}

	public void setDocNumberService(DocNumberService docNumberService) {
		this.docNumberService = docNumberService;
	}

	public SpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(SpecialService specialService) {
		this.specialService = specialService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	/**
	 * 上传附件
	 */
	public void uploadFile(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		try {
			InputStream in = getRequest().getInputStream();	//文件流
			String bathPath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String year = new SimpleDateFormat("yyyy").format(new Date());
			String month =  new SimpleDateFormat("MM").format(new Date());
			String day =  new SimpleDateFormat("dd").format(new Date());
			String fname = UuidGenerator.generate36UUID()+".tmp";
			String locPath = "attachments/"+year+"/"+month+"/"+day;
			String folder = bathPath+locPath;
			//上传附件
			boolean flag = UploadFileUtil.doUploadStreamWrite(in, folder, fname);
			if(flag){ 
				SendAttachments att = new SendAttachments();
				att.setLocalation(locPath+File.separator+fname);	//文件路径
				att.setFiletime(new Date());						//上传时间
				File upFile = new File(folder+File.separator+fname);
				if(upFile.exists()){
					att.setFilesize(upFile.length());				//文件大小
				}
				att.setDocguid("-1");
				att.setFilename("0");
				att.setFiletype("0");
				//保存附件信息
				SendAttachments sAtt = attachmentService.addSendAtts(att);			
//				System.out.println(att.getId());
				out.print("'success':'0','fileid':'"+att.getId()+"'");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			out.print("'success':'0','fileid':''");
		}
	}
	
	/**
	 * 专项办申请
	 */
	@SuppressWarnings("unchecked")
	public void saveSpecialApply(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		try {
			out = getResponse().getWriter();
			String formJson = "";
			String imageJson ="";
			if(getRequest().getHeader("formjson") != null){
				formJson = URLDecoder.decode(getRequest().getHeader("formjson"),"UTF-8");
			}
			if(getRequest().getHeader("files")!= null){
				imageJson = URLDecoder.decode(getRequest().getHeader("files"),"UTF-8");
			}
//			String formJson = URLDecoder.decode(getRequest().getHeader("formjson"),"UTF-8");
//			String imageJson = URLDecoder.decode(getRequest().getHeader("files"),"UTF-8");
			// 取得客户端传的数据流
			InputStream iStream = getRequest().getInputStream();
			
			// 转化为byte
			byte[] imgData = readStream(iStream);
//			byte[] imgData = readStream(new FileInputStream(new File("D:/111.jpg")));
			JSONObject json = JSONObject.fromObject(formJson);
//			"{7F000001-0000-0000-7734-568D00000106}";//
			String empId = (String) json.get("USERID");							//用户id
			
			String zxbdw = (String) json.get("ZXBDW");	//专项办单位 0：环整办 1：渣土办 2：废整办 3：城管办 4：广告办 5：控违办 6：弄环办
			String jbdw = (String) json.get("JBDW");	//交办单位
			String ly = (String) json.get("LY");		//来源
			String dd = (String) json.get("DD");		//地点
			String flfg = (String) json.get("FLFG");	//法律法规
//			String flfg = "法律法规";	//法律法规
			String jbsxms = (String) json.get("ZXBDW"); //交办事项概述
			String zgyq	= (String) json.get("ZGYQ");	//整改要求
			String zgsj = (String) json.get("ZGSJ");	//整改时间
			String lxr = (String) json.get("LXR");		//联系人
			String lxdh = (String) json.get("LXDH");	//联系电话
			String sj = (String) json.get("SJ");		//时间
			SpecialInfo info = new SpecialInfo( zxbdw, jbdw, ly, dd,flfg, jbsxms, zgyq, zgsj, lxr,lxdh, sj);
			
			String processId = UuidGenerator.generate36UUID();
			String instanceId = UuidGenerator.generate36UUID();
//			String nodeId = SystemParamConfigUtil.getParamValueByParam("dbNodeId");			//"393692F1-8144-4F5A-A0DF-BBBE7F78D864";//所在节点id
//			String itemId = SystemParamConfigUtil.getParamValueByParam("dbItemId");			//"72D7113D-03B3-47CA-A6E2-4E5DA972A7C9";//事项id
			
			String nodeId = "";
			String itemId = "";
			String fileFileName = "";
			
			if("0".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("hzb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("hzb_itemId");
				fileFileName = "环整办";
			}else if("1".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("ztb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("ztb_itemId");
				fileFileName = "渣土办 ";
			}else if("2".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("fzb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("fzb_itemId");
				fileFileName = "废整办";
			}else if("3".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("cgb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("cgb_itemId");
				fileFileName = "城管办";
			}else if("4".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("ggb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("ggb_itemId");
				fileFileName = "广告办";
			}else if("5".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("kwb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("kwb_itemId");
				fileFileName = "控违办";
			}else if("6".equals(zxbdw)){
				nodeId = SystemParamConfigUtil.getParamValueByParam("nhb_nodeId");
				itemId = SystemParamConfigUtil.getParamValueByParam("nhb_itemId");
				fileFileName = "农环办";
			}
			fileFileName = jbdw+zgsj+fileFileName+"套打表单";
			Object[] dbInfo = tableInfoService.getToDbInfoIds(nodeId);//查找流程id、表单id
			if(dbInfo==null){
				System.out.println("!!! 您配置的节点id或事项id有误!!!");
				out.print("{\"success\":\"0\"}");
				return ;
			}
			String formId = dbInfo[0].toString();		//	"6ABEF587-85D8-451C-9419-781FE94A11BF";
			String workFlowId = dbInfo[1].toString();	//	"C306DB74-4185-4369-85B7-1D3A76FD9F5C";
			
			//保存表单数据
			specialService.addSpecialInfo(info,instanceId,formId,workFlowId,processId,zxbdw);
			
			String year = new SimpleDateFormat("yyyy").format(new Date());
			String month =  new SimpleDateFormat("MM").format(new Date());
			String day =  new SimpleDateFormat("dd").format(new Date());
			
			String basePath =  SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			String dstFolder ="attachments/"+year+"/"+month+"/"+day;
			//保存附件数据
			if(imgData.length > 0){
				
				int length = 0;
				JSONArray jsonArr = JSONArray.fromObject(imageJson);
				String kzm = ".jpg";
				for(int i=0;i<jsonArr.size();i++){
					JSONObject jsonF = jsonArr.getJSONObject(i);
					int byteLength = Integer.valueOf(jsonF.getString("len"));
					byte[] ttByte = new byte[byteLength];
					
					System.arraycopy(imgData, length, ttByte, 0, byteLength);
					if("image/jpeg".equals(jsonF.getString("type"))){
						kzm = ".jpg";
					}else if("image/png".equals(jsonF.getString("type"))){
						kzm = ".png";
					}else{
						kzm = ".jpg";
					}
					
					String fname = UuidGenerator.generate36UUID()+kzm;
					//保存文件
					FileUploadUtils.getFile(ttByte, basePath + dstFolder+"/"+fname, basePath+dstFolder);
					
					SendAttachments att = new SendAttachments();
					att.setLocalation(dstFolder+"/"+fname);	//文件路径
					att.setFiletime(new Date());						//上传时间
					File upFile = new File(basePath + dstFolder+"/"+fname);
					if(upFile.exists()){
						att.setFilesize(upFile.length());				//文件大小
					}
					att.setDocguid(instanceId+"att");
					att.setFilename(fname);
					att.setFiletype(kzm);
					//保存附件信息
					attachmentService.addSendAtts(att);	
					length += byteLength;
				}
			}
			out.print("{\"success\":\"1\"}");
			//套打保存表单
			String value = "";
			value = this.saveForm("",2,instanceId,formId,nodeId,workFlowId);
			WfNode node = workflowBasicFlowService.getWfNode(nodeId);
			String templateId = node.getWfn_defaulttemplate();
			WfTemplate template = templateService.getTemplateById(templateId);
			List<WfLabel> list = labelService.getLabelByTemplateId(templateId);
			Map<String, String> map = templateService.getTemplateValue(list, instanceId, "");
			DocumentHandlerNew doc = new DocumentHandlerNew();
//			File file = doc.createDoc2(map, template,zxbdw);
			
			//图片
			String imagePath="";
			List labelList = new ArrayList();
			for(WfLabel label : list){
				if("4".equals(label.getVc_type())){
					String vc_label = label.getVc_label();
					imagePath = map.get(vc_label+"_attachmentPic");
					labelList.add(vc_label+"_attachmentPic");
				}
			}
			//插入普通字段数据
			File file = doc.createDocByJacob(map, template,zxbdw,labelList);
			//插入图片
			if(CommonUtil.stringNotNULL(imagePath)){
				String[] paths = imagePath.split(";");
				List imgUrls = new ArrayList();
				List targWords = new ArrayList();
				for(int i=0;i<paths.length;i++){
					if(!"".equals(paths[i])){
						String imgurl = basePath + dstFolder+File.separator+paths[i];
						imgurl.replaceAll("/", "\\\\");
//						String imgurl = "c:\\1.jpg";
//						String[] targWords = {"{{pic}}"};
						imgUrls.add(imgurl);
						targWords.add("{{pic}}");
					}
				}
				JacobWordUtil.insertImage(file.getAbsolutePath(), imgUrls, targWords);
			}else{
				//没有图片则将标签替换成空字符串
				JacobWordUtil.replaceWord(file.getAbsolutePath(), "", "{{pic}}");
				
			}
			
			this.saveTemplateToDb(nodeId, itemId, fileFileName, file,empId);
			//保存流程数据
			
//			String title = getTitle( workFlowId, formId, instanceId);	//流程标题
			
		} catch (Exception e) {
			
			/*JSONObject job=new JSONObject();
			Map outmap=new HashMap();
			outmap.put("", )*/
			out.print("{\"success\":\"0\"}");
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		
	}
	
	private String getTitle(String workFlowId,String formId,String instanceId){
		WfMain wf_main = workflowBasicFlowService.getWfMainById(workFlowId);
		String title = getTitle(wf_main, instanceId);//{标题}test---找到手动输入的值
		String name = tableInfoService.getTableAndColumnName(wf_main.getWfm_title_column());//tableName;columnName#tableName;columnName
		//查找列名,用于页面判断
		String columnName = ""; 
		String[] names = name.split("#");
		for (String str : names) {
			columnName += str.split(";")[1]+","; 
		}
		if(!("").equals(columnName) && columnName.length() > 0){
			columnName = columnName.substring(0,columnName.length()-1);
		}
		return title+";"+columnName;
	}
	
	public String getTitle(WfMain wfMain,String instanceId){
		String str=wfMain.getWfm_title_name();
		String tablename=wfMain.getWfm_title_table();
		String names=wfMain.getWfm_title_name();
		String ids=wfMain.getWfm_title_column();
		
		
		WfTableInfo table=tableInfoService.getTableById(tablename);
		if (table!=null) {
			tablename=table.getVc_tablename();
		}
		//解析标题字符串     2013年{姓名}3月{性别}4日
		List<String> cloumnNames=new ArrayList<String>();
		
		String regEx = "\\{[^\\}\\{]*\\}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(names);
		String tag = "";
		String all = "";
		while(m.find()){
			//标签和内容
			tag = m.group();
			if(tag != null && !tag.equals("")){
				tag = tag.substring(1,tag.length()-1);
				cloumnNames.add(tag);
			}
		}
		String[] idsArr=ids==null?null:ids.split(",");
		
		if (cloumnNames!=null) {
			for (int i = 0; i < cloumnNames.size(); i++) {
				WfFieldInfo filed=tableInfoService.getFieldById(idsArr[i]);
				String sql="select "+filed.getVc_fieldname()+",'替代' from "+tablename+" t where t.INSTANCEID='"+instanceId+"'";
				List<Object[]> list=tableInfoService.getListBySql(sql);
				String value="";
				if (list!=null&&list.size()>0) {
					value=String.valueOf(list.get(0)[0]);
				}
				str=str.replace("{"+cloumnNames.get(i)+"}", value);
				
			}
		}
		
		return str;
	}
	
	/**
	 * 处理流
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;  
	}
	/**
	 * 转换jsonobject
	 * @return
	 */
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	private String saveForm(String oldformId,int todo,String instanceId,String formId,String nodeid,String workFlowId){
		//第一步保存
		if(oldformId==null || ("").equals(oldformId)){
			oldformId = formId;
		}
		//读取表单所设定的所有表
		List<FormTagMapColumn> tableNameAllLists = zwkjFormService.getTableNameByFormId(oldformId);
		//========================列表类型=============================//
		List<FormTagMapColumn> haveLists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameHaveList = new ArrayList<String>();
		//========================非列表类型=============================//
		List<FormTagMapColumn> lists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameList = new ArrayList<String>();
		//列表和非列表类型的分开存入list中
		for (FormTagMapColumn ft : tableNameAllLists) {
			if(ft.getListId()!=null && !("").equals(ft.getListId())){
				haveLists.add(ft);
			}else{
				lists.add(ft);
			}
		}
		//读取两个list中的tableName(去重之后的)
		for (int i = 0, n = haveLists.size(); i < n; i++) { //列表型
			if(!tableNameHaveList.contains(haveLists.get(i).getTablename())){
				tableNameHaveList.add(haveLists.get(i).getTablename());
			}
		}
		for (int i = 0, n = lists.size(); i < n; i++) { //非列表型
			if(!tableNameList.contains(lists.get(i).getTablename())){
				tableNameList.add(lists.get(i).getTablename());
			}
		}
		//========================列表类型=============================//
		StringBuffer tagHaveName = new StringBuffer();
		if(tableNameHaveList.size()!=0 && !("").equals(tableNameHaveList) && tableNameHaveList != null){
			List<Map> list=new ArrayList<Map>();//存放查询出来的值
			for (String tableHaveName : tableNameHaveList) { //T_WF_OFFICE_PERSON,T_WF_OFFICE_WORKLIST
				//取出一类数据
				List<FormTagMapColumn> ftmcList = zwkjFormService.getFormTagMapColumnByFormId(oldformId,formId,tableHaveName,2,"");
				//取出表名
				String tabHaveName = ftmcList.get(0).getTablename();
				String columnHaveName = "";
				StringBuffer tagHaveNameValue = new StringBuffer();
				//查找字段类型 Date/varchar... 
				List<WfFieldInfo>  wfFieldInfoList = zwkjFormService.findWfFileldInfoByTableName(tableHaveName);
				//查询数据库已有的数据
				List<Map> mapList = zwkjFormService.findTableByFormId(tabHaveName,oldformId,instanceId); 
				Map m1 = new HashMap();
				for (FormTagMapColumn ftmc : ftmcList) {  //JL_GZSJ,JL_GZDD,JL_ZW,JL_XZ
					if(todo==2){
						//================查询表单的值(从待办点进来)==========================
						if(mapList.size()!=0 && !("").equals(mapList) && mapList != null){
							String[] haveValue = new String[mapList.size()];//定义数组的长度，即查出来的数据条数
							for (int i = 0, n = mapList.size(); i < n; i++) {
								String tagNameHaveValue = mapList.get(i).get(ftmc.getColumnname())==null?null:mapList.get(i).get(ftmc.getColumnname())+""; //列所对应的的值
								if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
									for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
										//clob类型装换成String
										if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
											if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 3){
												//此处已可以查出大字段内容
												tagNameHaveValue=tableInfoService.getClob(tabHaveName, instanceId, wfFieldInfo.getVc_fieldname(),formId);
											}
										}
									}
								}
								if(("null").equals(tagNameHaveValue) || tagNameHaveValue == null){
									tagNameHaveValue="";
								}
								haveValue[i] = tagNameHaveValue;
							}
							tagHaveName.append(ftmc.getFormtagname()+","); //；列的属性名
							m1.put(ftmc.getColumnname().toLowerCase(), haveValue);
						}
					}else{
						//=================保存或更新和点“完成”时=================
						if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
							columnHaveName += ftmc.getColumnname()+","; //列名    
							//表里的一类数据    格式：jl_gzsj=aaa,bbb,ccc,
							String tagHaveNameBak = "'"+(getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()))+"',";
							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										String thn = tagHaveNameBak.substring(0,tagHaveNameBak.length()-1);
										if(thn.split(",").length > 1){
											for (int i = 0, n = thn.split(",").length - 1; i < n; i++) {
												if(!("").equals(thn.split(",")[i])){
													tagHaveNameBak += "to_date('"+thn.split(",")[i]+"','yyyy-MM-dd'),"; //列所对应的的值，重新拼接  
												}else{
													tagHaveNameBak += "\"\",";//为空，则加空字符串
												}
											}
										}else{
											tagHaveNameBak = "to_date('"+tagHaveNameBak.substring(1,tagHaveNameBak.length()-2)+"','yyyy-MM-dd'),"; //列所对应的的值  
										}
									}
								}
							}
							tagHaveName.append(ftmc.getFormtagname()+","); //；列的属性名
							tagHaveNameValue.append(tagHaveNameBak); //列所对应的的值  格式：'a;1','a;2','a;3','a;4',
						}
					}
				}
				if(todo!=2){
					//对多列循环入库
					String[] tagMany = tagHaveNameValue.substring(0,tagHaveNameValue.length()-1).split(",");
					int cloumnSize = 0; //一共有几行
					for (int i = 0, n = tagMany.length; i < n; i++) { // 格式：['a;1','a;2','a;3','a;4']
						String taVal = tagMany[i].substring(1,tagMany[i].length()-1);
						String[] tgma = taVal.split(";");
						cloumnSize = tgma.length;
						break;
					}
					List<String[]> insertDataList = new ArrayList<String[]>(); 
					for (int i = 0, n = cloumnSize; i < n; i++) {
						StringBuffer saveValue = new StringBuffer();
						String cloumHaNa = columnHaveName; //重新初始化
						StringBuffer  isNull = new StringBuffer(); //判断一行是否为空
						for (int j = 0, m = tagMany.length; j < m; j++) {
							String taVal = tagMany[j].substring(1,tagMany[j].length()-1);
							isNull.append(taVal.split(";")[i]);
							saveValue.append("'" + taVal.split(";")[i] + "',"); //取出每一行的值，拼接
						}
						if(!("").equals(isNull)){
							for (WfFieldInfo wfColumn : wfFieldInfoList) {
								//基础字段(换表单如果有新的表加入，需加上基础字段)  
								if(wfColumn.getI_tableid()==null || ("").equals(wfColumn.getI_tableid())){
									if(cloumHaNa.toLowerCase().indexOf(wfColumn.getVc_fieldname().toLowerCase())<0){
										cloumHaNa += wfColumn.getVc_fieldname().toUpperCase()+",";
										//入库为新的formId
										if(("formid").equals(wfColumn.getVc_fieldname().toLowerCase())){
											saveValue.append("'"+formId+"',");
										}else{
											saveValue.append("'"+getRequest().getParameter(wfColumn.getVc_fieldname())+"',");
										}
									}
								}
							}
							//用于往下一步发送，删除之前的数据，再循环插入新数据
							String[] dataList = new String[3];//一个为表名，一个为列名，一个为列值
							dataList[0] = tabHaveName;
							dataList[1] = cloumHaNa;
							dataList[2] = saveValue.toString();
							insertDataList.add(dataList);
						}
					}
					if(todo!=0){
						if(todo==1){
							//更新--先删除再重新插库
							zwkjFormService.deleteForm(tableHaveName,formId,instanceId);
							if(tagHaveNameValue!=null && !("").equals(tagHaveNameValue)){
								for (String[] data : insertDataList) {
									zwkjFormService.saveForm(data[0], data[1].substring(0,data[1].length()-1),data[2].substring(0,data[2].length()-1));
								}
							}
						}
					}
				}else{
					list.add(m1); //拼接数据放入map中格式化(查询数据)
				}
				getRequest().setAttribute("tagHaveName", tagHaveName.substring(0, tagHaveName.length()-1));  
			}
			String valueHaveList = new Gson().toJson(list);
			getRequest().getSession().setAttribute("listValues", valueHaveList);//页面回值，存session
		}
		
		//========================非列表类型=============================//
		String value = "";//页面回值
		StringBuffer tagNameForm = new StringBuffer();//标签属性名
		StringBuffer searchValue = new StringBuffer();
		if(tableNameList.size()!=0 && !("").equals(tableNameList) && tableNameList != null){
			for (String tableName : tableNameList) {
				//取出一类数据
				List<FormTagMapColumn> ftmcList = zwkjFormService.getFormTagMapColumnByFormId(oldformId,formId,tableName,2,"");
				//取出表名
				String tabName = ftmcList.get(0).getTablename();
				String columnName = "";
				StringBuffer tagName = new StringBuffer();
				List<Map> mapList = zwkjFormService.findTableByFormId(tabName,oldformId,instanceId); 
				//查找字段类型 Date/varchar...
				List<WfFieldInfo>  wfFieldInfoList = zwkjFormService.findWfFileldInfoByTableName(tableName);
				for (FormTagMapColumn ftmc : ftmcList) {
					if(todo==2){
						//================查询表单的值(从待办点进来)==========================
						if(mapList.size()!=0 && !("").equals(mapList) && mapList != null){
							String tagNameValue = mapList.get(0).get(ftmc.getColumnname())==null?null:mapList.get(0).get(ftmc.getColumnname())+""; //列所对应的的值
							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//clob类型装换成String
									if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
										if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 3){
											//此处已可以查出大字段内容
											tagNameValue=tableInfoService.getClob(tableName, instanceId, wfFieldInfo.getVc_fieldname(),formId);
										}
									}
								}
					  		}
							if(("null").equals(tagNameValue) || tagNameValue == null){
								tagNameValue="";
							}
							searchValue.append(ftmc.getFormtagname()+":"+tagNameValue+";");
						}
					  	if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
					  		columnName += ftmc.getColumnname().toLowerCase()+","; //列名   
					  		String tagNameBak = mapList.get(0).get(ftmc.getColumnname())==null?null:mapList.get(0).get(ftmc.getColumnname())+""; //列所对应的的值  
					  		if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										if(wfFieldInfo.getVc_fieldname()!=null && !("").equals(wfFieldInfo.getVc_fieldname())){
											if(!("").equals(tagNameBak) && tagNameBak != null){
												if(tagNameBak.length()>=10 ){
													tagNameBak = tagNameBak.substring(0,10); //列所对应的的值(查询时截取，否则带时分秒里有"："影响)	
//													tagNameBak = "to_date('"+tagNameBak.substring(1,tagNameBak.length()-2)+"','yyyy-MM-dd'),"; //列所对应的的值  
												}
											}
										}
									}
								}
							}
					  		tagName.append("'"+tagNameBak+"',"); //列所对应的的值  
							tagNameForm.append(ftmc.getFormtagname()+","); 
						}
					}else{
						//=================保存或更新和点“完成”时=================
						if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
							columnName += ftmc.getColumnname()+","; //列名    
							//表里的一类数据  
							String columnValue = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
							
							//-----------------------------文号入库---------------start------------------
							//根据流程ID判断是发文还是办文确定序号
							WfItem item = tableInfoService.findItemByWorkFlowId(workFlowId);
							if(item!=null){
								if(item.getVc_sxlx().equals(Constant.DEFINE_TYPE_SEND)){  //发文
//-------need to do----------------//TODO 取出业务表里的文号，入库到文号表,这里需写活(取文号对应的字段的值)--写死了，需扩展
									if(("wh").equals(ftmc.getColumnname().toLowerCase())){
										String docNum = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
										DocNumberWhFw docNumberWhFw = docNumberService.findDocNumFw(instanceId);
										if (docNumberWhFw == null) {
											docNumberWhFw = new DocNumberWhFw();
										}
										docNumberWhFw.setFormId(formId);
										docNumberWhFw.setInstanceId(instanceId);
										docNumberWhFw.setWorkflowId(workFlowId);
										docNumberWhFw = setDocNumberWhFw(docNumberWhFw,docNum);
										if (docNumberWhFw != null) {
											docNumberService.updateFw(docNumberWhFw);
										}else{
											docNumberService.addFw(docNumberWhFw);
										}
									}
								}else if((item.getVc_sxlx().equals(Constant.DEFINE_TYPE_DO) || ("2").equals(item.getVc_sxlx()))){ //办文和传阅
									if(("bwh").equals(ftmc.getColumnname().toLowerCase())){
										String docNum = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
										DocNumberWhBw docNumberWhBw = docNumberService.findDocNumBw(instanceId);
										if (docNumberWhBw == null) {
											docNumberWhBw = new DocNumberWhBw();
										}
										docNumberWhBw.setFormId(formId);
										docNumberWhBw.setInstanceId(instanceId);
										List<String> depIds = (List<String>)getSession().getAttribute(MyConstants.DEPARMENT_IDS);
										String webId = depIds.get(0);
										docNumberWhBw.setWorkflowId(workFlowId);
										docNumberWhBw.setWebId(webId);
										//办文的类型-----暂时设置为0,如有作废功能,需更新
										docNumberWhBw.setResult(0);
										docNumberWhBw = setDocNumberWhBw(docNumberWhBw,docNum);
										if (docNumberWhBw != null) {
											docNumberService.updateBw(docNumberWhBw);
										}else{
											docNumberService.addBw(docNumberWhBw);
										}
									}
								}
							}
							//-----------------------------文号入库---------------end------------------
							
							value += ftmc.getColumnname()+":"+columnValue+";";
							String tagNameBak = "'"+(getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()))+"',";
							//类型是radio、checkbox时--undefined
							if("'undefined'".equals(tagNameBak)){
								tagNameBak = "";
							}
							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										tagNameBak = "to_date('"+tagNameBak.substring(1,tagNameBak.length()-2)+"','yyyy-MM-dd'),"; //列所对应的的值  
									}
								}
							}
							tagName.append(tagNameBak); //列所对应的的值  
							tagNameForm.append(ftmc.getFormtagname()+","); 
						}
					}
				} 
				for (WfFieldInfo wfColumn : wfFieldInfoList) {
					//基础字段(换表单如果有新的表加入，需加上基础字段)  
					if(wfColumn.getI_tableid()==null || ("").equals(wfColumn.getI_tableid())){
						if(columnName.toLowerCase().indexOf(wfColumn.getVc_fieldname().toLowerCase())<0){
							columnName += wfColumn.getVc_fieldname().toUpperCase()+",";
							//入库为新的formId
							if(("formid").equals(wfColumn.getVc_fieldname().toLowerCase())){
								tagName.append("'"+formId+"',");
							}else{
								tagName.append("'"+getRequest().getParameter(wfColumn.getVc_fieldname())+"',");
							}
						}
					}
				}
				if(todo!=0){
					if(todo==1){
						//更新--先删除再重新插库
						zwkjFormService.deleteForm(tableName,formId,instanceId);
						zwkjFormService.saveForm(tabName, columnName.substring(0,columnName.length()-1),tagName.substring(0,tagName.length()-1));
					}
				}
				if(!("").equals(searchValue.toString())){
					value = searchValue.toString();
				}
				getRequest().setAttribute("tagNameForm", tagNameForm.substring(0, tagNameForm.length()-1));  
			}
		}
		return value;
	}
	
	
	/**
	 * 文号拆分入库(发文)
	 * @param dn
	 * @param docNum
	 */
	private DocNumberWhFw setDocNumberWhFw(DocNumberWhFw dn, String docNum){
		try {
			//取字号
			String regChina = "[\u4e00-\u9fa5]+";
			Pattern p = Pattern.compile(regChina);
			Matcher m = p.matcher(docNum);
			if (m.find()) {
				dn.setJgdz(m.group());
			}
			//取年号
			p = Pattern.compile("\\d{4}");
			m = p.matcher(docNum);
			if(m.find()){
				dn.setFwnh(m.group());
			}
			//取序号
			p = Pattern.compile("\\d+号");
			m = p.matcher(docNum);
			if (m.find()) {
				dn.setFwxh(m.group().replace("号", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dn;
	}
	
	/**
	 * 文号拆分入库(办文)
	 * @param dn
	 * @param docNum
	 */
	private DocNumberWhBw setDocNumberWhBw(DocNumberWhBw dn,String docNum){
		//取字号
		String regChina = "[\u4e00-\u9fa5]+|-|[[A-Za-z]+[\u4e00-\u9fa5]+|[[A-Za-z]+-]+]+";
        Pattern p = Pattern.compile(regChina);
        Matcher m = p.matcher(docNum);
        if (m.find()) {
        	dn.setBwlx(m.group());
        }
        //取年号
        p = Pattern.compile("\\d{4}");
        m = p.matcher(docNum);
        if (m.find()) {
         	 dn.setBwnh(m.group());
        }
        //取序号
        p = Pattern.compile("\\d+号");
        m = p.matcher(docNum);
        if (m.find()) {
        	String str = m.group();
        	str = str.replace("号", "");
        	if(str.length()>=5){
        		dn.setLwdwlx(str.substring(0,2));
        		dn.setBwxh(str.substring(2));
        	}else{
        		dn.setBwxh(str);
        	}
        }
        //取得附号
//        p = Pattern.compile("附\\d{2}号");
//        m = p.matcher(docNum);
//        while(m.find()){
//        	p = Pattern.compile("\\d{2}");
//            m = p.matcher(m.group());
//            if(m.find()){
//           	 dn.setBwfh(m.group());
//            }
//        }
        if("号".equals(dn.getBwlx())) dn.setBwlx(null);
        return dn;
	}
	
	
	private String saveTemplateToDb(String nodeId,String itemId,String fileFileName,File file, String empId){
		String processId = UuidGenerator.generate36UUID();
		String instanceId = UuidGenerator.generate36UUID();
		nodeId = SystemParamConfigUtil.getParamValueByParam("dbNodeId");
		itemId = SystemParamConfigUtil.getParamValueByParam("dbItemId");
		Object[] dbInfo = tableInfoService.getToDbInfoIds(nodeId);//查找流程id、表单id
		if(dbInfo==null){
			System.out.println("!!! 您配置的节点id或事项id有误!!!");
			return null;
		}
		String oldFormId = "";
		String formId = dbInfo[0].toString();		//	"6ABEF587-85D8-451C-9419-781FE94A11BF";
		String workFlowId = dbInfo[1].toString();	//	"C306DB74-4185-4369-85B7-1D3A76FD9F5C";
		
		String zwName = fileFileName+".doc";	//正文文件名
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		String dstPath = FileUploadUtils.getRealFilePath(zwName, basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		SendAttachments attsext = new SendAttachments();
		//获取当前登录用户
		String userId = empId;
		String userName = specialService.findUserName(userId);;
		attsext.setEditer(userId+";"+userName);
		attsext.setDocguid(instanceId+"attzw");
		attsext.setFileindex(0L);
		attsext.setFilename(zwName);// 设置文件名属性
		attsext.setFiletype(FileUploadUtils.getExtension(zwName));// 设置文件类型(后缀名)的属性
		attsext.setFilesize(file.length());// 设置文件大小的属性
		attsext.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
		attsext.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
		attsext.setTitle(zwName);// 设置上传附件标题
		attsext.setType("doc");// 设置上传附件所属类别
		File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
		SendAttachments sAtt =attachmentService.addSendAtts(attsext);
		try{
			FileUploadUtils.copy(file, dstFile);// 完成上传文件，就是将本地文件复制到服务器上,并删除临时文件
		}catch (Exception e) {
			e.printStackTrace();
			attachmentService.deleteAttsext(attsext.getId(), false);
		}
		
		String pdfPath = "";
		
		pdfPath = this.saveForm_ToDb(oldFormId,1,instanceId,formId,nodeId,workFlowId,processId,zwName,"");
		//标题的值
		String vc_title = fileFileName;
		//更新步骤
		tableInfoService.updateProcess( processId, "0", instanceId, nodeId, userId, vc_title, workFlowId,itemId,pdfPath,"",formId,null);
		//同步数据
		excuteProcedure(oldFormId, 1, instanceId, oldFormId, nodeId);
		
//		try {
//			getResponse().getWriter().print(value);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		if(file.exists()){//tomcat下的临时文件
			file.delete();
		}
		return null;
		
	}
	
	private String saveForm_ToDb(String oldformId,int todo,String instanceId,String formId,String nodeid,String workFlowId,String processId,String zwName,String titleColName){
		//第一步保存
		if(oldformId==null || ("").equals(oldformId)){
			oldformId = formId;
		}
		//读取表单所设定的所有表
		List<FormTagMapColumn> tableNameAllLists = zwkjFormService.getTableNameByFormId(oldformId);
		//========================列表类型=============================//
		List<FormTagMapColumn> haveLists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameHaveList = new ArrayList<String>();
		//========================非列表类型=============================//
		List<FormTagMapColumn> lists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameList = new ArrayList<String>();
		//列表和非列表类型的分开存入list中
		for (FormTagMapColumn ft : tableNameAllLists) {
			if(ft.getListId()!=null && !("").equals(ft.getListId())){
				haveLists.add(ft);
			}else{
				lists.add(ft);
			}
		}
		//读取两个list中的tableName(去重之后的)
		for (int i = 0, n = haveLists.size(); i < n; i++) { //列表型
			if(!tableNameHaveList.contains(haveLists.get(i).getTablename())){
				tableNameHaveList.add(haveLists.get(i).getTablename());
			}
		}
		for (int i = 0, n = lists.size(); i < n; i++) { //非列表型
			if(!tableNameList.contains(lists.get(i).getTablename())){
				tableNameList.add(lists.get(i).getTablename());
			}
		}
		//========================列表类型=============================//
		StringBuffer tagHaveName = new StringBuffer();
		if(tableNameHaveList.size()!=0 && !("").equals(tableNameHaveList) && tableNameHaveList != null){
			List<Map> list=new ArrayList<Map>();//存放查询出来的值
			for (String tableHaveName : tableNameHaveList) { //T_WF_OFFICE_PERSON,T_WF_OFFICE_WORKLIST
				//取出一类数据
				List<FormTagMapColumn> ftmcList = zwkjFormService.getFormTagMapColumnByFormId(oldformId,formId,tableHaveName,2,"");
				//取出表名
				String tabHaveName = ftmcList.get(0).getTablename();
				String columnHaveName = "";
				StringBuffer tagHaveNameValue = new StringBuffer();
				//查找字段类型 Date/varchar... 
				List<WfFieldInfo>  wfFieldInfoList = zwkjFormService.findWfFileldInfoByTableName(tableHaveName);
				//查询数据库已有的数据
				List<Map> mapList = zwkjFormService.findTableByFormId(tabHaveName,oldformId,instanceId); 
				Map m1 = new HashMap();
				for (FormTagMapColumn ftmc : ftmcList) {  //JL_GZSJ,JL_GZDD,JL_ZW,JL_XZ
					if(todo==2){
						//================查询表单的值(从待办点进来)==========================
						if(mapList.size()!=0 && !("").equals(mapList) && mapList != null){
							String[] haveValue = new String[mapList.size()];//定义数组的长度，即查出来的数据条数
							for (int i = 0, n = mapList.size(); i < n; i++) {
								String tagNameHaveValue = mapList.get(i).get(ftmc.getColumnname())==null?null:mapList.get(i).get(ftmc.getColumnname())+""; //列所对应的的值
								if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
									for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
										//clob类型装换成String
										if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
											if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 3){
												//此处已可以查出大字段内容
												tagNameHaveValue=tableInfoService.getClob(tabHaveName, instanceId, wfFieldInfo.getVc_fieldname(),formId);
											}
										}
									}
								}
								if(("null").equals(tagNameHaveValue) || tagNameHaveValue == null){
									tagNameHaveValue="";
								}
								haveValue[i] = tagNameHaveValue;
							}
							tagHaveName.append(ftmc.getFormtagname()+","); //；列的属性名
							m1.put(ftmc.getColumnname().toLowerCase(), haveValue);
						}
					}else{
						//=================保存或更新和点“完成”时=================
						if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
							columnHaveName += ftmc.getColumnname()+","; //列名    
							//表里的一类数据    格式：jl_gzsj=aaa,bbb,ccc,
							String tagHaveNameBak = "'"+(getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()))+"',";
							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										String thn = tagHaveNameBak.substring(0,tagHaveNameBak.length()-1);
										if(thn.split(",").length > 1){
											for (int i = 0, n = thn.split(",").length - 1; i < n; i++) {
												if(!("").equals(thn.split(",")[i])){
													tagHaveNameBak += "to_date('"+thn.split(",")[i]+"','yyyy-MM-dd'),"; //列所对应的的值，重新拼接  
												}else{
													tagHaveNameBak += "\"\",";//为空，则加空字符串
												}
											}
										}else{
											tagHaveNameBak = "to_date('"+tagHaveNameBak.substring(1,tagHaveNameBak.length()-2)+"','yyyy-MM-dd'),"; //列所对应的的值  
										}
									}
								}
							}
							tagHaveName.append(ftmc.getFormtagname()+","); //；列的属性名
							tagHaveNameValue.append(tagHaveNameBak); //列所对应的的值  格式：'a;1','a;2','a;3','a;4',
						}
					}
				}
				if(todo!=2){
					//对多列循环入库
					String[] tagMany = tagHaveNameValue.substring(0,tagHaveNameValue.length()-1).split(",");
					int cloumnSize = 0; //一共有几行
					for (int i = 0, n = tagMany.length; i < n; i++) { // 格式：['a;1','a;2','a;3','a;4']
						String taVal = tagMany[i].substring(1,tagMany[i].length()-1);
						String[] tgma = taVal.split(";");
						cloumnSize = tgma.length;
						break;
					}
					List<String[]> insertDataList = new ArrayList<String[]>(); 
					for (int i = 0, n = cloumnSize; i < n; i++) {
						StringBuffer saveValue = new StringBuffer();
						String cloumHaNa = columnHaveName; //重新初始化
						StringBuffer  isNull = new StringBuffer(); //判断一行是否为空
						for (int j = 0, m = tagMany.length; j < m; j++) {
							String taVal = tagMany[j].substring(1,tagMany[j].length()-1);
							isNull.append(taVal.split(";")[i]);
							saveValue.append("'" + taVal.split(";")[i] + "',"); //取出每一行的值，拼接
						}
						if(!("").equals(isNull)){
							for (WfFieldInfo wfColumn : wfFieldInfoList) {
								//基础字段(换表单如果有新的表加入，需加上基础字段)  
								if(wfColumn.getI_tableid()==null || ("").equals(wfColumn.getI_tableid())){
									if(cloumHaNa.toLowerCase().indexOf(wfColumn.getVc_fieldname().toLowerCase())<0){
										cloumHaNa += wfColumn.getVc_fieldname().toUpperCase()+",";
										//入库为新的formId
										if(("formid").equals(wfColumn.getVc_fieldname().toLowerCase())){
											saveValue.append("'"+formId+"',");
										}else{
											saveValue.append("'"+getRequest().getParameter(wfColumn.getVc_fieldname())+"',");
										}
									}
								}
							}
							//用于往下一步发送，删除之前的数据，再循环插入新数据
							String[] dataList = new String[3];//一个为表名，一个为列名，一个为列值
							dataList[0] = tabHaveName;
							dataList[1] = cloumHaNa;
							dataList[2] = saveValue.toString();
							insertDataList.add(dataList);
						}
					}
					if(todo!=0){
						if(todo==1){
							//更新--先删除再重新插库
							zwkjFormService.deleteForm(tableHaveName,formId,instanceId);
							if(tagHaveNameValue!=null && !("").equals(tagHaveNameValue)){
								for (String[] data : insertDataList) {
									zwkjFormService.saveForm(data[0], data[1].substring(0,data[1].length()-1),data[2].substring(0,data[2].length()-1));
								}
							}
						}
					}
				}else{
					list.add(m1); //拼接数据放入map中格式化(查询数据)
				}
				getRequest().setAttribute("tagHaveName", tagHaveName.substring(0, tagHaveName.length()-1));  
			}
			String valueHaveList = new Gson().toJson(list);
			getRequest().getSession().setAttribute("listValues", valueHaveList);//页面回值，存session
		}
		
		//========================非列表类型=============================//
		String value = "";//页面回值
		StringBuffer tagNameForm = new StringBuffer();//标签属性名
		StringBuffer searchValue = new StringBuffer();
		if(tableNameList.size()!=0 && !("").equals(tableNameList) && tableNameList != null){
			for (String tableName : tableNameList) {
				//取出一类数据
				List<FormTagMapColumn> ftmcList = zwkjFormService.getFormTagMapColumnByFormId(oldformId,formId,tableName,2,"");
				//取出表名
				String tabName = ftmcList.get(0).getTablename();
				String columnName = "";
				StringBuffer tagName = new StringBuffer();
				List<Map> mapList = zwkjFormService.findTableByFormId(tabName,oldformId,instanceId); 
				//查找字段类型 Date/varchar...
				List<WfFieldInfo>  wfFieldInfoList = zwkjFormService.findWfFileldInfoByTableName(tableName);
				for (FormTagMapColumn ftmc : ftmcList) {
					if(todo==2){
						//================查询表单的值(从待办点进来)==========================
						if(mapList.size()!=0 && !("").equals(mapList) && mapList != null){
							String tagNameValue = mapList.get(0).get(ftmc.getColumnname())==null?null:mapList.get(0).get(ftmc.getColumnname())+""; //列所对应的的值
							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//clob类型装换成String
									if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
										if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 3){
											//此处已可以查出大字段内容
											tagNameValue=tableInfoService.getClob(tableName, instanceId, wfFieldInfo.getVc_fieldname(),formId);
										}
									}
								}
					  		}
							if(("null").equals(tagNameValue) || tagNameValue == null){
								tagNameValue="";
							}
							searchValue.append(ftmc.getFormtagname()+":"+tagNameValue+";");
						}
					  	if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
					  		columnName += ftmc.getColumnname().toLowerCase()+","; //列名   
					  		String tagNameBak = mapList.get(0).get(ftmc.getColumnname())==null?null:mapList.get(0).get(ftmc.getColumnname())+""; //列所对应的的值  
					  		if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										if(wfFieldInfo.getVc_fieldname()!=null && !("").equals(wfFieldInfo.getVc_fieldname())){
											if(!("").equals(tagNameBak) && tagNameBak != null){
												if(tagNameBak.length()>10 ){
													tagNameBak = tagNameBak.substring(0,10); //列所对应的的值(查询时截取，否则带时分秒里有"："影响)
												}
											}
										}
									}
								}
							}
					  		tagName.append("'"+tagNameBak+"',"); //列所对应的的值  
							tagNameForm.append(ftmc.getFormtagname()+","); 
						}
					}else{
						//=================保存或更新和点“完成”时=================
						if(ftmc.getColumnname()!=null && !("").equals(ftmc.getColumnname())){
							columnName += ftmc.getColumnname()+","; //列名    
							//表里的一类数据  
							String columnValue = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
							
							//-----------------------------文号入库---------------start------------------
//							List<String> depIds = (List<String>)getSession().getAttribute(MyConstants.DEPARMENT_IDS);
//							String webId = depIds.get(0);
							String webId = "tzqcgj";
							//根据流程ID判断是发文还是办文确定序号
							WfItem item = tableInfoService.findItemByWorkFlowId(workFlowId,webId);
							if(item!=null){
								if(item.getVc_sxlx().equals(Constant.DEFINE_TYPE_SEND)){  //发文
//-------need to do----------------//TODO 取出业务表里的文号，入库到文号表,这里需写活(取文号对应的字段的值)--写死了，需扩展
									if(("wh").equals(ftmc.getColumnname().toLowerCase())){
										String docNum = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
										DocNumberWhFw docNumberWhFw = docNumberService.findDocNumFw(instanceId);
										if (docNumberWhFw == null) {
											docNumberWhFw = new DocNumberWhFw();
										}
										docNumberWhFw.setFormId(formId);
										docNumberWhFw.setInstanceId(instanceId);
										docNumberWhFw.setWorkflowId(workFlowId);
										docNumberWhFw = setDocNumberWhFw(docNumberWhFw,docNum);
										if (docNumberWhFw != null) {
											docNumberService.updateFw(docNumberWhFw);
										}else{
											docNumberService.addFw(docNumberWhFw);
										}
									}
								}else if((item.getVc_sxlx().equals(Constant.DEFINE_TYPE_DO) || ("2").equals(item.getVc_sxlx()))){ //办文和传阅
									if(("bwh").equals(ftmc.getColumnname().toLowerCase())){
										String docNum = getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()).toString();
										DocNumberWhBw docNumberWhBw = docNumberService.findDocNumBw(instanceId);
										if (docNumberWhBw == null) {
											docNumberWhBw = new DocNumberWhBw();
										}
										docNumberWhBw.setFormId(formId);
										docNumberWhBw.setInstanceId(instanceId);
										docNumberWhBw.setWorkflowId(workFlowId);
										docNumberWhBw.setWebId(webId);
										//办文的类型-----暂时设置为0,如有作废功能,需更新
										docNumberWhBw.setResult(0);
										docNumberWhBw = setDocNumberWhBw(docNumberWhBw,docNum);
										if (docNumberWhBw != null) {
											docNumberService.updateBw(docNumberWhBw);
										}else{
											docNumberService.addBw(docNumberWhBw);
										}
									}
								}
							}
							//-----------------------------文号入库---------------end------------------
							
							value += ftmc.getColumnname()+":"+columnValue+";";
							String tagNameBak = "'"+(getRequest().getParameter(ftmc.getFormtagname())==null?"":getRequest().getParameter(ftmc.getFormtagname()))+"',";
							//类型是radio、checkbox时--undefined
							if("'undefined'".equals(tagNameBak)){
								tagNameBak = "";
							}
							if("FORMID".equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+formId+"',";
							}
							if("INSTANCEID".equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+instanceId+"',";
							}
							if("WORKFLOWID".equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+workFlowId+"',";
							}
							if("PROCESSID".equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+processId+"',";
							}
							if("WJBT".equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+zwName+"',";
							}
							if(CommonUtil.stringNotNULL(titleColName) && titleColName.equals(ftmc.getColumnname().toUpperCase())){
								tagNameBak = "'"+zwName+"',";
							}

							if(wfFieldInfoList != null && wfFieldInfoList.size() !=0 && !("").equals(wfFieldInfoList)){
								for (WfFieldInfo wfFieldInfo : wfFieldInfoList) {
									//日期型
									if((wfFieldInfo.getVc_fieldname().toLowerCase()).equals(ftmc.getColumnname().toLowerCase()) && Integer.parseInt(wfFieldInfo.getI_fieldtype()) == 1){
										tagNameBak = "to_date('"+tagNameBak.substring(1,tagNameBak.length()-2)+"','yyyy-MM-dd'),"; //列所对应的的值  
									}
								}
							}
							tagName.append(tagNameBak); //列所对应的的值  
							tagNameForm.append(ftmc.getFormtagname()+","); 
						}
					}
				} 
				for (WfFieldInfo wfColumn : wfFieldInfoList) {
					//基础字段(换表单如果有新的表加入，需加上基础字段)  
					if(wfColumn.getI_tableid()==null || ("").equals(wfColumn.getI_tableid())){
						if(columnName.toLowerCase().indexOf(wfColumn.getVc_fieldname().toLowerCase())<0){
							columnName += wfColumn.getVc_fieldname().toUpperCase()+",";
							//入库为新的formId
							if(("formid").equals(wfColumn.getVc_fieldname().toLowerCase())){
								tagName.append("'"+formId+"',");
							}else{
								tagName.append("'"+getRequest().getParameter(wfColumn.getVc_fieldname())+"',");
							}
						}
					}
				}
				if(todo!=0){
					if(todo==1){
						//更新--先删除再重新插库
						zwkjFormService.deleteForm(tableName,formId,instanceId);
						zwkjFormService.saveForm(tabName, columnName.substring(0,columnName.length()-1),tagName.substring(0,tagName.length()-1));
					}
				}
				if(!("").equals(searchValue.toString())){
					value = searchValue.toString();
				}
				getRequest().setAttribute("tagNameForm", tagNameForm.substring(0, tagNameForm.length()-1));  
			}
			//生成新的html并转成pdf
			String pdfPath = "";
				//页面回值--保存
			try {
				pdfPath = getHtmlToNewOfPath(formId, value, instanceId, false);
				return pdfPath;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	
	/**
	 * 得到html转成新的html，并转成pdf
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public String getHtmlToNewOfPath(String oldformId,String formValue,String instanceId,boolean isCyWF) throws Exception{
		//查找html路径
		ZwkjForm form = zwkjFormService.getOneFormById(oldformId);
		getRequest().setAttribute("form", form);
		String allPath = "";
		String newHtmlPath = "" ; 
		if (form != null && !stringIsNULL(form.getForm_htmlfilename())) {
			allPath = PathUtil.getWebRoot() + "form/html/" + form.getForm_htmlfilename();
			getRequest().setAttribute("filename", form.getForm_htmlfilename());
			// 对象，其日历字段已由当前日期和时间初始化
			Calendar calendar = Calendar.getInstance();
			// 生成一个新的html,用于存放值和生成pdf
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_HTML_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			newHtmlPath =  pdfRoot +dstPath +String.valueOf(calendar.getTimeInMillis()) + ".html";

		}
		//从html流中获取所有的标签数据
		List<FormTagMapColumn> mapList = new ArrayList<FormTagMapColumn>();
		String htmlString = readHTML(allPath);// 源数据
		List<TagBean> tags = getTagFromHTMLString(htmlString);// 返回页面taglist
		if (tags!=null) {
			for (int i = 0; i < tags.size(); i++) {
				FormTagMapColumn m = new FormTagMapColumn();
				m.setFormtagname(tags.get(i).getTagName());
				m.setFormtagtype(tags.get(i).getTagType());
				m.setSelectDic(tags.get(i).getSelect_dic());
				m.setListId(tags.get(i).getListId());
				m.setColumnCname(tags.get(i).getCommentDes());
				mapList.add(m);
			}
		}
		//查询已有表单标签和字段对应关系数据,修改页面默认选中
		List<FormTagMapColumn> mapedList=zwkjFormService.getFormTagMapColumnByFormId(oldformId);
		//对应页面标签和已有对应关系之间的差别，(html中增加或删除元素带来的影响)，页面只显示最新页面标签元素
		if (mapedList!=null&&mapedList.size()>0) {
			for (int i = 0; i < mapList.size(); i++) {
				for (int j = 0; j < mapedList.size(); j++) {
					if (mapedList.get(j).getFormtagname().equals(mapList.get(i).getFormtagname())) {
						mapList.set(i, mapedList.get(j));
						break;
					}
				}
			}
		}
		try {
			// 读取模板文件
			FileInputStream fileinputstream = new FileInputStream(allPath);
			// 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
			int length = fileinputstream.available();
			byte bytes[] = new byte[length];
			fileinputstream.read(bytes);
			fileinputstream.close();
			// 通过使用平台的默认字符集解码指定的 byte 数组，构造一个新的 String, 然后利用字符串的replaceAll()方法进行指定字符的替换,此处除了这种方法之外，应该还可以使用表达式语言${}的方法来进行。
			String start_en = SystemParamConfigUtil.getParamValueByParam("start_en");
//			String templateContent = new String(bytes,"UTF-8");
			String templateContent = "";
			if("0".equals(start_en)){
				templateContent = new String(bytes,"UTF-8");
				htmlString = new String(bytes,"UTF-8");
			}
			if("1".equals(start_en)){
				templateContent = new String(bytes,"GB2312");
				htmlString = new String(bytes,"GB2312");
			}
			templateContent =templateContent.replace("<html>", "<html><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />");
			//------------------------------进行内容替换-------start----------------------------------------
			UrlCatcher u = new UrlCatcher();
			// 获取所有input类型标签
			String reg_input = "<INPUT[^<]*>";
			String[] inputs = u.getStringByRegEx(htmlString, reg_input, true);
			// 获取所有select类型标签
			String reg_select = "<SELECT[^<]*>";
//			String reg_select = "<SELECT.*?</select>";
			String[] selects = u.getStringByRegEx(htmlString, reg_select, true);
			// 获取所有textarea类型标签
			String reg_textarea = "<TEXTAREA[^<]*</TEXTAREA>";
			String[] textareas = u.getStringByRegEx(htmlString, reg_textarea, true);
			// 获取正文及附件类型的标签
			String reg_spanAtt = "<SPAN[^<]*</SPAN>";
			String[] spanAtts = u.getStringByRegEx(htmlString, reg_spanAtt, true);
			// 把值填入html里
			// 把值填入html里
			String[] vals = formValue.split(";");
			String name = "" ;
			String value = "";
			int i = 0;//跳出循环标志位
			for (String val : vals) {
				if(val.split(":").length > 1){
					name = val.split(":")[0];
					value = val.split(":")[1];	
				}else{
					name = val.split(":")[0];
					value = "";
				}
				if(!("").equals(value) && value != null){
					for (FormTagMapColumn ftm : mapList) {
						//取name相同的标签，填入值
						if((ftm.getFormtagname().toUpperCase()).equals(name.toUpperCase())){
							if(("text").equals(ftm.getFormtagtype())){
								if (inputs != null && inputs.length > 0) {
									for (int j = 0;j < inputs.length; j++) {
										//利用正则表达式获取表单元素名称
										String reg_name = " name=\"" + name.toLowerCase()+"\"";
										if(inputs[j].indexOf(reg_name) > 0){
											templateContent = templateContent.replace(inputs[j],"<span name='"+name.toLowerCase()+"'>"+value+"</span>");
											i++;
											break;
										}
									}
									if (i > 0) {
										break;
									}
								}
							}else if(("checkbox").equals(ftm.getFormtagtype())){
								if (inputs != null && inputs.length > 0) {
									String[] values = value.split("\\^");
									for (String valstr : values) {
										//利用正则表达式获取表单元素名称
										String reg_name = " value=\""+valstr+"\"";
										for (int j = 0;j < inputs.length; j++) {
											if(inputs[j].indexOf(reg_name) > 0){
												templateContent = templateContent.replace(inputs[j],"<input type=\"checkbox\" name='"+name.toLowerCase()+"' checked=\"checked\" value=\""+valstr+"\"/>");
												i++;
												break;
											}
										}
									}
									if (i > 0) {
										break;
									}
								}
							}else if(("radio").equals(ftm.getFormtagtype())){
								if (inputs != null && inputs.length > 0) {
									for (int j = 0;j < inputs.length; j++) {
										//利用正则表达式获取表单元素名称
										String reg_name = " value=\""+value+"\"";
										if(inputs[j].indexOf(reg_name) > 0){
											templateContent = templateContent.replace(inputs[j],"<input type=\"radio\" checked=\"checked\" name='"+name.toLowerCase()+"' value=\""+value+"\"/>");
											i++;
											break;
										}
									}
									if (i > 0) {
										break;
									}
								}
							}else if(("select").equals(ftm.getFormtagtype())){
								if (selects != null && selects.length > 0) {
									for (int j = 0;j < selects.length; j++) {
										//利用正则表达式获取表单元素名称
										String reg_name = " name=\"" + name.toLowerCase()+"\"";
										if(selects[j].indexOf(reg_name) > 0){
											//需要html中的标签里有zname属性否则下面得到的content将不对
//											String cname = ftm.getColumnCname();
											String cname = ftm.getSelectDic();//----城管局修改
											//查询字典表，得到下拉框的内容
											String content = selectDicValues(oldformId, value, cname);
											String contentValue = value;
											if(!("").equals(content)){
												contentValue = content;
											}
//											templateContent = templateContent.replace(selects[j],"<select name='"+name+"'><option selected='selected'>"+contentValue+"</option>");
											templateContent = templateContent.replace(selects[j],"<span>"+contentValue+"</span><select name='"+name.toLowerCase()+"' style=\"display:none\"><option selected='selected'>"+contentValue+"</option>");
											i++;
											break;
										}
									}
									if (i > 0) {
										break;
									}
								}
							}else if(("textarea").equals(ftm.getFormtagtype())){
								if (textareas != null && textareas.length > 0) {
									for (int j = 0;j < textareas.length; j++) {
										//利用正则表达式获取表单元素名称
										String reg_name = " name=\"" + name.toLowerCase()+"\"";
										if(textareas[j].indexOf(reg_name) > 0){
											templateContent = templateContent.replace(textareas[j],"<textare name='"+name.toLowerCase()+"'>"+value+"</textarea>");
											i++;
											break;
										}
									}
								}
								if (i > 0) {
									break;
								}
							}
						}
					}
				}
			}
			for (FormTagMapColumn ftm : mapList) {
				if(("attachment").equals(ftm.getFormtagtype())){
					if (spanAtts != null && spanAtts.length > 0) {
						for (int j = 0;j < spanAtts.length; j++) {
							//利用正则表达式获取表单元素名称
							String reg_name = " id=\"" + ftm.getFormtagname().toLowerCase()+"show\"";
							//查找对应的附件
							List<SendAttachments> attZwList = attachmentService.findAllSendAtts(instanceId+"att"+ftm.getFormtagname().toLowerCase(),null);
							List<SendAttachments> attFjList = attachmentService.findAllSendAtts(instanceId+ftm.getFormtagname().toLowerCase(),null);
							List<SendAttachments> attList = new ArrayList<SendAttachments>();
							if(attZwList.size()!=0 && attZwList != null){
								attList = attZwList;
							}else{
								attList = attFjList;
							}
							//获取附件的名字
							String attNames = "";
							for (SendAttachments sa : attList) {
								attNames += sa.getFilename()+",";
							}
							if(attNames!=null && !("").equals(attNames)){
								attNames = attNames.substring(0,attNames.length()-1);
							}
							if(spanAtts[j].indexOf(reg_name) > 0){
								templateContent = templateContent.replace(spanAtts[j],"<span id='"+ftm.getFormtagname().toLowerCase()+"'>"+attNames+"</span>");
								break;
							}
						}
					}
				}
			}
			//----------对剩下的input框置成空,只显示空白----------
			String[] inputExists = u.getStringByRegEx(templateContent, reg_input, true);
			if (inputExists != null && inputExists.length > 0) {
				for (int k = 0;k < inputExists.length; k++) {
					if(inputExists[k].indexOf("checkbox") < 0 && inputExists[k].indexOf("radio") < 0 && inputExists[k].indexOf("hidden") < 0){
						templateContent = templateContent.replace(inputExists[k],"<span>&nbsp;</span>");
					}
				}
			}
			//----------对剩下的textarea框置成空,只显示空白----------
			String[] textareaExists = u.getStringByRegEx(htmlString, reg_textarea, true);
			if (textareaExists != null && textareaExists.length > 0) {
				for (int kk = 0;kk < textareaExists.length; kk++) {
					if(textareaExists[kk].indexOf("id") > 0 ){
						templateContent = templateContent.replace(textareaExists[kk],"<span>&nbsp;</span>");
					}
				}
			}
			// 添加传阅名单
			
			//----------------------------------进行内容替换--------end----------------------------------------
			
			// 使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
//			byte tag_bytes[] = templateContent.getBytes();
			byte[] tag_bytes = null;
			
			if("0".equals(start_en)){
				tag_bytes = templateContent.getBytes();
			}
			if("1".equals(start_en)){
				tag_bytes = templateContent.getBytes("utf-8");
			}
			
//			System.out.println(tag_bytes);
//			byte tag_bytes[] = templateContent.getBytes("utf-8");
			FileOutputStream fileoutputstream = new FileOutputStream(newHtmlPath);// 建立文件输出流
			OutputStreamWriter osw = new OutputStreamWriter(fileoutputstream, "UTF-8");
			osw.write(templateContent);
			osw.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//pdf地址--得到的地址会在前面加个"/",不懂,有待检查
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String pdfFormPath = pdfRoot+dstPath+newHtmlPath.substring(newHtmlPath.lastIndexOf("/"), newHtmlPath.length() - 4)+".pdf";
		//html转成pdf
		HtmlToPdf htp = new HtmlToPdf();
		htp.htmlToPdf(newHtmlPath,pdfFormPath);
		//获取正文附件地址 doc,ceb
		String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
		String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");//正文附件的后缀
		List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId+attSuffixName,null);
		//合并后的pdf地址
		String saveMergePath = pdfFormPath;
		ToPdfUtil pdfUtil = new ToPdfUtil();
		if(sattList.size()!= 0 && !("").equals(sattList)){
			String wordPath = "";
			String cebPath = "";
			for (SendAttachments sat : sattList) {
				//正文中存在同名ceb则不合入doc
				boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
				
				if(("doc").equals(sat.getFiletype())){
					wordPath  = docToPdf(sat.getLocalation())+",";
				}else if(("ceb").equals(sat.getFiletype())&&isSatt_ceb){
					CebToPdf cp = new CebToPdf();
					if(new File(filePathOfSys + sat.getLocalation()).exists()){
						cp.cebToPdf(filePathOfSys + sat.getLocalation());
						cebPath += filePathOfSys+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
					}else{
						cp.cebToPdf(pdfRoot+ sat.getLocalation());
						cebPath += pdfRoot+sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
					}
				}
			}
			if(wordPath != null && !("").equals(wordPath)){
				wordPath = wordPath.substring(0,wordPath.length()-1);
			}
			if(cebPath != null && !("").equals(cebPath)){
				cebPath = cebPath.substring(0,cebPath.length()-1);
			}
			//合并正文附件的pdf和表单的pdf
			MergePdf mp = new MergePdf();
			String[] files = {pdfFormPath, wordPath, cebPath};
			saveMergePath = pdfFormPath.substring(0,pdfFormPath.length()-4)+"merge" + ".pdf";
			mp.mergePdfFiles(files, saveMergePath);
		}
		return saveMergePath;
	}
	
	public List<TagBean> getTagFromHTMLString(String htmlString){
		List<TagBean> tags=new ArrayList<TagBean>();
		
		UrlCatcher u=new UrlCatcher();
		String reg_input="<INPUT[^<]*>";//获取所有input类型标签
		String[] inputs=u.getStringByRegEx(htmlString, reg_input, true);
		
		String reg_select="<SELECT[^<]*>";//获取所有select类型标签
		String[] selects=u.getStringByRegEx(htmlString, reg_select, true);
		
		String reg_textarea="<TEXTAREA[^<]*</TEXTAREA>";//获取所有textarea类型标签
		String[] textareas=u.getStringByRegEx(htmlString, reg_textarea, true);
		
		
		//新增意见标签抓取
		//<trueway:comment typeinAble="true" deleteAbled="true" id="${instanceId}csyj" instanceId="${instanceId}" currentStepId="${instanceId}"/>
		String reg_comment="<trueway:comment[^<]*>";//获取所有comment类型标签
		String[] comments=u.getStringByRegEx(htmlString, reg_comment, true);
		
		//附件标签抓取
		//<trueway:att onlineEditAble="true" id="${instanceId}att" docguid="${instanceId}" showId="attshow" ismain="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
		String reg_att="<trueway:att[^<]*>";//获取所有att类型标签
		String[] atts=u.getStringByRegEx(htmlString, reg_att, true);
		
		
		//文号标签抓取
		//<trueway:dn tagId="dn_tagid_zhu" defineId="${workFlowId}" webId="${webId}" showId="wenhaos" value="wenhaos"/>
		String reg_dn="<trueway:dn[^<]*>";//获取所有dn类型标签
		String[] dns=u.getStringByRegEx(htmlString, reg_dn, true);
		
		/*
		String reg_zd=">[^<]+</a>";
		String zd=uc.getStringByRegEx(datatds[5], reg_zd, false)[0];
		zd=zd.substring(1,zd.length()-4);
		*/
		if (inputs!=null&&inputs.length>0) {
			for (int i = 0; i < inputs.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(inputs[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				//利用正则表达式获取表单元素类型
				String reg_type=" type=['\"]?[^'\"]+['\"]?";
				String[] types=u.getStringByRegEx(inputs[i], reg_type, true);
				if (types!=null&&types.length>0) {
					tagType=types[0];
					tagType=tagType.trim();
					tagType=tagType.replaceAll("'", "");
					tagType=tagType.replaceAll("\"", "");
					tagType=tagType.substring(5,tagType.length());
				}
				
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(inputs[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
//				if (tagType==null) {
//					reg_type=" type=['\"]?hidden['\"]?";
//					types=u.getStringByRegEx(inputs[i], reg_type, true);
//					if (types!=null&&types.length>0) {
//						tagType=types[0];
//						tagType=tagType.trim();
//						tagType=tagType.replaceAll("'", "");
//						tagType=tagType.replaceAll("\"", "");
//						tagType=tagType.substring(5,tagType.length());
//					}
//				}
				//如果匹配到元素名称，但没有匹配到元素类型，则认为元素类型为text文本框
				if (tagName!=null&&tagType==null) {
					tagType="text";
				}
				String list=null;
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					
					//此处过滤radio、checkbox，同组radio或checkbox采用一条记录
					boolean isin=false;
					for (int j = 0; j < tags.size(); j++) {
						TagBean bean=tags.get(j);
						if (bean.getTagName().equals(tagName)&&bean.getTagType().equals(tagType)) {
							isin=true;break;
						}
					}
					if (!isin) {
						tags.add(tag);
					}
					
				}
			}
		}
		
		if (selects!=null&&selects.length>0) {
			for (int i = 0; i < selects.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(selects[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				tagType="select";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(selects[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				String reg_dic=" dic=['\"]?[^'\"]+['\"]?";
				String[] dics=u.getStringByRegEx(selects[i], reg_dic, true);
				if (dics!=null&&dics.length>0) {
					select_dic=dics[0];
					select_dic=select_dic.trim();
					select_dic=select_dic.replaceAll("'", "");
					select_dic=select_dic.replaceAll("\"", "");
					select_dic=select_dic.trim();
					select_dic=select_dic.substring(4,select_dic.length());
				}
				
				//匹配列表字段属性
				String reg_list=" list=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789]+['\"]?";
				String[] lists=u.getStringByRegEx(selects[i], reg_list, true);
				String list=null;
				if (lists!=null&&lists.length>0) {
					list=lists[0];
					list=list.trim();
					list=list.replaceAll("'", "");
					list=list.replaceAll("\"", "");
					list=list.substring(5,list.length());
				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		if (textareas!=null&&textareas.length>0) {
			for (int i = 0; i < textareas.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(textareas[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				tagType="textarea";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(textareas[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				//匹配列表字段属性
				String list=null;
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		//新增意见标签抓取
		if (comments!=null&&comments.length>0) {
			for (int i = 0; i < comments.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:comment typeinAble="true" deleteAbled="true" id="${instanceId}csyj" instanceId="${instanceId}" currentStepId="${instanceId}"/>
				String reg_name=" id=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(comments[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					//tagName=tagName.substring(16,tagName.length());
					if (tagName.indexOf("}")!=-1) {
						tagName=tagName.split("}")[1];
					}
				}
				tagType="comment";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(comments[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				//意见标签描述
//				String cname="";
//				String commentTagDes="";
//				String reg_cname=" commentDes=['\"]{1}[^'\"]+['\"]{1}";
//				String[] cnames=u.getStringByRegEx(comments[i], reg_cname, true);
//				if (cnames!=null&&cnames.length>0) {
//					cname=cnames[0];
//					cname=cname.trim();commentTagDes=cname;
//					cname=cname.replaceAll("'", "");
//					cname=cname.replaceAll("\"", "");
//					cname=cname.substring(11,cname.length());
//				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//意见标签描述
					tag.setCommentTagDes(tagZnameStr);
					tags.add(tag);
				}
			}
		}
		
		//附件标签抓取
		if (atts!=null&&atts.length>0) {
			for (int i = 0; i < atts.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:att onlineEditAble="true" id="${instanceId}att" docguid="${instanceId}" showId="attshow" ismain="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
				String reg_name=" id=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(atts[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					//tagName=tagName.substring(16,tagName.length());
					if (tagName.indexOf("}")!=-1) {
						tagName=tagName.split("}")[1];
					}
				}
				tagType="attachment";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(atts[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		//文号标签抓取
		if (dns!=null&&dns.length>0) {
			for (int i = 0; i < dns.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:dn tagId="dn_tagid_zhu" defineId="${workFlowId}" webId="${webId}" showId="wenhaos" value="wenhaos"/>
				String reg_name=" tagId=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(dns[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(6,tagName.length());
//					if (tagName.indexOf("}")!=-1) {
//						tagName=tagName.split("}")[1];
//					}
				}
				tagType="wh";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(dns[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		return tags;
	}
	
	public String selectDicValues(String formid,String value,String cname){
		//下拉框内被选中的内容
		String content = "";
		if (CommonUtil.stringNotNULL(formid)) {
			//查询下拉框对应字典表信息并转换为json字符串用于页面动态赋值
			List<FormTagMapColumn> mapedList=zwkjFormService.getFormTagMapColumnByFormId(formid);
			List<WfDictionary> dicList = new ArrayList<WfDictionary>();
			if (mapedList!=null) {
				for (int i = 0; i < mapedList.size(); i++) {
					FormTagMapColumn m = mapedList.get(i);
					if (CommonUtil.stringNotNULL(m.getSelectDic())) {
						dicList.add(dictionaryService.getDictionaryByName(m.getSelectDic()).get(0));
					}
				}
			}
			if(dicList != null && dicList.size()>0 ){
				for (WfDictionary wfdc : dicList) {
					String[] values = wfdc.getVc_value().split(",");
					for (int i = 0; i < values.length; i++) {
						if(value.equals(values[i]) && cname.equals(wfdc.getVc_name())){
							content = wfdc.getVc_key().split(",")[i];
							break;
						}
					}
				}
			}
		}
		return content;
	}
	
	public String readHTML(String path){
		StringBuffer htmlString=new StringBuffer();//返回页面流格式字符串
		//读取文件，用于展示
		File file=new File(path);
		BufferedReader reader=null;
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);  
				InputStreamReader in = new InputStreamReader(fin,"UTF-8");  
				reader = new BufferedReader(in);
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					htmlString.append(tempString+"\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (reader!=null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return htmlString.toString();
	}
	
	/**
	 * word转pdf
	 * @param filePath
	 * @param filename
	 * @throws IOException 
	 */
	public String  docToPdf(String filePath) throws IOException{
		//原附件的ID
//		String fileId = getRequest().getParameter("fileId");
		//是否是正文附件
//		String isManAttStr=getRequest().getParameter("isManAtt");
		//文件路径
		String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String path = filePathOfSys + filePath;
		if(!new File(path).exists()){
			path = pdfRoot + filePath;
		}
		//文件路径及文件名，除去后缀
		String fileAndPath = path.substring(0,path.length()-4);
		
		WordToPdfOfPrinter d2p = new WordToPdfOfPrinter();
		
		try {
			d2p.docToPDF(fileAndPath+".doc", fileAndPath+".ps", fileAndPath+".pdf");
			boolean success = (new File(fileAndPath+".ps")).delete();
			boolean success2 = (new File(fileAndPath+".log")).delete();
			
			if(success && success2){
				System.out.println("删除打印机文件成功");
			}
//			uploadPDF(fileId,isManAttStr);
//			getResponse().getWriter().print("success");
		} catch (Exception e) {
			e.printStackTrace();
//			getResponse().getWriter().print("fail");
		}
		return fileAndPath+".pdf";
		//若报错com.jacob.com.ComFailException: VariantChangeType failed
		//那么在C:\Windows\System32\config\systemprofile下创建文件夹Desktop
	}
	
	public boolean stringIsNULL(String s){
		if (s==null||s.equals("")) {
			return true;
		}
		return false;
	}
	
	private void excuteProcedure(String oldformId,int todo,String instanceId,String formId,String nodeid){
		if (CommonUtil.stringNotNULL(nodeid)) {
			WfNode node=workflowBasicFlowService.getWfNode(nodeid);
			if (node!=null) {
				String procedureName=node.getWfn_procedure_name();
				if (CommonUtil.stringNotNULL(procedureName)) {
					//输入、输出参数   目前只支持VARCHAR、INTEGER、DATE三种
					//输入、输出参数类型   仅有in、out两种
//					Object[][] obj={
//							{"in",	"VARCHAR","wh1234"},//1输入参数  2输入参数类型  3输入参数值
//							{"in",	"VARCHAR","id1234"},
//							{"in",	"DATE",Timestamp.valueOf("2013-01-01 15:25:33")},
//							{"out",	"INTEGER","oldCount"},//1输出参数 2输出参数类型  3输出值map的key
//							{"out",	"INTEGER","newCount"},
//							{"out",	"DATE","cDate"}
//					};
					//目前固定两个输入参数
//					Object[][] obj={
//							{"in",	"VARCHAR",UuidGenerator.generate36UUID()},//1输入参数  2输入参数类型  3输入参数值
//							{"in",	"VARCHAR",instanceId},//1输入参数  2输入参数类型  3输入参数值
//							{"in",	"VARCHAR",formId},
//							{"in",	"VARCHAR",nodeid}
//					};
//					Map<String, Object> map=zwkjFormService.excuteProcedure(obj, procedureName);
					
					
					Procedure param=new Procedure();
					param.setPname(procedureName);
					List<Procedure> pList=zwkjFormService.getProcedureByParam(param);
					String workflowid=getSession().getAttribute(MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
					if (pList!=null&&pList.size()>0) {
						Procedure p=pList.get(0);
						String[] paramname=p.getParamname()==null?null:p.getParamname().split(",");
						String[] paramtype=p.getParamtype()==null?null:p.getParamtype().split(",");
						String[] inoutparam=p.getInouttype()==null?null:p.getInouttype().split(",");
						Object[][] obj=null;
						if (paramname!=null) {
							obj=new Object[paramname.length][3];
							for (int i = 0; i < paramname.length; i++) {
								if (!paramname[i].equals("")) {
									Object[] o=new Object[3];
									o[0]=inoutparam[i];
									o[1]=paramtype[i];
									if (paramname[i].equals("uuid")) {
										o[2]=UuidGenerator.generate36UUID();
									}else if (paramname[i].equals("workflow_id")) {
										o[2]=workflowid;
									}else if (paramname[i].equals("workflow_instance_id")) {
										o[2]=instanceId;
									}else if (paramname[i].equals("form_id")) {
										o[2]=formId;
									}else if (paramname[i].equals("node_id")) {
										o[2]=nodeid;
									}
									obj[i]=o;
								}
							}
						}
						try {
							Map<String, Object> map=zwkjFormService.excuteProcedure(obj, procedureName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
}
