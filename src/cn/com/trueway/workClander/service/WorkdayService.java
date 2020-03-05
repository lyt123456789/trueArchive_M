package cn.com.trueway.workClander.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.com.trueway.workClander.pojo.Workday;

public interface WorkdayService {
	public void save(Workday workday)throws DataAccessException;
	public void update(Workday workday)throws DataAccessException;
	public void delete(Workday workday)throws DataAccessException;
	public List<Workday> getAllWorkday()throws DataAccessException;
	public Workday getOneWorkdayById(String id)throws DataAccessException;
	public List<Workday> getWorkdayListForPage(String column,String value,Workday workday,Integer pageindex,Integer pagesize);
	public Integer getWorkdayCountForPage(String column,String value,Workday workday);
	
	public void deleteByYear(String year)throws DataAccessException;
	public List<Workday> getListByYear(String year)throws DataAccessException;
}
