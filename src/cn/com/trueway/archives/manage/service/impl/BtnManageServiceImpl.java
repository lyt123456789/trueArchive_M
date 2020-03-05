package cn.com.trueway.archives.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.manage.dao.BtnManageDao;
import cn.com.trueway.archives.manage.service.BtnManageService;

public class BtnManageServiceImpl implements  BtnManageService{
    private BtnManageDao btnManageDao;

	public BtnManageDao getBtnManageDao() {
		return btnManageDao;
	}
	
	public void setBtnManageDao(BtnManageDao btnManageDao) {
		this.btnManageDao = btnManageDao;
	}

	@Override
	public int getBtnManageListCount(Map<String, Object> map) {
		return btnManageDao.getBtnManageListCount(map);
	}

	@Override
	public List<Map<String, String>> getBtnManageList(Map<String, Object> map) {
		return btnManageDao.getBtnManageList(map);
	}

	@Override
	public boolean saveBtnManageRecord(Map<String, Object> map) {
		try {
			this.btnManageDao.saveBtnManageRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteOneBtnManageById(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.btnManageDao.deleteOneBtnManageById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
  
  
}
