package cn.com.trueway.workflow.core.pojo.vo;

/**
 * 描述：TODO 对WfProcessVO进行描述
 * 作者：蒋烽
 * 创建时间：2017-4-5 下午5:03:28
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class WfProcessVO {
	
	/**
	 * 是否主办 
	 */
	private String doType;
	
	/**
	 * 人员姓名 
	 */
	private String userName;
	
	/**
	 * 接收时间 
	 */
	private String receiveTime;
	
	/**
	 * 办理时间 
	 */
	private String finishTime;
	
	/**
	 * 办理状态 
	 */
	private String status;

	/**
	 * 手机号码
	 */
	private String mobileNum;
	
	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
}
