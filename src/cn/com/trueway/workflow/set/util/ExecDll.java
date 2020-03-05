package cn.com.trueway.workflow.set.util;

import java.sql.Timestamp;

import org.xvolks.jnative.exceptions.NativeException;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


/**
 * 调用dll将pdf 生成图片以及根据pdf url获取pdf页数
 * @author Trueway
 */
public class ExecDll {
	/*static 
	{   
		String pdfToImage = SystemParamConfigUtil.getParamValueByParam("PDFToImage");
        System.load(pdfToImage); 
    } */ 
	
	/**
	 * 根据pdfpath 得到pdf 总共有多少页
	 * @param pdfPath
	 * @return int 返回总页数
	 * @throws NativeException
	 * @throws IllegalAccessException
	 *//*
	public int CreateRecordingCores(String  pdfPath) throws NativeException, IllegalAccessException
	{
		//                           dll Name          调用的方法名
		JNative myJNA = new JNative("PDFToImage.dll", "GetPDFPage");
		// 设置返回值类型
		myJNA.setRetVal(Type.INT);
		//设置参数 位置 及参数值
		myJNA.setParameter(0, pdfPath);
		myJNA.invoke();
		// 获取返回值
		return Integer.parseInt(myJNA.getRetVal());
	}
	*/
	/**
	 * 将pdf转成 图片,放到pngPath目录下面
	 * @param pdfPath
	 * @param pngPath 存放图片的目录
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public int PDFToPNGImage(String pdfPath,String pngPath) throws NativeException, IllegalAccessException{
		String jsonAddToPDF = SystemParamConfigUtil.getParamValueByParam("PDFToImage");
		String[] params = new String[4];
		params[0] = jsonAddToPDF;
		params[1] = pdfPath;
		params[2] = pngPath;
		String threadCount = SystemParamConfigUtil.getParamValueByParam("threadCount");
		params[3] = threadCount;
		int count = 0;
		Runtime rt =Runtime.getRuntime();
		Process process = null;  
		 try { 
			 Timestamp first = new Timestamp(System.currentTimeMillis());
			 process = rt.exec(params);
	         WatchThread errorGobbler = new WatchThread(process.getErrorStream(), "ERROR");
			 WatchThread outputGobbler = new WatchThread(process.getInputStream(), "OUTPUT");
			 errorGobbler.start();
			 outputGobbler.start();
		     count =  process.waitFor();
			 System.out.println("ExitValue: " + count);
			 Timestamp second = new Timestamp(System.currentTimeMillis());
			 Long time = second.getTime()-first.getTime();
			 System.out.println("pdfPath used time ="+time+"ms");
			 process.destroy();
	       } catch (Exception e) {  
	    	   e.getStackTrace();
	    	   process.destroy();
	           System.out.println("Error my exec ");  
	       } 
		 finally{
	        	if(process!=null){
	        		process.destroy();
	        		process=null;
	        	}
	        }  
		return count;
	}
	
	public static void main(String[] args) {
    }
}
