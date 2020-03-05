package cn.com.trueway.archives.reports.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.reports.pojo.WorkStatistics;

public interface ReportsDao {
	
	/**
	 * 对借阅单内容进行统计
	 * @param sql
	 * @return
	 */
	public List<WorkStatistics> getPeopleData(String sql);
	
	/**
	 * 查询每个人对应的总利用人数
	 * @param sql
	 * @return
	 */
	public String getUsingPeopleNum(String sql);
	
	/**
	 * 查询打印，复印，摘抄，拍照的统计
	 * @param sql
	 * @return
	 */
	public Map<String,String> getStoreStatistics(String sql);
	
	/**
	 * 获取全部接待人信息
	 * @return
	 */
	public List<WorkStatistics> getAllReceivePeople(String name);
	
	/**
	 * 获取需要的的字典表信息
	 * @param sql
	 * @return
	 */
	public List<Map<String,String>> getSysDicResult(String sql);
	
	/**
	 * 校验当前选中条件下是否存在查阅记录
	 * @param sql
	 * @return
	 */
	public boolean checkRecord(String sql);
	
	/**
	 * 获取总查档目的
	 * @return
	 */
	public List<WorkStatistics> getBorrowCdmdList();
	
	/**
	 * 获取借阅卷件资料总数
	 * @return
	 */
	public Map<String,String> getBorrowDataCount(String sql);
	
	/**
	 * 获取总其他目的
	 * @return
	 */
	public List<WorkStatistics> getBorrowOtherMdList();
	
	/**
	 * 查询总的档案分类
	 * @return
	 */
	public List<WorkStatistics> getBorrowArchiveList();
}
