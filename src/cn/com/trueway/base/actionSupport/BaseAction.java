/**
 * 文件名称:BaseAction.java
 * 作者:吴新星<br>
 * 创建时间:2011-11-29 下午08:54:30
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.actionSupport;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 描述：BaseAction
 * 作者：吴新星<br>
 * 创建时间：2011-11-29 下午08:54:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class BaseAction  extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	public BaseAction(){} 
	
	/**
	 * 获取HttpServletRequest对象
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获取HttpServletResponse对象
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse respones = ServletActionContext.getResponse();
		respones.setCharacterEncoding("UTF-8");
		return respones;
	}

	/**
	 * 获取HttpSession对象
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}
	
	/**
	 * 
	 * 描述：获取SpringContext
	 *
	 * @return WebApplicationContext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 下午10:35:06
	 */
	public WebApplicationContext getSpringContext(){
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		return webApplicationContext;
	}
	
	synchronized static public void renderResponse(HttpServletResponse response,String msg) throws IOException {
		if (response != null) {
			response.setContentType("text/xml;UTF-8");
			response.setContentType("<MEAT HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"/>");                
            if(msg!=null) {                    
            	response.getWriter().print(msg);
            }else {
              	System.out.println("msg is null");
            }
        }else {
          	System.out.println("response is null");
        }
    }
	
	/** 
	 * toPage:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void toPage(String str){
		PrintWriter out = null;
		try {
			getResponse().setCharacterEncoding("UTF-8");
			out =  getResponse().getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void outWirter(String data){
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setHeader("Access-Control-Allow-Origin","*");  
		getResponse().setHeader("Access-Control-Allow-Credentials","true");  
		getResponse().setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
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
	
	public void outWirter(JSONArray data,HttpServletResponse respone){
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setHeader("Access-Control-Allow-Origin","*");  
		getResponse().setHeader("Access-Control-Allow-Credentials","true");  
		getResponse().setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = respone.getWriter();
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
}
