package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.NoticeInfo;

public interface NoticeInfoDao {
	
	void saveNoticeInfo(NoticeInfo noticeInfo);
	
	NoticeInfo findNoticeInfoById(String id);
	
	void updateNoticeInfo(NoticeInfo noticeInfo) throws Exception;
	
	List<NoticeInfo> findNoticeInfoList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	int findNoticeInfoCount(String conditionSql);
	
	int findXMCount(String conditionSql);
	
	public List<Object[]> findXMList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	NoticeInfo findNoticeInfoByfprocessId(String fprocessId);
}
