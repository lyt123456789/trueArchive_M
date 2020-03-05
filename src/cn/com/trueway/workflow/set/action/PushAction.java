package cn.com.trueway.workflow.set.action;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.PushEntity;
import cn.com.trueway.workflow.set.service.PushService;
import cn.com.trueway.workflow.set.util.HttpRequest;

/**
 * ClassName: PushAction <br/>
 * date: 2016年4月19日 上午11:11:48 <br/>
 *
 * @author adolph_jiang
 * @version 
 * @since JDK 1.6
 */
public class PushAction extends BaseAction{

	private static final long	serialVersionUID 		= -8230730099860869252L;

	private static final String	WEBSOCKET_URL_ANDROID 	= SystemParamConfigUtil.getParamValueByParam("webSocketPushUrl")+"/1/admin/push/private?key=TruewayIM-Android-0&expire=300";
	
	private static final String	WEBSOCKET_URL_APNS 		= SystemParamConfigUtil.getParamValueByParam("webSocketPushUrl")+"/1/admin/push/private?key=TruewayIM-APNS-0&expire=300";
	
	private static final String WEBSOCKET_URL_HUAWEI	= SystemParamConfigUtil.getParamValueByParam("webSocketPushUrl")+"/1/admin/push/private?key=IM-Android-Huawei&expire=300";
	
	private static final Logger LOGGER 					= LoggerFactory.getLogger(PushAction.class);
	
	private PushService 		pushService;
	
	public PushService getPushService() {
		return pushService;
	}

	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}

	/** 
	 * addPush:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void addPush(){
		String _token = getRequest().getParameter("token");
		if(StringUtils.isNotBlank(_token)){
			String token = _token.replace("<", "").replace(">", "").replace(" ", "");
			if(token.length() == 32 || token.length() == 64){
				String loginName = getRequest().getParameter("loginName");
				String isApple = getRequest().getParameter("isApple");
				String userId = getRequest().getParameter("userId");
				PushEntity entity = new PushEntity();
				entity.setToken(token);
				entity.setLoginName(loginName);
				entity.setIsApple(isApple);
				entity.setUserId(userId);
				try {
					List<PushEntity> list  = pushService.getPushDateNew(token);
					if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(isApple)){
						if (null != list && list.size()>0) {
							pushService.deleteByToken(token);
							entity.setBadgeWf("1");
							pushService.add(entity);
							toPage("{\"result\":\"true\"}");
						}else{
							entity.setBadgeWf("1");
							pushService.add(entity);
							toPage("{\"result\":\"true\"}");
						}
					}else{
						LOGGER.error(getTraceInfo()+"token,loginName,isApple 不能为空");
						toPage("{\"result\":\"false\"}");
					}
				} catch (Exception e) {
					toPage("{\"result\":\"false\"}");
					e.printStackTrace();
					LOGGER.error("内部异常" + e+":"+getTraceInfo());
				}
			}else{
				toPage("{\"result\":\"false\"}");
			}
		}else{
			toPage("{\"result\":\"false\"}");
		}
	}
	
	/** 
	 * delPush:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void delPush(){
		String _token = getRequest().getParameter("token");
		if(StringUtils.isNotBlank(_token)){
			String token = _token.replace("<", "").replace(">", "").replace(" ", "");
			String loginName = getRequest().getParameter("loginName");
			String isApple = getRequest().getParameter("isApple");
			PushEntity entity = new PushEntity();
			entity.setToken(token);
			entity.setLoginName(loginName);
			entity.setIsApple(isApple);
			try {
				List<PushEntity> list  = pushService.getPushDate(loginName, token);
				if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(isApple)){
					if (null != list && list.size()>0) {
						pushService.delete(entity);
						toPage("{\"result\":\"true\"}");
					}else{
						LOGGER.error(getTraceInfo()+"数据不存在");
						toPage("{\"result\":\"false\"}");
					}
				}else{
					LOGGER.error(getTraceInfo()+"token,loginName,isApple 不能为空");
					toPage("{\"result\":\"false\"}");
				}
			} catch (Exception e) {
				toPage("{\"result\":\"false\"}");
				e.printStackTrace();
				LOGGER.error("内部异常" + e + ":"+getTraceInfo());
			}
		}else{
			toPage("{\"result\":\"false\"}");
		}
		
	}
	
	/** 
	 * updateSubtract:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void updateSubtract(){
		try {
			String id = getRequest().getParameter("id");
			List<PushEntity> list =  pushService.getPushDateByUserId(id);
			for (PushEntity pushEntity : list) {
				String badge = pushEntity.getBadgeWf();
				int _badge = 0;
				if (StringUtils.isNotBlank(badge)) {
					_badge = Integer.parseInt(badge)-1;
					if (_badge < 0) {
						_badge = 0;
					}
				}
				pushEntity.setBadgeWf(_badge + "");
				pushService.update(pushEntity);
				toPage("{\"result\":\"true\"}");
			}
		} catch (Exception e) {
			LOGGER.error("内部异常"+e+":"+getTraceInfo());
			toPage("{\"result\":\"false\"}");
			
		}
	}
	
	/** 
	 * updateAdd:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void updateAdd(){
		try {
			String id = getRequest().getParameter("id");
			List<PushEntity> list =  pushService.getPushDateByUserId(id);
			for (PushEntity pushEntity : list) {
				String badge = pushEntity.getBadgeWf();
				int _badge = 0;
				if (StringUtils.isNotBlank(badge)) {
					_badge = Integer.parseInt(badge);
					if (_badge < 0) {
						_badge = 1;
					}else{
						_badge = _badge + 1;
					}
				}
				
				pushEntity.setBadgeWf(_badge + "");
				pushService.update(pushEntity);
				toPage("{\"result\":\"true\"}");
			}
		} catch (Exception e) {
			LOGGER.error("内部异常"+e+":"+getTraceInfo());
			toPage("{\"result\":\"false\"}");
		}
	}
	
	/** 
	 * push:(添加方法描述). <br/> 
	 * 
	 * @author adolph_jiang 
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public void push() throws HttpException{
		String id = getRequest().getParameter("id");
		String text = getRequest().getParameter("text");
		String url = "";
		List<PushEntity> list =  pushService.getPushDateByUserId(id);
		for (PushEntity pushEntity : list) {
			String isApple = pushEntity.getIsApple();
			if (StringUtils.isNotBlank(isApple)) {
				if ("1".equals(isApple)) {
					url = WEBSOCKET_URL_APNS;
				} else if ("2".equals(isApple)){
					url = WEBSOCKET_URL_ANDROID;
				} else if ("3".equals(isApple)){
					url = WEBSOCKET_URL_HUAWEI;
				} else{
					LOGGER.error(getTraceInfo()+"参数错误,isApple参数不符合规则");
				}
			}else{
				LOGGER.error(getTraceInfo()+"参数错误,isApple参数不能为空");
			}
			String param = "{\"badge\":"+pushEntity.getBadgeWf()+",\"token\":\""+pushEntity.getToken()+"\",\"alert\":\""+text+"\"}";
			
			if(StringUtils.isNotBlank(url)){
				String __result = HttpRequest.sendPost(url, param);
			
				try {
					String _result = __result.replace("\n", "");
					String result = _result.replace(" ", "");
					String [] strs = result.split(":");
					String code = "";
					if (strs.length>1) {
						code = strs[1].substring(0,strs[1].length()-1);
					}else{
						code = "-1";
					}
					if("0".equals(code)){
						toPage("{\"result\":\"true\"}");
					}else if("65534".equals(code)){
						LOGGER.error(getTraceInfo()+"参数错误,code = "+code);
						toPage("{\"result\":\"false\"}");
					}else if("65535".equals(code)){
						LOGGER.error(getTraceInfo()+"内部错误,code = "+code);
						toPage("{\"result\":\"false\"}");
					}else if("-1".equals(code)){
						LOGGER.error(getTraceInfo()+"json格式错误,code = "+code);
						toPage("{\"result\":\"false\"}");
					}
				} catch (Exception e) {
					e.printStackTrace();
					toPage("{\"result\":\"false\"}");
				}
			}else{
				LOGGER.error("为配置推送地址，无法请求推送服务。");
				toPage("{\"result\":\"false\"}");
			}
		}
	}
	
	/**
	 * 描述：至验证推送服务器页面
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-1-18 上午9:02:10
	 */
	public String toCheckPushServer(){
		return "checkPushServer";
	}
	
	/**
	 * 描述：验证推送服务器
	 * 作者:蒋烽
	 * 创建时间:2017-1-18 上午9:42:46
	 */
	public void checkPushServer(){
		String ip = getRequest().getParameter("ip");
		String port = getRequest().getParameter("port");
		String token = getRequest().getParameter("token");
		Integer timeout = StringUtils.isNotBlank(getRequest().getParameter("timeout"))?
				Integer.parseInt(getRequest().getParameter("timeout")):5;
		String url = "";
		String title = SystemParamConfigUtil.getParamValueByParam("webTitle");
		if(StringUtils.isBlank(title)){
			title = "推送测试";
		}else{
			title += ",推送测试";
		}
		String result = "";
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(ip)){
			jsonObject.put("result", "false");
			jsonObject.put("code", "10001");
			toPage(jsonObject.toString());
		}else if(StringUtils.isBlank(port)){
			jsonObject.put("result", "false");
			jsonObject.put("code", "10002");
			toPage(jsonObject.toString());
		}else if (StringUtils.isBlank(token)) {
			jsonObject.put("result", "false");
			jsonObject.put("code", "10003");
			toPage(jsonObject.toString());
		}else{
			String [] str = ip.split("\\.");
			if(str.length != 4){//ip不合法
				jsonObject.put("result", "false");
				jsonObject.put("code", "10004");
				toPage(jsonObject.toString());
			}else{
				boolean checkIP = isHostReachable(ip, timeout*1000);//测试ip是否可以联通
				if(checkIP){
					boolean flag = isHostConnectable(ip, Integer.parseInt(port));//测试端口是否可以联通
					if(flag){
						if(token.length() == 32){//长度为32，是安卓终端的token
							url = getRequest().getScheme()+"://"+ip+":"+port+"/1/admin/push/private?key=TruewayIM-Android-0&expire=300";
							result = post(url, title, token);
							if(StringUtils.isBlank(result) || (StringUtils.isNotBlank(result) && result.equals("error"))){
								jsonObject.put("result", "false");
								jsonObject.put("code", "10006");
								toPage(jsonObject.toString());
							}else{
								jsonObject.put("result", "true");
								jsonObject.put("code", result);
								toPage(jsonObject.toString());
							}
						}else if(token.length() == 64){//长度为32，是苹果终端的token
							url = getRequest().getScheme()+"://"+ip+":"+port+"/1/admin/push/private?key=TruewayIM-APNS-0&expire=300";
							result = post(url, title, token);
							if(StringUtils.isBlank(result) || (StringUtils.isNotBlank(result) && result.equals("error"))){
								jsonObject.put("result", "false");
								jsonObject.put("code", "10006");
								toPage(jsonObject.toString());
							}else{
								jsonObject.put("result", "true");
								jsonObject.put("code", result);
								toPage(jsonObject.toString());
							}
						}else{//token不合法
							jsonObject.put("result", "false");
							jsonObject.put("code", "10005");
							toPage(jsonObject.toString());
						}
					}else{
						jsonObject.put("result", "false");
						jsonObject.put("code", "10007");
						toPage(jsonObject.toString());
					}
				}else{
					jsonObject.put("result", "false");
					jsonObject.put("code", "10008");
					toPage(jsonObject.toString());
				}
			}
		}
	}
	
	/**
	 * 描述：模拟post请求
	 * @param strURL
	 * @param title
	 * @param token
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-3-23 上午11:43:18
	 */
	private String post(String strURL, String title, String token) {
    	HttpClient client=new HttpClient();
		HttpMethod method = getPostMethod(strURL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		NameValuePair[] param={new NameValuePair("badge","1"),new NameValuePair("token",token),new NameValuePair("alert",title),};
		((PostMethod)method).setRequestBody(param);
		try {
			client.executeMethod(method);
			return method.getResponseBodyAsString();
		} catch (org.apache.commons.httpclient.HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }  
    
    /**
     * 描述：判断IP、端口是否可连接
     * @param host
     * @param port
     * @return boolean
     * 作者:蒋烽
     * 创建时间:2017-1-18 上午11:02:23
     */
    private boolean isHostConnectable(String host,int port){
    	Socket socket = new Socket();
    	try {
			socket.connect(new InetSocketAddress(host, port));
		} catch (IOException e) {
			LOGGER.error(e+":"+getTraceInfo());
			return false;
		}finally{
			try {
				socket.close();
			} catch (IOException e2) {
				LOGGER.error(e2+":"+getTraceInfo());
			}
		}
    	return true;
    }
    
    /**
     * 描述：判断IP是否可连接
     * @param host
     * @param timeOut
     * @return boolean
     * 作者:蒋烽
     * 创建时间:2017-1-18 上午11:17:31
     */
    public boolean isHostReachable(String host, Integer timeOut){
    	try {
			return InetAddress.getByName(host).isReachable(timeOut);
		} catch (UnknownHostException e) {
			LOGGER.error(e+":"+getTraceInfo());
		} catch (IOException e) {
			LOGGER.error(e+":"+getTraceInfo());
		}
    	return false;
    }
    
    /**
     * 描述：
     * @return String
     * 作者:蒋烽
     * 创建时间:2017-1-18 上午11:33:26
     */
    public String getTraceInfo(){
    	StringBuffer sb = new StringBuffer();
    	StackTraceElement[] stacks = new Throwable().getStackTrace();
    	sb.append("class: ").append(stacks[1].getClassName()).append("; method: ").append(stacks[1].getMethodName()).append("; number: ").append(stacks[1].getLineNumber());
    	return sb.toString();
    }
    
	private  HttpMethod getPostMethod(String url){
		PostMethod post=new PostMethod(url);
		return post;
	}
}
