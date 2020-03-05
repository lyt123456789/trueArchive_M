package cn.com.trueway.workflow.webService.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "ExchangeSendService", targetNamespace = "http://www.trueway.com.cn/workflow/webService/ExchangeSendService")
public interface ExchangeSendService {
	
	/**
	 * 根据用户id获取待收列表
	 * 描述：TODO 对此方法进行描述
	 * @param userId
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-6-20 上午8:49:23
	 */
	@WebResult(name = "result", targetNamespace = "")
	public String getToBeReceivedList(
			@WebParam(name = "userId") String userId,
			@WebParam(name = "pageIndex") String pageIndex, 
			@WebParam(name = "pageSize") String pageSize);
	
	/**
	 * 根据办件id更新办件状态
	 * 描述：TODO 对此方法进行描述
	 * @param id
	 * @return boolean
	 * 作者:季振华	
	 * 创建时间:2016-6-20 上午8:53:40
	 */
	@WebResult(name = "result", targetNamespace = "")
	public String updateLowDoFileReceive(@WebParam(name = "id") String id);
	
	
}
