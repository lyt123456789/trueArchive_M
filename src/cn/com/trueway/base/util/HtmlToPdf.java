package cn.com.trueway.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class HtmlToPdf {

	/**
	 * 描述：TODO 对此方法进行描述<br>
	 * @param args void
	 * 作者:陈静<br>
	 * 创建时间:2013-7-26 下午03:36:22
	 */
	public static void main(String[] args) {
       String htmlPath = "d:/cyd.html";
       String pdfPath = "d:/cyd.pdf";       
       HtmlToPdf htp = new HtmlToPdf();
       htp.htmlToPdf( htmlPath, pdfPath);
	}
	
	public void htmlToPdf(String htmlPath,String pdfPath){
		Runtime run = Runtime.getRuntime();  
        Process process = null;  
        String wkpath = SystemParamConfigUtil.getParamValueByParam("wkhtpPath");
        String operate = wkpath + " " + htmlPath + " " + pdfPath;
        File file = new File(pdfPath);
        if(file.exists()){
        	file.delete();
        }
        try {   
        	// 执行cmd命令  
            process = run.exec(operate);
//            ProcessBuilder builder = new ProcessBuilder();  
//            builder.command(operate);  
//            process = builder.start();
            //获取进程的标准输入流  
            final InputStream is1 = process.getInputStream();
            //获取进程的错误流  
            final InputStream is2 = process.getErrorStream();  
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流  
            new Thread() {  
               public void run() {  
                   try {  
                	   BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
                       String line1 = null;  
                       while ((line1 = br1.readLine()) != null) {  
                             if (line1 != null){}  
                         }  
                   } catch (IOException e) {  
                        e.printStackTrace();  
                   }finally{  
                        try {  
                          is1.close();  
                        } catch (IOException e) {  
                           e.printStackTrace();  
                       }  
                     }  
                   }  
                }.start();  
            new Thread() {   
                    public void  run() {   
                        try {   
                        	BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));   
                           String line2 = null ;   
                           while ((line2 = br2.readLine()) !=  null ) {   
                                if (line2 != null){
//                                	System.out.println(line2);
                                }  
                           }   
                         } catch (IOException e) {   
                               e.printStackTrace();  
                         }finally{  
                           try {  
                               is2.close();  
                           } catch (IOException e) {  
                               e.printStackTrace();  
                           }  
                         }  
                      }   
                    }.start();    
                    
            process.waitFor();
            process.destroy();   
            System.out.println("----------html转换成功----------");
        } catch (Exception e) { 
        	e.printStackTrace();
            System.out.println("----------html转换失败----------");  
        }finally{
        	try {
        		process.getErrorStream().close();  
				process.getInputStream().close();
				process.getOutputStream().close();  
			} catch (IOException e) {
				e.printStackTrace();
			}  
        	if(process!=null){
        		process.destroy();
        		process=null;
        	}
        }  
	}
}
