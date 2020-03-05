package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.FilingDao;
import cn.com.trueway.workflow.core.pojo.WfFiling;
import cn.com.trueway.workflow.core.pojo.vo.Filing;
import cn.com.trueway.workflow.core.service.FilingService;

public class FilingServiceImpl implements FilingService {

	private FilingDao filingDao;

	public FilingDao getFilingDao() {
		return filingDao;
	}

	public void setFilingDao(FilingDao filingDao) {
		this.filingDao = filingDao;
	}

	@Override
	public boolean insertFiling(WfFiling wfFiling) {
		return filingDao.insertFiling(wfFiling);
	}

	@Override
	public List<Filing> getFilingList(String conditionSql, String deptSql1,
			String deptSql2, Integer pageIndex, Integer pageSize) {
		return filingDao.getFilingList(conditionSql, deptSql1, deptSql2, pageIndex, pageSize);
	}

	@Override
	public int getCountOfFilings(String conditionSql, String deptSql1,
			String deptSql2) {
		return filingDao.getCountOfFilings(conditionSql, deptSql1, deptSql2);
	}

	@Override
	public boolean checkFiling(String processId) {
		return filingDao.checkFiling(processId);
	}
	
}
