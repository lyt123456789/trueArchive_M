package cn.com.trueway.document.component.docNumberManager.unit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
/**
 * 
 * 描述：策略的解析类<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-19 上午10:10:58<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class ManageStrategyParse {
	
	/**
	 * 
	 * 描述：解析年号<br>
	 *
	 * @param strategy
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:24:07
	 */
	public static String parseByYear(DocNumberStrategy strategy){
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
	 	calendar.setTime(trialTime);
		StringBuilder sb = new StringBuilder();
		sb.append("<input id='year' name='year' style='width:45px' value=\"").append(calendar.get(Calendar.YEAR)).append("\" />");
		return sb.toString();
	}
	/**
	 * 
	 * 描述：解析序号<br>
	 *
	 * @param strategy
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:24:04
	 */
	public static String parseNumber(DocNumberStrategy strategy){
		if("0".equals(strategy.getContent()))
		return "<input id='number' name='number' type='text' style='width:40px' />";
		else
			return "<input id='number' name='number' maxlength='"+strategy.getContent()+"' type='text' style='width:40px' />";
	}

	/**
	 * 
	 * 描述：格式化字符串<br>
	 *
	 * @param value
	 * @param size
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:55
	 */
	public static String parseNumber2Size(int value,int size){
		value = value+1;
		String str = "";
		for(int i=0;i<size;i++){
			str += "0";
		}
		DecimalFormat df = new DecimalFormat(str);
		return df.format(value);
	}
	/**
	 * 
	 * 描述：解析键值对<br>
	 *
	 * @param strategy
	 * @return StringBuilder
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:38
	 */
	public static StringBuilder parseKeyValue(DocNumberStrategy strategy){
		String str = strategy.getContent();
		List<String[]> list = new ArrayList<String[]>();
		if(str!=null){
			String[] strArray = null;
			if(str.indexOf(";")!=-1){
				strArray = str.split(";");
				for(String s:strArray){
					if(s.indexOf(",")!=-1){
						String[] strArr = s.split(",");
						list.add(strArr);
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"lwdwlx\" name='lwdwlx' >\n");
		for(String[] strA : list){
			sb.append("<option value=\"").append(strA[0]).append("\">").append(strA[1]).append("</option>\n");
		}
		sb.append("</select>\n");
		return sb;
	}
	
	public static String parseGroupNumber(DocNumberStrategy strategy){
		String str=strategy.getContent();
		String number="";
		if(str!=null){
			if(str.indexOf("_")!=-1){
				number=str.split("_")[1];
			}
		}
		if(number.equals("0")){
			return "<input id='number' type='text' style='width:40px' />";
		}
		return "<input id='number' maxlength='"+number+"' type='text' style='width:40px' />";
	}
	/**
	 * 
	 * 描述：取汉字<br>
	 *
	 * @param obj
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:31
	 */
	public static String parseBySelf(Object obj){
		Pattern p = null;
		Matcher m = null;
		String value = null;
		p = Pattern.compile("[\u4e00-\u9fa5]{2,}+|-|[[A-Za-z]{1,}[\u4e00-\u9fa5]{1,}|[[A-Za-z]+-]+]+");
		m = p.matcher(obj.toString());
		if (m.find()) {
			value = m.group(0);
		}
		if(value!=null&&value.trim().length()!=0){
			int size=value.length();
			if(size>=5){
				size=size+size/2;
			}
			return "<input size='"+size+"' id=\"type\" name='type' value='"+value+"'/>";
		}
		return obj.toString();
	}
	/**
	 * 
	 * 描述：解析实例<br>
	 *
	 * @param str
	 * @return List<String>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:24
	 */
	public static List<String> parseStrategy(String str){
		List<String> list = new ArrayList<String>();
		if(!str.contains("$")){
			list.add(str);
		}else{
			//[$fff$$bbbs$]
			if(str.contains("$$")){
				String[] ostr =  new String[]{"","",""};
				int beginIndex = str.indexOf("$");
				int endIndex = str.lastIndexOf("$");
				//[
				ostr[0] = str.substring(0,beginIndex); 
				if(!ostr[0].equals("")){
					list.add(ostr[0]);
				}
				//$fff$$bbbs$
				ostr[1] = str.substring(beginIndex, endIndex+1);
				list.add(ostr[1].split("\\$\\$")[0]+"$");
				list.add("$"+ostr[1].split("\\$\\$")[1]);
				//]
				ostr[2] = str.substring(endIndex+1,str.length()); 
				if(!ostr[2].equals("")){
					list.add(ostr[2]);
				}
			}else{
				//$fff$
				int beginIndex = str.indexOf("$");
				int endIndex = str.lastIndexOf("$");
				if(!"".equals(str.substring(0,beginIndex))){
					list.add(str.substring(0,beginIndex));
				}
				list.add(str.substring(beginIndex, endIndex+1));
				if(!"".equals(str.substring(endIndex+1,str.length()))){
					list.add(str.substring(endIndex+1,str.length()));
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
//		//String[] strarr = parseStrategyTest("$fff$$bbbs$$bbbs$");
//	 	for(String str : parseStrategy("[$fff$$bbbs$$aaa$]")){
//	 		System.out.println(str);
//	 	}
	  String total = /*"通政办发[2011]555号"*/"0000000";
	  System.out.println(parseBySelf(total));
	}
}
