package cn.com.trueway.workflow.set.util;

import java.io.IOException;
import java.sql.Timestamp;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


public class JsonToPng {


	
	public void JsonToPng(String json,String  pdfPath) throws NativeException, IllegalAccessException
	{
		String jsonToPng = SystemParamConfigUtil.getParamValueByParam("JsonToPng");
		String[] params = new String[3];
		params[0] = jsonToPng;
		params[1] = json;
		params[2] = pdfPath;
		Runtime run = Runtime.getRuntime();  
	    Process process = null;  
	        try { 
				 Timestamp first = new Timestamp(System.currentTimeMillis());
				 process = run.exec(params);
		         WatchThread errorGobbler = new WatchThread(process.getErrorStream(), "ERROR");
				 WatchThread outputGobbler = new WatchThread(process.getInputStream(), "OUTPUT");
				 errorGobbler.start();
				 outputGobbler.start();
				 process.waitFor();
				 Timestamp second = new Timestamp(System.currentTimeMillis());
				 Long time = second.getTime()-first.getTime();
				 System.out.println(pdfPath+"pdfPath 合并意见 used time ="+time+"ms");
				
		       } catch (Exception e) {  
		    	   e.getStackTrace();
		           System.out.println("Error my exec ");  
		       } finally{
	        	try {
	        		process.getErrorStream().close();  
					process.getInputStream().close();
					process.getOutputStream().close();  
					process.destroy();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        	if(process!=null){
	        		process.destroy();
	        		process=null;
	        	}
	        }  
	}
	
	
	public static void main(String[] args) {
		JsonToPng test = new JsonToPng();
		String json = "E:/1/newInfo_1399532493153merge.txt";
		String pdfPath = "E:/1";
		try {
			test.JsonToPng(json, pdfPath);
		} catch (NativeException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
