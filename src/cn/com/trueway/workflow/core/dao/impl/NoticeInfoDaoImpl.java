package cn.com.trueway.workflow.core.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.NoticeInfoDao;
import cn.com.trueway.workflow.core.pojo.NoticeInfo;
import cn.com.trueway.workflow.core.pojo.Replay;

public class NoticeInfoDaoImpl extends BaseDao implements NoticeInfoDao{

	@Override
	public void saveNoticeInfo(NoticeInfo noticeInfo) {
		getEm().persist(noticeInfo);
	}

	@Override
	public NoticeInfo findNoticeInfoById(String id) {
		return getEm().find(NoticeInfo.class, id);
	}

	@Override
	public void updateNoticeInfo(NoticeInfo noticeInfo) throws Exception {
		getEm().merge(noticeInfo);
	}

	@Override
	public List<NoticeInfo> findNoticeInfoList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.id,t.fprocessid,to_char(t.SENDTIME, 'yyyy-MM-dd hh:ss:mm'), t.userid,t.status, " +
				"t2.employee_name,t3.process_title,t3.pdfpath")
			  .append("  from t_wf_core_noticeinfo t, zwkj_employee t2, t_wf_process t3")
			  .append("  where t.status = '0' and t2.employee_guid = t.userid and t3.wf_process_uid = t.fprocessid ")
		      .append(conditionSql)
		      .append(" order by t.sendtime desc");
		Query query = super.getEm().createNativeQuery(buffer.toString());
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList(); 
		List<NoticeInfo> noticeList = new ArrayList<NoticeInfo>();
		NoticeInfo  noticeInfo = null;
		for(Object[] data : list){
			noticeInfo = new NoticeInfo();
			noticeInfo.setId(data[0]!=null?data[0].toString():"");
			noticeInfo.setFprocessId(data[1]!=null?data[1].toString():"");
			noticeInfo.setSendTime(convertStringToDate(data[2]));
			noticeInfo.setUserId(data[3]!=null?data[3].toString():"");
			noticeInfo.setStatus(data[4]!=null?data[4].toString():"");
			noticeInfo.setUserName(data[5]!=null?data[5].toString():"");
			noticeInfo.setTitle(data[6]!=null?data[6].toString():"");
			String pdfpath = data[7]!=null?data[7].toString():"";
			noticeInfo.setPdfPath(pdfpath);
			noticeList.add(noticeInfo);
		}
		return noticeList;
	}
	
	public Date convertStringToDate(Object obj){
		if(obj==null){
			return null;
		}
		String time = obj.toString();
		if(time==null || time.equals("")){
			return null;
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public int findNoticeInfoCount(String conditionSql) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from t_wf_core_noticeinfo t, zwkj_employee t2, t_wf_process t3")
			  .append("  where t.status = '0' and t2.employee_guid = t.userid and t3.wf_process_uid = t.fprocessid ")
			  .append(conditionSql);
		Query query = super.getEm().createNativeQuery(buffer.toString());
		return Integer.parseInt(getEm().createNativeQuery(buffer.toString()).getSingleResult().toString());
	}
	
	@Override
	public int findXMCount(String conditionSql) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from T_WF_OFFICE_XXZXLXSP t")
		.append("  where 1=1 ")
		.append(conditionSql);
		Query query = super.getEm().createNativeQuery(buffer.toString());
		return Integer.parseInt(getEm().createNativeQuery(buffer.toString()).getSingleResult().toString());
	}

	@Override
	public NoticeInfo findNoticeInfoByfprocessId(String fprocessId) {
		String sql = "select t.* from T_WF_CORE_NOTICEINFO t where t.fprocessId='"+fprocessId+"'";
		List<NoticeInfo> list = getEm().createNativeQuery(sql, NoticeInfo.class).getResultList();
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	@Override
	public List<Object[]> findXMList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.XMMC ,t.XMBH,t.INSTANCEID" )
			  .append("  from T_WF_OFFICE_XXZXLXSP t ")
			  .append("  where 1=1 ")
		      .append(conditionSql)
		      .append(" order by t.ZBRQ desc");
		Query query = super.getEm().createNativeQuery(buffer.toString());
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList(); 
		return list;
	}

}
