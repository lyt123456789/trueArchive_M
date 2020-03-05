/**
 * 文件名称:TIFReaderUtil.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-19 上午10:37:30
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.asprise.util.tiff.TIFFReader;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * 描述：TIF文件的相关操作
 * 作者：WangXF<br>
 * 创建时间：2011-12-19 上午10:37:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class TIFReaderUtil {
	
	/**
	 * 
	 * 描述：TIF文件转成WORD文件
	 *
	 * @param tifFile 
	 * @param docFile void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-19 上午10:42:52
	 * @throws Exception 
	 */
	public static File createDocFile(File tifFile,File docFile) throws Exception{
		//创建word文档    
		Document document = new Document(PageSize.A4);
		// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
		RtfWriter2.getInstance(document, new FileOutputStream(docFile)); 
		document.open();
        // 获取图片流
		//File file=new File("F:\\tif2word\\专题纪要25号.tif");
		long byte1=0;
		TIFFReader reader = new TIFFReader(tifFile);
		for(int i=0,j=reader.countPages(); i<j; i++) { 
			RenderedImage image = reader.getPage(i); // extract page  
			//获取图片流 
			int oWidth =image.getWidth();
			int oHeight = image.getHeight();
			int wy=105;
			BufferedImage tag = new BufferedImage(oWidth, oHeight,BufferedImage.TYPE_INT_RGB);
			Graphics g = ((Image) image).getGraphics();
			for (int j1 = image.getMinY(); j1 < oHeight; j1++) {
				for (int j2 = image.getMinX(); j2 < oWidth; j2++) {
					int rgb = ((BufferedImage) image).getRGB(j2, j1);
					if(j1<wy){
						rgb = 0xffffff;
					}
					tag.setRGB(j2, j1, rgb);
				}
		    }
			g.drawImage((Image) image, 0, 0, null);
			g.dispose();
			ByteArrayOutputStream imgbyte = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", imgbyte);
			// 添加图片 
			com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(imgbyte.toByteArray()); 
			img.setAbsolutePosition(0, 0);//
			img.scaleAbsoluteWidth(document.getPageSize().getWidth()-80);
			img.scaleAbsoluteHeight(document.getPageSize().getHeight()-120);     
			document.add(img);
			byte1+=imgbyte.size();
		}
		//关闭输入
		document.close();
		return docFile;
	}
}
