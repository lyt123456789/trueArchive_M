package cn.com.trueway.workflow.core.pojo.vo;

import java.io.Serializable;

/**
 * 文件名称： cn.com.trueway.workflow.core.pojo.vo.TrueOperateLog.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2018-12-21<br/>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>  
 * 修改记录：<br/> 
 * 修改作者        日期       修改内容<br/> 
 */
public class TrueOperateLog implements Serializable{

	/**
	 * 字段描述: [字段功能描述]
	 */
	private static final long serialVersionUID = -2316128758491215635L;

	private String userName;//用户名
	
	private String openTime;//打开时间
	
	private String operateTime;//办理时间

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
}
