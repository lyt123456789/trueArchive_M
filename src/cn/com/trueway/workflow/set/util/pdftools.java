package cn.com.trueway.workflow.set.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException; 
import com.itextpdf.text.Element;
import com.itextpdf.text.Font; 
import com.itextpdf.text.Image; 
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont; 
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfArray; 
import com.itextpdf.text.pdf.PdfContentByte; 
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.*;

public class pdftools extends JPanel implements ImageObserver{
	private static PdfReader reader;
	private static PdfStamper stamper;
	
	public pdftools(){
		
	}
	public void initDrawPDF(String input,String output)throws IOException, DocumentException{
		//创建一个pdf读入流  
        reader = new PdfReader(input);   
        //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.  
        stamper = new PdfStamper(reader,  
          new FileOutputStream(output)); 
	}
	/**
	 * 写入文本到PDF
	 * @param texts
	 * @throws IOException
	 * @throws DocumentException
	 * @throws JSONException 
	 */
	public void DrawTextBox(JSONArray texts)throws IOException, DocumentException, JSONException{
		int l=texts.length();
		int maxpage=reader.getNumberOfPages();
		JSONObject text;
		PdfContentByte over = null;
		PdfDictionary p = null;
		PdfObject po = null;
		PdfArray pa = null;
		
		//这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.  
        BaseFont bf = BaseFont.createFont("STSong-Light",   
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
        //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
        Font font = new Font(bf,10);
        font.setStyle(Font.BOLD);
        font.getBaseFont(); 
        
		for(int i=0;i<l;i++){
			text=texts.getJSONObject(i);
			
			if(text.getInt("page")>maxpage){
				continue;
			}
			
			//获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上. 
	        over = stamper.getOverContent(text.getInt("page"));
	        //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息. 
	        p = reader.getPageN(text.getInt("page")); 
	        //拿到mediaBox 里面放着该页pdf的大小信息. 
	        po =  p.get(new PdfName("MediaBox"));
	        pa = (PdfArray) po;
	        
	        ColumnText ct = new ColumnText(over);
	        Paragraph phrase = new Paragraph(new Chunk(text.getString("text"),font));
	        ct.setSimpleColumn(phrase, (float)text.getInt("x"), pa.getAsNumber(pa.size()-1).floatValue()-(float)text.getInt("y")-(float)text.getInt("h"), (float)text.getInt("w"), (float)text.getInt("h"), (float)text.getInt("size"), Element.ALIGN_LEFT);
	        ct.go();
		}
	}
	/**
	 * 写入文本到PDF
	 * @param texts
	 * @throws IOException
	 * @throws DocumentException
	 * @throws JSONException 
	 */
	public void DrawText(JSONArray texts)throws IOException, DocumentException, JSONException{
		int l=texts.length();
		int maxpage=reader.getNumberOfPages();
		JSONObject text;
		PdfContentByte over;
		PdfDictionary p;
		PdfObject po;
		PdfArray pa;
		
		//这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.  
        BaseFont bf = BaseFont.createFont("STSong-Light",   
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
        //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
        Font font = new Font(bf,10);
        font.setStyle(Font.BOLD);
        font.getBaseFont(); 
        
		for(int i=0;i<l;i++){
			text=texts.getJSONObject(i);
			
			if(text.getInt("page")>maxpage){
				continue;
			}
			
			//获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上. 
	        over = stamper.getOverContent(text.getInt("page"));
	        //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息. 
	        p = reader.getPageN(text.getInt("page")); 
	        //拿到mediaBox 里面放着该页pdf的大小信息. 
	        po =  p.get(new PdfName("MediaBox"));
	        pa = (PdfArray) po;
	        //开始写入文本 
	        over.beginText(); 
	        //设置字体和大小 
	        over.setFontAndSize(font.getBaseFont(), text.getInt("size"));  
	        //设置字体的输出位置 
	        over.setTextMatrix((float)text.getInt("x"), pa.getAsNumber(pa.size()-1).floatValue()-(float)text.getInt("y")-(float)(text.getInt("size")));  
	        //over.showTextAligned(PdfContentByte.ALIGN_LEFT, "This text is test", 300, 200, 0);
	        //要输出的text 
	        over.showText(text.getString("text"));  
	        over.endText();
		}
	}
	/**
	 * 写入图片到pdf
	 * @param imgs
	 * @throws IOException
	 * @throws DocumentException
	 * @throws JSONException 
	 */
	public void DrawImage(JSONArray imgs)throws IOException, DocumentException, JSONException{
		int l=imgs.length();
		int maxpage=reader.getNumberOfPages();
		JSONObject img;
		PdfContentByte over;
		PdfDictionary p;
		PdfObject po;
		PdfArray pa;
		
		for(int i=0;i<l;i++){
			img=imgs.getJSONObject(i);
			if(img.getInt("page")>maxpage){
				continue;
			}
			//获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上. 
	        over = stamper.getOverContent(img.getInt("page")); 
	        //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息. 
	        p = reader.getPageN(img.getInt("page")); 
	        //拿到mediaBox 里面放着该页pdf的大小信息. 
	        po =  p.get(new PdfName("MediaBox"));
	        pa = (PdfArray) po;
	        //创建一个image对象. 
	        String path = img.getString("path");
            Image image = Image.getInstance(path); 
            //设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值 
            //image.setAlignment(1);
            image.scaleAbsolute((float)595.32, (float)841.92);
            //image.setAbsolutePosition(0,pa.getAsNumber(pa.size()-1).floatValue()-image.getHeight());//0, 0, 841.92, 595.32
            image.setAbsolutePosition(0,0);
            over.addImage(image);
		}
	}
	/**
	 * 关闭输入流，输出文件
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void Output() throws DocumentException, IOException{
		stamper.close();
		reader.close();
	}
	/**
	 * 合并pdf
	 * @param pdfs
	 * @throws IOException
	 * @throws DocumentException
	 * @throws JSONException 
	 */
	public void MergerPdfs(JSONArray pdfs,String output)throws IOException, DocumentException, JSONException{
		Document __doc = new Document();
		PdfWriter __w = PdfWriter.getInstance(__doc, new FileOutputStream(output));
		__doc.open();
		int l=pdfs.length();
		for(int i=0;i<l;i++){
			PdfReader reader = new PdfReader(pdfs.getString(i));
			PdfContentByte cb = __w.getDirectContent();
			//__doc.newPage();
			int ll = reader.getNumberOfPages();
			for(int ii=1;ii<=ll;ii++){
				__doc.newPage();
				cb.addTemplate(__w.getImportedPage(reader, ii), 0, 0);
			}
			reader.close();
		}
		__doc.close();
		__w.close();
	}
	/**
	 * true to pdf
	 * @param imgs
	 * @throws IOException
	 */
	public void CoverTrue(JSONArray imgs) throws IOException{
		int width = 1024;
		int height = 1448;
		// 创建BufferedImage对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D
		Graphics2D g2d = image.createGraphics();
		// ----------  增加下面的代码使得背景透明  -----------------
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		// ----------  背景透明代码结束  -----------------
		int l=imgs.length();
		for(int i=0;i<l;i++){
			g2d.drawImage(CreatePng(i), 0, 0, width, height,this);
		}
		//释放对象
		g2d.dispose();
		// 保存文件    
		ImageIO.write(image, "png", new File("c/test.png"));
	}
	public BufferedImage CreatePng(int i) throws IOException{
		int width = 1024;
		int height = 1448;
		// 创建BufferedImage对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D
		Graphics2D g2d = image.createGraphics();
		// ----------  增加下面的代码使得背景透明  -----------------
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		// ----------  背景透明代码结束  -----------------
		// 画图
		//g2d.setColor(new Color(255,0,0));
		//g2d.setStroke(new BasicStroke(1));
		//g2d.drawArc(10, 10, 100, 100, 30, 30);
		GradientPaint gradient = new GradientPaint(0,0,Color.WHITE,  
                150,150,Color.YELLOW.darker());  
        g2d.setPaint(gradient);  
        //paint part of the component
        g2d.fillOval(i*200, 0, 200, 200); 
		//释放对象
		g2d.dispose();
		// 保存文件    
		return image;
		//ImageIO.write(image, "png", new File("/Users/chenjiawei/Desktop/img/test.png"));
		
	}
	
}
