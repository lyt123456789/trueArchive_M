package cn.com.trueway.workflow.set.util;

import java.awt.FontMetrics;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JLabel;

import sun.misc.BASE64Encoder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class WordAndWaterMarkTool {
	
	/**
	     * 
	     * 【功能描述：添加图片和文字水印】 【功能详细描述：功能详细描述】
	     * @param srcFile 待加水印文件
	     * @param destFile 加水印后存放地址
	     * @param text 加水印的文本内容
	     * @param textWidth 文字横坐标
	     * @param textHeight 文字纵坐标
	     * @return 
	     * @throws Exception
	     */
	    public static void addWaterMark(String srcFile, String text) throws Exception {
	    	Rectangle pageRect = null;  
	        File src = new File(srcFile);
	        File tempFile = new File(srcFile+"water.pdf");
	    	//输入内容base64编码
	    	String markContent = new String((new BASE64Encoder()).encodeBuffer(text.getBytes())).replaceAll("\r|\n", "")+"         ";
	        // 待加水印的文件
	        PdfReader reader = new PdfReader(srcFile);
	        // 加完水印的文件
	        FileOutputStream fos = new FileOutputStream(tempFile);
	        PdfStamper stamper = new PdfStamper(reader, fos);
	        int total = reader.getNumberOfPages() + 1;
	        PdfContentByte content;
	        // 设置字体
	        BaseFont font = BaseFont.createFont();
	        
	        // 循环对每页插入水印
	        for (int i = 1; i < total; i++)
	        {
//	        	 pageRect = reader.getPageSizeWithRotation(i); 
	        	pageRect = stamper.getReader().getPageSizeWithRotation(i);
	        	 float pageH = pageRect.getHeight();  
	             float pageW = pageRect.getWidth();  
	        	//float pageWidth = reader.getPageSize(i).getWidth();
	        	//float pageHeight = reader.getPageSize(i).getHeight();
	            // 水印的起始
	            //content = stamper.getUnderContent(i);
	            content =  stamper.getOverContent(i);
	            // 开始
	            content.beginText();
	            // 设置颜色 默认为蓝色
	            //content.setColorFill(BaseColor.BLUE);
	            content.setColorFill(new BaseColor(176,224,230));
	            // 设置字体及字号
	            content.setFontAndSize(font, 9);
	            //透明度
	            PdfGState gs = new PdfGState();
	            gs.setFillOpacity(0.3f);// 设置透明度为0.8
	            content.setGState(gs);
	            // 设置起始位置
	            // content.setTextMatrix(400, 880);
	            //content.setTextMatrix(textWidth, textHeight);
	            // 开始写入水印
	            String writeText = markContent+markContent+markContent+markContent+markContent+markContent+markContent;
	           
	            int a = 0;
				for (int height = 20-500; height < pageH; height = height+95) {
					a = a + 29;
					content.showTextAligned(Element.ALIGN_LEFT, writeText, 0-a, height, 45);//从height = 20开始，不断显示markcontent。角度8
				}
	            //content.showTextAligned(Element.ALIGN_LEFT, text, 100, 100, 45);
	            content.endText();

	        }
	        stamper.close();
	        reader.close();
	        fos.flush();
	        fos.close();
	        if(src.exists()){
	        	src.delete();
	        }
	        tempFile.renameTo(src);
	    }
	    
	    
	    public static void addNumber(String srcFile, String tempFile, String text) throws Exception{
	    	File src = new File(srcFile);
		    File tFile = new File(tempFile);
	        // 待加水印的文件
	        PdfReader reader = new PdfReader(srcFile);
	        // 加完水印的文件
	        FileOutputStream fos = new FileOutputStream(tFile);
	        PdfStamper stamper = new PdfStamper(reader, fos);
	        int total = reader.getNumberOfPages() + 1;
	        PdfContentByte content;
	        // 设置字体
	        BaseFont font = BaseFont.createFont();
	        // 循环对每页插入水印
	        for (int i = 1; i < total; i++)
	        {
	        	//float pageWidth = reader.getPageSize(i).getWidth();
	        	float pageHeight = reader.getPageSize(i).getHeight();
	            // 水印的起始
	            content = stamper.getUnderContent(i);
	            // 开始
	            content.beginText();
	            // 设置颜色 默认为蓝色
	            //content.setColorFill(BaseColor.BLUE);
	            content.setColorFill(BaseColor.RED);
	            // 设置字体及字号
	            content.setFontAndSize(font, 14);
	            //透明度
	            //PdfGState gs = new PdfGState();
	            //gs.setFillOpacity(0.3f);// 设置透明度为0.8
	            //content.setGState(gs);
	            // 设置起始位置
	            // content.setTextMatrix(400, 880);
	            // 开始写入水印
	            content.showTextAligned(Element.ALIGN_LEFT, text, 20, pageHeight-20, 0);
	            content.endText();

	        }
	        stamper.close();
	        reader.close();
	        fos.flush();
	        fos.close();
	        /*if(src.exists()){
	        	src.delete();
	        }*/
	        //tempFile.renameTo(src);
	    }
	    
	    /*public static  void setWaterMark2(String destinPathUrl,String input,String loginname) {
			Timestamp first = new Timestamp(System.currentTimeMillis());
			File file = new File(destinPathUrl);
			if(file.exists()){
				file.delete();
				System.out.println("删除原有文件");
			}
			String watermark = loginname;
			SimpleDateFormat datesend = new SimpleDateFormat(" MM-dd");
			watermark +=  datesend.format(new java.util.Date());
			String markContent = new String((new BASE64Encoder()).encodeBuffer(watermark.getBytes()));
			String waterMark = "";
			for (int j = 0; j < 7; j++) {
				waterMark += "     " + markContent ;//仿制水印样式
			}
			try{
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destinPathUrl)));
				PdfReader reader = new PdfReader(input);
				PdfStamper stamper = new PdfStamper(reader, bos);
				int total = reader.getNumberOfPages()+1;
				PdfContentByte content;
				BaseFont base = BaseFont.createFont("C:/Windows/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
				for (int i = 1; i < total; i++) {
					content =  stamper.getOverContent(i);//文字设置在原文前面，防止有些pdf自带背景色，遮住水印
					content.beginText();
					content.setColorFill(BaseColor.RED);//设置文字颜色 很浅，效仿pc端
					content.setFontAndSize(base, 11);//设置字体和大小 默认宋11
					content.setTextMatrix(100,100);//好像没什么用
					for (int height = 20; height < 900; height = height+50) {
						content.showTextAligned(com.lowagie.text.Element.ALIGN_CENTER, waterMark, 0, height, 8);//从height = 20开始，不断显示markcontent。角度8
					}
					content.endText();
				}
				stamper.close();
				Timestamp second = new Timestamp(System.currentTimeMillis());
				Long time = second.getDateTime() - first.getDateTime();
				System.out.println("生成水印文件，耗时" + time + "ms，url："+destinPathUrl);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("生成水印文件失败!");
			}
		}*/
	    
	    public static void main(String[] args) throws Exception{
	    	addWaterMark("D:\\tt.pdf", "liming 2018-08-20");
	    	//addNumber("C:\\Users\\zj\\Desktop\\2.pdf", "C:\\Users\\zj\\Desktop\\4.pdf", "No.000001");
	    }
	    
	    
}

