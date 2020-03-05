package cn.com.trueway.workflow.core.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_DELAYRESULT")
public class DelayResult {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;			//主键
	
	@Column(name="INSTANCEID")
	private String instanceid;	//实例ID
	
	@Column(name="ITEM_ID")
	private String item_id;		//事项ID
	
	@Column(name="DEALY_INSTANCEID")
	private String delay_instanceid; //延期事项的实例ID
	
	@Column(name="DELAY_ITEM_ID")
	private String delay_item_id; 	//延期的事项ID
	
	@Column(name="NODEID")
	private String nodeid; 	//延期的节点id
	
	@Column(name="NODENAME")
	private String nodename; //延期的节点名称
	
	@Column(name="STEPINDEX")
	private Integer stepindex; //被延期申请的步骤
	
	@Column(name="BEGIN_DATE")
	private Date begin_date ;	//申请的时间
	
	@Column(name="APPROVAL_DATE")
	private Date approval_date; //批准的时间
	
	@Column(name="IS_ALLOWED")
	private Integer is_allowed;		//是否批准
	
	@Column(name="ALLOW_PERSONID")
	private String allow_personid; 	//申请人id
	
	@Column(name="ALLOW_PERSONANME")
	private String allow_personname ; //申请人
	
	@Column(name="STATUS")
	private Integer status; 	//延期时间是否已经加成到办件中

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getDelay_instanceid() {
		return delay_instanceid;
	}

	public void setDelay_instanceid(String delay_instanceid) {
		this.delay_instanceid = delay_instanceid;
	}

	public String getDelay_item_id() {
		return delay_item_id;
	}

	public void setDelay_item_id(String delay_item_id) {
		this.delay_item_id = delay_item_id;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public Date getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	public Date getApproval_date() {
		return approval_date;
	}

	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
	}

	public Integer getIs_allowed() {
		return is_allowed;
	}

	public void setIs_allowed(Integer is_allowed) {
		this.is_allowed = is_allowed;
	}

	public String getAllow_personid() {
		return allow_personid;
	}

	public void setAllow_personid(String allow_personid) {
		this.allow_personid = allow_personid;
	}

	public String getAllow_personname() {
		return allow_personname;
	}

	public void setAllow_personname(String allow_personname) {
		this.allow_personname = allow_personname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStepindex() {
		return stepindex;
	}

	public void setStepindex(Integer stepindex) {
		this.stepindex = stepindex;
	}

}
