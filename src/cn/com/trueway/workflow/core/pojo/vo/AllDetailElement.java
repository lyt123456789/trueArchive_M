package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.set.pojo.Trueform;

public class AllDetailElement {
	
	private List<EmpData> empDataList; //人员信息
	
	private List<Trueform> trueList; //表单信息
	
	private List<String> existJsonList; //已经存在的手写json
	
	private List<NodeInfo> nodeInfoList; //节点的信息

	private List<WfChild> childList; //节点的信息
	
	private String pdfPath ;

	private Object flowInfo;
	
	private String processUrl;
	
	private String needFj;  //是否需要上传附件
	
	private String msgInfo;
	
	public AllDetailElement() {
	}

	public AllDetailElement(List<EmpData> empDataList,
			List<Trueform> trueList, List<String> existJsonList,
			List<NodeInfo> nodeInfoList,List<WfChild> childList,String pdfPath,Object flowInfo,String processUrl,String needFj,String msgInfo) {
		super();
		this.empDataList = empDataList;
		this.trueList = trueList;
		this.existJsonList = existJsonList;
		this.nodeInfoList = nodeInfoList;
		this.childList = childList;
		this.pdfPath = pdfPath;
		this.flowInfo = flowInfo;
		this.processUrl = processUrl;
		this.needFj = needFj;
		this.msgInfo = msgInfo;
	}

	public List<EmpData> getEmpDataList() {
		return empDataList;
	}
	public void setEmpDataList(List<EmpData> empDataList) {
		this.empDataList = empDataList;
	}
	public List<Trueform> getTrueList() {
		return trueList;
	}

	public void setTrueList(List<Trueform> trueList) {
		this.trueList = trueList;
	}

	public List<String> getExistJsonList() {
		return existJsonList;
	}
	public void setExistJsonList(List<String> existJsonList) {
		this.existJsonList = existJsonList;
	}

	public List<NodeInfo> getNodeInfoList() {
		return nodeInfoList;
	}
	public void setNodeInfoList(List<NodeInfo> nodeInfoList) {
		this.nodeInfoList = nodeInfoList;
	}

	public List<WfChild> getChildList() {
		return childList;
	}

	public void setChildList(List<WfChild> childList) {
		this.childList = childList;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public Object getFlowInfo() {
		return flowInfo;
	}

	public void setFlowInfo(Object flowInfo) {
		this.flowInfo = flowInfo;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public String getNeedFj() {
		return needFj;
	}

	public void setNeedFj(String needFj) {
		this.needFj = needFj;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	/*public String getFlowInfo() {
		return flowInfo;
	}

	public void setFlowInfo(String flowInfo) {
		this.flowInfo = flowInfo;
	}*/
	
}
