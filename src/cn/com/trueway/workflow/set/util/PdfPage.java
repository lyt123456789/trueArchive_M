package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.TrueToPdf;
import cn.com.trueway.base.util.Utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfPage {
	
	
	public static int getPdfPage(String pdfPath){
		if(StringUtils.isNotBlank(pdfPath)){
			File file = new File(pdfPath);
			if(!file.exists()){
				return 0;
			}
			String oldPath="";
			if(pdfPath.endsWith(".true")){
				String[] str = new TrueToPdf().trueToPdf(pdfPath);
				if(str!=null){
					oldPath = str[0];
				}
			}
			PdfReader reader = null;
			int n =0;
			try {
				if(Utils.isNotNullOrEmpty(oldPath)){
					reader = new PdfReader(oldPath);
				}else{
					reader = new PdfReader(pdfPath);
				}
				n = reader.getNumberOfPages();
			}catch (BadPasswordException e) {
				e.printStackTrace();
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}finally{
				if(null != reader){
					reader.close();
				}
			}
			if(Utils.isNotNullOrEmpty(oldPath)){
				new File(oldPath).delete();
			}
			return n;
		}
		return 0;
	}
	
	public static void main(String[] args) throws IOException, DocumentException {
	
	    // 待加水印的文件  
	    PdfReader reader = new PdfReader("D:/workflow/workflow_njxxzx/pdf/2018/08/01/2FE4D86A-97B6-48F9-BF45-11F53E86A9ECflexWithComt.pdf");  
	    // 加完水印的文件  
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(  
	            "D:/workflow/workflow_njxxzx/pdf/2018/08/01/2FE4D86A-97B6-48F9-BF45-11F53E86A9ECWater.pdf"));  
	      
	    int total = reader.getNumberOfPages() + 1;  
	
	    PdfContentByte content;  
	    // 设置字体  
	   /* BaseFont base = BaseFont.createFont("STSong-Light", "UTF-8",  
	            BaseFont.EMBEDDED);*/
	    // 水印文字  
	    String waterText = "水印文字！";  
	    int j = waterText.length(); // 文字长度  
	    char c = 0;  
	    int high = 0;// 高度  
	    // 循环对每页插入水印  
	    for (int i = 1; i < total; i++) {  
	        // 水印的起始  
	        high = 500;  
	        content = stamper.getUnderContent(i);  
	        // 开始  
	        content.beginText(); 
	        
	        // 设置颜色  
	        content.setColorFill(BaseColor.GRAY); 
	        // 设置字体及字号  
	        content.setFontAndSize(null, 18);  
	        // 设置起始位置  
	        content.setTextMatrix(400, 780);  
	        // 开始写入水印  
	        for (int k = 0; k < j; k++) {  
	            content.setTextRise(14);  
	            c = waterText.charAt(k);  
	            // 将char转成字符串  
	            content.showText(c + "");  
	            high -= 5;  
	        }  
	        content.endText();  

    	}  
    	stamper.close();  
	}
	
	/**
	 * pdf文件添加 图片水印
	 *
	 * @param sourceFilePath    源文件路径
	 * @param fileWaterMarkPath 水印生成文件路径
	 * @return
	 * @throws Exception
	 */
	public static void setWaterMarkForPDF(String sourceFilePath, String fileWaterMarkPath) throws Exception {
	    PdfReader reader = new PdfReader(sourceFilePath);
	    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileWaterMarkPath));

	    int total = reader.getNumberOfPages() + 1;
	    PdfContentByte content;
	    Image img = Image.getInstance("D:/water.png");
	    img.setAbsolutePosition(30, 100);
	    for (int i = 1; i < total; i++) {
	        content = stamp.getOverContent(i);// 在内容上方加水印
	        content.addImage(img);
	    }
	    stamp.close();
	    reader.close();
	}
}
