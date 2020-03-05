package cn.com.trueway.workflow.set.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

/**
 * 描述：TODO 对SendMsgUtil进行描述
 * 作者：蒋烽
 * 创建时间：2017-2-20 下午3:53:31
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class SendMsgUtil {
	
	private static final String SMS_URL = SystemParamConfigUtil.getParamValueByParam("smsUrl");
	
	private static final Logger LOGGER 	= LoggerFactory.getLogger(SendMsgUtil.class);
	
	/**
	 * 描述：发送短信
	 * @param url ip和端口
	 * @param numbers 被发送人的电话号码
	 * @param content	短信内容
	 * @param senderName 发送人的姓名
	 * 作者:蒋烽
	 * 创建时间:2017-2-20 下午3:53:54
	 */
	public void sendMsg( String numbers, String senderName, Map<String, String> map){
		HttpClient client = new HttpClient();
		try {
			String smsModel = SystemParamConfigUtil.getParamValueByParam("smsModel");
			String doBackModel = SystemParamConfigUtil.getParamValueByParam("doBackModel");
			String pendingModel = SystemParamConfigUtil.getParamValueByParam("pendingModel");
			String itemName = map.get("itemName");
			String sendUserName = map.get("sendUserName");
			String title = map.get("title");
			String msg = map.get("msg");
			String isDoBack = map.get("isDoBack");
			String isDoPend = map.get("isDoPend");
			String isDuBan = map.get("isDuBan");
			String senderId = map.get("senderId");
			String receiverName = map.get("receiverName");
			String intervalDate = map.get("intervalDate");
			String content = "";
			if(StringUtils.isNotBlank(title)){
				if(title.length()>20){
					title = title.substring(0,20)+"...";
				}
			}
			if(StringUtils.isBlank(receiverName)){
				receiverName="";
			}
			
			if(StringUtils.isNotBlank(isDoBack) && isDoBack.equals("1")){
				if(StringUtils.isNotBlank(doBackModel)){
					content = doBackModel.replace("{user}", sendUserName).replace("{title}", title);;
				}else{
					LOGGER.error(getTraceInfo() + "====参数异常:未配置短信模板====" );
					return;
				}
			}else if(StringUtils.isNotBlank(isDoPend) && isDoPend.equals("1")){
				//长期未办件模板
				if(StringUtils.isNotBlank(pendingModel)){
					content = pendingModel.replace("{intervalDate}", intervalDate).replace("{title}", title);
				}else{
					LOGGER.error(getTraceInfo() + "====参数异常:未配置短信模板====" );
					return;
				}
			}else if(StringUtils.isNotBlank(isDuBan) && isDuBan.equals("1")){//督办短信发送
				if(StringUtils.isNotBlank(msg)){
					content = msg;
				}else{
					LOGGER.error(getTraceInfo() + "====参数异常:短信内容为空====" );
					return;
				}
			}else{
				if(StringUtils.isNotBlank(smsModel)){
					content = smsModel.replace("{user}", sendUserName).replace("{itemName}", itemName).replace("{title}", title);
				}else{
					LOGGER.error(getTraceInfo() + "====参数异常:未配置短信模板====" );
					return;
				}
			}
			
			
			if(StringUtils.isNotBlank(content)){
				System.out.println(">>>>>>>>>>>>>>>>>"+content);
				content = URLEncoder.encode(content, "UTF-8");
				senderName = URLEncoder.encode(senderName, "UTF-8");
				receiverName = URLEncoder.encode(receiverName, "UTF-8");
				String url = SMS_URL+"/trueOA/sms_sendMsgToSms.do";
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>SendMsgUtil.sendMsg---url:"+url);
				String params = "numbers;"+numbers+"###content;"+content+"###senderName;"+senderName+"###TimeInMillis;"+(new Date()).getTime()+"###senderId;"+senderId+"###receiverName;"+receiverName;
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>SendMsgUtil.sendMsg---params:"+params);
				HttpMethod method = getPostMethod(url,params);
				client.executeMethod(method);
				method.getResponseBodyAsString();
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		} catch (HttpException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		} catch (IOException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		}
	}
	
	/**
	 * @param numbers
	 * @param senderName
	 * @param title
	 */
	public void sendMsg( String numbers, String senderName, String title){
		HttpClient client = new HttpClient();
		try {
			String smsModel = SystemParamConfigUtil.getParamValueByParam("smsModel4CB");
			String content = smsModel;
			if(StringUtils.isNotBlank(title)){
				if(title.length()>20){
					title = title.substring(0,20)+"...";
				}
			}
			if(StringUtils.isNotBlank(content)){
				String sendContent = content.replace("{title}", title);
				System.out.println(">>>>>>>>>>>>>>>>>"+sendContent);
				sendContent = URLEncoder.encode(sendContent, "UTF-8");
				senderName = URLEncoder.encode(senderName, "UTF-8");
				String url = SMS_URL+"/trueOA/sms_sendMsgToSms.do";
				String params = "numbers;"+numbers+"###content;"+sendContent+"###senderName;"+senderName+"###TimeInMillis;"+(new Date()).getTime();
//				HttpMethod method = getPostMethod(url,params);
//				client.executeMethod(method);
//				method.getResponseBodyAsString();
			}else{
				LOGGER.error(getTraceInfo() + "====参数异常:未配置短信模板====" );
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		}/* catch (HttpException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		}*/ catch (IOException e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
		}
	}
	
	private  HttpMethod getPostMethod(String url, String param){
		try {
			PostMethod post=new PostMethod(url);
			if(StringUtils.isNotBlank(param)){
				String [] params = param.split("###");
				for (String string : params) {
					String [] keyVa = string.split(";");
					if(keyVa.length>1){
						post.addParameter(keyVa[0], keyVa[1]);
					}
				}
			}
			return post;
		} catch (Exception e) {
			LOGGER.error(getTraceInfo() + "e:" + e);
			return null;
		}
	}
	
    /**
     * 描述：TODO 对此方法进行描述
     * @return String
     * 作者:蒋烽
     * 创建时间:2017-1-18 上午11:33:26
     */
    private String getTraceInfo(){
    	StringBuffer sb = new StringBuffer();
    	StackTraceElement[] stacks = new Throwable().getStackTrace();
    	sb.append("class: ").append(stacks[1].getClassName()).append("; method: ").append(stacks[1].getMethodName()).append("; number: ").append(stacks[1].getLineNumber());
    	return sb.toString();
    }
}
