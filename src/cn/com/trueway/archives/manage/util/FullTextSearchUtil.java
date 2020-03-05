package cn.com.trueway.archives.manage.util;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

@SuppressWarnings("serial")
public class FullTextSearchUtil{
	private static String errorlogpath = "D:\\error.txt";
	
	
	
	 /**
     * 文件创建索引
     */
    public static void createIndexByMapList(List<Map<String,String>> docList,String indexLocation) throws IOException {
    	 // 1. 创建 Directory (索引存放位置)
    	 Directory dir=FSDirectory.open(new File(indexLocation));  
        IndexWriter indexWriter = null;
        // 2. 创建IndexWriter 写索引
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);
        indexWriter = new IndexWriter(dir, iwc);
        // 3. 创建Document 对象 field
        Document document;
        for(int i=0;docList!=null&&docList.size()>0&&i<docList.size();i++){
        	document = new Document();       
            // 4. 为Documen添加field    
        	Map<String,String> map = docList.get(i);
            String dataId=map.get("dataId");
            String fatherStructId=map.get("fatherStructId");
            String JNstructId=map.get("JNstructId");
            String WJstructId=map.get("WJstructId");
            String title=map.get("title");
            String dh=map.get("dh");
            String bgqx=map.get("bgqx");
            String zrz=map.get("zrz");
            String nd=map.get("nd");
            String content=map.get("content");
            
            
            document.add(new StringField("dataId", dataId, Field.Store.YES));
            document.add(new StringField("fatherStructId", fatherStructId, Field.Store.YES));
            document.add(new StringField("JNstructId", JNstructId, Field.Store.YES));
            document.add(new StringField("WJstructId", WJstructId, Field.Store.YES));
            document.add(new TextField("title", title, Field.Store.YES));
            document.add(new TextField("bgqx", bgqx, Field.Store.YES));
            document.add(new TextField("zrz", zrz, Field.Store.YES));
            document.add(new StringField("nd", nd, Field.Store.YES));
            document.add(new TextField("content", content, Field.Store.YES));
         // 5. 通过IndexWriter 添加文档到索引中
            indexWriter.addDocument(document);   
        } 
        indexWriter.close();
    }  
	
    /**
     * 删除所有索引
     */
    public static void deleteAll(String indexPath) throws Exception{
    	 Directory dir = FSDirectory.open(new File(indexPath));
    	 IndexWriter indexWriter = null;
         Analyzer analyzer = new IKAnalyzer();
         IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);
         indexWriter = new IndexWriter(dir, iwc);
         indexWriter.deleteAll();
         indexWriter.close();
    }
    
    public static List<String[]> searchIndex(List<String> indexPathList,String[] fields,String keyWord,int pageSize,int pageIndex) throws Exception {
    	List<String[]> returnList = new ArrayList<String[]>(); 
    	List<IndexReader> allIndexReaders = new ArrayList<IndexReader>(); 
    	for(int i=0;i<indexPathList.size();i++){
    		// 1. 创建 Directory
    		  Directory dir = FSDirectory.open(new File(indexPathList.get(i))); // 本地磁盘
    		  // 2. 创建 IndexReader
    		  IndexReader indexReader = DirectoryReader.open(dir);
    		  allIndexReaders.add(indexReader);
    	}  
    	MultiReader multiReader = new MultiReader((IndexReader[])(IndexReader[])allIndexReaders.toArray(new IndexReader[allIndexReaders.size()]));
        // 3. 创建 IndexSearch
        IndexSearcher multiSearcher = new IndexSearcher(multiReader);
        // 4. 创建搜索的Query
        //String[] fields = { "title", "bgqx", "zrz", "nd","content"};  
        Query query = new MultiFieldQueryParser(fields, new IKAnalyzer()).parse(keyWord);  
        // 5.查询
        TopDocs topDocs = multiSearcher.search(query, pageSize+pageIndex);
        // 6. 根据topDocs 获得 scoreDocs
        ScoreDoc[] socreDocs = topDocs.scoreDocs;
        // 7. 高亮显示start  
        //算分  
        QueryScorer scorer=new QueryScorer(query);
        //显示得分高的片段  
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);  
        //设置标签内部关键字的颜色  
        //第一个参数：标签的前半部分；第二个参数：标签的后半部分。  
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //第一个参数是对查到的结果进行实例化；第二个是片段得分（显示得分高的片段，即摘要）  
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);  
        //设置片段  
        highlighter.setTextFragmenter(fragmenter);  
        
        //8. 组织数据
        int start = pageIndex;
        int end = pageSize+pageIndex;
        if(socreDocs.length>0){
        	if(socreDocs.length<end){
        		end=socreDocs.length;
        	}
        	for(int i=start;i<end;i++){
        		String[] str = new String[5];
        		StringBuffer result = new StringBuffer();
                Document doc = multiSearcher.doc(socreDocs[i].doc); 
                String title = "";
                if(doc.get("title")!=null&&!"".equals(doc.get("title"))){
                	title = highlighter.getBestFragment(new IKAnalyzer(), "title", doc.get("title"));
                	if(title==null||"null".equals(title)){
                		title=doc.get("title");
                	}
                }
                String dh = "";
                if(doc.get("dh")!=null&&!"".equals(doc.get("dh"))){
                	dh = highlighter.getBestFragment(new IKAnalyzer(), "dh", doc.get("dh"));
                	if(dh==null||"null".equals(dh)){
                		dh=doc.get("dh");
                	}
                }
                String bgqx = "";
                if(doc.get("bgqx")!=null&&!"".equals(doc.get("bgqx"))){
                	bgqx = highlighter.getBestFragment(new IKAnalyzer(), "bgqx", doc.get("bgqx"));
                	if(bgqx==null||"null".equals(bgqx)){
                		bgqx=doc.get("bgqx");
                	}
                }
                String zrz = "";
                if(doc.get("zrz")!=null&&!"".equals(doc.get("zrz"))){
                	zrz = highlighter.getBestFragment(new IKAnalyzer(), "zrz", doc.get("zrz"));
                	if(zrz==null||"null".equals(zrz)){
                		zrz=doc.get("zrz");
                	}
                }
                String nd = "";
                if(doc.get("nd")!=null&&!"".equals(doc.get("nd"))){
                	nd = highlighter.getBestFragment(new IKAnalyzer(), "nd", doc.get("nd"));
                	if(nd==null||"null".equals(nd)){
                		nd=doc.get("nd");
                	}
                } 
                result.append("&nbsp;题名：").append(title);
                result.append("&nbsp;&nbsp;档号：").append(dh);
                result.append("&nbsp;&nbsp;保管期限：").append(bgqx);
                result.append("&nbsp;&nbsp;责任者：").append(zrz);
                result.append("&nbsp;&nbsp;年度：").append(nd); 
                str[0]=doc.get("dataId");
                str[1]=doc.get("JNstructId");
                str[2]=doc.get("fatherStructId");
                str[3]=doc.get("title");
                str[4]=result.toString();
                returnList.add(str);
            }
        }
        multiReader.close();
        return returnList;
    } 
    
    
    
    
	public static String getTextContent(File file){
		 String content = "";
	     if(file.getName().endsWith(".doc")||file.getName().endsWith(".docx")){
	     	content=doc2String(file);
	     }else if(file.getName().endsWith(".txt")){
			content = txt2String(file);
	     }else if(file.getName().endsWith(".pdf")){
	     	content=pdf2String(file);
	     }else if(file.getName().endsWith(".xls")){
	    	 content = xls2String(file);
	     }else if(file.getName().endsWith(".xlsx")){
	    	content = xlsx2String(file);
	     }else if(file.getName().endsWith(".ppt")){
			content = ppt2String(file);
	     }else if(file.getName().endsWith(".html")){
	    	content = html2String(file);
	     }
		return content;
	}
	
	
    public static  String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

    public static  String xls2String(File file) {
		String result = "";
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(fis);
			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					for (int k = 0; k < cells.length; k++)
						sb.append(cells[k].getContents());
				}
			}
			fis.close();
			result += sb.toString();
		} catch (Exception e) {
			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
			e.printStackTrace();
		}
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
   
    private static String xlsx2String(File file){
    	InputStream str = null;
        try{
        	StringBuilder sb = new StringBuilder();
            str = new FileInputStream(file);
            XSSFWorkbook xwb = new XSSFWorkbook(str);  //利用poi读取excel文件流
            XSSFSheet st = xwb.getSheetAt(0);  //读取sheet的第一个工作表
            int rows=st.getLastRowNum();//总行数
            int cols;//总列数
            for(int i=0;i<rows;i++){
                XSSFRow row=st.getRow(i);//读取某一行数据
                if(row!=null){
                    //获取行中所有列数据
                    cols=row.getLastCellNum();
	                for(int j=0;j<cols;j++){
	                    XSSFCell cell=row.getCell(j);
	                    if(cell==null){
	                    }else{
		                    //判断单元格的数据类型
		                   /* switch (cell.getCellType()) { 
		                        case XSSFCell.CELL_TYPE_NUMERIC: // 数字 
		                        	sb.append(" "+cell.getNumericCellValue() + " "); 
		                            break; 
		                        case XSSFCell.CELL_TYPE_STRING: // 字符串 
		                        	sb.append(" "+cell.getStringCellValue() + " "); 
		                            break; 
		                        case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean 
		                        	sb.append(" "+cell.getBooleanCellValue() + " "); 
		                            break; 
		                        case XSSFCell.CELL_TYPE_FORMULA: // 公式 
		                        	sb.append(" "+cell.getCellFormula() + " "); 
		                            break; 
		                        case XSSFCell.CELL_TYPE_BLANK: // 空值 
		                            break; 
		                        case XSSFCell.CELL_TYPE_ERROR: // 故障 
		                            break; 
		                        default: 
		                            System.out.print("未知类型   "); 
		                            break; 
		                      } */
	                    	sb.append(cell.toString());
		                }
	                }
	                str.close();
                }
            }
            return sb.toString();
        }catch(IOException e){
        	errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
            e.printStackTrace();  
        }
        if(str!=null){
        	try {
				str.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return "";
         
    }
    
    public static  String ppt2String(File file){
		FileInputStream fi=null;
		try {
			fi = new FileInputStream(file);
			PowerPointExtractor ppExtractor=new PowerPointExtractor(fi);
			fi.close();
			return ppExtractor.getText();
		} catch (Exception e) {
			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
			e.printStackTrace();
		}
		if(fi!=null){
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";		
	}
    
    public static String html2String(File file){ 
    	InputStream input = null;
    	try {
    		input = new FileInputStream(file);
    	} catch (FileNotFoundException e) {
    		errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
    		e.printStackTrace();
    	}  
    	StringBuffer buffer = new StringBuffer();  
    	byte[] bytes = new byte[1024];
    	try {
    		for(int n ; (n = input.read(bytes))!=-1 ; ){  
    			buffer.append(new String(bytes,0,n,"UTF-8"));  
    		}
    		if(input!=null){
    			input.close();
    		}
    	} catch (IOException e) {
    		errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
    		e.printStackTrace();
    	}
    	String val = buffer.toString();
    	String start = "<body>";
     	String end = "</body>";
     	int s = val.indexOf(start) + start.length();
        int e = val.indexOf(end);
        return val.substring(s, e);  
    }
    	
    public static String doc2String(File file){  	
        if (file.exists()) {
        } else {
            return "";
        }   
        InputStream is = null;
        try {
        	String fileName = file.getName().toLowerCase();// 得到名字小写
            is=new FileInputStream(file);
            if (fileName.endsWith(".doc")) { // doc为后缀的
                WordExtractor extractor;
				try {
					extractor = new WordExtractor(is);
					return extractor.getText();
				} catch (Exception e) {
					errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
					e.printStackTrace();
				} 
            }
            if (fileName.endsWith(".docx")) { // docx为后缀的
                XWPFWordExtractor docx = new XWPFWordExtractor(new XWPFDocument(is));
                return docx.getText();
            }
            is.close();
        } catch (IOException e) {
        	errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
            e.printStackTrace();
        }
        if(is!=null){
        	try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return "";
    }
    
    public static String pdf2String(File file){  
    	PdfReader reader = null;
    	StringBuffer buff = new StringBuffer();
    	try {
			reader = new PdfReader(file.getAbsolutePath());
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
	    	int num = reader.getNumberOfPages();
	    	TextExtractionStrategy strategy = null;
	    	for(int i=1;i<=num;i++){
	    		 try {
					strategy = parser.processContent(i,new SimpleTextExtractionStrategy());
					buff.append(strategy.getResultantText());
				} catch (Exception e) {
					e.printStackTrace();
				}    		 
	    	}
	    	reader.close();
	    	return buff.toString();
		} catch (IOException e) {
			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
			e.printStackTrace();
			/*errorlog(file.getAbsolutePath()+"-------换成pdfbox读取pdf");
			return pdf2String2(file);*/
			return buff.toString();
		}
    }
    
    public String pdf2String2(File file){   	
    	int startPage = 1;
    	int endPage = Integer.MAX_VALUE;
    	String content = "";
    	InputStream input = null;
    	PDDocument document = null;
    	try {
    		input = new FileInputStream(file);
    		// 加载 pdf 文档
    		PDFParser parser = new PDFParser(input);
    		parser.parse();
    		document = parser.getPDDocument();
    		// 获取内容信息
    		PDFTextStripper pts = new PDFTextStripper();
    		pts.setSortByPosition(true);
    		endPage = document.getNumberOfPages();
    		pts.setStartPage(startPage);
    		pts.setEndPage(endPage);
    		try {
    			content = pts.getText(document);
    		} catch (Exception e) {
    			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
    		}
    	} catch (Exception e) {
			errorlog(file.getAbsolutePath()+"-------"+e.getMessage());
    	} finally {
    		if (null != input){
    			try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		if (null != document){
    			try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    	return content;
    }
    
	
	 /**
     * 遍历处文件下所有文件
     */
	public static List<File> getFileList(String strPath,List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(),filelist); // 获取文件绝对路径
                } else {
                	if(files[i].getName().endsWith(".doc")||files[i].getName().endsWith(".docx")||files[i].getName().endsWith(".xls")
                			||files[i].getName().endsWith(".xlsx")||files[i].getName().endsWith(".pdf")||files[i].getName().endsWith(".txt")
                			||files[i].getName().endsWith(".ppt")||files[i].getName().endsWith(".html")){
                		filelist.add(files[i]);
                        continue;
                	}   
                }
            }
        }
        return filelist;
    }
	
	public static void errorlog(String content){
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(errorlogpath), true)));
			out.write(content+"\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}