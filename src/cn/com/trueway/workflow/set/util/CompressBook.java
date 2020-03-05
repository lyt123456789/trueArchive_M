package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.zip.CnZipOutputStream;


public class CompressBook {

	public CompressBook() {}

    /**//*
    * inputFileName 输入一个文件夹
    * zipFileName 输出一个压缩文件夹
    */
    public void zip(String inputFileName) throws Exception {
        String zipFileName = inputFileName+".zip"; //打包后文件名字
        zip(zipFileName, new File(inputFileName));
    }

    private void zip(String zipFileName, File inputFile) throws Exception {
    	CnZipOutputStream out = new CnZipOutputStream(new FileOutputStream(zipFileName),"GBK");
        zip(out, inputFile, "");
        out.close();
    }

    private void zip(CnZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
        	   if(fl[i].getName().indexOf(".png") == -1){
        		   zip(out, fl[i], base + fl[i].getName());
        	   }
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),Charset.forName("UTF-8")));
           FileInputStream in = new FileInputStream(f);
           int b;
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }

    public static void main(String [] temp){
        CompressBook book = new CompressBook();
        try {
           book.zip("E://测试1");//你要压缩的文件夹
        }catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}
