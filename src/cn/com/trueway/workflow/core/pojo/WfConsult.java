package cn.com.trueway.workflow.core.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


/**
 * 协商信息表
 * @author Administrator(Feng)
 *
 */
@Entity
@Table(name="WF_CONSULT")
public class WfConsult {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false,length = 36)
	private String id;
	/**
	 * 发送人id
	 */
	@Column(name="FROM_USERID",length=38)
	private String fromUserId;
	/**
	 * 接收人id
	 */
	@Column(name="TO_USERID",length=38)
	private String toUserId;
	/**
	 * 是否已读
	 */
	@Column(name="IS_READ",length=10)
	private String isRead;
	/**
	 * 是否已回复
	 */
	@Column(name="IS_REPLIED",length=10)
	private String isReplied;
	/**
	 * 是否显示
	 */
	@Column(name="IS_SHOW",length=2)
	private String isShow;
	
	/**
	 * 消息内容
	 */
	@Column(name="MESSAGE",length=2000)
	private String message;
	
	/**
	 * 发送时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SENDTIME")
	private Date sendTime;
	/**
	 * 关联id
	 */
	@Column(name="RELATEID",length=36)
	private String relateId;
	
	/**
	 * 发送人名称
	 */
	@Column(name="FROM_USERNAME",length=100)
	private String fromUserName;
	
	/**
	 * 接收人名称
	 */
	@Column(name="TO_USERNAME",length=100)
	private String toUserName;
//	
//	@OneToMany(targetEntity=Employee.class,cascade=CascadeType.ALL)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name="FROM_USERID")
//	private Set<Employee> fromUser_sets=new HashSet<Employee>();
//	
//	@OneToMany(targetEntity=Employee.class,cascade=CascadeType.ALL)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name="TO_USERID")
//	private Set<Employee> toUser_sets=new HashSet<Employee>();

	public Date getSendTime() {
		return sendTime;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsReplied() {
		return isReplied;
	}

	public void setIsReplied(String isReplied) {
		this.isReplied = isReplied;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

//	public Set<Employee> getFromUser_sets() {
//		return fromUser_sets;
//	}
//
//	public void setFromUser_sets(Set<Employee> fromUser_sets) {
//		this.fromUser_sets = fromUser_sets;
//	}
//
//	public Set<Employee> getToUser_sets() {
//		return toUser_sets;
//	}
//
//	public void setToUser_sets(Set<Employee> toUser_sets) {
//		this.toUser_sets = toUser_sets;
//	}
	
	
	
}
