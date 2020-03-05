package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.util.CebToPdf;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.ExcelToPdf;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.base.util.TxtToPdf;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.WordToPdfOfPrinter_new;
import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.FlowService;

/**
 * 工具类: 将xls、xlsx、docx、doc、pdf、ceb转换成 pdf
 * @author caiyj
 *
 */
public class ToPdfUtil {
    /**
	 * 正文附件的后缀
	 */
	private final String ATT_SUFFIX_NAME = SystemParamConfigUtil.getParamValueByParam("attSuffixName"); 
	
	/**
	 * 附加附件的后缀
	 */
	private final String ATT_FJSUFFIX_NAME = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");
	/**
	 * 将word转换为pdf
	 * @param filePath		转换的文件路径
	 * @param type			doc/docx
	 * @return
	 * @throws IOException
	 */
	public String docToPdf(String filePath, String type, String instanceId, String attId) throws IOException {
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		FileUtils util = new FileUtils();
		String path = util.mergeUrl(pdfRoot, filePath);
		// 文件路径及文件名，除去后缀
		String fileAndPath = "";
		if(type.equals("doc")){
			fileAndPath = path.substring(0, path.length() - 4);
		}else if(type.equals("docx")){
			fileAndPath = path.substring(0, path.length() - 5);
		}
		WordToPdfOfPrinter_new d2p = new WordToPdfOfPrinter_new();
		try {
			boolean isSuccess = d2p.docToPDF(fileAndPath + "."+type, fileAndPath+ ".pdf", instanceId, attId);
			if(isSuccess){
				boolean success = (new File(fileAndPath + ".ps")).delete();
				boolean success2 = (new File(fileAndPath + ".log")).delete();
				if (success && success2) {
					System.out.println("删除打印机文件成功");
				}
				return fileAndPath + ".pdf";
			}else{
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * xls、xlsx 转换成pdf
	 * @param filePath
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public String xlsToPdf(String filePath, String type, String instanceId, String attId) throws Exception{
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		FileUtils util = new FileUtils();
		String path = util.mergeUrl(pdfRoot, filePath);
		
		// 文件路径及文件名，除去后缀
		String fileAndPath = "";
		if(type.equals("xls")){
			fileAndPath = path.substring(0, path.length() - 4);
		}else if(type.equals("xlsx")){
			fileAndPath = path.substring(0, path.length() - 5);
		}
//		Office2PDF xlsTodf = new Office2PDF();
		ExcelToPdf xlsToPdf = new ExcelToPdf();
		try {
			xlsToPdf.excelToPDF(fileAndPath + "."+type, fileAndPath+".pdf", instanceId, attId);
			return fileAndPath+".pdf";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String imgToPdf(String filePath, String type){
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		FileUtils util = new FileUtils();
		String path = util.mergeUrl(pdfRoot, filePath);
		// 文件路径及文件名，除去后缀
		String fileAndPath = "";
		if(type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png") 
				|| type.equalsIgnoreCase("bmp") || type.equalsIgnoreCase("tif")){
			fileAndPath = path.substring(0, path.length() - 4);
		}else if(type.equalsIgnoreCase("jpeg")){
			fileAndPath = path.substring(0, path.length() - 5);
		}
		//Office2Pdf xlsTodf = new Office2Pdf();
		ImageToPdf imgtoPdf = new ImageToPdf();
		try {
			imgtoPdf.imgToPdf(fileAndPath+".pdf", fileAndPath + "."+type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileAndPath+".pdf";
	}
	
	public String toAndCombToPdf(List<SendAttachments> sattList, 
			List<SendAttachments> sattExtList, String saveMergePath,
			WfProcess wfProcess, AttachmentService attachmentService,
			List<String> list,FlowService flowService,Employee emp) throws Exception{
		if((sattList==null || sattList.size()==0)
				&& sattExtList==null || sattExtList.size()==0){
			return "";
		}
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		//合并的文件集合
		String fileStrs = "";
		String fileTyle = ""; //文件类型
		ToPdfUtil toPdfUtil = new ToPdfUtil();
		if (sattList.size() != 0 && !("").equals(sattList)) {
			for (SendAttachments sat : sattList) {
				FileUtils.byteArrayToFile(sat,attachmentService);
				//正文中存在同名ceb则不合入ceb
				boolean isSatt_ceb = toPdfUtil.listIsHaveSameDocName(sattList,sat);
				
				fileTyle = sat.getFiletype().toLowerCase();	//小写
				if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						//直接获取
						fileStrs += sat.getTopdfpath()+ ",";
					}else{	//word转换为pdf,并且update数据
						String path = docToPdf(sat.getLocalation(),fileTyle, "", ""); 
						fileStrs += path+ ",";
					}
					
				}else if (("ceb").equals(sat.getFiletype()) && isSatt_ceb) {
					CebToPdf cp = new CebToPdf();
					// 文件路径
					cp.cebToPdf(pdfRoot+sat.getLocalation());
					fileStrs += pdfRoot+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";

				}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
					fileStrs += pdfRoot+sat.getLocalation() + ",";
				}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
						fileStrs += sat.getTopdfpath()+ ",";
					}else{
						String path = xlsToPdf(sat.getLocalation(), fileTyle, "", "");
						fileStrs += path+ ",";
					}
				}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
						fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
						|| fileTyle.equalsIgnoreCase("tif")){
					fileStrs+= imgToPdf(sat.getLocalation(), fileTyle)+ ",";
				}else if (("true").equals(sat.getFiletype())){
					fileStrs += pdfRoot+sat.getLocalation() + ",";
				}
			}
		}
		if (sattExtList.size() != 0 && !("").equals(sattExtList)) {
			for (SendAttachments satExt : sattExtList) {
				//附件中存在同名ceb则不合入ceb
				boolean isSattExt_ceb = toPdfUtil.listIsHaveSameDocName(sattExtList,satExt);
				
				//filefrom=1: 附件是由子流程返回
				Integer filefrom = satExt.getFilefrom();
				if(filefrom!=null && filefrom ==1){
					continue;
				}
				fileTyle = satExt.getFiletype().toLowerCase();
				if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
					if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){
						//直接获取
						fileStrs += satExt.getTopdfpath()+",";
					}else{	//word转换为pdf,并且update数据
						String path =docToPdf(satExt.getLocalation(),fileTyle, "",""); 
						fileStrs += path+ ",";
					}
				}else if (("ceb").equals(satExt.getFiletype()) && isSattExt_ceb) {
					CebToPdf cp = new CebToPdf();
					// 文件路径
					cp.cebToPdf(pdfRoot+satExt.getLocalation());
					fileStrs += pdfRoot+ satExt.getLocalation().substring(0,satExt.getLocalation().length() - 3) + "pdf,";
				}else if (("pdf").equalsIgnoreCase(satExt.getFiletype())){
					fileStrs += pdfRoot+satExt.getLocalation() + ",";
				}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					if(satExt.getTopdfpath()!=null && !satExt.getTopdfpath().equals("")){	
						fileStrs += satExt.getTopdfpath()+ ",";
					}else{
						String path = xlsToPdf(satExt.getLocalation(), fileTyle,"", "");
						fileStrs += path+ ",";
					}
				}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png")
						|| fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
						|| fileTyle.equalsIgnoreCase("tif")){
					fileStrs+= imgToPdf(satExt.getLocalation(), fileTyle)+ ",";
				}else if (("true").equals(satExt.getFiletype())){
					fileStrs += pdfRoot+satExt.getLocalation() + ",";
				}
			}
		}
		int pages = 0;
		try {
			// 判断是否存在
			File file = new File(wfProcess.getPdfPath().split(",")[0]);
			System.out.println("----"+wfProcess.getPdfPath());
			pages = PdfPage.getPdfPage(wfProcess.getPdfPath().split(",")[0]);
		} catch (Exception e) {
		}
		// 合并正文附件的pdf和表单的pdf
		MergePdf mp = new MergePdf();
		fileStrs = removeLastComma(fileStrs);
		String[] files = null;
		if (!("").equals(fileStrs) && fileStrs.length() > 0) {
			int page=0;
			files = new String[fileStrs.split(",").length];
			for (int i = 0; i < fileStrs.split(",").length; i++) {
				String url =fileStrs.split(",")[i];
				if(Utils.isNotNullOrEmpty(url)){
					try {
						page += PdfPage.getPdfPage(url);
					} catch (Exception e) {
					}
				}
				files[i] = url;
			}
			String json = wfProcess.getCommentJson();
			JSONArray newJs = new JSONArray();
			JSONObject newJsObject = new JSONObject();
			if(json!=null){
				JSONArray js;
				JSONArray resource;
				try {
					js = JSONArray.fromObject(json);
					for(int z=0;z<js.size();z++){
						JSONObject jsonObject = js.getJSONObject(z);
						int pa = jsonObject.getInt("page");
						if(pages-page<=pa){
							jsonObject.put("page",(pa-(pages-page)));
							newJs.add(jsonObject);
						}
					}
				} catch (Exception e) {
					JSONObject obj = JSONObject.fromObject(json);
					js	= obj.getJSONArray("pages");
					String ServerUrl = obj.getString("ServerUrl");
					String docId = obj.getString("docId");
					resource =  obj.getJSONArray("resources");
					int size = js.size();
					int nums = 0;
					for(int i = 0; i<size; i++){
						JSONObject jsonObject = js.getJSONObject(i);
						int pa = jsonObject.getInt("page");
						if(pages-page<=pa){
							jsonObject.put("page",(pa-(pages-page)));
							newJs.add(jsonObject);
						}
					}
					newJsObject.put("ServerUrl", ServerUrl);
					newJsObject.put("StampType", 1);
					newJsObject.put("docId", docId);
					newJsObject.put("pages", newJs);
					newJsObject.put("resource", resource);
				}
			}
			//1,将表单进行合并
			mp.mergePdfFiles(files, saveMergePath,newJsObject.toString());
			//2,
			TrueToPdf trueToPdf = new TrueToPdf();
			GenePdfUtil pdfUtil = GenePdfUtil.getInstance();
			String fjPath = UUID.randomUUID().toString();
			String newPdfPath = pdfRoot+dstPath + UuidGenerator.generate36UUID()+"RedChapter.pdf";
			String[] data = trueToPdf.trueToPdf(saveMergePath);
			if(data!=null && data.length>1){
				pdfUtil.geneStampPdf(data[0], newJsObject.toString(), newPdfPath, "0");
			}
			if(list!=null){
				for(int i=0;i<list.size();i++){
					File file = new File(newPdfPath);
					//File file = new File(saveMergePath.replace(".pdf", ".true"));
					//String filename = UUID.randomUUID().toString()+".true";
					String filename = fjPath+".pdf";			//生成的附件的名称
					File dstFile = new File(pdfRoot+dstPath +filename);// 创建一个服务器上的目标路径文件对象
					String oldpath = (dstPath +filename).substring(0, (dstPath +filename).length()-4);
					String redChapter = pdfRoot + oldpath.substring(0, oldpath.length())+"_red.pdf";
					if(data!=null && data.length>1){
						pdfUtil.geneStampPdf(data[0], newJsObject.toString(), redChapter, "1");
					}
					if(file.exists()){
						FileUploadUtils.copy(file, dstFile);
						SendAttachments att = new SendAttachments();
						att.setDocguid(list.get(i)+"fj");
						att.setFileindex(0L);
						att.setFilename(wfProcess.getProcessTitle()+".pdf");// 设置文件名属性
						//att.setFiletype("true");// 设置文件类型(后缀名)的属性
						att.setFiletype("pdf");// 设置文件类型(后缀名)的属性
						att.setFilesize(file.length());// 设置文件大小的属性
						att.setFiletime(new Date(System.currentTimeMillis()));// 设置上传时间属性
						att.setLocalation(dstPath +filename);// 设置上传后在服务器上保存路径的属性
						att.setType("正文材料");// 设置上传附件所属类别
						//att.setTitle(wfProcess.getProcessTitle()+".true");// 设置上传附件标题
						att.setTitle(wfProcess.getProcessTitle()+".pdf");// 设置上传附件标题
						att.setEditer(emp.getEmployeeGuid()+";"+emp.getEmployeeName());
						attachmentService.addSendAtts(att);
					}
				}
			}
			if(newJsObject.size()>0){
				return newJsObject.toString();
			}else{
				return "";
			}
		}
		return "";
	}
	
	public void saveAttList(String saveMergePath, String commentJson, AttachmentService attachmentService,
			List<String> list,FlowService flowService,Employee emp, WfProcess wfProcess, String pdfNewPath){
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		GenePdfUtil pdfUtil = GenePdfUtil.getInstance();
		String newPdfPath =  pdfRoot+dstPath+UuidGenerator.generate36UUID()+"RedChapter.pdf";
		pdfUtil.geneStampPdf(pdfNewPath, commentJson, newPdfPath, "0");

		String filename = pdfRoot+dstPath+UUID.randomUUID().toString()+".pdf";			//生成的附件的名称
		File dstFile = new File(filename);// 创建一个服务器上的目标路径文件对象
		File file = new File(newPdfPath);// 创建一个服务器上的目标路径文件对象
		
		String oldpath = filename.substring(0, filename.length()-4);
		String redChapter = oldpath.substring(0, oldpath.length())+"_red.pdf";
		pdfUtil.geneStampPdf(pdfNewPath, commentJson, redChapter, "1");
		for(int i=0;i<list.size();i++){
			if(file.exists()){
				FileUploadUtils.copy(file, dstFile);
				SendAttachments att = new SendAttachments();
				att.setDocguid(list.get(i)+"fj");
				att.setFileindex(0L);
				att.setFilename(wfProcess.getProcessTitle()+".pdf");// 设置文件名属性
				//att.setFiletype("true");// 设置文件类型(后缀名)的属性
				att.setFiletype("pdf");// 设置文件类型(后缀名)的属性
				att.setFilesize(file.length());// 设置文件大小的属性
				att.setFiletime(new Date(System.currentTimeMillis()));// 设置上传时间属性
				att.setLocalation(filename.replace(pdfRoot, ""));// 设置上传后在服务器上保存路径的属性
				att.setType("正文材料");// 设置上传附件所属类别
				//att.setTitle(wfProcess.getProcessTitle()+".true");// 设置上传附件标题
				att.setTitle(wfProcess.getProcessTitle()+".pdf");// 设置上传附件标题
				att.setEditer(emp.getEmployeeGuid()+";"+emp.getEmployeeName());
				attachmentService.addSendAtts(att);

			}
		}
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
	 * 获取json
	 * @param commentJson
	 */
	public void getJson(String commentJson) {
		
		
	}
	

	/**
	 * 判断是否有相同名字并且当前文件是ceb文件
	 * 描述：TODO 对此方法进行描述
	 * @return boolean
	 * 作者:季振华
	 * 创建时间:2017-5-18 上午10:17:55
	 */
	public boolean isCebAndHaveSaveName(List<SendAttachments> sattList,SendAttachments sendAttachments){
		boolean isCeb = false;
		String fileName = sendAttachments.getFilename();
		boolean isSatt_ceb = listIsHaveSameDocName(sattList,sendAttachments);
		if(CommonUtil.stringNotNULL(fileName)
				&&fileName.indexOf(".ceb")>-1){
			if(isSatt_ceb){
				isCeb = true;
			}
		}else{
			isCeb = true;
		}
		return isCeb;
	}
	
	/**
	 * list里面是否包含satt一样名字且是ceb的办件
	 * 描述：TODO 对此方法进行描述
	 * @param sattList
	 * @param satt
	 * @return boolean
	 * 作者:季振华
	 * 创建时间:2016-9-19 下午2:54:01
	 */
	public boolean listIsHaveSameDocName(List<SendAttachments> sattList,SendAttachments att){
		String all_sattName = "";
		boolean isSatt_ceb = true;
		for(SendAttachments satt:sattList){
			all_sattName += satt.getFilename() + ";";
		}
		if(CommonUtil.stringNotNULL(all_sattName)){
			all_sattName = all_sattName.substring(0,all_sattName.length()-1);
			String[] all_sattNames = all_sattName.split(";");
			int num = 0;
			String all_sameAttName = "";
			for(String s:all_sattNames){
				
				String fileName = att.getFilename();
				String s_pre = "";
				String attName_pre = "";
				if(CommonUtil.stringNotNULL(fileName)){
					attName_pre = fileName.substring(0,fileName.lastIndexOf("."));
				}
				s_pre = s.substring(0,s.lastIndexOf("."));
				if(s_pre.equalsIgnoreCase(attName_pre)){
					num++;
					all_sameAttName += s + ";";
				}
				
			}
			boolean isHaveSameCeb = false;
			if(CommonUtil.stringNotNULL(all_sameAttName)){
				all_sameAttName = all_sameAttName.substring(0,all_sameAttName.length()-1);
				String[] all_sameAttNames = all_sameAttName.split(";");
				for(String sameAttName:all_sameAttNames){
					String sameAttName_suffix = sameAttName.substring(sameAttName.lastIndexOf(".")+1,sameAttName.length());
					if("ceb".equalsIgnoreCase(sameAttName_suffix)){
						isHaveSameCeb = true;
					}
				}
			}
			if(num>1 && isHaveSameCeb){
				isSatt_ceb = false;//某个附件名字在list中超过两个
			}
		}

		return isSatt_ceb;
	}
	
	
	/**
	 * 判断是否有相同名字并且当前文件是ceb文件
	 * 描述：TODO 对此方法进行描述
	 * @return boolean
	 * 作者:季振华
	 * 创建时间:2017-5-18 上午10:17:55
	 */
	public boolean isCebAndHaveSaveName_Rec(List<RecShip> sattList,RecShip recShip){
		boolean isCeb = false;
		String fileName = recShip.getFileName();
		boolean isSatt_ceb = listIsHaveSameDocName_Rec(sattList,recShip);
		if(CommonUtil.stringNotNULL(fileName)
				&&fileName.indexOf(".ceb")>-1){
			if(isSatt_ceb){
				isCeb = true;
			}
		}else{
			isCeb = true;
		}
		return isCeb;
	}
	
	/**
	 * 前置机待收附件处理
	 * 描述：TODO 对此方法进行描述
	 * @param sattList
	 * @param att
	 * @return boolean
	 * 作者:季振华
	 * 创建时间:2017-5-17 下午5:25:36
	 */
	public boolean listIsHaveSameDocName_Rec(List<RecShip> sattList,RecShip att){
		String all_sattName = "";
		boolean isSatt_ceb = true;
		for(RecShip satt:sattList){
			all_sattName += satt.getFileName() + ";";
		}
		if(CommonUtil.stringNotNULL(all_sattName)){
			all_sattName = all_sattName.substring(0,all_sattName.length()-1);
			String[] all_sattNames = all_sattName.split(";");
			int num = 0;
			String all_sameAttName = "";
			for(String s:all_sattNames){
				
				String fileName = att.getFileName();
				String s_pre = "";
				String attName_pre = "";
				if(CommonUtil.stringNotNULL(fileName)){
					attName_pre = fileName.substring(0,fileName.lastIndexOf("."));
				}
				s_pre = s.substring(0,s.lastIndexOf("."));
				if(s_pre.equalsIgnoreCase(attName_pre)){
					num++;
					all_sameAttName += s + ";";
				}
				
			}
			boolean isHaveSameCeb = false;
			if(CommonUtil.stringNotNULL(all_sameAttName)){
				all_sameAttName = all_sameAttName.substring(0,all_sameAttName.length()-1);
				String[] all_sameAttNames = all_sameAttName.split(";");
				for(String sameAttName:all_sameAttNames){
					String sameAttName_suffix = sameAttName.substring(sameAttName.lastIndexOf(".")+1,sameAttName.length());
					if("ceb".equalsIgnoreCase(sameAttName_suffix)){
						isHaveSameCeb = true;
					}
				}
			}
			if(num>1 && isHaveSameCeb){
				isSatt_ceb = false;//某个附件名字在list中超过两个
			}
		}

		return isSatt_ceb;
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-4-23 下午1:50:34
	 * @throws Exception 
	 */
	public String fileToPdf(SendAttachments sat,AttachmentService attachmentService) throws Exception{
		FileUtils.byteArrayToFile(sat,attachmentService);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		//合并的文件集合
		String fileTyle = ""; //文件类型
		String path = "";
		fileTyle = sat.getFiletype().toLowerCase();	//小写          
		if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
			path = sat.getTopdfpath();
		}else{	
			if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {                                       		
				//word转换为pdf,并且update数据                                              		
				path = docToPdf(sat.getLocalation(),fileTyle, "", "");                                                		
			}else if (("ceb").equals(sat.getFiletype())) {                                                         		
				CebToPdf cp = new CebToPdf();                                                                                    		
				// 文件路径                                                                                                          		
				cp.cebToPdf(pdfRoot+sat.getLocalation());
				path = pdfRoot+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf";                 		
	                                                                                                                             
			}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){                                                              		
				path = pdfRoot+sat.getLocalation();                                                                   		
			}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){                                   		
				path = xlsToPdf(sat.getLocalation(), fileTyle, "", "");                                               		
			}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") ||                                   		
					fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")                                        		
					|| fileTyle.equalsIgnoreCase("tif")){                                                                        		
				path = imgToPdf(sat.getLocalation(), fileTyle);                                                         		
			}else if (("true").equals(sat.getFiletype())){                                                                       		
				path = pdfRoot+sat.getLocalation();                                                                   		
			}        
		}                                                                                                             		
		return path;
	}
	
	/**
	 * 季振华
	 * @param sattList
	 * @param sattExtList
	 * @param attachmentService
	 * @param list
	 * @param emp
	 */
	public void uploadSealedPdf(List<SendAttachments> sattList, List<SendAttachments> sattExtList, AttachmentService attachmentService, List<String> list, Employee emp){
		//合并附件列表
		if(sattList != null){
			sattList.addAll(sattExtList);
		}
		//附件列表上传
		if(sattList != null && sattList.size() > 0 && list != null && list.size() > 0){
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
			String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			for(int i = 0; i < sattList.size(); i++){
				SendAttachments attachment = sattList.get(i);
				FileUtils.byteArrayToFile(attachment,attachmentService);
				if(attachment != null){
					String fileName = attachment.getFilename().substring(0, attachment.getFilename().indexOf(".")) + ".pdf";
					String fileLoc = "";
					String fileType = "";
					if(attachment.getDocguid().endsWith(ATT_SUFFIX_NAME)){
					    fileType = ATT_SUFFIX_NAME;
					}else if(attachment.getDocguid().endsWith(ATT_FJSUFFIX_NAME)){
					    fileType = ATT_FJSUFFIX_NAME;
					}
					fileLoc = attachment.getTopdfpath();
					File file = new File(fileLoc);
					for(int j = 0; j < list.size(); j++){
						String newPdfPath = pdfRoot+dstPath + UuidGenerator.generate36UUID() + ".pdf";
						String docGuid = list.get(j) + fileType;
						File dstFile = new File(newPdfPath);
						FileUploadUtils.copy(file, dstFile);
						SendAttachments att = new SendAttachments();
						att.setDocguid(docGuid);
						att.setFileindex(0L);
						att.setFilename(fileName);// 设置文件名属性
						att.setFiletype("pdf");// 设置文件类型(后缀名)的属性
						att.setFilesize(file.length());// 设置文件大小的属性
						att.setFiletime(new Date(System.currentTimeMillis()));// 设置上传时间属性
						att.setLocalation(newPdfPath.replace(pdfRoot, ""));// 设置上传后在服务器上保存路径的属性
						att.setType("正文材料");// 设置上传附件所属类别
						att.setTitle(fileName);// 设置上传附件标题
						att.setEditer(emp.getEmployeeGuid()+";"+emp.getEmployeeName());
						att.setPagecount(attachment.getPagecount());
						att.setIsSeal(attachment.getIsSeal());
						att.setTopdfpath(newPdfPath);
						attachmentService.addSendAtts(att);
					}
				}
			}
		}
	}
	
	public String pptToPdf(String filePath, String type) throws Exception{
//		String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
//		String path = filePathOfSys + filePath;
		String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String path = filePathOfSys + filePath;
		if(!new File(path).exists()){
			path = pdfRoot + filePath;
		}
		// 文件路径及文件名，除去后缀
		String fileAndPath = "";
		if(type.equalsIgnoreCase("ppt")){
			fileAndPath = path.substring(0, path.length() - 4);
		}else if(type.equalsIgnoreCase("pptx")){
			fileAndPath = path.substring(0, path.length() - 5);
		}
		ExcelToPdf xlsToPdf = new ExcelToPdf();
		try {
			xlsToPdf.ppt2PDF(fileAndPath + "."+type, fileAndPath+".pdf");
			return fileAndPath+".pdf";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 
	 * 描述：将txt文件转为pdf
	 * @param filePath
	 * @param type
	 * @return
	 * @throws IOException String
	 * 作者:蔡亚军
	 * 创建时间:2014-9-2 下午4:50:38
	 */
	public String txtToPdf(String txtPath) throws IOException {
		String filePathOfSys = SystemParamConfigUtil.getParamValueByParam("filePath");
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String path = filePathOfSys + txtPath;
		if(!new File(path).exists()){
			path = pdfRoot +"/"+ txtPath;
		}
		// 文件路径及文件名，除去后缀
		String pdfPath = path.substring(0, path.length()-4);
		TxtToPdf txttoPdf = new TxtToPdf();
		String 	fileAndPath = path.substring(0, path.length() - 4);
		pdfPath = pdfPath + ".pdf";
		try {
			txttoPdf.txt2pdf(fileAndPath+".txt", pdfPath);
			return pdfPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 
	 * @Description:表单作为附件展示
	 * @author: xiep
	 * @time: 2017-9-6 下午3:11:13
	 * @param formPdfPath
	 * @param list
	 * @param title
	 * @param attachmentService
	 * @param emp
	 */
	public void uploadFormPdf(String formPdfPath, List<String> list, String title, AttachmentService attachmentService, Employee emp){
	    File file = new File(formPdfPath);
	    String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.UPLOAD_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
	    for(int i = 0; i < list.size(); i++){
		String newPdfPath = pdfRoot+dstPath + UuidGenerator.generate36UUID() + ".pdf";
		String docGuid = list.get(i) + ATT_FJSUFFIX_NAME;
		File dstFile = new File(newPdfPath);
		FileUploadUtils.copy(file, dstFile);
		SendAttachments att = new SendAttachments();
		att.setDocguid(docGuid);
		att.setFileindex(0L);
		att.setFilename(title + ".pdf");// 设置文件名属性
		att.setFiletype("pdf");// 设置文件类型(后缀名)的属性
		att.setFilesize(file.length());// 设置文件大小的属性
		att.setFiletime(new Date(System.currentTimeMillis()));// 设置上传时间属性
		att.setLocalation(newPdfPath.replace(pdfRoot, ""));// 设置上传后在服务器上保存路径的属性
		att.setType("正文材料");// 设置上传附件所属类别
		att.setTitle(title + ".pdf");// 设置上传附件标题
		att.setEditer(emp.getEmployeeGuid()+";"+emp.getEmployeeName());
		//att.setPagecount();
		att.setIsSeal("");
		att.setTopdfpath(newPdfPath);
		attachmentService.addSendAtts(att);
	}
	}
}
