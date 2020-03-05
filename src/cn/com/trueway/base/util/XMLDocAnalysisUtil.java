package cn.com.trueway.base.util;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Stack;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


import javax.xml.parsers.*;

public class XMLDocAnalysisUtil extends DefaultHandler{
	java.util.Stack tags = new Stack();
	/**
	 * 1）DOM（JAXP Crimson解析器） 最简单 w3c标准 适用非常广 效率较低 遍历速度较慢
	 */
	public void analysis1(){
		Long lasting = System.currentTimeMillis();
		try{
			File f=new File("NewFile.xml"); 
		    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document d = builder.parse(f);
			NodeList nl = d.getElementsByTagName("VALUE");
			for (int i=0;i<nl.getLength();i++){
				System.out.println("编号:" + d.getElementsByTagName("NO").item(i).getFirstChild().getNodeValue());
				System.out.println("描述:"		+	d.getElementsByTagName("CONTENT").item(i).getFirstChild().getNodeValue());
				}
			System.out.println("运行时间："+(System.currentTimeMillis() - lasting)+"毫秒");
			}catch(Exception e){
			e.printStackTrace();
			}
	}
	/**
	 * 2）SAX  性能最高  弃用 太过复杂 
	 */
	public void analysis2(){
			long lasting = System.currentTimeMillis();
			try{
				SAXParserFactory sf = SAXParserFactory.newInstance();
				javax.xml.parsers.SAXParser sp = sf.newSAXParser();
				XMLDocAnalysisUtil reader = new XMLDocAnalysisUtil();
				sp.parse(new File("NewFile.xml"),reader);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			System.out.println("运行时间："+(System.currentTimeMillis() - lasting)+"毫秒");
		}
	public void characters(char ch[] ,int start,int length){
		String tag = (String) tags.peek();
		if(tag.equals("NO")){
			System.out.println("编号"+new String(ch,start,length));
		}
		if(tag.equals("CONTENT")){
			System.out.println("描述："+new String(ch,start,length));
		}
	}
		
		public void startElement(String url,String localName,String qName,Attributes attrs){
			tags.push(qName);
		}
		
		
	public XMLDocAnalysisUtil() {
			super();
		}
	/**
	 * 3）DOM4j 效率最高 并且速度 开发难度 都不是太高
	 */
	@SuppressWarnings("unchecked")
	public void analysis3(){
		Long lasting = System.currentTimeMillis();
		try{
			File f=new File("NewFile.xml"); 
			SAXReader reader = new SAXReader();
			org.dom4j.Document d = reader.read(f);
			Element root = d.getRootElement();
			Element foo;
			for (Iterator i=root.elementIterator("VALUE");i.hasNext();){
				foo = (Element) i.next();
				System.out.println("编号:" + foo.elementText("NO"));
				System.out.println("描述:"		+	foo.elementText("CONTENT"));
				}
			}catch(Exception e){
			e.printStackTrace();
			}
			System.out.println("运行时间："+(System.currentTimeMillis() - lasting)+"毫秒");
	}
	
	//===========================================================================================//
	/**
	 * 该解析是适用dom4j方式 详情请看上述对比
	 */
	public void analysis(InputStream in){
		Long lasting = System.currentTimeMillis();
		try{
			SAXReader reader = new SAXReader();
			org.dom4j.Document d = reader.read(in);
			Element root = d.getRootElement();
			Element foo;
			for (Iterator i=root.elementIterator("VALUE");i.hasNext();){
				foo = (Element) i.next();
				System.out.println("编号:" + foo.elementText("NO"));
				System.out.println("描述:"		+	foo.elementText("CONTENT"));
				}
			}catch(Exception e){
			e.printStackTrace();
			}
			System.out.println("运行时间："+(System.currentTimeMillis() - lasting)+"毫秒");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new XMLDocAnalysisUtil().analysis1();
		new XMLDocAnalysisUtil().analysis2();
		new XMLDocAnalysisUtil().analysis3();
	}

}
