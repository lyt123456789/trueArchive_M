package cn.com.trueway.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCatcher {
	public static void main(String[] args) {
		UrlCatcher u=new UrlCatcher();
		
		String str="";
		str+="<INPUT type=hidden name=receiveBureauGUID>";
		str+="<INPUT class=displayTextInput_LONGER id=COMPANY_EMAIL name=COMPANY_EMAIL>";
		str+="<INPUT class=displayTextInput_LONG name=APPLICANT>";
		str+="<INPUT class=displayTextInput_LONG name=APPLICANT>";
		
		
		{
		String reg_input="<INPUT[^<]*>";//input类型
		String[] inputs=u.getStringByRegEx(str, reg_input, true);
		
		String reg_type="name=[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+";
		String[] types=u.getStringByRegEx(inputs[0], reg_input, true);
		}
	}
	/**
	 * 
	 * @Title: getStringByRegEx
	 * @Description:  根据正则返回符合条件的字符串数组
	 * @param srcString	需要匹配的字符串
	 * @param regEx		匹配的正则表达式
	 * @param isCase	是否忽略大小写匹配
	 * @return String    返回类型
	 */
	public String[] getStringByRegEx(String srcString,String regEx,boolean isCase){
		String[] strArray=null;
		ArrayList<String> list=new ArrayList<String>();
		Pattern p=null;
		if (isCase) {
			p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		}else {
			p=Pattern.compile(regEx);
		}
		Matcher m=p.matcher(srcString);
		//必须使用如下方式才能获得匹配的字符串(先执行m.find()后执行m.group())
		while (m.find()) {
			list.add(m.group());
		}
		if (list.size()>0) {
			strArray=new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				strArray[i]=list.get(i);
			}
		}
		return strArray;
	}
	/**
	 * 
	 * @Title: excute
	 * @Description: 	获取某个网页的信息
	 * @param url		网页地址
	 * @param encode	网页采用的编码,默认采用gbk
	 * @return String   返回类型
	 */
	public String excute(String url,String encode){
		//存储返回字符串
		StringBuffer sb=new StringBuffer();
		URL url2=null;
		HttpURLConnection con=null;
		try {
			//如果是内部网,还需要专门给它加上代理, Java以特殊的系统属性为代理服务器提供支持
			//System.getProperties().setProperty("http.proxyHost", proxyName );
			//System.getProperties().setProperty( "http.proxyPort", port );
			url2=new URL(url);
			//简单使用
			//BufferedReader br = new BufferedReader(newInputStreamReader(url.openStream()));
			//复杂使用,防止页面代码转向其它页面
			con=(HttpURLConnection)url2.openConnection();
//			HttpURLConnection.setFollowRedirects(true);
//			con.setInstanceFollowRedirects(false);
//			con.connect();
			con.setDoOutput(true);
//			OutputStreamWriter out=new OutputStreamWriter(con.getOutputStream());
//			out.write(sb.toString());
//			out.flush();
			//默认采用gbk编码
			if (encode==null||encode.equals("")) {
				encode="gbk";
			}
			BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),encode));
			String s="";
			while ((s=br.readLine())!=null) {
				sb.append(s);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (con!=null) {
				con.disconnect();
			}
		}
		return sb.toString();
	}
	
	public String[] getStringByRegEx(String srcString,String regEx,int type){
		String[] strArray=null;
		ArrayList<String> list=new ArrayList<String>();
		Pattern p=null;
		if (type == 3) {
			p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.DOTALL);
		}else if (type == 2){
			p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
			
		}else if (type == 1){
			p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		}else{
			p=Pattern.compile(regEx);
		}
		Matcher m=p.matcher(srcString);
		//必须使用如下方式才能获得匹配的字符串(先执行m.find()后执行m.group())
		while (m.find()) {
			list.add(m.group());
		}
		if (list.size()>0) {
			strArray=new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				strArray[i]=list.get(i);
			}
		}
		return strArray;
	}
}
