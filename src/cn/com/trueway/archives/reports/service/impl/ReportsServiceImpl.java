package cn.com.trueway.archives.reports.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.reports.dao.ReportsDao;
import cn.com.trueway.archives.reports.pojo.WorkStatistics;
import cn.com.trueway.archives.reports.service.ReportsService;

public class ReportsServiceImpl implements ReportsService {
	
	private ReportsDao reportsDao;

	public ReportsDao getReportsDao() {
		return reportsDao;
	}

	public void setReportsDao(ReportsDao reportsDao) {
		this.reportsDao = reportsDao;
	}

	@Override
	public List<WorkStatistics> getPeopleData(String sql) {
		return this.reportsDao.getPeopleData(sql);
	}

	@Override
	public String getUsingPeopleNum(String sql) {
		return this.reportsDao.getUsingPeopleNum(sql);
	}

	@Override
	public Map<String, String> getStoreStatistics(String sql) {
		return this.reportsDao.getStoreStatistics(sql);
	}

	@Override
	public List<WorkStatistics> getAllReceivePeople(String name) {
		return this.reportsDao.getAllReceivePeople(name);
	}

	@Override
	public List<Map<String, String>> getSysDicResult(String sql) {
		return this.reportsDao.getSysDicResult(sql);
	}

	@Override
	public boolean checkRecord(String sql) {
		return this.reportsDao.checkRecord(sql);
	}

	@Override
	public List<WorkStatistics> getBorrowCdmdList() {
		return this.reportsDao.getBorrowCdmdList();
	}

	@Override
	public Map<String, String> getBorrowDataCount(String sql) {
		return this.reportsDao.getBorrowDataCount(sql);
	}

	@Override
	public List<WorkStatistics> getBorrowOtherMdList() {
		return this.reportsDao.getBorrowOtherMdList();
	}

	@Override
	public List<WorkStatistics> getBorrowArchiveList() {
		return this.reportsDao.getBorrowArchiveList();
	}
	
	
}
