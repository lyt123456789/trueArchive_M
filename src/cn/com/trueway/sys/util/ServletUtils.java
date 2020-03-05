package cn.com.trueway.sys.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class ServletUtils {

	public static void output(HttpServletResponse response, String content) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 组织返回值，支持jsoup
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	public static void getJsonpOutput(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		String callback =request.getParameter("callback");
		String msg = jsonObject.toString();
		if(StringUtils.isNotBlank(callback)){
			msg=";"+callback+"("+msg+")";
		}
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			out = response.getWriter();
			out.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
	public static void getJsonpOutputArray(HttpServletRequest request, HttpServletResponse response, JSONArray jsonArray){
		if(jsonArray == null){
			return;
		}
		String callback =request.getParameter("callback");
		String msg = jsonArray.toString();
		if(StringUtils.isNotBlank(callback)){
			msg=";"+callback+"("+msg+")";
		}
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			out = response.getWriter();
			out.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
	public static void outWirter(JSONArray data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin","*");  
		response.setHeader("Access-Control-Allow-Credentials","true");  
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}
	
	public static Map<String,Object> getParams(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		for(Object key : request.getParameterMap().keySet()) {
			map.put((String)key, request.getParameter((String)key));
		}
		
		return map;
	}
	
	public static void putParamToAttr(HttpServletRequest request, String ... names) {
		for(String name : names) request.setAttribute(name, request.getParameter(name));
	}
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}
}
