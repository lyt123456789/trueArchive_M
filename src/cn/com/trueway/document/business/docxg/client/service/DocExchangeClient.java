package cn.com.trueway.document.business.docxg.client.service;

import java.util.List;

import cn.com.trueway.docxg.service.CarInterfaceService;
import cn.com.trueway.resourceInterface.service.RightService;
import cn.com.trueway.workflow.webService.service.ExchangeReceiveService;
import cn.com.trueway.workflow.webService.service.ExchangeSendService;
import cn.ppsoho.webservice.docxg.department.service.DepartmentService;
import cn.ppsoho.webservice.docxg.receive.service.ToReceviceService;
import cn.ppsoho.webservice.docxg.send.service.SendService;
import cn.ppsoho.webservice.docxg.statistics.service.StatisticsService;
import cn.ppsoho.webservice.docxg.untread.service.UntreadService;
/**
 * 描述：调用公文交换接口客户端类，包含获取公文收发单位、获取待收文信息、收取公文、发送公文、退文五个接口 <br>
 * 作者：周雪贇<br>
 * 创建时间：2011-11-30 下午10:01:37<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public final class DocExchangeClient{
	private DepartmentService docDepartmentServiceClient ;
	private ToReceviceService receiveDocServiceClient ;
	private SendService sendDocServiceClient ;
	private UntreadService untreadDocServiceClient;
	private StatisticsService statisticsServiceClient;
	private RightService rightServiceClient;
	private CarInterfaceService carServiceClient;
	private ExchangeSendService exchangeSendServiceClient;
	private ExchangeReceiveService exchangeReceiveServiceClient;
	
	
	/**
	 * 获取公文收发单位接口
	 * @param userKey 
	 * @return string 
	 */
	public final String getAllDocDepartments(String userKey){
		String result = docDepartmentServiceClient.listAllByXML(userKey);
		return result;
	}
	
	/**
	 * 获取待收文接口
	 * @param userKey 
	 * @return string 
	 */
	public final String getToBeReceivedList(String userKey){
		String result = receiveDocServiceClient.getToBeRecevicedList(userKey);
		return result;
	}
	
	/**
	 * 反馈收文状态接口
	 * @param userKey 
	 * @param receiveDocQueueId
	 * @return string 
	 */
	public final String receiveDoc(String userKey,String receiveDocQueueId){
		String result = receiveDocServiceClient.updateDocForReceive(userKey, receiveDocQueueId);
		return result;
	}
	
	/**
	 * 发文接口，需传入公文交换平台的发文接口所需的xml信息
	 * @param userKey 
	 * @param docInfoXml
	 * @return string
	 */
	public final String sendDoc(String userKey, String docInfoXml){
		String result = sendDocServiceClient.saveDoc(userKey, docInfoXml);
		return result;
	}
	
	/**
	 * 已发公文的状态信息查询，返回xml
	 * @param userKey
	 * @param docGuid
	 * @return xml 
	 */
	public final String getDocStatusByDocguid(String userKey, String docGuid){
		String result = sendDocServiceClient.getDocStatusByDocguid(userKey, docGuid);
		return result;
	}
	
	/**
	 * 已发公文的状态信息查询所发部门的收取状态
	 * @param userKey
	 * @param docGuid
	 * @return string 
	 */
	public final String getDocStatusByDocguidList(String userKey, List<String> docGuidList){
		String result = sendDocServiceClient.getDocStatusByDocguidList(userKey, docGuidList);
		return result;
	}
	
	
	/**
	 * 描述：退文操作<br>
	 *
	 * @param userKey
	 * @param docguid:OA系统中已收公文的DOCGUID，即公文交换平台的数据库中的DOCEXCHANGE_QUEUE表中的ID
	 * @param remark:退文原因
	 * @param depart_guid:退文单位的DEPART_GUID，即A发送给B一个公文，B退回公文，depart_guid=B.depart_guid
	 * @return String
	 * 
	 * @author 周雪贇
	 */
	public final String toSaveUntreadDoc(String userKey, String docGuid,String remark,String depart_guid){
		String result = untreadDocServiceClient.toSaveUntreadDoc(userKey, docGuid, remark, depart_guid);
		return result;
	}
	/**
	 * 描述：根据条件获取退文列表<br>
	 * zhouxy
	 * @param userKey
	 * @param pageIndex
	 * @param pageSize
	 * @param untreadStatus	-- 0：未接收  1：已查看   2为所有   空也为所有
	 * @return String -- 列表 + 总条数 
	 */
	public final String getUntreadToBeRecevicedList(String userKey,int pageIndex,int pageSize,int untreadStatus){
		String result=untreadDocServiceClient.getUntreadToBeRecevicedList(userKey, pageIndex, pageSize, untreadStatus);
		return result;
	}
	/**
	 * 描述：根据ID修改退文的状态位<br>
	 * zhouxy
	 * @param userKey
	 * @param pageIndex
	 * @param pageSize
	 * @param untreadStatus	-- 0：未接收  1：已查看   2为所有   空也为所有
	 * @return String -- 列表 + 总条数 
	 */
	public final String updateUntreadDoc(String userKey,String docguid){
		String result=untreadDocServiceClient.updateUntreadDoc(userKey,docguid);
		return result;
	}
	
	/**
	 * 车辆管理,返回一个拼接后的字符串--格式:车的id,车号;...;....
	 * @param userId
	 * @return
	 */
	public final String getCarList(String userId){
		String result = carServiceClient.getCarList(userId);
		return result;
	}
	
	public String getDocInfo(String userKey, String receDeptId,
			List<String> sendDeptIdList, boolean isReceivedTime,
			String beginTime, String endTime) {
		return statisticsServiceClient.getDocInfo(userKey, receDeptId, sendDeptIdList, isReceivedTime, beginTime, endTime);
	}
	
	public String getWhInfo(String userKey, String receDeptId,
			String sendDeptId, boolean isReceivedTime, String beginTime,
			String endTime) {
		return statisticsServiceClient.getWhInfo(userKey, receDeptId, sendDeptId, isReceivedTime, beginTime, endTime);
	}
	
	public String isRemind(String userId) {
		return rightServiceClient.isRemind(userId);
	}
	
	/**
	 * @return the docDepartmentServiceClient
	 */
	public DepartmentService getDocDepartmentServiceClient() {
		return docDepartmentServiceClient;
	}
	/**
	 * @param docDepartmentServiceClient the docDepartmentServiceClient to set
	 */
	public void setDocDepartmentServiceClient(
			DepartmentService docDepartmentServiceClient) {
		this.docDepartmentServiceClient = docDepartmentServiceClient;
	}
	/**
	 * @return the receiveDocServiceClient
	 */
	public ToReceviceService getReceiveDocServiceClient() {
		return receiveDocServiceClient;
	}
	/**
	 * @param receiveDocServiceClient the receiveDocServiceClient to set
	 */
	public void setReceiveDocServiceClient(ToReceviceService receiveDocServiceClient) {
		this.receiveDocServiceClient = receiveDocServiceClient;
	}
	/**
	 * @return the sendDocServiceClient
	 */
	public SendService getSendDocServiceClient() {
		return sendDocServiceClient;
	}
	/**
	 * @param sendDocServiceClient the sendDocServiceClient to set
	 */
	public void setSendDocServiceClient(SendService sendDocServiceClient) {
		this.sendDocServiceClient = sendDocServiceClient;
	}

	public UntreadService getUntreadDocServiceClient() {
		return untreadDocServiceClient;
	}

	public void setUntreadDocServiceClient(UntreadService untreadDocServiceClient) {
		this.untreadDocServiceClient = untreadDocServiceClient;
	}

	public StatisticsService getStatisticsServiceClient() {
		return statisticsServiceClient;
	}

	public void setStatisticsServiceClient(StatisticsService statisticsServiceClient) {
		this.statisticsServiceClient = statisticsServiceClient;
	}

	public RightService getRightServiceClient() {
		return rightServiceClient;
	}

	public void setRightServiceClient(RightService rightServiceClient) {
		this.rightServiceClient = rightServiceClient;
	}

	public CarInterfaceService getCarServiceClient() {
		return carServiceClient;
	}
	public void setCarServiceClient(CarInterfaceService carServiceClient) {
		this.carServiceClient = carServiceClient;
	}
	
	public ExchangeSendService getExchangeSendServiceClient() {
		return exchangeSendServiceClient;
	}

	public void setExchangeSendServiceClient(
			ExchangeSendService exchangeSendServiceClient) {
		this.exchangeSendServiceClient = exchangeSendServiceClient;
	}

	
	public ExchangeReceiveService getExchangeReceiveServiceClient() {
		return exchangeReceiveServiceClient;
	}

	public void setExchangeReceiveServiceClient(
			ExchangeReceiveService exchangeReceiveServiceClient) {
		this.exchangeReceiveServiceClient = exchangeReceiveServiceClient;
	}

	/**
	 * 获取公文收发单位接口
	 * @param userKey 
	 * @return string 
	 */
	public final String getLowReceivedList(String userKey,String pageIndex,String pageSize){
		String result = exchangeSendServiceClient.getToBeReceivedList(userKey,pageIndex,pageSize);
		return result;
	}
	
	/**
	 * 获取公文收发单位接口
	 * @param userKey 
	 * @return string 
	 */
	public final String updateLowDoFileReceive(String userKey){
		String result = exchangeSendServiceClient.updateLowDoFileReceive(userKey);
		return result;
	}
	
	/**
	 * 获取公文收发单位接口
	 * @param userKey 
	 * @return string 
	 */
	public final String receiveSendXml(String xml){
		String result = exchangeReceiveServiceClient.receiveSendXml(xml);
		return result;
	}
	
}
