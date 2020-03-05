/**
 * 文件名称:MeetingInfoDao.java
 * 作者:zhuxc<br>
 * 创建时间:2013-12-27 下午02:54:46
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.extra.meeting.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.extra.meeting.bean.MeetingApply;
import cn.com.trueway.extra.meeting.bean.MeetingApplyOut;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.dao.MeetingInfoDao;
import cn.com.trueway.extra.meeting.util.MeetingConstant;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;


/**
 * 描述： 对MeetingInfoDao进行描述
 * 作者：zhuxc
 * 创建时间：2013-12-27 下午02:54:46
 */
public class MeetingInfoDaoImpl extends BaseDao implements MeetingInfoDao{

	
	/**
	 * @param bigDepId
	 * @param conditionSql
	 * @return
	 */
	@Override
	public int getCountDeptMeetings(String bigDepId, String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select t.*,m.roomName as roomName,m.meeting_Begin_Time as meetingBeginTime,m.meeting_End_Time as meetingEndTime,m.state as state from t_wf_core_dofile t,T_WF_CORE_ITEM i,"+MeetingConstant.MEETING_TABLE+" m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null) ");
		sb.append(" and t.INSTANCEID=m.INSTANCEID");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		sb.append(conditionSql).append(" order by m.meeting_Begin_Time desc");
		return super.getEm().createNativeQuery(sb.toString(), DoFile.class).getResultList().size();
	}

	/**
	 * @param bigDepId
	 * @param conditionSql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<MeetingApply> getDeptMeetingList(String bigDepId,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append( "select t.*,m.roomName as roomName,m.meeting_Begin_Time as meetingBeginTime,m.meeting_End_Time as meetingEndTime,m.state as state from t_wf_core_dofile t,T_WF_CORE_ITEM i,"+MeetingConstant.MEETING_TABLE+" m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null) ");
		sb.append(" and t.INSTANCEID=m.INSTANCEID");
		if ( CommonUtil.stringNotNULL(bigDepId)){
			sb.append(" and i.vc_ssbmid='" + bigDepId + "' ");
		}
		sb.append(conditionSql).append(" order by m.state desc,m.meeting_Begin_Time desc");
		Query query=super.getEm().createNativeQuery(sb.toString(),MeetingApply.class);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();  
	}

	/**
	 * @param instanceId
	 * @param state
	 */
	@Override
	public void updateState(String instanceId, String state) {
		String sql = "update "+MeetingConstant.MEETING_TABLE+" t set t.state='"+state+"' where t.instanceid='"+instanceId+"'";
		this.getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public int getCountMeetings(String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_wf_core_dofile t,t_wf_office_applymeet m where t.instanceid=m.instanceid and (t.isdelete!=1 or t.isdelete is  null)");
		sb.append(conditionSql);
		return super.getEm().createNativeQuery(sb.toString()).getResultList().size();
	}

	@Override
	public List<MeetingphoApply> getMeetingList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		List<MeetingphoApply> returnlist = new ArrayList<MeetingphoApply>();
		StringBuffer sb = new StringBuffer("select m.roomname,m.meeting_begin_time,m.meeting_end_time,m.state,m.instanceid,m.workflowid,m.formid,m.processid,t.item_id");
		sb.append(" from t_wf_core_dofile t,t_wf_office_applymeet m where t.instanceid=m.instanceid and (t.isdelete!=1 or t.isdelete is  null) ");
		sb.append(conditionSql).append(" order by m.meeting_begin_time desc");
		Query query=super.getEm().createNativeQuery(sb.toString());
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		if(null !=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				MeetingphoApply meetingphoapply = new MeetingphoApply();
				meetingphoapply.setRoomname(obj[0]==null?"":obj[0].toString());
				meetingphoapply.setMeeting_begin_time(obj[1]==null?"":obj[1].toString());
				meetingphoapply.setMeeting_end_time(obj[2]==null?"":obj[2].toString());
				meetingphoapply.setState(obj[3]==null?"":obj[3].toString());
				meetingphoapply.setInstanceid(obj[4]==null?"":obj[4].toString());
				meetingphoapply.setWorkflowid(obj[5]==null?"":obj[5].toString());
				meetingphoapply.setFormid(obj[6]==null?"":obj[6].toString());
				meetingphoapply.setProcessid(obj[7]==null?"":obj[7].toString());
				meetingphoapply.setItem_id(obj[8]==null?"":obj[8].toString());
				returnlist.add(meetingphoapply);
			}
		}
		return returnlist;
	}

	/*@Override
	public int getCountMeetingOuts(String depids, String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_wf_core_dofile t,t_wf_core_item i,t_wf_office_applymeetout m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null) ");
		sb.append(" and t.instanceid=m.instanceid and t.dofile_result='2' and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24\"时\"mi\"分\"')");
		if ( CommonUtil.stringNotNULL(depids)){
			sb.append(" and i.vc_ssbmid='" + depids + "' ");
		}
		return super.getEm().createNativeQuery(sb.toString()).getResultList().size();
	}*/
	
	@Override
	public int getCountMeetingOuts(String depids, String conditionSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_wf_core_dofile t,t_wf_core_item i,t_wf_office_applymeetout m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null) ");
		sb.append(" and t.instanceid=m.instanceid and t.dofile_result='2' and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24\"时\"mi\"分\"')");
		if ( CommonUtil.stringNotNULL(depids)){
			sb.append(" and i.vc_ssbmid='" + depids + "' ");
		}
		return super.getEm().createNativeQuery(sb.toString()).getResultList().size();
	}

	@Override
	public List<MeetingApplyOut> getMeetingOutList(String depids,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		List<MeetingApplyOut> returnlist = new ArrayList<MeetingApplyOut>();
		StringBuffer sb = new StringBuffer();
		sb.append("select m.meetingname,m.newtitle,m.newtime,m.arr,m.instanceid from t_wf_core_dofile t,T_WF_CORE_ITEM i,t_wf_office_applymeetout m where t.item_id=i.id and (t.isdelete!=1 or t.isdelete is  null)");
		sb.append(" and t.instanceid=m.instanceid and t.dofile_result='2' and sysdate <= to_date(m.newtime,'YYYY-MM-dd HH24\"时\"mi\"分\"')");
		if ( CommonUtil.stringNotNULL(depids)){
			sb.append(" and i.vc_ssbmid='" + depids + "' ");
		}
		sb.append(conditionSql).append(" order by m.newtime desc");
		Query query=super.getEm().createNativeQuery(sb.toString());
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		if(null !=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				MeetingApplyOut meetingapplyout = new MeetingApplyOut();
				meetingapplyout.setMeetingname(obj[0]==null?"":obj[0].toString());
				meetingapplyout.setNewtitle(obj[1]==null?"":obj[1].toString());
				meetingapplyout.setNewtime(obj[2]==null?"":obj[2].toString());
				meetingapplyout.setArr(obj[3]==null?"":obj[3].toString());
				meetingapplyout.setInstanceid(obj[4]==null?"":obj[4].toString());
				returnlist.add(meetingapplyout);
			}
		}
		return returnlist;
	}

	@Override
	public List<Employee> getPersonName(String instanceId) {
		List<Employee> emplist = new ArrayList<Employee>();
		if(null != instanceId && !"".equals(instanceId)){
			StringBuffer sql = new StringBuffer();
			sql.append("select t2.department_name,t1.employee_name,t1.employee_jobtitles,t1.employee_mobile");
			sql.append(" from zwkj_employee t1");
			sql.append(" inner join zwkj_department t2 on t2.department_guid = t1.department_guid");
			sql.append(" where 1=1 ");
			String id = "";
			StringBuffer personsql = new StringBuffer();
			personsql.append("select to_char(t.personid),to_char(t.dxryid) from t_wf_office_applymeetout t");
			personsql.append(" where t.instanceid = '");
			personsql.append(instanceId);
			personsql.append("'");
			List<Object[]> perlist = super.getEm().createNativeQuery(personsql.toString()).getResultList();
			if(null != perlist && perlist.size()>0){
				Object[] obj = perlist.get(0);
				if(null != obj[0]){
					String[] personid = obj[0].toString().split(",");
					for (String str : personid) {
						id = id + "'"+str+"',";
					}
				}
				if(null != obj[1]){
					String[] chryid = obj[1].toString().split(",");
					for (String str : chryid) {
						id = id + "'"+str+"',";
					}
				}
			}
			if(!"".equals(id)){
				id = id.substring(0, id.length()-1);
				sql.append(" and t1.employee_guid in (");
				sql.append(id);
				sql.append(")");
				sql.append(" order by t2.department_name");
				List<Object[]> list = this.getEm().createNativeQuery(sql.toString()).getResultList();
				if (list != null && list.size() > 0) {
					for(int k=0;k<list.size();k++){
						Object[] obj = list.get(k);
						Employee e = new Employee();
						e.setDepartmentGuid(obj[0]==null?"":obj[0].toString());
						e.setEmployeeName(obj[1]==null?"":obj[1].toString());
						e.setEmployeeJobtitles(obj[2]==null?"":obj[2].toString());
						e.setEmployeeMobile(obj[3]==null?"":obj[3].toString());
						emplist.add(e);
					}
				}
			}
		}
		return emplist;
	}
}
