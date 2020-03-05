package cn.com.trueway.workflow.set.pojo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "T_WF_CORE_PROCEDURE")
public class Procedure {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id",length = 36)
	private String id;//主键
	
	@Column(name = "WORKFLOWID", nullable = true)
	private String workflowid;//关联工作流id
	
	@Column(name = "INTIME", nullable = true)
	private Timestamp intime;//入库时间
	
	@Column(name = "INPERSON", nullable = true)
	private String inperson;//入库人
	
	@Column(name = "UPDATETIME", nullable = true)
	private Timestamp updatetime;//更新时间
	
	@Column(name = "UPDATEPERSON", nullable = true)
	private String updateperson;//更新人
	
	@Column(name = "ZINDEX", nullable = true)
	private String zindex;//排序号
	
	@Column(name = "PNAME", nullable = true)
	private String pname;//存储过程名称
	
	@Column(name = "PARAMNAME", nullable = true)
	private String paramname;//存储过程参数名称
	
	@Column(name = "PARAMTYPE", nullable = true)
	private String paramtype;//存储过程参数类型
	
	@Column(name = "INOUTTYPE", nullable = true)
	private String inouttype;//存储过程参数输入输出类型
	
	@Column(name = "PCONTENT", nullable = true)
	private String pcontent;//存储过程内容
	
	@Column(name = "PCNAME", nullable = true)
	private String pcname;//存储过程中文名称
	
	
	@Transient
	private List<Map> params;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkflowid() {
		return workflowid;
	}
	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public String getInperson() {
		return inperson;
	}
	public void setInperson(String inperson) {
		this.inperson = inperson;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateperson() {
		return updateperson;
	}
	public void setUpdateperson(String updateperson) {
		this.updateperson = updateperson;
	}
	public String getZindex() {
		return zindex;
	}
	public void setZindex(String zindex) {
		this.zindex = zindex;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String getParamtype() {
		return paramtype;
	}
	public void setParamtype(String paramtype) {
		this.paramtype = paramtype;
	}
	public String getInouttype() {
		return inouttype;
	}
	public void setInouttype(String inouttype) {
		this.inouttype = inouttype;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public List<Map> getParams() {
		return params;
	}
	public void setParams(List<Map> params) {
		this.params = params;
	}
	public String getPcname() {
		return pcname;
	}
	public void setPcname(String pcname) {
		this.pcname = pcname;
	}
	
	
}
