package cn.com.trueway.workClander.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workClander.dao.WorkdayDao;
import cn.com.trueway.workClander.pojo.Workday;
import cn.com.trueway.workClander.service.WorkdayService;

public class WorkdayServiceImpl implements WorkdayService{
	private WorkdayDao workdayDao;
	
	public WorkdayDao getWorkdayDao() {
		return workdayDao;
	}

	public void setWorkdayDao(WorkdayDao workdayDao) {
		this.workdayDao = workdayDao;
	}

	public void delete(Workday workday) throws DataAccessException {
		workdayDao.delete(workday);
	}

	public List<Workday> getAllWorkday() throws DataAccessException {
		return workdayDao.getAllWorkday();
	}

	public Workday getOneWorkdayById(String id) throws DataAccessException {
		return workdayDao.getOneWorkdayById(id);
	}

	public Integer getWorkdayCountForPage(String column, String value,
			Workday workday) {
		return workdayDao.getWorkdayCountForPage(column, value, workday);
	}

	public List<Workday> getWorkdayListForPage(String column, String value,
			Workday workday, Integer pageindex, Integer pagesize) {
		return workdayDao.getWorkdayListForPage(column, value, workday, pageindex, pagesize);
	}

	public void save(Workday workday) throws DataAccessException {
		workdayDao.save(workday);
	}

	public void update(Workday workday) throws DataAccessException {
		workdayDao.update(workday);
	}

	public void deleteByYear(String year) throws DataAccessException {
		workdayDao.deleteByYear(year);
	}

	public List<Workday> getListByYear(String year) throws DataAccessException {
		return workdayDao.getListByYear(year);
	}

}
