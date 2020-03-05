package cn.com.trueway.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsHistory;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.util.DocToPdf;
import cn.com.trueway.workflow.set.util.TaskEntity;
import cn.com.trueway.workflow.set.util.TaskPoolManager;

public class FileUtils {
	
	private static Logger logger=Logger.getLogger(FileUtils.class);
	
	/**
	 * 
	 * 描述：文件转成二进制流
	 * @param fileName
	 * @return byte[]
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-6-30 下午4:44:33
	 */
	public static BLOB readFileToByteArray(String fileName,BLOB blob) throws Exception{ 
		File file = new File(fileName);
		
		int i = 0;
		byte[] bytes  = null;
		while(!file.exists()){
			try {
				i++;
				Thread.sleep(60);
				if(i > 10){
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(i <= 10){
			InputStream is = null;
			OutputStream os = null;
			try {
			    os = blob.setBinaryStream(0L);
				is = new FileInputStream(file);
				long length = file.length();
		        if (length > Integer.MAX_VALUE) {
		        	System.out.println("File is too large");
		        }
		        bytes = new byte[1024*100];
		        Integer iRead ;
		        Integer offset = Integer.valueOf(0);
		        iRead = is.read(bytes, offset, 1024*100);
				while (iRead.compareTo(-1)!=0) {
					os.write(bytes, 0, iRead);
					 iRead = is.read(bytes, offset, 1024*100);
				}
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
			}
		}
		return blob;
	} 
	
	public static byte[] readFileToByteArray(String fileName){
		File file = new File(fileName);
		InputStream is = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(file);
			long length = file.length();
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	        	System.out.println("File is too large");
	        }
	        bytes = new byte[(int)length];
	        int offset = 0;
	        int numRead = 0;
			while (offset < bytes.length
				       && (numRead = is.read(bytes, offset, bytes.length-offset)) >= 0) {
				    offset += numRead;
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bytes;
	}
	
	/**
	 * 
	 * 描述：二进制流 转成 文件
	 * @param datas void
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-6-30 下午4:46:10
	 */
	public static void byteArrayToFile(BLOB blob,String filePath){
		try {
			if(blob != null ){
				// 创建文件夹
				File file = new File(filePath);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				
				InputStream in = blob.getBinaryStream();// 建立输出流
				FileOutputStream fos = new FileOutputStream(filePath);
				int len = (int)blob.length();
				byte[] buffer = new byte[len]; // 建立缓冲区
				while((len = in.read(buffer))!= -1){
					fos.write(buffer);
				}
				in.close();
				fos.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(String filePath){
		File file = new File(filePath);
		
		try {
		/*	FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);*/
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String data = null;
			
			while((data = br.readLine())!= null) {
				sb.append(data);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String readDoc(String filePath){
		String value = "";
		FileInputStream in;
		try {
			in = new FileInputStream(new File(filePath));
			WordExtractor ex = new WordExtractor(in);
			
			/*HWPFDocument hdt = new HWPFDocument(in);
			//读取word文本内容
			Range range = hdt.getRange();
			value = range.text();*/
			value = ex.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	public static String readDocx(String filePath){
		String value = "";
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
			POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
			//HWPFDocument hdt = new HWPFDocument(in);
			/*//读取word文本内容
			Range range = hdt.getRange();*/
			value = ex.getText();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 描述：将文件中的 formid 替换成新的
	 * @param files
	 * @param formIds void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-9-23 上午10:46:49
	 */
	public static void replaceFile(File[] files , Map<String ,String> formIds){
		if(files != null && files.length>0){
			for(int i = 0; i< files.length ; i++){
				FileInputStream fileinputstream = null;
				OutputStreamWriter osw = null;
				try {
					fileinputstream = new FileInputStream(files[i].getAbsolutePath());
					int length = fileinputstream.available();
					byte bytes[] = new byte[length];
					fileinputstream.read(bytes);
					fileinputstream.close();
					String templateContent = "";
					templateContent = new String(bytes, "UTF-8");
					
					// 替换
					String old_formId ="";
					String new_formId ="";
					for(String key : formIds.keySet()){
						old_formId = key;
						new_formId = formIds.get(old_formId);
						templateContent = templateContent.replace("<li class=\"tw-nav-item\" data-select=\""+old_formId+"\">", "<li class=\"tw-nav-item\" data-select=\""+new_formId+"\">"); 
					}
					// 重新生成
					FileOutputStream fileoutputstream = new FileOutputStream(files[i].getAbsolutePath());// 建立文件输出流
					osw = new OutputStreamWriter(fileoutputstream,"UTF-8");
					osw.write(templateContent);
				} catch (FileNotFoundException e) {
					logger.error("将文件中的formid替换成新的formid失败："+e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("将文件中的formid替换成新的formid失败："+e.getMessage());
					e.printStackTrace();
				}finally {
		            if (osw != null) {
		                try {
		                	osw.close();
		                } catch (IOException e1) {
		                	logger.error("将文件中的formid替换成新的formid失败："+e1.getCause().getMessage());
		                	e1.printStackTrace();
		                }
		            }
		        }
			}
			

		}
		
	}
	
	public static List<String> readLine(String filePath) {
		File file = new File(filePath);

		List<String> list = new ArrayList<String>();
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "UTF-8"));
				StringBuffer sb = new StringBuffer();
				String data = null;
				while ((data = br.readLine()) != null) {
					sb.append(data);
					list.add(data);
				}
			} catch (FileNotFoundException e) {
				logger.error("文件"+filePath+"转成字符列表失败："+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("文件"+filePath+"转成字符列表失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		return list;

	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param url1
	 * @param url2
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-3-30 下午6:28:20
	 */
	public String mergeUrl(String url1, String url2){
		Integer i1 = url1.lastIndexOf("/");
		Integer i2 = url1.lastIndexOf("\\");
		
		Integer i3 = url2.indexOf("/");
		Integer i4 = url2.indexOf("\\");
		if(i1.equals(url1.length()-1) || i2.equals(url1.length()-1)){
			if(i3.equals(0) || i4.equals(0)){
				url1 = url1.substring(0,url1.length()-1);
			}
		}else if(!i1.equals(url1.length()-1) && !i2.equals(url1.length()-1)){
			if(!i3.equals(0) && !i4.equals(0)){
				url1 = url1 + "/";
			}
		}
		
		return url1 + url2;
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param files
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-5-4 上午10:39:52
	 */
	public String copyFile(List<File> files,String instanceId){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		File folder = new File(basePath + "/zip/"+instanceId);
		if(!folder.exists()){
			folder.mkdirs();
		}
		for (File file : files) {
			try{
				File newFile = new File(basePath + "/zip/"+instanceId+"/"+file.getName());
				FileUploadUtils.copy(file, newFile);// 完成上传文件，就是将本地文件复制到服务器上
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return basePath + "/zip/"+instanceId;
	}

	/**
	 * 描述：TODO 对此方法进行描述
	 * FileUtils
	 * void
	 * 作者:蒋烽
	 * 创建时间:2017 上午10:30:24
	 */
	public static void byteArrayToFile(Blob blob,String filePath){
		try {
			if(blob != null ){
				// 创建文件夹
				File file = new File(filePath);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				
				InputStream in = blob.getBinaryStream();// 建立输出流
				FileOutputStream fos = new FileOutputStream(filePath);
				int len = (int)blob.length();
				byte[] buffer = new byte[len]; // 建立缓冲区
				while((len = in.read(buffer))!= -1){
					fos.write(buffer);
				}
				in.close();
				fos.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * FileUtils
	 * void
	 * 作者:蒋烽
	 * 创建时间:2017 上午10:08:14
	 */
	public static void byteArrayToFile(SendAttachments sendAttachments,AttachmentService attachmentService){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String localation = sendAttachments.getLocalation();
		if(sendAttachments.getFiletype().equals("doc") || sendAttachments.getFiletype().equals("docx")){
			String toPdfPath = sendAttachments.getTopdfpath();
			if(StringUtils.isNotBlank(toPdfPath)){
				System.out.println("-----进入byteArrayToFile方法断点1-------");
				File pdfFile = new File(toPdfPath);
				if(!pdfFile.exists()){
					System.out.println("-----进入byteArrayToFile方法断点2-------");
					List<SendAttachmentsHistory> hisList = attachmentService.findAllSendAttHistory(sendAttachments.getDocguid(), sendAttachments.getId());
					if(null != hisList && hisList.size()>0){
						Blob data = hisList.get(0).getAttflow();
						if(null != data){
							FileUtils.byteArrayToFile(data, basePath+localation);
						}
					}else{
						Blob data = sendAttachments.getData();
						if(null != data){
							FileUtils.byteArrayToFile(data, basePath+localation);
						}
					}
					if(new File(basePath+localation).exists()){
						TaskEntity msg = null;
				        Map<String, String> params = null;
				        params = new HashMap<String, String>();
				        params.put("sourceFilePath", basePath + localation);
				        params.put("destinPDFFilePath", sendAttachments.getTopdfpath());
				        msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
						TaskPoolManager.newInstance().addTask(msg);
					}
				}
			}else{
				System.out.println("-----进入byteArrayToFile方法断点3-------");
				File localationFile = new File(basePath+localation);
				if(!localationFile.exists()){
					List<SendAttachmentsHistory> hisList = attachmentService.findAllSendAttHistory(sendAttachments.getDocguid(), sendAttachments.getId());
					if(null != hisList && hisList.size()>0){
						Blob data = hisList.get(0).getAttflow();
						if(null != data){
							FileUtils.byteArrayToFile(data, basePath+localation);
						}
					}else{
						Blob data = sendAttachments.getData();
						if(null != data){
							FileUtils.byteArrayToFile(data, basePath+localation);
						}
					}
				}
				if(localationFile.exists()){
					TaskEntity msg = null;
					Map<String, String> params = null;
					params = new HashMap<String, String>();
					params.put("sourceFilePath", basePath + localation);
					params.put("destinPDFFilePath", sendAttachments.getTopdfpath());
					msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
					TaskPoolManager.newInstance().addTask(msg);
				}
			}
		}else{
			System.out.println("-----进入byteArrayToFile方法断点4-------");
			String toPdfPath = sendAttachments.getTopdfpath();
			if(StringUtils.isNotBlank(toPdfPath)){
				File pdfFile = new File(toPdfPath);
				if(!pdfFile.exists()){
					Blob pdfData = sendAttachments.getPdfData();
					if(null != pdfData){
						byteArrayToFile(pdfData, sendAttachments.getTopdfpath());
					}
				}
			}
			
			
			File file = new File(basePath + localation);
			if(!file.exists()){
				Blob data = sendAttachments.getData();
				if(null != data){
					byteArrayToFile(data, basePath + localation);
				}
			}
		}
	}
	
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * FileUtils
	 * void
	 * 作者:蒋烽
	 * 创建时间:2017 上午10:08:14
	 */
	public static void byteArrayToFile(SendAttachments sendAttachments,AttachmentDao attachmentDao){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String localation = sendAttachments.getLocalation();
		if(sendAttachments.getFiletype().equals("doc") || sendAttachments.getFiletype().equals("docx")){
			String toPdfPath = sendAttachments.getTopdfpath();
			if(StringUtils.isNotBlank(toPdfPath)){
				File pdfFile = new File(toPdfPath);
				if(!pdfFile.exists()){
					List<SendAttachmentsHistory> hisList = attachmentDao.findAllSendAttHistory(sendAttachments.getDocguid(), sendAttachments.getId());
					if(null != hisList && hisList.size()>0){
						Blob data = hisList.get(0).getAttflow();
						if(null != data){
							FileUtils.byteArrayToFile(data, basePath+localation);
						}
					}else{
						FileUtils.byteArrayToFile(sendAttachments.getData(), basePath+localation);
					}
					if(new File(basePath+localation).exists()){
						TaskEntity msg = null;
				        Map<String, String> params = null;
				        params = new HashMap<String, String>();
				        params.put("sourceFilePath", basePath + localation);
				        params.put("destinPDFFilePath", sendAttachments.getTopdfpath());
				        msg = new TaskEntity(DocToPdf.class,"docToPDF",params);
						TaskPoolManager.newInstance().addTask(msg);
					}
				}
			}
		}else{
			String toPdfPath = sendAttachments.getTopdfpath();
			if(StringUtils.isNotBlank(toPdfPath)){
				File pdfFile = new File(toPdfPath);
				if(!pdfFile.exists()){
					Blob pdfData = sendAttachments.getPdfData();
					if(null != pdfData){
						byteArrayToFile(pdfData, sendAttachments.getTopdfpath());
					}
				}
			}
			
			
			File file = new File(basePath + localation);
			if(!file.exists()){
				Blob data = sendAttachments.getData();
				if(null != data){
					byteArrayToFile(data, basePath + localation);
				}
			}
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param sendAttachments void
	 * 作者:蒋烽
	 * 创建时间:2018-3-23 上午11:42:55
	 */
	public static void byteArrayToFile(CutPages cut){
		String toPdfPath = cut.getFilepath();
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		if(StringUtils.isNotBlank(toPdfPath)){
			File pdfFile = new File(basePath+toPdfPath);
			if(!pdfFile.exists()){
				Blob pdfData = cut.getPdfData();
				if(null != pdfData){
					byteArrayToFile(pdfData, basePath+toPdfPath);
				}
			}
		}
	}
	
	/** 
	 * byteArrayToFile:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param history 
	 * @since JDK 1.6 
	 */
	public static void byteArrayToFile(SendAttachmentsHistory history){
		String location = history.getLocalation();
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		if(StringUtils.isNotBlank(location)){
			File file = new File(basePath+location);
			if(!file.exists()){
				Blob fileData = history.getAttflow();
				if(null != fileData){
					byteArrayToFile(fileData, basePath+location);
				}
			}
		}
	}
}
