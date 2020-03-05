package cn.com.trueway.functions.car.dao;

import java.util.List;

import cn.com.trueway.functions.car.entity.ClOut;

public interface ClOutDao {

	public int getCountOfMyApply(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo); 

	public List<ClOut> getMyApplyList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo);
	
	public int getCountOfCarPending(String userId, String itemid,String searchDateFrom,String searchDateTo);
	
	List<ClOut> getPendingCarList(String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo);

}
