package cn.com.trueway.extra.meeting.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.dao.MeetingPhoDao;

public class MeetingPhoDaoImpl extends BaseDao implements MeetingPhoDao {

	@Override
	public List<MeetingphoApply> getMeetingApplylist(String userId,
			Integer pageIndex, Integer pageSize) {
		List<MeetingphoApply> list = new ArrayList<MeetingphoApply>();
		if(null != userId && !"".equals(userId)){
			StringBuffer sql = new StringBuffer();
			sql.append(" from MeetingphoApply t");
			sql.append(" where t.meeting_bcyryid like '%").append(userId).append("%'");
			sql.append(" and t.state = '1'");
			sql.append(" and sysdate <= to_date(t.meeting_begin_time,'yyyy-mm-dd hh24\"时\"mm\"分\"')");
			sql.append(" order by t.meeting_begin_time desc");
			Query query = super.getEm().createQuery(sql.toString());
			if (pageIndex != null && pageSize != null) {
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
			}
			list = query.getResultList();
		}
		return list;
	}

}
