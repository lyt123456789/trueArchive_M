package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.trueway.workflow.set.pojo.ZwkjForm;

/**
 * 工作流节点表
 * @author Administrator(Feng)
 *
 */
@Entity
@Table(name="WF_NODE_HISTROY")
public class WfHistoryNode {
	
	/**
	 * 节点id
	 */
	@Id
	@Column(name = "wfn_id", length = 36)
	private String wfn_id;
	
	/**
	 * 节点名称
	 */
	@Column(name="wfn_name",length=100)
	private String wfn_name;
	
	/**
	 * 节点创建时间
	 */
	@Column(name="wfn_createtime",length=100)
	private String wfn_createtime;
	
	/**
	 * 节点修改时间
	 */
	@Column(name="wfn_modifytime",length=100)
	private String wfn_modifytime;
	
	/**
	 * 节点期限
	 */
	@Column(name="wfn_deadline",length=100)
	private String wfn_deadline;
	
	/**
	 * 节点期限类型（0:分钟，1：小时，2：天，3：周，4：月，5：年）
	 */
	@Column(name="wfn_deadlineunit",length=2)
	private String wfn_deadlineunit;
	
	/**
	 * 节点期限是否仅限工作日
	 */
	@Column(name="WFN_DEADLINE_ISWORKDAY",length=2)
	private String wfn_deadline_isworkday;
	
	/**
	 * 该节点是否是起始节点 true/false
	 */
	@Column(name="wfn_inittask",length=10)
	private String wfn_inittask;
	
	/**
	 * 默认模板 0,1,2,3
	 */
	@Column(name="wfn_defaulttemplate",length=2)
	private String wfn_defaulttemplate;
	
	/**
	 * 默认日历  0,1,2,3
	 * 
	 */
	@Column(name="wfn_defaultcalendar",length=2)
	private String wfn_defaultcalendar;
	
	/**
	 * 默认表单	0,1,2,3
	 */
	@Column(name="wfn_defaultform",length=2)
	private String wfn_defaultform;
	
	/**
	 * 全程流程自定义状态 0,1,2,3
	 */
	@Column(name="wfn_global_process_custom",length=2)
	private String wfn_global_process_custom;
	
	/**
	 * 当前流程自定义状态
	 */
	@Column(name="wfn_current_process_custom",length=2)
	private String wfn_current_process_custom;
	
	/**
	 * 节点人员
	 */
	@Column(name="wfn_staff",length=100)
	private String wfn_staff;
	
	/**
	 * 是否允许协商
	 */
	@Column(name="wfn_allow_consultation",length=10)
	private String wfn_allow_consultation;
	
	/**
	 * 是否允许委托
	 */
	@Column(name="wfn_allow_delegation",length=10)
	private String wfn_allow_delegation;
	
	/**
	 * 是否允许抄送
	 */
	@Column(name="wfn_allow_cc",length=10)
	private String wfn_allow_cc;
	
	/**
	 * 判断节点类型
	 */
	@Column(name="wfn_type",length=10)
	private String wfn_type;

	/**
	 * 提醒内容
	 */
	@Column(name="WFN_TXNR",length=10)
	private String wfn_txnr;
	
	/**
	 * 提醒内容id
	 */
	@Column(name="TXNR_TXNRIDS",length=10)
	private String wfn_txnrid;
	
	/**
	 * 判断节点位置
	 */
	@Column(name="wfn_moduleid",length=10)
	private String wfn_moduleid;
	
	/**
	 * 绑定的存储过程
	 */
	@Column(name="WFN_PROCEDURE_NAME",length=10)
	private String wfn_procedure_name;
	
	/**
	 * 绑定的固定人员id
	 */
	@Column(name="WFN_BD_USER",nullable = true)
	private String wfn_bd_user;
	
	/**
	 * 节点路由类型
	 */
	@Column(name="WFN_ROUTE_TYPE",length=10)
	private String wfn_route_type;
	
	
	/**
	 *办理状态 0：待办; 1：等办 
	 */
	@Column(name="ACTION_STATUS")
	private Integer action_status;
	
	/**
	 * 是否经过公文交换平台    0:否; 1:是
	 */
	@Column(name="WFN_ISEXCHANGE")
	private Integer isExchange;
	
	/**
	 * 是否合并子流程  0：否，1：是
	 */
	@Column(name="wfl_child_merge",length=2)
	private String wfl_child_merge;
	
	/**
	 * 是否自循环程  0：否，1：是
	 */
	@Column(name="wfn_self_loop",length=2)
	private String wfn_self_loop;
	
	/**
	 * 是否沿用上一节点的表单
	 */
	@Column(name="WFN_FORM_CONTINUE",length=2)
	private String wfn_form_continue;
	
	/**
	 * 是否作废: 0:否; 1:是
	 */
	@Column(name="WFN_ISZF",length=2)
	private String wfn_iszf;
	
	/**
	 * 提前提醒时限
	 */
	@Column(name="WFN_TQTXSJLINE",length=10)
	private String wfn_tqtxsjline;
	
	/**
	 * 是否设置完成时限: 0:否; 1:是
	 */
	@Column(name="WFN_ISWCSX",length=2)
	private String wfn_iswcsx;
	
	/**
	 * 是否联合发文: 0:否; 1:是
	 */
	@Column(name="WFN_ISLHFW",length=2)
	private String wfn_islhfw;

	@Column(name = "NODE_STARTJB")
	private String node_startJb;
	
	/**
	 * 是否盖章，1.是
	 */
	@Column(name = "WFN_ISSEAL")
	private String wfn_isseal;
	
	@ManyToOne(targetEntity=WfMain.class)
	@JoinColumn(name="WFN_PID")
	private WfMain wfMain;
	
	@Column(name = "NODE_ISREPLY")	
	private Integer node_isReply;
	
	/**
	 * 节点是否隐藏
	 */
	@Column(name = "WFN_ISDISPLAY")	
	private Integer wfn_isdisplay;
	
	/**
	 * 表示意见需要排序
	 */
	@Column(name = "WFN_COMMENT_SORT")
	private String wfn_comment_sort;
	
	/**
	 * 排序号
	 */
	@Column(name="WFN_SORTNUMBER",length=3)
	private Integer wfn_sortNumber;
	
	@Transient
	private ZwkjForm zwkjForm;
	
	/**
	 * 是否有权限
	 */
	@Transient
	private boolean ishavePermit;
	
	/**
	 * 是否必须上传附件
	 */
	@Column(name = "WFN_ISUPLOADATTACH")
	private String wfn_isUploadAttach;
	
	/**
	 * 是否设置默认部门
	 */
	@Column(name="WFN_ISDEFDEP",length=2)
	private String wfn_isdefdep;
	
	/**
	 * 发送至人员类型
	 */
	@Column(name = "WFN_EMPTYPE")	
	private String wfn_empType;
	
	/**
	 * 是否复签
	 */
	@Column(name="WFN_ISCOUNTERSIGN",length=2)
	private Integer wfn_iscountersign;
	
	/**
	 * 是否自动发送至第二步
	 */
	@Column(name="WFN_ISOVERFIRSTSTEP",length=2)
	private Integer wfn_isoverfirststep;
	
	/**
	 * 是否设置办结提醒: 0:否; 1:是
	 */
	@Column(name="WFN_ISENDREMIND",length=2)
	private String wfn_isEndRemind;
	
	/**
	 * 是否可以关注 0:否; 1:是
	 */
	@Column(name="WFN_ISFOLLOW",length=2)
	private Integer wfn_isfollow;

	/**
	 * 是否可以补发 0:否; 1:是
	 */
	@Column(name="WFN_ISREISSUE",length=2)
	private Integer wfn_isreissue;
	
	/**
	 * 是否可以复制表单 0:否; 1:是
	 */
	@Column(name="WFN_FORMCOPY",length=2)
	private Integer wfn_formcopy;
	
	/**
	 * 是否支持强制拿回  0:否; 1:是
	 */
	@Column(name="WFN_FORCEBACK",length=2)
	private Integer wfn_forceback;
	
	@Column(name = "WFN_ISDOINMOBILE")
	private Integer wfn_isDoInMobile;		//是否可以在移动端办理
	
	@Column(name = "WFN_ISAUTOCLOSEWIN")
	private Integer wfn_isautoclosewin;		//是否10分钟自动关闭待办窗口
	
	/**
	 * 是否分批
	 */
	@Column(name="WFN_SEND_AGAIN")
	private Integer wfn_isSendAgain;
	
	/**
	 * 是否退回
	 */
	@Column(name="WFN_SEND_BACK")
	private Integer wfn_isSendBack;
	
	/**
	 * 是否一键办理
	 */
	@Column(name="WFN_ONEKEYHANDLE")
	private Integer wfn_oneKeyHandle;
	
	/**
	 * 是否过虑非本部门人员
	 */
	@Column(name="WFN_SKIPUSER")
	private Integer wfn_skipUser;
	
	/**
	 * 是否自动办理
	 */
	@Column(name="WFN_AUTOSEND")
	private Integer wfn_autoSend;
	
	/**
	 * 多少天后自动办理
	 */
	@Column(name="WFN_AUTOSENDDAYS")
	private Integer wfn_autoSendDays;
	
	/**
	 * 是否展示痕迹按钮
	 */
	@Column(name="WFN_SHOWMARKBTN")
	private Integer wfn_showMarkbtn;
	
	/**
	 * 是否过滤下一步节点
	 */
	@Column(name="WFN_SKIPNEXTNODES")
	private Integer wfn_skipNextnodes;
	
	/**
	 * 上一节点人员所在的用户组
	 */
	@Column(name="WFN_LASTSTAFF")
	private String wfn_lastStaff;
	
	/**
	 * 下一节点是否可以自动办理（用于控制当前节点是否能够查入自动办理表的数据）
	 */
	@Column(name="WFN_ISAUTOSEND")
	private Integer wfn_isAutoSend;
	
	/**
	 * 是否为督办节点
	 */
	@Column(name="WFN_ISSUPERVISION")
	private Integer wfn_isSupervision;
	
	@Column(name="WFN_ISUSENEWINPUT")
	private Integer wfn_isUseNewInput;
	
	/**
	 * 一键签批无落款
	 */
	@Column(name="WFN_AUTONONAME")
	private Integer wfn_autoNoname;
	
	public String getWfn_id() {
		return wfn_id;
	}

	public void setWfn_id(String wfn_id) {
		this.wfn_id = wfn_id;
	}

	public String getWfn_name() {
		return wfn_name;
	}

	public void setWfn_name(String wfn_name) {
		this.wfn_name = wfn_name;
	}

	public String getWfn_createtime() {
		return wfn_createtime;
	}

	public void setWfn_createtime(String wfn_createtime) {
		this.wfn_createtime = wfn_createtime;
	}

	public String getWfn_modifytime() {
		return wfn_modifytime;
	}

	public void setWfn_modifytime(String wfn_modifytime) {
		this.wfn_modifytime = wfn_modifytime;
	}

	public String getWfn_deadline() {
		return wfn_deadline;
	}

	public void setWfn_deadline(String wfn_deadline) {
		this.wfn_deadline = wfn_deadline;
	}

	public String getWfn_deadlineunit() {
		return wfn_deadlineunit;
	}

	public void setWfn_deadlineunit(String wfn_deadlineunit) {
		this.wfn_deadlineunit = wfn_deadlineunit;
	}

	public String getWfn_deadline_isworkday() {
		return wfn_deadline_isworkday;
	}

	public void setWfn_deadline_isworkday(String wfn_deadline_isworkday) {
		this.wfn_deadline_isworkday = wfn_deadline_isworkday;
	}

	public String getWfn_inittask() {
		return wfn_inittask;
	}

	public void setWfn_inittask(String wfn_inittask) {
		this.wfn_inittask = wfn_inittask;
	}

	public String getWfn_defaulttemplate() {
		return wfn_defaulttemplate;
	}

	public void setWfn_defaulttemplate(String wfn_defaulttemplate) {
		this.wfn_defaulttemplate = wfn_defaulttemplate;
	}

	public String getWfn_defaultcalendar() {
		return wfn_defaultcalendar;
	}

	public void setWfn_defaultcalendar(String wfn_defaultcalendar) {
		this.wfn_defaultcalendar = wfn_defaultcalendar;
	}

	public String getWfn_defaultform() {
		return wfn_defaultform;
	}

	public void setWfn_defaultform(String wfn_defaultform) {
		this.wfn_defaultform = wfn_defaultform;
	}

	public String getWfn_global_process_custom() {
		return wfn_global_process_custom;
	}

	public void setWfn_global_process_custom(String wfn_global_process_custom) {
		this.wfn_global_process_custom = wfn_global_process_custom;
	}

	public String getWfn_current_process_custom() {
		return wfn_current_process_custom;
	}

	public void setWfn_current_process_custom(String wfn_current_process_custom) {
		this.wfn_current_process_custom = wfn_current_process_custom;
	}

	public String getWfn_staff() {
		return wfn_staff;
	}

	public void setWfn_staff(String wfn_staff) {
		this.wfn_staff = wfn_staff;
	}

	public String getWfn_allow_consultation() {
		return wfn_allow_consultation;
	}

	public void setWfn_allow_consultation(String wfn_allow_consultation) {
		this.wfn_allow_consultation = wfn_allow_consultation;
	}

	public String getWfn_allow_delegation() {
		return wfn_allow_delegation;
	}

	public void setWfn_allow_delegation(String wfn_allow_delegation) {
		this.wfn_allow_delegation = wfn_allow_delegation;
	}

	public String getWfn_allow_cc() {
		return wfn_allow_cc;
	}

	public void setWfn_allow_cc(String wfn_allow_cc) {
		this.wfn_allow_cc = wfn_allow_cc;
	}

	public String getWfn_type() {
		return wfn_type;
	}

	public void setWfn_type(String wfn_type) {
		this.wfn_type = wfn_type;
	}

	public String getWfn_txnr() {
		return wfn_txnr;
	}

	public void setWfn_txnr(String wfn_txnr) {
		this.wfn_txnr = wfn_txnr;
	}

	public String getWfn_txnrid() {
		return wfn_txnrid;
	}

	public void setWfn_txnrid(String wfn_txnrid) {
		this.wfn_txnrid = wfn_txnrid;
	}

	public String getWfn_moduleid() {
		return wfn_moduleid;
	}

	public void setWfn_moduleid(String wfn_moduleid) {
		this.wfn_moduleid = wfn_moduleid;
	}

	public String getWfn_procedure_name() {
		return wfn_procedure_name;
	}

	public void setWfn_procedure_name(String wfn_procedure_name) {
		this.wfn_procedure_name = wfn_procedure_name;
	}

	public String getWfn_bd_user() {
		return wfn_bd_user;
	}

	public void setWfn_bd_user(String wfn_bd_user) {
		this.wfn_bd_user = wfn_bd_user;
	}

	public String getWfn_route_type() {
		return wfn_route_type;
	}

	public void setWfn_route_type(String wfn_route_type) {
		this.wfn_route_type = wfn_route_type;
	}

	public Integer getAction_status() {
		return action_status;
	}

	public void setAction_status(Integer action_status) {
		this.action_status = action_status;
	}

	public Integer getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(Integer isExchange) {
		this.isExchange = isExchange;
	}

	public String getWfl_child_merge() {
		return wfl_child_merge;
	}

	public void setWfl_child_merge(String wfl_child_merge) {
		this.wfl_child_merge = wfl_child_merge;
	}

	public String getWfn_form_continue() {
		return wfn_form_continue;
	}

	public void setWfn_form_continue(String wfn_form_continue) {
		this.wfn_form_continue = wfn_form_continue;
	}

	public String getWfn_iszf() {
		return wfn_iszf;
	}

	public void setWfn_iszf(String wfn_iszf) {
		this.wfn_iszf = wfn_iszf;
	}

	public String getWfn_tqtxsjline() {
		return wfn_tqtxsjline;
	}

	public void setWfn_tqtxsjline(String wfn_tqtxsjline) {
		this.wfn_tqtxsjline = wfn_tqtxsjline;
	}

	public String getWfn_iswcsx() {
		return wfn_iswcsx;
	}

	public void setWfn_iswcsx(String wfn_iswcsx) {
		this.wfn_iswcsx = wfn_iswcsx;
	}

	public String getWfn_islhfw() {
		return wfn_islhfw;
	}

	public void setWfn_islhfw(String wfn_islhfw) {
		this.wfn_islhfw = wfn_islhfw;
	}

	public String getNode_startJb() {
		return node_startJb;
	}

	public void setNode_startJb(String node_startJb) {
		this.node_startJb = node_startJb;
	}

	public String getWfn_isseal() {
		return wfn_isseal;
	}

	public void setWfn_isseal(String wfn_isseal) {
		this.wfn_isseal = wfn_isseal;
	}

	public WfMain getWfMain() {
		return wfMain;
	}

	public void setWfMain(WfMain wfMain) {
		this.wfMain = wfMain;
	}

	public Integer getNode_isReply() {
		return node_isReply;
	}

	public void setNode_isReply(Integer node_isReply) {
		this.node_isReply = node_isReply;
	}

	public Integer getWfn_isdisplay() {
		return wfn_isdisplay;
	}

	public void setWfn_isdisplay(Integer wfn_isdisplay) {
		this.wfn_isdisplay = wfn_isdisplay;
	}

	public ZwkjForm getZwkjForm() {
		return zwkjForm;
	}

	public void setZwkjForm(ZwkjForm zwkjForm) {
		this.zwkjForm = zwkjForm;
	}

	public boolean isIshavePermit() {
		return ishavePermit;
	}

	public void setIshavePermit(boolean ishavePermit) {
		this.ishavePermit = ishavePermit;
	}

	public String getWfn_isUploadAttach() {
		return wfn_isUploadAttach;
	}

	public void setWfn_isUploadAttach(String wfn_isUploadAttach) {
		this.wfn_isUploadAttach = wfn_isUploadAttach;
	}

	public String getWfn_isdefdep() {
		return wfn_isdefdep;
	}

	public void setWfn_isdefdep(String wfn_isdefdep) {
		this.wfn_isdefdep = wfn_isdefdep;
	}

	public String getWfn_empType() {
		return wfn_empType;
	}

	public void setWfn_empType(String wfn_empType) {
		this.wfn_empType = wfn_empType;
	}

	public Integer getWfn_iscountersign() {
		return wfn_iscountersign;
	}

	public void setWfn_iscountersign(Integer wfn_iscountersign) {
		this.wfn_iscountersign = wfn_iscountersign;
	}

	public Integer getWfn_isoverfirststep() {
		return wfn_isoverfirststep;
	}

	public void setWfn_isoverfirststep(Integer wfn_isoverfirststep) {
		this.wfn_isoverfirststep = wfn_isoverfirststep;
	}

	public String getWfn_isEndRemind() {
		return wfn_isEndRemind;
	}

	public void setWfn_isEndRemind(String wfn_isEndRemind) {
		this.wfn_isEndRemind = wfn_isEndRemind;
	}

	public Integer getWfn_isfollow() {
		return wfn_isfollow;
	}

	public void setWfn_isfollow(Integer wfn_isfollow) {
		this.wfn_isfollow = wfn_isfollow;
	}

	public Integer getWfn_isreissue() {
		return wfn_isreissue;
	}

	public void setWfn_isreissue(Integer wfn_isreissue) {
		this.wfn_isreissue = wfn_isreissue;
	}

	public Integer getWfn_formcopy() {
		return wfn_formcopy;
	}

	public void setWfn_formcopy(Integer wfn_formcopy) {
		this.wfn_formcopy = wfn_formcopy;
	}

	public String getWfn_self_loop() {
		return wfn_self_loop;
	}

	public void setWfn_self_loop(String wfn_self_loop) {
		this.wfn_self_loop = wfn_self_loop;
	}

	public Integer getWfn_forceback() {
		return wfn_forceback;
	}

	public void setWfn_forceback(Integer wfn_forceback) {
		this.wfn_forceback = wfn_forceback;
	}

	public Integer getWfn_isDoInMobile() {
		return wfn_isDoInMobile;
	}

	public void setWfn_isDoInMobile(Integer wfn_isDoInMobile) {
		this.wfn_isDoInMobile = wfn_isDoInMobile;
	}

	public Integer getWfn_isautoclosewin() {
		return wfn_isautoclosewin;
	}

	public void setWfn_isautoclosewin(Integer wfn_isautoclosewin) {
		this.wfn_isautoclosewin = wfn_isautoclosewin;
	}

	public Integer getWfn_isSendAgain() {
		return wfn_isSendAgain;
	}

	public void setWfn_isSendAgain(Integer wfn_isSendAgain) {
		this.wfn_isSendAgain = wfn_isSendAgain;
	}

	public Integer getWfn_isSendBack() {
		return wfn_isSendBack;
	}

	public void setWfn_isSendBack(Integer wfn_isSendBack) {
		this.wfn_isSendBack = wfn_isSendBack;
	}

	public Integer getWfn_oneKeyHandle() {
		return wfn_oneKeyHandle;
	}

	public void setWfn_oneKeyHandle(Integer wfn_oneKeyHandle) {
		this.wfn_oneKeyHandle = wfn_oneKeyHandle;
	}

	public Integer getWfn_skipUser() {
		return wfn_skipUser;
	}

	public void setWfn_skipUser(Integer wfn_skipUser) {
		this.wfn_skipUser = wfn_skipUser;
	}

	public Integer getWfn_autoSend() {
		return wfn_autoSend;
	}

	public void setWfn_autoSend(Integer wfn_autoSend) {
		this.wfn_autoSend = wfn_autoSend;
	}

	public Integer getWfn_autoSendDays() {
		return wfn_autoSendDays;
	}

	public void setWfn_autoSendDays(Integer wfn_autoSendDays) {
		this.wfn_autoSendDays = wfn_autoSendDays;
	}

	public Integer getWfn_showMarkbtn() {
		return wfn_showMarkbtn;
	}

	public void setWfn_showMarkbtn(Integer wfn_showMarkbtn) {
		this.wfn_showMarkbtn = wfn_showMarkbtn;
	}

	public Integer getWfn_skipNextnodes() {
		return wfn_skipNextnodes;
	}

	public void setWfn_skipNextnodes(Integer wfn_skipNextnodes) {
		this.wfn_skipNextnodes = wfn_skipNextnodes;
	}

	public String getWfn_lastStaff() {
		return wfn_lastStaff;
	}

	public void setWfn_lastStaff(String wfn_lastStaff) {
		this.wfn_lastStaff = wfn_lastStaff;
	}

	public String getWfn_comment_sort() {
		return wfn_comment_sort;
	}

	public void setWfn_comment_sort(String wfn_comment_sort) {
		this.wfn_comment_sort = wfn_comment_sort;
	}

	public Integer getWfn_sortNumber() {
		return wfn_sortNumber;
	}

	public void setWfn_sortNumber(Integer wfn_sortNumber) {
		this.wfn_sortNumber = wfn_sortNumber;
	}

	public Integer getWfn_isAutoSend() {
		return wfn_isAutoSend;
	}

	public void setWfn_isAutoSend(Integer wfn_isAutoSend) {
		this.wfn_isAutoSend = wfn_isAutoSend;
	}

	public Integer getWfn_isSupervision() {
		return wfn_isSupervision;
	}

	public void setWfn_isSupervision(Integer wfn_isSupervision) {
		this.wfn_isSupervision = wfn_isSupervision;
	}

	public Integer getWfn_isUseNewInput() {
		return wfn_isUseNewInput;
	}

	public void setWfn_isUseNewInput(Integer wfn_isUseNewInput) {
		this.wfn_isUseNewInput = wfn_isUseNewInput;
	}

	public Integer getWfn_autoNoname() {
		return wfn_autoNoname;
	}

	public void setWfn_autoNoname(Integer wfn_autoNoname) {
		this.wfn_autoNoname = wfn_autoNoname;
	}
}
