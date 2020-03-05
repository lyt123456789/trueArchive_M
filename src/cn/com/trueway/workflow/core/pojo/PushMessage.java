package cn.com.trueway.workflow.core.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;

@Entity
@Table(name = "T_WF_CORE_PUSHMESSAGE")
public class PushMessage  implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 5669557458455742821L;
	
	private String id; 
	
	private String instanceId;
	
	private String pushEmpId;//推送的人id
	
	private String pushEmpName;//推送的人名字
	
	private String pushedEmpId;//被推送的人id
	
	private String message;//推送的消息
	
	private Date pushTime;//推送时间
	private String fjid;//附件id

	private String zt;//状态
	
	private String  finstanceid;//被推送实例id

	@Column(name = "FINSTANCEID")
	public String getFinstanceid() {
	
		return finstanceid;
	}
	
	public void setFinstanceid(String finstanceid) {
	
		this.finstanceid = finstanceid;
	}

	@Column(name = "ZT")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@Column(name = "FJID")
	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}
	private List<SendAttachments> attList;//推送的附件
	

	private String pushKs;//科室
	
	public PushMessage() {
		super();
	}

	@Column(name = "PUSH_KS")
	public String getPushKs() {
		return pushKs;
	}

	public void setPushKs(String pushKs) {
		this.pushKs = pushKs;
	}



	public PushMessage(String id, String instanceId, String pushEmpId,
			String pushEmpName, String pushedEmpId, String message,
			Date pushTime) {
		super();
		this.id = id;
		this.instanceId = instanceId;
		this.pushEmpId = pushEmpId;
		this.pushEmpName = pushEmpName;
		this.pushedEmpId = pushedEmpId;
		this.message = message;
		this.pushTime = pushTime;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "INSTANCEID")
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "PUSH_EMPNAME")
	public String getPushEmpName() {
		return pushEmpName;
	}
	public void setPushEmpName(String pushEmpName) {
		this.pushEmpName = pushEmpName;
	}

	@Column(name = "PUSH_EMPID")
	public String getPushEmpId() {
		return pushEmpId;
	}
	public void setPushEmpId(String pushEmpId) {
		this.pushEmpId = pushEmpId;
	}

	@Column(name = "PUSHED_EMPID")
	public String getPushedEmpId() {
		return pushedEmpId;
	}
	public void setPushedEmpId(String pushedEmpId) {
		this.pushedEmpId = pushedEmpId;
	}

	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "PUSH_TIME")
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	@Transient
	public List<SendAttachments> getAttList() {
		return attList;
	}
	public void setAttList(List<SendAttachments> attList) {
		this.attList = attList;
	}
	
	
}
