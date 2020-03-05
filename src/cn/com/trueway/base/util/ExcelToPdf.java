package cn.com.trueway.base.util;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ExcelToPdf {
	
	private static final int ppSaveAsPDF = 32;
	
	/**

	　　* 将excel文件转换为PDF文件

	　　* @param   sourceFilePath
	　　*          源文件路径
	　　* @param   destinPDFFilePath
	　　*          生成PDF文件路径
	　　*/

	public void excelToPDF(String sourceFilePath, String destinPDFFilePath, String instanceId, String attId) {
		ComThread.InitSTA();
		Dispatch sheet = null;  
		Dispatch sheets = null;  
		//建立ActiveX部件
		ActiveXComponent excelCom = new ActiveXComponent("Excel.Application");
		//定义所有pdf的数组
	    String outFiles[] = null;
		excelCom.setProperty("Visible", new Variant(false));
		//返回wrdCom.Documents的Dispatch
		Dispatch wordbooks = excelCom.getProperty("Workbooks").toDispatch();
		//调用excelCom.Documents.Open方法打开指定的excel文档，返回excelDoc
		Dispatch wordbook = Dispatch.invoke(wordbooks, "Open", Dispatch.Method, new Object[] { sourceFilePath ,new Variant(false) ,new Variant(true)}, new int[3]).toDispatch();
		sheets= Dispatch.get(wordbook, "Sheets").toDispatch();
		int count = Dispatch.get(sheets, "Count").getInt();  
		System.out.println("---------合并的excel的sheet个数："+count+"个-----------");
		outFiles = new String[count];
	 	for (int j = 1; j <= count; j++) {
	 		try {
	 			String outFile = "";
	 			sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get,new Object[] { new Integer(j) }, new int[1]).toDispatch(); 
	 			String sheetname = Dispatch.get(sheet, "name").toString();
	 			sheetname = sheetname.trim();
	 			Dispatch.call(sheet, "Activate");	
	 			Dispatch.call(sheet, "Select");
	 			outFile = destinPDFFilePath.substring(0,destinPDFFilePath.length()-4) + j +".pdf";
	 			Dispatch.invoke(wordbook,"SaveAs",Dispatch.Method,new Object[]{outFile,new Variant(57), new Variant(false),
	 					new Variant(57), new Variant(57),new Variant(false), new Variant(true),new Variant(57), new Variant(true),
	 					new Variant(true), new Variant(true) },new int[1]);
	 			outFiles[j-1] = outFile;
 			}catch (Exception ex) {
 				ex.printStackTrace();
 				System.out.println("--------未发现打印的内容---------");
 			}
 		}
 		Dispatch.call(wordbook, "Close",new Variant(false));
 		System.out.println("---------转换pdf成功---------");
 		excelCom.invoke("Quit",new Variant[]{});
 		ComThread.Release();
 		MergePdf mp = new MergePdf();
 		// 合并正文附件的pdf和表单的pdf
		// 遍历 去掉 有问题的 文件
		int count2 = outFiles.length;
		for(int i = 0 ; i< outFiles.length;i++){
			if(outFiles[i]== null){
				count2 --;
			}
		}
		String outFiles2[] = null;
		outFiles2 = new String[count2];
		int  j = 0; 
		for(int i = 0 ; i< outFiles.length;i++){
			if(outFiles[i]!= null){
				outFiles2[j] = outFiles[i];
				j++;
			}
		}
		mp.mergePdfFiles(outFiles2, destinPDFFilePath);
		// 删除pdf
		for (String otf : outFiles2) {
			if(otf != null && !otf.equals("")){
				File file = new File(otf);
				if(file.exists()){
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 
	 * 描述：将ppt文件转换为pdf文件
	 * @param inputFile
	 * @param pdfFile
	 * @return boolean
	 * 作者:蔡亚军
	 * 创建时间:2015-10-8 下午12:01:03
	 */
	public boolean ppt2PDF(String inputFile,String pdfFile){
        try{
        ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
        //app.setProperty("Visible", msofalse);
        Dispatch ppts = app.getProperty("Presentations").toDispatch();
        Dispatch ppt = Dispatch.call(ppts, 
        							"Open",
                                    inputFile,
                                    true,//ReadOnly
                                    true,//Untitled指定文件是否有标题
                                    false//WithWindow指定文件是否可见
                                    ).toDispatch();
        Dispatch.call(ppt,
                    "SaveAs",
                    pdfFile,
                    ppSaveAsPDF 
                    );
        Dispatch.call(ppt, "Close");
        app.invoke("Quit");
        return true;
        }catch(Exception e){
            return false;
        }
    }
}
