package cn.com.trueway.workflow.core.action;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.workflow.set.util.ServerMAC;

public class MobileInterfaceAction extends BaseAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3950196093148047266L;

	public void getServerInfo(){
		// 获取mac 地址
		// 获取服务器时间
		PrintWriter out = null;
		String result ="";
		try{
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
			String macPath = ServerMAC.getMACAddress(ia);
			//String macPath = "FC-4D-D4-36-7C-FC";
			result = "{\"date\":\""+date+"\",\"mac\":\""+macPath+"\"}";
		}catch(Exception e){
		}
		out.print(JSONObject.fromObject(result));
		out.close();
	
	}
}
