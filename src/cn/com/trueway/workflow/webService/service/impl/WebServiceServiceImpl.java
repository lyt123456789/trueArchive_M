package cn.com.trueway.workflow.webService.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asprise.util.tiff.i;
import com.sun.jmx.snmp.Timestamp;

import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.service.FieldMatchingService;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.action.TableInfoAction;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.GetProcess;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.webService.dao.WebServiceDao;
import cn.com.trueway.workflow.webService.pojo.WebServiceInfo;
import cn.com.trueway.workflow.webService.util.FileUtil;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.util.GenePdfUtil;
import cn.com.trueway.workflow.set.util.ToPdfUtil;
import cn.com.trueway.workflow.webService.service.WebServiceService;

public class WebServiceServiceImpl implements WebServiceService{
	
	private WebServiceDao webServiceDao;
	
	private TableInfoDao tableInfoDao;
	
	private ItemDao itemDao;
	
	private AttachmentDao attachmentDao;
	
	private WorkflowBasicFlowDao workflowBasicFlowDao;
	
	private FieldMatchingService fieldMatchingService;
	
	private ZwkjFormDao zwkjFormDao;
	
	private EmployeeDao employeeDao;
	
	
	/**
	 * 获取事项列表
	 */
	public String getItemList(String dataJson){
		//解析json数据
		JSONObject infoJson = JSONObject.fromObject(dataJson);
		JSONObject result = new JSONObject();//返回结果
		if(infoJson != null){
			String userId = (String)infoJson.get("userId");				//用户id
			if(CommonUtil.stringNotNULL(userId)){
				Employee employee = employeeDao.queryEmployeeById(userId);
				if(null != employee){
					String isAdmin = (String)infoJson.get("isAdmin");			//是否管理员
					String pageSize= (String)infoJson.get("pageSize");			//每页条数
					String pageIndex= (String)infoJson.get("pageIndex");		//页码
					String itemName = (String)infoJson.get("itemName");			//事项名称
					itemName = CommonUtil.stringNotNULL(itemName) ? itemName.replaceAll("'", "\\'\\'") : "";
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("isAdmin", isAdmin);
					map.put("userId", userId);
					map.put("pageSize", pageSize);
					map.put("pageIndex", pageIndex);
					map.put("itemName", itemName);
					
					int count = webServiceDao.getItemListCount(map);
					List<Object[]> itemList = webServiceDao.getItemList(map);//获取事项列表
					List<WfItem> newItemList = new ArrayList<WfItem>();
					if(itemList != null && itemList.size() > 0){
						for(int i = 0; i < itemList.size(); i++){
							Object[] itemObj = itemList.get(i);
							WfItem item = new WfItem();
							item.setId(itemObj[0] == null ? "" : itemObj[0].toString());
							item.setVc_sxmc(itemObj[1] == null ? "" : itemObj[1].toString());
							item.setVc_ssbm(itemObj[2] == null ? "" : itemObj[2].toString());
							item.setVc_wcsx(itemObj[3] == null ? "" : itemObj[3].toString());
							item.setVc_sxlx(itemObj[4] == null ? "" : itemObj[4].toString());
							item.setB_yj(itemObj[5] == null ? "" : itemObj[5].toString());
							item.setLcid(itemObj[6] == null ? "" : itemObj[6].toString());
						/*	String dateStr = itemObj[7] == null ? "" : itemObj[7].toString();
							if(CommonUtil.stringNotNULL(dateStr)){
								item.setC_createdate(java.sql.Timestamp.valueOf(dateStr));
							}*/
							item.setVc_ssbmid(itemObj[8] == null ? "" : itemObj[8].toString());
							item.setVc_xxlx(itemObj[9] == null ? "" : itemObj[9].toString());
							item.setIndex(itemObj[10] == null ? 0 : Integer.valueOf(itemObj[10].toString()));
							newItemList.add(item);
						}
					}
					JSONArray itemArray = JSONArray.fromObject(newItemList);//事项列表转化JSon对象
					result.put("count", count);
					result.put("itemList", itemArray);
					result.put("message", "事项列表获取成功！");
				}else{
					result.put("message", "未找到此用户！");
				}
			}else{
				result.put("message", "用户不能为空！");
			}
		}
		return result.toString();
	}
	
	/**
	 * 根据外网提供的事项ID获取该事项需要同步的信息属性列表
	 */
	@Override
	public String getSyncInfoListByItemId(String dataJson){
		//解析json数据
		JSONObject infoJson = JSONObject.fromObject(dataJson);
		JSONObject result = new JSONObject();//返回结果
		if(infoJson != null){
			String itemId = infoJson.getString("itemId");
			String pageSize= (String)infoJson.get("pageSize");			//每页条数
			String pageIndex= (String)infoJson.get("pageIndex");		//页码
			Map<String, String> map = new HashMap<String, String>();
			map.put("itemId", itemId);
			map.put("pageSize", pageSize);
			map.put("pageIndex", pageIndex);
			
			int count = webServiceDao.getSyncInfoCountByItemId(map);
			List<Object[]> syncInfoList = webServiceDao.getSyncInfoListByItemId(map);//指定事项同步信息列表
			List<WfFieldInfo> fieldInfoList = new ArrayList<WfFieldInfo>();
			if(syncInfoList != null && syncInfoList.size() > 0){
				for(int i = 0; i < syncInfoList.size(); i++){
					Object[] syncInfoObj = syncInfoList.get(i);
					WfFieldInfo fieldInfo = new WfFieldInfo();
					fieldInfo.setId(syncInfoObj[0] == null ? "" : syncInfoObj[0].toString());
					fieldInfo.setVc_name(syncInfoObj[1] == null ? "" : syncInfoObj[1].toString());
					fieldInfo.setVc_fieldname(syncInfoObj[2] == null ? "" : syncInfoObj[2].toString());
					fieldInfo.setI_type(syncInfoObj[3] == null ? "" : syncInfoObj[3].toString());
					fieldInfo.setI_tableid(syncInfoObj[4] == null ? "" : syncInfoObj[4].toString());
					fieldInfo.setI_fieldtype(syncInfoObj[5] == null ? "" : syncInfoObj[5].toString());
					fieldInfo.setB_value(syncInfoObj[6] == null ? "" : syncInfoObj[6].toString());
					fieldInfo.setVc_comment(syncInfoObj[7] == null ? "" : syncInfoObj[7].toString());
					fieldInfo.setVc_value(syncInfoObj[8] == null ? "" : syncInfoObj[8].toString());
					fieldInfo.setI_length(syncInfoObj[9] == null ? "" : syncInfoObj[9].toString());
					fieldInfo.setI_precision(syncInfoObj[10] == null ? "" : syncInfoObj[10].toString());
					fieldInfo.setVc_ftable(syncInfoObj[11] == null ? "" : syncInfoObj[11].toString());
					fieldInfo.setVc_ffield(syncInfoObj[12] == null ? "" : syncInfoObj[12].toString());
					fieldInfo.setI_orderid(syncInfoObj[13] == null ? 0 : Integer.valueOf(syncInfoObj[13].toString()));
					fieldInfoList.add(fieldInfo);
				}
			}
			JSONArray syncInfoArray = JSONArray.fromObject(fieldInfoList);
			
			result.put("count", count);
			result.put("itemList", syncInfoArray);
			result.put("message", "事项同步信息列表获取成功！");
		}
		return result.toString();
	}
	
	
	@Override
	public String syncInfo(String dataJson) {
		//解析json数据
		JSONObject infoJson = JSONObject.fromObject(dataJson);
		WebServiceInfo webServiceInfo = new WebServiceInfo();
		webServiceInfo.setXml(dataJson);
		webServiceInfo.setRec_time(new Date());
		webServiceInfo.setStatus("0");
		JSONObject result = new JSONObject();
		if(infoJson!=null){
			//1.插入接收信息数据
			String userId = (String)infoJson.get("userId");			//用户id
			String userName = (String)infoJson.get("userName");		//用户名称
			String itemId = (String)infoJson.get("itemId");			//事项id
			String title = (String)infoJson.get("bt");			//标题
			webServiceInfo.setBt(title);
			
			Employee employee = employeeDao.queryEmployeeById(userId);
			if(employee==null){
				result.put("success", false);
				result.put("message", "未找到对应的人员信息");
				webServiceDao.saveWebServiceInfo(webServiceInfo);
				return result.toString();
			}
		
			WfItem wfItem = itemDao.getItemById(itemId);
			if(wfItem==null){
				result.put("success", true);
				result.put("message", "未找到对应的待办事项");
				webServiceDao.saveWebServiceInfo(webServiceInfo);
				return result.toString();
			}
			
			webServiceInfo.setStatus("1");
			webServiceDao.saveWebServiceInfo(webServiceInfo);

			String instanceId = UuidGenerator.generate36UUID();
			String workFlowId = wfItem.getLcid();
			WfNode wfNode = workflowBasicFlowDao.findFirstNodeId(workFlowId);

			//2.下载保存附件
			Object fileObj = infoJson.get("attexts");		//附件相应的数据
			Object obj = infoJson.get("fields");
			List<SendAttachments> attachmentList = null;
			if(fileObj!=null && !fileObj.equals("")){
				attachmentList = saveAttachment(userId, userName, instanceId, fileObj);
			}
			//3.保存表单
			saveForm(wfItem, instanceId, obj);
			
			String formId = wfNode.getWfn_defaultform();
			
			//4.插入待办数据
			Date nowTime = new Date();
			WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			newProcess.setWfInstanceUid(instanceId);
			newProcess.setNodeUid(wfNode.getWfn_id());
			newProcess.setFromUserId(userId);
			newProcess.setOwner(userId);
			newProcess.setApplyTime(nowTime);
			newProcess.setFinshTime(null);
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setIsEnd(0);
			newProcess.setIsExchanging(0);
			newProcess.setItemId(itemId);
			newProcess.setWfUid(wfItem.getLcid());
			newProcess.setUserUid(userId);
			newProcess.setIsMaster(1);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);
			newProcess.setFormId(formId);
			newProcess.setProcessTitle(title);
			newProcess.setAllInstanceid(instanceId);
			newProcess.setStatus("0");
			newProcess.setIsBack("0");
			newProcess.setFromNodeid("first");
			newProcess.setToNodeid(wfNode.getWfn_id());
			if(employee!=null){
				newProcess.setUserDeptId(employee.getDepartmentGuid());
			}
			//获取
			ZwkjForm zwkjForm = zwkjFormDao.getOneFormById(formId);
			String formPdf = zwkjForm.getForm_pdf();
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			if(attachmentList!=null && attachmentList.size()>0){		//有附件
				List<String> list = null;
				try {
				    list = changeAttachToPdf(attachmentList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//文件合并的过程
				String[] files = null;
				MergePdf mp = new MergePdf();
				if (list!=null && list.size()>0) {
					files = new String[list.size()+1];
					files[0] = PathUtil.getWebRoot()+"form/html/"+formPdf;
					for(int i=0; i<list.size(); i++){
						files[i+1] = list.get(i);
						
					}
					String mergerPdf = UuidGenerator.generate36UUID();
					String saveMergePath = pdfRoot+dstPath+mergerPdf+ "merge" + ".pdf";
					mp.mergePdfFiles(files, saveMergePath);
					String saveTruePath = saveMergePath.substring(0, saveMergePath.length()-4)+".true";
					new PDFToTrue().pdfToTrue(saveMergePath, null, saveTruePath);
					newProcess.setPdfPath(saveTruePath+","+saveTruePath);
				}
			}else{			//吴附件
				String filePath = PathUtil.getWebRoot()+"form/html/"+formPdf;
				File file = new File(filePath);
				String newTurePath = pdfRoot+dstPath+UuidGenerator.generate36UUID()+ "new_info" + ".true";
				File new_file = new File(newTurePath);
				FileUploadUtils.copy(file, new_file);// 完成上传文件，就是将本地文件复制到服务器上
				newProcess.setPdfPath(newTurePath+","+newTurePath);
			}
			tableInfoDao.save(newProcess);
			
			result.put("instanceId", instanceId);
			
			//5,插入dofile数据表中
			String nodeId = wfNode.getWfn_id();		//节点id
			DoFile doFile = new DoFile();
			doFile.setDoFile_title(title);
			doFile.setItemId(itemId);
			doFile.setWorkflowId(workFlowId);
			doFile.setInstanceId(instanceId);
			doFile.setFormId(formId);
			String itemName = tableInfoDao.findItemNameById(itemId);
			doFile.setItemName(itemName);
			doFile.setNodeId(nodeId);
			doFile.setDo_time(new Date());
			tableInfoDao.saveDoFile(doFile);
			
			//插入附件文件;
			SendAttachments attach = null;
			
			for(int i=0; attachmentList!=null && i<attachmentList.size(); i++){
				attach = attachmentList.get(i);
				attachmentDao.addSendAtts(attach);
			}
		}
		result.put("success", true);
		return result.toString();
	}
	
	
	public List<String> changeAttachToPdf(List<SendAttachments> sattList) throws Exception{
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		List<String> attachmentList = new ArrayList<String>();
		ToPdfUtil pdfUtil = new ToPdfUtil();
		String fileTyle = "";
		if (sattList.size() != 0 && !("").equals(sattList)) {
			for (SendAttachments sat : sattList) {
				//正文中存在同名ceb则不合入ceb
				boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
				
				fileTyle = sat.getFiletype();
				if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
					String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, "", sat.getId()); 
					attachmentList.add(path);
				} else if (("ceb").equalsIgnoreCase(sat.getFiletype())&&isSatt_ceb) {
					CebToPdf cp = new CebToPdf();
					cp.cebToPdf(pdfRoot + sat.getLocalation());
					String cebPath = pdfRoot +sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf";
					attachmentList.add(cebPath);
				}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
					String pdfPath = pdfRoot+sat.getLocalation();
					attachmentList.add(pdfPath);
				}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle,"", sat.getId());
					attachmentList.add(path);
				}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || fileTyle.equalsIgnoreCase("jpeg")){
					String imgpath= pdfUtil.imgToPdf(sat.getLocalation(), fileTyle);
					attachmentList.add(imgpath);
				}else if(("true").equalsIgnoreCase(fileTyle)){
					String truePath = pdfRoot+sat.getLocalation();
					attachmentList.add(truePath);
				}
			}
		}
		return attachmentList;
	}
	
	
	/**
	 * 
	 * 描述：保存附件
	 * @param userId
	 * @param userName
	 * @param instanceId
	 * @param fileObj void
	 * 作者:蔡亚军
	 * 创建时间:2015-8-17 上午11:58:56
	 */
	public List<SendAttachments> saveAttachment(String userId, String userName, String instanceId, Object fileObj){
		try{
			JSONArray fileArr = JSONArray.fromObject(fileObj);			//字段集合
			FileUtil fileUtil = new FileUtil();
			List<SendAttachments> attachList = new ArrayList<SendAttachments>();
			SendAttachments attachment = null;
			for(int i=0; i<fileArr.size(); i++){
				JSONObject file_obj = (JSONObject) fileArr.get(i);
				attachment = new SendAttachments();
				String fileName = (String) file_obj.get("name");			//文件名称
				String fileType = (String) file_obj.get("type");			//文件类型
				String url = (String) file_obj.get("url");					//提供下载的url地址
				String location = fileUtil.downloadFromUrl(url, fileName);		//文件下载以后的地址
				if(location!=null && !location.equals("")){
					String fileSize = (String) file_obj.get("fileSize");		//文件大写
					attachment.setDocguid(instanceId+"fj");
					attachment.setFilename(fileName);
					attachment.setFiletime(new Date());
					attachment.setLocalation(location);
					attachment.setFilesize(Long.parseLong(fileSize));
					attachment.setEditer(userId+","+userName);
					attachment.setFiletype(fileType);
					attachment.setTitle(fileName);
					attachment.setFileindex(Long.parseLong("999"));
					attachment.setType("正文材料");
					//attachmentDao.addSendAtts(attachment);
					attachList.add(attachment);
				}
			}
			return attachList;
		}catch (Exception e) {
			System.out.println("下载保存附件失败");
			return null;
		}
		
	}
	
	/**
	 * 
	 * 描述：保存form表单值
	 * @param itemId
	 * @param instanceId
	 * @param obj void
	 * 作者:蔡亚军
	 * 创建时间:2015-8-17 下午12:01:15
	 */
	public void saveForm(WfItem wfItem, String instanceId, Object obj ){
		//查询流程的第一个节点
		String workFlowId = wfItem.getLcid();
		WfNode wfNode = workflowBasicFlowDao.findFirstNodeId(workFlowId);
		String nodeId = wfNode.getWfn_id();		//节点id
		String formId = wfNode.getWfn_defaultform();		//表单id	
		ZwkjForm zwkjForm = zwkjFormDao.getOneFormById(formId);
		String tableName = zwkjForm.getInsert_table();		//插入的数据表
		String formValue = "";
		if(obj!=null && !obj.equals("")){
			JSONArray fieldArr = JSONArray.fromObject(obj);			//字段集合
			Map<String,Object> tagValueList = new HashMap<String,Object>();
			if(fieldArr!=null && fieldArr.size()>0){
				for(int i=0; i<fieldArr.size(); i++){
					JSONObject field_obj = (JSONObject)fieldArr.get(i);
					Iterator it = field_obj.keys(); 
					while(it.hasNext()){
						String key = (String)it.next();
						String value = field_obj.getString(key);
						tagValueList.put(key, value);
						formValue += key+":"+value+";";
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
		//2.数据保存完毕后,最想用的保存
	}

	/**
	 * 获取历程列表
	 */
	@Override
	public String getWfProcessList(String instanceId) {
		List<GetProcess> proList = tableInfoDao.findProcessList(instanceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(GetProcess process: proList){
			Date apply_date = process.getApplyTime();
			Date finish_date = process.getFinshTime();
			Date jssj = process.getJssj();
			if(apply_date!=null){
				process.setStr_applyTime(sdf.format(apply_date));
			}
			if(jssj!=null){
				process.setStr_jssj(sdf.format(jssj));
			}
			if(finish_date!=null){
				process.setStr_finshTime(sdf.format(finish_date));
			}
		}
		JSONArray array = JSONArray.fromObject(proList);
		return array.toString();
	}

	/**
	 * 外网查看办件当前详情，只读
	 */
	@Override
	public String getPdfPathInfo(String instanceId) {
		List<WfProcess> list = tableInfoDao.getProcessList(instanceId);
		JSONObject result = new JSONObject();
		if(list!=null && list.size()>0){
			String downurl = "";
			WfProcess wfp =  list.get(0);
			String path = wfp.getPdfPath();
			if(path!=null && !path.equals("")){
				String[] paths = path.split(",");
				if(paths!=null && paths.length==2){
					String truePath = paths[0];			//最近true表单的文件
					String[] st = new TrueToPdf().trueToPdf(truePath);
					String pdfpath = st[0];					//解析出来的pdf文件
					String processTitle = wfp.getProcessTitle();
					String commentJson = wfp.getCommentJson();	//意见
					GenePdfUtil pdfUtil = GenePdfUtil.getInstance();		
					String newPdfPath = pdfpath.substring(0, pdfpath.length()-5)+"_other.pdf";
					pdfUtil.geneStampPdf(pdfpath, commentJson, newPdfPath, "0");
					String fileDownloadUrl=SystemParamConfigUtil.getParamValueByParam("filedownloadurl");
					downurl = fileDownloadUrl+"?name="+processTitle+".pdf&location="+newPdfPath+"&isabsolute=1";
					result.put("success", true);
					result.put("downurl", downurl);
				}
			}
			if(downurl==null || downurl.equals("")){
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return result.toString();
	}
	
	/**
	 * 外网更新表单内容，加入满意度评判
	 */
	@Override
	public String updateEndWfpInfoList(String dataJson) {
		JSONObject infoJson = JSONObject.fromObject(dataJson);		//转换对应的json格式
		JSONObject result = new JSONObject();
		if(infoJson!=null){
			String instanceId = infoJson.getString("instanceId");		//实例id
			String fieldName = infoJson.getString("fieldName");			//需要更新的字段名
			String fieldValue = infoJson.getString("fieldValue");		//需要更新的字段值
			String userId = infoJson.getString("userId");		
			List<WfProcess> list = tableInfoDao.getProcessList(instanceId);
			WfProcess endWfp = null;			//已经办结的那步步骤信息
			for(WfProcess wfp: list){
				if(wfp.getIsEnd()!=null && wfp.getIsEnd()==1){
					endWfp = wfp;
					break;
				}
			}
			if(endWfp!=null){		//办件已经 结束
				String fromNodeId = endWfp.getFromNodeid(); 
				String workFlowId = endWfp.getWfUid();
				WfNode  node = workflowBasicFlowDao.getWfNode(fromNodeId);
				String nodeId = fromNodeId;
				String formId = node.getWfn_defaultform();				//表单
				String trueJson = endWfp.getCommentJson();
				String endPath = endWfp.getPdfPath();
				//查询插入的数据刻录
				ZwkjForm zwkjForm  = zwkjFormDao.getOneFormById(formId);
				if(zwkjForm!=null){
					String insert_table = zwkjForm.getInsert_table();		//查询出插入的数据表
					//更新业务数据表
					String sql = "update "+insert_table +" t set t."+fieldName+"='"+fieldValue+"'" +
							" where t.instanceId = '"+instanceId+"' and t.formId='"+formId+"'";	
					//System.out.println("sql="+sql);
					try{
						webServiceDao.executeSql(sql);
					}catch (Exception e) {			//执行update语句出现异常直接抛出数据
						result.put("success", false);
						result.put("message", "对应的业务表中没有该字段,请重新检查！");
						return result.toString();
					}
					//1, 获取action中的方法,慎用
					TableInfoAction tableInfoAction = Constant.tableInfoAction;
					if(tableInfoAction==null){
						ApplicationContext ctx = new ClassPathXmlApplicationContext(
										new String[]{"classpath*:spring/*/*.xml","classpath*:spring/*/*/*.xml","classpath*:spring/*/*/*/*.xml"});
						Constant.tableInfoAction = (TableInfoAction) ctx.getBean("tableInfoAction");
						tableInfoAction = Constant.tableInfoAction;
						ctx = null;
					}
					//2. 获取业务表单值
					String value = tableInfoAction.saveForm(formId, 2, instanceId, formId, fromNodeId, workFlowId,"","false", userId);
					String updateValue = "";
					if(value!=null && !value.equals("")){
						String[] data = value.split(";");
						for(String vak : data){
							String[] keyValue = vak.split(":");
							String key = keyValue[0];
							if(key.equalsIgnoreCase(fieldName)){
								updateValue += key+":"+fieldValue+";";
							}else{
								updateValue += vak+";";
							}
						}
					}
					try {
						String pdfPath = tableInfoAction.getHtmlToNewOfPath(formId, nodeId, updateValue, instanceId, false, false);
						String truePath  = new PDFToTrue().pdfToTrue(pdfPath, trueJson);
						//String[] paths = endPath.split(",");
						endPath = truePath+","+truePath;
						//System.out.println("truePath="+truePath);
						endWfp.setPdfPath(endPath);
						tableInfoDao.update(endWfp);
						result.put("success", true);
					} catch (Exception e) {
						e.printStackTrace();
						result.put("success", false);
						result.put("message", "重新生成表单出现异常!");
						return result.toString();
					}
				}
			}else{		//办件未办结, 不予处理
				result.put("success", false);
				result.put("message", "办件未办结,操作被驳回!");
				return result.toString();
			}
		}else{	//传输过来的json数据有问题
			result.put("success", false);
			result.put("message", "传输的数据格式为空");
			return result.toString();
		}
		return result.toString();
	}


	public WebServiceDao getWebServiceDao() {
		return webServiceDao;
	}


	public void setWebServiceDao(WebServiceDao webServiceDao) {
		this.webServiceDao = webServiceDao;
	}


	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}


	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}


	public ItemDao getItemDao() {
		return itemDao;
	}


	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}


	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}


	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}


	public WorkflowBasicFlowDao getWorkflowBasicFlowDao() {
		return workflowBasicFlowDao;
	}


	public void setWorkflowBasicFlowDao(WorkflowBasicFlowDao workflowBasicFlowDao) {
		this.workflowBasicFlowDao = workflowBasicFlowDao;
	}


	public FieldMatchingService getFieldMatchingService() {
		return fieldMatchingService;
	}


	public void setFieldMatchingService(FieldMatchingService fieldMatchingService) {
		this.fieldMatchingService = fieldMatchingService;
	}


	public ZwkjFormDao getZwkjFormDao() {
		return zwkjFormDao;
	}


	public void setZwkjFormDao(ZwkjFormDao zwkjFormDao) {
		this.zwkjFormDao = zwkjFormDao;
	}


	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	
	
}
