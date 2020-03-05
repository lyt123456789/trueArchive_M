package cn.com.trueway.base.util;

 import java.io.BufferedWriter;  
import java.io.ByteArrayOutputStream;
 import java.io.File; 
import java.io.FileInputStream;
 import java.io.FileNotFoundException; 
 import java.io.FileOutputStream;  
 import java.io.IOException;  
 import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
 import java.io.Writer; 
 import java.util.ArrayList; 
import java.util.Date;
 import java.util.HashMap;  
 import java.util.List;  
 import java.util.Map; 

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.trueway.workflow.core.pojo.WfTemplate;
 import freemarker.template.Configuration; 
 import freemarker.template.Template;
import freemarker.template.TemplateException; 
 
 /**
  * 通过模板生成word文档
  * 
  * @author hux
  *
  */
 
 public class DocumentHandlerNew {    
	 private static Configuration configuration = null; 
	 //模板路径
	 private static String path = "/model/";
	 // 临时输出路径
	 private static String outPath = "d:/outFile.xml";
	 
	 //初始化基本参数
	 public DocumentHandlerNew() {   
			 if(configuration == null){
				 configuration = new Configuration();      
				 configuration.setDefaultEncoding("utf-8");   
			 }
		 }    
	 /**
	  * 生成word文档
	  * @param dataMap 填充参数
	  * @param type  模板类型 1 2 3 
	  */
	 public File createDoc(Map<String, Object> dataMap ,String templeteName) throws FileNotFoundException, UnsupportedEncodingException {  
		 String pathAndFileName = "";
		 	//读取模板路径
			 configuration.setClassForTemplateLoading(this.getClass(), path); 
			 //默认设置所有为null的变量 值为""  也可自定义设置  这里只是用默认设置
			 configuration.setClassicCompatible(true);
			 Template t=null;      
			 try {          
				 // 通过type类型 选择加载模板类型        
				 t = configuration.getTemplate(templeteName);  
				 } catch (IOException e) {         
					 e.printStackTrace();       
					 }        
				 //输出路径及文件名称    生成的文档作为记录 保存
				 pathAndFileName = outPath;
				 File outFile = new File(pathAndFileName);   
				 if(outFile.exists()){
					 try {
						outFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
					 Writer out = null;        
					 try {          
							 FileOutputStream fos = new FileOutputStream(outFile);
							 OutputStreamWriter oWriter;
							 oWriter = new OutputStreamWriter(fos,"UTF-8");
							 out = new BufferedWriter(oWriter);  //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
					      
					      
						 //输出到word文档中
						 t.process(dataMap, out);  
						 out.close();
						 } catch (TemplateException e) {      
							 e.printStackTrace();        
						 } catch (IOException e) {         
							 e.printStackTrace();       
						 }    
						 return outFile;
			 } 
	 
	 private WebApplicationContext getSpringContext(){
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			return webApplicationContext;
		}
	 
	 public File createDoc2(Map<String, String> map,WfTemplate template, String zxbdw) throws FileNotFoundException, UnsupportedEncodingException{
		File outFile = null;	
		 try{
				//模板文件地址
				String srcPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\forMobile\\" + zxbdw+".doc";
				//临时文件地址，每次将文件拷贝到临时文件，然后再读取临时文件的内容，替换标签，
				String dstPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\forMobile\\info_" + zxbdw+".doc";
				
				FileInputStream in = new FileInputStream(new File(srcPath));
				HWPFDocument hdt = new HWPFDocument(in);
				//读取word文本内容
				Range range = hdt.getRange();
				//替换文本内容
				for (Map.Entry<String,String> entry:map.entrySet()) {
					//进行标签替换
					range.replaceText("{{"+entry.getKey()+"}}",entry.getValue());
				}
				ByteArrayOutputStream ostream = new ByteArrayOutputStream();
				
				File dstFile = new File(dstPath);
				//检查临时文件是否存在，存在先删除，再重新拷贝模板文件
				if(dstFile.exists()){
					dstFile.delete();
				}
				
				FileOutputStream out = new FileOutputStream(dstPath,true);
				hdt.write(ostream);
				//输出字节流
				out.write(ostream.toByteArray());
				out.close();
				ostream.close();
				outFile = new File(dstPath);
				
				
			}catch (Exception e) {
				 e.printStackTrace();      
			}
			return outFile;
	 }
	 
	 @SuppressWarnings("unchecked")
	public File createDocByJacob(Map<String, String> map,WfTemplate template, String zxbdw, List<String> labelList) throws FileNotFoundException, UnsupportedEncodingException{
			 try{
					//模板文件地址
					String srcPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\forMobile\\" + zxbdw+".doc";
					//临时文件地址，每次将文件拷贝到临时文件，然后再读取临时文件的内容，替换标签，
					String dstPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\forMobile\\" + zxbdw+"_temp_"+new Date().getTime()+".doc";
					File dstFile = new File(dstPath);
					//检查临时文件是否存在，存在先删除，再重新拷贝模板文件
					if(dstFile.exists()){
						dstFile.delete();
					}
					FileUploadUtils.copy(new File(srcPath), dstFile);
					
					FileInputStream in = new FileInputStream(new File(srcPath));
					HWPFDocument hdt = new HWPFDocument(in);
					//替换文本内容
					Map replMap = new HashMap();
					for (Map.Entry<String,String> entry:map.entrySet()) {
						//进行标签替换
						for(String str :labelList){
							if(!str.equals(entry.getKey())){
								replMap.put("{{"+entry.getKey()+"}}", entry.getValue());
//								JacobWordUtil.replaceWord(dstPath, "{{"+entry.getKey()+"}}", entry.getValue());
							}
						}
					}
					JacobWordUtil.replaceWord(dstPath, replMap);
					
					return new File(dstPath);
				}catch (Exception e) {
					 e.printStackTrace();      
				}
				return null;
		 }
		
			public static void main(String[] args) {
				// TODO Auto-generated method stub
				System.out.println("ok");
				DocumentHandlerNew o = new DocumentHandlerNew();
				System.out.println("ok");
				System.out.println("ok");
			}
			
 }
 