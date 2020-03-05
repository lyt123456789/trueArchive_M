package cn.com.trueway.workflow.webService.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.ProXmlUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.set.util.ToPdfUtil;
import cn.com.trueway.workflow.webService.pojo.ProBaseDoc;
import cn.com.trueway.workflow.webService.pojo.ReceiveSendAtt;
import cn.com.trueway.workflow.webService.service.ExchangeReceiveService;
import cn.com.trueway.workflow.webService.util.FileUtil;

public class ExchangeReceiveServiceImpl implements ExchangeReceiveService{
	
	private TableInfoDao tableInfoDao;
	
	private AttachmentDao attachmentDao;
	
	private FlowService flowService;
	
	
	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public String receiveSendXml(String xml) {
		List<ProBaseDoc> list = new ArrayList<ProBaseDoc>();
		list = ProXmlUtil.analysisReceiveXml(xml);
		FileUtil fileUtil = new FileUtil();
		ToPdfUtil pdfUtil = new ToPdfUtil();
		String isHaveTitle = "";
		for(ProBaseDoc pbd:list){
			//入收文表
			if(CommonUtil.stringNotNULL(pbd.getTitle())){
				Sw sw = new Sw();
				sw.setLwbt(pbd.getTitle());
				sw.setLwh(pbd.getWh()==null?"":pbd.getWh());
				sw.setLwdw(pbd.getSendDepName()==null?"":pbd.getSendDepName());
				sw.setZsdw(pbd.getTodepNames()==null?"":pbd.getTodepNames());
				sw.setCsdw(pbd.getCcdepNames()==null?"":pbd.getCcdepNames());
				
				
				List<ReceiveSendAtt> atts = pbd.getAtt();
				String fileTyle = "";
				String fileStrs = "";
				String saveMergePath = "";
				String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_process");	
				saveMergePath =  pdfRoot+"/"+Constant.GENE_FILE_PATH+UuidGenerator.generate36UUID()+".pdf";

				for(ReceiveSendAtt att:atts){
					try {
						String fileName = UuidGenerator.generate36UUID()+"."+att.getType();
						String location = "";		//文件下载以后的地址
						int count_loc = 0;
						while(CommonUtil.stringIsNULL(location)&&count_loc<5){
							location = fileUtil.downloadFromUrl_pdf(att.getUrl(), fileName);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count_loc++;
						}
						fileTyle = att.getType();
						if (("doc").equals(fileTyle) || fileTyle.equals("docx")) {
							String path;
							path = pdfUtil.docToPdf(location,fileTyle,"","");
							fileStrs += path+ ",";
						} else if (("ceb").equals(fileTyle)) {
							CebToPdf cp = new CebToPdf();
							// 文件路径
							cp.cebToPdf(location);
							fileStrs += location.substring(0,location.length() - 3)+ "pdf,";
						}else if (("pdf").equals(fileTyle)){
							fileStrs += location + ",";
						}else if(("xlsx").equals(fileTyle) || fileTyle.equals("xls")){
							String path;
							path = pdfUtil.xlsToPdf(location, fileTyle,"","");
							fileStrs += path+ ",";
						}else if(("jpg").equals(fileTyle) || fileTyle.equals("png") || fileTyle.equals("jpeg") || fileTyle.equals("bmp")){
							fileStrs+= pdfUtil.imgToPdf(location, fileTyle)+ ",";
						}else if (("true").equals(fileTyle)){
							fileStrs += SystemParamConfigUtil.getParamValueByParam("filePath")+location + ",";
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				// 合并正文附件的pdf和表单的pdf
				MergePdf mp = new MergePdf();
				fileStrs = removeLastComma(fileStrs);
				String[] str = new String[2];
				//String fileStrs = pdfFormPath + "," + wordPath + "," + cebPath+","+pdfPath+","+xlsPath;
				String[] files = null;
				if (!("").equals(fileStrs) && fileStrs.length() > 0) {
					files = new String[fileStrs.split(",").length];
					for (int i = 0; i < fileStrs.split(",").length; i++) {
						files[i] = fileStrs.split(",")[i];
					}
					String json=mp.mergePdfFiles(files, saveMergePath);
					str[1] =json;
				}
				String attPath= saveMergePath;
				//生成附件
				String basePath = SystemParamConfigUtil.getParamValueByParam("filepath"); // 得到上传文件在服务器上的基路径
				String dstPath = FileUploadUtils.
						getRealFilePath(pbd.getTitle()+".pdf",
								basePath, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
				File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
				File attFile = new File(attPath);
				int attnum=0;
				while(!attFile.exists() && attnum<3){
					try {
						Thread.sleep(1000);
						attnum++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				FileUploadUtils.copy(attFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
				
				saveMergePath = new PDFToTrue().pdfToTrue(saveMergePath, str[1]);
				
				String toDepId = pbd.getTodepIds();//主送单位id
				String ccDepId = pbd.getCcdepIds();//抄送单位id
				String[] allDepIds = null;
				if(CommonUtil.stringIsNULL(toDepId)
						&&CommonUtil.stringIsNULL(ccDepId)){
					return "noDepId";
				}else{
					String allDepId = toDepId + "," + ccDepId;
					allDepIds = allDepId.split(",");
				}
				
				String[] fs = pbd.getDyfs().split(",");
				String jointUnit = "";
				if(allDepIds != null && allDepIds.length > 0){
					
					Date nowTime = new Date();
					for(int i=0; i<allDepIds.length; i++){
						String uId = allDepIds[i];
						String dfs = fs[i];
						String newInstanceId = UuidGenerator.generate36UUID();
						//点击发送按钮发送,插待办并入待收表
						addNewProcessOfReceiveSend(pbd.getInstanceId(),uId,sw, saveMergePath,
								pbd.getSenderId(),newInstanceId, dfs,nowTime,jointUnit,dstPath,dstFile.length());
					}
				}
			}else{
				isHaveTitle = "titleIsNull";
			}
			
		}
		System.out.println(xml);
		return "success" + isHaveTitle;
	}
	
	/**
	 * 根据数据入库
	 * 描述：TODO 对此方法进行描述
	 * @param wfProcess
	 * @param userId
	 * @param sw
	 * @param fjpath
	 * @param emp
	 * @param newInstanceId
	 * @param dyfs
	 * @param nowTime
	 * @param jointUnit void
	 * 作者:季振华
	 * 创建时间:2017-1-5 下午3:35:24
	 */
	public void addNewProcessOfReceiveSend(String instanceId,String userId,Sw sw, 
			String fjpath, String senderId,String newInstanceId, String dyfs,Date nowTime,String jointUnit,String dstPath,long length){
		
		//插入下一步
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			newProcess.setfInstancdUid("");
			newProcess.setWfInstanceUid(newInstanceId);
			newProcess.setWfUid("");
//			newProcess.setFinshTime(nowTime);
			newProcess.setApplyTime(nowTime);
			newProcess.setProcessTitle(sw.getLwbt());
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setFromNodeid("");
			newProcess.setToNodeid("");
			newProcess.setOldFormId("");
			newProcess.setFromUserId(senderId);
			newProcess.setOwner(senderId);
			newProcess.setIsEnd(0);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);    
			newProcess.setStatus("0");
			newProcess.setAction_status(0);
			newProcess.setAllInstanceid(newInstanceId);
			newProcess.setIsExchanging(0);
			newProcess.setUserUid(userId);
			newProcess.setIsMaster(1);
			newProcess.setIsBack("0");
			newProcess.setNodeUid("receiveXml");
		tableInfoDao.save(newProcess);
		
		//插入对应附件
		SendAttachments att = new SendAttachments();
		att.setDocguid(newProcess.getWfInstanceUid()+"fj");//暂定为附件
		att.setFilename(newProcess.getProcessTitle()+".pdf");
		att.setFiletype("pdf");// 设置文件类型(后缀名)的属性
		att.setFileindex(0L);
		att.setFilesize(length);// 设置文件大小的属性
		att.setLocalation(dstPath);// 设置上传后在服务器上保存路径的属性
		att.setTitle(newProcess.getProcessTitle()+".pdf");
		att.setType("正文材料");// 设置上传附件所属类别
		att.setFiletime(new Timestamp(new Date().getTime()));// 设置上传时间属性
		att = attachmentDao.addSendAtts(att);
		
		
	 	//插入待收
		DoFileReceive dfr = new DoFileReceive();
 			dfr.setInstanceId(newInstanceId);
 			dfr.setpInstanceId(instanceId);
 			dfr.setProcessId(newProcess.getWfProcessUid());
 			dfr.setFormUserId(senderId);
 			//dfr.setToUserId(userId);
 			dfr.setToDepartId(userId);
 			// 是否回复 为0
 			dfr.setIsReback(0);
 			dfr.setApplyDate(nowTime);
 			dfr.setType(1);
 			dfr.setStatus(0);
 			dfr.setPdfpath(fjpath);
 			dfr.setDyfs(dyfs==null?0:Integer.parseInt(dyfs));
 			dfr.setUpdateType(2);//收文接口收来的
 		
		tableInfoDao.addDoFileReceive(dfr);

		Sw newSw = new Sw();
			newSw.setLwbt(sw.getLwbt());
			newSw.setLwdw(sw.getLwdw());
			newSw.setLwh(sw.getLwh());
			newSw.setYfdw(sw.getYfdw());	//发文单位
			newSw.setGwlx(sw.getGwlx());
			newSw.setFs(sw.getFs());
			newSw.setFwsj(nowTime);		//发文时间
			newSw.setInstanceid(newInstanceId);
			newSw.setZsdw(sw.getZsdw());
			newSw.setCsdw(sw.getCsdw());
			newSw.setZtc(sw.getZtc());
			newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
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
	
	
}
