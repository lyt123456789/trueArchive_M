/**
 * 文件名称:MeetingInfoDao.java
 * 作者:zhuxc<br>
 * 创建时间:2013-12-27 下午02:55:07
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.extra.meeting.dao;

import java.util.List;

import cn.com.trueway.extra.meeting.bean.MeetingApply;
import cn.com.trueway.extra.meeting.bean.MeetingApplyOut;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.workflow.core.pojo.Employee;

/**
 * 描述： 对MeetingInfoDao进行描述
 * 作者：zhuxc
 * 创建时间：2013-12-27 下午02:55:07
 */
public interface MeetingInfoDao {

	int getCountDeptMeetings(String bigDepId, String conditionSql);

	List<MeetingApply> getDeptMeetingList(String bigDepId, String conditionSql,
			Integer pageIndex, Integer pageSize);

	void updateState(String instanceId, String state);

	int getCountMeetings(String conditionSql);
	
	List<MeetingphoApply> getMeetingList(String conditionSql, Integer pageIndex, Integer pageSize);
	
	int getCountMeetingOuts(String depids, String conditionSql);
	
	List<MeetingApplyOut> getMeetingOutList(String depids, String conditionSql,
			Integer pageIndex, Integer pageSize);
	
	List<Employee> getPersonName(String instanceId);
}
