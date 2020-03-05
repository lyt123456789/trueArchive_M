package cn.com.trueway.workflow.core.pojo;

import java.util.List;

import cn.com.trueway.workflow.set.pojo.ZwkjForm;

public class WfMainJson {
	private String wfm_id;
	private String wfm_name;
	private String wfm_createtime;
	private String wfm_modifytime;
	private String wfm_status;
	private List<WfNode> nodeList;
	private List<ZwkjForm> formList;
	private WfItem wfItem;
	
	public String getWfm_modifytime() {
		return wfm_modifytime;
	}
	public void setWfm_modifytime(String wfm_modifytime) {
		this.wfm_modifytime = wfm_modifytime;
	}
	public String getWfm_id() {
		return wfm_id;
	}
	public void setWfm_id(String wfm_id) {
		this.wfm_id = wfm_id;
	}
	public String getWfm_name() {
		return wfm_name;
	}
	public void setWfm_name(String wfm_name) {
		this.wfm_name = wfm_name;
	}
	public String getWfm_createtime() {
		return wfm_createtime;
	}
	public void setWfm_createtime(String wfm_createtime) {
		this.wfm_createtime = wfm_createtime;
	}
	public String getWfm_status() {
		return wfm_status;
	}
	public void setWfm_status(String wfm_status) {
		this.wfm_status = wfm_status;
	}
	public List<WfNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<WfNode> nodeList) {
		this.nodeList = nodeList;
	}
	public List<ZwkjForm> getFormList() {
		return formList;
	}
	public void setFormList(List<ZwkjForm> formList) {
		this.formList = formList;
	}
	public WfItem getWfItem() {
		return wfItem;
	}
	public void setWfItem(WfItem wfItem) {
		this.wfItem = wfItem;
	}
}
