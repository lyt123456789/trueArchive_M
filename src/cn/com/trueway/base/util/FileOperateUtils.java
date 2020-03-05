package cn.com.trueway.base.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

/**
 * 
 * 描述：附件合并等操作类
 * 作者：蔡亚军
 * 创建时间：2016-3-1 上午9:53:11
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class FileOperateUtils {
	/**
	 * 
	 * 描述：将各种格式的文件进行合并
	 * @param list
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-1 上午9:56:54
	 */
	public String combineSendAtts(String instanceId, 
				AttachmentService attachmentService) throws Exception{
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String attSuffixName = SystemParamConfigUtil.getParamValueByParam("attSuffixName");	// 正文附件的后缀
		String attFjSuffixName = SystemParamConfigUtil.getParamValueByParam("attFjSuffixName");	// 附加附件的后缀
		List<SendAttachments> sattList = attachmentService.findAllSendAtts(instanceId + attSuffixName,null);
		List<SendAttachments> sattExtList = attachmentService.findAllSendAtts(instanceId + attFjSuffixName,"");
		sattList.addAll(sattExtList);
		//合并的文件集合
		String fileStrs = ""; 
		String fileTyle = ""; //文件类型
		ToPdfUtil pdfUtil = new ToPdfUtil();
		if (sattList.size() != 0 && !("").equals(sattList)) {
			for (SendAttachments sat : sattList) {
				FileUtils.byteArrayToFile(sat,attachmentService);
				//正文中存在同名ceb则不合入ceb
				boolean isSatt_ceb = pdfUtil.listIsHaveSameDocName(sattList,sat);
				
				fileTyle = sat.getFiletype().toLowerCase();	//小写
				if (("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")) {
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){
						String fp = sat.getTopdfpath();
						officeToPdf(sat.getTopdfpath(),sat.getLocalation(),fileTyle, instanceId, sat.getId());
						fileStrs += fp + ",";
					}else{	//word转换为pdf,并且update数据
						String path = pdfUtil.docToPdf(sat.getLocalation(),fileTyle,  instanceId, sat.getId()); 
						fileStrs += path+ ",";
						sat.setTopdfpath(path);
						attachmentService.updateSendAtt(sat);
					}
				} else if (("ceb").equals(sat.getFiletype()) && isSatt_ceb) {
					CebToPdf cp = new CebToPdf();
					// 文件路径
					cp.cebToPdf(pdfRoot + sat.getLocalation());
					fileStrs += pdfRoot+ sat.getLocalation().substring(0,sat.getLocalation().length() - 3) + "pdf,";
				}else if (("pdf").equalsIgnoreCase(sat.getFiletype())){
					fileStrs += pdfRoot+sat.getLocalation() + ",";
				}else if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					if(sat.getTopdfpath()!=null && !sat.getTopdfpath().equals("")){	
						officeToPdf(sat.getTopdfpath(),sat.getLocalation(),fileTyle, instanceId, sat.getId());
						fileStrs += sat.getTopdfpath()+ ",";
					}else{
						String path = pdfUtil.xlsToPdf(sat.getLocalation(), fileTyle, instanceId, sat.getId());
						fileStrs += path+ ",";
						sat.setTopdfpath(path);
						attachmentService.updateSendAtt(sat);
					}
				}else if(("jpg").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("png") || 
						fileTyle.equalsIgnoreCase("jpeg") || fileTyle.equalsIgnoreCase("bmp")
						|| fileTyle.equalsIgnoreCase("tif")){
					fileStrs+= pdfUtil.imgToPdf(sat.getLocalation(), fileTyle)+ ",";
				}else if(("true").equals(fileTyle)){
					fileStrs += pdfRoot+sat.getLocalation() + ",";
				}
			}
		}
		MergePdf mp = new MergePdf();
		fileStrs = removeLastComma(fileStrs);
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String saveMergePath =  pdfRoot +dstPath + instanceId+"_"+convertDateToString()+".pdf";
		String[] files = null;
		//存在附件内容
		if (fileStrs!=null && fileStrs.length() > 0) {
			files = new String[fileStrs.split(",").length];
			for (int i = 0; i < fileStrs.split(",").length; i++) {
				files[i] = fileStrs.split(",")[i];
			}
			 mp.combinePDFFiles(files, saveMergePath);
			return saveMergePath;
		}else{
			return "";
		}
	}
	
	
	public String convertDateToString(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		String time = sdf.format(date);
		return time;
	}
	
	
	/**
	 * 
	 * 描述：将office文件转换为pdf（word跟excel）
	 * @param allPath
	 * @param path
	 * @param fileTyle void
	 * 作者:蔡亚军
	 * 创建时间:2016-3-1 上午10:01:26
	 */
	public void officeToPdf(String allPath, String path,String fileTyle, String instanceId, String attId){
		ToPdfUtil pdfUtil = new ToPdfUtil();
		File file = new File(allPath);
		//判断附件是否转成了pdf,因有同步过来的数据,需检查
		if(!file.exists()){
			try {
				if(("doc").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("docx")){
					path = pdfUtil.docToPdf(path,fileTyle,  instanceId, attId);
				}
				if(("xlsx").equalsIgnoreCase(fileTyle) || fileTyle.equalsIgnoreCase("xls")){
					path = pdfUtil.xlsToPdf(path,fileTyle,  instanceId,  attId);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 
	 * 描述：简单操作归并类,将字符串最后的,号去除
	 * @param str
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-1 上午10:02:26
	 */
	public String removeLastComma(String str){
		if(str==null || str.equals("")){
			return "";
		}else{
			return str.substring(0, str.length() - 1);
		}
	}
	
	
	
}
