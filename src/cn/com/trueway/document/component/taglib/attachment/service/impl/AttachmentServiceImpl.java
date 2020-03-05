package cn.com.trueway.document.component.taglib.attachment.service.impl;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.Attachmentsext_type;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttHistoryLog;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsHistory;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.FlowDao;
import cn.com.trueway.workflow.core.pojo.WfOnlineEditStatus;
import cn.com.trueway.workflow.set.util.PdfPage;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

import com.itextpdf.text.pdf.PdfReader;

public class AttachmentServiceImpl implements AttachmentService {
	
	private AttachmentDao attachmentDao;
	
	private FlowDao flowDao;
	
	public FlowDao getFlowDao() {
		return flowDao;
	}
	           
	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（发文...）
	 *
	 * @param id
	 * @return OA_DOC_Attachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:44:45
	 */
	public SendAttachments findSendAtts(String id){
		return attachmentDao.findSendAtts(id);
	}
	
	/**
	 * 
	 * 描述：根据ID查出单个【附加附件】（发文...）
	 *
	 * @param id
	 * @return OA_DOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:23
	 */
	public SendAttachmentsext findSendAttsext(String id){
		return attachmentDao.findSendAttsext(id);
	}
	
	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:38
	 */
	public ReceiveAttachments findReceiveAtts(String id){
		return attachmentDao.findReceiveAtts(id);
	}

	/**
	 * 
	 * 描述：根据ID查出单个【附加附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:52
	 */
	public ReceiveAttachmentsext findReceiveAttsext(String id){
		return attachmentDao.findReceiveAttsext(id);
	}
	
	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（发文...）
	 *
	 * @param docguid
	 * @return List<OA_DOC_Attachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:35:01
	 */
	public List<SendAttachments> findAllSendAtts(String docguid,String isHb){
		return attachmentDao.findAllSendAtts(docguid,isHb);
	}

	
	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（发文...）
	 *
	 * @param docguid
	 * @return List<OA_DOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:37:21
	 */
	public List<SendAttachmentsext> findAllSendAttsext(String docguid){
		return attachmentDao.findAllSendAttsext(docguid);
	}

	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:01
	 */
	public List<ReceiveAttachments> findAllReceiveAtts(String docguid){
		return attachmentDao.findAllReceiveAtts(docguid);
	}

	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:30
	 */
	public List<ReceiveAttachmentsext> findAllReceiveAttsext(String docguid){
		return attachmentDao.findAllReceiveAttsext(docguid);
	}
	
	/**
	 * 
	 * 描述：增加【正文附件】（发文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:39:37
	 */
	public SendAttachments addSendAtts(SendAttachments atts){
		SendAttachments  sAtt = attachmentDao.addSendAtts(atts);
		return sAtt;
	}
	
	/**
	 * 
	 * 描述：增加【附加附件】（发文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:18
	 */
	public void addSendAttsext(SendAttachmentsext attsext){
		attachmentDao.addSendAttsext(attsext);
	}
	
	/**
	 * 
	 * 描述：增加【正文附件】（收文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:36
	 */
	public void addReceiveAtts(ReceiveAttachments atts){
		attachmentDao.addReceiveAtts(atts);
	}
	
	/**
	 * 
	 * 描述：增加【附加附件】（收文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:41:11
	 */
	public void addReceiveAttsext(ReceiveAttachmentsext attsext){
		attachmentDao.addReceiveAttsext(attsext);
	}

	/**
	 * 
	 * 描述：删除【正文附件】（发文或收文根据isReceive判断）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteAtts(String attsId,boolean isReceive){
//		if(isReceive){
//			attachmentDao.deleteReceiveAtts(attsId);
//		}else{
			attachmentDao.deleteSendAtts(attsId);
//		}
	}

	/**
	 * 
	 * 描述：删除【附加附件】（发文或收文根据isReceive判断）
	 *
	 * @param attsextId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:43:50
	 */
	public void deleteAttsext(String attsextId,boolean isReceive){
		if(isReceive){
			attachmentDao.deleteReceiveAttsext(attsextId);
		}else{
			attachmentDao.deleteSendAttsext(attsextId);
		}
	}

	/**
	 * 
	 * 描述：word转成CEB文件后和CEB文件盖章后 CEB文件的导入
	 *
	 * @param attObj
	 * @param isManAtt void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-3 上午10:36:02
	 */
	public void addCebAtt(Object attObj, boolean isManAtt,String toRemoveFileid) {
		if(isManAtt&&attObj instanceof SendAttachments){
			SendAttachments atts= (SendAttachments)attObj;
			attachmentDao.addSendAtts(atts);
			if(toRemoveFileid!=null){
				attachmentDao.deleteSendAtts(toRemoveFileid);
			}
		}else{
			SendAttachmentsext attsext = (SendAttachmentsext)attObj;
			attachmentDao.addSendAttsext(attsext);
			if(toRemoveFileid!=null){
				attachmentDao.deleteSendAttsext(toRemoveFileid);
			}
		}
	}
	
	/**
	 * 
	 * 描述：正文在线编辑历史 保存
	 *
	 * @param sendAttachmentsHistory void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:13
	 */
	public void addSendAttHistory(SendAttachmentsHistory sendAttachmentsHistory){
		attachmentDao.addSendAttHistory(sendAttachmentsHistory);
	}
	
	/**
	 * 
	 * 描述：查询 正文在线编辑历史
	 *
	 * @param docguid
	 * @return List<SendAttachmentsHistory>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:17
	 */
	public List<SendAttachmentsHistory> findAllSendAttHistory(String docguid, String id){
		return attachmentDao.findAllSendAttHistory(docguid,id);
	}

	public void updateSendAtt(SendAttachments att) {
		attachmentDao.updateSendAtt(att);
	}

	public void addSendAttsext(List<SendAttachmentsext> attsextList) {
		for (SendAttachmentsext sendAttachmentsext : attsextList) {
			attachmentDao.addSendAttsext(sendAttachmentsext);
		}
	}

	public List<Attachmentsext_type> findAllAttType() {
		return attachmentDao.findAllAttType();
	}

	public SendAttachments modifyAtts(String attsId) {
		return attachmentDao.modifyAtts(attsId);
	}

	public void modifyOfAtts(String attsId, String title, String type) {
		attachmentDao.modifyOfAtts(attsId,title,type);
	}

	public List<SendAttachments> findSendAttsByDocguid(String instanceId) {
		return attachmentDao.findSendAttsByDocguid(instanceId);
	}
	
	public List<SendAttachments> findSendAttachmentList(String instanceId,  List<String> typeList){
		return attachmentDao.findSendAttachmentList(instanceId, typeList);
	}


	public List<SendAttachmentsHistory> findIsEditOfSendAttHistory(String name) {
		return attachmentDao.findIsEditOfSendAttHistory(name);
	}

	public void updateSendAttHistory(SendAttachmentsHistory sh) {
		attachmentDao.updateSendAttHistory(sh);
	}

	@Override
	public List<SendAttachments> findSendAttsByIdAndUserName(String instanceId, String employeeGuid) {
		return attachmentDao.findSendAttsByIdAndUserName(instanceId,employeeGuid);
	}

	@Override
	public void deleteAtts(SendAttachments att) {
		attachmentDao.deleteAtts(att);
	}
	@Override
	public SendAttachments findAllSendAtt(String name) {
		return attachmentDao.findAllSendAtt(name);
	}
	@Override
	public List<SendAttachments> findSendAttachmentListByInstanceId(
			String instanceId) {
		return attachmentDao.findSendAttachmentListByInstanceId(instanceId);
	}
	@Override
	public List<SendAttachments> getAttachListByNode(Map<String, String> map) {
		List<SendAttachments> attachList = new ArrayList<SendAttachments>();
		attachList = attachmentDao.getAttachListByNode(map);
		return attachList;
	}

	@Override
	public String[] mergerAttToPdf(String pdfFormPath, String trueJson,String instanceId, String pdfNewPath, int copyNum) {
		String[] str = new String[2];
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		try{
			// 获取正文附件地址 doc,ceb
			String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");		// 正文附件的后缀
			String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");	// 附加附件的后缀
			List<SendAttachments> sattList = attachmentDao.findAllSendAtts(instanceId + attSuffixName, "");
			List<SendAttachments> sattExtList = attachmentDao.findAllSendAtts(instanceId + attFjSuffixName, "");
			if(sattList==null){
				sattList = new ArrayList<SendAttachments>();
			}
			if(sattExtList!=null && sattExtList.size()>0){
				sattList.addAll(sattExtList);
			}
			// 合并后的pdf地址
			String saveMergePath = pdfFormPath;
			//合并的文件集合
			String fileStrs = pdfFormPath + ",";
			for(int i=0;i<copyNum;i++){
				fileStrs += pdfFormPath + ",";
			}
			String fileTyle = ""; //文件类型
			ToPdfUtil pdfUtil = new ToPdfUtil();
			if (sattList.size() != 0 && !("").equals(sattList)) {
				for (SendAttachments sat : sattList) {
					FileUtils.byteArrayToFile(sat,attachmentDao);
					//正文中存在同名ceb则不合入ceb
					boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
					
					fileTyle = sat.getFiletype();	//小写
					if ((("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) ) {
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
							String fp = sat.getTopdfpath();
							File file = new File(fp);
							//判断附件是否转成了pdf,因有同步过来的数据,需检查
							if(!file.exists()){
								fp = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, instanceId, sat.getId() ); 
								sat.setTopdfpath(fp);
								attachmentDao.updateSendAtt(sat);
							}
							//直接获取
							fileStrs += fp + ",";
						}else{	//word转换为pdf,并且update数据
							String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, instanceId, sat.getId()); 
							fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentDao.updateSendAtt(sat);
						}
					} else if (("ceb").equalsIgnoreCase(sat.getFiletype()) && isSatt_ceb) {
						CebToPdf cp = new CebToPdf();
						String cebPath = "";
						// 文件路径
						cebPath += pdfRoot+sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf";
						if(!new File(cebPath).exists()){
							cp.cebToPdf( pdfRoot+ sat.getLocalation());
						}
						fileStrs+=cebPath+",";
					}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
						try {
								new PdfReader(pdfRoot+sat.getLocalation());
								fileStrs += pdfRoot+sat.getLocalation() + ",";
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("加密pdf处理");
								fileStrs += sat.getTopdfpath() + ",";
							}
					}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
						if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
							String fp = sat.getTopdfpath();
							File file = new File(fp);
							//判断附件是否转成了pdf,因有同步过来的数据,需检查
							if(!file.exists()){
								fp = pdfUtil.xlsToPdf(sat.getLocalation(),fileTyle, instanceId, sat.getId()); 
								sat.setTopdfpath(fp);
								attachmentDao.updateSendAtt(sat);
							}
							fileStrs += fp + ",";
						}else{
							String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle, instanceId, sat.getId());
							fileStrs += path+ ",";
							sat.setTopdfpath(path);
							attachmentDao.updateSendAtt(sat);
						}
					}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") 
							|| fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")|| fileTyle.equalsIgnoreCase("tif")){
						fileStrs+= pdfUtil.imgToPdf(sat.getLocalation(), fileTyle)+ ",";
					}else if(("true").equalsIgnoreCase(fileTyle)){
						fileStrs += pdfRoot+sat.getLocalation() + ",";
					}
				}
			}
			// 合并正文附件的pdf和表单的pdf
			MergePdf mp = new MergePdf();
			fileStrs = CommonUtil.removeLastStr(fileStrs);
			String[] files = null;
			String json= "";
			if (!("").equals(fileStrs) && fileStrs.length() > 0) {
				//去掉文件组合中空字符串的路径
				String[] fileStrs_all = fileStrs.split(",");
				fileStrs = "";
				for(String s:fileStrs_all){
					if(CommonUtil.stringNotNULL(s)){
						fileStrs += s +",";
					}
				}
				if(CommonUtil.stringNotNULL(fileStrs)){
					fileStrs = fileStrs.substring(0,fileStrs.length()-1);
				}
				files = new String[fileStrs.split(",").length];
				for (int i = 0; i < fileStrs.split(",").length; i++) {
					files[i] = fileStrs.split(",")[i];
				}
				saveMergePath = pdfNewPath.replace(".true", ".pdf");
				mp.mergePdfFiles(files, saveMergePath);
			}
			str[1] = json;
			saveMergePath = new PDFToTrue().pdfToTrue(saveMergePath, json);
			str[0] = saveMergePath;
			return str;
		}catch (Exception e) {
			return null;
		}
	}
	
	public List<SendAttachments> findAllSendAtts(String docguid){
		return attachmentDao.findAllSendAtts(docguid);
	}
	
	/**
	 * 更新附件页数
	 * 描述：TODO 对此方法进行描述
	 * @param sattList
	 * @return int
	 * 作者:季振华
	 * 创建时间:2017-7-27 上午10:41:37
	 */
	@Override
	public int updateAttsToPageCount(List<SendAttachments> sattList){
		//pdf地址
		try{
			String filePathOfSys = SystemParamConfigUtil
					.getParamValueByParam("filePath");
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			
			String fileTyle = ""; //文件类型
			ToPdfUtil pdfUtil = new ToPdfUtil();
			int pageCount_all = 0;
			int pageCount  =1;
			for (SendAttachments sat : sattList) {
				FileUtils.byteArrayToFile(sat,attachmentDao);
				fileTyle = sat.getFiletype().toLowerCase();	//小写
				
				if ((("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx"))) {
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							fp = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, sat.getDocguid(), sat.getId()); 
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
					}else{	//word转换为pdf,并且update数据
						String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle, sat.getDocguid(), sat.getId()); 
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}
				else if (("ceb").equalsIgnoreCase(sat.getFiletype())) {
					CebToPdf cp = new CebToPdf();
					// 文件路径
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							if(new File(filePathOfSys + sat.getLocalation()).exists()){
								cp.cebToPdf(filePathOfSys + sat.getLocalation());
								fp += filePathOfSys+ sat.getLocalation()
										.substring(0,sat.getLocalation().length() - 3) + "pdf";
							}else{
								cp.cebToPdf(pdfRoot+"/" + sat.getLocalation());
								fp += pdfRoot+"/" + sat.getLocalation()
										.substring(0,sat.getLocalation().length() - 3) + "pdf";
							}
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
					}else{	//word转换为pdf,并且update数据
						String path = "";
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							cp.cebToPdf(filePathOfSys + sat.getLocalation());
							path += filePathOfSys+ sat.getLocalation()
									.substring(0,sat.getLocalation().length() - 3) + "pdf";
						}else{
							cp.cebToPdf(pdfRoot+"/" + sat.getLocalation());
							path += pdfRoot+"/" + sat.getLocalation()
									.substring(0,sat.getLocalation().length() - 3) + "pdf";
						} 
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}
				else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							if(new File(filePathOfSys + sat.getLocalation()).exists()){
								fp = filePathOfSys+sat.getLocalation();
							}else{
								fp = pdfRoot+sat.getLocalation();
							} 
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
					}else{
						String path = "";
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							path = filePathOfSys+sat.getLocalation();
						}else{
							path = pdfRoot+sat.getLocalation();
						}
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
						
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							fp = pdfUtil.xlsToPdf(sat.getLocalation(),fileTyle, sat.getDocguid(), sat.getId());
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
					}else{
						String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle, sat.getDocguid(), sat.getId());
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}else if(("jpg").equalsIgnoreCase(fileTyle) 
						|| fileTyle.equalsIgnoreCase("png") 
						|| fileTyle.equalsIgnoreCase("jpeg") 
						|| fileTyle.equalsIgnoreCase("bmp")){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							fp = pdfUtil.imgToPdf(sat.getLocalation(), fileTyle);
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
						
					}else{	//word转换为pdf,并且update数据
						String path="";
						path = pdfUtil.imgToPdf(sat.getLocalation(), fileTyle);
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}else if(("true").equalsIgnoreCase(fileTyle)){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String pdfpath = sat.getTopdfpath();
						String truepath = "";
						File file = new File(pdfpath);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							if(new File(filePathOfSys + sat.getLocalation()).exists()){
								truepath =filePathOfSys+sat.getLocalation();
							}else{
								truepath = pdfRoot+sat.getLocalation();
							}
							TrueToPdf trueToPdf = new TrueToPdf();
							String[] result = trueToPdf.trueToPdf(truepath);
							pdfpath = result[0];
							sat.setTopdfpath(pdfpath);
							try {
								pageCount = PdfPage.getPdfPage(pdfpath);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
						
					}else{
						String pdfpath = "";
						String truepath = "";
						if(new File(filePathOfSys + sat.getLocalation()).exists()){
							truepath =filePathOfSys+sat.getLocalation();
						}else{
							truepath = pdfRoot+sat.getLocalation();
						}
						TrueToPdf trueToPdf = new TrueToPdf();
						String[] result = trueToPdf.trueToPdf(truepath);
						pdfpath = result[0];
						sat.setTopdfpath(pdfpath);
						try {
							pageCount = PdfPage.getPdfPage(pdfpath);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}else if(("ppt").equalsIgnoreCase(fileTyle)){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							fp = pdfUtil.pptToPdf(sat.getLocalation(), fileTyle);
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
						
					}else{	//word转换为pdf,并且update数据
						String path="";
						path = pdfUtil.pptToPdf(sat.getLocalation(), fileTyle);
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}else if(("txt").equalsIgnoreCase(fileTyle)){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						File file = new File(fp);
						//判断附件是否转成了pdf,因有同步过来的数据,需检查
						if((!file.exists()) 
								|| sat.getPagecount() == null 
								|| sat.getPagecount() == 0){
							fp = pdfUtil.txtToPdf(sat.getLocalation());
							sat.setTopdfpath(fp);
							try {
								pageCount = PdfPage.getPdfPage(fp);
							} catch (Exception e) {
								pageCount = 0;
							}
							sat.setPagecount(pageCount);
							updateSendAtt(sat);
						}else{
							pageCount = sat.getPagecount() == null?0:sat.getPagecount();
						}
						
					}else{	//word转换为pdf,并且update数据
						String path="";
						path = pdfUtil.txtToPdf(sat.getLocalation());
						sat.setTopdfpath(path);
						try {
							pageCount = PdfPage.getPdfPage(path);
						} catch (Exception e) {
							pageCount = 0;
						}
						sat.setPagecount(pageCount);
						updateSendAtt(sat);
					}
					pageCount_all += pageCount;
				}
			}
			return pageCount_all;
		}catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void saveCutPages(CutPages entity) {
		attachmentDao.saveCutPages(entity);
	}

	@Override
	public List<CutPages> findCutPagesListByDocId(String docId) {
		return attachmentDao.findCutPagesListByDocId(docId);
	}
	
	@Override
	public void deleteCutPages(CutPages entity){
		attachmentDao.deleteCutPages(entity);
	}
	
	@Override
	public Integer countNoCuteAtt(Integer fileSize) {
		return attachmentDao.countNoCuteAtt(fileSize);
	}

	@Override
	public List<String> getNoCuteAtt(Integer fileSize, Integer pageIndex,
			Integer pageSize) {
		return attachmentDao.getNoCuteAtt(fileSize, pageIndex, pageSize);
	}
	
	@Override
	public void update(Blob data, Blob pdfData, String fileId){
		attachmentDao.update(data, pdfData, fileId);
	}

	@Override
	public SendAttachmentsHistory findSendAttHistory(String id) {
		return attachmentDao.findSendAttHistory(id);
	}

	@Override
	public WfOnlineEditStatus findOESByInfo(String attId, String userId) {
		return attachmentDao.findOESByInfo(attId,userId);
	}

	@Override
	public void addWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus) {
		attachmentDao.addWfOnlineEditStatus(wfOnlineEditStatus);
	}

	@Override
	public void updateWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus) {
		attachmentDao.updateWfOnlineEditStatus(wfOnlineEditStatus);
	}
	
	@Override
	public CutPages findCutPageById(String id){
		return attachmentDao.findCutPageById(id);
	}

	@Override
	public void deleteAttsByIds(String ids) {
		attachmentDao.deleteAttsByIds(ids);
	}

	@Override
	public void addSendHistoryLog(SendAttHistoryLog sendAttHistoryLog) {
		attachmentDao.addSendAttHistoryLog(sendAttHistoryLog);
	}
	
}
