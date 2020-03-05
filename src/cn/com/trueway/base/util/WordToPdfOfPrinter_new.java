package cn.com.trueway.base.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class WordToPdfOfPrinter_new {
	
	private Logger logger = Logger.getLogger(this.getClass());


	/**
	 * 
	 * 描述：将doc文件转换为pdf文件
	 * @param sourceFilePath		源文件(doc文件)
	 * @param destinPDFFilePath		转换后的pdf文件
	 * @param instanceId			文件转换的实例Id
	 * @param attId					附件id
	 * @return boolean				附件是否转换成功
	 * 作者:蔡亚军
	 * 创建时间:2016-8-19 上午11:32:47
	 */
	public boolean docToPDF(String sourceFilePath, String destinPDFFilePath, String instanceId , String attId) {
		//建立ActiveX部件
		ActiveXComponent wordCom = new ActiveXComponent("Word.Application");
		try {
			wordCom.setProperty("Visible", new Variant(false));
			//返回wrdCom.Documents的Dispatch
			Dispatch wrdDocs = wordCom.getProperty("Documents").toDispatch();
			//调用wrdCom.Documents.Open方法打开指定的word文档，返回wordDoc
			Dispatch wordDoc = Dispatch.invoke(wrdDocs, "Open", Dispatch.Method, new Object[] { sourceFilePath ,new Variant(false) ,new Variant(true)}, new int[1]).toDispatch();
			File file = new File(destinPDFFilePath);
			if(!file.exists()){
				Dispatch.invoke(wordDoc, "SaveAs", Dispatch.Method, new Object[] { destinPDFFilePath , new Variant(17)}, new int[1]);
				System.out.println("---------转换pdf成功---------");
				Variant f = new  Variant(false);
				Dispatch.call(wrdDocs, "Close" ,f);
			}
			return true;
		} catch (Exception ex) {
			MDC.put("instanceId", instanceId);
			MDC.put("attId", attId);
			logger.error("doc转换文件为pdf失败,具体信息为："+ex.getMessage());
			MDC.remove("instanceId");
			MDC.remove("attId");
			//ex.printStackTrace();
			System.out.println("---------转换pdf失败---------");
			return false;
		}finally{
			wordCom.invoke("Quit",new Variant[]{});
		}
	}
	
	public static void main(String[] argv) {
		WordToPdfOfPrinter_new d2p = new WordToPdfOfPrinter_new();
	
		//d2p.docToPDF("D:/3333.doc", "D:/222.pdf");
	
		boolean   success   =   (new   File("D:/222.ps")).delete();
		boolean   success2   =   (new   File("D:/222.log")).delete();
	
		if(success){
			System.out.println("删除打印机文件成功");
		}
	}

}
