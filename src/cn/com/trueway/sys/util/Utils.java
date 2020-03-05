package cn.com.trueway.sys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.CLOB;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.PathUtil2;
import cn.com.trueway.base.util.SystemParamConfigUtil;
import cn.com.trueway.util.SystemTool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 关于文件上传操作等用到的工具类
 */
public class Utils {

	/**
	 * 上传数据
	 * 
	 * @param attach
	 * @param path
	 * @return
	 */
	public static String uploadFileVideo(MultipartFile attach, String path) {

		String url = "";
		if (attach.getOriginalFilename() != null
				&& !"".equals(attach.getOriginalFilename())) {
			// 获取文件后缀名
			String name = attach.getOriginalFilename();
			String type = name.substring(name.lastIndexOf(".") + 1);
			String dicpath = SystemParamConfigUtil.getParamValueByParam("zdzyUploadPath") + "video";
			File dicimg = new File(dicpath);
			if (!dicimg.exists()) {
				dicimg.mkdirs();
			}
			url = path + "." + type;
			FileOutputStream write = null;
			InputStream in = null;
			try {
				in = attach.getInputStream();
				write = new FileOutputStream(new File(dicpath + "/" + url));
				byte[] bt = new byte[1024 * 1024];
				int z = in.available();
				int d = 1024 * 1024;
				if (z < 1024 * 1024) {
					d = z;
				}
				while ((d = in.read(bt, 0, d)) != -1) {
					write.write(bt, 0, d);
					if (z / d == 0) {
						d = z % d;
					}
				}
				write.flush();
			} catch (Exception e) {
			} finally {

				if (null != write) {
					try {
						write.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return url;
	}

	/**
	 * 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static long getFileSize(File file) {

		FileInputStream fileInputStream = null;
		long size = 0;
		try {
			fileInputStream = new FileInputStream(file);
			size = fileInputStream.available();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	public static Object[] addArray(Object[] array, Object obj) {

		int index = 0;
		int length = 0;
		if (array == null || array.length <= 0) {
			length = 1;
		} else {
			index = array.length;
			length = array.length + 1;
		}
		Object[] oldArr = array;
		array = new Object[length];
		if (oldArr != null && oldArr.length > 0) {
			for (int i = 0; i < oldArr.length; i++) {
				array[i] = oldArr[i];
			}
		}
		array[index] = obj;
		return array;
	}

	// 验证string是否为空
	public static boolean isNotNullOrEmpty(String s) {

		if (null == s) {
			return false;
		}
		if (s.trim().equals("")) {
			return false;
		}
		if (s.trim().equals("null")) {
			return false;
		}
		return true;
	}

	// 将时间改成String型
	public static String getStringDate(String pattern, Date date) {

		return new SimpleDateFormat(pattern).format(date);
	}

	// 根据一页条数和总条数获取总页数
	public static int getZpage(int size, int count) {

		return count % size == 0 ? (count / size) : (count / size + 1);
	}

	/**
	 * 获取map中的key
	 * 
	 * @param map
	 * @return
	 */
	public static String[] getKey(Map<String, ?> map) {

		Set<String> set = map.keySet();
		String[] column = new String[set.size()];
		Iterator<String> iterator = set.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			column[i] = iterator.next().toString();
			i++;
		}
		return column;
	}

	/**
	 * 上传数据
	 * 
	 * @param attach
	 * @return
	 */
	public static String uploadFile(MultipartFile attach, String zdjc) {

		String url = "";
		if (attach.getOriginalFilename() != null
				&& !"".equals(attach.getOriginalFilename())) {
			// 获取文件后缀名
			String name = attach.getOriginalFilename();
			String type = name.substring(name.lastIndexOf(".") + 1);
			String dicpath = SystemParamConfigUtil.getParamValueByParam("zdzyUploadPath") + zdjc + "/upload/";
			File dicimg = new File(dicpath);
			if (!dicimg.exists()) {
				dicimg.mkdirs();
			}
			url = "/" + zdjc + "/upload/" + UUID.randomUUID().toString() + "." + type.toLowerCase();

			FileOutputStream write = null;
			InputStream in = null;
			try {
				in = attach.getInputStream();
				write = new FileOutputStream(new File(SystemParamConfigUtil.getParamValueByParam("zdzyUploadPath") + url));
				byte[] bt = new byte[1024 * 1024];
				int z = in.available();
				int d = 1024 * 1024;
				if (z < 1024 * 1024) {
					d = z;
				}
				while ((d = in.read(bt, 0, d)) != -1) {
					write.write(bt, 0, d);
					if (z / d == 0) {
						d = z % d;
					}
				}
				write.flush();
			} catch (Exception e) {
			} finally {
				if (null != write) {
					try {
						write.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return url;
	}

	/**
	 * 上传数据
	 * 
	 * @param attach
	 * @param path
	 * @return
	 */
	public static String uploadPicture(MultipartFile attach, String path) {

		String url = "";
		if (attach.getOriginalFilename() != null
				&& !"".equals(attach.getOriginalFilename())) {
			// 获取文件后缀名
			String name = attach.getOriginalFilename();
			String type = name.substring(name.lastIndexOf(".") + 1);
			String dicpath = PathUtil2.getServicePath() + "/picture";
			File dicimg = new File(dicpath);
			if (!dicimg.exists()) {
				dicimg.mkdirs();
			}
			url = path + "." + type;
			FileOutputStream write = null;
			InputStream in = null;
			try {
				in = attach.getInputStream();
				write = new FileOutputStream(new File(dicpath + "/" + url));
				byte[] bt = new byte[1024 * 1024];
				int z = in.available();
				int d = 1024 * 1024;
				if (z < 1024 * 1024) {
					d = z;
				}
				while ((d = in.read(bt, 0, d)) != -1) {
					write.write(bt, 0, d);
					if (z / d == 0) {
						d = z % d;
					}
				}
				write.flush();
			} catch (Exception e) {
			} finally {

				if (null != write) {
					try {
						write.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return url;
	}

	public static void createFtl(String name, String content){
		createFtl(name,content,null,null,null);
	}
	/**
	 * 创建模板ftl
	 * 
	 * @param name
	 *            模板名称
	 * @param content
	 *            内容
	 * @param resourceDicName
	 *            模板资源包名
	 * @param resourceDics
	 *            模板资源包下包含的目录
	 */
	public static void createFtl(String name, String content,String siteSimpleName, String resourceDicName, String resourceDics) {

		if (content != null && !"".equals(content)) {
			content = content.replaceAll("&nbsp;", " ");
			content = content.replaceAll("&gt;", ">");
			content = content.replaceAll("&lt;", "<");
			content = content.replaceAll("&quot;", "\"");
			content = content.replaceAll("&#39;", "'");
			if(StringUtils.isNotEmpty(siteSimpleName)){
				// 修改模板里的路径
				content = transStringWithRegx(content, siteSimpleName,resourceDicName,resourceDics);
			}
			
		}
		String basePath = SystemParamConfigUtil.getParamValueByParam("ftlPath");

		String path = name + ".ftl";
		File file = new File(basePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, path);
		// 文件存在,备份文件
		if (file.exists()) {
			try {
				String backupPath = SystemParamConfigUtil.getParamValueByParam("backupPath");
				if(StringUtils.isBlank(backupPath)){
					backupPath = PathUtil2.getWebInfPath();
				}
				String backupDir = backupPath + "backup/ftl/";
				File dirFile = new File(backupDir);
				if(!dirFile.exists()){
					dirFile.mkdirs();
				}
				File destFile = new File(backupDir + name + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".ftl");
				FileUtils.copyFile(file, destFile);
			} catch (Exception e) {
				System.out.println("自动备份ftl失败！" + e.getMessage());
			}
		}
		// 文件不存在,创建文件
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes("UTF-8"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createHTML(String filePath, String content){
		File file = new File(filePath);
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes("UTF-8"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 创建预览模板ftl
	 * 
	 * @param name
	 *            模板名称
	 * @param content
	 *            内容
	 * @param siteSimpleName
	 */
	public static void createPreviewFtl(String name, String content, String siteSimpleName, String resourceDicName, String resourceDics) {

		if (content != null && !"".equals(content)) {
			content = content.replaceAll("&nbsp;", " ");
			content = content.replaceAll("&gt;", ">");
			content = content.replaceAll("&lt;", "<");
			content = content.replaceAll("&quot;", "\"");
			content = content.replaceAll("&#39;", "'");
			content = transStringWithRegx(content, siteSimpleName,resourceDicName,resourceDics);
		}
		String basePath = SystemParamConfigUtil.getParamValueByParam("ftlPath");

		String path = name + ".ftl";
		File file = new File(basePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, path);
		// if (file.exists()) {
		// // 文件存在,删除文件
		// file.delete();
		// }
		// 文件不存在,创建文件
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes("UTF-8"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 正则替换，添加站点简称
	 * @param content
	 * @param siteSimpleName
	 * @param resourceDicName //资源包名
	 * @param resourceDics //资源包中的目录名称，以","隔开
	 * @return
	 */
	public static String transStringWithRegx(String content, String siteSimpleName, String resourceDicName, String resourceDics){
		String newContent = content;
		List<String> replaceString = new ArrayList<String>();
		String regExPrefix = SystemParamConfigUtil.getParamValueByParam("model.previewRegxPrefix");
		String regExSubfix = SystemParamConfigUtil.getParamValueByParam("model.previewRegxSubfix");
		String regEx = "(?:"+regExPrefix+")=\"?([^>]*?("+regExSubfix+"))(\"|>|\\s+)";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			replaceString.add(matcher.group(1));
		}
		
		//去重
		List<String> distinctReplaceString = new ArrayList<String>();
		
		for(String s : replaceString){
			if(!distinctReplaceString.contains(s)){
				distinctReplaceString.add(s);
			}
		}
		
		for(String s : distinctReplaceString){
			if(s.indexOf("http")<0&&s.indexOf("/"+siteSimpleName)!=0
					&&s.indexOf("./"+siteSimpleName)!=0&&s.indexOf("../"+siteSimpleName)!=0){
				//
				if(StringUtils.isNotEmpty(resourceDicName)&&
						StringUtils.isNotEmpty(resourceDics)){
					List<String> resourceDicList = Arrays.asList(resourceDics.split(","));
					
					if(resourceDicList.contains(getFirstDic(s))){
						newContent = newContent.replace(s, "/" + siteSimpleName+"/"+resourceDicName+transRelativeUrl(s));
					}else{
						if(s.indexOf("${")>=0){
							newContent = newContent.replace(s, transRelativeUrl(s) );
						}else{
							newContent = newContent.replace(s, "/" + siteSimpleName + transRelativeUrl(s) );
						}
					}
				}else{
					if(s.indexOf("${")>=0){
						newContent = newContent.replace(s, transRelativeUrl(s) );
					}else{
						newContent = newContent.replace(s, "/" + siteSimpleName + transRelativeUrl(s) );
					}
				}
			}
		}
		
		return newContent;
	}
	
	public static String transRelativeUrl(String url){
		if(url.startsWith("/")){
			return url;
		}
		if(url.startsWith("./")){
			return url.substring(1,url.length());
		}
		return "/" + url;
	}
	
	public static void addPluginToFile(String filePath, String siteURL, String siteId){
		addPluginToFile(filePath, siteURL, siteId, "");
	}
	/**
	 * 	添加插件（发布）
	 * 	
	 * 	添加一些公共的js：访问统计、视频。这些插件目前没有配置控制是否加入。暂时先都加入。
	 *  
	 * @param filePath 文件路径
	 * @param filePath 站点路径
	 * @return
	 */
	public static void addPluginToFile(String filePath, String siteURL, String siteId, String messageId){
		if(StringUtils.isBlank(filePath)){
			return;
		}
		try {
			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			Element head =  doc.getElementsByTag("head").first();
			Elements metas =  doc.select("meta[http-equiv=Content-Type]");
			if(metas == null || metas.size() == 0){
				head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			}else{
				metas.attr("content","text/html; charset=UTF-8");
			}
			head.append("<meta http-equiv=\"pragma\" content=\"no-cache\">");
			head.append("<meta http-equiv=\"Cache-Control\" content=\"no-cache,must-revalidate\">");
			head.append("<script id=\"common_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/common.js?siteURL=" + siteURL + "\"></script>");
			
			String cmsURL = getURL("") +  PathUtil2.getProjectName();
			Element body =  doc.getElementsByTag("body").first();
			body.append("<script id=\"visits_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/visits.js?cmsURL=" + cmsURL + "&siteId=" + siteId + "&messageId=" + messageId + "\"></script>")
					.append("<script type=\"text/javascript\" src=\"" + siteURL + "/resource/js/ckplayer/ckplayer.js\" charset=\"utf-8\"></script>")
					.append("<script id=\"video_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/video.js?siteURL=" + siteURL + "\"></script>")
					// 其它一些公用的js，都可以放到这个js里
					.append("<script id=\"plugin_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/plugin.js?siteURL=" + siteURL + "\"></script>");
			FileUtils.writeStringToFile(new File(filePath), doc.html(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	添加插件(用于预览)
	 * 	
	 * 	添加一些公共的js：访问统计、视频。这些插件目前没有配置控制是否加入。暂时先都加入。
	 *  
	 * @param filePath 文件路径
	 * @param filePath 站点路径
	 * @return
	 */
	public static void addPluginToFileForPreview(String filePath, String messageId){
		if(StringUtils.isBlank(filePath)){
			return;
		}
		try {
			String siteURL =  "/" + PathUtil2.getProjectName();
			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			Element head =  doc.getElementsByTag("head").first();
			Elements metas =  doc.select("meta[http-equiv=Content-Type]");
			if(metas == null || metas.size() == 0){
				head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			}else{
				metas.attr("content","text/html; charset=UTF-8");
			}
			head.append("<meta http-equiv=\"pragma\" content=\"no-cache\">");
			head.append("<meta http-equiv=\"Cache-Control\" content=\"no-cache,must-revalidate\">");
			head.append("<script id=\"common_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/common.js?siteURL=" + siteURL + "\"></script>");
			Element body =  doc.getElementsByTag("body").first();
			if(StringUtils.isNotBlank(messageId)){
				// 获取文章阅读数、视频
				String cmsURL = getURL("") +  PathUtil2.getProjectName();
				body.append("<script id=\"visitcount_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/visitcount.js?cmsURL=" + cmsURL + "&messageId=" + messageId + "\"></script>");
			}
			body.append("<script type=\"text/javascript\" src=\"" + siteURL + "/resource/js/ckplayer/ckplayer.js\" charset=\"utf-8\"></script>")
				.append("<script id=\"video_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/video.js?siteURL=" + siteURL + "\"></script>")
				// 其它一些公用的js，都可以放到这个js里
				.append("<script id=\"plugin_js\" type=\"text/javascript\" src=\"" + siteURL + "/resource/js/plugin.js?siteURL=" + siteURL + "\"></script>");
			
			FileUtils.writeStringToFile(new File(filePath), doc.html(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getFirstDic(String path){
		String[] dics = path.split("/");
		if(dics.length>0){
			for(String dic : dics){
				if(StringUtils.isNotEmpty(dic)&&!".".equals(dic)){
					return dic;
				}
			}
		}
		return "";
	}
	
	/**
	 * 删除模板ftl
	 * 
	 * @param name
	 */
	public static void deleteFtl(String name) {

		String basePath = SystemParamConfigUtil.getParamValueByParam("ftlPath");
		String path = name + ".ftl";
		File file = new File(basePath + path);
		if (file.exists()) {
			// 文件存在,删除文件
			file.delete();
		}
	}

	/**
	 * 截取String的前length长度的字段
	 * 
	 * @param old
	 * @param length
	 * @return
	 */
	public static String getSubStr(String old, int length) {

		String newStr = "";
		if (old.getBytes().length < length * 2) {
			return old;
		} else {
			newStr = old.substring(0, length) + "...";
		}
		return newStr;
	}

	/**
	 * 将数组两两组合成Map
	 * 
	 * @param keyValues
	 * @return
	 */
	public static Map<String, Object> toMap(String... keyValues) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (keyValues != null && keyValues.length > 1) {
			for (int i = 0; i < keyValues.length - 1; i += 2) {
				map.put(keyValues[i], keyValues[i + 1]);
			}
		}
		return map;
	}

	/**
	 * 将STRING 转成CLOB
	 * 
	 * @param str
	 * @return
	 */
	public static CLOB getCLOB(String str) {

		if (str == null)
			return null;

		try {
			CLOB clob = CLOB.getEmptyCLOB();
			clob.setBytes(str.getBytes());
			return clob;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取ip的方法
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {

		String ip = request.getHeader("x-forearded-for");
		if (!Utils.isNotNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			return request.getRemoteAddr();
		} else {
			String[] ips = ip.split(",");
			return ips[0];
		}
	}

	// 判断是否是整数
	public static boolean isInteger(String s) {

		Pattern p = Pattern.compile("^[\\d]*");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static int downloadFile(String sourceUrl,String targetPath) throws MalformedURLException{
		int bytesum = 0;
		int byteread = 0;
		
		URL url = new URL(sourceUrl);
		URLConnection conn = null;
		InputStream inStream = null;
		try{
			//文件下载路径不存在则创建文件目录
			String targetDicPath = targetPath.substring(0, targetPath.lastIndexOf("/"));
			FileUploadUtils.mkDirectory(targetDicPath);
			conn = url.openConnection();
			inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(targetPath);
			
			byte[] buffer = new byte[1024];
			int length;
			while((byteread = inStream.read(buffer))!=-1){
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return bytesum;
	}
	
/*	public static void main(String[] args) throws IOException{
		InputStream is = new FileInputStream(new File("D://Apache//conf//httpd.conf"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer string = new StringBuffer();
		String line = reader.readLine();
		while(line != null){
			string.append(line);
			string.append("\n");
			line = reader.readLine();
			
		}
		reader.close();
		is.close();
		String regEx = "#begin(\\S+)";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string.toString());
		List<String> replaceString = new ArrayList<String>();
		while (matcher.find()) {
			replaceString.add(matcher.group(1));
		}
//		String sss = Utils.transStringWithRegx(string.toString(),"12121");
		List<String> returnString = new ArrayList<String>();
		for(String s : replaceString){
			String returnstr = string.substring(string.indexOf("#begin"+s), string.indexOf("#end"+s));
			returnString.add(returnstr);
		}		
		String returnStr="";
		for(String s : returnString){
			returnStr += s;
		}
		System.out.println(returnStr);
//		String ss = SystemParamConfigUtil.getParamValueByParam("bjlxbm");
//		if(ss.indexOf("bjlx")>=0){
//			System.err.println("123");
//		}else{
//			System.err.println("456");
//		}
	}*/
	
	public static String getURL(String url){
		if (!Utils.isNotNullOrEmpty(url)) {
			url = SystemParamConfigUtil.getParamValueByParam("homeUrl");
		}
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		return url;
	}
	
	public static String fomatHtmlRes(String htmlRes){
		Document doc = Jsoup.parse(htmlRes);
		htmlRes = doc.html();
		
		return htmlRes;
	}
	
	public static void copyFile(File src, File dest) throws IOException{
		if(src.isDirectory()){
			if(!dest.exists()){
				dest.mkdirs();
			}
			String files[] = src.list();
			for(String file : files){
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				
				copyFile(srcFile, destFile);
			}
		}else{
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] buffer = new byte[1024];
			
			int length;
			
			while((length = in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
	
	public static void main(String[] args){
		try {
			Utils.copyFile(new File("C:/Users/trueway/Desktop/zhaotb2"), new File("D:/Users/trueway/Desktop/zhaotb2"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取apache配置文件模板
	 * @param tplPath 模板路径
	 * @param tplPath 模板名称
	 * 
	 * @return
	 */
	public static String buildConf(String tplPath, String tplName, Map<String, Object> data){
		StringWriter out = new StringWriter();
		try {
			Configuration config = new Configuration();
			config.setDirectoryForTemplateLoading(new File(tplPath));
			config.setDefaultEncoding("UTF-8");
			Template template = config.getTemplate(tplName, "UTF-8");
			template.setEncoding("UTF-8");
			template.process(data, out);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	// 获取电脑mac地址
	public static String getMac() throws Exception {

		String os = SystemTool.getOSName();
		String mac = null;
		System.err.println(os);
		if (os.equals("windows 7") || os.equals("windows vista")) {
			mac = SystemTool.getMACAddress();
		} else if (os.startsWith("windows")) {
			// // 本地是windows
			mac = SystemTool.getMACAddress();
			// mac = SystemTool.getWindowsMACAddress();
		} else {
			// 本地是非windows系统 一般就是unix
			mac = SystemTool.getUnixMACAddress();
		}
		return mac;
	}

	public static void SEO(String filePath, String title, String keyword, String description){

		if(StringUtils.isBlank(filePath)){
			return;
		}
		try {
			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			Element head =  doc.getElementsByTag("head").first();
			if(StringUtils.isNotBlank(title)){
				Element titleEle =  doc.getElementsByTag("title").first();
				if(titleEle == null){
					head.append("<title>"+title+"</title>");
				}else if(StringUtils.isBlank(titleEle.html())){
					titleEle.html(title);
				}
			}

			if(StringUtils.isNotBlank(keyword)){
				Elements keywords =  doc.select("meta[name='keywords']");
				if(keywords == null || keywords.size() == 0){
					head.append("<meta name=\"keywords\" content=\""+keyword+"\">");
				}else{
					keywords.append(keyword);
				}
			}
			
			if(StringUtils.isNotBlank(description)){
				Elements descriptions =  doc.select("meta[name='description']");
				if(descriptions == null || descriptions.size() == 0){
					head.append("<meta name=\"descriptions\" content=\""+description+"\">");
				}else if(StringUtils.isBlank(descriptions.attr("content"))){
					descriptions.attr("content",description);
				}
			}
			
			FileUtils.writeStringToFile(new File(filePath), doc.html(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
