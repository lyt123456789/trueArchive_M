package cn.com.trueway.workflow.set.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class FileGeneThread extends Thread{
	
	private String json ;
	private String instanceId;
	@Override
	public void run() {
		writeFileLog(json,instanceId);
	}

	public FileGeneThread(String json,String instanceId) {
		super();
		this.json = json;
		this.instanceId = instanceId;
	}

	public String writeFileLog(String json,String instanceId){
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, "file/"); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		String jsonPath = pdfRoot+dstPath+instanceId+".txt";

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
