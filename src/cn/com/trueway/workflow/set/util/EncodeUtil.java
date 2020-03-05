package cn.com.trueway.workflow.set.util;

import java.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class EncodeUtil {

	/**
	 * 字符编码
	 */
	private String encoding = "";
	/**
	 * 最符合的字符编码，默认为系统编码
	 */
	private String cset = System.getProperty("file.encoding");
	/**
	 * 是否自动识别到字符集编码
	 */
	private boolean found = false;
	
	/**
	 * 使用CHARSET格式化字符串
	 * @param str      字符串
	 * @param charset  字符编码
	 * @return         编码过的字符串
	 */
	public  String encodeStr(String str, String charset) {
		StringBuffer strbuf = new StringBuffer();
		if (str == null) {
			return null;
		}
		if (charset == null || "".equals(charset)) {
			charset = "GBK";
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isChinese(ch[i])) {
				try {
					strbuf.append(URLEncoder.encode("" + ch[i], charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				strbuf.append(ch[i]);
			}

		}
		return strbuf.toString();
	}

	/**
	 * 判断字节是否为中文
	 * @param c 字节
	 * @return  中文？
	 */
	public  boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 获取URL的字符集编码
	 * @param url   URL
	 * @return      字符集编码
	 */
	public String getURLEncoding(URL url){
		String returnValue = "UTF-8";
		try {
			returnValue = getCharEncoding(url.openStream());
		} catch (IOException e) {
			/**获取字符集编码异常*/
			System.out.println("EncodeUtil：自动获取字符集编码异常！");
		}
		return returnValue;
	}
	
	/**
	 * 判断字符集编码 
	 * @param in  InputStream
	 * @return    字符集编码
	 */
	public String getCharEncoding(InputStream in) {
		nsDetector det = new nsDetector(3);
		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				HtmlCharsetDetector.found = true;
				encoding = charset;
			}
		});
		BufferedInputStream imp = null;
		try {
			imp = new BufferedInputStream(in);
			byte[] buf = new byte[2048];
			// 是否已经确定某种字符集
			boolean done = false; 
			// 假定当前的串是ASCII编码
			boolean isAscii = false; 
			int len = 0;
			boolean found = false;
			while ((len = imp.read(buf, 0, buf.length)) != -1 && !isAscii
					&& !done) {
				// 检查是不是全是ASCII字符，当有一个字符不是ASC编码时，则所有的数据即不是ASCII编码了
				if (isAscii) {
					isAscii = det.isAscii(buf, len);
				}
				// 如果不是ASCII字符，则调用DoIt方法.
				// 如果不是ASCII，又还没确定编码集，则继续检测。
				if (!isAscii && !done) {
					done = det.DoIt(buf, len, false); 
				}
			}
			// 最后要调用此方法，此时，Notify被调用。
			det.DataEnd(); 
			if (!HtmlCharsetDetector.found) {
				if (isAscii) {
					found = true;
				}
				// 如果没找到，则找到最可能的那些字符集
				if (!found) {
					String prob[] = det.getProbableCharsets();
					if (prob.length > 0) {
						encoding = prob[prob.length - 1];
					}
				}
			}
		} catch (Exception e) {
			/*字符集异常*/
			encoding = "gb2312";
		} finally {
			if (imp != null) {
				try {
					imp.close();
				} catch (IOException e) {
					System.out.println("取字符集时发生异常:" + e.toString());
				}
			}
		}
		if (encoding != null && encoding.indexOf("GB") > -1) {
			encoding = "gb2312";
		}
		return encoding;
	}
	
	/**
	 * 自动获取最可能的字符集编码
	 * @param data  字节
	 * @return      最可能的字符集编码
	 */
	public String detector(byte[] data) {
		// 语言线索常量
		nsDetector det = new nsDetector(nsPSMDetector.ALL); 
		det.Init(new nsICharsetDetectionObserver() {
					/*Notify(String charset)编码识别成功后调用*/
					public void Notify(String charset) {
						cset = charset;
						found = true;
					}
				});
		boolean isAscii = true;
		/*先测试ASCII*/
		isAscii = det.isAscii(data, data.length); 
		if (!isAscii){
			det.DoIt(data, data.length, false);  
		}
		det.DataEnd();    
		if (isAscii) {
			cset = "ASCII";
			found = true;
		}
		/*没找到设定一个最有可能的编码*/
		if (!found) {
			String prob[] = det.getProbableCharsets();
			if ((prob != null) && (prob.length > 0)) {
				cset = prob[0];
			}
		}
		return cset.toLowerCase().trim();
	}


}
