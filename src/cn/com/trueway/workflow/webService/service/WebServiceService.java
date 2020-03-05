package cn.com.trueway.workflow.webService.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "WebServiceService", targetNamespace = "http://www.trueway.com.cn/workflow/webService/WebServiceService")
public interface WebServiceService {
	@WebResult(name = "result", targetNamespace = "")
	public String getItemList(@WebParam(name = "dataJson") String dataJson);
	
	@WebResult(name = "result", targetNamespace = "")
	public String getSyncInfoListByItemId(@WebParam(name = "dataJson") String dataJson);
	
	@WebResult(name = "result", targetNamespace = "")
	public String syncInfo(@WebParam(name = "dataJson") String dataJson);
	
	@WebResult(name = "result", targetNamespace = "")
	public String getWfProcessList(@WebParam(name = "instanceId") String instanceId);
	
	@WebResult(name = "result", targetNamespace = "")
	public String updateEndWfpInfoList(@WebParam(name = "dataJson") String dataJson);
	
	/**
	 * 
	 * 描述：获取instanceId获取下载的地址路径
	 * @param instanceId
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-1-20 下午8:14:53
	 */
	@WebResult(name = "result", targetNamespace = "")
	public String getPdfPathInfo(@WebParam(name = "instanceId") String instanceId);

}
