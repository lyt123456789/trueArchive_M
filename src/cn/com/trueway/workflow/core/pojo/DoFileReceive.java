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
/**
 * 办件待收列表
 * @author caiyj
 *
 */
@Entity
@Table(name = "T_WF_CORE_RECEIVE")
public class DoFileReceive implements java.io.Serializable{
	
	private static final long serialVersionUID = 5945671932840971343L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;	//主键
	
	@Column(name = "INSTANCEID")
	private String instanceId;	//实例ID
	
	@Column(name = "PINSTANCEID")
	private String pInstanceId;  //父实例ID
	
	@Column(name = "PROCESSID")
	private String processId;	//过程ID
	
	@Column(name = "FROMUSERID")
	private String formUserId;	//发送人
	
	@Column(name = "TOUSERID")
	private String toUserId;	//接收人
	
	@Column(name = "APPLYDATE")	
	private Date applyDate;		//申请时间
	
	@Column(name = "TYPE")
	private Integer type;		//状态 主送1； 抄送0；
	
	@Column(name = "STATUS")
	private Integer status;		//状态码：0-未接收，1-已接收，2-已阅读，3-已处理 or 已退文， 4-拒收
	
	@Column(name = "RECEIVE_TYPE")
	private String receiveType; //接收类型：0或者空-公文交换  1-流程中发送按钮
	
	@Column(name = "TODEPARTID")
	private String toDepartId;	//接收的机构id
	
	@Column(name = "FPROCESSID")
	private String fProcessId;	//处理分发步骤的步骤id
	
	@Column(name = "RECDATE")
	private Date recDate; 	//接收时间
	
	@Column(name = "PDFPATH")
	private String pdfpath;	//pdf地址
	
	@Column(name = "TRUEJSON")
	private String trueJson;	//true意见
	
	@Column(name = "DYFS")
	private Integer dyfs;		//打印份数

	@Column(name = "YDYFS")
	private Integer ydyfs;
	
	@Column(name = "ISINVALID")
	private Integer isInvalid;	//是否已被作废中‘
	
	@Column(name = "JRDB")
	private Integer jrdb;
	
	@Column(name = "ISREBACK")
	private Integer isReback; // 是否回复办件
	
	@Column(name = "TAKEBACK")
	private Integer takeback; // 是否收回
	
	@Column(name = "TOEXCHANGEPATH")
	private String toExchangePath; //公文交换合成的pdf路径
	
	@Column(name = "UPDATETYPE")
	private Integer updateType; //状态位记录1：发文接口更新；2：收文接口更新
	
	@Transient
	private List<WfItem> itemList;
	
	@Transient
	private String date;
	
	@Column(name = "CBCS")
	private Integer cbcs;
	
	public Integer getIsReback() {
		return isReback;
	}


	public void setIsReback(Integer isReback) {
		this.isReback = isReback;
	}


	/*@Column(name = "PDFFLOW")
	private String pdfFlow;*/
	@Transient
	private String  employeeName ;
	
	
	@Column(name = "REPLYDATE")	
	private Date replyDate;		//申请时间
	/*public String getPdfFlow() {
		return pdfFlow;
	}


	public void setPdfFlow(String pdfFlow) {
		this.pdfFlow = pdfFlow;
	}
*/

	public Date getReplyDate() {
		return replyDate;
	}


	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}


	@Transient
	private String  title;
	
	@Transient
	private String itemName;
	
	@Transient
	private String formId;		//表单id
	
	@Transient
	private String itemId;		//事项id
	
	@Transient
	private String lwbt;
	
	@Transient
	private String lwdw;
	
	@Transient
	private String yfdw;
	
	@Transient
	private String lwh;

	@Transient
	private Date fwsj;

	@Transient
	private String gwlx;
	
	
	public Integer getYdyfs() {
	
		return ydyfs;
	}

	
	public void setYdyfs(Integer ydyfs) {
	
		this.ydyfs = ydyfs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getpInstanceId() {
		return pInstanceId;
	}

	public void setpInstanceId(String pInstanceId) {
		this.pInstanceId = pInstanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getFormUserId() {
		return formUserId;
	}

	public void setFormUserId(String formUserId) {
		this.formUserId = formUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	
	public String getToDepartId() {
		return toDepartId;
	}

	public void setToDepartId(String toDepartId) {
		this.toDepartId = toDepartId;
	}

	public String getLwbt() {
		return lwbt;
	}

	public void setLwbt(String lwbt) {
		this.lwbt = lwbt;
	}

	public String getLwdw() {
		return lwdw;
	}

	public void setLwdw(String lwdw) {
		this.lwdw = lwdw;
	}

	public String getYfdw() {
		return yfdw;
	}

	public void setYfdw(String yfdw) {
		this.yfdw = yfdw;
	}

	public String getLwh() {
		return lwh;
	}

	public void setLwh(String lwh) {
		this.lwh = lwh;
	}

	public Date getFwsj() {
		return fwsj;
	}

	public void setFwsj(Date fwsj) {
		this.fwsj = fwsj;
	}

	public String getGwlx() {
		return gwlx;
	}

	public void setGwlx(String gwlx) {
		this.gwlx = gwlx;
	}

	public String getfProcessId() {
		return fProcessId;
	}

	public void setfProcessId(String fProcessId) {
		this.fProcessId = fProcessId;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

	public String getTrueJson() {
		return trueJson;
	}

	public void setTrueJson(String trueJson) {
		this.trueJson = trueJson;
	}

	public Integer getDyfs() {
		return dyfs;
	}

	public void setDyfs(Integer dyfs) {
		this.dyfs = dyfs;
	}

	public Integer getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(Integer isInvalid) {
		this.isInvalid = isInvalid;
	}


	public Integer getJrdb() {
		return jrdb;
	}


	public void setJrdb(Integer jrdb) {
		this.jrdb = jrdb;
	}

	public Integer getTakeback() {
		return takeback;
	}

	public void setTakeback(Integer takeback) {
		this.takeback = takeback;
	}


	public String getToExchangePath() {
		return toExchangePath;
	}


	public void setToExchangePath(String toExchangePath) {
		this.toExchangePath = toExchangePath;
	}


	public Integer getUpdateType() {
		return updateType;
	}


	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}


	public List<WfItem> getItemList() {
	    return itemList;
	}


	public void setItemList(List<WfItem> itemList) {
	    this.itemList = itemList;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public Integer getCbcs() {
		return cbcs;
	}


	public void setCbcs(Integer cbcs) {
		this.cbcs = cbcs;
	}
}
