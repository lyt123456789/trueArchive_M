package cn.com.trueway.workflow.core.action.thread;

import java.util.Date;

import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.service.TrueJsonService;

/**
 * 文件名称： cn.com.trueway.workflow.core.action.thread.TrueJsonLogThread.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2018-12-27<br/>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>  
 * 修改记录：<br/> 
 * 修改作者        日期       修改内容<br/> 
 */
public class TrueJsonLogThread extends Thread{

	private TrueJsonService trueJsonService;
	
	private String instanceId;

	private String processId;
	
	private String userId;
	
	private String trueJson;
	
	private String excute;
	
	public TrueJsonLogThread() {
		
	}

	public TrueJsonLogThread(TrueJsonService trueJsonService,
			String instanceId, String processId, String userId,
			String trueJson, String excute) {
		super();
		this.trueJsonService = trueJsonService;
		this.instanceId = instanceId;
		this.processId = processId;
		this.userId = userId;
		this.trueJson = trueJson;
		this.excute = excute;
	}

	@Override
	public void run() {
		try {
			TrueJson entity = new TrueJson();
			entity.setInstanceId(instanceId);
			entity.setProcessId(processId);
			entity.setSaveDate(new Date());
			entity.setUserId(userId);
			entity.setTrueJson(trueJson);
			entity.setExcute(excute);
			trueJsonService.saveTrueJson(entity);
		} catch (Exception e) {
			System.out.println("TrueJsonLogThread线程报错");
			e.printStackTrace();
		}
	}

	
}
