package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * @描述 文件下载的工具类
 * @作者 吴新星
 * @时间 2009-12-29
 * 
 * @编辑描述 未知
 * @编辑人 范吉锋
 * @编辑时间 2012年8月18日, PM 03:16:45
 */
public class DownloadFileUtil {

	/**
	 * 设置头信息，完成文件下载,暂时只支持windows
	 * 
	 * @param response
	 * @param fileNameWithPath
	 *            --带路径的文件名
	 * @param fileName
	 *            --下载下来的文件的文件名
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 */
	public static void downloadFile(HttpServletResponse response, String fileNameWithPath, String fileName) throws Exception {
		FileInputStream fileinputstream = null;
		// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
		fileNameWithPath = new String(fileNameWithPath.getBytes(), Constants.getSysEncode());
		// 下载文件时显示的文件名，一定要经过这样的转换，否则乱码
		fileName = URLEncoder.encode(fileName, "GB2312");
		fileName = URLDecoder.decode(fileName, "ISO8859-1");
		File file = new File(fileNameWithPath);

		fileinputstream = new FileInputStream(file);
		long l = file.length();
		int k = 0;
		byte abyte0[] = new byte[65000];
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) l);
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		while ((long) k < l) {
			int j;
			j = fileinputstream.read(abyte0, 0, 65000);
			k += j;
			response.getOutputStream().write(abyte0, 0, j);
		}
		fileinputstream.close();
	}

	/**
	 * 转成UTF-8编码
	 * 
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
			} else {
				byte b[];
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append((new StringBuilder("%")).append(Integer.toHexString(k).toUpperCase()).toString());
				}

			}
		}

		return sb.toString();
	}

	public static void downloadFileWithouTranscoding(HttpServletResponse response, String fileNameWithPath, String fileName) {
		try {
			// 下载文件时显示的文件名，一定要经过这样的转换，否则乱码
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859-1");
			File file = new File(fileNameWithPath);
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) l);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				response.getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
