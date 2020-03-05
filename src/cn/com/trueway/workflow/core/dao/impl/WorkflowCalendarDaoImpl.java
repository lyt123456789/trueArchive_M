/**
 * 文件名称:WorkflowCalendarDaoImpl.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午05:02:54
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.workflow.core.dao.WorkflowCalendarDao;
import cn.com.trueway.workflow.core.pojo.WorkCalendar;
import cn.com.trueway.workflow.core.pojo.WorkCalendarDetail;

/**
 * 描述：工作日历相关库操作实现
 * 作者：WangXF<br>
 * 创建时间：2011-12-30 下午05:02:54<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class WorkflowCalendarDaoImpl extends BaseDao implements WorkflowCalendarDao {

	/**
	 * 
	 * 描述：增加工作日历
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:22
	 */
	public void addCalendar(WorkCalendar calendar){
		if(calendar!=null){
			calendar.setId(null);
			getEm().persist(calendar);
		}
	}
	
	/**
	 * 
	 * 描述：根据ID删除工作日历
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:28
	 */
	public void deleteCalendar(String calendarId){
		String hql = "delete WorkCalendar c where c.id=:id";
		Query query=getEm().createQuery(hql).setParameter("id", calendarId);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 描述：更新工作日历
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:31
	 */
	public void updateCalendar(WorkCalendar calendar){
		if(calendar!=null&&calendar.getId()!=null){
			getEm().merge(calendar);
		}
	}

	/**
	 * 
	 * 描述：根据ID查找工作日历
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:34
	 */
	public WorkCalendar findCalendar(String calendarId){
		if(calendarId==null){
			return null;
		}
		return getEm().find(WorkCalendar.class, calendarId);
	}
	
	/**
	 * 
	 * 描述：增加CalendarDetail
	 *
	 * @param calendarDetail void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:38
	 */
	public void addCalendarDetail(WorkCalendarDetail calendarDetail){
		if(calendarDetail!=null&&calendarDetail.getId()==null){
			getEm().persist(calendarDetail);
		}
	}
	
	/**
	 * 
	 * 描述：根据calendarDetailId删除CalendarDetail
	 *
	 * @param calendarDetailId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:41
	 */
	public void deleteCalendarDetail(String calendarDetailId){
		String hql = "delete WorkCalendarDetail c where c.id=:id";
		Query query=getEm().createQuery(hql).setParameter("id", calendarDetailId);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 描述：根据calendarId删除CalendarDetail
	 *
	 * @param calendarId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午05:01:24
	 */
	public void deleteAllCalendarDetail(String calendarId){
		String hql = "delete WorkCalendarDetail c where c.calendarId=:calendarId";
		Query query=getEm().createQuery(hql).setParameter("calendarId", calendarId);
		query.executeUpdate();
	}
	
	
	/**
	 * 
	 * 描述：更新CalendarDetail
	 *
	 * @param calendar void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:45
	 */
	public void updateCalendarDetail(WorkCalendarDetail calendarDetail){
		getEm().merge(calendarDetail);
	}

	/**
	 * 
	 * 描述：查找CalendarDetail
	 *
	 * @param calendarDetailId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:49
	 */
	public WorkCalendarDetail findCalendarDetail(String calendarDetailId){
		if(calendarDetailId==null){
			return null;
		}
		return getEm().find(WorkCalendarDetail.class, calendarDetailId);
	}
	
	/**
	 * 
	 * 描述：根据calendarDetailId和yearMonth查找CalendarDetail
	 *
	 * @param calendarDetailId
	 * @param yearMonth
	 * @return CalendarDetail
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午06:02:01
	 */
	@SuppressWarnings("unchecked")
	public WorkCalendarDetail findCalendarDetail(String calendarId,String yearMonth){
		String hql = "from WorkCalendarDetail c where c.calendarId=:calendarId and c.yearMonth=:yearMonth";
		Query query=getEm().createQuery(hql).setParameter("calendarId", calendarId).setParameter("yearMonth", yearMonth);
		List<WorkCalendarDetail> list = query.getResultList();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：查找所有CalendarDetail
	 *
	 * @param calendarId
	 * @return List<CalendarDetail>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-30 下午04:58:52
	 */
	@SuppressWarnings("unchecked")
	public List<WorkCalendarDetail> findAllCalendarDetail(String calendarId,String yearMonth){
		String hql = "from WorkCalendarDetail c where c.calendarId=:calendarId ";
		if(yearMonth != null) hql += "and c.yearMonth like '%"+yearMonth+"%' ";
		hql += " order by c.yearMonth";
		Query query=getEm().createQuery(hql).setParameter("calendarId", calendarId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkCalendar> findCalendars(String webId,Integer pageindex, Integer pagesize){
//		String hql = "from WorkCalendar c where c.webId = :webid";
//		Query query=getEm().createQuery(hql).setParameter("webid", webId);
		String hql = "from WorkCalendar c";
		Query query=super.getEm().createQuery(hql);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();
	}

	public WorkCalendar getWorkCalendar(String id) {
		try {
			return getEm().find(WorkCalendar.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public int workCalendarIsSame(WorkCalendar calendar) {
		String hql = "select count(c.id) from WorkCalendar c where c.chName = :chName or c.enName = :enName";
		return ((Long)getEm().createQuery(hql).setParameter("chName", calendar.getChName()).setParameter("enName", calendar.getEnName()).getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<String> getCalendarYear(String calendarId) {
		String sql = "select distinct substr(t.year_month,0,4) from WF_CALENDAR_DETAIL t where t.calendar_id = :id";
		return getEm().createNativeQuery(sql).setParameter("id", calendarId).getResultList();
	}
	
	public DTPageBean getSuperintend(int pageIndex,int pageSize,List<String> depid,String title,Integer importent, String isover){
		//查询结果集语句
		StringBuffer querySql = new StringBuffer()
		.append("select t.WF_PROCESS_UID as wf_process_uid," )
		.append(" t.WF_ST_UID as wf_st_uid," ) 
		.append(" t.WF_INSTANCE_UID as wf_instance_uid," ) 
		.append(" t.PROCESS_TITLE as process_title," ) 
		.append(" t.APPLY_TIME as apply_time," ) 
		.append(" t.FINSH_TIME as finish_time," ) 
		.append(" s.WF_ST_NAME as wf_st_name, ")
		.append(" (select e.EMPLOYEE_NAME" ) 
		.append(" from RISENET_EMPLOYEE e" ) 
		.append(" where e.EMPLOYEE_GUID = t.owner) as employee_name," ) 
		.append(" t.IS_OVER as is_over," ) 
		.append(" t.IMPORTANT_STATE as important_state," ) 
		.append(" t.EXPIRE_TIME as expire_time," )
		.append(" (select e.EMPLOYEE_NAME" ) 
		.append(" from RISENET_EMPLOYEE e" ) 
		.append(" where e.EMPLOYEE_GUID = t.FROMUSERID) as from_uesr," ) 
		.append(" (select e.EMPLOYEE_NAME")
		.append(" from RISENET_EMPLOYEE e" ) 
		.append(" where e.EMPLOYEE_GUID = t.USER_UID) as deal_uesr " );
		//查询总数语句
		String totalRowsSql = "select count(t.WF_PROCESS_UID) ";
		//条件
		StringBuffer commen = new StringBuffer()
		.append("  from WF_PROCESS t, WF_STEP s, WF_DEFINE d") 
		.append(" where t.WF_ST_UID = s.WF_ST_UID")
		.append("   and s.WF_UID = d.WF_UID")
		.append(" and ( ");
		for(int i=0;i<depid.size();i++){
			commen.append(" d.DEPIDS like '%").append(depid.get(i)).append("%'   or");
		}
		commen = new StringBuffer(commen.substring(0, commen.length()-3));
		commen.append(" ) ");
		
		if(title != null)
		commen.append(" and process_title like '%").append(title).append("%' ");
		if(importent != null)
		commen.append(" and important_state =").append(importent);
		if(isover != null)
		commen.append(" and is_over ='").append(isover).append("' ");
		//commen.append("and t.IS_END!=2 and to_char(t.FINSH_TIME,'yyyy-MM-dd') !='").append(new Date(0)).append("' and t.EXPIRE_TIME is not null order by t.WF_INSTANCE_UID");
		commen.append("and t.IS_END!=2 and (decode(to_char(t.FINSH_TIME, 'yyyy-MM-dd'),'1970-01-01','1') != '1' or decode(to_char(t.FINSH_TIME, 'yyyy-MM-dd'),'1970-01-01','1') is null) and t.EXPIRE_TIME is not null order by t.WF_INSTANCE_UID");
		
		return pagingQuery(totalRowsSql+commen, querySql.toString()+commen, pageIndex, pageSize, "Superintend");
		
	}

	public int getCountCalendars() {
		String hql="from WorkCalendar where 1=1";
		return super.getEm().createQuery(hql).getResultList().size();
	}

	@SuppressWarnings("unchecked")
	public List<WorkCalendar> getCalendars(String webId) {
		String hql="from WorkCalendar where 1=1";
		return super.getEm().createQuery(hql).getResultList();
	}
}
