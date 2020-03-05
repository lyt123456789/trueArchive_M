package cn.com.trueway.workflow.core.action.thread;


import cn.com.trueway.base.util.MergePdf;

/**
 * 生成合并pdf
 * @author caiyj
 *
 */
public class GeneMergePdfThread extends Thread{
	
	/**
	 * 被合并的pdf
	 */
	private String pdfPath;		
	
	
	/**
	 * 需要被合并的附pdf
	 */
	private String oldpdfPath;
	
	
	public GeneMergePdfThread(String pdfPath,String oldpdfPath){
		this.pdfPath =pdfPath ;
		this.oldpdfPath = oldpdfPath;
	}
	
	
	@Override
	public synchronized void start() {
		super.start();
	}
	
	@Override
	public void run() {
		mergerPdf(pdfPath,oldpdfPath);
	}
	
	/**
	 * 合并pdf文件
	 * @param pdfPath
	 * @param oldpdfPath
	 */
	private void mergerPdf(String pdfPath, String oldpdfPath){
		MergePdf mp = new MergePdf();
		String mergePath = "";
		String fileStrs = pdfPath + "," + oldpdfPath;
		String[] files = null;
		if(oldpdfPath!=null && !oldpdfPath.equals("")){
			if (fileStrs!=null && fileStrs.length() > 0) {
				files = new String[fileStrs.split(",").length];
				for (int i = 0; i < fileStrs.split(",").length; i++) {
					files[i] = fileStrs.split(",")[i];
				}
				mergePath = pdfPath.substring(0, pdfPath.length() - 4) + "mergeNew" + ".pdf";
				mp.mergePdfFiles(files, mergePath);
				PDFToPNGImage(mergePath);
			}
		}
	}
	
	/**
	 * 将对应的pdf转换为相应的png文件(n页就转换为n张png文件)
	 * @param pdfPath
	 */
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
