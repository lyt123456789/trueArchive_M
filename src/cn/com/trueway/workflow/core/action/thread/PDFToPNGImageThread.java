package cn.com.trueway.workflow.core.action.thread;

import java.io.File;

import org.xvolks.jnative.exceptions.NativeException;

import cn.com.trueway.workflow.set.util.ExecDll;

public class PDFToPNGImageThread extends Thread {

	/**
	 * 被合并的pdf
	 */
	private String pdfPath;
	
	public PDFToPNGImageThread(String pdfPath) {
		super();
		this.pdfPath = pdfPath;
	}

	@Override
	public void run() {
		PDFToPNGImage(pdfPath);
	}

	@Override
	public synchronized void start() {
		super.start();
	}	
	
	public void PDFToPNGImage(String pdfPath){/*
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
