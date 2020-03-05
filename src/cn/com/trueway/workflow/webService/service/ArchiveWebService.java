package cn.com.trueway.workflow.webService.service;

/**
 * 玄武区档案系统webService接口
 * @author jszw
 *
 */
public interface ArchiveWebService {
	/**
	 * 登录档案系统
	 * @return
	 */
	public String loginArchiveSystem();
	
	/**
	 * 退出档案系统
	 * @return
	 */
	public String logoutArchiveSystem(String sessionId);
	
	/**
	 * 传输发文
	 * @return
	 */
	public String addSendDocument(String instanceId);
	
	/**
	 * 传输收文
	 * @return
	 */
	public String addAcceptDocument(String instanceId);
	
	
}
