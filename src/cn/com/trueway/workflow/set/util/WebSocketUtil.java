package cn.com.trueway.workflow.set.util;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


/**
 * 
 * 描述：webSocketUtil推送消息
 * 作者：蔡亚军
 * 创建时间：2015-6-9 上午9:13:37
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class WebSocketUtil {
	
	public static String WEB_SOCKET_URL = SystemParamConfigUtil.getParamValueByParam("pushSysUrl");//推送系统地址，不要以斜杠结尾

	public static String APP_MARK 		= SystemParamConfigUtil.getParamValueByParam("appMark");//本系统在推送辅助系统中的唯一标志
	
	/**
	 * 日志信息
	 */
	private Logger LOGGER 				= Logger.getLogger(this.getClass());
	
	/**
	 * 
	 * 描述：推送计数递减
	 * 作者:蔡亚军
	 * 创建时间:2015-6-9 上午9:22:12
	 * @throws JSONException 
	 */
	public void delBadge(String userId, String num, String changeNum) throws JSONException{
		String actionUrl = WEB_SOCKET_URL + "/push_updatePushDel.do";
		try {
			NameValuePair[] param={new NameValuePair("appMark",APP_MARK), new NameValuePair("userId",userId), 
					new NameValuePair("num",num), new NameValuePair("changeNum",changeNum)};
			String result = doPost(actionUrl, param);
			JSONObject backJSON = new JSONObject(result);
			if(null != backJSON && backJSON.getString("result").equals("true")){
				LOGGER.info("webSocket消息数递减接口,访问成功");
			}else{
				LOGGER.info("webSocket消息数递减接口,访问失败");
			}
		} catch (JSONException e) {
			LOGGER.error("json转换异常");
		}
	}
	
	/** 
	 * apnsPush:(消息推送). <br/> 
	 * 
	 * @author adolph.jiang
	 * @param text			//推送内容
	 * @param fromUserId	//推送人id
	 * @param isAdd			//消息递增还是递减（1,递增;0,递减）
	 * @param num			//递增或递减的数目
	 * @param changeNum		//替换的数字，一般用不到
	 * @param toUserIds		//接收人id
	 * @return
	 * @throws JSONException 
	 * @since JDK 1.6 
	 */
	public boolean apnsPush(String text, String fromUserId, String isAdd, String num, String changeNum, String toUserIds) throws JSONException{
		String zwCom_msg = SystemParamConfigUtil.getParamValueByParam("zwCom_msg");
		if(StringUtils.isNotBlank(zwCom_msg) && StringUtils.isNotBlank(text)){
			text = zwCom_msg.replace("{title}", text).replace("{type}", "待办公文");
		}
		
		String actionUrl = WEB_SOCKET_URL + "/push_push.do";
		
		System.out.println(">>>>>>>>>>>>>>>>apnsPush.actionUrl="+actionUrl);
		try {
			NameValuePair[] param={new NameValuePair("fromUserId",fromUserId), new NameValuePair("toUserIds",toUserIds), new NameValuePair("appMark",APP_MARK),
					new NameValuePair("text",text),new NameValuePair("isAdd",isAdd),new NameValuePair("num",num),new NameValuePair("changeNum",changeNum)};
			String result = doPost(actionUrl, param);
			System.out.println(">>>>>>>>>>>>>>>>apnsPush.result="+result);
			JSONObject backJSON = new JSONObject(result);
			if(null != backJSON && backJSON.getString("result").equals("true")){
				return true;
			}else{
				LOGGER.error("json转换异常");
			}
		} catch (JSONException e) {
			LOGGER.error("json转换异常");
		}
		return false;
	}
	
	//------------------以下是私有方法，你不需要知道它是干嘛的-----------------------
	private String doPost(String url, NameValuePair ... param) throws JSONException{
		System.out.println(">>>>>>>>>>>>>>>>doPost.url="+url);
		
		String result = "";
		HttpClient client=new HttpClient();
		HttpMethod method = getPostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		((PostMethod)method).setRequestBody(param);
		try {
			client.executeMethod(method);
			result=method.getResponseBodyAsString();
		} catch (HttpException e) {
			LOGGER.error("{} 接口请求失败，e:"+e);
		} catch (IOException e) {
			LOGGER.error("{} 接口请求失败，e:"+e);
		}finally{
			method.releaseConnection();
		}
		
		return result;
	}
	
	private HttpMethod getPostMethod(String url){
		PostMethod post=new PostMethod(url);
		return post;
	}
}
	