package cn.com.trueway.workflow.webService.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class FileUtil {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public  String downloadFromUrl(String fileUrl, String fileName) {
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到文件在服务器上的基路径
		String dstPath = null ;
		dstPath = FileUploadUtils.getRealFilePath(fileName,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
		logger.debug(dstPath);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(basePath + dstPath));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
			out.close();
			in.close();
			return dstPath;
		} catch (Exception e) {
			return null;
		}
	}
	
	public  String downloadFromUrl_pdf(String fileUrl, String fileName) {
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_process"); // 得到文件在服务器上的基路径
		basePath = basePath + "/";
		String dstPath = null ;
		dstPath = FileUploadUtils.getRealFilePath(fileName,basePath, Constant.GENE_FILE_PATH);//统一上传的路径
		logger.debug(dstPath);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(basePath + dstPath));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
			out.close();
			in.close();
			return basePath+dstPath;
		} catch (Exception e) {
			return null;
		}
	}



}
