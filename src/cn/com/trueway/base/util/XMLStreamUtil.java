package cn.com.trueway.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.JSONException;
import org.json.XML;


/**
 * 工具类 实体转xml 和 xml转实体 json 转xml  xml转json
 * @author hux
 * 时间：2013年3月18日18:44:15
 *
 */
public class XMLStreamUtil {
	
	
//	/**
//	 * 测试
//	 * @throws JAXBException
//	 * @throws IOException
//	 */
//	    public  void toXML() throws JAXBException, IOException {
//	        JAXBContext context = JAXBContext.newInstance(Person.class);
//	        //下面代码演示将对象转变为xml
//	        Marshaller m = context.createMarshaller();
//	        Address address = new Address("China","Beijing","Beijing","ShangDi West","100080");
//	        Person s  = new Person(Calendar.getInstance(),"JAXB2",address,Gender.MALE,"SW");
//	        FileWriter fw = new FileWriter("person.xml");
//	        m.marshal(s,fw);
//
//	        //下面代码演示将上面生成的xml转换为对象
//	        FileReader fr = new FileReader("person.xml");
//	        Unmarshaller um = context.createUnmarshaller();
//	        s = (Person)um.unmarshal(fr);
//	        System.out.println("Country:"+s.getAddress().getCountry());
//	    }
	
	    /**
	     * 把实体直接转换xml并保存
	     * @param os
	     * @param object
	     */
	    public static void toXMLForFileWriter(FileWriter os,Object object){
	    	JAXBContext context;
			try {
				context = JAXBContext.newInstance(object.getClass());
				 //将对象转变为xml
		        Marshaller m = context.createMarshaller();
		        m.marshal(object,os);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				try {
					os.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
	    /**
	     * 把xml类型的file文件转换需要对象
	     * （需要与实体类映射）
	     * @param in 流
	     * @param object 需要转换的类型
	     * @return
	     */
	    public static Object getObjectForFileReader(FileReader in,Object object){
	    	 JAXBContext context;
			try {
				//生成的xml转换为对象
				  context = JAXBContext.newInstance(object.getClass());
			      Unmarshaller um = context.createUnmarshaller();
			      object  = um.unmarshal(in);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				try {
					in.close();
					object = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return object;
	    }

	    /**
	     * 把实体直接转换xml并output
	     * @param os
	     * @param object
	     */
	    public static void toXMLForOutputStream(OutputStream os,Object object){
	    	JAXBContext context;
			try {
				context = JAXBContext.newInstance(object.getClass());
				 //将对象转变为xml
		        Marshaller m = context.createMarshaller();
		        m.marshal(object,os);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				try {
					os.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
	    /**
	     * 把inputStream转换需要对象
	     * 
	     * @param in 流
	     * @param object 需要转换的类型
	     * @return
	     */
	    public static Object getObjectForInputStream(InputStream in,Object object){
	    	 JAXBContext context;
			try {
				//生成的xml转换为对象
				  context = JAXBContext.newInstance(object.getClass());
			      Unmarshaller um = context.createUnmarshaller();
			      object  = um.unmarshal(in);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				try {
					in.close();
					object = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return object;
	    }
	    
	    /**
	     * 把json格式的字符 转换成xml形式写入到手机端
	     * @param jsonStr
	     * @param buffWri
	     * @return
	     */

	    public static boolean jsonToXMLForSend(String jsonStr,PrintWriter buffWri){
	    	
	        try {
	        	org.json.JSONObject jsonObj=new  org.json.JSONObject(jsonStr);
	   	        buffWri.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	   	        buffWri.write("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">");
				buffWri.write(XML.toString(jsonObj));
				buffWri.flush();
			    buffWri.close();
			    return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				buffWri.close();
				e.printStackTrace();
				return false;
			}
	    }
	    /**
	     * 把inputStream里面的xml取出来转换json字符串
	     * @param in
	     * @return
	     */
	    public static String XMLToJsonForInputStream(InputStream in){
	    	
			try {
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				int ch;
				while ((ch = in.read()) != -1) {
						bytestream.write(ch);
				}
				byte imgdata[] = bytestream.toByteArray();
				bytestream.close();
				return XML.toJSONObject(new String (imgdata,"UTF-8")).toString();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	    }

	    
	/**
	 * @param args
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JSONException, IOException {
		// TODO Auto-generated method stub
		//测试
		BufferedReader buffRed=null;
		 BufferedWriter buffWri=null;
		 buffWri = new BufferedWriter(new FileWriter("myxml.xml"));
		 org.json.JSONObject jsonObj=new  org.json.JSONObject("{\"key\":\"value\"}");
		 	buffWri.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 	buffWri.newLine();
	        buffWri.write("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">");
	        buffWri.newLine();
	        buffWri.write(XML.toString(jsonObj));
			buffWri.flush();
		    buffWri.close();
        System.out.println("run end!");
      //将XML转换成JSON
        String fileName1="myxml.xml";
        buffRed=new BufferedReader(new FileReader(fileName1));
        String tempStr;
        StringBuffer xmlStrBuff=new StringBuffer();
       while((tempStr=buffRed.readLine())!=null)
            xmlStrBuff.append(tempStr);
        System.out.println("JSON str="+XML.toJSONObject(xmlStrBuff.toString()));
	}

}
