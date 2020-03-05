package cn.com.trueway.workflow.core.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.dao.MonitorDao;
import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.pojo.WfInterfaceCheck;
import cn.com.trueway.workflow.core.service.MonitorService;

public class MonitorServiceImpl implements MonitorService {

	private MonitorDao monitorDao ;
	
	public MonitorDao getMonitorDao() {
		return monitorDao;
	}

	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}

	@Override
	public void addMonitorInfo(MonitorInfoBean info) {
		monitorDao.addMonitorInfo(info);
	}

	@Override
	public List<MonitorInfoBean> getMonitorInfoByParam(Map<String, Object> params) {
		return monitorDao.getMonitorInfoByParam(params);
	}

	@Override
	public List<String> getDayCpuInfo(String date) {
		return monitorDao.getDayCpuInfo( date);
	}

	@Override
	public List<String> getDayMemoryInfo(String date) {
		return monitorDao.getDayMemoryInfo( date);
	}

	@Override
	public List<WfInterfaceCheck> getInterfaceList(Map<String, String> map){
		return monitorDao.getInterfaceList(map);
	}
	
	@Override
	public void updateInterfaceResult(String id, String result){
		monitorDao.updateInterfaceResult(id, result);
	}
	
	@Override
	public List<Object[]> getDfStatisticBySite(Map<String, String> map){
		return monitorDao.getDfStatisticBySite(map);
	}
	
	@Override
	public List<Object[]> getDfStaByItemType(Map<String, String> map){
		return monitorDao.getDfStaByItemType(map);
	}

	@Override
	public List<Object[]> getActDeptList(Map<String, String> map) {
		return monitorDao.getActDeptList(map);
	}
}
