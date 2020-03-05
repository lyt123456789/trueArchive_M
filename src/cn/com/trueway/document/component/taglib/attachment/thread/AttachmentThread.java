package cn.com.trueway.document.component.taglib.attachment.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.util.DocToPdf;
import cn.com.trueway.workflow.set.util.PdfPage;
import cn.com.trueway.workflow.set.util.TaskEntity;
import cn.com.trueway.workflow.set.util.TaskPoolManager;
import cn.com.trueway.workflow.set.util.ToPdfUtil;

/** 
 * ClassName: AttachmentThread <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年5月30日 下午4:07:37 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public class AttachmentThread extends Thread{
	
	private AttachmentService attachmentService;
	
	private String sourceFilePath;
	
	private String fileType;
	
	private SendAttachments att;
	
	private File attsFile;
	      
	public AttachmentThread(AttachmentService attachmentService, String sourceFilePath, String fileType,
			SendAttachments att, File attsFile) {
		this.attachmentService = attachmentService;
		this.sourceFilePath = sourceFilePath;
		this.fileType = fileType;
		this.att = att;
		this.attsFile = attsFile;
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long endTime ;
		String destinPDFFilePath = "";
		if(fileType.equals("doc")){
			destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 4)+".pdf";
		}else if(fileType.equals("docx")){
			destinPDFFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 5)+".pdf";
		}
		if(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("jpeg") || fileType.equals("bmp")){
//			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			ToPdfUtil pdfUtil = new ToPdfUtil();
			String pdfPath = att.getLocalation();
//			pdfPath = pdfPath.substring(0, pdfPath.length()-fileType.length())+"pdf";
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
						partitionPdfFile(filepath, basePath+newFile,startPage, endPage);
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
   public void partitionPdfFile(String pdfFile,  
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
    	   if(document!=null){
    		   document.close();
    	   }
    	   if(copy!=null){
    		   copy.close();
    	   }
       }
    }  
}
