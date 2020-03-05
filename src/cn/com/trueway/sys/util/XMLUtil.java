package cn.com.trueway.sys.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
 
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.com.trueway.sys.pojo.Dic;

/**
 * 
 * 描述：对系统参数设置的setting.xml文件进行操作
 * 作者：刘钰冬
 * 创建时间：2016-8-12 下午5:59:50
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class XMLUtil {
	// 配置文件路径
	private static String fileURL = PathUtil.getWebClassesPath() + "setting.xml";
	
	
	/**
     * 向XML文件中添加一个系统设置
     * @param user
     */
    public static void add(Dic dic){
        Document document=getDocument();
        Element rootElement=document.getRootElement();
         
        Element dicElement=rootElement.addElement("attribute");
        Element desElement=dicElement.addElement("desc");
        Element nameElement=dicElement.addElement("name");
        Element requiredElement=dicElement.addElement("required");
        Element contentElement=dicElement.addElement("content");
        
        desElement.setText(dic.getDicName());
        nameElement.setText(dic.getDicKey());
        requiredElement.setText(dic.getDicRemark());
        contentElement.setText(dic.getDicValue());
         
        write2XML(document);
    }
    
    /**
     * 根据name删除系统设置
     * @param name
     */
    public static void deleteByName(String name){
        Document document=getDocument();
        Element rootElement=document.getRootElement();
         
        List<Element> dicElements=rootElement.elements("attribute");
        for (Element dic : dicElements) {
            if(dic.element("name").getTextTrim().equals(name)){
                System.out.println("开始删除.....");
                rootElement.remove(dic);
                System.out.println("删除结束.....");
            }
        }
        write2XML(document);
    }
    /**
     * 修改系统设置信息
     * @param dic
     */
    public static void update(Dic dic){
        Document document=getDocument();
        Element rootElement=document.getRootElement();
         
        List<Element> dicElements=rootElement.elements();
        for (Element dicElement : dicElements) {
            if(dicElement.element("name").getTextTrim().equals(dic.getDicKey())){
                dicElement.element("content").setText(dic.getDicValue());
            }
        }
        write2XML(document);
    }
    
    /**
     * 
     * 描述：由key数查找
     * @param name
     * @return Dic
     * 作者:刘钰冬
     * 创建时间:2016-8-12 下午6:00:36
     */
    public static Dic getDicByName(String name){
    	 Document document=getDocument();
         Element rootElement=document.getRootElement();
          
         Dic d = new Dic();
         List<Element> dicElements=rootElement.elements("attribute");
         for (Element dic : dicElements) {
             if(dic.element("name").getTextTrim().equals(name)){
            	 d.setDicName(dic.element("desc").getTextTrim());
     			 d.setDicKey(dic.element("name").getTextTrim());
     			 d.setDicValue(dic.element("content").getTextTrim());
     			 d.setDicRemark(dic.element("required").getTextTrim()); 
             }
         }
         
         return d;
    }
    
	/**
     * 获取XML中所有的系统设置信息
     * @return
     */
	public static List<Dic> getAllDic() {
		Document document = getDocument();
		Element rootElement = document.getRootElement();

		List<Element> dicElements = rootElement.elements();
		List<Dic> dics = new ArrayList<Dic>();
		for (Element dicElement : dicElements) {
			Dic dic = new Dic();
//			dic.setId(dicElement.attributeValue("id"));
			dic.setDicName(dicElement.element("desc").getTextTrim());
			dic.setDicKey(dicElement.element("name").getTextTrim());
			dic.setDicValue(dicElement.element("content").getTextTrim());
			dic.setDicRemark(dicElement.element("required").getTextTrim());
			dics.add(dic);
		}
		return dics;
	}
	
	 /**
     * 获取根节点
     * @return rootElement
     */
    public static Document getDocument(){
        try {
            SAXReader saxReader=new SAXReader();
            Document document=saxReader.read(fileURL);
             
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
	
    /**
     * 把内容写到XML文件中
     * @param document
     */
    public static void write2XML(Document document){
        try {
            OutputFormat format=new OutputFormat("  ", true, "utf-8");
            format.setTrimText(true);
            XMLWriter writer = new XMLWriter(format);
            writer.setOutputStream(new FileOutputStream(fileURL));
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * 描述：TODO 获取所有参数（除了一些如setup.step除外
     * ）
     * @return List<Dic>
     * 作者:刘钰冬
     * 创建时间:2016-8-15 上午9:20:03
     */
	public static List<Dic> getAllDicNotSetp() {
		Document document = getDocument();
		Element rootElement = document.getRootElement();

		List<Element> dicElements = rootElement.elements();
		List<Dic> dics = new ArrayList<Dic>();
		for (Element dicElement : dicElements) {
			Dic dic = new Dic();
//			dic.setId(dicElement.attributeValue("id"));
			dic.setDicName(dicElement.element("desc").getTextTrim());
			dic.setDicKey(dicElement.element("name").getTextTrim());
			dic.setDicValue(dicElement.element("content").getTextTrim());
			dic.setDicRemark(dicElement.element("required").getTextTrim());
			if(!dic.getDicKey().equals("setup.step")&&!dic.getDicKey().equals("version")&&!dic.getDicKey().equals("productName")&&!dic.getDicKey().equals("superUser")&&!dic.getDicKey().equals("keyWord")){
				dics.add(dic);
			}
		}
		return dics;
	}

}
