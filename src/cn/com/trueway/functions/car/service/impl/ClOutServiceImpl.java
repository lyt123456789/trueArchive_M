package cn.com.trueway.functions.car.service.impl;

import java.util.List;

import cn.com.trueway.functions.car.dao.ClOutDao;
import cn.com.trueway.functions.car.entity.ClOut;
import cn.com.trueway.functions.car.service.ClOutService;


public class ClOutServiceImpl implements ClOutService {

	private ClOutDao clOutDao;


	public int getCountOfMyApply(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		return clOutDao.getCountOfMyApply( conditionSql,userId, itemid,searchDateFrom,searchDateTo);
	}
	
	@Override
	public List<ClOut> getMyApplyList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		
		List<ClOut> pendList = clOutDao.getMyApplyList(conditionSql,userId, pageIndex, pageSize,itemid,searchDateFrom,searchDateTo);
		
		return pendList;
	}
	
	public int getCountOfCarPending(String userId,String itemid,String searchDateFrom,String searchDateTo){
		return clOutDao.getCountOfCarPending(userId,itemid,searchDateFrom,searchDateTo);
	}
	
	public List<ClOut> getPendingCarList(String userId, Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		return clOutDao.getPendingCarList(userId, pageIndex, pageSize,itemid,searchDateFrom,searchDateTo);
	}
	
	public void setClOutDao(ClOutDao clOutDao) {
		this.clOutDao = clOutDao;
	}
	
}
