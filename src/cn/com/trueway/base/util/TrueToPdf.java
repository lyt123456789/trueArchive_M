package cn.com.trueway.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

/**
 * true文件转换成pdf
 * @author trueway
 *
 */
public class TrueToPdf {
	//true转换成pdf并且返回String数组new String[]{路径,json}
	public String[] trueToPdf(String path){
		String[] result = new String[2];
		File file = new File(path);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_TRUE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录

		File outFile = new File(pdfRoot+dstPath);
		if(!file.exists()){
			return null;
		}
		if(!outFile.exists()){
			outFile.mkdirs();
		}
		result[0]=pdfRoot+dstPath+path.substring(path.lastIndexOf("/"),path.length()).replace(".true", ".pdf");
		outFile = new File(outFile,path.substring(path.lastIndexOf("/"),path.length()).replace(".true", ".pdf"));
		FileInputStream in=null;
		FileOutputStream out=null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(outFile);
			byte[] data = new byte[in.available()];
			in.read(data, 0, in.available());
			byte[] datas = AESPlus.decrypt(data);
			if(!new String(datas,0,4).equals("true")){
				return null;
			}
			int len = Utils.getIntValue(datas,4,8);
			String json =new String(datas,8,len);
			result[1]=json;
			out.write(datas,(8+len),(datas.length-(8+len)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	public String[] trueTwoPdf(String path){
		String[] result = new String[2];
		File file = new File(path);
		String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录

		File outFile = new File(pdfRoot+dstPath);
		if(!file.exists()){
			return null;
		}
		if(!outFile.exists()){
			outFile.mkdirs();
		}
		result[0]=pdfRoot+dstPath+path.substring(path.lastIndexOf("/"),path.length()).replace(".true", ".pdf");
		outFile = new File(outFile,path.substring(path.lastIndexOf("/"),path.length()).replace(".true", ".pdf"));
		FileInputStream in=null;
		FileOutputStream out=null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(outFile);
			byte[] data = new byte[in.available()];
			in.read(data, 0, in.available());
			byte[] datas = AESPlus.decrypt(data);
			if(!new String(datas,0,4).equals("true")){
				return null;
			}
			int len = Utils.getIntValue(datas,4,8);
			String json =new String(datas,8,len);
			result[1]=json;
			out.write(datas,(8+len),(datas.length-(8+len)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	public static void main(String[] args) {

		new TrueToPdf().trueToPdf("d:/test.true");
	}
	
}
