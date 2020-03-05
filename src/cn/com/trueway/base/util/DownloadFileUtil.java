package cn.com.trueway.base.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


/**
 * 实现下载文件功能
 * 
 * @作者 吴新星
 * @创建日期 Nov 28, 2009 9:53:46 PM
 */
public class DownloadFileUtil {
	
	private static Logger log = Logger.getLogger("DownloadFileUtil");

	/**
	 * 设置头信息，完成文件下载，暂时只支持windows
	 * 
	 * @param response
	 * @param fileNameWithPath--带路径的文件名
	 * @param fileName--下载下来的文件的文件名
	 */
	public static void downloadFile(HttpServletResponse response,
			String fileNameWithPath, String fileName) {
		FileInputStream fileinputstream = null;
		try {
			// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
			System.out.println("fileNameWithPath转码前：" + fileNameWithPath);
			fileNameWithPath = new String(fileNameWithPath.getBytes(), "GB2312");
			// 下载文件时显示的文件名，一定要经过这样的转换，否则乱码
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859-1");
			File file = new File(fileNameWithPath);
			log.debug("fileNameWithPath转码后:" + fileNameWithPath);
			fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) l);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				response.getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			System.out.println("用户取消下载");
			e.printStackTrace();
		}finally{
			try {
				if(fileinputstream!=null){
					fileinputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转成UT-8编码
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
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append((new StringBuilder("%")).append(
							Integer.toHexString(k).toUpperCase()).toString());
				}

			}
		}

		return sb.toString();
	}
	
	/**
	 * 描述：从公文交换平台收取附件<br>
	 * add by zhouxy
	 * @param fileUrl
	 * @param fileName
	 * @return
	 * @throws Exception String
	 */
	public static String downloadFromUrl(String fileUrl, String fileName) throws Exception
	{
		// 此方法只能用户HTTP协议

		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		String dstPath = FileUploadUtils.getRealFilePath(fileName, basePath, Constant.UPLOAD_FILE_PATH);//统一上传的目录	
		
		URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(basePath + dstPath));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, count);
			}
			out.flush();
			out.close();
			in.close();
			return dstPath;
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String fileName = "吴新星";
		fileName = URLEncoder.encode(fileName, "GB2312");
		System.out.println(fileName);
		fileName = URLDecoder.decode(fileName, "ISO8859-1");
		System.out.println(fileName);
	}
}
