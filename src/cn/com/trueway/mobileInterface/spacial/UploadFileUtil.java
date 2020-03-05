package cn.com.trueway.mobileInterface.spacial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFileUtil {
	/**
	 * 上传文件的写入
	 * @param file 文件对象
	 * @param folder	文件夹目录
	 * @param fnn		写入的文件名
	 * @return
	 */
	public static boolean doUploadWrite(File file, String folder, String fnn){
		File dir = new File(folder);
		if(!dir.exists()) dir.mkdirs();
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		boolean flag = false;

		try{
			in = new BufferedInputStream( new FileInputStream(file) );
			out = new BufferedOutputStream(new FileOutputStream(new File(folder, fnn)));
			
			byte[] buffer = new byte[2048];
			int n = 0;
			while((n = in.read(buffer)) != -1){
				out.write(buffer, 0, n);
			}
			flag = true;
		}catch(IOException e){
			e.printStackTrace();
			flag = false;
		}finally{
			try{
				out.flush();
				out.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 流的写入文件
	 * @param inStream  输入流
	 * @param folder	文件夹目录
	 * @param fnn		写入的文件名
	 * @return
	 */
	public static boolean doUploadStreamWrite(InputStream inStream, String folder, String fnn){
		File dir = new File(folder);
		if(!dir.exists()) dir.mkdirs();
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		boolean flag = false;

		try{

			in = new BufferedInputStream(inStream);
			out = new BufferedOutputStream(new FileOutputStream(new File(folder, fnn)));
			
			byte[] buffer = new byte[1024];
			int n = 0;
			while((n = in.read(buffer)) != -1){
				out.write(buffer, 0, n);
			}
			flag = true;
		}catch(IOException e){
			e.printStackTrace();
			flag = false;
		}finally{
			try{
				out.flush();
				out.close();
				in.close();
				inStream.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 字节数组的写入文件
	 * @param inStream  输入流
	 * @param folder	文件夹目录
	 * @param fnn		写入的文件名
	 * @return
	 */
	public static boolean doUploadStreamWrite(byte[] byteArray, String folder, String fnn){
		File dir = new File(folder);
		if(!dir.exists()) dir.mkdirs();
		
		boolean flag = false;
		FileOutputStream fileOutputStream = null;
		try{

			fileOutputStream = new FileOutputStream(new File(folder, fnn));
			fileOutputStream.write(byteArray);
			fileOutputStream.flush();
			fileOutputStream.close();
			flag = true;
		}catch(IOException e){
			e.printStackTrace();
			flag = false;
		}finally{
			try{
				fileOutputStream.flush();
				fileOutputStream.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static String generateFinalName(String fileName){
		String fname = fileName.substring(0,fileName.lastIndexOf("."));		//文件名,不带后缀名
		String ftype = fileName.substring(fileName.lastIndexOf("."));		//文件类型  如: ".txt"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format( new Date() );
		//文件写入到服务器时文件名加入时间戳
		String finalName = fname + "_" + time + ftype;
		
		return finalName;
	}
}
