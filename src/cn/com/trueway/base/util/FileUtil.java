package cn.com.trueway.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
	  /**
     * 读取某个文件夹下的所有文件
     */
    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
    	try {
            File file = new File(filepath);
            if (file.isDirectory()) {
            	System.out.println("文件夹");
            	String[] filelist = file.list();
            	String basePath = "F:\\database\\6";
                for (int i = 0; i < filelist.length; i++) {
                	String name = filelist[i].substring(0, filelist[i].lastIndexOf("."));
                	File readfile = new File(filepath + "\\" + filelist[i]);
                	File newFile = new File(basePath + "\\random" + name + ".dat");
                	FileUploadUtils.copy(readfile, newFile);
                }
            }
        } catch (Exception e) {
        	System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }
    
    public static void main(String[] args) {
    	try {
			FileUtil.readfile("F:\\temp\\6");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
