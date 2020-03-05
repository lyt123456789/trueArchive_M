package cn.com.trueway.workflow.core.pojo;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "T_WF_CORE_DOFILE")
public class DoFile implements java.io.Serializable,Comparable<DoFile> {

	// Fields

	private static final long serialVersionUID = -3601053263282892187L;
	/**
	 * 唯一标识
	 */
	private String doFile_id;
	/**
	 * 办件标题
	 */
	private String doFile_title;
	/**
	 * 事项Id
	 */
	private String itemId;
	
	/**
	 * 事项名称
	 */
	private String itemName;
	/**
	 * 办理结果
	 */
	private Integer doFile_result;
	/**
	 * 流程id
	 */
	private String workflowId;
	/**
	 * 实例id
	 */
	private String instanceId;
	/**
	 * 表单id
	 */
	private String formId;
	/**
	/**
	 * 节点id
	 */
	private String nodeId;
	/**
	 * 办理时间
	 */
	private Date do_time;
	
	private String processId;

	private String nodeName;
	
	private Integer isDelete;
	
	private String favourite;
	
	private String isChildWf;//是否子流程
	
	private String applyTime;

	private String link;
	
	private String isHaveChild;
	
	/**
	 * 办件的紧急程度 (高:1;中:2;低:3;)
	 */
	private Integer urgency;
	
	private Integer copyNumber; //表单复制标识为
	
	private String 	entrustName; //办件持有人
	
	private String 	itemType;
	
	private String 	isCanRead;
	
	private String 	pdfPath;	//办结时，生成的办件的pdf的路径
	
	private Blob	pdfData;	//pdf文件流
	
	private String  wh;//文号
	
	private String  delUserId;//删除人id
	
	private String  delUserName;//删除人name
	
	private String qrcodePath; //二维码地址
	
	private String dubanType; //督办类型	
	
	private String dubanTime; //督办时限
	
	public DoFile() {
	}

	public DoFile(String doFile_id, String doFile_title, String itemId,
			String itemName, Integer doFile_result, String workflowId,
			String instanceId, String formId, String nodeId, Date do_time) {
		super();
		this.doFile_id = doFile_id;
		this.doFile_title = doFile_title;
		this.itemId = itemId;
		this.itemName = itemName;
		this.doFile_result = doFile_result;
		this.workflowId = workflowId;
		this.instanceId = instanceId;
		this.formId = formId;
		this.nodeId = nodeId;
		this.do_time = do_time;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getDoFile_id() {
		return doFile_id;
	}
	public void setDoFile_id(String doFile_id) {
		this.doFile_id = doFile_id;
	}
	
	@Column(name = "DOFILE_TITLE",length = 36)
	public String getDoFile_title() {
		return doFile_title;
	}
	public void setDoFile_title(String doFile_title) {
		this.doFile_title = doFile_title;
	}

	@Column(name = "ISDELETE",length = 36)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name = "DELUSERID",length = 36)
	public String getDelUserId() {
		return delUserId;
	}

	public void setDelUserId(String delUserId) {
		this.delUserId = delUserId;
	}
	@Column(name = "DELUSERNAME",length = 36)
	public String getDelUserName() {
		return delUserName;
	}

	public void setDelUserName(String delUserName) {
		this.delUserName = delUserName;
	}

	@Column(name = "ITEM_ID", nullable = false, length = 38)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Column(name = "DOFILE_RESULT", length = 36)
	public Integer getDoFile_result() {
		return doFile_result;
	}
	public void setDoFile_result(Integer doFile_result) {
		this.doFile_result = doFile_result;
	}
	
	@Column(name = "WORKFLOWID", length = 36)
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	@Column(name = "INSTANCEID",  length = 36)
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "FORMID",  length = 36)
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name = "NODEID",  length = 36)
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "DOTIME")
	public Date getDo_time() {
		return do_time;
	}
	public void setDo_time(Date do_time) {
		this.do_time = do_time;
	}

	@Transient
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	@Transient
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Transient
	public String getFavourite() {
		return favourite;
	}

	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}
	
	@Transient
	public String getIsChildWf() {
		return isChildWf;
	}

	public void setIsChildWf(String isChildWf) {
		this.isChildWf = isChildWf;
	}

	@Transient
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	@Transient
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Transient
	public String getIsHaveChild() {
		return isHaveChild;
	}

	public void setIsHaveChild(String isHaveChild) {
		this.isHaveChild = isHaveChild;
	}

	@Column(name = "URGENCY",  length = 2)
	public Integer getUrgency() {
		return urgency;
	}

	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}

	@Column(name = "COPY_NUMBER")
	public Integer getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(Integer copyNumber) {
		this.copyNumber = copyNumber;
	}

	@Transient
	public String getEntrustName() {
		return entrustName;
	}

	public void setEntrustName(String entrustName) {
		this.entrustName = entrustName;
	}

	@Transient
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Transient
	public String getIsCanRead() {
		return isCanRead;
	}

	public void setIsCanRead(String isCanRead) {
		this.isCanRead = isCanRead;
	}

	@Column(name = "PDFPATH")
	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	@Column(name = "PDFDATA")
	public Blob getPdfData() {
		return pdfData;
	}

	public void setPdfData(Blob pdfData) {
		this.pdfData = pdfData;
	}
	
	@Transient
	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	@Column(name ="QRCODEPATH")
	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	@Transient
	public String getDubanType() {
		return dubanType;
	}

	public void setDubanType(String dubanType) {
		this.dubanType = dubanType;
	}

	@Transient
	public String getDubanTime() {
		return dubanTime;
	}

	public void setDubanTime(String dubanTime) {
		this.dubanTime = dubanTime;
	}

	@Override
	public int compareTo(DoFile doFile) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sdf.parse(this.getApplyTime()).getTime()-sdf.parse(doFile.getApplyTime()).getTime()<0?1:-1;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}