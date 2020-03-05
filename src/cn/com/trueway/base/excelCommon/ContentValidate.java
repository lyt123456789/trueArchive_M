package cn.com.trueway.base.excelCommon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentValidate {
	/**
	 * 检查是否为空 字符串,空：true,不空:false
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkNull(String value) {
		return value == null || "".equals(value.trim());
	}
	
	public static boolean checkMyNull(String value) {
		return value == null || "".equals(value.trim()) || "undefined".equals(value.trim());
	}

	/**
	 * 校验邮箱
	 * @param mobiles
	 * @return
	 */
	public static boolean isEmail(String email) {
		if(email == null) return false;
		   else 	
		return Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$").matcher(email).matches();
	}
	
	/** 
	* 检查电话输入 是否正确 
	* 正确格 式 012-87654321、0123-87654321、0123－7654321 
	* @param value 
	* @return 
	*/
	public static boolean checkTel(String value) {
		String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";  
        return Pattern.matches(reg, value); 
	}

	/** 
	* 检查手机输入 是否正确 
	* 
	* @param value 
	* @return 
	*/
	public static boolean checkMobile(String value) {
		return value.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$");
	}
	
	/**
	 * 日期校验
	 */
	public static boolean checkDateFormat(String date) {
		String rexp = "^(((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))|"  
                + "((\\d{2}(([02468][048])|([13579][26]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|(1[0-9])|(2[0-8])))))))";  
        Pattern p = Pattern.compile(rexp);  
        Matcher startM = p.matcher(date);
        return startM.matches();
	}
	
}
