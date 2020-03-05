package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xvolks.jnative.exceptions.NativeException;

public class GenePdfUtilThread implements Runnable{
	
	private String  pdfPath;	
	
	private String json;	//实例id
	 
	private String newPdfPath;	//需要的合并pdf
	
	public GenePdfUtilThread(String pdfPath, String json, String newPdfPath) {
		this.pdfPath = pdfPath;
		this.json = json;
		this.newPdfPath = newPdfPath;
	}

	
	@Override
	public void run() {
		genePdf(pdfPath,json,newPdfPath);
		PDFToPNGImage(newPdfPath);
	}

	public String writeFile(String pdfPath,String json){
		String jsonPath = pdfPath.substring(0, pdfPath.length()-4)+".txt";
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

	public void genePdf(String pdfPath,String json, String newPdfPath){
		String jsonPath = writeFile(pdfPath,json);
		//调用dll 生成  意见的图片
		JsonToPng test = new JsonToPng();
		String jsonPngPath = pdfPath.substring(0, pdfPath.length()-4);
		if(!(new File(jsonPngPath).isDirectory()))
		{
			new File(jsonPngPath).mkdir();
		}
		try {
			test.JsonToPng(jsonPath, jsonPngPath);
		} catch (NativeException e1) {
		} catch (IllegalAccessException e1) {
		}
		JSONArray imgs=new JSONArray();
		try {
			//_pdftools.initDrawPDF(pdfPath,newPdfPath);
			// 遍历文件夹 jsonPngPath
			File file = new File(jsonPngPath);
			
			if(file.isDirectory())
			{
				String[] files  = file.list();
				for(int i = 0 ; i <files.length ; i++){
					String pageNum = files[i].substring(0, files[i].indexOf("."));
					JSONObject img1=new JSONObject();
					img1.put("page", Integer.valueOf(pageNum)+1);
					img1.put("path", jsonPngPath+"/"+ files[i]);
					imgs.put(img1);
				}
			}
			
			  TaskEntity msg = null;
		        Map<String, String> params = null;
		        params = new HashMap<String, String>();
		        params.put("pdfs", pdfPath);
		        params.put("imgs", imgs.toString());
		        params.put("output", newPdfPath);
		        msg = new TaskEntity(PdfUtil.class,"sendSMS",params);
				TaskPoolManager.newInstance().addTask(msg);
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
	
	public void PDFToPNGImage(String pdfPath){/*
		System.out.println("pdf转换生成png");
		if(pdfPath != null && !pdfPath.equals("")){
			//String pdfDir = pdfPath.substring(0,pdfPath.lastIndexOf("/")+1);
			String pdfDir = pdfPath.substring(0,pdfPath.lastIndexOf("."))+"/";
			// 创建目录
			try
			{
				if(!(new File(pdfDir).isDirectory()))
				{
					new File(pdfDir).mkdir();
				}else{
					// 清空 文件夹
					String[] files =	new File(pdfDir).list();
					if(files != null){
						for(int i = 0; i <files.length ; i++){
							new File(pdfDir+files[i]).delete();
						}
					}
				}
			}
			catch(SecurityException e)
			{
			        e.printStackTrace();
			}
			// 设置 pdf 页数
			//int imageCount  =1;
			ExecDll exceDll = new ExecDll();
			try {
				exceDll.PDFToPNGImage(pdfPath, pdfDir);
			} catch (NativeException e) {
					e.printStackTrace();
			} catch (IllegalAccessException e) {
					e.printStackTrace();
			}
		}
	*/}
	
}
