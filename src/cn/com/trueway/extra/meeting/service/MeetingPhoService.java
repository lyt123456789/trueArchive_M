package cn.com.trueway.extra.meeting.service;

import java.util.List;

import cn.com.trueway.extra.meeting.bean.MeetingphoApply;

public interface MeetingPhoService {
	public List<MeetingphoApply> getMeetingApplylist(String userId,
			Integer pageIndex, Integer pageSize);
}
