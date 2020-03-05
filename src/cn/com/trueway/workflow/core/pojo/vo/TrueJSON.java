package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.set.pojo.EmployeeSort;
import cn.com.trueway.workflow.set.pojo.Trueform;
import cn.com.trueway.workflow.set.pojo.TrueformData;

public class TrueJSON {

	private String pdfurl; // pdfurl
	
	private List<Trueform> trueform; //表单信息
	
	private Object truepaper; // 意见信息
	
	private List<NodeInfo> nodeInfoList; //节点的信息
	
	private List<WfChild> childList; //子节点的信息
	
	private Object flowInfo;// 附加信息
	
	private String processUrl;// 查看历程的url
	
	private String needFj;  //是否需要上传附件
	
	private String msgInfo; // 消息提示 0 不显示消息中心  1 显示消息中心
	
	private List<String> personalComment ;
	
	private String formPage;	//第一页

	private String formPages;	//表单正文页数
	
	private List<TrueformData> trueformdata;	//保存业务表单值
	
	private String isFlexibleForm;
	
	private String lastStep;
	
	private JSONObject commonStatus;	//通用状态  (0,不展示按钮;1,可以关注提醒;2,可以补发)
	
	private String copyPdfPath;
	
	private String isDoInMobile;
	
	private String isDoEnd;
	
	/**
	 * 附件信息
	 */
	private List<MobileAttachment> attList;
	
	/**
	 * 日期时间格式
	 */
	private String dateFormat;
	
	/**
	 * 字号
	 */
	private String fontSize;
	
	/**
	 * 行间距
	 */
	private String verticalSpacing;
	
	/**
	 * 字体
	 */
	private String font;
	
	/**
	 * 节点的信息
	 */
	private List<EmployeeSort>  empList;
	
	/**
	 * 是否分批
	 */
	private String needFP;
	
	/**
	 * 可退回节点信息
	 */
	private String backNodeList; 
	
	/**
	 * 当前节点
	 */
	private String routeType;
	
	/**
	 * （true 已办，false 待办）
	 */
	private String isOver;
	
	private String userName;
	
	private JSONArray processIdSort;
	
	private String pleaseOrWatch;
	
	private String needHideOperate;//需要隐藏按钮
	
	private String needName; //是否需要添加落款
	
	public TrueJSON() {
		
	}

	public TrueJSON(String pdfurl, List<Trueform> trueform, String truepaper,
			List<NodeInfo> nodeInfoList, List<WfChild> childList,
			Object flowInfo, String processUrl, String needFj, String msgInfo) {
		super();
		this.pdfurl = pdfurl;
		this.trueform = trueform;
		this.truepaper = truepaper;
		this.nodeInfoList = nodeInfoList;
		this.childList = childList;
		this.flowInfo = flowInfo;
		this.processUrl = processUrl;
		this.needFj = needFj;
		this.msgInfo = msgInfo;
	}

	public String getPdfurl() {
		return pdfurl;
	}

	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}

	public List<Trueform> getTrueform() {
		return trueform;
	}

	public void setTrueform(List<Trueform> trueform) {
		this.trueform = trueform;
	}

	public Object getTruepaper() {
		return truepaper;
	}

	public void setTruepaper(Object truepaper) {
		this.truepaper = truepaper;
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

	public List<String> getPersonalComment() {
		return personalComment;
	}

	public void setPersonalComment(List<String> personalComment) {
		this.personalComment = personalComment;
	}

	public String getFormPage() {
		return formPage;
	}

	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}

	public String getFormPages() {
		return formPages;
	}

	public void setFormPages(String formPages) {
		this.formPages = formPages;
	}

	public List<TrueformData> getTrueformdata() {
		return trueformdata;
	}

	public void setTrueformdata(List<TrueformData> trueformdata) {
		this.trueformdata = trueformdata;
	}

	public String getIsFlexibleForm() {
		return isFlexibleForm;
	}

	public void setIsFlexibleForm(String isFlexibleForm) {
		this.isFlexibleForm = isFlexibleForm;
	}

	public String getLastStep() {
		return lastStep;
	}

	public void setLastStep(String lastStep) {
		this.lastStep = lastStep;
	}

	public JSONObject getCommonStatus() {
		return commonStatus;
	}

	public void setCommonStatus(JSONObject commonStatus) {
		this.commonStatus = commonStatus;
	}

	public String getCopyPdfPath() {
		return copyPdfPath;
	}

	public void setCopyPdfPath(String copyPdfPath) {
		this.copyPdfPath = copyPdfPath;
	}

	public String getIsDoInMobile() {
		return isDoInMobile;
	}

	public void setIsDoInMobile(String isDoInMobile) {
		this.isDoInMobile = isDoInMobile;
	}

	public List<MobileAttachment> getAttList() {
		return attList;
	}

	public void setAttList(List<MobileAttachment> attList) {
		this.attList = attList;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getVerticalSpacing() {
		return verticalSpacing;
	}

	public void setVerticalSpacing(String verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public List<EmployeeSort> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmployeeSort> empList) {
		this.empList = empList;
	}

	public String getNeedFP() {
		return needFP;
	}

	public void setNeedFP(String needFP) {
		this.needFP = needFP;
	}

	public String getBackNodeList() {
		return backNodeList;
	}

	public void setBackNodeList(String backNodeList) {
		this.backNodeList = backNodeList;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public String getIsDoEnd() {
		return isDoEnd;
	}

	public void setIsDoEnd(String isDoEnd) {
		this.isDoEnd = isDoEnd;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public JSONArray getProcessIdSort() {
		return processIdSort;
	}

	public void setProcessIdSort(JSONArray processIdSort) {
		this.processIdSort = processIdSort;
	}

	public String getPleaseOrWatch() {
		return pleaseOrWatch;
	}

	public void setPleaseOrWatch(String pleaseOrWatch) {
		this.pleaseOrWatch = pleaseOrWatch;
	}

	public String getNeedHideOperate() {
		return needHideOperate;
	}

	public void setNeedHideOperate(String needHideOperate) {
		this.needHideOperate = needHideOperate;
	}

	public String getNeedName() {
		return needName;
	}

	public void setNeedName(String needName) {
		this.needName = needName;
	}
	
}
