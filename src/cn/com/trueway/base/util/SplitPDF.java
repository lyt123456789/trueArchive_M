package cn.com.trueway.base.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class SplitPDF {
	
	public static void splitPDF(InputStream inputStream, OutputStream outputStream, int fromPage, int toPage) {
	    Document document = new Document();
	    try {
	        PdfReader inputPDF = new PdfReader(inputStream);

	        int totalPages = inputPDF.getNumberOfPages();

	        //make fromPage equals to toPage if it is greater
	        if(fromPage > toPage ) {
	            fromPage = toPage;
	        }
	        if(toPage > totalPages) {
	            toPage = totalPages;
	        }

	        // Create a writer for the outputstream
	        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

	        document.open();
	        PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
	        PdfImportedPage page;

	        while(fromPage <= toPage) {
	            document.newPage();
	            page = writer.getImportedPage(inputPDF, fromPage);
	            cb.addTemplate(page, 0, 0);
	            fromPage++;
	        }
	        outputStream.flush();
	        document.close();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (document.isOpen())
	            document.close();
	        try {
	            if (outputStream != null)
	                outputStream.close();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	    }
	}

	public static void main(String[] args) {
	    try {
	    	PdfReader inputPDF = new PdfReader(new FileInputStream("d:\\input.pdf"));
	    	int totalPages = inputPDF.getNumberOfPages();
	    	for (int i = 1; i <= totalPages; i++) {
	    		SplitPDF.splitPDF(new FileInputStream("d:\\input.pdf"), new FileOutputStream("d:\\output"+i+".pdf"), i, i);
	    		System.out.println("----------分割第"+i+"页成功--------------");
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
