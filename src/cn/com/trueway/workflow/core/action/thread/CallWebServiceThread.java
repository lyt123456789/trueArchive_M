package cn.com.trueway.workflow.core.action.thread;

import org.apache.log4j.Logger;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.webService.service.ArchiveWebService;

/**
 * 
 * @author xiep
 *
 */
public class CallWebServiceThread extends Thread{
	private String sxlx;//事项类型
	private String instanceId;//实例Id
	private ArchiveWebService archiveWebService; 
	private Logger logger = Logger.getLogger(this.getClass());
	
	public CallWebServiceThread(String sxlx, String instanceId, ArchiveWebService archiveWebService){
		this.sxlx = sxlx;
		this.instanceId = instanceId;
		this.archiveWebService = archiveWebService;
	}
	
	@Override
	public void run(){
		//主流程办结进行归档操作
		if(CommonUtil.stringNotNULL(sxlx)){
			try{
				if("0".equals(sxlx)){
					//发文
					archiveWebService.addSendDocument(instanceId);
				}else if("1".equals(sxlx)){
					//收文
					archiveWebService.addAcceptDocument(instanceId);
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("档案系统web service调用异常！");
				System.out.println("档案系统web service调用异常！");
			}
		}
	}
}
