package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：办件处理过程的意见信息
 * 作者：蔡亚军
 * 创建时间：2016-9-3 下午1:44:02
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_TRUEJSONLOG")
public class TrueJsonLog {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;			//主键id
	
	@Column(name="PROCESSID")
	private String processId;	//过程id
	
	@Column(name="INSTANCEID")
	private String instanceId;	//实例id
	
	@Column(name="READORWRITEDATE")
	private Date readOrWriteDate;		//读或者写意见的时间
	
	@Column(name="READORWRITE")
	private String readOrWrite;		//读或者写  2:删除
	
	@Column(name="USERID")
	private String userId;		//保存的人员
	
	@Column(name="TRUEJSON")
	private String trueJson;	//保存的意见json信息
	
	@Column(name="EXCUTE")
	private String excute;		//执行的类或者方法
	
	@Column(name="FROMEXCUTE")
	private String fromExcute;	//
	
	@Transient
	private String username;	//操作人姓名
	
	@Transient
	private String  process_title;	//办件标题

	@Transient
	private String nodeName;		//步骤名称

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

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Date getReadOrWriteDate() {
		return readOrWriteDate;
	}

	public void setReadOrWriteDate(Date readOrWriteDate) {
		this.readOrWriteDate = readOrWriteDate;
	}

	public String getReadOrWrite() {
		return readOrWrite;
	}

	public void setReadOrWrite(String readOrWrite) {
		this.readOrWrite = readOrWrite;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTrueJson() {
		return trueJson;
	}

	public void setTrueJson(String trueJson) {
		this.trueJson = trueJson;
	}

	public String getExcute() {
		return excute;
	}

	public void setExcute(String excute) {
		this.excute = excute;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProcess_title() {
		return process_title;
	}

	public void setProcess_title(String process_title) {
		this.process_title = process_title;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getFromExcute() {
		return fromExcute;
	}

	public void setFromExcute(String fromExcute) {
		this.fromExcute = fromExcute;
	}

}
