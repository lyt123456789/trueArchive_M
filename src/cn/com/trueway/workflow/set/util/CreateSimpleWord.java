package cn.com.trueway.workflow.set.util;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
public class CreateSimpleWord {
	public static void createDocContext(String bt, String content, String file)
			throws DocumentException, IOException {
	 // 设置纸张大小
	 Document document = new Document(PageSize.A4);
	 // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
	 RtfWriter2.getInstance(document, new FileOutputStream(file));
	 document.open();
	 // 设置中文字体
	 BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
	 "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	 // 标题字体风格
	 Font titleFont = new Font(bfChinese, 12, Font.BOLD);
	 // 正文字体风格
	 Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
	 Paragraph title = new Paragraph(bt);
	 // 设置标题格式对齐方式
	 title.setAlignment(Element.ALIGN_CENTER);
	 title.setFont(titleFont);
	 document.add(title);
	 
	 String[] data = content.split(";;");
	 String contextString = "";
	 for(String context :data){
		 if(context!=null && context.trim().length()>0){
			 contextString += context + " \n";// 换行
		 }
	 }
	 Paragraph context = new Paragraph(contextString);
	 // 正文格式左对齐
	 context.setAlignment(Element.ALIGN_LEFT);
	 context.setFont(contextFont);
	 // 离上一段落（标题）空的行数
	 context.setSpacingBefore(5);
	 // 设置第一行空的列数
	 context.setFirstLineIndent(20);
	 document.add(context);
	 document.close();
	 } 
}
