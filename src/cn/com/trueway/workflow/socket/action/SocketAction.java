package cn.com.trueway.workflow.socket.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.workflow.socket.service.SocketService;

/** 
 * ClassName: SocketAction <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年4月12日 上午11:07:06 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public class SocketAction extends BaseAction {

	private static final long serialVersionUID = -1779133948555446517L;
	
	private SocketService socketService;
	
	public SocketService getSocketService() {
		return socketService;
	}

	public void setSocketService(SocketService socketService) {
		this.socketService = socketService;
	}

	/** 
	 * isThisStepOver:(这里用一句话描述这个方法的作用). <br/> 
	 * 
	 * @author adolph.jiang
	 *  
	 * @since JDK 1.6 
	 */
	public void isThisStepOver(){
		String params = "";
		JSONObject json = getJSONObject();
		if(null != json){
			params = null != json.get("params")?json.get("params").toString():"";
		}else{
			params = getRequest().getParameter("params");
		}
		
		JSONObject obj = new JSONObject();
		if(StringUtils.isNotBlank(params)){
			JSONArray arr = socketService.checkIsOver(params);//过程信息
			obj.put("result", "true");
			obj.put("data", arr);
		}else{
			obj.put("result", "false");
			obj.put("message", "params是必传参数，不能为空。");
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
}
