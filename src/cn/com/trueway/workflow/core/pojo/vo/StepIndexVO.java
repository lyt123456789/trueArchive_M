package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

/**
 * 描述：TODO 对StepInexVO进行描述
 * 作者：蒋烽
 * 创建时间：2017-4-5 下午5:03:23
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class StepIndexVO {
	
	/**
	 * 节点名称
	 */
	private String 				nodeName;
	
	/**
	 * 上一步的发送时间 
	 */
	private String 				applyTime;
	
	/**
	 * 步骤的序号 
	 */
	private Integer 			stepIndex;
	
	/**
	 * 某一步办理人员列表 
	 */
	private List<WfProcessVO> 	stepUserList;
	
	/**
	 * 是否是最后一步(0,否;1,是)
	 */
	private String				isEnd;
	
	/**
	 * 手机号码
	 */
	private String				mobileNum;

	/**
	 * 流程线的起始位置
	 */
	private String 				moduleid;
	
	/**
	 * 节点名称
	 */
	private String				nodeId;
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}

	public List<WfProcessVO> getStepUserList() {
		return stepUserList;
	}

	public void setStepUserList(List<WfProcessVO> stepUserList) {
		this.stepUserList = stepUserList;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
