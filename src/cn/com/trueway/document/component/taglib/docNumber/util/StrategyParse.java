package cn.com.trueway.document.component.taglib.docNumber.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;

public class StrategyParse {
	
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
		String id = parse2Id(strategy.getStrategyId());
		sb.append("<input type=\"text\" class='form-input-control' id='").append(id).append("' value=\"").append(calendar.get(Calendar.YEAR)).append("\"/>");
		return sb.toString();
	}
	/**
	 * 
	 * 描述：解析数字类型的策略<br>
	 *
	 * @param strategy
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:24:04
	 */
	public static String parseNumber(DocNumberStrategy strategy){
		if(strategy != null && strategy.getContent()!= null && strategy.getContent().equals("0"))
			return "<input id='"+parse2Id(strategy.getStrategyId())+"' type='text' class='form-input-control'  />";
		return "<input id='"+parse2Id(strategy.getStrategyId())+"' maxlength='"+strategy.getContent()+"' type='text' class='form-input-control xh-class' />";
	}
	/**
	 * 
	 * 描述：去掉$<br>
	 *
	 * @param str
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:59
	 */
	private static String parse2Id(String str){
		return str.split("\\$")[1];
	}
	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
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
	 * 描述：解析键值对类型的策略<br>
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
		sb.append("<select id=\"").append(parse2Id(strategy.getStrategyId())).append("\" onchange=\"settype(this.value)\">\n");
		for(String[] strA : list){
			sb.append("<option value=\"").append(strA[0]).append("\">").append(strA[1]).append("</option>\n");
		}
		sb.append("</select>\n");
		return sb;
	}
	
	/**
	 * 
	 * 描述：TODO 解析数组定长策略中的长度大小<br>
	 *
	 * @param strategy
	 * @return String
	 *
	 * 作者:tkj<br>
	 * 创建时间:2012-8-1 下午05:42:01
	 */
	public static String parseGroupNumber(DocNumberStrategy strategy){
		String str=strategy.getContent();
		String number="";
		if(str!=null){
			if(str.indexOf("_")!=-1){
				number=str.split("_")[1];
			}
		}
		if(number.equals("0")){
			return "<input id='"+parse2Id(strategy.getStrategyId())+"' type='text' style='width:40px' />";
		}
		return "<input id='"+parse2Id(strategy.getStrategyId())+"' maxlength='"+number+"' type='text' style='width:40px' />";
	}
	/**
	 * 
	 * 描述：TODO 解析字号数组定长策略的字号数组<br>
	 *
	 * @param strategy
	 * @return String
	 *
	 * 作者:tkj<br>
	 * 创建时间:2012-8-1 下午05:44:34
	 */
	public static String parseGroupGidzs(DocNumberStrategy strategy){
		String str=strategy.getContent();
		String gidzs="";
		if(str!=null&&str.indexOf("_")!=-1){
		   String [] strArray=str.split("_")[0].split(",");
		   for(int i=0;i<strArray.length;i++){
			   if(i==strArray.length-1){
				   gidzs+="'"+strArray[i]+"'";
			   }else{
			   gidzs+="'"+strArray[i]+"',";
			   }
		   }
		}
		return gidzs;
	}
	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
	 *
	 * @param obj
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:23:31
	 */
	public static String parseBySelf(Object obj){
		return "<span>"+obj.toString()+"</span>";
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
}
