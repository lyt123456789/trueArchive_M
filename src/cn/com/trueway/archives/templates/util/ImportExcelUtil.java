package cn.com.trueway.archives.templates.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ImportExcelUtil {
	
	public static List<Map<String, String>> importExcel(File file,int sheetIndex) {
		try {
			//��ȡ�����
			FileInputStream is = new FileInputStream(file);
			//��ȡexcel
			Workbook workbook = new HSSFWorkbook(is);
			//�õ��ڼ���sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			//�õ����һ�е�����
			int lastRowNum = sheet.getLastRowNum();
			
			//��ȡ��ͷ
			Map<Integer, String> headMap = new HashMap<Integer, String>();
			//��ȡ��ͷ���ڵ���
			Row row = sheet.getRow(1);
		    //��ȡ���һ�е�����
		    short lastCellNum = row.getLastCellNum();
	        for (int j = 0; j < lastCellNum; j++) {
	            Cell cell = row.getCell(j);
	            headMap.put(j, cell.getStringCellValue());
	        }
	        
	        //��ȡ����
	        List<Map<String, String>> bodyList = new ArrayList<Map<String, String>>();
	        for (int i = 2; i <= lastRowNum; i++) {
        		Map<String,String> map = new HashMap<String,String>();
	        	Row rowBody = sheet.getRow(i);
	        	for (int j = 0; j < lastCellNum; j++) {
	        		Cell cell = rowBody.getCell(j);
	        		map.put(headMap.get(j), cell.getStringCellValue());
	        	}
	        	bodyList.add(map);
	        }
	        return bodyList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		try {
			//字符集
			String encodingStyle = "utf-8";
			//WSDL的地址
            String endpoint = "http://localhost:8080/trueArchive/services/dataReceptionWebService";  
            //命名空间，在WSDL中对应的标签是：参见说明第3条                                    
            String targetNamespace = "http://service.templates.archives.trueway.com.cn/";
            //具体方法的调用URI，在WSDL中对应的标签是：参见说明第4条
            String soapActionURI = "http://WebXml.com.cn/getCharFonts";
            //具体调用的方法名，在WSDL中对应的标签是：参见说明第5条
            String method = "upload";
            //调用接口的参数的名字
            String[] paramNames = {"byFontsLength"};
            //调用接口的参数的值
            Integer[] paramValues = {1};

			
			
	 
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			//call.setTimeout(new Integer(20000));  //设置超时时间
	        //call.setSOAPActionURI(soapActionURI);
	        call.setTargetEndpointAddress(endpoint);  //设置目标接口的地址
	        call.setEncodingStyle(encodingStyle);//设置传入服务端的字符集格式如utf-8等
	        call.setOperationName(new QName(targetNamespace,method));// 具体调用的方法名，可以由接口提供方告诉你，也可以自己从WSDL中找  
	        call.setUseSOAPAction(true); 
			
	        
	        String path = "C:\\tomcat8.rar";
			// 这样就相当于构造了一个带文件路径的File了
			DataHandler handler = new DataHandler(new FileDataSource(path));
			/**
			 * 注册异常类信息和序列化类
			 *  ns:FileUploadHandler 和 wsdd配置文件中的typeMapping中的xmlns:hns="ns:FileUploadHandler" 的对应 
			 *  DataHandler 和wsdd 配置文件中的typeMapping中的qname="hns:DataHandler"的DataHandler对应
			 */
			QName qn = new QName("myns:DataHandler", "DataHandler");
			call.registerTypeMapping(DataHandler.class, qn, JAFDataHandlerSerializerFactory.class, JAFDataHandlerDeserializerFactory.class);
			
			// 设置方法形参，注意的是参数1的type的DataHandler类型的，和上面的qn的类型是一样的
			call.addParameter("handler", qn, ParameterMode.IN);
			call.addParameter("fileName", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("json_str", XMLType.XSD_STRING, ParameterMode.IN);
			
			
			File file = new File(path);
			 InputStream in = new FileInputStream(file);
		    // 取得文件大小
		    long length = file.length();
		 
		    // 根据大小创建字节数组
		    byte[] bytes = new byte[(int) length];
		 
		    // 读取文件内容到字节数组
		    int offset = 0;
		    int numRead = 0;
		    while (offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
		      offset += numRead;
		    }
		 
		    // 读取完毕的校验  
		    if (offset < bytes.length) {
		      throw new IOException("不能完全讀取文件："+ file.getName());
		    }
		 
		    in.close();
		 
		    String file64 = Base64.getEncoder().encodeToString(bytes);
		    call.addParameter("file64", XMLType.XSD_STRING, ParameterMode.IN);
			
	 
			// 设置返回值类型，下面2种方法都可以
			call.setReturnClass(String.class);
			// call.setReturnType(XMLType.XSD_STRING);
			String result = (String) call.invoke(new Object[] { handler, "1.txt"," {\"className\":\"com.baosight.wzplat.mm.wo.common.wms.TestUpdateId\",\"methodName\":\"updateDocId\",\"recId\":\"201807120008\"}",file64 });
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
