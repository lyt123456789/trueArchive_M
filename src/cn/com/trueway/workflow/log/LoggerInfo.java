package cn.com.trueway.workflow.log;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import cn.com.trueway.workflow.core.pojo.WfProcess;

public class LoggerInfo {

	/*
	 * 暂存开始日志记录
	 */
	public static void onlySaveStart(Logger logger,WfProcess process){
		if(process==null ){
			logger.warn("办件第一步暂存");
		}else{
			logger.warn("《"+process.getProcessTitle()+"》"+" 暂存");
		}
	}
	

	/*
	 * 暂存结束日志记录
	 */
	public static void onlySaveEnd(Logger logger, WfProcess process){
		if(process==null ){
			logger.warn("办件第一步暂存");
		}else{
			logger.warn("《"+process.getProcessTitle()+"》"+" 暂存");
		}
	}
	/*
	 * 发送下一步开始日志记录
	 */
	public static void sendNextStart(Logger logger, WfProcess process){
		if(process==null ){
			logger.warn("办件发起");
		}else{
			MDC.put("instanceId", process.getWfInstanceUid());
			logger.warn("《"+process.getProcessTitle()+"》"+"发送下一步");
			MDC.remove("instanceId");
		}
	}
	
	/*
	 *  发送下一步结束日志记录
	 */
	public static void sendNextEnd(Logger logger,WfProcess process){
		if(process==null ){
			logger.warn("办件发起");
		}else{
			MDC.put("instanceId", process.getWfInstanceUid());
			logger.warn("《"+process.getProcessTitle()+"》"+"发送下一步");
		}
	}
	/**
	 * 
	 * 描述：办结开始
	 * @param logger
	 * @param process void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 上午11:22:42
	 */
	
	public static void endStart(Logger logger,WfProcess process){
		
	}
	
	/**
	 * 描述：办结结束
	 * @param logger
	 * @param process void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 上午11:23:24
	 */
	public static void endEnd(Logger logger, WfProcess process){
		
	}
	
	/**
	 * 描述：记录异常
	 * @param logger
	 * @param e void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午1:59:47
	 */
	public static void exception(Logger logger,Exception e){
		
	}
	
	/**
	 * 描述：意见处理异常
	 * @param logger
	 * @param e void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:01:25
	 */
	public static void commentException(Logger logger,Exception e){
		
	}
	
	/**
	 * 描述：附件处理异常
	 * @param logger
	 * @param e void
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:02:17
	 */
	public static void attachmentException(Logger logger,Exception e){
		
		
	}
	
	/**
	 * 描述：同步文件
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:07:32
	 */
	public static void syncDoc(){
		
	}
	
	/**
	 * 描述：同步过程
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:08:07
	 */
	public static void syncProcess(){
		
	}
	

	
	/**
	 * 描述：同步结果
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:08:53
	 */
	public static void syncResult(){
		
	}
	
	/**
	 * 描述：同步办件
	 *
	 * 作者:Yuxl
	 * 创建时间:2016-8-14 下午2:12:20
	 */
	public static void syncDoFile(){
		
	}
}
