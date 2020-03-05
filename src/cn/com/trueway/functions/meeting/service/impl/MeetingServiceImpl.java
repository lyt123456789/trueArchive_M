package cn.com.trueway.functions.meeting.service.impl;

import java.sql.Clob;
import java.util.List;

import cn.com.trueway.functions.meeting.dao.MeetingDao;
import cn.com.trueway.functions.meeting.entity.Chry;
import cn.com.trueway.functions.meeting.entity.Chry4Out;
import cn.com.trueway.functions.meeting.entity.Hyqj;
import cn.com.trueway.functions.meeting.entity.Hytz;
import cn.com.trueway.functions.meeting.entity.Sgtjhytz;
import cn.com.trueway.functions.meeting.service.MeetingService;

public class MeetingServiceImpl implements MeetingService {
	private MeetingDao meetingDao;
	
	public MeetingDao getMeetingDao() {
		return meetingDao;
	}
	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}
	
	
	public int getCountOfMyRegister(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		return meetingDao.getCountOfMyRegister( conditionSql,userId, itemid,searchDateFrom,searchDateTo);
	}
	
	@Override
	public List<Hytz> getMyRegisterList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		List<Hytz> regList = meetingDao.getMyRegisterList(conditionSql,userId, pageIndex, pageSize,itemid,searchDateFrom,searchDateTo);
		
		return regList;
	}
	
	public int getCountOfMyChmd(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		return meetingDao.getCountOfMyRegister( conditionSql,userId, itemid,searchDateFrom,searchDateTo);
	}
	
	@Override
	public List<Hytz> getMyChmdList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		List<Hytz> regList = meetingDao.getMyRegisterList(conditionSql,userId, pageIndex, pageSize,itemid,searchDateFrom,searchDateTo);
		
		return regList;
	}
	
	public int getCountOfMyHyqj(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo) {
		return meetingDao.getCountOfMyRegister( conditionSql,userId, itemid,searchDateFrom,searchDateTo);
	}
	
	@Override
	public List<Hytz> getMyHyqjList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo) {
		List<Hytz> regList = meetingDao.getMyHyqjList(conditionSql,userId, pageIndex, pageSize,itemid,searchDateFrom,searchDateTo);
		
		return regList;
	}
	
	@Override
	public Clob getPerson(String instanceId,String columnName) {
		return meetingDao.getPerson(instanceId,columnName);
	}
	
	public int getCountOfChmd(String eploeIds){
		return meetingDao.getCountOfChmd(eploeIds);
	}
	
	public List<Chry> getChmdList(Integer pageIndex, Integer pageSize,String eploeIds){
		return meetingDao.getChmdList(pageIndex,pageSize,eploeIds);
	}
	
	public int getCountOfHyqj(String instanceId){
		return meetingDao.getCountOfHyqj(instanceId);
	}
	
	public List<Hyqj> getHyqjList(Integer pageIndex, Integer pageSize,String instanceId){
		return meetingDao.getHyqjList(pageIndex,pageSize,instanceId);
	}
	
	public List<Sgtjhytz> getAllHy(Integer pageIndex, Integer pageSize,String conditionSql){
		return meetingDao.getAllHy(pageIndex,pageSize,conditionSql);
	}
	
	public int getAllHyCount(String conditionSql){
		return meetingDao.getAllHyCount(conditionSql);
	}
	
	public List<String> getQjry(String instanceId){
		return meetingDao.getQjry(instanceId);
	}
	
	@Override
	public int getCountMeetingOuts(String depids, String conditionSql) {
		return meetingDao.getCountMeetingOuts(depids, conditionSql);
	}

	@Override
	public List<Hytz> getMeetingOutList(String depids,String conditionSql, Integer pageIndex, Integer pageSize) {
		return meetingDao.getMeetingOutList(depids, conditionSql, pageIndex, pageSize);
	}
	
	public List<Chry4Out> getChmdList4Out(Integer pageIndex, Integer pageSize,String eploeIds){
		return meetingDao.getChmdList4Out(pageIndex,pageSize,eploeIds);
	}
	public List getMastUserid(String instanceId){
		return meetingDao.getMastUserid(instanceId);
	}
	public List getSupDepIdByUserid(String userid){
		return meetingDao.getSupDepIdByUserid(userid);
	}
	
	public int checkMeetingTime(String meetingBeginTime, String meetingEndTime, String roonName){
		return meetingDao.checkMeetingTime(meetingBeginTime, meetingEndTime,roonName);
	}

}
