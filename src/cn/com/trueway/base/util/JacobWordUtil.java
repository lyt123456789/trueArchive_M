/**
 * 文件名称:WordOptionTool.java
 * 作者:Lv☂<br>
 * 创建时间:2013-9-25 上午11:31:40
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
public class JacobWordUtil {
	
	/**  
     * 用于给指定的word文档中某处字符串做替换  
     * @param textPath  待替换的word文档 
     * @param targetWord   待替换的字符串 
     * @param replaceWord  要替换成的字符串
     */ 
	public static void replaceWord(String textPath, String targetWord,   
            String replaceWord) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            Dispatch docs = app.getProperty("Documents").toDispatch();   
  
            Dispatch doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { textPath, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
  
            Dispatch selection = app.getProperty("Selection").toDispatch();// 获得对Selection组件
  
            Dispatch.call(selection, "HomeKey", new Variant(6));// 移到开头
  
            Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
  
            Dispatch.put(find, "Text", targetWord);// 查找字符串targetWord
  
            Dispatch.call(find, "Execute");// 执行查询
  
            Dispatch.put(selection, "Text", replaceWord);// 替换为replaceWord
  
            Dispatch.call(doc, "Save");// 保存
  
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
	
	public static void replaceWord(String textPath, Map<String,String> valueMap) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            Dispatch docs = app.getProperty("Documents").toDispatch();   
  
            Dispatch doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { textPath, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
            for (Map.Entry<String,String> entry:valueMap.entrySet()) {
	            Dispatch selection = app.getProperty("Selection").toDispatch();// 获得对Selection组件
	  
	            Dispatch.call(selection, "HomeKey", new Variant(6));// 移到开头
	  
	            Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
           
            	 Dispatch.put(find, "Text", entry.getKey());// 查找字符串targetWord
            	  
                 Dispatch.call(find, "Execute");// 执行查询
       
                 Dispatch.put(selection, "Text", entry.getValue());// 替换为replaceWord
                 
            }
           
            Dispatch.call(doc, "Save");// 保存
//            Dispatch.call(doc, "Save");// 保存
  
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
	
	public static void replaceWord(String textPath, List<String> targetWords,List<String> replaceWords) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            Dispatch docs = app.getProperty("Documents").toDispatch();   
  
            Dispatch doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { textPath, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
            for (int i=0;i<targetWords.size();i++) {
	            Dispatch selection = app.getProperty("Selection").toDispatch();// 获得对Selection组件
	  
	            Dispatch.call(selection, "HomeKey", new Variant(6));// 移到开头
	  
	            Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
           
            	 Dispatch.put(find, "Text", targetWords.get(i));// 查找字符串targetWord
            	  
                 Dispatch.call(find, "Execute");// 执行查询
       
                 Dispatch.put(selection, "Text", replaceWords.get(i));// 替换为replaceWord
                 
            }
           
            Dispatch.call(doc, "Save");// 保存
//            Dispatch.call(doc, "Save");// 保存
  
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
  
    /**
	 * 创建一个word文档
	 * @param txtContent 要写入word文档的内容
	 * @param fileName 要保存的word文档路径
	 */  
    public static void createWordFile(String txtContent, String fileName) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
  
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
            Dispatch docs = app.getProperty("Documents").toDispatch();   
            Dispatch doc = Dispatch.call(docs, "Add").toDispatch();   
            Dispatch selection = Dispatch.get(app, "Selection").toDispatch();   
            Dispatch.put(selection, "Text", txtContent);   
            Dispatch.call(Dispatch.call(app, "WordBasic").getDispatch(),   
                    "FileSaveAs", fileName);   
            Variant f = new Variant(false);   
            Dispatch.call(doc, "Close", f);   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
  
    /**
	 * 根据现有的txt文本来创建word
	 * @param txt txt文本路径
	 * @param wordFile word路径
	 */  
    public static void createWordWithTxt(String txt, String wordFile) {   
        String txtContent = null;   
        try {   
            txtContent = bufferedReader(txt);   
        } catch (IOException e) {   
            // TODO Auto-generated catch block
            e.printStackTrace();   
        }   
        createWordFile(txtContent, wordFile);   
    }   
  
    /**
	 * 读文件
	 * @param path
	 * @return
	 * @throws IOException
	 */  
    public static String bufferedReader(String path) throws IOException {   
        File file = new File(path);   
        if (!file.exists() || file.isDirectory())   
            throw new FileNotFoundException();   
        BufferedReader br = new BufferedReader(new FileReader(file));   
        String temp = null;   
        StringBuffer sb = new StringBuffer();   
        temp = br.readLine();   
        while (temp != null) {   
            sb.append(temp + " ");   
            temp = br.readLine();   
        }   
        return sb.toString();   
    }   
  
    /**
	 * 给指定的word文档在字符串指定位置插入图片
	 * @param wordFile word文档
	 * @param imagePath  待添加图片的路径
	 * @param str 指定的字符串位置
	 */  
    public static void insertImage(String wordFile, String imagePath,   
            String tarStr) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            Dispatch docs = app.getProperty("Documents").toDispatch();   
  
            Dispatch doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { wordFile, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
  
            Dispatch selection = app.getProperty("Selection").toDispatch();   
  
            Dispatch.call(selection, "HomeKey", new Variant(6));// 移到开头
  
            Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
  
            Dispatch.put(find, "Text", tarStr);// 查找字符串tarStr
  
            Dispatch.call(find, "Execute");// 执行查询
  
            Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),   
                    "AddPicture", imagePath);// 在指定位置插入图片
//            Dispatch.put(find, "Width", new Variant(400));
//            Dispatch.put(find, "Height", new Variant(500));
            Dispatch.call(doc, "Save");// 保存
  
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
    
    /**
	 * 给指定的word文档在字符串指定位置插入图片
	 * @param wordFile word文档
	 * @param imagePath  待添加图片的路径
	 * @param str 指定的字符串位置
	 */  
    @SuppressWarnings("unchecked")
	public static void insertImage(String wordFile, List<String> imagePaths,   
    		List<String> tarStrs) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            Dispatch docs = app.getProperty("Documents").toDispatch();   
  
            Dispatch doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { wordFile, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
            
            for(int i=0;i<tarStrs.size();i++){
            	String tarStr = tarStrs.get(i);
            	String imagePath = imagePaths.get(i);
            	Dispatch selection = app.getProperty("Selection").toDispatch();   
            	  
                Dispatch.call(selection, "HomeKey", new Variant(6));// 移到开头
      
                Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
      
                Dispatch.put(find, "Text", tarStr);// 查找字符串tarStr
      
                Dispatch.call(find, "Execute");// 执行查询
      
                Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),   
                        "AddPicture", imagePath);// 在指定位置插入图片
//                Dispatch.put(find, "Width", new Variant(400));
//                Dispatch.put(find, "Height", new Variant(500));
                Dispatch.call(doc, "Save");// 保存
            }
            
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
  
  
    /**
	 * 在指定word文档的指定位置创建一个表格
	 * @param wordFile指定word文档
	 * @param pos 指定位置
	 * @param numCols 列数
	 * @param numRows 行数
	 */  
    public static void createTable(String wordFile, String pos, int numCols,   
            int numRows) {   
        ActiveXComponent app = new ActiveXComponent("Word.Application");// 启动word
        Dispatch selection = null;   
        Dispatch doc = null;   
        Dispatch docs = null;   
        boolean b = false;   
        try {   
            app.setProperty("Visible", new Variant(false));// 设置word不可见
  
            docs = app.getProperty("Documents").toDispatch();   
  
            doc = Dispatch.invoke(   
                    docs,   
                    "Open",   
                    Dispatch.Method,   
                    new Object[] { wordFile, new Variant(false),   
                            new Variant(false) }, new int[1]).toDispatch();   
            // 打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，
            // 因为我们要保存原文件，所以以可写方式打开。
  
            selection = app.getProperty("Selection").toDispatch();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        if (pos == null || pos.equals(""))   
            b = false;   
        // 从selection所在位置开始查询
        Dispatch find = app.call(selection, "Find").toDispatch();   
        // 设置要查找的内容
        Dispatch.put(find, "Text", pos);   
        // 向前查找
        Dispatch.put(find, "Forward", "True");   
        // 设置格式
        Dispatch.put(find, "Format", "True");   
        // 大小写匹配
        Dispatch.put(find, "MatchCase", "True");   
        // 全字匹配
        Dispatch.put(find, "MatchWholeWord", "True");   
        // 查找并选中
        b = Dispatch.call(find, "Execute").getBoolean();   
        // 创建表格
        if (b) {   
            Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();   
            Dispatch range = Dispatch.get(selection, "Range").toDispatch();   
            Dispatch newTable = Dispatch.call(tables, "Add", range,   
                    new Variant(numRows), new Variant(numCols)).toDispatch();   
            Dispatch.call(selection, "MoveRight");   
        } else  
            System.out.println("没有找到指定的位置，请检查是否存在这样的位置。");   
        try {   
            Dispatch.call(doc, "Save");// 保存
            Dispatch.call(doc, "Close", new Variant(false));   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            app.invoke("Quit", new Variant[] {});   
            app.safeRelease();   
        }   
    }   
    
    /***************************************************************************
     * 根据书签插入数据(提供书签的操作，需优化)
     * 
     * @param bookMarkKey 书签名
     * @param info  插入的数据
     * @return
     */
  
    public boolean intoValueBookMark(String bookMarkKey, String info) throws Exception{
    	 ActiveXComponent word = new ActiveXComponent("Word.Application");// 启动word
        Dispatch activeDocument = word.getProperty("ActiveDocument")
                .toDispatch();
        Dispatch bookMarks = word.call(activeDocument, "Bookmarks")
                .toDispatch();
        boolean bookMarkExist = word.call(bookMarks, "Exists", bookMarkKey).getBoolean();
        if (bookMarkExist) {
            Dispatch rangeItem = Dispatch.call(bookMarks, "Item", bookMarkKey).toDispatch();
            Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
            Dispatch.put(range, "Text", new Variant(info));
            return true;
        } 
        return false;
    }
    /**
	 * @param args
	 */  
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {  
    	String imgurl = "c:\\2.jpg";
    	String wordurl = "c:\\1.doc";
//    	insertImage(wordurl, imgurl, "{{pic}}");
    	Map map = new HashMap();
    	map.put("{{ly}}", "海底世界啊哈发送");
    	map.put("{{didian}}", "花洒");
    	replaceWord(wordurl, map);
    	System.out.println(wordurl);
    }   
}  

