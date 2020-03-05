package cn.com.trueway.functions.meeting.service;

import java.sql.Clob;
import java.util.List;

import cn.com.trueway.functions.meeting.entity.Chry;
import cn.com.trueway.functions.meeting.entity.Chry4Out;
import cn.com.trueway.functions.meeting.entity.Hyqj;
import cn.com.trueway.functions.meeting.entity.Hytz;
import cn.com.trueway.functions.meeting.entity.Sgtjhytz;

public interface MeetingService {
	
	int getCountOfMyRegister(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo);
	
	List<Hytz> getMyRegisterList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo);
	
	int getCountOfMyChmd(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo);
	
	List<Hytz> getMyChmdList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo);
	
	int getCountOfMyHyqj(String conditionSql,String userId, String itemid,String searchDateFrom,String searchDateTo);
	
	List<Hytz> getMyHyqjList(String conditionSql, String userId,Integer pageIndex, Integer pageSize, String itemid,String searchDateFrom,String searchDateTo);
	
	Clob getPerson(String instanceId,String columnName);
	
	int getCountOfChmd(String eploeIds);
	
	List<Chry> getChmdList(Integer pageIndex, Integer pageSize,String eploeIds);
	
	int getCountOfHyqj(String instanceId);
	
	List<Hyqj> getHyqjList(Integer pageIndex, Integer pageSize,String instanceId);
	
	List<Sgtjhytz> getAllHy(Integer pageIndex, Integer pageSize,String conditionSql);
	
	int getAllHyCount(String conditionSql);
	
	List<String> getQjry(String instanceId);
	
	int getCountMeetingOuts(String depids, String conditionSql);
	
	List<Hytz> getMeetingOutList(String depids, String conditionSql,Integer pageIndex, Integer pageSize);
	
	
	List<Chry4Out> getChmdList4Out(Integer pageIndex, Integer pageSize,String eploeIds);
	List getMastUserid(String instanceId);
	
	List getSupDepIdByUserid(String userid);
	
	int checkMeetingTime(String meetingBeginTime, String meetingEndTime, String roomName);
}
