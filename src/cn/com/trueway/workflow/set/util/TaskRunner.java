package cn.com.trueway.workflow.set.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;
/**
 * 执行任务的线程
 * @author jiang.li
 * @date 2013-12-17 13:17
 */
public class TaskRunner implements Runnable{
    /**任务实体定义**/
    private TaskEntity task;
    public TaskEntity getTask() {
        return task;
    }
    
    public TaskRunner(TaskEntity task) {
        this.task = task;
    }
    
    /**
     * 线程开始执行
     */
    public void run() {
        try {
            /**利用Java反射机制实现任务调度**/
        	synchronized (task) {
        		 Class<?> className = task.getTaskClass();
                 String classMethod = task.getTaskMethod();
                 Method method = className.getMethod(classMethod, Map.class);
                 try{
                	 if(processIsExist("WerFault.exe")){
                		 killProcess("WerFault.exe");
                	 }
                	 method.invoke(className.newInstance(),task.getTaskParam());
                 }catch(Exception e){
                	 // 杀掉进程重新来
                	 if(processIsExist("DW20.EXE")){
                		 killProcess("DW20.EXE");
                	 }
                	 e.printStackTrace();
                	 System.gc();
                	 System.out.println("++++++++++++++Error+++++++++++");
                	 method.invoke(className.newInstance(),task.getTaskParam());
                 }
			}
           
        }catch (Exception e) {
            System.out.println("出错了：" );
            e.printStackTrace();
        }
    }
    
    public boolean processIsExist(String processName){
    	boolean isExist = false;
    	Runtime run = Runtime.getRuntime();  
        Process process = null;  
        String outLine = "";
        try { 
			 process = run.exec("tasklist /fi "+'"'+"imagename eq "+processName+'"');
			 InputStreamReader isr = null; 
			 BufferedReader br = null;
			try {
					isr = new InputStreamReader(process.getInputStream(),"UTF-8");
					br = new BufferedReader(isr);
					String line = null;
					while ((line = br.readLine()) != null)
						outLine += line;
			} catch (IOException ioe) {
			}finally{
				try {
					isr.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			 process.destroy();
	       } catch (Exception e) {  
	    	   e.getStackTrace();
	    	   process.destroy();
	           System.out.println("Error my exec ");  
	       } finally{
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
	
        if(outLine.indexOf(processName)>-1){
        	isExist = true;
        }
        return isExist;
    }
    public void killProcess(String processName){

		Runtime run = Runtime.getRuntime();  
        Process process = null;  
        try { 
			 Timestamp first = new Timestamp(System.currentTimeMillis());
			 process = run.exec("taskkill /f /im "+processName);
	         WatchThread errorGobbler = new WatchThread(process.getErrorStream(), "ERROR");
			 WatchThread outputGobbler = new WatchThread(process.getInputStream(), "OUTPUT");
			 errorGobbler.start();
			 outputGobbler.start();
			 String  success =  process.waitFor()==0?"Success":"Error";
			 System.out.println("Kill "+processName+" "+success );
			 process.destroy();
	       } catch (Exception e) {  
	    	   e.getStackTrace();
	    	   process.destroy();
	           System.out.println("Error my exec ");  
	       } finally{
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