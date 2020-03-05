package cn.com.trueway.workflow.webService.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.document.business.docxg.client.support.DocXgXmlUtil;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.util.GenePdfUtilArchive;
import cn.com.trueway.workflow.set.util.ToPdfUtil;
import cn.com.trueway.workflow.webService.dao.ExchangeSendDao;
import cn.com.trueway.workflow.webService.service.ExchangeSendService;

import com.trueway.client.util.CommonUtils;

public class ExchangeSendServiceImpl implements ExchangeSendService{
	
	private EmployeeDao employeeDao;
	
	private EmployeeService employeeService;
	
	private DepartmentService  departmentService;
	
	private TableInfoDao tableInfoDao;
	
	private PendingService pendingService;
	
	private DictionaryService dictionaryService;
	
	private AttachmentService attachmentService;
	
	private FlowService flowService;
	
	private ExchangeSendDao exchangeSendDao;
	
	public ExchangeSendDao getExchangeSendDao() {
		return exchangeSendDao;
	}

	public void setExchangeSendDao(ExchangeSendDao exchangeSendDao) {
		this.exchangeSendDao = exchangeSendDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}


	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	/**
	 * 接口获取待收列表
	 * @param userId
	 * @return
	 */
	public String getToBeReceivedList(String userId,String pageIndex_str,String pageSize_str){
		String status = "0";
		
		int pageIndex = 1;
		
		int pageSize = 1;
		
		if(CommonUtil.stringIsNULL(userId)){
			return "noUserId";//未传入userId
		}
		userId = userId.replace("'", "").replace("\"", "").replace("\r\n", "");
		Employee dep_c = employeeService.findEmployeeById(userId);
		
		if(null == dep_c){
			return "noDepId";//未找到部门id
		}
		
		String depId = dep_c.getDepartmentGuid();
		
		if(CommonUtil.stringIsNULL(depId)){
			return "noDepId";//未找到部门id
		}
		
		if(CommonUtil.stringIsNULL(pageIndex_str)){
			pageIndex = -1;
		}else{
			pageIndex = Integer.valueOf(pageIndex_str);
		}
		
		if(CommonUtil.stringIsNULL(pageSize_str)){
			pageSize = -1;
		}else{
			pageSize = Integer.valueOf(pageSize_str);
		}
		
		
		String isDept = "";
		
		Map<String, String> searchmap = new HashMap<String, String>();
		
		Department dep = departmentService.findDepartmentById(depId);
		String departId = dep.getSuperiorGuid();	//获取当前人员的父机构Id
		String linkDeptIds = dep.getDepartmentGuid();
		if (!CommonUtils.isEmpty(linkDeptIds)) {  
			linkDeptIds = "'" + linkDeptIds.replaceAll(",", "','") + "'";
		}
		if (CommonUtil.stringIsNULL(isDept)  || !"1".equals(isDept)){
			linkDeptIds += ",'"+departId+"'";
		}
		
		searchmap.put("departId", linkDeptIds);
		
		List<LowDoc> list_low = tableInfoDao.getLowDocList(
				userId,pageIndex, pageSize,Integer.parseInt(status), searchmap);
		List<LowDoc> list_new = new ArrayList<LowDoc>();
		for(LowDoc lowDoc:list_low){
			String receiveId = lowDoc.getReceiveId();
			String title = lowDoc.getTitle();
			if(CommonUtil.stringNotNULL(title)){
				title = title.trim();
			}
			String path = "";
			String toExchangePath = lowDoc.getToExchangePath();
			File file_old = new File(toExchangePath);
			long pdfsize = file_old.length();
			if(CommonUtil.stringIsNULL(toExchangePath) || !(file_old.exists())){
				String uuid_all = "";
				boolean isExist = false;
				int limit_num = 0;
				while(!isExist&&limit_num<3){
					uuid_all = getdownLoad_archive(receiveId,title,"",dep_c);
					isExist = getFileExist(uuid_all);
					limit_num++;
					try {
						if(!isExist){
							System.out.println("文件还未生成，请稍候！");
							Thread.sleep(2000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				String downpdf = "";
				String syspath = "";
				
				if(isExist){
					downpdf = uuid_all.split(";")[0]+".pdf";
					syspath = uuid_all.split(";")[1];
				}
				String location_path = syspath+downpdf;
				File file_new = new File(location_path);
				pdfsize = file_new.length();
				path = SystemParamConfigUtil.getParamValueByParam("filedownloadUrl")+"?isabsolute=1&name="+title+".pdf"+"&location=" +
						location_path;
				
				
			}else{
				path = SystemParamConfigUtil.getParamValueByParam("filedownloadUrl")+"?isabsolute=1&name="+title+".pdf"+"&location=" +
						toExchangePath;
			}
			
			lowDoc.setPath_pdf(path);
			lowDoc.setPdfsize(pdfsize);
			
			list_new.add(lowDoc);
		}
		String xml = DocXgXmlUtil.genXmlForSendDocs(list_new);
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +xml;
		return xml;
	}
	
	/**
	 * 下级待收接口生成pdf
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-6-16 下午6:25:54
	 */
	public String getdownLoad_archive(String receiveId,String title,String type,Employee emp){
		String uuidall = "";
		DoFileReceive doFileReceive = tableInfoDao.getDoFileReceiveById(receiveId);
		try {
			String receiveType = "";
			String downpdf = "";
			if(doFileReceive!=null){
				String filename = title+".pdf";
				String pdfPath = doFileReceive.getToExchangePath();
				String trueJson =	doFileReceive.getTrueJson();
				receiveType = doFileReceive.getReceiveType();
				
				String processId = doFileReceive.getfProcessId();
				WfProcess wfProcess_ = tableInfoDao.getProcessById(processId);
				WfProcess wfProcess = pendingService.checkInstanceIsOver(wfProcess_);
				if(wfProcess==null || wfProcess.getPdfPath()==null || "".equals(wfProcess)){
					wfProcess = wfProcess_;
				}
				
				if(null != wfProcess && CommonUtil.stringNotNULL(wfProcess.getPdfPath())){
					
					//如果办文的话直接使用
					if(CommonUtil.stringIsNULL(pdfPath)){
						String[] pdfPaths = wfProcess.getPdfPath().split(",");
						if(pdfPaths.length>1){
							pdfPath = pdfPaths[1];
						}else{
							pdfPath = pdfPaths[0];
						}
						
						if(CommonUtil.stringNotNULL(receiveType) && "1".equals(receiveType)){//如果发文的话要去掉表单
							
							String newPdfPath = pdfPath.substring(0,pdfPath.lastIndexOf("/")+1) + UUID.randomUUID() + ".true";
							
							String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");// 正文附件的后缀
							String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");// 附加附件的后缀
							String attBwyjSuffixName = SystemParamConfigUtil.getParamValueByParam("attBwyjSuffixName");//办文意见附件的后缀
							List<SendAttachments> sattList = attachmentService.findAllSendAtts(wfProcess.getWfInstanceUid() + attSuffixName);
							List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(wfProcess.getWfInstanceUid() + attFjSuffixName);
							List<SendAttachments> sattBwyjList = attachmentService.findAllSendAtts(wfProcess.getWfInstanceUid()+attBwyjSuffixName);
							ToPdfUtil toPdfUtil = new ToPdfUtil();
							
							String json = toPdfUtil.toAndCombToPdf(sattList, sattExtList ,newPdfPath,wfProcess,attachmentService,null,flowService,emp);
							pdfPath = newPdfPath;
							trueJson = json;
						}
						doFileReceive.setPdfpath(pdfPath);
						
						if(trueJson==null || trueJson.equals("")){
							//拼凑地址
								TrueToPdf trueToPdf = new TrueToPdf();
								String path = "";
								String uuid = "";
								if(CommonUtil.stringNotNULL(pdfPath)){
									String[] result = trueToPdf.trueToPdf(pdfPath);
									downpdf = result[0];
									path = downpdf.substring(0,pdfPath.lastIndexOf("/")+1);
									uuid = pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.lastIndexOf("."));
								}
								
//								String path = SystemParamConfigUtil.getParamValueByParam("filedownloadUrl")+"?isabsolute=1&name="+filename+"&location=" +
//										downpdf;
								uuidall = uuid+";"+path;
								//getResponse().getWriter().print(uuidall);
							}
							//意见不为空,合并意见,采用异步处理方式
							if(trueJson!=null && !trueJson.equals("")){
								//随机生成pdf的地址: 非规则命名,避免与办件的附件冲突
								String uuid = UUID.randomUUID()+"" ;
								downpdf = uuid+".pdf";	
								String path = pdfPath.substring(0,pdfPath.lastIndexOf("/")+1);
								GenePdfUtilArchive genePdf = GenePdfUtilArchive.getInstance();
								genePdf.genePdf(pdfPath,trueJson,path+downpdf,"");
								//path  =	path+downpdf;
								uuidall = uuid+";"+path;
								//getResponse().getWriter().print(uuidall);
								doFileReceive.setTrueJson(trueJson);
							}
							
							if(CommonUtil.stringNotNULL(uuidall)){
								String[] syspaths = uuidall.split(";");
								String syspath = "";
								if(syspaths.length>1){
									syspath = uuidall.split(";")[1];
									String location_path = syspath+uuidall.split(";")[0]+".pdf";
									if(new File(location_path).exists()){
										//插入 附件流
										HashMap<String, String> keyValueSet = new HashMap<String, String>();
										keyValueSet.put("EXCHANGEFLOW",location_path);
										flowService.geneSql("T_WF_CORE_RECEIVE", keyValueSet, "ID",receiveId);
										doFileReceive.setToExchangePath(location_path);
									}else{
										doFileReceive.setToExchangePath(null);
									}
								}
							}
							exchangeSendDao.updateDoFileReceive(doFileReceive);
//							tableInfoDao.updateDoFileReceive(doFileReceive);
					}else{
						String path = pdfPath.substring(0,pdfPath.lastIndexOf("/")+1);	
						String uuid = pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.lastIndexOf("."));	
						uuidall = uuid+";"+path;
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return uuidall;
	}
	
	
	
	/**
	 * 验证pdf是否存在;  若存在,返回下载路径;  不存在则返回false;
	 */
	public boolean getFileExist(String uuid){
		boolean isExist = false;
		if(CommonUtil.stringNotNULL(uuid)){
			String[] uuids = uuid.split(";");
			if(uuids.length>1){
				String downpdf = uuid.split(";")[0]+".pdf";
				String syspath = uuid.split(";")[1];
				String path = syspath+"/"+downpdf;
				File file = new File(path);
				boolean	flag =file.exists();
				try {
					if(!flag){
						isExist = false;
					} else{
						isExist = true;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return isExist;
	}
	
	/**
	 * 下级来文收取接口
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-6-17 下午3:32:11
	 */
	public String updateLowDoFileReceive(String id){
		// 获取主键id
		DoFileReceive doFileReceive = tableInfoDao.getDoFileReceiveById(id);
		boolean msg = true;
		if (doFileReceive != null) { // 做更新操作
			Integer status = doFileReceive.getStatus();
			if(status!=null && status==1){
				msg = false;
				return "haveCharge";//已经收取
			}else{
				// 获取部门 
				String currentDeptId = doFileReceive.getToDepartId();
				String linkDeptIds = currentDeptId;
				List<WfDictionary> keys = dictionaryService.getDictionaryByName("linkDept");
				for(int index = 0 ; index <keys.size() ; index++){
					WfDictionary dic = keys.get(index);
					String[] dicKeys = dic.getVc_key().split(",");
					String[] dicValues = dic.getVc_value().split(",");
					for(int j = 0 ; j < dicValues.length ; j++){
						String temp = SystemParamConfigUtil.getParamValueByParam(dicValues[j]);
						if(temp != null && temp.indexOf(linkDeptIds)>-1){
							linkDeptIds = temp ;
							break;
						}
					}
				}
				if(currentDeptId.equals(linkDeptIds)){
					doFileReceive.setStatus(1); // 表示签收
					doFileReceive.setUpdateType(1);//发文接口收取
					doFileReceive.setRecDate(new Date());
					String commentJson = doFileReceive.getTrueJson();		//true意见
					String oldTruePath = doFileReceive.getPdfpath();
					if(oldTruePath!=null && !oldTruePath.equals("")){
						String[] args = new TrueToPdf().trueToPdf(oldTruePath);
						String  pdfPath= "";
						if(args!=null && args.length >0){
							pdfPath = args[0];
						}
						String truePath = new PDFToTrue().pdfToTrue(pdfPath, commentJson);
						doFileReceive.setPdfpath(truePath);
					}
					//将意见合并到true文件中
					/*if(jrdb.equals("false")&&!"2".equals(doFileReceive.getReceiveType())){
						createRecTrue(doFileReceive);
					}*/
					tableInfoDao.updateDoFileReceive(doFileReceive);
				}else{
					// 根据to 部门id，instanceid 查询 数据
					if (!CommonUtils.isEmpty(linkDeptIds)) {  
						linkDeptIds = "'" + linkDeptIds.replaceAll(",", "','") + "'";
					}
					List<DoFileReceive> receivers = tableInfoDao.getDoFileReceiveByPIdAndDeptIds(doFileReceive.getpInstanceId(),linkDeptIds);
					Date currentDate = new Date();
					if(receivers != null&&receivers.size()>0){
						for(int i = 0 ; i < receivers.size(); i++){
							DoFileReceive rece = receivers.get(i);
							rece.setStatus(1); // 表示签收
							doFileReceive.setUpdateType(1);//发文接口收取
							rece.setRecDate(currentDate);
							String commentJson = doFileReceive.getTrueJson();		//true意见
							String oldTruePath = doFileReceive.getPdfpath();
							String[] args = new TrueToPdf().trueToPdf(oldTruePath);
							String  pdfPath= "";
							if(args!=null && args.length >0){
								pdfPath = args[0];
							}
							String truePath = new PDFToTrue().pdfToTrue(pdfPath, commentJson);
							doFileReceive.setPdfpath(truePath);
							/*if(jrdb.equals("false")&&!"2".equals(rece.getReceiveType())){
								createRecTrue(rece);
							}*/
							tableInfoDao.updateDoFileReceive(rece);
						}
					}
				}
				// 查询 出 
				//统一部门的
				msg = true;
			}
		}
		if(msg){
			return "success";//收取成功
		}else{
			return "error";//收取失败
		}
		
	}
	
	
}
