package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_REPLAY")
public class Replay implements java.io.Serializable{
	
	private static final long serialVersionUID = -5459663413506832246L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;				//主键id
	
	@Column(name = "PROCESSID")
	private String processId;			//过程id
	
	@Column(name = "TITLE")
	private String title;				//标题
	
	@Column(name = "REPLAY_TIME")
	private Date replayTime;			//回复日期
	
	@Column(name = "REPLAY_USERID")
	private String replayUserid;		//回复人员用户id
	
	@Column(name = "REPLAY_PROCESSID")
	private String replayProcessid;			//回复的过程id
	
	@Column(name = "STATUS")
	private String status;				//状态位
	
	@Transient
	private String replayUserName;
	
	@Transient
	private String  instanceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(Date replayTime) {
		this.replayTime = replayTime;
	}

	public String getReplayUserid() {
		return replayUserid;
	}

	public void setReplayUserid(String replayUserid) {
		this.replayUserid = replayUserid;
	}

	public String getReplayProcessid() {
		return replayProcessid;
	}

	public void setReplayProcessid(String replayProcessid) {
		this.replayProcessid = replayProcessid;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReplayUserName() {
		return replayUserName;
	}

	public void setReplayUserName(String replayUserName) {
		this.replayUserName = replayUserName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Replay other = (Replay) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
