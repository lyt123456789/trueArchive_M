package cn.com.trueway.base.util;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 
 * FTP Automatic Application Deployment <BR>
 * <BR>
 * <P> [功能] 获得系统路径</P>
 * <P> [说明] </P>
 * <P> [备注] </P>
 * <P> [环境] Java2 1.6.0_13 Tomcate5.5</P>
 * <P> Copyright(c) 上海帝联信息科技发展有限公司(南通研发中心) 2010 </P>
 *
 * @author Lazy(DNION)
 * @version ver1.0
 * @Jun 12, 2010 3:15:39 PM
 */
public class PathUtil {
	
	/**
	 * 获得工程里的path，到 class 目录下面
	 * @return
	 */
	public static String getProjectPath(){
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		URL resource = cld.getResource("");
		String url = resource.getFile();
		url = URLDecoder.decode(url);
		return url; 
	}

	//获取当前工程的web-inf路径
	public static String getWebInfPath(){
	     String path = getProjectPath();
	     if (path.indexOf("WEB-INF") > 0) {
	      path = path.substring(0, path.indexOf("WEB-INF")+8);
	     } else {
	      //throw new IllegalAccessException("路径获取错误");
	     }
	     return path;
	}

	//获取当前工程路径
	public static String getWebRoot(){
	     String path = getProjectPath();
	     if (path.indexOf("WEB-INF") > 0) {
	      path = path.substring(0, path.indexOf("WEB-INF/classes"));
	     } else {
	      //throw new IllegalAccessException("路径获取错误");
	     }
//	     return path;
//	     return "/D:/Project/OA/TZ/CG/CGService-9285/webapps/trueWorkFlow/";
	     return path;
	}
	
	public static String getProjectPath(String path){
		return getProjectPath() + path;
	}
	
	public static void main(String[] args) {
		String src="http://192.168.7.44:8080/trueWorkflow/form/html/src/basic.html";
		String ctx="trueWorkflow";
		String url=src.substring(src.indexOf(ctx)+ctx.length(),src.length());
	}
}
