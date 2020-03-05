package cn.com.trueway.extra.meeting.service.impl;

import java.util.List;

import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.dao.MeetingPhoDao;
import cn.com.trueway.extra.meeting.service.MeetingPhoService;

public class MeetingPhoServiceImpl implements MeetingPhoService {
	private MeetingPhoDao meetingphodao;
	public void setMeetingphodao(MeetingPhoDao meetingphodao) {
		this.meetingphodao = meetingphodao;
	}
	public MeetingPhoDao getMeetingphodao() {
		return meetingphodao;
	}
	@Override
	public List<MeetingphoApply> getMeetingApplylist(String userId,
			Integer pageIndex, Integer pageSize) {
		return meetingphodao.getMeetingApplylist(userId, pageIndex, pageSize);
	}
}
