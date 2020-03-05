package cn.com.trueway.base.util;

import java.io.File;
import java.io.FileOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

/**
 * 合并pdf工具类
 * 
 * @author zj
 * 
 */
public class MergePdf {

	/*
	 * * 合並pdf文件 * * @param files 要合並文件數組(絕對路徑如{ "e:\\1.pdf", "e:\\2.pdf"}) *
	 * 
	 * @param newfile 合並後新產生的文件絕對路徑如e:\\temp.pdf,請自己刪除用過後不再用的文件請 * @return
	 * boolean 產生成功返回retValue, 否則返回null
	 */
	public String mergePdfFiles(String[] files, String newfile) {
		Document document = null;
		String[] fileValue = new String[files.length];
		JSONArray jsonArray = new JSONArray();
		if (files[0].endsWith(".true")) {
			TrueToPdf trueToPdf = new TrueToPdf();
			String[] result = trueToPdf.trueToPdf(files[0]);
			if(result!=null){
				if (!("").equals(result[1]) && !("null").equals(result[1])
						&& result[1] != null) {
					if(result[1].startsWith("{")){
						result[1] = "["+result[1]+"]";
					}
					jsonArray = JSONArray.fromObject(result[1]);
				}
				files[0] = result[0];
				fileValue[0] = files[0];
			}
		}
		try {
			File existFile = new File(files[0]);
			if(existFile.exists()){
				document = new Document(new PdfReader(files[0]).getPageSize(1));
				PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
				document.open();
				int pages = 0;
				for (int i = 0; i < files.length; i++) {
					if (files[i] != null && !("").equals(files[i])) {
						String file = files[i];
						if (file.endsWith(".true")) {
							TrueToPdf trueToPdf = new TrueToPdf();
							String[] result = trueToPdf.trueToPdf(file);
							if(result!=null){
								file = result[0];
								fileValue[i] = file;
								if (!("").equals(result[1])&& !("null").equals(result[1])&& result[1] != null) {
									if(result[1].startsWith("{")){
										result[1] = "["+result[1]+"]";
										JSONArray js = JSONArray.fromObject(result[1]);
										JSONArray jsonArray2 = js.getJSONObject(0).getJSONArray("pages");
										for (int z = 0; z < jsonArray2.size(); z++) {
											JSONObject jsonObject = jsonArray2.getJSONObject(z);
											int p = jsonObject.getInt("page");
											boolean flag = true;
											JSONArray oldPages = jsonArray.getJSONObject(0).getJSONArray("pages");
											for (int j = 0; j < oldPages.size(); j++) {
												JSONObject jsonObj = (JSONObject) oldPages.get(j);
												int pa = jsonObj.getInt("page");
												if ((pages + p) == pa) {
													flag = false;
												}
											}
											if (flag) {
												if(jsonObject.containsKey("processes")||jsonObject.containsKey("stamps")){
													jsonObject.put("page", pages + p);
													jsonArray.getJSONObject(0).getJSONArray("pages").add(jsonObject);
												}
												
												//jsonArray.add(jsonObject);
											}
										}
									}else{
										JSONArray js = JSONArray.fromObject(result[1]);
										for (int z = 0; z < js.size(); z++) {
											JSONObject jsonObject = js.getJSONObject(z);
											int p = jsonObject.getInt("page");
											boolean flag = true;
											for (int j = 0; j < jsonArray.size(); j++) {
												JSONObject jsonObj = (JSONObject) jsonArray
														.get(j);
												int pa = jsonObj.getInt("page");
												if ((pages + p) == pa) {
													flag = false;
												}
											}
											if (flag) {
												jsonObject.put("page", pages + p);
												jsonArray.add(jsonObject);
											}
										}
									}
									

								}
							}
						}
						PdfReader reader = new PdfReader(file);
						int n = reader.getNumberOfPages();
						pages += n;
						for (int j = 1; j <= n; j++) {
							document.newPage();
							PdfImportedPage page = copy.getImportedPage(reader, j);
							copy.addPage(page);
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("合并表单出错");
			e.printStackTrace();
		} finally {
			if(null != document){
				document.close();
			}
		}
		for (int i = 0; i < fileValue.length; i++) {
			if (fileValue[i] != null) {
				new File(fileValue[i]).delete();
			}
		}
		return jsonArray.toString();
	}

	/**
	 * 把文件合成true
	 * @param files
	 * @param saveMergePath
	 * @param json
	 * @throws Exception 
	 */
	public void mergePdfFiles(String[] files, String newfile,String json) throws Exception {
		Document document = null;
		String[] fileValue = new String[files.length];
		if (files[0].endsWith(".true")) {
			TrueToPdf trueToPdf = new TrueToPdf();
			String[] result = trueToPdf.trueToPdf(files[0]);
			if(result!=null){
				files[0] = result[0];
				fileValue[0] = files[0];
			}
		}
		try {
			document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile.replace(".true", ".pdf")));
			document.open();
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && !("").equals(files[i])) {
					String file = files[i];
					if (file.endsWith(".true")) {
						TrueToPdf trueToPdf = new TrueToPdf();
						String[] result = trueToPdf.trueToPdf(file);
						if(result!=null){
							file = result[0];
							fileValue[i] = file;
						}
					}
					PdfReader reader = new PdfReader(file);
//					try{
						int n = reader.getNumberOfPages();
						for (int j = 1; j <= n; j++) {
							document.newPage();
							PdfImportedPage page = copy.getImportedPage(reader, j);
							copy.addPage(page);
						}
//					}
//					catch (Exception e) {
//						System.out.println("---------加密的pdf跳过---------");
//						e.printStackTrace();
//					}
				}
			}
		} catch (Exception e) {
			System.out.println("合并表单出错");
			e.printStackTrace();
			throw e;
		} finally {
			if(null != document){
				document.close();
			}
		}
		for (int i = 0; i < fileValue.length; i++) {
			if (fileValue[i] != null) {
				new File(fileValue[i]).delete();
			}
		}
		new PDFToTrue().pdfToTrue(newfile.replace(".true", ".pdf"), json);
	}
	
	/**
	 * 
	 * 描述：合并pdf文件
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-3-1 上午10:05:45
	 */
	public void combinePDFFiles(String[] files, String path){
		Document document = null;
		try{
			document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(path));
			document.open();
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && !("").equals(files[i])) {
					String file = files[i];
					PdfReader reader = new PdfReader(file);
					int n = reader.getNumberOfPages();
					for (int j = 1; j <= n; j++) {
						document.newPage();
						PdfImportedPage page = copy.getImportedPage(reader, j);
						copy.addPage(page);
					}
				}
			}
		}catch (Exception e) {
		}finally {
			document.close();
		}
	}
	
	public boolean pdfIsNeedPwd(String file) {
		Document document = null;
		boolean isJm = false;
		try {
			String copyFile = file.replaceAll(".pdf", "copy.pdf").replaceAll(".PDF", "copy.pdf");
			File fileCopy = new File(copyFile);
			if(!fileCopy.exists()){
				fileCopy.createNewFile();
			}
			document = new Document(new PdfReader(file).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(copyFile));
			document.open();
			if (file != null && !("").equals(file)) {
				PdfReader reader = new PdfReader(file);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
		} catch (Exception e) {
			System.out.println("该文件为加密文件");
			e.printStackTrace();
			isJm = true;
		} finally {
			try {
				if (null != document) {
					document.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return isJm;
	}
}
