package cn.com.trueway.document.component.taglib.attachment.util;


import org.apache.commons.codec.binary.Base64;
 

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File2String {
	public static void main(String[] args) {
		 
        String filePath = "d:\\test.pdf";
        String str = file2String(filePath);
        System.out.println("文件转字符串结果：start");
        System.out.println(str);
        System.out.println("文件转字符串结果：end");
 
    }
 
    public static String file2String(String path) {
        // TODO Auto-generated method stub
        File file = new File(path);
        FileInputStream fis = null;
        StringBuffer content = new StringBuffer();
        try {
            fis = new FileInputStream(file);
            int length = 2 * 1024 * 1024;
            byte[] byteAttr = new byte[length];
            int byteLength = -1;
 
            while ((byteLength = fis.read(byteAttr, 0, byteAttr.length)) != -1) {
 
                String encode = "";
                if (byteLength != byteAttr.length) {
                    byte[] temp = new byte[byteLength];
                    System.arraycopy(byteAttr, 0, temp, 0, byteLength);
                    //使用BASE64转译
                    Base64 base64 = new Base64();
                    //encode = base64.encodeToString(temp);
                    encode = new BASE64Encoder().encode(temp);
                    content.append(encode);
                } else {
                    Base64 base64 = new Base64();
                    //encode = base64.encodeToString(byteAttr);
                    encode = new BASE64Encoder().encode(byteAttr);
                    content.append(encode);
                }
            }
 
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
