package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

public class PushMobileMsg {

	private String id;
	
	private String instanceId;
	
	private String pushEmpId;//推送的人id
	
	private String pushEmpName;//推送的人名字
	
	private String pushedEmpId;//被推送的人id
	
	private String message;//推送的消息
	
	private String pushTime ;
	
	private String  finstanceid;//被推送实例id
	
	private String pushKs;//科室
	private List<AttElement> atts;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getPushEmpId() {
		return pushEmpId;
	}
	public void setPushEmpId(String pushEmpId) {
		this.pushEmpId = pushEmpId;
	}
	public String getPushEmpName() {
		return pushEmpName;
	}
	public void setPushEmpName(String pushEmpName) {
		this.pushEmpName = pushEmpName;
	}
	public String getPushedEmpId() {
		return pushedEmpId;
	}
	public void setPushedEmpId(String pushedEmpId) {
		this.pushedEmpId = pushedEmpId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public String getFinstanceid() {
		return finstanceid;
	}
	public void setFinstanceid(String finstanceid) {
		this.finstanceid = finstanceid;
	}
	public String getPushKs() {
		return pushKs;
	}
	public void setPushKs(String pushKs) {
		this.pushKs = pushKs;
	}
	public List<AttElement> getAtts() {
		return atts;
	}
	public void setAtts(List<AttElement> atts) {
		this.atts = atts;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
