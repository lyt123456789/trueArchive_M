package cn.com.trueway.base.util;

import java.awt.image.BufferedImage;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.RandomAccessFile;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.TreeMap;  
  
import com.lowagie.text.Document;  
import com.lowagie.text.Image;  
import com.lowagie.text.Rectangle;  
import com.lowagie.text.pdf.PdfCopy;  
import com.lowagie.text.pdf.PdfImportedPage;  
import com.lowagie.text.pdf.PdfReader;  
import com.lowagie.text.pdf.PdfWriter;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
import com.sun.pdfview.PDFFile;  
import com.sun.pdfview.PDFPage;  
  

@SuppressWarnings("unused")  
public class ImgPdfUtils {  
    public static void main(String[] args) throws Exception {  
          
    }  
      
    private static void listOrder() {  
          
        File[] listFiles = new File("F:\\temp\\Project\\���\\dfdsfds\\���蹫���Ҵ���_img").listFiles();  
        TreeMap<Integer, File> tree = new TreeMap<Integer, File>();  
        for(File f : listFiles)  
        {  
            tree.put(Integer.parseInt(f.getName().replaceAll(".jpg$", "")), f);  
        }  
        for(Entry<Integer, File> eif : tree.entrySet())  
        {  
            System.out.println(eif.getKey()+"="+eif.getValue().toString());  
        }  
    }  
    public static boolean imgMerageToPdf(File[] list, File file)throws Exception {  
        Map<Integer,File> mif = new TreeMap<Integer,File>();  
        for(int i=0;i<list.length;i++) {
        	 mif.put(i,list[i]);
             ByteArrayOutputStream baos = new ByteArrayOutputStream(2048*3);  
             InputStream is = new FileInputStream(mif.get(i));  
             for(int len;(len=is.read())!=-1;)  
                 baos.write(len);  
               
             baos.flush();  
             Image image = Image.getInstance(baos.toByteArray());  
             float width = image.getWidth();  
             float height = image.getHeight();  
             baos.close();  
               
             Document document = new Document(new Rectangle(width,height));  
             PdfWriter pdfWr = PdfWriter.getInstance(document, new FileOutputStream(file));  
             document.open();  
             for(Entry<Integer,File> eif : mif.entrySet())  
             {  
                 baos = new ByteArrayOutputStream(2048*3);  
                 is = new FileInputStream(eif.getValue());  
                 for(int len;(len=is.read())!=-1;)  
                     baos.write(len);  
                 baos.flush();  
                   
                 image = Image.getInstance(baos.toByteArray());  
                 Image.getInstance(baos.toByteArray());  
                 image.setAbsolutePosition(0.0f, 0.0f);  
                   
                 document.add(image);  
                 document.newPage();  
                 baos.close();  
             }  
               
             document.close();  
             pdfWr.close();  
               
        }
        
        return true;  
    }  
    /** 
     *  
     * @param source Դ�ļ� 
     * @param target Ŀ���ļ� 
     * @param x ��ȡԴ�ļ��еĵڼ�ҳ 
     */  
    private static void pdfToJpg(String source,String target,int x) throws Exception {  
        //�������ж�ȡ��������д�루��ѡ�����������ļ�����R��ʾ����ֻ�Ƿ���ģʽ  
        RandomAccessFile rea = new RandomAccessFile(new File(source), "r");  
  
        //������ȡ���ڴ��У�Ȼ��ӳ��һ��PDF����  
        FileChannel channel = rea.getChannel();  
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());  
        PDFFile pdfFile = new PDFFile(buf);  
        PDFPage page = pdfFile.getPage(x);    
  
        // get the width and height for the doc at the default zoom    
        java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, (int) page.getBBox()    
                .getWidth(), (int) page.getBBox().getHeight());    
  
        // generate the image    
        java.awt.Image img = page.getImage(rect.width, rect.height, // width &  
                rect, // clip rect  
                null, // null for the ImageObserver  
                true, // fill background with white  
                true // block until drawing is done  
                );    
  
        BufferedImage tag = new BufferedImage(rect.width, rect.height,    
                BufferedImage.TYPE_INT_RGB);    
          
        tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height,    
                null);    
        FileOutputStream out = new FileOutputStream(target); // ������ļ���    
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
        encoder.encode(tag); // JPEG����    
        out.close();          
    }  
    /** 
     * @param source  ԴPDF�ļ�·�� 
     * @param target  ����PDF�ļ�·�� 
     * @param pageNum  ��ȡPDF�е�pageNumҳ 
     * @throws Exception   
     */  
    private static void pdfExtraction(String source,String target,int pageNum) throws Exception{  
        //1������PDF��ȡ����  
        PdfReader pr = new PdfReader(source);  
        System.out.println("this document "+pr.getNumberOfPages()+" page");  
          
        //2������pageҳתΪ��ȡ������document����  
        Document doc = new Document(pr.getPageSize(pageNum));  
          
        //3��ͨ��PdfCopyת�䵥���洢  
        PdfCopy copy = new PdfCopy(doc, new FileOutputStream(new File(target)));  
        doc.open();  
        doc.newPage();  
          
        //4����ȡ��1ҳ��װ�ص�document�С�  
        PdfImportedPage page = copy.getImportedPage(pr,pageNum);  
        copy.addPage(page);   
          
        //5���ͷ���Դ  
        copy.close();  
        doc.close();  
        pr.close();  
    }  
    /** 
     * @param pdfFile ԴPDF�ļ� 
     * @param imgFile   ͼƬ�ļ� 
     */  
    private static void jpgToPdf(File pdfFile,File imgFile)  throws Exception {  
        //�ļ�תimg  
        InputStream is = new FileInputStream(pdfFile);  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        for(int i;(i=is.read())!=-1;)  
        {  
            baos.write(i);  
        }  
        baos.flush();  
          
        //ȡ��ͼ��Ŀ�͸ߡ�  
        Image img = Image.getInstance(baos.toByteArray());  
        float width = img.getWidth();  
        float height = img.getHeight();  
        img.setAbsolutePosition(0.0F, 0.0F);//ȡ��ƫ��  
        System.out.println("width = "+width+"\theight"+height);  
          
        //imgתpdf  
        Document doc = new Document(new Rectangle(width,height));  
        PdfWriter pw = PdfWriter.getInstance(doc,new FileOutputStream(imgFile));  
        doc.open();  
        doc.add(img);  
          
        //�ͷ���Դ  
        System.out.println(doc.newPage());  
        pw.flush();  
        baos.close();  
        doc.close();  
        pw.close();  
    }  
      
}  