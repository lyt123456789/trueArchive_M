/**
 * 文件名称:MeetingApply.java
 * 作者:zhuxc<br>
 * 创建时间:2013-12-27 下午03:11:37
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.extra.meeting.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

/**
 * 描述： 已申请的会议
 * 作者：zhuxc
 * 创建时间：2013-12-27 下午03:11:37
 */
@Entity
@SqlResultSetMapping(name="MeetingApply", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.extra.meeting.bean.MeetingApply.class,
            	fields={
	            	@FieldResult(name="id", column="id"),
					@FieldResult(name="doFile_title", column="doFile_title"),
					@FieldResult(name="item_id", column="item_id"),
					@FieldResult(name="item_name", column="item_name"),
					@FieldResult(name="doFile_result", column="doFile_result"),	
					@FieldResult(name="workflowId", column="workflowId"),
					@FieldResult(name="instanceId", column="instanceId"),
					@FieldResult(name="formId", column="formId"),
					@FieldResult(name="nodeId", column="nodeId"),
					@FieldResult(name="doTime", column="doTime"),
					@FieldResult(name="roomName", column="roomName"),
					@FieldResult(name="meetingBeginTime", column="meetingBeginTime"),
					@FieldResult(name="meetingEndTime", column="meetingEndTime"),
					@FieldResult(name="state", column="state"),
	            	
                })
        }
)
public class MeetingApply {

	private static final long serialVersionUID = -3601053263282892187L;
	@Id
	private String id;
	private String doFile_title;
	private String item_id;
	private String item_name;
	private Integer doFile_result;
	private String workflowId;
	private String instanceId;
	private String formId;
	private String nodeId;
	private Date doTime;
//	private String roomId;//会议室ID
	private String roomName;//会议室名称
//	private String userId;	//申请人id
//	private String userName;//申请人名称
//	private String departmentId;//申请部门id
//	private String departmentName;//申请部门名称
	private String meetingBeginTime;//会议开始时间
	private String meetingEndTime;//会议结束时间
	private String state;//审核状态
	@Transient
	private String processId;

	
	public String getDoFile_title() {
		return doFile_title;
	}
	public void setDoFile_title(String doFile_title) {
		this.doFile_title = doFile_title;
	}
	
	
	public Integer getDoFile_result() {
		return doFile_result;
	}
	public void setDoFile_result(Integer doFile_result) {
		this.doFile_result = doFile_result;
	}
	
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getMeetingBeginTime() {
		return meetingBeginTime;
	}
	public void setMeetingBeginTime(String meetingBeginTime) {
		this.meetingBeginTime = meetingBeginTime;
	}
	public String getMeetingEndTime() {
		return meetingEndTime;
	}
	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public Date getDoTime() {
		return doTime;
	}
	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
