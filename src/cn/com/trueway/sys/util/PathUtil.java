package cn.com.trueway.sys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 描述：path工具类
 * 作者：刘钰冬
 * 创建时间：2016-8-15 上午10:55:27
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class PathUtil {

	// 获取当前文件路径
	public static String getWebClassesPath() {

		String path = PathUtil.class.getResource("").toString();

		if (path != null) {
			path = path.substring(5, path.indexOf("WEB-INF") + 8);// 如果是windwos将5变成6
		}
		return (path + "classes/");

	}

	// 获取当前工程的web-inf路径
	public static String getWebInfPath() throws IllegalAccessException {

		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	// 获取当前工程路径
	public static String getWebRoot() throws IllegalAccessException {

		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	// 获取当前工程
	public static String getRoot() throws IllegalAccessException {

		String path = getWebRoot();
		path = path.substring(0, path.length());
		int index1 = path.lastIndexOf("\\");
		int index2 = path.lastIndexOf("/");
		int index = index1 > index2 ? index1 : index2;
		return path.substring(0, index);
	}

	/**
	 * 
	 * 描述：获取当前工程名
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 上午10:56:00
	 */
	public static String getProjectName() {

		String root;
		try {
			root = getRoot();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		int start = root.lastIndexOf("\\");
		if (start < 0)
			start = root.lastIndexOf("/");

		if (start < 0)
			return null;
		return root.substring(start + 1);
	}

	/**
	 * 
	 * 描述：获取真实路径
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 上午10:56:21
	 */
	public static String getRealPath() {

		File directory = new File("");
		String projectpath = directory.getAbsolutePath();
		if (projectpath.endsWith("bin")) {
			projectpath = projectpath.substring(0, projectpath.length() - 4);
		}
		return projectpath;
	}

	/**
	 * 获取tomcatd的webapps绝对路径
	 * 
	 * @Title:PathUtil.java
	 * @Package:cn.com.cms.publish
	 * @param:@return
	 * @returnType:String
	 * @date:2012-10-18上午10:14:53
	 * @author:JiangWei
	 */
	public static String getTomcatPath() {

		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		if (tempdir.indexOf("webapps") > -1) {
			return tempdir;
		} else {
			return tempdir + "\\webapps";
		}
	}

	// 根据内容得到ImgUrl
	public static List<String> getImgStr(String htmlStr) {

		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*src=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); // 匹配src
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	/**
	 * 
	 * 描述：获取路径
	 * @param htmlStr
	 * @return List<String>
	 * 作者:刘钰冬
	 * 创建时间:2016-8-15 上午10:56:59
	 */
	public static List<String> getLinkStr(String htmlStr) {
		Pattern p_link;
		Matcher m_link;
		List<String> links = new ArrayList<String>();

		String regEx_link = "(href|src|data|value)=\"?([^>]*?(\\.docx|\\.doc|\\.jpg|\\.jpeg|\\.xls|\\.xlsx|\\.png|\\.gif|\\.bmp|\\.psd|\\.mp4|\\.rar|\\.zip|\\.pdf|\\.txt|\\.swf|\\.wmv))(\"|>|\\s+)"; // 链接地址,匹配文件类型为jpg,png,doc
		p_link = Pattern.compile(regEx_link, Pattern.CASE_INSENSITIVE);
		m_link = p_link.matcher(htmlStr);
		while (m_link.find()) {
			links.add(m_link.group(2));
		}
		return links;
	}
	
	/**
	 * 获取服务器地址
	 * 
	 * @return
	 */
	public static String getServicePath() {

		// String realPath = request.getRealPath("\\") + filePath;
		File directory = new File("");
		String projectpath = directory.getAbsolutePath();
		if (projectpath.endsWith("bin")) {
			projectpath = projectpath.substring(0, projectpath.length() - 4);
		}
		return projectpath + "/webapps";
	}

	// 根据内容得到ImgUrl
	public static List<String> getfjStr(String htmlStr) {

		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*href=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("href=\"?(.*?)(\"|>|\\s+)")
					.matcher(img); // 匹配src
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}
	
	/**
	 * 将一个文件复制到另外一个文件中
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFile(String oldPath, String newPath) {

		File file = new File(getServicePath() + "/" + oldPath);
		File outFile = new File(getServicePath() + "/"
				+ newPath.substring(0, newPath.lastIndexOf("/")));
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		outFile = new File(outFile, newPath.substring(newPath.lastIndexOf("/")+1,
				newPath.length()));
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(outFile);
			byte data[] = new byte[65000];
			long l = in.available();
			int k = 0;
			while ((long) k < l) {
				int j = in.read(data, 0, 65000);
				k += j;
				out.write(data, 0, j);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 将一个文件复制到另外一个文件中
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFile(File oldfile, File newfile) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(oldfile);
			out = new FileOutputStream(newfile);
			byte data[] = new byte[65000];
			long l = in.available();
			int k = 0;
			while ((long) k < l) {
				int j = in.read(data, 0, 65000);
				k += j;
				out.write(data, 0, j);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static String getDomainName(String url){
		if(StringUtils.isEmpty(url)){
			return "";
		}
		if(url.indexOf("//")>=0){
			String suburl = url.substring(url.indexOf("//")+2);
			return suburl.substring(0,suburl.indexOf("/"));
		}
		return "";
	}
	
	public static String getRelativePath(String url){
		if(StringUtils.isEmpty(url)){
			return "";
		}
		if(url.indexOf("//")>=0){
			String suburl = url.substring(url.indexOf("//")+2);
			return suburl.substring(suburl.indexOf("/"));
		}else{
			return url;
		}
	}
	
	public static void main(String[] args) throws Exception{
		
//		String abc = "http://www.baidu.com:9090/img/12321.jpg";
//		System.err.println(getDomainName(abc));
//		System.out.println(getRelativePath(abc));
//		File file = new File("f:\\test.txt");
//		FileReader fr = new FileReader(file);
//		int filelen = (int)file.length();
//		char[] chars = new char[filelen];
//		fr.read(chars);
//		String txt = String.valueOf(chars);
//		
//		List<String> list = getLinkStr(txt);
//		List<String> list = getImgStr(txt);
//		for(String str : list){
//			System.out.println(str);
//			txt = txt.replace(str, "test--------");
//		}
//		System.out.println(txt);
		
		System.out.println(PathUtil.getProjectName());
	}
}
