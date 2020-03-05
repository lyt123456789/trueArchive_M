package cn.com.trueway.workflow.set.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java去除字符串中的空格,回车,换行符,制表符
 * @author caiyj
 *
 */
public class StringUtil {
	
	public static String replaceBlank(String str){
		String dest = "";
		if(str!=null){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static void main (String[] args){
		System.out.println(StringUtil.replaceBlank("just do it"
				));
	}

}
