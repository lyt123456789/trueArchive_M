package cn.com.trueway.extra.meeting.dao;

import java.util.List;

import cn.com.trueway.extra.meeting.bean.MeetingphoApply;

public interface MeetingPhoDao {
	public List<MeetingphoApply> getMeetingApplylist(String userId,
			Integer pageIndex, Integer pageSize);
}
