package cn.com.trueway.workflow.core.action.thread;

import cn.com.trueway.workflow.core.service.TableInfoService;

/**
 * 更新时间线程
 * @author caiyj
 *
 */
public class UpdateTimeThread extends Thread{
	
	private TableInfoService tableInfoService;
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public UpdateTimeThread(TableInfoService tableInfoService){
		this.tableInfoService = tableInfoService ;
	}
	
	@Override
	public void run() {
		//update: jdqxDate; zhqxDate
		tableInfoService.updateProcessDate();
	}
}
