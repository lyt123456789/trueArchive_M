package cn.com.trueway.base.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TxtToPdf {
	public void txt2pdf(String txtPath, String pdfPath)
			throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);

		InputStream is = new FileInputStream(txtPath);
		//读取文本内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

		/** 新建一个字体,iText的方法
		 * STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
		 * UniGB-UCS2-H   是编码，在iTextAsian.jar 中以cmap为后缀
		 * H 代表文字版式是 横版， 相应的 V 代表 竖版
		 */
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", false);

		Font fontChinese = new Font(bfChinese, 30, Font.NORMAL, Color.BLACK); 
		
//		 打开文档，将要写入内容
	      document.open();
	     String line=reader.readLine();
	     while(line!=null){
	      Paragraph pg = new Paragraph(line,fontChinese);
	      document.add(pg);
	      line=reader.readLine();
	     }
	     document.close();
	     reader.close();
	     is.close();
	}
	
	
	public static void main(String[] args) {
		TxtToPdf pt = new TxtToPdf();
		// 单转
		try {
			pt.txt2pdf("d:/办文单.txt","d:/办文单.pdf");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 批量
		//pt.t();
	}
}
