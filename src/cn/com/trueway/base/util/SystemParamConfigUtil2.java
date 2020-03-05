package cn.com.trueway.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;


/**
 * 描述：<对此类的描述，可以引用系统设计中的描述><br>
 * 作者：顾锡均<br>
 * 创建时间：2009-12-25 11:00<br>
 * 修改：2010-05-04  李伟
 * 版本：v1.0 <br>
 * JDK版本：JDK1.5
 */
public final class SystemParamConfigUtil2 {

	/**
	 * 根据配置文件里的Key得到Value值
	 * 
	 * @param param
	 *            配置文件里的Key值
	 * @param propertiesFilePath
	 *            配置文件相对路径 默认从classes根目录下找配置文件
	 *            如config包下的Constants.properties配置文件
	 *            则参数即写成"Constants.properties"
	 * @return
	 */
	public synchronized static String getParamValueByParam(String param,
			String propertiesFilePath) {
		if (sysParamMap == null||!propertiesFilePath.equals("")) {
			initSmsParam(propertiesFilePath);
		}
		String result = (String) sysParamMap.get(param.toUpperCase());
		return result;
	}

	private static Map<String, String> sysParamMap = null;

	private static void initSmsParam(String propertiesFilePath) {
		String fileName = SystemParamConfigUtil2.class.getResource("/")
				+ propertiesFilePath;

		int n = fileName.lastIndexOf("/");
		
		InputStream in = SystemParamConfigUtil2.class
				.getResourceAsStream(fileName.substring(n));
		Properties pps = new Properties();

		try {
			pps.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Object[] obj = pps.keySet().toArray();

		sysParamMap = new Hashtable<String, String>();
		for (int i = 0; i < obj.length; i++) {
			String key = (String) obj[i];
			String value = (String) pps.get(obj[i]);
			sysParamMap.put(key.toUpperCase(), value);
		}
	}
	
	/**
	 * 
	 * @param param 参数名称
	 * @return
	 */
	public synchronized static String getParamValueByParam(String param){
		if (sysParamMap == null) {
			initSmsParam();
		}
		String result = (String)sysParamMap.get(param.toUpperCase());
		return  result;
	}
	
	private static void initSmsParam() {
		String fileName = SystemParamConfigUtil2.class.getResource("/")+  "sys.properties";
		int n = fileName.lastIndexOf("/");
		InputStream in = SystemParamConfigUtil2.class.getResourceAsStream(fileName.substring(n));
		Properties pps = new Properties();
		
		try {
			pps.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[] obj = pps.keySet().toArray();

		sysParamMap = new Hashtable<String, String>();
		for(int i=0;i<obj.length;i++){
			String key = (String)obj[i];
			String value = (String)pps.get(obj[i]);
			sysParamMap.put(key.toUpperCase(),value.trim());
		}
	}
	
}
