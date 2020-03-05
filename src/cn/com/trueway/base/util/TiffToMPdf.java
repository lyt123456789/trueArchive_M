package cn.com.trueway.base.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

import com.asprise.util.tiff.TIFFReader;

public class TiffToMPdf {
	public static File tifftopdf(File tifFile,File pdfFile){
		String tiftopdfpath=SystemParamConfigUtil.getParamValueByParam("tiftopdfpath");
				
		File tiffile=new File(tiftopdfpath);
		if(!tiffile.exists()){
			tiffile.mkdir();
		}
		String tifPath = tifFile.getAbsolutePath();
		String tifname=tifFile.getName().substring(0, tifFile.getName().lastIndexOf("."));
		int pages = 0;
		TIFFReader reader=null;
		try {
			reader = new TIFFReader(tifFile);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		pages=reader.countPages();
		if (pages > 0) {
			File[] filearray=new File[pages];
			try{
				//第一种方法，若出现异常，用catch中的第二种方法
				for(int i=0;i<pages; i++) { 
					RenderedImage image = reader.getPage(i); // extract page  
					System.out.println("first:"+(i+1)+"/"+pages);
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
					ImageIO.write(tag, "jpg", new File(tiftopdfpath+tifname+i+ ".JPG"));
					filearray[i]=new File(tiftopdfpath+tifname+i+ ".JPG");
					image=null;
					tag=null;
				}
				ImgPdfUtils.imgMerageToPdf(filearray, pdfFile);
				for(File f:filearray){
					if(f.isFile()&&f.exists()){
						f.delete();
					}
				}
			} catch (Exception e){
				//第二种方法
				File tiffFile = new File(tifPath);
				ImageReader tiffReader = null;
				ImageInputStream input = null;
				try {
					input = new FileImageInputStream(tiffFile);
					IIORegistry iioreg = IIORegistry.getDefaultInstance();
					iioreg.registerApplicationClasspathSpis();
					ImageIO.setUseCache(false);
					ImageReaderSpi irs = new com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi();
					tiffReader = irs.createReaderInstance();
					tiffReader.setInput(input);
					for (int i = 0; i < pages; i++) {
						System.out.println("second:"+(i+1)+"/"+pages);
						BufferedImage bimage = tiffReader.read(i);
						Image image = bimage.getScaledInstance(bimage.getWidth()/2,
								bimage.getHeight()/2, Image.SCALE_SMOOTH);
						BufferedImage tag = new BufferedImage(bimage.getWidth()/2,
								bimage.getHeight()/2, BufferedImage.TYPE_INT_BGR);
						Graphics g = tag.getGraphics();
						
						g.drawImage(image, 0, 0, null);
						g.dispose();
						ImageIO.write(tag, "JPG", new File(tiftopdfpath+tifname+i+ ".JPG"));
						filearray[i]=new File(tiftopdfpath+tifname+i+ ".JPG");
						image=null;
						tag=null;
						bimage=null;
					}
					input.close();
					tiffReader.dispose();
					ImgPdfUtils.imgMerageToPdf(filearray, pdfFile);
					for(File f:filearray){
						if(f.isFile()&&f.exists()){
							f.delete();
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return pdfFile;
	}
	/*public static void main(String[] args) throws Exception {
		String tiftopdfpath=SystemParamConfigUtil.getParamValueByParam("tiftopdfpath");
		File tiffile=new File(tiftopdfpath);
		if(!tiffile.exists()){
			tiffile.mkdir();
		}
		File tif=new File("F:\\市政简报第十三期.tif");
		File pdf=new File("F:\\abc.pdf");
		tifftopdf(tif,pdf);
	}*/
}
