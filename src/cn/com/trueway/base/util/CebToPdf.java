package cn.com.trueway.base.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class CebToPdf {

	public void cebToPdf(String cebPath){
		Runtime run = Runtime.getRuntime();  
        Process process = null;  
        String cebhtpPath = SystemParamConfigUtil.getParamValueByParam("cebhtpPath");
        String operate = cebhtpPath + " " + cebPath;
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
            System.out.println("----------ceb转换成功----------");
        } catch (Exception e) { 
        	e.printStackTrace();
            System.out.println("----------ceb转换失败----------");  
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
	
	public static void main(String[] args) throws Exception {
		 String cebath = " d:\\2013.ceb";
		 CebToPdf ctp = new CebToPdf();
		 ctp.cebToPdf(cebath);
	}
}
