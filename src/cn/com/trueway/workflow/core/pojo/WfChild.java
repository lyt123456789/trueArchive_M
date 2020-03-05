package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 */
@Entity
@Table(name="WF_CHILD")
public class WfChild {
	@Id
	@Column(name="WFC_ID",length=36)
	private String wfc_id;
	
	@Column(name="WFC_CID",length=36)
	private String wfc_cid;
	
	@Column(name="WFC_CNAME",length=100)
	private String wfc_cname;		//子流程名称
	
	@Column(name="WFC_CTYPE",length=2)
	private String wfc_ctype;		// 子流程类型--0:一个实例 1:多个实例 0:一个实例
	
	@Column(name="WFC_RELATION",length=2)
	private String wfc_relation;
	
	@Column(name="WFC_MODULEID",length=100)
	private String wfc_moduleId;		// 标志子流程位置的
	
	@Column(name="WFC_MAINMERGER",length=2)
	private String wfc_mainmerger;		//是否正文合并
	
	@Column(name="WFC_OUTPARWF",length=2)
	private String wfc_outparwf;		//是否脱离父流程
	
	@Column(name="WFC_RETURN_PEND",length=2)
	private String wfc_return_pend;		
	
	@Column(name="WFC_ISSEND",length=2)
	private String wfc_isSend;
	
	@Column(name="WFC_NODENAME", length=100)
	private String wfc_nodeName;			//子流程办理结束后自动调整的节点
	
	@ManyToOne(targetEntity=WfMain.class)
	@JoinColumn(name="WFC_PID")
	private WfMain wfMain;
	
	@Column(name="ISNEED_F_FORM",length=2)
	private String isNeedFForm;				//是否和父流程表单
	
	public String getWfc_id() {
		return wfc_id;
	}
	public void setWfc_id(String wfc_id) {
		this.wfc_id = wfc_id;
	}
	
	public String getWfc_cid() {
		return wfc_cid;
	}
	public void setWfc_cid(String wfc_cid) {
		this.wfc_cid = wfc_cid;
	}
	
	public String getWfc_cname() {
		return wfc_cname;
	}
	public void setWfc_cname(String wfc_cname) {
		this.wfc_cname = wfc_cname;
	}
	
	public String getWfc_ctype() {
		return wfc_ctype;
	}
	public void setWfc_ctype(String wfc_ctype) {
		this.wfc_ctype = wfc_ctype;
	}
	
	public String getWfc_relation() {
		return wfc_relation;
	}
	public void setWfc_relation(String wfc_relation) {
		this.wfc_relation = wfc_relation;
	}
	
	public String getWfc_moduleId() {
		return wfc_moduleId;
	}
	public void setWfc_moduleId(String wfc_moduleId) {
		this.wfc_moduleId = wfc_moduleId;
	}
	public String getWfc_mainmerger() {
		return wfc_mainmerger;
	}
	public void setWfc_mainmerger(String wfc_mainmerger) {
		this.wfc_mainmerger = wfc_mainmerger;
	}
	public String getWfc_outparwf() {
		return wfc_outparwf;
	}
	public void setWfc_outparwf(String wfc_outparwf) {
		this.wfc_outparwf = wfc_outparwf;
	}
	public String getWfc_return_pend() {
		return wfc_return_pend;
	}
	
	public void setWfc_return_pend(String wfc_return_pend) {
		this.wfc_return_pend = wfc_return_pend;
	}
	
	public String getWfc_isSend() {
		return wfc_isSend;
	}
	public void setWfc_isSend(String wfc_isSend) {
		this.wfc_isSend = wfc_isSend;
	}
	public WfMain getWfMain() {
		return wfMain;
	}
	public void setWfMain(WfMain wfMain) {
		this.wfMain = wfMain;
	}
	public String getWfc_nodeName() {
		return wfc_nodeName;
	}
	public void setWfc_nodeName(String wfc_nodeName) {
		this.wfc_nodeName = wfc_nodeName;
	}
	public String getIsNeedFForm() {
		return isNeedFForm;
	}
	public void setIsNeedFForm(String isNeedFForm) {
		this.isNeedFForm = isNeedFForm;
	}
}
