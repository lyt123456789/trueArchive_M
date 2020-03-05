package cn.com.trueway.workflow.log.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.log.dao.LogDao;
import cn.com.trueway.workflow.log.pojo.Log;
import cn.com.trueway.workflow.log.service.LogService;

public class LogServiceImpl implements LogService{
	
	private LogDao logDao;

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public int countLog(Map<String, String> map) {
		return logDao.countLog(map);
	}

	public List<Log> getLogList(Map<String, String> map, Integer pageIndex,
			Integer pageSize) {
		List<Log> list = logDao.getLogList(map,pageIndex,pageSize);
		return list;
	}

	public Log findLogById(String logid) {
		return logDao.findLogById(logid);
	}

	public String getTitle(String instanceid) {
		return logDao.getTitle(instanceid);
	}
	
}
