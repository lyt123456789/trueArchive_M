package cn.com.trueway.workflow.core.service;

import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.workflow.core.pojo.NoticeInfo;

public interface NoticeInfoService {
	
	JSONObject saveNoticeInfo(NoticeInfo noticeInfo);
	
	NoticeInfo findNoticeInfoById(String id);
	
	JSONObject updateNoticeInfo(NoticeInfo noticeInfo);
	
	List<NoticeInfo> findNoticeInfoList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	int findNoticeInfoCount(String conditionSql);
	
	int findXMCount(String conditionSql);
	
	List<Object[]> findXMList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	NoticeInfo findNoticeInfoByfprocessId(String fprocessId);
	
	String getNoticeInfoListOfMobile(List<NoticeInfo> list, String serverUrl);
	
}
