package cn.com.trueway.archives.templates.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.templates.dao.BusinessManageDao;
import cn.com.trueway.archives.templates.pojo.EssBusiness;
import cn.com.trueway.archives.templates.service.BusinessManageService;

public class BusinessManageServiceImpl implements BusinessManageService {
	
	private BusinessManageDao businessManageDao;

	public BusinessManageDao getBusinessManageDao() {
		return businessManageDao;
	}

	public void setBusinessManageDao(BusinessManageDao businessManageDao) {
		this.businessManageDao = businessManageDao;
	}

	@Override
	public int getBusinessManageCount() {
		return this.businessManageDao.getBusinessManageCount();
	}

	@Override
	public List<EssBusiness> getBusinessManageList(Map<String, Object> map) {
		return this.businessManageDao.getBusinessManageList(map);
	}

	@Override
	public boolean checkRecordByMap(Map<String, Object> map) {
		List<EssBusiness> list = this.businessManageDao.checkRecordByMap(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean saveBusinessRecord(Map<String, Object> map) {
		try {
			this.businessManageDao.saveBusinessRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteBusinessRecord(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.businessManageDao.deleteBusinessRecord(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
}
