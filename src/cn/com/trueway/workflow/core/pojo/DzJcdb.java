package cn.com.trueway.workflow.core.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DZJCDB@riseapprove")
public class DzJcdb {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "NO", unique = true, nullable = false, length = 36)
	private String no; 			//id
	
	@Column(name = "DBSX")
	private String dbsx;		//督办事项
	
	@Column(name = "DBDX")
	private String dbdx;       //督办对象
	
	@Column(name = "DBNR")
	private String dbnr;		//督办内容
	
	@Column(name = "DBR")
	private String dbr;			//督办人
	
	@Column(name = "DBSJ")
	private Date dbsj; 			//督办时间
	
	@Column(name = "BZ")
	private String bz; 			//备注
	
	@Column(name = "LY")
	private String ly; 			//来源(行政权力、公文管理、任务管理、重点工作、土地出让、矿业权出让、其他)
	
	@Column(name = "STATE")
	private String state;		//状态(0未督办、1已督办未反馈、2已督办已反馈、3延期督办)
	
	@Column(name = "YQDBSJ")
	private Date yqdbsj; 		//延期sj
	
	@Column(name = "HF")
	private String hf;			//回复意见
	
	@Column(name = "HFSJ")
	private Date hfsj; 			//回复时间
	
	@Column(name = "DBJZSJ")
	private Date dbjzsj;		//截止时间
	
	@Column(name = "WEBID")
	private String webId;		//webid
	
	@Column(name = "SXID")
	private String sxId;		//事项ID
	
	@Column(name = "INSTANCEID")
	private String instanceId;	
	
	@Column(name = "FORMID")
	private String formId;
	
	@Column(name = "WORKFLOWID")
	private String workflowId;
	
	@Column(name = "PROCESSID")
	private String processId;
	
	@Column(name = "PDFPATH")
	private String pdfPath;
	
	@Column(name = "YQBZ")
	private String yqbz;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDbsx() {
		return dbsx;
	}

	public void setDbsx(String dbsx) {
		this.dbsx = dbsx;
	}

	public String getDbdx() {
		return dbdx;
	}

	public void setDbdx(String dbdx) {
		this.dbdx = dbdx;
	}

	public String getDbnr() {
		return dbnr;
	}

	public void setDbnr(String dbnr) {
		this.dbnr = dbnr;
	}

	public String getDbr() {
		return dbr;
	}

	public void setDbr(String dbr) {
		this.dbr = dbr;
	}

	public Date getDbsj() {
		return dbsj;
	}

	public void setDbsj(Date dbsj) {
		this.dbsj = dbsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getYqdbsj() {
		return yqdbsj;
	}

	public void setYqdbsj(Date yqdbsj) {
		this.yqdbsj = yqdbsj;
	}

	public String getHf() {
		return hf;
	}

	public void setHf(String hf) {
		this.hf = hf;
	}

	public Date getHfsj() {
		return hfsj;
	}

	public void setHfsj(Date hfsj) {
		this.hfsj = hfsj;
	}

	public Date getDbjzsj() {
		return dbjzsj;
	}

	public void setDbjzsj(Date dbjzsj) {
		this.dbjzsj = dbjzsj;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public String getSxId() {
		return sxId;
	}

	public void setSxId(String sxId) {
		this.sxId = sxId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getYqbz() {
		return yqbz;
	}

	public void setYqbz(String yqbz) {
		this.yqbz = yqbz;
	}
}
