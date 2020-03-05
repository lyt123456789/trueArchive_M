package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * 描述：日志txt操作
 * 作者：蔡亚军
 * 创建时间：2015-7-21 上午11:41:13
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TxtUtil {
	public String writeFile(String pdfPath,String json){
		String jsonPath = pdfPath.substring(0, pdfPath.length()-4)+".txt";
		File file = new File(jsonPath);
		FileOutputStream fos = null; 
		try { 
			fos = new FileOutputStream(file);
			byte[] data = json.getBytes("UTF-8");
			fos.write(data);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return jsonPath;
	}
}
