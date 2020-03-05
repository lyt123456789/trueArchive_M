package cn.com.trueway.workflow.webService.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "ExchangeReceiveService", targetNamespace = "http://www.trueway.com.cn/workflow/webService/ExchangeReceiveService")
public interface ExchangeReceiveService {
	
	/**
	 * 收取办件信息
	 * 描述：TODO 对此方法进行描述
	 * @param dataJson
	 * @param other_ip
	 * @return String
	 * 作者:季振华
	 * 创建时间:2017-1-5 上午9:19:15
	 */
	@WebResult(name = "result", targetNamespace = "")
	public String receiveSendXml(String xml);
	
	
}
