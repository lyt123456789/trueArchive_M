package cn.com.trueway.base.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

public class CommonUtil {
	/**
	 * 
	 * @Title: getNowTimeString
	 * @Description: 获得当前时间(返回字符串)
	 * @param patten
	 * @return String    
	 */
	public static String getNowTimeString(String patten){
		SimpleDateFormat sdf=new SimpleDateFormat(patten==null?"yyyy-MM-dd":patten);
		return sdf.format(new Date());
	}
	/**
	 * 
	 * @Title: getNowTimeTimestamp
	 * @Description: 获得当前时间(返回timestamp)
	 * @param patten
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getNowTimeTimestamp(String patten){
		SimpleDateFormat sdf=new SimpleDateFormat(patten==null?"yyyy-MM-dd HH:mm:ss":patten);
		return Timestamp.valueOf(sdf.format(new Date()));
	}
	/**
	 * 
	 * @Title: stringNotNULL
	 * @Description: 工具，判断字符非空
	 * @param s
	 * @return boolean    返回类型
	 */
	public static boolean stringNotNULL(String s){
		if (s!=null&&!s.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Title: stringNotNULL
	 * @Description: 工具，判断字符非空
	 * @param s
	 * @return boolean    返回类型
	 */
	public static boolean stringIsNULL(String s){
		if (s==null||s.equals("")) {
			return true;
		}
		return false;
	}
	
	public static String ToString(Object o){
		if(o==null){
			return "";
		}else{
			return o.toString();
		}
		
	}
	
	/**
	 * 截取掉最后一个字符串
	 * @return
	 */
	public static String removeLastStr(String str){
		if(str==null || str.equals("")){
			return "";
		}else{
			return str.substring(0, str.length() - 1);
		}
	}
	
	
	/**
	 * 
	 * 描述：将form表单值格式化
	 * @param value2
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-8-19 下午3:09:48
	 */
	public static String formatStr(String value2){
		String value1 = "";
		String[] valses=value2.split(";");
		String valuess="";
		for(int i=0;i<valses.length;i++){
			if(i>0){
				valuess +=";";
			}
			String[] str = valses[i].split(":");
			String val = valses[i].split(":")[0];
			valuess+=val+"\":\"";
			if(str.length>1){
				valuess +=valses[i].replace(val+":", "");
			}
		}
		value1=value1.replaceAll(":","\":\"").replaceAll(";","\";\"");
		value1=value1.substring(0,value1.lastIndexOf(";"));
		value1=value1.replaceAll("\"\"", "\"---///\"");
		value2=valuess.replaceAll(";","\";\"");
		value2=value2.substring(0,value2.lastIndexOf(";"));
		value2=value2.replaceAll("\"\"", "\"---///\"");
		JSONObject js= JSONObject.fromObject("{\""+value1+"}");
		JSONObject js1= JSONObject.fromObject("{\""+value2+"}");
		Iterator it = js.keys();
		List<String> keyList = new ArrayList<String>();
		while(it.hasNext()){
            keyList.add(it.next().toString());
        }
		JSONObject js3=new JSONObject();
		for(int i=0;i<keyList.size();i++){
			if(js.get(keyList.get(i))==null||"---///".equals(js.get(keyList.get(i)))||"".equals(js.get(keyList.get(i)))){
				js3.put(keyList.get(i).toLowerCase(), js1.get(keyList.get(i).toLowerCase())==null?(js1.get(keyList.get(i).toUpperCase())==null?"---///":js1.get(keyList.get(i).toUpperCase())):js1.get(keyList.get(i).toLowerCase()));
			}else{
				js3.put(keyList.get(i).toLowerCase(), js.get(keyList.get(i))==null?"---///":js.get(keyList.get(i)));
			}
		}
		value1 = js3.toString().replace("{", "").replace("}", "");
		value1=value1.replaceAll("\"---///\"", "\"\"");
		value1 = value1.replaceAll("\",\"", ";").replaceAll("\"", "")+";";
		return value1;
	}
	
	
	public static String defaultIfBlank(String str){
		if(str!=null){
			return str;
		}
		return "";
		
	}
	
	public static String getFourNumberString(Integer number){
		String numberStr="";
		if(number<10){
			numberStr = "000"+number;
		}else if(number>=10&&number<100){
			numberStr= "00"+number;
		}else if(number>=100&&number<1000){
			numberStr = "0"+number;
		}else{
			numberStr = number +"";
		}
		return numberStr;
	}
}
