package cn.com.trueway.workflow.core.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *流程基本表
 * 
 */
@Entity
@Table(name = "WF_BasicFlow")
public class BasicFlow implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7457134915716417836L;
	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "uuid", length = 36)
	private String uuid;
	/**
	 * 流程名称
	 */
	@Column(name = "workFlowName",length=100)
	private String workFlowName;
	
	/**
	 * 创建者id
	 */
	@Column(name = "createUserId")
	private String createUserId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private Timestamp createTime = new Timestamp(new Date().getTime());
	
	/**
	 * 更新时间
	 */
	@Column(name = "updateTime")
	private Timestamp updateTime = new Timestamp(new Date().getTime());
	
	/**
	 * 办理期限，整数
	 */
	@Column(name = "timeLimit")
	private Integer timeLimit = 5;
	
	/**
	 * 期限类型 0分 1时 2天 3周 4月 5年 6工作日  
	 * 当选择工作日时 通过日历筛选
	 */
	@Column(name = "timeLimitType")
	private int timeLimitType = 3;
	
	/**
	 * 工作日历id
	 */
	@Column(name = "workCalendarId",length=100)
	private String workCalendarId;
	
	/**
	 * 启动任务的id
	 */
	@Column(name = "startTaskId")
	private String startTaskId;
	
	/**
	 * 录入表格id
	 */
	@Column(name = "formId")
	private String formId;
	
	/**
	 * 查询表格id
	 */
	@Column(name = "searchFormId")
	private String searchFormId;
	
	/**
	 * 简单查询表格id
	 */
	@Column(name = "simpleSearchFormId")
	private String simpleSearchFormId;
	
	/**
	 * 是否需要留痕 0否 1 是
	 */
	@Column(name = "marking")
	private int marking = 1;
	
	/**
	 * 该工作流状态 0 调试 1运行
	 */
	@Column(name = "status")
	private int status = 0;
	
	/**
	 * 标题字段 指定该表单某字段作为标题使用
	 */
	@Column(name = "title")
	private String title;
	
	/**
	 * 流程类型 1无自由流 2完全自由流 3嵌入自由流 
	 */
	@Column(name = "flowType")
	private int flowType = 1;
	
	/**
	 * 流程分类名称
	 */
	@Column(name = "flowTypeName")
	private String flowTypeName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateTime() {
		
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getTimeLimitType() {
		return timeLimitType;
	}

	public void setTimeLimitType(int timeLimitType) {
		this.timeLimitType = timeLimitType;
	}

	public String getWorkCalendarId() {
		return workCalendarId;
	}

	public void setWorkCalendarId(String workCalendarId) {
		this.workCalendarId = workCalendarId;
	}

	public String getStartTaskId() {
		return startTaskId;
	}

	public void setStartTaskId(String startTaskId) {
		this.startTaskId = startTaskId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getSearchFormId() {
		return searchFormId;
	}

	public void setSearchFormId(String searchFormId) {
		this.searchFormId = searchFormId;
	}

	public String getSimpleSearchFormId() {
		return simpleSearchFormId;
	}

	public void setSimpleSearchFormId(String simpleSearchFormId) {
		this.simpleSearchFormId = simpleSearchFormId;
	}

	public int getMarking() {
		return marking;
	}

	public void setMarking(int marking) {
		this.marking = marking;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFlowType() {
		return flowType;
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}

	public String getFlowTypeName() {
		return flowTypeName;
	}

	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}

	public BasicFlow() {
		super();
	}
	
	
	
}