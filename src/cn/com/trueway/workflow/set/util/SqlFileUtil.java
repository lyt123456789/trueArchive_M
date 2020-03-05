package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

public class SqlFileUtil {
	private static Logger logger=Logger.getLogger(SqlFileUtil.class);

	public static void write(String path,List<String> sql){
		
		File file = new File(path);
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			for(int i = 0; i< sql.size() ; i++){
				if(i< sql.size()-1){
					out.write(sql.get(i)+"\r\n");
				}else{
					out.write(sql.get(i));
				}
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getCause().getMessage());
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			logger.error(e1.getCause().getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	public static void writeTXT(String path,List<String> sql){
		
		File file = new File(path);
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			for(int i = 0; i< sql.size() ; i++){
				if(i< sql.size()-1){
					out.write(sql.get(i)+"\r\n");
				}else{
					out.write(sql.get(i));
				}
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getCause().getMessage());
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			logger.error(e1.getCause().getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
}
