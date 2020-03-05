package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class GenePdfUtilArchive {

	private static GenePdfUtilArchive instance;

	private GenePdfUtilArchive() {
	}

	public static synchronized GenePdfUtilArchive getInstance(){
		if(instance == null){
			instance = new GenePdfUtilArchive();
		}
		return instance;
	}
	public String writeFile(String pdfPath,String json,int type){
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_process");	
		pdfRoot = pdfRoot + "/";
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		if(!pdfPath.startsWith(pdfRoot+dstPath)){
			dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_TRUE_FILE_PATH);
		}
		String jsonPath = "";
		if(pdfPath.endsWith("true")){
			if(type ==0){
				jsonPath = pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/"),pdfPath.length()-5)+".txt";
			}else{
				jsonPath = pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/"),pdfPath.length()-5)+"sign.txt";
			}
		}else{
			if(type ==0){
				jsonPath = pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/"),pdfPath.length()-4)+".txt";
			}else{
				jsonPath = pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/"),pdfPath.length()-4)+"sign.txt";
			}
		}

		File file = new File(jsonPath);
		FileOutputStream fos = null; 
		try { 
			fos = new FileOutputStream(file);
			byte[] data = json.getBytes("UTF-8");
			fos.write(data);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return jsonPath;
	}
	
//	public String writeFileLog(String json){
//		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
//		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_ZIP_TXT_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
//		String jsonPath = pdfRoot+dstPath+UuidGenerator.generate36UUID()+".txt";
//
//		File file = new File(jsonPath);
//		FileOutputStream fos = null; 
//		try { 
//			fos = new FileOutputStream(file);
//			byte[] data = json.getBytes("UTF-8");
//			fos.write(data);
//			fos.flush();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				fos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//		return jsonPath;
//	}

	
	
	/**
	 * 
	 * 描述：将章合并到pdf中
	 * @param folderPath
	 * @param json
	 * @param jsonToPdf
	 * @param stampType	  stampType=1,表示使用红色的章 ；  0:拖密打印的黑章
	 * @return boolean
	 * 作者:蔡亚军
	 * 创建时间:2015-7-8 上午10:42:34
	 */
	public boolean jsonToPdf(String folderPath,String json,String jsonToPdf, String stampType,String signPath){
		String[] params = null;
		if(stampType!=null && !stampType.equals("")){
			params = new String[4];
			params[0] = jsonToPdf;
			params[1] = json;
			params[2] = folderPath;
			params[3] = stampType;
		}else{
			
		
			// 如果文件不为空
			File file = new File (signPath);
			if(file!=null &&file.exists()&&file.length()>0){
				params = new String[4];
				params[3] = signPath;
			}else{
				params = new String[3];
			}
			params[0] = jsonToPdf;
			params[1] = json;
			params[2] = folderPath;
		}
		int success =  0;
		Runtime run = Runtime.getRuntime();
		Process process = null;
		try {
			Timestamp first = new Timestamp(System.currentTimeMillis());
			process = run.exec(params);
			WatchThread errorGobbler = new WatchThread(
					process.getErrorStream(), "ERROR");
			WatchThread outputGobbler = new WatchThread(process.getInputStream(), "OUTPUT");
			errorGobbler.start();
			outputGobbler.start();
			success = process.waitFor();
			Timestamp second = new Timestamp(System.currentTimeMillis());
			Long time = second.getTime() - first.getTime();
			System.out.println("意见转pdf  =" + time + "ms");
			process.destroy();
		} catch (Exception e) {
			e.getStackTrace();
			process.destroy();
			System.out.println("Error my exec ");
			return false;
		} finally {
			try {
				process.getErrorStream().close();
				process.getInputStream().close();
				process.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		
		return true;
	}
	
	public void genePdf(String pdfPath,String json, String newPdfPath,String signs){
		String jsonPath = writeFile(pdfPath,json,0);
		String signPath = writeFile(pdfPath,signs,1);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_process");	
		pdfRoot = pdfRoot + "/";
		
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		if(!pdfPath.startsWith(pdfRoot+dstPath)){
			dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_TRUE_FILE_PATH);
		}
		
		String folderPath  = "";
		if(pdfPath.endsWith("true")){
			folderPath =  pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.length()-5)+"/";
		}else{
			folderPath =  pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.length()-4)+"/";
		}

		File folder = new File(folderPath);
		String jsonPng=	SystemParamConfigUtil.getParamValueByParam("JsonToPdf");
		if(folder.exists()){
			File[] files = folder.listFiles();
			// 文件夹存在 没有文件 则生成  否则 取原来的文件
			if(files != null && files.length > 0){
				for(int i = 0 ; i < files.length ; i++){
					files[i].delete();
				}
				jsonToPdf(folderPath,jsonPath,jsonPng,"",signPath);
			}
		}else{
			folder.mkdir();
			jsonToPdf(folderPath,jsonPath,jsonPng,"",signPath);
		}
		File file1 = new File(pdfPath);
		Integer num = 0;
		while(!file1.exists()&&num<4){
			try {
				Thread.sleep(3000);
				num++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		PdfStamper stamp =  null;
		try{
			TrueToPdf trueToPdf = new TrueToPdf();
			String[] result = trueToPdf.trueToPdf(pdfPath);
			PdfReader reader = new PdfReader(result[0]);			//输入的文件
			int test1Length = reader.getNumberOfPages();
			stamp = new PdfStamper(reader, new FileOutputStream(newPdfPath));		//输出的文件
			/**从现有的别的pdf合并过来**/
			PdfContentByte under;
			PdfReader readerOther;
			for (int i = 1; i <= test1Length; i++) {
				int index = i-1;
				String commentPdf = folderPath+index+".pdf";		//需要合并到主表单的pdf页面
				File file = new File(commentPdf);
				if(file.exists()){
					readerOther = new PdfReader(commentPdf);
					under = stamp.getOverContent(i);
					under.addTemplate(stamp.getImportedPage(readerOther, 1), 1, 0, 0, 1, 0, 0);
					Thread.sleep(1000);
				}
			}
			stamp.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public void genePdf(String pdfPath,String json, String newPdfPath,int startPage , int middleSize,String signs){
		String jsonPath = writeFile(pdfPath,json,0);
		String signPath =writeFile(pdfPath,signs,1);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		if(!pdfPath.startsWith(pdfRoot+dstPath)){
			dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_TRUE_FILE_PATH);
		}
		
		String folderPath  = "";
		if(pdfPath.endsWith("true")){
			folderPath =  pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.length()-5)+"/";
		}else{
			folderPath =  pdfRoot+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/")+1,pdfPath.length()-4)+"/";
		}

		File folder = new File(folderPath);
		String jsonPng=	SystemParamConfigUtil.getParamValueByParam("JsonToPdf");
		if(folder.exists()){
			File[] files = folder.listFiles();
			// 文件夹存在 没有文件 则生成  否则 取原来的文件
			if(files != null && files.length > 0){
				for(int i = 0 ; i < files.length ; i++){
					files[i].delete();
				}
				jsonToPdf(folderPath,jsonPath,jsonPng,"",signPath);
			}
		}else{
			folder.mkdir();
			jsonToPdf(folderPath,jsonPath,jsonPng,"",signPath);
		}
		PdfStamper stamp =  null;
		try{
			PdfReader reader = new PdfReader(pdfPath);			//输入的文件
			int test1Length = reader.getNumberOfPages();
			stamp = new PdfStamper(reader, new FileOutputStream(newPdfPath));		//输出的文件
			/**从现有的别的pdf合并过来**/
			PdfContentByte under;
			PdfReader readerOther;
			for (int i = 1; i <= test1Length; i++) {
				int index = i-1;
				if(i>startPage){
					index = index+middleSize-startPage;
				}
				String commentPdf = folderPath+index+".pdf";		//需要合并到主表单的pdf页面
				File file = new File(commentPdf);
				if(file.exists()){
					readerOther = new PdfReader(commentPdf);
					under = stamp.getOverContent(i);
					under.addTemplate(stamp.getImportedPage(readerOther, 1), 1, 0, 0, 1, 0, 0);
					Thread.sleep(1000);
				}
			}
			stamp.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void geneStampPdf(String pdfPath,String json, String newPdfPath, String stampType){
		String jsonPath = writeFile(pdfPath,json,0);
		//调用dll 生成  意见的图片
		JsonToPng test = new JsonToPng();
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String folderPath =  pdfRoot+"/"+dstPath+pdfPath.substring(pdfPath.lastIndexOf("/"),pdfPath.length()-4)+"/";

		File folder = new File(folderPath);
		String jsonPng=	SystemParamConfigUtil.getParamValueByParam("StampToPDF");

		if(folder.exists()){
			File[] files = folder.listFiles();
			// 文件夹存在 没有文件 则生成  否则 取原来的文件
			if(files != null && files.length > 0){
				for(int i = 0 ; i < files.length ; i++){
					files[i].delete();
				}
				jsonToPdf(folderPath,jsonPath,jsonPng,stampType,"");
			}
		}else{
			folder.mkdir();
			jsonToPdf(folderPath,jsonPath,jsonPng,stampType,"");
		}
		PdfStamper stamp =  null;
		try{
			PdfReader reader = new PdfReader(pdfPath);			//输入的文件
			int test1Length = reader.getNumberOfPages();
			stamp = new PdfStamper(reader, new FileOutputStream(newPdfPath));		//输出的文件
			/**从现有的别的pdf合并过来**/
			PdfContentByte under;
			PdfReader readerOther;
			for (int i = 1; i <= test1Length; i++) {
				int index = i-1;
				String commentPdf = folderPath+index+".pdf";		//需要合并到主表单的pdf页面
				File file = new File(commentPdf);
				if(file.exists()){
					readerOther = new PdfReader(commentPdf);
					under = stamp.getOverContent(i);
					under.addTemplate(stamp.getImportedPage(readerOther, 1), 1, 0, 0, 1, 0, 0);
					Thread.sleep(1000);
				}
			}
			stamp.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}