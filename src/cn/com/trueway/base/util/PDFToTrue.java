package cn.com.trueway.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFToTrue {
	// pdf转成true 返回null转换失败
	public String pdfToTrue(String path, String json) {
		if(json==null){
			json="";
		}
		if (!path.endsWith(".pdf")) {
			return null;
		}
		File file = new File(path);
		if(!file.exists()){
			return null;
		}
		File outFile = new File(path.replace(".pdf", ".true"));
		FileInputStream in=null;
		FileOutputStream out=null;
		try {
			in=new FileInputStream(file);
			out = new FileOutputStream(outFile);
			if(json != null && json.indexOf("\"resources\"")> -1 && json.startsWith("[")){
				json = json.substring(1, json.length()-1);
			}
			byte[] data = new byte[in.available()];
			in.read(data, 0, in.available());
			byte[] datas = Utils.getByteValue(json.getBytes().length);
			datas =Utils.addByte("true".getBytes(), datas);
			datas = Utils.addByte(datas,json.getBytes());
			datas =Utils.addByte(datas,data);
			datas =AESPlus.encrypt(datas);
			out.write(datas,0,datas.length);
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
		//file.delete();
		return path.replace(".pdf", ".true");
	}
	
	// pdf转成true 返回null转换失败
		public void pdfToTrue(String path, String json,String newPath) {
			if(json==null){
				json="";
			}
			if (!path.endsWith(".pdf")) {
				return ;
			}
			File file = new File(path);
			if(!file.exists()){
				return ;
			}
			File outFile = new File(newPath);
			FileInputStream in=null;
			FileOutputStream out=null;
			try {
				if(json != null && json.indexOf("\"resources\"")> -1 && json.startsWith("[")){
					json = json.substring(1, json.length()-1);
				}
				in=new FileInputStream(file);
				out = new FileOutputStream(outFile);
				byte[] data = new byte[in.available()];
				in.read(data, 0, in.available());
				byte[] datas = Utils.getByteValue(json.getBytes().length);
				datas =Utils.addByte("true".getBytes(), datas);
				datas = Utils.addByte(datas,json.getBytes());
				datas =Utils.addByte(datas,data);
				datas =AESPlus.encrypt(datas);
				out.write(datas,0,datas.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
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
			file.delete();
		}
}
