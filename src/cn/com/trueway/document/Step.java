package cn.com.trueway.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：流程步骤定义表<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 12:16:15 PM 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
@Entity
@Table(name = "WF_STEP")
public class Step implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7014627793517702644L;
	/**
	 * 唯一标识
	 */
	private String wfStUid;
	
	/**
	 * 对应流程的defineId
	 */
	private String wfUid;
	
	/**
	 * 步骤名称
	 */
	private String wfStName;
	/**
	 * 节点类型（0表示顺序，1表示并行，2表示竞争）
	 */
	private Long wfStType;
	/**
	 * Step显示时的顺序(从0开始，数字越大，显示的位置越往后)
	 */
	private Long showIndex;

	/**
	 * 本步骤对应的表单
	 */
	private String formUrl;

	/**
	 * 本步骤对应公文的待办状态的描述，该字段用于维护待办列表
	 */
	private String map2DocStatus;

	/**
	 * 本步骤对应公文的已办状态的描述，该字段用于维护待办列表
	 */
	private String map2DocStatusOver;

	/**
	 * 本步骤的办理类型，该字段用于维护步骤记录表
	 */
	private String dealType;

	/**
	 * 本步骤对应的表单数据处理方法的url
	 */
	private String dataProcessUrl;

	/**
	 * 本步骤对应的显示表单的url
	 */
	private String showFormUrl;

	/**
	 * 是否是自动发送，“true”为是
	 */
	private String isAuto = "false";

	/**
	 * 是否是结束步骤，“true”为是
	 */
	private String isEnd = "false";
	
	/**
	 * 本步骤数据处理的类型，do表示是action类型，js表示是js类型,默认为do
	 */
	private String dataProcessType;

	/**
	 * 意见类型（0表示审核意见，1表示签发意见）
	 */
	private Long opnType;

	
	private int index;//流程节点序号
	private boolean isLoop;//是否为 循环节点
	private String isRouteAuto;//是否自动 0-否 2-自动
	//修改固定发送人员20101123
	private String toUserId;
	private String toUserName;
	private String toUserType;
	private Integer isBack;
	
	private String multi;
	
	/**
	 * 办理期限，单位：天
	 */
	private Integer timeLimit;
	
	/**
	 * 工作日历
	 */
	private String workCalendar;
	
	/**
	 * 人员树固定展示的部门
	 */
	private String showdep;
	

	// Constructors

	/** default constructor */
	public Step() {
	}

	/** minimal constructor */
	public Step(String wfUid, String wfStName, Long wfStType) {
		this.wfUid = wfUid;
		this.wfStName = wfStName;
		this.wfStType = wfStType;
	}

	public Step(String wfStUid, String toUserId, String toUserName,
			String toUserType) {
		this.wfStUid = wfStUid;
		this.toUserId = toUserId;
		this.toUserName = toUserName;
		this.toUserType = toUserType;
	}
	

	public Step(String wfStUid) {
		this.wfStUid = wfStUid;
	}


	public Step(String wfStUid, String wfStName, Long wfStType, Long showIndex,
			String formUrl, String map2DocStatus, String map2DocStatusOver,
			String dealType, String dataProcessUrl, String showFormUrl,
			String dataProcessType,String multi) {
		this.wfStUid = wfStUid;
		this.wfStName = wfStName;
		this.wfStType = wfStType;
		this.showIndex = showIndex;
		this.formUrl = formUrl;
		this.map2DocStatus = map2DocStatus;
		this.map2DocStatusOver = map2DocStatusOver;
		this.dealType = dealType;
		this.dataProcessUrl = dataProcessUrl;
		this.showFormUrl = showFormUrl;
		this.dataProcessType = dataProcessType;
		this.multi = multi;
	}
	

	public Step(String wfStUid, String wfUid, String wfStName, Long wfStType,
			Long showIndex, String formUrl, String map2DocStatus,
			String map2DocStatusOver, String dealType, String dataProcessUrl,
			String showFormUrl, String isAuto, String isEnd,
			String dataProcessType, Long opnType, int index, boolean isLoop,
			String isRouteAuto, String toUserId, String toUserName,
			String toUserType, Integer isBack, String multi, Integer timeLimit,
			String workCalendar, String showdep) {
		super();
		this.wfStUid = wfStUid;
		this.wfUid = wfUid;
		this.wfStName = wfStName;
		this.wfStType = wfStType;
		this.showIndex = showIndex;
		this.formUrl = formUrl;
		this.map2DocStatus = map2DocStatus;
		this.map2DocStatusOver = map2DocStatusOver;
		this.dealType = dealType;
		this.dataProcessUrl = dataProcessUrl;
		this.showFormUrl = showFormUrl;
		this.isAuto = isAuto;
		this.isEnd = isEnd;
		this.dataProcessType = dataProcessType;
		this.opnType = opnType;
		this.index = index;
		this.isLoop = isLoop;
		this.isRouteAuto = isRouteAuto;
		this.toUserId = toUserId;
		this.toUserName = toUserName;
		this.toUserType = toUserType;
		this.isBack = isBack;
		this.multi = multi;
		this.timeLimit = timeLimit;
		this.workCalendar = workCalendar;
		this.showdep = showdep;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "WF_ST_UID", length = 36)
	public String getWfStUid() {
		return this.wfStUid;
	}

	public void setWfStUid(String wfStUid) {
		this.wfStUid = wfStUid;
	}

	@Column(name = "WF_ST_NAME", nullable = false, length = 30)
	public String getWfStName() {
		return this.wfStName;
	}

	public void setWfStName(String wfStName) {
		this.wfStName = wfStName;
	}

	@Column(name = "WF_ST_TYPE", nullable = false, precision = 1, scale = 0)
	public Long getWfStType() {
		return this.wfStType;
	}

	public void setWfStType(Long wfStType) {
		this.wfStType = wfStType;
	}


	@Column(name = "SHOW_INDEX", nullable = false, precision = 10, scale = 0)
	public Long getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Long showIndex) {
		this.showIndex = showIndex;
	}

	@Column(name = "OPN_TYPE", nullable = true, precision = 10, scale = 0)
	public Long getOpnType() {
		return opnType;
	}

	public void setOpnType(Long opnType) {
		this.opnType = opnType;
	}

	@Column(name = "FORMURL", nullable = true, length = 255)
	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	@Column(name = "MAP2DOCSTATUS", nullable = true, length = 255)
	public String getMap2DocStatus() {
		return map2DocStatus;
	}

	public void setMap2DocStatus(String map2DocStatus) {
		this.map2DocStatus = map2DocStatus;
	}

	@Column(name = "MAP2DOCSTATUSOVER", nullable = true, length = 255)
	public String getMap2DocStatusOver() {
		return map2DocStatusOver;
	}

	public void setMap2DocStatusOver(String map2DocStatusOver) {
		this.map2DocStatusOver = map2DocStatusOver;
	}

	@Column(name = "DEALTYPE", nullable = true, length = 255)
	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	@Column(name = "DATA_PROCESS_URL", nullable = true, length = 255)
	public String getDataProcessUrl() {
		return dataProcessUrl;
	}

	public void setDataProcessUrl(String dataProcessUrl) {
		this.dataProcessUrl = dataProcessUrl;
	}

	@Column(name = "SHOW_FORM_URL", nullable = true, length = 255)
	public String getShowFormUrl() {
		return showFormUrl;
	}

	public void setShowFormUrl(String showFormUrl) {
		this.showFormUrl = showFormUrl;
	}

	@Column(name = "DATA_PROCESS_TYPE", nullable = true, length = 30)
	public String getDataProcessType() {
		return dataProcessType;
	}

	public void setDataProcessType(String dataProcessType) {
		this.dataProcessType = dataProcessType;
	}

	@Transient
	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	@Transient
	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	@Column(name = "TO_USERID", nullable = true)
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	@Column(name = "TO_USERNAME", nullable = true)
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	@Column(name = "TO_USERTYPE", nullable = true)
	public String getToUserType() {
		return toUserType;
	}

	public void setToUserType(String toUserType) {
		this.toUserType = toUserType;
	}
	
	@Column(name = "ISBACK", nullable = true)
	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	@Column(name = "multi", nullable = true)
	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	/**
	 * 办理期限，单位：天
	 */
	@Column(name = "TIME_LIMIT")
	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	/**
	 * 工作日历
	 */
	@Column(name = "WORK_CALENDAR")
	public String getWorkCalendar() {
		return workCalendar;
	}

	public void setWorkCalendar(String workCalendar) {
		this.workCalendar = workCalendar;
	}

	@Transient
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	@Transient
	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	@Transient
	public String getIsRouteAuto() {
		return isRouteAuto;
	}

	public void setIsRouteAuto(String isRouteAuto) {
		this.isRouteAuto = isRouteAuto;
	}

	@Column(name = "WF_UID")
	public String getWfUid() {
		return wfUid;
	}

	public void setWfUid(String wfUid) {
		this.wfUid = wfUid;
	}
	
	@Column(name = "SHOWDEP")
	public String getShowdep() {
		return showdep;
	}

	public void setShowdep(String showdep) {
		this.showdep = showdep;
	}

	/**
	 * 重写equals方法
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Step){
			Step s = (Step)obj;
			if(this.getWfStUid().equals(s.getWfStUid())&&this.getWfUid().equals(s.getWfUid())){
				return true;
			}			
		}
		return false;
	}
}
