package cn.com.trueway.workflow.set.service.impl;

import cn.com.trueway.workflow.set.dao.ThirdpartyInterfaceLogDao;
import cn.com.trueway.workflow.set.pojo.ThirdpartyInterfaceLog;
import cn.com.trueway.workflow.set.service.ThirdpartyInterfaceLogService;

public class ThirdpartyInterfaceLogServiceImpl implements
		ThirdpartyInterfaceLogService {
	
	private ThirdpartyInterfaceLogDao	thirdpartyInterfaceLogDao;
	
	public ThirdpartyInterfaceLogDao getThirdpartyInterfaceLogDao() {
		return thirdpartyInterfaceLogDao;
	}

	public void setThirdpartyInterfaceLogDao(
			ThirdpartyInterfaceLogDao thirdpartyInterfaceLogDao) {
		this.thirdpartyInterfaceLogDao = thirdpartyInterfaceLogDao;
	}

	@Override
	public ThirdpartyInterfaceLog add(ThirdpartyInterfaceLog entity) {
		return thirdpartyInterfaceLogDao.insert(entity);
	}

	@Override
	public void update(ThirdpartyInterfaceLog entity) {
		thirdpartyInterfaceLogDao.update(entity);
	}

}
