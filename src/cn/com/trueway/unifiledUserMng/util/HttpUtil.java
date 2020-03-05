package cn.com.trueway.unifiledUserMng.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	public String sendPost(String url, Map<String, String> paramMap) {
		String returnmsg = "";
		try {
			// 封装参数
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			if (paramMap != null && paramMap.keySet() != null) {
				for (String key : paramMap.keySet()) {
					parameters.add(new BasicNameValuePair(key, paramMap.get(key)));
				}
			}
			// 创建UrlEncodedFormEntity对象
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
			returnmsg = sendPostHttp(url, formEntiry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnmsg;
	}

	// 发送http请求
	private String sendPostHttp(String url, UrlEncodedFormEntity formEntiry) {
		// 发送请求
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//连接超时，10s
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);//请求获取数据超时，10s
		String returnmsg = "fail"; // 失败
		try {
			// 实例化HTTP POST方法
			HttpPost postmethod = new HttpPost(url);
			postmethod.setEntity(formEntiry);
			// 执行请求
			HttpResponse reponse = client.execute(postmethod);
			// 回去返回实体
			HttpEntity entity = reponse.getEntity();
			returnmsg = EntityUtils.toString(entity, "UTF-8");
			// System.out.println("POST返回数据:"+returnmsg);
			// 若返回消息有中文要进行一下解码 服务器要加码URLEncoder.encode("服务器返回中文", "UTF-8")
			// System.out.println("POST返回数据--:"+URLDecoder.decode(returnmsg,"utf-8"));
		} catch (Exception e) {
			returnmsg = "fail";
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			client.getConnectionManager().shutdown();
		}
		return returnmsg;
	}
}
