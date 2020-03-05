package cn.com.trueway.workflow.set.util;

import java.io.File;

import org.xvolks.jnative.exceptions.NativeException;
public class PdfToPngImageUtil {
	/**
	 * pdf转换成image图片
	 * @param pdfPath
	 */
	public String PDFToPNGImage(String pdfPath){
		System.out.println("pdf转换生成png");
		if(pdfPath != null && !pdfPath.equals("")){
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
				return pdfDir+"1.png";
			} catch (NativeException e) {
					e.printStackTrace();
			} catch (IllegalAccessException e) {
					e.printStackTrace();
			}
		}
		return "";
	}
}
