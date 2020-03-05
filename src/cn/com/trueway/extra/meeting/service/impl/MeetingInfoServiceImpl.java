/**
 * 文件名称:MeetingInfoServiceImpl.java
 * 作者:zhuxc<br>
 * 创建时间:2013-12-27 下午02:54:22
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.extra.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.extra.meeting.bean.MeetingApply;
import cn.com.trueway.extra.meeting.bean.MeetingApplyOut;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.dao.MeetingInfoDao;
import cn.com.trueway.extra.meeting.service.MeetingInfoService;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfProcess;

/**
 * 描述： 对MeetingInfoServiceImpl进行描述
 * 作者：zhuxc
 * 创建时间：2013-12-27 下午02:54:22
 */
public class MeetingInfoServiceImpl implements MeetingInfoService {
	MeetingInfoDao meetingInfoDao;
	TableInfoDao tableInfoDao;

	public MeetingInfoDao getMeetingInfoDao() {
		return meetingInfoDao;
	}

	public void setMeetingInfoDao(MeetingInfoDao meetingInfoDao) {
		this.meetingInfoDao = meetingInfoDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	/**
	 * @param bigDepId
	 * @param conditionSql
	 * @return
	 */
	@Override
	public int getCountDeptMeetings(String bigDepId, String conditionSql) {
		return meetingInfoDao.getCountDeptMeetings(bigDepId,conditionSql);
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
		List<MeetingApply> dofileList = new ArrayList<MeetingApply>();
		List<MeetingApply> dofList = meetingInfoDao.getDeptMeetingList(bigDepId,conditionSql,pageIndex,pageSize);
		for (MeetingApply doFile : dofList) {
			MeetingApply doFileBak = doFile;
			doFileBak.setDoFile_title(doFile.getDoFile_title());
			doFileBak.setItem_id(doFile.getItem_id());
			doFileBak.setWorkflowId(doFile.getWorkflowId());
			doFileBak.setInstanceId(doFile.getInstanceId());
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFileBak.setDoTime(wfProcess.getFinshTime());
							}else{
								doFileBak.setDoTime(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFileBak.setDoFile_result(result);
			}else{
				doFileBak.setDoFile_result(doFile.getDoFile_result());
				doFileBak.setDoTime(doFile.getDoTime());
			}
			if(CommonUtil.stringIsNULL(doFile.getItem_name())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItem_id());
				doFileBak.setItem_name(itemName);
			}else{
				doFileBak.setItem_name(doFile.getItem_name());
			}
			if(processList.size()>0 && processList != null){
				doFileBak.setProcessId(processList.get(0).getWfProcessUid());
			}
			doFileBak.setFormId(doFile.getFormId());
			doFileBak.setNodeId(doFile.getNodeId());
			if(StringUtils.isNotBlank(doFile.getRoomName()) && doFile.getRoomName().split("\\*").length>1){
				doFileBak.setRoomName(doFile.getRoomName().split("\\*")[1]);
			}
			dofileList.add(doFileBak);
		}
		return dofileList;
//		return meetingInfoDao.getDeptMeetingList(bigDepId,conditionSql,pageIndex,pageSize);
	}

	/**
	 * @param instanceId
	 * @param state
	 */
	@Override
	public void updateState(String instanceId, String state) {
		meetingInfoDao.updateState(instanceId,state);
		
	}

	@Override
	public int getCountMeetings(String conditionSql) {
		return meetingInfoDao.getCountMeetings(conditionSql);
	}

	@Override
	public List<MeetingphoApply> getMeetingList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		return meetingInfoDao.getMeetingList(conditionSql, pageIndex, pageSize);
	}

	@Override
	public int getCountMeetingOuts(String depids, String conditionSql) {
		return meetingInfoDao.getCountMeetingOuts(depids, conditionSql);
	}

	@Override
	public List<MeetingApplyOut> getMeetingOutList(String depids,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		return meetingInfoDao.getMeetingOutList(depids, conditionSql, pageIndex, pageSize);
	}

	@Override
	public List<Employee> getPersonName(String instanceId) {
		return meetingInfoDao.getPersonName(instanceId);
	}
	
}
